<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil"%>
<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/taoCust_style_part.css${_v}" />
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<c:if test="${empty callList }">
		<!-- 暂无数据开始 -->
		<div class="none-bg">
			<p class="tit_none">暂无通话记录！</p>
		</div>
		<!-- 暂无数据结束 -->
</c:if>

<div class="timeline" >
<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
<input type="hidden" id="project_path" value="${project_path }">
	<c:choose>
			<c:when test="${not empty callList }">
					<div class="timeline_icon_grey_empty"></div>
					<!-- <div class="time_line"></div> -->
					<ul id="timeline_ul">
				<c:forEach items="${callList }" var="call">
					<li class="li-load">
						<!-- <div class="cfu-cir"></div> -->
						</li>
					<li class="li-load cfu-mt">
						<div class="cfu-cirb"></div>
						<div class="right">
						<div class="cfu-time" style="margin:5px 0 5px 10px">
							<fmt:formatDate value="${call.startTime}"
								pattern='yyyy-MM-dd HH:mm:ss' />
							&nbsp;
						</div>
							<div class="arrow">
								<em>◆</em><span>◆</span>
							</div>
							<div class="cfu-box">
							  <c:if test="${not empty call.define1}">
									<p class="cfu-list" style="text-overflow:clip;">
										<label class="lab" title="${call.define1}">客户联系人：${call.define1}</label>
									</p>
								</c:if>	
								<p class="cfu-list" >
									<c:choose>
										<c:when test="${call.callState eq 1 }">
											<em name="telphone_" style="font-style:normal;">${call.callerNum}</em>（主叫）
										</c:when>
										<c:otherwise>
											<em name="telphone_" style="font-style:normal;">${call.calledNum}</em>（被叫）
										</c:otherwise>
									</c:choose>
								</p>															
								<p class="cfu-list">销售联系人：${call.inputName}</p>
								<p class="cfu-list">
								<c:choose>
										<c:when test="${call.callState eq 2 }">
											<em name="telphone_" style="font-style:normal;">${call.callerNum}</em>（主叫）
										</c:when>
										<c:otherwise>
											<em name="telphone_" style="font-style:normal;">${call.calledNum}</em>（被叫）
										</c:otherwise>
									</c:choose>
								</p>
								<p class="cfu-list">
									<c:if test="${call.callState eq 1 }">
										<c:choose>
											<c:when test="${call.timeLength gt 0}">
													呼叫类型：已接来电
											</c:when>
											<c:otherwise>
													呼叫类型：未接来电
											</c:otherwise>
										</c:choose>		
									</c:if>
									<c:if test="${call.callState eq 2}">
										<c:choose>
											<c:when test="${call.timeLength gt 0}">
													呼叫类型：已接去电
											</c:when>
											<c:otherwise>
													呼叫类型：未接去电
											</c:otherwise>
										</c:choose>	
									</c:if>
								</p>
								<p class="cfu-list"><span style="display:inline-block;float:left;height:20px;line-height:20px;margin-right:5px;">通话时长：${call.timeShow }</span>
									<c:choose>
										<c:when test="${call.recordState eq 1 && !empty call.recordUrl && call.timeLength gt 0 && fn:contains(call.recordUrl,'http:')}">
			        					 		<span class="video_on" title="录音播放" name="icon-play"  callState="${call.callState}" timeLength="${call.timeLength}" url="${call.recordUrl }" callId="${call.id}" custName="${call.custName}" callerNum="${call.callerNum }"  calledNum="${call.calledNum}" ></span>		 					 	
			        					</c:when>
		        					 	<c:when test="${call.recordState eq 1 && call.timeLength gt 0}">
		        					 			<span class="video_on" name="icon-play"  callState="${call.callState}" timeLength="${call.timeLength}" url="${playUrl}${call.recordUrl}&d=${call.recordKey}&id=${call.code}&compid=${call.orgId}&calllen=${call.timeLength}" callId="${call.id}" custName="${call.custName}" callerNum="${call.callerNum }"  calledNum="${call.calledNum}" title="录音播放" ></span>		 					 	
		        					 	</c:when>		
	        					 	</c:choose>				
								</p>
							</div>
						</div></li>
				</c:forEach>
					</ul>
					<div class="timeline_icon_grey_empty_end"></div>
					</c:when>
					</c:choose>
				</div>

