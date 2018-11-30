<%@ page language="java" pageEncoding="UTF-8"%>
<html>
	<head>
	<%@ include file="/common/include.jsp"%>
	<style>
		.tree-node{width:240px;height:25px;}
	</style>
	<script type="text/javascript">
	$(function(){
		/*左边铺满整屏*/

		
		$("#tt1").tree({
			url:"${ctx}/orgGroup/get_group_json.do",
			onClick:function(node){
				var rel = "${ctx}/res/staffMg/queryStaffResMgList?groupId="+node.id+"&mark=1";
				$("#iframepage").attr("src",rel);
			}
		});
	});
	
	/**
	* 分配资源
	*/
	function toassginResource(staffAccs) {
		pubDivShowIframe('resAssign', ctx +'/res/staffMg/toResAssgin?staffAccs='+staffAccs, '资源分配',580,380);
	}
	
	/**
	* 回收资源
	*/
	function toRecyleResource(staffAccs) {
	   pubDivShowIframe('resRecyle', ctx+'/res/staffMg/toRecyleRes?staffAccs='+staffAccs, '资源回收',430,300);
	}
	</script>
	</head>
	<body> 
    <div class="hyx-dfpzy-reso hyx-layout">
	<div class="hyx-hsm-left hyx-layout-side">
				<p class="memb-edit-title">组织结构</p>
				<ul id="tt1">
					<!-- 部门树 -->		
				</ul>
			</div>
			<div class="hyx-memb-right hyx-layout-content" id="memb_right">
				<iframe src="${ctx}/res/staffMg/queryStaffResMgList" width="100%" height="100%" id="iframepage" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
			</div>
		</div>
	</body>
</html>