<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
<script type="text/javascript">
    $(function () {
	       	 $('#queryId').click(function(){
					$("#form").attr("action",ctx+"/report/lossSort/lossSortDetail");
					$("#form").submit();
	     	 })
	        $('#exportId').click(function(){
   				$("#form").attr("action",ctx+"/report/lossSort/detailExport");
   				$("#form").submit();
           	})
	     	 
});
    function setGroup(groupId){
    	$('#deptId').val(groupId);
    }    
</script>
<script type="text/javascript" src="../js/table.js"></script>


<script type="text/javascript" src="../js/form.js"></script>
</head>
<body>
<form id="form" action="${ ctx}/report/lossSort/lossSortDetail" method="post">
<input type="hidden" id="startTime" name="startTime" value="${ startTime}">
<input type="hidden" id="endTime" name="endTime" value="${ endTime}">
<input type="hidden" id="deptId" name="deptId" value="${ deptId}">
<input type="hidden" id="optionId" name="optionId" value="${ optionId}">
<c:set var="oDateName" value="请选择" />
<div class="sales-statis-conta clearfix">
	<div class="hyx-silent-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius overflow_hidden" title="${optionName }客户详情">${optionName }客户详情</label>
		<label class="fl_l" for="" style="margin-right:30px;">${startTime } ~${endTime }</label>
		<div class="com-search-report fl_l" style="margin-top:0;min-height:0">
					<label class="fl_l" for="">部门名称：</label>
					<dl class="select" style="z-index: 1;">
		            <c:choose>
		               <c:when test="${! empty groupList }">
		               		<c:forEach items="${groupList }" var="group">
		                     <c:if test="${deptId eq group.groupId }">
		                         <c:set var="oDateName" value="${group.groupName }" />
		                     </c:if>
		                  </c:forEach>
		               </c:when>
		               <c:when test="${ empty deptId  or '' eq deptId}">
		                   <c:set var="oDateName" value="请选择" />
		               </c:when>               
		            </c:choose>					
						<dt style="border:1px solid #e1e1e1 !important;">--${oDateName }--</dt>
						<dd>
		                    <ul>
			                  <li><a title="" href="###" onclick='setGroup("")'>全部</a></li>
			                  <c:forEach items="${groupList }" var="group">
			                         <li><a title="${group.groupName }" href="###" onclick='setGroup("${group.groupId}")'>${group.groupName }</a></li>
			                  </c:forEach>                      
		                    </ul>	
						</dd>
					</dl>
				</div>
		<a href="###" class="com-btna fl_l" id="queryId"><i class="min-search"></i><label class="lab-mar">查询</label></a>
		<a href="###" class="com-btna fl_r" id="exportId"><i class="min-remove-resou"></i><label class="lab-mar">导出</label></a>
	</div>
	<div class="com-table hyx-mpe-table hyx-cm-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">部门</span></th>
					<th><span class="sp sty-borcolor-b">客户名称</span></th>
					<th><span class="sp sty-borcolor-b">联系人</span></th>
					<th><span class="sp sty-borcolor-b">签约日期</span></th>
					<th><span class="sp sty-borcolor-b">订单累计金额（万）</span></th>
					<th><span class="sp sty-borcolor-b">未联系天数</span></th>
					<th><span class="sp sty-borcolor-b">上次联系时间</span></th>
					<th><span class="sp sty-borcolor-b">合同失效日期</span></th>
					<th>所有者</th>
				</tr>
			</thead>
		            <c:choose>
		               <c:when test="${ !empty list }">
		                   <c:forEach items="${ list}" var="cust" varStatus="vs">
					            <tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">
									<td><div class="overflow_hidden w100" title="${ cust.deptName}">${ cust.deptName}</div></td>
									<td><div class="overflow_hidden w100" title="${cust.name }">${cust.name }</div></td>
									<td><div class="overflow_hidden w100" title="${cust.linkName }">${cust.linkName }</div></td>
									<td><div class="overflow_hidden w100" title="<fmt:formatDate value='${cust.signDate}' pattern='yyyy-MM-dd'/>"><fmt:formatDate value='${cust.signDate}' pattern='yyyy-MM-dd'/></div></td>
									<td><div class="overflow_hidden w120" title="${cust.money }">${cust.money }</div></td>
									<td><div class="overflow_hidden w120" title="${cust.dayDiff }">${cust.dayDiff }</div></td>
									<td><div class="overflow_hidden w120" title="<fmt:formatDate value='${cust.actionDate}' pattern='yyyy-MM-dd'/>"><fmt:formatDate value='${cust.actionDate}' pattern='yyyy-MM-dd'/></div></td>
									<td><div class="overflow_hidden w100" title="<fmt:formatDate value='${cust.invalidDate}' pattern='yyyy-MM-dd'/>"><fmt:formatDate value='${cust.invalidDate}' pattern='yyyy-MM-dd'/></div></td>
									<td><div class="overflow_hidden w120" title="${cust.ownerAcc }">${cust.ownerAcc }</div></td>
						        </tr>
						   </c:forEach>
						</c:when>	
				   </c:choose>
			</tbody>
		</table>
	</div>
</div>
</form>
</body>
</html>