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
        if(dd.find('a').hasClass('diy') == true){
            dd.find(".diy").parent().siblings().click(function(){dt.html($(this).html());_hide();});     //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）

        }else{
            dd.find("a").click(function(){dt.html($(this).html());_hide();});     //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）
        }
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

    $('.perso-sign-edit').click(function(){
    	var id =$("input[name='id']").val();
    	pubDivShowIframe('alert_new_char_sign','${ctx}/email/sign/toOperaSign?id='+id,'编辑签名',630,420);
	});

	// 点击个性签名内容
	$('.pers-sign-top').click(function(){
		$('.cke_wysiwyg_frame').contents().find('body').append($(this).html());
		$('.cke_wysiwyg_frame').contents().find('.sign-top-cw').css({'border-top':'dashed 1px #e1e1e1','padding-top':'10px'});
	});
});

function addSign(){
	pubDivShowIframe('alert_new_char_sign','${ctx}/email/sign/toOperaSign','新建签名',630,420);
}

// 点击签名查询
function tmpGroupButt(id){
	var url = "${ctx }/call/email/toEmailBatchSendRight.do?id="+id;
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

function delete_(id){
	queDivDialog("res_remove_cust","是否删除？",function(){
		$.ajax({
			url:ctx+'/email/sign/deleteSign',
			type:'post',
			data:{id:id},
			dataType:'json',
			error:function(){alert('网络异常，请稍后再试！')},
			success:function(data){
				if(data == 0){
					window.top.iDialogMsg("提示",'删除成功！');
					setTimeout('tmpGroupButt("")',1000);
				}else{
					window.top.iDialogMsg("提示",'删除失败！');
				}
			}
		});
	})
}
</script>
<form id="rightForm" method="post">
<div class="hyx-cfu-tab hyx-cfu-tab-a">个性签名</div>
	<div class="hyx-wlm-cent-timeline hyx-cfu-timeline" style="height:auto;overflow-y:auto">
		<div class="sign-of-use clearfix">
			<input type="hidden" name="id"  value="${item.id }">
			<c:set var="signName" value="个性签名"/>
			<c:forEach items="${signs}" var="sign">
				<c:if test="${sign.id eq item.id }"><c:set var="signName" value="${sign.title}"/></c:if>
			</c:forEach>
			<label class="fl_l" for="">选择签名：</label>
			<dl class="select fl_l" style="margin-bottom:4px;">
				<dt>${signName }</dt>
				<dd style="left:120px;top:41px;">
					<ul>
						<li><a href="###" onclick="tmpGroupButt('')">不使用</a></li>
						<c:forEach items="${signs}" var="sign">
							<li><a href="###" onclick="tmpGroupButt('${sign.id}')" title="${sign.title}">${sign.title}</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
			<a class="add-sign-qm" href="###" onclick="addSign()">+</a>
		</div>
		<div class="amil-pers-sign">
			<div class="pers-sign-top">
				${item.context}
			</div>
				<c:if test="${not empty item}">
					<div class="perso-sign-butt">
						<a class="com-btnc bomp-btna com-btna-sty perso-sign-edit fl_l" href="###"><label>编辑</label></a>
						<a class="com-btnd bomp-btna perso-sign-dele fl_l" onclick="delete_('${item.id}')" id="close02" href="###"><label>删除</label></a>
					</div>
				</c:if>
		</div> 
	</div>
</form>