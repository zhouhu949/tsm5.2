<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
<%@ include file="/common/include.jsp" %>
<title>月度销售目标执行情况</title>
<title>销售统计-月度计划执行结果</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<script type="text/javascript" src="${ctx}/static/js/my97datepicker/rangeLimit.js${_v}"></script>
<style>
.tree-title{width:200px;}
.com-search .select dd{top:30px;}
.com-search .select dt.cur{border:solid #e1e1e1 1px !important;}
.select dd ul{overflow-x:hidden;}
.select dd .easyui-tree{width:260px !important;min-height:40px;}

.hyx-silent-p{margin-bottom:0;}
</style>  

<script type="text/javascript">
$(function(){
    $("#search").click(function(){
    	refreshPage();
    });

    /* $("#cfm").click(function(){
		$("#groupNamesDt").html("-请选择-");
		var str="";
	 	$("input:checkbox:checked").each(function(i){
	 		if(str.length!=0)str+=",";
	 		str+=$(this).attr("groupName");
	 	});
	 	
	 	$("#groupNames").val(str);
	 	$("#groupNamesDt").html(str);
	});
	
	$("#clear").click(function(){
		$("input:checkbox").removeAttr("checked");
		$("#groupNames").val("");
		$("#groupNamesDt").html("-请选择-");
	}); */
	
	$("#cfm").click(function(){
		var nodes = $('#groupTree').tree('getChecked', 'checked');
		var idArray = new Array();
		var nameArray = new Array();
		$.each(nodes,function(index,obj){
			nameArray.push(obj.text);
			idArray.push(obj.id);
		});
		$("#groupIdsStr").val(idArray.toString());
	 	$("#groupNames").val(nameArray.toString());
		if(nameArray.length==0){
			$("#groupNamesDt").html("-请选择-");
		}else{
			$("#groupNamesDt").html(nameArray.toString());
		}
	 	
	});
	
	$("#clear").click(function(){
		var nodes = $('#groupTree').tree('getChecked', 'checked');
		$.each(nodes,function(index,obj){
			 $('#groupTree').tree("uncheck",obj.target);
		});
		$("#groupIdsStr").val('');
		
		$("#groupNames").val("");
		$("#groupNamesDt").html("-请选择-");
	});
	
	 $(".groupData").live("click",function(){
	 		var groupId=$(this).attr("groupId");
	 		$("#iframepage_2").attr("src","${ctx}/report/monthSaleTarget/user?groupId="+groupId+"&dateStr=${dateStr}");
	 });
	 
	 $("#groupTree").tree({
			url:ctx+"/orgGroup/get_group_json",
			checkbox:true,
			onLoadSuccess:function(node,data){
				/* var oas = $("#groupIdsStr").val();
				if(oas != null && oas != ''){
					$.each(oas.split(','),function(index,obj){
						var n = $("#groupTree").tree("find",obj);
						$("#groupTree").tree("check",n.target).tree("expandTo",n.target);
					});
				} */
				 var oas = $("#groupIdsStr").val();
			 var names = new Array();;
			if(oas != null && oas != ''){
				$.each(oas.split(','),function(index,obj){
					var n = $("#groupTree").tree("find",obj);
					names.push(n.text);
					$("#groupTree").tree("check",n.target).tree("expandTo",n.target);
				});
			}
			$("#groupNamesDt").html(names.toString()).attr(names.toString());
			}
		});
});

function refreshPage(){
	$("form")[0].submit();
} 
window.onload=function(){
	var height = $(".sales-statis-conta").height()+200;
	window.parent.$("#iframepage").css({"height":height+"px"});
};
</script>
</head>
<body> 
<form action="${ctx}/report/monthSaleTarget">
<input id="groupNames" name="groupNames" type="hidden" value="${item.groupNames}"/>
<input id="groupIdsStr" name="groupIdsStr" type="hidden" value="${item.groupIdsStr}"/>
<div class="sales-statis-conta clearfix">
	<div class="hyx-silent-p clearfix" style="z-index:555;">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">部门统计</label>
		<div class="com-search-report fl_l" style="margin-top:-4px;min-height:0">
			<label class="fl_l" for="" style="line-height:34px;">选择部门：</label>
			<dl class="select fl_l" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-right:10px;">
				<dt id="groupNamesDt">${item.groupNames} </dt>
				<dd>
					<ul id="groupTree" class="easyui-tree" data-options="animate:false,dnd:false"> </ul>
					<%-- <ul id="tt1" class="easyui-tree" data-options="animate:true,dnd:false" style="max-height:160px;margin-top:10px;">
						<c:forEach items="${groups }" var="group">
							<c:set var="checked" value=""></c:set>
							<c:forEach items="${item.groupIds}" var="id">
								<c:if test="${id== group.groupId}">
									<c:set var="checked" value="checked='checked'"></c:set>
								</c:if>
							</c:forEach>
							<li><span><input class="group" name="groupIds" groupName="${group.groupName }" value="${group.groupId }" type="checkbox" ${checked}>${group.groupName }</span></li>
						</c:forEach>
					</ul> --%>
					<div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;">
						<a id="cfm" class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">确定</label></a>
						<a id="clear" class="com-btnd bomp-btna fl_l" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">清空</label></a>
					</div>
				</dd>
			</dl>
		</div>
		<label class="fl_l" for="">选择年月：</label>
		<span class="fl_l" style="display: inline-block; position: relative;"><!-- 限制日期选择只能选择一个月 -->
			<input id="dateStr" name="dateStr" value="${dateStr }" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M-{%d-1}'})" />
		</span>
		<a id="search" href="javascript:;" class="com-btna fl_l"><i class="min-search"></i><label class="lab-mar">查询</label></a>

	</div>
	<div class="com-table hyx-mpe-table hyx-cm-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">部门名称</span></th> 
					<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
					<th><span class="sp sty-borcolor-b">新增意向数（个）</span></th>
					<th>新增签约数（个）</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
				<c:when test="${!empty list}">
					<c:forEach items="${list }" var="bean" varStatus="i">
					<tr groupId="${bean.groupId }" class="groupData ${i.index%2!=0?'sty-bgcolor-b':''} ${i.index==0?'td-link tr-hover':''}">
						<td><div class="overflow_hidden w120" title="${bean.groupName}">${bean.groupName}</div></td>
						<td><div class="overflow_hidden w100" title="${bean.factMoney}/${bean.planMoney}">${bean.factMoney}/${bean.planMoney}</div></td>
						<td><div class="overflow_hidden w100" title="${bean.factWillcustnum}/${bean.planWillcustnum}">${bean.factWillcustnum}/${bean.planWillcustnum}</div></td>
						<td><div class="overflow_hidden w100" title="${bean.factSigncustnum}/${bean.planSigncustnum}">${bean.factSigncustnum}/${bean.planSigncustnum}</div></td>
					</tr>
					</c:forEach>
						<%-- <td><div class="overflow_hidden w120" title="${total.groupName}">${total.groupName}</div></td>
						<td><div class="overflow_hidden w100" title="${total.factMoney}/${total.planMoney}">${total.factMoney}/${total.planMoney}</div></td>
						<td><div class="overflow_hidden w100" title="${total.factWillcustnum}/${total.planWillcustnum}">${total.factWillcustnum}/${total.planWillcustnum}</div></td>
						<td><div class="overflow_hidden w100" title="${total.factSigncustnum}/${total.planSigncustnum}">${total.factSigncustnum}/${total.planSigncustnum}</div></td> --%>
				</c:when>
				<c:otherwise>
					<tr><td colspan="4" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
				</c:otherwise>
			    </c:choose>            
			</tbody>
		</table>
	</div>
	<c:if test="${!empty list }">
	<iframe src="${ctx}/report/monthSaleTarget/user?groupId=${list[0].groupId}&dateStr=${dateStr}" width="100%" height="100%" id="iframepage_2" frameborder="0"
				scrolling="no" marginheight="0" marginwidth="0" onload="iFrameHeight()"></iframe>
	</c:if>
	<p class="hyx-mpe-ptit"><label class="com-red">温馨提示：</label>“/”前面的值表示指标实际执行值，“/”后面的值表示指标计划制定的值。</p>
</div>
</form>
	<script type="text/javascript" language="javascript">
		function iFrameHeight() {
			var ifm= document.getElementById("iframepage_2");
			var subWeb = document.frames ? document.frames["iframepage_2"].document :ifm.contentDocument;
			if(ifm != null && subWeb != null) {
				ifm.height = subWeb.body.scrollHeight;
			}
		}
	</script>
</body>
</html>