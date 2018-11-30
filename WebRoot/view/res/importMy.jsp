<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transition al.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
<title>客户管理-我的客户-新增资源</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css${_v}"><!--弹框样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/skin/default/color.css"><!--换肤样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/form/skins/all.css"><!--form样式-->
<link type="text/css" rel="stylesheet" href="${ctx }/static/css/upload.css" />
<link href="${ctx }/static/js/ligerUI/skins/Aqua/css/ligerui-all.css" type="text/css" rel="stylesheet" />

<!--公共js-->
<script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js${_v}" dialog-theme="default"></script><!--可移动弹框插件-->
<script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js${_v}"></script><!--可移动弹框插件公共js-->
<script type="text/javascript" src="${ctx}/static/js/form.js${_v}"></script><!--表单优化-->
<script type="text/javascript" src="${ctx}/static/js/form/icheck.js${_v}"></script><!--表单优化-->
<script type="text/javascript" src="${ctx}/static/js/form/js/custom.min.js${_v}"></script><!--表单优化-->

<!--本页面样式-->
<link rel="stylesheet"  type="text/css"  href="${ctx}/static/js/stepbar/css/control.css"/><!--步骤-->
<link rel="stylesheet"  type="text/css"  href="${ctx}/static/css/importMy.css${_v}"><!--本页面整理样式-->

<script type="text/javascript">
var resGroupId ="";
var status = ${status};
var isDetail = '0';
var impShiroAccount = '${shrioUser.account}';
var impShiroOrgId = '${shrioUser.orgId}';
var impShiroUserId = '${shrioUser.id}';
var impShiroUserGroupId = '${shrioUser.groupId}';
var impShiroUserIsState = '${shrioUser.isState}';
var tsmUpLoadServiceUrl = '${tsmUpLoadServiceUrl}'; // <!-- 上传服务器路径 -->
$(function(){
	/*表单优化*/

    //对分组的id 和 文本 进行二次赋值
    resGroupId = $(".shows-third-select").val();
   $(".groupSname").text($(".sChooseGroup option:selected").text());
    //意向库之外禁用下拉
    selected();
    function selected(){

    	if($(".shows-first-select").val() != "2"){
    		$(".shows-second-select").attr("disabled","disabled");
    	}
    	$(".shows-first-select").change(function(){
    		if($(".shows-first-select").val() != "2"){
    			$(".shows-second-select").attr("disabled","disabled");
    		}else{
    			$(".shows-second-select").removeAttr("disabled");
    		}
    	})
    }
    
    //销售进程和分组的切换显示
    switchPro();
    function switchPro(){
    	var $groups=$(".sChooseGroup");
    	var $proes=$(".chooseSalePro");
    	var $groupN=$(".groupSname");
    	if(status != 3 && status != 4 ){
    		$proes.hide();
    	}else{
    		//$groups.hide();
    	};
    	$groups.change(function(){
    	var $this=$(this);
    		resGroupId=$this.find("option:selected").val();
    		$groupN.text($this.find("option:selected").text());
    	});
    	
    	//选择数据导入下面切换
    	$(".shows-first-select").change(function(){
    		var num=$(this).find("option:selected").val();
    		if(num != 2 && num != 3){
    			$proes.hide();
    			//$groups.show();
    		}else{
    			$proes.show();
    			//$groups.hide();
    		}
    	});
    };
});

</script>
</head>
<body>

