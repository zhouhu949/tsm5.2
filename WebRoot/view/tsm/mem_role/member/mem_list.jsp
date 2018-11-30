<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/roleOrgmem/mem_list.css"><!--主要样式-->
<script type="text/javascript">
$(function(){
	var isAdmin = $("#isAdmin").val();
	var url = "${ctx}/orgGroup/get_group_json.do";
	if(isAdmin == "1"){
		url = "${ctx}/orgGroup/get_all_group_json.do";
	}
	$("#tt1").tree({
		url:url,
		formatter : function(node) {
			var nodeText = "<label>" + node.text + "</label>";
			if (isAdmin == "1" || node.attributes != null) { // 权限内的部门
				if (node.text != '未分组') {
					nodeText += "<a href=\"javascript:;\" class=\"min-edit\" id=\"treeEdit_" + node.id + "\" title=\"编辑\"></a>";
					nodeText += "<a href=\"javascript:;\" class=\"min-dele\" id=\"treeDelete_" + node.id + "\" title=\"删除\"></a>";
				}
			}
			return nodeText;
		},
		onClick:function(node){
			var rel = "${ctx}/auth/user/orgMemberRightList.do?groupId="+node.id+"&mark=1&isAdmin="+isAdmin;
			$("#iframepage").attr("src",rel);
		},
		onLoadSuccess:function(node,data){
			$(".add-depart").click(function(e){
				e.stopPropagation();// 阻止冒泡
				pubDivShowIframe('dept_edit_',"${ctx}/auth/user/addOrEditDept",'编辑部门',480,320);
			});

			$("a[id^=treeEdit_]").click(function(event) {
				event.stopPropagation();// 阻止冒泡
				var groupId = $(this).attr("id").split("_")[1];
				pubDivShowIframe('dept_edit_',"${ctx}/auth/user/addOrEditDept?groupId="+groupId,'编辑部门',480,320);
			});

			$("a[id^=treeDelete_]").click(function(event) {
				event.stopPropagation();// 阻止冒泡
				var groupId = $(this).attr("id").split("_")[1];
				queDivDialog("res_remove_cust","删除部门将会同时删除该部门下<br />所属子部门,是否确认删除？",function(){
					$.ajax({
						url : ctx + "/auth/user/selectDept",
						type : 'POST',
						data : {
							'groupId' : groupId
						},
						error : function() {
							window.top.iDialogMsg("提示","网络异常，请稍后尝试！");
						},
						success : function(data) {
							if (data.result == 0) {
								$.ajax({
									url : ctx + "/auth/user/deldept",
									type : 'POST',
									data : {
										'groupId' : groupId
									},
									dataType : 'html',
									error : function() {
										window.top.iDialogMsg("提示","网络异常，请稍后尝试！");
									},
									success : function(data) {
										if (data == 0) {
											window.top.iDialogMsg("提示","删除部门成功！");
											setTimeout('window.top.addTab(ctx+"/auth/user/orgMemberList.do","成员编辑");',2000);
										} else {
											window.top.iDialogMsg("提示","删除部门失败！");
										}
									}
								});
							} else if(data.result == -1 || data.result == -2) {
									window.top.iDialogMsg("提示","部门或子部门下面有帐号，不能删除！");
							}else if(data.result == -3){ 
							
								window.top.memWarmDivDialog("部门删除提示",
										"删除该部门，会造成该部门及其子部门导入的资源丢失，请点击确定将此部门及及其子部门的资源变更资源录入部门后，再进行部门删除！",
										function(){pubDivShowIframe('dept_edit_resourse',ctx+"/auth/user/toChangeResImportDept?haveResDeptIds="+data.haveResDeptIds+"&&groupId="+data.groupId,'变更资源录入部门',480,320);},
										function(){});
							}
						}
					});
				});
			});
		}
	});

	/*左边铺满整屏*/
	var winhight=$(window).height()-30;
	$(".memb-edit-tree").height(winhight);
	$("#memb_right").height(winhight-20);
	
});
function ajaxAppendNode(id,name,currId){
	var isAdmin = $("#isAdmin").val();
	var parnode = $("#tt1").tree("find",id);
	if(parnode == null){
		parnode= $("#tt1").tree("getRoot");
	}
	$("#tt1").tree("append",{
		parent:parnode.target,
		data:[{
			id:currId,
			text:name
		}]
	});
	$("#tt1").tree({
		formatter : function(node) {
			var nodeText = "<label>" + node.text + "</label>";
			if (isAdmin == "1" || node.attributes != null) { // 权限内的部门
				if (node.text != '未分组') {
					nodeText += "<a href=\"javascript:;\" class=\"min-edit\" id=\"treeEdit_" + node.id + "\" title=\"编辑\"></a>";
					nodeText += "<a href=\"javascript:;\" class=\"min-dele\" id=\"treeDelete_" + node.id + "\" title=\"删除\"></a>";
				}
			}
			return nodeText;
		}
	});
}
function changeTreeName(currId,newName){
	var  node = $("#tt1").tree("find",currId);
	$(node.target).find(".tree-title>label").text(newName);
}
function selectItem(currId){
	var  node = $("#tt1").tree("find",currId);
	$("#tt1").tree("select",node.target);
	var rel = "${ctx}/auth/user/orgMemberRightList.do?groupId="+node.id+"&mark=1&isAdmin="+ $("#isAdmin").val();
	$("#iframepage").attr("src",rel);
}
/** 成员编辑 */
function openMemberEdit(userId){
	pubDivShowIframe('mem_edit_',ctx+'/auth/user/toMemberEdit.do?userId='+userId,'成员编辑',590,520);
}
/** 密码修改*/
function openMemberPassEdit(memAcc){
	pubDivShowIframe('mem_b_',ctx+'/auth/user/toMemberPwdEdit.do?memberAcc='+memAcc,'密码修改',420,200);
}

/** 角色编辑 */
function openMemberRoleEdit(userIds,userCounts){
	pubDivShowIframe('mem_a_',ctx+'/auth/user/toMemberRoleEdit.do?userIds='+userIds+'&accounts='+userCounts,'角色编辑',350,250);
}

/** 变更部门 */
function openMemberDeptEdit(userIds,userCounts){
	pubDivShowIframe('dept_change_',ctx+'/auth/user/toChangeDept.do?ids='+userIds+'&accounts='+userCounts,'变更部门',450,350);
}
/** 管辖权限 */
function openMemberPowerBatchEdit(userIds,userAccounts){
	pubDivShowIframe('batch_edit_power_',ctx+"/auth/user/toEditShareGroup?userIds="+userIds+"&userAccounts="+userAccounts,'管辖权限',300,270);
}
/* 重载表格方法 */
function reloadTable(){
	window.frames.iframepage.loadData();
}
</script>
</head>
<body>
<input type="hidden" id="isAdmin" value="${isAdmin}">

<div class="hyx-hsm-borall hyx-layout">
	<div class="memb-edit-tree hyx-layout-side">
		<p class="memb-edit-title">组织结构</p>
		<div class="add-depart">+添加部门</div>
		<ul id="tt1" >
			<!-- 部门树 -->
		</ul>
	</div>
<div class="hyx-memb-right hyx-layout-content" id="memb_right" >
	<iframe src="${ctx}/auth/user/orgMemberRightList?isAdmin=${isAdmin}" width="100%" height="100%" id="iframepage" frameborder="0"  marginheight="0" marginwidth="0"  scrolling="auto" style="overflow-y: auto;" ></iframe>
</div>
</div>

</body>
</html>
