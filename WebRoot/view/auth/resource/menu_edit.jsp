<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/headContent.jsp"%>
<script type="text/javascript">
    var resHeight = window.screen.height;
    var resWidth = window.screen.width;
    if(resWidth < 1280){
      <!-- 1024*768 -->
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1024.css?_v=3d78f345beb64f9b826b93f850d05d23" />');
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_css.css?_v=3d78f345beb64f9b826b93f850d05d23" />');
    }
    else if(resWidth ==  1440)
    {
      <!-- 1440*900 -->
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1440.css?_v=3d78f345beb64f9b826b93f850d05d23" />');
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_css_1440.css?_v=3d78f345beb64f9b826b93f850d05d23" />');
    }
    else if(resWidth ==  1366 || resWidth ==  1360)
    {
      <!-- 1440*900 -->
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1366.css?_v=3d78f345beb64f9b826b93f850d05d23" />');
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_css_1366.css?_v=3d78f345beb64f9b826b93f850d05d23" />');
    }
    else{
      <!-- 1280*768 -->
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1280.css?_v=3d78f345beb64f9b826b93f850d05d23" />');
      document.write('<link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_css_1280.css?_v=3d78f345beb64f9b826b93f850d05d23" />');
    }
  </script>
  <link rel="stylesheet" type="text/css" href="http://192.168.1.15/tsm/css/tsm/css/dxgj_1440.css?_v=3d78f345beb64f9b826b93f850d05d23"><link rel="stylesheet" type="text/css" href="/tsm/css/tsm/css/dxgj_css_1440.css?_v=3d78f345beb64f9b826b93f850d05d23">
  <script type="text/javascript">
  $(function(){
		var url = ctx+"/resource/resourceMng.do";
		 $('#comboTree').combotree({
			url:url,
		}).combotree("tree").tree({
			onLoadSuccess:function(node,data){
				var pid = $("#parentId").val();
				$("#comboTree").combotree("setValue",pid);
			},
			onSelect:function(node){
				var nodeId = node.id;
				$("#parentId").val(nodeId);
			}
		});
		
		//保存
		$("#saveBtn").click(function(){
				$("#editForm").ajaxSubmit({
					error:function(){dialogMsg(-1,"网络异常，请稍后尝试!");},
					success:function(data){
						if(data == 0){
							dialogMsg(0,"保存成功");
							document.location.href=ctx+"/resource/toEditResourceMenu.do?resourceid="+$("#resourceId").val();
						}else if(data == 2){
							dialogMsg(-1,"名称重复，请修改!");
						}else{
							dialogMsg(-1,"保存失败!");
						}
					}
				});
		});
  });
  </script>
	<!-- 弹框开始 -->
		<div class="com-bomo com-bomo-w">
			<form id="editForm" action="${ctx }/resource/editResourcMenu.do" method="POST">
			<input type="hidden" id="resourceId" name="resourceId" value="${item.resourceId }">
			<input type="hidden" name="parentId" id="parentId" value="${item.parentId }">
			<div class="com-bomo-left fl_l" style="height:154px;margin-left:25px;">
				<div class="com-bomo-p">
					<label>资源名称：</label>
					<input type="text" name="resourceName" value="${item.resourceName }" >
				</div>
				<div class="com-bomo-p">
					<label>资源描述：</label>
					<input type="text" name="resourceDesc" value="${item.resourceDesc }" >
				</div>
				<div class="com-bomo-p">
					<label><i class="com-import">*</i>上级菜单：</label>
					<input id="comboTree" name="comboTree" class="easyui-combobox" style="width:200px;" value="${parentName}">					
					<span class="com-red com-ml10"></span>
				</div>
				<div class="com-bomo-p">
					<label>URL地址：</label>
					<input type="text" name="resourceString" value="${item.resourceString }">
				</div>
				<div class="com-bomo-p">
					<label>是否后台：</label>
					<select name="isbackground">
						<option value="1"<c:if test="${item.isbackground eq 1 }">selected</c:if>>是</option>
						<option value="0" <c:if test="${item.isbackground eq 0 }">selected</c:if>>否</option>
					</select>
				</div>
				<div class="com-bomo-p">
					<label>资源类型：</label>
					<input type="radio" name="resourceType" value="01" ${item.resourceType eq '01'?'checked':''}>菜单
					<input type="radio" name="resourceType" value="02" ${item.resourceType eq '02'?'checked':''}>按钮
				</div>
				<div class="com-bomo-p">
					<label>排序：</label>
					<input type="text" name="sort" value="${item.sort }">
				</div>
			</div>
			<div class="com-bomo-btn fl_l com-bomo-bm" style="margin-left:215px;margin-top:30px;">
				<input type="button" id="saveBtn" value="更改" class="fl_l com-btna w68">
				<input type="button" id="cancel" value="取消" class="fl_r com-btna w68">
			</div>
			</form>
		</div>
	<!-- 弹框结束 -->