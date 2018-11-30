var isState
$(function(){
	isState = $("#isState").val();	
	loadData();
/*	$("#query").click(function(){
		loadData();		
	});*/
	
	var height = $(".hyx-ctr").height()+30;
	window.parent.$("#iframepage").css({"height":height+"px"});
	
	//查询框宽度动态设置
//	var inputquer=$(".input-query");
//	var parewid=inputquer.parent().width();
//	var prevwid=inputquer.prev().width();
//	inputquer.css("width",parewid-prevwid+"px");
	

    /*表单优化*/
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
	

 	//  审核时间
    $('#d1').dateRangePicker({
    	showShortcuts:false,
        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
    	var s_t = '';
    	var e_t = '';
    	if(obj.date1 != 'Invalid Date'){
    		$('#auditStartDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    		s_t = moment(obj.date1).format('YY.MM.DD');
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#auditEndDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    		e_t = moment(obj.date2).format('YY.MM.DD');
    	}
    	var s = $(this).parents('.select');
    	var dt=s.children("dt");
        var dd=s.children("dd");
        dt.html(s_t+'/'+e_t);
        $("#dDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
        //s.css("z-index",z);
 });
 
 // 申请时间
    $('#d2').dateRangePicker({
    	showShortcuts:false,
        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
    	var s_t = '';
    	var e_t = '';
    	if(obj.date1 != 'Invalid Date'){
    		$('#startDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    		s_t = moment(obj.date1).format('YY.MM.DD');
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#endDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    		e_t = moment(obj.date2).format('YY.MM.DD');
    	}
    	var s = $(this).parents('.select');
    	var dt=s.children("dt");
        var dd=s.children("dd");
        dt.html(s_t+'/'+e_t);
        $("#sDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
        //s.css("z-index",z);
 });
 
	//通过
	$("#btn_pass").on('click', function(){	
		var str=getAllCheckBoxValuesAndSnames();
		if(str==""){
			window.top.iDialogMsg("提示","请选择！");
		}else{
			ok_fun(str,"1");
		}
    });

    //驳回
    $("#btn_reject").on('click', function(){	
    	var str=getAllCheckBoxValuesAndSnames();
		if(str==""){
			window.top.iDialogMsg("提示","请选择！");
		}else{
			ok_fun(str,"0");
		}
    });
    
	// 客户卡片点击的时候
	$("table").on('click',"[name='add_custInfo']", function(){	
		var custId = $(this).attr("custId");
		var custName = $(this).attr("custName");
		var custName1 = custName||'客户卡片';
		window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName1);
	});
});
function loadData(page){

    var _param = $("form").serialize();
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

	var _url=ctx+"/cust/custFollow/extension/deferredAuditListJson";
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
		error:function(){
			$(".tip_search_text").hide();
			$(".tip_search_error").text("网络异常，请重新查询！");
			$(".tip_search_error").show();
		},
		success:function(data){
			if(data.status=="success"){
				loadTable(data.list);
				$(".tip_search_data").hide();
				clearTimeout(timeout);
				pa.load("new_page",data.item.page,refreshPageData,$(".ajax-table"));
			}else{
				clearTimeout(timeout);
				$(".tip_search_text").hide();
				$(".tip_search_error").text("网络异常，请重新查询！");
				$(".tip_search_error").show();
			}	
		}
	});
}

function loadTable(list){
	var html='';
	for(var i in list){
		var v = list[i];		
		html +=
		"<tr class='hyx-fur-tr "+(i%2==0?'':'sty-bgcolor-b')+"'>"+
		'<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal">';
		if(v.statusExtended==2){
			html += '<input type="checkbox" name="id_checkBox" id="chk_'+v.extensionId+'" value="'+v.extensionId+'"';
			if(isState == 0 && emptyTransfer(v.company) != ''){
				html += 'custName="'+emptyTransfer(v.company)+'"';
			}else{
				html += 'custName="'+emptyTransfer(v.custName)+'"';
			}
			html += '>';
		}else{
			html += '<input type="checkbox" disabled="disabled">';
		}
		html += '</div></td>'+
		'<td style="width:30px;"><div class="overflow_hidden w30 link">'+
		"<a href='javascript:;' class='icon-cust-card' name='add_custInfo' custId = '"+v.custId+"' ";
		if(isState == 0 && emptyTransfer(v.company) != ''){
			html += "custName='"+emptyTransfer(v.company)+"'";
		}else{
			html += "custName='"+emptyTransfer(v.custName)+"'";
		}
		html +="title='客户卡片'></a></div></td>"+
		'<td><div class="overflow_hidden w120" title="'+v.showApplicationTimeExtension+'">'+v.showApplicationTimeExtension+'</div></td>'+
		'<td><div class="overflow_hidden w150" title="'+emptyTransfer(v.custName)+'">'+emptyTransfer(v.custName)+'</div></td>';
		if(isState == 0){
			html += '<td><div class="overflow_hidden w150" title="'+emptyTransfer(v.company)+'">'+emptyTransfer(v.company)+'</div></td>';
		}
		html += '<td><div class="overflow_hidden w120" title="'+emptyTransfer(v.optionName)+'">'+emptyTransfer(v.optionName)+'</div></td>'+
		'<td><div class="overflow_hidden w100" >'+v.daysExtension+'天</div></td>'+
		'<td><div class="overflow_hidden w120" title="'+v.reasonsExtension+'">'+v.reasonsExtension+'</div></td>'+
		'<td><div class="overflow_hidden w70" title="'+emptyTransfer(v.applicantExtensionName)+'">'+emptyTransfer(v.applicantExtensionName)+'</div></td>'+
		'<td><div class="overflow_hidden w100" >';
		if(v.statusExtended==2){
			html += '待审核';
		}else if(v.statusExtended==0){
			html += '驳回';
		}else if(v.statusExtended==1){
			html += '通过';
		}
		html += '</div></td>'+
		'<td><div class="overflow_hidden w70" title="'+emptyTransfer(v.reviewerName)+'">'+emptyTransfer(v.reviewerName)+'</div></td>'+
		'<td><div class="overflow_hidden w120" title="'+v.showReviewerTime+'">'+v.showReviewerTime+'</div></td></tr>'
	}
	$(".ajax-table>tbody").html(html);
	
	 /*表单优化*/
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
	

}

var setDdate = function(i){
	$('#auditStartDate').val('');
	$('#auditEndDate').val('');
	$("#dDateType").val(i);
};

var setSdate = function(i){
	$('#startDate').val('');
	$('#endDate').val('');
	$("#sDateType").val(i);
};

//通过/驳回的通用操作
function ok_fun(str,status){
	var $text = "是否通过？";
	if(status == "0"){
		$text = "是否驳回？";
	}
	queDivDialog("res_remove_cust",$text,function(){
		$.ajax({
			url:ctx+'/cust/custFollow/extension/updateBatchStatus',
			type:'post',
			data:{"ids":str,"status":status},
			error:function(){alert('网络异常，请稍后再试！');},
			success:function(data){
				if(data == 0){
					window.top.iDialogMsg("提示",'操作成功！');
					refreshPageData(pa.pageParameter());
				}else{
					window.top.iDialogMsg("提示",'操作失败！');
				}
			}
		});
	});
}

//获取所有选中的节点
function getAllCheckBoxValuesAndSnames() {
	var returns = "";
	$("input[name='id_checkBox']").each(function(index) {
		if ($(this).attr("checked")) {
			var appid = $(this).val();
			var custName = $(this).attr("custName")
			if (appid && appid != null) {
				returns += appid+"_"+custName+ ",";
			}
		}
	});
	return returns;
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

var emptyTransfer = function(data){
	if(data == null)
		return '';
	else 
		return data;
};