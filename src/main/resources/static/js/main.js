$(document).ready(function () {

    let serverUrl = window.location.protocol + "//" + window.location.host;
     
    // setup gridView
    $('#grid').DataTable({
        "autoWidth": true,
        "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]]
    });

    
$("#runBatchBtn").click(function() {
        let jobCode = $("#selectedBatchName").val();
		
        $.ajax({
            method: 'POST',
            url: serverUrl + "/jobManager/runJob/"+ jobCode,
            dataType: 'json',
            contentType: 'application/json',
            success: function (result) {
                if(result){
                $("#batchRunResult").html(result.message);
                } else {
                $("#batchRunResult").html('Failed batch request');

                }

            }, error: function (errors) {
                console.log(errors);
            }
        });
    });



 $("#runMinutesBatchBtn").click(function() {
        let jobCode = $("#selectedMinutesBatchName").val();
        let minutesVal = $("#minutes").val();
		
        $.ajax({
            method: 'POST',
            url: serverUrl + "/jobManager/runJobinMinutes/"+ jobCode+"/"+minutesVal,
            dataType: 'json',
            contentType: 'application/json',
            success: function (result) {
                if(result){
                $("#minutesBatchRunResult").html(result.message);
                } else {
                $("#minutesBatchRunResult").html('Failed Minutes batch request');

                }

            }, error: function (errors) {
                console.log(errors);
            }
        });
    });

    
    $("#runHourlyBatchBtn").click(function() {
        let jobCode = $("#selectedHourlyBatchName").val();
        let hourlyVal = $("#hourly").val();
	    $.ajax({
            method: 'POST',
            url: serverUrl + "/jobManager/runJobinHours/"+ jobCode+"/"+hourlyVal,
            dataType: 'json',
            contentType: 'application/json',
            success: function (result) {
                if(result){
                $("#hourlyBatchRunResult").html(result.message);
                } else {
                $("#hourlyBatchRunResult").html('Failed Hourly batch request');

                }

            }, error: function (errors) {
                console.log(errors);
            }
        });
    });
    
     $("#runDailyBatchBtn").click(function() {
        let jobCode = $("#selectedHourlyBatchName").val();
        let dailyVal = $("input[name='daily']:checked").val();
        let dailyHourVal = $("#dailyHour").val();
		let dailyMinutesVal = $("#dailyMinutes").val();
        
		
        $.ajax({
            method: 'POST',
            url: serverUrl + "/jobManager/runJobDaily/"+ jobCode+"/"+dailyVal+"/"+dailyHourVal+"/"+dailyMinutesVal,
            dataType: 'json',
            contentType: 'application/json',
            success: function (result) {
                if(result){
                $("#dailyBatchRunResult").html(result.message);
                } else {
                $("#dailyBatchRunResult").html('Failed Daily batch request');

                }

            }, error: function (errors) {
                console.log(errors);
            }
        });
    });
     $("#runWeeklyBatchBtn").click(function() {
        let jobCode = $("#selectedHourlyBatchName").val();
		let weeklyHourVal = $("#weeklyHour").val();
		let weeklyMinutesVal = $("#weeklyMinutes").val();
		
		let checkboxes = document.querySelectorAll('input[id="weekDays"]');
		let checkedOne = Array.prototype.slice.call(checkboxes).some(x => x.checked);
		if(!checkedOne) {
			alert('At least one weekly checkbox have to be checked. Thank you!');
		}
		else {
			let weekDays= [];
            $.each($("input[id='weekDays']:checked"), function(){
                weekDays.push($(this).val());
            });
            let weekDaysStr =  weekDays.join("_");
            alert("My weeklyDays are: " +weekDaysStr );
		
        	$.ajax({
            method: 'POST',
            url: serverUrl + "/jobManager/runJobWeekly/"+ jobCode+"/"+weekDaysStr+"/"+weeklyHourVal+"/"+weeklyMinutesVal,
            dataType: 'json',
            contentType: 'application/json',
            success: function (result) {
                if(result){
                $("#weeklyBatchRunResult").html(result.message);
                } else {
                $("#weeklyBatchRunResult").html('Failed weekly batch request');

                }

            }, error: function (errors) {
                console.log(errors);
            }
        	});
        }
    });
    
     $("#runMonthlyBatchBtn").click(function() {
        let jobCode = $("#selectedHourlyBatchName").val();
        let dayOfMonth = $("#dayOfMonth").val();
		let months = $("#months").val();
		let monthlyHourVal = $("#monthlyHour").val();
		let monthlyMinutesVal = $("#monthlyMinutes").val();
        $.ajax({
            method: 'POST',
            url: serverUrl + "/jobManager/runJobMonthly/"+ jobCode+"/"+dayOfMonth+"/"+months+"/"+monthlyHourVal+"/"+monthlyMinutesVal,
            dataType: 'json',
            contentType: 'application/json',
            success: function (result) {
                if(result){
                $("#monthlyBatchRunResult").html(result.message);
                } else {
                $("#monthlyBatchRunResult").html('Failed Hourly batch request');

                }

            }, error: function (errors) {
                console.log(errors);
            }
        });
    });

});

function openBatchFrequencyTab(evt, cityName) {
  // Declare all variables
  var i, tabcontent, tablinks;

  // Get all elements with class="tabcontent" and hide them
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }

  // Get all elements with class="tablinks" and remove the class "active"
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }

  // Show the current tab, and add an "active" class to the button that opened the tab
  document.getElementById(cityName).style.display = "block";
  evt.currentTarget.className += " active";
} 
