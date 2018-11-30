<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <%@ include file="/common/include.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
    <script type="text/javascript">
    	$(function(){
    		$("#rechargeSms").keyup(function(){
    			$("#errorMsg").html('');
    			var tmp_times = $(this).val();
    			var max_times = parseInt($("#maxRechargeSms").val());
    			if(tmp_times == '' || isNaN(tmp_times) || parseInt(tmp_times)!= tmp_times || parseInt(tmp_times)<=0){
    				$("#errorMsg").html('请输入正整数');
    				return;
    			}
    			tmp_times = parseInt(tmp_times);
    			if(tmp_times > max_times){
    				$("#errorMsg").html('超出最大可分配短信');
    				return;
    			}
    			var totalSms = $("#totalSms").val();
    			var selCounts = $("#rechargeNum").val();
    			var after_sms = totalSms - tmp_times*selCounts;
    			$("#after_sms").text(after_sms);
    		});
    		
    		//分配
    		$("#okBtn").click(function(){
    			$("#errorMsg").html('');
    			var tmp_times = $("#rechargeSms").val();
    			var max_times = parseInt($("#maxRechargeSms").val());
    			if(tmp_times == '' || isNaN(tmp_times) || parseInt(tmp_times)!= tmp_times || parseInt(tmp_times)<=0){
    				$("#errorMsg").html('请输入正整数');
    				return;
    			}
    			tmp_times = parseInt(tmp_times);
    			if(tmp_times > max_times){
    				$("#errorMsg").html('超出最大可分配短信');
    				return;
    			}
    			var accs = $("#accs").val();
    			
    			//保存
    			$.ajax({
    				url:ctx+"/pakage/allocation/rechargeSmsBatch",
    				type:"post",
    				data:{accs:accs,rechargeSms:tmp_times},
    				dataType:"html",
    				error:function(){},
    				success:function(data){
    					if(data == "1"){
    						window.top.iDialogMsg("提示",'分配短信成功！');
    						setTimeout("window.parent.$('#cform').submit();", 1000);
    					}else{
    						window.top.iDialogMsg("提示",'分配短信失败！');
    					}
    				}
    			})
    		});
    		
    		//取消
    		$("#cancleBtn").click(function(){
    			closeParentPubDivDialogIframe("rechargeSmsFrame");
    		});
    	});
    </script>
</head>
<body>
	<input type="hidden" id="accs" value="${accs }" />
	<input type="hidden" id="maxRechargeSms" value="${maxRechargeSms }" />
	<input type="hidden" id="totalSms" value="${totalSms }" />
	<input type="hidden" id="rechargeNum" value="${rechargeNum }" />
	
	<div class="cust-impo-res" style="width:auto;padding-left:0;">
		<div class="comm-tim-dist clearfix" style="min-height:30px;z-index:999">
			<label class="fl_l" for=""><!-- <span class="font-color" style="width:auto;">*</span> -->当前单位剩余可分配短信：</label>
			<span class="font-color">${totalSms }</span>条
		</div>
		<div class="comm-tim-dist clearfix" style="min-height:30px;">		
			<label class="fl_l" for="">已选择帐号：</label>
			<span class="font-color">${rechargeNum }</span>个
		</div>
		<div class="comm-tim-dist clearfix" style="min-height:30px;">		
			<label class="fl_l" for="">最大可分配短信：</label>
			<span class="font-color"><fmt:formatNumber value="${maxRechargeSms }" /></span>条/人
		</div>
	</div>
	<div class="comm-tim-dist clearfix" style="min-height:30px;z-index:-1">
		<label class="fl_l" for="">预计分配短信：</label>
		<input type="text" id="rechargeSms" value="${maxRechargeSms }" style="width:150px;">&nbsp;条/人
		<label id="errorMsg" class="font-color fl_l"></label>
	</div>
	<div class="comm-tim-dist clearfix" style="min-height:30px;z-index:-1">
		<label class="fl_l" for="">分配后单位剩余短信：</label>
		<span id="after_sms" class="font-color">${restSms }</span>条
	</div>
	<!-- <p class="warn-deco"><span clas="font-color">分组名称不能为空</span></p> -->
	<div class="bomb-btn">
		<label class="bomb-btn-cen">
			<a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="javascript:;" id="okBtn"><label>确定</label></a>
			<a class="com-btna bomp-btna cancel-btn fl_l" href="javascript:;" id="cancleBtn"><label>取消</label></a>
		</label>
	</div>
</body>