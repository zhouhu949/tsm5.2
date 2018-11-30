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
            var height = $(".static-analy-line4").height()+30;
            window.parent.$("#iframepage1").css({"height":height+"px"});
        };
    </script>
</head>
<body>

<div class="static-analy-line4" id="main04"></div>
<script type="text/javascript" src="${ctx}/static/js/echarts-3.3.2/echarts.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/dateUtils.js"></script>
<script type="text/javascript" type="text/javascript">
//window.parent.setDate(${fromDateTime},${toDateTime});
	var list = ${list};
/*  	list=[
		{
			inputtime:"2017-07-01",
			percentStr:10
		},
		{
			inputtime:"2017-07-02",
			percentStr:20
		},
		{
			inputtime:"2017-07-03",
			percentStr:30
		},
		{
			inputtime:"2017-07-04",
			percentStr:40
		},
		{
			inputtime:"2017-07-05",
			percentStr:50
		},
	]  */
	var index =${index};
	var dateArray= new Array();
	var percentArray= new Array();

	if(list!=null){
		for(var i=0;i<list.length;i++){
			dateArray.push(formatDate(list[i].inputtime,2));
			var percent=list[i].percentStr==null?0:list[i].percentStr;
			percentArray.push(percent);
		}
	}
	
	
	if(list==null||list.length==0){
	console.log(111)
  		dateArray=[''];
  		percentArray= [''];
  	}
  	
	
var myChart = echarts.init(document.getElementById('main04'));
	
	option1 = {
	    title: {
	        x:'center',
	        text: '个人数据日分析'
	    },
	    tooltip : {
	        trigger: 'axis',
	      	 formatter:'{b0}:<br />{a0} : {c0}%'
	    },
	    legend: {
	        x:'center',
	        y:'bottom',
	        data:['行动完成率']
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
	            type : 'value',
	            axisLabel:{
	            	formatter:'{value} %'
	            }
	        }
	    ],
	    series : [
			{
			    name:'行动完成率',
			    type:'line',
			    smooth: true,
			    //itemStyle:{normal:{color:'#05b441'}},
			    label: {normal: {
			    				show: true,
			    				position: 'top',
			    				formatter:function(value,index){
			    					return value.data+"%";
			   					 }
			   			}
           		 },
			    data:percentArray
			}
	    ]
	};
    myChart.setOption(option1);

</script>
</body>
</html>
