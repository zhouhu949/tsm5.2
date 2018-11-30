<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css">
<!--主要样式-->
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/static/js/idialog/skins/iblue.css"> --%>
<script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
<script type="text/javascript">
	$(function() {


	$("input:text:first").focus()
		/*
		.each(function(item,obj){
 	   //if($(obj).attr("disabled")!="disabled"){
  		  $(obj).focus();
 	  // }
 	})
 	*/
	
		var mark = true;
		$("#btn_save").click(function() {
			if (mark) {
				saveData();
			} 
		});

		$("#cancel").click(function() {
			parent.closePubDivDialogIframe("addOrUpdateGroup");
		});

	});

	function saveData() {
		var groupName = $("#groupName").val();
		var oldGroupName = $("#oldGroupName").val();
		var groupSort=$("#groupSort").val();
		if (groupName == '') {
			window.top.iDialogMsg("提示", '分类名称不能为空');
			$("#groupName").focus();
			return false;
		}
	/* 	if (groupName == oldGroupName) { // 名称未修改
			window.top.iDialogMsg("提示", '名称未修改');
			return false;
		} */
		var pattern = /[^\x00-\xff]/g;
		if (groupName.replace(pattern, "gg").length > 20) {
			window.top.iDialogMsg("提示", '分类名称不能超过十个字');
			$("#groupName").focus();
			return false;
		}
		 if (checkNull(groupSort)) {
			window.top.iDialogMsg("提示", '排序值不为空');
			return false;
	    }
	    if (!(/^[1-9]\d*$/.test(groupSort))) {
			window.top.iDialogMsg("提示", '排序值只能为正整数');
			return false;
	    }
		$.ajax({
			url:ctx+"/sys/knowlege/sortIsOK",
			data:{
			    "id":$('input[name="groupId"]').val(),
				"groupSort":groupSort
			},
			complete:function(XMLHttpRequest){
	   			mark=true;
	   		},
			success:function(data){
				if(data.status){
					if(data.megType == 0){//如果megType为0 说明排序值可用
						$("#myForm").ajaxSubmit({
							url : '${ctx}/sys/knowlege/saveOrUpdateKnowlegeGroup',
							type : 'post',
							error : function(XMLHttpRequest, textStatus) {
							},
							success : function(data) {
								if (data == -1) {
									$("#errMsg").html("名称已存在,请更换分类名称");
								} else {
									// 刷新主页面
									window.top.iDialogMsg("提示", '操作成功');
									setTimeout('parent.document.forms[0].submit()', 1000);
								}
							}
						});
					}else if(data.megType == 1){
						window.top.iDialogMsg("提示","排序值重复");
					}
				}else{
					window.top.iDialogMsg("提示", data.errorMsg);
					return false;
				}
			}
		})
	}
	
		function checkNull(str) {
		if (str == '' || trim(str) == '')
				return true;
		}
		function trim(str) {
			var re = /(^\s*)|(\s*$)/g;
			return str.replace(re, '');
		}
</script>
</head>
<body>
	<form method="post" id="myForm" name="myForm"
		action="${ctx}/sys/knowlege/saveOrUpdateKnowlegeGroup">
		<input type="hidden" id="ctPath" value="${ctx}" /> 
		<input type="hidden" name="groupId" value="${knowlegeGroup.groupId}" /> 
		<input type="hidden" id="oldGroupName" value="${knowlegeGroup.groupName}" />
		<div class="reso-new-group fl_l">
			<label for="">分类名称：</label> <input type="text" id="groupName" name="groupName" value="${knowlegeGroup.groupName}" maxlength="20">
		</div>
		<p class="warn-deco fl_l">
			<span clas="font-color" id="errMsg"></span>
		</p>
		<div class="reso-new-group fl_l">
			<label for="">排序值：</label> <input type="text" id="groupSort" name="groupSort" value="${knowlegeGroup.groupSort}" maxlength="">
		</div>
		<p class="warn-deco fl_l">
			<span clas="font-color" id="errMsg-num"></span>
		</p>
		<div class="bomb-btn">
			<label class="bomb-btn-cen">
			    <a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="javascript:;" id="btn_save"><label>确定</label></a>
			    <a class="com-btna bomp-btna cancel-btn fl_l" href="javascript:;" id="cancel"><label>取消</label></a>
			</label>
		</div>
	</form>
</body>
</html>