$(function(){
	var isDouble = $("#isDouble").val();
	var customReportId = $("#customReportId").val();
	if(isDouble == 1){
		getSingleData(customReportId)
	}else{
		getMultiData(customReportId)
	}
	$(".hover-see-detail").hover(function(){
		$(this).find(".hover-see-detail-box").show();
	},function(){
		$(this).find(".hover-see-detail-box").hide();
	})
})

function picData(option){
	var myChart = echarts.init(document.getElementById('main01'));
	myChart.setOption(option);
}

function getSingleData(customReportId){
	$.ajax({
		url:ctx+"/custom/report/getSingleReportDatailJson",
		data:{
			customReportId:customReportId
		},
		success:function(data){
			singleDataChange(data)
		}
	})
}

function getMultiData(customReportId){
	$.ajax({
		url:ctx+"/custom/report/getDoutbleReportDatailJson",
		data:{
			customReportId:customReportId
		},
		success:function(data){
			multiDataChange(data)
		}
	})
}

function singleDataChange(data){
	var xArr = [];
	var dataArr = [];
	var barWidth = 0;
	if(data.length >= 0){
		for(var i = 0 , len = data.length ; i < len ; i++){
			xArr.push(data[i].singleName);
			dataArr.push(data[i].count);
		}
		
		var option = {
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        top: '10%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            axisLabel:{
			            	formatter:function(value){
			            		if(value.length > 20){
			            			return value.substring(0,20)+"...";
			            		}else{
			            			return value;
			            		}
			            	}
					    },
			            data : xArr,
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            type:'bar',
			            data:dataArr
			        }
			    ]
			};
		singleRenderTable(data)
		picData(option);
	}
}
function multiDataChange(data){
	var chartData = takeMultiDataUnion(data)
	/*var customReportName = $("#customReportName").val();*/
	var xArr = [];
	if(data.length >= 0){
		for(var i = 0 , len = data.length ; i < len ; i++){
			var lists = data[i].list;
			xArr.push(data[i].groupByName);
		}
		
		var option = {
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        top: '3%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            axisLabel:{
			            	formatter:function(value){
			            		if(value.length > 20){
			            			return value.substring(0,20)+"...";
			            		}else{
			            			return value;
			            		}
			            	}
					    },
			            data : xArr,
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series :chartData
			};
		picData(option);
	}
	multiRenderData(chartData,data);
}

function takeMultiDataUnion(data){
	var nameArr = [];
	for(var i = 0 , len = data.length ; i < len ; i ++){
		var curr = data[i];
		for(var j = 0 , lengs = curr.list.length ; j < lengs ; j++){
			var item = curr.list[j];
			nameArr.push(item.singleName)
		}
	}
	//数组去重
	var tmp = {};
	var ret = [];
	for(var i = 0 , len = nameArr.length ; i< len ; i++){
		if(!tmp[nameArr[i]]){
			tmp[nameArr[i]] = 1 ;
			ret.push(nameArr[i]);
		}
	}
	var returnArr = integratedMultiData(ret,data)
	return returnArr;
}

function integratedMultiData(ret,data){
	var json = {};
	for(var j = 0 , lengs = ret.length ; j < lengs ; j++){
		json[ret[j]] = [];
	}
	for(var i = 0 , len = data.length ; i < len ; i++){
		var curr =data[i];
		for(var m = 0 ; m < curr.list.length ; m++){
			var item = curr.list[m];
			for(var k in json ){
				if(item.singleName  == k){
					json[k].push(item.count);
				}
			}
		}
		//每次完成添加上数据后为没有的项目补0
		for(var a in json){
			if(!json[a][i]){
				json[a].push(0);
			}
		}
	}
	var returnArr = [];
	for(var b in json ){
		var returnItem = {};
		returnItem.name = b;
		returnItem.type="bar";
		returnItem.data = json[b];
		returnArr.push(returnItem);
	}
	return returnArr
}

function singleRenderTable(data){
	var total = 0;
	var thHtml ="<tr class='sty-bgcolor-b'><th><span class='sp sty-borcolor-b'>统计条目</span></th>";
	var tdHtml = "<tr><td>数量</td>";
	for(var i = 0 ; i < data.length ; i++){
		total +=Number(data[i].count)
		thHtml += "<th><span class='sp sty-borcolor-b' title='"+data[i].singleName+"'>"+data[i].singleName+"</span></th>";
		tdHtml += "<td>"+data[i].count+"</td>";
	}
	thHtml += "<th><span class='sp sty-borcolor-b'>合计</span></th></tr>";
	tdHtml += "<td>"+total+"</td></tr>";
	$(".ajax-table").find("thead").html(thHtml);
	$(".ajax-table").find("tbody").html(tdHtml);
}
function multiRenderData(takedata,data){
	var tableData = addTotalTable(takedata,data)
	var thHtml ="<tr class='sty-bgcolor-b'><th><span class='sp sty-borcolor-b'>统计条目</span></th>";
	var tdHtml = "";
	for(var i = 0 ; i < data.length ; i++){
		thHtml += "<th><span class='sp sty-borcolor-b' title='"+data[i].groupByName+"'>"+data[i].groupByName+"</span></th>";
	}
	thHtml += "<th><span class='sp sty-borcolor-b'>合计</span></th></tr>";
	for(var j = 0 ; j < tableData.length ; j ++){
		var item = tableData[j];
		tdHtml +="<tr "+(j%2 != 0 ? "class='sty-bgcolor-b'":"" )+"><td><div class='w100 overflow_hidden' title='"+item.name+"'>"+item.name+"</div></td>";
		for( k = 0 ; k < item.data.length ; k++){
			var curr = item.data[k];
			tdHtml += "<td>"+curr+"</td>";
		}
		tdHtml += "</tr>";
	}
	$(".ajax-table").find("thead").html(thHtml);
	$(".ajax-table").find("tbody").html(tdHtml);
}
function addTotalTable(data,oldData){
	//先用原始数据获取纵向的合计
	var json = {name:"合计",data:[]};
	for(var i = 0 ; i < oldData.length ; i++){
		var item = oldData[i].list;
		var rowsTotal = 0;
		for(var k = 0 ; k < item.length ; k++){
			var curr = item[k];
			rowsTotal += Number(curr.count);
		}
		json.data.push(rowsTotal)
	}
	//加入已处理好的数据
	data.push(json);
	//获取横向的合计。总合计也就自然出现
	for(var i = 0 ; i < data.length ; i ++){
		var colsitem = data[i].data;
		var colsTotal = 0 ;
		for(var j = 0 ; j < colsitem.length ; j ++){
			var currItem = colsitem[j]
			colsTotal += Number(currItem);
		}
		colsitem.push(colsTotal);
	}
	return data;
}
