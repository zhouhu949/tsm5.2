<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include-cut-version.jsp"%>
<%@ include file="/common/common-function/function.table.jsp"%>
<%@ include file="/common/common-function/function.date.jsp"%>
<%@ include file="/common/common-function/function.searchHead.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/view/follow/saleProcess.js${_v}"></script>
<title>客户跟进-销售进程</title>
</head>
<body>
<form method="post">
	<input type="hidden" id="isState" value="${shiroUser.isState}">
	<input type="hidden" id="isSys" value="${shiroUser.issys}">
<div class="hyx-ctr" >
    <div class="com-search hyx-cfu-search">
      <div class="com-search-box fl_l">
	   <div class="search clearfix" >
		   <div class="select_outerDiv fl_l">
		   <span class="icon_down_5px"></span>
		    <select class="fl_l search_head_select"  name="queryType">
	   		<option value = "1"  <c:if test="${item.queryType ne 2 && item.queryType ne 3}">selected</c:if>>客户名称</option>
	   		<option value = "2"  <c:if test="${item.queryType eq 2 }">selected</c:if>>联系人</option>
	   		<option value = "3"  <c:if test="${item.queryType eq 3}">selected</c:if>>联系电话</option>
	   		</select>
		   	</div>
		   	<input class="input-query fl_l" type="text" name="queryText" value="${item.queryText }" />
		   	<label class="hide-span"></label>
		  </div>
	   <div class="list-box">
	   		<!-- 本次销售进程 -->
	   		<input type="hidden" name="optionlistId" id="s_optionlistId" value="${item.optionlistId }" />
			<c:set var="nowsn" value="本次销售进程"/>
			<c:forEach var="sopn" items="${options }">
				<c:if test="${sopn.optionlistId eq item.optionlistId }">
					<c:set var="nowsn" value="${sopn.optionName }"/>
				</c:if>
			</c:forEach>
	   		<dl class="select">
				<dt>${nowsn}</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="$('#s_optionlistId').val('');">本次销售进程</a></li>
						<c:forEach var="sop" items="${options}">
							<li><a href="###" onclick="$('#s_optionlistId').val('${sop.optionlistId}');" title="${sop.optionName }">${sop.optionName }</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>

			<!-- 上次销售进程 -->
	   		<input type="hidden" name="lastSaleProcessId" id="s_lastSaleProcessId" value="${item.lastSaleProcessId }" />
			<c:set var="lastsn" value="上次销售进程"/>
			<c:forEach var="lsopn" items="${options }">
				<c:if test="${lsopn.optionlistId eq item.lastSaleProcessId }">
					<c:set var="lastsn" value="${lsopn.optionName }"></c:set>
				</c:if>
			</c:forEach>
			<dl class="select">
				<dt>${lastsn}</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="$('#s_lastSaleProcessId').val('');">上次销售进程</a></li>
						<c:forEach var="lsop" items="${options}">
							<li><a href="###" onclick="$('#s_lastSaleProcessId').val('${lsop.optionlistId}');" title="${lsop.optionName }">${lsop.optionName }</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>

			<!-- 销售进程变化 -->
			<input type="hidden" name="changeType" id="s_changeType" value="${item.changeType }">
			<c:set var="changeTypeName" value="销售进程变化" />
			<c:choose>
				<c:when test="${item.changeType eq '2' }"><c:set var="changeTypeName" value="有变化" /></c:when>
				<c:when test="${item.changeType eq '3' }"><c:set var="changeTypeName" value="无变化" /></c:when>
				<c:when test="${item.changeType eq '4' }"><c:set var="changeTypeName" value="新增" /></c:when>
			</c:choose>
			<dl class="select">
				<dt>${changeTypeName }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="$('#s_changeType').val('');">销售进程变化</a></li>
						<li><a href="###" onclick="$('#s_changeType').val('4');">新增</a></li>
						<li><a href="###" onclick="$('#s_changeType').val('2');">有变化</a></li>
						<li><a href="###" onclick="$('#s_changeType').val('3');">无变化</a></li>
					</ul>
				</dd>
			</dl>

			<div class="reso-sub-dep fl_l">
					<c:choose>
						<c:when test="${shiroUser.issys eq 0 }">
							<input style="border-right: 2px solid #e1e1e1;" class="owner-sour-nostr"  name="accs" type="text" readonly="readonly" value="${shiroUser.name}">
						</c:when>
						<c:otherwise>
							<input type="hidden" name="accs"  id="accs"  value="${not empty item.accs ? item.accs : shiroUser.account}">
							<input type="hidden" name="osType" value="${item.osType}" id="osType" />
							<input style="border-right: 2px solid #e1e1e1;" class="owner-sour" id="accNames"  type="text"  value="${not empty item.accs ? item.accs : shiroUser.name}">
						</c:otherwise>
					</c:choose>
					<div class="manage-owner-sour" style="height:370px;">
						<div class="shows-radio-boxs"></div>
						<ul class="shows-allorme-boxs">
							<li><input type="radio"  value="1"  name="searchOwnerType" ${item.osType eq '1' ? 'checked':'' }>查看全部</li>
							<li><input type="radio"  value="2"  name="searchOwnerType" ${item.osType eq '2' ? 'checked':'' }>查看自己</li>
						</ul>
						<div class="sub-dep-ul" data-type="search-tree">
								<ul id="tt1">

						        </ul>
			    		</div>
					    <div class="sure-cancle clearfix" style="width:120px">
							<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" id="checkedId" href="javascript:;"><label>确定</label></a>
							<a class="com-btnd bomp-btna fl_l" id="clearId" href="javascript:;"><label>清空</label></a>
						</div>
					</div>
					</div>

			<!-- 最近联系时间  -->
			<input type="hidden" name="lastStartActionDate" id="d_lastStartActionDate" value="${item.lastStartActionDate }" />
		   <input type="hidden" name="lastEndActionDate" id="d_lastEndActionDate" value="${item.lastEndActionDate }"/>
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
						<li><a href="###" onclick="setDdate(5)"id="d1" class="diy date-range">自定义</a></li>
					</ul>
				</dd>
			</dl>


			<div class="list-hid fl_l">
				<input type="hidden" name="actionType" id="s_actionType" value="${item.actionType }" />
		  		<c:set var="actionTypeName" value="最近联系方式"/>
		  		<c:choose>
		  		<c:when test="${item.actionType eq '1'}"><c:set var="actionTypeName" value="电话联系"/></c:when>
		  		<c:when test="${item.actionType eq '2'}"><c:set var="actionTypeName" value="会客联系"/></c:when>
		  		<c:when test="${item.actionType eq '3'}"><c:set var="actionTypeName" value="客户来电"/></c:when>
		  		<c:when test="${item.actionType eq '4'}"><c:set var="actionTypeName" value="短信联系"/></c:when>
		  		<c:when test="${item.actionType eq '5'}"><c:set var="actionTypeName" value="qq联系"/></c:when>
		  		<c:when test="${item.actionType eq '6'}"><c:set var="actionTypeName" value="邮件联系"/></c:when>
		  	</c:choose>
				<dl class="select">
					<dt>${actionTypeName }</dt>
					<dd>
						<ul>
							<li><a href="###"  onclick="$('#s_actionType').val('');" >最近联系方式</a></li>
							<li><a href="###"  onclick="$('#s_actionType').val('1');">电话联系</a></li>
							<li><a href="###"  onclick="$('#s_actionType').val('2');">会客联系</a></li>
							<li><a href="###"  onclick="$('#s_actionType').val('3');">客户来电</a></li>
							<li><a href="###"  onclick="$('#s_actionType').val('4');">短信联系</a></li>
							<li><a href="###"  onclick="$('#s_actionType').val('5');">QQ联系</a></li>
							<li><a href="###"  onclick="$('#s_actionType').val('6');">邮件联系</a></li>
						</ul>
					</dd>
				</dl>

			</div>
		</div>
	</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询"  id="query"/>
		<a href="javascript:;" class="more"><i></i>更多</a>
  </div>
	<div class="com-table com-mta hyx-cla-table fl_l" style="display:block;margin-top:20px !important;">
		<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
			<thead>
				<tr class="sty-bgcolor-b" role="head">
							<th><span class="sp sty-borcolor-b">操作</span></th>
							<th><span class="sp sty-borcolor-b" sort="true" ><label class="lab-d"><span>最近联系时间</span></label></span></th>
							<th><span class="sp sty-borcolor-b">最近联系方式</span></th>
							<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>客户名称</span></label></span></th>
							<th><span class="sp sty-borcolor-b" sort="true" ><label class="lab-d"><span>上次销售进程</span></label></span></th>
							<th><span class="sp sty-borcolor-b" sort="true" ><label class="lab-d"><span>本次销售进程</span></label></span></th>
							<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>联系小记</span></label></span></th>
							<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>员工分组</span></label></span></th>
							<th>所有者</th>
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
</div>
</form>
</body>
</html>
