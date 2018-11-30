$(function(){
	loadData();
	$("#tt1").tree({
		url:ctx+'/Resource/getGroupData',
		checkbox:true,
		onLoadSuccess:function(node,data){
			var oas = $("#groupIds").val();
			if(oas != null && oas != ''){
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
					$("#groupNameStr").text(text);
				}else{
					$("#groupNameStr").text("-请选择-");
				}
			}
		}
	});
	
	//选择
	$(".select").find("dt").live("click",function(e){
		e.stopPropagation();
		var isHidden = $(this).next().is(":hidden");
		if(isHidden){
			$(this).next().slideDown(200);
			$(this).next().addClass("cur");
		}else{
			$(this).next().slideUp(200);
			$(this).next().removeClass("cur");
		}
		return false;
	});
	$(".reso-owner-sure").live("click",function(){
		$(this).parent().parent().slideUp(200);
		$(this).parent().parent().removeClass("cur");
		return false;
	});
	$(".com-btna-cancle").live("click",function(){
		return false;	
	});
	
	$(document).click(function(e){
		e.stopPropagation();
		 $(".select dd").hide();
	});
	$(".selec-year-inpu").unbind('click').click(function(){
		WdatePicker({readOnly:true,onpicked:timeChange,changing:timeChange,oncleared:timeChange});
	});
	$("#resource_sure_btn").click(function(e){
		e.stopPropagation();
		var ids = '';
		var names = '';
		$($("#tt1").tree('getChecked', 'checked')).each(function(index,node){
			ids+=node.id+",";
			names+=node.text+",";
		});
		
		if(ids != ''){
			ids = ids.substring(0,ids.length-1);
			names = names.substring(0,names.length-1);
		}
		$("#groupIds").val(ids);
		$("#groupNameStr").text(names == '' ? '-请选择-' : names );
	});
	$("#resource_cancle_btn").click(function(e){
		e.stopPropagation();
		var nodes = $('#tt1').tree('getChecked', 'checked');
		$.each(nodes,function(index,obj){
			 $('#tt1').tree("uncheck",obj.target);
		});
	});
	$("#searchBtn").click(function(e){
		e.stopPropagation();
		loadData();
	});
	$("#export").click(function(){
		var data = $("#resurce_form").serialize();
		window.location.href=ctx+"/report/teamDayData/export?groupIds="+groupIds+"&expType=1";
	})
});
function timeChange(){
	$(".WdateDiv").hide();
}
function loadData(){
	var data = $("#resurce_form").serialize();
	loadAjax(data);
}
function loadAjax(data){
	searchingDataTip();
	var timeout = setTimeout(function(){
		$(".tip_search_text").hide();
		$(".tip_search_error").text("网络繁忙，请耐心等候或重新查询！");
		$(".tip_search_error").show();
	},10000);
	$.ajax({
		url:ctx+"/Resource/getAllResource",
		type:"get",
		data:data,
		cache:false,
		success:function(result){
			var renderList={list:result};
			renderTable(renderList);
			/* ***** *导出按钮的处理* ********/
			var exportbtn = $("#export");
			var exportUrl = exportbtn.data("href")+data;
			exportbtn.attr("href",exportUrl)
			/* ***** *导出按钮的处理* ********/
			$(".tip_search_data").hide();
			clearTimeout(timeout);
		},
		error:function(data){
			clearTimeout(timeout);
			$(".tip_search_text").hide();
			$(".tip_search_error").text("网络异常，请重新查询！");
			$(".tip_search_error").show();
		}
	})
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
	$("#t1 tbody").html(myTemplate(data));
}