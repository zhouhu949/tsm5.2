<%@page import="com.qftx.base.shiro.ShiroUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <style type="text/css"></style>
    <%@ include file="/common/include.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/tree_top_part.css${_v}"/>
    <script src="${ctx}/static/js/view/contract/list.js${_v}" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
    <script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
</head>
<body>
<div class="year-sale-play">
    <ul class="ann-sale-plan clearfix">
        <li class="select li_first">合同维护</li>
        <li class=""  onclick="window.location.href='${ctx}/contract/order/list';">订单维护</li>
        <li class="li_last" onclick="window.location.href='${ctx}/contract/orderinfo/list';">订单详情</li>
    </ul>
</div>
<form action="${ctx }/contract/list" method="post">
	<input type="hidden" name="isPageSearch" value="1" />
	<div class="hyx-hsrs-bot">
	    <div class="hyx-cfu-left hyx-ctr hyx-sc">
	        <div class="com-search hyx-cfu-search">
	            <div class="com-search-box fl_l">
	                <div class="search clearfix" >
					    <div class="select_outerDiv fl_l">
					    <span class="icon_down_5px"></span>
						   	<select name="queryType" class="fl_l search_head_select">
						   		<option value="3" ${(empty contractDto.queryType or contractDto.queryType eq '3' ? 'selected' : '')}>单位名称</option>
						   		<option value="1" ${contractDto.queryType eq '1' ? 'selected' : ''}>客户姓名</option>
						   		<option value="4" ${contractDto.queryType eq '4' ? 'selected' : ''}>联系电话</option>
						   		<option value="2" ${contractDto.queryType eq '2' ? 'selected' : ''}>合同编号</option>
						   	</select>
					   	</div>
		                <input class="input-query fl_l" name="queryText" type="text" value="${contractDto.queryText }"/>
		                <label class="hide-span"></label>
	                </div>
	                <div class="list-box">
	                	<!-- 签约日期 -->
						<input type="hidden" name="startSignDate" id="s_startSignDate" value="${contractDto.startSignDate }" />
						<input type="hidden" name="endSignDate" id="s_endSignDate" value="${contractDto.endSignDate }" />
						<input type="hidden" name="sDateType" id="s_sDateType" value="${contractDto.sDateType }" />
		                <c:set var="sDateName" value="签约日期" />
					    <c:choose>
					   		<c:when test="${contractDto.sDateType eq 1 }"><c:set var="sDateName" value="当天" /></c:when>
					   		<c:when test="${contractDto.sDateType eq 2 }"><c:set var="sDateName" value="本周" /></c:when>
					   		<c:when test="${contractDto.sDateType eq 3 }"><c:set var="sDateName" value="本月" /></c:when>
					   		<c:when test="${contractDto.sDateType eq 4 }"><c:set var="sDateName" value="半年" /></c:when>
					   		<c:when test="${contractDto.sDateType eq 5 }">
					   			<fmt:parseDate var="ssd" value="${contractDto.startSignDate }" pattern="yyyy-MM-dd" />
					   			<fmt:parseDate var="esd" value="${contractDto.endSignDate }" pattern="yyyy-MM-dd" />
					   			<c:set var="sDateName">
					   				<fmt:formatDate value="${ssd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${esd }" pattern="yy.MM.dd"/>
					   			</c:set>
					   		</c:when>
					    </c:choose>
	                    <dl class="select">
	                        <dt>${sDateName }</dt>
	                        <dd>
	                            <ul>
	                                <li><a href="###" onclick="setSdate(0)" title="签约日期">签约日期</a></li>
	                                <li><a href="###" onclick="setSdate(1)" title="当天">当天</a></li>
	                                <li><a href="###" onclick="setSdate(2)" title="本周">本周</a></li>
	                                <li><a href="###" onclick="setSdate(3)" title="本月">本月</a></li>
	                                <li><a href="###" onclick="setSdate(4)" title="半年">半年</a></li>
	                                <li><a href="###" title="自定义" class="diy date-range" id="d1">自定义</a></li>
	                            </ul>
	                        </dd>
	                    </dl>
	                    <!-- 生效日期 -->
	                    <input type="hidden" name="startEffecDate" id="s_startEffecDate" value="${contractDto.startEffecDate }" />
						<input type="hidden" name="endEffecDate" id="s_endEffecDate" value="${contractDto.endEffecDate }" />
						<input type="hidden" name="eDateType" id="s_eDateType" value="${contractDto.eDateType }" />
		                <c:set var="eDateName" value="生效日期" />
		                <c:choose>
					   		<c:when test="${contractDto.eDateType eq 1 }"><c:set var="eDateName" value="当天" /></c:when>
					   		<c:when test="${contractDto.eDateType eq 2 }"><c:set var="eDateName" value="本周" /></c:when>
					   		<c:when test="${contractDto.eDateType eq 3 }"><c:set var="eDateName" value="本月" /></c:when>
					   		<c:when test="${contractDto.eDateType eq 4 }"><c:set var="eDateName" value="半年" /></c:when>
					   		<c:when test="${contractDto.eDateType eq 5 }">
					   			<fmt:parseDate var="sed" value="${contractDto.startEffecDate }" pattern="yyyy-MM-dd" />
					   			<fmt:parseDate var="eed" value="${contractDto.endEffecDate }" pattern="yyyy-MM-dd" />
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
	                    <input type="hidden" name="startInvalidDate" id="s_startInvalidDate" value="${contractDto.startInvalidDate }" />
						<input type="hidden" name="endInvalidDate" id="s_endInvalidDate" value="${contractDto.endInvalidDate }" />
						<input type="hidden" name="iDateType" id="s_iDateType" value="${contractDto.iDateType }" />
		                <c:set var="iDateName" value="失效日期" />
		                <c:choose>
					   		<c:when test="${contractDto.iDateType eq 1 }"><c:set var="iDateName" value="当天" /></c:when>
					   		<c:when test="${contractDto.iDateType eq 2 }"><c:set var="iDateName" value="本周" /></c:when>
					   		<c:when test="${contractDto.iDateType eq 3 }"><c:set var="iDateName" value="本月" /></c:when>
					   		<c:when test="${contractDto.iDateType eq 4 }"><c:set var="iDateName" value="半年" /></c:when>
					   		<c:when test="${contractDto.iDateType eq 5 }">
					   			<fmt:parseDate var="sid" value="${contractDto.startInvalidDate }" pattern="yyyy-MM-dd" />
					   			<fmt:parseDate var="eid" value="${contractDto.endInvalidDate }" pattern="yyyy-MM-dd" />
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
	                    <c:if test="${shiroUser.issys eq 1 }">
		                    <input type="hidden" name="ownerAccsStr" value="${contractDto.ownerAccsStr }" id="ownerAccsStr" />
          							<input type="hidden" name="osType" value="${contractDto.osType }" id="osType" />
          							<div class="reso-sub-dep fl_l">
          								<input id="ownerNameStr" readonly="readonly" class="owner-sour" type="text" value="签约人">
          								<div class="manage-owner-sour"  style="height:370px;">
          									<div class="shows-radio-boxs"></div>
          									<ul class="shows-allorme-boxs">
          										<li><input type="radio"  value="1"  name="searchOwnerType" ${contractDto.osType eq '1' ? 'checked':'' }>查看全部</li>
          										<li><input type="radio" name="searchOwnerType"  value="2" ${contractDto.osType eq '2' ? 'checked':'' }>查看自己</li>
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
                        <input id="inputerName" readonly="readonly" class="owner-sour" type="text" value="签约人">
                        <input id="signAccsStr" name="signAccsStr" type="hidden" value="${contractDto.signAccsStr }">
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

                      <c:set var="contractTypeName" value="合同类别" />
					  <c:choose>
					  	<c:when test="${contractDto.isDel eq 0 }">
					  		<c:set var="contractTypeName" value="有效合同" />
					  	</c:when>
					  	<c:when test="${contractDto.isDel eq 1 }">
					  		<c:set var="contractTypeName" value="无效合同" />
					  	</c:when>
					  </c:choose>
					  <input type="hidden" name="isDel" id="isDel" value="${contractDto.isDel }" />
                      <dl class="select list-hid" style="display:none;">
                         <dt>${contractTypeName }</dt>
                         <dd>
                             <ul>
                                 <li><a href="javascript:void(0);" onclick="$('#isDel').val('')" title="合同类别">合同类别</a></li>
                                 <li><a href="javascript:void(0);" onclick="$('#isDel').val(0)" title="有效合同">有效合同</a></li>
                                 <li><a href="javascript:void(0);" onclick="$('#isDel').val(1)" title="无效合同">无效合同</a></li>
                             </ul>
                         </dd>
                     </dl>


	                </div>
	            </div>
	            <input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
              <a href="javascript:;" class="more"><i></i>更多</a>
	        </div>
	        <c:if test="${commonOnwerOpen  eq 1 }">
		        <input type="hidden" name="noteType" value="${contractDto.noteType }" id="noteType" />
		        <p class="com-moTag">
						<label class="a">客户分类：</label>
						<a href="javascript:;" nt="1" class="e ${contractDto.noteType eq '1' ? 'e-hover' : '' }">我的客户</a>
						<a href="javascript:;" nt="2" class="e ${contractDto.noteType eq '2' ? 'e-hover' : '' }">共有客户</a>
				</p>
	        </c:if>
	        <div class="com-btnlist hyx-cm-btnlist">
	            <a href="javascript:;" <c:if test="${shiroUser.serverDay gt 0}">onclick="toBatchSmsSend()"</c:if> class="${shiroUser.serverDay gt 0 ? 'com-btna' : 'com-btna-no' } demoBtn_a fl_l"><i class="min-messag ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }"></i><label
	                    class="lab-mar">发送短信</label></a>
