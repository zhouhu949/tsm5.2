function CheckBox(id,idPrefix,extend,extend1,extend2){
	_self=this;
	_self.first=$("#"+id);
	//全选事件
	
	// 全选
	_self.first.on('ifChecked', function() {
		$("input[id^="+idPrefix+"]").each(function(){
			if( !$(this).prop("disabled")){
				$(this).iCheck('check');
			}
		});
	});
	// 全不选
	_self.first.on('ifUnchecked', function() {
		$("input[id^="+idPrefix+"]").each(function(){
			if( !$(this).prop("disabled")){
				$(this).iCheck('uncheck');
			}
		});
	});
	
	_self.getIds=function (){
		_self.ids = new Array();
		_self.custIds = new Array();
		_self.extend1 = new Array();
		_self.extend2 = new Array();
		$("input[id^="+idPrefix+"]").each(function(index, obj) {
			if ($(obj).is(':checked')) {
				_self.ids.push($(obj).val());
				if(extend!=undefined && extend!=null){
					_self.custIds.push($(obj).attr('custId'));
				}
				if(extend1!=undefined && extend1!=null){
					_self.extend1.push($(obj).attr(extend1));
				}
				if(extend2!=undefined && extend2!=null){
					_self.extend2.push($(obj).attr(extend2));
				}
				
			}
		});
		
		if (_self.ids.length == 0) {
			window.parent.window.top.iDialogMsg("提示", '请先选择资源！');
			return null;
		}else{
			return _self.ids;
		}
	};
}