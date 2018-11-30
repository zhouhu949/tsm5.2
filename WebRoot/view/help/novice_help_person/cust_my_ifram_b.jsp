<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<html>
<head>
<title>客户管理-我的客户</title>

<link rel="stylesheet" href="${ctx }/static/js/intro/css/introjs.css" type="text/css" /><!--新手引导-->
<link rel="stylesheet" href="${ctx }/static/js/intro/css/bootstrap-responsive.min.css" type="text/css" /><!--新手引导-->
<style type="text/css">
/* tabcon的宽度比media里面少15  不减去的话因为浮动右边会掉下去  */
@media screen and (min-width: 1255px){  /* 1280 */
.hyx-cm-box .tabcon{width:1130px;}
.hyx-cm-left{width:775px;}
}
@media screen and (min-width: 1332px) and  (max-width: 1360px){ /* 1360 */
.hyx-cm-box .tabcon{width:1210px;}
.hyx-cm-left{width:860px;}
}
@media screen and (min-width: 1361px) and  (max-width: 1366px){ /* 1366 */
.hyx-cm-box .tabcon{width:1216px;}
.hyx-cm-left{width:860px;}
}
@media screen and (min-width: 1376px){   /* 1400 */
.hyx-cm-box .tabcon{width:1250px;}
}
@media screen and (min-width: 1416px){   /* 1440 */
.hyx-cm-box .tabcon{width:1290px;}
.hyx-cm-left{width:925px;}
}
@media screen and (min-width: 1576px){   /* 1600 */
.hyx-cm-box .tabcon{width:1450px;}
.hyx-cm-left{width:1085px;}
}
@media screen and (min-width: 1656px){  /* 1680 */
.hyx-cm-box .tabcon{width:1530px;}
}
@media screen and (min-width: 1896px){  /* 1920������ */
.hyx-cm-box .tabcon{width:1770px;}
}
.introjs-fixParent{position:relative !important;}
</style>

