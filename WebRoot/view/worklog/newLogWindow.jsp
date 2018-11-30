<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
	<title>新建日志</title>
	<!--公共样式-->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css${_v}"><!--弹框样式-->
	<!--本页面样式-->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
	<!--本页面js-->
	<script type="text/javascript" src="${ctx}/static/js/view/worklog/newLogWindow.js${_v}"></script>
	<script type="text/javascript" src="${ctx}/static/js/view/worklog/dateUtils.js${_v}"></script>
	<script type="text/javascript">
	var wliId="${wliId}";
	if(wliId==""){
		wliId=null;
	}
	</script>
</head>
<body>
	<div class="bomp-cen main">
		<p class="bomb-datea">${title }</p>
		<div class="new-log-text-box">
			<p class="bomp-pos-title-new">今日工作总结</p>
			<p class="bomp-pos-a">
				<textarea id="new_log_text" class="bomb-areaa bomp-focus bomb-textarea-lessheight" placeholder="最多可输入2000个汉字。">${context }</textarea>
				<%-- <label class="lab_hid" style="left:15px;"></label> --%>
			</p>
		</div>
		<div class="new-log-text-box">
			<p class="bomp-pos-title-new">明日工作计划</p>
			<p class="bomp-pos-a">
				<textarea id="new_log_workPlan" class="bomb-areaa bomp-focus bomb-textarea-lessheight" placeholder="最多可输入2000个汉字。">${workPlan }</textarea>
				<%-- <label class="lab_hid" style="left:15px;"></label> --%>
			</p>
		</div>
		<div class="bomb-btn">
			<label class="bomb-btn-cen">
				<a id="commit" href="javascript:;" class="com-btna com-btna-sty fl_l"><label>发布</label></a>
				<a id="save" href="javascript:;" class="com-btna com-btna-sty fl_l"><label>保存</label></a>
				<a id="cancel" href="javascript:;" class="com-btna fl_l"><label>取消</label></a>
			</label>
		</div>
	</div>
</body>
</html>
