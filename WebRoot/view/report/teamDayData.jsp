<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/common/report_include.jsp" %>
    <title>团队今日数据</title>
    <style type="text/css">
    	#data_table>thead th{cursor: pointer;}
    </style>
    <script type="text/javascript" src="${ctx }/static/js/view/report/todayData.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/view/report/teamDayData.js${_v}"></script>
    <script type="text/javascript">
        $(function () {
			$("#export").click(function(){
				var groupIds = $("#groupIds").val();
				window.location.href=ctx+"/report/teamDayData/export?groupIds="+groupIds+"&expType=1";
			});
			
			/* $("#searchBtn").click(function(){
				$("#form").submit();
	        }); */
			
			$(".ann-sale-plan li").click(function(){
				var rel = $(this).attr("rel");
				if(rel != null && rel != ''){
					window.location.href = rel;
				}
			});
			
			/* $("span[sort=true]").parent().click(function(){
				var orderColumn = $(this).find('span[sort=true]').attr("column");
				var orderType = '';
				if($(this).find('span[sort=true]').hasClass("td_sort_desc")){
					orderType = 'ASC';
				}else{
					orderType = 'DESC';
				}
				$("#orderColumn").val(orderColumn);
				$("#orderType").val(orderType);
				$("#form").submit();
			}); */
        });
    </script>
</head>
<body>
<div class="sales-statis-conta">
	<div class="year-sale-play">
		<ul class="ann-sale-plan clearfix" style="width:960px">
			<li class="li_first select" name="a">今日呼叫数据</li>
			<li name="b" rel="${ctx }/report/teamDayData/resContact">今日资源联系结果</li>
			<li name="c" rel="${ctx }/report/teamDayData/custContact">今日意向客户联系结果</li>
			<li rel="${ctx }/report/teamDayData/deals" class="li_last" name="d">今日交易结果</li>
		</ul>
	</div>
	<form id="form" action="${ctx }/report/teamDayData" method="post">
		<div class="sales-statis-conta">
		    <div class="team-today clearfix" style="margin-bottom:10px;">
		        <div class="com-search-report fl_l" style="margin-top:0;min-height:0">
		            <label class="fl_l" for="" >部门名称：</label>
		            <input type="hidden" id="groupIds" name="groupIdStr" value="${groupIdStr }" />
		            <dl class="select fl_l" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-right:10px;">
						<dt id="groupNameStr">-请选择-</dt>
						<dd>
							<ul id="tt1">
									
							</ul>
							<div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;">
								<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" onclick="selGroup()" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">确定</label></a>
								<a class="com-btnd bomp-btna com-btna-cancle fl_l" onclick="clearGroup()" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">清空</label></a>
							</div>
						</dd>
					</dl>
		        </div>
		        <a href="###" id="searchBtn" class="com-btna fl_l" data-flag="true"><i class="min-search"></i><label class="lab-mar">查询</label></a>
		        <a href="###" id="export" class="com-btna fl_r"><i class="min-remove-resou"></i><label class="lab-mar">导出</label></a>
		    </div>
<!-- 		    <p class="hyx-silent-p"> -->
<!-- 		        <label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">今日呼叫数据</label> -->
<!-- 		    </p> -->
			<!-- 排序 -->
			<input type="hidden" name="orderColumn" id="orderColumn" value="${callReportDto.orderColumn }" />
			<input type="hidden" name="orderType" id="orderType" value="${callReportDto.orderType }" />
		    <div class="com-table hyx-mpe-table hyx-cm-table">
		        <table id="data_table" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a" >
		            <thead>
		            <tr class="sty-bgcolor-b">
		                <th><span class="sp sty-borcolor-b">小组成员</span></th>
		                <th><span class="sp sty-borcolor-b">所属小组</span></th>
		                <th name="calloutTotal"><span class="sp sty-borcolor-b">呼出总数（次）<span class="td_sort_asc"></span></span></th>
		                <th name="calloutAnswer"><span class="sp sty-borcolor-b">呼出已接（次）<span class="td_sort_asc"></span></th>
		                <th name="validCalls"><span class="sp sty-borcolor-b">有效呼叫（次）<span class="td_sort_asc"></span></th>
<!-- 		                <th><span class="sp sty-borcolor-b">呼叫资源数（个）</span></th> -->
<!-- 		                <th><span class="sp sty-borcolor-b">呼叫意向客户数（个）</span></th> -->
<!-- 		                <th><span class="sp sty-borcolor-b">上门拜访量</span> -->
		                <th name="calloutTime"><span class="sp sty-borcolor-b">呼出时长（分钟）<span class="td_sort_asc"></span></th>
		                <th name="callinTime"><span class="sp sty-borcolor-b">呼入时长（分钟）<span class="td_sort_asc"></span></th>
						<th name="billingTime"><span class="sp sty-borcolor-b">计费时长（分钟）<span class="td_sort_asc"></span></span></th>
						<th name="billingTime"><span class="sp sty-borcolor-b">行动完成率</span></th>
		            </tr>
		            </thead>
		            <tbody></tbody>
		        </table>
		    </div>
		    <div class="person-todata-warm">
		        <h2>温馨提示：</h2>
		        <p><span>1、“计费时长”为本地参考值，实际计费以服务器计费系统数据为准。</span></p>
		        <p><span>2、&nbsp;&nbsp;&nbsp;单次通话“计费时长”不满一分钟时，按一分钟计算。</span></p>
		        <p><span>3、“有效呼叫”指在电话接通<label for="">${timeLength}</label>秒后(此项可在“系统设置-销售管理”中进行设置)</span></p>
		        <p><span>4、“呼出时长”是将用户的呼出时长以“秒”为单位进行合计后再换算成“分钟”为单位，不足1分钟部分按1分钟计算。</span></p>
        		<p><span>5、“呼入时长”是将用户的呼入时长以“秒”为单位进行合计后再换算成“分钟”为单位，不足1分钟部分按1分钟计算。</span></p>
		    </div>
		</div>
	</form>
</div>
</body>
</html>
