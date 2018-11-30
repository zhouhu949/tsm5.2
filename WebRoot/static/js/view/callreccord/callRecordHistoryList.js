function loadData(page){

    var _param = $("form").serialize();
    var _url=ctx+"/callrecord/historyListJson";
    loaderAjax(_url,_param);
    $('#checkAll').iCheck('uncheck');
}

function refreshPageData(isScroll){
    var table = $(".ajax-table");
    var _url=ctx+"/callrecord/historyScrollListJson ";
    var _param={scroll:$("#scrollstr").val()};
    loaderAjax(_url,_param,isScroll);
}


function loaderAjax(url,_param,isScroll){
    var table = $(".ajax-table");
	searchingDataTip();
	var timeout = setTimeout(function(){
		$(".tip_search_text").hide();
		$(".tip_search_error").text("网络繁忙，请耐心等候或重新查询！");
		$(".tip_search_error").show();
	},10000);
	$.ajax({
		type:"get",
		url:url,
        data:_param,
		error:function(){
			$(".tip_search_text").hide();
			$(".tip_search_error").text("网络异常，请重新查询！");
			$(".tip_search_error").show();
		},
		success:function(data){
			if(data.status=="success"){
				loadTable(data.list,isScroll);
				if(!isScroll && data.list.length == 0){
					completeTable(table)
				}
				var scrollStr = JSON.stringify(data.scroll);
				$("#scrollstr").val(scrollStr);
				$(".tip_search_data").hide();
				clearTimeout(timeout);
				//pa.load("new_page",data.item.page,refreshPageData,$(".ajax-table"));
			}else{
				clearTimeout(timeout);
				$(".tip_search_text").hide();
				$(".tip_search_error").text("网络异常，请重新查询！");
				$(".tip_search_error").show();
			}	
		}
	});
}

