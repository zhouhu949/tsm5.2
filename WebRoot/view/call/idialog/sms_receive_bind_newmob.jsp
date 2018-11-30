<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<html>
<head>
<title>绑定短信接收号码</title>
<script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery.form.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js${_v}" dialog-theme="default"></script><!--可移动弹框插件-->
<script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js${_v}"></script><!--可移动弹框插件公共js-->
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css"><!--弹框样式-->
<script type="text/javascript" src="${ctx}/static/js/view/call/sms_receive.js${_v}"></script><!-- 计时器函数 -->
<script type="text/javascript">
$(function(){

	//获取验证码
	$(".get_yzmbtn").on("click",function(e){
		e.stopPropagation();
		var mobile=$("#mobile").val();
		if(!verification_mobile(mobile)){
			return false;
		}
		settime(this)
		$.ajax({
			url:ctx+"/SendSms/getSmsCode",
			data:{
				"mobile":mobile
			},
			success:function(data){
				if(data.status == true ){
				}
			}
		})
	})
	//提交按钮
	var isSubmited = false;
    $(".sure-btn").click(function(){
    	var isPass = checkIsNull();
		if(!isSubmited && isPass){
			isSubmited = true;
					
		}
	});
	//取消
	$(".cancel-btn").click(function(){
		closeParentPubDivDialogIframe('smsReceive_bind');
	});
});

function closeDiv() {
	closeParentPubDivDialogIframe('smsReceive_bind');
}
// 验证不能为空
function checkIsNull(){
	var isTrue = true;
	var mobile = $("#mobile").val();
	var yzm=$("#yzm").val();
	
	if(!verification_mobile(mobile)){//手机号码验证
		return false;
	}
	if(!verification_code(yzm,mobile)){//验证码验证
		return false;
	}
	return isTrue;
}

function verification_code(yzm,mobile){
	if(yzm == null || yzm ==""){
		window.top.iDialogMsg("提示","验证码不可以为空！");
		return false;
	}
	$.ajax({
		url:ctx+"/SendSms/checkSmsCode",
		data:{
			"mobile":mobile,
			"code":yzm
		},
		success:function(data){
			if(data.status == false){
				window.top.iDialogMsg("提示",data.errorMsg);
			}else{//如果验证成功
				$("#myForm").ajaxSubmit({
					url : '${ctx}/message/smsReceiveBind',
					type : 'post',
					error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
					complete:function(XMLHttpRequest){
						isSubmited=false;
					}, 
					success : function(data) {							
						if(data == 0){
							// 默认刷新主页面
							window.top.iDialogMsg("提示","更换号码成功！");
							setTimeout("closeDiv()",1000);						
						}else{
							// 提示失败
							window.top.iDialogMsg("提示","绑定失败！");
						}
					}
				});		
			}
		}
	})
	
	return true;
}

function verification_mobile(mobile){
	if(mobile == null || mobile ==""){
		window.top.iDialogMsg("提示","号码不可以为空！");
		return false;
	}
	var pattern = /^(0?1[123456789]\d{9})$/;
	if(!pattern.test(mobile)){
		window.top.iDialogMsg("提示","号码格式不正确！");
		return false;
	}
	return true;
}
</script>
</head>
<body> 
<div class='bomp-cen' style="margin-top:30px;">
	<form id="myForm">
		<div class='bomp-p w-a bomp-linewidth'>
			<label class='lab_a fl_l'>新手机号码：</label><input type='text' name="mobile" id="mobile" value="${mobile}" class='ipt_a fl_l ipt_mobile'  placeholder="请输入手机号"/>
		</div>
		<div class='bomp-p w-a bomp-linewidth'  style="margin-top:20px;">
			<label class='lab_a fl_l'>验证码：</label><input type='text' name="code" id="yzm"  class='ipt_a fl_l ipt_yzm'  placeholder="请输入验证码"/><input  type="button" class="get_yzmbtn" value="获取验证码" />
		</div>
		<div class="fl_l receive_remainder" >
			温馨提示：设置的手机号码用于接收来电未接的提醒短信。
		</div>	
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