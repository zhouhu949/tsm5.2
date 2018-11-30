
$(function(){
	$("[id^=addMore_]").live("click",function(){
		var type = $(this).attr("id").split("_")[1];
		var currentPage = $("#currentPage").val();
		$("#currentPage").val(parseInt(currentPage) + 1);
		$(this).remove();
		getTimeLine(type);
	});
	
	var tabType = $("#tabType").val();
	if(tabType == "1"){
		$(".custCard_button_area>button").hide();
		$("#btn_follow").show();
		$(".cust_follow_area").css("visibility","hidden");
		$(".cust_follow_area").height(0);
	}else{
		$(".cust_follow_area").css("visibility","hidden");
		$(".cust_follow_area").height(0);
		$(".custCard_button_area>button").hide();
	}
	
	$("#btn_follow").on("click",function(event){
		event.stopPropagation();
		$(".cust_follow_area").css("visibility","visible");
		$(".cust_follow_area").height(330);
		$(".custCard_button_area>button").show();
		$(this).attr("disabled","true");
		/*$(this).hide();*/
	});
	
	$("#btn_giveUp").on("click",function(event){
		event.stopPropagation();
		$("#followFrame")[0].contentWindow.giveUpCust();
	});
	
	$("#btn_sign").on("click",function(event){
		event.stopPropagation();
		$("#followFrame")[0].contentWindow.sign();
	});
	
	$("#btn_cancel").on("click",function(event){
		event.stopPropagation();
		$(".cust_follow_area").css("visibility","hidden");
		$(".cust_follow_area").height(0);
		$(".custCard_button_area>button").hide();
		$("#btn_follow").show();
		$("#followFrame")[0].src = $("#followFrame")[0].src;
		$("#btn_follow").removeAttr("disabled");
	});
	
	$("#btn_save").on("click",function(event){
		event.stopPropagation();
		$(this).attr("disabled","true");
		$("#followFrame")[0].contentWindow.save();
	});
	
	$("#btn_tao").on("click",function(event){
		event.stopPropagation();
		$(this).attr("disabled","true");
		//todo
		$("#followFrame")[0].contentWindow.tao();
	});
});
//function addMore(type, obj) {
//	var currentPage = $("#currentPage").val();
//	$("#currentPage").val(parseInt(currentPage) + 1);
//	$(obj).remove();
//	getTimeLine(type);
//}

function addMoreCall(obj) {
	var currentPage = $("#currentPage").val();
	$("#currentPage").val(parseInt(currentPage) + 1);
	$(obj).remove();
	getCallTimeLine();
}

function getCallTimeLine() {
	var custId = $("#custId").val();
	var dataObj = new Object();
	dataObj.custId = custId;
	var showCount = $("#showCount").val();
	var totalResult = $("#totalResult").val();
	var currentPage = $("#currentPage").val();
	var page = new Object();
	page.showCount = showCount;
	page.totalResult = totalResult;
	page.currentPage = currentPage;
	dataObj.page = page;
	$.ajax({
		url: ctx + '/cust/card/callRecord',
		type: 'post',
		data: JSON.stringify(dataObj),
		contentType: "application/json",
		dataType: 'json',
		error: function() {},
		success: function(data) {
			var timeLineData = data.datas;
			var pageData = data.page;
			$("#showCount").val(pageData.showCount);
			$("#totalResult").val(pageData.totalResult);
			$("#currentPage").val(pageData.currentPage);
			var html = '';
			if (!$.isEmptyObject(timeLineData)) {
				$.each(timeLineData, function(index, tld) {
					var timeLineData = data.datas;
					var pageData = data.page;
					$("#showCount").val(pageData.showCount);
					$("#totalResult").val(pageData.totalResult);
					$("#currentPage").val(pageData.currentPage);
					if (!$.isEmptyObject(timeLineData)) {
						if ($("#clock").length == 0) {
							$("#lineDiv").prepend('<div id="clock" class="timeline_clock"></div>');
							$("#lineDiv").append('<ul id="timeLine"></ul>');
						}
						for (key in tld) {
							var obj = tld[key];
							//拼时间轴html
							//							var  key.spilt("-");
							//							alert( key.split("-"));
							html += createCallLineHtml(obj, key);
						}
					}
				});
				if (pageData.currentPage < pageData.totalPage) {
					//有多页 此处加载翻页按钮
					html += '</li><div class="hyx-mca-nonew" onclick="addMoreCall(this)">' +
						'<span class="line fl_l"></span> <label class="fl_l">加载更多</label>' +
						'<span class="line fl_r"></span>' +
						'</div>';
				}else{
					html += '</li><div class="hyx-mca-nonew">' +
					'<span class="line fl_l"></span> <label class="fl_l">没有更多记录</label>' +
					'<span class="line fl_r"></span>' +
					'</div>';
				}
			} else {
				html += '<div class="hyx-mcn-bg"><span>暂无记录！</span></div>';
			}

			if ($("#clock").length > 0) {
				$("#timeLine").append(html);
			} else {
				$("#lineDiv").append(html);
			}
			var left_timeline = $(".left_timeline");
			var custCard_timeline_content = $(".custCard_timeline_content");
			left_timeline.height(custCard_timeline_content.height() - 30);
			if ($("#timeLine li").length > 0) {
				left_timeline.css("visibility","visible");
			} else {
				left_timeline.css("visibility","hidden");
			}
			
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
			
		}
	});
}

