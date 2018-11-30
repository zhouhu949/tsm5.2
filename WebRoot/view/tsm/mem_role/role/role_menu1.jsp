<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
  <form id="myForm_1" method="post">
  	<input type="hidden" id="curStep1" name="curStep1" value="1"> <!-- 触发步骤 -->
    <input type="hidden" id="old_roleName" value="${roleName}">
    <input type="hidden" id="old_check" value="0">
    <p class="clearfix"><label class="fl_l" for="">输入角色名：</label><input class="inpu-role fl_l" id="roleName" name="roleName" type="text" value="${roleName}" maxlength="20" onblur="chekRolename_()"></p>
	<p class="manag-role"><input type="radio" name="roleType" value="0"  ${roleType ne 1?'checked':''}><span>销售</span><input type="radio" name="roleType" value="1" ${roleType eq 1?'checked':''}><span>管理者</span></p>
  </form>
  <script type="text/javascript">
  		// 验证角色唯一
  		function chekRolename_(){
  			var isPass = true;
  			var oldVal  = $("#old_roleName").val();
  			var chkVal = $("#roleName").val();
  			if(chkVal != "" && chkVal != null){
  				if(oldVal!='' && oldVal != null && chkVal==oldVal){	// 未作修改不请求验证
  					return isPass;
  				}
  				$.ajax({
  					url : '${ctx}/auth/role/check_unitRoleName.do',
  					data: {'roleName':chkVal},
  					type : 'post',
  					async:false,
  					error : function(XMLHttpRequest, textStatus){window.top.iDialogMsg("提示","请求失败！");},
  					success : function(data) {
  						if(data != 0 ){
  							isPass = false;
  						}
  					}
  				});	
  			}
  			if(!isPass){
  				window.top.iDialogMsg("提示","该角色名已存在！")
  				$("#old_check").val(1);
  			}else{
  				$("#old_check").val(0);
  			}
  		}
  </script>
