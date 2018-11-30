<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<meta http-equiv="X-UA-Compatible" content="IE=edge charset=utf-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	pageContext.setAttribute("base",request.getContextPath());
    pageContext.setAttribute("ctx",request.getContextPath());
%>
<html>
<head>
<!--公共样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dialog.css"><!--弹框样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/skin/default/color.css"><!--换肤样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/form/skins/all.css"><!--form样式-->

<script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js?v=222" dialog-theme="default"></script><!--可移动弹框插件-->
<script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js"></script><!--可移动弹框插件公共js-->
<script type="text/javascript" src="${ctx}/static/js/form/icheck.js"></script><!--表单优化-->
<script type="text/javascript" src="${ctx}/static/js/form/js/custom.min.js"></script><!--表单优化-->
<script type="text/javascript" src="${ctx}/static/js/common/common.js"></script>
<style>
.tree-node {
	width: 240px;
}

.hyx-hsm-left dl {
	text-align: left;
}

.hyx-hsm-left dl dt{
	text-indent: 30px;
}
 .hyx-hsm-left dl dd {
	text-indent: 13px;
}
.icheckbox_minimal {
	margin: 12px 0 0 10px;
}
</style>
<script type="text/javascript" src="${ctx}/static/js/table.js"></script>
<script type="text/javascript" src="${ctx}/static/js/form.js"></script>
<script type="text/javascript">
$(function(){
    /*内容展开*/
    $('.temp-for-words').each(function(i,item){
    	var text_box = $(item).find('.wrds-decor');
    	var all_text = text_box.text();
    	if(all_text.length >= 100){
    		text_box.next().show();
    		if(i != 0){
    			text_box.text(all_text.substr(0,100) + '...');
    		}else{
    			text_box.next().removeClass('bomp-scb-down').addClass('bomp-scb-up');
    		}
    		$(item).find('i').click(function(){
    			if($(this).hasClass('bomp-scb-down') == true){
    				$(this).parent().find('.wrds-decor').text(all_text);
	    			text_box.next().removeClass('bomp-scb-down').addClass('bomp-scb-up');
    			}else{
    				$(this).parent().find('.wrds-decor').text(all_text.substr(0,100) + '...');
	    			text_box.next().removeClass('bomp-scb-up').addClass('bomp-scb-down');
    			}
    		});
	    }else{
	    	$(item).find('i').hide();
	    }

    });

    $('dd[id^=find_]').click(function(){
    	var groupId = $(this).attr('id').substring(5);
		$("#groupId").val(groupId);
		document.forms[0].submit()
    })
});
</script>
</head>
<body>
<form action="${ctx}/sys/knowlege/list" method="post">
	<input type="hidden" id="groupId" name="groupId" value="${groupId}">
	<div class="bomp-scb">
		<div class="hyx-hsm-left fl_l" style="overflow-y:auto;">
			<dl>
				<dt>模板分类</dt>
				<dd id="find_" class="<c:if test='${ empty groupId or "" eq groupId }'>dd-hover</c:if>">所有分类(${allKnowlege })</dd>
                <dd id="find_0" class="<c:if test='${"0" eq groupId }'>dd-hover</c:if>"><span>未分类(${unGroup})</span></dd>
	            <c:forEach items="${knowlegeGroupList}" var="group" varStatus="vs">
		           <dd id="find_${group.groupId }" class="<c:if test='${group.groupId eq groupId }'>dd-hover</c:if>">
					    <span title="${group.groupName}(${group.knowlegeNum})">${group.groupName}(${group.knowlegeNum})</span>
				   </dd>
				</c:forEach>
			</dl>
		</div>
		<div class="hyx-hsm-right fl_l" style="border:none;overflow-y: auto;overflow-x:hidden;">
			<div class="com-search hyx-cm-search">
				<div class="com-search-box fl_l" style="padding-bottom:0;background-color:#fff;">
				   <p class="search clearfix"><input class="input-query fl_l" type="text" value="${knowlegeDto.qKeyWordId }" id="qKeyWordId" name="qKeyWordId" style="border:none;background-color:#fff;"/><label class="hide-span" style="line-height:15px;">输入关键字</label></p>
				</div>
				<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query" />
			</div>
			<div class="hid fl_l" style="display:block;width:100%;">

			<c:choose>
			       <c:when test="${!empty knowlegeList }">
			           <c:forEach items="${ knowlegeList}" var="kl">
							<div class="temp-for-words fl_l" style="width:100%;">
								<p class="words-p-first clearfix">
									<span class="temp-words-span fl_l">${kl.question }</span>
								</p>
								<p><span class="wrds-decor">${kl.answer }</span><i class="bomp-scb-down"></i></p>
							</div>
			           </c:forEach>
			       </c:when>
			       <c:otherwise><span class="no-data-mf">暂无数据</span></c:otherwise>
			</c:choose>
					<div class="com-bot" style="padding-bottom:15px;">
						<div class="com-page fl_r" style="margin:10px 10px 0 0">
							<div class="page">${knowlegeDto.page.pageStr}</div>
							<div class="clr_both"></div>
						</div>
					</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>
