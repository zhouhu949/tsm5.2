<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%-- <link type="text/css" href="${ctx}/css/unit/css/dxgj_admin_css.css${_v}" rel="stylesheet"/> --%>
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.2.min.js${_v}"></script>
<script type="text/javascript" src="${ctx}/js/jquery/jquery.form.js${_v}"></script>
<!-- JQuery弹窗插件 -->
<%-- <script src="${ctx}/js/artDialog/jquery.artDialog.js${_v}&skin=blue" type="text/javascript"></script>
<script src="${ctx}/js/artDialog/plugins/iframeTools.js${_v} " type="text/javascript"></script> --%>
<script type="text/javascript" src="${ctx}/js/common/topmenu.js${_v}"></script>
<script>
var base = "";
var code = "";
var pid = "";
var errMsg1 = "数据重复";
var reeMsg2 = "不能为空";
var isSubmit = true;
	$(function(){
		base = $("#base").val();
		code = $("#code").val();
		pid = $("#pid").val();
		$('#addhtml').click(function(){
			var len = $("tr[name='tr']").length;
			$("#f"+len).after(
					'<tr name="tr" id="f'+(len+1)+'">'+
	                   '<td>'+
	                   		'<input type="text" name="options['+len+'].optionName" id="p'+(len+1)+'" ck="ck_1" maxlength="15" class="text_search" />'+
	                   		'<span class="err_text_1" id="err_p'+(len+1)+'"></span>'+
	                   '</td>'+
	                   '<td>'+
	                   		'<input type="text" name="options['+len+'].sort" id="m'+(len+1)+'" ck="ck_2" maxlength="3" onkeyup="this.value=this.value.replace(\/\\D\/g, \'\');" onblur="this.value=this.value.replace(\/\\D\/g, \'\');" class="text_sort_1" />'+
	                   		'<span class="err_text_1" id="err_m'+(len+1)+'"></span>'+
	                   '</td>'+
	                   '<td>'+
		                   	'<a href="javascript:del('+(len+1)+');">'+
	      						'<img src="'+base+'/css/unit/images/ooopic_1395990962q.png" />'+
	      					'</a>'+
	                   '</td>'+
	                   '<input type="hidden" name="options['+len+'].itemCode" value="'+code+'"/>'+
	                   '<input type="hidden" name="options['+len+'].pid" value="'+pid+'"/>'+
	                '</tr>'
			);
		});
		//动态绑定
		$("[ck^='ck_']").live('blur', function(){
			var id = $(this).attr('id');
			var chkType = $(this).attr('ck').split('_')[1];
			checkVal(id,chkType);
		});
		//提交
		$("#btn_save").click(function(){
			if(isSubmit && checkAll()){
				isSubmit = false;
				$("#myform").ajaxSubmit({
					url : base+'/option/addSubOption.do',
					type : 'post',
					error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
					success : function(data) {
						if(data != 0){
							// 提示失败
							dialogMsg(-1);
						}else{
						   // 默认刷新主页面
							var p_iframe=window.top.$("iframe[src$='propertyset.do']")[0];
							if(p_iframe!=undefined){
								p_iframe.contentWindow.document.forms[0].submit();
							}
						}
					}
				});
			}
		});
	});
	function checkAll(){
		var r = 0;
		$("[ck^='ck_']").each(function(){
			var id = $(this).attr('id');
			var chkType = $(this).attr('ck').split('_')[1];
			var i = checkVal(id,chkType);
			if(!i){
				r++;
			}
		});
		if(r == 0){
			return true;
		}
		return false;
	}
	
	function checkVal(id,chkType){
		var val = $.trim($('#'+id).val());
		var flg = true;
		//验证非空
		if(val == ''){
			return setMsg(id,reeMsg2,false);
		}
		//验证当前表单中唯一
		if(chkType == '1'){
			$('[ck^="ck_1"]').each(function(i){
				var tmpv = $.trim($(this).val());
				var tmpid = $(this).attr('id');
				if(tmpid != id && tmpv != '' && tmpv == val){
					flg = false;
					setMsg(id,errMsg1,false);
					return false;
				}
			});
		}
		//验证当前表单中唯一
		if(chkType == '2'){
			$('[ck^="ck_2"]').each(function(){
				var tmpv = $.trim($(this).val());
				var tmpid = $(this).attr('id');
				if(tmpid != id && tmpv == val){
					flg = false;
					setMsg(id,errMsg1,false);
					return false;
				}
			});
		}
		if(!flg){
			return false;
		}
		return setMsg(id,"",true);
	}
	
	//设置错误消息
	function setMsg(id,msg,bool){
		$("#err_"+id).text(msg);
			return bool;
	}
	
	//删除行
	function del(id){
		$("#f"+id).remove();
	}
