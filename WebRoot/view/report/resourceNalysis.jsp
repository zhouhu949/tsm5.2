<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<title>资源分析</title>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${ctx }/static/js/view/report/resourceNalysis.js${_v}"></script>
</head>
<body>
<form id="resurce_form">
	<div class="resource-nalysis-big-box">
		<div class="resource-nalysis-big-box-head">
			 <label class="fl_l" for="">选择日期：</label>
			<span class="fl_l" style="display: inline-block; position: relative;"><!-- 限制日期选择只能选择一个月 -->
			<input name="strDate" value="" type="text" class="selec-year-inpu com-form-data fl_l" />
			<span class="silent-custo-span fl_l">&nbsp;—&nbsp;</span>
			<input name="endDate" value="" type="text" class="selec-year-inpu com-form-data fl_l" />
			</span>
			<div class="com-search-report fl_l" style="margin-top:0;min-height:0">
	            <label class="fl_l" for="" >资源分组：</label>
	            <input type="hidden" id="groupIds" name="groupIds" value="" />
	            <dl class="select fl_l" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-right:10px;">
					<dt id="groupNameStr">-请选择-</dt>
					<dd>
						<ul id="tt1">
								
						</ul>
						<div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;">
							<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l"  id="resource_sure_btn" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">确定</label></a>
							<a class="com-btnd bomp-btna com-btna-cancle fl_l"  id="resource_cancle_btn"  href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">清空</label></a>
						</div>
					</dd>
				</dl>
	        </div>
			<a href="javascript:;" id="searchBtn" class="com-btna fl_l search_btn" ><i class="min-search"></i><label class="lab-mar">统计</label></a>
	        <a href="javascript:;" id="export" class="com-btna fl_r" data-href="${ctx }/Resource/exportResourceData?"><i class="min-remove-resou"></i><label class="lab-mar">导出</label></a>
	       </div>
			<p class="hyx-silent-p">
		        <label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">资源组分析</label>
		    </p>
			<div class="com-table hyx-mpe-table hyx-cm-table">
				<table width="100%" cellspacing="0" cellpadding="0" id="t1" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
					<thead>
						<tr class="sty-bgcolor-b">
							<th><span class="sp sty-borcolor-b">资源分类</span></th> 
							<th><span class="sp sty-borcolor-b">资源组</span></th> 
							<th><span class="sp sty-borcolor-b">客户总数</span></th>
							<th><span class="sp sty-borcolor-b">意向客户数</span></th>
							<th><span class="sp sty-borcolor-b">签约客户数</span></th>
							<th><span class="sp sty-borcolor-b">接通数</span></th>
							<th><span class="sp sty-borcolor-b">有效呼叫数</span></th>
							<th>信息错误数</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
	</div>
	</form>
	<script type="text/x-handlebars-template" id="template">
	{{#each list}}
		<tr class="{{GroupFlId}}">
			<td rowspan="{{return_len valus}}">{{GroupFlName}}</td>
			{{#each valus}}
			{{#compare @index "==" 0}}
			<td>{{groupName}}</td>
			<td>{{resCount}}</td>
			<td>{{willCountstr}}</td>
			<td>{{signCountstr}}</td>
			<td>{{callCountstr}}</td>
			<td>{{vaildCallCountstr}}</td>
			<td>{{errorCountstr}}</td>
			{{/compare}}
			{{/each}}
		</tr>
		{{#each valus}}
			{{#compare @index ">" 0}}
			<tr>
			<td>{{groupName}}</td>
			<td>{{resCount}}</td>
			<td>{{willCountstr}}</td>
			<td>{{signCountstr}}</td>
			<td>{{callCountstr}}</td>
			<td>{{vaildCallCountstr}}</td>
			<td>{{errorCountstr}}</td>
			</tr>
			{{/compare}}
			{{/each}}
	{{/each}}
	</script>
</body>
</html>
