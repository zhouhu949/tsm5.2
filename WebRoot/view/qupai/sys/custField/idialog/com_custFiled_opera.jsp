<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--主要样式-->
<title>系统设置-系统字段设置（企业客户）-添加自定义字段</title>
<style type="text/css">
	.form-group-selection {
		position: relative;
		padding-left: 100px;
		margin-bottom: 5px;
	}
	
	.form-group-selection .btn-remove-option {
		position: absolute;
		right: -36px;
		top: 0;
		padding: 0 4px;
	}
	
	.form-group-selection .isDefault {
		position: absolute;
		right: -8px;
		top: 3px;
	}
	
	.bomp-selections .head {
		position: relative;
	}
	.bomp-selections .head .isDefault-title {
		position: absolute;
		right: -18px;
		top: 5px;
	}
	
	.edit-dataType {
		display: none;
	}
	.calc-input {
		width: 60px;
	}
	span.error {
		font-size: 12px;
		color: #f00;
	}
</style>
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
    	}else if(/[<>!#$%=+\-\"]/.test($val)){
    		checkFailCss("请不要输入!#$%=+-<>\"",1);
    		return;
    	}
    	$val = $.trim($val);
    	var $oldVal = $("#old_fieldName").val();
    	if($oldVal != null && $oldVal != "" && $.trim($oldVal) == $val){
    		ajaxSubmit_();
    		return;
    	}
    	var url = "${ctx}/custField/quPai/fieldNameIsExists.do";
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
	$('select[name="dataType"]').attr("disabled",false);
	var form_serializeObject = $('#operaForm').serializeObject();
	var form_group_selection = $('.form-group-selection');
	if(form_group_selection.length>0){
		var dataJson = [];
		form_group_selection.each(function(idx,item){
			var $item = $(item);
			var optionlistId = $item.find('.optionlistId').val();
			var optionName = $item.find('.optionName').val();
			var isDefault = $item.find('.isDefault')[0].checked?1:0;
			dataJson.push({
				optionlistId:optionlistId,
				optionName:optionName,
				isDefaultValue:isDefault
			});
		});
		form_serializeObject.dataJson = dataJson;
	}
	if($('#operaForm').valid()){
		$.ajax({
			url : '${ctx}/custField/quPai/opera.do',
			type : 'post',
			data: form_serializeObject,
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
					//$('select[name="dataType"]').attr("disabled",true);
				}
			},
			error : function(XMLHttpRequest, textStatus){
				alert("请求失败！");
				$('select[name="dataType"]').attr("disabled",true);
			}
		});
	}
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
		<select class="sel_a" name="dataType" <c:if test="${isEditPage eq 1}">disabled="disabled"</c:if>>
			<option value="1" <c:if test="${item.dataType eq 1 }">selected</c:if>>文本类型</option>
			<option value="2" <c:if test="${item.dataType eq 2 }">selected</c:if>>日期类型</option>
			<option value="3" <c:if test="${item.dataType eq 3 }">selected</c:if>>单选类型</option>
			<option value="4" <c:if test="${item.dataType eq 4 }">selected</c:if>>多选类型</option>
			<option value="5" <c:if test="${item.dataType eq 5 }">selected</c:if>>金额</option>
			<option value="6" <c:if test="${item.dataType eq 6 }">selected</c:if>>引用类型</option>
			<option value="7" class="edit-dataType" <c:if test="${item.dataType eq 7 }">selected</c:if>>计算类型</option>
			<option value="8" class="edit-dataType" <c:if test="${item.dataType eq 8 }">selected</c:if>>计算类型</option>
			<option value="9" class="edit-dataType" <c:if test="${item.dataType eq 9 }">selected</c:if>>计算类型</option>
			<option value="10" class="edit-dataType" <c:if test="${item.dataType eq 10 }">selected</c:if>>图片</option>
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
	
	<c:if test="${item.dataType eq 3 || item.dataType eq 4 }">
	<div class='bomp-p bomp-selections'>
		<div class="head">
			<label class='lab_a'>选择项：</label>
			<button type="button" class="btn-add-option">+添加</button>
			<span class="isDefault-title">默认</span>
		</div>
		<c:forEach items="${optionList }" var="item_optionList" varStatus="v">
			<div class="form-group-selection">
				<input type='text' class="optionName" name="listItemName_${v.index }" data-index="${v.index }" value="${item_optionList.optionName }" data-rule-required="true" data-rule-selectionInputNotSame="true" maxlength="10" class='ipt_a' />
				<input type='hidden' class="optionlistId" value="${item_optionList.optionlistId }" />
				<input type='checkbox' class="isDefault" <c:if test="${item_optionList.isDefault == 1 }">checked</c:if> />
				<button type="button" class="btn-remove-option">-</button>
				<span class="error"></span>
			</div>
		</c:forEach>
	</div>
	</c:if>
	
	<c:if test="${item.dataType eq 6 }">
	<p class='bomp-p ref-selections' >
		<label class='lab_a fl_l'>引用选择：</label>
		<select class="sel_a" name="fieldValue">
		<optgroup label="${hyxItem.fileName}">
			<c:forEach items="${hyxItem.children}" var="item_child" varStatus="v">
				<option value="${item_child.fieldCode }" <c:if test="${item.fieldValue == item_child.fieldCode }" >selected</c:if> >${item_child.fieldName }</option>
			</c:forEach>
		</optgroup>
		</select>
	</p>
	</c:if>
	
	<c:if test="${item.dataType eq 8 }">
	<p class='bomp-p'>
		<label class='lab_a'>计算设置：</label>
		<span>等于{借款金额}-{各项服务费}</span>
	</p>
	</c:if>
	
	<c:if test="${item.dataType eq 7 }">
	<p class='bomp-p'>
		<label class='lab_a'>计算设置：</label>
		<span>等于{借款金额}*</span><input type="text" class="calc-input" name="fieldValue" value="${item.fieldValue }" /><span>%</span>
	</p>
	</c:if>
	
	<c:if test="${item.dataType eq 9 }">
	<p class='bomp-p'>
		<label class='lab_a'>计算设置：</label>
		<span>等于{放款日}+</span><input type="text" class="calc-input" name="fieldValue" value="${item.fieldValue }" />
	</p>
	</c:if>
	
	<div class="bomb-btn">
			<label class="bomb-btn-cen">
				<a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="javascript:;"><label>确定</label></a>
				<a class="com-btna bomp-btna cancel-btn fl_l" href="javascript:;"><label>取消</label></a>
			</label>
	</div>
