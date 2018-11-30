<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css"><!--主要样式-->
	<style media="screen">
		.manage-drop .sure-cancle label{height: auto;line-height: 18px;text-align: center;width: auto;;}
		.com-resources{padding-top: 10px;position: relative;z-index: 10;margin: 0 auto;font-size: 12px;}
		.tree li div {height: 18px;line-height: 18px;}
		.tree-hit{margin-top: 0;}
		.tree-title{height: 18px!important;line-height: 18px!important;}
		.change-resources-reminder{margin:20px auto;}
	</style>
<form id="mem_resourse_form" method="post">
	<input type="hidden"  name="groupId" value="${groupId}" />
  <div class="change-resources">
    <div class="com-resources">
      <div class="change-resources-linebox clearfix">
        <label class="fl_l">变更前资源录入部门：</label>
        <input class="sub-dep-inpu12" type="text" id="accNames" style="border: 1px solid #d6d6d6;" readonly>
			  <input type="hidden" id="accs" name="newDeptIds" value="${haveResDeptIds}" />
				<div class="manage-drop fl_r" id="manage_drop" style="width:200px;margin-right:60px;">
					<ul id="tt1" class="role-jujurisdiction-treeul">

					</ul>
					<div class="sure-cancle clearfix" style="width: 120px">
						<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l"
											href="javascript:;" id="checkedId"><label>确定</label></a>
						<a	class="com-btnd bomp-btna fl_l reso-sub-clear" id="clearId"
											href="javascript:;"><label>清空</label></a>
					</div>
		     </div>
      </div>
      <div class="change-resources-linebox clearfix">
        <label class="fl_l">变更后资源录入部门：</label>
        <div class="tree-box bomo-tree fl_l">
					<div class="st_tree">
						<ul>
							<c:forEach var="dept" items="${deptList }">
								<li class="${!empty dept.children ? 'expand':''}">
									<input type="radio" name="deptId" class="check" value="${dept.id }"   <c:if test="${haveResDeptIdss eq dept.id }">disabled</c:if>  />
									<c:if test="${!empty dept.children }">
										<span class="triangle-down icon_down_px"></span>
									</c:if>
									<a href="javascript:;"  class="shows-changeText">${dept.text }</a>
								</li>
								<c:if test="${!empty dept.children }">
									<c:set var="deptList" value="${dept.children }" scope="request"></c:set>
									<c:import url="/view/tsm/mem_role/member/change_resources_org_tree.jsp"></c:import>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
      </div>
      <div class="change-resources-reminder">
        温馨提示：成功变更资源录入部门后，将直接删除部门及其子部门。
      </div>
    </div>
    <div class="sure-cancle clearfix neworg-surbtn">
      <a href="javascript:;" class="com-btna bomp-btna com-btna-sty fl_l" onclick="submitForm();"><label>确定</label></a>
      <a href="javascript:;" class="com-btna bomp-btna fl_l" id='close02' onclick="close02();"><label>取消</label></a>
    </div>
  </div>
</form>
<script type="text/javascript">
	$(function(){
		$(".expand").click(function(e){
			e.stopPropagation();
			$showme=$(this).nextAll("ul:first");
			$showme.slideToggle();
		});
		$(".st_tree li").click(function(e){
			e.stopPropagation();
			var $check = $(this).find(".check");
			if($check.attr("disabled")){
				return false;
			}
			$check.attr("checked","true");
		});

		$("#tt1").tree({
			url:ctx+"/orgGroup/get_all_group_json",
			checkbox:true,
			onLoadSuccess:function(node,data){
				var oas = $("#accs").val();
				if(oas != null && oas != ''){
					var text='';
					$.each(oas.split(','),function(index,obj){
						var n = $("#tt1").tree("find",obj);
						if(n != null){
							text+=n.text+',';
							$("#tt1").tree("check",n.target).tree("expandTo",n.target);
						}
					});
					if(text != ''){
						text=text.substring(0,text.length-1);
						$("#accNames").val(text);
					}else{
						$("#accNames").val("帐号");
					}
				}
			}
		});

		$("#checkedId").click(function(){
			var nodes =$("#tt1").tree("getChecked");
			var accs = "";
			var text='';
			$(nodes).each(function(index,obj){
					accs+=","+obj.id;
					text+=obj.text+',';
			});
			if(accs.length > 0){
				accs=accs.substring(1,accs.length);
				text=text.substring(0,text.length-1);
				$("#accs").val(accs);
				$("#accNames").val(text);
			}
			$("#manage_drop").hide();
		});
		$("#manage_drop").click(function(e){
			e.stopPropagation();
		});
		$(document).click(function(){
			$("#manage_drop").hide();
		})
		//清空
		$('#clearId').click(function(){
			var nodes = $('#tt1').tree('getChecked');
			 $(nodes).each(function(index,obj){
	        	$('#tt1').tree('uncheck', obj.target);
	        });
	        $('#accs').val('');
	        $("#accNames").val('');
		});
		$("#checkedId").click();//初始化直接将树中的勾选值填到input中


	});

	function submitForm(){
		if($("input[name=deptId]:checked").length == 0){
			window.top.iDialogMsg("提示","请选择变更后资源录入部门！");
			return false;
		}
		$('#mem_resourse_form').ajaxSubmit({
			url : ctx+'/auth/user/changeResImportDept',
			type : 'post',
			error : function(XMLHttpRequest, textStatus,data){
				alert("请求失败！");
				},
			success : function(data) {
				if(data.status == true){
					window.top.iDialogMsg("提示","操作成功！");
					setTimeout(function(){
						 window.top.addTab(ctx+"/auth/user/orgMemberList.do","成员编辑"); 
						close02();
					},1000)
				}else{
					window.top.iDialogMsg("提示",data.errorMsg);
				}
			}
		});
}



	// 取消
	function close02(){
		closeParentPubDivDialogIframe('dept_edit_resourse');
	}
</script>
