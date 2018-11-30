function searchingDataTip(){
	var table = $(".ajax-table");
	var tip_search_data = $(".tip_search_data");
	var tip_search_text = tip_search_data.find(".tip_search_text");
	var tip_search_error = tip_search_data.find(".tip_search_error");
	var table_height = table.height();
	var margin_top = (table_height - tip_search_text.height())/2;
	tip_search_data.height(table_height);
	tip_search_text.css("margin-top",margin_top);
	tip_search_error.css("margin-top",margin_top);
	tip_search_data.show();
	tip_search_error.hide();
	tip_search_text.show();
}

function loadData(page){
    var _param = $("form").serialize();
    loaderAjax(_param,$("[name='showCount']").length>0? "page.showCount="+ $("[name='showCount']").val(): "");
}

function refreshPageData(page){
    var table = $(".ajax-table");
    var _param=table.attr("data-param")
    var pageStr="&";
    if(page){
        pageStr = "&page.currentPage="+page.currentPage+"&page.totalResult="+page.totalResult+"&page.showCount="+page.showCount;
    }
    loaderAjax(_param,pageStr);
}

function loaderAjax(_param,pageStr){
    var table = $(".ajax-table");
    table.attr("data-param",_param);
    var _url=ctx+"/callrecord/soundListJson";
	//var _param = $("form").serialize();
	searchingDataTip();
	var timeout = setTimeout(function(){
		$(".tip_search_text").hide();
		$(".tip_search_error").text("网络繁忙，请耐心等候或重新查询！");
		$(".tip_search_error").show();
	},10000);
	$.ajax({
		type:"get",
		url:_url,
        data:_param+"&"+pageStr,
		error:function(){
			$(".tip_search_text").hide();
			$(".tip_search_error").text("网络异常，请重新查询！");
			$(".tip_search_error").show();
		},
		success:function(data){
			if(data.status=="success"){
				loadTable(data.list);
				$(".tip_search_data").hide();
				clearTimeout(timeout);
				pa.load("new_page",data.item.page,refreshPageData,$(".ajax-table"));
			}else{
				clearTimeout(timeout);
				$(".tip_search_text").hide();
				$(".tip_search_error").text("网络异常，请重新查询！");
				$(".tip_search_error").show();
			}	
		}
	});
}


function loadTable(list){
	var html='';
	for(var i in list){
		var call = list[i];
		html +=
		"<tr class='"+(i%2==0?'':'sty-bgcolor-b')+"'>"+
		"<td style='width:160px;'><div class='overflow_hidden w160 link'>";
		if(call.recordState == 1 && call.recordUrl != null && call.timeLength> 0 && call.recordUrl.indexOf('http:') != -1){
				html+="<a href='javascript:void(0);' class='icon-play' name='icon-play'  callState='"+call.callState+"' timeLength='"+call.timeLength+"' url='"+call.recordUrl+"' callId='"+call.id+"' custName='"+emptyTransfer(call.custName)+"' callerNum='"+call.callerNum+"'  calledNum='"+call.calledNum+"' title='录音播放'></a>";	
	    }else if(call.recordState == 1 && call.recordUrl != null && call.timeLength> 0){
		    	html+="<a href='javascript:void(0);' class='icon-play' name='icon-play'  callState='"+call.callState+"' timeLength='"+call.timeLength+"' url='"+$("#playUrl").val()+call.recordUrl+"&d="+call.recordKey+"&id="+call.code+"&compid="+call.orgId+"&calllen="+call.timeLength+"' callId='"+call.id+"' custName='"+emptyTransfer(call.custName)+"' callerNum='"+call.callerNum+"'  calledNum='"+call.calledNum+"' title='录音播放'></a>";
	    }else{
	    		html+= "<a href='###' class='icon-play img-gray' title='录音播放'></a>";	    	
	    }
		html+="</div></td>";
		html+="	<td><div class='overflow_hidden w70'>";
			if(call.custId == null || call.custId == ""){
				html+= "访客";
			}else if(call.custState == 4){
				html+= "公海客户";
			}else if(call.custType == 1){
				html+= "资源";
			}else if(call.custState == 3 || call.custState == 2){
				html+= "意向客户";
			}else if(call.custState == 6){
				html+= "签约客户";
			}else if(call.custState == 7){
				html+= "沉默客户";
			}else if(call.custState == 8){
				html+= "流失客户";
			}
			html+=" </div></td>"+
			"	<td><div class='overflow_hidden w120' title='"+call.callerNum+"' name='callerNum_'>"+call.callerNum+"</div></td>"+
			"	<td><div class='overflow_hidden w120' title='"+call.calledNum+"' name='calledNum_'>"+call.calledNum+"</div></td>";
			html+="	<td><div class='overflow_hidden w120' title='"+call.saleProcessName +"'>";
			 if(call.saleProcessName != null){
				 html+= call.saleProcessName;
			 }else{
				 html+= "无";
			 }
			 html+=" </div></td>";
			 html+=
					"	<td><div class='overflow_hidden w120' title='"+call.define15 +"'>"+call.define15 +"</div></td>"+
					"	<td><div class='overflow_hidden w120' title='"+call.timeLength +"秒'>"+call.define16 +"</div></td>";
		  html+="<td><div class='overflow_hidden w70'>";
		  if(call.callState == 1){
			  if(call.timeLength > 0){
				  html+= "<div class='overflow_hidden w40px'><span href='###' class='icon-incom-call'  title='已接来电'></span></div>";
			  }else{
				  html+= "<div class='overflow_hidden w40px'><span href='###' class='icon-missed-call'  title='未接来电'></span></div>";
			  }
		  }else if(call.callState == 2){
			  if(call.timeLength > 0){
				  html+= "<div class='overflow_hidden w40px'><span href='###' class='icon-out-call' title='已接去电'></span></div>";
			  }else{
				  html+= "<div class='overflow_hidden w40px'><span href='###' class='icon-not-connec' title='未接去电'></span></div>";
			  }
		  }					
		  html+="	<td><div class='overflow_hidden w100' title='"+call.inputName+"'>"+call.inputName+"</div></td>"+
		  "</tr>";
	}
	$(".ajax-table>tbody").html(html);
	/* 屏蔽手机或固话中间几位  */
    var idReplaceWord = $("#idReplaceWord").val();	
	if(idReplaceWord==1){
	    $("div[name=callerNum_]").each(function(){
	    		   var str = $(this).text();
	    		   replaceWord(str,$(this));
	    		   replaceTitleWord(str,$(this));
	    		  });
	    $("div[name=calledNum_]").each(function(){
	    		   var str = $(this).text();
	    		   replaceWord(str,$(this));
	    		   replaceTitleWord(str,$(this));
	    		  });    
	}
	/* 屏蔽手机或固话中间几位  */
}

