<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/include.jsp" %>
	<title>报错页面-404</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/style.css"><!--主要样式-->
</head>
<body>
	<div class="error-page">
		<p class="error-first-p">
			<img width="320" height="191" src="${ctx}/static/images/no-root.png" alt="">
		</p>
		<p class="error-second-p">
			<span>抱歉，您没有该项操作的权限</span>
		</p>
		<!-- <p class="error-third-p">
			<a href="">返回</a>
		</p> -->
		<p class="error-forth-p">
			<span>客服热线：400-826-2277</span>
		</p>
	</div>
</body>
</html>