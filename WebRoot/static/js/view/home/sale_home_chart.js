var dataStyle = {
	normal : {
		label : {
			show : false
		},
		labelLine : {
			show : false
		}
	}
};
var placeHolderStyle = {
	normal : {
		color : 'rgba(0,0,0,0)',
		label : {
			show : false
		},
		labelLine : {
			show : false
		}
	},
	emphasis : {
		color : 'rgba(0,0,0,0)'
	}
};

var ec= echarts;

var myChart4,myChart5;
var option04,option05;
    myChart4 = ec.init(document.getElementById('main04'),{noDataLoadingOption:{text: '不做计划的销售不是好销售',effect:'bubble',effectOption : {backgroundColor:'#F4F4F4',effect: {n: 0}},textStyle: {fontSize: 15}}});
    myChart5 = ec.init(document.getElementById('main05'),{noDataLoadingOption:{text: '不做计划的销售不是好销售',effect:'bubble',effectOption : {backgroundColor:'#F4F4F4',effect: {n: 0}},textStyle: {fontSize: 15}}});
    
    option04 = {
            title: {
                text: '',
                x: 230,
                y: 40,
                itemGap: 0,
                textStyle: {
                    color: '#000',
                    fontFamily: '微软雅黑',
                    fontSize: 12,
                    fontWeight: 'normal'
                },
                subtextStyle: {
                                color: '#015f9f',
                                fontSize: 16
                            }
            },
            tooltip: {
                show: true,
                formatter: "{a} <br/>{b}"
            },
            legend: {
                orient: 'vertical',
                x: document.getElementById('main04').offsetWidth / 2,
                y: 5,
                itemGap: 3
            },
            series: [{
                name: '计划',
                type: 'pie',
                clockWise: false,
                hoverAnimation:false,
                radius: [68, 78],
                itemStyle: dataStyle,
                data: [{
                	value:75,
                    itemStyle: {
                        normal: {
                            color: '#1583ec'
                        }
                    }
                }, {
                    value: 25,
                    name: 'invisible',
                    tooltip:{
                    	show:false
                    },
                    itemStyle: placeHolderStyle
                }]
            }, {
                name: '执行',
                type: 'pie',
                clockWise: false,
                hoverAnimation:false,
                radius: [65, 68],
                itemStyle: dataStyle,
                data: [{
                    itemStyle: {
                        normal: {
                            color: '#63aeff'
                        }
                    }
                }, {
                    name: 'invisible',
                    tooltip:{
                    	show:false
                    },
                    itemStyle: placeHolderStyle
                }]
            }]
        };
    
    option05 = {
            title: {
                text: '今日完成联系',
                x: 'center',
                y: 'center',
                itemGap: 10,
                textStyle: {
                    color: '#000',
                    fontFamily: '微软雅黑',
                    fontSize: 12,
                    fontWeight: 'normal'
                },
                subtextStyle: {
                                color: '#015f9f',
                                fontSize: 24
                            }
            },
            tooltip: {
                show: true,
                formatter: "{a} <br/>{b}"
            },
            legend: {
                orient: 'vertical',
                x: document.getElementById('main05').offsetWidth / 2,
                y: 0,
                itemGap: 1
            },
            series: [{
                name: '计划',
                type: 'pie',
                hoverAnimation:false,
                clockWise: false,
                radius: [50, 58],
                itemStyle: dataStyle,
                data: [{
                    value: 60,
                    itemStyle: {
                        normal: {
                            color: '#d9a624'
                        }
                    }
                }, {
                    value: 40,
                    tooltip:{
                    	show:false
                    },
                    name: 'invisible',
                    itemStyle: placeHolderStyle
                }]
            }, {
                name: '执行',
                type: 'pie',
                clockWise: false,
                hoverAnimation:false,
                radius: [47, 50],
                itemStyle: dataStyle,
                data: [{
                    itemStyle: {
                        normal: {
                            color: '#f9cc00'
                        }
                    }
                }, 
                {
                    name: 'invisible',
                    tooltip:{
                    	show:false
                    },
                    itemStyle: placeHolderStyle
                }]
            }]
        };
    todayView('');

