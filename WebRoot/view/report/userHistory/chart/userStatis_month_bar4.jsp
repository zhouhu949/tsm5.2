<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>销售统计-个人统计分析-月统计分析</title>
    <style>
        .com-table table thead tr  th{font-weight:normal;font-size:12px;}
    </style>
    <script type="text/javascript">
        $(function(){

        });
        window.onload=function(){
            var height = $(".static-analy-line4").height()+30;
            window.parent.$("#iframepage1").css({"height":height+"px"});
        };
    </script>
</head>
<body>
<div class="static-analy-line4" id="main04"></div>

<script type="text/javascript" src="${ctx}/static/thirdparty/echarts-3.3.2/echarts.min.js"></script>
<script type="text/javascript" type="text/javascript">
	var list = ${list};
	var index =${index};
	
	var dateArray= new Array();
	var moneyArray= new Array();
	var saleArray = new Array();
	var saleChanceArray = new Array();
	var max=0;
	
	if(list!=null){
		for(var i=0;i<list.length;i++){
			dateArray.push(list[i].dateFmt);
			var signMoney=list[i].signMoney==null?0:list[i].signMoney;
			var saleMoney = list[i].saleMoney==null?0:list[i].saleMoney;
			var saleChanceMoney = list[i].saleChanceMoney==null?0:list[i].saleChanceMoney;
			
			if(signMoney>max) max=signMoney;
			if(saleMoney>max) max=saleMoney;
			if(saleChanceMoney>max) max=saleChanceMoney;
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
  	
 	 if(index==9)  selected = {'签单金额(万元)' : false,'预期签单金额(万元)':false};
	if(index==10)  selected = {'回款金额(万元)' : false,'预期签单金额(万元)':false};
	if(index==11)  selected = {'签单金额(万元)' : false,'回款金额(万元)' : false};

  	var ec=echarts;
                var myChart4 = ec.init(document.getElementById('main04'));

                //加载图表
                option04 = {
                    title : {
                        x: 'center',
                        y: 'top',
                        text: '月度统计表'
                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                   		 selected: selected,	
                        /*orient: 'vertical',*/
                        x: 'center',
                        y: 'bottom',
                        data:['回款金额(万元)','签单金额(万元)','预期签单金额(万元)']
                    },
                    calculable : true,
                    xAxis : [
                        {
                            type : 'category',
                            splitLine:{
                                show:false
                            },
                            data :  dateArray
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
                    series : [
                        {
                            name:'回款金额(万元)',
                            type:'bar',
                            data:moneyArray,
                            /*barWidth:10,*/
                            barCategoryGap:'78%',
                            /* markLine : {
                             data : [
                             {type : 'average', name: '平均值'}
                             ]
                             },*/
                            itemStyle: {
                                normal: {
                                    width:20,
                                    color: '#5DAFE8',
                                    barBorderWidth:0,
                                    barBorderColor:'#228bb0',
                                    label : {
                                        show : false,
                                        textStyle : {
                                            color:'#000',
                                            fontSize : '12',
                                            fontFamily : '微软雅黑',
                                            fontWeight : 'normal'
                                        }
                                    }
                                }
                            }
                        },
                        {
                           	name:'签单金额(万元)',
                            type:'bar',
                            data:saleArray,
                            /*barWidth:10,*/
                            barCategoryGap:'78%',
                            /* markLine : {
                             data : [
                             {type : 'average', name: '平均值'}
                             ]
                             },*/
                            itemStyle: {
                                normal: {
                                    width:20,
                                    color: '#ff9e9e',
                                    barBorderWidth:0,
                                    barBorderColor:'#228bb0',
                                    label : {
                                        show : false,
                                        textStyle : {
                                            color:'#000',
                                            fontSize : '12',
                                            fontFamily : '微软雅黑',
                                            fontWeight : 'normal'
                                        }
                                    }
                                }
                            }
                        },
                        {
                            name:'预期签单金额(万元)',
                            type:'bar',
                            data:saleChanceArray,
                            /*barWidth:10,*/
                            barCategoryGap:'78%',
                            /* markLine : {
                             data : [
                             {type : 'average', name: '平均值'}
                             ]
                             },*/
                            itemStyle: {
                                normal: {
                                    width:20,
                                    color: '#B3D52B',
                                    barBorderWidth:0,
                                    barBorderColor:'#228bb0',
                                    label : {
                                        show : false,
                                        textStyle : {
                                            color:'#000',
                                            fontSize : '12',
                                            fontFamily : '微软雅黑',
                                            fontWeight : 'normal'
                                        }
                                    }
                                }
                            }
                        }
                    ]
                };
                myChart4.setOption(option04);

</script>
</body>
</html>