<div class="cust-impo-reso">
	<div id="stepBar" class="ui-stepBar-wrap">
		<div class="ui-stepBar">
			<div class="ui-stepProcess"></div>
		</div>
		<div class="ui-stepInfo-wrap">
			<table class="ui-stepLayout" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="ui-stepInfo">
						<a class="ui-stepSequence01">1</a>
						<p class="ui-stepName">选择导入库</p>
					</td>
					<td class="ui-stepInfo">
						<a class="ui-stepSequence01">2</a>
						<p class="ui-stepName">上传文件</p>
					</td>
					<td class="ui-stepInfo">
						<a class="ui-stepSequence01">3</a>
						<p class="ui-stepName">匹配字段</p>
					</td>
					<td class="ui-stepInfo">
						<a class="ui-stepSequence01">4</a>
						<p class="ui-stepName">提交成功</p>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="shows-cust-select" id="role-menu1" >
		<p><span>选择数据将要导入的库：</span>
		<select class="shows-first-select" id="mark">
			<option value="1">资源库</option>
			<option value="2" ${status eq 3 ? 'selected':'' }>意向库</option>
			<option value="3" ${status eq 4 ? 'selected':'' }>签约库</option>
		</select>
		</p>
		<p class="chooseSalePro"><span>选择意向对应的销售进程：</span>
		<select class="shows-second-select"  id="processId">
			<c:forEach items="${saleProcessList}" var="saleProcess" varStatus="vs">
					<option value="${saleProcess.optionlistId}"
						<c:choose>
						    <c:when test="${saleProcess.isDefault ==1}">
						         selected
						    </c:when>											    
						</c:choose>>
						${saleProcess.optionName}
					</option>
				</c:forEach>
		</select>
		</p>
		<p class="sChooseGroup"><span>选择需要导入的分组：</span>
		<select class="shows-third-select">
			<%-- <c:forEach items="${resGroups}" var="group">
			  <option value="${group.resGroupId }" <c:if test="${group.groupName eq '未分组' }">selected</c:if>>${group.groupName}</option>
			</c:forEach> --%>				
		</select>
		</p>
		<div class="subm-succ fl_l">
			<h2 >温馨提示：</h2>
			<p><span>1.导入数据库可选择资源库、意向库和签约库；</span></p>
			<p><span>2.导入客户时，默认分组为“未分组”，也可以选择导入其他组；</span></p>
			<p><span>3.用户选中导入库为意向库，则需要选择意向客户导入的销售进程；</span></p>
			<p><span>4.资源分组的维护，统一由管理员在【资源管理】-【待分配资源中】维护；</span></p>
			<p><span>5.销售进程的维护，统一由管理员在【系统设置】-【系统属性】中维护；</span></p>
			<p><span>6.导入意向时，选择了销售进程且在表格中导入了“销售进程”字段，将以导入的内容为准；</span></p>
			<p><span>7.导入意向时，如果Excel中没有“淘到客户时间”，则以导入时间作为“淘到客户时间”；</span></p>
			<p><span>8.导入签约时，如果Excel中没有“签约时间”，则以导入时间作为“签约时间”；</span></p>
		</div>
		
		<div class="next-prev-btnbox"><button onclick="step_()" class="com-btnb com-btnb-sty next-step import-next-btn" >下一步</button>	</div>
	</div>
	
	<div class="cust-impo-res" id="role-menu2" style="display:none;">
		<p class="batch-impo-add"><img width="216px" height="27px" src="${ctx}/static/images/batch-impo-add.png" alt=""></p>
		<div class="com-search clearfix" style="min-height:0;height:20px;line-height:20px;">
			<label class="fl_l" for="" style="width:auto;height:27px;line-height:27px;">分组：</label>
			<span class="fl_l groupSname" style="display:inline-block;height:27px;line-height:27px;"></span>
		</div>
		<p class="impor-browse clearfix">
			<div class="message" style="position:relative;">
				<!-- <input type="text" id="txt" name="txt" class="input" value="点击浏览选择要上传的文件" disabled="disabled" /> -->
				<input id="uploadButton" type="button" value="点击浏览选择要上传的文件"/>
				<a href="javascript:;" onclick="expTempExcel()" class="temp-download" >模板下载</a>
				<br><output id="selectedFiles"></output>
				<a href="javascript:;" class="chooseFiles">上传导入文件(5Mb Max)
					 <input type="file" id="files" />
				 </a>
			</div>
		</p>
	</div>

	<div class="match-field" id="role-menu3" style="display:none;">
		<input type="hidden" id="filedSets">
		<p class="mat-fie-dec">
			<label for="">数据字段匹配（共</label><span id="totalRow"></span><label for="">条）：</label></p>
		<div class="mat-fie-tab">
			<table id="matchTable" width="100%" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th>导入字段</th>
						<th>匹配字段</th>
						<th>首条数据</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
		<p class="impo-negle clearfix"><input class="fl_l" type="checkbox"  id="firstCheck" checked="checked"  class="check"><span class="fl_l">导入时忽略第一行数据</span></p>
		<h2>温馨提示：</h2>
		<p>导入文件第一行为具体数据，请不要选择此项，否则在导入时将忽略首行数据，确认字段匹配无误，请进行下一步操作。</p>
	</div>

	<div class="subm-succ fl_l" id="role-menu4" style="display:none;margin-left:120px;margin-top:20px;margin-bottom:0;">
		<p class="subm-succ-line clearfix" style="margin-left:100px;"><img class="fl_l" width="61" height="61" src="${ctx}/static/images/fashion.png" alt=""><span class="fl_l">提交成功</span></p>
		<h2>温馨提示：</h2>
		<p><span>1.导入数据处理时间与数据量有关，通常需要1至5分钟；</span></p>
		<p><span>2.请通过【导入结果】查看数据导入情况；</span></p>
		<p><span>3.提交成功后可关闭本向导。</span></p>
		
	</div>

	<div class="role-step clearfix" style="width:510px;">
		<input type="hidden" id="step_" value="1">
		<a href="javascript:;" class="com-btnb last-step fl_l" onclick="step_1()">上一步</a>
		<div style="dispaly:inline-block;position:relative;padding-left:120px;"><button href="javascript:;" class="com-btnb com-btnb-sty next-step fl_l" onclick="stepBefore()" style="display:none;border:none;outline:none;">下一步</button></div>
		<span id="show_msg" style="position:absolute;left:160px;top:-15px;width:150px;height:15px;line-height:15px;color:red;display: none;">正在提交中，请稍后。。。</span>
		<a href="${ctx }/resimp/page?resGroupId=${resGroupId}&status=${status}" class="com-btnb com-btnb-sty fish-secu">继续提交</a>
	</div>
	
	<div class="kind-remin" style="position:relative;display:none;"> 
		<h2>温馨提示：</h2>
		<p><span>1.支持标准和非标准Excel模版导入；</span></p>
		<p><span>2.单个文件数据最大支持10,000行；</span></p>
		<p><span>3.非标准模版第一行为字段名称，不可为空或其他内容；</span></p>
		<p><span>4.所有日期格式（如：生日）为：“2016-01-01”。</span></p>
		<div class="next-prev-btnbox prev-margin-left"><a href="javascript:;" class="com-btnb com-btnb-sty next-step" onclick="step_1()" style="display:inline-block;margin-left:auto;margin-right:auto;margin-top:20px;">上一步</a></div>
	</div>
</div>
<!--本页面js-->
<script type="text/javascript" src="${ctx}/static/js/stepbar/js/jquery.easing.1.3.js${_v}"></script><!--步骤-->
<script type="text/javascript" src="${ctx}/static/js/stepbar/js/stepbar.js${_v}"></script><!--步骤-->


<script type="text/javascript" src="${ctx}/static/js/view/res/importResMy.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/importFileJs.js${_v}"></script>

<script type="text/javascript" src="${ctx}/static/js/ligerUI/js/core/base.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/ligerUI/js/plugins/ligerDrag.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/ligerUI/js/plugins/ligerDialog.js${_v}"></script>
</body>

</html>
