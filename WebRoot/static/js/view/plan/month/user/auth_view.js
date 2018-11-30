var checkBox = null;

function authPass(){
	$.post(ctx+"/plan/month/user/auth",{"planIdsStr":checkBox.ids.toString(),"authState":2,"context":null},
			function(data){
		if("success"==data.status){
			refreshPage();
		}else{
			window.parent.warmDivDialog("erro","出错!");
		}
	});
}

function authNoPass(){
	$.post(ctx+"/plan/month/user/auth",{"planIdsStr":checkBox.ids.toString(),"authState":0,"context":null},
			function(data){
		if("success"==data.status){
			refreshPage();
		}else{
			window.parent.warmDivDialog("erro","出错!");
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
				if(checkBox.extend1[i]==2){
					flag=false;
				}else if(checkBox.extend1[i]==0){
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
				if(checkBox.extend1[i]==2){
					flag=false;
				}else if(checkBox.extend1[i]==0){
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
	
	$("#confirmUserTree").click(function(){
		confirmUserTree();
	});
	
	$("#clearUserTree").click(function(){
		clearUserTree();
	});
	
	$("#confirmAuthUserTree").click(function(){
		confirmAuthUserTree();
	});
	
	$("#clearAuthUserTree").click(function(){
		clearAuthUserTree();
	});
}

function treeInit(){
	$("#authUserTree").tree({
		url:ctx+"/orgGroup/get_group_user_json",
		checkbox:true,
		onLoadSuccess:function(node,data){
			var oas = $("#authUserAccs").val();
			if(oas != null && oas != ''){
				$.each(oas.split(','),function(index,obj){
					var n = $("#authUserTree").tree("find",obj);
					$("#authUserTree").tree("check",n.target).tree("expandTo",n.target);
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

function confirmAuthUserTree(){
	var nodes = $('#authUserTree').tree('getChecked', 'checked');
	var accArray = new Array();
	var idArray = new Array();
	$.each(nodes,function(index,obj){
		var type = obj.attributes.type;
		if(type == "M"){
			accArray.push(obj.id);
			idArray.push(obj.user_id);
		}
	});
	$("#authUserAccs").val(accArray.toString());
	$("#authUserIds").val(idArray.toString());
}

function clearAuthUserTree(){
	var nodes = $('#authUserTree').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#authUserTree').tree("uncheck",obj.target);
	});
	$("#authUserIds").val('');
	$("#authUserAccs").val('');
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
