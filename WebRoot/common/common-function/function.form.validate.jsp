<%@ page language="java" pageEncoding="UTF-8"%>

<script type="text/javascript" src="${ctx}/static/thirdparty/jquery.validation/dist/jquery.validate.js${_v}"></script>
<script type="text/javascript">
//添加验证方法
jQuery.validator.addMethod("mobile", function(value, element, param) {
	    var bool = /^(0?1[123456789]\d{9})$/.test(value);
	    return bool;
}, jQuery.validator.format("请输入有效手机号码"));
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
//添加验证
$(function(){
    $(".validate-form").each(function(i,obj){
        $(this).validate({
          invalidHandler:function(form,validator){
            $.each(validator.invalid,function(key,value){
              console.log(key,value)//到时切为弹窗提醒 
              return false
            })

          },
          errorPlacement:function(error,element){

          }
       })
    })
    //提交防暴力点击
    $(".validate-form").on('submit',function(e){
         var targetForm=$(this);
         if(targetForm.valid()){
             targetForm.find('[type=submit]').addClass('disabled');
             setTimeout(function(){
                 targetForm.find('[type=submit]').removeClass('disabled');
             },1000);
         }
     });
     $(".validate-form").on("click","[type='submit'].disabled",function(e){
        e.stopPropagation();
        e.preventDefault();
     })
})
</script>