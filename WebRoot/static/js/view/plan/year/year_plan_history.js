function chart(yearArray,pMArray,fMArray,max){
    var ec=echarts;
    var theme=echarts.theme;
	            myChart = ec.init(document.getElementById('main'), theme);
	            myChart.setOption({
	                title : {
	                    text: '历史规划走势图',
	                    x:'center'
	                },
	                tooltip : {
	                    trigger: 'item',
	                    axisPointer:{type:'none'},
	                    showDelay: 0
	                },
	                legend: {
				        data:['规划','执行'],
				        x:'right'
				    },
	                grid:{x:45,x2:20,y:50,y2:50},
	                xAxis : [
	                    {
	                        type : 'category',
	                        boundaryGap : true,
	                        axisLabel: {
	                            interval:0
	                        },
	                        data : yearArray
	                    }
	                ],
	                yAxis : [
	                    {
	                    	name:'单位（万元）',
	                        type: 'value',
	                        splitNumber: 5,
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
	                    name:'规划',
	                    type:'line',
	                    smooth: true,
	                    itemStyle:{normal:{color:'#E87C25'}},
	                    data:pMArray
	                },
	                {
	                    name:'执行',
	                    type:'line',
	                    smooth: true,
	                    itemStyle:{normal:{color:'#05b441'}},
	                    data:fMArray
	                }
	                ]
	            });
	            window.onresize = myChart.resize;
}

function chartFn(){
	var fromYear=$("#fromYear").val();
	var toYear=$("#toYear").val();
	var groupId=$("#groupId").val();
	
	$.post(ctx+"/plan/year/history_json",{"fromYear":fromYear,"toYear":toYear,"groupId":groupId},function(list){
		var yearArray=new Array();
		var pMArray =new Array();
		var fMArray =new Array();
		var max = 0 ;
		for(var i=0;i<list.length;i++){
			yearArray.push(list[i].planYear+'年');
			pMArray.push({value:list[i].planMoney,itemStyle:{normal:{label:{show:true,formatter:list[i].planMoney.toString()}}}});
			fMArray.push({value:list[i].factMoney,itemStyle:{normal:{label:{show:true,formatter:list[i].factMoney.toString()}}}});
			
			if(max <list[i].planMoney) max =list[i].planMoney;
			if(max <list[i].factMoney) max =list[i].factMoney;
		}
		
		if(yearArray.length==0||pMArray.length==0||fMArray.length==0){
			yearArray = [''];
			pMArray = [''];
			fMArray = [''];
		}
		
		chart(yearArray,pMArray,fMArray,max);
	});
}
