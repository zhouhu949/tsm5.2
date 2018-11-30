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
    var _url=ctx+"/sms/record/hookSmsSendListJson";
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
		var hook = list[i];
		html +=
		"<tr class='"+(i%2==0?'':'sty-bgcolor-b')+"'>"+
		'<td><div class="overflow_hidden w150" name="phone_" title="'+hook.mobilePhone+'">'+hook.mobilePhone+'</div></td>'+
		'<td><div class="overflow_hidden w350" title="'+hook.smsContent+''+hook.signAture+'">'+hook.smsContent+''+hook.signAture+'</div></td>'+
		'<td><div class="overflow_hidden w70" >'+hook.smsNumber+'</div></td>'+
		'<td><div class="overflow_hidden w70" >';
		if(hook.submitStatus == '0'){
			html+= "提交中";
		}else if(hook.submitStatus == '1'){
			html+= "成功";
		}else if(hook.submitStatus == '-1'){
			html+= "未提交";
		}else{
			html+= "失败";
		}
		html+= '</div></td>'+
		'<td><div class="overflow_hidden w120" title="'+hook.showSendDate+'">'+hook.showSendDate+'</div></td>'+
		 "</tr>";
	}
	$(".ajax-table>tbody").html(html);
	  /* 屏蔽手机或固话中间几位  */
    var idReplaceWord = $("#idReplaceWord").val();	
	if(idReplaceWord==1){
	    $("div[name=phone_]").each(function(){
	    		   var str = $(this).text();
	    		   replaceWord(str,$(this));
	    		   replaceTitleWord(str,$(this));
	    		  });
	}
	/* 屏蔽手机或固话中间几位  */
}

$(function(){

	loadData();
/*	$("#query").click(function(){
		loadData();
	});*/
	
	/*表单优化*/
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
    
   // 发送时间
    $('#d1').dateRangePicker({
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
         $("#dDateType").val(5);
         dd.slideUp(200);
         dt.removeClass("cur");
         //s.css("z-index",z);
     });

});

var setNdate = function(i){
	$('#startDate').val('');
	$('#endDate').val('');
	$("#dDateType").val(i);
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
}