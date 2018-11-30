 var totalFileLength, totalUploaded, fileCount, filesUploaded;



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
            //需要传的数据
            fd.append("f", file);
            fd.append("orgId",impShiroOrgId);
            fd.append("account",impShiroAccount);
            fd.append("userId",impShiroUserId);
            fd.append("userGroupId",impShiroUserGroupId);
            fd.append("isState",impShiroUserIsState)

            xhr.addEventListener("error", onUploadFailed, false);
            xhr.open("POST", tsmUpLoadServiceUrl+ctx+"/fileupload/leadimpHyx/upload");
            xhr.onreadystatechange=function(){
               if (xhr.readyState==4 && xhr.status==200)
               {
                  txt = xhr.responseText;
                  var data=JSON.parse(txt);
			                  
                  if(data.status == "erro"){
                	  window.top.iDialogMsg("提示",data.message);
                  }else{
                	  headerAndFistRowData = data.headerAndFistRowData;
  					if (headerAndFistRowData.rows > 10001) {
  						window.top.iDialogMsg("提示","文件不能超过10000行!");
  					} else {
  						importFields = data.importFields;
  						id = data.id;
  						$("#filedSets").val(data.filedSets);// 设置必填字段
  						createMatchTable();
  						step_();
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
        document.getElementById('files').addEventListener(
        'change', onFileSelect, false);
    }
    
    // 导出导入模板
    function expTempExcel(){
       
    	var url = ctx+"/credit/leadimp/expTempExcel";
    	document.location = url;	
    }
    
    // 导出联系人导入模板
    function expDetailTempExcel(){
    	var url = ctx+"/resimp/expDetailTempExcel";
    	document.location = url;	
    }