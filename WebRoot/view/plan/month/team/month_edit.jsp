<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-月度计划（团队编辑）-编辑页面（团队）</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<script type="text/javascript">
var isNew = ${isNew};

$(function(){
	/*表单优化*/

    $("#upReport").click(function(){
		checkStatus();
	});
	
	$("#save").click(function(){
		savePlan(0);
	});
});

function commontWindow(planId){
	pubDivShowIframe('commontWindow','${ctx}/plan/month/user/commontWindow?planId='+planId,'计划点评',700,250);
}

function back(planId){
	pubDivDialog("bakPlanWindow","你确定要退回该计划吗？",function(){
		$.post(ctx+"/plan/month/user/bakReport",{"planId":planId},
			function(data){
			if("success"==data.status){
				refreshPage();
			}else{
			}
		});
	});
}

function authWindow(planId,userName,groupPlanId){
	pubDivShowIframe('authWindow','${ctx}/plan/month/user/authWindow?planId='+planId+'&groupPlanId='+groupPlanId,'计划审核（'+userName+'）',900,510);
}

function showHistory(userId,userName){
	pubDivShowIframe('historyWindow','${ctx}/plan/month/user/historyWindow?userId='+userId,'历史计划走势（'+userName+'）',915,380);
}

function showDetail(planId,userName){
	pubDivShowIframe('detailWindow','${ctx}/plan/month/user/detailWindow?planId='+planId,'计划详情（'+userName+'）',900,510);
}

function warpWindow(){
	var params = $("#monthPlanForm").serialize();
	pubDivShowIframe('warpWindow','${ctx}/plan/month/team/warpWindow${_v}&'+params,'偏差值编辑',700,380);
}

function checkStatus(){
	var params = $("#monthPlanForm").serialize();
	$.post(ctx+"/plan/month/team/getPlanStatus${_v}&"+params,
		function(data){
		if(data.noAuthNum >0 || data.noUpNum >0){
			msg = "该月计划还有 ";
			if(data.noUpNum >0) msg+= data.noUpNum+"人未上报，";
			if(data.noAuthNum >0) msg+=data.noAuthNum+"人未审核，";
			msg+="您确定要上报该小组的月计划吗？";
			upReport(msg);
		}else{
			upReport();
		}
	});
}

function upReport(msg){
	savePlan(1,msg);
}

function savePlan(status,msg){
	if(msg==undefined || msg==null){
		msg="确定要保存计划吗？";
		if(status==1) msg="确定要上报计划吗？";
	}
	pubDivDialog("savePlanWindow",msg,function(){
		var params = $("#monthPlanForm").serialize();
		$.post(ctx+"/plan/month/team/saveTeamPlan${_v}&"+params+"&status="+status ,
			function(data){
			if("success"==data.status){
				bakTab();
				if(status==1){
					setTimeout('window.top.$("#centerTabs").tabs("close","小组月计划-编辑");',10);
				}else{
					refreshPage();
				}
			}else{
				warmDivDialog("warm",data.msg);
			}
		});
	});	
}

function bakTab(){
	window.top.addTab(ctx+"/plan/month/team/yearView${_v}&groupId=${plan.groupId}","小组月计划");
}

