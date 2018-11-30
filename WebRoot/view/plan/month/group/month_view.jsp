<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>月度计划--部门查看未执行</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<script type="text/javascript">
$(function(){

    $('.demoBtn_b').click(function(){
		showHistory('${plan.groupId}','${plan.groupName}');
    });
});

function commontWindow(planId){
	window.parent.commontWindow(planId);
}
function showDetail(planId,groupName,type){
	window.parent.showDetail(planId,groupName,type,true);
}

function showHistory(groupId,groupName){
	window.parent.showHistory(groupId,groupName);
}

/* 刷新页面*/
function refreshPage(){
	$("#monthPlanForm").submit();
}
</script>
</head>
<body> 
<form id="monthPlanForm" action="${ctx}/plan/month/group/monthView${_v}">
	<input id="id" name="id" type="hidden" value="${plan.id}"/>
	<input id="planYear" name="planYear" type="hidden" value="${plan.planYear}"/>
	<input id="planMonth" name="planMonth" type="hidden" value="${plan.planMonth}"/>
</form>
<div class="com-conta">
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">计划汇总</label>
		<a href="javascript:;" class="com-btna demoBtn_b fl_r"><i class="min-rise"></i><label class="lab-mar">历史走势</label></a>
	</p>
	<div class="com-table hyx-mpe-table hyx-cm-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">操作</span></th> 
					<th><span class="sp sty-borcolor-b">部门名称</span></th>
					<th><span class="sp sty-borcolor-b">新增意向数（个）</span></th>
					<th><span class="sp sty-borcolor-b">新增签约数（个）</span></th>
					<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
					<th><span class="sp sty-borcolor-b">审核结果</span></th>
					<th><span class="sp sty-borcolor-b">审核备注</span></th>
				</tr>
			</thead>
			<tbody>              
				<c:choose>
					<c:when test="${plans!=null and !empty plans}">
						<c:forEach items="${plans}" var="teamPlan" varStatus="i">
							<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
							<c:if test="${i.index%2==0}"><tr></c:if>
								<td style="width:80px;"><div class="overflow_hidden w80 link">
									<a href="javascript:commontWindow('${teamPlan.id}');" class="icon-review" title="点评"></a>
									<a href="javascript:showDetail('${teamPlan.id}','${teamPlan.groupName}',${teamPlan.groupType});" class="icon-detail" title="详情"></a>
									<a href="javascript:showHistory('${teamPlan.groupId}','${teamPlan.groupName}');" class="icon-hist-trend" title="历史走势"></a>
									</div></td>
								<td><div class="overflow_hidden w70" title="${teamPlan.groupName }">${teamPlan.groupName }</div></td>
								<td><div class="overflow_hidden w70" title="${teamPlan.planWillcustnum }">${teamPlan.planWillcustnum }</div></td>
								<td><div class="overflow_hidden w70" title="${teamPlan.planSigncustnum }">${teamPlan.planSigncustnum }</div></td>
								<td><div class="overflow_hidden w70" title="${teamPlan.planMoney }">${teamPlan.planMoney }</div></td>
								<c:choose>
									<c:when test="${teamPlan.authState == 0}"><td><div class="overflow_hidden w120" title="驳回">驳回</div></td></c:when>
									<c:when test="${teamPlan.authState == 1}"><td><div class="overflow_hidden w120" title="待审核">待审核</div></td></c:when>
									<c:when test="${teamPlan.authState == 2}"><td><div class="overflow_hidden w120" title="通过">通过</div></td></c:when>
								</c:choose>
								<td><div class="overflow_hidden w360" title="${teamPlan.authDesc }">${teamPlan.authDesc }</div></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="7" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
					</c:otherwise>
				</c:choose>	
				<tr class="sty-bgcolor-b">
					<td colspan="2"><span class="sp  sty-borcolor-b">合计</span></td>
					<td><div class="overflow_hidden w110" title="${teamPlansSum.planWillcustnum}">${teamPlansSum.planWillcustnum}</div></td>
					<td><div class="overflow_hidden w110" title="${teamPlansSum.planSigncustnum}">${teamPlansSum.planSigncustnum}</div></td>
					<td><div class="overflow_hidden w110" title="${teamPlansSum.planMoney}">${teamPlansSum.planMoney}</div></td>
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
	<c:if test="${pGroupId!=null}">
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
	</c:if>
	<p class="hyx-mpe-ptit"><label class="com-red">温馨提示：</label>偏差值调整，作为本月计划中各个指标的调剂值；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px" >本月计划审核截止日期为当月${autoAuthDay}日23:00，届时已上报的未审核的计划将自动审核通过；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px">审核方式，分自动和手动两种，自动审核后，审核人默认为“系统”；</p>
</div>
<script type="text/javascript">
        window.onload = function () {
            var height = $(".com-conta").height() + 30;
            window.parent.$("#iframepage").css({ /*"width":width+"px",*/
                "height": height + "px"
            });
        };
    </script>
</body>
</html>