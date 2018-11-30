<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/taoCust_style_part.css${_v}" />
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/plan/dailyplan/right/planRightFollow.js${_v}"></script>
<c:set var="base_custEdit" value="0"/>
<shiro:hasPermission name="base_custEdit"> <!-- 编辑权限 -->
	<c:set var="base_custEdit" value="1"/>
</shiro:hasPermission>
<input type="hidden" id="custId" value="${resCustId }">
<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<div class="hyx-cfu-tab">
		<ul class="tab-list clearfix">
			<li id="cust_" class="select li_first">客户资料</li>
			<li id="follow_"  class="li_last">跟进记录</li>
		</ul>
	</div>
	<div class="cust_information" id="cust_infor" >
		<div class="hyx-cfu-card fl_l">
			<c:choose>
				<c:when test="${state eq 1 }">
					<!-- 企业资源 -->
					<c:forEach items="${details }" var="detail">
						<!-- 获取默认联系人 -->
						<c:choose>
						<c:when test="${detail.isDefault eq 1 }">
						    <c:set var="isDefaultFlagMain" value="1"/>
							<input type="hidden" id="url" value="${ctx }/cust/custFollow/custFollowEnterInfo">
							<div class="new-daily-plan-right-item" title="客户名称：${custInfo.name }">
								<label class="new-daily-plan-right-item-left">客户名称：</label>
								${custInfo.name }
							</div>
							<div class="new-daily-plan-right-item" title="联系人姓名：${detail.name }">
								<label class="new-daily-plan-right-item-left">联系人姓名：</label>
								${detail.name }
							</div>
							<div class="new-daily-plan-right-item" title="常用电话：${detail.telphone }">
								<label class="new-daily-plan-right-item-left">常用电话：</label>
								<label name = "mobilephone">${detail.telphone }</label>
								<c:if test="${!empty detail.telphone }">
									<a href="javascript:;"  class="icon-phone" title="拨打电话" style="float:none;" onclick="followCallPhone('${detail.telphone}','${custInfo.resCustId}','${custInfo.name}','${custInfo.status}','${detail.name }' ,'${custInfo.type}','','${custInfo.isConcat }','${custInfo.lastOptionId} ','${custInfo.lastOptionName }');" ></a>
								</c:if>
							</div>
							<div class="new-daily-plan-right-item" title="备用电话：${detail.telphonebak }">
								<label class="new-daily-plan-right-item-left">备用电话：</label>
								<label name = "mobilephone">${detail.telphonebak }</label>
								<c:if test="${!empty detail.telphonebak }">
									<a href="javascript:;"  class="icon-phone" title="拨打电话" style="float:none;" onclick="followCallPhone('${detail.telphonebak}','${custInfo.resCustId}','${custInfo.name}','${custInfo.status}','${detail.name }' ,'${custInfo.type}','','${custInfo.isConcat }','${custInfo.lastOptionId} ','${custInfo.lastOptionName }');" ></a>
								</c:if>
							</div>
						</c:when>
						<c:otherwise>
						  <c:set var="isDefaultFlag" value="0"/>
						  <c:set var="custName" value="${custInfo.name }"/>
						</c:otherwise>
						</c:choose>
					</c:forEach>
						<c:if test = "${ isDefaultFlag eq 0 && !isDefaultFlagMain && isDefaultFlagMain ne 1 }">
					      <input type="hidden" id="url" value="${ctx }/cust/custFollow/custFollowEnterInfo">
                            <div class="new-daily-plan-right-item" title="客户名称：${custInfo.name }">
                                <label class="new-daily-plan-right-item-left">客户名称：</label>
                                ${custName }
                            </div>
                            <div class="new-daily-plan-right-item" title="联系人姓名：${detail.name }">
                                <label class="new-daily-plan-right-item-left">联系人姓名：</label>
                            </div>
                            <div class="new-daily-plan-right-item" title="常用电话：${detail.telphone }">
                                <label class="new-daily-plan-right-item-left">常用电话：</label>
                            </div>
                            <div class="new-daily-plan-right-item" title="备用电话：${detail.telphonebak }">
                                <label class="new-daily-plan-right-item-left">备用电话：</label>
                            </div>
						</c:if>
						<div class="append-div"></div>
				</c:when>
				<c:otherwise>
					<input type="hidden" id="url" value="${ctx }/cust/custFollow/custFollowPersonInfo">
					<!-- 个人资源 -->
					<div class="new-daily-plan-right-item" title="客户姓名：${custInfo.name }">
						<label class="new-daily-plan-right-item-left">客户姓名：</label>
						${custInfo.name }
					</div>
					<%-- <div class="new-daily-plan-right-item" title="单位名称：${detail.company }">
						<label class="new-daily-plan-right-item-left">单位名称：</label>
						${custInfo.company }
					</div> --%>
					<div class="new-daily-plan-right-item" title="常用电话：${custInfo.mobilephone }">
						<label class="new-daily-plan-right-item-left">常用电话：</label>
						${custInfo.mobilephone }
						<c:if test="${!empty custInfo.mobilephone }">
							<a href="javascript:;"  class="icon-phone" title="拨打电话" style="float:none;"   onclick="followCallPhone('${custInfo.mobilephone}','${custInfo.resCustId}','${custInfo.name}','${custInfo.status}','${detailName}','${custInfo.type}','${custInfo.company}','${custInfo.isConcat}','${custInfo.lastOptionId} ','${custInfo.lastOptionName }');"></a>
						</c:if>
					</div>
					<div class="new-daily-plan-right-item" title="备用电话：${custInfo.telphone }">
						<label class="new-daily-plan-right-item-left">备用电话：</label>
						${custInfo.telphone }
						<c:if test="${!empty custInfo.telphone }">
							<a href="javascript:;"  class="icon-phone" title="拨打电话" style="float:none;"   onclick="followCallPhone('${custInfo.telphone}','${custInfo.resCustId}','${custInfo.name}','${custInfo.status}','${detailName}','${custInfo.type}','${custInfo.company}','${custInfo.isConcat}','${custInfo.lastOptionId} ','${custInfo.lastOptionName }');"></a>
						</c:if>
					</div>
					<div class="append-div"></div>
				</c:otherwise>
			</c:choose>
		</div>
		<c:choose>
			<c:when test="${not empty resCustId and shiroUser.serverDay gt 0}">
				<div data-authority="base_custEdit"  class="new-daily-plan-rightbottom-editbtn" custid="${resCustId }"  id="editCustBtn" >编辑</div>
			</c:when>
			<c:otherwise>
				<div data-authority="base_custEdit" class="new-daily-plan-rightbottom-editbtn">编辑</div>
			</c:otherwise>
		</c:choose>
	</div>
	<c:choose>
		<c:when test="${not empty actionDtos }">
			<div class="hyx-wlm-cent-timeline hyx-cfu-timeline"  id="follow_show" style="display:none;">
				<div class="timeline-outerbox">
					<div class="timeline">
						<div class="timeline_icon_grey_empty"></div>
						<!-- <div class="time_line"></div> -->
						<ul id="timeline_ul">
							    	<c:forEach items="${actionDtos }"  var="follow">
								        <c:choose>
								        	<c:when test="${empty follow.custFollowId }">
								        		<li class="li-load cfu-mt">
									       	   		<div class="cfu-cirb"></div>
									        		<div class="right">
									        		<div class="cfu-time"><fmt:formatDate value="${follow.inputDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
									        			<div class="arrow"><em>◆</em><span>◆</span></div>
									        			<div class="cfu-box">
									        				<c:if test="${custInfo.state eq 1 && not empty follow.mainLinkman}">
									        					<p class="cfu-list" style="text-overflow:clip;"><label class="lab" title="${follow.mainLinkman }">客户联系人：${follow.mainLinkman }</label></p>
									        				</c:if>
									        				<p class="cfu-list">销售联系人：${empty follow.inputName ? follow.inputAcc : follow.inputName }</p>
									        				<%-- <c:if test="${not empty follow.nextConcatTime}">
									        					<p class="cfu-list">下次联系时间：<fmt:formatDate value="${follow.nextConcatTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
									        				</c:if> --%>
									        				<p class="cfu-list-note" ><label class="fl_l">资源备注：</label><span class="fl_l contact_note">${follow.remark }</span></p>
									        			</div>
									      			</div>
									       	   </li>
								        	</c:when>
								        	<c:otherwise>
								        		<li class="li-load cfu-mt">
									        		<div class="cfu-cirb"></div>
									        		<div class="right">
									        		<div class="cfu-time"><fmt:formatDate value="${follow.inputDate }" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;<c:choose>
							       						<c:when test="${follow.actionType eq 1}"><!-- <span class="phone_dhlx" title="电话联系"></span> -->（电话联系）</c:when>
							       						<c:when test="${follow.actionType eq 2}"><!-- <span class="phone_hklx" title="会客联系"></span> -->（会客联系）</c:when>
							       						<c:when test="${follow.actionType eq 3}"><!-- <span class="customer_khlx" title="客户来电"></span> -->（客户来电）</c:when>
							       						<c:when test="${follow.actionType eq 4}"><!-- <span class="message_dxlx" title="短信联系"></span> -->（短信联系）</c:when>
							       						<c:when test="${follow.actionType eq 5}"><!-- <span class="qq_qqlx" title="QQ联系"></span> -->（QQ联系）</c:when>
							       						<c:when test="${follow.actionType eq 6}"><!-- <span class="mail_yjlx" title="邮件联系"></span> -->（邮件联系）</c:when>
							       					</c:choose>
									        		</div>
									        			<div class="arrow"><em>◆</em><span>◆</span></div>
									        			<div class="cfu-box">
									        				<c:if test="${custInfo.state eq 1 && not empty follow.mainLinkman}">
										        				<p class="cfu-list" style="text-overflow:clip;"><label class="lab" title="${follow.mainLinkman }">客户联系人：${follow.mainLinkman }</label>
											        				<%-- <c:choose>
											        					<c:when test="${follow.effectiveness eq 1 }"><span class="valid_follow" title="有效联系"></span></c:when>
											        					<c:otherwise><span class="novalid_follow" title="无效联系"></span></c:otherwise>
											        				</c:choose> --%>
										        				</p>
									        				</c:if>
									        				<p class="cfu-list">销售联系人：${empty follow.inputName ? follow.inputAcc : follow.inputName }
									        					<%-- <c:if test="${custInfo.state ne 1 || empty follow.mainLinkman}">
									        						<c:choose>
											        					<c:when test="${follow.effectiveness eq 1 }"><span class="valid_follow" title="有效联系"></span></c:when>
											        					<c:otherwise><span class="novalid_follow" title="无效联系"></span></c:otherwise>
											        				</c:choose>
									        					</c:if> --%>
									        				</p>
									        				<%-- <p class="cfu-list" title="销售进程：${follow.saleProcessName }">销售进程：${follow.saleProcessName }</p> --%>
									        				<c:if test="${not empty follow.labels}">
									        					<p class="cfu-list-note" ><label class="fl_l">行动标签：</label><span class="fl_l contact_note">${follow.labels}</span></p>
									        				</c:if>
									        				<c:if test="${not empty follow.urlList }">
									        					<p class="cfu-list"><label class="lab" style="width:80px">通话录音：</label>
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
	<%-- 								        				<c:if test="${!empty follow.nextConcatTime }">					        				 --%>
	<%-- 								        				<p class="cfu-list">下次联系时间：<fmt:formatDate value="${follow.nextConcatTime}" pattern="yyyy-MM-dd HH:mm:ss" /><c:choose>
									        						<c:when test="${follow.followType eq 1}"><span class="phone_dhlx fixed_position_icon " title="电话联系"></span></c:when>
									        						<c:when test="${follow.followType eq 2}"><span class="phone_hklx fixed_position_icon " title="会客联系"></span></c:when>
									        						<c:when test="${follow.followType eq 3}"><span class="customer_khlx fixed_position_icon " title="客户来电"></span></c:when>
									        						<c:when test="${follow.followType eq 4}"><span class="message_dxlx fixed_position_icon " title="短信联系"></span></c:when>
									        						<c:when test="${follow.followType eq 5}"><span class="qq_qqlx fixed_position_icon " title="QQ联系"></span></c:when>
									        						<c:when test="${follow.followType eq 6}"><span class="mail_yjlx fixed_position_icon " title="邮件联系"></span></c:when>
									        					</c:choose></p> --%>
	<%-- 								        				</c:if> --%>
									        				<p class="cfu-list-note" ><label class="fl_l">联系小记：</label><span class="fl_l contact_note">${follow.remark }</span></p>
									        			</div>
									      			</div>
										        </li>
								        	</c:otherwise>
								        </c:choose>
							    	</c:forEach>
						  		</ul>
						<div class="timeline_icon_grey_empty_end"></div>
					</div>
				</div>
				<div class="new-daily-plan-rightbottom-editbtn new-daily-plan-rightbottom-editbtn-follow" data-custid= "${resCustId }">新增跟进</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="hyx-wlm-cent-timeline hyx-cfu-timeline" id="follow_show" style="display:none;">
				<!-- 暂无联系记录开始 -->
				<div class="none-bg">
					<p class="tit_none">暂无联系记录！</p>
				</div>
				<!-- 暂无联系记录结束 -->
				<div class="new-daily-plan-rightbottom-editbtn new-daily-plan-rightbottom-editbtn-follow" data-custid= "${resCustId }">新增跟进</div>
			</div>
		</c:otherwise>
	</c:choose>
