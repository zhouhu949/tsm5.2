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

/* 格式化日期方法   */
function formatDate(str,type){
	var month = new Array("","一","二","三","四","五","六","七","八","九","十","十一","十二");
	var week = new Array("天","一","二","三","四","五","六");
	var tempDate = null;
	if(typeof(str)== "string"){
		tempDate=getDateFromStr(str);
	}else{
		tempDate=str;
	}
	
	if(tempDate==null){
		return "";
	}else{
		var dateArray = tempDate.getValueArray();
		if(type==1||type==null||type==undefined){
			return dateArray[0]+"年"+dateArray[1]+"月"+dateArray[2]+"日";
		}else if(type==2){
			return dateArray[1]+"月"+dateArray[2]+"日";
		}else if(type==3){
			return dateArray[0]+"年"+dateArray[1]+"月"+dateArray[2]+"日"+",星期"+week[dateArray[3]];
		}else if(type==4){
			return dateArray[2];
		}else if(type==5){
			return month[dateArray[1]]+"月";
		}else if(type==6){
			return dateArray[0]+"年"+dateArray[1]+"月";
		}else if(type==7){
			return dateArray[0]+"."+dateArray[1]+"."+dateArray[2];
		}
	}
}
/* 从字符串获取日期对象   */
function getDateFromStr(str){
	if(/\d{4}(-|\/)\d{1,2}(-|\/)\d{1,2}( \d+:\d+:\d+)?/.test(str)){
		if(/-/.test(str)) str=str.replace(/-/g,"\/");
		return new Date(str);
	}else {
		return null;
	}
}
/* 从字符串数组获取日期对象数组   */
function getDateFromArray(array){
	var tempArray = null;
	if(array!=null && array.length>0){
		tempArray =  new Array();
		for(var i = 0;i<array.length;i++){
			var temp = getDateFromStr(array[i]);
			if(temp!=null){
				tempArray.push(temp);
			}
		}
	}
	return tempArray;
}