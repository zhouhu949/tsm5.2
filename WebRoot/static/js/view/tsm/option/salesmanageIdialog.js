$(function(){
	//取消关闭窗口
	$("#close02").on("click",function(e){
		e.stopPropagation();
		closeParentPubDivDialogIframe("fetur_to_follow_");
	});
	//确认业务逻辑
	$(".salesidialog_surebtn").on("click",function(e){
		e.stopPropagation();
		var arr=[];
	      var  flag=true;
	      var require= true;
	      var max=$("#optionValue").val() == "" ? 0 : $("#optionValue").val();
	      $(".ipt").each(function(i,obj){
	    	if($.trim($(obj).val()) == ""){
	    		require = false;
	    	}
	      	if(!(/^[1-9]\d*$/.test($(obj).val()))){
	      		flag=false;
	      	}
	     	var json={};
	      	json.optionId=$(obj).attr("optionId");
	      	json.optionName=$(obj).attr("optionName");
	      	json.optionValue=$(obj).val();
	      	arr.push(json);
	      })
	      if(!require){//有值未填
	    	  window.parent.warmDivDialog('_warm_text_val','跟进天数必填！');
		      	return false;
	      }
	      if(!flag){//验证未通过
	     	 window.parent.warmDivDialog('_warm_text_val','跟进天数只能为正整数！');
	      	return false;
	      }
	      var returnStr=JSON.stringify(arr);
	      var b = new Base64();  
		  var base64Data = b.encode(returnStr);  
		  $("#fetur_to_follow_input", window.parent.document).val(base64Data);
		  setTimeout(function(){
			  closeParentPubDivDialogIframe("fetur_to_follow_");
		  },500)
	})
})