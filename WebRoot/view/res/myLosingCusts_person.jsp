<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tree_top_part.css${_v}"/>
<style>
	.com-moTag .a{width:72px;text-align:right;}/* 为了适应“未联系天数”字段的长度 */
</style>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/custcommon.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/res/mySilentCust.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
</head>
<body>
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<input type="hidden" id="isState" value="${shiroUser.isState }" />
	<form action="${ctx }/res/cust/myLosingCusts" method="post">
	<!--资源-->
		<div class="hyx-layout">
		<div class="hyx-cm-left hyx-layout-content">
			<div class="com-search hyx-cm-search">
				<div class="com-search-box fl_l">
					<div class="search clearfix" >
					   <div class="select_outerDiv fl_l">
					   <span class="icon_down_5px"></span>
					   	<select name="queryType" class="fl_l search_head_select">
				   			<option value="4" ${(empty resCustInfoDto.queryType or resCustInfoDto.queryType eq '4') ? 'selected' : ''}>单位名称</option>
					   		<option value="1" ${resCustInfoDto.queryType eq '1' ? 'selected' : ''}>客户姓名</option>
					   		<option value="3" ${resCustInfoDto.queryType eq '3' ? 'selected' : ''}>联系电话</option>
					   		<c:forEach items="${queryFileds }" var="field">
					   			<option value="${field.fieldCode }" ${resCustInfoDto.queryType eq field.fieldCode ? 'selected' : ''}>${field.fieldName }</option>
					   		</c:forEach>
					   	</select>
					   	</div>
					   	<input class="input-query fl_l" type="text" name="queryText" value="${resCustInfoDto.queryText }" />
					   	<label class="hide-span"></label>
					  </div>
				   <div class="list-box">
					    <!-- 上次联系时间 -->
						<input type="hidden" name="startActionDate" id="s_startActionDate" value="${resCustInfoDto.startActionDate }" />
					    <input type="hidden" name="endActionDate" id="s_endActionDate" value="${resCustInfoDto.endActionDate }"/>
						<input type="hidden" name="lDateType" id="lDateType" value="${resCustInfoDto.lDateType }" />
						<c:set var="lDateName" value="上次联系时间" />
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
						<dl class="select prec-cont-time">
							<dt>${lDateName }</dt>
							<dd>
								<ul>
									<li><a href="###" onclick="setActionDate(0)" title="上次联系时间">上次联系时间</a></li>
									<li><a href="###" onclick="setActionDate(1)" title="当天">当天</a></li>
									<li><a href="###" onclick="setActionDate(2)" title="本周">本周</a></li>
									<li><a href="###" onclick="setActionDate(3)" title="本月">本月</a></li>
									<li><a href="###" onclick="setActionDate(4)" title="半年">半年</a></li>
									<li><a href="###" title="自定义" id="actionDate" class="diy date-range">自定义</a></li>
								</ul>
							</dd>
						</dl>
						<!-- 合同截止时间 -->
					   <input type="hidden" name="pstartDate" id="s_pstartDate" value="${resCustInfoDto.pstartDate }" />
					   <input type="hidden" name="pendDate" id="s_pendDate" value="${resCustInfoDto.pendDate }"/>
					   <input type="hidden" name="oDateType" id="oDateType" value="${resCustInfoDto.oDateType }" />
					   <c:set var="oDateName" value="合同截止时间" />
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
					   <dl class="select prec-cont-time">
							<dt>${oDateName }</dt>
							<dd>
								<ul>
									<li><a href="###" onclick="setPdate(0)" title="合同截止时间">合同截止时间</a></li>
									<li><a href="###" onclick="setPdate(1)" title="当天">当天</a></li>
									<li><a href="###" onclick="setPdate(2)" title="本周">本周</a></li>
									<li><a href="###" onclick="setPdate(3)" title="本月">本月</a></li>
									<li><a href="###" onclick="setPdate(4)" title="半年">半年</a></li>
									<li><a href="###" id="pDate" title="自定义" class="diy date-range">自定义</a></li>
								</ul>
							</dd>
						</dl>
						<input name="losingId" id="s_losingId" value="${resCustInfoDto.losingId }" type="hidden" />
						<c:set var="losingName" value="流失原因" />
						<c:forEach var="opts" items="${options }">
							<c:if test="${opts.optionlistId eq resCustInfoDto.losingId }">
								<c:set var="losingName" value="${opts.optionName }" />
							</c:if>
						</c:forEach>
						<dl class="select">
							<dt>${losingName }</dt>
							<dd>
								<ul>
									<li><a href="###" onclick="$('#s_losingId').val('')" title="流失原因">流失原因</a></li>
									<c:forEach var="opt" items="${options }">
										<li><a href="###" onclick="$('#s_losingId').val('${opt.optionlistId }')" title="${opt.optionName }">${opt.optionName }</a></li>
									</c:forEach>
								</ul>
							</dd>
						</dl>
						<c:if test="${shiroUser.issys eq 1 }">
							<input type="hidden" name="ownerAccsStr" value="${resCustInfoDto.ownerAccsStr }" id="ownerAccsStr" />
							<input type="hidden" name="osType" value="${resCustInfoDto.osType }" id="osType" />
							<div class="reso-sub-dep fl_l">
								<input style="border-right: 2px solid #e1e1e1;" id="ownerNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="所有者">
								<div class="manage-drop"  style="height:370px;">
									<div class="shows-radio-boxs"></div>
									<ul class="shows-allorme-boxs">
										<li><input type="radio"  value="1"  name="searchOwnerType" ${resCustInfoDto.osType eq '1' ? 'checked':'' }>查看全部</li>
										<li><input type="radio" name="searchOwnerType"  value="2" ${resCustInfoDto.osType eq '2' ? 'checked':'' }>查看自己</li>
									</ul>
									<div class="sub-dep-ul" style="width:190px;" data-type="search-tree">
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
				<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query" />
			</div>
			<input type="hidden" id="noteType" value="${resCustInfoDto.noteType }" name="noteType" />
			<p class="com-moTag">
				<label class="a">未联系天数：</label>
				<a href="javascript:;" nt="1" class="e${resCustInfoDto.noteType eq '1' ? ' e-hover' : '' }">全部</a>
				<a href="javascript:;" nt="2" class="e${resCustInfoDto.noteType eq '2' ? ' e-hover' : '' }">7天</a>
				<a href="javascript:;" nt="3" class="e${resCustInfoDto.noteType eq '3' ? ' e-hover' : '' }">30天</a>
				<a href="javascript:;" nt="4" class="e${resCustInfoDto.noteType eq '4' ? ' e-hover' : '' }">90天</a>
