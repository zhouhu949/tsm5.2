<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<html>
<head>
<title>新手帮助（系统管理员）-设置引导首页</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->

<script type="text/javascript">
$(function(){
	/*表单优化*/
    $(".sure-btn").click(function(){
    		window.parent.window.location.href = '${ctx}/main';
	});
	$(".novice_b").click(function(){
		window.parent.window.location.href = '${ctx}/view/help/novice_help_com/add_role.jsp';
	});
	$(".novice_c").click(function(){
	    	document.location.href = '${ctx}/view/help/novice_help_com/alert_novice_help_d.jsp';
	});
	$(".novice_d").click(function(){
		window.parent.window.location.href = '${ctx}/view/help/novice_help_com/sales_manag_set.jsp';
	});
	$(".novice_e").click(function(){
		window.parent.window.location.href = '${ctx}/view/help/novice_help_com/sys_field_set_enter.jsp';
	});
	$(".novice_f").click(function(){
		window.parent.window.location.href = '${ctx}/view/help/novice_help_com/mess_set.jsp';
	});

	window.parent.$('.i-close').show();
    
});

</script>
</head>
<body> 
<div class='bomp-nha bomp-nhb'>
	<div class="list">
		<p class="fl_l">
			<i class="i_checked fl_l"></i>
			<label class="lab fl_l">角色管理设置</label>
			<a href="javascript:;" class="com-btna com-btna-sty novice_b fl_l"><label>新手指导</label></a>
		</p>
		<p class="fl_l">
			<i class="fl_l"></i>
			<label class="lab fl_l">系统属性设置</label>
			<a href="javascript:;" class="com-btna com-btna-sty novice_c fl_l"><label>新手指导</label></a>
		</p>
		<p class="fl_l">
			<i class="fl_l"></i>
			<label class="lab fl_l">销售管理设置</label>
			<a href="javascript:;" class="com-btna com-btna-sty novice_d fl_l"><label>新手指导</label></a>
		</p>
		<p class="fl_l">
			<i class="fl_l"></i>
			<label class="lab fl_l">系统字段设置</label>
			<a href="javascript:;" class="com-btna com-btna-sty novice_e fl_l"><label>新手指导</label></a>
		</p>
		<p class="fl_l">
			<i class="fl_l"></i>
			<label class="lab fl_l">消息设置</label>
			<a href="javascript:;" class="com-btna com-btna-sty novice_f fl_l"><label>新手指导</label></a>
		</p>
	</div>
	<div class="tit_box fl_r">
		<img src="${ctx}/static/images/hyx_inform_02.png" width="317px" height="234px">
	</div>
	<a href="javascript:;" class="com-btna sure-btn com-btna-sty bomp-nha-btn fl_l"><label>退出设置引导</label></a>
</div>
</body>
</html>