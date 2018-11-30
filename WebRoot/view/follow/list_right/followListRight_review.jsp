<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/taoCust_style_part.css${_v}" />
<div class="timeline">
					<c:choose>
					<c:when test="${not empty tsmCustReviews }">
					<div class="timeline_icon_grey_empty"></div>
					<!-- <div class="time_line"></div> -->
					<ul id="timeline_ul">
						
						<c:forEach items="${tsmCustReviews }" var="custRewiew">
							 <!--  <li class="li-load">
				        		<div class="cfu-cir"></div>
						        </li> -->
						        <li class="li-load cfu-mt">
					        		<div class="cfu-cirb"></div>
					        		<div class="right">
					        			<div class="cfu-time" style="margin:5px 0 5px 10px"><fmt:formatDate value="${custRewiew.reviewDate}" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;</div>
					        			<div class="arrow"><em>◆</em><span>◆</span></div>
					        			<div class="cfu-box">
					        				<p class="" style="padding:0 10px"><label class="">${custRewiew.revComment}</label></p>
					        			</div>
					        			<div class="comm-on-peop" style="float:left;background:transparent;">
					        				<p class="cfu-list">点评人：${custRewiew.reviewAcc }</p>
					        			</div>
					      			</div>
						        </li>
						</c:forEach>
					
					</ul>
					<div class="timeline_icon_grey_empty_end"></div>
					</c:when>
					<c:otherwise>
					
					</c:otherwise>
				</c:choose>		
				</div>

<%-- 	<div class="timeline">
		    <ul>
		    	<c:choose>
					<c:when test="${not empty tsmCustReviews }">
						<c:forEach items="${tsmCustReviews }" var="custRewiew">
							  <li class="li-load">
				        		<div class="cfu-cir"></div>
				        		<div class="cfu-time"><fmt:formatDate value="${custRewiew.reviewDate}" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;</div>
						        </li>
						        <li class="li-load cfu-mt">
					        		<div class="cfu-cirb"></div>
					        		<div class="right">
					        			<div class="arrow"><em>◆</em><span>◆</span></div>
					        			<div class="cfu-box">
					        				<p class="" style="padding:0 10px"><label class="">${custRewiew.revComment}</label></p>
					        			</div>
					        			<div class="comm-on-peop">
					        				<p class="cfu-list">点评人：${custRewiew.reviewAcc }</p>
					        			</div>
					      			</div>
						        </li>
						</c:forEach>
					</c:when>
					<c:otherwise>
					
					</c:otherwise>
				</c:choose>		      
	  		</ul>
		</div> --%>
<form id="reviewForm_" action="${ctx }/cust/custFollow/List/addReview.do" method="post">
		<input type="hidden" name="retaId" id="retaId"><!-- 跟进ID -->
		<input type="hidden" name="custId" id="custId"><!-- 客户ID -->
		<div class="comm-content fl_l">
			<p class="clearfix">
				<label class="fl_l" for="">发表评论：</label>
				<textarea class="fl_l" name="revComment" id="revComment" cols="30" rows="10" onkeyup="maxLength_(this)"></textarea>
				<a class="com-btnc bomp-btna com-btna-sty fl_r" href="javascript:;" id="send_">发表</a>
			</p>
		</div>
</form>
<script type="text/javascript">
$(function(){
	// 设置跟进ID、客户ID
	$("#retaId").val($("#followId").val());
	$("#custId").val($("#resCustId").val());
	
	// 点击发表 触发事件
	$("#send_").click(function(){
		$("#reviewForm_").ajaxSubmit({
			dataType:'json',
			type : 'post',
			error:function(){},
			success:function(data){
				if(data==0){
					var resCustId = $("#resCustId").val();	 // 资源ID
					var retaId = $("#retaId").val();	 // 跟进ID
					var url =  "${ctx}/cust/custFollow/List/queryReview.do";
					var data = {'resCustId':resCustId,'retaId':retaId};
					$.post(url,data,function(data){
						$("#reView_show").html(data);
					},'html');
				}else{
					alert("添加异常");
				}			
			}
		});	
	});
})
function maxLength_($this){
    	$($this).val( $($this).val().substring(0, 150));
    }
</script>