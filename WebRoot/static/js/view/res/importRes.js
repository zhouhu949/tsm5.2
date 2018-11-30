var headerAndFistRowData=null; // 导入数据
var importFields =null; // 匹配字段
var id = null; // import_res_file 主键ID
var opera = true;
function stepBefore(){
	$(".role-step.clearfix .next-step").attr("disabled","disabled");
		var curStep = stepBar.curStep;
		if(curStep==2){
			matchFields(step_);
		}else{
			step_();
		}
}

function step_() {
	var _this = stepBar;
	var curStep = stepBar.curStep;
	$("#step_").val(curStep);
	var step = parseInt($("#step_").val()) + parseInt(1);
	if (step < 4) {
		$("#step_").val(step);
		var triggerStep = step;
		page_tab(step);
		_this.triggerStep = triggerStep;
		_this.percent();
		if (step > 1) {
			$(".last-step").css({
				'display' : 'inline-block'
			});
			$(".next-step").css({
				'display' : 'inline-block'
			});
			$(".temp-down-load").hide();
			$(".kind-remin").hide();
		}
		;
		if (step == 3) {
			$(".fish-secu").css({
				'display' : 'inline-block'
			});
			$(".fish-secu").css({
				'margin-left' : '115px'
			});
			$(".last-step").hide();
			$(".next-step").hide();
		}
		;
	}
	$(".role-step.clearfix .next-step").removeAttr("disabled");
}

function step_1() {
	var _this = stepBar;
	var curStep = stepBar.curStep;
	$("#step_").val(curStep);
	var step = parseInt($("#step_").val()) - parseInt(1);
	if (step > 0 && step < 6) {
		$("#step_").val(step);
		var triggerStep = step;
		page_tab(step);
		_this.triggerStep = triggerStep;
		_this.percent();
		if (step < 5) {
			$(".fish-secu").hide();
			$(".last-step").css({
				'display' : 'inline-block'
			});
			$(".next-step").css({
				'display' : 'inline-block'
			});
		}
		;
		if (step == 1) {
			$(".last-step").hide();
			$(".next-step").hide();
			$(".temp-down-load").show();
			$(".kind-remin").show();

		}
		;
	} else {

	}
}
function page_tab(type) {
	$("div[id^='role-menu']").each(function() {
		$("#" + this.id).hide();

	});
	$("#role-menu" + type).show();

}
/* 生成表格*/
function createMatchTable(){
	var html="";
	var headers =headerAndFistRowData.header;
	var data=headerAndFistRowData.datas;
	var headerFiled = headerAndFistRowData.headerFiled;
	if(data.length==headers.length){
		for(var i=0;i<data.length;i++){
			html+=
			'<tr>'+
			'	<td>'+headers[i]+'</td>'+
			'	<td><select class="selectField" onchange="selectChk(this.value)">';
			html+='<option  value=" ">不导入该字段</option>';
			for(var j=0;j<importFields.length;j++){
				html+='<option  '+(headerFiled[i]==importFields[j].fieldCode+'_'+importFields[j].isState?'selected = "selected"':'')+'value="'+importFields[j].fieldCode+'_'+importFields[j].isState+'">'+importFields[j].fieldName+'</option>';
			}			
			html+='</select></td>'+
			'	<td>'+data[i]+'</td>'+
			'</tr>';
		}
	$("#matchTable tbody").html(html);
	
	$("#totalRow").html(headerAndFistRowData.rows);
	}else{
		window.top.iDialogMsg("提示","EXCEL 表头与第一行数据列数不同!");
	}
}
/* 匹配字段文件*/
function matchFields(callback){
	$(".role-step.clearfix .next-step").attr("disabled","disabled");
	var $arry = $(".selectField");
	var arry = []; 
	var arryNamemap = new Map();
	var $filedSets = $('#filedSets').val();
	var $arry1 = $('#filedSets').val().split(";"); // 必填字段
	if($arry.length>0){
		$arry.each(function(){    
			arry.push($(this).val());
			arryNamemap.put($(this).val(),$(this).find("option:selected").text())
		 }); 
		var nary=arry.sort();  	
		var jj = 0;
		for(var i=0;i<arry.length;i++){ 
			 if($.trim(nary[i]) == ""){
				 jj++;
			 }		 
			 
			if ($.trim(nary[i]) != "" && nary[i]==nary[i+1]){	
				 window.top.iDialogMsg("提示","出现相同字段【"+arryNamemap.get(nary[i])+"】，请重新匹配");
				 $(".role-step.clearfix .next-step").removeAttr("disabled");
				 return false;
			 } 
			 if($filedSets.length > 0){ // 必填字段不为空
				for(var arr=0 ;arr<$arry1.length;arr++){					
					if(nary[i]== $arry1[arr].split(',')[0]){			
						$filedSets = $filedSets.replace($arry1[arr].split(',')[0]+","+$arry1[arr].split(',')[1]+";","");					
					}
				} 
			 }	 
		 }
		var $arry22 = '';
		if($filedSets.length > 0){
			var $arry2 = $filedSets.split(";"); // 必填字段
			for(var a=0 ;a<$arry2.length-1;a++){ 
				$arry22 += $arry2[a].split(",")[1]+"、";
			 }; 
			window.top.iDialogMsg("提示",$arry22.substring(0,$arry22.length-1)+" 为必填项！");
			$(".role-step.clearfix .next-step").removeAttr("disabled");
			return false;
		}

		if(jj == arry.length){
			window.top.iDialogMsg("提示","请至少匹配一个字段");
			$(".role-step.clearfix .next-step").removeAttr("disabled");
			return false;
		}
		// 正常匹配字段
		var tempArray= new Array();
		for(var i=0;i<$arry.length;i++){			
			var select =$($arry[i]);
			var value =select.val();
			tempArray.push(value);
		}
		
		var firstCheck = 0;
		if($("#firstCheck").is(":checked")){
			firstCheck = 1;
		}else{
			firstCheck = 0;
		}	
		 $.ajax({
	            type: "post",
	            dataType: "html",
	            url: tsmUpLoadServiceUrl+ctx + "/fileupload/resimpHyx/matchFields",
	            data: {"id":id,"fieldsStr":tempArray.toString(),"firstCheck":firstCheck,
	            	"orgId":impShiroOrgId,"isState":impShiroUserIsState,
	            	"account":impShiroAccount,
	            	"groupId":impShiroUserGroupId,
	            	"isMoreDetail":isDetail},
	            success: function(json,status) {
	            	var data = JSON.parse(json);	
	            	if(data.status=="success"){
	    				//analyze();
	            		$(".role-step.clearfix .next-step").removeAttr("disabled");
	    				callback();
	    			}else{
	    				$(".role-step.clearfix .next-step").removeAttr("disabled");
	    				window.top.iDialogMsg("提示",data.msg);
	    			}
	            }
	        });
	}
	
}

