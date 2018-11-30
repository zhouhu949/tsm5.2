$(function(){
	$(".com-search #queryByConditions").on("click",function(e){
		var dDateType = $("#dDateType").val();
		var startDate = $("#s_startDate").val();
		var endDate = $("#s_endDate").val();
		var diff_days = moment(endDate).diff(moment(startDate),"days");
		if(dDateType == 5 && diff_days > 180){
			window.top.iDialogMsg("提示","操作时间必须在180天内!");
			return false;
		}
		var accountsStr = $("#accountsStr").val();
		var groupIdStr = $("#groupIdStr").val();
		if(accountsStr == "" && groupIdStr == ""){
			window.top.iDialogMsg("提示","帐号或模块至少选择1个!");
			return false;
		}
		var _this = $(this);
		_this.attr("disabled",true);
		try{
			loadData();
		}catch(e){
			document.forms[0].submit();
		}
		setTimeout(function(e){
			_this.attr("disabled",false);
		},500);
	});
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
	    	if(moment($('#s_endDate').val()).diff(moment($('#s_startDate').val()),"days")>=15){
	    		window.top.iDialogMsg("提示","自定义日期控件选择不能超过15天");
	    		return false;
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
		onlyLeafCheck: true,
		onSelect:function(node,checked){
			var cknodes =$('#tt1').tree('getChecked', 'checked');
            for (var i = 0; i < cknodes.length; i++) {
                if (cknodes[i].id != node.id) {
                    $('#tt1').tree("uncheck", cknodes[i].target);
                }
            }
            if (node.checked) {
                $('#tt1').tree('uncheck', node.target);

            } else {
                $('#tt1').tree('check', node.target);
            }
		},
		onLoadSuccess:function(node,data){
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
					$("#userNames").val("员工姓名");
				}
			}
			$(this).find('span.tree-checkbox').unbind().click(function () {
                $('#tt1').tree('select', $(this).parent());
                return false;
            });
			//如果叶子节点为部门的 checkbox隐藏
	           var roots =  $("#tt1").tree("getRoot");
	           var childs = $("#tt1").tree("getChildren",roots);
	           for( var i = 0 ; i < childs.length ; i++){
	        	   var item = childs[i];
	        	   if( item.attributes.type &&item.attributes.type == "G" ){
	        		   $(item.target).find(".tree-checkbox").remove();
	        	   }
	           }
		}
	});
	
	$("#tt2").tree({
		url:ctx+"/log/loguser/getModuleList",
		checkbox:true,
		onlyLeafCheck: true,
		onSelect:function(node,checked){
			var cknodes =$('#tt2').tree('getChecked', 'checked');
            for (var i = 0; i < cknodes.length; i++) {
                if (cknodes[i].id != node.id) {
                    $('#tt2').tree("uncheck", cknodes[i].target);
                }
            }
            if (node.checked) {
                $('#tt2').tree('uncheck', node.target);

            } else {
                $('#tt2').tree('check', node.target);
            }
		},
		onLoadSuccess:function(node,data){
			var oas = $("#groupIdStr").val();
			if(oas != null && oas != ''){
				var text='';
				$.each(oas.split(','),function(index,obj){
					var n = $("#tt2").tree("find",obj);
					text+=n.text+',';
					$("#tt2").tree("check",n.target).tree("expandTo",n.target);
				});
				if(text != ''){
					text=text.substring(0,text.length-1);
					$("#groupNameStr").val(text);
				}else{
					$("#groupNameStr").val("模块");
				}
			}
			$(this).find('span.tree-checkbox').unbind().click(function () {
                $('#tt2').tree('select', $(this).parent());
                return false;
            });
			//如果叶子节点为部门的 checkbox隐藏
	           var roots =  $("#tt2").tree("getRoot");
	           var childs = $("#tt2").tree("getChildren",roots);
	           for( var i = 0 ; i < childs.length ; i++){
	        	   var item = childs[i];
	        	   if( item.attributes.type &&item.attributes.type == "G" ){
	        		   $(item.target).find(".tree-checkbox").remove();
	        	   }
	           }
		}
	});

	// 表格载入数据
	loadData();


	//查询
	$("#operationform").submit(function(e){
		e.stopPropagation();
		e.preventDefault();
		loadData();
	})
	$(".ajax-table").on("click","tr",function(e){
		e.stopPropagation();
		if($(this).attr("systemoperateid")){//如果有该属性则是销售管理页面 弹窗
			var systemoperateid=$(this).attr("systemoperateid");
			var id=$(this).attr("id");
			window.parent.pubDivShowIframe("salesmanage_operation_log",ctx+"/log/loguser/getSysOPerateDate?id="+id,"销售管理",800,550)
		}
	})
});


function loadData(page){

    var _param = $('#operationform').serialize();
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
	var _url = ctx+'/log/loguser/myLoguserOperate';
	//	var _param = $('#operationform').serialize();
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
			$("input[name='currentPage']").attr("readonly","readonly");
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
		names="帐号";
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
		groupNames="模块";
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
		$('#d_startDate').val('');
		$('#d_endDate').val('');
		$("#dDateType").val(i);
	}
