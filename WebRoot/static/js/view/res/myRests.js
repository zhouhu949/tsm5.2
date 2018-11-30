$(function(){
	$("#tt3").tree({
		url:ctx+"/orgGroup/get_all_group_json",
		checkbox:true
	});
	//日计划 加入计划
	$("#addPlanBtn").click(function(e){
		e.stopPropagation();
		var ids="";
		var dateStr = $("#dateStr").val();
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				ids+=$(obj).attr('id').split('_')[1]+",";
			}
		});
		if(ids.length == 0){
			window.top.iDialogMsg("提示",'请先选择资源！');
			return;
		}
		$.post(ctx+"/plan/day/addPlan",{"custIdsStr":ids,"dateStr":dateStr,"type":"res"},function(data){
			if(data.status){
				window.parent.parent.pageSubmit();
				setTimeout('closeParentPubDivDialogIframe("join_custs");',10);
			}else{
				alert(data.errorMsg);
			}
		});
	});
	
	//放弃客户
	$('#giveUpBtn').click(function(){
		var ids = '';
		var operate_auth = true;
		var loginAcc = $("#loginAcc").val();
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				if(loginAcc != $(obj).attr("owner")){
					operate_auth = false;
				}
				ids+=$(obj).attr('id').split('_')[1]+'_'+$(obj).attr('status')+',';
			}
		});
		if(!operate_auth){
			window.top.iDialogMsg("提示",'非自己资源不能放入公海！');
			return;
		}
		if(ids.length == 0){
			window.top.iDialogMsg("提示",'请先选择资源！');
			return;
		}
		ids=ids.substring(0,ids.length-1);
		pubDivDialog("res_remove_cust","是否确认放入公海？",function(){
			$.ajax({
				url:ctx+'/res/cust/removeCust',
				type:'post',
				data:{ids:ids,module:"1"},
				dataType:'html',
				error:function(){alert('网络异常，请稍后再试！')},
				success:function(data){
					if(data == 1){
						window.top.iDialogMsg("提示",'放弃客户成功！');
						refreshPageData(pa.pageParameter());
					}else{
						window.top.iDialogMsg("提示",'放弃客户失败！');
					}
				}
			});
		})
	});
	//设置优先级
	$("#setImpBtn").click(function(){
		setImpOrUnImp(1);
	});
	//取消优先级
	$("#setUnImpBtn").click(function(){
		setImpOrUnImp(0);
	});
	
	//开始拥有时间
	 $('#pDate').dateRangePicker({
		    showShortcuts:false,
	        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
	    }).bind('datepicker-apply',function(event,obj){
	    	var s_t = '';
	    	var e_t = '';
	    	if(obj.date1 != 'Invalid Date'){
	    		$('#s_pstartDate').val(moment(obj.date1).format('YYYY-MM-DD'));
	    		s_t = moment(obj.date1).format('YY.MM.DD');
	    	}
	    	if(obj.date2 != 'Invalid Date'){
	    		$('#s_pendDate').val(moment(obj.date2).format('YYYY-MM-DD'));
	    		e_t = moment(obj.date2).format('YY.MM.DD');
	    	}
	    	var s = $(this).parents('.select');
	    	var dt=s.children("dt");
	        var dd=s.children("dd");
	        dt.html(s_t+'/'+e_t);
	        $("#oDateType").val(5);
	        dd.slideUp(200);
	        dt.removeClass("cur");
//	        s.css("z-index",z);
	 });
	
	//最近联系时间
	 $("#actionDate").dateRangePicker({
		    showShortcuts:false,
	        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
	    }).bind('datepicker-apply',function(event,obj){
	    	var s_t = '';
	    	var e_t = '';
	    	if(obj.date1 != 'Invalid Date'){
	    		$('#s_startActionDate').val(moment(obj.date1).format('YYYY-MM-DD'));
	    		s_t = moment(obj.date1).format('YY.MM.DD');
	    	}
	    	if(obj.date2 != 'Invalid Date'){
	    		$('#s_endActionDate').val(moment(obj.date2).format('YYYY-MM-DD'));
	    		e_t = moment(obj.date2).format('YY.MM.DD');
	    	}
	    	var s = $(this).parents('.select');
	    	var dt=s.children("dt");
	        var dd=s.children("dd");
	        dt.html(s_t+'/'+e_t);
	        $("#lDateType").val(5);
	        dd.slideUp(200);
	        dt.removeClass("cur");
//	        s.css("z-index",z);
	 });
	
	//下次联系时间
	 $("#nDate").dateRangePicker({
		 showShortcuts:false,
		 format: 'YYYY-MM-DD'
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
	        $("#nDateType").val(5);
	        dd.slideUp(200);
	        dt.removeClass("cur");
//	        s.css("z-index",z);
	 });
	 
	 $('#importId').click(function() {
		pubDivShowIframe('import_id', ctx+'/resimp/page?status=2', '导入资源', 740,550);
	 });
	 $('#importResultId').click(function() {
		pubDivShowIframe('import_result_id',  ctx+'/resimp/result', '导入结果', 700, 550);
	 });
	 
	 $("#addResBtn").click(function(){
			$.ajax({
				url:ctx+'/res/resmanage/getResLimitNum',
				type:'post',
				data:{code:'DATA_10028'},
				success:function(data){
					if(data=='1'){
						window.top.iDialogMsg("提示","超出个人拥有资源限制数量！");
						return false;
					}else{
                        pubDivShowIframe('add_cust',ctx+'/res/cust/toAddRes?comFrom=2','添加资源',500,550);
					}
				}
			});		 
	 });
	 $('#startScreen').click(function() {
		pubDivShowIframe('start_screen', ctx+'/phone/toChoose?keyWord=1', '开始筛查', 740,550);
	 });
	 $('#screenResult').click(function() {
		pubDivShowIframe('screen_result_id',  ctx+'/phone/chooseResult?moduleId=1001&ownerAcc='+$('#loginAcc').val(), '筛查结果', 700, 550);
	 });
});

