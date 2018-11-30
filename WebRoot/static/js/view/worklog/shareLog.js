function updateBbsNum(wliId,type){
	var old = $("#commentNum_"+wliId).attr("num")?parseInt($("#commentNum_"+wliId).attr("num")):0;//判断是否存在num这个属性
	if(type){
		old--;
	}else{
		old++;
	}
	if(old <= 0){
		$("#commentNum_"+wliId).text("回复");
		$("#commentNum_"+wliId).attr("num",0);
	}else{
		$("#commentNum_"+wliId).text(old);
		$("#commentNum_"+wliId).attr("num",old);
	}
}

/*日志-点击分享按钮*/
function share_click(id){
	window.parent.pubDivShowIframe('shareWindow',ctx+'/worklog/share/window?v='+new Date().getTime()+'&type=1&wliId='+id,'分享日志',330,270);
}

function buttonBind(){
	//评论
	$("body").delegate(".comment","click",function(e){
		var wliId = $(this).attr("wliid");
		var iframepage01 = '<iframe src="'+ctx+'/worklog/bbs/bbsWindow?v='+new Date().getTime()+'&wliId='+wliId+'" class="iframepage01" name="bbsiframe" frameborder="0" scrolling="no" width="100%" style="height:0;"></iframe>';
       	if($(this).parents('.rightbox').find('.iframepage01').length <=0){
       		$('.iframepage01').remove();
       		$(this).parents('.rightbox').append(iframepage01);
       	}else{
       		$(this).parents('.rightbox').find('.iframepage01').remove();
       	}
	});
	//赞
	$("body").delegate(".favour","click",function(e){
		var wliId = $(this).attr("wliid");
		var $this = $(this);
		prasefavour(wliId,$this);
	});
	//查询
	$("body").delegate("#query","click",function(e){
		$(".timeline ul").html("");
		$(".my-member").iCheck('uncheck');//勾选状态取消 
		$("#useId").val("");//userId置空
		$("#accountsStr").val("");//置空userIds（树选择出来的字段）
		$("#logDateTime").val($("#keepSystemTime").val());//查询的时候需要把时间改为原系统时间
		dataQueryGo($("#sharelog-form").serialize())
	})
	//关注
	$("body").delegate(".myLog-focus","click",function(e){
		_this =this;
		var attentionUserId = $(_this).attr("userId");
		var type =null;
		var _clazz = $(_this).attr("class");
		if(_clazz.indexOf("focus-on")!=-1){
			type = "add";
		}else if(_clazz.indexOf("focus-off")!=-1){
			type = "cancel";
		}
		if(attentionUserId!=null && type!=null){
			$.post(ctx+"/worklog/attention", { "attentionUserId": attentionUserId,"type":type},function(data){
				if(data.status == "success"){
					if(type=='cancel'){
						val = $("#userId").val();
						if(attentionUserId!=null && attentionUserId.length!='' && attentionUserId==val){
							$("#userId").val('');
							$("form")[0].submit();
						}
					}

					$.each($("."+attentionUserId),function(i,item){
						if(type=='add'){
							$(item).removeClass("focus-on");
							$(item).addClass("focus-off");
							$(item).text("取消关注");
						}else{
							$(item).removeClass("focus-off");
							$(item).addClass("focus-on");
							$(item).text("+加关注");
						}
					});
				}else{
					window.parent.parent.warmDivDialog("提示",data.msg);
				}
			});
		}
	});
	//文件详情
	$("body").delegate(".files-click-idialog","click",function(e){
		e.stopPropagation();
		var b = new Base64()
		var json = $(this).data("json");
		var codes = JSON.stringify(json);
		var data = b.encode(codes);
		pubDivShowIframe("log_files_idialog",ctx+"/worklog/fileList?data="+data,"文件详情",400,400)
	})
	//查看图片详情
	$("body").delegate(".app-style-pic-box","click",function(e){
		e.stopPropagation();
		var b = new Base64()
		var params = b.encode($(this).data("img"))
		pubDivShowIframe("look_at_img",ctx+"/report/workSign/imgView?params="+params,"查看图片",700,500)
	})
	//播放器播放录音
	$("body").delegate(".log-sound-play-box","click",function(e){
		e.stopPropagation();
		var $this = $(this);
		var url = $this.data("src");
		var plength = $this.data("plength").toString();
		var code = $this.data("code");
		signLogPlayRecords(url,code,plength,"1");
	})
}

