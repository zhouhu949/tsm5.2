<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link href="${ctx}/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/worklog/date.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/view/plan/saleWork.js"></script>

<script type="text/javascript">
	var data = ${data};
	var server_date = new Date(${date});
	if(server_date ==null){
		server_date = new Date();
	}
</script>

<style type="text/css">
	table.gridtable {
		font-family: verdana,arial,sans-serif;
		font-size:11px;
		color:#333333;
		border-width: 1px;
		border-color: #666666;
		border-collapse: collapse;
	}
	table.gridtable th {
		border-width: 1px;
		padding: 8px;
		border-style: solid;
		border-color: #666666;
		background-color: #dedede;
	}
	table.gridtable td {
		border-width: 1px;
		padding: 8px;
		border-style: solid;
		border-color: #666666;
		background-color: #ffffff;
	}
</style>

</head>

<body>
	<div class="title" style="font-size:30px"></div>
	<div >
		<table class="gridtable">
			<thead>
				<tr>
					<th>操作</th>
					<th>账号</th>
					<th>姓名</th>
					<th>上线时长</th>
					<th>账号状态</th>
					<th>通话状态</th>
					<th>通话时长</th>
					<th>资源联系情况（已联系/总数）</th>
					<th>意向联系情况（已联系/总数）</th>
				</tr>	
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
   	<%--输入弹出窗口--%>
	<div id="confirm" class="easyui-dialog" title="变更说明" style="width:600px;height:400px;" closed="true" data-options="modal:true">
   		<span>规划变更变更说明</span>
   		<textarea id="reason" style="width:100%;height:80%"></textarea>
   		<a id="confirm_save" href="###" class="easyui-linkbutton" >确认保存</a>
   		<a id="confirm_cancel" href="###" class="easyui-linkbutton" >取消</a>
   	</div>
   	
   	<%--资源弹出窗口--%>
	<div id="resource_window" class="easyui-dialog" title="资源联系情况" style="width:600px;height:400px;" closed="true" data-options="modal:true">
   	</div>
   	
   	<%--意向弹出窗口--%>
	<div id="pupose_window" class="easyui-dialog" title="意向联系情况" style="width:600px;height:400px;" closed="true" data-options="modal:true">
   	</div>

</body>
</html>
