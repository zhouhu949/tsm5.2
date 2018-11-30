<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%--<%response.setStatus(200);%>
<%
    Throwable ex = null;
    if (exception != null)
        ex = exception;
    if (request.getAttribute("javax.servlet.error.exception") != null) {
        ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
    }
%>--%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>fail - 免登失败</title>
</head>
<body>
<div class="error-page">
    <p class="error-first-p">
        <img width="282" height="191" src="${ctx}/static/images/error_page500.png" alt="">
    </p>
    <p class="error-second-p">
        <span>OH&nbsp;&nbsp;HO！登录失败..${error}.</span>
    </p>
   <%-- <p class="error-third-p">
        <a href="${ctx}/main">返回</a>
    </p>--%>
    <p class="error-forth-p">
        <span>客服热线：400-826-2277</span>
    </p>
</div>
</body>
</html>
