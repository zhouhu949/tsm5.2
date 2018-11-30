<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html>
<head>
    <meta charset="UTF-8">
    <title>邮箱验证</title>
    <%@ include file="/common/include.jsp" %>
    <style type="text/css">
		body{background-color:#e4e4e4 !important;}
	</style>
<body>
	<c:choose>
		<c:when test="${re eq '-1' || re eq '-2' }">
			<div class="hyx-rtv">
				<div class="tit">您的邮箱绑定验证失败</div>
				<p class="logo"></p>
				<p class="tip">验证信息无效</p>
			</div>
		</c:when>
		<c:when test="${re eq '0' }">
			<div class="hyx-rtv hyx-ve">
				<div class="tit">您的验证信息已经过期</div>
				<p class="logo"></p>
				<p class="tip">请重新登录客户端绑定您的邮箱</p>
			</div>
		</c:when>
		<c:otherwise>
			<div class="hyx-rtv hyx-sv">
				<div class="tit">电子邮箱验证成功</div>
				<p class="logo"></p>
				<p class="tip">您的电子邮箱<a href="mailto:${re }">${re }</a>已验证成功</p>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>