function getTimeLine(type) {
	var ajax_url = ctx + '/cust/card/actionData';
	var dataObj = new Object();
	var custId = $("#custId").val();
	dataObj.custId = custId;
	if(type != '1'){
		dataObj.type = type;
		ajax_url = ctx + '/cust/card/data';
	}
	var showCount = $("#showCount").val();
	var totalResult = $("#totalResult").val();
	var currentPage = $("#currentPage").val();
	var page = new Object();
	page.showCount = showCount;
	page.totalResult = totalResult;
	page.currentPage = currentPage;
	dataObj.page = page;
	$.ajax({
		url: ajax_url,
		type: 'post',
		data: JSON.stringify(dataObj),
		contentType: "application/json",
		dataType: 'json',
		error: function() {},
		success: function(data) {
			var timeLineData = data.datas;
			var pageData = data.page;
			$("#showCount").val(pageData.showCount);
			$("#totalResult").val(pageData.totalResult);
			$("#currentPage").val(pageData.currentPage);
			var html = '';
			if (!$.isEmptyObject(timeLineData)) {
				if ($("#clock").length == 0) {
					$("#lineDiv").prepend('<div id="clock" class="timeline_clock"></div>');
					$("#lineDiv").append('<ul id="timeLine"></ul>');
				}
				$.each(timeLineData, function(index, tld) {
					for (key in tld) {
						var obj = tld[key];
						//拼时间轴html
						//						var  key.spilt("-");
						//						alert( key.split("-"));
						html += createLineHtml(obj, type, key);
					}
				});

				if (pageData.currentPage < pageData.totalPage) {
					//有多页 此处加载翻页按钮
					html += '<div class="hyx-mca-nonew" id="addMore_'+type+'">' +
						'<span class="line fl_l"></span> <label class="fl_l">加载更多</label>' +
						'<span class="line fl_r"></span>' +
						'</div>';
				} else {
						html += '<div class="hyx-mca-nonew">' +
						'<span class="line fl_l"></span> <label class="fl_l">没有更多记录</label>' +
						'<span class="line fl_r"></span>' +
						'</div>';
				}
			} else {
				//无记录
				html += '<div class="hyx-mcn-bg"><span>暂无记录！</span></div>';
			}
			if ($("#clock").length > 0) {
				$("#timeLine").append(html);
			} else {
				$("#lineDiv").append(html);
			}
			var left_timeline = $(".left_timeline");
			var custCard_timeline_content = $(".custCard_timeline_content");
			left_timeline.height(custCard_timeline_content.height() - 30);
			if ($("#timeLine li").length > 0) {
				left_timeline.css("visibility","visible");
			} else {
				left_timeline.css("visibility","hidden");
			}
			$(".custCard_timeline_content .right").each(function(){
				var $this = $(this);
				var p = $this.find(".cfu-list-note");
				var cfu_list_note_label = p.find("label");
				var cfu_list_note_span = p.find("span");
				var cfu_list_note_hetong = p.find(".shows-hetong");
				cfu_list_note_span.width(p.width() - cfu_list_note_label.width() - 20);
				cfu_list_note_hetong.width(p.width()/5 - cfu_list_note_label.width()-10);
				/*var p_height = p.height();
				var span = p.find("span");
				var span_height = span.height();
				var cfu_box_bottom = $this.find(".cfu_box_bottom");
				var icon = $this.find("i");
				if(cfu_box_bottom.find("label").length == 0 && span_height == 26){
					icon.hide();
				}else{
					icon.toggle(
						function(){
							p.css("height",span_height);
							cfu_box_bottom.show();
							icon.removeClass().addClass("icon_up_arrow");
							$(".left_timeline").height($(".custCard_timeline_content").height() -30);
						},
						function(){
							p.css("height",p_height);
							cfu_box_bottom.hide();
							icon.removeClass().addClass("icon_down_arrow");
							$(".left_timeline").height($(".custCard_timeline_content").height() -30);
						}
					);
				}*/
			});
		}
	});
}

