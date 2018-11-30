<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <title></title>
    <style type="text/css"></style>
    <%@ include file="/common/include.jsp" %>
    <script type="text/javascript">
        function update() {
            var userId = $("#update-userId").val();
            var userAccount = $("#update-userAccount").val();
            var userName = $("#update-userName").val();
            var postData = {
                userId: userId,
                userAccount: userAccount,
                userName: userName
            };
            if ($("#update-form").form('validate') == false) {
                return false;
            }
            $.post(ctx + "/user/update", postData, function (msg) {
                if (msg == 0) {
                    parent.addTabFun({
                        src: ctx + "/user/index",
                        title: "用户管理"
                    });
                    parent.closeTab("修改资源");
                } else if (msg == -1) {
                    $.messager.alert('提示', '提交参数不对!');
                } else if (msg == -2) {
                    $.messager.alert('提示', '该资源已存在!');
                } else {
                    $.messager.alert('提示', '修改失败，请稍候再试!');
                }
            }, "text");

        }
    </script>
</head>
<body>
<div class="panel-header">
    <div class="panel-title">修改用户【${item.userName}】</div>
    <div class="panel-tool"></div>
</div>
<div style="margin: 10px;">
    <form id="update-form" class="formc" action="#">
        <input type="hidden" id="update-userId" value="${item.userId }"/>
        <table>
            <table>
                <tr height="30px"></tr>
                <tr>
                    <td align="right">用户名称：</td>
                    <td><input type="text" id="update-userName" class="easyui-textbox"
                               data-options="required:true,missingMessage:'用户名称不能为空!',prompt:'请输入用户名称...'"
                               style="width:200px;" maxlength="200" validtype="length[1,200]"
                               invalidMessage="有效长度为1-200" value="${item.userName}"/>
                    </td>
                </tr>
                <tr height="8px"></tr>
                <tr valign="top">
                    <td align="right">用户账号：</td>
                    <td>
                        <input id="update-userAccount" class="easyui-textbox"
                               data-options="multiline:false,prompt:'请输入用户账号...'"
                               style="width: 450px;" maxlength="200" validtype="length[1,200]"
                               invalidMessage="有效长度为1-200" value="${item.userAccount}">
                    </td>
                <tr height="8px"></tr>
            </table>
            <div style="border-top: 1px solid #000000; display: block; height: 1px; width: 600px"></div>
<br>
            <div class="submit">
                <div colspan="2">
                    <input style="width: 70px;" class="btn btn-primary btn-sm" type="button" value="提交"
                           onclick="update()"/>
                    &nbsp;<input style="width: 70px;" class="btn btn-default btn-sm" type="reset" value="重置"></div>
            </div>
    </form>
</div>
</div>
</body>
</html>
