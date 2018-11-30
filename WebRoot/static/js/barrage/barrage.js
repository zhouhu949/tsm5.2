$(function(){
	// 初始化
	init_screen();
	
	//showBarrage("eyJtZXNzYWdlaW5mbyI6eyJtc2ciOiI1Ynk1NWJtVlVWRT0iLCJ3YXYiOiIxIiwidGl0bGUiOiI1Ynk1NWJtVjc3eUIifX0=");
	 	
	// 发送内容
	$(".s_sub").click(function(){	
		   $(".d_show").text("");
		   var text=$(".s_txt").val();
		   var div="<div>"+text+"</div>";
		   $(".s_txt").val('');
		   $(".d_show").append(div);
		   // 发送弹幕消息
		   var postData = {content: text};			     
		   $.post(ctx + "/main/sale/barrage", postData, function (msg) {}, "text");
		   // 初始化
		   init_screen();
		});
	// 点击评论
	$(".comment").click(function(){
		$(".send").show();
	});
	
	// 点击关闭
	$(".d_del").click(function(){
		$(".dm,.d_del").toggle(1000);
	});

	$('#banner').mousedown( 
			function (event) { 
				var isMove = true; 
				/*var abs_x = event.pageX - $('div.moveBar').offset().left; */
				var abs_y = event.pageY - $('div.moveBar').offset().top; 
				$(document).mousemove(function (event) { 
					if (isMove) { 
					var obj = $('div.moveBar'); 
					obj.css({/*'left':event.pageX - abs_x,*/ 'top':event.pageY - abs_y}); 
					} 
				} 
				).mouseup( 
					function () { 
					isMove = false; 
					} 
				); 
			} 
		); 
	});

	//初始化弹幕
	function init_screen(){
		var _top=0;
		$(".d_show").find("div").show().each(function(){
			/*$(this).css({"color":"#fff","font-size":"22px"});*/
			var _left;
			if($(this).width() >= $(window).width()){
				_left = $(this).width();
			}else{
				_left = $(window).width();
			}
			var _height=$(".dm").height();

			_top=_top+30;
			if(_top>=_height-100){
				_top=0;
			}

			$(this).css({"left":_left,"top":_top});
			var time=32000;
			if($(this).index()%2==0){
				time=35000;
			}
			$(this).animate({left:"-"+_left+"px"},time,function(){
			
			}).queue(function(next){
				$(this).remove();
				next();
			});
		});
	}

	//随机获取颜色值
	function getReandomColor(){
		return '#'+(function(h){
		return new Array(7-h.length).join("0")+h;
		})((Math.random()*0x1000000<<0).toString(16));
	}
	function getfontsize(){
		return Math.floor(Math.random()*10+14)+'px';
	}
	
	/** 显示信息 */
	function showBarrage(content){
			var postData = {msgMain: content};			     
		   $.post(ctx + "/main/sale/barrageDecode", postData, function (msg) {
			   if (typeof (msg) != 'undefined') {
				   $(".dm,.d_del").show(1000);
				   //感觉还是得添加标记位
				   if(msg.indexOf("恭喜")==0 && msg.indexOf("成功签约")){
					   var div="<div style='font-weight:blod;background-color:#f6dd07;border-radius:5px;color:#fb0225;font-size:30px;'>&nbsp;&nbsp;&nbsp;"+msg+"&nbsp;&nbsp;&nbsp;</div>";
				   }else{
					   var div = "<div>"+msg+"</div>";
				   }
				   $(".d_show").append(div);
				   init_screen();
			   }		  
		   }, "text");	
	}