<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%> 
<title>系统设置-通知公告-修改</title>

<style>
	.cke_chrome{width:898px !important;}
	.cke_contents{height:325px !important;}
</style>

<!--ckeditor-->
<script src="${ctx }/static/thirdparty/ckeditor/ckeditor.js"></script>
<script src="${ctx }/static/thirdparty/ckeditor/config.js"></script>
<!--本页面js-->

<script type="text/javascript">
var edit 
$(function(){
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
		document.location.href = "${ctx}/notice/noticelist?v="+new Date().getTime();
	});
	//保存
	$("#save").click(function(){
		var url = "${ctx}/notice/addnotice";
		var $announceId = $("#announceId").val();
		if($announceId != null && $announceId != ""){
			url = "${ctx}/notice/editnotice";
		}
		if(checkform()){
			$("#myForm").ajaxSubmit({
				url : url,
				type : 'post',
				error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
				success : function(data) {
					if(data != 0){
						// 提示失败
						window.top.iDialogMsg("提示","保存失败！");
					}else{
						window.top.iDialogMsg("提示","保存成功！");
						setTimeout('document.location.href = "${ctx}/notice/noticelist?v="+new Date().getTime();',1000);	
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

</head>
<body> 
<form id="myForm" method="post">
<input type="hidden" id="announceId" name="announceId" value="${announcement.announceId}">
<div class="hyx-na-tit fl_l"><label class="lab">通知公告 > ${empty announcement.announceId ?'新增':'修改' }</label></div>
<div class="hyx-na-form">
	<p class="par"><label class="lab">标题：</label><input type="text" id="title" name="title" maxlength="50" value="${announcement.title }" class="ipt" /></p>
	<div class="area">
		<label class="lab">内容：</label>
		<div class="txt"><textarea  id="editor"  name="content" class="add_notice_edit">${announcement.content }</textarea></div>
	</div>
	<div class="com-btnbox">
		<a href="javascript:;" class="com-btna com-btna-sty fl_l" id="save"><label>确定</label></a>
		<a href="javascript:;" class="com-btna fl_r" id="cancel"><label>取消</label></a>
	</div>
	<div id="content" style="display: none;">
		${announcement.content}
	</div>
</div>
</form>
</body>
</html>
