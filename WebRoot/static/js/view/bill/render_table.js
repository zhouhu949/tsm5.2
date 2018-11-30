$(function(){
	loadData();
});

function renderTable(data){
	var myTemplate = Handlebars.compile($("#template").html());
	$(".ajax-table>tbody").html(myTemplate(data));
	$('#checkAll').iCheck('uncheck');
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
    if($('[data-isreplaceword=true]').length>0){
    	var idReplaceWord = $("#idReplaceWord").val();
    	if(idReplaceWord==1){
    		$('[data-isreplaceword=true]').each(function(idx,obj){
    			var phone = $(obj).text();
    			if(phone != null && phone != ''){
    				replaceWord(phone,$(obj));
    				replaceTitleWord(phone,$(obj));
    			}
    		});
    	 }
    }
}

function loadData(page){
    var _param = $('form').serialize();
    loaderAjax(_param,$("[name='showCount']").length>0? "page.showCount="+ $("[name='showCount']").val(): "");
    $('#checkAll').iCheck('uncheck');
}
function refreshPageData(page){
    var table = $(".ajax-table");

    var pageString= $("#new_page").data("page")
    var pageObject=JSON.parse(pageString);
    pageObject.operation=page.action || 0;
    pageObject.showCount=page.showCount || 10;
    var _param=table.attr("data-param")
    var pageStr="&";
    if(page){
        pageStr = "&page.currentPage="+page.currentPage+"&page.totalResult="+page.totalResult+"&page.showCount="+page.showCount+"&pageStr="+encodeURIComponent(JSON.stringify(pageObject));
    }
    loaderAjax(_param,pageStr);
}
function loaderAjax(_param,pageStr){
    var table = $(".ajax-table");
    table.attr("data-param",_param);
	var _url = $("form").attr("data-render-url");
	var first_tr_selected = table.data("first-tr-selected");
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
                renderTable(data);
                $(".tip_search_data").hide();
                clearTimeout(timeout);
                pa.load('new_page',data.item.page,refreshPageData,$('#t1'));
                $("[action='/resOperate/getLogResOperateJson'] input[name='currentPage']").attr("readonly","readonly");
                try{
                    if(typeof(eval(resetHeight)) == "function"){
                        resetHeight();
                    }
                }catch(e){
                    console.log("function resetHeight not exists");
                }
                if(table.attr("data-form") == "orderManage"){
                    orderCountData();
                }
                if($("[data-authority]") && $("[data-authority]").length > 0){
                    window.isAuthority && isAuthority("data-authority");
                }
                if(first_tr_selected){
                    table.find("tbody tr:first").trigger("click");
                }
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
