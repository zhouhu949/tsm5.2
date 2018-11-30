<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
	<c:when test="${!empty countData['5'] }">
		<span class="sp">订单总额：</span><label class="lab"><fmt:formatNumber type="number" pattern="#,###0.00#" maxFractionDigits="2" value="${countData['5'] }"></fmt:formatNumber>万</label>
	</c:when>
	<c:otherwise>
		<span class="sp">订单总额：</span><label class="lab">0.00万</label>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${!empty countData['2'] }">
		<span class="sp">通过金额：</span><label class="lab"><fmt:formatNumber type="number" pattern="#,###0.00#" maxFractionDigits="2" value="${countData['2'] }"></fmt:formatNumber>万</label>
	</c:when>
	<c:otherwise>
		<span class="sp">通过金额：</span><label class="lab">0.00万</label>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${!empty countData['1'] }">
		<span class="sp">待审金额：</span><label class="lab"><fmt:formatNumber type="number" pattern="#,###0.00#" maxFractionDigits="2" value="${countData['1'] }"></fmt:formatNumber>万</label>
	</c:when>
	<c:otherwise>
		<span class="sp">待审金额：</span><label class="lab">0.00万</label>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${!empty countData['3'] }">
		<span class="sp">驳回金额：</span><label class="lab"><fmt:formatNumber type="number" pattern="#,###0.00#" maxFractionDigits="2" value="${countData['3'] }"></fmt:formatNumber>万</label>
	</c:when>
	<c:otherwise>
		<span class="sp">驳回金额：</span><label class="lab">0.00万</label>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${!empty countData['0'] }">
		<span class="sp">未上报：</span><label class="lab"><fmt:formatNumber type="number" pattern="#,###0.00#" maxFractionDigits="2" value="${countData['0'] }"></fmt:formatNumber>万</label>
	</c:when>
	<c:otherwise>
		<span class="sp">未上报：</span><label class="lab">0.00万</label>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${!empty countData['4'] }">
		<span class="sp">作废金额：</span><label class="lab"><fmt:formatNumber type="number" pattern="#,###0.00#" maxFractionDigits="2" value="${countData['4'] }"></fmt:formatNumber>万</label>
	</c:when>
	<c:otherwise>
		<span class="sp">作废金额：</span><label class="lab">0.00万</label>
	</c:otherwise>
</c:choose>