$(function() {
	Array.prototype.indexOf = function(val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == val) return i;
		}
		return -1;
	};
	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};

	var module = $("#module").val();
	var module_url = "";
	if(module == "cust_silent" || module == "cust_losing" || module == "call_record"){
		module_url = ctx+"/highSearch/toOtherPageJson";
	}else{
		module_url = ctx+"/highSearch/toPageJson";
	}
	
	$.ajax({
		type: "get",
		url: module_url,
		cache: false,
		data: {
			"module": module
		},
		success: function(jsonStr) {
			var data = $.parseJSON(jsonStr);
//			console.log(data);
			//console.log(jsonStr);
			renderQueryContent(data);
			delegateEvent(data);
		}
	});

	function delegateEvent(data){
		$(".tree-box").each(function(index){
			var _this = $(this);
			_this.find(".tree-ul-box").attr("data-type","search-tree");
			_this.find(".tree-ul-box").treeSearch();
		});
		
		$(".time-input").on("blur",function(e){
			var _this = $(this);
			_this.val(_this.val().replace(/\D/g, ''));
		});
		
		$(".dateRange").each(function(e) {
			var _this = $(this);
			_this.dateRangePicker({
				showShortcuts: false,
				endDate: moment().format("YYYY-MM-DD"),
				format: 'YYYY-MM-DD'
			}).bind('datepicker-apply', function(event, obj) {
				var startDate = '';
				var endDate = '';
				var date_show_area = _this.parent().find(".data-show-area");
				var title_date_show = _this.parent().prev(".advancedQuery-everytitle").find(".data-show-area");
				if(obj.date1 != 'Invalid Date') {
					startDate = moment(obj.date1).format('YYYY-MM-DD');
				}
				if(obj.date2 != 'Invalid Date') {
					endDate = moment(obj.date2).format('YYYY-MM-DD');
				}
				if($("#module").val() == "call_record" && moment(endDate).diff(moment(startDate),'days') >= 30){
					window.top.iDialogMsg("提示","通话日期时间跨度选择不能大于30天");
					return false;
				}
				date_show_area.text(startDate + "/" + endDate);
				title_date_show.text(startDate + "/" + endDate);
			});
		});

		$(".dateRange-later").each(function(e) {
			var _this = $(this);
			_this.dateRangePicker({
				showShortcuts: false,
				format: 'YYYY-MM-DD'
			}).bind('datepicker-apply', function(event, obj) {
				var startDate = '';
				var endDate = '';
				var date_show_area = _this.parent().find(".data-show-area");
				var title_date_show = _this.parent().prev(".advancedQuery-everytitle").find(".data-show-area");
				if(obj.date1 != 'Invalid Date') {
					startDate = moment(obj.date1).format('YYYY-MM-DD');
				}
				if(obj.date2 != 'Invalid Date') {
					endDate = moment(obj.date2).format('YYYY-MM-DD');
				}
				date_show_area.text(startDate + "/" + endDate);
				title_date_show.text(startDate + "/" + endDate);
			});
		});

		$(".single-checked>.advancedQuery-tagincont").on("click", function(e) {
			var _this = $(this);
			var _this_radio = _this.find("input[type='radio']");
			_this_radio.attr("checked", true);
			if(_this_radio.attr("checked")) {
				_this.siblings().find("input[type='radio']").attr("checked", false);
				_this.siblings().removeClass("active");
				_this.addClass("active");
			}
		});

		$(".multi-checked>.advancedQuery-tagincont").on("click",function(e){
			var _this = $(this);
			var _this_check = _this.find("input[type='checkbox']");
			if(_this.hasClass("active")){
				_this_check.attr("checked", false);
				if(_this_check.attr("checked")) {
					_this.addClass("active");
				} else {
					_this.removeClass("active");
				}
			}else{
				_this_check.attr("checked", true);
				if(_this_check.attr("checked")) {
					_this.addClass("active");
				} else {
					_this.removeClass("active");
				}
			}
		});

		$(".date-checked>.advancedQuery-tagincont").on("click", function(e) {
			var _this = $(this);
			var startDate = '';
			var endDate = moment().format('YYYY-MM-DD');
			var date_show_area = _this.parent().find(".data-show-area");
			var title_date_show = _this.parent().prev(".advancedQuery-everytitle").find(".data-show-area");
			if(_this.hasClass("date-no-time")) {
				date_show_area.text("");
				title_date_show.text("");
			} else if(_this.hasClass("date-today")) {
				startDate = moment().format('YYYY-MM-DD');
				date_show_area.text(startDate + "/" + endDate);
				title_date_show.text(startDate + "/" + endDate);
			} else if(_this.hasClass("date-week")) {
				startDate = moment().day("Sunday").format('YYYY-MM-DD');
				endDate = moment().day("Saturday").format('YYYY-MM-DD');
				date_show_area.text(startDate + "/" + endDate);
				title_date_show.text(startDate + "/" + endDate);
			} else if(_this.hasClass("date-month")) {
				startDate = moment().startOf('month').format('YYYY-MM-DD');
				endDate = moment().endOf('month').format('YYYY-MM-DD');
				date_show_area.text(startDate + "/" + endDate);
				title_date_show.text(startDate + "/" + endDate);
			} else if(_this.hasClass("date-half-year-ago")) {
				startDate = moment().subtract(6, 'months').format('YYYY-MM-DD');
				date_show_area.text(startDate + "/" + endDate);
				title_date_show.text(startDate + "/" + endDate);
			} else if(_this.hasClass("date-half-year-later")) {
				startDate = moment().format('YYYY-MM-DD');
				endDate = moment().add(6, 'months').format('YYYY-MM-DD');
				date_show_area.text(startDate + "/" + endDate);
				title_date_show.text(startDate + "/" + endDate);
			}
		});

		$(".tree-checked .treeNode-checked").on("click", function(e) {
			e.stopPropagation();
			var _this = $(this);
			_this.parents(".advancedQuery-everycontent").find(".tree-box").show();
		});
		
		$(".city-checked .city-select-checked").on("click", function(e) {
			e.stopPropagation();
			var _this = $(this);
			SelCity(this,e,function(data){
				_this.parents(".advanced-query-block").find(".data-show-area").text(data);
			});
		});
		
		$(".res-group-checked .res-group-select-checked").on("click",function(e){
			e.stopPropagation();
			var _this = $(this);
			_this.parents(".advancedQuery-everycontent").find(".tree-box").show();
		});
		
		$("[data-type='resource-tree']").each(function(){
			var _this = $(this);
			var _url = ctx + "/res/group/get_res_group_json";
			var _tree_box = _this.parents(".tree-box");
			var selectedNode = _this.data("value");
			var _return_text = _this.parents(".advanced-query-block").find(".data-show-area");
			$.ajax({
				url: _url,
				type: "get",
				success: function(data){
					var first_source = {
						"id":"",
						"text":"资源分组",
						"type":"none",
						"level":"none",
						"isSharePool":"none",
						"inputAcc":"none"
					};
					
					data.splice(0,0,first_source);
					
					_this.tree({
						data: data,
						formatter: function(node){
							var nodeText = "<label class='tree-node-label' data-id='"+node.id+"' data-share-pool='"+node.isSharePool+"'>" + node.text + "</label>";
							return nodeText;
						},
						onBeforeSelect:function(node){
							if(node.level != 1 && node.level != "none"){
								return false;
							}
						},
						onSelect:function(node){
							_this.attr("data-value",node.id);
							_this.parents(".tree-box").find(".input-tree").val(node.id);
							_return_text.text(node.text);
							_tree_box.hide();
						},
						onLoadSuccess: function(node,data){
							if(selectedNode && selectedNode!=""){
								_this.tree("select",_this.tree("find",selectedNode).target);
							}
						}
					});
				},
				error: function(status){
					alert("分组加载失败！");
				}
			});
		});
		
		$("[data-type='tree']").each(function(index){
			var _this = $(this);
			var treeUrl = _this.attr("data-url");
			var treeValue = _this.attr("data-value");
			var searchCode = _this.attr("data-searchCode");
			var treeBox = _this.parents(".tree-box");
			_this.tree({
				url: treeUrl,
				method: "get",
				checkbox:function(node){
					if(searchCode == "resGroup"){
						if(node.level == 1){//0：分类    1：分组
							return true;
						}
					}else{
						return true;
					}
				},
				onLoadSuccess:function(node,data){
					_this.tree("collapseAll");
					for(var i=0;i<data.length;i++){
						_this.tree("expand",data[i].target);
						addTreeTitle(_this.tree("find",data[i].id));//获取节点调用给节点添加title的函数
					}

					if(treeValue && treeValue!=""){
						var treeValueArr = treeValue.split(',');
						$.each(treeValueArr,function(index,obj){
							var treeNode = _this.tree("find",obj);
							if(treeNode != null){
								_this.tree("check",treeNode.target).tree("expandTo",treeNode.target);
								addTreeNode([treeNode.id],[treeNode.text],treeBox.parents(".tree-checked").find(".treeNode-box"));
								removeTreeNodeEvent();
							}else{
								addTreeNode([treeValueArr[index]],[treeValueArr[index]],treeBox.parents(".tree-checked").find(".treeNode-box"));
								removeTreeNodeEvent();
							}
						});
					}

					$(".ownerDiv>label").on("click",function(e){
						$("input[name=tree-search]").val("");
						$("input[name=tree-search]").trigger("input");
						var _this_label = $(this);
						var index = _this_label.index();
						var shiroUser_account = $("#shiroUser_account").val();
						var treeNode = _this.tree("find",shiroUser_account);
						if(index == 0){
							var roots = _this.tree("getRoots");
							for(var i=0;i<roots.length;i++){
								_this.tree("expandAll").tree("check",roots[i].target);
							}
							_this_label.find("input[name='ownerType']").attr("checked","checked");
						}else if(index == 1){
							var nodes = _this.tree('getChecked', 'checked');
							$.each(nodes,function(index,obj){
								_this.tree("uncheck",obj.target);
							});
							if(treeNode != null){
								_this.tree("check",treeNode.target).tree("expandTo",treeNode.target);
							}else {
								_this_label.parents(".tree-box").find(".input-tree").val(shiroUser_account);
							}
							_this_label.find("input[name='ownerType']").attr("checked","checked");
						}
					});

					treeBox.find(".btn-submit").on("click",function(e){
						var nodes = _this.tree('getChecked', 'checked');
						var accs = "";
						var names = "";
						$.each(nodes,function(index,obj){
							//todo
							if(obj.attributes){
								var type = obj.attributes.type;
								if(type == "M"){
//								if(_this.tree("isLeaf",obj.target)){
									accs+=obj.id+",";
									names+=obj.text+",";
								}
							}else{
//							if(_this.tree("isLeaf",obj.target)){
								accs+=obj.id+",";
								names+=obj.text+",";
//							}
							}
						});
						if(accs != ""){
							accs=accs.replace(/,$/g,"");
							names=names.replace(/,$/g,"");
						}

						var treeNode_box = treeBox.parents(".tree-checked").find(".treeNode-box");
						treeNode_box.html("");

						/**
						 *查看自己 modify by 2017-07-20
						 *组织架构树中没有自己所在部门的权限
						 *查看自己又需要查找所有者为自己账号的数据
						 */
						if($(".ownerDiv") && $(".ownerDiv input[name='ownerType']:checked").val() == 1 && accs == ""){
							var self_acc = _this.parents(".tree-box").find(".input-tree").val(accs);
							accs += self_acc;
							names += self_acc;
						}else if($(".ownerDiv") && $(".ownerDiv input[name='ownerType']:checked").val() == 0){
							var shiroUser_account = $("#shiroUser_account").val();
							//查看自己当前是否在树下
							if(!_this.tree("find",shiroUser_account)){
								names = names + "," + shiroUser_account;
								accs = accs + "," + shiroUser_account;
							}
							_this.parents(".tree-box").find(".input-tree").val(accs);
						}else{
							_this.parents(".tree-box").find(".input-tree").val(accs);
						}

						_this.attr("data-value",accs);
						_this.attr("data-names",names);
						if(accs != ""){
							addTreeNode(accs.split(","),names.split(","),treeNode_box);
							removeTreeNodeEvent();
						}
						treeBox.hide();
					});
					treeBox.find(".btn-clean").on("click",function(e){
						var nodes = _this.tree('getChecked', 'checked');
						$.each(nodes,function(index,obj){
							_this.tree("uncheck",obj.target);
						});
					});
				},
				onCheck:function(node,isCheck){
					if($(".ownerDiv")){
//						var nodes = _this.tree('getChecked');
//						if(nodes.length > 0){
							$(".ownerDiv input[name=ownerType]:checked").attr("checked",false);
//						}
					}
				}
			});
		});
		

		$(".sure-cancel #btn-submit").on("click",function(e){
			e.stopPropagation();
			var _this_btn = $(this);
			var _param = $("form.submit-form").serialize();
			var exportType = $("#exportType").val();
			if(_this_btn.attr("data-step") == 1){
				var _display_text = "";
				$("[data-datatype]").each(function(index){
					var _this = $(this);
					var dataType = _this.attr("data-datatype");
					if(dataType == 2){
						var checkedValue = _this.find("input:checked").val();
						var dateShow = _this.find(".data-show-area").text();
						data[index].displayContent = dateShow;
						if(dateShow != ""){
							_display_text += "<span class='display-text'>"+data[index].searchName + "：" + dateShow + "</span>";
							dateShow = dateShow.split("/");
						}else{
							dateShow = ["",""];
						}
						_this.find(".startDate").val(dateShow[0]);
						_this.find(".endDate").val(dateShow[1]);
						var childList = data[index].childList;
						data[index].value = checkedValue;
						for(var j=0;j<childList.length;j++){
							childList[j].value = dateShow[j];
						}
					}else if(dataType == 5){
						data[index].value = _this.find("[data-type='tree']").attr("data-value");
						if(_this.find(".ownerDiv")){
							data[index].defined1 = _this.find(".ownerDiv input[name='ownerType']:checked").val();
						}
						if(data[index].value != ""){
							_display_text += "<span class='display-text'>"+data[index].searchName + "：" + _this.find(".tree").data("names") + "</span>";
						}
					}else if(dataType == 6){
						var query_block = _this.parents(".advanced-query-block");
						var pid = query_block.find("input[name=provinceId]").val();
						var cid = query_block.find("input[name=cityId]").val();
						var oid = query_block.find("input[name=countyId]").val();
						var _text = query_block.find(".data-show-area").text();
						var area = [pid,cid,oid];
						data[index].value = area.join();
						data[index].displayContent = _text;
						if(_text != ""){
							_display_text += "<span class='display-text'>"+data[index].searchName + "：" + _text + "</span>";
						}
					}else if(dataType == 7){
						var query_block = _this.parents(".advanced-query-block");
						var _text = query_block.find(".data-show-area").text();
						data[index].displayContent = _text;
						data[index].value = _this.find("[data-type='resource-tree']").attr("data-value");
						if(_text != ""){
							_display_text += "<span class='display-text'>"+data[index].searchName + "：" + _text + "</span>";
						}
					}else if(dataType == 8){
						var query_block = _this.parents(".advanced-query-block");
						var timeArr = [];
						var startTime = _this.find(".start-time").val();
						var endTime = _this.find(".end-time").val();
						if(startTime == "" && endTime == ""){
							data[index].value = "";
							$("input[name="+data[index].searchCode+"]").val("");
						}else{
							timeArr.push(startTime);
							timeArr.push(endTime);
							data[index].value = timeArr.join("_");
							$("input[name="+data[index].searchCode+"]").val(timeArr.join("_"));
							_display_text += "<span class='display-text'>"+data[index].searchName + "：" + timeArr.join("-") + "</span>";
						}
					}else{
						_this.find("input").each(function(i){
							var _this_input = $(this);
							_this_input.attr("checked")?(data[index].childList)[i].isCheck=1:(data[index].childList)[i].isCheck=0;
							if(_this_input.attr("checked") && _this_input.parent().text()!="" && dataType!=4){
								_display_text += "<span class='display-text'>"+data[index].searchName + "：" + _this_input.parent().text() + "</span>";
							}
						});
						if(dataType == 4){
							var checked_checkbox = $("input[name="+data[index].searchCode+"]:checked");
							var display_names = [];
							for(var i=0;i<checked_checkbox.length;i++){
								display_names.push(checked_checkbox.eq(i).parent().text());
							}
							if(display_names.join() != ""){
								_display_text += "<span class='display-text'>"+data[index].searchName + "：" + display_names.join() + "</span>";
							}
						}
					}
				});
				$(".advanced-conditions .content").html(_display_text);
				$("#exportSearchContext").val(_display_text);
			}else if(_this_btn.attr("data-step") == 2){
				var checked_columns = $("input[name=exportColumns]:checked");
				if(checked_columns.length == 0){
					alert("你特么的至少选一个啊！");
					return false;
				}
				var step2_url = ctx+"/fileupload/exportCount?"+_param;
				if(module == "call_record"){
					step2_url = ctx+"/fileupload/callExportCount?"+_param;
				}
				$.ajax({
					type:"post",
					url:step2_url,
					async:true,
					data: {
						"exportType":exportType
					},
					success: function(data){
						$(".total-count").text(data.totalCount);
					},
					error: function(error){}
				});
			}else if(_this_btn.attr("data-step") == 3 && _this_btn.text() == "确定导出"){
				var checkCode = $(".check-code").val();
				if(checkCode == ""){
					window.top.iDialogMsg("提示","验证码为空！");
					return false;
				}
				
				var exportColumns = [];
				var checked_columns = $("input[name=exportColumns]:checked");
				for(var i=0;i<checked_columns.length;i++){
					exportColumns.push(checked_columns.eq(i).val());
				}
				var exportSearchContext = $("#exportSearchContext").val();
				var step3_url = ctx+"/fileupload/exportData?"+_param;
				if(module == "call_record"){
					step3_url = ctx+"/fileupload/callExportData?"+_param;
				}
				$.ajax({
					type:"post",
					url:step3_url,
					async:true,
					data: {
						"exportType":exportType,
						"exportColumnStr":exportColumns.join(),
						"exportSearchContext":exportSearchContext,
						"code":checkCode
					},
					success: function(data){
						/**下载链接  /fileupload/log/download?exportId=...*/
						if(data.success){
							var exportId = data.exportId;
							$("form.download-form").attr("action",ctx+"/fileupload/log/download?exportId="+exportId);
							$("form.download-form").submit();
							closeParentPubDivDialogIframe("alert_data_output");
						}else{
							window.top.iDialogMsg("提示",data.msg);
						}
					},
					error: function(error){}
				});
			}
			
//			console.log(_display_text);
//			var _param = $("form").serialize();
/*			var extra_param = $("#superQuery",window.parent.document).data("param");
			if(extra_param && extra_param!=""){
				_param += extra_param;
			}

			saveSelectedValues(data,module);
			closeParentPubDivDialogIframe("alert_high_search_e");*/
			var _step = parseInt($(".sure-cancel .btn-prev-step").attr("data-step"));
			if(_step != 3){
				$(".sure-cancel .btn-prev-step").attr("data-step",_step+1);
				_this_btn.attr("data-step",_step+1);
				$("div[data-show-step]").hide();
				$("div[data-show-step='"+(_step+1)+"']").show();
			}
			if(_this_btn.attr("data-step") == 3){
				_this_btn.text("确定导出");
//				_this_btn.attr("disabled",true);
			}else{
				_this_btn.text("下一步");
//				_this_btn.attr("disabled",false);
			}
		});
		
		$(".sure-cancel .btn-prev-step").on("click",function(e){
			var _this = $(this);
			var _step = parseInt(_this.attr("data-step"));
			_this.attr("data-step",_step-1);
			$(".sure-cancel #btn-submit").attr("data-step",_step-1);
			$("div[data-show-step]").hide();
			$("div[data-show-step='"+(_step-1)+"']").show();
			if(_this.attr("data-step") != 3){
				$(".sure-cancel #btn-submit").text("下一步");
				$(".sure-cancel #btn-submit").attr("disabled",false);
			}
		});

		$(".tree-box").click(function(e){
			e.stopPropagation();
		});
	}

	$(document).click(function(e){
		$(".tree-box").hide();
	});

	function addTreeNode(accounts,names,insertObject){
		var appendCode = '';
		for(var i=0;i<accounts.length;i++){
			appendCode += '<span class="advancedQuery-tagincont active" data-id="'+accounts[i]+'"  title="'+names[i]+'">'+names[i]+'<span class="icon-close fa fa-close"></span></span>';
		}
		insertObject.append(appendCode);
	}

	function removeTreeNodeEvent(){
		$(".tree-checked .icon-close").on("click",function(e){
			e.stopPropagation();
			var _this = $(this);
			var _this_treeNode = _this.parents(".advancedQuery-tagincont");
			var reomve_value = _this_treeNode.attr("data-id");
			_this_treeNode.hide();
			var tree = _this_treeNode.parents(".tree-checked").find("[data-type='tree']");
			var tree_value = tree.attr("data-value").split(",");
			tree_value.remove(reomve_value);
			tree.attr("data-value",tree_value.toString());
			tree.parents(".tree-box").find(".input-tree").val(tree_value.toString());
			uncheckTreeNode(reomve_value,tree);
//			tree.parents(".tree-box").find(".btn-submit").click();
		});
	}

	function renderQueryContent(data) {
		// var myTemplate = Handlebars.compile($("#template").html());
		// $(".advancedQuery-box").html(myTemplate(data));
		var outerBox = $(".advancedQuery-box");
		outerBox.html("");
		for(var i=0;i<data.length;i++){
			var curData = data[i];
			var dataType = curData.dataType;
			var appendCode = '<div class="advanced-query-block">';
			appendCode += '<div class="advancedQuery-everytitle">'+curData.searchName+'：<span class="data-show-area"></span></div>';
			switch (dataType) {
				case 2:
					appendCode += '<div class="advancedQuery-everycontent date-checked single-checked" data-searchCode="'+curData.searchCode+'" data-dataType="'+curData.dataType+'">';
					var checkedValue = curData.value;
					var childList = curData.childList;
					var developCode = curData.developCode;
//					appendCode += '<span class="advancedQuery-tagincont date-no-time '+isCheck_class0+'" title="无联系时间">无联系时间<input type="radio" name="radio_'+curData.searchCode+'" value="0" '+isCheck_checked0+'/></span>';
					if(developCode == "contractExpireTime" || developCode == "nextFollowTime"){
						appendCode += '<span class="advancedQuery-tagincont dateRange-later" title="自定义">自定义<input type="radio" name="radio_'+curData.searchCode+'" value="5" /></span>';
					}else{
						appendCode += '<span class="advancedQuery-tagincont dateRange" title="自定义">自定义<input type="radio" name="radio_'+curData.searchCode+'" value="5" /></span>';
					}
					appendCode += '<span class="advancedQuery-tagincont date-today" title="当天">当天<input type="radio" name="radio_'+curData.searchCode+'" value="1" /></span>';
					appendCode += '<span class="advancedQuery-tagincont date-week" title="本周">本周<input type="radio" name="radio_'+curData.searchCode+'" value="2" /></span>';
					appendCode += '<span class="advancedQuery-tagincont date-month" title="本月">本月<input type="radio" name="radio_'+curData.searchCode+'" value="3" /></span>';
					if(developCode == "contractExpireTime" || developCode == "nextFollowTime"){
						appendCode += '<span class="advancedQuery-tagincont date-half-year-later" title="半年">半年<input type="radio" name="radio_'+curData.searchCode+'" value="4" /></span>';
					}else if($("#module").val() == "call_record"){
						appendCode += '';
					}else{
						appendCode += '<span class="advancedQuery-tagincont date-half-year-ago" title="半年">半年<input type="radio" name="radio_'+curData.searchCode+'" value="4" /></span>';
					}
					if(childList.length != 0 && childList[0].value != "" && childList[0].name != ""){
						appendCode += '<span class="advancedQuery-text data-show-area"></span>';
						appendCode += '<input type="hidden" name="'+childList[0].name+'" value="" class="startDate" />';
						appendCode += '<input type="hidden" name="'+childList[1].name+'" value="" class="endDate" />';
					}else if(childList.length != 0 && childList[0].name != ""){
						appendCode += '<span class="advancedQuery-text data-show-area"></span>';
						appendCode += '<input type="hidden" name="'+childList[0].name+'" value="" class="startDate" />';
						appendCode += '<input type="hidden" name="'+childList[1].name+'" value="" class="endDate" />';
					}else{
						appendCode += '<span class="advancedQuery-text data-show-area"></span>';
						appendCode += '<input type="hidden" name="startDate'+i+'" value="" class="startDate" />';
						appendCode += '<input type="hidden" name="endDate'+i+'" value="" class="endDate" />';
					}
					appendCode += '</div>';
					break;
				case 3:
					appendCode += '<div class="advancedQuery-everycontent single-checked" data-searchCode="'+curData.searchCode+'" data-dataType="'+curData.dataType+'">';
					var childList = curData.childList;
					for(var j=0;j<childList.length;j++){
						var curChildList = childList[j];
						//公海客户--放弃资源原因  需要传递的是文字，不是code值
						if(curData.searchCode == "reason"){
							appendCode += '<span class="advancedQuery-tagincont" title="'+curChildList.name+'">'+curChildList.name+'<input type="radio" name="'+curData.searchCode+'" value="'+curChildList.name+'" /></span>';
						}else{
							appendCode += '<span class="advancedQuery-tagincont" title="'+curChildList.name+'">'+curChildList.name+'<input type="radio" name="'+curData.searchCode+'" value="'+curChildList.value+'" /></span>';
						}
					}
					appendCode += '</div>';
					break;
				case 4:
					appendCode += '<div class="advancedQuery-everycontent multi-checked" data-searchCode="'+curData.searchCode+'" data-dataType="'+curData.dataType+'">';
					var childList = curData.childList;
					for(var j=0;j<childList.length;j++){
						var curChildList = childList[j];
						appendCode += '<span class="advancedQuery-tagincont" title="'+curChildList.name+'">'+curChildList.name+'<input type="checkbox" name="'+curData.searchCode+'" value="'+curChildList.value+'" /></span>';
					}
					appendCode += '</div>';
					break;
				case 5:
					appendCode += '<div class="advancedQuery-everycontent tree-checked" data-searchCode="'+curData.searchCode+'" data-dataType="'+curData.dataType+'">';
					appendCode += '<span class="advancedQuery-tagincont treeNode-checked" title="选择'+curData.searchName+'">选择'+curData.searchName+'</span>';
					appendCode += '<span class="treeNode-box"></span>';
					appendCode += '<div class="tree-box">';
					var defined1 = curData.defined1;
					var radio_checked_all = '';
					var radio_checked_self = '';
					var radio_checked_none = '';
					if(defined1 == "0"){
						radio_checked_all = 'checked="checked"';
					}else if(defined1 == "1"){
						radio_checked_self = 'checked="checked"';
					}else{
						radio_checked_none = 'checked="checked"';
					}
					if(curData.developCode == "ownerAcc"){
						appendCode = appendCode +
						'<div class="ownerDiv">'+
							'<label><input type="radio" name="ownerType" value="0" '+radio_checked_all+' />查看全部</label>'+
							'<label><input type="radio" name="ownerType" value="1" '+radio_checked_self+' />查看自己</label>'+
							'<label><input class="style-hidden" type="radio" name="ownerType" value="-1" '+radio_checked_none+' /></label>'+
						'</div>';
					}
					appendCode = appendCode +
						'<input type="hidden" class="input-tree" name="'+curData.searchCode+'" value="" />'+
						'<div class="tree-ul-box">'+
							'<ul data-type="tree" data-url="'+curData.paramValue+'" data-value="" data-searchCode="'+curData.searchCode+'"></ul>'+
						'</div>'+
						'<div class="button-area">'+
							'<button type="button" class="btn btn-submit">确定</button>'+
							'<button type="button" class="btn btn-default btn-clean">清空</button>'+
						'</div>'+
					'</div>';
					appendCode += '</div>';
					break;
				case 6:
					appendCode += '<input type="hidden" name="countyId" data-id="" data-name="" id="harea" value="">';
					appendCode += '<input type="hidden" name="cityId" data-id="" data-name="" id="hproper" value="">';
					appendCode += '<input type="hidden" name="provinceId" data-id="" data-name="" id="hcity" value="">';
					appendCode += 	
						'<div class="advancedQuery-everycontent city-checked" data-searchCode="'+curData.searchCode+'" data-dataType="'+curData.dataType+'">'+
							'<span class="advancedQuery-tagincont city-select-checked" title="选择'+curData.searchName+'">选择'+curData.searchName+'</span>'+
						'</div>';
					break;
				case 7:
					appendCode += 	
						'<div class="advancedQuery-everycontent res-group-checked" data-searchCode="'+curData.searchCode+'" data-dataType="'+curData.dataType+'">'+
							'<span class="advancedQuery-tagincont res-group-select-checked" title="选择'+curData.searchName+'">选择'+curData.searchName+'</span>'+
							'<div class="tree-box">'+
								'<input type="hidden" class="input-tree" name="'+curData.searchCode+'" value="" />'+
								'<ul data-type="resource-tree" data-url="'+curData.paramValue+'" data-value="" data-searchCode="'+curData.searchCode+'"></ul>'+
							'</div>'+
						'</div>';
					break;
				case 8:
					appendCode += 	
						'<div class="advancedQuery-everycontent time-range-input" data-searchCode="'+curData.searchCode+'" data-dataType="'+curData.dataType+'">'+
						'<input type="hidden" name="'+curData.searchCode+'" value="" />'+
							'<input type="text" class="time-input start-time" placeholder="秒" />'+
							'--'+
							'<input type="text" class="time-input end-time" placeholder="秒" />'+
						'</div>';
					break;
				default:
					var appendCode = "";
					break;
			}
			appendCode += '</div>';
			outerBox.append(appendCode);
			// if(curData.developCode == "ownerAcc"){
			// 	$(".tree-box").attr("data-developcode","ownerAcc");
			// 	$(".tree-ul-box").attr("data-type","search-tree");
			// 	$(".tree-ul-box").treeSearch();
			// }

		}
		var appendButton =
		'<div class="sure-cancel button-area">'+
			'<button type="button" class="btn btn-default btn-md btn-prev-step" data-step="1">上一步</button>'+
			'<button type="button" class="btn btn-submit btn-md btn-next-step" id="btn-submit" data-step="1">下一步</button>'+
		'</div>';
		$(appendButton).insertAfter(".advancedQuery-box");

	}

	function uncheckTreeNode(id,curTree){
		var node = curTree.tree("find",id);
		if(node != null){
			curTree.tree("uncheck",node.target);
		}
	}
});

function saveSelectedValues(data,module){
	$.ajax({
		url: ctx+"/highSearch/modify",
		type: "post",
		data: {
			"jsonData": JSON.stringify(data),
			"module": module
		},
		success: function(result){

		},
		error: function(result){

		}
	});
}
//给树节点添加title函数
function addTreeTitle(row){
	$(row).each(function(idx,val){
		$("#"+val.domId).attr('title',val.text);
		if(val.children){
			addTreeTitle(val.children);
		}
	});
}
