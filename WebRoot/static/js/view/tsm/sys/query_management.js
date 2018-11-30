$(function(){
	var data_entitys = $.parseJSON($("table").attr("data-entitys"));
	var obj_entitys = {"list":data_entitys};
	var is_changed = false;
	renderTable(obj_entitys);
//	console.log(obj_entitys);
//	console.log(data_entitys);
	
	$(".ajax-table>tbody").delegate(".fa-arrow-circle-o-up","click",function(e){
		var _this = $(this);
		var _this_tr = _this.parents("tr");
		var _this_tr_num = _this_tr.find(".sort-num");
		var _this_index =  parseInt(_this_tr_num.text());
		var _this_tr_prev = _this_tr.prev().eq(0);
		var _this_tr_prev_num = _this_tr_prev.find(".sort-num");
		var _this_prev_index = parseInt(_this_tr_prev_num.text());
		if(_this_tr_prev.length != 0){
			_this_tr.insertBefore(_this_tr_prev);
			_this_tr_num.text(parseInt(_this_tr_num.text())-1);
			_this_tr_prev_num.text(parseInt(_this_tr_prev_num.text())+1);
			_this_tr.toggleClass("sty-bgcolor-b");
			_this_tr_prev.toggleClass("sty-bgcolor-b");
//			console.log(_this_index)
//			console.log(_this_prev_index)
			swapItems(data_entitys,_this_index-1,_this_prev_index-1);
//			console.log(data_entitys);
			is_changed = true;
		}
	});
	$(".ajax-table>tbody").delegate(".fa-arrow-circle-o-down","click",function(e){
		var _this = $(this);
		var _this_tr = _this.parents("tr");
		var _this_tr_num = _this_tr.find(".sort-num");
		var _this_index =  parseInt(_this_tr_num.text());
		var _this_tr_next = _this_tr.next().eq(0);
		var _this_tr_next_num = _this_tr_next.find(".sort-num");
		var _this_next_index = parseInt(_this_tr_next_num.text());
		if(_this_tr_next.length != 0){
			_this_tr.insertAfter(_this_tr_next);
			_this_tr_num.text(parseInt(_this_tr_num.text())+1);
			_this_tr_next_num.text(parseInt(_this_tr_next_num.text())-1);
			_this_tr.toggleClass("sty-bgcolor-b");
			_this_tr_next.toggleClass("sty-bgcolor-b");
//			console.log(_this_index)
//			console.log(_this_next_index)
			swapItems(data_entitys,_this_index-1,_this_next_index-1);
//			console.log(data_entitys);
			is_changed = true;
		}
	});
	
	$(".ajax-table>tbody").delegate(".is-query input[type='checkbox']","click",function(e){
		var checked_length = $(".is-query input[type='checkbox']:checked").length;
		var inputText_length = $(".is-query input[data-type='1']:checked").length;
		if((checked_length-inputText_length) < 1){
			e.preventDefault();
			warmDivDialog("warm","常用输入查询项数量最少为1个");
//			window.top.iDialogMsg("提示","常用输入查询项数量最少为1个");
			return false;
		}else if((checked_length-inputText_length) > 10){
			e.preventDefault();
			warmDivDialog("warm","常用下拉查询项数量（包含所有者查询项）最多为10个，无法选择更多");
//			window.top.iDialogMsg("提示","常用下拉查询项数量（包含所有者查询项）最多为10个，无法选择更多");
			return false;
		}
		var _this = $(this);
		var _this_tr = _this.parents("tr");
		var _this_tr_num = _this_tr.find(".sort-num");
		var _this_index =  parseInt(_this_tr_num.text());
		data_entitys[_this_index-1].isQuery = _this[0].checked?1:0;
//		console.log(data_entitys[_this_index-1].isQuery);
//		console.log(data_entitys);
//		console.log(data_entitys[_this_index-1]);
		is_changed = true;
	});
	
	$(".ajax-table>tbody").delegate(".is-show input[type='checkbox']","click",function(e){
		var checked_length = $(".is-show input[type='checkbox']:checked").length;
		if(checked_length < 4){
			e.preventDefault();
//			warmDivDialog("warm","列表显示字段不能小于4个！");
			window.top.iDialogMsg("提示","列表内显示字段的数量最少为4个！");
			return false;
		}
		var _this = $(this);
		var _this_tr = _this.parents("tr");
		var _this_tr_num = _this_tr.find(".sort-num");
		var _this_index =  parseInt(_this_tr_num.text());
		data_entitys[_this_index-1].isShow = _this[0].checked?1:0;
//		console.log(data_entitys);
		is_changed = true;
	});
	
	$(".btn-submit").on("click",function(e){
		var input_searchCode = $("input[name='searchCode']");
		var input_listCode = $("input[name='listCode']");
		input_searchCode.each(function(i){
			var _this = $(this);
			data_entitys[i].searchCode = _this.val();
		});
		input_listCode.each(function(i){
			var _this = $(this);
			data_entitys[i].listCode = _this.val();
		});
//		console.log(data_entitys);
		$.ajax({
			url: ctx + '/searchManager/modfiy',
			type: "post",
			data: {'jsonDate':JSON.stringify(data_entitys)},
			success: function(data){
				is_changed = false;
				window.top.iDialogMsg("提示","页面规则保存成功！");
			},
			error: function(data){
				alert("error");
			}
		});
	});
	
	$(".search-modules").on("change",function(e){
		var _this = $(this);
		var module = _this.val();
		if(is_changed){
			queSaveDialog("isSaved","您对当前页面的规则做了变更，请确认是否需要保存；如果不需要保存，请点击“切换”；否则请点击“取消”。",function(){searchSetPage(module);});
		}else{
			searchSetPage(module);
		}
	});
});

function searchSetPage(value){
	window.location.href = ctx + '/searchManager/searchSetPage?module='+value;
}

function renderTable(data){
	var myTemplate = Handlebars.compile($("#template").html());
	$(".ajax-table>tbody").html(myTemplate(data));
}

function swapItems(arr,index1,index2){
	arr[index1] = arr.splice(index2, 1, arr[index1])[0];
	if(arr[index1].sort && arr[index2].sort){
		var sort1 = arr[index1].sort;
		var sort2 = arr[index2].sort;
		arr[index1].sort = sort2;
		arr[index2].sort = sort1;
	}
    return arr;
}