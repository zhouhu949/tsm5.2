function buttonBind(){
	$("#aaa").click(function (){ 
		
	});
}

function refreshPage(){
	$.post(ctx+"/plan/day/refresh",{"date":date.getTime()},function(model){
		data= model.data;
		server_date = new Date(model.date);
		initPage();
	});
}

function initPage(){
	if(data!=null){
		sudId = data.id;
	  	$("#planDay").html(formatDate(server_date,3));
	  	initCalendar();
	  	initTableData();
	}else{
		alert("当天无计划！！！");
	}
}

function init(){
	
};

$(document).ready(function() {
	init();
});
