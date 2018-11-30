var ec=echarts
function custsLayout(groupId){

	if($('#main01-nodata').length > 0){
		$('#main01-nodata').parent().removeAttr('style');
		$('#main01-nodata').parent().html('<div class="tip-box" id="main" style="height:300px;"></div>');
	}
		var myChart = ec.init(document.getElementById('main'),{noDataLoadingOption:{text: '暂无数据',effect:'bubble',effectOption : {backgroundColor:'#F4F4F4',effect: {n: 0}},textStyle: {fontSize: 15}}});
		var myChartOption = {
				title : {
					text : '',
					subtext : '',
					x : 'center'
				},
				tooltip : {
					trigger : 'item',
					formatter : "{b} : {c} 个 ({d}%)"
				},
				series : [ {
					type : 'pie',
					radius : '65%',
					center : [ '50%', '48%' ]
				} ]
		};
		$.ajax({
			url : ctx + '/layout/user/custStateChart',
			type : 'post',
			data : {groupId:groupId},
			dataType : 'json',
			error : function() {
			},
			success : function(data) {
				var resNum = data.resNum;
				var intNum = data.custNum;
				var signNum = data.signNum;
				var silentNum = data.silentNum;
				var losingNum = data.losingNum;
				var allNum = data.allNum;
//				var poolNum = data.poolNum;
				var array = new Array();
				if (resNum > 0) {
					var obj = new Object();
					obj.name = "资源";
					obj.value = resNum;
					obj.status = "1";
					array.push(obj);
				}
				if (intNum > 0) {
					var obj = new Object();
					obj.name = "意向客户";
					obj.value = intNum;
					obj.status = "2";
					array.push(obj);
				}
				if (signNum > 0) {
					var obj = new Object();
					obj.name = "签约客户";
					obj.value = signNum;
					obj.status = "3";
					array.push(obj);
				}
				if (silentNum > 0) {
					var obj = new Object();
					obj.name = "沉默客户";
					obj.value = silentNum;
					obj.status = "4";
					array.push(obj);
				}
				if (losingNum > 0) {
					var obj = new Object();
					obj.name = "流失客户";
					obj.value = losingNum;
					obj.status = "5";
					array.push(obj);
				}
				myChartOption.series[0].data = array;
				if(myChartOption.series[0].data.length == 0){
					$('#main').parent().attr('style','text-align:center');
	    			$('#main').parent().html('<span id="main01-nodata" class="index-no-data">暂无数据</span>');
				}else{
					myChart.setOption(myChartOption);
				}
				if($(".hyx-silent-p").length > 0){
					myChart.on("click",function(param){
						window.parent.$("li:eq(1)").addClass("select").siblings("li").removeClass("select");
						window.parent.$("#iframepage").attr("src",ctx+"/layout/team/custStatusReport?groupId="+groupId+"&statusStr="+param.data.status);
					});
				}
				$("#allCustNum").text((intNum+signNum+silentNum+losingNum+resNum)+"个");
				$("#intCustNum").text(intNum+"个");
				$("#signCustNum").text(signNum+"个");
				$("#silentCustNum").text(silentNum+"个");
				$("#losingCustNum").text(losingNum+"个");
				$("#poolCustNum").text(resNum+"个");
			}
		});
}

