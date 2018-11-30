<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html >
<head>
    <meta charset="UTF-8">
    <title>个人中心编辑页面（弹窗）</title>
    <%@ include file="/common/include-home-cut-version.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/font-awesome-4.7.0/css/font-awesome.min.css${_v}"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/personal-center.css${_v}"/>
    <script type="text/javascript" src="${ctx}/static/js/time/moment.min.js"></script><!--选择区域日期插件-->
    <script type="text/javascript" src="${ctx}/static/js/time/jquery.daterangepicker.js${_v}"></script><!--选择区域日期插件-->
    <script type="text/javascript" src="${ctx}/static/js/my97datepicker/WdatePicker.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/view/home/personal_center_edit.js${_v}"></script>
</head>
<body>
<input type="hidden" id="birthdayOpen" value="${isOpen }">
	<form id="editForm" action="${ctx }/user/updateUser" method="post">
	<input type="hidden" name="mail" value="${orgmemberuserdto.mail }"/>
	<div class='bomp-cen bomp_change project-comp-idialog-box' >
		<div class="personal-edit-rows">
			<label class="personal-edit-rows-labels">帐号：</label>
			<span class="personal-edit-rows-spans" title="${orgmemberuserdto.userAccount }">${orgmemberuserdto.userAccount }</span>
		</div>
		<div class="personal-edit-rows">
			<label class="personal-edit-rows-labels">所在部门：</label>
			<span class="personal-edit-rows-spans" title="${orgmemberuserdto.groupName }">${orgmemberuserdto.groupName }</span>
		</div><div class="personal-edit-rows">
			<label class="personal-edit-rows-labels">姓名：</label>
			<input type="text"  name="userName" class="personal-edit-rows-inputs" value="${orgmemberuserdto.userName }"/>
			<input type="hidden"  name="mobile" class="personal-edit-rows-inputs" value="${orgmemberuserdto.mobile }"/>
		
		</div>
		<div class="personal-edit-rows">
			<label class="personal-edit-rows-labels">性别：</label>
			<select class="personal-edit-rows-select" name="sex">
				<option value="0"  ${ orgmemberuserdto.sex == 0 ? "selected":"" } >-请选择-</option>
				<option value="1" ${ orgmemberuserdto.sex == 1 ? "selected":"" }>男</option>
				<option value="2" ${ orgmemberuserdto.sex == 2 ? "selected":"" }>女</option>
			</select>
		</div>
		<div class="personal-edit-rows">
			<label class="personal-edit-rows-labels">职务：</label>
			<input type="hidden" name="post" value='${orgmemberuserdto.post }'/>
			<span class="personal-edit-rows-spans" title="${orgmemberuserdto.post }">${orgmemberuserdto.post }</span>
		</div>
		<c:choose>
			<c:when test="${isOpen eq 1 && not empty orgmemberuserdto.birthday }">
				<div class="personal-edit-rows">
					<label class="personal-edit-rows-labels">出生年月：</label>
					<input type="hidden" name="birthday" value='<fmt:formatDate value="${orgmemberuserdto.birthday }" pattern="yyyy-MM-dd"/>'/>
					<span class="personal-edit-rows-spans" title='<fmt:formatDate value="${orgmemberuserdto.birthday }" pattern="yyyy-MM-dd"/>'><fmt:formatDate value="${orgmemberuserdto.birthday }" pattern="yyyy-MM-dd"/></span>
				</div>
			</c:when>
			<c:otherwise>
				<div class="personal-edit-rows">
					<label class="personal-edit-rows-labels">出生年月：</label>
					<input type="text" class="personal-edit-rows-inputs" name="birthday" value='<fmt:formatDate value="${orgmemberuserdto.birthday }" pattern="yyyy-MM-dd"/>'/>
				</div>
			</c:otherwise>
			</c:choose>
		<div class="personal-edit-rows">
			<label class="personal-edit-rows-labels">角色：</label>
			<span class="personal-edit-rows-spans" title="${orgmemberuserdto.roleName }">${orgmemberuserdto.roleName }</span>
		</div>
		<div class="personal-edit-rows">
			<label class="personal-edit-rows-labels">管辖权限：</label>
			<span class="personal-edit-rows-spans" title="${orgmemberuserdto.shareGroupNames }">${orgmemberuserdto.shareGroupNames }</span>
		</div>
		<div class="personal-edit-rows">
			<label class="personal-edit-rows-labels">通讯地址：</label>
			<textarea class="personal-edit-rows-textarea" name="maiLingAddress">${orgmemberuserdto.maiLingAddress }</textarea>
		</div>
		<!-- 底部按钮组开始 -->
		<div class='bomb-btn bomb-btn-top bomb-btn-change project-comp-idialog-btnbox' style="padding-bottom:15px;">
				<label class='bomb-btn-cen'>
					<a href="###" id="saveResBtn" class="com-btna bomp-btna sure-btn com-btna-sty fl_l"><label>保存</label></a>
					<a href="###" id="cacleResBtn" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
				</label>
		</div>
</div>
</form>
</body>
</html>