//点赞函数
function prasefavour(wliId,$this){
	$.ajax({
		url:ctx+"/worklog/favour",
		data:{
			'id':wliId,
			'type':1,
			'v':new Date().getTime()
		},
		success:function(data){
			if(data.status == true){
				var old = $("#favourNum_"+wliId).attr("num")?parseInt($("#favourNum_"+wliId).attr("num")):0;//判断是否存在num这个属性
				if(data.upNum == -1){
					old--;
					$this.find(".sharecomback").toggleClass("praise").toggleClass("praised")
					if(old <= 0){
						$("#favourNum_"+wliId).text("赞");
						$("#favourNum_"+wliId).attr("num",0);
					}else{
						$("#favourNum_"+wliId).text("("+old+")");
						$("#favourNum_"+wliId).attr("num",old);
					}
				}else if(data.upNum == 1){
					$this.find(".sharecomback").toggleClass("praise").toggleClass("praised")
					old++;
					$("#favourNum_"+wliId).text("("+old+")");
					$("#favourNum_"+wliId).attr("num",old);
				}
			}else{
				window.parent.warmDivDialog("warm",data.errorMsg);
			}
			//如果此时回复box打开状态则更新
			if($this.parents('.rightbox').find('.iframepage01').length >0){
	       		//document.getElementById("bbsiframe").location.reload();
	       		bbsiframe.window.location.reload();
	       	}
		}
	})
}

//查询和第一次请求 获取数据方式
function dataQueryGo(data){
	getData(data,false);
}
//滚动获取数据方式
function dataScrollGo(type){
	var dateTime = $(".right:"+type).attr("datetime");
	$("#logDateTime").val(dateTime);
	var data=formToJson( $("#sharelog-form").serialize() );
	data.type = type ;
	getData(data,true);
}

