<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>物流查询</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ include file="/common/common.jsp"%>
		<style type="text/css">
			*{
				margin: 0;
				padding: 0;
			}
			
			ul,li{
				list-style: none;
			}
			
			.head{
				text-align: center;
			}
			
			.time-line  {
				position: relative;
				margin-left: 120px;
			}
			
			.time-line .line-li-point{
				width: 10px;
				height: 10px;
				border: 1px solid #cdcdcd;
				border-radius: 50%;
				background-color: #cdcdcd;
			}
			
			.time-line .line-li-start-point{
				position: absolute;
				top: -10px;
				left: -6px;
			}
			
			.time-line .line-li-end-point{
				position: absolute;
				bottom: -2px;
				left: -6px;
			}
			
			.time-line .line-li-block {
				min-height: 80px;
				border-left: 1px solid #cdcdcd;
			}
			
			.time-line .time-line-block{
				position: relative;
			}
			.time-line ul.time-line-block li{
				display: inline-block;
			}
			.time-line ul.time-line-block .time-node{
				width: 10px;
				height: 10px;
				border: 1px solid #cdcdcd;
				border-radius: 50%;
				background-color: #fff;
				position: absolute;
				left: -7px;
				top: 24px;
			}
			
			.time-line-block .time{
				position: absolute;
				left: -120px;
				top: 15px;
				width: 120px;
				text-align: center;
			}
			
			.time-line-block .content{
				line-height: 24px;
				padding-left: 10px;
				padding-top: 19px;
			}
			
			.time .year-mon-day{
				display: block;
			}
		</style>
	</head>
	<body>
		<input type="hidden" name="courierNumber" value="${courierNumber }" />
		<input type="hidden" name="orderId" value="${orderId }" />
		<div class="head">
			<div class="head-number">
				<span>物流单号：</span>
				<span class="content"></span>
			</div>
			<div class="head-owner">
				<span>承运方：</span>
				<span class="content"></span>
			</div>
		</div>
		<div class="content">
			<ul class="time-line">
				<li class="line-li-point line-li-start-point"></li>
				<li class="line-li-point line-li-end-point"></li>
			</ul>
		</div>
<script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js"></script>
<%@ include file="/common/common-function/function.handlebar.jsp"%>
<script type="text/x-handlebars-template" id="template">
	<li class="line-li-point line-li-start-point"></li>
	{{#each data}}
		<li class="line-li-block">
			<ul class="time-line-block">
				<li class="time-node"></li>
				<li class="time">
					<span class="year-mon-day">{{time}}</span>
					<span class="hour-min"></span>
					<span class="weekday"></span>
				</li>
				<li class="content">{{context}}</li>
			</ul>
		</li>
	{{/each}}
	<li class="line-li-point line-li-end-point"></li>
</script>
<script type="text/javascript">
	function renderInfo(data){
		var myTemplate = Handlebars.compile($("#template").html());
		$(".content>.time-line").html(myTemplate(data));
	}
	$(function(){
		var courierNumber = $("input[name=courierNumber]").val();
		var orderId = $("input[name=orderId]").val();
		var url = ctx+"/courier/data?courierNumber="+courierNumber+"&orderId="+orderId;
		$.ajax({
			type:"get",
			url:url,
			success: function(result){
				if(result.state == 1){
					$(".head-number .content").text(result.courierNumber);
					$(".head-owner .content").text(result.expTextName);
					renderInfo(result);
				}else if(result.state == -1){
					alert(result.msg);
				}
			},
			error: function(){}
		});
	});
</script>
	</body>
</html>
