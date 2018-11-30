<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
$(function(){
	/*表单优化*/
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal',
        increaseArea: '20%'
    });
});


</script>
<div class="hyx-cfu-card fl_l" style="height:508px;">
				<div class="send-record-prod">
					<div class="record-prod-title" title="${tes.title }" style="width:auto;">${tes.title }</div>
					<p><label for="">收件人：</label><span class="overflow_hidden" title="${tes.emailReviceUser }">${fn:escapeXml(tes.emailReviceUser)}</span></p>
					<p><label for="">发送时间：</label><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${tes.inputTime}"/></p>				
					<p><label for="">发件人：</label><span class="overflow_hidden">${tes.sendUser }</span></p>
				</div>
				<div class="mail-record-enve">		
					<p><span>${tes.context }</span></p>	
					<c:if test="${not empty sysFiles }">
						<div class="encl-osure clearfix">
							<div class="encl-osure-title"><img width="15" height="15" src="${ctx}/static/images/fashion.png" alt="">附件（${fn:length(sysFiles)}个）</div>
							<div class="encl-osure-left fl_l"><img width="30" height="30" src="${ctx}/static/images/fashion.png" alt=""></div>
							<div class="encl-osure-right fl_l">			
								<c:forEach items="${sysFiles }" var="sysFile" varStatus="vs">
									<p><span>${sysFile.fileName }(${sysFile.fileSize }K)</span></p>
									<p><a href="javascript:;" onclick="emailDownLoad('${sysFile.fileUrl}','${sysFile.fileName}')">下载</a></p>
								</c:forEach>								
							</div>
						</div>
					</c:if>			
				</div>
</div>
		