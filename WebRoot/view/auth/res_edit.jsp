<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <title></title>
    <style type="text/css"></style>
    <%@ include file="/common/include.jsp" %>
    <script type="text/javascript">
        function update() {
            var resourceId = $("#update-resourceId").val();
            var resourceString = $("#update-resourceString").val();
            var resourceName = $("#update-resourceName").val();
            var postData = {
                resourceId: resourceId,
                resourceString: resourceString,
                resourceName: resourceName
            };
            if ($("#update-form").form('validate') == false) {
                return false;
            }
            $.post(ctx + "/authres/update", postData, function (msg) {
                if (msg == 0) {
                    parent.addTabFun({
                        src: ctx + "/res/index",
                        title: "资源管理_新"
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
    <div class="panel-title">修改资源【${item.resourceName}】</div>
    <div class="panel-tool"></div>
</div>
<div style="margin: 10px;">
    <form id="update-form" class="formc" action="#">
        <input type="hidden" id="update-resourceId" value="${item.resourceId }"/>
            <table>
                <tr height="30px"></tr>
                <tr>
                    <td align="right">资源名称：</td>
                    <td><input type="text" id="update-resourceName" class="easyui-textbox"
                               data-options="required:true,missingMessage:'资源名称不能为空!',prompt:'请输入资源名称...'"
                               style="width:200px;" maxlength="200" validtype="length[1,200]"
                               invalidMessage="有效长度为1-200" value="${item.resourceName}"/>
                    </td>
                </tr>
                <tr height="8px"></tr>
                <tr valign="top">
                    <td align="right">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</td>
                    <td>
                        <input id="update-resourceString" class="easyui-textbox"
                               data-options="multiline:false,prompt:'请输入地址...'"
                               style="width: 450px;" maxlength="200" validtype="length[1,200]"
                               invalidMessage="有效长度为1-200" value="${item.resourceString}">
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
