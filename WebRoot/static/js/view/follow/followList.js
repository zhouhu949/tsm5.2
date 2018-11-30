var userAccount
var serverDay
var adminSignAuth
var isSys
var isState
$(function(){
	$("span[sort=true]").click(function(){
		var $this = $(this);
		var sortColumn = $this.attr("sortColumn");
		var sortType = $this.attr("sortType");
		sortType = sortType || 'desc';
		$("span[sort=true]").each(function(index,obj){
			$(obj).find("span:eq(0)").attr("class","td_sort_asc");
			$(obj).attr("sortType","");
		});
		$this.find("span:eq(0)").attr("class","td_sort_"+sortType);
		$this.attr("sortType",sortType == 'desc' ? 'asc' : 'desc');
		$("#orderKey").val(sortColumn+" "+sortType);
		loadData();
	});
	isSys = $("#isSys").val();
	isState = $("#isState").val();
	adminSignAuth = $("#adminSignAuth").val();
	serverDay = $("#serverDay").val();
	userAccount = $("#userAccount").val();
	loadData();
/*	$("#query").click(function(){
		loadData();		
	});*/
	
    /*所有者*/	
    if(isSys == 1){
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
    	
    	var url =ctx+"/orgGroup/get_group_user_json";
    	$("#tt1").tree({
    		url:url,
    		checkbox:true,
    		onLoadSuccess:function(node,data){
    			var $accs = $('#accs').val();	
    			$("#tt1").tree("collapseAll");
				for(var i=0;i<data.length;i++){
					$("#tt1").tree("expand",data[i].target);
				}
				var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
				if(searchOwnerType == '1'){
					$("#accNames").val("查看全部");
				}else if(searchOwnerType == '2'){
					$("#accNames").val("查看自己");
				}else if($accs != null && $accs.length > 0){
    				var text='';
    				$($accs.split(',')).each(function(index,data){
    					var node = $("#tt1").tree("find",data);
    					if(node != null){
    						text+=node.text+',';
    						$("#tt1").tree("check",node.target);
    					}				
    				});
    				if(text != ''){
						text=text.substring(0,text.length-1);
						$("#accNames").val(text);
					}else{
						$("#accNames").val("所有者");
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
    }


	// 选择点保存
	$("#checkedId").click(function() {	
		var nodes =$("#tt1").tree("getChecked");
		var accs = "";
		var text='';
		$(nodes).each(function(index,obj){
			if(obj.attributes.type == 'M'){
				accs+=","+obj.id;
				text+=obj.text+',';
			}		
		});
		if(accs.length > 0){
			accs=accs.substring(1,accs.length);
			text=text.substring(0,text.length-1);			
			$("#osType").val("3");
		}else{
			var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
			if(searchOwnerType == '1'){
				text="查看全部";
			}else{
				text="查看自己";
			}
			$("#osType").val(searchOwnerType);
		}	
		$("#accs").val(accs);
		$("#accNames").val(text);	
	});
	//清空
	$('#clearId').click(function(){
		var nodes = $('#tt1').tree('getChecked');
		 $(nodes).each(function(index,obj){
        	$('#tt1').tree('uncheck', obj.target);
        });
        $('#accs').val('');
        $("#accNames").val('');
	});
	
//  开始联系时间
    $('#d3').dateRangePicker({
    	showShortcuts:false,
        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
    	var s_t = '';
    	var e_t = '';
    	if(obj.date1 != 'Invalid Date'){
    		$('#initStartFollowDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    		s_t = moment(obj.date1).format('YY.MM.DD');
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#initEndFollowDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    		e_t = moment(obj.date2).format('YY.MM.DD');
    	}
    	var s = $(this).parents('.select');
    	var dt=s.children("dt");
        var dd=s.children("dd");
        dt.html(s_t+'/'+e_t);
        $("#mDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
        //s.css("z-index",z);
    });
	
//  最近联系时间
    $('#d2').dateRangePicker({
    	showShortcuts:false,
        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
    	var s_t = '';
    	var e_t = '';
    	if(obj.date1 != 'Invalid Date'){
    		$('#lastStartActionDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    		s_t = moment(obj.date1).format('YY.MM.DD');
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#lastEndActionDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    		e_t = moment(obj.date2).format('YY.MM.DD');
    	}
    	var s = $(this).parents('.select');
    	var dt=s.children("dt");
        var dd=s.children("dd");
        dt.html(s_t+'/'+e_t);
        $("#dDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
        //s.css("z-index",z);
 });
 
 	// 下次联系时间
    $('#d1').dateRangePicker({
    	showShortcuts:false,
        format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
    	var s_t = '';
    	var e_t = '';
    	if(obj.date1 != 'Invalid Date'){
    		$('#nextStartActionDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    		s_t = moment(obj.date1).format('YY.MM.DD');
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#nextEndActionDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    		e_t = moment(obj.date2).format('YY.MM.DD');
    	}
    	var s = $(this).parents('.select');
    	var dt=s.children("dt");
        var dd=s.children("dd");
        dt.html(s_t+'/'+e_t);
        $("#nDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
       // s.css("z-index",z);
 });

    /*下拉框部分*/
    $('.dt_list').click(function(){
        $(this).find('.drop').fadeToggle();
    });
	
	$('#demoBtn_a').click(function(){
		setIds();
		var custIds = $("#custIds").val();
		var followIds = $("#followIds").val();
		
		if(custIds != null && custIds !="" && followIds != null && followIds != ""){
			pubDivShowIframe('alert_cust_follow_a',ctx+'/view/follow/idialog/custFollw_next_actionDate.jsp','跟进时间调整',450,330);
		}else{//如果未选中客户
			window.parent.iDialogMsg("提示","请先选择客户");
		}	
	});

	// 客户卡片点击的时候
	$("table").on('click',"[name='add_custInfo']", function(){	
		var custId = $(this).attr("custId");
		var custName = $.trim($(this).attr("custName"));
		var custName1 = custName||'客户卡片';
		window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName1);
	});
});

/** 点击左侧列表 触发事件 */
function leftListClick(){  
    $('.hyx-fur-tr').each(function(t,item_t){
    	$(item_t).click(function(){
    		loadRecordRight($(this).attr('cust_id'),$(this).attr('name'));
    	});    	
    });
    
}

function loadData(page){

    var _param = $("form").serialize();
	loaderAjax(_param,"page.showCount"+$("[name='showCount']").val());
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
	var _url=ctx+"/cust/custFollow/List/custFollowListJson";
	//var _param = $("form").serialize();
	searchingDataTip();
	var timeout = setTimeout(function(){
		$(".tip_search_text").hide();
		$(".tip_search_error").text("网络繁忙，请耐心等候或重新查询！");
		$(".tip_search_error").show();
	},10000);
	$.ajax({
		type:"get",
		url:_url,
        data:_param+"&"+pageStr,
		error:function(){
			$(".tip_search_text").hide();
			$(".tip_search_error").text("网络异常，请重新查询！");
			$(".tip_search_error").show();
		},
		success:function(data){
			if(data.status=="success"){
				loadTable(data.list,data.defList);
				$(".tip_search_data").hide();
				clearTimeout(timeout);
				pa.load("new_page",data.item.page,refreshPageData,$(".ajax-table"));
				if($("[data-authority]") && $("[data-authority]").length > 0){
					isAuthority("data-authority");
				}
			}else{
				clearTimeout(timeout);
				$(".tip_search_text").hide();
				$(".tip_search_error").text("网络异常，请重新查询！");
				$(".tip_search_error").show();
			}	
		}
	});
}

function loadTable(list,defList){
	var html='';
	for(var i in list){
		var follow = list[i];
		var resType = follow.type;
		var status = follow.status;
		var source = follow.source;
		var statusName = "";
		var custSource = "";
		if(source == 1) {
			custSource = "自有导入";
		}else if (source == 2) {
			custSource = "分配交接";
		}else if (source == 3) {
			custSource = "公海取回";
		}else if (source == 4) {
			custSource = "AI初筛";
		}else if (source == 5) {
			custSource = "在线表单";
		}else if (source == 6) {
			custSource = "数据合作";
		}
		if((status == 2 || status ==3) && resType ==2){
			statusName= "意向客户";
		}
		else if(status == 6 && resType ==2){
			statusName= "签约客户";
		}
		else if(status == 7 && resType ==2){
			statusName= "沉默客户";
		}
		else if(status == 8 && resType ==2){
			statusName= "流失客户";
		}
		if(i == 0){
			// 取第一条客户资源ID，加载右侧资源信息 
			html +="<input type='hidden' id='resCustId_' value='"+follow.resCustId+"'>"+
						 "<input type='hidden' id='followId_' value='"+follow.custFollowId+"'>";
		}		
		html +=
		"<tr class='hyx-fur-tr "+(i%2==0?'':'sty-bgcolor-b')+"' name='"+follow.custFollowId+"' cust_id='"+follow.resCustId+"'>"+
		"<td style='width:30px;'><div class='overflow_hidden w30 skin-minimal'><input type='checkbox' name='check_' follwId='"+follow.custFollowId+"'  custId='"+follow.resCustId+"'  id='chk_"+follow.resCustId+"'/></div></td>"+
		"<td style='width:130px;'><div class='overflow_hidden w130 link'>";
		if(serverDay >0){	
			html += "<a data-authority = 'base_followCust' href='javascript:;' onclick='custFollow(this)' custId='"+follow.resCustId+"' custType='6' class='icon-follow-up' title='客户跟进'></a>";
		}else{
			html += "<a data-authority = 'base_followCust' href='javascript:;'  class='icon-follow-up img-gray' title='客户跟进'></a>";
		}
		html += "<a href='javascript:;' class='icon-cust-card' name='add_custInfo' custId = '"+follow.resCustId+"' ";
			if(isState == 0 && emptyTransfer(follow.company) != ''){
				html += "custName='"+emptyTransfer(follow.company)+"'";
			}else{
				html += "custName='"+emptyTransfer(follow.custName)+"'";
			}
		html +="title='客户卡片'></a>";
		if(follow.isMajor==1){
			html += "<a href='###' class='icon-focus-atten' title='重点关注' custId = '"+follow.resCustId+"' isMajar='0' onclick='setIsMajor_(this)'></a>";
		}else{
			html += "<a href='###' class='icon-nofocus' title='非重点关注' custId = '"+follow.resCustId+"' isMajar='1' onclick='setIsMajor_(this)'></a>";
		}
		if(serverDay>0){
			if(follow.status != 6 && follow.status != 7 && follow.status != 8 && userAccount == follow.ownerAcc ){ // 非签约客户 可放弃客户
				html +='<a href="javascript:;" data-authority = "base_signCust" class="icon-sign-cont" onclick="custSign(\''+follow.resCustId+'\')" title="签约"></a>';
				html +='<a data-authority = "base_putIntoHighSeas" onclick="remove_(\''+follow.resCustId+'\')" href="javascript:;" class="icon-dele" title="放弃客户"></a>';							
			}else{
				if(follow.status == 6 || follow.status == 7 || follow.status == 8){
					html += "<a data-authority = 'base_signCust' href='javascript:;' class='icon-sign-cont img-gray'></a>"+
				                  " <a data-authority = 'base_putIntoHighSeas' href='###'class='icon-dele img-gray'></a>";
				}else{
					if(userAccount != follow.ownerAcc){
						if(adminSignAuth == 1){
								html += '<a data-authority = "base_signCust" href="javascript:;" class="icon-sign-cont" onclick="custSign(\''+follow.resCustId+'\')" title="签约"></a>';							
						}else{
							html += "<a data-authority = 'base_signCust' href='javascript:;' class='icon-sign-cont img-gray'></a>";
						}
						html += "<a data-authority = 'base_putIntoHighSeas' href='javascript:;' class='icon-dele img-gray' ></a>";
					}					
				}
			}			
		}else{
			html += "<a  data-authority = 'base_signCust' href='javascript:;' class='icon-sign-cont img-gray'></a>"+
		                 "<a data-authority = 'base_putIntoHighSeas' href='javascript:;' class='icon-dele img-gray' ></a>";
		}
		html +="</div></td>";
		html += "<td><div class='overflow_hidden w120' title='"+follow.showNextActionDate+"'>"+follow.showNextActionDate+"</div></td>";
		if(isState == 0){
			html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(follow.company)+"'>"+emptyTransfer(follow.company)+"</div></td>";
		}else{
			html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(follow.custName)+"'>"+emptyTransfer(follow.custName)+"</div></td>";
		}	
		if(isState == 0){
			html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(follow.custName)+"'>"+emptyTransfer(follow.custName)+"</div></td>";
		}else{
			html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(follow.linkName)+"'>"+emptyTransfer(follow.linkName)+"</div></td>";
		}
		html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(follow.custTypeName)+"'>"+emptyTransfer(follow.custTypeName)+"</div></td>";
		
		html +="<td><div class='overflow_hidden w100' title='"+emptyTransfer(follow.optionName)+"'>"+emptyTransfer(follow.optionName)+"</div></td>"+
		"<td><div class='overflow_hidden w120' title='"+follow.showLastActionDate+"'>"+follow.showLastActionDate+"</div></td>";
		html += "<td><div class='overflow_hidden w120' title='"+statusName+"'>"+statusName+"</div></td>";
		html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(follow.groupName)+"'>"+emptyTransfer(follow.groupName)+"</div></td>";
		html += "<td><div class='overflow_hidden w120' title='"+follow.showMinActionDate+"'>"+follow.showMinActionDate+"</div></td>";
		html += "<td><div class='overflow_hidden w50' title='"+custSource+"'>"+custSource+"</div></td>";
		html += "<td><div class='overflow_hidden w50' title='"+emptyTransfer(follow.ownerName)+"'>"+emptyTransfer(follow.ownerName)+"</div></td>";		
		html += "<td><div class='overflow_hidden w120' phone='tel' title='"+emptyTransfer(follow.custMobilephone)+"'>"+emptyTransfer(follow.custMobilephone)+"</div></td>";
		html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(follow.area)+"'>"+emptyTransfer(follow.area)+"</div></td>";
		html += "<td><div class='overflow_hidden w120' title='"+emptyTransfer(follow.companyTrade)+"'>"+emptyTransfer(follow.companyTrade)+"</div></td>";
		html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(follow.defined1)+"'>"+emptyTransfer(follow.defined1)+"</div></td>";
		html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(follow.defined2)+"'>"+emptyTransfer(follow.defined2)+"</div></td>";
		html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(follow.defined3)+"'>"+emptyTransfer(follow.defined3)+"</div></td>";
		html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(follow.defined4)+"'>"+emptyTransfer(follow.defined4)+"</div></td>";
		html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(follow.defined5)+"'>"+emptyTransfer(follow.defined5)+"</div></td>";
		html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(follow.defined6)+"'>"+emptyTransfer(follow.defined6)+"</div></td>";
		html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(follow.defined7)+"'>"+emptyTransfer(follow.defined7)+"</div></td>";
		html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(follow.defined8)+"'>"+emptyTransfer(follow.defined8)+"</div></td>";
		html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(follow.defined9)+"'>"+emptyTransfer(follow.defined9)+"</div></td>";
		html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(follow.defined10)+"'>"+emptyTransfer(follow.defined10)+"</div></td>";
		html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(follow.defined11)+"'>"+emptyTransfer(follow.defined11)+"</div></td>";
		html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(follow.defined12)+"'>"+emptyTransfer(follow.defined12)+"</div></td>";
		html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(follow.defined13)+"'>"+emptyTransfer(follow.defined13)+"</div></td>";
		html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(follow.defined14)+"'>"+emptyTransfer(follow.defined14)+"</div></td>";
		html += "<td><div class='overflow_hidden w80' title='"+emptyTransfer(follow.defined15)+"'>"+emptyTransfer(follow.defined15)+"</div></td>";
		html += "<td><div class='overflow_hidden w100' title='"+emptyTransfer(follow.showdefined16)+"'>"+emptyTransfer(follow.showdefined16)+"</div></td>";
		html += "<td><div class='overflow_hidden w100' title='"+emptyTransfer(follow.showdefined17)+"'>"+emptyTransfer(follow.showdefined17)+"</div></td>";
		html += "<td><div class='overflow_hidden w100' title='"+emptyTransfer(follow.showdefined18)+"'>"+emptyTransfer(follow.showdefined18)+"</div></td>";
		html +="</tr>";
	}

	$(".ajax-table>tbody").html(html);
	
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
	
    // 异步加载 右侧信息
  	loadRecordRight($("#resCustId_").val(),$("#followId_").val());
	leftListClick();
	//tableDataInLater();//表格数据填入后点击和hover的效果
	 /* 表格默认选中第一条 */
    default_tr($('.com-table').find('tbody').find('tr:first'));
	$('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });

}


//点击 数据分类
function iCheck(state){
	var $state = $("#followCustCation");
	$state.val(state);
	$("[id^=i_]").removeClass("e-hover");
	$("#i_"+$state.val()).addClass("e-hover");
	loadData();
}

var setDdate = function(i){
	$('#lastStartActionDate').val('');
	$('#lastEndActionDate').val('');
	$("#dDateType").val(i);
}	

var setMdate = function(i){
	$('#initStartFollowDate').val('');
	$('#initEndFollowDate').val('');
	$("#mDateType").val(i);
}	

var setNdate = function(i){
	$('#nextStartActionDate').val('');
	$('#nextEndActionDate').val('');
	$("#nDateType").val(i);
}	

/** 异步加载 右侧列表信息 */
function loadRecordRight(reCustId,followId){
	 $.ajax({
			url: ctx+'/cust/custFollow/List/followListRight.do',
			type:'get',
			data:{'reCustId':reCustId,'followId':followId,'showCount':'0'},
			dataType:'html',
			error:function(){alert("网络异常，请稍后再操作！")},
			success:function(data){
				$("#list_right").html(data);
			}
		});
}

/** 客户跟进  */
function custFollow(obj){
	var custId = $(obj).attr("custId");
	var custType = $(obj).attr("custType");
	var custCation = $("#followCustCation").val();
	var param = $("#myForm").serialize();
	window.top.addTab(ctx+"/cust/custFollow/custFollowPage?"+param+"&custId="+custId+"&custListType="+custType+"&custCation="+custCation+"&v="+new Date().getTime(),"跟进");
}

/** 放弃客户 */
function remove_(resCustId){
		if(resCustId != null && resCustId != ""){
			queDivDialog("res_remove_cust","是否放弃客户？",function(){
				$.ajax({
					url:ctx+'/res/cust/removeCust',
					type:'post',
					data:{ids:resCustId+'_2'},
					dataType:'json',
					error:function(){alert('网络异常，请稍后再试！')},
					success:function(data){
						if(data == 1){
							window.top.iDialogMsg("提示",'放弃客户成功！');
							refreshPageData(pa.pageParameter());
						}else{
							window.top.iDialogMsg("提示",'放弃客户失败！');
						}
					}
				});
		});
	}
}

/**
* 设置重点关注
* @param custId 资源ID
* @param isMajar 是否是关注，0:否，1:是
*/
function setIsMajor_(obj){	
	var custId =  $(obj).attr("custId");
	var isMajar =  $(obj).attr("isMajar");
	$.ajax({
		url:ctx+'/res/tao/setMajor',
		type:'post',
		data:{custId:custId,isMajar:isMajar},
		success:function(data){
			if(data == 0){
				refreshPageData(pa.pageParameter());
			}else{
				window.top.iDialogMsg("提示",'设置失败！');
			}
		}
	});
}


/** 客户签约 */
function custSign(custId){
	var signSetting = $("#signSetting").val();
	if(signSetting == '1' || !window.top.shrioAuthObj("mySignCust_addContract")){ // 读取签约是否与合同无关 0-需添加合同 1-无需添加合同
		pubDivDialog("confirm_sign","是否确认签约？",function(){
			$.ajax({
				url:ctx+"/contract/sign",
				type:"post",
				data:{custId:custId, module:3},
				dataType:"json",
				error:function(){},
				success:function(data){
					if(data == "1"){
						window.top.iDialogMsg("提示","签约成功!");
						refreshPageData(pa.pageParameter());
					}else{
						window.top.iDialogMsg("提示","签约失败!");
					}
				}
			});
		});
	}else{
		window.top.addTab(ctx+"/contract/toAdd?module=3&custId="+custId+"&t="+Date.parse(new Date()),"新增合同");	
	}
}

//表格默认选中第一条
function default_tr(tab){
  tab.find('td').removeClass('td-link');
  tab.find('td').addClass('td-hover');
  tab.find('td:first').css({'border-left-color':'#469bff'});
  tab.find('td:last').css({'border-right-color':'#469bff'});
}

//获取选中列表复选的ID 集合
function setIds(){
	var custIds = "";
	var followIds = "";
	$("input[name='check_']:checked").each(function(){
		custIds += $(this).attr("custId") + ",";
		followIds += $(this).attr("follwId") + ",";
	});	
	$("#custIds").val(custIds);
	$("#followIds").val(followIds);
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
