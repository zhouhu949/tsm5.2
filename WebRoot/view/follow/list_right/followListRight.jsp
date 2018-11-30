<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/taoCust_style_part.css${_v}" />
<c:set var="base_custEdit" value="0"/>
<shiro:hasPermission name="base_custEdit"> <!-- 编辑权限 -->
	<c:set var="base_custEdit" value="1"/>
</shiro:hasPermission>
<form id="rightForm_" method="post">
	<input type="hidden" id="data_isShow" value="${isShow }" />
	<input type="hidden" id="resCustId" value="${resCustId}">
	<input type="hidden" id="followId" value="${followId}">
	<input type="hidden" id="optionId" value="${optionId}">
	<input type="hidden" id="optionName" value="${optionName}">
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<input type="hidden" id="project_path" value="${project_path }">
	<input type="hidden" id="follow_feedbackComment" value="${follow.feedbackComment }">
	<div class="hyx-cfu-card ">
		<div class="tit clearfix"><span title="${custInfo.name }" class="sp">${custInfo.name }</span>
		<c:if test="${custInfo.isMajor eq 1 }">
			<a href="###" class="icon-focus-atten" title="重点关注"></a>
		</c:if>
		<c:if test="${shiroUser.isState eq 1 }"> <!-- 企业资源 -->
			<div class="${not empty resCustId ? 'icon-conte-list':'icon-conte-list img-gray'}"  title="通讯录">
				<c:set var="detailName"/>
				<c:set var="detailTelphone"/>
				<c:set var="detailMail"/>
				<div class="drop" style="display:none;" title="">
					<div class="arrow"><em>◆</em><span>◆</span></div>
						<ul>
							<c:forEach items="${details }" var="detail">
								<!-- 获取默认联系人 -->
								<c:if test="${detail.isDefault eq 1 }">
									<c:set var="detailName" value="${detail.name }"/>
									<c:set var="detailTelphone" value="${detail.telphone }"/>
									<c:set var="detailMail" value="${detail.email }"/>
								</c:if>
								<li><p class="list "><span title="${detail.name }<c:if test="${not empty detail.work }">（${detail.work }）</c:if>">${detail.name }<c:if test="${not empty detail.work }">（${detail.work }）</c:if></span><i style="display:inline-block;height:25px;line-height:20px;float:left;font-style:normal;">：</i>
								<c:choose>
									<c:when test="${shiroUser.serverDay gt 0}">
										<label title="${detail.telphone }" name="detailTelphone">${detail.telphone }</label><c:if test="${not empty detail.telphone }"><a href="javascript:;" class="icon-phone" onclick="followCallPhone('${detail.telphone}','${custInfo.resCustId}','${custInfo.name}','${custInfo.status}','${detail.name }' ,'${custInfo.type}','','${custInfo.isConcat }');"  title="拨打电话"></a></c:if>
										<c:if test="${not empty detail.email }"><a href="javascript:;" onclick="emailBysend('${detail.name }','${detail.email }')" class="icon-email demoBtn_d" title="发送邮件"></a></c:if>
									</c:when>
									<c:otherwise>
										<label title="${detail.telphone }" name="detailTelphone">${detail.telphone }</label><c:if test="${not empty detail.telphone }"><a href="javascript:;" class="icon-phone img-gray"  title="拨打电话"></a></c:if>
										<c:if test="${not empty detail.email }"><a href="javascript:;"  class="icon-email img-gray" title="发送邮件"></a></c:if>
									</c:otherwise>
								</c:choose>
								</p></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</c:if>
			<c:choose>
				<c:when test="${not empty resCustId and shiroUser.serverDay gt 0}">
					<a data-authority="base_custEdit" href="javascript:;" id="editCustBtn" custid="${resCustId }" class="icon-edit demoBtn_c fl_r " title="编辑"></a>
				</c:when>
				<c:otherwise>
					<a ata-authority="base_custEdit" href="javascript:;" class="icon-edit fl_r img-gray" title="编辑"></a>
				</c:otherwise>
			</c:choose>
		</div>
		<c:choose>
			<c:when test="${state eq 1 }">
					<!-- 企业资源 -->
				<p class="list "><span>联系人：</span><label title="${detailName }">${detailName }</label></p>
				<c:choose>
					<c:when test="${shiroUser.serverDay gt 0 }">
						<p class="list clearfix"><span>联系电话：</span><label title="${detailTelphone }" name="detailTelphone">${detailTelphone }</label><c:if test="${not empty detailTelphone }"><a href="javascript:;" onclick="followCallPhone('${detailTelphone}','${custInfo.resCustId}','${custInfo.name}','${custInfo.status}','${detailName}','${custInfo.type}','','${custInfo.isConcat}');" class="icon-phone" title="拨打电话"></a></c:if><c:if test="${not empty detailTelphone }"><a href="javascript:;" class="icon-message demoBtn_b" title="发送短信" phone="${detailTelphone }" name="${detailName }"></a></c:if></p>
						<p class="list clearfix"><span>邮&nbsp;&nbsp;箱：</span><c:if test="${not empty detailMail }"><label title="${detailMail }">${detailMail }</label><a href="javascript:;" class="icon-email demoBtn_d"  onclick="emailBysend('${detailName}','${detailMail }')" title="发送邮件"></a></c:if></p>
					</c:when>
					<c:otherwise>
						<p class="list clearfix"><span>联系电话：</span><label title="${detailTelphone }" name="detailTelphone">${detailTelphone }</label><c:if test="${not empty detailTelphone }"><a href="javascript:;"  class="icon-phone img-gray" title="拨打电话></a></c:if><c:if test="${not empty detailTelphone }"><a href="javascript:;" class="icon-message img-gray" title="发送短信"></a></c:if></p>
						<p class="list clearfix"><span>邮&nbsp;&nbsp;箱：</span><c:if test="${not empty detailMail }"><label title="${detailMail }">${detailMail }</label><a href="javascript:;" class="icon-email img-gray"   title="发送邮件"></a></c:if></p>
					</c:otherwise>
				</c:choose>
				<p class="list clearfix"><span>联系地址：</span><label title="${custInfo.address }">${custInfo.address }</label></p>
			</c:when>
			<c:otherwise>
				<!-- 个人资源 -->
				<p class="list clearfix"><span>性别：</span>
				<label>
					<c:choose>
						<c:when test="${custInfo.sex eq 1 }">男</c:when>
						<c:when test="${custInfo.sex eq 2 }">女</c:when>
					</c:choose>
				</label>
				</p>
				<c:choose>
					<c:when test="${shiroUser.serverDay gt 0 }">
						<p class="list clearfix"><span>联系电话：</span><label title="${custInfo.mobilephone }" name="mobilephone">${custInfo.mobilephone }</label><c:if test="${not empty custInfo.mobilephone}"><a href="javascript:;" onclick="followCallPhone('${custInfo.mobilephone}','${custInfo.resCustId}','${custInfo.name}','${custInfo.status}','${custInfo.name}','${custInfo.type}','${custInfo.company}','${custInfo.isConcat}');"  class="icon-phone" title="拨打电话"></a></c:if><c:if test="${not empty custInfo.mobilephone}"><a href="javascript:;" class="icon-message demoBtn_b" title="发送短信" phone="${custInfo.mobilephone }" name="${custInfo.name }"></a></c:if></p>
						<p class="list clearfix"><span>邮&nbsp;&nbsp;箱：</span><c:if test="${not empty custInfo.email }"><label title="${custInfo.email }">${custInfo.email }</label><a href="javascript:;" onclick="emailBysend('${custInfo.name }','${custInfo.email }')" class="icon-email demoBtn_d" title="发送邮件"></a></c:if></p>
					</c:when>
					<c:otherwise>
						<p class="list clearfix"><span>联系电话：</span><label title="${custInfo.mobilephone }" name="mobilephone">${custInfo.mobilephone }</label><c:if test="${not empty custInfo.mobilephone}"><a href="javascript:;"  class="icon-phone img-gray" title="拨打电话"></a></c:if><c:if test="${not empty custInfo.mobilephone}"><a href="javascript:;" class="icon-message img-gray" title="发送短信" ></a></c:if></p>
						<p class="list clearfix"><span>邮&nbsp;&nbsp;箱：</span><c:if test="${not empty custInfo.email }"><label title="${custInfo.email }">${custInfo.email }</label><a href="javascript:;" class="icon-email img-gray" title="发送邮件"></a></c:if></p>
					</c:otherwise>
				</c:choose>
				<p class="list clearfix"><span>联系地址：</span><label title="${custInfo.companyAddr }">${custInfo.companyAddr }</label></p>
			</c:otherwise>
		</c:choose>
	</div>
    <div class="hyx-tab">
	<div class="hyx-cfu-tab">
		<ul class="tab-list clearfix">
			<li id="follow_" class="select li_first">跟进记录</li>
			<li id="callLog_" >通话记录</li>
			<li id="reView_" class="li_last">点评信息</li>
		</ul>
	</div>
	<c:choose>
		<c:when test="${not empty custFollows }">
				<div class="hyx-wlm-cent-timeline hyx-cfu-timeline" id="follow_show">
					<div class="timeline">
						<div class="timeline_icon_grey_empty"></div>
						<!-- <div class="time_line"></div> -->
							<ul id="timeline_ul">
								<c:forEach items="${custFollows }"  var="follow">
							        <li class="li-load cfu-mt">
						        		<div class="cfu-cirb"></div>
						        		<div class="right">
						        			<c:if test="${showCount eq '1'}">
							        			<c:choose>
								        			<c:when test="${shiroUser.isState eq '0' && !empty follow.company}">
								        				<a href="javascript:;" class="href-more" data-custId="${follow.custId}" data-custName="${follow.company}" style="position:absolute;right:10px;top:-20px;">更多</a>
								        			</c:when>
								        			<c:otherwise>
								        				<a href="javascript:;" class="href-more" data-custId="${follow.custId}" data-custName="${follow.name}" style="position:absolute;right:10px;top:-20px;">更多</a>
								        			</c:otherwise>
							        			</c:choose>
						        			</c:if>
						        			<div class="arrow"><em>◆</em><span>◆</span></div>
						        			<div class="cfu-box">
						        				<div class="cfu-time" style="margin:5px 0 5px 0;">${follow.showLastActionDate}&nbsp;
							        				<c:choose>
							       						<c:when test="${follow.actionType eq 1}"><!-- <span class="phone_dhlx" title="电话联系"></span> -->（电话联系）</c:when>
							       						<c:when test="${follow.actionType eq 2}"><!-- <span class="phone_hklx" title="会客联系"></span> -->（会客联系）</c:when>
							       						<c:when test="${follow.actionType eq 3}"><!-- <span class="customer_khlx" title="客户来电"></span> -->（客户来电）</c:when>
							       						<c:when test="${follow.actionType eq 4}"><!-- <span class="message_dxlx" title="短信联系"></span> -->（短信联系）</c:when>
							       						<c:when test="${follow.actionType eq 5}"><!-- <span class="qq_qqlx" title="QQ联系"></span> -->（QQ联系）</c:when>
							       						<c:when test="${follow.actionType eq 6}"><!-- <span class="mail_yjlx" title="邮件联系"></span> -->（邮件联系）</c:when>
							       					</c:choose>
								        		</div>
						        				<p class="cfu-list" style="text-overflow:clip;height:20px;"><label class="lab" title="${follow.custName }">${shiroUser.isState eq 1 ? '客户联系人' : '客户姓名'}：${shiroUser.isState eq 1 ? follow.custName : follow.name}</label></p><%-- <c:choose>
						        					<c:when test="${follow.effectiveness eq 1 }"><span class="valid_follow" title="有效联系"></span></c:when>
						        					<c:otherwise><span class="novalid_follow" title="无效联系"></span></c:otherwise>
						        				</c:choose> --%>
						        				<p class="cfu-list" >销售联系人：${empty follow.ownerName ? follow.followAcc : follow.ownerName }</p>
						        				<%-- <p class="cfu-list">销售进程：${follow.optionName }</p> --%>
						        				<c:if test="${not empty follow.labelName}"><p class="cfu-list-note" title="${follow.labelName }"><label class="fl_l">行动标签：</label><span class="fl_l ">${follow.labelName}</span></p></c:if>
						        				<c:if test="${not empty follow.urlList }">
						        					<p class="cfu-list" style="height:20px;"><label class="lab" style="width:80px;">通话录音：</label>
							        					 <c:forEach items="${follow.urlList }" var="call">
							        					 <c:choose>
								        					 	<c:when test="${call.recordState eq 1 && !empty call.recordUrl && call.timeLength gt 0 && fn:contains(call.recordUrl,'http:')}">
									        					 		<span class="video_on" title="${call.timeShow}" name="icon-play"  callState="${call.callState}" timeLength="${call.timeLength}" url="${call.recordUrl }" callId="${call.callId}" custName="${call.custName}" callerNum="${call.callerNum }"  calledNum="${call.calledNum}" ></span>
									        					</c:when>
								        					 	<c:when test="${call.recordState eq 1 && call.timeLength gt 0}">
								        					 			<span class="video_on" title="${call.timeShow}" name="icon-play"  callState="${call.callState}" timeLength="${call.timeLength}" url="${playUrl}${call.recordUrl}&d=${call.recordKey}&id=${call.code}&compid=${call.orgId}&calllen=${call.timeLength}" callId="${call.callId}" custName="${call.custName}" callerNum="${call.callerNum }"  calledNum="${call.calledNum}" ></span>
								        					 	</c:when>
							        					 	</c:choose>
							        					 </c:forEach>
						        					</p>
						        				</c:if>
						        				<%-- <p class="cfu-list">下次联系时间：${follow.showNextActionDate}
						        					<c:choose>
						        						<c:when test="${follow.followType eq 1}"><span class="phone_dhlx fixed_position_icon" title="电话联系"></span></c:when>
						        						<c:when test="${follow.followType eq 2}"><span class="phone_hklx fixed_position_icon" title="会客联系"></span></c:when>
						        						<c:when test="${follow.followType eq 3}"><span class="customer_khlx fixed_position_icon" title="客户来电"></span></c:when>
						        						<c:when test="${follow.followType eq 4}"><span class="message_dxlx fixed_position_icon" title="短信联系"></span></c:when>
						        						<c:when test="${follow.followType eq 5}"><span class="qq_qqlx fixed_position_icon" title="QQ联系"></span></c:when>
						        						<c:when test="${follow.followType eq 6}"><span class="mail_yjlx fixed_position_icon" title="邮件联系"></span></c:when>
						        					</c:choose></p> --%>
						        				<p class="cfu-list-note" ><label class="fl_l">联系小记：</label><span class="fl_l contact_note">${follow.feedbackComment.replaceAll("\\n","<br/>") }</span></p>
						        				<!-- <textarea class="fl_l" style="width:170px;" rows="3" readonly>${follow.feedbackComment }</textarea> -->
						        			</div>
						      			</div>
							        </li>
						    	</c:forEach>
							</ul>
						<div class="timeline_icon_grey_empty_end"></div>
					</div>
					<%-- <div class="timeline">
					    <ul>
					    	<c:forEach items="${custFollows }"  var="follow">
					    		<li class="li-load">
					        		<div class="cfu-cir"></div>
					        		<div class="cfu-time">${follow.showLastActionDate}&nbsp;<c:choose>
			       						<c:when test="${follow.actionType eq 1}"><span class="phone_dhlx" title="电话联系"></span></c:when>
			       						<c:when test="${follow.actionType eq 2}"><span class="phone_hklx" title="会客联系"></span></c:when>
			       						<c:when test="${follow.actionType eq 3}"><span class="customer_khlx" title="客户来电"></span></c:when>
			       						<c:when test="${follow.actionType eq 4}"><span class="message_dxlx" title="短信联系"></span></c:when>
			       						<c:when test="${follow.actionType eq 5}"><span class="qq_qqlx" title="QQ联系"></span></c:when>
			       						<c:when test="${follow.actionType eq 6}"><span class="mail_yjlx" title="邮件联系"></span></c:when>
			       					</c:choose>
					        		</div>
					       	   </li>
						        <li class="li-load cfu-mt">
					        		<div class="cfu-cirb"></div>
					        		<div class="right">
					        			<div class="arrow"><em>◆</em><span>◆</span></div>
					        			<div class="cfu-box">
					        				<p class="cfu-list" style="text-overflow:clip;"><label class="lab">${shiroUser.isState eq 1 ? '客户联系人' : '客户姓名'}：${follow.custName }</label><c:choose>
					        					<c:when test="${follow.effectiveness eq 1 }"><span class="valid_follow" title="有效联系"></span></c:when>
					        					<c:otherwise><span class="novalid_follow" title="无效联系"></span></c:otherwise>
					        				</c:choose><i></i></p>
					        				<p class="cfu-list">销售联系人：${follow.ownerName }（${follow.followAcc }）</p>
					        				<p class="cfu-list">销售进程：${follow.optionName }
					        				</p>
					        				<c:if test="${not empty follow.labelName}">
					        					<p class="com-none" title="${follow.labelName}" style="margin-left:12px;">行动标签：${follow.labelName}</p>
					        				</c:if>
					        				<c:if test="${not empty follow.urlList }">
					        					<p class="cfu-list"><label class="lab" style="width:80px">通话录音：</label>
						        					 <c:forEach items="${follow.urlList }" var="call">
						        					 <c:choose>
							        					 	<c:when test="${call.recordState eq 1 && !empty call.recordUrl && call.timeLength gt 0 && fn:contains(call.recordUrl,'http:')}">
								        					 		<span class="video_on" title="${call.timeShow}" name="icon-play"  callState="${call.callState}" timeLength="${call.timeLength}" url="${call.recordUrl }" callId="${call.callId}" custName="${call.custName}" callerNum="${call.callerNum }"  calledNum="${call.calledNum}" ></span>
								        					</c:when>
							        					 	<c:when test="${call.recordState eq 1 && call.timeLength gt 0}">
							        					 			<span class="video_on" title="${call.timeShow}" name="icon-play"  callState="${call.callState}" timeLength="${call.timeLength}" url="${playUrl}${call.recordUrl}&d=${call.recordKey}&id=${call.code}" callId="${call.callId}" custName="${call.custName}" callerNum="${call.callerNum }"  calledNum="${call.calledNum}" ></span>
							        					 	</c:when>
						        					 	</c:choose>
						        					 </c:forEach>
					        					</p>
					        				</c:if>
					        				<p class="cfu-list com-none">下次联系时间：${follow.showNextActionDate}<c:choose>
					        						<c:when test="${follow.followType eq 1}"><span class="phone_dhlx" title="电话联系"></span></c:when>
					        						<c:when test="${follow.followType eq 2}"><span class="phone_hklx" title="会客联系"></span></c:when>
					        						<c:when test="${follow.followType eq 3}"><span class="customer_khlx" title="客户来电"></span></c:when>
					        						<c:when test="${follow.followType eq 4}"><span class="message_dxlx" title="短信联系"></span></c:when>
					        						<c:when test="${follow.followType eq 5}"><span class="qq_qqlx" title="QQ联系"></span></c:when>
					        						<c:when test="${follow.followType eq 6}"><span class="mail_yjlx" title="邮件联系"></span></c:when>
					        					</c:choose></p>
					        				<p class="cfu-list-note com-none" ><label class="">联系小记：</label><span class=" contact_note">${follow.feedbackComment }</span></p>
					        				<!-- <textarea class="" style="width:170px;" rows="3" readonly>${follow.feedbackComment }</textarea> -->
					        			</div>
					      			</div>
						        </li>
					    	</c:forEach>
				  		</ul>
					</div> --%>
				</div>
		</c:when>
		<c:otherwise>
			<div class="hyx-wlm-cent-timeline hyx-cfu-timeline" id="follow_show">
				<!-- 暂无数据开始 -->
				<div class="none-bg">
					<p class="tit_none">暂无跟进记录！</p>
				</div>
				<!-- 暂无数据结束 -->
			</div>
		</c:otherwise>
	</c:choose>
	<div class="hyx-wlm-cent-timeline hyx-cfu-timeline com-none" id="callLog_show" style="height:400px;">
		<!-- 通话记录 -->
	</div>
	<div class="hyx-wlm-cent-timeline hyx-cfu-timeline com-none" id="reView_show" style="height:400px;">
		<!-- 点评信息 -->
	</div>

	</div>
