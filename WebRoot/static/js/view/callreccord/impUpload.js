//var contextPath;
var imp_queueErrorArray;
$(function(){
	var impUpload = new SWFUpload({
		upload_url: uploadUrl,
		flash_url : ctx+"/static/js/swfupload/swfupload.swf",	
		
		file_post_name: 'file',
		use_query_string: true,
		post_params: {
			"type":'3'
		},
		
		file_types : "*.xls;*.xlsx",
		file_types_description : "文件",
		file_size_limit: "5 MB",
		file_upload_limit: "1",
		
		// handlers
		file_dialog_start_handler: imp_fileDialogStart,
		file_queued_handler: imp_fileQueued,
		file_queue_error_handler: imp_fileQueueError,
		file_dialog_complete_handler: imp_fileDialogComplete,
		upload_start_handler: imp_uploadStart,
		upload_progress_handler: imp_uploadProgress,
		upload_success_handler: imp_uploadSuccess,
		upload_complete_handler: imp_uploadComplete,
		
		button_placeholder_id : "importUpload",
		button_image_url : ctx+"/static/images/SmallSpyGlassWithTransperancy_17x18.png",
		button_text : '<span class="button">上传导入文件<span class="buttonSmall">(5 MB Max)</span></span>',
		button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; line-height: 18px;color: blue;} .buttonSmall { font-size: 10pt; }',
		button_text_top_padding: 0,
		button_text_left_padding: 18,
		button_width: 150,
		button_height: 18,
		button_cursor: SWFUpload.CURSOR.HAND,   // 鼠标移到按钮上的光标样式  
//		button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,  // Flash剪辑的WMODE属性
	});
});

//========================================  回调函数Handlers  ===================================

/**
* 打开文件选择对话框时响应
*/
function imp_fileDialogStart() {
	if (imp_queueErrorArray) {
		imp_queueErrorArray = null;
	}
}

/**
* 文件被加入上传队列时的回调函数,增加文件信息到列表并自动开始上传.<br />
* <p></p>
* SWFUpload.startUpload(file_id)方法导致指定文件开始上传,
* 如果参数为空,则默认上传队列第一个文件;<br />
* SWFUpload.cancelUpload(file_id,trigger_error_event)取消指定文件上传并从队列删除,
* 如果file_id为空,则删除队列第一个文件,trigger_error_event表示是否触发uploadError事件.
* @param file 加入队列的文件
*/
function imp_fileQueued(file) {
	var impUpload = this;
	var listItem = '<li id="' + file.id + '">';
	listItem += '文件:<em>' + file.name + '</em>(' + Math.round(file.size/1024) + ' KB)';
	listItem += '<div class="progressBar"><div class="progress"></div></div>'
			  + '<span class="progressValue"></span>'
			  + '<p class="status" >Pending</p>'
			  + '<span class="min-dele" >&nbsp;</span>'
			  + '</li>';
	$("#importList").append(listItem);
/*	$("li#" + file.id + " .min-dele").click(function(e) {
		var stats = swfUpload.getStats();
	    stats.successful_uploads--;
	    swfUpload.setStats(stats);
		swfUpload.cancelUpload(file.id);
		$("li#" + file.id).slideUp('fast');
		$("li#" + file.id).remove();
		
		var index = $("li#" + file.id).index("li");//获取当前li的index值
		var fileIdsStr=$("#fileIds").val();//获取页面的隐藏字段fileIds
		var fileIdsArr = fileIdsStr.split(",");//将字段转换成数组
		var del_fileId = fileIdsArr[index];//获取当前index值下的数组值
		fileIdsStr = fileIdsStr.replace(del_fileId+"," , "");//删除$("#fileIds").val()中的值
		$("#fileIds").val(fileIdsStr);//fileIds重新被赋值
	});*/
//	swfUpload.startUpload();
}

