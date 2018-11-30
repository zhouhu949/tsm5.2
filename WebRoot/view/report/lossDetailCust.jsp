<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>销售统计-流失客户统计（部门）</title>
    <script type="text/javascript">
        $(function () {
        	$('#queryId').click(function(){
        		$("#form").attr("action",ctx+"/report/lossCust/lossDetail");
        		$('#form').submit()
        	})
        	
           	$('#exportId').click(function(){
 				$("#form").attr("action",ctx+"/report/lossCust/exportDetail");
 				$("#form").submit();
         	})   	
        	

        });

        function setGroup(groupId){
        	$('#deptId').val(groupId);
        }
    </script>
</head>
<body>
<form id="form" action="${ctx }/report/lossCust/lossDetail" method="post">
<input type="hidden" name="currDate" value="${currDate }" id="currDate"/>
<input type="hidden" name="deptId" value="${deptId }" id="deptId"/>
<c:set var="oDateName" value="请选择" />
	<div class="sales-statis-conta clearfix">
<div class="hyx-silent-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">流失客户统计</label>
		<label class="fl_l">年月：</label></label><label class="fl_l" for="" style="margin-right:30px;">${currDate }</label>
		<div class="com-search-report fl_l" style="margin-top:0;min-height:0">
					<label class="fl_l" for="" >部门名称：</label>
					<dl class="select" style="z-index: 1;">
		            <c:choose>
		               <c:when test="${! empty teamGroups }">
		                  <c:forEach items="${teamGroups }" var="group">
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
			                  <c:forEach items="${teamGroups }" var="group">
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
					<th><span class="sp sty-borcolor-b">小组</span></th>
					<th><span class="sp sty-borcolor-b">小组成员</span></th>
					<th><span class="sp sty-borcolor-b">签约客户总数（个）</span></th>
					<th><span class="sp sty-borcolor-b">流失客户总数（个）</span></th>
					<th><span class="sp sty-borcolor-b">本月流失客户数（个）</span></th>
					<th><span class="sp sty-borcolor-b">本月到期客户数（个）</span></th>
					<th><span class="sp sty-borcolor-b">客户流失率</span></th>
				</tr>
			</thead>
			<tbody>              
            <c:choose>
               <c:when test="${ !empty list }">
                   <c:forEach items="${ list}" var="cust" varStatus="vs">
			            <tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">
							<td><div class="overflow_hidden w100" title="${ cust.deptName}">${ cust.deptName}</div></td>
							<td><div class="overflow_hidden w100" title="${cust.account }">${cust.account }</div></td>
							<td><div class="overflow_hidden w120" title="${cust.signTotal }">${cust.signTotal }</div></td>
							<td><div class="overflow_hidden w120" title="${cust.lossTotal }">${cust.lossTotal }</div></td>
							<td><div class="overflow_hidden w120" title="${cust.addLossTotal }">${cust.addLossTotal }</div></td>
							<td><div class="overflow_hidden w120" title="${cust.expireCustTotal }">${cust.expireCustTotal }</div></td>
							<td><div class="overflow_hidden w120" title="${cust.lossRate }%">${cust.lossRate }%</div></td>
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