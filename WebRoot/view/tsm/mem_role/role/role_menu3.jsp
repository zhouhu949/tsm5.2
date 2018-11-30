<%@ page language="java" pageEncoding="UTF-8"%>
   <form id="myForm_3" method="post">
    <input type="hidden" id="curStep3" name="curStep3" value="3">
    <div class="menu-title">${orgName}</div>
				<div class="menu-cont">
					<input type="hidden" id="members" name="members" >
					<p class="sele-canc clearfix">
						<input type="radio"  name="chc" onclick="onclic_()">
						<span class="fl_l" style="margin-right:30px;">全部选中</span>
						<input type="radio" name="chc"  onclick="onclic_1()">
						<span class="fl_l">全部取消</span>
					</p>
					 <ul id="tt3">
			           	<!-- 成员树 -->
			        </ul>
				</div>
	 </form>
  <script type="text/javascript">
	   
  $(function(){	   
	  var membersJson = eval('${membersJson}');
		 //加载树
		$("#tt3").tree({
			checkbox:true,
			data:membersJson,
			onLoadSuccess:function(node,data){
				//处理选中
				var checks = '${checkMembers}';
				if(checks.length > 0){
					$(checks.split(',')).each(function(index,data){
						var node1 = $("#tt3").tree("find",data);
						if(node1 != null){
							$("#tt3").tree("check",node1.target);
						}			
					});
				}
			}
		});
	});
	
	//全选中
	function onclic_(){
		var roots = $("#tt3").tree('getRoots');//返回tree的所有根节点数组
		for ( var i = 0; i < roots.length; i++) {
				var node = $("#tt3").tree('find', roots[i].id);//查找节点
				$("#tt3").tree('check', node.target);//将得到的节点选中
			}
	}
	
	// 全取消
	function onclic_1(){
		var roots = $("#tt3").tree('getRoots');//返回tree的所有根节点数组
		for ( var i = 0; i < roots.length; i++) {
			var node = $("#tt3").tree('find', roots[i].id);
			$("#tt3").tree('uncheck', node.target);
		}
	}
  </script>
