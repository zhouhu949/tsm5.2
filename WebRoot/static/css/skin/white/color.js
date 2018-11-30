
$(function(){
$("#nav > li").hover(function(){
                    $(this).find('ul:first').siblings().css({'color':'#555'});
                    $(this).find('ul:first').css({visibility: "visible",display: "block"});
                },function(){
                    //$(this).css({'color':'#888'});
                    $(this).find('ul:first').siblings().css({'color':'#555'});
                    $(this).find('ul:first').css({visibility: "hidden"});
                });
})