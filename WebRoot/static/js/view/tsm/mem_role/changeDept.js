$(function(){
	var base = $("#base").val();
	$(".expand").click(function(e){
		e.stopPropagation();
		$showme=$(this).nextAll("ul:first");
		$(this).find(".check").attr("checked","true");
		$showme.slideToggle();
	});
	$(".st_tree li").click(function(e){
		e.stopPropagation();
		$(this).find(".check").attr("checked","true");
	});
	
	$("#okBtn").click(function(){
		var checkDept = $("input[name=deptNode]:checked").val();
		if(checkDept == undefined){
			window.top.iDialogMsg("提示","请选择要变更的部门！");
			return;
		}
		var accs = $("#accounts").val();
		var ids = $("#ids").val();
		$.ajax({
			url:ctx+'/auth/user/changeDept.do',
			type:'POST',
			data:{'userAccounts':accs,'deptId':checkDept,'ids':ids},
			dataType:'html',
			error:function(){window.top.iDialogMsg("提示","网络异常，请稍后尝试!");},
			success:function(data){
				if(data == 0){
					setTimeout('window.parent.frames[0].loadData();',300);
					closeParentPubDivDialogIframe('dept_change_');
					window.top.iDialogMsg("提示","保存成功！");
				}else{
					window.top.iDialogMsg("提示","保存失败！");
				}
			}
		});
	});
	
	$("#cancleBtn").click(function(){
		closeParentPubDivDialogIframe('dept_change_');
	});
})