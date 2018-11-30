<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>我的计划-上报计划</title>

<!--本页面样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css${_v}"><!--主要样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/core.css${_v}"/><!--弹框插件样式-->

<script type="text/javascript">
var sudId="${sudId}";
$(function(){
    $(".sure-btn").click(function(){
    	$.post(ctx+"/plan/day/reportPlan",{"id":sudId},function(data){
			if("success"==data){
				window.parent.refreshPage(window.parent.date);
			}else{
				window.top.iDialogMsg("错误","上报计划失败，请联系管理员!");
			}
    	}); 	
	});
	$(".cancel-btn").click(function(){
		closeParentPubDivDialogIframe('reportPlanWindow');
	});
});
</script>
</head>
<body> 
<div class='bomp-cen bomp-mpa'>
	<div class="bomp-tip-a fl_l">
		<p><fmt:formatDate value="${plan.planDate}" pattern="MM月dd日" />工作计划如下：</p>
		<p>资源数：${plan.resourceCount }个，意向客户数：${plan.willcustCount }个，签约客户数：${plan.signcustCount }个</p> 
        <p>提交后，您无法调整该计划，您确定要提交该日计划吗？</p>
	</div>
	<div class='bomb-btn' style="margin-top:35px;">
		<label class='bomb-btn-cen'>
			<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>确定</label></a>
			<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
		</label>
	</div>
</div>
</body>
</html>