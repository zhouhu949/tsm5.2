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
 
 <style>
      .introjs-skipbutton {
        border:0 none;
        color:#000;
        position:absolute;
        bottom:11px;
        right:6px;
        z-index:555;
      }
      .tree{
      	padding-top:0 !important;
      }
      .tree li div{
      	height:18px !important;
      	line-height:18px !important;
      }
      .tree-title{
      	height:18px !important;
      	line-height:18px !important;
      }
    </style>

<script type="text/javascript">
$(function(){
	/*表单优化*/
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
<script type="text/javascript" src="${ctx}/static/js/stepbar/js/stepbar.js"></script><!--步骤-->
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

			<div class="role-edit-info" id="role-menu1" style="display:none;">
				<p class="clearfix"><label class="fl_l" for="">输入角色名：</label><input class="inpu-role fl_l" type="text"></p>
				<p class="manag-role"><input type="radio" checked><span>销售</span><input type="radio"><span>管理者</span></p>
			</div>
			<div class="func-menu" id="role-menu2">
				<div class="menu-title">功能菜单</div>
				 <div class="menu-cont">
					<p class="sele-canc clearfix">
						<input type="radio">
						<span class="fl_l" style="margin-right:30px;">全部选中</span>
						<input type="radio">
						<span class="fl_l">全部取消</span>
					</p>
					<ul id="tt1" class="tree fl_l"><li><div id="_easyui_tree_995" class="tree-node"><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">淘客户</span></div></li><li><div id="_easyui_tree_996" class="tree-node"><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">销售计划管理</span></div><ul style="display:block"><li><div id="_easyui_tree_997" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">目标管理</span></div></li><li><div id="_easyui_tree_998" class="tree-node"><span class="tree-indent"></span><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">部门月计划</span></div><ul style="display:block"><li><div id="_easyui_tree_999" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox0"></span><span class="tree-title">查看</span></div></li><li><div id="_easyui_tree_1000" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox0"></span><span class="tree-title">编辑</span></div></li></ul></li><li><div id="_easyui_tree_1001" class="tree-node"><span class="tree-indent"></span><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">小组月计划</span></div><ul style="display:block"><li><div id="_easyui_tree_1002" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">查看页面</span></div></li><li><div id="_easyui_tree_1003" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox0"></span><span class="tree-title">编辑</span></div></li></ul></li><li><div id="_easyui_tree_1004" class="tree-node"><span class="tree-indent"></span><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">个人月计划</span></div><ul style="display:block"><li><div id="_easyui_tree_1005" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">月度计划查看（个人未执行）</span></div></li><li><div id="_easyui_tree_1006" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">月度计划查看（个人）</span></div></li><li><div id="_easyui_tree_1007" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">月度计划编辑（个人）</span></div></li></ul></li><li><div id="_easyui_tree_1008" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">历年计划</span></div></li><li><div id="_easyui_tree_1009" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">个人日计划</span></div></li><li><div id="_easyui_tree_1010" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">日计划审核</span></div></li><li><div id="_easyui_tree_1011" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">工作日志</span></div></li></ul></li><li><div id="_easyui_tree_1012" class="tree-node"><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">客户跟进</span></div><ul style="display:block"><li><div id="_easyui_tree_1013" class="tree-node"><span class="tree-indent"></span><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">客户跟进</span></div><ul style="display:block"><li><div id="_easyui_tree_1014" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">跟进详细页面</span></div></li></ul></li><li><div id="_easyui_tree_1015" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">跟进记录</span></div></li><li><div id="_easyui_tree_1016" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">跟进警报</span></div></li><li><div id="_easyui_tree_1017" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">销售进程查询</span></div></li><li><div id="_easyui_tree_1018" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">延期审核</span></div></li></ul></li><li><div id="_easyui_tree_1019" class="tree-node"><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">客户管理</span></div><ul style="display:block"><li><div id="_easyui_tree_1020" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">我的客户</span></div></li><li><div id="_easyui_tree_1021" class="tree-node"><span class="tree-indent"></span><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">流失客户</span></div><ul style="display:block"><li><div id="_easyui_tree_1022" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox0"></span><span class="tree-title">客户卡片按钮</span></div></li><li><div id="_easyui_tree_1023" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox0"></span><span class="tree-title">客户跟进按钮</span></div></li><li><div id="_easyui_tree_1024" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox0"></span><span class="tree-title">放弃客户(按钮)</span></div></li><li><div id="_easyui_tree_1025" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox0"></span><span class="tree-title">删除按钮</span></div></li><li><div id="_easyui_tree_1026" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox0"></span><span class="tree-title">重新分配(按钮)</span></div></li><li><div id="_easyui_tree_1027" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox0"></span><span class="tree-title">回收待分配(按钮)</span></div></li><li><div id="_easyui_tree_1028" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox0"></span><span class="tree-title">清空(按钮)</span></div></li></ul></li><li><div id="_easyui_tree_1029" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">公海客户</span></div></li><li><div id="_easyui_tree_1030" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">客户交接</span></div></li><li><div id="_easyui_tree_1031" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">交接记录</span></div></li><li><div id="_easyui_tree_1032" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">撞单查询</span></div></li></ul></li><li><div id="_easyui_tree_1033" class="tree-node"><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">客户签约</span></div><ul style="display:block"><li><div id="_easyui_tree_1034" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">签约客户</span></div></li><li><div id="_easyui_tree_1035" class="tree-node"><span class="tree-indent"></span><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">合同管理</span></div><ul style="display:block"><li><div id="_easyui_tree_1036" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">合同新增</span></div></li><li><div id="_easyui_tree_1037" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">合同修改</span></div></li><li><div id="_easyui_tree_1038" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">合同查看</span></div></li></ul></li><li><div id="_easyui_tree_1039" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">订单审核</span></div></li></ul></li><li><div id="_easyui_tree_1040" class="tree-node"><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">资源管理</span></div><ul style="display:block"><li><div id="_easyui_tree_1041" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">待分配资源</span></div></li><li><div id="_easyui_tree_1042" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">已分配资源</span></div></li><li><div id="_easyui_tree_1043" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">全部资源</span></div></li><li><div id="_easyui_tree_1044" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">员工资源管理</span></div></li></ul></li><li><div id="_easyui_tree_1045" class="tree-node"><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">通信管理</span></div><ul style="display:block"><li><div id="_easyui_tree_1046" class="tree-node"><span class="tree-indent"></span><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">通话记录</span></div><ul style="display:block"><li><div id="_easyui_tree_1047" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox0"></span><span class="tree-title">客户卡片(按钮)</span></div></li><li><div id="_easyui_tree_1048" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox0"></span><span class="tree-title">播放录音(按钮)</span></div></li><li><div id="_easyui_tree_1049" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox0"></span><span class="tree-title">录音下载</span></div></li></ul></li><li><div id="_easyui_tree_1050" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">短信发送记录</span></div></li><li><div id="_easyui_tree_1051" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">挂机短信发送记录</span></div></li><li><div id="_easyui_tree_1052" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">短信接收记录</span></div></li><li><div id="_easyui_tree_1053" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">短信群发</span></div></li><li><div id="_easyui_tree_1054" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">邮件群发</span></div></li><li><div id="_easyui_tree_1055" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">邮件发送记录</span></div></li><li><div id="_easyui_tree_1056" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">通信时长分配</span></div></li><li><div id="_easyui_tree_1057" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">时长分配日志</span></div></li></ul></li><li><div id="_easyui_tree_1058" class="tree-node"><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">知识库</span></div><ul style="display:block"><li><div id="_easyui_tree_1059" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">话术模板</span></div></li><li><div id="_easyui_tree_1060" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">录音范例</span></div></li></ul></li><li><div id="_easyui_tree_1061" class="tree-node"><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">统计分析</span></div><ul style="display:block"><li><div id="_easyui_tree_1062" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">个人今日数据</span></div></li><li><div id="_easyui_tree_1063" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">个人统计分析</span></div></li><li><div id="_easyui_tree_1064" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">小组今日数据</span></div></li><li><div id="_easyui_tree_1065" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">小组历史数据</span></div></li><li><div id="_easyui_tree_1066" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">通话效率分析</span></div></li><li><div id="_easyui_tree_1067" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">销售目标结果</span></div></li><li><div id="_easyui_tree_1068" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">排行榜查询</span></div></li><li><div id="_easyui_tree_1069" class="tree-node"><span class="tree-indent"></span><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">沉默客户统计</span></div><ul style="display:block"><li><div id="_easyui_tree_1070" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">沉默客户统计（部门）</span></div></li></ul></li><li><div id="_easyui_tree_1071" class="tree-node"><span class="tree-indent"></span><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">流失客户统计</span></div><ul style="display:block"><li><div id="_easyui_tree_1072" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">流失客户统计（部门）</span></div></li></ul></li><li><div id="_easyui_tree_1073" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">流失原因统计</span></div></li><li><div id="_easyui_tree_1074" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">月计划执行结果</span></div></li><li><div id="_easyui_tree_1075" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">资源转化分析</span></div></li><li><div id="_easyui_tree_1076" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">个人客户分布</span></div></li><li><div id="_easyui_tree_1077" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">团队客户分布</span></div></li></ul></li><li><div id="_easyui_tree_1078" class="tree-node"><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">成员角色</span></div><ul style="display:block"><li><div id="_easyui_tree_1079" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">角色编辑</span></div></li><li><div id="_easyui_tree_1080" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">成员编辑</span></div></li></ul></li><li><div id="_easyui_tree_1081" class="tree-node"><span class="tree-hit tree-expanded"></span><span class="tree-icon tree-folder tree-folder-open "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">系统设置</span></div><ul style="display:block"><li><div id="_easyui_tree_1082" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">系统属性</span></div></li><li><div id="_easyui_tree_1083" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">短信模版</span></div></li><li><div id="_easyui_tree_1084" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">系统字段</span></div></li><li><div id="_easyui_tree_1085" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">销售管理</span></div></li><li><div id="_easyui_tree_1086" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">成长等级</span></div></li><li><div id="_easyui_tree_1087" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">挂机短信</span></div></li><li><div id="_easyui_tree_1088" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">通知公告</span></div></li><li><div id="_easyui_tree_1089" class="tree-node"><span class="tree-indent"></span><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">消息设置</span></div></li></ul></li><li><div id="_easyui_tree_1090" class="tree-node"><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox0"></span><span class="tree-title">消息中心</span></div></li><li><div id="_easyui_tree_1091" class="tree-node"><span class="tree-indent"></span><span class="tree-icon tree-file "></span><span class="tree-checkbox tree-checkbox1"></span><span class="tree-title">审核中心</span></div></li></ul>
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
				<a href="javascript:;" class="com-btnb last-step fl_l" style="display:block;"><label>上一步</label></a>
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
        element: '#role-menu2',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>完成了角色名的相关设置后，接下去就需要为角色配置相关的功能权限。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'left'
      },
      {
        element: '#step_next',
        intro: "<p style='font-size:14px;width:165px;line-height:25px;position:absolute;left:15px;top:30px;'>请点击“下一步”，进行配置角色成员操作。</p><img src='${ctx}/static/images/hyx_inform.png' style='margin-left:165px;' />",
        position: 'right'
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
		window.parent.window.location.href = '${ctx}/view/help/novice_help_com/add_role_b.jsp';
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
		step : 2,
		change : true,
		animation : true
	});
    $(".next-step").css("margin-left","120px");
});
</script>
</body>
</html>