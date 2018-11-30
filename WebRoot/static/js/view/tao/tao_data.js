var array = new Array();
var mark = 0;
var timeline_html = $(".taoCust_right_bottom>.timeline").html(); // 获取右下角“时间轴”没有数据时候的html
var p_b_div_html = $(".card .p_b_div").html(); // 获取左上角“资源信息”没有数据时候的html
var tip_box_html = $(".actionTag .tip-box").html(); // 获取左下角“行动标签”没有数据时候的html
$(document).ready(function() {
	// 页面加载中，数据未展现时候，页面顶层出现遮盖层
	ajaxLoading("正在加载页面，请稍候");
	$(".datagrid-mask,.datagrid-mask-msg").ajaxStop(function() {
		$(this).remove();
	});
	taoRightTopData(); // 初始化右上角列表数据
});

// 待呼数据异步填入；淘客户右上角数据初始化
function taoRightTopData() {
	$.ajax({
		url : ctx + '/res/tao/getTaoCustList',
		type : 'post',
		data : {
			resId : $('#custId').val()
		},
		dataType : 'json',
		error : function(error) {
		},
		success : function(data) {
			resourcesTableInit(data);
			resourceGroupingInit(data.groupList); // 资源分组填充

			var custId = $("#custId").val();
			var pool = $('#pool').val();
			if (custId != '') {
				initTenResources(); // 初始化右上角列表十条数据
			}
			resourceListClickActionInit(); // 初始化右上角列表点击事件
			taoMainPlatform(custId); // 初始化左上角信息“资源信息”
			taoCustGuide(custId); // 初始化左下角信息“销售导航信息”
			getRemarkList(custId); // 初始化右下角信息“资源备注信息”
			resourceListDefaultChosenSelect(); // 右上角默认筛选条件选中情况
			chooseResourceListGroup(); // 右上角“分组”改变事件
			chooseResourceListOrder(); // 右上角“排序”改变事件
			chooseResourceListContact(); // 右上角“资源是否联系”改变事件
		}
	});
}

function resourcesTableInit(data) {
	$("#isAutoCall").val(data.autoCall);
	$("#custId").val(data.custId);
	$("#currResId").val(data.currResId);
	$("#followId").val(data.followId);
	$("#isConcat").val(data.isConcat);
	$("#isFromOtherPage").val(data.isFromOtherPage);
	$("#isReplaceWord").val(data.isReplaceWord);
	$("#orderType").val(data.orderType);
	$("#pool").val(data.pool);
	$("#project_path").val(data.projectPath);
	$("#resourceGroupId").val(data.resourceGroupId);
	$("#signSetting").val(data.signSetting);

	if (data.dyName != null && data.dyName != "") {
		$(".th_dyName").text(data.dyName);
	}
	if(data.orderType == null || data.orderType == ""){
		$("#orderType").val("owner_start_date desc");//orderType 给默认值
	}

	var _TOBECALLED = 1;
	var _DELAYEDCALLED = 2;

	// 待呼列表填充
	if (data.pool == _TOBECALLED) {
		$(".resource_table").show();
		$(".delay_table").hide();
		$("#willPoolId").hide();
		$("#resPoolId").show();
		if (data.otherList.length != 0) {
			var tables = $(".resource_table").find("tbody");
			tables.html("");
			var str = "";
			var length = data.otherList.length;
			for (var i = 0; i < length; i++) {
				if (data.otherList[i].resId == data.custId) {
					str += "<tr id=otherRes_Tr_" + data.otherList[i].resId + " class='icon_blue_full active'>";
				} else {
					str += "<tr id=otherRes_Tr_" + data.otherList[i].resId + " class='icon_blue_full'>";
				}
				str += "<td title='" + data.otherList[i].name + "' id='" + data.otherList[i].resId + "'>";
				str += "<span class='icon_grey_empty icon_blue_full'></span><div class='otherRes_td_div_custName table_tr_td_div text_align_left'><span>" + data.otherList[i].name + "</span></div>";
				str += "<td id=otherRes_Td_" + data.otherList[i].resId + " phone='tel'><span class='td_tel_span'>" + data.otherList[i].phone + "</span></td>";
				str += "</td></tr>";
			}
			if (length < 10) {
				for (var i = length; i < 10; i++) {
					str += "<tr><td colspan='5'></td></tr>";
				}
			}
			tables.append(str);

			setTimeout(function() {
				init_rightTop();
			}, 0);
		} else {
			var tables = $(".resource_table").find("tbody");
			tables.html("");
			var str = "<tr style='height:310px;'><td colspan='5'>当前列表无数据</td></tr>";
			tables.append(str);
		}
	} else if (data.pool == _DELAYEDCALLED) {
		$(".resource_table").hide();
		$(".delay_table").show();
		$("#willPoolId").show();
		$("#resPoolId").hide();
		if (data.otherList.length != 0) {
			var tables = $(".delay_table").find("tbody");
			tables.html("");
			var str = "";
			var length = data.otherList.length;
			for (var i = 0; i < length; i++) {
				if (data.otherList[i].resId == data.custId) {
					str += "<tr id=otherRes_Tr_" + data.otherList[i].resId + " class='icon_blue_full active'>";
				} else {
					str += "<tr id=otherRes_Tr_" + data.otherList[i].resId + " class='icon_blue_full'>";
				}
				str += "<td id=" + data.otherList[i].resId + ">";
				str += "<span class='icon_grey_empty icon_blue_full'></span><div class='otherRes_td_div_time'>" + data.otherList[i].callDatePostponed + "</div></td>";
				str += "<td id=otherRes_Td_" + data.otherList[i].resId + " title='" + data.otherList[i].name + "'><div class='otherRes_td_div_auto table_tr_td_div'><span>" + data.otherList[i].name
						+ "</span></div></td>";
				str += "<td id='" + data.otherList[i].resId + "' title='" + data.otherList[i].reasonForCalldelay + "'><div class='otherRes_td_div_auto table_tr_td_div'><span>"
						+ data.otherList[i].reasonForCalldelay + "</span></div>";
				str += "</td></tr>";
			}
			if (length < 10) {
				for (var i = length; i < 10; i++) {
					str += "<tr><td colspan='5'></td></tr>";
				}
			}
			tables.append(str);

			setTimeout(function() {
				init_rightTop();
			}, 0);
		} else {
			var tables = $(".delay_table").find("tbody");
			tables.html("");
			var str = "<tr style='height:310px;'><td colspan='5'>当前列表无数据</td></tr>";
			tables.append(str);
		}
	}

	// 电话号码安全设置
	var isReplaceWord = $("#isReplaceWord").val();
	if (isReplaceWord == 1) {
		$("[phone=tel]").each(function(idx, obj) {
			var phone = $(obj).text();
			if (phone != null && phone != '') {
				replaceWord(phone, $(obj).find(".td_tel_span"));
				replaceTitleWord(phone, $(obj).find(".td_tel_span"));
			}
		});
	}
}

function resourceGroupingInit(groupList) {
	if (groupList) {
		var groupList_all_resource = $(".groupList_all_resource");
		var groupListItem = "";
		for (var i = 0; i < groupList.length; i++) {
			groupListItem += "<li><a href='###' title='" + groupList[i].groupName + "' select_res_id='" + groupList[i].groupId + "'>" + groupList[i].groupName + "</a></li>";
		}
		groupList_all_resource.after(groupListItem);
	}
}

