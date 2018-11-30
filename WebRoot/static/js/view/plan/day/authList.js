var ids = new Array();
function auth(state){
	ids = new Array();
	var boxs =$("input[name='selectPlan'][checked='checked']:gt(0)");
	for(var i=0;i<boxs.length;i++){
		ids.push($(boxs[i]).val());
	}
	if(ids.length!=0){
		$.post(ctx+"/plan/day/authPlan",{"idsStr":ids.toString(),"authState":state,"authDesc":"authDesc"},function(data){
			if(data=="success"){
				$.messager.alert('成功','更新状态成功！');
			}else{
				$.messager.alert('失败','id参数为空！');
			}
		});
	}else{
		$.messager.alert('警告','请选择至少一条记录！');
	}
}


function buttonBind(){
	$("#authPass").click(function (){
		auth(1);
	});
	
	$("#authNoPass").click(function (){
		auth(2);
	});
}

function init(){
	var selector ="input[name='selectPlan']";
	$(selector+":first").click(function (){
		var checked = $(this).attr("checked");
		check(selector+":gt(0)",checked);
	});
};

function check(selector,checked){
	var array = $(selector);
	for(var i=0;i<array.length;i++){
		if("checked"==checked){
			$(array[i]).attr("checked",true);
		}else{
			$(array[i]).attr("checked",false);
		}
	};
}

$(document).ready(function() {
	init();
	buttonBind();
});
