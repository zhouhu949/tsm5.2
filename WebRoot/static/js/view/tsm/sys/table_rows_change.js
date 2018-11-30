$(function(){
	var timeOutLength = 500;
	var dataUrl = $("table.easyui-datagrid").data("url");
	var dataFirstRowLock = $("table.easyui-datagrid").attr("data-firstRowLock");
	if(dataFirstRowLock){
		var fisrt_tr = $(".datagrid-body tbody tr").eq(0);
		var second_tr = $(".datagrid-body tbody tr").eq(1);
		fisrt_tr.find(".fa-arrow-circle-o-up").addClass("icon-gray");
		fisrt_tr.find(".fa-arrow-circle-o-down").addClass("icon-gray");
		second_tr.find(".fa-arrow-circle-o-up").addClass("icon-gray");
	}
	$(".datagrid-body tbody").delegate(".fa-arrow-circle-o-up","click",function(e){
		e.stopPropagation();
		var _this = $(this);
		
		if(dataFirstRowLock){
			if(_this.hasClass("icon-gray")){
				return false;
			}
		}
		
		var timestamp = new Date().getTime();
		if(!_this.data("timestamp")){
			_this.data("timestamp",timestamp) ;
		}else{
			var timeDifference = timestamp - _this.data("timestamp");
			if(timeDifference < timeOutLength){
				return false;
			}
		}
		_this.data("timestamp",timestamp);
		
		var _this_tr = _this.parents("tr");
		var _this_tr_num = _this_tr.find(".datagrid-cell-c1-sort");
		var _this_index =  parseInt(_this_tr_num.text());
		var _this_tr_prev = _this_tr.prev().eq(0);
		var _this_tr_prev_num = _this_tr_prev.find(".datagrid-cell-c1-sort");
		var _this_prev_index = parseInt(_this_tr_prev_num.text());
		if(_this_tr_prev.length != 0){
			setTimeout(function(){
				_this_tr.insertBefore(_this_tr_prev);
				_this_tr_num.text(parseInt(_this_tr_num.text())-1);
				_this_tr_prev_num.text(parseInt(_this_tr_prev_num.text())+1);
				_this_tr.toggleClass("sty-bgcolor-b");
				_this_tr_prev.toggleClass("sty-bgcolor-b");
				if(dataFirstRowLock){
					$(".datagrid-body tbody .icon-gray").removeClass("icon-gray");
					var fisrt_tr = $(".datagrid-body tbody tr").eq(0);
					var second_tr = $(".datagrid-body tbody tr").eq(1);
					fisrt_tr.find(".fa-arrow-circle-o-up").addClass("icon-gray");
					fisrt_tr.find(".fa-arrow-circle-o-down").addClass("icon-gray");
					second_tr.find(".fa-arrow-circle-o-up").addClass("icon-gray");
				}
			},timeOutLength);
			sortTableRows(_this,_this_tr_prev.find(".fa-arrow-circle-o-up"),dataUrl);
		}
	});
	$(".datagrid-body tbody").delegate(".fa-arrow-circle-o-down","click",function(e){
		e.stopPropagation();
		var _this = $(this);
		
		if(dataFirstRowLock){
			if(_this.hasClass("icon-gray")){
				return false;
			}
		}
		
		var timestamp = new Date().getTime();
		if(!_this.data("timestamp")){
			_this.data("timestamp",timestamp) ;
		}else{
			var timeDifference = timestamp - _this.data("timestamp");
			if(timeDifference < timeOutLength){
				return false;
			}
		}
		_this.data("timestamp",timestamp);
		
		var _this_tr = _this.parents("tr");
		var _this_tr_num = _this_tr.find(".datagrid-cell-c1-sort");
		var _this_index =  parseInt(_this_tr_num.text());
		var _this_tr_next = _this_tr.next().eq(0);
		var _this_tr_next_num = _this_tr_next.find(".datagrid-cell-c1-sort");
		var _this_next_index = parseInt(_this_tr_next_num.text());
		if(_this_tr_next.length != 0){
			setTimeout(function(){
				_this_tr.insertAfter(_this_tr_next);
				_this_tr_num.text(parseInt(_this_tr_num.text())+1);
				_this_tr_next_num.text(parseInt(_this_tr_next_num.text())-1);
				_this_tr.toggleClass("sty-bgcolor-b");
				_this_tr_next.toggleClass("sty-bgcolor-b");
				if(dataFirstRowLock){
					$(".datagrid-body tbody .icon-gray").removeClass("icon-gray");
					var fisrt_tr = $(".datagrid-body tbody tr").eq(0);
					var second_tr = $(".datagrid-body tbody tr").eq(1);
					fisrt_tr.find(".fa-arrow-circle-o-up").addClass("icon-gray");
					fisrt_tr.find(".fa-arrow-circle-o-down").addClass("icon-gray");
					second_tr.find(".fa-arrow-circle-o-up").addClass("icon-gray");
				}
			},timeOutLength);
			sortTableRows(_this,_this_tr_next.find(".fa-arrow-circle-o-up"),dataUrl);
		}
	});
});

function sortTableRows(currRow,changedRow,dataUrl){
	var params =  {
		"currRowId":currRow.data("id"),
		"changedRowId":changedRow.data("id")
//		"currRowSort":currRow.data("sort"),
//		"changedRowSort":changedRow.data("sort")
	};
	$.ajax({
		url: dataUrl,
		type: "POST",
		data: params,
		success: function(result){
			
		},
		error: function(result){
			
		}
	});
}