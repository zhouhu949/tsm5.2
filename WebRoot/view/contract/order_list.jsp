<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <style type="text/css"></style>
    <%@ include file="/common/include.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/tree_top_part.css${_v}"/>
    <script type="text/javascript" src="${ctx}/static/js/view/contract/order_list.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/page.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/view/bill/render_table.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/common/button_auth.js${_v}"></script>
</head>
<body>
<div class="year-sale-play">
    <ul class="ann-sale-plan clearfix">
        <li class="li_first" onclick="window.location.href='${ctx}/contract/list?noteType=${contractOrderDto.noteType }';">合同维护</li>
        <li class="select">订单维护</li>
        <li class="li_last" onclick="window.location.href='${ctx}/contract/orderinfo/list?noteType=${contractOrderDto.noteType }';">订单详情</li>
    </ul>
</div>
     <form id="orderManageForm" action="${ctx }/contract/order/list" method="post" data-render-url="${ctx }/contract/order/data">
     	 <input type="hidden" name="noteType"  value="${contractOrderDto.noteType }"  id="noteType" /> 
     	<input type="hidden" name="isPageSearch" value="1" />
        <div class="hyx-cfu-left hyx-ctr hyx-sc">
            <div class="com-search hyx-cfu-search">
                <div class="com-search-box fl_l">
                    <div class="search clearfix" >
					    <div class="select_outerDiv fl_l">
					    <span class="icon_down_5px"></span>
						   	<select name="queryType" class="fl_l search_head_select">
						   		<c:if test="${shiroUser.isState eq 0}">
						   			<option value="4">单位名称</option>
						   		</c:if>
						   		<option value="1">${shiroUser.isState eq 1 ? '客户名称' : '客户姓名' }</option>
						   		<option value="2">订单号</option>
						   		<option value="3">物流单号</option>
						   	</select>
					   	</div>
                    	<input class="input-query fl_l" type="text" name="queryText" value="${contractOrderDto.queryText }" /><label class="hide-span"></label>
                    </div>
                    <div class="list-box">
                        <!-- 交易日期 -->
						<input type="hidden" name="startDate" id="s_startDate" value="${contractOrderDto.startDate }" />
						<input type="hidden" name="endDate" id="s_endDate" value="${contractOrderDto.endDate }" />
						<input type="hidden" name="sDateType" id="s_sDateType" value="${contractOrderDto.sDateType }" />
		                <c:set var="sDateName" value="交易日期" />
					    <c:choose>
					   		<c:when test="${contractOrderDto.sDateType eq 1 }"><c:set var="sDateName" value="当天" /></c:when>
					   		<c:when test="${contractOrderDto.sDateType eq 2 }"><c:set var="sDateName" value="本周" /></c:when>
					   		<c:when test="${contractOrderDto.sDateType eq 3 }"><c:set var="sDateName" value="本月" /></c:when>
					   		<c:when test="${contractOrderDto.sDateType eq 4 }"><c:set var="sDateName" value="半年" /></c:when>
					   		<c:when test="${contractOrderDto.sDateType eq 5 }">
					   			<fmt:parseDate var="sd" value="${contractOrderDto.startDate }" pattern="yyyy-MM-dd" />
					   			<fmt:parseDate var="ed" value="${contractOrderDto.endDate }" pattern="yyyy-MM-dd" />
					   			<c:set var="sDateName">
					   				<fmt:formatDate value="${sd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ed }" pattern="yy.MM.dd"/>
					   			</c:set>
					   		</c:when>
					    </c:choose>
	                    <dl class="select">
	                        <dt>${sDateName }</dt>
	                        <dd>
	                            <ul>
	                                <li><a href="###" onclick="setSdate(0)" title="交易日期">交易日期</a></li>
	                                <li><a href="###" onclick="setSdate(1)" title="当天">当天</a></li>
	                                <li><a href="###" onclick="setSdate(2)" title="本周">本周</a></li>
	                                <li><a href="###" onclick="setSdate(3)" title="本月">本月</a></li>
	                                <li><a href="###" onclick="setSdate(4)" title="半年">半年</a></li>
	                                <li><a href="###" title="自定义" class="diy date-range" id="d1">自定义</a></li>
	                            </ul>
	                        </dd>
	                    </dl>
                        <!-- 生效日期 -->
	                    <input type="hidden" name="startEffecDate" id="s_startEffecDate" value="${contractOrderDto.startEffecDate }" />
						<input type="hidden" name="endEffecDate" id="s_endEffecDate" value="${contractOrderDto.endEffecDate }" />
						<input type="hidden" name="eDateType" id="s_eDateType" value="${contractOrderDto.eDateType }" />
		                <c:set var="eDateName" value="生效日期" />
		                <c:choose>
					   		<c:when test="${contractOrderDto.eDateType eq 1 }"><c:set var="eDateName" value="当天" /></c:when>
					   		<c:when test="${contractOrderDto.eDateType eq 2 }"><c:set var="eDateName" value="本周" /></c:when>
					   		<c:when test="${contractOrderDto.eDateType eq 3 }"><c:set var="eDateName" value="本月" /></c:when>
					   		<c:when test="${contractOrderDto.eDateType eq 4 }"><c:set var="eDateName" value="半年" /></c:when>
					   		<c:when test="${contractOrderDto.eDateType eq 5 }">
					   			<fmt:parseDate var="sed" value="${contractOrderDto.startEffecDate }" pattern="yyyy-MM-dd" />
					   			<fmt:parseDate var="eed" value="${contractOrderDto.endEffecDate }" pattern="yyyy-MM-dd" />
					   			<c:set var="eDateName">
					   				<fmt:formatDate value="${sed }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${eed }" pattern="yy.MM.dd"/>
					   			</c:set>
					   		</c:when>
					    </c:choose>
	                    <dl class="select">
	                        <dt>${eDateName }</dt>
	                        <dd>
	                            <ul>
	                                <li><a href="###" onclick="setEdate(0)" title="生效日期">生效日期</a></li>
	                                <li><a href="###" onclick="setEdate(1)" title="当天">当天</a></li>
	                                <li><a href="###" onclick="setEdate(2)" title="本周">本周</a></li>
	                                <li><a href="###" onclick="setEdate(3)" title="本月">本月</a></li>
	                                <li><a href="###" onclick="setEdate(4)" title="半年">半年</a></li>
	                                <li><a href="###" title="自定义" class="diy date-range" id="d2">自定义</a></li>
	                            </ul>
	                        </dd>
	                    </dl>
                        <!-- 失效日期 -->
	                    <input type="hidden" name="startInvalidDate" id="s_startInvalidDate" value="${contractOrderDto.startInvalidDate }" />
						<input type="hidden" name="endInvalidDate" id="s_endInvalidDate" value="${contractOrderDto.endInvalidDate }" />
						<input type="hidden" name="iDateType" id="s_iDateType" value="${contractOrderDto.iDateType }" />
		                <c:set var="iDateName" value="失效日期" />
		                <c:choose>
					   		<c:when test="${contractOrderDto.iDateType eq 1 }"><c:set var="iDateName" value="当天" /></c:when>
					   		<c:when test="${contractOrderDto.iDateType eq 2 }"><c:set var="iDateName" value="本周" /></c:when>
					   		<c:when test="${contractOrderDto.iDateType eq 3 }"><c:set var="iDateName" value="本月" /></c:when>
					   		<c:when test="${contractOrderDto.iDateType eq 4 }"><c:set var="iDateName" value="半年" /></c:when>
					   		<c:when test="${contractOrderDto.iDateType eq 5 }">
					   			<fmt:parseDate var="sid" value="${contractOrderDto.startInvalidDate }" pattern="yyyy-MM-dd" />
					   			<fmt:parseDate var="eid" value="${contractOrderDto.endInvalidDate }" pattern="yyyy-MM-dd" />
					   			<c:set var="iDateName">
					   				<fmt:formatDate value="${sid }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${eid }" pattern="yy.MM.dd"/>
					   			</c:set>
					   		</c:when>
					    </c:choose>
	                    <dl class="select">
	                        <dt>${iDateName }</dt>
	                        <dd>
	                            <ul>
	                                <li><a href="###" onclick="setIdate(0)" title="失效日期">失效日期</a></li>
	                                <li><a href="###" onclick="setIdate(1)" title="当天">当天</a></li>
	                                <li><a href="###" onclick="setIdate(2)" title="本周">本周</a></li>
	                                <li><a href="###" onclick="setIdate(3)" title="本月">本月</a></li>
	                                <li><a href="###" onclick="setIdate(4)" title="半年">半年</a></li>
	                                <li><a href="###" title="自定义" class="diy date-range" id="d3">自定义</a></li>
	                            </ul>
	                        </dd>
	                    </dl>
	                    
						<div class="reso-sub-dep fl_l">
	                        <input id="inputerName" readonly="readonly" class="owner-sour" type="text" value="提交人">
	                        <input id="userIdsStr" name="userIdsStr" type="hidden" value="${contractOrderDto.userIdsStr }">
	                        <div class="manage-owner-sour"  style="height:370px;">
	                          <div class="sub-dep-ul" style="width:190px;" >
	                            <ul id="tt2">
	
	                            </ul>
	                            </div>
	                            <div class="sure-cancle clearfix" style="width:120px">
	                            <a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" onclick="seleInputerTree()" href="###"><label>确定</label></a>
	                            <a class="com-btnd bomp-btna fl_l" id="close02" onclick="unCheckTree('tt2')" href="###"><label>清空</label></a>
	                          </div>
	                        </div>
	                    </div>
	                    
	                    <c:if test="${shiroUser.issys eq 1 }">
	                        <input type="hidden" name="ownerAccStr" id="ownerAccStr" value="${contractOrderDto.ownerAccStr }" />
	                        <input type="hidden" name="osType" value="${contractOrderDto.osType }" id="osType" />
    							<div class="reso-sub-dep fl_l">
    								<input id="ownerNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="所有者">
    								<div class="manage-drop"  style="height:370px;">
    									<div class="shows-radio-boxs"></div>
    									<ul class="shows-allorme-boxs">
    										<li><input type="radio"  value="1"  name="searchOwnerType" ${contractOrderDto.osType eq '1' ? 'checked':'' }>查看全部</li>
    										<li><input type="radio" name="searchOwnerType"  value="2" ${contractOrderDto.osType eq '2' ? 'checked':'' }>查看自己</li>
    									</ul>
    									<div class="sub-dep-ul" style="width:190px;" data-type="search-tree">
    										<ul id="tt1">

    						       			</ul>
    						    		</div>
    								    <div class="sure-cancle clearfix" style="width:120px">
    										<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" onclick="seleTree()" href="###"><label>确定</label></a>
    										<a class="com-btnd bomp-btna fl_l" id="close02" onclick="unCheckTree('tt1')" href="###"><label>清空</label></a>
    									</div>
    								</div>
    							</div>
                        </c:if>
                        <c:choose>
                         <c:when test="${shiroUser.issys ne 1 }">
	                        <input type="hidden" name="authState" id="s_authState" value="" />
	                        <dl class="select">
	                            <dt>订单状态</dt>
	                            <dd>
	                                <ul>
	                                    <li><a href="###" onclick="$('#s_authState').val('');" title="订单状态">订单状态</a></li>
	                                    <li><a href="###" onclick="$('#s_authState').val('0');" title="未上报">未上报</a></li>
	                                    <li><a href="###" onclick="$('#s_authState').val('1');" title="待审核">待审核</a></li>
	                                    <li><a href="###" onclick="$('#s_authState').val('2');" title="生效中">生效中</a></li>
	                                    <li><a href="###" onclick="$('#s_authState').val('3');" title="驳回">驳回</a></li>
	                                    <li><a href="###" onclick="$('#s_authState').val('4');" title="作废">作废</a></li>
	                                    <li><a href="###" onclick="$('#s_authState').val('5');" title="已失效">已失效</a></li>
	                                </ul>
	                            </dd>
	                        </dl>
	                        
	                        <input id="s_courierStatus" type="hidden" name="courierStatus" value="">
	                        <!-- 物流状态 1 暂无记录 2 在途中 3 派送中 4 已签收 5 用户拒签 6 疑难件 7 无效单8 超时单 9 签收失败 10 退回 -->
	                        <dl class="select list-hid" style="display:none;">
	                            <dt>物流状态</dt>
	                            <dd>
	                                <ul>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('');" title="订单状态">物流状态</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('1');" title="暂无记录">暂无记录</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('2');" title="在途中">在途中</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('3');" title="派送中">派送中</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('4');" title="已签收">已签收</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('5');" title="用户拒签">用户拒签</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('6');" title="疑难件">疑难件</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('7');" title="无效单">无效单</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('8');" title="超时单">超时单</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('9');" title="签收失败">签收失败</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('10');" title="退回">退回</a></li>
	                                </ul>
	                            </dd>
	                        </dl>
	                        
                        </c:when>
                        <c:otherwise>
                        	<input type="hidden" name="authState" id="s_authState" value="" />
	                        <dl class="select list-hid" style="display:none;">
	                            <dt>订单状态</dt>
	                            <dd>
	                                <ul>
	                                    <li><a href="###" onclick="$('#s_authState').val('');" title="订单状态">订单状态</a></li>
	                                    <li><a href="###" onclick="$('#s_authState').val('0');" title="未上报">未上报</a></li>
	                                    <li><a href="###" onclick="$('#s_authState').val('1');" title="待审核">待审核</a></li>
	                                    <li><a href="###" onclick="$('#s_authState').val('2');" title="生效中">生效中</a></li>
	                                    <li><a href="###" onclick="$('#s_authState').val('3');" title="驳回">驳回</a></li>
	                                    <li><a href="###" onclick="$('#s_authState').val('4');" title="作废">作废</a></li>
	                                    <li><a href="###" onclick="$('#s_authState').val('5');" title="已失效">已失效</a></li>
	                                </ul>
	                            </dd>
	                        </dl>
	                        
	                        <input id="s_courierStatus" type="hidden" name="courierStatus" value="">
	                        <!-- 物流状态 1 暂无记录 2 在途中 3 派送中 4 已签收 5 用户拒签 6 疑难件 7 无效单8 超时单 9 签收失败 10 退回 -->
	                        <dl class="select list-hid" style="display:none;">
	                            <dt>物流状态</dt>
	                            <dd>
	                                <ul>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('');" title="订单状态">订单状态</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('1');" title="暂无记录">暂无记录</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('2');" title="在途中">在途中</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('3');" title="派送中">派送中</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('4');" title="已签收">已签收</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('5');" title="用户拒签">用户拒签</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('6');" title="疑难件">疑难件</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('7');" title="无效单">无效单</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('8');" title="超时单">超时单</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('9');" title="签收失败">签收失败</a></li>
	                                    <li><a href="###" onclick="$('#s_courierStatus').val('10');" title="退回">退回</a></li>
	                                </ul>
	                            </dd>
	                        </dl>
	                        
                        </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <input class="query-button fl_r" id="query" type="button" value="查&nbsp;询" />
                 <c:if test="${shiroUser.issys eq 1 }">
                 	<a href="javascript:;" class="more"><i></i>更多</a>
                 </c:if>
            </div>
            
            
            <div class="com-btnlist hyx-cm-btnlist">
	            <a href="javascript:;"  btn-type="auth" auth_id="contractOrder_deleteOrder"  id="delBtn" class="com-btna alert_sys_pro_set_a_b fl_l"><i class="com_btn min-dele"></i><label class="lab-mar">删&nbsp;&nbsp;&nbsp;&nbsp;除</label></a>
            	<a href="javascript:;" id="export" class="com-btna alert_sys_pro_set_a_b fl_l"><i class="min-remove-resou"></i><label class="lab-mar">导&nbsp;&nbsp;&nbsp;&nbsp;出</label></a>
            </div>
            <div class="com-table com-mta hyx-cla-table">
                <table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table" data-form="orderManage">
                    <thead>
                    <tr role="head" class="sty-bgcolor-b">
                        <th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
                        <th><span class="sp sty-borcolor-b">操作</span></th>
                        <th><span class="sp sty-borcolor-b" sort="true">交易日期</span></th>
                        <th><span class="sp sty-borcolor-b">${shiroUser.isState eq 1 ? '客户名称':'客户姓名' }</span></th>
	                    <c:if test="${shiroUser.isState eq 0  }">
	                    	<th><span class="sp sty-borcolor-b">单位名称</span></th>
	                    </c:if>
                        <th><span class="sp sty-borcolor-b">订单号</span></th>
                        <th><span class="sp sty-borcolor-b">交易金额(元)</span></th>
                        <th><span class="sp sty-borcolor-b">提交人</span></th>
                        <th><span class="sp sty-borcolor-b">所有者</span></th>
                        <th><span class="sp sty-borcolor-b">订单状态</span></th>
                        <th><span class="sp sty-borcolor-b">物流单号</span></th>
                        <th><span class="sp sty-borcolor-b">物流状态</span></th>
                        <th><span class="sp sty-borcolor-b" sort="true">生效日期</span></th>
                        <th><span class="sp sty-borcolor-b" sort="true">失效日期</span></th>
                        <th>审核备注</th>
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
	                </tbody>
	            </table>
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
			<!-- 新增代码开始 -->
			<div id="countDiv" class="hyx-cmb-list">
				<span class="sp">订单总额：</span><label class="lab">0.00万</label>
				<span class="sp">通过金额：</span><label class="lab">0.00万</label>
				<span class="sp">待审金额：</span><label class="lab">0.00万</label>
				<span class="sp">驳回金额：</span><label class="lab">0.00万</label>
				<span class="sp">未上报：</span><label class="lab">0.00万</label>
			</div>
			<!-- 新增代码结束 -->
		</div>
	</form>