function loadTable(list,isScroll){
	var html='';
	for(var i in list){
		var call = list[i];//拼表格
		html +=
		"<tr class='"+(i%2==0?'':'sty-bgcolor-b')+"'>"+
		"<td style='width:100px;'><div class='overflow_hidden w100 link'>";
		if(call.custId != null){	
			html+="<a href='###' custId='"+call.custId+"'";
			if(isState == 0){
				if(call.define3 != null){
					html+="custName='"+emptyTransfer(call.define3)+"'";
				}else{
					html+="custName='"+emptyTransfer(call.custName)+"'";
				}
			}else{
				html+="custName='"+emptyTransfer(call.custName)+"'";
			}			
			html+= " class='icon-cust-card' title='客户卡片' name='add_custInfo'></a>";
		}else{		
			html+="<a href='javascript:void(0)' class='icon-cust-card img-gray' title='客户卡片'></a>";		
		}
		if(call.recordState == 1 && call.recordUrl != null && call.timeLength> 0 && call.recordUrl.indexOf('http:') != -1){				
				html+="<a data-authority = 'callRecord_recordPlay' href='javascript:void(0);' class='icon-play' name='icon-play'  callState='"+call.callState+"' timeLength='"+call.timeLength+"' url='"+call.recordUrl+"' callId='"+call.id+"' custName='"+emptyTransfer(call.custName)+"' callerNum='"+call.callerNum+"'  calledNum='"+call.calledNum+"' title='录音播放'></a>";
				html+="<a data-authority = 'callRecord_recordDownload' href='"+call.recordUrl+"' class='icon-down-load' title='下载'></a>";	
			if(issys == 1){
				html+="<a href='###' data-authority = 'callRecord_recordExample' class='icon-sound-recor img-gray' title='录音范例' ></a>";
			}			
	    }else if(call.recordState == 1 && call.recordUrl != null && call.timeLength> 0){
			html+="<a data-authority = 'callRecord_recordPlay' href='javascript:void(0);' class='icon-play' name='icon-play'  callState='"+call.callState+"' timeLength='"+call.timeLength+"' url='"+$("#playUrl").val()+call.recordUrl+"&d="+call.recordKey+"&id="+call.code+"&compid="+call.orgId+"&calllen="+call.timeLength+"' callId='"+call.id+"' custName='"+emptyTransfer(call.custName)+"' callerNum='"+call.callerNum+"'  calledNum='"+call.calledNum+"' title='录音播放'></a>";
			html+="<a data-authority = 'callRecord_recordDownload' href='"+$("#playUrl").val()+call.recordUrl+"&d="+call.recordKey+"&id="+call.code+"&compid="+call.orgId+"&calllen="+call.timeLength+"' class='icon-down-load' title='下载'></a>";
						
			if(issys == 1){
				html+="<a data-authority = 'callRecord_recordExample' href='###' class='icon-sound-recor' id='"+call.code+"'  title='录音范例' timeLength='"+call.timeLength+"' url='"+$("#playUrl").val()+call.recordUrl+"&d="+call.recordKey+"&id="+call.code+"&compid="+call.orgId+"&calllen="+call.timeLength+"'  callId='"+call.id+"' inputName='"+call.inputAcc+"' inputDate='"+call.define15+"' recordCode='"+call.recordUrl+"' attachParam='"+call.recordKey+"' callCode = '"+call.code+"' name='add_review'></a>";
			}	
	    }else{
	    	html+= "<a href='###' class='icon-play img-gray' title='录音播放'></a>"+
	    				"<a href='###' class='icon-down-load img-gray' title='下载'></a>";
	    	if(issys == 1){
	    		html += "<a href='###' data-authority = 'callRecord_recordExample' class='icon-sound-recor img-gray' title='录音范例' ></a>";
	    	}
	    }
		html+="</div></td>";
		if(isState == 0){
			html+="<td><div class='overflow_hidden w70' title='"+emptyTransfer(call.define3) +"'>"+emptyTransfer(call.define3) +"</div></td>";
		}
		html+="<td><div class='overflow_hidden w120' title='"+emptyTransfer(call.custName) +"'>"+emptyTransfer(call.custName) +"</div></td>";

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
		"	<td><div class='overflow_hidden w70' >";
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
		  html+="	</div></td>"+		
		"	<td><div class='overflow_hidden w120' title='"+call.callerNum+"' name='callerNum_'>"+call.callerNum+"</div></td>"+
		"	<td><div class='overflow_hidden w120' title='"+call.calledNum+"' name='calledNum_'>"+call.calledNum+"</div></td>";
		
		html+=
		"	<td><div class='overflow_hidden w120' title='"+call.define15 +"'>"+call.define15 +"</div></td>"+
		"	<td><div class='overflow_hidden w120' title='"+call.timeLength +"秒'>"+call.define16 +"</div></td>";
		 if(call.saleProcessName != null){//如果销售进程不为空
			 html+="	<td><div class='overflow_hidden w120' title='"+call.saleProcessName+"'>"+call.saleProcessName+"</div></td>";
		 }else{//销售进程为空
			 html+= "	<td><div class='overflow_hidden w120' title='无'>无</div></td>";
		 }
		html+=
		"	<td><div class='overflow_hidden w100' title='"+call.inputName+"'>"+call.inputName+"</div></td>"+
		"	<td><div class='overflow_hidden w70'>";
		if(call.dataresource != null || call.dataresource != ""){
			if(call.dataresource == 1){
				html+= "PC端";
			}else if(call.dataresource == 2){
				html+= "ye";
			}else if(call.dataresource ==3){
				html+= "ippbx";
			}else if(call.dataresource == 4){
				html+= "快话";
			}else if(call.dataresource == 5){
				html+= "APP";
			}
		} 
		html+=" </div></td>"+
		"</tr>";
	}
	
	//判断是不是需要append （滚动请求数据）
	if(isScroll){
		$(".ajax-table>tbody").append(html);
		$("#flag").val("true");
	}else{
		$(".ajax-table>tbody").html(html);
	}
    /* 屏蔽手机或固话中间几位  */
    //var idReplaceWord = $("#idReplaceWord").val();	
    dataReplaceWord($("#idReplaceWord").val())
}

/* 屏蔽手机或固话中间几位函数  */
function dataReplaceWord(idReplaceWord){
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
}

