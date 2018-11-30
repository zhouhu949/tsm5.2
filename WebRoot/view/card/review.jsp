<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <style type="text/css"></style>
    <%@ include file="/common/include.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
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
			//确定
			$(".sure-btn").click(function(){
					var text = $("#revComment").val();
					if(text == null || text == ''){
						$("#errorMsg").text("请填写评论内容!");
						return;
					}
					if(text.length > 150){
						$("#errorMsg").text("评论内容不能超过150个字!");
						return;
					}
					$("#cform").ajaxSubmit({
						dataType:'json',
						success:function(data){
							if(data == '1'){
								window.top.iDialogMsg("提示","评论成功!");
								setTimeout("window.parent.location.href=window.parent.location.href",1000);
							}else{
								window.top.iDialogMsg("提示","评论失败!");
							}
						}
					});
			});
			
			//取消
			$(".cancel-btn").click(function(){
				closeParentPubDivDialogIframe('alert_cust_card_a');
			});
		});
	</script>
</head>
<body>
	<form id="cform" action="${ctx }/cust/card/saveReview" method="post">
		<input type="hidden" name="custId" id="custId" value="${custId }" />
		<div class='bomp-cen'>
			<p class='bomp-p bomp-p-width bomp-error'>
				<label class='lab_a fl_l'><b class="com-red">*</b>评论内容：</label>
				<label class="bomp-sce-area">
					<textarea class='area_a w_b fl_l' id="revComment" maxlength="150" name="revComment" style="height:100px;"></textarea>
					<span style="width:550px;height:100px;">最多可输入150个汉字。</span>
				</label>
				<span id="errorMsg" class="error"></span>
			</p>
			<div class='bomb-btn bomb-btn-top'>
				<label class='bomb-btn-cen'>
					<a href="javascript:;" class="com-btna bomp-btna sure-btn com-btna-sty fl_l"><label>确定</label></a>
					<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
				</label>
			</div>
		</div>
	</form>
</body>
</html>
