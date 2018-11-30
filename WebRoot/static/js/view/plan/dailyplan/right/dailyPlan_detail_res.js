$(function(){
	var checkBox= new CheckBox("checkAll","chk_","custId","state","resCustLevel");
	 loadData();
	 $("#join_cust").click(function(e){
		 e.stopPropagation();
		 var $this =$(this);
		 var dateStr =$this.data("datestr");
		 var planid = $this.data("planid");
		 pubDivShowIframe('join_custs',ctx+'/res/cust/myRests?planPage=1&planType=res&dateStr='+dateStr+'&planid='+planid,'加入客户',900,560);
	 });
	$("#time_change").click(function(e){
		e.stopPropagation();
		var planDate = $("#dateStr").val();
		var sudId = $("#sudId").val();
		if(checkBox.getIds()!=null){
			pubDivShowIframe('moveAnotherDayWindow',ctx+'/plan/day/moveAnotherDayWindow?type=res&sudId='+sudId+'&planDate='+planDate+'&custIds='+checkBox.custIds.toString(),'计划时间调整',450,330);
		}
	});
	 //标签点击
	 $(".com-moTag .e").click(function(e){
		 e.stopPropagation();
		 var $this = $(this);
		 $(".com-moTag .e").removeClass("e-hover");
		 $this.addClass("e-hover");
		 var result = $(this).data("result");
		 
		 
		 var array=["全部","转签约","转意向","转公海","已联系","未联系","联系无变化","优先资源"];
		//清除其他查询条件
		$("#queryText").val(null);
		$("#custLevel").val(null);
		$("#status").val(null);
		$("#contactResult").val(null);
		//已安排  1  已联系 2  未联系 3
		if(result==0){
			
		}else if(result==4){
			//已联系
			$("#status").val(1);
		}else if(result==5){
			//未联系
			$("#status").val(0);
		}else if(result==7){
			//优先资源
			$("#custLevel").val(1);
		}else{
			$("#status").val(1);
			$("#contactResult").val(array[result]);
		}
		 loadData();
	 });
	//设置优先级
	$("#setImpBtn").click(function(){
		setImpOrUnImp(1);
	});
	//取消优先级
	$("#setUnImpBtn").click(function(){
		setImpOrUnImp(0);
	});
});
function loadData(){
	var data = $("#myForm").serialize();
	loadAjax(data);
}

function refreshRight(){
	debugger;
	$("#iframepage").contents().find("form").submit();
}

function refreshPageData(page){
    var table = $(".ajax-table");
    var _param=table.attr("data-param")
    var pageStr="&";
    if(page){
        pageStr = "&page.currentPage="+page.currentPage+"&page.totalResult="+page.totalResult+"&page.showCount="+page.showCount;
    }
    loadAjax(_param,pageStr);
}

function loadAjax(data,page){
	$(".ajax-table").attr("data-param",data);
	
	searchingDataTip();
	var timeout = setTimeout(function(){
		$(".tip_search_text").hide();
		$(".tip_search_error").text("网络繁忙，请耐心等候或重新查询！");
		$(".tip_search_error").show();
	},10000);
	if(page){
		data = data+"&"+page;
	}
	$.ajax({
		url:ctx+"/plan/day/resource/resDataJson",
		type:"get",
		data:data,
		cache:false,
		success:function(result){
		    /*
			if(result.length > 0){
				var custId = result.list[0].cust_custId;
				var followId = result.list[0].cust_last_cust_follow_id;
				loadRecordRight(custId,followId);
			}else{
				loadRecordRight(undefined,undefined);
			}
			*/
			renderTable(result);
			$(".tip_search_data").hide();
         //   jQuery(".hyx-fur-table .hyx-fur-tr").not(".disabled").first().addClass("asdf");
			jQuery(".hyx-fur-table .hyx-fur-tr").not(".disabled").first().click();
			clearTimeout(timeout);
			pa.load('new_page',result.item.page,refreshPageData,$('#t1'));
		},
		error:function(data){
			clearTimeout(timeout);
			$(".tip_search_text").hide();
			$(".tip_search_error").text("网络异常，请重新查询！");
			$(".tip_search_error").show();
		}
	})
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

function renderTable(data){
	var myTemplate = Handlebars.compile($("#template").html());
	$("#t1 tbody").html(myTemplate(data));
	$('.skin-minimal input').iCheck({
			checkboxClass: 'icheckbox_minimal',
			radioClass: 'iradio_minimal',
			increaseArea: '20%'
	});
	 $('#checkAll').iCheck('uncheck');
}
function setImpOrUnImp(pr){
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
	window.top.pubDivDialog("res_imp_"+pr,"是否确认"+(pr == 0 ? '取消' : '设置')+"优先级？",function(){
			$.ajax({
				url:ctx+'/res/cust/setImp',
				type:'post',
				data:{ids:ids,isPrecedence:pr},
				dataType:'html',
				error:function(){alert('网络异常，请稍后再试！')},
				success:function(data){
					if(data == 1){
						window.top.iDialogMsg("提示",(pr == 0 ? '取消' : '设置')+'优先级成功！');
						refreshPageData(pa.pageParameter());
					}else{
						window.top.iDialogMsg("提示",(pr == 0 ? '取消' : '设置')+'优先级失败！');
					}
				}
		});
	});
}