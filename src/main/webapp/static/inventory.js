
var product_barcode ;
function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}

//BUTTON ACTIONS
function addInventory(event){
	//Set the values to update
	var $form = $("#inventory-form");
	var json = toJson($form);
	var url = getInventoryUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getInventoryList();
	   },
	   error: handleAjaxError
	});

	return false;
}

function getInventoryList(){
	var url = getInventoryUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayInventoryList(data);
	   },
	   error: handleAjaxError
	});
}


function updateInventory(){
 var url = getInventoryUrl();
 url = url + "/updateInventory";
 url = url + "/" + product_barcode;

 var $form = $("#inventory-edit-form");
 	var json = toJson($form);

 	$.ajax({
 	   url: url,
 	   type: 'PUT',
 	   data: json,
 	   headers: {
        	'Content-Type': 'application/json'
        },
 	   success: function(response) {
              var element = document.getElementById('success-alert');
              console.log(element)
              element.style.display = "inline";
              },
 	   error: handleAjaxError
 	});

}

function displayEditInventory(){
$('#editInventoryModal').modal();
}

$(function(){
    $("[data-hide]").on("click", function(){
        $("." + $(this).attr("data-hide")).hide();
        // -or-, see below
        // $(this).closest("." + $(this).attr("data-hide")).hide();
    });
});


//UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#inventoryFile')[0].files[0];
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
	var url = getInventoryUrl();

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


function resetUploadDialog(){
	//Reset file name
	var $file = $('#inventoryFile');
	$file.val('');
	$('#inventoryFileName').html("Choose File");
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
	var $file = $('#inventoryFile');
	var fileName = $file.val();
	$('#inventoryFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-inventory-modal').modal('toggle');
}

//UI DISPLAY METHODS

function displayInventoryList(data){
	console.log('Printing user data');
	var $tbody = $('#inventory-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		product_barcode = e.barcode;
		var buttonHtml = ''
		buttonHtml += ' <button onclick="displayEditInventory(' + ')">Edit</button>'
		var row = '<tr>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.product + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}


//INITIALIZATION CODE

function setPage(){
  var role = $("meta[name=role]").attr("content");
  if(role==="standard"){
  var form = document.getElementById('inventory-form');
  form.style.display = "none";
  var admin = document.getElementById('admin-nav-link');
  admin.style.display = "none";
  var reports = document.getElementById('reports-nav-link');
  reports.style.display = "none";
  }
}


function init(){
	$('#add-inventory').click(addInventory);
	$('#upload-data').click(displayUploadData);
    $('#process-data').click(processData);
    $('#download-errors').click(downloadErrors);
    $('#inventoryFile').on('change', updateFileName)
//	$('#refresh-data').click(getUserList);
}

$(document).ready(init);
$(document).ready(getInventoryList);
$(document).ready(setPage);

