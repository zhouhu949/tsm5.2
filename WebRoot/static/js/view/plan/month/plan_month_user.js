/*领取单个*/
function receiveSingle(){
	$.post(ctx+"/plan/month/user/receivePoint",{"id":id,"type":type},function(model){
	});
}
/*领取全部*/
function receiveAll(){
	$.post(ctx+"/plan/month/user/receivePoint",{"id":id,"type":type},function(model){
	});
}

function buttonBind(){
	$("#aaa").click(function (){ 
		
	});
}

function refreshPage(){
	$.post(ctx+"/plan/month/user/receivePoint",{"id":id,"type":type},function(model){
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
