<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%> 
<link href="${ctx}/static/css/dialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js"></script>
<title>弹屏-点评记录</title>
</head>

<script type="text/javascript">
window.onload=function(){
	var height = $(".bomp_ical").height();
	window.parent.$("#iframepage").css({"height":height+"px"});
};
</script>
</head>
<body> 
<form action="${ctx}/res/incall/queryServiceList" method="post">
<input type='hidden' name='custId' id='custId' value='${custId }'>
<div class="bomp_ical">
	<div class="com-table com-mta">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">回访时间</span></th>
					<th><span class="sp sty-borcolor-b">回访人</span></th>
					<th><span class="sp sty-borcolor-b">回访方式</span></th>
					<th><span class="sp sty-borcolor-b">沟通内容</span></th>
					<th>服务标签</th>
				</tr>
			</thead>
			<tbody>     
			    <c:choose>
			        <c:when test="${! empty list }">
						  <c:forEach items="${list}" var="serivce" varStatus="vs">
								<tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">	
									<td><div class="overflow_hidden w120" title="<fmt:formatDate value='${serivce.visitingDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"><fmt:formatDate value='${serivce.visitingDate}' pattern='yyyy-MM-dd HH:mm:ss'/></div></td>
									<td><div class="overflow_hidden w70" title="${serivce.visitingName}">${serivce.visitingName}</div></td>
									<td><div class="overflow_hidden w70">
									    <c:if test="${serivce.visitingType eq '1'}">电话联系</c:if>
                                		<c:if test="${serivce.visitingType eq '2'}">会客联系</c:if> 
									</div></td>
									<td><div class="overflow_hidden w160" title="${serivce.remark}">${serivce.remark}</div></td>
									<td><div class="overflow_hidden w100" title="${fn:replace(serivce.labelName,'#', '，')}">&nbsp;${fn:replace(serivce.labelName,'#', '，')}</div></td>
								</tr>								
			              </c:forEach>
			        </c:when>
						<c:otherwise>
							<tr>
								<td colspan="5">
									<center>当前列表无数据！</center>
								</td>
							</tr>									
						</c:otherwise>
			    </c:choose>         
			</tbody>
		</table>
	</div>
	<div class="com-bot">
		<div class="com-page fl_r">
			<div class="page">${serviceVisitDto.page.pageStr}</div>
			<div class="clr_both"></div>
		</div>
	</div>
</div>
</form>
</body>
</html>