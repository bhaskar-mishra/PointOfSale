
function getBaseUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl;
}

//BUTTON ACTIONS
function getReport(event){

console.log("reaching getReport");

   var brand = document.getElementById('inputBrand').value;
   var category = document.getElementById('inputCategory').value;

   console.log("brand "+brand);
   console.log("category "+category);

   if(brand==="" && category===""){
   return false;
   }

	var $form = $("#brands-report-form");
	var json = toJson($form);
	var url = getBaseUrl();
	url = url + "/api/brands/brandsReport";

	console.log(url);

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		displayBrandCategoryList(response);
	   },
	   error: handleAjaxError
	});

	return false;
}



function getBrandCategoryList(){
	var url = getBaseUrl();
	url = url + "/api/brand";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrandCategoryList(data);
	   },
	   error: handleAjaxError
	});
}

//UI DISPLAY METHODS

function displayBrandCategoryList(data){
	console.log('Printing user data');
	var $tbody = $('#brand-report-table').find('tbody');
	$tbody.empty();
	var serial_number = 1;
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + (serial_number++) + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}


//INITIALIZATION CODE
function init(){
	$('#get-brands-report').click(getReport);
//	$('#refresh-data').click(getUserList);
}

$(document).ready(init);
$(document).ready(getBrandCategoryList);