function resourceListClickActionInit() {
	// 刷新当前资源列表数据
	$('#freshCache').on('click', function() {
		freshCache($("#resourceGroupId").val(), $("#orderType").val(), $("#isConcat").val());
	});
	// 链接到个人今日数据
	$('#todayDateId').on('click', function() {
		window.top.addTab(ctx + "/report/userDayData", "个人今日数据");
	});

	// 话术模板展示
	$('.demoBtn_d').click(function() {
		// pubDivShowIframe('add_cust',ctx+'/sys/knowlege/list','添加资源',1000,570);
		var timestmp = (new Date()).valueOf();
		var account = $("#account").val();
		var orgId = $("#orgId").val();
		var hsUrl = $('#project_path').val() + '/sys/knowlege/list?' + "account=" + account + "&orgId=" + orgId + "&t=" + timestmp;
		var reqXml = '<xml><Oparation Name=\"ExpandCallDlg\" Title=\"话术模板\"  Http=\"' + hsUrl + '\" StartPos=\"-1,-1\" EndPos=\"1000,550\"/></xml>';
		external.OnBtnClickShowDetail(reqXml);
	});

	$('#moreFollowId').on('click', function() {
		var custId = $('#custId').val();
		var name = $('#custName').val();
		var company = $('#company').val();
		var isState = $('#isState').val();
		if (custId == '' || custId == null) {
			return false;
		} else {
			toCard(custId, name, company, isState);
		}
	});

	// 添加资源
	$('.demoBtn_a').click(function() {
		// queryCallNum($('#custId').val(), $('#concatId').val());
		// window.top.syncMarkToPc('959c462d9b1a408296676eb10e275a1d','13018953319','6','1')
		// window.top.syncScreen(1)
//		 pubDivShowIframe('add_cust',ctx+'/res/cust/toAddResByScreen?phone=12345678','添加资源',800,570);//
//		http://localhost:8080/popup/list?j_path=telPhoneCust&j_parameter=13312214455&j_username=fhtx001&orgId=fhtx  //添加意向
		// 新增资源
//		 pubDivShowIframe('add_cust',ctx+'/res/incall/toEditResIframe?custId=e5182ee923a541428b178406a323dc64','添加资源',800,570);//qqzj001
//		 pubDivShowIframe('add_cust',ctx+'/res/incall/toEditResIframe?custId=a006cb7954a84730a90a0ae9c14270d1','添加资源',800,570);//hyxzyb1001
		// 来电弹屏
//		www.test.com/popup/list?j_path=telPhoneCall&j_parameter=18766232682&j_username=fhtx001
//		 pubDivShowIframe('add_cust',ctx+'/popup/list/toEditResIframe','添加资源',800,570);
//		 pubDivShowIframe('add_cust',ctx+'/res/cust/toAddRes?phone=123456789','添加资源',800,570);//添加到意向
		pubDivShowIframe('add_cust', ctx + '/res/cust/toAddRes?comFrom=1', '添加资源', 500, 570);
	});

	// 切换待呼列表下列表显示
	$('.taoCust_pools li').click(function() {
		var pool = $(this).attr("name");
		$('.taoCust_pools li').removeClass("active");
		$(this).addClass("active");
		var resourceGroupId = $('#resourceGroupId').val();
		var orderType = $('#orderType').val();
		var isConcat = $('#isConcat').val();
		var timestamp = new Date().getTime();
		if (pool == '2') {
			$('#pool').val(pool);
			freshCache(resourceGroupId, orderType, isConcat);
		}
		$.ajax({
			url : ctx + '/res/tao/getTaoCustList',
			type : 'post',
			data : {
				pool : pool,
				resourceGroupId : resourceGroupId,
				orderType : orderType,
				isConcat : isConcat,
				v : timestamp
			},
			dataType : 'json',
			error : function(error) {
			},
			success : function(data) {
				resourcesTableInit(data);

				taoMainPlatform(data.custId);
				taoCustGuide(data.custId);
				getRemarkList(data.custId);
			}
		});
	});

	$("tbody tr").on("mouseover", function() {
		var $this = $(this);
		if ($this.attr("disabled") == "disabled") {
			$this.find(".tr_note").siblings().hide();
			$this.find(".tr_note").show();
		}
	});

	$("tbody tr").on("mouseout", function() {
		var $this = $(this);
		if ($this.attr("disabled") == "disabled") {
			$this.find(".tr_note").siblings().show();
			$this.find(".tr_note").hide();
		}
	});

	$(".taoCust_pools li").on('click', function() {
		$(".taoCust_pools li.active").removeClass("active");
		$(this).addClass("active");
	});
	var otherRes = function(resId, upOrDown, isFromOtherPage) {
		setChosenResourceId(resId);
		getResource(resId, upOrDown, isFromOtherPage);
	};

	// 点击其他资源
	var isSubmited = false;
	$('.resource_table,.delay_table').on('click', 'tr[id^="otherRes_Tr_"]', function() {
		if (isSubmited) {
			return false;
		}
		var click_tr = $(this);
		if (click_tr.attr("disabled") == "disabled") {
			return false;
		}
		click_tr.siblings('tr').removeClass("active");
		click_tr.addClass("active");
		var resId = $(this).attr('id').split('_')[2];
		otherRes(resId, 2, 1);
	});

	/* 自动连拨 */
	$('.hyx-sce-right-btn').find('.switch').click(function() {
		var isAutoCall = '0';
		if ($(this).attr('name') == 'on') {
			$(this).addClass('click-hover');
			$(this).attr('name', 'off');
			isAutoCall = '0';
		} else {
			$(this).removeClass('click-hover');
			$(this).attr('name', 'on');
			isAutoCall = '1';
		}

		// 设置到sesion中
		$.ajax({
			url : ctx + '/res/tao/changeAutoCall',
			data : {
				'isAutoCall' : isAutoCall
			},
			type : 'post',
			success : function(data) {
				$('#isAutoCall').val(isAutoCall);
				if (data != 0) {
					dialogMsg(-1, '操作失败,请重试');
				} else {

				}
			}
		});
	});
}

function taoMainPlatform(custId) {
	$.ajax({
		url : ctx + '/res/tao/taoMainPlatform',
		type : 'post',
		data : {
			custId : custId
		},
		dataType : 'json',
		error : function(data) {
		},
		success : function(data) {
//			console.log(data);

			custDataInit(data.custData, data.id); // 资源信息初始化
			var isState = $('#isState').val();
			if (isState == 1) {
				queryTaoByCustId(data.concatId);
			} else {
				queryTaoByCustId(data.id);
			}
			// queryTaoByCustId(data.id); //通话记录初始化
			PlatformInfoInit(data); // 平台信息初始化

			autoCall(); // 自动连拨功能

			var serverDay = data.serverDay;
			// 服务天数小于0时，所有功能按钮图标不允许点击
			if (serverDay <= 0) {
				$(".content_icon_size").unbind("click");
			}

			// 强行修改电话号码超出长度后的字体大小
			var operate_content_area = $(".operate_content_area");
			operate_content_area.css("font-size", "14px");
			if ($("#custInfo_mobilephone").height() > operate_content_area.height() || $("#custInfo_telphone").height() > operate_content_area.height()) {
				operate_content_area.css("font-size", "12px");
			}
		}
	});
}

// 销售导航
function taoCustGuide(custId) {
	$.ajax({
		url : ctx + '/res/tao/taoCustGuide',
		type : 'post',
		data : {
			custId : custId
		},
		dataType : 'json',
		error : function(data) {
		},
		success : function(data) {
			// 初始化销售信息内容的值
			if (data.isopen == "1") {
				buttonInit(data.messageFailureList, data.commFailureList);// 按钮初始化
			} else {
				$("#dealType_5").parent().find(".drop").remove();
				$("#dealType_4").parent().find(".drop").remove();
			}

			// 销售信息区域值初始化
			$("#taoForm input[type='hidden']").val(""); // 隐藏域的值清空
			$(".sales_info input[type='text']").val(""); // 销售信息文本框的值清空
			$(".sales_info textarea").val(""); // 销售信息多行文本的值清空
			$("#taoCustId").text("淘到客户"); // 按钮初始化
			$("#taoCustId").css("cursor", "pointer");
			$("#signCustId").text("签约客户"); // 按钮初始化
			$("#signCustId").css("cursor", "pointer");

			custTypeInit(data.custTypeList); // 客户类型
			productTreeInit(data.suitProcList); // 产品列表
			saleProcessInit(data.saleProcessList); // 销售进程
//			console.log(data.labelList);
			if(data.isSelect == 0){
				$(".hyx-sce-area .tip-box .choose-tag").remove();
				actionTagInit(data.labelList); // 行动标签
			}else{
				groupActionTagInit();
				$(".hyx-sce-area .tip-box .choose-tag").show();
				$(".hyx-sce-area .tip-box .sty-hid").remove();
			}
			
			//todo 添加判断条件，判断followDate是否需要塞值
			$("#followDate").attr("data-next")&&$("#followDate").attr("data-next") == 0 ? $("#followDate").val(""):$("#followDate").val(data.defDate);
//			$("#followDate").val(data.defDate); // 下次联系时间
//			alert(data.defDate);
			$("#resCustId").val(custId); // 隐藏域resCustId值赋值
			var isSubmited = true;
			$('#taoCustId,#signCustId').unbind('click').click(function() {
				var id = $(this).attr('id');
				var signSetting = $('#signSetting').val();
				var resId = $('#resCustId').val();
				$('#concatName').val($("#concat_name").val());
				$('#concatPhone').val($("#concat_phone").val());
				$("#resourceGroupId_tao").val($("#resourceGroupId").val());
				$('#orderType_tao').val($("#orderType").val());
				$('#isConcat_tao').val($("#isConcat").val());
				$('#pool_tao').val($("#pool").val());
				$('#custFollowId').val($('#followId').val());
				$('#saleProcessName').val($('#saleProcessId').find('option:selected').text());
				$('#custInfoName_tao').val($("#custInfoName").val());
				var clickBtn = $(this);
				if (custId == '' || !checkForm() || !setShowMsg('suitProcId')) {
					isSubmited = true;
					return false;
				}
				if (clickBtn.attr("id") == "taoCustId") {
					disabledButton(clickBtn);
				}
				if (isSubmited) {
					isSubmited = false;
					$.ajax({
						url : ctx + '/res/resmanage/getCustLimitNum',
						type : 'post',
						data : {
							code : 'DATA_10003'
						},
						success : function(data) {
							if (data == '1' && id=='taoCustId') {
								window.top.iDialogMsg("提示", "超出个人拥有意向客户上限！");
								isSubmited = true;
								return false;
							}
							if (id == 'signCustId') {
								var nextfallow=$('#nextFollowValidation').val();
					    		var html = nextfallow == '1' ? "是否确认签约？<br /><input type='checkbox' id='saveNextFollow' />勾选保存下次联系时间" : "是否确认签约？";
//					    		pubDivDialog("res_remove_cust", "是否确认签约？", function() {
					    		window.top.pubDivDialog("confirm_sign",html,
						    		function(){
								    	if(window.top.$("#saveNextFollow").length > 0){
											var saveNextFollow = window.top.$("#saveNextFollow").is(":checked") ? 1 : 2;
											$("#isAddDate").val(saveNextFollow);
										}
									    var is_add_contract = window.top.shrioAuthObj("mySignCust_addContract");							    
										if (signSetting == '0'&& is_add_contract==true) {
											toSignCustomerPage(custId);
											return;
										} else {
											$('#opterType').val("2");
											submitAjax();
										}
									},
									function(){
										isSubmited = true;
									}
					    		);
							} else {
								submitAjax();
							}
						}
					});
				}
			});
			// 如果服务天数<=0，将页面所有按钮调至不可用状态
			if (data.serverDay <= 0) {
				$(".com-btna").attr("disabled", "true");
				$(".com-btnb").addClass("com-btnb-no");
				$(".com-btna-no,.com-btnb-no").unbind("click");
			}
		},
		error : function() {
			// alert("error");
		}
	});
}

