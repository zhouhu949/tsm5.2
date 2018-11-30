<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<!--公共样式-->
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"> --%>
<!--主要样式-->
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/static/js/idialog/skins/iblue.css"> --%>
<script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/res/resourceCheck.js${_v}"></script>
<style>
.cust-impo-res .select dt {width:168px;background: #fff url(${ctx}/static/images/drop-down-arrow.png) no-repeat 380px center;box-shadow: 0 0 3px #e1e1e1;height: 25px; display: inline-block;border: 1px solid #e1e1e1;line-height: 25px;font-weight: bold;padding-left: 10px;cursor: pointer;padding-right: 12px;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;position: relative; z-index: 99;}
body .com-search{width:auto;}
.com-search label:not(.tree-node-label){display: inline-block;width: 100px;height: 27px;line-height: 27px;text-align: right;}
.select.resGroup dd{max-height:140px;width:397px;}
.form{
    width:600px;
    margin: 0 auto;
    padding:0 20px;
}

</style>
<script type="text/javascript">
	var isSubmit = true;
	$(function() {

	$("#chooseCount").blur(function(){
        var number = ${allChooseCount};
            this.value=this.value.replace(/\D/g, '');
            var $small = 0;
            if(Number($('#ressum').html())<=number){
                $small=Number($('#ressum').html());
            }else{
                $small=number;
            }
            if(Number($('#chooseCount').val())>Number($small)){
                $("#prompt").remove();
                $("#check").append('<div id="prompt" style="color:red;margin-left:100px;">最大可筛查资源数量为'+$small+'</div>')
                isSubmit = false;
            }else{
                $("#prompt").remove();
                isSubmit = true;
            }
        });

		$("input").each(function(item, obj) {
			if ($(obj).attr("disabled") != "disabled") {
				$(obj).focus();
			}
		})

		$('#submitId').click(function(e) {
			e.preventDefault();
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
			assignRes();
		})
		$('#clearId').click(function() {
			parent.closePubDivDialogIframe("start_screen");
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
		var resGroupId = $('#resGroupId').val();
		var keyWord = $('#keyWord').val();
		$.ajax({ //获取下拉改资源组下资源条数
			url : '/phone/getNoChooseCount',
			type : 'post',
			data: {
				groupId: resGroupId,
				keyWord: keyWord
			},
			error : function(XMLHttpRequest, textStatus) {
				window.top.iDialogMsg("提示", '请求失败！');
			},
			success : function(data) {
				if (data.code == '-1') {
					window.top.iDialogMsg("提示", data.msg);
					return;
				} else {
					$('#ressum').text(data.noChooseCount);
				}
			}
		});
	}

	//分配资源 -->
	function assignRes() {
		var resGroupId = $('#resGroupId').val();
		$.ajax({
			url : '${ctx}/phone/saveChoose',
			type : 'post',
			data: {
				groupId: resGroupId,
				chooseCount: $('#chooseCount').val(),
				keyWord: $('#keyWord').val()
			},
			error : function(XMLHttpRequest, textStatus) {
			},
			success : function(data) {
				ajaxLoadEnd()
				if(data.code == -1){
					window.top.iDialogMsg("提示", data.msg);
					isSubmit = true;
				}else{
					isSubmit = true;
					window.top.iDialogMsg("提示", "提交成功");
					parent.closePubDivDialogIframe("start_screen");
				}

			}
		});
		// setTravl(taskId,3000);
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
	<form method="post" id="myForm1" name="myForm1" class="form">
		<input type="hidden" id="keyWord" name="keyWord" value="${keyWord}" />
		<div class="cust-impo-res" style="width: auto; padding-left: 0;">
			<div class="com-search clearfix" style="min-height: 30px; z-index: 999">
				<label class="fl_l" for=""><span class="font-color" style="width: auto;">*</span>资源分组：</label>
				<dl class="select resGroup" data-input="[name='resGroupId']" >
					<dt style="width:380px;height:30px;">资源分组</dt>
					<dd>
						<ul style="width:380px" class="resource-group-tree" data-url="${ctx}/res/group/get_res_group_json">
							<!-- 部门树 -->
						</ul>
					</dd>
			 		<input name="resGroupId" id="resGroupId" type="hidden" value=""  checkProp="chk_1_1" />
					<span class="error" name='a' id="error_resGroupId" style="padding-left:0;"></span>				
			   	</dl>	
			</div>
			<div style="padding-left:100px">共<span class="font-color" style="width: auto" id="ressum">0</span>条未筛查资源</div>
		</div>

		<div class="com-search clearfix " id="check" style="min-height: 30px;">
			<label class="fl_l" for=""><span class="font-color" style="width: auto;">*</span>筛查条数：</label><input autocomplete="off" type="text" style="width: 404px; height:30px;" id="chooseCount" onkeyup="this.value=this.value.replace(/\D/g, '');" >
		</div>
		<div style="padding-left:100px;">当前帐号剩余<span class="product-currencyUnit">蜂豆</span>数：${fd}<span class="product-currencyUnit">蜂豆</span>。最大可筛查${allChooseCount}条资源</div>
		<p style="margin: 20px 0 0 40px; z-index: -1">
			<label for=""> <div class="font-color" style="width: auto; font-size: 14px;padding-left:100px;">温馨提示：</div><span style="padding-left:100px;display:inline-block;width:400px;">资源筛查利用大数据帮助用户智能筛选号码状态，可将号码分类为正常、停机、沉默（经常没使用的号码）、空号。智能筛查将消耗<span class="product-currencyUnit">蜂豆</span>。</span>
			</label>
		</p>
		<div class="bomb-btn" style="margin-top: 80px">
			<label class="bomb-btn-cen"> <a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="###" id="submitId"><label>确定</label></a> <a class="com-btna bomp-btna cancel-btn fl_l" href="###"
				id="clearId"><label>取消</label></a>
			</label>
		</div>
	</form>
</body>
