<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date"%>
<head>
  <title>通知公告</title>
  <%@ include file="/common/include.jsp"%>

    <script type="text/javascript" src="${ctx}/static/js/table.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/stamper/jquery.stamper.js${_v}"></script>
    <style type="text/css">
		body{background-color:#f4f4f4 !important;overflow:hidden;}
	</style>
    <script type="text/javascript">
        $(function(){
            /*表单优化*/

          /*左边、右边铺满整屏*/
           var winhight_a=window.parent.$('.hyx-mc-right').height(); 
           $(".hyx-aspc").css({'height':winhight_a+'px'}); 
			$(".bomp-time").css({'height':winhight_a-$(".bomp-time").offset().top-70+'px'}); 

            /*log加载*/
            $('.timeline').each(function(i,item){
                var load_num = 5,i_num = 5,forLiLen = 0,
                        load_len = $(item).find('.li-load').length;
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
                                 //下拉箭头的隐藏与显示
						       $('.timeline').find('.con').each(function(j,item_a){
						       		$(item_a).removeAttr('style');
						        	if($(item_a).height() <= 20){
							        	$(item_a).parent().find('.icon').hide();
							        }else{
							       		$(item_a).css({'height':'20px','overflow':'hidden','white-space':'nowrap','text-overflow':'ellipsis'});
							        	$(item_a).parent().find('.icon').show();
							        }
						        });
                            }
                        }
                        i_num = i_num + load_num;
                    },200);
                }

                $('.bomp-time').scroll(function(){
                    if($(this)[0].scrollTop + $(this).height() >= $(this)[0].scrollHeight-50){
                        limitLi();
                    }
                });
            });

            $('.hyx-mca-oldbtn').click(function(){
                $(this).hide();
                $(this).next('.hyx-mca-old').show();
                $(".bomp-time").css({'height':winhight_a-$(".bomp-time").offset().top-50+'px'}); 
                 //下拉箭头的隐藏与显示
			       $('.hyx-mca-old').find('.con').each(function(j,item_a){
			      		$(item_a).removeAttr('style');
			        	if($(item_a).height() <= 20){
				        	$(item_a).parent().find('.icon').hide();
				        }else{
				       		$(item_a).css({'height':'20px','overflow':'hidden','white-space':'nowrap','text-overflow':'ellipsis'});
				        	$(item_a).parent().find('.icon').show();
				        }
			        });
            });
            
	        /*点评内容显示*/
		    $('.timeline').each(function(i,item){
		        //下拉箭头的隐藏与显示
		       $('.timeline').find('.con').each(function(j,item_a){
		       		$(item_a).removeAttr('style');
		        	if($(item_a).height() <= 20){
			        	$(item_a).parent().find('.icon').hide();
			        }else{
			       		$(item_a).css({'height':'20px','overflow':'hidden','white-space':'nowrap','text-overflow':'ellipsis'});
			        	$(item_a).parent().find('.icon').show();
			        }
		        });
		        $(item).find('.right:first').find('.icon').removeClass('icon_up').addClass('icon_down');
		        $(item).find('.right:first').find('.con').addClass('con_all');
		        $(item).find('.right:first').find('.con').removeAttr('style');
		        
		        $(item).find('.li-load').click(function(){
		        	if($(this).find('.icon').css('display') != 'none'){
		        		if($(this).find('.con').hasClass('con_all') == true){
		        			$(this).find('.con').removeClass('con_all');
		        			$(this).find('.con').css({'height':'20px','overflow':'hidden','white-space':'nowrap','text-overflow':'ellipsis'});
		        			$(this).find('.icon').removeClass('icon_down').addClass('icon_up');
		        		}else{
		        		    $('.timeline').find('.icon').removeClass('icon_down').addClass('icon_up');
		        		    $('.timeline').find('.con').css({'height':'20px','overflow':'hidden','white-space':'nowrap','text-overflow':'ellipsis'});
		        			$('.timeline').find('.con').removeClass('con_all');
		        			$(this).find('.con').addClass('con_all');
		        			$(this).find('.con').removeAttr('style');
		        			$(this).find('.icon').removeClass('icon_up').addClass('icon_down');
		        		}
		        	}
		        });
		
		    });
            
        });
        
        // 清空已读消息
        function clear_(type){
        	queDivDialog("res_remove_cust","是否清空？",function(){
    			$.ajax({
    				url:ctx+'/message/deleteMessage',
    				type:'post',
    				data:{type:type},
    				dataType:'html',
    				error:function(){alert('网络异常，请稍后再试！')},
    				success:function(data){
    					if(data == 0){
    						window.top.iDialogMsg("提示",'删除成功！');
    						setTimeout('document.forms[0].submit()',1000);
    					}else{
    						window.top.iDialogMsg("提示",'删除失败！');
    					}
    				}
    			});
    		});
        }
        
        function getNoticeInfo(indexSize){
        	window.parent.pubDivShowIframe('alert_message_center_e_a','${ctx}/message/getNoticeInfo.do?indexSize='+indexSize,'通知公告',900,550);
        }
    </script>
    <script type="text/javascript" src="${ctx}/static/js/form.js"></script>
    <script type="text/javascript">
        window.onload=function(){
            var height = $(".hyx-mca").height();
            window.parent.$("#iframepage").css({"height":height+"px"});
        };
    </script>
