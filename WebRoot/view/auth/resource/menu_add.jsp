<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/include.jsp"%>
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
  // multiple: true,
  $(function(){
		var url = ctx+"/resource/resourceMng.do";
		 $('#comboTree').combotree({
			url:url,
			
		}).combotree("tree").tree({
			onLoadSuccess:function(node,data){
				//移除图标
				$(".tree-icon").remove();
				var pid = $("#parentId").val();
				$("#comboTree").combotree("setValue",pid);
			},
			onSelect:function(node){
				var nodeId = node.id;
				$("#parentId").val(nodeId);
			},onExpand:function(node){
				$(".tree-title").removeClass("tree-folder-open");
			}
		});
		
		//保存
		$("#saveBtn").click(function(){				
				$("#addForm").ajaxSubmit({
					error:function(){dialogMsg(-1,"网络异常，请稍后尝试!");},
					success:function(data){
						if(data == 0){
							$.messager.alert("提示","保存成功");
							document.location.href=ctx+"/resource/toEditResourceMenu.do";
						}else if(data == 2){
							$.messager.alert("提示","名称重复，请修改!");
						}else{
							$.messager.alert("提示","保存失败!");
						}
					}
				});
		});
		
		//  取消
		$("#cancel").click(function(){
			$("input[name='comboTree']").each(function(){
				alert($(this).val());
			});
		});
  });
  </script>
	<!-- 弹框开始 -->
		<div class="com-bomo com-bomo-w">
			<form id="addForm" action="${ctx}/resource/editResourcMenu.do" method="POST">
			<div class="com-bomo-left fl_l" style="height:154px;margin-left:25px;">
				<div class="com-bomo-p">
					<label>菜单名称：</label>
					<input type="text" name="resourceName" >
				</div>
				<div class="com-bomo-p">
					<label>资源描述：</label>
					<input type="text" name="resourceDesc"  >
				</div>
				<div class="com-bomo-p">
					<label><i class="com-import">*</i>上级菜单：</label>
					<input id="comboTree" name="comboTree" class="easyui-combobox" style="width:200px;">
					<input type="hidden" id="parentId" name="parentId"/>
					<span class="com-red com-ml10"></span>
					<input type="hidden" id="resourceId" name="resourceId"  />
				</div>
				<div class="com-bomo-p">
					<label>URL地址：</label>
					<input type="text" name="resourceString" >
				</div>
				<div class="com-bomo-p">
					<label>是否后台：</label>
					<select name="isbackground">
						<option value="0">否</option>
						<option value="1">是</option>						
					</select>
				</div>
				<div class="com-bomo-p">
					<label>排序：</label>
					<input type="text" name="sort" >
				</div>
			</div>
			<div class="com-bomo-btn fl_l com-bomo-bm" style="margin-left:215px;margin-top:30px;">
				<input type="button" id="saveBtn" value="添加" class="fl_l com-btna w68">
				<input type="button" id="cancel" value="取消" class="fl_r com-btna w68">
			</div>
			</form>
		</div>
	<!-- 弹框结束 -->