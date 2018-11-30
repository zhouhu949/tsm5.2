<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
	<%@ include file="/common/include.jsp"%>
	<title>系统设置-挂机短信规则设置</title>
	<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
	<script type="text/javascript">
	$(function(){
		// 挂机短信规则设置
		$("#a").click(function(){
			document.location.href = "${ctx }/sys/hookSms/hookRulePage";
		});

		// 挂机短信模板设置
		$("#b").click(function(){
			document.location.href = "${ctx }/sys/hookSms/hookTempPage";
		});



		$('.hyx-hsrs-b-add').click(function(){
			pubDivShowIframe('alert_hook_sms_rules_set_b_a',ctx+'/sys/hookSms/toAddOrEditTemp','新增挂机短信',915,510);
		});


		//创建时间
		 $('#date-range1').dateRangePicker({
		 		showShortcuts:false,
		        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
		    }).bind('datepicker-apply',function(event,obj){
		    		var s_t = '';
    				var e_t = '';
		    	if(obj.date1 != 'Invalid Date'){
		    		$('#d_startDate').val(moment(obj.date1).format('YYYY-MM-DD'));
		    		s_t = moment(obj.date1).format('YY.MM.DD');
		    	}
		    	if(obj.date2 != 'Invalid Date'){
		    		$('#d_endDate').val(moment(obj.date2).format('YYYY-MM-DD'));
		    		e_t = moment(obj.date2).format('YY.MM.DD');
		    	}
		    	var s = $(this).parents('.select');
		    	var dt=s.children("dt");
		        var dd=s.children("dd");

        		dt.html(s_t+'/'+e_t);
		        $("#dDateType").val(5);
		        dd.slideUp(200);
		        dt.removeClass("cur");
		        //s.css("z-index",z);
		 });
	});
	function update_(id){
		pubDivShowIframe('alert_hook_sms_rules_set_b_a',ctx+'/sys/hookSms/toAddOrEditTemp?id='+id+'&v='+new Date().getTime(),'修改挂机短信模板',915,510);
	}

	/** 启用事件 enable 1:已经启用的，0：还未启用的*/
	function enable_(enable,id){
		var enable1 = enable;
		if(enable == 1){ // 已经启用的，点击按钮后设置为不启用
			enable =0;
		}else{
			enable = 1;
		}
		var url = "${ctx }/sys/hookSms/setEnable";
		$.ajax({
			url: url,
			type:'POST',
			data:{'enable':enable,'id':id},
			dataType:'json',
			error:function(){alert("网络异常，请稍后再操作！")},
			success:function(data){
				if(data == 0){
					if(enable1 == 1){
						window.top.iDialogMsg("提示","停用成功！");
					}else{
						window.top.iDialogMsg("提示","启用成功！");
					}
					$("#tempForm").submit();
				}
			}
		});
	}

	var setDdate = function(i){
		$('#d_startDate').val('');
		$('#d_endDate').val('');
		$("#dDateType").val(i);
	}

	</script>
