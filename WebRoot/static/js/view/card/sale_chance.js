function getAjaxCardAction(){
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
		url:ctx+'/cust/card/saleChanceData',
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
							html += createTimeLineHtml(obj, key);
						}
					}
				});
				if (pageData.currentPage < pageData.totalPage) {
					//有多页 此处加载翻页按钮
					html += '<div class="hyx-mca-nonew" onclick="addMoreLog(this)">' +
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
		}	
	});
}

function createTimeLineHtml(datas,key){
	var html = '';
	$.each(datas, function(index, data) {
		html +=
			'<li>' +
			'<div dk=' + key + ' class="timeline_month">' + key.split("-")[2] + '<br/><span>' + number_to_month[key.split("-")[1]] + '</span></div>' +
			'<div class="time_node"></div>' +
			'<div class="right">' +
			'	<div class="arrow"><span>◆</span><em>◆</em></div>' +
			'	<div class="cfu-box">' +
			'		<div class="border-bottom cfu_box_head">' +
			'			<label class="fl_l">创建时间：</label><span class="cfu_time fl_l">' + data.inputDate + '</span>'+
			'			<label class="fl_l">销售机会名称：</label><span class="fl_l">' + data.saleChanceName + '</span>'+
			'  	 </div>'+
			'		<p class="cfu-list-note" >' +
			'			<label class="fl_l">预期签单金额：' + data.theorySignMoney + '</label>' +
			'			<label class="fl_l">预期签单时间：' + data.theorySignDate + '</label>' +
			'			<label class="fl_l">预期成功率：' + getSuccessRateName(data.theorySuccessRate) + '</label>' ;
			html+=
			'		</p>' +
			'	</div>'+
			'</li>';
	});
	return html;
}

//1-0%，2-20%，3-50%，4-70%，5-90%
function getSuccessRateName(i){
	if(i == 1){
		return "0%";
	}else if(i == 2){
		return "20%";
	}else if(i == 3){
		return "50%";
	}else if(i == 4){
		return "70%";
	}else if(i == 5){
		return "90%";
	}else{
		return "";
	}
}

function addMoreLog(obj){
	var currentPage = $("#currentPage").val();
	$("#currentPage").val(parseInt(currentPage) + 1);
	$(obj).remove();
	getAjaxCardAction();
}