<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <style type="text/css"></style>
    <%@ include file="/common/include.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/tree_top_part.css${_v}"/>
    <script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/view/contract/order_list.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/common/tablerows.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
</head>
<body>
<div class="year-sale-play">
    <ul class="ann-sale-plan clearfix">
        <li class="li_first" onclick="window.location.href='${ctx}/contract/list';">合同维护</li>
        <li class="select">订单维护</li>
        <li class="li_last" onclick="window.location.href='${ctx}/contract/orderinfo/list';">订单详情</li>
    </ul>
</div>
	 <input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
     <form id="orderManageForm" action="${ctx }/contract/order/list" method="post">
     	<input type="hidden" name="isPageSearch" value="1" />
        <div class="hyx-cfu-left hyx-ctr hyx-sc">
            <div class="com-search hyx-cfu-search">
                <div class="com-search-box fl_l">
                    <div class="search clearfix" >
					    <div class="select_outerDiv fl_l">
					    <span class="icon_down_5px"></span>
						   	<select name="queryType" class="fl_l search_head_select">
						   		<option value="3" ${(empty contractOrderDto.queryType or contractOrderDto.queryType eq '3') ? 'selected' : ''}>单位名称</option>
						   		<option value="1" ${contractOrderDto.queryType eq '1' ? 'selected' : ''}>客户姓名</option>
						   		<option value="4" ${contractOrderDto.queryType eq '4' ? 'selected' : ''}>联系电话</option>
						   		<option value="2" ${contractOrderDto.queryType eq '2' ? 'selected' : ''}>订单号</option>
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
                        <input type="hidden" name="authState" id="s_authState" value="${contractOrderDto.authState }" />
                        <c:set var="authStateName">订单状态</c:set>
                        <c:choose>
                        	<c:when test="${contractOrderDto.authState eq 0 }"><c:set var="authStateName">未上报</c:set></c:when>
                        	<c:when test="${contractOrderDto.authState eq 1 }"><c:set var="authStateName">待审核</c:set></c:when>
                        	<c:when test="${contractOrderDto.authState eq 2 }"><c:set var="authStateName">通过</c:set></c:when>
                        	<c:when test="${contractOrderDto.authState eq 3 }"><c:set var="authStateName">驳回</c:set></c:when>
                        	<c:when test="${contractOrderDto.authState eq 4 }"><c:set var="authStateName">作废</c:set></c:when>
                        </c:choose>
                        <dl class="select">
                            <dt>${authStateName }</dt>
                            <dd>
                                <ul>
                                    <li><a href="###" onclick="$('#s_authState').val('');" title="订单状态">订单状态</a></li>
                                    <li><a href="###" onclick="$('#s_authState').val('0');" title="未上报">未上报</a></li>
                                    <li><a href="###" onclick="$('#s_authState').val('1');" title="待审核">待审核</a></li>
                                    <li><a href="###" onclick="$('#s_authState').val('2');" title="通过">通过</a></li>
                                    <li><a href="###" onclick="$('#s_authState').val('3');" title="驳回">驳回</a></li>
                                    <li><a href="###" onclick="$('#s_authState').val('4');" title="驳回">作废</a></li>
                                </ul>
                            </dd>
                        </dl>
                        <c:if test="${shiroUser.issys eq 1 }">
	                        <input type="hidden" name="ownerAccStr" id="ownerAccStr" value="${contractOrderDto.ownerAccStr }" />
	                        <input type="hidden" name="osType" value="${contractOrderDto.osType }" id="osType" />
							<div class="reso-sub-dep fl_l">
								<input id="ownerNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="提交人">
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

                    </div>
                </div>
                <input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
            </div>

            <c:if test="${commonOnwerOpen  eq 1 }">
		        <input type="hidden" name="noteType" value="${contractOrderDto.noteType }" id="noteType" />
		        <p class="com-moTag">
						<label class="a">客户分类：</label>
						<a href="javascript:;" nt="1" class="e ${contractOrderDto.noteType eq '1' ? 'e-hover' : '' }">我的客户</a>
						<a href="javascript:;" nt="2" class="e ${contractOrderDto.noteType eq '2' ? 'e-hover' : '' }">共有客户</a>
				</p>
	        </c:if>

            <div class="com-btnlist hyx-cm-btnlist">
            	<shiro:hasPermission name="contractOrder_deleteOrder">
                	<a href="javascript:;" id="delBtn" class="com-btna alert_sys_pro_set_a_b fl_l"><i class="com_btn min-dele"></i><label class="lab-mar">删&nbsp;&nbsp;&nbsp;&nbsp;除</label></a>
            	</shiro:hasPermission>
            	<a href="javascript:;" id="export" class="com-btna alert_sys_pro_set_a_b fl_l"><i class="min-remove-resou"></i><label class="lab-mar">导&nbsp;&nbsp;&nbsp;&nbsp;出</label></a>
            </div>
            <div class="com-table com-mta hyx-cla-table">
                <table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
                    <thead>
                    <tr role="head" class="sty-bgcolor-b">
                        <th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
                        <th><span class="sp sty-borcolor-b">操作</span></th>
                        <th><span class="sp sty-borcolor-b" sort="true">交易日期</span></th>
                        <th><span class="sp sty-borcolor-b">客户姓名</span></th>
                        <th><span class="sp sty-borcolor-b">联系电话</span></th>
                        <th><span class="sp sty-borcolor-b">联系地址</span></th>
                        <th><span class="sp sty-borcolor-b">单位名称</span></th>
                        <th><span class="sp sty-borcolor-b">订单号</span></th>
                        <th><span class="sp sty-borcolor-b">交易金额(元)</span></th>
                        <th><span class="sp sty-borcolor-b">提交人</span></th>
                        <th><span class="sp sty-borcolor-b">所有者</span></th>
                        <th><span class="sp sty-borcolor-b">订单状态</span></th>
                        <th>审核备注</th>
                    </tr>
                    </thead>
                    <tbody>
                    	<c:choose>
                    		<c:when test="${!empty orderDtos }">
                    			<c:forEach items="${orderDtos }" var="order" varStatus="vs">
                    				<tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">
				                        <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" id="chk_${order.id }_${order.authState}" /></div></td>
				                        <td style="width:100px;">
				                        	<div class="overflow_hidden w100 link">
				                        		<c:choose>
				                        			<c:when test="${order.authState eq 0 }">
				                        				<a href="javascript:;" id="view_${order.id }" class="icon-search-look" title="查看"></a>
				                        				<shiro:hasPermission name="contractOrder_editOrder">
				                        					<a href="javascript:;" <c:if test="${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1)}">id="uporder_${order.id }"</c:if> class="icon-edit ${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }" title="编辑"></a>
				                        				</shiro:hasPermission>
				                        				<a href="javascript:;" <c:if test="${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1)}">id="report_${order.id }"</c:if> class="icon-report ${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }" title="上报"></a>
				                        			</c:when>
				                        			<c:when test="${order.authState eq 1 }">
				                        				<a href="javascript:;" id="view_${order.id }" class="icon-search-look" title="查看"></a>
				                        				<shiro:hasPermission name="contractOrder_recallOrder">
					                        				<a href="javascript:;" <c:if test="${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1)}">id="reback_${order.id }"</c:if> class="icon-cancel-contr shows-cancel-contr ${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }" title="撤回"></a>
				                        				</shiro:hasPermission>
				                        			</c:when>
				                        			<c:when test="${order.authState eq 2 }">
				                        				<a href="javascript:;" id="view_${order.id }" class="icon-search-look" title="查看"></a>
				                        				<shiro:hasPermission name="contractOrder_cancelOrder">
				                        					<a href="javascript:;" <c:if test="${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) }">id="cancelled_${order.id }"</c:if> class="icon-cancel-contr ${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }" title="作废"></a>
				                        				</shiro:hasPermission>
				                        			</c:when>
				                        			<c:when test="${order.authState eq 3 }">
				                        				<a href="javascript:;" id="view_${order.id }" class="icon-search-look" title="查看"></a>
				                        				<shiro:hasPermission name="contractOrder_editOrder">
				                        					<a href="javascript:;" <c:if test="${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) }">id="uporder_${order.id }"</c:if> class="icon-edit ${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }" title="编辑"></a>
				                        				</shiro:hasPermission>
				                        			</c:when>
				                        			<c:otherwise>
				                        				<a href="javascript:;" id="view_${order.id }" class="icon-search-look" title="查看"></a>
				                        			</c:otherwise>
				                        		</c:choose>
				                        	</div>
				                        </td>
				                        <td><div class="overflow_hidden w90" title="<fmt:formatDate value="${order.tradeDate }" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${order.tradeDate }" pattern="yyyy-MM-dd"/></div></td>
				                        <td><div class="overflow_hidden w150" title="${order.custName }">${order.custName }</div></td>
				                        <td><div phone="tel" class="overflow_hidden w90" title="${empty order.mobilephone ? order.telphone : order.mobilephone}">${empty order.mobilephone ? order.telphone : order.mobilephone}</div></td>
				                        <td><div class="overflow_hidden w150" title="${order.companyAddr }">${order.companyAddr }</div></td>
				                        <td><div class="overflow_hidden w150" title="${order.company }">${order.company }</div></td>
				                        <td><div class="overflow_hidden w120" title="${order.code }">${order.code }</div></td>
				                        <td><div class="overflow_hidden w70" title="<fmt:formatNumber type="number" value="${order.money }" pattern="#,##0.00#" maxFractionDigits="2" />"><fmt:formatNumber type="number" value="${order.money }" pattern="#,##0.00#" maxFractionDigits="2" /></div></td>
				                        <td><div class="overflow_hidden w50" title="${order.userName }">${order.userName }</div></td>
				                        <td><div class="overflow_hidden w50" title="${order.saleAcc }">${order.saleAcc }</div></td>
				                        <c:choose>
				                        	<c:when test="${order.authState eq 1 }"><c:set var="authName">待审核</c:set></c:when>
				                        	<c:when test="${order.authState eq 2 }"><c:set var="authName">通过</c:set></c:when>
				                        	<c:when test="${order.authState eq 3 }"><c:set var="authName">驳回</c:set></c:when>
				                        	<c:when test="${order.authState eq 4 }"><c:set var="authName">作废</c:set></c:when>
				                        	<c:otherwise><c:set var="authName">未上报</c:set></c:otherwise>
				                        </c:choose>
				                        <td><div class="overflow_hidden w70" title="${authName }">${authName }</div></td>
				                        <td><div class="overflow_hidden w120" title="${order.authDesc }">${order.authDesc }</div></td>
				                    </tr>
                    			</c:forEach>
                    		</c:when>
                    		<c:otherwise>
                    			<tr class="no_data_tr">
                    				<td>当前列表无数据!</td>
                    			</tr>
                    		</c:otherwise>
                    	</c:choose>
                    </tbody>
                </table>
            </div>
            <div class="com-bot" >
                <div class="com-page fl_r">
                    ${contractOrderDto.page.pageStr }
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
        <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" id="chk_${id }_${authState}" /></div></td>
        <td style="width:100px;">
            <div class="overflow_hidden w100 link">
                {{compare authState "==" "0"}}
               // param
                    <a href="javascript:;" <c:if test="${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }">id="uporder_${order.id }"</c:if> class="icon-edit ${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }" title="编辑"></a>
                    <a href="javascript:;" <c:if test="${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }">id="report_${order.id }"</c:if> class="icon-report ${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }" title="上报"></a>
                {{/compare}}
                {{compare authState "==" "1"}}
                    <a href="javascript:;" <c:if test="${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }">id="reback_${order.id }"</c:if> class="icon-cancel-contr shows-cancel-contr ${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }" title="撤回"></a>
                {{/compare}}
                {{compare authState "==" "2"}}
                    <a href="javascript:;" <c:if test="${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }">id="cancelled_${order.id }"</c:if> class="icon-cancel-contr ${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }" title="作废"></a>
                {{/compare}}
                {{compare authState "==" "3"}}
                    <a href="javascript:;" <c:if test="${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }">id="uporder_${order.id }"</c:if> class="icon-edit ${(order.commonAcc ne shiroUser.account) || (order.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }" title="编辑"></a>
                {{/compare}}
                <a href="javascript:;" id="view_${order.id }" class="icon-search-look" title="查看"></a>
            </div>
        </td>
        <td><div class="overflow_hidden w90" title="{{ formatDate tradeDate 'YYYY-MM-DD' }}">{{ formatDate tradeDate 'YYYY-MM-DD' }}</div></td>
        <td><div class="overflow_hidden w150" title="{{custName }}">{{custName }}</div></td>
		<td><div phone="tel" class="overflow_hidden w90" title="{{compare mobilephone '==' '' }} {{telphone}} {{else}} {{mobilephone}} {{/compare}}">{{compare mobilephone '==' '' }} {{telphone}} {{else}} {{mobilephone}} {{/compare}}</div></td>
		<td><div class="overflow_hidden w150" title="{{companyAddr }}">{{companyAddr }}</div></td>
		<td><div class="overflow_hidden w150" title="{{company }}">{{company }}</div></td>
        <td><div class="overflow_hidden w120" title="{{code }}">{{code }}</div></td>
        <td><div class="overflow_hidden w70" title="{{formatNumber money '2'}}">{{formatNumber money '2'}}</div></td>
        <td><div class="overflow_hidden w50" title="{{order.userName }}">{{order.userName }}</div></td>
        <td><div class="overflow_hidden w50" title="{{saleAcc }}">{{saleAcc }}</div></td>
        <td><div class="overflow_hidden w70" title="{{authName }}">{{authStateStr authState}}</div></td>
        <td><div class="overflow_hidden w120" title="{{authDesc }}">{{authDesc }}</div></td>
    </tr>
	{{/each}}
</script>
</body>
</html>
