package com.payoneer.jobmgmtservice.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.payoneer.jobmgmtservice.common.constants.BatchJobEnum;

/**
 * @author Ananth Shanmugam
 * Controller to hold links for pages in web app
 */
@Controller
public class HomeController {

	private final String INDEX_PAGE = "index";

	private final String FAQ_PAGE = "faq";

	private final String ABOUT_PAGE = "about";
	private final String ERROR_PAGE_403 = "403";

	private final String ERROR_PAGE_404 = "404";

	private final String PERMISSION_ERROR_MSG = "You do not have permission to access this page!";

	@GetMapping(value = { "/" }) /* Get the entry page of book store */
	public String index(Model model) {

		Map<String, String> batchMap = new HashMap<>();
		for (BatchJobEnum currCode : BatchJobEnum.values()) {
			String jobName = currCode.name().replace("_", " ");
			batchMap.put(currCode.getJobCode(), jobName);
		}
		model.addAttribute("selectedBatchName", "");
		model.addAttribute("batchMap", batchMap);

		return INDEX_PAGE;
	}

	@GetMapping(value = { "/faq" }) /* Get the faq page of book store */
	public String faq(Model model) {
		return FAQ_PAGE;
	}

	@GetMapping(value = { "/about" }) /* Get the about page of book store */
	public String about(Model model) {
		return ABOUT_PAGE;
	}

	@GetMapping("/404")
	public String get404() {
		return ERROR_PAGE_404;
	}

	@GetMapping("/403")
	public String get403(Principal user, Model model) {
		model.addAttribute("msg", PERMISSION_ERROR_MSG);
		return ERROR_PAGE_403;
	}

	@GetMapping("/error") /* Generic error page mapping */
	public String getError() {
		return "error";
	}


	@GetMapping("/user/dashboard") /* Generic error page mapping */
	public String getDashboard() {
		return "dashboard";
	}

	private final String ACCOUNT_LOGIN_PAGE = "/account/login";

	private final String REDIRECT_TO_HOME_PAGE = "redirect:/";

	@GetMapping(value = { "/account/login" })
	public String getLoginPage() { /* get the login page */
		return ACCOUNT_LOGIN_PAGE;
	}

	@RequestMapping(value = "/account/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return REDIRECT_TO_HOME_PAGE;
	}
}
