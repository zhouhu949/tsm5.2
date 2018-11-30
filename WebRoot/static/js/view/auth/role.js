$(function () {
    $("#data-list").datagrid({
        url: ctx+"/role/list",
        columns: [[
            {field: 'roleName', title: '名称', width:80, align: 'center'},
            {field: 'roleCode', title: '编码', width: 50, align: 'center'},
            {field: 'roleDesc', title: '描述', width: 100, align: 'center'},
            {field: 'enabled', title: '状态', width: 20, align: 'center',
                formatter:function formatStatus(value,row,index){
                if(row.enabled == 1){
                    return "<span style='color: green'>是</span>";
                }else if(row.enabled == 0){
                    return "<span style='color: red'>否</span>";
                }else{
                    return  "<span style='color: #b6b9cf'>未知</span>";
                }
            }},
            {field: 'createTime', title: '录入时间', width: 80, align: 'center', sortable: true},
            {
                field: 'opt2', title: '操作', width: 50, align: 'center',
                formatter: function (value, row, index) {
                    var str="";
                  //  str+="<a class='edit_' href=javascript:openDelete('" + row.roleId + "') style='cursor:hand'>删除</a>";
                    str+="&nbsp;&nbsp;<a class='edit_' href=javascript:openInfo('" + row.roleId + "') style='cursor:hand'>编辑</a>";
                    str+="&nbsp;&nbsp;<a class='edit_' href=javascript:openInfo('" + row.roleId + "') style='cursor:hand'>详情</a>";
                    return str;
                }
            }
        ]],
        rowStyler:function(index,row){
            if((index % 2) != 0) {
               return 'background-color:#FAFAD2;';
            }
        },
        rownumbers : true,
        singleSelect : false,
        selectOnCheck: true,
        checkOnSelect: true,
        toolbar : '#tb', //如果不要黑认显示toolbar1定义
        sortName:'orgId',
        sortOrder:'desc',
        fit : true,
        fitColumns : true,
        striped:true,
        pagination : true,
        pageSize : 10,
        pageList : [10,20, 30, 40, 50 ]
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
        pageSize: 10,//每页显示的记录条数，默认为10
        pageList: [10,20, 30, 40, 50 ,80],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        /*onBeforeRefresh:function(){
         $(this).pagination('loading');
         alert('before refresh');
         $(this).pagination('loaded');
         }*/
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
        src:"/role/edit?roleId=" + selection.roleId,
        title: "修改角色"
    });
}

function openDelete() {
    var selection = $('#data-list').datagrid('getSelected');
    if (selection == null) {
        $.messager.alert('提示', '请先选择需要删除的信息!', 'info');
        return;
    }

    $.messager.confirm("提示", "确定要删除【" + selection.roleName + "】吗？", function (r) {
        if (r) {
            $.messager.alert('提示', r?"是":"否", 'error');
          /*  $.ajax({
                type: 'POST',
                url: ctx + "/role/delete",
                data: {roleId: selection.roleId},
                dataType: 'text',
                success: function (msg) {
                    if (msg == "0") {
                        $('#data-list').datagrid('reload');
                    } else if (msg == "-1") {
                        $.messager.alert('提示', '登录已过期，请重新登录!', 'warning', function () {
                            window.location.href =  "/login.jsp";
                        });
                    } else if ($.trim(msg) == 'unauthorized') {
                        $.messager.alert('提示', '您没有权限!', 'warning');
                    } else {
                        $.messager.alert('提示', '信息删除失败', 'error');
                    }
                }
            });*/
        }
    });
}