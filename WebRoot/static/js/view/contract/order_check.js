$(function(){
	//模糊处理手机、电话号码
	var idReplaceWord = $("#idReplaceWord").val();
	if(idReplaceWord==1){
		$("[phone=tel]").each(function(idx,obj){
			var phone = $(obj).text();
			if(phone != null && phone != ''){
				replaceWord(phone,$(obj));
			}
		});
	}
	
	//打电话
    $("a[id^=call_]").live('click',function(){
    	var phone = $(this).attr("id").split("_")[1];
    	var custId = $(this).attr("custId");
    	var custName = $(this).attr("custName");
    	var custType = $(this).attr("custType");
    	var custState = $(this).attr("custState");
    	var define1 = $(this).attr("define1");
    	var lastOptionId = $(this).attr("lastOptionId");
    	var lastOptionName = $(this).attr("lastOptionName");
    	var define3 = $(this).attr("define3");
    	var arrays = new Array();
    	arrays.push("\"custId\":\""+custId+"\"");
    	arrays.push("\"custName\":\""+custName+"\"");
    	arrays.push("\"custType\":\""+custType+"\"");
    	arrays.push("\"custState\":\""+custState+"\"");
    	arrays.push("\"define1\":\""+define1+"\"");
    	if(lastOptionId != null && lastOptionId != ''){
    		arrays.push("\"saleProcessId\":\""+lastOptionId+"\"");
    		arrays.push("\"saleProcessName\":\""+lastOptionName+"\"");
    	}
    	if(define3 != null && define3 != ''){
    		arrays.push("\"define3\":\""+define3+"\"");
    	}
    	window.top.custCallPhone(phone,arrays,custId);
    });
    
	var is_submit = false;
	$("#saveBtn").click(function(){
		if(!is_submit){
			is_submit = true;
			var orderId = $("#orderId").val();
			var checkStatus = $("#checkStatus").val();
			var type = $("#type").val();
			if(checkStatus == ""){
				window.top.iDialogMsg("提示","请选择审核结果!");
				return;
			}
			var checkContext = $("#checkContext").val();
			if(checkContext != null && checkContext.length > 150){
				window.top.iDialogMsg("提示","审核备注最多只能输入150个汉字!");
				return;
			}
			$.ajax({
				url:ctx+"/contract/order/saveCheck",
				type:'post',
				data:{id:orderId,authState:checkStatus,authDesc:checkContext},
				dataType:'json',
				error:function(){is_submit = false;},
				success:function(data){
					if(data == "1"){
						window.top.iDialogMsg("提示","审核订单成功!");
						if(type == "2"){
							setTimeout("window.parent.$('#iframepage')[0].contentWindow.$('#cform').submit();closeParentPubDivDialogIframe('check_order');",1000);
						}else{
							setTimeout("window.parent.document.forms[0].submit();",1000);
						}
					}else{
						window.top.iDialogMsg("提示","审核订单失败!");
						is_submit = false;
					}
				}
			});
		}
	});
	
	$("#cancleBtn").click(function(){
		closeParentPubDivDialogIframe('check_order');
	})
});
