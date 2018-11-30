$(function(){
	//新增合同
	var is_submit = false;
	$("#addBtn").click(function(){
		if(checkForm() && !is_submit){
			is_submit = true;
			$("#cform").ajaxSubmit({
				dataType:'html',
				error:function(){is_submit = false;},
				success:function(data){
					if(data != '-1'){
						var cid = data.split("_")[0];
						var rt = data.split("_")[1];
						$("#addBtn").hide();
						window.top.isFresh =1;
//						if(window.top.shrioAuthObj("contract_addOrder")){
//							pubDivDialog("confirm_add_order",rt == "1" ? "签约成功，是否立刻新增订单？" :"新增合同成功，是否立刻新增订单？",function(){
//								var custId = $("#custId").val();
//								pubDivShowIframe("add_order",ctx+"/contract/order/addOrder?fromPage=2&contractId="+cid+"&custId="+custId,"新增订单",900,500);
//							},function(){
//								
//								window.top.$("#centerTabs").tabs('close','新增合同');
//							});
//						}else{
							window.top.iDialogMsg("提示",rt == "1" ? "签约成功" : "新增合同成功!");
							window.top.$("#centerTabs").tabs('close','新增合同');
//						}
					}else{
						window.top.iDialogMsg("提示","新增合同失败!");
						is_submit = false;
					}
				}
			});
		}
	});
	//编辑合同
	$("#editBtn").click(function(){
		if(checkForm() && !is_submit){
			is_submit = true;
			$("#cform").ajaxSubmit({
				dataType:'json',
				error:function(){is_submit=false;},
				success:function(data){
					if(data != '-1'){
						window.top.iDialogMsg("提示","修改合同成功!");
						setTimeout("window.location.href=window.location.href",1000);
					}else{
						window.top.iDialogMsg("提示","修改合同失败!");
						is_submit=false;
					}
				}
			});
		}
	});
	//上传附件
	$("#file").live('change',function(){
		var sp = $(this).val();
		var ptn = /\.jpg$|\.jpeg$|\.docx$|\.doc$|\.pdf$|\.png$/i;
		if(!ptn.test(sp)){
			window.top.iDialogMsg("提示","只允许上传word、pdf、jpg、png格式的文件！");
			return;
		} 
		$.ajaxFileUpload({
			 url: tsmUpLoadServiceUrl+ctx + '/fileupload/upload',
	         secureuri: false,
	         fileElementId: 'file',
	         dataType: 'text/html',
	         data: {
	            type:'2',
	            length:10485760,
	            orgId:orgId,
	            account:account,
	            userId:userId
	         },
	         success: function(json, status){
	        	 var data = JSON.parse(json);
            	 if (data.code == 1) {
            		 var fileIdsStr=$("#fileIdsStr").val();
            		 $("#fileIdsStr").val(fileIdsStr+data.fileId+",");
            		 $("#fileList").append("<li id=\"file_li_"+data.fileId+"\"><label class=\"name\">"+data.fileName+"</label><a href=\"###\" onclick=\"delfile('"+data.fileId+"')\" class=\"link\">删除</a></li>");
            		 resetFileInput($("#file"));
            	 }else{
            		 window.top.iDialogMsg("提示",data.message);
            		 resetFileInput($("#file"));
            	 }
             }
		 });
	});
	//新增合同
	$("#addOrderBtn").click(function(){
		var contractId = $("input[name=id]").val();
		var fromPage = $("#fromPage").val();
		var custId = $("#custId").val();
		pubDivShowIframe("add_order",ctx+"/contract/order/addOrder?fromPage="+fromPage+"&contractId="+contractId+"&custId="+custId,"新增订单",900,500);
	});
	//编辑订单
	$("a[id^=uporder_]").click(function(){
		var orderId = $(this).attr("id").split("_")[1];
		var fromPage = $("#fromPage").val();
		pubDivShowIframe("edit_order",ctx+"/contract/order/toEdit?fromPage="+fromPage+"&orderId="+orderId,"编辑订单",900,500);
	});
	//查看订单
	$("a[id^=view_]").click(function(){
		var orderId = $(this).attr("id").split("_")[1];
		pubDivShowIframe('view_order',ctx+'/contract/order/orderView?orderId='+orderId,'订单详情',885,610);
	});
	//上报
	$("a[id^=report_]").click(function(){
		var orderId = $(this).attr("id").split("_")[1];
		pubDivDialog("confirm_report_order","是否确认上报？",function(){
			$.ajax({
				url:ctx+"/contract/order/reportOrder",
				type:"post",
				data:{orderId:orderId},
				dataType:"json",
				error:function(){},
				success:function(data){
					if(data == "1"){
						window.top.iDialogMsg("提示","上报成功!");
						setTimeout("window.location.reload(true)",1000);
					}else{
						window.top.iDialogMsg("提示","上报失败!");
					}
				}
			})
		});
	});
	//撤回
	$("a[id^=reback_]").click(function(){
		var orderId = $(this).attr("id").split("_")[1];
		pubDivDialog("confirm_report_order","是否确认撤回？",function(){
			$.ajax({
				url:ctx+"/contract/order/rebackOrder",
				type:"post",
				data:{orderId:orderId},
				dataType:"json",
				error:function(){},
				success:function(data){
					if(data == "1"){
						window.top.iDialogMsg("提示","撤回成功!");
						setTimeout("window.location.reload(true)",1000);
					}else{
						window.top.iDialogMsg("提示","撤回失败!");
					}
				}
			})
		});
	});
	var is_submit = false;
	//作废
	$("a[id^=cancelled_]").click(function(){
		var orderId = $(this).attr("id").split("_")[1];
		pubDivDialog("confirm_report_order","是否确认作废？",function(){
			if(!is_submit){
				is_submit=true;
				$.ajax({
					url:ctx+"/contract/order/cancelledOrder",
					type:"post",
					data:{orderId:orderId},
					dataType:"json",
					error:function(){is_submit=false;},
					success:function(data){
						if(data == "1"){
							window.top.iDialogMsg("提示","作废成功!");
							setTimeout("window.location.reload(true)",1000);
						}else{
							window.top.iDialogMsg("提示","作废失败!");
							is_submit=false;
						}
					}
				})
			}
		});
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