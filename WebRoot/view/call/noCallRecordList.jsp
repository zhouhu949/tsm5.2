<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>通信管理-未接记录</title>

<script type="text/javascript">
$(function(){
    $('.icon-email').click(function(){
		showIframe('alert_nocall_record_e','alert_nocall_record_e.html','发送短信',900,530);
	});
});
</script>
<script type="text/javascript">
$(function(){
	// 最近来电时间
    $('#d1').dateRangePicker({
    	showShortcuts:false,
        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
    	if(obj.date1 != 'Invalid Date'){
    		$('#startDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#endDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    	}
    	var s = $(this).parents('.select');
    	var dt=s.children("dt");
        var dd=s.children("dd");
        dt.html("自定义");
        $("#dDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
        //s.css("z-index",z);
 });

    /*所有者*/
	var url ="${ctx}/orgGroup/get_group_user_json.do";
	$("#tt1").tree({
		url:url,
		checkbox:true,
		onLoadSuccess:function(node,data){
			var $accs = $('#accs').val();
			if($accs != null && $accs.length > 0){
				$($accs.split(',')).each(function(index,data){
					var node = $("#tt1").tree("find",data);
					if(node != null){
						$("#tt1").tree("check",node.target);
					}
				});
			}
		}
	});

	// 选择点保存
	$("#checkedId").click(function() {
		var nodes =$("#tt1").tree("getChecked");
		var accs = "";
		$(nodes).each(function(index,obj){
			if(obj.attributes.type == 'M'){
				accs+=","+obj.id;
			}
		});
		if(accs.length > 0){
			accs=accs.substring(1,ids.length);
			$("#accs").val(accs);
		}
		$("#manage_drop").hide();
	});
	//清空
	$('#clearId').click(function(){
		var nodes = $('#tt1').tree('getChecked');
		 $(nodes).each(function(index,obj){
        	$('#tt1').tree('uncheck', obj.target);
        });
        $('#accs').val('');
		 $("#manage_drop").hide();
	});
	// 显示所有者
	$("#accs").click(function(){
		$("#manage_drop").toggle();
	});
});
var setNdate = function(i){
	$('#startDate').val('');
	$('#endDate').val('');
	$("#dDateType").val(i);
};
</script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
</head>
<body>
<form action="${ctx}/callrecord/noCall/list.do" method="post">
<div class="hyx-ctr" >
   <div class="com-search hyx-cfu-search">
      <div class="com-search-box fl_l">
	   <p class="search clearfix"><input class="input-query fl_l" type="text" value="${queryText }" name="queryText"/><label class="hide-span">输入客户名称/来电号码</label></p>
	   <div class="list-box">
	   		<input type="hidden" id="status" name="status" value="${item.status}">
				<c:set var="statusName" value="处理状态"/>
				<c:choose>
					<c:when test="${item.status eq 0 }"><c:set var="statusName" value="未处理"/></c:when>
					<c:when test="${item.status eq 1 }"><c:set var="statusName" value="处理"/></c:when>
				</c:choose>
		   <dl class="select">
				<dt>${statusName }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="$('#reason').val('');">处理状态</a></li>
						<li><a href="###" onclick="$('#reason').val('');">全部</a></li>
						<li><a href="###" onclick="$('#reason').val('1');">已处理</a></li>
						<li><a href="###" onclick="$('#reason').val('0');">未处理</a></li>
					</ul>
				</dd>
		   </dl>
		      <!--最近来电时间  -->
			  <input type="hidden" name="startDate" id="startDate" value="${item.startDate }" />
			   <input type="hidden" name="endDate" id="endDate" value="${item.endDate }"/>
			   <input type="hidden" name="dDateType" id="dDateType" value="${dDateType }" />
			   <c:set var="dDateName" value="通话日期" />
			   <c:choose>
			   		<c:when test="${dDateType eq 1 }"><c:set var="dDateName" value="当天" /></c:when>
			   		<c:when test="${dDateType eq 2 }"><c:set var="dDateName" value="本周" /></c:when>
			   		<c:when test="${dDateType eq 3 }"><c:set var="dDateName" value="本月" /></c:when>
			   		<c:when test="${dDateType eq 4 }"><c:set var="dDateName" value="半年" /></c:when>
			   		<c:when test="${dDateType eq 5 }"><c:set var="dDateName" value="自定义" /></c:when>
			   </c:choose>
		    <dl class="select">
				<dt>${dDateName }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="setNdate(0)">最近来电时间</a></li>
						<li><a href="###" onclick="setNdate(1)">当天</a></li>
						<li><a href="###" onclick="setNdate(2)">本周</a></li>
						<li><a href="###" onclick="setNdate(3)">本月</a></li>
						<li><a href="###" onclick="setNdate(4)">半年</a></li>
						<li><a href="###" onclick="setNdate(5)" class="diy date-range" id="d1">自定义</a></li>
					</ul>
				</dd>
			</dl>
			<div class="reso-sub-dep fl_l">
					<input class="owner-sour"  name="accs" id="accs" type="text" value="${item.accs}">
					<div class="manage-owner-sour" id="manage_drop" style="display:none;">
						<div class="sub-dep-ul">
								<ul id="tt1" >

						        </ul>
			    		</div>
					    <div class="sure-cancle clearfix" style="width:120px">
							<a id="checkedId"  class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" href="javascript:;"><label>确定</label></a>
							<a class="com-btnd bomp-btna fl_l" id="clearId" href="javascript:;"><label>清空</label></a>
						</div>
					</div>
					</div>
		</div>
	</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
   </div>
	<div class="com-table com-mta hyx-cla-table fl_l" style="display:block;margin-left:15px;">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
							<th><span class="sp sty-borcolor-b">操作</span></th>
							<th><span class="sp sty-borcolor-b">客户名称</span></th>
							<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>来电号码</span></label></span></th>
							<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>来电归属地</span></label></span></th>
							<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>最近来电时间</span></label></span></th>
							<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>未接来电次数</span></label></span></th>
							<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>被呼成员</span></label></span></th>
							<th>处理状态</th>
						</tr>
					</thead>
					<tbody>
					<c:choose>
						<c:when test="${not empty list }">
							<c:forEach items="${list }" var="call" varStatus="vs">
								<tr class=" ${vs.count%2==0?'sty-bgcolor-b':''}">
									<td style="width:60px;"><div class="overflow_hidden w60 link"><a href="javascript:;" class="icon-phone" title="电话"></a><a href="javascript:;" class="icon-email" title="邮件"></a></div></td>
									<td><div class="overflow_hidden w120" title="${call.name}">${call.name}</div></td>
									<td><div class="overflow_hidden w120" title="${call.phone}">${call.phone}</div></td>
									<td><div class="overflow_hidden w120" title="${call.region}">${call.region}</div></td>
									<td><div class="overflow_hidden w120" title="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${call.inputDate}"/>"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${call.inputDate}"/></div></td>
									<td><div class="overflow_hidden w120" >${call.missNum}</div></td>
									<td><div class="overflow_hidden w80" title="${call.inputAcc }">${call.inputAcc }</div></td>
									<td><div class="overflow_hidden w80" >
											<c:choose>
												<c:when test="${call.status eq 0 }">未处理</c:when>
												<c:when test="${call.status eq 1 }">已处理</c:when>
											</c:choose>
									</div></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
								<tr>
									<td colspan="8" style="text-align: center;">
	                                		<div class="overflow_hidden w120" title="当前列表无数据">当前列表无数据！</div>
	                            	</td>
                            	</tr>
						</c:otherwise>
					</c:choose>
			</tbody>
		</table>
	</div>
	<div class="com-bot clearfix" >
		<div class="com-page fl_r">
				${item.page.pageStr}
		</div>
	</div>
</div>
</form>
</body>
</html>