$(function(){
	isGroupType = $("#groupType").val();
	loadData();
/*	$("#query").click(function(){
		loadData();
	});*/
	
	/*表单优化*/
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
    
       /* 通话时长 */
    $('.tag').find('input').click(function(){
        $(this).next('.hid').hide();
        $(this).blur(function(){
            if($(this).val() == ''){
                $(this).next('.hid').show();
            }
         }); 
    });
    $('.tag').find('.hid').click(function(){
    	var ipt_a = $(this).prev('input');
        ipt_a.focus();
        $(this).hide();
        ipt_a.blur(function(){
            if(ipt_a.val() == ''){
                $(this).show();
            }
         }); 
    });
    $('.tag').find('input').each(function(i,item){
		if($(item).val() != ''){
    		$(item).next('.hid').hide();
 	   }    	
    });
	

	// 通话日期
    $('#d1').dateRangePicker({
    	showShortcuts:false,
    	startDate:moment().subtract(3,"months").format("YYYY-MM-DD"),
        endDate:getNowFormatDate(),
        format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
    	var s_t = '';
    	var e_t = '';
    	if(obj.date1 != 'Invalid Date'){
    		$('#startDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    		s_t = moment(obj.date1).format('YY.MM.DD');
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#endDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    		e_t = moment(obj.date2).format('YY.MM.DD');
    	}
    	var s = $(this).parents('.select');
    	var dt=s.children("dt");
        var dd=s.children("dd");
        dt.html(s_t+'/'+e_t);
        $("#dDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
        //s.css("z-index",z);
    });
    
	//播放按钮点击的时候
	$("table").on('click',"[name='icon-play']", function(){
		var v = $(this).attr("v");
		var callState = $(this).attr("callState");//呼叫类型
		// 获取当前行的客户姓名
		var callName = $(this).attr("custName");
		var callNum = "";
		// 如果单位姓名为空就获取号码
		if(callState == "1"){ //1:呼入，2:呼出
				callNum = $(this).attr("callerNum");// 主叫号码
		}else{
				callNum = $(this).attr("calledNum");// 被叫号码
		}				
		var httpUrl = $("#project_path").val();
		var plength = $(this).attr("timeLength");
		var url = $(this).attr("url");
		//var url = "http://record.qftx.cn:8070/ds?v=1&p=73e053c3e2e7bf5b81ed719357f43756160304mp34a86c50e58e934b3914";
		var callId = $(this).attr("callId");
		//window.location.href = httpUrl+"/call/callPlay.do?timeElngth="+plength+"&recordUrl="+encodeURIComponent(encodeURIComponent(url,"utf-8"))+"&callId="+callId+"&callName="+encodeURI(encodeURI(callName))+"&callNum="+callNum;
		recordCallPlay(httpUrl,plength,url,callId,callName,callNum);
	});
	

	    /*所有者*/	    
	    var url = ctx+"/orgGroup/get_all_sale_user_json";
	    if(isGroupType == 2){ // 客服
	    	url = ctx+"/orgGroup/get_all_service_user_json";
	    }
    	$("input[name=searchOwnerType]").iCheck({
 	    	radioClass: 'iradio_minimal-green'
 		 });
	    $("input[name=searchOwnerType]").on('ifClicked',function(){
	    	var nodes = $('#tt1').tree('getChecked');
	    	if(nodes.length > 0){
	    		$.each(nodes,function(index,obj){
	    			 $('#tt1').tree("uncheck",obj.target);
	    		});
	    	}
	    });
    	
    	$("#tt1").tree({
    		url:url,
    		checkbox:true,
    		onLoadSuccess:function(node,data){
    			var $accs = $('#accs').val();	
    			$("#tt1").tree("collapseAll");
				for(var i=0;i<data.length;i++){
					$("#tt1").tree("expand",data[i].target);
				}
				var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
				if(searchOwnerType == '1'){
					$("#accNames").val("查看全部");
				}else if(searchOwnerType == '2'){
					$("#accNames").val("查看自己");
				}else if($accs != null && $accs.length > 0){
    				var text='';
    				$($accs.split(',')).each(function(index,data){			
    					var node = $("#tt1").tree("find",data);
    					if(node != null){
    						text+=node.text+',';
    						$("#tt1").tree("check",node.target);
    					}				
    				});
    				if(text != ''){
						text=text.substring(0,text.length-1);
						$("#accNames").val(text);
					}else{
						$("#accNames").val("联系人");
					}
    			}
    		},
			onCheck:function(node,isCheck){
				var nodes = $('#tt1').tree('getChecked');
				if(nodes.length > 0){
					$("input[name=searchOwnerType]").iCheck('uncheck');
				}else if($("input[name=searchOwnerType]:checked").length == 0){
					$("input[name=searchOwnerType]:eq(0)").iCheck('check');
				}
			}
    	});	
	
	// 选择点保存
	$("#checkedId").click(function() {
		var nodes =$("#tt1").tree("getChecked");
		var accs = "";
		var text='';
		$(nodes).each(function(index,obj){
			if(obj.attributes.type == 'M'){
				accs+=","+obj.id;
				text+=obj.text+',';
			}		
		});
		if(accs.length > 0){
			accs=accs.substring(1,accs.length);
			text=text.substring(0,text.length-1);			
			$("#osType").val("3");
		}else{
			var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
			if(searchOwnerType == '1'){
				text="查看全部";
			}else{
				text="查看自己";
			}
			$("#osType").val(searchOwnerType);
		}	
		$("#accs").val(accs);
		$("#accNames").val(text);
	});
	
	//清空
	$('#clearId').click(function(){
		var nodes = $('#tt1').tree('getChecked');
		 $(nodes).each(function(index,obj){
        	$('#tt1').tree('uncheck', obj.target);
        });
        $('#accs').val('');
        $("#accNames").val('');
	});
	
	//播放全部录音(正序插入)
	$("#play-all-soundlist").on("click",function(e){
		e.stopPropagation();
		var $element = $("[name='icon-play']");//全部可以播放的录音
		var httpUrl = $("#project_path").val();
		$element.each(function(i,obj){
			var bplay=0;
			if(i == 0){//当第一条时，bplay为1；其他则为0
				bplay = 1;
			}else{
				bplay = 0;
			}
			var $this = $(obj);
			var v = $this.attr("v");
			var callState = $this.attr("callState");//呼叫类型
			// 获取当前行的客户姓名
			var callName = $this.attr("custName");
			var callNum = "";
			if(callState == "1"){ //1:呼入，2:呼出
					callNum = $this.attr("callerNum");// 主叫号码
			}else{
					callNum = $this.attr("calledNum");// 被叫号码
			}	
			var plength = $this.attr("timeLength");
			var url = $this.attr("url");
			//var url = "http://record.qftx.cn:8070/ds?v=1&p=73e053c3e2e7bf5b81ed719357f43756160304mp34a86c50e58e934b3914";
			var callId = $this.attr("callId");
			//window.location.href = httpUrl+"/call/callPlay.do?timeElngth="+plength+"&recordUrl="+encodeURIComponent(encodeURIComponent(url,"utf-8"))+"&callId="+callId+"&callName="+encodeURI(encodeURI(callName))+"&callNum="+callNum;
			recordCallPlay(httpUrl,plength,url,callId,callName,callNum,bplay);
		})
	})
	
});

var setNdate = function(i){
	$('#startDate').val('');
	$('#endDate').val('');
	$("#dDateType").val(i);
};	

var setCallState = function(i){
	$('#callState').val(i);
	$('#timeLength').val('');
	$('#timeLengthEnd').val('');
}

var emptyTransfer = function(data){
	if(data == null)
		return '';
	else 
		return data;
}