$(function(){

	/*表单优化*/
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });

	// 删除
	$('#remove_').click(function(){
			var ids = "";
			$("input[name='check_box']:checked").each(function(){
				ids += $(this).val() + ",";
			});
			if(ids == ""){
				window.top.iDialogMsg("提示","请选择！");
				return false;
			}
			var itemCode = $("input[name='itemCode']").val();
			var data = {ids:ids,itemCode:itemCode};
			window.parent.delete_(data);
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

	// 行动标签、服务标签 分组
	$('#group_c').change(function(){
		$("#groupId_c").val($("#group_c").val());
		$('[name="page.currentPage"]').val("1");
		$("#default_c").val($(this).find("option:checked").data("is-default"));
		document.forms[0].submit();
	});
	
	// 变更分组
	$('#change_').click(function(){
		var ids = "";
		$("input[name='check_box']:checked").each(function(){
			ids += $(this).val() + ",";
		});
		if(ids == ""){
			window.top.iDialogMsg("提示","请选择！");
			return false;
		}
		var itemCode = $("input[name='itemCode']").val();
		window.parent.c_change_group(ids,itemCode);
	});
	
});

//新增
function add_(code){
	window.parent.c_add(code);
}

//修改
function update_(id){
	window.parent.c_update(id);
}

//新增标签
function add_tag_(code,groupId){
	window.parent.c_add_tag(code,groupId);
}

//修改标签
function update_tag_(id,code){
	window.parent.c_update_tag(id,code);
}

//新增
function add_multi_(code){
	window.parent.c_add(code,"multi");
}

//修改
function update_multi_(id){
	window.parent.c_update(id,"multi");
}

// 设置默认
function set_isdefaul(id,code){
	$.post(ctx+'/opt/set/optionSetDefault.do',{'id':id,'itemCode':code},function(data){
		if(data == 0){
			window.top.iDialogMsg("提示","修改成功！");
			setTimeout('document.forms[0].submit();',1000);
		}else{
			window.top.iDialogMsg("提示","修改失败！");
			setTimeout('document.forms[0].submit();',1000);
		}
	},'json');
}