// 提交销售信息这个form里面的信息
function submitAjax() {
	$('#taoForm').ajaxSubmit({
		async : false,
		url : ctx + '/res/tao/addMyCust',
		dataType : 'json',
		type : 'post',
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				// alert("加载超时，请重试");
			} else {
				// alert(textStatus);
			}
		},
		success : function(data) {
			var status = data.status;
			var resId = data.resId;
			var isCall = data.isCall;
			var name = data.name;
			var phone = data.phone;
			var filterType = data.filterType;
			var pool = data.pool;
			var type = data.type;
			if (isDisabled(status, type, filterType, pool)) {
				updateStatus(pool, status, resId, isCall, name, phone, filterType, type);
			}
			//next();
			taonext();
			var nextfallow=$('#nextFollowValidation').val();
			if(nextfallow =="0"){
				$('#followType').val('0');
			}else if(nextfallow =="1"){
				$('#followType').val('1');
			}

		}
	});
}

// 点击签约客户，并且确定签约
function toSignCustomerPage(custId) {
	var obj = new Object();
	$('#custFollowId').val($('#followId').val());
	obj.resCustId = custId;
	obj.remark = encodeURIComponent($('#remark').val());
	obj.suitProcId = $('#suitProcId').val();
	obj.groupId = $('#resourceGroupId').val();
	obj.isConcat = $('#isConcat').val();
	obj.orderType = $('#orderType').val();
	obj.custFollowId = $('#custFollowId').val();
	obj.labelCode = $('#labelCodeId').val();
	obj.labelName = $('#labelNameId').val();
	obj.custDetailId = $('#concatId').val();
	obj.concatName = $('#concat_name').val();
	obj.concatPhone = $('#concat_phone').val();
	obj.feedbackComment = $('#feedbackComment').val();
	if($("#isAddDate").val()=="2"){
		obj.followDate = null;
	}else{
		obj.followDate = $('#followDate').val();	
	}
	obj.effectiveness = $('#effectiveness').val();
	obj.custTypeId = $('#custTypeId').val();
	obj.saleProcessId = $('#saleProcessId').val();
	obj.followType = $('#followType').val();
	obj.expectDate = $('#expectDate').val();
	obj.expectSale = $('#expectSale').val();
	obj.saleWay = $('#saleWay').val();
	obj.custArgue = $('#custArgue').val();
	obj.custInterest = $('#custInterest').val();
	obj.competitor = $('#competitor').val();
	var param = encodeURIComponent(JSON.stringify(obj));
	
//			// 弹出新增合同tab
		window.top.addTab(ctx + "/contract/toAdd?custId=" + custId + "&jsonStr=" + param+"&module=0", "新增合同");

}


//签约，不通过合同
function toSign(param) {
	$.ajax({
		url : ctx + '/contract/toSign',
		type : 'post',
		data : {
			jsonStr : param
		},
		dataType : 'json',
		error : function(data) {
			// alert("getRemarkListError");
		},
		success : function(data) {
			if(data==1){
				alert("签约成功");
				next();
				
			}
			
		}
	});
}


// 获取资源备注信息
function getRemarkList(custId) {
	$.ajax({
		url : ctx + '/res/tao/getRemarkList',
		type : 'post',
		data : {
			custId : custId
		},
		dataType : 'json',
		error : function(data) {
			// alert("getRemarkListError");
		},
		success : function(data) {
			remarkListInit(data);
		}
	});
}

function remarkListInit(data) {
	var length = data.bzDtos.length;
	var taoCust_right_bottom = $(".taoCust_right_bottom");
	var timeline = $(".taoCust_right_bottom .timeline");
	var appendCode = "";
	if (length == 0) {
		appendCode = "<div class='no_data_tip'>暂无备注记录</div>";
		timeline.html(appendCode);

		var no_data_tip = $(".no_data_tip");
		if (no_data_tip.length != 0) {
			timeline.height(taoCust_right_bottom.height() - 30);
			timeline.css("margin-top", "0");
			no_data_tip.css("padding-top", (timeline.height() - no_data_tip.height()) / 2);
		}
	} else {
		timeline.attr("style", "");
		timeline.html(timeline_html);
		var timeline_ul = $("#timeline_ul");
		for (var i = 0; i < length; i++) {
			appendCode = "<li class='li-load cfu-mt'>" + "<div class='cfu-cirb'></div>" + "<div class='right'>" + "<div class='arrow'><em>◆</em><span>◆</span></div>" + "<div class='cfu-box'>"
					+ "<div class='cfu-time'>" + data.bzDtos[i].inputTime + "</div>";
			if (data.bzDtos[i].nextConcatTime != "" && data.bzDtos[i].nextConcatTime != null) {
				appendCode += "<p class='cfu-list com-none'>下次联系时间：" + data.bzDtos[i].nextConcatTime + "</span></p>";
			}
			appendCode = appendCode + "<p class='cfu-list-note com-none fl_l' title='" + data.bzDtos[i].context + "'><label class='fl_l'>资源备注：</label><span class='fl_l'>" + data.bzDtos[i].context
					+ "</span></p>" + "</div>" + "</div>" + "</li>";
			timeline_ul.append(appendCode);
		}
		timeline_ul.find("li").eq(length - 1).addClass("end");
		var cfu_list_note = $("p.cfu-list-note");
		var cfu_list_note_label = cfu_list_note.find("label");
		var cfu_list_note_span = cfu_list_note.find("span");
		cfu_list_note_span.width(cfu_list_note.width() - cfu_list_note_label.width() - 10);
	}
}

// 判断值是否为空
function isNull(value) {
	if (value == "" || value == null) {
		return false;
	} else {
		return true;
	}
}

// 让有信息的图标变蓝
function changeImgBlue(obj) {
	for (var i = 0; i < obj.length; i++) {
		var blue = obj.eq(i).attr("src_blue");
		obj.eq(i).attr("src", blue);
	}
	obj.parent().css("cursor", "pointer");
}

// 让无信息的图标变灰
function changeImgGrey(obj) {
	for (var i = 0; i < obj.length; i++) {
		var grey = obj.eq(i).attr("src_grey");
		obj.eq(i).attr("src", grey);
	}
	obj.parent().css("cursor", "default");
}

// 左上角平台客户信息初始化
function custDataInit(custData, custId) {
	var length = custData.length;
	var p_b_div = $(".card .p_b_div");
	var p_a = $(".card .p_a");
	if (custId == "" || custId == null) {
		p_a.find("i").addClass("img-gray");
		if ($("#pool").val() == "1") {
			p_b_div
					.html("<p class='left_none fl_l'><b>目前无待呼资源，您可通过以下方式获得资源：</b></p><p class='left_none fl_l'>【淘客户】页面直接【添加资源】。</p><p class='left_none fl_l'>【我的客户-资源】进行【添加资源】或【导入资源】。</p><p class='left_none fl_l' >联系管理员进行资源分配。</p>");
		} else {
			p_b_div.html("<p class='left_none fl_l'><b>目前暂无延后呼叫资源</b></p>");
		}
	} else {
		p_b_div.html(p_b_div_html);
		p_a.find("i").removeClass("img-gray");
		var p_b_div_hidden = p_b_div.find(".sty-hid");

		// 下拉箭头是否显示
		var icon_custInfo_more = $(".p_b_div i");
		icon_custInfo_more.hide();

		var clientHeight = $(window).height();
		var card = $(".card");
		var limit_height = card.height() - 40;

		for (var i = 0; i < length; i++) {
			var appendCode = "";
			appendCode = "<p class='p_b'><label>" + custData[i].fieldName + "：</label><span title='" + custData[i].fieldValue + "'>" + custData[i].fieldValue + "</span></p>";
			if (custData[i].fieldCode == "sex") {
				if (custData[i].fieldValue == "1") {
					appendCode = "<p class='p_b'><label>" + custData[i].fieldName + "：</label><span title='男'>男</span></p>";
				} else {
					appendCode = "<p class='p_b'><label>" + custData[i].fieldName + "：</label><span title='女'>女</span></p>";
				}
			}
			if (custData[i].fieldCode == "unithome") {
				appendCode = "<p class='p_b'><label>" + custData[i].fieldName + "：</label><span class='span_unithome' title='" + custData[i].fieldValue + "'><a href='###' id='showPubURL'>"
						+ custData[i].fieldValue + "</a></span></p>";
			}
			p_b_div.append(appendCode);
			if ($("#showPubURL")) {
				$("#showPubURL").unbind("click").click(function() {
					showPublicUrl($("#showPubURL").text());
				});
			}
			var p_b_div_p = p_b_div.find("p:last");
			if (p_b_div.height() > limit_height) {
				icon_custInfo_more.show();
				p_b_div_p.appendTo(p_b_div_hidden);
			} else {
				p_b_div_p.insertBefore(p_b_div_hidden);
			}
		}
	}
}

