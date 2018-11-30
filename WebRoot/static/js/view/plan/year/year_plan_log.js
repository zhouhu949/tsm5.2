function chart(dateArray,pMArray){
    var ec=echarts;
    var theme=echarts.theme;
		            myChart = ec.init(document.getElementById('main'), theme);
		            myChart.setOption({
		                title : {
		                    text: '变更记录走势图',
		                    x:'center'
		                },
		                tooltip : {
		                    trigger: 'item',
		                    axisPointer:{type:'none'},
		                    showDelay: 0,
		                    formatter: function(data){  
						        return data[0]+"："+data[5]['count'];
						    }
		                },
		                grid:{x:45,x2:20,y:40,y2:50},
		                xAxis : [
		                    {
		                        type : 'category',
		                        boundaryGap : true,
		                        axisLabel: {
		                            interval:0
		                        },
		                        data : dateArray
		                    }
		                ],
		                yAxis : [
		                    {
		                        type: 'value',
		                        splitNumber: 8,
		                        min: 0,
		                        max: 1600,
		                        axisLabel: {
		                            formatter: function (value) {
		                              return Math.round(value - 0)
		                            }
		                        }
		                    }
		                ],
		                series : [{
		                    name:'变更原因',
		                    type:'line',
		                    smooth: true,
		                    itemStyle:{normal:{color:'#E87C25'}},
		                    data:pMArray
		                }]
		            });
		            window.onresize = myChart.resize;
}

function chartFn(){
	var fromYear=$("#fromYear").val();
	var toYear=$("#toYear").val();
	var groupId=$("#groupId").val();
	
	$.post(ctx+"/plan/year/history_json",{"fromYear":fromYear,"toYear":toYear,"groupId":groupId},function(list){
		var dateArray=new Array();
		var pMArray =new Array();
		for(var i=0;i<list.length;i++){
			dateArray.push(formatDate(list[i].inputtime,2));
			pMArray.push({value:list[i].planMoney,itemStyle:{normal:{label:{show:true,formatter:list[i].planMoney.toString()}}},count:list[i].reason});
		}
		
		chart(dateArray,pMArray);
	});
}
