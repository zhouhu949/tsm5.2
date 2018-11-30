$(document).ready(function() {
	progress();
	init();
	var tabType = $("#tabType").val();
	if(tabType == '3'){
		getCallTimeLine();
	}else if(tabType == '5'){
		getAjaxCardContract();
	}else if(tabType == '6'){
		getAjaxCardContractOrder();
	}else if(tabType == '7' || tabType == '8'){
		getAjaxCardAction();
	}else{
		getTimeLine(tabType);
	}
	//查看订单
	$("a[id^=orderView_]").live("click",function(){
		var id = $(this).attr("id").split("_")[1];
		window.parent.pubDivShowIframe('view_order',ctx+'/contract/order/orderView?orderId='+id,'订单详情',995,610);
	});
	
	//查看合同
	$("a[id^=contractView_]").live("click",function(){
		var id = $(this).attr("id").split("_")[1];
		window.top.addTab(ctx+'/contract/toView?contractId='+id,'查看合同');
	});
	
	//打电话
//    $("a[id^=call_]").live('click',function(){
//    	var phone = $(this).attr("id").split("_")[1];
//    	var custId = $(this).attr("custId");
//    	var custName = $(this).attr("custName");
//    	var custType = $(this).attr("custType");
//    	var custState = $(this).attr("custState");
//    	var define1 = $(this).attr("define1");
//    	var arrays = new Array();
//    	arrays[0] = "\"custId\":\""+custId+"\"";
//    	arrays[1] = "\"custName\":\""+custName+"\"";
//    	arrays[2] = "\"custType\":\""+custType+"\"";
//    	arrays[3] = "\"custState\":\""+custState+"\"";
//    	arrays[4] = "\"define1\":\""+define1+"\"";
//    	window.top.custCallPhone(phone,arrays,custId);
//    });
	//模糊处理手机、电话号码
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
	
	//通话录音播放
	$('span[rc=voice]').live('click',function(){
		var httpUrl = $("#project_path").val();
		var callNum = $(this).attr('callNum');
		var url = $('#playUrl').val()+$(this).attr('url');
		if($(this).attr('url').indexOf('http:') != -1){ // 包含http:// 字符
			url = $(this).attr('url');
		}	
		var timeLength = $(this).attr('timeLength');
		var callId = $(this).attr("callId");
		var callName = $(this).attr("custName");
		recordCallPlay(httpUrl,timeLength,url,callId,callName,callNum);
	});

	$(".custCard_timeline_content .right").each(function(){
		var $this = $(this);
		var p = $this.find(".cfu-list-note");
		var cfu_list_note_label = p.find("label");
		var cfu_list_note_span = p.find("span");
		cfu_list_note_span.width(p.width() - cfu_list_note_label.width() - 20);
		var p_height = p.height();
		var span = p.find("span");
		var span_height = span.height();
		var cfu_box_bottom = $(".cfu_box_bottom");
		var icon = $this.find("i");
		if(span.height() > p.height()){
			icon.show();
			icon.toggle(
				function(){
					p.css("height",span_height);
					cfu_box_bottom.show();
					icon.removeClass().addClass("icon_up_arrow");
				},
				function(){
					p.css("height",p_height);
					cfu_box_bottom.hide();
					icon.removeClass().addClass("icon_down_arrow");
				}
			);
		}
	});
});

function progress() {
	var stateDesc = [];
	var saleProcs = $("#saleProcs").val();
	if (saleProcs != null && saleProcs != '') {
		stateDesc = saleProcs.split("#");
	}
	var actionTag = $(".progress_actionTag");
	var total = stateDesc.length;
	var width = 100 / total;
	var add_node = '<div class="progress_actionTag_state"><img src="' + ctx + '/static/images/icon_state_small.png"/></div>';
	var add_text = '<div class="font-icon-state-small"></div>';
	for (var i = 0; i < total; i++) {
		actionTag.append(add_node);
		var state = $(".progress_actionTag_state:last");
		state.css({
			"width": width + "%",
			"left": width * i + "%"
		});
		if (i % 2 == 0) {
			state.addClass("actionTag_state_even");
			state.append(add_text);
		} else {
			state.addClass("actionTag_state_odd");
			state.prepend(add_text);
		}
		var last_text = $(".font-icon-state-small:last");
		last_text.text(stateDesc[i]);
	}
}

