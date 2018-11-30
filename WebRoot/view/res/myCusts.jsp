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
				<li id="tag01">
					<a class="current resource-cust" href="${ctx }/res/cust/myRests" target="myRestsIframe">资源</a>
				</li>
				<li id="tag02">
					<a class="will-cust" href="${ctx }/res/cust/myIntCusts" target="myRestsIframe">意向客户</a>
					<span class="operate-area"><i class="fa fa-sort-down"></i></span>
					<ul class="arrow-down-ul">
						<c:forEach items="${options }" var="os">
							<li><a href="${ctx }/res/cust/myIntCusts?saleProcessId=${os.optionlistId}"  target="myRestsIframe" title="${os.optionName }">${os.optionName }</a></li>
						</c:forEach>
					</ul>
				</li>
				
				<li id="tag03" ><a class="sign-cust" href="${ctx }/res/cust/mySignCusts" target="myRestsIframe">签约客户</a></li>
				<li id="tag04" ><a class="slience-cust" href="${ctx }/res/cust/mySilentCusts" target="myRestsIframe">沉默客户</a></li>
				<li id="tag05" ><a class="lost-cust" href="${ctx }/res/cust/myLosingCusts" target="myRestsIframe">流失客户</a></li>
				<li id="tag06" ><a class="all-cust"  href="${ctx }/res/cust/myAllTypeCusts" target="myRestsIframe">全部客户</a></li>
				<c:if test="${commonOnwerOpen eq 1 }">
					<li id="tag07"><a class="common-cust" href="${ctx }/res/cust/myCommonCusts" target="myRestsIframe">共有客户</a></li>
				</c:if>
			</ul>
		</div>
		<div class="tabcon hyx-layout-content" style="padding:0;">
			<iframe src="${ctx }/res/cust/myRests?dd" name="myRestsIframe" width="100%" height="100%" id="iframepage" frameborder="0" scrolling="auto" style="overflow-x:hidden; overflow-y:hidden;" marginheight="0" marginwidth="0"></iframe>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/view/res/myCusts.js"></script>
</body>
</html>
