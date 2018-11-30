var timeline_html = $(".taoCust_right_bottom>.timeline").html();
var p_b_div_html = $(".card .p_b_div").html();
var tip_box_html = $(".actionTag .tip-box").html();
var isOpera = true; // 不可重复点击
$(document).ready(function(){
	var custId = $("#custId").val();
	var planParam = $("#planParam").val();
	var custType = $("#custType").val();
	var custCation = $("#custCation").val();
	var state = $("#state").val();
	var startPage = $("#startPage").val();
	var last_option_id = $("#last_option_id").val();
	var last_cust_type = $("#last_cust_type").val();
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
		"custId": custId,
		"custCation":$("#custCation").val(),
		"page.showCount": $("#pp_showCount").val(),
		"page.currentPage": $("#pp_currentPage").val(),
		"isContact": $(".table-check-area input[name=isContact]").val()
	};

	$('#moreFollowId').live('click',function(){
		var custName = "";
		if(state!=null && state == 1){ // 企业客户
			custName = $("#cust_name").val()||"客户卡片";
		}else{
			custName = $("#cust_company").val()||$("#cust_name").val()||"客户卡片";
		}
		var custId = $('#custId').val();
		if(custId=='' || custId==null){
			return false;
		}else{
			window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName);
		}
	});
	mainCustFollowPage(custId,planParam,custType,custCation,state);
	
	custFollowInfo(custId);
	
	custFollowShowList(custId);
	
	saleChanceInit(custId);
	
	if($("#isAlarm").val() =="1"){
		custFollowAralmRight(custId,startPage);
	}else{
		custFollowRightWait(submitData);
		var type_choose_search=$(".type_choose_search");
		var custType_list_search=$(".custType_list_search");
		var saleProcess_list_search=$(".saleProcess_list_search");
		list_search_select(type_choose_search);
		list_search_select(custType_list_search);
		list_search_select(saleProcess_list_search);
	}

	function saleChanceInit(custId){
		$.ajax({
			type: "get",
			url: ctx+"/cust/saleChance/getSaleChanceByCustId",
			data:{
				"custId": custId
			},
			success: function(data){
				var appendCode = '<option value="">-请选择-</option>';
				if(data.length > 0){
					for(var i=0;i<data.length;i++){
						appendCode += '<option value="'+data[i].saleChanceId+'">'+data[i].saleChanceName+'</option>';
					}
				}
				$("#saleChanceId").html(appendCode);
			},
			error: function(e){}
		});
	}
	
	function list_search_select(list){
		list.change(function(){
			custFollowRightWait($('#custId').val(),$("#planParam").val(),custType,custCation,$("#last_cust_type").val(),$("#last_option_id").val());
		});
	};
	/** **************************** 保存 **********************************  */

    // 保存并继续下一条
    $("#saveBut").live("click",function(){
    	var custId = $('#custId').val();
    	$("#custGiveUp").val("");
    	$("#custSign").val(""); 
    	if(isOpera && checkIsNull()){
    		isOpera = false;
//    		disabledButton(this);
    		// 查询客户是否变成公海客户
    		var url1 = ctx+"/cust/custFollow/getCustStatus";
    		$.ajax({
    			url: url1,
    			type:'POST',
    			data:{'custId':custId},
    			dataType:'json',
    			error:function(){alert("网络异常，请稍后再操作！");},
    			success:function(data){
    				if(data == 0){
    					queDivDialog("follw_save_cust","客户已入公海池，是否需要继续当前操作？",function(){
    		    			saveCustFollow("");
    		    		},
    		    		function(){
    		    			nextPage(); // 取下一条
    		    		});  
    				}else{
    					saveCustFollow("");
    				}
    			}
    		}); 		
    	}
    });
    
   // 保存并放弃 
    $("#canelBut").live("click",function(){
    	var custId = $('#custId').val();
    	if(isOpera && checkIsNull()){
    		$("#custGiveUp").val("1"); // 有值表示是放弃 操作
        	$("#custSign").val(""); 
    		isOpera = false;
    		// 查询客户是否变成公海客户
    		var url1 = ctx+"/cust/custFollow/getCustStatus";
    		$.ajax({
    			url: url1,
    			type:'POST',
    			data:{'custId':custId},
    			dataType:'json',
    			error:function(){alert("网络异常，请稍后再操作！");},
    			success:function(data){		    				
    				if(data == 0){
    					queDivDialog("follw_save_cust","客户已入公海池，是否需要继续当前操作？",function(){
    		    			saveCustFollow("");
    		    		},
    		    		function(){
    		    			nextPage(); // 取下一条
    		    		});  
    				}else{
    					queDivDialog("follw_save_cust","正在放弃客户，是否需要继续当前操作？",function(){
    		    			saveCustFollow("");
    					},
    					function(){
    						isOpera = true;
    					}); 
    				}
    			}
    		}); 		
    	}
    });
    
 	// 签约客户
    $("#signCustId").live("click",function(){
    		var nextContactValidation = $('#nextContactValidation').val();
    		var html = nextContactValidation == 1 ? "是否确认签约？<br /><input type='checkbox' id='saveNextFollow' />勾选保存下次联系时间" : "是否确认签约？";
    		window.top.pubDivDialog("confirm_sign",html,function(){
    		var custId = $('#custId').val();
    		//debugger;
    		if(window.top.$("#saveNextFollow").length > 0){
				var saveNextFollow = window.top.$("#saveNextFollow").is(":checked") ? '2' : '1';
				$("#isAddDate").val(saveNextFollow);
			}
        	if(isOpera && checkIsNull()){
        		$("#custSign").val("1"); // 有值表示是签约 操作
        		$("#custGiveUp").val("");
//        		disabledButton(this);
        		// 查询客户是否变成公海客户
        		var url1 = ctx+"/cust/custFollow/getCustStatus";
        		$.ajax({
        			url: url1,
        			type:'POST',
        			data:{'custId':custId},
        			dataType:'json',
        			error:function(){alert("网络异常，请稍后再操作！");},
        			success:function(data){
        				if(data == 0){
        					window.top.iDialogMsg("提示","客户已入公海池，无法签约！"); 
        					setTimeout('nextPage()',1000); // 取下一条
        				}else{
        					
        					saveCustFollow("sign");
        				}
        			}
        		}); 	   		
        	}
    	});  	
    });
	
	//播放按钮点击的时候
	$("[name='icon-play']").live("click",function(){
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
		var callId = $(this).attr("callId");		
		//window.location.href = httpUrl+"/call/callPlay.do?timeElngth="+plength+"&recordUrl="+encodeURIComponent(encodeURIComponent(url,"utf-8"))+"&callId="+callId+"&callName="+encodeURI(encodeURI(callName))+"&callNum="+callNum;
		recordCallPlay(httpUrl,plength,url,callId,callName,callNum);
	});
	
});

function replaceWord_(){
	// 电话号码安全设置
	/********** 屏蔽手机或固话中间几位  *********/
	var idReplaceWord = $("#idReplaceWord").val();
	if (idReplaceWord == 1) {
		$("[name=telphone_]").each(function(idx, obj) {
			var phone = $(obj).text();
			if (phone != null && phone != '') {
				replaceWord(phone, $(obj));
				replaceTitleWord(phone, $(obj));
			}
		});
	}
}

