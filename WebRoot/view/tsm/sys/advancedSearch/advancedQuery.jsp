<%@ page language="java" pageEncoding="UTF-8"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta http-equiv="X-UA-Compatible" content="ie=edge" />
		<%@ include file="/common/common.jsp"%>
		<%@ include file="/common/common-function/function.area.select.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/gray/easyui.css${_v}"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/advancedQuery.css${_v}"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/js/time/css/daterangepicker.css${_v}" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/font-awesome-4.7.0/css/font-awesome.min.css${_v}"/>
		<title>高级查询</title>
		<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
		<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
	</head>

	<body>
		<form>
			<input type="hidden" id="shiroUser_account" value="${shiroUser.account }" />
			<input type="hidden" id="module" value="${module}">
			<div class="advancedQuery-box">
				<!--<div class="advancedQuery-everytitle">
				客户类型：
			</div>
			<div class="advancedQuery-everycontent single-checked">
				<span class="advancedQuery-tagincont active">潜在客户<input type="radio" name="custTypeId" value="1" checked="checked" /></span>
				<span class="advancedQuery-tagincont">普通客户<input type="radio" name="custTypeId" value="2" /></span>
			</div>

			<div class="advancedQuery-everytitle">
				销售进程：
			</div>
			<div class="advancedQuery-everycontent multi-checked">
				<span class="advancedQuery-tagincont">初步沟通有意向<input type="checkbox" name="saleProgress" value="初步沟通有意向" /></span>
				<span class="advancedQuery-tagincont">深入沟通<input type="checkbox" name="saleProgress" value="深入沟通" /></span>
				<span class="advancedQuery-tagincont">初步了解<input type="checkbox" name="saleProgress" value="初步了解" /></span>
				<span class="advancedQuery-tagincont">全面了解<input type="checkbox" name="saleProgress" value="全面了解" /></span>
				<span class="advancedQuery-tagincont">会客面谈<input type="checkbox" name="saleProgress" value="会客面谈" /></span>
				<span class="advancedQuery-tagincont">准备签约<input type="checkbox" name="saleProgress" value="准备签约" /></span>
			</div>

			<div class="advancedQuery-everytitle">
				所有者：
			</div>
			<div class="advancedQuery-everycontent tree-checked">
				<span class="advancedQuery-tagincont treeNode-checked">选择所有者</span>
				<span class="advancedQuery-text">张磊磊</span>
				<span class="advancedQuery-text">朱倩倩</span>
				<div class="tree-box">
					<div data-type="tree"></div>
					<div class="button-area">
						<button type="button" class="btn btn-submit">确定</button>
						<button type="button" class="btn btn-default">清空</button>
					</div>
				</div>
			</div>

			<div class="advancedQuery-everytitle">
				最近联系时间：
			</div>
			<div class="advancedQuery-everycontent date-checked single-checked">
				<span class="advancedQuery-tagincont date-no-time active">无联系时间<input type="radio" name="dDateType" value="0" checked="checked" /></span>
				<span class="advancedQuery-tagincont date-today">当天<input type="radio" name="dDateType" value="1" /></span>
				<span class="advancedQuery-tagincont date-week">本周<input type="radio" name="dDateType" value="2" /></span>
				<span class="advancedQuery-tagincont date-month">本月<input type="radio" name="dDateType" value="3" /></span>
				<span class="advancedQuery-tagincont date-half-year">半年<input type="radio" name="dDateType" value="4" /></span>
				<span class="advancedQuery-tagincont dateRange">自定义<input type="radio" name="dDateType" value="5" /></span>
				<span class="advancedQuery-text date-show-area">2017-12-12/2017-12-27</span>
			</div>

			<div class="advancedQuery-everytitle">
				下次联系时间：
			</div>
			<div class="advancedQuery-everycontent date-checked single-checked">
				<span class="advancedQuery-tagincont date-no-time active">无联系时间<input type="radio" name="nDateType" value="0" checked="checked" /></span>
				<span class="advancedQuery-tagincont date-today">当天<input type="radio" name="nDateType" value="1" /></span>
				<span class="advancedQuery-tagincont date-week">本周<input type="radio" name="nDateType" value="2" /></span>
				<span class="advancedQuery-tagincont date-month">本月<input type="radio" name="nDateType" value="3" /></span>
				<span class="advancedQuery-tagincont date-half-year">半年<input type="radio" name="nDateType" value="4" /></span>
				<span class="advancedQuery-tagincont dateRange">自定义<input type="radio" name="nDateType" value="5" /></span>
				<span class="advancedQuery-text date-show-area"></span>
			</div>
			<div class="sure-cancle clearfix neworg-surbtn">
				<a href="javascript:;" class="com-btna bomp-btna com-btna-sty fl_l"><label>确定</label></a>
				<a href="javascript:;" class="com-btna bomp-btna fl_l"><label>取消</label></a>
				<button type="button" class="btn btn-submit">确定</button>
				<button type="button" class="btn btn-default">取消</button>
			</div>-->
			</div>
		</form>
		<script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/time/moment.min.js"></script><!--选择区域日期插件-->
		<script type="text/javascript" src="${ctx}/static/js/time/jquery.daterangepicker.js${_v}"></script><!--选择区域日期插件-->
		<script type="text/javascript" src="${ctx}/static/thirdparty/easyui/jquery.easyui.min.js${_v}"></script>
		<script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js${_v}"></script><!--可移动弹框插件公共js-->
		<script type="text/javascript" src="${ctx}/static/js/view/tsm/sys/advancedQuery.js${_v}"></script>
		<script type="text/javascript" src="${ctx }/static/js/common/search_input_tree.js${_v}"></script>
	</body>
</html>
