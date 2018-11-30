<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>通信管理-回拨统计-日统计分析</title>
<style type="text/css">
.com-search label{display:inline-block;width:100px;height:35px;line-height:27px;text-align:right;}
.com-search input{width:140px;height:25px;}
</style>
<script type="text/javascript">
$(function(){
	/*表单优化*/
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
	
});
window.onload=function(){
	var height = $(".hyx-ctr").height()+30;
	window.parent.$("#iframepage").css({"height":height+"px"});
};
</script>

<script type="text/javascript">
$(function(){
	/*时间弹框*/
	$('#d1').dateRangePicker({
		  showShortcuts:false,
		  endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
    }).bind('datepicker-change',function(event,obj){
    	if(obj.date1 != 'Invalid Date'){
    		$('#startDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#endDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    	}
	});
});

</script>
</head>
<body> 
<form action="${ctx }/callBack/stati/callBackStatiDay" method="post">
<div class="hyx-ctr" >
 	<div class="com-search clearfix" style="min-height:30px;">		
		<label class="fl_l" for="">选择时间：</label>
		<span style="position:relative;display:inline-block">
		<input type="hidden" name="startDate" id="startDate" value="${startDate }">
		<input type="hidden" name="endDate" id="endDate" value="${endDate }">
		<input class="diy date-range date_littleicon" type="text"  name="dateValue" value="${dateValue }"  style="width:190px" id="d1"></span>
		<a href="###" class="com-btna fl_l" onclick="document.forms[0].submit();"><i class="min-search"></i><label class="lab-mar">查询</label></a>
	</div>
	<div class="com-table com-mta hyx-cla-table fl_l" style="display:block;margin-top:5px !important;">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">日期</span></th>
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>挂机短信发送数</span><i></i></label></span></th>
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>一天内回拨</span><i></i></label></span></th>
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>一周内回拨</span><i></i></label></span></th>
					<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>一月内回拨</span><i></i></label></span></th>
					<th><span class="lab-d"><span>短信回复数</span><i></i></span></th>
				</tr>
			</thead>
			<tbody>              
				<tr>
					<td><div class="overflow_hidden w120" title="2015-01-01">2015-01-01</div></td>
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