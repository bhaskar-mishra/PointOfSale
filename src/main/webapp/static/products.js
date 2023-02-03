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
	        handleSuccess("Product Added Successfully");
	        document.getElementById('product-form').reset();
	   		getProductList();
	   },
	   error: handleAjaxError
	});

	return false;
}




//Edits a product with a given barcode

function updateProduct(){
console.log('inside update product');
var url = getProductUrl();
url+="/api/product";
console.log('url');
var $form = $("#product-edit-form");
var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
             getProductList();
             handleSuccess("Product Updated");
             $('#editProductsModal').modal('hide');
             },
	   error: handleAjaxError
	});

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

function addBrandOptions()
{
  var url = getProductUrl() + "/api/brand";
  $.ajax({
  	   url: url,
  	   type: 'GET',
  	   success: function(data) {
  	        console.log(data);
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
//addBrandCategoryId("brandCategoryId");
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
selectElement.add(new Option(e.category));
}

}

function displayEditProduct(barcode){
$('#editProductsModal').modal();
document.getElementById('inputBarcodeEdit').value = barcode;
var url = getProductUrl()+"/api/product/"+barcode;
 $.ajax({
   	   url: url,
   	   type: 'GET',
   	   success: function(data) {
   	   console.log('data fetched for given barcode');
   	   console.log(data);
   	   		document.getElementById('inputProductEdit').value = data.product;
   	   		document.getElementById('inputMRPEdit').value = data.mrp;
   	   },
   	   error: handleAjaxError
   	});

}

$(function(){
    $("[data-hide]").on("click", function(){
        $("." + $(this).attr("data-hide")).hide();
        // -or-, see below
        // $(this).closest("." + $(this).attr("data-hide")).hide();
    });
});

function hideAlert(){
var element = document.getElementById('editProductsModal');
element.style.display = "none";
}


// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#productsFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}

	//Process next row
	var row = fileData[processCount];
	processCount++;

	var json = JSON.stringify(row);
	var url = getProductUrl();
	url = url+"/api/product";

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		uploadRows();
	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}



// PRODUCT EDIT METHODS

function deleteProduct(id){
	var url = getProductUrl();
	url = url+"/api/product";
	url = url + "/" + product_barcode;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getProductList();
	   },
	   error: handleAjaxError
	});
}

//UI DISPLAY METHODS

function displayProductList(data){
	console.log('Printing user data');
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	console.log(data.length);
	var serialNo = 1;
	for(var i in data){
		var e = data[i];
        var buttonHtml = ''; //'<button onclick="deleteProduct(' + ')">Delete</button>'
        		buttonHtml += ' <button onclick="displayEditProduct(\'' + e.barcode + '\')">Edit</button>'
		var row = '<tr>'
		+ '<td>' + (serialNo++) + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + e.product + '</td>'
		+ '<td>' + e.mrp + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}

}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#productsFile');
	$file.val('');
	$('#productsFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#productsFile');
	var fileName = $file.val();
	$('#productsFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-product-modal').modal('toggle');
}

function pagination(){
  $('.dataTables_length').addClass('bs-select');
}


//INITIALIZATION CODE

function setPage(){
  var role = $("meta[name=role]").attr("content");
  if(role==="standard"){
  var form = document.getElementById('product-form');
  form.style.display = "none";
  var admin = document.getElementById('admin-nav-link');
  admin.style.display = "none";
  var reports = document.getElementById('reports-nav-link');
  reports.style.display = "none";
  }
}


function init(){
console.log('product page loading');
	$('#add-product').click(addProduct);
	$('#update-product').click(updateProduct);
	$('#upload-data').click(displayUploadData);
    $('#process-data').click(processData);
    $('#download-errors').click(downloadErrors);
    $('#employeeFile').on('change', updateFileName)

}

$(document).ready(addBrandOptions);
$(document).ready(init);
$(document).ready(getProductList);
$(document).ready(setPage);

