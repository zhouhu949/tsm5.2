<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html >
<head>
    <meta charset="UTF-8">
    <title>管理者首页</title>
    <%@ include file="/common/include-home-cut-version.jsp" %>
</head>
<body>
<!-- 左边 -->
<%-- <input id="callCountUrl" type="hidden" value="${callCountUrl }" /> --%>
<div class="hyx-layout">
<div class="hyx-index-left hyx-layout-side">
	<div class="home-index-box">
	<div class="index-manage-top clearfix">
		<img class="manag-top-left fl_l click-into-personal-center" src="${empty searchUser.imgUrl ? ctx : ''}${empty searchUser.imgUrl ? '/static/images/header.png' : searchUser.imgUrl}" title="进入个人中心"/>
		<div class="manag-top-right fl_l  pare-inherit-width">
			<a href="javascript:;"  title="钱包" class="enter-hyx-wallet"></a>
			<h2 class="overflow_hidden" title="${searchUser.userName }">
				${searchUser.userName }
			</h2>
			<p><span title="${searchUser.userAccount }">${searchUser.userAccount }</span></p>
<%-- 			<c:if test="${!empty bindEmail }"> --%>
<!-- 				<p> -->
<!-- 					<span> -->
<%-- 						<a href="javascript:;" title="邮箱变更" onclick="pubDivShowIframe('change_email','${ctx}/email/config/toUnBind','邮箱变更',345,210)">${bindEmail }</a> --%>
<!-- 					</span> -->
<!-- 				</p> -->
<%-- 			</c:if> --%>
		</div>
	</div>
	<div class="index-manage-bottom clearfix">
		<div class="mana-bott-midd fl_l" id="beans">
			<p><span id="home-phone" class="home-mesage product-currencyUnit-title" data-title="兑换规则：1元兑换100" title="兑换规则：1元兑换100蜂豆"></span></p>
			<p><span id="home-minute" class="home-minute">0<span class="product-currencyUnit">蜂豆</span></span></p>
		</div>
		<div class="mana-bott-right fl_l">
			<p><span class="home-clock" title="坐席到期时间：<fmt:formatDate value="${searchUser.serveTime }" pattern="yyyy-MM-dd" />，剩余${dayCount }天"></span></p>
			<p><span class="home-days">${dayCount }天</span></p>
		</div>
	</div>
	</div>
	<div class="home-mess-center">
		<div class="mess-center-title">
		<span>消息中心</span>
		</div>
		<div class="mess-center-icon">
			<div class="mess-icon-first clearfix">
				<div rel="${ctx }/message/list?type=5" class="icon-first-left fl_l message-btn">
					<p class="first-left-p">
						<span class="noti-anno-bg noti-anno-max"></span>
						<c:if test="${!empty message && message.noticeNum gt 0 }"><span class="noti-anno-minbg">${message.noticeNum }</span></c:if>
					</p>
					<p>
						<span class="noti-anno-title">通知公告</span>
					</p>
				</div>
				<div rel="${ctx }/message/list?type=3" class="icon-first-midd fl_l message-btn">
					<p class="first-midd-p">
						<span class="noti-anno-bg missed-calls-max"></span>
						 <c:if test="${!empty message && message.callNum gt 0 }"><span class="noti-anno-minbg">${message.callNum }</span></c:if>
					</p>
					<p>
						<span class="missed-calls-title">未接来电</span>
					</p>
				</div>
				<div rel="${ctx }/message/list?type=4" class="icon-first-right fl_l message-btn">
					<p class="first-right-p">
						<span class="noti-anno-bg expir-remin-max"></span>
						 <c:if test="${!empty message && message.dateNum gt 0 }"><span class="noti-anno-minbg">${message.dateNum }</span></c:if>
					</p>
					<p>
						<span class="expir-remin-title">到期提醒</span>
					</p>
				</div>
			</div>
			<div class="mess-icon-second clearfix">
				<div rel="${ctx }/message/list?type=2" class="icon-second-left fl_l message-btn">
					<p class="second-left-p">
						<span class="noti-anno-bg comm-infor-max"></span>
						<c:if test="${!empty message && message.bbsNum gt 0 }"><span class="noti-anno-minbg">${message.bbsNum }</span></c:if>
					</p>
					<p>
						<span class="comm-infor-title">点评信息</span>
					</p>
				</div>

				<div rel="${ctx }/message/list?type=1" class="icon-second-right fl_l message-btn">
					<p class="second-right-p">
						<span class="noti-anno-bg cust-cont-max"></span>
						 <c:if test="${!empty message && message.custNum gt 0 }"><span class="noti-anno-minbg">${message.custNum }</span></c:if>
					</p>
					<p>
						<span class="cust-cont-title">客户联系</span>
					</p>
				</div>
				<div rel="${ctx }/message/list?type=1" class="icon-second-midd fl_l message-btn">
					<p class="second-midd-p">
						<span class="noti-anno-bg noti-more-infor"></span>
						<c:if test="${!empty message && message.authNum gt 0 }"><span class="noti-anno-minbg">${message.authNum }</span></c:if>
					</p>
					<p>
						<span class="matur-extr-title">更多消息</span>
					</p>
			  </div>
			</div>
		</div>
	</div>
	<div class="home-mess-center">
		<div class="mess-center-title clearfix">
		<span class="fl_l">快捷功能</span>
		<span id="quickMenuSetBtn" class="shor-func fl_r"></span>
		</div>
		<div class="mess-center-icon">
			<div class="mess-icon-first clearfix">
				<c:forEach items="${quickMenu }" var="qm" varStatus="vs">
					<c:if test="${vs.count le 3 }">
						<c:choose>
							<c:when test="${vs.count % 3 eq 1 }">
								<div onclick="window.top.addTab('${qm.resourceString }','${qm.resourceName}')" class="icon-first-left fl_l">
									<p class="first-left-p">
										<span class="noti-anno-bg ${qm.icon }"></span>
									</p>
									<p>
										<span>${qm.resourceName }</span>
									</p>
								</div>
							</c:when>
							<c:when test="${vs.count % 3 eq 2 }">
								<div onclick="window.top.addTab('${qm.resourceString }','${qm.resourceName}')" class="icon-first-midd fl_l">
									<p class="first-midd-p">
										<span class="noti-anno-bg ${qm.icon }"></span>
									</p>
									<p>
										<span>${qm.resourceName }</span>
									</p>
								</div>
							</c:when>
							<c:otherwise>
								<div onclick="window.top.addTab('${qm.resourceString }','${qm.resourceName}')" class="icon-first-right fl_l">
									<p class="first-right-p">
										<span class="noti-anno-bg ${qm.icon }"></span>
									</p>
									<p>
										<span>${qm.resourceName }</span>
									</p>
								</div>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
			</div>
			<div class="mess-icon-second clearfix">
				<c:forEach items="${quickMenu }" var="qm" varStatus="vs">
					<c:if test="${vs.count gt 3 }">
						<c:choose>
							<c:when test="${vs.count % 3 eq 1 }">
								<div onclick="window.top.addTab('${qm.resourceString }','${qm.resourceName}')" class="icon-second-left fl_l">
									<p class="second-left-p">
										<span class="noti-anno-bg ${qm.icon }"></span>
									</p>
									<p>
										<span>${qm.resourceName }</span>
									</p>
								</div>
							</c:when>
							<c:when test="${vs.count % 3 eq 2 }">
								<div onclick="window.top.addTab('${qm.resourceString }','${qm.resourceName}')" class="icon-second-midd fl_l">
									<p class="second-midd-p">
										<span class="noti-anno-bg ${qm.icon }"></span>
									</p>
									<p>
										<span>${qm.resourceName }</span>
									</p>
								</div>
							</c:when>
							<c:otherwise>
								<div onclick="window.top.addTab('${qm.resourceString }','${qm.resourceName}')" class="icon-second-right fl_l">
									<p class="second-right-p">
										<span class="noti-anno-bg ${qm.icon }"></span>
									</p>
									<p>
										<span>${qm.resourceName }</span>
									</p>
								</div>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</div>
	<div class="home-mess-center">
		<div class="mess-center-title">
		<span>联系我们</span>
		</div>
		<div class="mess-center-icon"  style="padding-bottom:0;">
			<div class="mess-icon-first clearfix" style="margin-bottom:0;">
				<div class="icon-first-left fl_l service_qq product-qq-title" data-title="服务QQ：" title="服务QQ：4008262277"><!-- $('#QQOnLine iframe').contents().find('#launchBtn').click(); -->
					<p class="first-left-p">
						<img style="width:25px;height:25px;" src="${ctx}/static/images/home_qq.png"/>
					</p>
					<p>
						<span>服务QQ</span>
					</p>
				</div>
				<div class="icon-first-midd fl_l product-serviceTel-title" onclick="call('4008262277')" data-title="客服热线：" title="客服热线：4008262277">
					<p class="first-midd-p">
						<img style="width:25px;height:25px;" src="${ctx}/static/images/home_tel.png"/>
					</p>
					<p>
						<span>客服热线</span>
					</p>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 右边 -->