//type first last
function getData(datato,flag){
	$.post(ctx+"/worklog/share/shareLogJson",datato,function(data){
		var dtos=data.dtos;
		var html='';
		for (var i=0;i<dtos.length;i++){
			var dto = dtos[i];
			var lengths=dto.logs.length;
			if($("#"+dto.logDate).length != 0){
				//插入已有日期
				var html="";
				var newlilog = false;
				for(var j=0;j<lengths;j++){
					var log=dto.logs[j];
					html +=	'	<div class="right rightsmagin" dateTime="'+log.logDateTime+'">'+
					'		<div class="rightbox">'+
					'<div class="right-box shows-outta-name">'+
					'<span>'+log.userName+'('+log.userAccount+') '+(log.attention ? '<span userId = "'+log.userId+'" class="myLog-focus focus-off  '+log.userId+'">取消关注</span>' : '<span userId = "'+log.userId+'" class="myLog-focus focus-on '+log.userId+'">+加关注</span>' )+'</span>'+
					'</div>'+
					'<div class="right-box shows-outta-from">'+
					'<span><b>'+log.inputDateStr+'</b></span>'+
					'<span class="log-from-where">来自'+log.devType+'</span>'+
					'				'+(log.status ==0?'<a href="javascript:edit(\''+log.wliId+'\');" class="a work-log-d sty-color-b fl_r">编辑</a>':'')+
					'</div>'+
					'<div class="shows-outta-icon">'+
					'<img src="'+log.imgUrl_+'" alt="加载图片">'+
					'</div><div class="log-dl-outer-div">'+
					'			<dl class="log-content-box">今日工作总结'+
					'				<dd><pre style="display:inline-block;width:100%;line-height:20px;white-space: pre-wrap;word-wrap: break-word;">'+log.context+'</pre></dd>'+
					'			</dl>';
					if(log.workPlan != "" && log.workPlan != null){
						html+='			<dl class="log-content-box">明日工作计划'+
									'				<dd><pre style="display:inline-block;width:100%;line-height:20px;white-space: pre-wrap;word-wrap: break-word;">'+log.workPlan+'</pre></dd>'+
									'			</dl>';
						}
						html += '</div><p><label class="more"><a href="javascript:;" class="sty-color-b fl_l">展开</a><i></i></label></p>';
					if(log.devType != "PC端" && log.devType != "PC客户端" && log.devType != null){
						if(log.imgs != '' && log.imgs != null){
							html+='<div class="app-style-pic-box" data-img="'+log.imgs+'">';
							var urlArr = log.imgs.split("#");
							for(var k = 0 ;k<urlArr.length ; k++){
								html+='	<img src=" '+ urlArr[k] + '" alt="加载图片">';
							}
							html+='</div>';
						}
						if(log.record != '' && log.record != null){
							html+=	'<div class="app-style-sound-box">'+
							'		<div class="play-sound-filesbox log-sound-play-box" data-src="'+log.record+'" data-plength="'+log.recordTime+'" data-code = "'+playSoundGetOnlyUuid()+'">'+
							'		<span class="icon-play"></span><span>录音</span><span>'+log.recordTime+'秒</span>'+
							'	</div>'+
							'</div>';
						}
						if(log.files != "" && log.files != null){
							var file = dealAppData(log.files);
							html+='<div class="app-style-files-box"><div class="play-sound-filesbox  files-click-idialog" data-json='+JSON.stringify(file)+'>';
							html+='	<span>附件：</span><span>'+file.fileTotal+'</span><span>个附件</span><span>共</span><span>'+translateSize(file.fileTotalSize,"M")+'M</span><span class="fa fa-chevron-right files-show"></span>';
							html+= '</div></div>';
						}
					}
					html+=
					(log.status ==1?'<div class="right-box"><a href="javascript:share_click(\''+log.wliId+'\');" class="b fl_r"><span  class="sharecomback showshare"></span>'+(log.shareNum > 0? '<span class="log-right-comshare" id="shareNum'+log.wliId+'" num="'+log.shareNum+'">('+log.shareNum+')</span>':'<span class="log-right-comshare" id="shareNum'+log.wliId+'">分享</span>')+'</a><a href="javascript:;" class="favour b fl_r" wliid="'+log.wliId+'"><span '+(log.favourNum > 0? 'class="sharecomback '+(log.favour? "praised":"praise")+'"></span><span class="log-right-comshare" id="favourNum_'+log.wliId+'" num="'+log.favourNum+'">('+log.favourNum+')</span>':'class="sharecomback '+(log.favour? "praised":"praise")+'"></span><span class="log-right-comshare" id="favourNum_'+log.wliId+'">赞</span>')+'</a><a wliId="'+log.wliId+'"  href="javascript:;" class="comment b fl_r w60"><span   class="sharecomback commit"></span>'+(log.commentNum > 0? '<span class="log-right-comshare" id="commentNum_'+log.wliId+'" num="'+log.commentNum+'">('+log.commentNum+')</span>':'<span class="log-right-comshare" id="commentNum_'+log.wliId+'">回复</span>')+
					'				</a></div>':'')+
					'		</div>'+
					'	</div>';
				}
			}else{
				//新增 LI 日期
				var html="";
				var newlilog = true; //
				html+='<li id='+dto.logDate+' class="li-load'+(i==0?' li-hover ':'')+((i==(dtos.length-1)&&flag&&datato.type=="last")?' end ':'')+'">';
				for(var j=0;j<lengths;j++){
					var log=dto.logs[j];
					if(j==0){
						html+='	<div class="left sty-borcolor-e fl_l"><label>'+dto.formatDay+'</label><span>'+dto.formatMonth+'</span></div>'+
						'	<div class="cir"></div>'+
						'	<div class="right" dateTime="'+log.logDateTime+'">'+
						'		<div class="rightbox">';
					}else{
						html+='	<div class="right rightsmagin" dateTime="'+log.logDateTime+'">'+
						'		<div class="rightbox">';
					}
					html+='<div class="right-box shows-outta-name">'+
					'<span>'+log.userName+'('+log.userAccount+') '+(log.attention ? '<span userId = "'+log.userId+'" class="myLog-focus focus-off  '+log.userId+'">取消关注</span>' : '<span userId = "'+log.userId+'" class="myLog-focus focus-on  '+log.userId+'">+加关注</span>' )+' </span>'+
					'</div>'+
					'<div class="right-box shows-outta-from">'+
					'<span><b>'+log.inputDateStr+'</b></span>'+
					'<span class="log-from-where">来自'+log.devType+'</span>'+
					'				'+(log.status ==0?'<a href="javascript:edit(\''+log.wliId+'\');" class="a work-log-d sty-color-b fl_r">编辑</a>':'')+
					'</div>'+
					'<div class="shows-outta-icon">'+
					'<img src="'+log.imgUrl_+'" alt="加载图片">'+
					'</div><div class="log-dl-outer-div">'+
					'			<dl class="log-content-box">今日工作总结'+
					'				<dd><pre style="display:inline-block;width:100%;line-height:20px;white-space: pre-wrap;word-wrap: break-word;">'+log.context+'</pre></dd>'+
					'			</dl>';
					if(log.workPlan != "" && log.workPlan != null){
						html+='			<dl class="log-content-box">明日工作计划'+
									'				<dd><pre style="display:inline-block;width:100%;line-height:20px;white-space: pre-wrap;word-wrap: break-word;">'+log.workPlan+'</pre></dd>'+
									'			</dl>';
						}
						html += '</div><p><label class="more"><a href="javascript:;" class="sty-color-b fl_l">展开</a><i></i></label></p>';
					if(log.devType != "PC端" && log.devType != "PC客户端" && log.devType != null){
						if(log.imgs != '' && log.imgs != null){
							html+='<div class="app-style-pic-box" data-img="'+log.imgs+'">';
							var urlArr = log.imgs.split("#");
							for(var k = 0 ;k<urlArr.length ; k++){
								html+='	<img src=" '+ urlArr[k] + '" alt="加载图片">';
							}
							html+='</div>';
						}
						if(log.record != '' && log.record != null){
							html+=	'<div class="app-style-sound-box">'+
							'		<div class="play-sound-filesbox log-sound-play-box" data-src="'+log.record+'" data-plength="'+log.recordTime+'" data-code = "'+playSoundGetOnlyUuid()+'">'+
							'		<span class="icon-play"></span><span>录音</span><span>'+log.recordTime+'秒</span>'+
							'	</div>'+
							'</div>';
						}
						if(log.files != "" && log.files != null){
							var file = dealAppData(log.files);
							html+='<div class="app-style-files-box"><div class="play-sound-filesbox  files-click-idialog" data-json='+JSON.stringify(file)+'>';
							html+='	<span>附件：</span><span>'+file.fileTotal+'</span><span>个附件</span><span>共</span><span>'+translateSize(file.fileTotalSize,"M")+'M</span><span class="fa fa-chevron-right files-show"></span>';
							html+= '</div></div>';
						}
					}
					html+=
						'				'+(log.status ==1?'<div class="right-box"><a href="javascript:share_click(\''+log.wliId+'\');" class="b fl_r"><span class="sharecomback showshare"></span>'+(log.shareNum > 0? '<span class="log-right-comshare" id="shareNum'+log.wliId+'" num="'+log.shareNum+'">('+log.shareNum+')</span>':'<span class="log-right-comshare" id="shareNum'+log.wliId+'">分享</span>')+'</a><a href="javascript:;" class="favour b fl_r" wliid="'+log.wliId+'"><span '+(log.favourNum > 0? 'class="sharecomback '+(log.favour? "praised":"praise")+'"></span><span class="log-right-comshare" id="favourNum_'+log.wliId+'" num="'+log.favourNum+'">('+log.favourNum+')</span>':'class="sharecomback '+(log.favour? "praised":"praise")+'"></span><span class="log-right-comshare" id="favourNum_'+log.wliId+'">赞</span>')+'</a><a wliId="'+log.wliId+'"  href="javascript:;" class="comment b fl_r w60"><span  class="sharecomback commit"></span>'+(log.commentNum > 0? '<span class="log-right-comshare" id="commentNum_'+log.wliId+'" num="'+log.commentNum+'">('+log.commentNum+')</span>':'<span class="log-right-comshare" id="commentNum_'+log.wliId+'">回复</span>')+
					'				</a></div>':'')+
					'		</div>'+
					'	</div></div>';
				}
				html+='</li>';
			}
			if(flag){//是否滚动请求数据
				if(newlilog){//是否新增li日期
					if(datato.type=="last"){
						$(".timeline ul").append(html);
						$("li.end").removeClass("end");
						openCloseBtn();
					}else{
						$(".timeline ul").prepend(html);
						openCloseBtn();
					}
				}else{
					console.log(log.inputDateStr)
					if(datato.type=="last"){
						$("#"+dto.logDate).append(html);
						openCloseBtn();
					}else if(datato.type == "first"){
						$("#"+dto.logDate).find(".right:first").addClass("rightsmagin").before(html);//在原日志之前插入新日志并给没有rightsmagin的元素加类名保持样式一致
						$("#"+dto.logDate).find(".right:first").removeClass("rightsmagin");//在插入后第一个日志的rightmagin类名去除
						openCloseBtn();
					}
				}
			}else{
				$(".timeline ul").append(html);
				openCloseBtn();
			}
		}
		noDataHandle(flag,lengths);
		 $("#logAjaxFlag").val(true);
	});
}

