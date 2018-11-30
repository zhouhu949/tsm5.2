$(function(){
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
	 if($("#nDate").length > 0){
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
	 }
	 /**************************************客户交接页面***********************************************/
	 //客户交接
	 $("#transferBtn").click(function(){
//		 var ids = '';
//		 $('input[id^=chk_]').each(function(index,obj){
//			if($(obj).is(':checked')){
//				ids+=$(obj).attr('id').split('_')[1]+'_'+$(obj).attr('custType')+'_'+$(obj).attr('ownerAcc')+',';
//			}
//		 });
		 if($('input[id^=chk_]:checked').length == 0){
			window.top.iDialogMsg("提示",'请先选择客户！');
			return;
		 }
//		 ids=ids.substring(0,ids.length-1);
		 pubDivShowIframe('transfer',ctx+'/res/cust/toTransfer','客户交接',600,550);
	 });
	 //销售交接
	 $("#saleTransferBtn").click(function(){
		 pubDivShowIframe('sale_transfer',ctx+'/res/cust/toSaleTransfer','销售交接',600,550);
	 });
})

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

var setCustType = function(i){
	var cm = $('.com-moTag:visible');
	var labelTag = $('#label-tag');
	var cmId = '';
	if(cm.length > 0){
		cmId = cm.attr("id")
	}
	if(i == '0'){
		cm.hide();
		labelTag.hide();
		$('#allLabels').val('');
		$('span[label=1]').remove();
	}else if(i == '1'){
		if(cmId != 'res-tag'){
			cm.hide();
			labelTag.hide();
			$('#allLabels').val('');
			$('span[label=1]').remove();
			$('#res-tag').show();
			$('#res-tag').find('.e-hover').removeClass('e-hover');
			$('#res-tag').find('[nt=1]').addClass('e-hover');
			$('#noteType').val('1');
		}
	}else if(i == '2'){
		if(cmId != 'cust-tag'){
			cm.hide();
			labelTag.show();
			$('#cust-tag').show();
			$('#cust-tag').find('.e-hover').removeClass('e-hover');
			$('#cust-tag').find('[nt=1]').addClass('e-hover');
			$('#noteType').val('1');
		}
	}else if(i == '3'){
		if(cmId != 'sign-tag'){
			cm.hide();
			labelTag.show();
			$('#sign-tag').show();
			$('#sign-tag').find('.e-hover').removeClass('e-hover');
			$('#sign-tag').find('[nt=1]').addClass('e-hover');
			$('#noteType').val('1');
		}
	}else{
		cm.hide();
		labelTag.show();
	}
	$('#s_custType').val(i);
}