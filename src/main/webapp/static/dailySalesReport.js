function getBaseUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl;
}


function getSchedulerList(){
	var url = getBaseUrl();
	url = url + "/api/reports/getDailySalesReport";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displaySchedulerList(data);
	   },
	   error: handleAjaxError
	});
}
//UI DISPLAY METHODS

function displaySchedulerList(data){
	console.log('Printing Scheduler');
	var $tbody = $('#scheduler-table').find('tbody');
	var serialNo = 1;
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + (serialNo++)+ '</td>'
		+ '<td>' + e.date+ '</td>'
		+ '<td>' + e.invoiced_orders_count + '</td>'
		+ '<td>' + e.invoiced_items_count + '</td>'
		+ '<td>' + e.total_revenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}



//INITIALIZATION CODE
$(document).ready(getSchedulerList);


