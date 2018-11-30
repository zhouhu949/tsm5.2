 <%@ page language="java" import="java.util.*" pageEncoding="utf-8" deferredSyntaxAllowedAsLiteral="true"%>
<%@ include file="/common/headContent.jsp"%>
<script type="text/javascript">
<!--
$(function(){
	//关闭弹出窗口
	$(".pop_cancel").click(function(){
		 closeDialog('popId');
	});
	
	$("#btn_editMember").click(function(){
		editMember();
	});
	
	// 成员共享分组全选
	$("#memberGroupSelect").click(function() { 
		var isSelected = $("#memberGroupSelect").attr("checked")=="checked"?true:false;
		$("input[name='newShareGroupId']").each(function() {
			$(this).attr("checked", isSelected);
		});
	});
});

function editMember(){
	var memberName = $("#memberName").val();
	if(memberName == ''){
		$("#errMsg").html("用户姓名不能为空");
		$("#memberName").focus();
		return false;
	}
	
	var pattern = /[^\x00-\xff]/g;
	if(memberName.replace(pattern, "gg").length > 40){
   	  	$("#errMsg").html("用户姓名不能超过20个字");
   	 	$("#memberName").focus();
   	 	return false;
    }
	
	$("#editMemberForm").ajaxSubmit({
		url : 'memberEdit.do',
		type : 'post',
		success : function(data) {
			alert(data);
		   // 刷新页面
		   closeDialog('popId');
		   loadPage(2);
		}
	});
}
//-->
</script>
<!-- ajax session time out 处理 -->
<div style="height: 450px;overflow-y: auto;overflow-x:hidden;">
 <form  method="post" id="editMemberForm">
 	<input type="hidden" name="memberInfo.userId" value="${memberInfo.userId}">
 	<input type="hidden" name="memberInfo.userAccount" value="${memberInfo.userAccount}">
 	<input type="hidden" name="oldRoleId" value="${memberInfo.roleId}">
 	<input type="hidden" id="oldGroupId" name="oldGroupId" value="${memberInfo.groupId}">
	 <!-- 编辑成员_弹出层  -->
     <div class="pop_layer edit_person">
        <!-- 
         <div class="pop_layer_title">
           <h3>编辑成员</h3>
           <a href=""></a>
       </div>
         -->
        <div class="pop_layer_cont padding_10">
              
               <p class="overflow_hidden">
                  <label class="pop_label_admin">帐号：</label>
                  <span class="select_pop_admin">${memberInfo.userAccount}</span>
               </p>
               <p class="">
                  <label class="pop_label_admin">用户姓名：</label>
                  <span class="select_pop_admin"><input type="text" class="edit_person_admin" id="memberName" name="memberInfo.userName" value="${memberInfo.userName}"/></span>
               </p>
               <p class="">
                  <label class="pop_label_admin">目标分组：</label>
                  <span class="select_pop_admin">
                  	<select id="memberNewGroup" name="memberInfo.groupId" class="select_pop_admin">
       				<c:forEach items="${orgGroups}" var="group" varStatus="vs">
       					<option value="${group.groupId}" <c:if test="${group.groupId eq memberInfo.groupId}">selected</c:if>>${group.groupName}</option>
       				</c:forEach>
       			</select>
                  </span>
               </p>
              <p class="">
                  <label class="pop_label_admin">目标角色：</label>
                  <select  id="memberNewRoleId" name="memberInfo.roleId" class="select_pop_admin">
					<c:forEach items="${roles}" var="v">
						<option value="${v.roleId}" <c:if test="${v.roleId eq memberInfo.roleId}">selected</c:if>>${v.roleName}</option>
					</c:forEach>
				  </select>
                  </span>
               </p> 
               <div class="overflow_hidden">
                  <label class="pop_label_admin">权限：</label>
                  <p class="float_l">
                     <ul class="share_area">
                        <li><span><input type="checkbox" class="checkbox" id="memberGroupSelect" /></span><label>全选</label></li>
                        <li><span></span><label>&nbsp;</label></li>
                     	<c:forEach items="${orgGroups}" var="group" varStatus="vs">
         				    <li>
         				    <span><input type="checkbox" class="checkbox" name="newShareGroupId" value="${group.groupId}" <c:if test="${group.isChecked eq 1}">checked</c:if> /></span>
         				    <label title="${group.groupName}">${group.groupName}</label>
         				    </li>
         				</c:forEach>
                     </ul>
                   </p>
               </div> 
             <div class="overflow_hidden margin_tb20px">
	             <a href="javascript:void(0);" class="pop_submit"  id="btn_editMember"></a>
	             <a href="javascript:void(0);" class="pop_cancel"></a>
             </div>
        </div> 
   </div>
   </div>
<!-- 编辑成员_弹出层  -->
 </form>