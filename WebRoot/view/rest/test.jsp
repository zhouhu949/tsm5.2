<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>测试页面</title>
    <script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js"></script>

</head>
<body>
IP地址：<%= (com.qftx.base.util.HttpUtil.getIP(request)) %>
</body>
</html>
</html>
