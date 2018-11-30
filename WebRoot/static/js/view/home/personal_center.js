$(function(){
	//点击左侧页签切换右侧头和iframe
	$(".personal-center-box-left-items").each(function(i,obj){
		$(obj).click(function(e){
			e.stopPropagation();
			var $this = $(this);
			var ifremaUrl = $this.data("url");
			var title = $this.text();
			var edit = $this.data("edit");
			var pay = $this.data("pay");
			$(".personal-center-box-left-items").removeClass("active");
			$this.addClass("active");
			$(".personal-center-information-head .information-title").text(title);
			console.log(edit)
			if(edit){
				$(".personal-center-information-head .personal-center-icon-edit").show();
			}else{
				$(".personal-center-information-head .personal-center-icon-edit").hide();
			}
			if(ifremaUrl){
				if(pay == "true"){
					$("#iframepage").attr("src",ifremaUrl+"?v="+new Date().getTime());
				}else{
					$("#iframepage").attr("src",ifremaUrl);
				}
			}
		})
	});
	//编辑信息按钮
	$(".personal-center-icon-edit").on("click",function(e){
		e.stopPropagation();
		pubDivShowIframe('personal_edit', ctx + '/user/toPersonal_center_edit_idialog?v='+new Date().getTime(), '编辑信息', 500, 570);
	});
	//绑定和更换号码
	$(".personal-center-change-number").on("click",function(e){
		e.stopPropagation();
		pubDivShowIframe('smsReceive_bind',ctx+'/message/smsReceiveBindPage?v='+new Date().getTime(),'帐号绑定手机号',450,350);
	});
	$(".personal-center-change-email-bind").on("click",function(e){
		e.stopPropagation();
		pubDivShowIframe('email_bind',ctx+'/view/follow/idialog/emailBind.jsp','绑定邮箱',400,250);
	});
	$(".personal-center-change-email-change").on("click",function(e){
		e.stopPropagation();
		pubDivShowIframe('email_bind',ctx+'/email/config/toUnBind?v='+new Date().getTime(),'邮箱变更',400,250);
	});
	//编辑头像弹窗
	$(".personal-center-box-left-img").on("click",function(e){
		e.stopPropagation();
		pubDivShowIframe('img_iconbind',ctx+'/user/to_personal_center_img_idialog?v='+new Date().getTime(),'编辑头像',400,470);
	})
})
