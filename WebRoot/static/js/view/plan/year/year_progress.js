function chart1(planMonth,max,groupArray,planArray,factArray) {
	var ec=window.echarts;
		var myChart1 = ec.init(document.getElementById('main01'));
		// 加载图表
		option01 = {
			title : {
				x : 'center',
				y : 'top',
				text : planMonth+'月各部门销售统计'
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				orient : 'vertical',
				x : 'right',
				y : 'top',
				data : [ '规划', '执行' ]
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				data :  groupArray,
				}
			],
			yAxis : [ {
				type : 'value',
				name : '销售业绩(万元)',
				data : [ , 300, 600, 900, 1200, 1500 ]
			} ],
			series : [ {
				name : '规划',
				type : 'bar',
				data : planArray,
				/* barWidth:30, */
				barCategoryGap : '78%',
				itemStyle : {
					normal : {
						width : 20,
						color : '#89c1d5',
						barBorderWidth : 2,
						barBorderColor : '#228bb0',
						label : {
							show : true,
							textStyle : {
								color : '#000',
								fontSize : '16',
								fontFamily : '微软雅黑',
								fontWeight : 'normal'
							}
						}
					}
				}
			}, {
				name : '执行',
				type : 'bar',
				data : factArray,
				/* barWidth:30, */
				barCategoryGap : '78%',
				itemStyle : {
					normal : {
						color : '#ff9e9e',
						barBorderWidth : 2,
						barBorderColor : '#ff4a4a',
						label : {
							show : true,
							textStyle : {
								color : '#000',
								fontSize : '16',
								fontFamily : '微软雅黑',
								fontWeight : 'normal'
							}
						}
					}
				}
			} ]
		};
		myChart1.setOption(option01);
}

function chart2(planMonth,groupName,max,userArray,planArray,factArray) {
    var ec=window.echarts;
		// ，可单独写出来作为回调函数
		var myChart2 = ec.init(document.getElementById('main02'));
		// 加载图表
		option02 = {
			title : {
				x : 'center',
				y : 'top',
				text : planMonth+'月'+groupName+'各成员销售统计'
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				orient : 'vertical',
				x : 'right',
				y : 'top',
				data : [ '计划', '执行' ]
			},
			calculable : true,
			xAxis : [ {
				type : 'value',
				name : '销售额(万)',
				data : [ , 300, 600, 900, 1200, 1500 ]
			} ],
			yAxis : [ {
				type : 'category',
				name : '成员名称',
				data : userArray
			} ],
			series : [ {
				name : '执行',
				type : 'bar',
				data : factArray,
				/* barWidth:30, */
				barCategoryGap : '50%',
				itemStyle : {
					normal : {
						color : '#ff9e9e',
						barBorderWidth : 2,
						barBorderColor : '#ff4a4a',
						label : {
							show : true,
							textStyle : {
								color : '#000',
								fontSize : '16',
								fontFamily : '微软雅黑',
								fontWeight : 'normal'
							}
						}
					}
				}
			}, {
				name : '计划',
				type : 'bar',
				data : planArray,
				/* barWidth:30, */
				barCategoryGap : '50%',
				/*
				 * markLine : { data : [ {type : 'average', name: '平均值'} ] },
				 */
				itemStyle : {
					normal : {
						width : 20,
						color : '#89c1d5',
						barBorderWidth : 2,
						barBorderColor : '#228bb0',
						label : {
							show : true,
							textStyle : {
								color : '#000',
								fontSize : '16',
								fontFamily : '微软雅黑',
								fontWeight : 'normal'
							}
						}
					}
				}
			}

			]
		};
		try{
		myChart2.setOption(option02);}catch(e){

		}
}
function chart1Fn(){
	$.post(ctx+"/plan/year/progress/findAllGroupByMonth",{"planMonth":month,"planYear":planYear},function(data){
		var groupArray= new Array();
		var planArray= new Array();
		var factArray= new Array();
		var max=0;
		var list = data.plans;
		for(var i=0;i<list.length;i++){
			groupArray.push(list[i].groupName);
			planArray.push(list[i].planMoney==0?'':list[i].planMoney);
			factArray.push(list[i].factMoney==0?'':list[i].factMoney);
			if(max<list[i].planMoney) max=list[i].planMoney;
			if(max<list[i].factMoney) max=list[i].factMoney;
		}

		if(planArray.length==0||factArray.length==0||groupArray.length==0){
			groupArray = new Array();
			for(var i=0;i<data.groups.length;i++){
				groupArray.push(data.groups[i].groupName);
			}
			planArray = [''];
			factArray = [''];
		}

		chart1(month,max,groupArray,planArray,factArray,data.groups);
	});
}

function chart2Fn(){
	$.post(ctx+"/plan/year/progress/findAllUserPlanByGroupAndMonth",{"groupId":selGroupId,"planYear":planYear,"planMonth":month},function(data){
		var userArray= new Array();
		var planArray= new Array();
		var factArray= new Array();
		var max=0;
		var list = data.plans;
		for(var i=0;i<list.length;i++){
			userArray.push(list[i].userName);
			planArray.push(list[i].planMoney==0?'':list[i].planMoney);
			factArray.push(list[i].factMoney==0?'':list[i].factMoney);

			if(max<list[i].planMoney){
				max=list[i].planMoney;
			}

			if(max<list[i].factMoney){
				max=list[i].factMoney;
			}
		}

		if(planArray.length==0||factArray.length==0||userArray.length==0){
			userArray = new Array();
			for(var i=0;i<data.users.length;i++){
				userArray.push(data.users[i].userName);
			}
			planArray = [''];
			factArray = [''];
		}

		chart2(month,selGroupName,max,userArray,planArray,factArray);
	});
}
