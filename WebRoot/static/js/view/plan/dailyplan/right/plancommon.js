$(function(){
	//模糊处理手机、电话号码
	var idReplaceWord = $("#idReplaceWord").val();
	if(idReplaceWord==1){
		$("[phone=tel]").each(function(idx,obj){
			var phone = $(obj).text();
			if(phone != null && phone != ''){
				replaceWord(phone,$(obj));
				replaceTitleWord(phone,$(obj));
			}
		});
	 }
	//点击表格行
	$('.ajax-table tr').live('click',function(e){
		 e.stopPropagation();
		 var $this = $(this);
		 if($this.hasClass('td-hover') == false){
	    		$(".td-hover").removeClass("td-hover");
	    		$(".tr-hover").removeClass("tr-hover");
	    		$this.addClass("tr-hover");
	    	}
		 if($this.hasClass("disabled")){
			 return false;
		 }
		 var followId = $this.data("followid");
		 var custId = $this.data("custid");
		 if(custId || followId){
			 loadRecordRight(custId,followId);
		 }
	});

	//淘客户
	$("a[id^=tao_]").live('click',function(e){
		e.stopPropagation();
		var resId = $(this).attr("id").split("_")[1];
		var filterType = $(this).attr("filterType");
		if(filterType != '2'){
			filterType = '1';
		}
		window.top.addTab(ctx+"/res/tao/taoMyRes?resId="+resId+"&isFromOtherPage=0&pool="+filterType,"淘客户");
	});
	$("a[id^=dele_]").live('click',function(e){
		e.stopPropagation();
		var $this = $(this);
		if($this.hasClass("img-gray")){
			return false;
		}
		var resCustId = $this.attr("id").replace("dele_","");
		var data = {ids:resCustId};
		if(resCustId != null && resCustId != ""){
			queDivDialog("res_remove_cust","是否放弃客户？",function(){
				$.ajax({
					url:ctx+'/res/cust/removeCust',
					type:'post',
					data:data,
					dataType:'json',
					error:function(){alert('网络异常，请稍后再试！')},
					success:function(data){
						if(data == 1){
							window.top.iDialogMsg("提示",'放弃客户成功！');
							refreshPageData(pa.pageParameter());
						}else{
							window.top.iDialogMsg("提示",'放弃客户失败！');
						}
					}
				});
		});
	}
	});
	//右侧跳转客户卡片
	$("a[id^=cardInfo_]").live("click",function(e){
		e.stopPropagation();
		var $this = $(this);
		if($this.hasClass("img-gray")){
			return false;
		}
		var custId = $this.attr("id").split("_")[1];
		if($this.attr("company")){
			var custName = $this.attr("company");
		}else{
			var custName = $this.attr("custName")||"客户卡片";
		}
		window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName);
	});
	//签约
	$("a[id^=sign_]").live('click',function(e){
		e.stopPropagation();
		var $this = $(this);
		if($this.hasClass("img-gray")){
			return false;
		}
		var custId = $this.attr("id").split("_")[1];
		var signSetting = $("#signSetting").val();
		var module = $this.attr("module");
		if(signSetting == '1' || !window.top.shrioAuthObj("mySignCust_addContract")){
			pubDivDialog("confirm_sign","是否确认签约？",function(){
				$.ajax({
					url:ctx+"/contract/sign",
					type:"post",
					data:{custId:custId,"module":module},
					dataType:"json",
					error:function(){},
					success:function(data){
						if(data == "1"){
							window.top.iDialogMsg("提示","签约成功!");
							refreshPageData(pa.pageParameter());
						}else{
							window.top.iDialogMsg("提示","签约失败!");
						}
					}
				});
			});
		}else{
			window.top.addTab(ctx+"/contract/toAdd?custId="+custId+"&module="+module+"&t="+Date.parse(new Date()),"新增合同");
		}
	});

	//取消签约
	$("a[id^=unsign_]").live('click',function(e){
		e.stopPropagation();
		var custId = $(this).attr("id").split("_")[1];
		pubDivDialog("confirm_unsign","是否确认取消签约？",function(){
			$.ajax({
				url:ctx+"/contract/unSign",
				type:"post",
				data:{custId:custId},
				dataType:"json",
				error:function(){},
				success:function(data){
					if(data == "1"){
						window.top.iDialogMsg("提示","取消签约成功!");
						refreshPageData(pa.pageParameter());
					}else{
						window.top.iDialogMsg("提示","取消签约失败!");
					}
				}
			});
		});
	});
	//新增订单
	$("a[id^=addOrder_]").live('click',function(e){
		e.stopPropagation();
		var id = $(this).attr("id").split("_")[1];
		var module = $(this).attr("module");
		pubDivShowIframe("add_order",ctx+"/contract/order/addOrder?fromPage=3&module="+module+"&custId="+id,"新增订单",900,500);
	});
	//新增合同
	$("a[id^=addContract_]").live('click',function(e){
		e.stopPropagation();
		var custId = $(this).attr("id").split("_")[1];
		window.top.addTab(ctx+"/contract/toAdd?custId="+custId+"&t="+Date.parse(new Date()),"新增合同");
	});
	//取消重点关注
	$("a[id^=cancleMajor_]").live('click',function(e){
		e.stopPropagation();
		var $this = $(this);
		if($this.hasClass("img-gray")){
			return false;
		}
		var custId = $this.attr("id").split("_")[1];
		$.ajax({
			url:ctx+'/res/tao/setMajor',
			type:'post',
			data:{custId:custId,isMajar:0},
			dataType:'json',
			error:function(){},
			success:function(data){
				if(data == '0'){
					window.top.iDialogMsg("提示","取消关注成功!");
					refreshPageData(pa.pageParameter());
				}else{
					window.top.iDialogMsg("提示","取消关注失败!");
				}
			}
		})
	});
	//重点关注
	$("a[id^=setMajor_]").live('click',function(e){
		e.stopPropagation();
		var $this = $(this);
		if($this.hasClass("img-gray")){
			return false;
		}
		var custId = $this.attr("id").split("_")[1];
		$.ajax({
			url:ctx+'/res/tao/setMajor',
			type:'post',
			data:{custId:custId,isMajar:1},
			dataType:'json',
			error:function(){},
			success:function(data){
				if(data == '0'){
					window.top.iDialogMsg("提示","重点关注成功!");
					refreshPageData(pa.pageParameter());
				}else{
					window.top.iDialogMsg("提示","重点关注失败!");
				}
			}
		})
	});
})
//右侧跟进信息加载
function loadRecordRight(reCustId,followId){
	 $.ajax({
			url: ctx+'/cust/custFollow/List/dayPlanRight',
			type:'get',
			data:{'reCustId':reCustId,'followId':followId,'showCount':'0'},
			dataType:'html',
			error:function(){alert("网络异常，请稍后再操作！")},
			success:function(data){
				$("#list_right").html(data);
			}
		});
}