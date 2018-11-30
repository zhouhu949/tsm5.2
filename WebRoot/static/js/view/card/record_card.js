$(function(){
	/*总的高度铺满整屏*/
	var winhight=$(window).height(); 
	$(".hyx-cca").height(winhight-2);
	/*时间轴整屏*/
	$(".hyx-cca-bot").height(winhight-80);
	$("#cur_more").live("click",function(){
		var currentPage = $("#currentPage").val();
		$("#currentPage").val(parseInt(currentPage)+1);
		$(this).remove();
		getAjaxCardAction();
	})
	getAjaxCardAction();
	//通话录音播放
	$('i[rc=voice]').live('click',function(){
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
});

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
		url:ctx+'/cust/card/callRecord',
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
			if(!$.isEmptyObject(timeLineData)){
				var json_data="<div class='timeline_all'>"
						    +	"<div class='container'>"
							+		"<div class='timeline'>"
							+			"<div class='plus2'></div>"
							+		"</div>"
							+	"</div>";
				$.each(timeLineData,function(index,tld){
					json_data+="<div class='item'>"
									+"<p class='time'>"
									+"<label class='left fl_l'>"+tld.startTime+"</label>";
							if(tld.recordState == 1){
								json_data+="<label class='right fl_r'><i rc='voice' callNum='"+getCallNum(tld)+"' custName='"+tld.custName+"' url='"+tld.recordUrl+"&d="+tld.recordKey+"&id="+tld.code+"&compid="+tld.orgId+"&calllen="+tld.timeLength+"' callId='"+tld.id+"' timeLength='"+tld.timeLength+"'></i><span>"+getHoursTime(tld.timeLength)+"</span></label>";
							}
									
							json_data+="</p>"
								+"<p class='list'><label class='lab'>呼叫类型：</label><span class='sp'>"+getCallState(tld.callState)+"</span></p>"
								+"<p class='list'><label class='lab'>主叫号码：</label><span phone='tel' class='sp'>"+tld.callerNum+"</span></p>"
								+"<p class='list'><label class='lab'>被叫号码：</label><span phone='tel' class='sp'>"+tld.calledNum+"</span></p>"
								+"<p class='list'><label class='lab'>通话时间：</label><span class='sp'>"+tld.startTime+"</span></p>"
								+"<p class='list'><label class='lab'>通话时长：</label><span class='sp'>"+getHoursTime(tld.timeLength)+"</span></p>"
								+"<p class='list'><label class='lab'>联系人：</label><span class='sp'>"+(tld.define1 == null ? tld.inputAcc : tld.define1)+"</span></p>"
							+"</div>";
				});
				json_data+="</div>";
				$('.hyx-cca-bot').append(json_data);
				$('.timeline_all').masonry({itemSelector : '.item'});
				Arrow_Points();
				item_click();
				if(pageData.currentPage < pageData.totalPage){
					var morePage = '<div id="cur_more" class="hyx-mca-oldbtn" style="margin-bottom:20px;">'
						+	'<label>查看更多</label>'
						+	'<span>&or;</span>'
						+'</div>';
					$('.hyx-cca-bot').append(morePage);
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
			}else{
				var morePage = '<label class="hyx-mcn-bg"><span>暂无记录！</span></label>';
				$('.hyx-cca-bot').append(morePage);
			}
		}
	});
}

function getCallState(callState){
	if(callState == 1){
		return "呼入";
	}else if(callState == 2){
		return "呼出";
	}else{
		return "";
	}
}

function Arrow_Points(){
	var s = $(".timeline_all").find(".item");
	$.each(s,function(i,obj){
		var posLeft = $(obj).css("left");
		if(posLeft == "0px"){
			if($(obj).find('.rightcorner').length == 0){
				html = "<span class='rightcorner'><label class='arrow'><em>◆</em><span>◆</span></label><label class='cir'></label></span>";
				$(obj).prepend(html);
			}
		} else {
			if($(obj).find('.leftcorner').length == 0){
				html = "<span class='leftcorner'><label class='arrow'><em>◆</em><span>◆</span></label><label class='cir'></label></span>";
				$(obj).prepend(html);
			}
		}
	});
}

//节点点击
function item_click(){
	$(".hyx-cca-bot").find(".item").click(function(){
    	$(this).parents('.hyx-cca-bot').find('.item').removeClass('item_click');
    	$(this).addClass('item_click');
    });
}

//将秒转换为小时
function getHoursTime(second) {
	second = Math.ceil(second);//向上取整
	var h = 0;
	var d = 0;
	var s = 0;
	var str = "";
	var temp = parseInt(second % 3600);//舍去小数
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
	if(s==0&&h==0&&d==0){
		str="0秒";
	}
	return str;

}

function getCallNum(tld){
	if(tld.callState == "1"){
		return tld.callerNum;
	}else{
		return tld.calledNum;
	}
}