<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include-cut-version.jsp"%>
<%@ include file="/common/common-function/function.table.jsp"%>
<%@ include file="/common/common-function/function.date.jsp"%>
<%@ include file="/common/common-function/function.searchHead.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/ajax_common.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/custcommon.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/res/myCommonCusts.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/myCommonCustsData.js${_v}"></script>

<style type="text/css" rel="stylesheet">
<c:forEach items="${sorts}" var="sort">
	.com-table>table>tbody>tr>td:nth-child(${sort}),.com-table>table>thead>tr>th:nth-child(${sort}){display: none;}
</c:forEach>
</style>
</head>
<body>
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<form id="queryForm" action="${ctx }/res/cust/myCommonCusts" method="post">
		<div class="hyx-layout">
		<div class="hyx-cm-left hyx-layout-content">
			<div class="com-search hyx-cm-search">
				<div class="com-search-box fl_l">
				   <div class="search clearfix" >
					   <div class="select_outerDiv fl_l">
					   <span class="icon_down_5px"></span>
					   	<select name="queryType" class="fl_l search_head_select">
					   		<c:choose>
					   			<c:when test="${shiroUser.isState eq 1 }">
					   				<option value="1" selected>客户名称</option>
						   			<option value="2">联系人</option>
							   		<option value="3">联系电话</option>
					   			</c:when>
					   			<c:otherwise>
					   				<option value="4" selected>单位名称</option>
							   		<option value="1">客户姓名</option>
							   		<option value="3">联系电话</option>
					   			</c:otherwise>
					   		</c:choose>
