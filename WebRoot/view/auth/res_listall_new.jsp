<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
  <title></title>
  <style type="text/css">
   </style>
  <%@ include file="/common/include.jsp"%>
  <script type="text/javascript">
    function searchMeet(){
      var resourceName = $("#resourceName").val();
      var resourceString = $("#resourceString").val();
      $("#data-list").datagrid('load',{resourceString:resourceString,resourceName:resourceName});
    }
    $(function(){
      $("#data-list").treegrid({
        url:ctx + '/menu/leftall',
        onLoadSuccess:function(){
          $('#add-parentId').combotree({
            url:ctx + '/menu/getAllMenujson'
          });

          $('#update-parentId').combotree({
            url:ctx + '/menu/getAllMenujson'
          });
        }
      });

    });
    function openAdd(){
      var selection = $('#data-list').treegrid('getSelected');
      $("#add-form").form("reset");
      $("#add-win").window("open");
   // alert(selection.id);
      if(selection!=null){
        if(selection.parentId==0){
          $("#add-parentId").combotree("setValue","0");
        }else{
          $("#add-parentId").combotree("setValue",selection.id);
        }
      }
    }

    function openUpdate(){
      var selection = $('#data-list').treegrid('getSelected');
      if(selection==null){
        $.messager.alert('提示','请先选择一项!');
        return;
      }
    //
     var parentId= selection.parendId;
      if(parentId==null||parentId==0)parentId = 1;

      $("#update-id").val(selection.id);
      $("#update-name").textbox('setValue',selection.name);
      $("#update-text").textbox('setValue',selection.text);
      $("#update-parentId").combotree("setValue",parentId);
      $("#update-url").textbox('setValue',selection.url);
      $("#update-sort").numberbox('setValue',selection.sort);
      $("#update-win").window("open");
    }

    function addMenu(){
      if($("#add-form").form('validate') == false){
        return false;
      }

      var name = $("#add-name").val();
      var text = $("#add-text").val();
      var parentId = $("#add-parentId").combotree("getValue");
      var url = $("#add-url").val();
      var sort = $("#add-sort").val();
      if(parentId==null||parentId==0)parentId = 1;
      if(sort==null)sort = 0;
      if(parentId==1){
        parentId="";
      }
      $.post(ctx+"/authres/save",{resourceName:name,resourceDesc:text,resourceString:url,parentId:parentId,sort:sort},function(msg){
        if(msg=="0"){
          $("#add-win").window("close");
          $('#data-list').treegrid('reload');
        }else if(msg=="2"){
          $.messager.alert('提示','提交参数不对!');
        }else{
          $.messager.alert('提示','添加失败，请稍候再试!');
        }
      },"text");
    }

    function updateMenu(){
      if($("#update-form").form('validate') == false){
        return false;
      }
      var id = $("#update-id").val();
      var name = $("#update-name").val();
      var parentId = $("#update-parentId").combotree("getValue");
      var url = $("#update-url").val();
      var sort = $("#update-sort").val();
      var text = $("#update-text").val();
      if(parentId==null||parentId==0)parentId = 1;
      if(sort==null)sort = 0;
      if(parentId==1){
        parentId="";
      }
     // alert(parentId);
  $.post(ctx+"/authres/update",{resourceId:id,resourceName:name,resourceDesc:text,resourceString:url,parentId:parentId,sort:sort},function(msg){
        if(msg=="0"){
          $("#update-win").window("close");
          $('#data-list').treegrid('reload');
        }else if(msg=="2"){
          $.messager.alert('提示','提交参数不对!');
        }else{
          $.messager.alert('提示','更新失败，请稍候再试!');
        }
      },"text");
    }

    function delMenu() {

      var selection = $('#data-list').treegrid('getSelected');
      if (selection == null) {
        $.messager.alert('提示', '请先选择一项!');
        return;
      }
      $.messager.confirm("提示", "确定要删除该项吗？", function (r) {
        if (r) {

          $.ajax({
            url: ctx + "/authres/delete",
            data: {resourceId: selection.id},
            type: "POST",
            dataType: 'text',
            success: function (msg) {
              if (msg == "0") {
                $('#data-list').treegrid('reload');
              } else if (msg == 2) {
                $.messager.alert('提示', '该项不存在!');
              } else if (msg == 3) {
                $.messager.alert('提示', '该菜单下还有子菜单，不能删除!');
              } else {
                $.messager.alert('提示', '删除失败，请稍候再试!');
              }
            }
          });
        }
      });
    }
  </script>