function noDataHandle(flag,lengths){
	//对无数据时候的dom 结构的处理
	var noDataHtml='<div class="bomp-time" style="text-align:center;">'+
								'<label class="hyx-mcn-bg"><span>暂无数据！</span></label>'+
								'</div>';
	
		if(!flag ){//如果不是滚动请求数据
			if(lengths>0){
				$(".timeline").show();
				$(".bomp-time").hide();
			}else{//如果没有请求到数据
				$(".timeline").hide();
				if($(".hyx-wlm-cent-timeline .bomp-time").length > 0){
					$(".hyx-wlm-cent-timeline .bomp-time").show();
				}else{
					$(".hyx-wlm-cent-timeline").append(noDataHtml);
				}
			}
		}
}

function init(){
	//渲染工作日志
  	buttonBind();
  	
  	//未提交人员列表
  	$.post(ctx+"/worklog/queryNoLogUsers", { "dateTime": $("#logDateTime").val()},function(data){
		if(data.status){
			var html="";
			var selectTime = moment(Number($("#logDateTime").val())).format("YYYY-MM-DD");
			var nowTime = moment(new Date().getTime()).format("YYYY-MM-DD");
			var flag=moment(selectTime).isSame(nowTime,"day");
			if(flag){//如果是今天
				$(".focus-tag-list1>span").html("今日未提交：");
			}else{
				$(".focus-tag-list1>span").html(data.dateFlag+"未提交：")
			}
			for(var i=0;i<data.data.length;i++){
				html+='<a href="javascript:view(\''+data.data[i].userId+'\');" class="tagName clickChoose">'+data.data[i].userName+'</a>'
			}
			$(".tag-box1").prepend(html);
			tagBoxDeal();//处理人员过多时候的函数
		}
	});
  	
  	//更多
  	$(".shows-more1").on("click",function(e){
  		e.stopPropagation();
  		var $this = $(this);
  		var box = $this.parent().find(".tag-box1");
  		if( box.height() > 20 ){//如果盒子元素高度超过
  			box.css("height","20px");
  			$this.text("更多");
  		}else{
  			box.css("height","auto");
  			$this.text("收起");
  		}
  	})
};
//处理未提交人员过多的情况
function tagBoxDeal(){
	var box = $(".tag-box1");
	var child = $(".shows-more1");
	if( box.height()  > 20  ){//如果盒子元素的高度超过20  
		child.show();
		box.css("height","20px");
	}else{
		child.hide();
	}
}

