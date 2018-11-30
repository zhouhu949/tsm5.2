<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-月度计划（团队编辑）-月度计划编辑（个人）-添加客户</title>
<!--公共样式-->
<script type="text/javascript" src="${ctx}/static/js/view/plan/day/checkBoxUtil.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js"></script>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js"></script>
<script type="text/javascript">
var datevalue1="";
var planId="${planId}";
$(function(){
	var checkBox= new CheckBox("checkAll","chk_","");
	var clickNum=0;
    $(".sure-btn").click(function(){
		if(clickNum==0 && checkBox.getIds()!=null){
			clickNum++;
			window.parent.addCust(checkBox.getIds());
		}
	});

	$(".cancel-btn").click(function(){
		closeParentPubDivDialogIframe("addCustWindow");
	});

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

	/* $('#d1').dateRangePicker({
    }).bind('datepicker-change',function(event,obj){
		datevalue1=obj.value;
	}); */

	$('#d2').dateRangePicker({
		separator : '/',
		format: 'YY.MM.DD'
	}).bind('datepicker-change',function(event,obj){
		if(obj.date1 != 'Invalid Date'){
			$('#startNextContactDate').val(moment(obj.date1).format('YYYY-MM-DD'));
		}
		if(obj.date2 != 'Invalid Date'){
			$('#endNextContactDate').val(moment(obj.date2).format('YYYY-MM-DD'));
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
	$("#saleProcessId").val(value);
}
function custType(value){
	$("#custType").val(value);
}

function setPdate(id,type){
	$("#"+id).val(type);
	if(type==0){
		$("#startNextContactDate").val('');
		$("#endNextContactDate").val('');
	}
}

function doQuery(){

}
</script>
</head>
<body>
<form id="addCustForm" action="${ctx}/plan/month/user/addCustWindow" method="post">
<input id="planId" name ="planId" type="hidden" value="${planId}"/>
<input id="custIdsStr" name ="custIdsStr" type="hidden" value="${custIdsStr}"/>
<input id="saleProcessId" name ="saleProcessId" type="hidden" value="${item.saleProcessId}"/>
<input id="custType" name ="custType" type="hidden" value="${item.custType}"/>



<div class='bomp-cen bomp-mpb'>
	<div class="com-search bomp-search">
		<div class="com-search-box fl_l">
		<div class="search clearfix" >
			   <div class="select_outerDiv fl_l">
			   <span class="icon_down_5px"></span>
			   <select name="queryType" class="fl_l search_head_select">
					<c:if test="${isState eq 0}">
					<option value="4" ${(empty item.queryType or item.queryType eq '4') ? 'selected' : ''}>单位名称</option>
					</c:if>
					<option value="1" ${item.queryType eq '1' ? 'selected' : ''}>${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</option>
					<c:if test="${isState eq 1}">
					<option value="2" ${item.queryType eq '2' ? 'selected' : ''}>联系人</option
					</c:if>
					<option value="3" ${item.queryType eq '3' ? 'selected' : ''}>联系电话</option>
				</select>
			   	</div>
			   	<input class="input-query fl_l" type="text" name="queryText" value="${resCustInfoDto.queryText }" />
			   	<label class="hide-span"></label>
		  	</div>
		   <div class="list-box">
			   	<dl class="select">
					<c:set var="proName" value="客户状态" />
					<c:if test="${item.custType ==2}"> <c:set var="proName" value="意向客户" /></c:if>
					<c:if test="${item.custType ==3}"> <c:set var="proName" value="签约客户" /></c:if>
					<dt>${proName}</dt>
					<dd>
						<ul>
							<li><a href="javascript:custType(6);" title="客户状态">客户状态</a></li>
							<li><a href="javascript:custType(2);" title="意向客户">意向客户</a></li>
							<li><a href="javascript:custType(3);" title="签约客户">签约客户</a></li>
						</ul>
					</dd>
			   </dl>
		   	   <dl class="select">
					<c:set var="proName" value="销售进程" />
					<c:forEach items="${salProgress }" var="progress">
						<c:if test="${item.saleProcessId ==progress.optionlistId}">
							<c:set var="proName" value="${ progress.optionName}" />
						</c:if>
					</c:forEach>
					<dt>${proName}</dt>
					<dd>
						<ul>
							<li><a href="javascript:progress(null);" title="销售进程">销售进程</a></li>
							<c:forEach items="${salProgress }" var="progress">
							<li><a href="javascript:progress('${progress.optionlistId }');" title="${progress.optionName }">${ progress.optionName}</a></li>
							</c:forEach>
						</ul>
					</dd>
				</dl>
				<!--合同到期时间 -->
				<input type="hidden" name="startNextContactDate" id="startNextContactDate" value="${item.startNextContactDate }" />
				<input type="hidden" name="endNextContactDate" id="endNextContactDate" value="${item.endNextContactDate }" />
				<input type="hidden" name="lDateType" id="lDateType" value="${item.lDateType }" />
				<c:set var="lDateName" value="下次联系时间" />
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
						<c:set var="lDateName" value="${item.startNextContactDate.replaceAll('^20','').replace('-','.') }/${item.endNextContactDate.replaceAll('^20','').replace('-','.') }" />
					</c:when>
				</c:choose>

				<dl class="select">
					<dt style="border-right:none !important;">${lDateName }</dt>
					<dd>
						<ul>
							<li><a href="###" onclick="setPdate('lDateType',0)"
								title="最近联系时间">下次联系时间</a></li>
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
				<p class="check skin-minimal fl_l" style="margin:-5px 0 0 25px"><input type="checkbox" name="isMajor" value="1" <c:if test="${item.isMajor==1 }">checked="checked"</c:if>/><label style="height:20px;">重点客户</label></p>
			</div>
		</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
	</div>
	<div class='com-table bomp-table-a fl_l'>
		<table width='100%' cellspacing='0' cellpadding='0' class='sty-borcolor-a sty-borcolor-c com-table-b com-radius'>
			<thead>
				<tr class='sty-bgcolor-b'>
					<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
					<th><span class='sp sty-borcolor-b'>${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</span></th>
					<c:if test="${isState eq 0}"><th><span class='sp sty-borcolor-b'>单位名称</span></th></c:if>
					<th><span class='sp sty-borcolor-b'>销售进程</span></th>
					<th><span class='sp sty-borcolor-b'>客户类型</span></th>
					<th><span class='sp sty-borcolor-b'>客户状态</span></th>
					<th><span class='sp sty-borcolor-b'>重点客户</span></th>
					<th>下次联系时间</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
				<c:when test="${!empty list}">
					<c:forEach var="bean" items="${list}" varStatus="i">
						<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
						<c:if test="${i.index%2==0}"><tr></c:if>
							<td style="width:20px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" id="chk_${bean.resCustId}" value="${bean.resCustId}"/></div></td>
							<td><div class="overflow_hidden w60" title="${bean.name}">${bean.name}</div></td>
							<c:if test="${isState eq 0}">
								<td><div class="overflow_hidden w60" title="${bean.company}">${bean.company}</div></td>
							</c:if>
							<td><div class="overflow_hidden w90" title="${bean.saleProcessName}"></div>${bean.saleProcessName}</td>
							<td><div class="overflow_hidden w60" title="${bean.custTypeName}">${bean.custTypeName}</div></td>

							<c:choose>
							<c:when test="${bean.status==6}"><td><div class="overflow_hidden w60" title="签约客户">签约客户</div></td></c:when>
							<c:otherwise><td><div class="overflow_hidden w60" title="意向客户">意向客户</div></td></c:otherwise>
							</c:choose>

							<c:choose>
							<c:when test="${bean.isMajor==1}"><td><div class="overflow_hidden w40" title="是">是</div></td></c:when>
							<c:when test="${bean.status==6}"><td><div class="overflow_hidden w40" title=""></div></td></c:when>
							<c:otherwise><td><div class="overflow_hidden w60" title="否">否</div></td></c:otherwise>
							</c:choose>

							<td><div class="overflow_hidden w80" title="<fmt:formatDate value="${bean.nextFollowDate}" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${bean.nextFollowDate}" pattern="yyyy-MM-dd"/></div></td>
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
	<div class="com-bot">${item.page.pageStr}</div>
	<div class='bomb-btn' style="margin-bottom:0;">
		<label class='bomb-btn-cen'>
			<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>确定</label></a>
			<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
		</label>
	</div>
</div>
</form>
</body>
</html>
