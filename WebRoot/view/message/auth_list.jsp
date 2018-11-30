<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date"%>
<head>
  <title>待办审核</title>
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
        
        // 待办审核点击查看
        function showAuth_(type){
        	 if(type == 8){ // 订单审核
        		 window.top.addTab(ctx+"/audit/center?type=1","审核中心");  	 
        	 }else if(type == 16){ // 日计划审核
        		 window.top.addTab(ctx+"/audit/center?type=2","审核中心");  	 
        	 }else if(type == 15){ // 成员月计划审核
        		 window.top.addTab(ctx+"/audit/center?type=3","审核中心");  	 
        	 }else if(type == 17){ // 延期审核
        		 window.top.addTab(ctx+"/audit/center?type=5","审核中心");  	 
        	 }else if(type == 19 || type == 20){ // 团队月计划审核
        		 window.top.addTab(ctx+"/audit/center?type=4","审核中心");  	 
        	 }else if(type == 28){ // qupai 放款审核
        		 window.top.addTab(ctx+"/credit/review/reviewList","放款确认");  	 
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
<form action="${ctx}/message/authList.do" id="authListForm" method="post"/>
<div class="hyx-aspc hyx-mca">
    <div class="hyx-mca-top">
        <label class="lab fl_l">待办审核提醒</label>
        <label class="btn fl_r">
            <a href="javascript:;" onclick="clear_(6)"  class="com-btna fl_l">清空已读</a>
        </label>
    </div>
    <c:choose>
    	<c:when test="${not empty list }">
    		<div class="bomp-time">
			        <div class="timeline">
			            <span class="top-icon"></span>
			            <label class="top-arrow"><fmt:formatDate pattern="yyyy年MM月dd日" value="<%=new Date() %>"/></label>
			            <ul>
			            	<c:forEach items="${list}" var="list">
			            		  <li class="li-load">
					                    <label class="cir"></label>
					                    <div class="right">
					                        <div class="arrow"><em>◆</em><span>◆</span></div>
					                        <div class="rightbox">
					                            <p class="time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${list.sendDate}"/><a href="#" onclick="showAuth_('${list.msgType}')"  class="flow_up">查看</a></p>
					                            <p class="bot">${list.msgCenterContent }</p>
					                        </div>
					                    </div>
					                </li>
			            	</c:forEach>
			            </ul>			
			            <c:if test="${not empty historyList}">
				            <div class="hyx-mca-oldbtn">
				                <label>查看历史消息</label>
				                <span>&or;</span>
				            </div>	
			            </c:if>			            		
			            <ul class="hyx-mca-old" style="display:none;">
			               <c:forEach items="${historyList }" var="history">
			               		  <li class="li-load li-click">
					                    <label class="cir"></label>
					                    <div class="right">
					                        <div class="arrow"><em>◆</em><span>◆</span></div>
					                        <div class="rightbox">
					                            <p class="time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${history.sendDate}"/><a href="#" onclick="showAuth_('${history.msgType}')" class="flow_up">查看</a></p>
					                            <p class="bot">${history.msgCenterContent }</p>
					                        </div>
					                    </div>
					                </li>
			               </c:forEach>
			            </ul>
			        </div>
			    </div>
    	</c:when>
    	<c:otherwise>
    		<div class="hyx-mca-nonew">
				<span class="line fl_l"></span>
				<label class="fl_l">暂无最新消息，以下是历史消息</label>
				<span class="line fl_r"></span>
			</div>
			<div class="bomp-time">
			<div class="timeline">
			<span class="top-icon"></span>
			<label class="top-arrow"><fmt:formatDate pattern="yyyy年MM月dd日" value="<%=new Date() %>"/></label>
	  		<ul class="hyx-mca-old" style="display:block;">
		        <c:forEach items="${historyList }" var="history">
               		  <li class="li-load li-click">
		                    <label class="cir"></label>
		                    <div class="right">
		                        <div class="arrow"><em>◆</em><span>◆</span></div>
		                        <div class="rightbox">
		                            <p class="time"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${history.sendDate}"/><a href="#" onclick="showAuth_('${history.msgType}')"  class="flow_up">查看</a></p>
		                            <p class="bot">${history.msgCenterContent }</p>
		                        </div>
		                    </div>
		                </li>
			     </c:forEach>
	  		</ul>
		</div>
	</div>
    	</c:otherwise>
    </c:choose>
</div>
</body>
</html>