<%-- 					   		<c:forEach items="${queryFileds }" var="field"> --%>
<%-- 					   			<option value="${field.fieldCode }">${field.fieldName }</option> --%>
<%-- 					   		</c:forEach> --%>
					   	</select>
					   	</div>
					   	<input class="input-query fl_l" type="text" name="queryText" value="" />
					   	<label class="hide-span"></label>
					  </div>
				   <div class="list-box">
					   <!-- 客户状态 -->
					   <input type="hidden" name="custType" value="" id="s_custType" />
					   <dl class="select">
							<dt>客户状态</dt>
							<dd>
								<ul>
									<li><a href="###" onclick="$('#s_custType').val('0');" title="客户类型">客户状态</a></li>
									<li><a href="###" onclick="$('#s_custType').val('2');" title="意向客户">意向客户</a></li>
									<li><a href="###" onclick="$('#s_custType').val('3');" title="签约客户">签约客户</a></li>
								</ul>
							</dd>
						</dl>

						<!-- 客户类型 -->
					    <input type="hidden" name="custTypeId" id="s_custTypeId" value="">
						<dl class="select" data-input="[name='custTypeId']">
							<dt>客户类型</dt>
							<dd>
								<ul>
									<li><a href="javascript:void(0);"  onclick="$('#s_custTypeId').val('');"  data-value="" title="客户类型">客户类型</a></li>
									<c:forEach items="${typeOptions }" var="ts">
										<li><a href="javascript:void(0);" onclick="$('#s_custTypeId').val('${ts.optionlistId}');"  data-value="${ts.optionlistId}" title="${ts.optionName }">${ts.optionName }</a></li>
									</c:forEach>
								</ul>
							</dd>
					    </dl>

						<!-- 销售进程 -->
						<input type="hidden" name="saleProcessId" id="s_saleProcessId" value="" />
						<dl class="select">
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

						<!-- 资源分组 -->
					    <dl class="select resGroup" data-input="[name='resGroupId']" >
							<dt>资源分组</dt>
							<dd>
								<ul class="resource-group-tree" data-url="${ctx}/res/group/get_res_group_json">
									<!-- 部门树 -->
								</ul>
							</dd>
							<input name="resGroupId" type="hidden" value="" id="s_groupId">
				  	    </dl>

				  	    <!-- 所有者 -->
						<div class="reso-sub-dep fl_l">
	                        <input id="commonOwnerNameStr" readonly="readonly" class="owner-sour" type="text" value="所有者">
	                        <input id="commonAccsStr" name="commonAccsStr" type="hidden" value="">
	                        <div class="manage-owner-sour"  style="height:370px;">
	                          <div class="sub-dep-ul" style="width:190px;" data-type="search-tree">
	                            <ul id="tt2">

	                            </ul>
	                            </div>
	                            <div class="sure-cancle clearfix" style="width:120px">
	                            <a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" onclick="seleCommonAccTree()" href="###"><label>确定</label></a>
	                            <a class="com-btnd bomp-btna fl_l" id="close02" onclick="unCheckTree('tt2')" href="###"><label>清空</label></a>
	                          </div>
	                        </div>
                        </div>

						<div class="list-hid fl_l">
							<!-- 最近联系时间 -->
							<input type="hidden" name="startActionDate" id="s_startActionDate" value="" />
						    <input type="hidden" name="endActionDate" id="s_endActionDate" value=""/>
							<input type="hidden" name="lDateType" id="lDateType" value="" />
							<dl class="select prec-cont-time">
								<dt>最近联系时间</dt>
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
						    <input type="hidden" name="pstartDate" id="s_pstartDate" value="" />
						    <input type="hidden" name="pendDate" id="s_pendDate" value=""/>
						    <input type="hidden" name="oDateType" id="oDateType" value="" />
							<dl class="select prec-cont-time">
								<dt>下次联系时间</dt>
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
							<!-- 签约时间-->
							<input type="hidden" name="startDate" id="s_startDate" value="" />
						    <input type="hidden" name="endDate" id="s_endDate" value=""/>
							<input type="hidden" name="nDateType" id="nDateType" value="" />
							<dl class="select prec-cont-time">
								<dt>签约时间</dt>
								<dd>
									<ul>
										<li><a href="javascript:;" onclick="setSignDate(0)" title="签约时间">签约时间</a></li>
										<li><a href="javascript:;" onclick="setSignDate(1)" title="当天">当天</a></li>
										<li><a href="javascript:;" onclick="setSignDate(2)" title="本周">本周</a></li>
										<li><a href="javascript:;" onclick="setSignDate(3)" title="本月">本月</a></li>
										<li><a href="javascript:;" onclick="setSignDate(4)" title="半年">半年</a></li>
										<li><a href="javascript:;" title="自定义" id="signDate" class="diy date-range">自定义</a></li>
									</ul>
								</dd>
							</dl>

						</div>
					</div>
				</div>
				<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query" />
				<a href="javascript:;" class="more"><i></i>更多</a>
			</div>
			<input type="hidden" id="noteType" value="1" name="noteType" />
			<p class="com-moTag" id="res-tag">
				<label class="a">数据分类：</label>
				<a href="javascript:viod(0);" nt="1" class="e e-hover">全部</a>
				<a href="javascript:viod(0);" nt="2" class="e">今日已联系</a>
				<a href="javascript:viod(0);" nt="3" class="e">今日待联系</a>
				<a href="javascript:viod(0);" nt="4" class="e">本周已联系</a>
				<a href="javascript:viod(0);" nt="5" class="e">7天未联系</a>
				<a href="javascript:viod(0);" nt="6" class="e">30天未联系</a>
			</p>
			<p class="com-moTag">
				<label class="a">行动标签：</label>
				<span id="addLabelBtn" style="cursor: pointer;color: blue;" class="e">选择标签</span>
				<input id="allLabels" name="allLabels" type="hidden" value=""/>
			</p>
			<div class="com-btnlist hyx-cm-btnlist fl_l">
				<a href="###" <c:if test="${shiroUser.serverDay gt 0}">onclick="toBatchSmsSend()"</c:if> class="${shiroUser.serverDay gt 0 ? 'com-btna' : 'com-btna-no' } demoBtn_e fl_l"><i class="min-messag ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }"></i><label class="lab-mar">发送短信</label></a>
				<a href="###" <c:if test="${shiroUser.serverDay gt 0}">onclick="window.top.addTab('${ctx }/contract/list?noteType=2','共有者-合同管理')"</c:if> class="${shiroUser.serverDay gt 0 ? 'com-btna' : 'com-btna-no' } demoBtn_e fl_l"><i class="min-word-temp ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }"></i><label class="lab-mar">合同订单</label></a>
			</div>
			<input type="hidden" name="orderKey" id="orderKey" value="" />
			<div class="com-table com-mta hyx-cm-table fl_l" style="display:block;">
				<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
					<thead>
						<tr role="head" class="sty-bgcolor-b">
							<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
							<th><span class="sp sty-borcolor-b">操作</span></th>
							<th><span class="sp sty-borcolor-b">${shiroUser.isState eq 1 ? '客户名称':'客户姓名' }</span></th>
							<c:if test="${shiroUser.isState ne 1 }">
								<th><span class="sp sty-borcolor-b">单位名称</span></th>
							</c:if>
							<th><span class="sp sty-borcolor-b">资源分组</span></th>
							<th><span class="sp sty-borcolor-b">客户类型</span></th>
							<th><span class="sp sty-borcolor-b">客户状态</span></th>
							<th><span class="sp sty-borcolor-b">销售进程</span></th>
							<th hidesort="true"><span class="sp sty-borcolor-b" sortColumn="ACTION_DATE" sort="true">最近联系<!-- <span class="td_sort_asc"></span> --></span></th>
							<th hidesort="true"><span class="sp sty-borcolor-b" sortColumn="NEXTFOLLOW_DATE" sort="true">下次联系时间<!-- <span class="td_sort_asc"></span> --></span></th>
							<th><span class="sp sty-borcolor-b" sortColumn="SIGN_DATE" sort="true">签约时间</span></th>
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
