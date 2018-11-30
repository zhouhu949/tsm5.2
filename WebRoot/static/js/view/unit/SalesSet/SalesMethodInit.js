var base = "";
$(function(){
	base = $.trim($("#base").val());
	$("#checkAll").click(function() {
		//复选框全选 
		if ($(this).is(":checked")) {
			$("input[name='selectboxs']").each(function() {
				$(this).attr("checked", true);
			});
		} else {
			//复选框取消全选 
			$("input[name='selectboxs']").each(function() {
				$(this).attr("checked", false);
			});
		}
	});
	
	//应用子功能项开关
	$('input[name="subBtn"]').change(function(){
		var val = $(this).val().split("_")[1];
		var id = $(this).val().split("_")[0];
		var url = base+"/option/updateSubOpt.do";
		$.post(url,{'val':val,'id':id},function(data){
			//之间逻辑...
		},'text');
	});
	
	//设置默认值或取消默认值
	$("a[id^='def-']").click(function(){
		var array = $(this).attr("id").split("-");
		var id = array[2];
		var code = array[1];
		var flg = array[3];
		$("input[name='id']").val(id);
		$("input[name='code']").val(code);
		$("input[name='flg']").val(flg);
		document.forms[0].action = base+"/option/setDefault.do";
		document.forms[0].submit();
	});
	
	//客户放弃原因
	$("a[id^='find_']").click(function(){
		var id = $(this).attr('id').split('_')[1];
		openNewDialogByUrl('','添加子项',base+'/option/jump.do?target=3&id='+id);
	});
});

//删除
function toDelete(){
	var idsCount = $("input[name='selectboxs']:checked").length;
	if(idsCount==0){
		dialogMsg(1);
		return;
	}
	var ids = "";
	$("input[name='selectboxs']:checked").each(function(){
		ids += $(this).val() + ",";
	});
	dialogMsg(2,'',function(){
		document.forms[0].action = base + "/option/optionDel.do?ids="+ids+"&target="+1;
		document.forms[0].submit();
	});
}
//新增或编辑
function toAddOrEdit(itemCode,type,optionId){
	var title = "";
	if(itemCode == ''){
		alert("请选中码表设置列表项然后再操作!");
		return;
	}
	switch (itemCode) {
	case 'SALES_10001':
		title = type == '0'?'添加销售进程设计':'修改销售进程设计';
		break;
	case 'SALES_10002':
		title = type == '0'?'添加目标客户分类':'修改目标客户分类';
		break;
	case 'SALES_10003':
		title = type == '0'?'添加自定义销售线索':'修改自定义销售线索';
		break;
	case 'SALES_10004':
		title = type == '0'?'添加反馈信息维护':'修改反馈信息维护';
		break;
	case 'SALES_10005':
		title = type == '0'?'添加销售产品维护':'修改销售产品维护';
		if(type == '0'){
			$.dialog.open(base+"/view/unit/SalesSet/SalesMethodAddOrEdit.jsp?itemCode="+itemCode,{title:title,id:'SalesAdd',lock:true,resize:false,background:'#F0F0F0',opacity: 0.0});
		}else if(type == '1'){
			$.dialog.open(base+"/option/jump.do?option.optionlistId="+optionId+"&target=1",{title:title,id:'SalesEdit',lock:true,resize:false,background:'#F0F0F0',opacity: 0.0});
		}
		return;
		break;
	case 'SALES_10006':
		title = type == '0'?'添加客户放弃原因':'修改客户放弃原因';
		break;
	case 'RECORD_1000':
		title = type == '0'?'添加录音示范分类':'修改录音示范分类';
		if(type == '0'){
			$.dialog.open(base+"/view/unit/recordSet/recordCaseAddOrEdit.jsp?itemCode="+itemCode,{title:title,id:'recordCaseAdd',lock:true,resize:false,background:'#F0F0F0',opacity: 0.0});
		}else if(type == '1'){
			$.dialog.open(base+"/option/jump.do?option.optionlistId="+optionId+"&target=2",{title:title,id:'recordCaseEdit',lock:true,resize:false,background:'#F0F0F0',opacity: 0.0});
		}
		return;
		break;
	}
	if(type == '0'){
		$.dialog.open(base+"/view/unit/SalesSet/SalesMethodAddOrEdit.jsp?itemCode="+itemCode,{title:title,id:'SalesAdd',lock:true,resize:false,background:'#F0F0F0',opacity: 0.0});
	}else if(type == '1'){ 
		$.dialog.open(base+"/option/jump.do?option.optionlistId="+optionId+"&target=1",{title:title,id:'SalesEdit',lock:true,resize:false,background:'#F0F0F0',opacity: 0.0});
	}
}