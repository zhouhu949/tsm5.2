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
	getAjaxData();
	
	$('#quickMenuSetBtn').click(function(){
		pubDivShowIframe('quick_menu', ctx+'/main/sale/quickMenu?t='+Date.parse(new Date()), '编辑快捷功能',610,450);
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
	 
	 $(".message-btn").click(function(){
 		var rel = $(this).attr("rel");
// 		var title = $(this).find("p:last").find("span:first").text();
 		window.top.addTab(rel,"消息中心");
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
	 
	 $(".divis-hover").hover(function(){
		 	$("#iframepage").attr("src",ctx+"/main/sale/levelGrowth");
	    	$(".index-growth-level").show();
	    },function(){
	    	$(".index-growth-level").hover(function(){
	    		$(this).show();
	    	},function(){
	    		$(this).hide();
	    	});
	    	$(".index-growth-level").hide();
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
	 
	 $(".resour-will-sign ul").find("li").each(function(){
	    $(this).click(function(){
	    	$(this).removeClass('resour-second-li').addClass("resour-first-li");
	    	$(this).siblings().removeClass('resour-first-li').addClass("resour-second-li");
	    	var li_id = $(this).attr("id");
	    	if(li_id == 'res_li'){
	    		getAjaxPlanRes();
	    	}else if(li_id == 'int_li'){
	    		getAjaxPlanInt();
	    	}else{
	    		getAjaxPlanSign();
	    	}
	    })
	 });
	 var f;
	 var y = $(".top-tit").find(".charts").width();
	 $(".votebox").find(".reward-perc-numb").each(function(i,item){
	    	var a=parseInt($(item).text());
	    	var p=parseInt($(item).parent().siblings('.p').text());
	    	f= parseInt($(item).find(".reward-perc-numb").text());
	    	$(item).parent().siblings(".votebox-sign-cust").find(".vote-btn").hide();
	    		$(item).parent().siblings(".barbox").find(".charts").css({"background":"#7ea9dc"});
	    		$(item).parent().siblings(".sp").hide();
	    		var inter02 = setInterval(function(){
		    		if(a<=p){
		    			$(item).parent().siblings(".barbox").find(".charts").css({"width":a+"%"});
		    			$(item).text(a);
		    			a=a+1;
			    		if(a>=100){
				    		$(item).parent().siblings(".votebox-sign-cust").find(".vote-btn").show();
				    		$(item).parent().siblings(".barbox").find(".charts").css({"background":"#ff0000"});
				    		$(item).parent().siblings(".sp").show();
			    		}
		    		}else{
		    			clearInterval(inter02);
		    		}
		    	},20);
	    });
	    $('.vote-btn').click(function(){
	    	$(this).parent().parent().find('.charts').each(function(i,item){
				$(item).parent().parent().parent().find('.vote-btn').hide();
				var a = parseInt($(item).parent().parent().next().find('.a').text());
				var b = parseInt($(".top-tit").find(".sale-hand").text());
				var c = f;
				var d = 1;
				var e = a*(d/100);
				var g =a/b;
				var inter = setInterval(function(){
					$(item).parent().parent().next().find('.a').text(a);
					$(item).parent().parent().parent().find('.p').text(b);
					if(a > 0){
						a=a-e;
						$(".top-tit").find(".charts").css({"width":y+"%"});
						y=y+g;
					}else{
						clearInterval(inter);
					}
				},20);
			});
			$(this).parent().siblings('.sp').css({'color':'#000000'}).animate({'top':'-50px','opacity':'0'},1000);
			$(this).parent().siblings('.read-lq').show(1000);
			//领取积分
			var pointType = $(this).attr("pointType");
			var rt="";
			if(pointType == "signMoney"){rt = "1";}else if(pointType == "signNum"){rt = "2";}else{rt = "3";}
			$.ajax({
				url:ctx+"/main/sale/receivePoints",
				type:"post",
				data:{rt:rt},
				dataType:"json",
				error:function(){},
				success:function(data){
					if(data == "-1"){
						window.top.iDialogMsg("提示","积分领取失败!");
					}else if(data == "2"){
						window.top.iDialogMsg("提示","未完成不可领取!");
					}else if(data == "3"){
						window.top.iDialogMsg("提示","已经领取过积分!");
					}else{
						var beforeLevel = parseInt($("#levelIcon").attr("level"));
						if(data.level > beforeLevel){
							$("#levelIcon").attr("class","divis-hover divis-icon0"+data.level);
							$("#levelIcon").attr("level",data.level);
							$("#levelName").text(data.levelName);
							if(data.level < 5){
								var pl = data.endNumber - data.startNumber;
								$(".sale-hand").text(pl);
								$("#levelProc").attr("style","width:"+(data.beyondNumber/pl*100).toFixed(2)+"%;");
							}else{
								$("#levelProc").attr("style","width:100%;");
							}
						}
						
					}
				}
			});
		});	
	    
	   //处理
	   $("li[class*=hero-list-me]").each(function(idx,obj){
		   $(obj).find("span").eq(1).text("我");
	   });
	   
	   //客户卡片
	   $("a[id^=cardInfo_]").live("click",function(){
		  var custId = $(this).attr("id").split("_")[1];
		  var custName = $(this).attr("custName")||"客户卡片";
		  window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName);
	   });
	   //签约 新增合同
	   $("a[id^=sign_]").live("click",function(){
		   var custId = $(this).attr("id").split("_")[1];
			var signSetting = $(this).attr("signSetting");
			if(signSetting == '1'){
				pubDivDialog("confirm_sign","是否确认签约？",function(){
					$.ajax({
						url:ctx+"/contract/sign",
						type:"post",
						data:{custId:custId},
						dataType:"json",
						error:function(){},
						success:function(data){
							if(data == "1"){
								window.top.iDialogMsg("提示","签约成功!");
								var pid = $(".resour-first-li").attr("id");
								if(pid == 'res_li'){
									getAjaxPlanRes();
								}else{
									getAjaxPlanInt();
								}
							}else{
								window.top.iDialogMsg("提示","签约失败!");
							}
						}
					});
				});
			}else{
				window.top.addTab(ctx+"/contract/toAdd?custId="+custId+"&t="+Date.parse(new Date()),"新增合同");
			}
	   });
	   
	   	//新增合同
		$("a[id^=addContract_]").live("click",function(){
			var custId = $(this).attr("id").split("_")[1];
			window.top.addTab(ctx+"/contract/toAdd?custId="+custId+"&t="+Date.parse(new Date()),"新增合同");
		});
	   
	    //客户跟进
		$("a[id^=follow_]").live('click',function(){
			var custId = $(this).attr("id").split("_")[1];
			var custType = $(this).attr("custType");
			window.top.addTab(ctx+"/cust/custFollow/custFollowPage.do?custId="+custId+"&custListType="+custType+"&v="+new Date().getTime(),"跟进");
		});
		//淘客户
		$("a[id^=tao_]").live('click',function(){
			var resId = $(this).attr("id").split("_")[1];
			window.top.addTab(ctx+"/res/tao/taoMyRes?resId="+resId+"&isFromOtherPage=0","淘客户"); 
		});
		//今日关注  更多
		$("#moreInfo").click(function(){
			window.top.addTab(ctx+"/plan/day/view","我的计划");
		});
});

function getAjaxData(){
	/*剩余通话时长*/
	$.ajax({
		url:ctx+'/main/sale/getTimeLen',
		type:'post',
		cache:false,
		data:{},
		dataType:'json',
		error:function(){},
		success:function(data){
			$('#home-minute').text(data.totalLen+localStorage.getItem("product_currencyUnit"));
			$('#home-phone').attr('title','兑换规则：1元兑换100'+localStorage.getItem("product_currencyUnit"));
		}
	});
	
	$.ajax({
		url:ctx+'/main/sale/planNums',
		type:'post',
		dataType:'json',
		error:function(){},
		success:function(data){
			if(data.resNum != null){
				$('#resNum').text(data.resNum);
			}
			if(data.intNum != null){
				$('#intNum').text(data.intNum);
			}
			if(data.signNum != null){
				$('#signNum').text(data.signNum);
			}
			if(data.planId != null){
				$('#plan_id').val(data.planId);
			}
			getAjaxPlanInt();
		}
	});
	
	$.ajax({
		url:ctx+'/main/sale/ranking',
		type:'post',
		dataType:'json',
		error:function(){},
		success:function(data){
			createSaleMoneyRank(data.saleDtos);
			createSignCustsRank(data.signDtos);
		}
	});
}

function createSaleMoneyRank(list){
	var html = "";
	for(var i in list){
		var saleDto = list[i];
		if(saleDto.rank == 1){
			html+=
				"<li class='sale-perfor-td "+((i%2 == 0 || saleDto.userAccount == "curr_user_account") ? "" : "sale-perth-bg")+(saleDto.userAccount == "curr_user_account" ? " hero-list-me":"")+"'>" +
						"<span><label class='gold-medal'></label></span>" +
						"<span class='overflow_hidden' title='"+saleDto.userName+"'>"+saleDto.userName+"</span>" +
						"<span>"+saleDto.saleNum+"</span>" +
				"</li>";
		}else if(saleDto.rank == 2){
			html+=
				"<li class='sale-perfor-td "+((i%2 == 0 || saleDto.userAccount == "curr_user_account") ? "" : "sale-perth-bg")+(saleDto.userAccount == "curr_user_account" ? " hero-list-me":"")+"'>" +
						"<span><label class='silver-medal'></label></span>" +
						"<span class='overflow_hidden' title='"+saleDto.userName+"'>"+saleDto.userName+"</span>" +
						"<span>"+saleDto.saleNum+"</span>" +
				"</li>";
		}else if(saleDto.rank == 3){
			html+=
				"<li class='sale-perfor-td "+((i%2 == 0 || saleDto.userAccount == "curr_user_account") ? "" : "sale-perth-bg")+(saleDto.userAccount == "curr_user_account" ? " hero-list-me":"")+"'>" +
						"<span><label class='bronze-medal'></label></span>" +
						"<span class='overflow_hidden' title='"+saleDto.userName+"'>"+saleDto.userName+"</span>" +
						"<span>"+saleDto.saleNum+"</span>" +
				"</li>";
		}else if(saleDto.userAccount == 'sale_num_avg'){
			html+=
				"<li class='sale-perfor-td sale-perfor-average'>" +
				  		"<span>平均值</span>" +
				  		"<span class='overflow_hidden' title='平均哥'>平均哥</span>" +
				  		"<span>"+saleDto.saleNum+"</span>" +
			    "</li>";
		}else{
			html+=
				"<li class='sale-perfor-td "+((i%2 == 0 || saleDto.userAccount == "curr_user_account") ? "" : "sale-perth-bg")+(saleDto.userAccount == "curr_user_account" ? " hero-list-me":"")+"'>" +
						"<span>"+saleDto.rank+"</span>" +
						"<span class='overflow_hidden' title='"+saleDto.userName+"'>"+saleDto.userName+"</span>" +
						"<span>"+saleDto.saleNum+"</span>" +
				"</li>";
		}
	}
	
	for(var t=1;t <=(6-list.length);t++){
		html+="<li class='sale-perfor-td "+((t+list.length)%2  == 1 ? "" : "sale-perth-bg")+"'>" +
				"<span>&nbsp;</span>" +
				"<span>&nbsp;</span>" +
				"<span>&nbsp;</span>" +
			  "</li>";
	}
	
	$("#saleMoneyRank .sale-perfor-td").remove();
	$("#saleMoneyRank").append(html);
}

function createSignCustsRank(list){
	var html = "";
	for(var i in list){
		var signDto = list[i];
		if(signDto.rank == 1){
			html+=
				"<li class='sale-perfor-td "+((i%2 == 0 || signDto.userAccount == "curr_user_account") ? "" : "sale-perth-bg")+(signDto.userAccount == "curr_user_account" ? " hero-list-me":"")+"'>" +
						"<span><label class='gold-medal'></label></span>" +
						"<span class='overflow_hidden' title='"+signDto.userName+"'>"+signDto.userName+"</span>" +
						"<span>"+signDto.signNum+"</span>" +
				"</li>";
		}else if(signDto.rank == 2){
			html+=
				"<li class='sale-perfor-td "+((i%2 == 0 || signDto.userAccount == "curr_user_account") ? "" : "sale-perth-bg")+(signDto.userAccount == "curr_user_account" ? " hero-list-me":"")+"'>" +
						"<span><label class='silver-medal'></label></span>" +
						"<span class='overflow_hidden' title='"+signDto.userName+"'>"+signDto.userName+"</span>" +
						"<span>"+signDto.signNum+"</span>" +
				"</li>";
		}else if(signDto.rank == 3){
			html+=
				"<li class='sale-perfor-td "+((i%2 == 0 || signDto.userAccount == "curr_user_account") ? "" : "sale-perth-bg")+(signDto.userAccount == "curr_user_account" ? " hero-list-me":"")+"'>" +
						"<span><label class='bronze-medal'></label></span>" +
						"<span class='overflow_hidden' title='"+signDto.userName+"'>"+signDto.userName+"</span>" +
						"<span>"+signDto.signNum+"</span>" +
				"</li>";
		}else if(signDto.userAccount == 'sign_num_avg'){
			html+=
				"<li class='sale-perfor-td sale-perfor-average'>" +
				  		"<span>平均值</span>" +
				  		"<span class='overflow_hidden' title='平均哥'>平均哥</span>" +
				  		"<span>"+signDto.signNum+"</span>" +
			    "</li>";
		}else{
			html+=
				"<li class='sale-perfor-td "+((i%2 == 0 || signDto.userAccount == "curr_user_account") ? "" : "sale-perth-bg")+(signDto.userAccount == "curr_user_account" ? " hero-list-me":"")+"'>" +
						"<span>"+signDto.rank+"</span>" +
						"<span class='overflow_hidden' title='"+signDto.userName+"'>"+signDto.userName+"</span>" +
						"<span>"+signDto.signNum+"</span>" +
				"</li>";
		}
	}
	
	for(var t=1;t <=(6-list.length);t++){
		html+="<li class='sale-perfor-td "+((t+list.length)%2  == 1 ? "" : "sale-perth-bg")+"'>" +
				"<span>&nbsp;</span>" +
				"<span>&nbsp;</span>" +
				"<span>&nbsp;</span>" +
			  "</li>";
	}
	
	$("#signCustsRank .sale-perfor-td").remove();
	$("#signCustsRank").append(html);
}

function call(phone){
	var param = "<xml><Oparation Phone=\""+phone+"\" /></xml>";
	try{
		external.OnCallXml(param);			
	}catch(e){
		external.OnCall(phone+"_");
	}
}

function getAjaxPlanRes(){
	var planId = $("#plan_id").val();
//	var num = parseInt($("#res_li").find("span").text());
//	if(num > 10){
//		$("#moreInfo").show();
//		$("#moreInfo").attr("custType","1");
//	}else{
//		$("#moreInfo").hide();
//	}
	$.post(ctx+'/main/sale/resPlanList',{sudId:planId},function(data){
		$("#ajaxTable").html(data);
		if($("#idReplaceWord").length > 0){
			var idReplaceWord = $("#idReplaceWord").val();
			if(idReplaceWord==1){
				$("[phone=tel]").each(function(idx,obj){
					var phone = $(obj).text();
					if(phone != null && phone != ''){
						replaceWord(phone,$(obj));
						replaceTitleWord(phone,$(obj));
					}
				});
			}
		}
	});
}

function getAjaxPlanInt(){
	var planId = $("#plan_id").val();
//	var num = parseInt($("#int_li").find("span").text());
//	if(num > 10){
//		$("#moreInfo").show();
//		$("#moreInfo").attr("custType","2");
//	}else{
//		$("#moreInfo").hide();
//	}
	$.post(ctx+'/main/sale/intPlanList',{sudId:planId},function(data){
		$("#ajaxTable").html(data);
		if($("#idReplaceWord").length > 0){
			var idReplaceWord = $("#idReplaceWord").val();
			if(idReplaceWord==1){
				$("[phone=tel]").each(function(idx,obj){
					var phone = $(obj).text();
					if(phone != null && phone != ''){
						replaceWord(phone,$(obj));
						replaceTitleWord(phone,$(obj));
					}
				});
			}
		}
	});
}

function getAjaxPlanSign(){
	var planId = $("#plan_id").val();
//	var num = parseInt($("#sign_li").find("span").text());
//	if(num > 10){
//		$("#moreInfo").show();
//		$("#moreInfo").attr("custType","3");
//	}else{
//		$("#moreInfo").hide();
//	}
	$.post(ctx+'/main/sale/signPlanList',{sudId:planId},function(data){
		$("#ajaxTable").html(data);
		if($("#idReplaceWord").length > 0){
			var idReplaceWord = $("#idReplaceWord").val();
			if(idReplaceWord==1){
				$("[phone=tel]").each(function(idx,obj){
					var phone = $(obj).text();
					if(phone != null && phone != ''){
						replaceWord(phone,$(obj));
						replaceTitleWord(phone,$(obj));
					}
				});
			}
		}
	});
}