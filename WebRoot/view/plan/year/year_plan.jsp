<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-年度销售规划-年度规划</title>
<style>
	.hyx-aspa .votebox .box .tip_up{background-position: -45px -316px;}
	.hyx-aspa .votebox .box .tip_down{background-position: -25px -316px;}
</style>
<script type="text/javascript" src="${ctx}/static/js/view/plan/year/year_plan_css.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/plan/year/year_plan.js"></script>
<script type="text/javascript">
var ifInit = ${dto.ifInit};
var dateTime=${dateTime};
var currentDateTime =${currentDateTime};
var date=new Date(dateTime);
var serverDate=new Date(currentDateTime);
</script>

</head>
<body>
<form id="yearPlanForm" action="${ctx}/plan/year/view${_v}">
	<input id="dateTime" name="dateTime" type="hidden" value="${dateTime}"/>
</form>
<div class="year-sale-play hyx-aspa-play">
	<ul class="ann-sale-plan clearfix">
		<a href="${ctx}/plan/year/view"><li class="select li_first">年度规划</li></a>
		<a href="${ctx}/plan/year/progress/view"><li>执行进度</li></a>
		<a href="${ctx}/plan/yearlog/view"><li class="li_last">变更日志</li></a>
	</ul>
</div>
<div class="hyx-hsrs-bot">
	<div class="hyx-cfu-left hyx-ctr hyx-aspa" style="margin:auto 0;">
		<div class="hyx-aspa-time">
			<label class="center">
				<span class="sp_a fl_l">
					<label class="left">&lt;</label>
					<label class="date">2015</label>
					<label class="right">&gt;</label>
				</span>
				<span class="sp_b fl_l">年度规划</span>
			</label>
		</div>

		<div class="votebox">
	        <dl class="barbox">
	            <dd class="barline">
	                <span style="width:0%;" class="charts"></span>
	                <label class="box plan-value">
	                	<label class="tit_a">计划销售额</label>
	                	<label class="p" name="${progress.planMoney}">0</label>
	                	<i class="tip tip_down"></i>
	                </label>
	                <span style="width:0%;" class="play-show"></span>
	                <label class="box box_bot actual-value">
	                	<i class="tip tip_up"></i>
	               		<label class="p p2" name="${progress.factMoney}">0</label>
	                	<label class="tit_a">实际销售额</label>
	               	</label>
	            </dd>
	        </dl>
	        <span class="sp"><label class="tit">全年规划销售额</label><label class="b">${progress.planMoneyAll}</label>万<i class="tip" style="display:block;"></i></span>
	    </div>

	   <div class="com-btnlist">
	   		<label class="comp fl_l">单位：万元</label>
			<a href="javascript:;" class="com-btna demoBtn_a fl_r"><i class="min-rise"></i><label class="lab-mar">历史走势</label></a>
		</div>
		<div class="com-table-a com-mta hyx-aspa-table">
			<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius" style="border-collapse:separate;">
				<thead>
					<tr class="sty-bgcolor-b">
						<th class="th_bg"><span class="sp sty-borcolor-b bor-left-none"></span><span class="font_a">月份</span><span class="font_b">团队名称</span></th> 
						<th><span class="sp sty-borcolor-b">1月</span></th>
						<th><span class="sp sty-borcolor-b">2月</span></th>
						<th><span class="sp sty-borcolor-b">3月</span></th>
						<th><span class="sp sty-borcolor-b">4月</span></th>
						<th><span class="sp sty-borcolor-b">5月</span></th>
						<th><span class="sp sty-borcolor-b">6月</span></th>
						<th><span class="sp sty-borcolor-b">7月</span></th>
						<th><span class="sp sty-borcolor-b">8月</span></th>
						<th><span class="sp sty-borcolor-b">9月</span></th>
						<th><span class="sp sty-borcolor-b">10月</span></th>
						<th><span class="sp sty-borcolor-b">11月</span></th>
						<th><span class="sp sty-borcolor-b">12月</span></th>
						<th><span class="sp sty-borcolor-b">全年</span></th>
					</tr>
				</thead>
				<tbody>              
					<c:choose>
					<c:when test="${dto !=null and !empty dto.list}">
						<c:forEach var="gyDto" items="${dto.list}" varStatus="i">
							<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
							<c:if test="${i.index%2==0}"><tr></c:if>
							<td><div class="overflow_hidden" title="${gyDto.group.groupName}">${gyDto.group.groupName}</div></td>
							<c:forEach var="month" items="${dto.months}" varStatus="i">
								<c:choose>
								<c:when test="${gyDto.map !=null and gyDto.map[month]!=null}">
									<td><div class="overflow_hidden" title="${gyDto.map[month]}"><input type="text" dbId="${gyDto.idMap[month]}" month="${month}" groupName="${gyDto.group.groupName }" groupId="${gyDto.group.groupId }" value="${gyDto.map[month]}" dbValue="${gyDto.map[month]}" class="ipt-bor moneyInput" /></div></td>
								</c:when>
								<c:otherwise>
									<td><div class="overflow_hidden" title="0.00"><input type="text" value="0.00" class="ipt-bor" /></div></td>
								</c:otherwise>
								</c:choose>
							</c:forEach>
						</tr>
						</c:forEach>
						
						<c:if test="${(dto.list.size())%2!=0}"><tr class="sty-bgcolor-b"></c:if>
						<c:if test="${(dto.list.size())%2==0}"><tr></c:if>
						<td><div class="overflow_hidden" title="合计">合计</div></td>
						<c:forEach var="month" items="${dto.months}" varStatus="i">
								<c:choose>
								<c:when test="${dto.totalByMonth !=null and dto.totalByMonth[month]!=null}">
									<td><div class="overflow_hidden" title="${dto.totalByMonth[month]}"><input type="text" value="${dto.totalByMonth[month]}" class="ipt-bor" /></div></td>
								</c:when>
								<c:otherwise>
									<td><div class="overflow_hidden" title="0.00"><input type="text" value="0.00" class="ipt-bor" /></div></td>
								</c:otherwise>
								</c:choose>
							</c:forEach>
						</tr>
					</c:when>
					<c:otherwise>
						<tr><td colspan="14" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
					</c:otherwise>
				    </c:choose>
					
				</tbody>
			</table>
		</div>
		<div class="com-btnbox-a">
			<label class='com-btnbox-cen'>
				<a href="javascript:;" class="com-btnb com-btnb-sty hyx-aspa-save fl_l" style="display:none;"><label>保&nbsp;&nbsp;&nbsp;&nbsp;存</label></a>
				<a href="javascript:;" class="com-btnb com-btnb-sty hyx-aspa-cancel fl_l" style="display:none;"><label>取&nbsp;&nbsp;&nbsp;&nbsp;消</label></a>
				<a href="javascript:;" class="com-btnb com-btnb-sty hyx-aspa-edit fl_l"><label>编&nbsp;&nbsp;&nbsp;&nbsp;辑</label></a>
			</label>
		</div>
	</div>
</div>
</body>
</html>