/**
* 文件加入上传队列失败时触发,触发原因包括:<br />
* 文件大小超出限制<br />
* 文件类型不符合<br />
* 上传队列数量限制超出等.
* @param file 当前文件
* @param errorCode 错误代码(参考SWFUpload.QUEUE_ERROR常量)
* @param message 错误信息
*/
function imp_fileQueueError(file,errorCode,message) {
	if (!imp_queueErrorArray) {
		imp_queueErrorArray = [];
	}
	var errorFile = {
		file: file,
		code: errorCode,
		error: ''
	};
	switch (errorCode) {
	case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
		errorFile.error = '文件大小超出限制.';
		break;
	case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
		errorFile.error = '上传文件必须是：excel.';
		break;
	case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
		errorFile.error = '文件为空文件.';
		break;
	case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
		errorFile.error = '用户选取的文件超过了允许的数量.';
		break;
/*	case SWFUpload.QUEUE_ERROR.UPLOAD_LIMIT_EXCEEDED:
		errorFile.error = "上传的文件数量超过了允许的最大值";
		break;	*/
	default:
		alert('加载入队列出错.');
		break;
	}
	imp_queueErrorArray.push(errorFile);
}

/**
* 选择文件对话框关闭时触发,报告所选文件个数、加入上传队列文件数及上传队列文件总数
* @param numSelected 选择的文件数目
* @param numQueued 加入队列的文件数目
* @param numTotalInQueued 上传文件队列中文件总数
*/
function imp_fileDialogComplete(numSelected,numQueued,numTotalInQueued) {
	var swfupload = this;
	if (imp_queueErrorArray && imp_queueErrorArray.length) {
		var table = $('<table><tr><td>文件</td><td>大小</td></tr></table>');
		for(var i in imp_queueErrorArray) {
			var tr = $('<tr></tr>');
			if(imp_queueErrorArray[i].code == -100){
				table = $('<table></table>');
				var info =  '<td>超出了最大文件上传数！</td>';
			}else{
				var info = '<td>' + imp_queueErrorArray[i].file.name + '<span style="color:red">(' + imp_queueErrorArray[i].error + ')</span></td>'
						+ '<td>' + imp_queueErrorArray[i].file.size + 'bytes</td>';
			}
			table.append(tr.append(info));
		}
		$.ligerDialog.open({
			width: 500,
			content: table,
			title: '文件选择错误提示',
			buttons: [{
				text: '确定',
				onclick: function(btn,dialog,index) {
					$("#impQueueStatus").text('选择文件:' + numSelected + ' / 加入队列文件:' + numQueued);
					swfupload.startUpload();
					dialog.close();
				}
			}]
		});
	} else {
		this.startUpload();
	}
}

/**
* 文件开始上传时触发
* @param file 开始上传目标文件
*/
function imp_uploadStart(file) {
	if (file) {
		$("#importList li#" + file.id).find('p.status').text('上传中...');
		$("#importList li#" + file.id).find('p.progressValue').text('0%');
	}
}

/**
* 文件上传过程中定时触发,更新进度显示
* @param file 上传的文件
* @param bytesCompleted 已上传大小
* @param bytesTotal 文件总大小
*/
function imp_uploadProgress(file,bytesCompleted,bytesTotal) {
	var percentage = Math.round((bytesCompleted / bytesTotal) * 100);
	$("#importList li#" + file.id).find('div.progress').css('width',percentage + '%');
	$("#importList li#" + file.id).find('span.progressValue').text(percentage + '%');
}

/**
* 文件上传完毕并且服务器返回200状态码时触发
* @param file 上传的文件
* @param serverData 
* @param response
*/
function imp_uploadSuccess(file,serverData,response) {
	var swfUpload = this;
	var stats = swfUpload.getStats();
    stats.successful_uploads--;
    swfUpload.setStats(stats);
	swfUpload.cancelUpload(file.id);
	
	$("#txt").val(file.name);
	
	var item = $("#importList li#" + file.id);
	item.find('div.progress').css('width','100%');
	item.find('span.progressValue').css('color','blue').text('100%');
	item.find('div.progressBar').hide();
	item.find('span.progressValue').hide();
	
	item.addClass('success').find('p.status').html('上传完成!');
	item.hide();
	
	if(response){
//		var progress = new FileProgress(file,  this.customSettings.upload_target);
		var data = JSON.parse(serverData);
	   	 if(data==null || data=='undefined'){
	    		return;
	    	 }
	     if (typeof(data.msg) != 'undefined') {
	    	 var msg = data.msg;
	    	 msg = msg.replace(/&lt;/g,"<");
	    	 msg = msg.replace(/&gt;/g,">");
	       	$("#showMsg").html(msg);
	       }
	} 
}

/**
* 在一个上传周期结束后触发(uploadError及uploadSuccess均触发)
* 在此可以开始下一个文件上传(通过上传组件的uploadStart()方法)
* @param file 上传完成的文件对象
*/
function imp_uploadComplete(file) {
	this.uploadStart();
}