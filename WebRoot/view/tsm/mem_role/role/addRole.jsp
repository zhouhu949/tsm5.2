<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>

<!--本页面样式-->
<link rel="stylesheet" href="${ctx}/static/js/stepbar/css/control.css" type="text/css" /><!--步骤-->
<link rel="stylesheet" href="${ctx}/static/js/stepbar/css/addRoleNew.css" type="text/css" /><!-- 新样式 -->
<!--本页面js-->

<%-- <script type="text/javascript" src="${ctx}/static/js/stepbar/js/stepbar.js"></script><!--步骤--> --%>
<style type="text/css">
	.tree-node{width: 370px;}
	.tree-title{width:300px;}
	.hyx-hsm-left dl dd span{text-indent:30px;}
</style>
</head>
<body>
<div class="">
	<div class="hyx-hsm-left fl_l">
		<dl>
			<dt>角色列表</dt>
			<p class="add-role" style="text-indent:30px;text-align:left;"><a class="add-role-new" href="${ctx}/auth/role/addRolePage"><span>创建角色</span><img src="${ctx }/static/images/add-role.png" alt=""></a></p> 
			<c:forEach items="${roles}" var="role">
				<dd title="${role.roleName }"><span class="fl_l">${role.roleName }</span>
					<a href="###" class="min-dele fl_r" onclick=" delete_('${role.roleId}','${role.roleName}')" title="删除"></a>
					<a class="min-edit fl_r" title="修改"  onclick=" jump_('${role.roleId}')" href="###"></a>
				</dd>	
			</c:forEach>
		</dl>
	</div>
	<div class="hyx-oral-right fl_l" style="padding-top:0;">
	<div class="role-new fl_l" style="width:100%;text-align:center;">	
	<div class="hyx-oral-right-title"><span>创建角色</span><div style="float:right;"><button class="hyx-right-title-btn stitle-btn-save" onclick="complete_()">保存</button></div></div>
			<form id="myForm_1" method="post">	
			<div class="role-edit-info" id="role-menu1">			  
					    <input type="hidden" id="old_roleName" value="${roleName}">
					    <input type="hidden" id="old_check" value="0">
					    <div class="addRoleTitle"><div class="xiaoduanshu"></div>新增角色名称</div>
					    <p class="clearfix newRoleBox">
						    	<label class="fl_l" for="">输入角色名：</label>
						    	<input class="inpu-role fl_l" id="roleName" name="roleName" type="text" value="${roleName}" maxlength="20" onblur="chekRolename_()">
					    	<p class="manag-role"><input type="radio" name="roleType" value="0"  ${roleType ne 1?'checked':''}><span>普通角色</span><input type="radio" name="roleType" value="1" ${roleType eq 1?'checked':''}><span>管理者角色</span></p>
					    </p>
			</div>
			<div class="func-menu" id="role-menu2" >
						<input type="hidden" id="mark" value="${mark }">
						<!-- 1:表示新增，2：表现修改 -->
						<input type="hidden" id="resourceIds" name="resourceIds"> <input
							type="hidden" id="resources" value="${resources}">
						<div class="addRoleTitle"><div class="xiaoduanshu"></div>功能权限列表</div>
						<div class="menu-cont">
							<p class="sele-canc clearfix" style="position:absolute;">
								<input type="radio" name="ch" onclick="onclic_()" style="margin-left:60px;"> <span
									class="fl_l" style="margin-right:67px;">全部选中</span> 
									<input type="radio" name="ch" onclick="onclic_1()"> <span
									class="fl_l">全部取消</span>
							</p>
							<ul id="tt1" style="text-align:left;">
								<!-- 菜单树 -->
							</ul>
						</div>
					</div>
			<div class="func-menu" id="role-menu3">				
						<!-- <div class="menu-title">时间设置</div> -->
						<div class="addRoleTitle" style="position:relative;"><div class="xiaoduanshu"></div>通信时间设置<div  class="shows-morenzhi">(默认值：7*24小时)</div></div>
						<div class="work-time-set-box">
							<div class="work-time-set">
								<p class="clearfix">
									<span class="sp sty-borcolor-b skin-minimal"
										style="float:left;margin-top:3px;"><input
										type="checkbox" class="check" id="workDay1"
										name="roleTimeControls[1].workDay" value="1"  ${map[1].workDay==1?'checked':''}/> </span><span
										class="work-time-week fl_l">星期一</span><input class="fl_l"
										type="text" id="startTime1"
										name="roleTimeControls[1].startTime" value="${map[1].startTime}"
										onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime1\')||\'23:59:59\'}'})"><span
										class="time-line fl_l"></span><input class="fl_l" type="text"
										id="endTime1" name="roleTimeControls[1].endTime" value="${map[1].endTime}" 
										onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime1\')||\'00:00:00\'}',maxDate:'23:59:59'})">
								</p>
							</div>
							<div class="work-time-set">
								<p class="clearfix">
									<span class="sp sty-borcolor-b skin-minimal"
										style="float:left;margin-top:3px;"><input
										type="checkbox" class="check" id="workDay2"
										name="roleTimeControls[2].workDay" value="2"  ${map[2].workDay==2?'checked':''} /> </span><span
										class="work-time-week fl_l">星期二</span><input class="fl_l"
										type="text" id="startTime2"
										name="roleTimeControls[2].startTime"  value="${map[2].startTime}"
										onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime2\')||\'23:59:59\'}'})"><span
										class="time-line fl_l"></span><input class="fl_l" type="text"
										id="endTime2" name="roleTimeControls[2].endTime"  value="${map[2].endTime}" 
										onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime2\')||\'00:00:00\'}',maxDate:'23:59:59'})">
								</p>
							</div>
							<div class="work-time-set">
								<p class="clearfix">
									<span class="sp sty-borcolor-b skin-minimal"
										style="float:left;margin-top:3px;"><input
										type="checkbox" class="check" id="workDay3"
										name="roleTimeControls[3].workDay" value="3"  ${map[3].workDay==3?'checked':''}/> </span><span
										class="work-time-week fl_l">星期三</span><input class="fl_l"
										type="text" id="startTime3"
										name="roleTimeControls[3].startTime" value="${map[3].startTime}"
										onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime3\')||\'23:59:59\'}'})"><span
										class="time-line fl_l"></span><input class="fl_l" type="text"
										id="endTime3" name="roleTimeControls[3].endTime" value="${map[3].endTime}" 
										onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime3\')||\'00:00:00\'}',maxDate:'23:59:59'})">
								</p>
							</div>
							<div class="work-time-set">
								<p class="clearfix">
									<span class="sp sty-borcolor-b skin-minimal"
										style="float:left;margin-top:3px;"><input
										type="checkbox" class="check" id="workDay4"
										name="roleTimeControls[4].workDay" value="4" ${map[4].workDay==4?'checked':''} /> </span><span
										class="work-time-week fl_l">星期四</span><input class="fl_l"
										type="text" id="startTime4"
										name="roleTimeControls[4].startTime" value="${map[4].startTime}"
										onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime4\')||\'23:59:59\'}'})"><span
										class="time-line fl_l"></span><input class="fl_l" type="text"
										id="endTime4" name="roleTimeControls[4].endTime" value="${map[4].endTime}" 
										onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime4\')||\'00:00:00\'}',maxDate:'23:59:59'})">
								</p>
							</div>
							<div class="work-time-set">
								<p class="clearfix">
									<span class="sp sty-borcolor-b skin-minimal"
										style="float:left;margin-top:3px;"><input
										type="checkbox" class="check" id="workDay5"
										name="roleTimeControls[5].workDay" value="5"  ${map[5].workDay==5?'checked':''}/> </span><span
										class="work-time-week fl_l">星期五</span><input class="fl_l"
										type="text" id="startTime5"
										name="roleTimeControls[5].startTime" value="${map[5].startTime}"
										onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime5\')||\'23:59:59\'}'})"><span
										class="time-line fl_l"></span><input class="fl_l" type="text"
										id="endTime5" name="roleTimeControls[5].endTime" value="${map[5].endTime}" 
										onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime5\')||\'00:00:00\'}',maxDate:'23:59:59'})">
								</p>
							</div>
							<div class="work-time-set">
								<p class="clearfix">
									<span class="sp sty-borcolor-b skin-minimal"
										style="float:left;margin-top:3px;"><input
										type="checkbox" class="check" id="workDay6"
										name="roleTimeControls[6].workDay" value="6" ${map[6].workDay==6?'checked':''}/> </span><span
										class="work-time-week fl_l">星期六</span><input class="fl_l"
										type="text" id="startTime6"
										name="roleTimeControls[6].startTime"  value="${map[6].startTime}"
										onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime6\')||\'23:59:59\'}'})"><span
										class="time-line fl_l"></span><input class="fl_l" type="text"
										id="endTime6" name="roleTimeControls[6].endTime"  value="${map[6].endTime}" 
										onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime6\')||\'00:00:00\'}',maxDate:'23:59:59'})">
								</p>
							</div>
							<div class="work-time-set">
								<p class="clearfix">
									<span class="sp sty-borcolor-b skin-minimal"
										style="float:left;margin-top:3px;"><input
										type="checkbox" class="check" id="workDay0"
										name="roleTimeControls[0].workDay" value="0"  ${map[0].workDay==0?'checked':''}/> </span><span
										class="work-time-week fl_l">星期日</span><input class="fl_l"
										type="text" id="startTime0"
										name="roleTimeControls[0].startTime" value="${map[0].startTime}"
										onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'00:00:00',maxDate:'#F{$dp.$D(\'endTime0\')||\'23:59:59\'}'})"><span
										class="time-line fl_l"></span><input class="fl_l" type="text"
										id="endTime0" name="roleTimeControls[0].endTime" value="${map[0].endTime}" 
										onclick="WdatePicker({dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'startTime0\')||\'00:00:00\'}',maxDate:'23:59:59'})">
								</p>
							</div>
						</div>
				</div>
				</form>
		</div>
    </div>
  </div>

