/***********************************************
 * 页面表单元素验证_共同JS文件
 * @author haoqj
 * @date 2013-11-15
 ***********************************************/

var err_not_empty = '必填项';
var err_over_maxlen = '超出最大长度';
var err_over_minlen = '超出最小长度';
var err_over_len_range = '超出长度范围';
var err_not_number = '必须为数字';
var err_err_format = '格式不正确';
var err_is_exist = '该数据已存在';
var err_all_empty = '同时为空';
var err_over_Char = '非法字符';
$(function(){
	/**
	 * 表单元素操作时验证
	 * checkProp = 'chk_验证类型_验证规则'
	 * 
	 * 验证类型： 1：可写入文本框; 2: 下拉框; 3: 单选、复选;4：文件类型文本框; 注：验证类型为1以外时只验证是否为空,
	 * 验证规则： ab a:是否为空(a=1验证); b:正则验证(1: 纯数字;2：手机号码;3:固话号码;4:手机或固话;5:电子邮箱;6,7,8...可自行添加); c:Ajax验证
	 * 如：
	 * <input id="name" checkProp='chk_1_103' maxlength="20" type="text"/>
	 * checkProp='chk_1_13': 验证可写入文本框,非空、固话
	 * checkProp='chk_3_1': 验证单选、复选, 非空
	 */
	$('[checkProp^="chk_"]').each(function() {
		var chkTag = $(this); 			// 待验证元素
		var typeRules = $(this).attr('checkProp').split('_');
		var chkType = typeRules[1];		// 验证类型
		var chkRule = typeRules[2];		// 验证规则
		
		if(chkType=='1'){ // 通过blur事件验证
			chkTag.blur(function(){
				chkInput(chkTag,chkRule);
			});
		}else if(chkType=='2'){ // 通过change事件验证
			chkTag.change(function(){
				chkSelect(chkTag,chkRule);
			});
		}else if(chkType=='3'){ // 通过change事件验证
			chkTag.change(function(){
				chkChange(chkTag,chkRule);
			});
		}else if(chkType=='4'){ // 通过blur事件验证
			chkTag.blur(function(){
				chkFileInput(chkTag,chkRule);
			});
		}
	});
});

