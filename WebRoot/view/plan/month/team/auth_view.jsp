<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>审核中心-月计划审核(团队)</title>
<!--本页面样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<style type="text/css">
	body{background-color:#f4f4f4 !important;}
</style>
<script type="text/javascript" src="${ctx}/static/js/my97datepicker/WdatePicker.js"></script><!--选择年月日期插件-->
<script type="text/javascript" src="${ctx}/static/js/view/plan/day/checkBoxUtil.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/plan/month/team/auth_view.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js"></script>
<script type="text/javascript">
    var datevalue1="";
	var datevalue2="";
$(function(){

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
    		$('#startInputtime').val(moment(obj.date1).format('YYYY-MM-DD'));
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#endInputtime').val(moment(obj.date2).format('YYYY-MM-DD'));
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
			$("#startInputtime").val('');
			$("#endInputtime").val('');
		}
	}
}
function showDetail(planId,groupName,groupType){
	if(groupType==0){
		window.parent.pubDivShowIframe('detailWindow','${ctx}/plan/month/team/detailWindow?planId='+planId,'计划详情（'+groupName+'）',900,510);
	}else{
		window.parent.pubDivShowIframe('detailWindow','${ctx}/plan/month/group/detailWindow?planId='+planId,'计划详情（'+groupName+'）',900,510);
	}
}

function pickerTime(){
	var time=$("#time").val();
	var array=time.split("-");
	$("#planYear").val(array[0]);
	$("#planMonth").val(array[1].replace(/^0/g,''));
}


</script>
<script type="text/javascript">
window.onload=function(){
	var height = $(".hyx-ac-right").height()+30;
	window.parent.$("#iframepage").css({"height":height+"px"});
};
</script>
</head>
<body>
<form action="${ctx }/plan/month/group/authView">
	<input id="authState" name="authState" type="hidden" value="${item.authState}"/>
	<input id="planYear" name="planYear" type="hidden" value="${item.planYear}"/>
	<input id="planMonth" name="planMonth" type="hidden" value="${item.planMonth}"/>

	<input id="authUserIds" name="authUserIds" type="hidden" value="${authUserIds}"/>
	<input id="authUserAccs" name="authUserAccs" type="hidden" value="${authUserAccs}"/>
	<input id="groupIds" name="groupIds" type="hidden" value="${groupIds}"/>
