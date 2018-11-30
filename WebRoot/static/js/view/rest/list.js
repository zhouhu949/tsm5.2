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
        url: ctx + "/query/jsontest",
        columns: [[
            {field: 'orgId', title: '单位机构', width: 150, align: 'center'},
            {field: 'callId', title: '通话记录', width:150, align: 'center'},
            {field: 'callerNum', title: '主叫', width: 80, align: 'center'},
            {field: 'called_num', title: '被叫', width: 80, align: 'center'},
            {field: 'isDell', title: '状态', width: 50, align: 'center',
                formatter: function (value, row, index) {
                    if (value == 0) {
                        return '删除';
                    } else if (value == 1) {
                        return '<span style=\"color:#1e1ab8\">正常</span>';
                    } else {
                        return '';
                    }// ,0: 删除 1：提交注册 2: 审核通过 3:审核拒绝 4：暂停使用
                }
            },
            {field: 'inputDate', title: '录入时间', width: 120, align: 'center', sortable: true},
            //{field: 'modifydate', title: '更新时间', width: 120, align: 'center', sortable: true},
            {
                field: 'opt2', title: '操作', width: 150, align: 'center',
                formatter: function (value, row, index) {
                    return "<a class='edit_' href=javascript:openInfo('" + row.callId + "') style='cursor:hand'>详情</a>";

                }
            }
        ]],
        rowStyler:function(index,row){
            if((index % 2) != 0) {
             //   return 'background-color:#FAFAD2;';
            }
        },toolbar:toolbar1,
        rownumbers : true,
        singleSelect : true,
        toolbar : '#tb', //如果不要黑认显示toolbar1定义
        sortName:'callId',
        sortOrder:'desc',
        fit : true,
        fitColumns : true,
        striped:true,
        pagination : true,
        pageSize : 15,
        pageList : [ 15, 20, 30, 40, 50 ]
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
   /* $(p).pagination({
        pageSize: 20,//每页显示的记录条数，默认为10
        pageList: [20,30,35],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
     });*/
});

function openInfo() {
    var selection = $('#data-list').datagrid("getSelected");
    if (selection == null) {
        $.messager.alert('提示', '请先选择需要查看详情的信息!');
        return;
    }
    parent.addTabFun({
        src:"/query/json?ids=" + selection.callId,
        title: "查看详情"
    });
}