var totalFileLength, totalUploaded, fileCount, filesUploaded;
$(function(){
	$(".file").change(function(e){
		var img = e.target.files[0];
		var windowURL = window.URL || window.webkitURL;
		var dataURL = null;
		if(!img){
			return false;
		}
		dataURL = windowURL.createObjectURL(img);
		//var targetSrc = e.target.value;
		$(".personal-center-imgedit-item-box").html('<img  src="'+dataURL+'" class="edit-img">')
		exampleImg($(".edit-img"))
	})
	$("#saveResBtn").click(function(e){
		e.stopPropagation();
		var $this = $(this);
		var x = $this.data("x");
		var y = $this.data("y");
		var width = $this.data("width");
		var height = $this.data("height");
		//开始上传
		uploadNext(x,y,width,height)
	})
	$("#cacleResBtn").click(function(e){
		e.stopPropagation();
		closeParentPubDivDialogIframe("img_iconbind");
	})
	exampleImg($(".edit-img"));
})
function exampleImg(target){
	target.cropper({
		  aspectRatio: 1/ 1,
		  crop: function(data) {
		    var x = data.x;
		    var y = data.y;
		    var widths = data.width;
		    var heights = data.height;
		    var sureBtn = $("#saveResBtn");
		    sureBtn.data("x",x);
		    sureBtn.data("y",y);
		    sureBtn.data("width",widths);
		    sureBtn.data("height",heights);
		  }
	});
}

function onUploadFailed(e) {
    window.top.iDialogMsg("提示","Error uploading file");
}

function uploadNext(x,y,width,height) {
	totalUploaded = filesUploaded = 0;
	var tsmUpLoadServiceUrl = $("#tsmUpLoadServiceUrl").val();
    var xhr = new XMLHttpRequest();
   
    var fd = new FormData();
    var file = document.getElementById('files').files[filesUploaded];
    //需要传的数据
    if(file){
    	fd.append("file", file);
    }
    fd.append("orgId",$("#orgId").val());
    fd.append("account",$("#account").val());
    fd.append("x",x);
    fd.append("y",y);
    fd.append("width",width);
    fd.append("height",height);

    xhr.addEventListener("error", onUploadFailed, false);
    xhr.open("POST", tsmUpLoadServiceUrl+ctx+"/fileupload/uploadImg");
        xhr.onreadystatechange=function(){
           if (xhr.readyState==4 && xhr.status==200){
              txt = xhr.responseText;
              var data=JSON.parse(txt);
		       if(data.status){
		    	   window.top.iDialogMsg("提示",data.message);
		    	   setTimeout(function(){
		    		   window.parent.location.reload();
		    		   closeParentPubDivDialogIframe("img_iconbind");
		    	   },500);
		       }else{
		    	   iDialogMsg("提示",data.message)
		       }
          }
      };
      xhr.send(fd);
 }