
$(function(){
	isSys = $("#isSys").val();
	isState = $("#isState").val();
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

    /* 表格默认选中第一条 */
    default_tr($('.com-table').find('tbody').find('tr:first'));

    /*下拉框部分*/
    $('.dt_list').click(function(){
        $(this).find('.drop').fadeToggle();
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
		 $(".manage-drop").hide();
	});


	// 跟进者查询
	if(isSys == 1){
	$("input[name=searchOwnerType_2]").iCheck({
    	radioClass: 'iradio_minimal-green'
	 });
    $("input[name=searchOwnerType_2]").on('ifClicked',function(){
    	var nodes = $('#tt2').tree('getChecked');
    	if(nodes.length > 0){
    		$.each(nodes,function(index,obj){
    			 $('#tt2').tree("uncheck",obj.target);
    		});
    	}
    });
	var url2 =ctx+"/orgGroup/get_all_group_user_json";
	$("#tt2").tree({
		url:url2,
		checkbox:true,
		onLoadSuccess:function(node,data){
			var $faccs = $('#faccs').val();	
			$("#tt2").tree("collapseAll");
			for(var i=0;i<data.length;i++){
				$("#tt2").tree("expand",data[i].target);
			}
			var searchOwnerType2 = $("input[name=searchOwnerType_2]:checked").val();
			if(searchOwnerType2 == '1'){
				$("#faccNames").val("查看全部");
			}else if(searchOwnerType2 == '2'){
				$("#faccNames").val("查看自己");
			}else if($faccs != null && $faccs.length > 0){
				var text='';
				$($faccs.split(',')).each(function(index,data){
					var node = $("#tt2").tree("find",data);
					if(node != null){
						text+=node.text+',';
						$("#tt2").tree("check",node.target);
					}				
				});
				if(text != ''){
					text=text.substring(0,text.length-1);
					$("#faccNames").val(text);
				}else{
					$("#faccNames").val("跟进人");
				}
			}
		},
		onCheck:function(node,isCheck){
			var nodes = $('#tt2').tree('getChecked');
			if(nodes.length > 0){
				$("input[name=searchOwnerType_2]").iCheck('uncheck');
			}
		}
	});
	}

// 选择点保存
$("#checkedId_2").click(function() {
	var nodes =$("#tt2").tree("getChecked");
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
		$("#fosType").val("3");
	}else{
		var searchOwnerType2 = $("input[name=searchOwnerType_2]:checked").val();
		if(searchOwnerType2 == '1'){
			text="查看全部";
			$("#fosType").val("2");
		}else if(searchOwnerType2 == '2'){
			text="查看自己";
			$("#fosType").val("2");
		}else{
			text="跟进人";
			$("#fosType").val("3");
		}
	}	
	$("#faccs").val(accs);
	$("#faccNames").val(text);	
});
//清空
$('#clearId_2').click(function(){
	var nodes = $('#tt2').tree('getChecked');
	 $(nodes).each(function(index,obj){
    	$('#tt2').tree('uncheck', obj.target);
    });
    $('#faccs').val('');
    $("#faccNames").val('');
	 $(".manage-drop").hide();
});
	

//共有者查询
if(isSys == 1){
$("input[name=searchOwnerType_3]").iCheck({
	radioClass: 'iradio_minimal-green'
 });
$("input[name=searchOwnerType_3]").on('ifClicked',function(){
	var nodes = $('#tt3').tree('getChecked');
	if(nodes.length > 0){
		$.each(nodes,function(index,obj){
			 $('#tt3').tree("uncheck",obj.target);
		});
	}
});
var url3 =ctx+"/orgGroup/get_group_user_json";
$("#tt3").tree({
	url:url3,
	checkbox:true,
	onLoadSuccess:function(node,data){
		var $comaccs = $('#comaccs').val();	
		$("#tt3").tree("collapseAll");
		for(var i=0;i<data.length;i++){
			$("#tt3").tree("expand",data[i].target);
		}
		var searchOwnerType3 = $("input[name=searchOwnerType_3]:checked").val();
		if(searchOwnerType3 == '1'){
			$("#comaccNames").val("共有者-查看全部");
		}else if(searchOwnerType3 == '2'){
			$("#comaccNames").val("共有者-查看自己");
		}else if($comaccs != null && $comaccs.length > 0){
			var text='';
			$($comaccs.split(',')).each(function(index,data){
				var node = $("#tt3").tree("find",data);
				if(node != null){
					text+=node.text+',';
					$("#tt3").tree("check",node.target);
				}				
			});
			if(text != ''){
				text=text.substring(0,text.length-1);
				$("#comaccNames").val(text);
			}else{
				$("#comaccNames").val("共有者");
			}
		}
	},
	onCheck:function(node,isCheck){
		var nodes = $('#tt3').tree('getChecked');
		if(nodes.length > 0){
			$("input[name=searchOwnerType_3]").iCheck('uncheck');
		}
	}
});
}

//选择点保存
$("#checkedId3").click(function() {
var nodes =$("#tt3").tree("getChecked");
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
	$("#comosType").val("3");
}else{
	var searchOwnerType2 = $("input[name=searchOwnerType_3]:checked").val();
	if(searchOwnerType2 == '1'){
		text="共有者-查看全部";
		$("#comosType").val("2");
	}else if(searchOwnerType2 == '2'){
		text="共有者-查看自己";
		$("#comosType").val("2");
	}else{
		text="共有者";
		$("#comosType").val("3");
	}
}	
$("#comaccs").val(accs);
$("#comaccNames").val(text);	
});
//清空
$('#clearId3').click(function(){
var nodes = $('#tt3').tree('getChecked');
 $(nodes).each(function(index,obj){
	$('#tt3').tree('uncheck', obj.target);
});
$('#comaccs').val('');
$("#comaccNames").val('');
$(".manage-drop").hide();
});

	// 客户卡片点击的时候
	$("table").on('click',"[name='add_custInfo']", function(){	
		var custId = $(this).attr("custId");
		var custName = $(this).attr("custName");
		var custName1 = custName||'客户卡片';
		window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName1);
	});
	
 	//  最近联系时间
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
 
 	// 下次联系时间
    $('#d2').dateRangePicker({
    	showShortcuts:false,
        format: 'YYYY-MM-DD'
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
});

