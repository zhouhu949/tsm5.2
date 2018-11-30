<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-年度销售规划-年度规划-历史走势</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
<script type="text/javascript" src="${ctx }/static/js/view/plan/year/year_plan_history.js"></script>

<style type="text/css">
.echarts-tooltip{width:150px;overflow:hidden;word-wrap:break-word;word-break:normal;white-space:normal !important;}
</style>

</head>
<body>
<div class='bomp-cen bomp-aspa'>
	<p class="top fl_l">
		<label class="lab fl_l">年份选择：</label>
		<select id="fromYear" class="sel fl_l">
		</select>
		<span class="line fl_l"></span>
		<select id="toYear" class="sel fl_l">
		</select>
		<label class="lab pd_l fl_l">部门选择：</label>
		<select id="groupId" class="sel fl_l">
			<option value="">全部</option>
			<c:forEach items="${groups}" var="group">
				<option value="${group.groupId}">${group.groupName}</option>
			</c:forEach>
		</select>
	</p>
	<div class="tip fl_l">
		<div class="tip-box" id="main" style="width:886px;height:320px;"></div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/thirdparty/echarts-3.3.2/echarts.min.js"></script>

<script type="text/javascript" type="text/javascript">
var currentYear = ${currentYear};

function sel(){
	var fromYearHtml="";
	var toYearHtml="";
	for(var i=0;i<3;i++){
		fromYearHtml+='<option value="'+(currentYear-3-i)+'" '+ (i==3?'selected="true"':'') +' >'+(currentYear-3-i)+'年</option>';
	}
	
	for(var i=0;i<3;i++){
		toYearHtml+='<option value="'+(currentYear-i)+'" '+ (i==0?' selected="true"':'') +'" >'+(currentYear-i)+'年</option>';
	}
	
	$("#fromYear").html(fromYearHtml);
	$("#toYear").html(toYearHtml);
}


window.onload = function(){
	sel();
	
	$("select").change(function(){
		chartFn();
	});
	
	chartFn();
};
</script>
</body>
</html>