<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
<%@ include file="/common/common-function/function.area.select.jsp"%>
<%@ include file="/common/include.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/view/tsm/res/queryAllResList.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/common.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/tsm/res/safePhone.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/tao/searchHeadSelect.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/common/page.js${_v}"></script>
<script type="text/javascript" src="${ctx }/static/js/view/tsm/res/rescommon.js${_v}"></script>
<script type="text/javascript">
 $(function(){
		if ($('#qKeyWordId').val() == "" || $('#qKeyWordId').val() == null) {
		} else {
			$('#qKeyWordId').next().text('');
		}
		var url = $('#base').val() + "/orgGroup/get_group_json"
		$("#deptTree").tree({
			formatter : function(node) {
				var nodeText = node.text;
				return nodeText;
			},
			url : url,
			checkbox : true,
			onBeforeLoad : function(node, param) {
				loading = true;
			},
			onLoadSuccess : function(node, data) {
				var pid;
				var accs = $('#deptId').val();
				if (accs != null && accs != '') {
					var arrs = accs.split(',');
					for (var i = 0; i < arrs.length; i++) {
						pid = arrs[i];
						pid = $('#deptTree').tree('find', pid);
						if(pid!=null && pid!=undefined){
						   $('#deptTree').tree('check', pid.target);
						}
					}
					$('#deptTree').tree("expandAll");
				}
				//移除图标
				$(".tree-icon").remove();
			},
			onExpand : function(node) {
				$(".tree-title").removeClass("tree-folder-open");
			},
			onCheck : function(node, checked) {
			},
			onSelect : function(node) {
			}
		});

		//选择部门
		$("#checkedId").click(function() {
			var nodes = $('#deptTree').tree('getChecked');
			if (nodes == null) {
				window.top.iDialogMsg("提示", '请选择部门！');
				return;
			}
			var mark = 0;
			var deptIds ="";
            //M-成员；G-分组
			$(nodes).each(function(index,obj){
					deptIds+=obj.id.substring(0,obj.id.length)+',';
					mark =mark+1;
			});
            if(mark==0){
            	window.top.iDialogMsg("提示", '请选择部门！');
            	return ;
            }else{
            	$('#deptId').val(deptIds);
            }
		});

		//清空
		$('#clearId').click(function(){
			var nodes = $('#deptTree').tree('getChecked');
			 $(nodes).each(function(index,obj){
            	$('#deptTree').tree('uncheck', obj.target);
            })
		  $('#deptId').val('');
		})
	})
</script>
<style>
.hyx-hsm-left dl dd span{width:165px;}
.hyx-hsm-left dl {text-align: left;}
.hyx-hsm-left dl dt{text-indent:25px;}
.hyx-hsm-left dl dd{text-indent:13px;}
	.btn {
		font-family: microsoft yahei;
		font-size: 12px;
		outline: none;
		cursor: pointer;
		box-sizing: border-box;
		position: relative;
	}
	
	.btn:disabled {
		cursor: not-allowed;
	}
	
	.btn-84px {
		height: 25px;
		line-height: 25px;
		width: 84px;
		background-color: #fff;
		border: 1px solid #bbb;
		border-radius: 4px;
		margin-right: 15px;
		padding-left: 17px;
	}
	
	.btn-84px .icon {
		position: absolute;
		left: 2px;
		top: 4px;
	}
	
	.btn-84px:hover {
		background-color: #1979ca;
		color: #fff;
	}
	
	.btn-84px:hover .icon {
		background-image:url(${ctx}/static/images/all-sprite-white.png);
	}
	
	.item-list-head {
		background-color: #e1e1e1;
	}
</style>
<style type="text/css" rel="stylesheet">
<c:forEach items="${sorts}" var="sort">
	.com-table>table>tbody>tr>td:nth-child(${sort}),.com-table>table>thead>tr>th:nth-child(${sort}){display: none;}
