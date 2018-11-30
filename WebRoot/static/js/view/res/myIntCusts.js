$(function(){
	 $('#importId').click(function() {
		pubDivShowIframe('import_id', ctx+'/resimp/page?status=3', '导入客户', 740, 500);
	 });
	 $('#importResultId').click(function() {
		pubDivShowIframe('import_result_id',  ctx+'/resimp/result', '导入结果', 700, 550);
	 });
	 //设置共有者 todo
	 $("#addCommonOwner").on("click",function(e){
		var ids = '';
		var operate_auth = true;
		var loginAcc = $("#loginAcc").val();
//		var issys = $("#issys").val();
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
//				if(loginAcc == $(obj).attr("commonAcc")){
//					operate_auth = false;
//				}
				ids+=$(obj).attr('id').split('_')[1]+"_"+$(obj).attr("owner")+",";
			}
		});
//		if(!operate_auth){
//			window.top.iDialogMsg("提示",'非自己客户不能操作！');
//			return;
//		}
		if(ids.length == 0){
			window.top.iDialogMsg("提示",'请先选择客户！');
			return;
		}
		ids=ids.substring(0,ids.length-1);
		pubDivShowIframe('add_common_owner',ctx+'/res/cust/toCommonOwnerSet?ids='+ids,'设置共有者',600,450);
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
			$.post(ctx+"/plan/day/addPlan",{"custIdsStr":ids,"dateStr":dateStr,"type":"will"},function(data){
				if(data.status){
					window.parent.parent.pageSubmit();
					setTimeout('closeParentPubDivDialogIframe("join_custs");',10);
				}else{
					alert(data.errorMsg);
				}
			});
		});
	 
	 //取消共有者
	 $("#cancleCommonOwner").click(function(){
		 var ids = '';
		 $('input[id^=chk_]').each(function(index,obj){
				if($(obj).is(':checked') && $(obj).attr("commonAcc") != null && $(obj).attr("commonAcc") != ''){
					ids+=$(obj).attr('id').split('_')[1]+"_"+$(obj).attr("owner")+",";
				}
		 });
		 if(ids.length == 0){
			window.top.iDialogMsg("提示",'请先选择已经设置共有者的客户！');
			return;
		 }
		 ids=ids.substring(0,ids.length-1);
		 pubDivDialog("cancle_common_owner","是否取消该共有客户？",function(){
			 $.ajax({
				 url:ctx+'/res/cust/setCommonOwner',
				 type:'post',
				 data:{ids:ids,commonAcc:"",commonName:""},
				 dataType:'html',
				 error:function(){alert('网络异常，请稍后再试！')},
				 success:function(data){
					if(data == '0'){
						window.top.iDialogMsg("提示",'取消共有者成功！');
						refreshPageData(pa.pageParameter());
					}else{
						window.top.iDialogMsg("提示",'取消共有者失败！');
					}
				 }
			 });
		 });
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
	
	//放弃客户
	$('#giveUpBtn').click(function(){
		var ids = '';
		var operate_auth = true;
		var loginAcc = $("#loginAcc").val();
		var commonOnwerGiveUp = $("#commonOnwerGiveUp").val();
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				if(loginAcc == $(obj).attr("owner") || (loginAcc == $(obj).attr("commonAcc") && commonOnwerGiveUp == 1)){
					ids+=$(obj).attr('id').split('_')[1]+'_'+$(obj).attr('status')+',';
				}else {
					operate_auth = false;
				}
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
				data:{ids:ids,module:"2"},
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
});


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
