<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<%@ include file="/common/include-cut-version.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/font-awesome-4.7.0/css/font-awesome.min.css${_v}"/>
</head>
<body>
	<div class="hyx-cm-box hyx-layout">
		<div class="tabTagBox hyx-layout-side">
			<ul class="tabTagList">
				<li id="tag02">
					<a class="will-cust" href="${ctx }/cust/custFollow/List/custFollowListContent?status=2" target="custFollowIframe">意向客户</a>
					<span class="operate-area"><i class="fa fa-sort-down"></i></span>
					<ul class="arrow-down-ul">
						<c:forEach items="${options }" var="os">
							<li><a href="${ctx }/cust/custFollow/List/custFollowListContent?status=2&saleProcessId=${os.optionlistId}"  target="custFollowIframe" title="${os.optionName }">${os.optionName }</a></li>
						</c:forEach>
					</ul>
				</li>
				<li id="tag03" ><a class="sign-cust" href="${ctx }/cust/custFollow/List/custFollowListContent?status=3" target="custFollowIframe">签约客户</a></li>
				<li id="tag04" ><a class="slience-cust"  href="${ctx }/cust/custFollow/List/custFollowListContent?status=4" target="custFollowIframe">沉默客户</a></li>
				<li id="tag05" ><a class="lost-cust" href="${ctx }/cust/custFollow/List/custFollowListContent?status=5" target="custFollowIframe">流失客户</a></li>
			</ul>
		</div>
		<div class="tabcon hyx-layout-content" style="padding:0;">
			<iframe src="${ctx }/cust/custFollow/List/custFollowListContent?status=2" name="custFollowIframe" width="100%" height="100%" id="iframepage" frameborder="0" scrolling="auto" style="overflow-x:hidden; overflow-y:hidden;" marginheight="0" marginwidth="0"></iframe>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/view/res/myCusts.js"></script>
</body>
</html>
