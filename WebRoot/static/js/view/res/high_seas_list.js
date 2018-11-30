$(function(){
	loadGroupList("0");
	//全选
	/*表单优化*/
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
    
    $(".reason-li>a").on("click",function(e){
    	var _this = $(this);
    	var _this_val = _this.text();
    	document.getElementById("reason").value = _this_val;
    });
    
    $("#nDate").dateRangePicker({
    	showShortcuts:false,
        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
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
        $("#nDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
//        s.css("z-index",z);
    });
    
    $("#lDate").dateRangePicker({
    	showShortcuts:false,
        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
    	var s_t = '';
    	var e_t = '';
    	if(obj.date1 != 'Invalid Date'){
    		$('#s_startActionDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    		s_t = moment(obj.date1).format('YY.MM.DD');
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#s_endActionDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    		e_t = moment(obj.date2).format('YY.MM.DD');
    	}
    	var s = $(this).parents('.select');
    	var dt=s.children("dt");
        var dd=s.children("dd");
        dt.html(s_t+'/'+e_t);
        $("#lDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
//        s.css("z-index",z);
    });
    
    /***
     * 处理选中 查看共享资源
     */
    $("#checkShare").on('ifChecked',function(){
    	$("#h_isShareRes").val("1");
    	if(window.top.shrioAuthObj('HighSeasCust_recycleUnAssign')) $("#recyleBtn").hide();
    });
    
    $("#checkShare").on('ifUnchecked',function(){
    	$("#h_isShareRes").val("0");
    	if(window.top.shrioAuthObj('HighSeasCust_recycleUnAssign')) $("#recyleBtn").show();
    });
    
    /***
     * 左侧分组点击
     */
    $(".hyx-hsm-left dl dd").live("click",function(){
    	var groupId = $(this).attr("groupId");
    	if(groupId == 'undefined'){//未分组
    		$("#h_resGroupId").val("");
    	}else{
    		$("#h_resGroupId").val(groupId);
    	}
    	if($(this).hasClass("dd-hover")){
    		return;
    	}else{
    		$(".hyx-hsm-left dl dd").removeClass("dd-hover");
    		$(this).addClass("dd-hover");
    	}
    	loadData();
    });
    
    /***
     * 客户再分配
     */
    $("#distributeBtn").click(function(){
    	var ids = '';
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				ids+=$(obj).attr('id').split('_')[1]+',';
			}
		});
		if(ids.length == 0){
			window.top.iDialogMsg("提示",'请先选择资源！');
			return;
		}
		ids=ids.substring(0,ids.length-1);
		var module = $(this).attr("module");
		var poolType = $("#highSeaType").val();
		pubDivShowIframe('distribute', ctx+'/res/sea/toDistribute?ids='+ids+'&module='+module+'&poolType='+poolType, '重新分配',310,390);
    });
    
    /***
     * 删除
     */
    $("#delBtn").click(function(){
    	var ids = '';
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				ids+=$(obj).attr('id').split('_')[1]+',';
			}
		});
		if(ids.length == 0){
			window.top.iDialogMsg("提示",'请先选择资源！');
			return;
		}
		ids=ids.substring(0,ids.length-1);
		var module = $(this).attr("module");
		var poolType = $("#highSeaType").val();
		pubDivDialog("res_remove_cust","是否确认删除？",function(){
			$.ajax({
				url:ctx+'/res/sea/delBatchCust',
				type:'post',
				data:{ids:ids,module:module,poolType:poolType},
				dataType:'json',
				error:function(){},
				success:function(data){
					if(data == '1'){
						window.top.iDialogMsg("提示",'删除成功!');
						refreshPageData(pa.pageParameter());
					}else{
						window.top.iDialogMsg("提示",'删除失败!');
					}
				}
			});
		});
    });
    
    /***
     * 清空
     */
    $("#cleanBtn").click(function(){
    	pubDivShowIframe('clean_cust', ctx+'/res/sea/toClean?'+$('#queryForm').serialize(),'清空',450,500);
    });
    
    /***
     * 回收资源
     */
    $("#recyleBtn").click(function(){
    	pubDivShowIframe('recyle_cust', ctx+'/res/sea/toRecyle','公海客户回收到待分配资源',450,500);
    })
    
    /***
     * 取回
     */
    $("#getBackBtn").click(function(){
    	var ids = '';
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				ids+=$(obj).attr('id').split('_')[1]+',';
			}
		});
		if(ids.length == 0){
			window.top.iDialogMsg("提示",'请先选择资源！');
			return;
		}
		ids=ids.substring(0,ids.length-1);
		var module = $("#highSeaType").val();
		pubDivDialog("get_back_cust","是否确认取回？",function(){
			$.ajax({
				url:ctx+'/res/sea/updateBatchResourceOrCust',
				type:'post',
				data:{ids:ids,module:module},
				dataType:'html',
				error:function(){},
				success:function(data){
					if(data == '1'){
						window.top.iDialogMsg("提示","取回成功!");
						refreshPageData(pa.pageParameter());
					}else if(data == '-1'){
						window.top.iDialogMsg("提示","取回失败!");
					}else{
						window.top.iDialogMsg("提示",data);
					}
				}
			});
		});
    });
    
    
    /***
     * 取回意向
     */
    $("#getBackBtnYx").click(function(){
    	var ids = '';
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				ids+=$(obj).attr('id').split('_')[1]+',';
			}
		});
		if(ids.length == 0){
			window.top.iDialogMsg("提示",'请先选择资源！');
			return;
		}
		ids=ids.substring(0,ids.length-1);
		var highSeaType = $("#highSeaType").val();
		pubDivDialog("get_back_cust","是否确认取回为意向客户？",function(){
			$.ajax({
				url : ctx + '/res/resmanage/getCustLimitNum',
				type : 'post',
				data : {
					code : 'DATA_10003'
				},
				success : function(data) {
					if (data == '1') {
						window.top.iDialogMsg("提示", "超出个人拥有意向客户上限！");
						return false;
					}
					$.ajax({
						url:ctx+'/res/sea/updateBatchResourceToCust',
						type:'post',
						data:{ids:ids,module:highSeaType},
						dataType:'html',
						error:function(){},
						success:function(data){
							if(data == '1'){
								window.top.iDialogMsg("提示","取回意向成功!");
								refreshPageData(pa.pageParameter());
							}else if(data == '-1'){
								window.top.iDialogMsg("提示","取回意向失败!");
							}else{
								window.top.iDialogMsg("提示",data);
							}
						}
					});
				}
			});
		});
    });
    
    $("#progressBtn").click(function(e){
		e.stopPropagation();
		appendtos("progress_bar","HIGH_SEA_PROGRESS");
		$(".progress_bar").show();
	});
    //查询按钮