/**
 *  匹配下拉框事件
 */
function selectChk(code){	
	var arry = $(".selectField");
	var i = 0;
	if($.trim(code)!=""){
	 if(arry.length>0){
	  for(var j = 0; j < arry.length; j++) {
		   if(code == arry[j].value){
			   i++;
		   }
	  	}
	  if(i>1){
		  window.top.iDialogMsg("提示","所匹配字段已经匹配，请重新选择");
	  }
	 }
	}
}

/*analyze*/
function analyze(){
	$.post(ctx+"/resimp/analyze",{"id":id},function(data){
		if(data.status=="success"){
			window.top.iDialogMsg("提示","解析完成");
		}else{
			window.top.iDialogMsg("提示",data.msg);
		}
	});
}


Array.prototype.contains = function (obj){
	if(this==null ||this.length==0){
		return false;
	}else{
		for(var i=0;i<this.length;i++){
			if(this[i]==obj){
				return true;
				break;
			};
		}
		return false;
	};
};

/*事件绑定*/
function eventBind(){
	$(".select li a").click =function (){
	};
	
	$(".selectField").select =function (){
	};
}

function init(){
	eventBind();
}

function styleInit(){
	$("#step_").val(2);
	stepBar.init("stepBar", {
		step : 1,
		change : true,
		animation : true
	});
/*	$(".next-step").css({"margin-left": "120px"});*/
}

/** =========定义MAP================ */
	function Map(){
		this.container = new Object();
	}


	Map.prototype.put = function(key, value){
	this.container[key] = value;
	}


	Map.prototype.get = function(key){
	return this.container[key];
	}


	Map.prototype.keySet = function() {
	var keyset = new Array();
	var count = 0;
	for (var key in this.container) {
	// 跳过object的extend函数
	if (key == 'extend') {
	continue;
	}
	keyset[count] = key;
	count++;
	}
	return keyset;
	}


	Map.prototype.size = function() {
	var count = 0;
	for (var key in this.container) {
	// 跳过object的extend函数
	if (key == 'extend'){
	continue;
	}
	count++;
	}
	return count;
	}


	Map.prototype.remove = function(key) {
	delete this.container[key];
	}
/**=============================================*/
$(function() {
	styleInit();
	init();
});