<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
	<title>慧营销V5.0 Web1</title>
	<%@ include file="/common/common.jsp"%>
    <link href="${ctx}/static/css/style.css${_v}" rel="stylesheet" type="text/css"/>
    <script src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js${_v}" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/login.js${_v}"></script>
</head>
<script type="text/javascript">

    if (top.location != self.location) {
        top.location = self.location;
    }
    function reloadcode() {
        var verify = document.getElementById("code");
        verify.setAttribute("src", "/getCaptcha?t=" + Math.random());
    }
    jQuery(document).ready(function () {
        try {
            //$("#mainlogin").hide();
            external.RefreshMainPage();//小房子首页
            // external.ExitMainPage();//注销
        } catch (e) {
            // alert(e);
            $("#mainlogin").css('display','block');
        }
    });
</script>
	<body>
	<div id="mainlogin" style="display: none">
			<div class="login_top">
				<div class="login_topleft">
					<img src="${ctx}/static/images/logo.png" alt="加载图片" class="logoIcon"/>
					<span class="login_topline"></span>
					<img src="${ctx}/static/images/focusenterprisemarketing.png" alt="加载图片" class="focusmarketing"/>
				</div>
				<div class="login_topright"><a href="http://www.qftx.com/" target="_blank">企蜂通信官网</a></div>
			</div>
			<div class="login_middle">
				<img src="${ctx}/static/images/banner02.png" alt="加载图片"  class="middle_banner"/>
				<div class="middle_right_inputbox">
					<form action="${ctx}/login" id="loginForm" method="post" onsubmit="return loginChk()">
						<p class="inputbox_title">登录</p>
						<p class="inputbox_title">
							<img src="${ctx}/static/images/login_accounts.png" alt="加载图片" />
							<input type="text" class="login_inputsStyle" name="account" id="account"  maxlength="30" autocomplete="off" />
						</p>
						<p class="inputbox_title">
							<img src="${ctx}/static/images/login_password.png" alt="加载图片" class="passwordicon"/>
							<input  class="login_inputsStyle" type="password" name="password"  id="password"   maxlength="60" autocomplete="off"/>
						</p>
						<p class="inputbox_title yzm_outerp">
							<input type="text" class="login_inputsStyle new_loginyzm" maxlength="5" name="code" id="code1"  value="" onkeydown='if(event.keyCode==13) return document.forms[0].submit();' />
							<img src="${ctx}/getCaptcha" id="code" onclick="reloadcode()" alt="加载图片" />
						</p>
						<div class="bc" id="errmsg">${message}</div>
						<button type="button" class="new_loginBtn" id="login_btn">登&nbsp;&nbsp;录</button>
					</form>
				</div>
			</div>
			<div class="login_bottom">
				<a href="http://www.qftx.com/onlineweb/downloadFouthPageNew.html" target="_blank">客户端下载</a><span>&nbsp;&nbsp;|&nbsp;&nbsp;客服热线：400-826-2277&nbsp;&nbsp;|&nbsp;&nbsp;</span><span>版权所有@2016企蜂通信   浙ICP备1303780号</span>
			</div>
		</div>
	</body>
</html>