$(document).ready(function() {
	init_leftTop();
	
	img_hover();
	
	init_leftBottom();
	
	init_rightTop();
	
	init_rightBottom();
	
	$("tbody tr").each(function() {
		var $this = $(this);
		$this.click(function(){
			if(!$this.attr("disabled")){
				$("tbody tr").removeClass("active");
				$this.addClass("icon_blue_full");
			}
		});
		$this.hover(
			function(){
				if($this.attr("disabled")){
					$this.find(".tr_note").siblings().hide();
					$this.find(".tr_note").show();
				}
			},
			function(){
				if($this.attr("disabled")){
					$this.find(".tr_note").hide();
					$this.find(".tr_note").siblings().show();
				}
			}
		);
	});

	$(".taoCust_pools li").click(function() {
		$(".taoCust_pools li.active").removeClass("active");
		$(this).addClass("active");
	});
	
	//适用产品选择框
	$(".sub-dep-inpu").click(function(){
		$(".manage-drop").show();
		$(".manage-drop").parent().siblings().find("dd").hide();
		$(".manage-owner-sour").hide();
		return false;
	});
	$(".reso-alloc-sure").click(function() {
		$(this).parent().parent().hide();
		return false;
	});
	$(".reso-sub-clear").click(function() {
		$(this).parent().parent().show();
		return false;
	});
	$(document).click(function(){
		$(".manage-drop").hide();
		$("#next_contact_select").hide();
	});
});

$(window).resize(function() {
	init_leftTop();
	
	init_leftBottom();
	
	init_rightTop();
	
	init_rightBottom();
});

function init_leftTop(){
	//左上角				
	var clientHeight = $(window).height();
	var card = $(".card");
	var card_operate = $(".card_operate");
	
	var isState = $("#isState").val();
	
	if(isState == "1"){
		// 页签实现
		var card_operate_apart=$(".card_operate_apart");
		card_operate_apart.click(function(){
			card_operate_apart.removeClass("choosed");
			$(this).addClass("choosed");
		});
	}
	
	$(".p_b_div i").live("click",function(){
		$(this).toggle(
			function(){
				$(".p_b_div .sty-hid").show();
				$(this).removeClass("icon_down");
				$(this).addClass("icon_up");
			},
			function(){
				$(".p_b_div .sty-hid").hide();
				$(this).addClass("icon_down");
				$(this).removeClass("icon_up");
			}
		);
		$(this).trigger('click');
	});
	
	
	$('.icon-edit').live('click',function(){
	    var custId = $('#custId').val();
		if(custId=='' || custId==null){return false;}
		var getTimestamp=new Date().getTime();
		pubDivShowIframe('edit_'+custId,ctx+'/res/cust/toEditRes?custId='+custId+'&v='+getTimestamp,'信息编辑',500,570);
	});
	
}

