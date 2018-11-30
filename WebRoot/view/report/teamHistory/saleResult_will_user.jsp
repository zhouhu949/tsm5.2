<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售统计-小组历史数据-资源联系明细</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<style>
/* .com-table table thead tr  th{font-weight:normal;font-size:12px;} */
.tree-title{width:200px;}
.com-search .select dd{top:30px;}
.com-search .select dt.cur{border:solid #e1e1e1 1px !important;}
.select dd ul{overflow-x:hidden;}
.select dd .easyui-tree{width:260px !important;min-height:40px;}

.bomp_ic_tip{position:relative;width:100%;height:30px;border-bottom:solid #979797 1px;margin-top:15px;}
.bomp_ic_tip .list{position:absolute;left:7px;bottom:-1px;width:auto;height:30px;}
.bomp_ic_tip .list li{float:left;width:100px;height:28px;line-height:28px;text-align:center;font-size:14px;color:#636363;border:solid #979797 1px;background-color:#e2e2e2;cursor:pointer;}
.bomp_ic_tip .list .li_click{color:#000;background-color:#fff;border-bottom:solid #fff 1px;}
</style> 

<script type="text/javascript">
$(function(){
	$(".detail").live("click",function(){
		var userId=$(this).attr("userId");
		var userName=$(this).attr("userName");
		window.parent.pubDivShowIframe("detail","${ctx }/report/teamHistoryData/saleResultDetail?pageType=will&userId="+userId+"&fromDateStr=${fromDateStr}&toDateStr=${toDateStr}",userName+"销售结果明细",800,400);
	 });
	
	$("#cfm").click(function(){
		$("#userNamesDt").html("-请选择-");
		var str="";
	 	$("input:checkbox:checked").each(function(i){
	 		if(str.length!=0)str+=",";
	 		str+=$(this).attr("userName");
	 	});
	 	
	 	$("#userNames").val(str);
	 	$("#userNamesDt").html(str);
	 	refwillhPage();
	});
	
	$("#clear").click(function(){
		$("input:checkbox").removeAttr("checked");
		$("#userNames").val("");
		$("#userNamesDt").html("-请选择-");
	});
	
	$(".view_change").click(function(){
		var startDate = $("#fromDateStr").val();
	 	var endDate = $("#toDateStr").val();
		var date = $(this).attr("date");
		var custType = $(this).attr("cust_type");
		var changeType = $(this).attr("change_type");
		var ownerAcc = $(this).attr("account");
		var name = "";
		if(custType == 1){
			if(changeType == 1)
				name="资源转意向详情";
			else
				name="资源转签约详情"
		}else{
			if(changeType == 1)
				name="意向客户意向变更详情";
			else
				name="意向客户转签约详情"
		}
		window.top.pubDivShowIframe('change_log_detail',ctx+'/report/contact?ownerAcc='+ownerAcc+"&startDate="+startDate+"&endDate="+endDate+"&custType="+custType+"&changeType="+changeType,name,800,450);
	});
	
	//适应下拉框高度
	var dd_height = $("#tt2").parent().height();
	$(".adapt_dd_height").css("min-height",dd_height);
	
	var sale_res_user_height = $(".sale_res_user").height();
	window.parent.$("#iframepage_2").css({"height":sale_res_user_height+"px"});
	var height = window.parent.$(".dataData_group").height()+window.parent.$("#iframepage_2").height()+80;
	window.parent.parent.$("#iframepage").css({"height":height+"px"});
	
});

function refwillhPage(){
	$("form")[0].submit();
}
</script>

</head>
<body style="min-height:300px;"> 
<form action="${ctx }/report/teamHistoryData/saleResultUser">
<input name="pageType" type="hidden" value="will"/>
<input id="userNames" name="userNames" type="hidden" value="${item.userNames}"/>
<input id="groupId" name="groupId" type="hidden" value="${item.groupId}"/>
<input id="fromDateStr" name="fromDateStr" type="hidden" value="${fromDateStr}"/>
<input id="toDateStr" name="toDateStr" type="hidden" value="${toDateStr}"/>

<div class="sale_res_user">
<div class="hyx-silent-p clearfix">
	<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">个人统计</label>
	<div class="com-search fl_l" style="margin-top:-4px;min-height:0">
		<label class="fl_l" for="" style="line-height:34px;">选择人员：</label>
		<dl class="select fl_l" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-right:10px;">
			<dt id="userNamesDt">${item.userNames}</dt>
			<dd>
				<ul id="tt2" class="easyui-tree" data-options="animate:true,dnd:false" style="max-height:160px;margin-top:10px;">
					<c:forEach items="${members }" var="member">
						<c:set var="checked" value=""></c:set>
						<c:forEach items="${item.userIds}" var="id">
							<c:if test="${id== member.userId}">
								<c:set var="checked" value="checked='checked'"></c:set>
							</c:if>
						</c:forEach>
						<li><span><input name="userIds" userName="${member.memberName }" value="${member.userId }" type="checkbox" ${checked}>${member.memberName }</span></li>
					</c:forEach>
				</ul>
				<div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;">
					<a id="cfm" class="com-btnc bomp-btna com-btna-sty willo-owner-sure fl_l" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">确定</label></a>
					<a id="clear" class="com-btnd bomp-btna fl_l" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">清空</label></a>
				</div>
			</dd>
		</dl>
	</div>
</div>

<div class="com-table hyx-mpe-table hyx-cm-table adapt_dd_height">
	<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
		<thead>
			<tr class="sty-bgcolor-b">
				<th><span class="sp sty-borcolor-b">操作</span></th> 
				<th><span class="sp sty-borcolor-b">销售姓名</span></th>
				<th><span class="sp sty-borcolor-b">销售帐号</span></th>
				<th><span class="sp sty-borcolor-b">计划联系数</span></th>
				<th><span class="sp sty-borcolor-b">实际联系数</span></th>
				<th><span class="sp sty-borcolor-b">转签约</span></th>
				<th><span class="sp sty-borcolor-b">意向变更</span></th>
				<th><span class="sp sty-borcolor-b">转公海</span></th>
				<th><span class="sp sty-borcolor-b">未联系</span></th>
				<th>联系无变化</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
			<c:when test="${!empty list}">
				<c:forEach items="${list }" var="bean" varStatus="i">
				<tr class="${i.index%2!=0?'sty-bgcolor-b':''}">
					<td><div class="overflow_hidden w30 link"><a userName="${bean.userName }" userId="${bean.userId }" href="javascript:;" class="icon-detail detail" title="明细"></a></div></td>
					<td><div class="overflow_hidden w120" title="${bean.userName }">${bean.userName }</div></td>
					<td><div class="overflow_hidden w120" title="${bean.account }">${bean.account }</div></td>
					<td><div class="overflow_hidden w70" title="${bean.willPlanNum }">${bean.willPlanNum }</div></td>
					<td><div class="overflow_hidden w70" title="${bean.willTotalNum }">${bean.willTotalNum }</div></td>
					
					<td><div class="overflow_hidden w70" title="${bean.willSignNum }">
					<c:choose>
			                	<c:when test="${bean.willSignNum > 0 }">
			                		<a class="view_change" href="javascript:;" account="${bean.account }" cust_type="2" change_type="2">${bean.willSignNum }</a>
			                	</c:when>
			                	<c:otherwise>${bean.willSignNum }</c:otherwise>
			                </c:choose>
			                </div></td>
	                
			                <td><div class="overflow_hidden w70" title="${bean.willCustNum }">
							<c:choose>
			                	<c:when test="${bean.willCustNum > 0 }">
			                		<a class="view_change" href="javascript:;" account="${bean.account }" cust_type="2" change_type="1">${bean.willCustNum }</a>
			                	</c:when>
			                	<c:otherwise>${bean.willCustNum }</c:otherwise>
			                </c:choose>
			                </div></td>
		                
					<td><div class="overflow_hidden w70" title="${bean.willPoolNum }">${bean.willPoolNum }</div></td>
					<td><div class="overflow_hidden w70" title="${bean.willNoContact }">${bean.willNoContact }</div></td>
					<td><div class="overflow_hidden w70" title="${bean.willNoNum }">${bean.willNoNum }</div></td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr class="no_data_tr"><td><b>当前列表无数据！</b></td></tr>
			</c:otherwise>
		    </c:choose>
		</tbody>
	</table>
</div>
</div>
</form>
</body>
</html>