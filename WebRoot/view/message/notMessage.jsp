<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
 <%@ include file="/common/include.jsp" %>
<title>消息中心-暂无消息提醒</title>
<style type="text/css">
body{background-color:#f4f4f4 !important;overflow:hidden;}
</style>

<script type="text/javascript">
$(function(){

   /*左边、右边铺满整屏*/
        var winhight_a=window.parent.$('.hyx-mc-right').height(); 
        $(".hyx-aspc").css({'height':winhight_a+'px'}); 
		$(".bomp-time").css({'height':winhight_a-$(".bomp-time").offset().top-50+'px'}); 

});
</script>
<script type="text/javascript">
window.onload=function(){
	var height = $(".hyx-mca").height();
	window.parent.$("#iframepage").css({"height":height+"px"});
};
</script>
</head>
<body> 
<div class="hyx-aspc hyx-mca">
	<div class="hyx-mca-top">
		<label class="lab fl_l">
			<c:choose>
				<c:when test="${type eq 1 }">客户联系提醒</c:when>
				<c:when test="${type eq 2 }">点评提醒</c:when>
				<c:when test="${type eq 3 }">未接来电提醒</c:when>
				<c:when test="${type eq 4 }">到期提醒</c:when>
				<c:when test="${type eq 5 }">通知公告提醒</c:when>
				<c:when test="${type eq 6 }">待办审核提醒</c:when>
				<c:when test="${type eq 7 }">系统消息提醒</c:when>
				<c:when test="${type eq 8 }">其他消息提醒</c:when>
			</c:choose>
		</label>
	</div>
	<div class="bomp-time" style="text-align:center;">
		<label class="hyx-mcn-bg"><span>暂无消息提醒！</span></label>
	</div>
</div>
</body>
</html>