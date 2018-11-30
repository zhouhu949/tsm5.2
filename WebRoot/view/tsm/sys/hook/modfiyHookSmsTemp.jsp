<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%> 
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--主要样式-->
<title>系统设置-挂机短信规则设置-挂机短信模板设置-编辑</title>

<script type="text/javascript">
$(function(){
	/*表单优化*/
    noteCount();
	/** 短信内容事件 默认显示 请输入短信内容 */
    $(".bomp-pos-a").find('.lab_hid').click(function(){
        var ipt_b = $(this).parent().find('.bomp-focus');
        ipt_b.focus();
        $(this).parent().find('.lab_hid').hide();
        ipt_b.blur(function(){
            if($(this).val() == ''){
                $(this).parent().find('.lab_hid').show();
            }
         }); 
    });
	
    /** 短信内容事件 默认显示 请输入短信内容 */
    $(".bomp-pos-a").find('.bomp-focus').click(function(){
        var ipt_c = $(this);
        ipt_c.focus();
        $(this).parent().parent().find('.lab_hid').hide();
        ipt_c.blur(function(){
            if($(this).val() == ''){
                $(this).parent().parent().find('.lab_hid').show();
            }
         }); 
    });
    
    // 保存
    $("#save_buf").click(function(){
    	if(checkIsNull()){
        	$('#idialogForm').ajaxSubmit({
    			url : ctx+'/sys/hookSms/addOrEditTemp',
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
    
 	// 取消
    $("#cancel_buf").click(function(){
    	closeParentPubDivDialogIframe('alert_hook_sms_rules_set_b_a');
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

function checkIsNull(){
	var statusVal = "";
	$("input[id^=status_]:checked").each(function(){
		statusVal += $(this).val() + ",";
	});
	if(statusVal == ""){
		window.top.iDialogMsg("提示","请选择呼叫类型！");
		return false;
	}else{
		$("#status").val(statusVal.substring(0, statusVal.length-1));
	}
	var sendCondiVal = "";
	$("input[id^=sendCondi_]:checked").each(function(){
		sendCondiVal += $(this).val() + ",";
	});
	if(sendCondiVal == ""){
		window.top.iDialogMsg("提示","请选择短信发送条件！");
		return false;
	}else{
		$("#sendCondi").val(sendCondiVal.substring(0, sendCondiVal.length-1));
	}
	var sendTypeVal = "";
	$("input[id^=sendType_]:checked").each(function(){
		sendTypeVal += $(this).val() + ",";
	});
	if(sendTypeVal == ""){
		window.top.iDialogMsg("提示","请选择发送对象！");
		return false;
	}else{
		$("#sendType").val(sendTypeVal.substring(0, sendTypeVal.length-1));
	}
	
	if($("#jq_content_area").val() == null || $.trim($("#jq_content_area").val())==""){
		window.top.iDialogMsg("提示","短信内容不能为空！");
		return false;
	}
	return true;
}

</script>
</head>
<body> 
<div class='bomp-cen alert_d bomp-hsrs-b'>
	<form id="idialogForm" method="post">
		<input type="hidden" name="id" value="${entity.id}">
	<div class="bomp_mess_left fl_l">
		<p class='bomp-p bomp-p-widtha skin-minimal'>
			<label class='lab_a fl_l'>呼叫类型：</label>
			<input type="hidden" id="status" name="status" value="${entity.status}">
			<input type="checkbox" id="status_0" <c:if test="${fn:contains(entity.status, '0')}">checked</c:if> value="0"/><label class="bomp-hsrs-b-lab">呼入</label>
			<input type="checkbox" id="status_1" <c:if test="${fn:contains(entity.status, '1')}">checked</c:if> value="1"/><label class="bomp-hsrs-b-lab">呼出</label>
		</p>
		<p class='bomp-p bomp-p-widtha skin-minimal'>
			<label class='lab_a fl_l'>短信发送条件：</label>
			<input type="hidden" id="sendCondi" name="sendCondi" value="${entity.sendCondi}">
			<input type="checkbox" id="sendCondi_1" value="1" <c:if test="${fn:contains(entity.sendCondi, '1')}">checked</c:if>/><label class="bomp-hsrs-b-lab">接通</label>
			<input type="checkbox" id="sendCondi_0" value="0" <c:if test="${fn:contains(entity.sendCondi, '0')}">checked</c:if>/><label class="bomp-hsrs-b-lab">未接通</label>
		</p>
		<div class='bomp-p bomp-p-widtha skin-minimal'>
			<label class='lab_a fl_l'>短信发送对象：</label>
			<input type="hidden" id="sendType" name="sendType" value="${entity.sendType}">
			<input type="checkbox" id="sendType_1" value="1" <c:if test="${fn:contains(entity.sendType, '1')}">checked</c:if> /><label class="bomp-hsrs-b-lab">资源</label>
			<div class="bomp-hsrs-b-pos-b">
				<input type="checkbox" id="sendType_7" value="7" <c:if test="${fn:contains(entity.sendType, '7')}">checked</c:if>/><label class="bomp-hsrs-b-lab">访客</label>
			</div>
			<div class="bomp-hsrs-b-drop">
				<input type="checkbox" id="sendType_5" value="5" <c:if test="${fn:contains(entity.sendType, '5')}">checked</c:if> /><label class="bomp-hsrs-b-lab">意向客户</label>
			</div>
			<div class="bomp-hsrs-b-pos">
				<input type="checkbox" id="sendType_2"  value="2"  <c:if test="${fn:contains(entity.sendType, '2')}">checked</c:if> /><label class="bomp-hsrs-b-lab">签约客户</label>
			</div>
			<div class="bomp-hsrs-b-pos-a">
				<input type="checkbox" id="sendType_3" value="3" <c:if test="${fn:contains(entity.sendType, '3')}">checked</c:if> /><label class="bomp-hsrs-b-lab">沉默客户</label>
			</div>
			<div class="bomp-hsrs-b-pos-b">
				<input type="checkbox" id="sendType_4" value="4" <c:if test="${fn:contains(entity.sendType, '4')}">checked</c:if>/><label class="bomp-hsrs-b-lab">流失客户</label>
			</div>
			<div class="bomp-hsrs-b-pos-b">
				<input type="checkbox" id="sendType_6" value="6" <c:if test="${fn:contains(entity.sendType, '6')}">checked</c:if>/><label class="bomp-hsrs-b-lab">公海客户</label>
			</div>
		</div>
		
		<p class="bomp-p bomp-p-widtha bomp-pos-a">
			<label class='lab_a fl_l'>短信内容：</label>
			<label class="area_box">
				<textarea class='area_a bomp-focus fl_l' name="content" id="jq_content_area" onkeyup="noteCount()" onblur="noteCount()">${entity.content}</textarea>
				<span class="box_a">剩余<b class="bomp-red" id="jq_charSize">280</b>字&nbsp;&nbsp;预计<b class="bomp-red" id="jq_noteCount_span">0</b>条短信&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</label>
			<label class="lab_hid" <c:if test="${!empty entity.content}">style="display: none"</c:if>>请输入短信内容</label>
		</p>
		<p class="bomp-p bomp-p-widtha bomp-pos">
			<label class='lab_a fl_l'>签名：</label><input type='text' id="jq_sign_input" value="${smslabel }" readonly="readonly" name="smslabel"  class='ipt_a w_c  bomp-focus fl_l' />
		</p>
		<div class='bomb-btn'>
			<label class='bomb-btn-cen'>
				<a href="javascript:;" class="com-btna bomp-btna com-btna-sty fl_l" id="save_buf"><label>确定</label></a>
				<a href="javascript:;" class="com-btna bomp-btna fl_l" id="cancel_buf"><label>取消</label></a>
			</label>
		</div>
	</div>
	</form>
	<div class="bomp_mess_right fl_l" id="bomp_mess_right">
			<c:import url="/view/tsm/sys/hook/modfiyHookSmsTempRight.jsp"></c:import>
	</div>
</div>
</body>
</html>