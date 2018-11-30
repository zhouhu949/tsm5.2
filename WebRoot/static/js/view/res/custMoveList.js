$(function(){
	loadData();
	$("#searchBtn").click(function(){
		loadData();
	});
	
	$("#nDate").dateRangePicker({
  		showShortcuts:false,
        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
    	var s_t = '';
    	var e_t = '';
    	if(obj.date1 != 'Invalid Date'){
    		$('#s_startDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    		s_t = moment(obj.date1).format('YY.MM.DD');
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#s_endDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    		e_t = moment(obj.date2).format('YY.MM.DD');
    	}
    	var s = $(this).parents('.select');
    	var dt=s.children("dt");
        var dd=s.children("dd");
        dt.html(s_t+'至'+e_t);
        $("#nDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
    });
	
	$("input[name=searchOwnerType]").iCheck({
	    radioClass: 'iradio_minimal-green'
	 });
	 
	 $("input[name=searchOwnerType]").on('ifClicked',function(){
	    	var nodes = $('#tt1').tree('getChecked');
	    	if(nodes.length > 0){
	    		$.each(nodes,function(index,obj){
	    			 $('#tt1').tree("uncheck",obj.target);
	    		});
	    	}
	  });
	  //树
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
							$("#ownerNameStr").val("操作人");
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

		//客户卡片
		$("a[id^=cardInfo_]").live("click",function(){
			var custId = $(this).attr("id").split("_")[1];
			var custName = $(this).attr("custName")||"客户卡片";
			window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName);
		});
		// 列表排序
});

function loadData(page){

    //var _param = $('#operationform').serialize();
    var _param = $('#queryForm').serialize();
    loaderAjax(_param,$("[name='showCount']").length>0? "page.showCount="+ $("[name='showCount']").val(): "");
    $('#checkAll').iCheck('uncheck');
}

function refreshPageData(page){
    var table = $(".ajax-table");
    var _param=table.attr("data-param")
    var pageStr="&";
    if(page){
        pageStr = "&page.currentPage="+page.currentPage+"&page.totalResult="+page.totalResult+"&page.showCount="+page.showCount;
    }
    loaderAjax(_param,pageStr);
}

function loaderAjax(_param,pageStr){
    var table = $(".ajax-table");
    table.attr("data-param",_param);
	var _url = ctx+'/res/cust/custMoveListData';
	searchingDataTip();
	var timeout = setTimeout(function(){
		$(".tip_search_text").hide();
		$(".tip_search_error").text("网络繁忙，请耐心等候或重新查询！");
		$(".tip_search_error").show();
	},10000);
	$.ajax({
		type:"get",
		url	:_url,
        data:_param+"&"+pageStr,
		success	:function(data){
			loadCustMoveList(data.list,data.account);
			$(".tip_search_data").hide();
			clearTimeout(timeout);
			pa.load('new_page',data.item.page,refreshPageData,$('#t1'));
		},
		error:function(data){
			clearTimeout(timeout);
			$(".tip_search_text").hide();
			$(".tip_search_error").text("网络异常，请重新查询！");
			$(".tip_search_error").show();
		}
	});
}

function loadCustMoveList(list,account){
	var html='';
	for(var i in list){
		var move = list[i];
		html+=
			"<tr class='"+(i%2==0?'':'sty-bgcolor-b')+"'>" +
					"<td style='width:30px;'>" +
						"<div class='overflow_hidden w30 link'>" ;
							if(move.ownerAcc == account){
								if(isState == 1){
									html+="<a href='javascript:;' id='cardInfo_"+move.custId+"' custName='"+AjaxQueryData.emptyTransfer(move.custName)+"' class='icon-cust-card' title='客户卡片'></a>" ;
								}else{
									html+="<a href='javascript:;' id='cardInfo_"+move.custId+"' custName='"+(AjaxQueryData.emptyTransfer(move.company) == '' ? AjaxQueryData.emptyTransfer(move.custName) : AjaxQueryData.emptyTransfer(move.company))+"' class='icon-cust-card' title='客户卡片'></a>" ;
								}
							}
					html+=	"</div>" +
					"</td>" +
					"<td hidevalue='"+move.startDate+"'><div class='overflow_hidden w100' title='"+AjaxQueryData.emptyTransfer(move.startDate)+"'>"+AjaxQueryData.emptyTransfer(move.endDate)+"</div></td>" +
					"<td><div class='overflow_hidden w70' title='"+AjaxQueryData.emptyTransfer(move.custName)+"'>"+AjaxQueryData.emptyTransfer(move.custName)+"</div></td>" ;
					if(isState != 1){
						html+="<td><div class='overflow_hidden w120' title='"+AjaxQueryData.emptyTransfer(move.company)+"'>"+AjaxQueryData.emptyTransfer(move.company)+"</div></td>" ;
					}
					html+=
					"<td><div class='overflow_hidden w70' title='"+move.custTypeName+"'>"+move.custTypeName+"</div></td>" +
					"<td><div phone='tel' class='overflow_hidden w100' title='"+(AjaxQueryData.emptyTransfer(move.mobilephone) == '' ?  AjaxQueryData.emptyTransfer(move.telphone) : move.mobilephone)+"'>"+(AjaxQueryData.emptyTransfer(move.mobilephone) == '' ?  AjaxQueryData.emptyTransfer(move.telphone) : move.mobilephone)+"</div></td>" +
					"<td><div class='overflow_hidden w120' title='"+AjaxQueryData.emptyTransfer(move.optionName)+"'>"+AjaxQueryData.emptyTransfer(move.optionName)+"</div></td>" +
					"<td><div class='overflow_hidden w70' title='"+(move.transferType == 1 ? '转入' : '转出')+"'>"+(move.transferType == 1 ? '转入' : '转出')+"</div></td>" +
					"<td><div class='overflow_hidden w100' title='"+AjaxQueryData.emptyTransfer(move.userName)+"'>"+AjaxQueryData.emptyTransfer(move.userName)+"</div></td>" +
					"<td><div class='overflow_hidden w100' title='"+AjaxQueryData.emptyTransfer(move.ownerName)+"'>"+AjaxQueryData.emptyTransfer(move.ownerName)+"</div></td>" +
					"<td><div class='overflow_hidden w100px' title='"+getTransferType(move)+"'>"+getTransferType(move)+"</div></td>" +
					"<td><div class='overflow_hidden w50' title='"+AjaxQueryData.emptyTransfer(move.optoerName)+"'>"+AjaxQueryData.emptyTransfer(move.optoerName)+"</div></td>" +
					"<td><div class='overflow_hidden w120' title='"+AjaxQueryData.emptyTransfer(move.reason)+"'>"+AjaxQueryData.emptyTransfer(move.reason)+"</div></td>"+
			"</tr>";
	}
	$(".ajax-table>tbody").html(html);
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
}

function getTransferType(move){
	var typeName = '';
	if(move.transferType == 2){
		if(move.type == 11)
			typeName = '系统到期';
		else if(move.type == 12)
			typeName = '主动放弃';
		else if(move.type == 14)
			typeName = '主动转移('+AjaxQueryData.emptyTransfer(move.userName)+')';
	}
	return typeName;
}

var setNDate = function(i){
	$('#s_startDate').val('');
	$('#s_endDate').val('');
	$("#nDateType").val(i);
};

var seleTree=function(){
	var nodes = $('#tt1').tree('getChecked', 'checked');
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
};

var unCheckTree=function(){
	var nodes = $('#tt1').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#tt1').tree("uncheck",obj.target);
	});
};

function searchingDataTip(){
	var table = $(".ajax-table");
	var tip_search_data = $(".tip_search_data");
	var tip_search_text = tip_search_data.find(".tip_search_text");
	var tip_search_error = tip_search_data.find(".tip_search_error");
	var table_height = table.height();
	var margin_top = (table_height-tip_search_text.height())/2;
	tip_search_data.height(table_height);
	tip_search_text.css("margin-top",margin_top);
	tip_search_error.css("margin-top",margin_top);
	tip_search_data.show();
	tip_search_error.hide();
	tip_search_text.show();
}