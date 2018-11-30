var checkBox = null;

/*数据标签*/
function dataLable(id){
	var array=["计划联系资源","转签约","意向增进","转公海","意向无变化","未联系"];
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
	$("#willcustForm").submit();
}

/* 刷新页面*/
function refreshPage(){
	$("#willcustForm").submit();
}

function button_bind(){
	$('.demoBtn_a').click(function(){
		window.parent.addWillWindow(dateTime,0);
	});
}

function init(){
	button_bind();
	checkBox= new CheckBox("checkAll","chk_","custId","state");
}

$(document).ready(function() {
	init();
});