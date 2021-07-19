package com.payoneer.jobmgmtservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.payoneer.jobmgmtservice.common.constants.BatchJobEnum;
import com.payoneer.jobmgmtservice.domain.JobDto;
import com.payoneer.jobmgmtservice.job.scheduler.QrtzJobScheduler;
import com.payoneer.jobmgmtservice.job.service.BatchJobLauncher;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ananth Shanmugam Primary controller to run/schedule payoneer jobs
 */
@RestController
@RequestMapping("/jobManager")
@Slf4j
public class BatchWebController {

	@Autowired
	private BatchJobLauncher batchJobLauncher;

	@Autowired
	private QrtzJobScheduler qrtzJobScheduler;

	private final String MINUTES_CRON_EXPR = "0 0/XX * 1/1 * ? *";

	private final String HOURLY_CRON_EXPR = "0 0 0/XX 1/1 * ? *";

	private final String EVERYDAY_CRON_EXPR = "0 MM HH 1/1 * ? *";

	private final String EVERYWEEKDAY_CRON_EXPR = "0 MM HH ? * MON-FRI *";

	private final String MONTHLY_CRON_EXPR = "0 MM HH XX 1/YY ? *";

	private final String WEEKDAY_CRON_EXPR = "0 MM HH ? * XX *";

	@PostMapping("/runJob/{jobCode}")
	@ResponseBody
	public JobDto executeBatch(@PathVariable("jobCode") String jobCode) {

		// String result = "Batch executed!";
		log.info("JobCode received is " + jobCode);
		boolean result = batchJobLauncher.launchJob(jobCode);
		return getJobResultStrDto(result, jobCode);
	}

	@PostMapping("/runJobinMinutes/{jobCode}/{minutes}")
	@ResponseBody
	public JobDto executeScheduledJobinMinutes(@PathVariable("jobCode") String jobCode,
			@PathVariable("minutes") String minutes) {

		String cronExp = MINUTES_CRON_EXPR.replaceAll("XX", minutes);
		boolean result = scheduleQuartzJob(jobCode, cronExp);
		return getJobScheduleResultStrDto(result, minutes, "minutes");
	}

	@PostMapping("/runJobinHours/{jobCode}/{hours}")
	@ResponseBody
	public JobDto executeScheduledJobinHours(@PathVariable("jobCode") String jobCode,
			@PathVariable("hours") String hours) {

		
		String cronExp = HOURLY_CRON_EXPR.replaceAll("XX", hours);
		boolean result = scheduleQuartzJob(jobCode, cronExp);
		return getJobScheduleResultStrDto(result, hours, "hours");
	}

	@PostMapping("/runJobDaily/{jobCode}/{daily}/{hour}/{minutes}")
	@ResponseBody
	public JobDto executeScheduledJobDaily(@PathVariable("jobCode") String jobCode, @PathVariable("daily") String daily,
			@PathVariable("hour") String hour, @PathVariable("minutes") String minutes) {

		String cronExp = "";
		if (daily.equals("everyday")) {
			cronExp = EVERYDAY_CRON_EXPR;
		} else {
			cronExp = EVERYWEEKDAY_CRON_EXPR;
		}
		cronExp = cronExp.replaceAll("XX", daily);
		cronExp = cronExp.replaceAll("HH", hour);
		cronExp = cronExp.replaceAll("MM", minutes);
		boolean result = scheduleQuartzJob(jobCode, cronExp);
		return getJobScheduleResultStrDto(result, daily, "at " + hour + ":" + minutes);
	}

	@PostMapping("/runJobWeekly/{jobCode}/{weekdays}/{hour}/{minutes}")
	@ResponseBody
	public JobDto executeScheduledJobWeekly(@PathVariable("jobCode") String jobCode,
			@PathVariable("weekdays") String weekdays, @PathVariable("hour") String hour,
			@PathVariable("minutes") String minutes) {

		weekdays = weekdays.replaceAll("_", ",");
		String cronExp = WEEKDAY_CRON_EXPR.replaceAll("XX", weekdays);
		cronExp = cronExp.replaceAll("HH", hour);
		cronExp = cronExp.replaceAll("MM", minutes);
		boolean result = scheduleQuartzJob(jobCode, cronExp);
		return getJobScheduleResultStrDto(result, weekdays, "weekly at " + hour + ":" + minutes);
	}

	@PostMapping("/runJobMonthly/{jobCode}/{dayOfMonth}/{months}/{hour}/{minutes}")
	@ResponseBody
	public JobDto executeScheduledJobMonthly(@PathVariable("jobCode") String jobCode,
			@PathVariable("dayOfMonth") String dayOfMonth, @PathVariable("months") String months,
			@PathVariable("hour") String hour, @PathVariable("minutes") String minutes) {

		// MONTHLY_CRON_EXPR = "0 0 12 XX 1/YY ? *";
		String cronExp = MONTHLY_CRON_EXPR.replaceAll("XX", dayOfMonth);
		cronExp = cronExp.replaceAll("YY", months);
		cronExp = cronExp.replaceAll("HH", hour);
		cronExp = cronExp.replaceAll("MM", minutes);
		boolean result = scheduleQuartzJob(jobCode, cronExp);
		return getJobScheduleResultStrDto(result, months, "monthly at " + hour + ":" + minutes);
	}

	private JobDto getJobResultStrDto(boolean result, String jobCode) {
		JobDto jobItemDTO = new JobDto();
		String resultStr = "Job ran for " + jobCode + "!";
		if (result) {
			jobItemDTO.setMessage(resultStr);
		} else {
			jobItemDTO.setMessage("Job error");
		}
		return jobItemDTO;

	}

	private JobDto getJobScheduleResultStrDto(boolean result, String value, String type) {
		JobDto jobItemDTO = new JobDto();
		String resultStr = "Job scheduled for " + value + " " + type + "!";
		if (result) {
			jobItemDTO.setMessage(resultStr);
		} else {
			jobItemDTO.setMessage("Job scheduling error");
		}
		return jobItemDTO;

	}

	private boolean scheduleQuartzJob(String jobCode, String cronExp) {
		return qrtzJobScheduler.scheduler(jobCode, cronExp);
		
	}

}