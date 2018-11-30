<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@page import="com.qftx.base.enums.FollowCustEnum"%>
<%@ include file="/common/include-cut-version.jsp"%>
<%@ include file="/common/common-function/function.table.jsp"%>
<%@ include file="/common/common-function/function.date.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/font-awesome-4.7.0/css/font-awesome.min.css${_v}"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/gray/easyui.css${_v}" />
<script type="text/javascript" src="${ctx}/static/js/handlebars-v4.0.10.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/handlebar.helper.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/thirdparty/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/common_business.js${_v}"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/newdaily_plan.css${_v}"/>
<script type="text/javascript" src="${ctx}/static/js/view/plan/dailyplan/dailyPlan.js${_v}"></script>
<title>日计划总览页面</title>
</head>
<body>
	<input type="hidden"  id="weekStart"  value="<fmt:formatDate value="${week[0] }" pattern="yyyy-MM-dd"/>"/>
	<input type="hidden"  id="weekEnd"  value="<fmt:formatDate value="${week[1] }" pattern="yyyy-MM-dd"/>"/>
	<input type="hidden" id="currTime"  value="${currTime }"/>
	<input type="hidden"  id="weekNum" value="${weekNum }"/>
	<div class="new-daily-plan-box-head">
	<form id="daily-plan-form">
	<input type="hidden" id="weekIndex" name="weekIndex" value="${weekIndex }"/>
		<div class="new-daily-plan-box-head-left-select">
			
			<select class="new-daily-box-head-select-detail select-month w110 " name="dateStr">
				
			</select>
			<select class="new-daily-box-head-select-detail select-week w80" name="week">
			
			</select>
			
			<input type="hidden" id="userAcc" name="userAcc" value="${user.account }" />
            <input type="hidden" id="userId" name="userId" value="${user.id }" />
            <input type="hidden" id="userName" name="userName" value="${user.name }" />
			<c:if test="${user.issys eq 1 }">
			<div class="com-search-report fl_r" style="margin-top:11px;">
		            <dl class="select fl_l" style="position:relative;z-index:555;width:90px">
						<dt id="groupNameStr">-请选择-</dt>
						<dd>
							<ul id="tt1">
									
							</ul>
							<div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;">
								<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" onclick="selGroup()" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">确定</label></a>
								<a class="com-btnd bomp-btna com-btna-cancle fl_l" onclick="clearGroup()" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">清空</label></a>
							</div>
						</dd>
					</dl>
		        </div>
		        </c:if>
		</div>
		</form>
		<div class="new-daily-plan-box-head-right-items">
			<span class="new-daily-color-show new-daily-color-green"></span>
			<span class="new-daily-font-behind">已安排</span>
			<span class="new-daily-color-show new-daily-color-red"></span>
			<span class="new-daily-font-behind">已联系</span>
			<span class="new-daily-color-show new-daily-color-gray"></span>
			<span class="new-daily-font-behind">未联系</span>
		</div>
		<span class="new-daily-plan-box-head-title"><span class="new-daily-plan-box-head-title-month"></span>月份日计划（第<span class="new-daily-plan-box-head-title-weekIndex"></span>周） <b class="start-date"><fmt:formatDate value="${week[0] }" pattern="yyyy年MM月dd日"/></b>-<b class="end-date"><fmt:formatDate value="${week[1] }" pattern="yyyy年MM月dd日"/></b></span>
	</div>
	<div class="new-daily-plan-box-body">
		<div class="new-daily-plan-box">
			<!-- 数据注入 -->
		</div>
	</div>
	
	
