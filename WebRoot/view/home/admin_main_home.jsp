<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理者首页</title>
    <%@ include file="/common/include.jsp" %>
</head>
<body>
	<h2 class="hyx-hy-logo"></h2>
	<div class="hyx-hy-lx">
		<dl class="lx-a fl_l product-qq-title" onclick="$('#QQOnLine iframe').contents().find('#launchBtn').click();">
			<dt></dt>
			<dd title="服务QQ：4008262277">4008262277</dd>
		</dl>
		<dl class="lx-b fl_r">
			<dt></dt>
			<dd class="product-serviceTel">400-826-2277</dd>
		</dl>
	</div>
	<!-- 在线客服 -->
<!-- <div id="QQOnLine" style="display: none;">
	<script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODAwNjMxNF8xNjk2MTFfNDAwODI2MjI3N18"></script>
</div> -->
</body>
<script type="text/javascript">
function call(phone){
	var param = "<xml><Oparation Phone=\""+phone+"\" /></xml>";
	try{
		external.OnCallXml(param);
	}catch(e){
		external.OnCall(phone+"_");
	}
}
</script>
</html>