$(function() {
	var search_head_select = $(".search_head_select");
	$(".com-search-box .hide-span").text("请输入" + search_head_select.find("option:selected").text()); 
	search_head_select.change(function(){
		$(".com-search-box .hide-span").text("请输入" + search_head_select.find("option:selected").text()); 
	});
});