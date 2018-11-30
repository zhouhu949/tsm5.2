$(function(){
  $(".customize-time").each(function(idx,item){
    var $item = $(item);
    var startDate = $item.parents("dl.select").find(".startDate");
    var endDate = $item.parents("dl.select").find(".endDate");
    var type = $item.parents("dl.select").find(".type");
    $item.dateRangePicker({
      showShortcuts:false,
      format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
      var s_t = '';
      var e_t = '';
      if(obj.date1 != 'Invalid Date'){
        startDate.val(moment(obj.date1).format('YYYY-MM-DD'));
        s_t = moment(obj.date1).format('YY.MM.DD');
      }
      if(obj.date2 != 'Invalid Date'){
        endDate.val(moment(obj.date2).format('YYYY-MM-DD'));
        e_t = moment(obj.date2).format('YY.MM.DD');
      }
      var s = $(this).parents('.select');
      var dt=s.children("dt");
      var dd=s.children("dd");
      dt.html(s_t+'/'+e_t);
      type.val(5);
      dd.slideUp(200);
      dt.removeClass("cur");
    });
  });
  
  $(".member-tree").each(function(idx,item){
	  var $item = $(item);
	  var reso_sub_dep = $item.parents('.reso-sub-dep');
	  var tree_accs = reso_sub_dep.find('.tree-accs');
	  var tree_show_type = reso_sub_dep.find('.tree-show-type');
	  var tree_show_value = reso_sub_dep.find('.tree-show-value');
	  var trer_head_type_checkbox = reso_sub_dep.find("input[name^=searchOwnerType]");
	  trer_head_type_checkbox.each(function(idx,it){
		 var $it = $(it);
		 $it.iCheck({
			 radioClass: 'iradio_minimal'
		 });
		 $it.on('ifClicked',function(){
	    	var nodes = $item.tree('getChecked');
	    	if(nodes.length > 0){
	    		$.each(nodes,function(index,obj){
	    			$item.tree("uncheck",obj.target);
	    		});
	    	}
		 });
	  });
	  $item.tree({
			url:$item.data('url')?$item.data('url'):ctx+"/orgGroup/get_group_user_json",
			checkbox:true,
			onLoadSuccess:function(node,data){
				$item.tree("collapseAll");
                for(var i=0;i<data.length;i++){
                    $item.tree("expand",data[i].target);
                }
				var searchOwnerType = reso_sub_dep.find("input[name^=searchOwnerType]:checked").val();
				var oas = tree_accs.val();
				if(searchOwnerType == '1'){
					tree_show_value.val("查看全部");
				}else if(searchOwnerType == '2'){
					tree_show_value.val("查看自己");
				}else if(oas != null && oas != ''){
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
			},
			onCheck:function(node,isCheck){
				var nodes = $item.tree('getChecked');
				if(nodes.length > 0){
					reso_sub_dep.find("input[name^=searchOwnerType]").iCheck('uncheck');
				}else if($("input[name^=searchOwnerType]:checked").length == 0){
					reso_sub_dep.find("input[name^=searchOwnerType]:eq(0)").iCheck('check');
				}
			}
	  });	

  });
  
  $('.reso-owner-sure').each(function(idx,item){
	  var $item = $(item);
	  var reso_sub_dep = $item.parents('.reso-sub-dep');
	  var member_tree =  reso_sub_dep.find('.member-tree');
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
			}else{
				var searchOwnerTypeChecked = reso_sub_dep.find("input[name^=searchOwnerType]:checked");
				var searchOwnerType = searchOwnerTypeChecked.length>0?searchOwnerTypeChecked.val():1;
				if(searchOwnerType == '1'){
					names="查看全部";
				}else{
					names="查看自己";
				}
				tree_show_type.val(searchOwnerType);
			}
			tree_accs.val(accs);
			tree_show_value.val(names);
	  });
  });
  
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
});

function setDateType(e,value){
  var $this = $(e);
  var startDate = $this.parents("dl.select").find(".startDate");
  var endDate = $this.parents("dl.select").find(".endDate");
  var type = $this.parents("dl.select").find(".type");
  startDate.val('');
  endDate.val('');
  type.val(value);
}
