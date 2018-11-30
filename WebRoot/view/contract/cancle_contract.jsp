<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <%@ include file="/common/include.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
    <script type="text/javascript">
    	$(function(){
    		/*表单优化*/
            $('#isRemoveOrders').iCheck({
                checkboxClass: 'icheckbox_minimal',
                radioClass: 'iradio_minimal',
                increaseArea: '20%'
            });
    		
        	$('#isRemoveOrders').on('ifChecked',function(){
        		$("#isRemoveOrders").val("1");
        	});
        	
        	$('#isRemoveOrders').on('ifUnchecked',function(){
        		$("#isRemoveOrders").val("0");
        	});
    		 //取消合同
    	     var is_submit = false;
    		 $("#qxBtn").click(function(){
    			if(!is_submit){
    				is_submit = true;
    				var caid = $("#caid").val();
        			var custId = $("#custId").val();
        			var cancleRemark = $("#cancleRemark").val();
        			var isRemoveOrders = $("#isRemoveOrders").val();
        			if(cancleRemark.length > 100){
    					$(".error").text("最多只能输入100个字!");
    					is_submit = false;
        				return;
        			}
        	    	$.ajax({
        					url:ctx+'/contract/del',
        					type:'post',
        					data:{id:caid,custId:custId,cancleRemark:cancleRemark,isRemoveOrders:isRemoveOrders},
        					dataType:'json',
        					error:function(){is_submit = false;},
        					success:function(data){
        						if(data == '1'){
        							window.top.iDialogMsg("提示","取消合同成功!");
        							setTimeout("window.parent.document.forms[0].submit()",1000);
        						}/* else if(data == '2'){
        							window.top.iDialogMsg("提示","删除合同成功,客户已经自动转变为意向客户状态!");
        							setTimeout("window.parent.document.forms[0].submit()",1000);
        						}else if(data == '1' || data == '3'){
        							window.top.iDialogMsg("提示","删除合同成功,客户已经自动转变为资源状态!");
        							setTimeout("window.parent.document.forms[0].submit()",1000);
        						} */else{
        							is_submit = false;
        							window.top.iDialogMsg("提示","取消合同失败!");
        						}
        					}
        				});
    			}
    		});

    	});
    </script>
</head>
<body>
<input type="hidden" id="caid" value="${caid }" />
<input type="hidden" id="custId" value="${custId }">
<div class='bomp-cen bomp-cma'>
	<div class="bomp-tip-a">
		<div class="box fl_l cancle-contract-reason-box" style="width:100%;">
			<div class='bomp-p w-a'>
				<label class='lab_a fl_l'>取消原因：</label>
				<label class="bomp-cma-area">
					<textarea id="cancleRemark" class='area_a area_w fl_l'  placeholder="最多可输入100个汉字。"></textarea>
				</label>
			</div>
			<span class="error cancle-contract-error"></span>
		</div>
<!-- 		<p class="bomp-p bomp-tit"> -->
<!-- 			<label class="bomp-red">温馨提示：</label>取消合同后的客户状态默认为此合同签约前的客户状态。 -->
<!-- 		</p> -->
	</div>
  <div class="cancle-contract-link">
    <input type="checkbox" id="isRemoveOrders" name="isRemoveOrders" value="0">勾选将自动作废该合同关联的订单
  </div>
	<div class='bomb-btn'>
		<label class='bomb-btn-cen'>
			<a href="javascript:;" id="qxBtn" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>确定</label></a>
			<a href="javascript:;" onclick="closeParentPubDivDialogIframe('cancle_order');" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
		</label>
	</div>
</div>
</body>
</html>
