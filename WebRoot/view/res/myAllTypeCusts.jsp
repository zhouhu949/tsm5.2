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
<script type="text/javascript" src="${ctx }/static/js/common/ajax_common.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/myAllTypeCustData.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/custcommon.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/res/myAllTypeCusts.js${_v}"></script>

<style type="text/css" rel="stylesheet">
<c:forEach items="${sorts}" var="sort">
	.com-table>table>tbody>tr>td:nth-child(${sort}),.com-table>table>thead>tr>th:nth-child(${sort}){display: none;}
</c:forEach>
</style>
</head>
<body>

	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<input type="hidden" id="isState" value="${shiroUser.isState }" />
	<form id="queryForm" action="${ctx }/res/cust/myAllTypeCusts" method="post">
		<div class="hyx-layout">
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
					   <input type="hidden" name="custType" value="" id="s_custType" />
					   <dl class="select pos${empty mutilSearchCodeSortMap['custType'] ? '0' : mutilSearchCodeSortMap['custType']}">
							<dt>客户状态</dt>
							<dd>
								<ul>
									<li><a href="###" onclick="setCustType('0');" title="客户类型">客户状态</a></li>
									<li><a href="###" onclick="setCustType('1');" title="资源">资源</a></li>
									<li><a href="###" onclick="setCustType('2');" title="意向客户">意向客户</a></li>
									<li><a href="###" onclick="setCustType('3');" title="签约客户">签约客户</a></li>
									<li><a href="###" onclick="setCustType('4');" title="沉默客户">沉默客户</a></li>
									<li><a href="###" onclick="setCustType('5');" title="流失客户">流失客户</a></li>
								</ul>
							</dd>
						</dl>
						<!-- 销售进程 -->
						<input type="hidden" name="saleProcessId" id="s_saleProcessId" value="" />
						<dl class="select pos${empty mutilSearchCodeSortMap['saleProcessId'] ? '0' : mutilSearchCodeSortMap['saleProcessId']}">
							<dt>销售进程</dt>
							<dd>
								<ul>
									<li><a href="###" onclick="$('#s_saleProcessId').val('')" title="销售进程">销售进程</a></li>
									<c:forEach items="${options }" var="os">
										<li><a href="###" onclick="$('#s_saleProcessId').val('${os.optionlistId}')" title="${os.optionName }">${os.optionName }</a></li>
									</c:forEach>
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
						
						<c:choose>
							<c:when test="${shiroUser.issys eq 1 }">
								<input type="hidden" name="ownerAccsStr" value="" id="ownerAccsStr" />
								<input type="hidden" name="osType" value="1" id="osType" />
								<div class="reso-sub-dep fl_l pos${empty mutilSearchCodeSortMap['ownerAccsStr'] ? '0' : mutilSearchCodeSortMap['ownerAccsStr']}">
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
								<div class="reso-sub-dep fl_l pos${empty mutilSearchCodeSortMap['ownerAccsStr'] ? '0' : mutilSearchCodeSortMap['ownerAccsStr']}">
									<input style="border-right: 2px solid #e1e1e1;" id="ownerNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="${shiroUser.name }">
								</div>
							</c:otherwise>
						</c:choose>

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
									<li><a href="###" onclick="setNDate(6)" title="无联系时间">无联系时间</a></li>
									<li><a href="###" onclick="setNDate(1)" title="当天">当天</a></li>
									<li><a href="###" onclick="setNDate(2)" title="本周">本周</a></li>
									<li><a href="###" onclick="setNDate(3)" title="本月">本月</a></li>
									<li><a href="###" onclick="setNDate(4)" title="半年">半年</a></li>
									<li><a href="###" id="nDate" title="自定义" class="date-range diy">自定义</a></li>
								</ul>
							</dd>
						</dl>
						<!-- 资源分组 -->
					    <dl class="select resGroup pos${empty mutilSearchCodeSortMap['resGroupId'] ? '0' : mutilSearchCodeSortMap['resGroupId']}" data-input="[name='resGroupId']">
							<dt>资源分组</dt>
							<dd>
								<ul class="resource-group-tree" data-url="${ctx}/res/group/get_res_group_json">
									<!-- 部门树 -->
								</ul>
							</dd>
							<input name="resGroupId" type="hidden" value="" id="s_groupId">
				  	    </dl>

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
						  			 <input name="${defindeCode.searchCode }" type="hidden" id="${defindeCode.searchCode }">
										<dt>${defindeCode.searchName}</dt>
										<dd>
											<ul>
												<li><a href="###" onclick="$('#${defindeCode.searchCode }').val('');" title="${defindeCode.searchName}">${defindeCode.searchName}</a></li>
												<c:forEach items="${defindeCode.childList }"  var="childCode">
													<li><a href="###" data-value="${childCode.value}" title="${childCode.name }">${childCode.name}</a></li>
												</c:forEach>
											</ul>
										</dd>
								   </dl>
						  		</c:when>
						  		<c:when test="${defindeCode.dataType == 4}"> <!-- 多选 -->
						  			<dl class="select pos${empty mutilSearchCodeSortMap[defindeCode.searchCode] ? '0' : mutilSearchCodeSortMap[defindeCode.searchCode]}" data-input="[name='${defindeCode.searchCode }']" data-multi="true">
						  			<input name="${defindeCode.searchCode }" type="hidden" >
										<dt>${defindeCode.searchName}</dt>
										<dd>
											<ul>
												<c:forEach items="${defindeCode.childList }" var="childCode">
													<li><a href="###" data-value="${childCode.value}" title="${childCode.name }">${childCode.name}</a></li>
												</c:forEach>
											</ul>
										</dd>
								   </dl>
						  		</c:when>
						  	</c:choose>
						  </c:forEach>
					</div>
				</div>
				<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query" />
				<input class="query-button fl_r" type="button" value="高级查询"  id="superQuery" data-url="${ctx }/highSearch/toPage?module=7" data-title="高级查询" />
				<c:if test="${mutilSearchCodeSortMap.keySet().size() gt 5 }"><a href="javascript:;" class="more"><i></i>更多</a></c:if>
			</div>
			<input type="hidden" id="noteType" value="1" name="noteType" />
			<p class="com-moTag" style="display:none;" id="res-tag">
				<label class="a">数据分类：</label>
				<a href="###" nt="1" class="e">全部</a>
				<a href="###" nt="4" class="e">今日待联系</a>
				<a href="###" nt="5" class="e">今日已联系</a>
				<a href="###" nt="7" class="e">7天未联系</a>
				<a href="###" nt="2" class="e">未联系资源</a>
				<a href="###" nt="3" class="e">已联系资源</a>
				<a href="###" nt="6" class="e">优先资源</a>
			</p>
			<p class="com-moTag" style="display:none;" id="cust-tag">
				<label class="a">数据分类：</label>
				<a href="###" nt="1" class="e">全部</a>
				<a href="###" nt="2" class="e">今日新增</a>
				<a href="###" nt="3" class="e">今日待联系</a>
				<a href="###" nt="4" class="e">今日已联系</a>
				<a href="###" nt="6" class="e">本周已联系</a>
				<a href="###" nt="7" class="e">7天未联系</a>
				<a href="###" nt="8" class="e">30天未联系</a>
				<a href="###" nt="5" class="e">重点客户</a>
			</p>
			<p class="com-moTag" style="display:none;" id="sign-tag">
				<label class="a">数据分类：</label>
				<a href="###" nt="1" class="e">全部</a>
				<a href="###" nt="2" class="e">今日新增</a>
				<a href="###" nt="3" class="e">本月新增</a>
				<a href="###" nt="4" class="e">今日待联系</a>
				<a href="###" nt="5" class="e">今日已联系</a>
				<a href="###" nt="6" class="e">7天未联系</a>
				<a href="###" nt="7" class="e">30天未联系</a>
			</p>
			<p class="com-moTag" style="display:none;" id="label-tag">
				<label class="a">行动标签：</label>
				<span id="addLabelBtn" style="cursor: pointer;color: blue;" class="e">选择标签</span>
				<input id="allLabels" name="allLabels" type="hidden" value=""/>
			</p>
			<div class="com-btnlist hyx-cm-btnlist fl_l">
				<a href="javascript:;" <c:if test="${shiroUser.serverDay gt 0}">onclick="toBatchSmsSend()"</c:if> class="${shiroUser.serverDay gt 0 ? 'com-btna' : 'com-btna-no' } demoBtn_e fl_l"><i class="min-messag ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }"></i><label class="lab-mar">发送短信</label></a>
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
							<th><span class="sp sty-borcolor-b">客户状态</span></th>
							<th>销售进程</th>
							<th hidesort="true"><span class="sp sty-borcolor-b" sortColumn="OWNER_START_DATE" sort="true">添加/分配<!-- <span class="td_sort_asc"></span> --></span></th>
							<th hidesort="true"><span class="sp sty-borcolor-b" sortColumn="ACTION_DATE" sort="true">最近联系<!-- <span class="td_sort_asc"></span> --></span></th>
							<th hidesort="true"><span class="sp sty-borcolor-b" sortColumn="NEXTFOLLOW_DATE" sort="true">下次联系<!-- <span class="td_sort_asc"></span> --></span></th>
							<th><span class="sp sty-borcolor-b">客户来源</span></th>
							<th><span class="sp sty-borcolor-b">所有者</span></th>
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
		<div id="custRight" class="hyx-cm-right hyx-layout-side">
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
