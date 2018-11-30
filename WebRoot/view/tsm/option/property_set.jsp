<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>系统设置-系统属性设置</title>

<style>
	.tree-node{width:240px;}
	.hyx-hsm-left dl{text-align:left;}
	.hyx-hsm-left dl dt,.hyx-hsm-left dl dd{text-indent:30px;}
</style>

<script type="text/javascript">
$(function(){
    /*左边铺满整屏*/
		var winhight=$(window).height();
		$(".hyx-dfpzy-reso").height(winhight-5);
	/*左边选择样式切换*/
    $('.hyx-hsm-left').find('dd').each(function(){
    	$(this).click(function(){
    		$(this).addClass("dd-hover");
    		$(this).siblings().removeClass("dd-hover");
    	});
    	$(this).hover(function(){
    		$(this).find(".reso-grou-edit").show();
    		$(this).find(".reso-grou-dele").show();

    	},function(){
    		$(this).find(".reso-grou-edit").hide();
    		$(this).find(".reso-grou-dele").hide();
    	});

    });
    /*iframe切换*/
    $('.hyx-hsm-left').find('dd').click(function(){
    	$(this).parent().parent().parent().find('iframe').attr('src',$(this).attr('src'))
    });
});

function a_add(){
	pubDivShowIframe('alert_sys_pro_set_a',ctx+'/opt/set/propertyset_pro_modfiy.do?v='+new Date().getTime(),'新增产品信息',330,360);
}

function a_update(id){
	pubDivShowIframe('alert_sys_pro_set_a',ctx+'/opt/set/propertyset_pro_modfiy.do?id='+id+'&isMark=0&v='+new Date().getTime(),'修改产品信息',330,390);
}

function d_update(id){
	pubDivShowIframe('alert_sys_pro_set',ctx+'/opt/set/propertyset_giveUp_modfiy.do?id='+id+'&isMark=0&v='+new Date().getTime(),'增加子项',560,250);
}

function c_add_tag(code,groupId){
	var url = ctx+'/opt/set/propertyset_modfiy_tag?code='+code+'&groupId='+groupId;
	pubDivShowIframe('alert_sys_set',url,'新增数据项',330,250);
}

function c_update_tag(id,code){
	var url = ctx+'/opt/set/propertyset_modfiy_tag.do?id='+id+'&code='+code+'&v='+new Date().getTime()
	pubDivShowIframe('alert_sys_set',url,'数据项编辑',330,250);
}

function c_add(code,type){
	var url = ctx+'/opt/set/propertyset_modfiy.do?code='+code;
	if(type == "multi"){
		url = ctx+'/opt/set/propertyset_modfiy_multi.do?code='+code;
	}
	pubDivShowIframe('alert_sys_set',url,'新增数据项',330,250);
}

function c_add_process(code){
	pubDivShowIframe('alert_sys_set',ctx+'/opt/set/propertyset_modfiy_process.do?code='+code,'新增销售进程',330,230);
}
function c_update_process(id){
	pubDivShowIframe('alert_sys_set',ctx+'/opt/set/propertyset_modfiy_process.do?id='+id+'&v='+new Date().getTime(),'数据项编辑',330,210);
}

function c_add_group(code){
	pubDivShowIframe('alert_sys_set_group',ctx+'/opt/set/group/modfiyPage?code='+code,'新增分组',330,230);
}

function c_update_group(id,code){
	pubDivShowIframe('alert_sys_set_group',ctx+'/opt/set/group/modfiyPage?code='+code+'&groupId='+id,'编辑分组',330,230);
}

function c_change_group(ids,code){
	pubDivShowIframe('alert_sys_set_group',ctx+'/opt/set/group/toChangeGroup?ids='+ids+'&itemCode='+code,'变更分组',330,230);
}

function c_update(id,type){
	var url = ctx+'/opt/set/propertyset_modfiy.do?id='+id+'&v='+new Date().getTime()
	if(type == "multi"){
		url = ctx+'/opt/set/propertyset_modfiy_multi.do?id='+id+'&v='+new Date().getTime()
	}
	pubDivShowIframe('alert_sys_set',url,'数据项编辑',330,210);
}

function delete_(data,type){
	url = ctx+'/opt/set/optionDel';
	if(type == "pro"){
		url = ctx+'/opt/set/proDel';
	}
	queDivDialog("res_remove_cust","删除该数据项，将导致客户数据中有关该数据项的值丢失，请确认是否删除！",function(){
		$.ajax({
			url:url,
			type:'post',
			data:data,
			dataType:'json',
			error:function(){alert('网络异常，请稍后再试！')},
			success:function(data){
				if(data == 0){
					window.top.iDialogMsg("提示",'删除成功！');
					setTimeout('window.frames[0].document.forms[0].submit();',1000);
				}else{
					window.top.iDialogMsg("提示",'删除失败！');
				}
			}
		});
	});
}

function delete_group_(data){
	url = ctx+'/opt/set/group/remove';
	queDivDialog("group_remove_cust","删除该分组将自动把分组下的行动标签转移至未分组，是否继续？",function(){
		$.ajax({
			url:url,
			type:'post',
			data:data,
			dataType:'json',
			error:function(){alert('网络异常，请稍后再试！')},
			success:function(data){
				if(data == 0){
					window.top.iDialogMsg("提示",'删除成功！');
					setTimeout('window.frames[0].document.forms[0].submit();',1000);
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
<div class="hyx-dfpzy-reso hyx-layout" style="width:100%;position:relative;">
	<div class="hyx-hsm-left hyx-layout-side">
		<dl>
			<dt>销售信息设置</dt>
			<dd class="dd-hover"  src="${ctx}/opt/set/propertyset_pro">销售产品维护</dd>
			<dd name="b" src="${ctx}/opt/set/propertyset_option.do?itemCode=SALES_10001">销售进程设计</dd>
			<dd name="c" src="${ctx}/opt/set/propertyset_option.do?itemCode=SALES_10002">目标客户分类</dd>
			<dd name="g" src="${ctx}/opt/set/propertyset_option.do?itemCode=SALES_10010">行动标签维护</dd>
			<dt>客户属性设置</dt>
			<c:choose>
			 	<c:when test="${!empty multiList}">
			 		<c:forEach items="${multiList }" var="entity" varStatus="v">
			 			<dd name="${entity.fieldCode }" src="${ctx}/opt/set/propertyset_option?itemCode=${entity.fieldCode}">${entity.fieldName }</dd>
			 		</c:forEach>
			 	</c:when>
			 </c:choose>
			<dt>其他信息设置</dt>
			<dd name="d" src="${ctx}/opt/set/propertyset_giveUp">客户放弃原因</dd>
			<dd name="e" src="${ctx}/opt/set/propertyset_option.do?itemCode=SALES_40001">流失客户原因</dd>
			<dd name="f" src="${ctx}/opt/set/propertyset_option.do?itemCode=RECORD_1000">录音范例分类</dd>
			<dd name="h" src="${ctx}/opt/set/propertyset_option.do?itemCode=SALES_10011">客户等级维护</dd>
			<dd name="i" src="${ctx}/opt/set/propertyset_option.do?itemCode=SALES_10012">服务标签维护</dd>
			<dd name="j" src="${ctx}/opt/set/propertyset_option.do?itemCode=SALES_CLUE_TYPE">线索类型设置</dd>
		</dl>
	</div>
	<div class="hyx-hsm-right hyx-layout-content" style="border:none;">
		<iframe src="${ctx}/opt/set/propertyset_pro" width="100%" height="100%" id="iframepage" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
	</div>
</div>
</body>
</html>
