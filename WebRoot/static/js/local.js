$(function(){
    /*log部分*/
    // $.fn.sel_log = function(){
    //     $(this).click(function(){
    //         $(this).find('.drop').fadeToggle();
    //     });
    // }
    /*log加载*/
    // $.fn.load_log = function(){
    //     $(this).each(function(i,item){
    //         var load_num = 5,i_num = 5,forLiLen = 0,
    //         load_len = $(item).find('.li-load').length;
    //         if($(item).parent().hasClass('hyx-cfu-timeline') == true){
    //             load_num = 5;
    //             i_num = 4;
    //         }
    //         $(item).find('.li-load').css({'display':'none'});
    //         for(var i=0;i<load_num;i++){
    //             $(item).find('.li-load').eq(i).fadeIn(500);  
    //         }
    //         function limitLi(){
    //             var timer = setTimeout(function(){
    //                 if(i_num >= load_len){
    //                         clearTimeout(timer);
    //                 }
    //                 if(load_len - i_num < load_num){
    //                         forLiLen = load_len;
    //                 }else{
    //                         forLiLen = i_num + load_num;
    //                 }
    //                 if(forLiLen <= load_len){
    //                         for(var i=i_num;i<forLiLen;i++){
    //                                 $(item).find('.li-load').eq(i).fadeIn(1000);
    //                         }
    //                 }
    //                 i_num = i_num + load_num;
    //             },200);         
    //         }

    //         $('.hyx-wlm-cent-timeline').scroll(function(){
    //             if($(this)[0].scrollTop + $(this).height() >= $(this)[0].scrollHeight){
    //                 limitLi();
    //             }
    //          });
    //     });
    // }    
    /*log加载伸缩按钮*/
    // $.fn.edit_log = function(){
    //     $(this).each(function(i,item){
    //         $(item).find('.comment').click(function(){
    //             $(this).parent().siblings('.box').slideToggle();
    //         });
    //         $(item).find('.more').click(function(){
    //             if($(this).find('a').text() == '展开'){
    //                 $(this).parent().siblings('dl').css({'height':'auto'});
    //                 $(this).find('a').text('收起');
    //                 $(this).find('i').addClass('i-down');
    //             }else{
    //                 $(this).parent().siblings('dl').css({'height':'30px'}); 
    //                 $(this).find('a').text('展开');   
    //                 $(this).find('i').removeClass('i-down'); 
    //             }
    //         });
    //         $(item).find('.bortop').each(function(j,item_a){
    //             $(item_a).find('.reply').click(function(){
    //                     $(this).parent().parent().siblings('.reply-box').slideToggle();
    //             });
    //         });
    //         $(item).parent().parent().click(function(){
    //             $(this).addClass('li-hover').siblings().removeClass('li-hover');
    //         });
    //     });
    // }

    /*客户跟进tab*/
    // $.fn.cfu_tab = function(){
    //     $(this).find('li').click(function(){
    //         $(this).addClass('select').siblings('li').removeClass('select');
    //         var index = $(this).index();
    //         $(this).parent().parent().parent().find('.hyx-cfu-timeline').hide();
    //         $(this).parent().parent().parent().find('.hyx-cfu-timeline').eq(index).show();
    //     });
    // }
    /*客户跟进加载*/
    // $.fn.cfu_load = function(){
    //     var left_height = $('.hyx-cfu-left').height();
    //     var card_height = $('.hyx-cfu-card').height();
    //     var tab_height = $('.hyx-cfu-tab').height();
    //     var load_height = left_height - card_height - tab_height -65;
    //     $(this).each(function(i,item){
    //         $(item).css({'height':load_height + 'px'})
    //         $(item).find('.right:first').find('.cfu-box').find('i').addClass('i-down');
    //         $(item).find('.right:first').find('.com-none').slideDown();
    //         $(item).find('.right').each(function(j,itema){
    //             $(itema).click(function(){
    //                 if($(this).find('.com-none').css('display') == 'none'){
    //                     $(this).parent().siblings('.li-load').find('.cfu-box').find('i').removeClass('i-down');
    //                     $(this).parent().siblings('.li-load').find('.com-none').slideUp();
    //                     $(this).find('.cfu-box').find('i').addClass('i-down');
    //                     $(this).find('.com-none').slideDown();
    //                 }else{
    //                     $(this).find('.cfu-box').find('i').removeClass('i-down');
    //                     $(this).find('.com-none').slideUp();
    //                 }
                    
    //             });
    //         });
    //     });
    // }
    /*表格*/
    // $.fn.comTable_auto = function(){
    //     $(this).find('tr').each(function(i,item){
    //         var back_color = $(item).css('background-color');
    //         $(item).find('td').css({'border-top-style':'solid','border-top-width':'1px','border-top-color':back_color,'border-bottom-style':'solid','border-bottom-width':'1px','border-bottom-color':back_color});
    //         $(item).find('td:first').css({'border-left-style':'solid','border-left-width':'1px','border-left-color':back_color});
    //         $(item).find('th:first').css({'border-left-style':'solid','border-left-width':'1px','border-left-color':back_color});
    //         $(item).find('td:last').css({'border-right-style':'solid','border-right-width':'1px','border-right-color':back_color});
    //         $(item).find('th:last').css({'border-right-style':'solid','border-right-width':'1px','border-right-color':back_color});
    //         $(item).mouseover(function(){
    //             $(this).find('td').addClass('td-link');
    //             $(this).click(function(){
    //                 var back_color_a = $(this).css('background-color');
    //                 if($(this).find('td').hasClass('td-hover') == false){
    //                     $(this).find('td').removeClass('td-link');
    //                     $(this).find('td').addClass('td-hover');
    //                     $(this).find('td:first').css({'border-left-color':'#469bff'});
    //                     $(this).find('td:last').css({'border-right-color':'#469bff'});
    //                     $(this).siblings('tr').find('td').removeClass('td-hover');
    //                     $(this).siblings('tr').find('td:first').css({'border-left-color':back_color_a});
    //                     $(this).siblings('tr').find('td:last').css({'border-right-color':back_color_a});
    //                 }
    //             });
    //             $(this).mouseout(function(){
    //                 $(this).find('td').removeClass('td-link');
    //             });
    //         });
    //     });

    //     $(this).find('.overflow_hidden').each(function(i,item){
    //         var currentObj = $('<pre>').hide().appendTo(document.body);
    //         $(currentObj).html($(item).text());
    //         var width = currentObj.width();
    //         currentObj.remove();
    //         if(width > $(item).width()){
    //             $(item).css({'text-align':'left'});
    //         }else{
    //             $(item).css({'text-align':'center'});
    //         }
    //     });
    // }

    /*查询部分*/
    // $.fn.comSer_auto = function(){
    //     $(this).find('.select').each(function(){
    //         var s=$(this);
    //         var z=parseInt(s.css("z-index"));
    //         var dt=$(this).children("dt");
    //         var dd=$(this).children("dd");
    //         var _show=function(){dd.slideDown(200);dt.addClass("cur");s.css("z-index",z+1);};   //展开效果
    //         var _hide=function(){dd.slideUp(200);dt.removeClass("cur");s.css("z-index",z);};    //关闭效果
    //         dt.click(function(){dd.is(":hidden")?_show():_hide();});
    //         dd.find(".diy").parent().siblings().click(function(){dt.html($(this).html());_hide();});     //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）
    //         $('.apply-btn').click(function(){ _hide();}); 
    //         $("body").click(function(i){ !$(i.target).parents(".select").first().is(s) ? _hide():"";});
    //     });
    //     $(this).find('.input-query,.hide-span').click(function(){
    //         var input_query = $(this).parent().find('.input-query');
    //         input_query.focus();
    //         $(this).parent().find('.hide-span').hide();
    //         input_query.blur(function(){
    //             if($(this).val() == ''){
    //                 $(this).parent().find('.hide-span').show();
    //             }
    //          }); 
    //     });
    //     var isShow = false; 
    //     $(this).find('.more').mouseover(function(){
    //         if(!isShow){
    //             $(this).parent().addClass('list-topbor').parent().find('.list-hid').show();
    //         }
    //         isShow = true;
    //     });
    //    $(this).find('.list-topbor').live("mouseleave",function(){
    //         $(this).removeClass('list-topbor').find('.list-hid').hide();
    //         isShow = false;
    //     });
    // }
    
     /*公海客户池（资源列表选择分组）*/
     // $.fn.hsmLeft_list = function(){
     //     $(this).find('dd').each(function(i,item){
     //        $(item).click(function(){
     //            if($(this).className != 'dd-hover'){
     //                $(this).addClass('dd-hover').siblings().removeClass('dd-hover');
     //                $(this).parent().parent().siblings().find('.com-table').eq(i).show().siblings('.com-table').hide();
     //            } 
     //        });
     //     });   
     // }
     
     /*表单优化*/
     // $.fn.form_auto = function(){
     //    $(this).iCheck({
     //        checkboxClass: 'icheckbox_minimal',
     //        radioClass: 'iradio_minimal',
     //        increaseArea: '20%'
     //    });
     // }

});