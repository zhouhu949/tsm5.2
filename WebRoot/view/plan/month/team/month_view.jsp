<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-团队月度计划-查看页面（未执行）</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<script type="text/javascript">
$(function(){
	/*表单优化*/

    $("#back").click(function(){
		$.post(ctx+"/plan/month/team/bakReport",{"planId":"${plan.id}"},function(data){
			if(data.status=="success"){
				setTimeout('window.top.$("#centerTabs").tabs("close","小组月计划-查看");',10);
				bakTab();
			}
		});
	});
});

function bakTab(){
	window.top.addTab(ctx+"/plan/month/team/yearView","小组月计划");
}

function commontWindow(planId){
	pubDivShowIframe('commontWindow','${ctx}/plan/month/user/commontWindow?planId='+planId,'计划点评',700,250);
}

function showDetail(planId,userName){
	pubDivShowIframe('detailWindow','${ctx}/plan/month/user/detailWindow?planId='+planId,'计划详情（'+userName+'）',900,510);
}

function showHistory(userId,userName){
	pubDivShowIframe('historyWindow','${ctx}/plan/month/user/historyWindow?userId='+userId,'历史计划走势（'+userName+'）',915,380);
}
/* 刷新页面*/
function refreshPage(){
	$("#monthPlanForm").submit();
}

</script>
</head>
<body> 
<form id="monthPlanForm" action="${ctx}/plan/month/team/monthView">
	<input id="id" name="id" type="hidden" value="${plan.id}"/>
</form>
<div class="com-conta">
	<h2 class="hyx-mpe-h2">${plan.planYear}年${plan.groupName}${plan.planMonth}月份计划</h2>
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">计划汇总</label>
	</p>
	<div class="com-table hyx-mpe-table hyx-cm-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">操作</span></th> 
					<th><span class="sp sty-borcolor-b">部门成员</span></th>
					<th><span class="sp sty-borcolor-b">新增意向数（个）</span></th>
					<th><span class="sp sty-borcolor-b">新增签约数（个）</span></th>
					<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
					<th><span class="sp sty-borcolor-b">审核结果</span></th>
					<th><span class="sp sty-borcolor-b">审核备注</span></th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${userPlans!=null and !empty userPlans}">
						<c:forEach items="${userPlans}" var="plan" varStatus="i">
							<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
							<c:if test="${i.index%2==0}"><tr></c:if>
								<td style="width:80px;"><div class="overflow_hidden w80 link">
									<a href="javascript:commontWindow('${plan.id}');" class="icon-review" title="点评"></a>
									<a href="javascript:showDetail('${plan.id}','${plan.userName}');" class="icon-detail" title="详情"></a>
									<a href="javascript:showHistory('${plan.userId}','${plan.userName}');" class="icon-hist-trend" title="历史走势"></a>
								</div></td>
								<td><div class="overflow_hidden w70" title="${plan.userName}">${plan.userName}</div></td>
								<td><div class="overflow_hidden w70" title="${plan.planWillcustnum}">${plan.planWillcustnum}</div></td>
								<td><div class="overflow_hidden w70" title="${plan.planSigncustnum}">${plan.planSigncustnum}</div></td>
								<td><div class="overflow_hidden w70" title="${plan.planMoney}">${plan.planMoney}</div></td>
								<c:choose>
									<c:when test="${plan.authState == 0}"><td><div class="overflow_hidden w120" title="驳回">驳回</div></td></c:when>
									<c:when test="${plan.authState == 1}"><td><div class="overflow_hidden w120" title="待审核">待审核</div></td></c:when>
									<c:when test="${plan.authState == 2}"><td><div class="overflow_hidden w120" title="通过">通过</div></td></c:when>
								</c:choose>
								<td><div class="overflow_hidden w360" title="${plan.authDesc}">${plan.authDesc}</div></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="7" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
					</c:otherwise>
				</c:choose>	
				<tr class="sty-bgcolor-b">
					<td colspan="2"><span class="sp  sty-borcolor-b">合计</span></td>
					<td><div class="overflow_hidden w70" title="${userPlansSum.planWillcustnum}">${userPlansSum.planWillcustnum}</div></td>
					<td><div class="overflow_hidden w70" title="${userPlansSum.planSigncustnum}">${userPlansSum.planSigncustnum}</div></td>
					<td><div class="overflow_hidden w70" title="${userPlansSum.planMoney}">${userPlansSum.planMoney}</div></td>
					<td colspan="2"><div class="overflow_hidden"></div></td>
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
					<th><span class="sp sty-borcolor-b">规划金额（万）</span></th>
					<th><span class="sp sty-borcolor-b" style="position:relative;">差额（万）<i class="listenTM" title="规划金额减去计划金额"></i></span></th>
				</tr>
			</thead>
			<tbody>              
				<tr>
					<td><div class="overflow_hidden w110" title="${plan.planWillcustnum}">${plan.planWillcustnum}</div></td>
					<td><div class="overflow_hidden w110" title="${plan.planSigncustnum}">${plan.planSigncustnum}</div></td>
					<td><div class="overflow_hidden w110" title="${plan.planMoney}">${plan.planMoney}</div></td>
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
						<c:when test="${plan.authState == 0}"><td><div class="overflow_hidden w110" title="驳回">驳回</div></td></c:when>
						<c:when test="${plan.authState == 1}"><td><div class="overflow_hidden w110" title="待审核">待审核</div></td></c:when>
						<c:when test="${plan.authState == 2}"><td><div class="overflow_hidden w110" title="通过">通过</div></td></c:when>
					</c:choose>
					<td><div class="overflow_hidden w110" title="${plan.authType}">${plan.authType}</div></td>
					<td><div class="overflow_hidden w110" title="${plan.authUsername}">${plan.authUsername}</div></td>
					<td><div class="overflow_hidden w120" title="<fmt:formatDate pattern="yyyy-MM-dd" value="${plan.authTime}"/>"><fmt:formatDate pattern="yyyy-MM-dd" value="${plan.authTime}"/></div></td>
					<td><div class="overflow_hidden w360" title="${plan.authDesc}">${plan.authDesc}</div></td>
				</tr>
			</tbody>
		</table>
	</div>
	<p class="hyx-mpe-ptit"><label class="com-red">温馨提示：</label>偏差值调整，作为本月计划中各个指标的调剂值；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px" >本月计划审核截止日期为当月${autoAuthDay}日23:00，届时已上报的未审核的计划将自动审核通过；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px">审核方式，分自动和手动两种，自动审核后，审核人默认为“系统”；</p>
	
	<div class="com-btnbox" style="width:405px;padding-bottom:60px;">
		<div class="com-btnbox-cen">
			<c:if test="${plan.status==1 and plan.authState==1}">
				<a id="back"  href="javascript:;" class="com-btna com-btna-sty fl_l"><label>撤回计划</label></a>
			</c:if>
		</div>
	</div>
</div>
</body>
</html>