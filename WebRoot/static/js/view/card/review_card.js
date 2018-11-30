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
	//追溯历史
	$("#view_history").live("click",function(){
		$("#showCount").val(10);
		$("#totalResult").val("");
		$("#currentPage").val("");
		$(this).remove();
		getHistroyAjaxCardAction();
	});
	
	$("#his_more").live("click",function(){
		var currentPage = $("#currentPage").val();
		$("#currentPage").val(parseInt(currentPage)+1);
		$(this).remove();
		getHistroyAjaxCardAction();
	});
	getAjaxCardAction();
	
	//查看订单
	$("a[id^=orderView_]").live("click",function(){
		var id = $(this).attr("id").split("_")[1];
		window.parent.pubDivShowIframe('view_order',ctx+'/contract/order/orderView?orderId='+id,'订单详情',885,610);
	});
	
	//查看合同
	$("a[id^=contractView_]").live("click",function(){
		var id = $(this).attr("id").split("_")[1];
		window.top.addTab(ctx+'/contract/toView?contractId='+id,'查看合同');
	});
	
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
	var type = $("#type").val();
	var custId = $("#custId").val();
	var userId = $("#userId").val();
	var lifeCode = $("#lifeCode").val();
	var dataObj = new Object();
	dataObj.type = type;
	dataObj.custId = custId;
	//dataObj.userId = userId;
	dataObj.lifeCode = lifeCode;
	var showCount = $("#showCount").val();
	var totalResult = $("#totalResult").val();
	var currentPage = $("#currentPage").val();
	var page = new Object();
	page.showCount = showCount;
	page.totalResult = totalResult;
	page.currentPage = currentPage;
	dataObj.page = page;
	$.ajax({
		url:ctx+'/cust/card/data',
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
				$.each(timeLineData,function(index,tld){
					for(key in tld){
						var obj = tld[key];
						var tl = key+"_curr";
						if($(".timeline_all").length > 0 && $(".timeline_all:last").attr("tl") == tl){
							var json_data = reloadLast($(".timeline_all:last"))+getTimeCard(obj,'',dataObj.type);
							if(!(dataObj.type == '5' || dataObj.type == '6')){
								json_data="<div tl="+tl+" class='timeline_all'>"
										+	"<div class='container'>"
										+		"<div class='timeline'>"
										+			"<div class='plus'>"+getCustTypeName(key)+"</div>"
										+			"<div class='plus2'></div>"
										+		"</div>"
										+	"</div>"
										+json_data
										+"</div>";
							}else{
								json_data="<div tl="+tl+" class='timeline_all'>"
										+	"<div class='container'>"
										+		"<div class='timeline'>"
										+			"<div class='plus2'></div>"
										+		"</div>"
										+	"</div>"
										+json_data
										+"</div>";
								
							}
							$(".timeline_all:last").remove();
							$('.hyx-cca-bot').append(json_data);
						}else{
							var json_data = "<div tl="+tl+" class='timeline_all'>";
							if(!(dataObj.type == '5' || dataObj.type == '6')){
								json_data+= "<div class='container'>"
										+		"<div class='timeline'>"
										+			"<div class='plus'>"+getCustTypeName(key)+"</div>"
										+			"<div class='plus2'></div>"
										+		"</div>"
										+	"</div>";
							}else{
								 json_data += "<div class='container'>"
											+		"<div class='timeline'>"
											+			"<div class='plus2'></div>"
											+		"</div>"
											+	"</div>";
							}
							json_data=getTimeCard(obj,json_data,dataObj.type);
							json_data+= "</div>";
							$('.hyx-cca-bot').append(json_data);
						}
					}
				});
				/*时间轴显示*/
				$('.timeline_all').masonry({itemSelector : '.item'});
				Arrow_Points();
				list_more();
				list_over();
				item_click();
				if(pageData.currentPage < pageData.totalPage){
					var morePage = '<div id="cur_more" class="hyx-mca-oldbtn" style="margin-bottom:20px;">'
						+	'<label>查看更多</label>'
						+	'<span>&or;</span>'
						+'</div>';
					$('.hyx-cca-bot').append(morePage);
				}else{
					if(data.history > 0){
						var morePage = '<div id="view_history" class="hyx-mca-oldbtn" style="margin-bottom:20px;">'
							+	'<label>追溯历史</label>'
							+	'<span>&or;</span>'
							+'</div>';
						$('.hyx-cca-bot').append(morePage);
					}
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
				if(data.history > 0){
					var morePage = '<label class="hyx-mcn-bg"><span>暂无记录！</span></label>'
						+'<div id="view_history" class="hyx-mca-oldbtn hyx-cca-old" style="margin-bottom:20px;">'
						+   '<span class="line_a fl_l"></span>'
						+	'<label>追溯历史</label>'
						+   '<span class="line_b fl_r"></span>'
						+	'<span>&or;</span>'
						+'</div>';
					$('.hyx-cca-bot').append(morePage);
				}else{
					var morePage = '<label class="hyx-mcn-bg"><span>暂无记录！</span></label>';
					$('.hyx-cca-bot').append(morePage);
				}
			}
		}
	});
}

