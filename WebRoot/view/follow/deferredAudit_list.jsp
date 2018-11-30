<%@ page language="java" pageEncoding="utf-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
<title>客户跟进-延期审核</title>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/page.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/follow/deferredAuditList.js${_v}"></script>
</head>
<body> 
<form method="post">
<input type="hidden" id="isState" value="${shiroUser.isState}">
<div class="hyx-ctr">
   <div class="com-search hyx-cfu-search">
      <div class="com-search-box fl_l">
	     <div class="search clearfix" >
		   <div class="select_outerDiv fl_l">
		   <span class="icon_down_5px"></span>
		    <select class="fl_l search_head_select"  name="queryType">
	   		<option value = "1"  <c:if test="${item.queryType ne 2 && item.queryType ne 3 && item.queryType ne 5}">selected</c:if>>客户名称</option>
	   		<option value = "2"  <c:if test="${item.queryType eq 2 }">selected</c:if>>联系人</option>
	   		<option value = "3"  <c:if test="${item.queryType eq 3}">selected</c:if>>联系电话</option>
	   		<option value = "5"  <c:if test="${item.queryType eq 5 }">selected</c:if>>申请人</option>
	   		</select>
		   	</div>
		   	<input class="input-query fl_l" type="text" name="queryText" value="${item.queryText }" />
		   	<label class="hide-span"></label>
		  </div>
	   <div class="list-box">
	   		<input type="hidden" name="statusExtended" id="statusExtended" value="${item.statusExtended}">
		   	<c:set var="statusName" value="待审核"/>
		   	<c:choose>
		   		<c:when test="${item.statusExtended eq 2 }"><c:set var="statusName" value="待审核"/></c:when>
		   		<c:when test="${item.statusExtended eq 1 }"><c:set var="statusName" value="通过"/></c:when>
		   		<c:when test="${item.statusExtended eq 0 }"><c:set var="statusName" value="驳回"/></c:when>
		   	</c:choose>
		   <dl class="select">
				<dt>${statusName }</dt>
				<dd>
					<ul>
						<!-- <li><a href="###"  onclick="$('#statusExtended').val('');">审核状态</a></li> -->
						<li><a href="###"  onclick="$('#statusExtended').val('2');">待审核</a></li>
						<li><a href="###"  onclick="$('#statusExtended').val('1');">通过</a></li>
						<li><a href="###"  onclick="$('#statusExtended').val('0');">驳回</a></li>
					</ul>
				</dd>
		   </dl>
		   <!--  审核时间  -->
			<input type="hidden" name="auditStartDate" id="auditStartDate" value="${item.auditStartDate }" />
		   <input type="hidden" name="auditEndDate" id="auditEndDate" value="${item.auditEndDate }"/>
		   <input type="hidden" name="dDateType" id="dDateType" value="${dDateType }" />
		   <c:set var="dDateName" value="审核时间" />
		   <c:choose>
		   		<c:when test="${dDateType eq 1 }"><c:set var="dDateName" value="当天" /></c:when>
		   		<c:when test="${dDateType eq 2 }"><c:set var="dDateName" value="本周" /></c:when>
		   		<c:when test="${dDateType eq 3 }"><c:set var="dDateName" value="本月" /></c:when>
		   		<c:when test="${dDateType eq 4 }"><c:set var="dDateName" value="半年" /></c:when>
		   		<c:when test="${dDateType eq 5 }">
		   			<fmt:parseDate var="psd" value="${item.auditStartDate }" pattern="yyyy-MM-dd" />
						<fmt:parseDate var="ped" value="${item.auditEndDate }" pattern="yyyy-MM-dd" />
			   			<c:set var="dDateName">
			   				<fmt:formatDate value="${psd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ped }" pattern="yy.MM.dd"/>
			   			</c:set>			   		
		   		</c:when>
		   </c:choose>
		    <dl class="select">
				<dt>${dDateName }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="setDdate(0)">审核时间</a></li>
						<li><a href="###" onclick="setDdate(1)">当天</a></li>
						<li><a href="###" onclick="setDdate(2)">本周</a></li>
						<li><a href="###" onclick="setDdate(3)">本月</a></li>
						<li><a href="###" onclick="setDdate(4)">半年</a></li>
						<li><a href="###" onclick="setDdate(5)" class="diy date-range" id="d1">自定义</a></li>
					</ul>
				</dd>
			</dl>
			 <!--  申请时间  -->
			<input type="hidden" name="startDate" id="startDate" value="${item.startDate }" />
		   <input type="hidden" name="endDate" id="endDate" value="${item.endDate }"/>
		   <input type="hidden" name="sDateType" id="sDateType" value="${sDateType }" />
		   <c:set var="sDateName" value="本周" />
		   <c:choose>
		   		<c:when test="${sDateType eq 1 }"><c:set var="sDateName" value="当天" /></c:when>
		   		<c:when test="${sDateType eq 2 }"><c:set var="sDateName" value="本周" /></c:when>
		   		<c:when test="${sDateType eq 3 }"><c:set var="sDateName" value="本月" /></c:when>
		   		<c:when test="${sDateType eq 4 }"><c:set var="sDateName" value="半年" /></c:when>
		   		<c:when test="${sDateType eq 5 }">
		   			<fmt:parseDate var="nsd" value="${item.startDate }" pattern="yyyy-MM-dd" />
					<fmt:parseDate var="ned" value="${item.endDate }" pattern="yyyy-MM-dd" />
			   		<c:set var="sDateName">
			   			<fmt:formatDate value="${nsd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ned }" pattern="yy.MM.dd"/>
			   		</c:set>		   		
		   		</c:when>
		   </c:choose>
			<dl class="select">
				<dt>${sDateName }</dt>
				<dd>
					<ul>
						<!-- <li><a href="###" onclick="setSdate(0)">申请时间</a></li> -->
						<li><a href="###" onclick="setSdate(1)">当天</a></li>
						<li><a href="###" onclick="setSdate(2)">本周</a></li>
						<li><a href="###" onclick="setSdate(3)">本月</a></li>
						<li><a href="###" onclick="setSdate(4)">半年</a></li>
						<li><a href="###" onclick="setSdate(5)" class="diy date-range" id="d2">自定义</a></li>
					</ul>
				</dd>
			</dl>
			<input type="hidden" name="optionId" id="optionId" value="${item.optionId }" />
			<c:set var="nowsn" value="销售进程"/>
			<c:forEach var="sopn" items="${options }">
				<c:if test="${sopn.optionlistId eq item.optionId }">
					<c:set var="nowsn" value="${sopn.optionName }"/>
				</c:if>
			</c:forEach>
			<dl class="select">
				<dt>${nowsn }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="$('#optionId').val('');">销售进程</a></li>
						<c:forEach var="lsop" items="${options}">
							<li><a href="###" onclick="$('#optionId').val('${lsop.optionlistId}');" title="${lsop.optionName }">${lsop.optionName }</a></li>
						</c:forEach>	
					</ul>
				</dd>
			</dl>			
		</div>
	</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询"  id="query"/>
 
   </div>
   <div class="com-btnlist hyx-cm-btnlist" style="margin:0 auto;margin-top:15px;">
				<a class="com-btna demoBtn_a fl_l" href="javascript:;" id="btn_pass"><i class="min-pass"></i><label class="lab-mar">通过</label></a>
				<a class="com-btna demoBtn_b fl_l" href="javascript:;" id="btn_reject"><i class="min-rebut"></i><label class="lab-mar">驳回</label></a>
	</div>
	<!-- <p class="com-moTag skin-minimal com-mta fl_l" ><label class="f">当前跟进警报为：2条。</label></p> -->
	<div class="com-table com-mta hyx-cla-table" style="display:block;margin:0 auto;margin-top:10px !important;">
		<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
			<thead>
				<tr class="sty-bgcolor-b" role="head">
							<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
							<th><span class="sp sty-borcolor-b">操作</span></th> 
							<th><span class="sp sty-borcolor-b" sort="true">申请时间</span></th>
							<th><span class="sp sty-borcolor-b">客户名称</span></th>
							<th><span class="sp sty-borcolor-b " sort="true"><label class="lab-d"><span>销售进程</span></label></span></th>
							<th><span class="sp sty-borcolor-b " ><label class="lab-d"><span>延期天数</span></label></span></th>
							<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>申请理由</span></label></span></th>
							<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>申请人</span></label></span></th>
							<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>审核状态</span></label></span></th>
							<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>审核人</span></label></span></th>
							<th>审核时间</th>
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
</div>
</form>
</body>
</html>