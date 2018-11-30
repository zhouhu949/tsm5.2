$(function(){
	$(".com-moTag").find("a").click(function(){
		$("#noteType").val($(this).attr("nt"));
		$(this).parent().find(".e-hover").removeClass("e-hover");
		$(this).addClass("e-hover");
		document.forms[0].submit();
	});
	// 列表排序

	$('#d1').dateRangePicker({
		showShortcuts:false,
        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
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
        $("#s_sDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
    });
	
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
    
	//所有者
    if($("#tt1").length > 0){
    	var ownerUrl = ctx+"/orgGroup/get_group_user_json";
    	if($("#noteType").val() == 2){
    		ownerUrl = ctx+"/orgGroup/get_all_sale_user_json";
    	}
		$("#tt1").tree({
			url:ownerUrl,
			checkbox:true,
			onLoadSuccess:function(node,data){
				if($("#noteType").val() == 2){
		    		$(".shows-allorme-boxs").hide();
		    	}
				$("#tt1").tree("collapseAll");
				for(var i=0;i<data.length;i++){
					$("#tt1").tree("expand",data[i].target);
				}
				var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
				var oas = $("#ownerAccsStr").val();
				if(searchOwnerType == '1'){
					$("#ownerNameStr").val("查看全部");
				}else if(searchOwnerType == '2'){
					$("#ownerNameStr").val("查看自己");
				}else if(oas != null && oas != ''){
					var text='';
					$.each(oas.split(','),function(index,obj){
						var n = $("#tt1").tree("find",obj);
						if(n != null){
							text+=n.text+',';
							$("#tt1").tree("check",n.target).tree("expandTo",n.target);
						}
					});
					if(text != ''){
						text=text.substring(0,text.length-1);
						$("#ownerNameStr").val(text);
					}else{
						$("#ownerNameStr").val("所有者");
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
    
    if($("#tt2").length > 0){
    	$("#tt2").tree({
			url:ctx+"/orgGroup/get_all_sale_user_json",
			checkbox:true,
			onLoadSuccess:function(node,data){
				$("#tt2").tree("collapseAll");
				for(var i=0;i<data.length;i++){
					$("#tt2").tree("expand",data[i].target);
				}
				var userIds = $("#userIdsStr").val();
				if(userIds != null && userIds != ''){
					var text='';
					$.each(userIds.split(','),function(index,obj){
						var n = $("#tt2").tree("find",obj);
						if(n != null){
							text+=n.text+',';
							$("#tt2").tree("check",n.target).tree("expandTo",n.target);
						}
					});
					if(text != ''){
						text=text.substring(0,text.length-1);
						$("#inputerName").val(text);
					}else{
						$("#inputerName").val("提交人");
					}
				}
			}
		});
    }
    
    $('.hyx-cmc-td').mouseover(function(){
        $(this).find('.drop').fadeIn(1);
        var dropTop = $(this).find('.drop').offset().top;
        var tableTop = $('.com-table').offset().top;
        var dropH = $(this).find('.drop').height();
        var tableH = $('.com-table').height();
        var tdH = $(this).height();
        var drop_table = dropTop-tableTop;
        var drop_top_num = tdH-dropH;
        var arrow_top_num = dropH - tdH - 5;
        if(drop_table+dropH >= tableH){
        	$(this).find('.drop').css({'top':drop_top_num+'px'});
        	$(this).find('.arrow').css({'top':arrow_top_num+'px'});
        }

    });
    $('.hyx-cmc-td').mouseleave(function(){
        $(this).find('.drop').fadeOut(1);
    });
    
});
//设置签约日期查询
var setSdate = function(i){
	$('#s_startDate').val('');
	$('#s_endDate').val('');
	$("#s_sDateType").val(i);
}

//签约者查询条件树 确定
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
		$("#osType").val("3");
	}else{
		var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
		if(searchOwnerType == '1'){
			names="查看全部";
		}else{
			names="查看自己";
		}
		$("#osType").val(searchOwnerType);
	}
	$("#ownerAccsStr").val(accs);
	$("#ownerNameStr").val(names);
}
//查询条件树 取消
var unCheckTree=function(tid){
	var nodes = $('#'+tid).tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#'+tid).tree("uncheck",obj.target);
	});
}

var seleInputerTree=function(){
	var nodes = $('#tt2').tree('getChecked', 'checked');
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
		names="提交人";
	}
	$("#userIdsStr").val(accs);
	$("#inputerName").val(names);
}

