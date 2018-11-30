/***********************************************
 * 添加修改资源验证
 * @author haoqj
 * @date 2013-11-15
 ***********************************************/

var err_not_empty = '必填项';
var err_over_maxlen = '超出最大长度';
var err_not_number = '必须为数字';
var err_err_format = '格式不正确';
var err_is_exist = '该数据已存在';
var err_all_empty = '同时为空';
var err_over_Char = '非法字符';


var phoneValiDateType = 1;
var unitValiDateType = 0;
var unitHomeValiDateType = 0;
var errorArray =  new Array(); 
$(function(){
	phoneValiDateType = $("#pValiDateType").val() == ''?1:$("#pValiDateType").val();
	unitValiDateType = $("#uValiDateType").val() == ''?0:$("#uValiDateType").val();
	unitHomeValiDateType = $("#uhValiDateType").val() == ''?0:$("#uhValiDateType").val();
	/**
	 * 表单元素操作时验证
	 * checkProp = 'chk_验证类型_验证规则'
	 * 
	 * 验证类型： 1：可写入文本框; 2: 下拉框; 3: 单选、复选;4：文件类型文本框; 注：验证类型为1以外时只验证是否为空
	 * 验证规则： ab a:是否为空(a=1验证); b:正则验证(1: 纯数字;2：手机号码;3:固话号码;4:手机或固话;5:电子邮箱;6,7,8...可自行添加)
	 * 如：
	 * <input id="name" checkProp='chk_1_103' maxlength="20" type="text"/>
	 * checkProp='chk_1_13': 验证可写入文本框,非空、固话
	 * checkProp='chk_3_1': 验证单选、复选, 非空
	 */
	$('[checkDetailProp^="chk_"]').each(function() {
		var chkTag = $(this); 			// 待验证元素
		var typeRules = $(this).attr('checkDetailProp').split('_');
		var chkType = typeRules[1];		// 验证类型
		var chkRule = typeRules[2];		// 验证规则
		if(chkType=='1'){ // 通过blur事件验证
			chkTag.change(function(){
				var isPass = true;
				if(isPass){
					chkInput(chkTag,chkRule);
				}
			});
		}else if(chkType=='2'){ // 通过blur事件验证
			chkTag.change(function(){
				chkSelect(chkTag,chkRule);
			});
		}else if(chkType=='3'){ // 通过change事件验证
			chkTag.change(function(){
				chkChange(chkTag,chkRule);
			});
		}else if(chkType=='4'){ // 通过blur事件验证
			chkTag.change(function(){
				chkFileInput(chkTag,chkRule);
			});
		}
	});
});

/**
 * 表单元素提交验证(验证规则同上)
 * true: 通过; false: 不通过
 */
function checkForm(){
	errorArray =  new Array(); 
	var isFail = false;
	var errCount = 0;
	var firstErrTag = null ;
	$('[checkDetailProp^="chk_"]').each(function() {
		var chkTag = $(this); 			// 待验证元素
		var typeRules = $(this).attr('checkDetailProp').split('_');
		var chkType = typeRules[1];		// 验证类型
		var chkRule = typeRules[2];		// 验证规则
		
		if(chkType=='1'|| chkType=='5'){
			if(!chkInput(chkTag,chkRule)){	
				isFail = true;
				errCount = errCount + 1;
				if(errCount == 1)firstErrTag = chkTag;
			}
		}else if(chkType=='2'){
			if(!chkSelect(chkTag,chkRule)){
				isFail = true;
				errCount = errCount + 1;
				if(errCount == 1)firstErrTag = chkTag;
			}
		}else if(chkType=='3'){
			if(!chkChange(chkTag,chkRule)){
				isFail = true;
				errCount = errCount + 1;
				if(errCount == 1)firstErrTag = chkTag;
			}
		}else if(chkType=='4'){
			if(!chkFileInput(chkTag,chkRule)){
				isFail = true;
				errCount = errCount + 1;
				if(errCount == 1)firstErrTag = chkTag;
			}
		}
	});
	
	if(isFail){
//		firstErrTag.focus();
	}
	if(errorArray.length>0){
		$('#error_dp').html(errorArray[0]);
	}
	return !isFail;
}	
	
	/**
	 * 导航表单元素提交验证(验证规则同上)
	 * true: 通过; false: 不通过
	 */
	function checkFormDh(){
		var isFail = false;
		var errCount = 0;
		var firstErrTag = null ;
		$('[checkDetailProp^="chk_"]').each(function() {
			var chkTag = $(this); 			// 待验证元素
			var typeRules = $(this).attr('checkDetailProp').split('_');
			var chkType = typeRules[1];		// 验证类型
			var chkRule = typeRules[2];		// 验证规则
			
			if(chkType=='1'){
				if(!chkInput(chkTag,chkRule)){	
					isFail = true;
					errCount = errCount + 1;
					if(errCount == 1)firstErrTag = chkTag;
				}
			}else if(chkType=='2'){
				if(!chkSelect(chkTag,chkRule)){
					isFail = true;
					errCount = errCount + 1;
					if(errCount == 1)firstErrTag = chkTag;
				}
			}else if(chkType=='3'){
				if(!chkChange(chkTag,chkRule)){
					isFail = true;
					errCount = errCount + 1;
					if(errCount == 1)firstErrTag = chkTag;
				}
			}else if(chkType=='4'){
				if(!chkFileInput(chkTag,chkRule)){
					isFail = true;
					errCount = errCount + 1;
					if(errCount == 1)firstErrTag = chkTag;
				}
			}
		});
	
	if(isFail){
		firstErrTag.focus();
	}
	
	return !isFail;
}

