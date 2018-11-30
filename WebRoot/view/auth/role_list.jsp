<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
  <title></title>
  <style type="text/css"> </style>
  <%@ include file="/common/include.jsp"%>
  <script type="text/javascript" src="${ctx}/static/js/view/auth/role.js${_v}" ></script>
  <script type="text/javascript">
    function searchMeet(){
      var resourceName = $("#resourceName").val();
      var resourceString = $("#resourceString").val();
      $("#data-list").datagrid('load',{roleDesc:resourceString,roleName:resourceName});
    }
  </script>
</head>
<body>
<table id="data-list" title="查询列表"  data-options="rownumbers:true,singleSelect:true,toolbar:'#tb',fit:true"></table>
<div id="tb" class="datagrid-toolbar" style="padding:10px;">
  &nbsp;&nbsp;
  名称 : <input class="easyui-textbox" data-options="prompt:'请输入名称...'" id='resourceName' name=resourceName'>　
  &nbsp;地址 : <input class="easyui-textbox" data-options="prompt:'请输入地址...'" id='resourceString' name='resourceString'>　
  <a class="easyui-linkbutton" plain="false" icon="icon-search" onclick="searchMeet()" href="javascript:void(0)">查询</a>
</div>
</body>
</html>
