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


var ec = echarts

window.onload = function(){
    setTimeout("loadPage()", 100);
};

function loadPage(){
	groupPlan('');
    monthLinePlan('1','');
    /*tomorrowPlan();*/
    todayView('');
}

function groupPlan(groupId){
	if($('#main01-nodata').length > 0){
		$('#main01-nodata').parent().removeAttr('style');
		$('#main01-nodata').parent().html('<div id="main01" class="home-manag-piech01"></div>');
	}
		var myChart1 = ec.init(document.getElementById('main01'),{noDataLoadingOption:{text: '不做计划的销售不是好销售',effect:'bubble',effectOption : {backgroundColor:'#F4F4F4',effect: {n: 0}},textStyle: {fontSize: 15}}});
		var option01 = {
					title : {
						x : 'center',
						y : 'center',
						itemGap : 10,
						textStyle : {
							color : '#6b6b6b',
							fontFamily : '微软雅黑',
							fontSize : 12,
							fontWeight : 'normal'
						},
						subtextStyle : {
							color : '#015f9f',
							fontSize :	16
						}
					},
					tooltip : {
						trigger : 'item',
						formatter : function (params,ticket,callback){
							if(params.data.plan > 0){
								return params.name+':'+params.value+'万（'+(params.data.value/params.data.plan*100).toFixed(0)+'%）';
							}else{
								return '无计划';
							}
						}
					},
					legend : {
						orient : 'vertical',
						x : '-10',
						y : 'center'
					},
					calculable : false,
					series : [ {
						name : '',
						type : 'pie',
						radius : [ '50%', '70%' ],
						itemStyle : {
							normal : {
								label : {
									show : true,
									formatter : function(params) {
										if(params.data.plan > 0 ){
											 return params.value + '万\n完成'+(params.data.value/params.data.plan*100).toFixed(0) + '%';
										}else{
											return  '0万\n完成0%';
										}
									}
								},
								labelLine : {
									show : true,
									length : 5
								}
							}
						}
					} ]
				};
		$.ajax({
	    	url:ctx+'/main/sale/monthTeamPlan',
	    	type:'post',
	    	data:{groupId:groupId},
	    	dataType:'json',
	    	error:function(){},
	    	success:function(data){
	    		if(data != null){
	    			option01.series[0].data=data.data;
	    			option01.legend.data=data.legendData;
	    			option01.title.text='计划总额\n'+data.planTotal.toFixed(2)+'万';
	    			if(data.planTotal > 0){
	    				option01.title.subtext=(data.factTotal/data.planTotal*100).toFixed(0)+'%';
	    			}else{
	    				option01.title.subtext='0%';
	    			}
	    			if(option01.series[0].data.length == 0){
	    				$('#main01').parent().attr('style','text-align:center');
	    				$('#main01').parent().html('<span id="main01-nodata" class="index-no-data">暂无数据</span>');
	    			}else{
	    				myChart1.setOption(option01,true);
	    			}
	    		}
	    	}
	    });
}