function createCallLineHtml(datas,key) {
	var html = '';
	var isState = $("#isState").val();
	$.each(datas, function(index, data) {
		if (index == 0 && $("div[dk=" + key + "]").length == 0) {
			html +=
				'<li>' +
				'<div dk=' + key + ' class="timeline_month">' + key.split("-")[2] + '<br/><span>' + number_to_month[key.split("-")[1]] + '</span>' +
				'</div>' +
				'<div class="time_node"></div>' +
				'<div class="right">' +
				'<div class="arrow"><span>◆</span><em>◆</em></div>' +
				'<div class="cfu-box">' +
				'	<div class="border-bottom cfu_box_head">' +
				'		<label class="fl_l">联系时间：</label><span class="cfu_time fl_l">' + data.startTime + '</span>';
			if (data.recordState == 1) {
				html += '		<span class="cfu_record fl_r"><span rc="voice" callNum="' + getCallNum(data) + '" custName="' + data.custName + '" url="' + data.recordUrl + '&d=' + data.recordKey + '&id=' + data.code + '&compid='+data.orgId+'&calllen='+data.timeLength+'" callId="' + data.id + '" timeLength="' + data.timeLength + '" class="icon-play"></span>' + getHoursTime(data.timeLength) + '</span>';
			}
			html += '	</div>' +
				'	<p class="cfu-list-note" >' +
				'		<label class="fl_l">呼叫类型：' + getCallState(data.callState,data.timeLength) + '</label>' +
				'		<label class="fl_l">主叫号码：<span phone="tel">' + data.callerNum + '</span></label>' +
				'		<label class="fl_l">被叫号码：<span phone="tel">' + data.calledNum + '</span></label>' ;
				if(data.define1 != null && data.define1 != ''){
					html+=
					'		<label class="fl_l">'+(isState == '1' ? '客户联系人：' : '客户姓名：') + data.define1 + '</label>' ;
				}
				html+=
				'		<label class="fl_l">联系人：' + data.inputName + '</label>' +
				'	</p>' +
				'</div>';
		} else {
			html +=
				'<li>' +
				'<div class="time_node"></div>' +
				'<div class="right">' +
				'<div class="arrow"><span>◆</span><em>◆</em></div>' +
				'<div class="cfu-box">' +
				'	<div class="border-bottom cfu_box_head">' +
				'		<label class="fl_l">联系时间：</label><span class="cfu_time fl_l">' + data.startTime + '</span>';
			if (data.recordState == 1) {
				html += '		<span class="cfu_record fl_r"><span rc="voice" callNum="' + getCallNum(data) + '" custName="' + data.custName + '" url="' + data.recordUrl + '&d=' + data.recordKey + '&id=' + data.code + '&compid='+data.orgId+'&calllen='+data.timeLength+'" callId="' + data.id + '" timeLength="' + data.timeLength + '" class="icon-play"></span>' + getHoursTime(data.timeLength) + '</span>';
			}
			html += '	</div>' +
				'	<p class="cfu-list-note" >' +
				'		<label class="fl_l">呼叫类型：' + getCallState(data.callState,data.timeLength) + '</label>' +
				'		<label class="fl_l">主叫号码：<span phone="tel">' + data.callerNum + '</span></label>' +
				'		<label class="fl_l">被叫号码：<span phone="tel">' + data.calledNum + '</span></label>';
				if(data.define1 != null && data.define1 != ''){
					html+=
					'		<label class="fl_l">'+(isState == '1' ? '客户联系人：' : '客户姓名：') + data.define1 + '</label>' ;
				}
				html+=
				'		<label class="fl_l">联系人：' + data.inputName + '</label>' +
				'	</p>' +
				'</div>';
		}
	});
	return html;
}

