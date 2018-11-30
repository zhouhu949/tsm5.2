<%@ page language="java" pageEncoding="UTF-8"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/common/common.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/advancedQuery.css${_v}"/>
		<title>查询条件显示</title>
	</head>

	<body>
		<div class="output-config">
			<div class="conditions-block advanced-conditions">
				<div class="title advancedQuery-everytitle">查询条件：</div>
				<div class="content">${exportSearchContext }</div>
			</div>
		</div>
	</body>
</html>
