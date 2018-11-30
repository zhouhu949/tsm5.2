<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <title></title>
    <%@ include file="/common/include.jsp" %>
    <!--配置Tab菜单-->
    <script type="text/javascript" src="${ctx}/static/js/common/topmenu.js${_v}"></script>
    <script type="text/javascript">
        $(function () {
            // initMenuTree();
            addTab(ctx + '/test', '首页');
        });
        function test() {
            alert("出来就好了");
        }
    </script>

</head>
<body class="easyui-layout">
<div data-options="region:'north',border:false"
     style="height:60px;background-image:url('${ctx}/static/images/top.jpg');overflow: hidden;">
    <div style="width: 85%;height: 100%;float: left;">
        <img style="margin-left:20px;" src="${ctx}/static/images/top_logo1.png" height=60>
    </div>
    <div style="width:15%;height: 100%;float: right;line-height: 60px;text-align: center;margin-right: 0px">
        <span style="color: floralwhite;cursor: pointer;" title="<shiro:principal property="account"/>">
            <shiro:principal property="name"/></span>&nbsp;
        <a href="${ctx}/main" target="_blank"><span style="color: floralwhite">主窗口</span></a>&nbsp;
        <a href="/"><span style="color: floralwhite">首页</span></a>&nbsp;
        <a href="/logout"><span style="color: floralwhite">退出</span></a>&nbsp;
        ${ctx}
    </div>
</div>
<div data-options="region:'west',title:'系统菜单',split:true,border:false" style="width:250px;padding:5px;">
    <ul class="easyui-tree">
        <li>
            <span>系统管理</span>
            <ul>
                <li><span><a href="javascript:addTab('${ctx}/role/index','角色管理')">角色管理</a></span></li>
                <li><span><a href="javascript:addTab('${ctx}/user/index','成员管理')">成员管理</a></span></li>
                <li><span><a href="javascript:addTab('${ctx}/org/index','单位管理')">单位管理</a></span></li>
                <li><span><a href="javascript:addTab('${ctx}/authres/index_new','用户菜单')">用户菜单</a></span></li>
                <li><span><a href="javascript:addTab('${ctx}/authres/index_all_new','所有菜单')">所有菜单</a></span></li>
                <li><span><a href="javascript:addTab('${ctx}/authres/index','资源管理')">资源管理</a></span></li>
            </ul>
        </li>
        <li data-options="state:'open'">
            <span>测试管理</span>
            <ul>
                <li><a href="javascript:addTab('${ctx}/dp/list','测试多对象数据提交')">测试多对象数据提交</a></li>
                <li><a href="javascript:addTab('${ctx}/button','测试按钮权限')">测试按钮权限</a></li>
                <li><a href="javascript:addTab('${ctx}/df/error','测试异常输出')">测试异常输出</a></li>
                <li><a href="javascript:addTab('${ctx}/druid/sql.html','SQL监控')">SQL监控</a></li>
                <li><a href="javascript:addTab('${ctx}/test','统计报表')">统计报表</a></li>
                <li><a href="javascript:addTab('${ctx}/userFile','上传文件')">上传文件</a></li>
                <li><a href="javascript:addTab('${ctx}/sql','sql日志')">sql日志</a></li>
            </ul>
        </li>
    </ul>

</div>
<div data-options="region:'center',border:false" style="">
    <div id="centerTabs" class="easyui-tabs" fit="true" border="false" plain="true">
    </div>
</div>
</body>
</html>
