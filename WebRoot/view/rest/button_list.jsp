<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <title>按钮权限测试</title>
    <%@ include file="/common/include.jsp" %>
    <script type="text/javascript">

        function clsAuth() {
            var name = $("#name").val();
            var postData = {
                name: name
            };
            $.post(ctx + "/button/cls", postData, function (msg) {
                $.messager.alert('提示', msg.account);
                }, "json");

        }
        function clearAuth() {
            $.get(ctx + "/button/clear",  function (msg) {
                $.messager.alert('提示', msg);
            }, "text");

        }
        function clearAllAuth() {
            $.get(ctx + "/button/clsall",  function (msg) {
                $.messager.alert('提示', msg);
            }, "json");

        }
        function jsonpTest() {
            $.ajax({
                type: "get",
                async: false,
                url: ctx + "/jsonp/login?name=jsonpCallBack",
                dataType: "jsonp",
                jsonp: "callback",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(一般默认为:callback)
                jsonpCallback:"jsonpCallBack",//自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名，也可以写"?"，jQuery会自动为你处理数据
                success: function(json){
                    $.messager.alert('登录信息','账户名： ' + json.account + '，名称： ' + json.name + '');
                },
                error: function(){
                    $.messager.alert('提示','fail');
                }
            });
        }

    </script>
</head>
<body>
<form:form modelAttribute="resItem,crItem" action="${ctx}/dp/test" method="post" id="enterForm">
    <label for="name">用户名称:</label>
    <input type="text" name="name" id="name" value="<shiro:principal/>"  />
    <a class="easyui-linkbutton"  icon="icon-search" onclick="clsAuth()" href="javascript:void(0)">清除指定用户权限</a>
    <a class="easyui-linkbutton"  icon="icon-search" onclick="clearAuth()" href="javascript:void(0)">清除当前权限</a>
    <a class="easyui-linkbutton"  icon="icon-search" onclick="clearAllAuth()" href="javascript:void(0)">清除所有</a>
    <a class="easyui-linkbutton"  icon="icon-search" onclick="jsonpTest()" href="javascript:void(0)">JSONP</a>
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
userId：<shiro:principal property="id"/>
</body>
</html>
