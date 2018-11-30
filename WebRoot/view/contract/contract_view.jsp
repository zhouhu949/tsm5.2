<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>查看合同</title>
    <%@ include file="/common/include.jsp" %>
    	<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
	<% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
    <script type="text/javascript" src="${ctx}/static/js/view/rest/ajaxfileupload.js"></script>
    <script type="text/javascript">
		var account = '${shrioUser.account}';
		var orgId = '${shrioUser.orgId}';
		var userId = '${shrioUser.id}';
		var tsmUpLoadServiceUrl = '${tsmUpLoadServiceUrl}'; // <!-- 上传服务器路径 -->
	</script>
    <script type="text/javascript" src="${ctx }/static/js/view/contract/contract_add.js"></script>
</head>
<body>

	<div class="hyx-nc hyx-vc">
	<div class='bomp_tit'><label class='lab'>合同基本信息</label></div>
	<div class="bomp-box">
		<p class='bomp-p bomp-error'>
			<label class='lab_a fl_l'>${empty filedMap['name'] ? '客户名称' : filedMap['name'] }：</label><label class="lab_b fl_l">${contractDto.custName }</label>
		</p>
		<p class='bomp-p'>
			<label class='lab_a fl_l'>合同编号：</label><label class="lab_b fl_l">${contractDto.code }</label>
		</p>
		<p class='bomp-p'>
			<label class='lab_a fl_l'>合同名称：</label><label class="lab_b fl_l">${contractDto.contractName }</label>
		</p>
	</div>
	<div class="bomp-box">
		<p class='bomp-p'>
			<label class='lab_a fl_l'>签订日期：</label><label class="lab_b fl_l"><fmt:formatDate value="${contractDto.signDate }" pattern="yyyy-MM-dd" /></label>
		</p>
		<p class='bomp-p'>
			<label class='lab_a fl_l'>销售人员：</label><label class="lab_b fl_l">${contractDto.signUsername }</label>
		</p>
    <p class='bomp-p'>
			<label class='lab_a fl_l'>合同签订主体：</label><label class="lab_b fl_l">${contractDto.body }</label>
		</p>
	</div>
	<div class="bomp-box">
		<p class='bomp-p'>
			<label class='lab_a fl_l'>生效日期：</label><label class="lab_b fl_l"><fmt:formatDate value="${contractDto.effectiveDate }" pattern="yyyy-MM-dd" /></label>
		</p>
		<p class='bomp-p'>
			<label class='lab_a fl_l'>失效日期：</label><label class="lab_b fl_l"><fmt:formatDate value="${contractDto.invalidDate }" pattern="yyyy-MM-dd" /></label>
		</p>
		<p class='bomp-p'>
			<label class='lab_a fl_l'>合同归档索引：</label><label class="lab_b fl_l">${contractDto.storeIndex }</label>
		</p>
	</div>
	<div class='bomp_tit bomp-tit-mt'><label class='lab'>合同主要内容</label></div>
	<div class="bomp-box">
		<p class='bomp-p'>
			<label class='lab_a fl_l'>合同金额：</label><label class="lab_b fl_l"><fmt:formatNumber type="number" value="${contractDto.money }" pattern="0.00万元" maxFractionDigits="2" /></label>
		</p>
	</div>
	<div class='bomp-p bomp-p-w'>
		<label class='lab_a fl_l'>合同附件：</label>
		<ul class="hyx-vc-list fl_l" style="padding:0;">
			<c:forEach items="${conFileDtos }" var="conFile">
			<li><label class="name">${conFile.fileName }</label><a id="download_${conFile.fileId }" href="javascript:void(0);" class="link">下载</a><!-- <a href="###" class="link" >预览</a> --></li>
			</c:forEach>
		</ul>
	</div>
	<p class='bomp-p bomp-p-w'>
		<label class='lab_a fl_l'>付款计划说明：</label><label class="lab_b lab_b_w fl_l">${contractDto.payContext }</label>
	</p>
	<p class='bomp-p bomp-p-w bomp-p-line'>订单内容：</p>
	<div class="com-table com-mta hyx-cla-table fl_l">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">操作</span></th>
					<th><span class="sp sty-borcolor-b">订单编号</span></th>
					<th><span class="sp sty-borcolor-b">交易日期</span></th>
					<th><span class="sp sty-borcolor-b">付款方式</span></th>
					<th><span class="sp sty-borcolor-b">生效日期</span></th>
					<th><span class="sp sty-borcolor-b">失效日期</span></th>
					<th>订单金额（元）</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty orderBeans }">
						<c:forEach items="${orderBeans }" var="order">
							<tr>
								<td style="width:30px;">
									<div class="overflow_hidden w30 link">
										<a href="javascript:;" id="view_${order.id }" class="icon-search-look demoBtn_a" title="查看"></a>
									</div>
								</td>
								<td><div class="overflow_hidden w100" title="${order.code }">${order.code }</div></td>
								<td><div class="overflow_hidden w120" title="<fmt:formatDate value="${order.tradeDate }" pattern="yyyy-MM-dd" />"><fmt:formatDate value="${order.tradeDate }" pattern="yyyy-MM-dd" /></div></td>
								<td>
									<div class="overflow_hidden w10" title="">
										<c:choose>
	               							<c:when test="${order.payType eq 1 }">现金</c:when>
	               							<c:when test="${order.payType eq 2 }">银行转账/汇款</c:when>
	               							<c:when test="${order.payType eq 3 }">在线支付</c:when>
	               							<c:when test="${order.payType eq 4 }">支付宝转账</c:when>
	               						</c:choose>
									</div>
								</td>
								<td><div class="overflow_hidden w120" title="<fmt:formatDate value="${order.effectiveDate }" pattern="yyyy-MM-dd" />"><fmt:formatDate value="${order.effectiveDate }" pattern="yyyy-MM-dd" /></div></td>
								<td><div class="overflow_hidden w120" title="<fmt:formatDate value="${order.invalidDate }" pattern="yyyy-MM-dd" />"><fmt:formatDate value="${order.invalidDate }" pattern="yyyy-MM-dd" /></div></td>
								<td><div class="overflow_hidden w100" title="<fmt:formatNumber type="number" value="${order.money }" pattern="0.00" maxFractionDigits="2" />"><fmt:formatNumber type="number" value="${order.money }" pattern="0.00" maxFractionDigits="2" /></div></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="7">暂无订单!</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</div>
</body>
</html>
