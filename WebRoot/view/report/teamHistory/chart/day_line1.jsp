<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>销售统计-个人统计分析-日统计分析</title>
    <script type="text/javascript">
        window.onload=function(){
            var height = $(".static-analy-line1").height()+30;
            window.parent.$("#iframepage1").css({"height":height+"px"});
        };
    </script>
</head>
<body>
<div class="static-analy-line1" id="main01"></div>
<script type="text/javascript" src="${ctx}/static/js/echarts-3.3.2/echarts.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/dateUtils.js"></script>
<script type="text/javascript" type="text/javascript">
	var list = ${list};
	var index =${index};
	
	var dateArray = [];
	var callOutNumArray= [];
	var callInNumArray= [];
	var callResArray= [];
	var validCallOutArray= [];
	if(list!=null){
		for(var i=0;i<list.length;i++){
			dateArray.push(list[i].dateFmt);
			
			var callOutNum=list[i].callOutNum==null?0:list[i].callOutNum;
			var callInNum=list[i].callInNum==null?0:list[i].callInNum;
			var callRes=list[i].callRes==null?0:list[i].callRes;
			var validCallOut=list[i].validCallOut==null?0:list[i].validCallOut;
			
		 	callOutNumArray.push(callOutNum);
			callInNumArray.push(callInNum);
			callResArray.push(callRes);
			validCallOutArray.push(validCallOut);
			
		}
	}
	
	if(list==null||list.length==0){
		dateArray=[''];
		callOutNumArray= [''];
		callInNumArray= [''];
		callResArray= [''];
		validCallOutArray= [''];
	}
	
	var selected = null;
	if(index==0)  selected = {'已接总数(个)' : false, '呼叫资源(个)' : false, '有效呼叫(个)' : false};
	if(index==1)  selected = {'呼出总数(个)' : false, '呼叫资源(个)' : false, '有效呼叫(个)' : false};
	if(index==2)  selected = {'呼出总数(个)' : false, '已接总数(个)' : false, '有效呼叫(个)' : false};
	if(index==3)  selected = {'呼出总数(个)' : false, '已接总数(个)' : false, '呼叫资源(个)' : false};
	var myChart = echarts.init(document.getElementById('main01'));
	option1 = {
	    title: {
	        x:'center',
	        text: '小组数据日分析'
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	    	selected: selected,
	        x:'center',
	        y:'bottom',
	        data:['呼出总数(个)','已接总数(个)','呼叫资源(个)','有效呼叫(个)']
	    },
	    /* grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '6%',
	        containLabel: true
	    }, */
	    grid:{x:45,x2:20,y:40,y2:50},
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : true,
	            data : dateArray
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name:'呼出总数(个)',
	            type:'line',
	            smooth:true,
	            data:callOutNumArray,
	            label: {normal: {show: true,position: 'top'}}
	        },
	        {
	            name:'已接总数(个)',
	            type:'line',
	            smooth:true,
	            data:callInNumArray,
	            label: {normal: {show: true,position: 'top'}}
	        },
	        {
	            name:'呼叫资源(个)',
	            type:'line',
	            smooth:true,
	            data:callResArray,
	            label: {normal: {show: true,position: 'top'}}
	        },
	        {
	            name:'有效呼叫(个)',
	            type:'line',
	            smooth:true,
	            data:validCallOutArray,
	            label: {normal: {show: true,position: 'top'}}
	        }
	    ]
	};
    myChart.setOption(option1);

</script>

</body>
</html>
