<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@page import="com.qftx.base.enums.FollowCustEnum"%>
<%@ include file="/common/common-function/function.area.select.jsp"%>
<%@ include file="/common/include-cut-version.jsp"%>
<%@ include file="/common/common-function/function.table.jsp"%>
<%@ include file="/common/common-function/function.date.jsp"%>
<%@ include file="/common/common-function/function.searchHead.jsp"%>

<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/follow/followList.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/button_auth.js${_v}"></script><!-- 按钮权限 -->
<title>客户跟进-客户跟进</title>
<style type="text/css" rel="stylesheet">
<c:forEach items="${sorts}" var="sort">
	.com-table>table>tbody>tr>td:nth-child(${sort}),.com-table>table>thead>tr>th:nth-child(${sort}){display: none;}
</c:forEach>
</style>
</head>
<body>
<div class="hyx-layout">
<div class="hyx-cfu-left hyx-layout-content">
	<form id="myForm" method="post">
	<input type="hidden" id="status" name="status" value=${status }>
	<input type="hidden" id="signSetting" value=${signSetting }>
	<input type="hidden" id="adminSignAuth" value=${adminSignAuth }>
	<input type="hidden" id="isState" value="${shiroUser.isState}">
	<input type="hidden" id="isSys" value="${shiroUser.issys}">
	<input type="hidden" id="serverDay" value="${shiroUser.serverDay}">
	<input type="hidden" id="userAccount" value="${shiroUser.account}">
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<input type="hidden" id="add_contract" name="add_contract" value="${ addContract}" />
   <div class="com-search hyx-cfu-search">
      <div class="com-search-box fl_l">
	    <div class="search clearfix" >
		   <div class="select_outerDiv fl_l">
		   <span class="icon_down_5px"></span>
		    <select class="fl_l search_head_select"  name="queryType">
	   		<c:if test="${ !empty tList && fn:length(tList) > 0 }">
				<c:forEach items="${tList}" var="defined" varStatus="vs">
					 <option value="${defined.searchCode }" ${item.queryType eq defined.searchCode? 'selected' :''}>${defined.searchName }</option>
		   		</c:forEach>
	   		</c:if>
	   		</select>
		   	</div>
		   	<input class="input-query fl_l" type="text" name="queryText" value="${item.queryText }" />
		   	<label class="hide-span"></label>
		  </div>
	   <div class="list-box">

		   <c:set var="custTypeName"  value="客户类型"/>
		   <c:forEach var="v" items="${custTypeList }">
				<c:if test="${v.optionlistId eq item.custTypeId }">
					<c:set var="custTypeName">${v.optionName }</c:set>
				</c:if>
			</c:forEach>
		   <dl class="select pos${empty mutilSearchCodeSortMap['custTypeId'] ? '0' : mutilSearchCodeSortMap['custTypeId']}" data-input="[name='custTypeId']" >
				<dt>${custTypeName }</dt>
				<dd>
					<ul>
						<li><a href="javascript:;" onclick="$('#custTypeId').val('');">客户类型</a></li>
						<c:forEach var="vs" items="${custTypeList }">
							<li><a href="javascript:;" data-value="${vs.optionlistId}">${vs.optionName }</a></li>
						</c:forEach>
					</ul>
				</dd>
				<input type="hidden" name="custTypeId" id="custTypeId" value="${item.custTypeId}">
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
			<dl class="select pos${empty mutilSearchCodeSortMap['nextStartActionDate'] ? '0' : mutilSearchCodeSortMap['nextStartActionDate']}" >
				<dt>${nDateName }</dt>
				<dd>
					<ul>
						<li><a href="javascript:;" onclick="setNdate(0)">下次联系时间</a></li>
						<li><a href="javascript:;" onclick="setNdate(1)">当天</a></li>
						<li><a href="javascript:;" onclick="setNdate(2)">本周</a></li>
						<li><a href="javascript:;" onclick="setNdate(3)">本月</a></li>
						<li><a href="javascript:;" onclick="setNdate(4)">半年</a></li>
						<li><a href="javascript:;" onclick="setNdate(5)" id="d1" class="diy date-range">自定义</a></li>
					</ul>
				</dd>
			</dl>
			<!-- 所有者 -->
		     <div class="reso-sub-dep pos${empty mutilSearchCodeSortMap['accs'] ? '0' : mutilSearchCodeSortMap['accs']}">
				 <c:choose>
					 <c:when test="${shiroUser.issys eq 0 }">
						 <input class="owner-sour-nostr"  name="accs" type="text" readonly="readonly" value="${shiroUser.name}" />
					 </c:when>
					 <c:otherwise>
						 <input type="hidden" name="accs" id="accs"  value="${item.accs}">
						 <input type="hidden" name="osType" value="${item.osType}" id="osType" />
						 <input class="owner-sour" id="accNames"  type="text"  value="${item.accs}" style="border-right:2px solid #e1e1e1;">
					 </c:otherwise>
				 </c:choose>
				 <div class="manage-owner-sour " style="height:370px;" >
					 <div class="shows-radio-boxs"></div>
					 <ul class="shows-allorme-boxs">
						 <li><input type="radio"  value="1"  name="searchOwnerType" ${item.osType eq '1' ? 'checked':'' }>查看全部</li>
						 <li><input type="radio"  value="2"  name="searchOwnerType" ${item.osType eq '2' ? 'checked':'' }>查看自己</li>
					 </ul>
					 <div class="sub-dep-ul" style="width:190px;" data-type="search-tree">
						 <ul id="tt1" >

						 </ul>
					 </div>
					 <div class="sure-cancle clearfix" style="width:120px">
						 <a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" id="checkedId" href="javascript:;"><label>确定</label></a>
						 <a class="com-btnd bomp-btna reso-sub-clear fl_l" id="clearId"  href="javascript:;"><label>清空</label></a>
					 </div>
				 </div>
			 </div>
			 	<!-- 开始联系时间  -->
				<input type="hidden" name="initStartFollowDate" id="initStartFollowDate" value="${item.initStartFollowDate }" />
			   <input type="hidden" name="initEndFollowDate" id="initEndFollowDate" value="${item.initEndFollowDate }"/>
			   <input type="hidden" name="mDateType" id="mDateType" value="${mDateType }" />
			   <c:set var="mDateName" value="开始联系时间" />
			   <c:choose>
			   		<c:when test="${mDateType eq 1 }"><c:set var="mDateName" value="当天" /></c:when>
			   		<c:when test="${mDateType eq 2 }"><c:set var="mDateName" value="本周" /></c:when>
			   		<c:when test="${mDateType eq 3 }"><c:set var="mDateName" value="本月" /></c:when>
			   		<c:when test="${mDateType eq 4 }"><c:set var="mDateName" value="半年" /></c:when>
			   		<c:when test="${mDateType eq 5 }">
			   			<fmt:parseDate var="msd" value="${item.initStartFollowDate }" pattern="yyyy-MM-dd" />
						<fmt:parseDate var="med" value="${item.initEndFollowDate }" pattern="yyyy-MM-dd" />
			   			<c:set var="mDateName">
			   				<fmt:formatDate value="${msd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${med }" pattern="yy.MM.dd"/>
			   			</c:set>
			   		</c:when>
			   </c:choose>
				<dl class="select pos${empty mutilSearchCodeSortMap['initStartFollowDate'] ? '0' : mutilSearchCodeSortMap['initStartFollowDate']}">
					<dt>${mDateName }</dt>
					<dd>
						<ul>
							<li><a href="javascript:;" onclick="setMdate(0)">开始联系时间</a></li>
							<li><a href="javascript:;" onclick="setMdate(1)">当天</a></li>
							<li><a href="javascript:;" onclick="setMdate(2)">本周</a></li>
							<li><a href="javascript:;" onclick="setMdate(3)">本月</a></li>
							<li><a href="javascript:;" onclick="setMdate(4)">半年</a></li>
							<li><a href="javascript:;"  onclick="setMdate(5)" id="d3" class="diy date-range">自定义</a></li>
						</ul>
					</dd>
				</dl>
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
				<dl class="select pos${empty mutilSearchCodeSortMap['lastStartActionDate'] ? '0' : mutilSearchCodeSortMap['lastStartActionDate']}">
					<dt>${dDateName }</dt>
					<dd>
						<ul>
							<li><a href="javascript:;" onclick="setDdate(0)">最近联系时间</a></li>
							<li><a href="javascript:;" onclick="setDdate(1)">当天</a></li>
							<li><a href="javascript:;" onclick="setDdate(2)">本周</a></li>
							<li><a href="javascript:;" onclick="setDdate(3)">本月</a></li>
							<li><a href="javascript:;" onclick="setDdate(4)">半年</a></li>
							<li><a href="javascript:;"  onclick="setDdate(5)" id="d2" class="diy date-range">自定义</a></li>
						</ul>
					</dd>
				</dl>
			<!-- 销售进程 -->
			<dl class="select pos${empty mutilSearchCodeSortMap['optionlistId'] ? '0' : mutilSearchCodeSortMap['optionlistId']}" data-input="[name='optionlistId']">
				<c:if test="${empty optionlistId}">
					<dt>销售进程</dt>
				</c:if>
				<c:if test="${not empty optionlistId}">
					<c:forEach items="${options }" var="os">
						<c:if test="${optionlistId eq os.optionlistId}">
							<dt>${os.optionName }</dt>
						</c:if>
					</c:forEach>
				</c:if>
				<dd>
					<ul>
						<li><a href="javascript:;" onclick="$('#optionlistId').val('');">销售进程</a></li>
						<c:forEach var="sop" items="${options}">
							<li><a href="javascript:;" data-value="${sop.optionlistId}" >${sop.optionName }</a></li>
						</c:forEach>
					</ul>
				</dd>
				<input type="hidden" name="optionlistId" id="optionlistId" value="${optionlistId }" />
			</dl>

			 <dl class="select pos${empty mutilSearchCodeSortMap['companyTrade'] ? '0' : mutilSearchCodeSortMap['companyTrade']}" data-input="[name='companyTrade']">
				<dt>所属行业</dt>
					<dd>
						<ul>
							<li><a href="javascript:;" onclick="$('#s_companyTrade').val('');" title="所属行业">所属行业</a></li>
							<c:forEach items="${companyTrades }" var="ct">
								<li><a href="javascript:;" data-value="${ct.optionlistId}" >${ct.optionName }</a></li>
							</c:forEach>
						</ul>
					</dd>
					 <input name="companyTrade" type="hidden" value="" id="s_companyTrade">
			   </dl>

			   <%-- <dl class="select pos${empty mutilSearchCodeSortMap['status'] ? '0' : mutilSearchCodeSortMap['status']}" data-input="[name='status']">
			   		<input type="hidden" name="status" id="status" value="${item.status}">
					<dt>客户状态</dt>
					<dd>
						<ul>
							<li><a href="javascript:;" data-value="1" >全部</a></li>
							<li><a href="javascript:;" data-value="2">意向客户</a></li>
							<li><a href="javascript:;" data-value="3">签约客户</a></li>
							<li><a href="javascript:;" data-value="4">沉默客户</a></li>
							<li><a href="javascript:;" data-value="5">流失客户</a></li>
						</ul>
					</dd>
				</dl> --%>
				
				 <dl class="select pos${empty mutilSearchCodeSortMap['source'] ? '0' : mutilSearchCodeSortMap['source']}" data-input="[name='source']">
			   		<input type="hidden" name="source" id="source" value="${item.source}">
					<dt>客户来源</dt>
					<dd>
						<ul>
							<li><a href="javascript:;" data-value="0" >全部</a></li>
							<li><a href="javascript:;" data-value="1">自有导入</a></li>
							<li><a href="javascript:;" data-value="2">分配交接</a></li>
							<li><a href="javascript:;" data-value="3">公海取回</a></li>
							<li><a href="javascript:;" data-value="4">AI初筛</a></li>
							<li><a href="javascript:;" data-value="5">在线表单</a></li>
							<li><a href="javascript:;" data-value="6">数据合作</a></li>
						</ul>
					</dd>
				</dl>
				   <dl class="select resGroup pos${empty mutilSearchCodeSortMap['resGroup'] ? '0' : mutilSearchCodeSortMap['resGroup']}" data-input="[name='resGroup']">
						<dt>资源分组</dt>
						<dd>
							<ul class="resource-group-tree" data-url="${ctx}/res/group/get_res_group_json">
								<!-- 部门树 -->
							</ul>
						</dd>
						 <input name="resGroup" type="hidden" value="" id="s_groupId">
				   </dl>
				<dl class="select pos${empty mutilSearchCodeSortMap['area'] ? '0' : mutilSearchCodeSortMap['area']}" data-select-type="area">
					<dt>所在地区</dt>
				</dl>
				  <c:forEach items="${definedSearchCodes }" var="defindeCode">
				  	<c:choose>
				  		<c:when test="${defindeCode.dataType == 2}"> <!-- 日期型 -->
				  			<dl class="select pos${empty mutilSearchCodeSortMap[defindeCode.searchCode] ? '0' : mutilSearchCodeSortMap[defindeCode.searchCode]}">
					  		<c:forEach items="${defindeCode.childList}" var="childCode">
					  			<input type="hidden" name="${childCode.name }" data-type="${childCode.order eq 0 ? 'start':'end' }"/>
					  		</c:forEach>
							<dt>${defindeCode.searchName}</dt>
							<dd>
								<ul>
									<li><a href="javascript:;" data-type="0">${defindeCode.searchName}</a></li>
									<li><a href="javascript:;" data-type="1">当天</a></li>
									<li><a href="javascript:;" data-type="2">本周</a></li>
									<li><a href="javascript:;" data-type="3">本月</a></li>
									<li><a href="javascript:;" data-type="4">半年</a></li>
									<li><a  class="date-range custom-picker" >自定义</a></li>
								</ul>
							</dd>
						</dl>
				  		</c:when>
				  		<c:when test="${defindeCode.dataType == 3}" > <!-- 单选 -->
				  			<dl class="select pos${empty mutilSearchCodeSortMap[defindeCode.searchCode] ? '0' : mutilSearchCodeSortMap[defindeCode.searchCode]}" data-input="[name='${defindeCode.searchCode }']">
								<dt>${defindeCode.searchName}</dt>
								<dd>
									<ul>
										<li><a href="javascript:;" onclick="$('#${defindeCode.searchCode }').val('');" title="${defindeCode.searchName}">${defindeCode.searchName}</a></li>
										<c:forEach items="${defindeCode.childList }"  var="childCode">
											<li><a href="javascript:;" data-value="${childCode.value}" title="${childCode.name }">${childCode.name}</a></li>
										</c:forEach>
									</ul>
								</dd>
								 <input name="${defindeCode.searchCode }" type="hidden"  id="${defindeCode.searchCode }">
						   </dl>
				  		</c:when>
				  		<c:when test="${defindeCode.dataType == 4}"> <!-- 多选 -->
				  			<dl class="select pos${empty mutilSearchCodeSortMap[defindeCode.searchCode] ? '0' : mutilSearchCodeSortMap[defindeCode.searchCode]}" data-input="[name='${defindeCode.searchCode }']" data-multi="true">
								<dt>${defindeCode.searchName}</dt>
								<dd>
									<ul>
										<c:forEach items="${defindeCode.childList }" var="childCode">
											<li><a href="javascript:;" data-value="${childCode.value}" title="${childCode.name }">${childCode.name}</a></li>
										</c:forEach>
									</ul>
								</dd>
								 <input name="${defindeCode.searchCode }" type="hidden" id="${defindeCode.searchCode }">
						   </dl>
				  		</c:when>
				  	</c:choose>

				  </c:forEach>

		</div>
	</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询"  id="query"/>
		<input class="query-button fl_r" type="button" value="高级查询"  id="superQuery" data-url="${ctx }/highSearch/toPage?module=1" data-title="高级查询" data-param="&status=${status }" />
  		<c:if test="${mutilSearchCodeSortMap.keySet().size() gt 5 }"><a href="javascript:;" class="more"><i></i>更多</a></c:if>
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
		<a data-authority="custFollow_changeFollowDate" href="javascript:;" class="com-btna fl_l" id="demoBtn_a"><label>跟进时间调整</label></a>
	</div>
	<input type="hidden" name="orderKey" id="orderKey" value="" />
	<div class="com-table hyx-fur-table com-mta fl_l" style="display:block;">
		<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
			<thead>
				<tr class="sty-bgcolor-b" role="head">
					<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
					<th><span class="sp sty-borcolor-b">操作</span></th>
					<th hidesort="true"><span class="sp sty-borcolor-b" sortColumn="NEXTFOLLOW_DATE" sort="true" ><label class="lab-d">下次联系时间</label></span></th>
					<th><span class="sp sty-borcolor-b">${shiroUser.isState eq 0 ?'单位名称' : '客户名称'}</span></th>
					<th><span class="sp sty-borcolor-b">${shiroUser.isState eq 0 ? '客户姓名' : '联系人'}</span></th>
					<th><span class="sp sty-borcolor-b">客户类型</span></th>
					<th><span class="sp sty-borcolor-b">销售进程</span></th>
					<th hidesort="true"><span class="sp sty-borcolor-b" sortColumn="ACTION_DATE" sort="true" ><label class="lab-d">最近联系时间</label></span></th>
			   		<th><span class="sp sty-borcolor-b">客户状态</span></th>
			   		<th><span class="sp sty-borcolor-b">资源分组</span></th>
			   		<th hidesort="true"><span class="sp sty-borcolor-b" sortColumn="AMOYTOCUSTOMER_DATE" sort="true"><label class="lab-d">开始联系时间</label></span></th>
			   		<th><span class="sp sty-borcolor-b">客户来源</span></th>
			   		<th><span class="sp sty-borcolor-b">所有者</span></th>
			   		<th><span class="sp sty-borcolor-b">联系电话</span></th>
			   		<th><span class="sp sty-borcolor-b">所在地区</span></th>
			   		<th><span class="sp sty-borcolor-b">所属行业</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined1"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined2"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined3"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined4"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined5"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined6"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined7"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined8"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined9"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined10"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined11"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined12"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined13"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined14"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined15"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined16"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined17"]}</span></th>
			   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined18"]}</span></th>
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
<div class="hyx-cm-right hyx-cfu-right hyx-layout-side" id="list_right">
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
</div>
</body>

</html>
