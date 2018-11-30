$(function(){
	/*switch开关*/
    $('.hyx-sms').find('.switch').click(function(){
    	var $id =$(this).attr('id').split('_')[1];  
    	if($(this).attr('name') == 'on'){
    		$("#dic_"+$id).val("0"); // 设置关闭
    		$(this).addClass('switch-hover');
    		$(this).attr('name','off');
    		$(this).parent().next('.switch-hid').hide();
    	}else{
    		$("#dic_"+$id).val("1"); // 设置开启
    		$(this).removeClass('switch-hover');
    		$(this).attr('name','on');
    		$(this).parent().next('.switch-hid').show();
    	}    	
    });
    
  //提交
	$("#savemanageId").click(function(){	
			$("#myForm").ajaxSubmit({
				url : ctx+'/msg/manage/updateMsgCfg',
				type : 'post',
				dataType : 'JSON',
				error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
				success : function(data) {		
					if(data == 0){
						window.top.iDialogMsg("提示","设置成功！");
						setTimeout('document.forms[0].submit();',1000);	
					}else{
						window.top.iDialogMsg("提示","设置失败！");
					}
				}
			});		
		});
});

    
	

