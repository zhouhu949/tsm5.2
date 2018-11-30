<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>系统设置-系统属性设置-销售产品维护-编辑</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css"><!--弹框样式-->
<script type="text/javascript">
var opera = true;
$(function(){
    $("input:text:first").focus();

    $(".sure-btn").click(function(){
    	if(opera){
    		opera = false;
    		checkIsNull();
    	}
	});
	$(".cancel-btn").click(function(){
		closeParentPubDivDialogIframe('alert_sys_pro_set_a');
	});
});

function submit_(){
	$('#proSetFrom').ajaxSubmit({
		url : '${ctx}/opt/set/proSet.do',
		type : 'post',
		dataType:'json',
		error : function(XMLHttpRequest, textStatus){alert(XMLHttpRequest.status);},
		success : function(data) {	
			if(data == 0){			
				window.top.iDialogMsg("提示","保存成功！");
				setTimeout('window.parent.frames[0].document.forms[0].submit();',1000);					
				setTimeout('closeParentPubDivDialogIframe("alert_sys_pro_set_a");',1000);					
			}else{
				window.top.iDialogMsg("提示","保存失败！");
			}
		}
	});
}

//验证是否为空
function checkIsNull(){
	var isTrue = true;
	$('[checkProp^="chk_"]').each(function() {
		var id =$(this).attr("id");
		if($(this).val() != null && $.trim($(this).val()) != ""){
			$("#bomp_error_"+id).removeClass("bomp-error");
			$("#error_"+id).text("");
		}else{
			isTrue = false;
			$("#bomp_error_"+id).addClass("bomp-error");
			$("#error_"+id).text("必填项！");
			opera = true;
		}
	});
	if(isTrue){ // 判断排序是否唯一
		var url = "${ctx}/opt/set/judgeOnlyProSort";	
		$.ajax({
				url: url,
				type:'POST',
				async:false,
				data:{'sort':$("#sort").val(),'id':$("#proId").val()},
				dataType:'json',
				error:function(){alert("网络异常，请稍后再操作！")},
				success:function(data){
					if(data != 0){						
						$("#bomp_error_sort").addClass("bomp-error");
						$("#error_sort").text("已存在！");
						opera = true;
					}else{
						submit_();
					}
				}
		});	
	}
}
</script>
</head>
<body> 
<div class='bomp-cen' style="width:98%;">
	<form id="proSetFrom"  method="post">
	<input type="hidden" name="id" id="proId" value="${item.id}">
	<p class='bomp-p' id="bomp_error_name">
		<label class='lab_a fl_l'><b class="bomp-red">*</b>产品名称：</label><input type='text' id="name" name="name" value='${item.name }'  maxlength="50"   class='ipt_a fl_l'  style="border:1px solid #d6d6d6 !important;"checkProp="chk_" />
		<span class="error" id="error_name"></span>
	</p>
	<p class='bomp-p'>
		<label class='lab_a fl_l'>产品规格：</label><input type='text' name="type" id="type" value='${item.type }' class='ipt_a fl_l' />
	</p>
	<p class='bomp-p'>
		<label class='lab_a fl_l'>产品型号：</label><input type='text' name="model" id="model" value='${item.model }' class='ipt_a fl_l' />
	</p>
	<p class='bomp-p'>
		<label class='lab_a fl_l'>标准价格：</label><input type='text' name="price" id="price" onblur="this.value = this.value.replace(/([^0-9\.]|(^0+[1-9])|(^\.)|(\.$))/g, '')" value='<fmt:formatNumber value='${item.price}' pattern='###0.00#'/>' class='ipt_a fl_l' />
	</p>
	<p class='bomp-p' id="bomp_error_sort"> 
		<label class='lab_a fl_l'><b class="bomp-red">*</b>排序值：</label><input type='text'  name="sort" id="sort" value='${item.sort }' onblur="this.value = this.value.replace(/[^0-9]/g, '')" class='ipt_a fl_l'  checkProp="chk_"/>
		<span class="error" id="error_sort"></span>
	</p>
	<p class='bomp-p' id="bomp_error_units">
		<label class='lab_a fl_l'><b class="bomp-red">*</b>计量单位：</label><input type='text' name="units" id="units"  value='${item.units}' class='ipt_a fl_l'  checkProp="chk_"/>
		<span class="error" id="error_units"></span>
	</p>
	<div class='bomb-btn bomb-btn-top'>
		<label class='bomb-btn-cen'>
			<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>保存</label></a>
			<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l" ><label>取消</label></a>
		</label>
	</div>
	</form>
</div>
</body>
</html>