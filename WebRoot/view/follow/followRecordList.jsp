<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/common-function/function.area.select.jsp"%>
<%@ include file="/common/include-cut-version.jsp"%>
<%@ include file="/common/common-function/function.table.jsp"%>
<%@ include file="/common/common-function/function.date.jsp"%>
<%@ include file="/common/common-function/function.searchHead.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/follow/followRecordList.js${_v}"></script>
<title>客户跟进-跟进记录</title>
<style type="text/css" rel="stylesheet">
<c:forEach items="${sorts}" var="sort">
	.com-table>table>tbody>tr>td:nth-child(${sort}),.com-table>table>thead>tr>th:nth-child(${sort}){display: none;}
</c:forEach>
</style>
</head>
<body>
<div class="hyx-layout">
<div class="hyx-cfu-left hyx-layout-content">
<input type="hidden" id="isState" value="${shiroUser.isState}">
<input type="hidden" id="isSys" value="${shiroUser.issys}">
<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
<form method="post">
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
		   <dl class="select pos${empty mutilSearchCodeSortMap['custTypeId'] ? '0' : mutilSearchCodeSortMap['custTypeId']}" data-input="[name='custTypeId']">
		   		 <input type="hidden" name="custTypeId" id="custTypeId" value="${item.custTypeId}">
				<dt>客户类型</dt>
				<dd>
					<ul>
						<li><a href="javascript:;" onclick="$('#custTypeId').val('');">客户类型</a></li>
						<c:forEach var="vs" items="${custTypeList }">
							<li><a href="javascript:;" data-value="${vs.optionlistId}" >${vs.optionName }</a></li>
						</c:forEach>
					</ul>
				</dd>
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
			<!-- 销售进程 -->
			<dl class="select pos${empty mutilSearchCodeSortMap['optionlistId'] ? '0' : mutilSearchCodeSortMap['optionlistId']}" data-input="[name='optionlistId']">
				<input type="hidden" name="optionlistId" id="optionlistId" value="${item.optionlistId }" />
				<dt>销售进程</dt>
				<dd>
					<ul>
						<li><a href="javascript:;" onclick="$('#optionlistId').val('');">销售进程</a></li>
						<c:forEach var="sop" items="${options}">
							<li><a href="javascript:;" data-value="${sop.optionlistId}"  title="${sop.optionName }">${sop.optionName }</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>

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
				<dl class="select pos${empty mutilSearchCodeSortMap['lastStartActionDate'] ? '0' : mutilSearchCodeSortMap['lastStartActionDate']}" >
					<!-- 最近联系时间  -->
					<input type="hidden" name="lastStartActionDate" id="d_lastStartActionDate" value="${item.lastStartActionDate }" />
				   <input type="hidden" name="lastEndActionDate" id="d_lastEndActionDate" value="${item.lastEndActionDate }"/>
				   <input type="hidden" name="dDateType" id="dDateType" value="${dDateType }" />
					<dt>${dDateName }</dt>
					<dd>
						<ul>
							<li><a href="javascript:;" onclick="setDdate(0)">最近联系时间</a></li>
							<li><a href="javascript:;" onclick="setDdate(1)">当天</a></li>
							<li><a href="javascript:;" onclick="setDdate(2)">本周</a></li>
							<li><a href="javascript:;" onclick="setDdate(3)">本月</a></li>
							<li><a href="javascript:;" onclick="setDdate(4)">半年</a></li>
							<li><a href="javascript:;" onclick="setDdate(5)"id="d1" class="diy date-range">自定义</a></li>
						</ul>
					</dd>
				</dl>


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
			<dl class="select pos${empty mutilSearchCodeSortMap['nextStartActionDate'] ? '0' : mutilSearchCodeSortMap['nextStartActionDate']}">
				<!--  下次联系时间  -->
			  <input type="hidden" name="nextStartActionDate" id="nextStartActionDate" value="${item.nextStartActionDate }" />
			  <input type="hidden" name="nextEndActionDate" id="nextEndActionDate" value="${item.nextEndActionDate }"/>
			  <input type="hidden" name="nDateType" id="nDateType" value="${nDateType }" />
				<dt>${nDateName }</dt>
				<dd>
					<ul>
						<li><a href="javascript:;" onclick="setNdate(0)">下次联系时间</a></li>
						<li><a href="javascript:;" onclick="setNdate(1)">当天</a></li>
						<li><a href="javascript:;" onclick="setNdate(2)">本周</a></li>
						<li><a href="javascript:;" onclick="setNdate(3)">本月</a></li>
						<li><a href="javascript:;" onclick="setNdate(4)">半年</a></li>
						<li><a href="javascript:;" onclick="setNdate(5)" class="diy date-range" id="d2">自定义</a></li>
					</ul>
				</dd>
			</dl>
			<div class="reso-sub-dep pos${empty mutilSearchCodeSortMap['accs'] ? '0' : mutilSearchCodeSortMap['accs']}">
				<c:choose>
						<c:when test="${shiroUser.issys eq 0 }">
							<input class="owner-sour-nostr"  name="accs" type="text" readonly="readonly" value="${shiroUser.name}">
						</c:when>
						<c:otherwise>
							<input type="hidden" name="accs"  id="accs" value="${item.accs}">
							<input type="hidden" name="osType" value="${item.osType}" id="osType" />
							<input class="owner-sour" id="accNames" type="text"  value="${item.accs}">
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
							<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l"  id="checkedId" href="javascript:;"><label>确定</label></a>
							<a class="com-btnd bomp-btna fl_l" id="clearId" href="javascript:;"><label>清空</label></a>
						</div>
					</div>
			</div>
		   <dl class="select pos${empty mutilSearchCodeSortMap['followType'] ? '0' : mutilSearchCodeSortMap['followType']}" data-input="[name='followType']">
		   		 <input type="hidden" name="followType" id="followType" value="${item.followType }" />
				<dt>下次联系方式</dt>
				<dd>
					<ul>
						<li><a href="javascript:;" data-value="">下次联系方式</a></li>
						<li><a href="javascript:;"  data-value="1"  >电话联系</a></li>
						<li><a href="javascript:;"  data-value="2" >会客联系</a></li>
						<li><a href="javascript:;"  data-value="3" >客户来电</a></li>
						<li><a href="javascript:;" 	data-value="4"  >短信联系</a></li>
						<li><a href="javascript:;"  data-value="5" >QQ联系</a></li>
						<li><a href="javascript:;"  data-value="6" >邮件联系</a></li>
					</ul>
				</dd>
			</dl>
			<dl class="select pos${empty mutilSearchCodeSortMap['status'] ? '0' : mutilSearchCodeSortMap['status']}" data-input="[name='status']">
				<input type="hidden" name="status" id="status" value="${item.status}">
				<dt>客户状态</dt>
				<dd>
					<ul>
						<li><a href="javascript:;" onclick="$('#status').val('');" data-value="1">客户状态</a></li>
						<li><a href="javascript:;" data-value="2">意向客户</a></li>
						<li><a href="javascript:;" data-value="3">签约客户</a></li>
						<li><a href="javascript:;" data-value="4">沉默客户</a></li>
						<li><a href="javascript:;" data-value="5">流失客户</a></li>
					</ul>
				</dd>
			</dl>

			<!-- 有效联系 -->
				<dl class="select pos${empty mutilSearchCodeSortMap['effectiveness'] ? '0' : mutilSearchCodeSortMap['effectiveness']}" data-input="[name='effectiveness']">
				<input type="hidden" name="effectiveness" id="effectiveness" value="${item.effectiveness }">
				<dt>有效联系</dt>
				<dd>
					<ul>
						<li><a href="javascript:;" onclick="$('#effectiveness').val('');">有效联系</a></li>
						<li><a href="javascript:;" data-value="1">是</a></li>
						<li><a href="javascript:;" data-value="0">否</a></li>
					</ul>
				</dd>
			</dl>
			<!-- 跟进人 -->
			 <div class="reso-sub-dep pos${empty mutilSearchCodeSortMap['faccs'] ? '0' : mutilSearchCodeSortMap['faccs']}">
				<c:choose>
						<c:when test="${shiroUser.issys eq 0 }">
							<input class="owner-sour-nostr"  name="faccs" type="text" readonly="readonly" value="${shiroUser.name}">
						</c:when>
						<c:otherwise>
							<input type="hidden" name="faccs"  id="faccs" value="${item.faccs}">
							<%-- <input type="hidden" name="fosType" value="${item.fosType}" id="fosType" /> --%>
							<input class="owner-sour" id="faccNames" type="text"  value="${empty item.faccs ? '跟进人' : item.faccs}">
						</c:otherwise>
					</c:choose>
					<div class="manage-owner-sour" style="height:370px;">
						<div class="shows-radio-boxs"></div>
						<%-- <ul class="shows-allorme-boxs">
							<li><input type="radio"  value="1"  name="searchOwnerType_2" ${item.fosType eq '1' ? 'checked':'' }>查看全部</li>
							<li><input type="radio"  value="2"  name="searchOwnerType_2" ${item.fosType eq '2' ? 'checked':'' }>查看自己</li>
						</ul> --%>
						<div class="sub-dep-ul" data-type="search-tree">
								<ul id="tt2"  >

						        </ul>
			    		</div>
					    <div class="sure-cancle clearfix" style="width:120px">
							<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l"  id="checkedId_2" href="javascript:;"><label>确定</label></a>
							<a class="com-btnd bomp-btna fl_l" id="clearId_2" href="javascript:;"><label>清空</label></a>
						</div>
					</div>
				</div>
			<%-- 		 <!-- 共有者 -->
			 <div class="reso-sub-dep pos${empty mutilSearchCodeSortMap['comaccs'] ? '0' : mutilSearchCodeSortMap['comaccs']}">
			 		<c:choose>
					 <c:when test="${shiroUser.issys eq 0 }">
						 <input class="owner-sour-nostr"  name="comaccs" type="text" readonly="readonly" value="${shiroUser.name}" />
					 </c:when>
					 <c:otherwise>
						 <input type="hidden" name="comaccs" id="comaccs"  value="${item.comaccs}">
						 <input type="hidden" name="comosType" value="${item.comosType}" id="comosType" />
						 <input class="owner-sour" id="comaccNames"  type="text"  value="${item.comaccs}">
					 </c:otherwise>
				 </c:choose>
					<div class="manage-owner-sour" style="height:370px;" >
						<div class="shows-radio-boxs"></div>
						<ul class="shows-allorme-boxs">
							<li><input type="radio"  value="1"  name="searchOwnerType_3" ${item.comosType eq '1' ? 'checked':'' }>查看全部</li>
							<li><input type="radio"  value="2"  name="searchOwnerType_3" ${item.comosType eq '2' ? 'checked':'' }>查看自己</li>
						</ul>
						<div class="sub-dep-ul" style="width:190px;">
							<ul id="tt3"  >

						   </ul>
			    		</div>
					    <div class="sure-cancle clearfix" style="width:120px">
							<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" id="checkedId3" href="javascript:;"><label>确定</label></a>
							<a class="com-btnd bomp-btna reso-sub-clear fl_l" id="clearId3"  href="javascript:;"><label>清空</label></a>
						</div>
					</div>
				</div> --%>

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
		</div>
	</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询"  id="query"/>
		<input class="query-button fl_r" type="button" value="高级查询"  id="superQuery" data-url="${ctx }/highSearch/toPage?module=2" data-title="高级查询" />
		<c:if test="${mutilSearchCodeSortMap.keySet().size() gt 5 }"><a href="javascript:;" class="more"><i></i>更多</a></c:if>
  </div>
	<p class="com-moTag">
		<label class="a">行动标签：</label>
		<c:forEach items="${labelNamesList }" var="ln">
			<span label="1" class="e">${ln }</span>
		</c:forEach>
		<span id="addLabelBtn"  style="cursor: pointer;color: blue;" class="e">选择标签</span>
		<input id="allLabels" name="allLabels" type="hidden" value="${allLabels }"/>
	</p>
	<div class="com-table hyx-fur-table fl_l" style="display:block;margin-top:40px;">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">操作</span></th>
					<th><span class="sp sty-borcolor-b"><label class="lab-d">联系时间</label></span></th>
					<th><span class="sp sty-borcolor-b">${shiroUser.isState eq 0 ?'单位名称' : '客户名称'}</span></th>
					<th><span class="sp sty-borcolor-b">${shiroUser.isState eq 0 ? '客户姓名' : '联系人'}</span></th>
					<th><span class="sp sty-borcolor-b">客户类型</span></th>
					<th><span class="sp sty-borcolor-b">客户状态</span></th>
					<th><span class="sp sty-borcolor-b"><label class="lab-d">销售进程</label></th>
					<th><span class="sp sty-borcolor-b"><label class="lab-d">有效联系</label></th>
					<th><span class="sp sty-borcolor-b">资源分组</span></th>
					<th><span class="sp sty-borcolor-b"><label class="lab-d">下次联系时间</label></span></th>
					<th>所有者</th>
					<th><span class="sp sty-borcolor-b">跟进人</span></th>
					<th><span class="sp sty-borcolor-b">下次联系方式</span></th>

					<th><span class="sp sty-borcolor-b">联系电话</span></th>
					<th><span class="sp sty-borcolor-b">所在地区</span></th>
			   		<th><span class="sp sty-borcolor-b">所属行业</span></th>
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
<!-- 右侧信息 -->
<div class="hyx-cm-right hyx-cfu-right hyx-layout-side" id="record_right">
<div class="hyx-cfu-card fl_l">
		<div class="tit fl_l"><span class="sp"></span>
		<c:if test="${shiroUser.isState eq 1 }"> <!-- 企业资源 -->
			<div class="icon-conte-list img-gray" title="通讯录">
			<div class="drop" style="display:none;" title="">
				<div class="arrow"><em>◆</em><span>◆</span></div>
					<ul>
						<li><p class="list fl_l"></p></li>
					</ul>
				</div>
			</div>
		</c:if>
			<a href="javascript:;" class="icon-edit  fl_r img-gray" title="编辑"></a>
		</div>
		<c:choose>
			<c:when test="${shiroUser.isState eq 1 }">
				<!-- 企业资源 -->
				<p class="list fl_l"></p>
				<p class="list fl_l"><span>联系电话：</span></p>
				<p class="list fl_l"><span>邮&nbsp;&nbsp;箱：</span></p>
				<p class="list fl_l"><span>联系地址：</span></p>
			</c:when>
			<c:otherwise>
				<!-- 个人资源 -->
				<p class="list fl_l"><span>性别：</span>
				</p>
				<p class="list fl_l"><span>联系电话：</span></p>
				<p class="list fl_l"><span>邮&nbsp;&nbsp;箱：</span></p>
				<p class="list fl_l"><span>联系地址：</span></p>
			</c:otherwise>
			</c:choose>
	</div>
	<div class="hyx-cfu-tab">
		<span title="跟进信息" class="cfu-tab-foll">跟进信息</span>
	</div>
	<div class="hyx-wlm-cent-timeline hyx-cfu-timeline" >
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
