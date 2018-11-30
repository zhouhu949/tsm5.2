<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/common-function/function.area.select.jsp"%>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tree_top_part.css${_v}"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/progressBar/progress_detail.css${_v}"/>
<style>
	.com-moTag .a{width:72px;text-align:right;}/* 为了适应“未联系天数”字段的长度 */
</style>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/page.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/ajax_common.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/losingCustManageData.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/custcommon.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/res/mySilentCust.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
<%-- <script type="text/javascript" src="${ctx }/static/js/progressBar/progress_detail.js"></script> --%>
<script type="text/javascript" src="${ctx }/static/js/common/button_auth.js${_v}"></script>
<script type="text/javascript">
$(function(){
	$("#progressBtn").click(function(e){
		e.stopPropagation();
		appendtos("progress_bar","LOSING_CUST_RECYLE");
		$(".progress_bar").show();
	});
});
</script>
<style type="text/css" rel="stylesheet">
<c:forEach items="${sorts}" var="sort">
	.com-table>table>tbody>tr>td:nth-child(${sort}),.com-table>table>thead>tr>th:nth-child(${sort}){display: none;}
</c:forEach>
</style>
</head>
<body>
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<input type="hidden" id="isState" value="${shiroUser.isState }" />
	<form id="queryForm" action="${ctx }/res/cust/myLosingCusts" method="post">
	<input type="hidden" value="manage" name="pageView" />
	<!--资源-->
		<div class="hyx-layout">
		<div class=" hyx-cm-left hyx-layout-content">
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
					   	<input class="input-query fl_l" type="text" id="queryText" name="queryText" value="" /><label class="hide-span"></label>
				   </div>
				   <div class="list-box">
					    <!-- 上次联系时间 -->
						<input type="hidden" name="startActionDate" id="s_startActionDate" value="" />
					    <input type="hidden" name="endActionDate" id="s_endActionDate" value=""/>
						<input type="hidden" name="lDateType" id="lDateType" value="" />
						<dl class="select pos${empty mutilSearchCodeSortMap['startActionDate'] ? '0' : mutilSearchCodeSortMap['startActionDate']}">
							<dt>上次联系时间</dt>
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
					   <input type="hidden" name="pstartDate" id="s_pstartDate" value="" />
					   <input type="hidden" name="pendDate" id="s_pendDate" value=""/>
					   <input type="hidden" name="oDateType" id="oDateType" value="" />
					   <dl class="select pos${empty mutilSearchCodeSortMap['pstartDate'] ? '0' : mutilSearchCodeSortMap['pstartDate']}">
							<dt>合同截止时间</dt>
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
						<input name="losingId" id="s_losingId" value="" type="hidden" />
						<dl class="select pos${empty mutilSearchCodeSortMap['losingId'] ? '0' : mutilSearchCodeSortMap['losingId']}">
							<dt>流失原因</dt>
							<dd>
								<ul>
									<li><a href="###" onclick="$('#s_losingId').val('')" title="流失原因">流失原因</a></li>
									<c:forEach var="opt" items="${options }">
										<li><a href="###" onclick="$('#s_losingId').val('${opt.optionlistId }')" title="${opt.optionName }">${opt.optionName }</a></li>
									</c:forEach>
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
				  	     <input type="hidden" name="custTypeId" id="s_custTypeId" value="">
						   <dl class="select pos${empty mutilSearchCodeSortMap['custTypeId'] ? '0' : mutilSearchCodeSortMap['custTypeId']}" data-input="[name='custTypeId']">
								<dt>客户类型</dt>
								<dd>
									<ul>
										<li><a href="javascript:void(0);"  onclick="$('#s_custTypeId').val('');" data-value="" title="客户类型">客户类型</a></li>
										<c:forEach items="${typeOptions }" var="ts">
											<li><a href="javascript:void(0);"  onclick="$('#s_custTypeId').val('${ts.optionlistId}');"  data-value="${ts.optionlistId}" title="${ts.optionName }">${ts.optionName }</a></li>
										</c:forEach>
									</ul>
								</dd>
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
				<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query" />
				<input class="query-button fl_r" type="button" value="高级查询"  id="superQuery" data-url="${ctx }/highSearch/toPage?module=12" data-title="高级查询" />
				<c:if test="${mutilSearchCodeSortMap.keySet().size() gt 5 }"><a href="javascript:;" class="more"><i></i>更多</a></c:if>
			</div>
			<input type="hidden" id="noteType" value="1" name="noteType" />
			<p class="com-moTag">
				<label class="a">未联系天数：</label>
				<a href="javascript:;" nt="1" class="e e-hover">全部</a>
				<a href="javascript:;" nt="2" class="e">7天</a>
				<a href="javascript:;" nt="3" class="e">30天</a>
				<a href="javascript:;" nt="4" class="e">90天</a>
				<a href="javascript:;" nt="5" class="e">90天以上</a>
