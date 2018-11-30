(function($){
	$.fn.treeSearch = function(){
		var tree_search_input = '<input type="text" name="tree-search" placeholder="搜索" data-type="tree_search_input" />';

		return this.each(function(){
			var _this = $(this);
			var _this_search_tree = _this.find('ul');
//			var data_type = _this.attr("data-type");
//			$(tree_search_input).insertBefore('[data-type="'+data_type+'"]');
			if(_this.siblings('[name="tree-search"]').length == 0){
				$(tree_search_input).insertBefore(this);
			}
			treeFilter(_this_search_tree);
			searchInputInit(_this_search_tree,_this);
		});
	};

	function treeFilter(filterTree){
		filterTree.tree({
			filter:function(value,node){
				if(node.text.toLowerCase().indexOf(value.toLowerCase()) >= 0){
					if($.trim(value) != ""){
						filterTree.tree("expandTo",node.target);
					}
					return true;
				}else{
					return parentHas(filterTree,node,value);
				}
				return false;
    		}
		});
	}

	function searchInputInit(filterTree,treeBox){
		treeBox.parent().find('input[name="tree-search"]').on("input",function(e){
			var _this_input = $(this);
			filterTree.tree("doFilter",_this_input.val());
		});
	}

	function parentHas(tree,treeNode,value){
		if(!treeNode){
			return false;
		}
		var parentNode = tree.tree("getParent",treeNode.target);
		if(parentNode != null){
			if(parentNode.text.toLowerCase().indexOf(value.toLowerCase()) >= 0){
				return true;
			}else{
				parentHas(parentNode);
			}
		}else{
			return false;
		}
	}
})(jQuery);
