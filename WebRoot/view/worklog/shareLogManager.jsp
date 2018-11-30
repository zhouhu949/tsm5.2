<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>工作日志-共享日志-管理人员</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css${_v}"><!--弹框样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/core.css"/><!--弹框插件样式-->
<style type="text/css">
.tree-title{width:52px;}
.tree-checkbox0{margin-top:6px;}
.tree-checkbox1{margin-top:6px;}
.tree-checkbox2{margin-top:6px;}
.select dd ul{width:152px;overflow-x:hidden;}
</style>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/shareLogManager.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/dateUtils.js${_v}"></script>
<script type="text/javascript">
/* window.parent.pageType="mannager";
window.parent.calendar(); */
var isAll=${item.isAll};

$(document).ready(function() {
	$("#tt1").tree({
		url:ctx+"/orgGroup/get_group_json",
		checkbox:true,
		onLoadSuccess:function(node,data){
			var oas = $("#groupIdsStr").val();
			if(oas != null && oas != ''){
				$.each(oas.split(','),function(index,obj){
					var n = $("#tt1").tree("find",obj);
					$("#tt1").tree("check",n.target).tree("expandTo",n.target);
				});
				getUsers(false);
			}
		},
		onCheck: function(node,checked){
			var nodes = $("#tt1").tree('getChecked', 'checked');
			var idArray = new Array();
			$.each(nodes,function(index,obj){
				idArray.push(obj.id);
			});
			$("#groupIdsStr").val(idArray.toString());
		}
	});
});

function getUsers(flag){
	var groupIdsStr = $("#groupIdsStr").val();
	var userIdsStr = $("#userIdsStr").val();
	var userArray=userIdsStr.split(",");
	$.post(ctx+"/worklog/share/findUsers", { "groupIdsStr": groupIdsStr},function(data){
		 if(data!=null && data.length>0){
			 var html='';
			 for(var i=0;i<data.length;i++){
				 if(flag){
					 html+='<li><input class="userId" type="checkbox" value="'+data[i].userId+'" checked="true"/><label>'+data[i].userName+'</label></li>';
					 
				 }else{
					 html+='<li><input class="userId" type="checkbox" value="'+data[i].userId+'"'+ (contains(userArray,data[i].userId)||isAll?'checked="true"':'')+'/><label>'+data[i].userName+'</label></li>';
				 }
			 }
			 $("#userList").html(html);
		 }else{
			 $("#userList").html('');
		 }
	});
}

function contains(userArray,userId){
	if(userArray==null ||userId==null ||userArray.length==0) return false;
 	for(var i=0;i<userArray.length;i++){
		if(userArray[i]==userId){
			return true;
		}
	}
	return false;
}

var resHeight = window.screen.height;
if(resHeight < 790){
  document.write('<link ruserIdel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/date_768.css" />');
}
else{
  document.write('<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/date_900.css" />');
}
</script>
<script type="text/javascript">
$(function(){
	/*log加载*/
    $('.timeline').each(function(i,item){
        $('.hyx-wlm-cent-timeline').scroll(function(){
            if($(this)[0].scrollTop + $(this).height() >= $(this)[0].scrollHeight){
                getData("last");
            }
            
            if($(this)[0].scrollTop == 0){
            	 getData("first");
            }
         });
    });
	
	/*log加载伸缩按钮*/
    $('.rightbox').each(function(i,item){
        if($(item).find('dd').height() <= 20){
        	$(item).find('.more').hide();
        }
        
        $(item).find('.more').click(function(){
            if($(this).find('a').text() == '展开'){
                $(this).parent().siblings('dl').css({'height':'auto'});
                $(this).find('a').text('收起');
                $(this).find('i').addClass('i-down');
            }else{
                $(this).parent().siblings('dl').css({'height':'30px'}); 
                $(this).find('a').text('展开');   
                $(this).find('i').removeClass('i-down'); 
            }
        });
        $(item).parent().parent().click(function(){
            $(this).addClass('li-hover').siblings().removeClass('li-hover');
        });
    });

	/*下拉框部分*/
    $(".hyx-wlm-right").find('.select').each(function(){
        var s=$(this);
        var z=parseInt(s.css("z-index"));
        var dt=$(this).children("dt");
        var dd=$(this).children("dd");
        var _show=function(){dd.slideDown(200);dt.addClass("cur");s.css("z-index",z+1);};   //展开效果
        var _hide=function(){dd.slideUp(200);dt.removeClass("cur");s.css("z-index",z);};    //关闭效果
        dt.click(function(){dd.is(":hidden")?_show():_hide();});
        
        $(document).click();
        $(document).click(function(i){
            !$(i.target).parents(".select").first().is(s) ? _hide():"";
        });
    });
	
	$("#isAll").change(function(){
		$("input[name='userIds']").attr("checked",false);
		$("#groupIdsStr").val('');
		$("#userIdsStr").val('');
		refresh();
	});
	
	$("#okBtn").click(function(){
		$("#isAll").attr("checked",false);
		getUsers(true);
	});
	
	$("#cancleBtn").click(function(){
		var nodes = $('#tt1').tree('getChecked', 'checked');
		$.each(nodes,function(index,obj){
			 $('#tt1').tree("uncheck",obj.target);
		});
		$("#groupIdsStr").val('');
		$("#userIdsStr").val('');
		$("#userList").empty();
	});
	
	$("body").delegate(".userId","click",function(e){
		$("#isAll").attr("checked",false);
	});
	
	$("#cfm_query").click(function(){
		var userIdsStr="";
		$(".userId:checked").each(function(i){
		   	if(i!=0) userIdsStr=userIdsStr+",";
		   	userIdsStr+=$(this).val();
		});
		$("#userIdsStr").val(userIdsStr);
		refresh();
	});
});