$(function(){
	issys = $("#isSys").val();
	isState = $("#isState").val();
//	loadData();
	$("#querys").click(function(e){
		e.stopPropagation();
		var queryval = $.trim($("#queryText").val());
		var queryTitle = $(".search_head_select option:selected").text();
		if( queryval != ""){
			loadData();
		}else{
			window.top.iDialogMsg("提示",'请输入'+queryTitle+"！");
		}
	});
	//scroll data
	var i =0;
	$(".hyx-history-scroll-tablebox").scroll(function(){
		var $this = $(this);
		var flag = $("#flag");
		
		var scrollTop = $this.scrollTop();
		var scrollHeight = $this.get(0).scrollHeight;
		var heights = $this.height();
		
		var offset = 10;
		if( scrollHeight - ( scrollTop + heights ) <= offset   ){
			if( flag.val() == 'true' ){
				flag.val("false");
				refreshPageData(true)
			}
		}
	})
	//表格固定表头
	$(".hyx-history-scroll-tablebox").find(".ajax-table").theadFixed();
	
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

    var toEndDate = moment().subtract(3,"months").format("YYYY-MM-DD");
	if(!moment().isSame(toEndDate,"year")){
		toEndDate = moment(toEndDate).endOf("year").format("YYYY-MM-DD");
	}
	// 通话日期
    $('#d1').dateRangePicker({
    	language: "cn",
    	showShortcuts:false,
        endDate:toEndDate,
        format: 'YYYY-MM-DD',
    })
    .bind('datepicker-apply',function(event,obj){
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
        $('#d1').data("dateRangePicker").setEndDate(toEndDate);
        $('#d1').data("dateRangePicker").resetMonthsView();
    }).bind('datepicker-first-date-selected',function(event,obj){
    	var startDate = moment(obj.date1.getTime()).format("YYYY-MM-DD");
    	if(moment().isSame(startDate,"year")){
    		var endDate = moment().subtract(3,"months").format("YYYY-MM-DD");
    		$('#d1').data("dateRangePicker").setEndDate(endDate);
    		$('#d1').data("dateRangePicker").resetMonthsView();
    	}else{
    		var endDate = moment(startDate).endOf("year").format("YYYY-MM-DD");
    		$('#d1').data("dateRangePicker").setEndDate(endDate);
        	$('#d1').data("dateRangePicker").resetMonthsView();
    	}
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
	
	// 录音范例点击的时候
	$("table").on('click',"[name='add_review']", function(){	
		var plength = $(this).attr("timeLength");
		var url = $(this).attr("url");
		var callId = $(this).attr("callId");
		var inputName = $(this).attr("inputName");
		var inputDate = $(this).attr("inputDate");
		var recordCode = $(this).attr("recordCode");
		var attachParam = $(this).attr("attachParam");
		var callCode = $(this).attr("callCode");
		var id = $(this).attr("id");
		if(inputName == "" || inputName == null){
			inputName = "admin";
		}		
		var reUrl = ctx+"/callrecord/review?id="+id+"&callId="+callId+"&timeLength="+plength+"&inputDate="+inputDate+"&callCode="+callCode+"&url="+encodeURIComponent(encodeURIComponent(url,"utf-8"))+"&inputName="+encodeURIComponent(encodeURIComponent(inputName,"utf-8"))+"&recordCode="+recordCode+"&attachParam="+encodeURIComponent(encodeURIComponent(attachParam,"utf-8"))+"&v="+new Date().getTime();
		pubDivShowIframe('add_review_',reUrl,'录音范例',700,255);		
	});

	// 客户卡片点击的时候
	$("table").on('click',"[name='add_custInfo']", function(){	
		var custId = $(this).attr("custId");
		var custName = $(this).attr("custName");
		 $.ajax({
				url:ctx+'/callrecord/findResIsDel',
				type:'post',
				data:{custId:custId},
				dataType:'text',
				error:function(){alert('网络异常，请稍后再试！')},
				success:function(data){
					if(data == 'success'){
						var custName1 = custName||"客户卡片";
						window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName1);	
					}else{
						if(data == 'isDel'){
							window.top.iDialogMsg("提示",'资源已经被删除！');
						}else if(data == 'error'){
							window.top.iDialogMsg("提示",'出现异常，请稍后操作。');
						}else {
							if(issys == 1){
								window.top.iDialogMsg("提示",'该客户的所有者不在您的管辖范围，您无权查看；');
							}else{
								window.top.iDialogMsg("提示",'该客户的所有者是【'+data+'】，无权查看客户卡片；');
							}							
						}						
					}
				}
			});	
	});
	

    /*所有者*/	
    /*
    if(issys == 1){
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
    	var url = ctx+"/orgGroup/get_group_user_json";
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
						$("#accNames").val("所有者");
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
    }
	
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
	});*/
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
};


var emptyTransfer = function(data){
	if(data == null)
		return '';
	else 
		return data;
};

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

function completeTable(tableObj){
	var tbody=tableObj.find("tbody");
	var trNums=tbody.find("tr").length;
	var column = tableObj.find("thead").find("th:visible").length;//表格列数
	var tableStr="";
	for(var i = trNums;i<10;i++){
		if(i == 0){
			tableStr  +="<tr><td colspan='" + column + "'>当前列表无数据！</td></tr>";
		}else{
			tableStr  +="<tr class="+(i % 2 ==0? '': 'sty-bgcolor-b')+"><td colspan='" + column + "'></td></tr>";
		}
	}
	tbody.html(tableStr);
}