function setPage(){
var role = $("meta[name=role]").attr("content")
console.log(role);
if(role==="standard"){
  document.getElementById('reportsDropDown').style.display = 'none';
}
}


$(document).ready(setPage);