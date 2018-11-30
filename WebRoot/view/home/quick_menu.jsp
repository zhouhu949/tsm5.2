<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理者首页</title>
   <%@ include file="/common/common.jsp"%>
	<!--换肤样式-->
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/skin/default/color.css${_v}">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/js/form/skins/all.css${_v}">
    <link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css" >
    <script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js${_v}" dialog-theme="default"></script><!--可移动弹框插件-->
	<script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js${_v}"></script><!--可移动弹框插件公共js-->
	<script type="text/javascript" src="${ctx}/static/js/form/icheck.js${_v}"></script><!--表单优化-->
	<script type="text/javascript" src="${ctx}/static/js/form/js/custom.min.js${_v}"></script><!--表单优化-->
    <script type="text/javascript">
	$(function(){
		/*表单优化*/
	    $('input[type=checkbox]').iCheck({
	        checkboxClass: 'icheckbox_minimal',
	        radioClass: 'iradio_minimal',
	        increaseArea: '20%'
	    });
		
		$("input[id^=sel_quick_]").on("ifChecked",function(){
			var resourceId = $(this).attr("id").split("_")[2];
			var text = $(this).parent().next().text();
			var html = '<input type="checkbox" id="quick_menu_'+resourceId+'" checked/>';
			html+='<span class="menu-name fl_l">'+text+'</span>';
			$("#quickMenuP").append(html);
			$('#quick_menu_'+resourceId).iCheck({
		        checkboxClass: 'icheckbox_minimal',
		        radioClass: 'iradio_minimal',
		        increaseArea: '20%'
		    });
			$('#quick_menu_'+resourceId).on("ifUnchecked",function(event){
				var resourceId = $(this).attr("id").split("_")[2];
				$(this).parent().next().remove();
				$(this).parent().remove();
				$("#sel_quick_"+resourceId).iCheck("uncheck");
				$("input[id^=sel_quick_]").each(function(){
					if(!$(this).is(":checked")){
						$(this).iCheck("enable");
					}
				});
			});
			if($("input[id^=quick_menu_]").length == 6){
				$("input[id^=sel_quick_]").each(function(){
					if(!$(this).is(":checked")){
						$(this).iCheck("disable");
					}
				});
			}
		});
		
		$("input[id^=sel_quick_]").on("ifUnchecked",function(){
			var resourceId = $(this).attr("id").split("_")[2];
			$("#quick_menu_"+resourceId).parent().next().remove();
			$("#quick_menu_"+resourceId).parent().remove();
			$("input[id^=sel_quick_]").each(function(){
				if(!$(this).is(":checked")){
					$(this).iCheck("enable");
				}
			});
		});
		
		$("input[id^=quick_menu_]").on("ifUnchecked",function(){
			var resourceId = $(this).attr("id").split("_")[2];
			$(this).parent().next().remove();
			$(this).parent().remove();
			$("#sel_quick_"+resourceId).iCheck("uncheck");
			$("input[id^=sel_quick_]").each(function(){
				if(!$(this).is(":checked")){
					$(this).iCheck("enable");
				}
			});
		});
		
		$("#closeBtn").click(function(){
			closeParentPubDivDialogIframe('quick_menu');
		});
		
		$("#saveBtn").click(function(){
			var ids='';
			$("input[id^=quick_menu_]").each(function(){
				if(!$(this).is(":disabled")){
					if(ids == ''){
						ids+=$(this).attr("id").split("_")[2];
					}else{
						ids+=','+$(this).attr("id").split("_")[2];
					}
				}
			});
			$.ajax({
				url:ctx+'/main/sale/saveQuickMenu',
				type:'post',
				data:{ids:ids},
				dataType:'json',
				error:function(){},
				success:function(data){
					if(data == '1'){
						window.top.iDialogMsg('提示','保存快捷菜单成功!');
						setTimeout('window.parent.location.reload();',1000);
					}else{
						window.top.iDialogMsg('提示','保存快捷菜单失败!');
					}
				}
			});
		});
	});
	window.onload=function(){
		if($("input[id^=quick_menu_]").length == 6){
			$("input[id^=sel_quick_]").each(function(){
				if(!$(this).is(":checked")){
					$(this).iCheck("disable");
				}
			});
		}
	}
	</script>
    <style>
		.second-menu span{*margin-top:3px;}
	</style>
</head>
<body style="overflow-x:hidden;*width:590px">
	<div class="edit-short-func-btn clearfix">
		<p id="quickMenuP" class="second-menu clearfix" style="width:520px;*width:540px;*height:auto;line-height:auto;">
			<c:forEach items="${quickList }" var="quick">
				<input type="checkbox" id="quick_menu_${quick.resourceId }" checked />
				<span class="menu-name fl_l">${quick.resourceName }</span>
			</c:forEach>
		</p>
	</div>
	<p class="list-title"><span>权限列表</span></p>
	<div class="edit-short-func-list">
		<div class="list-short-box">
			<div class="all-menu">
				<c:forEach items="${listtree }" var="tree" varStatus="vs">
					<c:choose>
						<c:when test="${tree.resourceName eq '首页' || tree.resourceName eq '消息中心' }"></c:when>
						<c:when test="${tree.resourceName eq '淘客户' || tree.resourceName eq '审核中心' }">
							<div class="first-menu skin-minimal clearfix">
								<span class="fl_l">${tree.resourceName }</span>
							</div>
							<div class="second-menu skin-minimal clearfix">
								<input type="checkbox" id="sel_quick_${tree.resourceId }"${tree.isUserQuick eq 1 ? ' checked' : '' }/>
								<span class="menu-name fl_l">${tree.resourceName }</span>
							</div>
						</c:when>
						<c:otherwise>
							<c:set var="childList" value="${tree.childList }"/>
							<div class="first-menu skin-minimal clearfix">
								<span class="fl_l">${tree.resourceName }</span>
							</div>
							<c:if test="${!empty childList }">
								<div class="second-menu skin-minimal clearfix">
									<c:forEach items="${childList }" var="tree1" varStatus="vs1">
										<input type="checkbox" id="sel_quick_${tree1.resourceId }"${tree1.isUserQuick eq 1 ? ' checked' : '' }/>
										<span class="menu-name fl_l">${tree1.resourceName }</span>
									</c:forEach>
								</div>
							</c:if>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</div>
	</div>
	<div class='bomb-btn'>
		<label class='bomb-btn-cen'>
			<a href="###" id="saveBtn" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>保存</label></a>
			<a href="###" id="closeBtn" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
		</label>
	</div>
</body>
</html>