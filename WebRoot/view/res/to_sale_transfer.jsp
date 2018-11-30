<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE>
<html>
<head>
<%@include file="/common/include.jsp"%>
<link rel="stylesheet" href="${ctx }/static/js/view/res/cust_contect.css">
<script type="text/javascript" src="${ctx }/static/js/view/res/to_sale_transfer.js"></script>
<script type="text/javascript" src="${ctx}/static/js/idialog/loading.js"></script>
<%-- <script type="text/javascript" src="${ctx }/static/js/view/res/cust_contect_radio.js"></script> --%>
<%-- <script type="text/javascript" src="${ctx }/static/js/view/res/swap.js"></script> --%>
<script>
	$(function(){
		var shows_idialog_bottom=$(".shows-idialog-bottom");
		var next_tip=$(".next-tip");
		var true_click=$(".true-click");
		//下一步
		next_tip.on("click",function(e){
			var rd = $("#moveAcc").val();
	    	if(rd==null || rd.length == 0){
	    		window.top.iDialogMsg("提示","请选分配对象!");
	    	}else{
	    		e.stopPropagation();
	    		shows_idialog_bottom.eq(0).hide();
	    		shows_idialog_bottom.eq(1).show();
	    		step++;
	    		checkRadio();
	    		custDeliveryAllNum(rd,$("#custType").val());
	    		$(".shows-middle-title").html("选择接收人");
	    		initUser(null);
	    	}
		})
		//上一步
		true_click.on("click",function(e){
			e.stopPropagation();
			shows_idialog_bottom.eq(1).hide();
			shows_idialog_bottom.eq(0).show();
			step--;
			checkRadio();
			$(".shows-middle-title").html("选择移交人");
			initUser(null);
		})
		
		//获取交接客户数量
		function custDeliveryAllNum(ownerAcc,custType){
			$.post(ctx+"/res/cust/custDeliveryAllNum",{ownerAcc:ownerAcc,custType:custType},function(data){
		  		$(".access-obj-num").text(data);
			}, "json");
		}
		
		$("body").delegate("input[name=radioBtn]","click",function(){
			if(step == 0){
				$("#moveAcc").val($(this).val());
				$(".xs-name").text($(this).attr("ownerName"));
			}else{
				$("#recAcc").val($(this).val());
				$(".access-obj").text($(this).attr("ownerName"));
			}
		});
		//var rd = $("input[name=radioBtn]:checked");
		
	    $(".confirm").click(function(){
	    	var ownerAcc = $("#moveAcc").val();
	    	var moveToAcc = $("#recAcc").val();
	    	var ownerName = $($(".xs-name")[0]).text();
	    	var recName = $(".access-obj").text();
	    	var custType = $("#custType").val();
	    	var reason = $("#reason").val();
	    	if(ownerAcc == null || ownerAcc == ''){
	    		window.top.iDialogMsg("提示","请先选择移交人!");
	    		return;
	    	}
	    	if(moveToAcc == null || moveToAcc == ''){
	    		window.top.iDialogMsg("提示","请先选择接收人!");
	    		return;
	    	}
	    	if(ownerAcc == moveToAcc){
	    		window.top.iDialogMsg("提示","提交人与接收人不能为同一个人!");
	    		return;
	    	}
	    	window.parent.pubDivDialog("sale_trans_ok",getSureMsg(ownerName,recName,custType)+"，是否确认进行交接？",function(){
		    	ajaxLoading("正在交接，请稍后") ;
		    	$.ajax({
		    		url:ctx+'/res/cust/custDeliveryAll',
		    		type:'post',
		    		data:{ownerAcc:ownerAcc,ownerName:ownerName,moveToAcc:moveToAcc,custType:custType,reason:reason},
		    		dataType:'json',
		    		error:function(){},
		    		success:function(data){
		    			if(data == '1'){
		    				window.top.iDialogMsg("提示","客户交接成功!");
		    				setTimeout('window.parent.loadData();closeParentPubDivDialogIframe("sale_transfer");',1000);
		    			}else if(data == '9'){
		    				window.top.iDialogMsg("提示","没有找到要交接的资源或客户!");
		    			}else if(data == '10'){
		    				window.top.iDialogMsg("提示","接收人资源数超过上限!");
		    			}else{
		    				window.top.iDialogMsg("提示","客户交接失败!");
		    			}
		    			ajaxLoadEnd();
		    		}
		    	});
	    	});
		});
		$(".cancle-click").click(function(){
			closeParentPubDivDialogIframe('sale_transfer');
		});
		
		function getSureMsg(fname,tname,type){
			var msg = "交接后“"+fname+"”的";
			if(type == ""){
				msg+="全部客户将会移交给“"+tname+"”";
			}else if(type == "1"){
				msg+="全部资源将会移交给“"+tname+"”";
			}else if(type == "2"){
				msg+="全部意向客户将会移交给“"+tname+"”";
			}else if(type == "3"){
				msg+="全部签约客户将会移交给“"+tname+"”";
			}else if(type == "4"){
				msg+="全部沉默客户将会移交给“"+tname+"”";
			}else if(type == "5"){
				msg+="全部流失客户将会移交给“"+tname+"”";
			}
			return msg;
		}
	});
</script>
</head>
<body>
<input type="hidden" value="" id="moveAcc">
<input type="hidden" value="" id="recAcc">
	<div class="show-box">
		<div class="shows-idialog-top">
			<div class="shows-input-box">
				<input id="searchText" type="text" class="cust-resson">
				<button id="searchUser" class="cust-resson">查询</button>
				<span class="placehold1">输入帐号/姓名</span>
			</div>
		</div>
		<div class="shows-idialog-middle">
			<div class="shows-middle-title">选择移交人</div>
		</div>
		<div class="shows-idialog-bottom">
			<div class="shows-reason-box">
				<div class="shows-reason-box-part">
					<span class="cust-out-xs">移交人：</span>
					<span class="xs-name"></span>
				</div>
				<div class="shows-reason-box-part" style="margin-left:50px;width:40%;">
					<span class="cust-states">客户状态：</span>
					<select id="custType" class="cust-sele-state">
						<option value="">全部客户</option>
						<option value="1">资源</option>
						<option value="2">意向客户</option>
						<option value="3">签约客户</option>
						<option value="4">沉默客户</option>
						<option value="5">流失客户</option>
					</select>
				</div>
			</div>
			<button class="next-tip">下一步</button>
		</div>
		<div class="shows-idialog-bottom" style="display:none;">
			<div class="shows-reason-box-1">
				<span class="out-resson">交接原因</span>
				<textarea id="reason"></textarea>
			</div>
			<div class="shows-three-reason-box">
				<div class="shows-three-reason-part">
					<span class="cust-out-xs">移交人：</span>
					<span class="xs-name"></span>
				</div>
				<div class="shows-three-reason-part">
					<span class="cust-out-xs">接收人：</span>
					<span class="access-obj"></span>
				</div>
				<div class="shows-three-reason-part">
					<span class="cust-out-xs">交接客户数：</span>
					<span class="access-obj-num"></span><span>个</span>
				</div>
			</div>
			<button class="true-click">上一步</button>
			<button class="confirm">提交</button>
			<button class="cancle-click">取消</button>
		</div>
	</div>
</body>
</html>
