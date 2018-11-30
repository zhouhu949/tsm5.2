<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/salesmanage.css"><!--主要样式-->
<script type="text/javascript" src="${ctx}/static/js/base64.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/tsm/option/salesmanageIdialog.js${_v}"></script>
<input type="hidden" id="optionValue" value="${optionValue } ">
  <form id="sales_idialog" method="post" class="salesmanage-idialog-form">
  <div class="sales-idialog-overcont">
    <c:choose>
			 	<c:when test="${!empty dicProcessList}">
			 		<c:forEach items="${dicProcessList}" var="entity" varStatus="v">
			 				 <p class="tit_b">
						      <label class="lab sales-idialog-labels">${entity.optionName }</label>
						      <input type="text" value="${empty entity.optionValue ? optionValue : entity.optionValue}" optionId="${entity.optionId}"  optionName="${entity.optionName }"  optionValue="${empty entity.optionValue ? optionValue : entity.optionValue}" maxlength="3" class="ipt" />
						      <label class="lab">天未跟进，将自动放入公海客户池中。</label>
						    </p>
			 		</c:forEach>
			 	</c:when>
	 </c:choose>
	 </div>
	 <div class="bomb-btn btn-box-in-salesidialog">
			<label class="bomb-btn-cen">
				<a class="com-btna bomp-btna com-btna-sty fl_l salesidialog_surebtn"   href="javascript:;"><label>确定</label></a>
				<a class="com-btna bomp-btna fl_l" id="close02"    href="javascript:;"><label>取消</label></a>
			</label>
		</div>
  </form>