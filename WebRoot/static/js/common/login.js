/**
 * 登录页面_专属JS
 Written by:haoqj
 Created Date: 2013-06-13
 Copyright：chinajoin 2013 -2014
**/

$(function(){
	$("#account,#password,#code").keydown(function(e){
		if(e.which == 13)
			$("#loginForm").submit();
	});
	
	$("#login_btn").click(function(){
		$("#loginForm").submit();
	});
});

/**
 * 输入框提示功能(不影响元素内容,提示聚集与输入两种方式，可自行扩展)
 * @param tagId	要作用元素ID
 * @param tipsMsg 提示信息
 */
function helpMsgShow(tagId,tipsMsg){
	$("#"+tagId).wrap("<label style='display:block;position:relative;'></label>");
	$("#"+tagId).before("<span style='font-size:14px;position:absolute;float:left;line-height:35px;left:10px;color:#BCBCBC;cursor:text;z-index:1'>"+tipsMsg+"</span>");
	
	$("#"+tagId).each(function(){
		if($(this).val()!=""){
   			$(this).siblings().hide();
   		}else{
   			$(this).siblings().show();
   		}
		
		$(this).siblings().click(function(){
			$(this).siblings().focus();
		});
		
    	// 聚焦型输入框验证
		$(this).focus(function(){
			$(this).siblings().hide();
		}).blur(function(){
			if($(this).val()!=""){
				$(this).siblings().hide();
			}else{
				$(this).siblings().show();
			}
		});
	});
}

// 登录输入校验
function loginChk(){
	var j_username = $('#account').attr("value");
	var j_password = $('#password').attr("value");
	var img_code = $("#code1").attr("value");
	if(j_username =='' && j_password==''){
		$('#errmsg').attr("class","bc");
		$('#errmsg').text("请输入帐号和密码");
		$('#account').focus();
		return false;
	}
	
	if(j_username==''){
		$('#errmsg').attr("class","bc");
		$('#errmsg').text("请输入帐号");
		$('#account').focus();
		return false;
	}
	
	if(j_password==''){
		$('#errmsg').attr("class","bc");
		$('#errmsg').text("请输入密码");
		$('#password').focus();
		return false;
	}
	
	if(img_code==''){
		$('#errmsg').attr("class","bc");
		$('#errmsg').text("请输入验证码");
		$('#code1').focus();
		return false;
	}
	
	return true;
}