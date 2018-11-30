<!DOCTYPE html>
<head>
  <meta charset="UTF-8">
  <title>慧营销4.0</title>
  <link href="${ctx}/static/css/style.css" rel="stylesheet" type="text/css" />
</head>
<script type="text/javascript">
  if(top.location != self.location) {
    top.location = self.location;
  }
  function reloadcode(){
    var verify=document.getElementById("code");
    verify.setAttribute("src","/getCaptcha?t="+Math.random());
  }
</script>
<body>
<div class="login_bj">
  <div class="login_logo">
    <div style="width: 100%;height: 100%;margin-left: 8px;">
      <img src="${ctx}/static/images/logo1.png" width="100%" height="114" />
    </div>
  </div>
  <div id="login">
    <form action="login" method="post">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="16%" height="35" align="right"><img src="${ctx}/static/images/yhm.jpg" width="16" height="16" /></td>
          <td width="17%" height="35" align="center">用户名</td>
          <td width="67%" height="35">
            <input type="text" name="account" id="account" value="" class="login_txt" /></td>
        </tr>
        <tr>
          <td height="35" align="right"><img src="${ctx}/static/images/mm.jpg" width="16" height="16" /></td>
          <td height="35" align="center">密&nbsp;码</td>
          <td height="35"><input type="password" name="password" id="password" value="" class="login_txt"  /></td>
        </tr>
        <tr>
          <td height="35" align="right">&nbsp;</td>
          <td height="35" align="center">验证码</td>
          <td height="35"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="46%"><input type="text" name="code" id="code1"  class="login_txt1" value="8888" /></td>
              <td width="54%"><img src="${ctx}/getCaptcha" id="code" onclick="reloadcode()" width="66" height="24" /></td>
            </tr>
          </table>
          </td>
        </tr>
        <tr>
          <td height="22" align="right">&nbsp;</td>
          <td height="22">&nbsp;</td>
          <td height="22"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="9%"><input type="checkbox" name="rember" id="rember" checked /></td>
              <td width="91%">记住密码</td><span style="color:red"></span>
            </tr>
          </table></td>
        </tr>
        <tr>
          <td height="40" align="right">&nbsp;</td>
          <td height="40">&nbsp;</td>
          <td height="40"><input name="button" type="submit" class="dl_btn" id="button" value=" "/></td>
        </tr>
      </table>
    </form>
  </div>
  <div class="login_wz">
    技术所有：企峰通信技术有限公司
  </div>
</div>
</body>
</html>