// 左上角平台客户联系信息初始化
function PlatformInfoInit(data) {
	// var dialedNumber = data.custInfo.dialedNumber;
	var name = data.custName;
	if (data.custName == null) {
		name = "";
	}

	var mobilephone = data.custInfo.mobilephone;
	var telphone = data.custInfo.telphone;
	var qq = data.custInfo.qq;
	var weChat = "";
	var email = data.custInfo.email;
	var wangwang = data.custInfo.wangWang;
	$('#custName').val(data.custName);
	$('#concat_phone').val(data.mainPhone);
	$('#concat_name').val(data.concatName);
	$('#concatId').val(data.concatId);
	$("#company").val(data.company);
	$("#resIsConcat").val(data.isConcat); // resIsConcat资源是否联系过
	var list = data.custInfo.list;

	// $("#custInfo_dialedNumber").text(dialedNumber);
	$("#custInfo_name").attr("title", name);
	$("#custInfo_name").text(name);
	$('input[name="custInfoName"]').val(name);
	judgeIsNull(mobilephone, telphone, qq, weChat, email, wangwang);

	if (data.state == "1") {
		var card_operate_head_ul = $("#card_operate_head_ul");
		card_operate_head_ul.html("");
		if (list.length > 0) {
			var appendCode = "";
			for (var i = 0; i < list.length; i++) {
				if (list[i].isDefault == "1") {
					appendCode = "<div class='card_operate_apart import' title='" + list[i].name + "'><span class='import_one'></span>" + list[i].name + "</div>";
				} else {
					appendCode = "<div class='card_operate_apart' title='" + list[i].name + "'>" + list[i].name + "</div>";
				}
				card_operate_head_ul.append(appendCode);
			}
			var div = card_operate_head_ul.find("div");
			div.eq(0).addClass("choosed");
			if (list[0].sex == "1") {
				$(".card_person_infomation_sex>span").text("男");
			} else if (list[0].sex == "2") {
				$(".card_person_infomation_sex>span").text("女");
			} else {
				$(".card_person_infomation_sex>span").text("");
			}
			// $(".card_person_infomation_sex>span").text(list[0].sex);
			$(".card_person_infomation_zw>span").text(list[0].duty);
			/*
			 * if (list[0].callNum == "" || list[0].callNum == null) {
			 * $("#list_callnum").text("0"); } else {
			 * $("#list_callnum").text(list[0].callNum); }
			 */

//			console.log($("#concatId").val());
			//queryTaoByCustId连续调了2次，关掉这个调用
//			queryTaoByCustId($("#concatId").val());
			
			// $(".card_person_infomation_cs>span").text(list[0].sex);
			judgeIsNull(list[0].telphone, list[0].telphonebak, list[0].qq, list[0].weChat, list[0].email, list[0].wangwang);
			div.click(function() {
				var $this = $(this);
				var index = $this.index();
				div.removeClass("choosed");
				$this.addClass("choosed");
				$("#concat_phone").val(list[index].telphone);
				$("#concat_name").val(list[index].name);
				$('#concatId').val(list[index].detailId);
				if (list[index].sex == "1") {
					$(".card_person_infomation_sex>span").text("男");
				} else if (list[index].sex == "2") {
					$(".card_person_infomation_sex>span").text("女");
				} else {
					$(".card_person_infomation_sex>span").text("");
				}
				// $(".card_person_infomation_sex>span").text(list[index].sex);
				$(".card_person_infomation_zw>span").text(list[index].duty);
				// $(".card_person_infomation_cs>span").text(list[0].sex);
				judgeIsNull(list[index].telphone, list[index].telphonebak, list[index].qq, list[index].weChat, list[index].email, list[index].wangwang);
				/*
				 * if (list[index].callNum == "" || list[index].callNum == null) {
				 * $("#list_callnum").text("0"); } else {
				 * $("#list_callnum").text(list[index].callNum); }
				 */
//				console.log($("#concatId").val());
				queryTaoByCustId($("#concatId").val());
			});
		} else {
			card_operate_head_ul.html("");
			$(".card_person_infomation_sex>span").text("");
			$(".card_person_infomation_zw>span").text("");
			$("#list_callnum").text("--");
		}
	}
}

// qq联系
function qqSend(qq) {
	$('.qq_iframe').attr('src', "tencent://message/?uin=" + qq + "&Site=&menu=yes");
}

// 销售信息上方按钮初始化
function buttonInit(messageFailureList, commFailureList) {
	var message_length = messageFailureList.length;
	var messageFailure = $("#dealType_5").parent().find(".box");
	messageFailure.html("");
	for (var i = 0; i < message_length; i++) {
		var appendCode = "<a href='###' id='dealType_5_" + messageFailureList[i].optionId + "'><i></i><label title='"+ messageFailureList[i].optionName+"'>" + messageFailureList[i].optionName + "</label></a>";
		messageFailure.append(appendCode);
	}

	var comm_length = commFailureList.length;
	var commFailure = $("#dealType_4").parent().find(".box");
	commFailure.html("");
	for (var i = 0; i < comm_length; i++) {
		var appendCode = "<a href='###' id='dealType_4_" + commFailureList[i].optionId + "'><i></i><label title='"+ commFailureList[i].optionName+"'>" + commFailureList[i].optionName + "</label></a>";
		commFailure.append(appendCode);
	}
}

/** 操作按钮 * */
// 销售信息上方按钮，当鼠标移动至上方，是否显示菜单初始化
$('.hyx-sce-left-btn').find('.drop-box').each(function(i, item) {
	if ($(item).find('.com-btna').hasClass('delay_call') == false) {
		$(item).find('.com-btna').mouseover(function() {
			var btn = $(this);
			$(this).next().show();
			$(this).next().find('a').click(function() {
				btn.next().hide();
			});
		});
		$(item).mouseleave(function() {
			$(this).find('.drop').hide();
		});
	} else {
		$(item).find('.delay_call').click(function() {
			if ($('#custId').val() == null || $('#custId').val() == '') {
				return false;
			}
			$("#delayReason").val("");
			$("#delayReason").parent().find(".textarea_tip").show();
			$.ajax({
				async : false,
				url : ctx + '/res/tao/toUpdateDelayCallReason.do',
				type : 'post',
				success : function(data) {
					if (data == '-3') {
						dialogMsg(-1, '操作失败,请重试');
					} else {
						$('#delayId').next().show();
						$('#waitDate').val(data);
					}
				}
			});
		});
	}
});

// “延后呼叫”按钮
$("#submitDelayId").click(function() {
	var waitDate = $("#waitDate").val();
	var delayReason = $("#delayReason").val();
	var resourceGroupId = $("#resourceGroupId").val();
	var orderType = $('#orderType').val();
	var isConcat = $('#isConcat').val();
	var pool = $('#pool').val();
	var custInfo_name = $('#custInfo_name').text();
	if (waitDate == "" || waitDate == null) {
		window.top.iDialogMsg("提示", '待呼时间不能为空！');
		return false;
	}

	if (!checkLength(delayReason, 60)) {
		window.top.iDialogMsg("提示", '超出最大长度');
		return;
	}
	$.ajax({
		async : false,
		url : 'delayCall',
		type : 'post',
		dataType : 'json',
		data : {
			"resourceGroupId" : resourceGroupId,
			"orderType" : orderType,
			isConcat : isConcat,
			resId : $('#custId').val(),
			waitDate : waitDate,
			delayReason : delayReason,
			pool : pool,
			custInfo_name:custInfo_name
		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				// alert("加载超时，请重试");
			} else {
				// alert(textStatus);
			}
		},
		success : function(data) {
			if (data != '-3') {
				$("#refreshOneId").val("1");
				$('#delayId').next().hide();
				if (array[mark] == undefined) {
					next();
					taoCustDelay();
					return false;
				}
				var resId = array[mark].resId;
				if (resId == '' || resId == null) {
					next();
					// taoCustDelay();
					return false;
				}
				var status = data.status;
				var resId = data.resId;
				var isCall = data.isCall;
				var name = data.name;
				var phone = data.phone;
				var filterType = data.filterType;
				var type = data.type;
				var pool = $('#pool').val();
				if (isDisabled(status, type, filterType, pool)) {
					updateStatus(pool, status, resId, isCall, name, phone, filterType, type);
				}
				next();
			} else {
				window.top.iDialogMsg("提示", '修改异常！');
			}
		}
	});

});

// 提交延后呼叫原因按钮
$("#closeDelayId").click(function() {
	$('#delayId').next().hide();
});

// 沟通失败、信息错误
$(".drop-box").on("click", "[id^='dealType_']", function() {
	if ($('#custId').val() == null || $('#custId').val() == '') {
		return false;
	}
	var resourceGroupId = $("#resourceGroupId").val();
	var orderType = $('#orderType').val();
	var isConcat = $('#isConcat').val();
	var resId = $('#custId').val();
	var pool = $('#pool').val();
	var custInfo_name = $('#custInfo_name').text();
	if (resId == '' || resId == null) {
		return false;
	}
	var type = $(this).attr("id").substring(9); // 处理类型
	$.ajax({
		async : false,
		url : ctx + '/res/tao/giveUpRes',
		data : {
			resId : resId,
			dealType : type,
			resourceGroupId : resourceGroupId,
			orderType : orderType,
			isConcat : isConcat,
			pool : pool,
			custInfo_name:custInfo_name
		},
		dataType : 'json',
		type : 'post',
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				// alert("加载超时，请重试");
			} else {
			}
		},
		success : function(data) {
			var status = data.status;
			var resId = data.resId;
			var isCall = data.isCall;
			var name = data.name;
			var phone = data.phone;
			var filterType = data.filterType;
			var pool = $('#pool').val();
			updateStatus(pool, status, resId, isCall, name, phone, filterType, type);
			next();
		}
	});
});

// 上下条按钮
$('[id^=updownType_]').click(function() {
	if ($('#custId').val() == null || $('#custId').val() == '') {
		return false;
	}
	var $obj = $(this).attr('id');
	var arrs = $obj.split('_');
	var updownType = '';
	if (arrs != null && arrs.length > 0) {
		updownType = arrs[1];
	} else {
		return "";
	}
	if (updownType == "1") {
		up();
	} else {
		next();
	}
});

// 资源备注按钮
$('#zybz').on('click', function() {
	var concatName = $('#concat_name').val();
	pubDivShowIframe("addResLogId", ctx + "/res/cust/toResLog?concatName=" + concatName, '资源备注', 700, 270);
});

