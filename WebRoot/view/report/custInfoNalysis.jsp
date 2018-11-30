<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<title>资源信息分析</title>
<%@ include file="/common/include.jsp"%>
    <script type="text/javascript">
        $(function () {
	       	 $('#queryId').click(function(){
					$("#form").attr("action",ctx+"/report/custInfoNalysis");
					$("#form").submit();
	     	 })        	

        	$('#exportId').click(function(){
				$("#form").attr("action",ctx+"/report/custInfoNalysis/export");
				$("#form").submit();
        	})
        });
        
        function setGroup(groupId){
        	$('#groupId').val(groupId);
        }
    </script>
</head>
<body>
<form id="form" action="${ctx }/report/custInfoNalysis" method="post">
<input type="hidden" id="groupId" name="groupId" value="${groupId}">
<c:set var="oDateName" value="请选择" />
<div class="sales-statis-conta clearfix">
    <div class="hyx-silent-p clearfix">
        <div class="com-search fl_l" style="margin-top:0;min-height:0">
            <label class="fl_l" >资源分组：</label>
            <dl class="select" style="z-index: 1;">
            <c:choose>
               <c:when test="${! empty groupList }">
                  <c:forEach items="${groupList }" var="group">
                     <c:if test="${groupId eq group.resGroupId }">
                         <c:set var="oDateName" value="${group.groupName }" />
                     </c:if>
                  </c:forEach>
               </c:when>
               <c:when test="${ empty groupId  or '' eq groupId}">
                   <c:set var="oDateName" value="请选择" />
               </c:when>               
            </c:choose>
                <dt style="border:1px solid #e1e1e1 !important;">${oDateName }</dt>
                <dd>
                    <ul>
	                  <li><a title="" href="###" onclick='setGroup("")'>全部</a></li>
	                  <c:forEach items="${groupList }" var="group">
	                         <li><a title="${group.groupName }" href="###" onclick='setGroup("${group.resGroupId}")'>${group.groupName }</a></li>
	                  </c:forEach>                      
                    </ul>
                </dd>
            </dl>
        </div>
        <a href="###" class="com-btna fl_l" id='queryId'><i class="min-search"></i><label class="lab-mar">查询</label></a>
        <a href="###" class="com-btna fl_r" id="exportId"><i class="min-remove-resou"></i><label class="lab-mar">导出</label></a>
    </div>
    <div class="com-table hyx-mpe-table hyx-cm-table" style="margin-top:5px;">
        <table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
            <thead>
            <tr class="sty-bgcolor-b">
                <th><span class="sp sty-borcolor-b">资源组</span></th>
                <th><span class="sp sty-borcolor-b">资源总数（个）</span></th>
                <th><span class="sp sty-borcolor-b">资源拨通人数（个）</span></th>
                <th><span class="sp sty-borcolor-b">资源拨通率</span></th>
                <th><span class="sp sty-borcolor-b">有效通话人数（个）</span></th>
                <th><span class="sp sty-borcolor-b">资源有效通话率</span></th>
                <th><span class="sp sty-borcolor-b">转意向人数（个）</span></th>
                <th><span class="sp sty-borcolor-b">意向转化率</span></th>
                <th><span class="sp sty-borcolor-b">签约客户数（个）</span></th>
                <th>签约转化率</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
               <c:when test="${ !empty list }">
                   <c:forEach items="${ list}" var="cust" varStatus="vs">
			            <tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">
			                <td><div class="overflow_hidden w100" title="${cust.groupName }">${cust.groupName }</div></td>
			                <td><div class="overflow_hidden w100" title="">${cust.resTotal }</div></td>
			                <td><div class="overflow_hidden w100" title="">${cust.calledTotal }</div></td>
			                <td><div class="overflow_hidden w120" title="">${cust.calledRate }%</div></td>
			                <td><div class="overflow_hidden w120" title="">${cust.effectCallTotal }</div></td>
			                <td><div class="overflow_hidden w120" title="">${cust.effectCallRate }%</div></td>
			                <td><div class="overflow_hidden w100" title="">${cust.willTotal }</div></td>
			                <td><div class="overflow_hidden w120" title="">${cust.willRate }%</div></td>
			                <td><div class="overflow_hidden w100" title="">${cust.signTotal }</div></td>
			                <td><div class="overflow_hidden w120" title="">${cust.signRate }%</div></td>
			            </tr>                   
                   </c:forEach>
			            <tr class="${(fn:length(list) % 2) ne 0 ? 'sty-bgcolor-b' : '' }">
			                <td><div class="overflow_hidden w100" title="">合计</div></td>
			                <td><div class="overflow_hidden w100" title="">${totalCust.resTotal }</div></td>
			                <td><div class="overflow_hidden w100" title="">${totalCust.calledTotal }</div></td>
			                <td><div class="overflow_hidden w120" title="">${totalCust.calledRate }%</div></td>
			                <td><div class="overflow_hidden w120" title="">${totalCust.effectCallTotal }</div></td>
			                <td><div class="overflow_hidden w120" title="">${totalCust.effectCallRate }%</div></td>
			                <td><div class="overflow_hidden w100" title="">${totalCust.willTotal }</div></td>
			                <td><div class="overflow_hidden w120" title="">${totalCust.willRate }%</div></td>
			                <td><div class="overflow_hidden w100" title="">${totalCust.signTotal }</div></td>
			                <td><div class="overflow_hidden w120" title="">${totalCust.signRate }%</div></td>
			            </tr>                     
               </c:when>
               <c:otherwise>
						<tr>
							<td colspan="8">
								<center>当前列表无数据！</center>
							</td>
						</tr>               
               </c:otherwise>
            </c:choose>

            </tbody>
        </table>
    </div>
</form>

    <div class="person-todata-warm">
        <h2>温馨提示：</h2>
        <p><span>1、资源拨通率=资源拨通人数/资源总数</span></p>
        <p><span>2、资源有效通话率=资源有效通话人数/资源总数</span></p>
        <P><span>3、 意向转化率=转意向人数/资源总数</span></P>
        <p><span>4、签约转化率=签约客户数/资源总数</span></p>
    </div>
</div>
</body>
</html>
