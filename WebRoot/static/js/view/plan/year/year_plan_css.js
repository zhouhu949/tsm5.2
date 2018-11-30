$(function(){
	/*隐藏按钮*/
	if(date.getFullYear()<serverDate.getFullYear()){
		$('.hyx-aspa-edit').hide();
	}else{
		$('.hyx-aspa-edit').show();
	}
	// 表格
	$('.hyx-aspa-table').find('tr').each(function(i,item){
        var back_color = $(item).css('background-color');
        $(item).find('td:first').css({'border-left-style':'solid','border-left-width':'1px','border-left-color':back_color});
        $(item).find('th:first').css({'border-left-style':'solid','border-left-width':'1px','border-left-color':back_color});
        $(item).find('td:last').css({'border-right-style':'solid','border-right-width':'1px','border-right-color':back_color});
        $(item).find('th:last').css({'border-right-style':'solid','border-right-width':'1px','border-right-color':back_color});
        $(item).mouseover(function(){
            $(this).find('td').addClass('td-link');
            $(this).mouseout(function(){
                $(this).find('td').removeClass('td-link');
            });
        });
    });

	function tableColSel(){
		//var date = new Date();
		var nowMonth =date.getMonth()+1;
		var nowYear = date.getFullYear();
		var table = $('.hyx-aspa-table');
		var timeDiv = $('.hyx-aspa-time');
		var changeYear = nowYear;
		// 年份增减
		function dateSel(){
			function nowY(obj){
				timeDiv.find('.date').text(obj);
			}
			nowY(nowYear);
			timeDiv.find('.left').click(function(){
				changeYearFn(-1);
			});
			timeDiv.find('.right').click(function(){
				changeYearFn(1);
			});
		}
		dateSel();
		// 初始化单元格样式
		function starTable(){
			table.find('th').each(function(i,item){
				table.find('th').eq(i).removeClass('th-cli');
				if(i>=1 && i<=12){
					table.find('th').eq(i).addClass('th-cli');
				}
			})
			table.find('tr').each(function(i,item){
				$(item).find('td').each(function(j,item_a){
						table.find('tr').eq(i).find('td').eq(j).removeClass('tb-cli');
						table.find('tr').eq(i).find('td').eq(j).find('input').removeClass('ipt-none');
						if(j>=1 && j<=12){
							table.find('tr').eq(i).find('td').eq(j).addClass('tb-cli');
						}
					if(j>=nowMonth && j<=12){
						table.find('tr:last').find('td').find('input').addClass('ipt-none');
						table.find('tr:last').find('td').find('input').attr('disabled','disabled');
					}else{
						table.find('tr').eq(i).find('td').eq(j).find('input').addClass('ipt-none');
						table.find('tr').eq(i).find('td').eq(j).find('input').attr('disabled','disabled');
					}
				})
			})
		}
		starTable();
		
		// 取消按钮
		/*$('.hyx-aspa-cancel').click(function(){ 
			starTable();
			starTab();
			$(this).hide();
			$('.hyx-aspa-save').hide();
			$('.hyx-aspa-edit').show();
		});*/
		
		// 编辑按钮
		$('.hyx-aspa-edit').click(function(){
			table.find('th').each(function(i,item){
				table.find('th').eq(i).removeClass('th-cli');
				table.find('th').removeClass('th-cli-now');
				table.find('th').eq(nowMonth).addClass('th-cli-now');
				//小于当年的 ||当年小于当月  不可编辑
				//大于当年||当年大于当月
				if((date.getFullYear()>serverDate.getFullYear()&& i<=12 )||(date.getFullYear()==serverDate.getFullYear() &&i>=nowMonth && i<=12)){
					$(item).click(function(){
						table.find('th').eq(i).siblings().removeClass('th-cli-now');
						table.find('th').eq(i).addClass('th-cli-now');
						table.find('tr').each(function(j,item_a){
							$(item_a).each(function(){
								$(this).find('td').eq(i).siblings().removeClass('tb-cli-now');
								$(this).find('td').eq(i).addClass('tb-cli-now');
								table.find('tr:last').find('td').eq(i).siblings().removeClass('last-bor');
								table.find('tr:last').find('td').eq(i).addClass('last-bor');
							});
						});
					});
				}
			});
			table.find('tr').each(function(i,item){
				$(item).find('td').each(function(j,item_a){
					table.find('tr').eq(i).find('td').eq(j).removeClass('tb-cli');
					table.find('tr').eq(i).find('td').removeClass('tb-cli-now');
					table.find('tr:last').find('td').removeClass('last-bor');
					table.find('tr').eq(i).find('td').eq(nowMonth).addClass('tb-cli-now');
					table.find('tr:last').find('td').eq(nowMonth).addClass('last-bor');
					if((date.getFullYear()>serverDate.getFullYear())&& j<=12
							||(date.getFullYear()==serverDate.getFullYear() &&j>nowMonth && j<=12)){
						table.find('tr').eq(i).find('td').eq(j).find('input').removeClass('ipt-none');
						table.find('tr').eq(i).find('td').eq(j).find('input').removeAttr('disabled');
						$(item_a).click(function(){
							table.find('th').eq(j).siblings().removeClass('th-cli-now');
							table.find('th').eq(j).addClass('th-cli-now');
							table.find('tr').each(function(){
								$(this).find('td').eq(j).siblings().removeClass('tb-cli-now');
								$(this).find('td').eq(j).addClass('tb-cli-now');
								table.find('tr:last').find('td').eq(j).siblings().removeClass('last-bor');
								table.find('tr:last').find('td').eq(j).addClass('last-bor');
							});
						});
					}
					table.find('tr:last').find('td').find('input').addClass('ipt-none');
					table.find('tr:last').find('td').find('input').attr('disabled','disabled');
				});
			});
			$(this).hide();
			$('.hyx-aspa-save').show();
			$('.hyx-aspa-cancel').show();
		});
		// 刚开始表格状态
		function starTab(){
			table.find('.th-cli').each(function(i,item){
				table.find('.th-cli').eq(i).removeClass('th-cli-now');
				table.find('.th-cli').eq(nowMonth-1).addClass('th-cli-now');
				if(i>=0 && i<=12){
					$(item).click(function(){
						table.find('.th-cli').eq(i).siblings().removeClass('th-cli-now');
						table.find('.th-cli').eq(i).addClass('th-cli-now');
						table.find('tr').each(function(j,item_a){
							$(item_a).each(function(){
								$(this).find('.tb-cli').eq(i).siblings().removeClass('tb-cli-now');
								$(this).find('.tb-cli').eq(i).addClass('tb-cli-now');
								table.find('tr:last').find('.tb-cli').eq(i).siblings().removeClass('last-bor');
								table.find('tr:last').find('.tb-cli').eq(i).addClass('last-bor');
							});
						});
					});
				}
			});
			table.find('tr').each(function(i,item){
				$(item).find('.tb-cli').removeClass('tb-cli-now');
				table.find('tr:last').find('.tb-cli').removeClass('last-bor');
				$(item).find('.tb-cli').eq(nowMonth-1).addClass('tb-cli-now');
				table.find('tr:last').find('.tb-cli').eq(nowMonth-1).addClass('last-bor');
				$(item).find('.tb-cli').each(function(j,item_a){
					table.find('tr').find('.tb-cli').find('input').addClass('ipt-none');
					table.find('tr').find('.tb-cli').find('input').attr('disabled','disabled');
					if(j>=0 && j<=12){
						$(item_a).click(function(){
							table.find('.th-cli').eq(j).siblings().removeClass('th-cli-now');
							table.find('.th-cli').eq(j).addClass('th-cli-now');
							table.find('tr').each(function(){
								$(this).find('.tb-cli').eq(j).siblings().removeClass('tb-cli-now');
								$(this).find('.tb-cli').eq(j).addClass('tb-cli-now');
								table.find('tr:last').find('.tb-cli').eq(j).siblings().removeClass('last-bor');
								table.find('tr:last').find('.tb-cli').eq(j).addClass('last-bor');
							});
						});
					}
				});
			});	
		}
		starTab();
	}
	tableColSel();
	
	// 进度条
/*	var charts_a = $('.votebox').find('.charts');
    var charts_b = $('.votebox').find('.play-show');
	var char_times_a = 0;
	var char_times_b = 0;
	var char_a = charts_a.next().find('.p').attr('name');
	var char_b = charts_b.next().find('.p').attr('name');
	if(char_a>=char_b){
    	charts_a.css({'z-index':'1'});
    	charts_b.css({'z-index':'2'});
    	char_times_a = 8;
    	char_times_b = 8;
    }else{
    	charts_a.css({'z-index':'2'});
    	charts_b.css({'z-index':'1'});
    	char_times_a = 8;
    	char_times_b = 8;
    }
	charts_two(charts_a,char_times_a);
    charts_two(charts_b,char_times_b);

    var return_num = 0;
	
	 function charts_two(chart_name,inter_time){
	        chart_name.each(function(i,item){
	            var b = $(item).next().find('.p');  //计划销售额或实际销售额
	            var b_a = b.text();   //滚动值（初始值为零）
	            var b_b = b.attr('name');  //实际值
	            var c = 0;  //计划销售额或实际销售额滚动的进度条初始值为零 
//	            var d = 5;  //计划销售额或实际销售额滚动的进度条每次长度增加的数值（差值）
	            var f = $(item).parent().parent().next().find('.b');  //全年计划
	            var f_a = f.text();   //全年计划的实际值
//	            var c_length = b_b / f_a * 100;  //计划销售额或实际销售额所占全年计划的百分比
//	            percent_control(b_b/f_a*100,c_a);
	            var del_a = $(item).parent().find('.box').width();  //小箭头上方提示语的宽度
	            var del_b = $('.barline').width();   //进度条的宽度
	            var del_c = del_b * b_b / f_a;  //计划销售额或实际销售额的进度条要滚动的长度
//	            percent_control(del_b * b_b / f_a,del_c);
//		    	del_c = return_num;
		    	var d = del_c / inter_time;
	            var del_v = 0;  //全年计划滚动的进度条初始值为零

	            var inter = setInterval(function(){
	                if(c<del_c){   //计划销售额或实际销售额已滚动的进度条长度 < 要滚动的长度的时候
	                	if(c>=del_b){  //计划销售额或实际销售额已滚动的进度条长度 >= 进度条的宽度的时候
	                		c = del_b;  //计划销售额或实际销售额已滚动的进度条长度 = 进度条的宽度
	                		if(del_v < del_c-c){  //全年计划滚动的进度条长度 < (计划销售额或实际销售额的进度条要滚动的长度 - 计划销售额或实际销售额已滚动的进度条长度)
	                			$('.votebox').find('.sp').find('.tip').css({'display':'block'});  
	                			del_v = del_v + 1;
	                			$('.votebox').find('.sp').css({"right":del_v+(del_c-c)/inter_time+"px"});
//	                			$('.votebox').find('.sp').css({"right":percent_control(del_c-c,c)/inter_time+"px"});
	                		}
	                	}else{  //计划销售额或实际销售额已滚动的进度条长度 < 进度条的宽度的时候
	                		c=c+d;
	                	}
	                	$(item).next().css({"left":c-del_a/2-1+"px"});
	                    $(item).css({"width":c-1+"px"});
	                }
	                if(b_a >= 0 && b_a < b_b){  //滚动值 >= 0 && 滚动值 < 实际值
	                    c=c+d;  
	                    if(b_b-b_a>=d){  //实际值 - 滚动值 >= 差值
	                        percent_control(b_a+d,b_a);
		    				b_a = return_num;
	                    }else{
	                        b_a=b_b;
	                    }
	                }
	                b.text(b_a+'万');  //小箭头上方数值的滚动
	                if(c >= del_b && b_a >= b_b || c >= del_c && b_a >= b_b){   //计划销售额或实际销售额已滚动的进度条长度 >= 要滚动的长度的时候
	                	var name_p2 = $('.votebox').find('.p2').attr('name');
	                	if(b_a == f_a && b_b == f_a && name_p2 <= f_a){   //当计划销售额=全年计划的时候，全年计划的显示样式
	                		var sp_html = "<label class='tit' style='right:-25px;top:-30px;'>全年计划</label>";
	                		$('.votebox').find('.sp').html(sp_html);
	                	}
	                    clearInterval(inter);
	                }
	            },inter_time);
	        });
	    }*/
	
	var progress_time = 80;	//进度条滚动时间
	
    var plan_progress = $('.votebox').find('.charts');
    var actual_progress = $('.votebox').find('.play-show');
    var total_progress = $('.votebox').find('.sp');
    //类名为box的label中类名为p的label的值
    var plan_value = parseFloat(plan_progress.next().find('.p').attr('name'));		//计划销售额
    var actual_value = parseFloat(actual_progress.next().find('.p').attr('name'));	//实际销售额
    var total_value = parseFloat(total_progress.find(".b").text());					//全年规划销售额
    total_progress.find(".b").text(total_value.toFixed(2));
    total_progress.css("right",$(".votebox").width() * 0.03 - total_progress.width()/2 + "px");
    //判断计划金额与实际金额的大小，让大的在下面（z-index的值小）
    if(plan_value>=actual_value){
    	plan_progress.css('z-index','1');
    	actual_progress.css('z-index','2');
    }else{
    	plan_progress.css('z-index','2');
    	actual_progress.css('z-index','1');
    }
    
    if(actual_value <= total_value){
    	progress_leftToRight(plan_value,total_value,progress_time,plan_progress);
    	progress_leftToRight(actual_value,total_value,progress_time,actual_progress);
    }else{
    	progress_leftToRight(plan_value,actual_value,progress_time,plan_progress);
    	progress_leftToRight(actual_value,actual_value,progress_time,actual_progress);
    	progress_rightToLeft(total_value,actual_value,progress_time,total_progress);
    }
    
    if(total_value == 0 && actual_value != 0){
    	$(".plan-value").find(".tip").removeClass("tip_down");
    	$(".plan-value").find(".tip").prependTo(".plan-value").addClass("tip_up");
    	$(".plan-value").css("top","11px");
    	$(".hyx-aspa .votebox .tit").css("right","-20px");
    }
    
    if(total_value != 0 && total_value == plan_value){
    	$(".plan-value").find(".tip").removeClass("tip_down");
    	$(".plan-value").find(".tip").prependTo(".plan-value").addClass("tip_up");
    	$(".plan-value").css("top","11px");
    }
    
    function progress_leftToRight(value,total,time,object){
    	var percent_value = value / time;
    	var precent_progress = value / total / time;
    	var curr_value = 0;
    	var curr_progress = 0;
    	var curr_time = 0;
    	var next_obj = object.next();
    	var next_obj_length = next_obj.width();
		var inter = setInterval(function(){
    		curr_value += percent_value;
    		curr_progress += precent_progress;
    		object.width(curr_progress*100 + "%");
    		next_obj.find(".p").text(parseFloat(curr_value).toFixed(2) + "万");
    		next_obj.css("left",object.width() - next_obj_length / 2);
    		curr_time ++;
    		if(curr_time >= time){
    			clearInterval(inter);
    		}
    	},16);
    }
    
    function progress_rightToLeft(value,total,time,object){
    	var minus_value = total - value;
    	var precent_progress = minus_value / total / time;
    	var curr_progress = 0;
    	var curr_time = 0;
    	var object_length = object.width();
    	var object_right = parseFloat(object.css("right"));
		var inter = setInterval(function(){
    		curr_progress += precent_progress;
    		object.css("right",curr_progress * $(".barline").width() + object_right);
    		curr_time ++;
    		if(curr_time >= time){
    			clearInterval(inter);
    		}
    	},16);
    }

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
});