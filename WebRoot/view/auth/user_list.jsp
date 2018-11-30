<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
  <title></title>
  <style type="text/css"> </style>
  <%@ include file="/common/include.jsp"%>
  <script type="text/javascript" src="${ctx}/static/js/view/auth/user.js${_v}" ></script>
  <script type="text/javascript">
    var ctx="<%=request.getContextPath()%>";
    function searchMeet(){
      var name = $("#name").val();
      var loginname = $("#loginname").val();
    //  var startDate = $("#startDate").datebox('getValue');//创建时间--开始
    //  var endDate = $("#endDate").datebox('getValue');//创建时间---结束
      //  alert(orgId);
      $("#data-list").datagrid('load',{loginname:loginname,name:name});
    }
    function getSelect(){
      var checkedItems = $('#data-list').datagrid('getChecked');
      var names = [];
      $.each(checkedItems, function(index, item){
        names.push(item.orgId);
      });
      alert(names.join(","));
    }
  </script>
</head>
<body>
<table id="data-list" title="查询列表"  data-options="rownumbers:true,singleSelect:true,toolbar:'#tb',fit:true"></table>
<div id="tb" class="datagrid-toolbar" style="padding:10px;">
  &nbsp;&nbsp;
  用户名 : <input class="easyui-textbox" data-options="prompt:'请输入用户名...'" id='name' name='name'>　
  &nbsp;登录名称 : <input class="easyui-textbox" data-options="prompt:'请输入登录名称...'" id='loginname' name='loginname'>　
<%--  服务到期时间 : <input id="startDate"  class="easyui-datebox" data-options="prompt:'选择开始时间'" style="width: 120px" /> --
  <input id="endDate"  class="easyui-datebox" data-options="prompt:'选择结束时间'" style="width: 120px" />--%>
  <a class="easyui-linkbutton" plain="false" icon="icon-search" onclick="searchMeet()" href="javascript:void(0)">查询</a>
  <a class="easyui-linkbutton" plain="false" icon="icon-search" onclick="getSelect()" href="javascript:void(0)">取值</a>
</div>
</body>
</html>
