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
	var index =${index};
	var dateArray= new Array();
	var moneyArray= new Array();
	var saleArray = new Array();
	var saleChanceArray = new Array();

	if(list!=null){
		for(var i=0;i<list.length;i++){
			dateArray.push(formatDate(list[i].inputtime,2));
			var signMoney = list[i].signMoney==null?0:list[i].signMoney;
			var saleMoney = list[i].saleMoney==null?0:list[i].saleMoney;
			var saleChanceMoney = list[i].saleChanceMoney==null?0:list[i].saleChanceMoney;
			moneyArray.push(signMoney);
			saleArray.push(saleMoney)
			saleChanceArray.push(saleChanceMoney)
		}
	}
	
	
	if(list==null||list.length==0){
  		dateArray=[''];
  		moneyArray= [''];
  		saleMoney= [''];
  		saleChanceMoney= [''];
  	}
	 var selected = null;
  	if(index==10)  selected = {'签单金额(万元)' : false,'预期签单金额(万元)':false};
	if(index==11)  selected = {'回款金额(万元)' : false,'预期签单金额(万元)':false};
	if(index==12)  selected = {'签单金额(万元)' : false,'回款金额(万元)' : false};
	
var myChart = echarts.init(document.getElementById('main04'));
	
	option1 = {
	    title: {
	        x:'center',
	        text: '个人数据日分析'
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	    	selected: selected,
	        x:'center',
	        y:'bottom',
	        data:['回款金额(万元)','签单金额(万元)','预期签单金额(万元)']
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
			    name:'回款金额(万元)',
			    type:'line',
			    smooth: true,
			    //itemStyle:{normal:{color:'#05b441'}},
			    label: {normal: {show: true,position: 'top'}
            },
			    data:moneyArray
			},
			{
			    name:'签单金额(万元)',
			    type:'line',
			    smooth: true,
			    //itemStyle:{normal:{color:'#05b441'}},
			    label: {normal: {show: true,position: 'top'}
            },
			    data:saleArray
			},
			{
			    name:'预期签单金额(万元)',
			    type:'line',
			    smooth: true,
			    //itemStyle:{normal:{color:'#05b441'}},
			    label: {normal: {show: true,position: 'top'}
            },
			    data:saleChanceArray
			}
	    ]
	};
    myChart.setOption(option1);

</script>
</body>
</html>
