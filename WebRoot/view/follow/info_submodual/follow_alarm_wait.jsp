<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<span class="hyx-sce-right-tit fl_l">其他待跟进客户</span>
<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
<div class="com-table hyx-sce-right-table fl_l" >
<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">待跟进时间</span></th>
					<th><span class="sp sty-borcolor-b">客户名称</span></th>
					<th>联系电话</th>
				</tr>
			</thead>
			<tbody>              
				<c:forEach items="${restDtos}" var="restDto" varStatus="v">
					<c:if test="${v.index eq 0}">
						<input type="hidden"  id="nextStartPage" value="${restDto.startPage }">
						<input type="hidden" id="nextCustId"  value="${restDto.resCustId }"> <!--  设值下一条待跟进资源ID -->
					</c:if>
					<tr class="${v.count%2==0?'sty-bgcolor-b':''}" name="otherClick" id="${restDto.resCustId }" startPage="${restDto.startPage}">
						<td><div class="overflow_hidden w100" title="<fmt:formatDate value="${restDto.nextActionDate}" type="date" pattern='MM-dd HH:mm'/>"><fmt:formatDate value="${restDto.nextActionDate}" type="date" pattern='MM-dd HH:mm'/></div></td>
						<td><div class="overflow_hidden w100" title="${restDto.custName}">${restDto.custName}</div></td>
						<td><div class="overflow_hidden w100" title="${restDto.telphone}" name="telphone_">${restDto.telphone}</div></td>
					</tr>
				</c:forEach>				
			</tbody>
		</table>
	</div>
<script type="text/javascript">
	$(function(){
		  //触发其他待跟进资源
		$('tr[name=otherClick]').click(function(){
				 var custId = $(this).attr("id");
				 var startPage =  $(this).attr("startPage");
				 document.location.href="${ctx}/cust/custFollow/custFollowPage.do?custId="+custId
						+"&startPage="+startPage+"&isAlarm=1";
		});
		/********** 屏蔽手机或固话中间几位  *********/
	    var idReplaceWord = $("#idReplaceWord").val();	
		if(idReplaceWord==1){
		    $("div[name=telphone_]").each(
		    		function(){
		    		   var str = $(this).text();
		    		   replaceWord(str,$(this));
		    		   replaceTitleWord(str,$(this));
		    		  }
		    );    
		}  
	});
</script>