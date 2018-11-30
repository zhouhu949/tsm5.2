function commontWindow(planId){
	pubDivShowIframe('commontWindow',ctx+'/plan/month/team/commontWindow?planId='+planId,'计划点评',700,250);
}

function back(planId){
	pubDivDialog("bakPlanWindow","你确定要退回该计划吗？",function(){
		$.post(ctx+"/plan/month/team/bakReport",{"planId":planId},
			function(data){
			if("success"==data.status){
				$("#iframepage")[0].contentWindow.refreshPage();
			}else{
			}
		});
	});
}

function checkStatus(){
	var params = $($("#iframepage")[0].contentWindow.$("#monthPlanForm")).serialize();
	$.post(ctx+"/plan/month/team/getPlanStatus?"+params,
		function(data){
		if(data.noAuthNum >0 || data.noUpNum >0){
			msg = "";
			if(data.noUpNum >0) msg+=data.noUpSb+"计划未上报！";
			if(data.noAuthNum >0) msg+=data.noAuthPassSb+"计划未审核!";
			
			msg+="上报后下属团队无法上报审核计划，点击确定忽略，点击取消返回!";
			upReport(msg);
		}else{
			upReport();
		}
	});
}

function upReport(msg){
	savePlan(1,msg);
}

function savePlan(status,msg){
	if(msg==undefined || msg==null){
		msg="确定要保存计划吗？";
		if(status==1) msg="确定要上报计划吗？";
	}
	
	/* var msg="确定要保存计划吗？";
	if(status==1) {
		msg="确定要上报计划吗？";
	}  */
	
	pubDivDialog("savePlanWindow",msg,function(){
		var params = $($("#iframepage")[0].contentWindow.$("#monthPlanForm")).serialize();
		$.post(ctx+"/plan/month/team/saveTeamPlan?"+params+"&status="+status ,
			function(data){
			if("success"==data.status){
				changeYear(0);
			}else{
				warmDivDialog("warm",data.msg);
			}
		});
	});	
	
	/* pubDivDialog("savePlanWindow","确定要保存计划吗？",function(){
		var warpWillcustnum=$($("#iframepage")[0].contentWindow.$(".warpValue")[0]).attr("value");
		var warpSigncustnum=$($("#iframepage")[0].contentWindow.$(".warpValue")[1]).attr("value");
		var warpMoney=$($("#iframepage")[0].contentWindow.$(".warpValue")[2]).attr("value");
		var warpDesc=$($("#iframepage")[0].contentWindow.$(".warpValue")[3]).attr("value");
		
		$.post(ctx+"/plan/month/team/savePlan",
				{"planId":planId,
			"warpWillcustnum":warpWillcustnum,
			"warpSigncustnum":warpSigncustnum,
			"warpMoney":warpMoney,
			"warpDesc":warpDesc},
			function(data){
			if("success"==data.status){
				
			}else{
			}
		});
	});	 */
	
}

function authWindow(planId,userName,groupPlanId){
	//showIframe('alert_mp_team_edit_edit_c','alert_mp_team_edit_edit_c.html','计划审核（成员一）',900,580);
	pubDivShowIframe('authWindow',ctx+'/plan/month/team/authWindow?planId='+planId+'&groupPlanId='+groupPlanId,'计划审核（'+userName+'）',900,510);
}

function showDetail(planId,groupName,type,isFuture){
	if(type==0){
		pubDivShowIframe('detailWindow',ctx+'/plan/month/team/detailWindow?planId='+planId+'&isFuture='+isFuture,'详细计划（'+groupName+'）',900,500);
	}else if(type==1){
		pubDivShowIframe('detailWindow',ctx+'/plan/month/group/detailWindow?planId='+planId+'&isFuture='+isFuture,'详细计划（'+groupName+'）',900,500);
	}
}

function showHistory(groupId,groupName){
	//showIframe('alert_mp_team_edit_see_b','alert_mp_team_edit_see_b.html','历史计划走势（成员一）',915,380);
	pubDivShowIframe('historyWindow',ctx+'/plan/month/team/historyWindow?groupId='+groupId,'历史计划走势（'+groupName+'）',915,380);
}

function warpWindow(params){
	//showIframe('alert_mp_team_edit_edit_f','alert_mp_team_edit_edit_f.html','偏差值编辑',700,380);
	pubDivShowIframe('warpWindow',ctx+'/plan/month/team/warpWindow?type=group&'+params,'偏差值编辑',700,380);
	//pubDivShowIframe('warpWindow',ctx+'/plan/month/team/warpWindow?planId='+planId,'偏差值编辑',700,380);
}

