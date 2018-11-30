<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include-cut-version.jsp"%>
<%@ include file="/common/common-function/function.table.jsp"%>
<%@ include file="/common/common-function/function.date.jsp"%>
<%@ include file="/common/common-function/function.searchHead.jsp"%>
<style>
	.com-moTag .a{width:72px;text-align:right;}/* 为了适应“未联系天数”字段的长度 */
</style>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/ajax_common.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/myLosingCustData.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/custcommon.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/res/mySilentCust.js${_v}"></script>
</head>
<body>
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<input type="hidden" id="isState" value="${shiroUser.isState }" />
	<form id="queryForm" action="${ctx }/res/cust/myLosingCusts" method="post">
		<div class="hyx-layout">
	<!--资源-->
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
					    <!-- 上次联系时间 -->
						<input type="hidden" name="startActionDate" id="s_startActionDate" value="" />
					    <input type="hidden" name="endActionDate" id="s_endActionDate" value=""/>
						<input type="hidden" name="lDateType" id="lDateType" value="" />
						<dl class="select prec-cont-time">
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
					   <dl class="select prec-cont-time">
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
						<dl class="select">
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
								<div class="reso-sub-dep fl_l">
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
						</c:choose>

						<dl class="select resGroup prec-cont-time" data-input="[name='resGroupId']">
							<dt>资源分组</dt>
							<dd>
								<ul class="resource-group-tree" data-url="${ctx}/res/group/get_res_group_json">
									<!-- 部门树 -->
								</ul>
							</dd>
							<input type="hidden" name="resGroupId" id="s_groupId" value="" />
						</dl>
					</div>
				</div>
				<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query" />
			</div>
			<input type="hidden" id="noteType" value="1" name="noteType" />
			<p class="com-moTag">
				<label class="a" >未联系天数：</label>
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
				<c:forEach items="${labelNamesList }" var="ln">
					<span label="1" class="e">${ln }</span>
				</c:forEach>
				<span id="addLabelBtn" style="cursor: pointer;color: blue;" class="e">选择标签</span>
				<input id="allLabels" name="allLabels" type="hidden" value="${resCustInfoDto.allLabels }"/>
			</p>
			<div class="com-btnlist hyx-cm-btnlist fl_l" style="margin-top:12px;">
				<a href="javascript:;" <c:if test="${shiroUser.serverDay gt 0}">onclick="toBatchSmsSend()"</c:if> class="${shiroUser.serverDay gt 0 ? 'com-btna' : 'com-btna-no' } demoBtn_e fl_l"><i class="min-messag ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }"></i><label class="lab-mar">发送短信</label></a>
			</div>
			<input type="hidden" name="orderKey" id="orderKey" value="" />
			<div class="com-table com-mta hyx-cm-table fl_l" style="display:block;">
				<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
					<thead>
						<tr role="head" class="sty-bgcolor-b">
							<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
							<th><span class="sp sty-borcolor-b">操作</span></th>
							<th><span class="sp sty-borcolor-b">${shiroUser.isState eq 1 ? '客户名称':'客户姓名' }</span></th>
<%-- 							<c:if test="${shiroUser.issys eq 1 }"> --%>
<!-- 								<th><span class="sp sty-borcolor-b">联系人</span></th> -->
<%-- 							</c:if> --%>
							<c:if test="${shiroUser.isState ne 1 }">
								<th><span class="sp sty-borcolor-b">单位名称</span></th>
							</c:if>
							<th><span class="sp sty-borcolor-b">资源分组</span></th>
							<th><span class="sp sty-borcolor-b">流失原因</span></th>
<!-- 							<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>订单累计金额（万）</span><i></i></label></span></th> -->
							<th><span class="sp sty-borcolor-b" sortColumn="CONTRACT_END_DATE" sort="true">合同到期时间<!-- <span class="td_sort_asc"></span> --></span></th>
							<th><span class="sp sty-borcolor-b" sortColumn="DAYS" sort="true">未联系天数<!-- <span class="td_sort_asc"></span> --></span></th>
							<th hidesort="true"><span class="sp sty-borcolor-b" sortColumn="ACTION_DATE" sort="true">上次联系时间<!-- <span class="td_sort_asc"></span> --></span></th>
							<th><span class="sp sty-borcolor-b">备注</span></th>
<!-- 							<th><span class="sp sty-borcolor-b">资源分组</span></th> -->
							<th><span class="sp sty-borcolor-b">所有者</span></th>
							<%-- <c:choose>
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
							</c:choose> --%>
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
