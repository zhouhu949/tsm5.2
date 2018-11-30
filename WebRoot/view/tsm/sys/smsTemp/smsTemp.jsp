<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<%@ include file="/common/include.jsp"%> 
<head>
<title>系统设置-短信模板设置</title>
<style>
	.tree-node{width:240px;}
	.hyx-hsm-left dl{text-align:left;}
	.hyx-hsm-left dl dt{text-indent:30px;}
</style>
<!--本页面js-->

<script type="text/javascript">
$(function(){
	$('.alert_sms_temp_set_a').click(function(){
		pubDivShowIframe('sms_temp_set_a',ctx+'/sys/smsTemp/reditTempGroupPage.do?v='+new Date().getTime(),'编辑分类',300,130);
	});
	$('.alert_sms_temp_set_b').click(function(){
		pubDivShowIframe('sms_temp_set_b',ctx+'/sys/smsTemp/reditTempPage.do?tsgId='+$("#tsgId").val(),'新增模板',700,390);
	});
	$('.alert_sms_temp_set_c').click(function(){
		var id = $(this).attr("id");
		pubDivShowIframe('sms_temp_set_b',ctx+'/sys/smsTemp/reditTempPage.do?id='+id+'&v='+new Date().getTime(),'修改模板',700,390);
	});

	// 左侧短信模板 页面效果
	$('.hyx-hsm-left').find('dd span').each(function(){
    	$(this).parent().siblings().hover(function(){
    		$(this).addClass("dd-hovers");
    	},function(){
    		$(this).removeClass("dd-hovers");
    	});
    	$(this).click(function(){
    		var tsgId = $(this).attr("tsgId");
    		$(this).parent().addClass("dd-hover");
    		$(this).parent().siblings().removeClass("dd-hover");
    		document.location.href = "${ctx }/sys/smsTemp/smsTempList?tsgId="+tsgId;	
    	});
    	$(this).parent().hover(function(){
    		$(this).find(".reso-grou-edit").show();
    		$(this).find(".reso-grou-dele").show();
    		
    	},function(){
    		$(this).find(".reso-grou-edit").hide();
    		$(this).find(".reso-grou-dele").hide();
    		/*$(".reso-grou-edit").hide();
    		$(".reso-grou-dele").hide();*/
    	});
    	
    });
});

// 编辑 模板分类
function update_temp_group(id){
	pubDivShowIframe('sms_temp_set_a',ctx+'/sys/smsTemp/reditTempGroupPage.do?tsgId='+id+'&v='+new Date().getTime(),'编辑分类',300,130);
}

// 删除模板分类
function del_temp_group(id){
	queDivDialog("remove_temp_group","是否删除？",function(){		
		$.ajax({
			url:ctx+'/sys/smsTemp/removeSmsTempGroup',
			type:'post',
			data:{tsgId:id},
			dataType:'json',
			error:function(){alert('网络异常，请稍后再试！')},
			success:function(data){
				if(data == 0){
					window.top.iDialogMsg("提示",'删除成功！');
					setTimeout('document.forms[0].submit()',1000);
				}else{
					window.top.iDialogMsg("提示",'删除失败！');
				}
			}
		});
	});
}

//删除模板
function del_temp(id){
	queDivDialog("remove_temp","是否删除？",function(){		
		$.ajax({
			url:ctx+'/sys/smsTemp/removeSmsTemp',
			type:'post',
			data:{id:id},
			dataType:'json',
			error:function(){alert('网络异常，请稍后再试！')},
			success:function(data){
				if(data == 0){
					window.top.iDialogMsg("提示",'删除成功！');
					setTimeout('document.forms[0].submit()',1000);
				}else{
					window.top.iDialogMsg("提示",'删除失败！');
				}
			}
		});
	});
}
</script>

</head>
<body> 
<div class="hyx-sts">
	<div class="hyx-hsm-left fl_l" style="overflow-y:auto">
		<dl>
			<dt>模板分类</dt>
			<dt style="background:#f4f4f4;"><a class="allo-new-group alert_sms_temp_set_a" href="javascript:;"><span style="margin-left:-10px;font-size:14px;">+</span>新建分类</a></dt>
			<dd class="${empty tsgId?'dd-hover':'' }" title="全部短信(${allCount})" style="text-indent: 1em;"><span tsgId="" >全部短信(${allCount})</span></dd>
			<c:forEach items="${smsTempGroups }" var="group">
				<dd class="${group.tsgId eq tsgId?'dd-hover':''}" style="text-indent: 1em;"><span tsgId="${group.tsgId }"  title="${group.groupName }(${group.counts})">${group.groupName }(${group.counts})</span><a href="javascript:;" title="删除" onclick="del_temp_group('${group.tsgId}')" class="reso-grou-dele min-dele fl_r"></a><a href="javascript:;" title="编辑"  onclick="update_temp_group('${group.tsgId}')" class="reso-grou-edit min-edit fl_r"></a></dd>
			</c:forEach>
		</dl>
	</div>
	<div class="hyx-sts-right fl_l">
		<input type="hidden" id="tsgId" value="${tsgId}">
		<div class="com-btnlist fl_l">
			<a href="javascript:;" class="com-btna alert_sms_temp_set_b fl_l"><i class="min-new-add"></i><label class="lab-mar">新&nbsp;&nbsp;&nbsp;&nbsp;增</label></a>
		</div>
		<ul class="list fl_l">
			<c:forEach items="${smsTemps }" var="temp" varStatus="v">
				<li class="bortop">
				   <span class="fl_l" style="display:inline-block;margin-left:215px;">${v.count }、</span><pre style="float:left;display:inline-block;width:80%;line-height:20px;white-space:pre-wrap;word-wrap:break-word;text-align:left;">${temp.content }</pre>
					<label class="lab fl_r">
						<a href="javascript:;" class="alert_sms_temp_set_c" id="${temp.id}">修改</a>
						<a href="javascript:;" onclick="del_temp('${temp.id}')">删除</a>
					</label>
				</li>
			</c:forEach>			
		</ul>
	</div>
</div>
<form action="${ctx }/sys/smsTemp/smsTempList" method="post"></form>
</body>
</html>