<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date"%> 
<head>
	<title>客户联系</title>
	<%@ include file="/common/include.jsp"%>
	<style type="text/css">
		body{background-color:#f4f4f4 !important;overflow:hidden;}
		.hyx-mca .bomp-time .rightbox .bot{position:relative;}
		.icon_up{position:absolute;right:0;bottom:0;width:0 !important;height:0;border-left:5px solid transparent;border-right:5px solid transparent;border-top:10px solid #6b6a6b;border-bottom:0 none;}
		.icon_down{position:absolute;right:0;bottom:0;width:0 !important;height:0;border-left:5px solid transparent;border-right:5px solid transparent;border-top:0 none;border-bottom:10px solid #6b6a6b;}
		.hyx-aspc .bomp-time .rightbox p span.font_related_person{cursor:pointer; width:auto; float:none;}
		span.font_related_person:hover{color:blue;}
	</style>  
	<script type="text/javascript" src="${ctx}/static/js/stamper/jquery.stamper.js${_v}"></script>
 	<script type="text/javascript">

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
     
     // 跟进
     function follow_(custId,type){
    	 if(custId != null && custId != ""){
        	 if(type == 1){ // 客户跟进
        		 window.top.addTab(ctx+"/cust/custFollow/custFollowPage.do?custId="+custId+"&custListType=6&planParam=2&v="+new Date().getTime(),"跟进");
        	 }else if(type == 3){//  跟进警报
        		 window.top.addTab(ctx+"/cust/custFollow/custFollowPage.do?custId="+custId+"&isAlarm=1&v="+new Date().getTime(),"跟进");
        	 }else if(type == 4){//  回访操作
        		 window.top.addTab(ctx+"/service/visit/toAddVisit?custId="+custId+"&v="+new Date().getTime(),"回访操作");
        	 }else if(type == 2){ // 延后呼叫
        		 $.ajax({
        				url: ctx+"/res/tao/setDelay",
        				type:'POST',
        				data:{'resId':custId},
        				dataType:'json',
        				error:function(){alert("网络异常，请稍后再操作！")},
        				success:function(data){
        					if(data == 0){
        						 window.top.addTab(ctx+"/res/tao/taoMyRes?resId="+custId,"淘客户");
        					}
        				}
        			});		
        	 } 	 
    	 }
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
	<form action="${ctx}/message/custList.do" id="custListForm" method="post"/>
	<div class="hyx-aspc hyx-mca">
	    <div class="hyx-mca-top">
	        <label class="lab fl_l">客户联系提醒</label>
	        <label class="btn fl_r">
	            <a href="javascript:;" onclick="clear_(1)" class="com-btna fl_l">清空已读</a>
	        </label>
	    </div>
	    <c:choose>
	    	<c:when test="${not empty list}">
					<div class="bomp-time">
						<div class="timeline">
							<span class="top-icon"></span> <label class="top-arrow"><fmt:formatDate
									value="<%=new Date() %>" pattern="yyyy-MM-dd" />
							</label>
							<ul>
								<c:forEach items="${list }" var="list">
									<li class="li-load"><label class="cir"></label>
										<div class="right">
											<div class="arrow">
												<em>◆</em><span>◆</span>
											</div>
											<div class="rightbox">
												<p class="time">
													<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
														value="${list.sendDate}" />
													<c:if test="${not empty list.remark}">
														<a href="###"
														onclick="follow_('${list.remark}','${list.msgType }')"
														class="flow_up">
														<c:choose>
															<c:when test="${list.msgType eq 2 }">开始淘</c:when>
															<c:when test="${list.msgType eq 4 }">回访</c:when>
															<c:otherwise>跟进&gt;&gt;</c:otherwise>
														</c:choose>
														</a>
													</c:if>												
												</p>
												<p class="bot"><span class="span_content"><c:out value=" ${list.msgCenterContent}" escapeXml="false" /></span></p>
											</div>
										</div></li>
								</c:forEach>
							</ul>
						<c:if test="${not empty historyList}">
							<div class="hyx-mca-oldbtn">
								<label>查看历史消息</label> <span>&or;</span>
							</div>
						</c:if>
							
							<ul class="hyx-mca-old" style="display:none;">
								<c:forEach items="${historyList }" var="history">
									<li class="li-load li-click"><label class="cir"></label>
										<div class="right">
											<div class="arrow">
												<em>◆</em><span>◆</span>
											</div>
											<div class="rightbox">
												<p class="time">
													<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
														value="${history.sendDate}" />
														<c:if test="${not empty history.remark}">
														<a href="###" data-authority = "base_followCust"
														onclick="follow_('${history.remark}','${history.msgType }')"
														class="flow_up">
														<c:choose>
															<c:when test="${history.msgType eq 2 }">开始淘</c:when>
															<c:when test="${history.msgType eq 4 }">回访</c:when>
															<c:otherwise>跟进&gt;&gt;</c:otherwise>
														</c:choose>
														</a>
													</c:if>
												</p>
												<p class="bot"><span class="span_content"><c:out value=" ${history.msgCenterContent}" escapeXml="false" /></span></p>
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
								<c:forEach items="${historyList }" var="history">
									<li class="li-load li-click"><label class="cir"></label>
										<div class="right">
											<div class="arrow">
												<em>◆</em><span>◆</span>
											</div>
											<div class="rightbox">
												<p class="time">
													<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
														value="${history.sendDate}" />
														<c:if test="${not empty history.remark}">
														<a href="###" data-authority = "base_followCust"
														onclick="follow_('${history.remark}','${history.msgType }')"
														class="flow_up">
														<c:choose>
															<c:when test="${history.msgType eq 2 }">开始淘</c:when>
															<c:when test="${history.msgType eq 4 }">回访</c:when>
															<c:otherwise>跟进&gt;&gt;</c:otherwise>
														</c:choose>
														</a>
														</c:if>
												</p>
												<p class="bot">
													<span class="span_content">
														<c:out value=" ${history.msgCenterContent}" escapeXml="false" />
													</span>
												</p>
											</div>
										</div></li>
								</c:forEach>
							</ul>
						</div>
					</div>
	    	</c:otherwise>
	    </c:choose>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$(".bot").find(".span_content").each(function(){
				var $_this = $(this);
				var p_bot = $_this.parent();
				var curr_height = p_bot.height();
	/*   			alert("p的高度为"+curr_height+"-------span的高度为"+$_this.height());  */
				console.log($_this.height())
				console.log(curr_height)
				if($_this.height() > curr_height){
					$_this.append('<span class="icon_arrow icon_up"></span>');
					p_bot.css("cursor","pointer");
				}
				var icon = $_this.find(".icon_arrow");
				if(icon){
					p_bot.toggle(
						function(){
							p_bot.css("height","auto");
							icon.removeClass().addClass("icon_down");
						},
						function(){
							p_bot.css("height",curr_height+"px");
							icon.removeClass().addClass("icon_up");
						}
					);
				}
			});
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
	            });
	            
	             // 点击待联系用户名 跳转至客户卡片
			       $("span[name='expire_']").on("click",function(event){
			       		event.stopPropagation();
						var custId = $(this).attr("cust_id");
						var custName = "客户卡片";
						$.ajax({
									url:ctx+'/message/findResShowName',
									type:'post',
									data:{custId:custId},
									dataType:'json',
									error:function(){window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName);	},
									success:function(data){
										window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,data.name);
									}
								});	
				   });
		});
	</script>
</body>
</html>
