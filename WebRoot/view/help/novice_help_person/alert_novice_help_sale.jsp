<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<html>
<head>
<title>新手帮助（普通销售）-系统设置引导说明</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css"><!--弹框样式-->
<script type="text/javascript">
$(function(){
    $(".sure-btn").click(function(){
    	window.parent.window.location.href = '${ctx}/view/help/novice_help_person/cust_my_ifram_a.jsp';
	});    
});

</script>
</head>
<body> 
<div class='bomp-nha'>
	<div class="tit_box">
		<img src="${ctx}/static/images/hyx_inform_04.png" width="401px" height="311px" />
	</div>
	<a href="javascript:;" class="com-btna sure-btn com-btna-sty bomp-nha-btn fl_l"><label>新手引导>></label></a>
</div>
</body>
</html>