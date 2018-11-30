<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>操作日志</title>
    <style type="text/css"></style>
    <%@ include file="/common/include.jsp" %>
</head>
<body>
	<form id="operationform" action="" method="">
	  <input type="hidden" id="comp_type" name="type" value="0">
		<div class="hyx-ctr" >
		   <div class="com-search hyx-cfu-search">
		      <div class="com-search-box fl_l">
				   <div class="list-box">
					    <input type="hidden" name="startDate" id="s_startDate" value="${optorDto.startDate }" />
					    <input type="hidden" name="endDate" id="s_endDate" value="${optorDto.endDate }" />
					    <input type="hidden" name="dDateType" id="dDateType" value="${optorDto.dDateType }" />
					    <dl class="select">
							<dt>操作时间</dt>
							<dd>
								<ul>
									<li><a href="javascript:;" onclick="setDdate(0)" title="操作时间">操作时间</a></li>
									<li><a href="javascript:;" onclick="setDdate(1)" title="当天">当天</a></li>
									<li><a href="javascript:;" onclick="setDdate(2)" title="本周">本周</a></li>
									<li><a href="javascript:;" title="自定义" class="diy date-range" id="dDate">自定义</a></li>
								</ul>
							</dd>
						</dl>
	          		<%-- 操作人 --%>
	          		<input type="hidden" id="accountsStr" name="accountsStr" value="" />
					  <div class="reso-sub-dep fl_l">
							<input class="owner-sour" id="userNames"  readonly="readonly" type="text" value="帐号" style="border-right: 2px solid #e1e1e1;">
							<div class="manage-owner-sour">
								<div class="sub-dep-ul">
									<ul id="tt1">

							        </ul>
					    		</div>
							    <div class="sure-cancle clearfix" style="width:120px">
									<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" onclick="seleTree()" href="javascript:;"><label>确定</label></a>
									<a class="com-btnd bomp-btna fl_l" onclick="unCheckTree()" href="javascript:;"><label>清空</label></a>
								</div>
							</div>
						</div>
						<!-- 模块 -->
						<input type="hidden" id="groupIdStr" name="moduleStr" value="" />
						  <div class="reso-sub-dep fl_l">
								<input class="owner-sour" id="groupNameStr"  readonly="readonly" type="text" value="模块" >
								<div class="manage-owner-sour">
									<div class="sub-dep-ul">
										<ul id="tt2">

								        </ul>
						    		</div>
								    <div class="sure-cancle clearfix" style="width:120px">
										<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" onclick="seleGroupTree()" href="javascript:;"><label>确定</label></a>
										<a class="com-btnd bomp-btna fl_l" onclick="unCheckGroupTree()" href="javascript:;"><label>清空</label></a>
									</div>
								</div>
							</div>
					</div>
			  </div>
				<input class="query-button fl_r" placepholder="请输入员工姓名" type="submit" value="查&nbsp;询" id="queryByConditions" />
		   </div>

		   <div class="com-table com-mta hyx-cla-table fl_l" style="display:block;margin-top:5px !important;">
				<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
					<thead>
						<tr role="head" class="sty-bgcolor-b">
							<th><span class="sp sty-borcolor-b">操作时间</span></th>
							<th><span class="sp sty-borcolor-b">员工帐号</span></th>
							<th><span class="sp sty-borcolor-b">员工姓名</span></th>
							<th><span class="sp sty-borcolor-b">模块</span></th>
							<th><span class="sp sty-borcolor-b">功能操作</span></th>
							<th>备注说明</th>
						</tr>
					</thead>
			          <tbody>
			            <tr class="no_data_tr"><td></td></tr>
			            <tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
			            <tr class="no_data_tr"><td></td></tr>
			            <tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
			            <tr class="no_data_tr"><td></td></tr>
			            <tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
			            <tr class="no_data_tr"><td></td></tr>
			            <tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
			            <tr class="no_data_tr"><td></td></tr>
			            <tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
			          </tbody>
				</table>
			</div>
	      <div class="com-bot" >
		        <div class="com-page fl_r">
			          <div class="page" id="new_page"></div>
			          <div class="clr_both"></div>
		        </div>
	      </div>
		</div>
	</form>
 <script type="text/javascript" src="${ctx}/static/js/common/page.js${_v}"></script>
 <script type="text/javascript" src="${ctx}/static/js/view/worklog/operationlog.js${_v}"></script>
 <script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
  <script type="text/x-handlebars-template" id="template">
  {{#each list}}
	<tr class="{{even_odd @index}}" {{#if systemOperateId}}systemOperateId="{{systemOperateId}}" {{/if}}{{#if id}}id="{{id}}" {{/if}}>
		<td>
      {{formatDate inputTime "YYYY-MM-DD HH:mm:ss"}}
		</td>
		<td>
      {{userAccount}}
		</td>
		<td>
			{{userName}}
		</td>
		<td>
			{{moduleName}}
		</td>
		<td>
			{{operateName}}
		</td>
		<td>
			<div class="w150 overflow_hidden" title="{{content}}">{{content}}</div>
		</td>
	</tr>
 {{/each}}
</script>

</body>
</html>
