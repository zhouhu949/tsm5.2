<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-月度计划（个人）-月度计划查看（个人）</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<script type="text/javascript">
$(function(){

    $("#back").click(function(){
		$.post(ctx+"/plan/month/user/bakReport",{"planId":"${dto.planMonth.id}"},function(data){
			if(data.status=="success"){
				setTimeout('window.top.$("#centerTabs").tabs("close","个人月计划-查看");',10);
				bakTab();
			}
		});
	});
});

function bakTab(){
	window.top.addTab(ctx+"/plan/month/user/view${_v}","个人月计划");
}
</script>
</head>
<body> 
<div class="com-conta" style="padding-bottom: 60px;">
	<h2 class="hyx-mpe-h2">${dto.planMonth.planYear}年${dto.planMonth.planMonth}月份计划</h2>
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">重点客户</label>
	</p>
	<div class="com-table hyx-mpe-table hyx-cm-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</span></th>
					<c:if test="${isState eq 0}"><th><span class='sp sty-borcolor-b'>单位名称</span></th></c:if>
					<th><span class="sp sty-borcolor-b">销售进程</span></th>
					<th><span class="sp sty-borcolor-b">客户状态</span></th>
					<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
					<th><span class="sp sty-borcolor-b">完成情况</span></th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${dto!=null and !empty dto.custs}">
						<c:forEach items="${dto.custs}" var="cust" varStatus="i">
							<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
							<c:if test="${i.index%2==0}"><tr></c:if>
								<td><div class="overflow_hidden w90" title="${cust.custName }">${cust.custName }</div></td>
								<c:if test="${isState eq 0}">
									<td><div class="overflow_hidden w180" title="${cust.company}">${cust.company}</div></td>
								</c:if>
								<td><div class="overflow_hidden w100" title="${cust.custStatus }">${cust.custStatus }</div></td>
								<td><div name="singcustNum" class="overflow_hidden w110" title="${cust.singcustNum==0?'签约客户':'意向客户' }">${cust.singcustNum==0?'签约客户':'意向客户' }</div></td>
								<td><div class="overflow_hidden w80" title="${cust.factMoney }/${cust.planMoney }">${cust.factMoney }/${cust.planMoney }</div></td>
								<c:choose>
									<c:when test="${cust.status ==1 or cust.status ==2}"><td><div class="overflow_hidden w80" title="完成">完成</div></td></c:when>
									<c:when test="${cust.status ==0}"><td><div class="overflow_hidden w80" title="未完成">未完成</div></td></c:when>
								</c:choose>
								
								<td><div class="overflow_hidden w360" title="${cust.context }">${cust.context }</div></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="6" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
					</c:otherwise>
				</c:choose>
				<tr class="sty-bgcolor-b">
					<td colspan="${isState eq 0?4:3}"><span class="sp  sty-borcolor-b">合计</span></td>
					<td><div class="overflow_hidden w100" title="${dto.custCount.factMoney}/${dto.custCount.planMoney}">${dto.custCount.factMoney}/${dto.custCount.planMoney}</div></td>
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
					<th><span class="sp sty-borcolor-b">新增意向数（个）</span></th>
					<th><span class="sp sty-borcolor-b">新增签约数（个）</span></th>
					<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
					<th><span class="sp sty-borcolor-b">完成情况</span></th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>              
				<tr>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.factWillcustnumAdd}/${dto.planMonth.planWillcustnumAdd}">${dto.planMonth.factWillcustnumAdd}/${dto.planMonth.planWillcustnumAdd}</div></td>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.factSigncustnumAdd}/${dto.planMonth.planSigncustnumAdd}">${dto.planMonth.factSigncustnumAdd}/${dto.planMonth.planSigncustnumAdd}</div></td>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.factWillcustnumMoney}/${dto.planMonth.planWillcustnumMoney}">${dto.planMonth.factWillcustnumMoney}/${dto.planMonth.planWillcustnumMoney}</div></td>
					
					<c:choose>
						<c:when test="${dto.planMonth.waitConfirmCustState ==1 or dto.planMonth.waitConfirmCustState ==2}"><td><div class="overflow_hidden w120" title="完成">完成</div></td></c:when>
						<c:when test="${dto.planMonth.waitConfirmCustState ==0}"><td><div class="overflow_hidden w120" title="未完成">未完成</div></td></c:when>
					</c:choose>
					<td><div class="overflow_hidden w360" title="${dto.planMonth.planWillcustnumDesc}">${dto.planMonth.planWillcustnumDesc}</div></td>
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
					<th>回款金额（万）</th>
			</thead>
			<tbody>              
				<tr>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.factWillcustnum}/${dto.planMonth.planWillcustnum}">${dto.planMonth.factWillcustnum}/${dto.planMonth.planWillcustnum}</div></td>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.factSigncustnum}/${dto.planMonth.planSigncustnum}">${dto.planMonth.factSigncustnum}/${dto.planMonth.planSigncustnum}</div></td>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.factMoney}/${dto.planMonth.planMoney}">${dto.planMonth.factMoney}/${dto.planMonth.planMoney}</div></td>
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
						<c:when test="${dto.planMonth.authState == 0}"><td><div class="overflow_hidden w110" title="驳回">驳回</div></td></c:when>
						<c:when test="${dto.planMonth.authState == 1}"><td><div class="overflow_hidden w110" title="待审核">待审核</div></td></c:when>
						<c:when test="${dto.planMonth.authState == 2}"><td><div class="overflow_hidden w110" title="通过">通过</div></td></c:when>
					</c:choose>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.authType}">${dto.planMonth.authType}</div></td>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.authUsername}">${dto.planMonth.authUsername}</div></td>
					<td><div class="overflow_hidden w120" title="<fmt:formatDate pattern="yyyy-MM-dd" value="${dto.planMonth.authTime}"/>"><fmt:formatDate pattern="yyyy-MM-dd" value="${dto.planMonth.authTime}"/></div></td>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.authDesc}">${dto.planMonth.authDesc}</div></td>
				</tr>
			</tbody>
		</table>
	</div>
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">点评信息</label>
	</p>
	<div class="com-table hyx-mpe-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">点评人</span></th>
					<th><span class="sp sty-borcolor-b">点评时间</span></th>
					<th><span class="sp sty-borcolor-b">点评内容</span></th>
				</tr>
			</thead>
			<tbody>
			<c:choose>
				<c:when test="${dto!=null and !empty dto.userPlanCommonts}">
					<c:forEach items="${dto.userPlanCommonts}" var="commont" varStatus="i">
						<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
						<c:if test="${i.index%2==0}"><tr></c:if>
							<td><div class="overflow_hidden w70" title="${commont.commontUserName}">${commont.commontUserName}</div></td>
							<td><div class="overflow_hidden w90" title="<fmt:formatDate pattern="yyyy-MM-dd" value="${commont.inputtime}"/>"><fmt:formatDate pattern="yyyy-MM-dd" value="${commont.inputtime}"/></div></td>
							<td><div style="width:700px;" class="overflow_hidden" title="${commont.context}">${commont.context}</div></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr><td colspan="5" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
	</div>
	<p class="hyx-mpe-ptit"><label class="com-red">温馨提示：</label>已签约客户，不计入签约客户统计；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px" >偏差值调整，作为本月计划中各个指标的调剂值；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px" >本月计划审核截止日期为当月${autoAuthDay}日23:00，届时已上报的未审核的计划将自动审核通过；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px">审核方式，分自动和手动两种，自动审核后，审核人默认为“系统”；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px;color:#6b6b6b;">格式说明：例如，“30/28”，/前表示执行情况，/后表示计划情况。</p>
	
	<c:if test="${dto.planMonth.status==1 and dto.planMonth.authState==1}">
	<div class="com-btnbox" style="width:405px;padding-bottom:60px;">
		<div class="com-btnbox-cen">
				<a id="back"  href="javascript:;" class="com-btna com-btna-sty fl_l"><label>撤回计划</label></a>
		</div>
	</div>
	</c:if>
</div>
</body>
</html>