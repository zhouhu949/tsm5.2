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
	
	 // 点击模板
     $('.bomp_mess_right').find('dd').click(function(){
     	$('.bomp_mess_left').find('.bomp-pos-a').find('.lab_hid').hide();
	 	var area_val = $('.bomp_mess_left').find('.area_box').find('textarea').html();
	 	$('.bomp_mess_left').find('.area_box').find('textarea').html($(this).find('label').html());
	 	noteCount(); // 短信字数
	 });
    
	// 选择模板分类 展示效果
	$(".bomp_mess_right").find('.select').each(function(){
        var s=$(this);
        var z=parseInt(s.css("z-index"));
        var dt=$(this).children("dt");
        var dd=$(this).children("dd");
        var _show=function(){dd.slideDown(200);dt.addClass("cur");s.css("z-index",z+1);};   //展开效果
        var _hide=function(){dd.slideUp(200);dt.removeClass("cur");s.css("z-index",z);};    //关闭效果
        dt.click(function(){dd.is(":hidden")?_show():_hide();});
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
});

// 点击模板分类 跳转
function tmpGroupButt(id){
	var url = "${ctx }/call/sms/toRightSmsTemp.do?tsgId="+id;
	$("#rightForm").ajaxSubmit({
		url:url,
		dataType:'html',
		type : 'post',
		error:function(){},
		success:function(data){
			$("#bomp_mess_right").html(data);		
		}
	});	
}
</script>
<form id="rightForm"  method="post">
			<input type="hidden" name="tsgId" value="${tsgId}">
			<c:set var="tsgName"  value="短信类别"></c:set>
		   <c:forEach items="${smsTempGroups }" var="v" >
				<c:if test="${v.tsgId eq tsgId }">
					<c:set var="tsgName">${v.groupName }</c:set>
				</c:if>
			</c:forEach>
		<dl class="dl_a">
			<dt>常用模板:
				<dl class="select">
					<dt>${tsgName}</dt>
					<dd>
						<ul>
							<li><a href="###" onclick="tmpGroupButt('')">短信类别</a></li>
							<li><a href="###" onclick="tmpGroupButt('')">全部</a></li>
							<c:forEach var="vs" items="${smsTempGroups }">
								<li><a href="###" onclick="tmpGroupButt('${vs.tsgId}')">${vs.groupName }</a></li>
							</c:forEach>
						</ul>
					</dd>
				</dl>
			</dt>
			<c:forEach items="${smsTemps}" var="temp" varStatus="vs">
				<dd title="${vs.count}、${temp.content}" class="dd_a dd-hover">
					<span class="fl_l">${vs.count}.</span><label class="temp-label fl_l overflow_hidden">${temp.content}</label>
					<span class="drop" style="display:none;">
						<label class="arrow"><em>◆</em><span>◆</span></label>
						<label class="box">${vs.count}、${temp.content}</label>
					</span>
				</dd>
			</c:forEach>
		</dl>
</form>