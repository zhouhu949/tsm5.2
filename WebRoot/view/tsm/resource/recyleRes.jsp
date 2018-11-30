<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	deferredSyntaxAllowedAsLiteral="true"%>
<%@ include file="/common/include.jsp"%>
<!--公共样式-->
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"> --%>
<!--主要样式-->
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/static/js/idialog/skins/iblue.css"> --%>
<head>
<style>
#myForm2 label.fl_l{display: inline-block;width: 125px;text-align: right; height: 25px;line-height: 25px;}
.com-search .select dt{background-position:160px center;height: 25px;line-height: 25px;width: 160px;border: 1px solid #e1e1e1;margin-top: 0;}
.sel_a{line-height: 25px;height: 25px;width: 180px;border:1px solid #e1e1e1;}
#recyleSumid{line-height: 25px;height: 25px;border: 1px solid #e1e1e1;display: inline-block;width: 180px;}
.w-a {margin-top: 10px;}
</style>
<script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
</head>
<body>
<input type="hidden" id="taskId" value="${taskId}" /> 
<form method="post" id="myForm2" class="clearfix project-comp-idialog-box">
	<div class="reso-new-group" style="margin-bottom:5px !important;margin-top:20px;">
		<input type="hidden" id="ctPath" value="${ctx}" /> 
		<input type="hidden" id="staffAccs" value="${staffAccs}" /> 
		<input type="hidden" id="staffAcc" value="${staffAcc}" />
		<input type="hidden" id="minSum" value="${minSum}" />	
		<label for="" class="fl_l">回收条数：</label>
		<input type="text" id="recyleSumid" value="">
		<span>条/人</span>
	</div>
	
		<div class='w-a clearfix' >
			<label class="fl_l"  for="">回收前资源分组：</label>
			<%-- <dl class="select resGroup" style="width:210px;" data-input="[name='resGroupId']">
				<dt>全部</dt>
				<dd>
					<ul class="resource-group-tree second-group-tree" data-url="${ctx}/res/group/get_res_group_json">
						<!-- 部门树 -->
					</ul>
				</dd>
				<input type="hidden" name="resGroupId" id="resGroupId" />
		   </dl> --%>
		   <select class="sel_a" name="resGroupId" id="resGroupId"></select>
		</div>
		<div class='w-a clearfix' >
			<label class="fl_l"  for="">回收后资源分组：</label>
			<%-- <dl class="select resGroup" style="width:210px;" data-input="[name='newResGroupId']">
				<dt>资源分组</dt>
				<dd>
					<ul class="resource-group-tree second-group-tree" data-url="${ctx}/res/group/get_res_group_json">
						<!-- 部门树 -->
					</ul>
				</dd>
				<input type="hidden" name="newResGroupId" id="newResGroupId" />
		   </dl> --%>
		   <select class="sel_a" name="newResGroupId" id="newResGroupId"></select>
		</div>
	
	
	
	<div class='w-a clearfix' >
		<label class='lab_a fl_l' >联系状态：</label>
		<select class='sel_a area_w fl_l' name="concatStatus" id="concatStatus">
		   <option value="">--全部联系--</option>
		   <option value="1">--已联系--</option>
		   <option value="2">--未联系--</option>
	   </select>
	</div>
	<div class="bomb-btn project-comp-idialog-btnbox" style="padding-bottom:15px;">
			<label class="bomb-btn-cen">
				<a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="###" id="recylebtn"><label>确定</label></a>
				<a class="com-btna bomp-btna cancel-btn fl_l"  href="###" id="cancel"><label>取消</label></a>
			</label>
		</div>
	</form>
<script type="text/javascript">
$(function(){
	var isSubmit = true ;
    $("input:text:first").focus();

	$('#recyleSumid').keyup(function(){
		var recyleSumid = $.trim($(this).val());
		if(recyleSumid == '' || isNaN(recyleSumid) || parseInt(recyleSumid)!= recyleSumid || parseInt(recyleSumid)<=0){
			window.top.iDialogMsg("提示", '请输入正整数！');
			return ;
		}
	 });
	$("#recylebtn").click(function(){
		if(!isSubmit){
			isSubmit = true;
			return ;
		}
		isSubmit = false ;
	     //1 当回收条数改变，则触发事件2，限制不能超过一百条
	       var recyleSumid=Number($.trim($("#recyleSumid").val()));
		   if(recyleSumid == '' || isNaN(recyleSumid) || parseInt(recyleSumid)!= recyleSumid || parseInt(recyleSumid)<=0){
			   window.top.iDialogMsg("提示", '请输入正整数！');
			   isSubmit = true ;
			   return;
		   }else{
			       ajaxLoading("正在回收，请稍后") ;
	    		   recyleResource(isSubmit);
	    	   }
	       });
	
	$("#cancel").click(function(){
		parent.closePubDivDialogIframe("resRecyle");
	});
	
	$.ajax({
		url: ctx+"/res/group/get_res_group_json",
		type: "get",
		success: function(data){
			var appendCode = "";
			for(var i=0;i<data.length;i++){
				var _this_data = data[i];
				var _this_data_child = _this_data.children;
				appendCode += '<optgroup label="'+_this_data.text+'" data-id="'+_this_data.id+'">';
				if(_this_data_child.length > 0){
					for(var j=0;j<_this_data_child.length;j++){
						appendCode += '<option value="'+_this_data_child[j].id+'">'+_this_data_child[j].text+'</option>';
					}
				}
				appendCode += '</optgroup>';
			}
			var res_code = '<option value="">全部</option>' + appendCode;
			var new_res_code = '<option value="">原资源组</option>' + appendCode;
			$("#resGroupId").html(res_code);
			$("#newResGroupId").html(new_res_code);
		},
		error: function(){
			console.error("加载资源分组失败");
		}
	});
});

	
//回收资源
function recyleResource(isSubmit){
	var staffAccs=$('#staffAccs').val();
	 var recyleSumid=Number($.trim($("#recyleSumid").val()));
	 var resGroupId = $('#resGroupId').val();
	 var url ='${ctx}/res/staffMg/recyleRes';
	 var taskId = $("#taskId").val();
// 	 window.top.iDialogMsg("提示", '请等待，回收中。。。');
	// $('#recylebtn').attr('readonly','readonly');
	 $.ajax({
		url : url,
		type : 'post',
		data: {
			"staffAccs": staffAccs,
			"recyleSumid": recyleSumid,
			"resGroupId": resGroupId,
			"newResGroupId": $("#newResGroupId").val(),
			"concatStatus": $('#concatStatus').val(),
			"taskId":taskId
		},
		error : function(XMLHttpRequest, textStatus){},
		success : function(data) {
			if(data == '0'){
				$('#p').progressbar({
					    value: 100
					});
				window.top.iDialogMsg("提示", '回收提交成功！');
				setTimeout('window.parent.frames[0].document.forms[0].submit();', 1000);
				setTimeout('parent.closePubDivDialogIframe("resRecyle")',1000);
			}else{
				if('0005'==data2){
					window.top.iDialogMsg("提示", data.msg);
				}else if('0001'==data2){
					window.top.iDialogMsg("提示", '没有找到相应数据！');
				}
				else if('1111'==data2){
					window.top.iDialogMsg("提示", '系统异常！');
				}
				else{
					window.top.iDialogMsg("提示", '操作失败，请稍后再试！');
				}
			}
			isSubmit = true;
		}
	});
	setTravl(taskId,3000);
}
//进度条轮询函数
function setTravl(taskId,time){
	var content = '<div class="finished-prograss">处理中...需处理总数<span class="total">0</span>,当前完成<span class="finished">0</span></div><div id="p" style="width:200px;"></div>';
	var idialogId = "process_idialog";
	$.dialog({
		title:"处理中",
		id:idialogId,
		width:250,
		height:135,
		lock:true,
		content:content,
		show:function(){
			$('#p').progressbar({
			    value: 0
			});
		}
	})
	var t=setInterval(function(){
		$.ajax({
			url:ctx+"/progress/getProgress",
			data:{
				taskId:taskId
			},
			success:function(data){
				console.log(data+new Date().getTime())
		    	console.log(data)
		    	if(data){
		    		if(data.status){
			    		var percent = (data.finished/ data.total).toFixed(2)*100;
			    		$('#p').progressbar({
						    value: percent
						});
			    		$(".finished-prograss>.total").text(data.total);
			    		$(".finished-prograss>.finished").text(data.finished);
			    		if(percent >= 100){//完成 成功
			    			$(".finished-prograss").html("操作成功！共操作"+data.total+"条数据");
			    			clearInterval(t);
			    			setTimeout(function(){
			    				closeIdialog(idialogId)
			    	    	},100)
			    		}
			    	}else{//失败
			    		clearInterval(t);
			    		$('#p').progressbar({
						    value: 0
						});
			    		$(".finished-prograss").html(data.errorMsg)
			    		setTimeout(function(){
			    				closeIdialog(idialogId)
			    	    	},100)
			    	}
		    	}
			}
		});
	},time)
}
function closeIdialog(id){
	$.dialog.get[id].remove();
}
</script>		
</body>