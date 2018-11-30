function getAjaxCardContractOrder(){
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
		url:ctx+'/cust/card/orderData',
		type:'post',
		data:JSON.stringify(dataObj),
		contentType:"application/json",
		dataType:'json',
		error:function(){},
		success:function(data){
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
							html += createContractOrderLineHtml(obj, key);
						}
					}
				});
				if (pageData.currentPage < pageData.totalPage) {
					//有多页 此处加载翻页按钮
					html += '<div class="hyx-mca-nonew" onclick="addMoreContractOrder(this)">' +
						'<span class="line fl_l"></span> <label class="fl_l">加载更多</label>' +
						'<span class="line fl_r"></span>' +
						'</div>';
				}else{
					html += '<div class="hyx-mca-nonew">' +
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
			$(".custCard_timeline_content .right").each(function(){
				var $this = $(this);
				var p = $this.find(".cfu-list-note");
				var cfu_list_note_label = p.find("label");
				var cfu_list_note_span = p.find("span");
				var cfu_list_note_hetong = p.find(".shows-hetong");
				cfu_list_note_span.width(p.width() - cfu_list_note_label.width() - 20);
				cfu_list_note_hetong.width(p.width()/5 - cfu_list_note_label.width()-10);
				var p_height = p.height();
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
				}
			});
		}	
	});
}

function createContractOrderLineHtml(datas,key){
	var html = '';
	$.each(datas, function(index, data) {
		if (index == 0 && $("div[dk=" + key + "]").length == 0) {
			html +=
				'<li>' +
					'<div dk=' + key + ' class="timeline_month">' + key.split("-")[2] + '<br/><span>' + number_to_month[key.split("-")[1]] + '</span></div>' +
					'<div class="time_node"></div>' +
					'<div class="right">' +
						'<div class="arrow"><span>◆</span><em>◆</em></div>'+
						'<div class="cfu-box">' +
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
							'<span class="fl_l shows-hetong" title='+ data.userId+'>' + data.userId + '</span>' +
							'</p>' +
						'</div>';
						'<i class="icon_down_arrow"></i>' +
					'</div>' +
				'</li>';
		}else{
			html +=
				'<li>' +
					'<div class="time_node"></div>' +
					'<div class="right">' +
						'<div class="arrow"><span>◆</span><em>◆</em></div>'+
						'<div class="cfu-box">' +
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
							'<span class="fl_l shows-hetong" title='+ data.userId+'>' + data.userId + '</span>' +
							'</p>' +
						'</div>';
						'<i class="icon_down_arrow"></i>' +
					'</div>' +
				'</li>';
		}
	});
	return html;
}

function addMoreContractOrder(obj){
	var currentPage = $("#currentPage").val();
	$("#currentPage").val(parseInt(currentPage) + 1);
	$(obj).remove();
	getAjaxCardContract();
}