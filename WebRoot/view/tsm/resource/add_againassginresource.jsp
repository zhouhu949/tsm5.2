<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/include.jsp" %>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css">
<!--主要样式-->
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/static/js/idialog/skins/iblue.css"> --%>
<script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
<!--可移动弹框插件公共js-->
<script type="text/javascript"
        src="${ctx }/static/thirdparty/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
<script type="text/javascript">
    $(function () {
        var url = "${ctx}/orgGroup/get_group_user_json";
        $("#deptTree").tree({
            url: url,
            formatter: function (node) {
                var txt = node.text;
                if (node.attributes.type == 'M') {
                    txt = '<input type="radio" ownerName="' + txt + '" name="radioBtn" value="' + node.id + '"/>' + txt;
                }
                return txt;
            },
            onLoadSuccess: function (node, data) {
                $('#deptTree').tree("expandAll");
            },
            onExpand: function (node) {
                $(".tree-title").removeClass("tree-folder-open");
            },
            onSelect: function (node) {
                $(".tree-node-selected").children().last().children().attr('checked', true)
            }
        });
        var isSubmit = true ;
        $("#againBtn").click(function () {
            if(!isSubmit){
                return ;
            }
            isSubmit = false;
            var rd = $("input[name=radioBtn]:checked");
            if (rd.length == 0) {
                isSubmit = true;
                return;
            } else {
            	ajaxLoading("正在分配，请稍后") ;
                var ownerName = rd.attr("ownerName");
                var ownerAcc = rd.val();
                againAssignRes(ownerAcc, ownerName,isSubmit);
            }
        });


        $("#cancel").click(function () {
            parent.closePubDivDialogIframe("againassginResource");
        });

    });
    //再分配资源
    function againAssignRes(ownerAcc, ownerName,isSubmit) {
        var ids = $("#ids").val();
        var $isLimtis = $('#isLimtis').val();
        $.ajax({
            url: ctx + '/res/resmanage/getAssginRes',
            type: 'post',
            data: {
                'ids': ids, 'ownerAcc': ownerAcc, 'ownerName': ownerName
            },
            error: function (XMLHttpRequest, textStatus) {
            	ajaxLoadEnd();
                isSubmit = true ;
            },
            success: function (data) {
                if (data == '0') {
                    window.top.iDialogMsg("提示", '分配资源成功');
                    setTimeout('parent.document.forms[0].submit()', 100);
                } else if (data == '0001') {
                    window.top.iDialogMsg("提示", '分配资源超过设置上限！');
                    setTimeout(parent.closePubDivDialogIframe("againassginResource"), 100);
                } else {
                    window.top.iDialogMsg("提示", '分配资源！');
                    setTimeout(parent.closePubDivDialogIframe("againassginResource"), 100);
                }
                isSubmit = true ;
                ajaxLoadEnd();
            }
        });
    }
</script>
<body>
<form method="post" id="myForm1" name="myForm1">
    <input type="hidden" id="ctPath" value="${ctx}"/>
    <input type="hidden" id="ids" value="${ids}"/>
    <input type="hidden" id="isLimtis" value="${isLimtis}"/>

    <p class="sele-hb-grou">
        <label for="" style="margin-bottom:0;">选择分配对象：</label>
    </p>

    <div class="change-group" style="height:240px;padding-top:5px;">
        <div class="reso-deall"  data-type="search-tree">
            <ul id="deptTree"></ul>
        </div>
    </div>
    <div class="bomb-btn">
        <label class="bomb-btn-cen">
            <a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="###" id="againBtn"><label>确定</label></a>
            <a class="com-btna bomp-btna cancel-btn fl_l" href="###" id="cancel"><label>取消</label></a>
        </label>
    </div>
</form>
</body>