function warp(i,value){
	$($(".warpValue")[i]).text(value);
	$($(".warpValue")[i]).attr("value",value);
	$($(".warpValue")[i]).attr("title",value);
	
	if(i==0) {
		$("#warpWillcustnum").val(value);
		var will = parseInt($("#userTotalWill").text())+parseInt(value);
		
		$("#totalWill").text(will);
		$("#totalWill").attr("title",will);
		$("#planWillcustnum").text(will);
	}
	
	if(i==1) {
		$("#warpSigncustnum").val(value);
		var sign = parseInt($("#userTotalSign").text())+parseInt(value);
		
		$("#totalSign").text(sign);
		$("#totalSign").attr("title",sign);
		$("#planSigncustnum").text(sign);
	}
	
	if(i==2) {
		$("#warpMoney").val(value);
		var money = accAdd(parseFloat($("#userTotalMoney").text()),parseFloat(value));
		
		$("#totalMoney").text(money);
		$("#totalMoney").attr("title",money);
		
		$("#planMoney").text(money);
		
		var guihua = parseFloat($("#guihua").text());
		var cha = accAdd(parseFloat(guihua),-parseFloat($("#totalMoney").text()));
		$("#cha").text(cha);
		$("#cha").attr("title",cha);
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
<form id="monthPlanForm" action="${ctx}/plan/month/team/monthEdit${_v}">
	<input id="isNew" name="isNew" type="hidden" value="${isNew}"/>
	<input id="id" name="id" type="hidden" value="${plan.id}"/>
	<input id="groupId" name="groupId" type="hidden" value="${plan.groupId}"/>
	<input id="groupType" name="groupType" type="hidden" value="0"/>
	
	<input id="planYear" name="planYear" type="hidden" value="${plan.planYear}"/>
	<input id="planMonth" name="planMonth" type="hidden" value="${plan.planMonth}"/>
	
	<input id="warpWillcustnum" name="warpWillcustnum" type="hidden" value="${plan.warpWillcustnum}"/>
	<input id="warpSigncustnum" name="warpSigncustnum" type="hidden" value="${plan.warpSigncustnum}"/>
	<input id="warpMoney" name="warpMoney" type="hidden" value="${plan.warpMoney}"/>
	<input id="warpDesc" name="warpDesc" type="hidden" value="${plan.warpDesc}"/>
	
	<input id="planWillcustnum" name="planWillcustnum" type="hidden" value="${plan.planWillcustnum}"/>
	<input id="planSigncustnum" name="planSigncustnum" type="hidden" value="${plan.planSigncustnum}"/>
	<input id="planMoney" name="planMoney" type="hidden" value="${plan.planMoney}"/>
	
	<input id="usersPlanWillcustnum" name="usersPlanWillcustnum" type="hidden" value="${userPlansSum.planWillcustnum}"/>
	<input id="usersPlanSigncustnum" name="usersPlanSigncustnum" type="hidden" value="${userPlansSum.planSigncustnum}"/>
	<input id="usersPlanMoney" name="usersPlanMoney" type="hidden" value="${userPlansSum.planMoney}"/>
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
						<c:forEach items="${userPlans}" var="userPlan" varStatus="i">
							<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
							<c:if test="${i.index%2==0}"><tr></c:if>
								<td style="width:120px;"><div class="overflow_hidden w120 link">
									<a href="javascript:commontWindow('${userPlan.id}');" class="icon-review" title="点评"></a>
									<c:choose>
										<c:when test="${userPlan.authState == 1}">
										<a href="javascript:back('${userPlan.id}');" class="icon-take-back" title="退回"></a>
										<a href="javascript:authWindow('${userPlan.id}','${userPlan.userName}','${plan.id}');" class="icon-examine" title="审核"></a>
										</c:when>
										<c:otherwise>
										<a href="###" class="icon-take-back img-gray" title="退回"></a>
										<a href="###" class="icon-examine img-gray" title="审核"></a>
										</c:otherwise>
									</c:choose>
									<a href="javascript:showDetail('${userPlan.id}','${userPlan.userName}');" class="icon-detail" title="详情"></a>
									<a href="javascript:showHistory('${userPlan.userId}','${userPlan.userName}');" class="icon-hist-trend" title="历史走势"></a>
								</div></td>
								<td><div class="overflow_hidden w70" title="${userPlan.userName}">${userPlan.userName}</div></td>
								<td><div class="overflow_hidden w70" title="${userPlan.planWillcustnum}">${userPlan.planWillcustnum}</div></td>
								<td><div class="overflow_hidden w70" title="${userPlan.planSigncustnum}">${userPlan.planSigncustnum}</div></td>
								<td><div class="overflow_hidden w70" title="${userPlan.planMoney}">${userPlan.planMoney}</div></td>
								<c:choose>
									<c:when test="${userPlan.authState == 0}"><td><div class="overflow_hidden w120" title="驳回">驳回</div></td></c:when>
									<c:when test="${userPlan.authState == 1}"><td><div class="overflow_hidden w120" title="待审核">待审核</div></td></c:when>
									<c:when test="${userPlan.authState == 2}"><td><div class="overflow_hidden w120" title="通过">通过</div></td></c:when>
								</c:choose>
								<td><div class="overflow_hidden w360" title="${userPlan.authDesc}">${userPlan.authDesc}</div></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="7" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
					</c:otherwise>
				</c:choose>	
				<tr class="sty-bgcolor-b">
					<td colspan="2"><span class="sp  sty-borcolor-b">合计</span></td>
					<td><div id="userTotalWill" class="overflow_hidden w70" title="${userPlansSum.planWillcustnum}">${userPlansSum.planWillcustnum}</div></td>
					<td><div id="userTotalSign" class="overflow_hidden w70" title="${userPlansSum.planSigncustnum}">${userPlansSum.planSigncustnum}</div></td>
					<td><div id="userTotalMoney" class="overflow_hidden w70" title="${userPlansSum.planMoney}">${userPlansSum.planMoney}</div></td>
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
			<a id="upReport" href="javascript:;" class="com-btna com-btna-sty fl_l"><label>上报</label></a>
			<a id="save" href="javascript:;" class="com-btna com-btna-sty fl_l"><label>保存</label></a>
		</div>
	</div>
</div>
</body>
</html>