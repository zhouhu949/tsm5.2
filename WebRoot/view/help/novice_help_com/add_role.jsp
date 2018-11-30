<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<html>
<head>
<title>公海客户-管理页面</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
<link rel="stylesheet" href="${ctx}/static/js/stepbar/css/control.css" type="text/css" /><!--步骤-->
<link rel="stylesheet" href="${ctx}/static/js/intro/css/introjs.css" type="text/css" /><!--新手引导-->
<link rel="stylesheet" href="${ctx}/static/js/intro/css/bootstrap-responsive.min.css" type="text/css" /><!--新手引导-->
 <script type="text/javascript" src="${ctx}/static/js/stepbar/js/stepbar.js"></script><!--步骤-->
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
	/*左边铺满整屏*/
	$('.hyx-hsm-left').find('dd').each(function(){
    	$(this).siblings().hover(function(){
    		$(this).addClass("dd-hovers");
    	},function(){
    		$(this).removeClass("dd-hovers");
    	});
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
    		/*$(".reso-grou-edit").hide();
    		$(".reso-grou-dele").hide();*/
    	});
    	
    });

});

</script>
<style type="text/css">
	.tree-title{width:150px;}
</style>
</head>
<body> 
	<div class="hyx-hsm-left fl_l">
		<dl>
			<dt>角色列表</dt>
			<dd class="dd-hover">高级管理</dd>
			<dd>中级管理</dd>
			<dd>销售人员</dd>
		</dl>
		<p class="add-role"><a class="add-role-new" href="javascript:;"><span>添加角色</span><img src="${ctx}/static/images/add-role.png" alt=""></a></p>
	</div>
	<div class="hyx-oral-right fl_l" style="padding-top:0;">



		<div class="role-new fl_l" style="width:100%">
	       
