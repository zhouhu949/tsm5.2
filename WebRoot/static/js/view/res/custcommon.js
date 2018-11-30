$(function(){

	$("span[sort=true]").click(function(){
		var $this = $(this);
		var sortColumn = $this.attr("sortColumn");
		var sortType = $this.attr("sortType");
		sortType = sortType || 'desc';
		$("span[sort=true]").each(function(index,obj){
			$(obj).find("span:eq(0)").attr("class","td_sort_asc");
			$(obj).attr("sortType","");
		});
		$this.find("span:eq(0)").attr("class","td_sort_"+sortType);
		$this.attr("sortType",sortType == 'desc' ? 'asc' : 'desc');
		$("#orderKey").val(sortColumn+" "+sortType);
		loadData();
	});

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
	/*表单优化*/
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });

   $("input[name=searchOwnerType]").iCheck({
    	radioClass: 'iradio_minimal'
    });
    $("input[name=searchOwnerType]").on('ifClicked',function(){
    	var nodes = $('#tt1').tree('getChecked');
    	if(nodes.length > 0){
    		$.each(nodes,function(index,obj){
    			 $('#tt1').tree("uncheck",obj.target);
    		});
    	}
    });
    /*下拉框部分*/
    $('.mail').live('click',function(){
        $(this).find('.drop').fadeToggle();
    });

    $('.hyx-cm-new').find('.load').live('mouseover',function(){
        $(this).find('.drop').fadeIn(1);
    });
    $('.hyx-cm-new').find('.load').live('mouseleave',function(){
        $(this).find('.drop').fadeOut(1);
    });
    //编辑
    $("#editCustBtn").live('click',function(){
    	var custId = $(this).attr("custid");
    	pubDivShowIframe('edit_'+custId,ctx+'/res/cust/toEditRes?custId='+custId,'信息编辑',500,570);
    });

    //打电话
    $("a[id^=call_]").live('click',function(){
    	var phone = $(this).attr("id").split("_")[1];
    	var custId = $(this).attr("custId");
    	var custName = $(this).attr("custName");
    	var custType = $(this).attr("custType");
    	var custState = $(this).attr("custState");
    	var define1 = $(this).attr("define1");
    	var lastOptionId = $(this).attr("lastOptionId");
    	var lastOptionName = $(this).attr("lastOptionName");
    	var define3 = $(this).attr("define3");
    	var arrays = new Array();
    	arrays.push("\"custId\":\""+custId+"\"");
    	arrays.push("\"custName\":\""+custName+"\"");
    	arrays.push("\"custType\":\""+custType+"\"");
    	arrays.push("\"custState\":\""+custState+"\"");
    	arrays.push("\"define1\":\""+define1+"\"");
    	if(lastOptionId != null && lastOptionId != ''){
    		arrays.push("\"saleProcessId\":\""+lastOptionId+"\"");
    		arrays.push("\"saleProcessName\":\""+lastOptionName+"\"");
    	}
    	if(define3 != null && define3 != ''){
    		arrays.push("\"define3\":\""+define3+"\"");
    	}
    	window.top.custCallPhone(phone,arrays,custId);
    });
	//点击表格行
	$('tr[id^=rightView_]').live('click',function(){
		var resCustId = $(this).attr('id').split('_')[1];
		//加载右侧
		getAjaxRightInfo(resCustId);
	});

	$(".com-moTag").find("a").click(function(){
		$("#noteType").val($(this).attr("nt"));
		if($('#days').length > 0){
			$('#days').val('');
		}
		try{
			$(this).parent().find(".e-hover").removeClass("e-hover");
			$(this).addClass("e-hover");
			loadData();
		}catch(e){
			document.forms[0].submit();
		}
	});

	if($("#tt1").length > 0){
		$("#tt1").tree({
			url:ctx+"/orgGroup/get_group_user_json",
			checkbox:true,
			onLoadSuccess:function(node,data){
				$("#tt1").tree("collapseAll");
				for(var i=0;i<data.length;i++){
					$("#tt1").tree("expand",data[i].target);
				}
				var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
				var oas = $("#ownerAccsStr").val();
				if(searchOwnerType == '1'){
					$("#ownerNameStr").val("查看全部");
				}else if(searchOwnerType == '2'){
					$("#ownerNameStr").val("查看自己");
				}else if(oas != null && oas != ''){
					var text='';
					$.each(oas.split(','),function(index,obj){
						var n = $("#tt1").tree("find",obj);
						if(n != null){
							text+=n.text+',';
							$("#tt1").tree("check",n.target).tree("expandTo",n.target);
						}
					});
					if(text != ''){
						text=text.substring(0,text.length-1);
						$("#ownerNameStr").val(text);
					}else{
						$("#ownerNameStr").val("所有者");
					}
				}
			},
			onCheck:function(node,isCheck){
				var nodes = $('#tt1').tree('getChecked');
				if(nodes.length > 0){
					$("input[name=searchOwnerType]").iCheck('uncheck');
				}else if($("input[name=searchOwnerType]:checked").length == 0){
					$("input[name=searchOwnerType]:eq(0)").iCheck('check');
				}
			}
		});
	}


	//共有者
	if($("#tt2").length > 0){
		$("#tt2").tree({
			url:ctx+"/orgGroup/get_all_sale_user_json",
			checkbox:true,
			onLoadSuccess:function(node,data){
				$("#tt2").tree("collapseAll");
				for(var i=0;i<data.length;i++){
					$("#tt2").tree("expand",data[i].target);
				}
			}
		});
	}
	
	if($("#tt3").length > 0){
		$("#tt3").tree({
			url:ctx+"/orgGroup/get_all_service_user_json",
			checkbox:true,
			onLoadSuccess:function(node,data){
				$("#tt3").tree("collapseAll");
				for(var i=0;i<data.length;i++){
					$("#tt3").tree("expand",data[i].target);
				}
			}
		});
	}

	//淘客户
	$("a[id^=tao_]").live('click',function(){
		var resId = $(this).attr("id").split("_")[1];
		var filterType = $(this).attr("filterType");
		if(filterType != '2'){
			filterType = '1';
		}
		window.top.addTab(ctx+"/res/tao/taoMyRes?resId="+resId+"&isFromOtherPage=0&pool="+filterType,"淘客户");
	});
	//客户跟进
	$("a[id^=follow_]").live('click',function(){
		var custId = $(this).attr("id").split("_")[1];
		var custType = $(this).attr("custType");
		var param = $("form").serialize();
		window.top.addTab(ctx+"/cust/custFollow/custFollowPage.do?"+param+"&custId="+custId+"&custListType="+custType+"&v="+new Date().getTime(),"跟进");
	});
	//右侧跳转客户卡片
	$("a[id^=cardInfo_]").live("click",function(){
		var custId = $(this).attr("id").split("_")[1];
		var custName = $(this).attr("custName")||"客户卡片";
		window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName);
	});
	//修改资源
	$("a[id^=editInfo_]").live('click',function(){
		var custId = $(this).attr("id").split("_")[1];
		pubDivShowIframe('edit_'+custId,ctx+'/res/cust/toEditRes?custId='+custId,'信息编辑',500,570);
	});
	//签约
	$("a[id^=sign_]").live('click',function(){
		var custId = $(this).attr("id").split("_")[1];
		var signSetting = $(this).attr("signSetting");
		var module = $(this).attr("module");
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
	$("a[id^=unsign_]").live('click',function(){
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
	$("a[id^=addOrder_]").live('click',function(){
		var id = $(this).attr("id").split("_")[1];
		var module = $(this).attr("module");
		pubDivShowIframe("add_order",ctx+"/contract/order/addOrder?fromPage=3&module="+module+"&custId="+id,"新增订单",900,500);
	});
	//新增合同
	$("a[id^=addContract_]").live('click',function(){
		var custId = $(this).attr("id").split("_")[1];
		window.top.addTab(ctx+"/contract/toAdd?custId="+custId+"&t="+Date.parse(new Date()),"新增合同");
	});
	//取消重点关注
	$("a[id^=cancleMajor_]").live('click',function(){
		var custId = $(this).attr("id").split("_")[1];
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
	$("a[id^=setMajor_]").live('click',function(){
		var custId = $(this).attr("id").split("_")[1];
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

window.onload=function(){
	var isState = $("#isState").val();
	var html = '<div class="hyx-cfu-card hyx-cfu-card-none fl_l">';
	if(isState == 1){
		html+='	<div class="tit fl_l">'
			+'		<span title="" class="sp"></span>'
			+'		<div class="mail icon-conte-list img-gray" title="通讯录"></div>'
			+'      <a href="javascript:;" class="edit demoBtn_l icon-edit img-gray fl_r" title="编辑"></a>'
			+'	</div>'
			+'	<p class="list fl_l"><span>联系人：</span><label title=""></label></p>'
			+'	<p class="list fl_l"><span>联系电话：</span><label title=""></label></p>'
			+'	<p class="list fl_l"><span>邮箱：</span><label title=""></label></p>'
			+'	<p class="list fl_l"><span>联系地址：</span><label title=""></label></p>'
			+'</div>'
			+'<div class="hyx-cfu-tab">'
			+'	<ul class="tab-list clearfix">'
			+'		<li id="follow_" class="select li_first">跟进记录</li>'
			+'		<li id="callLog_" >通话记录</li>'
			+'		<li id="reView_" class="li_last">点评信息</li>'
			+'	</ul>'
			+'</div>'
			+'<div class="hyx-wlm-cent-timeline hyx-cfu-timeline">'
			+'      <div class="none-bg">'
			+'			<p class="tit_none">暂无联系记录！</p>'
			+'      </div>'
			+'</div>';
	}else{
		html+='	<div class="tit fl_l">'
			+'		<span title="" class="sp"></span>'
			+'      <a href="javascript:;" class="edit demoBtn_l icon-edit img-gray fl_r" title="编辑"></a>'
			+'	</div>'
			+'	<p class="list fl_l"><span>性别：</span><label title=""></label></p>'
			+'	<p class="list fl_l"><span>联系电话：</span><label title=""></label></p>'
			+'	<p class="list fl_l"><span>邮箱：</span><label title=""></label></p>'
			+'	<p class="list fl_l"><span>联系地址：</span><label title=""></label></p>'
			+'</div>'
			+'<div class="hyx-cfu-tab">'
			+'	<ul class="tab-list clearfix">'
			+'		<li id="follow_" class="select li_first">跟进记录</li>'
			+'		<li id="callLog_" >通话记录</li>'
			+'		<li id="reView_" class="li_last">点评信息</li>'
			+'	</ul>'
			+'</div>'
			+'<div class="hyx-wlm-cent-timeline hyx-cfu-timeline">'
			+'    <div class="none-bg">'
			+'		<p class="tit_none">暂无联系记录！</p>'
			+'    </div>'
			+'</div>';
	}
	$("#custRight").html(html);

	var firstRes  = $('tr[id^=rightView_]:first');
	if(firstRes.length > 0){
		var back_color_a = firstRes.css('background-color');
        if(firstRes.find('td').hasClass('td-hover') == false){
        	firstRes.find('td').removeClass('td-link');
        	firstRes.find('td').addClass('td-hover');
        	firstRes.find('td:first').css({'border-left-color':'#469bff'});
        	firstRes.find('td:last').css({'border-right-color':'#469bff'});
        	firstRes.siblings('tr').find('td').removeClass('td-hover');
        	firstRes.siblings('tr').find('td:first').css({'border-left-color':back_color_a});
        	firstRes.siblings('tr').find('td:last').css({'border-right-color':back_color_a});
        }
		var resCustId = firstRes.attr('id').split('_')[1];
		//加载右侧
		getAjaxRightInfo(resCustId);
	}
}

var getAjaxRightInfo=function(custId){
	$.post(ctx+'/res/cust/custRight',{resCustId:custId},function(data){
		$("#custRight").html(data);
		$('.hyx-cm-new').find('.load').each(function(){
	    	if($(this).find('.sp').text().length >= 23){
		    	$(this).find('.sp').text($(this).find('.sp').text().substr(0,23) + '...');
		    }
	    });
		if($("#idReplaceWord").length > 0){
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
		}
	},'html');
}

var seleTree=function(){
	var nodes = $('#tt1').tree('getChecked', 'checked');
	var accs = "";
	var names = "";
	var userIds = "";
	$.each(nodes,function(index,obj){
		var type = obj.attributes.type;
		if(type == "M"){
			accs+=obj.id+",";
			names+=obj.text+",";
			userIds+=obj.user_id+",";
		}
	});
	if(accs != ""){
		accs=accs.substring(0,accs.length-1);
		names=names.substring(0,names.length-1);
		userIds=userIds.substring(0,userIds.length-1);
		$("#osType").val("3");
	}else{
		var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
		if(searchOwnerType == '1'){
			names="查看全部";
		}else{
			names="查看自己";
		}
		$("#osType").val(searchOwnerType);
	}
	$("#ownerAccsStr").val(accs);
	$("#ownerNameStr").val(names);
	if($("#ownerUserIdsStr").length > 0){
		$("#ownerUserIdsStr").val(userIds);
	}

}

var unCheckTree=function(tid){
	tid=tid||'tt1';
	var nodes = $('#'+tid).tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#'+tid).tree("uncheck",obj.target);
	});
}

var seleCommonAccTree=function(){
	var nodes = $('#tt2').tree('getChecked', 'checked');
	var accs = "";
	var names = "";
	$.each(nodes,function(index,obj){
		var type = obj.attributes.type;
		if(type == "M"){
			accs+=obj.id+",";
			names+=obj.text+",";
		}
	});
	if(accs != ""){
		accs=accs.substring(0,accs.length-1);
		names=names.substring(0,names.length-1);
	}else{
		names="共有者";
	}
	$("#commonAccsStr").val(accs);
	$("#commonOwnerNameStr").val(names);
}

var seleServiceAccTree=function(){
	var nodes = $('#tt3').tree('getChecked', 'checked');
	var accs = "";
	var names = "";
	$.each(nodes,function(index,obj){
		var type = obj.attributes.type;
		if(type == "M"){
			accs+=obj.id+",";
			names+=obj.text+",";
		}
	});
	if(accs != ""){
		accs=accs.substring(0,accs.length-1);
		names=names.substring(0,names.length-1);
	}else{
		names="客服人员";
	}
	$("#serviceAccStr").val(accs);
	$("#serviceAccNameStr").val(names);
}


