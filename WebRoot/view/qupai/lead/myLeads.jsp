<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
<%@ include file="/common/include-cut-version.jsp"%>
<%@ include file="/common/common-function/function.table.jsp"%>
<%@ include file="/common/common-function/function.date.jsp"%>
<%@ include file="/common/common-function/function.searchHead.jsp"%>
<%@ include file="/common/common-function/function.handlebar.jsp"%>
<script>
	console.log(${mutilSearchCodeSortMapJson});
	console.log(${definedSearchCodesJson});
</script>
<script type="text/javascript" src="${ctx }/static/js/view/bill/render_table.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/button_auth.js${_v}"></script><!-- 按钮权限 -->
<script type="text/javascript" src="${ctx }/static/js/view/qupai/loan-common.js"></script>
<script type="text/javascript" src="${ctx }/static/js/view/qupai/my-leads.js"></script>
<style>
	.hyx-hsm-left dl{text-align:left;}
	.hyx-hsm-left dl dt{text-indent:25px;}
	.hyx-hsm-left dl dd{text-indent:5px;}
	.btn {
		font-family: microsoft yahei;
		font-size: 12px;
		outline: none;
		cursor: pointer;
		box-sizing: border-box;
		position: relative;
	}

	.btn:disabled {
		cursor: not-allowed;
	}

	.btn-84px {
		height: 25px;
		line-height: 25px;
		width: 84px;
		background-color: #fff;
		border: 1px solid #bbb;
		border-radius: 4px;
		margin-right: 15px;
		padding-left: 17px;
	}

	.btn-84px .icon {
		position: absolute;
		left: 2px;
		top: 4px;
	}

	.btn-84px:hover {
		background-color: #1979ca;
		color: #fff;
	}

	.btn-84px:hover .icon {
		background-image:url(${ctx}/static/images/all-sprite-white.png);
	}

	.item-list-head {
		background-color: #e1e1e1;
	}
</style>
<style type="text/css" rel="stylesheet">
<c:forEach items="${sorts}" var="sort">
	.com-table>table>tbody>tr>td:nth-child(${sort}),.com-table>table>thead>tr>th:nth-child(${sort}){display: none;}
