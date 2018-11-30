<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售统计-小组历史数据-月统计分析-按组分析</title>
<!--本页面样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<style>
	.com-table table thead tr  th{font-weight:normal;font-size:12px;}
</style>
<script type="text/javascript">
$(function(){
	
	$(".groupData").live("click",function(){
 		var groupId=$(this).attr("groupId");
 		$("#iframepage_2").attr("src","${ctx}/report/teamHistoryData/monthDataUser?dateStr=${dateStr}&groupId="+groupId);
 		
 		/* var html='<iframe src="${ctx}/report/teamHistoryData/dayDataGroup?date='+date+
 				'" width="100%" height="100%" id="iframepage_2" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" onload="iFrameHeight()"></iframe>';
 		$("#aaa").html(html); */
 	});
	
	$("#doExport").click(function(){
		$("form")[0].submit();
	});
});
window.onload=function(){
/* 	var height = $(".person-static-max").height()+30;
	window.parent.$("#iframepage").css({"height":height+"px"}); */
	var height = $(".dataData_group").height()+$("#iframepage_2").height();
	var iframepage_height = parseInt(window.parent.$("#iframepage_height").val());
	window.parent.$("#iframepage_1").css({"height":height+"px"});
	window.parent.parent.$("#iframepage").css("height",(height+iframepage_height)+"px");
};
</script>
</head>
<body>
<form id="form" action="${ctx}/report/teamHistoryData/monthDataGroupExport" method="post">
	<input type="hidden" name="dateStr" id="dateStr" value="${dateStr }" />
</form>
<div class="dataData_group">
<p class="hyx-silent-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">按组分析</label>
		<a class="com-btna fl_l" id="doExport" href="javascript:;"><i class="min-remove-resou"></i><label class="lab-mar">导出</label></a>
	</p>
	<div class="com-table hyx-mpe-table hyx-cm-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">小组名称</span></th> 
					<th><span class="sp sty-borcolor-b">呼出总数（已接/总数）</span></th>
					<th><span class="sp sty-borcolor-b">呼叫资源数（个）</span></th>
					<th><span class="sp sty-borcolor-b">有效呼叫（次）</span></th>
					<th><span class="sp sty-borcolor-b">计费时长（分钟）</span></th>
					<th><span class="sp sty-borcolor-b">呼出时长（分钟）</span></th>
					<th><span class="sp sty-borcolor-b">呼入时长（分）</span></th>
					<th><span class="sp sty-borcolor-b">新增意向数（个）</span></th>
					<th><span class="sp sty-borcolor-b">新增签约数（个）</span></th>
					<th><span class="sp sty-borcolor-b">回款金额（万元）</span></th>
					<th><span class="sp sty-borcolor-b">签单金额（万元）</span></th>
					<th><span class="sp sty-borcolor-b">预期签单金额（万元）</span></th>
				</tr>
			</thead>
			<tbody>              
			<c:choose>
			<c:when test="${!empty list}">
				<c:forEach items="${list }" var="bean" varStatus="i">
				<tr groupId="${bean.groupId }" class="groupData ${i.index%2!=0?'sty-bgcolor-b':''} ${i.index==0?'td-link tr-hover':''}">
					<td style="width:100px;"><div class="overflow_hidden w100 link" title="${bean.groupName }">${bean.groupName }</div></td>
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
<iframe src="${ctx}/report/teamHistoryData/monthDataUser?dateStr=${dateStr}&groupId=${list[0].groupId}" width="100%" height="100%" id="iframepage_2" frameborder="0"
			scrolling="no" marginheight="0" marginwidth="0" onload="iFrameHeight()"></iframe>
	<script type="text/javascript" language="javascript">
	function iFrameHeight() {
		var ifm= document.getElementById("iframepage_2");
		var subWeb = document.frames ? document.frames["iframepage_2"].document :ifm.contentDocument;
		if(ifm != null && subWeb != null) {
			ifm.height = subWeb.body.scrollHeight;
		}
	}
	</script>
</body>
</html>