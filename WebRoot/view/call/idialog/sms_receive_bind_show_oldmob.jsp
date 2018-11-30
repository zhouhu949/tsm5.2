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

	//取消
    $(".cancel-btn").click(function(){
	  closeDiv();
	});
    //下一步
	$(".sure-btn").click(function(){		
		  var yzm=$("#yzm").val();
	    	var mobile=$("#mobile").val();
    		if(!verification_code(yzm,mobile)){
	    		return false;
	    	}
	});
});

function closeDiv() {
	closeParentPubDivDialogIframe('smsReceive_bind');
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
		error:function(data){
			
		},
		success:function(data){
			if(data.status == false){
				window.top.iDialogMsg("提示",data.errorMsg);
			}else{//如果状态为true 则调用成功的弹窗和提示
				window.location.href=ctx+"/SendSms/SmsReceiveNew";
			}
		}
	})
	
	return true;
}

</script>
</head>
<body> 
<div class='bomp-cen' style="margin-top:50px;">
	<form id="myForm">
		<input type="hidden" id="mobile"  value="${mobile}"/>
		<div class='bomp-p w-a bomp-linewidth' style="margin-top:25px;padding-left:5px;line-height:27px;">
			<label class='lab_a fl_l'>原手机号码：</label>${mobile}
		</div>
		<div class='bomp-p w-a bomp-linewidth'  style="margin-top:20px;">
			<label class='lab_a fl_l'>验证码：</label><input type='text' name="code" id="yzm"  class='ipt_a fl_l ipt_yzm'  placeholder="请输入验证码"/><input  type="button" class="get_yzmbtn" value="获取验证码" />
		</div>
		<div class="fl_l receive_remainder" >
			温馨提示：设置的手机号码用于接收来电未接的提醒短信。
		</div>		
		<div class='bomb-btn' style="margin-top:25px;">
			<label class='bomb-btn-cen'>
				<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>下一步</label></a>
				<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
			</label>
		</div>
	</form>
</div>
</body>
</html>