<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-月度计划（团队编辑）-月度计划编辑（个人）</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<script type="text/javascript">
var isState = ${isState};
var planId="${dto.planMonth.id}";
var planYear="${dto.planMonth.planYear}";
var planMonth="${dto.planMonth.planMonth}";
var isNew="${isNew}";
function addCust(custIds){
	closePubDivDialogIframe("addCustWindow");
	$.post(ctx+"/plan/month/user/cust/findFromRes",{"custIdsStr":custIds.toString()},function(custs){
		tbody = $("#custTbody");
		for(var i=0;i<custs.length;i++){
			var cust = custs[i];
			if(cust.custName ==null) cust.custName ='';
			if(cust.custStatus ==null) cust.custStatus ='';
			if(cust.company ==null) cust.company ='';
			
			length = tbody.find("tr").not(".total,.empty").length;
			_clazz='';
			if(length%2!=0) _clazz='sty-bgcolor-b';
			
			var html='<tr custid="'+cust.custId+'" class="'+_clazz+' custtr">'+
			'	<td style="width:30px;"><div class="overflow_hidden w30 link">'+
			'	<a href="javascript:;" class="icon-dele deleteCustBtn" title="删除"></a>'+
			'	</div></td>'+
			'	<td><div name="custName" class="overflow_hidden w100" title="'+cust.custName +'">'+cust.custName +'</div></td>'+
			(isState==0?
			'	<td><div name="company" class="overflow_hidden w180" title="'+cust.company +'">'+cust.company +'</div></td>'
			:'')+
			'	<td><div name="custStatus" class="overflow_hidden w120" title="'+cust.custStatus +'">'+cust.custStatus +'</div></td>'+
			'	<td><div name="singcustNum" class="overflow_hidden w70" value="'+cust.singcustNum+'" title="'+(cust.singcustNum==0?'签约客户':'意向客户') +'">'+(cust.singcustNum==0?'签约客户':'意向客户') +'</div></td>'+
			'	<td><div class="hyx-tip-drop w70"><input name="planMoney"  type="text"  value="0.0" class="mpse-ipt w70 doubleNumber" /></div></td>'+
			'	<td><div class="hyx-tip-drop w360"><input name="context" type="text"  value="" class="mpse-ipt w360" /></div></td>'+
			
			//'	<td><div class="overflow_hidden w120" title="'+cust.planMoney +'"><input name="planMoney"  type="text" planid="'+cust.id+'" dbvalue="'+cust.planMoney +'" value="'+cust.planMoney +'" class="mpse-ipt w110 doubleNumber" /></div></td>'+
			//'	<td><div class="overflow_hidden w290" title="'+cust.context +'"><input name="context" type="text" planid="'+cust.id+'" dbvalue="'+cust.context +'" value="'+cust.context +'" class="mpse-ipt w280" /></div></td>'+
			
			'</tr>';
			$(".total").remove();
			$(".empty").remove();
			$("#custTbody").append(html);
		}
		addTotal();
	});
}

function check(_this){
	var _clazz=$(_this).attr("class");
	var reg1=/^\d{1,5}$/;
	var reg2=/^\d{1,5}(\.\d{1,2})?$/;
	var flag=false;
	var msg='';
	var value=$(_this).val();
	
	if(_clazz.indexOf("doubleNumber")!=-1){
		//double
		if(reg2.test(value)) {
			flag=true;
			setValue();
		}else{
			msg='请输入5位小数，小数点后2位！';
		}
	}else if(_clazz.indexOf("intNumber")!=-1){
		//int
		if(reg1.test(value)) {
			flag=true;
			setValue();
		}else{
			msg='请输入5位整数！';
		}
	}else{
		if(value.replace(/(^\s+)|(\s+)/,'').length<=50) {
			flag=true;
		}else{
			msg='最大长度为50！';
		}
		//text
	}
	
	if(!flag){
		if($(_this).siblings("span").length==0){
			var html='<span class="drop">'+
			'	<label class="arrow"><em>◆</em><span>◆</span></label>'+
			'	<label class="box">'+msg+'</label>'+
			'</span>';
			$(_this).parent().append(html);
		}
	}else{
		$(_this).siblings("span").remove();
	}
	
	return flag;
};

