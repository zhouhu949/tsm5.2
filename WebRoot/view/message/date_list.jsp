<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date"%>
<head>
  <title>到期提醒</title>
  <%@ include file="/common/include.jsp"%>

    <script type="text/javascript" src="${ctx}/static/js/table.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/stamper/jquery.stamper.js${_v}"></script>
   <style type="text/css">
		body{background-color:#f4f4f4 !important;overflow:hidden;}
		.hyx-mca .bomp-time .rightbox .bot{position:relative;height: 40px;}
		.icon_up{position:absolute;right:0;bottom:0;width:0 !important;height:0;border-left:5px solid transparent;border-right:5px solid transparent;border-top:10px solid #6b6a6b;border-bottom:0 none;}
		.icon_down{position:absolute;right:0;bottom:0;width:0 !important;height:0;border-left:5px solid transparent;border-right:5px solid transparent;border-top:0 none;border-bottom:10px solid #6b6a6b;}
		.hyx-aspc .bomp-time .rightbox p span.font_related_person{cursor:pointer; width:auto; float:none;}
		span.font_related_person:hover{color:blue;}
	</style>

    <script type="text/javascript">
        $(function(){
        
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
            /*表单优化*/

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
     
        // 计划未上报查看
        function plan_show_(remark,type){
	       	 if(remark != null && remark != ""){
	       		 var year = remark.split("_")[0]
	       		 var month = remark.split("_")[1]
	       		 var groupId = remark.split("_")[2]
	           	 if(type == 1){ // 个人月计划
	           		userMonth(year,month);
	           	 }else if(type == 2){//  团队月计划
	           		teamMonth(groupId,year,month);
	           	 }else if(type == 3){ // 部门月计划
	           		groupMonth(groupId,year,month);
	           	 } 	 
	       	 }
        }
        
        function userMonth(year,month){
        	$.post(ctx+"/plan/month/user/getUserMonthPlan",{"year":year,"month":month},function(data){
        		if(data.tooLateEdit){
        			iDialogMsg("提示","已超过计划上报的截止时间，无法上报计划！");
        		}else{
        			if(data==""||data==null||data.status=='0'||data.authState=="0"){
            			window.top.addTab(ctx+"/plan/month/user/monthEdit?planYear="+year+"&month="+month+"","个人月计划-编辑");
            		}else{
            			iDialogMsg("提示","月计划已上报！");
            		}
        		}
        	});
        }
        
        function teamMonth(groupId,year,month){
        	$.post(ctx+"/plan/month/team/getGroupmonthPlan",{"groupId":groupId,"year":year,"month":month},function(data){
        		if(data.tooLateEdit){
        			iDialogMsg("提示","已超过计划上报的截止时间，无法上报计划！");
        		}else{
        			if(data==""||data==null||data.status=='0'||data.authState=="0"){
            			if(data==""||data==null){
            				window.top.addTab(ctx+"/plan/month/team/monthEdit?groupId="+groupId+"&planYear="+year+"&planMonth="+month+"&isNew=true&isFirst=true&groupType=0","小组月计划-编辑");
            			}else{
            				window.top.addTab(ctx+"/plan/month/team/monthEdit?id="+data.id+"&isFirst=true&groupType=0","小组月计划-编辑");
            			}
            		}else{
            			iDialogMsg("提示","月计划已上报！");
            		}
        		}
        	});
        }
        function groupMonth(groupId,year,month){
        	$.post(ctx+"/plan/month/team/getGroupmonthPlan",{"groupId":groupId,"year":year,"month":month},function(data){
        		if(data.tooLateEdit){
        			iDialogMsg("提示","已超过计划上报的截止时间，无法上报计划！");
        		}else{
        			if(data==""||data==null||data.status=='0'||data.authState=="0"){
            			if(data==""||data==null){
            				window.top.addTab(ctx+"/plan/month/group/monthEdit?groupId="+groupId+"&planYear="+year+"&planMonth="+month+"&isNew=true&isFirst=true&groupType=1","部门月计划-编辑");
            			}else{
            				window.top.addTab(ctx+"/plan/month/group/monthEdit?groupId="+groupId+"&planYear="+year+"&planMonth="+month+"&id="+data.id+"&isFirst=true&groupType=1","部门月计划-编辑");
            			}
            		}else{
            			iDialogMsg("提示","月计划已上报！");
            		}
        		}
        	});
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
<form action="${ctx}/message/dateList.do" id="dateListForm" method="post"/>
<div class="hyx-aspc hyx-mca hyx-mcd">
    <div class="hyx-mca-top">
        <label class="lab fl_l">到期提醒</label>
        <label class="btn fl_r">
            <a href="javascript:;" onclick="clear_(4)"  class="com-btna fl_l">清空已读</a>
        </label>
    </div>
    <c:choose>
    	<c:when test="${not empty list }">
				<div class="bomp-time">
					<div class="timeline">
						<span class="top-icon"></span> <label class="top-arrow"><fmt:formatDate
								pattern="yyyy年MM月dd日" value="<%=new Date() %>" />
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
													<c:if test="${list.msgType eq 14 }"><a href="###"
													onclick="plan_show_('${list.remark}','${list.contractType }')"
													class="flow_up">上报</a></c:if>
											</p>
											<p class="bot"><span class="span_content"><c:out value=" ${list.msgCenterContent}" escapeXml="false" /><c:if test="${list.msgType eq 22 || list.msgType eq 23}">（提示：客户被销售取回，则不能点击）</c:if></span></p>
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
													<c:if test="${history.msgType eq 14 }">
													<a href="###"
													onclick="plan_show_('${history.remark}','${history.contractType }')"
													class="flow_up">上报</a>
													</c:if>
											</p>
											<p class="bot"><span class="span_content"><c:out value=" ${history.msgCenterContent}" escapeXml="false" /><c:if test="${history.msgType eq 22 || history.msgType eq 23}">（提示：客户被销售取回，则不能点击）</c:if></span></p>
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
													<c:if test="${history.msgType eq 14 }">
													<a href="###"
													onclick="plan_show_('${history.remark}','${history.contractType }')"
													class="flow_up">上报</a>
													</c:if>
											</p>
											<p class="bot"><span class="span_content"><c:out value=" ${history.msgCenterContent}" escapeXml="false" /><c:if test="${history.msgType eq 22 || history.msgType eq 23}">（提示：客户被销售取回，则不能点击）</c:if></span></p>
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