<div id="stepBar" class="ui-stepBar-wrap">
				<div class="ui-stepBar">
					<div class="ui-stepProcess"></div>
				</div>
				<div class="ui-stepInfo-wrap">
					<table class="ui-stepLayout" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="ui-stepInfo">
								<!-- <a class="ui-stepSequence" onclick="page_tab(1)">1</a> -->
								<a class="ui-stepSequence01">1</a>
								<p class="ui-stepName">配置角色</p>
							</td>
							<td class="ui-stepInfo">
								<!-- <a class="ui-stepSequence" onclick="page_tab(2)">2</a> -->
								<a class="ui-stepSequence01">2</a>
								<p class="ui-stepName">配置权限</p>
							</td>
							<td class="ui-stepInfo">
								<a class="ui-stepSequence01">3</a>
								<p class="ui-stepName">配置成员</p>
							</td>
							<td class="ui-stepInfo">
								<a class="ui-stepSequence01">4</a>
								<p class="ui-stepName">快捷功能</p>
							</td>
							<td class="ui-stepInfo">
								<a class="ui-stepSequence01">5</a>
								<p class="ui-stepName">时间设置</p>
							</td>
						</tr>
					</table>

				</div>
			</div>

			<div class="role-edit-info" id="role-menu1">
				<p class="clearfix"><label class="fl_l" for="">输入角色名：</label><input class="inpu-role fl_l" type="text"></p>
				<p class="manag-role"><input type="radio" checked><span>销售</span><input type="radio"><span>管理者</span></p>
			</div>
			<div class="func-menu" id="role-menu2" style="display:none;">
				<div class="menu-title">功能菜单</div>
				 <div class="menu-cont">
					<p class="sele-canc clearfix">
						<input type="checkbox"/>
						<span class="fl_l" style="margin-right:30px;">全部选中</span>
						<input type="checkbox" />
						<span class="fl_l">全部取消</span>
					</p>
					<div class="all-menu">
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">销售计划</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">年度规划</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">个人月度计划</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">团队月度计划</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">团队月度计划查看</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">部门月度计划</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">部门月度计划查看</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">我的计划</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">日计划审核</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">工作日志</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">工作指导</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">淘客户</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">客户跟进</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">客户跟进</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">跟进记录</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">跟进警报</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">销售进程查询</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">自荐审核</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">客户管理</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">我的客户</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">流失客户</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">公海客户</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">客户交接</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						    <span class="menu-name fl_l">交接记录</span>
						    <input type="checkbox"/>
						    <span class="menu-name fl_l">撞单查询</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">客户签约</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">签约客户</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">合同管理</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">订单审核</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">资源管理</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">待分配资源</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">已分配资源</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">全部资源</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">员工资源管理</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">通信管理</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">通话记录</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">普通短信发送记录</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">挂机短信发送记录</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">短信接收记录</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">短信群发</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">邮件群发</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">邮件发送记录</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">知识库</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">诈术模板</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">录音范例</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">知识库维护</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">统计分析(个人)</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">今日数据</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">历史数据</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">统计分析(团队)</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">今日数据</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">历史数据</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">通话效率分析</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">销售目标完成结果</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">月度计划执行结果</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">沉默客户统计</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">流失客户统计</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">资源信息分析</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">排行榜查询</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">系统设置</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">系统属性设置</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">短信模板设置</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">系统字段设置</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">销售管理设置</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">通知公告</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">用户日志信息查询</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">消息设置</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">等级成长设置</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">成员角色</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">角色编辑</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">成员编辑</span>
						</p>
					</div>
				</div>
			</div>
			<div class="func-menu" id="role-menu3" style="display:none;">
				<div class="menu-title">杭州企蜂通信有限公司</div>
				<div class="menu-cont">
					<p class="sele-canc clearfix">
						<input type="checkbox">
						<span class="fl_l" style="margin-right:30px;">全部选中</span>
						<input type="checkbox">
						<span class="fl_l">全部取消</span>
					</p>
					 <ul id="tt1" class="easyui-tree" >
			            <li>
			                <span><input type="checkbox">总经办</span>
			                <ul>
			                    <li data-options="state:'closed'">
			                        <span><input type="checkbox">张强(帐号：001)</span>
			                        <ul>
			                            <li>
			                                <span><input type="checkbox">File 11</span>
			                                <ul>
			                                    <li><input type="checkbox">srfelarsrfelarsrfelar</li>
			                                    <li><input type="checkbox">dfvesrfsrfelar</li>
			                                </ul>
			                            </li>
			                            <li>
			                                <span><input type="checkbox">File 12</span>
			                            </li>
			                            <li>
			                                <span><input type="checkbox">File 13</span>
			                            </li>
			                        </ul>
			                    </li>
			                    <li>
			                        <span><input type="checkbox">李刚(帐号：002)</span>
			                    </li>
			                    <li>
			                        <span><input type="checkbox">File 李刚(帐号：002)</span>
			                    </li>
			                    <li><input type="checkbox">File 4</li>
			                    <li><input type="checkbox">File 5</li>
			                </ul>
			            </li>
			            <li>
			                <span><input type="checkbox">销售部</span>
			                <ul>
			                	<li>
			                		<span><input type="checkbox">销售1部</span>
			                		<ul>
			                			<li><span><input type="checkbox">李刚(帐号：002)</span></li>
			                			<li><span><input type="checkbox">李刚(帐号：002)</span></li>
			                			<li><span><input type="checkbox">李刚(帐号：002)</span></li>
			                		</ul>
			                	</li>
			                	<li>
			                		<span><input type="checkbox">销售2部</span>
			                		<ul>
			                			<li><span><input type="checkbox">李刚(帐号：002)</span></li>
			                			<li><span><input type="checkbox">李刚(帐号：002)</span></li>
			                			<li><span><input type="checkbox">李刚(帐号：002)</span></li>
			                		</ul>
			                	</li>
			                </ul>
			            </li>
			        </ul>
				</div>
			</div>
			<div class="func-menu" id="role-menu4" style="display:none;">
				<div class="menu-title">功能菜单</div>
				<div class="menu-cont">
					<p class="sele-canc clearfix">
						<input type="checkbox"/>
						<span class="fl_l" style="margin-right:30px;">全部选中</span>
						<input type="checkbox" />
						<span class="fl_l">全部取消</span>
					</p>
					<div class="all-menu">
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">销售计划</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">年度规划</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">个人月度计划</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">团队月度计划</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">团队月度计划查看</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">部门月度计划</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">部门月度计划查看</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">我的计划</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">日计划审核</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">工作日志</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">工作指导</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">淘客户</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">客户跟进</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">客户跟进</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">跟进记录</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">跟进警报</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">销售进程查询</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">自荐审核</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">客户管理</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">我的客户</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">流失客户</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">公海客户</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">客户交接</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						    <span class="menu-name fl_l">交接记录</span>
						    <input type="checkbox"/>
						    <span class="menu-name fl_l">撞单查询</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">客户签约</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">签约客户</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">合同管理</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">订单审核</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">资源管理</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">待分配资源</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">已分配资源</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">全部资源</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">员工资源管理</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">通信管理</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">通话记录</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">普通短信发送记录</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">挂机短信发送记录</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">短信接收记录</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">短信群发</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">邮件群发</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">邮件发送记录</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">知识库</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">诈术模板</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">录音范例</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">知识库维护</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">统计分析(个人)</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">今日数据</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">历史数据</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">统计分析(团队)</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">今日数据</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">历史数据</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">通话效率分析</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">销售目标完成结果</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">月度计划执行结果</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">沉默客户统计</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">流失客户统计</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">资源信息分析</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">排行榜查询</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">系统设置</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">系统属性设置</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">短信模板设置</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">系统字段设置</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">销售管理设置</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">通知公告</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">用户日志信息查询</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">消息设置</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">等级成长设置</span>
						</p>
						<p class="first-menu clearfix">
							<input type="checkbox"/>
						<span class="fl_l">成员角色</span>
						</p>
						<p class="second-menu clearfix">
							<input type="checkbox"/>
						<span class="menu-name fl_l">角色编辑</span>
						<input type="checkbox"/>
						<span class="menu-name fl_l">成员编辑</span>
						</p>
					</div>
				</div>
			</div>
			<div class="func-menu" id="role-menu5" style="display:none;">
				<div class="menu-title">时间设置</div>
				<div class="work-time-set" style="margin-top:20px;">
					<p class="clearfix">
						<span class="sp sty-borcolor-b skin-minimal" style="float:left;margin-top:3px;"><input type="checkbox" name="a" class="check" /></span><span class="work-time-week fl_l">星期一</span><input class="fl_l" type="text" value="00:00:00"><span class="time-line fl_l"></span><input class="fl_l" type="text"value="23:59:59">
					</p>
				</div>
				<div class="work-time-set">
					<p class="clearfix">
						<span class="sp sty-borcolor-b skin-minimal" style="float:left;margin-top:3px;"><input type="checkbox" name="a" class="check" /></span><span class="work-time-week fl_l">星期二</span><input class="fl_l" type="text" value="00:00:00"><span class="time-line fl_l"></span><input class="fl_l" type="text" value="23:59:59">
					</p>
				</div>
				<div class="work-time-set">
					<p class="clearfix">
						<span class="sp sty-borcolor-b skin-minimal" style="float:left;margin-top:3px;"><input type="checkbox" name="a" class="check" /></span><span class="work-time-week fl_l">星期三</span><input class="fl_l" type="text" value="00:00:00"><span class="time-line fl_l"></span><input class="fl_l" type="text" value="23:59:59">
					</p>
				</div>
				<div class="work-time-set">
					<p class="clearfix">
						<span class="sp sty-borcolor-b skin-minimal" style="float:left;margin-top:3px;"><input type="checkbox" name="a" class="check" /></span><span class="work-time-week fl_l">星期四</span><input class="fl_l" type="text" value="00:00:00"><span class="time-line fl_l"></span><input class="fl_l" type="text" value="23:59:59">
					</p>
				</div>
				<div class="work-time-set">
					<p class="clearfix">
						<span class="sp sty-borcolor-b skin-minimal" style="float:left;margin-top:3px;"><input type="checkbox" name="a" class="check" /></span><span class="work-time-week fl_l">星期五</span><input class="fl_l" type="text" value="00:00:00"><span class="time-line fl_l"></span><input class="fl_l" type="text" value="23:59:59">
					</p>
				</div>
				<div class="work-time-set">
					<p class="clearfix">
						<span class="sp sty-borcolor-b skin-minimal" style="float:left;margin-top:3px;"><input type="checkbox" name="a" class="check" /></span><span class="work-time-week fl_l">星期六</span><input class="fl_l" type="text" value="00:00:00"><span class="time-line fl_l"></span><input class="fl_l" type="text" value="23:59:59">
					</p>
				</div>
				<div class="work-time-set">
					<p class="clearfix">
						<span class="sp sty-borcolor-b skin-minimal" style="float:left;margin-top:3px;"><input type="checkbox" name="a" class="check" /></span><span class="work-time-week fl_l">星期日</span><input class="fl_l" type="text" value="00:00:00"><span class="time-line fl_l"></span><input class="fl_l" type="text" value="23:59:59">
					</p>
				</div>
			</div>
			<div class="role-step clearfix" id="step">
				<input type="hidden" id="step_" value="1">
				<a href="javascript:;" class="com-btnb last-step fl_l"><label>上一步</label></a>
				<a href="javascript:;" class="com-btnb com-btnb-sty next-step fl_l" id="step_next"><label>下一步</label></a>
				<a href="javascript:;" class="com-btnb com-btnb-sty fish-secu fl_r" id="step_plte"><label>完成</label></a>
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
        element: '#role-menu1',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>请输入角色名并选择角色类别。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'bottom'
      },
      {
        element: '#step',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>请点击“下一步”，进行配置角色权限操作。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
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
	$(".introjs-skipbutton").live('click',function(){
		window.parent.window.location.href = '${ctx}/view/help/novice_help_com/add_role_a.jsp';
	})

});

