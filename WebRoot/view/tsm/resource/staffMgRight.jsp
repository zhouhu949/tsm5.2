<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/view/tsm/res/queryStaffList.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/common.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/page.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/tsm/res/rescommon.js${_v}"></script>
<style>
	.tree-node{width:240px;}
	.com-search .hide-span{left: 10px;}
</style>
<script type="text/javascript">
  $(function(){
    var queryStartRemain = $('#queryStartRemain').val();
    var queryEndRemain = $('#queryEndRemain').val();
    if(queryEndRemain!='' || queryStartRemain !=''){
    	$('#remainResId').val('资源剩余量(' + queryStartRemain+"-" + queryEndRemain+')');
    }
    $('#remainSubmitId').click(function(){
        var queryStartRemain = $('#queryStartRemain').val();
        var queryEndRemain = $('#queryEndRemain').val();
        if(queryEndRemain!='' || queryStartRemain !=''){
        	$('#remainResId').val('资源剩余量(' + queryStartRemain+"-" + queryEndRemain+')');
        }
    })

    $('#close02').click(function(){
    	$('#remainResId').val('资源剩余量');
        $('#queryStartRemain').val('');
        $('#queryEndRemain').val('');
    })
  })
	window.onload=function(){
	var height = $(".hyx-spsa").height()+30;
	window.parent.$("#iframepage").css({"height":height+"px"});
	};
</script>
<form id="myForm" action="${ctx}/res/staffMg/queryStaffResMgList"
	method="post">
	<input type="hidden" id="groupId" value="${groupId}" name="groupId">
    <input type="hidden" id="mark" name ="mark" value="${mark}">
	<input type="hidden" id="base" value="${ctx}">
	<div class="hyx-spsa clearfix">
	<div class="com-search hyx-cm-search">
		<div class="com-search-box fl_l">
			<p class="search clearfix">
				<input class="input-query fl_l" type="text" value="${staffDto.queryContition }" name= "queryContition"/><label class="hide-span">输入帐号/姓名</label>
			</p>
			   <div class="list-box">
			   		<c:set var="dDateName" value="到期时间" />
				    <c:choose>
				   		<c:when test="${staffDto.dDateType eq 1 }"><c:set var="dDateName" value="当天" /></c:when>
				   		<c:when test="${staffDto.dDateType eq 2 }"><c:set var="dDateName" value="本周" /></c:when>
				   		<c:when test="${staffDto.dDateType eq 3 }"><c:set var="dDateName" value="本月" /></c:when>
				   		<c:when test="${staffDto.dDateType eq 4 }"><c:set var="dDateName" value="半年" /></c:when>
				   		<c:when test="${staffDto.dDateType eq 5 }">
				   			<fmt:parseDate var="sd" value="${staffDto.startDate }" pattern="yyyy-MM-dd" />
					   		<fmt:parseDate var="ed" value="${staffDto.endDate }" pattern="yyyy-MM-dd" />
				   			<c:set var="dDateName">
				   				<fmt:formatDate value="${sd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ed }" pattern="yy.MM.dd"/>
				   			</c:set>
				   		</c:when>
				    </c:choose>
				    <input type="hidden" name="startDate" id="s_startDate" value="${staffDto.startDate }" />
				    <input type="hidden" name="endDate" id="s_endDate" value="${staffDto.endDate }" />
				    <input type="hidden" name="dDateType" id="dDateType" value="${staffDto.dDateType }" />
				    <dl class="select prec-cont-time">
						<dt>${dDateName }</dt>
						<dd>
							<ul>
								<li><a href="javascript:;" onclick="setDdate(0)" title="到期时间">到期时间</a></li>
								<li><a href="javascript:;" onclick="setDdate(1)" title="当天">当天</a></li>
								<li><a href="javascript:;" onclick="setDdate(2)" title="本周">本周</a></li>
								<li><a href="javascript:;" onclick="setDdate(3)" title="本月">本月</a></li>
								<li><a href="javascript:;" onclick="setDdate(4)" title="半年">半年</a></li>
								<li><a href="javascript:;" title="自定义" class="diy date-range" id="dDate">自定义</a></li>
							</ul>
						</dd>
					</dl>
					<div class="reso-sub-dep fl_l" style="margin-bottom:5px;">
						<input class="sub-dep-inpu" type="text" id="remainResId" readonly="readonly" value="资源剩余量">
						<div class="manage-drop" style="height:130px">
							<div class="reso-surp">
								<input type="text" value="${staffDto.queryStartRemain}" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" id="queryStartRemain" name="queryStartRemain">&nbsp;<i>-</i>&nbsp;&nbsp;
								<input type="text" id="queryEndRemain" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" name="queryEndRemain" value="${staffDto.queryEndRemain}">
							</div>
						    <div class="sure-cancle clearfix" style="width:120px;margin-top:30px">
								<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" id="remainSubmitId"><label>确定</label></a>
								<a class="com-btnd bomp-btna fl_l" id="close02" href="###"><label>清空</label></a>
							</div>
						</div>
					</div>
				</div>
		</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query" />
	</div>
	<div class="com-btnlist hyx-cm-btnlist fl_l">
			<a data-authority="resStaff_resAssign" href="###" class="com-btna empl-reso-a fl_l" id="resAssignId" ><i class="min-reass"></i><label class="lab-mar">资源分配</label></a>
			<a data-authority="resStaff_resRecycle" href="###" class="com-btna empl-reso-b fl_l" id="resRecyleId" ><i class="min-back"></i><label class="lab-mar">资源回收</label></a>
	</div>

	<div class="hid" style="display: block;">
		<div class="com-table com-mta test-table fl_l">
			<!-- <p class="reso-math">目前共有资源数：<span>3</span>条</p> -->
			<table  id="t1" width="100%" cellspacing="0" cellpadding="0"
				class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
				<thead>
					<tr role="head" class="sty-bgcolor-b">
						<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
						<th><span class="sp sty-borcolor-b">员工姓名</span></th>
						<th><span class="sp sty-borcolor-b">员工帐号</th>
						<th><span class="sp sty-borcolor-b">所在部门</span></th>
						<th><span class="sp sty-borcolor-b">到期时间</span></th>
						<th  hidesort="true"><span class="sp sty-borcolor-b "  sort="true">剩余资源量(已联系/未联系)</span></th>
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
	</div>
</form>
