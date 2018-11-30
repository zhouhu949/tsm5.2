$(function(){
	var _changeArr =  ["零", "一", "二", "三", "四", "五", "六", "七", "八", "九"];
	
	
	$(".click-into-detail-span,.new-daily-plan-box-body-row-right-items-title-today").live("click",function(e){
		e.stopPropagation();
		var $this = $(this);
		var line = $this.parents(".new-daily-plan-box-body-row");
		var type=$this.data("type");
		var status = $this.data("status");
		var planId = line.data("id");
		var dateStr = line.data("datestr");
		var userAcc = $("#userAcc").val();
		var userId = $("#userId").val();
		var userName = $("#userName").val();
		var custStatusId=$this.data("custstatusid")?$this.data("custstatusid"):"";
		var t=new Date().getTime();
		 window.parent.addTab(ctx+'/plan/day/detail?s='+t+'&dateStr='+dateStr+'&type='+type+'&status='+status+'&planId='+planId+'&userAcc='+userAcc+'&userId='+userId+'&custStatusId='+custStatusId, userName+'-日计划');
	})
	//开始安排本日工作
	$(".new-daily-plan-start-planbtn").live("click",function(e){
		e.stopPropagation();
		var userAcc = $("#userAcc").val();
		var userId = $("#userId").val();
		var userName = $("#userName").val();
		var dateStr = $(this).parents(".new-daily-plan-box-body-row").data("datestr");
		 window.parent.addTab(ctx+'/plan/day/detail?dateStr='+dateStr+'&userAcc='+userAcc+'&type=1&userId='+userId, userName+'-日计划');
	})
	
	//选择月份的select注入
	selectMonth();
	//选择周的select注入
	selectWeek(_changeArr);
	
	var weekIndex = $("[name='week']").val();
	var dateStr = $("[name='dateStr']").val();
	serilizeForm(weekIndex,_changeArr,dateStr);
	
	var $weekEndTime = $("#weekEnd").val();
	var $currWeekIndex = Number($("#weekIndex").val());
	$(".new-daily-plan-box-head-title-month").html(moment($weekEndTime).format("M"));
	$(".new-daily-plan-box-head-title-weekIndex").html(_changeArr[$currWeekIndex]);
	//人员树注入
	$("#tt1").tree({
		url:ctx+'/orgGroup/get_group_user_json',
		checkbox:true,
		onlyLeafCheck: true,
		cascadeCheck : false,
		onSelect:function(node,checked){
			var cknodes =$('#tt1').tree('getChecked', 'checked');
            for (var i = 0; i < cknodes.length; i++) {
                if (cknodes[i].id != node.id) {
                    $('#tt1').tree("uncheck", cknodes[i].target);
                }
            }
            if (node.checked) {
                $('#tt1').tree('uncheck', node.target);

            } else {
                $('#tt1').tree('check', node.target);
            }
		},
		onLoadSuccess:function(node,data){
			var oas = $("#userAcc").val();
			if(oas != null && oas != ''){
				var text='';
				$.each(oas.split(','),function(index,obj){
					var n = $("#tt1").tree("find",obj);
					if(n != null){
						text+=n.text+',';
						$("#tt1").tree("check",n.target).tree("expandTo",n.target);
					}
				});
				if(text != ''){
					text=text.substring(0,text.length-1);
					$("#groupNameStr").text(text);
					$("#userName").val(text);
				}else{
					$("#groupNameStr").text("-请选择-");
				}
			}
			$(this).find('span.tree-checkbox').unbind().click(function () {
                $('#tt1').tree('select', $(this).parent());
                return false;
            });
			//如果叶子节点为部门的 checkbox隐藏
	           var roots =  $("#tt1").tree("getRoot");
	           var childs = $("#tt1").tree("getChildren",roots);
	           for( var i = 0 ; i < childs.length ; i++){
	        	   var item = childs[i];
	        	   if( item.attributes.type &&item.attributes.type == "G" ){
	        		   $(item.target).find(".tree-checkbox").remove();
	        	   }
	           }
		}
	});
	
	$("[name='week']").change(function(){
		var weekIndex = $("[name='week']").val();
		var dateStr = $("[name='dateStr']").val();
		serilizeForm(weekIndex,_changeArr,dateStr);
	})
	$("[name='dateStr']").change(function(){
		$("#weekIndex").val(1);
		$("#daily-plan-form")[0].submit();
	})
	//选择
	$(".select").find("dt").live("click",function(e){
		e.stopPropagation();
		var isHidden = $(this).next().is(":hidden");
		if(isHidden){
			$(this).next().slideDown(200);
			$(this).next().addClass("cur");
		}else{
			$(this).next().slideUp(200);
			$(this).next().removeClass("cur");
		}
		return false;
	});
	$(".reso-owner-sure").live("click",function(){
		$(this).parent().parent().slideUp(200);
		$(this).parent().parent().removeClass("cur");
		return false;
	});
	$(".com-btna-cancle").live("click",function(){
		return false;	
	});
	
	$(document).click(function(e){
		e.stopPropagation();
		 $(".select dd").hide();
	});
})
function serilizeForm(weekIndex,_changeArr,dateStr){
	var data = $("#daily-plan-form").serialize();
	loadAjax(data,weekIndex,_changeArr,dateStr);
}