/**
 * 可写入文本框或文本域验证
 * @param  chkTag		验证元素
 * @param  chkRule		验证规则
 * @return true: 通过; false: 不通过
 */
function chkInput(chkTag,chkRule){
	var chkTagId = chkTag.attr("id");				// 元素ID
	var chkVal = $.trim(chkTag.val());				// 验证值
	var maxLen=chkTag.attr("maxlength");			// 可输入最大长度
	maxLen = maxLen == null?'':maxLen;
	var rules = chkRule.split('');					// 验证规则
	var isRequired = rules[0];						// 是否必填  1：是;1以外：否;
	var ruleType = rules[1]==null?'':rules[1];		// 其他正则验证   1: 纯数字;2：手机号码;3:固话号码;4:手机或固话;5:电子邮箱(可自行添加规则)
	var isExtend = rules[2]==null?'':rules[2];		// 扩展验证(数据是否重复)
	// 为空
	if(isRequired == '1' && chkVal ==''){
		return setErrMsg(chkTag,err_not_empty,false);
	}
	
	//非法字符
	if(chkVal!='' && !chkIllicitChar(chkVal)){
		return setErrMsg(chkTag,err_over_Char,false);
	}
	
	// 超出最大长度
	if(chkVal!='' && maxLen!='' && !chkByteLen(chkVal,maxLen)){
		return setErrMsg(chkTag,err_over_maxlen,false);
	}
	
	// 非数字
	if (ruleType == '1' && chkVal != '' && isNaN(chkVal)) {
		return setErrMsg(chkTag,err_not_number,false);
	}
	// 正则格式验证
	if(chkVal!='' && ruleType!='' && ruleType!='1' && !checkValByRule(chkVal,ruleType)){
		return setErrMsg(chkTag,err_err_format,false);
	}
	// 扩展验证
	if(chkVal!='' && isExtend!='' && !extendAjaxChk(chkTag,isExtend)){
		return false;
	}
	
	return setErrMsg(chkTag,'',true);
}

/**
 * 下拉框 验证
 * @param  chkTag		验证元素
 * @param  chkRule		验证规则
 * @return true: 通过; false: 不通过
 */
function chkSelect(chkTag,chkRule){
	var chkTagId = chkTag.attr("id");				// 元素ID
	var chkVal = $.trim(chkTag.val());				// 验证值
	var rules = chkRule.split('');					// 验证规则
	var isRequired = rules[0];						// 是否必填  1：是;1以外：否;
	
	// 为空
	if(isRequired == '1' && chkVal ==''){
		return setErrMsg(chkTag,err_not_empty,false);
	}
	
	return setErrMsg(chkTag,'',true);
}

/**
 * 单选、复选验证
 * @param  chkTag		验证元素
 * @param  chkRule		验证规则
 * @return true: 通过; false: 不通过
 */
