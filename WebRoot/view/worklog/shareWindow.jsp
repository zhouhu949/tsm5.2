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
var type = ${type};

var shareWorkId = "${wliId}";
	$(function(){
		$("#tt1").tree({
			url:ctx+"/orgGroup/get_all_group_user_json",
			checkbox:true,
			onLoadSuccess:function(node,data){
				$("#tt1").tree("collapseAll");
				for(var i=0;i<data.length;i++){
					$("#tt1").tree("expand",data[i].target);
				}
				var userIdsStr = $("#shareUserAccs").val();
				if(userIdsStr != null && userIdsStr != ''){
					$.each(userIdsStr.split(','),function(index,obj){
						var n = $("#tt1").tree("find",obj);
						$("#tt1").tree("check",n.target).tree("expandTo",n.target);
					});
				}
			}
		});

		$("#save").click(function(){
			save();
		});

		$("#cancel").click(function(){
			setTimeout('closeParentPubDivDialogIframe("shareWindow");',10);
		});
	});

	function save(){
		var nodes = $('#tt1').tree('getChecked', 'checked');
		var accArray = new Array();
		var idArray = new Array();
		$.each(nodes,function(index,obj){
			var type = obj.attributes.type;
			if(type == "M"){
				accArray.push(obj.id);
				idArray.push(obj.user_id);
			}
		});

		var params ={"wliId": shareWorkId,"accArrayStr":accArray.toString(),"idArrayStr":idArray.toString(),"type":type};
		$.post(ctx+"/worklog/share/save", params,function(data){
			if(data.status=="success"){
				if(type==1){
					window.parent.setShareValue(shareWorkId,data.msg);
				}
				setTimeout('closeParentPubDivDialogIframe("shareWindow");',10);
			}else if(data.status=="noChange"){
				setTimeout('closeParentPubDivDialogIframe("shareWindow");',10);
			}else{
				if(type==0){
					window.parent.wrongDivDialog("wrong","设置默认分享日志失败");
				}else{
					window.parent.wrongDivDialog("wrong","分享日志失败");
				}
			}
		});

	};

	var unCheckTree=function(){
		var nodes = $('#tt1').tree('getChecked', 'checked');
		$.each(nodes,function(index,obj){
			 $('#tt1').tree("uncheck",obj.target);
		});
	};
</script>
</head>
<body>
<div class="bomp-cen main" data-type="search-tree">
	<input type ="hidden" id="shareUserAccs" value="${shareUserAccs}" />
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
