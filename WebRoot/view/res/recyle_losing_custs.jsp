<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css"><!--弹框样式-->

<style>
	.lab_a.shows-labs{display:block;text-align:left;width:100%;}
</style>
</head>
<body>
	<div class='bomp-cen' style="width:98%;">
		<input type="hidden" value="0" id="isClean" />
		<input type="hidden" value="0" id="oldResGroup" />
		<div class='bomp-p w-a' style="margin-top:20px;margin-left:15px;">
			<label class='lab_a shows-labs'>回收客户数：<span class="recycleNum">0</span></label>
			<div class='lab_a shows-labs'>回收后资源分组：
				<select class="sel_a" name="resGroupId" id="resGroupId" style="width:150px;"></select>
			</div>
		</div>
		<div class='bomp-p skin-minimal' style="width:235px;margin-left:15px;margin-top:20px;font-size:12px;">
			<input id="cleanCheck" type='checkbox' class='fl_l' /><label class='lab_b fl_l' style="padding-left:2px;">同时清空被选客户的历史联系信息</label>
		</div>
		<div class='bomp-p skin-minimal' style="width:235px;margin-left:15px;margin-top:5px;margin-bottom:10px;font-size:12px;">
			<input id="groupCheck" type='checkbox' class='fl_l' /><label class='lab_b fl_l' style="padding-left:2px;">自动回收到客户原有资源组中</label>
		</div>
 		<p class='bomp_tit_c fl_l' style="width:85%;margin:0px 0 0px 15px;"><label class="bomp-red fl_l" style="display:inline-block;width:100%;height:20px;line-height:20px;">温馨提示：</label><span class="fl_l" style="display:inline-block;width:100%;line-height:20px;">确定后，系统将当前查询条件下的<span class="recycleNum">0</span>条客户自动回收到【待分配资源】中！</span></p>
		<div class='bomb-btn' style="margin-top:35px;">
			<label class='bomb-btn-cen'>
				<a href="javascript:;" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>确定</label></a>
				<a href="javascript:;" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
			</label>
		</div>
	</div>
<script type="text/javascript">
	$(function(){

		$("#cleanCheck").on('ifChecked',function(){
			$("#isClean").val("1");
		});
		
		$("#cleanCheck").on('ifUnchecked',function(){
			$("#isClean").val("0");
		});
		
		$("#groupCheck").on('ifChecked',function(){
			$("#oldResGroup").val("1");
			$("#resGroupId").attr("disabled","disabled");
		});
		
		$("#groupCheck").on('ifUnchecked',function(){
			$("#oldResGroup").val("0");
			$("#resGroupId").removeAttr("disabled");
		});
		
		
		var is_submit = false;
	    $(".sure-btn").click(function(){
	    	ajaxLoading("正在回收，请稍后") ;
	    	if(!is_submit){
	    		is_submit = true;
	    		var _param = window.parent.$('#queryForm').serialize();
	    		var isClean = $("#isClean").val();
		    	var oldResGroup = $("#oldResGroup").val();
		    	var regroupId = $("#resGroupId").val();
		    	_param+="&isClean="+isClean+"&oldResGroup="+oldResGroup+"&regroupId="+regroupId;
		    	$.ajax({
		    		url:ctx+'/res/sea/recyleLosingCust',
		    		type:'post',
		    		data:_param,
		    		dataType:'json',
		    		error:function(){is_submit = false;},
		    		success:function(data){
		    			if(data == '1'){
		    				window.top.iDialogMsg("提示","操作成功！");
							setTimeout('window.parent.loadData();closeParentPubDivDialogIframe("recyle_cust");',1000);
		    			}else if(data == '9'){
		    				window.top.iDialogMsg("提示","没有找到要回收的流失客户！");
		    				setTimeout('window.parent.loadData();closeParentPubDivDialogIframe("recyle_cust");',1000);
		    			}else{
		    				window.top.iDialogMsg("提示","操作失败！");
		    				is_submit = false;
		    			}
		    		}
		    	})
	    	}
		});
		$(".cancel-btn").click(function(){
			closeParentPubDivDialogIframe('recyle_cust');
		});
		
		getNum();
		
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
				$("#resGroupId").html(appendCode);
			},
			error: function(){
				console.error("加载资源分组失败");
			}
		});
	});
	
	function getNum(){
		var _param = window.parent.$('#queryForm').serialize();
		var isClean = $("#isClean").val();
    	var oldResGroup = $("#oldResGroup").val();
    	var regroupId = $("#resGroupId").val();
    	_param+="&isClean="+isClean+"&oldResGroup="+oldResGroup+"&regroupId="+regroupId;
    	$.ajax({
    		url:ctx+'/res/sea/recyleLosingCustNum',
    		type:'post',
    		data:_param,
    		dataType:'json',
    		error:function(){},
    		success:function(data){
    			$(".recycleNum").text(data);
    		}
    	})
	}
	
</script>
</body>
</html>
