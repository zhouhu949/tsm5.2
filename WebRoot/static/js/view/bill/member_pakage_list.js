$(function(){
	$(".com-search #queryByConditions").on("click",function(e){
		var dDateType = $("#dDateType").val();
		var startDate = $("#s_startDate").val();
		var endDate = $("#s_endDate").val();
		var diff_days = moment(endDate).diff(moment(startDate),"days");
		if(dDateType == 5 && diff_days > 180){
			window.top.iDialogMsg("提示","操作时间必须在6个月内!");
			return false;
		}
		var _this = $(this);
		_this.attr("disabled",true);
		try{
			loadData();
		}catch(e){
			document.forms[0].submit();
		}
		setTimeout(function(e){
			_this.attr("disabled",false);
		},500);
	});
	
	/*表单优化*/
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
    
	//到期时间
	 $('#dDate').dateRangePicker({
		 	showShortcuts:false,format: 'YYYY-MM-DD'
	    }).bind('datepicker-apply',function(event,obj){
	    	var s_t = '';
	    	var e_t = '';
	    	if(obj.date1 != 'Invalid Date'){
	    		$('#s_startDate').val(moment(obj.date1).format('YYYY-MM-DD'));
	    		s_t = moment(obj.date1).format('YY.MM.DD');
	    	}
	    	if(obj.date2 != 'Invalid Date'){
	    		$('#s_endDate').val(moment(obj.date2).format('YYYY-MM-DD'));
	    		e_t = moment(obj.date2).format('YY.MM.DD');
	    	}
	    	var s = $(this).parents('.select');
	    	var dt=s.children("dt");
	        var dd=s.children("dd");
	        dt.html(s_t+'/'+e_t);
	        $("#dDateType").val(5);
	        dd.slideUp(200);
	        dt.removeClass("cur");
	 });
	//时长分配
	$("#recharge").click(function(){
		var accs = '';
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				accs+=$(obj).attr('account')+',';
			}
		});
		if(accs.length == 0){
			window.top.iDialogMsg("提示",'请先选择帐号！');
			return;
		}
		accs=accs.substring(0,accs.length-1);
		pubDivShowIframe('rechargeFrame',ctx+'/pakage/allocation/toRechargeTime?accs='+accs,'通信币分配',400,260);
	});
	//时长回收
	$("#recover").click(function(){
		var accs = '';
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				accs+=$(obj).attr('account')+',';
			}
		});
		if(accs.length == 0){
			window.top.iDialogMsg("提示",'请先选择帐号！');
			return;
		}
		accs=accs.substring(0,accs.length-1);
		pubDivShowIframe('recoverFrame',ctx+'/pakage/allocation/toRecoverTime?accs='+accs,'通信币回收',400,270);
	});
	//短信分配
	$("#smsRechargeId").click(function(){
		var accs = '';
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				accs+=$(obj).attr('account')+',';
			}
		});
		if(accs.length == 0){
			window.top.iDialogMsg("提示",'请先选择帐号！');
			return;
		}
		accs=accs.substring(0,accs.length-1);
		pubDivShowIframe('rechargeSmsFrame',ctx+'/pakage/allocation/toRechargeSms?accs='+accs,'短信分配',400,260);
	});
	//短信回收
	$("#smsRecoverId").click(function(){
		var accs = '';
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				accs+=$(obj).attr('account')+',';
			}
		});
		if(accs.length == 0){
			window.top.iDialogMsg("提示",'请先选择帐号！');
			return;
		}
		accs=accs.substring(0,accs.length-1);
		pubDivShowIframe('recoverSmsFrame',ctx+'/pakage/allocation/toRecoverSms?accs='+accs,'短信回收',400,270);
	});
	
	$("#tt1").tree({
		url:ctx+"/orgGroup/get_all_group_user_json",
		checkbox:true,
		onlyLeafCheck: true,
		onSelect:function(node,checked){
			var cknodes =$('#tt1').tree('getChecked', 'checked');
            for (var i = 0; i < cknodes.length; i++) {
                if (cknodes[i].id != node.id) {
                    $('#tt1').tree("uncheck", cknodes[i].target);
                }
            }
            if (node.checked) {
                $('#tt1').tree('uncheck', node.target);

            } else {
                $('#tt1').tree('check', node.target);
            }
		},
		onLoadSuccess:function(node,data){
			$("#tt1").tree("collapseAll");
			for(var i=0;i<data.length;i++){
				$("#tt1").tree("expand",data[i].target);
			}
			var oas = $("#accountsStr").val();
			if(oas != null && oas != ''){
				var text='';
				$.each(oas.split(','),function(index,obj){
					var n = $("#tt1").tree("find",obj);
					text+=n.text+',';
					$("#tt1").tree("check",n.target).tree("expandTo",n.target);
				});
				if(text != ''){
					text=text.substring(0,text.length-1);
					$("#userNames").val(text);
				}else{
					$("#userNames").val("帐号");
				}
			}
			
			$(this).find('span.tree-checkbox').unbind().click(function () {
                $('#tt1').tree('select', $(this).parent());
                return false;
            });
			//如果叶子节点为部门的 checkbox隐藏
	           var roots =  $("#tt1").tree("getRoot");
	           var childs = $("#tt1").tree("getChildren",roots);
	           for( var i = 0 ; i < childs.length ; i++){
	        	   var item = childs[i];
	        	   if( item.attributes.type &&item.attributes.type == "G" ){
	        		   $(item.target).find(".tree-checkbox").remove();
	        	   }
	           }
		}
	});
	
	$("#tt2").tree({
		url:ctx+"/orgGroup/get_all_group_json",
		checkbox:true,
		onLoadSuccess:function(nodes,data){
			var oas = $("#groupIdStr").val();
			if(oas != null && oas != ''){
				var text='';
				$.each(oas.split(','),function(index,obj){
					var n = $("#tt2").tree("find",obj);
					text+=n.text+',';
					$("#tt2").tree("check",n.target).tree("expandTo",n.target);
				});
				if(text != ''){
					text=text.substring(0,text.length-1);
					$("#groupNameStr").val(text);
				}
			}else{
				var text = '';
				$.each(data,function(index,node){
					$("#tt2").tree("check",node.target);
				});
				var nodes = $("#tt2").tree("getChecked");
				$.each(nodes,function(index,node){
					text+=node.text+',';
				});
				if(text != ''){
					text=text.substring(0,text.length-1);
					$("#groupNameStr").val(text);
				}
			}
		}
	});
	
	$("#tt3").tree({
		url:ctx+"/orgGroup/get_all_group_user_json",
		checkbox:true,
		onlyLeafCheck: true,
		onSelect:function(node,checked){
			var cknodes =$('#tt3').tree('getChecked', 'checked');
            for (var i = 0; i < cknodes.length; i++) {
                if (cknodes[i].id != node.id) {
                    $('#tt3').tree("uncheck", cknodes[i].target);
                }
            }
            if (node.checked) {
                $('#tt3').tree('uncheck', node.target);

            } else {
                $('#tt3').tree('check', node.target);
            }
		},
		onLoadSuccess:function(node,data){
			$("#tt3").tree("collapseAll");
			for(var i=0;i<data.length;i++){
				$("#tt3").tree("expand",data[i].target);
			}
			var oas = $("#operateAcc").val();
			if(oas != null && oas != ''){
				var text='';
				$.each(oas.split(','),function(index,obj){
					var n = $("#tt3").tree("find",obj);
					text+=n.text+',';
					$("#tt3").tree("check",n.target).tree("expandTo",n.target);
				});
				if(text != ''){
					text=text.substring(0,text.length-1);
					$("#operateName").val(text);
				}else{
					$("#operateName").val("帐号");
				}
			}
			
			$(this).find('span.tree-checkbox').unbind().click(function () {
                $('#tt3').tree('select', $(this).parent());
                return false;
            });
			//如果叶子节点为部门的 checkbox隐藏
	           var roots =  $("#tt3").tree("getRoot");
	           var childs = $("#tt3").tree("getChildren",roots);
	           for( var i = 0 ; i < childs.length ; i++){
	        	   var item = childs[i];
	        	   if( item.attributes.type &&item.attributes.type == "G" ){
	        		   $(item.target).find(".tree-checkbox").remove();
	        	   }
	           }
		}
	});
	
	// 列表排序
});

