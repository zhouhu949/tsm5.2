<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	deferredSyntaxAllowedAsLiteral="true"%>
<%@ include file="/common/include-cut-version.jsp"%>
<!--公共样式-->
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"> --%>
<!--主要样式-->
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/static/js/idialog/skins/iblue.css"> --%>
<script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/gray/easyui.css${_v}"/>
<script type="text/javascript" src="${ctx}/static/thirdparty/easyui/jquery.easyui.min.js${_v}"></script>
<script type="text/javascript">
	$(function() {
		$("#againBtn").click(function() {
			/* var groupId = $('input:radio[name="radio"]:checked').val();
			var groupType =  $('input:radio[name="radio"]:checked').attr('');
			var groupName = $('input:radio[name="radio"]:checked').attr('groupName');
			if (groupId == null || groupId == '') {
				window.top.iDialogMsg("提示", '请先选择资源分组！');
				return;
			} else {
				changeGroup(groupId,groupType,groupName);
			} */
			
			var selectedNode = $(".resource-group-tree").tree("getSelected");
			if(selectedNode){
				var groupId = selectedNode.id;
				var groupType =  "";
				var groupName = selectedNode.text;
				changeGroup(groupId,groupType,groupName);
			}else{
				window.top.iDialogMsg("提示", '请先选择资源分组！');
				return false;
			}
		});

		$("#cancel").click(function() {
			parent.closePubDivDialogIframe("changeGroup");
		});
		
		$.ajax({
			url: $(".resource-group-tree").attr("data-url"),
			type: "get",
			success: function(data){
	//			console.log(data);
				
				var render_data = [{
						"id":"all",
						"text":"所有资源",
						"type":"none",
						"level":"none",
						"isSharePool":"none",
						"inputAcc":"none",
						"children":data,
				}];
				
	//			console.log(render_data);
				
				$(".resource-group-tree").tree({
					data: render_data,
					formatter: function(node){
						var nodeText = "<label class='tree-node-label' data-id='"+node.id+"' data-share-pool='"+node.isSharePool+"' title='"+node.text+"'>" + node.text + "</label>";
						return nodeText;
					},
					onBeforeSelect:function(node){
						if(node.level != 1){
							return false;
						}
					},
					onLoadSuccess: function(node,data){
						var selectedNode = $(".resource-group-tree").tree("find",$("#groupId").val());
						$(selectedNode.target).addClass("tree-node-selected");
						
						$(".tree-node").hover(function(e){
							e.stopPropagation();
							$(this).addClass("tree-node-hover");
						},function(e){
							e.stopPropagation();
							$(this).removeClass("tree-node-hover");
						});
					}
				});
			},
			error: function(status){
				alert("分组加载失败！");
			}
		});
		
	});

	//变更分组
	function changeGroup(groupId,groupType,groupName) {
		var ids = $("#ids").val();
		var module = $("#module").val();
		$.ajax({
			url : '${ctx}/res/resmanage/changeResGroup',
			data:{ids:ids,groupId:groupId,groupType:groupType,groupName:groupName,module:module},
			type : 'post',
			error : function(XMLHttpRequest, textStatus) {
			},
			success : function(data) {
				var data2 = data.replace(/(\n|\r\n)+/g, "");//回车换行替换为空格
				if (data2 == '0') {
					window.top.iDialogMsg("提示", '变更分组成功');
					setTimeout('parent.document.forms[0].submit()', 1000);
				} else {
					window.top.iDialogMsg("提示", '变更失败！');
					setTimeout(parent.closePubDivDialogIframe("changeGroup"), 1000);
				}
			}
		});
	}
</script>
<form method="post" id="myForm1" name="myForm1">
	<input type="hidden" id="ctPath" value="${ctx}" /> 
	<input type="hidden" id="ids" value="${ids}" />
	<input type="hidden" id="module" value="${module}" />
	<p class="sele-hb-grou"><label for="" style="margin:10px 15px 0">选择分配对象：</label></p>
	<%-- <div class="change-group">
		<c:forEach items="${resourceGroupList}" var="mlist" varStatus="vs">
			<c:if test="${mlist.type ne 1 }">
				<p class="clearfix">
					<input class="fl_l" type="radio" name="radio" value="${ mlist.resGroupId}" unGroup="${mlist.type }" groupName="${mlist.groupName }"><span class="fl_l">${ mlist.groupName } </span>
				</p>
			</c:if>
		</c:forEach>
	</div> --%>
	<ul class="resource-group-tree" data-url="${ctx}/res/group/get_res_group_json" style="margin:0 30px;">
		<!-- 部门树 -->
	</ul>
	<div class="bomb-btn">
		<label class="bomb-btn-cen"> 
			<a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="javascript:;" id="againBtn"><label>确定</label></a>
			<a class="com-btna bomp-btna cancel-btn fl_l" href="javascript:;" id="cancel"><label>取消</label></a>
		</label>
	</div>
</form>
