var serverDay
var isState
$(function(){
	isState = $("#isState").val();
	serverDay = $("#serverDay").val();
	
	loadData();
/*	$("#query").click(function(){
		loadData();		
	});*/
	


 	//  最近联系时间
    $('#d1').dateRangePicker({
    	showShortcuts:false,
        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
    	var s_t = '';
    	var e_t = '';
    	if(obj.date1 != 'Invalid Date'){
    		$('#lastStartActionDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    		s_t = moment(obj.date1).format('YY.MM.DD');
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#lastEndActionDate').val(moment(obj.date2).format('YYYY-MM-DD'));
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
 
 	// 下次联系时间
    $('#d2').dateRangePicker({
    	showShortcuts:false,
        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
    	var s_t = '';
    	var e_t = '';
    	if(obj.date1 != 'Invalid Date'){
    		$('#nextStartActionDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    		s_t = moment(obj.date1).format('YY.MM.DD');
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#nextEndActionDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    		e_t = moment(obj.date2).format('YY.MM.DD');
    	}
    	var s = $(this).parents('.select');
    	var dt=s.children("dt");
        var dd=s.children("dd");
        dt.html(s_t+'/'+e_t);
        $("#nDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
        //s.css("z-index",z);
 });
		
		//群发短信
	    $("#groupCustSMS_btn").click(function(){
	    	toBatchSmsSend();
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
	var _url=ctx+"/cust/custFollow/List/followUpTheAlertListJson";
//	var _param = $("form").serialize();
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
				loadTable(data.list,data.currentPage,data.showCount);
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

function loadTable(list,currentPage,showCount){
	var count = (parseInt(currentPage)-1)*showCount;
	var html='';
	var count1 = null;
	for(var i in list){
		count1 = parseInt(count)+parseInt(i)+parseInt(1);
		var tsmCustFollowDto = list[i];	
		html +=
		"<tr class='hyx-fur-tr "+(i%2==0?'':'sty-bgcolor-b')+"'>"+
		'<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox"  id="chk_'+tsmCustFollowDto.resCustId+'"  name="check_" name_tel ="'+tsmCustFollowDto.custName +'|'+tsmCustFollowDto.telphone+'" name_mobile="'+tsmCustFollowDto.custName+'|'+tsmCustFollowDto.custMobilephone+'" mobile="'+tsmCustFollowDto.custMobilephone+'" telPhone="'+tsmCustFollowDto.telphone+'" /></div></td>'+
		'<td style="width:160px;"><div class="overflow_hidden w160 link">';
		if(serverDay > 0){
			html += '<a href="javascript:;"data-authority = "base_followCust"  class="icon-follow-up" onclick="custFollow(this)" custId="'+tsmCustFollowDto.resCustId+'" startPage="'+count1+'"  title="客户跟进"></a>';
		}else{
			html += '<a href="javascript:;" data-authority = "base_followCust" class="icon-follow-up img-gray" title="客户跟进"></a>';
		}
		if(tsmCustFollowDto.statusExtended !=1 && tsmCustFollowDto.statusExtended !=2){
			html += '<a href="javascript:;" class="icon-exten-reques" onclick="delay_click(\''+tsmCustFollowDto.resCustId+'\')" title="申请延期"></a>';
		}else{
			html += '<a href="javascript:;" class="icon-exten-reques img-gray" ></a>';
		}
		html += "<a href='javascript:;' class='icon-cust-card' name='add_custInfo' custId = '"+tsmCustFollowDto.resCustId+"' ";
		if(isState == 0 && emptyTransfer(tsmCustFollowDto.company) != ''){
			html += "custName='"+emptyTransfer(tsmCustFollowDto.company)+"'";
		}else{
			html += "custName='"+emptyTransfer(tsmCustFollowDto.custName)+"'";
		}
		html +="title='客户卡片'></a>";
		if(tsmCustFollowDto.isMajor==1){
			html += "<a href='###' class='icon-focus-atten' title='重点关注' custId = '"+tsmCustFollowDto.resCustId+"' isMajar='0' onclick='setIsMajor_(this)'></a>";
		}else{
			html += "<a href='###' class='icon-nofocus' title='非重点关注' custId = '"+tsmCustFollowDto.resCustId+"' isMajar='1' onclick='setIsMajor_(this)'></a>";
		}
		if(serverDay>0){
			if(tsmCustFollowDto.status != 6 && tsmCustFollowDto.status != 7 && tsmCustFollowDto.status != 8 ){ 
				html +='<a href="javascript:;" data-authority = "base_signCust" class="icon-sign-cont" onclick="custSign(\''+tsmCustFollowDto.resCustId+'\')" title="签约"></a>'+
				              '<a data-authority="base_putIntoHighSeas" onclick="remove_(\''+tsmCustFollowDto.resCustId+'\')" href="javascript:;" class="icon-dele" title="放弃客户"></a>';
			}else{
				if(tsmCustFollowDto.status == 6 || tsmCustFollowDto.status == 7 || tsmCustFollowDto.status == 8){
					html += "<a href='javascript:;' data-authority='base_signCust' class='icon-sign-cont img-gray'></a>"+
				                  " <a href='###' data-authority='base_putIntoHighSeas' class='icon-dele img-gray'></a>";
				}
			}			
		}else{
			html += "<a href='javascript:;' data-authority='base_signCust' class='icon-sign-cont img-gray'></a>"+
		                 "<a href='javascript:;' data-authority='base_putIntoHighSeas' class='icon-dele img-gray' ></a>";
		}
		html +="</div></td>"+
		'<td><div class="overflow_hidden w70" >'+tsmCustFollowDto.daysAfterDateCount+'</div></td>'+
		'<td><div class="overflow_hidden w100" title="'+emptyTransfer(tsmCustFollowDto.optionName)+'" >'+emptyTransfer(tsmCustFollowDto.optionName)+'</div></td>'+
		'<td><div class="overflow_hidden w120" title="'+tsmCustFollowDto.showNextActionDate+'">'+tsmCustFollowDto.showNextActionDate+'</div></td>'+
		'<td><div class="overflow_hidden w120" title="'+tsmCustFollowDto.showLastActionDate+'">'+tsmCustFollowDto.showLastActionDate+'</div></td>'+
		'<td><div class="overflow_hidden w120" title="'+emptyTransfer(tsmCustFollowDto.custName)+'">'+emptyTransfer(tsmCustFollowDto.custName)+'</div></td>';
		if(isState == 0){
			html += '<td><div class="overflow_hidden w120" title="'+emptyTransfer(tsmCustFollowDto.company)+'">'+emptyTransfer(tsmCustFollowDto.company)+'</div></td>';
		}
		html += '<td><div class="overflow_hidden w100" >'+emptyTransfer(tsmCustFollowDto.custTypeName)+'</div></td>'+
		'<td><div class="overflow_hidden w50">';
		if(tsmCustFollowDto.statusExtended==0){
			html += '驳回';
		}else if(tsmCustFollowDto.statusExtended==1){
			html += '通过';
		}else if(tsmCustFollowDto.statusExtended==2){
			html += '待审核';
		}else{
			html += '未申请';
		}
		html += '</div></td>';
		html += '</tr>';
	}

	$(".ajax-table>tbody").html(html);
	isAuthority("data-authority");
	$('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
	

	/********** 屏蔽手机或固话中间几位  *********/
    var idReplaceWord = $("#idReplaceWord").val();	
	if(idReplaceWord==1){
	    $("span[name=custMobilephone_]").each(
	    		function(){
	    		   var str = $(this).text();
	    		   replaceWord(str,$(this));
	    		  }
	    );
	    $("span[name=telphone_]").each(
	    		function(){
	    		   var str = $(this).text();
	    		   replaceWord(str,$(this));
	    		  }
	    );    
	}
	

}

var setDdate = function(i){
	$('#lastStartActionDate').val('');
	$('#lastEndActionDate').val('');
	$("#dDateType").val(i);
};

var setNdate = function(i){
	$('#nextStartActionDate').val('');
	$('#nextEndActionDate').val('');
	$("#nDateType").val(i);
};


/** 申请延期 */
function delay_click(custId){
	var url = ctx+"/cust/custFollow/extension/toDeferredAudit?custId="+custId;
	pubDivShowIframe('alert_cust_follow_delay',url,'申请延期',330,260);
}

/**
 * 设置重点关注
 * @param custId 资源ID
 * @param isMajar 是否是关注，0:否，1:是
 */
function setIsMajor_(obj){	
	var custId =  $(obj).attr("custId");
	var isMajar =  $(obj).attr("isMajar");
	$.ajax({
		url:ctx+'/res/tao/setMajor',
		type:'post',
		data:{custId:custId,isMajar:isMajar},
		success:function(data){
			if(data == 0){
				refreshPageData(pa.pageParameter());
			}else{
				window.top.iDialogMsg("提示",'设置失败！');
			}
		}
	});
}

/** 放弃客户 */
function remove_(resCustId){
		if(resCustId != null && resCustId != ""){
			queDivDialog("res_remove_cust","是否放弃客户？",function(){
				$.ajax({
					url:ctx+'/res/cust/removeCust',
					type:'post',
					data:{ids:resCustId+'_2'},
					dataType:'json',
					error:function(){alert('网络异常，请稍后再试！');},
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
}
/** 客户跟进  */
function custFollow(obj){
	var custId = $(obj).attr("custId");
	var startPage = $(obj).attr("startPage");
	window.top.addTab(ctx+"/cust/custFollow/custFollowPage.do?custId="+custId+"&isAlarm=1&startPage="+startPage+"&v="+new Date().getTime(),"跟进");
}

/** 客户签约 */
function custSign(custId){
	var signSetting = $("#signSetting").val();
	if(signSetting == '1'|| !window.top.shrioAuthObj("mySignCust_addContract")){ // 读取签约是否与合同无关 0-需添加合同 1-无需添加合同
		pubDivDialog("confirm_sign","是否确认签约？",function(){
			$.ajax({
				url:ctx+"/contract/sign",
				type:"post",
				data:{custId:custId},
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
		window.top.addTab(ctx+"/contract/toAdd?custId="+custId+"&t="+Date.parse(new Date()),"新增合同");
	}	
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