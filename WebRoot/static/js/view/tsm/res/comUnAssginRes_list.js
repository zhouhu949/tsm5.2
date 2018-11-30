/*******************************************************************************
 * 公司资源_专属JS文件
 * 
 * @author wuwei
 * @date 2013-10-23
 ******************************************************************************/
$(function() {
	
	$.ajax({
		url: $(".resource-group-tree").attr("data-url")+'?v='+new Date().getTime(),
		type: "get",
		success: function(data){
			
			var render_data = [{
					"id":"all",
					"text":"所有资源",
					"type":"none",
					"level":"none",
					"isSharePool":"none",
					"inputAcc":"none",
					"children":data,
			}];
			
//			console.log(render_data);
			var isShare = $("#isShare").val();
			$(".resource-group-tree").tree({
				data: render_data,
				formatter: function(node){
					var nodeText = "<label class='tree-node-label' data-id='"+node.id+"' data-share-pool='"+node.isSharePool+"' title='"+node.text+"'>" + node.text + "</label>";
					if(node.isSharePool == 1 && isShare == 1){
						node.level == 1 ? nodeText += '<span class="min-share-state-size"></span>':'';
					}
					nodeText += '<span class="operate-icon">';
					if(node.level == 0){
						nodeText += '<a data-id="'+node.id+'" href="javascript:;" title="添加"  class="min-new-add group-add"></a>';
					}
					if(node.isSharePool == 0 && isShare == 1){
						node.level == 1 ? nodeText += '<a data-id="'+node.id+'" data-is-share-pool="'+node.isSharePool+'" href="javascript:;" title="共享到公海"  class="min-share-to-high-seas group-share"></a>':'';
					}else if(node.isSharePool == 1 && isShare == 1){
						node.level == 1 ? nodeText += '<a data-id="'+node.id+'" data-is-share-pool="'+node.isSharePool+'" href="javascript:;" title="取消共享"  class="min-cancel-share group-cancel-share"></a>':'';
					}
					if(node.type == 0){
						if(node.inputAcc == $("#account").val()){
							nodeText += '<a data-authority="resUnAssign_editResGroup" data-id="'+node.id+'" data-level="'+node.level+'" href="javascript:;" title="修改" class="min-edit group-edit"></a>';
							nodeText += '<a data-authority="resUnAssign_deleteResGroup" data-id="'+node.id+'" data-level="'+node.level+'" href="javascript:;" title="删除" class="min-dele group-dele"></a>';
						}
					}
					nodeText += '</span>';
					return nodeText;
				},
				onSelect:function(node){
					var groupId = node.id;
					var isSharePool = node.isSharePool;
					$('#isSharePool').val(isSharePool);
					$("#groupId").val(groupId);
					$("#level").val(node.level);
					if(node.level != 1){
						$("#importId").attr("disabled",true);
					}else{
						$("#importId").attr("disabled",false);
					}
					findMembers(groupId);
				},
				onLoadSuccess: function(node,data){
					var selectedNode = $(".resource-group-tree").tree("find",$("#groupId").val());
					$(".resource-group-tree").tree("select",selectedNode.target);
					
					$(".tree-node").hover(function(e){
						e.stopPropagation();
						$(this).addClass("tree-node-hover");
					},function(e){
						e.stopPropagation();
						$(this).removeClass("tree-node-hover");
					});
					
					$(".group-add").on("click",function(e){
						e.stopPropagation();
						var _this = $(this);
						addOrEditGroup("",_this.attr("data-id"));
					});
				
					$(".group-edit").on("click",function(e){
						e.stopPropagation();
						var _this = $(this);
						var groupId = _this.attr("data-id");
						var level = _this.attr("data-level");
						$("#level").val(level);
						$("#groupId").val(groupId);
						if(level == 0){
							addOrEditClass(groupId);
						}else if(level == 1){
							addOrEditGroup(groupId);
						}
					});
					
					$(".group-dele").on("click",function(e){
						e.stopPropagation();
						var _this = $(this);
						var groupId = _this.attr("data-id");
						var level = _this.attr("data-level");
						$("#groupId").val(groupId);
						delGroup(groupId,level);
					});
					
					$(".group-share,.group-cancel-share").on("click",function(e){
						e.stopPropagation();
						var _this = $(this);
						var groupId = _this.attr("data-id");
						var type = _this.attr("data-is-share-pool");
						if($('#isShare').val()!='1'){
							window.top.iDialogMsg("提示", '请开启共享公海客户设置！');
							return ;
						}
						setShare(groupId,type);
					});
				}
			});
		},
		error: function(status){
			alert("分组加载失败！");
		}
	});
	
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	};
	var aa=1;
	loadData();
/*	$("#query").click(function(){
		loadData();
	});*/
	
	var url = ctx+"/orgGroup/get_group_json";
	$("#deptTree").tree({
		formatter:function(node){
			var nodeText = node.text;			
			return nodeText;
		},
		url:url,
		checkbox:true,
		onBeforeLoad:function(node, param){ 
		    loading=true; 
		}, 
		onLoadSuccess:function(node,data){
			var pid ;
			var accs = $('#deptId').val();
			var arrs  = accs.split(',');
			if (accs != null && accs != '') {
				var arrs = accs.split(',');
				for (var i = 0; i < arrs.length; i++) {
					pid = arrs[i];
					pid = $('#deptTree').tree('find', pid);
					if(pid!=null && pid!=undefined){
					   $('#deptTree').tree('check', pid.target);
					}
				}
				$('#deptTree').tree("expandAll");
			}				
			//移除图标
			$(".tree-icon").remove();
		},
		onExpand:function(node){
			$(".tree-title").removeClass("tree-folder-open");
		},
		onCheck:function(node,checked){
		},
		onSelect:function(node){
		}
	});
	
	//选择部门
	$("#checkedId").click(function() {
		var nodes = $('#deptTree').tree('getChecked');
		if (nodes == null) {
			alert('请选择账号！');
			return;
		}
		var mark = 0;
		var deptIds ="";
        //M-成员；G-分组
		$(nodes).each(function(index,obj){
				deptIds+=obj.id.substring(0,obj.id.length)+',';
				mark =mark+1;
		});
        if(mark==0){
        	$('#deptId').val("");
        }else{
        	$('#deptId').val(deptIds);
        }
	});
	
	//清空
	$('#clearId').click(function(){
		var nodes = $('#deptTree').tree('getChecked');
		 $(nodes).each(function(index,obj){
        	$('#deptTree').tree('uncheck', obj.target);
        });
	  $('#deptId').val('');
	});

/*	var isSharePool = $('#isSharePool').val();
	if(isSharePool=='1'){
		$('#btn_share_pool_id').html('<i class="min-rebut"></i><label class="lab-mar">取消共享</label>');
	}else{
		$('#btn_share_pool_id').text('共享到公海');
	}*/

	/* 下拉框部分 */
	$('.mail').click(function() {
		$(this).find('.drop').fadeToggle();
	});
	$('.hyx-cm-new').find('.load').each(function() {
		if ($(this).find('.sp').text().length >= 23) {
			$(this).find('.sp').text($(this).find('.sp').text().substr(0, 23) + '...');
		}
	});

	$('.hyx-cm-new').find('.load').mouseover(function() {
		$(this).find('.drop').fadeIn(1);
	});
	$('.hyx-cm-new').find('.load').mouseleave(function() {
		$(this).find('.drop').fadeOut(1);
	});

	// 变更分组
	$('#changeGroupId').click(function() {
		var ids = '';
		$('input[id^=chk_]').each(function(index, obj) {
			if ($(obj).is(':checked')) {
				ids += $(obj).attr('id').split('_')[1] + ',';
			}
		});
		if (ids.length == 0) {
			window.top.iDialogMsg("提示", '请先选择资源！');
			return;
		}
		ids = ids.substring(0, ids.length - 1);
		changeGroup(ids);
	});
	// 分配资源
	$('#assignBtn').click(function() {
		var ids = '';
		$('input[id^=chk_]').each(function(index, obj) {
			if ($(obj).is(':checked')) {
				ids += $(obj).attr('id').split('_')[1] + ',';
			}
		});
		if (ids.length == 0) {
			window.top.iDialogMsg("提示", '请先选择资源！');
			return;
		}
		ids = ids.substring(0, ids.length - 1);
		assginResource(ids);
	});
	$('#importId').click(function() {
	   var  resGroupId  = $('#groupId').val();
	    if(resGroupId == "all"){
	    	window.top.iDialogMsg("提示", '请选择分组！！');
	    	return false;
	    }
		pubDivShowIframe('import', ctx+'/resimp/page?resGroupId='+resGroupId+'&status=1&mark=4', '导入资源', 740, 500);
	});
	
	$('#importDetailId').click(function() {
		pubDivShowIframe('importDetail', ctx+'/resimp/page?status=1&mark=4&isDetail=1', '导入联系人', 740, 500);
	});
	
	$('#importResultId').click(function() {
		pubDivShowIframe('importResult',  ctx+'/resimp/result', '导入结果', 700, 550);
	});
	// 删除资源
	$('#deleteRes').click(function() {
		deleteRes();
	});
	// 清空资源
	$('#clearAll').click(function() {
		//clearAll();
		var $this = $(this);
		var taskId = $this.data("taskid");
		var groupId = $("#groupId").val() == 'all'?'':$("#groupId").val();
		var qKeyWord = $("#qKeyWordId").val();
		var startDate = $("#s_pstartDate").val();
		var endDate = $("#s_pendDate").val();
		var deptId = $("#deptId").val();
		var oDateType = $("#oDateType").val();
		var formData = $("#unAssginForm").serialize();
		pubDivDialog("res_remove_cust", "确认清空您所查询筛选之后的待分配资源？如果数据量大，请耐心等待！！！", function() {
			$.ajax({
				url : ctx + '/res/resmanage/clearMyRes?taskId='+taskId,
				type : 'post',
				timeout : 360000,
				/*data : {
					'resGroupId' : groupId,
					'qKeyWord' : qKeyWord,
					'deptId' : deptId,
					'oDateType' : oDateType,
					'startDate' : startDate,
					'endDate' : endDate
				},*/
				data: formData,
				dataType : 'html',
				error : function() {
					alert('网络异常，请稍后再试！');
				},
				success : function(data) {
					if (data == 0) {
						//window.top.iDialogMsg("提示", '清空所有资源成功！');
						//setTimeout('document.forms[0].submit()', 1000);
					} else {
						window.top.iDialogMsg("提示", '清空所有资源失败！');
					}
				},
				complete : function(XMLHttpRequest, status) {
					var waitMs = 10;
					if (status == 'timeout' || status == 'error') {
						if (status == 'timeout') {
							waitMs += 10000;
						} // 长连接超时，说明数据量大或DB处理慢，延时后再刷新
						setTimeout('document.forms[0].submit()', waitMs);
					}
				}
			});
			setTravl(taskId,3000)
		});
	});

	//放弃客户
	$('#giveUpBtn').click(function(){
		var ids = '';
		var operate_auth = true;
		var loginAcc = $("#loginAcc").val();
		$('input[id^=chk_]').each(function(index,obj){
			if($(obj).is(':checked')){
				if(loginAcc != $(obj).attr("owner")){
					operate_auth = false;
				}
				ids+=$(obj).attr('id').split('_')[1] + ',';
			}
		});
		if(!operate_auth){
			window.top.iDialogMsg("提示",'非自己资源不能放入公海！');
			return;
		}
		if(ids.length == 0){
			window.top.iDialogMsg("提示",'请先选择资源！');
			return;
		}
		ids=ids.substring(0,ids.length-1);
		pubDivDialog("res_remove_cust","是否确认放入公海？",function(){
			$.ajax({
				url:ctx+'/res/resmanage/removeCust',
				type:'post',
				data:{ids:ids,module:"1"},
				dataType:'html',
				error:function(){alert('网络异常，请稍后再试！')},
				success:function(data){
					if(data == 1){
						window.top.iDialogMsg("提示",'放弃客户成功！');
						setTimeout(function(){
						    refreshPageData(pa.pageParameter());
						}, 2000);
					}else{
						window.top.iDialogMsg("提示",'放弃客户失败！');
					}
				}
			});
		})
	});

	$('.reso-manag-e').click(function() {
		addOrEditClass("");
	});
/*	$('#btn_share_pool_id').click(function() {
		var groupId = $('#groupId').val();
		if(groupId=='' || groupId==null || groupId == 'all'){
			window.top.iDialogMsg("提示", '请选择分组！');
			return ;
		}
		if($('#isShare').val()!='1'){
			window.top.iDialogMsg("提示", '请开启共享公海客户设置！');
			return ;
		}
		var type = $('#isSharePool').val();
		setShare(groupId,type);
	});*/

		//开始拥有时间
	 $('#pDate').dateRangePicker({
		    showShortcuts:false,
	        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
	    }).bind('datepicker-apply',function(event,obj){
	    	var s_t = '';
	    	var e_t = '';
	    	if(obj.date1 != 'Invalid Date'){
	    		$('#s_pstartDate').val(moment(obj.date1).format('YYYY-MM-DD'));
	    		s_t = moment(obj.date1).format('YY.MM.DD');
	    	}
	    	if(obj.date2 != 'Invalid Date'){
	    		$('#s_pendDate').val(moment(obj.date2).format('YYYY-MM-DD'));
	    		e_t = moment(obj.date2).format('YY.MM.DD');
	    	}
	    	var s = $(this).parents('.select');
	    	var dt=s.children("dt");
	        var dd=s.children("dd");
	        dt.html(s_t+'/'+e_t);
	        $("#oDateType").val(5);
	        dd.slideUp(200);
	        dt.removeClass("cur");
	 });
	
	// 左侧
	$('.hyx-hsm-left').find('.item-list-content').each(function() {
		var groupId = $(this).attr("id").substring(7);
		var _this = $(this);
		_this.click(function(e) {
			e.stopPropagation();
			_this.parents(".hyx-hsm-left").find(".list-content-active").removeClass("list-content-active");
			_this.addClass("list-content-active");
			// 查找点击
/*			var isSharePool = $(this).parent().attr('isSharePool'); 
			$('#isSharePool').val(isSharePool);
			if(isSharePool=='1'){
				$('#btn_share_pool_id').html('<i class="min-rebut"></i><label class="lab-mar">取消共享</label>');
			}else{
				$('#btn_share_pool_id').text('共享到公海');
			}			*/
			$("#groupId").val(groupId);
			findMembers(groupId);
		});
	});
	
	$("a[id^=editInfo_]").live('click',function(){
		var custId = $(this).attr("id").split("_")[1];
		pubDivShowIframe('edit_'+custId,ctx+'/res/cust/toEditRes?custId='+custId,'信息编辑',500,570);
	});
	
	     $('#startScreen').click(function() {
			pubDivShowIframe('start_screen', ctx+'/phone/toChoose?keyWord=2', '开始筛查', 740,550);
		 });
		 $('#screenResult').click(function() {
			pubDivShowIframe('screen_result_id',  ctx+'/phone/chooseResult?moduleId=106&ownerAcc='+$('#account').val(), '筛查结果', 700, 550);
		 });
	
});
//进度条轮询函数
function setTravl(taskId,time){
	var content = '<div class="finished-prograss">处理中...需处理总数<span class="total">0</span>,当前完成<span class="finished">0</span></div><div id="p" style="width:250px;"></div>';
	var idialogId = "process_idialog";
	$.dialog({
		title:"处理中",
		id:idialogId,
		width:300,
		height:135,
		lock:true,
		content:content,
		show:function(){
			$('#p').progressbar({
			    value: 0
			});
		}
	})
	var t=setInterval(function(){
		$.ajax({
			url:ctx+"/progress/getProgress",
			data:{
				taskId:taskId
			},
			success:function(data){
				//console.log(data+new Date().getTime())
				if(data){
					if(data.status){
			    		var percent = (data.finished/ data.total).toFixed(2)*100;
			    		$('#p').progressbar({
						    value: percent
						});
			    		$(".finished-prograss>.total").text(data.total);
			    		$(".finished-prograss>.finished").text(data.finished);
			    		if(percent >= 100){//完成 成功
			    			$(".finished-prograss").html("操作成功！共操作"+data.total+"条数据");
			    			clearInterval(t);
			    			setTimeout(function(){
			    				window.top.iDialogMsg("提示", '清空所有资源成功！');
								setTimeout('document.forms[0].submit()', 1000);
			    				closeIdialog(idialogId)
			    	    	},500)
			    		}
			    	}else{//失败
			    		clearInterval(t);
			    		$('#p').progressbar({
						    value: 0
						});
			    		$(".finished-prograss").html(data.errorMsg)
			    		setTimeout(function(){
				    			window.top.iDialogMsg("提示", '清空所有资源成功！');
								setTimeout('document.forms[0].submit()', 1000);
			    				closeIdialog(idialogId)
			    	    	},500)
			    	}
				}
			}
		});
	},time)
}
function closeIdialog(id){
	$.dialog.get[id].remove();
}
// 共享分组
function setShare(groupId,type) {
	var title =type=="1"?"确定要取消共享公海池设置吗？":"确定要设置共享公海池吗？";
	var isSharePool = type=="1"?'0':"1";
	pubDivDialog("res_remove_cust", title, function() {
		$.ajax({
			url : $('#base').val() + '/res/resmanage/setShare',
			type : 'post',
			data : {
				'groupId' : groupId,"isSharePool": isSharePool
			},
			dataType : 'html',
			error : function() {
				alert('网络异常，请稍后再试！');
			},
			success : function(data) {
				var ok =type=="1"?"取消共享公海池成功":"设置共享公海池成功！"; 
				var failture =type=="1"?"取消共享公海池失败!":"设置共享公海池失败！"; 
				if (data == 0) {
					$('#isSharePool').val(isSharePool);
					window.top.iDialogMsg("提示", ok);
					setTimeout('document.forms[0].submit()', 1000);
				} else {
					window.top.iDialogMsg("提示", failture);
				}
			}
		});
	});
}
// 清空资源
function clearAll() {
}

