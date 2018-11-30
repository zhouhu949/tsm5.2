<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js"></script>
<!-- 载入播放器资源文件 -->
<link href="${ctx}/static/js/jPlayer/skin/blue.monday/jplayer.blue.monday.css" rel="stylesheet" type="text/css" />
<style type="text/css"> 
      body{font-size:12px;}
      img{border:0;}
      .player_listview{width:300px;height:378px;border:1px solid #999;}
      .player_title{width:300px;height:39px;background:#2b3140;} 
      .player_middle{width:300px;height:39px;background:#f1f4f3;border-bottom:1px solid #eee;} 
      .player_bottom{width:300px;height:302px;overflow-y:auto;}
      .player_left{float:left;width:20px;height:37px;}
      .player_right{float:left;width:300px;height:37px;}
      .player_close,.player_minwindw{height:10px;margin-top:8px;margin-left:4px;}
      .player_closeimg{margin:0 atuo;width:8px;height:8px;display:inline-block;background:url(${ctx}/static/js/jPlayer/images/player_close.png) no-repeat;}
      .player_closeimg:hover{background:url(${ctx}/static/js/jPlayer/images/player_closehover.png) no-repeat;}
      .player_closehoverimg{margin:0 atuo;width:8px;height:8px;display:inline-block;background:url(${ctx}/static/js/jPlayer/images/player_min.png) no-repeat;}
      .player_closehoverimg:hover{background:url(${ctx}/static/js/jPlayer/images/player_minhover.png) no-repeat;}
      .player_up,.player_maxbutton,.player_down,.player_customer,.player_all,.player_deletelist{display:inline-block;vertical-align:top;margin-top:10px;}
      .player_up{width:17px;height:17px;margin-left:15px;margin-top:4px;margin-right:6px;background:url(${ctx}/static/js/jPlayer/images/player_up.png) no-repeat;}
      .player_up:hover{background:url(${ctx}/static/js/jPlayer/images/player_uphover.png) no-repeat;}
      .player_maxbutton{width:24px;height:24px;margin-top:6px;margin-left:6px;background:url(${ctx}/static/js/jPlayer/images/player_maxbutton.png) no-repeat;}
      .player_down{width:17px;height:17px;margin-top:4px;margin-left:6px;background:url(${ctx}/static/js/jPlayer/images/player_down.png) no-repeat;}
      .player_down:hover{background:url(${ctx}/static/js/jPlayer/images/player_downhover.png) no-repeat;}
      .player_all{width:16px;height:13px;margin-top:-16px;margin-left:240px !important;*margin-top:-33px;*margin-left:180px !important;margin-right:6px;background:url(${ctx}/static/js/jPlayer/images/player_all02.png) no-repeat;}
      .player_all:hover{background:url(${ctx}/static/js/jPlayer/images/player_all01.png) no-repeat;}
      .player_all_lineheight{width:16px;height:13px;margin-top:-16px;margin-left:240px !important;*margin-top:-33px;*margin-left:180px !important;margin-right:6px;background:url(${ctx}/static/js/jPlayer/images/player_all01.png) no-repeat;}
      .player_all_lineheight01{width:16px;height:13px;margin-top:4px;margin-left:240px !important;*margin-top:-35px;*margin-left:180px !important;margin-right:6px;background:url(${ctx}/static/js/jPlayer/images/player_all01.png) no-repeat;}
      .player_deletelist{width:11px;height:13px;margin-top:-14px;margin-left:270px;*margin-left:210px;background:url(${ctx}/static/js/jPlayer/images/player_deletelist.png) no-repeat;}
      .player_deletelist:hover{background:url(${ctx}/static/js/jPlayer/images/player_deletehover.png) no-repeat;}
      .player_customer{margin-left:50px !important;*margin-left:70px !important;margin-top:1px;*margin-left:70px !important;*margin-top:-20px;color:#fff;}
      .player_middle span{display:inline-block;height:39px;line-height:39px;margin-left:20px;font-size:14px;}
      .lelistplayer_tab{border-right:1px solid #eee;}
      .lelistplayer_tab tr td{border:0;border-bottom:1px solid #eee;padding:5px;}
      .player_selectmin{width:7px;height:8px;margin-right:4px;display:inline-block;background:url(${ctx}/static/js/jPlayer/images/player_selecctmin.png) no-repeat;}
      .player_selectmin:hover{background:url(${ctx}/static/js/jPlayer/images/player_minhover02.png) no-repeat;}
      .player_close03{width:9px;height:9px;margin-right:4px;display:inline-block;background:url(${ctx}/static/js/jPlayer/images/player_close03.png) no-repeat;}
      .player_close03:hover{background:url(${ctx}/static/js/jPlayer/images/player_closehover.png) no-repeat;}
      .player_download01{width:10px;height:9px;display:inline-block;background:url(${ctx}/static/js/jPlayer/images/player_download01.png) no-repeat;}
      .player_download01:hover{background:url(${ctx}/static/js/jPlayer/images/player_downloadhover.png) no-repeat;}
      .player_nochoose01{width:7px;height:8px;margin-right:4px;display:inline-block;background:url(${ctx}/static/js/jPlayer/images/player_mingray.png) no-repeat;}
      .player_nochoose02{width:9px;height:9px;margin-right:4px;display:inline-block;background:url(${ctx}/static/js/jPlayer/images/player_close.png) no-repeat;}
      .player_nochoose03{width:10px;height:9px;display:inline-block;background:url(${ctx}/static/js/jPlayer/images/player_download02.png) no-repeat;}
      </style>
      </head>
      <body>
      <div id="jquery_jplayer_1" class="jp-jplayer"></div>
      		<input type="hidden" name="base" value="${ctx}"/>
      		<input type="hidden" id="idReplaceWord" value="${idReplaceWord}"/>
      		<input type="hidden" id="indexxs" value="0"/><!-- 存储当前播放条数 -->
			<input type="hidden" id="listSize" value="${listSize}"/><!-- 存储进播放器播放哪一条 -->
			<input type="hidden" id="palyAll" value="1"/><!-- 1:全部播放，0:只播放当前 -->
      		<c:choose>
      		<c:when test="${empty callList}">
      			      		<div class="player_title">
      			<div class="player_right">
      				<div id="jp_container_1" class="jp-audio">
					<div class="jp-type-single">
      				<div class="jp-gui jp-interface">
					    <ul class="jp-controls">
						<li> <a href="javascript:void(0)" class="player_up"  id="last_play"></a></li>
						<li><a href="javascript:void(0)" class="jp-pause" tabindex="1" >暂停</a></li>		
						<li><a href="javascript:void(0)" class="player_down"  id="next_play"></a></li>
						<span class="player_customer" id="scroll"></span>
						<a href="javascript:void(0)" class="player_all_lineheight01"  title="取消播放全部" id="palys"></a>
						<a href="javascript:void(0)" class="player_deletelist" id="clearAll" title="清空"></a>
						</ul>												
					<div class="jp-progress">
						<div class="jp-seek-bar">
							<div class="jp-play-bar"></div>
						</div>
					</div>		
				</div>
				</div>
      			</div>
      		</div>		
      		</div>
      		</c:when>
      		<c:otherwise>
      			      		<div class="player_title">
      			<div class="player_right">
      				<div id="jp_container_1" class="jp-audio">
					<div class="jp-type-single">
      				<div class="jp-gui jp-interface">
					    <ul class="jp-controls">
						<li> <a href="javascript:void(0)" class="player_up"  id="last_play"></a></li>
						<li><a href="javascript:void(0)" class="jp-play" tabindex="1">播放</a></li>
						<li><a href="javascript:void(0)" class="jp-pause" tabindex="1" >暂停</a></li>		
						<li><a href="javascript:void(0)" class="player_down"  id="next_play"></a></li>
						<span class="player_customer" id="scroll"></span>
						<a href="javascript:void(0)" class="player_all_lineheight"  title="取消播放全部" id="palys"></a> 
						<a href="javascript:void(0)" class="player_deletelist" id="clearAll" title="清空"></a>
						</ul>												
					<div class="jp-progress">
						<div class="jp-seek-bar">
							<div class="jp-play-bar"></div>
						</div>
					</div>		
				</div>
				</div>
      			</div>
      		</div>		
      		</div>
      		</c:otherwise>
      		</c:choose>
      		<div class="player_middle">
        		<span>播放列表</span>    
      		</div>
      		<div class="player_bottom">
        		<table class="lelistplayer_tab"cellspacing="0" cellpadding="0" id="list">
        			 <c:set var="index" value="0"/>
        			 <c:forEach items="${callList}" var="call" varStatus="v">       					             
		              <tr callName="<c:out value="${call.custName}"/>" >  
		                    <td style="width:6px">&nbsp;</td>
		                    <td  id="cnm_${index}" style="width:90px; color:#909090" title="${call.custName}"><span style="display:inline-block;width:100%;">${call.custName}</span><span style="display:inline-block;width:100%;" name="callerNum_">${call.calledNum}</span></td>	                  
		                    <td style="width:25px;"><img id="img_${index}" src="${ctx}/static/js/jPlayer/images/player_present.png" alt="" style="display: none;margin-bottom:-3px;">&nbsp;</td>
		                    <td style="width:60px;">
		                    <a href="javascript:void(0)" name="play" v="${index}"  id="playing_${index}"  class="player_nochoose01" title="录音播放"></a>
		                    <a href="javascript:void(0)"  id="del_playing_${index}"  class="player_nochoose02" name="deletePlay" playId="${call.callId}" title="删除" ></a>
		                    <a href="${call.recordUrl}"  id="down_playing_${index}"  class="player_nochoose03" title="下载录音"></a>
		                    <input type="hidden" value="${call.recordUrl}"/>
			             	<c:set var="index" value="${index+1}"/>
		                    </td>		                    
		                    <td style="width:80px;">${call.callTimeShow}</td>
		              </tr>
		               </c:forEach>		               
        		</table>    
      </div>
  
<script type="text/javascript" src="${ctx}/static/js/jPlayer/js/jquery.jplayer.min.js"></script>

<script type="text/javascript">
var arrlist = new Array();
var base = "";
$(function(){
	var $listSize = $('#listSize').val();
	base = $.trim($("input[name='base']").val());
	
	// 列表样式更改
	$("#cnm_"+$listSize).css({ width: "90px", color: "#26ba60" });
	$("#img_"+$listSize).css("display","inline-block");
	$("#playing_"+$listSize).attr("class","player_selectmin");
	$("#del_playing_"+$listSize).attr("class","player_close03");
	$("#down_playing_"+$listSize).attr("class","player_download01");
	
	//遍历父窗口所有列表数据
	$("#list").find("tr").each(function(i){
		var khxm = $.trim($(this).attr("callName"));
		var mp3 = $.trim($(this).find("input[type='hidden']").val());
		json = {'khxm':khxm,'mp3':mp3};
		arrlist[i] = json;
	});
	
	if(arrlist.length > 0){
		if(arrlist[$listSize].khxm =="" || arrlist[$listSize].khxm =="" ){
			//字幕信息
			$("#scroll").text(arrlist[$listSize].khxm.substring(0,8));
			$("#palys").attr("class","player_all_lineheight01");
		}else{
			//字幕信息
			$("#scroll").text(arrlist[$listSize].khxm.substring(0,8));
			$("#palys").attr("class","player_all_lineheight");
		}
		
		//播放器初始化
		$("#jquery_jplayer_1").jPlayer({
			ready: function () {
				$(this).jPlayer("setMedia", {//设置要播放的MP3文件
					mp3:arrlist[$listSize].mp3
				});
				$(this).jPlayer("play");//自动加载播放
			},
			solution: "flash,html", // 支持flash和html 两种格式播放
			swfPath: "${ctx}/static/js/jPlayer/js/Jplayer.swf", 
			supplied: "mp3",
			wmode: "window",
			smoothPlayBar: true,
			keyEnabled: true,
			ended: function() { // 当前播放完后，播放下一条	 
				// 如果需要播放全部则 需要设置下一首播放 +1
				if($("#palyAll").val() == "1"){
					nextPlay();	
				}else{
					cleckPlay(parseInt($('#listSize').val()));
				}
	  		}
		});
		
		$("#last_play").click(function(){
			lastPlay();
		});
		
		$("#next_play").click(function(){
			nextPlay();
		});
		// 点击试听
		$("[name='play']").click(function(){
			$("#jquery_jplayer_1").jPlayer( "clearMedia");
			var index = $(this).attr("v");	
			$('#listSize').val(index);
			cleckPlay($('#listSize').val());
		});
	}
	
	 /* 屏蔽手机或固话中间几位  */
    var idReplaceWord = $("#idReplaceWord").val();	
	 
	if(idReplaceWord==1){
	    $("span[name=callerNum_]").each(function(){
	   		   var str = $(this).text();
	   		   replaceWord(str,$(this));
	   		   replaceTitleWord(str,$(this));
   		  });  
	}
	
// 播放下一条
function nextPlay(){	
	// 设置当前播放条数，如果是最后一条，则从头再播放	
	if($('#listSize').val() == arrlist.length-1){
		$('#listSize').val("0");
	}else{
		$('#listSize').val(parseInt($('#listSize').val())+1);
	}
	cleckPlay($('#listSize').val());
 }
	
	// 播放上一条
 function lastPlay(){	
	// 设置当前播放条数，如果是最后一条，则从头再播放	
	if($('#listSize').val()  == 0){
		$('#listSize').val(arrlist.length-1);
	}else{
		$('#listSize').val(parseInt($('#listSize').val())-1);
	}
	cleckPlay($('#listSize').val());
 }
	// 播放
 function cleckPlay (index){
		
	   $(".player_download01").attr("class","player_nochoose03");
	   $(".player_close03").attr("class","player_nochoose02");
	   $(".player_selectmin").attr("class","player_nochoose01");
	   $("[id^=img_]").css("display","none");
	   $("[id^=cnm_]").css({ width: "90px", color: "#909090" });
	   
		$("#cnm_"+index).css({ width: "90px", color: "#26ba60" });
		$("#img_"+index).css("display","inline-block");
		$("#playing_"+index).attr("class","player_selectmin");
		$("#del_playing_"+index).attr("class","player_close03");
		$("#down_playing_"+index).attr("class","player_download01");
		
		if(arrlist[index].khxm =="" || arrlist[index].khxm =="" ){
			//字幕信息
			$("#scroll").text(arrlist[index].khxm.substring(0,8));
			$("#palys").attr("class","player_all_lineheight01");
		}else{
			//字幕信息
			$("#scroll").text(arrlist[index].khxm.substring(0,8));
			$("#palys").attr("class","player_all_lineheight");
		}
		//设置Mp3文件

		$("#jquery_jplayer_1").jPlayer("setMedia",{
			mp3:arrlist[index].mp3
		});
		$("#jquery_jplayer_1").jPlayer("play"); // 播放
	}


// 播放全部按钮 切换
 $("#palys").toggle(function(){
		 $("#palyAll").val("0");
		 $(this).attr("class","player_all");
		 $(this).attr("title","点击全部播放");
		 },
	   	 function(){
		 $("#palyAll").val("1");
		 $(this).attr("class","player_all_lineheight");
		 $(this).attr("title","取消全部播放");}
	);
	// 删除
	$("[name='deletePlay']").click(function(){
		var id =  $(this).attr("playId"); 
		if(confirm("确定要删除吗？"))
		 {
			$.ajax({
				url : "${ctx}/call/delPlay.do?ids=" + id,
				type : 'post',
				dataType : 'json',
				error : function(XMLHttpRequest, textStatus) {
					dialogMsg(-1,'操作失败,请重试');
				},
				success : function(data) {
		           if(data == '0'){
		        	   window.location.href = "${ctx}/call/reloadCallPlay.do";		   
		           }else{
		        	   dialogMsg(-1,'操作失败,请重试');       
		           }
				}
			});	
		 }
		
	});
	
	// 清空
	$("#clearAll").click(function(){
		if(confirm("确定要清空吗？")){
			$.ajax({
				url : "${ctx}/call/clearPlay.do",
				type : 'post',
				dataType : 'json',
				error : function(XMLHttpRequest, textStatus) {
					dialogMsg(-1,'操作失败,请重试');
				},
				success : function(data) {
		           if(data == '0'){
		        	   window.location.href = "${ctx}/call/reloadCallPlay.do";		   
		           }else{
		        	   dialogMsg(-1,'操作失败,请重试');       
		           }
				}
			});	
		}
	});
	
});
</script>
</html>