<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<c:if test="${!empty deptList }">
	<ul style="display: none;margin-left:10px;">
		<c:forEach var="dept" items="${deptList }">
			<li class="${!empty dept.children ? 'expand':''}" ><input type="radio" name="deptNode" class="check" value="${dept.id }" /><c:if test="${!empty dept.children }"><span class="triangle-down"></span></c:if><a href="javascript:;" style="color:#000;">${dept.text }</a></li>
			<c:if test="${!empty dept.children }">
				<c:set var="deptList" value="${dept.children }" scope="request"></c:set>
				<c:import url="/view/tsm/mem_role/idialog/treeDept.jsp"></c:import>
			</c:if>
		</c:forEach>
	</ul>
</c:if>