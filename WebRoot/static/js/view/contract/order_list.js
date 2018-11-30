$(function(){
	$(".com-moTag").find("a").click(function(){
		$("#noteType").val($(this).attr("nt"));
		$(this).parent().find(".e-hover").removeClass("e-hover");
		$(this).addClass("e-hover");
		document.forms[0].submit();
	});
	
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
	// 列表排序
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
    
    //签约日期
    $('#d1').dateRangePicker({
    	showShortcuts:false,
        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
    	var s_t = '';
    	var e_t = '';
    	if(obj.date1 != 'Invalid Date'){
    		$('#s_startDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    		s_t = moment(obj.date1).format('YY.MM.DD');
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#s_endDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    		e_t = moment(obj.date2).format('YY.MM.DD');
    	}
    	var s = $(this).parents('.select');
    	var dt=s.children("dt");
        var dd=s.children("dd");
        dt.html(s_t+'/'+e_t);
        $("#s_sDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
    });
    if($('#d2').length > 0){
	    //生效日期
	    $('#d2').dateRangePicker({
	    	showShortcuts:false,format: 'YYYY-MM-DD'
	    }).bind('datepicker-apply',function(event,obj){
	    	var s_t = '';
	    	var e_t = '';
	    	if(obj.date1 != 'Invalid Date'){
	    		$('#s_startEffecDate').val(moment(obj.date1).format('YYYY-MM-DD'));
	    		s_t = moment(obj.date1).format('YY.MM.DD');
	    	}
	    	if(obj.date2 != 'Invalid Date'){
	    		$('#s_endEffecDate').val(moment(obj.date2).format('YYYY-MM-DD'));
	    		e_t = moment(obj.date2).format('YY.MM.DD');
	    	}
	    	var s = $(this).parents('.select');
	    	var dt=s.children("dt");
	        var dd=s.children("dd");
	        dt.html(s_t+'/'+e_t);
	        $("#s_eDateType").val(5);
	        dd.slideUp(200);
	        dt.removeClass("cur");
	    });
    }
    if($('#d3').length > 0){
	    //失效日期
	    $('#d3').dateRangePicker({
	    	showShortcuts:false,format: 'YYYY-MM-DD'
	    }).bind('datepicker-apply',function(event,obj){
	    	var s_t = '';
	    	var e_t = '';
	    	if(obj.date1 != 'Invalid Date'){
	    		$('#s_startInvalidDate').val(moment(obj.date1).format('YYYY-MM-DD'));
	    		s_t = moment(obj.date1).format('YY.MM.DD');
	    	}
	    	if(obj.date2 != 'Invalid Date'){
	    		$('#s_endInvalidDate').val(moment(obj.date2).format('YYYY-MM-DD'));
	    		e_t = moment(obj.date2).format('YY.MM.DD');
	    	}
	    	var s = $(this).parents('.select');
	    	var dt=s.children("dt");
	        var dd=s.children("dd");
	        dt.html(s_t+'/'+e_t);
	        $("#s_iDateType").val(5);
	        dd.slideUp(200);
	        dt.removeClass("cur");
	    });
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
    //所有者
    if($("#tt1").length > 0){
    	var ownerUrl = ctx+"/orgGroup/get_group_user_json";
    	if($("#noteType").val() == 2){
    		ownerUrl = ctx+"/orgGroup/get_all_sale_user_json";
    	}
		$("#tt1").tree({
			url:ownerUrl,
			checkbox:true,
			onLoadSuccess:function(node,data){
				if($("#noteType").val() == 2){
		    		$(".shows-allorme-boxs").hide();
		    	}
				$("#tt1").tree("collapseAll");
				for(var i=0;i<data.length;i++){
					$("#tt1").tree("expand",data[i].target);
				}
				var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
				var oas = $("#ownerAccStr").val();
				if(searchOwnerType == '1'){
					$("#ownerNameStr").val("查看全部");
				}else if(searchOwnerType == '2'){
					$("#ownerNameStr").val("查看自己");
				}else if(oas != null && oas != ''){
					var text='';
					$.each(oas.split(','),function(index,obj){
						var n = $("#tt1").tree("find",obj);
						if(n != null){
							text+=n.text+',';
							$("#tt1").tree("check",n.target).tree("expandTo",n.target);
						}
					});
					if(text != ''){
						text=text.substring(0,text.length-1);
						$("#ownerNameStr").val(text);
					}else{
						$("#ownerNameStr").val("提交人");
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
    
    
    //提交人
    if($("#tt2").length > 0){
    	$("#tt2").tree({
			url:ctx+"/orgGroup/get_all_sale_user_json",
			checkbox:true,
			onLoadSuccess:function(node,data){
				$("#tt2").tree("collapseAll");
				for(var i=0;i<data.length;i++){
					$("#tt2").tree("expand",data[i].target);
				}
				var userIds = $("#userIdsStr").val();
				if(userIds != null && userIds != ''){
					var text='';
					$.each(userIds.split(','),function(index,obj){
						var n = $("#tt2").tree("find",obj);
						if(n != null){
							text+=n.text+',';
							$("#tt2").tree("check",n.target).tree("expandTo",n.target);
						}
					});
					if(text != ''){
						text=text.substring(0,text.length-1);
						$("#inputerName").val(text);
					}else{
						$("#inputerName").val("提交人");
					}
				}
			}
		});
    }
    
    
    var is_submit = false;
    $("#delBtn").click(function(){
    	if(!is_submit){
    		is_submit = true;
    		var ids = '';
    		var flag = true;
    		$('input[id^=chk_]').each(function(index,obj){
    			if($(obj).is(':checked')){
    				var state = $(obj).attr('id').split('_')[2];
    				if(state != '2'){
    					ids+=$(obj).attr('id').split('_')[1]+',';
    				}else{
    					flag = false;
    				}
    			}
    		});
    		if(!flag){
    			//window.top.iDialogMsg("提示",'只能对状态为<font color="red"><b>未审核</b></font>或<font color="red"><b>驳回</b></font>的订单执行删除操作！<br />当前选中订单存在非<font color="red"><b>未审核</b></font>或<font color="red"><b>驳回</b></font>状态订单，请重新选择！');
    			window.top.iDialogMsg("提示",'当前选中订单存在非未审核状态订单，请重新选择！');
    			is_submit = false;
    			return;
    		}
    		if(ids.length == 0){
    			window.top.iDialogMsg("提示",'请先选择订单！');
    			is_submit = false;
    			return;
    		}
    		ids=ids.substring(0,ids.length-1);
    		pubDivDialog("confirm_delete_order","确定要执行删除订单操作吗？",function(){
    			$.ajax({
    				url:ctx+'/contract/order/deleteOrder',
    				type:'post',
    				data:{idsStr:ids},
    				dataType:'json',
    				error:function(){is_submit = false;},
    				success:function(data){
    					if(data == '1'){
    						window.top.iDialogMsg("提示","批量删除订单成功!")
    						setTimeout("document.forms[0].submit();",1000);
    					}else{
    						window.top.iDialogMsg("提示","批量删除订单失败!")
    						is_submit = false;
    					}
    				}
    			});
    		},function(){
    			is_submit = false;
    		});
    	}
    });
    
    //导出
    $("#export").click(function(){
//    	pubDivDialog("confirm_delete_order","您确定要导出当前条件下的订单数据吗？",function(){
//    		var param = $("#orderManageForm").serialize();
//    		window.location.href=ctx+"/contract/order/export?"+param;
//		});
    	pubDivShowIframe('export_order',ctx+'/contract/order/export','订单导出',400,350);
    });
    
    $("table").delegate("a[id^=courierView_]","click",function(){
    	var courierNumber = $(this).attr("id").split("_")[1];
    	var orderId = $(this).attr("data-orderId");
    	pubDivShowIframe('courier_view',ctx+'/courier/view?courierNumber='+courierNumber+"&orderId="+orderId,'物流查询',550,350);
    });
   /*****************************************订单审核页面 JS****************************************/
    
    //通过
    $("#passBtn").click(function(){
    	if(!is_submit){
    		is_submit = true;
    		var ids = '';
    		var flag = true;
    		$('input[id^=chk_]').each(function(index,obj){
    			if($(obj).is(':checked')){
    				var state = $(obj).attr('id').split('_')[2];
    				if(state == '1'){
    					ids+=$(obj).attr('id').split('_')[1]+',';
    				}else{
    					flag = false;
    				}
    			}
    		});
    		if(!flag){
    			//window.top.iDialogMsg("提示",'只能对状态为<font color="red"><b>未审核</b></font>的订单执行审核通过操作！<br />当前选中订单存在非<font color="red"><b>未审核</b></font>状态订单，请重新选择！');
    			window.top.iDialogMsg("提示",'当前选中订单存在非未审核状态订单，请重新选择！');
    			is_submit = false;
    			return;
    		}
    		if(ids.length == 0){
    			window.top.iDialogMsg("提示",'请先选择订单！');
    			is_submit = false;
    			return;
    		}
    		ids=ids.substring(0,ids.length-1);
    		pubDivDialog("confirm_check_order","确定要执行通过审核操作吗？",function(){
    			$.ajax({
    				url:ctx+'/contract/order/batchCheck',
    				type:'post',
    				data:{idsStr:ids,authState:2},
    				dataType:'json',
    				error:function(){is_submit = false;},
    				success:function(data){
    					if(data == '1'){
    						window.top.iDialogMsg("提示","批量审核通过成功!")
    						setTimeout("document.forms[0].submit();",1000);
    					}else{
    						window.top.iDialogMsg("提示","批量审核通过失败!")
    						is_submit = false;
    					}
    				}
    			});
    		},function(){
    			is_submit = false;
    		});
    	}
    });
    
    //驳回
    $("#backBtn").click(function(){
    	if(!is_submit){
    		is_submit = true;
    		var ids = '';
    		var flag = true;
    		$('input[id^=chk_]').each(function(index,obj){
    			if($(obj).is(':checked')){
    				var state = $(obj).attr('id').split('_')[2];
    				if(state == '1'){
    					ids+=$(obj).attr('id').split('_')[1]+',';
    				}else{
    					flag = false;
    				}
    			}
    		});
    		if(!flag){
    			//window.top.iDialogMsg("提示",'只能对状态为<font color="red"><b>未审核</b></font>的订单执行审核驳回操作！<br />当前选中订单存在非<font color="red"><b>未审核</b></font>状态订单，请重新选择！');
    			window.top.iDialogMsg("提示",'当前选中订单存在非未审核状态订单，请重新选择！');
    			is_submit = false;
    			return;
    		}
    		if(ids.length == 0){
    			window.top.iDialogMsg("提示",'请先选择订单！');
    			is_submit = false;
    			return;
    		}
    		ids=ids.substring(0,ids.length-1);
    		pubDivDialog("confirm_check_order","确定要执行驳回审核操作吗？",function(){
    			$.ajax({
    				url:ctx+'/contract/order/batchCheck',
    				type:'post',
    				data:{idsStr:ids,authState:3},
    				dataType:'json',
    				error:function(){is_submit = false;},
    				success:function(data){
    					if(data == '1'){
    						window.top.iDialogMsg("提示","批量审核驳回成功!")
    						setTimeout("document.forms[0].submit();",1000);
    					}else{
    						window.top.iDialogMsg("提示","批量审核驳回失败!")
    						is_submit = false;
    					}
    				}
    			});
    		},function(){
    			is_submit = false;
    		});
    	}
    });
    
    //审核
    $("table").delegate("a[id^=check_]","click",function(){
    	var id = $(this).attr("id").split("_")[1];
    	var type = $("#type").val();
    	if(type == '2'){
    		window.parent.pubDivShowIframe('check_order',ctx+'/contract/order/toCheck?orderId='+id+'&type='+type,'订单审核',900,570);
    	}else{
    		pubDivShowIframe('check_order',ctx+'/contract/order/toCheck?orderId='+id,'订单审核',900,570);
    	}
    });
    
    //查看
    $("table").delegate("a[id^=view_]","click",function(){
    	var id = $(this).attr("id").split("_")[1];
    	var type = $("#type").val();
    	if(type == '2'){
    		window.parent.pubDivShowIframe('view_order',ctx+'/contract/order/orderView?orderId='+id,'订单详情',900,550);
    	}else{
    		pubDivShowIframe('view_order',ctx+'/contract/order/orderView?orderId='+id,'订单详情',900,550);
    	}
    });
    
    //编辑订单
    $("table").delegate("a[id^=uporder_]","click",function(){
		var orderId = $(this).attr("id").split("_")[1];
		pubDivShowIframe("edit_order",ctx+"/contract/order/toEdit?fromPage=3&orderId="+orderId,"编辑订单",900,500);
	});
	
	//上报
	$("table").delegate("a[id^=report_]","click",function(){
			var orderId = $(this).attr("id").split("_")[1];
			pubDivDialog("confirm_report_order","是否确认上报？",function(){
				if(!is_submit){
					is_submit = true;
					$.ajax({
						url:ctx+"/contract/order/reportOrder",
						type:"post",
						data:{orderId:orderId},
						dataType:"json",
						error:function(){is_submit = false;},
						success:function(data){
							if(data == "1"){
								window.top.iDialogMsg("提示","上报成功!");
								setTimeout(loadData(),1000);
							}else{
								window.top.iDialogMsg("提示","上报失败!");
								is_submit = false;
							}
						}
					})
				}
			});
	});
	
	//撤回
	$("table").delegate("a[id^=reback_]","click",function(){
			var orderId = $(this).attr("id").split("_")[1];
			pubDivDialog("confirm_report_order","是否确认撤回？",function(){
				if(!is_submit){
					is_submit = true;
					$.ajax({
						url:ctx+"/contract/order/rebackOrder",
						type:"post",
						data:{orderId:orderId},
						dataType:"json",
						error:function(){is_submit = false;},
						success:function(data){
							if(data == "1"){
								window.top.iDialogMsg("提示","撤回成功!");
								setTimeout("document.forms[0].submit();",1000);
							}else{
								window.top.iDialogMsg("提示","撤回失败!");
								is_submit = false;
							}
						}
					})
				}
			});
	});
	
	//作废
	$("table").delegate("a[id^=cancelled_]","click",function(){
			var orderId = $(this).attr("id").split("_")[1];
			pubDivDialog("confirm_report_order","是否确认作废？",function(){
				if(!is_submit){
					is_submit = true;
					$.ajax({
						url:ctx+"/contract/order/cancelledOrder",
						type:"post",
						data:{orderId:orderId},
						dataType:"json",
						error:function(){is_submit = false;},
						success:function(data){
							if(data == "1"){
								window.top.iDialogMsg("提示","作废成功!");
								setTimeout("document.forms[0].submit();",1000);
							}else{
								window.top.iDialogMsg("提示","作废失败!");
								is_submit = false;
							}
						}
					})
				}
			});
	});
});
//设置签约日期查询
var setSdate = function(i){
	$('#s_startDate').val('');
	$('#s_endDate').val('');
	$("#s_sDateType").val(i);
}
//设置生效日期查询
var setEdate = function(i){
	$('#s_startEffecDate').val('');
	$('#s_endEffecDate').val('');
	$("#s_eDateType").val(i);
}
//设置失效日期查询
var setIdate = function(i){
	$('#s_startInvalidDate').val('');
	$('#s_endInvalidDate').val('');
	$("#s_iDateType").val(i);
}
//签约者查询条件树 确定
var seleTree=function(){
	var nodes = $('#tt1').tree('getChecked', 'checked');
	var accs = "";
	var names = "";
	$.each(nodes,function(index,obj){
		var type = obj.attributes.type;
		if(type == "M"){
			accs+=obj.id+",";
			names+=obj.text+",";
		}
	});
	if(accs != ""){
		accs=accs.substring(0,accs.length-1);
		names=names.substring(0,names.length-1);
		$("#osType").val("3");
	}else{
		var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
		if(searchOwnerType == '1'){
			names="查看全部";
		}else if(searchOwnerType == '2'){
			names="查看自己";
		}else{
			names = '所有者';
		}
		$("#osType").val(searchOwnerType);
	}
	$("#ownerAccStr").val(accs);
	$("#ownerNameStr").val(names);
}
//查询条件树 取消
var unCheckTree=function(tid){
	var nodes = $('#'+tid).tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#'+tid).tree("uncheck",obj.target);
	});
}

var seleInputerTree=function(){
	var nodes = $('#tt2').tree('getChecked', 'checked');
	var accs = "";
	var names = "";
	$.each(nodes,function(index,obj){
		var type = obj.attributes.type;
		if(type == "M"){
			accs+=obj.id+",";
			names+=obj.text+",";
		}
	});
	if(accs != ""){
		accs=accs.substring(0,accs.length-1);
		names=names.substring(0,names.length-1);
	}else{
		names="提交人";
	}
	$("#userIdsStr").val(accs);
	$("#inputerName").val(names);
}

function orderCountData(){
	var json = $("#orderManageForm").serializeArray();
	var pdata = new Object();
	$.each(json,function(index,obj){
		pdata[obj.name] = obj.value;
	});
	$.ajax({
		url:ctx+'/contract/order/count',
		type:'post',
		data:pdata,
		dataType:'html',
		error:function(){},
		success:function(data){
			$("#countDiv").html(data);
		}
	});
}