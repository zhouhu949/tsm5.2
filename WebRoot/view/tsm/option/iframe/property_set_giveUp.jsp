<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>系统设置-系统属性设置-客户放弃原因</title>
<script type="text/javascript">
$(function(){

  	//应用子功能项开关
	$('input[name="subBtn"]').change(function(){
		var val = $(this).val().split("_")[1];
		var id = $(this).val().split("_")[0];
		var url = ctx+"/opt/set/updateSubOpt";
		$.ajax({
			url:url,
			type:'post',
			data:{val:val,id:id},
			dataType:'json',
			error:function(){},
			success:function(data){
				if(data == '0'){
					window.top.iDialogMsg("提示","设置成功!");
				}else{
					window.top.iDialogMsg("提示","设置失败!");
				}
			}
		});
	});
});

//修改
function update_(id){
	window.parent.d_update(id);
}
window.onload=function(){
	var height = $(".hyx-spsa").height()+30;
	window.parent.$("#iframepage").css({"height":height+"px"});
};
</script>
</head>
<body> 
<form id="myForm" action="${ctx}/opt/set/propertyset_giveUp" method="post"> 
<div class="hyx-spsa clearfix" >
	<div class="hyx-sps-radio">
		<input type="radio" class="ipt" id="btn_on" name="subBtn"  value="${dic.dictionaryId}_1" ${dic.dictionaryValue eq '1'?'checked':''}/><label class="lab">开启</label>
		<input type="radio" class="ipt" id="btn_off" name="subBtn" value="${dic.dictionaryId}_0" ${dic.dictionaryValue eq '0'?'checked':''} /><label class="lab">关闭</label>
		<span class="sp red_a">（开启：对非意向客户进行细化分类，可清晰了解【放弃客户】原因。）</span>
	</div>
	<div class="com-table com-mta test-table fl_l">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">操作</span></th>
					<th><span class="sp sty-borcolor-b">数据项名称</span></th>
					<th>子项名称</th>
				</tr>
			</thead>
			<tbody>  
			<c:choose>
				 	<c:when test="${!empty optionList}">
				 		<c:forEach items="${optionList }" var="option" varStatus="v">
				 			<tr class="${v.count%2==0?'sty-bgcolor-b':''}">
								<td style="width:30px;"><div class="overflow_hidden w30 link"><a href="javascript:;" class="icon-edit  alert_sys_pro_set_d_a" title="编辑" onclick="update_('${option.optionlistId}')"></a></div></td>
								<td><div class="overflow_hidden w150" title="${option.optionName}">${option.optionName}（<span class="hyx-sps-red">系统默认</span>）</div></td>
								<td><div class="overflow_hidden w220" title="${option.subNameList}">${option.subNameList}</div></td>
							</tr>
				 		</c:forEach>
				 	</c:when>
				 	<c:otherwise>
				 		<tr><td colspan="3"><b>当前列表无数据！</b></td></tr>
				 	</c:otherwise>
			</c:choose>            	
			</tbody>
		</table>
	</div>
	<div class="com-bot">
		<div class="com-page fl_r">
			${item.page.pageStr}
		</div>
	</div>
</div>
</form>
</body>
</html>