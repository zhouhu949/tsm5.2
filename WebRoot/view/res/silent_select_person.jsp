<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link href="${ctx}/static/css/dialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/static/js/view/res/silent_select.js"></script>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js"></script>
<script type="text/javascript">
	$(function(){
		var that;
		var that1;
		$('tr', this).each(function(row) {
			$('td:eq(0)', this).filter(':visible').each(function(col) {
				if (that!=null && $(this).html() == $(that).html()) {
					rowspan = $(that).attr("rowSpan");
				if (rowspan == undefined) {
					$(that).attr("rowSpan",1);
					rowspan = $(that).attr("rowSpan"); }
					rowspan = Number(rowspan)+1;
					$(that).attr("rowSpan",rowspan);
					$(that).next().attr("rowSpan",rowspan);
					$(that).next().next().next().next().attr("rowSpan",rowspan);
					$(this).css("display","none");
					$(this).next().css("display","none");
					$(this).next().next().next().next().css("display","none");
				} else {
					that = this;
				}
			});
// 			$('td:eq(1)', this).filter(':visible').each(function(col) {
// 				if (that1!=null && $(this).prev().html() == $(that1).prev().html()) {
// 					rowspan = $(that1).attr("rowSpan");
// 				if (rowspan == undefined) {
// 					$(that1).attr("rowSpan",1);
// 					rowspan = $(that1).attr("rowSpan"); }
// 					rowspan = Number(rowspan)+1;
// 					$(that1).attr("rowSpan",rowspan);
// 					$(this).hide();
// 				} else {
// 					that1 = this;
// 				}
// 			});

		});


			if($("#tt1").length > 0){
				$("#tt1").tree({
					url:ctx+"/orgGroup/get_group_user_json",
					checkbox:true,
					onLoadSuccess:function(node,data){
						var oas = $("#ownerAccsStr").val();
						if(oas != null && oas != ''){
							var text='';
							$.each(oas.split(','),function(index,obj){
								var n = $("#tt1").tree("find",obj);
								if(n != null){
									text+=n.text+',';
									$("#tt1").tree("check",n.target).tree("expandTo",n.target);
								}
							});
							if(text != ''){
								text=text.substring(0,text.length-1);
								$("#ownerNameStr").val(text);
							}else{
								$("#ownerNameStr").val("所有者");
							}
						}
					}
				});
			}

	});
	var seleTree=function(){
		var nodes = $('#tt1').tree('getChecked', 'checked');
		var accs = "";
		var names = "";
		$.each(nodes,function(index,obj){
			var type = obj.attributes.type;
			if(type == "M"){
				accs+=obj.id+",";
				names+=obj.text+",";
			}
		});
		if(accs != ""){
			accs=accs.substring(0,accs.length-1);
			names=names.substring(0,names.length-1);
		}else{
			names="所有者";
		}
		$("#ownerAccsStr").val(accs);
		$("#ownerNameStr").val(names);

	}

	var unCheckTree=function(){
		var nodes = $('#tt1').tree('getChecked', 'checked');
		$.each(nodes,function(index,obj){
			 $('#tt1').tree("uncheck",obj.target);
		});
	}
