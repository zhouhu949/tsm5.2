<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
	<!--公共样式-->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--主要样式-->
<%-- 	<link rel="stylesheet" type="text/css" href="${ctx}/static/js/idialog/skins/iblue.css"> --%>
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
			window.parent.closePubDivDialogIframe("groupIdDialog");
		});
	});

	function saveData() {
		var groupName = $("#groupName").val();
		var pid = $("#pid").val();
		var oldGroupName = $("#oldGroupName").val();
		if (groupName == '') {
			$("#errMsg").html("分组名称不能为空");
			$("#groupName").focus();
			return false;
		}

/* 		if (groupName == oldGroupName) { // 名称未修改
			parent.closePubDivDialogIframe("groupIdDialog");
			return false;
		} */
		var pattern = /[^\x00-\xff]/g;
		if (groupName.replace(pattern, "gg").length > 20) {
			$("#errMsg").html("分组名称不能超过十个字");
			$("#groupName").focus();
			return false;
		}
		$("#myForm").ajaxSubmit({
			url : '${ctx}/res/group/saveOrUpdateGroup',
			type : 'post',
			error : function(XMLHttpRequest, textStatus) {
			},
			success : function(data) {
				if (!data.status) {
					$("#errMsg").html(data.errorMsg);
				} else {
					$('#groupId',window.parent.document).val(data.data);
					$('#level',window.parent.document).val(data.level);
					// 刷新主页面
					parent.document.forms[0].submit();
					parent.closePubDivDialogIframe("groupIdDialog");
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
	    	<label for="" style="width:90px;">选择资源分类：</label>
	    	<select style="width:150px;height:25px;line-height:25px;" id = "pid" name = "pid">
	    		<c:forEach items="${resClassList }" var="resClass">
	    			<option value="${resClass.resGroupId }" <c:if test="${resClass.resGroupId eq pid}">selected</c:if>>${resClass.groupName }</option>
	    		</c:forEach>
	    	</select>
	    </div>
		<div class="reso-new-group">
			<label for="" style="width:90px;">分组名称：</label>
			<input type="text" maxlength="20" class="edit_person_admin" id="groupName" name="groupName" value="${resGroup.groupName}" />
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
