<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>新增意向客户统计-历史统计</title>
    <style>
        .com-table table thead tr  th{font-weight:normal;font-size:12px;}
        .color_grey{color:grey !important;}
    </style>
    <script type="text/javascript" src="${ctx}/static/js/view/worklog/dateUtils.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/my97datepicker/rangeLimit.js${_v}"></script>
    <script type="text/javascript">
	    var toWeekFrom="${toWeekFrom}";
		var toWeekTo="${toWeekTo}";
		var toMonthFrom="${toMonthFrom}";
		var toMonthTo="${toMonthTo}";
        $(function () {
            /* $(".hyx-silent-p").find('dt').click(function(){
             $(this).parent().find('li')
             })*/
            var width = $(window).width();
            $(".today-static-b").find("li a").click(function(){
                var id = $(this).parent().attr("processId");
                $("#processId").val(id)
            });
            
           	$("#doQuery").click(function(){
           		doQuery();
           	});
           	
           	$("#daySearch").live("click",function(){
		 		var fromDate=$("#data_03").val();
		 		var toDate=$("#data_04").val();
		 		if((fromDate==null ||fromDate.length==0) && (toDate==null ||toDate.length==0)){
		 			alert("请先选择日期");
		 		}else{
		 			var html="<iframe src='${ctx}/newWill/oldDaydataByGroup?fromDateStr="+fromDate+"&toDateStr="+toDate+"'"+
		 					" width='100%' height='100%' id='iframepage_1' frameborder='0' scrolling='no' marginheight='0' marginwidth='0' onload='iFrameHeight()'></iframe>";
				$("#aaa").html(html);
		 		}
		 		//$("#iframepage_2").attr("src","${ctx}/report/teamHistoryData/dayDataGroup?dateStr="+date);
		 	});
           	
           	$("#toWeek").click(function(){
        		$("#timeType").val(0);
        		$("#data_01").val("<fmt:formatDate value='${toWeekArray[0]}' pattern='yyyy-MM-dd'/>");
        		$("#data_02").val("<fmt:formatDate value='${toWeekArray[1]}' pattern='yyyy-MM-dd'/>");
        		doQuery();
            });
        	    
        	$("#toMonth").click(function(){
        		$("#timeType").val(1);
        		$("#data_01").val("<fmt:formatDate value='${toMonthArray[0]}' pattern='yyyy-MM-dd'/>");
        		$("#data_02").val("<fmt:formatDate value='${toMonthArray[1]}' pattern='yyyy-MM-dd'/>");
        		doQuery();
        	});
        	$("body").delegate(".report-click-detail","click",function(e){
				e.stopPropagation();
				var $this = $(this);
				var selectType = $this.data("selecttype");
				var optionlistId = $.trim($this.data("optionid")) ;
				var currDate =$this.data("currdate");
				var optionName =$this.data("optionname"); 
				if($.trim(optionlistId) != "" && optionlistId != null){
					var name = "历史新增"+optionName+"进程明细";
					if(selectType == 4){
						var startDate = $this.data("startdate"); 
						var endDate = $this.data("enddate"); 
						window.top.pubDivShowIframe('change_log_detail',ctx+"/newWill/newWillHistoryIdialogNewWillStatus?startDate="+startDate+"&endDate="+endDate+"&selectType="+selectType+"&optionlistId="+optionlistId,name,900,450);
					}else{
						window.top.pubDivShowIframe('change_log_detail',ctx+"/newWill/newWillHistoryIdialogNewWillStatus?currDate="+currDate+"&selectType="+selectType+"&optionlistId="+optionlistId,name,900,450);
					}
				}else{
					var name = "新增意向客户明细";
					if(selectType == 4){
						var startDate = $this.data("startdate"); 
						var endDate = $this.data("enddate"); 
						window.top.pubDivShowIframe('change_log_detail',ctx+"/newWill/newWillHistoryId?startDate="+startDate+"&endDate="+endDate+"&selectType="+selectType,name,900,450);
					}else{
						window.top.pubDivShowIframe('change_log_detail',ctx+"/newWill/newWillHistoryId?currDate="+currDate+"&selectType="+selectType,name,900,450);
					}
				}
			})
        });
       	
       	function doQuery(){
       		$("form")[0].submit();
       	}
       	
     window.onload=function(){
	/*
	 *height:获取当前内容高度（也可以理解为这一层的iframe高度）
	 *将父层的id为iframepage，$("#iframepage")该对象，让其高度为当前内容高度，防止滚动条出现
	 *将进入该页面时的iframepage的高度保存起来（因为之后每次都在改变这个值）iframepage_height
	 */
		var height = $(".person-static-max").height()+30;
		window.parent.$("#iframepage").css({"height":height+"px"});
		var iframepage_height = window.parent.$("#iframepage").height();
		$("#iframepage_height").val(iframepage_height);
	/* 	alert($("iframe[src$='teamHistoryData/mainPage']").prevObject.height()); */
	};
    </script>
