$(function(){
	$.ajax({
		type: "get",
		url:ctx+"/report/teamDayData/callData",
		success:function(data){
			appendTableData($("#data_table"),data);
			thToggleData(data);
			
			$("#searchBtn").on("click",function(){
				var groupNameStr = $("#groupNameStr").text();
				//groupNameStr == "-请选择-" ? sortData = data : sortData = sortDataByGroupName(data,groupNameStr);
				//appendTableData($("#data_table"),sortData);
				var $this = $(this) ;
				if($this.data().flag){
					$this.data("flag",false);
					ajaxForData($this);
				}
		    });
		},
		error:function(){
			window.top.iDialogMsg("提示","teamDayData数据获取失败！");
		}
	});
});

function appendTableData(table,data){
	if(table.find("tbody").html() != ""){
		table.find("tbody").html("");
	}
	var data_length = data.length;
	var total_calloutAnswer  = 0;
	var total_calloutTotal = 0;
	var total_validCalls = 0;
	var total_calloutTime = 0;
	var total_callinTime = 0;
	var total_billingTime = 0;
	for(var i=0; i<data_length; i++){
		total_calloutAnswer += data[i].calloutAnswer;
		total_calloutTotal += data[i].calloutTotal;
		total_validCalls += data[i].validCalls;
		total_calloutTime += data[i].calloutTime;
		total_callinTime += data[i].callinTime;
		total_billingTime += data[i].billingTime;
		var tbody_tr = "";
		i%2 == 0 ?  tbody_tr += "<tr>": tbody_tr += "<tr class='sty-bgcolor-b'>";
		var td_userName = '<td><div class="overflow_hidden w90" title="" class="td_userName">'+data[i].userName+'</div></td>';
		var td_groupName = '<td><div class="overflow_hidden w90" title="" class="td_groupName">'+data[i].groupName+'</div></td>';
		var td_calloutTotal = '<td><div class="overflow_hidden w100" title="" class="td_calloutTotal">'+data[i].calloutTotal+'</div></td>';
		var td_calloutAnswer = '<td><div class="overflow_hidden w100" title="" class="td_calloutAnswer">'+data[i].calloutAnswer+'</div></td>';
		var td_validCalls = '<td><div class="overflow_hidden w100" title="" class="td_validCalls">'+data[i].validCalls+'</div></td>';
		var td_calloutTime = '<td><div class="overflow_hidden w100" title="" class="td_calloutTime">'+data[i].calloutTime+'</div></td>';
		var td_callinTime = '<td><div class="overflow_hidden w100" title="" class="td_callinTime">'+data[i].callinTime+'</div></td>';
		var td_billingTime = '<td><div class="overflow_hidden w100" title="" class="td_billingTime">'+data[i].billingTime+'</div></td>';
		var td_actionCompletionRate = '<td><div class="overflow_hidden w100" title="" class="td_billingTime">'+data[i].actionCompletionRate+'</div></td>';
		tbody_tr +=(td_userName + td_groupName + td_calloutTotal + td_calloutAnswer + td_validCalls + td_calloutTime + td_callinTime + td_billingTime + td_actionCompletionRate + "</tr>");
		table.find("tbody").append(tbody_tr);
	}
	var tr_total = "";
	data_length %2 == 0 ? tr_total+='<tr id="data_tr_total">':tr_total+='<tr id="data_tr_total" class="sty-bgcolor-b">';
	tr_total = tr_total+
							 '<td colspan="2"><div class="overflow_hidden w90" title="">合计</div></td>'+
							 '<td><div class="overflow_hidden w100" title="">'+total_calloutTotal+'</div></td>'+
							 '<td><div class="overflow_hidden w100" title="">'+total_calloutAnswer+'</div></td>'+
							 '<td><div class="overflow_hidden w100" title="">'+total_validCalls+'</div></td>'+
							 '<td><div class="overflow_hidden w100" title="">'+total_calloutTime+'</div></td>'+
							 '<td><div class="overflow_hidden w100" title="">'+total_callinTime+'</div></td>'+
							 '<td><div class="overflow_hidden w100" title="">'+total_billingTime+'</div></td>'+
							 '<td><div class="overflow_hidden w100" title="">/</div></td>'+
						  '</tr>';
	table.find("tbody").append(tr_total);
}

function ajaxForData($this){
	$.ajax({
		url:ctx+"/report/teamDayData/callData",
		type:"get",
		data:{
			groupIds:$("#groupIds").val()
		},
		complete:function(){
			$this.data("flag",true);
		},
		error:function(){
			window.top.iDialogMsg("提示","teamDayData数据获取失败！");
		},
		success:function(data){
			appendTableData($("#data_table"),data);
			thToggleData(data);
		}
	})
}

function thToggleData(data){
	$("#data_table>thead th").toggle(
			function(){
				var dataTypeName = $(this).attr("name");
				if(dataTypeName && dataTypeName != ""){
					var currentData = sortDataDesc(data,dataTypeName);
					$(this).find(".td_sort_asc").addClass("td_sort_desc").removeClass("td_sort_asc");
					appendTableData($("#data_table"),currentData);
				}
			},function(){
				var dataTypeName = $(this).attr("name");
				if(dataTypeName && dataTypeName != ""){
					var currentData = sortDataAsc(data,dataTypeName);
					$(this).find(".td_sort_desc").addClass("td_sort_asc").removeClass("td_sort_desc");
					appendTableData($("#data_table"),currentData);
				}
			}
		);
}

function sortDataAsc(data,dataTypeName){
	var data_length = data.length;
	var temp_data = 0;
	for(var i=0;i<data_length;i++){
		for(var j=i;j<data_length;j++){
			if(data[i][dataTypeName] > data[j][dataTypeName]){
				temp_data = data[i];
				data[i] = data[j];
				data[j] = temp_data;
			}
		}
	}
//	console.log(data);
	return data;
}

function sortDataDesc(data,dataTypeName){
	var data_length = data.length;
	var temp_data = 0;
	for(var i=0;i<data_length;i++){
		for(var j=i;j<data_length;j++){
			if(data[i][dataTypeName] < data[j][dataTypeName]){
				temp_data = data[i];
				data[i] = data[j];
				data[j] = temp_data;
			}
		}
	}
//	console.log(data);
	return data;
}

function sortDataByGroupName(data,groupName){
	var data_length = data.length;
	var groups = groupName.split(",");
	var groups_length = groups.length;
	var groupsData = [];
	for(var i=0; i<data_length; i++){
		for(var j=0; j<groups_length; j++){
			if(groups[j] == data[i].groupName){
				groupsData.push(data[i]);
			}
		}
	}
//	console.log(groupsData);
	return groupsData;
}