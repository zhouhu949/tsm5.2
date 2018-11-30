<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>通信管理-通信时长分配日志</title>
    <style type="text/css"></style>
    <%@ include file="/common/include.jsp" %>
    <script type="text/javascript" src="${ctx }/static/js/view/bill/member_pakage_list.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
</head>
<body>
	<form id="cform" action="${ctx }/pakage/allocation/findLogCommuOptorList" method="post">
		<div class="hyx-ctr">
		   <div class="com-search hyx-cfu-search">
		      <div class="com-search-box fl_l">
			   <p class="search clearfix"><input class="input-query fl_l" type="text" name="queryText" value="${optorDto.queryText }" placeholder="操作人"/></p>
			   <div class="list-box">
				    <c:set var="dDateName" value="操作时间" />
				    <c:choose>
				   		<c:when test="${optorDto.dDateType eq 1 }"><c:set var="dDateName" value="当天" /></c:when>
				   		<c:when test="${optorDto.dDateType eq 2 }"><c:set var="dDateName" value="本周" /></c:when>
				   		<c:when test="${optorDto.dDateType eq 3 }"><c:set var="dDateName" value="本月" /></c:when>
				   		<c:when test="${optorDto.dDateType eq 4 }"><c:set var="dDateName" value="半年" /></c:when>
				   		<c:when test="${optorDto.dDateType eq 5 }">
				   			<fmt:parseDate var="sd" value="${optorDto.startDate }" pattern="yyyy-MM-dd" />
					   		<fmt:parseDate var="ed" value="${optorDto.endDate }" pattern="yyyy-MM-dd" />
				   			<c:set var="dDateName">
				   				<fmt:formatDate value="${sd }" pattern="yy.MM.dd"/>/<fmt:formatDate value="${ed }" pattern="yy.MM.dd"/>
				   			</c:set>
				   		</c:when>
				    </c:choose>
				    <input type="hidden" name="startDate" id="s_startDate" value="${optorDto.startDate }" />
				    <input type="hidden" name="endDate" id="s_endDate" value="${optorDto.endDate }" />
				    <input type="hidden" name="dDateType" id="dDateType" value="${optorDto.dDateType }" />
				    <dl class="select">
						<dt>${dDateName }</dt>
						<dd>
							<ul>
								<li><a href="javascript:;" onclick="setDdate(0)" title="操作时间">操作时间</a></li>
								<li><a href="javascript:;" onclick="setDdate(1)" title="当天">当天</a></li>
								<li><a href="javascript:;" onclick="setDdate(2)" title="本周">本周</a></li>
								<li><a href="javascript:;" onclick="setDdate(3)" title="本月">本月</a></li>
								<li><a href="javascript:;" onclick="setDdate(4)" title="半年">半年</a></li>
								<li><a href="javascript:;" title="自定义" class="diy date-range" id="dDate">自定义</a></li>
							</ul>
						</dd>
					</dl>
				   <input type="hidden" name="operateType" id="operateType" value="${optorDto.operateType }" />
				   <c:set var="osName" value="操作类型" />
				   <c:choose>
				   		<c:when test="${optorDto.operateType eq 1 }"><c:set var="osName" value="时长分配" /></c:when>
				   		<c:when test="${optorDto.operateType eq 2 }"><c:set var="osName" value="时长回收" /></c:when>
				   		<c:when test="${optorDto.operateType eq 6 }"><c:set var="osName" value="定时分配" /></c:when>
				   		<c:when test="${optorDto.operateType eq 7 }"><c:set var="osName" value="短信分配" /></c:when>
				   		<c:when test="${optorDto.operateType eq 8 }"><c:set var="osName" value="短信回收" /></c:when>
				   </c:choose>
				   <dl class="select">
						<dt>${osName }</dt>
						<dd>
							<ul>
								<li><a href="javascript:;" onclick="$('#operateType').val('')" title="操作类型">操作类型</a></li>
								<li><a href="javascript:;" onclick="$('#operateType').val('1')" title="时长分配">时长分配</a></li>
								<li><a href="javascript:;" onclick="$('#operateType').val('2')" title="时长回收">时长回收</a></li>
								<li><a href="javascript:;" onclick="$('#operateType').val('6')" title="定时分配">定时分配</a></li>
								<li><a href="javascript:;" onclick="$('#operateType').val('7')" title="短信分配">短信分配</a></li>
								<li><a href="javascript:;" onclick="$('#operateType').val('8')" title="短信回收">短信回收</a></li>
							</ul>
						</dd>
				   </dl>
				  <input type="hidden" id="accountsStr" name="accountsStr" value="${optorDto.accountsStr }" />
				  <div class="reso-sub-dep fl_l">
						<input class="owner-sour" id="userNames" readonly="readonly" type="text" value="帐号">
						<div class="manage-owner-sour">
							<div class="sub-dep-ul">
								<ul id="tt1">

						        </ul>
				    		</div>
						    <div class="sure-cancle clearfix" style="width:120px">
								<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" onclick="seleTree()" href="javascript:;"><label>确定</label></a>
								<a class="com-btnd bomp-btna fl_l" onclick="unCheckTree()" href="javascript:;"><label>清空</label></a>
							</div>
						</div>
					</div>

				</div>
			  </div>
				<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query"/>
		   </div>
		   <div class="com-table com-mta hyx-cla-table fl_l" style="display:block;margin-top:5px !important;">
				<table id="t1" width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
					<thead>
						<tr role="head" class="sty-bgcolor-b">
							<th><span class="sp sty-borcolor-b">操作时间</span></th>
							<th><span class="sp sty-borcolor-b">帐号</span></th>
							<th><span class="sp sty-borcolor-b">用户姓名</span></th>
							<th><span class="sp sty-borcolor-b">操作类型</span></th>
							<th><span class="sp sty-borcolor-b" sort="true">操作时长</span></th>
							<th><span class="sp sty-borcolor-b" sort="true">操作前剩余时长（分钟）</span></th>
							<th><span class="sp sty-borcolor-b" sort="true">操作后剩余时长（分钟）</span></th>
							<th>操作人</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${!empty optorDtos }">
								<c:forEach items="${optorDtos }" var="optor" varStatus="vs">
									<tr class="${vs.count%2 eq 0 ? 'sty-bgcolor-b' : '' }">
										<td><div class="overflow_hidden w130" title="<fmt:formatDate value="${optor.operateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${optor.operateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></div></td>
										<td><div class="overflow_hidden w70" title="${optor.allocationAcc }">${optor.allocationAcc }</div></td>
										<td><div class="overflow_hidden w70" title="${optor.userName }">${optor.userName }</div></td>
										<td>
											<div class="overflow_hidden w120">
												${optor.operateType==1?'时长分配':''}
	                                        	${optor.operateType==2?'时长回收':''}
	                                        	${optor.operateType==3?'系统分配':''}
	                                        	${optor.operateType==6?'定时分配':''}
	                                        	${optor.operateType==7?'短信分配':''}
	                                        	${optor.operateType==8?'短信回收':''}
											</div>
										</td>
										<td><div class="overflow_hidden w120" title="${optor.operateTimelength}">${optor.operateTimelength}</div></td>
										<td><div class="overflow_hidden w100" title="${optor.firstOperateTimelength}">${optor.firstOperateTimelength}</div></td>
										<td><div class="overflow_hidden w100" title="${optor.afterOperateTimelength}">${optor.afterOperateTimelength}</div></td>
										<td><div class="overflow_hidden w120" title="${optor.operateNname}">${optor.operateNname}</div></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr class="no_data_tr"><td>当前列表无数据！</td></tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<div class="com-bot" >
				${optorDto.page.pageStr }
			</div>
		</div>
	</form>
<script type="text/x-handlebars-template" id="template">
  {{#each list}}
	<tr class="{{even_odd @index}}">
		<td><div class="overflow_hidden w130" title="{{formatDate operateTime 'YYYY-MM-dd HH:mm:ss'}}">{{formatDate operateTime 'YYYY-MM-dd HH:mm:ss'}}</div></td>
		<td><div class="overflow_hidden w70" title="{{allocationAcc }}">{{allocationAcc }}</div></td>
		<td><div class="overflow_hidden w70" title="{{optor.userName }}">{{userName }}</div></td>
		<td>
			<div class="overflow_hidden w120">
                {{operateStateStr operateType}}
			</div>
		</td>
		<td><div class="overflow_hidden w120" title="{{operateTimelength}}">{{operateTimelength}}</div></td>
		<td><div class="overflow_hidden w100" title="{{firstOperateTimelength}}">{{firstOperateTimelength}}</div></td>
		<td><div class="overflow_hidden w100" title="{{afterOperateTimelength}}">{{afterOperateTimelength}}</div></td>
		<td><div class="overflow_hidden w120" title="{{operateNname}}">{{operateNname}}</div></td>
    </tr>
 {{/each}}
</script>
</body>
</html>
