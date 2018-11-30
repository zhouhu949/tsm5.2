<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>通信管理-群发短信</title>
<link type="text/css" rel="stylesheet" href="${ctx }/static/css/upload.css" />
<link href="${ctx }/static/js/ligerUI/skins/Aqua/css/ligerui-all.css" type="text/css" rel="stylesheet" />
<%-- <script type="text/javascript" src="${ctx}/static/js/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${ctx}/static/js/swfupload/swfupload.queue.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/callreccord/impUpload.js"></script> --%>

<script type="text/javascript" src="${ctx}/static/js/view/res/importFileEmailJs.js"></script>

<script type="text/javascript" src="${ctx}/static/js/ligerUI/js/core/base.js"></script>
<script type="text/javascript" src="${ctx}/static/js/ligerUI/js/plugins/ligerDrag.js"></script>
<script type="text/javascript" src="${ctx}/static/js/ligerUI/js/plugins/ligerDialog.js"></script>

<%-- <script type="text/javascript" src="${ctx }/static/js/file-up/html5.js"></script> --%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
<script type="text/javascript">
var account = '${shrioUser.account}';
var orgId = '${shrioUser.orgId}';
var userId = '${shrioUser.id}';
var tsmUpLoadServiceUrl = '${tsmUpLoadServiceUrl}'; // <!-- 上传服务器路径 -->
$(function(){
	var opera = true;

	// 保存
    $("#save_buf").click(function(){
    	if(opera && checkIsNull()){
    		opera = false;
        	$('#myForm').ajaxSubmit({
    			url : ctx+'/call/sms/smsBatchSend.do',
    			type : 'post',
    			error : function(XMLHttpRequest, textStatus){window.top.iDialogMsg("提示","请求失败！");},
    			success : function(data) {
    				var  data2 =data.replace(/(\n|\r\n)+/g,"");//回车换行替换为空格
    				if(data2.substr(0,1)=="0"){
    					window.top.iDialogMsg("提示","发送成功！");
    					document.location.href = ctx+"/call/sms/toSmsBatchSend";
    				}else{
    					if('3'==data2){
    						window.top.iDialogMsg("提示","发送失败！");
    					}else if('9001'==data2){
    						window.top.iDialogMsg("提示","用户不存在！");
    					}else if('9002'==data2){
    						window.top.iDialogMsg("提示","接收人所有手机格式不正确！");
    					}else if('9003'==data2){
    						window.top.iDialogMsg("提示","当前的短信剩余条数不够，请充值！");
    					}else if('9004'==data2){
    						window.top.iDialogMsg("提示","短信条数为空，请充值！");
    					}else if('9005'==data2){
    						window.top.iDialogMsg("提示","短信接收人总数不得超过10000！");
    					}else if('9006'==data2){
    						window.top.iDialogMsg("提示","短信接收人为空！");
    					}else if('9007'==data2){
    						window.top.iDialogMsg("提示","短信内容以及签名不可以为空！");
    					}else{
    						window.top.iDialogMsg("提示",data2);
    					}
    				}
    			}
    		});	
    	}
    });
});

//短信字数及条数统计：
function noteCount() {
	var $jq_content_area = $("#jq_content_area");
	var $jq_sign_input = $("#jq_sign_input");
	var $jq_charSize = $("#jq_charSize"); // 还可以输入多少字
	var $jq_noteCount_span = $("#jq_noteCount_span"); // 短信条数
	var signVal = $jq_sign_input.val().trim();
	var AREA_MAX_LEN = 280; // 最多可以输入的字节
	var oneNoteMaxChars = 66; // 一条短信最多字节
	var areaVal = $jq_content_area.val().trim();
	
	var signLength = 0;
	if (signVal.length != 0) {
		signLength = signVal.length;
	}
	
	// 过滤当字数超出AREA_MAX_LEN 的情况：
	if (areaVal.length > AREA_MAX_LEN- signLength) {
		areaVal = areaVal.substring(0, AREA_MAX_LEN- signLength);
		$jq_content_area.val(areaVal);
	}
	
	// 文本域长度：【回车换行符统一计为2字】
	areaVal = areaVal.replace(/\r\n/g, "##").replace(/\n/g, "##").replace(/\r/g, "##");
	var total = 0;
	total = areaVal.length;
	total += signVal.length;
	
	$jq_charSize.text(AREA_MAX_LEN-parseInt(total));
	$jq_noteCount_span.text(Math.ceil(total / oneNoteMaxChars));
}

function checkIsNull(){	
	if($("#jq_content_area").val() == null || $.trim($("#jq_content_area").val())==""){
		window.top.iDialogMsg("提示","短信内容不能为空！");
		return false;
	}
	if($("#selectedFiles").val() == null || $.trim($("#selectedFiles").val())==""){
		window.top.iDialogMsg("提示","接收人不能为空！");
		return false;
	}
	if($.trim($("#selectedFiles").val()) == "请导入接收人"){
		window.top.iDialogMsg("提示","接收人不能为空！");
		return false;
	}
	return true;
}

