<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理者首页</title>
    <script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js${_v}"></script>
    <link rel="stylesheet" type="text/css" href="${ctx }/static/css/style.css${_v}" >
    <link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css${_v}" >
    <link rel="stylesheet" type="text/css" href="${ctx }/static/css/personal-center.css${_v}" >
</head>
<body>
	<div class="hyx-personal-center-sale-grow">
		<div class="hyx-personal-center-sale-grow-head hyx-layout">
			<div class="hyx-personal-center-sale-grow-head-left  hyx-layout-side">
				<c:choose>
            	<c:when test="${curPoints.level ne 5 }">
		                <div class="growth-level-left fl_l">
		                    <span class="growth-level-icon divis-icon0${curPoints.level } fl_l"></span>
		                    <span class="growth-level-text fl_l">${curPoints.levelName }</span>
		                </div>
		                <div class="votebox fl_l">
		                      <label for="" class="points-scroll-left">${userPoints }</label>
		                      <label for="" class="points-scroll-right">+${curPoints.endNumber-userPoints }</label>
		                      <dl class="barbox">
		                          <dd class="barline">
		                              <span style="width:0%;" class="charts"></span>
		                              <label class="p" name="${userPoints }">0</label>
		                          </dd>
		                      </dl>
		                      <span class="sp" style=" display:none;"><label class="b">${curPoints.endNumber}</label></span>
		               </div>
		               <div class="growth-level-right fl_l">
		                          <span class="growth-level-icon divis-gray-icon0${curPoints.level+1 } fl_l"></span>
		                          <span class="growth-level-text fl_l" style="color:#b6b5b5;">${levelMap[curPoints.level+1]['levelName'] }</span>
		               </div>
		               <span class="high-level" style="display:none;">${curPoints.startNumber }</span>
		          	   <span class="lower-level" style="display:none;">${curPoints.endNumber }</span>
            	</c:when>
            	<c:otherwise>
            		   <p class="growth-sm02" style="text-indent:30px;"><span>截止<fmt:formatDate value="${date }" pattern="yyyy年MM月dd日"/>，你所收获的销售成长积分已突破${levelMap[5]['startNumber'] }分。</span></p>
            			<p class="growth-sm03" style="width:450px;text-align:center;"><span class="sales-star" style="display:inline-block;">恭喜您，成为${levelMap[5]['levelName'] }!</span></p>
            	</c:otherwise>
            </c:choose>
			</div>
			<div class="hyx-personal-center-sale-grow-head-right hyx-layout-content">
				<div class="points-acces-rule">
					<span>积分规则</span>
	                <ul>
	                    <li>销售每签约${dictionaryList[0].dictionaryValue}元获得1积分奖励。</li>
	                    <li>每月单项任务完成，获得${dictionaryList[1].dictionaryValue}积分奖励。</li>
	                    <li>完成月度计划，获得${dictionaryList[2].dictionaryValue}积分奖励。</li>
	                    <li>连续3个月完成月度计划，获得${dictionaryList[3].dictionaryValue}积分奖励。</li>
	                </ul>
				</div>
			</div>
		</div>
		<div class="hyx-personal-center-sale-grow-body">
                    <div class="divis-icon-top">
                        <span class="icon-top-first">${levelMap[1]['startNumber'] }</span>
                        <span class="icon-top-second">${levelMap[2]['startNumber'] }</span>
                        <span class="icon-top-third">${levelMap[3]['startNumber'] }</span>
                        <span class="icon-top-forth">${levelMap[4]['startNumber'] }</span>
                        <span class="icon-top-fivth">${levelMap[5]['startNumber'] }</span>
                        <span class="icon-text-first">${levelMap[1]['levelName'] }</span>
                        <span class="icon-text-second">${levelMap[2]['levelName'] }</span>
                        <span class="icon-text-third">${levelMap[3]['levelName'] }</span>
                        <span class="icon-text-forth">${levelMap[4]['levelName'] }</span>
                        <span class="icon-text-fivth">${levelMap[5]['levelName'] }</span>
                    </div>
		</div>
	</div>

	<c:if test="${curPoints.level ne 5 }">
	<script language="javascript">
		$(function(){
			$('.votebox').find('.charts').each(function(i,item){
		        var a=parseInt($(".high-level").text());/*当前已有积分*/
		        var b=parseInt($(".lower-level").text());/*下一级积分*/
		        var c=b-a;/*两级别之差*/
		        var d=parseInt($(".points-scroll-left").text())-a;/*当前新增积分*/
		        var e=c-d;/*距离升级还差多少积分*/
		        var f=d-c;/*新增积分超出下一级积分部分*/
		        var i=0;
		        $(".points-scroll-right").text(e);
		        if(d<=c){
		            var inter = setInterval(function(){
		                    i=i+50;
		                if(i<=d){
		                    $(item).css({"width":(i/c)*100+"%"});
		                    $(item).siblings(".p").text(((i/c)*100).toFixed(2)+"%");
		                }else{
		                    clearInterval(inter);
		                    if(d==c){
		                        nextlevel(f);
		                        }
		                };
		
		            },20);
		            
		        }else{
		            var inter = setInterval(function(){
		                    i=i+50;
		                if(i<=c){
		                    $(item).css({"width":(i/c)*100+"%"});
		                    $(item).siblings(".p").text(((i/c)*100).toFixed(2)+"%");
		                }else{
		                    clearInterval(inter); 
		                    nextlevel(f);
		                };
		
		            },20);
		        }
		    });
		});
	</script>
  	</c:if>
</body>
</html>