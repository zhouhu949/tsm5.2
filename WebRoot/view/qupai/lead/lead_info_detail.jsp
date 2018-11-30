<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<html>
<head>
    <title>新增放款</title>
    <%@ page import="com.qftx.base.shiro.ShiroUtil" %>
   
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/style.css${_v}">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/default.css${_v}"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/button.css${_v}"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/skin/default/color.css${_v}">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/js/form/skins/all.css${_v}">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/leadAddEdit.css${_v}"/>
    <script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js${_v}" dialog-theme="default"></script><!--可移动弹框插件-->
    <script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js${_v}"></script><!--可移动弹框插件公共js-->
    <script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery.form.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/my97datepicker/WdatePicker.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/form.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/time/moment.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/common.js${_v}"></script>
    <% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
    <script>
    	console.log(${fieldSetsJson});
    </script> 
    <script type="text/javascript" src="${ctx }/static/js/view/qupai/add-leads.js"></script>
</head>
<body>
    <shiro:hasPermission name="mySignCust_addOrder">
        <input type="hidden" id="mySignCust_addOrder" value="1"/>
    </shiro:hasPermission>
    <form id="leadOperateForm" action="${ctx }/credit/lead/addLead" method="post">
    	<input type="hidden" id="idReplaceWord" value=${idReplaceWord }>
         <div class="hyx-nc">
        <c:if test="${ !empty fieldSets}">
                <c:forEach items="${fieldSets}" var="field" varStatus="vs">
                    <c:choose>
                    <c:when test="${field.dataType == 5 || field.dataType == 7 || field.dataType == 8 }">
                         <p class='bomp-p'>
                             <label class='lab_a fl_l'>
                                 <c:choose>
                                     <c:when test="${field.isRequired eq 1 }">
                                         <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                     </c:when>
                                 </c:choose>
                                 ${field.fieldName}：
                             </label>
                             <span class="detail-info" data-moneyformat="true" data-fieldcode="${field.fieldCode }" title='${field.showValue }'>${field.showValue }</span>
                         </p>
                    </c:when>
                    <c:when test="${field.dataType == 10 }">
                         <p class='bomp-p loanVoucherBox'>
                             <label class='lab_a fl_l'>
                                 <c:choose>
                                     <c:when test="${field.isRequired eq 1 }">
                                         <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                     </c:when>
                                 </c:choose>
                                 ${field.fieldName}：
                             </label>
                             <c:if test="${!empty field.showValue }">
                                  <span class="file_box fl_l">
                                       <a href="javascript:;" class="chooseFiles">
                                           <img src="${field.showValue }"/>
                                      </a>
                                  </span>
                              </c:if>
                         </p>
                    </c:when>
                    <c:when test="${field.enable eq 1 && field.fieldCode!='batch'}">
                    	<p class='bomp-p'>
                             <label class='lab_a fl_l'>
                                 <c:choose>
                                     <c:when test="${field.isRequired eq 1 }">
                                         <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>                                                   
                                     </c:when>
                                 </c:choose>
                                 ${field.fieldName}：
                             </label>
                             <span class="detail-info" data-fieldcode="${field.fieldCode }" title='${field.showValue }'>${field.showValue }</span>
                         </p>   
                    </c:when>
                    </c:choose>
                </c:forEach>
                
            </c:if>
        </div>
        <c:if test="${reviewAuth != '3'}">
		    <div class="progress">
		        <c:if test="${list.size() > 0}">
                <span class="title">审核流程：</span>
                </c:if>
		        <c:forEach var="bean" items="${list}" varStatus="i">
	                <div class="box">
	                    <div class="round">
                            <span class="step">${i.index + 1}</span>
                            <span class="status">
                                <c:choose>
                                    <c:when test="${bean.reviewResult == 0}">未完成</c:when>
                                    <c:when test="${bean.reviewResult == 1}">进行中</c:when>
                                    <c:when test="${bean.reviewResult == 2}">已完成</c:when>
                                    <c:when test="${bean.reviewResult == 3}">驳回</c:when>
                                </c:choose>
                            </span>
                            <span class="account">审核人：${bean.reviewAcc}</span>
                            <span class="remarks" title=${bean.reviewRemark}>审核备注：${bean.reviewRemark}</span>

                        </div>
	                    <c:if test="${i.index > 0}">
                            <div class="line">
                                <c:if test="${list[i.index-1].reviewResult == 2}">通过</c:if>&nbsp;
                                <c:if test="${list[i.index-1].reviewResult == 3}">驳回</c:if>&nbsp;
                            </div>
                        </c:if>
	                </div>
	            </c:forEach>
	        </div>
		</c:if>
    </form>
</body>
</html>
