<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>短信发送记录-查看明细</title>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/dialog.css"><!--弹框样式-->
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript">
$(function(){

    /* 屏蔽手机或固话中间几位  */
    var idReplaceWord = $("#idReplaceWord").val();	
	if(idReplaceWord==1){
	    $("div[name=phone_]").each(function(){
   		   var str = $(this).text();
   		   replaceWord(str,$(this));
   		   replaceTitleWord(str,$(this));
	    });
	}
});
</script>
</head>
<body> 
<div class='bomp-cen'>
	<form action="${ctx }/sms/record/smsSendDetailList.do" method="post" id="idialogForm">
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<input type="hidden" name="smsId" value="${item.smsId }">
	<div class='com-table bomp-table-a fl_l'>
		<table width='100%' cellspacing='0' cellpadding='0' class='sty-borcolor-a sty-borcolor-c com-table-b com-radius'>
			<thead>
				<tr class='sty-bgcolor-b'>
					<th><span class='sp sty-borcolor-b'>发送时间</span></th> 
					<th><span class='sp sty-borcolor-b'>短信条数</span></th>
					<th><span class='sp sty-borcolor-b'>电话号码</span></th>
					<th>短信状态</th>
				</tr>
			</thead>
			<tbody>     
				<c:choose>
					<c:when test="${not empty list }">
						<c:forEach items="${list }" var="detail" varStatus="vs">
							<tr class=" ${vs.count%2==0?'sty-bgcolor-b':''}">
								<td><div class='overflow_hidden w150' title="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${detail.sendDate}"/>"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${detail.sendDate}"/></div></td>
								<td><div class='overflow_hidden w70' >${detail.smsNumber}</div></td>
								<td><div class='overflow_hidden w120' name="phone_" title='${detail.mobilePhone}'>${detail.mobilePhone}</div></td>
								<td><div class='overflow_hidden w70' >
									<c:choose>
                                		<c:when test="${detail.submitStatus eq '-1'}">未提交</c:when>
                                		<c:when test="${detail.submitStatus eq '0'}">提交中</c:when> 
                                		<c:when test="${detail.submitStatus eq '1'}">成功</c:when>
                                		<c:otherwise>失败</c:otherwise>
                               		</c:choose>
								</div></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="4" style="text-align: center;">
                              		<div class="overflow_hidden w120" title="当前列表无数据">当前列表无数据！</div>
                          		</td>
						</tr>
					</c:otherwise>
				</c:choose>         
			</tbody>
		</table>
	</div>
	<div class="com-bot">
		<div class="com-page fl_r">
			${item.page.pageStr}
		</div>
	</div>
	</form>
</div>
</body>
</html>