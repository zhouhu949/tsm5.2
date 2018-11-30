<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>通信管理-回拨统计-月统计分析</title>
<style type="text/css">
.com-search label{display:inline-block;width:100px;height:35px;line-height:27px;text-align:right;}
.com-search input{width:140px;height:25px;}
.back-staic{display:inline-block;height:25px;line-height:25px;margin-left:5px;}
.month-stati-time{margin-right:15px;}
</style>
<script type="text/javascript">
window.onload=function(){
	var height = $(".hyx-ctr").height()+30;
	window.parent.$("#iframepage").css({"height":height+"px"});
};
</script>
</head>
<body> 
<form action="${ctx }/callBack/stati/callBackStatiMonth"  method="post">
<div class="hyx-ctr" >
 	<div class="com-search clearfix" style="min-height:30px;">
 	<span class="fl_l" style="position:relative;display:inline-block">	
 	<input type="text" value="" class="sel fl_l" onclick="WdatePicker({minDate:'%y-%M',dateFmt:'yyyy-MM',isShowClear:false,readOnly:true})" style="margin-left:50px;"/></span><span class="back-staic">年月度统计表</span>
 	<a href="###" class="com-btna fl_l" onclick="document.forms[0].submit();"><i class="min-search"></i><label class="lab-mar">查询</label></a>
 	<p class="month-stati-time fl_r"><label>统计时间：</label><span></span></p>
	</div>
	<div class="com-table com-mta hyx-cla-table fl_l" style="display:block;margin-top:5px !important;">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">月份</span></th>
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>挂机短信发送数</span><i></i></label></span></th>
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>一天内回拨</span><i></i></label></span></th>
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>一周内回拨</span><i></i></label></span></th>
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>一月内回拨</span><i></i></label></span></th>
					<th><span class="lab-d"><span>短信回复数</span><i></i></span></th>
				</tr>
			</thead>
			<tbody>              
				<tr>
					<td><div class="overflow_hidden w120" title="12">12</div></td>
					<td><div class="overflow_hidden w120" title=""></div></td>
					<td><div class="overflow_hidden w110" title="12">12</div></td>
					<td><div class="overflow_hidden w220" title="2">2</div></td>
					<td><div class="overflow_hidden w220" title="2">2</div></td>
					<td><div class="overflow_hidden w120" title="4">4</div></td>
				</tr>				
			</tbody>
		</table>
	</div>
	<div class="com-bot" >
		<div class="com-page fl_r">
			${item.page.pageStr}
		</div>
	</div>
</div>
</form>
</body>
</html>