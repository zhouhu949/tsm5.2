<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>我的计划-待联系资源列表</title>
	<%@ include file="/common/include.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css${_v}"><!--弹框样式-->
	<script type="text/javascript" src="${ctx}/static/js/view/plan/day/checkBoxUtil.js${_v}"></script>
	<script type="text/javascript" src="${ctx}/static/js/view/plan/day/willcust_add.js${_v}"></script>
	<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
		<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
	<c:if test="${idReplaceWord==1 }">
	<script type="text/javascript" src="${ctx}/static/js/view/plan/day/phoneNumber.js${_v}"></script>
	<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
	</c:if>
	<!--本页面js-->
	<script type="text/javascript">
	var dateTime =${dateTime};

	var datevalue1="";
	var datevalue2="";
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

	$('#d2').dateRangePicker({
		separator : '/',
		format: 'YY.MM.DD'
    }).bind('datepicker-change',function(event,obj){
    	if(obj.date1 != 'Invalid Date'){
    		$('#startActionDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#endActionDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    	}
    	var s = $(this).parents('.select');
    	var dt=s.children("dt");
        var dd=s.children("dd");
        dt.html(obj.value);
        $("#lDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
        //s.css("z-index",z);
	});

});

function progress(value){
	$("#custStatusId").val(value);
}
function custType(value){
	$("#custTypeId").val(value);
}

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

function doQuery(){
	//alert(datevalue1+'|'+datevalue2);
}
</script>
</head>
<body>
<form id="addResourceForm" action="${ctx}/plan/day/willcust/willcustAddWindow" method="post">
<input id="dateTime" name ="dateTime" type="hidden" value="${dateTime}"/>

<input id="custStatusId" name ="custStatusId" type="hidden" value="${item.custStatusId}"/>
<input id="custTypeId" name ="custTypeId" type="hidden" value="${item.custTypeId}"/>
<div class='bomp-cen bomp-mpb'>
	<div class="com-search bomp-search">
		<div class="com-search-box fl_l">
		   <div class="search clearfix" >
			   <div class="select_outerDiv fl_l" style="margin:5px 0 0 8px;">
			   <span class="icon_down_5px"></span>
			   <select name="queryType" class="fl_l search_head_select">
					<c:if test="${isState eq 0}">
					<option value="4" ${(empty item.queryType or item.queryType eq '4') ? 'selected' : ''}>单位名称</option>
					</c:if>
					<option value="1" ${item.queryType eq '1' ? 'selected' : ''}>${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</option>
					<c:if test="${isState eq 1}">
					<option value="2" ${item.queryType eq '2' ? 'selected' : ''}>联系人</option>
					</c:if>
					<option value="3" ${item.queryType eq '3' ? 'selected' : ''}>联系电话</option>
				</select>
			   	</div>
			   	<input class="input-query fl_l" type="text" name="queryStr" value="${item.queryStr }" />
			   	<label class="hide-span"></label>
		  	</div>
		  	<div class="list-box">
		   		<dl class="select">
					<c:set var="proName" value="客户类型" />
					<c:forEach items="${custTypes }" var="custType">
						<c:if test="${item.custTypeId ==custType.optionlistId}">
							<c:set var="proName" value="${ custType.optionName}" />
						</c:if>
					</c:forEach>
					<dt>${proName}</dt>
					<dd>
						<ul>
							<li><a href="javascript:custType(null);" title="客户类型">客户类型</a></li>
							<c:forEach items="${custTypes }" var="custType">
							<li><a href="javascript:custType('${custType.optionlistId }');" title="${custType.optionName }">${ custType.optionName}</a></li>
							</c:forEach>
						</ul>
					</dd>
			   </dl>
			   <dl class="select">
					<c:set var="proName" value="销售进程" />
					<c:forEach items="${salProgress }" var="progress">
						<c:if test="${item.custStatusId ==progress.optionlistId}">
							<c:set var="proName" value="${ progress.optionName}" />
						</c:if>
					</c:forEach>
					<dt style="border-right:none !important;">${proName}</dt>
					<dd>
						<ul>
							<li><a href="javascript:progress(null);" title="销售进程">销售进程</a></li>
							<c:forEach items="${salProgress }" var="progress">
							<li><a href="javascript:progress('${progress.optionlistId }');" title="${progress.optionName }">${ progress.optionName}</a></li>
							</c:forEach>
						</ul>
					</dd>
				</dl>

				<!-- 添加/分配时间 -->
				<input type="hidden" name="pstartDate" id="pstartDate" value="${item.pstartDate }" />
				<input type="hidden" name="pendDate" id="pendDate" value="${item.pendDate }" />
				<input type="hidden" name="oDateType" id="oDateType" value="${item.oDateType }" />
				<c:set var="oDateName" value="添加/分配时间" />
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

				<dl class="select">
					<dt>${oDateName }</dt>
					<dd>
						<ul>
							<li><a href="###" onclick="setPdate('oDateType',0)"
								title="添加/分配时间">添加/分配时间</a></li>
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
				<!-- 最近联系时间 -->
				<input type="hidden" name="startActionDate" id="startActionDate" value="${item.startActionDate }" />
				<input type="hidden" name="endActionDate" id="endActionDate" value="${item.endActionDate }" />
				<input type="hidden" name="lDateType" id="lDateType" value="${item.lDateType }" />
				<c:set var="lDateName" value="最近联系时间" />
				<c:choose>
					<c:when test="${item.lDateType eq 1 }">
						<c:set var="lDateName" value="当天" />
					</c:when>
					<c:when test="${item.lDateType eq 2 }">
						<c:set var="lDateName" value="本周" />
					</c:when>
					<c:when test="${item.lDateType eq 3 }">
						<c:set var="lDateName" value="本月" />
					</c:when>
					<c:when test="${item.lDateType eq 4 }">
						<c:set var="lDateName" value="半年" />
					</c:when>
					<c:when test="${item.lDateType eq 5 }">
						<c:set var="lDateName" value="${item.startActionDate.replaceAll('^20','').replace('-','.') }/${item.endActionDate.replaceAll('^20','').replace('-','.') }" />
					</c:when>
				</c:choose>

				<dl class="select">
					<dt style="border-right:0 none !important;">${lDateName }</dt>
					<dd>
						<ul>
							<li><a href="###" onclick="setPdate('lDateType',0)"
								title="最近联系时间">最近联系时间</a></li>
							<li><a href="###" onclick="setPdate('lDateType',1)"
								title="当天">当天</a></li>
							<li><a href="###" onclick="setPdate('lDateType',2)"
								title="本周">本周</a></li>
							<li><a href="###" onclick="setPdate('lDateType',3)"
								title="本月">本月</a></li>
							<li><a href="###" onclick="setPdate('lDateType',4)"
								title="半年">半年</a></li>
							<li><a href="###" id="d2" title="自定义"
								class="diy date-range">自定义</a></li>
						</ul>
					</dd>
				</dl>
				<p class="check skin-minimal fl_l" style="margin:0 0 0 62px"><input type="checkbox" name ="isMajor" value="1" <c:if test="${item.isMajor==1}"> checked="checked"</c:if>/><label>重点客户</label></p>
			</div>
		</div>
		<input class="query-button fl_r" type="submit" value="查&nbsp;询" id="query" />
	</div>
	<div class="com-btnlist fl_l">
		<a href="javascript:;" class="com-btna demoBtn_a ml"><label>加入计划</label></a>
	</div>
	<div class='com-table bomp-table-a fl_l'>
		<table width='100%' cellspacing='0' cellpadding='0' class='sty-borcolor-a sty-borcolor-c com-table-b com-radius'>
			<thead>
				<tr class='sty-bgcolor-b'>
					<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
					<th><span class='sp sty-borcolor-b'>${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</span></th>
					<c:choose>
					<c:when test="${isState eq 1}"><th><span class='sp sty-borcolor-b'>联系人</span></th></c:when>
					<c:otherwise><th><span class='sp sty-borcolor-b'>单位名称</span></th></c:otherwise>
					</c:choose>
					<th><span class='sp sty-borcolor-b'>联系电话</span></th>
					<th><span class='sp sty-borcolor-b'>客户类型</span></th>
					<th><span class="sp sty-borcolor-b">销售进程</span></th>
					<th><span class="sp sty-borcolor-b">最近联系</span></th>
					<th><span class="sp sty-borcolor-b">下次联系</span></th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
				<c:when test="${!empty list}">
					<c:forEach var="bean" items="${list}" varStatus="i">
						<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
						<c:if test="${i.index%2==0}"><tr></c:if>
							<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" id="chk_${bean.custId}" value="${bean.custId}"/></div></td>
							<td><div class="overflow_hidden w60" title="${bean.custName}">${bean.custName}</div></td>
							<c:choose>
							<c:when test="${isState eq 1}">
							<td><div class="overflow_hidden w70" title="${bean.custUser}">${bean.custUser}</div></td>
							</c:when>
							<c:otherwise>
							<td><div class="overflow_hidden w70" title="${bean.company}">${bean.company}</div></td>
							</c:otherwise>
							</c:choose>
							<td><div phone="tel" class="overflow_hidden w70" title="${bean.custTel}">${bean.custTel}</div></td>
							<td><div class="overflow_hidden w70" title="${bean.custTypeName}">${bean.custTypeName}</div></td>
							<td><div class="overflow_hidden w100" title="${bean.custStatus}">${bean.custStatus}</div></td>
							<td><div class="overflow_hidden w110" title="${bean.actionDateStr}">${bean.actionDateStr}</div></td>
							<td><div class="overflow_hidden w110" title="${bean.custNexttimeStr}">${bean.custNexttimeStr}</div></td>
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
