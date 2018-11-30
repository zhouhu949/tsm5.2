<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
   <%@ include file="/common/include.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--主要样式-->
    <title>延期审批弹出层</title>
    <script type="text/javascript">
    $(function() { 
    	var isSubmited = false;
	    // 保存
		$("#btn_save").click(function(){
			var isPass = checkIsNull();
			if(!isSubmited && isPass){
				isSubmited = true;
					$("#myform").ajaxSubmit({
						url : '${ctx}/cust/custFollow/extension/deferredAudit.do',
						type : 'post',
						error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
						success : function(data) {							
							if(data == 0){
								// 默认刷新主页面
								window.top.iDialogMsg("提示","保存成功！");
								$("#query", window.parent.document).click();
								closeParentPubDivDialogIframe('alert_cust_follow_delay');							
							}else{
								// 提示失败
								window.top.iDialogMsg("提示","保存失败！");
							}
						}
					});			
			}
		
		});
		
		//取消
		$("#btn_cancel").click(function(){
			closeParentPubDivDialogIframe('alert_cust_follow_delay');
	    });
    });
    
    // 验证不能为空
    function checkIsNull(){
    	var isTrue = true;
    	var daysExtension = $("#daysExtension").val();
    	if(daysExtension == null || $.trim(daysExtension) == ""){
    		window.top.iDialogMsg("提示","申请延期不能为空！");
    		isTrue = false;
    	}
    	return isTrue;
    }
    </script>
</head>
<!-- 延期审批弹出层 -->
<body style="overflow:hidden;" scroll="no">
<form method="post" id="myform">
<div class="bomp-cen">
   <div class="bomp-tip-a">
       <p class="bomp-p w-a" style="width:310px;">
           <label class="lab_a fl_l" id="t_daysExtension">申请延期：</label>
           <span class="kp_element">
              <input  type="hidden" name="custId" value="${custId}"/>
           	  <input type="text" class="ipt_a fl_a" id="daysExtension"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  name="daysExtension" maxlength="2"/>
           	  <span style="display:inline-block;height:30px;line-height:30px;padding:0 0 0 5px;">天</span>
           </span>
       </p>
       <p class="bomp-p w-a">
           <label class="lab_a fl_l">延期原因：</label>
           <span class="kp_element">
           	  <textarea class="area_a fl_l" id="reasonsExtension"   maxlength="40" name="reasonsExtension" style="width:180px;"></textarea>
           </span>
        </p>
    <div class='bomb-btn'style="margin-top:50px;">
		<label class='bomb-btn-cen'>
			<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l" id="btn_save"><label>确定</label></a>
			<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l" id="btn_cancel"><label>取消</label></a>
		</label>
	</div>
 </div> 
</div>
</form>
<!-- 延期审批弹出层 -->
</body>
</html>
