<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>我的计划-日计划审核</title>
<script type="text/javascript" src="${ctx}/static/js/view/plan/day/checkBoxUtil.js${_v}"></script>
	<script type="text/javascript" src="${ctx}/static/js/view/plan/day/day_plan_auth.js${_v}"></script>
	<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript">
$(function(){
	/*表单优化*/

    /*时间弹框*/
	$.dateRangePickerLanguages['custom'] ={
		'selected': 'Choosed:',
		'days': 'Days',
		'apply': 'Close',
		'week-1' : 'Mon',
		'week-2' : 'Tue',
		'week-3' : 'Wed',
		'week-4' : 'Thu',
		'week-5' : 'Fri',
		'week-6' : 'Sat',
		'week-7' : 'Sun',
		'month-name': ['January','February','March','April','May','June','July','August','September','October','November','December'],
		'shortcuts' : 'Shortcuts',
		'past': 'Past',
		'7days' : '7days',
		'14days' : '14days',
		'30days' : '30days',
		'previous' : 'Previous',
		'prev-week' : 'Week',
		'prev-month' : 'Month',
		'prev-quarter' : 'Quarter',
		'prev-year' : 'Year',
		'less-than' : 'Date range should longer than %d days',
		'more-than' : 'Date range should less than %d days',
		'default-more' : 'Please select a date range longer than %d days',
		'default-less' : 'Please select a date range less than %d days',
		'default-range' : 'Please select a date range between %d and %d days',
		'default-default': 'This is costom language'
	};
/*时间弹框*/

	$('#d1').dateRangePicker({
		separator : '/',
		format: 'YY.MM.DD'
    }).bind('datepicker-change',function(event,obj){
    	if(obj.date1 != 'Invalid Date'){
    		$('#pstartDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#pendDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    	}
    	var s = $(this).parents('.select');
    	var dt=s.children("dt");
      	var dd=s.children("dd");
        dt.html(obj.value);
        $("#oDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
        //s.css("z-index",z);
	});
});

function setPdate(id,type){
	$("#"+id).val(type);
	if(type==0){
		if('oDateType'==id){
			$("#pstartDate").val('');
			$("#pendDate").val('');
		}else{
			$("#startActionDate").val('');
			$("#endActionDate").val('');
		}
	}
}
</script>
<script type="text/javascript">
	window.onload=function(){
		var height = $(".hyx-cfu-left").height()+30;
		window.parent.$("#iframepage").css({"height":height+"px"});
	};
	</script>
</head>
<body>
<form action="${ctx }/plan/day/auth/view">
	<input id="authState" name="authState" type="hidden" value="${item.authState}"/>
	<input id="groupIds" name="groupIds" type="hidden" value="${groupIds}"/>
	<input id="userIds" name="userIds" type="hidden" value="${userIds}"/>
	<input id="userAccs" name="userAccs" type="hidden" value="${userAccs}"/>

<div class="hyx-cfu-left hyx-ctr hyx-sc">
   <div class="com-search hyx-cfu-search" style="min-height:42px;">
      <div class="com-search-box fl_l">
	   <div class="list-box">
			<dl class="select" style="margin-bottom:0;">
				<c:choose>
					<c:when test="${item.authState==0}"><dt>待审核</dt></c:when>
					<c:when test="${item.authState==1}"><dt>通过</dt></c:when>
					<c:when test="${item.authState==2}"><dt>驳回</dt></c:when>
					<c:otherwise><dt>审核状态</dt></c:otherwise>
				</c:choose>

				<dd>
					<ul>
						<li><a href="javascript:authState(null)" title="审核状态">审核状态</a></li>
						<li><a href="javascript:authState(0)" title="待审核">待审核</a></li>
						<li><a href="javascript:authState(1)" title="通过">通过</a></li>
						<li><a href="javascript:authState(2)" title="驳回">驳回</a></li>
					</ul>
				</dd>
			</dl>

			<!-- 添加/分配时间 -->
			<input type="hidden" name="pstartDate" id="pstartDate" value="${item.pstartDate }" />
			<input type="hidden" name="pendDate" id="pendDate" value="${item.pendDate }" />
			<input type="hidden" name="oDateType" id="oDateType" value="${item.oDateType }" />
			<c:set var="oDateName" value="计划日期" />
			<c:choose>
				<c:when test="${item.oDateType eq 1 }">
					<c:set var="oDateName" value="当天" />
				</c:when>
				<c:when test="${item.oDateType eq 2 }">
					<c:set var="oDateName" value="本周" />
				</c:when>
				<c:when test="${item.oDateType eq 3 }">
					<c:set var="oDateName" value="本月" />
				</c:when>
				<c:when test="${item.oDateType eq 4 }">
					<c:set var="oDateName" value="半年" />
				</c:when>
				<c:when test="${item.oDateType eq 5 }">
					<c:set var="oDateName" value="${item.pstartDate.replaceAll('^20','').replace('-','.') }/${item.pendDate.replaceAll('^20','').replace('-','.') }" />
				</c:when>
			</c:choose>

			<dl class="select add-fp-time">
				<dt>${oDateName }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="setPdate('oDateType',0)"
							title="计划日期">计划日期</a></li>
						<li><a href="###" onclick="setPdate('oDateType',1)"
							title="当天">当天</a></li>
						<li><a href="###" onclick="setPdate('oDateType',2)"
							title="本周">本周</a></li>
						<li><a href="###" onclick="setPdate('oDateType',3)"
							title="本月">本月</a></li>
						<li><a href="###" onclick="setPdate('oDateType',4)"
							title="半年">半年</a></li>
						<li><a href="###" id="d1" title="自定义"
							class="diy date-range">自定义</a></li>
					</ul>
				</dd>
			</dl>

			<!-- <div class="reso-sub-dep fl_l">
				<input class="sub-dep-inpu" type="text" value="所在团队" style="border-right:2px solid #e1e1e1">
				<div class="manage-drop">
					<div class="sub-dep-ul">
						<ul id="groupTree" class="easyui-tree" data-options="animate:false,dnd:false"></ul>
				    </div>
				    <div class="sure-cancle clearfix" style="width:120px">
						<a id="confirmGroupTree" class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" href="javascript:;"><label>确定</label></a>
						<a id="clearGroupTree" class="com-btnd bomp-btna fl_l"  href="javascript:;"><label>清空</label></a>
					</div>
				</div>
			</div> -->
			<div class="reso-sub-dep fl_l">
				<input class="owner-sour" type="text" value="提交人">
				<div class="manage-owner-sour">
					<div class="sub-dep-ul">
						<ul id="userTree" class="easyui-tree" data-options="animate:true,dnd:false"></ul>
		    		</div>
				    <div class="sure-cancle clearfix" style="width:120px">
						<a id="confirmUser" class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" href="javascript:;"><label>确定</label></a>
						<a id="clearUser"  class="com-btnd bomp-btna fl_l" id="close02" href="javascript:;"><label>清空</label></a>
					</div>
				</div>
			</div>

		</div>
	</div>
		<input class="query-button fl_r" type="submit" value="查&nbsp;询" id="query" />
   </div>
   <div class="com-btnlist hyx-cm-btnlist">
		<a href="javascript:;" class="com-btna demoBtn_a fl_l"><i class="min-pass"></i><label class="lab-mar">通&nbsp;&nbsp;&nbsp;&nbsp;过</label></a>
		<a href="javascript:;" class="com-btna demoBtn_b fl_l"><i class="min-rebut"></i><label class="lab-mar">驳&nbsp;&nbsp;&nbsp;&nbsp;回</label></a>
	</div>
	<div class="com-table com-mta hyx-cla-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
					<th><span class="sp sty-borcolor-b">所在小组</span></th>
					<th><span class="sp sty-borcolor-b">计划提交人</span></th>
					<th><span class="sp sty-borcolor-b">计划日期</span></th>
					<th><span class="sp sty-borcolor-b">计划资源联系数</span></th>
					<th><span class="sp sty-borcolor-b">计划意向联系数</span></th>
					<th><span class="sp sty-borcolor-b">计划签约联系数</span></th>
					<th>审核结果</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
				<c:when test="${!empty list}">
					<c:forEach var="bean" items="${list}" varStatus="i">
						<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
						<c:if test="${i.index%2==0}"><tr></c:if>
							<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox"  id="chk_${bean.id}" value="${bean.id}" authState="${bean.authState}"/></div></td>
							<td><div class="overflow_hidden w70" title="${bean.groupName}">${bean.groupName}</div></td>
							<td><div class="overflow_hidden w70" title="${bean.userName}">${bean.userName}</div></td>
							<td><div class="overflow_hidden w70" title="${bean.planDateStr}">${bean.planDateStr}</div></td>
							<td><div class="overflow_hidden w100" title="${bean.resourceCount}">${bean.resourceCount}</div></td>
							<td><div class="overflow_hidden w70" title="${bean.willcustCount}">${bean.willcustCount}</div></td>
							<td><div class="overflow_hidden w700" title="${bean.signcustCount}">${bean.signcustCount}</div></td>
							<td><div class="overflow_hidden w100" title="${bean.authStateStr}">${bean.authStateStr}</div></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr><td colspan="8" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
				</c:otherwise>
			    </c:choose>
			</tbody>
		</table>
	</div>
	<div class="com-bot">${item.page.pageStr}</div>
</div>
</form>
</body>
</html>
