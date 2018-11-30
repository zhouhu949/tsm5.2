/**
 * 网上营业厅校验JS
 * author lixing
 * 
 * */
var err_default = "格式不正确"
var err_not_empty = "必填项";
var err_not_num = "只能输入数字";
$(function(){
	$("[validate^=chk_]").each(function(index,obj){
		var checkObj = $(obj);//校验对象
		var checkType = checkObj.attr("validate").split("_")[1];//校验方式 1-失去焦点 2-值发生改变
		var nullAble = checkObj.attr("validate").split("_")[2];//是否可以为空 0-可以为空 1-不可以为空
		var checkRule = checkObj.attr("validate").split("_")[3];//校验规则
		if(checkType == '1'){
			checkObj.blur(function(){
				checkObj.next().text("");
				check(checkObj,nullAble,checkRule);
			});
		}else{
			checkObj.change(function(){
				checkObj.next().text("");
				check(checkObj,nullAble,checkRule);
			});
		}
	});
});

function check(obj,nullAble,checkRule){
	var checkValue = obj.val();
	var maxlength = parseInt(obj.attr("maxlength") == null ? "0" : obj.attr("maxlength"));
	if(nullAble == '1' && checkValue==''){
		return setMsg(obj,err_not_empty,false);
	}
	if(checkValue != '' && maxlength > 0 && checkValue.length > maxlength){
		return setMsg(obj,"最多只能输入"+maxlength+"个字符",false);
	}
	if(checkValue != '' && !checkValByRule(checkValue,checkRule)){
		return setMsg(obj,err_default,false);
	}
}

function checkForm(){
	var flag = true;
	$("[validate^=chk_]").each(function(index,obj){
		var checkObj = $(obj);//校验对象
		var checkType = checkObj.attr("validate").split("_")[1];//校验方式 1-失去焦点 2-值发生改变
		var nullAble = checkObj.attr("validate").split("_")[2];//是否可以为空 0-可以为空 1-不可以为空
		var checkRule = checkObj.attr("validate").split("_")[3];//校验规则
		
		var checkValue = checkObj.val();
		var maxlength = parseInt(checkObj.attr("maxlength") == null ? "0" : checkObj.attr("maxlength"));
		if(nullAble == '1' && checkValue==''){
			flag = setMsg(checkObj,err_not_empty,false);
		}
		if(checkValue != '' && maxlength > 0 && checkValue.length > maxlength){
			flag =  setMsg(checkObj,"最多只能输入"+maxlength+"个字符",false);
		}
		if(checkValue != '' && !checkValByRule(checkValue,checkRule)){
			flag =  setMsg(checkObj,err_default,false);
		}
	});
	return flag;
}

/**
 * 正则验证
 * @param  chkVal	
 * @param  ruleType	  1: 电话号码 传真;2：邮编;3:数字;4手机号码;5银行卡号;6邮箱;7手机或固话号码;8企业税号(可自行添加规则)
 * @return true: 通过; false: 不通过
 */
function checkValByRule(chkVal,ruleType){
	var pattern;
	if(ruleType == '1'){		// 电话号码 传真
		pattern = /^((0(10|2[^6,\D]|[3-9]\d{2}))?-?[1-9]\d{6,7})$/;
	}else if(ruleType == '2'){	// 邮编
		pattern = /^[1-9][0-9]{5}$/;
	}else if(ruleType == '3'){	//数字
		pattern = /^(-?\d+)(\.\d+)?$/;
	}else if(ruleType == '4'){
		pattern = /^(((13[0-9]{1})|(15[^4,\D])|(17[0-9]{1})|(14[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	}else if(ruleType == '5'){
		pattern = /^(\d{16}|\d{19})$/;
	}else if(ruleType == '6'){	
		pattern = /\w@\w*\.\w/;
	}else if(ruleType == '7'){
		pattern = /(^(((13[0-9]{1})|(15[^4,\D])|(17[0-9]{1})|(14[0-9]{1})|(18[0-9]{1}))+\d{8})$)|(^((0(10|2[^6,\D]|[3-9]\d{2}))?-?[1-9]\d{6,7})$)/;
	}else if(ruleType == '8'){
		pattern = /^[A-Z0-9]{15}$|^[A-Z0-9]{17}$|^[A-Z0-9]{18}$|^[A-Z0-9]{20}$/;
	}else{
		return true;
	}
	return pattern.test(chkVal);
}

function setMsg(obj,msg,bol){
	obj.next().text(msg);
	return bol;
}