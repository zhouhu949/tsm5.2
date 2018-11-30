<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
		<%@ include file="/common/include.jsp" %>
	</head>
	<body>
		<form data-render-url="${ctx }/newWill/OldDayNewWillSum">
			<input type="hidden" name="currDate" value="${currDate}" />
			<input type="hidden" name="selectType" value="${selectType}" />
			<input type="hidden" name="startDate" value="${startDate}" />
			<input type="hidden" name="endDate" value="${endDate }" />
			<input type="hidden" name="groupId" value="${groupId}" />
			<input type="hidden" name="userAccount" value="${userAccount}" />
			<div class="com-table com-mta hyx-cla-table">
	            <table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
	                <thead>
	                <tr role="head" class="sty-bgcolor-b">
	                    <th><span class="sp sty-borcolor-b">操作</span></th>
	                    <th><span class="sp sty-borcolor-b">日期</span></th>
	                    <th><span class="sp sty-borcolor-b">${shiroUser.isState eq 1 ? '客户名称':'客户姓名' }</span></th>
	                    <th><span class="sp sty-borcolor-b">${shiroUser.isState eq 1 ? '联系人':'单位名称' }</span></th>
	                    <th><span class="sp sty-borcolor-b">本日初始状态</span></th>
	                    <th><span class="sp sty-borcolor-b">本日结束状态</span></th>
                    	<th><span class="sp sty-borcolor-b">所属小组</span></th>
	                    <th><span class="sp sty-borcolor-b">销售姓名</span></th>
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
		</form>
		<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
		<script type="text/javascript" src="${ctx}/static/js/common/page.js${_v}"></script>
		<script type="text/javascript" src="${ctx }/static/js/view/bill/render_table.js${_v}"></script>
		<script type="text/x-handlebars-template" id="template">
			{{#each list}}
			<tr class="{{even_odd @index}}">
				<td>
					<div class="overflow_hidden w40 link">
						{{userDayDataIdialogBtn ../isState showCard custId custName company}}
					</div>
				</td>
				<td>
					<div class="overflow_hidden w80" title="{{currDate }}">{{currDate }}</div>
				</td>
					<td>
						<div class="overflow_hidden w120" title="{{custName }}">{{custName }}</div>
					</td>
					{{#if ../isState}}
					<td>
					<div class="overflow_hidden w80" title="{{mainLinkman }}">{{mainLinkman }}</div>
					</td>
					{{else}}
					<td>
		            	<div class="overflow_hidden w150" title="{{company }}">{{company }}</div>
		        	</td>
				{{/if}}
				<td>
					<div class="overflow_hidden w80" title="{{toDateInitType }}">{{toDateInitType }}</div>
				</td>
				<td>
					<div class="overflow_hidden w50" title="意向-{{currProcessName }}">意向-{{currProcessName }}</div>
				</td>
				<td>
					<div class="overflow_hidden w80" title="{{groupName }}">{{groupName }}</div>
				</td>
				<td>
					<div class="overflow_hidden w80" title="{{ownerAcc }}">{{ownerAcc }}</div>
				</td>
			</tr>
			{{/each}}
		</script>
		<script type="text/javascript">
			$(function(){
				//客户卡片
			    $("table").delegate("a[id^=cardInfo_]","click",function(){
			    	var custId = $(this).attr("id").split("_")[1];
					var custName = $(this).attr("custName")||"客户卡片";
					window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName);
			    });
			});
		</script>
	</body>
</html>
