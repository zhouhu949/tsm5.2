<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<html>
<head>

<title>系统设置-系统字段设置（企业客户）-企业字段</title>

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

<script type="text/javascript" src="${ctx}/static/js/datagrid-dnd/datagrid-dnd.js"></script><!--表格拖拽-->

<script type="text/javascript">
$(function(){
    $('.demoBtn_a').click(function(){
		showIframe('alert_sys_field_set_a','alert_sys_field_set_a.html','新建字段',330,270);
	});
	$('.demoBtn_b').click(function(){
		showIframe('alert_sys_field_set_b','alert_sys_field_set_b.html','编辑字段',330,270);
	});
	$('.demoBtn_c').click(function(){
		showIframe('alert_sys_field_set_c','alert_sys_field_set_c.html','编辑字段',330,270);
	});
 
});
</script>

<script type="text/javascript">
var data = {"total":1,"rows":[
        	{"num":"1","operation":"<a href='###' class='icon-edit demoBtn_b' title='编辑' id='step-edit'></a>","name":"客户名称","type":"文本类型","required":"&radic;","onlyread":"&chi;","start":"&radic;","query":"--"},
        	{"num":"2","operation":"<a href='###' class='icon-edit demoBtn_b' title='编辑'></a>","name":"所属行业","type":"文本类型","required":"&chi;","onlyread":"&chi;","start":"&radic;","query":"--"},
        	{"num":"3","operation":"<a href='###' class='icon-edit demoBtn_b' title='编辑'></a>","name":"注册资本","type":"文本类型","required":"&chi;","onlyread":"&chi;","start":"&radic;","query":"--"},
        	{"num":"4","operation":"<a href='###' class='icon-edit demoBtn_b' title='编辑'></a>","name":"公司网站","type":"文本类型","required":"&chi;","onlyread":"&chi;","start":"&radic;","query":"--"},
        	{"num":"5","operation":"<a href='###' class='icon-edit demoBtn_b' title='编辑'></a>","name":"联系地址","type":"文本类型","required":"&chi;","onlyread":"&chi;","start":"&radic;","query":"--"},
        	{"num":"6","operation":"<a href='###' class='icon-edit demoBtn_b' title='编辑'></a>","name":"法人代表","type":"文本类型","required":"&chi;","onlyread":"&chi;","start":"&radic;","query":"--"},
        	{"num":"7","operation":"<a href='###' class='icon-edit demoBtn_b' title='编辑'></a>","name":"公司传真","type":"文本类型","required":"&chi;","onlyread":"&chi;","start":"&radic;","query":"--"},
        	{"num":"8","operation":"<a href='###' class='icon-edit demoBtn_b' title='编辑'></a>","name":"关键字","type":"文本类型","required":"&chi;","onlyread":"&chi;","start":"&radic;","query":"--"},
        	{"num":"9","operation":"<a href='###' class='icon-edit demoBtn_b' title='编辑'></a>","name":"所在地区","type":"文本类型","required":"&chi;","onlyread":"&chi;","start":"&radic;","query":"--"},
        	{"num":"10","operation":"<a href='###' class='icon-edit demoBtn_b' title='编辑'></a>","name":"经营范围","type":"文本类型","required":"&chi;","onlyread":"&chi;","start":"&radic;","query":"--"},
        	{"num":"11","operation":"<a href='###' class='icon-edit demoBtn_b' title='编辑'></a>","name":"重点关注","type":"文本类型","required":"&chi;","onlyread":"&chi;","start":"&radic;","query":"--"},
        	{"num":"12","operation":"<a href='###' class='icon-edit demoBtn_b' title='编辑'></a>","name":"备注","type":"文本类型","required":"&chi;","onlyread":"&chi;","start":"&radic;","query":"--"},
        	{"num":"13","operation":"<a href='###' class='icon-edit demoBtn_c' title='编辑'></a>","name":"自定义","type":"文本类型","required":"&chi;","onlyread":"&chi;","start":"&radic;","query":"&radic;"}
        ]};
</script>

</head>
<body> 
<div class="hyx-sfs fl_l" style="overflow-x:hidden;">
	<div class="year-sale-play hyx-hsrs-top">
		<ul class="ann-sale-plan clearfix">
			<a href="sys_field_set_enter.html"><li class="select li_first">企业字段</li></a>
			<a href="sys_field_set_enter_a.html"><li class="li_last">联系人字段</li></a>
		</ul>
	</div>
	<dl class="tit">
		<dt class="com-red"><h2>温馨提示：</h2></dt>
		<dd>1.拖动列表可以对字段进行排序，客户名称字段不能被重新排序。</dd>
		<dd>2.可被设置为查询条件的自定义字段最多为两项，选择后的查询条件会影响的页面：客户管理模块中的“我的客户”、“流失客户”、“公海客户”。</dd>
		<dd>3.字段排序会影响的页面：“淘客户”，系统中修改、添加客户信息时的弹出页面。</dd>
	</dl>
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">企业信息字段</label>
		<a href="###" class="com-red fl_r demoBtn_a" id="step-add">+添加自定义字段</a>
	</p>
	<div class="com-table hyx-mpe-table hyx-cm-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a easyui-datagrid" data-options="
				singleSelect:true,
				data:data,
				onLoadSuccess:function(){
					$(this).datagrid('enableDnd');
				}
			">
			<thead>
				<tr class="sty-bgcolor-b">
					<th data-options="field:'num',width:'8%'"><span class="sp sty-borcolor-b">序号</span></th> 
					<th data-options="field:'operation',width:'8%'"><span class="sp sty-borcolor-b">操作</span></th> 
					<th data-options="field:'name',width:'20%'"><span class="sp sty-borcolor-b">字段名称</span></th> 
					<th data-options="field:'type',width:'20%'"><span class="sp sty-borcolor-b">字段类型</span></th> 
					<th data-options="field:'required',width:'10%'"><span class="sp sty-borcolor-b">必填</span></th> 
					<th data-options="field:'onlyread',width:'10%'"><span class="sp sty-borcolor-b">只读</span></th> 
					<th data-options="field:'start',width:'10%'"><span class="sp sty-borcolor-b">启用</span></th> 
					<th data-options="field:'query',width:'10%'">查询字段</th> 
				</tr>
			</thead>
		</table>
	</div>

</div>
<script type="text/javascript">
$(function(){
	var inter_table = null;
	inter_table = setInterval(function(){
		for(var i=0;i<$('.datagrid-btable').find('tr').length;i++){
			var num_index = $('.datagrid-btable').find('tr').eq(i).attr('datagrid-row-index');
			$('.datagrid-btable').find('tr').eq(i).find('td').eq(0).find('div').text(parseInt(num_index)+1);
			$('.datagrid-btable').find('tr').removeClass('sty-bgcolor-b');
			$('.datagrid-btable').find('tr:odd').addClass('sty-bgcolor-b');
		}
	},1);	
});
</script>
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
        element: '#step-add',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>您可以通过点击此按钮来添加系统中的自定义字段，企业字段中的自定义字段最多能添加10个。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'left'
      },
      {
        element: '#step-edit',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>你可以通过点击编辑按钮来编辑系统字段，编辑内容包括：字段名称、是否必填、是否只读字段、是否启用。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
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
		pubDivShowIframe('alert_novice_help_back_a','${ctx}/view/help/novice_help_com/alert_novice_help_back_a.jsp','系统字段设置指导完成',650,350);
	});
	window.parent.$('.i-close').live('click',function(){
        window.parent.window.location.href = '${ctx}/main';
    });
});
</script>
</body>
</html>