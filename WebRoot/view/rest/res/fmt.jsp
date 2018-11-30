<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <%@ page trimDirectiveWhitespaces="true" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
    <%
        pageContext.setAttribute("ctx",request.getContextPath());
      /*  pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());*/
    %>
    <script type="text/javascript">
        var ctx="${ctx}";
    </script>
    <c:set var="_v">?v=1</c:set>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/style.css${_v}">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/skin/default/color.css${_v}">
    <script type="text/javascript" src="${ctx}/static/js/common/tablerows.js"></script>
</head>
<body>
<%--<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
<input type="hidden" id="isState" value="${shiroUser.isState }" />--%>
<form action="${ctx}/load/test" method="GET">
    <div class="hyx-cma hyx-layout">
        <div class="hyx-cm-left hyx-layout-content">
           <div class="com-table com-mta hyx-cm-table fl_l" style="display:block;">
                <table id="t22" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
                    <thead>
                    <tr role="head" class="sty-bgcolor-b">
                        <th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
                        <th><span class="sp sty-borcolor-b">操作</span></th>
                        <th><span class="sp sty-borcolor-b">${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</span></th>
                        <th><span class="sp sty-borcolor-b">资源分组</span></th>
                        <th hidesort="true"><span class="sp sty-borcolor-b" sort="true">添加/分配</span></th>
                        <th hidesort="true"><span class="sp sty-borcolor-b" sort="true">最近联系</span></th>
                        <th hidesort="true"><span class="sp sty-borcolor-b" sort="true">计划安排</span></th>
                        <th>所有者</th>
                    </tr>
                    </thead>
                    <tbody>
               <%--     <c:choose>
                        <c:when test="${empty list }">
                            <tr>
                                <td colspan="8">
                                    <center>当前列表无数据！</center>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>--%>
                            <c:forEach items="${list}" var="res" varStatus="vs">
                                <tr <%--id="rightView_${res.resCustId }" class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }"--%>>
                                    <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal">
                                        <input type="checkbox" name="check_" name_tel="${res.name }|${res.telphone}" name_mobile="${res.name }|${res.mobilephone}" mobile="${res.mobilephone }" telPhone="${res.telphone}" id="chk_${res.resCustId }" /></div></td>
                                    <td style="width:100px;">
                                        <div class="overflow_hidden w100 link">
                                           <%--  ${shiroUser.serverDay}1
                                              ${shiroUser.serverDay}2
                                              ${shiroUser.serverDay}3
                                              ${shiroUser.serverDay}4
                                              ${shiroUser.serverDay}5--%>

                                                   <%--${res.resCustId}1
                                                   ${res.resCustId}2
                                                   ${res.resCustId}3
                                                   ${res.resCustId}4
                                                   ${res.resCustId}5
                                                   ${res.resCustId}6
                                                   ${res.resCustId}7
                                                   ${res.resCustId}8
                                                   ${res.resCustId}9
                                                   ${res.resCustId}10