</c:forEach>
</style>
</head>
<body>
	<form method="post" action="${ctx }/res/resmanage/queryComResList" id="myForm">
		<input type="hidden" id="deptId" value="${deptId}" name="deptId">
		<input type="hidden" id="base" value="${ctx}">
		<input type="hidden" id="groupId" name="groupId" value="${groupId}">
		<input type="hidden" id="groupName" name="groupName" value="${groupName}">
		<input type="hidden" id="level" name="level" value="${level}">
		<%-- <input type="hidden" name="shareGroupIds" id="shareGroupIds" value="${shareGroupIds}" /> --%>
		<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
		<input type="hidden" id="isState" value="${shiroUser.isState}" name="isState">
		<div class="hyx-dfpzy-reso hyx-layout">
             <div class="hyx-hsm-left hyx-layout-side" style="overflow-y: auto">
             	<ul>
					<li class="item-list-head">资源分组</li>
				</ul>
				<ul class="resource-group-tree" data-url="${ctx}/res/group/get_res_group_json">
					<!-- 部门树 -->
				</ul>
			</div>
			<div class="hyx-hsm-right hyx-layout-content" style="border: none;">
				<div class="com-search hyx-cm-search">
					<div class="com-search-box fl_l">
						<div class="search clearfix" >
						   <div class="select_outerDiv fl_l">
						   <span class="icon_down_5px"></span>
				   			<select class="fl_l search_head_select"  name="queryType">
					   		<c:if test="${ !empty tList && fn:length(tList) > 0 }">
								<c:forEach items="${tList}" var="defined" varStatus="vs">
									 <option value="${defined.searchCode }" ${item.queryType eq defined.searchCode? 'selected' :''}>${defined.searchName }</option>
						   		</c:forEach>
					   		</c:if>
					   		</select>
						   	</div>
							<input class="input-query fl_l" id="qKeyWordId" type="text" name="qKeyWord" value="${resCustDto.qKeyWord }" /><label class="hide-span"></label>
						</div>
						<div class="list-box">
								<!-- 创建日期 -->
								<input type="hidden" name="startDate" id="s_pstartDate" value="${resCustDto.startDate }" />
								<input type="hidden" name="endDate" id="s_pendDate" value="${resCustDto.endDate }" />
								<input type="hidden" name="oDateType" id="oDateType" value="${resCustDto.oDateType }" />
								<input type="hidden" name="qStatus" id="qStatus" value="${resCustDto.qStatus }" />
								<c:set var="oDateName" value="创建日期" />
								<c:set var="qStatusName" value="资源状态" />
								<c:choose>
									<c:when test="${resCustDto.oDateType eq 1 }">
										<c:set var="oDateName" value="当天" />
									</c:when>
									<c:when test="${resCustDto.oDateType eq 2 }">
										<c:set var="oDateName" value="本周" />
									</c:when>
									<c:when test="${resCustDto.oDateType eq 3 }">
										<c:set var="oDateName" value="本月" />
									</c:when>
									<c:when test="${resCustDto.oDateType eq 4 }">
										<c:set var="oDateName" value="半年" />
									</c:when>
						   		<c:when test="${resCustDto.oDateType eq 5 }">
						   			<fmt:parseDate var="psd" value="${resCustDto.startDate }" pattern="yyyy-MM-dd" />
						   			<fmt:parseDate var="ped" value="${resCustDto.endDate }" pattern="yyyy-MM-dd" />
						   			<c:set var="oDateName">
						   				<fmt:formatDate value="${psd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ped }" pattern="yy.MM.dd"/>
						   			</c:set>
						   		</c:when>
								</c:choose>
								<dl class="select pos${empty mutilSearchCodeSortMap['startDate'] ? '0' : mutilSearchCodeSortMap['startDate']}" >								<dt>${oDateName }</dt>
								<dd>
									<ul>
										<li><a href="###" onclick="setPdate(0)"
											title="创建日期">创建日期</a></li>
										<li><a href="###" onclick="setPdate(1)"
											title="当天">当天</a></li>
										<li><a href="###" onclick="setPdate(2)"
											title="本周">本周</a></li>
										<li><a href="###" onclick="setPdate(3)"
											title="本月">本月</a></li>
										<li><a href="###" onclick="setPdate(4)"
											title="半年">半年</a></li>
										<li><a href="###" id="pDate" title="自定义"
											class="diy date-range">自定义</a></li>
									</ul>
								</dd>
							</dl>
								<c:choose>
								   <c:when test="${ resCustDto.qStatus eq 1}">
								       <c:set var="qStatusName" value="待分配资源" />
								   </c:when>
								   <c:when test="${ resCustDto.qStatus eq 2}">
								        <c:set var="qStatusName" value="已分配资源" />
								   </c:when>
								   <c:when test="${ resCustDto.qStatus eq 3}">
								         <c:set var="qStatusName" value="已分配未联系资源" />
								   </c:when>
								   <c:when test="${ resCustDto.qStatus eq 4}">
								       <c:set var="qStatusName" value="已联系资源" />
								   </c:when>
								   <c:when test="${ resCustDto.qStatus eq 5}">
								        <c:set var="qStatusName" value="意向客户" />
								   </c:when>
								   <c:when test="${ resCustDto.qStatus eq 6}">
								       <c:set var="qStatusName" value="签约客户" />
								   </c:when>
								   <c:when test="${ resCustDto.qStatus eq 7}">
								        <c:set var="qStatusName" value="沉默客户" />
								   </c:when>
								   <c:when test="${ resCustDto.qStatus eq 8}">
								        <c:set var="qStatusName" value="流失客户" />
								   </c:when>
								   <c:when test="${ resCustDto.qStatus eq 9}">
								        <c:set var="qStatusName" value="公海" />
								   </c:when>
								</c:choose>

								 <dl class="select pos${empty mutilSearchCodeSortMap['companyTrade'] ? '0' : mutilSearchCodeSortMap['companyTrade']}" data-input="[name='companyTrade']">
									<dt>所属行业</dt>
										<dd>
											<ul>
												<li><a href="###" onclick="$('#s_companyTrade').val('');" title="所属行业">所属行业</a></li>
												<c:forEach items="${companyTrades }" var="ct">
													<li><a href="###" data-value="${ct.optionlistId}" >${ct.optionName }</a></li>
												</c:forEach>
											</ul>
										</dd>
										 <input name="companyTrade" type="hidden" value="" id="s_companyTrade">
								   </dl>

								<dl class="select pos${empty mutilSearchCodeSortMap['qStatus'] ? '0' : mutilSearchCodeSortMap['qStatus']}">
									<dt>${qStatusName}</dt>
									<dd>
										<ul>
										    <li><a href="###" title="资源状态" onclick="setStatus(0)">资源状态</a></li>
											<li><a href="###" title="待分配资源" onclick="setStatus(1)">待分配资源</a></li>
											<li><a href="###" title="已分配资源" onclick="setStatus(2)">已分配资源</a></li>
											<li><a href="###" title="已分配未联系资源" onclick="setStatus(3)">已分配未联系</a></li>
											<li><a href="###" title="已联系资源" onclick="setStatus(4)">已分配已联系</a></li>
											<li><a href="###" title="意向客户" onclick="setStatus(5)">意向客户</a></li>
											<li><a href="###" title="签约客户" onclick="setStatus(6)">签约客户</a></li>
											<li><a href="###" title="沉默客户" onclick="setStatus(7)">沉默客户</a></li>
											<li><a href="###" title="流失客户" onclick="setStatus(8)">流失客户</a></li>
											<li><a href="###" title="公海" onclick="setStatus(9)">公海客户</a></li>
										</ul>
									</dd>
								</dl>
								<div class="reso-sub-dep fl_l pos${empty mutilSearchCodeSortMap['deptId'] ? '0' : mutilSearchCodeSortMap['deptId']}">
										<span class="sub-dep-inpu">资源录入部门</span>
										<div class="manage-drop">
											<div class="sub-dep-ul"  data-type="search-tree" style="width:190px;">
												<ul id="deptTree" ></ul>
											</div>
											<div class="sure-cancle clearfix" style="width: 120px">
												<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" href="###" id="checkedId"><label>确定</label></a>
												<a class="com-btnd bomp-btna fl_l" id="clearId" href="###"><label>清空</label></a>
											</div>
										</div>
								</div>
								
								<dl class="select pos${empty mutilSearchCodeSortMap['area'] ? '0' : mutilSearchCodeSortMap['area']}" data-select-type="area">
									<dt>所在地区</dt>
								</dl>
								
								<c:forEach items="${definedSearchCodes }" var="defindeCode">
							  	<c:choose>
							  		<c:when test="${defindeCode.dataType == 2}"> <!-- 日期型 -->
							  			<dl class="select pos${empty mutilSearchCodeSortMap[defindeCode.searchCode] ? '0' : mutilSearchCodeSortMap[defindeCode.searchCode]}">
								  		<c:forEach items="${defindeCode.childList}" var="childCode">
								  			<input type="hidden" name="${childCode.name }" data-type="${childCode.order eq 0 ? 'start':'end' }"/>
								  		</c:forEach>
										<dt>${defindeCode.searchName}</dt>
										<dd>
											<ul>
												<li><a href="javascript:;" data-type="0">${defindeCode.searchName}</a></li>
												<li><a href="javascript:;" data-type="1">当天</a></li>
												<li><a href="javascript:;" data-type="2">本周</a></li>
												<li><a href="javascript:;" data-type="3">本月</a></li>
												<li><a href="javascript:;" data-type="4">半年</a></li>
												<li><a  class="date-range custom-picker" >自定义</a></li>
											</ul>
										</dd>
									</dl>
							  		</c:when>
							  		<c:when test="${defindeCode.dataType == 3}" > <!-- 单选 -->
							  			<dl class="select pos${empty mutilSearchCodeSortMap[defindeCode.searchCode] ? '0' : mutilSearchCodeSortMap[defindeCode.searchCode]}" data-input="[name='${defindeCode.searchCode }']">
											<dt>${defindeCode.searchName}</dt>
											<dd>
												<ul>
													<li><a href="###" onclick="$('#${defindeCode.searchCode }').val('');" title="${defindeCode.searchName}">${defindeCode.searchName}</a></li>
													<c:forEach items="${defindeCode.childList }"  var="childCode">
														<li><a href="###" data-value="${childCode.value}" title="${childCode.name }">${childCode.name}</a></li>
													</c:forEach>
												</ul>
											</dd>
											 <input name="${defindeCode.searchCode }" type="hidden" id="${defindeCode.searchCode }">
									   </dl>
							  		</c:when>
							  		<c:when test="${defindeCode.dataType == 4}"> <!-- 多选 -->
							  			<dl class="select pos${empty mutilSearchCodeSortMap[defindeCode.searchCode] ? '0' : mutilSearchCodeSortMap[defindeCode.searchCode]}" data-input="[name='${defindeCode.searchCode }']" data-multi="true">
											<dt>${defindeCode.searchName}</dt>
											<dd>
												<ul>
													<c:forEach items="${defindeCode.childList }" var="childCode">
														<li><a href="###" data-value="${childCode.value}" title="${childCode.name }">${childCode.name}</a></li>
													</c:forEach>
												</ul>
											</dd>
											 <input name="${defindeCode.searchCode }" type="hidden" >
									   </dl>
							  		</c:when>
							  	</c:choose>

							  </c:forEach>
						</div>
					</div>
					<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
					<input class="query-button fl_r" type="button" value="高级查询"  id="superQuery" data-url="${ctx }/highSearch/toPage?module=11" data-title="高级查询" data-param="&groupId=${groupId }&shareGroupIds=${shareGroupIds }" />
					<c:if test="${mutilSearchCodeSortMap.keySet().size() gt 5 }"><a href="javascript:;" class="more"><i></i>更多</a></c:if>
				</div>
				<div class="com-btnlist hyx-cm-btnlist fl_l">
					<!-- <a data-authority="resAll_importRes" href="###" class="com-btna demoBtn_c fl_l" id="importId"><i class="min-into-resou"></i><label class="lab-mar">导入资源</label></a> -->
					<button type="button" data-authority="resUnAssign_importRes"  id="importId" class="fl_l btn btn-84px" disabled="disabled"><i class="min-into-resou icon"></i>导入资源</button>
					<a data-authority="resAll_changeResGroup" href="###" class="com-btna reso-manag-a  fl_l" id="changeResId"><i class="min-reass"></i><label class="lab-mar">变更分组</label></a>
					<a data-authority="resAll_deleteRes" href="###" class="com-btna reso-manag-c fl_l" id="deleteResId"><i class="min-dele"></i><label class="lab-mar">删除资源</label></a>
				</div>
				<div class="hid clearfix" style="display:block;">
					<div class="com-table com-mta test-table fl_l">
						<!-- <p class="reso-math">目前共有资源数：<span>3</span>条</p> -->
						<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius ajax-table">
							<thead>
								<tr role="head"  class="sty-bgcolor-b">
									<th><span class="sp sty-borcolor-b skin-minimal">
									     <input type="checkbox" id="checkAll" class="check" /></span></th>
									<th><span class="sp sty-borcolor-b">操作</span></th>
									<th><span class="sp sty-borcolor-b">${shiroUser.isState eq 0 ? '单位名称' : '客户名称'}</span></th>
									<th><span class="sp sty-borcolor-b">${shiroUser.isState eq 0 ? '客户姓名' : '联系人'}</span></th>
									<th><span class="sp sty-borcolor-b">常用电话</th>
									<th><span class="sp sty-borcolor-b">备用电话</span></th>
									<th><span class="sp sty-borcolor-b">资源状态</span></th>
									<th><span class="sp sty-borcolor-b">资源分组</span></th>
									<th><span class="sp sty-borcolor-b">所有者</span></th>
									<th><span class="sp sty-borcolor-b">资源录入部门</span></th>
									<th  hidesort="true"><span class="sp sty-borcolor-b "  sort="true">创建时间</span></th>
									<th><span class="sp sty-borcolor-b">联系电话</span></th>
									<th><span class="sp sty-borcolor-b">所在地区</span></th>
							   		<th><span class="sp sty-borcolor-b">所属行业</span></th>
									<th><span class="sp sty-borcolor-b">${definedNameMap["defined1"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined2"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined3"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined4"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined5"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined6"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined7"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined8"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined9"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined10"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined11"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined12"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined13"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined14"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined15"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined16"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined17"]}</span></th>
							   		<th><span class="sp sty-borcolor-b">${definedNameMap["defined18"]}</span></th>
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
				<p class="reminder" style="font-size: 14px;"><span class="com-red">温馨提示：</span>用户可以查看资源的录入部门是所属管辖权限范围内的数据</p>
			</div>
		</div>
	</form>
</body>
</html>
