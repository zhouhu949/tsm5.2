/**
 * 校验JS
 * author lixing
 * 
 * */
var err_default = "格式不正确"
var err_not_empty = "必填项";
var err_not_num = "只能输入数字";
var contract_repeat = "已经存在编号为{code}的合同";
var contract_order_repeat = "订单号重复";
$(function(){
	$('[checkPub^=chk_]').each(function(){
		var chkTag = $(this); 			// 待验证元素
		var typeRules = $(this).attr('checkPub').split('_');
		var chkType = typeRules[1];		// 验证类型
		var nullAble = typeRules[2];	//是否为空
		var chkRule = typeRules[3];		// 验证规则
		if(chkType=='1'){ // 通过blur事件验证
			chkTag.live('blur',function(){
				chkTag.parent().removeClass("bomp-error");
				chkTag.nextAll('.error').text('');
				check(chkTag,nullAble,chkRule);
			});
		}else if(chkType=='2'){// 通过change事件验证
			chkTag.live('change',function(){
				chkTag.parent().removeClass("bomp-error");
				chkTag.nextAll('.error').text('');
				check(chkTag,nullAble,chkRule);
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
		var msg = '';
		if(checkRule == '9'){
			msg = contract_repeat.replace('{code}',checkValue);
		}else if(checkRule == '10'){
			msg = contract_order_repeat;
		}else{
			msg = err_default;
		}
		return setMsg(obj,msg,false);
	}
}

function checkForm(){
	var flag = true;
	$("[checkPub^=chk_]").each(function(index,obj){
		var checkObj = $(obj);//校验对象
		var typeRules = $(obj).attr('checkPub').split('_');
		var checkType = typeRules[1];//校验方式 1-失去焦点 2-值发生改变
		var nullAble = typeRules[2];//是否可以为空 0-可以为空 1-不可以为空
		var checkRule = typeRules[3];//校验规则
		
		var checkValue = checkObj.val();
		var maxlength = parseInt(checkObj.attr("maxlength") == null ? "0" : checkObj.attr("maxlength"));
		if(nullAble == '1' && checkValue==''){
			flag = setMsg(checkObj,err_not_empty,false);
		}
		if(checkValue != '' && maxlength > 0 && checkValue.length > maxlength){
			flag =  setMsg(checkObj,"最多只能输入"+maxlength+"个字符",false);
		}
		if(checkValue != '' && !checkValByRule(checkValue,checkRule)){
			var msg = '';
			if(checkRule == '9'){
				msg = contract_repeat.replace('{code}',checkValue);
			}else if(checkRule == '10'){
				msg = contract_order_repeat;
			}else{
				msg = err_default;
			}
			flag =  setMsg(checkObj,msg,false);
		}
	});
	return flag;
}

/**
 * 正则验证
 * @param  chkVal	
 * @param  ruleType	  1: 电话号码 传真;2：邮编;3:数字;4手机号码;5银行卡号;6邮箱;7手机或固话号码;8企业税号;9合同编号-ajax验证(可自行添加规则)
 * @return true: 通过; false: 不通过
 */
function checkValByRule(chkVal,ruleType){
	var pattern;
	if(ruleType == '1'){		// 电话号码 传真
		pattern = /^((0(10|2[^6,\D]|[3-9]\d{2}))?-?[1-9]\d{6,7})$/;
	}else if(ruleType == '2'){	// 邮编
		pattern = /^[1-9][0-9]{5}$/;
	}else if(ruleType == '3'){	//数字（不能以0开头且是正数）
		pattern = /^(0|[1-9][0-9]*)(\.\d+)?$/;
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
	}else if(ruleType == '9'){
		var id = $("#contractId").val();
		var flag = false;
		$.ajax({
			url:ctx+'/contract/check',
			type:'post',
			data:{id:id,code:chkVal},
			async:false,
			error:function(){},
			success:function(data){
				if(data == 1){
					flag = true;
				}
			}
		});
		return flag;
	}else if(ruleType == '10'){
		var id = $("#orderId").val();
		var flag = false;
		$.ajax({
			url:ctx+'/contract/order/check',
			type:'post',
			data:{id:id,code:chkVal},
			async:false,
			error:function(){},
			success:function(data){
				if(data == 1){
					flag = true;
				}
			}
		});
		return flag;
	}else{
		return true;
	}
	return pattern.test(chkVal);
}

function setMsg(obj,msg,bol){
	obj.parent().addClass("bomp-error");
	obj.nextAll('.error').text(msg);
	return bol;
}