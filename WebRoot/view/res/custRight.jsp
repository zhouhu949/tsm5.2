<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/taoCust_style_part.css${_v}" />
<!DOCTYPE>
<html>
<head>
<style type="text/css">
		.hyx-cfu-timeline{height:400px;}
		/* .cfu-time{margin:5px 0 5px 10px;} */
	</style>
</head>
<body>
<div id="rightForm_" >
	<input type="hidden" id="project_path" value="${project_path }">
	<div class="hyx-cfu-card fl_l">
		<div class="tit fl_l"><span title="${custInfo.name }" class="sp">${custInfo.name }</span>
			<c:if test="${custInfo.isMajor eq 1 }">
				<a href="###" class="icon-focus-atten" title="重点关注"></a>
			</c:if>
			<c:if test="${custInfo.state eq 1 }">
				<c:set var="lxr" value=""></c:set>
				<c:set var="detailMail" value=""></c:set>
				<div class="mail icon-conte-list" title="通讯录">
					<div class="drop" style="display:none;" title="">
						<div class="arrow"><em>◆</em><span>◆</span></div>
							<ul>
								<c:forEach items="${details }" var="detail">
									<c:if test="${detail.isDefault eq 1 }">
										<c:set var="lxr" value="${detail.name }"></c:set>
										<c:set var="detailMail" value="${detail.email}"></c:set>
									</c:if>
									<li>
										<p class="list fl_l">
											<span title="${detail.name }<c:if test="${!empty detail.work }">（${detail.work }）</c:if>">${detail.name }<c:if test="${!empty detail.work }">（${detail.work }）</c:if></span><i style="display:inline-block;height:25px;line-height:20px;float:left;font-style:normal;">：</i><label phone="tel" title="${empty detail.telphone ? detail.telphonebak : detail.telphone }">${empty detail.telphone ? detail.telphonebak : detail.telphone }</label>
											<c:if test="${(!empty detail.telphone) || (!empty detail.telphonebak) }">
												<a href="javascript:;" <c:if test="${shiroUser.serverDay gt 0}">id="call_${empty detail.telphone ? detail.telphonebak : detail.telphone }" custName="${custInfo.name }" custId="${custInfo.resCustId }" custType="${custInfo.type }" custState="${custInfo.status }" define1="${detail.name }" lastOptionId="${custInfo.lastOptionId }"  lastOptionName="${lastOptionName }"</c:if> class="tel icon-phone  ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="拨打电话"></a>
											</c:if>
											<c:if test="${!empty detail.email }">
												<a href="javascript:;" <c:if test="${shiroUser.serverDay gt 0}">onclick="emailBysend('${detail.name}','${detail.email }')"</c:if> class="e-mail icon-email demoBtn_m  ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="发送邮件"></a>
											</c:if>
										</p>
									</li>
								</c:forEach>
							</ul>
					</div>
				</div>
			</c:if>
			<c:choose>
				<c:when test="${commonOnwerOpen eq 1 && shiroUser.account eq custInfo.commonAcc }">
					<c:choose>
						<c:when test="${commonOnwerEdit eq 1 }">
							<a href="javascript:;" btn-type="auth" auth_id="base_custEdit"  id="editCustBtn" custid="${custInfo.resCustId }" class="edit demoBtn_l icon-edit fl_r" title="编辑"></a>
						</c:when>
						<c:otherwise>
							<a href="javascript:;" btn-type="auth" auth_id="base_custEdit"  class="edit demoBtn_l icon-edit img-gray fl_r" title="编辑"></a>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<a href="javascript:;" btn-type="auth" auth_id="base_custEdit"  id="editCustBtn" custid="${custInfo.resCustId }" class="edit demoBtn_l icon-edit fl_r" title="编辑"></a>
				</c:otherwise>
			</c:choose>
		</div>
		<c:choose>
			<c:when test="${custInfo.state eq 1 }">
				<p class="list fl_l"><span>联系人：</span><label title="${lxr }">${lxr }</label></p>
				<p class="list fl_l">
					<span>联系电话：</span>
					<label phone="tel" title="${custInfo.mobilephone }">${custInfo.mobilephone }</label>
					<c:if test="${!empty custInfo.mobilephone }">
						<a href="javascript:;" <c:if test="${shiroUser.serverDay gt 0}">id="call_${custInfo.mobilephone }" custId="${custInfo.resCustId }"  custName="${custInfo.name }" custType="${custInfo.type }" custState="${custInfo.status }" define1="${lxr }" lastOptionId="${custInfo.lastOptionId }"  lastOptionName="${lastOptionName }"</c:if> class="tel icon-phone ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="拨打电话"></a>
						<a href="javascript:;" <c:if test="${shiroUser.serverDay gt 0}">onclick="toSmsSendPage('${custInfo.state eq 1 ? lxr : custInfo.name}','${custInfo.mobilephone }')"</c:if>  class="mess icon-message demoBtn_e ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="发送短信" id="demoBtn_fa"></a>
					</c:if>
				</p>
				<p class="list fl_l"><span>邮&nbsp;&nbsp;箱：</span><c:if test="${not empty detailMail }"><label title="${detailMail }">${detailMail }</label><a href="javascript:;" class="icon-email demoBtn_d  ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }"  <c:if test="${shiroUser.serverDay gt 0}">onclick="emailBysend('${lxr}','${detailMail }')"</c:if> title="发送邮件"></a></c:if></p>
				<p class="list fl_l"><span>联系地址：</span><label title="${custInfo.address }">${custInfo.address }</label></p>
			</c:when>
			<c:otherwise>
				<p class="list fl_l"><span>性别：</span>
					<label>
						<c:choose>
							<c:when test="${custInfo.sex eq 1 }">男</c:when>
							<c:when test="${custInfo.sex eq 2 }">女</c:when>
						</c:choose>
					</label>
				</p>
				<p class="list fl_l">
					<span>联系电话：</span>
					<label phone="tel" title="${custInfo.mobilephone }">${custInfo.mobilephone }</label>
					<c:if test="${!empty custInfo.mobilephone }">
						<a href="javascript:;" <c:if test="${shiroUser.serverDay gt 0}">id="call_${custInfo.mobilephone }" custId="${custInfo.resCustId }" custName="${custInfo.name }" custType="${custInfo.type }" custState="${custInfo.status }" define1="${custInfo.name }" lastOptionId="${custInfo.lastOptionId }" lastOptionName="${lastOptionName }" define3="${custInfo.company }"</c:if> class="tel icon-phone ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="拨打电话"></a>
						<a href="javascript:;" <c:if test="${shiroUser.serverDay gt 0}">onclick="toSmsSendPage('${custInfo.state eq 1 ? lxr : custInfo.name}','${custInfo.mobilephone }')"</c:if>  class="mess icon-message demoBtn_e ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }" title="发送短信" id="demoBtn_fa"></a>
					</c:if>
				</p>
				<p class="list fl_l"><span>邮&nbsp;&nbsp;箱：</span><c:if test="${not empty custInfo.email }"><label title="${custInfo.email }">${custInfo.email }</label><a href="javascript:;" <c:if test="${shiroUser.serverDay gt 0}">onclick="emailBysend('${custInfo.name }','${custInfo.email }')"</c:if> class="icon-email demoBtn_d ${shiroUser.serverDay gt 0 ? '' : 'img-gray' }"  title="发送邮件"></a></c:if></p>
				<p class="list fl_l"><span>联系地址：</span><label title="${custInfo.companyAddr }">${custInfo.companyAddr }</label></p>
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
		<c:when test="${not empty actionDtos }">
			<div class="hyx-wlm-cent-timeline hyx-cfu-timeline"  id="follow_show">
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
								        				<p class="cfu-list-note" ><label class="fl_l">联系小记：</label><span class="fl_l contact_note">${follow.remark.replaceAll("\\n","<br/>") }</span></p>
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
		</c:when>
		<c:otherwise>
			<div class="hyx-wlm-cent-timeline hyx-cfu-timeline" id="follow_show">
				<!-- 暂无联系记录开始 -->
				<div class="none-bg">
					<p class="tit_none">暂无联系记录！</p>
				</div>
				<!-- 暂无联系记录结束 -->
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