<%-- 				<input type="text" id="days" name="days" value="${resCustInfoDto.days }" class="diy-box" /> --%>
<!-- 				<label class="diy-day">天</label> -->
			</p>
			<p class="com-moTag">
				<label class="a">行动标签：</label>
				<span id="addLabelBtn" style="cursor: pointer;color: blue;" class="e">选择标签</span>
				<input id="allLabels" name="allLabels" type="hidden" value=""/>
			</p>
			<div class="com-btnlist hyx-cm-btnlist fl_l" style="margin-top:12px;">
				<a href="javascript:;" btn-type="auth" auth_id="base_putIntoHighSeas" id="giveUpBtn" class="com-btna demoBtn_a fl_l"><i class="min-turn-will"></i><label class="lab-mar">放入公海</label></a>
				<a href="javascript:;"  btn-type="auth" auth_id="losingCust_delete" module="5" id="delBtn" class="com-btna demoBtn_b fl_l"><i class="min-dele"></i><label class="lab-mar">删&nbsp;&nbsp;&nbsp;&nbsp;除</label></a>
				<a href="javascript:;"  btn-type="auth" auth_id="losingCust_reassign" module="5" id="distributeBtn" class="com-btna demoBtn_c fl_l"><i class="min-reass"></i><label class="lab-mar">重新分配</label></a>
				<a href="javascript:;"  btn-type="auth" auth_id="losingCust_recycleUnAssign" id="recyleBtn" class="com-btna demoBtn_d fl_l"><i class="min-back"></i><label class="lab-mar">回收待分配</label></a>
				<!-- <a href="javascript:;" id="progressBtn" class="com-btna demoBtn_d fl_l"><i class="min-back"></i><label class="lab-mar">回收进度</label></a>
				<div class="progress_bar"></div> -->
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
							<th><span class="sp sty-borcolor-b">流失原因</span></th>
							<th><span class="sp sty-borcolor-b">合同到期</span></th>
							<th><span class="sp sty-borcolor-b" sortColumn="DAYS" sort="true">未联系天数<!-- <span class="td_sort_asc"></span> --></span></th>
							<th><span class="sp sty-borcolor-b">所有者</span></th>
							<th hidesort="true"><span class="sp sty-borcolor-b" sortColumn="ACTION_DATE" sort="true">上次联系<!-- <span class="td_sort_asc"></span> --></span></th>
							<th><span class="sp sty-borcolor-b">客户类型</span></th>
							<th><span class="sp sty-borcolor-b">资源分组</span></th>
							<th><span class="sp sty-borcolor-b">备注</span></th>
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
					<!-- 加载层 -->
					<div class="tip_search_data" >
						<div class="tip_search_text">
							<span><img alt="加载中" src="${ctx }/static/images/hua_loading.gif" /></span>
							<span>数据加载中…</span>
						</div>
						<div class="tip_search_error"></div>
					</div>
				</table>
			</div>
			<div class="com-bot" >
				<div class="com-page fl_r">
					<div class="page" id="new_page"></div>
					<div class="clr_both"></div>
				</div>
			</div>
		</div>
		<div id="custRight" class="hyx-cm-right hyx-layout-side">

		</div>

		</div>
	</form>
</body>
</html>