</script>
<style>
#uploadButton{width:600px;height:25px;line-height:20px;background-color:#fafafa;color:#cccccc;text-align:left;padding-left:10px;margin-top:0;cursor:default;}
	.chooseFiles{width:160px;background-color:#fff;color:#000;margin-top:10px;margin-left:100px;}
</style>
</head>
<body> 
<div class='bulk-sms-box alert_d'>
<form id="myForm" method="post">
	<div class="bomp_mess_left fl_l">
		<div class='bomp-p bomp-p-widtha bomp-pos message'>
			<label class='lab_a fl_l'>接收人：</label>
			<div class="lab_icon">
				<!-- <input name="txt" type='text' value='请导入接收人' id="txt" class='ipt_a w_d bomp-focus fl_l' disabled="disabled" /> -->
				<input  id="uploadButton" class="uploadButton" type="button" value="请导入接收人"/>
			</div>
			<a href="${ctx }/templet/sms_send_temp.xls" class="down-load-demo fl_l">下载模板</a>
			<!-- <div id="impupload" class="fl_l" style="width:100%;">
		    	<span id="importUpload" ></span>
				<p id="impQueueStatus" ></p>
				<ol id="importList"></ol>
		    </div> -->
		    
<!-- 			<input type="button" onMouseMove="f.style.pixelLeft=event.x-60;f.style.pixelTop=this.offsetTop;" value="导入" size="30" onClick="f.click()" class="liulan"> -->
<!-- 			<input type="file"  id="f"  name="f" style="height:26px;" class="files"  size="1" hidefocus> -->
<%-- 			<a href="${ctx }/templet/sms_send_temp.xls" class="down-load-demo fl_l">下载模板</a> --%>
		</div>
		<p><output id="selectedFiles" style="margin-left:100px;"></output><br>
		 		 <a href="javascript:;" class="chooseFiles">上传导入文件(5Mb Max)
					 <input type="file" id="files"  class="sms"/>
				 </a>
		</p>
		<p class="bulk-sms fl_l" id="showMsg"></p>
		<p class='bomp-p bomp-p-widtha bomp-pos-a'>
			<label class='lab_a fl_l'>短信内容：</label>
			<label class="area_box">
				<textarea class='area_a bomp-focus fl_l' name="smsContent" id="jq_content_area" onkeyup="noteCount()" onblur="noteCount()" placeholder="请输入短信内容"></textarea>
				<span class="box_a">剩余<b class="font-color" id="jq_charSize">280</b>字&nbsp;&nbsp;预计<b class="font-color" id="jq_noteCount_span">0</b>条短信&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<!-- <label class="lab_hid">请输入短信内容</label> -->
				<!-- <input class="lab_hid clea-clos" type="text" required value="请输入短信内容" style="width:745px;height:20px;line-height:20px;border:none"> -->
			</label>
		</p>
		<p class='bomp-p bomp-p-widtha bomp-pos' style="z-index:-1;">
			<label class='lab_a fl_l'>签名：</label>
			<input type='text' name="smslabel" readonly="readonly"  id="jq_sign_input" value='${smslabel}'  class='ipt_a w_c  bomp-focus fl_l' style="width: 180px !important;"/>
			<span style="display:inline-block; line-height:30px;">（签名也计入字数）</span>
			<label class="lab_hid"></label>
		</p>
		<dl class="tit">
			<dt class="font-color"><h2>温馨提示：</h2></dt>
			<dd>1.本平台支持非营销类短信发送；</dd>
			<dd>2.营销或非法短信将被系统自动屏蔽；</dd>
			<dd>3.短信有效发送时间为8:30-18:00，非有效时间发送平台自动顺延发送时间；</dd>
			<dd>4.请遵守《信息源入网信息安全责任书》的相关规定，并接受通信管理局审计。</dd>
			<dd>5.导入文件支持Excel格式。</dd>
		</dl>
		<div class='bomb-btn' style="margin-top:0 !important;">
			<label class='bomb-btn-cen'>
				<c:choose>
					<c:when test="${shrioUser.serverDay gt 0 }">
						<a href="javascript:;" class="com-btna bomp-btna com-btna-sty fl_l" id="save_buf"><label>发送</label></a>
					</c:when>
					<c:otherwise>
						<a href="javascript:;" class="com-btna-no fl_l"><label>发送</label></a>
					</c:otherwise>
				</c:choose>		
			</label>
		</div>
	</div>
	</form>
	<div class="bomp_mess_right fl_r" id="bomp_mess_right">
		<c:import url="/view/call/submodual/smsBatchSend_right.jsp"/>
	</div>
</div>
</body>
</html>