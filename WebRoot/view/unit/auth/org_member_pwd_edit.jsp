<%@ page pageEncoding="UTF-8" import="com.qftx.base.auth.bean.User"%>
<%@ include file="/common/include.jsp"%>
<div class="layer_res edit_layer_res">
  <div class="layer_res_cont padding_10">
       <form id="editMemberForm" name="editMemberForm" method="post">
       	  <input type="hidden" name="account" value="${memberAcc}"/>
          <p class="tool_layer_res">
              <label>帐号：</label><span class="accunt_numb">${memberAcc}</span>
          </p>
          <p class="tool_layer_res">
              <label>新密码：</label>
              <input id="pwd" name="pwd" type="text" class="text_layer_res"/>
              <span id="errmsg_pwd" class="must"></span>
          </p>
          <p class="tool_layer_res">
          	<label></label>
          	<span>密码格式：6-12位英文字符与数字，区分大小写</span>
          </p>
          <p class="tool_layer_res padding110">
	          <a href="javascript:void(0);" id="btn_edit" class="save_layer_res"></a>
	          <a href="javascript:void(0);" id="btn_close" class="cancel_layer_res"></a>
          </p>
       </form>
  </div>  
</div>
<script type="text/javascript">
<!--
$(function(){
	// 密码
	$("#pwd").blur(function(){
		chkNewPwd('pwd');
	});
	
	// 保存
	$("#btn_edit").click(function(){
		var isSubmited = false;
		if(!isSubmited && chkNewPwd('pwd')){
			submitForm();
			isSubmited =true;
		}
	});
	
	// 取消
	$("#btn_close").click(function(){
		closeDialog('popId');
	});
	
});

// 验证密码
function chkNewPwd(id){
	var chkVal = $('#'+id).val();
	if(chkVal == ''){
		return setErrMsg(id,'不能为空',false);
	}
	
	var pattern = /[^\x00-\xff]/g;
	var valLen = chkVal.replace(pattern, "gg").length;
	if(valLen  < 6){
		return setErrMsg(id,'超出最小长度',false);
	}
	if(valLen > 12){
		return setErrMsg(id,'超出最大长度',false);
	}
	
	pattern = /^([a-zA-Z0-9]+)$/;
	if(!pattern.test(chkVal)){
		return setErrMsg(id,'必须为英数字',false);
	}
	
	return setErrMsg(id,'',true);
}

function setErrMsg(chkTagId,msg,bol){
	$("#errmsg_" + chkTagId).text(msg);
	return bol;
}

function submitForm(){
	$('#editMemberForm').ajaxSubmit({
		url : $('#base').val()+'/auth/user/memberPwdEdit.do',
		type : 'post',
		error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
		success : function(data) {
			closeDialog('popId');
			
			if(data != 0){
				// 提示失败
				dialogMsg(-1);
			}else{
			    dialogMsg(0);
			}
		}
	});
}
-->
</script>
