window.onload=function(){
	resetHeight();
};

function updateUpNum(_this,upNum){
	var old = parseInt($(_this).find("span").text());
	old= parseInt(old)+parseInt(upNum);
	$(_this).find("span").text(old);
}

$(function(){

	//表情计算字数
	$(document).on("click","#facebox img",function(e){
		e.stopPropagation();
		$(".areas").keyup();
	})
	
	//回复字数统计
	$(".areas").on("keyup",function(){
		var $this = $(this);
		var total = 200;
		var currentLength = $this.html().replace(/<img.*?>/gi,"字").length;
		var remindnum=total - currentLength ;
		if(remindnum <0){
			remindnum = 0 ;
		}
		$this.next().find(".remindme-fontnum>span").text(remindnum);
	})
	
	$(".areas").on("paste",function(e){
		e.stopPropagation();
		e.preventDefault();
	})
	
	//对回复进行回复
    $('.reply').click(function(e){
    	e.stopPropagation();
    	var $this = $(this) ; 
   	    $this.parent().parent().siblings('.reply-every-box').find('.reply-box').hide();
       	$this.parent().siblings('.reply-box').toggle();
       	//回复时候的高度比较特殊 重新设置需要加110px
      		resetHeight();
    });

    $(".up").click(function(){
    	_this = this;
    	var wlbId = $(this).attr("replyWlbId");
    	var upele=$(this).find(".upNum");
		prasefavour(wlbId,upele)
    });
    //点赞函数
    function prasefavour(wlbId,upele){
    	var num=Number(upele.text());
		$.ajax({
			url:ctx+"/worklog/favour",
			data:{
				'id':wlbId,
				'type':0,
				'v':new Date().getTime()
			},
			success:function(data){
				if(data.status == true){
					if(data.upNum == 1){
						num++;
						upele.text(num);
					}else{
						num--
						if(num<0){
							num=0;
						}
						upele.text(num);
					}
					fontColor();
				}else{
					window.parent.warmDivDialog("warm",data.errorMsg);
				}
			}
		})
	}
	//评论
	$(".com-btna").click(function(e){
		e.stopPropagation();
		var wliId = $("#wliId").val();
		var context_ = $(this).parent().siblings(".areas").html();
		context_=replace(context_);
		context_=context_.replace(/(^\s+)|(\s+$)/g,"");
		if(context_.length==0||context_=='<br>'){
			window.parent.parent.warmDivDialog('warm','评论不允许为空！');
		}else if(context_.length>200){
			window.parent.warmDivDialog('warm','评论最大长度为200汉字,表情占1个汉字！');
		}else{
			var replyWlbId = $(this).attr("replyWlbId");
			$.post(ctx+"/worklog/bbs/insertCommont", { "wliId": wliId,"replyWlbId":replyWlbId,"context":context_},function(data){
				if(data.status == "success"){
					window.parent.updateBbsNum(wliId);
					setTimeout(refreshBbs(),500);
				}else{
					window.parent.parent.warmDivDialog("warm","评论失败！");
				}
			});
		}
	});
		//删除评论
		$(".delete-bbs").click(function(e){
			e.stopPropagation();
			var wliId = $(this).attr("replyWlbId");
			var repId=$("#wliId").val();
			window.parent.queDivDialog("mylog_del_","是否删除这条回复？",function(){
				$.ajax({
					url:ctx+"/worklog/ bbs/del",
					data:{
						"id":wliId
					},
					success:function(data){
						if(data.status == true){
							window.parent.updateBbsNum(repId,true);
							window.parent.iDialogMsg("warm","删除成功！");
							setTimeout(refreshBbs(),500);
						}else{
							window.parent.warmDivDialog("warm",data.errorMsg);
						}
					}
				})
			})
		});
	//判断顶的字色
	fontColor()
	
	
	//点击回复和赞切换
	$(".log-goodreply-box").on("click",function(e){
		e.stopPropagation();
		$(".bbs-replay").toggle();
		$(".bbs-good").toggle();
		$(".log-goodreply-box").toggleClass("log-active");
		resetHeight();//从点赞切回回复列表要重置高度
	});
	
	loadAjax({'wlbId':$("#wliId").val()});//初始化载入列表
});
function replace(str){
	var reg=/<img .*?(\d+).gif".*?>/;
	while((result=reg.exec(str))!=null){
		str=str.replace(/<img.*?>/,"[qface:"+result[1]+"]");
	}
	str=str.replace(/&nbsp;/g," ");
	return str;
}

function page(num){
	$("#page").val(num);
	refreshBbs();
}

function refreshBbs(){
	$("form")[0].submit();
}

//判断顶的个数来修改字色
function fontColor(){
	var $ups=$(".up span");
	$ups.each(function(){
		var $this=$(this);
		var $pares=$this.parent();
		var upNum = $this.text();
		if(upNum>0){
			$pares.css("color","red");
		}else{
			$pares.css("color","#000");
		}
	});
}

function loadAjax(data){
	$.ajax({
		url:ctx+"/worklog/favourListJson",
		type:"get",
		data:data,
		cache:false,
		success:function(data){
			if(data.status){
				if(data.list.length>0){
					renderTable(data);//点赞列表渲染
					pageGood(data.item);//点赞分页
				}else{
					$(".bbs-goodlist-box").html("<div class='bortops bortop good-every-box log-good-nodata'>当前无点赞！</div>");
					pageGood(data.item);//点赞分页
				}
				//重置iframe高度
				resetHeight();
			}
		}
	})
}

function pageLoadData(current){
	var data = {
		'wlbId':$("#wliId").val(),
		'currentPage':current
	};
	loadAjax(data);
}

function pageGood(data){
	var page=data.page;
	var html="";
	html += '<a href="javascript:pageLoadData(1);" class="a-page">首页</a>';
	for(var i = 0 ; i < page.totalPage ; i++ ){
		var current = i +1; 
		html += '<a href="javascript:pageLoadData('+current+');" class="a-num '+(current == page.currentPage?"a-hover":"")+'">'+current+'</a>';
	}
	html += '<a href="javascript:pageLoadData('+page.totalPage+');" class="a-page">尾页</a>';
	$(".bbs-good .com-pagea").html(html);
}

function renderTable(data){
	var myTemplate = Handlebars.compile($("#template").html());
	$(".bbs-goodlist-box").html(myTemplate(data));
}

function resetHeight(){
	var height = $(".box").height()+30;
	window.parent.$(".iframepage01").css({"height":height+"px"});
}