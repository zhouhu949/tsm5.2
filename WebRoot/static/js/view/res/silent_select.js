$(function(){
	/*表单优化*/
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
    

	//数据标签
	$(".com-moTag").find("a").click(function(){
		$("#noteType").val($(this).attr("nt"));
		$("#days").val("");
		document.forms[0].submit();
	});
	
	//处理自定义时间
	$('#pDate').dateRangePicker({
		showShortcuts:false,
		format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
    	if(obj.date1 != 'Invalid Date'){
    		$('#s_pstartDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#s_pendDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    	}
    	var s = $(this).parents('.select');
    	var dt=s.children("dt");
        var dd=s.children("dd");
        dt.html("自定义");
        $("#oDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
//        s.css("z-index",z);
  });
  
  //转为沉默客户
  $("#silentBtn").click(function(){
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
					setTimeout('document.forms[0].submit()',1000);
				}else{
					window.top.iDialogMsg("提示",'转为沉默客户失败！');
				}
			}
		});
	});
  });
  
  //转为流失客户
  $("#losingBtn").click(function(){
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
		 window.top.iDialogMsg("提示",'非自己签约客户不能转为流失客户！');
		 return;
	  }
	  if(ids.length == 0){
		window.top.iDialogMsg("提示",'请先选择客户！');
		return;
	  }
	  ids=ids.substring(0,ids.length-1);
	  pubDivShowIframe('to_losing',ctx+'/res/cust/toLosing?ids='+ids,'转为流失客户',330,230);
  });
  
});

var setPdate = function(i){
	$('#s_pstartDate').val('');
	$('#s_pendDate').val('');
	$("#oDateType").val(i);
}
