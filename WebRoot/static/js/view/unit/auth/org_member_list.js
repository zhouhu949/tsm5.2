/***********************************************
 * 成员列表_专属JS文件
 * @author haoqj
 * @date 2013-10-12
 ***********************************************/

$(function(){
	// 新增分组
	$("#groupAdd").click(function(){
		addOrEditGroup('');
	});
	
	// 查询
	$("#btn_find").click(function(){
		$("#groupId").val("");
		loadPage(2);
	});
	
	// 编辑角色
	$("#editRole").click(function(){
		document.forms[1].reset();
		batchOpreat(1);
	});
	
	// 设置共享范围
	$("#setShareGroup").click(function(){
		document.forms[2].reset();
		batchOpreat(2);
	});
	
	// 更换分组
	$("#changeGroup").click(function(){
		document.forms[3].reset();
		batchOpreat(3);
	});

	// 批量修改密码
	$("#batchUpdatepwd").click(function(){
		batchOpreat(4);
	});
	// 本地同步单位和用户信息
	$("#sysncId").click(function(){
		if(confirm("确认同步单位和用户信息")){
			var orgId =  $('#orgId').val();
			var url = $("#base").val()+'/auth/unitAndUser/sysncUnitAndUser.do';
			$.ajax({
				url : url,
				type : 'post',
				data:{'orgId':orgId},
				dataType : 'json',
				error : function(XMLHttpRequest, textStatus){},
				success : function(data){
					if(data.code=='0'){
						dialogMsg(0, data.msg);
						setTimeout(function(){
							loadPage(2);
						},3000);
					}else{
						dialogMsg('-1', data.msg);
					}
				}
			});
		}else{
			   return;
		}

	});
	
	// 成员列表全选
	$("#allSel").click(function() { 
	    var isSelected = $("#allSel").attr("checked")=="checked"?true:false;
	    $("input[isSel='1']").each(function(){
	        $(this).attr("checked", isSelected);
	    });
	});
	
	// 左侧团队分组树处理
	$("li[id^='groupP_']").each(function() {
		var groupId = $(this).attr("id").substring(7);
		var findTag = $("#find_"+groupId);
		var editTag = $("#edit_"+groupId);
		var deleteTag = $("#delete_"+groupId);
		
		$(this).mouseenter(function(){
            editTag.attr("class" ,'edit'); 
            deleteTag.attr("class" ,'del2'); 
		}).mouseleave(function(){
			editTag.attr("class" ,''); 
            deleteTag.attr("class" ,'');
		});
		
		// 编辑图标点击
		editTag.click(function(){addOrEditGroup(groupId);});
		// 删除图标点击
		deleteTag.click(function(){delGroup(groupId);});
		// 查找点击
		findTag.click(function(){findMembers(groupId)});
	});
});

function findMembers(groupId){
	$("#groupId").val(groupId);
	loadPage(2);
}

/**
 * 新增或修改团队分组
 */
function addOrEditGroup(groupId){
	var tmpTitle = groupId==''?'新增分组':'修改分组';
	editDialogByUrl("popId",tmpTitle,"toOrgGroup.do?groupId="+groupId,true);
}

/**
 * 删除团队分组
 */
function delGroup(groupId){
	var del=function(){
		$.ajax({
			url : 'delOrgGroup.do?groupId=' + groupId,
			type : 'post',
			error : function(XMLHttpRequest, textStatus){},
			success : function(data){
				if(data == 0){
					// 刷新主页面
			   		loadPage(1);
				}
			}
		});
	}
	
	dialogMsg(2,'',del);
	
}

/**
 * 批量操作：编辑角色、共享范围设置、更换分组
 */
function batchOpreat(optType){
	var custIdCounts = $("input[name='memberAcc']:checked").length;
		if(custIdCounts==0){
			dialogMsg(1,'');
			return false;
		}
		
		var accs = "";
		var names = "";
		var userIds = "";
		$("input[name='memberAcc']:checked").each(function(){
			accs += $(this).val() + ",";
			names += $(this).attr("memberName") + ",";
			userIds += $(this).attr("memberId") + ",";
		});
		
		
		if(optType == 1){
			editRole(userIds);
		}else if(optType == 2){
			setShareGroup(accs);
		}else if(optType == 3){
			changeGroup(accs,names);
		}else if(optType == 4){
			updPwd(accs);
}		
}

function editRole(userIds){
	editDialogByDiv('popId','角色编辑',true,$("#editRoleDiv").html());
	
	$(".pop_cancel").click(function(){
		closeDialog('popId');
	});
	
	$("#btn_editRole").click(function(){
		var roleId = $("#newRoleId").val();
		
		$(this).attr("disabled",true);
	    $.ajax({
			url : 'editRoleBatch.do',
			data:{'roleId':roleId,
				  'memberIds':userIds},
			type : 'post',
			success : function(data) {
				if(data == 0){
					closeDialog('popId');
	   				loadPage(2);
				}
			}
		});
	});
}

function setShareGroup(accounts){
	editDialogByDiv('popId','共享范围设置',true,$("#setShareGroupDiv").html());
	
	$(".pop_cancel").click(function(){
		closeDialog('popId');
	});
	
	$("#btn_setShareGroup").click(function(){
		$("#setShareGroupDiv").html("");
		// 共享分组ID
		var shareGroupIds = '';
		$("input[name='shareGroupId']:checked").each(function(){
			shareGroupIds += $(this).val() + ",";
		});
		
		$(this).attr("disabled",true);
	    $.ajax({
			url : 'setShareGroups.do',
			data:{'memberAccs':accounts,
				  'shareGroupIds':shareGroupIds},
			type : 'post',
			success : function(data) {
				if(data == 0){
	   				closeDialog('popId');
					loadPage(2);
				}
			}
		});
	});
	
	// 共享分组全选
	$("#groupsSelect").click(function() { 
		var isSelected = $("#groupsSelect").attr("checked")=="checked"?true:false;
		$("input[name='shareGroupId']").each(function() {
			$(this).attr("checked", isSelected);
		});
	});
}

function changeGroup(accounts,names){
	editDialogByDiv('popId','更换分组',true,$("#changeGroupDiv").html());
	
	$(".pop_cancel").click(function(){
		closeDialog('popId');
	});
	
	$("#btn_changeGroup").click(function(){
		var groupId = $("#groupId").val();
		var newGroupId = $("#newGroupId").val();
		// 未作更改时返回并刷新主页面
	    if(groupId==newGroupId){
	    	closeDialog('popId');
	    	loadPage(2);
	    }
	    
	    $(this).attr("disabled",true);
	    $.ajax({
			url : 'changeGroup.do',
			data:{'groupId':groupId,
				  'newGroupId':newGroupId,
				  'memberAccs':accounts,
				  'memberNames':names},
			type : 'post',
			success : function(data) {
				if(data == 0){
					closeDialog('popId');
					loadPage(2);
				}
			}
		});
	});
}

/**
 * 成员编辑
 * @param memberAcc
 */
function openMemberEdit(memberAcc){
	editDialogByUrl('popId','成员编辑','toMemberEdit.do',true,'memberAcc',memberAcc);
}

/**
 * 成员密码修改
 * @param memberAcc
 */
function updPwd(memberAcc){
	editDialogByUrl('popId','密码重置','toMemberPwdEdit.do',true,'memberAcc',memberAcc);
	
}