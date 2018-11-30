<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
	<%@ include file="/common/include.jsp"%>
	<link href="${ctx}/static/css/dialog.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx }/static/js/view/contract/contract_check.js"></script>
	<script type="text/javascript">
		$(function(){
		/*textarea提示信息*/
		    $('.bomp-sce-area').find('textarea,span').click(function(){
		    	var ipt_a = $(this).parent().find('textarea');
		        ipt_a.focus();
		        $(this).parent().find('span').hide();
		        ipt_a.blur(function(){
		            if($(this).val() == ''){
		                $(this).parent().find('span').show();
		            }
		         }); 
		    });
		
		
			$("#cancelBtn").click(function(){
				closeParentPubDivDialogIframe('to_losing');
			});
			$("#okBtn").click(function(){
				if(checkForm()){
					$("#lform").ajaxSubmit({
						dataType:'json',
						error:function(){},
						success:function(data){
							if(data == '1'){
								window.top.iDialogMsg("提示","转为流失客户成功!");
								setTimeout('window.parent.loadData();closeParentPubDivDialogIframe("to_losing");',1000);
							}else{
								window.top.iDialogMsg("提示","转为流失客户失败!");
							}
						}
					});
				}
			});
		});
	</script>
</head>
<body>
	<form id="lform" action="${ctx }/res/cust/changeToLosing" method="post">
		<div class='bomp-cen'>
			<div class="bomp-tip-a">
	<!-- 			<label class="tip-b tip-b-mar fl_l"></label> -->
				<input type="hidden" name="ids" value="${ids }"/>
				<input type="hidden" name="module" value="${module }"/>
				<div class="box fl_l">
					<div class='bomp-p w-a'>
						<label class='lab_a fl_l'><b class="com-red">*</b>流失原因：</label>
						<select class='sel_a area_w fl_l' checkPub="chk_2_1_0" name="losingId">
							<option value="">-请选择-</option>
							<c:forEach items="${options }" var="opt">
								<option value="${opt.optionlistId }">${opt.optionName }</option>
							</c:forEach>
						</select>
						<span class="error"></span>
					</div>
					<div class='bomp-p w-a'>
						<label class='lab_a fl_l'>备注：</label>
						<label class="bomp-sce-area">
							<textarea name="losingRemark" checkPub="chk_1_0_0" maxlength="150"  class='area_a area_w fl_l'></textarea>
							<span style="width:400px;height:60px;">最多可输入150个汉字。</span>
						</label>
					</div>
				</div>
			</div>
			<div class='bomb-btn'>
				<label class='bomb-btn-cen'>
					<a href="javascript:;" id="okBtn" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>确定</label></a>
					<a href="javascript:;" id="cancelBtn" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
				</label>
			</div>
		</div>
	</form>
</body>
</html>