<!-- 	            <a href="javascript:;" id="qxBtn" class="com-btna demoBtn_b fl_l"><i class="com_btn com_btn_21"></i><label -->
<!-- 	                    class="lab-mar">取消合同</label></a> -->
	        </div>
	        <div class="com-table com-mta hyx-cla-table">
	            <table id="t1" width="100%" cellspacing="0" cellpadding="0"
	                   class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
	                <thead>
	                <tr role="head" class="sty-bgcolor-b">
	                    <th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check"/></span></th>
	                    <th><span class="sp sty-borcolor-b">操作</span></th>
	                    <th><span class="sp sty-borcolor-b" sort="true">签约日期</span></th>
	                    <th><span class="sp sty-borcolor-b">客户姓名</span></th>
	                    <th><span class="sp sty-borcolor-b">单位名称</span></th>
	                    <th><span class="sp sty-borcolor-b">合同编号</span></th>
	                    <th><span class="sp sty-borcolor-b">合同名称</span></th>
	                    <th><span class="sp sty-borcolor-b" sort="true">生效日期</span></th>
	                    <th><span class="sp sty-borcolor-b" sort="true">失效日期</span></th>
	                    <th><span class="sp sty-borcolor-b">销售团队</span></th>
	                    <th><span class="sp sty-borcolor-b">签约人</span></th>
	                    <th><span class="sp sty-borcolor-b">所有者</span></th>
	                    <th><span class="sp sty-borcolor-b">合同类别</span></th>
	                    <th>合同取消原因</th>
	                </tr>
	                </thead>
	                <tbody>
	                	<c:choose>
	                		<c:when test="${!empty contractDtos }">
	                			<c:forEach var="cd" items="${contractDtos }" varStatus="vs">
	                				<tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">
					                    <c:choose>
											<c:when test="${shiroUser.isState eq 1}">
												<td style="width:30px;">
							                        <div class="overflow_hidden w30 skin-minimal"><input name="check_" name_tel="${cd.mainLinkman }|${cd.telphone}" name_mobile="${cd.mainLinkman }|${cd.mobilephone}" mobile="${cd.mobilephone }" telPhone="${cd.telphone}" type="checkbox" id="chk_${cd.id }"/></div>
							                    </td>
											</c:when>
											<c:otherwise>
												<td style="width:30px;">
							                        <div class="overflow_hidden w30 skin-minimal"><input name="check_" name_tel="${cd.custName }|${cd.telphone}" name_mobile="${cd.custName }|${cd.mobilephone}" mobile="${cd.mobilephone }" telPhone="${cd.telphone}" type="checkbox" id="chk_${cd.id }"/></div>
							                    </td>
											</c:otherwise>
										</c:choose>
					                    <td style="width:130px;">
					                        <div class="overflow_hidden w130 link">
					                        	<c:choose>
					                        		<c:when test="${cd.isDel eq 1 }">
								                         <shiro:hasPermission name="contract_contractLook">
								                        	<a href="javascript:;" class="icon-search-look" id="look_${cd.id }" title="查看"></a>
					                        			 </shiro:hasPermission>
					                        		</c:when>
					                        		<c:otherwise>
					                        			<a href="javascript:;" class="icon-cust-card" id="cardInfo_${cd.custId }" custName="${empty cd.company ? cd.custName : cd.company }" title="客户卡片"></a>
								                        <shiro:hasPermission name="contract_addOrder">
									                        <a href="javascript:;" module="10" class="icon-new-order ${(shiroUser.serverDay gt 0 && cd.commonAcc ne shiroUser.account) || (cd.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }" <c:if test="${(shiroUser.serverDay gt 0 && cd.commonAcc ne shiroUser.account) || (cd.commonAcc eq shiroUser.account && commonOnwerSign eq 1)}">id="addOrder_${cd.id }"</c:if> custId="${cd.custId }" title="新增订单"></a>
								                        </shiro:hasPermission>
								                        <shiro:hasPermission name="contract_contractEdit">
									                        <a href="javascript:;" class="icon-edit ${(cd.commonAcc ne shiroUser.account) || (cd.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }" <c:if test="${(cd.commonAcc ne shiroUser.account) || (cd.commonAcc eq shiroUser.account && commonOnwerSign eq 1) }">id="editcon_${cd.id }"</c:if> title="合同编辑"></a>
								                        </shiro:hasPermission>
								                        <shiro:hasPermission name="contract_contractLook">
									                        <a href="javascript:;" class="icon-search-look" id="look_${cd.id }" title="查看"></a>
								                        </shiro:hasPermission>
								                        <shiro:hasPermission name="contract_cancelContract">
							                        		<a href="javascript:;" class="icon-cancel-contr demoBtn_b icon-cancel-contr-bonus ${(cd.commonAcc ne shiroUser.account) || (cd.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }" <c:if test="${(cd.commonAcc ne shiroUser.account) || (cd.commonAcc eq shiroUser.account && commonOnwerSign eq 1) }">id="cancle_${cd.id }_${cd.custId }"</c:if> title="取消合同"></a>
					                        			</shiro:hasPermission>
					                        		</c:otherwise>
					                        	</c:choose>
					                        </div>
					                    </td>
					                    <td>
					                        <div class="overflow_hidden w80" title="<fmt:formatDate value="${cd.signDate }" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${cd.signDate }" pattern="yyyy-MM-dd"/></div>
					                    </td>
					                    <td>
					                        <div class="overflow_hidden w120" title="${cd.custName }">${cd.custName }</div>
					                    </td>
					                    <td>
					                        <div class="overflow_hidden w120" title="${cd.company }">${cd.company }</div>
					                    </td>
					                    <td>
					                        <div class="overflow_hidden w100" title="${cd.code }">${cd.code }</div>
					                    </td>
					                    <td>
					                    	<div class="overflow_hidden w120" title="${cd.contractName }">${cd.contractName }</div>
					                    </td>
					                    <td>
					                        <div class="overflow_hidden w80" title="<fmt:formatDate value="${cd.effectiveDate }" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${cd.effectiveDate }" pattern="yyyy-MM-dd"/></div>
					                    </td>
					                    <td>
					                        <div class="overflow_hidden w80" title="<fmt:formatDate value="${cd.invalidDate }" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${cd.invalidDate }" pattern="yyyy-MM-dd"/></div>
					                    </td>
					                    <td>
					                        <div class="overflow_hidden w70" title="${cd.groupName }">${cd.groupName }</div>
					                    </td>
					                    <td>
					                        <div class="overflow_hidden w50" title="${cd.signUsername }">${cd.signUsername }</div>
					                    </td>
					                    <td>
					                        <div class="overflow_hidden w50" title="${cd.ownerName }">${cd.ownerName }</div>
					                    </td>
					                    <td>
					                        <div class="overflow_hidden w50" title="${cd.isDel eq 1 ? '无效合同' : '有效合同' }">${cd.isDel eq 1 ? '无效合同' : '有效合同' }</div>
					                    </td>
					                     <td>
					                        <div class="overflow_hidden w80" title="${cd.cancleRemark }">${cd.cancleRemark }</div>
					                    </td>
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
	            <div class="com-page fl_r">${contractDto.page.pageStr }</div>
	        </div>
	    </div>
	</div>
