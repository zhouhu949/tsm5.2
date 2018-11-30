<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售统计-小组历史数据-销售结果统计-资源</title>
<!--本页面样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<script type="text/javascript" src="${ctx}/static/js/my97datepicker/rangeLimit.js${_v}"></script>
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

.hyx-silent-p{margin-bottom:0;}
.color_grey{color:grey !important;}
</style> 
<script type="text/javascript">
$(function(){


    /*标签页选择*/
    $(".bomp_ic_tip").find("li").click(function(){
    	if($(this).hasClass("li_click") == false){
    		$(this).addClass("li_click");
    		$(this).siblings("li").removeClass("li_click");
    		var _url = $(this).attr("_url");
    		window.parent.changeUrl(_url);
    	};
    });
    
    $("#search").click(function(){
    	refreshPage();
    });
    
	$("#toWeek").click(function(){
		$("#timeType").val(0);
		$("#data_01").val("<fmt:formatDate value='${toWeekArray[0]}' pattern='yyyy-MM-dd'/>");
		$("#data_02").val("<fmt:formatDate value='${toWeekArray[1]}' pattern='yyyy-MM-dd'/>");
		refreshPage();
    });
	    
	$("#toMonth").click(function(){
		$("#timeType").val(1);
		$("#data_01").val("<fmt:formatDate value='${toMonthArray[0]}' pattern='yyyy-MM-dd'/>");
		$("#data_02").val("<fmt:formatDate value='${toMonthArray[1]}' pattern='yyyy-MM-dd'/>");
		refreshPage();
	});
	
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
	 		$("#iframepage_2").attr("src","${ctx}/report/teamHistoryData/saleResultUser?pageType=res&groupId="+groupId+"&fromDateStr=${fromDateStr}&toDateStr=${toDateStr}");
	 });
	 
	 $("#groupTree").tree({
			url:ctx+"/orgGroup/get_group_json",
			checkbox:true,
			onLoadSuccess:function(node,data){
				var oas = $("#groupIdsStr").val();
				if(oas != null && oas != ''){
					$.each(oas.split(','),function(index,obj){
						var n = $("#groupTree").tree("find",obj);
						$("#groupTree").tree("check",n.target).tree("expandTo",n.target);
					});
				}
			}
		});
	 
	 $(".view_change").click(function(e){
		 e.stopPropagation();
			var startDate = $("#data_01").val();
		 	var endDate = $("#data_02").val();
		 	var groupId = $(this).attr("groupId");
			var custType = $(this).attr("cust_type");
			var changeType = $(this).attr("change_type");
			var name = "";
			if(custType == 1){
				if(changeType == 1)
					name="资源转意向详情";
				else
					name="资源转签约详情"
			}else{
				if(changeType == 1)
					name="意向变更详情";
				else
					name="意向转签约详情"
			}
			pubDivShowIframe('change_log_detail',ctx+'/report/contact?groupIdStr='+groupId+"&startDate="+startDate+"&endDate="+endDate+"&custType="+custType+"&changeType="+changeType,name,800,450);
		});
});

function refreshPage(){
	$("form")[0].submit();
}

window.onload=function(){
	var win_height = window.parent.$("body").height() - 100;
	var height = $(".sales-statis-conta").height()+80;
	if(height < win_height){
		window.parent.$("#iframepage").css({"height":win_height+"px"});
	}else{
		window.parent.$("#iframepage").css({"height":height+"px"});
	}
};
</script>
</head>
<body> 
<form action="${ctx }/report/teamHistoryData/saleResult">
<input name="pageType" type="hidden" value="res"/>
<input id="groupNames" name="groupNames" type="hidden" value="${item.groupNames}"/>
<input id="timeType" name="timeType" type="hidden" value="${timeType }"/>
<input id="groupIdsStr" name="groupIdsStr" type="hidden" value="${item.groupIdsStr}"/>
<input id="iframepage_height" name="iframepage_height" type="hidden" value=""/>

