<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/view/tsm/option/propertyset.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<title>系统设置-系统属性设置-流失客户原因</title>
<script type="text/javascript">
window.onload=function(){
	var height = $(".hyx-spsa").height()+30;
	window.parent.$("#iframepage").css({"height":height+"px"});
	$("#add_").on("click",function(e){
		add_multi_($(this).attr("data-itemCode"));
	});
};
</script>
</head>
<body>
<form id="myForm" action="${ctx}/opt/set/propertyset_option" method="post">
<input type="hidden" name="itemCode" value="${item.itemCode }">
<div class="hyx-spsa clearfix">
	<div class="com-btnlist hyx-sps-btnlist fl_l">
		<%-- <a href="javascript:;" class="com-btna alert_sys_pro_set_b_a fl_l" onclick="add_multi_('${item.itemCode }');"><i class="min-new-add"></i><label class="lab-mar">新&nbsp;&nbsp;&nbsp;&nbsp;增</label></a>
		<a href="javascript:;" class="com-btna alert_sys_pro_set_a_b fl_l" id="remove_"><i class="min-dele"></i><label class="lab-mar">删&nbsp;&nbsp;&nbsp;&nbsp;除</label></a> --%>
		<button type="button" class="button-mid property-add" data-itemCode="${item.itemCode}" id="add_" <c:if test="${item.page.totalResult >= 50}">disabled</c:if>><i class="min-new-add"></i>新&nbsp;&nbsp;&nbsp;&nbsp;增</button>
		<button type="button" class="button-mid property-dele" id="remove_"><i class="min-dele"></i>删&nbsp;&nbsp;&nbsp;&nbsp;除</button>
	</div>
	<div class="com-table com-mta test-table fl_l">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
					<th><span class="sp sty-borcolor-b">操作</span></th>
					<th><span class="sp sty-borcolor-b">数据项名称</span></th>
					<th><span class="sp sty-borcolor-b">是否默认值</span></th>
					<th>排序值</th>
				</tr>
			</thead>
			<tbody>
				 <c:choose>
				 	<c:when test="${!empty optionList}">
				 		<c:forEach items="${optionList }" var="option" varStatus="v">
				 			<tr class="${v.count%2==0?'sty-bgcolor-b':''}">
								<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="check_box" id="chk_${option.optionlistId}" value="${option.optionlistId}"/></div></td>
								<td style="width:70px;"><div class="overflow_hidden w70 link"><a href="javascript:;" class="icon-edit alert_sys_pro_set_b_b" onclick="update_multi_('${option.optionlistId}')"  title="编辑"></a><a href="javascript:;" class="${option.isDefault eq 1?'icon-set-defau':'icon-cancel-defau'}" onclick="set_isdefaul('${option.optionlistId}','${item.itemCode }')"  title="${option.isDefault eq 1?'取消默认':'设置默认'}" ></a></div></td>
								<td><div class="overflow_hidden w150" title="${option.optionName}">${option.optionName}</div></td>
								<td>
								<c:choose>
									<c:when test="${option.isDefault eq 1}">
										<div class="overflow_hidden w70" title="是">&radic;</div>
									</c:when>
									<c:otherwise>
										<div class="overflow_hidden w70" title="否">&Chi;</div>
									</c:otherwise>
								</c:choose>
								</td>
								<td><div class="overflow_hidden w70" title="${option.sort }">${option.sort }</div></td>
							</tr>
				 		</c:forEach>
				 	</c:when>
				 	<c:otherwise>
				 		<tr><td colspan="5"><b>当前列表无数据！</b></td></tr>
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
