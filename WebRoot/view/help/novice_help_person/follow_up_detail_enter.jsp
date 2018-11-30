<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<html>
<head>
<title>客户跟进-跟进详细页面-企业</title>

<style type="text/css">  /*style.css文件里已加，不必再添加*/
.hyx-fude-search{position:absolute;left:45px;top:40px;}
.hyx-fude-search .com-search-box{border-color:#f5f6f7;}
.hyx-fude-search .select{background-color:#f5f6f7;}
.hyx-fude-search .select dt{width:95px;font-size:14px;background:#fff url(${ctx}/static/images/drop-down-arrow.png) no-repeat 80px center;background-color:#f5f6f7;}
.hyx-fude-search .select dd{left:-8px;}
</style>

<link rel="stylesheet" href="${ctx }/static/js/intro/css/introjs.css" type="text/css" /><!--新手引导-->
<link rel="stylesheet" href="${ctx }/static/js/intro/css/bootstrap-responsive.min.css" type="text/css" /><!--新手引导-->


<script type="text/javascript">
$(function(){
	/*表单优化*/

	var datevalue1="";
	var datevalue2="";
	var datevalue3="";
	if($('#d3').attr('value') == '默认：1天后 12-13 01:01:01'){
		$('#d3').focus(function(){
			$(this).attr('value','');
			if($(this).attr('value') == ''){
				$(this).blur(function(){
					$(this).attr('value','默认：1天后 12-13 01:01:01');
				});
			}
		});
	}
	/*时间弹框*/
	$.dateRangePickerLanguages['custom'] ={
		'selected': 'Choosed:',
		'days': 'Days',
		'apply': 'Close',
		'week-1' : 'Mon',
		'week-2' : 'Tue',
		'week-3' : 'Wed',
		'week-4' : 'Thu',
		'week-5' : 'Fri',
		'week-6' : 'Sat',
		'week-7' : 'Sun',
		'month-name': ['January','February','March','April','May','June','July','August','September','October','November','December'],
		'shortcuts' : 'Shortcuts',
		'past': 'Past',
		'7days' : '7days',
		'14days' : '14days',
		'30days' : '30days',
		'previous' : 'Previous',
		'prev-week' : 'Week',
		'prev-month' : 'Month',
		'prev-quarter' : 'Quarter',
		'prev-year' : 'Year',
		'less-than' : 'Date range should longer than %d days',
		'more-than' : 'Date range should less than %d days',
		'default-more' : 'Please select a date range longer than %d days',
		'default-less' : 'Please select a date range less than %d days',
		'default-range' : 'Please select a date range between %d and %d days',
		'default-default': 'This is costom language'
	};
/*时间弹框*/
	$('#d1').dateRangePicker({
		showShortcuts:false,
		autoClose:true,
		singleDate:true,
		showShortcuts:false,
		format:'YYYY-MM-DD HH:mm:ss',
		autoClose: false,
		time: {
			enabled: true
		}
	}).bind('datepicker-change',function(event,obj){
		//alert(obj.date1);
		//alert(obj.date2);
		datevalue1=obj.value;
	});
	$('#d2').dateRangePicker({
		showShortcuts:false,
		autoClose:true,
		singleDate:true,
		showShortcuts:false,
		format:'YYYY-MM-DD HH:mm:ss',
		autoClose: false,
		time: {
			enabled: true
		}
	}).bind('datepicker-change',function(event,obj){
		//alert(obj.date1);
		//alert(obj.date2);
		datevalue3=obj.value;
	});
	$('#d3').dateRangePicker({
		showShortcuts:false,
		autoClose:true,
		singleDate:true,
		showShortcuts:false,
		format:'YYYY-MM-DD HH:mm:ss',
		autoClose: false,
		time: {
			enabled: true
		}
	}).bind('datepicker-change',function(event,obj){
		//alert(obj.date1);
		//alert(obj.date2);
		datevalue3=obj.value;
	});

    /*card下拉*/
    $('.icon_down,.icon_down_a').click(function(){
    	var sty_hid = $(this).prev();
    	if($(this).attr('name') == 'up'){
    		$(this).css({'background':'url(${ctx}/static/images/down-alert.png) no-repeat'});
    		$(this).attr('name','down');
    		if($(this).hasClass('icon_down') == true){
    			$(this).parent().animate({'height':'130px'},1);
    		}	
    	}else{
    		if($(this).hasClass('icon_down') == true){
    			$(this).parent().css({'height':'auto'});
    		}
    		$(this).css({'background':'url(${ctx}/static/images/up-alert.png) no-repeat'});
    		$(this).attr('name','up');
    	}
    	sty_hid.slideToggle();
    });
    /*行动标签*/
    $('.tip-box').find('a').click(function(){
    	if($(this).hasClass('click-hover') == true){
    		$(this).removeClass('click-hover');
    	}else{
    		$(this).addClass('click-hover');
    	}
    });

    /*textarea提示信息*/
    $('.hyx-sce-area').find('textarea,span').click(function(){
    	var ipt_a = $(this).parent().find('textarea');
        ipt_a.focus();
        $(this).parent().find('span').hide();
        ipt_a.blur(function(){
            if($(this).val() == ''){
                $(this).parent().find('span').show();
            }
         }); 
    });

    $('.hyx-cm-new').find('.load').each(function(){
    	if($(this).find('.sp').text().length >= 40){
	    	$(this).find('.sp').text($(this).find('.sp').text().substr(0,40) + '...');
	    }
    });
    $('.hyx-cm-new').find('.load').mouseover(function(){
        $(this).find('.drop').fadeIn(1);
    });
    $('.hyx-cm-new').find('.load').mouseleave(function(){
        $(this).find('.drop').fadeOut(1);
    });

    //联系人更多
    $('.more_name_btn').click(function(){
    	$(this).next('.drop').toggle();
    	$(this).next('.drop').find('.box').find('span').click(function(){
    		$(this).parents('.drop').hide();
    		$(this).parents('.more_name').prev().find('span').text($(this).text());
    	});
    });

});
</script>
</head>
<body> 
<div class="hyx-sce hyx-fud">
	<div class="hyx-sce-left fl_l">
		<div class="card fl_l">
			<p class="p_a fl_l">
				<label>宁波企蜂通信有限公司</label>
				<i class="icon-focus-atten"></i>
				<i class="icon-edit demoBtn_b" title="编辑"></i>
			</p>
			<p class="p_b fl_l">
				<label>所属行业：</label><span title="电子商务">电子商务</span>
			</p>
			<p class="p_b fl_l">
				<label>注册资本：</label><span title="500万">500万</span>
			</p>
			<p class="p_b fl_l">
				<label>公司网址：</label><span title="www.baidu.com">www.baidu.com</span>
			</p>
			<p class="p_b fl_l">
				<label>联系地址：</label><span title="浙江省杭州市西湖区翠柏路184号">浙江省杭州市西湖区翠柏路184号</span>
			</p>
			<label class="sty-hid fl_l">
				<p class="p_b fl_l">
					<label>联系地址：</label><span title="杭州市西湖区翠柏路">杭州市西湖区翠柏路</span>
				</p>
				<p class="p_b fl_l">
					<label>联系人：</label><span title="虾米">虾米</span>
				</p>
				<p class="p_b hyx-sce-tel fl_l">
					<label>联系电话：</label><span title="15899989669">15899989669</span><i class="icon-phone" style="margin-left:25px;"></i><i class="icon-message demoBtn_e"></i>
				</p>
				<p class="p_b hyx-sce-mail fl_l">
					<label>邮箱：</label><span title="5988788445@qq.com">5988788445@qq.com</span><i class="icon-email"></i>
				</p>
				<p class="p_b fl_l">
					<label>所属行业：</label><span title="电子商务">电子商务</span>
				</p>
				<p class="p_b fl_l">
					<label>注册资本：</label><span title="500万">500万</span>
				</p>
				<p class="p_b fl_l">
					<label>法人代表：</label><span title="小米">小米</span>
				</p>
				<p class="p_b fl_l">
					<label>公司网址：</label><span title="www.baidu.com">www.baidu.com</span>
				</p>
				<p class="p_b fl_l">
					<label>联系地址：</label><span title="浙江省杭州市西湖区翠柏路184号">浙江省杭州市西湖区翠柏路184号</span>
				</p>
			</label>
			<i class="icon_down" name="down"></i>
		</div>
		<p class="group">
			<label class="lab fl_l">联系人：<span>张三</span></label>
			<a href="###" class="more_name fl_l"><label class="more_name_btn">更多</label>
				<span class="drop" style="display:none;">
					<label class="arrow"><em>◆</em><span>◆</span></label>
					<label class="box">
						<span>张三</span>
						<span href="###">李四</span>
						<span href="###">王五</span>
					</label>
				</span>
			</a>
		</p>
		<div class="main fl_l">
			<div class="bot">
				<dl class="bot_a fl_l">
					<dt><i></i></dt>
					<dd>15425554887</dd>
					<dd class="dd_bt dd_a"><label><i></i><span>短信</span></label></dd>
					<dd class="dd_bt dd_b"><label><i></i><span>呼叫</span></label></dd>
				</dl>
				<dl class="bot_b fl_l">
					<dt><i></i></dt>
					<dd>15425554887</dd>
					<dd class="dd_bt dd_a"><label><i></i><span>短信</span></label></dd>
					<dd class="dd_bt dd_b"><label><i></i><span>呼叫</span></label></dd>
				</dl>
				<dl class="bot_c fl_l">
					<dt><i></i></dt>
					<dd class="dd_q"><i></i></dd>
					<dd class="dd_w"><i></i></dd>
				</dl>
			</div>
		</div>
		<div class="hyx-sce-left-form fl_l">
			<div class='bomp_tit'><label class='lab'>销售信息</label></div>
			<div class="bomp-box">
				<p class='bomp-p bomp-error'>
					<label class='lab_a fl_l'><i class="com-red">*</i>客户类型：</label><select class="sel_a fl_l"><option>对软件感兴趣</option></select>
					<span class="error">请选择正确的客户类型！</span>
				</p>
				<div class='bomp-p'>
					<label class='lab_a fl_l'>适用产品：</label>
					<div class="reso-sub-dep fl_l">
						<span class="sub-dep-inpu"></span>
						<div class="manage-drop">
							<div class="sub-dep-ul">
								<ul id="tt1" class="easyui-tree" data-options="animate:false,dnd:false">
						            <li><span><input type="checkbox">慧营销</span></li>
						            <li><span><input type="checkbox">快话</span></li>
						            <li><span><input type="checkbox">慧服务</span></li>
						        </ul>
					    	</div>
					   		 <div class="sure-cancle clearfix" style="width:120px">
								<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" href="javascript:;"><label>确定</label></a>
								<a class="com-btnd bomp-btna fl_l" id="close02" href="javascript:;"><label>清空</label></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="bomp-box">
				<p class='bomp-p'>
					<label class='lab_a fl_l'><i class="com-red">*</i>联系方式：</label>
					<select class="sel_a fl_l">
						<option>-请选择-</option>
						<option>电话联系</option>
						<option>会客联系</option>
						<option>客户来电</option>
						<option>短信联系</option>
						<option>QQ联系</option>
						<option>邮件联系</option>
					</select>
				</p>
				<div class='bomp-p'>
					<label class='lab_a fl_l'><i class="com-red">*</i>联系时间：</label>
					<input type="text" value="" class="ipt" id="d1" />
				</div>
			</div>
			<div class="bomp-box">
				<p class='bomp-p'>
					<label class='lab_a fl_l'><i class="com-red">*</i>销售进程：</label><select class="sel_a fl_l"><option>F初步沟通意向</option></select>
				</p>
				<p class='bomp-p'>
					<label class='lab_a fl_l'><i class="com-red">*</i>联系有效性：</label><select class="sel_a fl_l"><option>有效联系</option></select>
				</p>
			</div>
			<div class='bomp-p bomp-p-w'>
				<label class='lab_a fl_l'>行动标签：</label>
				<div class="tip-box fl_l">
					<a href="###" class="click-hover">初次跟进跟进</a>
					<a href="###">初次跟进跟进</a>
					<a href="###">初次跟进跟进</a>
					<a href="###">初次跟进跟进</a>
					<a href="###">初次跟进跟进</a>
					<a href="###">初次跟进跟进</a>
					<label class="1sty-hid fl_l">
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
						<a href="###">初次跟进跟进</a>
					</label>
					<i class="tip-more icon_down_a fl_l" name="down"></i>
				</div>
			</div>
			<div class='bomp-p bomp-p-w'>
				<label class='lab_a fl_l'><i class="com-red">*</i>联系小记：</label>
				<label class="hyx-sce-area">
					<textarea class='area_a fl_l'></textarea>
					<span>请输入与客户联系结果，最多可输入2000个汉字。</span>
				</label>
			</div>
			<div class="bomp-box">
				<p class='bomp-p'>
					<label class='lab_a fl_l'><i class="com-red">*</i>下次联系：</label>
					<select class="sel_a fl_l">
						<option>-请选择-</option>
						<option>电话联系</option>
						<option>会客联系</option>
						<option>客户来电</option>
						<option>短信联系</option>
						<option>QQ联系</option>
						<option>邮件联系</option>
					</select>
				</p>
				<p class='bomp-p'>
					<label class='lab_a fl_l'><i class="com-red">*</i>下次联系时间：</label>
					<input type="text" value="默认：1天后 12-13 01:01:01" class="ipt" id="d3" />
					<!-- <select class="sel_a fl_l"><option>默认：1天后 12-13 01:01:01</option></select> -->
				</p>
			</div>
			<div class="sty-hid fl_l">
				<div class="bomp-box">
					<p class='bomp-p'>
						<label class='lab_a fl_l'>预期签单时间：</label>
						<input type="text" value="" class="ipt" id="d2" />
					</p>
					<p class='bomp-p'>
						<label class='lab_a fl_l'>预期销售额：</label>
						<input type="text" value="" class="ipt" />
					</p>
				</div>
				<p class='bomp-p bomp-p-w'>
					<label class='lab_a fl_l'>销售对策：</label>
					<input type="text" value="" class="ipt bomp-ipt-w" />
				</p>
				<p class='bomp-p bomp-p-w'>
					<label class='lab_a fl_l'>客户异议：</label>
					<input type="text" value="" class="ipt bomp-ipt-w" />
				</p>
				<p class='bomp-p bomp-p-w'>
					<label class='lab_a fl_l'>客户兴趣：</label>
					<input type="text" value="" class="ipt bomp-ipt-w" />
				</p>
				<p class='bomp-p bomp-p-w'>
					<label class='lab_a fl_l'>竞争对手：</label>
					<input type="text" value="" class="ipt bomp-ipt-w" />
				</p>
				<div class='bomp-p bomp-p-w'>
					<label class='lab_a fl_l'>备注：</label>
					<label class="hyx-sce-area">
						<textarea class='area_a fl_l'></textarea>
						<span>最多可输入200个汉字。</span>
					</label>
				</div>
			</div>
			<i class="tip-more icon_down_a form-more fl_l" name="down"></i>
		</div>
		<div class="com-btnbox hyx-sce-left-btnbox">
			<a href="javascript:;" class="com-btnb com-btnb-sty fl_l" id="step_1">签&nbsp;&nbsp;约</a>
			<a href="javascript:;" class="com-btnb fl_r">保存并继续</a>
		</div>
	</div>
	<div class="hyx-sce-right fl_r" style="position:relative;margin-top:25px;">
		<span class="hyx-sce-right-tit fl_l">其他待跟进客户</span>
		<!-- 计划待跟进下拉框开始 -->
		<div class="com-search hyx-sce-right-search hyx-fude-search fl_l">
     		<div class="com-search-box fl_l">
			   <div class="list-box">
					<dl class="select">
						<dt style="border:0 none !important;">计划待跟进</dt>
						<dd>
							<ul>
								<li><a href="#" title="计划待跟进">计划待跟进</a></li>
								<li><a href="#" title="其他待跟进">其他待跟进</a></li>
							</ul>
						</dd>
					</dl>
				</div>
			</div>
	    </div>
	    <!-- 计划待跟进下拉框结束 -->
		<div class="com-table hyx-sce-right-table fl_l">
			<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
				<thead>
					<tr class="sty-bgcolor-b">
						<th><span class="sp sty-borcolor-b">&nbsp;</span></th>
						<th><span class="sp sty-borcolor-b">&nbsp;</span></th>
						<th><span class="sp sty-borcolor-b">客户名称</span></th>
						<th>联系电话</th>
					</tr>
				</thead>
				<tbody>              
					<tr>
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="12-12 10:10">12-12 10:10</div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15869969887">15869969887</div></td>
					</tr>
					<tr class="sty-bgcolor-b">
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="12-12 10:10">12-12 10:10</div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15869969887">15869969887</div></td>
					</tr>
					<tr>
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="12-12 10:10">12-12 10:10</div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15869969887">15869969887</div></td>
					</tr>
					<tr class="sty-bgcolor-b">
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="12-12 10:10">12-12 10:10</div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15869969887">15869969887</div></td>
					</tr>
					<tr>
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="12-12 10:10">12-12 10:10</div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15869969887">15869969887</div></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="hyx-cm-new fl_l">
			<div class="top">
				<label>客户跟进最新动态</label><i></i>
			</div>
			<ul>
				<li><label class="lab">客户联系人：</label><span class="sp">李工（123456789）</span></li>
				<li class="sty-bgcolor-b"><label class="lab">销售联系人：</label><span class="sp">张三（123456789）</span></li>
				<li><label class="lab">销售进程：</label><span class="sp">3全面了解产品</span></li>
				<li class="sty-bgcolor-b"><label class="lab">行动标签：</label><span class="sp">初次跟进，高意向客户</span></li>
				<li><label class="lab">通话录音：</label><span class="sp"><i class="radio"></i></span></li>
				<li class="sty-bgcolor-b"><label class="lab">下次联系时间：</label><span class="sp">2015-01-01 01:01:01</span></li>
				<li><label class="lab">联系方式：</label><span class="sp">会客联系</span></li>
				<li class="sty-bgcolor-b load"><label class="lab">联系小记：</label><span class="sp">本次跟进，需要继续跟客户会面，然后代入公司。就是这样的吧，是的吧就是这样的吧，是的吧就是这样的吧，是的吧</span>
					<span class="drop" style="display:none;">
						<label class="arrow"><em>◆</em><span>◆</span></label>
						<label class="box">联系小记：本次跟进，需要继续跟客户会面，然后代入公司。就是这样的吧，是的吧就是这样的吧，是的吧就是这样的吧，是的吧</label>
					</span>
				</li>
				<li><label class="lab"></label><span class="sp"></span></li>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/intro/js/intro.js"></script><!--新手引导-->
<script type="text/javascript">
var intro = introJs();
function startIntro(){
  	intro.setOptions({
    steps: [
      {
        element: '#step_1',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>对客户跟进过程中感觉商机已成熟，可点击“签约”来对客户进行签约。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'top'
      }
    ]
  });

  intro.setOption("doneLabel","下一步").start();
}
window.onload=function(){
	startIntro();
}
$(function(){
	$(".introjs-skipbutton").live('click',function(){
		window.parent.window.location.href = '${ctx}/view/help/novice_help_person/cust_my_ifram_c.jsp';
	})
});
</script>
</body>
</html>