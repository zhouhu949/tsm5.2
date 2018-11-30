<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date"%>
<head>
  <title>未接来电</title>
  <%@ include file="/common/include.jsp"%>

    <script type="text/javascript" src="${ctx}/static/js/table.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/stamper/jquery.stamper.js${_v}"></script>
   	<style type="text/css">
		body{background-color:#f4f4f4 !important;overflow:hidden;}
	</style>
    <script type="text/javascript">
        $(function(){

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
            
            /*switch开关*/
            $('.bomp-time').find('.switch').click(function(){
            	if($(this).attr('name') == 'off'){
            		$(this).addClass('switch-hover');
            		$(this).attr('name','on');
            		var phone = $(this).attr('phone')
            		handle_(phone);
            	}
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
     
     // 做处理
     function handle_(phone){
    	 $.ajax({
				url:ctx+'/callrecord/updateMissCallStatus',
				type:'post',
				data:{phone:phone},
				dataType:'json',
				error:function(){alert('网络异常，请稍后再试！')},
				success:function(data){
					if(data == 0){
						window.top.iDialogMsg("提示",'处理成功！');
						setTimeout('document.forms[0].submit()',1000);
					}else{
						window.top.iDialogMsg("提示",'处理失败！');
					}
				}
			});
     }
     
  // 打电话公共方法
     function followCallPhone(phone,resCustId){
     	var arrays = new Array();
     	var isConcat = "";
     	if(resCustId != null && $.trim(resCustId) != ""){
     		arrays.push("\"custId\":\""+resCustId+"\"");
     		$.ajax({
	            type: "post",
	            async: false,
	            dataType:'json',
	            url: ctx+'/callrecord/findResCallByCustId',
	            data: {"resCustId":resCustId},
	            success: function(data) {
	            	if(data.status=="success"){
	            		arrays.push("\"custName\":\""+data.name+"\"");
	            		arrays.push("\"custState\":\""+data.state+"\"");
	            		arrays.push("\"custType\":\""+data.custType+"\"");
	            		if($.trim(data.detailName) != ""){
	            			arrays.push("\"define1\":\""+data.detailName+"\"");
	            		}
	            		if($.trim(data.company)!=""){
	            			arrays.push("\"define3\":\""+data.company+"\"");
	            		}	
	            		if($.trim(data.saleProcessId)!=""){
	            			arrays.push("\"saleProcessId\":\""+data.saleProcessId+"\"");			
	            		}
	            		if($.trim(data.saleProcessName)!=""){
	            			arrays.push("\"saleProcessName\":\""+data.saleProcessName+"\"");			
	            		}
	            		isConcat = data.isConcat;            		
	    			}
	            }
	        });
     	}   	
     	window.top.custCallPhone(phone,arrays,resCustId,isConcat);
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
<form action="${ctx}/message/callList.do" id="callListForm" method="post"/>
<div class="hyx-aspc hyx-mca">
    <div class="hyx-mca-top">
        <label class="lab fl_l">未接来电提醒</label>
        <label class="btn fl_r">
            <a href="javascript:;" onclick="clear_(3)" class="com-btna fl_l">清空已读</a>
        </label>
    </div>
    <c:choose>
    	<c:when test="${not empty list}">
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
													<c:if test="${not empty list.remark}">
														<a href="###" class="flow_up" onclick="followCallPhone('${list.remark}','${list.businessId}')" >回拨</a>
													</c:if>												
											</p>
											<p class="bot">${list.msgCenterContent }</p>
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
														<a href="###" class="flow_up" onclick="followCallPhone('${history.remark}','${history.businessId}')" >回拨</a>
													</c:if>												
											</p>
											<p class="bot">${history.msgCenterContent }</p>
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
														<a href="###" class="flow_up" onclick="followCallPhone('${history.remark}','${history.businessId}')" >回拨</a>
													</c:if>	
											</p>
											<p class="bot">${history.msgCenterContent }</p>
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