</form>
<script src="${ctx}/static/thirdparty/jquery.validation/dist/jquery.validate.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/js/common/jquery-validate.js" type="text/javascript" charset="utf-8"></script>
<script>
	$(function(){
		$('#operaForm').delegate('.btn-remove-option','click',function(e){
			var $target = $(e.currentTarget);
			$target.parents('.form-group-selection').remove();
		});
		$('#operaForm').delegate('.isDefault','change',function(e){
			var target = e.currentTarget;
			var $target = $(target);
			if(target.checked){
				$target.parent().siblings('.form-group-selection').find('.isDefault').attr('checked',false);
			}
		});
		
		$('select[name="dataType"]').on('change',function(e){
			var $target = $(e.currentTarget);
			$('.bomp-selections,.ref-selections').remove();
			if($target.val() == 3 || $target.val() == 4){
				var target_html = `<div class='bomp-p bomp-selections'>
										<div class="head">
											<label class='lab_a'>选择项：</label>
											<button type="button" class="btn-add-option">+添加</button>
											<span class="isDefault-title">默认</span>
										</div>
									</div>`;
				$('.ref-selections').remove();
				$(target_html).insertAfter('.bomp-p:last');
			}else if($target.val() == 6){
				var target_html = `<p class='bomp-p ref-selections' >
										<label class='lab_a fl_l'>引用选择：</label>
										<select class="sel_a" name="fieldValue">
										<optgroup label="${hyxItem.fileName}">
											<c:forEach items="${hyxItem.children}" var="item_child" varStatus="v">
												<option value="${item_child.fieldCode }" <c:if test="${item.fieldValue == item_child.fieldCode }" >selected</c:if> >${item_child.fieldName }</option>
											</c:forEach>
										</optgroup>
										</select>
									</p>`;
				$(target_html).insertAfter('.bomp-p:last');
			}
		});
	});
</script>
<script type="text/javascript" src="${ctx }/static/js/view/qupai/custField-operate.js${_v}"></script>
</body>
</html>