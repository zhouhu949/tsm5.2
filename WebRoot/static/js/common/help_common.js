/***********************************************
 * Grid封装元素共同验证_共同JS文件
 * @author haoqj
 * @date 2013-05-15
 ***********************************************/

/**
 * 输入框提示功能(不影响元素内容,提示聚集与输入两种方式，可自行扩展)
 * @param tagId	要作用元素ID
 * @param tipsMsg 提示信息
 * 
 */
function tipsShow(tagId,tipsMsg){
	$("#"+tagId).wrap("<label style='display:block;position:relative;'></label>");
	$("#"+tagId).before("<span style='position:absolute;float:left;line-height:28px;left:10px;color:#BCBCBC;cursor:text;z-index:1'>"+tipsMsg+"</span>");
	
	$("#"+tagId).each(function(){
		if($(this).val()!=""){
   			$(this).siblings().hide();
   		}else{
   			$(this).siblings().show();
   		}
		
		$(this).siblings().click(function(){
			$(this).siblings().focus();
		})
		
    	// 聚焦型输入框验证
		$(this).focus(function(){
			$(this).siblings().hide();
		}).blur(function(){
			if($(this).val()!=""){
				$(this).siblings().hide();
			}else{
				$(this).siblings().show();
			}
		})
		
//		// 输入型输入框验证
//		$(this).keyup(function(){
//			$(this).siblings().hide();
//		}).blur(function(){
//			if($(this).val()!=""){
//				$(this).siblings().hide();
//			}else{
//				$(this).siblings().show();
//			}
//		})
	});
}