// 删除资源
function deleteRes() {
	// 放弃客户
	var ids = '';
	$('input[id^=chk_]').each(function(index, obj) {
		if ($(obj).is(':checked')) {
			ids += $(obj).attr('id').split('_')[1] + ',';
		}
	});
	if (ids.length == 0) {
		window.top.iDialogMsg("提示", '请先选择资源！');
		return;
	}
	ids = ids.substring(0, ids.length - 1);
	var module = 'unAssgin';
	pubDivDialog("res_remove_cust", "是否删除资源？", function() {
		$.ajax({
			url : ctx + '/res/resmanage/deleteComRes',
			type : 'post',
			data : {
				ids : ids,
				module : module
			},
			dataType : 'html',
			error : function() {
				alert('网络异常，请稍后再试！');
			},
			success : function(data) {
				if (data == 0) {
					window.top.iDialogMsg("提示", '删除资源成功！');
					setTimeout('document.forms[0].submit()', 1000);
				} else {
					window.top.iDialogMsg("提示", '删除资源失败！');
				}
			}
		});
	});
}
/**
 * 新增或修改团队分组
 */
function addOrEditGroup(groupId,pid) {
	var tmpTitle = groupId == '' ? '新增分组' : '修改分组';
	pubDivShowIframe("groupIdDialog", $('#base').val() + '/res/group/toResGroup?groupId=' + groupId + '&pid=' + pid, tmpTitle, 300, 180);
}

