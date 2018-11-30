<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
<!DOCTYPE>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/style.css${_v}" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css${_v}" />
		<style>
			*{font-family:"微软雅黑"!important;line-height:1.6!important;}
			.ql-align-center {
				text-align: center;
			}
		</style>
	</head>
	<body>
		<div id="quill-container">
			<div class="ql-container ql-disabled" style="position: relative;">
				<div class="ql-editor" data-gramm="false" contenteditable="false" data-placeholder="正文">
					<p class="ql-align-center"><strong style="font-size: 21px;">关于6.0版本更新说明</strong></p>
					<p><br></p>
					<div style="width: 600px; margin: 0 auto;">
						<p><strong style="font-size: 14px;">1.</strong> <strong style="font-size: 14px;">小F语音机器人上线</strong></p>
						<p style="text-indent: 28px;"><strong style="font-size: 14px;">（1）</strong> <strong style="font-size: 14px;">我的任务</strong></p>
						<p style="text-indent: 4ch; margin-left: 28px;"><span style="font-size: 14px;">可查看今日任务数据、通话时长分布、通话轮次分布、任务创建与选择；</span></p>
						<p style="text-indent: 28px;"><strong style="font-size: 14px;">（2）</strong> <strong style="font-size: 14px;">小蜂资源库</strong></p>
						<p style="text-indent: 4ch; margin-left: 28px;"><span style="font-size: 14px;">资源的加入、资源的再次拨打、资源的取回、资源意向的取回、资源放入公海；</span></p>
						<p style="text-indent: 28px;"><strong style="font-size: 14px;">（3）</strong> <strong style="font-size: 14px;">话术管理</strong></p>
						<p style="text-indent: 4ch; margin-left: 28px;"><span style="font-size: 14px;">流程话术：设置话术引导流程；</span></p>
						<p style="text-indent: 4ch; margin-left: 28px;"><span style="font-size: 14px;">问题话术：客户问题中关键字的触发，使之有相对应的答案进行回答；</span></p>
						<p style="text-indent: 28px;"><strong style="font-size: 14px;">（4）</strong> <strong style="font-size: 14px;">语音管理</strong></p>
						<p style="text-indent: 4ch; margin-left: 28px;"><span style="font-size: 14px;">可以设置录入个人语音和单位语音，通过语音上传审核成功之后便可使用；</span></p>
						<p style="text-indent: 28px;"><strong style="font-size: 14px;">（5）</strong> <strong style="font-size: 14px;">自主学习</strong></p>
						<p style="text-indent: 4ch; margin-left: 28px;"><span style="font-size: 14px;">通过数据内容小F语音机器人进行自主学习；</span></p>
						<p style="text-indent: 28px;"><strong style="font-size: 14px;">（6）</strong> <strong style="font-size: 14px;">数据统计</strong></p>
						<p style="text-indent: 4ch; margin-left: 28px;"><span style="font-size: 14px;">个人数据统计：</span></p>
						<p style="text-indent: 4ch; margin-left: 28px;"><span style="font-size: 14px;">呼叫分析：通话时长分布、通话轮次分布、接通率、接通占比；</span></p>
						<p style="text-indent: 4ch; margin-left: 28px;"><span style="font-size: 14px;">客户分析：处理客户数、接通率、客户分类、客户组转化分析；</span></p>
						<p style="text-indent: 4ch; margin-left: 28px;"><span style="font-size: 14px;">话术分析：挂断流程排行、热点问题统计、话术执行时长分布、话术轮次分布；</span></p>
						<p style="text-indent: 28px;"><strong style="font-size: 14px;">（7）</strong> <strong style="font-size: 14px;">小组数据统计：</strong></p>
						<p style="text-indent: 4ch; margin-left: 28px;"><span style="font-size: 14px;">个人汇总：呼叫总次数、通话总时长、客户分类总数、平均通话时长、平均通话轮次；</span></p>
						<p style="text-indent: 4ch; margin-left: 28px;"><span style="font-size: 14px;">个人分析：通话时长、通话轮次分布、客户分类；</span></p>
						<p style="text-indent: 28px;"><strong style="font-size: 14px;">（8）</strong> <strong style="font-size: 14px;">通信管理</strong></p>
						<p style="text-indent: 4ch; margin-left: 28px;"><span style="font-size: 14px;">可查看通话记录和挂机短信记录；</span></p>
						<p style="text-indent: 28px;"><strong style="font-size: 14px;">（9）</strong> <strong style="font-size: 14px;">挂机短信</strong></p>
						<p style="text-indent: 4ch; margin-left: 28px;"><span style="font-size: 14px;">对于机器人呼出电话挂机行为，通过选择接听状态和客户分类进行发送短信；</span></p>
						<p><strong style="font-size: 14px;">2.</strong> <strong style="font-size: 14px;">增加自动开场白功能</strong></p>
						<p style="text-indent: 28px;"><span style="font-size: 14px;">可以自定义开场白内容，使用最优秀的话术；</span></p>
						<p style="text-indent: 28px;"><span style="font-size: 14px;">解决销售员重复的开场白语句，解放人力；</span></p>
						<p style="text-indent: 28px;"><span style="font-size: 14px;">可随时打断，人工自动接入；</span></p>
						<p><strong style="font-size: 14px;">3.</strong> <strong style="font-size: 14px;">修改模块菜单项</strong></p>
						<p style="text-indent: 28px;"><span style="font-size: 14px;">原来单独的“合同管理”、“订单审核”模块统一合并在“客户管理”模块内；</span></p>
						<p><strong style="font-size: 14px;">4.</strong> <strong style="font-size: 14px;">增加号码自动筛查</strong></p>
						<p style="text-indent: 28px;"><span style="font-size: 14px;">通过自动筛选功能，可以过滤空号、停机、沉默（经常没使用的号码）的电话号码；</span></p>
						<p><strong style="font-size: 14px;">5.</strong> <strong style="font-size: 14px;">增加密码安全性相关逻辑</strong></p>
						<p style="text-indent: 28px;"><span style="font-size: 14px;">增加密码设置的复杂性，避免密码过于简单；</span></p>
						<p><strong style="font-size: 14px;">6.</strong> <strong style="font-size: 14px;">修复操作日志bug </strong></p>
						<p style="text-indent: 28px;"><span style="font-size: 14px;">修复操作日志bug和已知的bug；</span></p>
						<p><br></p>
						<p><br></p>
					</div>
				</div>
				<div class="ql-clipboard" contenteditable="true" tabindex="-1"></div>
			</div>
		</div>
  	</body>
</html>