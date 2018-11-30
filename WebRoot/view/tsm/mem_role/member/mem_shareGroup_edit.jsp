<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css${_v}"><!--弹框样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/skin/default/color.css"><!--换肤样式-->
<!--本页面样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/icon.css"><!--树形结构样式-->
<!--公共js-->
<style type="text/css">
 .tree-title{height:18px !important;line-height:18px !important;}
 .tree-hit{margin-top:0;}
 .tree li div.tree-node{height:18px;line-height:18px;}
</style>
<script type="text/javascript">

	$(function(){
		$("#tt1").tree({
			url:ctx+"/orgGroup/get_all_group_json",
			checkbox:true,
			onLoadSuccess:function(node,data){
				$("#tt1").tree("collapseAll");
				for(var i=0;i<data.length;i++){
					$("#tt1").tree("expand",data[i].target);
				}
			}
		});

		$("#save").click(function(){
			save();
		});

		$("#cancel").click(function(){
			setTimeout('closeParentPubDivDialogIframe("batch_edit_power_");',10);
		});
	});

	function save(){
		var nodes = $('#tt1').tree('getChecked', 'checked');
		var groupArray = new Array();
		$.each(nodes,function(index,obj){
			groupArray.push(obj.id);
		});
		var params ={"userIds": $("#userIds").val(),"userAccounts": $("#userAccounts").val(),"newShareGroupIds":groupArray.toString()};
		$.post(ctx+"/auth/user/editShareGroup", params,function(data){
			if(data.status){
				window.top.iDialogMsg("提示",data.data);
				setTimeout('window.parent.frames[0].loadData();',300);
				setTimeout('closeParentPubDivDialogIframe("batch_edit_power_");',500);
			}else{
				window.top.iDialogMsg("提示",data.errorMsg)
			}
		}); 

	};

</script>
</head>
<body>
<div class="bomp-cen main" data-type="search-tree">
	<input type ="hidden" id="userIds" value="${userIds}" />
	<input type ="hidden" id="userAccounts" value="${userAccounts}" />
    <ul id="tt1" style="width:300px;height:150px;overflow-y:auto;"></ul>
    <div class="bomb-btn">
        <label class="bomb-btn-cen">
        	<a id="save" href="javascript:;" class="com-btna com-btna-sty fl_l"><label>保存</label></a>
        	<a id="cancel" href="javascript:;" class="com-btna fl_l"><label>取消</label></a>
        </label>
    </div>
</div>
</body>
</html>
