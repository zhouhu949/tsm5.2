<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-历年计划详情查询</title>
<!--本页面样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css${_v}">
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/page.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/plan/year/history_view_dataIn.js${_v}"></script>
<script type="text/javascript">
var isState=${isState};
</script>
</head>
<body>
<form id="historyForm">
<input type="hidden" id="planYear" name="planYear" value=""/>
<input type="hidden" id="planMonth" name="planMonth" value=""/>
<input type="hidden" id="status" name="status" value=""/>
<input id="groupIds" name="groupIds" type="hidden" value=""/>
<input id="userIds" name="userIds" type="hidden" value=""/>
<input id="userAccs" name="userAccs" type="hidden" value=""/>

<div class="hyx-cfu-left hyx-ctr hyx-sc">
   <div class="com-search hyx-cfu-search">
      <div class="com-search-box fl_l">
	   <div class="search clearfix" >
		   <div class="select_outerDiv fl_l">
		   <span class="icon_down_5px"></span>
		   	<select name="queryType" class="fl_l search_head_select">
	   			<option value="2" >单位名称</option>
		   		<option value="1" >${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</option>
		   	</select>
		   	</div>
		   	<input class="input-query fl_l" type="text" name="queryText" value="" />
		   	<label class="hide-span"></label>
	  </div>

	   <div class="list-box">
			<dl class="select">
				<dt>年份选择</dt>
				<dd>
					<ul>
						<li><a href="javascript:planYear(null);" title="年份选择">年份选择</a></li>
						<c:forEach items="${years}" var="year">
						<li><a href="javascript:planYear(${year});" title="${year}年">${year}年</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
			<dl class="select">
				<dt>月份选择</dt>
				<dd>
					<ul>
						<li><a href="javascript:planMonth(null);" value ="" title="月份选择">月份选择</a></li>
						<c:forEach items="${months}" var="month">
						<li><a href="javascript:planMonth(${month});" title="${month}月">${month}月</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
			<dl class="select">
				<dt>完成情况</dt>
				<dd>
					<ul>
						<li><a href="javascript:status(null);" title="完成情况">完成情况</a></li>
						<li><a href="javascript:status(1);" title="已完成">已完成</a></li>
						<li><a href="javascript:status(0);" title="未完成">未完成</a></li>
					</ul>
				</dd>
			</dl>
			<div class="reso-sub-dep fl_l" >
				<input class="owner-sour" type="text" value="销售人员" style="cursor:pointer;"/>
				<div class="manage-owner-sour">
					<div class="sub-dep-ul">
						<ul id="userTree" class="easyui-tree" ></ul>
		    		</div>
				    <div class="sure-cancle clearfix" style="width:120px">
						<a id="cfm02" class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" href="javascript:;"><label>确定</label></a>
						<a id="close02" class="com-btnd bomp-btna fl_l" href="javascript:;"><label>清空</label></a>
					</div>
				</div>
			</div>
		</div>
	</div>
		<input id="query" class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
   </div>
	<div class="com-table com-mta hyx-cla-table" style="display:block;margin-top:45px !important;">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">年份</span></th>
					<th><span class="sp sty-borcolor-b">月份</span></th>
					<th><span class="sp sty-borcolor-b">部门</span></th>
					<th><span class="sp sty-borcolor-b">销售人员</span></th>
					<th><span class="sp sty-borcolor-b">${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</span></th>
					<c:if test="${isState eq 0}"><th><span class='sp sty-borcolor-b'>单位名称</span></th></c:if>
					<th><span class="sp sty-borcolor-b">意向客户数（个）</span></th>
					<th><span class="sp sty-borcolor-b">签约客户数（个）</span></th>
					<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
					<th>完成情况</th>
				</tr>
			</thead>
			<tbody>
				<tr><td colspan="${isState eq 0?10:9}" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
			</tbody>
		</table>
	</div>
	<div class="com-bot" >
		<div class="com-page fl_r">
			<div class="page" id="new_page"></div>
			<div class="clr_both"></div>
		</div>
	</div>
</div>
</form>
</body>
</html>
