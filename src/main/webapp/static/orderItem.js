var orderCode;
var invoiceToDownload;
var orderItemListLength;
var orderItemId;

function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl;
}

//BUTTON ACTIONS
function addItem(event){
	//Set the values to update
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
     	        downloadPdf(data);
     	   },
     	   error: handleAjaxError
     	});
}


 //data is the base64 encoded string
function base64ToArrayBuffer(base64) {
    var binaryString = window.atob(base64);
    var binaryLen = binaryString.length;
    var bytes = new Uint8Array(binaryLen);
    for (var i = 0; i < binaryLen; i++) {
        var ascii = binaryString.charCodeAt(i);
        bytes[i] = ascii;
    }
    return bytes;
}


function downloadPdf(data){
console.log('inside downloadPdf');
    var arrrayBuffer = base64ToArrayBuffer(data);
    var blob = new Blob([arrrayBuffer], {type: "application/pdf"});
    var link = window.URL.createObjectURL(blob);
    window.open(link,'', 'height=650,width=840');
}


function updateOrderItem()
{
    console.log("this function will update order item");
    var $form = $("#editOrderItemForm");
    var json = toJson($form);
    var url = getOrderItemUrl();
    url = url +"/api/orderItem/editOrderItem";

    if((JSON.parse(json).quantity) == 0)
    {
        deleteOrderItem(JSON.parse(json).orderItemId);
        return;
    }

    $.ajax({
        	   url: url,
        	   type: 'PUT',
        	   data: json,
        	   headers: {
               	'Content-Type': 'application/json'
               },
        	   success: function(response) {
        	   		getOrderItemList();
        	   		handleSuccess("Item Updated");
        	   		$('#exampleModalCenter').modal('hide');
        	   },
        	   error: handleAjaxError

        	});

        	return false;
}


function displayEditOrderItem(id){
$('#editOrderItemModal').modal();
orderItemId = id;
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
		var buttonHtml = '<button onclick="deleteOrderItem(' + e.orderItemId+')">Delete</button>'
		buttonHtml += ' <button onclick="displayEditOrderItem(' + e.orderItemId + ')">Edit</button>'
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
    orderCode =  $("meta[name=orderCode]").attr("content");
    console.log(orderCode);
    console.log("inside init method in orderItem page");
    var inputItem = document.getElementById("inputOrderId");
    inputItem.value = orderCode;
	$('#add-orderItem').click(addItem);
	$('#place-order').click(placeOrder);
	$('#download-invoice').click(getInvoiceDetails);
}


$(document).ready(init);
$(document).ready(getOrderItemList);
$(document).ready(resetData);

