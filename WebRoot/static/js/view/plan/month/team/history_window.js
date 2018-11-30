var dateArray=new Array();
var pMoneyArray =new Array();
var fMoneyArray =new Array();
var pSignArray =new Array();
var fSignArray =new Array();
var pWillArray =new Array();
var fWillArray =new Array();
var moneyMax=0;
var willMax=0;
var signMax=0;
var danwei="个";
function chart(){
	var type=$("select").val();
	pArray=null;
	fArray=null;
	title=null;
	max = 0;
	if("will"==type){
		pArray=pWillArray;
		fArray=fWillArray;
		max=willMax;
		title="新增意向数";
		danwei="个";
	}else if("sign"==type){
		pArray=pSignArray;
		fArray=fSignArray;
		max=signMax;
		title="新增签约数";
		danwei="个";
	}else if("money"==type){
		pArray=pMoneyArray;
		fArray=fMoneyArray;
		max=moneyMax;
		title="回款金额";
		danwei="万";
	}
	
	if(dateArray.length==0||pArray.length==0||fArray.length==0){
		dateArray = [''];
		pArray = [{value:'',itemStyle:{normal:{label:{show:true,formatter:''}}}}];
		fArray = [{value:'',itemStyle:{normal:{label:{show:true,formatter:''}}}}];
	}
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
                        boundaryGap :true,
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
	var groupId=$("#groupId").val();
	$.post(ctx+"/plan/month/team/historyJson",{"fromTime":fromTime,"toTime":toTime,"groupId":groupId},function(list){
		dateArray=new Array();
		pMoneyArray =new Array();
		fMoneyArray =new Array();
		pSignArray =new Array();
		fSignArray =new Array();
		pWillArray =new Array();
		fWillArray =new Array();
		moneyMax=0;
		willMax=0;
		signMax=0;
		for(var i=0;i<list.length;i++){
			
			dateArray.push(formatDate(new Date(list[i].planYear,list[i].planMonth-1,1),6));
			
			if(list[i].planMoney>moneyMax) moneyMax = list[i].planMoney;
			if(list[i].factMoney>moneyMax) moneyMax = list[i].factMoney;
			
			if(list[i].planSigncustnum>signMax) signMax = list[i].planSigncustnum;
			if(list[i].factSigncustnum>signMax) signMax = list[i].factSigncustnum;
			
			if(list[i].planWillcustnum>willMax) willMax = list[i].planWillcustnum;
			if(list[i].factWillcustnum>willMax) willMax = list[i].factWillcustnum;
			
			pMoneyArray.push({value:list[i].planMoney,itemStyle:{normal:{label:{show:true,formatter:list[i].planMoney.toString()}}}});
			fMoneyArray.push({value:list[i].factMoney,itemStyle:{normal:{label:{show:true,formatter:list[i].factMoney.toString()}}}});
			
			pSignArray.push({value:list[i].planSigncustnum,itemStyle:{normal:{label:{show:true,formatter:list[i].planSigncustnum.toString()}}}});
			fSignArray.push({value:list[i].factSigncustnum,itemStyle:{normal:{label:{show:true,formatter:list[i].factSigncustnum.toString()}}}});
			
			pWillArray.push({value:list[i].planWillcustnum,itemStyle:{normal:{label:{show:true,formatter:list[i].planWillcustnum.toString()}}}});
			fWillArray.push({value:list[i].factWillcustnum,itemStyle:{normal:{label:{show:true,formatter:list[i].factWillcustnum.toString()}}}});
		}
		chart();
	});
}

function pickerTime(){
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	if(fromDate!=null &&toDate !=null &&fromDate!='' &&toDate !=''){
		chartFn(new Date(fromDate).getTime(),new Date(toDate).getTime());
	}
}

