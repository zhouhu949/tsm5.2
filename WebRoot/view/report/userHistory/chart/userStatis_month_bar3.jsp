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
            var height = $(".static-analy-line3").height()+30;
            window.parent.$("#iframepage1").css({"height":height+"px"});
        };
    </script>
</head>
<body>
<div class="static-analy-line3" id="main03"></div>
<script type="text/javascript" src="${ctx}/static/thirdparty/echarts-3.3.2/echarts.min.js"></script>
<script type="text/javascript" type="text/javascript">
		var list = ${list};
		var index =${index};
		
		var dateArray= new Array();
		var willArray= new Array();
		var signArray= new Array();
		var max=0;
		if(list!=null){
		for(var i=0;i<list.length;i++){
			dateArray.push(list[i].dateFmt);
			
			var willNum=list[i].willNum==null?0:list[i].willNum;
			var signNum=list[i].signNum==null?0:list[i].signNum;
			
			if(willNum>max) max=willNum;
			if(signNum>max) max=signNum;
			
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

	var ec=echarts;
                var myChart3 = ec.init(document.getElementById('main03'));

                //加载图表
                option03 = {
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
                        data:['新增意向(个)','新增签约(个)']
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
                       /*  {
                            type : 'value',
                            data : [,300,600,900,1200,1500]
                        } */
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
                            name:'新增意向(个)',
                            type:'bar',
                            data:willArray,
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
                            name:'新增签约(个)',
                            type:'bar',
                            data:signArray,
                            /*barWidth:10,*/
                            barCategoryGap:'78%',
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
                        }
                    ]
                };
                myChart3.setOption(option03);

</script>
</body>
</html>