/*	$("#query").on("click",function(e){
		loadData();
	});*/
    
    //切换公海客户/共享资源
    $(".high_sea_type").on("click",function(){
    	var _this = $(this);
    	var index = _this.index();
    	$("#highSeaType").val(index);
    	$(".high_sea_type").removeClass("select");
    	_this.addClass("select");
    	setSearch(index);
    	loadData();
    });
  //客户卡片
  $("a[id^=cardInfo_]").live('click',function(){
		var custId = $(this).attr("id").split("_")[1];
		var custName = $(this).attr("custName")||"客户卡片";
		window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName);
  });
  //取回资源
  $("a[id^=getBack_]").live('click',function(){
	  var custId = $(this).attr("id").split("_")[1];
	  var module = $("#highSeaType").val();
	  pubDivDialog("get_back_cust","是否确认取回？",function(){
			$.ajax({
				url:ctx+'/res/sea/updateBatchResourceOrCust',
				type:'post',
				data:{ids:custId,module:module},
				dataType:'html',
				error:function(){},
				success:function(data){
					if(data == '1'){
						window.top.iDialogMsg("提示","取回成功!");
						refreshPageData(pa.pageParameter());
					}else if(data == '-1'){
						window.top.iDialogMsg("提示","取回失败!");
					}else{
						window.top.iDialogMsg("提示",data);
					}
				}
			});
		});
  });
  //取回意向
  $("a[id^=getBackToInt_]").live('click',function(){
	  var custId = $(this).attr("id").split("_")[1];
	  var highSeaType = $("#highSeaType").val();
	  pubDivDialog("get_back_cust","是否确认取回为意向客户？",function(){
			$.ajax({
				url : ctx + '/res/resmanage/getCustLimitNum',
				type : 'post',
				data : {
					code : 'DATA_10003'
				},
				success : function(data) {
					if (data == '1') {
						window.top.iDialogMsg("提示", "超出个人拥有意向客户上限！");
						return false;
					}
					$.ajax({
						url:ctx+'/res/sea/updateBatchResourceToCust',
						type:'post',
						data:{ids:custId,module:highSeaType},
						dataType:'html',
						error:function(){},
						success:function(data){
							if(data == '1'){
								window.top.iDialogMsg("提示","取回意向成功!");
								refreshPageData(pa.pageParameter());
							}else if(data == '-1'){
								window.top.iDialogMsg("提示","取回意向失败!");
							}else{
								window.top.iDialogMsg("提示",data);
							}
						}
					});
				}
			});
		});
  });
  
  $("input[name=searchOwnerType]").iCheck({
		radioClass: 'iradio_minimal-green'
  });
  
  $("input[name=searchOwnerType]").on('ifClicked',function(){
		var nodes = $('#tt1').tree('getChecked');
		if(nodes.length > 0){
			$.each(nodes,function(index,obj){
				 $('#tt1').tree("uncheck",obj.target);
			});
		}
  });
  
  $("input[name=searchUpdateType]").iCheck({
		radioClass: 'iradio_minimal-green'
  });
  
  $("input[name=searchUpdateType]").on('ifClicked',function(){
		var nodes = $('#tt2').tree('getChecked');
		if(nodes.length > 0){
			$.each(nodes,function(index,obj){
				 $('#tt2').tree("uncheck",obj.target);
			});
		}
});
  
  $("#tt1").tree({
		url:ctx+"/orgGroup/get_high_sea_user_json",
		checkbox:true,
		onLoadSuccess:function(node,data){
			$("#tt1").tree("collapseAll");
			for(var i=0;i<data.length;i++){
				$("#tt1").tree("expand",data[i].target);
			}
			var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
			var oas = $("#ownerAccsStr").val();
			if(searchOwnerType == '1'){
				$("#ownerNameStr").val("查看全部");
			}else if(searchOwnerType == '2'){
				$("#ownerNameStr").val("查看自己");
			}else if(oas != null && oas != ''){
				var text='';
				$.each(oas.split(','),function(index,obj){
					var n = $("#tt1").tree("find",obj);
					if(n != null){
						text+=n.text+',';
						$("#tt1").tree("check",n.target).tree("expandTo",n.target);
					}
				});
				if(text != ''){
					text=text.substring(0,text.length-1);
					$("#ownerNameStr").val(text);
				}else{
					$("#ownerNameStr").val("所有者");
				}
			}
		},
		onCheck:function(node,isCheck){
			var nodes = $('#tt1').tree('getChecked');
			if(nodes.length > 0){
				$("input[name=searchOwnerType]").iCheck('uncheck');
			}else if($("input[name=searchOwnerType]:checked").length == 0){
				$("input[name=searchOwnerType]:eq(0)").iCheck('check');
			}
		}
	});
  
  
  $("#tt2").tree({
		url:ctx+"/orgGroup/get_high_sea_user_json",
		checkbox:true,
		onLoadSuccess:function(node,data){
			$("#tt2").tree("collapseAll");
			for(var i=0;i<data.length;i++){
				$("#tt2").tree("expand",data[i].target);
			}
		},
		onCheck:function(node,isCheck){
			var nodes = $('#tt2').tree('getChecked');
			if(nodes.length > 0){
				$("input[name=searchUpdateType]").iCheck('uncheck');
			}else if($("input[name=searchUpdateType]:checked").length == 0){
				$("input[name=searchUpdateType]:eq(0)").iCheck('check');
			}
		}
	});
  
  
  $(".com-moTag > a").click(function(){
		 $(this).parent().find(".e-hover").removeClass("e-hover");
		 $(this).addClass("e-hover");
		 var input = $(this).parent().attr("data-input");
		 $(input).val($(this).attr("nt"));
		 loadData();
	  });
});

