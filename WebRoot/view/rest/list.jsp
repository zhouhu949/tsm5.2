<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
  <title></title>
  <style type="text/css"> </style>
  <%@ include file="/common/include.jsp"%>
  <script src="${ctx}/static/js/view/rest/list.js${_v}" type="text/javascript"></script>
  <script type="text/javascript">
    function searchMeet(){
      var orgId = $("#orgId").val();
      var ids = $("#ids").val();
      var startDate = $("#startDate").datebox('getValue');//创建时间--开始
      var endDate = $("#endDate").datebox('getValue');//创建时间---结束
    //  alert(orgId);
      $("#data-list").datagrid('load',{orgId:orgId,ids:ids,startDate:startDate,endDate:endDate});
    }
    function query(){
      var urlstr="/query/json";
      var orgid = $("#orgid").val();
      var ids = $("#ids").val();
      if((orgid!=null&&orgid!="")||(ids!=null&&ids!="")){
        urlstr+="?";
      }
      if(orgid!=null&&orgid!=""){
        urlstr+="orgid="+orgid;
      }
      if(ids!=null&&ids!=""){
        urlstr+="&ids="+ids;
      }
      window.open(urlstr);
    }

  </script>
</head>
<body>
<table id="data-list" title="查询列表"  data-options="rownumbers:true,singleSelect:true,toolbar:'#tb',fit:true"></table>
<div id="tb" class="datagrid-toolbar" style="padding:10px;">
  &nbsp;&nbsp;
  机构名称(ID) : <input class="easyui-textbox" data-options="prompt:'请输入机构名称(ID)...'" id='orgId' name='orgId'>　
  &nbsp;通话记录(ID) : <input class="easyui-textbox" data-options="prompt:'请输入通话记录(ID)...'" id='ids' name='ids'>　
  通话时间 : <input id="startDate"  class="easyui-datebox" data-options="prompt:'选择开始时间'" style="width: 120px" /> --
  <input id="endDate"  class="easyui-datebox" data-options="prompt:'选择结束时间'" style="width: 120px" />
  <a class="easyui-linkbutton" plain="false" icon="icon-search" onclick="searchMeet()" href="javascript:void(0)">查询</a>
 </div>
</body>
</html>