</head>
<body>

<div id="tb">
  &nbsp;&nbsp;
  <a class="easyui-linkbutton" plain="true" icon="icon-add" onclick="openAdd();">新增</a>
<%--  &nbsp;&nbsp;
  <a class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="openUpdate();">修改</a>
  &nbsp;&nbsp;
  <a class="easyui-linkbutton" plain="true" icon="icon-remove" onclick="delMenu();">删除</a>--%>
</div>
<table id="data-list" title="资源列表" class="easyui-treegrid"
       data-options="
                url: '${ctx}/menu/leftall',
                method: 'POST',
                rownumbers: true,
                idField: 'id',
                treeField: 'name',
                toolbar:'#tb',
                fit:true,
	    		  fitColumns:true
            ">
  <thead>
  <tr>
    <th data-options="field:'name'" width="80" align="left">名称</th>
    <th data-options="field:'url'" width="120" align="left">Url</th>
    <th data-options="field:'sort'" width="20" align="center">排序</th>
    <th data-options="field:'text'" width="80" align="center">描述</th>
    <th data-options="field:'inputtime'" width="80" align="center">添加时间</th>
  </tr>
  </thead>
</table>
<div id="add-win" class="easyui-window" title="添加菜单"
     data-options="collapsible:false,minimizable:false,maximizable:false,modal:true,closed:true"
     style="width:350px;height:320px;">
  <div class="m-form">
    <form id="add-form" name="" action="#">
      <fieldset style="border:0;">
        <div class="formitm">
          <label class="lab">名　　称：</label>
          <div class="ipt">
            <input type="text" id="add-name" name="add-name" class="easyui-textbox"  data-options="required:true,missingMessage:'名称不能为空!',prompt:'请输入菜单名称...'" style="width:200px;" maxlength="20" validtype="length[1,20]" invalidMessage="有效长度为1-20"/>
          </div>
        </div>
        <div class="formitm">
          <label class="lab">上级菜单：</label>
          <div class="ipt">
            <input id="add-parentId" name="add-parentId" class="easyui-combobox" data-options="required:true,missingMessage:'上级菜单不能为空!',prompt:'请输入上级菜单名称...'" style="width:200px;"/>
          </div>
        </div>
        <div class="formitm">
          <label class="lab">　  URL：</label>
          <div class="ipt">
            <input type="text" id="add-url" name="add-url" class="easyui-textbox"  data-options="required:false,missingMessage:'URL不能为空!',prompt:'请输入URL...'" style="width:200px;" maxlength="100" validtype="length[1,100]" invalidMessage="有效长度为1-100"/>
          </div>
        </div>
        <div class="formitm">
          <label class="lab">排　　序：</label>
          <div class="ipt">
            <input type="text" id="add-sort" name="add-sort" class="easyui-numberbox"  data-options="required:true,missingMessage:'排序不能为空!',prompt:'请输入排序...'" style="width:200px;" min="0" maxlength="10" validtype="length[1,10]" invalidMessage="有效长度为1-10"  />
          </div>
        </div>  <div class="formitm">
        <label class="lab">描　　述：</label>
        <div class="ipt">
          <input type="text" id="add-text" name="add-text" class="easyui-textbox"  data-options="required:true,missingMessage:'描述不能为空!',prompt:'请输入描述...'" style="width:200px;" />
        </div>
      </div>
        <div style="height: 30px;"></div>
        <div align="center" style="margin-right: 10px;">
          <input style="width: 70px;" class="btn btn-primary btn-sm" type="button" value="提交" onclick="addMenu()" />
          <input style="width: 70px;" class="btn btn-default btn-sm" type="reset" value="重置">
        </div>
      </fieldset>
    </form>
  </div>
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