// 沟通失败、信息错误
function giveup(obj) {
	var custId = $('#custId').val();
	var custInfo_name = $('#custInfo_name').text();
	if (custId == null || custId == '') {
		return false;
	}
	if (custId == '' || custId == null) {
		return false;
	}
	var type = $(obj).attr("id").substring(9); // 处理类型
	$.ajax({
		async : false,
		url : ctx + '/res/tao/giveUpRes',
		data : {
			resId : custId,
			dealType : type,
			custInfo_name:custInfo_name
		},
		dataType : 'json',
		type : 'post',
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				// alert("加载超时，请重试");
			} else {
			}
		},
		success : function(data) {
			markCust(data.resId);
			var status = data.status;
			var resId = data.resId;
			var isCall = data.isCall;
			var name = data.name;
			var phone = data.phone;
			var filterType = data.filterType;
			var pool = data.pool;
			var type = data.type;
			updateStatus(pool, status, resId, isCall, name, phone, filterType, type);
			next();
		}
	});
}

// 上下条
function upOrNext(obj) {
	if ($('#custId').val() == null || $('#custId').val() == '') {
		return false;
	}
	var $obj = $(obj).attr('name');
	var arrs = $obj.split('_');
	var updownType = '';
	if (arrs != null && arrs.length > 0) {
		updownType = arrs[1];
	} else {
		return "";
	}
	if (updownType == "1") {
		up();
	} else {
		next();
	}
}
/** 销售信息区域 * */
// 客户类型
function custTypeInit(custTypeList) {
	var length = custTypeList.length;
	var custType_list = $(".custType_list");
	custType_list.html("<option value='' selected>--请选择--</option>");
	for (var i = 0; i < length; i++) {
		var appendCode = "";
		if (custTypeList[i].isDefault == "1") {
			appendCode = "<option value='" + custTypeList[i].optionId + "' selected>" + custTypeList[i].optionName + "</option>";
		} else {
			appendCode = "<option value='" + custTypeList[i].optionId + "'>" + custTypeList[i].optionName + "</option>";
		}
		custType_list.append(appendCode);
	}
}
// 适用产品
function productTreeInit(suitProcList) {
	var length = suitProcList.length;
	var productTree = $("#productTree");
	var suitProcId = "";
	var suitProcName = "";
	productTree.html("");
	for (var i = 0; i < length; i++) {
		 var appendCode = "";
		 if(suitProcList[i].isDefault == "1"){
			 appendCode = '<li class="selected"><a href="javascript:void(0);"  data-value="' + suitProcList[i].optionId + '" title="'+ suitProcList[i].optionName +'">' + suitProcList[i].optionName +"</a></li>";
			 suitProcId = suitProcId + "," + suitProcList[i].optionId;
			 suitProcName = suitProcName + "," + suitProcList[i].optionName;
		 }else{
			 appendCode = '<li><a href="javascript:void(0);"  data-value="' + suitProcList[i].optionId + '" title="'+ suitProcList[i].optionName +'">' + suitProcList[i].optionName +"</a></li>";
		 }
		 productTree.append(appendCode);
/*		var isChecked = false;
		if (suitProcList[i].isDefault == "1") {
			isChecked = true;
		}
		productTree.tree("append", {
			data : [ {
				id : suitProcList[i].optionId,
				text : suitProcList[i].optionName,
				checked : isChecked
			} ]
		});*/
	}
	
	$("#suitProcId").val(suitProcId.substring(1));
	if(suitProcName != null && suitProcName != ""){
		productTree.parents(".select").find("dt").text(suitProcName.substring(1)).attr("title",suitProcName.substring(1));
	}else{
		var dt = productTree.parents(".select").find("dt");
		dt.text(dt.data("placeholder")).attr("title",dt.data("placeholder"));
	}
	
/*	var nodes = productTree.tree('getChecked');
	var suitProNameId_text = "";
	var suitProNameId_id = "";
	if (nodes.length > 0) {
		for (var i = 0; i < nodes.length - 1; i++) {
			suitProNameId_id = nodes[i].id + ",";
			suitProNameId_text = nodes[i].text + ",";
		}
		suitProNameId_id += nodes[nodes.length - 1].id;
		suitProNameId_text += nodes[nodes.length - 1].text;
	}
	$("#suitProcId").val(suitProNameId_id);
	if (suitProNameId_text == "") {
		$("#suitProNameId").text("--请选择--");
	} else {
		$("#suitProNameId").text(suitProNameId_text);
	}

	var setSuitPro = function() {
		var nodes = $('#productTree').tree('getChecked', 'checked');
		var proId = "";
		var proName = '';
		$.each(nodes, function(index, obj) {
			proId += obj.id + ",";
			proName += obj.text + ",";
		});
		if (proId != "") {
			proId = proId.substring(0, proId.length - 1);
			proName = proName.substring(0, proName.length - 1);
		} else {
		}
		$('#suitProNameId').html(proName);
		$('#suitProcId').val(proId);
	};

	var closeSuitPro = function() {
		var nodes = $('#productTree').tree('getChecked', 'checked');
		$.each(nodes, function(index, obj) {
			$('#productTree').tree("uncheck", obj.target);
		});
		$('#suitProNameId').html("--请选择--");
		$('#suitProcId').val("");
		$("#manage_drop").hide();
		setShowMsg('suitProcId');
	};

	*//** 选择产品 *//*
	$('#setProId').click(function() {
		setSuitPro();
		setShowMsg('suitProcId');
	});

	$('#clearId').click(function() {
		closeSuitPro();
	});*/
	
	initSuitProductTree(productTree);
}

//适用产品树初始化
function initSuitProductTree(productTree){
	productTree.parents('[data-multi="true"]').each(function() {
		var s = $(this);
		var z = parseInt(s.css("z-index"));
		var dt = s.children("dt");
		var dd = s.children("dd");
		var _show = function() {
			dd.slideDown(200);
			dt.addClass("cur");
			s.css("z-index", z + 1);
		}; //展开效果
		var _hide = function() {
			dd.slideUp(200);
			dt.removeClass("cur");
			s.css("z-index", z);
		}; //关闭效果
		dt.unbind("click").click(function(e) {
			e.stopPropagation();
			dd.is(":hidden") ? _show() : _hide();
		});
		dd.find("a").click(function(e) {
			e.stopPropagation();
			var $target= jQuery(e.currentTarget);
			var field = $target.parents(".select");
            var input= field.data("input")
			if(field.data('multi')==true){
                $target.parents("li").toggleClass("selected");
                field.parents(".bomp-p").find(input).val(
                    $target.parents("ul").find(".selected a").map(function(){
                        return $(this).data('value');
                    }).get().join(",")
				);
                dt.html(
                    $target.parents("ul").find(".selected a").map(function(){
                        return $(this).text();
                    }).get().join(",")
				);
                dt.attr("title",
               		 $target.parents("ul").find(".selected a").map(function(){
                            return $(this).text();
                        }).get().join(",")
                   );
                if($target.parents("ul").find(".selected a").length == 0){
					 dt.text(dt.data("placeholder")).attr("title",dt.data("placeholder"));
				}
			}else{
			//	$target.parents("li").siblings().removeClass("selected");
             //   $target.parents("li").toggleClass("selected");
                field.find(input).val($(this).data('value'));
                dt.html($(this).html());
                _hide();
			}
		}); //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）
		
		$(document).click(function(){
			if(!dd.is(":hidden")){
				_hide();
			}
		});
 	});
}

// 行动标签
function actionTagInit(labelList) {
	var length = labelList.length;
	var tip_box = $(".actionTag .tip-box");
	tip_box.html(tip_box_html);
	var tip_box_hidden = tip_box.find(".sty-hid");
	var icon_down = $(".actionTag>.icon_down");
	icon_down.hide();
	for (var i = 0; i < length; i++) {
		var appendCode = "";
		if (labelList[i].isDefault == "1") {
			appendCode = "<a href='###' class='click-hover' name='" + labelList[i].optionId + "'>" + labelList[i].optionName + "</a>";
		} else {
			appendCode = "<a href='###' name='" + labelList[i].optionId + "'>" + labelList[i].optionName + "</a>";
		}
		tip_box.append(appendCode);
		var tip_box_a = tip_box.find("a:last");
		if (tip_box.height() > 25) {
			icon_down.show();
			tip_box_a.appendTo(tip_box_hidden);
		} else {
			tip_box_a.insertBefore(tip_box_hidden);
		}
	}
	$(".hyx-sce-area .tip-box a").each(function() {
		var $this = $(this);
		$this.click(function(e) {
			e.stopPropagation();
			if ($this.hasClass("click-hover") == true) {
				$this.removeClass("click-hover");
			} else {
				$this.addClass("click-hover");
			}
			assembleChosenLabel();
		});
	});
}

function groupActionTagInit(){
	var tip_box = $(".actionTag .tip-box");
	tip_box.html(tip_box_html);
	$(".hyx-sce-area").delegate(".choose-tag","click",function(e){
		var labelCodeId = $(this).attr("data-chosen-id")?$(this).attr("data-chosen-id"):"";
		pubDivShowIframe('alert_action_tag_choose',ctx+'/res/tao/toActionTagChoose?optionlistIds='+labelCodeId,'行动标签',610,420);
	});
}

