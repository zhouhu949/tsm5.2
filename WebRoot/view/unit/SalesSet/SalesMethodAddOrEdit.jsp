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
<script type="text/javascript" src="${ctx}/js/common/common_checkII.js${_v}"></script>
<script type="text/javascript" src="${ctx}/js/common/topmenu.js${_v}"></script>
<script type="text/javascript" src="${ctx}/js/unit/recordSet/recordCaseAddOrEdit.js${_v}"></script>
<!-- ajax session time out 处理 -->
<%@ include file="/common/ajaxSessionTimeout.jsp" %>
</head>
  <body>
     <input type="hidden" name="base" id="base" value="${ctx}"/>
<form id="myform" name="myform">
     <div class="pop_layer cash_admin">
          <div class="pop_layer_cont padding_10">
          	<c:choose>
          		<c:when test="${param.itemCode == 'SALES_10005' || option.itemCode == 'SALES_10005'}">
          			<p class="overflow_hidden">
	                    <label class="pop_label_admin">产品名称：</label>
	                    <span class="pop_element_admin"><input type="text" checkProp="chk_1_102" id="optionName" name="option.optionName" maxlength="40" value="${option.optionName}" class="text_pop_admin" /></span>
	                 	<span id="errmsg_optionName" class="must"></span>
	                 </p>
	                 <p class="overflow_hidden">
	                    <label class="pop_label_admin">排序值：</label>
	                    <span class="pop_element_admin"><input type="text" checkProp="chk_1_11" id="sort" name="option.sort" maxlength="3" value="${option.sort}" class="cash_text" /></span>
	                 	<span id="errmsg_sort" class="must"></span>
	                 </p>
	                 <p class="overflow_hidden">
	                    <label class="pop_label_admin">计量单位：</label>
	                    <span class="pop_element_admin"><input type="text" checkProp="chk_1_1" id="units" name="option.units" maxlength="10" value="${option.units}" class="cash_text" /></span>
	                 	<span id="errmsg_units" class="must"></span>
	                 </p>
          		</c:when>
          		<c:otherwise>
          			<p class="overflow_hidden">
	                    <label class="pop_label_admin">数据项名称：</label>
	                    <span class="pop_element_admin">
	                    <c:if test="${param.itemCode == 'SALES_10006' ||option.itemCode eq 'SALES_10006'}">
	                    <input type="text" id="optionName" name="option.optionName" value="${option.optionName}" class="cash_text_two" ${option.itemCode eq 'SALES_10006'?'disabled':''}/></span>
	                 	</c:if>
	                 	<c:if test="${param.itemCode == 'SALES_10001' ||option.itemCode eq 'SALES_10001'}">
	                    <input type="text" checkProp="chk_1_103" id="optionName" name="option.optionName" maxlength="40" value="${option.optionName}" class="cash_text_two" /></span>
	                 	</c:if>
	                 	<c:if test="${param.itemCode == 'SALES_10002' ||option.itemCode eq 'SALES_10002'}">
	                    <input type="text" checkProp="chk_1_104" id="optionName" name="option.optionName" maxlength="40" value="${option.optionName}" class="cash_text_two" /></span>
	                 	</c:if>
	                 	<c:if test="${param.itemCode == 'SALES_10004' ||option.itemCode eq 'SALES_10004'}">
	                    <input type="text" checkProp="chk_1_105" id="optionName" name="option.optionName" maxlength="40" value="${option.optionName}" class="cash_text_two" /></span>
	                 	</c:if>
	                    <c:if test="${param.itemCode == 'SALES_10010' ||option.itemCode eq 'SALES_10010'}">
	                    <input type="text" checkProp="chk_1_106" id="optionName" name="option.optionName" maxlength="12" value="${option.optionName}" class="cash_text_two" /></span>
	                 	</c:if>
	                    <c:if test="${param.itemCode == 'SALES_10011' ||option.itemCode eq 'SALES_10011'}">
	                    <input type="text" checkProp="chk_1_107" id="optionName" name="option.optionName" maxlength="12" value="${option.optionName}" class="cash_text_two" /></span>
	                 	</c:if>
	                    <c:if test="${param.itemCode == 'SALES_10012' ||option.itemCode eq 'SALES_10012'}">
	                    <input type="text" checkProp="chk_1_108" id="optionName" name="option.optionName" maxlength="12" value="${option.optionName}" class="cash_text_two" /></span>
	                 	</c:if>
	                 	<span id="errmsg_optionName" class="must"></span>
	                 </p>
	                 <p class="overflow_hidden">
	                    <label class="pop_label_admin">排序值：</label>
	                    <span class="pop_element_admin"><input type="text" checkProp="chk_1_11" id="sort" name="option.sort" maxlength="3" value="${option.sort}" class="cash_text_two" /></span>
	                 	<span id="errmsg_sort" class="must"></span>
	                 </p>
          		</c:otherwise>
          	</c:choose>
               <div class="overflow_hidden margin_tb20px">
               		<c:choose>
               			<c:when test="${flg eq 1}">
               				<input type="hidden" name="option.optionlistId" value="${option.optionlistId}" />
               				<input type="hidden" name="option.itemCode" value="${option.itemCode}" />
               				<input type="hidden" name="target" value="1"/>
               				<a href="javascript:toSubmit();" class="pop_revise01"></a>
               			</c:when>
               			<c:otherwise>
               				<input type="hidden" name="option.itemCode" value="${param.itemCode}" />
               				<input type="hidden" name="target" value="0"/>
               				<a href="javascript:toSubmit();" class="pop_submit_2"></a>
               			</c:otherwise>
               		</c:choose>
	               	<a href="javascript:$.dialog.close();" class="pop_cancel"></a>
               </div>
          </div> 
     </div>
</form>
<input type="hidden" id="old_optionName" value="${option.optionName}"/>
  </body>
</html>