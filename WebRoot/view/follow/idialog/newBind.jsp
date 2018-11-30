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
	var isSubmited = false;
    $(".sure-btn").click(function(){
    	var isPass = checkIsNull();
		if(!isSubmited && isPass){
			isSubmited = true;
				$("#myForm").ajaxSubmit({
					url : '${ctx}/email/config/saveEmailConfig.do',
					type : 'post',
					error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
					success : function(data) {							
						if(data == 0){
							// 默认刷新主页面
							window.location.href='${ctx}/view/follow/idialog/bindmail_ok.jsp?email='+$("#email").val();
							//window.parent.location.reload();
						}else if(data == -1){
							window.top.iDialogMsg("提示","邮箱帐号密码校验不通过！");
							isSubmited = false;
						}else{
							// 提示失败
							window.location.href='${ctx}/view/follow/idialog/bindmail_error.jsp?email='+$("#email").val();
						}
					}
				});			
		}
	});
	$(".cancel-btn").click(function(){
		closeParentPubDivDialogIframe('email_bind');
	});
});

// 验证不能为空
function checkIsNull(){
	var isTrue = true;
	var email = $("#email").val();
	var password = $("#password").val();
	if(email == null || email ==""){
		window.top.iDialogMsg("提示","邮箱不可以为空！");
		return false;
	}
	
	var pattern = /\w@\w*\.\w/;
	if(!pattern.test(email)){
		window.top.iDialogMsg("提示","邮箱格式不正确！");
		return false;
	}
	
	if(password == null || password == ""){
		window.top.iDialogMsg("提示","密码不能为空！");
		return false;
	}
	return isTrue;
}
</script>
</head>
<body> 
<div class='bomp-cen'>
	<form id="myForm">
		<div class="bomp-tip-a" style="margin-top:20px;"><label class="tip-b fl_l" style="margin-left:45px;margin-right:10px;"></label><span class="sp-a fl_l" style="font-size:12px;">绑定新的邮箱</span></div>
		<div class='bomp-p w-a' style="margin-top:0;padding-left:5px;">
			<label class='lab_a fl_l'>帐号：</label><input type='text' name="email" id="email" value='' class='ipt_a fl_l' />
		</div>
		<div class='bomp-p w-a' style="margin-top:12px;padding-left:5px;">
			<label class='lab_a fl_l'>密码：</label><input type="password" name="password" id="password" value='' class='ipt_a fl_l' />
		</div>
		<!-- <div class='bomp-p' style="padding-left:105px;"><input type='text' name="email" id="email" value='' class='ipt_a fl_l' /></div> -->
		<div class='bomb-btn' style="margin-top:25px;">
			<label class='bomb-btn-cen'>
				<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>确定</label></a>
				<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
			</label>
		</div>
	</form>
</div>
</body>
</html>