</script>
<!-- ajax session time out 处理 -->
<%@ include file="/common/ajaxSessionTimeout.jsp" %>
</head>
<body>
<input type="hidden" name="base" id="base" value="${ctx}"/>
<input type="hidden" name="code" id="code" value="${code}"/>
<input type="hidden" name="pid" id="pid" value="${pid}"/>
	<form id="myform" name="myform">
         <div class="commu_fail">
         	<div class="scroll_fali">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="fail_table">
                 <thead>
                     <tr>
                       <th width="50%">子项名称</th>
                       <th width="40%">排序值</th>
                       <th width="10%"><a href="###" id="addhtml"><img src="${ctx}/css/unit/images/ooopic_1395990965.png" /></a></th>
                     </tr>
                  </thead>
                  <c:choose>
                  	<c:when test="${!empty entitys}">
                  		<c:forEach items="${entitys}" var="entity" varStatus="v">
	                  	<tr name="tr" id="f${v.count}">
		                   <td>
		                   		<input type="text" name="options[${v.index}].optionName" id="p${v.count}" ck="ck_1" value="${entity.optionName}" maxlength="10" class="text_search" />
		                   		<span class="err_text_1" id="err_p${v.count}"></span>
		                   </td>
		                   <td>
		                   		<input type="text" name="options[${v.index}].sort" id="m${v.count}" ck="ck_2" value="${entity.sort}" maxlength="3" class="text_sort_1" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"/>
		                   		<span class="err_text_1" id="err_m${v.count}"></span>
		                   </td>
		                   <td>
		                   		<c:choose>
		                   			<c:when test="${v.index eq '0'}">
		                   				&nbsp;
		                   			</c:when>
		                   			<c:otherwise>
		                   				<a href="javascript:del(${v.count});">
		                   					<img src="${ctx}/css/unit/images/ooopic_1395990962q.png" />
		                   				</a>
		                   			</c:otherwise>
		                   		</c:choose>
		                   </td>
		                   <input type="hidden" name="options[${v.index}].itemCode" value="${code}"/>
		                   <input type="hidden" name="options[${v.index}].pid" value="${pid}"/>
		                </tr>
	                  </c:forEach>
                  	</c:when>
                  	<c:otherwise>
                  		<tr name="tr" id="f1">
		                   <td>
		                   		<input type="text" name="options[0].optionName" id="p1" ck="ck_1" maxlength="10" class="text_search" />
		                   		<span class="err_text_1" id="err_p1"></span>
		                   </td>
		                   <td>
		                   		<input type="text" name="options[0].sort" id="m1" ck="ck_2" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="text_sort_1" />
		                   		<span class="err_text_1" id="err_m1"></span>
		                   </td>
		                   <td>&nbsp;</td>
		                   <input type="hidden" name="options[0].itemCode" value="${code}"/>
		                   <input type="hidden" name="options[0].pid" value="${pid}"/>
		                </tr>
                  	</c:otherwise>
                  </c:choose>
             </table>
             </div>
             <div class="btn_pop_layer padding_tl45">
               <a class="pop_submit_1" id="btn_save" href="###"></a>
               <a class="pop_cancel_1" href="javascript:$.dialog.close();"></a>
             </div>
         </div>
         <input type="hidden" name="code" value="${code}"/>
	</form>
</body>
</html>