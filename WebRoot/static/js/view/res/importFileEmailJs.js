 var totalFileLength, totalUploaded, fileCount, filesUploaded,totalSize;

 
 //接收人文件上传
 function onFileSelect(e) {
        var files = e.target.files; // FileList object
        var output = [];
        fileCount = files.length;
        totalFileLength = 0;
        for (var i=0; i<fileCount; i++) {
            var file = files[i];
            var names=file.name;
            var sarr=names.split(".");
            var lastOne=sarr.pop();
            var Mbs=file.size/(1024*1024);
            totalSize=file.size;
			if(Mbs > 5 ){
				window.top.iDialogMsg("提示","文件大小超过限制！")
				return false;
			}
            if(lastOne !== "xls" && lastOne !== "xlsx"){
            	window.top.iDialogMsg("提示","只能上传excel文件！");
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
        document.getElementById('selectedFiles').innerHTML = output.join('');
        startUpload();
    }

        function onUploadFailed(e) {
            window.top.iDialogMsg("提示","Error uploading file");
        }

        function uploadNext() {
            var xhr = new XMLHttpRequest();
            var fd = new FormData();
            var file = document.getElementById('files').files[filesUploaded];
            fd.append("file", file);
            fd.append("type","3");
            fd.append("orgId",orgId);
            fd.append("userId",userId);
            fd.append("account",account);
            xhr.upload.onprogress =progressFunction;
            xhr.upload.onloadstart=loadStart;
            xhr.upload.onloadend= loadEnd;
            xhr.addEventListener("error", onUploadFailed, false);
            var fie=document.getElementById("files");
            var classNa=fie.getAttribute("class");
            	if(classNa == "email"){
                	xhr.open("POST", tsmUpLoadServiceUrl+ctx+"/fileupload/iptEmailSendFile");
                }else{
                	xhr.open("POST", tsmUpLoadServiceUrl+ctx+"/fileupload/iptSmsSendFile");
                }
            xhr.onreadystatechange=function(){
               if (xhr.readyState==4 && xhr.status==200)
               {
                  var txt = xhr.responseText;
                  var data=JSON.parse(txt);
            	   	 if(data==null || data=='undefined'){
            	    		return;
            	    	 }
            	     if (typeof(data.msg) != 'undefined') {
            	    	 var msg = data.msg;
            	    	 msg = msg.replace(/&lt;/g,"<");
            	    	 msg = msg.replace(/&gt;/g,">");
            	       	$("#showMsg").html(msg);
            	       	setTimeout(function(){
				    		$.dialog.get["process_idialog"].remove();
				    	},500)
            	       }
              }
          };
          xhr.send(fd);
      }
      function startUpload() {
        totalUploaded = filesUploaded = 0;
        uploadNext();
    }
      
 //上传附件
      function onFileSelects(e) {
          var files = e.target.files; // FileList object
          var output = [];
          fileCount = files.length;
          totalFileLength = 0;
          for (var i=0; i<fileCount; i++) {
              var file = files[i];
              var names=file.name;
              var sarr=names.split(".");
              var lastOne=sarr.pop();
              var Mbs=file.size/(1024*1024);
              totalSize = file.size;
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
              fd.append("orgId",orgId);
              fd.append("userId",userId);
              fd.append("account",account);
              xhr.addEventListener("error", onUploadFaileds, false);
              xhr.upload.onprogress =progressFunction;
              xhr.upload.onloadstart=loadStart;
              xhr.upload.onloadend= loadEnd;
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
      
    window.onload = function() {
        document.getElementById('files').addEventListener(
        'change', onFileSelect, false);//接收人上传文件
        document.getElementById('addFujian').addEventListener(
                'change', onFileSelects, false);//附件上传
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