
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
		var time = e.time;
		if(e.time===null){
		time = 'NA';
		}
		else
		{
		   time = dateToISOLikeButLocal(new Date(e.time*1000));
		}

        var buttonHtml  = ' <button type="button" class="btn btn-primary" onclick="editOrder(' + e.orderId + ')">View</button>'
		var row = '<tr>'
		+ '<td>' + e.orderCode + '</td>'
		+ '<td>' + e.status + '</td>'
		+ '<td>' + time + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}

}

function dateToISOLikeButLocal(date){
//date = date.toFixed(0);
console.log(date);
 const offsetMs = date.getTimezoneOffset()*60*1000;
 const msLocal = date.getTime()-offsetMs;
 const dateLocal = new Date(msLocal);
 const iso = dateLocal.toISOString();
 const isoLocal = iso.slice(0,10)+" "+iso.slice(11,19);
 return isoLocal;
}

//INITIALIZATION CODE
function setPage(){
  var role = $("meta[name=role]").attr("content");
  if(role==="standard"){
  document.getElementById('reportsDropDown').style.display = 'none';
   document.getElementById('userDropDownOption').style.display = 'none';
  }
}


function init(){
	$('#create-order').click(createOrder);
//	$('#refresh-data').click(getUserList);
}

$(document).ready(init);
$(document).ready(getOrderList);
$(document).ready(setPage);

