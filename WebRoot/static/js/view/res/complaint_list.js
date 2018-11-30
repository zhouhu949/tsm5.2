$(function(){

	//到期时间
	 $('#dDate').dateRangePicker({
		 	showShortcuts:false,format: 'YYYY-MM-DD'
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
	        dt.html(s_t+'/'+e_t);
	        $("#dDateType").val(5);
	        dd.slideUp(200);
	        dt.removeClass("cur");
	 });

	$("#tt1").tree({
		url:ctx+"/orgGroup/get_all_group_user_json",
		checkbox:true,
		onLoadSuccess:function(node,data){
			$("#tt1").tree("collapseAll");
			for(var i=0;i<data.length;i++){
				$("#tt1").tree("expand",data[i].target);
			}
			var oas = $("#accountsStr").val();
			if(oas != null && oas != ''){
				var text='';
				$.each(oas.split(','),function(index,obj){
					var n = $("#tt1").tree("find",obj);
					text+=n.text+',';
					$("#tt1").tree("check",n.target).tree("expandTo",n.target);
				});
				if(text != ''){
					text=text.substring(0,text.length-1);
					$("#userNames").val(text);
				}else{
					$("#userNames").val("添加人");
				}
			}
		}
	});

	// 表格载入数据
	loadData();

	// 新增按钮
	$(".complaint_add").on("click",function(e){
		e.stopPropagation();
		e.preventDefault();
		pubDivShowIframe('compaint_list_add',ctx+"/res/cust/addBlackComList",'新增号码',350,200);
	});
	// 删除按钮
	$(".complaint_del").on("click",function(e){
		e.stopPropagation();
		
		var blackIds='';
		var phones='';
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				blackIds+=$(obj).attr('blackId')+',';
				phones+=$(obj).attr('phone')+',';
			}
		});
		if(blackIds.length == 0){
			window.top.iDialogMsg("提示",'请先选择！');
			return;
		}
		blackIds=blackIds.substring(0,blackIds.length-1);//去掉最后一个逗号
		phones=phones.substring(0,phones.length-1);//去掉最后一个逗号
		
		window.pubDivDialog("sure_to_delete_","是否确认删除？",function(){
			$.ajax({
				url:ctx+"/res/cust/deleteBlackList",
				data:{
					blackIds:blackIds,
					phone:phones,
					type:$("#comp_type").val()
				},
				cache:false,
				success:function(data){
					if(data){
						window.top.iDialogMsg("提示",'删除成功！');
						refreshPageData(pa.pageParameter());
					}else{
						window.top.iDialogMsg("提示",'操作失败，请重试！');
					}
				}
			})
		})
	});

	//查询
	$("#cform").submit(function(e){
		e.stopPropagation();
		e.preventDefault();
		loadData();
	})
});
function loadData(page){

    var _param = $('#cform').serialize();
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
	var _url = ctx+'/res/cust/selectBlackListJson';

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
		cache:false,
		success:function(data){
			renderTable(data);
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

function renderTable(data){
	var myTemplate = Handlebars.compile($("#template").html());
	$(".ajax-table>tbody").html(myTemplate(data));
	/*表单优化*/
	$('.skin-minimal input').iCheck({
			checkboxClass: 'icheckbox_minimal',
			radioClass: 'iradio_minimal',
			increaseArea: '20%'
	});
	 $('#checkAll').iCheck('uncheck');
}




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
	}else{
		names="添加人";
	}
	$("#accountsStr").val(accs);
	$("#userNames").val(names);

}

var unCheckTree=function(){
	var nodes = $('#tt1').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#tt1').tree("uncheck",obj.target);
	});
}

var seleGroupTree=function(){
	var nodes = $('#tt2').tree('getChecked', 'checked');
	var groupIds = "";
	var groupNames = "";
	$.each(nodes,function(index,obj){
		groupIds+=obj.id+",";
		groupNames+=obj.text+",";
	});
	if(groupIds != ""){
		groupIds=groupIds.substring(0,groupIds.length-1);
		groupNames=groupNames.substring(0,groupNames.length-1);
	}else{
		groupNames="所在部门";
	}
	$("#groupIdStr").val(groupIds);
	$("#groupNameStr").val(groupNames);

}

var unCheckGroupTree=function(){
	var nodes = $('#tt2').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#tt2').tree("uncheck",obj.target);
	});
}

var setDdate = function(i){
		$('#s_startDate').val('');
		$('#s_endDate').val('');
		$("#dDateType").val(i);
	}
var setSelectType=function(i){
	$("#type").val(i);
}