
//单双行
Handlebars.registerHelper('even_odd', function(index, options) {
	return  index %2 ==0 ? "":"sty-bgcolor-b";
});

Handlebars.registerHelper('serial_num', function(index, options) {
	return  index+1;
});

/**
 *  日期格式化工具 依赖monent.js 支持
 *
 *
 *   {{formateDate  date "yyy-mm-dd"}}
 *   {{formateDate  date "yyyy-mm}}

 */

Handlebars.registerHelper('formatDate', function(date,format, options) {
		if(date != null && date != ""){
			return moment(date).format(format);
		}else{
			return "";
		}
});

Handlebars.registerHelper('return_len', function(arr, options) {
	return  arr.length;
});

//两数相减
Handlebars.registerHelper('numSubtract', function(number1,number2, options) {
	if(number1 >= number2){
		return number1-number2;
	}else{
		return number2-number1;
	}
});
//完成率
Handlebars.registerHelper('finishPercentage', function(count,finished, options) {
	var num=0;
	if(count > finished){
		num = (finished/count)*100;
		return num.toFixed(2)+"%";
	}else if(count > finished){
		num =(count/finished)*100;
		return num.toFixed(2)+"%";
	}else{
		return "100%";
	}
});

//生成唯一uuid
Handlebars.registerHelper('getOnlyUuid', function(options) {
	var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 32; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
 
    var uuid = s.join("");
    return uuid;
});
//日计划权限
Handlebars.registerHelper('dayPlanDis', function(arg,className,args, options) {
	if(arg){
		return "";
	}else{
		return className;
	}
});

Handlebars.registerHelper('typeDayPlanDis', function(arg,className,args, options) {
	if(arg && !args){
		return "";
	}else{
		return className;
	}
});
//判断是否是
Handlebars.registerHelper('areyou', function(num1 , options){
    if(Number(num1) == 1 ){
    	return true;
    }else{
    	return false;
    }
});
/**
 *
 *   保留小数位数
 *
 *
 *
 *
 */

Handlebars.registerHelper('formatNumber', function(num,decimals,options){
    return num.toFixed(decimals);
});

/**
 *
 *   千分位格式化
 *
 *
 *  use:{{formatnumber num}}
 *
 */

Handlebars.registerHelper('formatMoney', function(num, options){
    if(num == null || num == ''){
        return '';
    }else{
        num = num + '';
        return num.replace(/(?=(?!^)(?:\d{3})+(?:\.|$))(\d{3}(\.\d+$)?)/g,',$1');
    }
});

/**
 *   单一对象比较
 *
 *
 *   {{#compare people.name '==' 'peter'}}
         他的名字是peter
         {{else}}
         他的名字不是peter
         {{/compare}}
 *
 *
 */