//追溯历史
function getHistroyAjaxCardAction(){
	var type = $("#type").val();
	var custId = $("#custId").val();
	//var userId = $("#userId").val();
	var lifeCode = $("#lifeCode").val();
	var dataObj = new Object();
	dataObj.type = type;
	dataObj.custId = custId;
//	dataObj.userId = userId;
	dataObj.lifeCode = lifeCode;
	var showCount = $("#showCount").val();
	var totalResult = $("#totalResult").val();
	var currentPage = $("#currentPage").val();
	var page = new Object();
	page.showCount = showCount;
	page.totalResult = totalResult;
	page.currentPage = currentPage;
	dataObj.page = page;
	$.ajax({
		url:ctx+'/cust/card/historyData',
		type:'post',
		data:JSON.stringify(dataObj),
		contentType:"application/json",
		dataType:'json',
		error:function(){},
		success:function(data){
			//去掉显示空的图片
			if($(".hyx-mcn-bg").length > 0){
				$(".hyx-mcn-bg").remove();
			}
			var timeLineData = data.datas;
			var pageData = data.page;
			$("#showCount").val(pageData.showCount);
			$("#totalResult").val(pageData.totalResult);
			$("#currentPage").val(pageData.currentPage);
			if(!$.isEmptyObject(timeLineData)){
				$.each(timeLineData,function(index,tld){
					for(key in tld){
						var obj = tld[key];
						var tl = key+"_hist";
						if($(".timeline_all").length > 0 && $(".timeline_all:last").attr("tl") == tl){
							var json_data = reloadLast($(".timeline_all:last"))+getTimeCard(obj,'',dataObj.type);
							if(!(dataObj.type == '5' || dataObj.type == '6')){
								json_data="<div tl="+tl+" class='timeline_all'>"
										+	"<div class='container'>"
										+		"<div class='timeline'>"
										+			"<div class='plus'>"+getCustTypeName(key)+"</div>"
										+			"<div class='plus2'></div>"
										+		"</div>"
										+	"</div>"
										+json_data
										+"</div>";
							}else{
								json_data="<div tl="+tl+" class='timeline_all'>"
										+	"<div class='container'>"
										+		"<div class='timeline'>"
										+			"<div class='plus2'></div>"
										+		"</div>"
										+	"</div>"
										+json_data
										+"</div>";
								
							}
							$(".timeline_all:last").remove();
							$('.hyx-cca-bot').append(json_data);
						}else{
							var json_data = "<div tl="+tl+" class='timeline_all'>";
							if(!(dataObj.type == '5' || dataObj.type == '6')){
								json_data+= "<div class='container'>"
										+		"<div class='timeline'>"
										+			"<div class='plus'>"+getCustTypeName(key)+"</div>"
										+			"<div class='plus2'></div>"
										+		"</div>"
										+	"</div>";
							}else{
								 json_data += "<div class='container'>"
											+		"<div class='timeline'>"
											+			"<div class='plus2'></div>"
											+		"</div>"
											+	"</div>";
							}
							json_data=getTimeCard(obj,json_data,dataObj.type);
							json_data+= "</div>";
							$('.hyx-cca-bot').append(json_data);
						}
					}
				});
				/*时间轴显示*/
				$('.timeline_all').masonry({itemSelector : '.item'});
				Arrow_Points();
				list_more();
				list_over();
				item_click();
				if(pageData.currentPage < pageData.totalPage){
					var morePage = '<div id="his_more" class="hyx-mca-oldbtn" style="margin-bottom:20px;">'
						+	'<label>查看更多历史</label>'
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
				
			}
		}
	});
}

function reloadLast(obj){
	var items = $(obj).find(".item");
	var html='';
	$.each(items,function(index,item){
		$(item).find(".rightcorner").remove();
		$(item).find(".leftcorner").remove();
		html+=	"<div class='item'>"
				+$(item).html()
				+"</div>"
	});
	return html;
}

function getTimeCard(obj,json_data,type){
	$.each(obj,function(idx,o){
		//解析
		if(type == '1'){
			json_data+=
				"<div class='item'>"
					+"<p class='time'>"
						+"<label class='left fl_l'>"+o.inputDate+"</label>";
			if(o.records != null){
				$.each(o.records,function(i,record){
					if(record.recordState == 1){
						json_data+="<label class='right fl_r'><i rc='voice' callNum='"+getCallNum(record)+"' custName='"+record.custName+"' url='"+record.recordUrl+"&d="+record.recordKey+"&id="+record.code+"&compid="+record.orgId+"&calllen="+record.timeLength+"' callId='"+record.id+"' timeLength='"+record.timeLength+"'></i><span>"+getHoursTime(record.timeLength)+"</span></label>";
					}
				})
			}
			
			if(o.saleProcessName == null || o.saleProcessName == ''){
				json_data+="</p>"
					+"<p class='list'><label class='lab'>客户联系人：</label><span class='sp'>"+o.mainLinkman+"</span></p>"
					+"<p class='list'><label class='lab'>联系人：</label><span class='sp'>"+o.userName+"</span></p>"
					+"<p class='list'><label class='lab'>下次联系时间：</label><span class='sp'>"+(o.nextConcatTime == null ? '' : o.nextConcatTime)+"</span></p>"
					+"<div class='list list_more'>"
						+"<label class='lab'>资源备注：</label>"
						+"<span class='sp'  style='height:50px;overflow:hidden;'>"+o.remark+"</span>"
						+"<div class='drop'>"
							+"<label class='arrow'><em>◆</em><span>◆</span></label>"
							+"<div class='box'>资源备注："+o.remark+"</div>"
						+"</div>"
					+"</div>"
				+"</div>";
			}else{
				var labelsStr = o.labels == null ? '' : o.labels.replace(/#/g,'，');
				if(labelsStr != ''){
					labelsStr = labelsStr.substring(0,labelsStr.length -1);
				}
				json_data+="</p>"
						+"<p class='list'><label class='lab'>客户联系人：</label><span class='sp'>"+o.mainLinkman+"</span></p>"
						+"<p class='list'><label class='lab'>联系人：</label><span class='sp'>"+o.userName+"</span></p>"
						+"<p class='list'><label class='lab'>联系电话：</label><span phone='tel' class='sp'>"+o.telphone+"</span></p>"
						+"<p class='list'><label class='lab'>联系方式：</label><span class='sp'>"+getContractWay(o.type)+"</span></p>"
						+"<p class='list'><label class='lab'>销售进程：</label><span class='sp'>"+o.saleProcessName+"</span></p>"
						+"<p class='list'><label class='lab'>行动标签：</label><span class='sp'>"+labelsStr+"</span></p>"
						+"<div class='list list_more'>"
							+"<label class='lab'>联系小记：</label>"
							+"<span class='sp' style='height:50px;overflow:hidden;'>"+o.remark+"</span>"
							+"<div class='drop'>"
								+"<label class='arrow'><em>◆</em><span>◆</span></label>"
								+"<div class='box'>联系小记："+o.remark+"</div>"
							+"</div>"
						+"</div>"
					+"</div>";
			}
		}else if(type == '2'){
			json_data+=
				"<div class='item'>"
					+"<p class='time'>"
						+"<label class='left fl_l'>"+o.inputDate+"</label>"
					+"</p>"
					+"<p class='list'><label class='lab'>回访时间：</label><span class='sp'>"+o.visitDate+"</span></p>"
					+"<p class='list'><label class='lab'>回访人：</label><span class='sp'>"+o.userName+"</span></p>"
					+"<p class='list'><label class='lab'>回访方式：</label><span class='sp'>"+o.type+"</span></p>"
					+"<p class='list'><label class='lab'>服务标签：</label><span class='sp'>"+o.labels+"</span></p>"
					+"<div class='list list_more'>"
						+"<label class='lab'>沟通内容：</label>"
						+"<span class='sp' style='height:50px;overflow:hidden;'>"+o.remark+"</span>"
						+"<div class='drop'>"
							+"<label class='arrow'><em>◆</em><span>◆</span></label>"
							+"<div class='box'>沟通内容："+o.remark+"</div>"
						+"</div>"
					+"</div>"
				+"</div>";
		}else if(type == '4'){
			json_data+=
				"<div class='item'>"
					+"<p class='time'>"
						+"<label class='left fl_l'>"+o.reviewDate+"</label>"
					+"</p>"
					+"<p class='list'><label class='lab'>点评时间：</label><span class='sp'>"+o.reviewDate+"</span></p>"
					+"<p class='list'><label class='lab'>点评人：</label><span class='sp'>"+o.reviewName+"</span></p>"
					+"<div class='list list_more'>"
						+"<label class='lab'>点评内容：</label>"
						+"<span class='sp'  style='height:50px;overflow:hidden;'>"+o.revComment+"</span>"
						+"<div class='drop'>"
							+"<label class='arrow'><em>◆</em><span>◆</span></label>"
							+"<div class='box'>点评内容："+o.revComment+"</div>"
						+"</div>"
					+"</div>"
				+"</div>";
		}else if(type == '5'){
			json_data+=
				"<div class=\"item\">"
				+"	<p class=\"time\">"
				+"		<label class=\"left fl_l\">"+o.inputtime+"</label>"
				+"	</p>"
				+"	<p class=\"list\"><label class=\"lab\">客户联系人：</label><span class=\"sp\">"+o.custName+"</span></p>"
				+"	<p class=\"list\"><label class=\"lab\">合同编号：</label><span class=\"sp\">"+o.code+"</span></p>"
				+"	<p class=\"list\"><label class=\"lab\">合同名称：</label><span class=\"sp\">"+o.contractName+"</span></p>"
				+"	<p class=\"list\"><label class=\"lab\">签约日期：</label><span class=\"sp\">"+o.signDate+"</span></p>"
				+"	<p class=\"list\"><label class=\"lab\">失效日期：</label><span class=\"sp\">"+o.invalidDate+"</span></p>"
				+"	<p class=\"list\"><label class=\"lab\">签约人：</label><span class=\"sp\">"+o.signUsername+"</span></p>"
				+"	<div class=\"list list_over hyx-cce-over\">"
				+"		<a href=\"###\" id=\"contractView_"+o.id+"\" class=\"link fl_r\">合同查看&gt;&gt;</a>"
				+"	</div>"
				+"</div>";
		}else if(type == '6'){
			json_data+='<div class="item">'
						+'<p class="time">'
						+'	<label class="left fl_l">'+o.inputtime+'</label>'
						+'</p>'
//						+'<p class="list"><label class="lab">客户联系人：</label><span class="sp">张三2</span></p>'
						+'<p class="list"><label class="lab">合同名称：</label><span class="sp">'+o.contractName+'</span></p>'
						+'<p class="list"><label class="lab">订单编号：</label><span class="sp">'+o.code+'</span></p>'
						+'<p class="list"><label class="lab">订单总额：</label><span class="sp">'+o.money+'元</span></p>'
						+'<p class="list"><label class="lab">提交人：</label><span class="sp">'+o.userName+'</span></p>'
						+'<div class="list list_over">'
						+'	<a href="###" id="orderView_'+o.id+'" class="link fl_r">产品配置&gt;&gt;</a>'
						+'</div>'
					+'</div>'
		}
	});
	return json_data;
}

function getCustTypeName(state){
	if(state == '1'){
		return '资源';
	}else if(state == '2'){
		return '意向';
	}else if(state == '3'){
		return '签约';
	}else if(state == '4'){
		return '沉默';
	}else if(state == '5'){
		return '流失';
	}
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
	}else{
		return '邮件联系';
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

//文字过多时的省略号
function list_more(){
	$('.hyx-cca-bot').find('.list_more').each(function(){
    	if($(this).find('.sp').text().length >= 45){
	    	$(this).find('.sp').text($(this).find('.sp').text().substr(0,45) + '...');
	    }
    });
}
//鼠标放上去显示
function list_over(){
	$('.hyx-cca-bot').find('.list_more').each(function(i,item){
    	$(item).mouseover(function(){
    		$(this).find('.drop').show();
    	});
    	$(item).mouseleave(function(){
    		$(this).find('.drop').hide();
    	});
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

//window.onload=function(){
//	var height = $(".hyx-cca").height()+30;
//	window.parent.$("#iframepage").css({"height":height+"px"});
//};