function todayView(gid){
	$.ajax({
		url:ctx+'/main/sale/todayViewSale',
		type:'post',
		data:{groupId:gid},
		dataType:'json',
		error:function(){},
		success:function(data){
			if(data != null){
				var resPlan = data.resPlan == null ? 0 : data.resPlan;
				var resFact = 0;
				var intPlan = data.intPlan == null ? 0 : data.intPlan;
				var intFact = 0;
				//处理统计
				$.each(data.datas,function(idx,obj){
					if(obj.type == 1){//资源
						$("#res_sign_num").text(obj.signNum+"个");
						$("#res_no_num").text(obj.noNum+"个");
						$("#res_pool_num").text(obj.poolNum+"个");
						$("#res_cust_num").text(obj.custNum+"个");
						$("#res_prac").text(obj.totalNum+"个");
						resFact = obj.totalNum;
					}else{//意向
						$("#int_sign_num").text(obj.signNum+"个");
						$("#int_no_num").text(obj.noNum+"个");
						$("#int_pool_num").text(obj.poolNum+"个");
						$("#int_cust_num").text(obj.custNum+"个");
						$("#int_prac").text(obj.totalNum+"个");
						intFact=obj.totalNum;
					}
				});
				$("#res_call_num").text(data.resTimeLength+"分");
				$("#int_call_num").text(data.intTimeLength+"分");
				
				if(resPlan > 0){
					option04.series[0].data[0].name= '资源：' + resPlan + '个';
					option04.series[1].data[0].name= '资源：' + resFact + '个';
					option04.legend.data=['资源：' + resFact + '个'];
					if(resFact > resPlan){
						var fp = (resPlan/resFact)*75;
						option04.series[0].data[0].value=fp;
						option04.series[0].data[1].value=100-fp;
						
						option04.series[1].data[0].value=75;
						option04.series[1].data[1].value=25;
					}else{
						var fp = (resFact/resPlan)*75;
						option04.series[1].data[0].value=fp;
						option04.series[1].data[1].value=100-fp;
					}
				}else{
					option04.series[0].data[0].name= '资源无计划';
					option04.series[1].data[0].name= '资源无计划';
					option04.legend.data=['资源无计划'];
					option04.series[0].data[0].value=0;
					option04.series[0].data[1].value=100;
					
					option04.series[1].data[0].value=0;
					option04.series[1].data[1].value=100;
				}
				
				if(intPlan > 0){
					option05.series[0].data[0].name= '意向：' + intPlan + '个';
					option05.series[1].data[0].name= '意向：' + intFact + '个';
					option05.legend.data=['意向：' + intFact + '个'];
					if(intFact > intPlan){
						var fp1 = (intPlan/intFact)*60;
						option05.series[0].data[0].value=fp1;
						option05.series[0].data[1].value=100-fp1;
						
						option05.series[1].data[0].value=60;
						option05.series[1].data[1].value=40;
					}else{
						var fp1 = (intFact/intPlan)*60;
						option05.series[1].data[0].value=fp1;
						option05.series[1].data[1].value=100-fp1;
					}
				}else{
					option05.series[0].data[0].name= '意向无计划';
					option05.series[1].data[0].name= '意向无计划';
					option05.legend.data=['意向无计划'];
					option05.series[0].data[0].value=0;
					option05.series[0].data[1].value=100;
					
					option05.series[1].data[0].value=0;
					option05.series[1].data[1].value=100;
				}
				
				//计算总的百分比
				if(resPlan > 0 && intPlan > 0){
					option05.title.subtext=((intFact+resFact)/(resPlan+intPlan)*100).toFixed(0)+ '%';
				}else if(resPlan > 0 && intPlan == 0){
					option05.title.subtext=(resFact/resPlan*100).toFixed(0)+ '%';
				}else if(resPlan == 0 && intPlan > 0){
					option05.title.subtext=(intFact/intPlan*100).toFixed(0)+ '%';
				}else{
					option04.series[0].data[0].value=75;
					option04.series[0].data[1].value=25;
					
					option05.series[0].data[0].value=60;
					option05.series[0].data[1].value=40;
					
					option05.title.text='无计划';
				}
				myChart4.setOption(option04);
				myChart5.setOption(option05);
			}
		}
	});
}