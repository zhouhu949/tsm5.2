<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <style type="text/css"></style>
    <%@ include file="/common/include.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
    <style>
		.swfupload{display:inline-block;float:left;width:200px;height:20px;line-height:20px;border:solid #d6d6d6 1px;}
		.chooseFiles{padding: 2px 10px;width:158px;height: 20px;line-height: 20px;position: relative;cursor: pointer;color: #545454;background: #fafafa;border: 1px solid #ddd;overflow: hidden;display: inline-block;*display: inline;*zoom: 1}
		.chooseFiles:hover{text-decoration:none;}
		#files{position: absolute; font-size: 100px; right: 0; top: 0; opacity: 0; filter: alpha(opacity=0); cursor: pointer ;width:200px;}
		#files:hover{color: #444; background: #eee; border-color: #ccc; text-decoration: none ;}
		.bomp-box-margin-top10{margin-top:10px;}
		.bomp-oad .bomp-p{position:relative;height:50px;margin-top:0;}
		.bomp-oad .bomp-oad-w{height:auto;}
		.bomp-oad .bomp-p-w{width:100%;}
		.bomp-p .ipt_a{box-sizing:border-box;}
		.bomp-oad .bomp-p .error{postiiton:absolute;left:100px;bottom:0;font-size:12px;}
		.bomp-nca .bomp-nca-comp{position:absolute;}
	</style>
    <script type="text/javascript" src="${ctx }/static/js/view/contract/contract_check.js"></script>
    <%@ page import="com.qftx.base.shiro.ShiroUtil" %>
	<% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
    <script type="text/javascript">
		var account = '${shrioUser.account}';
		var orgId = '${shrioUser.orgId}';
		var userId = '${shrioUser.id}';
		var tsmUpLoadServiceUrl = '${tsmUpLoadServiceUrl}'; // <!-- 上传服务器路径 -->
		var totalUploaded, fileCount, filesUploaded;

		//接收人文件上传
		function onFileSelect(e) {
			var files = e.target.files; // FileList object
			fileCount = files.length;
          for (var i=0; i<fileCount; i++) {
              var file = files[i];
              var names=file.name;
              var sarr=names.split(".");
              var lastOne=sarr.pop();
              var Mbs=file.size/(1024*1024);
				if(Mbs > 10 ){
					alert("文件大小超过限制！");
					return false;
				}
            if(lastOne !== "jpg" && lastOne !== "jpeg" && lastOne !== "png" && lastOne !== "pdf" && lastOne !== "docx" && lastOne !== "doc"){
              	alert("只允许上传word、pdf、jpg、png格式的文件！");
              	return false;
              }
          }
			startUpload();
		}

		function onUploadFailed(e) {
			alert("Error uploading file");
		}

		function uploadNext() {
		 	var url = tsmUpLoadServiceUrl+ctx + '/fileupload/upload';
			var xhr = new XMLHttpRequest();
			var fd = new FormData();
			var file = document.getElementById('files').files[filesUploaded];
			fd.append("file", file);
			fd.append("type", "2");
			fd.append("account", account);
			fd.append("orgId", orgId);
			fd.append("userId", userId);
			xhr.addEventListener("error", onUploadFailed, false);
			xhr.open("POST", url);
			xhr.onreadystatechange = function() {
				if (xhr.readyState == 4 && xhr.status == 200) {
					var txt = xhr.responseText;
					var data = JSON.parse(txt);
					if (data.code == '0') {
						window.top.iDialogMsg("提示", data.message);
						return;
					}
					if (data == null || data == 'undefined') {
						return;
					}
					if (typeof (data.status) != 'undefined') {
						if (data.status == 'success') {
							var fileIds = $("#fileIdsStr").val();
							$("#fileIdsStr").val(fileIds + data.fileId + ","); // 获取附件ID
							$("#fileList")
									.append(
											"<li id=\"file_li_"
													+ data.fileId
													+ "\"><label class=\"name\">"
													+ data.fileName
													+ "</label><a href=\"###\" onclick=\"delfile('"
													+ data.fileId
													+ "')\" class=\"link\">删除</a></li>");
						}
					}
				}
			};
			xhr.send(fd);
		}
		function startUpload() {
			totalUploaded = filesUploaded = 0;
			uploadNext();
		}
		window.onload = function() {
			document.getElementById('files').addEventListener('change',
					onFileSelect, false);//接收人上传文件
		};
	</script>
    <script type="text/javascript" src="${ctx}/static/js/view/rest/ajaxfileupload.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/view/contract/order_add.js"></script>
</head>
<body>
	<input type="hidden" id="fromPage" value="${fromPage }" />
	<form id="cform" action="${ctx }/contract/order/saveEdit" method="post">
		<input type="hidden" id="orderId" name="orderBean.id" value="${orderBean.id }"/>
		<div class='bomp-cen bomp-oad bomp-nca' style="padding-bottom:0 !important;width:99%;">
			<div class='bomp_tit bomp_tit_a'><label class='lab'>订单基本信息</label></div>
			<div class="bomp-box bomp-box-margin-top10">
				<p class='bomp-p'>
					<label class='lab_a fl_l'><label class="bomp-red">*</label>订单编号：</label><input type='text' checkPub="chk_1_1_10" name="orderBean.code" maxlength="50" value='${orderBean.code }' class='ipt_a fl_l' />
					<span class="error"></span>
				</p>
				<p class='bomp-p'>
					<label class='lab_a fl_l'><label class="bomp-red">*</label>交易日期：</label><input type='text' checkPub="chk_1_1_0" name="orderBean.tradeDate" onclick="WdatePicker({maxDate:'%y-%M-%d'})" value='<fmt:formatDate value="${orderBean.tradeDate }" pattern="yyyy-MM-dd" />' class='ipt_a bomp-date fl_l' id="d1" />
					<span class="error"></span>
				</p>
				<p class='bomp-p'>
					<label class='lab_a fl_l'>付款方式：</label>
					<select name="orderBean.payType" class='sel_a fl_l'>
						<option value="">请选择</option>
	                    <option value="1" ${orderBean.payType eq 1 ? 'selected' : '' }>现金</option>
	                    <option value="2" ${orderBean.payType eq 2 ? 'selected' : '' }>银行转账/汇款</option>
	                    <option value="3" ${orderBean.payType eq 3 ? 'selected' : '' }>在线支付</option>
	                    <option value="4" ${orderBean.payType eq 4 ? 'selected' : '' }>支付宝转账</option>
					</select>
				</p>
			</div>
			<div class="bomp-box">
				<p class='bomp-p'>
					<input type="hidden" id="caid" name="orderBean.caId" value="${orderBean.caId }" />
					<label class='lab_a fl_l'>合同编号：</label>
					<!-- 签约客户新增订单 -->
					<select id="contractSele" class='sel_a fl_l'>
						<option value="">请选择</option>
						<c:forEach items="${contracts }" var="con">
							<option ${con.id eq cDto.id ? 'selected' : '' } caId="${con.id }" caName="${con.contractName }" value="${con.code }">${con.code }<c:if test="${!empty con.contractName }">(${con.contractName })</c:if></option>
						</c:forEach>
					</select>
				</p>
				<p class='bomp-p'>
					<label class='lab_a fl_l'>合同名称：</label><input id="caName" type='text' readonly="readonly" value='${cDto.contractName }' class='ipt_a fl_l' />
				</p>
				<p class='bomp-p'>
					<input type="hidden" name="orderBean.custId" value="${orderBean.custId }" />
					<label class='lab_a fl_l'>${empty filedMap['name'] ? '客户名称' : filedMap['name'] }：</label><input type='text' readonly="readonly" name="orderBean.custName" value='${orderBean.custName }' class='ipt_a fl_l' />
				</p>
			</div>
			<div class="bomp-box">
				<p class='bomp-p'>
					<label class='lab_a fl_l'>生效日期：</label><input type='text' name="orderBean.effectiveDate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'d3\')}'})" value='<fmt:formatDate value="${orderBean.effectiveDate }" pattern="yyyy-MM-dd" />' class='ipt_a bomp-date fl_l' id="d2" />
				</p>
				<p class='bomp-p'>
					<label class='lab_a fl_l'>失效日期：</label><input type='text' name="orderBean.invalidDate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'d2\')||\'%y-%M-%d\'}'})" value='<fmt:formatDate value="${orderBean.invalidDate }" pattern="yyyy-MM-dd" />' class='ipt_a bomp-date fl_l' id="d3" />
				</p>
				<p class='bomp-p' style="width:298px;">
					<label class='lab_a fl_l'>订单金额：</label><input type='text' id="totalMoney" readonly="readonly" checkPub="chk_1_0_3" name="orderBean.money" value='<fmt:formatNumber type="number" value="${orderBean.money }" pattern="0.00" maxFractionDigits="2" />' class='ipt_a fl_l' /><span class="bomp-nca-comp fl_l">元</span>
					<span class="error"></span>
				</p>
			</div>
			<div class="bomp-box">
				<p class='bomp-p'>
					<c:choose>
						<c:when test="${shrioUser.isState eq 1 }">
							<label class='lab_a fl_l'>联系人：</label><input readonly="readonly" type='text' name="orderBean.linkName" value="${orderBean.linkName }"  class='ipt_a fl_l'  />
						</c:when>
						<c:otherwise>
							<label class='lab_a fl_l'>单位名称：</label><input readonly="readonly" type='text' name="orderBean.company" value="${orderBean.company }"  class='ipt_a fl_l'  />
						</c:otherwise>
					</c:choose>
				</p>
				<p class='bomp-p'>
					<label class='lab_a fl_l'>联系电话：</label><input readonly="readonly" type='text' name="orderBean.linkPhone" value="${orderBean.linkPhone }"  class='ipt_a fl_l'  />
				</p>
				<p class='bomp-p'>
					<label class='lab_a fl_l'>物流单号：</label><input type='text' maxlength="20" checkPub="chk_1_0_0" name="orderBean.courierNumber" value='${orderBean.courierNumber }' class='ipt_a fl_l' />
					<span class="error"></span>
				</p>
			</div>
			
			<div class="bomp-box">
				<p class='bomp-p' style="width: 577px;">
					<label class='lab_a fl_l'>联系地址：</label><input readonly="readonly" type='text' name="orderBean.linkAddress" value="${orderBean.linkAddress }" class='ipt_a fl_l' style="width: 470px !important;" />
				</p>
				<p class='bomp-p'>
					<label class='lab_a fl_l'>销售机会：</label>
					<select name="orderBean.salesOppId"  class='sel_a fl_l'>
						<option value="">请选择</option>
	                    <c:forEach items="${saleChances }" var="saleChance">
	                    	<option value="${saleChance.saleChanceId }" ${orderBean.salesOppId eq saleChance.saleChanceId ? 'selected' : '' }>${saleChance.saleChanceName }</option>
	                    </c:forEach>
					</select>
				</p>
			</div>
			
			<div class="bomp-box">
				<p class='bomp-p bomp-oad-w'>
					<label class='lab_a fl_l'>订单说明：</label><textarea name="orderBean.context" maxlength="200" checkPub="chk_1_0_0" class='area_a fl_l'>${orderBean.context }</textarea>
					<span class="error"></span>
				</p>
			</div>
			
			<div class="bomp-box bomp-box-margin-top10">
				<p class='bomp-p bomp-p-w'>
					<input type="hidden" id="fileIdsStr" name="fileIdsStr" value=""/>
					<input type="hidden" id="delFileIdsStr" name="delFileIdsStr" value=""/>
					<label class='lab_a fl_l'>订单附件：</label>
					<span class="file_box fl_l">
						 <a href="javascript:;" class="chooseFiles">上传附件
					 			<input type="file" id="files" />
				 		</a>
					</span>
					<span class="bomp-tip fl_l" style="padding-left:15px;"><label class="com-red">提示：</label>允许上传word、pdf、jpg、png格式的文件，文件上传的大小不能超过10M。</span>
					<!-- 上传进度条 -->
					<div id="divFileProgressContainer" style="padding:2px;margin-top:8px;margin-left:94px;"></div>
					<ul id="fileList" class="hyx-vc-list fl_l">
						<c:forEach items="${conFileDtos }" var="conFile">
							<li id="file_up_${conFile.id }"><label class="name">${conFile.fileName }</label><a href="javascript:void(0);" id="download_${conFile.fileId }" class="link">下载</a><a href="javascript:void(0);" id="delfile_${conFile.id }" class="link">删除</a></li>
						</c:forEach>
					</ul>
				</p>
			</div>
			
			<select style="display: none;" id="pmode">
				<option value="">-请选择-</option>
				<c:forEach var="pro" items="${products }">
					<option model="${pro.model }" ptype="${pro.type }" price="<fmt:formatNumber type="number" value="${pro.price }" pattern="0.000" maxFractionDigits="3" />" units="${pro.units }" value="${pro.id }">${pro.name }</option>
				</c:forEach>
			</select>
			<div class='bomp_tit bomp_tit_a'><label class='lab'>订单配置产品</label></div>
			<div class="com-table bomp_sps_table fl_l" style="width:96%;padding:15px 0 0 30px;">
				<input type="hidden" value="${fn:length(detailBeans)-1 }" id="dlength" />
				<input type="hidden" name="delDetailIdsStr" id="delDetailIdsStr" value="" />
				<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
					<thead>
						<tr class="sty-bgcolor-b">
							<th><span class="sp sty-borcolor-b">产品名称</span></th>
							<th><span class="sp sty-borcolor-b">产品型号</span></th>
							<th><span class="sp sty-borcolor-b">产品规格</span></th>
							<th><span class="sp sty-borcolor-b">标准价格（元）</span></th>
							<th><span class="sp sty-borcolor-b">销售价格（元）</span></th>
							<th><span class="sp sty-borcolor-b">数量</span></th>
							<th><span class="sp sty-borcolor-b">合计金额（元）</span></th>
							<th><span class="sp sty-borcolor-b">备注</span></th>
							<th><a href="javascript:;" id="addDetailBtn" class="add alert_sys_pro_set_d_a" title="添加"></a></th>
						</tr>
					</thead>
					<tbody id="pds">              
						<c:choose>
							<c:when test="${!empty detailBeans}">
								<c:forEach var="detail" items="${detailBeans }" varStatus="vs">
									<tr id="pro_detail_${vs.index }">
										<td>
											<div class="overflow_hidden">
												<input type="hidden" id="detail_id_${vs.index }" name="orderDetailBeans[${vs.index }].id" value="${detail.id }"/>
												<input type="hidden" id="pro_name_${vs.index }" name="orderDetailBeans[${vs.index }].productName" value="${detail.productName }"/>
												<select id="pro_id_${vs.index }" name="orderDetailBeans[${vs.index }].productId" class="ipt_bor ipt_w80">
													<option value="">-请选择-</option>
													<c:forEach var="pro" items="${products }">
														<option model="${pro.model }" ptype="${pro.type }" price="<fmt:formatNumber type="number" value="${pro.price }" pattern="0.00" maxFractionDigits="2" />" units="${pro.units }" value="${pro.id }" ${pro.id eq detail.productId ? 'selected' : ''}>${pro.name }</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td>
											<div class="overflow_hidden">
												<input type="text" readonly="readonly" name="orderDetailBeans[${vs.index }].productModel" class="ipt_bor w70" id="pro_model_${vs.index }" value="${detail.productModel }">
											</div>
										</td>
										<td>
											<div class="overflow_hidden">
												<input type="text" readonly="readonly" name="orderDetailBeans[${vs.index }].productType" class="ipt_bor w70" id="pro_type_${vs.index }" value="${detail.productType }">
											</div>
										</td>
										<td>
											<div class="overflow_hidden">
												<input type="text" readonly="readonly" name="orderDetailBeans[${vs.index }].productPrice" class="ipt_bor w70" id="pro_price_${vs.index }" value="<fmt:formatNumber type="number" value="${detail.productPrice }" pattern="0.000" maxFractionDigits="3" />">
											</div>
										</td>
										<td>
											<div class="overflow_hidden">
												<input type="text" id="pro_buyprice_${vs.index }" name="orderDetailBeans[${vs.index }].buyPrice" value="<fmt:formatNumber type="number" value="${detail.buyPrice }" pattern="0.000" maxFractionDigits="3" />" class="ipt_bor ipt_w100" />
											</div>
										</td>
										<td>
											<div class="overflow_hidden">
												<input type="text" id="pro_buynum_${vs.index }" name="orderDetailBeans[${vs.index }].buyNum" value="${detail.buyNum }" class="ipt_bor ipt_w50" />
											</div>
										</td>
										<td>
											<div class="overflow_hidden">
												<input type="text" id="pro_buymoney_${vs.index }" name="orderDetailBeans[${vs.index }].buyMoney" value="<fmt:formatNumber type="number" value="${detail.buyMoney }" pattern="0.00" maxFractionDigits="2" />" class="ipt_bor ipt_w100" />
											</div>
										</td>
										<td>
											<div class="overflow_hidden">
												<input type="text" id="pro_context_${vs.index }" name="orderDetailBeans[${vs.index }].context" value="${detail.context }" class="ipt_bor ipt_w100" />
											</div>
										</td>
										<td style="width:30px;">
											<div class="overflow_hidden_01 w30 link" >
												<a href="javascript:;" id="pro_del_${vs.index }" class="del alert_sys_pro_set_d_a" title="删除"></a>
											</div>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr id="pro_detail_0">
									<td>
										<div class="overflow_hidden">
											<input type="hidden" id="pro_name_0" name="orderDetailBeans[0].productName" value=""/>
											<select id="pro_id_0" name="orderDetailBeans[0].productId" class="ipt_bor ipt_w80">
												<option value="">-请选择-</option>
												<c:forEach var="pro" items="${products }">
													<option model="${pro.model }" ptype="${pro.type }" price="<fmt:formatNumber type="number" value="${pro.price }" pattern="0.00" maxFractionDigits="2" />" units="${pro.units }" value="${pro.id }">${pro.name }</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td>
										<div class="overflow_hidden">
											<input type="text" readonly="readonly" name="orderDetailBeans[0].productModel" class="ipt_bor w70" id="pro_model_0" value="">
										</div>
									</td>
									<td>
										<div class="overflow_hidden">
											<input type="text" readonly="readonly" name="orderDetailBeans[0].productType" class="ipt_bor w70" id="pro_type_0" value="">
										</div>
									</td>
									<td>
										<div class="overflow_hidden">
											<input type="text" readonly="readonly" name="orderDetailBeans[0].productPrice" class="ipt_bor w70" id="pro_price_0" value="">
										</div>
									</td>
									<td>
										<div class="overflow_hidden">
											<input type="text" id="pro_buyprice_0" name="orderDetailBeans[0].buyPrice" value="" class="ipt_bor w100" />
										</div>
									</td>
									<td>
										<div class="overflow_hidden">
											<input type="text" id="pro_buynum_0" name="orderDetailBeans[0].buyNum" value="" class="ipt_bor w50" />
										</div>
									</td>
									<td>
										<div class="overflow_hidden">
											<input type="text" id="pro_buymoney_0" name="orderDetailBeans[0].buyMoney" value="" class="ipt_bor w100" />
										</div>
									</td>
									<td>
										<div class="overflow_hidden">
											<input type="text" id="pro_context_0" name="orderDetailBeans[0].context" value="" class="ipt_bor w100" />
										</div>
									</td>
									<td style="width:30px;">
										<div class="overflow_hidden_01 w30 link" >
											<a href="javascript:;" id="pro_del_0" class="del alert_sys_pro_set_d_a" title="删除"></a>
										</div>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<div class='bomb-btn'>
				<label class='bomb-btn-cen'>
					<c:if test="${orderBean.authState eq 0 || orderBean.authState eq 3}">
					<a href="javascript:;" id="reportBtn" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>上报</label></a>
					</c:if>
					<a href="javascript:;" id="saveEditBtn" class="com-btna bomp-btna com-btna-sty sure-btn fl_l"><label>保存</label></a>
					<a href="javascript:;" id="cancleEditBtn" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
				</label>
			</div>
	</form>
</body>
</html>
