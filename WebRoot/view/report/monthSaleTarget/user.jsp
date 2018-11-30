<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
<%@ include file="/common/include.jsp" %>
<title>月度销售目标执行情况</title>
<title>销售统计-月度计划执行结果-</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<script type="text/javascript" src="${ctx}/static/js/my97datepicker/rangeLimit.js${_v}"></script>
<style>
.tree-title{width:200px;}
.com-search .select dd{top:30px;min-height:70px;}
.com-search .select dt.cur{border:solid #e1e1e1 1px !important;}
.select dd ul{overflow-x:hidden;}
.select dd .easyui-tree{width:260px !important;min-height:40px;}

.hyx-silent-p{margin-bottom:0;}
</style>  

<script type="text/javascript">
$(function(){
	$(".detail").live("click",function(){
		var userId=$(this).attr("userId");
		var userName=$(this).attr("userName");
		window.parent.pubDivShowIframe("detail","${ctx }/report/teamHistoryData/saleResultDetail?pageType=will&userId="+userId+"&fromDateStr=${fromDateStr}&toDateStr=${toDateStr}",userName+"销售结果明细",800,400);
	 });
	
	$("#cfm").click(function(){
		$("#userNamesDt").html("-请选择-");
		var str="";
	 	$("input:checkbox:checked").each(function(i){
	 		if(str.length!=0)str+=",";
	 		str+=$(this).attr("userName");
	 	});
	 	
	 	$("#userNames").val(str);
	 	$("#userNamesDt").html(str);
	 	refreshPage();
	});
	
	$("#clear").click(function(){
		$("input:checkbox").removeAttr("checked");
		$("#userNames").val("");
		$("#userNamesDt").html("-请选择-");
	});
	
	//适应下拉框高度
	var dd_height = $("#tt2").parent().height();
	$(".adapt_dd_height").css("min-height",dd_height);
	
});

function refreshPage(){
	$("form")[0].submit();
} 
window.onload=function(){
	var height = $(".sales-statis-conta").height()+200;
	window.parent.$("#iframepage").css({"height":height+"px"});
};
</script>
</head>
<body>
<form action="${ctx}/report/monthSaleTarget/user">
<input id="userNames" name="userNames" type="hidden" value="${item.userNames}"/>
<input id="groupId" name="groupId" type="hidden" value="${item.groupId}"/>
<div class="hyx-silent-p clearfix">
	<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">个人统计</label>
	<div class="com-search-report fl_l" style="margin-top:-4px;min-height:0">
		<label class="fl_l" for="" style="line-height:34px;">选择人员：</label>
		<dl class="select fl_l" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-right:10px;">
			<dt id="userNamesDt">${item.userNames}</dt>
			<dd>
				<ul id="tt2" class="easyui-tree" data-options="animate:true,dnd:false" style="max-height:160px;margin-top:10px;">
					<c:forEach items="${members }" var="member">
						<c:set var="checked" value=""></c:set>
						<c:forEach items="${item.userIds}" var="id">
							<c:if test="${id== member.userId}">
								<c:set var="checked" value="checked='checked'"></c:set>
							</c:if>
						</c:forEach>
						<li><span><input name="userIds" userName="${member.memberName }" value="${member.userId }" type="checkbox" ${checked}>${member.memberName }</span></li>
					</c:forEach>
				</ul>
				<div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;">
					<a id="cfm" class="com-btnc bomp-btna com-btna-sty willo-owner-sure fl_l" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">确定</label></a>
					<a id="clear" class="com-btnd bomp-btna fl_l" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">清空</label></a>
				</div>
			</dd>
		</dl>
	</div>
</div>
<div class="com-table hyx-mpe-table hyx-cm-table adapt_dd_height" style="min-height:100px;">
	<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
		<thead>
			<tr class="sty-bgcolor-b">
				<th><span class="sp sty-borcolor-b">销售姓名</span></th>
				<th><span class="sp sty-borcolor-b">销售帐号</span></th> 
				<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
				<th><span class="sp sty-borcolor-b">新增意向数（个）</span></th>
				<th>新增签约数（个）</th>
			</tr>
		</thead>
		<tbody>              
			<c:choose>
				<c:when test="${!empty list}">
					<c:forEach items="${list }" var="bean" varStatus="i">
					<tr class="dayData ${i.index%2!=0?'sty-bgcolor-b':''}">
						<td><div class="overflow_hidden w120" title="${bean.userName}">${bean.userName}</div></td>
						<td><div class="overflow_hidden w120" title="${bean.userAcc}">${bean.userAcc}</div></td>
						<td><div class="overflow_hidden w100" title="${bean.factMoney}/${bean.planMoney}">${bean.factMoney}/${bean.planMoney}</div></td>
						<td><div class="overflow_hidden w100" title="${bean.factWillcustnum}/${bean.planWillcustnum}">${bean.factWillcustnum}/${bean.planWillcustnum}</div></td>
						<td><div class="overflow_hidden w100" title="${bean.factSigncustnum}/${bean.planSigncustnum}">${bean.factSigncustnum}/${bean.planSigncustnum}</div></td>
					</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr><td colspan="5" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
				</c:otherwise>
			    </c:choose> 
		</tbody>
	</table>
</div>
</form>
</body>
</html>