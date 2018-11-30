<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@page import="com.qftx.base.enums.FollowCustEnum"%>
<%@ include file="/common/include.jsp" %>
	<script type="text/javascript" src="${ctx}/static/js/view/plan/day/checkBoxUtil.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/page.js${_v}"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/newdaily_plan.css${_v}"/>
<script type="text/javascript" src="${ctx}/static/js/view/plan/dailyplan/right/plancommon.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/plan/dailyplan/right/dailyPlan_detail_res.js${_v}"></script>
<title>日计划详情</title>
<style type="text/css" rel="stylesheet">
<c:forEach items="${sorts}" var="sort">
	.com-table>table>tbody>tr>td:nth-child(${sort}),.com-table>table>thead>tr>th:nth-child(${sort}){display: none;}
</c:forEach>
<c:if test="${ isMine && todayFlag ne -1 }">
.new-daily-plan-rightbottom-editbtn-follow{display:block}
</c:if>
</style>
</head>
<body>
	<div class="hyx-layout">
		<div class="hyx-dayplan-center hyx-layout-content">
			<form id="myForm" method="post">
				<input type="hidden" id="sudId" name="sudId" value="${sudId}"><!-- 计划ID -->
				<input type="hidden" id="dateStr" name="dateStr" value="${dateStr}"><!-- 时间 -->
				<input type="hidden" id="type" name="type" value="${type}"><!-- 类型 资源 意向 签约-->
				<input type="hidden" id="status" name="status" value="${status}"><!-- 联系状态-->
				<input type="hidden" id="custStatusId" name="custStatusId" value="${custStatusId}"><!-- 销售进程ID-->
				<input type="hidden" id="userAcc" name="userAcc" value="${userAcc}"><!-- 用户账号-->
				<input type="hidden" id="userId" name="userId" value="${userId}"><!-- 用户ID-->
				<input type="hidden" id="isMine" name="isMine" value="${isMine}"><!-- 是否为当前账户-->
				<input type="hidden" id="todayFlag" name="todayFlag" value="${todayFlag}"><!-- 是否为当天-->
				<input type="hidden" id="contactResult" name="contactResult" value=""><!-- 数据分类联系状态 -->
				<input type="hidden" id="custLevel" name="custLevel" value=""><!-- 优先级状态 -->
				<input type="hidden" id="signSetting"  value="${signSetting }"><!-- 签约相关 -->
		   <div class="com-search hyx-cfu-search new-detail-com-search">
		      <div class="com-search-box fl_l">
			    <div class="search clearfix" >
				   <div class="select_outerDiv fl_l">
				   <span class="icon_down_5px"></span>
				    <select class="fl_l search_head_select"  name="queryType">
				    	<c:if test="${isState eq 0}">
						<option value="4" >单位名称</option>
						</c:if>
						<option value="1" >${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</option>
						<c:if test="${isState eq 1}">
						<option value="2" >联系人</option>
						</c:if>
						<option value="3" >联系电话</option>
			   		</select>
				   	</div>
				   	<input class="input-query fl_l" type="text" id="queryStr" name="queryStr" />
				   	<label class="hide-span"></label>
				  </div>												
			</div>
				<input class="query-button fl_r" type="button" value="查&nbsp;询"  id="query"/>
		  </div>
			<p class="com-moTag"><label class="a">数据分类：</label>
				  	<a class="e ${empty status?'e-hover':''}"  href="javascript:;" data-result ="0">全部</a>
				  	<a class="e"   href="javascript:;" data-result ="1">转签约</a>
				  	<a class="e"   href="javascript:;" data-result ="2">转意向</a>
				  	<a class="e"   href="javascript:;" data-result ="3">转公海</a>
				  	<a class="e ${status eq '1' ?'e-hover':''}"   href="javascript:;" data-result ="4">已联系</a>
				  	<a class="e ${status eq '0' ?'e-hover':''}"   href="javascript:;" data-result ="5">未联系</a>
				  	<a class="e"   href="javascript:;" data-result ="6">无变化</a>
				  	<!-- <a class="e"   href="javascript:;" data-result ="7">优先资源</a> -->
			</p>
			<div class="com-btnlist hyx-cfu-btnlist fl_l">
				<c:if test="${ isMine && todayFlag ne -1 }">
					<a href="javascript:;" class="com-btna fl_l" id="join_cust" data-planid="${sudId }" data-dateStr="${dateStr }"><label>加入资源</label></a>
					<a href="javascript:;" class="com-btna fl_l" id="time_change"><label>时间调整</label></a>
					<a href="###" id="setImpBtn" class="com-btna demoBtn_f fl_l"><i class="min-set-prior"></i><label class="lab-mar">设置优先</label></a>
					<a href="###" id="setUnImpBtn" class="com-btna demoBtn_g fl_l"><i class="min-cancel-prior"></i><label class="lab-mar">取消优先</label></a>
				</c:if>
			</div>
			<input type="hidden" name="orderKey" id="orderKey" value="" />
			<div class="com-table hyx-fur-table com-mta fl_l" style="display:block;">
				<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
					<thead>
						<tr class="sty-bgcolor-b" role="head">
							<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
							<th><span class="sp sty-borcolor-b">操作</span></th>
							
							<th><span class="sp sty-borcolor-b">${isState eq 1 ? '客户名称':'单位名称' }</span></th>
							<th><span class="sp sty-borcolor-b">${isState eq 1 ? '联系人' : '客户姓名' }</span></th>
							
							<th><span class="sp sty-borcolor-b">客户来源</span></th>
							<th><span class="sp sty-borcolor-b">添加/分配时间</span></th>
					   		<th>联系结果</th>
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
			<div class="com-bot" style="width:99%;">
				<div class="com-page fl_r">
					<div class="page" id="new_page"></div>
					<div class="clr_both"></div>
				</div>
			</div>
			</form>
		</div>
		<div class="hyx-layout-side">
			<div class="hyx-dayplan-right"  id="list_right"></div>
		</div>
		
	</div>
	
		<script type="text/x-handlebars-template" id="template">
  {{#each list}}
  <tr class="hyx-fur-tr {{even_odd @index}} {{dayPlanDis permision 'disabled'}}" data-custId="{{custId}}">
    <td style="width:30px;">
      <div class="overflow_hidden w30 skin-minimal">
        <input type="checkbox" name="check_" id="chk_{{custId}}" data-sudId="{{sudId}}" custId="{{custId}}" state="{{state}}" custLevel="{{custLevel}}" {{dayPlanDis permision "disabled"}}/>
      </div>
    </td>
    <td>
		<c:choose>
		<c:when test="${isState eq 1}"><a href='javascript:;' id='cardInfo_{{custId}}' custName="{{custName}}" class='icon-cust-card {{dayPlanDis permision "img-gray"}}' title='客户卡片'></a></c:when>
		<c:otherwise><a href='javascript:;' id='cardInfo_{{custId}}' custName="{{custName}}" company="{{company}}" class='icon-cust-card {{dayPlanDis permision "img-gray"}}' title='客户卡片'></a></c:otherwise>
		</c:choose>
		<c:if test="${ isMine && todayFlag ne -1}">
			<a href='javascript:;' id='dele_{{custId}}_1'  class='icon-dele {{typeDayPlanDis permision "img-gray" typeChange}}' title='放弃客户'></a>
		</c:if>
    </td>
    <td>
<div class="w100 overflow_hidden " title="{{${isState eq 1 ? 'custName':'company' }}}">{{#compare custLevel "==" 1}}
        <i class='min-key-areas'></i>
    {{/compare}}{{${isState eq 1 ? 'custName':'company' }}}</div>
    </td>
	<td>
<div class="w100 overflow_hidden " title="{{${isState eq 1 ? 'custUser' : 'custName'}}}">{{${isState eq 1 ? 'custUser' : 'custName'}}}</div> 
    </td>
	 <td>
<div class="w80 overflow_hidden " title="{{sourceStr}}">{{sourceStr}}</div>
    </td>
    <td>
<div class="w120 overflow_hidden " title="{{custInputtimeStr}}">{{custInputtimeStr}}</div>
    </td>
    <td>
     <div class="w80 overflow_hidden " title="{{contactResultStr}}">{{contactResultStr}}</div>
    </td>
  </tr>
 {{/each}}
</script>
</body>

</html>
