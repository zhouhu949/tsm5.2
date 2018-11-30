<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
      <title>个人今日数据</title>
    <script type="text/javascript">
        $(function () {
			$("a[id^=view_change_]").click(function(){
				var ownerAcc = $(this).attr("id").split("_")[2];
				var custType = $(this).attr("cust_type");
				var changeType = $(this).attr("change_type");
				var name = "";
				if(custType == 1){
					if(changeType == 1)
						name="资源转意向详情";
					else
						name="资源转签约详情"
				}else{
					if(changeType == 1)
						name="意向变更详情";
					else
						name="意向转签约详情"
				}
				pubDivShowIframe('change_log_detail',ctx+'/report/contact?ownerAcc='+ownerAcc+"&custType="+custType+"&changeType="+changeType,name,800,450);
			});
        });
    </script>
</head>
<body>
<div class="sales-statis-conta clearfix">
    <p class="hyx-silent-p">
        <label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">今日呼叫数据</label>
    </p>
    <div class="com-table hyx-mpe-table hyx-cm-table">
        <table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
            <thead>
            <tr class="sty-bgcolor-b">
                <th><span class="sp sty-borcolor-b">呼出已接/呼出总数（次）</span></th>
                <th><span class="sp sty-borcolor-b">有效呼叫（次）</span></th>
<!--                 <th><span class="sp sty-borcolor-b">呼叫资源数（个）</span></th> -->
<!--                 <th><span class="sp sty-borcolor-b">呼叫意向客户数（个）</span></th> -->
<!--                 <th><span class="sp sty-borcolor-b">上门拜访量</span></th> -->
                <th><span class="sp sty-borcolor-b">呼出时长（分钟）</span></th>
                <th><span class="sp sty-borcolor-b">呼入时长（分钟）</span></th>
                <th><span class="sp sty-borcolor-b">计费时长（分钟）</span></th>
                <th><span class="sp sty-borcolor-b">行动完成率</span></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td style="width:200px;"><div class="overflow_hidden w200 link" title="">${callReportBean.calloutAnswer }/${callReportBean.calloutTotal }</div></td>
                <td><div class="overflow_hidden w100" title="">${callReportBean.validCalls }</div></td>
