var checkBox = null;

function authPass(){
	$.post(ctx+"/plan/day/authPlan",{"idsStr":checkBox.ids.toString(),"authState":1,"authDesc":null},
			function(data){
		if("success"==data){
			refreshPage();
		}else{
			alert("错误！");
		}
	});
}

function authNoPass(){
	$.post(ctx+"/plan/day/authPlan",{"idsStr":checkBox.ids.toString(),"authState":2,"authDesc":null},
			function(data){
		if("success"==data){
			refreshPage();
		}else{
			alert("错误！");
		}
	});
}

/* 刷新页面*/
function refreshPage(){
	$("form")[0].submit();
}

function button_bind(){
	$('.demoBtn_a').click(function(){
		if(checkBox.getIds()!=null){
			var flag=true;
			var flag1=true;
			for(var i=0;i<checkBox.extend1.length;i++){
				if(checkBox.extend1[i]==1){
					flag=false;
				}else if(checkBox.extend1[i]==2){
					flag1=false;
				}
			}
			if(flag&&flag1){
				pubDivDialog("authPass","确定要执行通过审核操作吗？",authPass);
			}else if(!flag){
				window.top.iDialogMsg("错误！","已审核计划无法继续操作！");
			}else{
				window.top.iDialogMsg("错误！","已驳回计划无法继续操作！");
			}
		}
	});
	$('.demoBtn_b').click(function(){
		if(checkBox.getIds()!=null){
			var flag=true;
			var flag1=true;
			for(var i=0;i<checkBox.extend1.length;i++){
				if(checkBox.extend1[i]==1){
					flag=false;
				}else if(checkBox.extend1[i]==2){
					flag1=false;
				}
			}
			if(flag&&flag1){
				pubDivDialog("authNoPass","确定要执行驳回审核操作吗？",authNoPass);
			}else if(!flag){
				window.top.iDialogMsg("错误！","已审核计划无法继续操作！");
			}else{
				window.top.iDialogMsg("错误！","已驳回计划无法再次驳回！");
			}
		}
	});
	
	$("#confirmGroupTree").click(function(){
		confirmGroupTree();
	});
	
	$("#clearGroupTree").click(function(){
		clearGroupTree();
	});
	

	$("#confirmUser").click(function(){
		confirmUserTree();
	});
	
	$("#clearUser").click(function(){
		clearUserTree();
	});
	
	
	
}

function treeInit(){
	$("#groupTree").tree({
		url:ctx+"/orgGroup/get_group_json",
		checkbox:true,
		onLoadSuccess:function(node,data){
			var oas = $("#groupIds").val();
			if(oas != null && oas != ''){
				$.each(oas.split(','),function(index,obj){
					var n = $("#groupTree").tree("find",obj);
					$("#groupTree").tree("check",n.target).tree("expandTo",n.target);
				});
			}
		}
	});
	
	$("#userTree").tree({
		url:ctx+"/orgGroup/get_group_user_json",
		checkbox:true,
		onLoadSuccess:function(node,data){
			var oas = $("#userAccs").val();
			if(oas != null && oas != ''){
				$.each(oas.split(','),function(index,obj){
					var n = $("#userTree").tree("find",obj);
					$("#userTree").tree("check",n.target).tree("expandTo",n.target);
				});
			}
		}
	});
}

function confirmUserTree(){
	var nodes = $('#userTree').tree('getChecked', 'checked');
	var accArray = new Array();
	var idArray = new Array();
	$.each(nodes,function(index,obj){
		var type = obj.attributes.type;
		if(type == "M"){
			accArray.push(obj.id);
			idArray.push(obj.user_id);
		}
	});
	$("#userAccs").val(accArray.toString());
	$("#userIds").val(idArray.toString());
}

function clearUserTree(){
	var nodes = $('#userTree').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#userTree').tree("uncheck",obj.target);
	});
	$("#userAccs").val('');
	$("#userIds").val('');
}

function confirmGroupTree(){
	var nodes = $('#groupTree').tree('getChecked', 'checked');
	var idArray = new Array();
	$.each(nodes,function(index,obj){
		idArray.push(obj.id);
	});
	$("#groupIds").val(idArray.toString());
}

function clearGroupTree(){
	var nodes = $('#groupTree').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#groupTree').tree("uncheck",obj.target);
	});
	$("#groupIds").val('');
}

function authState(value){
	$("#authState").val(value);
}

/* 初始化*/
function initPage(){
	treeInit();
}

function init(){
	checkBox= new CheckBox("checkAll","chk_",'','authState');
	button_bind();
	initPage();
}

$(document).ready(function() {
	init();
});
