var orderCode;
var invoiceToDownload;
var orderItemListLength;
var orderItemId;
var status;


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
	        handleSuccess('item added');
	        document.getElementById('orderItem-form').reset();
	        document.getElementById('inputOrderId').value = orderCode;
	   		getOrderItemList();
	   },
	   error: handleAjaxError
	});

	return false;
}



function getOrderItemList(){
	var url = getOrderItemUrl();
	url+="/api/orderItem";
	url = url + "/" + orderCode;
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
 var url = getOrderItemUrl();
 url = url+"/api/orderItem";
 url = url+"/" + orderCode;
 var $tbody = $('#orderItem-table').find('tbody');
 var orderItemsCount = $tbody.length;
 console.log($tbody.length);

 $.ajax({
 	   url: url,
 	   type: 'PUT',
 	   success: function() {
 	   status = "PLACED"
 	   var element = document.getElementById('place-order');
 	   element.style.display = "none";
 	   var form = document.getElementById('orderItem-form');
 	   form.style.display = "none";
 	     var downloadInvoice = document.getElementById('download-invoice');
         downloadInvoice.style.display = "block";
         handleSuccess("Order Placed");
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




function deleteOrderItem(id){

	var url = getOrderItemUrl() + "/api/orderItem/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	        handleSuccess("item deleted");
	   		getOrderItemList();
	   },
	   error: handleAjaxError
	});
}



// DownloadPDF METHODS
function getInvoiceDetails(){
     var url = getOrderItemUrl();
     url = url + "/api/getInvoice";
     url = url + "/" + orderCode;

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
    var $form = $("#editOrderItemForm");
    var json = toJson($form);
    var url = getOrderItemUrl();
    url = url +"/api/orderItem/editOrderItem";

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
        	   		$('#editOrderItemModal').modal('hide');
        	   },
        	   error: handleAjaxError

        	});

        	return false;
}


function displayEditOrderItem(id){
if(status==="PLACED"){
return true;
}
orderItemId = id;
$('#editOrderItemModal').modal('toggle');

var url = getOrderItemUrl();
	url+="/api/orderItem/getById";
	url = url + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		document.getElementById('editModalOrderCode').value = orderCode;
	   		document.getElementById('editModalBarcode').value = data.barcode;
	   		document.getElementById('editModalQuantity').value = data.quantity;
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
		var buttonHtml = '<button onclick="deleteOrderItem(' + e.orderItemId+')">Delete</button>'
		buttonHtml += ' <button onclick="displayEditOrderItem(' + e.orderItemId + ')">Edit</button>'
		var row = '<tr>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.product + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.price + '</td>'
		+ '<td>' + e.total + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}

	console.log('inside displayOrderItemList in orderItem.js');
	console.log('printing table length '+$tbody.length);
}



function resetDataHelper(data){
status = data.status;
if(status==="PLACED"){
  document.getElementById('place-order').style.display = "none";

  document.getElementById('download-invoice').style.display = "block";

  document.getElementById('orderItem-form').style.display = "none";
}
}

function resetData(){
var url = getOrderItemUrl() + "/api/order/"+orderCode;
 $.ajax({
 	   url: url,
 	   type: 'GET',
 	   success: function(data) {
 	   		resetDataHelper(data);
 	   },
 	   error: handleAjaxError
 	});
}



//resets buttons and hides form if the order is already placed



//INITIALIZATION CODE
function init(){
    orderCode =  $("meta[name=orderCode]").attr("content");
    console.log(orderCode);
    console.log("inside init method in orderItem page");
    var inputItem = document.getElementById("inputOrderId");
    inputItem.value = orderCode;
	$('#add-orderItem').click(addItem);
	$('#update-orderItem').click(updateOrderItem);
	$('#place-order').click(placeOrder);
	$('#download-invoice').click(getInvoiceDetails);
}


$(document).ready(init);
$(document).ready(getOrderItemList);
$(document).ready(resetData);

