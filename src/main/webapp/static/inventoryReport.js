var report;
function getBaseUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl;
}

//METHODS FOR WHEN THE PAGE IS LOADED
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



//UI DISPLAY METHODS
function displayInventoryList(data){
	console.log('Printing Inventory Report');
	var $tbody = $('#inventory-report-table').find('tbody');
	$tbody.empty();
	var serialNo = 1;
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + (serialNo++) + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

//BUTTON ACTIONS
function getReport(event){

console.log("reaching getReport");

	var $form = $("#inventory-report-form");
	var json = toJson($form);
	var url = getBaseUrl();
	url = url + "/api/reports/inventoryReport";

	console.log(url);

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   console.log('printing report response : ' + response);
	        report = response;
	   		displayInventoryList(response);
	   },
	   error: handleAjaxError
	});

	return false;
}

//Download method
function downloadReport(){
console.log('download Report function being called');
       writeFileData(report);
       console.log('after writefiledata');
}


//INITIALIZATION CODE
function init(){
	$('#get-inventory-report').click(getReport);
    $('#download-reports').click(downloadReport);
    $('#inputBrand').change(brandDropDownHandler);
}

$(document).ready(init);
$(document).ready(setDropDowns);

