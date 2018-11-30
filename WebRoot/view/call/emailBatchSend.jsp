<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>通信管理-邮件群发</title>

<link type="text/css" rel="stylesheet" href="${ctx }/static/css/upload.css" />
<link href="${ctx }/static/js/ligerUI/skins/Aqua/css/ligerui-all.css" type="text/css" rel="stylesheet" />



<!--ckeditor-->
<script src="${ctx }/static/thirdparty/ckeditor/ckeditor.js"></script>
<script src="${ctx }/static/thirdparty/ckeditor/config.js"></script>
<%-- <script type="text/javascript" src="${ctx }/static/js/file-up/html5.js"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/static/js/swfupload/swfupload.js"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/static/js/swfupload/handlers.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/callreccord/emailSwfUpload.js"></script> --%>

<%-- <script type="text/javascript" src="${ctx}/static/js/swfupload/swfupload.queue.js"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/static/js/view/callreccord/swfupload.js"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/static/js/view/callreccord/impUpload.js"></script> --%>

<script type="text/javascript" src="${ctx}/static/js/view/res/importFileEmailJs.js"></script>

<script type="text/javascript" src="${ctx}/static/js/ligerUI/js/core/base.js"></script>
<script type="text/javascript" src="${ctx}/static/js/ligerUI/js/plugins/ligerDrag.js"></script>
<script type="text/javascript" src="${ctx}/static/js/ligerUI/js/plugins/ligerDialog.js"></script>


