<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html>
<head>
    <meta charset="UTF-8">
    <title>销售首页</title>
    <%@ include file="/common/home_include.jsp" %>
    <script type="text/javascript" src="${ctx}/static/thirdparty/echarts-3.3.2/echarts.min.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
   	<script type="text/javascript" src="${ctx}/static/js/view/home/sale_home.js${_v}"></script>
<body>
<!-- 左边 -->
<!-- 左边 -->
<%-- <input id="callCountUrl" type="hidden" value="${callCountUrl }" /> --%>
<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
<div class="hyx-layout">
	<div class="hyx-layout-side hyx-index-left">
		<div class="home-index-box">
			<div class="index-manage-top clearfix">
				<img class="manag-top-left fl_l click-into-personal-center" src="${empty searchUser.imgUrl ? ctx : ''}${empty searchUser.imgUrl ? '/static/images/header.png' : searchUser.imgUrl}" title="进入个人中心"/>
				<div class="manag-top-right fl_l  pare-inherit-width">
					<a href="javascript:;"  title="钱包" class="enter-hyx-wallet"></a>
					<h2 class="overflow_hidden" title="${searchUser.userName }">
						${searchUser.userName }
					</h2>
					<p><span title="${searchUser.userAccount }">${searchUser.userAccount }</span></p>
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
				<c:if test="${!empty monthPlan }">
						<div class="votebox">
							<div class="votebox-sign-cust clearfix">
								<label class="fl_l" for="">回款金额：</label>
								<span class="fl_l">${monthPlan.factMoney }/${monthPlan.planMoney }万元</span>
								<c:if test="${monthPlan.planMoneyState eq 1 }">
									<a class="vote-btn fl_r" pointType="signMoney">领取</a>
								</c:if>
							</div>
							<dl class="barbox">
								<dd class="barline">
									<span class="charts"></span>
								</dd>
							</dl>
							<span class="sp">
								<c:choose>
									<c:when test="${monthPlan.planMoneyState eq 1 }">
										奖励<label class="a">${dictionaryList[1].dictionaryValue}</label>积分
									</c:when>
									<c:when test="${monthPlan.planMoneyState eq 2 }">已领取</c:when>
								</c:choose>
							</span>
							<c:if test="${monthPlan.planMoneyState eq 1 }">
								<span class="read-lq">已领取</span>
							</c:if>
							<span class="reward-percent"><label class="reward-perc-numb" for="">0</label>%</span>
							<p class="p" style="display:none;"><fmt:formatNumber type="number" value="${monthPlan.factMoney/monthPlan.planMoney*100 }" pattern="0.0" maxFractionDigits="1" /></p>
							<!-- <p class="p">0</p> -->
						</div>
						<div class="votebox">
							<div class="votebox-sign-cust clearfix">
								<label class="fl_l" for="">签约客户：</label>
								<span class="fl_l">${monthPlan.factSigncustnum }/${monthPlan.planSigncustnum }个</span>
								<c:if test="${monthPlan.planSigncustnumState eq 1 }">
									<a class="vote-btn fl_r" pointType="signNum">领取</a>
								</c:if>
							</div>
							<dl class="barbox">
								<dd class="barline">
									<span class="charts"></span>
								</dd>
							</dl>
							<span class="sp">
								<c:choose>
									<c:when test="${monthPlan.planSigncustnumState eq 1 }">
										奖励<label class="a">${dictionaryList[1].dictionaryValue}</label>积分
									</c:when>
									<c:when test="${monthPlan.planSigncustnumState eq 2 }">已领取</c:when>
								</c:choose>
							</span>
							<c:if test="${monthPlan.planSigncustnumState eq 1 }">
								<span class="read-lq">已领取</span>
							</c:if>
							<span class="reward-percent"><label class="reward-perc-numb" for="">0</label>%</span>
							<p class="p" style="display:none;"><fmt:formatNumber type="number" value="${monthPlan.factSigncustnum/monthPlan.planSigncustnum*100 }" pattern="0.0" maxFractionDigits="1" /></p>
						</div>
						<div class="votebox">
							<div class="votebox-sign-cust clearfix">
								<label class="fl_l" for="">意向客户：</label>
								<span class="fl_l">${monthPlan.factWillcustnum }/${monthPlan.planWillcustnum }个</span>
								<c:if test="${monthPlan.planWillcustnumState eq 1 }">
									<a class="vote-btn fl_r" pointType="intNum">领取</a>
								</c:if>
							</div>
							<dl class="barbox">
								<dd class="barline">
									<span class="charts"></span>
								</dd>
							</dl>
							<span class="sp">
								<c:choose>
									<c:when test="${monthPlan.planWillcustnumState eq 1 }">
										奖励<label class="a">${dictionaryList[1].dictionaryValue}</label>积分
									</c:when>
									<c:when test="${monthPlan.planWillcustnumState eq 2 }">已领取</c:when>
								</c:choose>
							</span>
							<c:if test="${monthPlan.planWillcustnumState eq 1 }">
								<span class="read-lq">已领取</span>
							</c:if>
							<span class="reward-percent"><label class="reward-perc-numb" for="">0</label>%</span>
							<p class="p" style="display:none;"><fmt:formatNumber type="number" value="${monthPlan.factWillcustnum/monthPlan.planWillcustnum*100 }" pattern="0.0" maxFractionDigits="1" /></p>
						</div>
				</c:if>
			</div>
		<%-- <div class="home-index-box clearfix">
		<dl class="top-tit fl_l">
			<dt><img src="${empty searchUser.imgUrl ? ctx : ''}${empty searchUser.imgUrl ? '/static/images/header.png' : searchUser.imgUrl}" class="sale-home-iconimg"/></dt>
			<dd class="dd_icon"><span id="levelIcon" level="${curPoints.level }" class="divis-hover divis-icon0${curPoints.level }"></span><label id="levelName">${curPoints.levelName }</label></dd>
			<dd class="chart">
				<span id="levelProc" style="width:${curPoints.level eq 5 ? 100 : (userPoints-curPoints.startNumber)/(curPoints.endNumber-curPoints.startNumber)*100}%;" class="charts"></span>
				<label class="sale-hand" style="display:none;">${curPoints.endNumber-curPoints.startNumber }</label>
			</dd>
		</dl>
		<dl class="top-infor-right fl_l">
			<a href="javascript:window.top.hyxWallet();"  title="钱包" class="enter-hyx-wallet-sale"></a>
			<dt class="top-info-right-pay">
				<span class="top-infor-name" title="${searchUser.userName }">${searchUser.userName }</span>
			</dt>
			<dt class="top-info-right-pay">
				<span class="right-accounts" title="${searchUser.userAccount }">${searchUser.userAccount }</span>
			</dt>
			<dt>
				<span class="right-infor-phone">${searchUser.communicationNo }</span>
			</dt>
			<dt class="clearfix"><span id="infor-phone" class="infor-phone"></span><span id="phone-number" class="phone-number">0分钟</span></dt>
			<dt class="clearfix"><span class="infor-message" title="剩余短信${empty searchUser.messagesNumber ? '0' : searchUser.messagesNumber}条"></span><span class="message-number">${empty searchUser.messagesNumber ? '0' : searchUser.messagesNumber}条</span></dt>
			<dt class="clearfix"><span class="clock-time" title="坐席到期时间：<fmt:formatDate value="${searchUser.serveTime }" pattern="yyyy-MM-dd" />，剩余${dayCount }天"></span><span class="day-time">${dayCount }天</span></dt>
		</dl>
		<c:if test="${!empty monthPlan }">
			<div class="votebox">
				<div class="votebox-sign-cust clearfix">
					<label class="fl_l" for="">回款金额：</label>
					<span class="fl_l">${monthPlan.factMoney }/${monthPlan.planMoney }万元</span>
					<c:if test="${monthPlan.planMoneyState eq 1 }">
						<a class="vote-btn fl_r" pointType="signMoney">领取</a>
					</c:if>
				</div>
				<dl class="barbox">
					<dd class="barline">
						<span class="charts"></span>
					</dd>
				</dl>
				<span class="sp">
					<c:choose>
						<c:when test="${monthPlan.planMoneyState eq 1 }">
							奖励<label class="a">${dictionaryList[1].dictionaryValue}</label>积分
						</c:when>
						<c:when test="${monthPlan.planMoneyState eq 2 }">已领取</c:when>
					</c:choose>
				</span>
				<c:if test="${monthPlan.planMoneyState eq 1 }">
					<span class="read-lq">已领取</span>
				</c:if>
				<span class="reward-percent"><label class="reward-perc-numb" for="">0</label>%</span>
				<p class="p" style="display:none;"><fmt:formatNumber type="number" value="${monthPlan.factMoney/monthPlan.planMoney*100 }" pattern="0.0" maxFractionDigits="1" /></p>
				<!-- <p class="p">0</p> -->
			</div>
			<div class="votebox" style="margin-top:6px">
				<div class="votebox-sign-cust clearfix">
					<label class="fl_l" for="">签约客户：</label>
					<span class="fl_l">${monthPlan.factSigncustnum }/${monthPlan.planSigncustnum }个</span>
					<c:if test="${monthPlan.planSigncustnumState eq 1 }">
						<a class="vote-btn fl_r" pointType="signNum">领取</a>
					</c:if>
				</div>
				<dl class="barbox">
					<dd class="barline">
						<span class="charts"></span>
					</dd>
				</dl>
				<span class="sp">
					<c:choose>
						<c:when test="${monthPlan.planSigncustnumState eq 1 }">
							奖励<label class="a">${dictionaryList[1].dictionaryValue}</label>积分
						</c:when>
						<c:when test="${monthPlan.planSigncustnumState eq 2 }">已领取</c:when>
					</c:choose>
				</span>
				<c:if test="${monthPlan.planSigncustnumState eq 1 }">
					<span class="read-lq">已领取</span>
				</c:if>
				<span class="reward-percent"><label class="reward-perc-numb" for="">0</label>%</span>
				<p class="p" style="display:none;"><fmt:formatNumber type="number" value="${monthPlan.factSigncustnum/monthPlan.planSigncustnum*100 }" pattern="0.0" maxFractionDigits="1" /></p>
			</div>
			<div class="votebox" style="margin-top:6px">
				<div class="votebox-sign-cust clearfix">
					<label class="fl_l" for="">意向客户：</label>
					<span class="fl_l">${monthPlan.factWillcustnum }/${monthPlan.planWillcustnum }个</span>
					<c:if test="${monthPlan.planWillcustnumState eq 1 }">
						<a class="vote-btn fl_r" pointType="intNum">领取</a>
					</c:if>
				</div>
				<dl class="barbox">
					<dd class="barline">
						<span class="charts"></span>
					</dd>
				</dl>
				<span class="sp">
					<c:choose>
						<c:when test="${monthPlan.planWillcustnumState eq 1 }">
							奖励<label class="a">${dictionaryList[1].dictionaryValue}</label>积分
						</c:when>
						<c:when test="${monthPlan.planWillcustnumState eq 2 }">已领取</c:when>
					</c:choose>
				</span>
				<c:if test="${monthPlan.planWillcustnumState eq 1 }">
					<span class="read-lq">已领取</span>
				</c:if>
				<span class="reward-percent"><label class="reward-perc-numb" for="">0</label>%</span>
				<p class="p" style="display:none;"><fmt:formatNumber type="number" value="${monthPlan.factWillcustnum/monthPlan.planWillcustnum*100 }" pattern="0.0" maxFractionDigits="1" /></p>
			</div>
		</c:if>
			<div class="index-growth-level" style="display:none;">
				<iframe src="${ctx}/main/sale/levelGrowth" id="iframepage" frameborder="0" scrolling="no" width="100%" height="100%"></iframe>
			</div>
		</div> --%>
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
		<!-- <div class="contect-us">
			<span class="conte-us-title fl_l">联系我们</span>
			<span class="conte-us-phone fl_r" onclick="call('4008262277')" title="客服热线：4008262277"></span>
			<span class="conte-us-qq fl_r" onclick="$('#QQOnLine iframe').contents().find('#launchBtn').click();" title="服务QQ：4008262277"></span>
		</div> -->
		<div class="home-mess-center">
			<!-- <span class="conte-us-title fl_l">联系我们</span>
			<span class="conte-us-phone fl_r" onclick="call('4008262277')" title="客服热线：4008262277"></span>
			<span class="conte-us-qq fl_r" onclick="$('#QQOnLine iframe').contents().find('#launchBtn').click();" title="服务QQ：4008262277"></span> -->
			<div class="mess-center-title">
			<span>联系我们</span>
			</div>
			<div class="mess-center-icon" style="padding-bottom:0;">
				<div class="mess-icon-first clearfix" style="margin-bottom:0;">
					<div class="icon-first-left fl_l product-qq-title" data-title="服务QQ：" onclick="$('#QQOnLine iframe').contents().find('#launchBtn').click();" title="服务QQ：4008262277">
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
	<div class="hyx-layout-content hyx-layout">
		<!-- 中间 -->
		<div class="hyx-index-center hyx-layout-content">
			<div class="index-sale-top clearfix">
				<div class="plan-the-month"><span>今日计划</span></div>
				<div class="sale-top-left w33p">
					<div class="anal-deta-pies08">
						<span id="res_sign_num" class="trans-contr11">0个</span>
						<span id="res_no_num" class="trans-contr12">0个</span>
						<span id="res_call_num" class="trans-contr13">0分</span>
						<span id="res_pool_num" class="trans-contr14">0个</span>
						<span id="res_cust_num" class="trans-contr15">0个</span> 
						<div class="home-resou-will06"><span class="center-resou">资源</span><span class="prac-conne">实际联系</span><span id="res_prac" class="prac-comm-number">0</span></div>
					</div>	
				</div>
				<div class="sale-top-middle w33p">
					<div class="anal-deta-pies10">
				    	<div id="main04" class="home-resou-will10"></div>
		        	</div>
		        	<div class="anal-deta-pies11 w33p">
		            	<div id="main05" class="home-resou-will11"></div>
		        	</div>
				</div>
				<div class="sale-top-right w33p">
					<div class="anal-deta-pies09">
		        		<span id="int_sign_num" class="will-trans-contr11">0个</span>
						<span id="int_no_num" class="will-trans-contr12">0个</span>
						<span id="int_call_num" class="will-trans-contr13">0分</span>
						<span id="int_pool_num" class="will-trans-contr14">0个</span>
						<span id="int_cust_num" class="will-trans-contr15">0个</span> 
						<div class="home-resou-will07"><span class="will-center-resou">意向</span><span class="will-prac-conne">实际联系</span><span id="int_prac" class="will-prac-number">0</span></div>
					</div>
				</div>
			</div>
			<div class="index-sale-bottom clearfix">
				<div class="sale-bottom-title clearfix">
					<span class="bottom-title-img fl_l"></span>
					<span class="today-jrgz fl_l">今日关注</span>
					<a href="javascript:void(0);" id="moreInfo" class="today-jrgz-more fl_r">更多</a>
				</div>
				<input type="hidden" id="plan_id" value=""/>
				<div class="resour-will-sign">
					<ul>
						<li id="res_li" class="resour-second-li">
							<label for="">资源</label>
							<span id="resNum">0</span>
						</li>
						<li id="int_li" class="resour-first-li">
							<label for="">意向客户</label>
							<span id="intNum">0</span>
						</li>
						<li id="sign_li" class="resour-second-li">
							<label for="">签约客户</label>
							<span id="signNum">0</span>
						</li>
					</ul>
				</div>
				<div class="index-center-table" id="ajaxTable">
					
				</div>
			</div>
		</div>       
		<!-- 右边 -->
		<div class="hyx-index-right hyx-layout-side">
			<div class="sales-perfor-rank">
				<ul id="saleMoneyRank">
					<li class="sale-perfor-title"><span>回款金额排行榜</span> <!-- <a href="###">更多</a> --></li>
					<li class="sale-perfor-th sale-perth-bg clearfix">
						<span>排名</span>
						<span>姓名</span>
						<span style="border:none;">金额(万)</span>
					</li>
					<li class="sale-perfor-td"><span>&nbsp;</span><span>&nbsp;</span><span>&nbsp;</span></li>
					<li class="sale-perfor-td sale-perth-bg"><span>&nbsp;</span><span>&nbsp;</span><span>&nbsp;</span></li>
					<li class="sale-perfor-td"><span>&nbsp;</span><span>&nbsp;</span><span>&nbsp;</span></li>
					<li class="sale-perfor-td sale-perth-bg"><span>&nbsp;</span><span>&nbsp;</span><span>&nbsp;</span></li>
					<li class="sale-perfor-td"><span>&nbsp;</span><span>&nbsp;</span><span>&nbsp;</span></li>
					<li class="sale-perfor-td sale-perth-bg"><span>&nbsp;</span><span>&nbsp;</span><span>&nbsp;</span></li>
				</ul>
			</div>
			<div class="cust-hero-list">
				<ul id="signCustsRank">
					<li class="sale-perfor-title"><span>签约客户排行榜</span> <!-- <a href="###">更多</a> --></li>
					<li class="sale-perfor-th sale-perth-bg clearfix">
						<span>排名</span>
						<span>姓名</span>
						<span style="border:none;">客户(个)</span>
					</li>
					<li class="sale-perfor-td"><span>&nbsp;</span><span>&nbsp;</span><span>&nbsp;</span></li>
					<li class="sale-perfor-td sale-perth-bg"><span>&nbsp;</span><span>&nbsp;</span><span>&nbsp;</span></li>
					<li class="sale-perfor-td"><span>&nbsp;</span><span>&nbsp;</span><span>&nbsp;</span></li>
					<li class="sale-perfor-td sale-perth-bg"><span>&nbsp;</span><span>&nbsp;</span><span>&nbsp;</span></li>
					<li class="sale-perfor-td"><span>&nbsp;</span><span>&nbsp;</span><span>&nbsp;</span></li>
					<li class="sale-perfor-td sale-perth-bg"><span>&nbsp;</span><span>&nbsp;</span><span>&nbsp;</span></li>
				</ul>
			</div>
		</div>
	</div>
</div>


<!-- 在线客服 -->
<div id="QQOnLine" style="display: none;">
	<script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODAwNjMxNF8xNjk2MTFfNDAwODI2MjI3N18"></script>
</div>
<script>
	$(document).ready(function(){
		ajaxLoading("正在加载页面，请稍后") ;
		$(".datagrid-mask,.datagrid-mask-msg").ajaxStop(function(){
			$(this).remove();
		});
	});
</script>
<script type="text/javascript" src="${ctx}/static/js/view/home/sale_home_chart.js${_v}"></script>
</body>
</html>