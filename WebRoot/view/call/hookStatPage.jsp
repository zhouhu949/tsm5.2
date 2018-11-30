<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>通信管理-挂机短信发送记录</title>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/page.js${_v}"></script>
	<script type="text/javascript" src="${ctx}/static/js/view/callreccord/hookRecordList.js${_v}"></script>
</head>
<body> 
<div class="hyx-ctr" >
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
 <form  method="post">
   <div class="com-search hyx-cfu-search">
      <div class="com-search-box fl_l">
	     <div class="search clearfix" >
		   <div class="select_outerDiv fl_l">
		   <span class="icon_down_5px"></span>
		    <select class="fl_l search_head_select"  name="queryType">
	   		<option value ="1"  selected >客户号码</option>
	   		</select>
		   	</div>
		   	<input class="input-query fl_l" type="text" name="queryText" value="${item.queryText}" />
		   	<label class="hide-span"></label>
		</div>
	   <div class="list-box">
	   			<!-- 通话日期  -->
			   <input type="hidden" name="startDate" id="startDate" value="${item.startDate }" />
			   <input type="hidden" name="endDate" id="endDate" value="${item.endDate }"/>
			   <input type="hidden" name="dDateType" id="dDateType" value="${dDateType }" />
			   <c:set var="dDateName" value="发送时间" />
			   <c:choose>
			   		<c:when test="${dDateType eq 1 }"><c:set var="dDateName" value="当天" /></c:when>
			   		<c:when test="${dDateType eq 2 }"><c:set var="dDateName" value="本周" /></c:when>
			   		<c:when test="${dDateType eq 3 }"><c:set var="dDateName" value="本月" /></c:when>
			   		<c:when test="${dDateType eq 4 }"><c:set var="dDateName" value="半年" /></c:when>
			   		<c:when test="${dDateType eq 5 }">
			   			<fmt:parseDate var="nsd" value="${item.startDate }" pattern="yyyy-MM-dd" />
						<fmt:parseDate var="ned" value="${item.endDate }" pattern="yyyy-MM-dd" />
			   			<c:set var="dDateName">
			   				<fmt:formatDate value="${nsd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ned }" pattern="yy.MM.dd"/>
			   			</c:set>
			   		</c:when>
			   </c:choose>
		    <dl class="select">
				<dt>${dDateName }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="setNdate(0)">发送时间</a></li>
						<li><a href="###" onclick="setNdate(1)">当天</a></li>
						<li><a href="###" onclick="setNdate(2)">本周</a></li>
						<li><a href="###" onclick="setNdate(3)">本月</a></li>
						<li><a href="###" onclick="setNdate(4)">半年</a></li>
						<li><a href="###" onclick="setNdate(5)" class="diy date-range" id="d1">自定义</a></li>
					</ul>
				</dd>
			</dl>
		</div>
	</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询"  id="query"/>
    </div>
	<div class="com-table com-mta hyx-cla-table" style="display:block;margin:0 auto;margin-top:37px !important">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>客户号码</span></label></span></th>
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>短信内容</span></label></span></th>
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>短信条数</span></label></span></th>
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>短信状态</span></label></span></th>
					<th>发送时间</th>
				</tr>
			</thead>
			<tbody>      
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
</form>
</div>
</body>
</html>