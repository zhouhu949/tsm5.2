<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<html>
<head>

<title>系统设置-销售管理设置</title>

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
    		$(this).parent().next('.switch-hid').hide();
    	}else{
    		$(this).removeClass('switch-hover');
    		$(this).attr('name','on');
    		$(this).parent().next('.switch-hid').show();
    	}
    });
});
</script>
</head>
<body> 
<div class="hyx-na-tit fl_l"><label class="lab">销售管理设置</label></div>
<div class="hyx-sms fl_l">
	<div class="fl_l" id="step_1" style="width:100%;">
		<p class="tit">
			<label class="lab fl_l"><i class="tip"></i>资源回收设置</label>
			<i class="switch fl_r" name="on"></i>
		</p>
		<div class="switch-hid fl_l">
			<p class="tit_b">
				<label class="lab" style="padding-left:15px;">分配到帐号的客户信息</label>
				<input type="text" value="2" class="ipt" />
				<label class="lab">天未联系，直接回收到待分资源中。回收天数从资源分配时间点开始计时。</label>
			</p>
		</div>
	</div>
	<div class="fl_l" id="step_2" style="width:100%;">
		<p class="tit">
			<label class="lab fl_l"><i class="tip"></i>资源去重设置</label>
			<i class="switch fl_r" name="on"></i>
		</p>
		<div class="switch-hid fl_l">
			<p class="tit_b">
				<input type="checkbox" name="a" class="check" />
				<label class="lab">对</label>
				<select class="sel sel_a">
					<option>资源用户</option>
				</select>
				<label class="lab">电话号码进行去重保护</label>
			</p>
			<p class="tit_b">
				<input type="checkbox" name="a" class="check" />
				<label class="lab">对</label>
				<select class="sel sel_a">
					<option>所有资源</option>
					<option>签约用户</option>
					<option>意向用户</option>
					<option>所有资源</option>
				</select>
				<label class="lab">客户名称进行去重保护</label>
			</p>
			<p class="tit_b">
				<input type="checkbox" name="a" class="check" />
				<label class="lab">对</label>
				<select class="sel sel_a">
					<option>签约用户</option>
				</select>
				<label class="lab">公司主页进行去重保护</label>
			</p>
		</div>
	</div>
	<p class="tit">
		<label class="lab fl_l"><i class="tip"></i>客户跟进设置</label>
		<i class="switch fl_r" name="on"></i>
	</p>
	<div class="switch-hid fl_l">
		<p class="tit_b">
			<input type="checkbox" name="a" class="check" />
			<label class="lab">下次跟进客户默认时间为</label>
			<input type="text" value="1" class="ipt" />
			<select class="sel">
				<option>天</option>
				<option>小时</option>
				<option>分钟</option>
				<option>秒</option>
			</select>
			<label class="lab">以后。</label>
		</p>
		<p class="tit_b">
			<input type="checkbox" name="a" class="check" />
			<label class="lab">意向客户超过</label>
			<input type="text" value="1" class="ipt" />
			<label class="lab">天未跟进，将自动回收到公海客户池中。</label>
		</p>
		<p class="tit_b">
			<input type="checkbox" name="a" class="check" />
			<label class="lab">允许个人拥有资源上限</label>
			<input type="text" value="500" class="ipt" />
			<label class="lab">人，到达上限将提示操作人员并限制添加客户操作。</label>
		</p>
		<p class="tit_b">
			<input type="checkbox" name="a" class="check" />
			<label class="lab">当个人资源数少于</label>
			<input type="text" value="1" class="ipt" />
			<label class="lab">人，系统自动分配资源，每次分配</label>
			<input type="text" value="1" class="ipt" />
			<label class="lab">人。</label>
		</p>
		<p class="tit_b">
			<input type="checkbox" name="a" class="check" />
			<label class="lab">允许个人拥有意向客户上限</label>
			<input type="text" value="1" class="ipt" />
			<label class="lab">人，到达上限将提示操作人员并限制淘客户操作。</label>
		</p>
		<p class="tit_b">
			<input type="checkbox" name="a" class="check" />
			<label class="lab">允许完成客户签单的最长期限</label>
			<input type="text" value="1" class="ipt" />
			<label class="lab">天，如超期回收到公海客户池中。</label>
		</p>
	</div>
	<div class="fl_l" id="step_3" style="width:100%;">
		<p class="tit">
			<label class="lab fl_l"><i class="tip"></i>客户防骚扰设置</label>
			<i class="switch fl_r" name="on"></i>
		</p>
		<div class="switch-hid fl_l">
			<p class="tit_a" style="padding-left:15px;">
				<label class="lab fl_l">客户防骚扰选择</label>
				<input type="radio" name="a" class="radio"><label class="lab">意向客户</label>
				<input type="radio" name="a" class="radio"><label class="lab">签约客户</label>
			</p>
		</div>
	</div>
	<div class="fl_l" id="step_4" style="width:100%;">
		<p class="tit">
			<label class="lab fl_l"><i class="tip"></i>有效呼叫设置</label>
			<i class="switch fl_r" name="on"></i>
		</p>
		<div class="switch-hid fl_l">
			<p class="tit_b">
				<label class="lab" style="padding-left:15px;">电话接通</label>
				<input type="text" value="10" class="ipt" />
				<label class="lab">秒后，视为有效通话。</label>
			</p>
		</div>
	</div>
	<div class="fl_l" id="step_5" style="width:100%;">
		<p class="tit">
			<label class="lab fl_l"><i class="tip"></i>资源安全设置</label>
			<i class="switch fl_r" name="on"></i>
		</p>
		<div class="switch-hid fl_l">
			<p class="tit_b">
				<label class="lab" style="padding-left:15px;">电话号码中间4位用"*"作模糊处理</label>
			</p>
		</div>
	</div>
	<div class="fl_l" id="step_6" style="width:100%;">
		<p class="tit">
			<label class="lab fl_l"><i class="tip"></i>呼叫区号设置</label>
			<i class="switch fl_r" name="on"></i>
		</p>
		<div class="switch-hid fl_l">
			<p class="tit_b">
				<label class="lab" style="padding-left:15px;">在7位或者8位固话号码前自动添加本地区号：</label>
				<input type="text" value="0571" class="ipt" />
			</p>
		</div>
	</div>
	<p class="tit">
		<label class="lab fl_l"><i class="tip"></i>客户信息字段格式校验设置</label>
		<i class="switch fl_r" name="on"></i>
	</p>
	<div class="switch-hid fl_l">
		<p class="tit_a">
			<input type="checkbox" name="a" class="radio"><label class="lab">常用电话</label>
			<input type="checkbox" name="a" class="radio"><label class="lab">备用电话</label>
			<input type="checkbox" name="a" class="radio"><label class="lab">备用电话2</label>
			<input type="checkbox" name="a" class="radio"><label class="lab">传真</label>
			<input type="checkbox" name="a" class="radio"><label class="lab">邮箱</label>
		</p>
	</div>
	<p class="tit">
		<label class="lab fl_l"><i class="tip"></i>待分配资源共享到公海池功能</label>
		<i class="switch fl_r" name="on"></i>
	</p>
	<p class="tit">
		<label class="lab fl_l"><i class="tip"></i>数据默认显示设置</label>
	</p>
	<p class="tit_a">
		<input type="radio" name="a" class="radio" checked="checked"><label class="lab">10条</label>
		<input type="radio" name="a" class="radio"><label class="lab">20条</label>
		<input type="radio" name="a" class="radio"><label class="lab">30条</label>
		<input type="radio" name="a" class="radio"><label class="lab">50条</label>
		<input type="radio" name="a" class="radio"><label class="lab">100条</label>
	</p>
	<p class="tit">
		<label class="lab fl_l"><i class="tip"></i>每日计划审核功能</label>
		<i class="switch fl_r" name="on"></i>
	</p>
	<div class="com-btnbox">
		<a href="javascript:;" class="com-btna com-btna-sty"><label>保存</label></a>
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
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>资源回收设置开启后，系统会自动回收到达逾期天数没有联系的客户。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'right'
      },
      {
        element: '#step_2',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>资源去重设置开启后，系统会对您指定的客户进行去重保护，相同信息的客户就不能加进系统。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'right'
      },
      {
        element: '#step_3',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>客户防骚扰设置开启后，系统会对您指定的客户进行去重保护，相同信息的客户就不能重复拨打。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'right'
      },
      {
        element: '#step_4',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>打开有效呼叫开关，输入有效呼叫的时长，达到该设置的通话设为有效通话。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'right'
      },
      {
        element: '#step_5',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>打开资源安全设置，号码的中间4位会自动模糊显示。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'right'
      },
      {
        element: '#step_6',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>打开呼叫区号设置，输入区号值，系统在拨打固话时会自动增加区号。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
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
		pubDivShowIframe('alert_novice_help_a','${ctx}/view/help/novice_help_com/alert_novice_help_back.jsp','销售管理设置指导完成',650,350);
	});
	window.parent.$('.i-close').live('click',function(){
        window.parent.window.location.href = '${ctx}/main';
    });
});
</script>
</body>
</html>