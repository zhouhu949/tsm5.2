<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%> 
<link href="${ctx}/static/css/dialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/tsm/res/safePhone.js"></script>
<title>弹屏-通话记录</title>
</head>
<script type="text/javascript">
</script>
<script type="text/javascript">
window.onload=function(){
	var height = $(".bomp_ical").height();
	window.parent.$("#iframepage").css({"height":height+"px"});
};
</script>
</head>
<body> 
<form action="${ctx}/res/incall/queryReviewList" method="post">
<input type='hidden' name='custId' id='custId' value='${custId }'>
<div class="bomp_ical">
	<div class="com-table com-mta">
		<table width="100%" id="t1" cellspacing="0"  cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr role="head" class="sty-bgcolor-b">
				    <th><span sort="true">点评时间</span></th>
					<th><span class="sp sty-borcolor-b">点评人</span></th> 
					<th>点评内容</th>
				</tr>
			</thead>
			<tbody>     
			    <c:choose>
			        <c:when test="${! empty list }">
						  <c:forEach items="${list}" var="review" varStatus="vs">
								<tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">	
									<td><div class="overflow_hidden w120" title="<fmt:formatDate value='${review.reviewDate}' pattern='yyyy-MM-dd HH:mm'/>"><fmt:formatDate value='${review.reviewDate}' pattern='yyyy-MM-dd HH:mm'/></div></td>
									<td><div class="overflow_hidden w100" title="${ review.reviewAcc}">${ review.reviewAcc}</div></td>
									<td><div class="overflow_hidden w310" title="${review.revComment }">${review.revComment }</div></td>
								</tr>								
			              </c:forEach>
			        </c:when>
						<c:otherwise>
							<tr>
								<td colspan="3">
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
			<div class="page">${tsmCustReview.page.pageStr}</div>
			<div class="clr_both"></div>
		</div>
	</div>
</div>
</form>
</body>
</html>