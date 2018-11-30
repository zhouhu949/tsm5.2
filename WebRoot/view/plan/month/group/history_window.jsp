<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-月度计划（团队编辑）-查看页面（已执行）-历史计划走势</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
<style type="text/css">
.echarts-tooltip{width:150px;overflow:hidden;word-wrap:break-word;word-break:normal;white-space:normal !important;}
</style>
<script type="text/javascript" src="${ctx}/static/js/my97datepicker/WdatePicker.js"></script><!--选择年月日期插件-->
<script type="text/javascript" src="${ctx}/static/js/view/worklog/dateUtils.js"></script>

</head>
<body> 
<div class='bomp-cen bomp-aspa'>
	<p class="top fl_l">
        <input type="text" value="" class="sel fl_l" onclick="WdatePicker({minDate:'%y-%M',dateFmt:'yyyy-MM',isShowClear:false,readOnly:true})" />
		<span class="line fl_l"></span>
		<input type="text" value="" class="sel fl_l" onclick="WdatePicker({minDate:'%y-%M',dateFmt:'yyyy-MM',isShowClear:false,readOnly:true})" />
		<select class="sel fl_l" style="margin-left:50px;">
            <option value="will">新增意向数</option>
            <option value="sign">新增签约数</option>
			<option value="money">回款金额</option>
		</select>
	</p>
	<div class="tip fl_l">
		<div class="tip-box" id="main" style="width:886px;height:320px;"></div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/thirdparty/echarts-3.3.2/echarts.min.js"></script>
<script type="text/javascript" type="text/javascript">
$(function(){
	$("select").change(function(){
		var type=$(this).val();
		pArray=null;
		fArray=null;
		if("will"==type){
			pArray=pWillArray;
			fArray=fWillArray;
		}else if("sign"==type){
			pArray=pSignArray;
			fArray=fSignArray;
		}else if("money"==type){
			pArray=pMoneyArray;
			fArray=fMoneyArray;
		}
		myChart.setOption({
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
			//$("form:first").submit();
		}		
	);
});

var list=${plansJson};

var dateArray=new Array();
var pMoneyArray =new Array();
var fMoneyArray =new Array();
var pSignArray =new Array();
var fSignArray =new Array();
var pWillArray =new Array();
var fWillArray =new Array();
for(var i=0;i<list.length;i++){
	//planMoney    				factMoney
	//planSigncustnum			factSigncustnum
	//planWillcustnum			factWillcustnum
	dateArray.push(formatDate(new Date(list[i].planYear,list[i].planMonth-1,1),6));
	
	pMoneyArray.push({value:list[i].planMoney,itemStyle:{normal:{label:{show:true,formatter:list[i].planMoney.toString()}}}});
	fMoneyArray.push({value:list[i].factMoney,itemStyle:{normal:{label:{show:true,formatter:list[i].factMoney.toString()}}}});
	
	pSignArray.push({value:list[i].planSigncustnum,itemStyle:{normal:{label:{show:true,formatter:list[i].planSigncustnum.toString()}}}});
	fSignArray.push({value:list[i].factSigncustnum,itemStyle:{normal:{label:{show:true,formatter:list[i].factSigncustnum.toString()}}}});
	
	
	pWillArray.push({value:list[i].planWillcustnum,itemStyle:{normal:{label:{show:true,formatter:list[i].planWillcustnum.toString()}}}});
	fWillArray.push({value:list[i].factWillcustnum,itemStyle:{normal:{label:{show:true,formatter:list[i].factWillcustnum.toString()}}}});
}
	var ec=echarts
            myChart = ec.init(document.getElementById('main'));
            myChart.setOption({
                title : {
                    text: '新增意向数',
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
                        	//['1月','2月', '3月', '4月', '5月', '6月']
                    }
                ],
                yAxis : [
                    {
                    	name:'单位（个）',
                        type: 'value',
                        splitNumber: 10,
                        min: 0,
                        max: 900,
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
                    data:pWillArray
                   /*  [
                      {value:800,itemStyle:{normal:{label:{show:true,formatter:'800'}}}},
                      {value:90,itemStyle:{normal:{label:{show:true,formatter:'90'}}}},
                      {value:150,itemStyle:{normal:{label:{show:true,formatter:'150'}}}},
                      {value:90,itemStyle:{normal:{label:{show:true,formatter:'90'}}}},
                      {value:200,itemStyle:{normal:{label:{show:true,formatter:'200'}}}},
                      {value:316,itemStyle:{normal:{label:{show:true,formatter:'316'}}}}
                    ] */
                },
                {
                    name:'执行',
                    type:'line',
                    smooth: true,
                    itemStyle:{normal:{color:'#05b441'}},
                    data:fWillArray
                   	/* [
                      {value:950,itemStyle:{normal:{label:{show:true,formatter:'950'}}}},
                      {value:200,itemStyle:{normal:{label:{show:true,formatter:'200'}}}},
                      {value:290,itemStyle:{normal:{label:{show:true,formatter:'290'}}}},
                      {value:150,itemStyle:{normal:{label:{show:true,formatter:'150'}}}},
                      {value:90,itemStyle:{normal:{label:{show:true,formatter:'90'}}}},
                      {value:916,itemStyle:{normal:{label:{show:true,formatter:'916'}}}}
                    ] */
                }
                ]
            });
            window.onresize = myChart.resize;

</script>
</body>
</html>