function leftListClick(){
	$('.hyx-fur-tr').each(function(t,item_t){
    	$(item_t).click(function(){
    		loadRecordRight($(this).attr('cust_id'),$(this).attr('name'));
    	});    	
    });
}
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
	var _url=ctx+"/cust/custFollow/List/followRecordListJson";
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
		if(i == 0){
			// 取第一条客户资源ID，加载右侧资源信息 
			html +="<input type='hidden' id='resCustId_' value='"+follow.resCustId+"'>"+
						 "<input type='hidden' id='followId_' value='"+follow.custFollowId+"'>";
			}
		
		html +=
		"<tr class='hyx-fur-tr "+(i%2==0?'':'sty-bgcolor-b')+"' name='"+follow.custFollowId+"' cust_id='"+follow.resCustId+"'>"+
		"<td style='width:40px;'><div class='overflow_hidden w40 link'><a href='javascript:;' class='icon-cust-card' name='add_custInfo' custId='"+follow.resCustId+"' ";
		if(isState == 0 && emptyTransfer(follow.company) != ''){
			html += "custName='"+emptyTransfer(follow.company)+"'";
		}else{
			html += "custName='"+emptyTransfer(follow.custName)+"'";
		}
		html +="title='客户卡片'></a></div></td>";		
		html += "<td><div class='overflow_hidden w90' title='"+follow.showLastActionDate+"'>"+follow.showLastActionDate+"</div></td>";
		if(isState == 0){
			html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(follow.company)+"'>"+emptyTransfer(follow.company)+"</div></td>";
		}else{
			html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(follow.custName)+"'>"+emptyTransfer(follow.custName)+"</div></td>";
		}	
		if(isState == 0){
			html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(follow.custName)+"'>"+emptyTransfer(follow.custName)+"</div></td>";
		}else{
			html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(follow.linkName)+"'>"+emptyTransfer(follow.linkName)+"</div></td>";
		}
		html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(follow.custTypeName)+"'>"+emptyTransfer(follow.custTypeName)+"</div></td>";
		
		html += "<td><div class='overflow_hidden w90'>";
		if(follow.custStatus == 6){
			html+= "签约客户";
		}else if(follow.custStatus == 7){
			html+= "沉默客户";
		}else if(follow.custStatus == 8){
			html+= "流失客户";
		}else if(follow.custStatus == 5){
			html+= "意向客户";
		}
		html+="</div></td>";
		html += "<td><div class='overflow_hidden w100'>"+emptyTransfer(follow.optionName)+"</div></td>";
		html+= "	<td><div class='overflow_hidden w50' >";
		if(follow.effectiveness==1){
			html+= "是";
		}else if(follow.effectiveness==0){
			html+= "否";
		}
		html+="</div></td>";		
		html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(follow.groupName)+"'>"+emptyTransfer(follow.groupName)+"</div></td>";	
		html+= "<td><div class='overflow_hidden w90' title='"+follow.showNextActionDate+"'>"+follow.showNextActionDate+"</div></td>";		
		html += "<td><div class='overflow_hidden w70' title='"+emptyTransfer(follow.ownerName)+ "'>"+emptyTransfer(follow.ownerName)+"</div></td>";
		html+="<td><div class='overflow_hidden w90' title='"+emptyTransfer(follow.followAcc)+"'>"+emptyTransfer(follow.followAcc)+"</div></td>";
		html += "<td><div class='overflow_hidden w90'>";
		if(follow.followType == 1){
			html+= "电话联系";
		}else if(follow.followType == 2){
			html+= "会客联系";
		}else if(follow.followType == 3){
			html+= "客户来电";
		}else if(follow.followType == 4){
			html+= "短信联系";
		}else if(follow.followType == 5){
			html+= "邮件联系";
		}else if(follow.followType == 6){
			html+= "QQ联系";
		}
		html+="</div></td>";
		html += "<td><div class='overflow_hidden w120' phone='tel' title='"+emptyTransfer(follow.custMobilephone)+"'>"+emptyTransfer(follow.custMobilephone)+"</div></td>";
		html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(follow.area)+"'>"+emptyTransfer(follow.area)+"</div></td>";
		html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(follow.companyTrade)+"'>"+emptyTransfer(follow.companyTrade)+"</div></td>";
		html +="</tr>";
	}
	$(".ajax-table>tbody").html(html);
	
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
	
    // 异步加载 右侧信息
  	loadRecordRight($("#resCustId_").val(),$("#followId_").val());
	leftListClick();
	 /* 表格默认选中第一条 */
   default_tr($('.com-table').find('tbody').find('tr:first'));
}


var setDdate = function(i){
	$('#d_lastStartActionDate').val('');
	$('#d_lastEndActionDate').val('');
	$("#dDateType").val(i);
}	

var setNdate = function(i){
	$('#nextStartActionDate').val('');
	$('#nextEndActionDate').val('');
	$("#nDateType").val(i);
}	

/** 异步加载 右侧列表信息 */
function loadRecordRight(reCustId,followId){
	 $.ajax({
			url: ctx+'/cust/custFollow/List/followListRight',
			type:'POST',
			data:{'reCustId':reCustId,'followId':followId,'showCount':'1'},
			dataType:'html',
			error:function(){alert("网络异常，请稍后再操作！")},
			success:function(data){
				$('#record_right').html(data);
			}
		});
}


//表格默认选中第一条
function default_tr(tab){
    tab.find('td').removeClass('td-link');
    tab.find('td').addClass('td-hover');
    tab.find('td:first').css({'border-left-color':'#469bff'});
    tab.find('td:last').css({'border-right-color':'#469bff'});
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