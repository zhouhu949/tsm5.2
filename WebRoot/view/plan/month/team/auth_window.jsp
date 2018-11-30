<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-月度计划（部门编辑）-未上报（部门）-计划审核</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
<script type="text/javascript">
var groupPlanId ="${groupPlanId}";
var planId="${plan.id}";
$(function(){
    $(".sure-btn").click(function(){
    	auth(2);
	});
	$(".cancel-btn").click(function(){
		auth(0);
	});
	/*textarea提示信息*/
    $('.bomp-sce-area').find('textarea,span').click(function(){
    	var ipt_a = $(this).parent().find('textarea');
        ipt_a.focus();
        $(this).parent().find('span').hide();
        ipt_a.blur(function(){
            if($(this).val() == ''){
                $(this).parent().find('span').show();
            }
         }); 
    });
});
function auth(authState){
	var dContext=$("#dContext").val();
	$.post(ctx+"/plan/month/team/auth",{"planIdsStr":planId,"authState":authState,"context":dContext},
			function(data){
		if("success"==data.status){
			window.parent.$("#iframepage")[0].contentWindow.refreshPage();
			setTimeout('closeParentPubDivDialogIframe("authWindow");',10);
		}else{
			
		}
	});
}
</script>
</head>
<body> 
<div class='bomp-cen bomp-mtesa bomp-mteec'>
	<div class="com-table hyx-mpe-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">部门名称</span></th>
					<th><span class="sp sty-borcolor-b">新增意向数（个）</span></th>
					<th><span class="sp sty-borcolor-b">新增签约数（个）</span></th>
					<th>回款金额（万）</th>
				</tr>
			</thead>
			<tbody>              
				<tr>
					<td><div class="overflow_hidden w110" title="${plan.groupName }">${plan.groupName }</div></td>
					<td><div class="overflow_hidden w110" title="${plan.planWillcustnum }">${plan.planWillcustnum }</div></td>
					<td><div class="overflow_hidden w110" title="${plan.planSigncustnum }">${plan.planSigncustnum }</div></td>
					<td><div class="overflow_hidden w110" title="${plan.planMoney }">${plan.planMoney }</div></td>
				</tr>
				<tr class="sty-bgcolor-b">
					<td><div class="overflow_hidden w110">调整值</div></td>
					<td><div class="overflow_hidden w110" title="${plan.warpWillcustnum }">${plan.warpWillcustnum }</div></td>
					<td><div class="overflow_hidden w110" title="${plan.warpSigncustnum }">${plan.warpSigncustnum }</div></td>
					<td><div class="overflow_hidden w110" title="${plan.warpMoney }">${plan.warpMoney }</div></td>
				</tr>
				<tr>
					<td><div class="overflow_hidden w110">合计</div></td>
					<td><div class="overflow_hidden w110" title="${plan.planWillcustnum }">${plan.planWillcustnum }</div></td>
					<td><div class="overflow_hidden w110" title="${plan.planSigncustnum }">${plan.planSigncustnum }</div></td>
					<td><div class="overflow_hidden w110" title="${plan.planMoney }">${plan.planMoney }</div></td>
				</tr>
			</tbody>
		</table>
	</div>
	<p class='bomp-p'>
		<label class='lab_a fl_l'>调整原因：</label>
		<label class="bomp-sce-area" style="width:740px;height:auto;margin-top:5px">
		${plan.warpDesc }
		</label>
	</p>
	<p class='bomp-p'>
		<label class='lab_a fl_l'>审核备注：</label>
		<label class="bomp-sce-area">
			<textarea id="dContext" class='area_a' value="${dto.planMonth.authDesc}">${plan.authDesc }</textarea>
			<span>
				<c:if test="${plan.authDesc ==null }">最多可输入500个汉字</c:if>
			</span>
		</label>
	</p>
	<div class='bomb-btn bomb-btn-top'>
		<label class='bomb-btn-cen'>
			<a href="javascript:;" class="com-btna bomp-btna sure-btn com-btna-sty fl_l"><label>通过</label></a>
			<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>驳回</label></a>
		</label>
	</div>
</div>
</body>
</html>