function custFollowAralmRight(custId,startPage){
	$.ajax({
		url:ctx + "/cust/custFollow/custFollowAralmRightPage",
		type:'post',
		data:{
			custId			:		custId,
			startPage		:		startPage
		},
		dataType:'json',
		error:function(data){},
		success:function(data){
			var length = data.restDtos.length;
			if(length > 0){
				var tbody = $(".right_wait_table tbody");
				tbody.find("tr").remove();
				for(var i=0;i<length;i++){
					var tr = "<tr id='"+data.restDtos[i].resCustId+"' startPage='"+data.restDtos[i].startPage+"'><td>"+data.restDtos[i].nextActionDate+"</td><td><div class='otherRes_td_div_auto table_tr_td_div'><span title='"+data.restDtos[i].custName+"'>"+data.restDtos[i].custName+"</span></td><td name='telphone_'>"+data.restDtos[i].telphone+"</td></tr>";
					tbody.append(tr);
				}
				for(var i=length;i<5;i++){
					var tr = "<tr><td colspan='5'></td></tr>";
					tbody.append(tr);
				}
				
				$("#next_custId").val(tbody.find("tr").eq(0).attr("id"));
				
				tbody.find("tr").each(function(){
					var $this = $(this);
					if($this.attr("id") != null && $this.attr("id") != ""){
						$this.click(function(){
							var custId = $this.attr("id");
							var startPage = $this.attr("startPage");
							var custType = $("#custType").val();
							var custCation = $("#custCation").val();
							var planParam = $("#planParam").val();
							var state = $("#state").val();
							
							$("#custId").val(custId);
							$("#startPage").val(startPage);
							
							custFollowInfo(custId);
							
							custFollowShowList(custId);
							
							custFollowAralmRight(custId,startPage);
							
							RefreshCustFollowPage(custId,planParam,custType,custCation,state,$("#isSelect").val());
						});
					}
				});
				replaceWord_();
				td_div_auto();
			}
		}
	});
}

function custFollowRightWait(submitData){
	$.ajax({
		url:ctx + "/cust/custFollow/custFollowRightPage",
		type:'post',
		data:submitData,
		dataType:'json',
		error:function(data){},
		success:function(data){
//			console.log(data.restDtos);
//			console.log(submitData);
//			console.log(data.dto);
			var showCount = data.dto.page.showCount;
			var totalPage = data.dto.page.totalPage;
			var currentPage = $("#pp_currentPage").val();
			
			$("#pp_totalResult").val(data.dto.page.totalResult);
			
			var length = data.restDtos.length;
			var sort_field = $(".right_wait_table .sort-field");
			var sort_name = "";
			if(submitData.orderType == 1 || submitData.orderType == 2){
				sort_field.text("下次联系时间");
				sort_name = "nextActionDate";
			}else if(submitData.orderType == 3 || submitData.orderType == 4){
				sort_field.text("最近联系时间");
				sort_name = "actionDate";
			}else if(submitData.orderType ==5 || submitData.orderType == 6){
				sort_field.text("淘到客户时间");
				sort_name = "amoyToCustomrDate";
			}
			if(length > 0){
				var tbody = $(".right_wait_table tbody");
				tbody.find("tr").remove();
				var nextDate = "";
				for(var i=0;i<length;i++){
					if(data.restDtos[i][sort_name] !=null){
						nextDate = data.restDtos[i][sort_name].substring(0,16);
					}else{
						nextDate = "";
					}
					var tr = "<tr id='"+data.restDtos[i].resCustId+"' startPage='"+data.restDtos[i].startPage+"'><td>"+nextDate+"</td><td><div class='otherRes_td_div_auto table_tr_td_div'><span title='"+data.restDtos[i].custName+"'>"+data.restDtos[i].custName+"</span></td><td name='telphone_'>"+data.restDtos[i].telphone+"</td></tr>";
					tbody.append(tr);
				}
				for(var i=length;i<5;i++){
					var tr = "<tr><td colspan='5'></td></tr>";
					tbody.append(tr);
				}
				
				$("#next_custId").val(tbody.find("tr").eq(0).attr("id"));
				
				tbody.find("tr").each(function(){
					var $this = $(this);
					if($this.attr("id") != null && $this.attr("id") != ""){
						$this.click(function(){
							var custId = $(this).attr("id");
							var custType = $("#custType").val();
							var custCation = $("#custCation").val();
							var planParam = $("#planParam").val();
							var state = $("#state").val();
							var last_option_id = $("#last_option_id").val();
							var last_cust_type = $("#last_cust_type").val();
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
								"custId": custId,
								"custCation":$("#custCation").val(),
								"page.showCount": $("#pp_showCount").val(),
								"page.currentPage": $("#pp_currentPage").val(),
								"isContact": $(".table-check-area input[name=isContact]").val()
							};
							
							custFollowInfo(custId);
							
							custFollowShowList(custId);
							
							custFollowRightWait(submitData);
							
							RefreshCustFollowPage(custId,planParam,custType,custCation,state,$("#isSelect").val());
						});
					}
				});
				replaceWord_();
				td_div_auto();
				
				if(totalPage == 1 || totalPage == 0){
					$(".table-button-area .btn-prev").attr("disabled",true);
					$(".table-button-area .btn-next").attr("disabled",true);
				}else if(currentPage == 1){
					$(".table-button-area .btn-prev").attr("disabled",true);
					$(".table-button-area .btn-next").attr("disabled",false);
				}else if(currentPage == totalPage){
					$(".table-button-area .btn-prev").attr("disabled",false);
					$(".table-button-area .btn-next").attr("disabled",true);
				}else{
					$(".table-button-area .btn-prev").attr("disabled",false);
					$(".table-button-area .btn-next").attr("disabled",false);
				}
			}else{
				var tbody = $(".right_wait_table tbody");
				tbody.find("tr").remove();
				var tr = "<tr><td colspan='5'>当前列表暂无数据</td></tr>";
				tbody.append(tr);
				for(var i=0;i<4;i++){
					var tr = "<tr><td colspan='5'></td></tr>";
					tbody.append(tr);
				}
				$(".table-button-area .btn-prev").attr("disabled",true);
				$(".table-button-area .btn-next").attr("disabled",true);
			}
			tableBottomAreaInit(submitData);
			$("#planParam").val(data.planParam);
		}
	});
}

function tableBottomAreaInit(submitData){
	var currentPage = parseInt($("#pp_currentPage").val());
//	console.log(submitData);
	$(".btn-next").unbind("click").click(function(e){
		$("#pp_currentPage").val(currentPage+1);
		$(".table-button-area .show-count").text(currentPage+1);
		submitData["page.currentPage"] = $("#pp_currentPage").val();
		submitData["page.totalResult"] = $("#pp_totalResult").val();
		custFollowRightWait(submitData);
	});
	
	$(".btn-prev").unbind("click").click(function(e){
		$("#pp_currentPage").val(currentPage-1);
		$(".table-button-area .show-count").text(currentPage-1);
		submitData["page.currentPage"] = $("#pp_currentPage").val();
		custFollowRightWait(submitData);
	});
	
	$(".table-check-area input[name=isContact]").unbind("change").change(function(e){
		var _this = $(this);
		$("#pp_currentPage").val(1);
		$(".table-button-area .show-count").text($("#pp_currentPage").val());
		var checked_input_isContact = $(".table-check-area input[name=isContact]:checked");
		checked_input_isContact.length>0?_this.val(1):_this.val(0);
		submitData = {
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
			"page.showCount": $("#pp_showCount").val(),
			"page.currentPage": $("#pp_currentPage").val(),
			"isContact": $(".table-check-area input[name=isContact]").val()
		};
		custFollowRightWait(submitData);
	});
}