var setImpOrUnImp = function(pr){
	var ids = '';
	$('input[id^=chk_]').each(function(index,obj){
		if($(obj).is(':checked')){
			ids+=$(obj).attr('id').split('_')[1]+',';
		}
	});
	if(ids.length == 0){
		window.top.iDialogMsg("提示",'请先选择资源！');
		return;
	}
	ids=ids.substring(0,ids.length-1);
	window.top.pubDivDialog("res_imp_"+pr,"是否确认"+(pr == 0 ? '取消' : '设置')+"优先级？",function(){
			$.ajax({
				url:ctx+'/res/cust/setImp',
				type:'post',
				data:{ids:ids,isPrecedence:pr},
				dataType:'html',
				error:function(){alert('网络异常，请稍后再试！')},
				success:function(data){
					if(data == 1){
						window.top.iDialogMsg("提示",(pr == 0 ? '取消' : '设置')+'优先级成功！');
						refreshPageData(pa.pageParameter());
					}else{
						window.top.iDialogMsg("提示",(pr == 0 ? '取消' : '设置')+'优先级失败！');
					}
				}
		});
	});
}

var setPdate = function(i){
	$('#s_pstartDate').val('');
	$('#s_pendDate').val('');
	$("#oDateType").val(i);
}

var setActionDate = function(i){
	$('#s_startActionDate').val('');
	$('#s_endActionDate').val('');
	$("#lDateType").val(i);
}

var setNDate = function(i){
	$('#s_startDate').val('');
	$('#s_endDate').val('');
	$("#nDateType").val(i);
}

var seleDeptTree = function(){
	var nodes = $('#tt3').tree('getChecked', 'checked');
	var deptIds = "";
	var names = "";
	$.each(nodes,function(index,obj){
		deptIds+=obj.id+",";
		names+=obj.text+",";
	});
	if(deptIds != ""){
		deptIds=deptIds.substring(0,deptIds.length-1);
		names=names.substring(0,names.length-1);
	}else{
		names="资源录入部门";
	}
	$("#importDeptIdsStr").val(deptIds);
	$("#importDeptNameStr").val(names);
}

var unCheckDeptTree = function(){
	var nodes = $('#tt3').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#'+tid).tree("uncheck",obj.target);
	});
}