$(function(){
	$(".com-table tbody tr").live('mouseover',function(){
		var $_this = $(this);
		$_this.addClass("td-link");
	});
	
	$(".com-table tbody tr").live('mouseout',function(){
		var $_this = $(this);
     	$_this.removeClass("td-link");
	});
	
	$(".com-table tbody tr").live('click',function(){
		var $_this = $(this);
		if($_this.hasClass('td-hover') == false){
    		$(".td-hover").removeClass("td-hover");
    		$(".tr-hover").removeClass("tr-hover");
    		$_this.addClass("tr-hover");
    	}
	});

    //全选
    $('#checkAll').length>0 && $('#checkAll').live('ifChecked',function(){
        $("input[id^=chk_]").not("[disabled]").iCheck('check');
    });

    //全不选
    $('#checkAll').length>0 && $('#checkAll').live('ifUnchecked',function(){
        $("input[id^=chk_]").iCheck('uncheck');
    });

	/*表单优化*/
    $('.skin-minimal input').length>0 && $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });

    // 列表排序
    $('#t1').length>0 && $('#t1').sorttable({
        allowMask:  false,
        ascImgUrl: ctx+"/static/images/table_sprite_05.png",
        descImgUrl: ctx+"/static/images/table_sprite_03.png",
        unSortImgUrl: ctx+"/static/images/table_sprite_05.png",
        ascImgSize: "8px",
        descImgSize: "8px",
        unSortImgSize: "8px",
        onSorted: function (cell) {
            $('#t1').find("tr[role!='head']").each(function(index,obj){
                if(((index+1)%2)==0){
                    $(this).attr("class","sty-bgcolor-b");
                }else{
                    $(this).attr("class","");
                }
            });
			/*表单优化*/
            $("input[id^=chk_]").iCheck({
                checkboxClass: 'icheckbox_minimal',
                radioClass: 'iradio_minimal',
                increaseArea: '20%'
            });
        }
    });
    /*表格*/
//    $('.com-table tbody tr').each(function(i,item){
//    	var $_item = $(item);
//    	var bg_color = $_item.css("background-color");
/*        var back_color = $(item).css('background-color');
        $(item).find('td').css({'border-top-style':'solid','border-top-width':'1px','border-top-color':back_color,'border-bottom-style':'solid','border-bottom-width':'1px','border-bottom-color':back_color});
        $(item).find('td:first').css({'border-left-style':'solid','border-left-width':'1px','border-left-color':back_color});
        $(item).find('th:first').css({'border-left-style':'solid','border-left-width':'1px','border-left-color':back_color});
        $(item).find('td:last').css({'border-right-style':'solid','border-right-width':'1px','border-right-color':back_color});
        $(item).find('th:last').css({'border-right-style':'solid','border-right-width':'1px','border-right-color':back_color});*/
//    	$_item.live('mouseover',function(){
//        	var $_this = $(this);
//            $_this.find('td').addClass('td-link');
//        	$_this.css("background-color","");
//        	$_this.removeClass();
//        	$_this.addClass("td-link");
//            $_this.click(function(){
//                var back_color_a = $_this.css('background-color');
//                if($_this.find('td').hasClass('td-hover') == false){
//                    $_this.find('td').removeClass('td-link');
//                    $_this.find('td').addClass('td-hover');
//                    $_this.find('td:first').css({'border-left-color':'#469bff'});
//                    $_this.find('td:last').css({'border-right-color':'#469bff'});
//                    $_this.siblings('tr').find('td').removeClass('td-hover');
//                    $_this.siblings('tr').find('td:first').css({'border-left-color':back_color_a});
//                    $_this.siblings('tr').find('td:last').css({'border-right-color':back_color_a});
//                }
/*            	$_this.css({
        			"border-top"			:	"1px solid #469bff !important",
        			"border-bottom"	:	"1px solid #469bff !important"
        		});*/
//            	if($_this.hasClass('td-hover') == false){
//            		$(".td-hover").removeClass("td-hover");
//            		$(".tr-hover").removeClass("tr-hover");
//            		$_this.addClass("tr-hover");
//            	}
//            });
            
//            $_this.mouseout(function(){
//                $_this.find('td').removeClass('td-link');
//                $_this.removeClass("td-link");
//            });
//        });
//    	$_item.live('mouseout',function(){
//        	var $_this = $(this);
//         	$_this.removeClass("td-link");
//         	$_this.css("background-color",bg_color);
//        });
//    });
    
/*    if(navigator.userAgent.indexOf("MSIE")>0){
    	if(navigator.userAgent.indexOf("MSIE 8.0")>0) {
    		change_overflow_hidden_text_align();
    	}else if(navigator.userAgent.indexOf("MSIE 9.0")>0){
    		change_overflow_hidden_text_align();
    	}
    }
    function change_overflow_hidden_text_align(){
    	$('.com-table,.com-table-a,.hyx_int_table,.sale-perfor-td').find('.overflow_hidden').each(function(i,item){
	        var currentObj = $('<pre>').hide().appendTo(document.body);
	        $(currentObj).html($(item).text());
	        var width = currentObj.width();
	        currentObj.remove();
	        if(width > $(item).width()){
	            $(item).css({'text-align':'left'});
	        }else{
	            $(item).css({'text-align':'center'});
	        }
	    });
    }*/
});


//************************************************************************************************
//说明：表头固定
//
//使用：1、调用扩展方法，如：$(".ajax-table").theadFixed();
//
//参数：JSON格式
//  ==普通参数==
//  excludClassName:      原表格克隆后需要删去的类名（防止影响数据注入）,
// 
//*************************************************************************************************
$.fn.extend({
	theadFixed:function(options){
		var defaults ={
				excludClassName:"ajax-table"
		}
		
		var opts = $.extend(defaults,options);
		
		var curTable = $(this);
		var offset = curTable.offset();
		var widths = curTable.outerWidth();
		
		var newHead = curTable.clone();
		curTable.parent().append(newHead)
		newHead.removeClass(opts.excludClassName).css({"position":"fixed","left":offset.left,"top":offset.top,"width":widths,"z-index":1}).find("tbody").remove();
		
		return this;
	}
});

