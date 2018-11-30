<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tree_top_part.css${_v}"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/progressBar/progress_detail.css${_v}"/>

<style>
	.tree-node{width:240px;}
	.hyx-hsm-left dl{text-align:left;}
	.hyx-hsm-left dl dt,.hyx-hsm-left dl dd{text-indent:30px;}
	.com-search .hide-span{left:110px;}
</style>
<style type="text/cccss" rel="stylesheet">
.com-table>table>tbody>tr>td:nth-child(1),.com-table>table>thead>tr>th:nth-child(1){display: none;}
.com-table>table>tbody>tr>td:nth-child(2),.com-table>table>thead>tr>th:nth-child(2){display: none;}
.com-table>table>tbody>tr>td:nth-child(3),.com-table>table>thead>tr>th:nth-child(3){display: none;}
.com-table>table>tbody>tr>td:nth-child(4),.com-table>table>thead>tr>th:nth-child(4){display: none;}
.com-table>table>tbody>tr>td:nth-child(5),.com-table>table>thead>tr>th:nth-child(5){display: none;}
.com-table>table>tbody>tr>td:nth-child(6),.com-table>table>thead>tr>th:nth-child(6){display: none;}
.com-table>table>tbody>tr>td:nth-child(7),.com-table>table>thead>tr>th:nth-child(7){display: none;}
.com-table>table>tbody>tr>td:nth-child(8),.com-table>table>thead>tr>th:nth-child(8){display: none;}
.com-table>table>tbody>tr>td:nth-child(9),.com-table>table>thead>tr>th:nth-child(9){display: none;}
.com-table>table>tbody>tr>td:nth-child(10),.com-table>table>thead>tr>th:nth-child(10){display: none;}
.com-table>table>tbody>tr>td:nth-child(11),.com-table>table>thead>tr>th:nth-child(11){display: none;}
.com-table>table>tbody>tr>td:nth-child(12),.com-table>table>thead>tr>th:nth-child(12){display: none;}
.com-table>table>tbody>tr>td:nth-child(13),.com-table>table>thead>tr>th:nth-child(13){display: none;}
.com-table>table>tbody>tr>td:nth-child(14),.com-table>table>thead>tr>th:nth-child(14){display: none;}
.com-table>table>tbody>tr>td:nth-child(15),.com-table>table>thead>tr>th:nth-child(15){display: none;}
.com-table>table>tbody>tr>td:nth-child(16),.com-table>table>thead>tr>th:nth-child(16){display: none;}
</style>
</head>
<body>
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<form action="${ctx }/res/sea/highSeasList" method="post">
		<input type="hidden" name="resGroupId" id="h_resGroupId" value="${resCustInfoDto.resGroupId }" />
		<input type="hidden" name="isShareRes" id="h_isShareRes" value="${resCustInfoDto.isShareRes }" />
		<div class="hyx-dfpzy-reso hyx-layout">
			<div class="hyx-hsm-left hyx-layout-side" style="overflow-y:auto;">
				<dl>
					<dt>资源分组</dt>
					<dd class="${empty resCustInfoDto.resGroupId ? 'dd-hover' : '' }">所有资源<%-- (${allSum }) --%></dd>
					<c:forEach items="${groupList }" var="gl">
						<dd class="${resCustInfoDto.resGroupId eq gl.resGroupId ? 'dd-hover' : '' }" groupId="${gl.resGroupId }">${gl.groupName }<%-- (${gl.groupCount }) --%></dd>
					</c:forEach>
				</dl>
			</div>
			<div class="hyx-hsm-right1 hyx-layout-content" style="border:none;">
				<div class="com-search hyx-cm-search high_seas_listbox_width">
					<div class="com-search-box fl_l">
					   <div class="search clearfix" >
						   <div class="select_outerDiv fl_l">
							    <span class="icon_down_5px"></span>
						   		<select name="queryType" class="fl_l search_head_select">
							   		<option value="5" ${(empty resCustInfoDto.queryType or resCustInfoDto.queryType eq '5') ? 'selected' : ''}>单位名称</option>
							   		<option value="1" ${resCustInfoDto.queryType eq '1' ? 'selected' : ''}>客户姓名</option>
							   		<option value="3" ${resCustInfoDto.queryType eq '3' ? 'selected' : ''}>联系电话</option>
							   		<c:if test="${isOpenReason eq '1'}">
							   			<option value="4" ${resCustInfoDto.queryType eq '4' ? 'selected' : ''}>资源放弃原因</option>
							   		</c:if>
							   		<c:forEach items="${queryFileds }" var="field">
							   			<option value="${field.fieldCode }" ${resCustInfoDto.queryType eq field.fieldCode ? 'selected' : ''}>${field.fieldName }</option>
							   		</c:forEach>
							   	</select>
							</div>
					   		<input class="input-query fl_l" id="queryText" type="text" name="queryText" value="${resCustInfoDto.queryText }" /><label class="hide-span"></label>
					   </div>
					   <div class="list-box">
						   <c:set var="nDateName" value="放弃时间" />
						   <c:choose>
						   		<c:when test="${resCustInfoDto.nDateType eq 1 }"><c:set var="nDateName" value="当天" /></c:when>
						   		<c:when test="${resCustInfoDto.nDateType eq 2 }"><c:set var="nDateName" value="本周" /></c:when>
						   		<c:when test="${resCustInfoDto.nDateType eq 3 }"><c:set var="nDateName" value="本月" /></c:when>
						   		<c:when test="${resCustInfoDto.nDateType eq 4 }"><c:set var="nDateName" value="半年" /></c:when>
						   		<c:when test="${resCustInfoDto.nDateType eq 5 }">
						   			<fmt:parseDate var="sd" value="${resCustInfoDto.startDate }" pattern="yyyy-MM-dd" />
					   				<fmt:parseDate var="ed" value="${resCustInfoDto.endDate }" pattern="yyyy-MM-dd" />
						   			<c:set var="nDateName">
						   				<fmt:formatDate value="${sd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ed }" pattern="yy.MM.dd"/>
						   			</c:set>
						   		</c:when>
						   </c:choose>
						   <dl class="select">
							   <input type="hidden" name="startDate" id="s_startDate" value="${resCustInfoDto.startDate }" />
							   <input type="hidden" name="endDate" id="s_endDate" value="${resCustInfoDto.endDate }"/>
							   <input type="hidden" name="nDateType" id="nDateType" value="${resCustInfoDto.nDateType }" />
								<dt>${nDateName }</dt>
								<dd>
									<ul>
										<li><a href="###" onclick="setNDate(0)" title="放弃时间">放弃时间</a></li>
										<li><a href="###" onclick="setNDate(1)" title="当天">当天</a></li>
										<li><a href="###" onclick="setNDate(2)" title="本周">本周</a></li>
										<li><a href="###" onclick="setNDate(3)" title="本月">本月</a></li>
										<li><a href="###" onclick="setNDate(4)" title="半年">半年</a></li>
										<li><a href="###" id="nDate" title="自定义" class="date-range diy">自定义</a></li>
									</ul>
								</dd>
						   </dl>
						   <!-- 销售进程 -->
							<c:set var="saleProcessName" value="销售进程" />
							<c:forEach items="${options }" var="ops">
								<c:if test="${ops.optionlistId eq resCustInfoDto.saleProcessId  }">
									<c:set var="saleProcessName" value="${ops.optionName }" />
								</c:if>
							</c:forEach>
							<dl class="select">
								<input type="hidden" name="saleProcessId" id="s_saleProcessId" value="${resCustInfoDto.saleProcessId }" />
								<dt>${saleProcessName }</dt>
								<dd>
									<ul>
										<li><a href="###" onclick="$('#s_saleProcessId').val('')" title="销售进程">销售进程</a></li>
										<c:forEach items="${options }" var="os">
											<li><a href="###" onclick="$('#s_saleProcessId').val('${os.optionlistId}')" title="${os.optionName }">${os.optionName }</a></li>
										</c:forEach>
									</ul>
								</dd>
							</dl>
						   <!-- 回收类型 -->
						   <c:set var="opName">放弃类型</c:set>
						   <c:choose>
								<c:when test="${'11' eq resCustInfoDto.opreateType}"><c:set var="opName">客户-到期未签约</c:set></c:when>
								<c:when test="${'16' eq resCustInfoDto.opreateType}"><c:set var="opName">客户-到期未跟进</c:set></c:when>
								<c:when test="${'12' eq resCustInfoDto.opreateType}"><c:set var="opName">客户-主动放弃</c:set></c:when>
								<c:when test="${'4' eq resCustInfoDto.opreateType}"><c:set var="opName">资源-沟通失败</c:set></c:when>
								<c:when test="${'5' eq resCustInfoDto.opreateType}"><c:set var="opName">资源-信息错误</c:set></c:when>
						   </c:choose>
						   <dl class="select">
							   <input type="hidden" name="opreateType" id="s_opreateType" value="${resCustInfoDto.opreateType }"/>
								<dt>${opName }</dt>
								<dd>
									<ul>
										<li><a href="###" onclick="$('#s_opreateType').val('');" title="放弃类型">放弃类型</a></li>
										<li><a href="###" onclick="$('#s_opreateType').val('11');" title="客户-到期未签约">客户-到期未签约</a></li>
										<li><a href="###" onclick="$('#s_opreateType').val('16');" title="客户-到期未跟进">客户-到期未跟进</a></li>
										<li><a href="###" onclick="$('#s_opreateType').val('12');" title="客户-主动放弃">客户-主动放弃</a></li>
										<li><a href="###" onclick="$('#s_opreateType').val('4');" title="资源-沟通失败">资源-沟通失败</a></li>
										<li><a href="###" onclick="$('#s_opreateType').val('5');" title="资源-信息错误">资源-信息错误</a></li>
									</ul>
								</dd>
							</dl>
							<!-- 销售人员 -->
							<div class="reso-sub-dep fl_l">
								<input style="border-right: 2px solid #e1e1e1;" id="ownerNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="销售人员">
								<input type="hidden" name="ownerAccsStr" value="${resCustInfoDto.ownerAccsStr }" id="ownerAccsStr" />
								<input type="hidden" name="osType" value="${resCustInfoDto.osType }" id="osType" />
								<div class="manage-drop"  style="height:370px;">
									<div class="shows-radio-boxs"></div>
									<ul class=" 1">
										<li><input type="radio"  value="1"  name="searchOwnerType" ${resCustInfoDto.osType eq '1' ? 'checked':'' }>查看全部</li>
										<li><input type="radio" name="searchOwnerType"  value="2" ${resCustInfoDto.osType eq '2' ? 'checked':'' }>查看自己</li>
									</ul>
									<div class="sub-dep-ul" style="width:260px;" data-type="search-tree">
										<ul id="tt1">

						       			</ul>
						    		</div>
								    <div class="sure-cancle clearfix" style="width:120px">
										<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" onclick="seleTree()" href="###"><label>确定</label></a>
										<a class="com-btnd bomp-btna fl_l" id="close02" onclick="unCheckTree()" href="###"><label>清空</label></a>
									</div>
								</div>
							</div>
							<c:if test="${isShare eq '1' }">
								<!-- <p class="check skin-minimal fl_l" style="margin:0 0 0 10px"><input id="checkShare" ${resCustInfoDto.isShareRes eq '1' ? 'checked' : ''} type="checkbox" /><label>查看共享资源</label></p> -->
							<c:choose>
								<c:when test="${resCustInfoDto.isShareRes eq '0' }">
									<c:set var="searchName">公海客户</c:set>
								</c:when>
								<c:otherwise>
									<c:set var="searchName">共享资源</c:set>
								</c:otherwise>
							</c:choose>
							<dl class="select">
								<dt style="border:0 none !important;">${searchName }</dt>
								<dd>
									<ul>
										<li><a href="###" onclick="setSearch('0')" title="公海客户">公海客户</a></li>
										<li><a href="###" onclick="setSearch('1')" title="共享资源">共享资源</a></li>
									</ul>
								</dd>
							</dl>
							</c:if>

						</div>
					</div>
					<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
				</div>
				<p class="com-moTag" >
					<label class="a">行动标签：</label>
					<c:forEach items="${labelNamesList }" var="ln">
						<span label="1" class="e">${ln }</span>
					</c:forEach>
					<span id="addLabelBtn" style="cursor: pointer;color: blue;" class="e">选择标签</span>
					<input id="allLabels" name="allLabels" type="hidden" value="${resCustInfoDto.allLabels }"/>
				</p>
				<div class="com-btnlist hyx-cm-btnlist fl_l">
					<c:choose>
						<c:when test="${shiroUser.issys eq 1 }">
							<a href="###" id="distributeBtn" class="com-btna demoBtn_c fl_l"><i class="min-reass"></i><label class="lab-mar">重新分配</label></a>
							<a href="###" id="delBtn" class="com-btna demoBtn_b fl_l"><i class="min-dele"></i><label class="lab-mar">删&nbsp;&nbsp;&nbsp;&nbsp;除</label></a>
							<a href="###" id="cleanBtn" class="com-btna demoBtn_a fl_l"><i class="min-dustbin"></i><label class="lab-mar">清&nbsp;&nbsp;&nbsp;&nbsp;空</label></a>
							<a href="###" id="recyleBtn" <c:if test="${resCustInfoDto.isShareRes eq '1'}">style="display:none;"</c:if> class="com-btna demoBtn_d fl_l"><i class="min-back"></i><label class="lab-mar">回收待分配</label></a>
							<%-- <a href="###" id="progressBtn" <c:if test="${resCustInfoDto.isShareRes eq '1'}">style="display:none;"</c:if> class="com-btna fl_l"><i class="min-back"></i><label class="lab-mar">回收进度</label></a>
							<div class="progress_bar"></div> --%>
						</c:when>
						<c:otherwise>
							<c:if test="${isBatchRecyle eq '1' }">
								<a href="###" id="getBackBtn" class="com-btna demoBtn_c fl_l"><i class="min-back"></i><label class="lab-mar">取回</label></a>
							</c:if>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="hid" style="display:block;">
					<div class="com-table com-mta test-table fl_l">
						<!-- <p class="reso-math">目前共有资源数：<span>3</span>条</p> -->
						<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
							<thead>
								<tr role="head" class="sty-bgcolor-b">
									<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" id="checkAll" class="check" /></span></th>
									<th><span class="sp sty-borcolor-b">操作</span></th>
									<th hidesort="true"><span class="sp sty-borcolor-b" sort="true">放弃时间</span></th>
									<th><span class="sp sty-borcolor-b">客户姓名</span></th>
									<th><span class="sp sty-borcolor-b">单位名称</span></th>
									<th><span class="sp sty-borcolor-b">联系电话</span></th>
									<th><span class="sp sty-borcolor-b" sort="true">销售进程</span></th>
									<c:choose>
										<c:when test="${resCustInfoDto.isShareRes eq '0' }">
											<th><span class="sp sty-borcolor-b">放弃类型</span></th>
											<th><span class="sp sty-borcolor-b">资源放弃原因</span></th>
											<th><span class="sp sty-borcolor-b">销售人员</span></th>
											<c:choose>
												<c:when test="${!empty queryFileds }">
													<th><span class="sp sty-borcolor-b">放弃人</span></th>
													<c:forEach items="${queryFileds }" var="field" varStatus="vs">
														<c:choose>
															<c:when test="${vs.count eq fn:length(queryFileds) }">
																<th>${field.fieldName }</th>
															</c:when>
															<c:otherwise>
																<th><span class="sp sty-borcolor-b">${field.fieldName }</span></th>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<th style="border-right:important #f5f6f7 1px solid">放弃人</th>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${!empty queryFileds }">
													<th><span class="sp sty-borcolor-b">放弃类型</span></th>
													<c:forEach items="${queryFileds }" var="field" varStatus="vs">
														<c:choose>
															<c:when test="${vs.count eq fn:length(queryFileds) }">
																<th>${field.fieldName }</th>
															</c:when>
															<c:otherwise>
																<th><span class="sp sty-borcolor-b">${field.fieldName }</span></th>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<th><span class="sp sty-borcolor-b" style="border-right: #f5f6f7 1px solid">放弃类型</span></th>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${!empty custs }">
										<c:forEach items="${custs }" var="cust" varStatus="vs">
											<tr class="${(vs.count % 2) ne 0 ? '' : 'sty-bgcolor-b' }">
												<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox" id="chk_${cust.resCustId }" /></div></td>
												<td style="width:50px;">
													<div class="overflow_hidden w50 link">
														<a href="###" id="cardInfo_${cust.resCustId }" custName="${empty cust.company ? cust.name : cust.company }" class="icon-cust-card" title="客户卡片"></a>
														<a href="javascript:;" id="getBack_${cust.resCustId }" class="icon-get-back" title="取回"></a>
													</div>
												</td>
												<td hidevalue="${cust.startDate }"><div class="overflow_hidden w70" title="${cust.startDate }">${cust.endDate }</div></td>
												<td><div class="overflow_hidden w100" title="${cust.name }">${cust.name }</div></td>
												<td><div class="overflow_hidden w120" title="${cust.company }">${cust.company }</div></td>
												<td><div phone="tel" class="overflow_hidden w80" title="${cust.mobilephone }">${cust.mobilephone }</div></td>
												<td><div class="overflow_hidden w100" title="${cust.saleProcessName }">${cust.saleProcessName }</div></td>
												<c:choose>
													<c:when test="${'11' eq cust.opreateType}"><c:set var="resOrCustName">客户-到期未签约</c:set></c:when>
													<c:when test="${'16' eq cust.opreateType}"><c:set var="resOrCustName">客户-到期未跟进</c:set></c:when>
													<c:when test="${'12' eq cust.opreateType}"><c:set var="resOrCustName">客户-主动放弃</c:set></c:when>
													<c:when test="${'4' eq cust.opreateType}"><c:set var="resOrCustName">资源-沟通失败</c:set></c:when>
													<c:when test="${'5' eq cust.opreateType}"><c:set var="resOrCustName">资源-信息错误</c:set></c:when>
												</c:choose>
												<td><div class="overflow_hidden w100" title="${empty cust.ownerName ? '' : resOrCustName }">${empty cust.ownerName ? '' : resOrCustName }</div></td>
												<c:if test="${resCustInfoDto.isShareRes eq '0' }">
													<td><div class="overflow_hidden w100" title="${cust.recuclingTypeDetails }">${cust.recuclingTypeDetails }</div></td>
													<td><div class="overflow_hidden w80" title="${cust.ownerName }">${cust.ownerName }</div></td>
													<td><div class="overflow_hidden w50" title="${empty cust.giveUpName ? 'system' : cust.giveUpName }">${empty cust.giveUpName ? 'system' : cust.giveUpName }</div></td>
												</c:if>
												<c:forEach items="${queryFileds }" var="field" varStatus="vs">
													<td><div class="overflow_hidden w50" title="${cust[field.fieldCode] }">${cust[field.fieldCode] }</div></td>
												</c:forEach>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="no_data_tr">
											<td colspan="${resCustInfoDto.isShareRes eq '0' ? fn:length(queryFileds)+11 : fn:length(queryFileds)+8 }">
												当前列表无数据!
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
					<div class="com-bot">
						<div class="com-page fl_r">${resCustInfoDto.page.pageStr }</div>
					</div>
				</div>
			</div>
		</div>
	</form>

<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#progressBtn").click(function(e){
			e.stopPropagation();
			appendtos("progress_bar","HIGH_SEA_PROGRESS");
			$(".progress_bar").show();
		});
		$(".query-button").on("click",function(e){
			e.stopPropagation();
			ajaxLoading("正在查询，请稍后") ;
			$("form").submit();
		});
	});
</script>
<script type="text/javascript" src="${ctx }/static/js/view/res/high_seas_list.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/progressBar/progress_detail.js${_v}"></script>

</body>
</html>
