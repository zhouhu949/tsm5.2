$(function(){
	//合同截止时间
	 $('#pDate').dateRangePicker({
		 showShortcuts:false,
		 format: 'YYYY-MM-DD'
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
	
	//上次联系时间
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
	 
	  //转为流失客户
	  $("#losingBtn").click(function(){
		  var ids = '';
		  $('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				ids+=$(obj).attr('id').split('_')[1]+',';
			}
		  });
		  if(ids.length == 0){
			window.top.iDialogMsg("提示",'请先选择客户！');
			return;
		  }
		  ids=ids.substring(0,ids.length-1);
		  var module = $(this).attr("module");
		  pubDivShowIframe('to_losing',ctx+'/res/cust/toLosing?ids='+ids+'&module='+module,'转为流失客户',420,250);
	  });
	  
	//唤醒客户
	  $("#weekBtn").click(function(){
	     var ids = '';
		 $('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				ids+=$(obj).attr('id').split('_')[1]+',';
			}
		 });
		 if(ids.length == 0){
			window.top.iDialogMsg("提示",'请先选择客户！');
			return;
		 }
		 ids=ids.substring(0,ids.length-1);
		 pubDivDialog("week_cust","是否确认唤醒客户？",function(){
			$.ajax({
				url:ctx+'/res/cust/weekUp',
				type:'post',
				data:{ids:ids},
				dataType:'html',
				error:function(){alert('网络异常，请稍后再试！')},
				success:function(data){
					if(data == 1){
						window.top.iDialogMsg("提示",'唤醒客户成功！');
						refreshPageData(pa.pageParameter());
					}else{
						window.top.iDialogMsg("提示",'唤醒客户失败！');
					}
				}
			});
		});
	  });
	/*****************************管理者界面js********************************/  
	//放弃客户
	$('#giveUpBtn').click(function(){
		var ids = '';
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				ids+=$(obj).attr('id').split('_')[1]+'_'+$(obj).attr('status')+',';
			}
		});
		if(ids.length == 0){
			window.top.iDialogMsg("提示",'请先选择客户！');
			return;
		}
		ids=ids.substring(0,ids.length-1);
		pubDivDialog("res_remove_cust","是否确认放入公海？",function(){
			$.ajax({
				url:ctx+'/res/cust/removeCust',
				type:'post',
				data:{ids:ids,operateType:21,module:"5"},
				dataType:'html',
				error:function(){alert('网络异常，请稍后再试！')},
				success:function(data){
					if(data == 1){
						window.top.iDialogMsg("提示",'放弃客户成功！');
						setTimeout('document.forms[0].submit()',1000);
					}else{
						window.top.iDialogMsg("提示",'放弃客户失败！');
					}
				}
			});
		})
	});
	
	/***
     * 删除
     */
    $("#delBtn").click(function(){
    	var ids = '';
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				ids+=$(obj).attr('id').split('_')[1]+',';
			}
		});
		if(ids.length == 0){
			window.top.iDialogMsg("提示",'请先选择客户！');
			return;
		}
		ids=ids.substring(0,ids.length-1);
		var module = $(this).attr("module");
		pubDivDialog("res_remove_cust","是否确认删除？",function(){
			$.ajax({
				url:ctx+'/res/sea/delBatchCust',
				type:'post',
				data:{ids:ids,module:module},
				dataType:'json',
				error:function(){},
				success:function(data){
					if(data == '1'){
						window.top.iDialogMsg("提示",'删除成功!');
						setTimeout("document.forms[0].submit();",1000);
					}else{
						window.top.iDialogMsg("提示",'删除失败!');
					}
				}
			});
		});
    });
    
    /***
     * 客户再分配
     */
    $("#distributeBtn").click(function(){
    	var ids = '';
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				ids+=$(obj).attr('id').split('_')[1]+',';
			}
		});
		if(ids.length == 0){
			window.top.iDialogMsg("提示",'请先选择客户！');
			return;
		}
		ids=ids.substring(0,ids.length-1);
		var module=$(this).attr("module");
		pubDivShowIframe('distribute', ctx+'/res/sea/toDistribute?ids='+ids+'&module='+module, '重新分配',310,390);
    });
    
    /***
     * 回收客户
     */
    $("#recyleBtn").click(function(){
    	pubDivShowIframe('recyle_cust', ctx+'/res/cust/toRecyleLosingCust','回收客户',330,300);
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

var setGroupName = function(i){
	$("#groupName").val(i);
}