$(function() {
	init();
	//查看下属成员树
	$("#tt1").tree({
		url:ctx+"/orgGroup/get_group_user_json",//需要更改url  查看下属成员
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
					$("#userNames").val("帐号");
				}
			}
		}
	});
	

	//表单优化并监听change事件
	$('.my-member').on('ifClicked ifChanged', function(e){
			var $showbox=$(".lookat-my-member-box");
			if(this.checked){
				$showbox.show();
			}else{
				$showbox.hide();
			}
	    }).iCheck({
	    	checkboxClass : 'icheckbox_minimal',
			radioClass : 'iradio_minimal',
			increaseArea : '20%'
	     });
	//点击document隐藏box,点击box区域不影响
	$(".lookat-my-member-box").on("click",function(e){
		e.stopPropagation();
	});
	$(document).on("click",function(){
		$(".lookat-my-member-box").hide();
	})
	$('.my-member-text').on('click',function(e){
		e.stopPropagation();
		if(!$('.my-member')[0].checked){//如果勾选状态为未勾选不作处理
			return false;
		}
		$(".lookat-my-member-box").toggle();
	})
});

var seleTree=function(){
	var nodes = $('#tt1').tree('getChecked', 'checked');
	var accs = "";
	var names = "";
	$.each(nodes,function(index,obj){
		var type = obj.attributes.type;
		if(type == "M"){
			accs+=obj.user_id+",";
			names+=obj.text+",";
		}
	});
	if(accs != ""){
		accs=accs.substring(0,accs.length-1);
		names=names.substring(0,names.length-1);
	}else{
		names="";
	}
	$("#accountsStr").val(accs);
	$("#userNames").val(names);
	
	//树点击确定之后的载入数据
	$(".timeline ul").html("");
	$(".input-query").val("");//置空搜索框
	$("#useId").val("");//userId置空
	$("#logDateTime").val($("#keepSystemTime").val());//查询的时候需要把时间改为原系统时间
	dataQueryGo($("#sharelog-form").serialize());
	$(".lookat-my-member-box").hide();
}

