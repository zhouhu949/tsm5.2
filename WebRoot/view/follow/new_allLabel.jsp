<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>行动标签</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
<script type="text/javascript">
$(function(){
	$('.all-label').find('a').each(function(){
		$(this).click(function(){
			$(this).toggleClass("foll-hover");
		});
	})


	// 初始化
	var $lable = $('#label').val().split(",");
	// 获取指定 iframe 的 document
	var $iframe = $("#allLabels",window.parent.document);	
	if($lable.length > 0){
		for (var j = 0; j < $lable.length; j++)  
		{    
			$("a[name='l_label']").each(function(){
				if($lable[j] == $(this).attr("id")){
					$(this).addClass("foll-hover");
					return;
				}
			});
		}
	}
	
	// 点击确定
	$('.sure-btn').bind("click",function(){
		var $obj_Checked = $('.foll-hover');
		var arry = [];
		var html = '';
		$obj_Checked.each(function(){
			arry.push($(this).attr("id"));
			var labelName = $(this).attr("labelName");
			html+='<span label="1" class="e">'+labelName+'</span>';
		});
		var obj = arry.join(",");	
		if(obj != null){
			$("span[label=1]",window.parent.document).remove();
			$("#addLabelBtn",window.parent.document).before(html);
			$iframe.val(obj);
		}
		closeParentPubDivDialogIframe('alert_cust_follow_e');
		$("#query", window.parent.document).click(); // 执行父类方法
		
	});
	
	// 点击取消
	$('.cancel-btn').bind("click",function(){
		$("span[label=1]",window.parent.document).remove();
		$iframe.val("");
		closeParentPubDivDialogIframe('alert_cust_follow_e');
		$("#query", window.parent.document).click(); // 执行父类方法
	});
});
</script>
</head>
<body> 
<div class='bomp-cen'>
	<input type="hidden" id="label" value="${label}"/>
	<div class="all-label">
		 <c:forEach items="${entitys}" var="entity" varStatus="v">     
		 		<a href="javascript:;" name="l_label" class="fl_l" id="${entity.optionlistId}" labelName="${entity.optionName}"><label>${entity.optionName}</label></a>		                     
         </c:forEach> 
	</div>
	<div class='bomb-btn'>
		<label class='bomb-btn-cen'>
			<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>确定</label></a>
			<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
		</label>
	</div>
</div>
</body>
</html>