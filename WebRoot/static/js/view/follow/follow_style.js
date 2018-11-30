$(document).ready(function() {
	init_leftTop();
	hover();
	
	init_leftBottom();
	
	init_rightTop();
	initButtonArea();
	
	//init_rightBottom();
	
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
	
	$(document).click(function(){
		$(".hidden-function-area").hide();
	});
	$(".hyx-sce-right-table .tabs>li").click(function(){
		$(".hidden-function-area").hide();
	});
});

$(window).resize(function() {
	init_leftTop();
	
	init_leftBottom();
	
	init_rightTop();
	
//	init_rightBottom();
});

function init_leftTop(){
	//左上角				
	var clientHeight = $(window).height();
	var card = $(".card");
	var card_operate = $(".card_operate");
	card.height(clientHeight * 0.27);
	card_operate.height(clientHeight * 0.27);
	
	var isState = $("#state").val();
	
	$("#card_operate_head_ul").hide();
	
	var card_operate_head = $(".card_operate_head");
	var card_operate_content = $(".card_operate_content");
	var card_operate_content_table = $(".card_operate_content_table");
	var contentHeight = card_operate.height() - card_operate_head.height();
	card_operate_content.height(contentHeight + 40);
	card_operate_content_table.height(contentHeight + 40);
	
	if(isState == "1"){
		$("#card_operate_head_ul").show();
		card_operate_content.height(contentHeight - 24);
		card_operate_content_table.height(contentHeight - 24);
		var card_operate_whole_part=$(".card_operate_whole_part");
		// 页签下部整体的高度设置
		card_operate_whole_part.height(contentHeight);
		// 页签实现
		var card_operate_apart=$(".card_operate_apart");
		card_operate_apart.click(function(){
			card_operate_apart.removeClass("choosed");
			$(this).addClass("choosed");
		});
	}
	
	var operate_content_icon = $(".operate_content_icon");
	var operate_content_icons = $(".operate_content_icons");
	var content_icon_size_img = $(".content_icon_size img");
	var content_icon_size_four_img = $(".content_icon_size_four img");
	var top = (operate_content_icon.height() - content_icon_size_img.height()) / 2;
	var top_four = (operate_content_icons.height() - content_icon_size_four_img.height()) / 2;

/*	content_icon_size_img.css("margin-top", top);
	content_icon_size_four_img.css("margin-top", top_four);*/

	var operate_content_area = $(".operate_content_area");
	operate_content_area.css("line-height", operate_content_area.height() + "px");
	
//	setTimeout(function(){
//		$(".p_b_div i").hide();
//		if($(".p_b_div .sty-hid").find("p").length > 0){
//			$(".p_b_div i").show();
//		}
//	},100);
	
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
	hyx_sce_left_form.height(clientHeight * 0.6);
	
	$(".actionTag i").toggle(
		function(){
			if($(".actionTag .choose-tag").css("display")=="block"){
				$(".tip-box").css("height","30px");
			}else{
				$(".tip-box .sty-hid").show();
			}
			$(this).removeClass("icon_down");
			$(this).addClass("icon_up");
		},
		function(){
			if($(".actionTag .choose-tag").css("display")=="block"){
				$(".tip-box").css("height","auto");
			}else{
				$(".tip-box .sty-hid").hide();
			}
			$(this).addClass("icon_down");
			$(this).removeClass("icon_up");
		}
	);
	
	var leftBottom_info_height = hyx_sce_left_form.height();
	$(".icon_more_info").toggle(
		function(){
			$(".sales_info_detail_input>.sty-hid").show();
			$(this).removeClass("icon_down");
			$(this).addClass("icon_up");
			hyx_sce_left_form.css("height","auto");
		},
		function(){
			$(".sales_info_detail_input>.sty-hid").hide();
			$(this).addClass("icon_down");
			$(this).removeClass("icon_up");
			hyx_sce_left_form.height(leftBottom_info_height);
		}
	);
	
/*	$('.hyx-sce-area').find('textarea,span').click(function(){
		var $this = $(this);
		var ipt_a = $this.parent().find('textarea');
		ipt_a.focus();
		$this.parent().find('.textarea_tip').hide();
		ipt_a.blur(function(e){
			if($this.val() == ''){
				$this.parent().find('span').show();
			}else if($this.val().length > Number($this.attr("maxlength"))/2){
				$this.parent().parent().find('.error').show();
				$this.parent().parent().find('.error').text("超出最大长度!");
			}
		}); 
	});*/
	
//	$("#feedbackComment").blur(function(e){
//		e.stopPropagation();
//		var $this = $(this);
//		
//	});
	$('.select[data-multi="true"] dd ul li').live("click",function(e){
		e.stopPropagation();
		var _this = $(this);
		_this.find("a").click();
	});
}

