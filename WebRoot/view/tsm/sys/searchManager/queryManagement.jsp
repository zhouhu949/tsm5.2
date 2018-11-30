<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<%@page import="com.qftx.tsm.sys.enums.SysEnum"%>
<title>系统设置-查询管理项</title>
<style>
	button.btn {border: 1px solid #d3d3d3; border-radius: 5px; cursor: pointer; margin: 0 10px; padding:0 15px;	line-height:22px;}
	.btn.btn-md {width: 100px; height: 30px; line-height: 30px;}
	.btn.btn-submit {color: #fff; background-color: #1979ca;}
	.btn.btn-submit:hover {background-color: #0f5aac;}
</style>
</head>
<body> 
<div class="com-table-a hyx-mpe-table hyx-cm-table fl_l" style="overflow-x:hidden;">
	<div class="hyx-na-tit fl_l"><label class="lab">查询管理项</label></div>
	<div class="hyx-sfs fl_l" style="overflow-x:hidden;">
		<dl class="tit">
			<dt class="com-red"><h2>温馨提示：</h2></dt>
			<dd>1.常用查询项勾选表示启用其字段在该页面作为查询条件；</dd>
			<dd>2.根据字段类型进行区分文本类型的查询字段为输入查询，单选、多选类型的查询字段为下拉查询；</dd>
			<dd>3.每个页面的列表页显示字段根据“列表显示”内的勾选决定，最少为4个；</dd>
			<dd>4.每个页面的下拉查询包含“所有者”查询项最多为10个，每个页面的输入查询项最少为1个；</dd>
		</dl>
		<p class="hyx-mpe-p" style="margin-left:1%;">
			<label>页面选择：</label>			
			<select style="width: 200px; height: 22px;" class="search-modules">
				<c:forEach items="<%=SysEnum.getSearchModules()%>" var="searchModule">
				  	<option value="${searchModule.state}" ${searchModule.state eq module ? 'selected':'' }>${searchModule.descr}</option>
            	</c:forEach>
			</select>
			<button type="button" class="btn btn-md btn-submit" style="float: right; margin-right: 10px;">保存</button>
		</p>
		<div class="com-table com-mta hyx-cm-table fl_l" style="display:block;">
			<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table" <c:if test="${!empty entitys }">data-entitys='${entitysJson }'</c:if>>
				<thead>
					<tr role="head" class="sty-bgcolor-b">
						<th><span class="sp sty-borcolor-b">序号</span></th>
						<th><span class="sp sty-borcolor-b">操作</span></th>
						<th><span class="sp sty-borcolor-b">字段</span></th>
						<th><span class="sp sty-borcolor-b">字段类型</span></th>
						<!-- <th><span class="sp sty-borcolor-b">searchCode</span></th> -->
						<!-- <th><span class="sp sty-borcolor-b">listCode</span></th> -->
						<th><span class="sp sty-borcolor-b">常用查询项</span></th>
						<th><span class="sp sty-borcolor-b">列表显示</span></th>
					</tr>
				</thead>
				<tbody>
					<%-- <c:choose>
					<c:when test="${!empty entitys }">
						<c:forEach items="${entitys }"  var="entity"  varStatus="vs">
							<tr class="${vs.count%2==0?'sty-bgcolor-b':''}">
								<td class="sort-num">${vs.count}</td>
								<td>
									<i class="fa fa-arrow-circle-o-up icon-20px icon-blue"></i>
									<i class="fa fa-arrow-circle-o-down icon-20px icon-blue"></i>
								</td>
								<td>${entity.searchName }</td>
								<td>
									<input type="checkbox" name="check_" />
								</td>
								<td>
									<input type="checkbox" name="check_" />
								</td>
							</tr>
						</c:forEach>
					</c:when>
					</c:choose> --%>
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
<script type="text/x-handlebars-template" id="template">
{{#each list}}
	<tr class="{{even_odd @index}}">
		<td class="sort-num">{{sort}}</td>
		<td>
			<i class="fa fa-arrow-circle-o-up icon-20px icon-blue"></i>
			<i class="fa fa-arrow-circle-o-down icon-20px icon-blue"></i>
		</td>
		<td>{{searchName}}</td>
		<td>{{judgeDataType dataType}}</td>
		{{! <td>{{addInput searchCode "searchCode"}}</td> }}
		{{! <td>{{addInput listCode "listCode"}}</td> }}
		<td class="is-query">{{judgeValue "isQuery"}}</td>
		<td class="is-show">{{judgeValue "isShow"}}</td>
	</tr>
{{/each}}
</script>
<!--本页面js-->
<script type="text/javascript" src="${ctx}/static/js/view/tsm/sys/query_management.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/handlebar.helper.js${_v}"></script>
</body>
</html>