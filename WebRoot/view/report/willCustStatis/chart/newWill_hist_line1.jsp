<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>销售统计-个人统计分析-日统计分析</title>
    <script type="text/javascript">
        window.onload=function(){
            var height = $(".static-analy-line1").height()+30;
            window.parent.$("#iframepage1").css({"height":height+"px"});
        };
    </script>
</head>
<body>
<div class="static-analy-line1" id="main01"></div>
<script type="text/javascript" src="${ctx}/static/js/echarts-3.3.2/echarts.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/dateUtils.js"></script>
<script type="text/javascript" type="text/javascript">
	var list = ${list}; 
 	var items =${item} ;
	var process = "${process}";
	//不选中的数组选择
	var selected = {};
	var notSlectArr=removeByValue(items,process);
	for(var i = 0 ; i < notSlectArr.length ; i++){
		var detail = notSlectArr[i]
		selected[detail] = false;
	}
	selected[process] = true;
	var newlist = bubbleSort(list,true)
	function bubbleSort(array,type){
	    /*给每个未确定的位置做循环*/
	    for(var unfix=array.length-1; unfix>0; unfix--){
	      /*给进度做个记录，比到未确定位置*/
	      for(var i=0; i<unfix;i++){
	        if(array[i].sort>array[i+1].sort){
	          var temp = array[i];
	          array.splice(i,1,array[i+1]);
	          array.splice(i+1,1,temp);
	        }
	      }
	    }
	    if(type){//函数重载判断返回处理数据还是日期的函数
	    	return dataCheck(list);
	    }else{
	   		 return dateCheck(list);
	    }
	  }
	  
	  function dataCheck(list){//为数据的处理函数
		  var tempPro="";
		  var groupjson={};
		  	for(var i = 0 ; i < list.length ; i++){
		  		var item = list[i];
		  		if(tempPro != item.process ){
		  			groupjson[item.process]=[];
		  		}
	  			groupjson[item.process].push(item.returnlist);
		  		tempPro=item.process;
		  	}
		  return groupjson;
	  }
	 function dateCheck(list){//为日期的处理函数
		 var tempPro="";
		   var groupjson={};
		  	for(var i = 0 ; i < list.length ; i++){
		  		var item = list[i];
		  		if(tempPro != item.process ){
		  			groupjson[item.process]=[];
		  		}
	  			groupjson[item.process].push(item.countvalue);
		  		tempPro=item.process;
		  	}
		  	for(var j in groupjson){
		  		return groupjson[j];
		  	}
	 	}
	
	var dataArray=dataComplete(newlist);//数据初始化
	function dataComplete(newlist){//处理data的函数
		var dataArr=[];
		for(var i  in newlist){
			var json={};
			json.name= i ;
			json.type="line";
			json.smooth = true;
			json.data = newlist[i];
			json.label={normal: {show: true,position: 'top'}};
			dataArr.push(json)
		}
		return dataArr;
	}
	
	//日期的数组处理
	var dateArray = [];
	if(list!=null){
		dateArray = bubbleSort(list,false)
		
	}
	
	if(list==null||list.length==0){
		dateArray=[''];
	}
	
	
	var myChart = echarts.init(document.getElementById('main01'));
	option1 = {
	    title: {
	        x:'center',
	        text: '意向客户日分析'
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	    	selected: selected,
	        bottom:'-0',
	        data:items
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '30%',
	        containLabel: true
	    }, 
	  //  grid:{x:45,x2:20,y:40,y2:50},
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : true,
	            data : dateArray
	        } 
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series :dataArray
	};
    myChart.setOption(option1);
    
    
   function removeByValue(arr, val) {
      var reparr= [];
      for(var j = 0; j <arr.length ; j++){
      		reparr[j] = arr[j];
      }
	  for(var i=0; i<reparr.length; i++) {
	    if(reparr[i] == val) {
	      reparr.splice(i, 1);
	      break;
	    }
	  }
	  return reparr;
	}
</script>

</body>
</html>
