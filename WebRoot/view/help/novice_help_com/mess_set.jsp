<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<html>
<head>
<title>系统设置-消息设置</title>

<link rel="stylesheet" href="${ctx}/static/js/intro/css/introjs.css" type="text/css" /><!--新手引导-->
<link rel="stylesheet" href="${ctx}/static/js/intro/css/bootstrap-responsive.min.css" type="text/css" /><!--新手引导-->
 <style>
      .introjs-skipbutton {
        border:0 none;
        color:#000;
        position:absolute;
        bottom:11px;
        right:6px;
        z-index:555;
      }
    </style>

<!--本页面js-->
<script type="text/javascript">
$(function(){
	/*switch开关*/
    $('.hyx-sms').find('.switch').click(function(){
    	if($(this).attr('name') == 'on'){
    		$(this).addClass('switch-hover');
    		$(this).attr('name','off');
    	}else{
    		$(this).removeClass('switch-hover');
    		$(this).attr('name','on');
    	}
    });
});
</script>
</head>
<body> 
<div class="hyx-na-tit fl_l"><label class="lab">消息中心设置</label></div>
<div class="hyx-sms hyx-ms fl_l">
	<div class="fl_l" id="step_1" style="width:100%;">
		<p class="tit">
			<label class="lab fl_l"><i class="tip"></i>客户联系设置</label>
		</p>
		<p class="tit_a">
			<label class="lab">客户跟进提前：</label>
			<select class="sel">
				<option>5分钟</option>
				<option>10分钟</option>
				<option>15分钟</option>
				<option>20分钟</option>
			</select>
			<label class="lab">发送提醒。</label>
			<i class="switch fl_r" name="on"></i>
		</p>
		<p class="tit_a">
			<label class="lab">延后呼叫提前：</label>
			<select class="sel">
				<option>5分钟</option>
				<option>10分钟</option>
				<option>15分钟</option>
				<option>20分钟</option>
			</select>
			<label class="lab">发送提醒。</label>
			<i class="switch fl_r" name="on"></i>
		</p>
		<p class="tit_a">
			<label class="lab">跟进警报提前：</label>
			<select class="sel">
				<option>1天</option>
				<option>2天</option>
				<option>3天</option>
				<option>4天</option>
			</select>
			<label class="lab">发送提醒。</label>
			<i class="switch fl_r" name="on"></i>
		</p>
	</div>
	<div class="fl_l" id="step_2" style="width:100%;">
		<p class="tit">
			<label class="lab fl_l"><i class="tip"></i>续费提醒设置</label>
		</p>
		<p class="tit_a">
			<label class="lab">短信剩余量为：</label>
			<input type="text" class="ipt" value="" />
			<label class="lab">条时发送提醒。</label>
		</p>
		<p class="tit_a">
			<label class="lab">坐席剩余时间：</label>
			<input type="text" class="ipt" value="" />
			<label class="lab">天时发送提醒。</label>
			<label class="lab">订单剩余时间：</label>
			<input type="text" class="ipt" value="" />
			<label class="lab">天时发送提醒。</label>
		</p>
	</div>
	<div class="fl_l" id="step_3" style="width:100%;">
		<p class="tit">
			<label class="lab fl_l"><i class="tip"></i>月计划提交设置</label>
		</p>
		<p class="tit_a">
			<label class="lab">每月计划在月底前</label>
			<input type="text" class="ipt" value="" />
			<label class="lab">天未提交时发送提醒。</label>
		</p>
	</div>
	<div class="com-btnbox" id="step_4">
		<a href="javascript:;" class="com-btna com-btna-sty step-save" onclick="javascript:introJs().exit();"><label>保存</label></a>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/intro/js/intro.js"></script><!--新手引导-->
<script type="text/javascript">
var intro = introJs();
function startIntro(){
  	intro.setOptions({
  		//对应的按钮
    prevLabel:"&larr; 上一步", 
    nextLabel:"下一步 &rarr;",
    skipLabel:"",
    doneLabel:"下一步 &rarr;",
    steps: [
      {
        element: '#step_1',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>请进行客户联系设置操作。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'right'
      },
      {
        element: '#step_2',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>请进行续费提醒设置操作。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'right'
      },
      {
        element: '#step_3',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>请进行月计划提交设置操作</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'right'
      },
      {
        element: '#step_4',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>请点击“保存”。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'right'
      }
    ]
  });

  intro.start();
}
window.onload=function(){
	startIntro();
}
$(function(){
	$('.introjs-skipbutton').live('click',function(){
		pubDivShowIframe('alert_novice_help_back_b','${ctx}/view/help/novice_help_com/alert_novice_help_back_b.jsp','消息设置指导完成',650,350);
	});
	window.parent.$('.i-close').live('click',function(){
        window.parent.window.location.href = '${ctx}/main';
    });
});
</script>
</body>
</html>