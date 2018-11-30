<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>销售统计-个人统计分析-日统计分析</title>
    <style>
        .com-table table thead tr  th{font-weight:normal;font-size:12px;}
    </style>
    <script type="text/javascript">
        window.onload=function(){
            var height = $(".static-analy-line3").height()+30;
            window.parent.$("#iframepage1").css({"height":height+"px"});
        };
    </script>
</head>
<body>
<div class="static-analy-line3" id="main03"></div>
<script type="text/javascript" src="${ctx}/static/js/echarts-3.3.2/echarts.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/dateUtils.js"></script>
<script type="text/javascript" type="text/javascript">
  	var list = ${list};
  	var index =${index};
  	
  	var dateArray= new Array();
  	var willArray= new Array();
  	var signArray= new Array();
  	if(list!=null){
		for(var i=0;i<list.length;i++){
			dateArray.push(list[i].dateFmt);
			
			var willNum=list[i].willNum==null?0:list[i].willNum;
			var signNum=list[i].signNum==null?0:list[i].signNum;
			
			willArray.push(willNum);
			signArray.push(signNum);
		}
	}
  	
  	if(list==null||list.length==0){
  		dateArray=[''];
  		willArray= [''];
  		signArray= [''];
  	}
  	var selected = null;
  	//'新增意向(个)','新增签约(个)'
  	if(index==7)  selected = {'新增签约(个)' : false};
	if(index==8)  selected = {'新增意向(个)' : false};
	
	var myChart = echarts.init(document.getElementById('main03'));
	
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
	        data:['新增意向(个)','新增签约(个)']
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
	            name:'新增意向(个)',
	            type:'line',
	            smooth: true,
	            data:willArray,
	            label: {normal: {show: true,position: 'top'}}
	        },
	        {
	            name:'新增签约(个)',
	            type:'line',
	            smooth: true,
	            data:signArray,
	            label: {normal: {show: true,position: 'top'}}
	        }
	    ]
	};
    myChart.setOption(option1);
</script>

</body>
</html>