<div class="hyx-home-right hyx-layout-content">
	<div class="home-right-top clearfix">
		<div class="right-top-left w50p">
			<div class="plan-the-month"><span>本月计划</span></div>
			<div class="home-manag-selec">
				<dl class="select">
					<dt>全部</dt>
					<dd style="display: none;">
						<ul>
							<li><a title="全部" onclick="groupPlan('');" href="javascript:;">全部</a></li>
							<c:forEach items="${teamGroups }" var="group">
								<li><a title="${group.groupName }" onclick="groupPlan('${group.groupId}');" href="javascript:;">${group.groupName }</a></li>
							</c:forEach>
						</ul>
					</dd>
				</dl>
			</div>
			<div class="home-manag-pie01">
				<div id="main01" class="home-manag-piech01"></div>
			</div>
			<div class="home-details">
<!-- 				<a href="">详情</a> -->
			</div>
		</div>
		<div class="right-top-right w50p">
			<div class="plan-the-month"><span>团队业绩</span></div>
			<input type="hidden" id="line_group_id" value="" />
			<div class="month-new-will skin-minimal">
				<span class="plan_radio">
					<input type="radio" value="1" name="right_radio" checked="checked">
					<span class="span_content">回款金额</span>
				</span>
				<span class="plan_radio">
					<input name="right_radio" value="2" type="radio">
					<span class="span_content">新增签约</span>
				</span>
				<span class="plan_radio">
					<input name="right_radio" value="3" type="radio">
					<span class="span_content">新增意向</span>
				</span>
			</div>
			<div class="home-manag-selec">
				<dl class="select">
					<dt>全部</dt>
					<dd style="display: none;">
						<ul>
							<li><a title="全部" onclick="lineGroupPlan('');" href="javascript:;">全部</a></li>
							<c:forEach items="${teamGroups }" var="group">
								<li><a title="${group.groupName }" onclick="lineGroupPlan('${group.groupId}');" href="javascript:;">${group.groupName }</a></li>
							</c:forEach>
						</ul>
					</dd>
				</dl>
			</div>
			<div class="home-manag-pie02">
				<div id="main02" class="home-manag-piech02"></div>
			</div>
			<div class="home-details">
