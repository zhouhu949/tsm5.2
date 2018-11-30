<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<title>签到轨迹</title>
<%@ include file="/common/include.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
<script type="text/javascript" src="${ctx}/static/js/base64.js${_v}"></script>
<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
 <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=uGaLPgqMWw0VExOETV2Hb1vucnoG3PQ5"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/signTrack.css${_v}">
<script type="text/javascript" src="${ctx}/static/js/common/page.js${_v}"></script>
 <script type="text/javascript" src="${ctx }/static/js/view/report/signInTrackMap.js${_v}"></script>
 <script type="text/javascript" src="${ctx }/static/js/view/report/signInTrack.js${_v}"></script>
</head>
<body>
<input type="hidden" id="isState" value="${shiroUser.isState }" />
<input type="hidden" id="curr_user_account" value="${shiroUser.account }"/>
	<div class="sign-in-track-box">
		<form id="signForm">
		<div class="sign-in-track-head">
				<c:choose>
				<c:when test="${shiroUser.issys eq 1 }">
						 <label class="fl_l" for="" >选择人员：</label>
						<div class="reso-sub-dep fl_l">
							<input style="border: 1px solid #e1e1e1;padding:3px;height:25px;margin-top:0;" id="groupNameStr" readonly class="sub-dep-inpu" type="text" value="查看全部">
							<input type="hidden" id="userAcc" name="userAccsStr" value="">
							<div class="manage-drop"  style="height:370px;">
								<div class="shows-radio-boxs"></div>
								<ul class="shows-allorme-boxs">
									<li><input type="radio" name="seeWhat" class="all" checked >查看全部</li>
									<li><input type="radio" name="seeWhat"  class="self"  value="2">查看自己</li>
								</ul>
								<div class="sub-dep-ul" style="width:190px;" data-type="search-tree">
									<ul id="tt1">

					       			</ul>
					    		</div>
							    <div class="sure-cancle clearfix" style="width:120px">
									<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" id="tree-true-btn" href="###"><label>确定</label></a>
									<a class="com-btnd bomp-btna fl_l"  id="tree-cancle-btn"  href="###"><label>清空</label></a>
								</div>
							</div>
						</div>
		        </c:when>
		        <c:otherwise>
		        <input type="hidden" id="userAcc" name = "userAccsStr"value="${shiroUser.account }">
		        	<div class="com-search-report fl_l" style="margin-top:0;min-height:0">
			            <label class="fl_l" for="" >选择人员：</label>
			            <label class="fl_l" for=""  style="margin-right: 20px;">${shiroUser.name }</label>
			        </div>
		        </c:otherwise>
		        </c:choose>
		        <label class="fl_l" for="">选择日期：</label>
				<span class="fl_l" style="display: inline-block; position: relative;"><!-- 限制日期选择只能选择一个月 -->
				<%-- <input id="data_01" name="startDate" value="${fromDateStr }" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker()" />
				<span class="silent-custo-span fl_l">&nbsp;—&nbsp;</span> --%>
				 <input type="hidden" id="start" name="startDate" value="">
				<input id="data_02" name="endDate"  type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({maxDate:'%y-%M-%d',onpicked:timeChange,changing:timeChange,oncleared:timeChange})" />
				</span>
				<a id="today" href="javascript:;" class="silent-week fl_l color_grey">今日</a>
				<a id="yesterday" href="javascript:;" class="silent-month fl_l ">昨日</a>
		        <a href="###" id="searchBtn" class="com-btna fl_l" data-flag="true"><i class="min-search"></i><label class="lab-mar">查询</label></a>
	       </div>
	       </form>
			<div class="sign-in-track-body-map-box" id="dituContent">
				<!-- 百度地图容器 -->
			</div>
			<div class="sign-in-track-foot-table-box com-table">
				<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
					<thead>
						<tr class="sty-bgcolor-b">
							<th><span class="sp sty-borcolor-b">操作</span></th> 
							<th><span class="sp sty-borcolor-b">签到时间</span></th> 
							<th><span class="sp sty-borcolor-b">${shiroUser.isState eq 1?'客户名称':'客户姓名'}</span></th>
							<th><span class="sp sty-borcolor-b">${shiroUser.isState eq 1?'联系人':'单位名称'}</span></th>
							<th><span class="sp sty-borcolor-b">签到地址</span></th>
							<th><span class="sp sty-borcolor-b">签到人员</span></th>
							<th>签到说明</th>
						</tr>
					</thead>
					<tbody>
						<tr class="no_data_tr"><td>当前列表无数据！</td></tr>
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
			</div>
			<div class="com-bot" >
			<div class="com-page fl_r">
		          <div class="page" id="new_page"></div>
		          <div class="clr_both"></div>
	        </div>
		</div>
	</div>
	<script type="text/x-handlebars-template" id="template">
  {{#each list}}
					<tr class="{{even_odd @index}}">
							<td>
								{{#compare record "!=" "" }}
									{{#compare record "!=" null }}
									<span class="fa fa-play-circle-o play-record" data-url="{{record}}" data-lens="{{recordTime}}" data-code="{{getOnlyUuid}}" title="播放录音"></span>
									{{/compare}}
								{{/compare}}
								{{#compare imgs "!=" "" }}
									{{#compare imgs "!=" null }}
									<a href="javascript:;" class="fa fa-file-image-o img-details" data-url="{{imgs}}" title="查看图片"></a>
									{{/compare}}
								{{/compare}}
							</td>
							<td>{{signDate}}</td>
							${shiroUser.isState eq 1? '<td>{{name}}</td><td>{{toucherName}}</td>':'<td>{{toucherName}}</td><td>{{name}}</td>'}
							<td>{{address}}</td>
							<td>{{userName}}</td>
							{{#compare signInfo "!=" ""}}
							<td><div class="w150 overflow_hidden" title="{{signInfo}}">{{signInfo}}</div></td>
							{{else}}
							<td><div class="w150 overflow_hidden" title="签到">签到</div></td>
							{{/compare}}
						</tr>
	{{/each}}
	</script>
		<script type="text/x-handlebars-template" id="templateNoData">
<tr class="no_data_tr"><td>当前列表无数据！</td></tr>
						<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
						<tr class="no_data_tr"><td></td></tr>
						<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
						<tr class="no_data_tr"><td></td></tr>
						<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
						<tr class="no_data_tr"><td></td></tr>
						<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
						<tr class="no_data_tr"><td></td></tr>
						<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
	</script>
</body>
</html>
