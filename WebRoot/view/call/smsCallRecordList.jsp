<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<style type="text/css">
	.com-search .hide-span{left: 10px;}
</style>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tree_top_part.css"/>
	<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
<title>通信管理-来电短信发送记录</title>
</head>
<body>
<div class="hyx-ctr" >
<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
<form id="myForm" action="${ctx}/sms/call/record/smsSendList" method="post">
   <div class="com-search hyx-cfu-search">
      <div class="com-search-box fl_l">
	  <div class="search clearfix" >
		   <div class="select_outerDiv fl_l">
		   <span class="icon_down_5px"></span>
		    <select class="fl_l search_head_select"  name="queryType">
	   		<option value = "1"  <c:if test="${queryType eq 1}">selected</c:if>>主叫号码</option>
	   		<option value = "2"  <c:if test="${queryType ne 1 }">selected</c:if>>接收号码</option>
	   		</select>
		   	</div>
		   	<input class="input-query fl_l" type="text" name="queryText" value="${queryText}" />
		   	<label class="hide-span"></label>
		</div>
	   <div class="list-box">
	   			<input type="hidden" name="startDate" id="startDate" value="${item.startDate }" />
			   <input type="hidden" name="endDate" id="endDate" value="${item.endDate }"/>
			   <input type="hidden" name="dDateType" id="dDateType" value="${dDateType }" />
			   <c:set var="dDateName" value="来电时间" />
			   <c:choose>
			   		<c:when test="${dDateType eq 1 }"><c:set var="dDateName" value="当天" /></c:when>
			   		<c:when test="${dDateType eq 2 }"><c:set var="dDateName" value="本周" /></c:when>
			   		<c:when test="${dDateType eq 3 }"><c:set var="dDateName" value="本月" /></c:when>
			   		<c:when test="${dDateType eq 4 }"><c:set var="dDateName" value="半年" /></c:when>
			   		<c:when test="${dDateType eq 5 }">
			   				<fmt:parseDate var="psd" value="${item.startDate }" pattern="yyyy-MM-dd" />
							<fmt:parseDate var="ped" value="${item.endDate }" pattern="yyyy-MM-dd" />
				   			<c:set var="dDateName">
				   				<fmt:formatDate value="${psd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ped }" pattern="yy.MM.dd"/>
				   			</c:set>
			   		</c:when>
			   </c:choose>
		    <dl class="select">
				<dt>${dDateName }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="setNdate(0)">来电时间</a></li>
						<li><a href="###" onclick="setNdate(1)">当天</a></li>
						<li><a href="###" onclick="setNdate(2)">本周</a></li>
						<li><a href="###" onclick="setNdate(3)">本月</a></li>
						<li><a href="###" onclick="setNdate(4)">半年</a></li>
						<li><a href="###" onclick="setNdate(5)"class="diy date-range" id="d1">自定义</a></li>
					</ul>
				</dd>
			</dl>
				<div class="reso-sub-dep fl_l" style="margin-right:10px">
							<input type="hidden" name="accs" id="accs"  value="${not empty item.accs ? item.accs : shrioUser.account}">
							<input type="hidden" name="osType" value="${item.osType}" id="osType" />
							<input class="owner-sour" id="accNames"  type="text"  value="${not empty item.accs ? item.accs : shrioUser.name}">
					<div class="manage-owner-sour" style="height:370px;">
						<div class="shows-radio-boxs"></div>
						<ul class="shows-allorme-boxs">
							<li><input type="radio"  value="1"  name="searchOwnerType" ${item.osType eq '1' ? 'checked':'' }>查看全部</li>
							<li><input type="radio"  value="2"  name="searchOwnerType" ${item.osType eq '2' ? 'checked':'' }>查看自己</li>
						</ul>
						<div class="sub-dep-ul" data-type="search-tree">
							<ul id="tt1">

					        </ul>
			    		</div>
					    <div class="sure-cancle clearfix" style="width:120px">
							<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" id="checkedId" href="javascript:;"><label>确定</label></a>
							<a class="com-btnd bomp-btna fl_l" id="clearId" href="javascript:;"><label>清空</label></a>
						</div>
					</div>
				</div>
			<!-- <p class="check skin-minimal fl_l"><input type="checkbox" id="isSys_check" <c:if test="${not empty item.isSys and item.isSys eq 0 }">checked</c:if>/><label>系统发送</label></p> -->
		</div>
		<input type="hidden" id="isSys" name="isSys" value="1">

	</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
    </div>
	<!-- <p class="com-moTag skin-minimal com-mta fl_l" ><label class="f">当前跟进警报为：2条。</label></p> -->
	<div class="com-table com-mta hyx-cla-table fl_l" style="display:block;margin-top:5px !important;">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">主叫号码</span></th>
					<th><span class="sp sty-borcolor-b">接收号码</span></th>
					<th>接收人</th>
					<th><span class="sp sty-borcolor-b">来电时间</span></th>
					<th><span class="sp sty-borcolor-b">发送时间</span></th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${not empty list }">
						<c:forEach items="${list }" var="sms" varStatus="vs">
							<tr class=" ${vs.count%2==0?'sty-bgcolor-b':''}">
								<td title="${sms.callerNum }"><div class="overflow_hidden w120"  name="callerNum_">${sms.callerNum }</div></td>
								<td title="${sms.calledNum }"><div class="overflow_hidden w120" name="calledNum_">${sms.calledNum }</div></td>
								<td><div class="overflow_hidden w120" title="${sms.ownerName }">${sms.ownerName }</div></td>
								<td><div class="overflow_hidden w120" title="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${sms.callDate}"/>"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${sms.callDate}"/></div></td>
								<td><div class="overflow_hidden w120" title="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${sms.sendDate}"/>"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${sms.sendDate}"/></div></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="no_data_tr">
							<td colspan="6" style="text-align: center;">
                              		<div class="overflow_hidden w120" title="当前列表无数据">当前列表无数据！</div>
                          		</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	<div class="com-bot" >
		<div class="com-page fl_r">
			${item.page.pageStr}
		</div>
	</div>
	</form>