function addOrEditClass(groupId,pid) {
	var tmpTitle = groupId == '' ? '新增分类' : '修改分类';
	pubDivShowIframe("classIdDialog", $('#base').val() + '/res/group/toAddResClass?groupId=' + groupId, tmpTitle, 300, 180);
}

function findMembers(groupId) {
	loadData();
}

function delGroup(groupId,level) {
	var delUrl = $('#base').val() + "/res/group/delResourceGroup";
	var tips = "";
	if(level == 0){
		tips = "是否要删除该资源分类？删除后该资源分类下的资源分组将归类于未分类下！";
	}else if(level == 1){
		tips = "是否删除该资源分组？删除后该资源分组下的全部资源将转移至未分组下！";
	}
	pubDivDialog('delGroupId', tips, function() {
		$.ajax({
			url : delUrl,
			data: {
				"groupId": groupId,
				"level": level
			},
			type : 'post',
			error : function(XMLHttpRequest, textStatus) {
			},
			success : function(data) {
				if (data == 0) {
					// 刷新主页面
					$('#groupId').val('');
					$('#isSharePool').val("0");
					setTimeout('document.forms[0].submit()', 100);
//					location.href = "/res/resmanage/queryComUnAssginResList";
				}
			}
		});
	});
}

// 变更分组
function changeGroup(ids) {
	var tmpTitle = '变更分组';
	var module = 'unAssgin';
	pubDivShowIframe("changeGroup", $('#base').val() + '/res/resmanage/toChangeGroup?ids=' + ids + '&module=' + module, tmpTitle, 270, 350);

}

