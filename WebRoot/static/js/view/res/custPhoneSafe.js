/**
 * 
 */
//输入框模糊处理
function custPhoneSafe(type){
		if(type=='1'){
			$('#telphone_safe,#telphonebak_safe,#mobilephone_safe,#alternatePhone2_safe').each(function(item,obj){
				var id = $(obj).attr('id').split('_')[0];
				var isFomatCheck =$('#'+id).attr('id').indexOf('4'); 
				var content = $(obj).val();
				   replaceWordInput(content,$(obj),isFomatCheck);
			   });
		}
	   $('#telphone_safe,#telphonebak_safe,#mobilephone_safe,#alternatePhone2_safe').focus(function(){
		   var type = $('#idReplaceWord').val();
		   if(type=='1'){
			   $(this).val("");
		   }
	   });
	   $('#telphone_safe,#telphonebak_safe,#mobilephone_safe,#alternatePhone2_safe').blur(function(){
		   $('#error_dp').html('');
		   var id = $(this).attr('id').split('_')[0];
		   var isFomatCheck =$('#'+id).attr('checkDetailProp').indexOf('4');
		   var phone = $(this).val();
		   if(phone==null || phone==''){
			  phone = $('#'+id).val();
		   }
		   $('#'+id).val(phone);
		   if(type=='1'){
			   replaceWordInput(phone,$(this),isFomatCheck);
		   }else{
			   $(this).val(phone);
		   }
			var chkTag = $('#'+id); 			// 待验证元素
			var typeRules = chkTag.attr('checkDetailProp').split('_');
			var chkRule = typeRules[2];		// 验证规则
			chkInput(chkTag,chkRule);		
			
			//除了校验去重，其他的校验隐藏。否则失去焦点要判断其他的字段
			 $('#error'+'_'+id).html('');  
			 $('#' + id).parent().attr('class',"bomp-p p_relative"); // 清除 含有  bomp-error 的样式
	   });	
}