</div>
</body>
<script type="text/javascript">
$(function(){

	// 通话日期
    $('#d1').dateRangePicker({
    	showShortcuts:false,
        endDate:getNowFormatDate(),format: 'YYYY-MM-DD'
    }).bind('datepicker-apply',function(event,obj){
    	var s_t = '';
    	var e_t = '';
    	if(obj.date1 != 'Invalid Date'){
    		$('#startDate').val(moment(obj.date1).format('YYYY-MM-DD'));
    		s_t = moment(obj.date1).format('YY.MM.DD');
    	}
    	if(obj.date2 != 'Invalid Date'){
    		$('#endDate').val(moment(obj.date2).format('YYYY-MM-DD'));
    		e_t = moment(obj.date2).format('YY.MM.DD');
    	}
    	var s = $(this).parents('.select');
    	var dt=s.children("dt");
        var dd=s.children("dd");
        dt.html(s_t+'/'+e_t);
        $("#dDateType").val(5);
        dd.slideUp(200);
        dt.removeClass("cur");
        //s.css("z-index",z);
 });
	 /* 屏蔽手机或固话中间几位  */
    var idReplaceWord = $("#idReplaceWord").val();
	if(idReplaceWord==1){
	    $("div[name=callerNum_]").each(function(){
	    		   var str = $(this).text();
	    		   replaceWord(str,$(this));
	    		   replaceTitleWord(str,$(this));
	    		  });
	    $("div[name=calledNum_]").each(function(){
	    		   var str = $(this).text();
	    		   replaceWord(str,$(this));
	    		   replaceTitleWord(str,$(this));
	    		  });
	}
    /*所有者*/
    	 $("input[name=searchOwnerType]").iCheck({
 	    	radioClass: 'iradio_minimal-green'
 		 });
	    $("input[name=searchOwnerType]").on('ifClicked',function(){
	    	var nodes = $('#tt1').tree('getChecked');
	    	if(nodes.length > 0){
	    		$.each(nodes,function(index,obj){
	    			 $('#tt1').tree("uncheck",obj.target);
	    		});
	    	}
	    });
    	var url ="${ctx}/orgGroup/get_all_group_user_json";
    	$("#tt1").tree({
    		url:url,
    		checkbox:true,
    		onLoadSuccess:function(node,data){
    			var $accs = $('#accs').val();
    			$("#tt1").tree("collapseAll");
				for(var i=0;i<data.length;i++){
					$("#tt1").tree("expand",data[i].target);
				}
				var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
				if(searchOwnerType == '1'){
					$("#accNames").val("查看全部");
				}else if(searchOwnerType == '2'){
					$("#accNames").val("查看自己");
				}else if($accs != null && $accs.length > 0){
    				var text='';
    				$($accs.split(',')).each(function(index,data){
    					var node = $("#tt1").tree("find",data);
    					if(node != null){
    						text+=node.text+',';
    						$("#tt1").tree("check",node.target);
    					}
    				});
    				if(text != ''){
    					text=text.substring(0,text.length-1);
    					$("#accNames").val(text);
    				}else{
						$("#accNames").val("接收人");
					}
    			}
    		},
			onCheck:function(node,isCheck){
				var nodes = $('#tt1').tree('getChecked');
				if(nodes.length > 0){
					$("input[name=searchOwnerType]").iCheck('uncheck');
				}else if($("input[name=searchOwnerType]:checked").length == 0){
					$("input[name=searchOwnerType]:eq(0)").iCheck('check');
				}
			}
    	});


	// 选择点保存
	$("#checkedId").click(function() {
		var nodes =$("#tt1").tree("getChecked");
		var accs = "";
		var text='';
		$(nodes).each(function(index,obj){
			if(obj.attributes.type == 'M'){
				accs+=","+obj.id;
				text+=obj.text+',';
			}
		});
		if(accs.length > 0){
			accs=accs.substring(1,accs.length);
			text=text.substring(0,text.length-1);
			$("#osType").val("3");
		}else{
			var searchOwnerType = $("input[name=searchOwnerType]:checked").val();
			if(searchOwnerType == '1'){
				text="查看全部";
			}else{
				text="查看自己";
			}
			$("#osType").val(searchOwnerType);
		}
		$("#accs").val(accs);
		$("#accNames").val(text);
	});

	//清空
	$('#clearId').click(function(){
		var nodes = $('#tt1').tree('getChecked');
		 $(nodes).each(function(index,obj){
        	$('#tt1').tree('uncheck', obj.target);
        });
        $('#accs').val('');
        $("#accNames").val('');
	});


	// 是否选中 系统发送 查看挂机短信
		$('#isSys_check').on('ifChecked',function(){
			$("#isSys").val("0");
		});

		$('#isSys_check').on('ifUnchecked',function(){
			$("#isSys").val("1");
		});


  	//全选/全不选
	$("#allCheck").click(function(){
		 $("input[name='smsId_ckb']").attr("checked", $("#allCheck").is(":checked"));
	});

  	// 删除
	$('.demoBtn_a').click(function(){
		var ids = getAllCheckBoxValuesAndSnames();
		if(ids == ""){
			window.top.iDialogMsg("提示","请选择！");
			return;
		}
		pubDivDialog("res_remove_cust","是否删除？",function(){
			$.ajax({
				url:ctx+'/sms/record/deleteSmsSend',
				type:'post',
				data:{ids:ids},
				dataType:'json',
				error:function(){alert('网络异常，请稍后再试！')},
				success:function(data){
					if(data == 0){
						window.top.iDialogMsg("提示",'删除成功！');
						setTimeout("document.forms[0].submit();",1000);
					}else{
						window.top.iDialogMsg("提示",'删除失败！');
					}
				}
			});
		});
	});
});

// 列表中选中的复选框
function getAllCheckBoxValuesAndSnames() {
	var returns = "";
	$("input[name='smsId_ckb']").each(function(index) {
		if ($(this).attr("checked")) {
			var appid = $(this).val();
			if (appid && appid != null) {
				returns += appid + ",";
			}
		}
	});
	return returns;
}
// 弹窗短信明细
function sendDetail(smsId){
	pubDivShowIframe('alert_send_text_mess','${ctx}/sms/record/smsSendDetailList.do?smsId='+smsId,'发送短信明细',700,550);
}
var setNdate = function(i){
	$('#startDate').val('');
	$('#endDate').val('');
	$("#dDateType").val(i);
};
</script>

<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
</html>
