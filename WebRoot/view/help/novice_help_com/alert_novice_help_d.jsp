<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<html>
<head>
<title>新手帮助（系统管理员）-设置引导首页</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->

<script type="text/javascript">
$(function(){
    $(".sure-btn").click(function(){
    	document.location.href = '${ctx}/view/help/novice_help_com/alert_novice_help_b.jsp';
	});
    $(".novice_a").click(function(){
    	window.parent.window.location.href = '${ctx}/view/help/novice_help_com/sys_pro_set.jsp';
	});
	$(".novice_b").click(function(){
    	window.parent.window.location.href = '${ctx}/view/help/novice_help_com/sys_pro_set_a.jsp';
	});
	$(".novice_c").click(function(){
    	window.parent.window.location.href = '${ctx}/view/help/novice_help_com/sys_pro_set_b.jsp';
	});
	$(".novice_d").click(function(){
    	window.parent.window.location.href = '${ctx}/view/help/novice_help_com/sys_pro_set_c.jsp';
	});
	$(".novice_e").click(function(){
    	window.parent.window.location.href = '${ctx}/view/help/novice_help_com/sys_pro_set_d.jsp';
	});
	$(".novice_f").click(function(){
    	window.parent.window.location.href = '${ctx}/view/help/novice_help_com/sys_pro_set_e.jsp';
	});
	$(".novice_g").click(function(){
    	window.parent.window.location.href = '${ctx}/view/help/novice_help_com/sys_pro_set_g.jsp';
	});
});
</script>
</head>
<body> 
<div class='bomp-nha bomp-nhb'>
	<div class="list">
		<p class="txt">接下去，您需要完成以下6种系统属性的设置，分别是：</p>
		<p class="fl_l">
			<i class="i_checked fl_l"></i>
			<label class="lab fl_l">销售产品维护</label>
			<a href="javascript:;" class="com-btna com-btna-sty novice_a fl_l"><label>新手指导</label></a>
		</p>
		<p class="fl_l">
			<i class="fl_l"></i>
			<label class="lab fl_l">销售进程设计</label>
			<a href="javascript:;" class="com-btna com-btna-sty novice_b fl_l"><label>新手指导</label></a>
		</p>
		<p class="fl_l">
			<i class="fl_l"></i>
			<label class="lab fl_l">目标客户分类</label>
			<a href="javascript:;" class="com-btna com-btna-sty novice_c fl_l"><label>新手指导</label></a>
		</p>
		<p class="fl_l">
			<i class="fl_l"></i>
			<label class="lab fl_l">客户放弃原因</label>
			<a href="javascript:;" class="com-btna com-btna-sty novice_d fl_l"><label>新手指导</label></a>
		</p>
		<p class="fl_l">
			<i class="fl_l"></i>
			<label class="lab fl_l">流失客户原因</label>
			<a href="javascript:;" class="com-btna com-btna-sty novice_g fl_l"><label>新手指导</label></a>
		</p>
		<p class="fl_l">
			<i class="fl_l"></i>
			<label class="lab fl_l">录音范例分类</label>
			<a href="javascript:;" class="com-btna com-btna-sty novice_e fl_l"><label>新手指导</label></a>
		</p>
		<p class="fl_l">
			<i class="fl_l"></i>
			<label class="lab fl_l">行动标签维护</label>
			<a href="javascript:;" class="com-btna com-btna-sty novice_f fl_l"><label>新手指导</label></a>
		</p>
	</div>
	<div class="tit_box fl_r">
		<img src="${ctx}/static/images/hyx_inform_02.png" width="317px" height="234px">
	</div>
	<a href="javascript:;" class="com-btna sure-btn com-btna-sty bomp-nha-btn fl_l"><label>返回引导首页</label></a>
</div>
</body>
</html>