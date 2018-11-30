$(function(){
	var b = new Base64();
	var  fileDetail = $("#file").val();
	var fileStr = b.decode(fileDetail);
	var fileJson = JSON.parse(fileStr);
	loadFiles(fileJson)
})

function loadFiles(data){
	var myTemplate = Handlebars.compile($("#template").html());
	$(".log-file-list").html(myTemplate(data));
}