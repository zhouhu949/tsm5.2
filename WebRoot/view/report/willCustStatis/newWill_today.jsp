<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/common/include.jsp"%>
    <title>意向客户分析-今日数据</title>
    <style type="text/css">
    	#data_table>thead th{cursor: pointer;}
    	 .team-today .select dd ul{max-height:140px;}
    	.team-today .select dd ul{width:180px;}
    	.tree-node{width:auto;}
    </style>
    <script type="text/javascript" src="${ctx }/static/js/view/report/newwill_today.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/view/report/newwill_todayData.js${_v}"></script>
    <script type="text/javascript">
        $(function () {
			$("#export").click(function(){
				var groupIds = $("#groupIds").val();
				window.location.href=ctx+"/newWill/export?groupIds="+groupIds+"&expType=1";
			});
			
			
		});
			
		
        window.onload=function(){
           heightReset();
        };
        function heightReset(){
        	 var height = $(".sales-statis-conta").height()+30;
           	 window.parent.$("#iframepage").css({"height":height+"px"});
        }	
    </script>
</head>
<body>
<div class="sales-statis-conta">
	<form id="form" action="${ctx }/newWill/testdaydata" method="post">
		<div class="sales-statis-conta">
		    <div class="team-today clearfix" style="margin-bottom:10px;">
		        <div class="com-search-report fl_l" style="margin-top:-4px;min-height:0">
		            <label class="fl_l" for="" style="line-height:34px;">部门名称：</label>
		            <input type="hidden" id="groupIds" name="groupIds" value="${groupIds }" />
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
		        <a href="###" id="searchBtn" class="com-btna fl_l"><i class="min-search"></i><label class="lab-mar">查询</label></a>
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
		                
		            </tr>
		            </thead>
		            <tbody></tbody>
		        </table>
		    </div>
		    <div class="person-todata-warm">
		        <h2>温馨提示：</h2>
		        <p><span>1.今日新增意向数：统计今日新增加的意向客户中，客户状态仍为意向的数量，不含已经转变为其他状态的客户数量；</span></p>
		        <p><span>2.根据意向客户今日销售进程的结果，统计每个销售进程今日的新增量；</span></p>
		        <p><span>3.新增意向数的数量小于等于各销售进程新增量之和；</span></p>
		    </div>
		</div>
	</form>
</div>
</body>
</html>
