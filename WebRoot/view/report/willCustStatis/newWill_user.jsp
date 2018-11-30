<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<head>
<%@ include file="/common/include.jsp"%>
<title>新增意向客户统计-历史统计-按组分析</title>
<!--本页面样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<style>
	.com-table table thead tr  th{font-weight:normal;font-size:12px;}
</style>
<script type="text/javascript">
$(function(){

$(".detail").live("click",function(){
		var $this = $(this);
		var useracc=$this.attr("useracc");
		var username=$this.attr("username")
		window.top.pubDivShowIframe("detail","${ctx }/newWill/findUserdataBygroup?account="+useracc+"&fromDateStr=${fromDateStr}&toDateStr=${toDateStr}",username+"销售结果明细",800,400);
	 });
/*
 *height:获取当前内容高度（也可以理解为这一层的iframe高度）
 *将父层的dataData_group_height对象的高度取过来
 *将保存起来的iframepage_height值拿过来
 *将父层的id为iframepage_2，$("#iframepage_2")该对象，让其高度为当前内容高度，防止滚动条出现
 *将父父层的id为iframepage_1，$("#iframepage_1")该对象，让其高度为当前内容高度+dataData_group_height，防止滚动条出现
 *将父父父层的id为iframepage，$("#iframepage")该对象，让其高度为iframepage_1的新高度加iframepage_height，防止滚动条出现
 */
	var height = $(".sales-statis-conta").height()+30;
	var dataData_group_height = window.parent.$(".dataData_group").height();
	var iframepage_height = parseInt(window.parent.parent.$("#iframepage_height").val());
/* 	var page_clientHeight =  window.parent.parent.parent.parent.document.body.clientHeight; */
	window.parent.$("#iframepage_2").css({"height":height+"px"});
	window.parent.parent.$("#iframepage_1").css({"height":(height+dataData_group_height)+"px"});
	window.parent.parent.parent.$("#iframepage").css("height",(height+dataData_group_height+iframepage_height)+"px");
/* 	alert(	"window.parent"+"------"+window.parent.$("#iframepage_2").height());
	alert("window.parent.parent"+"------"+window.parent.parent.$("#iframepage_1").height()); */
	
	$("span[sort=true]").parent().click(function(){
		var orderColumn = $(this).find('span[sort=true]').attr("column");
		var orderType = '';
		if($(this).find('span[sort=true]').hasClass("td_sort_desc")){
			orderType = 'ASC';
		}else{
			orderType = 'DESC';
		}
		$("#column").val(orderColumn);
		$("#orderType").val(orderType);
		$("#form").submit();
	});
	
	$("#doExport").click(function(){
		var action = $("form")[0].action;
		$("form")[0].action="${ctx }/newWill/exportbyUser";
		$("form")[0].submit();
		$("form")[0].action=action;
	});
		$("body").delegate(".report-click-detail","click",function(e){
		e.stopPropagation();
		var $this = $(this);
		var selectType = $this.data("selecttype");
		var userAccount = $this.data("useracc");
		var optionlistId = $this.data("optionid");
		var startDate =$this.data("startdate");
		var endDate = $this.data("enddate");
		var optionName =$this.data("optionname"); 
		if(optionlistId != "" && optionlistId != null){
			var name = "历史新增"+optionName+"进程明细";
			window.top.pubDivShowIframe('change_log_detail',ctx+"/newWill/newWillHistoryIdialogNewWillStatus?startDate="+startDate+"&endDate="+endDate+"&selectType="+selectType+"&userAccount="+userAccount+"&optionlistId="+optionlistId,name,900,450);
		}else{
			var name = "新增意向客户明细";
			window.top.pubDivShowIframe('change_log_detail',ctx+"/newWill/newWillHistoryId?startDate="+startDate+"&endDate="+endDate+"&selectType="+selectType+"&userAccount="+userAccount,name,900,450);
		}
	})
});
</script>
</head>
<body>
<form id="form" action="${ctx}/newWill/exportbyUser" method="post">
<input type="hidden" name="fromDateStr" id="fromDateStr" value="${fromDateStr }" />
<input type="hidden" name="toDateStr" id="toDateStr" value="${toDateStr }" />
<input type="hidden" name="timeType" id="timeType" value="${timeType }" />
<input type="hidden" name="groupId" id="groupId" value="${groupId }" />
<input type="hidden" name="column" id="column" value="${column }" />
<input type="hidden" name="orderType" id="orderType" value="${orderType}" />

</form>
<div class="sales-statis-conta clearfix">
	<p class="hyx-silent-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">个人统计</label>
		<a class="com-btna fl_l" id="doExport" href="javascript:;"><i class="min-remove-resou"></i><label class="lab-mar">导出</label></a>
	</p>
	<div class="com-table hyx-mpe-table hyx-cm-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">操作</span></th> 
					<th><span class="sp sty-borcolor-b">销售姓名</span></th> 
					<th><span class="sp sty-borcolor-b">新增意向数</span></th> 
					<c:if test="${!empty item}">
		                <c:set var="itemlength" value="${fn:length(item)}"></c:set>
		                <c:forEach var="sopn" items="${item }" varStatus="v" >
							 <c:choose>
							 <c:when test="${ v.count == itemlength }">
							  <th>${sopn}</th>
							 </c:when>
							 <c:otherwise>
							  <th><span class="sp sty-borcolor-b">${sopn}</span></th>
							 </c:otherwise>
							 </c:choose>
						</c:forEach>
					</c:if>
				</tr>
			</thead>
			<tbody>              
				<c:choose>
				<c:when test="${!empty list}">
					<c:forEach items="${list }" var="bean" varStatus="i">
					<tr class="${i.index%2!=0?'sty-bgcolor-b':''}">
						<td style="width:100px;"><a useracc="${bean.userAccount }"  username="${bean.userName }"  href="javascript:;" class="icon-detail detail" title="明细"></a></td>
						<td><div class="overflow_hidden w70" title="${bean.userName }">${bean.userName }</div></td>
						<c:if test="${!empty bean.returnlist}">
							<c:forEach var="beanitem" items="${bean.returnlist}" varStatus="b">
								   <td><div class="overflow_hidden w70"  >
									<c:choose>
									<c:when test="${beanitem.num gt 0  }">
									<a href="javascript:;"  class="report-click-detail"  title="${beanitem.num }" data-startdate="${fromDateStr }" data-enddate="${toDateStr }" data-selecttype="3"  data-useracc="${bean.userAccount }" data-optionid="${beanitem.optionId }" data-optionname="${beanitem.optionName }">${beanitem.num }</a>
									</c:when>
									<c:otherwise>
										${beanitem.num }
									</c:otherwise>
									</c:choose>
								 </div></td>
							</c:forEach>
						</c:if>
					</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="no_data_tr"><td style="text-align: center;"><b>当前列表无数据！</b></td></tr>
				</c:otherwise>
			    </c:choose> 
			</tbody>
		</table>
	</div>
</div>
</body>
</html>