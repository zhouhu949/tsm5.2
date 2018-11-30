<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tree_top_part.css${_v}"/>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/custcommon.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/mySignCusts.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
<style type="text/cccss" rel="stylesheet">
.com-table>table>tbody>tr>td:nth-child(1),.com-table>table>thead>tr>th:nth-child(1){display: none;}
.com-table>table>tbody>tr>td:nth-child(2),.com-table>table>thead>tr>th:nth-child(2){display: none;}
.com-table>table>tbody>tr>td:nth-child(3),.com-table>table>thead>tr>th:nth-child(3){display: none;}
.com-table>table>tbody>tr>td:nth-child(4),.com-table>table>thead>tr>th:nth-child(4){display: none;}
.com-table>table>tbody>tr>td:nth-child(5),.com-table>table>thead>tr>th:nth-child(5){display: none;}
.com-table>table>tbody>tr>td:nth-child(6),.com-table>table>thead>tr>th:nth-child(6){display: none;}
.com-table>table>tbody>tr>td:nth-child(7),.com-table>table>thead>tr>th:nth-child(7){display: none;}
.com-table>table>tbody>tr>td:nth-child(8),.com-table>table>thead>tr>th:nth-child(8){display: none;}
.com-table>table>tbody>tr>td:nth-child(9),.com-table>table>thead>tr>th:nth-child(9){display: none;}
.com-table>table>tbody>tr>td:nth-child(10),.com-table>table>thead>tr>th:nth-child(10){display: none;}
.com-table>table>tbody>tr>td:nth-child(11),.com-table>table>thead>tr>th:nth-child(11){display: none;}
.com-table>table>tbody>tr>td:nth-child(12),.com-table>table>thead>tr>th:nth-child(12){display: none;}
.com-table>table>tbody>tr>td:nth-child(13),.com-table>table>thead>tr>th:nth-child(13){display: none;}
.com-table>table>tbody>tr>td:nth-child(14),.com-table>table>thead>tr>th:nth-child(14){display: none;}
.com-table>table>tbody>tr>td:nth-child(15),.com-table>table>thead>tr>th:nth-child(15){display: none;}
.com-table>table>tbody>tr>td:nth-child(16),.com-table>table>thead>tr>th:nth-child(16){display: none;}
</style>
</head>
<body>
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<input type="hidden" id="isState" value="${shiroUser.isState }" />
	<input type="hidden" id="loginAcc" value="${shiroUser.account }" />
	<form action="${ctx }/res/cust/mySignCusts" method="post">
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
						   		<c:when test="${resCustInfoDto.lDateType eq 6 }"><c:set var="lDateName" value="无联系时间" /></c:when>
						    </c:choose>
							<dl class="select prec-cont-time">
								<dt>${lDateName }</dt>
								<dd>
									<ul>
										<li><a href="javascript:;" onclick="setActionDate(0)" title="最近联系时间">最近联系时间</a></li>
										<li><a href="javascript:;" onclick="setActionDate(6)" title="无联系时间">无联系时间</a></li>
										<li><a href="javascript:;" onclick="setActionDate(1)" title="当天">当天</a></li>
										<li><a href="javascript:;" onclick="setActionDate(2)" title="本周">本周</a></li>
										<li><a href="javascript:;" onclick="setActionDate(3)" title="本月">本月</a></li>
										<li><a href="javascript:;" onclick="setActionDate(4)" title="半年">半年</a></li>
										<li><a href="javascript:;" title="自定义" id="actionDate" class="diy date-range">自定义</a></li>
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
						   		<c:when test="${resCustInfoDto.oDateType eq 6 }"><c:set var="oDateName" value="无联系时间" /></c:when>
						    </c:choose>
							<dl class="select prec-cont-time">
								<dt>${oDateName }</dt>
								<dd>
									<ul>
										<li><a href="javascript:;" onclick="setPdate(0)" title="下次联系时间">下次联系时间</a></li>
										<li><a href="javascript:;" onclick="setPdate(6)" title="无联系时间">无联系时间</a></li>
										<li><a href="javascript:;" onclick="setPdate(1)" title="当天">当天</a></li>
										<li><a href="javascript:;" onclick="setPdate(2)" title="本周">本周</a></li>
										<li><a href="javascript:;" onclick="setPdate(3)" title="本月">本月</a></li>
										<li><a href="javascript:;" onclick="setPdate(4)" title="半年">半年</a></li>
										<li><a href="javascript:;" title="自定义" id="pDate" class="diy date-range">自定义</a></li>
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
										<li><a href="javascript:;" onclick="setSignDate(0)" title="首次签约时间">首次签约时间</a></li>
										<li><a href="javascript:;" onclick="setSignDate(1)" title="当天">当天</a></li>
										<li><a href="javascript:;" onclick="setSignDate(2)" title="本周">本周</a></li>
										<li><a href="javascript:;" onclick="setSignDate(3)" title="本月">本月</a></li>
										<li><a href="javascript:;" onclick="setSignDate(4)" title="半年">半年</a></li>
										<li><a href="javascript:;" title="自定义" id="signDate" class="diy date-range">自定义</a></li>
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
					<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
				</div>
				<input type="hidden" id="noteType" value="${resCustInfoDto.noteType }" name="noteType" />
				<p class="com-moTag">
					<label class="a">数据分类：</label>
					<a href="javascript:;" nt="1" class="e${resCustInfoDto.noteType eq '1' ? ' e-hover' : '' }">全部</a>
					<a href="javascript:;" nt="2" class="e${resCustInfoDto.noteType eq '2' ? ' e-hover' : '' }">今日新增</a>
					<a href="javascript:;" nt="3" class="e${resCustInfoDto.noteType eq '3' ? ' e-hover' : '' }">本月新增</a>
					<a href="javascript:;" nt="4" class="e${resCustInfoDto.noteType eq '4' ? ' e-hover' : '' }">今日待联系</a>
					<a href="javascript:;" nt="5" class="e${resCustInfoDto.noteType eq '5' ? ' e-hover' : '' }">今日已联系</a>
					<a href="javascript:;" nt="6" class="e${resCustInfoDto.noteType eq '6' ? ' e-hover' : '' }">7天未联系</a>
					<a href="javascript:;" nt="7" class="e${resCustInfoDto.noteType eq '7' ? ' e-hover' : '' }">30天未联系</a>
				</p>
				<p class="com-moTag">
					<label class="a">行动标签：</label>
					<c:forEach items="${labelNamesList }" var="ln">
						<span label="1" class="e">${ln }</span>
					</c:forEach>
					<span id="addLabelBtn" style="cursor: pointer;color: blue;" class="e">选择标签</span>
					<input id="allLabels" name="allLabels" type="hidden" value="${resCustInfoDto.allLabels }"/>
				</p>
				<div class="com-btnlist hyx-cm-btnlist fl_l">
					<a href="###" id="importId" class="com-btna demoBtn_c fl_l"><i class="min-into-resou"></i><label class="lab-mar">导入客户</label></a>
					<a href="###" id="importResultId" class="com-btna demoBtn_d fl_l"><i class="min-into-resou"></i><label class="lab-mar">导入结果</label></a>
					<a href="javascript:;" <c:if test="${shiroUser.serverDay gt 0}">onclick="toBatchSmsSend()"</c:if> class="${shiroUser.serverDay gt 0 ? 'com-btna' : 'com-btna-no' } demoBtn_e fl_l"><i class="min-messag ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }"></i><label class="lab-mar">发送短信</label></a>
					<a href="javascript:;" id="cmkhBtn" class="com-btna demoBtn_h fl_l"><label>转为沉默客户</label></a>
					<a href="javascript:;" id="lskhBtn" class="com-btna demoBtn_i fl_l"><label>转为流失客户</label></a>
				</div>
				<div class="com-table com-mta hyx-cm-table fl_l" style="display:block;">
					<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
						<thead>
							<tr role="head" class="sty-bgcolor-b">
								<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox"  id="checkAll" class="check" /></span></th>
								<th><span class="sp sty-borcolor-b">操作</span></th>
								<th><span class="sp sty-borcolor-b">客户姓名</span></th>
								<th><span class="sp sty-borcolor-b">单位名称</span></th>
								<th><span class="sp sty-borcolor-b" sort="true">订单累计金额(元)</span></th>
								<th hidesort="true"><span class="sp sty-borcolor-b" sort="true">最近联系时间</span></th>
								<th hidesort="true"><span class="sp sty-borcolor-b" sort="true">下次联系时间</span></th>
								<th hidesort="true"><span class="sp sty-borcolor-b" sort="true">首次签约时间</span></th>
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
								<c:when test="${empty rests }">
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
								</c:when>
								<c:otherwise>
									<c:forEach items="${rests }" var="res" varStatus="vs">
										<tr id="rightView_${res.resCustId }" class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">
											<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input name="check_" owner="${res.ownerAcc }" name_tel="${res.name }|${res.telphone}" name_mobile="${res.name }|${res.mobilephone}" mobile="${res.mobilephone }" telPhone="${res.telphone}" type="checkbox" id="chk_${res.resCustId }" /></div></td>
											<td style="width:100px;">
												<div class="overflow_hidden w100 link">
													<c:choose>
														<c:when test="${shiroUser.serverDay gt 0 }">
															<a href="javascript:;" id="follow_${res.resCustId }" custType="3" class="icon-follow-up" title="客户跟进"></a>
															<a href="javascript:;" id="cardInfo_${res.resCustId }" custName="${empty res.company ? res.name : res.company }" class="icon-cust-card" title="客户卡片"></a>
															<a href="javascript:;" id="addContract_${res.resCustId }" class="icon-new-contr" title="新增合同"></a>
															<c:choose>
																<c:when test="${shiroUser.account eq res.ownerAcc }">
																	<a href="javascript:;" id="unsign_${res.resCustId }" class="icon-cancel-contr shows-cancel-change" title="取消签约"></a>
																</c:when>
																<c:when test="${adminSignAuth eq 1 }">
																	<a href="javascript:;" id="unsign_${res.resCustId }" class="icon-cancel-contr shows-cancel-change" title="取消签约"></a>
																</c:when>
																<c:otherwise>
																	<a href="javascript:;" class="icon-cancel-contr shows-cancel-change" title="取消签约"></a>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<a href="javascript:;" class="icon-follow-gray" title="客户跟进"></a>
															<a href="javascript:;" id="cardInfo_${res.resCustId }" custName="${empty res.company ? res.name : res.company }" class="icon-cust-card" title="客户卡片"></a>
															<a href="javascript:;" class="icon-sign-gray" title="新增合同"></a>
															<a href="javascript:;" class="icon-cancel-contr icon-cancel-change-gray" title="取消签约"></a>
														</c:otherwise>
													</c:choose>
												</div>
											</td>
											<td><div class="overflow_hidden w110" title="${res.name }">${res.name }</div></td>
											<td><div class="overflow_hidden w110" title="${res.company }">${res.company }</div></td>
											<fmt:formatNumber value="${res.totalOrderAmount }" pattern="#,##0.00#" var="totalOrderAmount"/>
											<td><div class="overflow_hidden w70" title="${totalOrderAmount }">${totalOrderAmount }</div></td>
											<td hidevalue="${res.startActionDate }"><div class="overflow_hidden w120" title="${res.startActionDate }">${res.endActionDate }</div></td>
											<td hidevalue="${res.pstartDate }"><div class="overflow_hidden w100" title="${res.pstartDate }">${res.pendDate }</div></td>
											<td hidevalue="${res.startDate }"><div class="overflow_hidden w120" title="${res.startDate }">${res.startDate }</div></td>
											<td><div class="overflow_hidden w50" title="${res.ownerName }">${res.ownerName }</div></td>
											<c:forEach items="${queryFileds }" var="field" varStatus="vs">
												<td><div class="overflow_hidden w50" title="${res[field.fieldCode] }">${res[field.fieldCode] }</div></td>
											</c:forEach>
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
			<div id="custRight" class="hyx-cm-right hyx-layout-side">

			</div>
		</form>
</body>
</html>
