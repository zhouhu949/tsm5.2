$(function(){
	$.ajax({
		type: "get",
		url:ctx+"/newWill/testdaydata",
		success:function(data){
			var sortData = data.list;
			appendTableTh($("#data_table"),data.item)
			appendTableData($("#data_table"),data.list,data.item);
		},
		error:function(){
			alert("意向客户今日数据获取失败！");
		}
	});
	$("#searchBtn").on("click",function(){
		$.ajax({
			type:"get",
			url:ctx+"/newWill/testdaydata",
			data:$("#form").serialize(),
			success:function(data){
				var sortData = data.list;
				appendTableTh($("#data_table"),data.item)
				appendTableData($("#data_table"),data.list,data.item);
			}
		})
    });
	$("body").delegate(".report-click-detail","click",function(e){
		e.stopPropagation();
		var $this = $(this);
		var userAccount = $this.data("useracc");
		var optionlistId = $this.data("optionid");
		var optionName = $this.data("optionname");
		if(optionlistId != "" && optionlistId != null){
			var name = "今日新增"+optionName+"进程明细";
		}else{
			var name = "今日新增意向客户明细";
		}
		window.top.pubDivShowIframe('change_log_detail',ctx+'/newWill/newWillToday?userAccount='+userAccount+"&optionlistId="+optionlistId,name,900,450);
	})
});

function appendTableData(table,data,item){
	var thLen=Number(item.length) + 3;
	var tbody_tr = "";
	var data_length = data.length;
	if(data_length > 1 ){//多了一个合计
		for(var i=0; i<data_length; i++){
			i%2 == 0 ?  tbody_tr += "<tr>": tbody_tr += "<tr class='sty-bgcolor-b'>";
				if( i  == (data_length-1) ){
					var td_userName = '<td colspan="2"><div class="overflow_hidden w90" title="">合计</div></td>';
				}else{
					var td_userName = '<td><div class="overflow_hidden w90" title="" class="td_userName">'+data[i].userName+'</div></td><td><div class="overflow_hidden w90" title="" class="td_groupName">'+data[i].groupName+'</div></td>';
				}
			var td_data='';
			var returnlistLength=data[i].returnlist.length;
			for(var j = 0 ; j<returnlistLength ; j ++){
				var item=data[i].returnlist[j];
				if(item.num > 0){
					td_data +='<td><div class="overflow_hidden w100"><a href="javascript:;" class="report-click-detail" title="'+item.num+'" data-optionid="'+item.optionId+'" data-optionname="'+item.optionName+'" data-useracc="'+data[i].userAccount+'">'+item.num+'</a></div></td>';
				}else{
					td_data +='<td><div class="overflow_hidden w100" title="'+item.num+'">'+item.num+'</div></td>';
				}
			}
			tbody_tr +=(td_userName  +td_data+ "</tr>");
			table.find("tbody").html(tbody_tr);
		}
	}else{
		tbody_tr += '<tr><td colspan="'+thLen+'">当前列表无数据！</td></tr>';
		table.find("tbody").html(tbody_tr);
	}
	heightReset();
}

function appendTableTh(table,data){
	var html = '<th><span class="sp sty-borcolor-b">销售姓名</span></th><th><span class="sp sty-borcolor-b">所属小组</span></th><th><span class="sp sty-borcolor-b">新增意向数</span></th>';
	for(var i = 0 ; i < data.length ; i ++){
		if(i != (data.length-1)){
			html +='  <th><span class="sp sty-borcolor-b">'+data[i]+'</span></th>';
		}else{
			html +='  <th>'+data[i]+'</th>';
		}
	}
	table.find("thead>tr").html(html);
}