function init_leftBottom(){
	//左下角
	var clientHeight = $(window).height();
	var hyx_sce_left_form = $(".hyx-sce-left-form");
	
	$(".actionTag i").toggle(
		function(){
			$(".tip-box").css("height","auto");
			$(this).removeClass("icon_down");
			$(this).addClass("icon_up");
		},
		function(){
            $(".tip-box").css("height","30px");
			$(this).addClass("icon_down");
			$(this).removeClass("icon_up");
		}
	);
	
	var leftBottom_info_height = hyx_sce_left_form.height();
	
	var tip_box_a_last = null;
	$(".icon_more_info").toggle(
		function(){
			$(".sales_info_detail_input>.sty-hid").show();
			$(this).removeClass("icon_down");
			$(this).addClass("icon_up");
			hyx_sce_left_form.css("height","auto");
			if($(".tip-box").height() > 25){
				tip_box_a_last = $(".tip-box>a:last");
				tip_box_a_last.insertBefore($(".tip-box>.sty-hid>a:first"));
			}
		},
		function(){
			$(".sales_info_detail_input>.sty-hid").hide();
			$(this).addClass("icon_down");
			$(this).removeClass("icon_up");
			hyx_sce_left_form.height(leftBottom_info_height);
			if(tip_box_a_last != null){
				tip_box_a_last.insertBefore($(".tip-box>.sty-hid"));
			}
		}
	);
	
	 $('.hyx-sce-area').find('textarea,span').click(function(){
			var ipt_a = $(this).parent().find('textarea');
			ipt_a.focus();
			$(this).parent().find('.textarea_tip').hide();
			ipt_a.blur(function(){
			if($(this).val() == ''){
				$(this).parent().find('span').show();
			}
		}); 
	 });
	 
 	$("#followDate").unbind('click').click(function(){
		WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d',startDate:'%y-%M-{%d} %H:%m:%s',readOnly:true,onpicked:timeChange,changing:timeChange,oncleared:timeChange});
		setTimeout(function(){
			var last_iframe = $("iframe").last();
			if(last_iframe.width() == 186 && last_iframe.height() == 221){
 				last_iframe.attr("id","last_iframe");
 				$("#next_contact_select").insertAfter(last_iframe.parent());
 				var height = last_iframe.height();//221px
 				var top = parseFloat(last_iframe.parent().css("top"));
 				var left = last_iframe.parent().css("left");
 				$("#next_contact_select").css("left",left);
 				$("#next_contact_select").css("top",top + height);
 				$("#next_contact_select").show();
 			}
		},200);
	});
	
	function timeChange(){
		$("#next_contact_select").hide();
	}
	
	$("#next_contact_select li").each(function(index){
		var $this = $(this);
		$this.click(function(e){
			e.stopPropagation();
			$("#followDate").val("");
			switch(index){
			case 0:
				$("#followDate").unbind('click').click(function(){
 					WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d',startDate:'%y-%M-{%d+1} %H:%m:%s'});
 					$("#next_contact_select").show();
				});
				break;
			case 1:
				$("#followDate").unbind('click').click(function(){
 					WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d',startDate:'%y-%M-{%d+3} %H:%m:%s'});
 					$("#next_contact_select").show();
				});
				break;
			case 2:
				$("#followDate").unbind('click').click(function(){
 					WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d',startDate:'%y-%M-{%d+5} %H:%m:%s'});
 					$("#next_contact_select").show();
				});
				break;
			case 3:
				$("#followDate").unbind('click').click(function(){
 					WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d',startDate:'%y-%M-{%d+7} %H:%m:%s'});
 					$("#next_contact_select").show();
				});
				break;
			default:
				$("#followDate").unbind('click').click(function(){
 					WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d',startDate:'%y-%M-{%d} %H:%m:%s'});
 					$("#next_contact_select").show();
				});
				break;
			}
			setTimeout(function(){
				$("#followDate")[0].click();
 				$("#last_iframe").contents().find("#dpOkInput").click();
 				$("#next_contact_select").hide();
 			},0);
		});
	});
	
	$('.select[data-multi="true"] dd ul li').live("click",function(e){
		e.stopPropagation();
		var _this = $(this);
		_this.find("a").click();
	});
}

function init_rightTop(){
	$(document).click(function(){
		$(".list-box .select").find("dd").hide();
	});
	
	$(".list-box .select").click(function(e){
		e.stopPropagation();
		var $this = $(this);
		$(".list-box .select").find("dd").hide();
		$this.find("dd").show();
	});
	
	var icon_grey_empty_left = parseFloat($(".icon_grey_empty").css("left"));
	var otherRes_td_div_time = $(".otherRes_td_div_time");
	var otherRes_td_div_custName = $(".otherRes_td_div_custName");
	var otherRes_td_div_auto = $(".otherRes_td_div_auto");
	if(otherRes_td_div_time){
		otherRes_td_div_time.css("margin-left",icon_grey_empty_left+15);
	}
	
	if(otherRes_td_div_custName){
		otherRes_td_div_custName.css("margin-left",icon_grey_empty_left+15);
		var div_width = otherRes_td_div_custName.width();
		var span = otherRes_td_div_custName.find("span");
		for(var i=0;i<span.length;i++){
			if(span.eq(i).height() > otherRes_td_div_custName.height()){
				span.eq(i).css("text-align","left");
				var count = div_width / 12;
				var newText = span.eq(i).text().substring(0,count-2) + "...";
				span.eq(i).text(newText);
			}
		}
	}
	
	if(otherRes_td_div_auto){
		var div_width = otherRes_td_div_auto.width();
		var span = otherRes_td_div_auto.find("span");
		for(var i=0;i<span.length;i++){
			if(span.eq(i).height() > otherRes_td_div_auto.height()){
				span.eq(i).css("text-align","left");
				var count = div_width / 12;
				var newText = span.eq(i).text().substring(0,count-2) + "...";
				span.eq(i).text(newText);
			}
		}
	}
}

