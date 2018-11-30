$(function(){
	var data_entitys = $.parseJSON($("table").attr("data-entitys"));
	var obj_entitys = {"list":data_entitys};
	var is_changed = false;
	renderTable(obj_entitys);
//	console.log(obj_entitys);
//	console.log(data_entitys);
	
	$(".ajax-table>tbody").delegate(".fa-arrow-circle-o-up","click",function(e){
		var _this = $(this);
		if(_this.hasClass("icon-grey")){
			return false;
		}
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
			if(_this_tr_num.text() < 3 && _this_tr_prev.find(".fa-arrow-circle-o-up").hasClass("icon-grey")){
				_this_tr.find(".fa-arrow-circle-o-up").addClass("icon-grey");
				_this_tr_prev.find(".fa-arrow-circle-o-up").removeClass("icon-grey");
			}
//			console.log(_this_index)
//			console.log(_this_prev_index)
			swapItems(data_entitys,_this_index-1,_this_prev_index-1);
//			console.log(data_entitys);
			is_changed = true;
		}
	});
	$(".ajax-table>tbody").delegate(".fa-arrow-circle-o-down","click",function(e){
		var _this = $(this);
		if(_this.hasClass("icon-grey")){
			return false;
		}
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
			if(_this_tr_num.text() > 2 && _this_tr.find(".fa-arrow-circle-o-up").hasClass("icon-grey")){
				_this_tr.find(".fa-arrow-circle-o-up").removeClass("icon-grey");
				_this_tr_next.find(".fa-arrow-circle-o-up").addClass("icon-grey");
			}
//			console.log(_this_index)
//			console.log(_this_next_index)
			swapItems(data_entitys,_this_index-1,_this_next_index-1);
//			console.log(data_entitys);
			is_changed = true;
		}
	});
	
	$(".ajax-table>tbody").delegate(".fa-edit","click",function(e){
		var _this = $(this);
		var id = _this.attr("data-id");
		var url = _this.attr("data-url");
		pubDivShowIframe('alert_sys_field_set',url+'?id='+id+'&v='+new Date().getTime(),'编辑字段',330,300);
	});
	
	$(".ajax-table>tbody").delegate(".is-required input[type='checkbox']","click",function(e){
		var checked_length = $(".is-required input[type='checkbox']:checked").length;
/*		if(checked_length < 4){
			e.preventDefault();
			window.top.iDialogMsg("提示","列表内显示字段的数量最少为4个！");
			return false;
		}*/
		var _this = $(this);
		var _this_tr = _this.parents("tr");
		var _this_tr_num = _this_tr.find(".sort-num");
		var _this_index =  parseInt(_this_tr_num.text());
		data_entitys[_this_index-1].isRequired = _this[0].checked?1:0;
//		console.log(data_entitys);
		is_changed = true;
	});
	
	$(".ajax-table>tbody").delegate(".is-read input[type='checkbox']","click",function(e){
		var checked_length = $(".is-read input[type='checkbox']:checked").length;
/*		if(checked_length < 4){
			e.preventDefault();
			window.top.iDialogMsg("提示","列表内显示字段的数量最少为4个！");
			return false;
		}*/
		var _this = $(this);
		var _this_tr = _this.parents("tr");
		var _this_tr_num = _this_tr.find(".sort-num");
		var _this_index =  parseInt(_this_tr_num.text());
		data_entitys[_this_index-1].isRead = _this[0].checked?1:0;
//		console.log(data_entitys);
		is_changed = true;
	});
	
	$(".ajax-table>tbody").delegate(".is-enable input[type='checkbox']","click",function(e){
		var checked_length = $(".is-enable input[type='checkbox']:checked").length;
/*		if(checked_length < 4){
			e.preventDefault();
			window.top.iDialogMsg("提示","列表内显示字段的数量最少为4个！");
			return false;
		}*/
		var _this = $(this);
		var _this_tr = _this.parents("tr");
		var _this_tr_num = _this_tr.find(".sort-num");
		var _this_index =  parseInt(_this_tr_num.text());
		data_entitys[_this_index-1].enable = _this[0].checked?1:0;
//		console.log(data_entitys);
		is_changed = true;
	});
	
	$('.demoBtn_a').click(function(){
		var _this = $(this);
		var data_sure_url = _this.attr("data-sure-url");
		var data_url = _this.attr("data-url");
		var data_count = _this.attr("data-count");
    	$.ajax({
			url : data_sure_url,
			type : 'post',
			async:false,
			error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
			success : function(data) {
				if(data <  data_count){
					pubDivShowIframe('alert_sys_field_set',data_url+'?v='+new Date().getTime(),'新增字段',330,300);
				}else{
					window.top.iDialogMsg("提示","目前只允许新增"+data_count+"个自定义字段！");
				}
			}
		});
	});


	jQuery(".ann-sale-plan a").on('click',function(event){
        event.stopImmediatePropagation();
        event.preventDefault();
		var $target=jQuery(event.currentTarget);
         if(is_changed){
             window.top.queDivDialog("ask_to_leave","您修改的设置尚未保存，确定要离开吗？",function(){
                 window.location.href=$target.attr("href");
             },function(){
			 })
		 }else{
             window.location.href=$target.attr("href");
		 }
	})


	$(".btn-submit").on("click",function(e){
		var _btn_submit = $(this);
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
		$.ajax({
			url: _btn_submit.attr("data-url"),
			type: "post",
			data: {'jsonDate':JSON.stringify(data_entitys)},
			success: function(data){
				is_changed = false;
				window.top.iDialogMsg("提示","页面规则保存成功！");
				window.location.reload();
			},
			error: function(data){
				alert("error");
			}
		});
	});
});

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