var planChanges = new Array();
var erro = new Array();
var reason=null;
//输入框格式校验正则
var reg = /^\d{1,5}(\.\d{1,2})?$/;
//切换年份
function changeYearFn(i){
	date.setFullYear(date.getFullYear()+i);
	$("#dateTime").val(date.getTime());
	$("#yearPlanForm").submit();
}

function getPlanChanges(){
	planChanges = new Array();
	erro = new Array();
	var  inputs = $(".moneyInput[month!='total']");
	for(var i=0;i<inputs.length;i++){
		var input = inputs[i];
		var newValue = $(input).val();
		var dbValue = $(input).attr("dbValue");
		
		if(dbValue!=newValue){
			var dbId = $(input).attr("dbId");
			var groupId = $(input).attr("groupId");
			var groupName = $(input).attr("groupName");
			var month = $(input).attr("month");
			
			if(!isNaN(newValue) && reg.test(newValue)){
				newValue=Number(newValue);
				var change = new PlanChange(dbId,groupId,groupName,month,newValue);
				planChanges.push(change);
			}else{
				//格式校验错误
				var change = new PlanChange(dbId,groupId,groupName,month,newValue);
				erro.push(change);
			}
		}
	}
}

function PlanChange(id,groupId,groupName,month,money){
	return {"id":id,"groupId":groupId,"groupName":groupName,"month":month,"money":money};
}

function confirmSave(){
	
	$.post(ctx+"/plan/year/savePlan",{"planChanges":JSON.stringify(planChanges),"reason":reason,"planYear":date.getFullYear()},function(data){
		if(data=="success"){
			changeYearFn(0);
		}else{
			wrongDivDialog("erro","保存年度计划失败！");
		}
	});
}

//保存计划
function saveYearPlan(){
	getPlanChanges();
	if(erro.length > 0){
		iDialogMsg("确认","输入格式仅允许保留两位小数！");
	}else if(planChanges.length == 0){
		pubDivDialog("noChangeConfirm","年度计划未修改！",function(){
			changeYearFn(0);
		});
	}else{
		if(ifInit){
			confirmSave();
		}else{
			pubDivShowIframe('planContextWindow', ctx+"/plan/year/planContextWindow",'规划变更说明',700,250);
		}
	}
}

function buttonBind(){
	$('.demoBtn_a').click(function(){
		pubDivShowIframe('historyWindow', ctx+"/plan/year/history",'历史走势',915,380);
	});
	
	$("input").change(function(){
		var _this=$(this);
		value= $(_this).val();
		
		$(_this).parent("div").attr("title",value);
		if(!isNaN(value)&&(reg.test(value))){
			var tr = $(_this).parents("tr");
			var tds = tr.find('td:gt(0)');
			var total = 0;
			for(var i=0;i<tds.length-1;i++){
				total+=parseFloat($($(tds[i]).find("input")[0]).val())*100;
			}
			total = total/100;
			$(tr).find("td").last().find("input").val(total);
			$(tr).find("td").last().find("input").parent("div").attr("title",total);
			
			var month = $(this).attr("month");
			
			var tbody = $(_this).parents("tbody");
			var trs = $(tbody).find("tr");
			var monthTotal = 0;
			for(var i=0;i<trs.length-1;i++){
				monthTotal+=parseFloat($($(trs[i]).find("input")[month-1]).val())*100;
			}
			monthTotal=monthTotal/100;
			$(trs.last().find("input")[month-1]).val(monthTotal);
			$(trs.last().find("input")[month-1]).parent("div").attr("title",monthTotal);
			
			var allTotal = 0;
			var inputs = trs.last().find("input");
			for(var i=0;i<inputs.length-1;i++){
				allTotal += parseFloat($(inputs[i]).val())*100;
			}
			allTotal = allTotal/100;
			$(trs.last().find("input")[12]).val(allTotal);
			$(trs.last().find("input")[12]).parent("div").attr("title",allTotal);;
		}else{
			iDialogMsg("确认","输入格式仅允许保留两位小数！");
		}
	});
	
	// 保存按钮
	$('.hyx-aspa-save').click(function(){ 
		saveYearPlan();
	});
	
	// 取消按钮
	$('.hyx-aspa-cancel').click(function(){ 
		changeYearFn(0);
	});
}

$(function(){
	buttonBind();
});