<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>团队历史数据</title>
    <style>
        .com-table table thead tr  th{font-weight:normal;font-size:12px;}
    </style>
    <script type="text/javascript">
    var toWeekFrom="${toWeekFrom}";
	var toWeekTo="${toWeekTo}";
	var toMonthFrom="${toMonthFrom}";
	var toMonthTo="${toMonthTo}";
	
    window.onload=function(){
        var height = $(".person-static-max").height()+30;
        window.parent.$("#iframepage").css({"height":height+"px"});
    };
    
    $(function(){
    	$("#doQuery").click(function(){
    		doQuery();
    	});
    });
    
    function setGroupId(value){
    	$("#groupId").val(value);
    }
    function doQuery(){
       	$("form")[0].submit();
    }
	function toWeekFn(){
	   	$("#fromDateStr").val(toWeekFrom);
	   	$("#toDateStr").val(toWeekTo);
	}
    function toMonthFn(){
    	$("#fromDateStr").val(toMonthFrom);
    	$("#toDateStr").val(toMonthTo);
    }
    </script>
</head>
<body>
<form action="${ctx }/report/teamData${_v}" method="post">
<input type="hidden" id="groupId" name="groupId" value="${groupId }" />
<div class="sales-statis-conta">
    <div class="person-static-max">
        <div class="hyx-silent-p clearfix">

            <div class="com-search fl_l" style="margin-top:0;min-height:0">
                <label class="fl_l" for="" >部门名称：</label>
                <dl class="select" style="z-index: 1;">
	            <c:choose>
	            <c:when test="${groupId!=null}">
            	<c:forEach var="group" items="${groups}">
            		<c:if test="${group.groupId ==groupId}">
            		<dt style="border:1px solid #e1e1e1 !important;">${group.groupName }</dt>
            		</c:if>
            	</c:forEach>
            	</c:when>
            	<c:otherwise>
            	<dt style="border:1px solid #e1e1e1 !important;">全部</dt>
            	</c:otherwise>
	            </c:choose>
            	
               
                <dd>
                    <ul>
                    	<li><a title="全部" href="javascript:setGroupId(null)">全部</a></li>
                    	<c:forEach var="group" items="${groups}">
                    		<li><a title="${group.groupName }" href="javascript:setGroupId('${group.groupId}')">${group.groupName }</a></li>
                    	</c:forEach>
                    </ul>
                </dd>
            	</dl>
            </div>
            <label class="fl_l" for="">选择年月：</label>
		<span class="fl_l" style="display: inline-block; position: relative;">
		<input id="fromDateStr" name="fromDateStr" value="<fmt:formatDate value="${fromDate }" pattern="YYYY-MM-dd"/>" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({maxDate:'%y-%M',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:true})" />
		<span class="silent-custo-span fl_l">&nbsp;—&nbsp;</span>
		<input id="toDateStr" name="toDateStr" value="<fmt:formatDate value="${toDate }" pattern="YYYY-MM-dd"/>" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({maxDate:'%y-%M-%ld',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:true})" />
		</span>
		<a href="javascript:toWeekFn();" class="silent-week fl_l">本周</a>
		<a href="javascript:toMonthFn();" class="silent-month fl_l">本月</a>
		
		<a id="doQuery" href="javascript:;" class="com-btna fl_l"><i class="min-search"></i><label class="lab-mar">查询</label></a>
       <!--  <a href="###" class="com-btna fl_r"><i class="min-remove-resou"></i><label class="lab-mar">导出</label></a> -->
        <div class="static-time fl_r"><label for="">统计时间：</label><span><fmt:formatDate value="${currentDate}" pattern="YYYY年M月dd日"/></span></div>
        </div>
        <div class="com-table hyx-mpe-table hyx-cm-table" style="margin-top:5px;margin-bottom:40px;">
            <table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
                <thead>
                <tr class="sty-bgcolor-b">
                    <th rowspan="2"><span class="sp sty-borcolor-b" style="font-size:14px;font-weight:bold;">日期</span></th>
                    <th rowspan="2"><span class="sp sty-borcolor-b" style="font-size:14px;font-weight:bold;">部门名称</span></th>
                    <th rowspan="2"><span class="sp sty-borcolor-b" style="font-size:14px;font-weight:bold;">销售帐号</span></th>
                    <th rowspan="2"><span class="sp sty-borcolor-b" style="font-size:14px;font-weight:bold;">姓名</span></th>
                    <th colspan="7" class="sales-statis-th"><span class="sp sty-borcolor-b" style="font-size:14px;font-weight:bold;">资源（个）</span></th>
                    <th colspan="7" class="sales-statis-th"><span class="sp sty-borcolor-b" style="font-size:14px;font-weight:bold;">意向客户（个）</span></th>
                    <th rowspan="2"><span class="sp sty-borcolor-b" style="font-size:14px;font-weight:bold;">呼出时长（分钟）</span></th>
                    <th rowspan="2"><span class="sp sty-borcolor-b" style="font-size:14px;font-weight:bold;">回款金额（万）</span></th>
                    <th rowspan="2" style="font-size:14px;font-weight:bold;">交易客户数（个）</th>
                </tr>
                <tr class="sty-bgcolor-b">
                    <th><span class="sp sty-borcolor-b">计划联系数</span></th>
                    <th><span class="sp sty-borcolor-b">实际联系数</span></th>
                    <th><span class="sp sty-borcolor-b">转签约</span></th>
                    <th><span class="sp sty-borcolor-b">转意向</span></th>
                    <th><span class="sp sty-borcolor-b">转公海</span></th>
                    <th><span class="sp sty-borcolor-b">未联系</span></th>
                    <th><span class="sp sty-borcolor-b">联系无变化</span></th>
                    <th><span class="sp sty-borcolor-b">计划联系数</span></th>
                    <th><span class="sp sty-borcolor-b">实际联系数</span></th>
                    <th><span class="sp sty-borcolor-b">意向变更</span></th>
                    <th><span class="sp sty-borcolor-b">签约</span></th>
                    <th><span class="sp sty-borcolor-b">转公海</span></th>
                    <th><span class="sp sty-borcolor-b">联系无变化</span></th>
                    <th><span class="sp sty-borcolor-b">未联系</span></th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
					<c:when test="${!empty userList}">
						<c:forEach var="bean" items="${userList}" varStatus="i">
							<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
							<c:if test="${i.index%2==0}"><tr></c:if>
							
						<td style="width:90px;"><div class="overflow_hidden w90 link" title="<fmt:formatDate value="${bean.reportDate}" pattern="yyyy/MM/dd"/>"><fmt:formatDate value="${bean.reportDate}" pattern="yyyy-MM-dd"/></div></td>
		                <td style="width:90px;"><div class="overflow_hidden w90 link" title="${bean.groupName}">${bean.groupName}</div></td>
                    	<td style="width:90px;"><div class="overflow_hidden w90 link" title="${bean.account}">${bean.account}</div></td>
                    	<td style="width:90px;"><div class="overflow_hidden w90 link" title="${bean.userName}">${bean.userName}</div></td>
		                
		                <td>
		                <c:if test="${bean.resPlanNum !=null}"><div class="overflow_hidden w100" title="${bean.resPlanNum }">${bean.resPlanNum }</div></c:if>
		                <c:if test="${bean.resPlanNum ==null}"><div class="overflow_hidden w100" title="无计划">无计划</div></c:if>
		                </td>
		                <td><div class="overflow_hidden w100" title="${bean.resTotalNum }">${bean.resTotalNum }</div></td>
		                <td><div class="overflow_hidden w90" title="${bean.resSignNum }">${bean.resSignNum }</div></td>
		                <td><div class="overflow_hidden w90" title="${bean.resCustNum }">${bean.resCustNum }</div></td>
		                <td><div class="overflow_hidden w120" title="${bean.resPoolNum }">${bean.resPoolNum }</div></td>
		                <td>
		                <c:if test="${bean.resNoContact !=null}"><div class="overflow_hidden w90" title="${bean.resNoContact }">${bean.resNoContact }</div></c:if>
		                <c:if test="${bean.resNoContact ==null}"><div class="overflow_hidden w90" title="无计划">无计划</div></c:if>
		                </td>
		                <td><div class="overflow_hidden w100" title="${bean.resNoNum }">${bean.resNoNum }</div></td>
		                
		                <td>
		                <c:if test="${bean.willPlanNum !=null}"><div class="overflow_hidden w100" title="${bean.willPlanNum }">${bean.willPlanNum }</div></c:if>
		                <c:if test="${bean.willPlanNum ==null}"><div class="overflow_hidden w100" title="无计划">无计划</div></c:if>
		               </td>
		                <td><div class="overflow_hidden w100" title="${bean.willTotalNum }">${bean.willTotalNum }</div></td>
		                <td><div class="overflow_hidden w90" title="${bean.willCustNum }">${bean.willCustNum }</div></td>
		                <td><div class="overflow_hidden w120" title="${bean.willSignNum }">${bean.willSignNum }</div></td>
		                <td><div class="overflow_hidden w90" title="${bean.willPoolNum }">${bean.willPoolNum }</div></td>
		                <td><div class="overflow_hidden w120" title="${bean.willNoNum }">${bean.willNoNum }</div></td>
		                <td>
		                <c:if test="${bean.willNoContact !=null}"><div class="overflow_hidden w100" title="${bean.willNoContact }">${bean.willNoContact }</div></c:if>
		                <c:if test="${bean.willNoContact ==null}"><div class="overflow_hidden w100" title="无计划">无计划</div></c:if>
		               </td>
		                <td><div class="overflow_hidden w120" title="${bean.callOutTime }">${bean.callOutTime }</div></td>
		                <td><div class="overflow_hidden w100" title="${bean.signMoney }">${bean.signMoney }</div></td>
		                <td><div class="overflow_hidden w120" title="${bean.tradCustNum }">${bean.tradCustNum }</div></td>
						</tr>
						</c:forEach>
					</c:when>
			    </c:choose>
			    
			     <c:choose>
					<c:when test="${!empty teamList}">
						<c:forEach var="bean" items="${teamList}" varStatus="i">
							<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
							<c:if test="${i.index%2==0}"><tr></c:if>
						
						<td colspan="4"><div class="overflow_hidden w360" title="${bean.groupName}">${bean.groupName}</div></td>
		                <td>
		                <c:if test="${bean.resPlanNum !=null}"><div class="overflow_hidden w100" title="${bean.resPlanNum }">${bean.resPlanNum }</div></c:if>
		                <c:if test="${bean.resPlanNum ==null}"><div class="overflow_hidden w100" title="无计划">无计划</div></c:if>
		                </td>
		                <td><div class="overflow_hidden w100" title="${bean.resTotalNum }">${bean.resTotalNum }</div></td>
		                <td><div class="overflow_hidden w90" title="${bean.resSignNum }">${bean.resSignNum }</div></td>
		                <td><div class="overflow_hidden w90" title="${bean.resCustNum }">${bean.resCustNum }</div></td>
		                <td><div class="overflow_hidden w120" title="${bean.resPoolNum }">${bean.resPoolNum }</div></td>
		                <td>
		                <c:if test="${bean.resNoContact !=null}"><div class="overflow_hidden w90" title="${bean.resNoContact }">${bean.resNoContact }</div></c:if>
		                <c:if test="${bean.resNoContact ==null}"><div class="overflow_hidden w90" title="无计划">无计划</div></c:if>
		                </td>
		                <td><div class="overflow_hidden w100" title="${bean.resNoNum }">${bean.resNoNum }</div></td>
		                
		                <td>
		                <c:if test="${bean.willPlanNum !=null}"><div class="overflow_hidden w100" title="${bean.willPlanNum }">${bean.willPlanNum }</div></c:if>
		                <c:if test="${bean.willPlanNum ==null}"><div class="overflow_hidden w100" title="无计划">无计划</div></c:if>
		               </td>
		                <td><div class="overflow_hidden w100" title="${bean.willTotalNum }">${bean.willTotalNum }</div></td>
		                <td><div class="overflow_hidden w90" title="${bean.willCustNum }">${bean.willCustNum }</div></td>
		                <td><div class="overflow_hidden w120" title="${bean.willSignNum }">${bean.willSignNum }</div></td>
		                <td><div class="overflow_hidden w90" title="${bean.willPoolNum }">${bean.willPoolNum }</div></td>
		                <td><div class="overflow_hidden w120" title="${bean.willNoNum }">${bean.willNoNum }</div></td>
		                <td>
		                <c:if test="${bean.willNoContact !=null}"><div class="overflow_hidden w100" title="${bean.willNoContact }">${bean.willNoContact }</div></c:if>
		                <c:if test="${bean.willNoContact ==null}"><div class="overflow_hidden w100" title="无计划">无计划</div></c:if>
		               </td>
		                <td><div class="overflow_hidden w120" title="${bean.callOutTime }">${bean.callOutTime }</div></td>
		                <td><div class="overflow_hidden w100" title="${bean.signMoney }">${bean.signMoney }</div></td>
		                <td><div class="overflow_hidden w120" title="${bean.tradCustNum }">${bean.tradCustNum }</div></td>
						</tr>
						</c:forEach>
					</c:when>
			    </c:choose>
                
                <c:if test="${total !=null}">
						<tr class="sty-bgcolor-b">
						<td colspan="4"><div class="overflow_hidden w360" title="${total.groupName}">${total.groupName}</div></td>
		                <td>
		                <c:if test="${total.resPlanNum !=null}"><div class="overflow_hidden w100" title="${total.resPlanNum }">${total.resPlanNum }</div></c:if>
		                <c:if test="${total.resPlanNum ==null}"><div class="overflow_hidden w100" title="无计划">无计划</div></c:if>
		                </td>
		                <td><div class="overflow_hidden w100" title="${total.resTotalNum }">${total.resTotalNum }</div></td>
		                <td><div class="overflow_hidden w90" title="${total.resSignNum }">${total.resSignNum }</div></td>
		                <td><div class="overflow_hidden w90" title="${total.resCustNum }">${total.resCustNum }</div></td>
		                <td><div class="overflow_hidden w120" title="${total.resPoolNum }">${total.resPoolNum }</div></td>
		                <td>
		                <c:if test="${total.resNoContact !=null}"><div class="overflow_hidden w90" title="${total.resNoContact }">${total.resNoContact }</div></c:if>
		                <c:if test="${total.resNoContact ==null}"><div class="overflow_hidden w90" title="无计划">无计划</div></c:if>
		                </td>
		                <td><div class="overflow_hidden w100" title="${total.resNoNum }">${total.resNoNum }</div></td>
		                
		                <td>
		                <c:if test="${total.willPlanNum !=null}"><div class="overflow_hidden w100" title="${total.willPlanNum }">${total.willPlanNum }</div></c:if>
		                <c:if test="${total.willPlanNum ==null}"><div class="overflow_hidden w100" title="无计划">无计划</div></c:if>
		               </td>
		                <td><div class="overflow_hidden w100" title="${total.willTotalNum }">${total.willTotalNum }</div></td>
		                <td><div class="overflow_hidden w90" title="${total.willCustNum }">${total.willCustNum }</div></td>
		                <td><div class="overflow_hidden w120" title="${total.willSignNum }">${total.willSignNum }</div></td>
		                <td><div class="overflow_hidden w90" title="${total.willPoolNum }">${total.willPoolNum }</div></td>
		                <td><div class="overflow_hidden w120" title="${total.willNoNum }">${total.willNoNum }</div></td>
		                <td>
		                <c:if test="${total.willNoContact !=null}"><div class="overflow_hidden w100" title="${total.willNoContact }">${total.willNoContact }</div></c:if>
		                <c:if test="${total.willNoContact ==null}"><div class="overflow_hidden w100" title="无计划">无计划</div></c:if>
		               </td>
		                <td><div class="overflow_hidden w120" title="${total.callOutTime }">${total.callOutTime }</div></td>
		                <td><div class="overflow_hidden w100" title="${total.signMoney }">${total.signMoney }</div></td>
		                <td><div class="overflow_hidden w120" title="${total.tradCustNum }">${total.tradCustNum }</div></td>
						</tr>
                </c:if>
                
                <!-- <tr><td colspan="18" style="text-align: center;"><b>当前列表无数据！</b></td></tr> -->
                </tbody>
            </table>
        </div>
    </div>
</div>
</form>
</body>
</html>
