<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <style type="text/css"></style>
    <%@ include file="/common/include.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
    <%@ page import="com.qftx.base.shiro.ShiroUtil" %>
	<% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
    <script type="text/javascript">
		var account = '${shrioUser.account}';
		var orgId = '${shrioUser.orgId}';
		var userId = '${shrioUser.id}';
		var tsmUpLoadServiceUrl = '${tsmUpLoadServiceUrl}'; // <!-- 上传服务器路径 -->
		$(function(){
			//下载附件
			$("a[id^=download_]").live("click",function(){
				var fileId = $(this).attr("id").split("_")[1];
				var form=$("<form>");//定义一个form表单
                var iframe=$('<iframe name="hideIframe" style="display:none"></iframe>')
				form.attr("style","display:none");
				form.attr("target","hideIframe");
				form.attr("method","post");
				form.attr("action",tsmUpLoadServiceUrl+ctx + "/fileupload/download");
				var input1=$("<input>");
				input1.attr("type","hidden");
				input1.attr("name","fileId");
				input1.attr("value",fileId);
				var input2=$("<input>");
				input2.attr("type","hidden");
				input2.attr("name","orgId");
				input2.attr("value",orgId);
				$("body").append(iframe);
				$("body").append(form);//将表单放置在web中
				form.append(input1);
				form.append(input2);
				form.submit();//表单提交 
			});
			
			//打电话
		    $("a[id^=call_]").live('click',function(){
		    	var phone = $(this).attr("id").split("_")[1];
		    	var custId = $(this).attr("custId");
		    	var custName = $(this).attr("custName");
		    	var custType = $(this).attr("custType");
		    	var custState = $(this).attr("custState");
		    	var define1 = $(this).attr("define1");
		    	var lastOptionId = $(this).attr("lastOptionId");
		    	var lastOptionName = $(this).attr("lastOptionName");
		    	var define3 = $(this).attr("define3");
		    	var arrays = new Array();
		    	arrays.push("\"custId\":\""+custId+"\"");
		    	arrays.push("\"custName\":\""+custName+"\"");
		    	arrays.push("\"custType\":\""+custType+"\"");
		    	arrays.push("\"custState\":\""+custState+"\"");
		    	arrays.push("\"define1\":\""+define1+"\"");
		    	if(lastOptionId != null && lastOptionId != ''){
		    		arrays.push("\"saleProcessId\":\""+lastOptionId+"\"");
		    		arrays.push("\"saleProcessName\":\""+lastOptionName+"\"");
		    	}
		    	if(define3 != null && define3 != ''){
		    		arrays.push("\"define3\":\""+define3+"\"");
		    	}
		    	window.top.custCallPhone(phone,arrays,custId);
		    });
			
		});
	</script>
