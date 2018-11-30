<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-月度计划（部门编辑）-编辑页面（部门）</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<script type="text/javascript">
$(function(){

    $('.demoBtn_b').click(function(){
		showHistory('${plan.groupId}','${plan.groupName}');
    });
    
    $("#upReport").click(function(){
    	window.parent.checkStatus();
	});
	
	$("#save").click(function(){
		window.parent.savePlan(0);
	});
});
function commontWindow(planId){
	window.parent.commontWindow(planId);
}

function back(planId){
	window.parent.back(planId);
}

function authWindow(planId,groupName,groupPlanId){
	window.parent.authWindow(planId,groupName,groupPlanId);
}

function showDetail(planId,groupName,type){
	window.parent.showDetail(planId,groupName,type,true);
}

function showHistory(groupId,groupName){
	window.parent.showHistory(groupId,groupName);
}

function warpWindow(){
	var params = $("#monthPlanForm").serialize();
	window.parent.warpWindow(params);
}

function warp(i,value){
	$($(".warpValue")[i]).text(value);
	$($(".warpValue")[i]).attr("value",value);
	$($(".warpValue")[i]).attr("title",value);
	
	if(i==0) {
		$("#warpWillcustnum").val(value);
		$("#totalWill").text(parseInt($("#userTotalWill").text())+parseInt(value));
		
		$("#planWillcustnum").text(parseInt($("#userTotalWill").text())+parseInt(value));
	}
	
	if(i==1) {
		$("#warpSigncustnum").val(value);
		$("#totalSign").text(parseInt($("#userTotalSign").text())+parseInt(value));
		$("#planSigncustnum").text(parseInt($("#userTotalSign").text())+parseInt(value));
	}
	
	if(i==2) {
		$("#warpMoney").val(value);
		//$("#totalMoney").text((parseFloat($("#userTotalMoney").text())*100+parseFloat(value)*100 )/100);
		//$("#planMoney").text((parseFloat($("#userTotalMoney").text())*100+parseFloat(value)*100 )/100);
		
		
		$("#totalMoney").text(accAdd(parseFloat($("#userTotalMoney").text()),parseFloat(value)));
		$("#planMoney").text(accAdd(parseFloat($("#userTotalMoney").text()),parseFloat(value)));
		
		var guihua = parseFloat($("#guihua").text().replace("万",""));
		
		//$("#cha").text((guihua*100 - parseFloat($("#totalMoney").text())*100 )/100+"万");
		$("#cha").text(accAdd(parseFloat(guihua),parseFloat($("#totalMoney").text()))+"万");
	}
	
	if(i==3) {
		$("#warpDesc").val(value);
	}
}

function accAdd(arg1,arg2){ 
	var r1,r2,m; 
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
	m=Math.pow(10,Math.max(r1,r2)); 
	return (arg1*m+arg2*m)/m; 
} 

/* 刷新页面*/
function refreshPage(){
	$("#monthPlanForm").submit();
}

