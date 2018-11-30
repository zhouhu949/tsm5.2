<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css${_v}"><!--弹框样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/worklog/newMyLog.css${_v}"/>
<script type="text/javascript" src="${ctx}/static/js/base64.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/logIdialogFileList.js${_v}"></script>
</head>
<body>
<input type="hidden" value="${data }" id="file">
<ul class="log-file-list">

</ul>
<script type="text/x-handlebars-template" id="template">
{{#each fileList}}
	<li>
		<span class="log-file-name">{{name}}</span>
		<a href="{{href}}" class="log-file-download">下载</a>
	</li>
{{/each}}
</script>
</body>
</html>
