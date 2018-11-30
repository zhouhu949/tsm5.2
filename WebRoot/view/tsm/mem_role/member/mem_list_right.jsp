<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<style type="text/css">
	.com-search .hide-span{left: 100px;}
</style>
<script type="text/javascript" src="${ctx}/static/js/view/tsm/mem_role/memRight.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/page.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/bill/render_table.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript">
window.onload=function(){
	resetHeight();
};
function resetHeight(){
	var height = window.parent.$("#memb_right").height();
	window.parent.$("#iframepage").css({"height":height+"px"});
}
</script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js"></script>
</head>
<body>
<form id="myForm" action="${ctx}/auth/user/orgMemberRightList.do"method="post" data-render-url="${ctx}/auth/user/getOrgMemberRightJson">
<input type="hidden" id="groupId" name="groupId" value="${groupId}">
<input type="hidden" id="mark" name="mark" value="0">
<input type="hidden" id="isAdmin" name="isAdmin" value="${isAdmin}">
<div class="role-edit-cont clearfix">
<div class="com-search hyx-cm-search">
			<div class="com-search-box fl_l">
				<div class="search clearfix" >
				   <div class="select_outerDiv fl_l">
				   <span class="icon_down_5px"></span>
				    <select class="fl_l search_head_select"  name="queryType">
			   		<option value = "1" >成员帐号</option>
			   		<option value = "2" >成员姓名</option>
			   		<option value = "3" >通信号码</option>
			   		</select>
				   	</div>
				   	<input class="input-query fl_l" type="text" name="queryText" value="" />
				   	<label class="hide-span"></label>
				  </div>
			   <div class="list-box">
				  	  <!--到期时间 -->
						   <input type="hidden" name="startDate" id="d_startDate" value="" />
						   <input type="hidden" name="endDate" id="d_endDate" value=""/>
						   <input type="hidden" name="dDateType" id="dDateType" value="" />
				   <dl class="select">
						<dt>到期时间</dt>
						<dd>
							<ul>
								<li><a href="###" onclick="setDdate(0)" title="到期时间">到期时间</a></li>
								<li><a href="###" onclick="setDdate(1)" title="当天">当天</a></li>
								<li><a href="###" onclick="setDdate(2)" title="本周">本周</a></li>
								<li><a href="###" onclick="setDdate(3)" title="本月">本月</a></li>
								<li><a href="###" onclick="setDdate(4)" title="半年">半年</a></li>
								<li><a href="###" onclick="setDdate(5)" title="自定义" id="date-range1" class="diy date-range">自定义</a></li>
							</ul>
						</dd>
					</dl>
					<!-- 角色 -->
					<input type="hidden" name="roleId" id="d_roleId" value="" />
					<dl class="select">
						<dt>角色</dt>
						<dd>
							<ul>
								<li><a href="###" onclick="$('#d_roleId').val('')">角色</a></li>
								<c:forEach items="${roles }" var="rol">
									<li><a href="###" onclick="$('#d_roleId').val('${rol.roleId}')" title="${rol.roleName }">${rol.roleName}</a></li>
								</c:forEach>
							</ul>
						</dd>
					</dl>
					<!-- 职务 -->
					<input type="hidden" name="post" id="d_post" value="" />
					<dl class="select">
						<dt>职务</dt>
						<dd>
							<ul>
								<li><a href="###" onclick="$('#d_post').val('')">职务</a></li>
								<c:forEach items="${posts }" var="pos">
									<c:if test="${!empty pos }">
										<li><a href="###" onclick="$('#d_post').val('${pos}')" title="${pos}">${pos}</a></li>
									</c:if>
								</c:forEach>
							</ul>
						</dd>
					</dl>
				</div>
			</div>
			<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
</div>
		<div class="com-btnlist margin-trbl clearfix">
			<a href="javascript:;" class="com-btna role-edit-a fl_l" id="editRole"><i class="min-role-edit"></i><label class="lab-mar">角色编辑</label></a>
			<a href="javascript:;" class="com-btna role-edit-b fl_l" id="batchUpdatepwd"><i class="min-edit"></i><label class="lab-mar">修改密码</label></a>
			<a href="javascript:;" class="com-btna role-edit-b fl_l" id="batchUpdateDept"><i class="min-edit"></i><label class="lab-mar">变更部门</label></a>
			<a href="javascript:;" class="com-btna role-edit-b fl_l" id="batchCleanAccounts"><i class="min-edit"></i><label class="lab-mar">离职处理</label></a>
			<a href="javascript:;" class="com-btna role-edit-b fl_l" id="batchSettingPower"><i class="min-edit"></i><label class="lab-mar">管辖权限</label></a>
		</div>
		<div class="com-table test-table fl_l" style="display:block;">
			<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
				<thead>
					<tr class="sty-bgcolor-b">
						<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" class="check" id="checkAll"/></span></th>
						<th><span class="sp sty-borcolor-b">操作</span></th>
						<th><span class="sp sty-borcolor-b">角色</span></th>
						<th><span class="sp sty-borcolor-b">帐号</span></th>
						<th><span class="sp sty-borcolor-b">成员姓名</span></th>
						<th><span class="sp sty-borcolor-b">通信号码</span></th>
						<th><span class="sp sty-borcolor-b">所在部门</th>
						<th><span class="sp sty-borcolor-b">管辖权限</span></th>
						<th><span class="sp sty-borcolor-b"><label class="lab-d">到期时间</label></span></th>
						<th><span class="sp sty-borcolor-b">职务</span></th>
						<th><span class="sp sty-borcolor-b">手机号码</span></th>
						<th><span class="sp sty-borcolor-b">绑定F2机器人</span></th>
						<th><span class="sp sty-borcolor-b"><label class="lab-d">F2到期时间</label></span></th>
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
<script type="text/x-handlebars-template" id="template">
    {{#each list}}
        <tr class="{{even_odd @index}}">
            <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" class="check" name="memberAcc" id="chk_{{userId}}" memberId="{{userId}}" value="{{userAccount}}"/></div></td>
            <td style="width:70px;"><div class="overflow_hidden w70 link"><a href="javascript:;" onclick="openMemberEdit('{{userId}}')" class="icon-edit" title="编辑"></a></div></td>
            <td><div class="overflow_hidden w90" title="{{roleName}}">{{roleName}}</div></td>
            <td><div class="overflow_hidden w120" title="{{userAccount}}">{{userAccount}}</div></td>
            <td><div class="overflow_hidden w70" title="{{userName}}">{{userName}}</div></td>
            <td><div class="overflow_hidden w100" title="{{communicationNo}}">{{communicationNo}}</div></td>
            <td><div class="overflow_hidden w80" title="{{groupName}}">{{groupName}}</div></td>
            <td><div class="overflow_hidden w80" title="{{shareGroupNames}}">{{shareGroupNames}}</div></td>
            <td>
				<div class="overflow_hidden w100" >
				{{formatDate serveTime "YYYY-MM-DD"}}
                </div>
			</td>
            <td><div class="overflow_hidden w100" title="{{post}}">{{post}}</div></td>
            <td><div class="overflow_hidden w100" title="{{mobile}}">{{mobile}}</div></td>
			<td><div class="overflow_hidden w80" title="{{robotBinding}}">{{robotBinding}}</div></td>
			<td><div class="overflow_hidden w100" >{{formatDate robotDueTime "YYYY-MM-DD"}}</div></td>
		 </tr>
    {{/each}}
</script>
</body>
</html>
