
function getBaseUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl;
}

//BUTTON ACTIONS
function getReport(event){

console.log("reaching getReport");

   var brand = document.getElementById('inputBrand').value;
   var category = document.getElementById('inputCategory').value;
   var product = document.getElementById('inputProduct').value;

   console.log("brand "+brand);
   console.log("category "+category);
   console.log("product "+product);

   if(brand==="" && category==="" && product===""){
   return false;
   }

	var $form = $("#inventory-report-form");
	var json = toJson($form);
	var url = getBaseUrl();
	url = url + "/api/inventory/inventoryReport";

	console.log(url);

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		displayInventoryList(response);
	   },
	   error: handleAjaxError
	});

	return false;
}



function getInventoryList(){
	var url = getBaseUrl();
	url = url + "/api/inventory/getInventoryAll";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayInventoryList(data);
	   },
	   error: handleAjaxError
	});
}

//UI DISPLAY METHODS

function displayInventoryList(data){
	console.log('Printing Inventory data');
	var $tbody = $('#inventory-report-table').find('tbody');
	$tbody.empty();
	var serial_number = 1;
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + (serial_number++) + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + e.product + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}


//INITIALIZATION CODE
function init(){
	$('#get-inventory-report').click(getReport);
//	$('#refresh-data').click(getUserList);
}

$(document).ready(init);
$(document).ready(getInventoryList);

