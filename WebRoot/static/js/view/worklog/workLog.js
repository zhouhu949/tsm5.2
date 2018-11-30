//选择时间
var date = null;
//服务器时间
var server_date = null;
//本月已存在全部下属日志的日期
var existSubordinateDates = new Array();
//日历控件
var dateShow = null;
/* 页面类型 null为默认我的日志页面 "share"为共享日志页面 "lowLevel"只显示下级页面*/
var pageType = null;

function refreshLog(){
	$("#iframepage").contents().find("#logDateTime").val("");
	$("#iframepage").contents().find("form").submit();
}

function refreshLogSelectDay(time){
	$("#iframepage").contents().find("#logDateTime").val(time);
	$("#iframepage").contents().find("form").submit();
}

function newLogWindow(id){
	if(id==undefined || id ==null){
		pubDivShowIframe('newLogWindow',ctx+'/worklog/newLogWindow?title='+formatDate(date,3),'新建日志',450,400);
	}else{
		pubDivShowIframe('newLogWindow',ctx+'/worklog/newLogWindow?wliId='+id,'编辑日志',450,400);
	}
}

function defShareWindow(){
	pubDivShowIframe('shareWindow',ctx+'/worklog/share/window?type=0','选择默认分享的成员',330,270);
}


function setShareValue(id,value){
	$("#iframepage").contents().find("#shareNum"+id).text(value);
}

function calendar(){
	$(".daytd").removeClass("allReadyEdit");
	$(".daytd").removeClass("neverEdit");
	$(".daytd").siblings().remove();
	if(pageType==null){
		$.post(ctx+"/worklog/myLogState",{"dateTime":date.getTime()},function(dates){
	 		if(dates!=null&&dates.length>0){
				for(var i=0;i<dates.length;i++){
					var date = new Date(dates[i]);
					
					var valueArray=date.getValueArray();
					var year=valueArray[0];
					var month=valueArray[1];
					var day=valueArray[2];
					$(".daytd[year='"+year+"'][month='"+month+"'][day='"+day+"']").parent().append("<div class='all-sub'></div>");
				}
			}
		});
	}else if(pageType=="mannager"){
		$.post(ctx+"/worklog/managerLogState",{"dateTime":date.getTime()},function(dates){
	 		if(dates!=null&&dates.length>0){
				for(var i=0;i<dates.length;i++){
					var date = new Date(dates[i]);
					
					var valueArray=date.getValueArray();
					var year=valueArray[0];
					var month=valueArray[1];
					var day=valueArray[2];
					$(".daytd[year='"+year+"'][month='"+month+"'][day='"+day+"']").addClass("allReadyEdit");;
				}
			}
	 		$(".daytd:not(.allReadyEdit,.weekend,._futureDay)").addClass("neverEdit");
		});
		
	}else{
	
	}
	
}

function buttonBind(){
	/* 我的日志*/
	$("#my_work_log").click(function (){
		$("#my_work_log").addClass("btn-hover");
		$("#share_work_log").removeClass("btn-hover");
		$("#iframepage").attr("src",ctx+"/worklog/myLog?logDateTime="+date.getTime());
		
		pageType=null;
		calendar();
		$("#show_tip").hide();
	});
	
	/* 共享日志*/
	$("#share_work_log").click(function (){
		$("#share_work_log").addClass("btn-hover");
		$("#my_work_log").removeClass("btn-hover");
		$("#iframepage").attr("src",ctx+"/worklog/share/shareLog?&logDateTime="+date.getTime());
		
		if(isManager){
			pageType="mannager";	
		}else{
			pageType="share";
		}
		calendar();
		if(isManager) $("#show_tip").show();
	});
	
	
	$("#date_show").delegate(".neverEdit", "mouseenter mouseleave", function(event){
		if("mouseleave"==event.type){
			$('.hyx-wlm-drop').hide();
		}else{
			var day = $(this);
			/*
			 * 弹出框距离顶部的高度
			 * tr_height：日期行高
			 * tr_index：当前选中日期所在的行
			 * drop_top：弹出层距离顶部的高度
			 	* 238：距离第一行日期的像素（呵呵，手动测量的）
			 	* tr_height * (tr_index-1)：（当前行数-1）*行高
			 	* div_height：加上单个div的高度；让弹出层与div的底部对其
			 * 
			 */
			var div_height = day.height();
			var tr_height = day.parent().parent().height();
			var tr_index = day.parent().parent().index();
			var drop_top = 238 + tr_height * (tr_index-1) + div_height;
			$('.hyx-wlm-drop').css("top",drop_top);
			$('.hyx-wlm-drop').show();
			var tempDate=new Date(day.attr("year"),day.attr("month")-1,day.attr("day"));
			$.post(ctx+"/worklog/queryNoLogUsers", { "dateTime": tempDate.getTime()},function(data){
				if(data.status){
					var html="";
					for(var i=0;i<data.data.length;i++){
						html+="<li title='"+data.data[i].userName+"'>"+data.data[i].userName+"</li>";
					}
					$("#noLogUsers").html(html);
				}
			});
		}
	});
	$('.hyx-wlm-drop').mouseenter(function(){
		$(this).show();
		$(this).mouseleave(function(){
			$(this).hide();
		});
	});
	 
}
function init(){
	//渲染工作日志
	server_date = new Date(server_dateTime);
	date = new Date(dateTime);
	
  	//渲染日历控件
  	dateShow = new DateShow("date_show");
  	dateShow.init();
  	buttonBind();  	
};

$(document).ready(function() {
	init();
});
