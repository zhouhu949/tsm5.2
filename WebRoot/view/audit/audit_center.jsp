<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript">
	$(function(){

		/*左边菜单选中效果*/
		$(".hyx-mc-left").find('.box').each(function(i,item){
			$(item).click(function(){
				$(this).addClass('box-click').siblings('.box').removeClass('box-click');
				var rel =$(this).attr('rel');
				$('#iframepage').attr('src',rel);
			});
		});
		
	});
</script>
</head>
<body>
	<div class="hyx-mc" style="background-color:#f4f4f4 !important;">
		<div class="hyx-mc-left fl_l">
			<shiro:hasPermission name="20e09bf1fde54c38b3f946c8f0c6398e">
				<label class="box fl_l ${type eq '1' ? 'box-click' : '' }" rel="${ctx }/contract/order/list?pageView=check&type=2">
					<span class="sp">订单审核</span>
				</label>
			</shiro:hasPermission>
			<shiro:hasPermission name="811ff228d6a84f3c91ac9fd58c4ce0e7">
				<label class="box fl_l ${type eq '2' ? 'box-click' : '' }" rel="${ctx }/plan/day/auth/view">
					<span class="sp">日计划审核</span>
				</label>
			</shiro:hasPermission>
			<shiro:hasPermission name="groupPlan_edit">
				<label class="box fl_l ${type eq '3' ? 'box-click' : '' }" rel="${ctx }/plan/month/user/authView">
					<span class="sp">成员月计划审核</span>
				</label>
			</shiro:hasPermission>
			<shiro:hasPermission name="deptPlan_edit">
				<label class="box fl_l ${type eq '4' ? 'box-click' : '' }" rel="${ctx }/plan/month/group/authView">
					<span class="sp">部门月计划审核</span>
				</label>
			</shiro:hasPermission>
			<shiro:hasPermission name="0bbb63c8bc584ec89ae14dbb11a286c4">
				<label class="box fl_l ${type eq '5' ? 'box-click' : '' }" rel="${ctx }/cust/custFollow/extension/deferredAuditList">
					<span class="sp">延期审核</span>
				</label>
			</shiro:hasPermission>
		</div>
		<c:choose>
			<c:when test="${type eq '1' }">
				<c:set var="curUrl">${ctx }/contract/order/list?pageView=check&type=2</c:set>
			</c:when>
			<c:when test="${type eq '2' }">
				<c:set var="curUrl">${ctx }/plan/day/auth/view</c:set>
			</c:when>
			<c:when test="${type eq '3' }">
				<c:set var="curUrl">${ctx }/plan/month/user/authView</c:set>
			</c:when>
			<c:when test="${type eq '4' }">
				<c:set var="curUrl">${ctx }/plan/month/group/authView</c:set>
			</c:when>
			<c:when test="${type eq '5' }">
				<c:set var="curUrl">${ctx }/cust/custFollow/extension/deferredAuditList</c:set>
			</c:when>
		</c:choose>
		<div class="hyx-mc-right fl_l" style="background-color:#f4f4f4 !important;overflow-x:hidden;overflow-y:auto;">
			<iframe src="${curUrl }" width="100%" height="100%" id="iframepage" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" allowtransparency="true"></iframe>
		</div>
	</div>
</body>
</html>
