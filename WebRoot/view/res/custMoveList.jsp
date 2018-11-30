<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tree_top_part.css${_v}"/>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/page.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/ajax_common.js${_v}"></script>
<script type="text/javascript">
	var isState = ${shiroUser.isState};	
</script>
<script type="text/javascript" src="${ctx }/static/js/view/res/custMoveList.js${_v}"></script>
</head>
<body>
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<form id="queryForm" action="${ctx }/res/cust/custMoveList" method="post">
		<div class="hyx-cfu-left hyx-ctr">
		   <div class="com-search hyx-cfu-search">
		      <div class="com-search-box fl_l">
			   <div class="search clearfix" >
					   <div class="select_outerDiv fl_l">
						    <span class="icon_down_5px"></span>
					   		<select name="queryType" class="fl_l search_head_select">
					   			<c:choose>
					   				<c:when test="${shiroUser.isState == 1 }">
					   					<option value="1">客户名称</option>
							   			<option value="2">联系人</option>
					   				</c:when>
					   				<c:otherwise>
					   					<option value="4">单位名称</option>
								   		<option value="1">客户姓名</option>
					   				</c:otherwise>
					   			</c:choose>
				   				<option value="3">联系电话</option>
						   	</select>
						</div>
			   		<input class="input-query fl_l" name="queryText" type="text" value="" /><label class="hide-span"></label>
			   </div>
			   <input type="hidden" name="custTypeId" id="s_custTypeId" value="" />
			   <div class="list-box">
				   <input type="hidden" id="s_transferType" name="transferType" value="" />
				   <dl class="select">
						<dt>转移类型</dt>
						<dd>
							<ul>
								<li><a href="javascript:;" onclick="$('#s_transferType').val('');" title="转移类型">转移类型</a></li>
								<li><a href="javascript:;" onclick="$('#s_transferType').val('1');" title="转入">转入</a></li>
								<li><a href="javascript:;" onclick="$('#s_transferType').val('2');" title="转出">转出</a></li>
							</ul>
						</dd>
				   </dl>
				   <input type="hidden" name="type" id="s_type" value="" />
				   <dl class="select">
						<dt>转出类型</dt>
						<dd>
							<ul>
								<li><a href="javascript:;" onclick="$('#s_type').val('');" title="转出类型">转出类型</a></li>
								<li><a href="javascript:;" onclick="$('#s_type').val('14');" title="主动转移">主动转移</a></li>
							</ul>
						</dd>
				   </dl>
				   <input type="hidden" name="startDate" id="s_startDate" value="" />
				   <input type="hidden" name="endDate" id="s_endDate" value=""/>
				   <input type="hidden" name="nDateType" id="nDateType" value="1" />
				   <dl class="select">
						<dt>当天</dt>
						<dd>
							<ul>
								<li><a href="javascript:;" onclick="setNDate(0)" title="转移时间">转移时间</a></li>
								<li><a href="javascript:;" onclick="setNDate(1)" title="当天">当天</a></li>
								<li><a href="javascript:;" onclick="setNDate(2)" title="本周">本周</a></li>
								<li><a href="javascript:;" onclick="setNDate(3)" title="本月">本月</a></li>
								<li><a href="javascript:;" onclick="setNDate(4)" title="半年">半年</a></li>
								<li><a href="javascript:;" id="nDate" title="自定义" class="date-range diy">自定义</a></li>
							</ul>
						</dd>
					</dl>
					
					<c:if test="${shiroUser.issys eq 1 }">
							<input type="hidden" name="ownerAccsStr" value="" id="ownerAccsStr" />
							<input type="hidden" name="osType" value="1" id="osType" />
							<div class="reso-sub-dep fl_l">
								<input style="border-right: 2px solid #e1e1e1;" id="ownerNameStr" readonly="readonly" class="sub-dep-inpu" type="text" value="所有者">
								<div class="manage-drop"  style="height:370px;"> 
									<div class="shows-radio-boxs"></div>
									<ul class="shows-allorme-boxs">
										<li><input type="radio"  value="1"  name="searchOwnerType" checked>查看全部</li>
										<li><input type="radio" name="searchOwnerType"  value="2">查看自己</li>
									</ul>
									<div class="sub-dep-ul" style="width:190px;" data-type="search-tree"> 
										<ul id="tt1">
								            
						       			</ul>
						    		</div>
								    <div class="sure-cancle clearfix" style="width:120px">
										<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" onclick="seleTree()" href="javascript:;"><label>确定</label></a>
										<a class="com-btnd bomp-btna fl_l" id="close02" onclick="unCheckTree()" href="javascript:;"><label>清空</label></a>
									</div>
								</div>
							</div>
					</c:if>
					
					<input type="hidden" name="saleProcessId" id="s_saleProcessId" value="" />
					<dl class="select">
						<dt>销售进程</dt>
						<dd>
							<ul>
								<li><a href="javascript:;" onclick="$('#s_saleProcessId').val('');" title="销售进程">销售进程</a></li>
								<c:forEach var="sop" items="${options }">
									<li><a href="javascript:;" onclick="$('#s_saleProcessId').val('${sop.optionlistId}');" title="${sop.optionName }">${sop.optionName }</a></li>
								</c:forEach>
							</ul>
						</dd>
					</dl>
					<div class="list-hid fl_l">
						<dl class="select">
						<dt>客户类型</dt>
						<dd>
							<ul>
								<li><a href="javascript:;" onclick="$('#s_custTypeId').val('');"  title="客户类型">客户类型</a></li>
								<c:forEach var="ct" items="${typeOptions }">
									<li><a href="javascript:;" onclick="$('#s_custTypeId').val('${ct.optionlistId}');" title="${ct.optionName }">${ct.optionName }</a></li>
								</c:forEach>
							</ul>
						</dd>
				   </dl>
					</div>
				</div>
			</div>
				<input class="query-button fl_r" type="button" value="查&nbsp;询" id="searchBtn" />
				<c:if test="${shiroUser.issys eq 1 }"><a href="javascript:;" class="more"><i></i>更多</a></c:if>
		    </div>
			<div class="com-table com-mta hyx-cla-table" style="display:block;margin-top:20px !important;">
				<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
					<thead>
						<tr role="head" class="sty-bgcolor-b">
							<th><span class="sp sty-borcolor-b">操作</span></th> 
							<th hidesort="true"><span class="sp sty-borcolor-b" sort="true">转移日期</span></th>
							<th><span class="sp sty-borcolor-b">${shiroUser.isState eq 1 ? '客户名称' : '客户姓名' }</span></th>
							<c:if test="${shiroUser.isState ne 1 }">
								<th><span class="sp sty-borcolor-b">单位名称</span></th>
							</c:if>
							<th><span class="sp sty-borcolor-b">客户类型</span></th>
							<th><span class="sp sty-borcolor-b">联系电话</span></th>
							<th><span class="sp sty-borcolor-b" sort="true">销售进程</span></th>
							<th><span class="sp sty-borcolor-b">转移类型</span></th>
							<th><span class="sp sty-borcolor-b">当前所有人</span></th>
							<th><span class="sp sty-borcolor-b">历史所有人</span></th>
							<th><span class="sp sty-borcolor-b">转出类型</span></th>
							<th><span class="sp sty-borcolor-b">操作人</span></th>
							<th>交接原因</th>
						</tr>
					</thead>
					<tbody>              
						<tr class="no_data_tr"><td></td></tr>
						<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
						<tr class="no_data_tr"><td></td></tr>
						<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
						<tr class="no_data_tr"><td></td></tr>
						<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
						<tr class="no_data_tr"><td></td></tr>
						<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
						<tr class="no_data_tr"><td></td></tr>
						<tr class="no_data_tr sty-bgcolor-b"><td></td></tr>
					</tbody>
				</table>
				
				<div class="tip_search_data" >
					<div class="tip_search_text">
						<span><img alt="加载中" src="${ctx }/static/images/hua_loading.gif" /></span>
						<span>数据加载中…</span>
					</div>
					<div class="tip_search_error"></div>
				</div>
			</div>
			<div class="com-bot" >
				<div class="com-page fl_r">
					<div class="page" id="new_page"></div>
					<div class="clr_both"></div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
