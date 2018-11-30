<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <style type="text/css"></style>
    <%@ include file="/common/include.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/tree_top_part.css${_v}"/>
    <script src="${ctx}/static/js/view/contract/order_info.js${_v}" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/page.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/view/bill/render_table.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/common/button_auth.js${_v}"></script>
</head>
<body>
<div class="year-sale-play">
    <ul class="ann-sale-plan clearfix">
        <li class="li_first" onclick="window.location.href='${ctx}/contract/list?noteType=${orderDetailDto.noteType }';">合同维护</li>
        <li class=""   onclick="window.location.href='${ctx}/contract/order/list?noteType=${orderDetailDto.noteType }';">订单维护</li>
        <li class="select li_last">订单详情</li>
    </ul>
</div>
<div class="hyx-hsrs-bot">
	<form action="${ctx }/contract/orderinfo/list" method="post" data-render-url="${ctx }/contract/orderinfo/data">
		<input type="hidden" name="isPageSearch" value="1" />
         <input type="hidden" name="noteType" value="${orderDetailDto.noteType }" id="noteType" />
        <div class="hyx-cfu-left hyx-ctr hyx-sc">
            <div class="com-search hyx-cfu-search">
                <div class="com-search-box fl_l">
                    <div class="search clearfix" >
					    <div class="select_outerDiv fl_l">
					    <span class="icon_down_5px"></span>
						   	<select name="queryType" class="fl_l search_head_select">
						   		<c:if test="${shiroUser.isState eq 0}">
						   			<option value="3">单位名称</option>
						   		</c:if>
						   		<option value="1">${shiroUser.isState eq 1 ? '客户名称' : '客户姓名' }</option>
						   		<option value="2">订单号</option>
						   	</select>
					   	</div>
                    	<input class="input-query fl_l" type="text" name="queryText" value="${orderDetailDto.queryText }" /><label class="hide-span"></label>
                    </div>
                    <div class="list-box">
                        <!-- 交易日期 -->
						<input type="hidden" name="startDate" id="s_startDate" value="${orderDetailDto.startDate }" />
						<input type="hidden" name="endDate" id="s_endDate" value="${orderDetailDto.endDate }" />
						<input type="hidden" name="sDateType" id="s_sDateType" value="${orderDetailDto.sDateType }" />
		                <c:set var="sDateName" value="交易日期" />
					    <c:choose>
					   		<c:when test="${orderDetailDto.sDateType eq 1 }"><c:set var="sDateName" value="当天" /></c:when>
					   		<c:when test="${orderDetailDto.sDateType eq 2 }"><c:set var="sDateName" value="本周" /></c:when>
					   		<c:when test="${orderDetailDto.sDateType eq 3 }"><c:set var="sDateName" value="本月" /></c:when>
					   		<c:when test="${orderDetailDto.sDateType eq 4 }"><c:set var="sDateName" value="半年" /></c:when>
					   		<c:when test="${orderDetailDto.sDateType eq 5 }">
					   			<fmt:parseDate var="sd" value="${orderDetailDto.startDate }" pattern="yyyy-MM-dd" />
					   			<fmt:parseDate var="ed" value="${orderDetailDto.endDate }" pattern="yyyy-MM-dd" />
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
                        <!-- 产品名称 -->
                        <input type="hidden" name="productId" id="s_productId" value="${orderDetailDto.productId }" />
                        <c:set var="pname">产品名称</c:set>
                        <c:forEach items="${products }" var="ps">
                           	<c:if test="${ps.id eq orderDetailDto.productId  }">
                           		<c:set var="pname">${ps.name }</c:set>
                           	</c:if>
                        </c:forEach>
                        <dl class="select">
                            <dt>${pname }</dt>
                            <dd>
                                <ul>
                                    <li><a href="javascript:;" onclick="$('#s_productId').val('');" title="产品名称">产品名称</a></li>
                                   	<c:forEach items="${products }" var="p">
                                   		<li><a href="javascript:;" onclick="$('#s_productId').val('${p.id}');" title="${p.name }">${p.name }</a></li>
                                   	</c:forEach>
                                </ul>
                            </dd>
                        </dl>
                        <!-- 产品型号 -->
                        <input type="hidden" name="productModel" id="s_productModel" value="${orderDetailDto.productModel }" />
                        <dl class="select">
                            <dt>${empty orderDetailDto.productModel ? '产品型号' : orderDetailDto.productModel }</dt>
                            <dd>
                                <ul>
                                    <li><a href="javascript:;" onclick="$('#s_productModel').val('');" title="产品型号">产品型号</a></li>
                               		<c:forEach items="${products }" var="p">
                                   		<li><a href="javascript:;" onclick="$('#s_productModel').val('${p.model}');" title="${p.model }">${p.model }</a></li>
                                   	</c:forEach>
                                </ul>
                            </dd>
                        </dl>
                        <div class="reso-sub-dep fl_l">
	                        <input id="inputerName" readonly="readonly" class="owner-sour" type="text" value="提交人">
	                        <input id="userIdsStr" name="userIdsStr" type="hidden" value="${orderDetailDto.userIdsStr }">
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
	                        <input type="hidden" name="ownerAccsStr" id="ownerAccsStr" value="${orderDetailDto.ownerAccsStr }" />
	                        <input type="hidden" name="osType" value="${orderDetailDto.osType }" id="osType" />
							<div class="reso-sub-dep fl_l">
								<input id="ownerNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="所有者">
								<div class="manage-drop"  style="height:370px;">
									<div class="shows-radio-boxs"></div>
									<ul class="shows-allorme-boxs">
										<li><input type="radio"  value="1"  name="searchOwnerType" ${orderDetailDto.osType eq '1' ? 'checked':'' }>查看全部</li>
										<li><input type="radio" name="searchOwnerType"  value="2" ${orderDetailDto.osType eq '2' ? 'checked':'' }>查看自己</li>
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
                    </div>
                </div>
                <input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
            </div>

            <div class="com-table com-mta hyx-cla-table" style="margin-top:20px !important;">
                <table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
                    <thead>
                    <tr role="head" class="sty-bgcolor-b">
                        <th><span class="sp sty-borcolor-b">${shiroUser.isState eq 1 ? '客户名称':'客户姓名' }</span></th>
	                    <c:if test="${shiroUser.isState eq 0  }">
	                    	<th><span class="sp sty-borcolor-b">单位名称</span></th>
	                    </c:if>
                        <th><span class="sp sty-borcolor-b">订单号</span></th>
                        <th><span class="sp sty-borcolor-b" sort="true">交易日期</span></th>
                        <th><span class="sp sty-borcolor-b">产品名称</span></th>
                        <th><span class="sp sty-borcolor-b">产品型号</span></th>
                        <th><span class="sp sty-borcolor-b">标准价格</span></th>
                        <th><span class="sp sty-borcolor-b">交易价格</span></th>
                        <th><span class="sp sty-borcolor-b">交易数量</span></th>
                        <th><span class="sp sty-borcolor-b">交易金额(元)</span></th>
                        <th><span class="sp sty-borcolor-b">提交人</span></th>
                        <th><span class="sp sty-borcolor-b">所有者</span></th>
                        <th><span class="sp sty-borcolor-b" sort="true">生效日期</span></th>
                        <th><span class="sp sty-borcolor-b" sort="true">失效日期</span></th>
                        <th>备注</th>
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
            <td><div class="overflow_hidden w100" title="{{orderCode }}">{{orderCode }}</div></td>
            <td><div class="overflow_hidden w90" title="{{formatDate tradeDate 'YYYY-MM-DD' }}">{{formatDate tradeDate 'YYYY-MM-DD' }}</div></td>
            <td><div class="overflow_hidden w70" title="{{productName }}">{{productName }}</div></td>
            <td><div class="overflow_hidden w70" title="{{productModel }}">{{productModel }}</div></td>
            <td><div class="overflow_hidden w70" title="{{formatNumber productPrice '2'}}">{{formatNumber productPrice '2'}}</div></td>
            <td><div class="overflow_hidden w70" title="{{formatNumber buyPrice '2'}}">{{formatNumber buyPrice '2'}}</div></td>
            <td><div class="overflow_hidden w70" title="{{formatNumber buyNum}}">{{formatNumber buyNum}}</div></td>
            <td><div class="overflow_hidden w70" title="{{formatNumber buyMoney '2'}}">{{formatNumber buyMoney '2'}}</div></td>
            <td><div class="overflow_hidden w50" title="{{userName }}">{{userName }}</div></td>
            <td><div class="overflow_hidden w50" title="{{ownerAcc }}">{{ownerAcc }}</div></td>
            <td><div class="overflow_hidden w90" title="{{formatDate effectiveDate 'YYYY-MM-DD' }}">{{ formatDate effectiveDate  'YYYY-MM-DD' }}</div></td>
            <td><div class="overflow_hidden w90" title="{{formatDate invalidDate 'YYYY-MM-DD' }}">{{ formatDate invalidDate 'YYYY-MM-DD' }}</div></td>
            <td class="{{#compare context '!=' ''}} hyx-cmc-td {{/compare}}">
                <div class="overflow_hidden w120">{{context }}</div>
                <span class="drop" style="display:none;">
                    <label class="arrow"><em>◆</em><span>◆</span></label>
                    <label class="box">{{context }}</label>
                </span>
            </td>
        </tr>
        {{/each}}
    </script>
</div>
</body>
</html>
