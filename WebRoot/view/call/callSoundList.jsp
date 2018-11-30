<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<title>通信管理-通话录音</title>
	<%@ include file="/common/include.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tree_top_part.css${_v}"/>
	<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
	<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
	<script type="text/javascript" src="${ctx }/static/js/common/page.js${_v}"></script>
	<script type="text/javascript" src="${ctx}/static/js/view/callreccord/callSoundList.js${_v}"></script>
</head>
<body> 
<div class="hyx-ctr" >
	<input type="hidden" id="isSys" value="${shiroUser.issys}">
	<form id="myForm" action="" method="post">
	<input type="hidden" name="groupType"  id="groupType" value="${groupType}" /><!-- 2:客服，1：销售 -->
	<input type="hidden" id="project_path" value="${project_path }">
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<input type="hidden" id="playUrl" value="${playUrl}">
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
	   	   <!-- 销售进程 -->
		   <input type="hidden" name="saleProcessId" id="saleProcessId" value="${saleProcessId }" />
				<c:set var="nowsn" value="销售进程"/>
				<c:forEach var="sopn" items="${options }">
					<c:if test="${sopn.optionlistId eq saleProcessId }">
						<c:set var="nowsn">${sopn.optionName }</c:set>
					</c:if>
				</c:forEach>
				<dl class="select">
					<dt>${nowsn }</dt>
					<dd>
						<ul>
							<li><a href="###" onclick="$('#saleProcessId').val('${sop.optionlistId}');" >销售进程</a></li>
							<c:forEach var="sop" items="${options}">
								<li><a href="###" onclick="$('#saleProcessId').val('${sop.optionlistId}');" title="${sop.optionName }">${sop.optionName }</a></li>
							</c:forEach>
						</ul>
					</dd>
				</dl>
				
				<div class="reso-sub-dep fl_l">
						<input name="accs" id="accs" type="hidden" value="${not empty accs ? accs : ''}">
						<input type="hidden" name="osType" value="${osType}" id="osType" />
						<input class="owner-sour" id="accNames" type="text"  value="${not empty accs ? accs : shiroUser.name}" >							
						<div class="manage-owner-sour" style="height:370px;">
								<div class="shows-radio-boxs"></div>
								<ul class="shows-allorme-boxs">
									<li><input type="radio"  value="1"  name="searchOwnerType" ${osType eq '1' ? 'checked':'' }>查看全部</li>
									<li><input type="radio"  value="2"  name="searchOwnerType" ${osType eq '2' ? 'checked':'' }>查看自己</li>
								</ul>
								<div class="sub-dep-ul" data-type="search-tree">
										<ul id="tt1">
								           
								        </ul>
					    		</div>
							    <div class="sure-cancle clearfix" style="width:120px">
									<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" id="checkedId" href="javascript:;"><label>确定</label></a>
									<a class="com-btnd bomp-btna fl_l" id="clearId"  href="javascript:;"><label>清空</label></a>
								</div>
						</div>
				   </div>
				   
	   		<input type="hidden" name="custState" id="custState" value="${custState}">
			<c:set var="statusName" value="客户状态"/>
			<c:choose>
				<c:when test="${custState eq 3 }"><c:set var="statusName" value="意向客户"/></c:when>
				<c:when test="${custState eq 4 }"><c:set var="statusName" value="公海客户"/></c:when>
				<c:when test="${custState eq 6 }"><c:set var="statusName" value="签约客户"/></c:when>
				<c:when test="${custState eq 7 }"><c:set var="statusName" value="沉默客户"/></c:when>
				<c:when test="${custState eq 8 }"><c:set var="statusName" value="流失客户"/></c:when>
				<c:when test="${custState eq 9 }"><c:set var="statusName" value="资源"/></c:when>
				<c:when test="${custState eq 10 }"><c:set var="statusName" value="访客"/></c:when>
			</c:choose>
		   <dl class="select">
				<dt>${statusName }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="$('#custState').val('');">客户状态</a></li>
						<li><a href="###" onclick="$('#custState').val('3');">意向客户</a></li>
						<li><a href="###" onclick="$('#custState').val('6');">签约客户</a></li>
						<li><a href="###" onclick="$('#custState').val('7');">沉默客户</a></li>
						<li><a href="###" onclick="$('#custState').val('8');">流失客户</a></li>
						<li><a href="###" onclick="$('#custState').val('4');">公海客户</a></li>
						<li><a href="###" onclick="$('#custState').val('9');">资源</a></li>
						<li><a href="###" onclick="$('#custState').val('10');">访客</a></li>
					</ul>
				</dd>
		   </dl>
		  
		   	<!-- 通话日期  -->
				<input type="hidden" name="startDate" id="startDate" value="${item.startDate }" />
			   <input type="hidden" name="endDate" id="endDate" value="${item.endDate }"/>
			   <input type="hidden" name="dDateType" id="dDateType" value="${dDateType }" />
			   <c:set var="dDateName" value="通话日期" />
			   <c:choose>
			   		<c:when test="${dDateType eq 1 }"><c:set var="dDateName" value="当天" /></c:when>
			   		<c:when test="${dDateType eq 2 }"><c:set var="dDateName" value="本周" /></c:when>
			   		<c:when test="${dDateType eq 3 }"><c:set var="dDateName" value="本月" /></c:when>
			   		<%-- <c:when test="${dDateType eq 4 }"><c:set var="dDateName" value="半年" /></c:when> --%>
			   		<c:when test="${dDateType eq 5 }">
			   				<fmt:parseDate var="nsd" value="${item.startDate }" pattern="yyyy-MM-dd HH:mm:ss" />
						<fmt:parseDate var="ned" value="${item.endDate }" pattern="yyyy-MM-dd HH:mm:ss" />
			   			<c:set var="dDateName">
			   				<fmt:formatDate value="${nsd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ned }" pattern="yy.MM.dd"/>
			   			</c:set>
			   		</c:when>
			   </c:choose>
		    <dl class="select">
				<dt>${dDateName }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="setNdate(0)">通话日期</a></li>
						<li><a href="###" onclick="setNdate(1)">当天</a></li>
						<li><a href="###" onclick="setNdate(2)">本周</a></li>
						<li><a href="###" onclick="setNdate(3)">本月</a></li>
						<!-- <li><a href="###" onclick="setNdate(4)">半年</a></li> -->
						<li><a href="###" onclick="setNdate(5)" class="diy date-range" id="d1">自定义</a></li>
					</ul>
				</dd>
			</dl>
			
		   <p class='tag fl_l' style="position:relative;width:130px;padding:5px 0px 5px 2px;"><label class="fl_l">通话时长：</label>
				<input class="fl_l" type="text" id="timeLength" name="timeLengthBegin" value="${item.timeLength }"  onblur="this.value=this.value.replace(/\D/g, '');" style="width:24px;margin:0 3px;" placeholder="秒">
				<span class="fl_l">-</span>
				<input class="fl_l" type="text" id="timeLengthEnd" name="timeLengthEnd" value="${item.timeLengthEnd }"  onblur="this.value=this.value.replace(/\D/g, '');" style="width:24px;margin:0 3px;" placeholder="秒">
			</p>
			
			
			
			<div class="list-hid fl_l" style="width:98%;">		
				 <!-- 呼叫类型 -->
		   <input type="hidden" name="callState_"  id="callState"  value="${callState}">
		   <c:set var="callStateName" value="呼叫类型"/>
			<c:choose>
				<c:when test="${callState eq 1  }"><c:set var="callStateName" value="已接来电"/></c:when>
				<c:when test="${callState eq 2 }"><c:set var="callStateName" value="已接去电"/></c:when>					
			</c:choose>
		   <dl class="select">
				<dt>${callStateName }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="setCallState('')">呼叫类型</a></li>
						<li><a href="###" onclick="setCallState('1')">已接来电</a></li>
						<li><a href="###" onclick="setCallState('2')">已接去电</a></li>				
					</ul>
				</dd>
		   </dl>
			</div>
		</div>
	</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询"  id="query"/>
		<a href="###" class="more"><i></i>更多</a>
   </div>
    <div class="com-btnlist hyx-cm-btnlist play-allsound-box">
	       <a href="###" id="play-all-soundlist" class="com-btna reso-manag-f fl_l" title="播放当前页录音"><label>播放全部录音</label></a>
	</div>
	<div class="com-table com-mta hyx-cla-table" style="margin:0 auto;margin-top:20px !important;">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">操作</span></th> 
					<th><span class="sp sty-borcolor-b">客户状态</span></th>
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>主叫号码</span></label></span></th>
					<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>被叫号码</span></label></span></th>
					<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>销售进程</span></label></span></th>
					<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>通话时间</span></label></span></th>
					<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>通话时长</span></label></span></th>
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>呼叫类型</span></label></span></th>
					<th>联系人</th>
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