function saleProcLayout(groupId){
	if($('#main02-nodata').length > 0){
		$('#main02-nodata').parent().removeAttr('style');
		$('#main02-nodata').parent().html('<div id="main02" class="team-custom-circle02"></div>');
	}
		var myChart2 = ec.init(document.getElementById('main02'),{noDataLoadingOption:{text: '暂无数据',effect:'bubble',effectOption : {backgroundColor:'#F4F4F4',effect: {n: 0}},textStyle: {fontSize: 15}}});
		// 环形图
		var option02 = {
			title : {
				x : 'center',
				y : 'center',
				itemGap : 10,
				textStyle : {
					color : '#000',
					fontFamily : '微软雅黑',
					fontSize : 12,
					fontWeight : 'normal'
				},
				subtextStyle : {
					color : '#015f9f',
					fontSize : 16
				}
			},
			tooltip : {
				trigger : 'item',
				formatter : "{b} : {c} 个 ({d}%)"
			},
			calculable : false,
			series : [ {
				type : 'pie',
				radius : [ '40%', '70%' ],
				center:['50%','50%'],
				itemStyle : {
					normal : {
						label : {
							show : true,
							textStyle : {
								fontSize : '14',
								color : '#000'
							},
							subtextStyle : {
								color : '#015f9f',
								fontSize : 16
							},

							formatter : function(params) {
								return params.name
										+ (params.percent - 0).toFixed(1) + '%'
							}
						},
						labelLine : {
							show : true,
							length : 15
						}
					},
					emphasis : {
						label : {
							show : false,
							position : 'center',
							textStyle : {
								color : '#000',
								fontSize : '12'
							},
							subtextStyle : {
								color : '#015f9f',
								fontSize : 16
							}
						}
					}
				}
			} ]
		};
		$.ajax({
			url:ctx+'/layout/user/saleProcChart',
			type:'post',
			data:{groupId:groupId},
			dataType:'json',
			error:function(){},
			success:function(data){
				option02.series[0].data = data.data;
				if(option02.series[0].data.length == 0){
					$('#main02').parent().attr('style','text-align:center');
	    			$('#main02').parent().html('<span id="main02-nodata" class="index-no-data">暂无数据</span>');
				}else{
					myChart2.setOption(option02);
				}
				if($(".hyx-silent-p").length > 0){
					myChart2.on("click",function(param){
						window.parent.$("li:eq(2)").addClass("select").siblings("li").removeClass("select");
						window.parent.$("#iframepage").attr("src",ctx+"/layout/team/saleProcReport?groupId="+groupId+"&optionlistIds="+param.data.optionlistId);
					});
				}
				var totalNum = data.totalNum;
				var html = '<li class="clearfix">'
	                       +'   <span class="sales-proce-title fl_l">销售进程</span>'
	                       +'   <span class="sales-proce-title fl_l">客户数量（个）</span>'
	                       +'   <span class="sales-proce-title fl_l">占比（%）</span>'
	                       +'</li>';
				$.each(data.list,function(idx,obj){
					html+='<li class="clearfix">'
			              +'  <span class="sales-proce-text fl_l overflow_hidden" title="'+obj.optionName+'">'+obj.optionName+'</span>'
			              +'  <span class="sales-proce-text fl_l overflow_hidden" title="'+obj.custNums+'">'+obj.custNums+'</span>'
			              +'  <span class="sales-proce-text fl_l overflow_hidden" title="'+(data.totalNum == 0 ? '0' : (obj.custNums/data.totalNum*100).toFixed(1))+'%">'+(data.totalNum == 0 ? '0' : (obj.custNums/data.totalNum*100).toFixed(1))+'%</span>'
			             +'</li>'
				});
				$("#saleProcUl").html(html);
			}
		});
}

function custTypeLayout(groupId){
	if($('#main03-nodata').length > 0){
		$('#main03-nodata').parent().removeAttr('style');
		$('#main03-nodata').parent().html('<div id="main03" class="team-custom-circle03"></div>');
	}
		var myChart3 = ec.init(document.getElementById('main03'),{noDataLoadingOption:{text: '暂无数据',effect:'bubble',effectOption : {backgroundColor:'#F4F4F4',effect: {n: 0}},textStyle: {fontSize: 15}}});
		// 环形图
		var option03 = {
			title : {
				x : 'center',
				y : 'center',
				itemGap : 10,
				textStyle : {
					color : '#000',
					fontFamily : '微软雅黑',
					fontSize : 12,
					fontWeight : 'normal'
				},
				subtextStyle : {
					color : '#015f9f',
					fontSize : 16
				}
			},
			tooltip : {
				trigger : 'item',
				formatter : "{b} : {c} 个 ({d}%)"
			},
			calculable : false,
			series : [ {
				type : 'pie',
				radius :[ '40%', '70%' ],
				center:['50%','48%'],
				itemStyle : {
					normal : {
						label : {
							show : true,
							textStyle : {
								fontSize : '14',
								color : '#000'
							},
							subtextStyle : {
								color : '#015f9f',
								fontSize : 16
							},

							formatter : function(params) {
								return params.name
										+ (params.percent - 0).toFixed(1) + '%'
							}
						},
						labelLine : {
							show : true,
							length : 15
						}
					},
					emphasis : {
						label : {
							show : false,
							position : 'center',
							textStyle : {
								color : '#000',
								fontSize : '12'
							},
							subtextStyle : {
								color : '#015f9f',
								fontSize : 16
							}
						}
					}
				}
			} ]
		};
		$.ajax({
			url:ctx+'/layout/user/custTypeChart',
			type:'post',
			data:{groupId:groupId},
			dataType:'json',
			error:function(){},
			success:function(data){
				option03.series[0].data = data.data;
				if(option03.series[0].data.length == 0){
					$('#main03').parent().attr('style','text-align:center');
	    			$('#main03').parent().html('<span id="main03-nodata" class="index-no-data">暂无数据</span>');
				}else{
					myChart3.setOption(option03);
				}
				if($(".hyx-silent-p").length > 0){
					myChart3.on("click",function(param){
						window.parent.$("li:eq(3)").addClass("select").siblings("li").removeClass("select");
						window.parent.$("#iframepage").attr("src",ctx+"/layout/team/custTypeReport?groupId="+groupId+"&optionlistIds="+param.data.optionlistId);
					});
				}
				var totalNum = data.totalNum;
				var html = '<li class="clearfix">'
	                       +'   <span class="sales-proce-title fl_l">客户类型</span>'
	                       +'   <span class="sales-proce-title fl_l">客户数量（个）</span>'
	                       +'   <span class="sales-proce-title fl_l">占比（%）</span>'
	                       +'</li>';
				$.each(data.list,function(idx,obj){
					html+='<li class="clearfix">'
			              +'  <span class="sales-proce-text fl_l overflow_hidden" title="'+obj.optionName+'">'+obj.optionName+'</span>'
			              +'  <span class="sales-proce-text fl_l overflow_hidden" title="'+obj.custNums+'">'+obj.custNums+'</span>'
			              +'  <span class="sales-proce-text fl_l overflow_hidden" title="'+(data.totalNum == 0 ? '0' : (obj.custNums/data.totalNum*100).toFixed(1))+'%">'+(data.totalNum == 0 ? '0' : (obj.custNums/data.totalNum*100).toFixed(1))+'%</span>'
			             +'</li>'
				});
				$("#custTypeUl").html(html);
			}
		});
}

