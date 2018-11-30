<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>客户跟进-跟进警报</title>

<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/page.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/follow/followAlarmList.js${_v}"></script>
</head>
<body> 
<form method="post">
<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
<input type="hidden" id="signSetting" value=${signSetting }>
<input type="hidden" id="isState" value="${shiroUser.isState}">
<input type="hidden" id="serverDay" value="${shiroUser.serverDay}">
<input type="hidden" id="add_contract" name="add_contract" value="${addContract}" />

<div class="hyx-ctr" >
   <div class="com-search hyx-cfu-search">
      <div class="com-search-box fl_l">
      <div class="search clearfix" >
		   <div class="select_outerDiv fl_l">
		   <span class="icon_down_5px"></span>
		    <select class="fl_l search_head_select"  name="queryType">
	   		<option value = "4"  <c:if test="${item.queryType ne 1 && item.queryType ne 3}">selected</c:if>>单位名称</option>
	   		<option value = "1"  <c:if test="${item.queryType eq 1}">selected</c:if>>客户姓名</option>
	   		<option value = "3"  <c:if test="${item.queryType eq 3}">selected</c:if>>联系电话</option>
	   		</select>
		   	</div>
		   	<input class="input-query fl_l" type="text" name="queryText" value="${item.queryText }" />
		   	<label class="hide-span"></label>
	  </div>
	   <div class="list-box">
		   <input type="hidden" name="custTypeId" id="custTypeId" value="${item.custTypeId}">
		   <c:set var="custTypeName"  value="客户类型"/>
		   <c:forEach var="v" items="${custTypeList }">
				<c:if test="${v.optionlistId eq item.custTypeId }">
					<c:set var="custTypeName">${v.optionName }</c:set>
				</c:if>
			</c:forEach>
		   <dl class="select">
				<dt>${custTypeName }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="$('#custTypeId').val('');">客户类型</a></li>
						<c:forEach var="vs" items="${custTypeList }">
							<li><a href="###" onclick="$('#custTypeId').val('${vs.optionlistId}');">${vs.optionName }</a></li>
						</c:forEach>
					</ul>
				</dd>
		   </dl>
		   	 <!--  最近联系时间  -->
			  <input type="hidden" name="lastStartActionDate" id="lastStartActionDate" value="${item.lastStartActionDate }" />
			  <input type="hidden" name="lastEndActionDate" id="lastEndActionDate" value="${item.lastEndActionDate }"/>
			  <input type="hidden" name="dDateType" id="dDateType" value="${dDateType }" />
			  <c:set var="dDateName" value="最近联系时间" />
			   <c:choose>
		   		<c:when test="${dDateType eq 1 }"><c:set var="dDateName" value="当天" /></c:when>
		   		<c:when test="${dDateType eq 2 }"><c:set var="dDateName" value="本周" /></c:when>
		   		<c:when test="${dDateType eq 3 }"><c:set var="dDateName" value="本月" /></c:when>
		   		<c:when test="${dDateType eq 4 }"><c:set var="dDateName" value="半年" /></c:when>
		   		<c:when test="${dDateType eq 5 }">
		   			<fmt:parseDate var="psd" value="${item.lastStartActionDate }" pattern="yyyy-MM-dd" />
						<fmt:parseDate var="ped" value="${item.lastEndActionDate }" pattern="yyyy-MM-dd" />
			   			<c:set var="dDateName">
			   				<fmt:formatDate value="${psd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ped }" pattern="yy.MM.dd"/>
			   			</c:set>		   		
		   		</c:when>
		  	 </c:choose>
		    <dl class="select">
				<dt>${dDateName }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="setDdate(0)">最近联系时间</a></li>
						<li><a href="###" onclick="setDdate(1)">当天</a></li>
						<li><a href="###" onclick="setDdate(2)">本周</a></li>
						<li><a href="###" onclick="setDdate(3)">本月</a></li>
						<li><a href="###" onclick="setDdate(4)">半年</a></li>
						<li><a href="###" onclick="setDdate(5)" class="diy date-range" id="d1">自定义</a></li>
					</ul>
				</dd>
			</dl>
			<!--  下次联系时间  -->
			  <input type="hidden" name="nextStartActionDate" id="nextStartActionDate" value="${item.nextStartActionDate }" />
			  <input type="hidden" name="nextEndActionDate" id="nextEndActionDate" value="${item.nextEndActionDate }"/>
			  <input type="hidden" name="nDateType" id="nDateType" value="${nDateType }" />
			  <c:set var="nDateName" value="下次联系时间" />
			  <c:choose>
		   		<c:when test="${nDateType eq 1 }"><c:set var="nDateName" value="当天" /></c:when>
		   		<c:when test="${nDateType eq 2 }"><c:set var="nDateName" value="本周" /></c:when>
		   		<c:when test="${nDateType eq 3 }"><c:set var="nDateName" value="本月" /></c:when>
		   		<c:when test="${nDateType eq 4 }"><c:set var="nDateName" value="半年" /></c:when>
		   		<c:when test="${nDateType eq 5 }">
		   			<fmt:parseDate var="nsd" value="${item.nextStartActionDate }" pattern="yyyy-MM-dd" />
					<fmt:parseDate var="ned" value="${item.nextEndActionDate }" pattern="yyyy-MM-dd" />
			   		<c:set var="nDateName">
			   			<fmt:formatDate value="${nsd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ned }" pattern="yy.MM.dd"/>
			   		</c:set>
		   		</c:when>
		  	 </c:choose>
			<dl class="select">
				<dt>${nDateName }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="setNdate(0)">下次联系时间</a></li>
						<li><a href="###" onclick="setNdate(1)">当天</a></li>
						<li><a href="###" onclick="setNdate(2)">本周</a></li>
						<li><a href="###" onclick="setNdate(3)">本月</a></li>
						<li><a href="###" onclick="setNdate(4)">半年</a></li>
						<li><a href="###" onclick="setNdate(5)" class="diy date-range" id="d2">自定义</a></li>
					</ul>
				</dd>
			</dl>
			<!-- 销售进程 -->
	   		<input type="hidden" name="optionlistId" id="optionlistId" value="${item.optionlistId }" />
			<c:set var="nowsn" value="销售进程"/>
			<c:forEach var="sopn" items="${options }">
				<c:if test="${sopn.optionlistId eq item.optionlistId }">
					<c:set var="nowsn">${sopn.optionName }</c:set>
				</c:if>
			</c:forEach>
			<dl class="select">
				<dt>${nowsn }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="$('#optionlistId').val('');">销售进程</a></li>
						<c:forEach var="sop" items="${options}">
							<li><a href="###" onclick="$('#optionlistId').val('${sop.optionlistId}');" title="${sop.optionName }">${sop.optionName }</a></li>
						</c:forEach>	
					</ul>
				</dd>
			</dl>
		</div>
	</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
   </div>
 	<p class="com-moTag">
			<label class="a">行动标签：</label>
			<c:forEach items="${labelNamesList }" var="ln">
				<span label="1" class="e">${ln }</span>
			</c:forEach>
			<span id="addLabelBtn"  style="cursor: pointer;color: blue;" class="e">选择标签</span>
			<input id="allLabels" name="allLabels" type="hidden" value="${allLabels }"/>
		</p>
   <div class="com-btnlist hyx-cm-btnlist" style="margin:0 auto;">
   <c:choose>
		<c:when test="${shiroUser.serverDay gt 0}">
			<a class="com-btna demoBtn_e fl_l" id="groupCustSMS_btn" href="javascript:;">
					<i class="min-messag"></i>
				<label class="lab-mar">发送短信</label>
				</a>
		</c:when>
		<c:otherwise>
			<a class="com-btna-no fl_l" href="javascript:;"><i class="min-messag img-gray"></i>
			<label class="lab-mar">发送短信</label></a>
		</c:otherwise>
	</c:choose>		
	</div>
	<div class="com-table com-mta hyx-cla-table" style="display:block;margin:0 auto;margin-top:5px !important;">
		<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
			<thead>
				<tr class="sty-bgcolor-b" role="head">
							<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
							<th><span class="sp sty-borcolor-b">操作</span></th> 
							<th><span class="sp sty-borcolor-b">剩余天数</span></th>
							<th><span class="sp sty-borcolor-b " sort="true" ><label class="lab-d"><span>销售进程</span></label></span></th>
							<th><span class="sp sty-borcolor-b " sort="true" ><label class="lab-d"><span>下次联系时间</span></label></span></th>
							<th><span class="sp sty-borcolor-b" sort="true" ><label class="lab-d"><span>最近联系时间</span></label></span></th>
							<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>客户姓名</span></label></span></th>
							<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>单位名称</span></label></span></th>
							<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>客户类型</span></label></span></th>				
							<th>审核状态</th>
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
</form>
</body>
</html>