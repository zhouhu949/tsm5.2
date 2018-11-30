function chart(dateArray,pArray,fArray,max,title,danwei){
    var ec=echarts;
    var theme=echarts.theme;
            myChart = ec.init(document.getElementById('main'), theme);
            myChart.setOption({                
            	title : {
                    text: title,
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    axisPointer:{type:'none'},
                    showDelay: 0
                },
                legend: {
			        data:['计划','执行'],
			        x:'right'
			    },
                grid:{x:45,x2:45,y:40,y2:50},
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
                    	name:'单位（'+danwei+'）',
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
                    name:'计划',
                    type:'line',
                    smooth: true,
                    itemStyle:{normal:{color:'#E87C25'}},
                    data:pArray
                },
                {
                    name:'执行',
                    type:'line',
                    smooth: true,
                    itemStyle:{normal:{color:'#05b441'}},
                    data:fArray
                }
                ]
            });
            window.onresize = myChart.resize;
}

function chartFn(fromTime,toTime){
	var userId=$("#userId").val();
	$.post(ctx+"/plan/month/user/historyJson",{"fromTime":fromTime,"toTime":toTime,"userId":userId},function(list){
		var type=$("select").val();
		var dateArray = new Array();
		var pArray = new Array();
		var fArray = new Array();
		var title = null;
		var max = 0;
		var danwei="个";
		if("will"==type){
			title="新增意向数";
			danwei="个";
		}else if("sign"==type){
			title="新增签约数";
			danwei="个";
		}else if("money"==type){
			title="回款金额";
			danwei="万";
		}
		
		for(var i=0;i<list.length;i++){
			
			dateArray.push(formatDate(new Date(list[i].planYear,list[i].planMonth-1,1),6));
			
			var plan = null;
			var fact = null;
			
			if("will"==type){
				plan=list[i].planWillcustnum;
				fact=list[i].factWillcustnum;
			}else if("sign"==type){
				plan=list[i].planSigncustnum;
				fact=list[i].factSigncustnum; 
			}else if("money"==type){
				plan=list[i].planMoney;
				fact=list[i].factMoney;
			}
		
			if(plan>max) max= plan;
			if(fact>max) max= plan;
			
			pArray.push({value:plan,itemStyle:{normal:{label:{show:true,formatter:plan.toString()}}}});
			fArray.push({value:fact,itemStyle:{normal:{label:{show:true,formatter:fact.toString()}}}});
			
		}
		
		if(dateArray.length==0||pArray.length==0||fArray.length==0){
			dateArray = [''];
			pArray = [{value:'',itemStyle:{normal:{label:{show:true,formatter:''}}}}];
			fArray = [{value:'',itemStyle:{normal:{label:{show:true,formatter:''}}}}];
		}
		
		chart(dateArray,pArray,fArray,max,title,danwei);
	});
}

function pickerTime(){
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	if(fromDate!=null &&toDate !=null &&fromDate!='' &&toDate !=''){
		chartFn(new Date(fromDate).getTime(),new Date(toDate).getTime());
	}
}

