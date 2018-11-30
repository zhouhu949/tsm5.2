<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%@ include file="/common/common.jsp"%>
    <title>慧营销V5.0 Web1</title>
    <script type="text/javascript" src="${ctx}/static/js/jquery-1.8.2.min.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/citySelect/citySel.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/citySelect/popt.js${_v}"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/commonCity.css${_v}"/><!-- 下拉展示区样式 -->
</head>
<body>
	<input type="text" id="city">
	<script>
	$(function(){
		$("#city").click(function(e){
			console.log(e);
			var $this = $(this)
			SelCity(this,e,function(data){
				$this.val(data)
			});
		})
	})
	</script>
</body>
</html>
