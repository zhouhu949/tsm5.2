<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <%@ include file="/common/include.jsp" %>
    <script src="${ctx}/static/js/view/contract/order_list.js${_v}" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/page.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/view/bill/render_table.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/common/button_auth.js${_v}"></script>
    <script type="text/javascript">
	window.onload=function(){
		resetHeight();
	};

	function resetHeight(){
		var height = $(".hyx-cfu-left").outerHeight(true)+30;
		window.parent.$("#iframepage").css({"height":height+"px"});
	}
	</script>
</head>
<body>
     <form action="${ctx }/contract/order/list?pageView=check" id="cform" method="post" data-render-url="${ctx }/contract/order/data?pageView=check">
     	<input type="hidden" id="type" value="${type }" name="type" />
     	<input type="hidden" name="isPageSearch" value="1" />
     	<input type="hidden" name="noteType" value="1" />
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
                    	<input class="input-query fl_l" type="text" name="queryText" value="${contractOrderDto.queryText }" style="width:508px;"/><label class="hide-span"></label>
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
                        <input type="hidden" name="authState" id="s_authState" value="${contractOrderDto.authState }" />
                        <c:set var="authStateName">订单状态</c:set>
                        <c:choose>
                        	<c:when test="${contractOrderDto.authState eq 1 }"><c:set var="authStateName">待审核</c:set></c:when>
                        	<c:when test="${contractOrderDto.authState eq 2 }"><c:set var="authStateName">生效中</c:set></c:when>
                        	<c:when test="${contractOrderDto.authState eq 3 }"><c:set var="authStateName">驳回</c:set></c:when>
                        	<c:when test="${contractOrderDto.authState eq 4 }"><c:set var="authStateName">作废</c:set></c:when>
                        	<c:when test="${contractOrderDto.authState eq 5 }"><c:set var="authStateName">已失效</c:set></c:when>
                        </c:choose>
                        <dl class="select">
                            <dt>${authStateName }</dt>
                            <dd>
                                <ul>
                                    <li><a href="###" onclick="$('#s_authState').val('');" title="订单状态">订单状态</a></li>
                                    <li><a href="###" onclick="$('#s_authState').val('1');" title="待审核">待审核</a></li>
                                    <li><a href="###" onclick="$('#s_authState').val('2');" title="生效中">生效中</a></li>
                                    <li><a href="###" onclick="$('#s_authState').val('3');" title="驳回">驳回</a></li>
                                    <li><a href="###" onclick="$('#s_authState').val('4');" title="作废">作废</a></li>
                                    <li><a href="###" onclick="$('#s_authState').val('5');" title="已失效">已失效</a></li>
                                </ul>
                            </dd>
                        </dl>
                        <c:if test="${shiroUser.issys eq 1 }">
	                        <div class="reso-sub-dep fl_l">
	                            <input type="hidden" name="ownerAccStr" id="ownerAccStr" value="${contractOrderDto.ownerAccStr }" />
	                            <input id="ownerNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="所有者" />
	                            <div class="manage-drop">
	                                <div class="sub-dep-ul">
	                                    <ul id="tt1">

	                                    </ul>
	                                </div>
	                                <div class="sure-cancle clearfix" style="width:120px">
	                                    <a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" onclick="seleTree();" href="javascript:;"><label>确定</label></a>
	                                    <a class="com-btnd bomp-btna fl_l" id="close02" onclick="unCheckTree('tt1');" href="javascript:;"><label>清空</label></a>
	                                </div>
	                            </div>
	                        </div>
                        </c:if>
                    </div>
                </div>
                <input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
            </div>
            <div class="com-btnlist hyx-cm-btnlist">
                <a href="javascript:;" id="passBtn" class="com-btna demoBtn_a fl_l"><i class="min-pass"></i><label class="lab-mar">通&nbsp;&nbsp;&nbsp;&nbsp;过</label></a>
				<a href="javascript:;" id="backBtn" class="com-btna demoBtn_b fl_l"><i class="min-rebut"></i><label class="lab-mar">驳&nbsp;&nbsp;&nbsp;&nbsp;回</label></a>
            </div>
            <div class="com-table com-mta hyx-cla-table">
                <table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
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
                        <th><span class="sp sty-borcolor-b">订单状态</span></th>
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
		</div>
	</form>
<script type="text/x-handlebars-template" id="template">
  {{#each list}}
  <tr class="{{even_odd @index}}">
	<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" id="chk_{{id }}_{{authState}}" /></div></td>
	<td style="width:50px;">
		<div class="overflow_hidden w50 link">
            {{#compare authState '==' 1}}
					<a href="javascript:;" id="check_{{id }}" class="icon-examine demoBtn_d" title="审核"></a>
            {{else}}
					<a href="javascript:;" id="view_{{id }}" class="icon-detail demoBtn_c" title="查看"></a>
            {{/compare}}
		</div>
	</td>
	<td><div class="overflow_hidden w80" title="{{formatDate tradeDate 'YYYY-MM-DD'}}">{{formatDate tradeDate 'YYYY-MM-DD'}}</div></td>
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
	<td><div class="overflow_hidden w70" title="{{authStateStr authState}}">{{authStateStr authState}}</div></td>
	<td><div class="overflow_hidden w80" title="{{formatDate effectiveDate 'YYYY-MM-DD'}}">{{formatDate effectiveDate 'YYYY-MM-DD'}}</div></td>
	<td><div class="overflow_hidden w80" title="{{formatDate invalidDate 'YYYY-MM-DD'}}">{{formatDate invalidDate 'YYYY-MM-DD'}}</div></td>
	<td><div class="overflow_hidden w120" title="{{authDesc }}">{{authDesc }}</div></td>
 {{/each}}
</script>
</body>
</html>
