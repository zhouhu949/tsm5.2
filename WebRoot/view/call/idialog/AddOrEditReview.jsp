<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
	.textarea_tip {position: absolute;left: 104px;top: 3px;display: inline-block;height: 20px;line-height: 20px;color: #e1e1e1;font-size: 12px;}
</style>
<script type="text/javascript">
var oldtext = '${review.revComment}';
var oldsel = '${review.recordExampId}';
var isBan = false;
var base = "";
$(function(){
		ifhav();
		function ifhav(){
			if($("#sftext").val()){
					$(".textarea_tip").hide();
				}
			$("#sftext").focusin(function(){
				$(".textarea_tip").hide();
			});
				
			$("#sftext").focusout(function(){
				if($(this).val().length == 0){
					$(".textarea_tip").show();
				}
			});
		}

		base = $.trim($("input[name='base']").val());
		//设置下拉列表选中项
		$("#sel").each(function(){
			$(this).children("option").each(function(){
				if($(this).val() == oldsel){
					$(this).attr("selected",true);
				}
			});
		});
		//绑定新增按钮事件
		$("#add").click(function(){
			var sel = $("#sel").val();
			var text = $.trim($("#sftext").val());
			if(checkData(sel,text)){
				toSubmit(1);
			}
		});
		//绑定修改按钮
		$("#edit").click(function(){
			var sel = $("#sel").val();
			var text = $.trim($("#sftext").val());
			if(oldsel == sel && oldtext == text){
				closeParentPubDivDialogIframe('add_review_');	
				return;
			}
			if(checkData(sel,text)){
				toSubmit(2);
			}
		});
});
//异步提交数据
function toSubmit(type){
	if(isBan == false){
		isBan = true;
		var url = base+"/callrecord/review/callOpera";
		var msg = "";
		if(type == 1){			
			msg = "添加成功！";
		}else{
			msg = "修改成功！";
		}
		$("#myform1").ajaxSubmit({
			url : url,
			type : 'post',
			success : function(data) {
				if(data != ''){
					if(data == 0){
						window.top.iDialogMsg("提示",msg);
						setTimeout(function(){
							closeParentPubDivDialogIframe('add_review_');	
						},1000);
					}else{
						window.top.iDialogMsg("提示","网络繁忙，稍后再试！");
						isBan = false;
					}
					
				}
			}
		});
	}
}
	
//验证数据
function checkData(sel,text){
	if(sel == ''){
		window.top.iDialogMsg("提示","请选择范例类型！");
		return false;
	}
	if(text == ''){
		window.top.iDialogMsg("提示","请输入点评内容！");
		return false;
	}

	if(text.length > 300){
		window.top.iDialogMsg("提示","点评内容限制在150字符以内！");
		$("#sftext").val(text.substring(0,150));
		return false;
	}
	return true;
}
</script>
</head>
<body>
<!--加入示范弹出层-->
<input type="hidden" name="base" value="${ctx}"/>
<div class="pop_layer tape">
    <div class="pop_layer_cont padding_10">
          <form class="tape_form" id="myform1">
            <p class="overflow_hidden m_b" style="height:40px;line-height:40px;">
               <label class="pop_label_2">请选择示范分类：</label>
               <span class="pop_element">
               	<select class="select_pop" id="sel" name="recordExampId">
               		<option value="">-请选择-</option>
               		<c:forEach items="${oplist}" var="op">
               			<option value="${op.optionlistId}">${op.optionName}</option>
               		</c:forEach>
               	</select>
               </span>
            </p>
            <p class='bomp-p bomp-p-width bomp-error' style="margin:0; position:relative;">
		        <label class='lab_a fl_l'>点评内容：</label>
		        <label class="bomp-sce-area">
		            <textarea class='area_a w_b fl_l' style="height:125px;" id="sftext" name="revComment"
		                      maxlength="200">${review.revComment}</textarea>
		        </label>
		        <span class="textarea_tip" >最多可输入150个汉字。</span>
		    </p>
         
          <div class="fl_l" style="width:100%;margin-top:15px;">
          <div class="fl_l" style="margin-left:308px;">
          <c:choose>
          	<c:when test="${!empty review}">
          		<input type="hidden" name="retaId" value="${review.retaId}"/>
          		<input type="hidden" name="callId" value="${callId}"/>
          		<input type="hidden" name="reviewId" value="${review.reviewId}"/>
          		<a href="###" id="edit"  class="com-btna bomp-btna sure-btn com-btna-sty fl_l">添加</a>
          	</c:when>
          	<c:otherwise>
          		<input type="hidden" name="retaId" value="${retaId}"/>
          		<input type="hidden" name="recordUrl" value="${recordUrl}"/>
          		<input type="hidden" name="timeLengh" value="${timeLength}"/>
          		<input type="hidden" name="ownerAcc" value="${inputName}"/> 
          		<input type="hidden" name="inputDate" value="${inputDate}"/>  
          		<!-- 接口中使用 -->  
          		<input type="hidden" name="attachParam" value="${attachParam}"/>     	
          		<input type="hidden" name="callCode" value="${recordCode}"/>	
          		<input type="hidden" name="callD" value="${callId}"/>
          		<a href="###" id="add"  class="com-btna bomp-btna sure-btn com-btna-sty fl_l">添加</a>
          	</c:otherwise>
          </c:choose>
          
          	<a href="javascript:$.dialog.close();" class="pop_cancel"></a>
          </div>
          </div>
          </form>
     </div>
</div>
<!--加入示范弹出层-->
</body>
</html>