<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<%@ include file="/common/include.jsp"%> 
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
<head>
<script type="text/javascript">
$(function(){
    $("input:text:first").focus();

    $(".sure-btn").click(function(){
    	if(checkIsNull()){
        	$('#dialogForm').ajaxSubmit({
    			url : ctx+'/sys/smsTemp/operaSmsTempGroup.do',
    			type : 'post',
    			error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
    			success : function(data) {	
    				if(data == 0){			
    					window.top.iDialogMsg("提示","保存成功！");
    					setTimeout('window.parent.document.forms[0].submit();',1000);					
    				}else{
    					window.top.iDialogMsg("提示","保存失败！");
    				}
    			}
    		});	
    	}
	});
	$(".cancel-btn").click(function(){
		closeParentPubDivDialogIframe('sms_temp_set_a');
	});
});

//验证
function checkIsNull(){
	if($("#groupName").val() == null || $.trim($("#groupName").val())==""){
		$("#err_groupName").text("分类名称不能为空！");
		return false;
	}
	return true;
}
</script>
</head>
<body>
<form id="dialogForm" method="post">
	<input type="hidden" name="tsgId" value="${item.tsgId}">
	<div class="reso-new-group fl_l">
		<label for="">分类名称：</label>
		<input type="text" id="groupName" name="groupName" value="${item.groupName }" onkeyup="$('#err_groupName').text('')">
	</div>
	<p class="warn-deco fl_l"><span clas="font-color" id="err_groupName"></span></p>
	<div class="bomb-btn">
		<label class="bomb-btn-cen">
			<a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="javascript:;"><label>确定</label></a>
			<a class="com-btna bomp-btna cancel-btn fl_l" href="javascript:;"><label>取消</label></a>
		</label>
	</div>
</form>
</body>
</html>