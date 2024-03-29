<#global ctx="${(ctp.contextPath)!''}">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/base.css">
<#macro head>
	<meta http-equiv="pragma" content="no-cache">
	<meta name="viewport" content="width=device-width; initial-scale=1.0">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/base.css">
	<script type="text/javascript" src="${ctx}/static/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/Validate.js"></script>
	<script type="text/javascript">
		var ctx="${ctx}";
	</script>
</#macro>

<#macro easyui>
<@head />
	<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/staticeasyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script src="${ctx}/static/js/comAuth.js" charset="UTF-8" type="text/javascript"></script>
</#macro>

<#macro pagetoolbar page,formName>
<script type="text/javascript" src="${ctx}/js/frame/pagetoolbar.js"></script>
<script type="text/javascript" src="${ctx}/js/frame/list.js"></script>
			<#if (page.pageIndex >= page.pageCount) && (page.pageCount != 0)>
				<#assign index = page.pageCount />
			<#else>
				<#assign index = page.pageIndex />
			</#if>
			<ul class="box_2">
				<li class="home">
					<#if page.firstPage><a href="javascript:startPage('${formName}');">首页</a><#else>首页 </#if>
				</li>
				<li class="pageUP">
					<#if page.hasPrev><a href="javascript:upPage('${formName}');">上一页</a><#else>上一页 </#if>
				</li>
				<li>共计${page.totalCount}条数据</li>
				<li>第<#if (page.pageCount == 1)>${index}<#else>${index}</#if>页/共<span id="pageCount">${page.pageCount}</span>页</li>
				<li class="pageDown">
					<#if page.hasNext><a href="javascript:downPage('${formName}');">下一页</a><#else>下一页 </#if>
				</li>
				<li class="end">
					<#if page.lastPage><a href="javascript:endPage('${formName}');">尾页</a><#else>尾页 </#if>
				</li>
			</ul>
</#macro>