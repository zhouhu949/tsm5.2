<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css${_v}"><!--主要样式-->
<title>个人计划时间调整</title>

<script type="text/javascript">


$(function(){
	var type="${type}";
	var custIds = "${custIds}";
	var oldPlanDate = "${planDate}";
	var today = "${today}";
	var oldSudId = "${sudId}";
	var isSubmit = true;

    $(".sure-btn").click(function(e){
    	e.stopPropagation();
    	var planDate = $("#planDate").val();
    	if(!(planDate != null && planDate !="")){
    		window.top.iDialogMsg("提示","请先选择日期！");
    	}else if(planDate==oldPlanDate){
    		window.top.iDialogMsg("提示","重新选择日期！");
    	}else{
    		if(isSubmit && custIds != null && custIds !=""){
			isSubmit = false;
			$.post(ctx+"/plan/day/moveAnotherDay",{"type":type,"custIds":custIds,"oldPlanDate":oldPlanDate,"planDate":planDate,"oldSudId":oldSudId},function(data){
				if(data.status){
					window.parent.loadData();
					setTimeout('closeParentPubDivDialogIframe("moveAnotherDayWindow");',10);
				}else{
					isSubmit = true;
					window.top.warmDivDialog("warm",data.errorMsg);
				}
			});
		}
    }
	});
    
	$(".cancel-btn").click(function(e){
		e.stopPropagation();
		closeParentPubDivDialogIframe('moveAnotherDayWindow'); 
	});
    
});
</script>
</head>
<body> 
<div class='bomp-cen' id='alert_b'>
	<div class='cust-foll-time'>
		<label class='lab_a fl_l'>变更计划时间为：</label>
		<p class='p_a fl_l'>
		<input type='text' name="planDate"  id="planDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'${today}'})" class='ipt_a' readOnly="readonly"/>
		</p>
	</div>
	<div class='bomb-btn'style="margin-top:50px;">
		<label class='bomb-btn-cen'>
			<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>确定</label></a>
			<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
		</label>
	</div>
</div>
</body>
</html>