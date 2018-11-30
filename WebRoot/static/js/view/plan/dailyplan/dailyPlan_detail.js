$(function(){
	$(".hyx-dayplan-left-bottom-items").on("click",function(e){
		e.stopPropagation();
		var $this = $(this);
		if($this.hasClass("current")){
			return;
		}else{
			var src=$this.data("basesrc");
			var custStatusId = $this.data("custstatusid")?$this.data("custstatusid"):"";
			var type = $this.data("type");
			$("#type").val(type);
			$("#custStatusId").val(custStatusId);
			
			var status = null;
			var dateStr = $("#dateStr").val();
			var sudId = $("#sudId").val();
			var userAcc = $("#userAcc").val();
			var userId = $("#userId").val();
			
			var realLink = ctx+src+"?dateStr="+dateStr+"&sudId="+sudId+"&userAcc="+userAcc+"&userId="+userId+"&custStatusId="+custStatusId+"&type="+type+"&status="+status;
			$("a.current").removeClass("current");
			$this.addClass("current");
			$("#iframepage").attr("src",realLink);
		}
	});
	$(".operate-area .fa-sort-down").on("click",function(e){
		e.stopPropagation();
		var _this = $(this);
		var arrow_down_ul = _this.parents("li").find(".arrow-down-ul");
		arrow_down_ul.css("display")=="block" ? arrow_down_ul.hide() : arrow_down_ul.show();
	});
	$(".hyx-dayplan-left-top-btnleft").on("click",function(e){
		e.stopPropagation();
		$("#sudId").val("");
		var currTime = $("#dateStr").val();
		var newTime = moment(currTime).subtract(1,"days").format("YYYY-MM-DD");
		$("#dateStr").val(newTime);
		$("#dayPlan_detailsForm").submit();
	});
	$(".hyx-dayplan-left-top-btnright").on("click",function(e){
		e.stopPropagation();
		var currTime = $("#dateStr").val();
		var newTime = moment(currTime).add(1,"days").format("YYYY-MM-DD");
		$("#dateStr").val(newTime);
		$("#dayPlan_detailsForm").submit();
	});
});
function pageSubmit(){
	$("#dayPlan_detailsForm").submit();
}