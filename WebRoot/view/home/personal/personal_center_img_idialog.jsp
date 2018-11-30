<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html >
<head>
    <meta charset="UTF-8">
    <title>头像上传（弹窗）</title>
    <%@ include file="/common/include-home-cut-version.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/cropper/dist/cropper.css${_v}"/><!--可移动弹框插件公共css-->
     <link rel="stylesheet" type="text/css" href="${ctx}/static/css/personal-center.css${_v}"/>
    <script type="text/javascript" src="${ctx}/static/thirdparty/cropper/dist/cropper.js${_v}"></script>
     <script type="text/javascript" src="${ctx}/static/js/view/home/personal_center_imgedit.js${_v}"></script>
</head>
<body>
	<input type="hidden" id="orgId" value="${orgId }">
	<input type="hidden" id="account" value="${account }">
	<input type="hidden" id="tsmUpLoadServiceUrl" value="${tsmUpLoadServiceUrl }">
	<input type="hidden" id="aaa" value="${imgUrl }">
	<div class="project-comp-idialog-box">
		<div class="personal-center-img-headbtn">
			<button type="button" class="use-local-img">本地图片
				<input type="file" class="file" id="files"/>
			</button>
			请上传JPG，GIF，PNG格式的文件，建议使用高质量图片
		</div>
		<div class="hyx-layout">
			<div class="hyx-layout-content personal-center-imgedit-item">
				<div class="personal-center-imgedit-item-box">
				 	<img src="${empty imgUrl ? ctx : ''}${empty imgUrl ? '/static/images/header.png' : imgUrl}" class="edit-img">
				</div>
			</div>
		</div>
	</div>
	<div class="bomb-btn bomb-btn-top bomb-btn-change project-comp-idialog-btnbox personal-imgedit-bottomBtn" >
				<label class="bomb-btn-cen">
					<a href="###" id="saveResBtn" class="com-btna bomp-btna sure-btn com-btna-sty fl_l"><label>保存</label></a>
					<a href="###" id="cacleResBtn" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
				</label>
		</div>
</body>
</html>
