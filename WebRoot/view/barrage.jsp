<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>慧营销V5.0 Web1</title>
    <%@ include file="/common/common.jsp"%>
    <!--配置菜单Color-->
    <!--配置Tab菜单-->
    <link href="${ctx}/static/js/barrage/barrage.bundle.css${_v}" rel="stylesheet" type="text/css" />
    <% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
    <script src="${ctx}/static/js/barrage/barrage.bundle.js${_v}" charset="utf-8"></script>

</head>
<body style="overflow:hidden;">
   <div id="container"></div>
   <script>
       window.visiableView=true
       function restore(){
           var event = document.createEvent('HTMLEvents');
           event.initEvent("barrage", true, true);
           event.eventType = "message";
           document.dispatchEvent(event);
       }

       window.showBarrage=function(content){
           var event = document.createEvent('HTMLEvents');
           event.initEvent("onreceive", true, true);
           event.eventType = "message";
           event.data=content;
           document.dispatchEvent(event);
       }

       window.congratCardScreen=function(content){
           var ele=document.createElement("div");
           ele.innerHTML=content;
           document.body.appendChild(ele);

       }

       function destory(){
           document.body.removeChild(document.querySelector(".app"))

       }

       function render(){
           renderApp()
       }
       render()
       onVisibilityChanged=function(){
           window.visiableView=!window.visiableView;
       }
       window.costWin=function(){
          // alert("cost")
           external.OnCallBackMain("mainCostWin","");
       }

       window.congratCardScreen=function(content){
           /*
           var ele=document.createElement("div");
           ele.innerHTML=content;
           document.body.appendChild(ele);
           */
           external.OnCallBackMain("mainCardScreen",content);
       }


       //document.addEventListener("webkitvisibilitychange", onVisibilityChanged, false);
   </script>
</body>
</html>
