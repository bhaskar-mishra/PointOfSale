
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
	   		getBrandCategoryList();
	   },
	   error: handleAjaxError
	});

	return false;
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
	var url = getBrandCategoryUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getBrandCategoryList();
	   },
	   error: handleAjaxError
	});
}

//UI DISPLAY METHODS

function displayBrandCategoryList(data){
	console.log('Printing user data');
	var $tbody = $('#brand-category-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button onclick="deleteBrandCategory(' + e.id + ')">delete</button>'
		buttonHtml += ' <button onclick="displayEditUser(' + e.id + ')">edit</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}


//INITIALIZATION CODE
function init(){
	$('#add-brand-category').click(addBrandCategory);
//	$('#refresh-data').click(getUserList);
}

$(document).ready(init);
$(document).ready(getBrandCategoryList);

