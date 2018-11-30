<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/common/include.jsp"%>
<link rel="stylesheet" href="${ctx }/static/js/view/res/cust_contect.css">
<script type="text/javascript" src="${ctx }/static/js/view/res/to_set_common_owner.js"></script>
<script type="text/javascript" src="${ctx}/static/js/idialog/loading.js"></script>
</head>
<body>
	<form id="commonOwnerForm" action="${ctx }/res/cust/setCommonOwner" method="post">
		<input type="hidden" name="commonAcc" value="" id="commonAcc">
		<input type="hidden" name="commonName" value="" id="commonName">
		<input type="hidden" name="ids" value="${ids }" />
	</form>
	<div class="show-box">
		<div class="shows-idialog-top">
			<div class="shows-input-box">
				<input id="searchText" type="text" class="cust-resson">
				<button id="searchUser" class="cust-resson">查询</button>
				<span class="placehold1">输入帐号/姓名</span>
			</div>
		</div>
		<div class="shows-idialog-middle">
			<div class="shows-middle-title">
				选择共有者
<!-- 				<button class="cancel-check">取消选中</button> -->
			</div>
		</div>
		<div class="shows-idialog-bottom" style="height: auto; text-align: center; margin-top:10px;">
			<button class="confirm">提交</button>
			<button class="cancle-click">取消</button>
		</div>
	</div>
</body>
</html>
