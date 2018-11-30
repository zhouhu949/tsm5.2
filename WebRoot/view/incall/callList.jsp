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
<form action="${ctx }/res/incall/callList" method="post">
<input type='hidden' name='custId' id='custId' value='${custId }'>
<input type="hidden" name="idReplaceWord" id="idReplaceWord" value="${idReplaceWord}">
<div class="bomp_ical">
	<div class="com-table com-mta">
		<table width="100%" id="t1" cellspacing="0"  cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr role="head" class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">呼叫类型</span></th> 
					<th><span class="sp sty-borcolor-b">客户联系人</span></th>
					<th><span class="sp sty-borcolor-b">联系人</span></th>
					<th><span class="sp sty-borcolor-b">主叫号码</span></th>
					<th><span class="sp sty-borcolor-b">被叫号码</span></th>
					<th><span class="sp sty-borcolor-b">通话时间</span></th>
					<th><span class="sp sty-borcolor-b">通话时长</span></th>
					<th><span sort="true">销售进程</span></th>
					
				</tr>
			</thead>
			<tbody>     
			    <c:choose>
			        <c:when test="${! empty list }">
						  <c:forEach items="${list}" var="call" varStatus="vs">
								<tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">	
									<td>
									 <div class="overflow_hidden w60" title="">
												<c:if test="${call.callState eq '1'}">
													<c:choose>
														<c:when test="${call.timeLength gt 0}">
																<span href="###" class="icon-incom-call"  title="已接来电"></span>
														</c:when>
														<c:otherwise>
																<span href="###" class="icon-missed-call"  title="未接来电"></span>
														</c:otherwise>
													</c:choose>									             	
									             </c:if>
									             <c:if test="${call.callState eq '2'}">
									             	<c:choose>
									             		<c:when test="${call.timeLength gt 0}">
									             			<span href="###" class="icon-out-call" title="已接去电"></span>
									             		</c:when>
									             		<c:otherwise>
									             			<span href="###" class="icon-not-connec" title="未接去电"></span>
									             		</c:otherwise>
									             	</c:choose>									             	
									             </c:if>
									 </div></td>
									<td><div class="overflow_hidden w60" title="${call.define1 }">${call.define1 }</div></td>
									<td><div class="overflow_hidden w60" title="${call.inputAcc }">${call.inputAcc }</div></td>
									<td><div class="overflow_hidden w80" title="${call.callerNum}" phone='tel'>${call.callerNum}</div></td>
									<td><div class="overflow_hidden w80" title="${call.calledNum}" phone='tel'>${call.calledNum}</div></td>
									<td><div class="overflow_hidden w120" title="<fmt:formatDate value='${call.startTime}' pattern='yyyy-MM-dd HH:mm'/>"><fmt:formatDate value='${call.startTime}' pattern='yyyy-MM-dd HH:mm'/></div></td>
									<td><div class="overflow_hidden w60" title="${call.timeLength}秒">${call.timeLength}秒</div></td>
									<td><div class="overflow_hidden w90" title="${call.saleProcessName}">${call.saleProcessName}</div></td>
								</tr>								
			              </c:forEach>
			        </c:when>
						<c:otherwise>
							<tr class="no_data_tr">
								<td>
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
			<div class="page">${item.page.pageStr}</div>
			<div class="clr_both"></div>
		</div>
	</div>
</div>
</form>
</body>
</html>