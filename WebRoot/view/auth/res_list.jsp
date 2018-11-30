<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
  <title></title>
  <style type="text/css"> </style>
  <%@ include file="/common/include.jsp"%>
  <script type="text/javascript" src="${ctx}/static/js/view/auth/res.js${_v}" ></script>
  <script type="text/javascript">
    function searchMeet(){
      var resourceName = $("#resourceName").val();
      var resourceString = $("#resourceString").val();
      $("#data-list").datagrid('load',{resourceString:resourceString,resourceName:resourceName});
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
<div id="update-win" class="easyui-window" title="修改菜单"
     data-options="collapsible:false,minimizable:false,maximizable:false,modal:true,closed:true"
     style="width:350px;height:320px;">
  <div class="m-form">
    <form id="update-form" name="" action="#" method="get">
      <fieldset style="border:0;">
        <input type="hidden" id="update-id" />
        <div class="formitm">
          <label class="lab">名　　称：</label>
          <div class="ipt">
            <input type="text" id="update-name" name="update-name" class="easyui-textbox"  data-options="required:true,missingMessage:'名称不能为空!',prompt:'请输入菜单名称...'" style="width:200px;" maxlength="20" validtype="length[1,20]" invalidMessage="有效长度为1-20"/>
          </div>
        </div>
        <div class="formitm">
          <label class="lab">上级菜单：</label>
          <div class="ipt">
            <input id="update-parentId" name="update-parentId" class="easyui-combobox" data-options="required:true,missingMessage:'上级菜单不能为空!',prompt:'请输入上级菜单名称...'" style="width:200px;"/>
          </div>
        </div>
        <div class="formitm">
          <label class="lab">　  URL：</label>
          <div class="ipt">
            <input type="text" id="update-url" name="update-url" class="easyui-textbox"  data-options="required:false,missingMessage:'URL不能为空!',prompt:'请输入URL...'" style="width:200px;" maxlength="100" validtype="length[1,100]" invalidMessage="有效长度为1-100"/>
          </div>
        </div>
        <div class="formitm">
          <label class="lab">排　　序：</label>
          <div class="ipt">
            <input type="text" id="update-sort" name="update-sort" class="easyui-numberbox"  data-options="required:true,missingMessage:'排序不能为空!',prompt:'请输入排序...'" style="width:200px;" min="0" maxlength="10" validtype="length[1,10]" invalidMessage="有效长度为1-10"  />
          </div>
        </div>
        <div class="formitm">
          <label class="lab">描　　述：</label>
          <div class="ipt">
            <input type="text" id="update-text" name="update-text" class="easyui-textbox"  data-options="required:true,missingMessage:'描述不能为空!',prompt:'请输入描述...'" style="width:200px;"  />
          </div>
        </div>
        <div style="height: 30px;"></div>
        <!-- <div style="border-top: 2px dashed black; display: block; height: 2px;"></div>
        <div style="height: 10px;"></div> -->
        <div align="center" style="margin-right: 10px;">
          <input style="width: 70px;" class="btn btn-primary btn-sm" type="button" value="提交" onclick="updateMenu()" />
          <input style="width: 70px;" class="btn btn-default btn-sm" type="reset" value="重置">
        </div>
      </fieldset>
    </form>
  </div>
</div>
</body>
</html>
