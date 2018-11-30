<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<%@page import="com.qftx.base.enums.FollowCustEnum"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tree_top_part.css"/>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/page.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/follow/followList.js${_v}"></script>
<style type="text/cccss" rel="stylesheet">
.com-table>table>tbody>tr>td:nth-child(1),.com-table>table>thead>tr>th:nth-child(1){display: none;}
.com-table>table>tbody>tr>td:nth-child(2),.com-table>table>thead>tr>th:nth-child(2){display: none;}
.com-table>table>tbody>tr>td:nth-child(3),.com-table>table>thead>tr>th:nth-child(3){display: none;}
.com-table>table>tbody>tr>td:nth-child(4),.com-table>table>thead>tr>th:nth-child(4){display: none;}
.com-table>table>tbody>tr>td:nth-child(5),.com-table>table>thead>tr>th:nth-child(5){display: none;}
.com-table>table>tbody>tr>td:nth-child(6),.com-table>table>thead>tr>th:nth-child(6){display: none;}
.com-table>table>tbody>tr>td:nth-child(7),.com-table>table>thead>tr>th:nth-child(7){display: none;}
.com-table>table>tbody>tr>td:nth-child(8),.com-table>table>thead>tr>th:nth-child(8){display: none;}
.com-table>table>tbody>tr>td:nth-child(9),.com-table>table>thead>tr>th:nth-child(9){display: none;}
.com-table>table>tbody>tr>td:nth-child(10),.com-table>table>thead>tr>th:nth-child(10){display: none;}
.com-table>table>tbody>tr>td:nth-child(11),.com-table>table>thead>tr>th:nth-child(11){display: none;}
.com-table>table>tbody>tr>td:nth-child(12),.com-table>table>thead>tr>th:nth-child(12){display: none;}
.com-table>table>tbody>tr>td:nth-child(13),.com-table>table>thead>tr>th:nth-child(13){display: none;}
.com-table>table>tbody>tr>td:nth-child(14),.com-table>table>thead>tr>th:nth-child(14){display: none;}
.com-table>table>tbody>tr>td:nth-child(15),.com-table>table>thead>tr>th:nth-child(15){display: none;}
.com-table>table>tbody>tr>td:nth-child(16),.com-table>table>thead>tr>th:nth-child(16){display: none;}
</style>
</head>
<body> 
<div class="hyx-cfu-left fl_l">
	<input type="hidden" id="signSetting" value=${signSetting }>
	<input type="hidden" id="adminSignAuth" value=${adminSignAuth }>
	<input type="hidden" id="isState" value="${shiroUser.isState}">
	<input type="hidden" id="isSys" value="${shiroUser.issys}">
	<input type="hidden" id="serverDay" value="${shiroUser.serverDay}">
	<input type="hidden" id="userAccount" value="${shiroUser.account}">
	<form id="myForm" method="post">
	<input type="hidden" id="signSetting" value=${signSetting }>
   <div class="com-search hyx-cfu-search">
      <div class="com-search-box fl_l">
	    <div class="search clearfix" >
		   <div class="select_outerDiv fl_l">
		   <span class="icon_down_5px"></span>
		    <select class="fl_l search_head_select"  name="queryType">
	   		<option value = "4"  <c:if test="${item.queryType eq '4' }">selected</c:if>>单位名称</option>
	   		<option value = "1"  <c:if test="${item.queryType eq '1' }">selected</c:if>>客户姓名</option>
	   		<option value = "3"  <c:if test="${item.queryType eq '3'}">selected</c:if>>联系电话</option>
	   		<c:if test="${ !empty defList && fn:length(defList) > 0 }">
				<c:forEach items="${defList}" var="defined" varStatus="vs">
					 <option value="${defined.fieldCode }" ${item.queryType eq defined.fieldCode ? 'selected' :''}>${defined.fieldName }</option>
		   		</c:forEach>
	   		</c:if>
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
						<li><a href="###" onclick="setNdate(5)" id="d1" class="diy date-range">自定义</a></li>
					</ul>
				</dd>
			</dl>
			
			<div class="reso-sub-dep fl_l">	
					<c:choose>
						<c:when test="${shiroUser.issys eq 0 }">
							<input class="owner-sour-nostr"  name="accs" type="text" readonly="readonly" value="${shiroUser.name}" style="width:136px;border-right:2px solid #e1e1e1;">
						</c:when>
						<c:otherwise>
							<input type="hidden" name="accs" id="accs"  value="${not empty item.accs ? item.accs : shiroUser.account}">
							<input type="hidden" name="osType" value="${item.osType}" id="osType" />
							<input class="owner-sour" id="accNames"  type="text"  value="${not empty item.accs ? item.accs : shiroUser.name}" style="border-right:2px solid #e1e1e1;">
						</c:otherwise>
					</c:choose>				
					
					<div class="manage-owner-sour" style="height:370px;">
						<div class="shows-radio-boxs"></div>
						<ul class="shows-allorme-boxs">
							<li><input type="radio"  value="1"  name="searchOwnerType" ${item.osType eq '1' ? 'checked':'' }>查看全部</li>
							<li><input type="radio"  value="2"  name="searchOwnerType" ${item.osType eq '2' ? 'checked':'' }>查看自己</li>
						</ul>
						<div class="sub-dep-ul" data-type="search-tree">
							<ul id="tt1" >
						            
						   </ul>
			    		</div>
					    <div class="sure-cancle clearfix" style="width:120px">
							<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" id="checkedId" href="javascript:;"><label>确定</label></a>
							<a class="com-btnd bomp-btna reso-sub-clear fl_l" id="clearId"  href="javascript:;"><label>清空</label></a>
						</div>
					</div>
					</div>
			
					<!-- 最近联系时间  -->
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
							<li><a href="###"  onclick="setDdate(5)" id="d2" class="diy date-range">自定义</a></li>
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
							<li><a href="###" onclick="$('#optionlistId').val('${sop.optionlistId}');" >销售进程</a></li>
						<c:forEach var="sop" items="${options}">
							<li><a href="###" onclick="$('#optionlistId').val('${sop.optionlistId}');" title="${sop.optionName }">${sop.optionName }</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
			<div class="list-hid fl_l">								
				<input type="hidden" name="status" id="status" value="${item.status}">
			   <c:set var="statusName"  value="客户状态"/>
			   <c:choose>
				   	<c:when test="${item.status eq 1}"><c:set var="statusName" value="全部"/></c:when>
				   	<c:when test="${item.status eq 2}"><c:set var="statusName" value="意向客户"/></c:when>
				   	<c:when test="${item.status eq 3}"><c:set var="statusName" value="签约客户"/></c:when>
			  		<c:when test="${item.status eq 4}"><c:set var="statusName" value="沉默客户"/></c:when>
				   	<c:when test="${item.status eq 5}"><c:set var="statusName" value="流失客户"/></c:when>
			   </c:choose>
			   <dl class="select">
					<dt>${statusName }</dt>
					<dd>
						<ul>
							<li><a href="###" onclick="$('#status').val('1');">全部</a></li>		
							<li><a href="###" onclick="$('#status').val('2');">意向客户</a></li>	
							<li><a href="###" onclick="$('#status').val('3');">签约客户</a></li>	
							<li><a href="###" onclick="$('#status').val('4');">沉默客户</a></li>
							<li><a href="###" onclick="$('#status').val('5');">流失客户</a></li>					
						</ul>
					</dd>
				</dl>	
			</div>
		</div>
	</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询"  id="query"/>
		<a href="javascript:;" class="more"><i></i>更多</a>
  </div>
	<p class="com-moTag"><label class="a">数据分类：</label>
			<input type="hidden" id="followCustCation" value="${item.followCustCation}" name="followCustCation" />
			<c:forEach items="<%=FollowCustEnum.getFollowCust()%>" var="custCation">
				  	<a class="e ${custCation.state eq item.followCustCation ? 'e-hover':'' }" id="i_${custCation.state}" href="javascript:iCheck('${custCation.state}');">${custCation.descr}</a>
            </c:forEach>
	</p>
	<p class="com-moTag">
		<label class="a">行动标签：</label>
		<c:forEach items="${labelNamesList }" var="ln">
			<span label="1" class="e">${ln }</span>
		</c:forEach>
		<span id="addLabelBtn"  style="cursor: pointer;color: blue;" class="e">选择标签</span>
		<input id="allLabels" name="allLabels" type="hidden" value="${allLabels }"/>
	</p>
	<div class="com-btnlist hyx-cfu-btnlist fl_l">
		<input type="hidden" id="custIds" > <!-- 跟进时间调整 选中资源Id s -->
		<input type="hidden" id="followIds" ><!-- 跟进时间调整 选中跟进Id s -->
		<!-- <a href="javascript:;" class="com-btna demoBtn_a fl_l" id="demoBtn_"><label>测试</label></a>  -->
		<a href="javascript:;" class="com-btna fl_l" id="demoBtn_a"><label>跟进时间调整</label></a>
	</div>
	<div class="com-table hyx-fur-table com-mta fl_l" style="display:block;">
		<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
			<thead>
				<tr class="sty-bgcolor-b" role="head">
					<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
					<th><span class="sp sty-borcolor-b">操作</span></th> 
					<th ><span class="sp sty-borcolor-b" sort="true"  ><label class="lab-d">下次联系时间</label></span></th>
					<th><span class="sp sty-borcolor-b">客户姓名</span></th>
					<th><span class="sp sty-borcolor-b">单位名称</span></th>
					<th><span class="sp sty-borcolor-b" sort="true" ><label class="lab-d">销售进程</label></th>
					<th><span class="sp sty-borcolor-b" sort="true" ><label class="lab-d">最近联系时间</label></span></th>
					<th>所有者</th>
					<c:if test="${!empty defList && fn:length(defList) > 0 }">
						<c:forEach items="${defList}" var="defined" varStatus="vs">	
						     <th><span class="sp sty-borcolor-b">${defined.fieldName }</span></th>
				   		</c:forEach>
			   		</c:if>
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
<div class="hyx-cm-right hyx-cfu-right fl_l" id="list_right">
		<div class="hyx-cfu-card fl_l">
		<div class="tit fl_l"><span  class="sp"></span>
			<a href="javascript:;" class="icon-edit fl_r img-gray" title="编辑"></a>		
		</div>
		<c:choose>		
			<c:when test="${shiroUser.isState eq 1 }">
					<!-- 企业资源 -->
				<p class="list fl_l"><span>联系人：</span></p>
				<p class="list fl_l"><span>联系电话：</span></p>
				<p class="list fl_l"><span>邮&nbsp;&nbsp;箱：</span></p>
				<p class="list fl_l"><span>联系地址：</span></p>
			</c:when>
			<c:otherwise>
				<!-- 个人资源 -->
				<p class="list fl_l"><span>性别：</span></p>
				<p class="list fl_l"><span>联系电话：</span></p>
				<p class="list fl_l"><span>邮&nbsp;&nbsp;箱：</span></p>
				<p class="list fl_l"><span>联系地址：</span></p>	
		</c:otherwise>
		</c:choose>
	</div>
	<div class="hyx-cfu-tab">
		<ul class="tab-list clearfix">
			<li class="select li_first">跟进记录</li>
			<li >通话记录</li>
			<li class="li_last">点评信息</li>
		</ul>
	</div>
	<div class="hyx-wlm-cent-timeline hyx-cfu-timeline" id="follow_show">
		<!-- 暂无数据开始 -->
		<div class="none-bg">
			<p class="tit_none">暂无跟进记录！</p>
		</div>
		<!-- 暂无数据结束 -->
	</div>
</div>
</body>

</html>