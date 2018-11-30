<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<html>
<head>
<title>绑定邮箱</title>
<script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery.form.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js${_v}" dialog-theme="default"></script><!--可移动弹框插件-->
<script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js${_v}"></script><!--可移动弹框插件公共js-->
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css"><!--弹框样式-->
<script type="text/javascript">
$(function(){
    $(".sure-btn").click(function(){
    	window.location.href='${ctx}/view/follow/idialog/emailBind.jsp';
    });
});

</script>
</head>
<body> 
<div class='bomp-cen'>
	<div class="bomp-tip-a"><label class="tip-b fl_l" style="margin-left:45px;margin-right:10px;"></label><span class="sp-a fl_l" style="font-weight:bold;">激活邮件已发失败</span></div>
	<p class='bomp_tit_c fl_l' style="margin:-8px 0 0 103px;"><%=request.getParameter("email")%>的邮箱地址错误。</p>
	<div class='bomp-p' style="padding-left:105px;margin-top:5px;">请重新输入您要绑定的邮箱地址。</div>
	<div class='bomb-btn' style="margin-top:25px;">
		<label class='bomb-btn-cen'>
			<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>返回</label></a>
		</label>
	</div>
</div>
</body>
</html>