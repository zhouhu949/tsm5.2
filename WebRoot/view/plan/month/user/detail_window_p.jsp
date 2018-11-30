<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-月度计划（团队编辑）-查看页面（已执行）-详细计划</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->

</head>
<body> 
<div class='bomp-cen bomp-mtesa'>
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">重点客户</label>
	</p>
	<div class="com-table hyx-mpe-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</span></th>
					<c:if test="${isState eq 0}"><th><span class='sp sty-borcolor-b'>单位名称</span></th></c:if>
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
							<td><div class="overflow_hidden w110" title="${cust.custName }">${cust.custName }</div></td>
							<c:if test="${isState eq 0}">
									<td><div name="company" class="overflow_hidden w200" title="${cust.company}">${cust.company}</div></td>
								</c:if>
							<td><div name="singcustNum" class="overflow_hidden w110" value="${cust.singcustNum}" title="${cust.singcustNum==0?'签约客户':'意向客户' }">${cust.singcustNum==0?'签约客户':'意向客户' }</div></td>
							<td><div class="overflow_hidden w110" title="${cust.factMoney }/${cust.planMoney }">${cust.factMoney }/${cust.planMoney }</div></td>
							<c:choose>
								<c:when test="${cust.status ==1 or cust.status ==2}"><td><div class="overflow_hidden w110" title="完成">完成</div></td></c:when>
								<c:when test="${cust.status ==0}"><td><div class="overflow_hidden w110" title="未完成">未完成</div></td></c:when>
							</c:choose>
							<td><div class="overflow_hidden w360" title="${cust.context }">${cust.context }</div></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr><td colspan="5" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
				</c:otherwise>
				</c:choose>
				<tr class="sty-bgcolor-b">
					<td colspan="${isState eq 0?3:2}"><div class="overflow_hidden w110">合计</div></td>
					<td><div class="overflow_hidden w110" title="${dto.custCount.factMoney}/${dto.custCount.planMoney}">${dto.custCount.factMoney}/${dto.custCount.planMoney}</div></td>
					<td><div class="overflow_hidden w110" title=""></div></td>
					<td><div class="overflow_hidden w110" title=""></div></td>
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
						<c:when test="${dto.planMonth.waitConfirmCustState ==1 or dto.planMonth.waitConfirmCustState ==2}"><td><div class="overflow_hidden w110" title="完成">完成</div></td></c:when>
						<c:when test="${dto.planMonth.waitConfirmCustState ==0}"><td><div class="overflow_hidden w110" title="未完成">未完成</div></td></c:when>
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
					<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
					<th>完成情况</th>
				</tr>
			</thead>
			<tbody>              
				<tr>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.factWillcustnum}/${dto.planMonth.planWillcustnum}">${dto.planMonth.factWillcustnum}/${dto.planMonth.planWillcustnum}</div></td>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.factSigncustnum}/${dto.planMonth.planSigncustnum}">${dto.planMonth.factSigncustnum}/${dto.planMonth.planSigncustnum}</div></td>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.factMoney}/${dto.planMonth.planMoney}">${dto.planMonth.factMoney}/${dto.planMonth.planMoney}</div></td>
					<c:choose>
						<c:when test="${dto.planMonth.planStatus ==1 or dto.planMonth.planStatus ==2}"><td><div class="overflow_hidden w110" title="完成">完成</div></td></c:when>
						<c:when test="${dto.planMonth.planStatus ==0}"><td><div class="overflow_hidden w110" title="未完成">未完成</div></td></c:when>
					</c:choose>
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
					<td><div class="overflow_hidden w110" title=""><c:choose>
						<c:when test="${dto.planMonth.authState==0}">驳回</c:when>
						<c:when test="${dto.planMonth.authState==1}">待审核</c:when>
						<c:when test="${dto.planMonth.authState==2}">通过</c:when>
					</c:choose></div></td>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.authType}">${dto.planMonth.authType}</div></td>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.authUsername}">${dto.planMonth.authUsername}</div></td>
					<td><div class="overflow_hidden w110" title="<fmt:formatDate pattern="yyyy-MM-dd" value="${dto.planMonth.authTime}"/>"><fmt:formatDate pattern="yyyy-MM-dd" value="${dto.planMonth.authTime}"/></div></td>
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
								<td><div style="width:400px;" class="overflow_hidden" title="${commont.context}">${commont.context}</div></td>
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
	<p class="hyx-mpe-ptit" style="font-size:12px;"><label class="com-red">温馨提示：</label>已签约客户，不计入签约客户统计；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px;font-size:12px;" >偏差值调整，作为本月计划中各个指标的调剂值；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px;font-size:12px;" >本月计划审核截止日期为当月${autoAuthDay}日23:00，届时已上报的未审核的计划将自动审核通过；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px;font-size:12px;">审核方式，分自动和手动两种，自动审核后，审核人默认为“系统”；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px;color:#6b6b6b;font-size:12px;">格式说明：例如，“30/28”，/前表示执行情况，/后表示计划情况。</p>
</div>
</body>
</html>