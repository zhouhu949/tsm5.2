<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function(){
	/*表单优化*/
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
	
    $(".sign-of-use").find('.select').each(function(){
        var s=$(this);
        var z=parseInt(s.css("z-index"));
        var dt=$(this).children("dt");
        var dd=$(this).children("dd");
        var _show=function(){dd.slideDown(200);dt.addClass("cur");s.css("z-index",z+1);};   //展开效果
        var _hide=function(){dd.slideUp(200);dt.removeClass("cur");s.css("z-index",z);};    //关闭效果
        dt.click(function(){dd.is(":hidden")?_show():_hide();});
      	
      	$(document).click();
        $(document).click(function(i){
            !$(i.target).parents(".select").first().is(s) ? _hide():"";
        });
    });
    
    $('.bomp_mess_right').find('.dd_a').mouseover(function(){
        $(this).find('.drop').fadeIn(1);
    });
    $('.bomp_mess_right').find('.dd_a').mouseleave(function(){
        $(this).find('.drop').fadeOut(1);
    });
    
 	// 点击个性签名内容
	$('.pers-sign-top').click(function(){
		$('.cke_wysiwyg_frame').contents().find('body').append($(this).html());
		$('.cke_wysiwyg_frame').contents().find('.sign-top-cw').css({'border-top':'dashed 1px #e1e1e1','padding-top':'10px'});
	});
});

// 点击签名标题 显示签名内容
function signButt(id){
 	if(id != null && id != ""){
 		$("div[id^=sign_]").each(function(){
 			$(this).hide();	
 		});
 		$("#sign_"+id).show();
 	}else{
 		$("div[id^=sign_]").each(function(){
 			$(this).hide();	
 		});
 	}
	
}
</script>
<form id="rightForm"  method="post">
		<div class="hyx-cfu-tab">个性签名</div>
		<div class="hyx-wlm-cent-timeline hyx-cfu-timeline" style="overflow-y:auto">
			<div class="sign-of-use clearfix">
				<label class="fl_l" for="" style="width:110px;">选择签名：</label>
				<dl class="select fl_l" style="margin-bottom:4px;position:relative;z-index:0;">
					<dt>个性签名</dt>
					<dd style="left:0px;top:26px;">
						<ul>
							<li><a href="###"  onclick="signButt('');"   title="不使用">不使用</a></li>
							<c:forEach items="${signs }" var="sign" >
								<li><a href="###"  onclick="signButt('${sign.id}');" title="${sign.title }">${sign.title }</a></li>
							</c:forEach>
						</ul>
					</dd>
				</dl>
			</div>
			<div class="amil-pers-sign">
				<c:forEach items="${signs }" var="sign" >
						<div class="pers-sign-top" id="sign_${sign.id}" style="display: none">
							${sign.context}
						</div>
				</c:forEach>
			</div> 
		</div>
		
</form>