function monthLinePlan(rt,gid){
	if($('#main02-nodata').length > 0){
		$('#main02-nodata').parent().removeAttr('style');
		$('#main02-nodata').parent().html('<div id="main02" class="home-manag-piech02"></div>');
	}
		 var myChart2 = ec.init(document.getElementById('main02'),{noDataLoadingOption:{text: '不做计划的销售不是好销售',effect:'bubble',effectOption : {backgroundColor:'#F4F4F4',effect: {n: 0}},textStyle: {fontSize: 15}}});
		 var option02 = {
		            title : {
		                text: '',
		                x:'center'
		            },
		            tooltip : {
		                trigger: 'item',
		                axisPointer:{type:'none'},
		                showDelay: 0,
		                formatter: function(data){  
					        return data[0]+"（"+data[1]+"）："+data[5]['count'];
					    }
		            },
		            grid:{x:45,x2:20,y:40,y2:50},
		            xAxis : [
		                {
		                    type : 'category',
		                    boundaryGap : false,
		                    axisLabel: {
		                        interval:0,
		                        formatter:function(value){
		                        	return value + ' ';//解决日期显示不全问题！日期显示6位，居中显示，最右侧距离只够显示3个字符，所以加了个空格
		                        }
		                    },
		                    splitLine : {show:false},
		                }
		            ],
		            yAxis : [
		                {
		                    type: 'value',
		                    splitNumber: 5,
		                    min: 0,
		                    axisLabel: {
		                        formatter: function (value) {
		                          return Math.round(value - 0);
		                        }
		                    }
		                }
		            ],
		            legend: {
				        x : 'right',
				        y : 'top',
				        data:['计划','执行']
				    },
		            series : [{
		                name:'计划',
		                type:'line',
		                smooth: true,
		                itemStyle:{normal:{color:'#d39d63'}}
		            },
		            {
		                name:'执行',
		                type:'line',
		                smooth: true,
		                itemStyle:{normal:{color:'#6399d3'}}
		            }]
		        };
		 $.ajax({
		    	url:ctx+'/main/sale/monthReport',
		    	type:'post',
		    	data:{reportType:rt,groupId:gid},
		    	dataType:'json',
		    	error:function(){},
		    	success:function(data){
		    		if(data != null){
		    			option02.xAxis[0].data = data.labels;
		    			if(rt == '1'){
		    				option02.yAxis[0].name='万元';
		    			}else{
		    				option02.yAxis[0].name='人';
		    			}
		    			option02.series[0].data = data.planData;
		    			option02.series[1].data = data.factData;
		    			if(option02.series[0].data.length == 0 && option02.series[1].data.length == 0){
		    				$('#main02').parent().attr('style','text-align:center');
		    				$('#main02').parent().html('<span id="main02-nodata" class="index-no-data">暂无数据</span>');
		    			}else{
		    				myChart2.setOption(option02,true);
		    			}
		    		}
		    	}
		    });
}

function tomorrowPlan(){
		myChart3 = ec.init(document.getElementById('main03'),{noDataLoadingOption:{text: '不做计划的销售不是好销售',effect:'bubble',effectOption : {backgroundColor:'#F4F4F4',effect: {n: 0}},textStyle: {fontSize: 15}}});
	    option03 = {
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    legend: {
			    	orient: 'vertical',
					    	x: 'right',
					        y: 'center',
			        data:[
			        {
			        	name:'已审批',
						textStyle:{color:'auto'}
			        },{
			        	name:'待审批',
						textStyle:{color:'auto'}
			        },
			        {
			        	name:'未上报',
						textStyle:{color:'auto'}
			        }
			        ]
			    },
			    calculable : false,
			    xAxis : [
			        {
			            type : 'value',
			            name : '人',
						splitLine:{
							show:false
						}
			        }
			    ],
			    yAxis : [
			        {
			            type : 'category',
						axisLabel: {
							show:true,
							textStyle:{
								color: '#000',
								fontFamily: '微软雅黑',
								fontSize: 10,
								fontWeight: 'normal'
							}
	                    }
			        }
			    ],
			    series : [{
			            name:'已审批',
			            type:'bar',
			            stack: '总量',
			            itemStyle:{normal: {color:'#5DAFE7'}}
			        },{
			            name:'待审批',
			            type:'bar',
			            stack: '总量',
			            itemStyle:{normal: {color:'#FFB985'}}
			        },{
			            name:'未上报',
			            type:'bar',
			            stack: '总量',
			            itemStyle:{normal: {color:'#D67B82'}}
			        }]
			};
		$.ajax({
			url:ctx+'/main/sale/tomorrowPlan',
			type:'post',
			dataType:'json',
			error:function(){},
			success:function(data){
				if(data != null){
					option03.yAxis[0].data=data.labels;
					
					var cpArray = new Array();
					$.each(data.cpData,function(idx,obj){
						var cpObj = new Object();
						var itemStyleObj = { normal: {color:'#5DAFE7',label : {show: true, position: 'insideRight'}}};
						cpObj.value = obj;
						if(obj == 0){
							itemStyleObj.normal.label.show = false;
						}
						cpObj.itemStyle = itemStyleObj;
						cpArray[idx] = cpObj;
					});
					
					var uncpArray = new Array();
					$.each(data.uncpData,function(idx,obj){
						var uncpObj = new Object();
						var itemStyleObj = { normal: {color:'#FFB985',label : {show: true, position: 'insideRight'}}};
						uncpObj.value = obj;
						if(obj == 0){
							itemStyleObj.normal.label.show = false;
						}
						uncpObj.itemStyle = itemStyleObj;
						uncpArray[idx] = uncpObj;
					});
					
					var nrArray = new Array();
					$.each(data.nrData,function(idx,obj){
						var nrObj = new Object();
						var itemStyleObj = { normal: {color:'#D67B82',label : {show: true, position: 'insideRight'}}};
						nrObj.value = obj;
						if(obj == 0){
							itemStyleObj.normal.label.show = false;
						}
						nrObj.itemStyle = itemStyleObj;
						nrArray[idx] = nrObj;
					});
					
					option03.series[0].data=cpArray;
					option03.series[1].data=uncpArray;
					option03.series[2].data=nrArray;
					if(option03.series[0].data.length == 0 && option03.series[1].data.length == 0 && option03.series[2].data.length == 0){
						$('#main03').parent().attr('style','text-align:center;padding-top:45px;');
						$('#main03').parent().html('<span id="main03-nodata" class="index-no-data">暂无数据</span>');
					}else{
						myChart3.setOption(option03,true);
					}
				}
			}
		});
}

