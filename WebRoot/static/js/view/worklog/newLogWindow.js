/*新建日志-插入 -入库方法 1提交  0保存  */
function saveWorkLog(type){
	var context=$("#new_log_text").val().replace(/(^\s+)|(\s+$)/g,'');
	var workPlan= $("#new_log_workPlan").val().replace(/(^\s+)|(\s+$)/g,'');
	var typeArray=["保存","提交"];
	var date = parent.date; 
	
	if(wliId==null){
		$.post(ctx+"/worklog/insert", { "dateTime": date.getTime(), "context":context,"workPlan":workPlan,"status":type},
			   function(data){
					if(data.status){
						setTimeout('window.parent.refreshLog();',50);
						setTimeout('window.parent.closePubDivDialogIframe("newLogWindow");',100);
					}else {
						window.parent.warmDivDialog('warm',data.errorMsg);
					}
		});
	}else{
		$.post(ctx+"/worklog/edit", {"context":context,"workPlan":workPlan,"status":type,"wliId":wliId},
		   function(data){
				if(data.status){
					setTimeout('window.parent.refreshLog();',50);
					setTimeout('window.parent.closePubDivDialogIframe("newLogWindow");',100);
				}else{
					window.parent.warmDivDialog('warm',data.errorMsg);
				}
		});
	}
};
/*新建日志-校验方法 */
function check(){
	var context=$("#new_log_text").val();
	var workPlan= $("#new_log_workPlan").val();
	context=context.replace(/(^\s+)|(\s+$)/g,'');
	workPlan=workPlan.replace(/(^\s+)|(\s+$)/g,'');
	if(context.length==0){
		var msg = "";
		if(context.length==0){
			msg="今日工作总结不能为空！";
		} 
		/*if(workPlan.length==0){
			msg="明日工作计划不能为空！";
		} 
		if( context.length==0 && workPlan.length==0 ){
			msg = "日志内容不可为空！" ;
		}*/
		window.parent.warmDivDialog('warm',msg);
		return false;
	}else if(context.length>2000){
		window.parent.warmDivDialog('warm','日志最大长度为2000字！');
		return false;
	}else if(workPlan.length>2000){
		window.parent.warmDivDialog('warm','日志最大长度为2000字！');
		return false;
	}else{
		return true;
	}
};
function buttonBind(){
	$("#save").click(function (){
		if(check()){
			saveWorkLog(0);
		}
	});
	
	$("#commit").click(function (){
		if(check()){
			saveWorkLog(1);
		}
	});
	
	$("#cancel").click(function (){
	    window.parent.closePubDivDialogIframe("newLogWindow");
	});
}

function init(){
	buttonBind();  	
};

$(document).ready(function() {
	init();
});