function createLineHtml(datas, type, key) {
	var html = '';
	$.each(datas, function(index, data) {
		if (index == 0 && $("div[dk=" + key + "]").length == 0) {
			html +=
				'<li>' +
				'<div dk=' + key + ' class="timeline_month">' + key.split("-")[2] + '<br/><span>' + number_to_month[key.split("-")[1]] + '</span>' +
				'</div>' +
				'<div class="time_node"></div>' +
				'<div class="right">' +
				'<div class="arrow"><span>◆</span><em>◆</em></div>';

			
			if (type == '1') {
				var custId = $('#custId').val();
				var isState = $("#isState").val();
				var custType = $("#custType").val();
				var custStatus = $("#custStatus").val();
				var custName = $("#custName").val();
				var serverDay = parseInt($("#serverDay").val());
				var lastOptionId = $("#lastOptionId").val();
				var lastOptionName = $("#lastOptionName").val();
				html +=	'<div class="cfu-box">' +
								'<div class="border-bottom cfu_box_head">' +
								'<label class="fl_l">联系时间：</label><span class="cfu_time fl_l">' + data.inputDate + '</span>' ;
				if(data.actionType != null && data.actionType != ''){
					html+='<label class="fl_l">联系方式：</label><span class="cfu_time fl_l">' + getContractWay(data.actionType) + '</span>' ;
				}
				if(isState == '1' && data.mainLinkman != null && data.mainLinkman != ''){
					html+='<label class="fl_l">客户联系人：</label><span class="cfu_contact fl_l">' + data.mainLinkman + '</span>' ;
					if(data.telphone != null && data.telphone != ''){
						html+=
						'<a href="javascript:;" '+(serverDay > 0 ? 'id="call_'+data.telphone+'"' : '')+' custId="'+custId+'" custName="'+custName+'" custType="'+custType+'" custState="'+custStatus+'" define1="'+data.mainLinkman+'" lastOptionId="'+lastOptionId+'" lastOptionName="'+lastOptionName+'" class="tel icon-phone '+(serverDay > 0 ? '' : 'img-gray')+'" title="拨打电话" style="margin-left:5px;"></a>';
					}
				}
				html+='<label class="fl_l"  style="margin-left:30px;">联系人：</label><span class="cfu_contact fl_l">' + data.inputName + '</span>' +
							getVoice(data.records) +
							'</div>' ;
				if(data.saleProcessName == null || data.saleProcessName == ''){
					html+=
					'<p class="cfu-list-note" title="">'+
						'<label class="fl_l">资源备注：</label>' +
						'<span class="fl_l">' + data.remark + '</span>' +
					'</p>' +
					'<div class="cfu_box_bottom border-top fl_l">' ;
					if(data.nextConcatTime != null){
						html+='<label>下次联系时间：</label><span>'+data.nextConcatTime+'</span>' ;
					}
					html+='</div>' +
					'</div>';
				}else{
					if(data.remark != null && data.remark != ''){
						html+=
							'<p class="cfu-list-note" title="">'+
								'<label class="fl_l">联系小记：</label>' +
								'<span class="fl_l">' + data.remark + '</span>' +
							'</p>' +
							'<div class="cfu_box_bottom border-top fl_l">' ;
								var labelsStr = data.labels == null ? '' : data.labels.replace(/#$/g,"").replace(/#/g,'，');
								if(labelsStr != ''){
									html+= 
									'<label>行动标签：</label><span>'+labelsStr+'</span>' +
									'<label>销售进程：</label><span>'+data.saleProcessName+'</span>';
								}else{
									html+=
									'<label>销售进程：</label><span>'+data.saleProcessName+'</span>';
								}
								if(data.nextConcatTime != null && data.nextConcatTime != ''){
									if(labelsStr.length >27){
										html +=
											'<br><label>下次联系时间：</label><span>'+(data.nextConcatTime == null ? '' : data.nextConcatTime)+'</span>';
									}else{
										html +=
											'<label>下次联系时间：</label><span>'+(data.nextConcatTime == null ? '' : data.nextConcatTime)+'</span>';
									}
								}
								html+='</div></div>';
					}else{
						html+=
							'<p class="cfu-list-note" title="">'+
								'<label class="fl_l">销售进程：</label>' +
								'<span class="fl_l">' + data.saleProcessName + '</span>' +
							'</p>' ;
					}
				}
			} else if(type=='2'){
				html+='<div class="cfu-box">'+
				'<div class="border-bottom cfu_box_head">' +
				'<label class="fl_l">回访时间：</label><span class="cfu_time fl_l">' + data.visitingDate + '</span>' +
				'<label class="fl_l">回访人员：</label><span class="cfu_time fl_l">' + data.visitingName + '</span>'+
				'<label class="fl_l">客户联系人：</label><span class="cfu_time fl_l">' + data.mainLinkman + '</span>';
				html+='<span title="'+getContractWay(data.visitingType)+'" class="iconInCardBack '+getContractWayImgName(data.visitingType)+' littleIconInBo"></span>';
				if(data.effectiveness == 1){
					html+='<span class="iconInCardBack valid_follow" title="有效联系"></span>';
				}else{
					html+='<span class="iconInCardBack novalid_follow" title="无效联系"></span>';
				}
				html+='</div>'+
				'<div class="fl_l" style="width:100%;">' ;
				var labelsStr = data.labelName == null ? '' : data.labelName.replace(/#$/g,"").replace(/#/g,'，');
				if(data.serviceLevelName != null && data.serviceLevelName != ''){
					html+= 
						'<label class="fl_l">客户等级：</label><span class="fl_l">'+data.serviceLevelName+'</span>';
					}
					if(labelsStr != ''){
						html+= 
						'<label class="fl_l">服务标签：</label><span class="fl_l">'+labelsStr+'</span>';
					}
				html+='</div>' +
				'<p class="cfu-list-note" style="width:100%;" title="">'+
				'<label class="fl_l">联系小记：</label>' +
				'<span class="fl_l">' + data.remark + '</span>' +
			'</p>' +
				'</div>';
			} else if(type == '4') {
				html += '<div class="cfu-box">' +
					'<div class="border-bottom cfu_box_head">' +
					'<label class="fl_l">点评时间：</label><span class="cfu_time fl_l">' + data.reviewDate + '</span>' +
					'<label class="fl_l">点评人：</label><span class="cfu_contact fl_l">' + data.reviewName + '</span>' +
					'</div>' +
					'<p class="cfu-list-note" title="">' +
					'<label class="fl_l">点评内容：</label>' +
					'<span class="fl_l">' + data.revComment + '</span>' +
					'</p>' +
					'</div>';
			}else if(type == '5'){
					html += '<div class="cfu-box">' +
					'<div class="border-bottom cfu_box_head">' +
					'<label class="fl_l">签约日期：</label><span class="cfu_time fl_l">' + data.inputtime + '</span>' +
					'<label class="fl_r"><a href="javascript:;" id="contractView_'+data.id+'">查看详情</a></label>' +
					'</div>' +
					'<p class="cfu-list-note" title="">' +
					'<label class="fl_l">合同编号：</label>' +
					'<span class="fl_l shows-dingdan" title='+ data.code+'>' + data.code + '</span>' +
					'<label class="fl_l">合同名称：</label>' +
					'<span class="fl_l shows-dingdan" title='+ data.contractName+'>' + data.contractName + '</span>' ;
					if(data.money != null && data.money != ''){
						html+='<label class="fl_l">合同金额：</label>' +
						'<span class="fl_l shows-dingdan" title='+ data.money+'万元>' + data.money + '万元</span>' ;
					}
					html+=
					'<label class="fl_l">签约人：</label>' +
					'<span class="fl_l shows-dingdan" title='+ data.signUsername+'>' + data.signUsername + '</span>' +
					'</p>' +
					'</div>';
			}else if(type == '6'){
					html += '<div class="cfu-box">' +
					'<div class="border-bottom cfu_box_head">' +
					'<label class="fl_l">交易日期：</label><span class="cfu_time fl_l">' + data.inputtime + '</span>' +
					'<label class="fl_r"><a href="javascript:;" id="orderView_'+data.id+'">查看详情</a></label>' +
					'</div>' +
					'<p class="cfu-list-note" title="">' ;
					if(data.contractCode != null && data.contractCode != ''){
						html+='<label class="fl_l">合同编号：</label>' +
						'<span class="fl_l shows-hetong" title='+ data.contractCode+'>' + data.contractCode + '</span>' ;
					}
					html+='<label class="fl_l">订单编号：</label>' +
					'<span class="fl_l shows-hetong" title='+ data.code+'>' + data.code + '</span>' +
					'<label class="fl_l">订单金额：</label>' +
					'<span class="fl_l shows-hetong" title='+ data.money+'元>' + data.money + '元</span>' ;
					if(data.payType != null && data.payType != ''){
						html+='<label class="fl_l">付款方式：</label>' +
						'<span class="fl_l shows-hetong" title='+ getPayType(data.payType)+'>' + getPayType(data.payType) + '</span>' ;
					}
					html+=
					'<label class="fl_l">销售人员：</label>' +
					'<span class="fl_l shows-hetong" title='+ data.userName+'>' + data.userName + '</span>' +
					'</p>' +
					'</div>';
			}
			html += /*'<i class="icon_down_arrow"></i>' +*/
				'</div>' +
				'</li>';
		} else {
			html +=
				'<li>' +
				'<div class="time_node"></div>' +
				'<div class="right">' +
				'<div class="arrow"><span>◆</span><em>◆</em></div>';
			if (type == '1') {
				var custId = $('#custId').val();
				var isState = $("#isState").val();
				var custType = $("#custType").val();
				var custStatus = $("#custStatus").val();
				var custName = $("#custName").val();
				var serverDay = parseInt($("#serverDay").val());
				var lastOptionId = $("#lastOptionId").val();
				var lastOptionName = $("#lastOptionName").val();
				html += '<div class="cfu-box">' +
					'<div class="border-bottom cfu_box_head">' +
					'<label class="fl_l">联系时间：</label><span class="cfu_time fl_l">' + data.inputDate + '</span>' ;
					if(data.actionType != null && data.actionType != ''){
						html+='<label class="fl_l">联系方式：</label><span class="cfu_time fl_l">' + getContractWay(data.actionType) + '</span>' ;
					}
					if(isState == '1' && data.mainLinkman != null && data.mainLinkman != ''){
						html+='<label class="fl_l">客户联系人：</label><span class="cfu_contact fl_l">' + data.mainLinkman + '</span>' ;
						if(data.telphone != null && data.telphone != ''){
							html+=
							'<a href="javascript:;" '+(serverDay > 0 ? 'id="call_'+data.telphone+'"' : '')+' custId="'+custId+'" custName="'+custName+'" custType="'+custType+'" custState="'+custStatus+'" define1="'+data.mainLinkman+'" lastOptionId="'+lastOptionId+'" lastOptionName="'+lastOptionName+'" class="tel icon-phone '+(serverDay > 0 ? '' : 'img-gray')+'" title="拨打电话" style="margin-left:5px;"></a>';
						}
					}
					html+='<label class="fl_l"  style="margin-left:30px;">联系人：</label><span class="cfu_contact fl_l">' + data.inputName + '</span>' +
						getVoice(data.records) +
					'</div>' ;
					if(data.saleProcessName == null || data.saleProcessName == ''){
						html+=
						'<p class="cfu-list-note" title="">' + 
							'<label class="fl_l">资源备注：</label>' +
							'<span class="fl_l">' + data.remark + '</span>' +
						'</p>' +
						'<div class="cfu_box_bottom border-top fl_l">' ;
						if(data.nextConcatTime != null){
							html+='<label>下次联系时间：</label><span>'+data.nextConcatTime+'</span>' ;
						}
						html+='</div>' +
						'</div>';
					}else{
						if(data.remark != null && data.remark != ''){
							html+=
								'<p class="cfu-list-note" title="">'+
									'<label class="fl_l">联系小记：</label>' +
									'<span class="fl_l">' + data.remark + '</span>' +
								'</p>' +
								'<div class="cfu_box_bottom border-top fl_l">' ;
									var labelsStr = data.labels == null ? '' : data.labels.replace(/#$/g,"").replace(/#/g,'，');
									if(labelsStr != ''){
										html+= 
										'<label>行动标签：</label><span>'+labelsStr+'</span>' +
										'<label>销售进程：</label><span>'+data.saleProcessName+'</span>';
									}else{
										html+=
										'<label>销售进程：</label><span>'+data.saleProcessName+'</span>';
									}
									if(data.nextConcatTime != null && data.nextConcatTime != ''){
										if(labelsStr.length >27){
											html +=
												'<br><label>下次联系时间：</label><span>'+(data.nextConcatTime == null ? '' : data.nextConcatTime)+'</span>';
										}else{
											html +=
												'<label>下次联系时间：</label><span>'+(data.nextConcatTime == null ? '' : data.nextConcatTime)+'</span>';
										}
									}
									html+='</div></div>';
						}else{
							html+=
								'<p class="cfu-list-note" title="">'+
									'<label class="fl_l">销售进程：</label>' +
									'<span class="fl_l">' + data.saleProcessName + '</span>' +
								'</p>' ;
						}
					}
			}else if(type=='2'){
				html+='<div class="cfu-box">'+
				'<div class="border-bottom cfu_box_head">' +
				'<label class="fl_l">回访时间：</label><span class="cfu_time fl_l">' + data.visitingDate + '</span>' +
				'<label class="fl_l">回访人员：</label><span class="cfu_time fl_l">' + data.visitingName + '</span>'+
				'<label class="fl_l">客户联系人：</label><span class="cfu_time fl_l">' + data.mainLinkman + '</span>';
				html+='<span title="'+getContractWay(data.visitingType)+'" class="iconInCardBack '+getContractWayImgName(data.visitingType)+' littleIconInBo"></span>';
				if(data.effectiveness == 1){
					html+='<span class="iconInCardBack valid_follow" title="有效联系"></span>';
				}else{
					html+='<span class="iconInCardBack novalid_follow" title="无效联系"></span>';
				}
				html+='</div>'+
				'<div class="fl_l" style="width:100%;">' ;
				var labelsStr = data.labelName == null ? '' : data.labelName.replace(/#$/g,"").replace(/#/g,'，');
				if(data.serviceLevelName != null && data.serviceLevelName != ''){
					html+= 
						'<label class="fl_l">客户等级：</label><span class="fl_l">'+data.serviceLevelName+'</span>';
					}
					if(labelsStr != ''){
						html+= 
						'<label class="fl_l">服务标签：</label><span class="fl_l">'+labelsStr+'</span>';
					}
				html+='</div>' +
				'<p class="cfu-list-note" style="width:100%;" title="">'+
				'<label class="fl_l">联系小记：</label>' +
				'<span class="fl_l">' + data.remark + '</span>' +
			'</p>' +
				'</div>';
			}  else if(type == '4'){
				html += '<div class="cfu-box">' +
					'<div class="border-bottom cfu_box_head">' +
					'<label class="fl_l">点评时间：</label><span class="cfu_time fl_l">' + data.reviewDate + '</span>' +
					'<label class="fl_l">点评人：</label><span class="cfu_contact fl_l">' + data.reviewName + '</span>' +
					'</div>' +
					'<p class="cfu-list-note" title="">' +
					'<label class="fl_l">点评内容：</label>' +
					'<span class="fl_l">' + data.revComment + '</span>' +
					'</p>' +
					'</div>';
			}else if(type == '5'){
				html += '<div class="cfu-box">' +
				'<div class="border-bottom cfu_box_head">' +
				'<label class="fl_l">签约日期：</label><span class="cfu_time fl_l">' + data.inputtime + '</span>' +
				'<label class="fl_r"><a href="javascript:;" id="contractView_'+data.id+'">查看详情</a></label>' +
				'</div>' +
				'<p class="cfu-list-note" title="">' +
				'<label class="fl_l">合同编号：</label>' +
				'<span class="fl_l shows-dingdan" title='+ data.code+'>' + data.code + '</span>' +
				'<label class="fl_l">合同名称：</label>' +
				'<span class="fl_l shows-dingdan" title='+ data.contractName+'>' + data.contractName + '</span>' ;
				if(data.money != null && data.money != ''){
					html+='<label class="fl_l">合同金额：</label>' +
					'<span class="fl_l shows-dingdan" title='+ data.money+'万元>' + data.money + '万元</span>' ;
				}
				html+=
				'<label class="fl_l">签约人：</label>' +
				'<span class="fl_l shows-dingdan" title='+ data.signUsername+'>' + data.signUsername + '</span>' +
				'</p>' +
				'</div>';
			}else if(type == '6'){
				html += '<div class="cfu-box">' +
				'<div class="border-bottom cfu_box_head">' +
					'<label class="fl_l">交易日期：</label><span class="cfu_time fl_l">' + data.inputtime + '</span>' +
					'<label class="fl_r"><a href="javascript:;" id="orderView_'+data.id+'">查看详情</a></label>' +
					'</div>' +
					'<p class="cfu-list-note" title="">' ;
					if(data.contractCode != null && data.contractCode != ''){
						html+='<label class="fl_l">合同编号：</label>' +
						'<span class="fl_l shows-hetong" title='+ data.contractCode+'>' + data.contractCode + '</span>' ;
					}
					html+='<label class="fl_l">订单编号：</label>' +
					'<span class="fl_l shows-hetong" title='+ data.code+'>' + data.code + '</span>' +
					'<label class="fl_l">订单金额：</label>' +
					'<span class="fl_l shows-hetong" title='+ data.money+'元>' + data.money + '元</span>' ;
					if(data.payType != null && data.payType != ''){
						html+='<label class="fl_l">付款方式：</label>' +
						'<span class="fl_l shows-hetong" title='+ getPayType(data.payType)+'>' + getPayType(data.payType) + '</span>' ;
					}
					html+=
					'<label class="fl_l">销售人员：</label>' +
					'<span class="fl_l shows-hetong" title='+ data.userName+'>' + data.userName + '</span>' +
					'</p>' +
				'</div>';
			}
			html += /*'<i class="icon_down_arrow"></i>' +*/
				'</div>' +
				'</li>';
		}
	});
	return html;
}

function makingCenter(father, child) {
	var father_height = father.height();
	var child_height = child.height();
	var top = (father_height - child_height) / 2;
	child.css("margin-top", top);
}

function getVoice(datas) {
	var html = '';
	if (datas != null) {
		var i = 0;
		$.each(datas, function(index, data) {
			if (data.recordState == 1) {
				if (i == 0) {
					html += '<span class="cfu_record fl_r"><span rc="voice" callNum="' + getCallNum(data) + '" custName="' + data.custName + '" url="' + data.recordUrl + '&d=' + data.recordKey + '&id=' + data.code + '&compid='+data.orgId+'&calllen='+data.timeLength+'" callId="' + data.id + '" timeLength="' + data.timeLength + '" class="icon-play"></span>' + getHoursTime(data.timeLength) + '</span>';
				} else {
					html += '<span class="cfu_record fl_r margin_right_10"><span rc="voice" callNum="' + getCallNum(data) + '" custName="' + data.custName + '" url="' + data.recordUrl + '&d=' + data.recordKey + '&id=' + data.code + '&compid='+data.orgId+'&calllen='+data.timeLength+'" callId="' + data.id + '" timeLength="' + data.timeLength + '" class="icon-play"></span>' + getHoursTime(data.timeLength) + '</span>';
				}
				i++;
			}
		});
	}
	return html;
}

function getCallNum(tld) {
	if (tld.callState == "1") {
		return tld.callerNum;
	} else {
		return tld.calledNum;
	}
}

function getPayType(payType){
	if(payType == '1'){
		return '现金';
	}else if(payType == '2'){
		return '银行转账/汇款';
	}else if(payType == '3'){
		return '在线支付';
	}else if(payType == '4'){
		return '支付宝转账';
	}
}

//将秒转换为小时
function getHoursTime(second) {
	second = Math.ceil(second); //向上取整
	var h = 0;
	var d = 0;
	var s = 0;
	var str = "";
	var temp = parseInt(second % 3600); //舍去小数
	if (second >= 3600) {
		h = parseInt(second / 3600);
		if (temp != 0) {
			if (temp >= 60) {
				d = parseInt(temp / 60);
				if (temp % 60 != 0) {
					s = parseInt(temp % 60);
				}
			} else {
				s = parseInt(temp);
			}
		}
	} else {
		d = parseInt(second / 60);
		if (second % 60 != 0) {
			s = Math.ceil(second % 60);
		}
	}

	if (h > 0) {
		str = str + "" + h + "小时";
	}
	if (d > 0) {
		str = str + d + "分";
	}
	if (s > 0) {
		str = str + s + "秒";
	}
	if (s == 0 && h == 0 && d == 0) {
		str = "0秒";
	}
	return str;

}

function getContractWay(type){
	if(type == '1'){
		return '电话联系';
	}else if(type == '2'){
		return '会客联系';
	}else if(type == '3'){
		return '客户来电';
	}else if(type == '4'){
		return '短信联系';
	}else if(type == '5'){
		return 'QQ联系';
	}else if(type == '6'){
		return '邮件联系';
	}else{
		return '';
	}
}

function getContractWayImgName(type){
	if(type == '1'){
		return 'phone_dhlx';
	}else if(type == '2'){
		return 'phone_hklx';
	}else if(type == '3'){
		return 'customer_khlx';
	}else if(type == '4'){
		return 'message_dxlx';
	}else if(type == '5'){
		return 'qq_qqlx';
	}else{
		return 'mail_yjlx';
	}
}

function getCallState(callState,timeLength) {
	if (callState == 1) {
		if(timeLength > 0){
			return "已接来电";
		}else{
			return "未接来电";
		}
	} else if (callState == 2) {
		if(timeLength > 0){
			return "已接去电";
		}else{
			return "未接去电";
		}
	} else {
		return "";
	}
}