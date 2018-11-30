<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>变更部门</title>
<script type="text/javascript" src="${ctx }/static/js/view/tsm/mem_role/changeDept.js${_v}"></script>
<style>
.changeMember{display:block;margin:10px 0 10px 15px;}
.tree-box{padding-left:30px; height:240px; overflow-y:auto;}
.shows-changeText{width:200px;height:25px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;text-align:left;color:#000;}
.st_tree li{margin-top:10px;}
.triangle-down{display: none;height:0;content:"";border-top:7px solid #000;border-left:4px solid transparent;border-right:4px solid transparent;position: relative;top:-2px;left: 2px;margin-right:5px;cursor:pointer;}
.expand .triangle-down{display: inline-block;}
</style>
</head>
<body>
<!-- 弹框开始 -->
	<div class="com-bomo" style="margin-top:0;">
		<div class="com-bomo-p">
			<input type="hidden" id="ids" value="${ids }" />
			<input type="hidden" id="accounts" value="${accounts }" />
			<label class="changeMember">将选中成员调整到：</label>
			<div class="tree-box bomo-tree">
				<div class="st_tree">
					<ul>
						<c:forEach var="dept" items="${deptList }">
							<li class="${!empty dept.children ? 'expand':''}">
								<input type="radio" name="deptNode" class="check" value="${dept.id }" />
								<span class="triangle-down"></span>
								<a href="javascript:;"  class="shows-changeText">${dept.text }</a>
							</li>
							<c:if test="${!empty dept.children }">
								<c:set var="deptList" value="${dept.children }" scope="request"></c:set>
								<c:import url="/view/tsm/mem_role/idialog/treeDept.jsp"></c:import>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
		<p class="reminder" style="text-indent: 20px;"><span class="com-red">温馨提示：</span>变更部门同时需要注意管理管辖权限</p>
		<div class="sure-cancle clearfix com-bomo-btn">
				<a href="javascript:;" class="com-btna bomp-btna com-btna-sty fl_l w68"  id="okBtn" ><label>确定</label></a>
				<a href="javascript:;" class="com-btna bomp-btna fl_l w68" id='cancleBtn' ><label>取消</label></a>
		</div>
	</div>
<!-- 弹框结束 -->
</body>
</html>