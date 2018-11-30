<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-历年计划详情查询</title>
<!--本页面样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js"></script>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js"></script>
<script type="text/javascript">
$(function(){

	$("#cfm01").click(function(){
		confirmGroupTree();
	});

	$("#close01").click(function(){
		clearGroupTree();
	});

	$("#cfm02").click(function(){
		confirmUserTree();
	});

	$("#close02").click(function(){
		clearUserTree();
	});

    treeInit();
});

function planYear(value){
	$("#planYear").val(value);
}
function planMonth(value){
	$("#planMonth").val(value);
}
function status(value){
	$("#status").val(value);
}


function treeInit(){
	$("#groupTree").tree({
		url:ctx+"/orgGroup/get_group_json",
		checkbox:true,
		onLoadSuccess:function(node,data){
			var oas = $("#groupIds").val();
			if(oas != null && oas != ''){
				$.each(oas.split(','),function(index,obj){
					var n = $("#groupTree").tree("find",obj);
					$("#groupTree").tree("check",n.target).tree("expandTo",n.target);
				});
			}
		}
	});

	$("#userTree").tree({
		url:ctx+"/orgGroup/get_group_user_json",
		checkbox:true,
		onLoadSuccess:function(node,data){
			var oas = $("#userAccs").val();
			if(oas != null && oas != ''){
				$.each(oas.split(','),function(index,obj){
					var n = $("#userTree").tree("find",obj);
					$("#userTree").tree("check",n.target).tree("expandTo",n.target);
				});
			}
		}
	});
}


function confirmUserTree(){

	var nodes = $('#userTree').tree('getChecked', 'checked');
	var accArray = new Array();
	var idArray = new Array();
	$.each(nodes,function(index,obj){
		var type = obj.attributes.type;
		if(type == "M"){
			accArray.push(obj.id);
			idArray.push(obj.user_id);
		}
	});

	$("#userAccs").val(accArray.toString());
	$("#userIds").val(idArray.toString());
}

function clearUserTree(){
	var nodes = $('#userTree').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#userTree').tree("uncheck",obj.target);
	});
	$("#userAccs").val('');
	$("#userIds").val('');
}

function confirmGroupTree(){
	var nodes = $('#groupTree').tree('getChecked', 'checked');
	var idArray = new Array();
	$.each(nodes,function(index,obj){
		idArray.push(obj.id);
	});
	$("#groupIds").val(idArray.toString());
}

function clearGroupTree(){
	var nodes = $('#groupTree').tree('getChecked', 'checked');
	$.each(nodes,function(index,obj){
		 $('#groupTree').tree("uncheck",obj.target);
	});
	$("#groupIds").val('');
}

</script>
</head>
<body>
<form id="historyForm" action="/plan/month/user/historyView" method="post">
<input type="hidden" id="planYear" name="planYear" value="${item.planYear}"/>
<input type="hidden" id="planMonth" name="planMonth" value="${item.planMonth}"/>
<input type="hidden" id="status" name="status" value="${item.status}"/>
<input id="groupIds" name="groupIds" type="hidden" value="${groupIds}"/>
<input id="userIds" name="userIds" type="hidden" value="${userIds}"/>
<input id="userAccs" name="userAccs" type="hidden" value="${userAccs}"/>

