<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%> 
<link href="${ctx}/static/css/dialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/tsm/res/safePhone.js"></script>
<title>弹屏-通话记录</title>
</head>
<script type="text/javascript">
$(function(){

	//查看合同
	$("a[id^=look_]").click(function(){
		var id = $(this).attr("id").split("_")[1];
		var func = 'showHTDetail';
		external.OnCallBackMain(func,id);
	});	
});
</script>
<script type="text/javascript">
window.onload=function(){
	var height = $(".bomp_ical").height();
	window.parent.$("#iframepage").css({"height":height+"px"});
};
</script>
</head>
<body> 
<form action="${ctx}/res/incall/queryContractList" method="post">
<input type='hidden' name='custId' id='custId' value='${custId }'>
<div class="bomp_ical">
	<div class="com-table com-mta">
		<table width="100%"  id="t1" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr role="head"  class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">操作</span></th>
					<th><span class="sp sty-borcolor-b" sort="true">签约日期</span></th>
					<th><span class="sp sty-borcolor-b">合同编号</span></th> 
					<th><span class="sp sty-borcolor-b" sort="true">生效日期</span></th>
					<th><span class="sp sty-borcolor-b" sort="true">失效日期</span></th>
					<th>交易金额</th>
				</tr>
			</thead>
			<tbody>     
			    <c:choose>
			        <c:when test="${! empty list }">
						  <c:forEach items="${list}" var="contract" varStatus="vs">
								<tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">	
									<td style="width30px;"><div class="overflow_hidden w30 link"><a id="look_${contract.id }" href="###" class="icon-detail"  id="look_${cd.id }" title="查看"></a></div></td>
									<td><div class="overflow_hidden w100" title="<fmt:formatDate value="${contract.signDate }" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${contract.signDate }" pattern="yyyy-MM-dd"/></div></td>
									<td><div class="overflow_hidden w100" title="${contract.code }">${contract.code }</div></td>
									<td><div class="overflow_hidden w100" title="<fmt:formatDate value="${contract.effectiveDate }" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${contract.effectiveDate }" pattern="yyyy-MM-dd"/></div></td>
									<td><div class="overflow_hidden w100" title="<fmt:formatDate value="${contract.invalidDate }" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${contract.invalidDate }" pattern="yyyy-MM-dd"/></div></td>
									<td><div class="overflow_hidden w100" title='<fmt:formatNumber type="number" value="${contract.money }" pattern="0.00" maxFractionDigits="2" />万'><fmt:formatNumber type="number" value="${contract.money }" pattern="0.00" maxFractionDigits="2" />万</div></td>
								</tr>								
			              </c:forEach>
			        </c:when>
						<c:otherwise>
							<tr>
								<td colspan="6">
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
			<div class="page">${contractDto.page.pageStr}</div>
			<div class="clr_both"></div>
		</div>
	</div>
</div>
</form>
</body>
</html>