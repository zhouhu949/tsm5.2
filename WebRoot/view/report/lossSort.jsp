<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>流失客户原因分类统计</title>
    <script type="text/javascript">
        $(function () {
            $(function () {
   	       	 $('#queryId').click(function(){
   	       		    if(!compareTime()){
   	       		    	return false;
   	       		    }
   					$("#form").attr("action",ctx+"/report/lossSort");
   					$("#form").submit();
   	     	 })
        	
           	 $('#exportId').click(function(){
   				$("#form").attr("action",ctx+"/report/lossSort/export");
   				$("#form").submit();
           	})
           });
            
//             $('.alert_sys_pro_set_g_b').click(function(){
//             	window.top.addTab(ctx+"/report/lossSort/lossSortDetail?custId="+custId,"客户流失原因分析");
//                 //showIframe('alert_sys_pro_set_f_b','alert_sys_pro_set_f_b.html','修改标签',330,190);
//             });
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
        
        function toLossSortDetail(deptId,optionId){
           	window.top.addTab(ctx+"/report/lossSort/lossSortDetail?deptId="+deptId+"&optionId="+optionId+"&startTime="+$('#startTime').val()+"&endTime="+$('#endTime').val(),"客户流失原因分析");
        }
    </script>
</head>
<body>
<form id="form" action="${ ctx}/report/lossSort" method="post">
	<div class="sales-statis-conta clearfix">
	    <p class="hyx-silent-p">
	        <label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">客户流失原因统计</label>
	        <label class="fl_l" for="">选择年月：</label>
			<span class="fl_l" style="display: inline-block; position: relative;">
			<input id="startTime" onchange=""   name="startTime" value="${startTime }" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,readOnly:true})" /><span class="silent-custo-span fl_l">&nbsp;—&nbsp;</span><input id="endTime" onchange=""   name="endTime" value="${endTime }" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,readOnly:true})" /></span>
			<a href="###" class="com-btna fl_l" id="queryId"><i class="min-search"></i><label class="lab-mar">查询</label></a>
	        <a href="###" class="com-btna fl_r" id="exportId"><i class="min-remove-resou"></i><label class="lab-mar">导出</label></a>
	    </p>
	    <div class="com-table hyx-mpe-table hyx-cm-table">
	     <c:if test="${!empty optionList }">
	        <table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
	            <thead>
	            <tr class="sty-bgcolor-b">
	                <th style="width:100px;">
	                    <span class="sp sty-borcolor-b">小组</span>
	                </th>
	                   <c:forEach items="${optionList }" var="option" >
	                      <th><span class="sp sty-borcolor-b">${option.optionName }（人）</span></th>
	                   </c:forEach>
	            </tr>
	            </thead>
	            <tbody>
	            <tr>
	            <c:if test="${!empty list }">
	            
	               <c:forEach items="${list }" var="bean" varStatus="vs">
                       <tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">
	                     <c:forEach items="${bean }" var="cell" varStatus="vs">
	                              <c:choose>
	                                  <c:when test="${vs.index eq 0 }">
	                                      <td style="width:100px;"><div class="overflow_hidden w90" title="${cell.deptId }">${cell.deptName }</div></td>
	                                      <c:set var="deptId" value="${cell.deptId }"></c:set>                                  
	                                  </c:when>
	                                  <c:otherwise>
	                                      <td><div class="overflow_hidden w90"><a class="lost-custom-num" href="###" onclick="toLossSortDetail('${deptId }','${cell.optionId }')" title="${cell.total }">${cell.total }</a></div></td>
	                                  </c:otherwise>
	                              </c:choose>
	                     </c:forEach>
                            </tr>
	               </c:forEach>
			            <tr class="${(fn:length(list) % 2) ne 0 ? 'sty-bgcolor-b' : '' }">
			                <td style="width:100px;"><div class="overflow_hidden w100 link" title="合计">合计</div></td>
			                <c:forEach items="${totalList }" var="cell" varStatus="vs">
	                                     <td><div class="overflow_hidden w90"><a class="lost-custom-num" href="###"  onclick="toLossSortDetail('','${cell.optionId }')" title="${cell.total }">${cell.total }</a></div></td>
	                        </c:forEach>
			            </tr>
	            </c:if>
	          </tbody>
	         </table>
	     </c:if>
	</div>
	</div>
</form>
</body>
</html>