var unCheckTree=function(){
	var nodes = $('#tt1').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#tt1').tree("uncheck",obj.target);
	});
}

function openCloseBtn() {
	$('.rightbox').each(function(i, item) {
		var flag= false;
		$(item).find(".log-dl-outer-div").each(function(i,item){
			if($(item).height() > 200){
				$(item).height(200);
				flag = true;
			}else{
				$(item).css("height","auto");
				flag = false;
			}
		})
		if(flag){
			$(item).find(".more").show();
		}else{
			$(item).find(".more").hide();
		}

		$(item).find('.more').click(function() {
			if ($(this).find('a').text() == '展开') {
				$(this).parent().siblings(".log-dl-outer-div").css({
					'height' : 'auto'
				});
				$(this).find('a').text('收起');
				$(this).find('i').addClass('i-down');
			} else {
				$(this).parent().siblings(".log-dl-outer-div").css({
					'height' : '200px'
				});
				$(this).find('a').text('展开');
				$(this).find('i').removeClass('i-down');
			}
		});
		$(item).parent().parent().click(function() {
			$(this).addClass('li-hover').siblings().removeClass('li-hover');
		});
	});
}
function formToJson(data){//序列化表格元素转json
    data= decodeURIComponent(data,true);//防止中文乱码  
    data = data.replace(/&/g, "','" );
    data = data.replace(/=/g, "':'" );
    data = "({'" +data + "'})" ;
    obj = eval(data);
    return obj;
}

function dealAppData(fileStr){
	var returnJson = {};
	var fileArr = fileStr.split("#");
	returnJson.fileTotal = fileArr.length;//文件总数
	var base = new Base64();
	var size = 0;
	returnJson.fileList = [];
	for(var i = 0;i<fileArr.length;i++){
		var fileitem = {};
		//var currFile = base.decode(fileArr[i]);
		var currFile = fileArr[i];
		var result = currFile.split("<_>");
		size += Number(result[2]);
		fileitem.size=Number(result[2]);
		fileitem.type=result[0];
		fileitem.href=result[1];
		fileitem.name = result[3];
		returnJson.fileList.push(fileitem);
	}
	returnJson.fileTotalSize = size;
	return returnJson;
}
function translateSize(num,type){
	if(type == "M"){
		return (Number(num)/1024/1024).toFixed(2);
	}if(type == "K"){
		return (Number(num)/1024).toFixed(2);
	}
}

function playSoundGetOnlyUuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 32; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
 
    var uuid = s.join("");
    return uuid;
}