/**
 * 分配资源
 */
function assginResource(ids) {
	pubDivShowIframe('againassginResource', $('#base').val() + "/res/resmanage/toassginResource?ids=" + ids, '资源分配', 300, 350);
}
function setPdate(i) {
	$('#s_pstartDate').val('');
	$('#s_pendDate').val('');
	$("#oDateType").val(i);
}
function loadData(page){

    var _param = $("#unAssginForm").serialize() ;
    loaderAjax(_param,$("[name='showCount']").length>0? "page.showCount="+ $("[name='showCount']").val(): "");
    $('#checkAll').iCheck('uncheck');
}

function refreshPageData(page){
    var table = $(".ajax-table");
    var _param=table.attr("data-param")
    var pageStr="&";
    if(page){
        pageStr = "&page.currentPage="+page.currentPage+"&page.totalResult="+page.totalResult+"&page.showCount="+page.showCount;
    }
    loaderAjax(_param,pageStr);
}


function loaderAjax(_param,pageStr){
    var table = $(".ajax-table");
    table.attr("data-param",_param);
	var _url=ctx+"/res/resmanage/getUnAssginResJson";
	searchingDataTip();
	var timeout = setTimeout(function(){
		$(".tip_search_text").hide();
		$(".tip_search_error").text("网络繁忙，请耐心等候或重新查询！");
		$(".tip_search_error").show();
	},10000);
	$.ajax({
		type:"post",
		url	:_url,
        data:_param+"&"+pageStr,
		success	:function(data){
			loadTable(data.list,data.defList);
			$(".tip_search_data").hide();
			clearTimeout(timeout);
			pa.load("new_page",data.item.page,refreshPageData,$(".ajax-table"));
			if($("[data-authority]") && $("[data-authority]").length > 0){
				isAuthority("data-authority");
			}
			$('#checkAll').iCheck('uncheck');
		},
		error:function(data){
			clearTimeout(timeout);
			$(".tip_search_text").hide();
			$(".tip_search_error").text("网络异常，请重新查询！");
			$(".tip_search_error").show();
		}
	});
}

