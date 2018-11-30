<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css"><!--弹框样式-->
<script type="text/javascript">
	$(function(){
		/*表单优化*/

	    $("#tt1").tree({
			url:ctx+"/orgGroup/get_group_user_json",
			formatter:function(node){
				var txt = node.text;
				if(node.attributes.type == 'M'){
					txt = '<label><input type="radio" ownerName="'+txt+'" name="radioBtn" value="'+node.id+'"/>'+txt+'</label>';
				}
				return txt;
			},
			onLoadSuccess:function(node,data){
				$(this).tree("expandAll");
			}
		});
	    
	    $("#cleanCheck").on("ifChecked",function(){
	    	$("#isClean").val("1");
	    });
	    
	    $("#cleanCheck").on("ifUnchecked",function(){
	    	$("#isClean").val("0");
	    });
		var is_submit = false;
	    $(".sure-btn").click(function(){
	    	ajaxLoading("正在分配，请稍后") ;
	    	if(!is_submit){
	    		is_submit = true;
		    	var rd = $("input[name=radioBtn]:checked");
		    	if(rd.length == 0){
		    		ajaxLoadEnd();
		    		window.top.iDialogMsg("提示","请先选择接收人!");
		    		is_submit = false;
		    		return;
		    	}else{
		    		var ids = $("#custIds").val();
		    		var ownerName = rd.attr("ownerName");
		    		var ownerAcc = rd.val();
		    		var isClean = $("#isClean").val();
		    		$.ajax({
		    			url:ctx+'/res/sea/getAgainAssginCust',
		    			type:'post',
		    			data:{ids:ids,ownerName:ownerName,ownerAcc:ownerAcc,isClean:isClean,module:'${module}',poolType:'${poolType}'},
		    			dataType:'json',
		    			error:function(){is_submit = false;ajaxLoadEnd();},
		    			success:function(data){
		    				ajaxLoadEnd();
		    				if(data.flag){
		    					window.top.iDialogMsg("提示",data.msg);
		    					setTimeout('window.parent.loadData();closeParentPubDivDialogIframe("distribute");',1000);
		    				}else{
		    					is_submit = false;
		    					window.top.iDialogMsg("提示",data.msg);
		    				}
		    			}
		    		})
		    	}
	    	}
		});
		$(".cancel-btn").click(function(){
			closeParentPubDivDialogIframe('distribute');
		});
	})
</script>
</head>
<body>
	<input type="hidden" id="custIds" value="${ids }" />
	<input type="hidden" id="isClean" value="0" /> 
	<p class="sele-hb-grou"><label for="">选择接收人：</label></p>
	<div class="change-group" >
		<div class="reso-deall"style="overflow-y:hidden;">
				 <ul id="tt1" class="easyui-tree"   data-options="dnd:false">
			            
			     </ul>
		</div>
	</div>
	<div class='bomp-p skin-minimal' style="width:250px;margin-left:15px;">
		<input id="cleanCheck" type='checkbox' class='fl_l' /><label class='lab_b fl_l' style="padding-left:2px;">同时清空被选客户的历史联系信息</label>
	</div>
	<div class="bomb-btn">
		<label class="bomb-btn-cen">
			<a class="com-btna bomp-btna com-btna-sty sure-btn fl_l" href="javascript:;"><label>确定</label></a>
			<a class="com-btna bomp-btna cancel-btn fl_l" href="javascript:;"><label>取消</label></a>
		</label>
	</div>
</body>
</html>
