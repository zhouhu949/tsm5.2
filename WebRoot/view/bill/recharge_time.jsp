<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <%@ include file="/common/include.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
    <script type="text/javascript">
    	$(function(){
    		$("#rechargeTimes").blur(function(){
    			$("#errorMsg").html('');
    			var tmp_times = getFloatStr($(this).val());
    			$(this).val(tmp_times);
    			var max_times = parseFloat($("#maxRechargeTime").val());
    			tmp_times = parseFloat(tmp_times);
    			if(tmp_times > max_times){
    				$("#errorMsg").html('超出最大可分配通信币');
    				return;
    			}
    			var totalTimes = $("#totalTimes").val();
    			var selCounts = $("#rechargeNum").val();
    			var after_times = totalTimes - tmp_times*selCounts;
    			$("#after_times").text(after_times.toFixed(1));
    		});
    		
    		//分配
    		$("#okBtn").click(function(){
    			$("#errorMsg").html('');
    			var tmp_times = getFloatStr($("#rechargeTimes").val());
    			var max_times = parseFloat($("#maxRechargeTime").val());
    			tmp_times = parseFloat(tmp_times);
    			if(tmp_times > max_times){
    				$("#errorMsg").html('超出最大可分配通信币');
    				return;
    			}
    			var accs = $("#accs").val();
    			
    			//保存
    			$.ajax({
    				url:ctx+"/pakage/allocation/rechargeBatch",
    				type:"post",
    				data:{accs:accs,rechargeTimes:tmp_times,module:'${module}'},
    				dataType:"html",
    				error:function(){},
    				success:function(data){
    					if(data == "1"){
    						window.top.iDialogMsg("提示",'分配通信币成功！');
    						setTimeout("window.parent.$('#cform').submit();", 1000);
    					}else{
    						window.top.iDialogMsg("提示",'分配通信币失败！');
    					}
    				}
    			})
    		});
    		
    		//取消
    		$("#cancleBtn").click(function(){
    			closeParentPubDivDialogIframe("rechargeFrame");
    		});
    	});
    	
    	var getFloatStr = function(num){
    	    num += '';
    	    num = num.replace(/[^0-9|\.]/g, ''); //清除字符串中的非数字非.字符
    	    if(/^0+/.test(num)) //清除字符串开头的0
    	        num = num.replace(/^0+/, '');
    	    if(!/\./.test(num)) //为整数字符串在末尾添加.00
    	        num += '.0';
    	    if(/^\./.test(num)) //字符以.开头时,在开头添加0
    	        num = '0' + num;
    	    num += '0';        //在字符串末尾补零
    	    num = num.match(/\d+\.\d{1}/)[0];
    	    return num;
    	};
    </script>
</head>
<body>
	<input type="hidden" id="accs" value="${accs }" />
	<input type="hidden" id="maxRechargeTime" value="${maxRechargeTime }" />
	<input type="hidden" id="totalTimes" value="${totalTimes }" />
	<input type="hidden" id="rechargeNum" value="${rechargeNum }" />
	
	<div class="cust-impo-res" style="width:auto;padding-left:0;">
		<div class="comm-tim-dist clearfix" style="min-height:30px;z-index:999">
			<label class="fl_l" for=""><!-- <span class="font-color" style="width:auto;">*</span> -->当前${empty module ? '单位' : '' }剩余可分配通信币：</label>
			<span class="font-color">${totalTimes }</span><span class="product-currencyUnit">蜂豆</span>
		</div>
		<div class="comm-tim-dist clearfix" style="min-height:30px;">		
			<label class="fl_l" for="">已选择帐号：</label>
			<span class="font-color">${rechargeNum }</span>个
		</div>
		<div class="comm-tim-dist clearfix" style="min-height:30px;">		
			<label class="fl_l" for="">最大可分配通信币：</label>
			<span class="font-color">${maxRechargeTime }</span><span class="product-currencyUnit">蜂豆</span>/人
		</div>
	</div>
	<div class="comm-tim-dist clearfix" style="min-height:30px;z-index:-1">
		<label class="fl_l" for="">预计分配通信币：</label>
		<input type="text" id="rechargeTimes" value="${maxRechargeTime }" style="width:150px;">&nbsp;<span class="product-currencyUnit">蜂豆</span>/人
		<label id="errorMsg" class="font-color fl_l"></label>
	</div>
	<div class="comm-tim-dist clearfix" style="min-height:30px;z-index:-1">
		<label class="fl_l" for="">分配后${empty module ? '单位' : '' }剩余通信币：</label>
		<span id="after_times" class="font-color"><fmt:formatNumber value="${restTimes }" pattern="#,##0.0#" maxFractionDigits="1"/></span><span class="product-currencyUnit">蜂豆</span>
	</div>
	<!-- <p class="warn-deco"><span clas="font-color">分组名称不能为空</span></p> -->
	<div class="bomb-btn">
		<label class="bomb-btn-cen">
			<a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="javascript:;" id="okBtn"><label>确定</label></a>
			<a class="com-btna bomp-btna cancel-btn fl_l" href="javascript:;" id="cancleBtn"><label>取消</label></a>
		</label>
	</div>
</body>