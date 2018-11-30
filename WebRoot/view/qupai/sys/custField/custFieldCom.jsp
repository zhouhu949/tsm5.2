<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>业务对象管理-字段管理</title>
<style type="text/css">
	.hyx-sfs .tit dd{height: auto;}
	button.btn {border: 1px solid #d3d3d3; border-radius: 5px; cursor: pointer; margin: 0 10px; padding:0 15px;	line-height:22px;}
	.btn.btn-md {width: 100px; height: 30px; line-height: 30px;}
	.btn.btn-submit {color: #fff; background-color: #1979ca;}
	.btn.btn-submit:hover {background-color: #0f5aac;}
</style>

</head>
<body>
<form action="${ctx}/custField/quPai/custFieldQupaiPage.do" >
<div class="hyx-sfs com-table-a hyx-mpe-table hyx-cm-table fl_l" style="overflow-x:hidden;">
	<div class="year-sale-play">
		<ul class="ann-sale-plan clearfix">
			<a href="${ctx }/custField/quPai/custFieldQupaiPage.do"><li class="select li_first">字段管理</li></a>
			<a href="${ctx }/workflowSet/toWorkflowSetPage.do"><li class="li_last">工作流程设置</li></a>
			<a href="${ctx }/quPaiSearchManager/searchSetPage.do"><li class="li_last">查询项管理</li></a>
		</ul>
	</div>
	<dl class="tit">
		<dt class="com-red"><h2>温馨提示：</h2></dt>
		<dd>1.预设计算公式：用户到账金额=借款金额-各项服务费之和。</dd>
		<dd>2.预设计算公式：到期日=借款日+固定天数。</dd>
		<dd>3.引用字段可自动读取所引用的客户字段中数据值。</dd>
		<dd>4.自定义字段数量最多为18个，其中日期类型的字段数量最多为3个。</dd>
	</dl>
	<p class="hyx-mpe-p" style="margin-left:1%;">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">企业信息字段</label>
		<a href="###" class="com-red fl_r demoBtn_a" data-sure-url="${ctx}/custField/com/getIdFinedCount.do" data-url="${ctx }/custField/quPai/reditPage.do" data-count="18">+添加自定义字段</a>
		<button type="button" class="btn btn-md btn-submit" style="float: right; margin-right: 10px;" data-url="${ctx }/custField/quPai/modfiy">保存</button>
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
		<td>{{systemFieldOperateBtn "custFieldCom" fieldId sort "/custField/quPai/reditPage.do" fieldCode}}</td>
		<td>{{fieldName}}</td>
		<td>{{qupaiCustFieldJudgeDataType dataType}}</td>
		{{! <td>{{addInput searchCode "searchCode"}}</td> }}
		{{! <td>{{addInput listCode "listCode"}}</td> }}
		<td class="is-required">{{custFieldJudgeValue "isRequired"}}</td>
		<td class="is-read">{{custFieldJudgeValue "isRead"}}</td>
		<td class="is-enable">{{custFieldJudgeValue "enable"}}</td>
	</tr>
{{/each}}
</script>
<!--本页面js-->
<script type="text/javascript" src="${ctx}/static/js/view/qupai/system_field.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/handlebar.helper.js${_v}"></script>
</body>
</html>