function loadGroupList(isShareRes){
	$("#groupDl>dd").remove();
	$.ajax({
		url:ctx+'/res/sea/highSeasLeftData',
		type:'post',
		data:{isShareRes:isShareRes},
		dataType:'json',
		error:function(){},
		success:function(data){
			console.log(data);
			var render_data = [{
				"id":"",
				"text":"所有资源",
				"type":"none",
				"level":"none",
				"isSharePool":"none",
				"inputAcc":"none",
				"children":data,
			}];
			
	//		console.log(render_data);
			
			$(".resource-group-tree").tree({
				data: render_data,
				formatter: function(node){
					var nodeText = "<label class='tree-node-label' data-id='"+node.id+"' data-share-pool='"+node.isSharePool+"' title='"+node.text+"'>" + node.text + "</label>";
					return nodeText;
				},
				onSelect:function(node){
					var ids = new Array(); 
					var groupId = node.id;
					ids.push(groupId);
					if(node.level == 0){
						var childs = $(this).tree("getChildren",node.target);
						$(childs).each(function(index,obj){
							ids.push(obj.id);
						});
					}
					$("#h_resGroupId").val(ids.join(","));
					loadData();
				},
				onLoadSuccess: function(node,data){
					var selectedNode = $(".resource-group-tree").tree("find",$("#h_resGroupId").val());
					$(selectedNode.target).addClass("tree-node-selected");
					
					$(".tree-node").hover(function(e){
						e.stopPropagation();
						$(this).addClass("tree-node-hover");
					},function(e){
						e.stopPropagation();
						$(this).removeClass("tree-node-hover");
					});
				}
			});
		},
		error: function(status){
			alert("分组加载失败！");
		}
	});
}

