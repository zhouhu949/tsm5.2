<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<c:if test="${!empty deptList }">
	<ul style="display: none;margin-left:10px;">
		<c:forEach var="dept" items="${deptList }">
			<li class="${!empty dept.children ? 'expand':''}">
				<input type="radio" name="deptId" class="check" value="${dept.id }"  <c:if test="${haveResDeptIdss eq dept.id }">disabled</c:if> />
				<c:if test="${!empty dept.children }">
					<span class="triangle-down icon_down_px"></span>
				</c:if>
				<a href="javascript:;"  class="shows-changeText">${dept.text }</a>
			</li>
			<c:if test="${!empty dept.children }">
				<c:set var="deptList" value="${dept.children }" scope="request"></c:set>
				<c:import url="/view/tsm/mem_role/member/change_resources_org_tree.jsp"></c:import>
			</c:if>
		</c:forEach>
	</ul>
</c:if>
