<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<%@ include file="/common/include.jsp"%> 
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
<head>
<title>系统设置-系统属性设置-编辑模板</title>
<script type="text/javascript">
$(function(){

    noteCount(); // 短信条数

	$(".cancel-btn").click(function(){
		closeParentPubDivDialogIframe('sms_temp_set_b');
	});

	  // 保存
    $(".sure-btn").click(function(){
    	if(checkIsNull()){
        	$('#operaForm').ajaxSubmit({
    			url : ctx+'/sys/smsTemp/operaSmsTemp.do',
    			type : 'post',
    			error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
    			success : function(data) {	
    				if(data == 0){			
    					window.top.iDialogMsg("提示","保存成功！");
    					setTimeout('window.parent.document.forms[0].submit();',1000);					
    				}else{
    					window.top.iDialogMsg("提示","保存失败！");
    				}
    			}
    		});	
    	}
    });
	
	/*提示信息*/
    $('.bomp-pos,.bomp-pos-a').find('.bomp-focus,.lab_hid').click(function(){
    	var ipt_a = $(this).parent().find('.bomp-focus');
        ipt_a.focus();
        $(this).parent().find('.lab_hid').hide();
        ipt_a.blur(function(){
            if($(this).val() == ''){
                $(this).parent().find('.lab_hid').show();
            }
         }); 
    });
});

//短信字数及条数统计：
function noteCount() {
	var $jq_content_area = $("#jq_content_area");
	var $jq_sign_input = $("#jq_sign_input");
	var $jq_charSize = $("#jq_charSize"); // 还可以输入多少字
	var $jq_noteCount_span = $("#jq_noteCount_span"); // 短信条数
	
	var AREA_MAX_LEN = 280; // 最多可以输入的字节
	var oneNoteMaxChars = 66; // 一条短信最多字节
	var areaVal = $jq_content_area.val().trim();
	// 过滤当字数超出AREA_MAX_LEN 的情况：
	if (areaVal.length > AREA_MAX_LEN) {
		areaVal = areaVal.substring(0, AREA_MAX_LEN);
		$jq_content_area.val(areaVal);
	}
	
	// 文本域长度：【回车换行符统一计为2字】
	areaVal = areaVal.replace(/\r\n/g, "##").replace(/\n/g, "##").replace(/\r/g, "##");
	var total = 0;
	total = areaVal.length;
	var signVal = $jq_sign_input.val().trim();
	if (signVal.length != 0) {
		total += signVal.length;
	}
	/**var v = oneNoteMaxChars - total % oneNoteMaxChars;
	if (total > 0 && total % oneNoteMaxChars == 0) {
		v = 0;
	}*/
	
	$jq_charSize.text(AREA_MAX_LEN-parseInt(total));
	$jq_noteCount_span.text(Math.ceil(total / oneNoteMaxChars));
}
// 验证
function checkIsNull(){
	var tsgId = $("#tsgId").val();
	if(tsgId == null || tsgId == ""){
		alert("请选择分类！");
		return false;
	}	
	if($("#jq_content_area").val() == null || $.trim($("#jq_content_area").val())==""){
		alert("短信内容不能为空！");
		return false;
	}
	return true;
}

</script>
</head>
<body> 
<form id="operaForm" method="post">
<input type="hidden" name="id" value="${item.id}">
<div class='bomp-cen alert_d'>
	<div class="bomp_mess_left fl_l">
		<p class='bomp-p'>
			<label class='lab_a fl_l'>选择类型：</label>
			<select class='sel_a fl_l' name="tsgId" id="tsgId">
			<option value="">-请选择-</option>
			<c:forEach items="${smsTempGroups }" var="group">
				<option value="${group.tsgId}" <c:if test="${group.tsgId eq item.tsgId or group.tsgId eq tsgId }">selected</c:if>>${group.groupName}</option>
			</c:forEach>
			</select>
		</p>
		<p class='bomp-p bomp-p-widtha bomp-pos-a'>
			<label class='lab_a fl_l'>短信内容：</label>
			<label class="area_box">
				<textarea class='area_a bomp-focus fl_l' id="jq_content_area" onkeyup="noteCount()" onblur="noteCount()" name="content">${item.content}</textarea>
				<span class="box_a">剩余<b class="bomp-red" id="jq_charSize">280</b>字&nbsp;&nbsp;预计<b class="bomp-red" id="jq_noteCount_span">0</b>条短信&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<label class="lab_hid" <c:if test="${!empty item.content}">style="display: none"</c:if>>请输入短信内容</label>
			</label>
		</p>
		<p class='bomp-p bomp-p-widtha bomp-pos'>
			<label class='lab_a fl_l'>签名：</label><input type='text' id="jq_sign_input" value='${smslabel }'  readonly="readonly" class='ipt_a w_c bomp-focus fl_l' />
		</p>
	</div>
	<div class='bomb-btn'>
		<label class='bomb-btn-cen'>
			<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>确定</label></a>
			<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
		</label>
	</div>
</div>
</form>
</body>
</html>