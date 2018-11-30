<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售统计-个人历史数据-销售结果统计-意向</title>
<!--本页面样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css${_v}">
<script type="text/javascript" src="${ctx}/static/js/my97datepicker/rangeLimit.js${_v}"></script>
<style>
/* .com-table table thead tr  th{font-weight:normal;font-size:12px;} */
.tree-title{width:100px;}
.com-search .select dd{top:30px;}
.com-search .select dt.cur{border:solid #e1e1e1 1px !important;}
.select dd ul{overflow-x:hidden;}
.select dd .easyui-tree{width:260px !important;min-height:190px;}

.bomp_ic_tip{position:relative;width:100%;height:30px;border-bottom:solid #979797 1px;margin-top:15px;}
.bomp_ic_tip .list{position:absolute;left:7px;bottom:-1px;width:auto;height:30px;}
.bomp_ic_tip .list li{float:left;width:100px;height:28px;line-height:28px;text-align:center;font-size:14px;color:#636363;border:solid #979797 1px;background-color:#e2e2e2;cursor:pointer;}
.bomp_ic_tip .list .li_click{color:#000;background-color:#fff;border-bottom:solid #fff 1px;}

.hyx-silent-p{margin-bottom:0;}
.color_grey{color:grey !important;}
</style> 
<script type="text/javascript">
var ownerAcc="${user.account}"

$(function(){
    /*标签页选择*/
    $(".bomp_ic_tip").find("li").click(function(){
    	if($(this).hasClass("li_click") == false){
    		$(this).addClass("li_click");
    		$(this).siblings("li").removeClass("li_click");
    		var _url = $(this).attr("_url");
    		window.parent.changeUrl(_url);
    	};
    });
    
    $("#search").click(function(){
    	refreshPage();
    });
    
    $("#toWeek").click(function(){
    	$("#timeType").val(0);
		$("#data_01").val("<fmt:formatDate value='${toWeekArray[0]}' pattern='yyyy-MM-dd'/>");
		$("#data_02").val("<fmt:formatDate value='${toWeekArray[1]}' pattern='yyyy-MM-dd'/>");
		refreshPage();
    });
	    
	$("#toMonth").click(function(){
		$("#timeType").val(1);
		$("#data_01").val("<fmt:formatDate value='${toMonthArray[0]}' pattern='yyyy-MM-dd'/>");
		$("#data_02").val("<fmt:formatDate value='${toMonthArray[1]}' pattern='yyyy-MM-dd'/>");
		refreshPage();
	});
	
	$(".view_change").click(function(){
		var date = $(this).attr("date");
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
		window.parent.pubDivShowIframe('change_log_detail',ctx+'/report/contact?ownerAcc='+ownerAcc+"&dateStr="+date+"&custType="+custType+"&changeType="+changeType,name,800,450);
	});
	 
});

function refreshPage(){
	$("form")[0].submit();
} 
window.onload=function(){
	var height = $(".sales-statis-conta").height()+200;
	window.parent.$("#iframepage").css({"height":height+"px"});
};
</script>
</head>
<body> 
<form action="${ctx }/report/userStatis/saleResult">
<input name="pageType" type="hidden" value="will"/>
<input id="timeType" name="timeType" type="hidden" value="${timeType }"/>
<div class="sales-statis-conta">
	<div class="bomp_ic_tip">
		<ul class="list">
			<li _url="${ctx}/report/userStatis/saleResult?pageType=res" >资源联系结果</li>
			<li class="li_click" _url="${ctx}/report/userStatis/saleResult?pageType=will">意向联系结果</li>
		</ul>
	</div>

	<div class="hyx-silent-p clearfix" style="z-index:555;">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">销售结果统计</label>
		<label class="fl_l" for="">选择日期：</label>
		<span class="fl_l" style="display: inline-block; position: relative;"><!-- 限制日期选择只能选择一个月 -->
		<input id="data_01" name="fromDateStr" value="${fromDateStr }" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({onpicked:pickedFunc1,maxDate:'#F{$dp.$D(\'data_02\')}'})" />
		<span class="silent-custo-span fl_l">&nbsp;—&nbsp;</span>
		<input id="data_02" name="toDateStr" value="${toDateStr }" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({onpicked:pickedFunc2,minDate:'#F{$dp.$D(\'data_01\');}',maxDate:'%y-%M-{%d-1}'})" />
		</span>
		<a id="toWeek" href="javascript:;" class="silent-week fl_l ${timeType eq '0'?'color_grey':''}">本周</a>
		<a id="toMonth" href="javascript:;" class="silent-month fl_l ${timeType eq '1'?'color_grey':''}">本月</a>
		<a id="search" href="javascript:;" class="com-btna fl_l"><i class="min-search"></i><label class="lab-mar">查询</label></a>
		<!-- <a href="#" class="com-btna fl_r"><i class="min-remove-resou"></i><label class="lab-mar">导出</label></a> -->
		<!-- <div class="static-time fl_r" style="width: 280px; margin-right: 0px;"><label for="">统计时间：</label>2016年03月04日至2016年03月11日</div> -->
	</div>
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
					<tr groupId="${bean.groupId }" class="groupData ${i.index%2!=0?'sty-bgcolor-b':''}">
						<td><div class="overflow_hidden w120" title="${bean.reportDateStr}">${bean.reportDateStr}</div></td>
						<td><div class="overflow_hidden w70" title="${bean.willPlanNum }">${bean.willPlanNum }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.willTotalNum }">${bean.willTotalNum }</div></td>
						
						<td><div class="overflow_hidden w70" title="${bean.willSignNum }">
						 <c:choose>
				                	<c:when test="${bean.willSignNum > 0 }">
				                		<a class="view_change" href="javascript:;" date="${bean.reportDateStr}" cust_type="2" change_type="2">${bean.willSignNum }</a>
				                	</c:when>
				                	<c:otherwise>${bean.willSignNum }</c:otherwise>
				                </c:choose>
				                </div></td>
		                
				                <td><div class="overflow_hidden w70" title="${bean.willCustNum }">
								 <c:choose>
				                	<c:when test="${bean.willCustNum > 0 }">
				                		<a class="view_change" href="javascript:;" date="${bean.reportDateStr}" cust_type="2" change_type="1">${bean.willCustNum }</a>
				                	</c:when>
				                	<c:otherwise>${bean.willCustNum }</c:otherwise>
				                </c:choose>
				                </div></td>
						
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
			</tbody>
		</table>
	</div>
</div>
</form>
</body>
</html>
