$(function(){
	loadData();
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
	var _url = ctx+'/res/cust/myAllTypeCustsData';
	searchingDataTip();
	var timeout = setTimeout(function(){
		$(".tip_search_text").hide();
		$(".tip_search_error").text("网络繁忙，请耐心等候或重新查询！");
		$(".tip_search_error").show();
	},10000);
	$.ajax({
		type:"get",
		url:_url,
        data:_param+"&"+pageStr,
		success:function(data){
			loadRestsList(data);
			if(data.list == null || data.list.length == 0){
				clearRight();
			}else{
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
					getAjaxRightInfo(data.list[0].resCustId);
				}
			}
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

/**生成数据*/
function loadRestsList(data){
	var html="";
	for(var i = 0;i<data.list.length;i++){
		var res = data.list[i];
		html+=
			"<tr id='rightView_"+AjaxQueryData.emptyTransfer(res.resCustId)+"' class='"+(i%2==0?'':'sty-bgcolor-b')+"'>" +
				"<td style='width:30px;'><div class='overflow_hidden w30 skin-minimal'><input type='checkbox' name='check_' status='1' owner='"+AjaxQueryData.emptyTransfer(res.ownerAcc)+"' name_tel='"+AjaxQueryData.emptyTransfer(data.isState == 1 ? res.mainLinkman : res.name)+"|"+AjaxQueryData.emptyTransfer(res.telphone)+"' name_mobile='"+AjaxQueryData.emptyTransfer(data.isState == 1 ? res.mainLinkman : res.name)+"|"+AjaxQueryData.emptyTransfer(res.mobilephone)+"' mobile='"+AjaxQueryData.emptyTransfer(res.mobilephone)+"' telPhone='"+AjaxQueryData.emptyTransfer(res.telphone)+"' id='chk_"+AjaxQueryData.emptyTransfer(res.resCustId)+"' /></div></td>" +
				"<td style='width:100px;'>" +
					"<div class='overflow_hidden w100 link'>" +
					getResOperate(res,data.serverDay,data.loginAcc,data.signSetting,data.adminSignAuth,data.isState) +
					"</div>" +
				"</td>";
				if(data.isState == 0){
					html+=
					"<td><div class='overflow_hidden w110' title='"+AjaxQueryData.emptyTransfer(res.company)+"'>"+AjaxQueryData.emptyTransfer(res.company)+"</div></td>" + 
					"<td><div class='overflow_hidden w110' title='"+AjaxQueryData.emptyTransfer(res.name)+"'>"+AjaxQueryData.emptyTransfer(res.name)+"</div></td>" ;
				}else{
					html+=
						"<td><div class='overflow_hidden w110' title='"+AjaxQueryData.emptyTransfer(res.name)+"'>"+AjaxQueryData.emptyTransfer(res.name)+"</div></td>" + 
					"<td><div class='overflow_hidden w110' title='"+AjaxQueryData.emptyTransfer(res.mainLinkman)+"'>"+AjaxQueryData.emptyTransfer(res.mainLinkman)+"</div></td>" ;
				}
				html+=
				"<td><div class='overflow_hidden w70' title='"+AjaxQueryData.emptyTransfer(res.groupName )+"'>"+AjaxQueryData.emptyTransfer(res.groupName )+"</div></td>" +
				"<td><div class='overflow_hidden w70' title='"+getTypeName(AjaxQueryData.emptyTransfer(res.custType ))+"'>"+getTypeName(AjaxQueryData.emptyTransfer(res.custType ))+"</div></td>" +
				"<td><div class='overflow_hidden w100' title='"+AjaxQueryData.emptyTransfer(res.saleProcessName )+"'>"+AjaxQueryData.emptyTransfer(res.saleProcessName )+"</div></td>" +
				"<td hidevalue='"+AjaxQueryData.emptyTransfer(res.startDate )+"'><div class='overflow_hidden w100' title='"+AjaxQueryData.emptyTransfer(res.startDate )+"'>"+AjaxQueryData.emptyTransfer(res.endDate )+"</div></td>" +
				"<td hidevalue='"+AjaxQueryData.emptyTransfer(res.startActionDate )+"'><div class='overflow_hidden w100' title='"+AjaxQueryData.emptyTransfer(res.startActionDate )+"'>"+AjaxQueryData.emptyTransfer(res.endActionDate )+"</div></td>" +
				"<td hidevalue='"+AjaxQueryData.emptyTransfer(res.pstartDate )+"'><div class='overflow_hidden w100' title='"+AjaxQueryData.emptyTransfer(res.pstartDate )+"'>"+AjaxQueryData.emptyTransfer(res.pendDate )+"</div></td>" + 
				"<td><div class='overflow_hidden w50' title='"+getSourceName(res.source )+"'>"+getSourceName(res.source )+"</div></td>" +
				"<td><div class='overflow_hidden w50' title='"+AjaxQueryData.emptyTransfer(res.ownerName )+"'>"+AjaxQueryData.emptyTransfer(res.ownerName )+"</div></td>" +
				"<td><div class='overflow_hidden w50' phone='tel' title='"+AjaxQueryData.emptyTransfer(res.mobilephone||res.telphone )+"'>"+AjaxQueryData.emptyTransfer(res.mobilephone||res.telphone )+"</div></td>" +
				"<td><div class='overflow_hidden w50' title='"+AjaxQueryData.emptyTransfer(res.area )+"'>"+AjaxQueryData.emptyTransfer(res.area )+"</div></td>" +
				"<td><div class='overflow_hidden w50' title='"+AjaxQueryData.emptyTransfer(res.companyTrade )+"'>"+AjaxQueryData.emptyTransfer(res.companyTrade )+"</div></td>" +
				"<td><div class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.defined1 )+"'>"+AjaxQueryData.emptyTransfer(res.defined1 )+"</div></td>" +
				"<td><div class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.defined2 )+"'>"+AjaxQueryData.emptyTransfer(res.defined2 )+"</div></td>" +
				"<td><div class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.defined3 )+"'>"+AjaxQueryData.emptyTransfer(res.defined3 )+"</div></td>" +
				"<td><div class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.defined4 )+"'>"+AjaxQueryData.emptyTransfer(res.defined4 )+"</div></td>" +
				"<td><div class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.defined5 )+"'>"+AjaxQueryData.emptyTransfer(res.defined5 )+"</div></td>" +
				"<td><div class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.defined6 )+"'>"+AjaxQueryData.emptyTransfer(res.defined6 )+"</div></td>" +
				"<td><div class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.defined7 )+"'>"+AjaxQueryData.emptyTransfer(res.defined7 )+"</div></td>" +
				"<td><div class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.defined8 )+"'>"+AjaxQueryData.emptyTransfer(res.defined8 )+"</div></td>" +
				"<td><div class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.defined9 )+"'>"+AjaxQueryData.emptyTransfer(res.defined9 )+"</div></td>" +
				"<td><div class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.defined10 )+"'>"+AjaxQueryData.emptyTransfer(res.defined10 )+"</div></td>" +
				"<td><div class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.defined11 )+"'>"+AjaxQueryData.emptyTransfer(res.defined11 )+"</div></td>" +
				"<td><div class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.defined12 )+"'>"+AjaxQueryData.emptyTransfer(res.defined12 )+"</div></td>" +
				"<td><div class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.defined13 )+"'>"+AjaxQueryData.emptyTransfer(res.defined13 )+"</div></td>" +
				"<td><div class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.defined14 )+"'>"+AjaxQueryData.emptyTransfer(res.defined14 )+"</div></td>" +
				"<td><div class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.defined15 )+"'>"+AjaxQueryData.emptyTransfer(res.defined15 )+"</div></td>" +
				"<td><div class='overflow_hidden w100' title='"+AjaxQueryData.emptyTransfer(res.showdefined16 )+"'>"+AjaxQueryData.emptyTransfer(res.showdefined16 )+"</div></td>" +
				"<td><div class='overflow_hidden w100' title='"+AjaxQueryData.emptyTransfer(res.showdefined17 )+"'>"+AjaxQueryData.emptyTransfer(res.showdefined17 )+"</div></td>" +
				"<td><div class='overflow_hidden w100' title='"+AjaxQueryData.emptyTransfer(res.showdefined18 )+"'>"+AjaxQueryData.emptyTransfer(res.showdefined18 )+"</div></td>" +
			"</tr>";
	}
	$(".ajax-table>tbody").html(html);
	//复选框渲染
	$('input[id^=chk_]').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
}

function getSourceName(source){
	if(source == 1){
		return '自有导入';
	}else if(source == 2){
		return '分配交接';
	}else if(source == 3){
		return '公海取回';
	}else if(source == 4){
		return 'AI初筛';
	}else if(source == 5){
		return '在线表单';
	}else if(source == 6){
		return '数据合作';
	}else{
		return '';
	}
}

function getTypeName(custType){
	if(custType == 1){
		return "资源";
	}else if(custType == 2){
		return "意向客户";
	}else if(custType == 3){
		return "签约客户";
	}else if(custType == 4){
		return "沉默客户";
	}else if(custType == 5){
		return "流失客户";
	}else{
		return "";
	}
}

/**生成操作列*/
function getResOperate(res,serverDay,loginAcc,signSetting,adminSignAuth,isState){
	var html = "";
	if(res.custType == 1){
		if(res.ownerAcc == loginAcc && serverDay > 0){
			html+=
				"<a href='javascript:;' id='tao_"+AjaxQueryData.emptyTransfer(res.resCustId )+"' filterType='"+AjaxQueryData.emptyTransfer(res.filterType )+"' class='icon-amoy-cust' title='开始淘'></a>";
		}else{
			html+=
				"<a href='javascript:;' class='icon-amoy-gray' title='开始淘'></a>";
		}
		html+=
			"<a href='javascript:;' id='cardInfo_"+AjaxQueryData.emptyTransfer(res.resCustId )+"' custName='"+getCardTitle(res,isState)+"' class='icon-cust-card' title='客户卡片'></a>";
	}else{
		if(window.top.shrioAuthObj('base_followCust')){
			if(serverDay > 0){
				html+=
					"<a href='javascript:;' id='follow_"+AjaxQueryData.emptyTransfer(res.resCustId )+"' custType='1' class='icon-follow-up' title='客户跟进'></a>";
			}else{
				html+=
					"<a href='javascript:;' class='icon-follow-gray' title='客户跟进'></a>";
			}
		}
		html+=
			"<a href='javascript:;' id='cardInfo_"+AjaxQueryData.emptyTransfer(res.resCustId )+"' custName='"+getCardTitle(res,isState)+"' class='icon-cust-card' title='客户卡片'></a>";
	}
	return html;
}

/**获取客户卡片显示名称*/
function getCardTitle(res,isState){
	if(isState == 1){
		return AjaxQueryData.emptyTransfer(res.name);
	}else{
		if(res.company == null || res.company == ''){
			return AjaxQueryData.emptyTransfer(res.name);
		}else{
			return AjaxQueryData.emptyTransfer(res.company);
		}
	}
}

/**加载自定义字段*/
function getDefineFiled(fields,res){
	var html="";
	for(var i in fields){
		var field = fields[i];
		html+=
			"<td><div class='overflow_hidden w50' title='"+AjaxQueryData.emptyTransfer(res[field.fieldCode])+"'>"+AjaxQueryData.emptyTransfer(res[field.fieldCode])+"</div></td>";
	}
	return html;
}

/**清空右侧*/
function clearRight(){
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
}