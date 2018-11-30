var sudId = null;
function buttonBind(){
	 
}
function refreshPage(date){
	if(date!=undefined &&date!=null){
		$("#dateTime").val(date.getTime());
	}
	$("#dayPlanForm").submit();
}

function refreshRight(){
	$("#iframepage").contents().find("form").submit();
}

function init(){
  	dateShow = new DateShow("calendar");
  	dateShow.init();
	buttonBind();
};
/* 资源弹窗*/
function addResWindow(dateTime,type){
	var v = new Date().getTime();
	pubDivShowIframe('resourceAddWindow', ctx+"/plan/day/resource/resourceAddWindow?v="+v+"&pageType="+type+"&dateTime="+dateTime,'加入资源',750,560);
}

/* 资源弹窗*/
function addWillWindow(dateTime,type){
	var v = new Date().getTime();
	pubDivShowIframe('willcustAddWindow', ctx+"/plan/day/willcust/willcustAddWindow?v="+v+"&dateTime="+dateTime,'加入客户',760,580);
}

/* 资源弹窗*/
function addSignWindow(dateTime,type){
	var v = new Date().getTime();
	pubDivShowIframe('signcustAddWindow', ctx+"/plan/day/signcust/signcustAddWindow?v="+v+"&dateTime="+dateTime,'加入客户',750,580);
}

$(document).ready(function() {
	init();
});