// 组装选中的行动标签，改变<input type="hidden">的值
function assembleChosenLabel() {
	var tempLabel = '';
	var tempLabelName = '';
	$('.tip-box').find('a').each(function(item, obj) {
		if ($(this).hasClass('click-hover') == true) {
			tempLabel = tempLabel + $(this).attr('name') + '#';
			tempLabelName = tempLabelName + $(this).text() + '#';
		}
	});
	$('#labelCodeId').val(tempLabel.replace(/#$/g,""));
	$('#labelNameId').val(tempLabelName.replace(/#$/g,""));
}
// 销售进程
function saleProcessInit(saleProcessList) {
	var length = saleProcessList.length;
	var saleProcess_list = $(".saleProcess_list");
	saleProcess_list.html("<option value='' selected>--请选择--</option>");
	for (var i = 0; i < length; i++) {
		var appendCode = "";
		if (saleProcessList[i].isDefault == "1") {
			appendCode = "<option value='" + saleProcessList[i].optionId + "' selected>" + saleProcessList[i].optionName + "</option>";
		} else {
			appendCode = "<option value='" + saleProcessList[i].optionId + "'>" + saleProcessList[i].optionName + "</option>";
		}
		saleProcess_list.append(appendCode);
	}
}

// 判断是否为空
function judgeIsNull(mobilephone, telphone, qq, weChat, email, wangwang) {
	var isReplaceWord = $('#isReplaceWord').val();
	$("#custInfo_mobilephone").attr('name', mobilephone);
	$("#custInfo_telphone").attr('name', telphone);
	$("#custInfo_mobilephone").text(mobilephone);
	$("#custInfo_telphone").text(telphone);
	if (isReplaceWord == '1') {
		replaceWord(mobilephone, $("#custInfo_mobilephone"));
		replaceWord(telphone, $("#custInfo_telphone"));
	}
	$("#custInfo_qq").text(qq);
	// $("#custInfo_weChat").text(weChat);
	$("#custInfo_email").text(email);
	$("#custInfo_wangwang").text(wangwang);

	if (isNull(mobilephone)) {
		var img = $(".custInfo_mobilephone>img");
		changeImgBlue(img);
		$("#custInfo_mobilephone").parent().removeClass("custInfo_noTel");
	} else {
		var img = $(".custInfo_mobilephone>img");
		changeImgGrey(img);
		$("#custInfo_mobilephone").parent().addClass("custInfo_noTel");
	}

	if (isNull(telphone)) {
		var img = $(".custInfo_telphone>img");
		changeImgBlue(img);
		$("#custInfo_telphone").parent().removeClass("custInfo_noTel");
	} else {
		var img = $(".custInfo_telphone>img");
		changeImgGrey(img);
		$("#custInfo_telphone").parent().addClass("custInfo_noTel");
	}

	if (isNull(qq)) {
		var img = $(".custInfo_qq>img");
		changeImgBlue(img);
		$(".custInfo_qq").attr("title",qq);
		$(".custInfo_qq").click(function() {
			qqSend(qq);
		});
	} else {
		var img = $(".custInfo_qq>img");
		changeImgGrey(img);
		$(".custInfo_qq").attr("title","");
		$(".custInfo_qq").unbind("click");
	}
	
	var concatId = "";
	var name = "";
	if ($('#isState').val() == '1') {
		concatId = $('#concatId').val();
		name = $('#custName').val();
	} else {
		concatId = $('#custId').val();
		name = $('#concat_name').val();
	}
	if (isSet() == '1') {		
		initWxButton(concatId, name);
		/*
		 * $(".btn_reBind_weChat").hover( function(){
		 * $(".icon_reBind_weChat").hide();
		 * $(".icon_reBind_weChat_grey").show(); }, function(){
		 * $(".icon_reBind_weChat").hide();
		 * $(".icon_reBind_weChat_white").show(); } );
		 */
		$(".btn_bind_weChat").unbind("click").on("click", function(e) {
			e.stopPropagation();
			bindWxChat(concatId, name, "bind");
		});
		$(".btn_reBind_weChat").unbind("click").on("click", function(e) {
			e.stopPropagation();
			bindWxChat(concatId, name, "rebind");
		});
		$(".custInfo_weChat").attr("title",name);
		$(".custInfo_weChat").unbind("click").unbind("click").click(function() {
			var $this = $(this);
			if ($this.find("img").attr("src").indexOf("icon_white") >= 0) {
				bindWxChat(concatId, name, "chat");
			}
		});
	} else {
		$(".custInfo_weChat").attr("title","");
		$(".custInfo_weChat").unbind("click");
	}

	if (isNull(email)) {
		var img = $(".custInfo_email>img");
		changeImgBlue(img);
		$(".custInfo_email").attr("title",email);
		$(".custInfo_email").click(function() {
			emailBysend(name, email);
		});
	} else {
		var img = $(".custInfo_email>img");
		changeImgGrey(img);
		$(".custInfo_email").attr("title","");
		$(".custInfo_email").unbind("click");
	}

	if (isNull(wangwang)) {
		var img = $(".custInfo_wangwang>img");
		changeImgBlue(img);
		$(".custInfo_wangwang").attr("title",wangwang);
		$(".custInfo_wangwang").click(function() {
			var url = "http://www.taobao.com/webww/ww.php?ver=3&touid=" + wangwang + "&siteid=cntaobao&status=1&charset=utf-8";
			window.open(url);
		});
	} else {
		var img = $(".custInfo_wangwang>img");
		changeImgGrey(img);
		$(".custInfo_wangwang").attr("title","");
		$(".custInfo_wangwang").unbind("click");
	}
	// 必须要优化
	$(".custInfo_mobilephone").unbind('click').click(function() {
		if ($("#custInfo_mobilephone").attr('name') != "" && $("#custInfo_mobilephone").attr('name') != null) {
			var $this = $(this);
			var index = $this.index();
			if (index == 0) {
				setIsLianxi($("#custId").val(), $("#custInfo_mobilephone").attr('name'), $("#custName").val(), $("#concat_name").val(), $("#company").val(), $("#concatId").val());
			} else if (index == 1) {
				toSmsSendPage($("#custName").val(), $("#custInfo_mobilephone").attr('name'));
			}
		}
	});

	$(".custInfo_telphone").unbind('click').click(function() {
		if ($("#custInfo_telphone").attr('name') != "" && $("#custInfo_telphone").attr('name') != null) {
			var $this = $(this);
			var index = $this.index();
			if (index == 0) {
				setIsLianxi($("#custId").val(), $("#custInfo_telphone").attr('name'), $("#custName").val(), $("#concat_name").val(), $("#company").val(), $("#concatId").val());
			} else if (index == 1) {
				toSmsSendPage($("#custName").val(), $("#custInfo_telphone").attr('name'));
			}
		}
	});
}
// 设置是否已经联系
function setIsLianxi(custId, phone, name, defined1, defined3, defined4) {
	var custFollowId = $('#followId').val();
	$('#concat_phone').val(phone);
	if ($('#isState').val() == '1') {
		$('#concat_name').val(defined1);
	} else {
		$('#concat_name').val(name);
	}
	if (custId == null || custId == '') {
		window.top.iDialogMsg('提示', "资源id 不存在，刷新页面试试！！！");
		return false;
	}
	taoCallPhone(phone, custId, name, custFollowId, defined1, defined3, defined4);
	if ($('#isState').val() == '1') {
		queryTaoByCustId(defined4);
	} else {
		queryTaoByCustId(custId);
	}
	//queryTaoByCustId(custId);
	// updatePlanResult(custId)
}

/** 待分配资源/延后呼叫 列表 * */
// 初始十条数据
function initTenResources() {
	var pool = $('#pool').val();
	$.ajax({
		url : 'initTaoRes',
		type : 'post',
		data : {
			'pool' : pool
		},
		dataType : 'json',
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				// alert("加载超时，请重试");
			} else {
				// alert(textStatus);
			}
		},
		success : function(data) {
			if (data == null || data == "" || data == undefined) {
				return false;
			}
			array = new Array();
			for (var i = 0; i < data.length; i++) {
				var obj = new Object();
				obj.name = data[i].name;
				obj.phone = data[i].phone;
				obj.resId = data[i].resId;
				obj.isCall = data[i].isCall;
				obj.status = data[i].status;
				obj.filterType = data[i].filterType;
				array[i] = obj;
			}
			if (array.length > 0) {
				var currResId = $('#currResId').val();
				chooseActiveResource(currResId);
				setChosenResourceId(currResId);
			}
		}
	});
}

// 点击资源列表
function chooseActiveResource(custId) {
	$('#' + custId).parent().siblings().removeClass("active");
	$('#' + custId).parent().addClass('active');
}
// 查找系统当前默认选中行
function setChosenResourceId(custId) {
	for (var i = 0; i < array.length; i++) {
		if (custId == array[i].resId) {
			mark = i;
		}
	}
}
// 全部刷新
function getFresh(upOrDown) {
	var timestamp = new Date().getTime();
	var resourceGroupId = $("#resourceGroupId").val();
	var orderType = $("#orderType").val();
	var isConcat = $('#isConcat').val();
	var pool = $('#pool').val();
	var isConcat = $('#isConcat').val();
	$.ajax({
		url : ctx + '/res/tao/getFresh',
		data : {
			timestamp : timestamp,
			upOrDown : upOrDown,
			resourceGroupId : resourceGroupId,
			orderType : orderType,
			isConcat : isConcat,
			pool : pool
		},
		dataType : 'json',
		type : 'post',
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				// alert("加载超时，请重试");
			} else {
				// alert(textStatus);
			}
		},
		success : function(data) {
			resourcesTableInit(data);
			var currResId = $("#currResId").val();
			if (currResId != '') {
				initTenResources();
			}
			taoMainPlatform(data.custId);
			taoCustGuide(data.custId);
			getRemarkList(data.custId);
		}
	});
}
// 刷新操作页面
function getResource(resId, upOrDown, isFromOtherPage) {
	var resourceGroupId = $("#resourceGroupId").val();
	var orderType = $("#orderType").val();
	var isConcat = $('#isConcat').val();
	var pool = $('#pool').val();
	var isConcat = $('#isConcat').val();
	$.ajax({
		url : ctx + '/res/tao/getMyRes',
		data : {
			resId : resId,
			upOrDown : upOrDown,
			resourceGroupId : resourceGroupId,
			orderType : orderType,
			isConcat : isConcat,
			isFromOtherPage : isFromOtherPage,
			pool : pool
		},
		dataType : 'json',
		type : 'post',
		async : false,
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				// alert("加载超时，请重试");
			} else {
				// alert(textStatus);
			}
		},
		success : function(data) {
			if (isFromOtherPage == '1') {
				$('#isFromOtherPage').val(1);
			} else {
				$('#isFromOtherPage').val(0);
			}
			var status = data.status;
			var resId = data.resId;
			var isCall = data.isCall;
			var name = data.name;
			var phone = data.phone;
			var filterType = data.filterType;
			var type = data.type;
			var followId = data.followId;
			$('#followId').val(followId);
			$('#custId').val(resId);
			if (!isDisabled(status, type, filterType, pool)) {
				chooseActiveResource(resId);
				taoMainPlatform(resId); // 联系显示
				taoCustGuide(resId);
				getRemarkList(resId);
			} else {
				// 修改状态
				updateStatus(pool, status, resId, isCall, name, phone, filterType, type);
				if (upOrDown == 1) {
					up();
				} else {
					next();
				}

			}
			isSubmited = false;
		}
	});
}

