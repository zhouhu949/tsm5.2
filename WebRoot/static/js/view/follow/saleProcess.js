var isState
var isSys
$(function(){
	isState = $("#isState").val();	
	isSys = $("#isSys").val();
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

	/*所有者*/	
    if(isSys == 1){
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
    	var url =ctx+"/orgGroup/get_group_user_json";
    	$("#tt1").tree({
    		url:url,
    		checkbox:true,
    		onLoadSuccess:function(node,data){
    			var $accs = $('#accs').val();	
    			$("#tt1").tree("collapseAll");
				for(var i=0;i<data.length;i++){
					$("#tt1").tree("expand",data[i].target);
				}
				var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
				if(searchOwnerType == '1'){
					$("#accNames").val("查看全部");
				}else if(searchOwnerType == '2'){
					$("#accNames").val("查看自己");
				}else if($accs != null && $accs.length > 0){
    				var text='';
    				$($accs.split(',')).each(function(index,data){
    					var node = $("#tt1").tree("find",data);
    					if(node != null){
    						text+=node.text+',';
    						$("#tt1").tree("check",node.target);
    					}				
    				});
    				if(text != ''){
						text=text.substring(0,text.length-1);
						$("#accNames").val(text);
					}else{
						$("#accNames").val("所有者");
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

	
	// 选择点保存
	$("#checkedId").click(function() {
		var nodes =$("#tt1").tree("getChecked");
		var accs = "";
		var text='';
		$(nodes).each(function(index,obj){
			if(obj.attributes.type == 'M'){
				accs+=","+obj.id;
				text+=obj.text+',';
			}		
		});
		if(accs.length > 0){
			accs=accs.substring(1,accs.length);
			text=text.substring(0,text.length-1);			
			$("#osType").val("3");
		}else{
			var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
			if(searchOwnerType == '1'){
				text="查看全部";
			}else{
				text="查看自己";
			}
			$("#osType").val(searchOwnerType);
		}	
		$("#accs").val(accs);
		$("#accNames").val(text);
	});
	//清空
	$('#clearId').click(function(){
		var nodes = $('#tt1').tree('getChecked');
		 $(nodes).each(function(index,obj){
        	$('#tt1').tree('uncheck', obj.target);
        });
        $('#accs').val('');
        $("#accNames").val('');
	});

	// 列表排序

    // x下次联系时间
    $('#d1').dateRangePicker({
    	showShortcuts:false,
        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
    	var s_t = '';
    	var e_t = '';
    	if(obj.date1 != 'Invalid Date'){
    		$('#d_lastStartActionDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    		s_t = moment(obj.date1).format('YY.MM.DD');
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#d_lastEndActionDate').val(moment(obj.date2).format('YYYY-MM-DD'));
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
    

    //查询框宽度动态设置
//	var inputquer=$(".input-query");
//	var parewid=inputquer.parent().width();
//	var prevwid=inputquer.prev().width();
//	inputquer.css("width",parewid-prevwid+"px");
	
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
	var _url=ctx+"/cust/custFollow/List/custSaleProcessJson";
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
		var follow = list[i];		
		html +=
		"<tr class='hyx-fur-tr "+(i%2==0?'':'sty-bgcolor-b')+"'>"+
		'<td style="width:30px;"><div class="overflow_hidden w30 link">'+
		"<a href='javascript:;' class='icon-cust-card' name='add_custInfo' custId = '"+follow.resCustId+"' ";
		if(isState == 0 && emptyTransfer(follow.company) != ''){
			html += "custName='"+emptyTransfer(follow.company)+"'";
		}else{
			html += "custName='"+emptyTransfer(follow.custName)+"'";
		}
		html +="title='客户卡片'></a></div></td>"+
		'<td><div class="overflow_hidden w120" title="'+follow.showLastActionDate+'">'+follow.showLastActionDate+'</div></td>'+
		'<td><div class="overflow_hidden w80">';
		if(follow.actionType==1){
			html += '电话联系';
		}else if(follow.actionType==2){
			html += '会客联系';
		}else if(follow.actionType==3){
			html += '客户来电';
		}else if(follow.actionType==4){
			html += '短信联系';
		}else if(follow.actionType==5){
			html += 'qq联系';
		}else if(follow.actionType==6){
			html += '邮件联系';
		}
		html += '</div></td>'+
		'<td><div class="overflow_hidden w150" title="'+emptyTransfer(follow.custName)+'">'+emptyTransfer(follow.custName)+'</div></td>';
		if(isState == 0){
			html += '<td><div class="overflow_hidden w150" title="'+emptyTransfer(follow.company)+'">'+emptyTransfer(follow.company)+'</div></td>';
		}
		html += '<td><div class="overflow_hidden w120" title="'+emptyTransfer(follow.lastSaleProcess)+'">'+emptyTransfer(follow.lastSaleProcess)+'</div></td>'+
		'<td><div class="overflow_hidden w120" title="'+emptyTransfer(follow.optionName)+'">'+emptyTransfer(follow.optionName)+'</div></td>'+
		'<td><div class="overflow_hidden w160" title="'+emptyTransfer(follow.feedbackComment)+'">'+emptyTransfer(follow.feedbackComment)+'</div></td>'+
		'<td><div class="overflow_hidden w120" title="'+emptyTransfer(follow.groupName)+'">'+follow.groupName+'</div></td>'+
		'<td><div class="overflow_hidden w50" title="'+emptyTransfer(follow.ownerName)+'">'+follow.ownerName+'</div></td></tr>';
	}
	$(".ajax-table>tbody").html(html);
	
}

var setDdate = function(i){
	$('#d_lastStartActionDate').val('');
	$('#d_lastEndActionDate').val('');
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
};