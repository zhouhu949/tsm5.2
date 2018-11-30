<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-月度计划（部门编辑）-查看页面（已执行）-详细计划</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->

</head>
<body> 
<div class='bomp-cen bomp-mtesa'>
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">计划汇总</label>
	</p>
	<div class="com-table hyx-mpe-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">部门成员</span></th>
					<th><span class="sp sty-borcolor-b">新增意向数（个）</span></th>
					<th><span class="sp sty-borcolor-b">新增签约数（个）</span></th>
					<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
					<th><span class="sp sty-borcolor-b">完成情况</span></th>
					<th><span class="sp sty-borcolor-b">审核结果</span></th>
					<th><span class="sp sty-borcolor-b">审核备注</span></th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${userPlans!=null and !empty userPlans}">
						<c:forEach items="${userPlans}" var="userPlan" varStatus="i">
							<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
							<c:if test="${i.index%2==0}"><tr></c:if>  
								<td><div class="overflow_hidden w80" title="${userPlan.userName}">${userPlan.userName}</div></td>
								<td><div class="overflow_hidden w80" title="${userPlan.factWillcustnum}/${userPlan.planWillcustnum}">${userPlan.factWillcustnum}/${userPlan.planWillcustnum}</div></td>
								<td><div class="overflow_hidden w80" title="${userPlan.factSigncustnum}/${userPlan.planSigncustnum}">${userPlan.factSigncustnum}/${userPlan.planSigncustnum}</div></td>
								<td><div class="overflow_hidden w80" title="${userPlan.factMoney}/${userPlan.planMoney}">${userPlan.factMoney}/${userPlan.planMoney}</div></td>
								<c:choose>
									<c:when test="${plan.planStatus == 0}"><td><div class="overflow_hidden w100" title="未完成">未完成</div></td></c:when>
									<c:when test="${plan.planStatus == 1 or plan.planStatus == 2}"><td><div class="overflow_hidden w100" title="完成">完成</div></td></c:when>
								</c:choose>
								<c:choose>
									<c:when test="${userPlan.authState == 0}"><td><div class="overflow_hidden w100" title="驳回">驳回</div></td></c:when>
									<c:when test="${userPlan.authState == 1}"><td><div class="overflow_hidden w100" title="待审核">待审核</div></td></c:when>
									<c:when test="${userPlan.authState == 2}"><td><div class="overflow_hidden w100" title="通过">通过</div></td></c:when>
								</c:choose>
								<td><div class="overflow_hidden w250" title="${userPlan.authDesc}">${userPlan.authDesc}</div></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="7" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
					</c:otherwise>
				</c:choose>
				<tr class="sty-bgcolor-b">
					<td><span class="sp  sty-borcolor-b">合计</span></td>
					<td><div class="overflow_hidden w70" title="${userPlansSum.factWillcustnum}/${userPlansSum.planWillcustnum}">${userPlansSum.factWillcustnum}/${userPlansSum.planWillcustnum}</div></td>
					<td><div class="overflow_hidden w70" title="${userPlansSum.factSigncustnum}/${userPlansSum.planSigncustnum}">${userPlansSum.factSigncustnum}/${userPlansSum.planSigncustnum}</div></td>
					<td><div class="overflow_hidden w70" title="${userPlansSum.factMoney}/${userPlansSum.planMoney}">${userPlansSum.factMoney}/${userPlansSum.planMoney}</div></td>
					<td colspan="3"><div class="overflow_hidden"></div></td>
				</tr>   
			</tbody>
		</table>
	</div>
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">偏差值调整</label>
	</p>
	<div class="com-table hyx-mpe-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">新增意向数（个）</span></th>
					<th><span class="sp sty-borcolor-b">新增签约数（个）</span></th>
					<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
					<th>调整原因</th>
				</tr>
			</thead>
			<tbody>              
				<tr>
					<td><div class="overflow_hidden w110" title="${plan.warpWillcustnum}">${plan.warpWillcustnum}</div></td>
					<td><div class="overflow_hidden w110" title="${plan.warpSigncustnum}">${plan.warpSigncustnum}</div></td>
					<td><div class="overflow_hidden w110" title="${plan.warpMoney}">${plan.warpMoney}</div></td>
					<td><div class="overflow_hidden w360" title="${plan.warpDesc}">${plan.warpDesc}</div></td>
				</tr>
			</tbody>
		</table>
		</table>
	</div>
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">调整后合计</label>
	</p>
	<div class="com-table hyx-mpe-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">新增意向数（个）</span></th>
					<th><span class="sp sty-borcolor-b">新增签约数（个）</span></th>
					<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
					<th>完成情况</th>
					<th><span class="sp sty-borcolor-b">规划金额（万）</span></th>
					<th><span class="sp sty-borcolor-b" style="position:relative;">差额（万）<i class="listenTM" title="规划金额减去计划金额"></i></span></th>
				</tr>
			</thead>
			<tbody>              
				<tr>
					<td><div class="overflow_hidden w110" title="${plan.factWillcustnum}/${plan.planWillcustnum}">${plan.factWillcustnum}/${plan.planWillcustnum}</div></td>
					<td><div class="overflow_hidden w110" title="${plan.factSigncustnum}/${plan.planSigncustnum}">${plan.factSigncustnum}/${plan.planSigncustnum}</div></td>
					<td><div class="overflow_hidden w110" title="${plan.factMoney}/${plan.planMoney}">${plan.factMoney}/${plan.planMoney}</div></td>
					<c:choose>
						<c:when test="${plan.planStatus == 0}"><td><div class="overflow_hidden w110" title="未完成">未完成</div></td></c:when>
						<c:when test="${plan.planStatus == 1 or plan.planStatus == 2}"><td><div class="overflow_hidden w110" title="完成">完成</div></td></c:when>
					</c:choose>
					<td><div class="overflow_hidden w110" title="${planSaleYearBean.planMoney}">${planSaleYearBean.planMoney}</div></td>
					<td><div class="overflow_hidden w110" title="<fmt:formatNumber value="${planSaleYearBean.planMoney -plan.planMoney}" minFractionDigits="0" pattern="#.##"/>"><fmt:formatNumber value="${planSaleYearBean.planMoney -plan.planMoney}" minFractionDigits="0" pattern="#.##"/></div></td>
				</tr>
			</tbody>
		</table>
	</div>
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">审核信息</label>
	</p>
	<div class="com-table hyx-mpe-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">审核状态</span></th>
					<th><span class="sp sty-borcolor-b">审核方式</span></th>
					<th><span class="sp sty-borcolor-b">审核人</span></th>
					<th><span class="sp sty-borcolor-b">审核时间</span></th>
					<th>审核备注</th>
				</tr>
			</thead>
			<tbody>              
				<tr>
					<c:choose>
						<c:when test="${plan.authState == 0}"><td><div class="overflow_hidden w80" title="驳回">驳回</div></td></c:when>
						<c:when test="${plan.authState == 1}"><td><div class="overflow_hidden w80" title="待审核">待审核</div></td></c:when>
						<c:when test="${plan.authState == 2}"><td><div class="overflow_hidden w80" title="通过">通过</div></td></c:when>
					</c:choose>
					<td><div class="overflow_hidden w110" title="${plan.authType}">${plan.authType}</div></td>
					<td><div class="overflow_hidden w110" title="${plan.authUsername}">${plan.authUsername}</div></td>
					<td><div class="overflow_hidden w130" title="<fmt:formatDate pattern="yyyy-MM-dd" value="${plan.authTime}"/>"><fmt:formatDate pattern="yyyy-MM-dd" value="${plan.authTime}"/></div></td>
					<td><div class="overflow_hidden w300" title="${plan.authDesc}">${plan.authDesc}</div></td>
				</tr>
			</tbody>
		</table>
	</div>
	<p class="hyx-mpe-ptit"><label class="com-red">温馨提示：</label>偏差值调整，作为本月计划中各个指标的调剂值；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px" >本月计划审核截止日期为当月${autoAuthDay}日23:00，届时已上报的未审核的计划将自动审核通过；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px">审核方式，分自动和手动两种，自动审核后，审核人默认为“系统”；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px;color:#6b6b6b;">格式说明：例如，“30/28”，/前表示执行情况，/后表示计划情况。</p>
</div>
</body>
</html>