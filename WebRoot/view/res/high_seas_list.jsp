<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/common-function/function.area.select.jsp"%>
<%@ include file="/common/include-cut-version.jsp"%>
<%@ include file="/common/common-function/function.table.jsp"%>
<%@ include file="/common/common-function/function.date.jsp"%>
<%@ include file="/common/common-function/function.searchHead.jsp"%>
<style>
	.tree-node{width:240px;}
	.hyx-hsm-left dl{text-align:left;}
	.hyx-hsm-left dl dt,.hyx-hsm-left dl dd{text-indent:30px;}
	.com-search .hide-span{left:110px;}
	.com-moTag .a{width:72px;text-align:right;}/* 为了适应“未联系天数”字段的长度 */
	.hyx-dfpzy-reso .hyx-hsm-left .resource-group-tree li{padding-left:0;}
	.resource-group-tree .tree-collapsed, .tree-expanded{margin-top:2px;}
	.resource-group-tree li .tree-node{width:100%;position:relative;height:25px;line-height:25px;}
	.resource-group-tree .tree-node .tree-title{height:25px;line-height:25px}
	.hyx-hsm-left dl dd span{width:165px;}
	.item-list-head {background-color: #e1e1e1;}
</style>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/ajax_common.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/res/high_seas_list.js?${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/highSeasListData.js?V=111"></script>
<script type="text/javascript" src="${ctx}/static/js/idialog/loading.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/button_auth.js${_v}"></script>
<style type="text/css" rel="stylesheet">
<c:forEach items="${sorts}" var="sort">
	.com-table>table>tbody>tr>td:nth-child(${sort}),.com-table>table>thead>tr>th:nth-child(${sort}){display: none;}
</c:forEach>
.com-table>table>tbody>tr>td.notHighSeas{display: none;}
</style>
</head>
<body>
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<form id="queryForm" action="${ctx }/res/sea/highSeasList" method="post">
		<input type="hidden" name="resGroupIdsStr" id="h_resGroupId" value="" />
		<input type="hidden" name="isShareRes" id="h_isShareRes" value="0" />
		<input type="hidden" id="level" name="level" value="${level}">
		<div class="hyx-dfpzy-reso hyx-layout">
			<div class="hyx-hsm-left hyx-layout-side" style="overflow-y:auto;">
				<ul>
					<li class="item-list-head">资源分组</li>
				</ul>
				<ul class="resource-group-tree" data-url="${ctx}/res/group/get_res_group_json">
					<!-- 部门树 -->
				</ul>
			</div>
			<div class="hyx-hsm-right1 hyx-layout-content" style="border:none;">
				<input type="hidden" name="highSeaType" id="highSeaType" value="0" />
				<c:if test="${isShare eq 1 }">
					<div class="year-sale-play">
					    <ul class="ann-sale-plan clearfix" style="width:400px;">
					        <li class="select li_first high_sea_type">公海客户</li>
					        <li class="li_last high_sea_type">共享资源</li>
					    </ul>
					</div>
				</c:if>
				<div class="com-search hyx-cm-search high_seas_listbox_width">
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
					   		<input class="input-query fl_l" id="queryText" type="text" name="queryText" value="" /><label class="hide-span"></label>
					   </div>
					   <div class="list-box">
						   <dl class="select pos${empty mutilSearchCodeSortMap['startDate'] ? '0' : mutilSearchCodeSortMap['startDate']}" data-dt-value="放弃时间">
								<dt>放弃时间</dt>
								<dd>
									<ul>
										<li><a href="###" onclick="setNDate(0)" title="放弃时间">放弃时间</a></li>
										<li><a href="###" onclick="setNDate(1)" title="当天">当天</a></li>
										<li><a href="###" onclick="setNDate(2)" title="本周">本周</a></li>
										<li><a href="###" onclick="setNDate(3)" title="本月">本月</a></li>
										<li><a href="###" onclick="setNDate(4)" title="半年">半年</a></li>
										<li><a href="###" id="nDate" title="自定义" class="date-range diy">自定义</a></li>
									</ul>
								</dd>
							   <input type="hidden" name="startDate" id="s_startDate" value="" />
							   <input type="hidden" name="endDate" id="s_endDate" value=""/>
							   <input type="hidden" name="nDateType" id="nDateType" value="" />
						   </dl>

						   <dl class="select pos${empty mutilSearchCodeSortMap['startActionDate'] ? '0' : mutilSearchCodeSortMap['startActionDate']}"  data-dt-value="最近联系时间">
								<dt>最近联系时间</dt>
								<dd>
									<ul>
										<li><a href="###" onclick="setLDate(0)" title="最近联系时间">最近联系时间</a></li>
										<li><a href="###" onclick="setLDate(1)" title="当天">当天</a></li>
										<li><a href="###" onclick="setLDate(2)" title="本周">本周</a></li>
										<li><a href="###" onclick="setLDate(3)" title="本月">本月</a></li>
										<li><a href="###" onclick="setLDate(4)" title="半年">半年</a></li>
										<li><a href="###" id="lDate" title="自定义" class="date-range diy">自定义</a></li>
									</ul>
								</dd>
							   <input type="hidden" name="startActionDate" id="s_startActionDate" value="" />
							   <input type="hidden" name="endActionDate" id="s_endActionDate" value=""/>
							   <input type="hidden" name="lDateType" id="lDateType" value="" />
						   </dl>
						   <!-- 销售进程 -->
							<dl class="select pos${empty mutilSearchCodeSortMap['saleProcessId'] ? '0' : mutilSearchCodeSortMap['saleProcessId']}" data-dt-value="销售进程" >
								<dt>销售进程</dt>
								<dd>
									<ul>
										<li><a href="###" onclick="$('#s_saleProcessId').val('')" title="销售进程">销售进程</a></li>
										<c:forEach items="${options }" var="os">
											<li><a href="###" onclick="$('#s_saleProcessId').val('${os.optionlistId}')" title="${os.optionName }">${os.optionName }</a></li>
										</c:forEach>
									</ul>
								</dd>
								<input type="hidden" name="saleProcessId" id="s_saleProcessId" value="" />
							</dl>
						   <!-- 回收类型 -->
						   <dl class="select pos${empty mutilSearchCodeSortMap['opreateType'] ? '0' : mutilSearchCodeSortMap['opreateType']}"  data-dt-value="放弃类型">
								<dt>放弃类型</dt>
								<dd>
									<ul>
										<li><a href="###" onclick="$('#s_opreateType').val('');" title="放弃类型">放弃类型</a></li>
										<li><a href="###" onclick="$('#s_opreateType').val('11');" title="客户-到期未签约">客户-到期未签约</a></li>
										<li><a href="###" onclick="$('#s_opreateType').val('16');" title="客户-到期未跟进">客户-到期未跟进</a></li>
										<li><a href="###" onclick="$('#s_opreateType').val('12');" title="客户-主动放弃">客户-主动放弃</a></li>
										<li><a href="###" onclick="$('#s_opreateType').val('21');" title="签约客户-流失">签约客户-流失</a></li>
										<li><a href="###" onclick="$('#s_opreateType').val('23');" title="签约-到期未续签">签约-到期未续签</a></li>
										<li><a href="###" onclick="$('#s_opreateType').val('4');" title="资源-沟通失败">资源-沟通失败</a></li>
										<li><a href="###" onclick="$('#s_opreateType').val('5');" title="资源-信息错误">资源-信息错误</a></li>
										<li><a href="###" onclick="$('#s_opreateType').val('24');" title="资源-到期未联系">资源-到期未联系</a></li>
									</ul>
								</dd>
							   <input type="hidden" name="opreateType" id="s_opreateType" value=""/>
							</dl>
							<!-- 销售人员 -->
							<div class="reso-sub-dep fl_l pos${empty mutilSearchCodeSortMap['ownerAccsStr'] ? '0' : mutilSearchCodeSortMap['ownerAccsStr']}"  data-dt-value="销售人员">
								<input style="border-right: 2px solid #e1e1e1;" id="ownerNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="销售人员">
								<div class="manage-drop"  style="height:370px;">
									<div class="shows-radio-boxs"></div>
									<ul class="shows-allorme-boxs">
										<li><input type="radio"  value="1"  name="searchOwnerType" checked>查看全部</li>
										<li><input type="radio" name="searchOwnerType"  value="2">查看自己</li>
									</ul>
									<div class="sub-dep-ul" style="width:260px;" data-type="search-tree">
										<ul id="tt1">

						       			</ul>
						    		</div>
								    <div class="sure-cancle clearfix" style="width:120px">
										<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" onclick="seleTree()" href="###"><label>确定</label></a>
										<a class="com-btnd bomp-btna fl_l" id="close02" onclick="unCheckTree('tt1')" href="###"><label>清空</label></a>
									</div>
								</div>
								<input type="hidden" name="ownerAccsStr" value="" id="ownerAccsStr" />
								<input type="hidden" name="osType" value="1" id="osType" />
							</div>

							<!-- 放弃人 -->
							<div class="reso-sub-dep fl_l pos${empty mutilSearchCodeSortMap['updateAccsStr'] ? '0' : mutilSearchCodeSortMap['updateAccsStr']}"  data-dt-value="放弃人">
								<input style="border-right: 2px solid #e1e1e1;" id="updateNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="放弃人">
								<input type="hidden" name="updateAccsStr" value="" id="updateAccsStr" />
								<input type="hidden" name="usType" value="1" id="usType" />
								<div class="manage-drop"  style="height:370px;">
									<div class="shows-radio-boxs"></div>
									<ul class="shows-allorme-boxs">
										<li><input type="radio"  value="1"  name="searchUpdateType" checked>查看全部</li>
										<li><input type="radio" name="searchUpdateType"  value="2">查看自己</li>
									</ul>
									<div class="sub-dep-ul" style="width:260px;" data-type="search-tree">
										<ul id="tt2">

						       			</ul>
						    		</div>
								    <div class="sure-cancle clearfix" style="width:120px">
										<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" onclick="seleUpdateTree()" href="###"><label>确定</label></a>
										<a class="com-btnd bomp-btna fl_l" id="close02" onclick="unCheckTree('tt2')" href="###"><label>清空</label></a>
									</div>
								</div>
							</div>

<%-- 							<c:if test="${isOpenReason eq '1'}"> --%>
					   			<dl class="select pos${empty mutilSearchCodeSortMap['reason'] ? '0' : mutilSearchCodeSortMap['reason']}"  data-dt-value="资源放弃原因">
									<input type="hidden" name="reason" value="" id="reason" />
									<dt>资源放弃原因</dt>
									<dd>
										<ul>
											<li><a href="###" onclick="$('#reason').val('')" title="资源放弃原因">资源放弃原因</a></li>
											<c:forEach items="${reasons }" var="rs">
												<li class="reason-li"><a href="###" title="${rs }">${rs }</a></li>
											</c:forEach>
										</ul>
									</dd>
								</dl>
<%-- 					   		</c:if> --%>

							<dl class="select pos${empty mutilSearchCodeSortMap['companyTrade'] ? '0' : mutilSearchCodeSortMap['companyTrade']}" data-input="[name='companyTrade']" data-dt-value="所属行业">
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
						  			<dl class="select pos${empty mutilSearchCodeSortMap[defindeCode.searchCode] ? '0' : mutilSearchCodeSortMap[defindeCode.searchCode]}" data-dt-value="${defindeCode.searchName}">
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
						  			<dl class="select pos${empty mutilSearchCodeSortMap[defindeCode.searchCode] ? '0' : mutilSearchCodeSortMap[defindeCode.searchCode]}" data-input="[name='${defindeCode.searchCode }']" data-dt-value="${defindeCode.searchName}">
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
						  			<dl class="select pos${empty mutilSearchCodeSortMap[defindeCode.searchCode] ? '0' : mutilSearchCodeSortMap[defindeCode.searchCode]}" data-input="[name='${defindeCode.searchCode }']" data-multi="true" data-dt-value="${defindeCode.searchName}">
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
					<input class="query-button fl_r" type="button" value="高级查询"  id="superQuery" data-url="${ctx }/highSearch/toPage?module=8" data-title="高级查询" />
					<c:if test="${mutilSearchCodeSortMap.keySet().size() gt 5 }"><a href="javascript:;" class="more"><i></i>更多</a></c:if>
				</div>
				<p class="com-moTag"  data-input="actionTags">
					<label class="a">行动标签：</label>
					<span id="addLabelBtn" style="cursor: pointer;color: blue;" class="e">选择标签</span>
					<input id="allLabels" name="allLabels" type="hidden" value=""/>
					<input name="allLabelsName" type="hidden" value=""/>
				</p>
				<input type="hidden" name="notContactedType" value="1" />
				<p class="com-moTag" data-input="input[name=notContactedType]">
					<label class="a">未联系天数：</label>
					<a href="javascript:;" nt="1" class="e e-hover">全部</a>
					<a href="javascript:;" nt="2" class="e">7天</a>
					<a href="javascript:;" nt="3" class="e">30天</a>
					<a href="javascript:;" nt="4" class="e">90天</a>
					<a href="javascript:;" nt="5" class="e">90天以上</a>
				</p>
				<input type="hidden" name="noteType" value="1" />
				<p class="com-moTag" data-input="input[name=noteType]">
					<label class="a">在库时长：</label>
					<a href="javascript:;" nt="1" class="e e-hover">全部</a>
					<a href="javascript:;" nt="2" class="e">7天</a>
					<a href="javascript:;" nt="3" class="e">30天</a>
					<a href="javascript:;" nt="4" class="e">90天</a>
					<a href="javascript:;" nt="5" class="e">90天以上</a>
				</p>
				<div class="com-btnlist hyx-cm-btnlist fl_l">
						<c:if test="${shiroUser.issys eq 1 }">
							<a href="###" btn-type="auth" auth_id="HighSeasCust_reassign" module="6" id="distributeBtn" class="com-btna demoBtn_c fl_l"><i class="min-reass"></i><label class="lab-mar">重新分配</label></a>
							<a href="###" btn-type="auth" auth_id="HighSeasCust_delete" module="6" id="delBtn" class="com-btna demoBtn_b fl_l"><i class="min-dele"></i><label class="lab-mar">删&nbsp;&nbsp;&nbsp;&nbsp;除</label></a>
							<a href="###" btn-type="auth" auth_id="HighSeasCust_clear" id="cleanBtn" class="com-btna demoBtn_a fl_l"><i class="min-dustbin"></i><label class="lab-mar">清&nbsp;&nbsp;&nbsp;&nbsp;空</label></a>
							<a href="###" btn-type="auth" auth_id="HighSeasCust_recycleUnAssign" id="recyleBtn" <c:if test="${resCustInfoDto.isShareRes eq '1'}">style="display:none;"</c:if> class="com-btna demoBtn_d fl_l"><i class="min-back"></i><label class="lab-mar">回收待分配</label></a>
							<%-- <a href="###" id="progressBtn" <c:if test="${resCustInfoDto.isShareRes eq '1'}">style="display:none;"</c:if> class="com-btna fl_l"><i class="min-back"></i><label class="lab-mar">回收进度</label></a>
							<div class="progress_bar"></div> --%>
						</c:if>
						<c:if test="${isBatchRecyle eq '1' }">
								<a href="###" btn-type="auth" auth_id="HighSeasCust_batchRecycle" id="getBackBtn" class="com-btna demoBtn_c fl_l"><i class="min-back"></i><label class="lab-mar">取回资源</label></a>
						</c:if>
						<c:if test="${isBatchRecyleToCust eq '1' }">
								<a href="###" btn-type="auth" auth_id="HighSeasCust_BatchRecycleToInte" id="getBackBtnYx" class="com-btna demoBtn_c fl_l"><i class="min-will-back"></i><label class="lab-mar">取回意向</label></a>
						</c:if>
				</div>
				<div class="hid" style="display:block;">
					<div class="com-table com-mta test-table fl_l">
						<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
							<thead>
								<tr role="head" class="sty-bgcolor-b">
									<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
									<th><span class="sp sty-borcolor-b">操作</span></th>
									<th col_index="2" showType="high_seas" hidesort="true"><span class="sp sty-borcolor-b" sort="true">放弃时间</span></th>
									<th><span class="sp sty-borcolor-b">${shiroUser.isState eq 1 ? '客户名称':'单位名称' }</span></th>
									<th><span class="sp sty-borcolor-b">${shiroUser.isState eq 1 ? '联系人' : '客户姓名' }</span></th>
									<th><span class="sp sty-borcolor-b">联系电话</span></th>
									<th><span class="sp sty-borcolor-b" sort="true">销售进程</span></th>
									<th><span class="sp sty-borcolor-b">放弃类型</span></th>
									<th col_index="8" showType="high_seas"><span class="sp sty-borcolor-b">资源放弃原因</span></th>
									<th col_index="9" showType="high_seas"><span class="sp sty-borcolor-b">销售人员</span></th>
									<th col_index="10" showType="high_seas"><span class="sp sty-borcolor-b">放弃人</span></th>
									<th col_index="11" showType="high_seas"><span class="sp sty-borcolor-b">未联系天数</span></th>
									<th col_index="12" showType="high_seas"><span class="sp sty-borcolor-b">在库时长（天）</span></th>
									<th col_index="13" showType="high_seas"><span class="sp sty-borcolor-b">最近联系时间</span></th>
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
			</div>
		</div>
	</form>
</body>
</html>
