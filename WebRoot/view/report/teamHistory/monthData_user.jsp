<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售统计-小组历史数据-月统计分析</title>
<!--本页面样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<style>
	.com-table table thead tr  th{font-weight:normal;font-size:12px;}
</style>
<script type="text/javascript">
$(function(){
/* 	var height = $(".person-static-max").height()+30;
	window.parent.$("#iframepage").css({"height":height+"px"}); */
	var height = $(".sales-statis-conta").height()+30;
	var dataData_group_height = window.parent.$(".dataData_group").height();
	var iframepage_height = parseInt(window.parent.parent.$("#iframepage_height").val());
	window.parent.$("#iframepage_2").css({"height":height+"px"});
	window.parent.parent.$("#iframepage_1").css({"height":(height+dataData_group_height)+"px"});
	window.parent.parent.parent.$("#iframepage").css("height",(height+dataData_group_height+iframepage_height)+"px");
	
	
	
	$("span[sort=true]").parent().click(function(){
		var orderColumn = $(this).find('span[sort=true]').attr("column");
		var orderType = '';
		if($(this).find('span[sort=true]').hasClass("td_sort_desc")){
			orderType = 'ASC';
		}else{
			orderType = 'DESC';
		}
		$("#column").val(orderColumn);
		$("#orderType").val(orderType);
		$("#form").submit();
	});
	
	$("#doExport").click(function(){
		var fromDateStr = $("#dateStr").val();
		var groupId = $("#groupId").val();
		var column = $("#column").val();
		var orderType = $("#orderType").val();
		var type = 1;//日分析为0 月分析为1
		var urls = ctx+"/report/teamHistoryData/exportDataUser?dateStr="+fromDateStr +"&&groupId="+groupId +"&&column="+column +"&&orderType="+orderType+"&&type="+type ;
		window.parent.parent.pubDivShowIframe("teamDay_output",urls,"导出分组选择",360,300)
	});
});
</script>
</head>
<body>
<form id="form" action="${ctx}/report/teamHistoryData/monthDataUser" method="post">
<input type="hidden" name="dateStr" id="dateStr" value="${dateStr }" />
<input type="hidden" name="groupId" id="groupId" value="${groupId }" />
<input type="hidden" name="column" id="column" value="${column }" />
<input type="hidden" name="orderType" id="orderType" value="${orderType}" />
</form>
<div class="sales-statis-conta clearfix">
	<p class="hyx-silent-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">个人统计</label>
		<a class="com-btna fl_l" id="doExport" href="javascript:;"><i class="min-remove-resou"></i><label class="lab-mar">导出</label></a>
	</p>
	<div class="com-table hyx-mpe-table hyx-cm-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">销售姓名</span></th> 
					<th><span class="sp sty-borcolor-b">销售帐号</span></th> 
					<th><span class="sp sty-borcolor-b">呼出总数（已接/总数）<span sort="true" column="call_out_num" class="${((column eq 'call_out_num') && (orderType eq 'DESC')) ? 'td_sort_desc' :  'td_sort_asc'}"></span></span></th>
					<th><span class="sp sty-borcolor-b">呼叫资源数（个）</span></th>
					<th><span class="sp sty-borcolor-b">有效呼叫（次）<span sort="true" column="valid_call_out" class="${((column eq 'valid_call_out') && (orderType eq 'DESC')) ? 'td_sort_desc' :  'td_sort_asc'}"></span></span></th>
					<th><span class="sp sty-borcolor-b">计费时长（分钟）<span sort="true" column="charge_time" class="${((column eq 'charge_time') && (orderType eq 'DESC')) ? 'td_sort_desc' :  'td_sort_asc'}"></span></span></th>
					<th><span class="sp sty-borcolor-b">呼出时长（分钟）<span sort="true" column="call_time" class="${((column eq 'call_time') && (orderType eq 'DESC')) ? 'td_sort_desc' :  'td_sort_asc'}"></span></span></th>
					<th><span class="sp sty-borcolor-b">呼入时长（分）<span sort="true" column="in_call_time" class="${((column eq 'in_call_time') && (orderType eq 'DESC')) ? 'td_sort_desc' :  'td_sort_asc'}"></span></span></th>
					<th><span class="sp sty-borcolor-b">新增意向数（个）<span sort="true" column="will_num" class="${((column eq 'will_num') && (orderType eq 'DESC')) ? 'td_sort_desc' :  'td_sort_asc'}"></span></span></th>
					<th><span class="sp sty-borcolor-b">新增签约数（个）<span sort="true" column="sign_num" class="${((column eq 'sign_num') && (orderType eq 'DESC')) ? 'td_sort_desc' :  'td_sort_asc'}"></span></span></th>
					<th><span class="sp sty-borcolor-b">回款金额（万元）<span sort="true" column="sign_money" class="${((column eq 'sign_money') && (orderType eq 'DESC')) ? 'td_sort_desc' :  'td_sort_asc'}"></span></th>
					<th><span class="sp sty-borcolor-b">签单金额（万元）</span></th>
					<th><span class="sp sty-borcolor-b">预期签单金额（万元）</span></th>
				</tr>
			</thead>
			<tbody>              
				<c:choose>
				<c:when test="${!empty list}">
					<c:forEach items="${list }" var="bean" varStatus="i">
					<tr class="${i.index%2!=0?'sty-bgcolor-b':''}">
						<td style="width:100px;"><div class="overflow_hidden w100 link" title="${bean.userName }">${bean.userName }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.account }">${bean.account }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.callInNum }/${bean.callOutNum }">${bean.callInNum }/${bean.callOutNum }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.callRes }">${bean.callRes }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.validCallOut }">${bean.validCallOut }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.chargeTime }">${bean.chargeTime }</div></td>
						<td><div class="overflow_hidden w120" title="${bean.callTime }">${bean.callTime }</div></td>
						<td><div class="overflow_hidden w100" title="${bean.inCallTime }">${bean.inCallTime }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.willNum }">${bean.willNum }</div></td>
						<td><div class="overflow_hidden w120" title="${bean.signNum }">${bean.signNum }</div></td>
						<td><div class="overflow_hidden w80" title="${bean.signMoney }">${bean.signMoney }</div></td>
	                <td><div class="overflow_hidden w100" title="${bean.saleMoney }">${bean.saleMoney }</div></td>
	                <td><div class="overflow_hidden w100" title="${bean.saleChanceMoney }">${bean.saleChanceMoney }</div></td>
					</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="no_data_tr"><td style="text-align: center;"><b>当前列表无数据！</b></td></tr>
				</c:otherwise>
			    </c:choose> 
			</tbody>
		</table>
	</div>
</div>
</body>
</html>