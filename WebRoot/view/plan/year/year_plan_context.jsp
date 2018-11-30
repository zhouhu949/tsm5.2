<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-年度销售规划-变更日志</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->

<script type="text/javascript">
$(function(){
	/*表单优化*/
    $(".skin-minimal input").iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
    $(".sure-btn").click(function(){
    	var reason=$("#reason").val().replace(/(^\s+)|(\s+$)/g);
    	if(reason==null||reason.length==0){
    		window.parent.warmDivDialog("warm","变更说明不能为空！");
    	}else if(reason.replace(/[^\x00-\xff]/g, '__').length>500){
    		window.parent.warmDivDialog("warm","变更说明长度最大为500！");
    	}else{
    		window.parent.reason=reason;
        	window.parent.confirmSave();
    	}
	});
	$(".cancel-btn").click(function(){
		closeParentPubDivDialogIframe("planContextWindow");
	});

	/*textarea提示信息*/
    $(".bomp-sce-area").find('textarea,span').click(function(){
    	var ipt_a = $(this).parent().find('textarea');
        ipt_a.focus();
        $(this).parent().find('span').hide();
        ipt_a.blur(function(){
            if($(this).val() == ''){
                $(this).parent().find('span').show();
            }
         }); 
    });

    
});
</script>
</head>
<body> 
<div class='bomp-cen'>
	<p class='bomp-p bomp-p-width'>
		<label class='lab_a fl_l'>规划变更说明：</label>
		<label class="bomp-sce-area">
			<textarea id="reason" class='area_a w_b fl_l' style="height:125px;"></textarea>
			<span>最多可输入500个汉字。</span>
		</label>
	</p>
	<div class='bomb-btn bomb-btn-top'>
		<label class='bomb-btn-cen'>
			<a href="javascript:;" class="com-btna bomp-btna sure-btn com-btna-sty fl_l"><label>确定</label></a>
			<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
		</label>
	</div>
</div>
</body>
</html>