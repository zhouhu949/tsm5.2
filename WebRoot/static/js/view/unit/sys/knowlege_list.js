/*******************************************************************************
 * 公司资源_专属JS文件
 * 
 * @author wuwei
 * @date 2013-10-23
 ******************************************************************************/
$(function() {
	var _ctPath = $("#ctPath").val();
	helpMsgShow("mgquestionId","问题关键字");
	//查看
	$("a[id^='queryKnowlegE_']").each(function() {
		var knowlegeId = $(this).attr("id").substring(14);
		var findTag = $("#queryKnowlegE_" + knowlegeId);
		// 查找点击
		findTag.click(function() {
			getknowlege(knowlegeId);
		});
	});
	
	// 新增分组
	$("#knowlegeAdd").click(function() {
		addOrEditGroup('');
	});


	/**
	 * 查看知识
	 */
	function getknowlege(knowlegeId){
		editDialogByUrl("getknowlege",'问题详细',ctx + "/sys/knowlege/knowlege_search",true,"knowlegeId",knowlegeId);
	}
	
	/**
	 * 新增或修改团队分组
	 */
	function addOrEditGroup(groupId){
		var tmpTitle = groupId==''?'新增分类':'修改分类';
		editDialogByUrl("againassginCustId",tmpTitle,ctx + "/sys/knowlege/toGroup",true,"groupId",groupId);
	}

	// 查询
	$("#queryBtn").click(function() {
		$("#groupId").val("");
		loadPage(2);
	});
	

	// 设置全选及撤销全选
	$("#checkAll").click(function() {
		// 复选框全选
		if ($(this).is(":checked")) {
			$("input[name='selectboxs']").each(function() {
				$(this).attr("checked", true);
			});
		} else {
			// 复选框取消全选
			$("input[name='selectboxs']").each(function() {
				$(this).attr("checked", false);
			});
		}
	});

	/**
	 * 删除
	 */
	$("#delBtn").click(function() {
		var custIdCounts = $("input[name='selectboxs']:checked").length;
		if (custIdCounts == 0) {
			$.dialog({
				lock : true,
				background : '#F0F0F0',
				opacity : 0.50,
				ok : true,
				icon : 'warning',
				content : '请先选择！',
				title : '提示'
			});

			return false;
		}
		var ids = "";
		$("input[name='selectboxs']:checked").each(function() {
			ids += $(this).val() + ",";
		})
		$.dialog({
			lock : true,
			background : '#F0F0F0',
			opacity : 0.50,
			ok : function() {
				document.forms[0].action = "/sys/knowlege/knowlege_del?ids=" + ids;
				document.forms[0].submit();
			},
			cancel : true,
			icon : 'question',
			title : '确认',
			content : '确认要删除吗？'
		});
	});
	// 左侧团队分组树处理
	$("li[id^='groupP_']").each(function() {
		var groupId = $(this).attr("id").substring(7);
		var findTag = $("#find_" + groupId);
		var editTag = $("#edit_" + groupId);
		var deleteTag = $("#delete_" + groupId);

		$(this).mouseenter(function(){
            editTag.attr("class" ,'edit'); 
            deleteTag.attr("class" ,'del'); 
		}).mouseleave(function(){
			editTag.attr("class" ,''); 
            deleteTag.attr("class" ,'');
		});
		
		// 编辑图标点击
		editTag.click(function() {
			addOrEditGroup(groupId);
		});
		// 删除图标点击
		deleteTag.click(function() {
			delGroup(groupId);
		});
		// 查找点击
		findTag.click(function() {
			$("#groupId").val(groupId);
			queryKnowlege();
		});
	});
	
	function queryKnowlege(){
		loadPage(2);
	}
	/**
	 * 删除团队分组
	 */
	function delGroup(groupId) {
		var delUrl = ctx + "/sys/knowlege/knowlegeGroup_del";
		$.dialog({
			ok : function() {
				$.ajax({
					url : delUrl + '?groupId=' + groupId,
					type : 'post',
					error : function(XMLHttpRequest, textStatus) {
					},
					success : function(data) {
						if (data == 0) {
							// 刷新主页面
							loadPage(2);
						}
					}
				});
			},
			cancel : true,
			icon : 'question',
			title : '确认',
			content : '确定要删除吗？'
		});
	}
	
	//添加知识
	$("#addknowlegeId").click(function(){
			window.location.href= ctx + "/sys/knowlege/toSaveOrUpdate";
		});
	
	/**
	 * 保存
	 */
	$("#btn_save").click(function() {
		var chk = checkForm();
		if (chk && flg) {
			$("#btn_save").attr("disabled","disabled");
			document.forms[0].action = "addCustomer.do";
			document.forms[0].submit();
			toAlert("保存成功！");
		}
		
	});
	
});

function editKnowlege(knowlegeId){
	window.location.href= $("#ctPath").val() + "/sys/knowlege/toSaveOrUpdate?knowlegeId="+knowlegeId;
}