--%>


                                                 <%--  ${user.serverDay}1
                                                   ${user.serverDay}2
                                                   ${user.serverDay}3
                                                   ${user.serverDay}4
                                                   ${user.serverDay}5
                                                   ${user.serverDay}6
                                                   ${user.serverDay}7
                                                   ${user.serverDay}8
                                                   ${user.serverDay}9
                                                   ${user.serverDay}10--%>



                                                   <%--${user.name}1
                                                   ${user.name}2
                                                   ${user.name}3
                                                   ${user.name}4
                                                   ${user.name}5
                                                   ${user.name}6
                                                   ${user.name}7
                                                   ${user.name}8
                                                   ${user.name}9
                                                   ${user.name}10--%>
                                                 <shiro:principal property="name"/>1
                                                   <shiro:principal property="name"/>2
                                                  <shiro:principal property="name"/>3
                                                  <shiro:principal property="name"/>4
                                                  <shiro:principal property="name"/>5
                                                  <shiro:principal property="name"/>6
                                                  <shiro:principal property="name"/>7
                                                  <shiro:principal property="name"/>8
                                                  <shiro:principal property="name"/>9
                                                  <shiro:principal property="name"/>10
                                                <%--<c:choose>
                                                   <c:when test="${shiroUser.serverDay gt 0 }">
                                                       <a href="javascript:;" id="tao_${res.resCustId }" class="icon-amoy-cust" title="开始淘"></a>
                                                       <a href="javascript:;" id="cardInfo_${res.resCustId }" custName="${res.name }" class="icon-cust-card" title="客户卡片"></a>
                                                       <a href="javascript:;" id="editInfo_${res.resCustId }" class="icon-edit" title="修改资源"></a>
                                                       <a href="javascript:;" id="sign_${res.resCustId }" class="icon-sign-cont" title="签约"></a>
                                                   </c:when>
                                                   <c:otherwise>
                                                       <a href="javascript:;" class="icon-amoy-gray" title="开始淘"></a>
                                                       <a href="javascript:;" id="cardInfo_${res.resCustId }" custName="${res.name }" class="icon-cust-card" title="客户卡片"></a>
                                                       <a href="javascript:;" id="editInfo_${res.resCustId }" class="icon-edit" title="修改资源"></a>
                                                       <a href="javascript:;" class="icon-sign-gray" title="签约"></a>
                                                   </c:otherwise>
                                               </c:choose>--%>
                                        </div>
                                    </td>
                                    <td><div class="overflow_hidden w70" title="${res.name }"><c:if test="${res.isPrecedence eq 1 }"><i class="min-key-areas"></i></c:if>${res.name }</div></td>
                                    <td><div class="overflow_hidden w70" title="${res.groupName }">${res.groupName }</div></td>

                                    <%--<c:set var="ownerStartDate"></c:set>
                                    <c:set var="actionDate"></c:set>
                                    <c:set var="planDate"></c:set>
                                    <c:set var="ownerStartDate1"></c:set>
                                    <c:set var="actionDate1"></c:set>
                                    <c:set var="planDate1"></c:set>--%>

                                   <%-- <c:set var="ownerStartDate"><fmt:formatDate value="${res.ownerStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/></c:set>
                                    <c:set var="actionDate"><fmt:formatDate value="${res.actionDate}" pattern="yyyy-MM-dd HH:mm:ss"/></c:set>
                                    <c:set var="planDate"><fmt:formatDate value="${res.planDate}" pattern="yyyy-MM-dd HH:mm:ss"/></c:set>
                                    <c:set var="ownerStartDate1"><fmt:formatDate value="${res.ownerStartDate}" pattern="yyyy-MM-dd"/></c:set>
                                    <c:set var="actionDate1"><fmt:formatDate value="${res.actionDate}" pattern="yyyy-MM-dd"/></c:set>
                                    <c:set var="planDate1"><fmt:formatDate value="${res.planDate}" pattern="yyyy-MM-dd"/></c:set>--%>

                                   <%-- <td hidevalue="${ownerStartDate}"><div class="overflow_hidden w120" title="${ownerStartDate}">${ownerStartDate1}</div></td>
                                      <td hidevalue="${actionDate}"><div class="overflow_hidden w120" title="${actionDate}">${actionDate1}</div></td>
                                      <td hidevalue="${planDate}"><div class="overflow_hidden w120" title="${actionDate}">${planDate1}</div></td>
                                                            --%>

                                   <td><fmt:formatDate value="${res.planDate }" pattern="yyyy-MM-dd"/></td>
                                    <td><fmt:formatDate value="${res.planDate }" pattern="yyyy-MM-dd"/></td>
                                    <td><fmt:formatDate value="${res.planDate }" pattern="yyyy-MM-dd"/></td>

                                 <%-- <td hidevalue="<fmt:formatDate value='${res.ownerStartDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
                                       <div class="overflow_hidden w120" title="<fmt:formatDate value='${res.ownerStartDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
                                           <fmt:formatDate value="${res.ownerStartDate }" pattern="yyyy-MM-dd"/>
                                       </div>
                                   </td>
                                    <td hidevalue="<fmt:formatDate value='${res.actionDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
                                        <div class="overflow_hidden w120" title="<fmt:formatDate value='${res.actionDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
                                            <fmt:formatDate value="${res.actionDate }" pattern="yyyy-MM-dd"/></div>
                                    </td>
                                    <td hidevalue="<fmt:formatDate value='${res.planDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
                                        <div class="overflow_hidden w120" title="<fmt:formatDate value='${res.planDate }' pattern='yyyy-MM-dd'/>">
                                            <fmt:formatDate value="${res.planDate }" pattern="yyyy-MM-dd"/></div>
                                    </td>--%>
                                    <td><div class="overflow_hidden w50" title="${res.ownerName }">${res.ownerName }</div></td>
                                </tr>
                            </c:forEach>
                       <%-- </c:otherwise>
                    </c:choose>--%>
                    </tbody>
                </table>
            </div>
            <div class="com-bot">
                <div class="com-page fl_r">${resCustInfoDto.page.pageStr }</div>
            </div>
        </div>
    </div>
</form>
</body>
</html>
