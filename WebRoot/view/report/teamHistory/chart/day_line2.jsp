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
            var height = $(".static-analy-line2").height()+30;
            window.parent.$("#iframepage1").css({"height":height+"px"});
        };
    </script>
</head>
<body>
<div class="static-analy-line2" id="main02"></div>
<script type="text/javascript" src="${ctx}/static/js/echarts-3.3.2/echarts.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/dateUtils.js"></script>
<script type="text/javascript" type="text/javascript">
var list = ${list};
var index =${index};

var dateArray = new Array();
var chargeTimeArray= new Array();
var callTimeArray= new Array();
var inCallTimeArray= new Array();
if(list!=null){
	for(var i=0;i<list.length;i++){
		dateArray.push(list[i].dateFmt);
		var chargeTime=list[i].chargeTime==null?0:list[i].chargeTime;
		var callTime=list[i].callTime==null?0:list[i].callTime;
		var inCallTime=list[i].inCallTime==null?0:list[i].inCallTime;
		
		chargeTimeArray.push(chargeTime);
		callTimeArray.push(callTime);
		inCallTimeArray.push(inCallTime);
	}
}

if(list==null||list.length==0){
	dateArray=[''];
	chargeTimeArray= [''];
	callTimeArray= [''];
	inCallTimeArray= [''];
}

var selected = null;
//'计费时长(分)','呼出时长(分)'
if(index==4)  selected = {'呼出时长(分)' : false,'呼入时长(分)' : false};
if(index==5)  selected = {'计费时长(分)' : false,'呼入时长(分)' : false};
if(index==6)  selected = {'计费时长(分)' : false,'呼出时长(分)' : false};
   var myChart = echarts.init(document.getElementById('main02'));
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
  	 		data:['计费时长(分)','呼出时长(分)','呼入时长(分)']
  	    },
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
  	            name:'计费时长(分)',
  	            type:'line',
  	            smooth:true,
  	            data:chargeTimeArray,
  	         	label: {normal: {show: true,position: 'top'}}
  	        },
  	        {
  	            name:'呼出时长(分)',
  	            type:'line',
  	            smooth:true,
  	            data:callTimeArray,
  	          	label: {normal: {show: true,position: 'top'}}
  	        },
	        {
	            name:'呼入时长(分)',
	            type:'line',
	            smooth:true,
	            data:inCallTimeArray,
	          	label: {normal: {show: true,position: 'top'}}
	        }
  	    ]
  	};
   myChart.setOption(option1);
</script>

</body>
</html>