</head>
<body>
	<div class='bomp-cen bomp-oad'>
		<div class='bomp_tit bomp_tit_a'><label class='lab'>订单基本信息</label></div>
		<div class="bomp-box">
			<p class='bomp-p'>
				<label class='lab_a fl_l'>订单编号：</label><label class="lab_b fl_l">${orderBean.code }</label>
			</p>
			<p class='bomp-p'>
				<label class='lab_a fl_l'>交易日期：</label><label class="lab_b fl_l"><fmt:formatDate value="${orderBean.tradeDate }" pattern="yyyy-MM-dd"/></label>
			</p>
			<p class='bomp-p'>
				<label class='lab_a fl_l'>付款方式：</label>
				<label class="lab_b fl_l">
					<c:choose>
						<c:when test="${orderBean.payType eq 1 }">现金</c:when>
						<c:when test="${orderBean.payType eq 2 }">银行转账/汇款</c:when>
						<c:when test="${orderBean.payType eq 3 }">在线支付</c:when>
						<c:when test="${orderBean.payType eq 4 }">支付宝转账</c:when>
					</c:choose>
				</label>
			</p>
		</div>
		<div class="bomp-box">
			<p class='bomp-p'>
				<label class='lab_a fl_l'>合同编号：</label><label class="lab_b fl_l">${cDto.code }</label>
			</p>
			<p class='bomp-p'>
				<label class='lab_a fl_l'>合同名称：</label><label class="lab_b fl_l">${cDto.contractName }</label>
			</p>
			<p class='bomp-p'>
				<label class='lab_a fl_l'>${empty filedMap['name'] ? '客户名称' : filedMap['name'] }：</label><label class="lab_b fl_l">${orderBean.custName }</label>
			</p>
		</div>
		<div class="bomp-box">
			<p class='bomp-p'>
				<label class='lab_a fl_l'>生效日期：</label><label class="lab_b fl_l"><fmt:formatDate value="${orderBean.effectiveDate }" pattern="yyyy-MM-dd" /></label>
			</p>
			<p class='bomp-p'>
				<label class='lab_a fl_l'>失效日期：</label><label class="lab_b fl_l"><fmt:formatDate value="${orderBean.invalidDate }" pattern="yyyy-MM-dd" /></label>
			</p>
			<p class='bomp-p'>
				<label class='lab_a fl_l'>订单金额：</label><label class="lab_b fl_l"><fmt:formatNumber type="number" value="${orderBean.money }" pattern="#,##0.00#" maxFractionDigits="2" />元</label>
			</p>
		</div>
		<div class="bomp-box">
			<p class='bomp-p'>
				<label class='lab_a fl_l'>${shrioUser.isState eq 1 ? '联系人' : '单位名称' }：</label><label class="lab_b fl_l">${shrioUser.isState eq 1 ? orderBean.linkName : orderBean.company }</label>
			</p>
			<p class='bomp-p'>
				<label class='lab_a fl_l'>联系电话：</label>
				<label class="lab_b fl_l">
					${orderBean.linkPhone }
					<c:if test="${!empty orderBean.linkPhone }">
						<a href="javascript:;" <c:if test="${shrioUser.serverDay gt 0}">id="call_${orderBean.linkPhone }" custName="${custInfo.name }" custId="${custInfo.resCustId }" custType="${custInfo.type }" custState="${custInfo.status }" define1="${shrioUser.isState eq 1 ? custInfo.mainLinkman : custInfo.name }" lastOptionId="${custInfo.lastOptionId }"  lastOptionName="${lastOptionName }"</c:if> class="tel icon-phone  ${shrioUser.serverDay gt 0 ? '' : 'img-gray' }" title="拨打电话"></a>
					</c:if>
				</label>
			</p>
			<p class="bomp-p">
				<label class="lab_a fl_l">物流单号：</label><label class="lab_b fl_l">${orderBean.courierNumber }</label>
			</p>
		</div>
		<div class="bomp-box">
			<p class='bomp-p' style="width: 577px;">
				<label class='lab_a fl_l'>联系地址：</label>
				<label class="lab_b fl_l" style="width: 470px !important;">${orderBean.linkAddress }</label>
			</p>
			<p class='bomp-p'>
				<label class='lab_a fl_l'>销售机会：</label>
				<label class="lab_b fl_l">
					<c:forEach items="${saleChances }" var="saleChance">
						<c:if test="${orderBean.salesOppId eq saleChance.saleChanceId }">${saleChance.saleChanceName }</c:if>
                    </c:forEach>
				</label>
			</p>
		</div>
		<div class="bomp-box">
			<p class='bomp-p bomp-oad-w'>
				<label class='lab_a fl_l'>订单说明：</label><label class="lab_b fl_l">${orderBean.context }</label>
			</p>
		</div>
		<div class='bomp-p bomp-p-w' style="width:100%;">
			<label class='lab_a fl_l'>订单附件：</label>
			<ul class="hyx-vc-list fl_l" style="padding:0;width:730px;">
				<c:forEach items="${conFileDtos }" var="conFile">
				<li><label class="name">${conFile.fileName }</label><a id="download_${conFile.fileId }" href="javascript:void(0);" class="link">下载</a><!-- <a href="###" class="link" >预览</a> --></li>
				</c:forEach>
			</ul>
		</div>
		<div class='bomp_tit bomp_tit_a'><label class='lab'>订单配置产品</label></div>
		<div class='com-table bomp-table-a fl_l' style="width:93%;margin-left:30px;">
			<table width='100%' cellspacing='0' cellpadding='0' class='sty-borcolor-a sty-borcolor-c com-table-b com-radius'>
				<thead>
					<tr class='sty-bgcolor-b'>
						<th><span class='sp sty-borcolor-b'>产品名称</span></th>
						<th><span class='sp sty-borcolor-b'>产品型号</span></th>
						<th><span class='sp sty-borcolor-b'>产品规格</span></th>
						<th><span class='sp sty-borcolor-b'>标准价格（元）</span></th>
						<th><span class='sp sty-borcolor-b'>销售价格（元）</span></th>
						<th><span class='sp sty-borcolor-b'>数量</span></th>
						<th><span class='sp sty-borcolor-b'>合计金额（元）</span></th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
							<c:when test="${!empty detailBeans}">
								<c:forEach var="detail" items="${detailBeans }" varStatus="vs">
									<tr>
										<td><div class='overflow_hidden w70' title='${detail.productName }'>${detail.productName }</div></td>
										<td><div class='overflow_hidden w70' title='${detail.productModel }'>${detail.productModel }</div></td>
										<td><div class='overflow_hidden w50' title='${detail.productType }'>${detail.productType }</div></td>
										<td><div class='overflow_hidden w100' title='<fmt:formatNumber type="number" value="${detail.productPrice }" pattern="#,##0.00#" maxFractionDigits="3" />'><fmt:formatNumber type="number" value="${detail.productPrice }" pattern="#,##0.00#" maxFractionDigits="3" /></div></td>
										<td><div class='overflow_hidden w100' title='<fmt:formatNumber type="number" value="${detail.buyPrice }" pattern="#,##0.00#" maxFractionDigits="3" />'><fmt:formatNumber type="number" value="${detail.buyPrice }" pattern="#,##0.00#" maxFractionDigits="3" /></div></td>
										<td><div class='overflow_hidden w50' title='${detail.buyNum }'>${detail.buyNum }</div></td>
										<td><div class='overflow_hidden w120' title='<fmt:formatNumber type="number" value="${detail.buyMoney }" pattern="#,##0.00#" maxFractionDigits="2" />'><fmt:formatNumber type="number" value="${detail.buyMoney }" pattern="#,##0.00#" maxFractionDigits="2" /></div></td>
										<td><div class='overflow_hidden w100' title='${detail.context }'>${detail.context }</div></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="8">
										无订单详细信息!
									</td>
								</tr>
							</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<c:if test="${fn:length(authlogBeans) gt 0 }">
			<div class='bomp_tit bomp_tit_a'><label class='lab'>订单审核信息</label></div>
			<div class='com-table bomp-table-a fl_l' style="width:93%;margin-left:30px;">
				<table width='100%' cellspacing='0' cellpadding='0' class='sty-borcolor-a sty-borcolor-c com-table-b com-radius'>
					<thead>
						<tr class='sty-bgcolor-b'>
							<th><span class='sp sty-borcolor-b'>审核状态</span></th>
							<th><span class='sp sty-borcolor-b'>审核人</span></th>
							<th><span class='sp sty-borcolor-b'>审核时间</span></th>
							<th>审核备注</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${authlogBeans }" var="log">
							<tr>
								<td>
									<div class='overflow_hidden w100'>
										<c:choose>
											<c:when test="${log.authState eq '2' }">通过</c:when>
											<c:when test="${log.authState eq '3' }">驳回</c:when>
										</c:choose>
									</div>
								</td>
								<td><div class='overflow_hidden w100' title='${log.userName }'>${log.userName }</div></td>
								<td><div class='overflow_hidden w120' title='<fmt:formatDate value="${log.inputtime }" pattern="yyyy-MM-dd HH:mm:ss"/>'><fmt:formatDate value="${log.inputtime }" pattern="yyyy-MM-dd"/></div></td>
								<td><div class='overflow_hidden w200' title='${log.context }'>${log.context }</div></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		<div class='bomb-btn'>
			<label class='bomb-btn-cen'>
				<a href="javascript:;" onclick="closeParentPubDivDialogIframe('view_order');" class="com-btna bomp-btna cancel-btn fl_l"><label>关闭</label></a>
			</label>
		</div>
	</div>
</body>
</html>
