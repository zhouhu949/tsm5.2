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
var match = {
		"@163.com":"http://mail.163.com/",
		"@126.com":"http://mail.126.com/",
		"@qq.com":"http://mail.qq.com/",
		"@vip.qq.com":"http://mail.qq.com/",
		"@foxmail.com":"http://mail.foxmail.com/",
		"@sina.com":"http://mail.sina.com.cn/",
		"@sina.cn":"http://mail.sina.com.cn/",
		"@2008.sina.com":"http://mail.sina.com.cn/",
		"@51uc.com":"http://mail.sina.com.cn/",
		"@sohu.com":"http://mail.sohu.com/"
};

$(function(){
   $(".sure-btn").click(function(){
    	closeParentPubDivDialogIframe('email_bind');
   });
    
   var email = $(".mail_span").text();
   var start = email.indexOf("@");
   var mail_server = email.substring(start,email.length);
   if(match[mail_server] != null){
	//   alert(match[mail_server]);
	   $(".mail_span").html("<a href=\""+match[mail_server]+"\" target=\"_blank\">"+email+"</a>")
   }
});

</script>
</head>
<body> 
<div class='bomp-cen'>
	<div class="bomp-tip-a"><label class="tip-b fl_l" style="margin-left:45px;margin-right:10px;"></label><span class="sp-a fl_l" style="font-weight:bold;">激活邮件已发送至您</span></div>
	<p class='bomp_tit_c fl_l' style="margin:-8px 0 0 103px;"><span class="mail_span"><%=request.getParameter("email")%></span>的邮箱。</p>
	<div class='bomp-p' style="padding-left:105px;margin-top:5px;">请在24小时内登录邮箱，按照邮箱中的提示激活帐号。</div>
	<div class='bomb-btn' style="margin-top:25px;">
		<label class='bomb-btn-cen'>
			<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>完成</label></a>
		</label>
	</div>
</div>
</body>
</html>