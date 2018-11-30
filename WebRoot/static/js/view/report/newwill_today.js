$(function(){
	$("#tt1").tree({
		url:ctx+'/orgGroup/get_group_json',
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
});

function selGroup(){
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
}

function clearGroup(){
	var nodes = $('#tt1').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#tt1').tree("uncheck",obj.target);
	});
}