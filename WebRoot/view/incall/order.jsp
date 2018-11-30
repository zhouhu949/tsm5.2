<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%> 
<link href="${ctx}/static/css/dialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/custcommon.js"></script>
<title>弹屏-订单记录</title>
</head>

<script type="text/javascript">
window.onload=function(){
	var height = $(".bomp_ical").height();
	window.parent.$("#iframepage").css({"height":height+"px"});
};
</script>
</head>
<body> 
<form action="${ctx}/res/incall/queryOrderList" method="post">
<input type='hidden' name='custId' id='custId' value='${custId }'>
<div class="bomp_ical">
	<div class="com-table com-mta">
		<table width="100%" cellspacing="0"  id="t1" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr role="head"  class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">交易日期</span></th>
					<th><span class="sp sty-borcolor-b">产品名称</span></th>
					<th><span class="sp sty-borcolor-b">订单号</span></th>
					<th><span class="sp sty-borcolor-b">交易价格(元)</span></th>
					<th><span class="sp sty-borcolor-b">交易数量</span></th>
					<th><span class="sp sty-borcolor-b">交易金额(元)</span></th>
					<th><span class="sp sty-borcolor-b">订单提交人</span></th>
					<th><span class="sp sty-borcolor-b"  sort="true">失效日期</span></th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>     
			    <c:choose>
			        <c:when test="${! empty list }">
						  <c:forEach items="${list}" var="order" varStatus="vs">
								<tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">	
									<td><div class="overflow_hidden w100" title='<fmt:formatDate value="${order.tradeDate }" pattern="yyyy-MM-dd"/>'><fmt:formatDate value="${order.tradeDate }" pattern="yyyy-MM-dd"/></div></td>
									<td><div class="overflow_hidden w70" title="${order.productName }">${order.productName }</div></td>
									<td><div class="overflow_hidden w70" title="${order.orderCode }">${order.orderCode }</div></td>
									<td><div class="overflow_hidden w70" title='<fmt:formatNumber type="number" value="${order.buyPrice }" pattern="0.00" maxFractionDigits="2" />'><fmt:formatNumber type="number" value="${order.buyPrice }" pattern="0.00" maxFractionDigits="2" /></div></td>
									<td><div class="overflow_hidden w70" title="${order.buyNum }">${order.buyNum }</div></td>
									<td><div class="overflow_hidden w70" title='<fmt:formatNumber type="number" value="${order.buyMoney }" pattern="0.00" maxFractionDigits="2" />'><fmt:formatNumber type="number" value="${order.buyMoney }" pattern="0.00" maxFractionDigits="2" /></div></td>
									<td><div class="overflow_hidden w70" title="${order.userName }">${order.userName }</div></td>
				                    <td><div class="overflow_hidden w100" title='<fmt:formatDate value="${order.invalidDate }" pattern="yyyy-MM-dd" />'><fmt:formatDate value="${order.invalidDate }" pattern="yyyy-MM-dd" /></div></td>
									<td><div class="overflow_hidden w100" title="${order.context }">${order.context }</div></td>
								</tr>								
			              </c:forEach>
			        </c:when>
						<c:otherwise>
							<tr>
								<td colspan="9">
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
			<div class="page">${orderDetailDto.page.pageStr}</div>
			<div class="clr_both"></div>
		</div>
	</div>
</div>
</form>
</body>
</html>