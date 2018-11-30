<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>报表详情页</title>
     <link rel="stylesheet" type="text/css" href="${ctx}/static/css/custReport.css${_v}" />
    <script type="text/javascript" src="${ctx}/static/js/echarts-3.3.2/echarts.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/view/report/custReportDetail.js${_v}"></script>
</head>
<body>
<div class="custReport-detail-head">
	<div class="custReport-detail-head-item custReport-detail-head-item-title">${customReportName }</div>
	<div class="custReport-detail-head-item">
		<label>数据范围：</label>
		<a href="javascript:;" class="hover-see-detail">
			查看
			<div class="hover-see-detail-box">
				<c:forEach items="${list}" var="item">
					<div class="hover-see-detail-box-item">
						<span>${item.name }</span>
						<span>${item.match }</span>
						<span title="${item.value }">${item.value }</span>
					</div>
				</c:forEach>
			</div>
		</a>
	</div>
	<div class="custReport-detail-head-item custReport-detail-head-item-sharebox" title='已分享：<c:forEach items="${shareName}" var="shares">${shares }，</c:forEach>'>
		<label>已分享：</label><c:forEach items="${shareName}" var="shares">${shares }，</c:forEach>
	</div>
</div>

<input type="hidden" id="customReportId" value ="${customReportId }">
<input type="hidden" id="isDouble" value ="${isDouble }">
<input type="hidden" id="customReportName" value ="${customReportName }">
<div class="static-analy-line1" id="main01"></div>
<div class="table-padding-box">
	<div class="com-table hyx-fur-table com-mta fl_l" style="display:block;">
		<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
			<thead>
				<tr class="sty-bgcolor-b" role="head">
					<th><span class="sp sty-borcolor-b">表头1</span></th>
					<th><span class="sp sty-borcolor-b">表头2</span></th>
					<th><span class="sp sty-borcolor-b">表头3</span></th>
					<th><span class="sp sty-borcolor-b">表头4</span></th>
			   		<th><span class="sp sty-borcolor-b">表头5</span></th>
				</tr>
			</thead>
			<tbody>
				<tr class="no_data_tr"><td></td></tr>
				<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
				<tr class="no_data_tr"><td></td></tr>
				<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
				<tr class="no_data_tr"><td></td></tr>
				<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
				<tr class="no_data_tr"><td></td></tr>
				<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
				<tr class="no_data_tr"><td></td></tr>
				<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
			</tbody>
		</table>
	</div>
</div>
</body>
</html>
