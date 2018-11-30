<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-月度计划（团队查看）</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/default/easyui.css">
<style type="text/css">
	body{background-color:#f4f4f4 !important;}
</style>
<script type="text/javascript" src="${ctx}/static/js/stamper/jquery.stamper.js"></script>
<script type="text/javascript" src="${ctx}/static/js/view/plan/month/team/year_view.js"></script>
</head>
<body> 
<form id="yearPlanForm" action="${ctx}/plan/month/team/yearView">
	<input id="planYear" name="planYear" type="hidden" value="${planYear}"/>
	<input id="groupId" name="groupId" type="hidden" value="${groupId}"/>
	<input id="groupName" name="groupName" type="hidden" value="${groupName}"/>
</form>
<div class="com-conta" style="width:1238px;margin:0 auto;">
	<%-- <h2 class="hyx-mpe-h2">${planYear}年${groupName}月度计划</h2> --%>
	<div class="hyx-aspa-time" style="margin-top:0;">
		<label class="center">
			<span class="sp_a fl_l">
				<label class="left">&lt;</label>
				<label class="date">${planYear}</label>
				<label class="right">&gt;</label>
			</span>
			<span class="sp_b fl_l">
				<label class="fl_l">年</label>
				<dl class="select">
					<dt>${groupName}</dt>
					<dd>
						<ul>
							<c:forEach items="${groups}" var="group" >
								<li><a href="javascript:setGroupId('${group.groupId }','${group.groupName }');" title="${group.groupName }">${group.groupName }</a></li>
							</c:forEach>
						</ul>
					</dd>
				</dl>
				<label class="fl_l">月度计划</label>
			</span>
		</label>
	</div>
	<div class="com-btnlist hyx-mpp-btnlist">
		<a href="javascript:;" class="com-btna demoBtn_a fl_r" style="margin:0;"><i class="min-rise"></i><label class="lab-mar">历史走势</label></a>
	</div>
	<div class="hyx-mpp hyx-mte">
		 <c:forEach items="${months}" var="month" varStatus="i">
		<c:choose>
			<c:when test="${plansMap[month] !=null}">
			<c:set value="${plansMap[month].teamPlan}" var="plan" ></c:set>
			<c:set value="${plansMap[month].planStatus}" var="planStatus" ></c:set>
			<c:set value="${plansMap[month].teamPlanCommonts}" var="commonts" ></c:set>
				<c:choose>
					<c:when test="${planYear <currentYear or (planYear ==currentYear and month < currentMonth)  or (planYear ==currentYear and month == currentMonth and plan.status==1 and plan.authState!=0) }">
						<div class="box fl_l <c:if test="${month ==6 or month ==12}">box_no_mr</c:if>">
						<dl class="dl_big">
							<c:choose>
								<c:when test="${plan.planStatus==2}"><dt class="medal_small dt_complete_${month}"></dt></c:when>
								<c:otherwise><dt class="medal_small dt_hang_${month}"></dt></c:otherwise>
							</c:choose>
							
							<dd class="list skin-minimal">
								<c:choose>
								<c:when test="${plan.planWillcustnumState==0}">
									<i class="icon"></i>
									<label class="lab">新增意向：<span class="a" style="display:none;" name="${plan.factWillcustnum}">${plan.factWillcustnum}</span><span class="b">${plan.planWillcustnum}</span>个</label>
									<dl class="barbox">
					                    <dd class="barline">
					                        <span style="width:0%;" class="charts"></span>
					                    </dd>
				               	 	</dl>
				               	 	<p class="perc">0</p>
				                	<span class="add_integral com-red">+100</span>
								</c:when>
								
								<c:when test="${plan.planWillcustnumState==1}">
									<i class="icon"></i>
									<label class="lab">新增意向：<span class="a" style="display:none;" name="${plan.factWillcustnum}">${plan.factWillcustnum}</span><span style="display:none;">/</span><span class="b">${plan.planWillcustnum}</span>个</label>
									<dl class="barbox">
					                    <dd class="barline">
					                        <span style="width:0%;" class="charts"></span>
					                    </dd>
				               	 	</dl>
			               	 	 	<p class="perc">0</p>
					                <a planMonth="${month }" planId ="${plan.id}" pointType="will" href="javascript:;" class="com-btnc com-btnc-sty receive">领&nbsp;&nbsp;取</a>
					                <span class="add_integral com-red">+100</span>
								</c:when>
								
								<c:when test="${plan.planWillcustnumState==2}">
									<i class="icon icon_check"></i>
									<label class="lab">新增意向：<span class="a" name="${plan.factWillcustnum}">${plan.factWillcustnum}</span>/<span class="b">${plan.planWillcustnum}</span>个</label>
								</c:when>
								</c:choose>
							</dd>
							
							
							<dd class="list skin-minimal">
							<c:choose>
								<c:when test="${plan.planSigncustnumState==0}">
									<i class="icon"></i>
									<label class="lab">新增签约：<span class="a" style="display:none;" name="${plan.factSigncustnum}">${plan.factSigncustnum}</span><span class="b">${plan.planSigncustnum}</span>个</label>
									<dl class="barbox">
					                    <dd class="barline">
					                        <span style="width:0%;" class="charts"></span>
					                    </dd>
					                </dl>
					                <p class="perc">0</p>
					                <span class="add_integral com-red">+100</span>
								</c:when>
								
								<c:when test="${plan.planSigncustnumState==1}">
									<i class="icon"></i>
									<label class="lab">新增签约：<span class="a" style="display:none;" name="${plan.factSigncustnum}">${plan.factSigncustnum}</span><span style="display:none;">/</span><span class="b">${plan.planSigncustnum}</span>个</label>
									<dl class="barbox">
					                    <dd class="barline">
					                        <span style="width:0%;" class="charts"></span>
					                    </dd>
					                </dl>
					                <p class="perc">0</p>
					                <a planMonth="${month }" planId ="${plan.id}" pointType="sign" href="javascript:;" class="com-btnc com-btnc-sty receive">领&nbsp;&nbsp;取</a>
					                <span class="add_integral com-red">+100</span>
								</c:when>
								
								<c:when test="${plan.planSigncustnumState==2}">
									<i class="icon icon_check"></i>
									<label class="lab">新增签约：<span class="a" name="${plan.factSigncustnum}">${plan.factSigncustnum}</span>/<span class="b">${plan.planSigncustnum}</span>个</label>
								</c:when>
								
							</c:choose>
								
							</dd>
							<dd class="list skin-minimal">
								<c:choose>
									<c:when test="${plan.planMoneyState==0}">
										<i class="icon"></i>
										<label class="lab">回款金额：<span class="a" style="display:none" name="${plan.factMoney}" >${plan.factMoney}</span><span class="b" >${plan.planMoney}</span>万</label>
										<dl class="barbox">
						                    <dd class="barline">
						                        <span style="width:0%;" class="charts"></span>
						                    </dd>
						                </dl>
						                <p class="perc">0</p>
						                <span class="add_integral com-red">+100</span>
									</c:when>
									
									<c:when test="${plan.planMoneyState==1}">
										<i class="icon"></i>
										<label class="lab">回款金额：<span class="a" style="display:none" name="${plan.factMoney}">${plan.factMoney}</span><span style="display:none;">/</span><span class="b">${plan.planMoney}</span>万</label>
										<dl class="barbox">
						                    <dd class="barline">
						                        <span style="width:0%;" class="charts"></span>
						                    </dd>
						                </dl>
						                <p class="perc">0</p>
						                <a  planMonth="${month }" planId ="${plan.id}" pointType="money" href="javascript:;" class="com-btnc com-btnc-sty receive">领&nbsp;&nbsp;取</a>
						                <span class="add_integral com-red">+100</span>
									</c:when>
									
									<c:when test="${plan.planMoneyState==2}">
										<i class="icon icon_check"></i>
										<label class="lab">回款金额：<span class="a" name="${plan.factMoney}">${plan.factMoney}</span>/<span class="b">${plan.planMoney}</span>万</label>
									</c:when>
									
								</c:choose>
							</dd>
							<c:if test="${commonts !=nul and !empty commonts}">
								<dd class="comment comment_hid"><span class="comment_con">点评：${plan.lastCommont}</span>
									<span class="comment_drop" style="display:none;">
									<label class="arrow"><em>◆</em><span>◆</span></label>
									<label class="drop_box">
									<c:forEach items="${commonts}" var="commont" varStatus="i"> 
										<span class="drop_box_list">${commont.commontUserName }：${commont.context }</span>
									</c:forEach>
									</label>
									</span>
								</dd>
							</c:if>
							<dd class="link" <c:if test="${commonts ==nul or empty commonts}">style="margin-top:65px;"</c:if>>
								<a data-authority="groupPlan_check" href="javascript:newTab('${ctx}/plan/month/team/monthView_p${_v }&id=${plan.id}','小组月计划-查看')" class="fl_r">查看</a>
							</dd>
							<c:if test="${plan.planWillcustnumState==1 and plan.planSigncustnumState==1 and plan.planMoneyState==1}">
								<dd class="receive_all">
							 		<a planMonth="${month }" planId ="${plan.id}" pointType="all" href="javascript:;" class="com-btnc com-btnc-sty">全领取</a>
							 	</dd>
							</c:if>
						</dl>
						<c:choose>
							<c:when test="${plan.planStatus==2}"><i class="seal_compelte"></i></c:when>
							<c:otherwise><i class="seal_cover"></i></c:otherwise>
						</c:choose>
						</div>
					</c:when>
					
					<c:otherwise>
						<div class="box <c:if test="${plan.status==0}">box_not</c:if> fl_l <c:if test="${month ==6 or month ==12}">box_no_mr</c:if>">
							 <dl class="dl_big">
							 	<c:choose>
									<c:when test="${plan.status==0}"><dt class="medal_small dt_not_${month}"></dt></c:when>
									<c:otherwise><dt class="medal_small dt_hang_${month}"></dt></c:otherwise>
								</c:choose>
							 	<dd class="list skin-minimal">
							 		<i class="icon"></i>
							 		<label class="lab">新增意向：<span class="a" name="${plan.planWillcustnum}">${plan.planWillcustnum}</span>个</label>
							 	</dd>
							 	<dd class="list skin-minimal">
							 		<i class="icon"></i>
							 		<label class="lab">新增签约：<span class="a" name="${plan.planSigncustnum}">${plan.planSigncustnum}</span>个</label>
							 	</dd>
							 	<dd class="list skin-minimal">
							 		<i class="icon"></i>
							 		<label class="lab">回款金额：<span class="a" name="${plan.planMoney}">${plan.planMoney}</span>万</label>
							 	</dd>
							 	<c:if test="${commonts !=null and !empty commonts}">
									<dd class="comment comment_hid"><span class="comment_con">点评：${commonts[0].context }</span>
										<span class="comment_drop" style="display:none;">
										<label class="arrow"><em>◆</em><span>◆</span></label>
										<label class="drop_box">
										<c:forEach items="${commonts}" var="commont" varStatus="i"> 
											<span class="drop_box_list">${commont.commontUserName }：${commont.context }</span>
										</c:forEach>
										</label>
										</span>
									</dd>
								</c:if>
							 	<dd class="link" <c:if test="${commonts ==null or empty commonts}">style="margin-top:65px;"</c:if>>
							 		<c:choose>
										<c:when test="${(plan.status==0 or plan.authState==0)and ((planYear == currentYear and  month == currentMonth and currentTime < autoAuthTime) or(planYear > currentYear)or(planYear == currentYear and month > currentMonth))}">
										<a data-authority="groupPlan_edit" href="javascript:newTab('${ctx}/plan/month/team/monthEdit${_v }&id=${plan.id}&isFirst=true&groupType=0','小组月计划-编辑')" class="fl_r">编辑</a>
										</c:when>
										<c:otherwise>
										<a data-authority="groupPlan_check" href="javascript:newTab('${ctx}/plan/month/team/monthView${_v }&id=${plan.id}','小组月计划-查看')" class="fl_r">查看</a>
										</c:otherwise>
									</c:choose>
							 	</dd>
							 </dl>
							<c:choose>
								<c:when test="${plan.status==0}">
									<c:choose>
										<c:when test="${planStatus!=null and (planStatus.noUpNum>0 or planStatus.noAuthNum>0)}">
										<div class="hyx-mpp-never">
											<c:if test="${planStatus!=null and planStatus.noUpNum>0}"><p>${planStatus.noUpNum}人未上报</p></c:if>
										 	<c:if test="${planStatus!=null and planStatus.noAuthNum>0}"><p>${planStatus.noAuthNum}人未审核</p></c:if>
									 	</div>
									 	</c:when>
									 	<c:otherwise>
										<i class="seal_not">未上报</i>
									</c:otherwise>
									</c:choose>
								</c:when>
								<c:when test="${plan.authState==0}"><i class="seal_reject"></i></c:when>
							</c:choose>
							 
						</div>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<div class="box box_not <c:if test="${month ==6 or month ==12}">box_no_mr</c:if> fl_l"> 
				 <dl class="dl_big">
				 	<dt class="medal_big dt_next_${month}"></dt>
				 	<dd class="link">
				 		<c:choose>
				 		<c:when test="${(planYear > currentYear or (planYear == currentYear and  month > currentMonth))}">
				 		<a data-authority="groupPlan_edit" href="javascript:newTab('${ctx}/plan/month/team/monthEdit${_v }&groupId=${groupId}&planYear=${planYear}&planMonth=${month}&isNew=true&isFirst=true&groupType=0','小组月计划-编辑')" class="fl_r">编辑</a>
				 		</c:when>
				 		<c:when test="${(planYear == currentYear and  month == currentMonth and currentTime < autoAuthTime)}">
				 		<a data-authority="groupPlan_edit" href="javascript:newTab('${ctx}/plan/month/team/monthEdit${_v }&groupId=${groupId}&planYear=${planYear}&planMonth=${month}&isNew=true&isFirst=true&groupType=0','小组月计划-编辑')" class="fl_r">编辑</a>
				 		</c:when>
				 		</c:choose>
				 	</dd>
				 </dl>
				</div>
			</c:otherwise>
		</c:choose>
		</c:forEach>
	</div>
</div>
<div class="hyx-mpp-overlay">
	<div class="bg"></div>
	<div class="main">
		<img src="${ctx}/static/images/mt_overlay.png" class="main_bg" width="120%" height="120%" />
		<dl class="dl_box">
			<dt>完成<span id="zhezhao_title"></span>月计划</dt>
			<dd class="dd_a">获得<span>0</span>积分奖励</dd>
			<dd class="dd_b">0</dd>
		</dl>
	</div>
</div>
</body>
</html>