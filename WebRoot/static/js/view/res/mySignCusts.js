$(function(){
	
	 $('#importId').click(function() {
		pubDivShowIframe('import_id', ctx+'/resimp/page?status=4', '导入客户', 740, 500);
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
			$.post(ctx+"/plan/day/addPlan",{"custIdsStr":ids,"dateStr":dateStr,"type":"sign"},function(data){
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
	 });
	 //下次联系时间
	 if($('#pDate').length > 0){
		 $('#pDate').dateRangePicker({
			    showShortcuts:false,
		        startDate:getNowFormatDate(),format: 'YYYY-MM-DD'
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
		 });
	 }
	 
	 $("#signDate").dateRangePicker({
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
	        $("#nDateType").val(5);
	        dd.slideUp(200);
	        dt.removeClass("cur");
	 });
	 //沉默客户筛选
	 $("#cmkhBtn").click(function(){
		 var ids = '';
	     var auth_operate = true;
	     var loginAcc = $("#loginAcc").val();
		 $('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				if(loginAcc != $(obj).attr("owner")){
					auth_operate = false;
				}
				ids+=$(obj).attr('id').split('_')[1]+',';
			}
		 });
		 if(!auth_operate){
			 window.top.iDialogMsg("提示",'非自己签约客户不能转为沉默客户！');
			 return;
		 }
		 if(ids.length == 0){
			window.top.iDialogMsg("提示",'请先选择客户！');
			return;
		 }
		 ids=ids.substring(0,ids.length-1);
		 pubDivDialog("silent_cust","是否确认转为沉默客户？",function(){
			$.ajax({
				url:ctx+'/res/cust/changeToSilent',
				type:'post',
				data:{ids:ids},
				dataType:'html',
				error:function(){alert('网络异常，请稍后再试！')},
				success:function(data){
					if(data == 1){
						window.top.iDialogMsg("提示",'转为沉默客户成功！');
						setTimeout(function(){
							refreshPageData(pa.pageParameter());
						},1000);
					}else{
						window.top.iDialogMsg("提示",'转为沉默客户失败！');
					}
				}
			});
		});
//		 window.top.pubDivShowIframe('silent_select',ctx+'/res/cust/silentCustSelect?selectType=1','沉默客户筛选',730,550);
	 });
	 //流失客户筛选
	 $("#lskhBtn").click(function(){
		  var ids = '';
		  var auth_operate = true;
	      var loginAcc = $("#loginAcc").val();
	      var module = $(this).attr("module");
		  $('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				if(loginAcc != $(obj).attr("owner")){
					auth_operate = false;
				}
				ids+=$(obj).attr('id').split('_')[1]+',';
			}
		  });
		  if(!auth_operate){
			 window.top.iDialogMsg("提示",'非自己签约客户不能转为流失客户！');
			 return;
		  }
		  if(ids.length == 0){
			window.top.iDialogMsg("提示",'请先选择客户！');
			return;
		  }
		  ids=ids.substring(0,ids.length-1);
		  pubDivShowIframe('to_losing',ctx+'/res/cust/toLosing?ids='+ids+'&module='+module,'转为流失客户',330,230);
//		 pubDivShowIframe('losing_select',ctx+'/res/cust/silentCustSelect?selectType=2','流失客户筛选',730,550);
	 });
	 //新增合同
//	 $("a[id^=addContract_]").click(function(){
//		var custId = $(this).attr("id").split("_")[1];
//		window.top.addTab(ctx+"/contract/toAdd?custId="+custId,"新增合同");
//	 });
});
var setActionDate = function(i){
	$('#s_startActionDate').val('');
	$('#s_endActionDate').val('');
	$("#lDateType").val(i);
}
var setSignDate = function(i){
	$('#s_startDate').val('');
	$('#s_endDate').val('');
	$("#nDateType").val(i);
}
var setPdate = function(i){
	$('#s_pstartDate').val('');
	$('#s_pendDate').val('');
	$("#oDateType").val(i);
}