$(function(){
	//点击头像进入个人中心
	$(".click-into-personal-center").on("click",function(e){
		e.stopPropagation();
		window.top.intoPersonal();
	})
	 $(".enter-hyx-wallet").on("click",function(e){
			e.stopPropagation();
			window.top.addWallet();
		})
	/*剩余通话时长*/
	$.ajax({
		url:ctx+'/main/sale/getTimeLen',
		type:'post',
		cache:false,
		dataType:'json',
		error:function(){},
		success:function(data){
			$('#home-minute').text(data.totalLen+localStorage.getItem("product_currencyUnit"));
			$('#home-phone').attr('title','兑换规则：1元兑换100'+localStorage.getItem("product_currencyUnit"));
		}
	});
	/* 表单优化 */
    /*$('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });*/

	$(".home-manag-selec").find('.select').each(function(){
        var s=$(this);
        var z=parseInt(s.css("z-index"));
        var dt=$(this).children("dt");
        var dd=$(this).children("dd");
        var _show=function(){dd.slideDown(200);dt.addClass("cur");s.css("z-index",z+1);};   //展开效果
        var _hide=function(){dd.slideUp(200);dt.removeClass("cur");s.css("z-index",z);};    //关闭效果
        dt.click(function(){dd.is(":hidden")?_show():_hide();});
        if(dd.find('a').hasClass('diy') == true){
            dd.find(".diy").parent().siblings().click(function(){dt.html($(this).html());_hide();});     //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）

        }else{
            dd.find("a").click(function(){dt.html($(this).html());_hide();});     //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）
        }
        $(document).click(function(i){
        	!$(i.target).parents(".select").first().is(s) ? _hide():"";
        });
    });
	
	$('#quickMenuSetBtn').click(function(){
		pubDivShowIframe('quick_menu', ctx+'/main/sale/quickMenu?t='+Date.parse(new Date()), '编辑快捷功能',610,450);
	});
	
	$(".plan_radio").on("click",function(){
		var $this = $(this);
		var radio_box = $this.find("input[name=right_radio]");
		radio_box.attr("checked","checked");
		var rt = radio_box.val();
		var gid = $("#line_group_id").val();
		monthLinePlan(rt,gid);
	});
	
	 $(".message-btn").click(function(){
	 		var rel = $(this).attr("rel");
//	 		var title = $(this).find("p:last").find("span:first").text();
	 		window.top.addTab(rel,"消息中心");
	 });
	 
	 $(".mess-center-icon").find(".noti-anno-minbg").each(function(){
	    	var a = parseInt($(this).text());
	    	if(0 < a){
	    		$(this).css({"width":"13px","height":"13px","background-position-x":"-220px","background-position-y":"-8px"});
	    		if(10 <=a ){
	    			$(this).css({"width":"20px","height":"13px","background-position-x":"-262px","background-position-y":"-8px"});
	    			if(100 <=a ){
	    				$(this).css({"width":"20px","height":"13px","background-position-x":"-262px","background-position-y":"-8px","line-height":"6px"});
	    					$(this).text("...");
	    			};
	    		};
	    	}
	 });
	 
	 $(".mess-icon-first").find("div").each(function(){
	    	$(this).hover(function(){
	    		$(this).css({
	    			"background":"#fff",
	    			"border-width":"1px",
					"border-style":"solid",
					"border-color":"#469bff"
				})
	    	},function(){
	    		$(this).css({
	    			"background":"#f4f4f4",
	    			"border-width":"1px",
					"border-style":"solid",
					"border-color":"#f4f4f4"
				})
	    	});
	  });
	 
	 $(".mess-icon-second").find("div").each(function(){
	    	$(this).hover(function(){
	    		$(this).css({
	    			"background":"#fff",
	    			"border-width":"1px",
					"border-style":"solid",
					"border-color":"#469bff"
				})
	    	},function(){
	    		$(this).css({
	    			"background":"#f4f4f4",
	    			"border-width":"1px",
					"border-style":"solid",
					"border-color":"#f4f4f4"
				})
	    	});
	 });
});

function lineGroupPlan(gid){
	var rt = $("input[name=right_radio]:checked").val();
	$("#line_group_id").val(gid);
	monthLinePlan(rt,gid);
}

function call(phone){
	var param = "<xml><Oparation Phone=\""+phone+"\" /></xml>";
	try{
		external.OnCallXml(param);			
	}catch(e){
		external.OnCall(phone+"_");
	}
}