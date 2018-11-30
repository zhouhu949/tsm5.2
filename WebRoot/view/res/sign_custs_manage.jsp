<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/custcommon.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/mySignCusts.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
</head>
<body>
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<form action="${ctx }/res/cust/mySignCusts?pageView=manage" method="post">
		<div class="hyx-cfu-left hyx-ctr hyx-sc">
			<div class="com-search hyx-cm-search">
				 <div class="com-search-box fl_l">
				    <div class="search clearfix" >
					   <div class="select_outerDiv fl_l">
					   <span class="icon_down_5px"></span>
					   	<select name="queryType" class="fl_l search_head_select">
					   		<option value="1" ${(empty resCustInfoDto.queryType or resCustInfoDto.queryType eq '1') ? 'selected' : ''}>${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</option>
					   		<c:if test="${shiroUser.isState eq 1}">
					   			<option value="2" ${resCustInfoDto.queryType eq '2' ? 'selected' : ''}>联系人</option>
					   		</c:if>
					   		<option value="3" ${resCustInfoDto.queryType eq '3' ? 'selected' : ''}>联系电话</option>
					   	</select>
					   	</div>
				   		<input class="input-query fl_l" type="text" name="queryText" value="${resCustInfoDto.queryText }" /><label class="hide-span">输入${empty filedMap['name'] ? '客户名称' : filedMap['name'] }/联系人/联系电话</label>
				   </div>
				   <div class="list-box">
						<!-- 最近联系时间 -->
						<input type="hidden" name="startActionDate" id="s_startActionDate" value="${resCustInfoDto.startActionDate }" />
					    <input type="hidden" name="endActionDate" id="s_endActionDate" value="${resCustInfoDto.endActionDate }"/>
						<input type="hidden" name="lDateType" id="lDateType" value="${resCustInfoDto.lDateType }" />
						<c:set var="lDateName" value="最近联系时间" />
						<c:choose>
					   		<c:when test="${resCustInfoDto.lDateType eq 1 }"><c:set var="lDateName" value="当天" /></c:when>
					   		<c:when test="${resCustInfoDto.lDateType eq 2 }"><c:set var="lDateName" value="本周" /></c:when>
					   		<c:when test="${resCustInfoDto.lDateType eq 3 }"><c:set var="lDateName" value="本月" /></c:when>
					   		<c:when test="${resCustInfoDto.lDateType eq 4 }"><c:set var="lDateName" value="半年" /></c:when>
					   		<c:when test="${resCustInfoDto.lDateType eq 5 }">
					   			<fmt:parseDate var="sad" value="${resCustInfoDto.startActionDate }" pattern="yyyy-MM-dd" />
					   			<fmt:parseDate var="ead" value="${resCustInfoDto.endActionDate }" pattern="yyyy-MM-dd" />
					   			<c:set var="lDateName">
					   				<fmt:formatDate value="${sad }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ead }" pattern="yy.MM.dd"/>
					   			</c:set>
					   		</c:when>
					    </c:choose>
						<dl class="select">
							<dt>${lDateName }</dt>
							<dd>
								<ul>
									<li><a href="###" onclick="setActionDate(0)" title="最近联系时间">最近联系时间</a></li>
									<li><a href="###" onclick="setActionDate(1)" title="当天">当天</a></li>
									<li><a href="###" onclick="setActionDate(2)" title="本周">本周</a></li>
									<li><a href="###" onclick="setActionDate(3)" title="本月">本月</a></li>
									<li><a href="###" onclick="setActionDate(4)" title="半年">半年</a></li>
									<li><a href="###" title="自定义" id="actionDate" class="diy date-range">自定义</a></li>
								</ul>
							</dd>
						</dl>
						<!-- 下次联系时间 -->
					   <input type="hidden" name="pstartDate" id="s_pstartDate" value="${resCustInfoDto.pstartDate }" />
					   <input type="hidden" name="pendDate" id="s_pendDate" value="${resCustInfoDto.pendDate }"/>
					   <input type="hidden" name="oDateType" id="oDateType" value="${resCustInfoDto.oDateType }" />
					   <c:set var="oDateName" value="下次联系时间" />
					   <c:choose>
					   		<c:when test="${resCustInfoDto.oDateType eq 1 }"><c:set var="oDateName" value="当天" /></c:when>
					   		<c:when test="${resCustInfoDto.oDateType eq 2 }"><c:set var="oDateName" value="本周" /></c:when>
					   		<c:when test="${resCustInfoDto.oDateType eq 3 }"><c:set var="oDateName" value="本月" /></c:when>
					   		<c:when test="${resCustInfoDto.oDateType eq 4 }"><c:set var="oDateName" value="半年" /></c:when>
					   		<c:when test="${resCustInfoDto.oDateType eq 5 }">
					   			<fmt:parseDate var="psd" value="${resCustInfoDto.pstartDate }" pattern="yyyy-MM-dd" />
					   			<fmt:parseDate var="ped" value="${resCustInfoDto.pendDate }" pattern="yyyy-MM-dd" />
					   			<c:set var="oDateName">
					   				<fmt:formatDate value="${psd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ped }" pattern="yy.MM.dd"/>
					   			</c:set>
					   		</c:when>
					   </c:choose>
					   <dl class="select">
							<dt>${oDateName }</dt>
							<dd>
								<ul>
									<li><a href="###" onclick="setPdate(0)" title="下次联系时间">下次联系时间</a></li>
									<li><a href="###" onclick="setPdate(1)" title="当天">当天</a></li>
									<li><a href="###" onclick="setPdate(2)" title="本周">本周</a></li>
									<li><a href="###" onclick="setPdate(3)" title="本月">本月</a></li>
									<li><a href="###" onclick="setPdate(4)" title="半年">半年</a></li>
									<li><a href="###" id="pDate" title="自定义" class="diy date-range">自定义</a></li>
								</ul>
							</dd>
						</dl>
						 <!-- 首次签约时间-->
					   <input type="hidden" name="startDate" id="s_startDate" value="${resCustInfoDto.startDate }" />
					   <input type="hidden" name="endDate" id="s_endDate" value="${resCustInfoDto.endDate }"/>
					   <input type="hidden" name="nDateType" id="nDateType" value="${resCustInfoDto.nDateType }" />
					   <c:set var="nDateName" value="首次签约时间" />
					   <c:choose>
					   		<c:when test="${resCustInfoDto.nDateType eq 1 }"><c:set var="nDateName" value="当天" /></c:when>
					   		<c:when test="${resCustInfoDto.nDateType eq 2 }"><c:set var="nDateName" value="本周" /></c:when>
					   		<c:when test="${resCustInfoDto.nDateType eq 3 }"><c:set var="nDateName" value="本月" /></c:when>
					   		<c:when test="${resCustInfoDto.nDateType eq 4 }"><c:set var="nDateName" value="半年" /></c:when>
					   		<c:when test="${resCustInfoDto.nDateType eq 5 }">
					   			<fmt:parseDate var="sd" value="${resCustInfoDto.startDate }" pattern="yyyy-MM-dd" />
					   			<fmt:parseDate var="ed" value="${resCustInfoDto.endDate }" pattern="yyyy-MM-dd" />
					   			<c:set var="nDateName">
					   				<fmt:formatDate value="${sd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ed }" pattern="yy.MM.dd"/>
					   			</c:set>
					   		</c:when>
					   </c:choose>
					   <dl class="select prec-cont-time">
							<dt>${nDateName }</dt>
							<dd>
								<ul>
									<li><a href="###" onclick="setSignDate(0)" title="首次签约时间">首次签约时间</a></li>
									<li><a href="###" onclick="setSignDate(1)" title="当天">当天</a></li>
									<li><a href="###" onclick="setSignDate(2)" title="本周">本周</a></li>
									<li><a href="###" onclick="setSignDate(3)" title="本月">本月</a></li>
									<li><a href="###" onclick="setSignDate(4)" title="半年">半年</a></li>
									<li><a href="###" title="自定义" id="signDate" class="diy date-range">自定义</a></li>
								</ul>
							</dd>
					   </dl>
						<c:if test="${shiroUser.issys eq 1 }">
							<input type="hidden" name="ownerAccsStr" value="${resCustInfoDto.ownerAccsStr }" id="ownerAccsStr" />
							<div class="reso-sub-dep fl_l">
								<input id="ownerNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="所有者">
								<div class="manage-drop">
									<div class="sub-dep-ul">
										<ul id="tt1">

						       			</ul>
						    		</div>
								    <div class="sure-cancle clearfix" style="width:120px">
										<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" onclick="seleTree()" href="###"><label>确定</label></a>
										<a class="com-btnd bomp-btna fl_l" id="close02" onclick="unCheckTree()" href="###"><label>清空</label></a>
									</div>
								</div>
							</div>
						</c:if>
					</div>
				</div>
				<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
			</div>
			<div class="com-btnlist hyx-cm-btnlist">
				<a href="###" <c:if test="${shiroUser.serverDay gt 0}">onclick="toBatchSmsSend()"</c:if> class="${shiroUser.serverDay gt 0 ? 'com-btna' : 'com-btna-no' } demoBtn_e fl_l"><i class="min-messag ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }"></i><label class="lab-mar">发送短信</label></a>
				<a href="###" id="cmkhBtn" class="com-btna demoBtn_h fl_l"><label>沉默客户筛选</label></a>
				<a href="###" id="lskhBtn" class="com-btna demoBtn_i fl_l"><label>流失客户筛选</label></a>
			</div>
<%-- 			<p class="hyx-sc-tit">当前共有<b class="com-red">${signMap.signNum }</b>位签约客户，今日签约<b class="com-red">${signMap.todaySignNum }</b>位，本月签约<b class="com-red">${signMap.monthSignNum }</b>位。</p> --%>
			<div class="com-table com-mta hyx-cla-table">
				<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
					<thead>
						<tr role="head" class="sty-bgcolor-b">
							<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox"  id="checkAll" class="check" /></span></th>
							<th><span class="sp sty-borcolor-b">操作</span></th>
							<th><span class="sp sty-borcolor-b">${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</span></th>
							<th><span class="sp sty-borcolor-b">联系电话</span></th>
							<th><span class="sp sty-borcolor-b" sort="true">订单累计金额(元)</span></th>
							<th hidesort="true"><span class="sp sty-borcolor-b" sort="true">最近联系时间</span></th>
							<th hidesort="true"><span class="sp sty-borcolor-b" sort="true">下次联系时间</span></th>
							<th hidesort="true"><span class="sp sty-borcolor-b" sort="true">首次签约时间</span></th>
							<th>所有者</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty rests }">
								<tr class="no_data_tr">
									<td>
										<center>当前列表无数据！</center>
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${rests }" var="res" varStatus="vs">
									<tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">
										<c:choose>
											<c:when test="${shiroUser.isState eq 1}">
												<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input name="check_" name_tel="${res.mainLinkman }|${res.telphone}" name_mobile="${res.mainLinkman }|${res.mobilephone}" mobile="${res.mobilephone }" telPhone="${res.telphone}" type="checkbox" id="chk_${res.resCustId }" /></div></td>
											</c:when>
											<c:otherwise>
												<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input name="check_" name_tel="${res.name }|${res.telphone}" name_mobile="${res.name }|${res.mobilephone}" mobile="${res.mobilephone }" telPhone="${res.telphone}" type="checkbox" id="chk_${res.resCustId }" /></div></td>
											</c:otherwise>
										</c:choose>
										<td style="width:110px;">
										<div class="overflow_hidden w110 link">
											<c:choose>
												<c:when test="${shiroUser.serverDay gt 0 }">
													<a href="javascript:;" id="follow_${res.resCustId }" custType="3" class="icon-follow-up" title="客户跟进"></a>
													<a href="javascript:;" id="cardInfo_${res.resCustId }" custName="${res.name }" class="icon-cust-card" title="客户卡片"></a>
													<a href="javascript:;" id="sign_${res.resCustId }" class="icon-new-contr" title="新增合同"></a>
												</c:when>
												<c:otherwise>
													<a href="javascript:;" class="icon-follow-gray" title="客户跟进"></a>
													<a href="javascript:;" id="cardInfo_${res.resCustId }" custName="${res.name }" class="icon-cust-card" title="客户卡片"></a>
													<a href="javascript:;" class="icon-sign-gray" title="新增合同"></a>
												</c:otherwise>
											</c:choose>
										</div>
										</td>
										<td><div class="overflow_hidden w200" title="${res.name }">${res.name }</div></td>
										<td><div class="overflow_hidden w100" phone="tel" title="${empty res.mobilephone ? res.telphone : res.mobilephone}">${empty res.mobilephone ? res.telphone : res.mobilephone}</div></td>
										<td><div class="overflow_hidden w100" title="<fmt:formatNumber value="${res.totalOrderAmount }" pattern="#,##0.00#"/>"><fmt:formatNumber value="${res.totalOrderAmount }" pattern="#,##0.00#"/></div></td>
										<td hidevalue="<fmt:formatDate value="${res.actionDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"><div class="overflow_hidden w90" title="<fmt:formatDate value="${res.actionDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${res.actionDate }" pattern="yyyy-MM-dd"/></div></td>
										<td hidevalue="<fmt:formatDate value="${res.nextFollowDate }" pattern="yyyy-MM-dd"/>"><div class="overflow_hidden w90" title="<fmt:formatDate value="${res.nextFollowDate }" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${res.nextFollowDate }" pattern="yyyy-MM-dd"/></div></td>
										<td hidevalue="<fmt:formatDate value="${res.signDate }" pattern="yyyy-MM-dd"/>"><div class="overflow_hidden w90" title="<fmt:formatDate value="${res.signDate }" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${res.signDate }" pattern="yyyy-MM-dd"/></div></td>
										<td><div class="overflow_hidden w70" title="${res.ownerName }">${res.ownerName }</div></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<div class="com-bot">
				<div class="com-page fl_r">${resCustInfoDto.page.pageStr }</div>
			</div>
		</div>
	</form>
</body>
</html>
