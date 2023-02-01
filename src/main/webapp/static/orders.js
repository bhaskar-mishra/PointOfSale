
function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl ;
}

//BUTTON ACTIONS
function createOrder(event){
	console.log("create order clicked");
	var url = getOrderUrl() + "/api/order";

	$.ajax({
	   url: url,
	   type: 'POST',
	   success: function(response) {
	   		redirectToOrderItem(response);
	   },
	   error: handleAjaxError
	});

	return false;
}

function redirectToOrderItem(data){
 var baseUrl = getOrderUrl();
 var orderCode = data.orderCode;
 baseUrl+="/ui/orderItem";
 baseUrl = baseUrl + "/" + orderCode;
 window.location.href = baseUrl;
}

function getOrderList(){
    console.log("Working fine till here");
	var url = getOrderUrl();
	console.log(url);
	url = url + "/api/order"
	console.log(url);
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   console.log("success");
	   		displayOrderList(data);
	   },
	   error: handleAjaxError
	});
}



function editOrder(id){
console.log("inside editOrderItem");
var url = getOrderUrl();
url = url+"/api/order/getById";
url = url +"/" + id;

$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   console.log("success");
	   		redirectToOrderItem(data);
	   },
	   error: handleAjaxError
	});


}
//UI DISPLAY METHODS

function displayOrderList(data){
	console.log('Printing user data');
	var $tbody = $('#orders-table').find('tbody');
	$tbody.empty();
	console.log(data.length);
	for(var i in data){
		var e = data[i];
		console.log(e.orderId);
        var buttonHtml  = ' <button onclick="editOrder(' + e.orderId + ')">Edit</button>'
		var row = '<tr>'
		+ '<td>' + e.orderCode + '</td>'
		+ '<td>' + e.status + '</td>'
		+ '<td>' + e.time + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}

}

//INITIALIZATION CODE
function setPage(){
  var role = $("meta[name=role]").attr("content");
  if(role==="standard"){
  var admin = document.getElementById('admin-nav-link');
  admin.style.display = "none";
  var reports = document.getElementById('reports-nav-link');
  reports.style.display = "none";
  }
}


function init(){
	$('#create-order').click(createOrder);
//	$('#refresh-data').click(getUserList);
}

$(document).ready(init);
$(document).ready(getOrderList);
$(document).ready(setPage);

