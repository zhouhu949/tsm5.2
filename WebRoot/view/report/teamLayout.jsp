<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>销售统计-团队客户分布</title>
	<script type="text/javascript">
		$(function(){
			/*客户跟进tab*/
		    $('.year-sale-play').find('li').click(function(){
		        if(!$(this).hasClass('select')){
		    	$(this).addClass('select').siblings('li').removeClass('select');
		        var rel = $(this).attr('rel');
		        $("#iframepage").attr("src",rel);
		        }
		    });
	    });
	</script>
</head>
<body>
	<div class="sales-statis-conta">
	<div class="year-sale-play">
		<ul class="ann-sale-plan clearfix" style="width:960px">
			<li class="li_first select" rel="${ctx }/layout/team/custReport">客户分布统计</li>
			<li rel="${ctx }/layout/team/custStatusReport">客户状态分布详情</li>
			<li rel="${ctx }/layout/team/saleProcReport">销售进程分布详情</li>
			<li rel="${ctx }/layout/team/custTypeReport">客户类型分布详情</li>
			<li class="li_last" rel="${ctx }/layout/team/productReport">产品类型分布详情</li>
		</ul>
	</div>
	<div class="hyx-hsrs-bot">
		<iframe src="${ctx }/layout/team/custReport" width="100%" height="100%" id="iframepage" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
	</div>
	</div>
</body>
</html>