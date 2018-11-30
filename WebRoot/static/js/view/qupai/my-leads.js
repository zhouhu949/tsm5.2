$(function(){
	/*新增操作*/
	$("#addLead").on("click",function(e){
		e.stopPropagation();
		pubDivShowIframe("add_leads",ctx+"/credit/lead/toAddLead","新增放款",750,520);
	})
	/*导入操作*/
	$("#importId").on("click",function(e){
		e.stopPropagation();
		pubDivShowIframe("import_leads",ctx+"/credit/leadimp/page",'导入资源',740,550);
	})
	/*导入结果*/
	$("#importResultId").on("click",function(e){
		e.stopPropagation();
		pubDivShowIframe("import_result",ctx+"/credit/leadimp/result",'导入结果',700,550);
	})
	/*导出操作*/
	$("#output").on("click",function(e){
		var formSer = $("#unAssginForm").serialize();
		pubDivShowIframe("exports_page",ctx+"/credit/lead/toExport?"+formSer,'导出',500,300);
	})
	/*删除操作*/
	$("#deleteRes").click(function(e){
		e.stopPropagation();
		var ids = getCustIds();
    	if(ids ==''){
    		 window.top.iDialogMsg("提示", '请选择记录');
    		 return false;
    	}

        if(checkedOrderJudge('status',1,true)){
            window.top.iDialogMsg("提示", '待放款记录不可删除！');
            return false;
        }else if(checkedOrderJudge('status',2,true)){
            window.top.iDialogMsg("提示", '已放款记录不可删除！');
            return false;
        }else if(checkedOrderJudge('status',3,true)){
            queDivDialog('confirm_deleteRes','确定删除选中记录？',function(){
                $.ajax({
                    url:ctx+"/credit/lead/delBatchLead",
                    data:{ids:ids},
                    success:function(data){
                        if(data.resultCode == 1){
                            parent.iDialogMsg('提示','删除成功！');
                            loadData();
                        }else{
                            parent.iDialogMsg('提示',data.resultDesc);
                        }
                    }
                });
            })
        }
	})
	
	$('.ajax-table').delegate('.icon-edit','click',function(e){
		var $target = $(e.currentTarget);
		var leadId = $target.data('id');
		pubDivShowIframe("add_leads",ctx+"/credit/lead/toEditLead?leadId="+leadId,"编辑放款",750,520);
	});
	
	$('.ajax-table').delegate('.icon-search-look','click',function(e){
		var $target = $(e.currentTarget);
		var leadId = $target.data('id');
		pubDivShowIframe("lead_info_detail",ctx+"/credit/lead/leadInfoDetail?leadId="+leadId,"放款详情",750,520);
	});
	
	//客户卡片
    $(".ajax-table").delegate("a[id^=cardInfo_]","click",function(){
    	var custId = $(this).attr("id").split("_")[1];
		var custName = $(this).attr("custName")||"客户卡片";
		window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName);
    });
});
/*收集复选框id*/
function getCustIds(){
	var ids = "";
	$("[id^=chk_]:checked").each(function(item,object){
		var id = $(object).attr('id').split('_')[1];
		ids = ids + id + ",";
	})
	return ids;
}
/*排除特定状态*/
function checkedOrderJudge(name,value,status) {
	//(1待放款、2已放款、3驳回)
	var item_status = true;
	if(status){
	    item_status = false;
	    $("[id^=chk_]:checked").each(function(idx,item){
            var $item = $(item);
            if($item.data(name) == value){
                item_status = true;
                return;
            }
        });
	}else{
        $("[id^=chk_]:checked").each(function(idx,item){
            var $item = $(item);
            if($item.data(name) != value){
                item_status = false;
                return;
            }
        });
	}
	return item_status;
}