<!-- 				<a href="">详情</a> -->
			</div>
		</div>
	</div>
	<div class="home-right-middle clearfix">
		<div class="plan-the-month"><span>今日关注</span></div>
			<div class="home-manag-selec">
				<dl class="select">
					<dt>全部</dt>
					<dd style="display: none;">
						<ul>
							<li><a title="全部" onclick="todayView('');" href="javascript:;">全部</a></li>
							<c:forEach items="${teamGroups }" var="group">
								<li><a title="${group.groupName }" onclick="todayView('${group.groupId}');" href="javascript:;">${group.groupName }</a></li>
							</c:forEach>
						</ul>
					</dd>
				</dl>
			</div>
		<div class="anal-deta-pies06">
			<span id="res_sign_num" class="trans-contr01">0个</span>
			<span id="res_no_num" class="trans-contr02">0个</span>
			<span id="res_call_num" class="trans-contr03">0分</span>
			<span id="res_pool_num" class="trans-contr04">0个</span>
			<span id="res_cust_num" class="trans-contr05">0个</span>
			<div class="home-resou-will06"><span class="center-resou">资源</span><span class="prac-conne">实际联系</span><span id="res_prac" class="prac-comm-number">0</span></div>
		</div>
		<div class="anal-deta-pies04">
		    <div id="main04" class="home-resou-will04"></div>
        </div>
        <div class="anal-deta-pies05">
            <div id="main05" class="home-resou-will05"></div>
        </div>
        <div class="anal-deta-pies07">
        	<span id="int_sign_num" class="will-trans-contr01">0个</span>
			<span id="int_no_num" class="will-trans-contr02">0个</span>
			<span id="int_call_num" class="will-trans-contr03">0分</span>
			<span id="int_pool_num" class="will-trans-contr04">0个</span>
			<span id="int_cust_num" class="will-trans-contr05">0个</span>
			<div class="home-resou-will07"><span class="will-center-resou">意向</span><span class="will-prac-conne">实际联系</span><span id="int_prac" class="will-prac-number">0</span></div>
		</div>
        <div class="home-details">
