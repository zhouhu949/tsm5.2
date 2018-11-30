<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/common.jsp" %>
<div class="hyx-silent-p clearfix">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">个人统计</label>
<!-- 		<div class="com-search fl_l" style="margin-top:-4px;min-height:0"> -->
<!-- 			<label class="fl_l" for="" style="line-height:34px;">选择人员：</label> -->
<!-- 			<dl class="select fl_l" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-right:10px;"> -->
<!-- 				<dt id="filterNames">-请选择-</dt> -->
<!-- 				<dd> -->
<!-- 					<ul id="tt3" class="easyui-tree" data-options="animate:true,dnd:false" style="max-height:160px;margin-top:10px;"> -->
<%-- 						<c:forEach items="${list }" var="pd" varStatus="vs"> --%>
<%-- 							<li><span><input name="account_filter" type="checkbox" value="${pd.userAccount }">${pd.userName }</span></li> --%>
<%-- 						</c:forEach> --%>
<!-- 					</ul> -->
<!-- 					<div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;"> -->
<!-- 						<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" onclick="filterAccount()" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">确定</label></a> -->
<!-- 						<a class="com-btnd bomp-btna com-btna-cancle fl_l" href="javascript:;" onclick="clearFilter()" style="margin-left:10px;"><label style="height:19px;line-height:19px;">清空</label></a> -->
<!-- 					</div> -->
<!-- 				</dd> -->
<!-- 			</dl> -->
<!-- 		</div> -->
	</div>
	<div class="com-table hyx-mpe-table hyx-cm-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">销售姓名</span></th> 
					<th><span class="sp sty-borcolor-b">销售帐号</span></th>
					<c:forEach items="${products }" var="product" varStatus="vs">
						<c:choose>
							<c:when test="${empty colMap }">
								<th><span class="sp sty-borcolor-b">${product.name }</span></th>
							</c:when>
							<c:otherwise>
								<c:if test="${!empty colMap[product.id] }">
									<th><span class="sp sty-borcolor-b">${product.name }</span></th>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</tr>
			</thead>
			<tbody>              
				<c:choose>
					<c:when test="${!empty list }">
						<c:forEach items="${list }" var="pd" varStatus="vs">
							<tr id="member_${pd['userAccount'] }" class="${vs.count % 2 == 0 ? 'sty-bgcolor-b' : '' }">
								<td><div class="overflow_hidden w120" title="${pd['userName'] }">${pd['userName'] }</div></td>
								<td><div class="overflow_hidden w120" title="${pd['userAccount'] }">${pd['userAccount'] }</div></td>
								<c:forEach items="${products }" var="product" varStatus="vs">
									<c:choose>
										<c:when test="${empty colMap }">
											<td><div class="overflow_hidden w100" title="${pd[product.id] }/<fmt:formatNumber type="number" value="${pd['allNum'] gt 0 ? (pd[product.id] /pd['allNum']*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%">${pd[product.id] }/<fmt:formatNumber type="number" value="${pd['allNum'] gt 0 ? (pd[product.id] /pd['allNum']*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%</div></td>
										</c:when>
										<c:otherwise>
											<c:if test="${!empty colMap[product.id] }">
												<td><div class="overflow_hidden w100" title="${pd[product.id] }/<fmt:formatNumber type="number" value="${pd['allNum'] gt 0 ? (pd[product.id] /pd['allNum']*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%">${pd[product.id] }/<fmt:formatNumber type="number" value="${pd['allNum'] gt 0 ? (pd[product.id] /pd['allNum']*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%</div></td>
											</c:if>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<td colspan="${fn:length(colMap) eq 0 ? fn:length(products)+2 : fn:length(colMap)+2 }">当前列表无数据！</td>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>