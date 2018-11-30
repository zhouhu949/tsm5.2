  $(function(){
  		$("#tt1").tree({
		url:ctx+'/orgGroup/get_group_json',
		checkbox:true,
		onLoadSuccess:function(node,data){
			var oas = $("#groupId").val();
			if(oas != null && oas.length > 0 ){
				var text='';
				$.each(oas.split(','),function(index,obj){
					var n = $("#tt1").tree("find",obj);
					if(n != null){
						text+=n.text+',';
						$("#tt1").tree("check",n.target);
					}
				});
				if(text != ''){
					text=text.substring(0,text.length-1);
					$("#groupNameStr").text(text);
				}else{
					$("#groupNameStr").text("-请选择-");
				}
			}
		}
	});
	//选择
	$(".select").find("dt").live("click",function(e){
		e.stopPropagation();
		var isHidden = $(this).next().is(":hidden");
		if(isHidden){
			$(this).next().slideDown(200);
			$(this).next().addClass("cur");
		}else{
			$(this).next().slideUp(200);
			$(this).next().removeClass("cur");
		}
		return false;
	});
	
	//选择部门
	$("#team_output_tree_sure").click(function() {
		var nodes = $('#tt1').tree('getChecked');
		var shares ="";
		var names ="";
		if(nodes != null){
			$(nodes).each(function(index,obj){
				shares+=obj.id.substring(0,obj.id.length)+',';
				names+=obj.text.substring(0,obj.text.length)+';';
			});
		}
		$("#groupNameStr").text(names).attr("title",groupNameStr);
        $('#groupId').val(shares);
	});
	//清空
	$("#team_output_tree_cancel").click(function(){
		var nodes = $('#tt1').tree('getChecked');
		 $(nodes).each(function(index,obj){
        	$('#tt1').tree('uncheck', obj.target);
        })
        $('#groupId').val("");
		 $("#groupNameStr").text("-请选择-").attr("title","-请选择-");
	});
  })
  
// 保存
function submitForm(){
	$('#historyData-outputForm').submit();
	/*var $roleId = $("#roleId").val();
	if($roleId != null && $roleId != ""){
		$('#historyData-outputForm').ajaxSubmit({
			url : $(this).attr("action"),
			type : 'post',
			error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
			success : function(data) {
				if(data == 0){
					window.top.iDialogMsg("提示","操作成功！");
					setTimeout('close02();',10);
				}else{
					window.top.iDialogMsg("提示","操作失败！");
				}
			}
		});
	}*/
}

// 取消
function close02(){
	closeParentPubDivDialogIframe('teamDay_output');
}