function setValue(){
	var signNum = 0;
	var planMoney = 0;
	var tbody = $("#custTbody");
	tbody.find("tr").not(".total,.empty").each(function (index, domEle) { 
	  	// domEle == this 
	  	var num=$(this).find("[name='singcustNum']").text();
	  	if(num=="意向客户"){
	  		num=1;
		}else if(num=="签约客户"){
			num=0;
		}else{
			alert("客户状态不识别，请联系管理员！:"+num);
		}
	  	num = isNaN(num)?0:num;
	  	var money=parseFloat($(this).find("input").eq(0).val());
	  	money=isNaN(money)?0:money;
	  	
	  	signNum+=num;	
	  	planMoney =( planMoney*100 +money*100 )/100;
	});
	
	$(".total").find("td").eq(1).text(planMoney);
	
	var willWarp=parseInt($("#planWillcustnumAdd").val());
	var signWarp=parseInt($("#planSigncustnumAdd").val());
	var signMoney=parseFloat($("#planWillcustnumMoney").val());
	
	willWarp=isNaN(willWarp)?0:willWarp;
	signWarp=isNaN(signWarp)?0:signWarp;
	signMoney=isNaN(signMoney)?0:signMoney;
	$("#totalWillNum").text(willWarp);
	$("#totalWillNum").attr("title",willWarp);
	
	$("#totalSignNum").text(signWarp+signNum);
	$("#totalSignNum").attr("title",signWarp+signNum);
	
	$("#totalSignMoney").text((planMoney*100+signMoney*100)/100);
	$("#totalSignMoney").attr("title",(planMoney*100+signMoney*100)/100);
}

function addTotal(){
	var emptyHtml='<tr class="empty"><td colspan="${isState eq 0?7:6}" style="text-align: center;"><b>当前列表无数据！</b></td></tr>';
	var tobody = $("#custTbody");
	if(tobody.find("tr").not(".total").length==0) {
		tobody.find(".total").remove();
		tobody.append(emptyHtml);
	}
	
	var totalHtml='<tr class="sty-bgcolor-b total">'+
	'	<td colspan="${isState eq 0?5:4}"><span class="sp sty-borcolor-b">合计</span></td>'+
	'	<td><div class="overflow_hidden w70" title=""></div></td>'+
	'	<td><div class="overflow_hidden w290"></div></td>'+
	'</tr>';
	
	if(tobody.find(".total").length==0) tobody.append(totalHtml);
	setValue();
}

function del(_this){
	pubDivDialog("delCustDialog","你确定要删除该客户吗？",function(){
		$(_this).parents("tr").remove();
		addTotal();
	});
}

function checkForm(okFn,value){
	var flag =true;
	$("tr input").each(function(index,item){
		if(!check(item)) flag=false;
	});
	
	if(flag) okFn(value);
}

$(function(){

	$('.demoBtn_b').click(function(){
		var tbody = $("#custTbody");
		var custIds=  new Array();
		tbody.find("tr").not(".total,.empty").each(function (index, domEle) { 
			custIds.push($(this).attr("custid"));
		});
		pubDivShowIframe('addCustWindow','${ctx}/plan/month/user/addCustWindow${_v}&custIdsStr='+custIds.toString(),'添加客户',750,550);
	});
	
	$('.demoBtn_report').click(function(){
		checkForm(save,1);
		//save(1);
	});
	
	$('.demoBtn_save').click(function(){
		checkForm(save,0);
		//save(0);
	});
	
	$('.demoBtn_cancel').click(function(){
		bakTab();
		window.top.closeTab('个人月计划-编辑');
	});
	
	$("body").delegate(".deleteCustBtn","click",function(e){
		del(e.currentTarget);
	});
	
	$("body").delegate("input","blur focus",function(e){
	  	if(e.type=="focusout"){
	  		check(e.currentTarget);
	    }else if(e.type=="focusin"){
	    	$(e.currentTarget).siblings("span").remove();
	    }
	});
	
});

function save(status){
	var planWillcustnumAdd=$("#planWillcustnumAdd").val();
	var planSigncustnumAdd=$("#planSigncustnumAdd").val();
	var planWillcustnumMoney=$("#planWillcustnumMoney").val();
	var planWillcustnumDesc=$("#planWillcustnumDesc").val();
	var custs= new Array();
	$(".custtr").each(function (i,item){
		var custId=$(item).attr("custid");
		
		var planMoney = $(item).find("[name=planMoney]").val();
		var cont=$(item).find("[name=context]").val();
		
		var custName = $(item).find("[name='custName']").text();
		var company = $(item).find("[name='company']").text();
		var custStatus = $(item).find("[name='custStatus']").text();
		var singcustNum = $(item).find("[name='singcustNum']").text();
		if(singcustNum=="意向客户"){
			singcustNum=1;
		}else if(singcustNum=="签约客户"){
			singcustNum=0;
		}else{
			alert("客户状态不识别，请联系管理员！");
		}
		
		custs.push({"custId":custId,"custName":custName,"company":company,"custStatus":custStatus,"singcustNum":singcustNum,"planMoney":planMoney,"context":cont});
	});
		
	plan={"id":planId,
			"planYear":planYear,
			"planMonth": planMonth,
		"planWillcustnumAdd":planWillcustnumAdd,
		"planSigncustnumAdd":planSigncustnumAdd,
		"planWillcustnumMoney":planWillcustnumMoney,
		"planWillcustnumDesc":planWillcustnumDesc,
		"status":status
	};
	pubDivDialog("savePlanDialog",status==0?"你确定要保存计划吗？":"你确定要上报计划吗？",function(){
		$.post(ctx+"/plan/month/user/savePlan",{"planJsonStr":JSON.stringify(plan),"custsJsonStr":JSON.stringify(custs),"status":status,"isNew":isNew},
				function(data){
			if("success"==data.status){
				if(status==1){
					setTimeout('window.top.$("#centerTabs").tabs("close","个人月计划-编辑");',10);
					bakTab();
				}else{
					refreshPage();
				}
			}else{
				warmDivDialog("warm",data.msg);
			}
		});
	});
}