<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
<style type="text/css">
	.cke_chrome{margin-left:100px !important;}
	.chooseFiles{width:578px;}
	.addFj{background-color:#fff;outline:none;border-width:1px;border-style: solid;padding:2px 4px;width:auto;color:#000;margin-left:100px;}
	#uploadButton{width:600px;height:25px;line-height:20px;background-color:#fafafa;color:#cccccc;text-align:left;padding-left:10px;margin-top:0;cursor:default;}
	.xinchosFile{width:160px;background-color:#fff;color:#000;margin-top:10px;margin-left:100px;}
	.bomp-p{margin-top:15px;}
	.sign-select-email{line-height: 27px;height: 27px;width: 180px;padding-left: 5px;}
	.sign-bomp{width:800px;}
	.email-sign-btn-box{margin-left:20px;}
	.email-sign-edit{margin-right:10px;}
</style>
<script type="text/javascript">
var edit;
var account = '${shrioUser.account}';
var orgId = '${shrioUser.orgId}';
var userId = '${shrioUser.id}';
var tsmUpLoadServiceUrl = '${tsmUpLoadServiceUrl}'; // <!-- 上传服务器路径 -->
$(function(){

	var opera = true;
	//加载ckeditor
	CKEDITOR.config.height = '320';
	CKEDITOR.config.width = '765';
	edit = CKEDITOR.replace('editor', {
		customConfig: ctx+'/static/js/view/tsm/notice/notice_config.js?'+(Math.random()*10+1)
    })
	/*左边铺满整屏*/
	var winhight=$(window).height(); 
	$(".hyx-cm-right").height(winhight-30);
	$(".hyx-cfu-timeline").height(winhight-60);
	
	 // 保存
    $("#save_buf").click(function(){
    	var content = $.trim(edit.getData());
    	$("#editor").val(content);
    	if(opera && checkIsNull()){
    		opera = false;
        	$('#myForm').ajaxSubmit({
    			url : ctx+'/call/email/emailBatchSend.do',
    			type : 'post',
    			error : function(XMLHttpRequest, textStatus){window.top.iDialogMsg("提示","请求失败！");},
    			success : function(data) {	
    				var  data2 =data.replace(/(\n|\r\n)+/g,"");//回车换行替换为空格
    				if(data2.substr(0,1)=="0"){
    					window.top.iDialogMsg("提示","发送成功！");
    					document.location.href = ctx+"/call/email/toEmailBatchSend";
    				}else{
    					window.top.iDialogMsg("提示","发送失败！");
    					document.location.href = ctx+"/call/email/toEmailBatchSend";
    				}
    			}
    		});	
    		setTravl($("#taskid").val(),3000);
    	}
    });
    //初始化签名注入
	signData();
    //签名下拉改变触发函数
    $(".sign-select-email").change(function(){
    	var sele = $(this).find("option:selected");
    	var nextBox = $(".email-sign-btn-box");
    	if(sele.text() == "不使用"){
    		nextBox.hide();
    	}else{
    		nextBox.show();
    	}
    	$.ajax({
    		url:ctx+"/call/email/toEmailBatchSendRight",
    		data:{
    			id:sele.data("id")
    		},
    		success:function(data){
    			if(data.status){
	    			var  items = data.item;
	    			$('.cke_wysiwyg_frame').contents().find('body').append(items.context);
					$('.cke_wysiwyg_frame').contents().find('.sign-top-cw').css({'border-top':'dashed 1px #e1e1e1','padding-top':'10px'});
    			}
    		}
    	})
    });
    //新建签名
    $(".add-sign-email").on("click",function(e){
    	e.stopPropagation();
    	pubDivShowIframe('alert_new_char_sign','${ctx}/email/sign/toOperaSign','新建签名',630,420);
    });
    //编辑签名
    $(".email-sign-edit").on("click",function(e){
    	e.stopPropagation();
    	var id = $(".sign-select-email option:selected").data("id");
    	pubDivShowIframe('alert_new_char_sign','${ctx}/email/sign/toOperaSign?id='+id,'编辑签名',630,420);
    });
    //删除签名
    $(".email-sign-delete").on("click",function(e){
    	e.stopPropagation();
    	var id = $(".sign-select-email option:selected").data("id");
   		queDivDialog("res_remove_cust","是否删除？",function(){
			$.ajax({
				url:ctx+'/email/sign/deleteSign',
				type:'post',
				data:{id:id},
				dataType:'json',
				error:function(){alert('网络异常，请稍后再试！')},
				success:function(data){
					if(data == 0){
						window.top.iDialogMsg("提示",'删除成功！');
						setTimeout('signData()',1000);
					}else{
						window.top.iDialogMsg("提示",'删除失败！');
					}
				}
			});
		})
    });
});
 //签名下拉数据注入
function signData(){
    $.ajax({
    	url:ctx+"/call/email/toEmailBatchSendRight",
    	success:function(data){
    		if(data.status){
    			var html = "<option data-id='' title='不使用'>不使用</option>";
	    		var sign = data.signs;
	    		for(var i= 0;i < sign.length ; i++){
	    			html +="<option data-id='"+sign[i].id+"' title='"+sign[i].title+"'>"+sign[i].title+" </option>";
	    		}
	    		$(".sign-select-email").html(html);
    		}
    	}
    })
}
function checkIsNull(){	
	if($("#tecsLoginUser").val() == null || $.trim($("#tecsLoginUser").val())==""){
		window.top.iDialogMsg("提示","请先绑定发件人邮箱！");
		return false;
	}
	if($("#editor").val() == null || $.trim($("#editor").val())==""){
		window.top.iDialogMsg("提示","内容不能为空！");
		return false;
	}
	if($("#title").val() == null || $.trim($("#title").val())==""){
		window.top.iDialogMsg("提示","主题不能为空！");
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

//删除附件
function delfile(fileId){
	$("#file_li_"+fileId).remove();
	var fileIdsStr=$("#fileIds").val();
	fileIdsStr = fileIdsStr.replace(fileId+",","");
	$("#fileIds").val(fileIdsStr);
	if($("#fileList>li").length < 3){
		$("#addFujian").removeAttr("disabled");
		$("#addFujian").css("cursor","pointer");
		$(".chooseFiles.addFj").css("color","#000");
	}
}

//进度条轮询函数
function setTravl(taskId,time){
	var content = '<div class="finished-prograss">处理中...需处理总数<span class="total">0</span>,当前完成<span class="finished">0</span></div><div id="p" style="width:200px;"></div>';
	var idialogId = "process_idialog";
	$.dialog({
		title:"处理中",
		id:idialogId,
		width:250,
		height:135,
		lock:true,
		content:content,
		show:function(){
			$('#p').progressbar({
			    value: 0
			});
		}
	})
	var t=setInterval(function(){
		$.ajax({
			url:ctx+"/progress/getProgress",
			data:{
				taskId:taskId
			},
			success:function(data){
				console.log(data+new Date().getTime())
				if(data){
					if(data.status){
			    		var percent = (data.finished/ data.total).toFixed(2)*100;
			    		$('#p').progressbar({
						    value: percent
						});
			    		$(".finished-prograss>.total").text(data.total);
			    		$(".finished-prograss>.finished").text(data.finished);
			    		if(percent >= 100){//完成 成功
			    			$(".finished-prograss").html("操作成功！共操作"+data.total+"条数据");
			    			clearInterval(t);
			    			setTimeout(function(){
			    				closeIdialog(idialogId)
			    	    	},100)
			    		}
			    	}else{//失败
			    		clearInterval(t);
			    		$('#p').progressbar({
						    value: 0
						});
			    		$(".finished-prograss").html(data.errorMsg)
			    		setTimeout(function(){
			    				closeIdialog(idialogId)
			    	    	},100)
			    	}
				}
			}
		});
	},time)
}
function closeIdialog(id){
	$.dialog.get[id].remove();
}
</script>
</head>
<body> 
<div class='bulk-sms-box alert_d'>
	<div class="bomp_mess_left fl_l" >
		<form  id="myForm" method="post">
		<input type="hidden" name="taskId" id="taskid" value="${taskId }"/>
		<div class="bomp-p">
			<label class='lab_a fl_l'>发件人邮箱：</label>
			<span style="line-height:27px;color:#00f;">
				<input type="hidden" id="tecsLoginUser" value="${tecs.loginUser}">
				<input type="hidden" name="taskId" id="taskId" value="${taskId }">
				<c:choose>
					 <c:when test="${empty tecs.loginUser}"><a href="###" onclick="bindEmail()">绑定邮箱</a></c:when>
					 <c:otherwise>${tecs.loginUser}</c:otherwise>
				</c:choose>
			</span>	
		</div>
		<div class='bomp-p bomp-p-widtha bomp-pos message'>
			<label class='lab_a fl_l'>接收人：</label>
			<div class="lab_icon">
				<!-- <input name="txt" type='text' value='请导入接收人' id="txt" class='ipt_a w_d bomp-focus fl_l' disabled="disabled" /> -->
				</label><input  id="uploadButton" class="uploadButton" type="button" value="请导入接收人"/>
			</div>
			<a href="${ctx }/templet/email_send_temp.xls" class="down-load-demo " style="line-height:44px;">下载模板</a>
			<!-- <div id="impupload" class="fl_l" style="width:100%;">
		    	<span id="importUpload" ></span>
				<p id="impQueueStatus" ></p>
				<ol id="importList"></ol>
		    </div> -->
		      <br><output id="selectedFiles" style="margin-left:100px;"></output><br>
				 <a href="javascript:;" class="chooseFiles xinchosFile">上传导入文件(5Mb Max)
					 <input type="file" id="files"  class="email" />
				 </a>
			<!-- <input type="button"  value="导入" size="30"  id="spanButtonPlaceholder1" class="liulan"> -->
			<!-- <input type="file" id="f" name="f" style="height:26px;" class="files"  size="1" hidefocus>  -->
			
			<!-- 上传进度条 -->
			<!-- <div id="divFileProgressContainer1" style="padding:2px;margin-top:8px;margin-left:94px;"></div> -->
		</div>
		<p class="bulk-sms fl_l" id="showMsg"></p>
		
		<p class='bomp-p bomp-p-widtha bomp-pos-a clearfix' style="margin-top:15px;z-index:0;">
			<label class='lab_a fl_l'>主题：</label>
			<input class="mail-bulk-subject overflow_hidden fl_l" type="text" id="title" name="title" maxlength="50">
		</p>
		<div class="mail-bulk-mass fl_l">
			<input type="hidden" name="fileIds" id="fileIds">
			
<!-- 			<span id="showFile"></span> -->
<%-- 			<img class="fl_l" width="30" height="30" src="${ctx}/static/images/fashion.png" alt=""> --%>
<!-- 			<a href="javascript:;"  class="com-btna" id="spanButtonPlaceholder">添加附件</a> --><!--<input type="file" name="file" id="file"  class="imgUpload">  -->
			<!-- 上传进度条 -->
<!-- 			<div id="divFileProgressContainer" style="padding:2px;margin-top:8px;margin-left:94px;"></div>
			<span>（最大20MB）</span> -->
		</div>
		<!-- <div id="swfupload" class="fl_l" style="width:100%;">
	    	<span id="attachmentsUpload" ></span>
			<p id="queueStatus" ></p>
			<ol id="logList"></ol>
	    </div> -->
	    <div class="fl_l" style="width:100%;">
	     <a href="javascript:;" class="chooseFiles addFj">添加附件(20Mb Max)
					 <input type="file" id="addFujian"  />
				 </a>
	    <br><output id="selectedFileas" style="margin-left:100px;display:none;"></output>
	    <ul id="fileList" class="hyx-vc-list fl_l">
						
					</ul>
	    </div>
	    <div class="sign-bomp bomp-p">
	    	<label class='lab_a fl_l'>签名：</label>
	    	<select class="sign-select-email"><option>不使用</option></select>
	    	<a href="javascript:;" class="add-sign-email">+添加签名</a>
	    	<span class="email-sign-btn-box" style="display:none;">
	    		<a href="javascript:;" class="email-sign-edit">编辑</a>
	    		<a href="javascript:;" class="email-sign-delete">删除</a>
	    	</span>
	    </div>
		<div class='bomp-p bomp-p-widtha bomp-pos clearfix' style="z-index:0">
			<label class='lab_a fl_l'>正文：</label>
			<textarea id="editor" class="editor-textarea" name="context" cols="30" rows="10" style="z-index:-1"></textarea>
		</div>
		<div class='bomb-btn' style="margin-top:15px;margin-bottom:10px;">
			<label class='bomb-btn-cen'>
				<c:choose>
					<c:when test="${shrioUser.serverDay gt 0 }">
						<a href="javascript:;" id="save_buf"  class="com-btna bomp-btna com-btna-sty fl_l"><label>发送</label></a>
					</c:when>
					<c:otherwise>
						<a href="javascript:;" class="com-btna-no fl_l"><label>发送</label></a>
					</c:otherwise>
				</c:choose>	
			</label>
		</div>
		</form>
	</div>
<%-- 	<div class="hyx-cm-right hyx-cfu-right fl_l" style="background:#f4f4f4;margin-top:15px;" id="bomp_mess_right">
		<c:import url="/view/call/submodual/emailBatchSend_right.jsp"/>
	</div> --%>
</div>
</body>
</html>