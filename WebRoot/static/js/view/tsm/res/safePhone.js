$(function(){

   $("input[name=searchOwnerType]").iCheck({
    	radioClass: 'iradio_minimal-green'
    });
    $("input[name=searchOwnerType]").on('ifClicked',function(){
    	var nodes = $('#onwerTree').tree('getChecked');
    	if(nodes.length > 0){
    		$.each(nodes,function(index,obj){
    			 $('#onwerTree').tree("uncheck",obj.target);
    		});
    	}
    });
    
    /*表单优化*/
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
	//模糊处理手机、电话号码
	var idReplaceWord = $("#idReplaceWord").val();
	if(idReplaceWord==1){
		$("[phone=tel]").each(function(idx,obj){
			var phone = $(obj).text();
			if(phone != null && phone != ''){
				replaceWord(phone,$(obj));
				replaceTitleWord(phone,$(obj));
			}
		});
	 }
});

var seleTree=function(){
	var nodes = $('#onwerTree').tree('getChecked', 'checked');
	var accs = "";
	var names = "";
	var userIds = "";
	$.each(nodes,function(index,obj){
		var type = obj.attributes.type;
		if(type == "M"){
			accs+=obj.id+",";
			names+=obj.text+",";
			userIds+=obj.user_id+",";
		}
	});
	if(accs != ""){
		accs=accs.substring(0,accs.length-1);
		names=names.substring(0,names.length-1);
		userIds=userIds.substring(0,userIds.length-1);
		$("#osType").val("3");
	}else{
		var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
		if(searchOwnerType == '1'){
			names="查看全部";
		}else{
			names="查看自己";
		}
		$("#osType").val(searchOwnerType);
	}
	$("#ownerAccsStr").val(accs);
	$("#ownerNameStr").val(names);
	if($("#ownerUserIdsStr").length > 0){
		$("#ownerUserIdsStr").val(userIds);
	}
	
}

var unCheckTree=function(){
	var nodes = $('#onwerTree').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#onwerTree').tree("uncheck",obj.target);
	});
}