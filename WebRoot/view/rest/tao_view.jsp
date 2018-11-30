<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <title>测试多对象数据提交</title>
    <%@ include file="/common/include.jsp" %>
    <script type="text/javascript">
        function searchMeet(){
            var urlstr="/dp/test";
            var resItem_name = $("#resItem_name").val();
            var crItem_name = $("#crItem_name").val();
            if((resItem_name!=null&&resItem_name!="")||(crItem_name!=null&&crItem_name!="")){
                urlstr+="?";
            }
            if(resItem_name!=null&&resItem_name!=""){
                urlstr+="resItem.sysType="+resItem_name;
            }
            if(crItem_name!=null&&crItem_name!=""){
                urlstr+="&crItem.sysType="+crItem_name;
            }
            window.open(urlstr);
        }
    </script>
</head>
<body>
<form:form modelAttribute="resItem,crItem" action="${ctx}/dp/test" method="post" id="enterForm">
    <label for="resItem_name">资源名称:</label>
    <input type="text" name="resItem.name" id="resItem_name" value="222222"  />
    <label for="crItem_name">通话名称:</label>
    <input type="text" name="crItem.name" id="crItem_name" value="111111"  />

    <a class="easyui-linkbutton"  icon="icon-search" onclick="$('form').submit()" href="javascript:void(0)">提交</a>
    <a class="easyui-linkbutton"  icon="icon-search" onclick="searchMeet()" href="javascript:void(0)">查询</a>
 </form:form>

<shiro:hasRole name="administrator">
     用户[<shiro:principal/>]拥有角色administrator<br/>
</shiro:hasRole>

<shiro:hasPermission name="create">
   用户[<shiro:principal/>]拥有权限create<br/>
  </shiro:hasPermission>

<shiro:lacksPermission name="delete">
      用户[<shiro:principal/>]没有权限delete<br/>
    </shiro:lacksPermission>

用户名：<shiro:principal property="name"/>
账号：<shiro:principal property="account"/>

</body>
</html>
