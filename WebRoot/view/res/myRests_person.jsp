<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tree_top_part.css${_v}"/>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/custcommon.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/myRests.js${_v}"></script>
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
	<form action="${ctx }/res/cust/myRests" method="post">
	<div class="hyx-cma hyx-layout">
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
						   <!-- 资源分组 -->
						   <input name="groupId" type="hidden" value="${resCustInfoDto.groupId }" id="s_groupId">
					   	   <c:set var="selGroupName" value="资源分组" />
						   <c:forEach items="${groupList }" var="g">
						   		<c:if test="${g.resGroupId eq resCustInfoDto.groupId}">
						   			 <c:set var="selGroupName" value="${g.groupName }" />
						   		</c:if>
						   </c:forEach>
						   <dl class="select">
								<dt>${selGroupName }</dt>
								<dd>
									<ul>
										<li><a href="###" onclick="$('#s_groupId').val('');" title="资源分组">资源分组</a></li>
										<c:forEach items="${groupList }" var="group">
											<li><a href="###" onclick="$('#s_groupId').val('${group.resGroupId }');" groupId="${group.resGroupId }">${group.groupName }</a></li>
										</c:forEach>
									</ul>
								</dd>
						   </dl>
						   <!-- 添加分配时间 -->
						   <input type="hidden" name="pstartDate" id="s_pstartDate" value="${resCustInfoDto.pstartDate }" />
						   <input type="hidden" name="pendDate" id="s_pendDate" value="${resCustInfoDto.pendDate }"/>
						   <input type="hidden" name="oDateType" id="oDateType" value="${resCustInfoDto.oDateType }" />
						   <c:set var="oDateName" value="添加/分配时间" />
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
										<li><a href="###" onclick="setPdate(0)" title="添加/分配时间">添加/分配时间</a></li>
										<li><a href="###" onclick="setPdate(1)" title="当天">当天</a></li>
										<li><a href="###" onclick="setPdate(2)" title="本周">本周</a></li>
										<li><a href="###" onclick="setPdate(3)" title="本月">本月</a></li>
										<li><a href="###" onclick="setPdate(4)" title="半年">半年</a></li>
										<li><a href="###" id="pDate" title="自定义" class="diy date-range">自定义</a></li>
									</ul>
								</dd>
							</dl>
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
							<dl class="select prec-cont-time">
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
							<input type="hidden" name="startDate" id="s_startDate" value="${resCustInfoDto.startDate }" />
						    <input type="hidden" name="endDate" id="s_endDate" value="${resCustInfoDto.endDate }"/>
							<input type="hidden" name="nDateType" id="nDateType" value="${resCustInfoDto.nDateType }" />
							<c:set var="nDateName" value="下次联系时间" />
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
										<li><a href="###" onclick="setNDate(0)" title="下次联系时间">下次联系时间</a></li>
										<li><a href="###" onclick="setNDate(1)" title="当天">当天</a></li>
										<li><a href="###" onclick="setNDate(2)" title="本周">本周</a></li>
										<li><a href="###" onclick="setNDate(3)" title="本月">本月</a></li>
										<li><a href="###" onclick="setNDate(4)" title="半年">半年</a></li>
										<li><a href="###" id="nDate" title="自定义" class="date-range diy">自定义</a></li>
									</ul>
								</dd>
							</dl>
							<c:if test="${shiroUser.issys eq 1 }">
								<input type="hidden" name="ownerAccsStr" value="${resCustInfoDto.ownerAccsStr }" id="ownerAccsStr" />
								<input type="hidden" name="ownerUserIdsStr" value="${resCustInfoDto.ownerUserIdsStr }" id="ownerUserIdsStr" />
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
					<a href="###" nt="1" class="e${resCustInfoDto.noteType eq '1' ? ' e-hover' : '' }">全部</a>
					<a href="###" nt="4" class="e${resCustInfoDto.noteType eq '4' ? ' e-hover' : '' }">今日待联系</a>
					<a href="###" nt="5" class="e${resCustInfoDto.noteType eq '5' ? ' e-hover' : '' }">今日已联系</a>
					<a href="###" nt="7" class="e${resCustInfoDto.noteType eq '7' ? ' e-hover' : '' }">7天未联系</a>
					<a href="###" nt="2" class="e${resCustInfoDto.noteType eq '2' ? ' e-hover' : '' }">未联系资源</a>
					<a href="###" nt="3" class="e${resCustInfoDto.noteType eq '3' ? ' e-hover' : '' }">已联系资源</a>
					<a href="###" nt="6" class="e${resCustInfoDto.noteType eq '6' ? ' e-hover' : '' }">优先资源</a>
				</p>
				<div class="com-btnlist hyx-cm-btnlist fl_l">
					<a href="###" id="giveUpBtn" class="com-btna demoBtn_a fl_l"><i class="min-turn-will"></i><label class="lab-mar">放入公海</label></a>
					<a href="###" id="addResBtn" class="com-btna demoBtn_b fl_l"><i class="min-add-resou"></i><label class="lab-mar">添加资源</label></a>
					<a href="###" id="importId" class="com-btna demoBtn_c fl_l"><i class="min-into-resou"></i><label class="lab-mar">导入资源</label></a>
					<a href="###" id="importResultId" class="com-btna demoBtn_d fl_l"><i class="min-into-resou"></i><label class="lab-mar">导入结果</label></a>
					<a href="###" <c:if test="${shiroUser.serverDay gt 0}">onclick="toBatchSmsSend()"</c:if> class="${shiroUser.serverDay gt 0 ? 'com-btna' : 'com-btna-no' } demoBtn_e fl_l"><i class="min-messag ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }"></i><label class="lab-mar">发送短信</label></a>
					<a href="###" id="setImpBtn" class="com-btna demoBtn_f fl_l"><i class="min-set-prior"></i><label class="lab-mar">设置优先</label></a>
					<a href="###" id="setUnImpBtn" class="com-btna demoBtn_g fl_l"><i class="min-cancel-prior"></i><label class="lab-mar">取消优先</label></a>
				</div>
				<div class="com-table com-mta hyx-cm-table fl_l" style="display:block;">
					<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
						<thead>
							<tr role="head" class="sty-bgcolor-b">
								<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
								<th><span class="sp sty-borcolor-b">操作</span></th>
								<th><span class="sp sty-borcolor-b">客户姓名</span></th>
								<th><span class="sp sty-borcolor-b">单位名称</span></th>
								<th><span class="sp sty-borcolor-b">资源分组</span></th>
								<th hidesort="true"><span class="sp sty-borcolor-b" sort="true">添加/分配</span></th>
								<th hidesort="true"><span class="sp sty-borcolor-b" sort="true">最近联系</span></th>
								<th hidesort="true"><span class="sp sty-borcolor-b" sort="true">下次联系</span></th>
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
											<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="check_" status="1" owner="${res.ownerAcc }" name_tel="${res.name }|${res.telphone}" name_mobile="${res.name }|${res.mobilephone}" mobile="${res.mobilephone }" telPhone="${res.telphone}" id="chk_${res.resCustId }" /></div></td>
											<td style="width:100px;">
												<div class="overflow_hidden w100 link">
													<c:choose>
														<c:when test="${shiroUser.serverDay gt 0 }">
															<c:choose>
																<c:when test="${shiroUser.account eq res.ownerAcc }">
																	<a href="javascript:;" id="tao_${res.resCustId }" filterType="${res.filterType }" class="icon-amoy-cust" title="开始淘"></a>
																	<a href="javascript:;" id="cardInfo_${res.resCustId }" custName="${empty res.company ? res.name : res.company }" class="icon-cust-card" title="客户卡片"></a>
																	<a href="javascript:;" id="editInfo_${res.resCustId }" class="icon-edit" title="修改资源"></a>
																	<a href="javascript:;" id="sign_${res.resCustId }" signSetting="${signSetting }" class="icon-sign-cont" title="签约"></a>
																</c:when>
																<c:otherwise>
																	<a href="javascript:;" class="icon-amoy-gray" title="开始淘"></a>
																	<a href="javascript:;" id="cardInfo_${res.resCustId }" custName="${empty res.company ? res.name : res.company }" class="icon-cust-card" title="客户卡片"></a>
																	<a href="javascript:;" id="editInfo_${res.resCustId }" class="icon-edit" title="修改资源"></a>
																	<c:choose>
																		<c:when test="${adminSignAuth eq 1 }">
																			<a href="javascript:;" id="sign_${res.resCustId }" signSetting="${signSetting }" class="icon-sign-cont" title="签约"></a>
																		</c:when>
																		<c:otherwise>
																			<a href="javascript:;" class="icon-sign-gray" title="签约"></a>
																		</c:otherwise>
																	</c:choose>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<a href="javascript:;" class="icon-amoy-gray" title="开始淘"></a>
															<a href="javascript:;" id="cardInfo_${res.resCustId }" custName="${res.name }" class="icon-cust-card" title="客户卡片"></a>
															<a href="javascript:;" id="editInfo_${res.resCustId }" class="icon-edit" title="修改资源"></a>
															<a href="javascript:;" class="icon-sign-gray" title="签约"></a>
														</c:otherwise>
													</c:choose>
												</div>
											</td>
											<td><div class="overflow_hidden w90" title="${res.name }"><c:if test="${res.isPrecedence eq 1 }"><i class="min-key-areas"></i></c:if>${res.name }</div></td>
											<td><div class="overflow_hidden w100" title="${res.company }">${res.company }</div></td>
											<td><div class="overflow_hidden w70" title="${res.groupName }">${res.groupName }</div></td>
											<td hidevalue="${res.startDate }"><div class="overflow_hidden w100" title="${res.startDate }">${res.endDate }</div></td>
											<td hidevalue="${res.startActionDate }"><div class="overflow_hidden w100" title="${res.startActionDate }">${res.endActionDate }</div></td>
											<td hidevalue="<fmt:formatDate value="${res.nextFollowDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"><div class="overflow_hidden w120" title="<fmt:formatDate value="${res.nextFollowDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${res.nextFollowDate }" pattern="yyyy-MM-dd"/></div></td>
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
		</div>
		</form>
</body>
</html>
