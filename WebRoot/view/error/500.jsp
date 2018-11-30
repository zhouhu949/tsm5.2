<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%response.setStatus(200);%>
<%
    Throwable ex = null;
    if (exception != null)
        ex = exception;
    if (request.getAttribute("javax.servlet.error.exception") != null) {
        ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
    }
    ex.printStackTrace();
%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>500 - 系统内部错误</title>
</head>
<body>
<%--<h2>500 - 系统发生内部错误.</h2>
${exception }--%>

<div class="error-page">
    <p class="error-first-p">
        <img width="282" height="191" src="${ctx}/static/images/error_page500.png" alt="">
    </p>
    <p class="error-second-p">
        <span>服务器正在玩命工作中...请休息片刻，去喝杯水，再继续工作</span>
    </p>
    <%--   <p class="error-third-p">
    <a href="">返回</a>
    </p>--%>
    <p class="error-forth-p">
        <span>客服热线：400-826-2277</span>
    </p>
</div>
</body>
</html>
