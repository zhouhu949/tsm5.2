<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css${_v}"><!--主要样式-->
<script type="text/javascript" src="${ctx}/static/js/common/common_check_4.js${_v}"></script>
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/roleOrgmem/mem_edit.css${_v}"><!--主要样式-->
<!--[if IE]>
<style>
.mginLE5{margin-left:4px;}
</style>
<![endif]-->
  <form id="myForm_edit" method="post">
  		<input type="hidden" name="userAccount" value="${memberInfo.userAccount }"> <!-- 账号 -->
		<input type="hidden" name="userId" value="${memberInfo.userId }"> <!-- 账号ID -->
		<input type="hidden" name="roleId" id="roleId" value="${memberInfo.roleId }" /><!-- 角色ID -->
		<input type="hidden" id="groupId" name="groupId" value="${memberInfo.groupId }"><!-- 分组ID -->
		<input type="hidden" name="newShareGroupIds" id="newShareGroupIds" value="${orgGroups}"><!-- 管理部门ID集合 -->
		<input type="hidden" name="oldName" id="oldName" value="${memberInfo.userName}"><!-- yuanl -->
  		<div id="content-dom-demo" class="memb-edit-info" >
  			<div class="memb-edit-overflowbox">
	  		<!-- 编辑成员 -->
	  				<p class="shows-edit-title">编辑成员</p>
					<p>
						<label for="">帐号：</label><span class="marginright60">${memberInfo.userAccount }</span>
						<label for="">所在部门：</label><span>${memberInfo.groupName }</span>
					</p>
	
					<div class="notinqu">
						<label for="" class="mginLE5" ><i class="irequired">*</i>成员姓名：</label><input name="name" id="name" type="text" maxlength="20" checkProp="chk_1_1" value="${memberInfo.userName}" class="w nobuyao"/><span class="error" id="error_name" style="width:auto;"></span>
						<label for="">性别：</label> 
						<select name="sex">
							<option value="0">-请选择-</option>
							<option value="1" ${memberInfo.sex eq 1 ? 'selected' : '' }>男</option>
							<option value="2" ${memberInfo.sex eq 2 ? 'selected' : '' }>女</option>
						</select>
					</div>
					<p>
						<label for="">手机号码：</label><input type="text" name="verifyMobile" id="verifyMobile" checkProp="chk_1_06" value="${memberInfo.mobile }" class="w"  readonly/><span class="error" id="error_verifyMobile" style="width:auto;"></span>
						<label for="">电子邮件：</label><input type="text" checkProp="chk_1_05" name="verifyMailbox"  id="verifyMailbox" value="${memberInfo.email }" class="moble-input nobuyao" /><span class="error" id="error_verifyMailbox" style="width:auto;"></span>
					</p>
					<p>
						<label for="">职务：</label><input type="text"  maxlength="20" name="duty" value="${memberInfo.post }" class="w bianKuan" />
						<label for=""  class="mginLE5">出生年月：</label><span class="com-form-data">
						<input type="text" onclick="WdatePicker({maxDate:'%y-%M-%d'})"
							name="birthday" value="<fmt:formatDate value="${memberInfo.birthday}" pattern="yyyy-MM-dd"/>"  class="com-form-time w"> <i></i></span>
					</p>
					<%-- <p style="padding-top:0;"><label for="">用户姓名：</label><span>${memberInfo.userName }</span></p> --%>
	
					<!-- 角色编辑 -->
					<!-- <p class="shows-edit-title margintop25">角色编辑</p> -->
						<%-- <p style="padding-top:0;"></p> --%>
					<div  class="com-search01 clearfix role-edit-div" style="position:relative;">
						<!-- 角色 -->
						<c:set var="roleName" value="角色" />
						<c:forEach items="${roles }" var="role">
							<c:if test="${role.roleId eq memberInfo.roleId  }">
								<c:set var="roleName" value="${role.roleName}" />
							</c:if>
						</c:forEach>
						<label class="fl_l">角色：</label>
						<dl class="select role-dl-style">
							<dt class="role-dt-style">${roleName}</dt>
							<dd>
								<ul>
									<c:forEach items="${roles }" var="role">
										<li <c:if test="${role.roleId eq memberInfo.roleId  }">class="active"</c:if>><a href="###" onclick="selectRole('${role.roleId}',this)"  data-roleType="${role.roleType }">${role.roleName }</a></li>
									</c:forEach>
								</ul>
							</dd>
						</dl>
	
						<label class="fl_l role-jurisdiction-label">管辖权限：</label>
						<input class="sub-dep-inpu role-jujurisdiction-input" type="text" id="showShareGroupNames">
						<div class="manage-drop fl_l" id="manage_drop" style="margin-left:319px;">
								<ul id="tt1" class="role-jujurisdiction-treeul">
	
								</ul>
								<div class="sure-cancle clearfix" style="width: 120px">
									<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l"
														href="javascript:;" id="checkedId"><label>确定</label></a>
									<a	class="com-btnd bomp-btna fl_l reso-sub-clear" id="clearId"
														href="javascript:;"><label>清空</label></a>
								</div>
						</div>
						<div class="role-change-tip com-red" style="position:absolute; left:344px; top:40px;line-height:12px; display: none;">请注意管辖权限的变更</div>
					</div>
					
					<p><label for="">通讯地址：</label><textarea cols="58" name="mailingAddress" maxlength="100" rows="7"  validate="chk_1_0_0" >${memberInfo.maiLingAddress }</textarea></p>
					
				</div>
				<div class="sure-cancle clearfix neworg-surbtn">
					<label class="bomb-btn-cen">
						<a href="javascript:;" class="com-btna bomp-btna com-btna-sty fl_l" onclick="submitForm();"><label>确定</label></a>
						<a href="javascript:;" class="com-btna bomp-btna fl_l" id='close02' onclick="close02();"><label>取消</label></a>
					</label>
				</div>
		</div>
  </form>

  <script type="text/javascript">
	  var on_off = false; // 开关，on_off为false时不执行onCheck事件，
	  var menu2step_ = 1; // 1: 关联子节点，2：关联父节点
  	$(function(){
  		/*表单优化*/
  		var groupId = $("#groupId").val();
		var url ="${ctx}/orgGroup/get_all_group_json.do";
		$("#tt1").tree({
			url:url,
			checkbox:true,
			onLoadSuccess:function(node,data){
				//处理选中
				var newShareGroupIds = $('#newShareGroupIds').val();
				var names = "";
				if(newShareGroupIds != null && newShareGroupIds.length > 0){
					$(newShareGroupIds.split(',')).each(function(index,data){
						var node = $("#tt1").tree("find",data);
						if(node != null){
							names+=node.text+';';
							$("#tt1").tree("check",node.target);
						}
					});
					$('#showShareGroupNames').val(names);
				}
				on_off = true;
			},
			//cascadeCheck: false,
            onCheck: function (node, checked) {
           // $("#tt1").tree("check",node.target).tree("expandTo",node.target);
            	/* if(on_off){
                    if (checked) {
                    	if(menu2step_ == 1){ // 关联子节点
                    		 var childNode = $("#tt1").tree('getChildren', node.target);
                             if (childNode.length > 0) {
                                 for (var i = 0; i < childNode.length; i++) {
                                     $("#tt1").tree('check', childNode[i].target);
                                 }
                             }else{
                            	 menu2step_ = 2; // 如果子节点没有了，就执行父节点
                            	 checkParent(node);
                             }
                    	}
                    } else {
                        var childNode = $("#tt1").tree('getChildren', node.target);
                        if (childNode.length > 0) {
                            for (var i = 0; i < childNode.length; i++) {
                                $("#tt1").tree('uncheck', childNode[i].target);
                            }
                        }
                    }
            	} */
            }
		});
		
		function checkParent(node) {
			var parentNode = $("#tt1").tree('getParent', node.target);
			if (parentNode != null) {
				$("#tt1").tree('check', parentNode.target);
				checkParent(parentNode);
			} else {
				menu2step_ = 1;
			}
		}


		//选择部门
		$("#checkedId").click(function() {
			var nodes = $('#tt1').tree('getChecked');
			var shares ="";
			var names ="";
			if(nodes != null){
				$(nodes).each(function(index,obj){
					shares+=obj.id.substring(0,obj.id.length)+',';
					names+=obj.text.substring(0,obj.text.length)+';';
				});
			}

            $('#showShareGroupNames').val(names);
            $('#newShareGroupIds').val(shares);
		});

		//清空
		$("#clearId").click(function(){
			var nodes = $('#tt1').tree('getChecked');
			 $(nodes).each(function(index,obj){
            	$('#tt1').tree('uncheck', obj.target);
            })
            $('#showShareGroupNames').val('');
			 $('#newShareGroupIds').val('');
		});
		
		$("#showShareGroupNames").focus(function(e){
			$(".role-change-tip").hide();
		});
  	});

  /** 选择角色 */
	function selectRole(roleId,obj){
		$("#roleId").val(roleId);
		var _this = $(obj);
		var _this_roleType = _this.attr("data-roleType");
		var _this_active_roleType = _this.parents("ul").find("li.active").find("a").attr("data-roleType");
		if(_this_roleType != _this_active_roleType){
			$(".role-change-tip").show();
		}
		_this.parents("ul").find("li").removeClass("active");
		_this.parent().addClass("active");
	}
	var isOpera = true; // 不可重复点击
	// 保存
  	function submitForm(){
		if(isOpera && checkForm()){
			isOpera = false;
			$('#myForm_edit').ajaxSubmit({
				url : '${ctx}/auth/user/memberEdit.do',
				type : 'post',
				error : function(XMLHttpRequest, textStatus){isOpera = true;alert("请求失败！");},
				success : function(data) {
					isOpera = true;
					if(data.status){
						window.top.iDialogMsg("提示",data.data);
						setTimeout('window.parent.frames[0].document.forms[0].submit();',3000);
						setTimeout('close02();',3000);
					}else{
						window.top.iDialogMsg("提示",data.errorMsg);
					}
				}
			});
		}

	}

  	// 取消
  	function close02(){
  		closeParentPubDivDialogIframe('mem_edit_');
  	}
  </script>