</form>
<script type="text/x-handlebars-template" id="template">
	{{#each list}}
	<tr class="{{even_odd @index}}">
		<c:choose>
			<c:when test="${shiroUser.isState eq 1}">
				<td style="width:30px;">
					<div class="overflow_hidden w30 skin-minimal"><input name="check_" name_tel="${cd.mainLinkman }|${cd.telphone}" name_mobile="${cd.mainLinkman }|${cd.mobilephone}" mobile="${cd.mobilephone }" telPhone="${cd.telphone}" type="checkbox" id="chk_${cd.id }"/></div>
				</td>
			</c:when>
			<c:otherwise>
				<td style="width:30px;">
					<div class="overflow_hidden w30 skin-minimal"><input name="check_" name_tel="${cd.custName }|${cd.telphone}" name_mobile="${cd.custName }|${cd.mobilephone}" mobile="${cd.mobilephone }" telPhone="${cd.telphone}" type="checkbox" id="chk_${cd.id }"/></div>
				</td>
			</c:otherwise>
		</c:choose>
		<td style="width:130px;">
			<div class="overflow_hidden w130 link">
				<a href="javascript:;" class="icon-search-look" id="look_{{id }}" title="查看"></a>
                {{compare isDel '==' 1}}
                <a href="javascript:;" class="icon-cust-card" id="cardInfo_{{custId }}" custName="${empty cd.company ? cd.custName : cd.company }" title="客户卡片"></a>
                <a href="javascript:;" class="icon-new-order ${(shiroUser.serverDay gt 0 && cd.commonAcc ne shiroUser.account) || (cd.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }" <c:if test="${(shiroUser.serverDay gt 0 && cd.commonAcc ne shiroUser.account) || (cd.commonAcc eq shiroUser.account && commonOnwerSign eq 1)}">id="addOrder_${cd.id }"</c:if> custId="${cd.custId }" title="新增订单"></a>
                <a href="javascript:;" class="icon-edit ${(cd.commonAcc ne shiroUser.account) || (cd.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }" <c:if test="${(cd.commonAcc ne shiroUser.account) || (cd.commonAcc eq shiroUser.account && commonOnwerSign eq 1) }">id="editcon_${cd.id }"</c:if> title="合同编辑"></a>
                <a href="javascript:;" class="icon-cancel-contr demoBtn_b icon-cancel-contr-bonus ${(cd.commonAcc ne shiroUser.account) || (cd.commonAcc eq shiroUser.account && commonOnwerSign eq 1) ? '' : 'img-gray' }" <c:if test="${(cd.commonAcc ne shiroUser.account) || (cd.commonAcc eq shiroUser.account && commonOnwerSign eq 1) }">id="cancle_${cd.id }_${cd.custId }"</c:if> title="取消合同"></a>
				{{/compare}}
			</div>
		</td>
		<td>
			<div class="overflow_hidden w80" title="{{formatDate signDate 'YYYY-MM-DD' }}">{{formatDate signDate 'YYYY-MM-DD' }}</div>
		</td>
		<td>
			<div class="overflow_hidden w120" title="{{custName }}">{{custName }}</div>
		</td>
        <td>
            <div class="overflow_hidden w120" title="{{company }}">{{company }}</div>
        </td>
		<td>
			<div class="overflow_hidden w100" title="{{code }}">{{code }}</div>
		</td>
		<td>
			<div class="overflow_hidden w120" title="{{contractName }}">{{contractName }}</div>
		</td>
		<td>
			<div class="overflow_hidden w80" title="{{formatDate effectiveDate 'YYYY-MM-DD' }}">{{formatDate effectiveDate 'YYYY-MM-DD' }}</div>
		</td>
		<td>
			<div class="overflow_hidden w80" title="{{formatDate invalidDate 'YYYY-MM-DD' }}">{{formatDate invalidDate 'YYYY-MM-DD' }}</div>
		</td>
		<td>
			<div class="overflow_hidden w70" title="{{groupName }">{{groupName }}</div>
		</td>
		<td>
			<div class="overflow_hidden w50" title="{{signUsername }}">{{signUsername }}</div>
		</td>
		<td>
			<div class="overflow_hidden w50" title="{{ownerName }}">{{ownerName }}</div>
		</td>
		<td>
			<div class="overflow_hidden w50" title="{{compare isDel '==' 1 }}  '无效合同' {{/else}} '有效合同' {{/compare}}">{{compare isDel '==' 1 }}  '无效合同' {{/else}} '有效合同' {{/compare}}</div>
		</td>
		<td>
			<div class="overflow_hidden w80" title="{{cancleRemark }}">{{cancleRemark }}</div>
		</td>
	</tr>
	{{/each}}
</script>
</body>
</html>
