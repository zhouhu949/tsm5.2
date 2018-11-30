 <%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<title>通信管理-通话记录历史</title>
	<%@ include file="/common/include.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tree_top_part.css${_v}"/>
	<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
	<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
	<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
	<script type="text/javascript" src="${ctx }/static/js/common/page.js${_v}"></script>
	<script type="text/javascript" src="${ctx}/static/js/time/jquery.daterangepicker2.js${_v}"></script><!--选择区域日期插件-->
	<script type="text/javascript" src="${ctx}/static/js/view/callreccord/callRecordHistoryList.js${_v}"></script>
</head>
<body> 
<input type="hidden" id="flag" value="true">

<div class="hyx-ctr hyx-history-box" >
	<c:set var= "markIsSys" value="${shiroUser.issys}"/> <!-- 录音范例权限  -->
	<input type="hidden" id="isSys" value="${shiroUser.issys}">
	<input type="hidden" id="isState" value="${shiroUser.isState}">

	<form id="myForm" action="${ctx}/callrecord/list.do" method="post">
	<input type="hidden" id="scrollstr" name="scroll" value="">
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<input type="hidden" id="project_path" value="${project_path }">
	<input type="hidden" id="playUrl" value="${playUrl}">
	<input type="hidden" name="isHistory" value="1">
   <div class="com-search hyx-cfu-search">
      <div class="com-search-box fl_l">
      <div class="search clearfix" >
		   <div class="select_outerDiv fl_l">
		   <span class="icon_down_5px"></span>
		    <select class="fl_l search_head_select"  name="queryType">
		   		<option value = "1"  <c:if test="${queryType eq 1}">selected</c:if>>主叫号码</option>
		   		<option value = "2"  <c:if test="${queryType ne 1 }">selected</c:if>>被叫号码</option>
	   		</select>
		   	</div>
		   	<input class="input-query fl_l" type="text"  id="queryText"  name="queryText" value="${queryText}" />
		   	<label class="hide-span"></label>
		</div>
			<div class="list-box"> 	    
		   	<!-- 通话日期  -->
			<input type="hidden" name="startTimeBegin" id="startDate" value="${item.startTimeBegin }" />
			<input type="hidden" name="startTimeEnd" id="endDate" value="${item.startTimeEnd }"/>
		    <dl class="select">
				<dt id="d1">通话时间</dt>
			</dl>
					 <!-- 呼叫类型 -->
			   <input type="hidden" name="callState_"  id="callState"  value="${callState}">
			   <c:set var="callStateName" value="呼叫类型"/>
				<c:choose>
					<c:when test="${callState eq 1  }"><c:set var="callStateName" value="已接来电"/></c:when>
					<c:when test="${callState eq 2 }"><c:set var="callStateName" value="已接去电"/></c:when>	
					<c:when test="${callState eq 3 }"><c:set var="callStateName" value="未接来电"/></c:when>	
					<c:when test="${callState eq 4 }"><c:set var="callStateName" value="未接去电"/></c:when>	
				</c:choose>
			   <dl class="select">
					<dt style="border-right:0 none !important;">${callStateName }</dt>
					<dd>
						<ul>
							<li><a href="###" onclick="setCallState('')">呼叫类型</a></li>
							<li><a href="###" onclick="setCallState('1')">已接来电</a></li>
							<li><a href="###" onclick="setCallState('2')">已接去电</a></li>
							<li><a href="###" onclick="setCallState('3')">未接来电</a></li>
							<li><a href="###" onclick="setCallState('4')">未接去电</a></li>						
						</ul>
					</dd>
			   </dl>
			
				   <p class='tag fl_l' style="position:relative;width:132px;padding:5px 0px 5px 2px;"><label class="fl_l">通话时长：</label>
						<input class="fl_l" type="text" id="timeLength" name="timeLengthBegin" value="${item.timeLengthBegin }"  placeholder="秒" onblur="this.value=this.value.replace(/\D/g, '');" style="width:24px;margin:0 3px;">
						<span class="fl_l">-</span>
						<input class="fl_l" type="text" id="timeLengthEnd" name="timeLengthEnd" value="${item.timeLengthEnd }"  placeholder="秒"  onblur="this.value=this.value.replace(/\D/g, '');" style="width:24px;margin:0 3px;">
					</p>
			   </div>
		</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询"  id="querys"/>
		<a href="${ctx }/callrecord/list" class="ex-button">返&nbsp;回</a>
		<!-- <a href="###" class="more"><i></i>更多</a> -->
	</div>
	<div class="com-table com-mta hyx-cla-table hyx-history-scroll-tablebox" style="margin:0 auto;margin-top:20px !important;">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
			<thead>
				<tr class="sty-bgcolor-b">
							<th class="w100"><span class="sp sty-borcolor-b">操作</span></th> 
                            <th class="w120"><span class="sp sty-borcolor-b">单位名称</span></th>
                            <th class="w120"><span class="sp sty-borcolor-b">客户姓名</span></th>
                            <th class="w70"><span class="sp sty-borcolor-b">客户状态</span></th>
                            <th class="w70"><span class="sp sty-borcolor-b ">呼叫类型</span></th>
                            <th class="w120"><span class="sp sty-borcolor-b ">主叫号码</span></th>
                            <th class="w120"><span class="sp sty-borcolor-b">被叫号码</span></th>
                            <th class="w120"><span class="sp sty-borcolor-b">通话时间</span></th>
                            <th class="w120"><span class="sp sty-borcolor-b">通话时长</span></th>
                            <th class="w120"><span class="sp sty-borcolor-b">销售进程</span></th>
                            <th class="w100"><span class="sp sty-borcolor-b">联系人</span></th>
                            <th class="w70"><span class="sp sty-borcolor-b">通话来源</span></th>
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
		
		<div class="tip_search_data hyx-history-loading-mask" >
			<div class="tip_search_text">
				<span><img alt="加载中" src="${ctx }/static/images/hua_loading.gif" /></span>
				<span>数据加载中…</span>
			</div>
			<div class="tip_search_error"></div>
		</div>
	</div>
	</form>
	<div class="reminder">温馨提示：用户可查询3个月以外至18个月的历史记录，如果需要查找近3个月的通话记录，请点击【返回】进行查询</div>
</div>
</body>
</html>