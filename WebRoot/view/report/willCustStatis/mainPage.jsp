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
			<ul class="newwill-cust-plan clearfix">
				<li _url="${ctx}/newWill/returndaydata" class="li_first select">今日数据</li>
				<li _url="${ctx}/newWill/oldDaydata" class="li_last">历史数据</li>
			</ul>
		</div>
		<div class="hyx-hsrs-bot">
			<iframe src="${ctx}/newWill/returndaydata" width="100%"  id="iframepage" frameborder="0"
				scrolling="no" marginheight="0" marginwidth="0"></iframe>
		</div>
	</div>
</body>
</html>
