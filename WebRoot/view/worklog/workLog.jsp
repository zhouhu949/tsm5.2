<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>工作日志-我的日志</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css${_v}"><!--弹框样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<!--本页面样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/worklog/core.css"/><!--弹框插件样式-->
<style type="text/css">
.tree-title{width:52px;}
</style>
<!--本页面js-->
<script type="text/javascript" src="${ctx}/static/js/popup_layer.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/workLog.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/dateUtils.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/date.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/nongli.js${_v}"></script> 
<script type="text/javascript">
var isManager = ${isManager};
var server_dateTime = ${server_dateTime};
var dateTime = ${dateTime};
var resHeight = window.screen.height;
if(resHeight < 790){
  document.write('<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/date_768.css" />');
}
else{
  document.write('<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/date_900.css" />');
}

</script>

</head>
<body> 
<div class="com-contb" style="height:100%;">
	<div class="hyx-wlm-left fl_l">
		<div class="hyx-wlm-left-date sty-borcolor-a com-radius">
			<div class="date_box" id="date_show"></div>
			<div class="hyx-wlm-drop" style="display:none;">
				<h2>未提交列表</h2>
				<ul id="noLogUsers" class="list"></ul>
				<!-- <div class="btn">
					<a class="com-btnc"  href="###"><label>确定</label></a>
				</div> -->
			</div>
		</div>
		<div class="hyx-wlm-left-btn">
			<p id="my_work_log" class="btn sty-borcolor-a btn-hover com-radius fl_l">
				<label class="a">我的日志</label>
				<label class="b b_my"></label>
			</p>
			<p id="share_work_log" class="btn sty-borcolor-a com-radius fl_r">
				<label class="a">点评日志</label>
				<label class="b b_share"></label>
			</p>
		</div>
		<div id="show_tip" class="hyx-wlm-left-tit" style="display: none;">
			<span class="a"></span><label class="lab">全部提交</label>
			<span class="b"></span><label class="lab">有未提交</label>
			<span class="c"></span><label>当天</label>
		</div>
	</div>
		<div class="my-work-righta fl_l">
			<iframe src="${ctx}/worklog/myLog${_v}" id="iframepage" name="iframepage" frameborder="0" scrolling="no" width="100%" height="100%" name="logframes"></iframe>
		</div>
</div>
	<script type="text/javascript"> 
		$(document).ready(function(){
		/* 若分辨率高度<790，iframe的高度为618px，否则为700px */
			var resHeight = window.screen.height;
			if(resHeight < 790){
				$("#iframepage").height(618);
			}else{
				$("#iframepage").height(700);
			}
		});
	</script>
</body>
</html>