</script>
</head>
<body>
	<form action="${ctx }/res/cust/silentCustSelect" method="post">
		<div class='bomp-cen' style="padding-bottom:0;">
			<div class="com-search bomp-search">
				<div class="com-search-box fl_l">
				   <div class="search clearfix" >
						   <div class="select_outerDiv fl_l">
							    <span class="icon_down_5px"></span>
						   		<select name="queryType" class="fl_l search_head_select">
						   			<option value="3" ${(empty resCustInfoDto.queryType or resCustInfoDto.queryType eq '3') ? 'selected' : ''}>单位名称</option>
							   		<option value="1" ${resCustInfoDto.queryType eq '1' ? 'selected' : ''}>客户姓名</option>
							   		<option value="2" ${resCustInfoDto.queryType eq '2' ? 'selected' : ''}>合同编号</option>
							   	</select>
							</div>
				   		<input class="input-query fl_l" type="text" name="queryText" value="${resCustInfoDto.queryText }" /><label class="hide-span"></label>
				   </div>
				   <div class="list-box">
						<input type="hidden" name="pstartDate" id="s_pstartDate" value="${resCustInfoDto.pstartDate }" />
					    <input type="hidden" name="pendDate" id="s_pendDate" value="${resCustInfoDto.pendDate }"/>
					    <input type="hidden" name="oDateType" id="oDateType" value="${resCustInfoDto.oDateType }" />
					    <c:set var="oDateName" value="失效日期" />
					    <c:choose>
					   		<c:when test="${resCustInfoDto.oDateType eq 1 }"><c:set var="oDateName" value="当天" /></c:when>
					   		<c:when test="${resCustInfoDto.oDateType eq 2 }"><c:set var="oDateName" value="本周" /></c:when>
					   		<c:when test="${resCustInfoDto.oDateType eq 3 }"><c:set var="oDateName" value="本月" /></c:when>
					   		<c:when test="${resCustInfoDto.oDateType eq 4 }"><c:set var="oDateName" value="半年" /></c:when>
					   		<c:when test="${resCustInfoDto.oDateType eq 5 }"><c:set var="oDateName" value="自定义" /></c:when>
					    </c:choose>
						<dl class="select">
							<dt>${oDateName }</dt>
							<dd>
								<ul>
									<li><a href="###" onclick="setPdate(0)" title="失效日期">失效日期</a></li>
									<li><a href="###" onclick="setPdate(1)" title="当天">当天</a></li>
									<li><a href="###" onclick="setPdate(2)" title="本周">本周</a></li>
									<li><a href="###" onclick="setPdate(3)" title="本月">本月</a></li>
									<li><a href="###" onclick="setPdate(4)" title="半年">半年</a></li>
									<li><a href="###" id="pDate" title="自定义" class="diy date-range">自定义</a></li>
								</ul>
							</dd>
						</dl>
						<c:if test="${shiroUser.issys eq 1 }">
							<input type="hidden" name="ownerAccsStr" value="${resCustInfoDto.ownerAccsStr }" id="ownerAccsStr" />
							<div class="reso-sub-dep fl_l">
								<input id="ownerNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="所有者">
								<div class="manage-drop">
									<div class="sub-dep-ul">
										<ul id="tt1">

						       			</ul>
						    		</div>
								    <div class="sure-cancle clearfix" style="width:120px">
										<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" onclick="seleTree()" href="###"><label>确定</label></a>
										<a class="com-btnd bomp-btna reso-sub-clear fl_l" id="close02" onclick="unCheckTree()" href="###"><label>清空</label></a>
									</div>
								</div>
							</div>
						</c:if>
					</div>
				</div>
				<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
			</div>
			<input type="hidden" id="noteType" value="${resCustInfoDto.noteType }" name="noteType" />
			<p class="com-moTag">
				<label class="a">未联系天数：</label>
				<a href="javascript:;" nt="1" class="e${resCustInfoDto.noteType eq '1' ? ' e-hover':'' }">全部</a>
				<a href="javascript:;" nt="2" class="e${resCustInfoDto.noteType eq '2' ? ' e-hover':'' }">7天</a>
				<a href="javascript:;" nt="3" class="e${resCustInfoDto.noteType eq '3' ? ' e-hover':'' }">30天</a>
				<a href="javascript:;" nt="4" class="e${resCustInfoDto.noteType eq '4' ? ' e-hover':'' }">90天</a>
<%-- 				<input type="text" id="days" name="days" value="${resCustInfoDto.days }" class="diy-box" /><label class="diy-day">天</label> --%>
			</p>
			<div class="com-btnlist fl_l">
				<input type="hidden" value="${selectType }" name="selectType" />
				<c:choose>
					<c:when test="${selectType eq '1' }">
						<a href="javascript:;" id="silentBtn" class="com-btna ml demoBtn_a"><label>转为沉默客户</label></a>
					</c:when>
					<c:otherwise>
						<a href="javascript:;" id="losingBtn" class="com-btna ml demoBtn_a"><label>转为流失客户</label></a>
					</c:otherwise>
				</c:choose>
			</div>
			<div class='com-table-a bomp-table-a fl_l'>
				<table width='100%' cellspacing='0' cellpadding='0' class='sty-borcolor-a sty-borcolor-c com-table-b com-radius'>
					<thead>
						<tr class='sty-bgcolor-b'>
							<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
							<th><span class='sp sty-borcolor-b'>客户姓名</span></th>
							<th><span class='sp sty-borcolor-b'>单位名称</span></th>
							<th><span class='sp sty-borcolor-b'>合同编号</span></th>
							<th><span class='sp sty-borcolor-b'>失效日期</span></th>
							<th>未联系天数</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${!empty custs }">
								<c:forEach items="${custs }" var="cust">
									<tr>
										<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" id="chk_${cust.resCustId }" /></div></td>
										<td><div class='overflow_hidden w90' title='${cust.name }'>${cust.name }</div></td>
										<td><div class='overflow_hidden w120' title='${cust.company }'>${cust.company }</div></td>
										<td><div class='overflow_hidden w90' title='${cust.contractCode }'>${cust.contractCode }</div></td>
										<td><div class='overflow_hidden w70' title='<fmt:formatDate value="${cust.contractEndDate }" pattern="yyyy-MM-dd"/>'><fmt:formatDate value="${cust.contractEndDate }" pattern="yyyy-MM-dd"/></div></td>
										<td><div class='overflow_hidden w50' title='${cust.days }${empty cust.days ? '' : '天' }'>${cust.days }${empty cust.days ? '' : '天' }</div></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr class="no_data_tr">
									<td>
										当前列表无数据!
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<div class="com-bot">
				<div class="com-page fl_r" style="height:auto;">${resCustInfoDto.page.pageStr }</div>
			</div>
		</div>
	</form>
</body>
</html>
