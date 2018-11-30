<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
 <%@ include file="/common/include.jsp" %>
 <%@ include file="/common/common-function/function.area.select.jsp"%>
  <%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
 <title>自定义报表-新建报表</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
<script type="text/javascript">
var bean = ${bean}
var item = ${item}
var definArr = []
<c:forEach var="defin" items="${singleDefineds }">definArr.push({name:"${defin.fieldCode}",match:"${defin.fieldCode}Match",type:"single"});</c:forEach>
<c:forEach var="defin" items="${multipleDefineds }">definArr.push({name:"${defin.fieldCode}",match:"${defin.fieldCode}Match",type:"multi"});</c:forEach>
</script>
<link rel="stylesheet" href="${ctx}/static/js/stepbar/css/control.css" type="text/css" /><!--步骤-->
<script type="text/javascript" src="${ctx}/static/js/stepbar/js/stepbar.js${_v}"></script><!--步骤-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/custReport.css${_v}" />
<script type="text/javascript" src="${ctx}/static/js/view/report/editCustReport.js${_v}"></script>
<style id="aa0">
.choose-data-filter-select>option:nth-of-type(1){display:none;}
</style>
</head>
<body>
<input type="hidden" id="oldName" value="${item.customReportName }">
<div class="cust-impo-reso">
	<div class="stepBar-box">
		<div id="stepBar" class="ui-stepBar-wrap">
			<div class="ui-stepBar">
				<div class="ui-stepProcess"></div>
			</div>
			<div class="ui-stepInfo-wrap">
				<table class="ui-stepLayout" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="ui-stepInfo">
							<a class="ui-stepSequence01">1</a>
							<p class="ui-stepName">选择数据范围</p>
						</td>
						<td class="ui-stepInfo">
							<a class="ui-stepSequence01">2</a>
							<p class="ui-stepName">设置报表维度</p>
						</td>
						<td class="ui-stepInfo">
							<a class="ui-stepSequence01">3</a>
							<p class="ui-stepName">命令并保存</p>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<form id="addReportForm">
	<input type="hidden" id="customReportId" name="customReportId"  value="${item.customReportId }">
	<div class="new-custreport-step-first new-custreport-step" >
		<div class="step-top-operation-box step-top-operation-box-addbox">
			<div class="step-top-operation-box-item">
				<span class="step-top-operation-box-item-title">设置数据筛选条件：</span>
				<a href="javascript:;" class="add-more-condition" data-num="0">+添加更多条件</a>
			</div>
			<%-- <div class="step-top-operation-box-item screening-conditions">
				<span class="and">AND</span>
				<select class="choose-data-filter-select" data-select-id="aa{{num}}">
					<option data-type="please-choose">--请选择--</option>
					<option data-type="select-data" data-condition="custTypeMatch" data-ajaxurl="${ctx}/custom/report/getCustTypeList" data-name="custTypeId">客户类型</option>
					<option data-type="select-fixed" data-fixed-name="qStatus" data-condition="qStatusMatch">客户状态</option>
					<option data-type="select-data" data-condition="optionlistMatch" data-ajaxurl="${ctx}/custom/report/getSaleProcessList" data-name="optionlistId">销售进程</option>
					<option data-type="tree" data-condition="ownerAccMatch" data-treeurl="${ctx }/orgGroup/get_group_user_json" data-change="user">所有者</option>
					<option data-type="select-data" data-condition="resGroupMatch" data-ajaxurl="${ctx}/custom/report/getGroupList" data-name="resGroupId" data-resource="true">资源分组</option>
					<option data-type="time" data-time-start="actionDateStart" data-time-end="actionDateEnd" data-condition="between">最近联系时间</option>
					<option data-type="time" data-time-start="nextFollowDateStart" data-time-end="nextFollowDateEnd" data-condition="between">下次联系时间</option>
					<option data-type="time" data-time-start="amoytocustomerDateStart" data-time-end="amoytocustomerDateEnd" data-condition="between">淘到客户时间</option>
					<option data-type="time" data-time-start="inputDateStart" data-time-end="inputDateEnd" data-condition="between">创建时间</option>
					<option data-type="time" data-time-start="signDateStart" data-time-end="signDateEnd" data-condition="between">签约时间</option>
					<option data-type="select-data" data-condition="companyTradeMatch" data-ajaxurl="${ctx}/custom/report/getCompanyTradeList" data-name="companyTrade">所属行业</option>
					<option data-type="area" data-condition="areaMatch">所在地区</option>
					<option data-type="time" data-time-start="ownerStartDateStart" data-time-end="ownerStartDateEnd" data-condition="between">分配时间</option>
					<option data-type="tree" data-condition="importDeptMatch" data-treeurl="${ctx }/orgGroup/get_all_group_json" data-change="group">资源录入部门</option>
					<option data-type="select-fixed" data-fixed-name="source" data-condition="sourceMatch">客户来源</option>
					<option data-type="select-fixed" data-fixed-name="opreateType" data-condition="opreateTypeMatch">放弃类型</option>
					<c:forEach var="defin" items="${singleDefineds }">
							<option data-type="select-single-data" data-ajaxurl="${ctx}/custom/report/getSingleDefinedList" data-name="${defin.fieldCode}" data-condition="${defin.fieldCode}Match">${defin.fieldName}</option>
					</c:forEach>
					<c:forEach var="defin" items="${multipleDefineds }">
							<option data-type="select-multi-data" data-ajaxurl="${ctx}/custom/report/getMultipleDefinedList" data-name="${defin.fieldCode}" data-condition="multi" data-condition-name="${defin.fieldCode}Match">${defin.fieldName}</option>
					</c:forEach>
				</select>
				<select class="condition-select"><option value="0">等于</option><option value="1">不等于</option></select>
				<div class="step-choose-box com-search"><input type="text"/></div>
				<span class="fa fa-times delete-icons"></span>
			</div> --%>
		</div>
		<div class="step-middle-remind-box">
			说明提示：客户属性分析报表统计的数据范围为企业当前的全部客户数据；可以根据自身需求，设置不同的数据范围
		</div>
		<div class="step-bottom-btn-box">
			<button type="button" class="next-tips">下一步</button>
		</div>
	</div>
	<div class="new-custreport-step-second new-custreport-step" style="display:none;">
		<div class="step-top-operation-box">
			<div class="step-top-operation-box-item">
				<span class="step-top-operation-box-item-title">请选择表格样式：</span>
				<label class="items-radio-box"><input type="radio"  name="style"  <c:if test="${empty bean.groupByType2 }">checked</c:if> class="radio-change-type" data-type="tabular-list"/>列表式</label>
				<label class="items-radio-box"><input type="radio"  name="style"  <c:if test="${!empty bean.groupByType2 }">checked</c:if> class="radio-change-type" data-type="matrix-formula"/>矩阵式</label>
			</div>
			<div class="tabular-list-show" <c:if test="${!empty bean.groupByType2 }">style="display:none;"</c:if> >
				<div class="step-top-operation-box-example">
					<img src="${ctx }/static/images/tabular_list_of_report.png" alt="列表式"/>
				</div>
				<div class="step-top-operation-box-item">
					<select name="groupByType1" class="chooseType" <c:if test="${!empty bean.groupByType2 }">disabled="disabled" </c:if>>
						<option value="" data-type="系列">点击选择系列</option>
						<option value="last_cust_type_id" <c:if test="${empty bean.groupByType2 && bean.groupByType1 eq 'last_cust_type_id'}">selected  </c:if> >客户类型</option>
						<option value="qStatus" <c:if test="${empty bean.groupByType2 && bean.groupByType1 eq 'qStatus'}">selected  </c:if> >客户状态</option>
						<option value="last_option_Id" <c:if test="${empty bean.groupByType2 && bean.groupByType1 eq 'last_option_Id'}">selected  </c:if> >销售进程</option>
						<%-- <option value="import_dept_id" <c:if test="${empty bean.groupByType2 && bean.groupByType1 eq 'import_dept_id'}">selected  </c:if> >所属部门</option> --%>
						<option value="res_group_id" <c:if test="${empty bean.groupByType2 && bean.groupByType1 eq 'res_group_id'}">selected  </c:if> >资源分组</option>
						<c:if test="${shiroUser.isState eq	1}"><option value="company_trade" <c:if test="${empty bean.groupByType2 && bean.groupByType1 eq 'company_trade'}">selected  </c:if> >所属行业</option></c:if>
						<option value="opreate_type" <c:if test="${empty bean.groupByType2 && bean.groupByType1 eq 'opreate_type'}">selected  </c:if> >放弃类型</option>
						<c:forEach var="defin" items="${singleDefineds }">
							<option value="${defin.fieldCode}"  <c:if test="${empty bean.groupByType2 && bean.groupByType1 eq defin.fieldCode}">selected  </c:if> >${defin.fieldName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="matrix-formula-show"  <c:if test="${empty bean.groupByType2 }"> style="display:none;" </c:if>>
				<div class="step-top-operation-box-example">
					<img src="${ctx }/static/images/matrix_formula_of_report.png" alt="矩阵式"/>
				</div>
				<div class="step-top-operation-box-item">
					<select name="groupByType1" <c:if test="${empty bean.groupByType2 }">disabled="disabled"</c:if>  class="chooseType">
						<option value="" data-type="类别">点击选择类别</option>
						<option value="last_cust_type_id" <c:if test="${!empty bean.groupByType2 && bean.groupByType1 eq 'last_cust_type_id'}">selected  </c:if>>客户类型</option>
						<option value="qStatus" <c:if test="${!empty bean.groupByType2 && bean.groupByType1 eq 'qStatus'}">selected  </c:if> >客户状态</option>
						<option value="last_option_Id" <c:if test="${!empty bean.groupByType2 && bean.groupByType1 eq 'last_option_Id'}">selected  </c:if> >销售进程</option>
					<%-- 	<option value="import_dept_id" <c:if test="${!empty bean.groupByType2 && bean.groupByType1 eq 'import_dept_id'}">selected  </c:if> >所属部门</option> --%>
						<option value="res_group_id" <c:if test="${!empty bean.groupByType2 && bean.groupByType1 eq 'res_group_id'}">selected  </c:if> >资源分组</option>
						<c:if test="${shiroUser.isState eq	1}"><option value="company_trade" <c:if test="${!empty bean.groupByType2 && bean.groupByType1 eq 'company_trade'}">selected  </c:if> >所属行业</option></c:if>
						<option value="opreate_type" <c:if test="${!empty bean.groupByType2 && bean.groupByType1 eq 'opreate_type'}">selected  </c:if> >放弃类型</option>
						<c:forEach var="defin" items="${singleDefineds }">
							<option value="${defin.fieldCode}"  <c:if test="${!empty bean.groupByType2 && bean.groupByType1 eq defin.fieldCode}">selected  </c:if> >${defin.fieldName}</option>
						</c:forEach>
					</select>
					<select name="groupByType2" <c:if test="${empty bean.groupByType2 }">disabled="disabled"</c:if> class="chooseType">
						<option value="" data-type="系列">点击选择系列</option>
						<option value="last_cust_type_id" <c:if test="${!empty bean.groupByType2 && bean.groupByType2 eq 'last_cust_type_id'}">selected  </c:if> >客户类型</option>
						<option value="qStatus" <c:if test="${!empty bean.groupByType2 && bean.groupByType2 eq 'qStatus'}">selected  </c:if> >客户状态</option>
						<option value="last_option_Id" <c:if test="${!empty bean.groupByType2 && bean.groupByType2 eq 'last_option_Id'}">selected  </c:if> >销售进程</option>
						<%-- <option value="import_dept_id" <c:if test="${!empty bean.groupByType2 && bean.groupByType2 eq 'import_dept_id'}">selected  </c:if> >所属部门</option> --%>
						<option value="res_group_id" <c:if test="${!empty bean.groupByType2 && bean.groupByType2 eq 'res_group_id'}">selected  </c:if> >资源分组</option>
						<c:if test="${shiroUser.isState eq	1}"><option value="company_trade" <c:if test="${!empty bean.groupByType2 && bean.groupByType2 eq 'company_trade'}">selected  </c:if> >所属行业</option></c:if>
						<option value="opreate_type" <c:if test="${!empty bean.groupByType2 && bean.groupByType2 eq 'opreate_type'}">selected  </c:if> >放弃类型</option>
						<c:forEach var="defin" items="${singleDefineds }">
							<option value="${defin.fieldCode}"  <c:if test="${!empty bean.groupByType2 && bean.groupByType2 eq defin.fieldCode}">selected  </c:if> >${defin.fieldName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="step-middle-remind-box tabular-list-show" <c:if test="${!empty bean.groupByType2 }"> style="display:none;" </c:if> >
			说明提示：列表式表格仅用以统计单维度情况下的客户数据，报表生成后可查看全部结果
		</div>
		<div class="step-middle-remind-box matrix-formula-show" <c:if test="${empty bean.groupByType2 }"> style="display:none;" </c:if>>
			说明提示：矩阵式表格用以统计双维度情况下的交叉条件分析，报表生成后可查看全部结果
		</div>
		<div class="step-bottom-btn-box">
			<button type="button" class="prev-tips">上一步</button>
			<button type="button" class="next-tips">下一步</button>
		</div>
	</div>
	<div class="new-custreport-step-third new-custreport-step" style="display:none;">
		<div class="step-finish-input-box">
			图表名称：
			<input type="text" class="finish-named-input" name="customReportName" value="${item.customReportName }"/>
		</div>
		<div class="step-bottom-btn-box">
			<button type="button" class="prev-tips">上一步</button>
			<button type="button" class="btn-finished">完成</button>
		</div>
	</div>
	</form>
</div>
<script type="text/x-handlebars-template" id="templateItem">
<div class="step-top-operation-box-item screening-conditions screening-aa{{num}}">
				<span class="and">AND</span>
				<select class="choose-data-filter-select" data-select-id="aa{{num}}">
					<option data-type="please-choose">--请选择--</option>
					<option data-type="select-data" data-condition="custTypeMatch" data-ajaxurl="${ctx}/custom/report/getCustTypeList" data-name="custTypeId" data-identification="custTypeId">客户类型</option>
					<option data-type="select-fixed" data-fixed-name="qStatus" data-condition="qStatusMatch" data-identification="qStatus">客户状态</option>
					<option data-type="select-data" data-condition="optionlistMatch" data-ajaxurl="${ctx}/custom/report/getSaleProcessList" data-name="optionlistId" data-identification="optionlistId">销售进程</option>
					<option data-type="tree" data-condition="ownerAccMatch" data-treeurl="${ctx }/orgGroup/get_all_group_user_json" data-change="user" data-identification="accs">所有者</option>
					<option data-type="tree" data-condition="resGroupMatch" data-treeurl="${ctx}/custom/report/getGroupList" data-change="res" data-identification="resGroupId">资源分组</option>
					<option data-type="time" data-time-start="actionDateStart" data-time-end="actionDateEnd" data-condition="between" data-identification="actionDateStart">最近联系时间</option>
					<option data-type="time" data-time-start="nextFollowDateStart" data-time-end="nextFollowDateEnd" data-condition="between" data-identification="nextFollowDateStart">下次联系时间</option>
					<option data-type="time" data-time-start="amoytocustomerDateStart" data-time-end="amoytocustomerDateEnd" data-condition="between" data-identification="amoytocustomerDateStart">淘到客户时间</option>
					<option data-type="time" data-time-start="inputDateStart" data-time-end="inputDateEnd" data-condition="between" data-identification="inputDateStart">创建时间</option>
					<option data-type="time" data-time-start="signDateStart" data-time-end="signDateEnd" data-condition="between" data-identification="signDateStart">签约时间</option>
					<c:if test="${shiroUser.isState eq	1}"><option data-type="select-data" data-condition="companyTradeMatch" data-ajaxurl="${ctx}/custom/report/getCompanyTradeList" data-name="companyTrade" data-identification="companyTrade">所属行业</option>
					<option data-type="area" data-condition="areaMatch" data-identification="provinceId">所在地区</option></c:if>
					<option data-type="time" data-time-start="ownerStartDateStart" data-time-end="ownerStartDateEnd" data-condition="between" data-identification="ownerStartDateStart">分配时间</option>
					<option data-type="tree" data-condition="importDeptMatch" data-treeurl="${ctx }/orgGroup/get_all_group_json" data-change="group" data-identification="importDeptId">资源录入部门</option>
					<option data-type="select-fixed" data-fixed-name="source" data-condition="sourceMatch" data-identification="source">客户来源</option>
					<option data-type="select-fixed" data-fixed-name="opreateType" data-condition="opreateTypeMatch" data-identification="opreateType">放弃类型</option>
					<c:forEach var="defin" items="${singleDefineds }">
							<option data-type="select-multi-data" data-ajaxurl="${ctx}/custom/report/getSingleDefinedList" data-name="${defin.fieldCode}" data-condition="${defin.fieldCode}Match" data-identification="${defin.fieldCode}">${defin.fieldName}</option>
					</c:forEach>
					<c:forEach var="defin" items="${multipleDefineds }">
							<option data-type="select-multi-data" data-ajaxurl="${ctx}/custom/report/getMultipleDefinedList" data-name="${defin.fieldCode}" data-condition="multi" data-condition-name="${defin.fieldCode}Match" data-identification="${defin.fieldCode}">${defin.fieldName}</option>
					</c:forEach>
				</select>
				<select class="condition-select"><option value="0">等于</option><option value="1">不等于</option></select>
				<div class="step-choose-box com-search"><input type="text"/></div>
				<span class="fa fa-times delete-icons"></span>
				<span class="reminder"></span>
			</div>
</script>
<script type="text/x-handlebars-template" id="templateTime">
	<input type="hidden" name="{{startTime}}" class="startTime"/><input type="hidden" name="{{endTime}}" class="endTime"/>
	<input type="text" class="date"/>
</script>
<script type="text/x-handlebars-template" id="templateTree">
	<input type="text" class="tree"/>
	<div class="manage-drops fl_l" >
	<input type="hidden" name="{{names}}" class="tree-hidden"/>
								<ul class="tt1">
	
								</ul>
								<div class="sure-cancle clearfix" style="width: 120px">
									<a class="com-btnc bomp-btna com-btna-sty fl_l checkedId"
														href="javascript:;" ><label>确定</label></a>
									<a	class="com-btnd bomp-btna fl_l clearId"
														href="javascript:;"><label>清空</label></a>
								</div>
						</div>
</script>
<script type="text/x-handlebars-template" id="templateArea">
	<input type="text" class="area"/>
</script>
<script type="text/x-handlebars-template" id="templateSelectFix">
<dl class="select" data-input="[name='{{fixedname}}']" data-multi="true">
								<dt>--请选择--</dt>
								<dd>
									<ul>
										{{#each list}}
										<li><a href="javascript:;" data-value="{{value}}" title="{{name}}">{{name}}</a></li>
										{{/each}}
									</ul>
								</dd>
								 <input name="{{fixedname}}" type="hidden">
						   </dl>
</script>
<script type="text/x-handlebars-template" id="templateSelectData">
<dl class="select" data-input="[name='{{name}}']" data-multi="true">
								<dt>--请选择--</dt>
								<dd>
									<ul>
										{{#each list}}
										<li><a href="javascript:;" data-value="{{optionlistId}}" title="{{optionName}}">{{optionName}}</a></li>
										{{/each}}
									</ul>
								</dd>
								 <input name="{{name}}" type="hidden">
						   </dl>
</script>
<script type="text/x-handlebars-template" id="templateMultiData">
<dl class="select" data-input="[name='{{name}}']" data-multi="true">
								<dt>--请选择--</dt>
								<dd>
									<ul>
										{{#each list}}
										<li><a href="javascript:;" data-value="{{optionlistId}}" title="{{optionName}}">{{optionName}}</a></li>
										{{/each}}
									</ul>
								</dd>
								 <input name="{{name}}" type="hidden">
						   </dl>
</script>
</body>
</html>
