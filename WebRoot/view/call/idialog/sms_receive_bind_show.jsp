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

	//确认
    $(".cancel-btn").click(function(){
	    var yzm=$("#yzm").val();
	    var mobile=$("#mobile").val();
    	if(!verification_code(yzm,mobile)){
    		return false;
    	}
    	
	});
    // 更换号码
	$(".sure-btn").click(function(){		
		//window.location.href=ctx+'/message/smsReceiveBindPage1';
		//新的更换号码地址
		var mobile=$("#mobile").val();
		window.location.href=ctx+"/SendSms/SmsReceiveOld?mobile="+mobile;
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
			console.log(data)
		},
		success:function(data){
			if(data.status == false){
				window.top.iDialogMsg("提示",data.errorMsg);
			}else{//如果状态为true 则调用成功的弹窗和提示
				pubDivDialog1("confirm_sign","您确认要解除该手机号与帐号的关联关系吗？",function(){
	    		$("#myForm").ajaxSubmit({
					url : '${ctx}/message/smsReceiveUnBind',
					type : 'post',
					error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
					success : function(data) {							
						if(data == 0){
							// 默认刷新主页面
							window.top.iDialogMsg("提示","解绑成功！");
							setTimeout("closeDiv()",1000);						
						}else{
							// 提示失败
							window.top.iDialogMsg("提示","解绑失败！");
						}
					}
				});	
	    	},null,400,200);	
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
			<label class='lab_a fl_l'>手机号码：</label>${mobile}
		</div>
		<div class='bomp-p w-a bomp-linewidth'  style="margin-top:20px;">
			<label class='lab_a fl_l'>验证码：</label><input type='text' name="code" id="yzm"  class='ipt_a fl_l ipt_yzm'  placeholder="请输入验证码"/><input  type="button" class="get_yzmbtn" value="获取验证码" />
		</div>
		<div class="fl_l receive_remainder" >
			温馨提示：设置的手机号码用于接收来电未接的提醒短信。
		</div>		
		<div class='bomb-btn' style="margin-top:25px;">
			<label class='bomb-btn-cen'>
				<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>更换号码</label></a>
				<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>解除绑定</label></a>
			</label>
		</div>
	</form>
</div>
<!-- <script>
var countdown = 60;
		function settime(val) { 
			if (countdown == 0) { 
				val.removeAttribute("disabled");    
				val.value="免费获取验证码"; 
				countdown = 60; 
			} else { 
				val.setAttribute("disabled", true); 
				val.value="重新发送(" + countdown + ")"; 
				countdown--; 
			} 
			setTimeout(function() { 
				settime(val) 
			},1000) 
		} 

</script> -->
</body>
</html>