
function getProductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl ;
}

//BUTTON ACTIONS
function addProduct(event){
	//Set the values to update
	var $form = $("#product-form");
	var json = toJson($form);
	var url = getProductUrl() + "/api/product";

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getProductList();
	   },
	   error: handleAjaxError
	});

	return false;
}

function getProductList(){

console.log("Working fine till here");
	var url = getProductUrl();
	console.log(url);
	url = url + "/api/product"
	console.log(url);
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   console.log("success");
	   		displayProductList(data);
	   },
	   error: handleAjaxError
	});
}

function deleteBrandCategory(id){
	var url = getProductUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getBrandCategoryList();
	   },
	   error: handleAjaxError
	});
}

function addBrandOptions()
{
  var url = getProductUrl() + "/api/brand";
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
   selectElement.add(new Option(e.brand));
  }
}


function brandDropDownHandler(){
addBrandCategoryId("brandCategoryId");
 var x = document.getElementById('inputBrand').value;
 var url = getProductUrl() + "/api/brand" + "/"+x;

 $.ajax({
   	   url: url,
   	   type: 'GET',
   	   success: function(data) {
   	   		addCategoriesToDropDown(data);
   	   },
   	   error: handleAjaxError
   	});

}

function addCategoriesToDropDown(data)
{
var selectElement = document.getElementById('inputCategory');
 var L = selectElement.options.length - 1;
   for(var i = L; i >= 1; i--) {
      selectElement.remove(i);
   }

for(var i in data)
{
var e = data[i];
selectElement.add(new Option(e));
}

}

function setBrandCategoryId()
{
 var brand = document.getElementById('inputBrand').value;
 var category = document.getElementById('inputCategory').value;
 var url = getProductUrl() + "/api/brand"+"/" + brand + "/" + category;
 $.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   		addBrandCategoryId(data);
    	   },
    	   error: handleAjaxError
    	});
}

function addBrandCategoryId(data)
{
 var input = document.getElementById('inputBrandCategoryId');
 input.value = data;
}

//UI DISPLAY METHODS

function displayProductList(data){
	console.log('Printing user data');
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	console.log(data.length);
	for(var i in data){
		var e = data[i];

		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + e.product + '</td>'
		+ '<td>' + e.brandCategoryId+ '</td>'
		+ '<td>' + e.mrp + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}

}

function pagination(){
  $('.dataTables_length').addClass('bs-select');
}


//INITIALIZATION CODE
function init(){
	$('#add-product').click(addProduct);
//	$('#refresh-data').click(getUserList);
}

$(document).ready(addBrandOptions);
$(document).ready(init);
$(document).ready(getProductList);

