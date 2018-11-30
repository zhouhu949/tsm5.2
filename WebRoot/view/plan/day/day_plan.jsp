<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>我的计划</title>
<!--本页面样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/core.css${_v}"/><!--弹框插件样式-->
<style type="text/css">
.tree-title{width:52px;}
</style>

<script type="text/javascript" src="${ctx}/static/js/view/plan/day/day_plan.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/plan/day/commonDate.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/plan/day/date.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/nongli.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/popup_layer.js${_v}"></script>
<script type="text/javascript">
	var plans=${plans};
	
	var date = new Date(${dateTime});
	var server_date = new Date(${server_dateTime});
	
	if(server_date ==null){
		server_date = new Date();
	}

	var resHeight = window.screen.height;
	if(resHeight < 790){
	  document.write('<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/date_768.css${_v}" />');
	}
	else{
	  document.write('<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/date_900.css${_v}" />');
	}
	
	$(function(){
	    var setHeight = function(height){
	        $("#iframepage").css({"height":height+"px"});
	    }
	});
</script>
<script type="text/javascript">
$(function(){
    $('.hyx-mp-bar').find('.charts').each(function(i,item){
        var a = $(item).parent().parent().next().find('.a');
        var a_a = a.attr('name');   //实际完成值
        var a_b = a.text();       //滚动的实际完成值
        var b = $(item).parent().parent().parent().find('.p');  
        var b_a = b.text();  //滚动的百分之百的值
        var f = $(item).parent().parent().next().find('.b');
        var f_a = f.text();   //计划完成值
        var c = 0; 
        var c_a = Math.round((a_a/f_a*100)*10)/10;  //完成百分之百的值
        var e = a_a*(1/100);
        var d = c_a/parseInt(a_a/e);
        var inter = setInterval(function(){
            if(a_b < a_a){
                if(a_a-a_b>=e){
                    a_b=Math.round((a_b+e)*1000)/1000;
                }else{
                    a_b=a_a;
                }
            }
            if(b_a < c_a){
                if(c_a-b_a>=d){
                    b_a=Math.round((b_a+d)*1000)/1000;
                }else{
                    b_a=c_a;
                }
            }
            if(c < c_a){
                c=c+d;
                if(c_a-c>=d){
                    $(item).css({"width":c+"%"});
                }else{
                    $(item).css({"width":c_a+"%"});
                }
                if(c >= 100){
                    $(item).css({'background-color':'#ff0000','border-color':'#ff0000'});
                }else{
                    $(item).css({'background-color':'#1979ca','border-color':'#1979ca'});
                }
            }
            a.text(a_b);
            b.text(b_a+'%');
            if(a_b >= a_a && b_a >= c_a && c >= c_a){
                clearInterval(inter);
            }
        },20);
    });
});
</script>
</head>
<body> 
<form id="dayPlanForm" action="${ctx}/plan/day/view" method="post">
	<input type="hidden" id="dateTime" name="dateTime" value="${dateTime}">
</form>
<div class="com-contb hyx-mp">
	<div class="hyx-wlm-left fl_l">
		<div class="hyx-wlm-left-date sty-borcolor-a com-radius">
			<div class="date_box" id="calendar"></div>
		</div>
		<c:choose>
			<c:when test="${monthPlan ==null}">
				<div class="hyx-mp-bar sty-borcolor-a com-radius">
				<p class="tit_a tit_b">工作需要目标</p>
            	<p class="tit_a">正如销售需要签单</p>
            	</div>
			</c:when>
			<c:otherwise>
			<div class="hyx-mp-bar sty-borcolor-a com-radius">
	            <p class="tit">${monthPlan.planMonth}月份目标</p>
	            <div class="votebox fl_l">
	                <dl class="barbox">
	                    <dd class="barline">
	                        <span style="width:0%;" class="charts"></span>
	                    </dd>
	                </dl>
	                <span class="sp">回款金额：<label class="a" name="${monthPlan.factMoney}">0</label>万/<label class="b">${monthPlan.planMoney}</label>万</span>
	                <p class="p">0</p>
	            </div>
	            <div class="votebox fl_l">
	                <dl class="barbox">
	                    <dd class="barline">
	                        <span style="width:0%;" class="charts"></span>
	                    </dd>
	                </dl>
	                <span class="sp">签约客户数：<label class="a" name="${monthPlan.factSigncustnum}">0</label>个/<label class="b">${monthPlan.planSigncustnum}</label>个</span>
	                <p class="p">0</p>
	            </div>
	            <div class="votebox fl_l">
	                <dl class="barbox">
	                    <dd class="barline">
	                        <span style="width:0%;" class="charts"></span>
	                    </dd>
	                </dl>
	                <span class="sp">新增意向数：<label class="a" name="${monthPlan.factWillcustnum}">0</label>个/<label class="b">${monthPlan.planWillcustnum}</label>个</span>
	                <p class="p">0</p>
	            </div>
	        </div>
			</c:otherwise>
		</c:choose>
				
        
	</div>
	<div class="my-work-right fl_l">
		<iframe src="${ctx}/plan/day/resource/view?dateTime=${dateTime}" id="iframepage" frameborder="0" scrolling="no" width="100%" height="100%"></iframe>
	</div>
</div>
</body>
</html>
