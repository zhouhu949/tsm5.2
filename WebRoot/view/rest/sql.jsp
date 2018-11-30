<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <title></title>
    <style type="text/css"></style>
    <%@ include file="/common/include.jsp" %>
    <script src="${ctx}/static/js/view/rest/sql.js${_v}" type="text/javascript"></script>
    <script type="text/javascript">
        function searchMeet() {
            var name = $("#name").val();
            var time = $("#time").val();
            var startDate = $("#startDate").datebox('getValue');//创建时间--开始
            var endDate = $("#endDate").datebox('getValue');//创建时间---结束
            //  alert(orgId);
            $("#data-list").datagrid('load', {name: name, time: time, startDate: startDate, endDate: endDate});
        }
        function setState(state) {
            var urlstr = "";
            if (state == 1) {
                urlstr = "/sql/set?startSql=1";
            } else if (state == 2) {
                urlstr = "/sql/set?startSql=0";
            } else if (state == 3) {
                urlstr = "/sql/clear";
            }
            if (urlstr != "") {
                $.ajax({
                    type: "POST",
                    dataType: 'JSON',
                    url: ctx + urlstr,
                    success: function (data) {
                        //alert(data);
                        if(state==3){
                            searchMeet();
                        }
                    }
                });
            }

        }
        function query() {
            var urlstr = ctx + "/sql/test";
            window.open(urlstr);
        }
    </script>
</head>
<body>
<table id="data-list" title="查询列表" data-options="rownumbers:true,singleSelect:true,toolbar:'#tb',fit:true"></table>
<div id="tb" class="datagrid-toolbar" style="padding:10px;">
    &nbsp;&nbsp;
    方法名称 : <input class="easyui-textbox" data-options="prompt:'请输入方法名称...'" id='name' name='name'>　
    &nbsp;执行时长 : <input class="easyui-textbox" data-options="prompt:'请输入执行时长...'" id='time' name='time'>　
    执行时间 : <input id="startDate" class="easyui-datebox" data-options="prompt:'选择执行开始时间'" style="width: 120px"/> --
    <input id="endDate" class="easyui-datebox" data-options="prompt:'选择执行结束时间'" style="width: 120px"/>
    <a class="easyui-linkbutton" plain="false" icon="icon-search" onclick="searchMeet()"
       href="javascript:void(0)">查询</a>
    <a class="easyui-linkbutton" plain="false" icon="icon-search" onclick="setState(1)" href="javascript:void(0)">开启</a>
    <a class="easyui-linkbutton" plain="false" icon="icon-search" onclick="setState(2)" href="javascript:void(0)">关闭</a>
    <a class="easyui-linkbutton" plain="false" icon="icon-search" onclick="setState(3)" href="javascript:void(0)">重置</a>
    <a class="easyui-linkbutton" plain="false" icon="icon-search" onclick="query()" href="javascript:void(0)">详情</a>
</div>
</body>
</html>
