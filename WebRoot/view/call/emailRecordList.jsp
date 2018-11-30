<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<title>通信管理-邮件发送记录</title>
	<%@ include file="/common/include.jsp" %>
	<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
	<% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tree_top_part.css"/>
	<style type="text/css">
		.com-search .hide-span{left: 10px;}
	</style>
</head>
<body>
	<div class="hyx-cmc hyx-layout">
		<!--资源-->
		<div class=" hyx-layout-content">
			<form action="${ctx}/email/record/emailSendList" method="post">
				<div class="com-search hyx-cm-search">
					<div class="com-search-box fl_l">
					   <p class="search clearfix"><input class="input-query fl_l" type="text" name="title" value="${item.title }" /><label class="hide-span">输入邮件主题</label></p>
					   <div class="list-box">
					   		<input type="hidden" name="startDate" id="startDate" value="${item.startDate }" />
						   <input type="hidden" name="endDate" id="endDate" value="${item.endDate }"/>
						   <input type="hidden" name="dDateType" id="dDateType" value="${dDateType }" />
						   <c:set var="dDateName" value="发送时间" />
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
								<dt>${dDateName}</dt>
								<dd>
									<ul>
									<li><a href="###" onclick="setNdate(0)">发送时间</a></li>
									<li><a href="###" onclick="setNdate(1)">当天</a></li>
									<li><a href="###" onclick="setNdate(2)">本周</a></li>
									<li><a href="###" onclick="setNdate(3)">本月</a></li>
									<li><a href="###" onclick="setNdate(4)">半年</a></li>
									<li><a href="###" onclick="setNdate(5)"class="diy date-range" id="d1">自定义</a></li>
									</ul>
								</dd>
							</dl>
							<div class="reso-sub-dep fl_l" style="margin-right:165px">
							<c:choose>
							<c:when test="${shrioUser.issys eq 0}">
								<input class="owner-sour-nostr"  name="accs" type="text" readonly="readonly" value="${shrioUser.name}">
							</c:when>
							<c:otherwise>
								<input type="hidden" name="accs" id="accs"  value="${not empty item.accs ? item.accs : shrioUser.account}">
								<input type="hidden" name="osType" value="${item.osType}" id="osType" />
								<input class="owner-sour" id="accNames"  type="text"  value="${not empty item.accs ? item.accs : shrioUser.name}">
							</c:otherwise>
						</c:choose>
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
						</div>
					</div>
					<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query" />
				</div>
				<div class="com-btnlist hyx-cm-btnlist fl_l">
					<a class="com-btna demoBtn_a fl_l" href="###"><i class="min-dele"></i><label class="lab-mar">删除</label></a>
				</div>
				<div class="com-table com-mta hyx-cm-table fl_l" style="display:block;">
					<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
						<thead>
							<tr class="sty-bgcolor-b" >
								<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
								<th><span class="sp sty-borcolor-b">发送时间</span></th>
								<th><span class="sp sty-borcolor-b">收件人</span></th>
								<th><span class="sp sty-borcolor-b "><label class="lab-d"><span>邮件主题</span></label></span></th>
								<th>发件人</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${not empty list}">
									<c:forEach items="${list }" var="email" varStatus="vs">
										<c:if test="${vs.index == 0 }">
										<!--  取第一条邮件发送记录ID -->
										<input type="hidden" id="sendLogId_" value="${email.sendLogId }">
									</c:if>
										<tr class=" ${vs.count%2==0?'sty-bgcolor-b':''}" onclick="loadRecordRight('${email.sendLogId }')">
											<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox"  name="check_" value="${email.id }" id="chk_${email.id }"/></div></td>
											<td><div class="overflow_hidden w120" title="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${email.inputTime}"/>"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${email.inputTime}"/></div></td>
											<td><div class="overflow_hidden w200" title="${email.emailReviceUser }">${fn:escapeXml(email.emailReviceUser)}</div></td>
											<td><div class="overflow_hidden w220" title="${email.title }">${email.title }</div></td>
											<td><div class="overflow_hidden w90" title="${email.sendUser }">${email.sendUser }</div></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
								<tr>
									<td colspan="5" style="text-align: center;">
		                              		<div class="overflow_hidden w120" title="当前列表无数据">当前列表无数据！</div>
		                          		</td>
								</tr>
						</c:otherwise>
							</c:choose>
				</tbody>
			</table>
				</div>
				<div class="com-bot">
					<div class="com-page fl_r">
						${item.page.pageStr}
					</div>
				</div>
				</form>
			</div>

			<div class="hyx-cm-right hyx-layout-side" id="cm_right" style="padding:0 10px;">
				<!--<c:import url="/view/call/submodual/emailRecordList_right.jsp"/>-->
			</div>
	</div>