Handlebars.registerHelper('compare', function(left, operator, right, options) {
    if (arguments.length < 3) {
        throw new Error('Handlerbars Helper "compare" needs 2 parameters');
    }
    var operators = {
        '==': function(l, r) {return l == r; },
        '===': function(l, r) {return l === r; },
        '!=': function(l, r) {return l != r; },
        '!==': function(l, r) {return l !== r; },
        '<': function(l, r) {return l < r; },
        '>': function(l, r) {return l > r; },
        '<=': function(l, r) {return l <= r; },
        '>=': function(l, r) {return l >= r; },
        'typeof': function(l, r) {return typeof l == r; }
    };
    if (!operators[operator]) {
        throw new Error('Handlerbars Helper "compare" doesn\'t know the operator ' + operator);
    }
    var result = operators[operator](left, right);
    if (result) {
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
});

//其他自行注册

//审核状态
Handlebars.registerHelper('authStateStr', function(params, options) {
    var str="";
    switch (params) {
        case 1:
            str = "待审核";
            break;
        case 2:
            str = "生效中";
            break;
        case 3:
            str = "驳回";
            break;
        case 4:
            str = "作废";
            break;
        case 5:
        	str = "已失效";
        	break;
        default:
            str="未上报";
    }
    return str;

});

//服务开通状态
Handlebars.registerHelper('orderStateStr', function(params, options) {
    var str="";
    switch (params) {
        case 0:
            str = "未开通";
            break;
        case 1:
            str = "已开通";
            break;
        case 2:
            str = "已过期";
            break;
        default:
            str="";
    }
    return str;

});
// 操作类型
Handlebars.registerHelper('operateStateStr', function(params, options) {
    var str="";
    switch (params) {
        case 1:
            str = "通信币分配";
            break;
        case 2:
            str = "通信币回收";
            break;
        case 3:
            str = "系统分配";
            break;
        case 6:
            str = "定时分配";
            break;
        case 7:
            str = "短信分配";
            break;
        case 8:
            str = "短信回收";
            break;
        default:
            str="";
    }
    return str;

});
Handlebars.registerHelper('resStateStr', function(params, options) {
    var str="";
    switch (params) {
        case "3002":
            str = "资源分配";
            break;
        case "3003":
            str = "资源回收";
            break;
        case "3004":
            str = "资源删除";
            break;
        case "3005":
            str = "资源清空";
            break;
        default:
            str="";
    }
    return str;

});
//客户状态
Handlebars.registerHelper('custStateString', function(params, options) {
	var str="";
	switch (params) {
		case 1:
	  str = "资源";
	  break;
		case 2,3:
	  str = "意向客户";
	  break;
		case 4:
	  str = "公海客户";
	  break;
		case 6:
	  str = "签约客户";
	  break;
		case 7:
	  str = "沉默客户";
	  break;
		case 8:
	  str = "流失客户";
	  break;
	default:
	  str="访客";
   }
   return str;

});

Handlebars.registerHelper('complaintType', function(params, options) {
	var str="";
   if(params == 0){
	   str = "限制呼出";
   }else if(params == 1){
	   str = "限制呼入";
   }else if(params == 2){
	   str = "限制呼入、限制呼出"
   }
   return str;
});

// 来电状态
Handlebars.registerHelper('callStateStr', function(params, options) {
	var str="";
   if(params.callState==1){
	if(call.timeLength>0){
	  str="已接来电";
	}else{
	  str="未接来电";
	}
   }else if(params.callState==2){
	if(call.timeLength>0){
	  str="已接去电";
	}else{
	  str="未接去电";
	}
   }
   return str;

});

//系统字段
Handlebars.registerHelper('custFieldJudgeValue', function(name,options) {
//	console.log(options);
	var index = options.data.index;
	var _this = options.data.root.list[index];
	var value = _this[name];
	var dataType = _this.dataType;
	var fieldCode = _this.fieldCode;
	var disabled_html = "";
	if(fieldCode == "leadCode" || fieldCode == "custName"  || fieldCode == "borrowMoney" || fieldCode == "phone") {
		disabled_html = 'disabled';
	}
	if(value == 0){
		return new Handlebars.SafeString(
				'<input type="checkbox" ' + disabled_html +' name="'+name+'" data-type='+dataType+' />'
		);
	}else{
		return new Handlebars.SafeString(
				'<input type="checkbox" ' + disabled_html +' name="'+name+'" checked="checked" data-type='+dataType+' />'
		);
	}
});

//查询管理项
Handlebars.registerHelper('judgeValue', function(name,options) {
//	console.log(options);
	var index = options.data.index;
	var _this = options.data.root.list[index];
	var isDisabled = _this.isDisabled;
	var value = _this[name];
	var dataType = _this.dataType;
	switch(isDisabled){
		case 0:
			if(value == 0){
				return new Handlebars.SafeString(
						'<input type="checkbox" name="'+name+'" disabled="disabled" data-type='+dataType+' />'
				);
			}else if(value == 1){
				return new Handlebars.SafeString(
						'<input type="checkbox" name="'+name+'" checked="checked" disabled="disabled" data-type='+dataType+' />'
				);
			}else if(value == 3){
				return new Handlebars.SafeString(
						'/'
				);
			}
			break;
		case 1:
			if(value == 0){
				return new Handlebars.SafeString(
						'<input type="checkbox" name="'+name+'" data-type='+dataType+' />'
				);
			}else if(value == 1){
				return new Handlebars.SafeString(
						'<input type="checkbox" name="'+name+'" checked="checked" data-type='+dataType+' />'
				);
			}else if(value == 3){
				return new Handlebars.SafeString(
						'/'
				);
			}
			break;
		case 2:
			if(name == "isShow"){
				if(value == 0){
					return new Handlebars.SafeString(
							'<input type="checkbox" name="'+name+'" data-type='+dataType+' />'
					);
				}else if(value == 1){
					return new Handlebars.SafeString(
							'<input type="checkbox" name="'+name+'" checked="checked" data-type='+dataType+' />'
					);
				}else if(value == 3){
					return new Handlebars.SafeString(
							'/'
					);
				}
			}else{
				if(value == 0){
					return new Handlebars.SafeString(
							'<input type="checkbox" name="'+name+'" disabled="disabled" data-type='+dataType+' />'
					);
				}else if(value == 1){
					return new Handlebars.SafeString(
							'<input type="checkbox" name="'+name+'" checked="checked" disabled="disabled" data-type='+dataType+' />'
					);
				}else if(value == 3){
					return new Handlebars.SafeString(
							'/'
					);
				}
			}
			break;
		case 3:
			if(name == "isQuery"){
				renderInput(value,false);
			}else{
				if(value == 0){
					return new Handlebars.SafeString(
							'<input type="checkbox" name="'+name+'" disabled="disabled" data-type='+dataType+' />'
					);
				}else if(value == 1){
					return new Handlebars.SafeString(
							'<input type="checkbox" name="'+name+'" checked="checked" disabled="disabled" data-type='+dataType+' />'
					);
				}else if(value == 3){
					return new Handlebars.SafeString(
							'/'
					);
				}
			}
			break;
		default:
			if(value == 0){
				return new Handlebars.SafeString(
						'<input type="checkbox" name="'+name+'" data-type='+dataType+' />'
				);
			}else if(value == 1){
				return new Handlebars.SafeString(
						'<input type="checkbox" name="'+name+'" checked="checked" data-type='+dataType+' />'
				);
			}else if(value == 3){
				return new Handlebars.SafeString(
						'/'
				);
			}
			break;
	}
});

//添加input输入框
Handlebars.registerHelper('addInput', function(value,name,options) {
	return new Handlebars.SafeString(
			'<input type="text" name="'+name+'" value="'+value+'" />'
	);
});

//系统字段--操作--按钮
Handlebars.registerHelper('systemFieldOperateBtn', function(page,fieldId,sort,edit_url,fieldCode,options) {
	if(page != "custFieldContacts"){
		var icon_edit_html = '<i class="fa fa-edit icon-20px icon-blue" data-id="'+fieldId+'" data-url="'+ctx+edit_url+'"></i>';
		if(fieldCode == "leadCode" || fieldCode == "custName"  || fieldCode == "borrowMoney" || fieldCode == "phone") {
			icon_edit_html = '<i class="fa fa-edit icon-20px icon-blue icon-grey" data-id="'+fieldId+'" data-url="'+ctx+edit_url+'"></i>';
		}
		switch(sort){
			case 1:
				return new Handlebars.SafeString(
						icon_edit_html+
						'<i class="fa fa-arrow-circle-o-up icon-20px icon-blue icon-grey"></i>'+
						'<i class="fa fa-arrow-circle-o-down icon-20px icon-blue icon-grey"></i>'
				);
				break;
			case 2:
				return new Handlebars.SafeString(
						icon_edit_html+
						'<i class="fa fa-arrow-circle-o-up icon-20px icon-blue icon-grey"></i>'+
						'<i class="fa fa-arrow-circle-o-down icon-20px icon-blue"></i>'
				);
				break;
			default:
				return new Handlebars.SafeString(
						icon_edit_html+
						'<i class="fa fa-arrow-circle-o-up icon-20px icon-blue"></i>'+
						'<i class="fa fa-arrow-circle-o-down icon-20px icon-blue"></i>'
				);
				break;
		}
	}else{
		return new Handlebars.SafeString(
				'<i class="fa fa-edit icon-20px icon-blue" data-id="'+fieldId+'" data-url="'+ctx+edit_url+'"></i>'+
				'<i class="fa fa-arrow-circle-o-up icon-20px icon-blue"></i>'+
				'<i class="fa fa-arrow-circle-o-down icon-20px icon-blue"></i>'
		);
	}
	
});

//判断dataType的值
Handlebars.registerHelper('judgeDataType', function(value,options) {
	switch(value){
		case 1:
			return "文本类型";
			break;
		case 2:
			return "日期类型";
			break;
		case 3:
			return "单选类型";
			break;
		case 4:
			return "多选类型";
			break;
		case 5:
			return "人员选择";
			break;
		case 6:
			return "整数类型";
			break;
		case 7:
			return "double类型";
			break;
		default:
			return "文本类型";
			break;
	}
});

//判断dataType的值
Handlebars.registerHelper('qupaiManageJudgeDataType', function(value,options) {
	switch(value){
		case 1:
			return "文本类型";
			break;
		case 2:
			return "日期类型";
			break;
		case 3:
			return "单选类型";
			break;
		case 4:
			return "多选类型";
			break;
		case 5:
			return "人员选择";
			break;
		case 6:
			return "整数类型";
			break;
		case 7:
			return "double类型";
			break;
		case 8:
			return "引用类型";
			break;
		case 9:
			return "图片";
			break;
		case 10:
			return "统计";
			break;
		case 11:
			return "金额";
			break;
		default:
			return "文本类型";
			break;
	}
});

//判断dataType的值
Handlebars.registerHelper('qupaiCustFieldJudgeDataType', function(value,options) {
	switch(value){
		case 1:
			return "文本类型";
			break;
		case 2:
			return "日期类型";
			break;
		case 3:
			return "单选类型";
			break;
		case 4:
			return "多选类型";
			break;
		case 5:
			return "金额";
			break;
		case 6:
			return "引用字段";
			break;
		case 7:
			return "计算类型";
			break;
		case 8:
			return "计算类型";
			break;
		case 9:
			return "计算类型";
			break;
		case 10:
			return "图片";
			break;
		default:
			return "文本类型";
			break;
	}
});

Handlebars.registerHelper('contractOperateBtn', function(shiroUser,commonOnwerSign,options) {
//	console.log(options);
	var index = options.data.index;
	var _this = options.data.root.list[index];
	var isDel = _this.isDel;
	var id = _this.id;
	var custId = _this.custId;
	var custName = _this.newCustName;
	var company = _this.newCompany;
	var commonAcc = _this.commonAcc;
	var returnHtml = '';
	if(isDel != 1){
		if(company!="" && company!=null){
			returnHtml += '<a href="javascript:;" class="icon-cust-card" id="cardInfo_'+custId+'" custName="'+company+'" title="客户卡片"></a>';
		}else{
			returnHtml += '<a href="javascript:;" class="icon-cust-card" id="cardInfo_'+custId+'" custName="'+custName+'" title="客户卡片"></a>';
		}
		
		if( ( (shiroUser.serverDay > 0) && (commonAcc != shiroUser.account) ) || (   (commonAcc == shiroUser.account) && (commonOnwerSign == 1) )){
			returnHtml += '<a data-authority="contract_addOrder" href="javascript:;" module="10" class="icon-new-order" id="addOrder_'+id+'" custId="'+custId+'" title="新增订单"></a>';
		}else{
			//returnHtml += '<a data-authority="contract_addOrder" href="javascript:;" module="10" class="icon-new-order img-gray" custId="'+custId+'" title="新增订单"></a>';
		}
		
		if((commonAcc != shiroUser.account) || ((commonAcc == shiroUser.account) && (commonOnwerSign == 1))){
			returnHtml += '<a data-authority="contract_contractEdit" href="javascript:;" class="icon-edit" id="editcon_'+id+'" title="合同编辑"></a>';
			returnHtml += '<a data-authority="contract_cancelContract" href="javascript:;" class="icon-cancel-contr demoBtn_b icon-cancel-contr-bonus" id="cancle_'+id+'_'+custId+'" title="取消合同"></a>';
		}else{
			//returnHtml += '<a data-authority="contract_contractEdit" href="javascript:;" class="icon-edit img-gray" title="合同编辑"></a>';
			//returnHtml += '<a data-authority="contract_cancelContract" href="javascript:;" class="icon-cancel-contr demoBtn_b icon-cancel-contr-bonus img-gray" title="取消合同"></a>';
		}
	}
	returnHtml += '<a href="javascript:;" class="icon-search-look" id="look_'+id+'" title="查看"></a>';
	return new Handlebars.SafeString(
			returnHtml
	);
});

/**
 * 合同管理--合同类别
 * 
 * isDel==1?"无效合同":"有效合同"
 */
Handlebars.registerHelper('formatContractType', function(isDel,options) {
	var returnText = isDel==1?"无效合同":"有效合同";
	return returnText;
});

// 物流状态 1 暂无记录 2 在途中 3 派送中 4 已签收 5 用户拒签 6 疑难件 7 无效单8 超时单 9 签收失败 10 退回
Handlebars.registerHelper('courierStatusStr', function(courierStatus,options) {
	var returnText = "";
	switch(courierStatus){
		case 1:
			returnText = "暂无记录";
			break;
		case 2:
			returnText = "在途中";
			break;
		case 3:
			returnText = "派送中";
			break;
		case 4:
			returnText = "已签收";
			break;
		case 5:
			returnText = "用户拒签";
			break;
		case 6:
			returnText = "疑难件";
			break;
		case 7:
			returnText = "无效单";
			break;
		case 8:
			returnText = "超时单";
			break;
		case 9:
			returnText = "签收失败";
			break;
		case 10:
			returnText = "退回";
			break;
	}
	return returnText;
});

//订单维护
Handlebars.registerHelper('orderListBtn', function(shiroUser,commonOnwerSign,options) {
//	console.log(options);
	var index = options.data.index;
	var _this = options.data.root.list[index];
	var id = _this.id;
	var authState = _this.authState;
	var commonAcc = _this.commonAcc;
	var returnHtml = '';
	returnHtml += '<a href="javascript:;" class="icon-search-look" id="view_'+id+'" title="查看"></a>';
	if(authState == 0){
		if((commonAcc != shiroUser.account) || ((commonAcc == shiroUser.account) && (commonOnwerSign == 1))){
			returnHtml += '<a data-authority="contractOrder_editOrder" href="javascript:;" class="icon-edit" id="uporder_'+id+'" title="编辑"></a>';
			returnHtml += '<a href="javascript:;" class="icon-report" id="report_'+id+'" title="上报"></a>';
		}else{
			//returnHtml += '<a data-authority="contractOrder_editOrder" href="javascript:;" class="icon-edit img-gray" title="编辑"></a>';
			//returnHtml += '<a href="javascript:;" class="icon-report img-gray" title="上报"></a>';
		}
	}else if(authState == 1){
		if((commonAcc != shiroUser.account) || ((commonAcc == shiroUser.account) && (commonOnwerSign == 1))){
			returnHtml += '<a data-authority="contractOrder_recallOrder" href="javascript:;" class="icon-cancel-contr shows-cancel-contr" id="reback_'+id+'" title="撤回"></a>';
		}else{
			//returnHtml += '<a data-authority="contractOrder_recallOrder" href="javascript:;" class="icon-cancel-contr shows-cancel-contr img-gray" title="撤回"></a>';
		}
	}else if(authState == 2){
		if((commonAcc != shiroUser.account) || ((commonAcc == shiroUser.account) && (commonOnwerSign == 1))){
			returnHtml += '<a data-authority="contractOrder_cancelOrder" href="javascript:;" class="icon-cancel-contr" id="cancelled_'+id+'" title="作废"></a>';
		}else{
			returnHtml += '';
			//<a data-authority="contractOrder_cancelOrder" href="javascript:;" class="icon-cancel-contr img-gray" title="作废"></a>
		}
	}else if(authState == 3){
		if((commonAcc != shiroUser.account) || ((commonAcc == shiroUser.account) && (commonOnwerSign == 1))){
			returnHtml += '<a data-authority="contractOrder_editOrder" href="javascript:;" class="icon-edit" id="uporder_'+id+'" title="编辑"></a>';
		}else{
			//returnHtml += '<a data-authority="contractOrder_editOrder" href="javascript:;" class="icon-edit img-gray" title="编辑"></a>';
		}
	}
	return new Handlebars.SafeString(
			returnHtml
	);
});

Handlebars.registerHelper('userDayDataIdialogBtn', function(isState,showCard,custId,custName,company,options) {
//	console.log(options);
	var returnHtml = '';
	if(showCard){
		if(isState == 0){
			if(company !="" && company!=null){
				returnHtml = '<a href="javascript:;" class="icon-cust-card" id="cardInfo_'+custId+'" custName="'+company+'" title="客户卡片"></a>';
			}else{
				returnHtml = '<a href="javascript:;" class="icon-cust-card" id="cardInfo_'+custId+'" custName="'+(!custName? "":custName)+'" title="客户卡片"></a>';
			}
		}else{
			returnHtml = '<a href="javascript:;" class="icon-cust-card" id="cardInfo_'+custId+'" custName="'+(!custName? "":custName)+'" title="客户卡片"></a>';
		}
	}else{
		returnHtml = '';
	}
	
	return new Handlebars.SafeString(
			returnHtml
	);
});

Handlebars.registerHelper('saleChanceBtn', function(isState,custId,custName,company,saleChanceId,shareAccs,options) {
//	console.log(options);
	var index = options.data.index;
	var _this = options.data.root.list[index];
	var isFollow = _this.isFollow;
	var icon_card = "";
	var icon_edit = "";
	var icon_dele = "";
	var returnHtml = '';
	if(isState == 0){
		if(company!="" && company!=null){
			icon_card += '<a href="javascript:;" class="icon-cust-card" id="cardInfo_'+custId+'" custName="'+company+'" title="客户卡片"></a>';
		}else{
			icon_card += '<a href="javascript:;" class="icon-cust-card" id="cardInfo_'+custId+'" custName="'+custName+'" title="客户卡片"></a>';
		}
	}else{
		icon_card += '<a href="javascript:;" class="icon-cust-card" id="cardInfo_'+custId+'" custName="'+custName+'" title="客户卡片"></a>';
	}
	icon_edit += '<a href="javascript:;" class="icon-edit" id="edit_'+saleChanceId+'" title="编辑"></a>';
	icon_dele += '<a href="javascript:;" class="icon-cancel-contr" id="dele_'+saleChanceId+'" title="作废"></a>';
	
	if(isFollow == 0){
		returnHtml += icon_card + icon_edit + icon_dele;
	}
	
	return new Handlebars.SafeString(
			returnHtml
	);
});

Handlebars.registerHelper('saleChanceRate', function(theorySuccessRate,options) {
//	console.log(options);
	var returnHtml = '';
	switch(theorySuccessRate){
		case 1:
			returnHtml = "0%";
			break;
		case 2:
			returnHtml = "20%";
			break;
		case 3:
			returnHtml = "50%";
			break;
		case 4:
			returnHtml = "70%";
			break;
		case 5:
			returnHtml = "90%";
			break;
		default:
			returnHtml = "";
			break;
	}
	return returnHtml;
});

Handlebars.registerHelper('judgeExportCustType', function(type,options) {
	var returnHtml = "";
	switch(type){
		case 1:
			returnHtml = "资源";
			break;
		case 2:
			returnHtml = "意向客户";
			break;
		case 3:
			returnHtml = "签约客户";
			break;
		case 4:
			returnHtml = "沉默客户";
			break;
		case 5:
			returnHtml = "流失客户";
			break;
		case 6:
			returnHtml = "通话记录";
			break;
		default:
			returnHtml = "";
			break;
	}
	return new Handlebars.SafeString(
			returnHtml
	);
});

Handlebars.registerHelper('stringToHtml', function(text,options) {
	return new Handlebars.SafeString(
			text
	);
});

Handlebars.registerHelper('judgeLeadStatus', function(status,options) {
	var returnHtml = "";
	switch(status){
		case 1:
			returnHtml = "待放款";
			break;
		case 2:
			returnHtml = "已放款";
			break;
		case 3:
			returnHtml = "驳回";
			break;
		default:
			returnHtml = "";
			break;
	}
	return new Handlebars.SafeString(
			returnHtml
	);
});

Handlebars.registerHelper('leadOperate', function(leadId,isState,custId,custName,company,status,options) {
	var icon_edit = '';
	if(status == 3){
		icon_edit = '<a href="javascript:;" class="icon-edit" data-id="'+leadId+'" title="编辑"></a>';
	}
	var icon_detail = '<a href="javascript:;" class="icon-search-look" data-id="'+leadId+'" title="查看"></a>';
	var icon_card = "";
	if(isState == 0){
		if(company!="" && company!=null){
			icon_card += '<a href="javascript:;" class="icon-cust-card" id="cardInfo_'+custId+'" custName="'+company+'" title="客户卡片"></a>';
		}else{
			icon_card += '<a href="javascript:;" class="icon-cust-card" id="cardInfo_'+custId+'" custName="'+custName+'" title="客户卡片"></a>';
		}
	}else{
		icon_card += '<a href="javascript:;" class="icon-cust-card" id="cardInfo_'+custId+'" custName="'+custName+'" title="客户卡片"></a>';
	}
	var returnHtml = "";
	
	returnHtml += icon_edit + icon_card + icon_detail;
	
	return new Handlebars.SafeString(
			returnHtml
	);
});

Handlebars.registerHelper('leadReviewOperate', function(leadId,auditStatus,options) {
	var icon = "";
	if(auditStatus == 1){
		icon = "icon-examine";
	}else {
		icon = "icon-detail";
	}
	var icon_detail = '<a href="javascript:;" class="icon-review-operate '+icon+'" data-id="'+leadId+'" data-status="'+auditStatus+'" title="详情"></a>';
	var returnHtml = "";
	
	returnHtml += icon_detail;
	
	return new Handlebars.SafeString(
			returnHtml
	);
});

Handlebars.registerHelper('loanBillPicture', function(loanBill,options) {
	var returnHtml="";
	if(loanBill){
	    returnHtml = '<a href="'+loanBill+'" target="_blank">查看</a>'
	}
	return new Handlebars.SafeString(
			returnHtml
	);
});