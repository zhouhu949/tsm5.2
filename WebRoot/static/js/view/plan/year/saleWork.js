function re(){
	$("#resource_window").dialog("open");
}
function pu(){
	$("#pupose_window").dialog("open");
}
function buttonBind(){
	//上一年
	$("#lastYear").click(function(){
		planYear--;
		$("#planYear").html(planYear+"年度规划");
		refreshPage();
	});
}

function refreshPage(){
	$.post(ctx+"/plan/refresh",{"planYear":planYear},function(model){
		pageType="view";
		data= model.data;
		server_date = new Date(model.date);
		initPageData();
	});
}

function initPageData(){
	if(data!=null && data.length>0){
		var html="";
		for(var i=0;i<data.length;i++){
			html+=
				'<tr>'+
					'<td><a href="#">听一下</a> </td>'+
					'<td>'+data[i].userAccount+'</td>'+
					'<td>'+data[i].userName+'</td>'+
					'<td>'+data[i].userOnline+'</td>'+
					'<td>'+data[i].userState+'</td>'+
					'<td>'+data[i].telState+'</td>'+
					'<td>'+data[i].telTime+'</td>'+
					'<td><a href="#" onclick="javascript:re()">0/30</a></td>'+
					'<td><a href="#" onclick="javascript:pu()">0/30</a></td>'+
				'</tr>';
		};
	$(".gridtable tbody").html(html);	
	};
}

function init(){
	initPageData();
	buttonBind();
};

$(document).ready(function() {
	init();
});
