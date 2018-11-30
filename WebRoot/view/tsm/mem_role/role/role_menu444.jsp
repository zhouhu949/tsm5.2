<%@ page language="java" pageEncoding="UTF-8"%>
  <form id="myForm_4" method="post">
   <input type="hidden" id="curStep4" name="curStep4" value="4">
    <div class="menu-title">功能菜单</div>
    			<input type="hidden" name="quickResources" id="quickResources">
				<div class="menu-cont">
					<p class="sele-canc clearfix">
						<input type="radio" name="ch"  onclick="onclic_()">
						<span class="fl_l" style="margin-right:30px;">全部选中</span>
						<input type="radio"  name="ch"  onclick="onclic_1()">
						<span class="fl_l">全部取消</span>
					</p>
					<ul id="tt4">
			           	<!-- 菜单树 -->
			        </ul>
					
				</div>				
			</form>
  <script type="text/javascript">
	  $(function(){
		  var quickJson = eval('${quickJsonString}');
			//加载树
			$("#tt4").tree({
				checkbox:true,
				data:quickJson,
				onLoadSuccess:function(node,data){					 
					//处理选中
					var menus = '${quickResources}';
					if(menus.length > 0){
						$(menus.split(',')).each(function(index,data){
							var node = $("#tt4").tree("find",data);
							$("#tt4").tree("check",node.target);
						});
					}
				}
			});
	  });	  
	
	//全选中
		function onclic_(){
			var roots = $("#tt4").tree('getRoots');//返回tree的所有根节点数组
			for ( var i = 0; i < roots.length; i++) {
					var node = $("#tt4").tree('find', roots[i].id);//查找节点
					if(node != null){
						$("#tt4").tree('check', node.target);//将得到的节点选中
					}					
				}
		}
		
		// 全取消
		function onclic_1(){
			var roots = $("#tt4").tree('getRoots');//返回tree的所有根节点数组
			for ( var i = 0; i < roots.length; i++) {
				var node = $("#tt4").tree('find', roots[i].id);
				$("#tt4").tree('uncheck', node.target);
			}
		}
  </script>