</head>
<body>
	<div class="year-sale-play hyx-hsrs-top">
		<ul class="ann-sale-plan clearfix">
			<li class="li_first" id="a" >挂机短信规则设置</li>
			<li class="select  li_last" id="b">挂机短信模板设置</li>
		</ul>
	</div>
	<form id="tempForm"  action="${ctx }/sys/hookSms/hookTempPage" method="post" >
	<div class="hyx-hsrs-bot">
		<div class="hyx-cfu-left hyx-ctr hyx-hsrs-b">
   <div class="com-search hyx-cfu-search">
      <div class="com-search-box fl_l">
	   <p class="search clearfix"><input class="input-query fl_l" type="text" name="queryText" value="${item.queryText}" /><label class="hide-span">短信内容</label></p>
	   <div class="list-box">
	   		<!-- 呼叫类型 -->
			<input type="hidden" name="status" id="d_status" value="${item.status }" />
			<c:set var="statusName" value="呼叫类型" />
			<c:choose>
				<c:when test="${item.status eq '0'}"><c:set var="statusName" value="呼入" /></c:when>
				<c:when test="${item.status eq '1'}"><c:set var="statusName" value="呼出" /></c:when>
			</c:choose>
		   <dl class="select">
				<dt>${statusName}</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="$('#d_status').val('')">呼叫类型</a></li>
						<li><a href="###" onclick="$('#d_status').val('0')">呼入</a></li>
						<li><a href="###" onclick="$('#d_status').val('1')">呼出</a></li>
					</ul>
				</dd>
		   </dl>
		   <!-- 发送条件 -->
			<input type="hidden" name="sendCondi" id="d_sendCondi" value="${item.sendCondi }" />
			<c:set var="condiName" value="发送条件" />
			<c:choose>
				<c:when test="${item.sendCondi eq '1'}"><c:set var="condiName" value="接通" /></c:when>
				<c:when test="${item.sendCondi eq '0'}"><c:set var="condiName" value="未接通" /></c:when>
			</c:choose>
		   <dl class="select">
				<dt>${condiName}</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="$('#d_sendCondi').val('')">发送条件</a></li>
						<li><a href="###" onclick="$('#d_sendCondi').val('1')">接通</a></li>
						<li><a href="###" onclick="$('#d_sendCondi').val('0')">未接通</a></li>
					</ul>
				</dd>
		   </dl>
		   <!--状态 -->
			<input type="hidden" name="enable" id="d_enable" value="${item.enable }" />
			<c:set var="enableName" value="状态" />
			<c:choose>
				<c:when test="${item.enable eq 0}"><c:set var="enableName" value="不启用" /></c:when>
				<c:when test="${item.enable eq 1}"><c:set var="enableName" value="启用" /></c:when>
			</c:choose>
		   <dl class="select">
				<dt>${enableName}</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="$('#d_enable').val('')">状态</a></li>
						<li><a href="###" onclick="$('#d_enable').val(1)">启用</a></li>
						<li><a href="###" onclick="$('#d_enable').val(0)">不启用</a></li>
					</ul>
				</dd>
		   </dl>
		    <!--创建时间 -->
		   <input type="hidden" name="startDate" id="d_startDate" value="${item.startDate }" />
		   <input type="hidden" name="endDate" id="d_endDate" value="${item.endDate }"/>
		   <input type="hidden" name="dDateType" id="dDateType" value="${item.dDateType }" />
		   <c:set var="dDateName" value="创建时间" />
		    <c:choose>
		   		<c:when test="${item.dDateType eq 1 }"><c:set var="dDateName" value="当天" /></c:when>
		   		<c:when test="${item.dDateType eq 2 }"><c:set var="dDateName" value="本周" /></c:when>
		   		<c:when test="${item.dDateType eq 3 }"><c:set var="dDateName" value="本月" /></c:when>
		   		<c:when test="${item.dDateType eq 4 }"><c:set var="dDateName" value="半年" /></c:when>
		   		<c:when test="${item.dDateType eq 5 }">
		   				<fmt:parseDate var="nsd" value="${item.startDate }" pattern="yyyy-MM-dd" />
						<fmt:parseDate var="ned" value="${item.endDate }" pattern="yyyy-MM-dd" />
			   			<c:set var="dDateName">
			   				<fmt:formatDate value="${nsd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ned }" pattern="yy.MM.dd"/>
			   			</c:set>
			   	</c:when>
		   </c:choose>
		    <dl class="select">
				<dt>${dDateName}</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="setDdate(0)">创建时间</a></li>
						<li><a href="###" onclick="setDdate(1)">当天</a></li>
						<li><a href="###"  onclick="setDdate(2)">本周</a></li>
						<li><a href="###"  onclick="setDdate(3)">本月</a></li>
						<li><a href="###"  onclick="setDdate(4)">半年</a></li>
						<li><a href="###"  onclick="setDdate(5)" class="diy date-range" id="date-range1">自定义</a></li>
					</ul>
				</dd>
			</dl>
		</div>
	</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query" />
   </div>
   <div class="com-btnlist">
		<a href="javascript:;" class="com-btna hyx-hsrs-b-add"><i class="min-new-add"></i><label class="lab-mar">新&nbsp;&nbsp;&nbsp;&nbsp;增</label></a>
	</div>
	<div class="com-table com-mta hyx-cla-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">操作</span></th>
					<th><span class="sp sty-borcolor-b">呼叫类型</span></th>
					<th><span class="sp sty-borcolor-b">发送条件</span></th>
					<th><span class="sp sty-borcolor-b">发送对象</span></th>
					<th><span class="sp sty-borcolor-b">短信内容</span></th>
					<th>创建时间</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty list }">
						<c:forEach items="${list }" var="list" varStatus="v">
							<tr class="${v.count%2==0?'sty-bgcolor-b':''}">
								<td style="width:80px;"><div class="overflow_hidden w80 link skin-minimal"><a href="javascript:;" class="icon-edit ${list.enable ne 1 ?'':'img-gray'}" <c:if test="${list.enable ne 1}">onclick="update_('${list.id}')" title="修改"</c:if>></a><a href="javascript:;" class="${list.enable eq 1?'icon-enable':'icon-disable' }" title="${list.enable eq 1?'不启用':'启用' }" onclick="enable_('${list.enable}','${list.id}')"></a></div></td>
								<td><div class="overflow_hidden w70" title="${list.stateName }">${list.stateName}</div></td>
								<td><div class="overflow_hidden w70" title="${list.sendCondiName}">${list.sendCondiName}</div></td>
								<td><div class="overflow_hidden w100" title="${list.sendTypeName}">${list.sendTypeName}</div></td>
								<td><div class="overflow_hidden w150" title="${list.content}${smslabel}">${list.content}${smslabel}</div></td>
								<td><div class="overflow_hidden w100" title="<fmt:formatDate value="${list.inputTime}" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${list.inputTime}" pattern="yyyy-MM-dd"/></div></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
							<tr class="no_data_tr"><td style="text-align:center;"><b>当前列表无数据！</b></td></tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	<div class="com-bot" >
		<div class="com-page fl_r">
			${item.page.pageStr}
		</div>
	</div>
</div>
	</div>
	</form>
</body>
</html>