<div class="hyx-ac-right">
	<div class="com-search hyx-cm-search" style="min-height:42px;">
		<div class="com-search-box fl_l">
		  <!--<p class="search clearfix"><input class="input-query fl_l" type="text" value="" /><label class="hide-span"></label></p> -->
		   <div class="list-box">
				<!--提交时间 -->
				<input type="hidden" name="startInputtime" id="startInputtime" value="${item.startInputtime }" />
				<input type="hidden" name="endInputtime" id="endInputtime" value="${item.endInputtime }" />
				<input type="hidden" name="oDateType" id="oDateType" value="${oDateType }" />
				<c:set var="oDateName" value="提交时间" />
				<c:choose>
					<c:when test="${oDateType eq 1 }">
						<c:set var="oDateName" value="当天" />
					</c:when>
					<c:when test="${oDateType eq 2 }">
						<c:set var="oDateName" value="本周" />
					</c:when>
					<c:when test="${oDateType eq 3 }">
						<c:set var="oDateName" value="本月" />
					</c:when>
					<c:when test="${oDateType eq 4 }">
						<c:set var="oDateName" value="半年" />
					</c:when>
					<c:when test="${oDateType eq 5 }">
						<c:set var="oDateName" value="${item.startInputtime.replaceAll('^20','').replace('-','.') }/${item.endInputtime.replaceAll('^20','').replace('-','.') }" />
					</c:when>
				</c:choose>

				<dl class="select">
					<dt>${oDateName }</dt>
					<dd>
						<ul>
							<li><a href="###" onclick="setPdate('oDateType',0)"
								title="提交时间">提交时间</a></li>
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

				<dl class="select">
					<c:set var="tt" value="计划月份" />
					<c:choose>
					<c:when test="${item.planYear ==null or item.planMonth ==null}"></c:when>
					<c:when test="${item.planMonth <= 9}">
						<c:set var="tt" value="${item.planYear}-0${item.planMonth}" />
					</c:when>
					<c:otherwise>
						<c:set var="tt" value="${item.planYear}-${item.planMonth}" />
					</c:otherwise>
					</c:choose>
					<dt><input id="time" value="${tt}" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,readOnly:true,onpicked:pickerTime})" style="height:21px;border:0;font-weight:bold;line-height:21px;"/></dt>
				</dl>
				<dl class="select" style="margin-bottom:0;">
					<c:choose>
						<c:when test="${item.authState==1}"><dt>待审核</dt></c:when>
						<c:when test="${item.authState==2}"><dt>通过</dt></c:when>
						<c:when test="${item.authState==0}"><dt>驳回</dt></c:when>
						<c:otherwise><dt>审核状态</dt></c:otherwise>
					</c:choose>

					<dd>
						<ul>
							<li><a href="javascript:authState(null)" title="审核状态">审核状态</a></li>
							<li><a href="javascript:authState(1)" title="待审核">待审核</a></li>
							<li><a href="javascript:authState(2)" title="通过">通过</a></li>
							<li><a href="javascript:authState(0)" title="驳回">驳回</a></li>
						</ul>
					</dd>
				</dl>


				<div class="reso-sub-dep fl_l">
					<input class="sub-dep-inpu" type="text" value="审核人" style="width:121px;border-right:2px solid #e1e1e1;margin-top:5px;">
					<div class="manage-drop">
						<div class="sub-dep-ul">
							<ul id="authUserTree" class="easyui-tree" data-options="animate:false,dnd:false"> </ul>
					    </div>
					    <div class="sure-cancle clearfix" style="width:120px">
							<a id="confirmAuthUserTree" class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" href="javascript:;"><label>确定</label></a>
							<a id="clearAuthUserTree" class="com-btnd bomp-btna fl_l" id="close02" href="javascript:;"><label>清空</label></a>
						</div>
					</div>
				</div>
				<div class="reso-sub-dep fl_l">
					<input class="owner-sour" type="text" value="提交团队" style="margin-top:5px;">
					<div class="manage-owner-sour">
						<div class="sub-dep-ul">
							<ul id="groupTree" class="easyui-tree" > </ul>
			    		</div>
					    <div class="sure-cancle clearfix" style="width:120px">
							<a id="confirmGroupTree" class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" href="javascript:;"><label>确定</label></a>
							<a id="clearGroupTree" class="com-btnd bomp-btna fl_l" id="close02" href="javascript:;"><label>清空</label></a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<input class="query-button fl_r" type="submit" value="查&nbsp;询" id="query"/>
	</div>
	<div class="com-btnlist hyx-cm-btnlist fl_l">
		<a href="javascript:;" class="com-btna demoBtn_a fl_l"><i class="min-pass"></i><label class="lab-mar">通&nbsp;&nbsp;&nbsp;&nbsp;过</label></a>
		<a href="javascript:;" class="com-btna demoBtn_b fl_l"><i class="min-rebut"></i><label class="lab-mar">驳&nbsp;&nbsp;&nbsp;&nbsp;回</label></a>
	</div>
	<div class="com-table com-mta hyx-cm-table fl_l" style="display:block;">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
					<th><span class="sp sty-borcolor-b">操作</span></th>
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>提交时间</span><i></i></label></span></th>
					<th><span class="sp sty-borcolor-b">提交人</span></th>
					<th><span class="sp sty-borcolor-b">意向客户</span></th>
					<th><span class="sp sty-borcolor-b">签约客户</span></th>
					<th><span class="sp sty-borcolor-b">回款金额</span></th>
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>计划月份</span><i></i></label></span></th>
					<th><span class="sp sty-borcolor-b">审核人</span></th>
					<th>审核状态</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
				<c:when test="${!empty list}">
					<c:forEach var="bean" items="${list}" varStatus="i">
						<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
						<c:if test="${i.index%2==0}"><tr></c:if>
							<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox"  id="chk_${bean.id}" value="${bean.id}" authState="${bean.authState }"/></div></td>
							<td style="width:30px;"><div class="overflow_hidden w30 link">
							<a href="javascript:showDetail('${bean.id}','${bean.groupName}','${bean.groupType}');" class="icon-detail" title="查看"></a>
							</div></td>
							<td><div class="overflow_hidden w120" title="<fmt:formatDate value="${bean.inputtime }" pattern="yyyy-MM-dd HH:mm"/>"><fmt:formatDate value="${bean.inputtime }" pattern="yyyy-MM-dd HH:mm"/></div></td>
							<td><div class="overflow_hidden w70" title="${bean.groupName }">${bean.groupName }</div></td>
							<td><div class="overflow_hidden w100" title="${bean.planWillcustnum }">${bean.planWillcustnum }</div></td>
							<td><div class="overflow_hidden w100" title="${bean.planSigncustnum }">${bean.planSigncustnum }</div></td>
							<td><div class="overflow_hidden w100" title="${bean.planMoney }">${bean.planMoney }</div></td>
							<td><div class="overflow_hidden w120" title="${bean.planYear }-${bean.planMonth }">${bean.planYear }-${bean.planMonth }</div></td>
							<td><div class="overflow_hidden w70" title="${bean.authUsername }">${bean.authUsername }</div></td>
							<c:choose>
								<c:when test="${bean.authState == 0}"><td><div class="overflow_hidden w120" title="驳回">驳回</div></td></c:when>
								<c:when test="${bean.authState == 1}"><td><div class="overflow_hidden w120" title="待审核">待审核</div></td></c:when>
								<c:when test="${bean.authState == 2}"><td><div class="overflow_hidden w120" title="通过">通过</div></td></c:when>
							</c:choose>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="no_data_tr"><td colspan="8" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
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
