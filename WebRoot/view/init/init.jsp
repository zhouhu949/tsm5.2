<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="/common/include.jsp" %>
    <title>初始化</title>
    <script type="text/javascript">
        var opera = true;
    	$(function () {
    		pubDivShowIframe('alert_novice_help_a','${ctx}/view/help/novice_help_com/alert_novice_help_a.jsp','系统设置引导说明',650,350);
        });
    	function doSutmit() {
            if(opera){
               opera = false;
               var vt= $("#select_initParam").val();
               //  alert(vt);
               $("#initParam").attr("value",vt);
               //  alert($("#initParam").val());
               document.forms[0].submit();
            }
        }
    </script>

</head>
<body>
<form id="myForm" action="${ctx}/clientlogin/init" method="post">
    <div class="hyx-cfu-left fl_l">
        <input type="hidden" value="0" name="initParam" id="initParam">
    </div>
    <div class="hyx-sms hyx-ms hyx-hsrs-a fl_l">
        <p class="tit_a">
            <label class="lab lab_a">温馨提示：</label>
            <label class="lab lab_b">初始系统设置</label>
        </p>

        <p class="tit">
            <label class="lab fl_l"><i class="tip"></i>适用客户类型</label>
        </p>

        <p class="tit_b">
            <label class="lab">类型：</label>
            <select class="sel sel_w" style="width:120px" id="select_initParam">
                <option value='1'>企业客户</option>
                <option value='0'>个人客户</option>
            </select>
        </p>

        <div class="com-btnbox">
            <!-- <a href="javascript:doSutmit();" class="com-btna com-btna-sty" ><label>保存</label></a> -->
        </div>
    </div>
</form>
</body>
</html>