<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>系统设置-系统字段设置（企业客户）-联系人字段</title>
<style type="text/css">
.panel{margin-left:1%;}
.hyx-sfs .tit dd{height:auto;}
button.btn {border: 1px solid #d3d3d3; border-radius: 5px; cursor: pointer; margin: 0 10px; padding:0 15px;	line-height:22px;}
.btn.btn-md {width: 100px; height: 30px; line-height: 30px;}
.btn.btn-submit {color: #fff; background-color: #1979ca;}
.btn.btn-submit:hover {background-color: #0f5aac;}
</style>
</head>
<body> 
<form action="${ctx}/custField/contacts/custFieldContactsPage.do" >
<div class="hyx-sfs com-table-a hyx-mpe-table hyx-cm-table fl_l" style="overflow-x:hidden;">
	<div class="year-sale-play hyx-hsrs-top">
		<ul class="ann-sale-plan clearfix">
			<a href="${ctx }/custField/com/custFieldComPage.do"><li class="li_first">企业字段</li></a>
			<a href="${ctx }/custField/contacts/custFieldContactsPage.do"><li class="select li_last">联系人字段</li></a>
		</ul>
	</div>
	<dl class="tit">
		<dt class="com-red"><h2>温馨提示：</h2></dt>
		<dd>1.点击操作列“上移”、“下移”可以对字段进行排序。</dd>
		<dd>2.联系人字段的自定义字段不能设置为作为查询条件。</dd>
		<dd>3.字段排序会影响的页面：“淘客户”，系统中修改、添加客户信息时的弹出页面。</dd>
	</dl>
	<p class="hyx-mpe-p" style="margin-left:1%;">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">联系人字段</label>
		<a href="###" class="com-red fl_r demoBtn_a" data-sure-url="${ctx}/custField/contacts/getIdFinedCount.do" data-url="${ctx }/custField/contacts/reditPage.do" data-count="5">+添加自定义字段</a>
		<button type="button" class="btn btn-md btn-submit" style="float: right; margin-right: 10px;" data-url="${ctx }/custField/contacts/modfiy">保存</button>
	</p>
	<div class="com-table com-mta hyx-cm-table fl_l" style="display:block;">
		<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table" <c:if test="${!empty entitys }">data-entitys='${entitysJson }'</c:if>>
			<thead>
				<tr role="head" class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">序号</span></th>
					<th><span class="sp sty-borcolor-b">操作</span></th>
					<th><span class="sp sty-borcolor-b">字段名称</span></th>
					<th><span class="sp sty-borcolor-b">字段类型</span></th>
					<!-- <th><span class="sp sty-borcolor-b">searchCode</span></th> -->
					<!-- <th><span class="sp sty-borcolor-b">listCode</span></th> -->
					<th><span class="sp sty-borcolor-b">必填</span></th>
					<th><span class="sp sty-borcolor-b">只读</span></th>
					<th><span class="sp sty-borcolor-b">启用</span></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<!-- 加载层 -->
		<div class="tip_search_data" >
			<div class="tip_search_text">
				<span><img alt="加载中" src="${ctx }/static/images/hua_loading.gif" /></span>
				<span>数据加载中…</span>
			</div>
			<div class="tip_search_error"></div>
		</div>
	</div>
</div>
</form>
<script type="text/x-handlebars-template" id="template">
{{#each list}}
	<tr class="{{even_odd @index}}">
		<td class="sort-num">{{serial_num @index}}</td>
		<td>{{systemFieldOperateBtn "custFieldContacts" fieldId sort "/custField/contacts/reditPage.do"}}</td>
		<td>{{fieldName}}</td>
		<td>{{judgeDataType dataType}}</td>
		{{! <td>{{addInput searchCode "searchCode"}}</td> }}
		{{! <td>{{addInput listCode "listCode"}}</td> }}
		<td class="is-required">{{custFieldJudgeValue "isRequired"}}</td>
		<td class="is-read">{{custFieldJudgeValue "isRead"}}</td>
		<td class="is-enable">{{custFieldJudgeValue "enable"}}</td>
	</tr>
{{/each}}
</script>
<!--本页面js-->
<script type="text/javascript" src="${ctx}/static/js/view/tsm/sys/system_field.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/handlebar.helper.js${_v}"></script>
</body>
</html>