</head>
<body>
<form action="${ctx}/message/noticeList.do" id="noticeForm" method="post"/>
<div class="hyx-aspc hyx-mca hyx-mce">
    <div class="hyx-mca-top">
        <label class="lab fl_l">通知公告提醒</label>
        <!-- <label class="btn fl_r">
            <a href="javascript:;" onclick="clear_(5)" class="com-btna fl_l">清空已读</a>
        </label> -->
    </div>
    <c:choose>
    	<c:when test="${not empty list }">
				<div class="bomp-time">
					<div class="timeline">
						<span class="top-icon"></span> <label class="top-arrow"><fmt:formatDate
								pattern="yyyy年MM月dd日" value="<%=new Date() %>" />
						</label>
						<ul>
							<c:forEach items="${list }" var="list" varStatus="listv">
								<li class="li-load"><label class="cir"></label>
									<div class="right">
										<div class="arrow">
											<em>◆</em><span>◆</span>
										</div>
										<div class="rightbox">
											<p class="time">
												<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
													value="${list.sendDate}" />
												<a href="###" onclick="getNoticeInfo(${listv.index})"
													class="flow_up demoBtn_a">查看</a>
											</p>
											<dl class="comment">
												<dt>${list.dtoTitle }</dt>
												<dd class="con">${list.dtoContext }</dd>
												<dd class="icon icon_up"></dd>
											</dl>
										</div>
									</div></li>
							</c:forEach>
						</ul>
						<c:set var="indexSize" value="0" />
						<c:if test="${fn:length(list) gt 0}">
							<c:set var="indexSize" value="${fn:length(list)}" />
						</c:if>
						<c:if test="${not empty historyList}">
							<div class="hyx-mca-oldbtn">
								<label>查看历史消息</label> <span>&or;</span>
							</div>
						</c:if>						

						<ul class="hyx-mca-old" style="display:none;">
							<c:forEach items="${historyList }" var="history"
								varStatus="historyv">
								<li class="li-load li-click"><label class="cir"></label>
									<div class="right">
										<div class="arrow">
											<em>◆</em><span>◆</span>
										</div>
										<div class="rightbox">
											<p class="time">
												<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
													value="${history.sendDate}" />
												<a href="###"
													onclick="getNoticeInfo(${indexSize+historyv.index})"
													class="flow_up demoBtn_a">查看</a>
											</p>
											<dl class="comment">
												<dt>${history.dtoTitle}</dt>
												<dd class="con">${history.dtoContext }</dd>
												<dd class="icon icon_up"></dd>
											</dl>
										</div>
									</div></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</c:when>
    	<c:otherwise>
    		<div class="hyx-mca-nonew">
					<span class="line fl_l"></span> <label class="fl_l">暂无最新消息，以下是历史消息</label>
					<span class="line fl_r"></span>
				</div>
				<div class="bomp-time">
					<div class="timeline">
						<span class="top-icon"></span> <label class="top-arrow"><fmt:formatDate
								pattern="yyyy年MM月dd日" value="<%=new Date() %>" />
						</label>
						<ul class="hyx-mca-old" style="display:block;">
						<c:forEach items="${historyList }" var="history"
								varStatus="historyv">
								<li class="li-load li-click"><label class="cir"></label>
									<div class="right">
										<div class="arrow">
											<em>◆</em><span>◆</span>
										</div>
										<div class="rightbox">
											<p class="time">
												<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
													value="${history.sendDate}" />
												<a href="###"
													onclick="getNoticeInfo(${indexSize+historyv.index})"
													class="flow_up demoBtn_a">查看</a>
											</p>
											<dl class="comment">
												<dt>${history.dtoTitle}</dt>
												<dd class="con">${history.dtoContext }</dd>
												<dd class="icon icon_up"></dd>
											</dl>
										</div>
									</div></li>
							</c:forEach>
						</ul>
					</div>
				</div>
    	</c:otherwise>
    </c:choose>
    
</div>
</body>
</html>
