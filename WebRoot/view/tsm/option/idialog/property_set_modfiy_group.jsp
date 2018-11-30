<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--主要样式-->
<title>系统设置-系统属性设置-编辑分组</title>

<script type="text/javascript">
var opera = true;
$(function(){
    $("input:text:first").focus();
     $(".sure-btn").click(function(){
    	 if(opera){  		 
    		 opera = false;
    		 checkIsNull();
    	 }
	});
     $(".cancel-btn").click(function(){
 		closeParentPubDivDialogIframe('alert_sys_set_group');
 	});
});

function submit_(){
	 $('#setForm').ajaxSubmit({
			url : '${ctx}/opt/set/group/modfiy',
			type : 'post',
			error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
			success : function(data) {	
				if(data == 0){			
					window.top.iDialogMsg("提示","保存成功！");
					setTimeout('window.parent.frames[0].document.forms[0].submit();',1000);	
					setTimeout('closeParentPubDivDialogIframe("alert_sys_set_group");',1000);					
				}else{
					window.top.iDialogMsg("提示","保存失败！");
				}
			}
		}); 
}

//验证是否为空
function checkIsNull(){
	var isTrue = true;
	$('[checkProp^="chk_"]').each(function() {
		var id =$(this).attr("id");
		if($(this).val() != null && $.trim($(this).val()) != ""){
			$("#bomp_error_"+id).removeClass("bomp-error");
			$("#error_"+id).text("");
		}else{
			isTrue = false;
			$("#bomp_error_"+id).addClass("bomp-error");
			$("#error_"+id).text("必填项！");
			opera = true;
		}
	});
	if(isTrue){ // 判断排序是否唯一
		var url = "${ctx}/opt/set/group/judgeOnlySort";	
		$.ajax({
				url: url,
				type:'POST',
				data:{'itemCode':$("#itemCode").val(),'sort':$("#sort").val(),'groupId':$("#groupId").val(),'groupName':$("#groupName").val()},
				dataType:'json',
				error:function(){alert("网络异常，请稍后再操作！")},
				success:function(data){
					if(data != 0){
						if(data == 1){
							$("#bomp_error_groupName").addClass("bomp-error");
							$("#error_groupName").text("已存在！");
						}else if(data == 2){
							$("#bomp_error_sort").addClass("bomp-error");
							$("#error_sort").text("已存在！");
						}else if(data > 2){
							$("#bomp_error_groupName").addClass("bomp-error");
							$("#error_groupName").text("已存在！");
							$("#bomp_error_sort").addClass("bomp-error");
							$("#error_sort").text("已存在！");
						}										
						opera = true;
					}else{
						submit_();
					}
				}
			});		
	}
}
</script>
</head>
<body> 
<div class='bomp-cen'>
	<form id="setForm" method="post">
		<input type="hidden" name="groupId" id="groupId" value="${item.groupId}">
		<input type="hidden" name="itemCode"  id="itemCode" value="${itemCode}">
		<div class='bomp-p' id="bomp_error_groupName">
			<label class='lab_a fl_l'><b class="bomp-red">*</b>分组名称：</label>		
			<input type='text'  name="groupName" id="groupName" value='${item.groupName }' maxlength="6" class='ipt_a fl_l' checkProp="chk_"/>
			<span class="error" id="error_groupName"></span>
		</div>
		<div class='bomp-p' id="bomp_error_sort">
			<label class='lab_a fl_l'><b class="bomp-red">*</b>排序值：</label><input type='text' name="sort" id="sort" value='${item.sort}' onblur="this.value = this.value.replace(/[^0-9]/g, '')"  class='ipt_a fl_l' checkProp="chk_"/>
			<span class="error" id="error_sort"></span>
		</div>
		<div class='bomb-btn'>
			<label class='bomb-btn-cen'>
				<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>保存</label></a>
				<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
			</label>
		</div>
	</form>
</div>
</body>
</html>