<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>

<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/form.js"></script>
<span class="hyx-sce-right-tit fl_l">其他待跟进客户</span>
<!-- 计划待跟进下拉框开始 -->
<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
<div class="com-search hyx-sce-right-search hyx-fude-search fl_l">
     		<div class="com-search-box fl_l">	  
     		   <c:set var="planParamName" value="待跟进时间"/>
     		   <c:choose>
     		   		<c:when test="${planParam eq 1 }"><c:set var="planParamName" value="计划待跟进"/></c:when>
     		   		<c:when test="${planParam eq 2 }"><c:set var="planParamName" value="其他待跟进"/></c:when>
     		   		<c:when test="${planParam eq 3 }"><c:set var="planParamName" value="签约未跟进"/></c:when>
     		   </c:choose>
			   <div class="list-box">
					<dl class="select">
						<dt style="border-right:0 none !important;">${planParamName}</dt>
						<dd>
							<ul>
								<li><a href="###" title="计划待跟进" onclick="planParam_('1')">计划待跟进</a></li>
								<li><a href="###" title="其他待跟进" onclick="planParam_('2')">其他待跟进</a></li>
								<li><a href="###" title="签约未跟进" onclick="planParam_('3')">签约未跟进</a></li>
							</ul>
						</dd>
					</dl>
				</div>
			</div>
</div>
<div class="com-table hyx-sce-right-table fl_l" >
<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th style="width:124px;"><span class="sp sty-borcolor-b">&nbsp;</span></th>
					<th style="width:117px;"><span class="sp sty-borcolor-b">客户名称</span></th>
					<th>联系电话</th>
				</tr>
			</thead>
			<tbody>              
				<c:forEach items="${restDtos}" var="restDto" varStatus="v">
					<c:if test="${v.index == 0 }">
						<input type="hidden" id="nextCustId"  value="${restDto.resCustId }"> <!--  设值下一条待跟进资源ID -->
					</c:if>
					<tr class="${v.count%2==0?'sty-bgcolor-b':''}" name="otherClick" id='${restDto.resCustId }' >
						<td><div class="overflow_hidden w100" title="<fmt:formatDate value="${restDto.nextActionDate}" type="date" pattern='MM-dd HH:mm'/>"><fmt:formatDate value="${restDto.nextActionDate}" type="date" pattern='MM-dd HH:mm'/></div></td>
						<td><div class="overflow_hidden w100" title="${restDto.custName}">${restDto.custName}</div></td>
						<td><div class="overflow_hidden w100" title="${restDto.telphone}" name="telphone_" >${restDto.telphone}</div></td>
					</tr>
				</c:forEach>				
			</tbody>
		</table>
	</div>
<script type="text/javascript">
	$(function(){
		/*表单优化*/
		  //触发其他待跟进资源
		$('tr[name=otherClick]').click(function(){
				 var custId = $(this).attr("id");
				 var custType = $("#custType").val();
				 var custCation = $("#custCation").val();
				 var planParam = $("#planParam").val();
				 var state = $("#state").val();
				document.location.href="${ctx}/cust/custFollow/custFollowPage.do?custId="+custId
						+"&custListType="+custType+"&custCation="+custCation+"&state="+state+"&planParam="+planParam;
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
	
	// 选择是否计划跟进事件
	function planParam_(planParam){
		// 异步加载 其他待跟进数据
	    var url = "${ctx}/cust/custFollow/custFollowRightPage.do";
	    var custType =$("#custType").val();
	    var custCation =$("#custCation").val();
	    var custId =$("#custId").val();
	    $.ajax({
			url: url,
			type:'POST',
			data:{'custType':custType,'custCation':custCation,'custId':custId,'planParam':planParam},
			dataType:'html',
			error:function(){alert("网络异常，请稍后再操作！")},
			success:function(data){
				$("#planParam").val(planParam);
				$('#wait_page_div').html(data);
			}
		});
	}
</script>