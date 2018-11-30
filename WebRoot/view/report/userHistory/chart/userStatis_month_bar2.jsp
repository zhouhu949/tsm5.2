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
            var height = $(".static-analy-line2").height()+30;
            window.parent.$("#iframepage1").css({"height":height+"px"});
        };
    </script>
</head>
<body>
<div class="static-analy-line2" id="main02"></div>
<script type="text/javascript" src="${ctx}/static/thirdparty/echarts-3.3.2/echarts.min.js"></script>
<script type="text/javascript" type="text/javascript">
	var list = ${list};
	var index =${index};
	
	var dateArray = new Array();
	var chargeTimeArray= new Array();
	var callTimeArray= new Array();
	var inCallTimeArray= new Array();
	var max=0;
	if(list!=null){
		for(var i=0;i<list.length;i++){
			dateArray.push(list[i].dateFmt);
			if(list[i].chargeTime>max)  max=list[i].chargeTime;
			if(list[i].callTime>max)  max=list[i].callTime;
			if(list[i].inCallTime>max)  max=list[i].inCallTime;
			
			chargeTimeArray.push(list[i].chargeTime);
			callTimeArray.push(list[i].callTime);
			inCallTimeArray.push(list[i].inCallTime);
			
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

	var ec=echarts;
                var myChart2 = ec.init(document.getElementById('main02'));

                //加载图表
                option02 = {
                    title : {
                        x: 'center',
                        y: 'top',
                        text: '月度统计表'
                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                        /*orient: 'vertical',*/
                        selected: selected,
                        x: 'center',
                        y: 'bottom',
                        data:['计费时长(分)','呼出时长(分)','呼入时长(分)']
                    },
                    calculable : true,
                    xAxis : [
                        {
                            type : 'category',
                            splitLine:{
                                show:false
                            },
                            data : dateArray
                        }
                    ],
                    yAxis : [
                        {
						    type: 'value',
						    splitNumber: 10,
						    min: 0,
						    max: max<100?100:max,
						    axisLabel: {
						        formatter: function (value) {
						            return Math.round(value - 0)
						        }
						    }
						}
                    ],
                    series : [
                        {
                            name:'计费时长(分)',
                            type:'bar',
                            data:chargeTimeArray,
                            barCategoryGap:'78%',
                            itemStyle: {
                                normal: {
                                    width:20,
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
                            name:'呼出时长(分)',
                            type:'bar',
                            data:callTimeArray,
                            /*barWidth:10,*/
                            barCategoryGap:'78%',
                            itemStyle: {
                                normal: {
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
                            name:'呼入时长(分)',
                            type:'bar',
                            data:inCallTimeArray,
                            /*barWidth:10,*/
                            barCategoryGap:'78%',
                            itemStyle: {
                                normal: {
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
                myChart2.setOption(option02);

</script>
</body>
</html>
