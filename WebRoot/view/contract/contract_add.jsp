<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<html>
<head>
    <title>新增合同</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/style.css${_v}">
	<!--主要样式-->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/skin/default/color.css${_v}">
	<!--换肤样式-->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/js/form/skins/all.css${_v}">
	<!--公共js-->
	<script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js?v=222" dialog-theme="default"></script><!--可移动弹框插件-->
	<script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js"></script><!--可移动弹框插件公共js-->
	<!--引入公共样式及jquery--->
	<script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery.form.js${_v}"></script>
	<!-- JS时间插件 -->
	<script src="${ctx}/static/js/my97datepicker/WdatePicker.js${_v}" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx }/static/js/view/contract/contract_check.js"></script>
	<style>
		.swfupload{display:inline-block;float:left;width:200px;height:20px;line-height:20px;border:solid #d6d6d6 1px;}
		.chooseFiles{padding: 2px 10px;width:158px;height: 20px;line-height: 20px;position: relative;cursor: pointer;color: #545454;background: #fafafa;border: 1px solid #ddd;overflow: hidden;display: inline-block;*display: inline;*zoom: 1}
		.chooseFiles:hover{text-decoration:none;}
		#files{position: absolute; font-size: 100px; right: 0; top: 0; opacity: 0; filter: alpha(opacity=0); cursor: pointer ;}
		#files:hover{color: #444; background: #eee; border-color: #ccc; text-decoration: none ;}
	</style>
	<link rel="stylesheet" href="${ctx}/static/css/default.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/static/css/button.css" type="text/css" />
	<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
	<% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
    <script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
	<script type="text/javascript">
	var account = '${shrioUser.account}';
	var orgId = '${shrioUser.orgId}';
	var userId = '${shrioUser.id}';
	var tsmUpLoadServiceUrl = '${tsmUpLoadServiceUrl}'; // <!-- 上传服务器路径 -->
		var totalUploaded, fileCount, filesUploaded;

		//接收人文件上传
		function onFileSelect(e) {
			var files = e.target.files; // FileList object
			fileCount = files.length;
          for (var i=0; i<fileCount; i++) {
              var file = files[i];
              var names=file.name;
              var sarr=names.split(".");
              var lastOne=sarr.pop();
              var Mbs=file.size/(1024*1024);
				if(Mbs > 10 ){
					alert("文件大小超过限制！");
					return false;
				}
            if(lastOne !== "jpg" && lastOne !== "jpeg" && lastOne !== "png" && lastOne !== "pdf" && lastOne !== "docx" && lastOne !== "doc"){
              	alert("只允许上传word、pdf、jpg、png格式的文件！");
              	return false;
              }
          }
			startUpload();
		}

		function onUploadFailed(e) {
			alert("Error uploading file");
		}

		function uploadNext() {
		 	var url = tsmUpLoadServiceUrl+ctx + '/fileupload/upload';
			var xhr = new XMLHttpRequest();
			var fd = new FormData();
			var file = document.getElementById('files').files[filesUploaded];
			fd.append("file", file);
			fd.append("type", "2");
			fd.append("account", account);
			fd.append("orgId", orgId);
			fd.append("userId", userId);
			xhr.addEventListener("error", onUploadFailed, false);
			xhr.open("POST", url);
			xhr.onreadystatechange = function() {
				if (xhr.readyState == 4 && xhr.status == 200) {
					var txt = xhr.responseText;
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
							var fileIds = $("#fileIdsStr").val();
							$("#fileIdsStr").val(fileIds + data.fileId + ","); // 获取附件ID
							$("#fileList")
									.append(
											"<li id=\"file_li_"
													+ data.fileId
													+ "\"><label class=\"name\">"
													+ data.fileName
													+ "</label><a href=\"###\" onclick=\"delfile('"
													+ data.fileId
													+ "')\" class=\"link\">删除</a></li>");
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
		};
	</script>
    <script type="text/javascript" src="${ctx }/static/js/view/rest/ajaxfileupload.js"></script>
    <script type="text/javascript" src="${ctx }/static/js/view/contract/contract_add.js"></script>
</head>
<body>
	<shiro:hasPermission name="mySignCust_addOrder">
		<input type="hidden" id="mySignCust_addOrder" value="1"/>
	</shiro:hasPermission>

	<form id="cform" action="${ctx }/contract/saveAdd" method="post">
		<input type="hidden" id="custId" name="custId" value="${custInfoBean.resCustId }" />
		<input type="hidden" id="company" name="company" value="${custInfoBean.company }" />
		<input type="hidden" id="jsonStr"  name="jsonStr" value="${jsonStr }"/>
		<input type="hidden" id="module"  name="module" value="${module }"/>
		<input type="hidden" name="areFrom" id = "areFrom" value="${areFrom }"><!-- 来自哪里 : 1-- 客户跟进 -->
		<div class="hyx-nc">
			<div class='bomp_tit'><label class='lab'>合同基本信息</label></div>
			<div class="bomp-box">
				<p class='bomp-p'>
					<label class='lab_a fl_l'><b class="com-red">*</b>${empty filedMap['name'] ? '客户名称' : filedMap['name'] }：</label><input type='text' name="custName" checkPub="chk_1_1_0" readonly="readonly" value='${custInfoBean.name }' class='ipt_a fl_l' />
					<span class="error"></span>
				</p>
				<p class='bomp-p'>
					<label class='lab_a fl_l'><b class="com-red">*</b>合同编号：</label><input type='text' name="code" checkPub="chk_1_1_9"  maxlength="50" value='${contractCode }' class='ipt_a fl_l' />
					<span class="error"></span>
				</p>
				<p class='bomp-p'>
					<label class='lab_a fl_l'>合同名称：</label><input type='text' name="contractName" checkPub="chk_1_0_0" value='' maxlength="100" class='ipt_a fl_l' />
					<span class="error"></span>
				</p>
			</div>
			<div class="bomp-box">
				<p class='bomp-p'>
					<label class='lab_a fl_l'><b class="com-red">*</b>签订日期：</label><input type='text' name="signDate" checkPub="chk_1_1_0" onclick="WdatePicker({maxDate:'%y-%M-%d'})" value='' class='ipt_a fl_l bomp-date' id="d1" />
					<span class="error"></span>
				</p>
				<p class='bomp-p'>
					<input type="hidden" name="signUserid" id="signUserid" value='${signUser.userAccount }' />
					<label class='lab_a fl_l'>销售人员：</label><input type='text' readonly="readonly" name="signUsername" value='${signUser.userName }' class='ipt_a fl_l' />
					<span class="error"></span>
				</p>
        <p class='bomp-p'>
					<label class='lab_a fl_l'>合同签订主体：</label><input type='text' name="body" value='' class='ipt_a fl_l' />
				</p>
			</div>
			<div class="bomp-box">
				<p class='bomp-p'>
					<label class='lab_a fl_l'>生效日期：</label><input type='text' name="effectiveDate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'d3\')}'})" value='' class='ipt_a fl_l bomp-date' id="d2" />
					<span class="error"></span>
				</p>
				<p class='bomp-p'>
					<label class='lab_a fl_l'>失效日期：</label><input type='text' name="invalidDate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'d2\')||\'%y-%M-%d\'}'})" value='' class='ipt_a fl_l bomp-date' id="d3" />
					<span class="error"></span>
				</p>
				<p class='bomp-p'>
					<label class='lab_a fl_l'>合同归档索引：</label><input type='text' name="storeIndex" value='' class='ipt_a fl_l' />
				</p>
			</div>
			<div class='bomp_tit bomp-tit-mt'><label class='lab'>合同主要内容</label></div>
			<div class="bomp-box">
				<p class='bomp-p'>
					<label class='lab_a fl_l'>合同金额：</label><input type='text' name="money" value='' checkPub="chk_1_0_3" class='ipt_a fl_l' /><span class="bomp-comp fl_l">万元</span>
					<span class="error"></span>
				</p>
			</div>
			<div class="bomp-box">
				<p class='bomp-p bomp-p-w'>
					<input type="hidden" id="fileIdsStr" name="fileIdsStr" value=""/>
					<label class='lab_a fl_l'>合同附件：</label>
					<span class="file_box fl_l">
						<!-- <a href="javascript:;" class="com-btna" id="spanButtonPlaceholder">上传附件</a> -->
<!-- 						<input type="file" id="file" name="file" class="imgUpload"> -->
						 <a href="javascript:;" class="chooseFiles">上传附件
					 			<input type="file" id="files" />
				 		</a>
					</span>
					<span class="bomp-tip fl_l" style="padding-left:15px;"><label class="com-red">提示：</label>允许上传word、pdf、jpg、png格式的文件，文件上传的大小不能超过10M。</span>
					<!-- 上传进度条 -->
					<div id="divFileProgressContainer" style="padding:2px;margin-top:8px;margin-left:94px;"></div>
					<ul id="fileList" class="hyx-vc-list fl_l">

					</ul>
				</p>
			</div>
			<p class='bomp-p bomp-p-w'>
				<label class='lab_a fl_l'>付款计划说明：</label><textarea checkPub="chk_1_0_0" maxlength="200" name="payContext" class='area_a fl_l'></textarea>
				<span class="error"></span>
			</p>
			<center class="com-btnbox">
					<a href="javascript:;" id="addBtn" class="com-btna com-btna-sty" style="margin:0 auto;"><label>保存</label></a>
			</center>
		</div>
	</form>
</body>
</html>
