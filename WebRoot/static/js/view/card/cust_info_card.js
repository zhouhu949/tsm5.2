$(function(){
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

	  //打电话
    $("a[id^=call_]").live('click',function(){
    	var phone = $(this).attr("id").split("_")[1];
    	var custId = $(this).attr("custId");
    	var custName = $(this).attr("custName");
    	var custType = $(this).attr("custType");
    	var custState = $(this).attr("custState");
    	var define1 = $(this).attr("define1");
    	var lastOptionId = $(this).attr("lastOptionId");
    	var lastOptionName = $(this).attr("lastOptionName");
    	var define3 = $(this).attr("define3");
    	var arrays = new Array();
    	arrays[0] = "\"custId\":\""+custId+"\"";
    	arrays[1] = "\"custName\":\""+custName+"\"";
    	arrays[2] = "\"custType\":\""+custType+"\"";
    	arrays[3] = "\"custState\":\""+custState+"\"";
    	arrays[4] = "\"define1\":\""+define1+"\"";
    	if(lastOptionId != null && lastOptionId != ''){
    		arrays.push("\"saleProcessId\":\""+lastOptionId+"\"");
    		arrays.push("\"saleProcessName\":\""+lastOptionName+"\"");
    	}
    	if(define3 != null && define3 != ''){
    		arrays.push("\"define3\":\""+define3+"\"");
    	}
    	window.top.custCallPhone(phone,arrays,custId);
    });

	$('.demoBtn_a').click(function(){
		var custId = $(this).attr("custId");
		pubDivShowIframe('alert_cust_card_a',ctx+'/cust/card/toReview?custId='+custId,'评论',700,250);
	});

	// $("#operateLog").click(function(){
	// 	var custId = $(this).attr("custId");
	// 	var state = $(this).attr("state");
	// 	var cycleTime = $(this).attr("cycleTime");
	// 	var lifeCode = $(this).attr("lifeCode");
	// 	var rel = ctx+"/cust/card/events?custId="+custId+"&type=7&state="+state+"&cycleTime="+cycleTime+"&lifeCode="+lifeCode;
	// 	$(".current").removeClass("current");
  //       $(".hyx-cc-right iframe").hide()
	// 	if($('#iframepage[src="'+rel+'"]').length>0){
  //           $('#iframepage[src="'+rel+'"]').show()
	// 	}else{
  //           $(".hyx-cc-right").append('<iframe src="'+rel+'" width="100%" height="100%" id="iframepage" name="frameson" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>');
	// 	}
	//
	//
	// });
	//闭包对微聊聊天按钮进行开关控制，避免多次点击请求（不想出现全局变量）
	(function(){
		var $flag=true;
		var str="";
		$("#wechatRecord").click(function(){
			var custId = $(this).attr("custId");
			var state = $(this).attr("state");
			var recordsBox=$(window.frames["frameson"].document).find(".custCard_weChatRecord");
			var getMore=$(window.frames["frameson"].document).find("#wechat_record_getmore");
			$(window.frames["frameson"].document).find(".custCard_button_area").hide();
			$(window.frames["frameson"].document).find(".cust_follow_area").hide();
			$(window.frames["frameson"].document).find(".custCard_timeline").hide();
			recordsBox.show();
			recordsBox.html(str);
			$.ajax({
				url:ctx+"/cust/card/wxRecordJson",//ctx+"/cust/card/wxRecordJson"
				dataType:"json",
				data:{
					"custId":custId,
					"_id":null
				},
				error:function(){
					alert("error!");
				},
				success:function(data){
					if(data.status == "SUCCESS"){
						var datas=data.data;
						if( datas.item.page.currentPage  <  datas.item.page.totalPage ){
							getMore.show();
						}else{
							getMore.hide();
						}
						var html="";
						var lengths=datas.list.length;
						if(lengths == 0){
							html="<div id='lineDiv' class='custCard_timeline_content fl_l' style='margin-top:100px;'><div class='hyx-mcn-bg'><span>暂无记录！</span></div></div>";
							recordsBox.append(html);
							return false;
						}
						var lastTimeStr=recordsBox.find(".record_date").last().text();
						for( var i = 0; i<lengths ; i++){
							if(datas.list[i].dateStr == lastTimeStr){
								html=returnHtml(datas.list[i].sendName,datas.list[i].senderId,datas.list[i].timeStr,datas.list[i].content,datas.list[i]._id);
								recordsBox.find(".weChatRecord_box").last().append(html);
							}else{
								lastTimeStr=datas.list[i].dateStr;
								html='<div class="custCard_weChatRecord_onedayBox">'+
											'<div class="hyx-mca-nonew">'+
											'<span class="line fl_l"></span>'+
											'<label class="fl_l record_date">'+lastTimeStr+'</label>'+
											'<span class="line fl_r"></span>'+
											'</div>'+
											'<ul class="weChatRecord_box">';
								html+=	returnHtml(datas.list[i].sendName,datas.list[i].senderId,datas.list[i].timeStr,datas.list[i].content,datas.list[i]._id);
								html+='</ul>'+
											'</div>';
								recordsBox.append(html);
							}
						}
					}else if(data.status == "ERROR"){
						var html;
						html="<div id='lineDiv' class='custCard_timeline_content fl_l' style='margin-top:100px;'><div class='hyx-mcn-bg'><span>暂无记录！</span></div></div>";
						recordsBox.append(html);
						/*alert(data.errorMsg);*/
						return false;
					}
				}
			});
			//滚轮可申请数据
			recordsBox.scroll(function(){
				var $this=$(this);
				var heights=$this.height() + 5; //补丁（height有时候高度像素不够 无法满足if条件）
				if($this.scrollTop()+heights >= $this[0].scrollHeight){
					var _id=recordsBox.find("li:last").attr("id");
					$.ajax({
						url:ctx+"/cust/card/wxRecordJson",//ctx+"/cust/card/wxRecordJson"
						dataType:"json",
						data:{
							"custId":custId,
							"_id":_id
						},
						error:function(){
							alert("error!");
						},
						success:function(data){
							if(data.status == "SUCCESS"){
								var datas=data.data;
								if( datas.item.page.currentPage  <  datas.item.page.totalPage ){
									getMore.show();
								}else{
									getMore.hide();
								}
								var html="";
								var lengths=datas.list.length;
								var lastTimeStr=recordsBox.find(".record_date").last().text();
								for( var i = 0; i<lengths ; i++){
									if(datas.list[i].dateStr == lastTimeStr){
										html=returnHtml(datas.list[i].sendName,datas.list[i].senderId,datas.list[i].timeStr,datas.list[i].content,datas.list[i]._id);
										recordsBox.find(".weChatRecord_box").last().append(html);
									}else{
										lastTimeStr=datas.list[i].dateStr;
										html='<div class="custCard_weChatRecord_onedayBox">'+
													'<div class="hyx-mca-nonew">'+
													'<span class="line fl_l"></span>'+
													'<label class="fl_l record_date">'+lastTimeStr+'</label>'+
													'<span class="line fl_r"></span>'+
													'</div>'+
													'<ul class="weChatRecord_box">';
										html+=	returnHtml(datas.list[i].sendName,datas.list[i].senderId,datas.list[i].timeStr,datas.list[i].content,datas.list[i]._id);
										html+='</ul>'+
													'</div>';
										recordsBox.append(html);
									}
								}
							}else if(data.status == "ERROR"){
								alert(data.errorMsg);
							}
						}
					});
				}
			});
			//点击加载更多申请数据
			getMore.click(function(){
				if($flag){
					$flag=false;
					var _id=recordsBox.find("li:last").attr("id");
					$.ajax({
						url:ctx+"/cust/card/wxRecordJson",//ctx+"/cust/card/wxRecordJson"
						dataType:"json",
						data:{
							"custId":custId,
							"_id":_id
						},
						error:function(){
							alert("error!");
						},
						success:function(data){
							if(data.status == "SUCCESS"){
								var datas=data.data;
								if( datas.item.page.currentPage  <  datas.item.page.totalPage ){
									getMore.show();
								}else{
									getMore.hide();
								}
								var html="";
								var lengths=datas.list.length;
								var lastTimeStr=recordsBox.find(".record_date").last().text();
								for( var i = 0; i<lengths ; i++){
									if(datas.list[i].dateStr == lastTimeStr){
										html=returnHtml(datas.list[i].sendName,datas.list[i].senderId,datas.list[i].timeStr,datas.list[i].content,datas.list[i]._id);
										recordsBox.find(".weChatRecord_box").last().append(html);
									}else{
										lastTimeStr=datas.list[i].dateStr;
										html='<div class="custCard_weChatRecord_onedayBox">'+
													'<div class="hyx-mca-nonew">'+
													'<span class="line fl_l"></span>'+
													'<label class="fl_l record_date">'+lastTimeStr+'</label>'+
													'<span class="line fl_r"></span>'+
													'</div>'+
													'<ul class="weChatRecord_box">';
										html+=	returnHtml(datas.list[i].sendName,datas.list[i].senderId,datas.list[i].timeStr,datas.list[i].content,datas.list[i]._id);
										html+='</ul>'+
													'</div>';
										recordsBox.append(html);
									}
								}
							}else if(data.status == "ERROR"){
								alert(data.errorMsg);
							}
							$flag=true;
						}
					});
				}
			});
		});
	})();


	//htmldatain封装拼接函数
	function returnHtml(sendName,senderId,timeStr,content,_id){
		var html="";
		html+="<li id="+_id+">"+
					"<span>"+sendName+"</span>";
		/*if(senderId != ""){
				html+="<i>("+senderId+")</i>";
			};*/
		html+="<b>"+timeStr+"</b>"+
					"<p>"+content+"</p>"+
					"</li>";
		return html;
	}

	/*左边、右边铺满整屏*/
//	var winhight=$(window).height();
//	$(".hyx-cc-tabTagBox,.hyx-cc-right").height(winhight-2);

	/*card下拉*/
    $('.icon_down').click(function(){
    	var sty_hid = $(this).prev();
    	if($(this).attr('name') == 'up'){
    		$(this).css({'background':'url('+ctx+'/static/images/down-alert.png) no-repeat'});
    		$(this).attr('name','down');
    		if($(this).hasClass('icon_down') == true){
    			sty_hid.slideUp(50);
    			$(this).parent().animate({'height':'200px'},100);
    		}
    	}else{
    		if($(this).hasClass('icon_down') == true){
    			$(this).parent().css({'height':'auto'});
    			sty_hid.slideDown();
    		}
    		$(this).css({'background':'url('+ctx+'/static/images/up-alert.png) no-repeat'});
    		$(this).attr('name','up');
    	}
    });

	/*左边菜单选中效果*/
	$(".tabTagList").find('li').each(function(i,item){
		$(item).click(function(){
			$(this).siblings('li').removeClass('bor_bot');
			$(this).addClass('current').siblings('li').removeClass('current').find('.arrow').hide();
			$(this).find('.arrow').show();
			$(this).prev().addClass('bor_bot');
			var rel =$(this).attr('rel');
			var rightDivbox=$(".hyx-cc-right");
			rightDivbox.find("iframe").hide();
			if(rightDivbox.find("iframe[src='"+rel+"']").length > 0){
				rightDivbox.find("iframe[src='"+rel+"']").show();
			}else{
				rightDivbox.append('<iframe src="'+rel+'" width="100%" height="100%" id="iframepage" name="frameson" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>');
			}
			//$('#iframepage').attr('src',rel);
		});
	});
	// 通讯录下拉框
	$('.mail').click(function(){
        $(this).find('.drop').fadeToggle();
    });

	//取消关注
	$(".icon-focus-atten").click(function(){
		var custId = $(this).attr("custId");
		$.ajax({
			url:ctx+'/res/tao/setMajor',
			type:'post',
			data:{custId:custId,isMajar:0},
			dataType:'json',
			error:function(){},
			success:function(data){
				if(data == '0'){
					window.top.iDialogMsg("提示","取消关注成功!");
					setTimeout("window.location.reload();",1000);
				}else{
					window.top.iDialogMsg("提示","取消关注失败!");
				}
			}
		});
	});

	//重点关注
	$(".icon-nofocus").click(function(){
		var custId = $(this).attr("custId");
		$.ajax({
			url:ctx+'/res/tao/setMajor',
			type:'post',
			data:{custId:custId,isMajar:1},
			dataType:'json',
			error:function(){},
			success:function(data){
				if(data == '0'){
					window.top.iDialogMsg("提示","重点关注成功!");
					setTimeout("window.location.reload();",1000);
				}else{
					window.top.iDialogMsg("提示","重点关注失败!");
				}
			}
		});
	});

	//修改资源
	$("#editInfo").click(function(){
		var custId = $(this).attr("custId")
		pubDivShowIframe('edit_'+custId,ctx+'/res/cust/toEditRes?custId='+custId,'信息编辑',500,570);
	});
});
