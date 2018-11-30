$(function(){
	$(".tabTagList>li a").click(function(){
		var _this = $(this);
		if(_this.hasClass("current")){
			return;
		}else{
			$("a.current").removeClass("current");
			_this.addClass("current");
		}
	});
	
	$(".operate-area .fa-sort-down").on("click",function(e){
		e.stopPropagation();
		var _this = $(this);
		var arrow_down_ul = _this.parents("li").find(".arrow-down-ul");
		arrow_down_ul.css("display")=="block" ? arrow_down_ul.hide() : arrow_down_ul.show();
	});
	
//	$.ajax({
//		url:ctx+'/res/cust/getMyCustNums',
//		type:'post',
//		dataType:'json',
//		error:function(){},
//		success:function(data){
//			$('#resNum').text(data.resNum);
//			$('#intNum').text(data.intNum);
//			$('#signNum').text(data.signNum);
//			$('#silentNum').text(data.silentNum);
//			$('#losingNum').text(data.losingNum);
//			$('#allNum').text(data.allNum);
//		}
//	})
})

