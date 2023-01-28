var randomKey;
var invoiceToDownload;
var orderItemListLength;



function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl;
}



function clearErrors(){
errors = document.getElementsByClassName('errors');
for(let item of errors){
item.innerHTML = "";
}
}



function setError(id,error){
 var formElementError = document.getElementById(id);
 formElementError.innerHTML = error;
 formElementError.style.color = "red";
}



function validateForm(){
clearErrors();
var returnVal = true;
var barcode = document.getElementById('inputBarcode').value;
if(barcode===""){
setError("barcodeError","Invalid barcode");
returnVal = false;
}

var quantity = document.getElementById('inputQuantity').value;
 if(isNaN(quantity)||quantity==0){
 setError("quantityError","Input a valid number");
 returnVal = false;
 }

 var price = document.getElementById('inputSellingPrice').value;
  if(isNaN(price) || price==0){
  setError("priceError","price not valid");
  returnVal = false;
  }

  return returnVal;
}



//BUTTON ACTIONS
function addItem(event){
	//Set the values to update

	if(!validateForm()){
	return false;
	}

	var $form = $("#orderItem-form");
	var json = toJson($form);
	var url = getOrderItemUrl();
	url+="/api/orderItem";

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getOrderItemList();
	   },
	   error: handleAjaxError
	});

	return false;
}



function getOrderItemList(){
	var url = getOrderItemUrl();
	url+="/api/orderItem";
	url = url + "/" + randomKey;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderItemList(data);
	   },
	   error: handleAjaxError
	});
}



function placeOrder(event){
clearErrors();
 var url = getOrderItemUrl();
 url = url+"/api/orderItem";
 url = url+"/" + randomKey;

 var $tbody = $('#orderItem-table').find('tbody');
 var orderItemsCount = $tbody.length;
 console.log($tbody.length);
 if(orderItemListLength==0){
  setError("place-order-error","no items in the order");
  return false;
 }

 $.ajax({
 	   url: url,
 	   type: 'PUT',
 	   success: function() {
 	   var element = document.getElementById('place-order');
 	   element.disabled = true;
 	   element.style.display = "none";
 	   var form = document.getElementById('orderItem-form');
 	   form.style.display = "none";
 	   var downloadInvoice = document.getElementById('download-invoice');
         downloadInvoice.style.display = "block";
         updateScheduler();
 	   },
 	   error: handleAjaxError
 	});
}



//UPDATES SCHEDULER WHEN AN ORDER IS PLACED
function updateScheduler(){
 var url = getOrderItemUrl();
 url = url + "/api/scheduler/addSchedule/";
 console.log('printing randomKey in update scheduler : '+randomKey);
 url = url + randomKey;

 $.ajax({
 	   url: url,
 	   type: 'POST',
 	   success: function() {
 	   		console.log('scheduler updated successfully');
 	   },
 	   error: handleAjaxError
 	});
}



function redirectToOrdersPage(){
var url = getOrderItemUrl();
url = url+"/site/orders";
window.location.href = url;
}



function deleteOrderItem(id){
	var url = getOrderItemUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getBrandCategoryList();
	   },
	   error: handleAjaxError
	});
}



// DownloadPDF METHODS
function getInvoiceDetails(){
     var url = getOrderItemUrl();
     url = url + "/api/getInvoice";
     url = url + "/" + randomKey;

     	$.ajax({
     	   url: url,
     	   type: 'GET',
     	   success: function(data) {
     	        console.log(data);
     	   },
     	   error: handleAjaxError
     	});
}



function downloadPdf(data){
console.log('inside downloadPdf');
    var url = getOrderItemUrl();
    url = url + "/api/printPdf/"+randomKey;
    url = url + "/" + data;
    $.ajax({
    	   url: url,
    	   type: 'POST',
    	   success: function() {
    	    console.log('pdf downloaded successfully');
    	   },
    	   error: handleAjaxError
    	});
}



//UI DISPLAY METHODS
function displayOrderItemList(data){
    orderItemListLength = data.length;
	console.log('Printing user data');
	console.log('data length in display orderItemList ' + data.length);
	console.log(JSON.stringify(data));
	var $tbody = $('#orderItem-table').find('tbody');
    console.log($tbody.length);
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button onclick="deleteOrderItem(' + e.orderItemId + ')">delete</button>'
		buttonHtml += ' <button onclick="displayEditOrderItem(' + e.orderItemId + ')">edit</button>'
		var row = '<tr>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.product + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}

	console.log('inside displayOrderItemList in orderItem.js');
	console.log('printing table length '+$tbody.length);
}



function resetDataHelper(data){
var status = data.status;
if(status==="COMPLETE"){
  var placeOrderButton = document.getElementById('place-order');
  placeOrderButton.innerHTML = "ORDER PLACED";
  placeOrderButton.disabled = true;
  placeOrderButton.style.display = "none";
  var downloadInvoice = document.getElementById('download-invoice');
  downloadInvoice.style.display = "block";

  var orderItemForm = document.getElementById('orderItem-form');
  orderItemForm.style.display = "none";

}
}



//resets buttons and hides form if the order is already placed
function resetData(){
  var url = getOrderItemUrl();
  url+="/api/order/useRandomKey";
  url+="/";
  url+=randomKey;

  $.ajax({
  	   url: url,
  	   type: 'GET',
  	   success: function(data) {
  	   		resetDataHelper(data);
  	   },
  	   error: handleAjaxError
  	});

}



//INITIALIZATION CODE
function init(){
    randomKey =  $("meta[name=randomKey]").attr("content");
    console.log(randomKey);
    console.log("inside init method in orderItem page");
    var inputItem = document.getElementById("inputOrderId");
    inputItem.value = randomKey;
	$('#add-orderItem').click(addItem);
	$('#place-order').click(placeOrder);
	$('#download-invoice').click(getInvoiceDetails);
}



$(document).ready(init);
$(document).ready(getOrderItemList);
$(document).ready(resetData);