var setDdate = function(i){
	$('#s_startDate').val('');
	$('#s_endDate').val('');
	$("#dDateType").val(i);
}

var seleTree=function(){
	var nodes = $('#tt1').tree('getChecked', 'checked');
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
	}else{
		if($("#userNames").attr("data-defaultValue")){
			names = $("#userNames").attr("data-defaultValue");
		}else{
			names="帐号";
		}
	}
	$("#accountsStr").val(accs);
	$("#userNames").val(names);
	
}

var unCheckTree=function(){
	var nodes = $('#tt1').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#tt1').tree("uncheck",obj.target);
	});
}

var seleTree3=function(){
	var nodes = $('#tt3').tree('getChecked', 'checked');
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
	}else{
		if($("#userNames").attr("data-defaultValue")){
			names = $("#userNames").attr("data-defaultValue");
		}else{
			names="帐号";
		}
	}
	$("#operateAcc").val(accs);
	$("#operateName").val(names);
	
}

var unCheckTree3=function(){
	var nodes = $('#tt3').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#tt3').tree("uncheck",obj.target);
	});
}

var seleGroupTree=function(){
	var nodes = $('#tt2').tree('getChecked', 'checked');
	var groupIds = "";
	var groupNames = "";
	$.each(nodes,function(index,obj){
		groupIds+=obj.id+",";
		groupNames+=obj.text+",";
	});
	if(groupIds != ""){
		groupIds=groupIds.substring(0,groupIds.length-1);
		groupNames=groupNames.substring(0,groupNames.length-1);
	}else{
		groupNames="所在部门";
	}
	$("#groupIdStr").val(groupIds);
	$("#groupNameStr").val(groupNames);
	
}

var unCheckGroupTree=function(){
	var nodes = $('#tt2').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#tt2').tree("uncheck",obj.target);
	});
}