<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/include.jsp" %>
	<title>404 - 页面不存在</title>
</head>

<body>
<%--<%response.setStatus(200);%>
	<h2>404 - 页面不存在.</h2>
	<p><a href="<c:url value="/"/>">返回首页</a></p>--%>
<div class="error-page">
	<p class="error-first-p">
		<img width="282" height="191" src="${ctx}/static/images/error_page404.png" alt="">
	</p>
	<p class="error-second-p">
		<span>OH&nbsp;&nbsp;HO！页面君不见了，估计是上门拜访客户去了...</span>
	</p>
	<%--<p class="error-third-p">
		<a href="${ctx}/main">返回</a>
	</p>--%>
	<p class="error-forth-p">
		<span>客服热线：400-826-2277</span>
	</p>
</div>
</body>
</html>