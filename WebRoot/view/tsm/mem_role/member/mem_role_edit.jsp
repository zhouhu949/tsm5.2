<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css"><!--主要样式-->
  <form id="myForm_role_edit" method="post">
  			<input type="hidden" name="userIds" value="${userIds}">
  			<input type="hidden" name="roleId" id="roleId" >
  			<input type="hidden" name="accounts" value="${accounts}" >
			<div class="cust-impo-res" style="width:auto;padding-left:0;">
			<div class="com-search clearfix mem-role-edit-search">
					<label class="fl_l" for="">目标角色：</label>
					<dl class="select">
						<dt>-请选择-</dt>
						<dd>
							<ul>
								<c:forEach items="${roles }" var="role">
									<li><a href="###" onclick="selectRole('${role.roleId}')" >${role.roleName }</a></li>
								</c:forEach>
							</ul>
						</dd>
					</dl>
				</div>
				<p class="reminder" style="text-indent: 20px;"><span class="com-red">温馨提示：</span>变更角色同时需要注意管理管辖权限</p>
				</div>
				
				<div class="bomb-btn" style="margin-top:70px">
						<label class="bomb-btn-cen">
							<a class="com-btna bomp-btna com-btna-sty fl_l" onclick="submitForm();" href="javascript:;"><label>确定</label></a>
							<a class="com-btna bomp-btna fl_l" id="close02"  onclick="close02();" href="javascript:;"><label>取消</label></a>
						</label>
					</div>
  </form>
  <script type="text/javascript">

  	/** 选择角色 */
  	function selectRole(roleId){
  		$("#roleId").val(roleId);
  	}

  	// 保存
  	function submitForm(){
  		var $roleId = $("#roleId").val();
  		if($roleId != null && $roleId != ""){
  			$('#myForm_role_edit').ajaxSubmit({
  				url : '${ctx}/auth/user/editRoleBatch.do',
  				type : 'post',
  				error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
  				success : function(data) {
  					if(data == 0){
  						window.top.iDialogMsg("提示","保存成功！");
  						setTimeout('window.parent.frames[0].loadData();',1000);
  						setTimeout('close02();',1000);
  					}else{
  						window.top.iDialogMsg("提示","保存失败！");
  					}
  				}
  			});
  		}
	}

  	// 取消
  	function close02(){
  		closeParentPubDivDialogIframe('mem_a_');
  	}

  </script>
