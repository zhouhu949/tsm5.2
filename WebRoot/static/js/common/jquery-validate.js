//添加验证方法
jQuery.validator.addMethod("mobile", function(value, element, param) {
	    var bool = /^(0?1[123456789]\d{9})$/.test(value);
	    return bool;
}, jQuery.validator.format("请输入有效手机号码"));
jQuery.validator.addMethod("specialCharacter", function(value, element, param) {
    var bool = !(/[<>!#$%=+\-\"]/.test(value));
    return bool;
}, jQuery.validator.format("请不要输入!#$%=+-<>\""));
jQuery.validator.addMethod("money", function(value, element, param) {
	if(!value){
		var bool = true;
	}else {
		var bool = (/((^[-]?([1-9]\d*))|^0)(\.\d{1,2})?$|(^[-]0\.\d{1,2}$)/.test(value));
	}
    return bool;
}, jQuery.validator.format("请输入正确的货币格式"));
jQuery.validator.addMethod("selectionInputNotSame", function(value, element, param) {
	var bool = true;
	var $element = $(element);
    var listItemNames = $('.form-group-selection input[name^=listItemName_]');
    if(listItemNames.length > 0){
    	listItemNames.each(function(idx,item){
    		var $item = $(item);
			if($element.data('index') != $item.data('index')){
				if($item.val() == value){
					bool = false;
					return;
				}
			}
    	});
    }
    return bool;
}, jQuery.validator.format("选择项内容重复！"));
//自定义提示信息
$.extend($.validator.messages, {
	  required: "必须填写",
	  remote: "请修正此栏位",
	  email: "请输入有效的电子邮件",
	  url: "请输入有效的网址",
	  date: "请输入有效的日期",
	  dateISO: "请输入有效的日期 (YYYY-MM-DD)",
	  number: "请输入正确的数字",
	  digits: "只可输入数字",
	  creditcard: "请输入有效的信用卡号码",
	  equalTo: "你的输入不相同",
	  extension: "请输入有效的后缀",
	  maxlength: $.validator.format("最多 {0} 个字"),
	  minlength: $.validator.format("最少 {0} 个字"),
	  rangelength: $.validator.format("请输入长度为 {0} 至 {1} 之間的字串"),
	  range: $.validator.format("请输入 {0} 至 {1} 之间的数值"),
	  max: $.validator.format("请输入不大于 {0} 的数值"),
	  min: $.validator.format("请输入不小于 {0} 的数值")
}); 

$(function(){
	$("form").each(function(i,obj){
        $(this).validate({
/*          invalidHandler:function(form,validator){
        	  var targetForm = $(form.target)
        	  if(targetForm.hasClass("setting-form") || targetForm.hasClass("msg-dialog-form")){
        		  $.each(validator.invalid,function(key,value){
    				  if(targetForm.hasClass("msg-dialog-form")){
    					  remindMessage(value);
    				  }else{
    					  parentMessage(value);//到时切为弹窗提醒
    				  }
    	              return false
    	          })
        	  }
          },*/
          errorPlacement:function(error,element){
        	 var targetForm = $(element).parents("form");
    		 if (element.is(':radio') || element.is(':checkbox')) { //如果是radio或checkbox
	       		  var eid = element.attr('name'); //获取元素的name属性
	       		  error.appendTo(element.parent().parent()); //将错误信息添加当前元素的父结点后面
	       	 }else {
	       		  $(element).parent().find('span.error').html(error);
	       	 }
          }
       })
    })
});