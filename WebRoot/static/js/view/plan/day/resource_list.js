var checkBox = null;

function setPriori(){
	$.post(ctx+"/plan/day/resource/changeCustLevel",{"custIdsStr":checkBox.custIds.toString(),"level":"1"},
			function(data){
			if("success"==data){
				refreshPage();
			}else{
				window.top.wrongDivDialog("erro", data);
			}
	});
}
function cancelPriori(){
	$.post(ctx+"/plan/day/resource/changeCustLevel",{"custIdsStr":checkBox.custIds.toString(),"level":"0"},
			function(data){
		if("success"==data){
			refreshPage();
		}else{
			window.top.wrongDivDialog("erro", data);
		}
	});
}

function giveUp(){
	$.post(ctx+"/plan/day/resource/giveUp",{"custIdsStr":checkBox.custIds.toString()},
			function(data){
		if(data.status){
			refreshPage();
		}else{
			window.top.iDialogMsg("错误", data.errorMsg);
		}
	});
}

/*数据标签*/
function dataLable(id){
	var array=["计划联系资源","临时计划资源","转签约","转意向","转公海","联系无变化","未联系"];
	//清除其他查询条件
	$("#queryStr").val(null);
	$("#dataLableSelect").val(id);
	if(id==0){
		$("#contactResult").val(null);
		//$("#state").val(null);
		$("#tempRes").val(false);
	}else if(id==1){
		$("#contactResult").val(null);
		//$("#state").val(1);
		$("#tempRes").val(true);
	}else{
		$("#contactResult").val(array[id]);
		//$("#state").val(null);
		$("#tempRes").val(false);
	}
	$("#resourceForm").submit();
}
/* 刷新页面*/
function refreshPage(){
	$("#resourceForm").submit();
}

function button_bind(){
	$('.demoBtn_a').click(function(){
		if(checkBox.getIds()!=null){
			var flag=true;
			for(var i=0;i<checkBox.extend2.length;i++){
				if(checkBox.extend2[i] ==1){
					flag =false;
				}
			}
			if(flag){
				window.parent.pubDivDialog("cfmSetPriori","确定要执行设置优先操作吗？",setPriori);
			}else{
				window.parent.warmDivDialog("warm",'已设置优先资源无法继续设置优先！');
			}
		}
	});
	$('.demoBtn_b').click(function(){
		if(checkBox.getIds()!=null){
			var flag=true;
			for(var i=0;i<checkBox.extend2.length;i++){
				if(checkBox.extend2[i] ==0){
					flag =false;
				}
			}
			if(flag){
				window.parent.pubDivDialog("cfmSetPriori","确定要执行取消优先操作吗？",cancelPriori);
			}else{
				window.parent.warmDivDialog("warm",'未设置优先资源无法取消优先！');
			}
		}
	});
	
	$('.demoBtn_c').click(function(){
		window.parent.addResWindow(dateTime,0);
	});
	
	$('.demoBtn_e').click(function(){
		if(checkBox.getIds()!=null){
			window.parent.pubDivDialog("cfmSetPriori","确定要执行放入公海操作吗？",giveUp);
		}
	});
	
	$('.demoBtn_g').click(function(){
		window.parent.addResWindow(dateTime,1);
	});
}

function init(){
	button_bind();
	checkBox= new CheckBox("checkAll","chk_","custId","state","resCustLevel");
}

$(document).ready(function() {
	init();
});
