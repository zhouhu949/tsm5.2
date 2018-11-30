<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>工作日志-共享日志-普通用户</title>
<!--公共样式-->
<script type="text/javascript" src="${ctx}/static/js/base64.js${_v}"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css${_v}"><!--弹框样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/core.css"/><!--弹框插件样式-->
<script type="text/javascript" src="${ctx}/static/js/view/worklog/shareLog.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/dateUtils.js${_v}"></script>
<script type="text/javascript">
var resHeight = window.screen.height;
if(resHeight < 790){
  document.write('<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/date_768.css" />');
}
else{
  document.write('<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/date_900.css" />');
}

</script>
<script type="text/javascript">
function view(userId){

	$(".timeline ul").html("");
	$(".input-query").val("");//置空搜索框
	$(".my-member").iCheck('uncheck');//勾选状态取消
	$("#accountsStr").val("");//置空userIds（树选择出来的字段）
	$("#userId").val(userId);//将userid填入隐藏域 滚动请求数据时要用
	$("#logDateTime").val($("#keepSystemTime").val());//查询的时候需要把时间改为原系统时间
	var dataJson={
		"userId":userId,
		"logDateTime":$("#logDateTime").val()
	}
	dataQueryGo(dataJson)
}

$(function(){
	/*log加载*/
    $('.timeline').each(function(i,item){
        $('.hyx-wlm-cent-timeline').scroll(function(){
       		var $logflag = $("#logAjaxFlag");
            if($(this)[0].scrollTop + $(this).height() >= $(this)[0].scrollHeight){
            	if($logflag.val()){
					$logflag.val(false)
               		 dataScrollGo("last");
				}
            }

            if($(this)[0].scrollTop == 0){
            	if($logflag.val()){
					$logflag.val(false)
            	 	dataScrollGo("first");
				}
            }
         });
    });
      dataQueryGo({"logDateTime": $("#logDateTime").val()});
		//标签过多更多显示
		showsMore("tag-box","focus-tag-list","shows-more");//我的关注
		function showsMore(tagBox,tagList,show) {
			var tagBox = $("."+tagBox);
			var tagList = $("."+tagList);
			var showMore = $("."+show);
			var flag=true;
			if (tagBox.height() > tagList.height()) {
				showMore.show();
				showMore.click(function(e){
					e.stopPropagation();
					if(flag){
						tagList.height("auto");
						showMore.text("收起");
						flag=false;
					}else{
						tagList.height(20);
						showMore.text("更多");
						flag=true;
					}
				})
			}
		}
	});
	/* window.onload = function() {
		var height = $(".hyx-wlm-cent").height() + 30;
		window.parent.$("#iframepage").css({
			"height" : height + "px"
		});
	} */
</script>

<link rel="stylesheet" type="text/css" href="${ctx}/static/css/worklog/newMyLog.css${_v}"/>
</head>
<body>
	<form id="sharelog-form">
	<input type="hidden" id="logDateTime" name="logDateTime" value="${item.logDateTime }">
	<input type="hidden" id="keepSystemTime"  value="${item.logDateTime }">
	<input type="hidden" id="userId" name="userId" value="${logDateTime }">
	<input type="hidden" id="logAjaxFlag"  value="true">
		<div class="hyx-wlm-cent hyx-wlm-centa sty-borcolor-a com-radius fl_l">
			<div class="hyx-wlm-cent-bg sty-bgcolor-b"></div>
			<div class="hyx-wlm-cent-box">
				<div class="hyx-wlm-cent-btn"></div>
				<div class="hyx-wlm-cent-timeline">
					<div class="hyx-wlm-cent-toptitles">
						<div class="com-search hyx-cm-search" style="width:791px;">
							<div class="com-search-box fl_l">
								<div class="search clearfix">
									<input class="input-query fl_l" id="queryText" type="text" name="queryText" value="${item.queryText }" style="margin:0;" placeholder="输入姓名/帐号">
								</div>
							</div>
							<c:if test="${isManager }">
								<div class="only-my fl_r" style="margin-left:10px;">
									<input id="isAll" name="isAll" type="checkbox" class="my-member"  value="true" ${item.isAll?'checked':''}/><span class="my-member-text">只看下属成员</span>
									<div class="reso-sub-dep fl_l lookat-my-member-box">
									<input id="userNames"  type="hidden" value="">
									<input id="accountsStr" name="userIdsStr" type="hidden" value="">
										<div class="manage-owner-sour">
											<div class="sub-dep-ul" data-type="search-tree">
												<ul id="tt1">

										        </ul>
								    		</div>
										    <div class="sure-cancle clearfix" style="width:120px">
												<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" onclick="seleTree()" href="javascript:;"><label>确定</label></a>
												<a class="com-btnd bomp-btna fl_l" onclick="unCheckTree()" href="javascript:;"><label>清空</label></a>
											</div>
										</div>
								</div>
								</div>
							</c:if>
							<input id ="query" class="query-button fl_l" type="button" value="查&nbsp;询" id="query" />
						</div>
						<div class="focus-tag-list">
							<span>我的关注：</span>
							<div class="tag-box">
								<c:forEach var="attention" items="${attentions}">
									<a href="javascript:view('${attention.attentionUserId}');" class="tagName clickChoose">${attention.attentionUseName}</a>
								</c:forEach>
							</div>
						</div>
						<c:if test="${isManager }">
							<div class="focus-tag-list1">
								<a href="javascript:;" class="shows-more1">更多</a>
								<span>今日未提交：</span>
								<div class="tag-box1">
									<a href="javascript:view('${attention.attentionUserId}');" class="tagName clickChoose">${attention.attentionUseName}</a>
								</div>
							</div>
						</c:if>
					</div>
					<div class="timeline">
						<ul>
							<!-- 日志容器 -->
						</ul>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