<%-- 				<input type="text" id="days" name="days" value="${resCustInfoDto.days }" class="diy-box" /> --%>
<!-- 				<label class="diy-day">天</label> -->
			</p>
			<p class="com-moTag">
				<label class="a">行动标签：</label>
				<c:forEach items="${labelNamesList }" var="ln">
					<span label="1" class="e">${ln }</span>
				</c:forEach>
				<span id="addLabelBtn" style="cursor: pointer;color: blue;" class="e">选择标签</span>
				<input id="allLabels" name="allLabels" type="hidden" value="${resCustInfoDto.allLabels }"/>
			</p>
			<div class="com-btnlist hyx-cm-btnlist fl_l" style="margin-top:12px;">
				<a href="javascript:;" <c:if test="${shiroUser.serverDay gt 0}">onclick="toBatchSmsSend()"</c:if> class="${shiroUser.serverDay gt 0 ? 'com-btna' : 'com-btna-no' } demoBtn_e fl_l"><i class="min-messag ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }"></i><label class="lab-mar">发送短信</label></a>
			</div>
			<div class="com-table com-mta hyx-cm-table fl_l" style="display:block;">
				<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
					<thead>
						<tr role="head" class="sty-bgcolor-b">
							<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
							<th><span class="sp sty-borcolor-b">操作</span></th>
							<th><span class="sp sty-borcolor-b">客户姓名</span></th>
							<th><span class="sp sty-borcolor-b">单位名称</span></th>
<%-- 							<c:if test="${shiroUser.issys eq 1 }"> --%>
<!-- 								<th><span class="sp sty-borcolor-b">联系人</span></th> -->
<%-- 							</c:if> --%>
							<th><span class="sp sty-borcolor-b">流失原因</span></th>
