$(function(){
	loadData();
});
function loadData(page){

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
	var _url = ctx+'/res/sea/highSeasListData';
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
			if($("#h_isShareRes").val() == '0'){
				$("th[visible_in_seas=1]:hidden").each(function(index,element){
					var _this = $(element);
//					var index = _this.attr("col_index");
					_this.show();
//					$("#t1 tobody tr").find("td:("+parseInt(index)+")").show();
				});
				if(window.top.shrioAuthObj('HighSeasCust_recycleUnAssign')) $("#recyleBtn").show();
				$('[data-input="input[name=notContactedType]"]').show()
		        $('[data-input="input[name=noteType]"]').show()
			}else{
				$("th[showType=high_seas]:visible").each(function(index,element){
					var _this = $(element);
					if(_this.attr("visible_in_seas") != "1"){
						_this.attr("visible_in_seas","1");
					}
//					var index = _this.attr("col_index");
					_this.hide();
//					$("#t1 tobody tr").find("td:("+parseInt(index)+")").hide();
				});
				if(window.top.shrioAuthObj('HighSeasCust_recycleUnAssign')) $("#recyleBtn").hide();
		        $('[data-input="input[name=notContactedType]"]').hide()
		        $('[data-input="input[name=noteType]"]').hide()
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
	var td_class="";
	if($("#highSeaType").val() == 1){
		td_class="notHighSeas";
	}else{
		td_display = "";
	}
	for(var i = 0;i<data.list.length;i++){
		var res = data.list[i];
        html+=
			"<tr class='"+(i%2==0?'':'sty-bgcolor-b')+"'>" +
				"<td style='width:30px;'><div class='overflow_hidden w30 skin-minimal'><input name='check_' type='checkbox' id='chk_"+AjaxQueryData.emptyTransfer(res.resCustId )+"' /></div></td>" +
				"<td style='width:80px;'>" +
					"<div class='overflow_hidden w80 link'>" +
					getResOperate(res,data.serverDay,data.loginAcc,data.signSetting,data.adminSignAuth,data.isState) +
					"</div>" +
				"</td>" ;
				html+=
					"<td class='"+td_class+"' hidevalue='"+AjaxQueryData.emptyTransfer(res.startDate )+"'><div class='overflow_hidden w70' title='"+AjaxQueryData.emptyTransfer(res.startDate )+"'>"+AjaxQueryData.emptyTransfer(res.endDate )+"</div></td>" ;
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
				"<td><div phone='tel' class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.mobilephone||res.telphone )+"'>"+AjaxQueryData.emptyTransfer(res.mobilephone||res.telphone )+"</div></td>" +
				"<td><div class='overflow_hidden w100' title='"+AjaxQueryData.emptyTransfer(res.saleProcessName )+"'>"+AjaxQueryData.emptyTransfer(res.saleProcessName )+"</div></td>" +
				"<td><div class='overflow_hidden w100' title='"+getGiveUpReason(AjaxQueryData.emptyTransfer(res.opreateType))+"'>"+getGiveUpReason(AjaxQueryData.emptyTransfer(res.opreateType))+"</div></td>"+
				"<td class='"+td_class+"'><div class='overflow_hidden w100' title='"+AjaxQueryData.emptyTransfer(res.recuclingTypeDetails )+"'>"+AjaxQueryData.emptyTransfer(res.recuclingTypeDetails )+"</div></td>" +
				"<td class='"+td_class+"'><div class='overflow_hidden w80' title='"+AjaxQueryData.emptyTransfer(res.ownerName )+"'>"+AjaxQueryData.emptyTransfer(res.ownerName )+"</div></td>" +
				"<td class='"+td_class+"'><div class='overflow_hidden w50' title='"+(res.giveUpName == null ? '系统' : res.giveUpName )+"'>"+(res.giveUpName == null ? '系统' : res.giveUpName )+"</div></td>"+
				"<td class='"+td_class+"'><div class='overflow_hidden w90' title='"+AjaxQueryData.emptyTransfer(res.days)+"'>"+AjaxQueryData.emptyTransfer(res.days)+"</div></td>" +
				"<td class='"+td_class+"'><div class='overflow_hidden w90' title='"+AjaxQueryData.emptyTransfer(res.inputDays)+"'>"+AjaxQueryData.emptyTransfer(res.inputDays)+"</div></td>"+
				"<td class='"+td_class+"' hidevalue='"+AjaxQueryData.emptyTransfer(res.startActionDate )+"'><div class='overflow_hidden w110' title='"+AjaxQueryData.emptyTransfer(res.startActionDate )+"'>"+AjaxQueryData.emptyTransfer(res.endActionDate )+"</div></td>" +
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
	//复选框渲染
	$('input[id^=chk_]').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
}

function getGiveUpReason(opreateType){
	if(opreateType == '11'){
		return "客户-到期未签约";
	}else if(opreateType == '16'){
		return "客户-到期未跟进";
	}else if(opreateType == '12'){
		return "客户-主动放弃";
	}else if(opreateType == '4'){
		return "资源-沟通失败";
	}else if( opreateType== '5'){
		return "资源-信息错误";
	}else if( opreateType== '21'){
		return "签约客户-流失";
	}else if(opreateType == '23'){
		return "签约-到期未续签";
	}else if(opreateType == '24'){
		return "资源-到期未联系";
	}else{
		return "";
	}
}

/**生成操作列*/
function getResOperate(res,serverDay,loginAcc,signSetting,adminSignAuth,isState){
	var html = "<a href='javascript:;' id='cardInfo_"+AjaxQueryData.emptyTransfer(res.resCustId )+"' custName='"+getCardTitle(res,isState)+"' class='icon-cust-card' title='客户卡片'></a>" ;
	if(window.top.shrioAuthObj('HighSeasCust_recycle')){
		html+="<a href='javascript:;' id='getBack_"+AjaxQueryData.emptyTransfer(res.resCustId )+"' class='icon-get-back' title='取回资源'></a>";
	}
	if(window.top.shrioAuthObj('HighSeasCust_recycleToInte')){
		html+="<a href='javascript:;' id='getBackToInt_"+AjaxQueryData.emptyTransfer(res.resCustId )+"' class='icon-res-resave' title='取回意向'></a>";
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