function productLayout(groupId){
	if($('#main04-nodata').length > 0){
		$('#main04-nodata').parent().removeAttr('style');
		$('#main04-nodata').parent().html('<div id="main04" class="team-custom-circle04"></div>');
	}
		var myChart4 = ec.init(document.getElementById('main04'),{noDataLoadingOption:{text: '暂无数据',effect:'bubble',effectOption : {backgroundColor:'#F4F4F4',effect: {n: 0}},textStyle: {fontSize: 15}}});
		// 环形图
		var option04 = {
			title : {
				x : 'center',
				y : 'center',
				itemGap : 10,
				textStyle : {
					color : '#000',
					fontFamily : '微软雅黑',
					fontSize : 12,
					fontWeight : 'normal'
				},
				subtextStyle : {
					color : '#015f9f',
					fontSize : 16
				}
			},
			tooltip : {
				trigger : 'item',
				formatter : "{b} : {c} 个 ({d}%)"
			},
			calculable : false,
			series : [ {
				type : 'pie',
				radius : [ '40%', '70%' ],
				center:['50%','48%'],
				itemStyle : {
					normal : {
						label : {
							show : true,
							textStyle : {
								fontSize : '14',
								color : '#000'
							},
							subtextStyle : {
								color : '#015f9f',
								fontSize : 16
							},

							formatter : function(params) {
								return params.name
										+ (params.percent - 0).toFixed(1) + '%'
							}
						},
						labelLine : {
							show : true,
							length : 15
						}
					},
					emphasis : {
						label : {
							show : false,
							position : 'center',
							textStyle : {
								color : '#000',
								fontSize : '12'
							},
							subtextStyle : {
								color : '#015f9f',
								fontSize : 16
							}
						}
					}
				}
			} ]
		};
		$.ajax({
			url:ctx+'/layout/user/productChart',
			type:'post',
			data:{groupId:groupId},
			dataType:'json',
			error:function(){},
			success:function(data){
				option04.series[0].data = data.data;
				if(option04.series[0].data.length == 0){
					$('#main04').parent().attr('style','text-align:center');
	    			$('#main04').parent().html('<span id="main04-nodata" class="index-no-data">暂无数据</span>');
				}else{
					myChart4.setOption(option04);
				}
				if($(".hyx-silent-p").length > 0){
					myChart4.on("click",function(param){
						window.parent.$("li:eq(4)").addClass("select").siblings("li").removeClass("select");
						window.parent.$("#iframepage").attr("src",ctx+"/layout/team/productReport?groupId="+groupId+"&productIds="+param.data.productId);
					});
				}
				var totalNum = data.totalNum;
				var html = '<li class="clearfix">'
	                       +'   <span class="sales-proce-title fl_l">产品类型</span>'
	                       +'   <span class="sales-proce-title fl_l">客户数量（个）</span>'
	                       +'   <span class="sales-proce-title fl_l">占比（%）</span>'
	                       +'</li>';
				$.each(data.list,function(idx,obj){
					html+='<li class="clearfix">'
			              +'  <span class="sales-proce-text fl_l overflow_hidden" title="'+obj.productName+'">'+obj.productName+'</span>'
			              +'  <span class="sales-proce-text fl_l overflow_hidden" title="'+obj.custNums+'">'+obj.custNums+'</span>'
			              +'  <span class="sales-proce-text fl_l overflow_hidden" title="'+(data.totalNum == 0 ? '0' : (obj.custNums/data.totalNum*100).toFixed(1))+'%">'+(data.totalNum == 0 ? '0' : (obj.custNums/data.totalNum*100).toFixed(1))+'%</span>'
			             +'</li>'
				});
				$("#productUl").html(html);
			}
		});
}

function changeGroup(groupId){
	custsLayout(groupId);
	saleProcLayout(groupId);
	custTypeLayout(groupId);
	productLayout(groupId);
}