$(function(){
	// 部门选择下拉框
	$(".hyx-aspa-time").find('.select').each(function(){
        var s=$(this);
        var z=parseInt(s.css("z-index"));
        var dt=$(this).children("dt");
        var dd=$(this).children("dd");
        var _show=function(){dd.slideDown(200);dt.addClass("cur");s.css("z-index",z+1);};   //展开效果
        var _hide=function(){dd.slideUp(200);dt.removeClass("cur");s.css("z-index",z);};    //关闭效果
        dt.click(function(){dd.is(":hidden")?_show():_hide();});
        if(dd.find('a').hasClass('diy') == true){
            dd.find(".diy").parent().siblings().click(function(){dt.html($(this).html());_hide();});     //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）

        }else{
            dd.find("a").click(function(){dt.html($(this).html());_hide();});     //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）
        }
        
        $(document).click();
        $(document).click(function(i){
            !$(i.target).parents(".select").first().is(s) ? _hide():"";
        });
        
    });
    
	$(".canl-look").click(function(){
		var id=$(this).attr("planId");
		var planMonth=$(this).attr("planMonth");
		//$("#planMonth").val(planMonth);
		$(this).css({"color":"#777","text-decoration":"none"});
		$(this).siblings(".canl-analyse").css({"color":"blue","text-decoration":"underline"});
		$("#iframepage").attr("src",ctx+"/plan/month/group/monthView_p?id="+id+"&planYear="+planYear+"&planMonth="+planMonth);
	});
	
	$(".canl-edit").click(function(){
		var id=$(this).attr("planId");
		var planMonth=$(this).attr("planMonth");
		$(this).css({"color":"#777","text-decoration":"none"});
		$(this).siblings(".canl-analyse").css({"color":"blue","text-decoration":"underline"});
		$("#iframepage").attr("src",ctx+"/plan/month/group/monthEdit?groupId="+groupId+"&id="+id+"&planYear="+planYear+"&planMonth="+planMonth+'&isFirst=true&groupType=1');
	});
	
	
	$(".canl-analyse").click(function(){
		var planMonth=$(this).attr("planMonth");
		//$("#planMonth").val(planMonth);
		$(this).css({"color":"#777","text-decoration":"none"});
		$(this).siblings(".canl-look").css({"color":"blue","text-decoration":"underline"});
		$(this).siblings(".canl-edit").css({"color":"blue","text-decoration":"underline"});
		$("#iframepage").attr("src",ctx+"/plan/month/group/monthAnaly?groupId="+groupId+"&planYear="+planYear+"&planMonth="+planMonth);
	});
	$(".no-exec").click(function(){
		var id=$(this).attr("planId");
		var planMonth=$(this).attr("planMonth");
		var status=$(this).attr("status");
		var authState=$(this).attr("authstate");
		//$("#planMonth").val(planMonth);
		//未上报可以编辑
		//驳回可以编辑
		if(editRole &&(status==0 || (status==1 && authState==0))){
			$("#iframepage").attr("src",ctx+"/plan/month/group/monthEdit?groupId="+groupId+"&id="+id+"&planYear="+planYear+"&planMonth="+planMonth+'&isFirst=true&groupType=1');
		}else{
			$("#iframepage").attr("src",ctx+"/plan/month/group/monthView?id="+id+"&planYear="+planYear+"&planMonth="+planMonth);
		}
		
	});
	
	$(".no-plan").click(function(){
		var planMonth=$(this).attr("planMonth");
		//$("#planMonth").val(planMonth);
		if(editRole)	$("#iframepage").attr("src",ctx+"/plan/month/group/monthEdit?groupId="+groupId+"&planYear="+planYear+"&planMonth="+planMonth+"&isNew=true&isFirst=true&groupType=1");
	});
	// 年份增减
var timeDiv = $('.hyx-aspa-time');
timeDiv.find('.left').click(function(){
	changeYear(-1);
});
timeDiv.find('.right').click(function(){
		changeYear(1);
	});
});
	
function setGroupId(groupId,groupName){
	$("#groupId").val(groupId);
	$("#groupName").val(groupName);
	$("#yearPlanForm").submit();
}
	
function changeYear(i){
	$("#planYear").val(parseInt($("#planYear").val())+i);
	$('form')[0].submit();
}