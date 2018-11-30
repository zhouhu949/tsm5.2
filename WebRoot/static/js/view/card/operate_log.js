function getAjaxCardAction(){
	var custId = $("#custId").val();
	var pageObj = new Object();
    var pageStr = $("#currentPage").data("page-str") || "{}";
    if(pageStr!="{}"){
    	pageObj=JSON.parse(pageStr);
        pageObj.operation="3";
	}
	$.ajax({
		url:ctx+'/cust/card/logData?custId='+custId,
		type:'post',
		data:JSON.stringify(pageObj),
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
                    $("#currentPage").data("page-str",JSON.stringify(pageData))
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
							html += createLogLineHtml(obj, key);
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

function createLogLineHtml(datas,key){
	var html = '';
	$.each(datas, function(index, data) {
		if (index == 0 && $("div[dk=" + key + "]").length == 0) {
			html +=
				'<li>' +
				'<div dk=' + key + ' class="timeline_month">' + key.split("-")[2] + '<br/><span>' + number_to_month[key.split("-")[1]] + '</span></div>' +
				'<div class="time_node"></div>' +
				'<div class="right">' +
				'	<div class="arrow"><span>◆</span><em>◆</em></div>' +
				'	<div class="cfu-box">' +
				'		<div class="border-bottom cfu_box_head">' +
				'			<label class="fl_l">操作时间：</label><span class="cfu_time fl_l">' + data.inputDate + '</span>'+
				'  	 </div>'+
				'		<p class="cfu-list-note" >' +
				'			<label class="fl_l">操作类型：' + data.resOperateName + '</label>' +
				'			<label class="fl_l">操作人：' + (data.userName == null || data.userName == 'system' ? '系统' : data.userName) + '</label>' ;
				if(data.context != null && data.context != ''){
					html+= '<label class="fl_l">说明：' + data.context + '</label>';
				}
				html+=
				'		</p>' +
				'	</div>';
		}else{
			html +=
				'<li>' +
				'<div class="time_node"></div>' +
				'<div class="right">' +
				'<div class="arrow"><span>◆</span><em>◆</em></div>' +
				'<div class="cfu-box">' +
				'	<div class="border-bottom cfu_box_head">' +
				'		<label class="fl_l">操作时间：</label><span class="cfu_time fl_l">' + data.inputDate + '</span>'+
				'	</div>' +
				'	<p class="cfu-list-note" >' +
				'			<label class="fl_l">操作类型：' + data.resOperateName + '</label>' +
				'			<label class="fl_l">操作人：' + (data.userName == null ? '系统' : data.userName) + '</label>' ;
				if(data.context != null && data.context != ''){
					html+= '<label class="fl_l">说明：' + data.context + '</label>';
				}
				html+=
				'		</p>' +
				'	</div>';
		}
	});
	return html;
}

function addMoreLog(obj){
	var currentPage = $("#currentPage").val();
	$("#currentPage").val(parseInt(currentPage) + 1);
	$(obj).remove();
	getAjaxCardAction();
}