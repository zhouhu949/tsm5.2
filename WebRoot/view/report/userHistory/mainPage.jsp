<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售统计-小组历史数据</title>
<!--本页面样式-->
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<script type="text/javascript">
	$(function() {
		/*tab选择*/
		$('.year-sale-play').find('li').click(function() {
			if($(this).hasClass("select") == false){
				$(this).addClass('select');
				$(this).siblings('li').removeClass('select');
	    		var _url = $(this).attr("_url");
	    		$("#iframepage").attr("src", _url);
	    	};
		});
	})
	function changeUrl(_url) {
		$("#iframepage").attr("src", _url);
	}
</script>
</head>
<body>
	<div class="sales-statis-conta clearfix" style="padding-top:15px">
		<div class="year-sale-play" style="margin-top:0">
			<ul class="ann-sale-plan clearfix">
				<li _url="${ctx}/report/userStatis/saleResult?pageType=res" class="li_first">销售结果统计</li>
				<li _url="${ctx}/report/userStatis/dayData" class="select">日统计分析</li>
				<li _url="${ctx}/report/userStatis/monthData" class="li_last">月统计分析</li>
			</ul>
		</div>
		<div class="hyx-hsrs-bot">
			<iframe src="${ctx}/report/userStatis/dayData" width="100%" height="100%" id="iframepage" frameborder="0"
				scrolling="no" marginheight="0" marginwidth="0"></iframe>
		</div>
	</div>
</body>
</html>
