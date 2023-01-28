var report;
function getBaseUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl;
}



// METHODS FOR WHEN THE PAGE IS LOADED

//adds brands to the drop down
function addBrandOptions()
{
  var url = getBaseUrl() + "/api/brand/allBrands";
  $.ajax({
  	   url: url,
  	   type: 'GET',
  	   success: function(data) {
  	   		addBrandsToDropDown(data);
  	   },
  	   error: handleAjaxError
  	});
}



function addBrandsToDropDown(data){
 var selectElement = document.getElementById('inputBrand');
  for(var i in data)
  {
   var e = data[i];
   selectElement.add(new Option(e));
  }
}



// adds all the categories to the drop down
function addCategoryOptions(){
var url = getBaseUrl() + "/api/brand/allCategories";
  $.ajax({
   	   url: url,
   	   type: 'GET',
   	   success: function(data) {
   	   		addCategoriesToDropDown(data);
   	   },
   	   error: handleAjaxError
   	});
}



function addCategoriesToDropDown(data){
var selectElement = document.getElementById('inputCategory');
for(var i in data){
var e = data[i];
selectElement.add(new Option(e));
}
}



function getReport(){
var url = getBaseUrl();
url = url + "/api/reportController/allSales";
$.ajax({
   	   url: url,
   	   type: 'GET',
   	   success: function(data) {
   	   report = data;
   	   		displayReport(data);
   	   },
   	   error: handleAjaxError
   	});
}



// DROP DOWN METHODS
function brandDropDownHandler(){
return true;
}



function categoryDropDownHandler(){
return true;
}



//UI DISPLAY METHODS

// GETS SALES REPORT BASED ON USER INPUT
function getReportOnClick(event){
 var startDate = document.getElementById('inputStartDate');
 startDate = startDate.value;
 var endDate = document.getElementById('inputEndDate');
 endDate = endDate.value;

 if(startDate==="" && !(endDate==="")){
 throw "invalid date range";
 }else if(endDate==="" && !(startDate==="")){
 throw "invalid date range";
 }else if(startDate.localeCompare(endDate)>0){
 throw "invalid date range";
}

var brand = document.getElementById('inputBrand');
var category = document.getElementById('inputCategory');
 console.log(startDate);
 console.log(endDate);
if(brand==="" && category===""){
return false;
}

var $form = $("#sales-report-form");
	var json = toJson($form);
	var url = getBaseUrl() + "/api/reportsController/onUserInput";

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	     report = response;
	   		displayReport(response);
	   },
	   error: handleAjaxError
	});


}



function displayReport(data){
	console.log('Printing user data');
	var $tbody = $('#sales-report-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.revenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}


//Download method
function downloadReport(){
       writeFileData(report);
}

//INITIALIZATION CODE
function init(){
	$('#get-sales-report').click(getReportOnClick);
	$('#download-reports').click(downloadReport);
//	$('#refresh-data').click(getUserList);
}

$(document).ready(init);
$(document).ready(addBrandOptions);
$(document).ready(addCategoryOptions);
$(document).ready(getReport);

