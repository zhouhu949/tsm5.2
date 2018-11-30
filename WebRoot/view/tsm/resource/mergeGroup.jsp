<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	deferredSyntaxAllowedAsLiteral="true"%>
<%@ include file="/common/include.jsp"%>
<head>
	<style>
		.sel_a{line-height: 25px;height: 25px;width: 180px;border:1px solid #e1e1e1;}
	</style>
</head>

<body>
	<form method="post" id="myForm" name="myForm">
		<input type="hidden" id="ctPath" value="${ctx}" />
		<input type="hidden" id="groupId" value="" />
		<p class="sele-hb-grou"><label for="" style="margin:10px 15px 0">选择合并的分组：</label></p>
		<ul class="resource-group-tree first-group-tree" data-url="${ctx}/res/group/get_res_group_json" style="margin: 0 30px;">
			<!-- 部门树 -->
		</ul>
		<div class="cust-impo-res" style="width: auto;padding-left:0;padding-top:0;">
			<div class="com-search clearfix" style="min-height: 40px;width:auto;">
				<label class="fl_l" style="width: auto; line-height: 30px; margin-left: 15px;" for="">合并后分组名称：</label>
				<select class="sel_a" name="newGroup" id="newGroup"></select>
			</div>
		</div>
		<div class="bomb-btn" style="margin-top:60px">
			<label class="bomb-btn-cen"> 
				<a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="javascript:;" id="submitId"><label>确定</label></a> 
				<a class="com-btna bomp-btna cancel-btn fl_l" href="javascript:;" id="clearId"><label>取消</label></a>
			</label>
		</div>
	</form>
<script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
<script type="text/javascript">
	$(function() {
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
				
				$(".first-group-tree").tree({
					data: render_data,
					formatter: function(node){
						var nodeText = "<label class='tree-node-label' data-id='"+node.id+"' data-share-pool='"+node.isSharePool+"'>" + node.text + "</label>";
						return nodeText;
					},
					checkbox: function(node){
						if(node.level == 1){
							return true;
						}
					}
				});
			},
			error: function(status){
				alert("分组加载失败！");
			}
		});
	
		$("#submitId").click(function() {
			/* var s = '';
			$('input[name="checkbox"]:checked').each(function() {
				s += $(this).val() + ',';
			}); */
			
			var first_checkedNodes = $(".first-group-tree").tree("getChecked");
			var checkedNodes_ids = [];
			for(var i=0;i<first_checkedNodes.length;i++){
				var checkedNode = first_checkedNodes[i];
				checkedNodes_ids.push(checkedNode.id);
			}

			if (checkedNodes_ids.length == 0) {
				window.top.iDialogMsg("提示", '请选择要合并分组');
				return;
			} else {
				var checkedNodes_ids_string = checkedNodes_ids.join();
				megGroup(checkedNodes_ids_string);
			}
		});

		$("#clearId").click(function() {
			parent.closePubDivDialogIframe("mergeRes");
		});
		
		$.ajax({
			url: ctx+"/res/group/get_res_group_json",
			type: "get",
			success: function(data){ 
				var appendCode = "";
				for(var i=0;i<data.length;i++){
					var _this_data = data[i];
					var _this_data_child = _this_data.children;
					appendCode += '<optgroup label="'+_this_data.text+'" data-id="'+_this_data.id+'">';
					if(_this_data_child.length > 0){
						for(var j=0;j<_this_data_child.length;j++){
							appendCode += '<option value="'+_this_data_child[j].id+'">'+_this_data_child[j].text+'</option>';
						}
					}
					appendCode += '</optgroup>';
				}
				$("#newGroup").html(appendCode);
			},
			error: function(){
				console.error("加载资源分组失败");
			}
		});
		
	});
	//合并分组
	function megGroup(s) {
		var newGroup = $("#newGroup").val();
		if (!newGroup) {
			window.top.iDialogMsg("提示", '请选择合并后的分组');
		} else {
			$.ajax({
				url : $('#ctPath').val()+'/res/resmanage/mergeGroup?ids=' + s + "&newGroup=" + newGroup,
				type : 'post',
				error : function(XMLHttpRequest, textStatus) {
				},
				success : function(data) {
					var data2 = data.replace(/(\n|\r\n)+/g, "");//回车换行替换为空格
					if (data2 == '0') {
						window.top.iDialogMsg("提示", '合并组成功！');
						setTimeout('parent.document.forms[0].submit()', 1000);
					} else {
						window.top.iDialogMsg("提示", '合并失败！');
						setTimeout(parent.closePubDivDialogIframe("changeGroupId"), 1000);
					}
				}				
			});
		}
	}
	
	function checkRadio(groupId){
		$('#groupId').val(groupId);
	}
</script>
</body>