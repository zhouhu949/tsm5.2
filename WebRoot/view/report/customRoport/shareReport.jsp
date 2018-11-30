<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
 <%@ include file="/common/include.jsp" %>
    <title>自定义报表-我的报表</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/custReport.css${_v}" />
    <script type="text/javascript" src="${ctx}/static/js/echarts-3.3.2/echarts.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/view/report/custReport.js${_v}"></script>
</head>
<body>
<input type="hidden" id="isShare" value="1">
<div class="sales-statis-conta">
	<div class="year-sale-play">
		<ul class="ann-sale-plan clearfix" style="width:382px">
			<li class="li_first"  rel="${ctx }/custom/report/toMyCustomReportList">我的报表</li>
			<li class="li_last select" >分享给我的报表</li>
		</ul>
	</div>
	<div class="cust-report-picbox clearfix">
		<%-- <div class="cust-report-picbox-item" >
			<div class="picbox-top-title"></div>
			<img src="${ctx }/static/images/cust_report_single.png">
			<div class="picbox-bottom-option">
				<span class="picbox-bottom-edit">编辑</span>
				<span class="picbox-bottom-share">分享</span>
				<span class="picbox-bottom-delete">删除</span>
			</div>
		</div> --%>
	</div>
</div>
<script type="text/x-handlebars-template" id="myreportTemplate">
{{#each list}}
	<div class="cust-report-picbox-item" data-reportid={{customReportId}} data-name="{{customReportName}}" data-double="{{isDouble}}">
			<div class="picbox-top-title" title="{{customReportName}}">{{customReportName}}</div>
			{{#compare isDouble "==" 2}}
				<img src="${ctx }/static/images/cust_report_double.png">
			{{else}}
				<img src="${ctx }/static/images/cust_report_single.png">
			{{/compare}}
		</div>
{{/each}}
</script>
</body>
</html>
