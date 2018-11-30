<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/common/report_include.jsp" %>
    <title>团队今日数据</title>
    <script type="text/javascript" src="${ctx }/static/js/view/report/todayData.js"></script>
    <script type="text/javascript">
        $(function () {
			$("#export").click(function(){
				var groupIds = $("#groupIds").val();
				window.location.href=ctx+"/report/teamDayData/export?groupIds="+groupIds+"&expType=2";
			});
			
			$("#searchBtn").click(function(){
				$("#form").submit();
	        });	
			
			$(".ann-sale-plan li").click(function(){
				var rel = $(this).attr("rel");
				if(rel != null && rel != ''){
					window.location.href = rel;
				}
			});
			
			$("a[id^=view_change_]").click(function(){
				var ownerAcc = $(this).attr("id").split("_")[2];
				var custType = $(this).attr("cust_type");
				var changeType = $(this).attr("change_type");
				var groupIdStr = $(this).attr("groupIdStr");
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
				pubDivShowIframe('change_log_detail',ctx+'/report/contact?ownerAcc='+ownerAcc+"&custType="+custType+"&changeType="+changeType+"&groupIdStr="+groupIdStr,name,800,450);
			});
        });
    </script>
</head>
<body>
<div class="sales-statis-conta">
	<div class="year-sale-play">
		<ul class="ann-sale-plan clearfix" style="width:960px">
			<li class="li_first" rel="${ctx }/report/teamDayData" name="a">今日呼叫数据</li>
			<li class="select" name="b">今日资源联系结果</li>
			<li name="c" rel="${ctx }/report/teamDayData/custContact">今日意向客户联系结果</li>
			<li rel="${ctx }/report/teamDayData/deals" class="li_last" name="d">今日交易结果</li>
		</ul>
	</div>
	<form id="form" action="${ctx }/report/teamDayData/resContact" method="post">
		<div class="sales-statis-conta">
		    <div class="team-today clearfix" style="margin-bottom:10px;">
		        <div class="com-search-report fl_l" style="margin-top:0;min-height:0">
		            <label class="fl_l" for="" >部门名称：</label>
		            <input type="hidden" id="groupIds" name="groupIdStr" value="${groupIdStr }" />
		            <dl class="select fl_l" style="position:relative;z-index:555;width:145px;margin:0 auto;margin-right:10px;">
						<dt id="groupNameStr">-请选择-</dt>
						<dd>
							<ul id="tt1">
									
							</ul>
							<div class="sure-cancle clearfix" style="width:120px;margin:15px auto 0;padding-bottom:10px;">
								<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" onclick="selGroup()" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">确定</label></a>
								<a class="com-btnd bomp-btna com-btna-cancle fl_l" onclick="clearGroup()" href="javascript:;" style="margin-left:10px;"><label style="height:19px;line-height:19px;">清空</label></a>
							</div>
						</dd>
					</dl>
		        </div>
		        <a href="###" id="searchBtn" class="com-btna fl_l"><i class="min-search"></i><label class="lab-mar">查询</label></a>
		        <a href="###" id="export" class="com-btna fl_r"><i class="min-remove-resou"></i><label class="lab-mar">导出</label></a>
		    </div>
		    <div class="com-table hyx-mpe-table hyx-cm-table">
		        <table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-a">
		            <thead>
		            <tr class="sty-bgcolor-b">
		                <th><span class="sp sty-borcolor-b">小组成员</span></th>
		                <th><span class="sp sty-borcolor-b">所属小组</span></th>
		                <th><span class="sp sty-borcolor-b">计划联系数（个）</span></th>
		                <th><span class="sp sty-borcolor-b">实际联系数（个）</span></th>
		                <th><span class="sp sty-borcolor-b">添加资源数</span></th>
		                <th><span class="sp sty-borcolor-b">转签约（个）</span></th>
		                <th><span class="sp sty-borcolor-b">转意向（个）</span></th>
		                <th><span class="sp sty-borcolor-b">转公海（个）</span></th>
		                <th><span class="sp sty-borcolor-b">联系无变化（个）</span></th>
		                <th>计划未联系（个）</th>
		            </tr>
		            </thead>
		            <tbody>
			            <c:choose>
			            	<c:when test="${!empty resDtos }">
				            	<c:set var="resPlanNum" value="0" />
					            <c:set var="resTotalNum" value="0" />
					            <c:set var="resSignNum" value="0" />
					            <c:set var="resCustNum" value="0" />
					            <c:set var="resPoolNum" value="0" />
					            <c:set var="resNoNum" value="0" />
					            <c:set var="addResNum" value="0" />
					            <c:forEach items="${resDtos }" var="rd" varStatus="vs">
					            	<c:set var="resPlanNum" value="${resPlanNum+rd.planNum }" />
						            <c:set var="resTotalNum" value="${resTotalNum+rd.totalNum }" />
						            <c:set var="resSignNum" value="${resSignNum+rd.signNum }" />
						            <c:set var="resCustNum" value="${resCustNum+rd.custNum }" />
						            <c:set var="resPoolNum" value="${resPoolNum+rd.poolNum }" />
						            <c:set var="resNoNum" value="${resNoNum+rd.noNum }" />
						            <c:set var="resNoContactCount" value="${resNoContactCount+rd.noContactCount }" />
						            <c:set var="addResNum" value="${addResNum + rd.addResCount }" />
					            </c:forEach>
					            <tr>
					                <td colspan="2"><div class="overflow_hidden w100" title="">合计</div></td>
					                <td style="width:150px;"><div class="overflow_hidden w150 link" title="">${resPlanNum }</div></td>
					                <td><div class="overflow_hidden w100" title="">${resTotalNum }</div></td>
					                <td><div class="overflow_hidden w100" title="">${addResNum }</div></td>
					                <td>
					                	<div class="overflow_hidden w100" title="">
					                		<c:choose>
					                			<c:when test="${resSignNum gt 0 }">
					                				<a href="javascript:;" id="view_change_" groupIdStr="${groupIdStr }" cust_type="1" change_type="2">${resSignNum }</a>
					                			</c:when>
					                			<c:otherwise>${resSignNum }</c:otherwise>
					                		</c:choose>
					                	</div>
					                </td>
					                <td>
					                	<div class="overflow_hidden w100" title="">
					                		<c:choose>
					                			<c:when test="${resCustNum gt 0 }">
					                				<a href="javascript:;" id="view_change_" groupIdStr="${groupIdStr }" cust_type="1" change_type="1">${resCustNum }</a>
					                			</c:when>
					                			<c:otherwise>${resCustNum }</c:otherwise>
					                		</c:choose>
					                	</div>
					                </td>
					                <td><div class="overflow_hidden w100" title="">${resPoolNum }</div></td>
					                <td><div class="overflow_hidden w100" title="">${resNoNum }</div></td>
					                <td><div class="overflow_hidden w100" title="">${resNoContactCount }</div></td>
					            </tr>
					            <c:forEach items="${resDtos }" var="res" varStatus="vs">
					            	<tr class="${vs.count%2 eq 0 ? '' : 'sty-bgcolor-b' }">
						                <td><div class="overflow_hidden w90" title="">${res.userName }</div></td>
						                <td><div class="overflow_hidden w90" title="">${res.groupName }</div></td>
						                <td style="width:150px;"><div class="overflow_hidden w150 link" title="">${res.planNum }</div></td>
						                <td><div class="overflow_hidden w100" title="">${res.totalNum }</div></td>
						                <td><div class="overflow_hidden w100" title="">${res.addResCount }</div></td>
						                <td>
							                <div class="overflow_hidden w100" title="">
							                	<c:choose>
							                		<c:when test="${res.signNum gt 0 }">
							                			<a  href="javascript:;" id="view_change_${res.account }" cust_type="1" change_type="2">${res.signNum }</a>
							                		</c:when>
							                		<c:otherwise>${res.signNum }</c:otherwise>
							                	</c:choose>
							                </div>
						                </td>
						                <td>
						                	<div class="overflow_hidden w100" title="">
						                		<c:choose>
						                			<c:when test="${res.custNum gt 0 }">
						                				<a  href="javascript:;" id="view_change_${res.account }" cust_type="1" change_type="1">${res.custNum }</a>
						                			</c:when>
						                			<c:otherwise>${res.custNum }</c:otherwise>
						                		</c:choose>
						                	</div>
						                </td>
						                <td><div class="overflow_hidden w100" title="">${res.poolNum }</div></td>
						                <td><div class="overflow_hidden w100" title="">${res.noNum }</div></td>
						                <td><div class="overflow_hidden w100" title="">${res.noContactCount }</div></td>
						            </tr>
					            </c:forEach>
			            	</c:when>
			            	<c:otherwise>
			            		<tr>
			            			<td colspan="9">当前列表无数据！</td>
			            		</tr>
			            	</c:otherwise>
			            </c:choose>
		            </tbody>
		        </table>
			    <div class="com-bot">
					<div class="com-page fl_r">${contractDto.page.pageStr }</div>
				</div>
		    </div>
			
		    <div class="person-todata-warm">
		        <h2>温馨提示：</h2>
		        <p><span>1、“计费时长”为本地参考值，实际计费以服务器计费系统数据为准。</span></p>
		        <p><span>2、&nbsp;&nbsp;&nbsp;单次通话“计费时长”不满一分钟时，按一分钟计算。</span></p>
		        <p><span>3、“有效呼叫”指在电话接通<label for="">${timeLength}</label>秒后(此项可在“系统设置-销售管理”中进行设置)</span></p>
		        <p><span>4、“呼出时长”是将用户的呼出时长以“秒”为单位进行合计后再换算成“分钟”为单位，不足1分钟部分按1分钟计算。</span></p>
       			<p><span>5、“呼入时长”是将用户的呼入时长以“秒”为单位进行合计后再换算成“分钟”为单位，不足1分钟部分按1分钟计算。</span></p>
		    </div>
		</div>
	</form>
</div>
</body>
</html>
