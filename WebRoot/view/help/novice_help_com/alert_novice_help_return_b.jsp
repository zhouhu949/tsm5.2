<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<html>
<head>
<title>新手帮助（系统管理员）-销售进程设计指导完成</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->

<script type="text/javascript">
$(function(){
	/*表单优化*/
    $(".bomp-nha-btn").click(function(){
    	window.parent.$('.i-title').text('系统属性设置');
        window.parent.$("iframe").attr('src','${ctx}/view/help/novice_help_com/alert_novice_help_d.jsp');
	});

 	// 开始配置系统属性设置，销售进程设计
    $(".bomp-nha-btna").click(function(){
    	window.parent.window.location.href = '${ctx}/main?intro=6';
	});
});
</script>
</head>
<body> 
<div class='bomp-nha bomp-nhb bomp-nhc'>
	<p class="qut" style="text-indent:2em;">销售进程设计的过程已经向您演示完毕，接下来，您可以选择“开始配置”配置销售进程，选择“返回上一级”继续系统属性配置引导。</p>
    <a href="javascript:;" class="com-btna sure-btn com-btna-sty bomp-nha-btna fl_l"><label>开始配置</label></a>
	<div class="tit_box fl_r">
		<img src="${ctx}/static/images/hyx_inform.png" width="100px" height="114px">
	</div>
	<a href="javascript:;" class="com-btna sure-btn com-btna-sty bomp-nha-btn fl_l"><label>返回上一级</label></a>
</div>
</body>
</html>