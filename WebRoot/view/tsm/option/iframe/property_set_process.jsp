<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/view/tsm/option/propertyset.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/property_set_pro.css"><!--弹框样式-->
<title>系统设置-系统属性设置-销售进程设计</title>
<script type="text/javascript">
window.onload=function(){

	var height = $(".hyx-spsa").height()+180;
	window.parent.$("#iframepage").css({"height":height+"px"});
};

$(function(){
	$("#add_").on("click",function(e){
		var _this = $(this);
		add_process_(_this.attr("data-itemCode"));
	})
});

//新增
function add_process_(code){
	window.parent.c_add_process(code);
}

//修改
function update_process_(id){
	window.parent.c_update_process(id);
}
</script>
</head>
<body>
<input type="hidden" id="optionLen" value="${optionList}">
<form id="myForm" action="${ctx}/opt/set/propertyset_option.do" method="post">
<div class="hyx-spsa clearfix" >
<input type="hidden" name="itemCode"  id="itemCode" value="${item.itemCode }">

<div class="hyxCustCard_head_title">客户成熟度：</div>
	<div id="hyxCustCard_head">
			<div class="progress_state_start" >
				最低
			</div>
			<div class="progress_state_end" >
				最高
			</div>
		<div class="custCard_head_content">
			<div class="custCard_progress_solid"></div>
			<c:set var="optionlength" value="${fn:length(process)}"></c:set>   <!-- 先获取到optionlist的长度 -->
			<c:set var="optionwidth" value="${90/optionlength }"></c:set>  <!-- 用百分之90来除这个长度获取到每个元素的定位值 -->
			<c:set var="optionwidthhalf" value="${optionwidth/2 }"></c:set>
			<c:if test="${!empty process}">
				<c:forEach items="${process }" var="option" varStatus="v" >
					<c:choose>
					<c:when test="${optionlength  > 7}">
						<c:if test="${v.count  <= 4  ||  v.count>( optionlength - 3 ) }">
							<c:set var="behindtips" value="${9 - (optionlength - v.count) }"></c:set> <!-- 前四后三最后三个下标的处理   用9是因为想要总宽度占比为90% -->
							<c:choose>
								<c:when test="${v.count %2 == 0 }"><!-- 双数项文字在下 单数项文字在上 -->
									<div class="custCard_progress_state progress_state" style="left: ${  10 * (v.count  <= 4 ?  v.count : behindtips )}% " ><!-- 每个元素的left值需要和自身下标相乘 -->
										<div class="progress-icon-state-big">
											<img class="width-100" src="${ctx }/static/images/icon_state_big.png" />
										</div>
										<div class="font-icon-state-big" title="${option.optionName}">${option.optionName}</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="custCard_progress_state progress_state" style="left: ${10 * (v.count  <= 4 ?  v.count : behindtips )}% ;top:-27px;" ><!-- 每个元素的left值需要和自身下标相乘 -->
										<div class="font-icon-state-big" title="${option.optionName}">${option.optionName}</div>
										<div class="progress-icon-state-big">
											<img class="width-100" src="${ctx }/static/images/icon_state_big.png" />
										</div>
									</div>
								</c:otherwise>
							</c:choose>
							<div class="custCard_progress_state progress_state" style="left: 55% ;top:-18px;" ><!-- 每个元素的left值需要和自身下标相乘 -->
								<img class="width-100" src="${ctx }/static/images/icon-state-ellipsis.png" />
							</div>
							<div class="custCard_pointto_icon" style="left:${10 * (v.count  <= 4 ?  v.count : behindtips ) -5 }%">
								<img class="width-100" src="${ctx }/static/images/icon_state_point.png" />
							</div>
							<c:if test="${ v.count == optionlength }">
								<div class="custCard_pointto_icon" style="left:${10 * (v.count  <= 4 ?  v.count : behindtips ) +3 }%">
									<img class="width-100" src="${ctx }/static/images/icon_state_point.png" />
								</div>
							</c:if>
						</c:if>
					</c:when>
					<c:otherwise><!-- 如果销售进程的个数没有超过7个就全部显示 -->
						<c:choose>
							<c:when test="${v.count %2 == 0 }">
								<div class="custCard_progress_state progress_state" style="left: ${optionwidth * v.count}% " ><!-- 每个元素的left值需要和自身下标相乘 -->
									<div class="progress-icon-state-big">
										<img class="width-100" src="${ctx }/static/images/icon_state_big.png" />
									</div>
									<div class="font-icon-state-big" title="${option.optionName}">${option.optionName}</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="custCard_progress_state progress_state" style="left: ${optionwidth * v.count}% ;top:-27px;" ><!-- 每个元素的left值需要和自身下标相乘 -->
									<div class="font-icon-state-big" title="${option.optionName}">${option.optionName}</div>
									<div class="progress-icon-state-big">
										<img class="width-100" src="${ctx }/static/images/icon_state_big.png" />
									</div>
								</div>
							</c:otherwise>
						</c:choose>
						<div class="custCard_pointto_icon" style="left:${optionwidth * v.count - optionwidthhalf }%">
							<img class="width-100" src="${ctx }/static/images/icon_state_point.png" />
						</div>
						<c:if test="${ v.count == optionlength }">
							<div class="custCard_pointto_icon" style="left:${optionwidth * v.count + optionwidthhalf }%">
								<img class="width-100" src="${ctx }/static/images/icon_state_point.png" />
							</div>
						</c:if>
					</c:otherwise>
					</c:choose>
				</c:forEach>
				</c:if>
		</div>
	</div>

	<div class="com-btnlist hyx-sps-btnlist fl_l">
		<%-- <a href="javascript:;" class="com-btna alert_sys_pro_set_b_a fl_l" data-itemCode="${item.itemCode}" onclick="add_process_('${item.itemCode }');"><i class="min-new-add"></i><label class="lab-mar">新&nbsp;&nbsp;&nbsp;&nbsp;增</label></a>
		<a href="javascript:;" id="remove_" class="com-btna alert_sys_pro_set_a_b fl_l"><i class="min-dele"></i><label class="lab-mar">删&nbsp;&nbsp;&nbsp;&nbsp;除</label></a> --%>
		<button type="button" class="button-mid property-add" id="add_" data-itemCode="${item.itemCode}" ><i class="min-new-add"></i>新&nbsp;&nbsp;&nbsp;&nbsp;增</button>
		<button type="button" class="button-mid property-dele" id="remove_"><i class="min-dele"></i>删&nbsp;&nbsp;&nbsp;&nbsp;除</button>
	</div>
	<div class="com-table com-mta test-table fl_l">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll"  class="check" /></span></th>
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
								<td style="width:70px;"><div class="overflow_hidden w70 link"><a href="javascript:;" class="icon-edit alert_sys_pro_set_b_b" onclick="update_process_('${option.optionlistId}')" title="编辑"></a><a href="javascript:;" class="${option.isDefault eq 1?'icon-set-defau':'icon-cancel-defau'}"  title="${option.isDefault eq 1?'取消默认':'设置默认'}"  onclick="set_isdefaul('${option.optionlistId}','${item.itemCode }')" ></a></div></td>
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
	<div class="hyx-sps-pro fl_l">
		<h2 class="com-red">温馨提示：</h2>
		<p style="height: auto;">1、排序值的大小表示客户成熟度的高低，排序值越大成熟度越高。<br/>2、公海批量转为意向，该客户的销售进程为默认值进程，当没有设置默认值时，取排序值最小的销售进程。</p>
	</div>
</div>
</form>
</body>
</html>
