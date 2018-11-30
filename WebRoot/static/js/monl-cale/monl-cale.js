var monlsc=function(x){
	var return_num = 0;
	x.siblings(".canl-second").find('.charts').each(function(i,item){
        var a = $(item).parent().parent().next().find('.a');
        var a_a = parseFloat(a.attr('name'));   //实际完成值
        var a_b = parseFloat(a.text());		//滚动的实际完成值
        a_b = 0;
        var b = $(item).parent().parent().parent().find('.p').find('span');  
        var b_a = parseFloat(b.text());  //滚动的百分之百的值
        b_a = 0;
        var f = $(item).parent().parent().next().find('.b');
        var f_a = parseFloat(f.text());   //计划完成值
        var c = 0; 
        var c_a = 0;  //完成百分之百的值
        percent_control(a_a/f_a*100,c_a);
	    c_a = return_num;
        var e = a_a*(1/100);
        var d_a = a_a/e;
        var d = c_a/d_a;
        var inter = setInterval(function(){
            if(a_b < a_a){
        		if(a_a-a_b>=e){
        			a_b=Math.round((a_b+e)*1000)/1000;
                }else{
                    a_b=a_a;
                }
        	}
            if(b_a < c_a){
                if(c_a-b_a>=d){
                	b_a=Math.round((b_a+d)*10000)/10000;
                }else{
                    b_a=c_a;
                }
            }
            if(c < c_a){
            	c=c+d;
            	if(c_a-c>=d){
            		$(item).css({"width":c+"%"});
            	}else{
            		$(item).css({"width":c_a+"%"});
            	}
            	if(c >= 100){
		            $(item).css({'background-color':'#ff0000','border-color':'#ff0000'});
		        }else{
		            $(item).css({'background-color':'#1979ca','border-color':'#1979ca'});
		        }
            }
            a.text(a_b);
            b.text(b_a);
            if(a_b >= a_a && b_a >= c_a && c >= c_a){
            	clearInterval(inter);
            }
        },20);
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
}

$(function(){
	//var date=new Date();
	var month =$("#planMonth").val();
	/*$("#canl-analyse").click(function(){
		$("#iframepage").attr("src","month_analy.html");
	});
	$("#canl-look").click(function(){
		$("#iframepage").attr("src","month_look.html");
	});
*/

	$("#list_shot").find("li").each(function(){
		var $this=$(this);
		
		if($this.index()==parseInt(month-1)){
			$this.find(".canl-first").hide();
			$this.find(".canl-second").show();
		}
		
		
		if($this.find(".canl-second").is(":visible")){
			monlsc($this.find(".canl-first"));
		};

		$this.find(".canl-first").click(function(){
			var $this = $(this);
			var $thisparindex=$this.parent().index();
			var $sblings=$this.parent().siblings()
			var $showslis=$(".bottle ul li");
			$this.hide();
			$sblings.find(".canl-second").hide();
			$sblings.find(".canl-third").hide();
			$sblings.find(".canl-first").show();
			/*$this.siblings(".canl-third").show(monlsc($this));*/
			$this.siblings(".canl-second").show(monlsc($this));
			if($thisparindex >2 && $thisparindex < 10){
				var left=$showslis.css("left");
				left=parseInt(left);
				left=-($thisparindex - 3)*141+"px";
				$showslis.animate({"left":left},50);
			}else if($thisparindex  >= 10){
				var left=$showslis.css("left");
				left=parseInt(left);
				left=-6*141+"px";
				$showslis.animate({"left":left},50);
			}
		});

		/*$(this).find(".canl-second").hover(function(){
			var $this = $(this);
			$this.hide();
			$this.siblings(".canl-third").show(falling.init());
			$this.siblings(".canl-third").find(".canvas").show();
			$this.parent('li').siblings().find(".canvas").hide();
			
		});

		$(this).find(".canl-third").mouseout(function(){
			var $this = $(this);
			$this.hide();
			$this.siblings(".canl-second").show();
		});*/
	});
	
	//选中当前月份
	$("#list_shot").find("li").eq(month-1).find(".canl-first")[0].click();
	
	$(".list").mouseover(function(){
		$(this).css("background","#e6e6e6");
	});
	$(".list").mouseout(function(){
		$(this).css("background","white");
	});
	$w = $('.pic').width();
	$h = $('.pic').height();
	$w2 = $w + 20;
	$h2 = $h + 20;
	var indexlog=3;
	$(".bottle ul li").mouseover(function(){
		var $this=$(this);
		$this.children('.pic').stop().animate({height:$h2,width:$w2,left:"-10px",top:"-10px"},300);
		$this.children(".pic_01").stop().animate({bottom:'-10px',left:'-10px'},700);
	});
	$(".bottle ul li").mouseout(function(){
		var $this=$(this);
		$this.children('.pic').stop().animate({height:$h,width:$w,left:"0px",top:"0px"},500);
		$this.children(".pic_01").stop().animate({height:'0',bottom:'0'},300);
	});
	$(".bottle .r").click(function(){
		var size=$(".bottle ul li").size();
		var left=$(".bottle ul li").css("left");
		left=parseInt(left);
		left=left-141;
	if(left%141==0){
		if((size-6)*(-141)<left){
			left=left+"px";
			$(".bottle ul li").animate({left:left},100);
		}else{
			left=(size-6)*(-141)+"px";
			$(".bottle ul li").animate({left:left},100);
		}
	}
		
	});
	$(".bottle .l").click(function(){
		var left=$(".bottle ul li").css("left");
		left=parseInt(left);
		left=left+141;
		if(left%141==0){
		if(left<0){
			left=left+"px";
			$(".bottle ul li").animate({left:left},50);
		}else{
			$(".bottle ul li").animate({left:"0px"},200);
		}
		}
	});
	
});