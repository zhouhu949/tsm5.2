<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript">
//跳转至上一条或下一条
function jump_(indexSize){
	document.location.href = "${ctx}/message/getOtherNoticeInfo.do?indexSize="+indexSize;
}
</script>
<title>消息中心-其他消息提醒-查看</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
</head>
<body>

		<c:if test="${indexSize ne 0}"> <!-- 0 表示第一条 -->
			<div class="changeBtn_left" onclick="jump_(${indexSize - 1})"><img src="${ctx}/static/images/yd_l.png" alt="加载图片"></div>
		</c:if>
		<c:if test="${isLast ne 1 }"> <!-- 不等于1 表示不是最后一条 -->
			<div class="changeBtn_right" onclick="jump_(${indexSize + 1})"><img src="${ctx}/static/images/yd_r.png" alt="加载图片"></div>
		</c:if>
	
<input type="hidden" id="indexSize" value="${indexSize }"><!-- 当前条数在列表中的位置 -->
<div class='bomp-cen bomp-cma'>
	<div class="bomp-mce-form">
		<h2 class="fl_l">${dto.dtoTitle}</h2>
		<div class="tit fl_l">
			<p>
				<label class="tit_box title-box-first"><label>发布人：</label><span class="publisher-name"  title="${dto.sendAcc }">${dto.sendAcc }</span></label>
				<label class="tit_box title-box-second"><label>发布时间：</label><span><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dto.sendDate}"/></span></label>
			</p>
		</div>
		<div class="cont fl_l">
			<div class="cont_cen">${dto.dtoContext}</div>
		</div>
	</div>
<%-- 	<div class='bomb-btn'>
		<label class='bomb-btn-cen'>
			<c:if test="${indexSize ne 0}"> <!-- 0 表示第一条 -->
				<a href="javascript:;" onclick="jump_(${indexSize - 1})" class="com-btna bomp-btna fl_l"><label>上一条</label></a>
			</c:if>
			<c:if test="${isLast ne 1 }"> <!-- 不等于1 表示不是最后一条 -->
				<a href="javascript:;" onclick="jump_(${indexSize + 1})"  class="com-btna bomp-btna fl_l"><label>下一条</label></a>
			</c:if>
		</label>
	</div> --%>
</div>
</body>
</html>