//
//下一页,仅针对淘客户和签约
function taonext() {
	taosetNextMark();
	if (mark >= array.length) {
		getFresh("2");
	} else {
		var custId = array[mark].resId;
		getResource(custId, 2, 1);
	}
}

//下一条标记，,仅针对淘客户和签约
function taosetNextMark() {
	var temResId = $('#tempResId').val();
	if (temResId != '1') {
		mark++;
	}
	$('#tempResId').val("0");
}
////////////////////////////////////


// 下一条标记
function setNextMark() {
	callhang();
	var temResId = $('#tempResId').val();
	if (temResId != '1') {
		mark++;
	}
	$('#tempResId').val("0");
}
// 上一条标记
function setUpMark() {
	callhang();
	var temResId = $('#tempResId').val();
	if (temResId != '1') {
		mark--;
	}
	$('#tempResId').val("0");
}
// 下一页
function next() {
	setNextMark();
	if (mark >= array.length) {
		getFresh("2");
	} else {
		var custId = array[mark].resId;
		getResource(custId, 2, 1);
	}
}
// 上一页
function up() {
	setUpMark();
	if (mark < 0) {
		getFresh("1");
	} else {
		var custId = array[mark].resId;
		getResource(custId, 1, 1);
	}
}

//// 选择筛选分组
//function selectResourceGroup(resourceGroupId) {
//	// 淘客户资源分组排序选中,判断当前所选分组的资源数是否超过固定大小
//	var faxNum = 50000;
//	var pool = $('#pool').val();
//	var orderType = $("#orderType").val();
//	var isConcat = $('#isConcat').val();
//	var resNum = 0;
//	$.ajax({
//		url : 'getMyResByGroupId',
//		type : 'post',
//		dataType : 'json',
//		data : {
//			"resourceGroupId" : resourceGroupId,
//			"orderType" : orderType
//		},
//		error : function(jqXHR, textStatus, errorThrown) {
//			if (textStatus == "timeout") {
//				// alert("加载超时，请重试");
//			} else {
//				// alert(textStatus);
//			}
//		},
//		success : function(data) {
//			if (data == '-3') {
//				window.top.iDialogMsg('提示', '操作失败,请重试');
//				return;
//			} else {
//				resNum = parseInt(data);
//				if (resNum > faxNum) {
//					window.top.iDialogMsg('提示', "所选分组下资源超过5000条，建议合理划分资源组");
//					$("#resourceGroupId").val($("#groupSelectId").val());
//				} else {
//					// var timestamp = new Date().getTime();
//					// document.location.href = ctx +
//					// '/res/tao/updateTaoTag?resourceGroupId=' + val +
//					// "&orderType=" + orderType + "&isConcat=" + orderType +
//					// "&pool=" + pool + "&v=" + timestamp;
//					// $("#refreshOneId").val("1");
//					freshCache(resourceGroupId, orderType, isConcat);
//				}
//			}
//		}
//	});
//}


//选择筛选分组
function selectResourceGroup(resourceGroupId) {
	var orderType = $("#orderType").val();
	var isConcat = $('#isConcat').val();
	freshCache(resourceGroupId, orderType, isConcat);
}

// 选择资源分组
function chooseResourceListGroup() {
	var sellis = $(".shows-ul-linshi li a");
	sellis.each(function() {
		var $this = $(this);
		$this.click(function(e) {
			e.stopPropagation();
			selectResourceGroup($this.attr("select_res_id"));
			$("#resourceGroupId").val($this.attr("select_res_id"));
			$(".shows-linshi>.select_text").text($this.text());
			$this.parents("dd").hide();
		});
	});
}

// 选择分配时间排序
function chooseResourceListOrder() {
	var sellis = $(".shows-select-order li a");
	sellis.each(function() {
		var $this = $(this);
		$this.click(function(e) {
			e.stopPropagation();
			selectResourceOrder($this.attr("select_order_id"));
			$("#orderType").val($this.attr("select_order_id"));
			$(".shows-fenpei>.select_text").text($this.text());
			$this.parents("dd").hide();
		});
	});
}

// 是否联系过得资源排序
function chooseResourceListContact() {
	var sellis = $(".shows-contact-ye li a");
	sellis.each(function() {
		var $this = $(this);
		$this.click(function(e) {
			e.stopPropagation();
			selectResourceConcat($this.attr("contact_id"));
			$("#isConcat").val($this.attr("contact_id"));
			$(".shows-contact>.select_text").text($this.text());
			$this.parents("dd").hide();
		});
	});
}

// 选择筛选排序
function selectResourceOrder(orderType) {
	var timestamp = new Date().getTime();
	var pool = $('#pool').val();
	var resourceGroupId = $("#resourceGroupId").val();
	var isConcat = $('#isConcat').val();
	freshCache(resourceGroupId, orderType, isConcat);
	$("#refreshOneId").val("1");
}

// 选择筛选是否联系过的资源
function selectResourceConcat(isConcat) {
	var timestamp = new Date().getTime();
	var pool = $('#pool').val();
	var resourceGroupId = $("#resourceGroupId").val();
	var orderType = $("#orderType").val();
	freshCache(resourceGroupId, orderType, isConcat);
	$("#refreshOneId").val("1");
}

// 刷新列表缓存
function freshCache(resourceGroupId, orderType, isConcat) {
	var timestamp = new Date().getTime();
	var pool = $('#pool').val();
	// var resourceGroupId = $("#resourceGroupId").val();
	// var orderType = $("#orderType").val();
	// var isConcat = $('#isConcat').val();

	$.ajax({
		url : ctx + '/res/tao/updateTaoTag',
		data : {
			resourceGroupId : resourceGroupId,
			orderType : orderType,
			isConcat : isConcat,
			pool : pool,
			v : timestamp
		},
		dataType : 'json',
		type : 'post',
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "timeout") {
				// alert("加载超时，请重试");
			} else {
				// alert(textStatus);
			}
		},
		success : function(data) {
			resourcesTableInit(data);

			var currResId = $("#currResId").val();
			var custId = $("#custId").val();
			if (currResId != '') {
				initTenResources();
			}
			$("#refreshOneId").val("1");
			taoMainPlatform(custId);
			taoCustGuide(custId);
			getRemarkList(custId);

			// 点击其他资源
			/*
			 * var isSubmited = false; $('tr[id^=otherRes_Tr_]').on('click',
			 * function() { if (isSubmited) { return false; } var click_tr =
			 * $(this); if (click_tr.attr("disabled") == "disabled") { return
			 * false; } click_tr.siblings('tr').removeClass("active");
			 * click_tr.addClass("active"); var resId =
			 * $(this).attr('id').split('_')[2]; otherRes(resId, 2, 1); });
			 */
		}
	});

}

// 改变当前表格选中列的信息状态
function updateStatus(pool, status, resId, isCall, name, phone, filterType, type) {
	var $obj = "";
	/*
	 * // 模糊处理手机、电话号码 var isReplaceWord = $("#isReplaceWord").val(); if
	 * (isReplaceWord == 1) { phone = replaceWordStr(phone); }
	 */
	if (status == null || status == undefined) {
		status = "";
	}
	if (resId == null || resId == undefined) {
		resId = "";
	}
	if (isCall == null || isCall == undefined) {
		isCall = "";
	}
	if (name == null || name == undefined) {
		name = "";
	}
	// if (phone == null || phone == undefined) {
	// phone = "";
	// }
	if (type == null || type == undefined) {
		type = "";
	}
	$obj = showResStatus(status, type, filterType, pool);
	var $tr = $('#otherRes_Tr_' + resId);
	var $td = $('#otherRes_Td_' + resId);
	$td.find(".td_tel_span").append($obj.remark_pic);
	$tr.attr("disabled", "disabled");
	if ($tr.find("td[colspan='5']").length == 0) {
		$tr.append('<td colspan="5" class="tr_note">' + $obj.statusName + '</td>');
	}
	$tr.removeClass("active");
	return;
}
// 获取资源状态
function getResStatus(statusName, remark_pic) {
	this.statusName = statusName;
	this.remark_pic = remark_pic;
	return this;
}
// 显示资源状态
function showResStatus(status, type, filterType, pool) {
	var statusName = '';
	var remark_pic = '';
	if ((status == 2 || status == 3) && type == 1 && filterType == 2 && pool == '1') {
		statusName = ' 该资源被设置为延后呼叫资源！';
		remark_pic = "<img class='remark_img' src='" + ctx + "/static/images/already_delay.png' />";
	}
	if (status == 5 || status == 4) {
		statusName = '该资源已放入公海';
		remark_pic = "<img class='remark_img' src='" + ctx + "/static/images/already_giveUp.png' />";
	} else if (status == 3 && type == 2) {
		statusName = '该资源已转意向！';
		remark_pic = "<img class='remark_img' src='" + ctx + "/static/images/already_intent.png' />";
	} else if (status == 1) {
		statusName = ' 该资源已被回收！';
	} else if (status == 6) {
		statusName = '该资源已完成签约！';
		remark_pic = "<img class='remark_img' src='" + ctx + "/static/images/already_signed.png' />";
	} else if (status == '') {
		statusName = '该资源已被回收分配给其他人员';
	} else {

	}
	return getResStatus(statusName, remark_pic);
}

