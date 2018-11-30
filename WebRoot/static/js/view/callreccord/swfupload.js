//var contextPath;
var queueErrorArray;
$(function(){
	var swfUpload = new SWFUpload({
		upload_url: ctx + '/fileupload/upload',
		flash_url : ctx+"/static/js/swfupload/swfupload.swf",	
		
		file_post_name: 'file',
		use_query_string: true,
		post_params: {
			"type":'3'
		},
		
		file_types : "*.jpg;*.jpeg;*.png;*.pdf;*.docx;*.doc",
		file_types_description: '上报数据文件',
		file_size_limit: "20 MB",
		file_upload_limit: "0",
		
		// handlers
		file_dialog_start_handler: fileDialogStart,
		file_queued_handler: fileQueued,
		file_queue_error_handler: fileQueueError,
		file_dialog_complete_handler: fileDialogComplete,
		upload_start_handler: uploadStart,
		upload_progress_handler: uploadProgress,
		upload_success_handler: uploadSuccess,
		upload_complete_handler: uploadComplete,
		
		button_placeholder_id : "attachmentsUpload",
		button_image_url : ctx+"/static/images/SmallSpyGlassWithTransperancy_17x18.png",
		button_text : '<span class="button">添加附件<span class="buttonSmall">(20 MB Max)</span></span>',
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
function fileDialogStart() {
	if (queueErrorArray) {
		queueErrorArray = null;
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
function fileQueued(file) {
	var swfUpload = this;
	var listItem = '<li id="' + file.id + '">';
	listItem += '文件:<em>' + file.name + '</em>(' + Math.round(file.size/1024) + ' KB)';
	listItem += '<div class="progressBar"><div class="progress"></div></div>'
			  + '<span class="progressValue"></span>'
			  + '<p class="status" >Pending</p>'
			  + '<span class="min-dele" >&nbsp;</span>'
			  + '</li>';
	$("#logList").append(listItem);
	$("li#" + file.id + " .min-dele").click(function(e) {
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
	});
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
function fileQueueError(file,errorCode,message) {
	if (!queueErrorArray) {
		queueErrorArray = [];
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
		errorFile.error = '文件类型受限.';
		break;
	case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
		errorFile.error = '文件为空文件.';
		break;
	case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
		errorFile.error = '超出文件数量限制.';
		break;
/*	case SWFUpload.QUEUE_ERROR.UPLOAD_LIMIT_EXCEEDED:
		errorFile.error = "上传的文件数量超过了允许的最大值";
		break;	*/
	default:
		alert('加载入队列出错.');
		break;
	}
	queueErrorArray.push(errorFile);
}

/**
* 选择文件对话框关闭时触发,报告所选文件个数、加入上传队列文件数及上传队列文件总数
* @param numSelected 选择的文件数目
* @param numQueued 加入队列的文件数目
* @param numTotalInQueued 上传文件队列中文件总数
*/
function fileDialogComplete(numSelected,numQueued,numTotalInQueued) {
	var swfupload = this;
	if (queueErrorArray && queueErrorArray.length) {
		var table = $('<table><tr><td>文件</td><td>大小</td></tr></table>');
		for(var i in queueErrorArray) {
			var tr = $('<tr></tr>');
			if(queueErrorArray[i].code == -100){
				table = $('<table></table>');
				var info =  '<td>超出了最大文件上传数！</td>';
			}else{
				var info = '<td>' + queueErrorArray[i].file.name + '<span style="color:red">(' + queueErrorArray[i].error + ')</span></td>'
						+ '<td>' + queueErrorArray[i].file.size + 'bytes</td>';
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
//					$("#queueStatus").text('选择文件:' + numSelected + ' / 加入队列文件:' + numQueued);
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
function uploadStart(file) {
	if (file) {
		$("#logList li#" + file.id).find('p.status').text('上传中...');
		$("#logList li#" + file.id).find('p.progressValue').text('0%');
	}
}

/**
* 文件上传过程中定时触发,更新进度显示
* @param file 上传的文件
* @param bytesCompleted 已上传大小
* @param bytesTotal 文件总大小
*/
function uploadProgress(file,bytesCompleted,bytesTotal) {
	var percentage = Math.round((bytesCompleted / bytesTotal) * 100);
	$("#logList li#" + file.id).find('div.progress').css('width',percentage + '%');
	$("#logList li#" + file.id).find('span.progressValue').text(percentage + '%');
	if(percentage>=100){
		$("#logList li#" + file.id).find('span.progressValue').text('99%');
	}
/*	var percent = Math.ceil((bytesCompleted / bytesTotal) * 100);
	 
    var progress = new FileProgress(file, this.customSettings.progressTarget);
    progress.setProgress(percent);
    progress.setStatus("Uploading...");*/
}

/**
* 文件上传完毕并且服务器返回200状态码时触发
* @param file 上传的文件
* @param serverData 
* @param response
*/
function uploadSuccess(file,serverData,response) {
	
	try{
//		var progress = new FileProgress(file,  this.customSettings.upload_target);
		var data = JSON.parse(serverData);
    	 if(data.code=='0'){
     		window.top.iDialogMsg("提示",data.message);
     		return ;
     	}
    	 if(data==null || data=='undefined'){
     		return;
     	 }
         if (typeof(data.status) != 'undefined') {
        	 if (data.status == 'success') {
        		 var fileIds = $("#fileIds").val();
        		 $("#fileIds").val(fileIds+data.fileId+","); // 获取附件ID
        	 }
         }
	}catch(e){
		e.printStackTrace();  
	} 
	var item = $("#logList li#" + file.id);
	item.find('div.progress').css('width','100%');
	item.find('span.progressValue').css('color','blue').text('100%');
	item.find('div.progressBar').hide();
	item.find('span.progressValue').hide();
	
	item.addClass('success').find('p.status').html('上传完成!');
}

/**
* 在一个上传周期结束后触发(uploadError及uploadSuccess均触发)
* 在此可以开始下一个文件上传(通过上传组件的uploadStart()方法)
* @param file 上传完成的文件对象
*/
function uploadComplete(file) {
	this.uploadStart();
}