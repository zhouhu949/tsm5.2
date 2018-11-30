$(function(){
	getAjaxCardAction();
});

function getAjaxCardAction(){
	var state = $("#state").val();
	var type = $("#type").val();
	var custId = $("#custId").val();
	$.ajax({
		url:ctx+'/cust/card/data',
		type:'post',
		data:{custId:custId,state:state,type:type},
		dataType:'json',
		error:function(){},
		success:function(data){
			if(!$.isEmptyObject(data)){
				var data_all = '';
				for(key in data){
					var obj = data[key];
					var json_data = "<div class='timeline_all'>"
									+	"<div class='container'>"
									+	"<div class='timeline'>"
									+		"<div class='plus'>"+key+"</div>"
									+		"<div class='plus2'></div>"
									+	"</div>"
									+"</div>";
					$.each(obj,function(idx,o){
						//解析
						
					});
					json_data+="</div>";
					data_all+=json_data;
				}
				$('.hyx-cca-bot').append(data_all);
		    	/*时间轴显示*/
				$('.timeline_all').masonry({itemSelector : '.item'});
				Arrow_Points();
			}else{
				//空
//				alert('空');
			}
		}
	});
}

function Arrow_Points(){
	var s = $(".timeline_all").find(".item");
	$.each(s,function(i,obj){
		var posLeft = $(obj).css("left");
		if(posLeft == "0px"){
			html = "<span class='rightcorner'><label class='arrow'><em>◆</em><span>◆</span></label><label class='cir'></label></span>";
			$(obj).prepend(html);
		} else {
			html = "<span class='leftcorner'><label class='arrow'><em>◆</em><span>◆</span></label><label class='cir'></label></span>";
			$(obj).prepend(html);
		}
	});
}