</div>
	<script type="text/javascript">
		$(function(){
			/********** 屏蔽手机或固话中间几位  *********/
			$("[btn-type=auth]").each(function(index,element){
				var _this = $(element);
				var auth_id = _this.attr("auth_id");
				var is_show = window.top.shrioAuthObj(auth_id);
				if(_this.hasClass("hidden-element")){
					is_show ? _this.css("visibility","visible"):_this.css("visibility","hidden");
				}else{
					is_show ? _this.show():_this.hide();
				}
			});
			//播放按钮点击的时候
			$("[name='icon-play']").click(function(){
				var v = $(this).attr("v");
				var callState = $(this).attr("callState");//呼叫类型
				// 获取当前行的客户姓名
				var callName = $(this).attr("custName");
				var callNum = "";
				// 如果单位姓名为空就获取号码
				if(callState == "1"){ //1:呼入，2:呼出
						callNum = $(this).attr("calledNum");// 主叫号码
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
		        var left_height = $('.hyx-cm-left').height();
		        var card_height = $('.hyx-cfu-card').height();
		        var head_height = $('.hyx-cm-new').height();
		        var load_height = left_height - card_height - head_height -65;
		        $('.hyx-cfu-timeline').each(function(i,item){
		         //   $(item).css({'height':load_height + 'px'})
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
		        
// 		        /*下拉框部分*/
// 		        $('.icon-conte-list').click(function(){
// 		            $(this).find('.drop').fadeToggle();
// 		        });
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
		    
		    $('.hyx-cfu-tab').find('li').click(function(){
		        $(this).addClass('select').siblings('li').removeClass('select');
		        var id = $(this).attr("id");
		        if(id == "follow_"){
		        	$("#callLog_show").hide();
		        	$("#reView_show").hide();
		        	$("#follow_show").show();
		        }else if(id == "callLog_"){ // 通话记录      	
					var url =  "${ctx}/cust/custFollow/List/queryCallLog.do";
					var data = {'resCustId':'${custInfo.resCustId}'};
					$.post(url,data,function(data){
						$("#follow_show").hide();
			        	$("#reView_show").hide();
						$("#callLog_show").show();
						$("#callLog_show").html(data);				
					},'html');
		        	
		        }else if(id =="reView_"){ // 跟进信息
					var url =  "${ctx}/cust/custFollow/List/queryCustReview.do";
					var data = {'resCustId':'${custInfo.resCustId}'};
					$.post(url,data,function(data){
						$("#callLog_show").hide();
			        	$("#follow_show").hide();
						$("#reView_show").show();
						$("#reView_show").html(data);				
					},'html');
		        }
		    }); 
		    
		});
	</script>
</body>
</html>