<!-- 				<a href="">详情</a> -->
			</div>
	</div>
<%-- 	<div class="home-right-bottom clearfix">
		<!-- <div class="plan-the-month"><span>明日计划</span></div> -->
<!-- 			<div class="home-manag-selec"> -->
<!-- 				<dl class="select"> -->
<!-- 					<dt>全部</dt> -->
<!-- 					<dd style="display: none;"> -->
<!-- 						<ul> -->
<!-- 							<li><a title="全部" href="javascript:;">全部</a></li> -->
							<c:forEach items="${teamGroups }" var="group">
								<li><a title="${group.groupName }" href="javascript:;">${group.groupName }</a></li>
							</c:forEach>
<!-- 						</ul> -->
<!-- 					</dd> -->
<!-- 				</dl> -->
<!-- 			</div> -->
			<!-- <div class="home-manag-pie03">
				<div id="main03" class="home-manag-piech03"></div>
			</div> -->
			<div class="home-details">
<!-- 				<a href="">详情</a> -->
			</div>
	</div> --%>
</div>
</div>
<!-- 在线客服 -->
<div id="QQOnLine" style="display: none;">
	<!-- <script type='text/javascript' src='http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODAwNjMxNF8xNjk2MTFfNDAwODI2MjI3N18'></script> -->
</div>
<script type="text/javascript" src="${ctx}/static/thirdparty/echarts-3.3.2/echarts.min.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/home/admin_home.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/home/admin_home_chart.js${_v}"></script>
<script>
	$(document).ready(function(){
		// ajaxLoading("正在加载页面，请稍后") ;
		// $(".datagrid-mask,.datagrid-mask-msg").ajaxStop(function(){
		// 	$(this).remove();
		// });

		$(".service_qq").on("click",function(){
			var qqOnline = document.getElementById("QQOnLine");
			var appendCode = document.createElement("script");
			appendCode.type = "text/javascript";
			appendCode.src = "http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODAwNjMxNF8xNjk2MTFfNDAwODI2MjI3N18";
			qqOnline.appendChild(appendCode);
			setTimeout(function(){
				$('iframe[width="92"]').hide();
				$('iframe[width="92"]').contents().find('#launchBtn').click();
			},400);
		});
	});
</script>
</body>
</html>
