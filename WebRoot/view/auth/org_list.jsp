<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
  <title></title>
  <style type="text/css"> </style>
  <%@ include file="/common/include.jsp"%>
  <script type="text/javascript" src="${ctx}/static/js/view/auth/org.js${_v}"></script>
  <script type="text/javascript">
    function searchMeet(){
      var linkName = $("#linkName").val();
      var orgName = $("#orgName").val();
      $("#data-list").datagrid('load',{orgName:orgName,linkName:linkName});
    }
    function getSelectOrg(){
      var checkedItems = $('#data-list').datagrid('getChecked');
     var names = [];
      $.each(checkedItems, function(index, item){
              names.push(item.orgId);
          });
        return names.join(",");
     // alert(names.join(","));
    }

    function getDeleteAll(){
        $.messager.confirm("提示", "确定要删除吗？", function (r) {
            if (r) {
                $.ajax({
                    type: 'POST',
                    url: ctx + "/org/delete",
                    data: {orgid:getSelectOrg()},
                    dataType: 'text',
                    success: function (msg) {
                        if (msg == "0") {
                            $('#data-list').datagrid('reload');
                        } else if (msg == "-1") {
                            $.messager.alert('提示', '登录已过期，请重新登录!', 'warning', function () {
                                window.location.href =  "/login";
                            });
                        } else if ($.trim(msg) == 'unauthorized') {
                            $.messager.alert('提示', '您没有权限!', 'warning');
                        } else {
                            $.messager.alert('提示', '信息删除失败', 'error');
                        }
                    }
                });
            }
        });

    }
  </script>
</head>
<body>
<table id="data-list" title="查询列表"  data-options="rownumbers:true,singleSelect:true,toolbar:'#tb',fit:true"></table>
<div id="tb" class="datagrid-toolbar" style="padding:10px;">
  &nbsp;&nbsp;
  单位名称 : <input class="easyui-textbox" data-options="prompt:'请输入单位名称...'" id='orgName' name='orgName'>　
  &nbsp;联系人 : <input class="easyui-textbox" data-options="prompt:'请输入联系人...'" id='linkName' name='linkName'>　
  <a class="easyui-linkbutton" plain="false" icon="icon-search" onclick="searchMeet()" href="javascript:void(0)">查询</a>
  <a class="easyui-linkbutton" plain="false" icon="icon-search" onclick="getSelectOrg()" href="javascript:void(0)">取值</a>
    <a class="easyui-linkbutton"  icon="icon-search" onclick="getDeleteAll()" href="javascript:void(0)">批量删除</a>
</div>
</body>
</html>
