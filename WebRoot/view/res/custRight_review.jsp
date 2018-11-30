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

			<input type="hidden" name="custId" id="custId" value="${resCustId }"><!-- 客户ID -->
			<div class="comm-content fl_l">
				<p class="clearfix">
					<label class="fl_l" for="">发表评论：</label>
					<textarea class="fl_l" name="revComment" id="revComment" cols="30" rows="10" onkeyup="maxLength_(this)"></textarea>
					<a class="com-btnc bomp-btna com-btna-sty fl_r" href="javascript:;" id="send_">发表</a>
				</p>
			</div>
<script type="text/javascript">
$(function(){
	
	// 点击发表 触发事件
	$("#send_").click(function(){
		var text = $("#revComment").val();
		var custId = $("#custId").val();
		if(text == null || text == ''){
			window.top.iDialogMsg("提示","请填写评论内容!");
			return;
		}
		$.ajax({
			url:'${ctx}/cust/card/saveReview',
			type:'post',
			data:{'custId':custId,'revComment':text},
			dataType:'json',
			error:function(){},
			success:function(data){
				if(data == '1'){
					window.top.iDialogMsg("提示","评论成功!");
					var url =  "${ctx}/cust/custFollow/List/queryCustReview.do";
					var data = {'resCustId':custId};
					$.post(url,data,function(data){
						$("#reView_show").html(data);
					},'html');
				}else{
					window.top.iDialogMsg("提示","评论失败!");
				}
			}
		});
	});
})
function maxLength_($this){
    	$($this).val( $($this).val().substring(0, 150));
}
</script>