function chkChange(chkTag,chkRule){
	var chkTagName = chkTag.attr("name");			
	var chkTagId = chkTagName.split(".")[1];		// 元素ID
	var rules = chkRule.split('');					// 验证规则
	var isRequired = rules[0];						// 是否必填  1：是;1以外：否;
	
	var selCnt = $("input[name='"+chkTagName+"']:checked").length;
	if(isRequired == '1' && selCnt ==0 ){
		return setErrMsg(chkTag,err_not_empty,false);
	}
	
	return setErrMsg(chkTag,'',true);
}

/**
 * 文件类型文本框验证
 * @param  chkTag		验证元素
 * @param  chkRule		验证规则
 * @return true: 通过; false: 不通过
 */
function chkFileInput(chkTag,chkRule){ 
	var chkTagId = chkTag.attr("id");				// 元素ID
	var chkVal = $.trim(chkTag.val());				// 验证值
	var rules = chkRule.split('');					// 验证规则
	var isRequired = rules[0];						// 是否必填  1：是;1以外：否;
	
	if(isRequired == '1' && chkVal ==''){
		return setErrMsg(chkTag,err_not_empty,false);
	}
	
	return setErrMsg(chkTag,'',true);
}

/**
 * 检验字节长度
 * @param  chkVal  验证值
 * @param  len	   长度
 * @return true:未超出;false:超出
 */
function chkByteLen(chkVal,len){
	var pattern = /[^\x00-\xff]/g;
	return chkVal.replace(pattern, "gg").length <= len;
}

/**
 * 正则验证
 * @param  chkVal	
 * @param  ruleType	  1: 纯数字;2：手机号码;3:固话号码;4:手机或固话;5:电子邮箱(可自行添加规则)
 * @return true: 通过; false: 不通过
 */
function checkValByRule(chkVal,ruleType){
	var pattern;
	if(ruleType == '1'){		// 纯数字 
		pattern = /^(-?\\d+)(\\.\\d+)?$/;
	}else if(ruleType == '2'){		// 手机号码
		pattern = /^(0?((13[0-9]{1})|(15[^4,\D])|(18[0-9]{1})|(14[0-9]{1}))+\d{8})$/;
	}else if(ruleType == '3'){	// 固话号码
		pattern = /^((0(10|2[^6,\D]|[3-9]\d{2}))-?[1-9]\d{6,7}(-\d{1,8})?)$/;
	}else if(ruleType == '4'){	// 手机或固话号码
		pattern = /^(0?1[123456789]\d{9})$|^((0(10|2[1-9]|[1-9]\d{2}))?[1-9]\d{6,7})$|^(400[0-9]\d{6,9})$/;
	}else if(ruleType == '5'){	// 电子邮箱
		pattern = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
	}else if(ruleType == '6'){
		pattern = /^(\d{11,12})$|^(\d{7,8})$|^(\d{3,4}-\d{7,8})$|^(\d{3,4}-\d{7,8}-\d{1,4})$|^(\d{7,8}-\d{1,4})$|^(\d{11,12}-\d{1,4})$|^(\d{3,4}-\d{3,4}-\d{3,4})$|^(400\d{6,8})$/;
	}else{
		return true;
	}
	
	return pattern.test(chkVal);
}
/**
 * 错误信息处理
 * @param  chkTagId		验证项ID
 * @param  msg			错误信息
 * @param  bol			回传值
 */
function setErrMsg($tag,msg,bol){
	var id = $tag.attr('id');
	if(id=='mobilephone_safe'|| id=='telphone_safe'||id=='alternatePhone2_safe'|| id=='telphonebak_safe'){
		return true;
	}
	var tag = $tag;
	if(msg !=null && msg !=''){
		tag.nextAll(':last').html(msg);
		tag.parent().attr('class',"bomp-p bomp-error p_relative");
	}else{
		tag.nextAll(':last').html("");
		tag.parent().attr('class',"bomp-p p_relative");
	}
	
	return bol;
}

/**
 * 去重错误异常
 */
function setDPErrMsg($tag,msg,bol){
	var tag = $tag;
	if(msg !=null && msg !=''){
		$('#error_dp').html(msg);
	}else{
		$('#error_dp').html("");
	}
	
	return bol;
}
/**
 * 扩展Ajax验证
 * @param chkTag  待验证元素
 * @return
 */