function todayView(gid){
		var myChart4 = ec.init(document.getElementById('main04'),{noDataLoadingOption:{text: '不做计划的销售不是好销售',effect:'bubble',effectOption : {backgroundColor:'#F4F4F4',effect: {n: 0}},textStyle: {fontSize: 15}}});
	    var myChart5 = ec.init(document.getElementById('main05'),{noDataLoadingOption:{text: '不做计划的销售不是好销售',effect:'bubble',effectOption : {backgroundColor:'#F4F4F4',effect: {n: 0}},textStyle: {fontSize: 15}}});
	    var option04 = {
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
	                radius: [60, 70],
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
	                radius: [56, 60],
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
	    
	    var option05 = {
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
	                                fontSize: 16
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
	                radius: [42, 50],
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
                    hoverAnimation:false,
	                clockWise: false,
	                radius: [38, 42],
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
		$.ajax({
			url:ctx+'/main/sale/todayView',
			type:'post',
			data:{groupId:gid},
			dataType:'json',
			error:function(){},
			success:function(data){
				if(data != null){
				//处理统计
					$("#res_sign_num").text("0个");
					$("#res_no_num").text("0个");
					$("#res_pool_num").text("0个");
					$("#res_cust_num").text("0个");
					$("#res_prac").text("0");
					$("#int_sign_num").text("0个");
					$("#int_no_num").text("0个");
					$("#int_pool_num").text("0个");
					$("#int_cust_num").text("0个");
					$("#int_prac").text("0");
					var resPlan = data.resPlan == null ? 0 : data.resPlan;
					var resFact = 0;
					var intPlan = data.intPlan == null ? 0 : data.intPlan;
					var intFact = 0;
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
							intFact = obj.totalNum;
						}
					});
					
        			 $("#res_call_num").text(data.resTimeLength+"分");
        			 $("#int_call_num").text(data.intTimeLength+"分");
					
					if(resPlan > 0){
						option04.legend.data=['资源：' + resFact + '个'];
						option04.series[0].data[0].name= '资源：' + resPlan + '个';
						option04.series[1].data[0].name= '资源：' + resFact + '个';
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
					myChart4.setOption(option04,true);
					myChart5.setOption(option05,true);
				}
			}
		});
}