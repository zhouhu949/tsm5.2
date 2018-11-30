<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
<%@ include file="/common/include.jsp" %>
<title>销售统计-团队销售目标完成结果</title>
<!--本页面样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<style>
.tree-title{width:100px;}
.com-search .select dd{top:30px;}
.com-search .select dt.cur{border:solid #e1e1e1 1px !important;}
.select dd ul{overflow-x:hidden;}
.select dd .easyui-tree{width:260px !important;min-height:190px;}

.hyx-silent-p{margin-bottom:0;}
.com-table table thead tr  th{font-weight:normal;font-size:12px;}
</style> 
<script type="text/javascript">
$(function(){
	$("#query").click(function(){
   	 doQuery();
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
		$("#groupNames").val("")
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
	
	$(".groupData").click(function(){
 		var groupId=$(this).attr("groupId");
 		$("#iframepage_2").attr("src","${ctx }/report/teamSale/user?groupId="+groupId+"&planYear=${planYear}&type=${type}");
	});
	
	$("#groupTree").tree({
		url:ctx+"/orgGroup/get_group_json",
		checkbox:true,
		onLoadSuccess:function(node,data){
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

function changeYear(add){
	var timeDiv = $('.hyx-aspa-time');
	var year = parseInt(timeDiv.find('.date').text())+add;
	timeDiv.find('.date').text(year);
	$("#planYear").val(year);
	doQuery();
}

function setGroupId(value,groupName){
	$("#groupId").val(value);
	$("#groupName").val(groupName);
}
function setType(value){
	$("#type").val(value);
}
function doQuery(){
	$("form")[0].submit();
}

/* window.onload=function(){
	var height = $(".sales-target-right").height();
	$(".sales-target-tab").css({"height":height+"px"});
}; */
</script>
</head>
<body style="width:98%">
<form action="${ctx }/report/teamSale">
<input id="planYear" name="planYear" type="hidden" value="${planYear }">
<input id="type" name="type" type="hidden" value="${type }">
<input id="groupNames" name="groupNames" type="hidden" value="${item.groupNames}"/>
<input id="groupIdsStr" name="groupIdsStr" type="hidden" value="${item.groupIdsStr}"/>
<div class="person-static-max">
	<div class="hyx-silent-p clearfix" style="width:98%;margin:0 auto 5px;z-index:555;">
		<div class="com-search-report fl_l" style="margin-top:-4px;min-height:0">
      <label class="fl_l" for="" style="line-height:34px;">选择部门：</label>
      <dl class="select fl_l" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-right:10px;">
			<dt id="groupNamesDt"><%-- ${item.groupNames} --%>-请选择-</dt>
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
    <div class="com-search-report fl_l" style="margin-top:-4px;min-height:0">
      <label class="fl_l" for="" style="line-height:34px;">任务指标：</label>
      <dl class="select" style="z-index: 1;">
        <dt style="border:1px solid #e1e1e1 !important;">${type eq '0'?'新增意向':type eq '1'?'新增签约':'回款金额' }</dt>
        <dd>
          <ul>
          	<li><a title="新增意向" href="javascript:setType(0);">新增意向</a></li>
           	<li><a title="新增签约" href="javascript:setType(1);">新增签约</a></li>
            <li><a title="回款金额" href="javascript:setType(2);">回款金额</a></li>
          </ul>
        </dd>
      </dl>
    </div>
    <a id="query" href="javascript:;" class="com-btna fl_l"><i class="min-search"></i><label class="lab-mar">查询</label></a>
	<!-- <a href="javascript:;" class="com-btna fl_r"><i class="min-remove-resou"></i><label class="lab-mar">导出</label></a> -->
	</div>
  <div class="hyx-aspa-time">
      <label class="center">
        <span class="sp_a fl_l">
        	<label class="left" onclick="javascript:changeYear(-1)">&lt;</label>
          	<label class="date">${planYear}</label>
          	<label class="right" onclick="javascript:changeYear(1)">&gt;</label>
        </span>
        <span class="sp_b fl_l">年${type eq '0'?'新增意向':type eq '1'?'新增签约':'回款金额' }分布图</span>
      </label>
    </div>
  <div class="target-achieve-bottom clearfix">
    <div class="sales-target-right fl_l">
	<div class="tip" style="width:100%;">
		<div class="tip-box" id="main" style="width:100%;height:320px;"></div>
	</div>
  
  <div class="hyx-silent-p clearfix" style="z-index:555;">
    <label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">部门统计</label>
  </div>
  <div class="com-table hyx-mpe-table hyx-cm-table">
    <table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
      <thead>
        <tr class="sty-bgcolor-b">
          <th><span class="sp sty-borcolor-b">部门名称</span></th> 
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
      	<c:when test="${!empty groupPlans}">
	      	<c:forEach items="${groupPlans }" var="groupPlan" varStatus="i">
	      	<c:set var="monthMap" value="${groupPlan.monthMap}" ></c:set>
			<tr groupId="${groupPlan.groupId}" class="groupData ${i.index%2!=0?'sty-bgcolor-b':''} ${i.index==0?'td-link tr-hover':''}">
	          <td><div class="overflow_hidden " title="${groupPlan.groupName}">${groupPlan.groupName}</div></td>
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
		  <%--   <tr class="${maps.length%2!=0?'sty-bgcolor-b':''}">
		    	<td><div class="overflow_hidden w120" title="合计">合计</div></td>
		    	<c:forEach items="${months }" var="month">
		    	<c:choose>
		    	<c:when test="${totalMap[month] !=null }">
		    	<c:if test="${type eq '0' }"><td><div class="overflow_hidden w100" title="${totalMap[month].factWillcustnum}/${totalMap[month].planWillcustnum}">${totalMap[month].factWillcustnum}/${totalMap[month].planWillcustnum}</div></td></c:if>
	          	<c:if test="${type eq '1' }"><td><div class="overflow_hidden w100" title="${totalMap[month].factSigncustnum}/${totalMap[month].planSigncustnum}">${totalMap[month].factSigncustnum}/${totalMap[month].planSigncustnum}</div></td></c:if>
	          	<c:if test="${type eq '2' }"><td><div class="overflow_hidden w100" title="${totalMap[month].factMoney}/${totalMap[month].planMoney}">${totalMap[month].factMoney}/${totalMap[month].planMoney}</div></td></c:if>
	          	</c:when>
	          	<c:otherwise>
	          	<td><div class="overflow_hidden w100" title="-/-">-/-</div></td>
	          	</c:otherwise>
	          	</c:choose>
       	 		</c:forEach>
		    </tr> --%>
		    
      	</c:when>
      	<c:otherwise>
      		<tr><td colspan="13" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
      	</c:otherwise>
      	</c:choose>
      	
      </tbody>
    </table>
  </div>
  <c:if test="${groupPlans[0].groupId!=null}">
  <iframe src="${ctx }/report/teamSale/user?groupId=${groupPlans[0].groupId}&planYear=${planYear}&type=${type}" width="100%" height="100%" id="iframepage_2" frameborder="0"
				scrolling="no" marginheight="0" marginwidth="0" onload="iFrameHeight()"></iframe>
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
  <p class="hyx-mpe-ptit" style="padding-bottom:30px;"><label class="com-red">温馨提示：</label>“/”前面的值表示指标实际执行值，“/”后面的值表示指标计划制定的值。</p>
</div>
	<script type="text/javascript" src="${ctx}/static/js/echarts-2.2.7/asset/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/thirdparty/echarts-3.3.2/echarts.min.js"></script>
<script type="text/javascript" type="text/javascript">
	var totalMap = ${totalMapJson};
	var type =${type};
	var monthArray= [];
	var planArray= [];
	var factArray= [];
	var max=0;
	if(totalMap!=null){
		for(var i=1;i<=12;i++){
			var month = i;
			var plan = totalMap[month];
			
			if(plan!=null){
				monthArray.push(month+"月");
				if(type=="0"){
					planArray.push({value:plan.planWillcustnum,itemStyle:{normal:{label:{show:true,formatter:plan.planWillcustnum.toString()}}}});
					factArray.push({value:plan.factWillcustnum,itemStyle:{normal:{label:{show:true,formatter:plan.factWillcustnum.toString()}}}});
					if(max<plan.planWillcustnum) max = plan.planWillcustnum;
					if(max<plan.factWillcustnum) max = plan.factWillcustnum;
				}else if(type=="1"){
					planArray.push({value:plan.planSigncustnum,itemStyle:{normal:{label:{show:true,formatter:plan.planSigncustnum.toString()}}}});
					factArray.push({value:plan.factSigncustnum,itemStyle:{normal:{label:{show:true,formatter:plan.factSigncustnum.toString()}}}});
					if(max<plan.planSigncustnum) max = plan.planSigncustnum;
					if(max<plan.factSigncustnum) max = plan.factSigncustnum;
				}else if(type=="2"){
					planArray.push({value:plan.planMoney,itemStyle:{normal:{label:{show:true,formatter:plan.planMoney.toString()}}}});
					factArray.push({value:plan.factMoney,itemStyle:{normal:{label:{show:true,formatter:plan.factMoney.toString()}}}});
					if(max<plan.planMoney) max = plan.planMoney;
					if(max<plan.factMoney) max = plan.factMoney;
				}
				
			}
		}
	}
	if(monthArray.length==0||planArray.length==0||factArray.length==0){
		monthArray=[''];
		planArray=[''];
		factArray=[''];
	} 

	var ec=echarts;
            myChart = ec.init(document.getElementById('main'));
            myChart.setOption({
                title : {
                    text: '',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    axisPointer:{type:'none'},
                    showDelay: 0
                },
                legend: {
			        data:['目标','执行'],
			        x:'center',
			        y:'bottom'
			    },
                grid:{x:45,x2:20,y:40,y2:50},
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : true,
                        axisLabel: {
                            interval:0
                        },
                        data : monthArray
                    }
                ],
                yAxis : [
                    {
                        type: 'value',
                        name:'单位(${type eq '0'?'人':type eq '1'?'人':'万' })',
                        splitNumber: 10,
                        min: 0,
                        max: max<10?10:max,
                        axisLabel: {
                            formatter: function (value) {
                              return Math.round(value - 0)
                            }
                        }
                    }
                ],
                series : [
                {
                    name:'目标',
                    type:'line',
                    smooth: true,
                    itemStyle:{normal:{color:'#E87C25'}},
                    data: planArray
                },
                {
                    name:'执行',
                    type:'line',
                    smooth: true,
                    itemStyle:{normal:{color:'#05b441'}},
                    data: factArray
                }
                ]
            });
            window.onresize = myChart.resize;

</script>
</div>
</div>
</form>
</body>
</html>