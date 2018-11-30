/* 格式化日期方法   */
function formatDate(str,type){
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
		}
	}
}

function getDateFromStr(str){
	if(str!=null && /\d{4}(-|\/)\d{1,2}(-|\/)\d{1,2}( \d+:\d+:\d+)?/.test(str)){
		if(/-/.test(str)) str=str.replace(/-/g,"\/");
		return new Date(str);
	}else {
		return null;
	}
}

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