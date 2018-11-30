<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
<title>邮件发送</title>
<!--ckeditor-->
<script src="${ctx }/static/thirdparty/ckeditor/ckeditor.js"></script>
<script src="${ctx }/static/thirdparty/ckeditor/config.js"></script>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->

<link type="text/css" rel="stylesheet" href="${ctx }/static/css/upload.css" />
<link href="${ctx }/static/js/ligerUI/skins/Aqua/css/ligerui-all.css" type="text/css" rel="stylesheet" />
<%-- <script type="text/javascript" src="${ctx}/static/js/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${ctx}/static/js/swfupload/swfupload.queue.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/callreccord/swfupload.js"></script> --%>

<script type="text/javascript" src="${ctx}/static/js/ligerUI/js/core/base.js"></script>
<script type="text/javascript" src="${ctx}/static/js/ligerUI/js/plugins/ligerDrag.js"></script>
<script type="text/javascript" src="${ctx}/static/js/ligerUI/js/plugins/ligerDialog.js"></script>
<style type="text/css">
	.cke_chrome{margin-left:100px !important;}
</style>
<script type="text/javascript">
var edit;
var tsmUpLoadServiceUrl = '${tsmUpLoadServiceUrl}'; // <!-- 上传服务器路径 -->
var account = '${shrioUser.account}';
var orgId = '${shrioUser.orgId}';
var userId = '${shrioUser.id}';
$(function(){
    $("input:text:first").focus();
	
	var opera = true;
	//加载ckeditor
	CKEDITOR.config.height = '320';
	CKEDITOR.config.width = '765';
	edit = CKEDITOR.replace('editor', {
		customConfig: ctx+'/static/js/view/tsm/notice/notice_config.js?'+(Math.random()*10+1)
    });
	/*左边铺满整屏*/
	var winhight=$(window).height(); 
	$(".hyx-cm-right").height(winhight);
	$(".hyx-cfu-timeline").height(winhight-60);
	/*表单优化*/

    $(".bomp-pos").each(function(i,item){
    	if($(item).find('.bomp-focus').text().length > 0){
			$(item).find('.lab_hid').hide();
		}
    });
	
    $(".bomp-pos").find('.bomp-focus,.lab_hid').click(function(){
        var ipt_a = $(this).parent().find('.bomp-focus');
        ipt_a.focus();
        $(this).parent().find('.lab_hid').hide();
        ipt_a.blur(function(){
            if($(this).val() == ''){
                $(this).parent().find('.lab_hid').show();
            }
         }); 
    });
    
    $(".bomp-pos-a").find('.lab_hid').click(function(){
        var ipt_b = $(this).parent().find('.bomp-focus');
        ipt_b.focus();
        $(this).parent().find('.lab_hid').hide();
        ipt_b.blur(function(){
            if($(this).val() == ''){
                $(this).parent().find('.lab_hid').show();
            }
         }); 
    });
    $(".bomp-pos-a").find('.bomp-focus').click(function(){
        var ipt_c = $(this);
        ipt_c.focus();
        $(this).parent().parent().find('.lab_hid').hide();
        ipt_c.blur(function(){
            if($(this).val() == ''){
                $(this).parent().parent().find('.lab_hid').show();
            }
         }); 
    });
	$(".mail-bulk-subje").each(function(){
		$(this).click(function(){
			var area_val = $(".editor-textarea").html();
			var span_html = $(this).find("span").html();
			$(".mail-bulk-subject").attr('value',span_html);
			
			$(".editor-textarea").html(area_val+$(this).html());
			/*window.top.iDialogMsg("提示",$(".editor-textarea").html());*/
		});
	});


	// 控制接收人输入框的高度
    var adaptiveHeight = function(a, baserows, maxrows) {
	/*
	    a: textarea元素
	    baserows: 基础行数
	    maxrows: 最大行数
	*/
	    /*获取textarea的padding的上下高度*/
	    var po =  parseInt(a.css('padding-top')) + parseInt(a.css('padding-bottom'));
	    var baseLineHeight = parseInt(a.css('line-height'));
	    var baseHeight = baseLineHeight * baserows;
	    a.height(baseHeight);
	    var scrollval = a[0].scrollHeight;
	    /*检测是否达到了最大行数，达到了，则把高度设置为最大高度*/
	    if (scrollval - po >= baseLineHeight * maxrows) {
	        scrollval = baseHeight + baseLineHeight * (maxrows-baserows) + po;
	    }
	    a.height(scrollval - po);
	};

	var adaptiveTextarea = function(sel, baserows, maxrows, callback){
	    sel.bind('input propertychange', function(e) {
	        adaptiveHeight($(this), baserows, maxrows);
	        if(callback) callback(e);
	    });
	    /*初始化textarea高度*/
	    adaptiveHeight(sel, baserows, maxrows);
	};

	/*使用 初始行数为1， 最大行数为3*/
	adaptiveTextarea($('.bomp-mail-text'), 1, 3);

	/** 异步查询 资源信息 */
	$(".lab_icon_a").on("click", function(e){
		var lab_icon_a = $(this).parent();
		if(lab_icon_a.find('.drop').is(":hidden")){
			var url = "${ctx}/call/custInfos";
			$.post(url,function(data){
				lab_icon_a.find('.drop').fadeIn();
				lab_icon_a.find('.drop').html(data);
			},'html');
	    	
	    }else{
	    	lab_icon_a.find('.drop').fadeOut();
	    }

	    $(document).one("click", function(){
	        lab_icon_a.find('.drop').fadeOut();
	    });
	    e.stopPropagation();
	});
	$(".lab_icon_a").parent().find('.drop').on("click", function(e){
	    e.stopPropagation();
	});
	
	 // 保存
    $("#save_buf").click(function(){
    	var content = $.trim(edit.getData());
    	$("#editor").val(content);
    	if(opera && checkIsNull()){
    		opera = false;
        	$('#idialogForm').ajaxSubmit({
    			url : ctx+'/call/email/emailSend.do',
    			type : 'post',
    			error : function(XMLHttpRequest, textStatus){window.top.iDialogMsg("提示","请求失败！");},
    			success : function(data) {	
    				var  data2 =data.replace(/(\n|\r\n)+/g,"");//回车换行替换为空格
    				if(data2.substr(0,1)=="0"){
    					window.top.iDialogMsg("提示","发送成功！");
    					setTimeout('closeParentPubDivDialogIframe("email_send")',1000);
    				}else{
    					window.top.iDialogMsg("提示","发送失败！");
    				}
    			}
    		});	
    	}
    });
    
         //上传附件
      var totalFileLength, totalUploaded, fileCount, filesUploaded;
      function onFileSelects(e) {
          var files = e.target.files; // FileList object
          var output = [];
          fileCount = files.length;
          totalFileLength = 0;
          for (var i=0; i<fileCount; i++) {
              var file = files[i];
              var names=file.name;
              var sarr=names.split(".");
              var Mbs=file.size/(1024*1024);
				if(Mbs > 20 ){
					window.top.iDialogMsg("提示","文件大小超过限制！")
					return false;
				}
              output.push(file.name, ' (',
                file.size, ' bytes, ',
                file.lastModifiedDate.toLocaleDateString(), ')'
                );
              output.push('<br/>');
              /*debug('add ' + file.size);*/
              totalFileLength += file.size;
          }
          document.getElementById('selectedFileas').innerHTML = output.join('');
          /*debug('totalFileLength:' + totalFileLength);*/
          startUploads();
      }

          function onUploadFaileds(e) {
              window.top.iDialogMsg("提示","Error uploading file");
          }

          function uploadNexts() {
              var xhr = new XMLHttpRequest();
              var fd = new FormData();
              var file = document.getElementById('addFujian').files[filesUploaded];
              fd.append("file", file);
              fd.append("type","3");
              fd.append("account", account);
			  fd.append("orgId", orgId);
			  fd.append("userId", userId);
			  xhr.upload.onprogress =progressFunction;
              xhr.upload.onloadstart=loadStart;
              xhr.upload.onloadend= loadEnd;
              xhr.addEventListener("error", onUploadFaileds, false);
              xhr.open("POST", tsmUpLoadServiceUrl+ctx+"/fileupload/upload");
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
							var fileIds = $("#fileIds").val();
							$("#fileIds").val(fileIds + data.fileId + ","); // 获取附件ID
							$("#fileList").append(
											"<li id=\"file_li_"
													+ data.fileId
													+ "\"><label class=\"name\">"
													+ data.fileName
													+ "</label><a href=\"###\" onclick=\"delfile('"
													+ data.fileId
													+ "')\" class=\"link\">删除</a></li>");
							   	setTimeout(function(){
						    		$.dialog.get["process_idialog"].remove();
						    	},500)
							if($("#fileList>li").length >= 3){
								$("#addFujian").attr("disabled","disabled");
								$("#addFujian").css("cursor","default");
								$(".chooseFiles.addFj").css("color","#ccc")
							}
						}
					}
				}
            };
            xhr.send(fd);
        }
        function startUploads() {
          totalUploaded = filesUploaded = 0;
          uploadNexts();
      }
      
			document.getElementById('addFujian').addEventListener('change',
					onFileSelects, false);//附件上传
    
});

