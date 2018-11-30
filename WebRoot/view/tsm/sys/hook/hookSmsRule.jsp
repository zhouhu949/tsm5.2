<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
	<%@ include file="/common/include.jsp"%> 
	<title>系统设置-挂机短信规则设置</title>
	<script type="text/javascript">
	$(function(){
		// 挂机短信规则设置
		$("#a").click(function(){
			document.location.href = "${ctx }/sys/hookSms/hookRulePage";
		});
		
		// 挂机短信模板设置
		$("#b").click(function(){
			document.location.href = "${ctx }/sys/hookSms/hookTempPage";
		});
		
		$("#saveButt").click(function(){	
			$("#ruleForm").ajaxSubmit({
				url : '${ctx}/sys/hookSms/updateHookRule.do',
				type : 'post',
				dataType : 'JSON',
				error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
				success : function(data) {		
					if(data == 0){
						window.top.iDialogMsg("提示","设置成功！");
					}else{
						window.top.iDialogMsg("提示","设置失败！");
					}
				}
			});		
		});
		
	});
	

	</script>
</head>
<body>
	<div class="year-sale-play hyx-hsrs-top">
		<ul class="ann-sale-plan clearfix">
			<li class="select li_first" id="a" >挂机短信规则设置</li>
			<li class="li_last" id="b">挂机短信模板设置</li>
		</ul>
	</div>
	<form id="ruleForm" method="post" >
	<div class="hyx-hsrs-bot">
		<div class="hyx-sms hyx-ms hyx-hsrs-a fl_l">
			<p class="tit">
				<label class="lab fl_l"><i class="tip"></i>发送时间设置</label>
			</p>
			<p class="tit_a">
				<label class="lab">发送时间段：</label>
				<select class="sel" name="dictionaryList[0].dictionaryValue">
					<c:forEach begin="0" end="23" var="v">
						<option value="${v}" <c:if test="${v eq dictionaryList[0].dictionaryValue}">selected</c:if>>${v}:00</option>
					</c:forEach>						
				</select>
				<span class="sp"></span>
				<select class="sel" name="dictionaryList[1].dictionaryValue">
					<c:forEach begin="0" end="23" var="v">
						<option value="${v}" <c:if test="${v eq dictionaryList[1].dictionaryValue}">selected</c:if>>${v}:00</option>
					</c:forEach>	
				</select>
			</p>
			<p class="tit_a">
				<label class="lab lab_a">温馨提示：</label>
				<label class="lab lab_b">挂机短信只能在该时间段发送。</label>
			</p>
			<p class="tit">
				<label class="lab fl_l"><i class="tip"></i>发送频率设置</label>
			</p>
			<p class="tit_a">
			<label class="lab">发送频率：</label>
			<select class="sel sel_w" name="dictionaryList[2].dictionaryValue">
				<option value="1" <c:if test="${1 eq dictionaryList[2].dictionaryValue}">selected</c:if>>每次呼入/呼出发送</option>
				<option value="2" <c:if test="${2 eq dictionaryList[2].dictionaryValue}">selected</c:if>>每天仅一次</option>
				<option value="3" <c:if test="${3 eq dictionaryList[2].dictionaryValue}">selected</c:if>>每周仅一次</option>
				<option value="4" <c:if test="${4 eq dictionaryList[2].dictionaryValue}">selected</c:if>>每月仅一次</option>
			</select>
			</p>
			<p class="tit_a">
				<label class="lab lab_a">温馨提示：</label>
				<label class="lab lab_b">针对同一号码重复呼入/呼出发送频率。</label>
			</p>
			<p class="tit">
				<label class="lab fl_l"><i class="tip"></i>发送数量设置</label>
			</p>
			<p class="tit_b">
				<label class="lab">每天最多发送短信</label>
				<input type="text" name="dictionaryList[3].dictionaryValue" value="${dictionaryList[3].dictionaryValue}" maxlength="5" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"  class="ipt" />
				<label class="lab">条。</label>
			</p>
			<p class="tit_a">
				<label class="lab lab_a">温馨提示：</label>
				<label class="lab lab_b">为空时无限制。</label>
			</p>
			<div class="com-btnbox">
				<a href="javascript:;" id="saveButt" class="com-btna com-btna-sty"><label>保存</label></a>
			</div>
			<!-- 隐藏域  -->
		<input type="hidden" name="dictionaryList[0].dictionaryId" value="${dictionaryList[0].dictionaryId}">
		<input type="hidden" name="dictionaryList[1].dictionaryId" value="${dictionaryList[1].dictionaryId}">
		<input type="hidden" name="dictionaryList[2].dictionaryId" value="${dictionaryList[2].dictionaryId}">
		<input type="hidden" name="dictionaryList[3].dictionaryId" value="${dictionaryList[3].dictionaryId}">
		</div>
	</div>
	</form>
</body>
</html>