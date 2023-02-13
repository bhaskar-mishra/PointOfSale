var brandCategoryId;
function getBrandCategoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}

//BUTTON ACTIONS
function addBrandCategory(event){
	//Set the values to update
	var $form = $("#brand-category-form");
	var json = toJson($form);
	var url = getBrandCategoryUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   document.getElementById('brand-category-form').reset();
	   console.log('calling handleSuccess');
	        handleSuccess("BRAND ADDED SUCCESSFULLY!");
	   		getBrandCategoryList();
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateBrandCategory(){
var url = $("meta[name=baseUrl]").attr("content")
url+="/api/brand";
url = url + "/" + brandCategoryId;

var $form = $("#brand-category-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
             getBrandCategoryList();
             handleSuccess("Brand Updated");
             $('#editBrandCategoryModal').modal('hide');
             },
	   error: handleAjaxError
	});

}

function getBrandCategoryList(){
	var url = getBrandCategoryUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrandCategoryList(data);
	   },
	   error: handleAjaxError
	});
}

function deleteBrandCategory(id){
var url = getBrandCategoryUrl();
url = url+"/" + id;
$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getBrandCategoryList();
	   },
	   error: handleAjaxError
	});
}

function displayEditBrandCategory(id){
var role = $("meta[name=role]").attr("content");
if(role==="standard"){
return ;
}

$('#editBrandCategoryModal').modal();
brandCategoryId = id;

var url = getBrandCategoryUrl();
url = url + "/getById/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   console.log(data);
	   		var brand = document.getElementById('inputBrandEdit');
	   		var category = document.getElementById('inputCategoryEdit');
	   		brand.value = data.brand;
	   		category.value = data.category;
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
var element = document.getElementById('editBrandCategoryModal');
element.style.display = "none";
}


//UPLOAD METHODS

var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#brandCategoryFile')[0].files[0];
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
	getBrandCategoryList();
		return;
	}

	//Process next row
	var row = fileData[processCount];
	processCount++;

	var json = JSON.stringify(row);
	var url = getBrandCategoryUrl();

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


//UI DISPLAY METHODS

function displayBrandCategoryList(data){
	console.log('Printing user data');
	var $tbody = $('#brand-category-table').find('tbody');
	var serialNo = 1;
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = ''; //buttonHtml = '<button onclick="deleteBrandCategory(' + e.id + ')">Delete</button>'
		buttonHtml += ' <button type="button" class="btn btn-primary" onclick="displayEditBrandCategory(' + e.id + ')">Edit</button>'
		var row = '<tr>'
		+ '<td>' + (serialNo++)+ '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#brandCategoryFile');
	$file.val('');
	$('#brandCategoryFileName').html("Choose File");
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
	var $file = $('#brandCategoryFile');
	var fileName = $file.val();
	$('#brandCategoryFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-brand-category-modal').modal('toggle');
}


//INITIALIZATION CODE

function setPage(){
  var role = $("meta[name=role]").attr("content");
  if(role==="standard"){
  var form = document.getElementById('brand-category-form');
  form.style.display = "none";
     document.getElementById('reportsDropDown').style.display = 'none';
      document.getElementById('userDropDownOption').style.display = 'none';
  }
}


function init(){
	$('#add-brand-category').click(addBrandCategory);
	$('#update-brand-category').click(updateBrandCategory);
	$('#upload-data').click(displayUploadData);
    $('#process-data').click(processData);
    $('#download-errors').click(downloadErrors);
    $('#brandCategoryFile').on('change', updateFileName)

}

$(document).ready(init);
$(document).ready(getBrandCategoryList);
$(document).ready(setPage);

