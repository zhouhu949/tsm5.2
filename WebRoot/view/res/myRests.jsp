<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/common-function/function.area.select.jsp"%>
<%@ include file="/common/include-cut-version.jsp"%>
<%@ include file="/common/common-function/function.table.jsp"%>
<%@ include file="/common/common-function/function.date.jsp"%>
<%@ include file="/common/common-function/function.searchHead.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<%-- <script type="text/javascript" src="${ctx}/static/js/view/res/custcommon.js${_v}"></script> --%>
<script type="text/javascript" src="${ctx }/static/js/common/ajax_common.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/myRestsData.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/custcommon.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/myRests.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/button_auth.js${_v}"></script>
<style type="text/css" rel="stylesheet">
	<c:forEach items="${sorts}" var="sort">
		.com-table>table>tbody>tr>td:nth-child(${sort}),.com-table>table>thead>tr>th:nth-child(${sort}){display: none;}
	</c:forEach>
	 <c:choose>
	<c:when test="${planPage eq 1 }">
	[data-hidden="true"]{display:none!important;}
	.com-table>table>tbody>tr>td:nth-child(2),.com-table>table>thead>tr>th:nth-child(2){display:none!important;}
	[data-hidden-box="true"] a,[data-hidden-box="true"]>label,[data-hidden-box="true"]>span{display:none!important;}
	[data-hidden-box="true"] #addPlanBtn,.not-today-input-box{display:block!important;}
	</c:when>
	<c:otherwise>
	[data-hidden-box="true"] #addPlanBtn{display:none!important;}
	</c:otherwise>
	</c:choose> 
