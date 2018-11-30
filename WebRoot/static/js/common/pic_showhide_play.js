/**
 * 登录页面_图片淡入淡出播放
 Written by:haoqj
 Created Date: 2013-06-13
 Copyright：chinajoin 2013 -2014
**/
$(function(){
	var picTimer;
	var index = 0;
	var count= $("#banner_list li").length;
	$("#banner_list li:not(:first-child)").hide();
	
	//为小按钮添加鼠标滑入事件
	$("#point_list li").mouseenter(function() {
		index = $("#point_list li").index(this);
		showPics(index,count);
	});
	
	//鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
	$("#mid").hover(function() {
		clearInterval(picTimer);
	},function() {
		picTimer = setInterval(function() {
			index = index >=(count - 1) ? 0 : ++index;
			showPics(index);
		},4000); //自动播放的间隔，单位：毫秒
	}).trigger("mouseleave");
});

//显示图片函数，根据index值切换
function showPics(index,count) {
	if (index >= count) return;
	$("#banner_list li").filter(":visible").fadeOut(3000).parent().children().eq(index).fadeIn(1000);
	$("#point_list li").removeClass("on").eq(index).addClass("on");
}

function showAuto(){
	index = index >=(count - 1) ? 0 : ++index;
}