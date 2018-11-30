$(function () {
    $("#data-list").datagrid({
        url: ctx + "/sql/list",
        columns: [[
            {field: 'name', title: '名称', width: 150, align: 'center'},
            {field: 'execTime', title: '执行时长', width:50, align: 'center'},
            {field: 'sql', title: 'SQL语句', width: 300, align: 'center'},
            {field: 'inputTime', title: '执行时间', width: 120, align: 'center', sortable: true}
            //{field: 'modifydate', title: '更新时间', width: 120, align: 'center', sortable: true},
           /* {
                field: 'opt2', title: '操作', width: 150, align: 'center',
                formatter: function (value, row, index) {
                    return "<a class='edit_' href=javascript:openInfo('" + row.callId + "') style='cursor:hand'>详情</a>";

                }
            }*/
        ]],
        rowStyler:function(index,row){
            if((index % 2) != 0) {
             //   return 'background-color:#FAFAD2;';
            }
        },
        rownumbers : true,
        singleSelect : true,
        toolbar : '#tb', //如果不要黑认显示toolbar1定义
        fit : true,
        fitColumns : true,
        striped:true,
        pagination : true,
        pageSize : 20,
        pageList : [ 20, 30, 40, 50 ]
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
        pageList: [20,30,35],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
     });
});

function openInfo() {
    var selection = $('#data-list').datagrid("getSelected");
    if (selection == null) {
        $.messager.alert('提示', '请先选择需要查看详情的信息!');
        return;
    }
    parent.addTabFun({
        src:"/sql/json?ids=" + selection.callId,
        title: "查看详情"
    });
}