<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<!--公共样式-->
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"> --%>
<!--主要样式-->
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/static/js/idialog/skins/iblue.css"> --%>
<script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/resourceCheck.js${_v}"></script>
<style>
.cust-impo-res .select dt {width:168px;background: #fff url(${ctx}/static/images/drop-down-arrow.png) no-repeat 174px center;box-shadow: 0 0 3px #e1e1e1;height: 25px; display: inline-block;border: 1px solid #e1e1e1;line-height: 25px;font-weight: bold;padding-left: 10px;cursor: pointer;padding-right: 12px;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;position: relative; z-index: 99;}
body .com-search{width:auto;}
.com-search label:not(.tree-node-label){display: inline-block;width: 100px;height: 27px;line-height: 27px;text-align: right;}
.select.resGroup dd{max-height:140px;width:190px;}
</style>
<script type="text/javascript">
	$(function() {
		$("input").each(function(item, obj) {
			if ($(obj).attr("disabled") != "disabled") {
				$(obj).focus();
			}
		})

		var isSubmit = true;
		$('#submitId').click(function() {
			if (!isSubmit) {
				return;
			}
			isSubmit = false;
			if ($('#resGroupId').val() == null || $('#resGroupId').val() == '') {
				window.top.iDialogMsg("提示", '请选择资源分组！');
				isSubmit = true;
				return;
			}
			ajaxLoading("正在分配，请稍后") ;
			assignRes(isSubmit);
		})
		$('#clearId').click(function() {
			parent.closePubDivDialogIframe("resAssign");
		});
		
		$(".resGroup .resource-group-tree").each(function(){
			var _this = $(this);
			var resGroup = _this.parents(".resGroup");
			var selectedNode = resGroup.data("selected-node");
			var input = resGroup.data("input");
			$.ajax({
				url: _this.attr("data-url"),
				type: "get",
				success: function(data){
	//				console.log(data);
					
	//				var render_data = [{
	//					"id":"",
	//					"text":"资源分组",
	//					"type":"none",
	//					"level":"none",
	//					"isSharePool":"none",
	//					"inputAcc":"none",
	//					"children":data,
	//				}];
					var first_source = {
						"id":"",
						"text":"资源分组",
						"type":"none",
						"level":"none",
						"isSharePool":"none",
						"inputAcc":"none"
					};
					
					data.splice(0,0,first_source);
					
					_this.tree({
						data: data,
						formatter: function(node){
							var nodeText = "<label class='tree-node-label' data-id='"+node.id+"' data-share-pool='"+node.isSharePool+"'>" + node.text + "</label>";
							return nodeText;
						},
						onBeforeSelect:function(node){
							if(node.level != 1 && node.level != "none"){
								return false;
							}
						},
						onSelect:function(node){
							resGroup.find(input).val(node.id);
							resGroup.find("dt").text(node.text);
							resGroup.find("dd").hide();
							$('#resGroupId').val(node.id);
							getResAssginSum();
						},
						onLoadSuccess: function(node,data){
							if(selectedNode && selectedNode!=""){
								_this.tree("select",_this.tree("find",selectedNode).target);
							}
						}
					});
				},
				error: function(status){
					alert("分组加载失败！");
				}
			});
		});
	});

	//选中资源分组和获取相应资源
	function selectGroupId(groupId) {
		$('#resGroupId').val(groupId);
		getResAssginSum();
	}
	
	function selectDate(dateStr,id){
		if ($('#resGroupId').val() == null || $('#resGroupId').val() == '') {
	    	$('#'+id).val('');
			window.top.iDialogMsg("提示", '请选择资源分组！');
			return;
		}
		$('#'+id).val(dateStr);
		getResAssginSum();
	}
	function getResAssginSum() {
		var staffLen = $('#staffLen').text();
		if (staffLen == '' || staffLen == null) {
			window.top.iDialogMsg("提示", '请选择员工！');
			return;
		}
		var starttime = $('#startDate').val();
		var endtime = $('#endDate').val();
		var resGroupId = $('#resGroupId').val();
		$.ajax({ //获取下拉改资源组下资源条数 
			url : 'getResourceSum?resGroupId=' + resGroupId + '&starttime=' + starttime + '&endtime=' + endtime,
			type : 'post',
			error : function(XMLHttpRequest, textStatus) {
				window.top.iDialogMsg("提示", '请求失败！');
			},
			success : function(data) {
				if (data == '-3') {
					window.top.iDialogMsg("提示", '系统异常！');
					return;
				} else {
					var yu = parseInt(Number(data) / staffLen);//最大可以分配几条每人; 
					$("#every_sum").val(yu);
					$("#ressum").html(data);
					$("#resNumId").val(data);
				}
			}
		});
	}

	//分配资源 -->
	function assignRes(isSubmit) {
		var staffAccs = $('#staffAccs').val();
		var inputAcc = $('#inputAcc').val();
		var resGroupId = $('#resGroupId').val();
		var starttime = $('#startDate').val();
		var endtime = $('#endDate').val();
		var taskId = $("#taskId").val();
		if ($("#every_sum").val() == null || $("#every_sum").val() == '') {
			$("#every_sum").val('0');
		}
		var plansum = $.trim($("#every_sum").val());
		var every_sum = $.trim($("#every_sum").val());
		if ($('#isLimtis').val() == null || $('#isLimtis').val() == '') {
			$('#isLimtis').val('0');
		}
		var $isLimtis = $('#isLimtis').val();
		var limitIsOpen = $('#limitIsOpen').val();
		var realAssgin = every_sum * staffAccs.split(',').length;
/* 		if (realAssgin > $isLimtis) {
			window.top.iDialogMsg("提示", '员工拥有资源已达上限，请重新分配！');
			isSubmit = true;
			return;
		} */
		var acc_nums = staffAccs.split(',');
		var num = 0;
		var accs = '';
		var account = '';
		for (var i = 0; i < acc_nums.length; i++) {
			account = acc_nums[i].split('_')[0];
			accs = accs + account + ',';
			num = acc_nums[i].split('_')[1];
			/* if (parseInt(num) >= parseInt($isLimtis) && limitIsOpen == '1') {
				window.top.iDialogMsg("提示", '员工 '+account+'帐号 拥有资源已达上限，请重新分配！');
				isSubmit = true;
				return false;
			} */
		}
		$.ajax({
			url : '${ctx}/res/staffMg/getAssginResource?resGroupId=' + resGroupId + '&starttime=' + starttime + '&endtime=' + endtime + '&plansum=' + plansum + '&staffAccs=' + accs + '&every_sum='
					+ every_sum + '&inputAcc=' + inputAcc + '&resNum=' + $('#resNumId').val() + '&taskId=' + taskId,
			type : 'post',
			dataType : 'json',
			error : function(XMLHttpRequest, textStatus) {
			},
			success : function(data) {
				var data2 = data.result;
				var limit = data.limit;
				if (data2 == '0') {
					if (limit == '1') {
						setTimeout(window.top.iDialogMsg("提示", "分配资源提交成功,部分员工已达拥有资源上限！"), 1500);
					} else {
						$('#p').progressbar({
						    value: 100
						});
						window.top.iDialogMsg("提示", "分配资源提交成功");
					}
					setTimeout(' window.parent.frames[0].document.forms[0].submit();', 100);
					setTimeout('parent.closePubDivDialogIframe("resAssign")', 100);
				} else {
					if ('0005' == data2) {
						window.top.iDialogMsg("提示", data.msg);
					} else if ('0001' == data2) {
						window.top.iDialogMsg("提示", '可分配资源不够分配！');
					} else if ('0002' == data2) {
						window.top.iDialogMsg("提示", '没有找到相应数据！');
					} else if ('1111' == data2) {
						window.top.iDialogMsg("提示", '系统异常！');
					} else if ('0003' == data2) {
						var content = '<p style="font-size:13px">有员工资源已达上限（' + $isLimtis + '）人,超出上限的资源不分配！</p>';
						window.top.iDialogMsg("提示", content);

					} else {
						window.top.iDialogMsg("提示", "操作失败，请稍后再试！");
					}
					setTimeout('window.parent.frames[0].document.forms[0].submit();', 50);
					setTimeout('parent.closePubDivDialogIframe("resAssign")', 100);
				}
				isSubmit = true;
				ajaxLoadEnd()
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
<body>
<input type="hidden" id="taskId" value="${taskId}" /> 
	<form method="post" id="myForm1" name="myForm1">
		<input type="hidden" id="ctPath" value="${ctx}" /> 
		<input type="hidden" id="staffAccs" value="${staffAccs}" /> 
		<input type="hidden" id="inputAcc" value="${inputAcc}" /> 
		<input type="hidden" id="isLimtis" value="${isLimtis}" />
	    <input type="hidden" id="limitIsOpen" value="${limitIsOpen}" /> 
	    <input type="hidden" id="resNumId" value="" /> 
		<div class="cust-impo-res" style="width: auto; padding-left: 0;">
			<div class="com-search clearfix" style="min-height: 30px; z-index: 999">
				<label class="fl_l" for=""><span class="font-color" style="width: auto;">*</span>资源分组：</label>
				<dl class="select resGroup" data-input="[name='resGroupId']" >
					<dt>资源分组</dt>
					<dd>
						<ul class="resource-group-tree" data-url="${ctx}/res/group/get_res_group_json">
							<!-- 部门树 -->
						</ul>
					</dd>
			 		<input name="resGroupId" id="resGroupId" type="hidden" value=""  checkProp="chk_1_1" />
					<span class="error" name='a' id="error_resGroupId" style="padding-left:0;"></span>				
			   	</dl>	
			</div>
			<div class="com-search clearfix" style="min-height: 30px;">
				<label class="fl_l" for="">导入时间：</label>
				 <span style="position: relative; display: inline-block">
				   <input style="height:27px;" type="text" value="" name="startDate" onclick="WdatePicker({onpicked:function(dq){selectDate(dq.cal.getNewDateStr(),'startDate');},dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}'})" id="startDate" class="ipt"/>
				   <input style="height:27px;" type="text" value="" name="endDate" onclick="WdatePicker({onpicked:function(dq){selectDate(dq.cal.getNewDateStr(),'endDate');},dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})" id="endDate" class="ipt"/>
				</span>
			</div>
		</div>
		<div class="com-search clearfix" style="min-height: 27px; z-index: -1;line-height:27px;">
			<label class="fl_l" for="">选择人数：</label> <span class="empl-reso-span" id="staffLen">${staffLen }</span>
		</div>
		<div class="com-search clearfix" style="min-height: 30px;">
			<label class="fl_l" for="">分配条数：</label><input type="text" style="width: 190px;" id="every_sum" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" >&nbsp;条/人
		</div>
		<p style="margin: 20px 0 0 40px; z-index: -1">
			<label for=""> <span class="font-color" style="width: auto; font-size: 14px;">温馨提示：</span>最大可分配资源：<span class="font-color" style="width: auto" id="ressum">0</span>条。
			</label>
		</p>
		<!-- <p class="warn-deco"><span clas="font-color">分组名称不能为空</span></p> -->
		<div class="bomb-btn" style="margin-top: 80px">
			<label class="bomb-btn-cen"> <a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="###" id="submitId"><label>确定</label></a> <a class="com-btna bomp-btna cancel-btn fl_l" href="###"
				id="clearId"><label>取消</label></a>
			</label>
		</div>
	</form>
</body>
