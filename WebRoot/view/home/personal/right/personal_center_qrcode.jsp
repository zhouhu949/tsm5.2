<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html >
<head>
    <meta charset="UTF-8">
    <title>个人中心页面-右侧下载页面</title>
    <%@ include file="/common/include-home-cut-version.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/personal-center.css${_v}"/>
    <script src="${ctx }/static/js/jquery/jquery.qrcode.min.js"></script>
    <style>
    .box-title{
        width:200px;
        height:30px;
        margin:0 auto 20px;
        line-height:30px;
        font-size: 18px;
      }
      #qrbox{
        width:200px;
        height:200px;
        margin:0 auto;
      }
    </style>
</head>
<body>
	<div class="personal-center-right-head-box hyx-layout">
		<div class="personal-center-appdownload-items hyx-layout-content">
		    <div class="box-title">营销推广码</div>  
			<div id="qrbox"></div>
		</div>
	</div>
	<script type="text/javascript">
	      // var url =  ctx+"/popup/toPersonal_center_restable?orgId=${orgId}&userAccount=${userAccount}";
	       var url = "http://hyx.ikoushuo.net:8082/resTable/toPersonal_center_restable?orgId=${orgId}&userAccount=${userAccount}"
	       $("#qrbox").qrcode({
	           text:url,
	           width:200,
	           height:200
	       })
	</script>
</body>
</html>
