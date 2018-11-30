<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<title>系统设置-系统属性设置-销售产品维护</title>

<script type="text/javascript">
$(function(){
	// 新增
	$('#add_').click(function(){
		window.parent.a_add();
	});

	// 删除
	$('#remove_').click(function(){
		var ids = "";
		$("input[name='check_box']:checked").each(function(){
			ids += $(this).val() + ",";
		});
		if(ids == ""){
			window.top.iDialogMsg("提示","请选择！");
			return false;
		}
		var data = {ids:ids};
		window.parent.delete_(data,"pro");
	});

});

// 修改
function update_(id){
	window.parent.a_update(id);
}

// 设置默认
function set_isdefaul(id){
	$.post(ctx+'/opt/set/proSetDefault.do',{'id':id},function(data){
		if(data == 0){
			window.top.iDialogMsg("提示","修改成功！");
			setTimeout('document.forms[0].submit();',1000);
		}else{
			window.top.iDialogMsg("提示","修改失败！");
			setTimeout('document.forms[0].submit();',1000);
		}
	},'json');
}
</script>
<script type="text/javascript">
window.onload=function(){
	var height = $(".hyx-spsa").height()+30;
	window.parent.$("#iframepage").css({"height":height+"px"});
};
</script>
</head>
<body>
<form id="myForm" action="${ctx}/opt/set/propertyset_pro" method="post">
<div class="hyx-spsa clearfix">
	<div class="com-btnlist hyx-sps-btnlist fl_l">
		<%-- <a href="javascript:;" class="com-btna alert_sys_pro_set_a_a fl_l" id="add_" data-total="${item.page.totalResult}"><i class="min-new-add"></i><label class="lab-mar">新&nbsp;&nbsp;&nbsp;&nbsp;增</label></a> --%>
		<%-- <a href="javascript:;" class="com-btna alert_sys_pro_set_a_b" id="remove_"><i class="min-dele"></i><label class="lab-mar">删&nbsp;&nbsp;&nbsp;&nbsp;除</label></a> --%>
		<button type="button" class="button-mid property-add" id="add_" ><i class="min-new-add"></i>新&nbsp;&nbsp;&nbsp;&nbsp;增</button>
		<button type="button" class="button-mid property-dele" id="remove_"><i class="min-dele"></i>删&nbsp;&nbsp;&nbsp;&nbsp;除</button>
	</div>
	<div class="com-table com-mta test-table fl_l">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" class="check"  id="checkAll"/></span></th>
					<th><span class="sp sty-borcolor-b">操作</span></th>
					<th><span class="sp sty-borcolor-b">产品名称</span></th>
					<th><span class="sp sty-borcolor-b">产品型号</span></th>
					<th><span class="sp sty-borcolor-b">产品规格</th>
					<th><span class="sp sty-borcolor-b">标准价格</span></th>
					<th><span class="sp sty-borcolor-b">计量单位</span></th>
					<th><span class="sp sty-borcolor-b">是否默认值</span></th>
					<th>排序值</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty pros }">
						<c:forEach items="${pros}" var="pro" varStatus="v">
							<tr class="${v.count%2==0?'sty-bgcolor-b':''}">
								<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="check_box" id="chk_${pro.id}" value="${pro.id}"/></div></td>
								<td style="width:70px;"><div class="overflow_hidden w70 link"><a href="javascript:;" class="icon-edit alert_sys_pro_set_a_c" title="编辑" onclick="update_('${pro.id }')"></a><a href="javascript:;" class="${pro.isDefault eq 1?'icon-set-defau':'icon-cancel-defau'}" onclick="set_isdefaul('${pro.id }')" title="${pro.isDefault eq 1?'取消默认':'设置默认'}"></a></div></td>
								<td><div class="overflow_hidden w120" title="${pro.name }">${pro.name }</div></td>
								<td><div class="overflow_hidden w160" title="${pro.model }">${pro.model }</div></td>
								<td><div class="overflow_hidden w120" title="${pro.type }">${pro.type }</div></td>
								<td><div class="overflow_hidden w70" title="<fmt:formatNumber value='${pro.price}' pattern='#,##0.00#'/>"><fmt:formatNumber value='${pro.price}' pattern='#,##0.00#'/></div></td>
								<td><div class="overflow_hidden w70" >${pro.units}</div></td>
								<td>
								<c:choose>
									<c:when test="${pro.isDefault eq 1}">
										<div class="overflow_hidden w70" title="是">&radic;</div>
									</c:when>
									<c:otherwise>
										<div class="overflow_hidden w70" title="否">&Chi;</div>
									</c:otherwise>
								</c:choose>
								</td>
								<td><div class="overflow_hidden w70" >${pro.sort}</div></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
							<tr><td colspan="9"><b>当前列表无数据！</b></td></tr>
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
<script type="text/x-handlebars-template" id="template">
  {{#each list}}
	<tr class="{{even_odd @index}}">
          <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" name="check_box" value="{{id}}"/></div></td>
          <td style="width:70px;">
			  <div class="overflow_hidden w70 link">
				  <a href="javascript:;" class="icon-edit alert_sys_pro_set_a_c" title="编辑" onclick="update_('{{id }}')"></a>
				  <a href="javascript:;" class="{{compare isDefault '==' 1}} 'icon-set-defau' {{else}}'icon-cancel-defau' {{/compare}}" onclick="set_isdefaul('{{id }}')" title="{{compare isDefault '==' 1}} '取消默认' {{else}} '设置默认' {{/compare}}"></a>
			  </div>
		  </td>
          <td><div class="overflow_hidden w120" title="{{name }}">{{name }}</div></td>
          <td><div class="overflow_hidden w160" title="{{model }}">{{model }}</div></td>
          <td><div class="overflow_hidden w120" title="{{type }}">{{type }}</div></td>
          <td><div class="overflow_hidden w70" title="{{formatNumber price 2}}>{{formatNumber price 2}}</div></td>
		  <td><div class="overflow_hidden w70" >{{units}}</div></td>
          <td>
			  {{compare isDefault '==' 1}}
                 <div class="overflow_hidden w70" title="是">&radic;</div>
			  {{else}}
                  <div class="overflow_hidden w70" title="否">&Chi;</div>
			  {{/compare}}
          </td>
          <td><div class="overflow_hidden w70" >{{sort}}</div></td>
	</tr>
 {{/each}}
</script>
</body>
</html>
