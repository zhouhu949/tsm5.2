$(function(){
	//模糊处理手机、电话号码
	$("[phone=tel]").each(function(idx,obj){
		var phone = $(obj).text();
		if(phone != null && phone != ''){
			replaceWord(phone,$(obj));
			replaceTitleWord(phone,$(obj));
		}
	});
});
