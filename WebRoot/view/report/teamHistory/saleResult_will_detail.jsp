<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<head>
<%@ include file="/common/include.jsp"%>

<title>销售统计-小组历史数据-意向联系明细</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static//easyui/themes/default/easyui.css">
<style>
.com-table table thead tr  th{font-weight:normal;font-size:12px;}
.tree-title{width:200px;}
.com-search .select dd{top:30px;}
.com-search .select dt.cur{border:solid #e1e1e1 1px !important;}
.select dd ul{overflow-x:hidden;}
.select dd .easyui-tree{width:260px !important;min-height:40px;}

.bomp_ic_tip{position:relative;width:100%;height:30px;border-bottom:solid #979797 1px;margin-top:15px;}
.bomp_ic_tip .list{position:absolute;left:7px;bottom:-1px;width:auto;height:30px;}
.bomp_ic_tip .list li{float:left;width:100px;height:28px;line-height:28px;text-align:center;font-size:14px;color:#636363;border:solid #979797 1px;background-color:#e2e2e2;cursor:pointer;}
.bomp_ic_tip .list .li_click{color:#000;background-color:#fff;border-bottom:solid #fff 1px;}
</style> 

</head>
<body> 
<div class="sales-statis-conta">
	<p class="hyx-silent-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">个人明细</label>
	</p>
	<div class="com-table hyx-mpe-table hyx-cm-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">日期</span></th> 
					<th><span class="sp sty-borcolor-b">计划联系数</span></th>
					<th><span class="sp sty-borcolor-b">实际联系数</span></th>
					<th><span class="sp sty-borcolor-b">转签约</span></th>
					<th><span class="sp sty-borcolor-b">意向变更</span></th>
					<th><span class="sp sty-borcolor-b">转公海</span></th>
					<th><span class="sp sty-borcolor-b">未联系</span></th>
					<th>联系无变化</th>
				</tr>
			</thead>
			<tbody>              
				<c:choose>
				<c:when test="${!empty list}">
					<c:forEach items="${list }" var="bean" varStatus="i">
					<tr class="${i.index%2!=0?'sty-bgcolor-b':''}">
						<td><div class="overflow_hidden w120" title="<fmt:formatDate value="${bean.reportDate }" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${bean.reportDate }" pattern="yyyy-MM-dd"/></div></td>
						<td><div class="overflow_hidden w70" title="${bean.willPlanNum }">${bean.willPlanNum }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.willTotalNum }">${bean.willTotalNum }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.willSignNum }">${bean.willSignNum }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.willCustNum }">${bean.willCustNum }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.willPoolNum }">${bean.willPoolNum }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.willNoContact }">${bean.willNoContact }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.willNoNum }">${bean.willNoNum }</div></td>
					</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="no_data_tr"><td><b>当前列表无数据！</b></td></tr>
				</c:otherwise>
			    </c:choose>
		</table>
	</div>
	<div class="com-bot">${item.page.pageStr}</div>	
</div>
</body>
</html>