function init_rightBottom(){
	//右下角  左侧总共占了82%+50px
//	var taoCust_right_top = $(".taoCust_right_top");
//	alert(taoCust_right_top.height());
//	var clientHeight = $(window).height();
//	var taoCust_right_bottom = $(".taoCust_right_bottom");
//	taoCust_right_bottom.height(clientHeight * 0.94 - taoCust_right_top.height());
	
	var cfu_list_note = $("p.cfu-list-note");
	var cfu_list_note_label = cfu_list_note.find("label");
	var cfu_list_note_span = cfu_list_note.find("span");
	cfu_list_note_span.width(cfu_list_note.width() - cfu_list_note_label.width() - 10);
	
}

function img_hover() {
	var icons_grey = [
		"icon_grey_tel.png", 
		"icon_grey_msg.png", 
		"icon_grey_qq.png", 
		"icon_grey_weChat.png", 
		"icon_grey_mail.png", 
		"icon_grey_wangwang.png"
	];
	var icons_white = [
		"icon_white_tel.png", 
		"icon_white_msg.png", 
		"icon_white_qq.png", 
		"icon_white_weChat.png", 
		"icon_white_mail.png", 
		"icon_white_wangwang.png"
	];
	var icons_blue = [
		"icon_blue_tel.png", 
		"icon_blue_msg.png", 
		"icon_blue_qq.png", 
		"icon_blue_weChat.png", 
		"icon_blue_mail.png", 
		"icon_blue_wangwang.png"
	];
	$(".content_icon_size").hover(
		function() {
			var $this = $(this);
			var img = $this.find("img");
			var src = img.attr("src");
			var arr = src.split("/");
			var newSrc = "";
			for (var i = 0; i < arr.length - 1; i++) {
				newSrc = newSrc + arr[i] + "/";
			}
			var last = arr[arr.length - 1];
			var index = -1;
			if (last.indexOf("blue") != -1) {
				for (var j = 0; j < icons_blue.length; j++) {
					if (last == icons_blue[j]) {
						index = j;
						break;
					}
				}
				newSrc += icons_white[index];
				img.attr("src", newSrc);
				$this.css("background-color", "#197acb");
				if($this.hasClass="custInfo_weChat"){
					$(".icon_reBind_weChat").hide();
					$(".icon_reBind_weChat_white").show();
				}
			} 
		},
		function() {
			var $this = $(this);
			var img = $this.find("img");
			var src = img.attr("src");
			var arr = src.split("/");
			var newSrc = "";
			for (var i = 0; i < arr.length - 1; i++) {
				newSrc = newSrc + arr[i] + "/";
			}
			var last = arr[arr.length - 1];
			var index = -1;
			if (last.indexOf("white") != -1) {
				for (var j = 0; j < icons_white.length; j++) {
					if (last == icons_white[j]) {
						index = j;
						break;
					}
				}
				newSrc += icons_blue[index];
				img.attr("src", newSrc);
				$this.css("background-color", "#fff");
				if($this.hasClass="custInfo_weChat"){
					$(".icon_reBind_weChat").hide();
					$(".icon_reBind_weChat_blue").show();
				}
			}
		}
	);
}