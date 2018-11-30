/*******************************************************************************
 * 公司资源_专属JS文件
 * 
 * @author wuwei
 * @date 2013-10-27
 ******************************************************************************/
$(function() {
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


	/* 表单优化 */
	$('.skin-minimal input').iCheck({
		checkboxClass : 'icheckbox_minimal',
		radioClass : 'iradio_minimal',
		increaseArea : '20%'
	});

	$('#close02').click(function(){
		$('#queryStartRemain').val('');$('#queryEndRemain').val('');
		$(".manage-drop").hide();
		return false;
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


	$("body").click(function() {
		$(".sub-dep-inpu").click(function() {
			$(".manage-drop").show();
			return false;
		});
		$(".manage-drop").click(function() {
			$(".manage-drop").show();
			return false;
		});
		$(".reso-alloc-sure").click(function() {
			$(".manage-drop").hide();
			return false;
		});
		$(".owner-sour").click(function() {
			$(".manage-owner-sour").show();
			return false;
		});

		$(".manage-drop").hide();
		$(".manage-owner-sour").hide();
	});

	// 提交
	$('#queryBtn').click(function() {
		loadPage(2);
	});
	// 获取和失去焦点
	$("#qKeyWordId").focus(function() {
		$(this).next().text('');
	});
	$("#qKeyWordId").blur(function() {
		$(this).next().text('输入客户名称/电话号码/联系人');
	});

	//回收资源
	$('#resRecyleId').click(function() {
		var staffAccs = '';
		$('input[id^=chk_]').each(function(index, obj) {
			if ($(obj).is(':checked')) {
				staffAccs += $(obj).attr('id').split('_')[1] + ',';
			}
		});
		if (staffAccs.length == 0) {
			window.top.iDialogMsg("提示", '请先选择员工！');
			return;
		}
		staffAccs = staffAccs.substring(0, staffAccs.length - 1);
		window.parent.toRecyleResource(staffAccs);
	});	
	//分配资源
	$('#resAssignId').click(function() {
		var staffAccs = '';
		$('input[id^=chk_]').each(function(index, obj) {
			if ($(obj).is(':checked')) {
				staffAccs += $(obj).attr('id').substring(4) + ',';
			}
		});
		if (staffAccs.length == 0) {
			window.top.iDialogMsg("提示", '请先选择员工！');
			return;
		}
		staffAccs = staffAccs.substring(0, staffAccs.length - 1);
		window.parent.toassginResource(staffAccs);
	});
});	
	// 列表排序
function loadData(page){

    var _param = $("#myForm").serialize();
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

	var _url=ctx+"/res/staffMg/getStaffResMgJson";
	//var _param = $("#myForm").serialize();
	searchingDataTip();
	var timeout = setTimeout(function(){
		$(".tip_search_text").hide();
		$(".tip_search_error").text("网络繁忙，请耐心等候或重新查询！");
		$(".tip_search_error").show();
	},10000);
	$.ajax({
		type:"get",
		url	:_url,
        data:_param+"&"+pageStr,
		success	:	function(data){
			loadTable(data.list);
			$(".tip_search_data").hide();
			clearTimeout(timeout);
			pa.load("new_page",data.item.page,refreshPageData,$(".ajax-table"));
			var heights = $(".hyx-spsa").height()+30;
			window.parent.$("#iframepage").css({"height":heights+"px"});
		},
		error		:	function(data){
			clearTimeout(timeout);
			$(".tip_search_text").hide();
			$(".tip_search_error").text("网络异常，请重新查询！");
			$(".tip_search_error").show();
		}
	});
}

function loadTable(list){
	var html='';
	for(var i in list){
		var res = list[i];
		var staffAcc = res.staffAcc;
		var staffResourceSum = res.staffResourceSum;
		var staffName = res.staffName;
		var teamGroupName = res.teamGroupName;
		var concatTotal = res.concatTotal;
		var noConcatTotal = res.noConcatTotal;
		var servetime = moment(res.servetime).format("YYYY-MM-DD");
		html +=
		"<tr class='"+(i%2==0?'':'sty-bgcolor-b')+"'>"+
		"   <td style='width: 30px;'><div class='overflow_hidden w30 skin-minimal'><input type='checkbox' id='chk_"+ staffAcc +"_"+ staffResourceSum +"' /></div></td>" +
		"   <td><div class='overflow_hidden w70' title='"+staffName +"'>"+staffName +"</div></td>"+
		"   <td><div class='overflow_hidden w80' title='"+staffAcc +"'>"+staffAcc +"</div></td>"+
		"   <td><div class='overflow_hidden w100'>"+ teamGroupName +"</div></td>"+
		"   <td><div class='overflow_hidden w100'>"+ servetime +"</div></td>"+
		"   <td hidevalue='"+ staffResourceSum +"'><div class='overflow_hidden w150' title='"+staffResourceSum+"'>"+staffResourceSum+"("+concatTotal+"/"+noConcatTotal+")</div></td>" +
		"</tr>";
	}
	$(".ajax-table>tbody").html(html);
	/*表单优化*/
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
    //全选 
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

var setDdate = function(i){
	$('#s_startDate').val('');
	$('#s_endDate').val('');
	$("#dDateType").val(i);
};