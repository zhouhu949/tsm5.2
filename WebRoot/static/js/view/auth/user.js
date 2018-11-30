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
        url: "/user/list",
        columns: [[
            {field: 'ck', checkbox:true, width:50, align: 'center'},
            {field: 'userName', title: '用户名', width: 80, align: 'center'},
            {field: 'userAccount', title: '登录名', width: 80, align: 'center'},
            {field:'isbackground',title:'前后台',width:50,align:'center',
                formatter:function formatStatus(value,row,index){
                    if(row.isbackground == 0){
                        return "普通人员";
                    }else if(row.isbackground == 1){
                        return "<span style='color: orangered'>管理员</span>";
                    }else{
                        return "<spanstyle='color: red'>未知</span>";
                    }
                }
            },
            {field:'isunit',title:'WEB登录',width:50,align:'center',
                formatter:function formatStatus(value,row,index){
                    if(row.isunit == 1){
                        return "是";
                    }else if(row.isunit == 0){
                        return "<span style='color: green'>否</span>";
                    }else{
                        return "<spanstyle='color: red'>未知</span>";
                    }
                }
            },
            {field:'onlineState',title:'状态',width:50,align:'center',
                formatter:function formatStatus(value,row,index){
                    if(row.onlineState == 1){
                        return "是";
                    }else if(row.onlineState == 0){
                        return "<span style='color: green'>否</span>";
                    }else{
                        return "<spanstyle='color: red'>未知</span>";
                    }
                }
            },
            {field: 'serveTime', title: '服务到期时间', width: 80, align: 'center'},
          //  {field: 'createTime', title: '注册时间', width: 80, align: 'center'},
            {
                field: 'opt2', title: '操作', width: 50, align: 'center',
                formatter: function (value, row, index) {
                    return "<a class='edit_' href=javascript:openInfo('" + row.callId + "') style='cursor:hand'>详情</a>";

                }
            }
        ]],
        rowStyler:function(index,row){
            if((index % 2) != 0) {
               // return 'background-color:#FAFAD2;';
            }
        },toolbar:toolbar1,
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
        pageSize : 20,
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
    $(p).pagination({
        pageSize: 10,//每页显示的记录条数，默认为10
        pageList: [15, 20, 30, 40, 50 ,80],//可以设置每页记录条数的列表
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
    if (selection == null) {
        $.messager.alert('提示', '请先选择需要查看详情的信息!');
        return;
    }
    parent.addTabFun({
        src:"/user/edit?userId=" + selection.userId,
        title: "查看详情"
    });
}