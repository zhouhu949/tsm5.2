<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/common/include-cut-version.jsp"%>
	    <title>订单导出</title>
	    <style>
	    	.output-head {
				height: 30px;
				line-height: 30px;
				font-size: 14px;
				background-color: #F5F6F7;
				padding-left: 20px;
	    	}
	    	.output-content {
	    		padding-left: 20px;
	    		padding-bottom: 35px;
	    	}
	    	.output-content input[type=checkbox] {
			    vertical-align: middle;
			    margin-right: 2px;
			}
	    	.output-checkbox {
				display: inline-block;
			    width: 160px;
			    line-height: 24px;
	    	}
	    	.output-foot {
	    		position: fixed;
	    		width: 100%;
	    		bottom: 5px;
	    		text-align: center;
	    	}
	    </style>
	</head>
	<body>
		<div class="output-head">导出字段：</div>
		<div class="output-content">
			<div class="output-checkbox"><label><input type="checkbox" checked="checked" name="output_name" value="tradeDate|交易日期"/>交易日期</label></div>
			<div class="output-checkbox"><label><input type="checkbox" checked="checked" name="output_name" value="custName|${user.isState eq 1 ? '客户名称':'客户姓名' }"/>${user.isState eq 1 ? '客户名称':'客户姓名' } </label></div>
			<c:if test="${user.isState eq 0 }">
				<div class="output-checkbox"><label><input type="checkbox" checked="checked" name="output_name" value="company|单位名称"/>单位名称</label></div>
			</c:if>
			<div class="output-checkbox"><label><input type="checkbox" checked="checked" name="output_name" value="code|订单号"/>订单号</label></div>
			<div class="output-checkbox"><label><input type="checkbox" checked="checked" name="output_name" value="money|交易金额(元)"/>交易金额(元)</label></div>
			<div class="output-checkbox"><label><input type="checkbox" checked="checked" name="output_name" value="userName|提交人"/>提交人</label></div>
			<div class="output-checkbox"><label><input type="checkbox" checked="checked" name="output_name" value="saleAcc|所有者"/>所有者</label></div>
			<div class="output-checkbox"><label><input type="checkbox" checked="checked" name="output_name" value="authState|订单状态"/>订单状态</label></div>
			<div class="output-checkbox"><label><input type="checkbox" checked="checked" name="output_name" value="courierNumber|物流单号"/>物流单号</label></div>
			<div class="output-checkbox"><label><input type="checkbox" checked="checked" name="output_name" value="courierStatus|物流状态"/>物流状态</label></div>
			<div class="output-checkbox"><label><input type="checkbox" checked="checked" name="output_name" value="effectiveDate|生效日期"/>生效日期</label></div>
			<div class="output-checkbox"><label><input type="checkbox" checked="checked" name="output_name" value="invalidDate|失效日期"/>失效日期</label></div>
			<div class="output-checkbox"><label><input type="checkbox" checked="checked" name="output_name" value="authDesc|审核备注"/>审核备注</label></div>
			<c:if test="${user.isState eq 1 }">
				<div class="output-checkbox"><label><input type="checkbox" checked="checked" name="output_name" value="linkName|联系人"/>联系人</label></div>
			</c:if>
			<div class="output-checkbox"><label><input type="checkbox" checked="checked" name="output_name" value="productNames|产品信息"/>产品信息</label></div>
			<div class="output-checkbox"><label><input type="checkbox" checked="checked" name="output_name" value="linkAddress|联系地址"/>联系地址</label></div>
		</div>
		<div class="output-foot">
			<button type="button" class="button-mid">确定导出</button>
		</div>
		<script type="text/javascript">
			$(function(){
				$(".button-mid").click(function(){
					var exportParams = [];
					$("input[name='output_name']:checked").each(function(index,obj){
						exportParams.push($(obj).val());
					});
					if(exportParams.length == 0){
						window.top.iDialogMsg("提示",'请选择要导出字段！');
						return;
					}
			    	window.parent.pubDivDialog("confirm_delete_order","您确定要导出当前条件下的订单数据吗？",function(){
			    		var param = window.parent.$("#orderManageForm").serialize();
			    		window.parent.location.href=ctx+"/contract/order/export/data?exportParams="+exportParams.join('$')+"&"+param;
			    		closeParentPubDivDialogIframe('export_order');
			    	});
				});
			});
		</script>
	</body>
</html>