<div class="hyx-cfu-left hyx-ctr hyx-sc">
   <div class="com-search hyx-cfu-search">
      <div class="com-search-box fl_l">
	   <div class="search clearfix" >
		   <div class="select_outerDiv fl_l">
		   <span class="icon_down_5px"></span>
		   	<select name="queryType" class="fl_l search_head_select">
		   		<c:if test="${isState eq 0}">
		   			<option value="2" ${(empty item.queryType or item.queryType eq '2') ? 'selected' : ''}>单位名称</option>
		   		</c:if>
		   		<option value="1" ${item.queryType eq '1' ? 'selected' : ''}>${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</option>
		   	</select>
		   	</div>
		   	<input class="input-query fl_l" type="text" name="queryText" value="${item.queryText}" />
		   	<label class="hide-span"></label>
	  </div>

	   <div class="list-box">
			<dl class="select">
				<c:choose>
				<c:when test="${item.planYear!=null }">
					<dt>${item.planYear}年</dt>
				</c:when>
				<c:otherwise>
					<dt>年份选择</dt>
				</c:otherwise>
				</c:choose>
				<dd>
					<ul>
						<li><a href="javascript:planYear(null);" title="年份选择">年份选择</a></li>
						<c:forEach items="${years}" var="year">
						<li><a href="javascript:planYear(${year});" title="${year}年">${year}年</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
			<dl class="select">
				<c:choose>
				<c:when test="${item.planMonth!=null }">
					<dt>${item.planMonth}月</dt>
				</c:when>
				<c:otherwise>
					<dt>月份选择</dt>
				</c:otherwise>
				</c:choose>
				<dd>
					<ul>
						<li><a href="javascript:planMonth(null);" value ="" title="月份选择">月份选择</a></li>
						<c:forEach items="${months}" var="month">
						<li><a href="javascript:planMonth(${month});" title="${month}月">${month}月</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
			<dl class="select">
				<c:choose>
				<c:when test="${item.status==0 }"><dt>未完成</dt></c:when>
				<c:when test="${item.status==1 }"><dt>已完成</dt></c:when>
				<c:otherwise><dt>完成情况</dt></c:otherwise>
				</c:choose>
				<dd>
					<ul>
						<li><a href="javascript:status(null);" title="完成情况">完成情况</a></li>
						<li><a href="javascript:status(1);" title="已完成">已完成</a></li>
						<li><a href="javascript:status(0);" title="未完成">未完成</a></li>
					</ul>
				</dd>
			</dl>
			<!-- <div class="reso-sub-dep fl_l">
				<input class="sub-dep-inpu" type="text" value="部门选择" style="border-right:2px solid #e1e1e1">
				<div class="manage-drop">
					<div class="sub-dep-ul">
						<ul id="groupTree" class="easyui-tree" data-options="animate:false,dnd:false"></ul>
				    </div>
				    <div class="sure-cancle clearfix" style="width:120px">
						<a id="cfm01" class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" href="javascript:confirmGroupTree();"><label>确定</label></a>
						<a id="close02" class="com-btnd bomp-btna fl_l"  href="javascript:clearGroupTree();"><label>清空</label></a>
					</div>
				</div>
			</div> -->
			<div class="reso-sub-dep fl_l" >
				<input class="owner-sour" type="text" value="销售人员" style="cursor:pointer;"/>
				<div class="manage-owner-sour">
					<div class="sub-dep-ul">
						<ul id="userTree" class="easyui-tree" ></ul>
		    		</div>
				    <div class="sure-cancle clearfix" style="width:120px">
						<a id="cfm02" class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" href="javascript:;"><label>确定</label></a>
						<a id="close02" class="com-btnd bomp-btna fl_l" href="javascript:;"><label>清空</label></a>
					</div>
				</div>
			</div>
		</div>
	</div>
		<input class="query-button fl_r" type="submit" value="查&nbsp;询" id="query"/>
   </div>
	<div class="com-table com-mta hyx-cla-table" style="display:block;margin-top:45px !important;">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">年份</span></th>
					<th><span class="sp sty-borcolor-b">月份</span></th>
					<th><span class="sp sty-borcolor-b">部门</span></th>
					<th><span class="sp sty-borcolor-b">销售人员</span></th>
					<th><span class="sp sty-borcolor-b">${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</span></th>
					<c:if test="${isState eq 0}"><th><span class='sp sty-borcolor-b'>单位名称</span></th></c:if>
					<th><span class="sp sty-borcolor-b">意向客户数（个）</span></th>
					<th><span class="sp sty-borcolor-b">签约客户数（个）</span></th>
					<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
					<th>完成情况</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
				<c:when test="${!empty list}">
				<c:forEach var="plan" items="${list}" varStatus="i">
					<c:if test="${i.index%2!=0}"><tr class="sty-bgcolor-b"></c:if>
					<c:if test="${i.index%2==0}"><tr></c:if>
					<td><div class="overflow_hidden w70" title="${plan.planYear }">${plan.planYear }年</div></td>
					<td><div class="overflow_hidden w70" title="${plan.planMonth }月">${plan.planMonth }月</div></td>
					<td><div class="overflow_hidden w100" title="${plan.groupName }">${plan.groupName }</div></td>
					<td><div class="overflow_hidden w70" title="${plan.userName }">${plan.userName }</div></td>
					<td><div class="overflow_hidden w70" title="${plan.custName }">${plan.custName }</div></td>
					<c:if test="${isState eq 0}">
						<td><div class="overflow_hidden w180" title="${plan.company}">${plan.company}</div></td>
					</c:if>
					<td><div class="overflow_hidden w70" title="${plan.willcustNum }">${plan.willcustNum }</div></td>
					<td><div class="overflow_hidden w70" title="${plan.singcustNum }">${plan.singcustNum }</div></td>
					<td><div class="overflow_hidden w70" title="${plan.planMoney }">${plan.planMoney }</div></td>
					<td>
					<c:if test="${plan.status ==0}">
						<div class="overflow_hidden w100" title="未完成">未完成</div>
					</c:if>
					<c:if test="${plan.status ==1 or plan.status ==2}">
						<div class="overflow_hidden w100" title="未完成">完成</div>
					</c:if>
					</td>

				</tr>
				</c:forEach>
				</c:when>
				<c:otherwise>
					<tr><td colspan="${isState eq 0?10:9}" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
				</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	<div class="com-bot" >${item.page.pageStr}</div>
</div>
</form>
</body>
</html>
