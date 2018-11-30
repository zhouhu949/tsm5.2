$(function(){
	var isSubmit = false;
	
	//保存
	$("#saveBtn").click(function(){
		if(!isSubmit){
			isSubmit = true;
			if(checkForm()){
				$("#deptAddForm").ajaxSubmit({
					error:function(){dialogMsg(-1,"网络异常，请稍后尝试!");isSubmit = false;},
					success:function(data){
						if(data.status){
							window.top.iDialogMsg("提示","保存成功！");
							//setTimeout('window.top.addTab(ctx+"/auth/user/orgMemberList.do","成员编辑");',2000);	
							var editOr = $("#deptId").val() == "" ? false:true;
							closeParentPubDivDialogIframe("dept_edit_");
							if(!editOr){
								var id = $("#parentId").val();
								var name = $("[name='deptName']").val();
								window.parent.ajaxAppendNode(id,name,data.data)
							}else{
								window.parent.changeTreeName(data.data,$("[name='deptName']").val())
							}
							setTimeout(function(){
								window.parent.selectItem(data.data);
							},500)
						}else{
							isSubmit = false;
							window.top.iDialogMsg("提示",data.errorMsg);
						}
					}
				});
			}else{
				isSubmit = false;
			}
		}
	});
	
	//取消
	$("#cancelBtn").click(function(){
		closeParentPubDivDialogIframe('dept_edit_');
	});
	
	var deptId = $("#deptId").val();
	var shrioUserAccount = $("#shrioUserAccount").val();
	var url = ctx+"/orgGroup/get_new_group_json";
	if(shrioUserAccount.indexOf("admin@")>=0){
		url =  ctx+"/orgGroup/get_all_group_json1";
	}
	if(deptId != null && deptId != ""){
		url = ctx+"/orgGroup/get_new_all_group_json?groupId="+deptId;
	}
	$("#deptComboTree").combotree({
		url:url,
	}).combotree("tree").tree({
		onLoadSuccess:function(node,data){
			//移除图标
			$(".tree-icon").remove();
			var pid = $("#parentId").val();
			if(pid == ""){
				pid = "0";
			}
			if(pid != ""){
				pid = pid == "0" ? "DEPT_COMP_ID" : pid;
				$("#deptComboTree").combotree("setValue",pid);
			}
		},
		onLoadError: function(){
			alert("error!");
		},
		onExpand:function(node){
			$(".tree-title").removeClass("tree-folder-open");
		},
		onSelect:function(node){
			var nodeId = node.id;
			if(nodeId == "DEPT_COMP_ID"){
				$("#parentId").val("0");
			}else{
				$("#parentId").val(nodeId);
			}
		}
	});
	
	$("input[name=deptType]").click(function(){
		$("#deptType").val($(this).val());
	});
	

	
});