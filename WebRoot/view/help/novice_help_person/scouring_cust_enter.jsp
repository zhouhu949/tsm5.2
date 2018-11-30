<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<html>
<head>
<title>淘客户-企业</title>

<link rel="stylesheet" href="${ctx }/static/js/intro/css/introjs.css" type="text/css" /><!--新手引导-->
<link rel="stylesheet" href="${ctx }/static/js/intro/css/bootstrap-responsive.min.css" type="text/css" /><!--新手引导-->
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
		datevalue2=obj.value;
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
    /*自动连拨*/
    $('.hyx-sce-right-btn').find('.switch').click(function(){
    	if($(this).attr('name') == 'on'){
    		$(this).addClass('click-hover');
    		$(this).attr('name','off');
    	}else{
    		$(this).removeClass('click-hover');
    		$(this).attr('name','on');
    	}
    });
    /*联系人切换*/
    var sce_left = $('.hyx-sce-left');
    var name_list = sce_left.find('.name_list').find('label');
    var name_list_len = name_list.length;
    var cen_width = sce_left.find('.box_cen').width()+parseInt(sce_left.find('.box_cen').css('margin-left').substring(0,sce_left.find('.box_cen').css('margin-left').indexOf('px')));
    var cen_width_all = cen_width*(sce_left.find('.box_cen').length-1);
    var mar_l = 0;
    var now_i = 0;
    var now_num = 0;

    function startBtn(){
    	if(name_list.eq(0).hasClass('click-left-hover') == true){
			sce_left.find('.up').hide();
		}else{
			sce_left.find('.up').show();
		}
		if(name_list.eq(name_list_len-1).hasClass('click-left-hover') == true || name_list.eq(name_list_len-1).hasClass('click-right-hover') == true){
			sce_left.find('.down').hide();
		}else{
			sce_left.find('.down').show();
		}
		if(name_list.eq(0).hasClass('click-left-hover') == true && name_list.eq(name_list_len-1).hasClass('click-left-hover') == true){
			sce_left.find('.up').hide();
			sce_left.find('.down').hide();
		}
    }
	startBtn();
    click_tip();
    function click_tip(){
    	name_list.each(function(i,item){
	    	$(item).click(function(){
			    var all_w = parseInt(sce_left.find('.box_all').css('margin-left').substring(0,sce_left.find('.box_all').css('margin-left').indexOf('px')));
			    if(all_w <= 0 && all_w >= '-'+cen_width_all){
			    	now_i = i;
		    		sce_left.find('.box_cen').find('.right').animate({'z-index':'0'},1);
		    		mar_l = parseInt('-'+cen_width*i);
		    		sce_left.find('.box_all').animate({'margin-left':mar_l+'px'},500);
	    			change_tip(now_i);
	    			sce_left.find('.box_cen').find('.right').animate({'z-index':'555'},500);
		    	}
		    	startBtn();
	    	});
	    });
    }

    function change_tip(now_i){
    	if(name_list.eq(now_i).hasClass('left') == true){
    		name_list.removeClass('click-left-hover');
    		name_list.removeClass('click-right-hover');
	  		name_list.eq(now_i).addClass('click-left-hover');
    	}else{
    		name_list.removeClass('click-left-hover');
    		name_list.removeClass('click-right-hover');
	  		name_list.eq(now_i).addClass('click-right-hover');
    	}
    	
    }

    sce_left.find('.down').click(function(){
    	var all_w = parseInt(sce_left.find('.box_all').css('margin-left').substring(0,sce_left.find('.box_all').css('margin-left').indexOf('px')));
    	if(all_w > '-'+cen_width_all){
    		now_i++;
	    	sce_left.find('.box_cen').find('.right').animate({'z-index':'0'},1);
	    	mar_l = mar_l - cen_width;
	    	sce_left.find('.box_all').animate({'margin-left':mar_l+'px'},500);
	    	change_tip(now_i);
	    	sce_left.find('.box_cen').find('.right').animate({'z-index':'555'},500);
    	}
    	startBtn();
    });

    sce_left.find('.up').click(function(){
    	var all_w = parseInt(sce_left.find('.box_all').css('margin-left').substring(0,sce_left.find('.box_all').css('margin-left').indexOf('px')));
    	if(all_w < 0){
    		now_i--;
	    	sce_left.find('.box_cen').find('.right').animate({'z-index':'0'},1);
	    	mar_l = mar_l + cen_width;
	    	sce_left.find('.box_all').animate({'margin-left':mar_l+'px'},500);
	    	change_tip(now_i);
	    	sce_left.find('.box_cen').find('.right').animate({'z-index':'555'},500);
    	}
    	startBtn();
    });

    /*联系人联系小记标签切换*/
    $('.hyx-sce-list').each(function(i,item){
    	$(item).click(function(){
    		$(this).addClass('click-hover').siblings('.hyx-sce-list').removeClass('click-hover');
    		$('.box').eq(i).show().siblings('.box').hide();
    	});
    });

    /*按钮下拉框*/
    $('.hyx-sce-left-btn').find('.drop-box').each(function(i,item){
    	if($(item).find('.com-btnb').hasClass('delay_call') == false){
    		$(item).find('.com-btnb').mouseover(function(){
	    		var btn = $(this);
	    		$(this).next().show();
	    		$(this).next().find('a').click(function(){
	    			btn.next().hide();
	    		});
	    	});
	    	$(item).mouseleave(function(){
	    		$(this).find('.drop').hide();
	    	});
    	}else{
    		$(item).find('.delay_call').click(function(){
    			var btn = $(this);
    			$(this).next().show();
	    		$(this).next().find('a').click(function(){
	    			btn.next().hide();
	    		});
    		});
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

    /*表格radio*/
    $('.hyx-scp-tr').mouseover(function(){
    	$(this).find('.hyx-scp-disable').show();
    	$(this).find('.hyx-scp-disable').css({'background-color':$(this).css('background-color')});
    });
    $('.hyx-scp-tr').mouseleave(function(){
    	$(this).find('.hyx-scp-disable').hide();
    });

    // 表格默认选中第一条
    function default_tr(tab){
    	tab.find('td').removeClass('td-link');
        tab.find('td').addClass('td-hover');
        tab.find('td:first').css({'border-left-color':'#469bff'});
        tab.find('td:last').css({'border-right-color':'#469bff'});
    }
    default_tr($('.hyx-scp-nowtr'));

});
</script>
</head>
<body> 
<div class="hyx-sce">
	<div class="hyx-sce-left fl_l">
		<div class="card fl_l">
			<p class="p_a fl_l">
				<label>宁波企蜂通信有限公司</label>
				<i class="icon-focus-atten" title="重点关注"></i>
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
					<label>联系电话：</label><span title="15899989669">15899989669</span><i class="icon-phone" style="margin-left:28px;"></i><i class="icon-message demoBtn_e"></i>
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
		<p class="group">所在分组：销售一部</p>
		<div class="main fl_l" id="step_3">
			<ul class="list">
				<li class="hyx-sce-list click-hover" title="联系人">
					<p class="list_cen list_cen_a">
						<label class="a"><i></i><span>联系人</span></label>
						<label class="b">3人</label>
					</p>
				</li>
				<li class="hyx-sce-list" title="联系信息">
					<p class="list_cen list_cen_b">
						<label class="a"><i></i><span>联系信息</span></label>
						<label class="b">3条</label>
					</p>
				</li>
				<li class="hyx-sce-noclick" title="拨通次数">
					<p class="list_cen list_cen_c">
						<label class="a"><i></i><span>拨通次数</span></label>
						<label class="b">3次</label>
						<label class="c">10秒/次</label>
					</p>
				</li>
				<li class="hyx-sce-noclick" title="接通率">
					<p class="list_cen list_cen_d list_cen_nobor">
						<label class="a"><i></i><span>接通率</span></label>
						<label class="b">90%</label>
					</p>
				</li>
			</ul>
			<!-- 联系人 -->
			<div class="box">
				<div class="box_all fl_l">
					<div class="box_cen fl_l">
						<div class="left fl_l">
							<dl>
								<dt>东方不败</dt>
								<dd>采购经理</dd>
								<dd>主要联系人</dd>
								<dd class="tip_a"></dd>
							</dl>
						</div>
						<div class="right fl_l">
							<div class="bot fl_l">
								<dl class="bot_a fl_l">
									<dt><i></i></dt>
									<dd class="dd_bt dd_a"><label><i></i></label></dd>
									<dd class="dd_bt dd_b"><label><i></i></label></dd>
								</dl>
								<dl class="bot_b fl_l">
									<dt><i></i></dt>
									<dd class="dd_bt dd_a"><label><i></i></label></dd>
									<dd class="dd_bt dd_b"><label><i></i></label></dd>
								</dl>
							</div>
							<div class="top_box fl_l">
								<dl class="top fl_l">
									<dt><i></i></dt>
								</dl>
								<dl class="top_right fl_l">
									<dt><i></i></dt>
									<dd><i></i></dd>
								</dl>
							</div>
						</div>
					</div>
					<div class="box_cen fl_l">
						<div class="left fl_l">
							<dl>
								<dt>小明2</dt>
								<dd>采购经理</dd>
								<dd>主要联系人</dd>
								<dd class="tip_a"></dd>
							</dl>
						</div>
						<div class="right fl_l">
							<div class="bot fl_l">
								<dl class="bot_a fl_l">
									<dt><i></i></dt>
									<dd class="dd_bt dd_a"><label><i></i></label></dd>
									<dd class="dd_bt dd_b"><label><i></i></label></dd>
								</dl>
								<dl class="bot_b fl_l">
									<dt><i></i></dt>
									<dd class="dd_bt dd_a"><label><i></i></label></dd>
									<dd class="dd_bt dd_b"><label><i></i></label></dd>
								</dl>
							</div>
							<div class="top_box fl_l">
								<dl class="top fl_l">
									<dt><i></i></dt>
								</dl>
								<dl class="top_right fl_l">
									<dt><i></i></dt>
									<dd><i></i></dd>
								</dl>
							</div>
						</div>
					</div>
					<div class="box_cen fl_l">
						<div class="left fl_l">
							<dl>
								<dt>小明3</dt>
								<dd>采购经理</dd>
								<dd>主要联系人</dd>
								<dd class="tip_a"></dd>
							</dl>
						</div>
						<div class="right fl_l">
							<div class="bot fl_l">
								<dl class="bot_a fl_l">
									<dt><i></i></dt>
									<dd class="dd_bt dd_a"><label><i></i></label></dd>
									<dd class="dd_bt dd_b"><label><i></i></label></dd>
								</dl>
								<dl class="bot_b fl_l">
									<dt><i></i></dt>
									<dd class="dd_bt dd_a"><label><i></i></label></dd>
									<dd class="dd_bt dd_b"><label><i></i></label></dd>
								</dl>
							</div>
							<div class="top_box fl_l">
								<dl class="top fl_l">
									<dt><i></i></dt>
								</dl>
								<dl class="top_right fl_l">
									<dt><i></i></dt>
									<dd><i></i></dd>
								</dl>
							</div>
						</div>
					</div>
					<div class="box_cen fl_l">
						<div class="left fl_l">
							<dl>
								<dt>小明4</dt>
								<dd>采购经理</dd>
								<dd>主要联系人</dd>
								<dd class="tip_a"></dd>
							</dl>
						</div>
						<div class="right fl_l">
							<div class="bot fl_l">
								<dl class="bot_a fl_l">
									<dt><i></i></dt>
									<dd class="dd_bt dd_a"><label><i></i></label></dd>
									<dd class="dd_bt dd_b"><label><i></i></label></dd>
								</dl>
								<dl class="bot_b fl_l">
									<dt><i></i></dt>
									<dd class="dd_bt dd_a"><label><i></i></label></dd>
									<dd class="dd_bt dd_b"><label><i></i></label></dd>
								</dl>
							</div>
							<div class="top_box fl_l">
								<dl class="top fl_l">
									<dt><i></i></dt>
								</dl>
								<dl class="top_right fl_l">
									<dt><i></i></dt>
									<dd><i></i></dd>
								</dl>
							</div>
						</div>
					</div>
					<div class="box_cen fl_l">
						<div class="left fl_l">
							<dl>
								<dt>小明5</dt>
								<dd>采购经理</dd>
								<dd>主要联系人</dd>
								<dd class="tip_a"></dd>
							</dl>
						</div>
						<div class="right fl_l">
							<div class="bot fl_l">
								<dl class="bot_a fl_l">
									<dt><i></i></dt>
									<dd class="dd_bt dd_a"><label><i></i></label></dd>
									<dd class="dd_bt dd_b"><label><i></i></label></dd>
								</dl>
								<dl class="bot_b fl_l">
									<dt><i></i></dt>
									<dd class="dd_bt dd_a"><label><i></i></label></dd>
									<dd class="dd_bt dd_b"><label><i></i></label></dd>
								</dl>
							</div>
							<div class="top_box fl_l">
								<dl class="top fl_l">
									<dt><i></i></dt>
								</dl>
								<dl class="top_right fl_l">
									<dt><i></i></dt>
									<dd><i></i></dd>
								</dl>
							</div>
						</div>
					</div>
					<div class="box_cen fl_l">
						<div class="left fl_l">
							<dl>
								<dt>小明6</dt>
								<dd>采购经理</dd>
								<dd>主要联系人</dd>
								<dd class="tip_a"></dd>
							</dl>
						</div>
						<div class="right fl_l">
							<div class="bot fl_l">
								<dl class="bot_a fl_l">
									<dt><i></i></dt>
									<dd class="dd_bt dd_a"><label><i></i></label></dd>
									<dd class="dd_bt dd_b"><label><i></i></label></dd>
								</dl>
								<dl class="bot_b fl_l">
									<dt><i></i></dt>
									<dd class="dd_bt dd_a"><label><i></i></label></dd>
									<dd class="dd_bt dd_b"><label><i></i></label></dd>
								</dl>
							</div>
							<div class="top_box fl_l">
								<dl class="top fl_l">
									<dt><i></i></dt>
								</dl>
								<dl class="top_right fl_l">
									<dt><i></i></dt>
									<dd><i></i></dd>
								</dl>
							</div>
						</div>
					</div>
				</div>
				<div class="name_list">
					<p>
						<label class="left click-left-hover fl_l">
							<span class="fl_l">小明1</span>
						</label>
						<label class="right fl_r">
							<span class="fl_r">小明2</span>
						</label>
					</p>
					<p>
						<label class="left fl_l">
							<span class="fl_l">小明3</span>
						</label>
						<label class="right fl_r">
							<span class="fl_r">小明4</span>
						</label>
					</p>
					<p>
						<label class="left fl_l">
							<span class="fl_l">小明5</span>
						</label>
						<label class="right fl_r">
							<span class="fl_r">小明6</span>
						</label>
					</p>
				</div>
				<a href="##" class="more demoBtn_f">更多...</a>
				<!-- <div class="btn">
					<ul>
						<li class="up"></li>
						<li class="now"></li>
						<li class="down"></li>
					</ul>
				</div> -->
			</div>
			<!-- 联系信息 -->
			<div class="box box_note sty-hid">
				<p class="note_tit">最近联系信息</p>
				<a href="##" class="more">更多...</a>
				<div class="note_bot fl_l">
					<div class="com-table fl_l">
						<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
							<tbody>              
								<tr>
									<td class="td_tit">联系时间：</td>
									<td><div class="overflow_hidden_01 w280" title="大华科技">大华科技</div></td>
								</tr>
								<tr class="sty-bgcolor-b">
									<td class="td_tit">销售联系人：</td>
									<td><div class="overflow_hidden_01 w280" title="张三">张三</div></td>
								</tr>
								<tr>
									<td class="td_tit">客户联系人：</td>
									<td><div class="overflow_hidden_01 w280" title="张颖">张颖</div></td>
								</tr>
								<tr class="sty-bgcolor-b">
									<td class="td_tit">资源备注：</td>
									<td><div class="overflow_hidden_01 w280" title="初次见面 高意向客户高意向客户高意向客户高意向客户高意向客户高意向客户">初次见面 高意向客户高意向客户高意向客户高意向客户高意向客户高意向客户</div></td>
								</tr>
								<tr>
									<td class="td_tit"></td>
									<td><div class="overflow_hidden_01 w280" title=""></div></td>
								</tr>
								<tr class="sty-bgcolor-b">
									<td class="td_tit"></td>
									<td><div class="overflow_hidden_01 w280" title=""></div></td>
								</tr>
								<tr>
									<td class="td_tit"></td>
									<td><div class="overflow_hidden_01 w280" title=""></div></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="com-table fl_r">
						<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
							<tbody>              
								<tr>
									<td class="td_tit">联系时间：</td>
									<td><div class="overflow_hidden_01 w280" title="大华科技">大华科技</div></td>
								</tr>
								<tr class="sty-bgcolor-b">
									<td class="td_tit">销售联系人：</td>
									<td><div class="overflow_hidden_01 w280" title="李四">李四</div></td>
								</tr>
								<tr>
									<td class="td_tit">客户联系人：</td>
									<td><div class="overflow_hidden_01 w280" title="张颖">张颖</div></td>
								</tr>
								<tr class="sty-bgcolor-b">
									<td class="td_tit">销售进程：</td>
									<td><div class="overflow_hidden_01 w280" title="初次见面 高意向客户">初次见面 高意向客户</div></td>
								</tr>
								<tr>
									<td class="td_tit">下次联系时间：</td>
									<td><div class="overflow_hidden_01 w280" title="2015-01-04 11:11:11">2015-01-04 11:11:11</div></td>
								</tr>
								<tr class="sty-bgcolor-b">
									<td class="td_tit">联系小记：</td>
									<td><div class="overflow_hidden_01 w280" title="初次见面 高意向客户高意向客户高意向客户高意向客户高意向客户高意向客户">初次见面 高意向客户高意向客户高意向客户高意向客户高意向客户高意向客户</div></td>
								</tr>
								<tr>
									<td class="td_tit"></td>
									<td><div class="overflow_hidden_01 w280" title=""></div></td>
								</tr>
							</tbody>
						</table>
					</div>

				</div>
			</div>

		</div>
		<div class="hyx-sce-left-btn fl_l">
			<div class="drop-box drop-box-agin fl_r">
				<a href="###" class="com-btnb com-btnb-sty">下次再淘</a>
				<span class="drop" style="display:none;">
					<label class="arrow"><em>◆</em><span>◆</span></label>
					<label class="box">
						<a href="###"><i></i><label>下一条</label></a>
						<a href="###" class="demoBtn_c"><i></i><label>资源备注</label></a>
					</label>
				</span>
			</div>
			<div class="drop-box drop-box-a fl_r">
				<a href="###" class="com-btnb fl_r delay_call">延后呼叫</a>
				<span class="drop drop-a" style="display:none;">
					<label class="arrow"><em>◆</em><span>◆</span></label>
					<label class="box">
						<label class="lab"><span>待呼时间：</span><input type="text" id="d1" /></label>
						<label class="lab">
							<span>延后原因：</span>
							<label class="hyx-sce-area">
								<textarea class='area_a fl_l'></textarea>
								<span>最多可输入30个汉字。</span>
							</label>
						</label>
						<label class="btn">
							<a href="###" class="com-btnc com-btnc-sty fl_l">确定</a>
							<a href="###" class="com-btnd fl_r">取消</a>
						</label>
					</label>
				</span>
			</div>
			<div class="drop-box drop-box-b fl_r">
				<a href="###" class="com-btnb fl_r">沟通失败</a>
				<span class="drop" style="display:none;">
					<label class="arrow"><em>◆</em><span>◆</span></label>
					<label class="box">
						<a href="###"><i></i><label>直接拒绝</label></a>
						<a href="###"><i></i><label>非决策人或经办人</label></a>
						<a href="###"><i></i><label>反馈无需求</label></a>
						<a href="###"><i></i><label>很忙无时间</label></a>
					</label>
				</span>
			</div>
			<div class="drop-box drop-box-b fl_r">
				<a href="###" class="com-btnb fl_r">信息错误</a>
				<span class="drop" style="display:none;">
					<label class="arrow"><em>◆</em><span>◆</span></label>
					<label class="box">
						<a href="###"><i></i><label>空号或号码不存在</label></a>
						<a href="###"><i></i><label>非本人号码</label></a>
						<a href="###"><i></i><label>公司不存在或已离职</label></a>
					</label>
				</span>
			</div>
			<a href="###" class="com-btnb fl_r">上一条</a>
		</div>
		<div class="hyx-sce-left-form fl_l" id="step_4">
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
					<label class="sty-hid fl_l">
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
		<div class="com-btnbox hyx-sce-left-btnbox" id="step_6">
			<a href="javascript:;" class="com-btnb com-btnb-sty fl_l"><label>淘<span>到客户</span></label></a>
			<a href="javascript:;" class="com-btnb fl_r"><label>签&nbsp;&nbsp;约</label></a>
		</div>
	</div>
	<div class="hyx-sce-right fl_r">
		<p class="hyx-sce-right-btn fl_l">
			<label class="lab fl_l">自动连拨：</label><i class="switch fl_l" name="on"></i>
			<label class="right fl_r">
				<a href="javascript:;" class="com-btna demoBtn_a fl_l" id="step_1"><i class="min-add-resou"></i><label class="lab-mar">增加资源</label></a>
				<a href="javascript:;" class="com-btna demoBtn_d fl_r"><i class="min-word-temp"></i><label class="lab-mar">话术模板</label></a>
			</label>
		</p>
		<span class="hyx-sce-right-tit fl_l">待呼叫资源</span>
		<div class="com-search hyx-sce-right-search fl_l">
     		<div class="com-search-box fl_l">
			   <div class="list-box">
				   <dl class="select">
						<dt>今日计划</dt>
						<dd>
							<ul>
								<li><a href="#" title="今日计划">今日计划</a></li>
							</ul>
						</dd>
				   </dl>
				   <dl class="select">
						<dt>分配时间升序排列</dt>
						<dd>
							<ul>
								<li><a href="#" title="分配时间升序排列">分配时间升序排列</a></li>
							</ul>
						</dd>
				   </dl>
					<dl class="select">
						<dt style="border-right:none !important;">全部资源</dt>
						<dd>
							<ul>
								<li><a href="#" title="全部资源">全部资源</a></li>
							</ul>
						</dd>
					</dl>
				</div>
			</div>
	    </div>
		<div class="com-table hyx-sce-right-table fl_l" id="step_2">
			<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
				<thead>
					<tr class="sty-bgcolor-b">
						<th><span class="sp sty-borcolor-b"></span></th>
						<th><span class="sp sty-borcolor-b">客户名称</span></th>
						<th>联系电话</th>
					</tr>
				</thead>
				<tbody>              
					<tr class="hyx-scp-nowtr">
						<!-- 正在联系 -->
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" checked="checked" /></div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15899968778">15899968778</div></td>
					</tr>
					<tr class="sty-bgcolor-b">
						<!-- 已经联系过 -->
						<td style="width:30px;" class="hyx-scp-oldcall"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15899968778">15899968778</div></td>
					</tr>
					<tr class="hyx-scp-tr">
						<!-- 不可点击的 -->
						<td style="width:30px;"><span class="hyx-scp-disable">该客户已成为意向客户！</span><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" disabled="disabled" /></div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15899968778">15899968778</div></td>
					</tr>
					<tr class="sty-bgcolor-b hyx-scp-tr">
						<td style="width:30px;"><span class="hyx-scp-disable">该客户已成为意向客户！</span><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" disabled="disabled" /></div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15899968778">15899968778</div></td>
					</tr>
					<tr>
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15899968778">15899968778</div></td>
					</tr>
					<tr class="sty-bgcolor-b">
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15899968778">15899968778</div></td>
					</tr>
					<tr>
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15899968778">15899968778</div></td>
					</tr>
					<tr class="sty-bgcolor-b">
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15899968778">15899968778</div></td>
					</tr>
					<tr>
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15899968778">15899968778</div></td>
					</tr>
					<tr class="sty-bgcolor-b">
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15899968778">15899968778</div></td>
					</tr>
					<tr>
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15899968778">15899968778</div></td>
					</tr>
					<tr class="sty-bgcolor-b">
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="15899968778">15899968778</div></td>
					</tr>
				</tbody>
			</table>
		</div>

		<span class="hyx-sce-right-tit fl_l">延后呼叫资源</span>
		<div class="com-table hyx-sce-right-table fl_l" id="step_5"> 
			<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
				<thead>
					<tr class="sty-bgcolor-b">
						<th><span class="sp sty-borcolor-b"></span></th>
						<th><span class="sp sty-borcolor-b">延期时间</span></th>
						<th><span class="sp sty-borcolor-b">客户名称</span></th>
						<th>延后原因</th>
					</tr>
				</thead>
				<tbody>              
					<tr>
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="12-12 10:10">12-12 10:10</div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="多次电话不通">多次电话不通多次电话不通多次电话不通多次电话不通多次电话不通多次电话不通</div></td>
					</tr>
					<tr class="sty-bgcolor-b">
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="12-12 10:10">12-12 10:10</div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="多次电话不通">多次电话不通</div></td>
					</tr>
					<tr>
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="12-12 10:10">12-12 10:10</div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="多次电话不通">多次电话不通</div></td>
					</tr>
					<tr class="sty-bgcolor-b">
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="12-12 10:10">12-12 10:10</div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="多次电话不通">多次电话不通</div></td>
					</tr>
					<tr>
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="12-12 10:10">12-12 10:10</div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="多次电话不通">多次电话不通</div></td>
					</tr>
					<tr class="sty-bgcolor-b">
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="12-12 10:10">12-12 10:10</div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="多次电话不通">多次电话不通</div></td>
					</tr>
					<tr>
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="12-12 10:10">12-12 10:10</div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="多次电话不通">多次电话不通</div></td>
					</tr>
					<tr class="sty-bgcolor-b">
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="12-12 10:10">12-12 10:10</div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="多次电话不通">多次电话不通</div></td>
					</tr>
					<tr>
						<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="radio" name="a" /></div></td>
						<td><div class="overflow_hidden w100" title="12-12 10:10">12-12 10:10</div></td>
						<td><div class="overflow_hidden w100" title="大华科技">大华科技</div></td>
						<td><div class="overflow_hidden w100" title="多次电话不通">多次电话不通</div></td>
					</tr>
				</tbody>
			</table>
		</div>

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
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>做完计划，开始淘客户吧...</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />"
      },
      {
        element: '#step_1',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>可在此处添加资源。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'left'
      },
      {
        element: '#step_2',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>此处是一个资源列表，计划中的资源和系统中的资源都可以在此处展现。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'left'
      },
      {
        element: '#step_3',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>可通过各种联系方式，联系客户。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'right'
      },
      {
        element: '#step_4',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>对于有意向的客户可在此处添加销售信息，方便后续跟进。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'right'
      },
      {
        element: '#step_5',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>这里是延后呼叫的资源，在此页面也可以对延后呼叫的资源进行再淘的操作。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'left'
      },
      {
        element: '#step_6',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>点击淘到客户，该客户就会转变为意向客户，该客户也会出现在意向客户池中。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'top'
      }
    ]
  });

  intro.setOption("skipLabel","").start();
}
window.onload=function(){
	startIntro();
}
$(function(){
	$(".introjs-skipbutton").live('click',function(){
		window.parent.window.location.href = '${ctx}/view/help/novice_help_person/cust_my_ifram_b.jsp';
	})

});
</script>
</body>
</html>