</style>
</head>
<body>
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<input type="hidden" id="isState" value="${shiroUser.isState }" />
	<input type="hidden" id="loginAcc" value="${shiroUser.account }" />
	<form id="queryForm" action="${ctx}/res/cust/myRests" method="post">
	<!-- 加入计划字段 -->
	<input type="hidden" id="planPage" name="planPage" value="${planPage}" />
	<input type="hidden" id="planType" name="planType" value="${planType}" />
	<input type="hidden" id="dateStr" name="dateStr" value="${dateStr}" />
	<input type="hidden" id="planid" name="planid" value="${planid}" />
	<div class="hyx-cma hyx-layout">
		<div class="hyx-cm-left hyx-layout-content">
				<div class="com-search hyx-cm-search">
					<div class="com-search-box fl_l">
					   <div class="search clearfix" >
						   <div class="select_outerDiv fl_l">
							   <span class="icon_down_5px"></span>
							   <select name="queryType" class="fl_l search_head_select">
							   		<c:if test="${ !empty tList && fn:length(tList) > 0 }">
										<c:forEach items="${tList}" var="defined" varStatus="vs">
											 <option value="${defined.searchCode }" ${item.queryType eq defined.searchCode? 'selected' :''}>${defined.searchName }</option>
								   		</c:forEach>
							   		</c:if>
							   	</select>
						   	</div>
						   	<input class="input-query fl_l" type="text" name="queryText" value="" />
						   	<label class="hide-span"></label>
					   </div>
					   <div class="list-box">
						   <!-- 资源分组 -->
						   <dl class="select resGroup pos${empty mutilSearchCodeSortMap['groupId'] ? '0' : mutilSearchCodeSortMap['groupId']}" data-input="[name='groupId']">
								<dt>资源分组</dt>
								<dd>
									<ul class="resource-group-tree" data-url="${ctx}/res/group/get_res_group_json">
										<!-- 部门树 -->
									</ul>
								</dd>
								<input name="groupId" type="hidden" value="" id="s_groupId">
						   </dl>
						   <!-- 添加分配时间 -->
						   <input type="hidden" name="pstartDate" id="s_pstartDate" value="" />
						   <input type="hidden" name="pendDate" id="s_pendDate" value=""/>
						   <input type="hidden" name="oDateType" id="oDateType" value="" />
						   <dl class="select pos${empty mutilSearchCodeSortMap['pstartDate'] ? '0' : mutilSearchCodeSortMap['pstartDate']}">
								<dt>添加/分配时间</dt>
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
							<input type="hidden" name="startActionDate" id="s_startActionDate" value="" />
						    <input type="hidden" name="endActionDate" id="s_endActionDate" value=""/>
							<input type="hidden" name="lDateType" id="lDateType" value="" />
							<dl class="select pos${empty mutilSearchCodeSortMap['startActionDate'] ? '0' : mutilSearchCodeSortMap['startActionDate']}">
								<dt>最近联系时间</dt>
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
							<input type="hidden" name="startDate" id="s_startDate" value="" />
						    <input type="hidden" name="endDate" id="s_endDate" value=""/>
							<input type="hidden" name="nDateType" id="nDateType" value="" />
							<dl class="select pos${empty mutilSearchCodeSortMap['startDate'] ? '0' : mutilSearchCodeSortMap['startDate']}">
								<dt>下次联系时间</dt>
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
							
							<input type="hidden" name="source" id="v_source" value="">							
							<dl class="select pos${empty mutilSearchCodeSortMap['source'] ? '0' : mutilSearchCodeSortMap['source']}">
								<dt>客户来源</dt>
								<dd>
									<ul>
										<li><a href="###" onclick="$('#v_source').val('')" title="客户来源">客户来源</a></li>
										<li><a href="###" onclick="$('#v_source').val('1')" title="自有导入">自有导入</a></li>
										<li><a href="###" onclick="$('#v_source').val('2')" title="分配交接">分配交接</a></li>
										<li><a href="###" onclick="$('#v_source').val('3')" title="公海取回">公海取回</a></li>
										<li><a href="###" onclick="$('#v_source').val('4')" title="AI初筛">AI初筛</a></li>
										<li><a href="###" onclick="$('#v_source').val('5')" title="在线表单">在线表单</a></li>
										<li><a href="###" onclick="$('#v_source').val('6')" title="数据合作">数据合作</a></li>
									</ul>
								</dd>
							</dl>
							
							<dl class="select pos${empty mutilSearchCodeSortMap['inputStatus'] ? '0' : mutilSearchCodeSortMap['inputStatus']}" data-input="[name='inputStatusStr']" data-multi="true">
								<dt>筛查结果</dt>
								<dd>
									<ul>
									<!--<li><a href="###" onclick="$('#v_inputStatus').val('')" title="筛查结果">筛查结果</a></li> -->
										<li><a href="###" data-value="1" title="空号">空号</a></li>
										<li><a href="###" data-value="2" title="停机">停机</a></li>
										<li><a href="###" data-value="3" title="沉默">沉默</a></li>
										<li><a href="###" data-value="4" title="正常">正常</a></li>
									</ul>
								</dd>
								<input type="hidden" name="inputStatusStr" id="v_inputStatus" value="">
							</dl>
							
							
							<c:choose>
								<c:when test="${planPage ne 1 && shiroUser.issys eq 1 }">
									<input type="hidden" name="ownerAccsStr" value="" id="ownerAccsStr" />
									<input type="hidden" name="ownerUserIdsStr" value="" id="ownerUserIdsStr" />
									<input type="hidden" name="osType" value="1" id="osType" />
									<div class="reso-sub-dep pos${empty mutilSearchCodeSortMap['ownerAccsStr'] ? '0' : mutilSearchCodeSortMap['ownerAccsStr']}">
										<input style="border-right: 2px solid #e1e1e1;" id="ownerNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="查看全部">
										<div class="manage-drop"  style="height:370px;">
											<div class="shows-radio-boxs"></div>
											<ul class="shows-allorme-boxs">
												<li><input type="radio"  value="1"  name="searchOwnerType" checked>查看全部</li>
												<li><input type="radio" name="searchOwnerType"  value="2">查看自己</li>
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
								</c:when>
								<c:otherwise>
									<div class="reso-sub-dep pos${empty mutilSearchCodeSortMap['ownerAccsStr'] ? '0' : mutilSearchCodeSortMap['ownerAccsStr']}">
										<input style="border-right: 2px solid #e1e1e1;" id="ownerNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="${shiroUser.name }">
									</div>
								</c:otherwise>
							</c:choose>

							<input type="hidden" id="importDeptIdsStr" name="importDeptIdsStr" value="" />
							<div class="reso-sub-dep pos${empty mutilSearchCodeSortMap['importDeptIdsStr'] ? '0' : mutilSearchCodeSortMap['importDeptIdsStr']}">
								<input style="border-right: 2px solid #e1e1e1;" id="importDeptNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="资源录入部门">
								<div class="manage-drop"  style="height:370px;">
								<div class="sub-dep-ul" style="width:190px;" >
									<ul id="tt3">

					       			</ul>
					    		</div>
							    <div class="sure-cancle clearfix" style="width:120px">
									<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" onclick="seleDeptTree()" href="###"><label>确定</label></a>
									<a class="com-btnd bomp-btna fl_l" id="close02" onclick="unCheckDeptTree()" href="###"><label>清空</label></a>
								</div>
								</div>
							</div>

							<dl class="select pos${empty mutilSearchCodeSortMap['companyTrade'] ? '0' : mutilSearchCodeSortMap['companyTrade']}" data-input="[name='companyTrade']">
								<dt>所属行业</dt>
								<dd>
									<ul>
										<li><a href="###" onclick="$('#s_companyTrade').val('');" title="所属行业">所属行业</a></li>
										<c:forEach items="${companyTrades }" var="ct">
											<li><a href="###" data-value="${ct.optionlistId}" >${ct.optionName }</a></li>
										</c:forEach>
									</ul>
								</dd>
								 <input name="companyTrade" type="hidden" value="" id="s_companyTrade">
						   </dl>
							
							<dl class="select pos${empty mutilSearchCodeSortMap['area'] ? '0' : mutilSearchCodeSortMap['area']}" data-select-type="area">
								<dt>所在地区</dt>
							</dl>
							
							<!-- 自定义非文本查询 -->
							<c:forEach items="${definedSearchCodes }" var="defindeCode">
							  	<c:choose>
							  		<c:when test="${defindeCode.dataType == 2}"> <!-- 日期型 -->
							  			<dl class="select pos${empty mutilSearchCodeSortMap[defindeCode.searchCode] ? '0' : mutilSearchCodeSortMap[defindeCode.searchCode]}">
								  		<c:forEach items="${defindeCode.childList}" var="childCode">
								  			<input type="hidden" name="${childCode.name }" data-type="${childCode.order eq 0 ? 'start':'end' }"/>
								  		</c:forEach>
										<dt>${defindeCode.searchName}</dt>
										<dd>
											<ul>
												<li><a href="javascript:;" data-type="0">${defindeCode.searchName}</a></li>
												<li><a href="javascript:;" data-type="1">当天</a></li>
												<li><a href="javascript:;" data-type="2">本周</a></li>
												<li><a href="javascript:;" data-type="3">本月</a></li>
												<li><a href="javascript:;" data-type="4">半年</a></li>
												<li><a  class="date-range custom-picker" >自定义</a></li>
											</ul>
										</dd>
									</dl>
							  		</c:when>
							  		<c:when test="${defindeCode.dataType == 3}" > <!-- 单选 -->
							  			<dl class="select pos${empty mutilSearchCodeSortMap[defindeCode.searchCode] ? '0' : mutilSearchCodeSortMap[defindeCode.searchCode]}" data-input="[name='${defindeCode.searchCode }']">
											<dt>${defindeCode.searchName}</dt>
											<dd>
												<ul>
													<li><a href="###" onclick="$('#${defindeCode.searchCode }').val('');" title="${defindeCode.searchName}">${defindeCode.searchName}</a></li>
													<c:forEach items="${defindeCode.childList }"  var="childCode">
														<li><a href="###" data-value="${childCode.value}" title="${childCode.name }">${childCode.name}</a></li>
													</c:forEach>
												</ul>
											</dd>
											 <input name="${defindeCode.searchCode }" type="hidden" id="${defindeCode.searchCode }">
									   </dl>
							  		</c:when>
							  		<c:when test="${defindeCode.dataType == 4}"> <!-- 多选 -->
							  			<dl class="select pos${empty mutilSearchCodeSortMap[defindeCode.searchCode] ? '0' : mutilSearchCodeSortMap[defindeCode.searchCode]}" data-input="[name='${defindeCode.searchCode }']" data-multi="true">
											<dt>${defindeCode.searchName}</dt>
											<dd>
												<ul>
													<c:forEach items="${defindeCode.childList }" var="childCode">
														<li><a href="###" data-value="${childCode.value}" title="${childCode.name }">${childCode.name}</a></li>
													</c:forEach>
												</ul>
											</dd>
											 <input name="${defindeCode.searchCode }" type="hidden" >
									   </dl>
							  		</c:when>
							  	</c:choose>
							  </c:forEach>
						</div>
					</div>
					<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
					<input class="query-button fl_r" type="button" value="高级查询"  id="superQuery" data-url="${ctx }/highSearch/toPage?module=4" data-title="高级查询"  data-hidden="true" />
					<c:if test="${mutilSearchCodeSortMap.keySet().size() gt 5 }"><a href="javascript:;" class="more"><i></i>更多</a></c:if>
				</div>
				<input type="hidden" id="noteType" value="1" name="noteType" />
				<p class="com-moTag" data-hidden-box="true">
					<label class="a">数据分类：</label>
					<a href="###" nt="1" class="e e-hover">全部</a>
					<a href="###" nt="4" class="e">今日待联系</a>
					<a href="###" nt="5" class="e">今日已联系</a>
					<a href="###" nt="7" class="e">7天未联系</a>
					<a href="###" nt="2" class="e">未联系资源</a>
					<a href="###" nt="3" class="e">已联系资源</a>
					<a href="###" nt="6" class="e">优先资源</a>
					<c:if test="${will_recyle gt 0 }">
						<a href="###" nt="8" class="e">三日内回收</a>
					</c:if>
				</p>
				<p class="com-moTag not-today-input-box"  style="display:none;">
					<label><input type="checkbox" class="check" name="todayFlag" value="1" checked>非今日计划</label>
				</p>
				<div class="com-btnlist hyx-cm-btnlist fl_l"  data-hidden-box="true">
						<a href="###" id="addPlanBtn" class="com-btna demoBtn_b fl_l"><i class="min-add-resou"></i><label class="lab-mar">加入计划</label></a>
						<a href="###" data-authority="base_putIntoHighSeas" id="giveUpBtn" class="com-btna demoBtn_a fl_l"><i class="min-turn-will"></i><label class="lab-mar">放入公海</label></a>
						<a href="###" id="addResBtn" class="com-btna demoBtn_b fl_l"><i class="min-add-resou"></i><label class="lab-mar">添加资源</label></a>
						<a href="###" id="importId" class="com-btna demoBtn_c fl_l"><i class="min-into-resou"></i><label class="lab-mar">导入资源</label></a>
						<a href="###" id="importResultId" class="com-btna demoBtn_d fl_l"><i class="min-into-resou"></i><label class="lab-mar">导入结果</label></a>
						<a href="###" <c:if test="${shiroUser.serverDay gt 0}">onclick="toBatchSmsSend()"</c:if> class="${shiroUser.serverDay gt 0 ? 'com-btna' : 'com-btna-no' } demoBtn_e fl_l"><i class="min-messag ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }"></i><label class="lab-mar">发送短信</label></a>
						<a href="###" id="setImpBtn" class="com-btna demoBtn_f fl_l"><i class="min-set-prior"></i><label class="lab-mar">设置优先</label></a>
						<a href="###" id="setUnImpBtn" class="com-btna demoBtn_g fl_l"><i class="min-cancel-prior"></i><label class="lab-mar">取消优先</label></a>
						<a href="###" data-authority="myResCust_startScreen" id="startScreen" class="com-btna demoBtn_g fl_l"><i class="min-cancel-prior"></i><label class="lab-mar">开始筛查</label></a>
						<a href="###" data-authority="myResCust_ScreenResult" id="screenResult" class="com-btna demoBtn_g fl_l"><i class="min-cancel-prior"></i><label class="lab-mar">筛查结果</label></a>
				</div>
				<input type="hidden" name="orderKey" id="orderKey" value="" />
				<div class="com-table com-mta hyx-cm-table fl_l" style="display:block;">
					<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
						<thead>
							<tr role="head" class="sty-bgcolor-b">
								<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
								<th><span class="sp sty-borcolor-b">操作</span></th>
								<th><span class="sp sty-borcolor-b">${shiroUser.isState eq 1 ? '客户名称':'单位名称' }</span></th>
								<th><span class="sp sty-borcolor-b">${shiroUser.isState eq 1 ? '联系人' : '客户姓名' }</span></th>
								<th><span class="sp sty-borcolor-b">资源分组</span></th>
								<th hidesort="true"><span class="sp sty-borcolor-b" sortColumn="OWNER_START_DATE" sort="true">添加/分配<!-- <span class="td_sort_asc"></span> --></span></th>
								<th hidesort="true"><span class="sp sty-borcolor-b" sortColumn="ACTION_DATE" sort="true">最近联系<!-- <span class="td_sort_asc"></span> --></span></th>
								<th hidesort="true"><span class="sp sty-borcolor-b" sortColumn="NEXTFOLLOW_DATE" sort="true">下次联系<!-- <span class="td_sort_asc"></span> --></span></th>
								<th><span class="sp sty-borcolor-b">客户来源</span></th>
								<th><span class="sp sty-borcolor-b">筛查结果</span></th>
								<th><span class="sp sty-borcolor-b">所有者</span></th>
								<th><span class="sp sty-borcolor-b">资源录入部门</span></th>
								<th><span class="sp sty-borcolor-b">联系电话</span></th>
						   		<th><span class="sp sty-borcolor-b">所在地区</span></th>
						   		<th><span class="sp sty-borcolor-b">所属行业</span></th>
								<th><span class="sp sty-borcolor-b">${definedNameMap["defined1"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined2"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined3"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined4"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined5"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined6"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined7"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined8"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined9"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined10"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined11"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined12"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined13"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined14"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined15"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined16"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined17"]}</span></th>
						   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined18"]}</span></th>
								<c:if test="${recyle_setting eq 1 }">
									<th><span class="sp sty-borcolor-b">回收期限</span></th>
								</c:if>
							</tr>
						</thead>
						<tbody>
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
						</tbody>
					</table>
					<!-- 加载层 -->
					<div class="tip_search_data" >
						<div class="tip_search_text">
							<span><img alt="加载中" src="${ctx }/static/images/hua_loading.gif" /></span>
							<span>数据加载中…</span>
						</div>
						<div class="tip_search_error"></div>
					</div>
				</div>
				<div class="com-bot" >
					<div class="com-page fl_r">
						<div class="page" id="new_page"></div>
						<div class="clr_both"></div>
					</div>
				</div>
			</div>
			<div id="custRight" class="hyx-cm-right hyx-layout-side"  data-hidden="true">
				<c:choose>
					<c:when test="${shiroUser.isState eq 1 }">
						<div class="hyx-cfu-card hyx-cfu-card-none fl_l">
							<div class="tit fl_l">
								<span title="" class="sp"></span>
								<div class="mail icon-conte-list img-gray" title="通讯录"></div>
								<a href="javascript:;" class="edit demoBtn_l icon-edit img-gray fl_r" title="编辑"></a>
							</div>
							<p class="list fl_l"><span>联系人：</span><label title=""></label></p>
							<p class="list fl_l"><span>联系电话：</span><label title=""></label></p>
							<p class="list fl_l"><span>邮箱：</span><label title=""></label></p>
							<p class="list fl_l"><span>联系地址：</span><label title=""></label></p>
						</div>
						<div class="hyx-cfu-tab">
							<ul class="tab-list clearfix">
								<li id="follow_" class="select li_first">跟进记录</li>
								<li id="callLog_" >通话记录</li>
								<li id="reView_" class="li_last">点评信息</li>
							</ul>
						</div>
						<div class="hyx-wlm-cent-timeline hyx-cfu-timeline">
						      <div class="none-bg">
									<p class="tit_none">暂无联系记录！</p>
						      </div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="hyx-cfu-card hyx-cfu-card-none fl_l">
							<div class="tit fl_l">
								<span title="" class="sp"></span>
						      	<a href="javascript:;" class="edit demoBtn_l icon-edit img-gray fl_r" title="编辑"></a>
							</div>
							<p class="list fl_l"><span>性别：</span><label title=""></label></p>
							<p class="list fl_l"><span>联系电话：</span><label title=""></label></p>
							<p class="list fl_l"><span>邮箱：</span><label title=""></label></p>
							<p class="list fl_l"><span>联系地址：</span><label title=""></label></p>
						</div>
						<div class="hyx-cfu-tab">
							<ul class="tab-list clearfix">
								<li id="follow_" class="select li_first">跟进记录</li>
								<li id="callLog_" >通话记录</li>
								<li id="reView_" class="li_last">点评信息</li>
							</ul>
						</div>
						<div class="hyx-wlm-cent-timeline hyx-cfu-timeline">
						    <div class="none-bg">
								<p class="tit_none">暂无联系记录！</p>
						    </div>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		</form>
</body>
</html>
