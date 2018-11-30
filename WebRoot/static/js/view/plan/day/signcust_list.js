var checkBox = null;

/*数据标签*/
function dataLable(id){
	var array=["计划联系资源","续签","转沉默","联系无变化","未联系"];
	//清除其他查询条件
	$("#queryStr").val(null);
	$("#dataLableSelect").val(id);
	if(id==0){
		$("#contactResult").val(null);
		$("#state").val(null);
	}else{
		$("#contactResult").val(array[id]);
		$("#state").val(null);
	}
	$("#signcustForm").submit();
}

/* 刷新页面*/
function refreshPage(){
	$("#signcustForm").submit();
}

function button_bind(){
	$('.demoBtn_a').click(function(){
		window.parent.addSignWindow(dateTime,0);
	});
}

function init(){
	button_bind();
	checkBox= new CheckBox("checkAll","chk_","custId","state");
}

$(document).ready(function() {
	init();
});