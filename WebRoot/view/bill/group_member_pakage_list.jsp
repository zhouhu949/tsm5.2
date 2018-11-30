<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>通信管理-通信时长分配管理</title>
    <style type="text/css"></style>
    <%@ include file="/common/include.jsp" %>
</head>
<body>
	<form id="cform" class="clearfix"  action="${ctx }/pakage/allocation/groupMemberPakageList" method="post">
		<input type="hidden" name="flag" value="1" />
		<div class="hyx-ctr clearfix" >
		   <div class="com-search hyx-cfu-search">
		      <div class="com-search-box fl_l">
			   <p class="search clearfix"><input class="input-query fl_l" type="text" name="queryText" value="${orgMemberDto.queryText }" placeholder="帐号/用户姓名"/></p>
			   <div class="list-box">
				    <c:set var="dDateName" value="到期时间" />
				    <c:choose>
				   		<c:when test="${orgMemberDto.dDateType eq 1 }"><c:set var="dDateName" value="当天" /></c:when>
				   		<c:when test="${orgMemberDto.dDateType eq 2 }"><c:set var="dDateName" value="本周" /></c:when>
				   		<c:when test="${orgMemberDto.dDateType eq 3 }"><c:set var="dDateName" value="本月" /></c:when>
				   		<c:when test="${orgMemberDto.dDateType eq 4 }"><c:set var="dDateName" value="半年" /></c:when>
				   		<c:when test="${orgMemberDto.dDateType eq 5 }">
				   			<fmt:parseDate var="sd" value="${orgMemberDto.startDate }" pattern="yyyy-MM-dd" />
					   		<fmt:parseDate var="ed" value="${orgMemberDto.endDate }" pattern="yyyy-MM-dd" />
				   			<c:set var="dDateName">
				   				<fmt:formatDate value="${sd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ed }" pattern="yy.MM.dd"/>
				   			</c:set>
				   		</c:when>
				    </c:choose>
				    <input type="hidden" name="startDate" id="s_startDate" value="${orgMemberDto.startDate }" />
				    <input type="hidden" name="endDate" id="s_endDate" value="${orgMemberDto.endDate }" />
				    <input type="hidden" name="dDateType" id="dDateType" value="${orgMemberDto.dDateType }" />
				    <dl class="select prec-cont-time">
						<dt>${dDateName }</dt>
						<dd>
							<ul>
								<li><a href="javascript:;" onclick="setDdate(0)" title="到期时间">到期时间</a></li>
								<li><a href="javascript:;" onclick="setDdate(1)" title="当天">当天</a></li>
								<li><a href="javascript:;" onclick="setDdate(2)" title="本周">本周</a></li>
								<li><a href="javascript:;" onclick="setDdate(3)" title="本月">本月</a></li>
								<li><a href="javascript:;" onclick="setDdate(4)" title="半年">半年</a></li>
								<li><a href="javascript:;" title="自定义" class="diy date-range" id="dDate">自定义</a></li>
							</ul>
						</dd>
					</dl>
				   <input type="hidden" name="orderState" id="orderState" value="${orgMemberDto.orderState }" />
				   <c:set var="osName" value="开通情况" />
				   <c:choose>
				   		<c:when test="${orgMemberDto.orderState eq '1' }"><c:set var="osName" value="已开通" /></c:when>
				   		<c:when test="${orgMemberDto.orderState eq '2' }"><c:set var="osName" value="已过期" /></c:when>
				   </c:choose>
				   <dl class="select prec-cont-time">
						<dt>${osName }</dt>
						<dd>
							<ul>
								<li><a href="javascript:;" onclick="$('#orderState').val('')" title="开通情况">开通情况</a></li>
								<li><a href="javascript:;" onclick="$('#orderState').val('1')" title="已开通">已开通</a></li>
								<li><a href="javascript:;" onclick="$('#orderState').val('2')" title="已过期">已过期</a></li>
							</ul>
						</dd>
				   </dl>
				   <input type="hidden" name="groupIdStr" value="${orgMemberDto.groupIdStr }" id="groupIdStr" />
					<div class="reso-sub-dep fl_l">
						<input id="groupNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="所在部门">
						<div class="manage-drop">
							<div class="sub-dep-ul">
								<ul id="tt2">

				       			</ul>
				    		</div>
						    <div class="sure-cancle clearfix" style="width:120px">
								<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" onclick="seleGroupTree()" href="javascript:;"><label>确定</label></a>
								<a class="com-btnd bomp-btna reso-sub-clear fl_l" id="close02" onclick="unCheckGroupTree()" href="javascript:;"><label>清空</label></a>
							</div>
						</div>
					</div>
				   <p class='tag fl_l' style="padding:5px 0px 0 20px;"><label>总通信币小于：</label><input type="text" name="num" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"
				    value="${orgMemberDto.num }" style="height:20px;width:60px;"></p>
				</div>
			  </div>
				<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
		   </div>
		   <div class="com-btnlist hyx-cm-btnlist fl_l" >
					<a class="com-btna demoBtn_a fl_l" id="recharge" href="javascript:;"><i class="min-reass"></i><label class="lab-mar">通信币分配</label></a>
					<a class="com-btna demoBtn_b fl_l" id="recover" href="javascript:;"><i class="min-back"></i><label class="lab-mar">通信币回收</label></a>
			</div>
			<p class="com-moTag skin-minimal com-mta fl_l" style="width: 100%;"  ><label class="f">当前剩余通信币：<span class="font-color">${totalTimes }</span><span class="product-currencyUnit">蜂豆</span>（1元兑换100<span class="product-currencyUnit">蜂豆</span>）</label></p>
			<div class="com-table com-mta hyx-cla-table fl_l" style="display:block;margin-top:5px !important;">
				<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
					<thead>
						<tr role="head" class="sty-bgcolor-b">
							<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
							<th><span class="sp sty-borcolor-b">帐号</span></th>
							<th><span class="sp sty-borcolor-b">用户姓名</span></th>
							<th><span class="sp sty-borcolor-b" sort="true">剩余通信币（<span class="product-currencyUnit">蜂豆</span>）</span></th>
							<!-- <th><span class="sp sty-borcolor-b" sort="true">短信剩余（条）</span></th> -->
							<th><span class="sp sty-borcolor-b" sort="true">到期时间</span></th>
							<th>服务开通状态</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${!empty memberDtos }">
								<c:forEach items="${memberDtos }" var="member" varStatus="vs">
									<tr class="${vs.count%2 eq 0 ? 'sty-bgcolor-b' : '' }">
										<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input name="check_" id="chk_${member.userId }" account="${member.userAccount }" commuTimes="${member.communicationsTimes}" smsNumber="${member.messagesNumber }" type="checkbox"  /></div></td>
										<td><div class="overflow_hidden w120" title="${member.userAccount }">${member.userAccount }</div></td>
										<td><div class="overflow_hidden w70" title="${member.userName }">${member.userName }</div></td>
										<td><div class="overflow_hidden w120" title="${member.communicationsTimes }">${member.communicationsTimes }</div></td>
										<%-- <td><div class="overflow_hidden w120" title="${member.messagesNumber }">${member.messagesNumber }</div></td> --%>
										<td><div class="overflow_hidden w100" title="<fmt:formatDate value="${member.serveTime }" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${member.serveTime }" pattern="yyyy-MM-dd"/></div></td>
										<td>
											<div class="overflow_hidden w120">
												<c:choose>
													<c:when test="${member.orderState eq '0' }">未开通</c:when>
													<c:when test="${member.orderState eq '1' }">已开通</c:when>
													<c:when test="${member.orderState eq '2' }">已过期</c:when>
												</c:choose>
											</div>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="8">当前列表无数据！</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<div class="com-bot" >
				${orgMemberDto.page.pageStr }
			</div>
			<p class="reminder" style="font-size:14px;"><span style="color:red;">温馨提示：</span>通信币是产品内通信功能的标准计量货币，以<span class="product-currencyUnit">蜂豆</span>为单位</p>
			<p class="reminder" style="font-size:14px;margin-left:70px;">用户使用通信功能将消耗其所拥有的通信币</p>
		</div>
	</form>

<script type="text/x-handlebars-template" id="template">
  {{#each list}}
	<tr class="{{even_odd @index}}">

		<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input name="check_" id="chk_{{userId }}" account="{{userAccount }}" commuTimes="{{communicationsTimes}}" smsNumber="{{messagesNumber }}" type="checkbox"  /></div></td>
		<td><div class="overflow_hidden w120" title="{{userAccount }}">{{userAccount }}</div></td>
		<td><div class="overflow_hidden w70" title="{{userName }}">{{userName }}</div></td>
		<td><div class="overflow_hidden w120" title="{{communicationsTimes }}">{{communicationsTimes }}</div></td>
		<td><div class="overflow_hidden w120" title="{{messagesNumber }}">{{messagesNumber }}</div></td>
		<td><div class="overflow_hidden w100" title="{{formatDate serveTime 'YYYY-MM-DD'}}">{{formatDate serveTime 'YYYY-MM-DD'}}</div></td>
		<td>
			<div class="overflow_hidden w120">
				{{orderStateStr orderState}}
			</div>
		</td>
    </tr>
 {{/each}}
</script>
	<script type="text/javascript" src="${ctx }/static/js/view/bill/group_member_pakage_list.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
</body>
</html>
