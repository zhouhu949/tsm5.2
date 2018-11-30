<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-年度销售规划-变更日志</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
<style type="text/css">
	body{font-family:"微软雅黑";}
	.echarts-tooltip{width:150px;overflow:hidden;word-wrap:break-word;word-break:normal;white-space:normal !important;}
</style>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/dateUtils.js"></script>
</head>
<body>
<form action="${ctx}/plan/yearlog/view">
<div class="year-sale-play hyx-aspa-play">
    <ul class="ann-sale-plan clearfix">
        <a href="${ctx}/plan/year/view"><li class="li_first">年度规划</li></a>
		<a href="${ctx}/plan/year/progress/view"><li>执行进度</li></a>
		<a href="${ctx}/plan/yearlog/view"><li class="select li_last">变更日志</li></a>
    </ul>
</div>
<div class="hyx-hsrs-bot"> 
    <div class="hyx-aspc">
    	<div class='bomp_tit fl_l'>
    		<label class='lab fl_l'>变更记录走势图</label>
    		<label class="sel_year fl_r">
    			<span>年份选择：</span>
    			<select name="planYear">
    				<c:forEach items="${years}" var="year">
    					<option value="${year }" <c:if test="${planYear == year}">selected="true"</c:if>>${year}年</option>
    				</c:forEach>
    			</select>
    		</label>
   		</div>
    	<div class="hyx-aspc-tip fl_l">
    		<div class="tip" id="main" style="width:100%;height:320px;"></div>
    	</div>
    	<div class='bomp_tit fl_l'><label class='lab'>变更记录</label></div>
    	<div class="bomp-time">
    	<c:choose>
    	<c:when test="${list!=null and list.size()>0}">
    		<div class="timeline">
    			<span class="top-icon"></span>
    			<label class="top-arrow">${planYear}</label>
    		    <ul>
    		    <c:forEach items="${list}" var="bean" varStatus="i">
    		     	<li class="li-load<c:if test="${i.last }"> end</c:if>">
    		        	<div class="left fl_l"><label>${bean.inputtimeMonthDayStr }</label><span>${bean.inputtimeYearStr }</span></div>
    	        		<label class="cir"></label>
    	        		<div class="right">
    	        			<div class="arrow"><em>◆</em><span>◆</span></div>
    	        			<div class="rightbox">
    	        				<p>${bean.dcontext }</p>
    	        				<p><label>变更原因：</label><span>${bean.reason }</span></p>
    	        			</div>
    	      			</div>
    		        </li>
    		    </c:forEach>
    	  		</ul>
    		</div>
    	</c:when>
    	<c:otherwise>
    	<div class="bomp-time" style="text-align: center;padding-top:0;">
                <label class="hyx-mcn-bg" style="margin-top:0;"><span>暂无变更记录!</span></label>
            </div>
    	</c:otherwise>
    	</c:choose>
    	</div>
    </div>
</div>
</form>
<script type="text/javascript" src="${ctx}/static/thirdparty/echarts-3.3.2/echarts.min.js"></script>
<script type="text/javascript" type="text/javascript">
var list=${jsonList};
var dateArray=new Array();
var pMArray =new Array();
var max = 0;
for(var i=0;i<list.length;i++){
	dateArray.push(formatDate(list[i].inputtime,7));
	pMArray.push({value:list[i].planMoney,itemStyle:{normal:{label:{show:true,formatter:list[i].planMoney.toString()}}},count:list[i].reason});
	if(list[i].planMoney>max) max=list[i].planMoney;
}

if(dateArray.length==0) dateArray =[''];
if(pMArray.length==0) pMArray =[{value:'',itemStyle:{normal:{label:{show:true,formatter:''}}},count:''}];

var ec= echarts;
        myChart = ec.init(document.getElementById('main'));
        myChart.setOption({
            title : {
                text: '',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                axisPointer:{type:'none'},
                showDelay: 0,
                formatter: function(data){  
        return data[0]+"："+data[5]['count'];
    }
            },
            grid:{x:45,x2:45,y:40,y2:50},
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : true,
                    axisLabel: {
                        interval:0
                    },
                    data : dateArray
                }
            ],
            yAxis : [
                {
                    type: 'value',
                    splitNumber: 10,
                    min: 0,
                    max: max<10?10:max,
                    axisLabel: {
                        formatter: function (value) {
                          return Math.round(value - 0)
                        }
                    }
                }
            ],
            series : [{
                name:'变更原因',
                type:'line',
                smooth: true,
                itemStyle:{normal:{color:'#E87C25'}},
                data:pMArray
            }]
        });
        window.onresize = myChart.resize;


window.onload = function(){
	$("select").change(
		function(){
			$("form:first").submit();
		}		
	);
};

</script>
</body>
</html>