// 是否隐藏，不让点击
function isDisabled(status, type, filterType, pool) {
	var isDisabled = true;
	if (pool == '1') {
		if ((status == 2 || status == 3) && type == 1 && filterType != 2) {
			isDisabled = false;
		}
	} else if (pool == '2') {
		if (type == 1 && filterType == 2) {
			isDisabled = false;
		}
	}
	return isDisabled;
}
// 检查长度
function checkLength(chkVal, len) {
	var pattern = /[^\x00-\xff]/g;
	return chkVal.replace(pattern, "gg").length <= len;
}
// 必填字段提示信息
function setShowMsg(id) {
	var isTrue = true;
	var val = $('#' + id).val();
	if (val == null || val == "" || val == undefined) {
		$('#error_' + id).html('必填项');
		$("#bomp_error_" + id).addClass("bomp-error");
		isTrue = false;
	} else {
		$('#error_' + id).html('');
		$("#bomp_error_" + id).removeClass("bomp-error");
	}
	return isTrue;
}

// 将按钮不可用
function disabledButton(click_btn) {
	var $_this = click_btn;
	$_this.text("请稍候");
	// $_this.attr("disabled","true");
	$_this.css("cursor", "not-allowed");
}

// 自动连拨
function autoCall() {
	var custId = $('#custId').val();
	var autoCallResId = $('#autoCallResId').val();
	if (custId == autoCallResId) { // 播过之后，修改资源的情况下，要避免重复播放
		return;
	}
	var isAutoCall = $('#isAutoCall').val();
	var name = $('#custName').val();
	var phone = $('#concat_phone').val();
	var custFollowId = $('#followId').val();
	var concatName = $('#concat_name').val();
	var company = $('#company').val();
	var concatId = $('#concatId').val();
	$('#autoCallResId').val(custId);
	var isFromOtherPage = $('#isFromOtherPage').val();
	if (isAutoCall == '1' && isFromOtherPage == '1') {
		setTimeout(function() {
			taoCallPhone(phone, custId, name, custFollowId, concatName, company, concatId);
		}, 0);
	}
}
//校验自动连拨和销售管理中的开关都打开
function callhang() {
	var isAutoCall=$('#isAutoCall').val();
	if(isAutoCall == '1'){
		$.ajax({
			url : '/res/tao/getSallAutoCall',
			type : 'post',
			dataType : 'json',
			data : {},
			success : function(data) {
				if(data == "1"){
					tocallhangPhone();	
				}
			},
			error : function() {
			}
		});
		
	}
}

//挂电话
function tocallhangPhone() {
	$.ajax({
		url : 'http://127.0.0.1:3366/callhang?callback=cb_callout&Lid=12345',
		type : 'get',
		dataType : 'json',
		data : {},
		success : function(data) {
			
		},
		error : function() {
		}
	});
}

// 打电话公共方法
function taoCallPhone(phone, resCustId, name, followId, defined1, company, concatId) {
	var arrays = new Array();
	arrays[0] = "\"custId\":\"" + resCustId + "\"";
	arrays[1] = "\"custName\":\"" + name + "\"";
	arrays[2] = "\"followId\":\"" + followId + "\"";
	arrays[3] = "\"custType\":\"" + 1 + "\"";
	arrays[4] = "\"custState\":\"" + 2 + "\"";
	arrays[5] = "\"account\":\"" + $('#account').val() + "\"";
	arrays[6] = "\"orgId\":\"" + $('#orgId').val() + "\"";
	if ($('#isState').val() == '1') {
		arrays.push("\"define1\":\"" + defined1 + "\"");
	} else {
		arrays.push("\"define1\":\"" + name + "\"");
	}
	if ($.trim(company) != "" && $('#isState').val() == '0') {
		arrays.push("\"define3\":\"" + company + "\"");
	}
	if ($('#isState').val() == '1') {
		arrays.push("\"define4\":\"" + concatId + "\"");
	}
	var isConcat = $('#resIsConcat').val();
	window.top.custCallPhone(phone, arrays, resCustId, isConcat, concatId);
}

// 通话记录信息
function queryTaoByCustId(id) {
	// debugger;
	var url = "/res/tao/getCallNum";
	$.ajax({
		url : url,
		type : 'post',
		dataType : 'json',
		data : {
			"id" : id
		},
		success : function(data) {
			var calledNum = parseInt(data.num);
			$('#list_callnum').text(calledNum);
		},
		error : function() {
		}
	});
}
//// 通话记录信息
//function queryTaoByCustIdold(custid) {
//	// debugger;
//	var istate = $('#isState').val();
//	/*
//	 * if(istate == 1){ //企业的不统计资源统计记录，只统计联系人通话记录 return; }
//	 */
//	var currTime = new Date().getTime();
//	var orgId = $('#orgId').val();
//	var timeElngth = 0;
//	var url = $('#callStatUrl').val();
//	var inputAcc = $('#account').val();
//	var par = "orgId=" + orgId + "&timeLength=" + timeElngth + "&custId=" + custid + "&inputAcc=" + inputAcc + '&v=' + currTime;
////	console.log("url:" + url + "?" + par);
//	$.ajax({
//		type : "get",
//		url : url + "?" + par,
//		// url : "http://192.168.1.192:8081/res/callstat/queryCallNum?" + par,
//		// url : "http://192.168.1.10:6063/res/callstat/queryCallNum?" + par,
//		// data:{orgId:orgId,timeLength:timeLength,custId:custId,inputAcc:inputAcc,v:currTime},
//		// url : "http://call.qftx.net/service/queryTaoByCustId?" + par,
//		// url:"http://192.168.1.177:6090/service/queryTaoByCustId?" + par,
//		dataType : "jsonp",
//		jsonp : "callback",// 传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(一般默认为:callback)
//		jsonpCallback : "flightHandler",// 自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名，也可以写"?"，jQuery会自动为你处理数据
//		success : function(json) {
//			var str = "";
//			str += JSON.stringify(json);
//			// str += '\n通话类型=' + json[0].name + " 通话数=" + json[0].num;
//			// str += '\n通话类型=' + json[1].name + " 通话数=" + json[1].num;
//			// str += '\n通话类型=' + json[2].name + " 通话数=" + json[2].num;
//			var calledNum = parseInt(json.num);
//			$('#list_callnum').text(calledNum);
//		},
//		error : function() {
//		}
//	});
//}
// 联系人通话次数
function queryCallNum(resId, detailId) {
	$.ajax({
		url : 'queryCallNum',
		type : 'post',
		dataType : 'json',
		data : {
			"resId" : resId,
			"detailId" : detailId
		},
		success : function(data) {
			// alert(JSON.stringify(data))
			// todo
		},
		error : function() {
		}
	});
}
// 进入页面默认排序显示
function resourceListDefaultChosenSelect() {
	var linshi = $("#resourceGroupId").val();
	var fenpei = $("#orderType").val();
	var concat = $("#isConcat").val();
	var linshis, fenpeis, concats;
	$(".shows-ul-linshi li a").each(function() {
		if (linshi == $(this).attr("select_res_id")) {
			linshis = $(this).text();
		}
	});
	$(".shows-select-order li a").each(function() {
		if (fenpei == $(this).attr("select_order_id")) {
			fenpeis = $(this).text();
		}
	});
	$(".shows-contact-ye li a").each(function() {
		if (concat == $(this).attr("contact_id")) {
			concats = $(this).text();
		}
	});

	$(".shows-linshi>.select_text").text(linshis);
	$(".shows-fenpei>.select_text").text(fenpeis);
	$(".shows-contact>.select_text").text(concats);
}

function isSet() {
	return $('#isSet').val();
}

function testbase64() {
	var b = new Base64();
	// var data =
	// '{"wxName":"测试","custId":"dc087011cab9468094dbc38cd46030bf","wxId":"","wxLoginId":"微信loginid","custName":"唐跃强"}';
	var base64Json = 'eyJjdXN0SWQiOiJkYzA4NzAxMWNhYjk0NjgwOTRkYmMzOGNkNDYwMzBiZiIsImN1c3ROYW1lIjoi5ZSQ6LeD5by6Iiwid3hJZCI6IjY0ODEwNjc3MyIsInd4TG9naW5JZCI6IjIwMzIzMzM0MjgiLCJ3eE5hbWUiOiLnmb3kupHoi43ni5cifQo=';
	window.top.shipWxChat(base64Json);
	// var base64Data = b.encode(data);
	// reBindWxChat("dc087011cab9468094dbc38cd46030bf","唐跃强")
}

function toCard(custId, name, company, isState) {
	var custName = "";
	if (isState == "1") {
		custName = name;
	} else {
		if (company == "" || company == null) {
			custName = name;
		} else {
			custName = company;
		}
	}
	custName = custName || "客户卡片";
	window.top.addTab(ctx + "/cust/card/custInfoCard?custId=" + custId, custName);
}