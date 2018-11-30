$(function(){
	$(".com-moTag").find("a").click(function(){
		$("#noteType").val($(this).attr("nt"));
		$(this).parent().find(".e-hover").removeClass("e-hover");
		$(this).addClass("e-hover");
		document.forms[0].submit();
	});
	// 列表排序
	//表单优化
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
    		$('#s_startSignDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    		s_t = moment(obj.date1).format('YY.MM.DD');
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#s_endSignDate').val(moment(obj.date2).format('YYYY-MM-DD'));
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
				var oas = $("#ownerAccsStr").val();
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
						$("#ownerNameStr").val("所有者");
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
				var userIds = $("#signAccsStr").val();
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
						$("#inputerName").val("签约人");
					}
				}
			}
		});
    }
    //取消合同
    $("table").delegate("a[id^=cancle_]","click",function(){
    	var id = $(this).attr("id").split("_")[1];
    	var custId = $(this).attr("id").split("_")[2];
    	pubDivShowIframe('cancle_order',ctx+'/contract/toCancle?caid='+id+'&custId='+custId,'取消合同',350,250);
    });
    //编辑合同
    $("table").delegate("a[id^=editcon_]","click",function(){
    	var contractId = $(this).attr("id").split("_")[1];
    	window.top.addTab(ctx+'/contract/toEdit?contractId='+contractId+'&fromPage=1','合同编辑');
    });
    //客户卡片
    $("table").delegate("a[id^=cardInfo_]","click",function(){
    	var custId = $(this).attr("id").split("_")[1];
		var custName = $(this).attr("custName")||"客户卡片";
		window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName);
    });
    //新增订单
    $("table").delegate("a[id^=addOrder_]","click",function(){
    	var id = $(this).attr("id").split("_")[1];
		var custId = $(this).attr("custId");
		var module = $(this).attr("module");
		pubDivShowIframe("add_order",ctx+"/contract/order/addOrder?fromPage=3&module="+module+"&contractId="+id+"&custId="+custId,"新增订单",900,520);
    });
    //查看合同
    $("table").delegate("a[id^=look_]","click",function(){
    	var id = $(this).attr("id").split("_")[1];
		window.top.addTab(ctx+'/contract/toView?contractId='+id,'查看合同');
    });
/*    //取消合同
    $("a[id^=cancle_]").click(function(){
    	var id = $(this).attr("id").split("_")[1];
    	var custId = $(this).attr("id").split("_")[2];
    	pubDivShowIframe('cancle_order',ctx+'/contract/toCancle?caid='+id+'&custId='+custId,'取消合同',350,250);
    });

    //编辑合同
    $("a[id^=editcon_]").click(function(){
    	var contractId = $(this).attr("id").split("_")[1];
    	window.top.addTab(ctx+'/contract/toEdit?contractId='+contractId+'&fromPage=1','合同编辑');
    });

    //客户卡片
	$("a[id^=cardInfo_]").click(function(){
		var custId = $(this).attr("id").split("_")[1];
		var custName = $(this).attr("custName")||"客户卡片";
		window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName);
	});

	//新增订单
	$("a[id^=addOrder_]").click(function(){
		var id = $(this).attr("id").split("_")[1];
		var custId = $(this).attr("custId");
		var module = $(this).attr("module");
		pubDivShowIframe("add_order",ctx+"/contract/order/addOrder?fromPage=3&module="+module+"&contractId="+id+"&custId="+custId,"新增订单",900,520);
	});

	//查看合同
	$("a[id^=look_]").click(function(){
		var id = $(this).attr("id").split("_")[1];
		window.top.addTab(ctx+'/contract/toView?contractId='+id,'查看合同');
	});*/
});
//设置签约日期查询
var setSdate = function(i){
	$('#s_startSignDate').val('');
	$('#s_endSignDate').val('');
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
		}else{
			names="查看自己";
		}
		$("#osType").val(searchOwnerType);
	}
	$("#ownerAccsStr").val(accs);
	$("#ownerNameStr").val(names);
}
//查询条件树 取消
var unCheckTree=function(tid){
	var nodes = $('#'+tid).tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#'+tid).tree("uncheck",obj.target);
	});
}
//组织部门查询条件树 确定
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
		names="签约人";
	}
	$("#signAccsStr").val(accs);
	$("#inputerName").val(names);
}
