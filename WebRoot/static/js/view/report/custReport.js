$(function () {
	//tabs切换
	$(".ann-sale-plan li").click(function(){
		var rel = $(this).attr("rel");
		if(rel != null && rel != ''){
			window.location.href = rel;
		}
	});
	$("body").delegate(".cust-report-picbox-item","click",function(e){
		e.stopPropagation();
		var $this = $(this);
		var customReportId = $this.data("reportid");
		var customReportName = $this.data("name");
		var isDouble = $this.data("double");
		window.parent.addTab(ctx+'/custom/report/toReportDetail?customReportId='+customReportId+'&isDouble='+isDouble+'&customReportName='+customReportName, "报表详情");
	})
	//新建报表
	$(".new-report").click(function(e){
		e.stopPropagation();
		pubDivShowIframe('add_reports',ctx+'/custom/report/toAddCustomReport','新建报表',700,550);
	})
	//编辑
	$("body").delegate(".picbox-bottom-edit","click",function(e){
		e.stopPropagation();
		var customReportId = $(this).data("reportid");
		pubDivShowIframe('add_reports',ctx+'/custom/report/toEditCustomReport?customReportId='+customReportId,'编辑报表',700,550);
	})
	//分享
	$("body").delegate(".picbox-bottom-share","click",function(e){
		e.stopPropagation();
		var customReportId = $(this).data("reportid");
		window.top.pubDivShowIframe('share_reports',ctx+'/custom/report/toSetShare?customReportId='+customReportId,'分享报表',300,270);
	})
	//删除
	$("body").delegate(".picbox-bottom-delete","click",function(e){
		e.stopPropagation();
		var customReportId = $(this).data("reportid");
		$.ajax({
			url:ctx+"/custom/report/judgeIsShare",
			data:{
				customReportId:customReportId
			},
			success:function(data){
				if(data.status){
					pubDivDialog("sure_to_delete","是否要删除报表？",function(){
						deleteReport(customReportId);
					})
				}else{
					pubDivDialog("real_to_delete","删除后分享对象将无法查看该报表，是否要删除报表？",function(){
						deleteReport(customReportId);
					})
				}
			}
		})
	})
	
	var isShare = $("#isShare").val();
	if(isShare == 1){//分享报表
		loadShareData();
	}else{//我的报表
		loadMyData();
	}
});
function loadMyData(){
	$.ajax({
		url:ctx+"/custom/report/myCustomReportListJson",
		success:function(data){
			var result = {list:data};
			var template = Handlebars.compile($("#myreportTemplate").html());
			$(".cust-report-picbox").html(template(result));
		}
	})
}
function deleteReport(customReportId){
	$.ajax({
		url:ctx+"/custom/report/delCustomReport",//
		data:{
			customReportId:customReportId
		},
		success:function(data){
			if(data.status){
				window.top.iDialogMsg("提示",data.data)
				loadMyData()
			}else{
				window.top.iDialogMsg("提示",data.errorMsg)
			}
		}
	})
}
function loadShareData(){
	$.ajax({
		url:ctx+"/custom/report/shareToMeCustomReportListJson",
		success:function(data){
			var result = {list:data};
			var template = Handlebars.compile($("#myreportTemplate").html());
			$(".cust-report-picbox").html(template(result));
		}
	})
}