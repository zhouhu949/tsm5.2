<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>工作日志-我的日志</title>
<script type="text/javascript" src="${ctx}/static/js/base64.js${_v}"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css${_v}"><!--弹框样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/core.css"/><!--弹框插件样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/worklog/newMyLog.css${_v}"/>
<!--本页面js-->
<script type="text/javascript" src="${ctx}/static/js/view/worklog/myLog.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/dateUtils.js${_v}"></script>
<script type="text/javascript">
/* window.parent.pageType=null; */

var resHeight = window.screen.height;
if(resHeight < 790){
	document.write('<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/date_768.css" />');
}else{
	document.write('<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/date_900.css" />');
}
</script>
<script type="text/javascript">
$(function(){
	/*log加载*/
    $('.timeline').each(function(i,item){
        $('.hyx-wlm-cent-timeline').scroll(function(){
        		var $logflag = $("#logAjaxFlag");
        		if($(this)[0].scrollTop + $(this).height() >= $(this)[0].scrollHeight){
					var data={ "logDateTime":  $(".right:last").attr("datetime"),"type":"last"};
					if($logflag.val()){
						$logflag.val("false")
	                	getData(data,true);
					}
	            }	
	            if($(this)[0].scrollTop == 0){
	            	var data={ "logDateTime":  $(".right:first").attr("datetime"),"type":"first"};
	            	if($logflag.val()){
						$logflag.val("false")
	            		getData(data,true);
					}
	            }
        
         });
    });
    //第一次请求数据
     getData({"logDateTime": $("#logDateTime").val()},false);

	});

	/* window.onload = function() {
		var height = $(".hyx-wlm-cent").height() + 30;
		window.parent.$("#iframepage").css({
			"height" : height + "px"
		});
	}; */
</script>

</head>
<body>
<form action="${ctx}/worklog/myLog${_v}"> 
	<input type="hidden" id="logDateTime" name="logDateTime" value="${item.logDateTime }">
	<input type="hidden" id="logAjaxFlag"  value="true">
</form>
	<div class="hyx-wlm-cent hyx-wlm-centa sty-borcolor-a com-radius fl_l">
		<div class="hyx-wlm-cent-bg sty-bgcolor-b"></div>
		<div class="hyx-wlm-cent-box">
			<div class="hyx-wlm-cent-btn">
				<a href="javascript:defShareWindow();" class="com-btna fl_r" id="share_config"><label>分享设置</label></a>
				<a href="javascript:;" class="com-btna work-log-c fl_r" id="new_log_window_open_btn"><label>新建日志</label></a>
			</div>
			<div class="hyx-wlm-cent-timeline">
		    	<div class="timeline">
					<ul>
						<!-- 日志加载容器 -->
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