function init() {
	$(".style_hidden").show();
	var person_info_p_div = $(".person_info_p_div");
	person_info_p_div.find("p").each(function() {
		var $this = $(this);
		var label = $this.find("label");
		var span = $this.find("span");
//		span.width($this.width() - label.width() - 20);
		if(label.height()>22){
			label.css({
				"width":"auto",
				"text-align":"left"
			});
		}
	});
	
//	对联系地址过长之后显示bug的处理
	
	$(".style_hidden").show();
	var spans=$(".style_hidden").parent().find("p>span");
	function widthout(obj){
		if(obj.height()>30){
			obj.parent().next().css("margin-top","22px");
		}
	}
	spans.each(function(){
		var num=$(this).width();
		var bronum=$(this).parent().find("label").width();
		var parnum=$(this).parent().width();
		if(num+bronum>parnum){
			$(this).css("width","215px");
			widthout($(this));
		}
	})
	$(".style_hidden").hide();
	$(".person_info_p_div .icon_more").toggle(
			function(){
				var $this = $(this);
				$this.parent().find(".style_hidden").show();
				$this.removeClass("icon_down_arrow");
				$this.addClass("icon_up_arrow");
				$(".person_info_p_div").addClass("border-bottom");
			},
			function(){
				var $this = $(this);
				$this.parent().find(".style_hidden").hide();
				$this.removeClass("icon_up_arrow");
				$this.addClass("icon_down_arrow");
				$(".person_info_p_div").removeClass("border-bottom");
			}
	);
	
	var hyxCustCard_right = $(".hyxCustCard_right");
	var hyxCustCard_head = $("#hyxCustCard_head");
	hyxCustCard_head.width(hyxCustCard_right.width()*0.94);
	hyxCustCard_head.css("margin","0 auto");
	
	var custState = Number($("#custState").val()); //1-资源 2-意向 3-签约 4-沉默 5-流失    

	var head_content_width = $(".custCard_head_content").width();
	var solid_line = $(".custCard_progress_solid");
	var dashed_line = $(".custCard_progress_dashed");
	var custCard_mark_icon = $(".custCard_mark_icon");

	var custCard_progress_state = $(".custCard_progress_state");
	var progress_state_length = custCard_progress_state.length;
	for (var i = 0; i < custState; i++) {
		custCard_progress_state.eq(i).attr("arrived", "true");
	}
	for (var i = custState; i <= progress_state_length; i++) {
		custCard_progress_state.eq(i).attr("arrived", "false");
	}

	var progress_actionTag_state = $(".progress_actionTag_state");
	switch (custState) {
		case 1:
			custCard_progress_state.eq(custState - 1).attr("mark", "marked");
			break;
		case 2:
			if (progress_actionTag_state.length > 0) {
				var last = $(".progress_actionTag_state:last");
				last.attr("mark", "marked");
			} else {
				custCard_progress_state.eq(1).attr("mark", "marked");
			}
			break;
		case 3:
			custCard_progress_state.eq(2).attr("mark", "marked");
			break;
		case 4:
			custCard_progress_state.eq(2).attr("mark", "marked");
			custCard_mark_icon.remove();
			break;
		case 5:
			custCard_progress_state.eq(2).attr("mark", "marked");
			custCard_mark_icon.remove();
			break;
		case 6:
			custCard_mark_icon.remove();
			$(".font-icon-state-small").css("color","#808080");
			$(".progress_actionTag_state img").attr("src",ctx+"/static/images/icon_state_small_grey.png");
			$(".font-icon-state-big").css("color","#808080");
			$(".progress-icon-state-big img").attr("src",ctx+"/static/images/icon_state_big_grey.png");
			break;
		default:
			$(".font-icon-state-small").css("color","#808080");
			$(".progress_actionTag_state img").attr("src",ctx+"/static/images/icon_state_small_grey.png");
			break;
	}
	
	//判断是否是公海客户
	if(custState == 6){
		dashed_line.css({
			"width": "100%",
			"left": "0"
		});
		return;
	}
	
	if (custState != 0) {
		custCard_mark_icon.show();

		var actionTag = $(".progress_actionTag");
		var actionTag_left = actionTag.css("left");

		var marked_div = $("div[mark='marked']");

		var div_width = marked_div.css("width");
		var div_left = marked_div.css("left");

		var marked_left = parseFloat(div_width) / 2 + parseFloat(div_left);
		
		marked_div.find("img").css("visibility","hidden");
		
		if (marked_div.hasClass("progress_actionTag_state")) {
			marked_left = parseFloat(actionTag_left) + marked_left;
		}

		if (marked_div.hasClass("actionTag_state_odd")) {
			marked_div.removeClass("actionTag_state_odd").addClass("actionTag_state_even");
			marked_div.find("div").before(marked_div.find("img"));
		}

		custCard_mark_icon.css("left", marked_left - 11);
		dashed_line.css({
			"width": parseFloat(head_content_width) - marked_left,
			"left": marked_left
		});
		solid_line.css({
			"width": marked_left,
			"left": 0
		});

		if (marked_div.hasClass("progress_state_3")) {
			dashed_line.css({
				"width": 0
			});
			solid_line.css({
				"width": parseFloat(head_content_width),
				"left": 0
			});
		}
		
		//判断是不是沉默和流失客户
		if(custState == 4 || custState == 5){
			marked_div.find("img").css("visibility","visible");//如果是沉默或流失客户最后的签约蓝点显示
		}
		
	} else {
		dashed_line.css({
			"width": "100%",
			"left": "0"
		});
	}

	var state_imgs = [ctx + "/static/images/icon_state_big_grey.png", ctx + "/static/images/icon_state_big.png"];
	var custCard_progress_state = $(".custCard_progress_state");
	custCard_progress_state.each(function() {
		var $this = $(this);
		var state_img = $this.find("img");
		var font_color = $this.find(".font-icon-state-big");
		if ($this.attr("arrived") == "true") {
			state_img.attr("src", state_imgs[1]);
			font_color.css("color", "#197acb");
		} else {
			state_img.attr("src", state_imgs[0]);
			font_color.css("color", "#808080");
		}
	});

	var cust_left_menu_ul_li = $(".cust_left_menu ul li");
	var li_img_div = $(".li_img_div");
	var img_margin_top = (cust_left_menu_ul_li.height() - li_img_div.height()) / 2;
	li_img_div.css("padding-top", img_margin_top);

//	var cfu_list_note = $(".cfu-list-note");
//	var cfu_list_note_label = cfu_list_note.find("label");
//	var cfu_list_note_span = cfu_list_note.find("span");
//	cfu_list_note_span.width(cfu_list_note.width() - cfu_list_note_label.width() - 20);

	var cust_person_info = $(".cust_person_info");
	var cust_person_info_head = $(".cust_person_info_head");
	var cust_person_info_content = $(".cust_person_info_content");
	cust_person_info_content.height(cust_person_info.height() - cust_person_info_head.outerHeight());

	//	makingCenter($(".custCard_timeline"),$(".hyx-mcn-bg"));
}

var number_to_month = {
	"01": "一月",
	"02": "二月",
	"03": "三月",
	"04": "四月",
	"05": "五月",
	"06": "六月",
	"07": "七月",
	"08": "八月",
	"09": "九月",
	"10": "十月",
	"11": "十一月",
	"12": "十二月"
};