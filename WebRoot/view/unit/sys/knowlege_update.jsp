<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
	<!--公共样式-->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--主要样式-->
<%-- 	<link rel="stylesheet" type="text/css" href="${ctx}/static/js/idialog/skins/iblue.css"> --%>
    <script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
	<!--ckeditor-->
<script src="${ctx }/static/thirdparty/ckeditor/ckeditor.js"></script>
<script src="${ctx }/static/thirdparty/ckeditor/config.js"></script>
	<script type="text/javascript">
    $(function(){
	    	//加载ckeditor
			var	editor =CKEDITOR.replace("content", {
				width : 600,
				height : 200,
				customConfig: ctx+'/static/js/view/tsm/notice/notice_config.js?'+(Math.random()*10+1)
	    	});
			     var mark =true;//防止多次保存
				 $("#savebutton").click(function(){
				 	var sort=$("#sort").val(); 
					 if(mark){
					 mark=false;
						 var groupId=$("#groupId").val();
						 var question=$("#question").val();
						 var groupId=$("#groupId").val();
		                if(checkNull(groupId)){
		                	window.top.iDialogMsg("提示", '请选择分类');
		                	mark =true;
							return false;
						} else if (checkNull(question)) {
							window.top.iDialogMsg("提示", '标题不为空');
							mark =true;
							return false;
					    }else if(!getInterceptedStr(question,200)){
					    	window.top.iDialogMsg("提示", '标题不超过100字符');
					    	mark =true;
							return false;
						} else if (checkNull(editor.document.getBody().getText())) {
							window.top.iDialogMsg("提示", '内容不为空');
							mark =true;
							return false;
					    }else{
					    	$.ajax({
					    		url:ctx+"/sys/knowlege/sortIsOK",
					    		data:{
                                    "id":$('input[name="knowlegeId"]').val(),
					    			"sort":sort
					    		},
					    		complete:function(XMLHttpRequest){
					    			mark=true;
					    		},
					    		success:function(data){
					    		
									if(data.status){
										if(data.megType == 0){//如果megType为0 说明排序值可用
											$("#answer").val(editor.getData())
											$("#myForm").ajaxSubmit({
												url : '${ctx}/sys/knowlege/saveOrUpdate',
												type : 'post',
												error : function(XMLHttpRequest, textStatus) {
												},
												success : function(data) {
													if (data == "0") {
														parent.document.forms[0].submit();
													} else {
														window.top.iDialogMsg("提示", '系统异常！');
													}
												}
											});
										}else if(data.megType == 1){
											window.top.iDialogMsg("提示","排序值重复");
										}
									}else{
										window.top.iDialogMsg("提示", data.errorMsg);
										return false;
									}
								}
					    	})
					    }
					 }
				 });
		 $("#cancletBtn").click(function(){
			 parent.closePubDivDialogIframe("editKnowlege");
		 });
    })
	function checkNull(str) {
		if (str == '' || trim(str) == '')
			return true;
	}
	function trim(str) {
		var re = /(^\s*)|(\s*$)/g;
		return str.replace(re, '');
	}

	// 截取固定长度子字符串 sSource为字符串iLen为长度 
	function getInterceptedStr(sSource, iLen) 
	{ 
	    if(sSource.replace(/[^\x00-\xff]/g,"xx").length <= iLen) 
	    { 
	        return true; 
	    } else{
	            return false;
	        } 
	}
	
	function clickAdd(groupId){
		$('#groupId').val(groupId);
	}
</script>
	<style>
		.cke_chrome{margin:0 auto !important;}
	</style>
</head>
<body>
	<form method="post" action="${ctx}/sys/knowlege/saveOrUpdate" id="myForm" name="myForm">
	   <input type="hidden" id="ctPath" name="ctPath" value="${ctx}">
	   <input type="hidden" name="knowlegeId" value="${knowlege.knowlegeId }"/>
	   <input type="hidden" id="groupId" name="groupId" value="${groupId }">
	   <input type="hidden" id="answer" name="answer" value="">
	   <div class="cust-impo-res" style="width:auto;padding-left:0;">
		   <div class="com-search clearfix" style="min-height:30px;z-index:999">
				<label class="fl_l" for="" style="width:auto;margin-left:15px;">分类：</label>
				<dl class="select" style="z-index:999">
					<dt>--${knowlegeGroup.groupName}--</dt>
					<dd>
						<ul style="width:134px;">
						<li>--请选择--</li>
	                       <c:forEach var="group" items="${knowlegeGroups}" >
	                           <li><a href="###" onclick="clickAdd('${group.groupId}')">${group.groupName}</a></li>
	                       </c:forEach>
						</ul>
					</dd>
				</dl>
			</div>
		   <div class="com-search clearfix" style="min-height:30px;">
				<label class="fl_l" for="" style="width:auto;margin-left:5px;">排序值：</label>
				<input type="text" id="sort" name="sort" value="${knowlege.sort}" maxlength="">
			</div>
			<div class="new-words-title">
				<label for="">标题：</label><input type="text" value="${knowlege.question }" name="question" id="question">
			</div>
			<div class="new-words-cont">
				<textarea id="content" name="content"  cols="30" rows="10">${knowlege.answer}</textarea>
			</div>
		</div>
		<div class="bomb-btn" style="margin-top:20px">
				<label class="bomb-btn-cen">
					<a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="javascript:;" id="savebutton"><label>保存</label></a>
					<a class="com-btna bomp-btna cancel-btn fl_l" href="javascript:;" id="cancletBtn"><label>取消</label></a>
				</label>
		</div>
</form>
</body>
</html>