function checkIsNull(){	
	if($("#editor").val() == null || $.trim($("#editor").val())==""){
		window.top.iDialogMsg("提示","内容不能为空！");
		return false;
	}
	if($("#title").val() == null || $.trim($("#title").val())==""){
		window.top.iDialogMsg("提示","主题不能为空！");
		return false;
	}
	
	if($("#name_email").val() == null || $.trim($("#name_email").val())==""){
		window.top.iDialogMsg("提示","接收人不能为空！");
		return false;
	}
	return checkReciever($("#name_email").val())
}

//判断接收者格式是否正确
function checkReciever(receiveId){
	  var isValid = true;
	  var array = receiveId.split(",");

	  // 带姓名的邮箱
	  var reg = /^(\S+[^<])(<[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+>)$/; 
	  // 不带姓名的邮箱
	  var reg1 = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/
	  for (var i=0;i<array.length;i++){  
		  if(reg.test(array[i])){    	           
	            isValid = true;
	    	    break;
	        }else if(reg1.test(array[i])){
	        	isValid = true;
		        break;
	        }else{
	        	window.top.iDialogMsg("提示","收件人格式不正确！");
	        	isValid = false;
		    	break;
	        }
	  }
	  return isValid;
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
		$(".chooseFiles.addFj").css("color","#000")
	}
}

   function loadStart(){
    	var content = '<div id="p" style="width:200px;"></div>';
    	$.dialog({
    		title:"上传进度",
    		id:"process_idialog",
    		width:250,
    		height:100,
    		lock:true,
    		content:content,
    		show:function(){
    			$('#p').progressbar({
    			    value: 0
    			});
    		}
    	})
    }
    function loadEnd(){
    	$('#p').progressbar({
		    value:100
		});
    }
    function progressFunction(evt) {
    	var loaded=evt.loaded;
    	var percent = (evt.loaded/totalSize).toFixed(2)*100;
    	$('#p').progressbar({
		    value: percent
		});
   }
