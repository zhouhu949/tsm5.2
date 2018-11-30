<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html >
<head>
    <meta charset="UTF-8">
    <title>个人中心页面-右侧个人信息</title>
    <%@ include file="/common/include-home-cut-version.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/personal-center.css${_v}"/>
</head>
<body>
	<div class="personal-center-right-head-box">
	<div class="personal-information-box">
			<div class="personal-information-box-items">
				帐号：
			</div>
			${orgmemberuserdto.userAccount }
		</div>
		<div class="personal-information-box">
			<div class="personal-information-box-items">
				姓名：
			</div>
			${orgmemberuserdto.userName }
		</div>
		<div class="personal-information-box">
			<div class="personal-information-box-items">
				性别：
			</div>
			
			<c:choose>
				<c:when test="${orgmemberuserdto.sex ==0}">
				
				</c:when>
				<c:when test="${orgmemberuserdto.sex ==1}">
				男
				</c:when>
				<c:when test="${orgmemberuserdto.sex ==2}">
				女
				</c:when>
			</c:choose>
		</div>
		<div class="personal-information-box">
			<div class="personal-information-box-items">
				职务：
			</div>
			${orgmemberuserdto.post }
		</div>
		<div class="personal-information-box" >
			<div class="personal-information-box-items">
				角色：
			</div>
			<span title="${orgmemberuserdto.roleName }">${orgmemberuserdto.roleName }</span>
		</div>
		<div class="personal-information-box" >
			<div class="personal-information-box-items">
				所在部门：
			</div>
			<span title="${orgmemberuserdto.groupName }">${orgmemberuserdto.groupName }</span>
		</div>
		<div class="personal-information-box">
			<div class="personal-information-box-items">
				出生年月：
			</div>
			<fmt:formatDate value="${orgmemberuserdto.birthday }" pattern="yyyy-MM-dd"/>
		</div>
		<div class="personal-information-box">
			<div class="personal-information-box-items">
				管辖权限：
			</div>
			<span title="${orgmemberuserdto.shareGroupNames }">${orgmemberuserdto.shareGroupNames }</span>
		</div>
		<div class="personal-information-box" >
			<div class="personal-information-box-items">
				通讯地址：
			</div>
			<span title="${orgmemberuserdto.maiLingAddress }">${orgmemberuserdto.maiLingAddress }</span>
		</div>
	</div>
</body>
</html>
