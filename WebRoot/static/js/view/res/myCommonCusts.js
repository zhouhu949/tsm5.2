$(function(){
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
	 //签约时间
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
	 //放弃客户
	 $("a[id^=giveup_]").live("click",function(){
		 var param = $(this).attr("id").split("_")[1]+"_"+$(this).attr("status");
		 pubDivDialog("res_remove_cust","是否确认放入公海？",function(){
				$.ajax({
					url:ctx+'/res/cust/removeCust',
					type:'post',
					data:{ids:param},
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
			}); 
	 });
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