function init_rightTop(){
	
	var otherRes_td_div_auto = $(".otherRes_td_div_auto");
	
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
	
	//点击右上表头切换效果
/*	 titchang();
	function titchang(){
		var shows_th_tit=$(".shows-th-tit");
		var shows_tit_divs=$(".shows-tit-divs");
		var shows_tit_uls=$(".shows-tit-uls");
		shows_th_tit.toggle(
			function(e){
				e.stopPropagation();
				shows_tit_divs.slideDown();
			},
			function(){
				shows_tit_divs.slideUp();
			}
		);
		shows_tit_uls.on("click","li",function(e){
			e.stopPropagation();
			shows_th_tit.find("span").eq(0).text($(this).text());
			$("#planParam").val($(this).index()+1);
			shows_tit_divs.slideUp();
		});
		$(document).click(function(){
			shows_tit_divs.hide();
		});
	}*/
	
	$(".type_choose_search").change(function(){
		$("#planParam").val($(this).val());
	});
	$(".custType_list_search").change(function(){
		$("#last_cust_type").val($(this).val());
	});
	$(".saleProcess_list_search").change(function(){
		$("#last_option_id").val($(this).val());
	});
}

function init_rightBottom(){
	//右下角  左侧总共占了82%+50px

	var cfu_list_note = $("p.cfu-list-note");
	var cfu_list_note_label = cfu_list_note.find("label");
	var cfu_list_note_span = cfu_list_note.find("span");
	cfu_list_note_span.width(cfu_list_note.width() - cfu_list_note_label.width() - 10);
	
}

