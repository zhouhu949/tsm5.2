<%@page import="com.qftx.tsm.plan.facade.enu.PlanResCR"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${ctx}/static/js/table.js"></script>
<script type="text/javascript" src="${ctx }/static/js/common/button_auth.js${_v}"></script>
<table width="100%" cellspacing="0" cellpadding="0" class="hyx_int_table sty-borcolor-a sty-borcolor-c">
	<thead>
		<tr class="sty-bgcolor-b">
			<th><span class="sp sty-borcolor-b">${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</span></th> 
			<th><span class="sp sty-borcolor-b">联系人</span></th>
			<th><span class="sp sty-borcolor-b">联系电话</span></th>
			<th><span class="sp sty-borcolor-b">资源分组</span></th>
			<th><span class="sp sty-borcolor-b">分配时间</span></th>
			<th><span class="sp sty-borcolor-b">联系结果</span></th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>              
		<c:set var="trun_sign" value="<%=PlanResCR.TURN_SIGN %>"/>
		<c:set var="trun_will" value="<%=PlanResCR.TURN_WILL %>"/>
		<c:set var="turn_high_sea" value="<%=PlanResCR.TURN_HIGH_SEA %>"/>
		<c:forEach items="${list }" var="bean" varStatus="vs">
			<c:if test="${vs.count lt 10 }">
				<tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' } ${bean.permision ? '':'disabled' }">
					<td><div class="overflow_hidden w90" title="${bean.custName }">${bean.custName }</div></td>
					<td><div class="overflow_hidden w70" title="${bean.custUser }">${bean.custUser }</div></td>
					<td><div phone="tel" class="overflow_hidden w90" title="${bean.custTel }">${bean.custTel }</div></td>
					<td><div class="overflow_hidden w80" title="${bean.groupName }">${bean.groupName }</div></td>
					<td><div class="overflow_hidden w130" title="<fmt:formatDate value="${bean.custInputtime }" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${bean.custInputtime }" pattern="yyyy-MM-dd"/></div></td>
					<td><div class="overflow_hidden w80" title="${bean.contactResult }">${bean.contactResult }</div></td>
					<td>
						<div class="overflow_hidden w90 link">
							<c:choose>
								<c:when test="${!bean.permision }">
									<a href="###" class="icon-amoy-gray" title="淘客户"></a>
									<a href="###" btn-type="auth" auth_id="base_signCust" class="icon-sign-gray" title="签约"></a>
									<a href="###" class="icon-cust-card img-gray" title="客户卡片"></a>
								</c:when>
								<c:when test="${bean.contactResult eq trun_sign.result || bean.contactResult eq turn_high_sea.result}">
									<a href="###" class="icon-amoy-gray" title="淘客户"></a>
									<a href="###" btn-type="auth" auth_id="base_signCust" class="icon-sign-gray" title="签约"></a>
									<a href="###" id="cardInfo_${bean.custId }" custName="${shiroUser.isState eq 1 ? bean.custName : (empty bean.company ? bean.custName : bean.company) }" class="icon-cust-card" title="客户卡片"></a>
								</c:when>
								<c:when test="${bean.contactResult eq trun_will.result }">
									<a href="###" class="icon-amoy-gray" title="淘客户"></a>
									<a href="###"  btn-type="auth" auth_id="base_signCust" <c:if test="${shiroUser.serverDay gt 0}">id="sign_${bean.custId }"</c:if> signSetting="${signSetting }" class="icon-sign-cont ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="签约"></a>
									<a href="###" id="cardInfo_${bean.custId }" custName="${shiroUser.isState eq 1 ? bean.custName : (empty bean.company ? bean.custName : bean.company) }" class="icon-cust-card" title="客户卡片"></a>
								</c:when>
								<c:otherwise>
									<a href="###" <c:if test="${shiroUser.serverDay gt 0}">id="tao_${bean.custId }"</c:if> class="icon-amoy-cust ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="淘客户"></a>
									<a href="###" btn-type="auth" auth_id="base_signCust" <c:if test="${shiroUser.serverDay gt 0}">id="sign_${bean.custId }"</c:if> signSetting="${signSetting }" class="icon-sign-cont ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="签约"></a>
									<a href="###" id="cardInfo_${bean.custId }" custName="${shiroUser.isState eq 1 ? bean.custName : (empty bean.company ? bean.custName : bean.company) }" class="icon-cust-card" title="客户卡片"></a>
								</c:otherwise>
							</c:choose>
						</div>
					</td>
				</tr>
			</c:if>
		</c:forEach>
		<c:if test="${fn:length(list) lt 9 }">
			<c:forEach var="n" begin="1" end="${9-fn:length(list) }">
				<tr class="${(n+fn:length(list))%2 ne 0 ? '' : 'sty-bgcolor-b' }">
					<td><div class="overflow_hidden w90"></div></td>
					<td><div class="overflow_hidden w70"></div></td>
					<td><div class="overflow_hidden w90"></div></td>
					<td><div class="overflow_hidden w80"></div></td>
					<td><div class="overflow_hidden w130"></div></td>
					<td><div class="overflow_hidden w80"></div></td>
					<td><div class="overflow_hidden w90 link"></div></td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
<!-- <div class="com-bot" > -->
<%-- 	<div class="com-page fl_r">${pdrBean.page.pageStr }</div> --%>
<!-- </div> -->