function RefreshCustFollowPage(custId,planParam,custType,custCation,state,isSelect){
	$.ajax({
		url:ctx + "/cust/custFollow/RefreshCustFollowPage",
		type:'post',
		data:{
			custId			:		custId,
			planParam	:		planParam,
			custType		:		custType,
			custCation	:		custCation,
			state				:		state
		},
		dataType:'json',
		error:function(data){},
		success:function(data){
//			console.log(data);
			
			/**
			 * 天坑埋于2017-07-13
			 * 判断"放入公海"和"签约"按钮是否显示
			 * author:zhangjianhua
			 */
			var com_putinto_seas = $("#com_putinto_seas").val();
			var com_sign = $("#com_sign").val();
			if(com_putinto_seas == 'true') {
				com_putinto_seas = true;
			}else {
				com_putinto_seas = false;
			}
			if(com_sign == 'true') {
				com_sign = true;
			}else {
				com_sign = false;
			}
			//debugger;
			var is_show_highSeas = window.top.shrioAuthObj("base_putIntoHighSeas");
			var is_show_signed = window.top.shrioAuthObj("base_signCust");
			if(data.status != 4 && data.status != 6 && data.status != 7 && data.status != 8 && data.isMark == 1){								
				(com_putinto_seas && is_show_highSeas) ? $("#canelBut").show():$("#canelBut").hide();		
				(com_sign && is_show_signed) ? $("#signCustId").show():$("#signCustId").hide();
				$("#saveBut").css("margin-left","0");
			}else if(data.status != 4 && data.status != 6 && data.status != 7 && data.status != 8 && data.isMark == 2 && data.isSign== 1){
				$("#canelBut").hide();	
				(com_sign && is_show_signed) ? $("#signCustId").show():$("#signCustId").hide();
				$("#saveBut").css("margin-left","0");
			}else{
				$("#canelBut").hide();
				$("#signCustId").hide();
				$("#saveBut").css("margin-left","105px");
			}
//			isAuthority("data-authority");									
			$("#feedbackComment").val("");
			$("#custId").val(custId);
			$("#followId").val(data.followId);
			$("#planParam").val(data.planParam);
			productTreeInit(data.suitProcList); 
			$("#actionDate").val(data.actionDate);//联系时间
			$("#followDate").val(data.defDate);	//下次联系时间
			
			var tsmCustGuideEntity = data.tsmCustGuideEntity;
			var lastCustFollow  = data.lastCustFollow;		
			// 上一次跟进信息
			if(lastCustFollow != null){
				$("#lastSaleProcessId").val(lastCustFollow.optionlistId);					
				var custType_list = $("#custTypeId");
				var custType_list_options = custType_list.find("option");
				var actionType_list = $("#actionType");
				var actionType_list_options = actionType_list.find("option");
				option_selected(lastCustFollow.actionType,actionType_list_options);
				var followType_list = $("#followType");
				var followType_list_options = followType_list.find("option");
				if(followType_list.data("select-value") == 1){
					option_selected("1",followType_list_options);
				}else{
					option_selected("",followType_list_options);
				}
				var saleProcess_list = $("#saleProcessId");
				var saleProcess_list_options = saleProcess_list.find("option");
				option_selected(lastCustFollow.optionlistId,saleProcess_list_options);
				var effectiveness = $("#effectiveness");
				var effectiveness_options = effectiveness.find("option");
				option_selected(lastCustFollow.effectiveness,effectiveness_options);			
				var actionTag_a = $(".actionTag .tip-box a");
				var labelCode = lastCustFollow.labelCode;
				var labelName = lastCustFollow.labelName;
				if(isSelect == 0){
					$(".hyx-sce-area .tip-box .choose-tag").remove();
					actionTag_a.removeClass("click-hover");
					for(var i=0;i<actionTag_a.length;i++){
						if(labelCode.indexOf(actionTag_a[i].name) != -1){
							$(actionTag_a[i]).addClass("click-hover");
						}
					}
				}else{
					var appendCode = "";
					var arr_labelCode = labelCode==""?[]:labelCode.replace(/#$/g,"").split("#");
					var arr_labelName = labelName==""?[]:labelName.replace(/#$/g,"").split("#");
					
					for(var j=0;j<arr_labelCode.length;j++){
						appendCode += "<a href='javascript:;' class='action-tag click-hover' data-id='" + arr_labelCode[j] + "' data-name='"+arr_labelName[j]+"'>" + arr_labelName[j] + "</a>";
					}
					$(".hyx-sce-area .tip-box a.click-hover").remove();
					$(".hyx-sce-area .tip-box").append(appendCode);
					$(".hyx-sce-area .tip-box .choose-tag").attr("data-chosen-id",arr_labelCode.join());
				}
				
				
				$("#labelCodeId").val(labelCode);
				$("#labelNameId").val(labelName);			
			}else{
				$("#lastSaleProcessId").val(data.lastOptionListId);		
				var saleProcess_list = $("#saleProcessId");
				var saleProcess_list_options = saleProcess_list.find("option");
				option_selected(data.lastOptionListId,saleProcess_list_options);
			}		
			
			// 销售导航信息
			if(tsmCustGuideEntity != null){
				$("#custGuideId").val(tsmCustGuideEntity.custGuideId);
				option_selected(tsmCustGuideEntity.custTypeId,custType_list_options);
				$("#expectDate").val(tsmCustGuideEntity.expectDate);
				$("#expectSale").val(tsmCustGuideEntity.expectSale);
				$("#saleWay").val(tsmCustGuideEntity.saleWay);
				$("#custArgue").val(tsmCustGuideEntity.custArgue);
				$("#custInterest").val(tsmCustGuideEntity.custInterest);
				$("#competitor").val(tsmCustGuideEntity.competitor);
			}		
		}
	});
}

function mainCustFollowPage(custId,planParam,custType,custCation,state){
	$.ajax({
		url:ctx + "/cust/custFollow/mainCustFollowPage",
		type:'post',
		data:{custId:custId},
		dataType:'json',
		error:function(data){},
		success:function(data){
//			console.log(data);
//			"保存并放弃","签约"按钮是否显示
			//debugger;
			
			custTypeInit(data.custTypeList);			//客户类型
			productTreeInit(data.suitProcList);		//产品列表
			saleProcessInit(data.saleProcessList);	//销售进程
			//联系有效性
			if($("#isSelect").val() == 0){
				$(".hyx-sce-area .tip-box .choose-tag").remove();
				actionTagInit(data.labelList); // 行动标签
			}else{
				$(".hyx-sce-area .tip-box .choose-tag").show();
				$(".hyx-sce-area .tip-box .sty-hid").remove();
				groupActionTagInit();
			}
			//联系小记
			//下次联系
			$("#actionDate").val(data.actionDate);//联系时间
			$("#followDate").val(data.defDate);	//下次联系时间
			
			RefreshCustFollowPage(custId,planParam,custType,custCation,state,$("#isSelect").val());
			
		}
	});
}

function option_selected(lastCustFollow_value,options){
	for(var i=0;i<options.length;i++){
		if(lastCustFollow_value == options[i].value){
			options[i].selected = true;
		}
	}
}

function custFollowInfo(custId){
	var url2 = ctx + "/cust/custFollow/custFollowEnterInfo";
 	if($("#state").val() == 0){ // 个人客户
 		url2 = ctx + "/cust/custFollow/custFollowPersonInfo";
 	}
	$.ajax({
		url:url2,
		type:'post',
		data:{custId:custId},
		dataType:'json',
		error:function(data){},
		success:function(data){
//		console.log(data);
			
			custDataInit(data.custData,custId);
//			var dialedNumber = data.custInfo.dialedNumber;
			
			var name = data.custEntity.name;
			if(name == null){
				name = "";
			}
			if($("#state").val() == 0){
				var mobilephone = data.custEntity.mobilephone;
				var telphone = data.custEntity.telphone;
				var qq = data.custEntity.qq;
				var weChat = "";
				var email = data.custEntity.email;
				var wangwang = data.custEntity.wangwang;
				$("#cust_company").val(data.custEntity.company);
				$("#concat_name").val(data.custEntity.name);
			}else{
				var mobilephone = data.detail.telphone;
				var telphone = data.detail.telphonebak;
				var qq = data.detail.qq;
				var weChat = "";
				var email = data.detail.email;
				var wangwang = data.detail.wangwang;
				$("#concat_name").val(data.detail.name);
				$("#concat_phone").val(mobilephone);
				$('#concatId').val(data.detail.tscidId);
				$(".card_operate_person_infor").show();
			}
			
			$("#cust_name").val(data.custEntity.name);
			$("#cust_type").val(data.custEntity.type);
			$("#cust_status").val(data.custEntity.status);
			$("#isConcat").val(data.custEntity.isConcat);
			
//			$('#custName').val(data.custName);
//			$('#concat_phone').val(data.mainPhone);
//			$('#concat_name').val(data.concatName);
			var list = data.custInfo.list;
//			$("#custInfo_dialedNumber").text(dialedNumber);
			$("#custInfo_name").attr("title",name);
			$("#custInfo_name").text(name);
			judgeIsNull(mobilephone,telphone,qq,weChat,email,wangwang);
			
			if(list.length > 0){
				var card_operate_head_ul =$("#card_operate_head_ul");
				card_operate_head_ul.html("");
				var appendCode = "";
				for(var i=0;i<list.length;i++){
					if(list[i].isDefault == "1"){
						appendCode = "<div class='card_operate_apart import'><span class='import_one'></span>"+list[i].name+"</div>";
					}else{
						appendCode = "<div class='card_operate_apart'>"+list[i].name+"</div>";
					}
					card_operate_head_ul.append(appendCode);
				}
				var div = card_operate_head_ul.find("div");
				div.eq(0).addClass("choosed");
				if(list[0].sex == "1"){
					$(".card_person_infomation_sex>span").text("男");
				}else if(list[0].sex == "2"){
					$(".card_person_infomation_sex>span").text("女");
				}else{
					$(".card_person_infomation_sex>span").text("");
				}
//				$(".card_person_infomation_sex>span").text(list[0].sex);
				$(".card_person_infomation_zw>span").text(list[0].duty);
//				$(".card_person_infomation_cs>span").text(list[0].sex);
				judgeIsNull(list[0].telphone,list[0].telphonebak,list[0].qq,list[0].weChat,list[0].email,list[0].wangwang);
				div.click(function(){
					var $this = $(this);
					var index = $this.index();
					div.removeClass("choosed");
					$this.addClass("choosed");
					$("#concat_phone").val(list[index].telphone);
					$("#concat_name").val(list[index].name);
					if(list[index].sex == "1"){
						$(".card_person_infomation_sex>span").text("男");
					}else if(list[index].sex == "2"){
						$(".card_person_infomation_sex>span").text("女");
					}else{
						$(".card_person_infomation_sex>span").text("");
					}
//					$(".card_person_infomation_sex>span").text(list[index].sex);
					$(".card_person_infomation_zw>span").text(list[index].duty);
//					$(".card_person_infomation_cs>span").text(list[0].sex);
					judgeIsNull(list[index].telphone,list[index].telphonebak,list[index].qq,list[index].weChat,list[index].email,list[index].wangwang);
				});
			}
//			autoCall();
			//强行修改电话号码超出长度后的字体大小
			var operate_content_area = $(".operate_content_area");
			operate_content_area.css("font-size","14px");
			if($("#custInfo_mobilephone").height() > operate_content_area.height() || $("#custInfo_telphone").height() > operate_content_area.height()){
				operate_content_area.css("font-size","12px");
			}
		}
	});
}

function custFollowShowList(custId){
	$.ajax({
		url:ctx+'/cust/custFollow/custFollowShowList',
		type:'post',
		data:{custId:custId},
		dataType:'json',
		error:function(data){
			alert("custFollowShowListError");
		},
		success:function(data){
//			console.log(data);
			
			var length = data.custFollows.length;
			var taoCust_right_bottom = $(".taoCust_right_bottom");
			var timeline = $(".taoCust_right_bottom .timeline");
			var appendCode = "";
			if(length == 0){
				appendCode = "<div class='no_data_tip'>暂无跟进记录</div>";
				timeline.html(appendCode);
				
				var no_data_tip = $(".no_data_tip");
				if(no_data_tip.length != 0){
					timeline.height(taoCust_right_bottom.height() - 35);
					timeline.css("margin-top","0");
					no_data_tip.css("padding-top",(timeline.height() - no_data_tip.height())/2);
				}
			}else{
				timeline.attr("style","");
				timeline.html(timeline_html);
				var timeline_ul = $("#timeline_ul");
				for(var i = 0; i < length; i++){
					var actionType = "";
					switch (data.custFollows[i].actionType){
						case "1":
							actionType = '<span title="电话联系">（电话联系）</span>';
							break;
						case "2":
							actionType = '<span title="会客联系">（会客联系）</span>';
							break;
						case "3":
							actionType = '<span title="客户来电">（客户来电）</span>';
							break;
						case "4":
							actionType = '<span title="短信联系">（短信联系）</span>';
							break;
						case "5":
							actionType = '<span title="QQ联系">（QQ联系）</span>';
							break;
						case "6":
							actionType = '<span title="邮件联系">（邮件联系）</span>';
							break;
						default:
							actionType = '';
							break;
					}
					
/*					var followType = "";
					switch (data.custFollows[i].followType){
						case "1":
							followType = '<span class="phone_dhlx" title="电话联系"></span>';
							break;
						case "2":
							followType = '<span class="phone_hklx" title="会客联系"></span>';
							break;
						case "3":
							followType = '<span class="customer_khlx" title="客户来电"></span>';
							break;
						case "4":
							followType = '<span class="message_dxlx" title="短信联系">';
							break;
						case "5":
							followType = '<span class="qq_qqlx" title="QQ联系"></span>';
							break;
						case "6":
							followType = '<span class="mail_yjlx" title="邮件联系"></span>';
							break;
						default:
							followType = '';
							break;
					}*/
					
/*					var effectiveness = "";
					switch (data.custFollows[i].effectiveness){
						case 	1:
							effectiveness = '<span class="valid_follow" title="有效联系"></span>';
							break;
						default:
							effectiveness = '<span class="novalid_follow" title="无效联系"></span>';
							break;
					}*/
					
					var bitch_custName_title = $("#state").val()==0?"客户姓名":"客户联系人";
					var bitch_custName = $("#state").val()==0?data.custFollows[i].name:data.custFollows[i].custName;
					
					appendCode = "<li class='li-load cfu-mt'>"+
												"<div class='cfu-cirb'></div>"+
												"<div class='right'>"+
													"<div class='arrow'><em>◆</em><span>◆</span></div>"+
													"<div class='cfu-box'>"	+
														"<div class='cfu-time'>"+data.custFollows[i].showLastActionDate + actionType + "</div>"+
														"<p class='cfu-list com-none'>"+bitch_custName_title+"：<span>"+bitch_custName +"</span></p>"+
														"<p class='cfu-list com-none'>销售联系人：<span>"+data.custFollows[i].ownerName+"("+data.custFollows[i].followAcc+")"+"</span></p>";
														/*"<p class='cfu-list com-none'>销售进程：<span>"+data.custFollows[i].optionName+"</span></p>"*/
					if(data.custFollows[i].labelName != null && data.custFollows[i].labelName !=""){
						appendCode += "<p class='cfu-list com-none action-tag'><span>行动标签：</span><span class='tag-content' title='"+data.custFollows[i].labelName+"'>"+data.custFollows[i].labelName+"</span></p>";
					}
					if(data.custFollows[i].urlList != null && data.custFollows[i].urlList.length > 0){
						var ulength = data.custFollows[i].urlList.length;
						appendCode += "<p class='cfu-list com-none' style='height:20px;line-height:20px;position:relative;'>通话录音：";
						for(var u = 0; u<ulength;u++){
							var call = data.custFollows[i].urlList[u];
							if(call.recordState == 1 &&  call.recordUrl != null  && call.timeLength> 0 && call.recordUrl.indexOf('http:') != -1){
								appendCode += "<span class='video_on shows-video_on' title='"+call.timeShow+"' name='icon-play'  callState='"+call.callState+"' timeLength='"+call.timeLength+"' url='"+call.recordUrl+"' callId='"+call.callId+"' custName='"+call.custName+"' callerNum='"+call.callerNum+"' calledNum='"+call.calledNum+"' ></span>";
							}else if(call.recordState==1 && call.timeLength > 0){
								appendCode += "<span class='video_on shows-video_on' title='"+call.timeShow+"' name='icon-play'  callState='"+call.callState+"' timeLength='"+call.timeLength+"' url='"+$("#playUrl").val()+call.recordUrl+"&d="+call.recordKey+"&id="+call.code+"&compid="+call.orgId+"&calllen="+call.timeLength+"' callId='"+call.callId+"' custName='"+call.custName+"' callerNum='"+call.callerNum+"' calledNum='"+call.calledNum+"' ></span>";
							}
						}
						appendCode += "</p>";
					}
					appendCode = appendCode + /*"<p class='cfu-list com-none'>下次联系时间："+data.custFollows[i].showNextActionDate + followType +"</span></p>"+*/
														"<p class='cfu-list-note com-none'><label class='fl_l'>联系小记：</label><span class='fl_l content_detail'>"+data.custFollows[i].feedbackComment.replace(/\n/g,"<br/>")+"</span></p>"+
														"</div>"+
													"</div>"+
												"</li>";
					timeline_ul.append(appendCode);
				}
				timeline_ul.find("li").eq(length).addClass("end");
				var cfu_list_note = $("p.cfu-list-note");
				var cfu_list_note_label = cfu_list_note.find("label");
				var cfu_list_note_span = cfu_list_note.find(".content_detail");
//				cfu_list_note_span.width(cfu_list_note.width() - cfu_list_note_label.width() - 10);
				
				/*联系小记字数限制*/ 
				cfu_list_note.each(function(){
			    	var $_this = $(this).find('span');
			    	var text = $_this.text();
			    	if(text.length > 100){
				    	$_this.text(text.substr(0,100) + '...');
				    	$_this.append('<div style="width:20px;height:20px;float:none;cursor:pointer;display:inline-block;" class="icon-search-look note_detail" title="查看详情"></div>');
				    	$_this.find(".note_detail").click(function(e){
				    		e.stopPropagation();
				    		viewDetailDivDialog("note_detail_dialog",text);
					    	$("#note_detail_dialog .i-content").css({
						    	"height":"405px",
						    	"overflow-y":"auto"
						    });
				    	});
				    };
			    });
			}
		}
   	});
}


function isNull(value){
	if(value == "" || value == null){
		return false;
	}else{
		return true;
	}
}

function changeImgBlue(obj){
	for(var i=0;i<obj.length;i++){
		var blue = obj.eq(i).attr("src_blue");
		obj.eq(i).attr("src",blue);
	}
	obj.parent().css("cursor","pointer");
}

function changeImgGrey(obj){
	for(var i=0;i<obj.length;i++){
		var grey = obj.eq(i).attr("src_grey");
		obj.eq(i).attr("src",grey);
	}
	obj.parent().css("cursor","default");
}

function custDataInit(custData,custId){
	var length = custData.length;
	var p_b_div = $(".card .p_b_div");
	var p_a = $(".card .p_a");
	if(custId == "" || custId == null){
		p_a.find("i").addClass("img-gray");
	}else{
		p_b_div.html(p_b_div_html);
		p_a.find("i").removeClass("img-gray");
		var p_b_div_hidden = p_b_div.find(".sty-hid");
		
		//下拉箭头是否显示
		var icon_custInfo_more = $(".p_b_div i");
		icon_custInfo_more.hide();
		
		var clientHeight = $(window).height();
		var card = $(".card");
		card.height(clientHeight * 0.27);
		var limit_height = card.height() - 40;
		
		for(var i = 0; i < length; i++){
			var appendCode = "";
			appendCode = "<p class='p_b'><label>"+custData[i].fieldName+"：</label><span title='"+custData[i].fieldValue+"'>"+custData[i].fieldValue+"</span></p>";
			if(custData[i].fieldCode == "sex"){
				if(custData[i].fieldValue == "1"){
					appendCode = "<p class='p_b'><label>"+custData[i].fieldName+"：</label><span title='男'>男</span></p>";
				}else{
					appendCode = "<p class='p_b'><label>"+custData[i].fieldName+"：</label><span title='女'>女</span></p>";
				}
			}
			if(custData[i].fieldCode == "unithome"){
				appendCode = "<p class='p_b'><label>"+custData[i].fieldName+"：</label><span class='span_unithome' title='"+custData[i].fieldValue+"'><a href='###' id='showPubURL'>"+custData[i].fieldValue+"</a></span></p>";
			}
			p_b_div.append(appendCode);
			if($("#showPubURL")){
				$("#showPubURL").unbind("click").click(function(){
					showPublicUrl($("#showPubURL").text());
				});
			}
			var p_b_div_p = p_b_div.find("p:last");
			if(p_b_div.height() > limit_height){
				icon_custInfo_more.show();
				p_b_div_p.appendTo(p_b_div_hidden);
			}else{
				p_b_div_p.insertBefore(p_b_div_hidden);
			}
		}
	}
}

function qqSend(qq){
	$('.qq_iframe').attr('src', "tencent://message/?uin="+qq+"&Site=&menu=yes");
}

function custTypeInit(custTypeList){
	var length = custTypeList.length;
	var custType_list = $(".custType_list");
	custType_list.html("");
	for(var i = 0; i < length; i++){
		var appendCode = "";
		if(custTypeList[i].isDefault == "1"){
			appendCode = "<option value='"+custTypeList[i].optionlistId+"' selected>"+custTypeList[i].optionName+"</option>";
		}else{
			appendCode = "<option value='"+custTypeList[i].optionlistId+"'>"+custTypeList[i].optionName+"</option>";
		}
		custType_list.append(appendCode);
	}
	
	//右上角搜索区域下拉
	var custType_list_search = $(".custType_list_search");
	custType_list_search.html("<option value='' selected>--请选择--</option>");
	for(var i = 0; i < length; i++){
		var appendCode = "";
		appendCode = "<option value='"+custTypeList[i].optionlistId+"'>"+custTypeList[i].optionName+"</option>";
		custType_list_search.append(appendCode);
	}
}

//适用产品
function productTreeInit(suitProcList) {
	var length = suitProcList.length;
	var productTree = $("#productTree");
	var suitProcId = "";
	var suitProcName = "";
	productTree.html("");
	for (var i = 0; i < length; i++) {
		 var appendCode = "";
		 if(suitProcList[i].isDefault == "1"){
			 appendCode = '<li class="selected"><a href="javascript:void(0);"  data-value="' + suitProcList[i].id + '" title="'+ suitProcList[i].name +'">' + suitProcList[i].name +"</a></li>";
			 suitProcId = suitProcId + "," + suitProcList[i].id;
			 suitProcName = suitProcName + "," + suitProcList[i].name;
		 }else{
			 appendCode = '<li><a href="javascript:void(0);"  data-value="' + suitProcList[i].id + '" title="'+ suitProcList[i].name +'">' + suitProcList[i].name +"</a></li>";
		 }
		 productTree.append(appendCode);
/*		var isChecked = false;
		if (suitProcList[i].isDefault == "1") {
			isChecked = true;
		}
		productTree.tree("append", {
			data : [ {
				id : suitProcList[i].id,
				text : suitProcList[i].name,
				checked : isChecked
			} ]
		});*/
	}
	
	$("#suitProcId").val(suitProcId.substring(1));
	if(suitProcName != null && suitProcName != ""){
		productTree.parents(".select").find("dt").text(suitProcName.substring(1)).attr("title",suitProcName.substring(1));
	}else{
		var dt = productTree.parents(".select").find("dt");
		dt.text(dt.data("placeholder")).attr("title",dt.data("placeholder"));
	}
	
/*	var nodes = productTree.tree('getChecked');
	var suitProNameId_text = "";
	var suitProNameId_id = "";
	if (nodes.length > 0) {
		for (var i = 0; i < nodes.length - 1; i++) {
			suitProNameId_id = nodes[i].id + ",";
			suitProNameId_text = nodes[i].text + ",";
		}
		suitProNameId_id += nodes[nodes.length - 1].id;
		suitProNameId_text += nodes[nodes.length - 1].text;
	}
	$("#suitProcId").val(suitProNameId_id);
	if (suitProNameId_text == "") {
		$("#suitProNameId").text("--请选择--");
	} else {
		$("#suitProNameId").text(suitProNameId_text);
	}

	var setSuitPro = function() {
		var nodes = $('#productTree').tree('getChecked', 'checked');
		var proId = "";
		var proName = '';
		$.each(nodes, function(index, obj) {
			proId += obj.id + ",";
			proName += obj.text + ",";
		});
		if (proId != "") {
			proId = proId.substring(0, proId.length - 1);
			proName = proName.substring(0, proName.length - 1);
		} else {
		}
		$('#suitProNameId').html(proName);
		$('#suitProcId').val(proId);
	};

	var closeSuitPro = function() {
		var nodes = $('#productTree').tree('getChecked', 'checked');
		$.each(nodes, function(index, obj) {
			$('#productTree').tree("uncheck", obj.target);
		});
		$('#suitProNameId').html("--请选择--");
		$('#suitProcId').val("");
		$("#manage_drop").hide();
		setShowMsg('suitProcId');
	};

	*//** 选择产品 *//*
	$('#setProId').click(function() {
		setSuitPro();
		setShowMsg('suitProcId');
	});

	$('#clearId').click(function() {
		closeSuitPro();
	});*/
	
	initSuitProductTree(productTree);
}

//适用产品树初始化
function initSuitProductTree(productTree){
	productTree.parents('[data-multi="true"]').each(function() {
		var s = $(this);
		var z = parseInt(s.css("z-index"));
		var dt = s.children("dt");
		var dd = s.children("dd");
		var _show = function() {
			dd.slideDown(200);
			dt.addClass("cur");
			s.css("z-index", z + 1);
		}; //展开效果
		var _hide = function() {
			dd.slideUp(200);
			dt.removeClass("cur");
			s.css("z-index", z);
		}; //关闭效果
		dt.unbind("click").click(function(e) {
			e.stopPropagation();
			dd.is(":hidden") ? _show() : _hide();
		});
		dd.find("a").click(function(e) {
			e.stopPropagation();
			var $target= jQuery(e.currentTarget);
			var field = $target.parents(".select");
            var input= field.data("input")
			if(field.data('multi')==true){
                $target.parents("li").toggleClass("selected");
                field.parents(".bomp-p").find(input).val(
                    $target.parents("ul").find(".selected a").map(function(){
                        return $(this).data('value');
                    }).get().join(",")
				);
                dt.html(
                    $target.parents("ul").find(".selected a").map(function(){
                        return $(this).text();
                    }).get().join(",")
				);
                dt.attr("title",
            		 $target.parents("ul").find(".selected a").map(function(){
                         return $(this).text();
                     }).get().join(",")
                )
                if($target.parents("ul").find(".selected a").length == 0){
					 dt.text(dt.data("placeholder")).attr("title",dt.data("placeholder"));
				}
			}else{
			//	$target.parents("li").siblings().removeClass("selected");
             //   $target.parents("li").toggleClass("selected");
                field.find(input).val($(this).data('value'));
                dt.html($(this).html());
                _hide();
			}
		}); //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）
		
		$(document).click(function(){
			if(!dd.is(":hidden")){
				_hide();
			}
		});
 	});
}


function actionTagInit(labelList){
	var length = labelList.length;
	var tip_box = $(".actionTag .tip-box");
	tip_box.html(tip_box_html);
	var tip_box_hidden = tip_box.find(".sty-hid");
	var icon_down = $(".actionTag>.icon_down");
	icon_down.hide();
	for(var i = 0; i < length; i++){
		var appendCode = "";
		if(labelList[i].isDefault == "1"){
			appendCode = "<a href='###' class='click-hover' name='"+labelList[i].optionId+"'>"+labelList[i].optionName+"</a>";
		}else{
			appendCode = "<a href='###' name='"+labelList[i].optionId+"'>"+labelList[i].optionName+"</a>";
		}
		tip_box.append(appendCode);
		var tip_box_a = tip_box.find("a:last");
		if(tip_box.height() > 25){
			icon_down.show();
			tip_box_a.appendTo(tip_box_hidden);
		}else{
			tip_box_a.insertBefore(tip_box_hidden);
		}
	}
	$(".hyx-sce-area .tip-box a").each(function(){
		var $this = $(this);
		$this.click(function(e){
			e.stopPropagation();
			if($this.hasClass("click-hover") == true){
				$this.removeClass("click-hover");
			}else{
				$this.addClass("click-hover");
			}
			labelStr();
		});
	});
}

function groupActionTagInit(){
	$(".hyx-sce-area").delegate(".choose-tag","click",function(e){
		var labelCodeId = $(this).attr("data-chosen-id")?$(this).attr("data-chosen-id"):"";
		pubDivShowIframe('alert_action_tag_choose',ctx+'/res/tao/toActionTagChoose?optionlistIds='+labelCodeId,'行动标签',610,420);
	});
}

function labelStr() {
	var tempLabel = '';
	var tempLabelName = '';
	$('.tip-box').find('a').each(function(item, obj) {
		if ($(this).hasClass('click-hover') == true) {
			tempLabel = tempLabel + $(this).attr('name') + '#';
			tempLabelName = tempLabelName + $(this).text() + '#';
		}
	});
	$('#labelCodeId').val(tempLabel.replace(/#$/g,""));
	$('#labelNameId').val(tempLabelName.replace(/#$/g,""));
}

function saleProcessInit(saleProcessList){
	var length = saleProcessList.length;
	var saleProcess_list = $(".saleProcess_list");
	saleProcess_list.html("");
	for(var i = 0; i < length; i++){
		var appendCode = "";
		if(saleProcessList[i].isDefault == "1"){
			appendCode = "<option value='"+saleProcessList[i].optionlistId+"' selected>"+saleProcessList[i].optionName+"</option>";
		}else{
			appendCode = "<option value='"+saleProcessList[i].optionlistId+"'>"+saleProcessList[i].optionName+"</option>";
		}
		saleProcess_list.append(appendCode);
	}
	
	//右上角列表搜索区域下拉
	var saleProcess_list_search = $(".saleProcess_list_search");
	saleProcess_list_search.html("<option value='' selected>--请选择--</option>");
	for(var i = 0; i < length; i++){
		var appendCode = "";
		appendCode = "<option value='"+saleProcessList[i].optionlistId+"'>"+saleProcessList[i].optionName+"</option>";
		saleProcess_list_search.append(appendCode);
	}
}

function judgeIsNull(mobilephone,telphone,qq,weChat,email,wangwang){
	var isReplaceWord = $('#idReplaceWord').val();
	$("#custInfo_mobilephone").attr('name',mobilephone);
	$("#custInfo_telphone").attr('name',telphone);
	$("#custInfo_mobilephone").text(mobilephone);
	$("#custInfo_telphone").text(telphone);
	if(isReplaceWord == '1'){
		replaceWord(mobilephone,$("#custInfo_mobilephone"));
		replaceWord(telphone,$("#custInfo_telphone"));
	}
	$("#custInfo_qq").text(qq);
//	$("#custInfo_weChat").text(weChat);
	$("#custInfo_email").text(email);
	$("#custInfo_wangwang").text(wangwang);
	
	if(isNull(mobilephone)){
		var img = $(".custInfo_mobilephone>img");
		changeImgBlue(img);
		$("#custInfo_mobilephone").parent().removeClass("custInfo_noTel");
	}else{
		var img = $(".custInfo_mobilephone>img");
		changeImgGrey(img);
		$("#custInfo_mobilephone").parent().addClass("custInfo_noTel");
	}
	
	if(isNull(telphone)){
		var img = $(".custInfo_telphone>img");
		changeImgBlue(img);
		$("#custInfo_telphone").parent().removeClass("custInfo_noTel");
	}else{
		var img = $(".custInfo_telphone>img");
		changeImgGrey(img);
		$("#custInfo_telphone").parent().addClass("custInfo_noTel");
	}
	
	if(isNull(qq)){
		var img = $(".custInfo_qq>img");
		changeImgBlue(img);
		$(".custInfo_qq").attr("title",qq);
		$(".custInfo_qq").click(function(){
			qqSend(qq);
		});
	}else{
		var img = $(".custInfo_qq>img");
		changeImgGrey(img);
		$(".custInfo_qq").attr("title","");
		$(".custInfo_qq").unbind("click");
	}
		
	var concatId = "";
	var name = "";
	if ($('#state').val() == '1') {
		concatId = $('#concatId').val();
		name = $('#cust_name').val();
	} else {
		concatId = $('#custId').val();
		name = $('#concat_name').val();
	}		
	if (isSet() == '1') {			
		initWxButton(concatId,name);
		/*$(".btn_reBind_weChat").hover(
				function(){
					$(".icon_reBind_weChat").hide();
					$(".icon_reBind_weChat_grey").show();
				},
				function(){
					$(".icon_reBind_weChat").hide();
					$(".icon_reBind_weChat_white").show();
				}
		);*/
		$(".btn_bind_weChat").unbind("click").on("click",function(e){
			e.stopPropagation();
			bindWxChat(concatId,name,"bind");
		});
		$(".btn_reBind_weChat").unbind("click").on("click",function(e){
			e.stopPropagation();
			bindWxChat(concatId,name,"rebind");
		});
		$(".custInfo_weChat").attr("title",name);
		$(".custInfo_weChat").unbind("click").unbind("click").click(function() {
			var $this = $(this);
			if($this.find("img").attr("src").indexOf("icon_white") >= 0){
				bindWxChat(concatId,name,"chat");
			}
		});
	} else {
		$(".custInfo_weChat").attr("title","");
		$(".custInfo_weChat").unbind("click");
	}
	
	
	if(isNull(email)){
		var img = $(".custInfo_email>img");
		changeImgBlue(img);
		$(".custInfo_email").attr("title",email);
		$(".custInfo_email").click(function(){
			emailBysend(name,email);
		});
	}else{
		var img = $(".custInfo_email>img");
		changeImgGrey(img);
		$(".custInfo_email").attr("title","");
		$(".custInfo_email").unbind("click");
	}
	
	if(isNull(wangwang)){
		var img = $(".custInfo_wangwang>img");
		changeImgBlue(img);
		$(".custInfo_wangwang").attr("title",wangwang);
		$(".custInfo_wangwang").unbind('click').click(function(){
			var url  = "http://www.taobao.com/webww/ww.php?ver=3&touid="+wangwang+"&siteid=cntaobao&status=1&charset=utf-8";
			window.open(url); 
		});
	}else{
		var img = $(".custInfo_wangwang>img");
		changeImgGrey(img);
		$(".custInfo_wangwang").attr("title","");
		$(".custInfo_wangwang").unbind("click");
	}
//	必须要优化
	$(".custInfo_mobilephone").unbind('click').click(function(){
		if($("#custInfo_mobilephone").attr('name') != "" && $("#custInfo_mobilephone").attr('name') != null){
			var $this = $(this);
			var index = $this.index();
			if(index == 0){
				setIsLianxi($("#custId").val(),$("#custInfo_mobilephone").attr('name'),$("#concat_name").val(),$("#concat_name").val());
			}else if(index == 1){
				toSmsSendPage($("#cust_name").val(),$("#custInfo_mobilephone").attr('name'));
			}
		}
	});
	
	
	$(".custInfo_telphone").unbind('click').click(function(){
		if($("#custInfo_telphone").attr('name') != "" && $("#custInfo_telphone").attr('name') != null){
			var $this = $(this);
			var index = $this.index();
			if(index == 0){
				setIsLianxi($("#custId").val(),$("#custInfo_telphone").attr('name'),$("#concat_name").val(),$("#concat_name").val());
			}else if(index == 1){
				toSmsSendPage($("#cust_name").val(),$("#custInfo_telphone").attr('name'));
			}
		}
	});
	
	
	function setIsLianxi(custId,phone){
		var custFollowId = $('#followId').val();
		$('#concat_phone').val(phone);
		if(custId==null || custId ==''){
			window.top.iDialogMsg('提示',"资源id 不存在，刷新页面试试！！！");
			return false;
		}
		followCallPhone(phone,custId,$("#cust_name").val(),$("#cust_status").val(),custFollowId,$("#saleProcessId"),$('#concat_name').val(),$("#cust_type").val(),$("#cust_company").val(),$("#concatId").val());
	}
}

function checkLength(chkVal,len){
	var pattern = /[^\x00-\xff]/g;
	return  chkVal.replace(pattern, "gg").length <= len;
}

function setShowMsg(id){
	var isTrue = true;
	var val  = $('#'+id).val();
	if(val== null || val=="" || val == undefined){
		$('#error_'+id).html('必填项');
		$("#bomp_error_"+id).addClass("bomp-error");
		isTrue = false;
	}else{
		$('#error_'+id).html('');
		$("#bomp_error_"+id).removeClass("bomp-error");
	}
	return isTrue;
}

function disabledButton(click_btn){
	var $_this = click_btn;
	$_this.text("请稍候");
//	$_this.attr("disabled","true");
	$_this.css("cursor","not-allowed");
} 

//验证是否为空
function checkIsNull(){
	var isTrue = true;
	$('[checkProp^="chk_"]').each(function() {	
		var $this = $(this);
		var id =$this.attr("id");
		var maxlength = $this.attr("maxlength");
		if($this.val() != null && $this.val() != ""){
			if(maxlength != null && maxlength != ""){
				if($this.val().length > Number(maxlength)/2){
					isTrue = false;
				}
			}else{
				$("#bomp_error_"+id).removeClass("bomp-error");
				$("#error_"+id).text("");
			}
		}else{
			isTrue = false;
			$("#bomp_error_"+id).addClass("bomp-error");
			$("#error_"+id).text("必填项！");
		}
	});
	return isTrue;
}

function saveCustFollow(i){
	// 设值 联系人ID 企业客户才会有该值
	if($("#state").val() == 1){
		var tscidName = $("#concat_name").val();
		var tscMobile = $("#concat_phone").val();
		if(tscidName != null){
			$("#main_tscName").val(tscidName);
		}
		if(tscMobile != null){
			$("#main_tscMobile").val(tscMobile);
		}
	}
	$("#myForm").ajaxSubmit({
		dataType:'json',
		type : 'post',
		error:function(){
			isOpera = true;
			alert(" 请求失败！");},
		success:function(data){
			isOpera = true;
			if(data == 0){
				var tips = "保存成功！";
				// 跳转至下一条
				nextPage();
				if(i == "sign"){ // 签约客户
					if($("#signSetting").val() == "0"){ // 读取签约是否与合同无关 0-需添加合同 1-无需添加合同
						// 弹出新增合同tab
						window.top.addTab(ctx+"/contract/toAdd?module=4&custId="+$("#custId").val()+"&areFrom=1","新增合同");
					}else{
						tips = "签约成功！";
						window.top.iDialogMsg("提示",tips);
					}
				}else{
					window.top.iDialogMsg("提示",tips);
				}			
			}else{
				window.top.iDialogMsg("提示","保存失败！");
				// 跳转至下一条
				nextPage();
			}
			//默认选中  待跟进客户
			$("#follow_tabs").tabs("select",0);
		}
	});	
}

/**跳转至下一资源 */
var nextPage = function(){
	isOpera = true;
	var nextStartPage = $("#nextStartPage").val(); // 下一页
	if(typeof(nextStartPage)!="undefined" && nextStartPage != null && nextStartPage !=0){ // 设值
		$("#startPage").val(nextStartPage);
	}
	var custId = $("#next_custId").val(); // 下一条资源ID
	var custType = $("#custType").val();
	var custCation = $("#custCation").val();
	var planParam = $("#planParam").val();
	var state = $("#state").val();
	var last_option_id = $("#last_option_id").val();
	var last_cust_type = $("#last_cust_type").val();
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
		"custId": custId,
		"custCation":$("#custCation").val(),
		"page.showCount": $("#pp_showCount").val(),
		"page.currentPage": $("#pp_currentPage").val(),
		"isContact": $(".table-check-area input[name=isContact]").val()
	};
	custFollowInfo(custId);
	
	custFollowShowList(custId);
	
	custFollowRightWait(submitData);
	
	RefreshCustFollowPage(custId,planParam,custType,custCation,state,$("#isSelect").val());
};

//移除 验证样式
function removeErrorClass(id){
	if($("#"+id).val() != null && $("#"+id).val() != ""){
		$("#bomp_error_"+id).removeClass("bomp-error");
		$("#error_"+id).text("");
	}
}

//打电话公共方法
function followCallPhone(phone,resCustId,name,status,followId,saleProcess,detailName,custType,company,concatId){
	var arrays = new Array();
		arrays.push("\"custId\":\""+resCustId+"\"");
		arrays.push("\"custName\":\""+name+"\"");
		arrays.push("\"followId\":\""+followId+"\"");
		arrays.push("\"custState\":\""+status+"\"");
		arrays.push("\"custType\":\""+custType+"\"");
	if(saleProcess != null){
		arrays.push("\"saleProcessId\":\""+$(saleProcess).val()+"\"");
		arrays.push("\"saleProcessName\":\""+$.trim($(saleProcess).find("option:selected").text())+"\"");
	}
	if($.trim(detailName) != ""){
		arrays.push("\"define1\":\""+detailName+"\"");
	}
	if($.trim(company)!=""){
		arrays.push("\"define3\":\""+company+"\"");
	}
	if ($('#state').val() == '1') {
		arrays.push("\"define4\":\"" + concatId + "\"");
	}
	window.top.custCallPhone(phone,arrays,resCustId,$('#isConcat').val());
} 
//右上角表格截取公司名称
function td_div_auto(){
	var otherRes_td_div_auto = $(".otherRes_td_div_auto");
	otherRes_td_div_auto.width("156px");
	if(otherRes_td_div_auto){
		var div_width = parseInt(otherRes_td_div_auto.width());
		var span = otherRes_td_div_auto.find("span");
		for(var i=0;i<span.length;i++){
			if(span.eq(i).height() > otherRes_td_div_auto.height()){
				span.eq(i).css("text-align","left");
				var count = Math.floor(div_width / 12);
				var newText = span.eq(i).text().substring(0,count-3) + "...";
				span.eq(i).text(newText);
			}
		}
	}
}

function isSet() {
	return $('#isSet').val();
}
