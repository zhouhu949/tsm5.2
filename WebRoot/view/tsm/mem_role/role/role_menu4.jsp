<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <script type="text/javascript">
	$(function(){
		/*表单优化*/
	    $('input[type=checkbox]').iCheck({
	        checkboxClass: 'icheckbox_minimal',
	        radioClass: 'iradio_minimal',
	        increaseArea: '20%'
	    });
			
		$("input[id^=sel_quick_]").on("ifUnchecked",function(){
			$("input[id^=sel_quick_]").each(function(){
				if(!$(this).is(":checked")){
					$(this).removeAttr("disabled");
				}
			});
		});
		
		$("input[id^=sel_quick_]").on("ifChecked",function(){
			if($("input[id^=sel_quick_]:checked").length == 6){
				$("input[id^=sel_quick_]").each(function(){
					if(!$(this).is(":checked")){
						$(this).attr("disabled","disabled");
					}
				});
			}
		});
	});
	window.onload=function(){
		if($("input[id^=sel_quick_]:checked").length == 6){
			$("input[id^=sel_quick_]").each(function(){
				if(!$(this).is(":checked")){
					$(this).attr("disabled","disabled");
				}
			});
		}
	}
	</script>
    <style>
		.second-menu span{*margin-top:3px;}
	</style>
<form id="myForm_4" method="post">
   <input type="hidden" id="curStep4" name="curStep4" value="4">
   <input type="hidden" name="quickResources" id="quickResources">
	<div class="edit-short-func-list">
		<div class="list-short-box">
			<div class="all-menu">
				<c:forEach items="${listtree }" var="tree" varStatus="vs">
					<c:choose>
						<c:when test="${tree.resourceName eq '首页' || tree.resourceName eq '消息中心' }"></c:when>
						<c:when test="${tree.resourceName eq '淘客户' }">
							<div class="first-menu skin-minimal clearfix">
								<span class="fl_l">${tree.resourceName }</span>
							</div>
							<div class="second-menu skin-minimal clearfix">
								<input type="checkbox" id="sel_quick_${tree.resourceId }"${(tree.isUserQuick eq 1 || tree.quickDefault eq 1) ? ' checked' : '' }${tree.quickDefault eq 1 ? ' disabled' : '' }/>
								<span class="menu-name fl_l">${tree.resourceName }</span>
							</div>
						</c:when>
						<c:when test="${tree.resourceName eq '审核中心' }">
							<div class="first-menu skin-minimal clearfix">
								<span class="fl_l">${tree.resourceName }</span>
							</div>
							<div class="second-menu skin-minimal clearfix">
								<input type="checkbox" id="sel_quick_${tree.resourceId }"${(tree.isUserQuick eq 1 || tree.quickDefault eq 1) ? ' checked' : '' }${tree.quickDefault eq 1 ? ' disabled' : '' }/>
								<span class="menu-name fl_l">${tree.resourceName }</span>
							</div>
						</c:when>				
						<c:otherwise>
							<c:set var="childList" value="${tree.childList }"/>
							<div class="first-menu skin-minimal clearfix">
								<span class="fl_l">${tree.resourceName }</span>
							</div>
							<c:if test="${!empty childList }">
								<div class="second-menu skin-minimal clearfix">
									<c:forEach items="${childList }" var="tree1" varStatus="vs1">
										<input type="checkbox" id="sel_quick_${tree1.resourceId }"${(tree1.isUserQuick eq 1 || tree1.quickDefault eq 1) ? ' checked' : '' }${tree1.quickDefault eq 1 ? ' disabled' : '' }/>
										<span class="menu-name fl_l">${tree1.resourceName }</span>
									</c:forEach>
								</div>
							</c:if>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</div>
	</div>
	</form>