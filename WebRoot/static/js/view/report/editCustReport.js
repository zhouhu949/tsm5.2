$(function() {
	stepBar.init("stepBar", {
		step : 1,
		change : true,
		animation : true
	});
	var  firsts = $(".new-custreport-step-first");
	var seconds = $(".new-custreport-step-second");
	var thirds = $(".new-custreport-step-third");
	//一页下一步
	firsts.find(".next-tips").click(function(e){
		var conditions = $(".screening-conditions");
		if(conditions.length <= 0){
			window.top.iDialogMsg("提示","必须选择至少一个查询条件 ！")
			return false;
		}else{
			var flag = true;
			conditions.each(function(i,obj){
				var $this = $(obj);
				var reminder = $this.find(".reminder");
				var thirdDiv = $this.find(".step-choose-box");
				if($this.find(".choose-data-filter-select>option:selected").data("type") == "please-choose"){
					reminder.text("请选择条件值！");
					flag = false;
					return ;
				}else{
					if(thirdDiv.find("input,select").val() == "" || thirdDiv.find("input,select").val() == null){
						reminder.text("条件值不能为空！");
						flag = false;
						return ;
					}else{
						reminder.text("");
					}
				}
			})
			if(flag){
				var step = stepBar;
				step.triggerStep = 2 ;
				step.percent();
				firsts.hide();
				seconds.show();
				thirds.hide();
			}
		}
	});
	//二页上一步
	seconds.find(".prev-tips").click(function(e){
		e.stopPropagation();
		var step = stepBar;
		step.triggerStep = 1 ;
		step.percent();
		firsts.show();
		seconds.hide();
		thirds.hide();
	});
	//二页下一步
	seconds.find(".next-tips").click(function(e){
		e.stopPropagation();
		var $this = $(this);
		var selects = $this.parents(".new-custreport-step-second").find("select:visible");
		var flag = true ;
		var tips = ""
		selects.each(function(i,obj){
			var $this = $(obj)
			if($this.find("option:selected").val() == ""){
				var types = $this.find("option:selected").data("type");
				tips = "请选择"+types;
				flag = false ;
				return false;
			}
		})
		if(!flag){
			window.top.iDialogMsg("提示",tips);
			return false;
		}
		var step = stepBar;
		step.triggerStep = 3;
		step.percent();
		firsts.hide();
		seconds.hide();
		thirds.show();
	});
	//三页上一步
	thirds.find(".prev-tips").click(function(e){
		e.stopPropagation();
		var step = stepBar;
		step.triggerStep = 2 ;
		step.percent();
		firsts.hide();
		seconds.show();
		thirds.hide();
	});
	//三页完成
	thirds.find(".btn-finished").click(function(e){
		e.stopPropagation();
		var reportName = $.trim($(".finish-named-input").val());
		var partten = /(\%|\^|\&|\#|\$|\￥\@)/;
		if(reportName == "" || reportName == null){
			window.top.iDialogMsg("提示","图表名称不能为空！")
			return false;
		}
		if(partten.test(reportName)){
			window.top.iDialogMsg("提示","图表名称不能包含特殊符号！")
			return false;
		}
		if(reportName.length > 10 ){
			window.top.iDialogMsg("提示","图表名称不能超过10个字！")
			return false;
		}
		$.ajax({
			url:ctx+"/custom/report/judgeIsHaveSameName",
			data:{
				customReportName:reportName,
				oldCustomReportName:$("#oldName").val()
			},
			success:function(data){
				if(data.status){
					$("#addReportForm").ajaxSubmit({
						url:ctx+"/custom/report/editCustomReport",
						success:function(data){
							if(data.status){
								window.top.iDialogMsg("提示","操作成功！")
								setTimeout(function(){
									window.parent.loadMyData();
									closeParentPubDivDialogIframe("add_reports");
								},500);
							}else{
								iDialogMsg("提示",data.errorMsg)
							}
						}
					})
				}else{
					iDialogMsg("提示",data.errorMsg)
				}
			}
		})
		
	});
	//类型避免重复
	$("body").delegate(".chooseType","change",function(){
		var $this = $(this);
		var indexs = $this.find("option:selected").index();
		var anotherSelect = $this.parents(".step-top-operation-box-item").find("select").not($this);
		anotherSelect.find("option").show();
		anotherSelect.each(function(i,obj){
			var _this = $(obj);
			_this.find("option").eq(indexs).hide();
		})
	})
	//添加更多筛选条件
	$("body").delegate(".add-more-condition","click",function(e){
		e.stopPropagation();
		var $this = $(this);
		var num = Number($this.data("num"));
		$this.data("num",num+1);
		var styleStr = "<style id='aa"+num+"'></style>";
		var data={num:num};
		$("head").append(styleStr);
		var template = Handlebars.compile($("#templateItem").html());
		$(".step-top-operation-box-addbox").append(template(data));
	})
	$("body").delegate(".delete-icons","click",function(e){
		//e.stopPropagation();
		var $this = $(this);
		var boxItem = $this.parents(".step-top-operation-box-item");
		var styleId = boxItem.find(".choose-data-filter-select").data("select-id");
		$("#"+styleId).remove();
		boxItem.remove();
	})
	//选择列表形式
	$(".radio-change-type").change(function(){
		var $this = $(this);
		var type = $this.data("type");
		var $another = $(".radio-change-type").not($this);
		var anotherType = $another.data("type");
		var nowshow = $("."+type+"-show");
		var nowhide = $("."+anotherType+"-show");
		nowshow.show();
		nowshow.find("select").removeAttr("disabled");
		nowhide.hide();
		nowhide.find("select").attr("disabled",true);
	})
	//事件委托选择条件改变时
	$("body").delegate(".choose-data-filter-select","change",function(){
		var $this = $(this);
		var styleId = $this.data("select-id");
		var zindex = 20 - styleId.replace("aa","");
		var div = $this.parent().find(".step-choose-box");
		var selected = $this.find("option:selected")
		var index = Number(selected.index())+1;
		var type = selected.data("type");
		var condition = selected.data("condition");
		var cssStr = ".choose-data-filter-select>option:nth-of-type("+index+"){display:none;}";
		cssStr += ".screening-"+styleId+"{z-index:"+zindex+";}";
		$("#"+styleId).html(cssStr);
		if(type != "please-choose"){
			changeType(type,selected,div);
			changeCondition($this,condition);
		}
	})
	//事件委托地区选择
	$("body").delegate(".area","click",function(e){
		e.stopPropagation();
		var $this = $(this);
		SelCity(this,e,function(data){
			data==""?$this.val(""):$this.val(data);
		});
	})
	//事件委托树唤醒
	$("body").delegate(".tree","click",function(e){
		e.stopPropagation();
		var $this = $(this);
		$this.parents(".step-choose-box").find(".manage-drops").show();
	})
	//树点击相关
	$("body").delegate(".manage-drops","click",function(e){
		e.stopPropagation();
		$(this).show();
	});
	$(document).click(function(){
		$(".manage-drops").hide();
	})
	$("body").delegate(".checkedId","click",function(e){
		e.stopPropagation();
		var manage = $(this).parents(".manage-drops");
		var nodes = manage.find(".tt1").tree("getChecked");
		var shares ="";
		var names ="";
		if(nodes != null){
			$(nodes).each(function(index,obj){
				console.log(obj)
				shares+=obj.id.substring(0,obj.id.length)+',';
				names+=obj.text.substring(0,obj.text.length)+';';
			});
		}
		manage.parents(".step-choose-box").find(".tree").val(names).attr("title",names);
        manage.find(".tree-hidden").val(shares);
        manage.hide();
	});
	$("body").delegate(".clearId","click",function(e){
		e.stopPropagation();
		var tree = $(this).parents(".manage-drops").find(".tt1");
		var nodes = tree.tree('getChecked');
		 $(nodes).each(function(index,obj){
			 tree.tree('uncheck', obj.target);
	       })
	})
 	// 选择时间
	$("body").delegate(".date","focus",function(){
		var that =$(this);
	    $(this).dateRangePicker({
	    	showShortcuts:false,
	        format: 'YYYY-MM-DD',
	        opens:"left"
		    }).bind('datepicker-apply',function(event,obj){
		    	var s_t = '';
		    	var e_t = '';
		    	if(obj.date1 != 'Invalid Date'){
		    		//$('#nextStartActionDate').val(moment(obj.date1).format('YYYY-MM-DD'));
		    		that.parents(".step-choose-box").find(".startTime").val(moment(obj.date1).format('YYYY-MM-DD'));
		    		s_t = moment(obj.date1).format('YYYY-MM-DD');
		    	}
		    	if(obj.date2 != 'Invalid Date'){
		    		//$('#nextEndActionDate').val(moment(obj.date2).format('YYYY-MM-DD'));
		    		that.parents(".step-choose-box").find(".endTime").val(moment(obj.date2).format('YYYY-MM-DD'));
		    		e_t = moment(obj.date2).format('YYYY-MM-DD');
		    	}
		    	that.val(s_t+"/"+e_t);
		 });
	})
	//初始化数据注入
	editDataInitialization(bean);
	//初始化系列类别已选择的项目隐藏
	$(".chooseType").change();
});
//edit data
function editDataInitialization(bean){
	//console.log(definArr)
	var addBtn = $(".add-more-condition");
	var currArr = [
	               {name:"custTypeId",match:"custTypeMatch",type:"single"},
	               {name:"qStatus",match:"qStatusMatch",type:"single"},
	               {name:"optionlistId",match:"optionlistMatch",type:"single"},
	               {name:"accs",match:"ownerAccMatch",type:"tree"},
	               {name:"resGroupId",match:"resGroupMatch",type:"tree"},
	               {name:"actionDateStart",type:"time"},
	               {name:"nextFollowDateStart",type:"time"},
	               {name:"amoytocustomerDateStart",type:"time"},
	               {name:"inputDateStart",type:"time"},
	               {name:"signDateStart",type:"time"},
	               {name:"companyTrade",match:"companyTradeMatch",type:"single"},
	               {name:"provinceId",match:"areaMatch",type:"area"},
	               {name:"ownerStartDateStart",type:"time"},
	               {name:"importDeptId",match:"importDeptMatch",type:"tree"},
	               {name:"source",match:"sourceMatch",type:"single"},
	               {name:"opreateType",match:"opreateTypeMatch",type:"single"}
	            ];
	var nameArr = currArr.concat(definArr);
	for(var i = 0 ; i < nameArr.length ; i++){
		var item = nameArr[i];
		var matchs = item.match;
		var names = item.name;
		if(bean[names]){
			var idsNum = addBtn.data("num");
			addBtn.click();
			var select = $("select[data-select-id='aa"+idsNum+"']");
			select.find("option[data-identification='"+names+"']").attr("selected","true");
			select.change();
			select.next().find("option[value='"+bean[matchs]+"']").attr("selected","true");
			editClassifiedTreatment(bean,item,select);
		}
	}
}
//编辑初始化分类型处理
function editClassifiedTreatment(bean,item,select){
	var type = item.type;
	var thirdEle = select.parent().find(".step-choose-box");
	if(type == "time"){
		var startName = item.name ;
		var endName = startName.replace(/(Start)$/,"End");
		console.log(startName)
		console.log(endName)
		thirdEle.find(".startTime").val(bean[startName]);
		thirdEle.find(".endTime").val(bean[endName]);
		thirdEle.find(".date").val(bean[startName]+"/"+bean[endName]);
	}else if(type == "tree"){
		var names = item.name;
		thirdEle.find("[name='"+names+"']").val(bean[names]);
		var oas = bean[names];
		var trees = thirdEle.find(".tt1");
		trees.tree({
			onLoadSuccess:function(){
				var text = "";
				if(oas != null && oas != ''){
					$.each(oas.split(','),function(index,obj){
						var n = trees.tree("find",obj);
						if(n != null){
							trees.tree("check",n.target).tree("expandTo",n.target);
							text += n.text+",";
						}
					});
				}
				thirdEle.find("input.tree").val(text);
			}
		});
	}else if(type == "area"){//地区的未处理
		var areaInput = thirdEle.find(".area");
		var nameVal = "";
		if(bean.provinceId){
			var hprovic = $('<input>', {
	             type: 'hidden',
	             name: "provinceId",
	             "data-id": bean.provinceId,
	             "data-name":bean.provinceName,
	             id: "hcity",
	             val: bean.provinceId
	         });
			 areaInput.after(hprovic);
			 nameVal += bean.provinceName;
		}
		if(bean.cityId){
			var hcitys = $('<input>', {
	            type: 'hidden',
	            name: "cityId",
	            "data-id": bean.cityId,
	            "data-name":bean.cityName,
	            id: "hproper",
	            val: bean.cityId
	        });
			areaInput.after(hcitys);
			nameVal += "-"+bean.cityName;
		}
		if(bean.countyId){
			var hcount = $('<input>', {
	             type: 'hidden',
	             name: "countyId",
	             "data-id": bean.countyId,
	             "data-name":bean.countyName,
	             id: "harea",
	             val: bean.countyId
	         });
			 areaInput.after(hcount);
			 nameVal += "-"+bean.countyName;
		}
		areaInput.val(nameVal);
	}else if(type == "multi" || type == "single"){//单选状态去除 全部为多选
		var names = item.name;
		var hiddes = "<input type='hidden' id='"+names+"' value='"+bean[names]+"'>";
		var lis = thirdEle.find(".select>dd li");
		thirdEle.after(hiddes)
		multiDataEdit(lis,bean[names]);
	}
}
//中间下拉框条件和name改变
function changeCondition(obj,condition){
	var betweenStr = "<option>介于</option>";
	var otherStr = "<option value='0'>等于</option><option value='1'>不等于</option>";
	var multiStr = "<option value='0'>等于</option>";
	var select = obj.parent().find(".condition-select");
	if(condition == "between"){//时间类型 条件为介于
		select.removeAttr("name");
		select.html(betweenStr);
	}else if(condition == "multi"){//多选条件为等于
		var condName = obj.find("option:selected").data("condition-name");
		select.attr("name",condName);
		select.html(multiStr);
	}else{//其他情况条件为等于和不等于
		select.attr("name",condition);
		select.html(otherStr);
	}
}
//改变后联动第三部分的type
function changeType(type,selected,div){
	if(type == "time"){//时间
		var start = selected.data("time-start");
		var end = selected.data("time-end");
		var data = {startTime:start,endTime:end};
		renderHandle("templateTime",data,div);
	}else if(type == "tree"){//树
		var url = selected.data("treeurl");
		var change = selected.data("change");
		var data = treeName(change)
		renderHandle("templateTree",data,div);
		div.find(".tt1").tree({
			url:url,
			checkbox:true
		})
	}else if(type == "area"){//地区联动
		renderHandle("templateArea",undefined,div);
	}else if(type == "select-multi-data"){
		var url = selected.data("ajaxurl");
		var name = selected.data("name");
		multiDataAjax(url,name,div)
	}else if(type == "select-fixed"){//写死的下拉
		var fixedName = selected.data("fixed-name");
		var data = fixedDataAssemble(fixedName)
		renderHandle("templateSelectFix",data,div);
		multiDropDownFn()
	}else if(type == "select-data"){//申请数据下拉
		var url = selected.data("ajaxurl");
		var name = selected.data("name");
		selectDataAjax(url,name,div);//异步口子直接到成功函数中调用渲染模版函数
	}
}
function multiDataAjax(url,name,div){
	$.ajax({
		url:url,
		success:function(data){
			for(var i = 0 , len = data.length ; i < len ; i++ ){
				if(data[i].fieldCode == name ){//匹配对应的数据
					var item = data[i];
					var json = {name:name,list:item.optionList};
					renderHandle("templateMultiData",json,div);
					if(div.next().attr("id") == name){
						var lis = div.find(".select>dd li");
						var selet = div.next().val();
						div.find("input[name='"+name+"']").val(selet)
						multiDataEdit(lis,selet);
					}
					multiDropDownFn()
				}
			}
		}
	})
}
function selectDataAjax(url,name,div){
	$.ajax({
		url:url,
		success:function(data){
				var json = {name:name,list:data};
				renderHandle("templateSelectData",json,div);
				if(div.next().attr("id") == name){
					var lis = div.find(".select>dd li");
					var selet = div.next().val();
					div.find("input[name='"+name+"']").val(selet)
					multiDataEdit(lis,selet);
				}
				multiDropDownFn()
		}
	})
}
function treeName(change){
	if(change == "user"){
		return {names:"accs"};
	}else if(change == "group"){
		return {names:"importDeptId"};
	}else if(change == "res"){
		return {names:"resGroupId"}
	}
}

//写死的选项返回数据
function fixedDataAssemble(name){
	if(name == "qStatus"){
		var status={fixedname:"qStatus",list:[{value:1,name:"待分配资源"},{value:2,name:"已分配资源"},{value:3,name:"意向客户"},{value:4,name:"签约客户"},{value:5,name:"流失客户"},{value:6,name:"沉默客户"},{value:7,name:"公海客户"}]};
		return status;
	}else if(name == "source"){
		var sour = {fixedname:"source",list:[{value:1,name:"自有导入"},{value:2,name:"分配交接"},{value:3,name:"公海取回"}]};
		return sour;
	}else if(name == "opreateType"){
		var opreate = {fixedname:"opreateType",list:[{value:11,name:"客户-到期未签约"},{value:16,name:"客户-到期未跟进"},{value:12,name:"客户-主动放弃"},{value:21,name:"签约客户-流失"},{value:23,name:"签约-到期未续签"},{value:4,name:"资源-沟通失败"},{value:5,name:"资源-信息错误"},{value:24,name:"资源-到期未联系"}]};
		return opreate;
	}
}

function multiDataEdit(lis,values){
	var slist = values.split(",");
	var html = "";
	lis.each(function(i,obj){
		var $this = $(obj);
		var as = $this.find("a");
		for(var i = 0 ; i < slist.length ; i++){
			if(slist[i] == as.data("value")){
				$this.addClass("selected");
				if( i < slist.length - 1 ){
					html += as.text()+",";
				}else{
					html += as.text();
				}
			}
		}
	})
	lis.parents(".select").find("dt").text(html).attr("title",html);
	lis.parents(".select").find("input[type='hidden']").val(values);
}

function renderHandle(target,data,div){
	var template = Handlebars.compile($("#"+target).html());
	div.html(template(data));
}
