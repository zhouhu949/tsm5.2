<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--主要样式-->
<title>客户跟进-跟进时间调整</title>

<script type="text/javascript">
var isSubmit = true;
$(function(){
	// 更改
    $(".sure-btn").click(function(){
    	var custIds = $("#custIds", window.parent.document).val();
    	var followIds = $("#followIds", window.parent.document).val();
    
    	$("#resCustIds").val(custIds);
    	$("#custFollowIds").val(followIds);
    	var nextActionDate = $("#nextActionDate").val();
    	if(!(nextActionDate != null && nextActionDate !="")){
    		closeParentPubDivDialogIframe('alert_cust_follow_a');
    	}else{
    		if(isSubmit && custIds != null && custIds !="" 
    			&& followIds != null && followIds != ""){
			isSubmit = false;
			$("#dialogForm").ajaxSubmit({
				url :'${ctx}/cust/custFollow/List/setFollowNextActionDate',
				type : 'post',
				dataType : 'json',
				error : function(XMLHttpRequest, textStatus){alert(textStatus);},
				success : function(data) {
					if(data == 0){
						// 默认刷新主页面
						window.top.iDialogMsg("提示","保存成功！");
						$("#query", window.parent.document).click();
						closeParentPubDivDialogIframe('alert_cust_follow_a');				
					}else{
						// 提示失败
						window.top.iDialogMsg("提示","保存失败！");
					}
				}
			});
		}
    }   	
	});
    
	$(".cancel-btn").click(function(){
		closeParentPubDivDialogIframe('alert_cust_follow_a');
	});
    
});
</script>
</head>
<body> 
<form id="dialogForm" method="post">
<input type="hidden" name="custFollowIds" id="custFollowIds"> <!-- 跟进ID -->
<input type="hidden" name="resCustIds" id="resCustIds"> <!-- 资源ID -->
<div class='bomp-cen' id='alert_b'>
	<div class='cust-foll-time'>
		<label class='lab_a fl_l'>变更跟进时间为：</label>
		<p class='p_a fl_l'><input type='text' name="nextActionDate"  id="nextActionDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})" class='ipt_a' id="d1" readOnly="readonly"/><i class='i_a'></i>
			<span class="error" id="error_nextActionDate"></span>
		</p>
	</div>
	<div class='bomb-btn'style="margin-top:50px;">
		<label class='bomb-btn-cen'>
			<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>确定</label></a>
			<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
		</label>
	</div>
</div>
</form>
</body>
</html>