<%--                 <td><div class="overflow_hidden w100" title="">${callReportBean.restCalls }</div></td> --%>
<%--                 <td><div class="overflow_hidden w100" title="">${callReportBean.custCalls }</div></td> --%>
<%--                 <td><div class="overflow_hidden w100" title="">${empty callReportBean.visitNum ? 0 : callReportBean.visitNum }</div></td> --%>
                <td><div class="overflow_hidden w100" title="">${callReportBean.calloutTime }</div></td>
                <td><div class="overflow_hidden w100" title="">${callReportBean.callinTime }</div></td>
                <td><div class="overflow_hidden w100" title="">${callReportBean.billingTime }</div></td>
                <td><div class="overflow_hidden w100" title="">${callReportBean.actionCompletionRate }</div></td>
            </tr>
            </tbody>
        </table>
    </div>
    <p class="hyx-silent-p">
        <label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">今日资源联系结果</label>
    </p>
    <div class="com-table hyx-mpe-table hyx-cm-table">
        <table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
            <thead>
            <tr class="sty-bgcolor-b">
                <th><span class="sp sty-borcolor-b">计划联系数（个）</span></th>
                <th><span class="sp sty-borcolor-b">实际联系数（个）</span></th>
                <th><span class="sp sty-borcolor-b">添加资源数（个）</span></th>
                <th><span class="sp sty-borcolor-b">转签约（个）</span></th>
                <th><span class="sp sty-borcolor-b">转意向（个）</span></th>
                <th><span class="sp sty-borcolor-b">转公海（个）</span></th>
                <th><span class="sp sty-borcolor-b">联系无变化（个）</span></th>
                <th><span class="sp sty-borcolor-b">计划未联系（个）</span></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td style="width:150px;"><div class="overflow_hidden w150 link" title="">${empty resMap['planNum'] ? 0 : resMap['planNum'] }</div></td>
                <td><div class="overflow_hidden w100" title="">${empty resMap['totalNum'] ? 0 : resMap['totalNum'] }</div></td>
                <td><div class="overflow_hidden w100" title="">${empty resMap['addResCount'] ? 0 : resMap['addResCount'] }</div></td>
                <c:choose>
                	<c:when test="${!empty resMap['signNum'] && resMap['signNum'] > 0 }">
                		<td><div class="overflow_hidden w100" title=""><a href="javascript:;" id="view_change_${userAccount }" cust_type="1" change_type="2">${empty resMap['signNum'] ? 0 : resMap['signNum'] }</a></div></td>
                	</c:when>
                	<c:otherwise>
                		<td><div class="overflow_hidden w100" title="">${empty resMap['signNum'] ? 0 : resMap['signNum'] }</div></td>
                	</c:otherwise>
                </c:choose>
                <c:choose>
                	<c:when test="${!empty resMap['custNum'] && resMap['custNum'] > 0 }">
                		<td><div class="overflow_hidden w100" title=""><a href="javascript:;" id="view_change_${userAccount }" cust_type="1" change_type="1">${empty resMap['custNum'] ? 0 : resMap['custNum'] }</a></div></td>
                	</c:when>
                	<c:otherwise>
                		<td><div class="overflow_hidden w100" title="">${empty resMap['custNum'] ? 0 : resMap['custNum'] }</div></td>
                	</c:otherwise>
                </c:choose>
                <td><div class="overflow_hidden w100" title="">${empty resMap['poolNum'] ? 0 : resMap['poolNum'] }</div></td>
                <td><div class="overflow_hidden w100" title="">${empty resMap['noNum'] ? 0 : resMap['noNum'] }</div></td>
                <td><div class="overflow_hidden w150" title="">${empty resMap['noConNum'] ? 0 : resMap['noConNum'] }</div></td>
            </tr>
            </tbody>
        </table>
    </div>





    <p class="hyx-silent-p">
        <label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">今日意向客户联系结果</label>
    </p>
    <div class="com-table hyx-mpe-table hyx-cm-table">
        <table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
            <thead>
            <tr class="sty-bgcolor-b">
                <th><span class="sp sty-borcolor-b">计划联系数（个）</span></th>
                <th><span class="sp sty-borcolor-b">实际联系数（个）</span></th>
                <th><span class="sp sty-borcolor-b">转签约（个）</span></th>
                <th><span class="sp sty-borcolor-b">意向变更（个）</span></th>
                <th><span class="sp sty-borcolor-b">转公海（个）</span></th>
                <th><span class="sp sty-borcolor-b">联系无变化（个）</span></th>
                <th><span class="sp sty-borcolor-b">计划未联系（个）</span></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td style="width:150px;"><div class="overflow_hidden w150 link" title="">${empty intMap['planNum'] ? 0 : intMap['planNum'] }</div></td>
                <td><div class="overflow_hidden w100" title="">${empty intMap['totalNum'] ? 0 : intMap['totalNum'] }</div></td>
                <c:choose>
                	<c:when test="${!empty intMap['signNum'] && intMap['signNum'] > 0 }">
                		<td><div class="overflow_hidden w100" title=""><a href="javascript:;" id="view_change_${userAccount }" cust_type="2" change_type="2">${empty intMap['signNum'] ? 0 : intMap['signNum'] }</a></div></td>
                	</c:when>
                	<c:otherwise>
                		<td><div class="overflow_hidden w100" title="">${empty intMap['signNum'] ? 0 : intMap['signNum'] }</div></td>
                	</c:otherwise>
                </c:choose>
                <c:choose>
                	<c:when test="${!empty intMap['custNum'] && intMap['custNum'] > 0 }">
                		<td><div class="overflow_hidden w100" title=""><a href="javascript:;" id="view_change_${userAccount }" cust_type="2" change_type="1">${empty intMap['custNum'] ? 0 : intMap['custNum'] }</a></div></td>
                	</c:when>
                	<c:otherwise>
                		<td><div class="overflow_hidden w100" title="">${empty intMap['custNum'] ? 0 : intMap['custNum'] }</div></td>
                	</c:otherwise>
                </c:choose>
                <td><div class="overflow_hidden w100" title="">${empty intMap['poolNum'] ? 0 : intMap['poolNum'] }</div></td>
                <td><div class="overflow_hidden w100" title="">${empty intMap['noNum'] ? 0 : intMap['noNum'] }</div></td>
                <td><div class="overflow_hidden w150" title="">${empty intMap['noConNum'] ? 0 : intMap['noConNum'] }</div></td>
            </tr>
            </tbody>
        </table>
    </div>





    <p class="hyx-silent-p">
        <label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">今日交易结果</label>
    </p>
    <div class="com-table hyx-mpe-table hyx-cm-table">
        <table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
            <thead>
            <tr class="sty-bgcolor-b">
                <th><span class="sp sty-borcolor-b">新增签约数（个）</span></th>
                <th><span class="sp sty-borcolor-b">新增订单数（个）</span></th>
                <th><span class="sp sty-borcolor-b">回款金额（元）</span></th>
                <th><span class="sp sty-borcolor-b">签单金额（元）</span></th>
                <th><span class="sp sty-borcolor-b">预期签单金额（元）</span></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><div title="">${(empty intMap['signNum'] ? 0 : intMap['signNum'])+(empty resMap['signNum'] ? 0 : resMap['signNum']) }</div></td>
                <td><div title="">${orderDto.orderNum }</div></td>
                <td><div title=""><fmt:formatNumber value="${orderDto.money }" pattern="#,##0.00#"/></div></td>
                <td><div title=""><fmt:formatNumber value="${orderDto.signMoney }" pattern="#,##0.00#"/></div></td>
                <td><div title=""><fmt:formatNumber value="${orderDto.willSignMoney }" pattern="#,##0.00#"/></div></td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="person-todata-warm">
        <h2>温馨提示：</h2>
        <p><span>1、“计费时长”为本地参考值，实际计费以服务器计费系统数据为准。</span></p>
        <p><span>2、&nbsp;&nbsp;&nbsp;单次通话“计费时长”不满一分钟时，按一分钟计算。</span></p>
        <p><span>3、“有效呼叫”指在电话接通<label for="">${timeLength }</label>秒后(此项可在“系统设置-销售管理”中进行设置)</span></p>
        <p><span>4、“呼出时长”是将用户的呼出时长以“秒”为单位进行合计后再换算成“分钟”为单位，不足1分钟部分按1分钟计算。</span></p>
        <p><span>5、“呼入时长”是将用户的呼入时长以“秒”为单位进行合计后再换算成“分钟”为单位，不足1分钟部分按1分钟计算。</span></p>
    </div>
</div>
</body>
</html>
