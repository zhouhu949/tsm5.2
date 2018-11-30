$(function(){
	//点击弹窗外面弹窗隐藏
	$(".progress_bar").click(function(e){
		e.stopPropagation();
	});
	$("body").click(function(){
		$(".progress_bar").hide();
	});
	
	offselt();
});
function appendtos(classname,type){
	var str="<table class='shows_my_tables' style=''><thead><tr><th>任务名称</th><th>任务执行进度</th><th>任务执行状态</th><th>任务开始时间</th></tr></thead><tbody>";
	$.post("/progress/query", {"type":type},function(jsonData){
		for(var key in jsonData){
			var data = jsonData[key];
			str += "<tr><td>"+data.name+"</td><td><div class='shows_prograss_detail'><div class='shows_prograss_value' style='width:"+data.percent+"%;'></div><div class='shows_prograss_line'></div></div><div class='shows_prograss_num'>"+data.percent+"%</div></td><td>"+(data.percent==100?"完成":"进行中")+"</td><td>"+data.startaTimeStr+"</td></tr>";
		}
		str+="</tbody></table>";
		$("."+classname).html(str);
		$("."+classname).show();	
	});
}

function offselt(){
	var objs=$(".progress_bar").prev().position();
	$(".progress_bar").css({
		"left":objs.left+"px",
		"top":objs.top+30+"px"
	})
}