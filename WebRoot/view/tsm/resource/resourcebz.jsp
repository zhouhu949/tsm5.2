<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/common/include.jsp" %>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css">
<!--主要样式-->
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/static/js/idialog/skins/iblue.css"> --%>
<script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/common_check_4.js${_v}"></script>
<script type="text/javascript">
    $(function () {
        $("#closeId").click(function () {
            parent.closePubDivDialogIframe("addResLogId");
        });

        
        var isSubmited = true;
        $('#submitId').click(function () {
            if (!isSubmited || (!checkForm())) {
                return false;
            }
            isSubmited = false;
            $.ajax({
                url: ctx + '/res/cust/addResLog',
                type: 'post',
                data: {
                    context: $('#context').val(),
                    nextConcatTime: $('#nextConcatTime').val(),
                    concatName:$('#concatName').val(),
                    custId: $("#custId", window.parent.document).val()
                },
                dataType: 'json',
                error: function (XMLHttpRequest, textStatus) {
                	isSubmited = true;
                },
                success: function (data) {
                	 isSubmited = true;
                    if (data != '-3') {
                        window.top.iDialogMsg("提示", '保存成功！');
                        var status = data.status;
                        var resId = data.resId;
                        var isCall = "1";
                        var name = data.name;
                        var phone = data.phone;
                        var filterType = data.filterType;
                        var type = data.type;
                        //window.parent.window.updateStatus(status, resId, isCall, name, phone, filterType, type)
                        window.parent.window.next();
                        setTimeout('window.parent.closePubDivDialogIframe("addResLogId");', 1000);
                        // 刷新主页面
                    } else {
                        parent.closePubDivDialogIframe("addResLogId");
                    }
                    
                }
            });
        })

    });
</script>
</head>
<body>
<form action="" method="post" id="myForm" name="myForm"></form>
<input type="hidden" id="concatName" name="concatName" value="${concatName }" />
<div class='bomp-cen'>
    <p class='bomp-p bomp-p-nexttao'>
        <label class='lab_a fl_l'>下次联系时间：</label>
        <span style="display:inline-block;line-height:27px;">
            <input style="height:27px;" type="text" value=""
            name="nextConcatTime"
            onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})"
            id="nextConcatTime" class="ipt"/>
        </span>
    </p>

    <p class='bomp-p bomp-p-width bomp-error'>
        <label class='lab_a fl_l'><i class="com-red">*&nbsp;</i>备注：</label>
        <label class="bomp-sce-area">
            <textarea class='area_a w_b fl_l' style="height:125px;" id="context" name="context" checkProp='chk_1_1'
                      maxlength="200" placeholder="最多可输入100个汉字。"></textarea>
        </label>
        <span class="error" id="error_context"></span>
    </p>

    <div class='bomb-btn' style="margin-top:10px;">
        <label class='bomb-btn-cen'>
            <a href="###" class="com-btna bomp-btna sure-btn com-btna-sty fl_l" id="submitId"><label>保存并继续</label></a>
            <a href="###" class="com-btna bomp-btna cancel-btn fl_l" id="closeId"><label>取消</label></a>
        </label>
    </div>
</div>
</body>
</html>