<script type="text/javascript">
	//step2
	 if ($("#mark").val() == 1) {
		var on_off = true; // 开关，on_off为false时不执行onCheck事件，
	} else {
		var on_off = false; // 开关，on_off为false时不执行onCheck事件，
	}
	var menu2step_ = 1; // 1: 关联子节点，2：关联父节点  

	 //页面加载完成函数
	$(function() {
		/*左边铺满整屏*/
		var topTitleHeight=28;//页面上面的菜单和页签的高度
		var yinhight=$(window).height()-topTitleHeight;
		 $(".hyx-oral-right").css({"height":yinhight+"px","overflow-y":"auto"});
		 //处理中间部分的高度
		 middleHeigh();
		function middleHeigh(){
			var titlH=$(".hyx-oral-right-title").outerHeight(true);
			var topH=$("#role-menu1").outerHeight(true);
			var botH=$("#role-menu3").outerHeight(true);
			var midH=$("#role-menu2");
			var heighs=yinhight-titlH-botH-topH-10;
			midH.height(heighs);
		}
		rolesBtnCenter();
		function rolesBtnCenter(){
			var $roses=$(".manag-role");
			var pos=$roses.position();
			var $slec=$(".sele-canc");
			var titleW=$(".addRoleTitle");
			var widthS=titleW.outerWidth(true);
			$slec.css("left",pos.left-widthS+"px");
		}
		$('.hyx-hsm-left').find('dd').each(function() {
			$(this).siblings().hover(function() {
				$(this).addClass("dd-hovers");
				$(this).find(".min-edit").show();
				$(this).find(".min-dele").show();
			}, function() {
				$(this).removeClass("dd-hovers");
				$(this).find(".min-edit").hide();
				$(this).find(".min-dele").hide();
			});
			$(this).click(function() {
				$(this).addClass("dd-hover");
				$(this).siblings().removeClass("dd-hover");
			});
			$(this).hover(function() {
				$(this).find(".reso-grou-edit").show();
				$(this).find(".reso-grou-dele").show();

			}, function() {
				$(this).find(".reso-grou-edit").hide();
				$(this).find(".reso-grou-dele").hide();
			});
		});

		//step1中选择角色对树的切换
	 	changeTree();
		function changeTree(){
			var radios=$("input[name='roleType']");
			radios.each(function(){
				var $this=$(this);
				$this.click(function(e){
					e.stopPropagation();
					var datas=$this.val();
					$.ajax({
						url     	: 	'${ctx}/auth/role/get_tree_json',
						type		:	"POST",
						data  	:	{
							roleType:datas
						},
						error		:	function(XMLHttpRequest, textStatus, errorThrown){
							window.top.iDialogMsg("提示",XMLHttpRequest.readyState);
							window.top.iDialogMsg("提示",textStatus);
							window.top.iDialogMsg("提示",errorThrown);
						},
						success	:	function(data){
							changeRole(eval(data));
						}
					});
				});
			});
		} 
		
	//step2
		//树中的数据
		var resourceJson = eval('${jsonString}');
	
	//树的加载的函数封装
	changeRole(resourceJson);
	function changeRole(data) {
			$("#tt1").tree({
				data : data,
				checkbox : true,
				cascadeCheck : false,
				onCheck : function(node, checked) {			
					if (on_off) {
						if (checked) {
							if (menu2step_ == 1) { // 关联子节点
								var childNode = $("#tt1").tree('getChildren',node.target);
								if (childNode.length > 0) {
									for ( var i = 0; i < childNode.length; i++) {
										$("#tt1").tree('check',childNode[i].target);
									}
								} else {
									menu2step_ = 2; // 如果子节点没有了，就执行父节点
									checkParent(node);
								}
							}
						} else {
							var childNode = $("#tt1").tree('getChildren', node.target);
							if (childNode.length > 0) {
								for ( var i = 0; i < childNode.length; i++) {
									$("#tt1").tree('uncheck',childNode[i].target);
								}
							}
						}
					}
				}
			});
		};

		//处理选中
		var resources = $("#resources").val();
		if (resources.length > 0) {
			$(resources.split(',')).each(function(index, data) {
				var node = $("#tt1").tree("find", data);
				if (node != null) {
					$("#tt1").tree("check", node.target);
				}
			});
			on_off = true;
		}
		
		//step3
		//表单优化
		$('.skin-minimal input[type=checkbox]').iCheck({
			checkboxClass : 'icheckbox_minimal',
			radioClass : 'iradio_minimal',
			increaseArea : '20%'
		});
		
	});

	var isOpera = true; // 不可重复点击
	/** 角色点击完成 */
	function complete_() {
		if (isOpera) {
			isOpera = false;
			if($("#roleName").val() == null || $.trim($("#roleName").val()) == "" ){
				window.top.iDialogMsg("提示","请填写角色名！");
				isOpera = true;
				return false;
			}
			if($("#old_check").val() == "1"){
				window.top.iDialogMsg("提示","该角色名已存在！");
				isOpera = true;
				return false;
			}

			if(getResourceIds()){
				$("#myForm_1")
				.ajaxSubmit(
						{
							url : '${ctx}/auth/role/addRole',
							type : 'post',
							error : function(XMLHttpRequest, textStatus) {
								isOpera = true;
								window.top.iDialogMsg("提示","请求失败！");
							},
							success : function(data) {
								if (data == 0) {
									isOpera = true
									// 默认刷新主页面
									window.top.iDialogMsg("提示", "保存成功！");
									setTimeout(
											'document.location.href = "${ctx}/auth/role/addRolePage.do";',
											1000);
								} else {
									isOpera = true
									// 提示失败
									window.top.iDialogMsg("提示", "保存失败！");
								}
							}
						});
			}			
		}
	}

	// 获取配置权限资源IDs
	function getResourceIds() {
		var nodes = $("#tt1").tree("getChecked");
		var ids = "";
		$(nodes).each(function(index, obj) {
			ids += "," + obj.id;
		});
		if (ids.length > 0) {
			ids = ids.substring(1, ids.length);
		}
		if (ids == "") {
			window.top.iDialogMsg("提示","未配置角色权限，请先配置权限");
			isOpera = true;
			return false;
		}
		$("#resourceIds").val(ids);
		return true;
	}

	// 跳转至 角色编辑页面
	function jump_(roleId) {
		document.location.href = "${ctx}/auth/role/editRolePage.do?roleId="
				+ roleId;
	}

	//删除角色
	function delete_(roleId,roleName) {
		queDivDialog(
				"res_remove_cust",
				"是否删除？",
				function() {
					$
							.ajax({
								url : ctx + '/auth/role/deleteRole',
								type : 'post',
								data : {
									roleId : roleId,
									roleName : roleName
								},
								dataType : 'json',
								error : function() {
									window.top.iDialogMsg("提示",'网络异常，请稍后再试！')
								},
								success : function(data) {
									isOpera = true;
									if(data.status){
										window.top.iDialogMsg("提示",data.data);
										setTimeout(
												'document.location.href = "${ctx}/auth/role/addRolePage.do";',
												1000);
									}else{
										window.top.iDialogMsg("提示",data.errorMsg);
									}
								}
							});
				});
	}

	//step 1
	function chekRolename_() {
		var isPass = true;
		var oldVal = $("#old_roleName").val();
		var chkVal = $("#roleName").val();
		if (chkVal != "" && chkVal != null) {
			if (oldVal != '' && oldVal != null && chkVal == oldVal) { // 未作修改不请求验证
				return isPass;
			}
			$.ajax({
				url : '${ctx}/auth/role/check_unitRoleName.do',
				data : {
					'roleName' : chkVal
				},
				type : 'post',
				async : false,
				error : function(XMLHttpRequest, textStatus) {
					window.top.iDialogMsg("提示","请求失败！");
				},
				success : function(data) {
					if (data != 0) {
						isPass = false;
					}
				}
			});
		}
		if (!isPass) {
			window.top.iDialogMsg("提示","该角色名已存在！")
			$("#old_check").val(1);
		} else {
			$("#old_check").val(0);
		}
	}

	//step2
	//  选中父节点
	function checkParent(node) {
		var parentNode = $("#tt1").tree('getParent', node.target);
		if (parentNode != null) {
			$("#tt1").tree('check', parentNode.target);
			checkParent(parentNode);
		} else {
			menu2step_ = 1;
		}
	}

	// 全选中
	function onclic_(){
		var roots = $("#tt1").tree('getRoots');//返回tree的所有根节点数组
		for ( var i = 0; i < roots.length; i++) {
				var node = $("#tt1").tree('find', roots[i].id);//查找节点
				$("#tt1").tree('check', node.target);//将得到的节点选中
				allCheck_(node);
			}
	}
	
  	// 指定节点下子节点全部选中
  	function allCheck_(node){
  		var childNode = $("#tt1").tree('getChildren', node.target);
        if (childNode.length > 0) {
            for (var i = 0; i < childNode.length; i++) {
                $("#tt1").tree('check', childNode[i].target);
                allCheck_(childNode[i]);
            }
        }
  	}
  	
  	// 全取消
	function onclic_1(){
		var roots = $("#tt1").tree('getRoots');//返回tree的所有根节点数组
		for ( var i = 0; i < roots.length; i++) {
			var node = $("#tt1").tree('find', roots[i].id);
			$("#tt1").tree('uncheck', node.target);
			allUnCheck_(node);
		}
	}
  	
	// 指定节点下子节点全部取消
  	function allUnCheck_(node){
  		var childNode = $("#tt1").tree('getChildren', node.target);
        if (childNode.length > 0) {
            for (var i = 0; i < childNode.length; i++) {
                $("#tt1").tree('uncheck', childNode[i].target);
                allUnCheck_(childNode[i]);
            }
        }
  	}
</script>
</body>
</html>
