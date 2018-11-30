<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<input type="hidden" id="project_path" value="${project_path }">
<c:choose>
		<c:when test="${not empty custFollows }">
				<div class="hyx-wlm-cent-timeline hyx-cfu-timeline" id="follow_show" style="height:250px;">
					<div class="timeline">
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
					        				<p class="cfu-list" style="text-overflow:clip;"><label class="lab">客户联系人：${follow.custName }</label><c:choose>
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
							        					 			<span class="video_on" title="${call.timeShow}" name="icon-play"  callState="${call.callState}" timeLength="${call.timeLength}" url="${playUrl}${call.recordUrl}&d=${call.recordKey}&id=${call.code}&compid=${call.orgId}&calllen=${call.timeLength}" callId="${call.callId}" custName="${call.custName}" callerNum="${call.callerNum }"  calledNum="${call.calledNum}" ></span>		 					 	
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
					        				<p class="cfu-list-note com-none" ><label class="fl_l">联系小记：</label><span class="fl_l contact_note">${follow.feedbackComment.replaceAll("\\n","<br/>") }</span></p>
					        			</div>
					      			</div>
						        </li>		    
					    	</c:forEach>	        	
				  		</ul>
					</div>
				</div>
		</c:when>
		<c:otherwise>
			<div class="hyx-wlm-cent-timeline hyx-cfu-timeline" id="follow_show" style="height:250px;">
				<!-- 暂无数据开始 -->
				<div class="none-bg">
					<p class="tit_none">暂无跟进记录！</p>
				</div>
				<!-- 暂无数据结束 -->
			</div>
		</c:otherwise>
	</c:choose>
	<script type="text/javascript">
	$(function(){
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
/* 	        var left_height = $('.hyx-cfu-left').height();
	        var card_height = $('.hyx-cfu-card').height();
	        var tab_height = $('.hyx-cfu-tab').height();
	        var load_height = left_height - card_height - tab_height -65;
	        alert(left_height);
	        alert(card_height);
	        alert(tab_height);
	        alert(load_height); */
	        $('.hyx-cfu-timeline').each(function(i,item){
/* 	            $(item).css({'height':load_height + 'px'}); */
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
	});	
	</script>