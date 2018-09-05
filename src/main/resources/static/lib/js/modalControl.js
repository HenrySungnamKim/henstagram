var modal = document.getElementById('myModal');
var btn = document.getElementById("modalButton");
var write = document.getElementById("write");

//var span = document.getElementsByClassName("close")[0];

btn.onclick = function() {
	modal.style.display = "block";
}

write.onclick= function(){
	modal.style.display = "none";
	alert("글이 작성되었습니다.");
}
//span.onclick = function() {
//	modal.style.display = "none";
//}

window.onclick = function(event) {
	if (event.target == modal) {
		modal.style.display = "none";
	}
}