</script>
</head>
<body>
<form id="monthPlanForm" action="${ctx}/plan/month/group/monthEdit${_v}">
	<input id="isNew" name="isNew" type="hidden" value="${isNew}"/>
	<input id="id" name="id" type="hidden" value="${plan.id}"/>
	<input id="groupId" name="groupId" type="hidden" value="${plan.groupId}"/>
	<input id="groupType" name="groupType" type="hidden" value="1"/>
	<input id="planYear" name="planYear" type="hidden" value="${plan.planYear}"/>
	<input id="planMonth" name="planMonth" type="hidden" value="${plan.planMonth}"/>
	
	<input id="warpWillcustnum" name="warpWillcustnum" type="hidden" value="${plan.warpWillcustnum}"/>
	<input id="warpSigncustnum" name="warpSigncustnum" type="hidden" value="${plan.warpSigncustnum}"/>
	<input id="warpMoney" name="warpMoney" type="hidden" value="${plan.warpMoney}"/>
	<input id="warpDesc" name="warpDesc" type="hidden" value="${plan.warpDesc}"/>
	
	<input id="planWillcustnum" name="planWillcustnum" type="hidden" value="${plan.planWillcustnum}"/>
	<input id="planSigncustnum" name="planSigncustnum" type="hidden" value="${plan.planSigncustnum}"/>
	<input id="planMoney" name="planMoney" type="hidden" value="${plan.planMoney}"/>
	
	<input id="usersPlanWillcustnum" name="usersPlanWillcustnum" type="hidden" value="${teamPlansSum.planWillcustnum}"/>
	<input id="usersPlanSigncustnum" name="usersPlanSigncustnum" type="hidden" value="${teamPlansSum.planSigncustnum}"/>
	<input id="usersPlanMoney" name="usersPlanMoney" type="hidden" value="${teamPlansSum.planMoney}"/>
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
					<th><span class="sp sty-borcolor-b">部门</span></th>
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
							
							<td style="width:120px;"><div class="overflow_hidden w120 link">
								<a href="javascript:commontWindow('${teamPlan.id}');" class="icon-review" title="点评"></a>
								<c:choose>
									<c:when test="${teamPlan.authState == 1}">
									<a href="javascript:back('${teamPlan.id}');" class="icon-take-back" title="退回"></a>
									<a href="javascript:authWindow('${teamPlan.id}','${teamPlan.groupName}','${plan.id}');" class="icon-examine" title="审核"></a>
									</c:when>
									<c:otherwise>
									<a href="###" class="icon-take-back img-gray" title="退回"></a>
									<a href="###" class="icon-examine img-gray" title="审核"></a>
									</c:otherwise>
								</c:choose>
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
					<td><div id="userTotalWill" class="overflow_hidden w70" title="${teamPlansSum.planWillcustnum }">${teamPlansSum.planWillcustnum }</div></td>
					<td><div id="userTotalSign" class="overflow_hidden w70" title="${teamPlansSum.planSigncustnum }">${teamPlansSum.planSigncustnum }</div></td>
					<td><div id="userTotalMoney" class="overflow_hidden w70" title="${teamPlansSum.planMoney }">${teamPlansSum.planMoney }</div></td>
					<td colspan="2"><div class="overflow_hidden"></div></td>
				</tr>
			</tbody>
		</table>
	</div>
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">偏差值调整</label>
	</p>
	<div class="com-table hyx-mpe-table hyx-cm-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">操作</span></th> 
					<th><span class="sp sty-borcolor-b">新增意向数（个）</span></th>
					<th><span class="sp sty-borcolor-b">新增签约数（个）</span></th>
					<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
					<th>调整原因</th>
				</tr>
			</thead>
			<tbody>              
				<tr>
					<td style="width:30px;"><div class="overflow_hidden w30 link">
						<a href="javascript:warpWindow('${plan.id}');" class="icon-edit" title="编辑"></a></div></td>
					<td><div class="overflow_hidden w110 warpValue" dbValue ="${plan.warpWillcustnum}" value="${plan.warpWillcustnum}" title="${plan.warpWillcustnum}">${plan.warpWillcustnum}</div></td>
					<td><div class="overflow_hidden w110 warpValue" dbValue ="${plan.warpSigncustnum}" value="${plan.warpSigncustnum}" title="${plan.warpSigncustnum}">${plan.warpSigncustnum}</div></td>
					<td><div class="overflow_hidden w110 warpValue" dbValue ="${plan.warpMoney}" value="${plan.warpMoney}" title="${plan.warpMoney}">${plan.warpMoney}</div></td>
					<td><div class="overflow_hidden w360 warpValue" dbValue ="${plan.warpDesc}" value="${plan.warpDesc}" title="${plan.warpDesc}">${plan.warpDesc}</div></td>
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
					<td><div id="totalWill" class="overflow_hidden w110" title="${plan.planWillcustnum}">${plan.planWillcustnum}</div></td>
					<td><div id="totalSign" class="overflow_hidden w110" title="${plan.planSigncustnum}">${plan.planSigncustnum}</div></td>
					<td><div id="totalMoney" class="overflow_hidden w110" title="${plan.planMoney}">${plan.planMoney}</div></td>
					<td><div id="guihua" class="overflow_hidden w110" title="${planSaleYearBean.planMoney}">${planSaleYearBean.planMoney}</div></td>
					<td><div id="cha" class="overflow_hidden w110" title="<fmt:formatNumber value="${planSaleYearBean.planMoney -plan.planMoney}" minFractionDigits="0" pattern="#.##"/>"><fmt:formatNumber value="${planSaleYearBean.planMoney -plan.planMoney}" minFractionDigits="0" pattern="#.##"/></div></td>
				</tr>
			</tbody>
		</table>
	</div>
	<p class="hyx-mpe-ptit"><label class="com-red">温馨提示：</label>偏差值调整，作为本月计划中各个指标的调剂值；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px" >本月计划审核截止日期为当月${autoAuthDay}日23:00，届时已上报的未审核的计划将自动审核通过；</p>
	<div class="com-btnbox" style="width:405px;">
		<div class="com-btnbox-cen">
			<a id="save" href="javascript:;" class="com-btna com-btna-sty fl_l"><label>保存</label></a>
			<c:if test="${pGroupId!=null}">
				<a id="upReport" href="javascript:;" class="com-btna com-btna-sty fl_l"><label>上报</label></a>
			</c:if>
		</div>
	</div>
</div>
<script type="text/javascript">
        window.onload = function () {
            var height = $(".com-conta").height() + 30;
            window.parent.$("#iframepage").css({
                "height": height + "px"
            });
        };
    </script>
</body>
</html>