<!--本页面js-->
<script type="text/javascript">
$(function(){

	// 左边选择效果
	$('.tabTagList').find('li').click(function(){
		$(this).addClass('current').siblings('li').removeClass('current');
		$('#iframepage').attr('src','${ctx}/view/help/novice_help_person/cust_my_'+ $(this).attr('name') +'.jsp');
	});
});
</script>
</head>
<body> 
<div class="hyx-cm-box fl_l">
	<div class="tabTagBox">
		<ul class="tabTagList">
			<li id="tag01" name="a"><label>资&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;源</label><span></span></li>
			<li id="tag02" class="current" name="b"><label>意向客户</label><span></span></li>
			<li id="tag03" name="c"><label>签约客户</label><span></span></li>
			<li id="tag04" name="d"><label>沉默客户</label><span></span></li>
			<li id="tag05" name="e"><label>流失客户</label><span></span></li>
			<li id="tag06" name="f"><label>全部客户</label><span></span></li>
		</ul>
	</div>
	<div class="tabcon">
		<div class="hyx-cmb fl_l">
		<!--资源-->
			<div class="hyx-cm-left hyx-layout-content">
				<div class="com-search hyx-cm-search">
					<div class="com-search-box fl_l">
					   <p class="search clearfix"><input class="input-query fl_l" type="text" value="" /><label class="hide-span">输入客户名称/联系人/联系电话</label></p>
					   	<div class="list-box">
						   <dl class="select">
								<dt>客户类型</dt>
								<dd>
									<ul>
										<li><a href="#" title="客户类型">客户类型</a></li>
										<li><a href="#" title="潜在客户">潜在客户</a></li>
										<li><a href="#" title="普通客户">普通客户</a></li>
										<li><a href="#" title="VIP客户">VIP客户</a></li>
									</ul>
								</dd>
						   </dl>
						   <dl class="select">
								<dt>淘到客户时间</dt>
								<dd>
									<ul>
										<li><a href="#" title="淘到客户时间">淘到客户时间</a></li>
										<li><a href="#" title="当天">当天</a></li>
										<li><a href="#" title="本周">本周</a></li>
										<li><a href="#" title="本月">本月</a></li>
										<li><a href="#" title="半年">半年</a></li>
										<li><a href="#" title="自定义" class="date-range diy" id="d1">自定义</a></li>
									</ul>
								</dd>
							</dl>
							<dl class="select">
								<dt>最近联系时间</dt>
								<dd>
									<ul>
										<li><a href="#" title="最近联系时间">最近联系时间</a></li>
										<li><a href="#" title="当天">当天</a></li>
										<li><a href="#" title="本周">本周</a></li>
										<li><a href="#" title="本月">本月</a></li>
										<li><a href="#" title="半年">半年</a></li>
										<li><a href="#" title="自定义" class="date-range diy" id="d2">自定义</a></li>
									</ul>
								</dd>
							</dl>
							<dl class="select">
								<dt>下次联系时间</dt>
								<dd>
									<ul>
										<li><a href="#" title="下次联系时间">下次联系时间</a></li>
										<li><a href="#" title="当天">当天</a></li>
										<li><a href="#" title="本周">本周</a></li>
										<li><a href="#" title="本月">本月</a></li>
										<li><a href="#" title="半年">半年</a></li>
										<li><a href="#" title="自定义" class="date-range diy" id="d3">自定义</a></li>
									</ul>
								</dd>
							</dl>
							<dl class="select">
								<dt style="border-right:none !important;">销售进程</dt>
								<dd>
									<ul>
										<li><a href="#" title="销售进程">销售进程</a></li>
										<li><a href="#" title="F初步沟通有意向">F初步沟通有意向</a></li>
										<li><a href="#" title="E深入沟通需求清晰">E深入沟通需求清晰</a></li>
										<li><a href="#" title="D全面了解产品">D全面了解产品</a></li>
										<li><a href="#" title="C会客面谈">C会客面谈</a></li>
										<li><a href="#" title="B无疑义准备签约">B无疑义准备签约</a></li>
										<li><a href="#" title="A成功签约">A成功签约</a></li>
										<li><a href="#" title="资源-信息错误">资源-信息错误</a></li>
									</ul>
								</dd>
							</dl>
							<div class="list-hid fl_l">
								<div class="reso-sub-dep fl_l">
									<span class="sub-dep-inpu">所有者</span>
									<div class="manage-drop">
										<div class="sub-dep-ul">
											<ul id="tt1" class="easyui-tree" >
									            <li>
									                <span><input type="checkbox">踦借古喻今</span>
									                <ul>
									                    <li data-options="state:'closed'">
									                        <span><input type="checkbox">Sub 踦借古喻今 1</span>
									                        <ul>
									                            <li>
									                                <span><input type="checkbox">随便一睦11</span>
									                                <ul>
									                                    <li><input type="checkbox">大中企业</li>
									                                    <li><input type="checkbox">地大中企业</li>
									                                </ul>
									                            </li>
									                            <li>
									                                <span><input type="checkbox">埯当空舞</span>
									                            </li>
									                            <li>
									                                <span><input type="checkbox">极礌有</span>
									                            </li>
									                        </ul>
									                    </li>
									                    <li>
									                        <span><input type="checkbox">端口端口 2</span>
									                    </li>
									                    <li>
									                        <span><input type="checkbox">端口端口 3</span>
									                    </li>
									                    <li><input type="checkbox">端口端口 4</li>
									                    <li><input type="checkbox">端口端口 5</li>
									                </ul>
									            </li>
									            <li>
									                <span>端口端口21</span>
									            </li>
							       			</ul>
							    		</div>
									    <div class="sure-cancle clearfix" style="width:120px">
											<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" href="javascript:;"><label>确定</label></a>
											<a class="com-btnd bomp-btna fl_l" id="close02" href="javascript:;"><label>清空</label></a>
										</div>
									</div>
								</div>
								<p class='tag fl_l' style="padding:5px 0px 0 5px;"><label>行动标签：</label><a href='javascript:;' class="alert_cust_follow_e">选择标签</a></p>

							</div>
						</div>
					</div>
					<input class="query-button fl_r" type="button" value="查&nbsp;询" onClick="doQuery();" />
					<a href="javascript:;" class="more"><i></i>更多</a>
				</div>
				<p class="com-moTag"><label class="a">数据分类：</label><a href="javascript:;" class="e e-hover">全部</a><a href="javascript:;" class="e">未联系资源</a><a href="javascript:;" class="e">已联系资源</a><a href="javascript:;" class="e">已安排计划</a><a href="javascript:;" class="e">未安排计划</a></p>
				<div class="com-btnlist hyx-cm-btnlist fl_l">
					<a href="javascript:;" class="com-btna demoBtn_a fl_l"><i class="min-turn-will"></i><label class="lab-mar">放入公海</label></a>
					<a href="javascript:;" class="com-btna demoBtn_e fl_l"><i class="min-messag"></i><label class="lab-mar">发送短信</label></a>
				</div>
				<div class="com-table com-mta hyx-cm-table fl_l" style="display:block;">
					<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
						<thead>
							<tr class="sty-bgcolor-b">
								<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" name="a" class="check" /></span></th>
								<th><span class="sp sty-borcolor-b">操作</span></th> 
								<th><span class="sp sty-borcolor-b">客户名称</span></th>
								<th><span class="sp sty-borcolor-b">客户类型</span></th>
								<th><span class="sp sty-borcolor-b">销售进程</span></th>
								<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>最近联系</span><i></i></label></span></th>
								<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>下次联系</span><i></i></label></span></th>
								<th><span class="sp sty-borcolor-b"><label class="lab-d"><span>淘到客户</span><i></i></label></span></th>
								<th>所有者</th>
							</tr>
						</thead>
						<tbody>              
							<tr>
								<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="a" /></div></td>
								<td style="width:100px;"><div class="overflow_hidden w100 link"><a href="javascript:;" class="icon-follow-up" title="客户跟进" id="step_1"></a><a href="javascript:;" class="icon-cust-card" title="客户卡片"></a><a href="javascript:;" class="icon-nofocus" title="重点关注"></a><a href="javascript:;" class="icon-sign-cont" title="签约"></a></div></td>
								<td><div class="overflow_hidden w70" title="大华科技">大华科技</div></td>
								<td><div class="overflow_hidden w70" title="普通客户">普通客户</div></td>
								<td><div class="overflow_hidden w70" title="初步沟通">初步沟通</div></td>
								<td><div class="overflow_hidden w120" title="2015-01-01 09:09:09">2015-01-01 09:09:09</div></td>
								<td><div class="overflow_hidden w120" title="2015-01-01 09:09:09">2015-01-01 09:09:09</div></td>
								<td><div class="overflow_hidden w120" title="2015-01-01 09:09:09">2015-01-01 09:09:09</div></td>
								<td><div class="overflow_hidden w50" title="omg001">omg001</div></td>
							</tr>
							<tr class="sty-bgcolor-b">
								<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="a" /></div></td>
								<td style="width:100px;"><div class="overflow_hidden w100 link"><a href="javascript:;" class="icon-follow-up" title="客户跟进"></a><a href="javascript:;" class="icon-cust-card" title="客户卡片"></a><a href="javascript:;" class="icon-focus-atten" title="取消关注"></a><a href="javascript:;" class="icon-sign-cont" title="签约"></a></div></td>
								<td><div class="overflow_hidden w70" title="大华科技">大华科技</div></td>
								<td><div class="overflow_hidden w70" title="普通客户">普通客户</div></td>
								<td><div class="overflow_hidden w70" title="初步沟通">初步沟通</div></td>
								<td><div class="overflow_hidden w120" title="2015-01-01 09:09:09">2015-01-01 09:09:09</div></td>
								<td><div class="overflow_hidden w120" title="2015-01-01 09:09:09">2015-01-01 09:09:09</div></td>
								<td><div class="overflow_hidden w120" title="2015-01-01 09:09:09">2015-01-01 09:09:09</div></td>
								<td><div class="overflow_hidden w50" title="omg001">omg001</div></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
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
							<option value="50">50</option>
							<option value="100">100</option>
						</select>
						<label>条/页</label>
					</div>
				</div>
			</div>
			<div class="hyx-cm-right hyx-layout-side">
				<div class="hyx-cfu-card fl_l">
					<div class="tit fl_l"><span title="杭州企蜂科技有限公司" class="sp">杭州企蜂科技有限公司</span><div class="mail icon-conte-list" title="通讯录">
						<div class="drop" style="display:none;">
							<div class="arrow"><em>◆</em><span>◆</span></div>
								<ul>
									<li><p class="list fl_l"><span>张三（总监）：</span><label title="15898898774">15898898774</label><a href="javascript:;" class="tel icon-phone" title="电话联系"></a><a href="javascript:;" class="e-mail icon-email demoBtn_m" title="邮件联系"></a></p></li>
									<li><p class="list fl_l"><span>张三（总监）：</span><label title="15898898774">15898898774</label><a href="javascript:;" class="tel icon-phone" title="电话联系"></a><a href="javascript:;" class="e-mail icon-email demoBtn_m" title="邮件联系"></a></p></li>
									<li><p class="list fl_l"><span>张三（总监）：</span><label title="15898898774">15898898774</label><a href="javascript:;" class="tel icon-phone" title="电话联系"></a><a href="javascript:;" class="e-mail icon-email demoBtn_m" title="邮件联系"></a></p></li>
								</ul>
							</div>
						</div>
						<a href="javascript:;" class="edit demoBtn_l icon-edit fl_r" title="编辑"></a>
					</div>
					<p class="list fl_l"><span>联系地址：</span><label title="杭州市西湖区翠柏路">杭州市西湖区翠柏路</label></p>
					<p class="list fl_l"><span>联系人：</span><label title="小米">小米</label></p>
					<p class="list fl_l"><span>联系电话：</span><label title="15898898774">15898898774</label><a href="javascript:;" class="tel icon-phone" title="电话联系"></a><a href="javascript:;" class="mess icon-message demoBtn_e" title="短信联系" id="demoBtn_fa"></a></p>
					<p class="list fl_l"><span>备用电话：</span><label title="15898898774">15898898774</label><a href="javascript:;" class="tel icon-phone" title="电话联系"></a><a href="javascript:;" class="mess icon-message demoBtn_e" title="短信联系" id="demoBtn_fa"></a></p>
				</div>
				<div class="hyx-cm-new fl_l">
					<div class="top">
						<label>客户跟进最新动态</label><i></i>
					</div>
					<ul>
						<li><label class="lab">最近跟进：</label><span class="sp">2015-10-10&nbsp;09:09:09</span></li>
						<li class="sty-bgcolor-b"><label class="lab">拜访方式：</label><span class="sp">电话联系</span></li>
						<li><label class="lab">销售联系人：</label><span class="sp">李工</span></li>
						<li class="sty-bgcolor-b"><label class="lab">客户联系人：</label><span class="sp">张三</span></li>
						<li><label class="lab">销售进程：</label><span class="sp">有意向</span></li>
						<li class="sty-bgcolor-b"><label class="lab">行动标签：</label><span class="sp">初次见面，高意向客户</span></li>
						<li><label class="lab">下次联系时间：</label><span class="sp">2015-10-10&nbsp;09:09:09</span></li>
						<li class="sty-bgcolor-b load"><label class="lab">联系小记：</label><span class="sp">本次跟进，需要继续跟客户会面，然后代入公司。就是这样的吧，是的吧</span>
							<span class="drop" style="display:none;">
								<label class="arrow"><em>◆</em><span>◆</span></label>
								<label class="box">联系小记：本次跟进，需要继续跟客户会面，然后代入公司。就是这样的吧，是的吧</label>
							</span>
						</li>
						<li><label class="lab"></label><span class="sp"></span></li>
					</ul>
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
    steps: [
      {
        element: '#step_1',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>当客户变为意向客户后，就要对客户进行持续的跟进了，点击“跟进”按钮会进入到跟进页面。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'right'
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
		window.parent.window.location.href = '${ctx}/view/help/novice_help_person/follow_up_detail_enter.jsp';
	})
});
</script>
</body>
</html>