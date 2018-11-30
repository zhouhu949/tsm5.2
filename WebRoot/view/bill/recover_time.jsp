<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <%@ include file="/common/include.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css${_v}"><!--弹框样式-->
    <script type="text/javascript">
    	$(function(){
    		$("#recoverTime").blur(function(){
    			$("#errorMsg").html('');
    			var tmp_times = getFloatStr($(this).val());
    			$(this).val(tmp_times);
    			var totalTimes = parseFloat($("#totalTimes").val());
    			var selCounts = parseInt($("#recoverNum").val());
    			var after_times = totalTimes + parseFloat(tmp_times)*selCounts;
    			$("#after_times").text(after_times.toFixed(1));
    		});
    		
    		//分配
    		$("#okBtn").click(function(){
    			$("#errorMsg").html('');
    			var tmp_times = getFloatStr($("#recoverTime").val());
				tmp_times = parseFloat(tmp_times);  			
    			var accs = $("#accs").val();
    			//保存
    			$.ajax({
    				url:ctx+"/pakage/allocation/recoverBatch",
    				type:"post",
    				data:{accs:accs,recoverTimes:tmp_times,module:'${module}'},
    				dataType:"html",
    				error:function(){},
    				success:function(data){
    					if(data == "1"){
    						window.top.iDialogMsg("提示",'回收通信币成功！');
    						setTimeout("window.parent.$('#cform').submit();", 1000);
    					}else{
    						window.top.iDialogMsg("提示",'回收通信币失败！');
    					}
    				}
    			})
    		});
    		
    		//取消
    		$("#cancleBtn").click(function(){
    			closeParentPubDivDialogIframe("recoverFrame");
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
	<input type="hidden" id="totalTimes" value="${totalTimes }" />
	<input type="hidden" id="recoverNum" value="${recoverNum }" />
	<div class="cust-impo-res" style="width:auto;padding-left:0;">
		<div class="comm-tim-dist clearfix" style="min-height:30px;z-index:999">
			<label class="fl_l" for=""><!-- <span class="font-color" style="width:auto;">*</span> -->当前${empty module ? '单位' : '' }剩余总通信币：</label>
			<span class="font-color">${totalTimes }</span><span class="product-currencyUnit">蜂豆</span>
		</div>
		<div class="comm-tim-dist clearfix" style="min-height:30px;">		
			<label class="fl_l" for="">已选择帐号：</label>
			<span class="font-color">${recoverNum }</span>个
		</div>
	</div>
	<div class="comm-tim-dist clearfix" style="min-height:30px;z-index:-1">
		<label class="fl_l" for="">预计回收通信币：</label>
		<input type="text" id="recoverTime" style="width:150px;" value="100.0">&nbsp;<span class="product-currencyUnit">蜂豆</span>/人
		<label id="errorMsg" class="font-color fl_l"></label>
	</div>
	<div class="comm-tim-dist clearfix" style="min-height:30px;z-index:-1">
		<label class="fl_l" for="">预计回收后${empty module ? '单位' : '' }剩余通信币：</label>
		<span class="font-color" id="after_times">${totalTimes+recoverNum*100.0 }</span><span class="product-currencyUnit">蜂豆</span>
	</div>
	<p style="margin:20px 0 0 40px;">
		<label for=""><span class="font-color" style="width:auto;font-size:14px;">温馨提示：</span><span class="font-color" style="width:auto">通信币为0的帐号不作回收处理</span></label>
	</p>
	<!-- <p class="warn-deco"><span clas="font-color">分组名称不能为空</span></p> -->
	<div class="bomb-btn">
		<label class="bomb-btn-cen">
			<a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="javascript:;" id="okBtn"><label>确定</label></a>
			<a class="com-btna bomp-btna cancel-btn fl_l" id="cancleBtn" href="javascript:;"><label>取消</label></a>
		</label>
	</div>
</body>