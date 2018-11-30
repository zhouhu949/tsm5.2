<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<html>
<head>
<title>解绑邮箱</title>
<script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery.form.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js${_v}" dialog-theme="default"></script><!--可移动弹框插件-->
<script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js${_v}"></script><!--可移动弹框插件公共js-->
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css"><!--弹框样式-->
<style>
	.content {width: 320PX;font-size: 14px;margin:0 auto;}
	.content p{line-height: 28px;}
	span.email{color: #00f;}
	span.tips{color: #f00;}
	.input_div{height:32px;}
	.input_div input{width: 80px;height: 22px;font-size: 14px;line-height: 22px;}
</style>
<script type="text/javascript">
var SEND_CODE_SUCCESS = '验证码已发至您的邮箱，请在10分钟内完成验证！';
var SEND_CODE_ERROR = '发送验证码失败，请稍后再试！';
var CODE_VALID = '验证码错误，请重新发送并验证！';
var CODE_NULL = '请输入验证码！';
$(function(){
	var isSubmit = false;
	$('#sendCode').click(function(){
		if(!isSubmit){
			isSubmit = true;
			$.ajax({
				url:ctx+'/email/config/sendUnBindKeyCode',
				type:'post',
				dataType:'json',
				error:function(){isSubmit = false;},
				success:function(data){
					isSubmit = false;
					if(data == '0'){
						setMsg(SEND_CODE_SUCCESS);
				// 		startIntever();
					}else{
						setMsg(SEND_CODE_ERROR);
					}
				}
			});
		}
	});
	$("#okBtn").click(function(){
		if(!isSubmit){
			isSubmit = true;
			var code = $("#code").val();
			if(code == null || code == ''){
				setMsg(CODE_NULL);
				isSubmit = false;
				return;
			}
			$.ajax({
				url:ctx+'/email/config/checkUnBindCode',
				type:'post',
				data:{code:code},
				dataType:'json',
				error:function(){isSubmit = false;},
				success:function(data){
					isSubmit = false;
					if(data == '0'){
						window.location.href=ctx+'/view/follow/idialog/newBind.jsp';
					}else{
						setMsg(CODE_VALID);
					}
				}
			});
		}
	});
});
function startIntever(){
	var i = 10;
	$('#sendCode').text('('+i+'s)重新发送');
	var time1 = setInterval(function(){
		if(i == 0){
			clearInterval(time1);
			$('#sendCode').text('发送验证码');
		}else{
			$('#sendCode').text('('+i+'s)重新发送');
		}
		i--;
	}, 1000)
}
function setMsg(msg){
	$(".tips").text(msg);
}
</script>
</head>
<body> 
<div class='bomp-cen'>
	<c:choose>
		<c:when test="${empty bindEmail }">
			<div class="bomp-tip-a" style="margin-top:75px;"><label class="tip-b fl_l" style="margin-left:120px;margin-right:10px;"></label><span class="sp-a fl_l" style="font-size:12px;">未绑定邮箱</span></div>
		</c:when>
		<c:otherwise>
			<div class='content'>
				<p><span>为了您的帐号安全，修改邮箱请先进行安全验证</span></p>
				<p><span>验证邮箱：</span><span class="email">${bindEmail }</span></p>
				<div class="input_div"><input type='text' name="code" id="code" placeholder='邮箱验证码' /></div>
				<div><a href="javascript:;" id="sendCode" class="com-btna cancel-btn"><label>发送验证码</label></a></div>
				<p><span class="tips"></span></p>
				<div class='bomb-btn' style="margin-top:25px;">
					<label class='bomb-btn-cen'>
					<a href="javascript:;" id="okBtn" class="com-btna com-btna-sty sure-btn"><label>确定</label></a>
					</label>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<script type="text/javascript">
	var JPlaceHolder = {
	    //检测
	    _check : function(){
	        return 'placeholder' in document.createElement('input');
	    },
	    //初始化
	    init : function(){
	        if(!this._check()){
	            this.fix();
	        }
	    },
	    //修复
	    fix : function(){
	        jQuery(':input[placeholder]').each(function(index, element) {
	            var self = $(this), txt = self.attr('placeholder');
	            self.wrap($('<div></div>').css({position:'relative', zoom:'1', border:'none', background:'none', padding:'none', margin:'none'}));
	            var pos = self.position(), h = self.outerHeight(true), paddingleft = self.css('padding-left');
	            var holder = $('<span></span>').text(txt).css({position:'absolute', left:pos.left, top:pos.top, height:h, lineheight:h+'px', paddingLeft:paddingleft, color:'#aaa'}).appendTo(self.parent());
	            self.focusin(function(e) {
	                holder.hide();
	            }).focusout(function(e) {
	                if(!self.val()){
	                    holder.show();
	                }
	            });
	            holder.click(function(e) {
	                holder.hide();
	                self.focus();
	            });
	        });
	    }
	};
	//执行
	jQuery(function(){
	    JPlaceHolder.init();    
	});
</script>
</body>
</html>