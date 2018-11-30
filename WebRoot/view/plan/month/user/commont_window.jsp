<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-月度计划（团队编辑）-编辑页面（团队）-计划点评</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
<script type="text/javascript">
var planId="${planId}";
$(function(){
    $(".sure-btn").click(function(){
    	var dContext=$("#dContext").val();
    	if(dContext!=null&&dContext.replace(/(^\s+)|(\s+$)/g,'').length>0){
    		dContext = dContext.replace(/(^\s+)|(\s+$)/g,'');
    		if(dContext.length<=500){
    			$.post(ctx+"/plan/month/user/commont",{"planId":planId,"context":dContext},
    					function(data){
    				if("success"==data.status){
    					window.parent.refreshPage();
    					setTimeout('closeParentPubDivDialogIframe("commontWindow");',10);
    					//closeParentPubDivDialogIframe("commontWindow");
    				}else{
    				}
    			}); 
    		}else{
    			window.parent.warmDivDialog("erro","评论内容最大长度为500字!");
    		}
    	}else{
    		window.parent.warmDivDialog("erro","评论内容不能为空!");
    	}
		
    });
	$(".cancel-btn").click(function(){
		closeParentPubDivDialogIframe("commontWindow");
	});

	/*textarea提示信息*/
    $('.bomp-sce-area').find('textarea,span').click(function(){
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
		<label class='lab_a fl_l'>评论内容：</label>
		<label class="bomp-sce-area">
			<textarea class='area_a w_b fl_l' id="dContext" style="height:125px;"></textarea>
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