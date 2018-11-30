<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<html>
<head>
<title>新手帮助（系统管理员）-客户群体设置</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->

<script type="text/javascript">
$(function(){
	var opera1 = true;
    $(".bomp-nha-btn").click(function(){
    	if(opera1){
    		opera1 = false;
    		window.parent.$('.i-title').text('设置引导首页');
        	window.parent.document.forms[0].submit();
    	}
	});

	window.parent.$('.i-close').show(); 
});

function radio_(obj){
	var initVal = $(obj).attr("value");
	$("#initParam", window.parent.document).val(initVal);
}
</script>
</head>
<body> 
<div class='bomp-nha bomp-nhb bomp-nhc'>
	<p class="qut" style="padding:120px 0px 20px 130px;">请您选择贵公司服务的客户群体：</p>
	<div class="qut_sel" style="padding-left:112px;">
		<label class="fl_l"><input type="radio" checked="checked" name="a" value="0" onclick="radio_(this)"/><span>个人客户</span></label>
		<label class="fl_l"><input type="radio"  name="a" value="1" onclick="radio_(this)"/><span>企业客户</span></label>

	</div>
	<div class="tit_box fl_r">
		<img src="${ctx}/static/images/hyx_inform.png" width="100px" height="114px">
	</div>
	<a href="javascript:;" class="com-btna sure-btn com-btna-sty bomp-nha-btn fl_l"><label>下一步</label></a>
</div>
</body>
</html>