<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
	<% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
	<!--公共样式-->
	<link rel="stylesheet" href="${ctx}/static/css/default.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/static/css/button.css" type="text/css" />	
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--主要样式-->
<%-- 	<link rel="stylesheet" type="text/css" href="${ctx}/static/js/idialog/skins/iblue.css"> --%>
	<link type="text/css" rel="stylesheet" href="${ctx }/static/css/upload.css" />
	<style>
	#content{width:100%;margin:15px 0 0 0;}
	.swfupload{display:inline-block;float:left;width:200px;height:20px;line-height:20px;border:solid #d6d6d6 1px;}
	</style>
    <script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
<%-- 	<script type="text/javascript" src="${ctx}/static/js/file-up/html5.js"></script> --%>
	<%-- <script type="text/javascript" src="${ctx}/static/js/swfupload/swfupload.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/swfupload/handlers.js"></script>	 --%>

	<script type="text/javascript">
		var account = '${shrioUser.account}';
		var orgId = '${shrioUser.orgId}';
		var userId = '${shrioUser.id}';
		var tsmUpLoadServiceUrl = '${tsmUpLoadServiceUrl}'; // <!-- 上传服务器路径 -->
		 var totalFileLength, totalUploaded, fileCount, filesUploaded;
		function onFileSelect(e) {
			var files = e.target.files; // FileList object
			var output = [];
			fileCount = files.length;
			totalFileLength = 0;
			for ( var i = 0; i < fileCount; i++) {
				var file = files[i];
				var names = file.name;
				var sarr = names.split(".");
				var lastOne = sarr.pop();
				var Mbs=file.size/(1024*1024);
				if(Mbs > 20 ){
					alert("文件大小超过限制！")
					return false;
				}
			 	if (lastOne !== "mp3" && lastOne !== "spx" ) {
					alert("只允许上传mp3、spx格式的文件！");
					return false;
				} 
				output.push(file.name, ' (', file.size, ' bytes, ',
						file.lastModifiedDate.toLocaleDateString(), ')');
				output.push('<br/>');
				totalFileLength += file.size;
			}
			document.getElementById('selectedFiles').innerHTML = output
					.join('');

			startUpload();
		}

		function onUploadFailed(e) {
			alert("Error uploading file");
		}

		function uploadNext() {
			var opid=$('#opid').val()
			var url=tsmUpLoadServiceUrl+ctx + '/fileupload/upload';
			var xhr = new XMLHttpRequest();
			var fd = new FormData();
			var $filesNameOn = $(".chooseFiles");//包裹input file的a标签
			var file = document.getElementById('files').files[filesUploaded];
			fd.append("file", file);
			fd.append("opid",opid);
			fd.append("type", "4");
			fd.append("orgId", orgId);
			fd.append("account",account);
			fd.append("userId", userId);
			xhr.addEventListener("error", onUploadFailed, false);
			xhr.open("POST", url);
			xhr.onreadystatechange = function() {
				if (xhr.readyState == 4 && xhr.status == 200) {
					txt = xhr.responseText;
					var data = JSON.parse(txt);
					if (data.code == '0') {
						window.top.iDialogMsg("提示", data.message);
						return;
					}
					if (data == null || data == 'undefined') {
						return;
					}
					if (typeof (data.status) != 'undefined') {
						if (data.status == 'success') {
							/* var fileIds = $("#fileIds").val();
							$("#fileIds").val(fileIds + data.fileId + ","); // 获取附件ID */
							alert("上传成功");
							setTimeout(function(){
								window.parent.closePubDivDialogIframe('addRecordId');
								window.parent.document.forms[0].submit();
							},500);
						}
					}
				}
			};
			xhr.send(fd);
		}
		function startUpload() {
			totalUploaded = filesUploaded = 0;
			uploadNext();
		}

		window.onload = function() {
			document.getElementById('files').addEventListener('change',
					onFileSelect, false);//接收人上传文件
		}
	</script>
</head>
<body style="height:200px;">

		<form method="post" action="" enctype="multipart/form-data">
		    <input type="hidden" value='${opid}' name="opid" id="opid"> 
				<div id="content">
				<div  style="display: inline-block;padding: 5px;margin-left:160px;margin-top:30px;">
				  <!--   <span id="spanButtonPlaceholder"></span> 
				    <input id="btnUpload" type="button" value="上  传" onclick="startUploadFile();" style="display:inline-block;float:left;height:21px;line-height:20px;cursor:pointer;"/> 
					<input id="btnCancel" type="button" value="取消所有上传" onclick="cancelUpload();" disabled="disabled" style="display:inline-block;float:left;height:21px;line-height:21px;cursor:pointer;"  /> -->
					 <a href="javascript:;" class="chooseFiles" >上传文件(20 MB Max)
					 	<input type="file" id="files" accept=".mp3,.spx" />
					 </a>
				</div>
				<div id="divFileProgressContainer"></div>
				<div id="thumbnails">
					<!-- <table id="infoTable" border="0" width="500"
						style="display: inline; border: solid 1px #7FAAFF; background-color: #C5D9FF; padding: 2px; margin-top: 8px;">
					</table> -->
					<output id="selectedFiles" style="margin:10px auto;text-align:center;display:block;"></output>
				</div>
				</div>
	   </form>
</body>
</html>