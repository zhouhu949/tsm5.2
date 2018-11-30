<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>弹屏-客户跟进列表</title>
<link href="${ctx}/static/css/dialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js"></script>
<%-- <script type="text/javascript" src="${ctx}/static/js/view/res/custcommon.js"></script> --%>
<script type="text/javascript">
$(function(){
	//播放按钮点击的时候
	$("[name='icon-play']").click(function(){
		var v = $(this).attr("v");
		var callState = $(this).attr("callState");//呼叫类型
		// 获取当前行的客户姓名
		var callName = $(this).attr("custName");
		var callNum = "";
		// 如果单位姓名为空就获取号码
		if(callState == "1"){ //1:呼入，2:呼出
				callNum = $(this).attr("callerNum");// 被叫号码
		}else{
				callNum = $(this).attr("calledNum");// 主叫号码
		}				
		var httpUrl = $("#project_path").val();
		var plength = $(this).attr("timeLength");
		var url = $(this).attr("url");
		var callId = $(this).attr("callId");
		//window.location.href = httpUrl+"/call/callPlay.do?timeElngth="+plength+"&recordUrl="+encodeURIComponent(encodeURIComponent(url,"utf-8"))+"&callId="+callId+"&callName="+encodeURI(encodeURI(callName))+"&callNum="+callNum;
		recordCallPlay(httpUrl,plength,url,callId,callName,callNum);
	});
});
</script>
<script type="text/javascript">
window.onload=function(){
	var height = $(".bomp_ical").height();
	window.parent.$("#iframepage").css({"height":height+"px"});
};
</script>
</head>
<body> 
<form action="${ctx}/res/incall/findFollowList" method="post">
<input type='hidden' name='custId' id='custId' value='${custId }'>
<input type="hidden" id="project_path" value="${project_path }">
<div class="bomp_ical">
	<div class="com-table com-mta">
		<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr role="head"  class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b"  sort="true">联系时间</span></th>
					<th><span class="sp sty-borcolor-b">联系人</span></th> 
					<th><span class="sp sty-borcolor-b">联系方式</span></th>
					<th><span class="sp sty-borcolor-b">有效跟进</span></th>
					<th><span class="sp sty-borcolor-b"  sort="true">销售进程</span></th>
					<th><span class="sp sty-borcolor-b">销售人员</span></th>
					<th><span class="sp sty-borcolor-b">行动标签</span></th>
					<th><span class="sp sty-borcolor-b">通话录音</span></th>
					<th>联系小记</th>
				</tr>
			</thead>
			<tbody>     
			    <c:choose>
			        <c:when test="${! empty custFollowList }">
						  <c:forEach items="${custFollowList}" var="custFollow" varStatus="vs">
								<tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">	
								<td><div class="overflow_hidden w120" title="<fmt:formatDate value='${custFollow.actionDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"><fmt:formatDate value='${custFollow.actionDate}' pattern='yyyy-MM-dd HH:mm:ss'/></div></td>
								<td><div class="overflow_hidden w70" title="${custFollow.custName }">${custFollow.custName }</div></td>
								<td><div class="overflow_hidden w70" title=''><c:choose>
				       						<c:when test="${custFollow.actionType eq 1}">电话联系</c:when>
				       						<c:when test="${custFollow.actionType eq 2}">会客联系</c:when>
				       						<c:when test="${custFollow.actionType eq 3}">客户来电</c:when>
				       						<c:when test="${custFollow.actionType eq 4}">短信联系</c:when>
				       						<c:when test="${custFollow.actionType eq 6}">邮件联系</c:when>
				       						<c:when test="${custFollow.actionType eq 5}">QQ联系</c:when>
				       						<c:when test="${custFollow.actionType eq 7}">微信联系</c:when>
			        					</c:choose></div></td>
								<td><div class="overflow_hidden w70" title="<c:choose><c:when test="${custFollow.effectiveness eq 1}">是</c:when><c:otherwise>否</c:otherwise></c:choose>	"><c:choose>
			        						<c:when test="${custFollow.effectiveness eq 1}">是</c:when>
			        						<c:otherwise>否</c:otherwise>
			        					</c:choose></div></td>
								<td><div class="overflow_hidden w100" title="${custFollow.optionName}">${custFollow.optionName}</div></td>
								<td><div class="overflow_hidden w70" title="${custFollow.followAcc }">${custFollow.followAcc }</div></td>
								<td><div class="overflow_hidden w70" title="${custFollow.labelName }">${custFollow.labelName }</div></td>
								<td style="width:60px;"><div class="overflow_hidden w60 link">
								<c:if test="${not empty custFollow.urlList }">
			        					 <c:forEach items="${custFollow.urlList }" var="call">
												<c:choose>
													<c:when test="${call.recordState eq 1 && !empty call.recordUrl && call.timeLength gt 0 && fn:contains(call.recordUrl,'http:')}">
							        							<a href="###" class="icon-play" name="icon-play"  callState="${call.callState}" timeLength="${call.timeLength}" url="${call.recordUrl }" callId="${call.callId}" custName="${call.custName}" callerNum="${call.callerNum }"  calledNum="${call.calledNum}" title="录音播放"></a>
							        				</c:when>
						        					 <c:when test="${call.recordState eq 1 && call.timeLength gt 0}">
						        					 			<a href="###" class="icon-play" name="icon-play"  callState="${call.callState}" timeLength="${call.timeLength}" url="${playUrl}${call.recordUrl}&d=${call.recordKey}&id=${call.code}&compid=${call.orgId}&calllen=${call.timeLength}" callId="${call.id}" custName="${call.custName}" callerNum="${call.callerNum }"  calledNum="${call.calledNum}" title="录音播放"></a>
						        					 </c:when>	
													<c:otherwise>
														<a href="###" class="icon-play img-gray" title="录音播放"></a>
													</c:otherwise>
												</c:choose>
			        					 </c:forEach>        					
					        	</c:if></div></td>

					        									        	
								<td><div class="overflow_hidden w100" title="${custFollow.feedbackComment }">${custFollow.feedbackComment }</div></td>	
								</tr>									             
			              </c:forEach>
			        </c:when>
						<c:otherwise>
							<tr>
								<td colspan="9">
									<center>当前列表无数据！</center>
								</td>
							</tr>									
						</c:otherwise>
			    </c:choose>         
			</tbody>
		</table>
	</div>
	<div class="com-bot">
		<div class="com-page fl_r">
			<div class="page">${custFollowDto.page.pageStr}</div>
			<div class="clr_both"></div>
		</div>
	</div>
</div>
</form>
</body>
</html>