function hover() {
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

function maxLength_1($this){
	$($this).val( $($this).val().substring(0, 2000));
}

function initButtonArea(){
	$.ajax({
		url: ctx+"/cust/custFollow/mainCustFollowPage",
		type: "get",
		success: function(returnData){
//			console.log(data);
			var data = $.parseJSON(returnData);
			var custTypeList = data.custTypeList;
			var saleProcessList = data.saleProcessList;
			var resGroupList = data.resGroupList;
			for(var i=0; i<custTypeList.length; i++){
				var appendCode = '<li>'+custTypeList[i].optionName+'<input type="radio" name="last_cust_type" value="'+custTypeList[i].optionlistId+'" /><span class="check-mark fa fa-check"></span></li>';
				$(".custTypeList").append(appendCode);
			}
			for(var i=0; i<saleProcessList.length; i++){
				var appendCode = '<li>'+saleProcessList[i].optionName+'<input type="radio" name="last_option_id" value="'+saleProcessList[i].optionlistId+'" /><span class="check-mark fa fa-check"></span></li>';
				$(".saleProcessList").append(appendCode);
			}
			for(var i=0; i<resGroupList.length; i++){
				var appendCode = '<li>'+resGroupList[i].groupName+'<input type="radio" name="resGroupId" value="'+resGroupList[i].resGroupId+'" /><span class="check-mark fa fa-check"></span></li>';
				$(".resGroupList").append(appendCode);
			}
			
			$(".hidden-function-area ul li").on("click",function(e){
				e.stopPropagation();
				var _this = $(this);
				_this.siblings(".active").removeClass("active");
				_this.addClass("active");
				if(_this.find("input[type='radio']").length > 0){
					_this.parent().find("input[type='radio']").attr("checked",false);
					_this.find("input[type='radio']").attr("checked",true);
				}
				clearDatePicker(_this);
			});
		},
		error: function(error){
			alert(error);
		}
	});
	var hidden_area = $(".hidden-function-area");
	var area_screen = $(".area-screen");
	var area_sort = $(".area-sort");
	var left_area_li = area_screen.find(".left-area-ul li");
	var right_areas = area_screen.find(".right-area-ul");
	hidden_area.hide();
	
	$(".btn-screen").on("click",function(e){
		e.stopPropagation();
		var _this = $(this);
		$(".button-area>li").removeClass("active");
		_this.addClass("active");
		hidden_area.hide();
		area_screen.show();
	});
	
	$(".btn-sort").on("click",function(e){
		e.stopPropagation();
		var _this = $(this);
		$(".button-area>li").removeClass("active");
		_this.addClass("active");
		hidden_area.hide();
		area_sort.show();
	});
	
	left_area_li.on("click",function(e){
		var _this = $(this);
		var index = _this.index();
		right_areas.removeClass("active");
		right_areas.eq(index).addClass("active");
	});
	
/*	$(".hidden-function-area ul li").on("click",function(e){
//		e.stopPropagation();
		var _this = $(this);
		_this.siblings(".active").removeClass("active");
		_this.addClass("active");
		if(_this.find("input[type='radio']").length > 0){
			_this.parent().find("input[type='radio']").attr("checked",false);
			_this.find("input[type='radio']").attr("checked",true);
		}
	});*/
	
	area_screen.find(".btn-submit").on("click",function(e){
//		alert("area_screen--submit");
		$("#planParam").val($("input[name='planParam']:checked").val());
		$("#last_option_id").val($("input[name='last_option_id']:checked").val());
		$("#last_cust_type").val($("input[name='last_cust_type']:checked").val());
		$("#pp_currentPage").val(1);
		$(".table-button-area .show-count").text($("#pp_currentPage").val());
		var submitData = {
			"planParam": $("input[name='planParam']:checked").val(),
			"last_cust_type": $("input[name='last_cust_type']:checked").val(),
			"last_option_id": $("input[name='last_option_id']:checked").val(),
			"resGroupId": $("input[name='resGroupId']:checked").val(),
			"tDateType": $("input[name='tDateType']:checked").val(),
			"orderType": $("input[name='orderType']:checked").val(),
			"taoStartDate": $("#taoStartDate").val(),
			"taoEndDate": $("#taoEndDate").val(),
			"custListType": $("#custType").val(),
			"custId": $("#custId").val(),
			"custCation":$("#custCation").val(),
			"isContact":$("input[name=isContact]").val(),
			"page.showCount": $("#pp_showCount").val(),
			"page.currentPage": $("#pp_currentPage").val()
		};
		custFollowRightWait(submitData);
		area_screen.hide();
	});
	area_screen.find(".btn-reset").on("click",function(e){
		e.stopPropagation();
		left_area_li.eq(0).click();
		right_areas.each(function(index){
			var _this = $(this);
			_this.find("li").eq(0).click();
		});
	});
	area_sort.find(".btn-submit").on("click",function(e){
//		alert("area_sort--submit");
		$("#planParam").val($("input[name='planParam']:checked").val());
		$("#last_option_id").val($("input[name='last_option_id']:checked").val());
		$("#last_cust_type").val($("input[name='last_cust_type']:checked").val());
		$("#pp_currentPage").val(1);
		$(".table-button-area .show-count").text($("#pp_currentPage").val());
		var submitData = {
			"planParam": $("input[name='planParam']:checked").val(),
			"last_cust_type": $("input[name='last_cust_type']:checked").val(),
			"last_option_id": $("input[name='last_option_id']:checked").val(),
			"resGroupId": $("input[name='resGroupId']:checked").val(),
			"tDateType": $("input[name='tDateType']:checked").val(),
			"orderType": $("input[name='orderType']:checked").val(),
			"taoStartDate": $("#taoStartDate").val(),
			"taoEndDate": $("#taoEndDate").val(),
			"custListType": $("#custType").val(),
			"custId": $("#custId").val(),
			"custCation":$("#custCation").val(),
			"isContact":$("input[name=isContact]").val(),
			"page.showCount": $("#pp_showCount").val(),
			"page.currentPage": $("#pp_currentPage").val()
		};
		custFollowRightWait(submitData);
		area_sort.hide();
	});
	area_sort.find(".btn-cancel").on("click",function(e){
		area_sort.hide();
	});
	
//  最近联系时间
    $('.dateRange').dateRangePicker({
    	showShortcuts:false,
        endDate:moment().format("YYYY-MM-DD"),
        format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
    	var s_t = '';
    	var e_t = '';
    	var _this = $(this);
    	if(obj.date1 != 'Invalid Date'){
//    		$('#d_lastStartActionDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    		$("#taoStartDate").val(moment(obj.date1).format('YYYY-MM-DD'));
    		s_t = moment(obj.date1).format('YY.MM.DD');
    	}
    	if(obj.date2 != 'Invalid Date'){
//    		$('#d_lastEndActionDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    		$("#taoEndDate").val(moment(obj.date2).format('YYYY-MM-DD'));
    		e_t = moment(obj.date2).format('YY.MM.DD');
    	}
    	//_this.html("自定义<input type='radio' name='tDateType' value='5' checked='checked' />("+s_t+"/"+e_t+")");
    	_this.find(".dateValueInit").html("("+s_t+"/"+e_t+")");
    });
    
    function submitQuery(){
    	$.ajax({
    		url: ctx+'/cust/custFollow/custFollowRightPage',
    		data: {
    			"planParam": $("input[name='planParam']:checked").val(),
    			"last_cust_type": $("input[name='last_cust_type']:checked").val(),
    			"last_option_id": $("input[name='last_option_id']:checked").val(),
    			"resGroupId": $("input[name='resGroupId']:checked").val(),
    			"tDateType": $("input[name='tDateType']:checked").val(),
    			"orderType": $("input[name='orderType']:checked").val(),
    			"taoStartDate": $("#taoStartDate").val(),
    			"taoEndDate": $("#taoEndDate").val(),
    			"custListType": $("#custType").val(),
    			"custId": $("#custId").val(),
    			"custCation":$("#custCation").val()
    		},
    		success: function(data){
    			//console.log(data);
//    			alert("success");
    		},
    		error: function(error){
    			alert("error");
    		}
    	});
    }
}


function clearDatePicker(liele){
	var childInputs = liele.find("input[name='tDateType']");
	if( childInputs && childInputs.val() != 5 ){
		$(".dateValueInit").html("");
		$("#taoStartDate").val("");
		$("#taoEndDate").val("");
	}
}