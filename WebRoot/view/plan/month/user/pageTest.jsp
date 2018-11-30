<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-历年计划详情查询</title>
<script type="text/javascript" src="${ctx }/static/js/common/page.js${_v}"></script>
<!--本页面样式-->
<script type="text/javascript">
$(function(){
	loadData();
});
function loadData(curr,size){
	var _url="${ctx}/plan/month/user/historyViewJson";
	var _param ={};
	
	$.post(_url,_param,function(data){
		$("#data").text(data.list.toString());
		pa.load("page",data.item.page,loadData);
		//Page();
	});
}


</script>
</head>
<body> 
<div id="data"></div>
<div id="page"></div>
</body>
</html>