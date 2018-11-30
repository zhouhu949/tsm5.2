$(function(){
	
	//电话号码去重复选框
	$("#r5").change(function(){
		if($(this).attr('checked')){
			$('#dictionaryList_5').removeAttr('disabled');
		}else{
			$('#dictionaryList_5').attr('disabled',true);
		}
	});
	
	//单位名称去重复选框
	$("#r6").change(function(){
		if($(this).attr('checked')){
			$('#dictionaryList_6').removeAttr('disabled');
		}else{
			$('#dictionaryList_6').attr('disabled',true);
		}
	});
	
	//单位主页去重复选框
	$("#r15").change(function(){
		if($(this).attr('checked')){
			$('#dictionaryList_15').removeAttr('disabled');
		}else{
			$('#dictionaryList_15').attr('disabled',true);
		}
	});
	//客户跟进设置去重复选框
	$("#r7").change(function(){
		if($(this).attr('checked')){
			$('#dictionaryList_7').removeAttr('disabled');
			$('#dictionaryList_8').removeAttr('disabled');
		}else{
			$('#dictionaryList_7').attr('disabled',true);
			$('#dictionaryList_8').attr('disabled',true);
		}
	});
	$("#r9").change(function(){
		if($(this).attr('checked')){
			$('#dictionaryList_9').removeAttr('disabled');
		}else{
			$('#dictionaryList_9').attr('disabled',true);
		}
	});
	$("#r13").change(function(){
		if($(this).attr('checked')){
			$('#dictionaryList_13').removeAttr('disabled');
			$('#dictionaryList_14').removeAttr('disabled');
		}else{
			$('#dictionaryList_13').attr('disabled',true);
			$('#dictionaryList_14').attr('disabled',true);
		}
	});
	$("#r16").change(function(){
		if($(this).attr('checked')){
			$('#dictionaryList_16').removeAttr('disabled');
		}else{
			$('#dictionaryList_16').attr('disabled',true);
		}
	});
	$("#r20").change(function(){
		if($(this).attr('checked')){
			$('#dictionaryList_20').removeAttr('disabled');
		}else{
			$('#dictionaryList_20').attr('disabled',true);
		}
	});
	$("#r21").change(function(){
		if($(this).attr('checked')){
			$('#dictionaryList_21').removeAttr('disabled');
		}else{
			$('#dictionaryList_21').attr('disabled',true);
		}
	});
	$("#r22").change(function(){
		if($(this).attr('checked')){
			$('#dictionaryList_22').removeAttr('disabled');
		}else{
			$('#dictionaryList_22').attr('disabled',true);
		}
	});
	//隐藏或显示
	$("input[radio='r']").change(function(){
		var v = $(this).val();
		var target = $(this).attr('id').split('_')[1];
		if(v == 1){
			$('#div_'+target).show(600).slideDown();
		}else if(v == 0){
			$('#div_'+target).hide(600).slideUp();
		}
		
		
	});
	
	//提交
	$("#savemanageId").click(function(){
		var isNull = isInputNull();
		if(isNull){
			alert("输入框中有空值，请核实！！！");
		}else{
			if(distributionSC()){
				document.forms[0].submit();			
			}
		}
	});
	
});
//判断当开启开关时，输入框不能为空。
function isInputNull(){
	var flag = false;
	//选中所有开启的选项
	$("input[id^='on_']:checked").each(function(){
		var num = $(this).attr("id").substring(3);// 下划线后面的数字
		var parentObj = $("#div_" + num ); //  包含输入框和复选框的div
		//获取选中的复选框子类
		var childObj = parentObj.find("input[type='checkbox']:checked");
		if(childObj.length > 0){// 选中的复选框
			childObj.each(function(){
				var childNum = $(this).attr('id').substring(1);
				var $input = $("#dictionaryList_" + childNum );
				//判断是否该子类为文本型,排除'selected' 类型
				if($input.is('input')){
					var value = $.trim($input.val());
					if(value == '' || value == null){
						flag = true;
						return false ;
					}
				}
				//如果是 id='dictionaryList_7', 那么要组装个dictionaryList_8 来判断。
				if(childNum==7){
					var $input = $("#dictionaryList_8");
					//判断是否该子类为文本型
					if($input.is('input')){
						var value = $.trim($input.val());
						if(value == '' || value == null){
							flag = true;
							return false ;
						}
					}
				}
			});
		}
		if(flag){
			return flag;
		}
		var childObj = parentObj.find("input[type='checkbox']");
		//获取不含复选框子类
		if(childObj.length == 0){
			var childObj = parentObj.find("input[type='text']");	
			childObj.each(function(){
				var value = $.trim($(this).val());
				if(value == '' || value == null){
					flag = true;
					return false ;
				}
			});
		}		 //如果含复选框,不处理		
	});
   return 	flag ;
}

//每个帐号每次定量分配的时长   必须  <= 每个帐号每天可分配的时长
function distributionSC(){
	if($('#on_23').is(":checked")){ // 定时分配时长设置为：开启	
		var $input1 = $("#dictionaryList_25").val();// 每个帐号每次定量分配的时长
		var $input2 = $("#dictionaryList_26").val();// 每个帐号每天可分配的时长
		if(parseInt($input2) < parseInt($input1)){
			alert(" 帐号每次定量分配的时长应小于 每天可分配时长！");
			return false ;
		}
	}
	return true;
}