function loadAjax(data,weekIndex,_changeArr,dateStr){
	$.ajax({
		url:ctx+"/plan/day/viewJson",
		type:"get",
		data:data,
		cache:false,
		success:function(data){
			var aa={'list':data};
			renderTable(aa)
			var startStr = data[0].planDateStr;
			var endStr = data[data.length - 1].planDateStr;
			if(weekIndex){$(".new-daily-plan-box-head-title-weekIndex").text(_changeArr[weekIndex])}
			if(dateStr){$(".new-daily-plan-box-head-title-month").text(moment(dateStr).format("M"))}
			$(".start-date").text(moment(startStr).format("YYYY年MM月DD日"))
			$(".end-date").text(moment(endStr).format("YYYY年MM月DD日"))
		}
	})
}
function renderTable(data){
	var myTemplate = Handlebars.compile($("#template").html());
	$(".new-daily-plan-box").html(myTemplate(data));
}

function selectWeek(_changeArr){
	var account = Number($("#weekNum").val());
	var index = Number($("#weekIndex").val());
	var weekStr = "";
	for(var i = 0 ;i < account ; i++){
		var current = i +1;
		weekStr += '<option value="'+current+'" '+(current ==index ? 'selected':'')+'>第'+_changeArr[current]+'周</option>';
	}
	$(".select-week").html(weekStr);
}
//填充选择月份的select
function selectMonth(){
	var currTime = $("#weekEnd").val();
	var currStr = moment(currTime).format("YYYY年MM月");
	var beforeJson = monthLaterAndBefore(currTime,"subtract",3,"month");
	var laterJson = monthLaterAndBefore(currTime,"add",3,"month");
	beforeJson.itemFormat.push(currStr);
	beforeJson.itemTime.push(moment(currTime));
	var finalArr = beforeJson.itemFormat.concat(laterJson.itemFormat);
	var finalFor = beforeJson.itemTime.concat(laterJson.itemTime);
	var monthStr = "";
	for(var i = 0 ; i < finalArr.length ; i++){
		monthStr +='<option value="'+finalFor[i].format("YYYY-MM-01")+'" '+(currStr==finalArr[i]?"selected":"")+'>'+finalArr[i]+'</option>';
	}
	$(".select-month").html(monthStr);
}
//函数把格式化好的时间和最初的时间戳转为moment对象   组成两个数组后返回一个总的json
function monthLaterAndBefore(time,type,num,unit){
	var returnArr = [];
	var timeArr = [];
	if(type == "add"){
		for(var i = 0 ; i< num ; i++ ){
			var realNum = i+1;
			var Item = moment(time).add(realNum,unit).format("YYYY年MM月");
			returnArr.push(Item);
			timeArr.push(moment(time).add(realNum,unit));
		}
		var returnJson = {
				itemFormat: returnArr,
				itemTime:timeArr
		}
		return returnJson;
	}else if(type == "subtract"){
		for(var i = num ; i > 0 ; i-- ){
			var Item = moment(time).subtract(i,unit).format("YYYY年MM月");
			returnArr.push(Item);
			timeArr.push(moment(time).subtract(i,unit));
		}
	}
	var returnJson = {
			itemFormat: returnArr,
			itemTime:timeArr
	}
	return returnJson;
}
//树选择和树清空(树为单选，只有一个选项，父节点也不需要)
function selGroup(){
	var id = '';
	var name = '';
	var text = '';
	var checked = $($("#tt1").tree('getChecked', 'checked'))
	var flag = true;
	checked.each(function(index,node){
		if(index == checked.length - 1){
			id+=node.user_id;
			name+=node.id;
			text += node.text;
		}
		if(node.attributes.type && node.attributes.type =="G"){
			$("#userAcc").val("");
			$("#userId").val();
			$("#userName").val("");
			$("#groupNameStr").text("-请选择-");
			flag = false;
		}
	});
	if(!flag){
		return false;
	}
	$("#userId").val(id);
	$("#userAcc").val(name == '' ? '' : name );
	$("#userName").val(text);
	$("#groupNameStr").text(text == '' ? '-请选择-' : text ).attr("title",text == '' ? '-请选择-' : text);
	var _changeArr =  ["零", "一", "二", "三", "四", "五", "六", "七", "八", "九"];
	var data = $("#daily-plan-form").serialize();
	loadAjax(data,undefined,_changeArr,undefined);
}
function clearGroup(){
	var nodes = $('#tt1').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#tt1').tree("uncheck",obj.target);
	});
}