</script>
</head>
<body> 
<div class='bulk-sms-box bomp-mail'>
	<form action="" method="post" id="idialogForm">
		<div class="bomp_mess_left fl_l" style="width:690px">
			<div class='bomp-p'>
				<label class='lab_a fl_l'>发件人邮箱：</label>
				<span style="line-height:27px;color:#00f;">${tecs.loginUser}</span>
			</div>
			
			<div class='bomp-p bomp-p-widtha bomp-pos'>
				<label class='lab_a fl_l'>收件人：</label>
				<div class="lab_icon" style="width:475px;">
					<textarea class='ipt_a w_d bomp-focus bomp-mail-text fl_l' style="width:550px !important;min-height:60px;" id="name_email" name="name_email" placeholder="例：张三<186xxxx1234@qq.com>,186xxxx5678@qq.com,">${name_email}</textarea>	
					<!-- <label class="lab_hid">例：张三<186xxxx1234@qq.com>,186xxxx5678@qq.com,</label> -->
					<c:if test="${groupType != 2 }"><i class="lab_icon_a" title="添加收件人"></i></c:if>
					<div class="drop" style="display:none;right:-172px;" id="drop_">
						<!-- 异步查询 -->
					</div>
				</div>	
			</div>
			<p class='bomp-p bomp-p-widtha bomp-pos-a clearfix' style="margin-top:15px;z-index:0;">
				<label class='lab_a fl_l'>主题：</label>
					<input class="mail-bulk-subject overflow_hidden fl_l" type="text" id="title" name="title"  maxlength="50">
			</p>
			<div class="mail-bulk-mass fl_l">
				<input type="hidden" name="fileIds" id="fileIds">
<%-- 				<span id="showFile"></span>
				<img class="fl_l" width="30" height="30" src="${ctx }/static/images/fashion.png" alt="">
				<a href="">添加附件<input type="file" id="file" name="file" class="imgUpload"></a>
				<span>（最大20MB）</span> --%>
			</div>
			
			<a href="javascript:;" class="chooseFiles addFj" style="float:left;margin-top:5px;">添加附件(20Mb Max)
					 <input type="file" id="addFujian"  />
				 </a>
				 <br><output id="selectedFileas" style="margin-left:100px;display:none;"></output>
	    		<ul id="fileList" class="hyx-vc-list fl_l">
						
				</ul>
			<!-- <div id="swfupload" class="fl_l" style="width:100%;">
		    	<span id="attachmentsUpload" ></span>
				<p id="queueStatus" ></p>
				<ol id="logList"></ol>
		    </div> -->
			
			<div class='bomp-p bomp-p-widtha bomp-pos clearfix' style="z-index:0">
				<label class='lab_a fl_l'>正文：</label>
				<textarea id="editor" class="editor-textarea" name="context"  cols="5" rows="5" style="z-index:-1"></textarea>
			</div>
			<div class='bomb-btn' style="margin-top:10px;margin-bottom:5px;">
				<label class='bomb-btn-cen'>
					<a href="javascript:;" id="save_buf" class="com-btna bomp-btna com-btna-sty fl_l"><label>发送</label></a>
				</label>
			</div>
		</div>
	</form>
	<div class="hyx-cm-right hyx-cfu-right fl_r" style="background:#f4f4f4;" id="bomp_mess_right">
		<c:import url="/view/call/idialog/email_send_right.jsp"/>
	</div>
</div>
</div>
</body>
</html>