</c:forEach>
</style>
</head>
<body>
	<form method="post"  data-render-url="${ctx}/credit/lead/myLeadsData"  id="unAssginForm">
		<input type="hidden" id="deptId" value="${deptId}" name="deptId">
		<input type="hidden" id="base" value="${ctx}">
		<input type="hidden" id="groupId" name="groupId" value="${groupId}">
		<input type="hidden" id="level" name="level" value="${level}">
		<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
		<input type="hidden" id="isShare" name="isShare" value="${isShare}"> <!-- 是否开启共享设置 -->
		<input type="hidden" id="isSharePool" name="isSharePool" value="${isSharePool}"><!-- 该资源组是否被共享 -->
		<input type="hidden" id="isState" value="${shiroUser.isState}" name="isState">
		<input type="hidden" id="account" value="${shiroUser.account}" >
		<div class="hyx-dfpzy-reso hyx-layout">
			<div class="hyx-hsm-right hyx-layout-content" style="border: none;">
				<div class="com-search hyx-cm-search">
					<div class="com-search-box fl_l">
						<div class="search clearfix" >
					   <div class="select_outerDiv fl_l">
						   <span class="icon_down_5px"></span>
				   			<select class="fl_l search_head_select"  name="queryType">
				   			        <c:if test="${shiroUser.isState == 0}">
                                        <option value="company"}>单位名称</option>
                                    </c:if>
									<c:if test="${ !empty tList && fn:length(tList) > 0 }">
										<c:forEach items="${tList}" var="defined" varStatus="vs">
											 <option value="${defined.searchCode }" ${item.queryType eq defined.searchCode? 'selected' :''}>${defined.searchName }</option>
								   		</c:forEach>
							   		</c:if>
					   		</select>
						   	</div>
							<input class="input-query fl_l" id="qKeyWordId" type="text" name="queryText" value="" /><label class="hide-span"></label>
						</div>
						<div class="list-box">
						<!-- 借款状态 -->
						<input type="hidden" name="status" id="status" value="">
			               <dl class="select pos${empty mutilSearchCodeSortMap['status'] ? '0' : mutilSearchCodeSortMap['status']}">
			                    <dt>放款状态</dt>
			                    <dd>
			                        <ul>
			                        	<li><a href="###" onclick="$('#status').val('');">放款状态</a></li>
			                            <li><a href="###" onclick="$('#status').val('1');">待放款</a></li>      
			                            <li><a href="###" onclick="$('#status').val('2');">已放款</a></li>    
			                            <li><a href="###" onclick="$('#status').val('3');">已驳回</a></li>           
			                        </ul>
			                    </dd>
			                </dl>   
						
						  <!-- 借款日 -->
							<dl class="select pos${empty mutilSearchCodeSortMap['borrowDate'] ? '0' : mutilSearchCodeSortMap['borrowDate']}">
								<input type="hidden" class="startDate" name="bstartDate" />
								<input type="hidden" class="endDate" name="bendDate" />
								<input type="hidden" class="type" name="bDateType" />
								<dt>借款日</dt>
								<dd>
									<ul>
										<li><a href="###" onclick="setDateType(this,0)"
											title="借款日">借款日</a></li>
										<li><a href="###" onclick="setDateType(this,1)"
											title="当天">当天</a></li>
										<li><a href="###" onclick="setDateType(this,2)"
											title="本周">本周</a></li>
										<li><a href="###" onclick="setDateType(this,3)"
											title="本月">本月</a></li>
										<li><a href="###" onclick="setDateType(this,4)"
											title="半年">半年</a></li>
										<li><a href="###" id="" title="自定义"
											class="diy date-range customize-time">自定义</a></li>
									</ul>
								</dd>
							</dl>
							<!-- 到期日-->
							<dl class="select pos${empty mutilSearchCodeSortMap['repayDate'] ? '0' : mutilSearchCodeSortMap['repayDate']}">
								<input type="hidden" class="startDate" name="rstartDate" />
								<input type="hidden" class="endDate" name="rendDate" />
								<input type="hidden" class="type" name="rDateType" />
								<dt>到期日</dt>
								<dd>
									<ul>
										<li><a href="###" onclick="setDateType(this,0)"
											title="到期日">到期日</a></li>
										<li><a href="###" onclick="setDateType(this,1)"
											title="当天">当天</a></li>
										<li><a href="###" onclick="setDateType(this,2)"
											title="本周">本周</a></li>
										<li><a href="###" onclick="setDateType(this,3)"
											title="本月">本月</a></li>
										<li><a href="###" onclick="setDateType(this,4)"
											title="半年">半年</a></li>
										<li><a href="###" id="" title="自定义"
											class="diy date-range customize-time">自定义</a></li>
									</ul>
								</dd>
							</dl>
							<!-- 创建人 -->
							 <div class="reso-sub-dep fl_l pos${empty mutilSearchCodeSortMap['ownerAcc'] ? '0' : mutilSearchCodeSortMap['ownerAcc']}">
							 		<c:choose>
										<c:when test="${shiroUser.issys eq 1 }">
											<input type="hidden" name="ownerAccs" class="tree-accs" value="${item.ownerAccs}">
											<input type="hidden" name="ownerType" class="tree-show-type" value="${item.ownerType}"/>
											<input class="owner-sour tree-show-value" id="comaccNames" data-placeholder="创建人"  type="text" value="创建人" style="border-right:2px solid #e1e1e1;">
										</c:when>
								 	</c:choose>
									<div class="manage-owner-sour" style="height:370px;" >
										<div class="shows-radio-boxs"></div>
										<ul class="shows-allorme-boxs">
											<li><input type="radio"  value="1"  name="searchOwnerType_2" ${item.ownerType eq '1' ? 'checked':'' }>查看全部</li>
											<li><input type="radio"  value="2"  name="searchOwnerType_2" ${item.ownerType eq '2' ? 'checked':'' }>查看自己</li>
										</ul>
										<div class="sub-dep-ul" style="width:190px;" data-type="search-tree">
											<ul id="founder-tree" class="member-tree">
										   </ul>
							    		</div>
									    <div class="sure-cancle clearfix" style="width:120px">
											<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" id="" href="javascript:;"><label>确定</label></a>
											<a class="com-btnd bomp-btna reso-sub-clear fl_l" id=""  href="javascript:;"><label>清空</label></a>
										</div>
									</div>
								</div>
							<!-- 负责人 -->
						 <div class="reso-sub-dep fl_l pos${empty mutilSearchCodeSortMap['inchargeAcc'] ? '0' : mutilSearchCodeSortMap['inchargeAcc']}">
								<c:choose>
									<c:when test="${shiroUser.issys eq 1 }">
										<input type="hidden" name="inchargeAccs" class="tree-accs" value="${item.inchargeAccs}">
										<input type="hidden" name="inchargeType" class="tree-show-type" value="${item.inchargeType}" />
										<input class="owner-sour tree-show-value" id="comaccNames" data-placeholder="负责人"  type="text" value="负责人" style="border-right:2px solid #e1e1e1;">
									</c:when>
							 	</c:choose>
								<div class="manage-owner-sour" style="height:370px;" >
									<div class="shows-radio-boxs"></div>
									<ul class="shows-allorme-boxs">
										<li><input type="radio"  value="1"  name="searchOwnerType_2" ${item.inchargeType eq '1' ? 'checked':'' }>查看全部</li>
										<li><input type="radio"  value="2"  name="searchOwnerType_2" ${item.inchargeType eq '2' ? 'checked':'' }>查看自己</li>
									</ul>
									<div class="sub-dep-ul" style="width:190px;" data-type="search-tree">
										<ul id="manager-tree" class="member-tree">
										 </ul>
									</div>
										<div class="sure-cancle clearfix" style="width:120px">
										<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" id="" href="javascript:;"><label>确定</label></a>
										<a class="com-btnd bomp-btna reso-sub-clear fl_l" id=""  href="javascript:;"><label>清空</label></a>
									</div>
								</div>
							</div>
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
				<input class="query-button fl_r" type="button"  value="查&nbsp;询" id="query"/>
				<%-- <input class="query-button fl_r" type="button" value="高级查询"  id="superQuery" data-url="${ctx }/highSearch/toPage?module=9" data-title="高级查询" data-param="&groupId=${groupId }&shareGroupIds=${shareGroupIds }" /> --%>
				<c:if test="${mutilSearchCodeSortMap.keySet().size() gt 5 }"><a href="javascript:;" class="more"><i></i>更多</a></c:if>
				</div>
				<div class="com-btnlist hyx-cm-btnlist fl_l">
					 <button type="button"   id="deleteRes" class="fl_l btn btn-84px"><i class="min-dele icon"></i>删除</button>
					 <button type="button"   id="addLead" class="fl_l btn btn-84px" ><i class="min-new-add icon"></i>新增</button>
					 <button type="button" data-authority="makeLoans_Manage_impor"  id="importId" class="fl_l btn btn-84px" ><i class="min-into-resou icon"></i>导入</button>
					 <button type="button" data-authority="makeLoans_Manage_importResult"  id="importResultId" class="fl_l btn btn-84px" ><i class="min-into-resou icon"></i>导入结果</button>
					 <button type="button" data-authority="makeLoans_Manage_export"  id="output" class="fl_l btn btn-84px" ><i class="min-remove-resou icon"></i>导出</button>
				</div>
				<div class="hid clearfix" style="display: block;">
					<div class="com-table com-mta test-table fl_l">
						<table width="100%" cellspacing="0" cellpadding="0" id="t1"
							class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
							<thead>
								<tr  role="head" class="sty-bgcolor-b">
									<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll"  class="check" /></span></th>
									<th><span class="sp sty-borcolor-b">操作</span></th>
									<th><span class="sp sty-borcolor-b">放款编号</th>
									<th><span class="sp sty-borcolor-b">${shiroUser.isState eq '0' ? '单位名称' : '客户名称'}</span></th>
									<c:if test="${shiroUser.isState eq '0'}" ><th><span class="sp sty-borcolor-b">客户姓名</span></th></c:if>
									<th><span class="sp sty-borcolor-b">身份证号</span></th>
									<th><span class="sp sty-borcolor-b">联系电话</span></th>
									<th><span class="sp sty-borcolor-b">放款状态</span></th>
									<th><span class="sp sty-borcolor-b">借款金额</span></th>
									<th><span class="sp sty-borcolor-b">用户到账金额</span></th>
									<th><span class="sp sty-borcolor-b">综合服务费</span></th>
									<th><span class="sp sty-borcolor-b">服务费2</span></th>
									<th><span class="sp sty-borcolor-b">服务费3</span></th>
							   	    <th><span class="sp sty-borcolor-b">借据金额</span></th>
									<th><span class="sp sty-borcolor-b">借款日</span></th>
									<th><span class="sp sty-borcolor-b">到期日</span></th>
									<th><span class="sp sty-borcolor-b">银行卡号</span></th>
									<th><span class="sp sty-borcolor-b">出借人</span></th>
									<th><span class="sp sty-borcolor-b">开户行</span></th>
									<th><span class="sp sty-borcolor-b">借款凭证</span></th>
									<th><span class="sp sty-borcolor-b">创建人</span></th>
									<th><span class="sp sty-borcolor-b">团队名称</span></th>
									<th><span class="sp sty-borcolor-b">打款账户</span></th>
									<th><span class="sp sty-borcolor-b">负责人</span></th>
									<th><span class="sp sty-borcolor-b">放贷批次</span></th>
									<th><span class="sp sty-borcolor-b" data-defined="defined1">${definedNameMap["defined1"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined2">${definedNameMap["defined2"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined3">${definedNameMap["defined3"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined4">${definedNameMap["defined4"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined5">${definedNameMap["defined5"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined6">${definedNameMap["defined6"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined7">${definedNameMap["defined7"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined8">${definedNameMap["defined8"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined9">${definedNameMap["defined9"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined10">${definedNameMap["defined10"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined11">${definedNameMap["defined11"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined12">${definedNameMap["defined12"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined13">${definedNameMap["defined13"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined14">${definedNameMap["defined14"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined15">${definedNameMap["defined15"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined16">${definedNameMap["defined16"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined17">${definedNameMap["defined17"]}</span></th>
							   		<th><span class="sp sty-borcolor-b" data-defined="defined18">${definedNameMap["defined18"]}</span></th>
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
				<!-- <p class="reminder" style="font-size: 14px;"><span class="com-red">温馨提示：</span>用户可以查看资源的录入部门是所属管辖权限范围内的数据</p> -->
			</div>
		</div>
	</form>
	<script type="text/x-handlebars-template" id="template">
	  {{#each list}}
		<tr class="{{even_odd @index}}">

			<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input name="check_" id="chk_{{leadId }}" data-status={{status}} type="checkbox"  /></div></td>
			<td><div class="overflow_hidden w120">{{leadOperate leadId ../isState rciId custName company status}}</div></td>
			<td><div class="overflow_hidden w70" title="{{leadCode }}">{{leadCode }}</div></td>
			{{#if ../isState}}
				<td>
					<div class="overflow_hidden w100" title="{{custName }}">{{custName }}</div>
				</td>
			{{else}}
				<td>
	           		<div class="overflow_hidden w100" title="{{company }}">{{company }}</div>
	       		</td>
				<td>
					<div class="overflow_hidden w100" title="{{custName }}">{{custName }}</div>
				</td>
			{{/if}}
			<td><div class="overflow_hidden w120" title="{{cardId }}">{{cardId }}</div></td>
			<td><div class="overflow_hidden w70" data-isreplaceword="true" title="{{phone }}">{{phone }}</div></td>
			<td><div class="overflow_hidden w70" title="{{judgeLeadStatus status }}">{{judgeLeadStatus status }}</div></td>
			<td><div class="overflow_hidden w70" title="{{formatMoney borrowMoney }}">{{formatMoney borrowMoney }}</div></td>
			<td><div class="overflow_hidden w70" title="{{formatMoney accountMoney }}">{{formatMoney accountMoney }}</div></td>
			<td><div class="overflow_hidden w70" title="{{formatMoney serviceMoney }}">{{formatMoney serviceMoney }}</div></td>
			<td><div class="overflow_hidden w70" title="{{formatMoney serviceMoney2 }}">{{formatMoney serviceMoney2 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{formatMoney serviceMoney3 }}">{{formatMoney serviceMoney3 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{formatMoney billMoney }}">{{formatMoney billMoney }}</div></td>
			<td><div class="overflow_hidden w100" title="{{formatDate borrowDate 'YYYY-MM-DD'}}">{{formatDate borrowDate 'YYYY-MM-DD'}}</div></td>
			<td><div class="overflow_hidden w100" title="{{formatDate repayDate 'YYYY-MM-DD'}}">{{formatDate repayDate 'YYYY-MM-DD'}}</div></td>
			<td><div class="overflow_hidden w70" title="{{bankCard }}">{{bankCard }}</div></td>
			<td><div class="overflow_hidden w70" title="{{lender }}">{{lender }}</div></td>
			<td><div class="overflow_hidden w70" title="{{openingBank }}">{{openingBank }}</div></td>
			<td><div class="overflow_hidden w70" title="{{loanBill }}">{{loanBillPicture loanBill}}</div></td>
			<td><div class="overflow_hidden w70" title="{{ownerName }}">{{ownerName }}</div></td>
			<td><div class="overflow_hidden w70" title="{{importDeptName }}">{{importDeptName }}</div></td>
			<td><div class="overflow_hidden w70" title="{{fundAccount }}">{{fundAccount }}</div></td>
			<td><div class="overflow_hidden w70" title="{{inchargeName }}">{{inchargeName }}</div></td>
			<td><div class="overflow_hidden w70" title="{{batch }}">{{batch }}</div></td>
			<td><div class="overflow_hidden w70" title="{{defined1 }}">{{defined1 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{defined2 }}">{{defined2 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{defined3 }}">{{defined3 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{defined4 }}">{{defined4 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{defined5 }}">{{defined5 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{defined6 }}">{{defined6 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{defined7 }}">{{defined7 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{defined8 }}">{{defined8 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{defined9 }}">{{defined9 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{defined10 }}">{{defined10 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{defined11 }}">{{defined11 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{defined12 }}">{{defined12 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{defined13 }}">{{defined13 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{defined14 }}">{{defined14 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{defined15 }}">{{defined15 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{showdefined16 }}">{{showdefined16 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{showdefined17 }}">{{showdefined17 }}</div></td>
			<td><div class="overflow_hidden w70" title="{{showdefined18 }}">{{showdefined18 }}</div></td>
	    </tr>
	 {{/each}}
	</script>
</body>
</html>