function step_(){
	var _this = stepBar;
	var curStep = stepBar.curStep;
	$("#step_").val(curStep);
	var step = parseInt($("#step_").val())+parseInt(1);
	if (step < 6) {
		$("#step_").val(step);
		  var triggerStep = step;
		  page_tab(step);
	     _this.triggerStep = triggerStep;
	     _this.percent();
	     if(step > 1){
	     	$(".last-step").css({'display':'inline-block'});
	     };
	     if(step == 5){
	     	$(".fish-secu").css({'display':'inline-block'});
	     	$(".last-step").css({'display':'inline-block'});
	     	$(".next-step").hide();
	     };
	 }else{

	 }
}

function step_1(){
	var _this = stepBar;
	var curStep = stepBar.curStep;
	$("#step_").val(curStep);
	var step = parseInt($("#step_").val())-parseInt(1);
	if(step > 0 && step < 6){
		$("#step_").val(step);
		  var triggerStep = step;
		  page_tab(step);
	     _this.triggerStep = triggerStep;
	     _this.percent();
	     if(step < 5){
	     	$(".fish-secu").hide();
	     	$(".last-step").css({'display':'inline-block'});
	     	$(".next-step").css({'display':'inline-block'});
	     };
	     if(step == 1){
	     	$(".last-step").hide();
	     };
	 }else{

	 }
}
function page_tab(type){
	$("div[id^='role-menu']").each(function(){
		$("#"+this.id).hide();
		
	});
	$("#role-menu"+type).show();

}
$(function(){
	$("#step_").val(2);
	stepBar.init("stepBar", {
		step : 1,
		change : true,
		animation : true
	});
    $(".next-step").css("margin-left","120px");
});
</script>
</body>
</html>