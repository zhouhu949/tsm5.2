/**
 * 
 */
$(function(){
	//模糊处理手机、电话号码
	var idReplaceWord = $("#idReplaceWord").val();
	if(idReplaceWord==1){
		$("[phone=tel]").each(function(idx,obj){
			var phone = $(obj).text();
			if(phone != null && phone != ''){
				replaceWordRead(phone,$(obj));
				replaceTitleRead(phone,$(obj));
			}
		});
	 }
})