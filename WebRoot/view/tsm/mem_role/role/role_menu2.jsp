<%@ page language="java" pageEncoding="UTF-8"%>
  <form id="myForm_2" method="post">
  	<input type="hidden" id="curStep2" name="curStep2" value="2">
  	<input type="hidden" id ="mark" value="${mark }"><!-- 1:表示新增，2：表现修改 -->
   <input type="hidden" id = "resourceIds" name="resourceIds">
   <input type="hidden" id="resources" value="${resources}">
    <div class="menu-title">功能菜单</div>
				 <div class="menu-cont">
					<p class="sele-canc clearfix">
						<input type="radio" name="ch"  onclick="onclic_()">
						<span class="fl_l" style="margin-right:30px;">全部选中</span>
						<input type="radio"  name="ch"  onclick="onclic_1()">
						<span class="fl_l">全部取消</span>
					</p>
					<ul id="tt1">
			           	<!-- 菜单树 -->
			        </ul>
				</div>
	</form>
  <script type="text/javascript">
  if($("#mark").val() == 1){
	  var on_off = true; // 开关，on_off为false时不执行onCheck事件，
  }else{
	  var on_off = false; // 开关，on_off为false时不执行onCheck事件，
  } 
  var menu2step_ = 1; // 1: 关联子节点，2：关联父节点
  $(function(){
	  var resourceJson = eval('${jsonString}'); 
		//加载树
		$("#tt1").tree({
            data: resourceJson,
            checkbox: true,
            cascadeCheck: false,
            onCheck: function (node, checked) {
            	if(on_off){
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
            	}             
            }
        });
		//处理选中
		var resources = $("#resources").val();
		if(resources.length > 0){
			$(resources.split(',')).each(function(index,data){
				var node = $("#tt1").tree("find",data);
				if(node != null){
					$("#tt1").tree("check",node.target);
				}				
			});
			on_off = true;
		}
	});
	
  //  选中父节点
  function checkParent(node){
  	var parentNode = $("#tt1").tree('getParent', node.target);  
	  	if (parentNode != null) {
          $("#tt1").tree('check', parentNode.target);
          checkParent(parentNode);
      }else{
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
