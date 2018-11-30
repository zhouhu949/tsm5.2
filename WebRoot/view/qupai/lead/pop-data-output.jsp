<%@ page language="java" pageEncoding="UTF-8"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@ include file="/common/common.jsp"%>
		<%@ include file="/common/common-function/function.area.select.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/gray/easyui.css${_v}"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/advancedQuery.css${_v}"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/js/time/css/daterangepicker.css${_v}" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/font-awesome-4.7.0/css/font-awesome.min.css${_v}"/>
		<title>高级查询</title>
		<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
		<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
	</head>

	<body>
		<input type="hidden" id="service_url" value="${service_url}" />
		<form class="submit-form">
			<input type="hidden" id="shiroUser_account" value="${shiroUser.account }" />
			<input type="hidden" id="module" value="${module}">
			<div class="advancedQuery-box" data-show-step="1"></div>
		</form>
		<input type="hidden" id="exportType" value="${exportType}"/>
		<input type="hidden" id="exportSearchContext" value=""/>
		<div class="output-conditions-select" data-show-step="2">
			<div class="conditions-block conditions-company">
				<div class="title advancedQuery-everytitle">企业字段：</div>
				<div class="content"></div>
			</div>
			<div class="conditions-block conditions-contacts">
				<div class="title advancedQuery-everytitle">联系人字段：</div>
				<div class="content">
					<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" data-change="false" value="mainLinkman" checked  />联系人姓名</label></div>
					<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" data-change="false" value="mobilephone" checked  />常用电话</label></div>
					<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" data-change="false" value="telphone" checked  />备用电话</label></div>
				</div>
			</div>
			<div class="conditions-block conditions-call-record">
				<div class="title advancedQuery-everytitle">导出字段：</div>
				<div class="content">
					<c:if test="${shiroUser.isState eq 0 }">
						<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" checked value="custName" />客户姓名</label></div>
						<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" checked value="define3" />单位名称</label></div>
					</c:if>
					<c:if test="${shiroUser.isState eq 1 }">
						<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" checked value="custName" />客户名称</label></div>
					</c:if>
					<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" checked value="define11" />客户状态</label></div>
					<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" checked value="define12" />呼叫类型</label></div>
					<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" data-change="false" checked value="callerNum" />主叫号码</label></div>
					<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" data-change="false" checked value="calledNum" />被叫号码</label></div>
					<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" data-change="false" checked value="define15" />通话时间</label></div>
					<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" checked value="define16" />通话时长</label></div>
					<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" checked value="saleProcessName" />销售进程</label></div>
					<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" checked value="inputName" />联系人</label></div>
					<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" checked value="define13" />通话来源</label></div>
				</div>
			</div>
		</div>
		<div class="output-config" data-show-step="3">
			<div class="conditions-block advanced-conditions">
				<div class="title advancedQuery-everytitle">查询条件：</div>
				<div class="content"></div>
			</div>
			<div class="conditions-block">
				<div class="title advancedQuery-everytitle">查询结果数据条数：<span class="content total-count">0</span></div>
			</div>
			<div class="conditions-block message-code-check">
				<div>
					<label for="" class="label-72px">审核人号码：</label>
					<span class="content-auditor"><span class="phone"></span>（<span class="checkUserName"></span>）</span>
				</div>
				<div>
					<label for="" class="label-72px">验证码：</label>
					<span class="content">
						<input type="text" class="check-code" placeholder="短信验证码"/>
						<span class="check-code-result"></span>
						<button class="btn btn-default btn-get-sms">获取验证码</button>
					</span>
				</div>
				<div>
					<span class="warning label-72px">温馨提示：</span>
					<span class="content">系统将向审核人发送验证码短信，请输入该验证码；验证码${validTime }分钟内有效；</span>
				</div>
			</div>
		</div>
		<form class="download-form" method="post" action="" target="downloadIframe"></form>
		<iframe class="download-iframe" name="downloadIframe" style="display: none;"></iframe>
		<script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/time/moment.min.js"></script><!--选择区域日期插件-->
		<script type="text/javascript" src="${ctx}/static/js/time/jquery.daterangepicker.js${_v}"></script><!--选择区域日期插件-->
		<script type="text/javascript" src="${ctx}/static/thirdparty/easyui/jquery.easyui.min.js${_v}"></script>
		<script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js${_v}"></script><!--可移动弹框插件公共js-->
		<%-- <script type="text/javascript" src="${ctx}/static/js/view/tsm/sys/advancedQuery.js${_v}"></script> --%>
		<script type="text/javascript" src="${ctx}/static/js/view/data-output/pop-data-output.js${_v}"></script>
		<script type="text/javascript" src="${ctx }/static/js/common/search_input_tree.js${_v}"></script>
		<script type="text/javascript">
			$(function(){
				if($("#module").val() == "call_record"){//判断是否是通话记录导出
					$(".conditions-contacts").remove();
					$(".conditions-company").remove();
				}else{
					$(".conditions-call-record").remove();
					var _conditions_url = ctx + "/export/exportFields";
					$.ajax({
						type:"get",
						url:_conditions_url,
						async:true,
						success: function(data){
							if(data.isState == 0){
								$(".conditions-contacts").remove();
							}else if(data.isState == 1){
								$(".conditions-contacts").show();
							}
							if(data.success){
								var _fields = data.fields;
								var appendCode = "";
								for(var i=0;i<_fields.length;i++){
									if(data.isState==1 && _fields[i].fieldCode == "name"){
										appendCode += '<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" data-change="false" checked value="'+_fields[i].fieldCode+'" />'+_fields[i].fieldName+'</label></div>';
									}else if(data.isState==0 && (_fields[i].fieldCode == "name" || _fields[i].fieldCode == "company" || _fields[i].fieldCode == "mobilephone" || _fields[i].fieldCode == "telphone")){
										appendCode += '<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" data-change="false" checked value="'+_fields[i].fieldCode+'" />'+_fields[i].fieldName+'</label></div>';
									}else{
										appendCode += '<div class="output-checkbox"><label><input type="checkbox" name="exportColumns" value="'+_fields[i].fieldCode+'" />'+_fields[i].fieldName+'</label></div>';
									}
								}
								$(".conditions-company .content").html(appendCode);
							}
						},
						error: function(error){}
					});
				}
				
				$.ajax({
					url: ctx+"/export/exportPhoneCheck",
					type: "get",
					success: function(data){
						if(data.success && data.isCheckPhone){
							$(".content-auditor .phone").text(data.phone);
							$(".content-auditor .checkUserName").text(data.checkUserName);
							btnGetSms(data.phone);
						}
					},
					error: function(error){
						alert(error.msg);
					}
				});
				
				$(".conditions-block").delegate("input[data-change='false']","click",function(e){
					return false;
				});
				
				function btnGetSms(phone){
					$(".btn-get-sms").on("click",function(e){
						var that = this;
						var _this = $(this);
						var totalCount = _this.parents(".output-config").find(".content.total-count").text();
						if(totalCount == 0){
							window.top.iDialogMsg("提示","导出数据为0条！");
							return false;
						}
						$.ajax({
							url: ctx + "/export/sendSmsCheckCode?exportType=${exportType}",
							type: "get",
							success: function(data){
								if(data.success){
									numstart(that);
									window.top.iDialogMsg("提示","信息已发送！");
								}else{
									window.top.iDialogMsg("提示",data.msg);
								}
							},
							error: function(error){
								alert(error.msg);
							}
						});
					});
				}
				
				//验证码倒计时函数
				function numstart(btnele){
					var clock = '';
					var nums = 60;
					var btn;
					sendCode(btnele);
					function sendCode(btnele){
					  btn = btnele;
					  $(btn).attr("disabled",true); //将按钮置为不可点击
					  $(btn).text("重新发送("+nums+")s");
					  clock = setInterval(doLoop, 1000); //一秒执行一次
					}
					function doLoop(){
						  nums--;
						  if(nums > 0){
						   $(btn).text("重新发送("+nums+")s");
						  }else{
						   clearInterval(clock); //清除js定时器
						   $(btn).attr("disabled",false); 
						   $(btn).text('获取验证码');
						   nums = 10; //重置时间
						  }
					}
				}
				
				function smsCodeCheck(code){
					$.ajax({
						url: ctx + "/export/smsCodeCheck",
						type: "get",
						data:{"code":code},
						success: function(data){
							if(data.success){
								$(".check-code-result").text("true");
							}else{
								$(".check-code-result").text("false");
							}
						},
						error: function(error){
							alert(error.msg);
						}
					});
				}
			});
		</script>
		
	</body>
</html>
