/*日志-点击分享按钮*/
var pageType="view";
var planChanges = new Array();
function url_click(id){
	
}

function savePlan(){
	var reason = $("#reason").val();
	$.post(ctx+"/plan/savePlan",{"planChanges":JSON.stringify(planChanges),"reason":reason,"planYear":planYear},function(data){
		if(data=="success"){
			refreshPage();
			$("#confirm").dialog("close");
		}else{
			$.messager.alert('警告','保存年度计划失败！');
		}
		
	});
}

function getPlanChanges(){
	planChanges = new Array();
	var  inputs = $(".moneyInput");
	for(var i=0;i<inputs.length;i++){
		var input = inputs[i];
		var newValue = $(input).val();
		var dbValue = $(input).attr("dbValue");
		if(dbValue!=newValue){
			var dbId = $(input).attr("dbId");
			var groupId = $(input).attr("groupId");
			var groupName = $(input).attr("groupName");
			var month = $(input).attr("month");
			var change = new PlanChange(dbId,groupId,groupName,month,newValue);
			planChanges.push(change);
		}
	}
}

function showPlanTrend(){
	var fromYearHtml="";
	var toYearHtml="";
	for(var i=0;i<6;i++){
		if(i==0){
			toYearHtml+='<option value ="'+(server_date.getFullYear()-i)+'" selected = "selected")>'+(server_date.getFullYear()-i)+'</option>';
			fromYearHtml+='<option value ="'+(server_date.getFullYear()-i)+'">'+(server_date.getFullYear()-i)+'</option>';
		}else if(i==2){
			fromYearHtml+='<option value ="'+(server_date.getFullYear()-i)+'" selected = "selected")>'+(server_date.getFullYear()-i)+'</option>';
			toYearHtml+='<option value ="'+(server_date.getFullYear()-i)+'">'+(server_date.getFullYear()-i)+'</option>';
		}else{
			toYearHtml+='<option value ="'+(server_date.getFullYear()-i)+'">'+(server_date.getFullYear()-i)+'</option>';
			fromYearHtml+='<option value ="'+(server_date.getFullYear()-i)+'">'+(server_date.getFullYear()-i)+'</option>';
		}
	}
	$("#year_select_from").html(fromYearHtml);
	$("#year_select_to").html(toYearHtml);
	
	$.post(ctx+"/plan/querySaleGroup",function(model){
		if(model.status=="success"){
			var data = model.data;
			if(data!=null &&data.length>0){
				var groupHtml="<option>全部</option>";
				for(var i=0;i<data.length;i++){
					groupHtml+='<option value ="'+data[i].groupId+'">'+data[i].groupName+'</option>';
				}
				$("#group_select").html(groupHtml);
			}else{
				$.messager.alert('警告','未查询到销售部门！');
			}
			
		}else{
			$.messager.alert('警告','获取部门信息失败！');
		}
		
	});
	
	$("#plan_trend_window").dialog("open");
	var fromYear = null;
	var toYear = null;
	var groupId = null;
	$.post(ctx+"/plan/queryPlanTrend",{"fromYear":fromYear,"toYear":toYear,"groupId":groupId},function(model){
		if(model.status=="success"){
			$("#plan_trend_window .body").html("<div>"+JSON.stringify(model.data)+"</div>");
		}else{
			$.messager.alert('警告','保存年度计划失败！');
		}
	});
}

function showPlanLog(){
	$.post(ctx+"/plan/log",{"planYear":planYear},function(model){
		if(model.status=="success"){
			$("#plan_log_window .body").html("<div>"+JSON.stringify(model.data)+"</div>");
			$("#plan_log_window").dialog("open");
		}else{
			$.messager.alert('警告','查询变更日志失败！');
		}
	});
	
}

function PlanChange(id,groupId,groupName,month,money){
	return {"id":id==""?null:id,"groupId":groupId,"groupName":groupName,"month":month,"money":money};
}