</body>
<script type="text/javascript">
$(function(){
	// 异步加载右侧信息
    loadRecordRight($("#sendLogId_").val());

    $('.demoBtn_a').click(function(){
    	var ids = getAllCheckBoxValuesAndSnames();
		if(ids == ""){
			window.top.iDialogMsg("提示","请选择！");
			return;
		}
		pubDivDialog("res_remove_cust","是否删除？",function(){
			$.ajax({
				url:ctx+'/email/record/deleteEmailSend',
				type:'post',
				data:{ids:ids},
				dataType:'json',
				error:function(){alert('网络异常，请稍后再试！');},
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

    // 发送时间
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

    /*所有者*/
	var issys = '${shrioUser.issys}';
    if(issys == 1){
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
    	var url ="${ctx}/orgGroup/get_group_user_json.do";
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
						$("#accNames").val("所有者");
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
    }


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


	//全选/全不选
	$("#allCheck").click(function(){
		 $("input[name='check_']").attr("checked", $("#allCheck").is(":checked"));
	});

});

//列表中选中的复选框
function getAllCheckBoxValuesAndSnames() {
	var returns = "";
	$("input[name='check_']").each(function(index) {
		if ($(this).attr("checked")) {
			var appid = $(this).val();
			if (appid && appid != null) {
				returns += appid + ",";
			}
		}
	});
	return returns;
}

/** 异步加载 右侧列表信息 */
function loadRecordRight(sendLogId){
	 $.ajax({
			url: '${ctx}/email/record/rightEmailPage.do',
			type:'POST',
			data:{'sendLogId':sendLogId},
			dataType:'html',
			error:function(){alert("网络异常，请稍后再操作！");},
			success:function(data){
				$("#cm_right").html(data);
			}
		});
}
</script>
<script type="text/javascript">
window.onload=function(){
	var height = $(".hyx-cmc").height()+30;
	window.parent.$("#iframepage").css({"height":height+"px"});
};
var setNdate = function(i){
	$('#startDate').val('');
	$('#endDate').val('');
	$("#dDateType").val(i);
};

function emailDownLoad(filePath,fileName){
	var url = ctx+"/email/record/emailDownLoad?filePath="+filePath+"&fileName="+encodeURIComponent(encodeURIComponent(fileName,"utf-8"));
	document.location = url;
}
</script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/x-handlebars-template" id="template">
  {{#each list}}
	<tr class="{{even_odd @index}}" onclick="loadRecordRight('{{sendLogId }}')">
        <td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox"  name="check_" value="{{id }}"/></div></td>
        <td><div class="overflow_hidden w100" title="{{formatDate inputTime 'YYYY-MM-DD'}}">{{formatDate inputTime 'YYYY-MM-DD'}}</div></td>
        <td><div class="overflow_hidden w200" title="{{emailReviceUser }}">{{emailReviceUser}}</div></td>
        <td><div class="overflow_hidden w220" title="{{title }}">{{title }}</div></td>
        <td><div class="overflow_hidden w90" title="{{sendUser }}">${sendUser }</div></td>
    </tr>
 {{/each}}
</script>
</html>
