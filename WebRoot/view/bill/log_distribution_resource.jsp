<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>资源操作-资源操作日志</title>
    <style type="text/css"></style>
    <%@ include file="/common/include.jsp" %>
</head>
<body>
	<form id="cform" action="${ctx }/resOperate/getLogResOperateJson" method="post" data-render-url="${ctx }/resOperate/getLogResOperateJson">
		<div class="hyx-ctr" >
		   <div class="com-search hyx-cfu-search">
		      <div class="com-search-box fl_l">
			  <!--  <p class="search clearfix"><input class="input-query fl_l" type="text" name="queryText" value="" placeholder="请输入操作人姓名"/></p> -->
			   <div class="list-box">
				    <input type="hidden" name="startDate" id="s_startDate" value="" />
				    <input type="hidden" name="endDate" id="s_endDate" value="" />
				    <input type="hidden" name="dDateType" id="dDateType" value="" />
				    <dl class="select">
						<dt>操作时间</dt>
						<dd>
							<ul>
								<li><a href="javascript:;" onclick="setDdate(0)" title="操作时间">操作时间</a></li>
								<li><a href="javascript:;" onclick="setDdate(1)" title="当天">当天</a></li>
								<li><a href="javascript:;" onclick="setDdate(2)" title="本周">本周</a></li>
								<li><a href="javascript:;" onclick="setDdate(3)" title="本月">本月</a></li>
								<li><a href="javascript:;" onclick="setDdate(4)" title="半年">半年</a></li>
								<li><a href="javascript:;" title="自定义" class="diy date-range" id="dDate">自定义</a></li>
							</ul>
						</dd>
					</dl>
          <%-- 员工姓名 --%>
          <input type="hidden" id="accountsStr" name="ownerAcc" value="" />
				  <div class="reso-sub-dep fl_l">
						<input class="owner-sour" id="userNames" readonly="readonly" type="text" value="员工姓名" data-defaultValue="员工姓名">
						<div class="manage-owner-sour">
							<div class="sub-dep-ul" data-type="search-tree">
								<ul id="tt1">

						        </ul>
				    		</div>
						    <div class="sure-cancle clearfix" style="width:120px">
								<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" onclick="seleTree()" href="javascript:;"><label>确定</label></a>
								<a class="com-btnd bomp-btna fl_l" onclick="unCheckTree()" href="javascript:;"><label>清空</label></a>
							</div>
						</div>
					</div>

				   <input type="hidden" name="type" id="type" value="" />
				   <dl class="select">
						<dt>操作类型</dt>
						<dd>
							<ul>
								<li><a href="javascript:;" onclick="$('#type').val('')" title="操作类型">操作类型</a></li>
								<li><a href="javascript:;" onclick="$('#type').val('3002')" title="资源分配">资源分配</a></li>
								<li><a href="javascript:;" onclick="$('#type').val('3003')" title="资源回收">资源回收</a></li>
								<li><a href="javascript:;" onclick="$('#type').val('3004')" title="资源删除">资源删除</a></li>
								<li><a href="javascript:;" onclick="$('#type').val('3005')" title="资源清空">资源清空</a></li>
							</ul>
						</dd>
				   </dl>
				<!--操作人  -->
				  <input type="hidden" id="operateAcc" name="operateAcc" value="" />
				  <div class="reso-sub-dep fl_l">
						<input class="owner-sour" id="operateName" readonly="readonly" type="text" value="操作人" data-defaultValue="操作人">
						<div class="manage-owner-sour">
							<div class="sub-dep-ul" data-type="search-tree">
								<ul id="tt3">

						        </ul>
				    		</div>
						    <div class="sure-cancle clearfix" style="width:120px">
								<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" onclick="seleTree3()" href="javascript:;"><label>确定</label></a>
								<a class="com-btnd bomp-btna fl_l" onclick="unCheckTree3()" href="javascript:;"><label>清空</label></a>
							</div>
						</div>
					</div>

				</div>
			  </div>
				<input class="query-button fl_r" type="button" value="查&nbsp;询" id="queryByConditions"/>
		   </div>
		   <div class="com-table com-mta hyx-cla-table fl_l" style="display:block;margin-top:5px !important;">
				<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
					<thead>
						<tr role="head" class="sty-bgcolor-b">
							<th><span class="sp sty-borcolor-b">操作时间</span></th>
							<th><span class="sp sty-borcolor-b">员工帐号</span></th>
							<th><span class="sp sty-borcolor-b">员工姓名</span></th>
							<th><span class="sp sty-borcolor-b">操作类型</span></th>
							<th><span class="sp sty-borcolor-b" sort="true">操作数量</span></th>
							<th>操作人</th>
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
				<div class="tip_search_data" >
					<div class="tip_search_text">
						<span><img alt="加载中" src="${ctx }/static/images/hua_loading.gif" /></span>
						<span>数据加载中…</span>
					</div>
					<div class="tip_search_error"></div>
				</div>
			</div>
			<div class="com-bot" >
				<div class="com-page fl_r">
			          <div class="page" id="new_page"></div>
			          <div class="clr_both"></div>
		        </div>
			</div>
		</div>
	</form>
<script type="text/javascript" src="${ctx }/static/js/view/bill/member_pakage_list.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/page.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/bill/render_table.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/x-handlebars-template" id="template">
  {{#each list}}
	<tr class="{{even_odd @index}}">
		<td><div class="overflow_hidden w130" title="{{formatDate operateDate 'YYYY-MM-DD HH:mm:ss'}}">{{formatDate operateDate 'YYYY-MM-DD HH:mm:ss'}}</div></td>
		<td><div class="overflow_hidden w70" title="{{ownerAcc }}">{{ownerAcc }}</div></td>
		<td><div class="overflow_hidden w70" title="{{ownerName }}">{{ownerName }}</div></td>
		<td>
			<div class="overflow_hidden w120">
				{{resStateStr type}}
			</div>
		</td>
		<td><div class="overflow_hidden w120" title="{{size}}">{{size}}</div></td>
		<td><div class="overflow_hidden w120" title="{{operateName}}">{{operateName}}</div></td>
    </tr>
 {{/each}}
</script>
</body>
</html>
