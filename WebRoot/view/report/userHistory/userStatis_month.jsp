<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>销售统计-个人统计分析-月统计分析</title>
    <style>
        .com-table table thead tr  th{font-weight:normal;font-size:12px;}
        .color_grey{color:grey !important;}
    </style>
    <script type="text/javascript" src="${ctx}/static/js/view/worklog/dateUtils.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/my97datepicker/rangeLimit.js${_v}"></script>
    <script type="text/javascript">
        $(function(){
            /* $(".hyx-silent-p").find('dt').click(function(){
             $(this).parent().find('li')
             })*/
            var width = $(window).width();
            $(".today-static-b").find("li a").click(function(){
                var index = $(this).parent().index();
                $("#dataName").val($(this).attr("title"));
                $("#index").val(index);
                if(index == 0 || index == 1 || index == 2 || index == 3){
                	$("#dataIndex").val("bar1");
                }
                if(index == 4 || index == 5|| index == 6){
                	$("#dataIndex").val("bar2");
                }
                if(index == 7 || index == 8){
                	$("#dataIndex").val("bar3");
                }
                if(index == 9 || index == 10 || index == 11){
                	$("#dataIndex").val("bar4");
                }
            });
            
         	$("#toYear").click(function(){
        		$("#timeType").val(1);
        		$("#data_01").val("<fmt:formatDate value='${toYearArray[0]}' pattern='yyyy-MM'/>");
        		$("#data_02").val("<fmt:formatDate value='${toYearArray[1]}' pattern='yyyy-MM'/>");
        		doQuery();
            });
            
            $("#doQuery").click(function(){
           		doQuery();
           	});
        });
        
      
       	
       	function doQuery(){
       		$("form")[0].submit();
       	}
       	
        window.onload=function(){
            var height = $(".person-static-max").height()+30;
            window.parent.$("#iframepage").css({"height":height+"px"});
        };
    </script>
</head>
<body>
<form action="${ctx }/report/userStatis/monthData">
<input id="timeType" name="timeType" type="hidden" value="${timeType }"/>
<input id="dataIndex" name="dataIndex" value="${dataIndex}" type="hidden" />
<input id="dataName" name="dataName" value="${dataName}" type="hidden" />
<input id="index" name="index" value="${index}" type="hidden" />

