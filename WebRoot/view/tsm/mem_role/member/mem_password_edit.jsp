<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css"><!--主要样式-->
  <form id="myForm_pass_edit" method="post"> 			
			<div id="pass-word-demo" class="memb-edit-info" style="width:400px;">
			    <input type="hidden" name="account" value="${memberAcc}"/>
				<p class="clearfix" ><label class="fl_l" for="">帐号：</label><span class="overflow_hidden fl_l" style="width:270px;height:auto;"title="${memberAcc}">${memberAcc}</span></p>
				<div  class="com-search clearfix" style="min-height:35px;">
					<label class="fl_l" for="" >新密码：</label>
					<input type="password" name="pwd" id="pwd">
					<span id="errmsg_pwd" class="must" style="display:block; color:red;"></span>
				</div>
				<div  class="com-search clearfix" style="min-height:35px;">
					<label class="fl_l" for=""></label>
					<span>密码格式：6-12位数字、字母、符号，区分大小写</span>
				</div>
				<div class="sure-cancle clearfix" style="margin:0 auto;">
					<a href="javascript:;" id="btn_edit"  class="com-btna bomp-btna com-btna-sty fl_l"><label>确定</label></a>
					<a href="javascript:;" id="btn_close"  onclick="close02();" class="com-btna bomp-btna fl_l" ><label>取消</label></a>
		        </div>
			</div>
  </form>
  <script type="text/javascript">
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
			return setErrMsg(id,'最小长度为6位',false);
		}
		if(valLen > 12){
			return setErrMsg(id,'超出最大长度',false);
		}
		
//		pattern = /^([a-zA-Z0-9]+)$/;
//		if(!pattern.test(chkVal)){
//			return setErrMsg(id,'必须为英数字',false);
//		}
		
//		pattern =/^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}$/;
		pattern =/(?!^(\d+|[a-zA-Z]+|[~!@#$%^&*\-=+.,'";:|\\<>\]}\[{?]+)$)^[\da-zA-Z~!@#$%^&*\-=_+.,'";:|\\<>\]}\[{?]{6,12}$/;
		
		if(!pattern.test(chkVal)){
			return setErrMsg(id,'新密码安全性较低，建议输入数字+字母的组合密码',false);
		}
		return setErrMsg(id,'',true);
	}

	function setErrMsg(chkTagId,msg,bol){
		$("#errmsg_" + chkTagId).text(msg);
		return bol;
	}

	// 保存
	function submitForm(){
		$('#myForm_pass_edit').ajaxSubmit({
			url : '${ctx}/auth/user/memberPwdEdit.do',
			type : 'post',
			error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
			success : function(data) {		
				if(data == 0){
					window.top.iDialogMsg("提示","保存成功！");
					setTimeout('window.parent.frames[0].loadData();',1000);
					setTimeout('close02();',1000);
				}else if(data == "_REQUEST_REFUSE"){
					window.top.iDialogMsg("提示","新密码过于简单，请设复杂一些！");
				}else{
					window.top.iDialogMsg("提示","保存失败！");
				}
			}
		});
	}
	
	// 取消
  	function close02(){
  		closeParentPubDivDialogIframe('mem_b_');
  	}
  </script>