function refresh(){
	$("form")[0].submit();
}

window.onload=function(){
	var height = $(".hyx-wlm-cent").height()+30;
	window.parent.$("#iframepage").css({"height":height+"px"});
};
</script>
</head>
<body>
	<form action="${ctx }/worklog/share/shareLog${_v}">
	<input type="hidden" id="logDateTime" name="logDateTime" value="${item.logDateTime }">
	
	<div class="hyx-wlm-cent hyx-wlm-centb sty-borcolor-a com-radius fl_l">
		<div class="hyx-wlm-cent-bg sty-bgcolor-b"></div> 
		<div class="hyx-wlm-cent-box">
			<p class="hyx-wlm-cent-btn">
				<label class="fl_r">只看下属成员</label><input id="isAll" name="isAll" type="checkbox" value="true" class="check fl_r" <c:if test="${isAll}"> checked="true"</c:if>/>
				
			</p>
			<div class="hyx-wlm-cent-timeline">
		    	<c:choose>
		    	<c:when test="${shareLogs!=null and !empty shareLogs}">
		    	<div class="timeline">
		    	 <ul>
		    	<c:forEach items="${shareLogs }" var="log" varStatus="i">
		    	<li dateTime ="${log.logDateTime}"  class="li-load <c:if test="${i.first}"> li-hover </c:if><c:if test="${i.last}"> end </c:if>">
	        		<div class="left sty-borcolor-e fl_l"><label>${log.formatDay}</label><span>${log.formatMonth}</span></div>
	        		<div class="cir"></div>
	        		<div class="right">
	        			<div class="arrow"><em>◆</em><span>◆</span></div>
	        			<div class="rightbox">
	        				<div class="right-box">
	        					<span>${log.userName}(${log.userAccount})  ${log.inputDateStr}</span>
	        				</div>
	        				<dl>
	        					<dd><pre style="display:inline-block;width:100%;line-height:20px;white-space: pre-wrap;word-wrap: break-word;">${log.context}</pre></dd>
	        				</dl>
	        				<p><label class="more"><a href="javascript:;" class="sty-color-b fl_l">展开</a><i></i></label></p>
	        				<c:if test="${log.status ==1}">
	        					<div class="right-box"><a wliId="${log.wliId}"  href="javascript:;" class="comment b fl_r">评论&nbsp;<span id="commentNum_${log.wliId}">${log.commentNum}</span></a></div>
	        				</c:if>
	        			</div>
	      			</div>
		        </li>
		    	</c:forEach>
		    	</ul>
		    	</div>
		    	</c:when>
		    	<c:otherwise>
		    	<!-- 暂无数据开始 -->
				<div class="bomp-time" style="text-align:center;">
					<label class="hyx-mcn-bg"><span>暂无数据！</span></label>
				</div>
				<!-- 暂无数据结束 -->
		    	</c:otherwise>
		    	</c:choose>
			</div>
		</div>
	</div>
	<div class="hyx-wlm-right sty-borcolor-a sty-bgcolor-b com-radius fl_l" style="margin-left:6px;">
		<p class="tit">小组成员<p>
		
		<%-- <select id="groupSelect">
			<option value="">全部</option>
			<c:forEach var="group" items="${groups}">
				<option value="${group.groupId}">${group.groupName }</option>
	      	</c:forEach>
		</select> --%>
		
		<input id="groupIdsStr" name="groupIdsStr" type="hidden" value="${item.groupIdsStr}" >		
		<dl class="select" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-top:10px;">
				<dt>小组团队</dt>
				<dd>
					<ul id="tt1" class="easyui-tree" data-options="animate:true,dnd:false" style="width:152px;max-height:180px;overflow-x:hidden;">
					
					</ul>
					<div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;">
						<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" id="okBtn" href="javascript:;"><label style="height:19px;line-height:19px;">确定</label></a>
						<a class="com-btnd bomp-btna fl_l" id="cancleBtn" href="javascript:;"><label style="height:19px;line-height:19px;">清空</label></a>
					</div>
				</dd>
		</dl>
		
		<input id="userIdsStr" name="userIdsStr" type="hidden" value="${item.userIdsStr}" >
		<div class="list-scroll">
			<ul id="userList" class="list"></ul>
		</div>
		
		<div style="width:100px;height:25px;overflow:hidden;margin:0 auto;margin-top:10px;">
			<a href="javascript:;" class="com-btnc fl_l" id="cfm_query"><label>查询</label></a>
			<!-- <a href="javascript:;" class="com-btnd fl_r"><label>取消</label></a> -->
		</div>
	</div>
	</form>
</body>
</html>
