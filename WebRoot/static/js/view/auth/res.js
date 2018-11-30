var toolbar1 = [{
    text: '添加',
    iconCls: 'icon-add',
    handler: function () {
        parent.addTabFun({
            src: ctx + "/index/add", title: "新增"
        });
    }
}];
$(function () {
    $("#data-list").datagrid({
        url: "/authres/list",
        columns: [[
            {field: 'resourceName', title: '资源名称', width:100, align: 'center'},
            {field: 'resourceString', title: 'URL地址', width: 200, align: 'center'},
            {field: 'resourceDesc', title: '资源描述', width: 100, align: 'center'},
            {field: 'level', title: '级别', width: 50, align: 'center'},
            {field: 'sort', title: '排序', width: 50, align: 'center'},
            {field: 'createTime', title: '录入时间', width: 80, align: 'center', sortable: true},
            {
                field: 'opt2', title: '操作', width: 50, align: 'center',
                formatter: function (value, row, index) {
                    var str="";
                   // str+="<a class='edit_' href=javascript:delMenu('" + row.resourceId + "') style='cursor:hand'>删除</a>";
                    str+="&nbsp;&nbsp;<a class='edit_' href=javascript:openUpdate('" + row.resourceId + "') style='cursor:hand'>编辑</a>";
                    str+="&nbsp;&nbsp;<a class='edit_' href=javascript:openInfo('" + row.resourceId + "') style='cursor:hand'>详情</a>";
                    return str;
                }
            }
        ]],
        rowStyler:function(index,row){
            if((index % 2) != 0) {
              //  return 'background-color:#FAFAD2;';
            }
        },toolbar:toolbar1,
        rownumbers : true,
        singleSelect : true,
        toolbar : '#tb', //如果不要黑认显示toolbar1定义
        sortName:'resource_id',
        sortOrder:'desc',
        fit : true,
        fitColumns : true,
        striped:true,
        pagination : true,
        pageSize : 20,
        pageList : [20, 30, 40, 50 ]
     });
 /*   $('#group').combobox({
        // url: ctx + '/user/findUserGroup',
        data: [{'id': -1, 'text': '请选择状态', 'selected': true}, {'id': 1, 'text': '待审核'}, {
            'id': 2,
            'text': '审核通过'
        }, {'id': 3, 'text': '审核拒绝'}, {'id': 0, 'text': '删除'}],//1:正常 2:停用 0:删除
        valueField: 'id',
        textField: 'text',
        panelHeight: 'auto'
    });
*/
    var p = $("#data-list").datagrid('getPager');
    $(p).pagination({
        pageSize: 20,//每页显示的记录条数，默认为10
        pageList: [ 20, 30, 40, 50 ,80],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        /*onBeforeRefresh:function(){
         $(this).pagination('loading');
         alert('before refresh');
         $(this).pagination('loaded');
         }*/
    });
    $('#update-parentId').combotree({
        url:ctx + '/menu/getAllMenujson',
        valueField: 'id',
        textField: 'name',
        panelHeight: 'auto'
    });

});

function openInfo() {
    var selection = $('#data-list').datagrid("getSelected");
   // alert(selection.resourceId);
    if (selection == null) {
        $.messager.alert('提示', '请先选择需要查看详情的信息!');
        return;
    }
    parent.addTabFun({
        src:"/authres/edit?resourceId=" + selection.resourceId,
        title: "修改资源"
    });
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
                data: {resourceId: selection.resourceId},
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


function openUpdate(){

    var selection = $('#data-list').treegrid('getSelected');
    if(selection==null){
        $.messager.alert('提示','请先选择一项!');
        return;
    }
    var parentId= selection.parentId;
    if(parentId==null||parentId==0)parentId = 1;
    $("#update-id").val(selection.resourceId);
    $("#update-name").textbox('setValue',selection.resourceName);
    $("#update-text").textbox('setValue',selection.resourceDesc);
    $("#update-parentId").combotree("setValue",parentId);
    $("#update-url").textbox('setValue',selection.resourceString);
    $("#update-sort").numberbox('setValue',selection.sort);
    $("#update-win").window("open");
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
