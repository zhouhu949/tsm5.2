<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/common.jsp"%>
<div class="hyx-silent-p clearfix">
	<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">个人统计</label>
<!-- 	<div class="com-search fl_l" style="margin-top:-4px;min-height:0"> -->
<!-- 		<label class="fl_l" for="" style="line-height:34px;">选择人员：</label> -->
<!-- 		<dl class="select fl_l" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-right:10px;"> -->
<!-- 			<dt id="filterNames">-请选择-</dt> -->
<!-- 			<dd> -->
<!-- 				<ul id="tt3" class="easyui-tree" data-options="animate:true,dnd:false" style="max-height:160px;margin-top:10px;"> -->
<%-- 					<c:forEach items="${dtos }" var="dto" varStatus="vs"> --%>
<%-- 						<li><span><input name="account_filter" type="checkbox" value="${dto.userAccount }">${dto.userName }</span></li> --%>
<%-- 					</c:forEach> --%>
<!-- 				</ul> -->
<!-- 				<div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;"> -->
<!-- 					<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" onclick="filterAccount()" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">确定</label></a> -->
<!-- 					<a class="com-btnd bomp-btna com-btna-cancle fl_l" href="javascript:;" onclick="clearFilter()" style="margin-left:10px;"><label style="height:19px;line-height:19px;">清空</label></a> -->
<!-- 				</div> -->
<!-- 			</dd> -->
<!-- 		</dl> -->
<!-- 	</div> -->
</div>
<div class="com-table hyx-mpe-table hyx-cm-table">
	<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
		<thead>
			<tr class="sty-bgcolor-b">
				<th><span class="sp sty-borcolor-b">销售姓名</span></th> 
				<th><span class="sp sty-borcolor-b">销售帐号</span></th>
				<c:if test="${empty colMap || !empty colMap['1'] }">
					<th><span class="sp sty-borcolor-b">资源</span></th>
				</c:if>
				<c:if test="${empty colMap || !empty colMap['2'] }">
					<th><span class="sp sty-borcolor-b">意向客户</span></th>
				</c:if>
				<c:if test="${empty colMap || !empty colMap['3'] }">
					<th><span class="sp sty-borcolor-b">签约客户</span></th>
				</c:if>
				<c:if test="${empty colMap || !empty colMap['4'] }">
					<th><span class="sp sty-borcolor-b">沉默客户</span></th>
				</c:if>
				<c:if test="${empty colMap || !empty colMap['5'] }">
					<th>流失客户</th>
				</c:if>
			</tr>
		</thead>
		<tbody>              
			<c:choose>
				<c:when test="${!empty dtos }">
					<c:forEach items="${dtos }" var="dto" varStatus="vs">
						<tr id="member_${dto.userAccount }" class="${vs.count%2 eq 0 ? 'sty-bgcolor-b' : '' }">
							<td><div class="overflow_hidden w120" title="${dto.userName }">${dto.userName }</div></td>
							<td><div class="overflow_hidden w120" title="${dto.userAccount }">${dto.userAccount }</div></td>
							<c:if test="${empty colMap || !empty colMap['1'] }">
								<td><div class="overflow_hidden w100" title="${dto.resNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.resNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%">${dto.resNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.resNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%</div></td>
							</c:if>
							<c:if test="${empty colMap || !empty colMap['2'] }">
								<td><div class="overflow_hidden w100" title="${dto.custNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.custNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%">${dto.custNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.custNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%</div></td>
							</c:if>
							<c:if test="${empty colMap || !empty colMap['3'] }">
								<td><div class="overflow_hidden w100" title="${dto.signNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.signNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%">${dto.signNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.signNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%</div></td>
							</c:if>
							<c:if test="${empty colMap || !empty colMap['4'] }">
								<td><div class="overflow_hidden w100" title="${dto.silentNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.silentNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%">${dto.silentNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.silentNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%</div></td>
							</c:if>
							<c:if test="${empty colMap || !empty colMap['5'] }">
								<td><div class="overflow_hidden w100" title="${dto.losingNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.losingNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%">${dto.losingNum }/<fmt:formatNumber type="number" value="${dto.allNum gt 0 ? (dto.losingNum/dto.allNum*100) : 0 }" pattern="0.00" maxFractionDigits="2" />%</div></td>
							</c:if>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="${fn:length(colMap) eq 0 ? 7 : fn:length(colMap)+2 }">当前列表无数据！</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>