<script type="text/x-handlebars-template" id="template">
  {{#each list}}
	<div class="new-daily-plan-box-body-row" data-id="{{id}}" data-dateStr="{{planDateStr}}">
			{{#if planFlag}}
			{{#compare todayFlag "!=" 1}}
				{{#compare todayFlag "==" 0}}
					<div class="new-daily-plan-box-body-row-left today">
				{{else}}
					<div class="new-daily-plan-box-body-row-left">
				{{/compare}}
				<span class="new-daily-plan-box-body-row-left-week">星期{{planDateWeekStr}}</span>
				<span class="new-daily-plan-box-body-row-left-date">{{planDateStr}}</span>
			</div>
				{{#compare resourceCount "!=" 0}}
			<div class="new-daily-plan-box-body-row-right-items">
					<div class="new-daily-plan-box-body-row-right-items-title-today" data-type="1" data-status="">资源客户数</div>
					<div class="new-daily-plan-box-body-row-right-items-process">
						<div class="new-daily-plan-box-body-row-right-items-process-box">
						 {{#compare resourceCount "==" resourceContactCount}}
								<div class="new-daily-plan-box-body-row-right-items-process-true" style="width:{{finishPercentage resourceCount resourceContactCount}};background-color:#0767c8;"></div>
							{{else}}
								<div class="new-daily-plan-box-body-row-right-items-process-true" style="width:{{finishPercentage resourceCount resourceContactCount}};"></div>
							{{/compare}} 
						</div>
						<div class="new-daily-plan-box-body-row-right-items-process-number">{{finishPercentage resourceCount resourceContactCount}}</div>
					</div>
					<div class="new-daily-plan-box-body-row-right-items-prodetail">
						<span class="click-into-detail-span" data-type="1" data-status=""><span class="new-daily-color-show new-daily-color-green"></span>
						<span class="new-daily-font-behind">{{resourceCount}}</span></span>
						<span class="click-into-detail-span" data-type="1" data-status="1"><span class="new-daily-color-show new-daily-color-red"></span>
						<span class="new-daily-font-behind">{{resourceContactCount}}</span></span>
						<span class="click-into-detail-span" data-type="1" data-status="0"><span class="new-daily-color-show new-daily-color-gray"></span>
						<span class="new-daily-font-behind">{{numSubtract resourceCount resourceContactCount}}</span></span>
					</div>
				</div>
				{{/compare}}
				{{#compare willcustCount "!=" 0}}
				<div class="new-daily-plan-box-body-row-right-items">
					<div class="new-daily-plan-box-body-row-right-items-title-today" data-type="2" data-status="">意向客户数</div>
					<div class="new-daily-plan-box-body-row-right-items-process">
						<div class="new-daily-plan-box-body-row-right-items-process-box">
 							{{#compare willcustCount "==" willcustContactCount}}
								<div class="new-daily-plan-box-body-row-right-items-process-true" style="width:{{finishPercentage willcustCount willcustContactCount}};background-color:#0767c8;"></div>
							{{else}}
								<div class="new-daily-plan-box-body-row-right-items-process-true" style="width:{{finishPercentage willcustCount willcustContactCount}};"></div>
							{{/compare}} 
						</div>
						<div class="new-daily-plan-box-body-row-right-items-process-number">{{finishPercentage willcustCount willcustContactCount}}</div>
					</div>
					<div class="new-daily-plan-box-body-row-right-items-prodetail">
						<span class="click-into-detail-span" data-type="2" data-status=""><span class="new-daily-color-show new-daily-color-green"></span>
						<span class="new-daily-font-behind">{{willcustCount}}</span></span>
						<span class="click-into-detail-span" data-type="2" data-status="1"><span class="new-daily-color-show new-daily-color-red"></span>
						<span class="new-daily-font-behind">{{willcustContactCount}}</span></span>
						<span class="click-into-detail-span" data-type="2" data-status="0"><span class="new-daily-color-show new-daily-color-gray"></span>
						<span class="new-daily-font-behind">{{numSubtract willcustCount willcustContactCount}}</span></span>
					</div>
				</div>
				{{/compare}}
			{{#each willCustIndexlist}}
			<div class="new-daily-plan-box-body-row-right-items">
				<div class="new-daily-plan-box-body-row-right-items-title-today" data-type="2" data-status="" data-custStatusId="{{custStatusId}}">{{custStatus}}</div>
				<div class="new-daily-plan-box-body-row-right-items-process">
					<div class="new-daily-plan-box-body-row-right-items-process-box">
							{{#compare willcustCount "==" willcustContactCount}}
								<div class="new-daily-plan-box-body-row-right-items-process-true" style="width:{{finishPercentage willcustCount willcustContactCount}};background-color:#0767c8;"></div>
							{{else}}
								<div class="new-daily-plan-box-body-row-right-items-process-true" style="width:{{finishPercentage willcustCount willcustContactCount}};"></div>
							{{/compare}} 
					</div>
					<div class="new-daily-plan-box-body-row-right-items-process-number">{{finishPercentage willcustCount willcustContactCount}}</div>
				</div>
				<div class="new-daily-plan-box-body-row-right-items-prodetail">
					<span class="click-into-detail-span" data-type="2" data-status="" data-custStatusId="{{custStatusId}}"><span class="new-daily-color-show new-daily-color-green"></span>
					<span class="new-daily-font-behind">{{willcustCount}}</span></span>
					<span class="click-into-detail-span" data-type="2" data-status="1" data-custStatusId="{{custStatusId}}"><span class="new-daily-color-show new-daily-color-red"></span>
					<span class="new-daily-font-behind">{{willcustContactCount}}</span></span>
					<span class="click-into-detail-span" data-type="2" data-status="0" data-custStatusId="{{custStatusId}}"><span class="new-daily-color-show new-daily-color-gray"></span>
					<span class="new-daily-font-behind">{{numSubtract willcustCount willcustContactCount}}</span></span>
				</div>
			</div>
			{{/each}}
				{{#compare signcustCount "!=" 0}}
				<div class="new-daily-plan-box-body-row-right-items">
					<div class="new-daily-plan-box-body-row-right-items-title-today"  data-type="3" data-status="">签约客户数</div>
					<div class="new-daily-plan-box-body-row-right-items-process">
						<div class="new-daily-plan-box-body-row-right-items-process-box">
							{{#compare signcustCount "==" signcustContactCount}}
								<div class="new-daily-plan-box-body-row-right-items-process-true" style="width:{{finishPercentage signcustCount signcustContactCount}};background-color:#0767c8;"></div>
							{{else}}
								<div class="new-daily-plan-box-body-row-right-items-process-true" style="width:{{finishPercentage signcustCount signcustContactCount}};"></div>
							{{/compare}} 
						</div>
						<div class="new-daily-plan-box-body-row-right-items-process-number">{{finishPercentage signcustCount signcustContactCount}}</div>
					</div>
					<div class="new-daily-plan-box-body-row-right-items-prodetail">
						<span class="click-into-detail-span" data-type="3" data-status=""><span class="new-daily-color-show new-daily-color-green"></span>
						<span class="new-daily-font-behind">{{signcustCount}}</span></span>
						<span class="click-into-detail-span" data-type="3" data-status="1"><span class="new-daily-color-show new-daily-color-red"></span>
						<span class="new-daily-font-behind">{{signcustContactCount}}</span></span>
						<span class="click-into-detail-span" data-type="3" data-status="0"><span class="new-daily-color-show new-daily-color-gray"></span>
						<span class="new-daily-font-behind">{{numSubtract signcustCount signcustContactCount}}</span></span>
					</div>
				</div>
				{{/compare}}
			{{else}}
				<div class="new-daily-plan-box-body-row-left">
					<span class="new-daily-plan-box-body-row-left-week">星期{{planDateWeekStr}}</span>
					<span class="new-daily-plan-box-body-row-left-date">{{planDateStr}}</span>
				</div>
			
			{{#compare resourceCount "!=" 0}}
			<div class="new-daily-plan-box-body-row-right-items click-into-detail-span" data-type="1" data-status="">
					<span class="new-daily-plan-box-body-row-right-items-title">资源客户数</span>
					<span class="new-daily-plan-box-body-row-right-items-number">{{resourceCount}}</span>
				</div>
			{{/compare}}
			{{#compare willcustCount "!=" 0}}
				<div class="new-daily-plan-box-body-row-right-items click-into-detail-span" data-type="2" data-status="">
					<span class="new-daily-plan-box-body-row-right-items-title">意向总数</span>
					<span class="new-daily-plan-box-body-row-right-items-number">{{willcustCount}}</span>
				</div>
			{{/compare}}
				{{#each willCustIndexlist}}
				<div class="new-daily-plan-box-body-row-right-items click-into-detail-span" data-type="2" data-status="" data-custStatusId="{{custStatusId}}">
					<span class="new-daily-plan-box-body-row-right-items-title">{{custStatus}}</span>
					<span class="new-daily-plan-box-body-row-right-items-number">{{willcustCount}}</span>
				</div>
				{{/each}}
				{{#compare signcustCount "!=" 0}}
				<div class="new-daily-plan-box-body-row-right-items click-into-detail-span" data-type="3" data-status="">
					<span class="new-daily-plan-box-body-row-right-items-title">签约总数</span>
					<span class="new-daily-plan-box-body-row-right-items-number">{{signcustCount}}</span>
				</div>
				{{/compare}}
			{{/compare}}
             {{else}}
				{{#compare todayFlag "==" -1}}
					<div class="new-daily-plan-box-body-row-left">
						<span class="new-daily-plan-box-body-row-left-week">星期{{planDateWeekStr}}</span>
						<span class="new-daily-plan-box-body-row-left-date">{{planDateStr}}</span>
					</div>
					<div class="new-daily-plan-box-body-row-right-nodata-before">本日无安排！</div>
				{{else}}
					{{#compare todayFlag "==" 0}}
						<div class="new-daily-plan-box-body-row-left today">
					{{else}}
						<div class="new-daily-plan-box-body-row-left">
					{{/compare}}
						<span class="new-daily-plan-box-body-row-left-week">星期{{planDateWeekStr}}</span>
						<span class="new-daily-plan-box-body-row-left-date">{{planDateStr}}</span>
					</div>
					{{#if mineFlag}}
						<div class="new-daily-plan-box-body-row-right-nodata-later">
							<button type="button" class="new-daily-plan-start-planbtn">开始安排本日工作</button>
						</div>
					{{else}}
						<div class="new-daily-plan-box-body-row-right-nodata-before">本日无安排！</div>
					{{/if}}
				{{/compare}}
			{{/if}}
		</div>
 {{/each}}
</script>
	
</body>
</html>
