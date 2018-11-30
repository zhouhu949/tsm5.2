<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>月度计划--分析</title>
    <script type="text/javascript" src="${ctx}/static/thirdparty/echarts-3.3.2/echarts.min.js"></script>


</head>

<body>
    <div class="month-anal-chart">
        <div class="hyx-anal-p">
            <label class="lab sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">资源分析</label>
        </div>
        <div class="anal-deta-contain clearfix">
            <div class="anal-deta-memory">
                <p>
                    <label for="">回款金额：</label><span class="num-inte"><fmt:formatNumber value="${dto.currentMonthAnaly.signMoney}" pattern="0.##"> </fmt:formatNumber></span><span class="num-deci">万</span>
                </p>
                <p>
                    <label for="">通话时长：</label><span class="num-inte"><fmt:formatNumber value="${dto.currentMonthAnaly.callTime}" pattern="0.##"></fmt:formatNumber></span><span class="num-deci">分钟</span>
                </p>
                <p>
                    <label for="">每百个资源回款金额：</label>
                    <c:choose>
                    <c:when test="${dto.currentMonthAnaly.resNum != 0 }">
                    <span class="num-inte"><fmt:formatNumber value="${dto.currentMonthAnaly.signMoney/(dto.currentMonthAnaly.resNum<100?100:dto.currentMonthAnaly.resNum)*100}" pattern="0.##"> </fmt:formatNumber></span><span class="num-deci">万</span>
                    </c:when>
                    <c:otherwise><span class="num-inte">无数据 </span></c:otherwise>
                    </c:choose>
                    
                </p>
                <p>
                    <label for="">每百个资源通话时长：</label>
                    <c:choose>
                    <c:when test="${dto.currentMonthAnaly.resNum != 0 }">
                    <span class="num-inte"><fmt:formatNumber value="${dto.currentMonthAnaly.callTime/(dto.currentMonthAnaly.resNum<100?100:dto.currentMonthAnaly.resNum)*100}" pattern="0.##"> </fmt:formatNumber></span><span class="num-deci">分钟</span>
                    </c:when>
                    <c:otherwise><span class="num-inte">无数据 </span></c:otherwise>
                    </c:choose>
                </p>
            </div>
            <div class="anal-deta-charts">
                <div id="main01" class="anal-deta-funcle"></div>
            </div>
        </div>
        <div class="hyx-anal-p">
            <label class="lab sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">客户分析</label>
        </div>
        <div class="anal-deta-contain clearfix">
            <div class="anal-deta-charts">
                <div id="main02" class="anal-deta-funcle02"></div>
            </div>
            <div class="anal-deta-charts02">
                <div id="main06" class="anal-deta-funcle02"></div>
            </div>
            <div class="anal-deta-charts05">
                <div id="main07" class="anal-deta-funcle02"></div>
            </div>
            <div class="anal-deta-charts03">
                <div id="main03" class="anal-deta-funcle03"></div>
            </div>
        </div>
        <div class="hyx-anal-p">
            <label class="lab sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">收入分析</label>
        </div>
        <div class="anal-deta-contain02 clearfix">
            <div class="anal-deta-charts04">
                <div id="main04" class="anal-deta-funcle02"></div>
            </div>
            <div class="anal-deta-charts">
                <div id="main05" class="anal-deta-funcle03"></div>
            </div>
        </div>
        <div class="hyx-anal-p" style="border-bottom:none">
            <label class="lab sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">小组分析</label>
        </div>
        <div class="com-table hyx-mpe-table hyx-cm-table">
        <table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
            <thead>
                <tr class="sty-bgcolor-b">
                    <th><span class="sp sty-borcolor-b">小组名称</span></th>
                    <th><span class="sp sty-borcolor-b">总消耗资源（个）</span></th>
                    <th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
                    <th><span class="sp sty-borcolor-b">流失客户数（个）</span></th>
                    <!-- <th><span class="sp sty-borcolor-b">流失率（%）</span></th> -->
                    <th>总计费时长（分钟）</th>
                </tr>
            </thead>
            <tbody>
            <c:choose>
				<c:when test="${!empty list}">
					<c:forEach var="bean" items="${list}" varStatus="i">
						<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
						<c:if test="${i.index%2==0}"><tr></c:if>
						<td><div class="overflow_hidden w70" title="${bean.groupName}">${bean.groupName}</div></td>
	                    <td><div class="overflow_hidden w70" title="${bean.resNum}">${bean.resNum}</div></td>
	                    <td><div class="overflow_hidden w70" title="${bean.signMoney}">${bean.signMoney}</div></td>
	                    <td><div class="overflow_hidden w70" title="${bean.lostCustNum}">${bean.lostCustNum}</div></td>
	                    <!-- <td><div class="overflow_hidden w120" title=""></div></td> -->
	                    <td><div class="overflow_hidden w200" title="${bean.chargTime}">${bean.chargTime}</div></td>
	                    
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr><td colspan="6" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
				</c:otherwise>
			   	</c:choose>
            </tbody>
        </table>
    </div>
    </div>
    <script type="text/javascript">
    /* var a = 100;
    var b = 80;
    var c = 200; //签约客户总数
    var d = 20; //沉默客户总数
    var e = 5; //流失客户总数
     */
    var ec= echarts;
        var dataStyle = {
            normal: {
                label: {
                    show: false
                },
                labelLine: {
                    show: false
                }
            }
        };
        var placeHolderStyle = {
            normal: {
                color: 'rgba(0,0,0,0)',
                label: {
                    show: false
                },
                labelLine: {
                    show: false
                }
            },
            emphasis: {
                color: 'rgba(0,0,0,0)'
            }
        };


                //加载图表
                option01 = {

                    title: {
                        text: '',
                        subtext: '',
                        x: 'left',
                        y: 'bottom'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b}"
                    },
                    toolbox: {
                        show: false,
                        orient: 'vertical',
                        y: 'center',
                        feature: {
                            mark: {
                                show: true
                            },
                            dataView: {
                                show: true,
                                readOnly: false
                            },
                            restore: {
                                show: true
                            },
                            saveAsImage: {
                                show: true
                            }
                        }
                    },
                    calculable: false,
                    series: [

                        {
                            name: '漏斗图',
                            type: 'funnel',
                            width: '55%',
                            height: '80%',
                            x: '5%',
                            y: '5%',
                            itemStyle: {
                                normal: {
                                    //color: 各异,
                                    label: {
                                        position: 'right',
                                        textStyle: {
                                            fontSize: '12',
                                            color: '#000'
                                        },
                                        subtextStyle: {
                                        color: '#015f9f',
                                        fontSize: 24
                                    }
                                    },
                                    labelLine: {
                                        show: true,
                                        lineStyle: {
                                            color: '#dddddd'

                                        }


                                    }
                                },
                                emphasis: {
                                    label: {
                                        show: true,
                                        position: 'center',
                                        textStyle: {
                                            color:'#000',
                                            fontSize: '12'
                                        },
                                        subtextStyle: {
                                        color: '#015f9f',
                                        fontSize: 24
                                    }
                                    }
                                }
                            },
                            data: [{
                                value: ${dto.currentMonthAnaly.willNum/dto.currentMonthAnaly.resNum*100},
                                name: '转意向：${dto.currentMonthAnaly.willNum}个',
                                itemStyle: {
                                    normal: {
                                        color: '#d09f22',
                                        labelLine: {
                                            length: 40,
                                            lineStyle: {
                                                color: '#d09f22'
                                                    /*width : 3,
                                    type : 'dotted'*/
                                            }
                                        }
                                    }
                                }
                            },
                            {
                                value: 100,
                                name: '联系资源：${dto.currentMonthAnaly.resNum}个',
                                itemStyle: {
                                    normal: {
                                        color: '#1583ec',
                                        labelLine: {
                                            length: 40,
                                            lineStyle: {
                                                color: '#1583ec'
                                            }
                                        }

                                    }
                                }
                            },
                            {
                                value: ${dto.currentMonthAnaly.signNum/dto.currentMonthAnaly.resNum*100},
                                name: '转签约：${dto.currentMonthAnaly.signNum}个',
                                itemStyle: {
                                    normal: {
                                        color: '#f40505',
                                        labelLine: {
                                            length: 40,
                                            lineStyle: {
                                                color: '#f40505'
                                            }
                                        }

                                    }
                                }
                            }
                            ]
                        }
                    ]
                };

                <c:choose>
            	<c:when test="${dto.currentMonthAnaly.newCustSignNum==0 and dto.currentMonthAnaly.oldCustSignNum==0}">
            		<c:set var="newCustSignNum" value="1"/>
    				<c:set var="oldCustSignNum" value="1"/>
            		<c:set var="newCustSignNumColor" value="#e4e4e4"/>
            		<c:set var="oldCustSignNumColor" value="#e4e4e4"/>
            		<c:set var="custSignNumShow" value="false"/>
            	</c:when>
            	<c:otherwise>
            		<c:set var="newCustSignNum" value="${dto.currentMonthAnaly.newCustSignNum}"/>
        			<c:set var="oldCustSignNum" value="${dto.currentMonthAnaly.oldCustSignNum}"/>
	            	<c:set var="newCustSignNumColor" value="#d9a624"/>
            		<c:set var="oldCustSignNumColor" value="#1583ec"/>
            		<c:set var="custSignNumShow" value="true"/>
            	</c:otherwise>
        	</c:choose>

                //环形图
                option02 = {
                    title: {
                        text: '签约次数',
                        subtext: '${dto.currentMonthAnaly.signNum}个',
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
                   		show: ${custSignNumShow},
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'horizontal',
                        x: 'right',
                        y: 'top',
                        data: ['新客户', '老客户']
                    },
                    calculable: false,
                    series: [{
                        //name: '访问来源',
                        type: 'pie',
                        radius: ['66%', '85%'],
                        itemStyle: {
                            normal: {
                                label: {
                                    show: ${custSignNumShow},
                                    textStyle: {
                                        fontSize: '14',
                                        color: '#000'
                                    },
                                    subtextStyle: {
                                        color: '#015f9f',
                                        fontSize: 24
                                    },

                                    formatter: function (params) {
                                        return params.value + '个,' + (params.percent - 0).toFixed(0) + '%'
                                    }
                                },
                                labelLine: {
                                    show:  ${custSignNumShow},
                                    length: 4
                                }
                            },
                            emphasis: {
                                label: {
                                    show: false,
                                    position: 'center',
                                    textStyle: {
                                        color:'#000',
                                        fontSize: '12'
                                    },
                                    subtextStyle: {
                                        color: '#015f9f',
                                        fontSize: 24
                                    }
                                }
                            }
                        },
                        data: [{
                            value: ${newCustSignNum},
                            name: '新客户',
                            itemStyle: {
                                normal: {
                                    color: '${newCustSignNumColor}'
                                }
                            }
                        }, {
                            value: ${oldCustSignNum},
                            name: '老客户',
                            itemStyle: {
                                normal: {
                                    color: '${oldCustSignNumColor}'
                                }
                            }
                        }]
                    }]
                };

                <c:choose>
	            	<c:when test="${dto.currentMonthAnaly.silenceCustNum==0 and dto.lastMonthAnaly.silenceCustNum==0}">
	            		<c:set var="currSilenceCustNum" value="75"/>
        				<c:set var="lastSilenceCustNum" value="45"/>
	            		<c:set var="silenceColor1" value="#e4e4e4"/>
	            		<c:set var="silenceColor2" value="#e4e4e4"/>
	            	</c:when>
	            	<c:otherwise>
	            		<c:set var="currSilenceCustNum" value="${dto.currentMonthAnaly.silenceCustNum}"/>
            			<c:set var="lastSilenceCustNum" value="${dto.lastMonthAnaly.silenceCustNum}"/>
		            	<c:set var="silenceColor1" value="#d9a624"/>
	            		<c:set var="silenceColor2" value="#1583ec"/>
	            	</c:otherwise>
	            </c:choose>

                //环形图
                option06 = {
                    title: {
                        text: '环比增幅',
                        subtext: '${dto.silenceCustPercent}',
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
                        show: false,
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        x: document.getElementById('main03').offsetWidth / 2,
                        y: 0,
                        itemGap: 3,
                        data: ['本月沉默客户：${dto.currentMonthAnaly.silenceCustNum}', '上月沉默客户：${dto.lastMonthAnaly.silenceCustNum}']
                    },
                    toolbox: {
                        show: false,
                        feature: {
                            mark: {
                                show: true
                            },
                            dataView: {
                                show: true,
                                readOnly: false
                            },
                            restore: {
                                show: true
                            },
                            saveAsImage: {
                                show: true
                            }
                        }
                    },
                    series: [{
                        name: '1',
                        type: 'pie',
                        clockWise: false,
                        radius: [60, 73],
                        itemStyle: dataStyle,
                        data: [{
                            value: ${currSilenceCustNum/(currSilenceCustNum>lastSilenceCustNum?currSilenceCustNum:lastSilenceCustNum)*75},
                            name: '本月沉默客户：${dto.currentMonthAnaly.silenceCustNum}',
                            itemStyle: {
                                normal: {
                                    color: '${silenceColor1}'
                                }
                            }
                        }, {
                            value: 25,
                            name: 'invisible',
                            itemStyle: placeHolderStyle
                        }]
                    }, {
                        name: '2',
                        type: 'pie',
                        clockWise: false,
                        radius: [47, 57],
                        itemStyle: dataStyle,
                        data: [{
                            value: ${lastSilenceCustNum/(currSilenceCustNum>lastSilenceCustNum?currSilenceCustNum:lastSilenceCustNum)*75},
                            name: '上月沉默客户：${dto.lastMonthAnaly.silenceCustNum}',
                            itemStyle: {
                                normal: {
                                    color: '${silenceColor2}'
                                }
                            }
                        }, {
                            value: 25,
                            name: 'invisible',
                            itemStyle: placeHolderStyle
                        }]
                    }]
                };


                <c:choose>
            	<c:when test="${dto.currentMonthAnaly.lostCustNum==0 and dto.lastMonthAnaly.lostCustNum==0}">
            		<c:set var="currLostCustNum" value="75"/>
    				<c:set var="lastLostCustNum" value="45"/>
            		<c:set var="lostColor1" value="#e4e4e4"/>
            		<c:set var="lostColor2" value="#e4e4e4"/>
            	</c:when>
            	<c:otherwise>
            		<c:set var="currLostCustNum" value="${dto.currentMonthAnaly.lostCustNum}"/>
        			<c:set var="lastLostCustNum" value="${dto.lastMonthAnaly.lostCustNum}"/>
	            	<c:set var="lostColor1" value="#d9a624"/>
            		<c:set var="lostColor2" value="#1583ec"/>
            	</c:otherwise>
            </c:choose>

                //环形图
                option03 = {
                    title: {
                        text: '环比增幅',
                        subtext: '${dto.lostCustPercent}',
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
                        show: false,
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        x: document.getElementById('main03').offsetWidth / 2,
                        y: 0,
                        itemGap: 3,
                        data: ['本月流失客户：${dto.currentMonthAnaly.lostCustNum}', '上月流失客户：${dto.lastMonthAnaly.lostCustNum}']
                    },
                    toolbox: {
                        show: false,
                        feature: {
                            mark: {
                                show: true
                            },
                            dataView: {
                                show: true,
                                readOnly: false
                            },
                            restore: {
                                show: true
                            },
                            saveAsImage: {
                                show: true
                            }
                        }
                    },
                    series: [{
                        name: '1',
                        type: 'pie',
                        clockWise: false,
                        radius: [60, 73],
                        itemStyle: dataStyle,
                        data: [{
                            value: ${currLostCustNum/(currLostCustNum>lastLostCustNum?currLostCustNum:lastLostCustNum)*75},
                            name: '本月流失客户：${dto.currentMonthAnaly.lostCustNum}',
                            itemStyle: {
                                normal: {
                                    color: '${lostColor1}'
                                }
                            }
                        }, {
                            value: 25,
                            name: 'invisible',
                            itemStyle: placeHolderStyle
                        }]
                    }, {
                        name: '2',
                        type: 'pie',
                        clockWise: false,
                        radius: [47, 57],
                        itemStyle: dataStyle,
                        data: [{
                            value: ${lastLostCustNum/(currLostCustNum>lastLostCustNum?currLostCustNum:lastLostCustNum)*75},
                            name: '上月流失客户：${dto.lastMonthAnaly.lostCustNum}',
                            itemStyle: {
                                normal: {
                                    color:'${lostColor2}'
                                }
                            }
                        }, {
                            value: 25,
                            name: 'invisible',
                            itemStyle: placeHolderStyle
                        }]
                    }]
                };


                <c:choose>
	            	<c:when test="${dto.currentMonthAnaly.signMoney==0 and dto.lastMonthAnaly.signMoney==0}">
	            		<c:set var="currSignMoney" value="75"/>
	    				<c:set var="lastSignMoney" value="45"/>
	            		<c:set var="signMoneyColor1" value="#e4e4e4"/>
	            		<c:set var="signMoneyColor2" value="#e4e4e4"/>
	            	</c:when>
	            	<c:otherwise>
	            		<c:set var="currSignMoney" value="${dto.currentMonthAnaly.signMoney}"/>
	        			<c:set var="lastSignMoney" value="${dto.lastMonthAnaly.signMoney}"/>
		            	<c:set var="signMoneyColor1" value="#d9a624"/>
	            		<c:set var="signMoneyColor2" value="#1583ec"/>
	            	</c:otherwise>
            	</c:choose>

                //环形图
                option04 = {
                    title: {
                        text: '环比增幅',
                        subtext: '${dto.signMoneyPercent}',
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
                        show: false,
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        x: document.getElementById('main03').offsetWidth / 2,
                        y: 0,
                        itemGap: 3,
                        data: ['本月收入：${dto.currentMonthAnaly.signMoney}万', '上月收入：${dto.lastMonthAnaly.signMoney}万']
                    },
                    toolbox: {
                        show: false,
                        feature: {
                            mark: {
                                show: true
                            },
                            dataView: {
                                show: true,
                                readOnly: false
                            },
                            restore: {
                                show: true
                            },
                            saveAsImage: {
                                show: true
                            }
                        }
                    },
                    series: [{
                        name: '1',
                        type: 'pie',
                        clockWise: false,
                        radius: [60, 73],
                        itemStyle: dataStyle,
                        data: [{
                            value: ${currSignMoney/(currSignMoney>lastSignMoney?currSignMoney:lastSignMoney)*75},
                            name: '本月收入：${dto.currentMonthAnaly.signMoney}万',
                            itemStyle: {
                                normal: {
                                    color: '${signMoneyColor1}'
                                }
                            }
                        }, {
                            value: 25,
                            name: 'invisible',
                            itemStyle: placeHolderStyle
                        }]
                    }, {
                        name: '2',
                        type: 'pie',
                        clockWise: false,
                        radius: [47, 57],
                        itemStyle: dataStyle,
                        data: [{
                            value:  ${lastSignMoney/(currSignMoney>lastSignMoney?currSignMoney:lastSignMoney)*75},
                            name: '上月收入：${dto.lastMonthAnaly.signMoney}万',
                            itemStyle: {
                                normal: {
                                    color: '${signMoneyColor2}'
                                }
                            }
                        }, {
                            value: 25,
                            name: 'invisible',
                            itemStyle: placeHolderStyle
                        }]
                    }]
                };

                <c:choose>
	            	<c:when test="${dto.currentMonthAnaly.newCustSignMoney==0 and dto.currentMonthAnaly.oldCustSignMoney==0}">
	            		<c:set var="newCustSignMoney" value="1"/>
	    				<c:set var="oldCustSignMoney" value="1"/>
	            		<c:set var="newCustSignMoneyColor" value="#e4e4e4"/>
	            		<c:set var="oldCustSignMoneyColor" value="#e4e4e4"/>
	            		<c:set var="custSignMoneyShow" value="false"/>
	            	</c:when>
	            	<c:otherwise>
	            		<c:set var="newCustSignMoney" value="${dto.currentMonthAnaly.newCustSignMoney}"/>
	        			<c:set var="oldCustSignMoney" value="${dto.currentMonthAnaly.oldCustSignMoney}"/>
		            	<c:set var="newCustSignMoneyColor" value="#d9a624"/>
	            		<c:set var="oldCustSignMoneyColor" value="#1583ec"/>
	            		<c:set var="custSignMoneyShow" value="true"/>
	            	</c:otherwise>
	        	</c:choose>

                //环形图
                option05 = {
                    title: {
                        text: '回款金额',
                        subtext: '${dto.currentMonthAnaly.signMoney}万',
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
                    	show:${custSignMoneyShow},
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'horizontal',
                        x: 'right',
                        y: 'top',
                        data: ['新客户', '老客户']
                    },
                    calculable: false,
                    series: [{
                        //name: '访问来源',
                        type: 'pie',
                        radius: ['66%', '85%'],
                        itemStyle: {
                            normal: {
                                label: {
                                    show:${custSignMoneyShow},
                                    textStyle: {
                                        fontSize: '14',
                                        color: '#000'
                                    },
                                    subtextStyle: {
                                        color: '#015f9f',
                                        fontSize: 24
                                    },
                                    formatter: function (params) {
                                        return params.value + '个,' + (params.percent - 0).toFixed(0) + '%'
                                    }
                                },
                                labelLine: {
                                    show:${custSignMoneyShow},
                                    length: 4
                                }
                            },
                            emphasis: {
                                label: {
                                    show: false,
                                    position: 'center',
                                    textStyle: {
                                        color:'#000',
                                        fontSize: '12'
                                    },
                                    subtextStyle: {
                                        color: '#015f9f',
                                        fontSize: 24
                                    }
                                }
                            }
                        },
                        data: [{
                            value: ${newCustSignMoney},
                            name: '新客户',
                            itemStyle: {
                                normal: {
                                    color: '${newCustSignMoneyColor}'
                                }
                            }
                        }, {
                            value: ${oldCustSignMoney},
                            name: '老客户',
                            itemStyle: {
                                normal: {
                                    color: '${oldCustSignMoneyColor}'
                                }
                            }
                        }]
                    }]
                };
         var myChart1 = ec.init(document.getElementById('main01'));
         var myChart2 = ec.init(document.getElementById('main02'));
         var myChart3 = ec.init(document.getElementById('main03'));
        var myChart4 = ec.init(document.getElementById('main04'));
        var myChart5 = ec.init(document.getElementById('main05'));
        var myChart6 = ec.init(document.getElementById('main06'));
        myChart1.setOption(option01);
        myChart2.setOption(option02);
        myChart3.setOption(option03);
        myChart4.setOption(option04);
        myChart5.setOption(option05);
        myChart6.setOption(option06);
        window.onload = function () {
            var height = $(".month-anal-chart").height() + 30;
            /*var width = $(".hyx-wlm-cent").width();*/
            /*window.parent.setHeight(height);*/
            window.parent.$("#iframepage").css({ /*"width":width+"px",*/
                "height": height + "px"
            });
        };
    </script>
</body>

</html>