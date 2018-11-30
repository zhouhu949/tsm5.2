<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>销售统计-个人统计分析-月统计分析</title>
    <script type="text/javascript">
        $(function(){
        });
        window.onload=function(){
            var height = $(".static-analy-line1").height()+30;
            window.parent.$("#iframepage1").css({"height":height+"px"});
        };
    </script>
</head>
<body>
<div class="static-analy-line1" id="main01"></div>

<script type="text/javascript" src="${ctx}/static/thirdparty/echarts-3.3.2/echarts.min.js"></script>
<script type="text/javascript" type="text/javascript">
var list = ${list};
var index =${index};

var dateArray = new Array();
var callOutNumArray= new Array();
var callInNumArray= new Array();
var callResArray= new Array();
var validCallOutArray= new Array();
var max=0;
if(list!=null){
	for(var i=0;i<list.length;i++){
		dateArray.push(list[i].dateFmt);
		if(list[i].callOutNum>max)  max=list[i].callOutNum;
		if(list[i].callInNum>max)  max=list[i].callInNum;
		if(list[i].callRes>max)  max=list[i].callRes;
		if(list[i].validCallOut>max)  max=list[i].validCallOut;
		
		
	 	callOutNumArray.push(list[i].callOutNum);
		callInNumArray.push(list[i].callInNum);
		callResArray.push(list[i].callRes);
		validCallOutArray.push(list[i].validCallOut);
		
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

    // Step:4 require echarts and use it in the callback.
    // Step:4 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    var ec = echarts;
                var myChart1 = ec.init(document.getElementById('main01'));
                //加载图表
                option01 = {
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
                        data:['呼出总数(个)','已接总数(个)','呼叫资源(个)','有效呼叫(个)']
                    },
                    calculable : true,
                    xAxis : [
                        {
                            type : 'category',
                            splitLine:{
                                show:false
                            },
                            data :dateArray
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
                            name:'呼出总数(个)',
                            type:'bar',
                            data:callOutNumArray,
                            barWidth:10,
                            barCategoryGap:'40%',
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
                            name:'已接总数(个)',
                            type:'bar',
                            data:callInNumArray,
                            barWidth:10,
                            barCategoryGap:'40%',
                            itemStyle: {
                                normal: {
                                    color: '#ff9e9e',
                                    barBorderWidth:0,
                                    barBorderColor:'#FEB985',
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
                            name:'呼叫资源(个)',
                            type:'bar',
                            data:callResArray,
                            barWidth:10,
                            barCategoryGap:'40%',
                            itemStyle: {
                                normal: {
                                    color: '#D77B82',
                                    barBorderWidth:0,
                                    barBorderColor:'blue',
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
                            name:'有效呼叫(个)',
                            type:'bar',
                            data:validCallOutArray,
                            barWidth:10,
                            barCategoryGap:'40%',
                            itemStyle: {
                                normal: {
                                    color: '#15D25B',
                                    barBorderWidth:0,
                                    barBorderColor:'#ccc',
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
                myChart1.setOption(option01);

</script>
</body>
</html>
