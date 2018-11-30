$(function(){
	/*唤醒树*/
	$(".box").delegate(".setting-flow","click",function(e){
		e.stopPropagation();
		var $this = $(this);
		$(".manage-owner-sour").hide();
		$this.siblings(".manage-owner-sour").show();
	});
	
	$(".box").delegate(".btn-remove-formItem","click",function(e){
		e.stopPropagation();
		var $this = $(this);
		var value = $("#auditNum").val()
		var newNum = value - 1 ;
		$this.parents('.formItem').remove();
		$("#auditNum").val(newNum)
	});
	
	$(window).click(function(){
		$(".manage-owner-sour").hide();
	})
	$(".box").delegate(".manage-owner-sour","click",function(e){
		e.stopPropagation();
		$(this).show();
	})
	/*审核条件change*/
	$(".condition-select").on("change",function(){
		var $this = $(this);
		var btn = $(".add-condition");
		var box = $(".add_box");
		if($this.val() == 0){
			box.hide();
			btn.hide();
		}else{
			box.show();
			btn.show();
		}
	})
	/*添加审核*/
	$(".add-condition").on("click",function(e){
		e.stopPropagation();
		var value = $("#auditNum").val()
		if(value >=3){
			window.top.iDialogMsg("提示", '最多设置三级审核！');
			return false;
		}
		var newNum = Number(value) + 1; 
		$("#auditNum").val(newNum)
		addConditionItem({num:newNum})
		treeDataFnInit();
	})
	/*表单提交*/
	$(".work_flow_set_form").submit(function(e){
		e.preventDefault();
		var $this = $(this)
		if(checkAll()){
			window.top.iDialogMsg("提示", '审核人必填！');
			return false
		}
		
		$.ajax({
			url:ctx+"/workflowSet/saveData",
			data:$this.serialize(),
			success:function(result){
				if(result == 0){
					window.top.iDialogMsg("提示", '保存成功！');
				}
			}
		})
	})
	
	/*初始化读取审核总数*/
	conditionBoxInit();
	/*初始化触发下拉*/
	$(".condition-select").change();
	/*树初始化和树确定取消时间绑定*/
	treeDataFnInit();
});

function checkAll(){
	var select = $(".condition-select")
	var input = $("[name^='auditorAcc']")
	var flag = true;
	input.each(function(i,obj){
		if($(obj).val() == ""){
			flag = false
		}
	})
	return select.val() == 1 && !flag
}

function addConditionItem(data){
	var myTemplate = Handlebars.compile($("#template").html());
	$(".add_box").append(myTemplate(data));
	treeDataFnInit();
}

function conditionBoxInit(){
	var num = $("#auditNum").val();
	if(num > 0){
		for(var i = 0 ; i < num ; i++ ){
			var newNum = i + 1;
			addConditionItem(obj[newNum])
			
		}
	}
}

function treeDataFnInit(){
	/*树填充*/
	$(".member-tree").each(function(idx,item){
		  var $item = $(item);
		  var reso_sub_dep = $item.parents('.reso-sub-dep');
		  var tree_accs = reso_sub_dep.find('.tree-accs');
		  var tree_show_type = reso_sub_dep.find('.tree-show-type');
		  var tree_show_value = reso_sub_dep.find('.tree-show-value');
		  $item.tree({
				url:ctx+"/orgGroup/get_group_user_json",
				checkbox:true,
				onLoadSuccess:function(node,data){
					$item.tree("expandAll");
					var oas = tree_accs.val();
					if(oas != null && oas != ''){
						var text='';
						$.each(oas.split(','),function(index,obj){
							var n = $item.tree("find",obj);
							if(n != null){
								text+=n.text+',';
								$item.tree("check",n.target).tree("expandTo",n.target);
							}
						});
						if(text != ''){
							text=text.substring(0,text.length-1);
							tree_show_value.val(text);
						}else{
							tree_show_value.val(tree_show_value.data('placeholder'));
						}
					}
					
					/*单选添加逻辑*/
					$(this).find('span.tree-checkbox').unbind().click(function () {
                        if(!$item.tree('isLeaf', $(this).parent())){
                            return false;
                        }
						$item.tree('select', $(this).parent());
	                    return false;
	                });
				},
				onSelect: function (node) {
	                var cknodes = $item.tree("getChecked");
	                if(node.children && node.children.length>0){
	                    return false;
	                }
	                for (var i = 0; i < cknodes.length; i++) {
	                    if (cknodes[i].id != node.id) {
	                    	$item.tree("uncheck", cknodes[i].target);
	                    }
	                }
	                if (node.checked) {
	                	$item.tree('uncheck', node.target);

	                } else {
	                	$item.tree('check', node.target);

	                }

	            },
		  });	

	  });
	  /*确定*/
	  $('.reso-owner-sure').each(function(idx,item){
		  var $item = $(item);
		  var reso_sub_dep = $item.parents('.reso-sub-dep');
		  var member_tree =  reso_sub_dep.find('.member-tree');
		  var manageSour = reso_sub_dep.find(".manage-owner-sour");
		  var tree_accs = reso_sub_dep.find('.tree-accs');
		  var tree_show_type = reso_sub_dep.find('.tree-show-type');
		  var tree_show_value = reso_sub_dep.find('.tree-show-value');
		  
		  $item.click(function(){
			  var nodes = member_tree.tree('getChecked','checked');
				var accs = "";
				var names = "";
				$.each(nodes,function(index,obj){
					var type = obj.attributes.type;
					if(type == "M"){
						accs+=obj.id+",";
						names+=obj.text+",";
					}
				});
				if(accs != ""){
					accs=accs.substring(0,accs.length-1);
					names=names.substring(0,names.length-1);
					tree_show_type.val("3");
				}/*else{
					var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
					if(searchOwnerType == '1'){
						names="查看全部";
					}else{
						names="查看自己";
					}
					tree_show_type.val(searchOwnerType);
				}*/
				tree_accs.val(accs);
				tree_show_value.val(names);
				manageSour.hide();
				return false;
		  });
	  });
	  /*取消*/
	  $('.reso-sub-clear').each(function(idx,item){
		  var $item = $(item);
		  var reso_sub_dep = $item.parents('.reso-sub-dep');
		  var member_tree =  reso_sub_dep.find('.member-tree');
		  
		  $item.click(function(){
			  	var nodes = member_tree.tree('getChecked', 'checked');
				$.each(nodes,function(index,obj){
					member_tree.tree("uncheck",obj.target);
				});
		  });
	  });
}