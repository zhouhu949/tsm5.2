<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--主要样式-->
<title>系统设置-系统属性设置-变更分组</title>

<script type="text/javascript">
var opera = true;
$(function(){
     $(".sure-btn").click(function(){
    	 $('#setForm').ajaxSubmit({
			url : '${ctx}/opt/set/group/changeGroup',
			type : 'post',
			error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
			success : function(data) {	
				if(data == 0){			
					window.top.iDialogMsg("提示","成功！");
					setTimeout('window.parent.frames[0].document.forms[0].submit();',1000);	
					setTimeout('closeParentPubDivDialogIframe("alert_sys_set_group");',1000);					
				}else{
					window.top.iDialogMsg("提示","失败！");
				}
			}
		}); 
	});
     $(".cancel-btn").click(function(){
 		closeParentPubDivDialogIframe('alert_sys_set_group');
 	});
});
</script>
</head>
<body> 
<div class='bomp-cen'>
	<form id="setForm" method="post">
		<input type="hidden" name="ids" id="ids" value="${ids}">
		<input type="hidden" name="itemCode"  id="itemCode" value="${itemCode}">
		<div class='bomp-p' id="bomp_error_groupName">
			<label class='lab_a fl_l'><b class="bomp-red">*</b>选择分组：</label>
			<select id="group_c" name="groupId">
				<c:forEach items="${items}" var="group">				
					<option value="${group.groupId }" >${group.groupName}</option>			
				</c:forEach>			
			</select>			
		</div>
		<div class='bomb-btn'>
			<label class='bomb-btn-cen'>
				<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>保存</label></a>
				<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
			</label>
		</div>
	</form>
</div>
</body>
</html>