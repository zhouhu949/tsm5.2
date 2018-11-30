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
function doSelect(id){


    $.ajax({
        type: 'GET',
        url: ctx + "/email/log/get",
        data: {id:id},
        dataType: 'json',
        success: function (msg) {
          //  alert(msg.orgId);
            var str="\t<div class=\"send-record-prod\">\n" +
                "\t\t\t\t<div class=\"record-prod-title\">关于产品一的产品介绍</div>\n" +
                "\t\t\t\t<p><label for=\"\">收件人：</label><span class=\"overflow_hidden\">企蜂通信有限公司企蜂通信有限公司企蜂通信有限公司</span></p>\n" +
                "\t\t\t\t<p><label for=\"\">发送时间：</label><span class=\"overflow_hidden\">"+msg.inputtime+"</span></p>\n" +
                "\t\t\t\t<p><label for=\"\">收件人：</label><span class=\"overflow_hidden\">"+msg.id+"</span></p>\n" +
                "\t\t\t\t<p><label for=\"\">发件人：</label><span class=\"overflow_hidden\">企蜂通信有限公司</span></p>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<div class=\"mail-record-enve\">\n" +
                "\t\t\t\t<div class=\"record-enve-title\">亲爱的用户</div>\n" +
                "\t\t\t\t<p><span>你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用你好，我们最近扔出了“广千邮件聚合”功能，现邀请你来试用</span></p>\n" +
                "\t\t\t\t<p><span>现在，看看你的收件箱我们最近扔出了“广千邮件聚合”功能，现邀请你来试用我们最近扔出了“广千邮件聚合”功能，现邀请你来试用我们最近扔出了“广千邮件聚合”功能，现邀请你来试用我们最近扔出了“广千邮件聚合”功能，现邀请你来试用我们最近扔出了“广千邮件聚合”功能，现邀请你来试用我们最近扔出了“广千邮件聚合”功能，现邀请你来试用我们最近扔出了“广千邮件聚合”功能，现邀请你来试用</span></p>\n" +
                "\t\t\t\t<div class=\"encl-osure clearfix\">\n" +
                "\t\t\t\t\t<div class=\"encl-osure-title\"><img width=\"15\" height=\"15\" src=\"${ctx}/static/images/fashion.png\" alt=\"\">附件（1个）</div>\n" +
                "\t\t\t\t\t<div class=\"encl-osure-left fl_l\"><img width=\"30\" height=\"30\" src=\"${ctx}/static/images/fashion.png\" alt=\"\"></div>\n" +
                "\t\t\t\t\t<div class=\"encl-osure-right fl_l\">\n" +
                "\t\t\t\t\t\t<p><span>慧营销操作手册.doc(100MB)</span></p>\n" +
                "\t\t\t\t\t\t<p><a href=\"\">下载</a><a href=\"\">预览</a></p>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t</div>";
            $("#emailinfo").html(str);
        }
    });

};