</form>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript">
$(function(){
	$(".href-more").on("click",function(e){
		var _this = $(this);
		var custId = _this.attr("data-custId");
		var custName = _this.attr("data-custName") || '客户卡片';
		window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName);
	});
 $(".contact_note").click(
	function(){
		var $_this = $(this);
		var title = $_this.parent().attr("title");
		var text = $_this.text();
		if(title.length >= 50){
	    	text = title.substr(0,50) + '...';
	    }
		$_this.html('<textarea rows="4" readonly>'+title+'</textarea>');
		$_this.children().focus();
		$_this.children().focusout(
			function(){
				$_this.text(text);
			}
		);
	}
);


    /********** 屏蔽手机或固话中间几位  *********/
    var idReplaceWord = $("#idReplaceWord").val();
	if(idReplaceWord==1){
		$("label[name='detailTelphone']").each(function(idx,obj){
				var phone = $(obj).text();
				if(phone != null && phone != ''){
					replaceWord(phone,$(obj));
					replaceTitleWord(phone,$(obj));
				}
			});

		$("label[name='mobilephone']").each(function(idx,obj){
			var phone = $(obj).text();
			if(phone != null && phone != ''){
				replaceWord(phone,$(obj));
				replaceTitleWord(phone,$(obj));
			}
		});

	}
	/********** 屏蔽手机或固话中间几位  *********/

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

	   /*客户跟进tab*/
    $('.hyx-cfu-tab').find('li').click(function(){
        $(this).addClass('select').siblings('li').removeClass('select');
        var id = $(this).attr("id");
        if(id == "follow_"){
        	$("#callLog_show").hide();
        	$("#reView_show").hide();
        	$("#follow_show").show();
        }else if(id == "callLog_"){ // 通话记录
        	var resCustId = $("#resCustId").val();	 // 资源ID
			var url =  "${ctx}/cust/custFollow/List/queryCallLog.do";
			var data = {'resCustId':resCustId};
			$.post(url,data,function(data){
				$("#follow_show").hide();
	        	$("#reView_show").hide();
				$("#callLog_show").show();
				$("#callLog_show").html(data);
			},'html');

        }else if(id =="reView_"){ // 跟进信息
        	var resCustId = $("#resCustId").val();	 // 资源ID
			var retaId = $("#followId").val();	 // 跟进ID
			var url =  "${ctx}/cust/custFollow/List/queryReview.do";
			var data = {'resCustId':resCustId,'retaId':retaId};
			$.post(url,data,function(data){
				$("#callLog_show").hide();
	        	$("#follow_show").hide();
				$("#reView_show").show();
				$("#reView_show").html(data);
			},'html');
        }
    });

    /*log加载*/
    $('.timeline').each(function(i,item){
        var load_num = 6,i_num = 6,forLiLen = 0,
        load_len = $(item).find('.li-load').length;
        if($(item).parent().hasClass('hyx-cfu-timeline') == true){
            load_num = 6;
            i_num = 4;
        }
        $(item).find('.li-load').css({'display':'none'});
        for(var i=0;i<load_num;i++){
            $(item).find('.li-load').eq(i).fadeIn(500);
        }
        function limitLi(){
            var timer = setTimeout(function(){
                if(i_num >= load_len){
                        clearTimeout(timer);
                }
                if(load_len - i_num < load_num){
                        forLiLen = load_len;
                }else{
                        forLiLen = i_num + load_num;
                }
                if(forLiLen <= load_len){
                        for(var i=i_num;i<forLiLen;i++){
                                $(item).find('.li-load').eq(i).fadeIn(1000);
                        }
                }
                i_num = i_num + load_num;
            },200);
        }

        $('.hyx-wlm-cent-timeline').scroll(function(){
            if($(this)[0].scrollTop + $(this).height() >= $(this)[0].scrollHeight){
                limitLi();
            }
         });

    	/*客户跟进加载*/
      var left_height = $('.hyx-cfu-left').height();
        var card_height = $('.hyx-cfu-card').height();
        var tab_height = $('.hyx-cfu-tab').height();
        var load_height = left_height - card_height - tab_height -65;
        $('.hyx-cfu-timeline').each(function(i,item){
            //$(item).css({'height':load_height + 'px'})
            $(item).find('.right:first').find('.cfu-box').find('i').addClass('i-down');
            $(item).find('.right:first').find('.com-none').slideDown();
            $(item).find('.right').each(function(j,itema){
                $(itema).find('i').click(function(){
                	var right_box = $(this).parents('.right');
                    if(right_box.find('.com-none').css('display') == 'none'){
                        right_box.parent().siblings('.li-load').find('.cfu-box').find('i').removeClass('i-down');
                        right_box.parent().siblings('.li-load').find('.com-none').slideUp();
                        right_box.find('.cfu-box').find('i').addClass('i-down');
                        right_box.find('.com-none').slideDown();
                    }else{
                        right_box.find('.cfu-box').find('i').removeClass('i-down');
                        right_box.find('.com-none').slideUp();
                    }
                });
            });
        });

        /*下拉框部分*/
      $('.icon-conte-list').click(function(){
            $(this).find('.drop').fadeToggle();
        });
    });
    /*联系小记字数限制*/
    $('.cfu-list-note').each(function(){
    	var $_this = $(this).find('.contact_note');
    	var text = $_this.text();
    	if(text.length > 100){
	    	$_this.text(text.substr(0,100) + '...');
	    	$_this.append('<span style="width:20px;height:20px;float:none;cursor:pointer;" class="icon-search-look note_detail" title="查看详情"></span>');
	    	$_this.find(".note_detail").click(function(){
	    		viewDetailDivDialog("note_detail_dialog",text);
		    	$("#note_detail_dialog .i-content").css({
			    	"height":"405px",
			    	"overflow-y":"auto",
			    });
	    	});
	    }
    });

    // 发送短信
	$('.demoBtn_b').click(function(){
		var phone = $(this).attr("phone");
		var name = $(this).attr("name");
		toSmsSendPage(name,phone);
	});

	//编辑
    $("#editCustBtn").live('click',function(){
    	var custId = $(this).attr("custid");
    	var getTimestamp=new Date().getTime();
    	pubDivShowIframe('edit_'+custId,ctx+'/res/cust/toEditRes?custId='+custId+'&v='+getTimestamp,'信息编辑',500,570);
    });

});

// 打电话公共方法
function followCallPhone(phone,resCustId,name,status,detailName,custType,company,isConcat){
	var arrays = new Array();
	arrays.push("\"custId\":\""+resCustId+"\"");
	arrays.push("\"custName\":\""+name+"\"");
	arrays.push("\"custState\":\""+status+"\"");
	arrays.push("\"custType\":\""+custType+"\"");
	if($.trim(detailName)!=""){
		arrays.push("\"define1\":\""+detailName+"\"");
	}
	if($.trim(company)!=""){
		arrays.push("\"define3\":\""+company+"\"");
	}

	arrays.push("\"saleProcessId\":\""+$("#optionId").val()+"\"");
	arrays.push("\"saleProcessName\":\""+$("#optionName").val()+"\"");
	window.top.custCallPhone(phone,arrays,resCustId,isConcat);
}

</script>