var setNDate = function(i){
	$('#s_startDate').val('');
	$('#s_endDate').val('');
	$("#nDateType").val(i);
}

var setLDate = function(i){
	$('#s_startActionDate').val('');
	$('#s_endActionDate').val('');
	$("#lDateType").val(i);
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
		$("#osType").val("3");
	}else{
		var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
		if(searchOwnerType == '1'){
			names="查看全部";
		}else{
			names="查看自己";
		}
		$("#osType").val(searchOwnerType);
	}
	$("#ownerAccsStr").val(accs);
	$("#ownerNameStr").val(names);
}

var seleUpdateTree=function(){
	var nodes = $('#tt2').tree('getChecked', 'checked');
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
		$("#usType").val("3");
	}else{
		var searchUpdateType = $("input[name=searchUpdateType]:checked").val();
		if(searchUpdateType == '1'){
			names="放弃人-查看全部";
		}else{
			names="放弃人-查看自己";
		}
		$("#usType").val(searchUpdateType);
	}
	$("#updateAccsStr").val(accs);
	$("#updateNameStr").val(names);
}

var unCheckTree=function(tid){
	var nodes = $('#'+tid).tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#'+tid).tree("uncheck",obj.target);
	});
}

var setSearch=function(s){
	if($("#h_isShareRes").val() == s){
		return;
	}
	$("#h_isShareRes").val(s);
	$("#h_resGroupId").val("");
	loadGroupList(s);
}
