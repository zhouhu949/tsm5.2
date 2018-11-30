<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<!-- 后台需保存明码，此处暂不加密 
 <script type="text/javascript" src="${ctx}/js/jquery/jquery.md5.js"></script>
 -->
 <!-- ajax session time out 处理 -->

<div class="layer_res edit_layer_res">
  <div class="layer_res_cont padding_10">
       <form id="editAdminForm" name="editAdminForm" method="post">
          <p class="tool_layer_res">
             <label><span class="required">*</span>原密码：</label>
             <input id="old_pwd" name="old_pwd" type="password" class="text_layer_res"/>
             <span id="errmsg_old_pwd" class="must"></span>
          </p>
          <p class="tool_layer_res">
          	<label></label>
          	<span>密码格式：6-12位英文字符与数字，区分大小写</span>
          </p>
          <p class="tool_layer_res">
             <label><span class="required">*</span>新密码：</label>
             <input id="pwd" name="pwd" type="password" class="text_layer_res"/>
             <span id="errmsg_pwd" class="must"></span>
          </p>
          <p class="tool_layer_res">
             <label><span class="required">*</span>确认密码：</label>
             <input id="repeat_pwd" name="repeat_pwd" type="password" class="text_layer_res"/>
             <span id="errmsg_repeat_pwd" class="must"></span>
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
	// 原密码
	$("#old_pwd").blur(function(){
		chkOldPwd();
	});
	
	// 密码
	$("#pwd").blur(function(){
		chkNewPwd('pwd');
	});
	
	// 重复密码
	$("#repeat_pwd").blur(function(){
		chkNewPwd('repeat_pwd');
	});
	
	// 保存
	$("#btn_edit").click(function(){
		var isSubmited = false;
		if(!isSubmited && chkOldPwd() & chkNewPwd('pwd') & chkNewPwd('repeat_pwd')){
			submitForm();
			isSubmited =true;
		}
	});
	
	// 取消
	$("#btn_close").click(function(){
		closeDialog('popId');
	});
	
});

// 验证原密码
function chkOldPwd(){
	id = 'old_pwd';
	var pwd = $.trim($('#'+id).val());
	if(pwd == ''){
		return setErrMsg(id,'不能为空',false);
	}
	
	if(!ajaxChkVal(pwd)){
		return setErrMsg(id,'输入值不存在',false);
	}
	
	return setErrMsg(id,'',true);
}

// 验证新密码
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
	
	if(id == 'repeat_pwd'){
		var pwdVal = $('#pwd').val();
		if(pwdVal != chkVal){
			return setErrMsg(id,'两次输入不一致',false);
		}
	}
	
	return setErrMsg(id,'',true);
}

function setErrMsg(chkTagId,msg,bol){
	$("#errmsg_" + chkTagId).text(msg);
	return bol;
}

/**
 * Ajax验证
 * @param chkVal  待验证元素
 * @return
 */
function ajaxChkVal(chkVal){
	var isPass = true;
	var _base = $('#base').val();
	
	$.ajax({
		url : _base+'/auth/user/checkUserPwd.do',
		data: {'pwd':chkVal},
		type : 'post',
		async:false,
		error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
		success : function(data) {
			if(data != 0 ){
				isPass = false;
			}
		}
	});
	
	return isPass;
}

function submitForm(){
	$('#editAdminForm').ajaxSubmit({
		url : $('#base').val()+'/auth/user/adminPwdEdit.do',
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
