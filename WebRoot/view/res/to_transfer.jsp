<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" href="${ctx }/static/js/view/res/cust_contect.css">
<script type="text/javascript" src="${ctx }/static/js/view/res/to_sale_transfer.js"></script>
<script type="text/javascript" src="${ctx}/static/js/idialog/loading.js"></script>
<style type="text/css">
	.tree-title input{float:left;margin-top:9px;}
</style>
<script type="text/javascript">
	var step = 1;
	$(function(){
		$("#custNum").html(window.parent.$('input[id^=chk_]:checked').length);
		var is_submit = false;
	    $(".true-click").click(function(){
	    	if(!is_submit){
	    		is_submit = true;    		
		    	var rd = $("input[name=radioBtn]:checked");
		    	if(rd.length == 0){
		    		window.top.iDialogMsg("提示","请选分配对象!");
		    		is_submit = false;
		    		return;
		    	}else{
		    	ajaxLoading("正在交接，请稍后") ;
		    		var ids = '';
		    		$.each(window.parent.$('input[id^=chk_]:checked'),function(index,obj){
		    			ids+=$(obj).attr('id').split('_')[1]+'_'+$(obj).attr('custType')+'_'+$(obj).attr('ownerAcc')+',';
		    		});
		    		ids=ids.substring(0,ids.length-1);
		    		var ownerName = rd.attr("ownerName");
		    		var ownerAcc = rd.val();
		    		var reason = $("#reason").val();
		    		$.ajax({
		    			url:ctx+'/res/cust/custTransfer',
		    			type:'post',
		    			data:{ids:ids,ownerName:ownerName,ownerAcc:ownerAcc,reason:reason},
		    			dataType:'json',
		    			error:function(){is_submit = false;},
		    			success:function(data){
		    				if(data == '1'){
		    					window.top.iDialogMsg("提示","交接成功!");
		    					setTimeout('window.parent.loadData();closeParentPubDivDialogIframe("transfer");',1000);
		    				}else{
		    					window.top.iDialogMsg("提示","交接失败!");
		    					is_submit = false;
		    				}
		    				ajaxLoadEnd();
		    			}
		    		})
		    	}
	    	}
		});
		$(".cancle-click").click(function(){
			closeParentPubDivDialogIframe('transfer');
		});
	})
</script>
	
</head>
<body>
	<div class="show-box">
		<div class="shows-idialog-top">
			<div class="shows-input-box">
				<span>总共选中<i id="custNum">0</i>条记录</span>
				<input type="text" id="searchText">
				<button id="searchUser">查询</button>
				<span class="placehold">输入帐号/姓名</span>
			</div>
		</div>
		<div class="shows-idialog-middle">
		</div>
		<div class="shows-idialog-bottom">
			<div class="shows-reason-box">
				<span class="out-resson">交接原因</span>
				<textarea id="reason"></textarea>
			</div>
			<button class="true-click prev-click">确定</button>
			<button class="cancle-click">取消</button>
		</div>
	</div>
</body>
</html>
