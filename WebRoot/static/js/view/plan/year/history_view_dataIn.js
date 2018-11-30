function loadData(){
	var _url=ctx+"/plan/month/user/historyViewJson";
	var _param = $("form").serialize() ;
	$.ajax({
		type:"get",
		url:_url,
		data:_param,
		error:function(){
			window.parent.window.top.iDialogMsg("提示", '系统查询中，请稍候！');
		},
		success:function(data){
			loadTable(data.list);
			pa.load("new_page",data.item.page,loadData,$("table"));
		}
	});
}

function loadTable(list){
	var html='';
	for(var i in list){
		var plan = list[i];
		html +=
		"<tr class='"+(i%2==0?'':'sty-bgcolor-b')+"'>"+
		"	<td><div class='overflow_hidden w70' title='"+plan.planYear +"'>"+plan.planYear +"年</div></td>"+
		"	<td><div class='overflow_hidden w70' title='"+plan.planMonth +"月'>"+plan.planMonth +"月</div></td>"+
		"	<td><div class='overflow_hidden w100' title='"+plan.groupName +"'>"+plan.groupName +"</div></td>"+
		"	<td><div class='overflow_hidden w70' title='"+plan.userName +"'>"+plan.userName +"</div></td>"+
		"	<td><div class='overflow_hidden w70' title='"+plan.custName +"'>"+plan.custName +"</div></td>";
		if(isState == 0){
			html+="	<td><div class='overflow_hidden w70' title='"+plan.company +"'>"+plan.company +"</div></td>";
		}
		html+=
		"	<td><div class='overflow_hidden w70' title='"+plan.willcustNum +"'>"+plan.willcustNum +"</div></td>"+
		"	<td><div class='overflow_hidden w70' title='"+plan.singcustNum +"'>"+plan.singcustNum +"</div></td>"+
		"	<td><div class='overflow_hidden w70' title='"+plan.planMoney +"'>"+plan.planMoney +"</div></td>"+
		"	<td><div class='overflow_hidden w100' title='"+(plan.status ==0?'未完成':'完成')+"'>"+(plan.status ==0?'未完成':'完成')+"</div></td>"+
		"</tr>";
	}
	$("tbody").html(html);
}

$(function(){
	loadData();
	
/*	$("#query").click(function(){
		loadData();
	});*/
	
	/*表单优化*/
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });

	$("#cfm01").click(function(){
		confirmGroupTree();
	});
	
	$("#close01").click(function(){
		clearGroupTree();
	});
	
	$("#cfm02").click(function(){
		confirmUserTree();
	});
		
	$("#close02").click(function(){
		clearUserTree();
	});
	
    treeInit();
});

function planYear(value){
	$("#planYear").val(value);
}
function planMonth(value){
	$("#planMonth").val(value);
}
function status(value){
	$("#status").val(value);
}


function treeInit(){
	$("#groupTree").tree({
		url:ctx+"/orgGroup/get_group_json",
		checkbox:true,
		onLoadSuccess:function(node,data){
			var oas = $("#groupIds").val();
			if(oas != null && oas != ''){
				$.each(oas.split(','),function(index,obj){
					var n = $("#groupTree").tree("find",obj);
					$("#groupTree").tree("check",n.target).tree("expandTo",n.target);
				});
			}
		}
	});
	
	$("#userTree").tree({
		url:ctx+"/orgGroup/get_group_user_json",
		checkbox:true,
		onLoadSuccess:function(node,data){
			var oas = $("#userAccs").val();
			if(oas != null && oas != ''){
				$.each(oas.split(','),function(index,obj){
					var n = $("#userTree").tree("find",obj);
					$("#userTree").tree("check",n.target).tree("expandTo",n.target);
				});
			}
		}
	});
}


function confirmUserTree(){
	
	var nodes = $('#userTree').tree('getChecked', 'checked');
	var accArray = new Array();
	var idArray = new Array();
	$.each(nodes,function(index,obj){
		var type = obj.attributes.type;
		if(type == "M"){
			accArray.push(obj.id);
			idArray.push(obj.user_id);
		}
	});
	
	$("#userAccs").val(accArray.toString());
	$("#userIds").val(idArray.toString());
}

function clearUserTree(){
	var nodes = $('#userTree').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#userTree').tree("uncheck",obj.target);
	});
	$("#userAccs").val('');
	$("#userIds").val('');
}

function confirmGroupTree(){
	var nodes = $('#groupTree').tree('getChecked', 'checked');
	var idArray = new Array();
	$.each(nodes,function(index,obj){
		idArray.push(obj.id);
	});
	$("#groupIds").val(idArray.toString());
}

function clearGroupTree(){
	var nodes = $('#groupTree').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#groupTree').tree("uncheck",obj.target);
	});
	$("#groupIds").val('');
}