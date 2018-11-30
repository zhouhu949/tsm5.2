function DateShow(id) {
	this.id = id;
}

DateShow.prototype.init = function() {
	var _self=this;
	var html='<div class="head">'+
	'		<div class="show_day"></div>'+
	'		<div class="show_day_long"></div>'+
	'		<div class="show_nongli"></div>'+
	'</div>'+
	'<div class="content">'+
	'	<div class="title clearfix">'+
	'		<div class="dateContent"></div>'+
	'		<div class="nextMonth"><span class="up-page"></span></div>'+
	'		<div class="lastMonth"><span class="down-page"></span></div>'+
	'	</div>'+
	'	<div class="dateBody">'+
	'		<table class="dateTable">'+
	'			<tr>'+
	'				<th>日</th><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th>'+
	'			</tr>'+
	'		</table>'+
	'	</div>'+
	'</div>';
	$("#"+this.id).html(html);

	_self.week = new Array("星期天","星期一","星期二","星期三","星期四","星期五","星期六");
	
	_self.head = $("#" + _self.id + " .head");
	_self.head.show_day=$("#" + _self.id + " .head .show_day");
	_self.head.show_day_long=$("#" + _self.id + " .head .show_day_long");
	_self.head.show_nongli=$("#" + _self.id + " .head .show_nongli");
	
	_self.title = $("#" + _self.id + " .content .title .dateContent");
	_self.dateTable = $("#" + _self.id + " .dateTable tbody");
	_self.lastMonth = $("#" + _self.id + " .lastMonth span");
	_self.nextMonth = $("#" + _self.id + " .nextMonth span");
	

	_self.lastMonth.click(function (){
		date.setMonth(date.getMonth()-1);
		refreshPage(date);
		//_self.showAllDay();
	 });
		
	_self.nextMonth.click(function (){
		date.setMonth(date.getMonth()+1);
		refreshPage(date);
		//_self.showAllDay();
	});
	
	this.showAllDay();
};

DateShow.prototype.showAllDay = function() {
	var _self = this;
	_self.dateTable_day = $("#" + _self.id + " .dateTable tbody tr:gt(0)");
	_self.dateTable_day.remove();
	
	var valueArray=date.getValueArray();
	var year=valueArray[0];
	var month=valueArray[1];
	var day=valueArray[2];
	var weekNum=valueArray[3];

	var num=date.dayNum();
	var firstDay=date.firstDay();
	var lastDay=date.lastDay();
	//本月第一天的星期
	var fistDayWeek=firstDay.getDay();
	//本月最后一天的星期
	var lastDayWeek=lastDay.getDay();
	//上个月的最大天数
	var lastNum=date.dayNum(-1);
	
	var array=new Array();
	
	for(var i=0;i<fistDayWeek;i++){
		array.push(lastNum-(fistDayWeek-i)+1);
	}
	
	for(var i=1;i<=num;i++){
		array.push(i);
	}
	for(var i=1;i<=6-lastDayWeek;i++){
		array.push(i);
	}

	var height=array.length/7;
	var sum=0;
	
	
	for(var i=0;i<height;i++){
		var appendStr="";
		appendStr+="<tr>";
		for(var j=0;j<7;j++){
			appendStr+="<td>";
			if((i==0&&array[sum]>7)||(i==height-1&&array[sum]<7)){
				appendStr+="<div class ='lastOrNextMonth' row ='"+(i+1)+"' col ='"+(j)+"' year ='"+year+"' month='"+((i==0?-1:1)+month)+"' day='"+array[sum]+"'>"+array[sum]+"</div>";
			}else{
				plan = test(year,month-1,array[sum]);
				var _class="";
				var _subClass="";
				if(plan!=null){
					_subClass=" all-sub";
				}
				
				var tempArray = server_date.getValueArray();
				if(tempArray[0]==year&&tempArray[1]==month&&tempArray[2]==array[sum]) _class="todayDay";
					
				
				appendStr+="<div class = 'daytd "+_class+" "+(array[sum]==day?"selectDay":"")+"' row ='"+(i+1)+"' col ='"+(j)+"' year ='"+year+"' month='"+month+"'day='"+array[sum]+"' >"+array[sum]+"</div>";
				appendStr+="<div class='"+_subClass+"'></div>";
				//有审核要求   已上报计划 all-sub
				//有审核要求  审核通过 绿色  驳回黄色
				
				//无审核要求   有计划 all-sub     
			}
			sum++;
			appendStr+="</td>";
		}
		appendStr+="</tr>";
		this.dateTable.append(appendStr);
	}
	
	//TODO 1、	有计划安排的日期，日期下面有一点，表示这一天已经安排了计划，如果是有审核要求的，那么有点的日期代表已经上报的计划；
	//TODO 3、	如果日计划有审核要求的，审核通过的以绿色表示，未通过的用黄色表示；
	
	var nongli= new NongLi();
	this.head.show_day.html(day);
	this.head.show_day_long.html(year+"年"+month+"月"+day+"日 "+this.week[weekNum]);
	this.head.show_nongli.html(nongli.GetLunarDay(year, month, day));
	this.title.html(year+"年"+month+"月");
	
	_self.dayTd = $("#" + _self.id + " .daytd");
	_self.dayTd.click(function (x){
		selectDay=$(x.currentTarget);
		_self.selectDay(selectDay.attr("year"),selectDay.attr("month") ,selectDay.attr("day"),selectDay.attr("row"),selectDay.attr("col"));
	});
};

DateShow.prototype.selectDay = function(year,month,day,row,col) {
	date=new Date(year,month-1,day);
	refreshPage(date);
};

Date.prototype.getValueArray = function(){
	var year = this.getFullYear();
    var month = this.getMonth()+1;
    var day= this.getDate();
    var week = this.getDay();
    return new Array(year,month,day,week);
};

//获取当前月有多少天
Date.prototype.dayNum = function(i){
	i=i==null||i==undefined?0:i;
    var date= new Date();
    date.setFullYear(this.getFullYear(),this.getMonth()+1+i, 0);
    return date.getDate();
};

//获取当前月第一天的日期
Date.prototype.firstDay = function(){
    var date= new Date();
    date.setFullYear(this.getFullYear(),this.getMonth(), 1);
    return date;
};

//获取当前月最后一天的日期
Date.prototype.lastDay = function(){
    var date= new Date();
    date.setFullYear(this.getFullYear(), this.getMonth(), this.dayNum());
    return date;
};
//日期数组中是否包含天
function test(year,month,day){
	var plan = null;
	if(plans!=null&&plans.length>0){
		for(var i=0;i<plans.length;i++){
			var tempDate = getDateFromStr(plans[i].planDate);
			if(tempDate!=null && tempDate.getFullYear()==year &&tempDate.getMonth()==month&&tempDate.getDate()==day){
				plan=plans[i];
				break;
			}
		}
	}
	return plan;
}