function bakTab(){
	window.top.addTab(ctx+"/plan/month/user/view${_v}","个人月计划");
}

/* 刷新页面*/
function refreshPage(){
	$("#monthPlanForm").submit();
}
</script>
</head>
<body> 
<form id="monthPlanForm" action="${ctx}/plan/month/user/monthEdit${_v}">
	<input id="planYear" name="planYear" type="hidden" value="${dto.planMonth.planYear}"/>
	<input id="month" name="month" type="hidden" value="${dto.planMonth.planMonth}"/>
	<input id="id" name="id" type="hidden" value="${dto.planMonth.id}"/>
</form>
<div class="com-conta">
	<h2 class="hyx-mpe-h2">${dto.planMonth.planYear}年${dto.planMonth.planMonth}月份计划</h2>
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">重点客户</label>
		<a href="javascript:;" class="com-btna demoBtn_b fl_r"><i class="min-add-resou"></i><label class="lab-mar">添加客户</label></a>
	</p>
	<div class="com-table-a hyx-mpe-table hyx-cm-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">操作</span></th> 
					<th><span class="sp sty-borcolor-b">${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</span></th>
					<c:if test="${isState eq 0}"><th><span class='sp sty-borcolor-b'>单位名称</span></th></c:if>
					<th><span class="sp sty-borcolor-b">销售进程</span></th>
					<th><span class="sp sty-borcolor-b">客户状态</span></th>
					<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody id="custTbody">              
				<c:choose>
					<c:when test="${dto!=null and !empty dto.custs}">
						<c:forEach items="${dto.custs}" var="cust" varStatus="i">
							<c:if test="${i.index%2!=0}"><tr custid="${cust.custId}" class="sty-bgcolor-b custtr"></c:if>
							<c:if test="${i.index%2==0}"><tr custid="${cust.custId}" class="custtr"></c:if>
								<td style="width:30px;"><div class="overflow_hidden w30 link">
								<a href="javascript:;" class="icon-dele deleteCustBtn" title="删除"></a>
								</div></td>
								<td><div cname="custName" lass="overflow_hidden w120" title="${cust.custName }">${cust.custName }</div></td>
								<c:if test="${isState eq 0}">
									<td><div name="company" class="overflow_hidden w120" title="${cust.company}">${cust.company}</div></td>
								</c:if>
								<td><div name="custStatus" class="overflow_hidden w70" title="${cust.custStatus }">${cust.custStatus }</div></td>
								<td><div name="singcustNum" class="overflow_hidden w70" value="${cust.singcustNum}" title="${cust.singcustNum==0?'签约客户':'意向客户' }">${cust.singcustNum==0?'签约客户':'意向客户' }</div></td>
								<td><div class="hyx-tip-drop w70"><input name="planMoney"  type="text" value="${cust.planMoney }" class="mpse-ipt w70 doubleNumber" /></div></td>
								<td><div class="hyx-tip-drop w360"><input name="context" type="text" value="${cust.context }" class="mpse-ipt w360" /></div></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="empty"><td colspan="${isState eq 0?7:6}" style="text-align: center;"><b>当前列表无数据！</b></td></tr>
					</c:otherwise>
				</c:choose>
				<tr class="sty-bgcolor-b total">
					<td colspan="${isState eq 0?5:4}"><span class="sp sty-borcolor-b">合计</span></td>
					<td><div class="overflow_hidden w70" title="${dto.custCount.planMoney}">${dto.custCount.planMoney}</div></td>
					<td><div class="overflow_hidden w290"></div></td>
				</tr>
			</tbody>
		</table>
	</div>
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">偏差值调整</label>
	</p>
	<div class="com-table-a hyx-mpe-table hyx-cm-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">新增意向数（个）</span></th>
					<th><span class="sp sty-borcolor-b">新增签约数（个）</span></th>
					<th><span class="sp sty-borcolor-b">回款金额（万）</span></th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>              
				<tr>
					<td><div class="hyx-tip-drop w120"><input id="planWillcustnumAdd" type="text"  value="${dto.planMonth.planWillcustnumAdd}" class="mpse-ipt w110 intNumber" /></div></td>
					<td><div class="hyx-tip-drop w120"><input id="planSigncustnumAdd" type="text"  value="${dto.planMonth.planSigncustnumAdd}" class="mpse-ipt w110 intNumber" /></div></td>
					<td><div class="hyx-tip-drop w120"><input id="planWillcustnumMoney" type="text" value="${dto.planMonth.planWillcustnumMoney}" class="mpse-ipt w110 doubleNumber" /></div></td>
					<td><div class="hyx-tip-drop w360"><input id="planWillcustnumDesc" type="text"  value="${dto.planMonth.planWillcustnumDesc}" class="mpse-ipt w360" />
					</div></td>
				</tr>
			</tbody>
		</table>
	</div>
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">调整后合计</label>
	</p>
	<div class="com-table-a hyx-mpe-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">新增意向数（个）</span></th>
					<th><span class="sp sty-borcolor-b">新增签约数（个）</span></th>
					<th>回款金额（万）</th>
				</tr>
			</thead>
			<tbody>              
				<tr>
					<td><div id="totalWillNum" class="overflow_hidden w110" title="${dto.planMonth.planWillcustnum}">${dto.planMonth.planWillcustnum}</div></td>
					<td><div id="totalSignNum" class="overflow_hidden w110" title="${dto.planMonth.planSigncustnum}">${dto.planMonth.planSigncustnum}</div></td>
					<td><div id="totalSignMoney" class="overflow_hidden w110" title="${dto.planMonth.planMoney}">${dto.planMonth.planMoney}</div></td>
				</tr>
			</tbody>
		</table>
	</div>
	<c:if test="${dto.planMonth.authState==0 }">
	<p class="hyx-mpe-p">
		<label class="lab fl_l sty-bgcolor-a sty-color-a sty-borcolor-d com-radius">审核信息</label>
	</p>
	<div class="com-table-a hyx-mpe-table">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">审核状态</span></th>
					<th><span class="sp sty-borcolor-b">审核方式</span></th>
					<th><span class="sp sty-borcolor-b">审核人</span></th>
					<th><span class="sp sty-borcolor-b">审核时间</span></th>
					<th>审核备注</th>
				</tr>
			</thead>
			<tbody>              
				<tr>
					<c:choose>
						<c:when test="${dto.planMonth.authState==0}"><td><div class="overflow_hidden w110" title="驳回">驳回</div></td></c:when>
						<c:when test="${dto.planMonth.authState==1}"><td><div class="overflow_hidden w110" title="待审核">待审核</div></td></c:when>
						<c:when test="${dto.planMonth.authState==2}"><td><div class="overflow_hidden w110" title="通过">通过</div></td></c:when>
					</c:choose>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.authType}">${dto.planMonth.authType}</div></td>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.authUsername}">${dto.planMonth.authUsername}</div></td>
					<td><div class="overflow_hidden w120" title="<fmt:formatDate pattern="yyyy-MM-dd" value="${dto.planMonth.authTime}"/>"><fmt:formatDate pattern="yyyy-MM-dd" value="${dto.planMonth.authTime}"/></div></td>
					<td><div class="overflow_hidden w110" title="${dto.planMonth.authDesc}">${dto.planMonth.authDesc}</div></td>
				</tr>
			</tbody>
		</table>
	</div>
	</c:if>
	<p class="hyx-mpe-ptit"><label class="com-red">温馨提示：</label>已签约客户，不计入签约客户统计；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px" >偏差值调整，作为本月计划中各个指标的调剂值；</p>
	<p class="hyx-mpe-ptit" style="padding-left:71px" >本月计划审核截止日期为当月${autoAuthDay}日23:00，届时已上报的未审核的计划将自动审核通过；</p>
	<div class="com-btnbox" style="width:405px;padding-bottom:60px;">
		<div class="com-btnbox-cen">
			<a href="javascript:;" class="com-btna com-btna-sty demoBtn_report fl_l"><label>上报</label></a>
			<a href="javascript:;" class="com-btna com-btna-sty demoBtn_save fl_l"><label>保存</label></a>
			<a href="javascript:;" class="com-btna com-btna-sty demoBtn_cancel fl_l"><label>取消</label></a>
		</div>
	</div>
</div>
</body>
</html>