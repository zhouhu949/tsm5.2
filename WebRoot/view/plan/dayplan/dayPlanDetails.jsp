<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@page import="com.qftx.base.enums.FollowCustEnum"%>
<%@ include file="/common/include.jsp" %>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/newdaily_plan.css${_v}"/>
<script type="text/javascript" src="${ctx}/static/js/view/plan/dailyplan/dailyPlan_detail.js${_v}"></script>
<title>日计划详情</title>
<style type="text/css" rel="stylesheet">
<c:forEach items="${sorts}" var="sort">
	.com-table>table>tbody>tr>td:nth-child(${sort}),.com-table>table>thead>tr>th:nth-child(${sort}){display: none;}
</c:forEach>
</style>
</head>
<body>
<form id="dayPlan_detailsForm">
<input type="hidden" id="sudId" name="sudId" value="${sudId}"><!-- 计划ID -->
<input type="hidden" id="dateStr" name="dateStr" value="${dateStr}"><!-- 时间 -->
<input type="hidden" id="type" name="type" value="${type}"><!-- 类型 资源 1  意向 2  签约 3-->
<input type="hidden" id="status" name="status" value="${status}"><!-- 联系状态  已安排 null  已联系 1  未联系 0 -->
<input type="hidden" id="custStatusId" name="custStatusId" value="${custStatusId}"><!-- 销售进程ID-->
<input type="hidden" id="userAcc" name="userAcc" value="${userAcc}"><!-- 用户账号-->
<input type="hidden" id="userId" name="userId" value="${userId}"><!-- 用户ID-->
<form>
<div class="hyx-layout">	
	<div class="hyx-layout-side">
		<div class="hyx-dayplan-left">
			<div class="hyx-dayplan-left-top">
				${dateStr}
				<span class="fa fa-angle-left hyx-dayplan-left-top-btnleft"></span>
				<span class="fa fa-angle-right hyx-dayplan-left-top-btnright"></span>
			</div>
			<ul class="hyx-dayplan-left-bottom">
				<li>
					<a href="javascript:;" class="hyx-dayplan-left-bottom-items  resource-cust <c:if test="${type eq 1 }">current</c:if>"  data-basesrc="/plan/day/resource/view"  data-type="1">资源</a>
				</li>
				<li>
					<a href="javascript:;" class="hyx-dayplan-left-bottom-items  will-cust <c:if test="${type eq 2 && custStatusId eq '' }">current</c:if>"  data-basesrc="/plan/day/willcust/view"  data-type="2">意向客户</a>
					<span class="operate-area"><i class="fa fa-sort-down"></i></span>
					<ul class="arrow-down-ul">
						<c:forEach items="${indexs}" var="index" varStatus="i">
							<li><a href="javascript:;"  class="hyx-dayplan-left-bottom-items <c:if test="${custStatusId eq index.custStatusId }">current</c:if>" data-custStatusId="${index.custStatusId}" data-basesrc="/plan/day/willcust/view"  data-type="2" >${index.custStatus} 	</a></li>
						</c:forEach>
					</ul>
				</li>
				<li>
					<a href="javascript:;" class="hyx-dayplan-left-bottom-items sign-cust <c:if test="${type eq 3 }">current</c:if>"  data-basesrc="/plan/day/signcust/view" data-type="3">签约客户</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="hyx-layout hyx-layout-content" style="padding:0;">
		<c:choose>
			<c:when test="${type == null || type eq 1}">
				<iframe src="${ctx }/plan/day/resource/view?dateStr=${dateStr}&&sudId=${sudId}&&userAcc=${userAcc}&&userId=${userId}&&custStatusId=${custStatusId}&&status=${status}&&type=${type}" width="100%" height="100%" id="iframepage" frameborder="0"  marginheight="0" marginwidth="0"  scrolling="auto" style="overflow-y: auto;" ></iframe>
			</c:when>
			<c:when test="${type eq 2}">
				<iframe src="${ctx }/plan/day/willcust/view?dateStr=${dateStr}&&sudId=${sudId}&&userAcc=${userAcc}&&userId=${userId}&&custStatusId=${custStatusId}&&status=${status}&&type=${type}" width="100%" height="100%" id="iframepage" frameborder="0"  marginheight="0" marginwidth="0"  scrolling="auto" style="overflow-y: auto;" ></iframe>
			</c:when>
				<c:when test="${type eq 3}">
				<iframe src="${ctx }/plan/day/signcust/view?dateStr=${dateStr}&&sudId=${sudId}&&userAcc=${userAcc}&&userId=${userId}&&custStatusId=${custStatusId}&&status=${status}&&type=${type}" width="100%" height="100%" id="iframepage" frameborder="0"  marginheight="0" marginwidth="0"  scrolling="auto" style="overflow-y: auto;" ></iframe>
			</c:when>
		</c:choose>
	</div>
</body>

</html>
