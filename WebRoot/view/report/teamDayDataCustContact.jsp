<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@ include file="/common/report_include.jsp" %>
    <title>团队今日数据</title>
    <script type="text/javascript" src="${ctx }/static/js/view/report/todayData.js"></script>
    <script type="text/javascript">
        $(function () {
			$("#export").click(function(){
				var groupIds = $("#groupIds").val();
				window.location.href=ctx+"/report/teamDayData/export?groupIds="+groupIds+"&expType=3";
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
			<li rel="${ctx }/report/teamDayData/resContact" name="b">今日资源联系结果</li>
			<li class="select" name="c" >今日意向客户联系结果</li>
			<li rel="${ctx }/report/teamDayData/deals" class="li_last" name="d">今日交易结果</li>
		</ul>
	</div>
	<form id="form" action="${ctx }/report/teamDayData/custContact" method="post">
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
		                <th><span class="sp sty-borcolor-b">转签约（个）</span></th>
		                <th><span class="sp sty-borcolor-b">意向变更（个）</span></th>
		                <th><span class="sp sty-borcolor-b">转公海（个）</span></th>
		                <th><span class="sp sty-borcolor-b">联系无变化（个）</span></th>
		                <th>计划未联系（个）</th>
		            </tr>
		            </thead>
		            <tbody>
		            <c:choose>
		            	<c:when test="${!empty intDtos }">
		            		<c:set var="intPlanNum" value="0" />
				            <c:set var="intTotalNum" value="0" />
				            <c:set var="intSignNum" value="0" />
				            <c:set var="intCustNum" value="0" />
				            <c:set var="intPoolNum" value="0" />
				            <c:set var="intNoNum" value="0" />
				            <c:set var="intNoContactCount" value="0" />
				            <c:forEach items="${intDtos }" var="idt" varStatus="vs">
				            	<c:set var="intPlanNum" value="${intPlanNum+idt.planNum }" />
					            <c:set var="intTotalNum" value="${intTotalNum+idt.totalNum }" />
					            <c:set var="intSignNum" value="${intSignNum+idt.signNum }" />
					            <c:set var="intCustNum" value="${intCustNum+idt.custNum }" />
					            <c:set var="intPoolNum" value="${intPoolNum+idt.poolNum }" />
					            <c:set var="intNoNum" value="${intNoNum+idt.noNum }" />
					            <c:set var="intNoContactCount" value="${intNoContactCount+idt.noContactCount }" />
				            </c:forEach>
				            <tr>
				                <td colspan="2"><div class="overflow_hidden w100" title="">合计</div></td>
				                <td style="width:150px;"><div class="overflow_hidden w150 link" title="">${intPlanNum }</div></td>
				                <td><div class="overflow_hidden w100" title="">${intTotalNum }</div></td>
				                <td>
				                	<div class="overflow_hidden w100" title="">
				                		<c:choose>
				                			<c:when test="${intSignNum gt 0 }">
				                				<a href="javascript:;" id="view_change_" groupIdStr="${intSignNum }" cust_type="2" change_type="2">${intSignNum }</a>
				                			</c:when>
				                			<c:otherwise>${intSignNum }</c:otherwise>
				                		</c:choose>
				                	</div>
				                </td>
				                <td>
				                	<div class="overflow_hidden w100" title="">
				                		<c:choose>
				                			<c:when test="${intCustNum gt 0 }">
				                				<a href="javascript:;" id="view_change_" groupIdStr="${groupIdStr }" cust_type="2" change_type="1">${intCustNum }</a>
				                			</c:when>
				                			<c:otherwise>${intCustNum }</c:otherwise>
				                		</c:choose>
				                	</div>
				                </td>
				                <td><div class="overflow_hidden w100" title="">${intPoolNum }</div></td>
				                <td><div class="overflow_hidden w100" title="">${intNoNum }</div></td>
				                <td><div class="overflow_hidden w100" title="">${intNoContactCount }</div></td>
				            </tr>
				            <c:forEach items="${intDtos }" var="intDto" varStatus="vs">
				            	<tr class="${vs.count%2 eq 0 ? '' : 'sty-bgcolor-b' }">
					                <td><div class="overflow_hidden w90" title="">${intDto.userName }</div></td>
					                <td><div class="overflow_hidden w90" title="">${intDto.groupName }</div></td>
					                <td style="width:150px;"><div class="overflow_hidden w150 link" title="">${intDto.planNum }</div></td>
					                <td><div class="overflow_hidden w100" title="">${intDto.totalNum }</div></td>
					                <td>
					                	<div class="overflow_hidden w100" title="">
											<c:choose>
							                		<c:when test="${intDto.signNum gt 0 }">
							                			<a  href="javascript:;" id="view_change_${intDto.account }" cust_type="2" change_type="2">${intDto.signNum }</a>
							                		</c:when>
							                		<c:otherwise>${intDto.signNum }</c:otherwise>
							                </c:choose>
										</div>
									</td>
					                <td>
					                	<div class="overflow_hidden w100" title="">
					                		<c:choose>
					                			<c:when test="${intDto.custNum gt 0 }">
					                				<a  href="javascript:;" id="view_change_${intDto.account }" cust_type="2" change_type="1">${intDto.custNum }</a>
					                			</c:when>
					                			<c:otherwise>${intDto.custNum }</c:otherwise>
					                		</c:choose>
					                	</div>
					                </td>
					                <td><div class="overflow_hidden w100" title="">${intDto.poolNum }</div></td>
					                <td><div class="overflow_hidden w100" title="">${intDto.noNum }</div></td>
					                <td><div class="overflow_hidden w100" title="">${intDto.noContactCount }</div></td>
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
