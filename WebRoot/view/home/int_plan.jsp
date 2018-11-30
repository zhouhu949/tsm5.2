<%@page import="com.qftx.tsm.plan.facade.enu.PlanWillCR"%>
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
			<c:if test="${hasPlan eq 1 }">
				<th><span class="sp sty-borcolor-b">联系结果</span></th>
			</c:if>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>              
		<c:choose>
			<c:when test="${hasPlan eq 1 }">
				<c:set var="trun_sign" value="<%=PlanWillCR.TURN_SIGN %>" />
				<c:set var="turn_high_sea" value="<%=PlanWillCR.TURN_HIGH_SEA %>" />
				<c:forEach items="${list }" var="bean" varStatus="vs">
					<c:if test="${vs.count lt 10 }">
						<tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">
							<td><div class="overflow_hidden w90" title="${bean.custName }">${bean.custName }</div></td>
							<td><div class="overflow_hidden w70" title="${bean.custUser }">${bean.custUser }</div></td>
							<td><div phone="tel" class="overflow_hidden w90" title="${bean.custTel }">${bean.custTel }</div></td>
							<td><div class="overflow_hidden w80" title="${bean.groupName }">${bean.groupName }</div></td>
							<td><div class="overflow_hidden w130" title="<fmt:formatDate value="${bean.custInputtime }" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${bean.custInputtime }" pattern="yyyy-MM-dd"/></div></td>
							<td><div class="overflow_hidden w80" title="${bean.contactResultStr }">${bean.contactResultStr }</div></td>
							<td>
								<div class="overflow_hidden w90 link">
									<c:choose>
										<c:when test="${bean.contactResult eq trun_sign.result }">
											<a href="###" btn-type="auth" auth_id="base_followCust" <c:if test="${shiroUser.serverDay gt 0}">id="follow_${bean.custId }" custType="2"</c:if> class="icon-follow-up ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="跟进"></a>
											<a href="###" btn-type="auth" auth_id="base_signCust" class="icon-sign-gray" title="签约"></a>
											<a href="###" id="cardInfo_${bean.custId }" custName="${shiroUser.isState eq 1 ? bean.custName : (empty bean.company ? bean.custName : bean.company)  }" class="icon-cust-card" title="客户卡片"></a>
										</c:when>
										<c:when test="${bean.contactResult eq turn_high_sea.result }">
											<a href="###" btn-type="auth" auth_id="base_followCust" class="icon-follow-gray" title="跟进"></a>
											<a href="###" btn-type="auth" auth_id="base_signCust" class="icon-sign-gray" title="签约"></a>	
											<a href="###" id="cardInfo_${bean.custId }" custName="${shiroUser.isState eq 1 ? bean.custName : (empty bean.company ? bean.custName : bean.company)  }" class="icon-cust-card" title="客户卡片"></a>
										</c:when>
										<c:otherwise>
											<a href="###" btn-type="auth" auth_id="base_followCust" <c:if test="${shiroUser.serverDay gt 0}">id="follow_${bean.custId }" custType="2"</c:if> class="icon-follow-up ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="跟进"></a>
											<a href="###" btn-type="auth" auth_id="base_signCust" <c:if test="${shiroUser.serverDay gt 0}">id="sign_${bean.custId }"</c:if> signSetting="${signSetting }" class="icon-sign-cont ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="签约"></a>						
											<a href="###" id="cardInfo_${bean.custId }" custName="${shiroUser.isState eq 1 ? bean.custName : (empty bean.company ? bean.custName : bean.company)  }" class="icon-cust-card" title="客户卡片"></a>
										</c:otherwise>
									</c:choose>
								</div>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list }" var="bean" varStatus="vs">
					<c:if test="${vs.count lt 10 }">
						<tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">
							<td><div class="overflow_hidden w90" title="${bean.name }">${bean.name }</div></td>
							<td><div class="overflow_hidden w70" title="${bean.mainLinkman }">${bean.mainLinkman }</div></td>
							<td><div phone="tel" class="overflow_hidden w90" title="${empty bean.mobilephone ? bean.telphone : bean.mobilephone }">${empty bean.mobilephone ? bean.telphone : bean.mobilephone}</div></td>
							<td><div class="overflow_hidden w80" title="${bean.groupName }">${bean.groupName }</div></td>
							<td><div class="overflow_hidden w130" title="<fmt:formatDate value="${bean.ownerStartDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${bean.ownerStartDate }" pattern="yyyy-MM-dd"/></div></td>
							<td>
								<div class="overflow_hidden w90 link">
									<a href="###" btn-type="auth" auth_id="base_followCust" <c:if test="${shiroUser.serverDay gt 0}">id="follow_${bean.resCustId }" custType="2"</c:if> class="icon-follow-up ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="跟进"></a>
									<a href="###" btn-type="auth" auth_id="base_signCust" <c:if test="${shiroUser.serverDay gt 0}">id="sign_${bean.resCustId }"</c:if> signSetting="${signSetting }" class="icon-sign-cont ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="签约"></a>
									<a href="###" id="cardInfo_${bean.resCustId }" custName="${shiroUser.isState eq 1 ? bean.name : (empty bean.company ? bean.name : bean.company) }" class="icon-cust-card" title="客户卡片"></a>
								</div>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		<c:if test="${fn:length(list) lt 9 }">
			<c:forEach var="n" begin="1" end="${9-fn:length(list) }">
				<tr class="${(n+fn:length(list))%2 ne 0 ? '' : 'sty-bgcolor-b' }">
					<td><div class="overflow_hidden w90"></div></td>
					<td><div class="overflow_hidden w70"></div></td>
					<td><div class="overflow_hidden w90"></div></td>
					<td><div class="overflow_hidden w80"></div></td>
					<td><div class="overflow_hidden w130"></div></td>
					<c:if test="${hasPlan eq 1 }">
						<td><div class="overflow_hidden w80"></div></td>
					</c:if>
					<td><div class="overflow_hidden w90 link"></div></td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>