</head>
<body>
<form action="${ctx }/newWill/oldDaydata">
<input id="timeType" name="timeType" type="hidden" value="${timeType }"/>
<input id="processId" name="processId" value="${processIds}" type="hidden" />
<input id="iframepage_height" type="hidden" value=""/>

<div class="person-static-max">
    <div class="hyx-silent-p">
        <div class="com-search-report fl_l" style="margin-top:-4px;min-height:0">
            <label class="fl_l" for="" style="line-height:34px;">数据指标：</label>
            <dl class="select" style="z-index: 1;">
                <dt style="border:1px solid #e1e1e1 !important;">${process}</dt>
                <dd>
                    <ul class="today-static-b">
						 <c:if test="${!empty processlist }">
				                <c:forEach var="sopn" items="${processlist }" varStatus="v" >
										 <li class="clearfix"  processId="${sopn.processId }"><a title="${sopn.process }" href="javascript:;">${sopn.process }</a></li>
								</c:forEach>
							</c:if>
                    </ul>
                </dd>
            </dl>
        </div>
        <label class="fl_l" for="">选择日期：</label>
		<span class="fl_l" style="display: inline-block; position: relative;">
		<input id="data_01" name="fromDateStr" value="${fromDateStr }" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'data_02\')}'})" />
		<span class="silent-custo-span fl_l">&nbsp;—&nbsp;</span>
		<input id="data_02" name="toDateStr" value="${toDateStr }" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({minDate:'#F{$dp.$D(\'data_01\');}',maxDate:'%y-%M-{%d-1}'})" />
		</span>
		<a id="toWeek" href="javascript:;" class="silent-week fl_l ${timeType eq '0'?' color_grey':''}">本周</a>
		<a id="toMonth" href="javascript:;" class="silent-month fl_l ${timeType eq '1'?' color_grey':''}">本月</a>
		
		<a id="doQuery" href="javascript:;" class="com-btna fl_l"><i class="min-search"></i><label class="lab-mar">查询</label></a>
        <div class="static-time fl_r" style="width:280px;margin-right:0"><label for="">统计时间：</label>${fromDateStr }至${toDateStr }</div>
    </div>
    <div class="static-all-line" style="width:100%;">
        <iframe src="${ctx}/newWill/oldDaydataChart?processId=${processIds}&fromDateStr=${fromDateStr }&toDateStr=${toDateStr }" width="100%" height="100%" id="iframepage1" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
    </div>
    <div class="com-table hyx-person-table hyx-cm-table">
        <table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
            <thead>
            <tr class="sty-bgcolor-b">
	            <th><span class="sp sty-borcolor-b">日期</span></th>
	             <th><span class="sp sty-borcolor-b">新增意向数</span></th>
	            <c:if test="${!empty item}">
	                <c:set var="itemlength" value="${fn:length(item)}"></c:set>
	                <c:forEach var="sopn" items="${item }" varStatus="v" >
						 <c:choose>
						 <c:when test="${ v.count == itemlength }">
						  <th>${sopn}</th>
						 </c:when>
						 <c:otherwise>
						  <th><span class="sp sty-borcolor-b">${sopn}</span></th>
						 </c:otherwise>
						 </c:choose>
					</c:forEach>
				</c:if>
            </tr>
            </thead>
            <tbody>
            <c:set var="listlength" value="${fn:length(list)}"></c:set> 
            <c:choose>
			<c:when test="${!empty list}">
				<c:forEach var="bean" items="${list}" varStatus="i">
					<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
					<c:if test="${i.index%2==0}"><tr></c:if>
						<td><div class="overflow_hidden w100" title="${bean.countvalue }">${bean.countvalue }</div></td>
						<c:if test="${!empty bean.returnlist}">
							
								<c:choose><c:when test = "${i.count eq  listlength }">
									<c:forEach var="beanitem" items="${bean.returnlist}" varStatus="b">
										 <td><div class="overflow_hidden w100"  >
												<a href="javascript:;" class="report-click-detail"  title="${beanitem.num }" data-selecttype="4" data-currdate="${bean.countvalue }" data-startdate="${fromDateStr }"  data-enddate="${toDateStr }"  data-optionid="${beanitem.optionId }" data-optionname="${beanitem.optionName }">${beanitem.num }</a>
											 </div></td>
										</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach var="beanitem" items="${bean.returnlist}" varStatus="b">
										 <td><div class="overflow_hidden w100"  >
												<c:choose>
												<c:when test="${beanitem.num gt 0  }">
												<a href="javascript:;" class="report-click-detail"  title="${beanitem.num }" data-selecttype="1" data-currdate="${bean.countvalue }" data-optionid="${beanitem.optionId }" data-optionname="${beanitem.optionName }">${beanitem.num }</a>
												</c:when>
												<c:otherwise>
													${beanitem.num }
												</c:otherwise>
												</c:choose>
											 </div></td>
										</c:forEach>
								</c:otherwise>
							</c:choose>
						</c:if>
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
    
   	<c:if test="${!empty list}">
			<div class="sales-statis-conta clearfix">
				<p class="clearfix"><label class="fl_l" for="" style="line-height:26px;">选择日期：</label>
				<span class="fl_l" style="display: inline-block; position: relative;"><!-- 限制日期选择只能选择一个月 -->
				<input id="data_03" value="${fromDateStr }" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({onpicked:pickedFunc3,maxDate:'#F{$dp.$D(\'data_04\')}'})" />
				<span class="silent-custo-span fl_l">&nbsp;—&nbsp;</span>
				<input id="data_04" value="${toDateStr }" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({onpicked:pickedFunc4,minDate:'#F{$dp.$D(\'data_03\');}',maxDate:'%y-%M-{%d-1}'})" />
				</span>
				<a id="daySearch" href="javascript:;" class="com-btna fl_l" style="margin-left:20px;"><i class="min-search"></i><label class="lab-mar">分析</label></a>
				</p>
			<div id="aaa"></div>
			</div>
		</c:if>
    <div class="person-todata-warm">
        <h2>温馨提示：</h2>
        <p><span>1.每日新增意向数：统计每日新增加的意向客户中，客户状态仍为意向的数量，不含已经转变为其他状态的客户数量；</span></p>
        <p><span>2.根据意向客户每日销售进程的结果，统计每个销售进程当日的新增量；</span></p>
        <p><span>3.新增意向数的数量小于等于各销售进程新增量之和；</span></p>
    </div>
</div>
</form>
	<script type="text/javascript" language="javascript">
		function iFrameHeight() {
			var ifm= document.getElementById("iframepage_1");
			console.log(ifm)
			var subWeb = document.frames ? document.frames["iframepage_1"].document :ifm.contentDocument;
			console.log(subWeb.body.scrollHeight)
			if(ifm != null && subWeb != null) {
				ifm.height = subWeb.body.scrollHeight;
			}
		}
	</script>
</body>
</html>
