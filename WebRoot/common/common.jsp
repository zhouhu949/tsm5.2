<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<meta http-equiv="X-UA-Compatible" content="IE=edge charset=utf-8"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% pageContext.setAttribute("ctx", request.getContextPath());%>
<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico" type="image/x-icon" />
<c:set var="_v">?v=<%=System.currentTimeMillis()%></c:set>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/static/js/common/hack_common.js${_v}"></script>
<script type="text/javascript">
  var ctx = "${ctx}";
  try {
        String.prototype.endWith = function (s) {
            if (s == null || s == "" || this.length == 0 || s.length > this.length)
                return false;
            if (this.substring(this.length - s.length) == s)
                return true;
            else
                return false;
            return true;
        }
        if (window.location.href.indexOf("main")!=-1||window.location.href.endWith("logout")|| window.location.href.indexOf("login")!=-1) {
            document.title = "慧营销V5.1 Web1";
            //alert("1");
        }else{
           // alert(window.location.href);
        }
        if (window.location.host.endWith("test.com")) {
            document.domain = "test.com";
        } else{
            document.domain = "qftx.net";
        }
        //  alert(window.location.host);
    } catch (e) {
        //
    }
</script>