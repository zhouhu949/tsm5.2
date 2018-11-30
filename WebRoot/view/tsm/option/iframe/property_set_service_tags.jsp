<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/view/tsm/option/propertyset.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<title>系统设置-系统属性设置-服务标签</title>
<style>
	.edit-group,.dele-group{display:none;}	
</style>
<script type="text/javascript">
window.onload=function(){
	var height = $("#myForm").height()+30;
	window.parent.$("#iframepage").css({"height":height+"px"});
	$("#add_").on("click",function(e){
		add_tag_($(this).attr("data-itemCode"),$(this).attr("data-itemValue"));
	});
};

//新增
function add_group_(code){
	window.parent.c_add_group(code);
}

//修改
function update_group_(id,code){
	window.parent.c_update_group(id,code);
}

//remove
function remove_group_(id,code){
   $("#default_c").val("");
   $("#groupId_c").val("");
   var data = {groupId:id,itemCode:code};
	window.parent.delete_group_(data);
}

</script>
</head>
<body>
<form id="myForm" action="${ctx}/opt/set/propertyset_option" method="post">
<input type="hidden" name="itemCode" value="${item.itemCode }">
<input type="hidden" id ="groupId_c"  name="groupId_c"  value="${item.groupId }">
<input type="hidden" id ="default_c"  name="default_c"  value="${defaultC }">

<div class="tags-head">
	<div class="tags-group">
		<label>选择分组：</label>
		<select id="group_c">
		     <option value="">--请选择--</option>
			<c:forEach items="${items}" var="group">				
				<option value="${group.groupId }" ${item.groupId eq group.groupId ? 'selected' : ''} data-is-default="${group.isDefault }">${group.groupName}</option>			
			</c:forEach>			
		</select>
		<span>
			<a href="javascript:;" class="add-group" onclick="add_group_('${item.itemCode}')">添加分组</a>
			<a href="javascript:;" class="edit-group" onclick="update_group_('${item.groupId }','${item.itemCode}')"<c:if test="${defaultC==1 }">style="display:inline-block;"</c:if>>编辑分组</a>
			<a href="javascript:;" class="dele-group" onclick="remove_group_('${item.groupId }','${item.itemCode}')" <c:if test="${defaultC==1 }">style="display:inline-block;"</c:if>>删除分组</a>
		</span>
	</div>
</div>
<div class="hyx-spsa clearfix">
	<div class="com-btnlist hyx-sps-btnlist fl_l">
		<%-- <a href="javascript:;" class="com-btna alert_sys_pro_set_b_a fl_l" onclick="add_('${item.itemCode }');"><i class="min-new-add"></i><label class="lab-mar">新&nbsp;&nbsp;&nbsp;&nbsp;增</label></a>
		<a href="javascript:;" class="com-btna alert_sys_pro_set_a_b fl_l" id="remove_"><i class="min-dele"></i><label class="lab-mar">删&nbsp;&nbsp;&nbsp;&nbsp;除</label></a> --%>
		<button type="button" class="button-mid property-add" data-itemCode="${item.itemCode}" data-itemValue="${item.groupId}"  id="add_" ><i class="min-new-add"></i>新&nbsp;&nbsp;&nbsp;&nbsp;增</button>
		<button type="button" class="button-mid property-dele" id="remove_"><i class="min-dele"></i>删&nbsp;&nbsp;&nbsp;&nbsp;除</button>
		<button type="button" class="button-mid property-change" id="change_"><i class="min-edit"></i>变更分组</button>
	</div>
	<div class="com-table com-mta test-table fl_l">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
					<th><span class="sp sty-borcolor-b">操作</span></th>
					<th><span class="sp sty-borcolor-b">标签分组</span></th>
					<th><span class="sp sty-borcolor-b">数据项名称</span></th>
					<th>排序值</th>
				</tr>
			</thead>
			<tbody>
				 <c:choose>
				 	<c:when test="${!empty optionList}">
				 		<c:forEach items="${optionList }" var="option" varStatus="v">
				 			<tr class="${v.count%2==0?'sty-bgcolor-b':''}">
								<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="check_box" id="chk_${option.optionlistId}" value="${option.optionlistId}"/></div></td>
								<td style="width:70px;"><div class="overflow_hidden w70 link"><a href="javascript:;" class="icon-edit alert_sys_pro_set_b_b" onclick="update_tag_('${option.optionlistId}','${option.itemCode }')"  title="编辑" ></a></div></td>
								<td><div class="overflow_hidden w150" title="${option.groupName}">${option.groupName}</div></td>
								<td><div class="overflow_hidden w150" title="${option.optionName}">${option.optionName}</div></td>
								<td><div class="overflow_hidden w70" title="${option.sort }">${option.sort }</div></td>
							</tr>
				 		</c:forEach>
				 	</c:when>
				 	<c:otherwise>
				 		<tr class="no_data_tr"><td><b>当前列表无数据！</b></td></tr>
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
