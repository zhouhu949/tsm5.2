<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>工作日志-日志评论</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css${_v}"><!--弹框样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/date/css/core.css"/><!--弹框插件样式-->
<script type="text/javascript" src="${ctx}/static/js/popup_layer.js${_v}"></script>
<script type="text/javascript">
$(function(){
		$('#cry').qqFace({
		id : 'facebox',
		assign:'area',
		path:'${ctx}/static/js/qqface/arclist/'	//表情存放的路径
	});

	<c:forEach items="${list }" var="bbs1" varStatus="i">
		$('#cry_${bbs1.wlbId}').qqFace({
			id : 'facebox',
			assign:'area_${bbs1.wlbId}',
			path:'${ctx}/static/js/qqface/arclist/'	//表情存放的路径
		});
	</c:forEach>
})
</script>
<script type="text/javascript" src="${ctx}/static/js/view/worklog/bbsWindow.js${_v}"></script>
<link rel="stylesheet" href="${ctx}/static/js/qqface/css/reset.css${_v}">
<link rel="stylesheet" href="${ctx}/static/css/worklog/bbsWindow.css${_v}">
<script type="text/javascript" src="${ctx}/static/js/qqface/js/jquery.qqFace.js${_v}"></script>
</head>
<body>
	<form action="${ctx}/worklog/bbs/bbsWindow">
		<input id="wliId" name="wliId" value="${wliId }" type="hidden"/>
		<input id="page" name="page.currentPage" value="${item.page.currentPage}" type="hidden"/>
		<input id="totalResult" name="page.totalResult" value="${item.page.totalResult}" type="hidden"/>
	</form>
	<div class="box job-log-revi fl_l">
		<div class="arrow-a"><em>◆</em><span>◆</span></div>
		<div class="box-cont">
			<div class="areas" contenteditable="true" id="area" name="saytext"></div>
			<p class="cens cen posirela clearfix"><a id="cry" href="javascript:;" class="cry fl_l"></a><input type="button" value="发布" class="com-btna btnw55 fl_r"><span class="remindme-fontnum">还可以输入<span>200</span>字</span></p>
			<div class="bortops bortop  hole-replay-good"><span class="log-goodreply-box log-active"><span class="log-hole-replay">全部回复</span>(<span class="log-hole-replay-num">${log.commentNum == null ? 0 : log.commentNum}</span>) </span><span class="log-goodreply-box"><span class="log-hole-good">赞</span>(<span class="log-hole-good-num">${log.favourNum == null ? 0 : log.favourNum}</span>)</span></div>
				<div class="log-hole-replay-box bbs-replay">
					<c:if test="${not empty list}">
							<c:forEach items="${list }" var="bbs" varStatus="i">
								<div class="bortops bortop reply-every-box clearfix">
										<label class="con posirela">
											<span class="log-commit-head-icon">
												<img src="${bbs.imgUrl_}" alt="加载图片">
											</span>
											<a href="javascript:;" class="sty-color-b fl_l log-commit-user-name">
											${bbs.userName }(${bbs.userAccount })
											<c:if test="${not empty bbs.replyUserAccount}">
												<span class="log-reply-bwteen">回复：</span> 
												<span class="log-reply-username">${bbs.replyUserName }(${bbs.replyUserAccount })</span>
											</c:if>								
											<label class="btn"><span class="b fl_l"><fmt:formatDate value="${bbs.inputdate }" pattern="yyyy年MM月dd日 HH:mm:ss"/></span><span class="log-from-client">来自${bbs.devType}</span></label>
											</a>
										</label>
										<span class="fl_l log-reply-content">${bbs.context_ }</span>
										<p class="clearfix"><a replyWlbId="${bbs.wlbId }" href="javascript:;" class="delete-bbs b fl_r font-color">删除</a><label class="b fl_r marginLaR">|</label><a href="javascript:;" class="reply b fl_r font-color">回复</a><label class="b fl_r marginLaR">|</label><a replyWlbId="${bbs.wlbId }" upNum="1" href="javascript:;" class="up b fl_r font-color">赞(<span class="upNum">${bbs.upNum}</span>)</a></label></p>
									<div class="reply-box">
										<div id="area_${bbs.wlbId }" class="areas" contenteditable="true" ></div>
										<p class="cens cen posirela clearfix"><a id="cry_${bbs.wlbId }" href="javascript:;" class="cry fl_l"></a><input replyWlbId="${bbs.wlbId }" type="button" value="发布" class="com-btna btnw55 fl_r"><span class="remindme-fontnum">还可以输入<span>200</span>字</span></p>
									</div>
								</div>
							</c:forEach>
						</c:if>
						<c:if test="${empty list}">
							<div class="bortops bortop reply-every-box log-reply-nodata">当前无回复！</div>
						</c:if>
					</div>
				<div class="bbs-good bbs-goodlist-box" style="display:none;"><!-- 点赞列表盒子 -->
					
				</div>
				<div class="bortops bortop page-of-hole bbs-replay">
					<label class="com-pagea">
						<a href="javascript:page(1);" class="a-page">首页</a>
						<c:forEach var="i" varStatus="status"  begin="1" end="${item.page.totalPage}">
						   <a href="javascript:page(${i});" class="a-num ${i==item.page.currentPage?'a-hover':''}">${i}</a>
						</c:forEach>
						<a href="javascript:page(${item.page.totalPage });" class="a-page">尾页</a>
					</label>
				</div>
				<div class="bortops bortop page-of-hole bbs-good" style="display:none;">
					<label class="com-pagea">
					</label>
				</div>
		</div>
	</div>
	<script type="text/x-handlebars-template" id="template">
  {{#each list}}
	<div class="bortops bortop good-every-box">
		<span class="log-good-head-icon">
			<img src="${ctx}{{imgUrl_}}" alt="加载图片">
		</span>
		<p class="cens cen">
			{{userName}}赞了你的日志
		</p>
	</div>
 {{/each}}
</script>
</body>
</html>