<script type="text/x-handlebars-template" id="template">
	{{#each list}}
	<tr class="{{even_odd @index}}">
        <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" id="chk_{{id }}_{{authState}}" /></div></td>
        <td style="width:100px;">
            <div class="overflow_hidden w100 link">
            	{{orderListBtn ../shiroUser ../commonOnwerSign }}
            </div>
        </td>
        <td><div class="overflow_hidden w90" title="{{ formatDate tradeDate 'YYYY-MM-DD' }}">{{ formatDate tradeDate 'YYYY-MM-DD' }}</div></td>
		{{#if ../shiroUser.isState}}
			<td>
				<div class="overflow_hidden w120" title="{{custName }}">{{custName }}</div>
			</td>
		{{else}}
			<td>
				<div class="overflow_hidden w120" title="{{custName }}">{{custName }}</div>
			</td>
			<td>
            	<div class="overflow_hidden w120" title="{{company }}">{{company }}</div>
        	</td>
		{{/if}}
        <td><div class="overflow_hidden w120" title="{{code }}">{{code }}</div></td>
        <td><div class="overflow_hidden w70" title="{{formatNumber money '2'}}">{{formatNumber money '2'}}</div></td>
        <td><div class="overflow_hidden w50" title="{{userName }}">{{userName }}</div></td>
        <td><div class="overflow_hidden w50" title="{{saleAcc }}">{{saleAcc }}</div></td>
        <td><div class="overflow_hidden w70" title="{{authStateStr authState}}">{{authStateStr authState}}</div></td>
		<td><div class="overflow_hidden w70" title="{{courierNumber}}"><a href="javascript:;" id="courierView_{{courierNumber}}" data-orderId="{{id}}">{{courierNumber}}</a></div></td>
		<td><div class="overflow_hidden w70" title="{{courierStatusStr courierStatus}}">{{courierStatusStr courierStatus}}</div></td>
		<td><div class="overflow_hidden w80" title="{{formatDate effectiveDate 'YYYY-MM-DD'}}">{{formatDate effectiveDate 'YYYY-MM-DD'}}</div></td>
		<td><div class="overflow_hidden w80" title="{{formatDate invalidDate 'YYYY-MM-DD'}}">{{formatDate invalidDate 'YYYY-MM-DD'}}</div></td>
        <td><div class="overflow_hidden w120" title="{{authDesc }}">{{authDesc }}</div></td>
    </tr>
	{{/each}}
</script>
</body>
</html>
