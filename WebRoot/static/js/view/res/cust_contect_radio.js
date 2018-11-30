$(function(){
	var radios=$(".shows-idialog-middle input:radio");
	var inputshi=$(".shows-idialog-bottom .outofcust");
	var custname=$(".shows-idialog-bottom .xs-name");
	radios.each(function(){
		$(this).change(function(){
			if($(this).prop("checked")){
				inputshi.attr("value",$(this).attr("value"));
				custname.text($(this).attr("value"));
			}
		})
	})
})