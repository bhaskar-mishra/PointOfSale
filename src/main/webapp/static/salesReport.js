var report;
function getBaseUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl;
}

// METHODS FOR WHEN THE PAGE IS LOADED

function setDropDowns(){
   var url = getBaseUrl() + "/api/brand";
     $.ajax({
     	   url: url,
     	   type: 'GET',
     	   success: function(data) {
     	   		addBrandsToDropDown(data);
     	   		addCategoriesToDropDown(data);
     	   },
     	   error: handleAjaxError
     	});
}


//Adds brands to brand's dropdown
function addBrandsToDropDown(data){
 var selectElement = document.getElementById('inputBrand');
 selectElement.options.length = 0;
 selectElement.add(new Option("ALL"));
  for(var i in data)
  {
   var e = data[i];
   selectElement.add(new Option(e.brand));
  }
  selectElement.value = "ALL";
}

// DROP DOWN METHODS
function brandDropDownHandler(){
console.log('brandDropDownHandler called');
var brand = document.getElementById('inputBrand').value;
console.log(brand);

if(brand==="ALL"){
setDropDowns();
return true;
}
var url = getBaseUrl() + "/api/brand/" + brand;
$.ajax({
   	   url: url,
   	   type: 'GET',
   	   success: function(data) {
   	       addCategoriesToDropDown(data);
   	   },
   	   error: handleAjaxError
   	});

return true;
}


//Adds categories to category's dropdown

function addCategoriesToDropDown(data){
var selectElement = document.getElementById('inputCategory');
selectElement.options.length = 0;
selectElement.add(new Option("ALL"));
for(var i in data){
var e = data[i];
selectElement.add(new Option(e.category));
}
selectElement.value = 'ALL';
}


function getReport(){
var url = getBaseUrl();
url = url + "/api/reports/salesReport";

var $form = $("#sales-report-form");
var json = toJson($form);

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


//UI DISPLAY METHODS



function displayReport(data){
	console.log('Printing sales report');
	var $tbody = $('#sales-report-table').find('tbody');
	$tbody.empty();
	var serialNo = 1;
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + (serialNo++) + '</td>'
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
	$('#get-sales-report').click(getReport);
	$('#download-reports').click(downloadReport);
	$('#inputBrand').change(brandDropDownHandler);
}

$(document).ready(init);
$(document).ready(setDropDowns);

