<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html >
<head>
    <meta charset="UTF-8">
    <title>个人中心页面-右侧销售成长</title>
    <%@ include file="/common/include-home-cut-version.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/personal-center.css${_v}"/>
</head>
<body>
	<div class="personal-center-right-head-box">
		<iframe src="${ctx }/main/sale/levelGrowth" width="100%" height="100%" id="iframepage" frameborder="0" scrolling="auto" style="overflow-x:hidden; overflow-y:hidden;" marginheight="0" marginwidth="0"></iframe>
	</div>
</body>
</html>
