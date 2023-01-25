
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

function setForm(){
var role = $("meta[name=role]").attr("content");
  if(role==="standard"){
  var form = document.getElementById('inventory-form');
  form.style.display = "none";
  }
}


function init(){
	$('#add-inventory').click(addInventory);
//	$('#refresh-data').click(getUserList);
}

$(document).ready(init);
$(document).ready(getInventoryList);
$(document).ready(setForm);

