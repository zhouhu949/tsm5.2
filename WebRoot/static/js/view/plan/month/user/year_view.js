$(function(){
    $('.demoBtn_a').click(function(){
    	pubDivShowIframe('historyWindow',ctx+'/plan/month/user/historyWindow','历史计划走势',915,400);
	});
    
 	// 点评文字省略
	$('.box').each(function(){
    	if($(this).find('.comment_con').text().length >= 8){
	    	$(this).find('.comment_con').text($(this).find('.comment_con').text().substr(0,8) + '...');
	    }
    });

    //点评列表显示
    $('.box').each(function(i,item){
    	$(item).find('.comment_hid').mouseover(function(){
    		$(this).find('.comment_drop').fadeIn(1);
			var comment_drop = $(this).find('.comment_drop');
	        var dropTop = $(this).find('.comment_drop').offset().top;//点评列表到浏览器顶部的高度
	        var window_height = $(window).height();//浏览器的可视高度
	        var dropH = comment_drop.height();//点评列表的高度
	        var boxH = $(item).height();//box的高度
	        var arrowH = $(item).find('.arrow').height();//arrow的高度
	        if(i>=4 && i<=5 || i>=10 && i<=11){//如果是5、6、11、12月
	        	$(this).find('.comment_drop').addClass('comment_drop_right');//点评列表偏向左边
	        }
	        if(i<=5){
	        	//如果点评列表的高度>浏览器的可视高度-评列表到浏览器顶部的高度
		        if(dropH > window_height - dropTop){
		        	$(this).find('.drop_box').css({'height':parseInt(window_height-dropTop-50)+'px'});
		        }
	        }else{
	        	if(dropH >= 195){//点评列表高度大于195px的时候
	        		$(this).find('.drop_box').css({'height':'195px'});
	        	}
	        	$(this).find('.comment_drop').css({'top':parseInt(boxH-dropH-30)+'px'});
	        	$(this).find('.arrow').css({'top':parseInt(dropH-arrowH-10)+'px'});
	        }

	    });
	    $(item).find('.comment_hid').mouseleave(function(){
	        $(this).find('.comment_drop').fadeOut(1);
	    });
    });
	var return_num = 0;
    // 进度条
	$('.box').each(function(i,box){
		var array_a = new Array();
		$(this).find('.charts').each(function(i,item){
			var b = $(item).parent().parent().siblings('.lab').find('.a');
	        var b_a =  parseFloat(b.text());//滚动的完成值
	        var b_b = parseFloat(b.attr('name')); //实际完成值
	        var f = $(item).parent().parent().siblings('.lab').find('.b');
	        var f_a =  parseFloat(f.text());//实际目标值
	        var g = $(item).parent().parent().siblings('.perc');
	    	var g_a = parseFloat(b.text());//滚动的百分数
	    	var c_a = 0;//实际百分数
	    	percent_control(b_b/f_a*100,c_a);
	    	c_a = return_num;
	        var c = 0;
	        var d = 1;//进度条百分比差值和滚动的完成值差值
	        var d_a = 0;//滚动的百分数差值
	        percent_control(c_a*(d/100),d_a);
	    	d_a = return_num;
	        var del_b = $(item).parent().width();//进度条的总体宽度
	        var del_c = 0;//进度条要滚动的宽度
	        percent_control(del_b*c_a/100,del_c);
	    	del_c = return_num;
	        
	        array_a.push(c_a);
	        //出现单个按钮或者总的按钮
	        
	        function btn_sel(){
	        	var big_num = 100;
	        	var count = 0;
	        	for(var i in array_a){
	        		if(array_a[i] >= big_num){
	        			count++;
	        		}
	        	}
	        	if(count >=3){
	        		$(item).parent().parent().parent().siblings('.receive_all').show();//全部领取按钮出现
	        	}else{
	        		$(item).parent().parent().siblings('.receive').show();//单个领取按钮出现
	        	}
				clearInterval(inter);
	        }

	        var inter = setInterval(function(){
	            if(c<del_c){//进度条滚动的宽度小于进度条要滚动的宽度
	            	c=c+d;
	            	if(c>=del_b){//进度条滚动的宽度大于等于进度条总体宽度的时候
	            		c = del_b;
	            		$(item).css({'background-color':'#ff0000','border-color':'#ff0000'});//进度条颜色变红
			            btn_sel();
	            	}else{
			            $(item).css({'background-color':'#1979ca','border-color':'#1979ca'});//进度条颜色变蓝（默认）
			        }
			        $(item).css({"width":c+"px"});
			    }
                if(b_a >= 0 && b_a < b_b){
                    if(b_b-b_a>=d){
                        percent_control(b_a+d,b_a);
	    				b_a = return_num;
                    }else{
                        b_a=b_b;
                    }
                }
                if(g_a < c_a){
                	if(c_a-g_a>=d_a){
                		g_a=Math.round((g_a+d_a)*100000)/100000;
                    }else{
                        g_a=c_a;
                    }
                }
	            b.text(b_a);
	            g.text(g_a+'%');
	            if(c >= del_c && b_a >= b_b && g_a >= c_a){
	                clearInterval(inter);
	            }
	        },10);
	    });
	});

	//控制百分号
	function percent_control(start_num,end_num){
		if(start_num >= 0.1){
    		end_num = Math.round((start_num)*10)/10;
    	}
    	if(start_num < 0.1 && start_num >=0.01){
    		end_num = Math.round((start_num)*100)/100;
    	}
    	if(start_num < 0.01 && start_num >=0.001){
    		end_num = Math.round((start_num)*1000)/1000;
    	}
    	if(start_num < 0.001 && start_num >=0.0001){
    		end_num = Math.round((start_num)*10000)/10000;
    	}
    	if(start_num < 0.0001 && start_num >=0.00001){
    		end_num = Math.round((start_num)*100000)/100000;
    	}
    	if(start_num < 0.00001 && start_num >=0.000001){
    		end_num = Math.round((start_num)*1000000)/1000000;
    	}
    	return_num = end_num;
    	return return_num;
	}

	//出现遮罩层
	function overlay_show(button,point){
		$("#zhezhao_title").text($(button).attr("planMonth"));
		$('.hyx-mpp-overlay').animate({'left':'0%','top':'0%','width':'100%','height':'100%'},500);
		$('.hyx-mpp-overlay').find('.main_bg').animate({'width':'100%','height':'100%'},800);
		
		integral_change(button,point);
	}

	//遮罩层积分滚动
	function integral_change(button,point){
		var start_integral = point.dbPoint;//初始积分
		var add_integral = point.newPoint;//增加积分
		var end_integral = start_integral+add_integral;//最后总的积分
		$('.hyx-mpp-overlay').find('.dd_a').find('span').text(add_integral);
		var inter_a = setInterval(function(){
			if(start_integral < end_integral){
				start_integral++;
				$('.hyx-mpp-overlay').find('.dd_b').text(start_integral);
			}else{
				clearInterval(inter_a);
				overlay_hide(button);
			}
		},1);
	}

	//遮罩层消失
	function overlay_hide(button){
		var medal = $(button).parents(".dl_big").find('.medal_small');
		var overlay_offset = medal.offset();
		var overlay_offset_x = overlay_offset.left;
		var overlay_offset_y = overlay_offset.top;
		$('.hyx-mpp-overlay').animate({'left':overlay_offset_x+28+'px','top':overlay_offset_y+43+'px','width':'0%','height':'0%'},800);
		
		var clazz = medal.attr("class");
		
		var newClazz=clazz.replace("dt_hang","dt_complete");
		medal.attr("class",newClazz);
		seal(button);
	}
	//盖章效果
	function seal(button){
		 $(button).parents(".dl_big").siblings('.seal_cover').stamper({
			image : ctx+"/static/images/complete.png",
			scale : 5,
			speed : 2000,
			complete : function() {
				
			}
		});
	}
	
	//点击领取按钮
	$('.receive').click(function(){//点击单个领取按钮
		var isLast=$(this).parent().parent().find('.charts').length == 1;
		var _this=this;
		$.post(ctx+"/plan/month/user/receivePoint",{"id":$(_this).attr("planid"),"type":$(this).attr("pointtype"),"isLast":isLast},function(model){
			if(model.status=="success"){
				$(_this).hide();
				$(_this).next('.add_integral').show().animate({'top':'-50px','opacity':'0'},1000);
				$(_this).siblings('.barbox,.perc').remove();
				$(_this).siblings('.icon').addClass('icon_check');
				$(_this).siblings("span").show();
				if(isLast){//点击最后单个领取按钮出现遮罩层
					overlay_show(_this,model.point);
				}
			}else{
				alert("错误");
			}
		}); 
	});
	$('.receive_all a').click(function(){//点击全部领取按钮
		var _this=this;
		$.post(ctx+"/plan/month/user/receivePoint",{"id":$(this).attr("planid"),"type":$(this).attr("pointtype")},function(model){
			if(model.status=="success"){
				$(_this).parent().hide();
				$(_this).parent().siblings('.list').find('.add_integral').show().animate({'top':'-50px','opacity':'0'},1000);
				$(_this).parent().siblings('.list').find('.barbox,.perc').hide();
				$(_this).parent().parent().find('.icon').addClass('icon_check');
				$(_this).parents(".dl_big").find(".lab").find("span").show();
				overlay_show(_this,model.point);
			}else{
				alert("错误");
			}
		});
	});

	$(".hyx-aspa-time").find('.left').click(function(){
		changeYearFn(-1);
	});
	$(".hyx-aspa-time").find('.right').click(function(){
		changeYearFn(1);
	});
});

function newTab(url,pageName){
	window.top.addTab(url,pageName);
}

function refreshPage(){
	$("#yearPlanForm").submit();
}

function changeYearFn(i){
	$("#planYear").val(parseInt($("#planYear").val())+i);
	refreshPage();
}