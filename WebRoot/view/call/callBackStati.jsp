<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/include.jsp"%>
	<title>通信管理-回拨统计</title>
		<script type="text/javascript">
		$(function(){
			/*客户跟进tab*/
		    $('.year-sale-play').find('li').click(function(){
		        $(this).addClass('select').siblings('li').removeClass('select');
		        var index = $(this).index();
		        var url = $(this).attr('url');
		        $("#iframepage").attr("src",url);
		    });

		    $(".year-play-tab").find('.select').each(function(){
		        var s=$(this);
		        var z=parseInt(s.css("z-index"));
		        var dt=$(this).children("dt");
		        var dd=$(this).children("dd");
		        var _show=function(){dd.slideDown(200);dt.addClass("cur");s.css("z-index",z+1);};   //展开效果
		        var _hide=function(){dd.slideUp(200);dt.removeClass("cur");s.css("z-index",z);};    //关闭效果
		        dt.click(function(){dd.is(":hidden")?_show():_hide();});
		        dd.find(".diy").parent().siblings().click(function(){dt.html($(this).html());_hide();});     //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）
		        $('.apply-btn').click(function(){ _hide();}); 
		        $("body").click(function(i){ !$(i.target).parents(".select").first().is(s) ? _hide():"";});
		    });
		});
		</script>
</head>
<body>
	<div class="year-sale-play hyx-hsrs-top">
		<ul class="ann-sale-plan clearfix">
			<li class="select li_first" name="a" url="${ctx }/callBack/stati/callBackStatiDay">日统计分析</li>
			<li class="li_last" name="b" url="${ctx }/callBack/stati/callBackStatiMonth">月统计分析</li>
		</ul>
	</div>
	<div class="hyx-hsrs-bot">
		<iframe src="${ctx }/callBack/stati/callBackStatiDay" width="100%" height="100%" id="iframepage" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
	</div>
	<p class="hyx-mpe-ptit"><label class="com-red" >注：</label>
		<span>挂机短信发送后，单位周期内回拨多次，仅算最近一次。</span></p>
</body>
</html>