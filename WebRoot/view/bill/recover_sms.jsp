<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <%@ include file="/common/include.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
    <script type="text/javascript">
    	$(function(){
    		$("#recoverSms").keyup(function(){
    			$("#errorMsg").html('');
    			var tmp_times = $(this).val();
    			if(tmp_times == '' || isNaN(tmp_times) || parseInt(tmp_times)!= tmp_times || parseInt(tmp_times)<=0){
    				$("#errorMsg").html('请输入正整数');
    				return;
    			}
    			
    			var totalSms = parseInt($("#totalSms").val());
    			var selCounts = parseInt($("#recoverNum").val());
    			var after_sms = totalSms + parseInt(tmp_times)*selCounts;
    			$("#after_sms").text(after_sms);
    		});
    		
    		//分配
    		$("#okBtn").click(function(){
    			$("#errorMsg").html('');
    			var tmp_times = $("#recoverSms").val();
    			if(tmp_times == '' || isNaN(tmp_times) || parseInt(tmp_times)!= tmp_times || parseInt(tmp_times)<=0){
    				$("#errorMsg").html('请输入正整数');
    				return;
    			}
    			
    			var accs = $("#accs").val();
    			
    			//保存
    			$.ajax({
    				url:ctx+"/pakage/allocation/recoverSmsBatch",
    				type:"post",
    				data:{accs:accs,recoverSms:tmp_times},
    				dataType:"html",
    				error:function(){},
    				success:function(data){
    					if(data == "1"){
    						window.top.iDialogMsg("提示",'回收短信成功！');
    						setTimeout("window.parent.$('#cform').submit();", 1000);
    					}else{
    						window.top.iDialogMsg("提示",'回收短信失败！');
    					}
    				}
    			})
    		});
    		
    		//取消
    		$("#cancleBtn").click(function(){
    			closeParentPubDivDialogIframe("recoverSmsFrame");
    		});
    	});
    </script>
</head>
<body>
	<input type="hidden" id="accs" value="${accs }" />
	<input type="hidden" id="totalSms" value="${totalSms }" />
	<input type="hidden" id="recoverNum" value="${recoverNum }" />
	<div class="cust-impo-res" style="width:auto;padding-left:0;">
		<div class="comm-tim-dist clearfix" style="min-height:30px;z-index:999">
			<label class="fl_l" for=""><!-- <span class="font-color" style="width:auto;">*</span> -->当前单位剩余总短信：</label>
			<span class="font-color">${totalSms }</span>条
		</div>
		<div class="comm-tim-dist clearfix" style="min-height:30px;">		
			<label class="fl_l" for="">已选择帐号：</label>
			<span class="font-color">${recoverNum }</span>个
		</div>
	</div>
	<div class="comm-tim-dist clearfix" style="min-height:30px;z-index:-1">
		<label class="fl_l" for="">预计回收短信：</label>
		<input type="text" id="recoverSms" style="width:150px;" value="100">&nbsp;条/人
		<label id="errorMsg" class="font-color fl_l"></label>
	</div>
	<div class="comm-tim-dist clearfix" style="min-height:30px;z-index:-1">
		<label class="fl_l" for="">预计回收后单位剩余短信：</label>
		<span class="font-color" id="after_sms">${totalSms+recoverNum*100 }</span>条
	</div>
	<p style="margin:20px 0 0 40px;">
		<label for=""><span class="font-color" style="width:auto;font-size:14px;">温馨提示：</span><span class="font-color" style="width:auto">短信条数为0的帐号不作回收处理</span></label>
	</p>
	<!-- <p class="warn-deco"><span clas="font-color">分组名称不能为空</span></p> -->
	<div class="bomb-btn">
		<label class="bomb-btn-cen">
			<a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="javascript:;" id="okBtn"><label>确定</label></a>
			<a class="com-btna bomp-btna cancel-btn fl_l" id="cancleBtn" href="javascript:;"><label>取消</label></a>
		</label>
	</div>
</body>