//淘客户点击修改后保存时，判断条件是否满足
function  checkFormAll4custInfo(){
	var isFail = false;
	var errCount = 0;
	var firstErrTag = null ;
	$('[checkProp4Info^="chk_"]').each(function() {
		var chkTag = $(this); 			// 待验证元素
		var typeRules = $(this).attr('checkProp4Info').split('_');
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
 * 表单元素提交验证(验证规则同上)
 * true: 通过; false: 不通过
 */
function checkFormAll(){
	var isFail = false;
	var errCount = 0;
	var firstErrTag = null ;
	$('[checkProp^="chk_"]').each(function() {
		var chkTag = $(this); 			// 待验证元素
		var typeRules = $(this).attr('checkProp').split('_');
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
	chkTag.val(chkVal);								// 去除头尾空格再保存
	var maxLen=chkTag.attr("maxlength");			// 可输入最大长度
	maxLen = maxLen == null?'0':maxLen;
	var rules = chkRule.split('');					// 验证规则
	var isRequired = rules[0];						// 是否必填  1：是;1以外：否;
	var ruleType = rules[1]==null?'0':rules[1];		// 其他正则验证   1: 纯数字;2：手机号码;3:固话号码;4:手机或固话;5:电子邮箱;6:邮政编码;7字母数字(可自行添加规则);8:非负数
	var extendType = rules[2]==null?'0':rules[2];	// 扩展验证(数据是否重复)
	// 为空
	if(isRequired == '1' && chkVal ==''){
		return setErrMsg(chkTagId,err_not_empty,false);
	}
	
	//非法字符
	if(chkVal!='' && !chkIllicitChar(chkVal)){
		return setErrMsg(chkTagId,err_over_Char,false);
	}
	
	// 超出最大长度
	if(chkVal!='' && maxLen!='0' && !chkByteLen(chkVal,1,maxLen)){
		return setErrMsg(chkTagId,err_over_maxlen,false);
	}
	
	// 非数字
	if (ruleType == '1' && chkVal != '' && isNaN(chkVal)) {
		return setErrMsg(chkTagId,err_not_number,false);
	}
	
	// 正则格式验证
	if(chkVal!='' && ruleType!= '0' && ruleType!='1' && !checkValByRule(chkVal,ruleType)){
		return setErrMsg(chkTagId,err_err_format,false);
	}
	
	// 扩展验证
	if(chkVal!='' && extendType!='0' && !extendAjaxChk(chkTag,extendType)){
		return setErrMsg(chkTagId,err_is_exist,false);
	}
	
	return setErrMsg(chkTagId,'',true);
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
		return setErrMsg(chkTagId,err_not_empty,false);
	}
	
	return setErrMsg(chkTagId,'',true);
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
		return setErrMsg(chkTagId,err_not_empty,false);
	}
	
	return setErrMsg(chkTagId,'',true);
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
		return setErrMsg(chkTagId,err_not_empty,false);
	}
	
	return setErrMsg(chkTagId,'',true);
}

/**
 * 检验字节长度
 * @param  chkVal	验证值
 * @param  type		验证类型 1:最大长度;2:最小长度;3:最大最小同时验证
 * @param  Len1		对应长度 type=1||3 时指最大长度;type=2时指最小长度
 * @param  Len2		type = 2时必填，指最小长度
 * @return  true:未超出;false:超出
 */
function chkByteLen(chkVal,type,Len1,Len2){
	var pattern = /[^\x00-\xff]/g;
	var valLen = chkVal.replace(pattern, "gg").length;
	if(type==1){ // 最大长度
		return valLen <= Len1;
	}else if(type==2){ // 最小长度
		return valLen >= Len1;
	}else if(type==3){ // 最大最小同时验证
		return valLen <= Len1 && valLen >= Len2;
	}
	
	return true;
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

/**
 * 正则验证
 * @param  chkVal	
 * @param  ruleType	  1: 纯数字;2：手机号码;3:固话号码;4:手机或固话;5:电子邮箱;6:邮政编码;7字母数字(可自行添加规则);8:非负数
 * @return true: 通过; false: 不通过
 */
function checkValByRule(chkVal,ruleType){
	var pattern;
	if(ruleType == '1'){		// 纯数字 
		pattern = /^(-?\d+)(\.\d+)?$/;
	}else if(ruleType == '2'){		// 手机号码
		pattern = /^(((13[0-9]{1})|(15[^4,\D])|(18[0-9]{1})|(14[0-9]{1}))+\d{8})$/;
	}else if(ruleType == '3'){	// 固话号码
		pattern = /^((0(10|2[^6,\D]|[3-9]\d{2}))?-?[1-9]\d{6,7})$/;
	}else if(ruleType == '4'){	// 手机或固话号码
		pattern = /(^(((13[0-9]{1})|(15[^4,\D])|(18[0-9]{1})|(14[0-9]{1}))+\d{8})$)|(^((0(10|2[^6,\D]|[3-9]\d{2}))?-?[1-9]\d{6,7}(-\d{1,8})?)$)/;
	}else if(ruleType == '5'){	// 电子邮箱
		pattern = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
	}else if(ruleType == '6'){	//验证邮编
		pattern = /^[1-9][0-9]{5}$/;
	}else if(ruleType == '7'){	// 字符(A-Z, a-z, 0-9)
		pattern = /^([a-zA-Z0-9]+)$/;
	}else if(ruleType == '8')    //非负数
		pattern =/^[0-9]*[1-9][0-9]*$/
	else{
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
function setErrMsg(chkTagId,msg,bol){
	var errMsgTag = $("#errmsg_" + chkTagId);		// 验证项对应LI元素
	var errLiTag = $("#errli_" + chkTagId);			// 慧营销专用,验证项对应LI元素
	var title = $("#t_" + chkTagId).text();			// 慧营销专用,验证项的标题
	var errTitleTag = $("#errt_" + chkTagId);		// 慧营销专用,验证项对应标题元素
	errMsgTag.text(msg);
	if(msg !=null && msg !=''){
		var prams = chkTagId.split('_');
		var focusId = prams[0];
		errTitleTag.click(function(){$("#"+focusId).focus();});	// 添加定位事件
		errTitleTag.text(title);
		errLiTag.show();
	}else{
		errTitleTag.text('');
		errLiTag.hide();
	}
	return bol;
}

/**
 * 扩展Ajax验证
 * @param chkTag  待验证元素
 * @return
 */
function extendAjaxChk(chkTag,extendType){
	 // 此处可添加更多验证方法...
	 return ajaxchk(chkTag,extendType);
}

/**
 * Ajax验证字段是否重复
 * @param chkTag  待验证元素
 * @return
 */
function ajaxchk(chkTag,extendType){
	var isPass = true;
	var base = $("#base").val();
	var chkVal = chkTag.val();				// 元素现值
	var chkTagId = chkTag.attr('id');		// 元素ID
	var oldVal = $("#old_"+chkTagId).val(); // 元素原值
	oldVal = oldVal == null ? '' : oldVal;
	
	if(oldVal!='' && chkVal==oldVal){	// 未作修改不请求验证
		return isPass;
	}
	$.ajax({
		url : base+'/auth/org/ajax/commonCheck.do',
		data: {'chkType':extendType,
			   'chkVal':chkVal},
		type : 'post',
		async:false,
		error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
		success : function(data) {
			if(data != 0 ){
				isPass = false;
			}
		}
	});
	return isPass;
}