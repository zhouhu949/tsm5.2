$(function(){
	var initData = removeObjectName(leadData,"page");
	$.ajax({
		url:ctx+"/credit/lead/exportFields",
		data:initData,
		success:function(result){
			if(result.success){
				renderItem(result);
			}
		}
	})
	/*保存*/
	$("#saveResBtn").on("click",function(e){
		e.stopPropagation();
		var form = $(".submit-form");
		var dataStr = dealFormData(form);
		leadData.exportColumnStr = dataStr
		var data = removeObjectName(leadData,"page");
		if($("#totalCount").val() == 0){
			parent.iDialogMsg("提示","当前条件下暂无放款数据，请重新选择条件再进行导出！");
			return false;
		}
		if(!form.serializeArray().length){
			parent.iDialogMsg("提示","请至少选择一个字段！");
			return false;
		}
		
		window.parent.location.href = ctx+"/credit/lead/exportData?" + jQuery.param(data);

		closeParentPubDivDialogIframe('exports_page');
	})
	/*取消*/
	$("#cacleResBtn").on("click",function(e){
		e.stopPropagation();
		closeParentPubDivDialogIframe('exports_page');
	})
	
})

function renderItem(data){
	var myTemplete = Handlebars.compile($("#template").html())
	$(".formItem-box").html(myTemplete(data))
}

function dealFormData(form){
	var arr = form.serializeArray();
	var returnData = []
	arr.forEach(function(it){
		returnData.push(it.name)
	})
	return returnData.join(",")
}

function removeObjectName(obj,name){
	var newObj = obj;
	delete newObj[name]; 
	return newObj;
}