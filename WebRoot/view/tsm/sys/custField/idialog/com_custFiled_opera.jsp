<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--主要样式-->
<title>系统设置-系统字段设置（企业客户）-添加自定义字段</title>

<script type="text/javascript">
$(function(){
    $("input:text:first").focus();


	// 必填字段 选择事件
	$("#isRequired_check").click(function(){
		if($(this).is(":checked")){
			$("#isRequired").val("1");
		}else{
			$("#isRequired").val("0");
		}
	});
	
	// 只读字段 选择事件
	$("#isRead_check").click(function(){
		if($(this).is(":checked")){
			$("#isRead").val("1");
		}else{
			$("#isRead").val("0");
		}
	});
	
	// 查询条件 选择事件
	$("#isQuery_check").click(function(){
		if($(this).is(":checked")){
			$("#isQuery").val("1");
		}else{
			$("#isQuery").val("0");
		}
	});
    
	// 保存
    $(".sure-btn").click(function(){
    	var $val = $("#fieldName").val();
    	if($val == null ||  $.trim($val) == ""){
    		checkFailCss("字段名称不能为空！",1);
    		return;
    	}
    	$val = $.trim($val);
    	var $oldVal = $("#old_fieldName").val();
    	if($oldVal != null && $oldVal != "" && $.trim($oldVal) == $val){
    		ajaxSubmit_();
    		return;
    	}
    	var url = "${ctx}/custField/com/fieldNameIsExists.do";
    	$.ajax({
    		url : url,
    		data: {'fieldName':$val},
    		type : 'post',
    		async:false,
    		error : function(XMLHttpRequest, textStatus){alert("请求失败！");return ;},
    		success : function(data) {
    			if(data > 0 ){ // 有重复值
    				checkFailCss("该字段重复",1);
    				return ;
    			}else{
    				checkFailCss("",2);	
    				ajaxSubmit_();
    			}
    		}
    	});
	});
	
	$(".cancel-btn").click(function(){
		closeParentPubDivDialogIframe('alert_sys_field_set');
	});

});

function ajaxSubmit_(){
	checkBeforeSub();
	$('#operaForm').ajaxSubmit({
		url : '${ctx}/custField/com/opera.do',
		type : 'post',
		error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
		success : function(data) {	
			if(data == 0){			
				window.top.iDialogMsg("提示","保存成功！");
				window.parent.document.forms[0].submit();
				closeParentPubDivDialogIframe('alert_sys_field_set');
			}else{
				if(data == 4){
					window.top.iDialogMsg("提示","日期类型的自定义字段最多为3个");
				}else if(data == 3){
					window.top.iDialogMsg("提示","无法将字段类型变更为日期类型");
				}else if(data == 5){
					window.top.iDialogMsg("提示","非日期类型的自定义字段最多为15个");
				}else{
					window.top.iDialogMsg("提示","保存失败！");
				}
				
			}
		}
	});
}

//提交前 做一些验证
function checkBeforeSub(){
	// 查询条件
	if($("#isQuery_check").is(":checked")){
		$("#isQuery").val("1");
	}else{
		$("#isQuery").val("0");
	}
	// 只读字段 
	if($("#isRead_check").is(":checked")){
		$("#isRead").val("1");
	}else{
		$("#isRead").val("0");
	}
	// 必填字段
	if($("#isRequired_check").is(":checked")){
		$("#isRequired").val("1");
	}else{
		$("#isRequired").val("0");
	}
}

/**
 * 验证后，样式修改
 * @param text  错误信息
 * @param status  状态 1表示错误
 */
function checkFailCss(text,status){
	if(status == 1){
		$("#bomp_").addClass("bomp-error");
		$("#err_fieldName").text(text);
	}else{
		$("#bomp_").removeClass("bomp-error");
		$("#err_fieldName").text("");
	}

}
</script>
</head>
<body>
<form id="operaForm" method="post">
    <!--isDefined  1: 不是自定义字段  -->
	<input type="hidden" name="fieldId" value="${item.fieldId }">
	<input type="hidden" name="fieldCode" value="${item.fieldCode }">
	<p class='bomp-p bomp-sfs-p skin-minimal'>
		<input type="hidden" id="isRequired" name="isRequired" value="${item.isRequired }">
		<input type="checkbox" class="fl_l" id="isRequired_check" <c:if test="${item.isRequired eq 1 }">checked</c:if> />
		<label class='lab_a fl_l'>必填字段</label>
		<input type="hidden" id="isRead" name="isRead" value="${item.isRead }">
		<input type="checkbox" class="fl_l" id="isRead_check" <c:if test="${item.isRead eq 1 }">checked</c:if> />
		<label class='lab_a fl_l'>只读字段</label>
<%-- 		<input type="hidden" id="isQuery" name="isQuery" value="${item.isQuery }">
		<input type="checkbox" class="fl_l" id="isQuery_check" <c:if test="${ item.fieldCode ne 'companyTrade'  and (isDefined eq 1 or count >= 5)}">disabled="disabled"</c:if><c:if test="${item.isQuery eq 1 }">checked</c:if> />
		<label class='lab_a fl_l'>查询条件</label> --%>
	</p>
	<p class='bomp-p'  id="bomp_">
		<label class='lab_a fl_l'>字段名称：</label><input type='text' id="fieldName" name="fieldName" maxlength="6"  value="${item.fieldName }"  <c:if test="${isDefined eq 1 }">disabled="disabled"</c:if>  class='ipt_a fl_l' />
		<input type="hidden" id="old_fieldName" value="${item.fieldName}" />
		<span class="error" id="err_fieldName"></span>
	</p>
	<p class='bomp-p'>
		<label class='lab_a fl_l'>字段类型：</label>
		<select class="sel_a" name="dataType" <c:if test="${isDefined eq 1 or item.dataType eq 2}">disabled="disabled"</c:if>>
			<option value="1" <c:if test="${item.dataType eq 1 }">selected</c:if>>文本类型</option>
			<option value="2" <c:if test="${item.dataType eq 2 }">selected</c:if>>日期类型</option>
			<option value="3" <c:if test="${item.dataType eq 3 }">selected</c:if>>单选类型</option>
			<option value="4" <c:if test="${item.dataType eq 4 }">selected</c:if>>多选类型</option>
		</select>
	</p>
	<p class='bomp-p'>
		<label class='lab_a fl_l'>是否启用：</label>
		<select class="sel_a" name="enable" <c:if test="${item.fieldCode eq 'name' or item.fieldCode eq 'unithome'}">disabled="disabled"</c:if>>
			<option value="1" <c:if test="${item.enable eq 1 }">selected</c:if>>启用</option>
			<option value="0" <c:if test="${item.enable eq 0 }">selected</c:if>>不启用</option>
		</select>
	</p>
	<p class='bomp-p'>
		<label class='lab_a fl_l'>排序值：</label><input type='text' id="sort"  name="sort"  <c:if test="${item.fieldCode eq 'name'}">readOnly = "true"</c:if> value="${item.sort }" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"  class='ipt_a fl_l' />
		<input type="hidden" name="oldSort" value="${item.sort}" />
	</p>
	<div class="bomb-btn">
			<label class="bomb-btn-cen">
				<a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="javascript:;"><label>确定</label></a>
				<a class="com-btna bomp-btna cancel-btn fl_l" href="javascript:;"><label>取消</label></a>
			</label>
	</div>
</form>
</body>
</html>