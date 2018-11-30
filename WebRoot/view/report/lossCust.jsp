<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>销售统计-流失客户统计</title>
    <script type="text/javascript">
		    $(function () {
		       	 $('#queryId').click(function(){
		       		    if(!compareTime()){
		       		    	return false;
		       		    }
						$("#form").attr("action",ctx+"/report/lossCust");
						$("#form").submit();
		     	 })	
		   	 $('#exportId').click(function(){
					$("#form").attr("action",ctx+"/report/lossCust/export");
					$("#form").submit();
		   	})
		   });
	        function compareTime()   {
	        	var startDate = $('#startTime').val();
	        	var endDate = $('#endTime').val();
	        	if(startDate == '' || endDate == ''){
	        		window.top.iDialogMsg("提示", '不能为空');
	        		return false;
	        	}
	        	var strdate1 = (startDate+'-01').replace(/-/g,"\/");
	        	var strdate2 = (endDate+'-01').replace(/-/g,"\/");            
	        	var date1 = new Date(Date.parse(strdate1));
	        	var date2 = new Date(Date.parse(strdate2));
	        	if((date2-date1)/(1000*60*60*24) >365 ){
	        		window.top.iDialogMsg("提示", '您只能查询时间间隔最大为12个月');
	        		return false;
	        }else if(date2 <date1){
	        	window.top.iDialogMsg("提示", '开始时间大于结束时间');
	        		return false;
	        	}
	        else{
	        	return true;
	        }
	        } 
			
			function toLossDetail(currDate){
				window.top.addTab(ctx+"/report/lossCust/lossDetail?currDate="+currDate,"流失客户统计(部门)");
			}      
    </script>
</head>
<body>
<form action="${ ctx}/report/lossCust" method="post" id="form">
<div class="sales-statis-conta clearfix">
<p class="hyx-silent-p">
    <label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">流失客户统计</label>
    <label class="fl_l" for="">年月：</label>
		<span class="fl_l" style="display: inline-block; position: relative;">
		<input id="startTime" onchange="" name="startTime" value="${startTime }" type="text" class="selec-year-inpu com-form-data fl_l"
               onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,readOnly:true})"/><span
                class="silent-custo-span fl_l">&nbsp;—&nbsp;</span>
        <input id="endTime" onchange="" name="endTime" value="${endTime }" type="text"
                class="selec-year-inpu com-form-data fl_l"
                onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,readOnly:true})"/></span>
    <a href="###" class="com-btna fl_l" id="queryId"><i class="min-search"></i><label class="lab-mar">查询</label></a>
    <a href="###" class="com-btna fl_r" id="exportId"><i class="com_btn com_btn_15"></i><label class="lab-mar">导出</label></a>
</p>

<div class="com-table hyx-mpe-table hyx-cm-table">
    <table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
        <thead>
        <tr class="sty-bgcolor-b">
            <th><span class="sp sty-borcolor-b">日期</span></th>
            <th><span class="sp sty-borcolor-b">签约客户总数（个）</span></th>
            <th><span class="sp sty-borcolor-b">流失客户总数（个）</span></th>
            <th><span class="sp sty-borcolor-b">本月流失客户数（个）</span></th>
            <th><span class="sp sty-borcolor-b">本月到期客户数（个）</span></th>
            <th><span class="sp sty-borcolor-b">客户流失率</span></th>
            <th>各小组明细查看</th>
        </tr>
        </thead>
        <tbody>
            <c:choose>
               <c:when test="${ !empty list }">
                   <c:forEach items="${ list}" var="cust" varStatus="vs">
			            <tr class="${vs.count%2 eq 0 ? 'sty-bgcolor-b' : '' }">
			                <td><div class="overflow_hidden w100 link" title="${cust.currDate }">${cust.currDate }</div></td>
			                <td><div class="overflow_hidden w70" title="">${cust.signTotal }</div></td>
			                <td><div class="overflow_hidden w70" title="">${cust.lossTotal }</div></td>
			                <td><div class="overflow_hidden w70" title="">${cust.addLossTotal }</div></td>
			                <td><div class="overflow_hidden w70" title="10">${cust.expireCustTotal }</div></td>
			                <td><div class="overflow_hidden w120" title="30%">${cust.lossRate }%</div></td>
				            <td>
				                <div class="overflow_hidden w80" title=""><a href="###" onclick="toLossDetail('${cust.currDate}')">查看</a>
				                </div>
				            </td>			                
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
