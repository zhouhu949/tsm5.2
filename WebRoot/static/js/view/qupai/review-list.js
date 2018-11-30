$(function(){
	/*导出操作*/
	$("#output").on("click",function(e){
		e.stopPropagation();
		var unAssginForm = $("#unAssginForm").serialize();
		pubDivShowIframe("export",ctx+"/credit/review/getExportField?"+unAssginForm,'导出',500,300);
	});
	
	$('.ajax-table').delegate('.icon-review-operate','click',function(e){
		var $this = $(this);
		var leadId = $this.data('id');
		var auditStatus = $this.data('status');
		pubDivShowIframe("review_operate",ctx+"/credit/review/getReviewInfo?leadId="+leadId+"&auditStatus="+auditStatus,'详情',750,520);
	});
});

function formDataSerialize(){
	var unAssginForm = $("#unAssginForm").serializeObject();
	return unAssginForm;
}