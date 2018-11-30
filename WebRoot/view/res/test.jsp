<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>添加意向-跟进信息</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="../css/style.css"><!--主要样式-->
<link rel="stylesheet" type="text/css" href="../css/skin/default/color.css"><!--换肤样式默认蓝色-->
<link rel="stylesheet" type="text/css" href="../js/form/skins/all.css"><!--form样式-->
<link rel="stylesheet" type="text/css" href="../js/idialog/theme/default/style.css"><!--可移动弹框插件公共css-->
<!--本页面样式-->
<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css"><!--树形结构样式-->
<link rel="stylesheet" type="text/css" href="../js/time/css/daterangepicker.css" /><!--选择区域日期插件样式-->
<style type="text/css">
body{
	background-color:#f2f2f2 !important;
}
</style>


<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/idialog/jquery.idialog.js?v=222" dialog-theme="default"></script><!--可移动弹框插件-->
<script type="text/javascript" src="../js/idialog/idialog.common.js"></script><!--可移动弹框插件公共js-->
<script type="text/javascript" src="../js/time/moment.min.js"></script><!--选择区域日期插件-->
<script type="text/javascript" src="../js/time/jquery.daterangepicker.js"></script><!--选择区域日期插件-->
<script type="text/javascript" src="../js/form/icheck.js"></script><!--表单优化-->
<script type="text/javascript" src="../js/form/js/custom.min.js"></script><!--表单优化-->
<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
$(function(){

    var datevalue1="";
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

    $('.bomp-drop').each(function(i,item){
    	$(item).click(function(){
    		$(this).parent().find('.bomp-drop-hid').eq(i).slideToggle();
    		if($(this).hasClass('bomp-pull') == false){
    			$(this).find('label').removeClass('arrow').addClass('arrow_a');
    			$(this).addClass('bomp-pull');
    		}else{
    			$(this).find('label').removeClass('arrow_a').addClass('arrow');
    			$(this).removeClass('bomp-pull');
    		}
    	});
    });

    /*行动标签*/
    $('.tip-box').find('a').click(function(){
    	if($(this).hasClass('click-hover') == true){
    		$(this).removeClass('click-hover');
    	}else{
    		$(this).addClass('click-hover');
    	}
    });

    /*card下拉*/
    $('.icon_down,.icon_down_a').each(function(i,item){
    	console.log($(item).prev().attr('class'));
    	if($(item).prev().hasClass('sty-hid') == true){
    		$(item).show();
    	}else{
    		$(item).hide();
    	}
    });

    $('.icon_down,.icon_down_a').click(function(){
    	var sty_hid = $(this).prev();
    	if($(this).attr('name') == 'up'){
    		$(this).css({'background':'url(../images/down-alert.png) no-repeat'});
    		$(this).attr('name','down');	
    		sty_hid.slideUp();
    	}else{
    		if($(this).hasClass('icon_down') == true){
    			$(this).parent().css({'height':'auto'});
    		}
    		$(this).css({'background':'url(../images/up-alert.png) no-repeat'});
    		$(this).attr('name','up');
    		sty_hid.slideDown();
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
    
});
</script>
<script type="text/javascript" src="../js/form.js"></script>
</head>
<body> 
<div class="year-sale-play hyx-hsrs-top">
	<ul class="ann-sale-plan clearfix">
		<a href="add_res_a.html"><li class="li_first" name="a">客户信息</li></a>
		<a href="add_res_b.html"><li class="select li_last" name="b">跟进信息</li></a>
	</ul>
</div>
<div class="hyx-bomp-fllow">
	<div class="hyx-sce-left-form">
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
	<div class="com-btnbox hyx-sce-left-btnbox">
		<a href="javascript:;" class="com-btnb com-btnb-sty fl_l">保&nbsp;&nbsp;存</a>
		<a href="javascript:;" class="com-btnb fl_r">取&nbsp;&nbsp;消</a>
	</div>
</div>
</body>
</html>