<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
	<!--公共样式-->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--主要样式-->
<%-- 	<link rel="stylesheet" type="text/css" href="${ctx}/static/js/idialog/skins/iblue.css"> --%>
<style>
	.reso-new-group label.title {width:85px;}
</style>
<script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
<script type="text/javascript">
	$(function() {
        $("input:text:first").focus();
	
		var groupId = $('#groupId').val();
		var mark = 1;
		$("#btn_save").click(function() {
			if (mark == 1) {
				saveData();
			}
		});

		$("#cancel").click(function() {
			window.parent.closePubDivDialogIframe("classIdDialog");
		});
	});

	function saveData() {
		var groupName = $("#groupName").val();
		var oldGroupName = $("#oldGroupName").val();
		if (groupName == '') {
			$("#errMsg").html("资源分类名称不能为空");
			$("#groupName").focus();
			return false;
		}
/* 		if (groupName == oldGroupName) { // 名称未修改
			parent.closePubDivDialogIframe("classIdDialog");
			return false;
		} */
		var pattern = /[^\x00-\xff]/g;
		if (groupName.replace(pattern, "gg").length > 20) {
			$("#errMsg").html("资源分类名称不能超过十个字");
			$("#groupName").focus();
			return false;
		}
		
		var groupIndex = $("#groupIndex").val();
		if (groupIndex == '') {
			$("#errMsg").html("排序值不能为空");
			$("#groupIndex").focus();
			return false;
		}
		$("#myForm").ajaxSubmit({
			url : '${ctx}/res/group/saveOrUpdateResClass',
			type : 'post',
			error : function(XMLHttpRequest, textStatus) {
				alert("error");
			},
			success : function(data) {
				if (!data.status) {
					$("#errMsg").html(data.errorMsg);
				} else {
					$('#groupId',window.parent.document).val(data.data);
					$('#level',window.parent.document).val(data.level);
					// 刷新主页面
					parent.document.forms[0].submit();
					parent.closePubDivDialogIframe("classIdDialog");
				}
			}
		});
	}
</script>
<body>
	<form method="post" id="myForm" name="myForm">
		<input type="hidden" id="ctPath" value="${ctx}" /> 
		<input type="hidden" name="groupId" value="${resGroup.resGroupId}" id="groupId" />
	    <input type="hidden" id="oldGroupName" value="${resGroup.groupName}" />
		<div class="reso-new-group">
			<label for="" class="title">资源分类名称：</label>
			<input type="text" maxlength= "20" class="edit_person_admin" id="groupName" name="groupName" value="${resGroup.groupName}" />
		</div>
		<div class="reso-new-group">
			<label for="" class="title">排序值：</label>
			<input type="text" class="" id="groupIndex" name="groupIndex" value="${resGroup.groupIndex}" maxLength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" />
		</div>
		<div class="warn-deco">
			<span class="font-color" id="errMsg"></span>
		</div>
		<div class="bomb-btn">
			<label class="bomb-btn-cen"> 
				<a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="###" id="btn_save"><label>确定</label></a> 
				<a class="com-btna bomp-btna cancel-btn fl_l" href="###" id="cancel"><label>取消</label></a>
			</label>
		</div>
	</form>
</body>