<div class="sales-statis-conta">
	<div class="dataData_group">
	<div class="bomp_ic_tip">
		<ul class="list">
			<li class="li_click" _url="${ctx}/report/teamHistoryData/saleResult?pageTYpe=res" >资源联系结果</li>
			<li _url="${ctx}/report/teamHistoryData/saleResult?pageType=will">意向联系结果</li>
		</ul>
	</div>

	<div class="hyx-silent-p clearfix" style="z-index:555;">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">部门统计</label>
		<div class="com-search-report fl_l" style="margin-top:-4px;min-height:0">
			<label class="fl_l" for="" style="line-height:34px;">选择部门：</label>
			<dl class="select fl_l" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-right:10px;">
				<dt id="groupNamesDt">${item.groupNames}</dt>
				<dd>
					<ul id="groupTree" class="easyui-tree" data-options="animate:false,dnd:false"> </ul>
					<div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;">
						<a id="cfm" class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">确定</label></a>
						<a id="clear" class="com-btnd bomp-btna fl_l" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">清空</label></a>
					</div>
				</dd>
			</dl>
		</div>
		<label class="fl_l" for="">选择日期：</label>
		<span class="fl_l" style="display: inline-block; position: relative;"><!-- 限制日期选择只能选择一个月 -->
		<input id="data_01" name="fromDateStr" value="${fromDateStr }" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({onpicked:pickedFunc1,maxDate:'#F{$dp.$D(\'data_02\')}'})" />
		<span class="silent-custo-span fl_l">&nbsp;—&nbsp;</span>
		<input id="data_02" name="toDateStr" value="${toDateStr }" type="text" class="selec-year-inpu com-form-data fl_l" onclick="WdatePicker({onpicked:pickedFunc2,minDate:'#F{$dp.$D(\'data_01\');}',maxDate:'%y-%M-{%d-1}'})" />
		</span>
		<a id="toWeek" href="javascript:;" class="silent-week fl_l ${timeType eq '0'?'color_grey':''}">本周</a>
		<a id="toMonth" href="javascript:;" class="silent-month fl_l ${timeType eq '1'?'color_grey':''}">本月</a>
		<a id="search" href="javascript:;" class="com-btna fl_l"><i class="min-search"></i><label class="lab-mar">查询</label></a>
		<!-- <a href="#" class="com-btna fl_r"><i class="min-remove-resou"></i><label class="lab-mar">导出</label></a> -->
		<!-- <div class="static-time fl_r" style="width: 280px; margin-right: 0px;"><label for="">统计时间：</label>2016年03月04日至2016年03月11日</div> -->
	</div>
	<div class="com-table hyx-mpe-table hyx-cm-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">部门名称</span></th> 
					<th><span class="sp sty-borcolor-b">计划联系数</span></th>
					<th><span class="sp sty-borcolor-b">实际联系数</span></th>
					<th><span class="sp sty-borcolor-b">添加资源数</span></th>
					<th><span class="sp sty-borcolor-b">转签约</span></th>
					<th><span class="sp sty-borcolor-b">转意向</span></th>
					<th><span class="sp sty-borcolor-b">转公海</span></th>
					<th><span class="sp sty-borcolor-b">未联系</span></th>
					<th>联系无变化</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
				<c:when test="${!empty list}">
					<c:forEach items="${list }" var="bean" varStatus="i">
					<tr groupId="${bean.groupId }" class="groupData ${i.index%2!=0?'sty-bgcolor-b':''} ${i.index==0?'td-link tr-hover':''}">
						<td><div class="overflow_hidden w120" title="${bean.groupName }">${bean.groupName }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.resPlanNum }">${bean.resPlanNum }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.resTotalNum }">${bean.resTotalNum }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.resAddNum }">${bean.resAddNum }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.resSignNum }">
						 <c:choose>
		                	<c:when test="${bean.resSignNum > 0 }">
		                		<a class="view_change" href="javascript:;" groupId="${bean.groupId }" cust_type="1" change_type="2">${bean.resSignNum }</a>
		                	</c:when>
		                	<c:otherwise>${bean.resSignNum }</c:otherwise>
		                </c:choose>
		                </div></td>
		                
		                <td><div class="overflow_hidden w70" title="${bean.resCustNum }">
						 <c:choose>
		                	<c:when test="${bean.resCustNum > 0 }">
		                		<a class="view_change" href="javascript:;" groupId="${bean.groupId }"  cust_type="1" change_type="1">${bean.resCustNum }</a>
		                	</c:when>
		                	<c:otherwise>${bean.resCustNum }</c:otherwise>
		                </c:choose>
		                </div></td>
						<td><div class="overflow_hidden w70" title="${bean.resPoolNum }">${bean.resPoolNum }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.resNoContact }">${bean.resNoContact }</div></td>
						<td><div class="overflow_hidden w70" title="${bean.resNoNum }">${bean.resNoNum }</div></td>
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
	<c:if test="${!empty list }">
	<iframe src="${ctx}/report/teamHistoryData/saleResultUser?pageType=res&groupId=${list[0].groupId}&fromDateStr=${fromDateStr}&toDateStr=${toDateStr}" width="100%" height="100%" id="iframepage_2" frameborder="0"
				scrolling="no" marginheight="0" marginwidth="0" onload="iFrameHeight()"></iframe>
	<!-- <div id="res_user"></div> -->
	<script type="text/javascript" language="javascript">
	function iFrameHeight() {
		var ifm= document.getElementById("iframepage_2");
		var subWeb = document.frames ? document.frames["iframepage_2"].document :ifm.contentDocument;
		if(ifm != null && subWeb != null) {
			ifm.height = subWeb.body.scrollHeight;
		}
	}
	</script>
	</c:if>
</div>
</form>
</body>
</html>
