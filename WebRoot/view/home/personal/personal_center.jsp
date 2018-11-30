<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html >
<head>
    <meta charset="UTF-8">
    <title>个人中心页面</title>
    <%@ include file="/common/include-home-cut-version.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/font-awesome-4.7.0/css/font-awesome.min.css${_v}"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/personal-center.css${_v}"/>
    <script type="text/javascript" src="${ctx}/static/js/view/home/personal_center.js${_v}"></script>
</head>
<input type="hidden" id='tsmPayUrl' value="${tsmPayUrl}">
<input type="hidden" id='pay' value="${pay}">
<body class="personal-bigbox">
	<div class="hyx-layout">
		<div class="hyx-layout-side">
			<div class="personal-center-box-left boder">
				<div class="personal-center-box-left-imgbox boder">
					<%-- ${orgmemberuserdto.email }&&${orgmemberuserdto.mobile }&&${orgmemberuserdto.imgUrl } --%>
					<img src="${empty orgmemberuserdto.imgUrl ? ctx : ''}${empty orgmemberuserdto.imgUrl ? '/static/images/header.png' : orgmemberuserdto.imgUrl}" class="personal-center-box-left-img" title="更新头像"/>
					<div class="personal-center-box-left-username">${orgmemberuserdto.userName }</div>
					<div class="personal-center-box-left-mobile">
						<span class="fa fa fa-phone personal-center-icon"></span>
						<c:choose>
							<c:when test="${empty orgmemberuserdto.mobile }">
								<a href="javascript:;" class="personal-center-change-number">(绑定号码)</a>
							</c:when>
							<c:otherwise>
								${orgmemberuserdto.mobile }
							<a href="javascript:;" class="personal-center-change-number">(更换号码)</a>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="personal-center-box-left-email">
						<span class="fa fa-envelope personal-center-icon"></span>
						<c:choose>
							<c:when test="${empty orgmemberuserdto.email }">
								<a href="javascript:;" class="personal-center-change-email personal-center-change-email-bind">(绑定邮箱)</a>
							</c:when>
							<c:otherwise>
								${orgmemberuserdto.email }
							<a href="javascript:;" class="personal-center-change-email personal-center-change-email-change">(更换邮箱)</a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="personal-center-box-left-navbox">
					<div class="personal-center-box-left-items boder <c:if test="${pay ne 1}">active </c:if>"  data-url="${ctx }/user/toPersonal_center_information" data-edit="true">个人信息</div>
					<div class="personal-center-box-left-items boder <c:if test="${pay eq 1}">active </c:if>" data-url="${tsmPayUrl}/pay/index" data-pay = "true">我的钱包</div>
					<div class="personal-center-box-left-items boder" data-url="${ctx }/main/sale/levelGrowth" data-edit="false">销售成长</div>
					<div class="personal-center-box-left-items boder" data-url="${ctx }/user/toPersonal_center_preferential" data-edit="false" style="display:none;">优惠流量</div>
					<!--<div class="personal-center-box-left-items boder" data-url="${ctx }/user/toPersonal_center_hyxdownload" data-edit="false">慧营销APP下载</div>  -->
				    <c:if test="${orgId eq 'shtkc'}">
				    <div class="personal-center-box-left-items boder "  data-url="${ctx }/user/toPersonal_center_qrcode" data-edit="false">营销推广码</div>
				    </c:if>
				</div>
			</div>
		</div>
		<div class="hyx-layout-content" style="padding:0;">
			<div class="personal-center-box boder">
					<div class="personal-center-information-head">
						<c:choose>
							<c:when test="${pay eq 1 }">
								<span class="information-title">我的钱包</span>
								<span class="fa fa-edit personal-center-icon-edit" style="display:none;"></span>
							</c:when>
							<c:otherwise>
								<span class="information-title">个人信息</span>
								<span class="fa fa-edit personal-center-icon-edit"></span>
							</c:otherwise>
						</c:choose>
					</div>
					<c:choose>
						<c:when test="${pay eq 1 }">
							<iframe src="${tsmPayUrl}/pay/index" width="100%" height="100%" id="iframepage" frameborder="0" scrolling="auto" style="overflow-x:hidden; overflow-y:hidden;" marginheight="0" marginwidth="0"></iframe>
						</c:when>
						<c:otherwise>
							<iframe src="${ctx }/user/toPersonal_center_information" width="100%" height="100%" id="iframepage" frameborder="0" scrolling="auto" style="overflow-x:hidden; overflow-y:hidden;" marginheight="0" marginwidth="0"></iframe>
						</c:otherwise>
					</c:choose>
			</div>
		</div>
	</div>
</body>
</html>
