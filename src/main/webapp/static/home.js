function setPage(){

var role = $("meta[name=role]").attr("content")
if(role==="standard"){
 var admin = document.getElementById('admin-nav-link');
 admin.style.display = "none";
}
}


$(document).ready(setPage);