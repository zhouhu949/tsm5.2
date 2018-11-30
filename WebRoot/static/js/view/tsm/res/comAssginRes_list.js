/**
 * 
 */
$(function() {
	$.ajax({
		url: $(".resource-group-tree").attr("data-url"),
		type: "get",
		success: function(data){
//			console.log(data);
			
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
			
			$(".resource-group-tree").tree({
				data: render_data,
				formatter: function(node){
					var nodeText = "<label class='tree-node-label' data-id='"+node.id+"' data-share-pool='"+node.isSharePool+"' title='"+node.text+"'>" + node.text + "</label>";
					return nodeText;
				},
				onSelect:function(node){
					var groupId = node.id;
					$("#groupId").val(groupId);
					$("#level").val(node.level);
					findMembers(groupId);
				},
				onLoadSuccess: function(node,data){
					var selectedNode = $(".resource-group-tree").tree("find",$("#groupId").val());
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
	}
	
	loadData();
/*	$("#query").click(function(){
		loadData();
	});*/
	
	if ($('#qKeyWordId').val() == "" || $('#qKeyWordId').val() == null) {
	} else {
		$('#qKeyWordId').next().text('');
	}
	var url = $('#base').val() + "/orgGroup/get_group_json"
	$("#deptTree").tree({
		formatter : function(node) {
			var nodeText = node.text;
			return nodeText;
		},
		url : url,
		checkbox : true,
		onBeforeLoad : function(node, param) {
			loading = true;
		},
		onLoadSuccess : function(node, data) {
			var pid;
			var accs = $('#deptId').val();
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
		onExpand : function(node) {
			$(".tree-title").removeClass("tree-folder-open");
		},
		onCheck : function(node, checked) {
		},
		onSelect : function(node) {
		}
	});
	if($("#onwerTree").length > 0){
		$("#onwerTree").tree({
			url:ctx+"/orgGroup/get_group_user_json",
			checkbox:true,
			onLoadSuccess:function(node,data){
				$("#onwerTree").tree("collapseAll");
				for(var i=0;i<data.length;i++){
					$("#onwerTree").tree("expand",data[i].target);
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
						var n = $("#onwerTree").tree("find",obj);
						if(n != null){
							text+=n.text+',';
							$("#onwerTree").tree("check",n.target).tree("expandTo",n.target);
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
				var nodes = $('#onwerTree').tree('getChecked');
				if(nodes.length > 0){
					$("input[name=searchOwnerType]").iCheck('uncheck');
				}else if($("input[name=searchOwnerType]:checked").length == 0){
					$("input[name=searchOwnerType]:eq(0)").iCheck('check');
				}
			}
		});
	}

	//选择部门
	$("#checkedId").click(function() {
		var nodes = $('#deptTree').tree('getChecked');
		if (nodes == null) {
			window.top.iDialogMsg("提示", '请选择部门！');
			return;
		}
		var mark = 0;
		var deptIds = "";
		//M-成员；G-分组
		$(nodes).each(function(index, obj) {
			deptIds += obj.id.substring(0, obj.id.length) + ',';
			mark = mark + 1;
		});
		if (mark == 0) {
			window.top.iDialogMsg("提示", '请选择部门！');
			return;
		} else {
			$('#deptId').val(deptIds);
		}
	});

	//清空
	$('#clearId').click(function() {
		var nodes = $('#deptTree').tree('getChecked');
		$(nodes).each(function(index, obj) {
			$('#deptTree').tree('uncheck', obj.target);
		})
		$('#deptId').val('');
	})

	//选择账号
	$("#checkOnwerId").click(function() {
		var nodes = $('#onwerTree').tree('getChecked', 'checked');
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
			names="所有者";
		}
		$("#ownerAccsStr").val(accs);
		$("#ownerNameStr").val(names);
	});

	//清空
	$('#clearOnwerId').click(function() {
		var nodes = $('#onwerTree').tree('getChecked');
		$(nodes).each(function(index, obj) {
			$('#onwerTree').tree('uncheck', obj.target);
		})
		$("#ownerAccsStr").val("");
		$("#ownerNameStr").val("");
	})

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
//	        s.css("z-index",z);
	 });
	// 提交
	$('#queryBtn').click(function() {
		loadPage(2);
	});


	// 左侧
	$('.hyx-hsm-left').find('dd').each(function() {
		var groupId = $(this).attr("id").substring(7);
		var findTag = $("#find_" + groupId);
		$(this).siblings().hover(function() {
			$(this).addClass("dd-hovers");
		}, function() {
			$(this).removeClass("dd-hovers");
		});
		$(this).click(function() {
			$(this).addClass("dd-hover");
			$(this).siblings().removeClass("dd-hover");
		});
		// // 查找点击
		findTag.click(function() {
			$("#groupId").val(groupId);
			findMembers(groupId)
		});
	});

	
	$('#resRecyleId').click(function(){
		resRecyle()
	});
	$('#changeResId').click(function(){
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
	$('#mergeResId').click(function(){
		mergeRes();
	});
	
})
//合并分组
function mergeRes(){
	pubDivShowIframe("mergeRes", $('#base').val() + "/res/resmanage/toMergeGroup",'合并分组', 360, 400);
}
// 变更分组
function changeGroup(ids) {
	var tmpTitle = '变更分组';
	var module = 'assgin';
	pubDivShowIframe("changeGroup", $('#base').val() + '/res/resmanage/toChangeGroup?ids=' + ids + '&module=' + module, tmpTitle, 330, 350);

}

// 回收资源
function resRecyle() {
	var ids = '';
	var accs = '';
	$('input[id^=chk_]').each(function(index, obj) {
		if ($(obj).is(':checked')) {
			ids += $(obj).attr('id').split('_')[1] + ',';
			accs += $(obj).data('owneracc') + ',';
		}
	});
	if (ids.length == 0) {
		window.top.iDialogMsg("提示", '请先选择资源！');
		return;
	}
	ids = ids.substring(0, ids.length - 1);
	accs = accs.substring(0, ids.length - 1);
	pubDivDialog("resRecyle", "确认要回收到待分配资源吗？", function() {
		ajaxLoading("正在回收，请稍候") ;
		$.ajax({
			url : ctx + '/res/resmanage/recyleRes',
			type : 'post',
			data : {
				ids : ids,
				accs : accs,
			},
			dataType : 'html',
			error : function() {
				alert('网络异常，请稍后再试！')
			},
			success : function(data) {
				if (data == 0) {
					window.top.iDialogMsg("提示", '回收资源成功！');
					setTimeout('document.forms[0].submit()', 1000);
				} else {
					window.top.iDialogMsg("提示", '回收资源失败！');
				}
			}
		});
	})
}

//查看
	function findMembers(groupId){
		loadData();
	}

function setPdate(i) {
	$('#s_pstartDate').val('');
	$('#s_pendDate').val('');
	$("#oDateType").val(i);
}

function setStatus(i) {
	$("#qStatus").val(i);
}

function loadData(page){
	var _param = $("#assginForm").serialize();
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
	var _url=ctx+"/res/resmanage/getAssginResJson";
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
		var ownerStartDate = (res.ownerStartDate != null && res.ownerStartDate !=0)? new Date(res.ownerStartDate).Format("yyyy-MM-dd hh:mm:ss"):"";
		var mobilephone = res.mobilephone ==null ? "":res.mobilephone;
		var telphone = res.telphone ==null ? "":res.telphone;
		var groupName = res.groupName ==null ? "":res.groupName;
		var deptName =  res.deptName ==null ? "":res.deptName;
		var company = res.company ==null ? "":res.company;
		var name = res.name ==null ? "":res.name;
		var isConcat = res.isConcat;
		var ownerName = res.ownerName;
		var ownerAcc = res.ownerAcc==null?"":res.ownerAcc;
		var name = res.name ==null ? "":res.name;
		var mainLinkman = res.mainLinkman ==null ? "":res.mainLinkman;
		var custName = "";
		var cardName = company == ""?name:company;
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
		var resType = res.type;
		var status = res.status;
		var statusName = "";
		if(status == 1){
			statusName= "待分配资源";
		}
		else if(status == 2 && resType ==1){
			statusName= "已分配资源";
		}
		else if(status == 2 && isConcat==0 && resType ==1 ){
			statusName= "已分配未联系资源";
		}
		else if(status == 2 && isConcat==1 && resType ==1){
			statusName= "已联系资源";
		}
		else if((status == 2 || status ==3) && resType ==2){
			statusName= "意向客户";
		}
		else if(status == 6 && resType ==2){
			statusName= "签约客户";
		}
		else if(status == 4  || status == 5){
			statusName= "公海客户";
		}
		else if(status == 7 && resType ==2){
			statusName= "沉默客户";
		}
		else if(status == 8 && resType ==2){
			statusName= "流失客户";
		}else{
			statusName= "未知";
		}
		html +=
		"<tr class='"+(i%2==0?'':'sty-bgcolor-b')+"'>"+
		"   <td style='width: 30px;'><div class='overflow_hidden w30 skin-minimal'><input type='checkbox' data-owneracc='"+ownerAcc+"' id='chk_"+ resCustId +"' /></div></td>" +
		"   <td style='width: 45px;'><div class='overflow_hidden w40 link'> <a href='###'  onclick='toCard(\""+resCustId +"\",\""+cardName +"\")'  class='icon-cust-card' title='客户卡片' style='margin-left:8px;'></a></div></td>" +
		"   <td hidevalue='"+ ownerStartDate +"'><div class='overflow_hidden w120' title='"+ownerStartDate+"'>"+ownerStartDate+"</div></td>"+
		"   <td><div class='overflow_hidden w140' title='"+custName +"'>"+custName +"</div></td>";
		/*if(isState == 0){
			html += "<td><div class='overflow_hidden w140' title='"+company +"'>"+company +"</div></td>";
		}*/
		html +=
			"   <td><div class='overflow_hidden w90' title='"+man +"'>"+man +"</div></td>"+
			"   <td><div class='overflow_hidden w120'  phone='tel'  title='"+mobilephone +"' id='mobilehidden_"+mobilephone +"'>"+mobilephone +"</div></td>"+
			"   <td><div class='overflow_hidden w120'  phone='tel'  title='"+telphone +"' id='telhidden_"+telphone+"'>"+telphone +"</div></td>" +
			"   <td><div class='overflow_hidden w100'>"+ statusName +"</div></td>"+
			"   <td><div class='overflow_hidden w90' title='"+groupName +"'>"+groupName+"</div></td>"+
			"   <td><div class='overflow_hidden w90' title='"+deptName +"'>"+deptName +"</div></td>"+
			"   <td><div class='overflow_hidden w90' title='"+ownerName +"'>"+ownerName+"</div></td>"+
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