<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<html>
<head>

<title>系统设置-系统属性设置</title>

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

<style>
	.tree-node{width:240px;}
	.hyx-hsm-left dl{text-align:left;}
	.hyx-hsm-left dl dt,.hyx-hsm-left dl dd{text-indent:30px;}
</style>
<!--本页面js-->
<script type="text/javascript">
$(function(){
	/*左边选择样式切换*/
    $('.hyx-hsm-left').find('dd').each(function(){
    	$(this).click(function(){
    		$(this).addClass("dd-hover");
    		$(this).siblings().removeClass("dd-hover");
    	});
    	$(this).hover(function(){
    		$(this).find(".reso-grou-edit").show();
    		$(this).find(".reso-grou-dele").show();
    		
    	},function(){
    		$(this).find(".reso-grou-edit").hide();
    		$(this).find(".reso-grou-dele").hide();
    	});
    	
    });
    /*iframe切换*/
    $('.hyx-hsm-left').find('dd').click(function(){
    	$(this).parent().parent().parent().find('iframe').attr('src',"${ctx}/view/help/novice_help_com/sys_pro_set_" + $(this).attr('name') + ".jsp");
    });

});
</script>

</head>
<body> 
<div class="hyx-dfpzy-reso">
	<div class="hyx-hsm-left fl_l">
		<dl>
			<dt>系统属性设置</dt>
			<dd name="a">销售产品维护</dd>
			<dd name="b">销售进程设计</dd>
			<dd class="dd-hover" name="c">目标客户分类</dd>
			<dd name="d">客户放弃原因</dd>
			<dd name="e">流失客户原因</dd>
			<dd name="f">录音范例分类</dd>
			<dd name="g">行动标签维护</dd>
		</dl>
	</div>
	<div class="hyx-hsm-right fl_l" style="border:none;">
	<div class="hyx-spsa" style="overflow:hidden;">
	<div class="com-btnlist hyx-sps-btnlist fl_l">
		<a href="javascript:;" class="com-btna alert_sys_pro_set_c_a fl_l" id="step-add"><i class="min-new-add"></i><label class="lab-mar">新&nbsp;&nbsp;&nbsp;&nbsp;增</label></a>
		<a href="javascript:;" class="com-btna alert_sys_pro_set_a_b fl_l"><i class="min-dele"></i><label class="lab-mar">删&nbsp;&nbsp;&nbsp;&nbsp;除</label></a>
	</div>
	<div class="com-table com-mta test-table fl_l">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" name="a" class="check" /></span></th>
					<th><span class="sp sty-borcolor-b">操作</span></th>
					<th><span class="sp sty-borcolor-b">数据项名称</span></th>
					<th><span class="sp sty-borcolor-b">是否默认值</span></th>
					<th>排序值</th>
				</tr>
			</thead>
			<tbody>              
				<tr>
					<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="a" /></div></td>
					<td style="width:70px;"><div class="overflow_hidden w70 link" id="step-edit"><a href="javascript:;" class="icon-edit alert_sys_pro_set_c_b" title="编辑"></a><a href="javascript:;" class="icon-set-defau" title="设置默认"></a></div></td>
					<td><div class="overflow_hidden w150" title="准备签约">准备签约</div></td>
					<td><div class="overflow_hidden w70" title="是">&radic;</div></td>
					<td><div class="overflow_hidden w70" title="1">1</div></td>
				</tr>
				<tr class="sty-bgcolor-b">
					<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="a" /></div></td>
					<td style="width:70px;"><div class="overflow_hidden w70 link"><a href="javascript:;" class="icon-edit alert_sys_pro_set_c_b" title="编辑"></a><a href="javascript:;" class="icon-set-defau img-gray" title="取消默认"></a></div></td>
					<td><div class="overflow_hidden w150" title="准备签约">准备签约</div></td>
					<td><div class="overflow_hidden w70" title="是">&radic;</div></td>
					<td><div class="overflow_hidden w70" title="2">2</div></td>
				</tr>
				<tr>
					<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="a" /></div></td>
					<td style="width:70px;"><div class="overflow_hidden w70 link"><a href="javascript:;" class="icon-edit alert_sys_pro_set_c_b" title="编辑"></a><a href="javascript:;" class="icon-set-defau img-gray" title="取消默认"></a></div></td>
					<td><div class="overflow_hidden w150" title="准备签约">准备签约</div></td>
					<td><div class="overflow_hidden w70" title="是">&radic;</div></td>
					<td><div class="overflow_hidden w70" title="3">3</div></td>
				</tr>
				<tr class="sty-bgcolor-b">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr class="sty-bgcolor-b">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr class="sty-bgcolor-b">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr class="sty-bgcolor-b">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="com-bot">
		<div class="com-page fl_r">
			<a href="javascript:;" class="no">首页</a>
			<a href="javascript:;" class="no">上页</a>
			<input type="text" value="1" class="pa" /><label>/2</label>
			<a href="javascript:;">下页</a>
			<a href="javascript:;">尾页</a>
			<select>
				<option value="10">10</option>
				<option value="20">20</option>
				<option value="30">30</option>
			</select>
			<label>条/页</label>
		</div>
	</div>
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
        element:"#step-add",
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>点击新增按钮，可以新增目标客户信息。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'bottom'
      },
      {
        element:"#step-edit",
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>在这里可以对目标客户进行修改和设为默认。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'bottom'
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
		pubDivShowIframe('alert_novice_help_return_c','${ctx}/view/help/novice_help_com/alert_novice_help_return_c.jsp','目标客户分类指导完成',650,350);
	});
	window.parent.$('.i-close').live('click',function(){
        window.parent.window.location.href = '${ctx}/main';
    });
});

</script>
</body>
</html>