<div class="person-static-max">
    <div class="hyx-silent-p">
        <div class="com-search-report fl_l" style="margin-top:-4px;min-height:0">
            <label class="fl_l" for="" style="line-height:34px;">数据指标：</label>
            <dl class="select" style="z-index: 1;">
                <c:choose>
                <c:when test="${dataName !=null and dataName!=''}">
                <dt style="border:1px solid #e1e1e1 !important;">${dataName}</dt>
                </c:when>
                <c:otherwise>
                <dt style="border:1px solid #e1e1e1 !important;">呼出总数</dt>
                </c:otherwise>
                </c:choose>
                <dd>
                    <ul class="today-static-b">
                        <li class="clearfix"><a title="呼出总数" href="###">呼出总数</a></li>
                        <li class="clearfix"><a title="已接总数" href="###">已接总数</a></li>
                        <li class="clearfix"><a title="呼叫资源" href="###">呼叫资源</a></li>
                        <li class="clearfix"><a title="有效呼叫" href="###">有效呼叫</a></li>
                        <li class="clearfix"><a title="计费时长" href="###">计费时长</a></li>
                        <li class="clearfix"><a title="呼出时长" href="###">呼出时长</a></li>
                        <li class="clearfix"><a title="呼入时长" href="###">呼入时长</a></li>
                        <li class="clearfix"><a title="新增意向" href="###">新增意向</a></li>
                        <li class="clearfix"><a title="新增签约" href="###">新增签约</a></li>
                        <li class="clearfix"><a title="回款金额" href="###">回款金额</a></li>
                        <li class="clearfix"><a title="签单金额" href="###">签单金额</a></li>
                        <li class="clearfix"><a title="预期签单金额" href="###">预期签单金额</a></li>
                    </ul>
                </dd>
            </dl>
        </div>
        <label class="fl_l" for="">选择年月：</label>
		<span class="fl_l" style="display: inline-block; position: relative;">
		<input id="data_01" name="fromDateStr" value="${fromDateStr}" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({onpicked:pickedFuncMonth1,dateFmt:'yyyy-MM',isShowClear:false,readOnly:true,maxDate:'#F{$dp.$D(\'data_02\')}'})" />
		<span class="silent-custo-span fl_l">&nbsp;—&nbsp;</span>
		<input id="data_02" name="toDateStr" value="${toDateStr }" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({onpicked:pickedFuncMonth2,maxDate:'%y-{%M}',dateFmt:'yyyy-MM',isShowClear:false,readOnly:true,minDate:'#F{$dp.$D(\'data_01\');}'})" />
		<%-- <a href="javascript:toWeekFn();" class="silent-week fl_l ${timeType eq '0'?'color_grey':''}">本周</a> --%>
		</span>
		<a id="toYear" href="javascript:toYearFn();" class="silent-month fl_l ${timeType eq '1'?'color_grey':''}">本年</a>
		<a id="doQuery" href="javascript:;" class="com-btna fl_l"><i class="min-search"></i><label class="lab-mar">查询</label></a>
        <div class="static-time fl_r" style="width:280px;margin-right:0"><label for="">统计时间：</label>${fromDateStr}至${toDateStr}</div>
    </div>
    <div class="static-all-line" style="width:100%;">
        <iframe src="${ctx}/report/userStatis/monthChart?type=${dataIndex}&index=${index}&fromDateStr=${fromDateStr}&toDateStr=${toDateStr}" width="100%" height="100%" id="iframepage1" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
    </div>
    <div class="com-table hyx-person-table hyx-cm-table">
        <table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
            <thead>
            <tr class="sty-bgcolor-b">
                <th><span class="sp sty-borcolor-b">日期</span></th>
                <th><span class="sp sty-borcolor-b">呼出总数（已接/总数）</span></th>
                <th><span class="sp sty-borcolor-b">呼叫资源数（个）</span></th>
                <th><span class="sp sty-borcolor-b">有效呼叫（个）</span></th>
                <th><span class="sp sty-borcolor-b">计费时长（分）</span></th>
                <th><span class="sp sty-borcolor-b">呼出时长（分）</span></th>
                <th><span class="sp sty-borcolor-b">呼入时长（分）</span></th>
                <th><span class="sp sty-borcolor-b">新增意向数（个）</span></th>
                <th><span class="sp sty-borcolor-b">新增签约数（个）</span></th>
                <th><span class="sp sty-borcolor-b">回款金额（万元）</span></th>
                <th><span class="sp sty-borcolor-b">签单金额（万元）</span></th>
                <th>预期签单金额（万元）</th>
            </tr>
            </thead>
            <tbody>
             <c:choose>
			<c:when test="${!empty list}">
				<c:forEach var="bean" items="${list}" varStatus="i">
					<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
					<c:if test="${i.index%2==0}"><tr></c:if>
					<td style="width:150px;"><div class="overflow_hidden w150 link" title="${bean.dateFmt }">${bean.dateFmt }</div></td>
	               	<td style="width:150px;"><div class="overflow_hidden w150 link" title="${bean.callInNum}/${bean.callOutNum}">${bean.callInNum}/${bean.callOutNum}</div></td>
	                <td><div class="overflow_hidden w100" title="${bean.callRes }">${bean.callRes }</div></td>
	                <td><div class="overflow_hidden w100" title="${bean.validCallOut }">${bean.validCallOut }</div></td>
	                <td><div class="overflow_hidden w100" title="${bean.chargeTime }">${bean.chargeTime }</div></td>
	                <td><div class="overflow_hidden w100" title="${bean.callTime }">${bean.callTime }</div></td>
	                <td><div class="overflow_hidden w100" title="${bean.inCallTime }">${bean.inCallTime }</div></td>
	                <td><div class="overflow_hidden w100" title="${bean.willNum}">${bean.willNum}</div></td>
	                <td><div class="overflow_hidden w100" title="${bean.signNum}">${bean.signNum}</div></td>
	                <td><div class="overflow_hidden w100" title="${bean.signMoney}">${bean.signMoney}</div></td>
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
    <div class="person-todata-warm">
        <h2>温馨提示：</h2>
        <p><span>1、“计费时长”为本地参考值，实际计费以服务器计费系统数据为准。</span></p>
        <p><span>2、&nbsp;&nbsp;&nbsp;单次通话“计费时长”不满一分钟时，按一分钟计算。</span></p>
        <p><span>3、“有效呼叫”指在电话接通<label for="">${timeElngth}</label>秒后(此项可在“系统设置-销售管理”中进行设置)</span></p>
        <p><span>4、“呼出时长”是将用户的呼出时长以“秒”为单位进行合计后再换算成“分钟”为单位，不足1分钟部分按1分钟计算。</span></p>
    	<p><span>5、“呼入时长”是将用户的呼入时长以“秒”为单位进行合计后再换算成“分钟”为单位，不足1分钟部分按1分钟计算。</span></p>   
    </div>
</div>
</form>
</body>
</html>
