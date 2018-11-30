<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>系统设置-通知公告-查看</title>

<!--本页面js-->
<script type="text/javascript">

function returnBack(){
	document.location.href="${ctx}/notice/noticelist?v="+new Date().getTime();
}
</script>
</head>
<body>
<div class="hyx-na-tit"><label class="lab">通知公告 > 查看</label></div>
<div class="hyx-nas-form">
	<h2>${announcementDto.title}</h2>
	<div class="tit">
		<p>
			<label class="tit_box title-box-first"><label>发布人：</label><span>${announcementDto.inputerAcc}</span></label>
			<label class="tit_box title-box-second"><label>发布时间：</label><span><fmt:formatDate value="${announcementDto.inputdate}" pattern="yyyy-MM-dd HH:mm:ss"/></span></label>
		</p>
	</div>
	<div class="cont">
		<div class="cont_cen">${announcementDto.content}</div>
	</div>
	<div class="anyone-read-box">
		<label>已读人员:</label>
		<c:set var="readerList" value="${announcementDto.readeUser}"></c:set>
		<c:if test="${not empty readerList}">
		
			<c:forEach items="${readerList}" var="reader" >
				<span>${reader }</span>
			</c:forEach>
		</c:if>
	</div>
	<div class="com-btnbox">
		<a href="###" onclick="returnBack();" class="com-btna com-btna-sty" style="margin:0 auto;"><label>返回公告列表</label></a>
	</div>
</div>
</body>
</html>
