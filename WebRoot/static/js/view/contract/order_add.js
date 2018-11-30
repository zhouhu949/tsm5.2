$(function(){
	$("select[id^=pro_id_]").live("change",function(){
		var index = $(this).attr("id").split("_")[2];
		var option = $(this).find("option:selected");
		var model = option.attr("model");
		var ptype = option.attr("ptype");
		var price = option.attr("price");
		var pname = option.val() == '' ? '' : option.text();
		$("#pro_model_"+index).val(model);
		$("#pro_type_"+index).val(ptype);
		$("#pro_price_"+index).val(getThreeFloatStr(price));
		$("#pro_buyprice_"+index).val(getThreeFloatStr(price));
		$("#pro_name_"+index).val(pname);
	});
	var cidx = parseInt($("#dlength").val());
	$("#addDetailBtn").click(function(){
		cidx+=1;
		var pro_sel_context = $("#pmode").html();
		var context = '<tr id="pro_detail_'+cidx+'">';
			context+= '		<td>';
			context+= '			<div class="overflow_hidden">';
			context+= '				<input type="hidden" id="pro_name_'+cidx+'" name="orderDetailBeans['+cidx+'].productName" value=""/>';
			context+= '				<select id="pro_id_'+cidx+'" name="orderDetailBeans['+cidx+'].productId" class="ipt_bor w80">';
			context+= pro_sel_context;
			context+= '				</select>';
			context+= '			</div>';
			context+= '		</td>';
			context+= '		<td>';
			context+= '			<div class="overflow_hidden">';
			context+= '				<input type="text" readonly="readonly" name="orderDetailBeans['+cidx+'].productModel" class="ipt_bor w70" id="pro_model_'+cidx+'" value="">';
			context+= '			</div>';
			context+= '		</td>';
			context+= '		<td>';
			context+= '			<div class="overflow_hidden">';
			context+= '				<input type="text" readonly="readonly" name="orderDetailBeans['+cidx+'].productType" class="ipt_bor w70" id="pro_type_'+cidx+'" value="">';
			context+= '			</div>';
			context+= '		</td>';
			context+= '		<td>';
			context+= '			<div class="overflow_hidden">';
			context+= '				<input type="text" readonly="readonly" name="orderDetailBeans['+cidx+'].productPrice" class="ipt_bor w70" id="pro_price_'+cidx+'" value="">';
			context+= '			</div>';
			context+= '		</td>';
			context+= '		<td>';
			context+= '			<div class="overflow_hidden">';
			context+= '				<input type="text" id="pro_buyprice_'+cidx+'" name="orderDetailBeans['+cidx+'].buyPrice" value="" class="ipt_bor w100" />';
			context+= '			</div>';
			context+= '		</td>';
			context+= '		<td>';
			context+= '			<div class="overflow_hidden">';
			context+= '				<input type="text" id="pro_buynum_'+cidx+'" name="orderDetailBeans['+cidx+'].buyNum" value="" class="ipt_bor w50" />';
			context+= '			</div>';
			context+= '		</td>';
			context+= '		<td>';
			context+= '			<div class="overflow_hidden">';
			context+= '				<input type="text" id="pro_buymoney_'+cidx+'" name="orderDetailBeans['+cidx+'].buyMoney" value="" class="ipt_bor w100" />';
			context+= '			</div>';
			context+= '		</td>';
			context+= '		<td>';
			context+= '			<div class="overflow_hidden">';
			context+= '				<input type="text" id="pro_context_'+cidx+'" name="orderDetailBeans['+cidx+'].context" value="" class="ipt_bor w100" />';
			context+= '			</div>';
			context+= '		</td>';
			context+= '		<td style="width:30px;">';
			context+= '			<div class="overflow_hidden w30 link">';
			context+= '				<a href="javascript:;" id="pro_del_'+cidx+'" class="del alert_sys_pro_set_d_a" title="删除"></a>';
			context+= '			</div>';
			context+= '		</td>';
			context+= '</tr>';
			$("#pds").append(context);
	});
	
	$("a[id^=pro_del_]").live('click',function(){
		var index = $(this).attr("id").split("_")[2];
		if($("#detail_id_"+index).length > 0){
			var delDetailIdsStr = $("#delDetailIdsStr").val();
			if(delDetailIdsStr != null && delDetailIdsStr != ''){
				delDetailIdsStr+=','+$("#detail_id_"+index).val();
			}else{
				delDetailIdsStr = $("#detail_id_"+index).val();
			}
			$("#delDetailIdsStr").val(delDetailIdsStr);
		}
		$("#pro_detail_"+index).remove();
		var totalMoney=0;
		$("input[id^=pro_buymoney_]").each(function(idx,obj){
			var money = $(this).val();
			if(money != null && money != ''){
				totalMoney+=parseFloat(money);
			}
		});
		$("#totalMoney").val(getFloatStr(totalMoney));
	});
	
	$("input[id^=pro_buyprice_]").live('blur',function(){
		var index = $(this).attr("id").split("_")[2];
		var buyNum = $("#pro_buynum_"+index).val();
//		buyNum = getIntStr(buyNum);
		buyNum = getFloatStr(buyNum);
		$("#pro_buynum_"+index).val(buyNum);
		var buyPrice = $(this).val();
		if(!(/^-?\d+\.?\d{0,3}$/.test(buyPrice))){
			buyPrice = $("#pro_price_"+index).val();
		}
		buyPrice = getThreeFloatStr(buyPrice);
		$(this).val(buyPrice);
		$("#pro_buymoney_"+index).val(getFloatStr(buyPrice*buyNum));
		var totalMoney=0;
		$("input[id^=pro_buymoney_]").each(function(idx,obj){
			var money = $(this).val();
			if(money != null && money != ''){
				totalMoney+=parseFloat(money);
			}
		});
		$("#totalMoney").val(getFloatStr(totalMoney));
	});
	
	$("input[id^=pro_buynum_]").live('blur',function(){
		var index = $(this).attr("id").split("_")[2];
		var buyPrice = $("#pro_buyprice_"+index).val();
		if(!(/^-?\d+\.?\d{0,3}$/.test(buyPrice))){
			buyPrice = $("#pro_price_"+index).val();
		}
		buyPrice = getThreeFloatStr(buyPrice);
		$("#pro_buyprice_"+index).val(buyPrice);
		var buyNum = $(this).val();
//		buyNum = getIntStr(buyNum);
		buyNum = getFloatStr(buyNum);
		$(this).val(buyNum);
		$("#pro_buymoney_"+index).val(getFloatStr(buyPrice*buyNum));
		var totalMoney=0;
		$("input[id^=pro_buymoney_]").each(function(idx,obj){
			var money = $(this).val();
			if(money != null && money != ''){
				totalMoney+=parseFloat(money);
			}
		});
		$("#totalMoney").val(getFloatStr(totalMoney));
	});
	
	$("input[id^=pro_buymoney_]").live('blur',function(){
		var index = $(this).attr("id").split("_")[2];
		
		var buyPrice = $("#pro_buyprice_"+index).val();
		if(!(/^-?\d+\.?\d{0,3}$/.test(buyPrice))){
			buyPrice = $("#pro_price_"+index).val();
		}
		buyPrice = getThreeFloatStr(buyPrice);
		
		$("#pro_buyprice_"+index).val(buyPrice);
		var buyNum = $("#pro_buynum_"+index).val();
		buyNum = getFloatStr(buyNum);
		$("#pro_buynum_"+index).val(buyNum);
		
		var payMoney = $(this).val();
		
		if(payMoney == ''){
			$("#pro_buymoney_"+index).val(getFloatStr(buyPrice*buyNum));
		}
		var totalMoney=0;
		$("input[id^=pro_buymoney_]").each(function(idx,obj){
			var money = $(this).val();
			if(money != null && money != ''){
				totalMoney+=parseFloat(money);
			}
		});
		$("#totalMoney").val(getFloatStr(totalMoney));
	});
	
	var is_submit=false; 
	$("#saveBtn").click(function(){
		if(checkOrderProduct() && checkForm() && !is_submit){
			is_submit = true;
			var action = $("#cform").attr("action");
			action = action+"?orderBean.authState=0";
			$("#cform").attr("action",action);
			$("#cform").ajaxSubmit({
				dataType:'json',
				error:function(){is_submit=false;},
				success:function(data){
					if(data == '1'){
						window.top.iDialogMsg("提示","保存订单成功!");
						var fromPage = $("#fromPage").val();
						if(fromPage == '1'){
							setTimeout("window.parent.location=window.parent.location",1000);
						}else if(fromPage == '2'){
							var caid = $("#caid").val();
							setTimeout("window.parent.location='"+ctx+"/contract/toEdit?fromPage=2&contractId="+caid+"';",1000);
						}else{
							setTimeout("window.parent.document.forms[0].submit();",1000);
						}
					}else if(data == '2'){
						window.top.iDialogMsg("提示","订单保存失败,合同被删除!");
						is_submit=false;
					}else{
						window.top.iDialogMsg("提示","保存订单失败!");
						is_submit=false;
					}
				}
			});
		}
	});
	
	$("#saveEditBtn").click(function(){
//		var action = $("#cform").attr("action");
//		action = action+"?orderBean.authState=0";
//		$("#cform").attr("action",action)
		if(checkOrderProduct() && checkForm() && !is_submit){
			is_submit = true;
			$("#cform").ajaxSubmit({
				dataType:'json',
				error:function(){is_submit = false;},
				success:function(data){
					if(data == '1'){
						window.top.iDialogMsg("提示","编辑订单成功!");
						var fromPage = $("#fromPage").val();
						if(fromPage == '1'){
							setTimeout("window.parent.location=window.parent.location",1000);
						}else if(fromPage == '2'){
							var caid = $("#caid").val();
							setTimeout("window.parent.location='"+ctx+"/contract/toEdit?fromPage=2&contractId="+caid+"';",1000);
						}else{
							setTimeout("window.parent.document.forms[0].submit();",1000);
						}
					}else{
						window.top.iDialogMsg("提示","编辑订单失败!");
						is_submit = false;
					}
				}
			});
		}
	});
	
	$("#reportBtn").click(function(){
		if(checkOrderProduct() && checkForm() && !is_submit){
			is_submit = true;
			var action = $("#cform").attr("action");
			action = action+"?orderBean.authState=1";
			$("#cform").attr("action",action);
			$("#cform").ajaxSubmit({
				dataType:'json',
				error:function(){is_submit = false;},
				success:function(data){
					if(data == '1'){
						window.top.iDialogMsg("提示","上报订单成功!");
						var fromPage = $("#fromPage").val();
						if(fromPage == '1'){
							setTimeout("window.parent.location=window.parent.location",1000);
						}else if(fromPage == '2'){
							var caid = $("#caid").val();
							setTimeout("window.parent.location='"+ctx+"/contract/toEdit?fromPage=2&contractId="+caid+"';",1000);
						}else{
							setTimeout("window.parent.document.forms[0].submit();",1000);
						}
					}else if(data == '2'){
						window.top.iDialogMsg("提示","订单保存失败,合同被删除!");
						is_submit=false;
					}else{
						window.top.iDialogMsg("提示","上报订单失败!");
						is_submit = false;
					}
				}
			});
		}
	});
	
	$("#cancleBtn").click(function(){
		closeParentPubDivDialogIframe('add_order');
	});
	$("#cancleEditBtn").click(function(){
		closeParentPubDivDialogIframe('edit_order');
	});
	
	/**选择合同*/
	$("#contractSele").change(function(){
		var option = $(this).find("option:selected");
		if(option.val() == ''){
			$("#caid").val("");
			$("#caName").val("");
		}else{
			$("#caid").val(option.attr("caId"));
			$("#caName").val(option.attr("caName"));
		}
	});
	
	
	//删除附件
	$("a[id^=delfile_]").click(function(){
		var fileId = $(this).attr("id").split("_")[1];
		$("#file_up_"+fileId).remove();
		var delIds = $("#delFileIdsStr").val();
		if(delIds == null || delIds == ''){
			delIds = fileId;
		}else{
			delIds+=','+fileId;
		}
		$("#delFileIdsStr").val(delIds);
	});
	//下载附件
	$("a[id^=download_]").live("click",function(){
		var fileId = $(this).attr("id").split("_")[1];
		var form=$("<form>");//定义一个form表单
        var iframe=$('<iframe name="hideIframe" style="display:none"></iframe>')
		form.attr("style","display:none");
        form.attr("target","hideIframe");
		form.attr("method","post");
		form.attr("action",tsmUpLoadServiceUrl+ctx + "/fileupload/download");
		var input1=$("<input>");
		input1.attr("type","hidden");
		input1.attr("name","fileId");
		input1.attr("value",fileId);
		var input2=$("<input>");
		input2.attr("type","hidden");
		input2.attr("name","orgId");
		input2.attr("value",orgId);
		$("body").append(iframe);
		$("body").append(form);//将表单放置在web中
		form.append(input1);
		form.append(input2);
		form.submit();//表单提交 
	});
});

