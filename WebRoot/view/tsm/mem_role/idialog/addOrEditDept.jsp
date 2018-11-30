<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新增或编辑组织结构</title>
	<%@ include file="/common/include.jsp"%>
	<style type="text/css">
		.com-bomo-p{margin-top: 10px;}
		.com-bomo-p label{width: 140px;text-align: right;line-height: 25px;display: inline-block;color: #656565;}
		i.com-import{color: red;}
		.com-bomo-p .w_50_left{width:50px;text-align:left;}
		.tree-node{width: 180px;}
	</style>
</head>
<body>
	<!-- 弹框开始 -->
	<div class="com-bomo bomp-addor">
	    <input type="hidden" id="shrioUserAccount" value="${shrioUserAccount}">
		<form id="deptAddForm" action="${ctx }/auth/user/saveDept" method="POST">
			<div class="com-bomo-p">
				<label><i class="com-import">* </i>上级部门：</label>
				<input id="deptComboTree" name="deptComboTree" style="width: 200px;" type="text" value="">
				<input type="hidden" id="parentId" validate="chk_2_1_0" name="parentId" value="${item.pId }" />
				<span class="com-red com-ml10"></span>
				<input type="hidden" id="deptId" name="deptId" value="${item.groupId }" />
			</div>
			<div class="com-bomo-p">
				<label><i class="com-import">* </i>部门名称：</label>
				<input style="width:200px;" type="text" name="deptName" maxlength="25" validate="chk_1_1_0" value="${item.groupName }">
				<span class="com-red com-ml10"></span>
			</div>
			<div class="com-bomo-p">
				<label><i class="com-import">* </i>部门排序：</label>
				<input style="width:200px;" type="text" name="sort" validate="chk_1_1_0" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" validate="chk_1_0_3" value="${item.groupIndex }">
				<span class="com-red com-ml10"></span>
			</div>
			<div class="com-bomo-p">
				<label><i class="com-import">* </i>部门类型：</label>
				<input type="radio" name="deptType" class="radio" value="1" ${item.groupType eq 1 ? 'checked':'' }/><label class="w_50_left">销售</label>
				<input type="radio" name="deptType" class="radio" value="2" ${item.groupType eq 2 ? 'checked':'' } /><label class="w_50_left">客服</label>
				<input type="hidden"  id="deptType" validate="chk_1_1_0" value="${item.groupType }">
				<span class="com-red com-ml10"></span>
			</div>
		</form>
		<div class="sure-cancle clearfix">
			<a href="javascript:;" class="com-btna bomp-btna com-btna-sty fl_l"  id="saveBtn"><label>保存</label></a>
			<a href="javascript:;" class="com-btna bomp-btna fl_l" id="cancelBtn"><label>取消</label></a>
		</div>
	</div>
	<!-- 弹框结束 -->
	<script type="text/javascript" src="${ctx }/static/js/view/tsm/mem_role/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx }/static/js/view/tsm/mem_role/obhCheck.js"></script>
	<script type="text/javascript" src="${ctx }/static/js/view/tsm/mem_role/addOrEditDept.js"></script>
</body>
</html>