<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
<%@ include file="/common/include.jsp" %>
<title>销售统计-团队销售目标完成结果</title>
<!--本页面样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<style>
.tree-title{width:200px;}
.com-search .select dd{top:30px;}
.com-search .select dt.cur{border:solid #e1e1e1 1px !important;}
.select dd ul{overflow-x:hidden;}
.select dd .easyui-tree{width:260px !important;min-height:40px;}

.hyx-silent-p{margin-bottom:0;}
</style> 
<style>
	.com-table table thead tr  th{font-weight:normal;font-size:12px;}
</style>
<script type="text/javascript">
$(function(){
	$("#query").click(function(){
   	 doQuery();
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
	 	doQuery();
	});
	
	$("#clear").click(function(){
		$("input:checkbox").removeAttr("checked");
		$("#userNames").val("");
		$("#userNamesDt").html("-请选择-");
	});
	//适应下拉框高度
	var dd_height = $("#tt2").parent().height();
	$(".adapt_dd_height").css("min-height",dd_height);
});
 
function doQuery(){
	$("form")[0].submit();
}

window.onload=function(){
	var height = $(".sales-target-right").height();
	$(".sales-target-tab").css({"height":height+"px"});
};
</script>
</head>
<body>
<form action="${ctx }/report/teamSale/user">
<input id="planYear" name="planYear" type="hidden" value="${planYear }">
<input id="type" name="type" type="hidden" value="${type }">
<input id="groupId" name="groupId" type="hidden" value="${item.groupId }">
<input id="userNames" name="userNames" type="hidden" value="${item.userNames}"/>
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
				<a id="cfm" class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">确定</label></a>
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
          <th><span class="sp sty-borcolor-b">销售姓名</span></th>
          <th><span class="sp sty-borcolor-b">销售帐号</span></th>  
          <th><span class="sp sty-borcolor-b">1月</span></th>
          <th><span class="sp sty-borcolor-b">2月</span></th>
          <th><span class="sp sty-borcolor-b">3月</span></th>
          <th><span class="sp sty-borcolor-b">4月</span></th>
          <th><span class="sp sty-borcolor-b">5月</span></th>
          <th><span class="sp sty-borcolor-b">6月</span></th>
          <th><span class="sp sty-borcolor-b">7月</span></th>
          <th><span class="sp sty-borcolor-b">8月</span></th>
          <th><span class="sp sty-borcolor-b">9月</span></th>
          <th><span class="sp sty-borcolor-b">10月</span></th>
          <th><span class="sp sty-borcolor-b">11月</span></th>
          <th>12月</th>
        </tr>
      </thead>
      <tbody>              
        <c:choose>
      	<c:when test="${!empty userPlans}">
	      	<c:forEach items="${userPlans }" var="userPlan" varStatus="i">
	      	<c:set var="monthMap" value="${userPlan.monthMap}" ></c:set>
			<tr class="${i.index%2!=0?'sty-bgcolor-b':''}">
	          <td><div class="overflow_hidden " title="${userPlan.userName}">${userPlan.userName}</div></td>
	          <td><div class="overflow_hidden " title="${userPlan.account}">${userPlan.account}</div></td>
	          <c:forEach items="${months }" var="month">
	          	<c:choose>
	          	<c:when test="${monthMap[month] !=null }">
	          	<c:if test="${type eq '0' }"><td><div class="overflow_hidden " title="${monthMap[month].factWillcustnum}/${monthMap[month].planWillcustnum}">${monthMap[month].factWillcustnum}/${monthMap[month].planWillcustnum}</div></td></c:if>
	          	<c:if test="${type eq '1' }"><td><div class="overflow_hidden " title="${monthMap[month].factSigncustnum}/${monthMap[month].planSigncustnum}">${monthMap[month].factSigncustnum}/${monthMap[month].planSigncustnum}</div></td></c:if>
	          	<c:if test="${type eq '2' }"><td><div class="overflow_hidden " title="${monthMap[month].factMoney}/${monthMap[month].planMoney}">${monthMap[month].factMoney}/${monthMap[month].planMoney}</div></td></c:if>
	          	</c:when>
	          	<c:otherwise>
	          	<td><div class="overflow_hidden " title="-/-">-/-</div></td>
	          	</c:otherwise>
	          	</c:choose>
	          </c:forEach>
	        </tr>      	
		    </c:forEach>
      	</c:when>
      	<c:otherwise>
      		<tr><td colspan="14" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
      	</c:otherwise>
      	</c:choose>
      </tbody>
    </table>
  </div>
</form>
</body>
</html>