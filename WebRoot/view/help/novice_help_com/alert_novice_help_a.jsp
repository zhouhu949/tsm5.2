<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<html>
<head>
<title>新手帮助（系统管理员）-系统设置引导说明</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
<style type="text/css">
.i-close{display:none;}
</style>
<script type="text/javascript">
$(function(){
	/*表单优化*/
    $(".sure-btn").click(function(){
    	window.parent.$('.i-title').text('设置引导首页');
    	document.location.href = "${ctx}/view/help/novice_help_com/alert_novice_help_c.jsp";
    	//window.parent.$("iframe").attr('src','${ctx}/view/help/novice_help_com/alert_novice_help_c.jsp');
	});

    window.parent.$('.i-close').hide();
    
});
</script>
</head>
<body> 
<div class='bomp-nha'>
	<div class="tit_box">
		<img src="${ctx}/static/images/hyx_inform_01.png" width="401px" height="311px" />
	</div>
	<a href="javascript:;" class="com-btna sure-btn com-btna-sty bomp-nha-btn fl_l"><label>新手引导>></label></a>
</div>
</body>
</html>