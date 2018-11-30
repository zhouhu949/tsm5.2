function updateBbsNum(wliId){
	var old = parseInt($("#commentNum_"+wliId).text());
	old++;
	$("#commentNum_"+wliId).text(old);
}
 
function buttonBind(){
	$("body").delegate(".comment","click",function(e){
		var wliId = $(this).attr("wliid");
		var iframepage01 = '<iframe src="'+ctx+'/worklog/bbs/bbsWindow?v='+new Date().getTime()+'&wliId='+wliId+'" class="iframepage01" frameborder="0" scrolling="no" width="100%" style="height:0;"></iframe>';
       	if($(this).parents('.rightbox').find('.iframepage01').length <=0){
       		$('.iframepage01').remove();
       		$(this).parents('.rightbox').append(iframepage01);
       	}else{
       		$(this).parents('.rightbox').find('.iframepage01').remove();
       	}
	});
}
//type first last
function getData(type){
	var dateTime = $('.timeline li:'+type).attr("datetime");
	var userIdsStr=$("#userIdsStr").val();
	$.post(ctx+"/worklog/share/shareLogJson", { "logDateTime": dateTime,"isAll":isAll,"userIdsStr":userIdsStr,"type":type},function(data){
		var item = data.item;
		var logs = data.logs;
		var html='';
		for (var i=0;i<logs.length;i++){
			var log = logs[i];
			html+='<li dateTime ="'+log.logDateTime+'" class="li-load'+(i==0?' li-hover ':'')+((i==logs.length&&type=="last")?' end ':'')+'">'+
			'	<div class="left sty-borcolor-e fl_l"><label>'+log.formatDay+'</label><span>'+log.formatMonth+'</span></div>'+
			'	<div class="cir"></div>'+
			'	<div class="right">'+
			'		<div class="arrow"><em>◆</em><span>◆</span></div>'+
			'		<div class="rightbox">'+
			'			<div class="right-box">'+
			'				<span>'+log.userName+'('+log.userAccount+')'+ log.inputDateStr+'</span>'+
			'			</div>'+
			'			<dl>'+
			'				<dd>'+log.context+'</dd>'+
			'			</dl>'+
			'			<p><label class="more"><a href="javascript:;" class="sty-color-b fl_l">展开</a><i></i></label></p>'+
			'				'+(log.status ==1?'<div class="right-box"><a wliId="'+log.wliId+'"  href="javascript:;" class="comment b fl_r">评论&nbsp;<span id="commentNum_'+log.wliId+'">'+log.commentNum+'</span></a></div>':'')+
			'		</div>'+
			'	</div>'+
			'</li>';
		}
		if(logs.length>0){
			$(".li-hover").removeClass("li-hover");
			if(type=="last"){
				$(".end").removeClass("end");
				$(".timeline ul").append(html);
			}else{
				$(".timeline ul").prepend(html);
			}
		}
	});
}

function init(){
	//渲染工作日志
  	buttonBind();  	
};

$(document).ready(function() {
	init();
});
