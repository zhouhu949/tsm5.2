<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
	<title>签名操作</title>
	<!--公共样式-->
	<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css"><!--主要样式-->

	<!--ckeditor-->
<script src="${ctx }/static/thirdparty/ckeditor/ckeditor.js"></script>
<script src="${ctx }/static/thirdparty/ckeditor/config.js"></script>
	<script type="text/javascript">
	var edit;
	$(function(){
        $("input:text:first").focus();
	
	
		var opera = false;
		//加载ckeditor
		CKEDITOR.config.height = '200';
		CKEDITOR.config.width = '600';
		var content = $("#content").html();
		edit = CKEDITOR.replace('editor', {
			customConfig: ctx+'/static/js/view/tsm/notice/notice_config.js?'+(Math.random()*10+1)
	    });
		edit.setData(content);
		
		//取消
		$("#cancel").click(function(){
			closeParentPubDivDialogIframe('alert_new_char_sign');
		});
		//保存
		$("#save").click(function(){
			if(!opera && checkform()){
				opera = true;
				$("#dialogForm").ajaxSubmit({
					type : 'post',
					error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
					success : function(data) {
						if(data != 0){
							// 提示失败
							window.top.iDialogMsg("提示","保存失败！");
						}else{
							window.top.iDialogMsg("提示","保存成功！");
							var id = $("input[name='id']").val();
							window.parent.signData();	
							setTimeout('closeParentPubDivDialogIframe("alert_new_char_sign");',1000);	
						}
					}
				});
			}
		});
	});

	//验证
	function checkform(){
		var title = $.trim($("#title").val());
		var content = $.trim(edit.getData());
		var len = content.length;
		
		if(title == "" || content == ""){
			window.top.iDialogMsg("提示","请填写标题和内容！");
			return false;
		}
		if(content.length > 4000){
			window.top.iDialogMsg("提示","当前长度:"+len+"字节，系统限制:4000字节。<br/><font color='red'>（文本内容包含样式字节数，一个汉字占2字节）</font>");
			return false;
		}
		$("#editor").val(content);
		return true;
	}
	</script>
	<style>
		.cke_chrome{margin:0 auto !important;}
	</style>
</head>
<body>
	<form action="${ctx }/email/sign/operaSign" id="dialogForm" method="post">
		<input type="hidden" name="id" value="${item.id}">
		<div class="cust-impo-res" style="width:auto;padding-left:0;">
		<div class="com-search clearfix" style="min-height:30px;z-index:999">
				<label class="fl_l" for="" style="width:auto;margin-left:15px;height:27px;line-height:27px;">个性签名：</label>
				<input type="text" name="title" id="title"  value="${item.title}">
			</div>
			<div class="new-words-cont">
				<textarea id="editor" name="context" id="" cols="30" rows="10"></textarea>
			</div>
			</div>
			<div class="bomb-btn" style="margin-top:20px">
					<label class="bomb-btn-cen">
						<a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" id="save" href="javascript:;"><label>确定</label></a>
						<a class="com-btna bomp-btna cancel-btn fl_l" id="cancel" href="javascript:;"><label>取消</label></a>
					</label>
			</div>
			<input type="text"  style="display:none;">
			<div id="content" style="display: none;">
				${item.context}
			</div>
	</form>
</body>
</html>