<%-- <div class="timeline">
<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
<input type="hidden" id="project_path" value="${project_path }">
	<ul>
		<c:choose>
			<c:when test="${not empty callList }">
				<c:forEach items="${callList }" var="call">
					<li class="li-load">
						<div class="cfu-cir"></div>
						<div class="cfu-time">
							<fmt:formatDate value="${call.startTime}"
								pattern='yyyy-MM-dd HH:mm:ss' />
							&nbsp;
						</div></li>
					<li class="li-load cfu-mt">
						<div class="cfu-cirb"></div>
						<div class="right">
							<div class="arrow">
								<em>◆</em><span>◆</span>
							</div>
							<div class="cfu-box">
							  <c:if test="${not empty call.define1}">
									<p class="cfu-list" style="text-overflow:clip;">
										<label class="lab">客户联系人1：${call.define1}</label>
									</p>
								</c:if>	
								<p class="cfu-list" >
									<c:choose>
										<c:when test="${call.callState eq 1 }">
											<em name="telphone_" style="font-style:normal;">${call.callerNum}</em>（主叫）
										</c:when>
										<c:otherwise>
											<em name="telphone_" style="font-style:normal;">${call.calledNum}</em>（被叫）
										</c:otherwise>
									</c:choose>
								</p>															
								<p class="cfu-list">销售联系人：${call.inputName}</p>
								<p class="cfu-list">
								<c:choose>
										<c:when test="${call.callState eq 2 }">
											<em name="telphone_" style="font-style:normal;">${call.callerNum}</em>（主叫）
										</c:when>
										<c:otherwise>
											<em name="telphone_" style="font-style:normal;">${call.calledNum}</em>（被叫）
										</c:otherwise>
									</c:choose>
								</p>
								<p class="cfu-list">
									<c:if test="${call.callState eq 1 }">
										<c:choose>
											<c:when test="${call.timeLength gt 0}">
													呼叫类型：已接来电
											</c:when>
											<c:otherwise>
													呼叫类型：未接来电
											</c:otherwise>
										</c:choose>		
									</c:if>
									<c:if test="${call.callState eq 2}">
										<c:choose>
											<c:when test="${call.timeLength gt 0}">
													呼叫类型：已接去电
											</c:when>
											<c:otherwise>
													呼叫类型：未接去电
											</c:otherwise>
										</c:choose>	
									</c:if>
								</p>
								<p class="cfu-list"><span style="display:inline-block;float:left;height:20px;line-height:20px;margin-right:5px;">通话时长：${call.timeShow }</span>
									<c:choose>
										<c:when test="${call.recordState eq 1 && !empty call.recordUrl && call.timeLength gt 0 && fn:contains(call.recordUrl,'http:')}">
			        					 		<span class="video_on" title="录音播放" name="icon-play"  callState="${call.callState}" timeLength="${call.timeLength}" url="${call.recordUrl }" callId="${call.id}" custName="${call.custName}" callerNum="${call.callerNum }"  calledNum="${call.calledNum}" ></span>		 					 	
			        					</c:when>
		        					 	<c:when test="${call.recordState eq 1 && call.timeLength gt 0}">
		        					 			<span class="video_on" name="icon-play"  callState="${call.callState}" timeLength="${call.timeLength}" url="${playUrl}${call.recordUrl}&d=${call.recordKey}&id=${call.code}" callId="${call.id}" custName="${call.custName}" callerNum="${call.callerNum }"  calledNum="${call.calledNum}" title="录音播放" ></span>		 					 	
		        					 	</c:when>		
	        					 	</c:choose>				
								</p>
							</div>
						</div></li>
				</c:forEach>
			</c:when>
		</c:choose>
	</ul>
</div> --%>
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
				callNum = $(this).attr("callerNum");// 主叫号码
		}else{
				callNum = $(this).attr("calledNum");// 被叫号码
		}				
		var httpUrl = $("#project_path").val();
		var plength = $(this).attr("timeLength");
		var url = $(this).attr("url");
		var callId = $(this).attr("callId");
		//window.location.href = httpUrl+"/call/callPlay.do?timeElngth="+plength+"&recordUrl="+encodeURIComponent(encodeURIComponent(url,"utf-8"))+"&callId="+callId+"&callName="+encodeURI(encodeURI(callName))+"&callNum="+callNum;
		recordCallPlay(httpUrl,plength,url,callId,callName,callNum);
	});
	
	   /********** 屏蔽手机或固话中间几位  *********/
    var idReplaceWord = $("#idReplaceWord").val();	
	if(idReplaceWord==1){
		$("em[name='telphone_']").each(function(idx,obj){
				var phone = $(obj).text();
				if(phone != null && phone != ''){
					replaceWord(phone,$(obj));
				}
		});
	}
	/********** 屏蔽手机或固话中间几位  *********/

	
});
</script>