//JS版
//将传入数据转换为字符串,并清除字符串中非数字与.的字符
//按数字格式补全字符串
var getFloatStr = function(num){
    num += '';
    num = num.replace(/[^0-9|\.]/g, ''); //清除字符串中的非数字非.字符
    if(/^0+/.test(num)) //清除字符串开头的0
        num = num.replace(/^0+/, '');
    if(!/\./.test(num)) //为整数字符串在末尾添加.00
        num += '.00';
    if(/^\./.test(num)) //字符以.开头时,在开头添加0
        num = '0' + num;
    num += '00';        //在字符串末尾补零
    num = num.match(/\d+\.\d{2}/)[0];
    return num;
};

var getThreeFloatStr = function(num){
    num += '';
    num = num.replace(/[^0-9|\.]/g, ''); //清除字符串中的非数字非.字符
    if(/^0+/.test(num)) //清除字符串开头的0
        num = num.replace(/^0+/, '');
    if(!/\./.test(num)) //为整数字符串在末尾添加.00
        num += '.00';
    if(/^\./.test(num)) //字符以.开头时,在开头添加0
        num = '0' + num;
	num += '00';        //在字符串末尾补零
    num = num.match(/\d+\.\d{3}/)[0];
    return num;
};

var getIntStr = function(num){
	 num += '';
	 num = num.replace(/[^0-9|\.]/g, ''); //清除字符串中的非数字非.字符
	 if(/^0+/.test(num)) //清除字符串开头的0
        num = num.replace(/^0+/, '');
     if(!/\./.test(num)) //为整数字符串在末尾添加.00
        num += '.00';
     if(/^\./.test(num)) //字符以.开头时,在开头添加0
        num = '0' + num;
     num += '00';        //在字符串末尾补零
     num = num.match(/\d+\.\d{2}/)[0];
	 return Math.round(num);
};

var checkOrderProduct = function(){
	var flag = true;
	if($("tr[id^=pro_detail_]").length > 0){
		$("tr[id^=pro_detail_]").each(function(index,obj){
			var idx = $(obj).attr("id").split("_")[2];
			var pid = $("#pro_id_"+idx).val();
			var buynum = $("#pro_buynum_"+idx).val();
			if(pid == null || pid == ''){
				flag = false;
				window.top.iDialogMsg("提示","必须选择产品!");
				return false;
			}
			if(buynum == null || buynum == '' || parseFloat(buynum) <= 0){
				flag = false;
				window.top.iDialogMsg("提示","产品必须填写购买数量，且数量大于0!");
				return false;
			}
		});
	}else{
		window.top.iDialogMsg("提示","请添加产品信息!");
		flag = false;
	}
	return flag;
};
//重置上传
function resetFileInput(file){   
    file.after(file.clone().val(""));   
    file.remove();   
} 
//删除附件
function delfile(fileId){
	$("#file_li_"+fileId).remove();
	var fileIdsStr=$("#fileIdsStr").val();
	fileIdsStr = fileIdsStr.replace(fileId+",","");
	$("#fileIdsStr").val(fileIdsStr);
	$("#files").val("");
}