function loadTable(list,defList){
	var html='';
	for(var i in list){
		var res = list[i];
		var resCustId = res.resCustId;
		var inputDate = (res.inputDate != null && res.inputDate !=0)? new Date(res.inputDate).Format("yyyy-MM-dd hh:mm:ss"):"";
		var mobilephone = res.mobilephone ==null ? "":res.mobilephone;
		var telphone = res.telphone ==null ? "":res.telphone;
		var groupName = res.groupName ==null ? "":res.groupName;
		var deptName =  res.deptName ==null ? "":res.deptName;
		var company = res.company ==null ? "":res.company;
		var name = res.name ==null ? "":res.name;
		var mainLinkman = res.mainLinkman ==null ? "":res.mainLinkman;
		var custName = "";
		var cardName = company == ""?name:company;
//		alert(getInputStatus(res.inputStatus ));
		var isState = $("#isState").val();
		if(isState == 1){
			custName =name;
		}else{
			custName = company;
		}
		var man = "";
		if(isState == 1){
			man =mainLinkman;
		}else{
			if(name ==""){
				man =mainLinkman;
			}else{
				man =name;
			}
		}
		
		
		html +=
		"<tr class='"+(i%2==0?'':'sty-bgcolor-b')+"'>"+
		"   <td style='width: 30px;'><div class='overflow_hidden w30 skin-minimal'><input type='checkbox'  id='chk_"+resCustId +"'/></div></td>" +
		"   <td style='width: 50px;'><div class='overflow_hidden w60 link'> <a href='javascript:;' data-authority='base_custEdit' id='editInfo_"+emptyTransfer(res.resCustId)+"' class='icon-edit' title='修改资源'></a><a href='###'  onclick='toCard(\""+resCustId +"\",\""+cardName +"\")'  class='icon-cust-card' title='客户卡片'></a></div></td>" +
		"   <td><div class='overflow_hidden w140' title='"+custName +"'>"+custName +"</div></td>"+
		"   <td><div class='overflow_hidden w90' title='"+man +"'>"+man +"</div></td>"+
		"   <td hidevalue='"+ inputDate +"'><div class='overflow_hidden w120' title='"+inputDate+"'>"+inputDate+"</div></td>";
		/*if(isState == 0){
			html += "<td><div class='overflow_hidden w140' title='"+company +"'>"+company +"</div></td>";
		}*/
		html +=
			"   <td><div class='overflow_hidden w120'  phone='tel'  title='"+mobilephone +"' id='mobilehidden_"+mobilephone +"'>"+mobilephone +"</div></td>"+
			"   <td><div class='overflow_hidden w120'  phone='tel'  title='"+telphone +"' id='telhidden_"+telphone+"'>"+telphone +"</div></td>" +
			"   <td><div class='overflow_hidden w90' title='"+groupName +"'>"+groupName+"</div></td>"+
			"   <td><div class='overflow_hidden w90' title='"+deptName +"'>"+deptName +"</div></td>"+
			"   <td><div class='overflow_hidden w50' title='"+getInputStatus(res.inputStatus )+"'>"+getInputStatus(res.inputStatus )+"</div></td>" +
			"   <td><div class='overflow_hidden w120'  phone='tel'  title='"+mobilephone +"' id='mobilehidden_"+mobilephone +"'>"+mobilephone +"</div></td>";
			html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(res.locationArea)+"'>"+emptyTransfer(res.locationArea)+"</div></td>";
			html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(res.companyTrade)+"'>"+emptyTransfer(res.companyTrade)+"</div></td>";
			html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(res.defined1)+"'>"+emptyTransfer(res.defined1)+"</div></td>";
			html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(res.defined2)+"'>"+emptyTransfer(res.defined2)+"</div></td>";
			html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(res.defined3)+"'>"+emptyTransfer(res.defined3)+"</div></td>";
			html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(res.defined4)+"'>"+emptyTransfer(res.defined4)+"</div></td>";
			html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(res.defined5)+"'>"+emptyTransfer(res.defined5)+"</div></td>";
			html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(res.defined6)+"'>"+emptyTransfer(res.defined6)+"</div></td>";
			html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(res.defined7)+"'>"+emptyTransfer(res.defined7)+"</div></td>";
			html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(res.defined8)+"'>"+emptyTransfer(res.defined8)+"</div></td>";
			html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(res.defined9)+"'>"+emptyTransfer(res.defined9)+"</div></td>";
			html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(res.defined10)+"'>"+emptyTransfer(res.defined10)+"</div></td>";
			html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(res.defined11)+"'>"+emptyTransfer(res.defined11)+"</div></td>";
			html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(res.defined12)+"'>"+emptyTransfer(res.defined12)+"</div></td>";
			html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(res.defined13)+"'>"+emptyTransfer(res.defined13)+"</div></td>";
			html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(res.defined14)+"'>"+emptyTransfer(res.defined14)+"</div></td>";
			html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(res.defined15)+"'>"+emptyTransfer(res.defined15)+"</div></td>";
			html += "<td><div class='overflow_hidden w100' title='"+emptyTransfer(res.showdefined16)+"'>"+emptyTransfer(res.showdefined16)+"</div></td>";
			html += "<td><div class='overflow_hidden w100' title='"+emptyTransfer(res.showdefined17)+"'>"+emptyTransfer(res.showdefined17)+"</div></td>";
			html += "<td><div class='overflow_hidden w100' title='"+emptyTransfer(res.showdefined18)+"'>"+emptyTransfer(res.showdefined18)+"</div></td>";
			html +="</tr>";
		if(defList!=null && defList.length>0){
			for(var i in defList){
				var field = defList[i];
				var defineValue = res[field.fieldCode] == null ? "":res[field.fieldCode];
				html+=
					"  <td><div class='overflow_hidden w100' title='"+defineValue+"'>"+defineValue+"</div></td>";
			}
		}
		"</tr>";
	}
	$(".ajax-table>tbody").html(html);
	//模糊处理手机、电话号码
	var idReplaceWord = $("#idReplaceWord").val();
	if(idReplaceWord==1){
		$("[phone=tel]").each(function(idx,obj){
			var phone = $(obj).text();
			if(phone != null && phone != ''){
				replaceWord(phone,$(obj));
				replaceTitleWord(phone,$(obj));
			}
		});
	 }
	/*表单优化*/
 	$('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
}

function getInputStatus(inputStatus){
	if(inputStatus == 1){
		return '空号';
	}else if(inputStatus == 2){
		return '停机';
	}else if(inputStatus == 3){
		return '沉默';
	}else if(inputStatus == 4){
		return '正常';
	}else{
		return '';
	}
}

function searchingDataTip(){
	var table = $(".ajax-table");
	var tip_search_data = $(".tip_search_data");
	var tip_search_text = tip_search_data.find(".tip_search_text");
	var tip_search_error = tip_search_data.find(".tip_search_error");
	var table_height = table.height();
	var margin_top = (table_height-tip_search_text.height())/2;
	tip_search_data.height(table_height);
	tip_search_text.css("margin-top",margin_top);
	tip_search_error.css("margin-top",margin_top);
	tip_search_data.show();
	tip_search_error.hide();
	tip_search_text.show();
}
var emptyTransfer = function(data){
	if(data == null)
		return '';
	else 
		return data;
};