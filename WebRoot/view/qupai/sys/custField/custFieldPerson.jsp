<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>系统设置-系统字段设置（个人）</title>
<style type="text/css">
.panel{margin-left:1%;}
.hyx-sfs .tit dd{height:auto;}
button.btn {border: 1px solid #d3d3d3; border-radius: 5px; cursor: pointer; margin: 0 10px; padding:0 15px;	line-height:22px;}
.btn.btn-md {width: 100px; height: 30px; line-height: 30px;}
.btn.btn-submit {color: #fff; background-color: #1979ca;}
.btn.btn-submit:hover {background-color: #0f5aac;}
</style>
<!--本页面js-->
</head>
<body>
<form action="${ctx}/custField/person/custFieldPersonPage.do" > 
<div class="com-table-a hyx-mpe-table hyx-cm-table fl_l" style="overflow-x:hidden;">
	<div class="hyx-na-tit fl_l"><label class="lab">系统字段</label></div>
	<div class="hyx-sfs fl_l" style="overflow-x:hidden;">
		<dl class="tit">
			<dt class="com-red"><h2>温馨提示：</h2></dt>
			<dd>1.点击操作列“上移”、“下移”可以对字段进行排序，客户姓名字段不能被重新排序；</dd>
			<dd>2.可以被设置为查询条件的自定义字段最多为五项，选择后的查询条件会影响的页面：客户管理模块中的“我的客户-资源”、“我的客户-意向客户”、“我的客户-签约客户”、“我的客户-全部客户”、“流失客户”、“公海客户”、“客户交接”，资源管理模块中的“待分配资源”、“已分配资源”、“全部资源”,客户跟进模块中的“客户跟进”，联系人的自定义字段不能作为查询条件。</dd>
			<dd>3.设置的自定义单选、多选字段的数据项值在“系统属性”中设置；</dd>
			<dd>4.自定义字段数量最多为18个，其中日期类型的字段数量最多为3个。</dd>
		</dl>
		<p class="hyx-mpe-p" style="margin-left:1%;">
			<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">个人信息字段</label>
			<a href="###" class="com-red fl_r demoBtn_a" data-sure-url="${ctx}/custField/person/getIdFinedCount.do" data-url="${ctx }/custField/person/reditPage.do" data-count="18">+添加自定义字段</a>
			<button type="button" class="btn btn-md btn-submit" style="float: right; margin-right: 10px;" data-url="${ctx }/custField/person/modfiy">保存</button>
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
</div>
</form>
<script type="text/x-handlebars-template" id="template">
{{#each list}}
	<tr class="{{even_odd @index}}">
		<td class="sort-num">{{serial_num @index}}</td>
		<td>{{systemFieldOperateBtn "custFieldPerson" fieldId sort "/custField/person/reditPage.do"}}</td>
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