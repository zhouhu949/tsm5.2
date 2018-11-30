<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-月度计划（团队编辑）-查看页面（已执行）-历史计划走势</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
<style type="text/css">
.echarts-tooltip{width:150px;overflow:hidden;word-wrap:break-word;word-break:normal;white-space:normal !important;}
</style>
<script type="text/javascript" src="${ctx}/static/js/my97datepicker/WdatePicker.js"></script><!--选择年月日期插件-->
<script type="text/javascript" src="${ctx}/static/js/view/worklog/dateUtils.js"></script>
<script type="text/javascript" src="${ctx }/static/js/view/plan/month/team/history_window.js"></script>

</head>
<body> 
<div class='bomp-cen bomp-aspa'>
	<p class="top fl_l">
		<input id="groupId" value="${groupId}" type="hidden">
        <input id="fromDate" type="text" value="<fmt:formatDate value="${fromDate}" pattern="yyyy-MM"/> " class="sel fl_l" onclick="WdatePicker({maxDate:'%y-%M',dateFmt:'yyyy-MM',isShowClear:false,readOnly:true,onpicked:pickerTime})" />
		<span class="line fl_l"></span>
		<input id="toDate" type="text" value="<fmt:formatDate value="${toDate}" pattern="yyyy-MM"/> " class="sel fl_l" onclick="WdatePicker({maxDate:'%y-%M',dateFmt:'yyyy-MM',isShowClear:false,readOnly:true,onpicked:pickerTime})" />
		<select class="sel fl_l" style="margin-left:50px;">
            <option value="will">新增意向数</option>
            <option value="sign">新增签约数</option>
			<option value="money">回款金额</option>
		</select>
	</p>
	<div class="tip fl_l">
		<div class="tip-box" id="main" style="width:886px;height:320px;"></div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/thirdparty/echarts-3.3.2/echarts.min.js"></script>
<script type="text/javascript" type="text/javascript">
window.onload = function(){
	$("select").change(function(){
		chart();
	});
	chartFn();
};
</script>
</body>
</html>