<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transition al.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
<title>导入资源</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
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
<link rel="stylesheet" href="${ctx}/static/js/stepbar/css/control.css" type="text/css" /><!--步骤-->

<!--本页面js-->
<script type="text/javascript" src="${ctx}/static/js/stepbar/js/jquery.easing.1.3.js${_v}"></script><!--步骤-->
<script type="text/javascript" src="${ctx}/static/js/stepbar/js/stepbar.js${_v}"></script><!--步骤-->


<script type="text/javascript" src="${ctx}/static/js/view/qupai/importRes.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/qupai/importFileJs.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/ligerUI/js/core/base.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/ligerUI/js/plugins/ligerDrag.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/ligerUI/js/plugins/ligerDialog.js${_v}"></script>
<script type="text/javascript">
var resGroupId ="${resGroupId}";
var status = ${status};
var isDetail = ${isDetail};
var impShiroAccount = '${shrioUser.account}';
var impShiroOrgId = '${shrioUser.orgId}';
var impShiroUserId = '${shrioUser.id}';
var impShiroUserGroupId = '${shrioUser.groupId}';
var impShiroUserIsState = '${shrioUser.isState}';
var tsmUpLoadServiceUrl = '${tsmUpLoadServiceUrl}'; // <!-- 上传服务器路径 -->

$(function(){
    //选择分组改变groupId
    changeGI();
    function changeGI(){
    	var $chos=$(".noChoosed");
    	$chos.change(function(){
    		resGroupId=$(this).val();
    	});
    };
    
    //分组直接显示
    ;(function showGroups(){
    	var mm=$(".noChoosed option:selected").text();
    	$(".groupSname").text(mm);
    })();
});

</script>
<style type="text/css">
	.ui-stepBar-wrap .ui-stepInfo-wrap{width:100%;padding-left:27px;}
	.ui-stepBar-wrap{height:100px;margin-top:-15px;}
	.ui-stepInfo{padding-left:0 !important;}
	#importList{width:500px;}
	#importList li {margin:0;}
	.swfupload{margin-left:0;}
	.temp-download{display:inline-block;width:48px;height: 20px;line-height: 44px;text-decoration:underline;color:#666666;}
	.shows-select-box{position:absolute;width:400px;height:30px;top:-50px;line-height:30px;}
	.shows-select-box select{margin-left:10px;/* padding-right:5px; */}
	.noChoosed{display:inline-block;width:auto;padding:0 5px;height:20px;margin-top:4px;}
	#uploadButton{width:250px;height:25px;line-height:20px;background-color:#fafafa;color:#cccccc;text-align:left;padding-left:10px;cursor:default;}
	.chooseFiles{width:145px;height:20px;padding:0;background-color:#fff;color:#000;margin-top:10px;}
</style>
</head>
<body>

<div class="cust-impo-reso">
	<input type="hidden" id="mark1" value="${mark }"/>
	<div id="stepBar" class="ui-stepBar-wrap">
		<div class="ui-stepBar">
			<div class="ui-stepProcess"></div>
		</div>
		<div class="ui-stepInfo-wrap">
			<table class="ui-stepLayout" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="ui-stepInfo">
						<a class="ui-stepSequence01">1</a>
						<p class="ui-stepName">选择文件</p>
					</td>
					<td class="ui-stepInfo">
						<a class="ui-stepSequence01">2</a>
						<p class="ui-stepName">匹配字段</p>
					</td>
					<td class="ui-stepInfo">
						<a class="ui-stepSequence01">3</a>
						<p class="ui-stepName">提交成功</p>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="cust-impo-res" id="role-menu1">
		<p class="batch-impo-add"><img width="216px" height="27px" src="${ctx}/static/images/batch-impo-add.png" alt=""></p>
		<div class="com-search clearfix" style="min-height:0;height:20px;line-height:20px;">
			<c:if test="${isDetail ne 1}"><label class="fl_l shows-fenzu" for="" style="width:auto;height:27px;line-height:27px;">分组：</label></c:if>
			<span class="fl_l groupSname" style="display:inline-block;height:27px;line-height:27px;"></span>
			<select class="noChoosed" style="display:none;">
				<c:choose>
					<c:when test="${not empty resGroupId}">
						<c:forEach items="${resGroups}" var="group">
						  <option value="${group.resGroupId }" <c:if test="${resGroupId==group.resGroupId }">selected</c:if>>${group.groupName}</option>
						</c:forEach>
					</c:when>
				</c:choose>			
			</select>
			<%-- <span class="fl_l" style="display:inline-block;height:27px;line-height:27px;">
				<c:choose>
					<c:when test="${not empty resGroupId}">
						<c:forEach items="${resGroups}" var="group">
							<c:if test="${resGroupId==group.resGroupId }"><dt>${group.groupName}</dt></c:if>
						</c:forEach>
					</c:when>
				</c:choose>				
			</span> --%>
		</div>
		<p class="impor-browse clearfix">
			<div class="message" style="position:relative;">
				<!-- <input type="text" id="txt" name="txt" class="input" value="点击浏览选择要上传的文件" disabled="disabled" />  -->
				<input id="uploadButton" type="button" value="点击浏览选择要上传的文件"/>
				<c:choose>
					<c:when test="${isDetail eq 1 }"> <!--导入联系人-->
						<a href="javascript:;" onclick="expDetailTempExcel()" class="temp-download" >模板下载</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:;" onclick="expTempExcel()" class="temp-download" >模板下载</a>
					</c:otherwise>
				</c:choose>
				<br><output id="selectedFiles"></output>
				 <a href="javascript:;" class="chooseFiles">上传导入文件(5Mb Max)
					 <input type="file" id="files"  />
				 </a>
			</div>
	</div>

	<div class="match-field" id="role-menu2" style="display:none;">
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

	<div class="subm-succ fl_l" id="role-menu3" style="display:none;margin-left:120px;margin-top:20px;margin-bottom:0;">
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
		<a href="${ctx }/resimp/page?resGroupId=${resGroupId}&status=${status}&mark=${mark}" class="com-btnb com-btnb-sty fish-secu">继续提交</a>
	</div>
	
	<div class="kind-remin" style="position:relative;">
		<h2>温馨提示：</h2>
		<p>1.支持标准和非标准Excel模版导入；</p>
		<p>2.单个文件数据最大支持10,000行；</p>
		<p>3.非标准模版第一行为字段名称，不可为空或其他内容；</p>
		<p>4.所有日期格式（如：生日）为：“2016-01-01”。</p>
		<c:if test="${isDetail eq 1}"><p>5.导入联系人时，每一条记录对应的客户名称必须与库内客户名称对应，若不存在或者为空，这条记录将不能成功导入，并产生错误报告。</p></c:if>
		<div style="dispaly:inline-block;position:relative;padding-left:120px;margin-top:20px;"><button class="com-btnb com-btnb-sty  fl_l shows-next-tip-btn" style="border:none;outline:none;" disabled>下一步</button></div>
	</div>
</div>
</body>

</html>
