<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript">
<!--
$(function(){
	$("#btn_save").click(function(){
		saveData(); 
	});
	
	$("#cancel").click(function(){
	   closeDialog();
	});
	
});

function saveData(){
	var groupName = $("#groupName").val();
	var groupType = $("#groupType").val();
	var oldGroupName =  $("#oldGroupName").val();
	var oldGroupType = $("#oldGroupType").val();
	if(groupName == ''){
		$("#errMsg").html("分组名称不能为空");
		$("#groupName").focus();
		return false;
	}
	
	if(groupName == oldGroupName && groupType ==oldGroupType){ // 未修改
		closeDialog();
		return false;
	}
	
	var pattern = /[^\x00-\xff]/g;
	if(groupName.replace(pattern, "gg").length > 20){
   	  	$("#errMsg").html("分组名称不能超过十个字");
   	 	$("#groupName").focus();
   	 	return false;
    }
	
	$("#myForm").ajaxSubmit({
		url : '${ctx}/auth/user/addOrEditTeamGroup.do',
		type : 'post',
		error : function(XMLHttpRequest, textStatus){},
		success : function(data) {
			if(data == -1){
				$("#errMsg").html("名称已存在,请更换分组名称");
			}else{
			   // 刷新主页面
			   loadPage(2);
			}
		}
	});
}
-->
</script>
<!-- ajax session time out 处理 -->
		<form method="post" id="myForm" name="myForm">
		<input type="hidden" name="groupId" value="${orgGroup.groupId}" />
		<input type="hidden" id="oldGroupName" value="${orgGroup.groupName}" />
		<input type="hidden" id="oldGroupType" value="${orgGroup.groupType}" />
       <div class="pop_layer edit_person">
          <div class="pop_layer_cont padding_10">
              <p>
                    <label class="pop_label_admin">分组名称：</label>
                    <span class="select_pop_admin"><input type="text" maxlength="20" class="edit_person_admin" id="groupName" name="groupName" value="${orgGroup.groupName}"/></span>
              </p>
              <p>
                    <label class="pop_label_admin">分组类型：</label>
                    <span class="select_pop_admin">
                    	<select class="edit_person_admin" id="groupType" name="groupType">
								<option value="1" ${orgGroup.groupType eq 1 ? 'selected':''} >营销</option>                    	
								<option value="2" ${orgGroup.groupType eq 2 ? 'selected':''}>服务</option>                    	
                    	</select>
                    </span>
              </p>
              <p>
                    <label class="pop_label_admin">&nbsp;</label>
                    <span class="select_pop_admin" id="errMsg" style="color: red;"></span>
              </p>
             <div class="overflow_hidden margin_tb20px">
             	<a class="pop_submit" id="btn_save" href="javascript:void(0);" ></a>
             	<a class="pop_cancel" id="cancel" href="javascript:void(0);"></a>
             </div>
          </div> 
       </div>
	   </form>