function extendAjaxChk(chkTag,isExtend){
	var chkTagId = chkTag.attr("id");				// 元素ID
	var chkVal = $.trim(chkTag.val());
	// 此处可添加更多验证方法...
	 if(isExtend=='1'&& chkVal!='' && phoneValiDateType != '1'){// 验证号码
		 return checkPhone(chkTagId,chkVal);
	 }else if(isExtend=='2' && chkVal!='' && unitValiDateType != '0'){// 验证单位名称
		 return checkCompany(chkTagId,chkVal);
	 }else if(isExtend=='3' && chkVal!='' && unitHomeValiDateType != '0'){// 验证单位主页
		 return checkUnitHome(chkTagId,chkVal);
	 }
	 return true;
}

function checkCompany(id,val){
	//ajax验证
	if(!checkAjax(id,val,7,unitValiDateType)){
		return false;
	}
	return true;
}

function checkUnitHome(id,val){
	//ajax验证
	if(!checkAjax(id,val,8,unitHomeValiDateType)){
		return false;
	}
	return true;
}

function checkPhone(id,val){
	if(!checkPhoneIsRepeat(id,val)){
		return setDPErrMsg(id,err_is_exist,false);
	}else{
		setDPErrMsg(id,"",true);
	}
	if(val != '' && phoneValiDateType != 1 && !checkAjax(id,val,6,phoneValiDateType)){
		return false;
	}
	return true;
}

//验证电话号码当前页面不重复
function checkPhoneIsRepeat(id,val){
	var j = 0;
	$('#mobilephone,#telphone,#telphonebak').each(function(i){
		var tmpVal = $.trim($(this).val());
		var tmpId = $(this).attr('id');
		//记录当前表单电话重复
		if(id != tmpId && tmpVal != '' && val == tmpVal){
			j++;
		}
	});
	if(j > 0){
		return false;
	}
	return true;
}

/**
 * Ajax验证手机或固话是否重复
 * @param chkTag  待验证元素
 * @return
 */
//function ajaxchkPhone(chkTag){
//	var isPass = true;
//	var base = $("#base").val();
//	var chkVal = chkTag.val();				// 元素现值
//	var chkTagId = chkTag.attr('id');		// 元素ID
//	var oldVal = $("#old_"+chkTagId).val(); // 元素原值
//	
//	if(checkValByRule(oldVal,3)){ // 根据原值判断验证固话时先格式化
//		chkVal = chkVal.substring(0,1) == '0'?chkVal:'0'+chkVal;
//		if(chkVal.substring(3, 5).indexOf('-') != -1){
//			chkVal = chkVal.replace('-', '');// 固话加分机号 057188889999-12345678
//		}
//	}
//	
//	if(chkVal==oldVal){	// 未作修改不请求验证
//		return isPass;
//	}
//	
//	$.ajax({
//		url : base+'/res/taocust/checkPhone.do',
//		data: {'phone':chkVal},
//		type : 'post',
//		async:false,
//		error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
//		success : function(data) {
//			if(data > 0 ){
//				isPass = false;
//			}
//		}
//	});
//	
//	return isPass;
//}

//ajax验证
function checkAjax(id,val,type,validatetype){
	var isPass = true;
	var url = ctx + "/ajax/commonCheck";
	var resCustId = $('#rciId').val();
	$.ajax({
		url : url,
		data: {'resCustId':resCustId,
			   'chkType':type,
			   'chkVal':val,
			   'tanpin':$('#tanpin').val(),
			   'validatetype':validatetype},
		type : 'post',
		async:false,
		error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
		success : function(data) {
			var tag = $('#'+id);
			if(data != "0" ){
				errorArray.push(data);
				$('#error_dp').html(data);
				isPass =false;
				tag.next().html("");
				tag.parent().attr('class',"bomp-p p_relative");
			}else{
				$('#error_dp').html("");
			}
		}
	});
	return isPass;
}

/**
 * 检验非法字符
 * @return
 */
function chkIllicitChar(chkVal){
	if(/^[^\|"'<>]*$/.test(chkVal)){
		return true;
	}
	return false;
}
