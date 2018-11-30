	var swfu;
	var swfu1;
	window.onload = function () {
	        var url = 'http://up.test.com/fileupload/upload';
			swfu = new SWFUpload({
				upload_url: url,
				post_params: {
		            "type":'3'
		        },				
		        file_post_name: 'file', 
				// File Upload Settings
				file_size_limit : "20 MB",	// 1000MB
				file_types : "*.jpg;*.jpeg;*.png;*.pdf;*.docx;*.doc",
				file_types_description : "所有文件",
				file_upload_limit : "5",
								
				file_queue_error_handler : function (file, errorCode, message) {
					try {
						var msg = "文件上传错误";
						var errorName = "";
						if (errorCode === SWFUpload.errorCode_QUEUE_LIMIT_EXCEEDED) {
							errorName = "You have attempted to queue too many files.";
						}

						if (errorName !== "") {
							alert(errorName);
							return;
						}
						
						switch (errorCode) {
						case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
							msg = "文件大小为0";
							break;
						case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
							msg = "文件大小超过限制";
							break;
						case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
							msg = "无效类型";
							break;
						case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
							msg = "用户选取的文件超过了允许的数量";
							break;	
						case SWFUpload.QUEUE_ERROR.UPLOAD_LIMIT_EXCEEDED:
							msg = "上传的文件数量超过了允许的最大值";
							break;	
						default:
							msg = message;
							break;
						}
						window.top.iDialogMsg("提示",msg);
					} catch (ex) {
						this.debug(ex);
					}
				},
				file_dialog_complete_handler : function(numFilesSelected, numFilesQueued) {
					try {
						if (numFilesQueued > 0) {
// 							document.getElementById('btnCancel').disabled = "";
							this.startUpload();
						}
					} catch (ex) {
						this.debug(ex);
					}
				},//选择好文件后提交
// 				file_queued_handler : fileQueued,
				upload_progress_handler : uploadProgress,
				upload_success_handler : function(file, serverData){
					try {
						var progress = new FileProgress(file,  this.customSettings.upload_target);
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
			            		 $("#showFile").append("<li id=\"file_li_"+data.fileId+"\"><label class=\"name\">"+data.fileName+"</label><a href=\"###\" onclick=\"delfile('"+data.fileId+"')\" class=\"link\">删除</a></li>");
			            	 }
			             }
					} catch (ex) {
						this.debug(ex);
					}
				},
				upload_complete_handler : uploadComplete,
	
				// Button Settings
			    	button_image_url : ctx+"/static/images/SmallSpyGlassWithTransperancy_17x18.png",
				button_placeholder_id : "spanButtonPlaceholder",
				button_width: 200,
				button_height: 18,
				button_text : '<span class="button">选择文件 <span class="buttonSmall">(10 MB Max)</span></span>',
				button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
				button_text_top_padding: 0,
				button_text_left_padding: 18,
				button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
				button_cursor: SWFUpload.CURSOR.HAND,			
				// Flash Settings
				flash_url : ctx+"/static/js/swfupload/swfupload.swf",	
				custom_settings : {
					upload_target : "divFileProgressContainer"
				},
				// Debug Settings
				debug: false  //是否显示调试窗口
			});
			
			
			  var url1 = ctx + '/fileupload/iptEmailSendFile';
				swfu1 = new SWFUpload({
					upload_url: url1,			
			        file_post_name: 'file', 
					// File Upload Settings
					file_size_limit : "5 MB",	// 1000MB
					file_types : "*.xls;*.xlsx",
					file_types_description : "文件",
					file_upload_limit : "1",
									
					file_queue_error_handler : function (file, errorCode, message) {
						try {
							var msg = "文件上传错误";
							var errorName = "";
							if (errorCode === SWFUpload.errorCode_QUEUE_LIMIT_EXCEEDED) {
								errorName = "You have attempted to queue too many files.";
							}

							if (errorName !== "") {
								alert(errorName);
								return;
							}
							
							switch (errorCode) {
							case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
								msg = "文件大小为0";
								break;
							case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
								msg = "文件大小超过限制";
								break;
							case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
								msg = "上传文件必须是：excel";
								break;
							case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
								msg = "用户选取的文件超过了允许的数量";
								break;	
							case SWFUpload.QUEUE_ERROR.UPLOAD_LIMIT_EXCEEDED:
								msg = "上传的文件数量超过了允许的最大值";
								break;	
							default:
								msg = message;
								break;
							}
							window.top.iDialogMsg("提示",msg);
						} catch (ex) {
							this.debug(ex);
						}
					},
					file_dialog_complete_handler : function(numFilesSelected, numFilesQueued) {
						try {
							if (numFilesQueued > 0) {
//	 							document.getElementById('btnCancel').disabled = "";
								this.startUpload();
							}
						} catch (ex) {
							this.debug(ex);
						}
					},//选择好文件后提交
//	 				file_queued_handler : fileQueued,
					upload_progress_handler : uploadProgress,
					upload_success_handler : function(file, serverData){
						try {
							var progress = new FileProgress(file,  this.customSettings.upload_target);
							var data = JSON.parse(serverData);
				        	 if(data==null || data=='undefined'){
				         		return;
				         	 }
				             if (typeof(data.msg) != 'undefined') {
				            	 var msg = data.msg;
				            	 msg = msg.replace(/&lt;/g,"<");
				            	 msg = msg.replace(/&gt;/g,">")
				            	$("#showMsg").html(msg);
				             }
						} catch (ex) {
							this.debug(ex);
						}
					},
					upload_complete_handler : uploadComplete,
		
					// Button Settings
				    	button_image_url : ctx+"/static/images/SmallSpyGlassWithTransperancy_17x18.png",
					button_placeholder_id : "spanButtonPlaceholder1",
					button_width: 200,
					button_height: 18,
					button_text : '<span class="button">选择文件 <span class="buttonSmall">(5 MB Max)</span></span>',
					button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
					button_text_top_padding: 0,
					button_text_left_padding: 18,
					button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
					button_cursor: SWFUpload.CURSOR.HAND,			
					// Flash Settings
					flash_url : ctx+"/static/js/swfupload/swfupload.swf",	
					custom_settings : {
						upload_target : "divFileProgressContainer1"
					},
					// Debug Settings
					debug: false  //是否显示调试窗口
				});
		};
