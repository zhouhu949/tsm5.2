$(function () {
    //表格偶数行
    $("table tbody tr:odd").addClass("sty-bgcolor-b");
    //全选

    //表单优化
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
    //签约日期
    $('#d1').dateRangePicker({
    	showShortcuts:false,
        endDate: getNowFormatDate(), format: 'YYYY-MM-DD HH:mm:ss'
    }).bind('datepicker-apply', function (event, obj) {
        if (obj.date1 != 'Invalid Date') {
            $('#s_startDate').val(moment(obj.date1).format('YYYY-MM-DD HH:mm:ss'));
        }
        if (obj.date2 != 'Invalid Date') {
            $('#s_endDate').val(moment(obj.date2).format('YYYY-MM-DD HH:mm:ss'));
        }
        var s = $(this).parents('.select');
        var dt = s.children("dt");
        var dd = s.children("dd");
        dt.html("自定义");
        $("#s_sDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
    });
    //签约者
    if ($("#tt1").length > 0) {
        $("#tt1").tree({
            url: ctx + "/orgGroup/get_group_user_json",
            checkbox: true,
            onLoadSuccess: function (node, data) {
                var oas = $("#ownerAccsStr").val();
                if (oas != null && oas != '') {
                    $.each(oas.split(','), function (index, obj) {
                        var n = $("#tt1").tree("find", obj);
                        $("#tt1").tree("check", n.target).tree("expandTo", n.target);
                    });
                }
            }
        });
    }

});
//设置日期查询
var setSdate = function (i) {
    $('#s_startDate').val('');
    $('#s_endDate').val('');
    $("#s_sDateType").val(i);
};

//设置失效日期查询
var setIdate = function (i) {
    $('#s_startInvalidDate').val('');
    $('#s_endInvalidDate').val('');
    $("#s_iDateType").val(i);
};
//签约者查询条件树 确定
var seleTree = function () {
    var nodes = $('#tt1').tree('getChecked', 'checked');
    var accs = "";
    $.each(nodes, function (index, obj) {
        var type = obj.attributes.type;
        if (type == "M") {
            accs += obj.id + ",";
        }
    });
    if (accs != "") {
        accs = accs.substring(0, accs.length - 1);
    }
    $("#ownerAccsStr").val(accs);
};
//查询条件树 取消
var unCheckTree = function (tid) {
    var nodes = $('#' + tid).tree('getChecked', 'checked');
    $.each(nodes, function (index, obj) {
        $('#' + tid).tree("uncheck", obj.target);
    });
};
//组织部门查询条件树 确定
var seleGroupTree = function () {
    var nodes = $('#tt2').tree('getChecked', 'checked');
    var groupIds = "";
    $.each(nodes, function (index, obj) {
        groupIds += obj.id + ",";
    });
    if (groupIds != "") {
        groupIds = groupIds.substring(0, groupIds.length - 1);
    }
    $("#groupIdsStr").val(groupIds);
};
var doDelete=function() {
   var str="";
     $("[id^=chk_][checked]").each(function(){
        str+=$(this).val()+"";
        //alert($(this).val());
    });
    alert(str);
};
var doQuery=function() {
    alert($('#s_startDate').val()+"---"+$('#s_endDate').val());
};