function buttonBind(){
	//上一年
	$("#lastYear").click(function(){
		planYear--;
		$("#planYear").html(planYear+"年度规划");
		refreshPage();
	});
	
	//下一年
	$("#nextYear").click(function(){
		planYear++;
		$("#planYear").html(planYear+"年度规划");
		refreshPage();
	});
	
	//历史规划走势
	$("#plan_trend_button").click(function(){
		showPlanTrend();
	});
	
	//历史规划走势
	$("#plan_log_button").click(function(){
		showPlanLog();
	});
	
	$("#plan_edit").click(function(){
		if(planYear <server_date.getFullYear()){
			$.messager.alert('警告','不能编辑过去年份的计划！');
		}else{
			pageType="edit";
			initPageData();
		}
	});
	
	$("#plan_save").click(function(){
		getPlanChanges();
		if(planChanges.length == 0){
			$.messager.alert('警告','年度计划未修改！');
			pageType="view";
			initPageData();
		}else{
			$("#confirm").dialog("open");
		}
	});
	
	$("#confirm_save").click(function(){
		//TODO 校验
		if(1){
			savePlan();
		}
		
	});
	
	$("#confirm_cancel").click(function(){
		$("#confirm").dialog("close");
	});
}

function getPlanByMonth(month,plans){
	for(var i in plans){
		if(plans[i].planMonth ==month){
			return plans[i];
			break;
		}
	}
	return null;
}

function refreshPage(){
	$.post(ctx+"/plan/refresh",{"planYear":planYear},function(model){
		pageType="view";
		data= model.data;
		server_date = new Date(model.date);
		initPageData();
	});
}

function initPageData(){
	//总年计划
	var yearPlanCount = 0;
	//目前计划
	var currentPlanCount =0;
	//目前完成
	var currentCompleteCount =0;

	var html="";
	for(var i = 0;i<data.length;i++){
		var temp = data[i];
		var groupName = temp.teamGroupBean.groupName;
		var groupId=temp.teamGroupBean.groupId;
		var plans = temp.planSaleYearBeans;
		html+='<tr>'+
		'	<td >'+temp.teamGroupBean.groupName+'</td>';
		var count = 0;
		for(var j=1;j<=12;j++){
			var plan = getPlanByMonth(j,plans);
			var planMoney=(plan!=null?plan.planMoney:0);
			//统计数字
			//**************************************************************
			count+=planMoney;											//**
			if(j<=(server_date.getMonth()+1)){							//**	
				currentPlanCount +=planMoney;							//**
				currentCompleteCount +=(plan!=null?plan.factMoney:0);	//**
			}															//**
			//**************************************************************
			
			if(pageType=="edit"&&((j>=(server_date.getMonth()+1)&&planYear==server_date.getFullYear())||(planYear>server_date.getFullYear()))){
				html+='	<td><input class="moneyInput" month ="'+j+'"groupId="'+groupId+'" groupName="'+groupName+'" value="'+planMoney+'" dbValue="'+planMoney+'" dbId="'+(plan!=null?plan.id:'')+'" /></td>';
			}else{
				html+='	<td>'+planMoney+'</td>';
			}
		}
		html+="<td>"+count+"</td>";
		html+='</tr>';
		yearPlanCount+=count;
	}
	$("#plan_details").html(planYear+"年全年计划：应完成"+yearPlanCount+"万销售额，截止到"+(server_date.getMonth()+1)+"月底，计划应完成"+currentPlanCount+"万销售额，实际完成"+currentCompleteCount+"万销售额，完成率为"+(isNaN(num =( currentCompleteCount/currentPlanCount*100).toFixed(2))?0:num)+"%");
	$(".gridtable tbody").html(html);
}

function init(){
	$("#planYear").html(planYear+"年度规划");
	initPageData();
	buttonBind();
};

$(document).ready(function() {
	init();
});
