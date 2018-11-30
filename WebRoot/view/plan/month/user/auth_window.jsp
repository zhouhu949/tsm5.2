<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-月度计划（团队编辑）-编辑页面（团队）-计划审核</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
<script type="text/javascript">
var groupPlanId ="${groupPlanId}";
var planId="${dto.planMonth.id}";
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
	
	if(dContext!=null&&dContext.replace(/(^\s+)|(\s+$)/g,'').length>0){
		dContext = dContext.replace(/(^\s+)|(\s+$)/g,'');
		if(dContext.length<=500){
			$.post(ctx+"/plan/month/user/auth",{"planIdsStr":planId,"authState":authState,"context":dContext},
					function(data){
				if("success"==data.status){
					window.parent.refreshPage();
					setTimeout('closeParentPubDivDialogIframe("authWindow");',10);
				}else{
					window.parent.warmDivDialog("erro","出错!");
				}
			}); 
		}else{
			window.parent.warmDivDialog("erro","审核备注最大长度为500字!");
		}
	}else{
		window.parent.warmDivDialog("erro","审核备注不能为空!");
	}
	
	
}

</script>
</head>
<body> 
<div class='bomp-cen bomp-mtesa'>
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">重点客户</label>
	</p>
	<div class="com-table hyx-mpe-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</span></th>
					<c:if test="${isState eq 0}"><th><span class='sp sty-borcolor-b'>单位名称</span></th></c:if>
					<th><span class="sp sty-borcolor-b">客户状态</span></th>
					<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>              
				<c:choose>
				<c:when test="${dto!=null and !empty dto.custs}">
					<c:forEach items="${dto.custs}" var="cust" varStatus="i">
						<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
						<c:if test="${i.index%2==0}"><tr></c:if>
							<td><div class="overflow_hidden w80" title="${cust.custName }">${cust.custName }</div></td>
							<c:if test="${isState eq 0}">
								<td><div name="company" class="overflow_hidden w170" title="${cust.company}">${cust.company}</div></td>
							</c:if>
							<td><div name="singcustNum" class="overflow_hidden w80" value="${cust.singcustNum}" title="${cust.singcustNum==0?'新增签约':'意向客户' }">${cust.singcustNum==0?'新增签约':'意向客户' }</div></td>
							<td><div class="overflow_hidden w110" title="${cust.planMoney }">${cust.planMoney }</div></td>
							<td><div class="overflow_hidden w360" title="${cust.context }">${cust.context }</div></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr><td colspan="5" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
				</c:otherwise>
				</c:choose>
				<tr class="sty-bgcolor-b">
					<td colspan="${isState eq 0?3:2}"><div class="overflow_hidden w110">合计</div></td>
					<td><div class="overflow_hidden w110" title="${dto.custCount.planMoney}">${dto.custCount.planMoney}</div></td>
					<td><div class="overflow_hidden w110" title=""></div></td>
				</tr>
			</tbody>
		</table>
	</div>
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">偏差值调整</label>
	</p>
	<div class="com-table hyx-mpe-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">新增意向数（个）</span></th>
					<th><span class="sp sty-borcolor-b">新增签约数（个）</span></th>
					<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.planWillcustnumAdd}">${dto.planMonth.planWillcustnumAdd}</div></td>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.planSigncustnumAdd}">${dto.planMonth.planSigncustnumAdd}</div></td>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.planWillcustnumMoney}">${dto.planMonth.planWillcustnumMoney}</div></td>
					<td><div class="overflow_hidden w360" title="${dto.planMonth.planWillcustnumDesc}">${dto.planMonth.planWillcustnumDesc}</div></td>
				</tr>              
			</tbody>
		</table>
	</div>
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">调整后合计</label>
	</p>
	<div class="com-table hyx-mpe-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">新增意向数（个）</span></th>
					<th><span class="sp sty-borcolor-b">新增签约数（个）</span></th>
					<th>回款金额（万）</th>
				</tr>
			</thead>
			<tbody>              
				<tr>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.planWillcustnum}">${dto.planMonth.planWillcustnum}</div></td>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.planSigncustnum}">${dto.planMonth.planSigncustnum}</div></td>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.planMoney}">${dto.planMonth.planMoney}</div></td>
				</tr>
			</tbody>
		</table>
	</div>
	<p class='bomp-p' style="width:auto;">
		<label class='lab_a fl_l'>审核备注：</label>
		<label class="bomp-sce-area fl_l">
			<textarea id="dContext" class='area_a fl_l' style="width:753px;" value="${dto.planMonth.authDesc}">${dto.planMonth.authDesc}</textarea>
			<span>
				<c:if test="${dto.planMonth.authDesc ==null }">最多可输入500个汉字</c:if>
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