<!-- 							<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>订单累计金额（万）</span><i></i></label></span></th> -->
							<th><span class="sp sty-borcolor-b" sort="true">合同到期时间</span></th>
							<th><span class="sp sty-borcolor-b" sort="true">未联系天数</span></th>
							<th hidesort="true"><span class="sp sty-borcolor-b" sort="true">上次联系时间</span></th>
							<th><span class="sp sty-borcolor-b">备注</span></th>
							<c:choose>
								<c:when test="${!empty queryFileds }">
									<th><span class="sp sty-borcolor-b">所有者</span></th>
									<c:forEach items="${queryFileds }" var="field" varStatus="vs">
										<c:choose>
											<c:when test="${vs.count eq fn:length(queryFileds) }">
												<th>${field.fieldName }</th>
											</c:when>
											<c:otherwise>
												<th><span class="sp sty-borcolor-b">${field.fieldName }</span></th>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<th>所有者</th>
								</c:otherwise>
							</c:choose>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${!empty custs }">
								<c:forEach items="${custs }" var="cust" varStatus="vs">
									<tr id="rightView_${cust.resCustId }" class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">
										<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input name="check_" name_tel="${cust.name }|${cust.telphone}" name_mobile="${cust.name }|${cust.mobilephone}" mobile="${cust.mobilephone }" telPhone="${cust.telphone}" type="checkbox" id="chk_${cust.resCustId }" /></div></td>
										<td style="width:50px;">
											<div class="overflow_hidden w50 link">
												<c:choose>
														<c:when test="${shiroUser.serverDay gt 0 }">
															<a href="javascript:;" id="follow_${cust.resCustId }" custType="5" class="icon-follow-up" title="客户跟进"></a>
														</c:when>
														<c:otherwise>
															<a href="javascript:;" class="icon-follow-gray" title="客户跟进"></a>
														</c:otherwise>
												</c:choose>
												<a href="javascript:;" id="cardInfo_${cust.resCustId }" custName="${empty cust.company ? cust.name : cust.company }" class="icon-cust-card" title="客户卡片"></a>
											</div>
										</td>
										<td><div class="overflow_hidden w110" title="${cust.name }">${cust.name }</div></td>
										<td><div class="overflow_hidden w120" title="${cust.company }">${cust.company }</div></td>
<%-- 										<c:if test="${shiroUser.issys eq 1 }"> --%>
<%-- 											<td><div class="overflow_hidden w50" title="${cust.mainLinkman }">${cust.mainLinkman }</div></td> --%>
<%-- 										</c:if> --%>
										<td><div class="overflow_hidden w70" title="${cust.losingReason }">${cust.losingReason }</div></td>
<%-- 										<td><div class="overflow_hidden w170" title="<fmt:formatNumber value="${cust.totalOrderAmount }" pattern="#,##0.00#"/>"><fmt:formatNumber value="${cust.totalOrderAmount }" pattern="#,##0.00#"/></div></td> --%>
										<td><div class="overflow_hidden w100" title="<fmt:formatDate value="${cust.contractEndDate }" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${cust.contractEndDate }" pattern="yyyy-MM-dd"/></div></td>
										<td><div class="overflow_hidden w90" title="${cust.days }">${cust.days }</div></td>
										<td hidevalue="${cust.startActionDate }"><div class="overflow_hidden w100" title="${cust.startActionDate }">${cust.endActionDate }</div></td>
										<td><div class="overflow_hidden w100" title="${cust.losingRemark }">${cust.losingRemark }</div></td>
										<td><div class="overflow_hidden w50" title="${cust.ownerName }">${cust.ownerName }</div></td>
										<c:forEach items="${queryFileds }" var="field" varStatus="vs">
											<td><div class="overflow_hidden w50" title="${cust[field.fieldCode] }">${cust[field.fieldCode] }</div></td>
										</c:forEach>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr class="no_data_tr"><td></td></tr>
								<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
								<tr class="no_data_tr"><td></td></tr>
								<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
								<tr class="no_data_tr"><td></td></tr>
								<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
								<tr class="no_data_tr"><td></td></tr>
								<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
								<tr class="no_data_tr"><td></td></tr>
								<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<div class="com-bot">
				<div class="com-page fl_r">${resCustInfoDto.page.pageStr }</div>
			</div>
		</div>
		<div id="custRight" class="hyx-cm-right hyx-layout-side">

		</div>
		</div>
	</form>
</body>
</html>
