<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>Document</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/js/monl-cale/css/monl-cale.css">
	<script type="text/javascript" src="${ctx}/static/js/monl-cale/monl-cale.js"></script>
	<script type="text/javascript">
	var planYear="${planYear}";
	var groupId="${groupId}";
	var currentMonth = ${currentMonth};
	var currentYear = ${currentYear};
	var editRole = window.top.shrioAuthObj("deptPlan_edit");
	</script>
	<script type="text/javascript" src="${ctx}/static/js/view/plan/month/group/year_view.js"></script>
</head>
<body>
	<form id="yearPlanForm" action="${ctx }/plan/month/group/yearView">
		<input id="planYear" type="hidden" name="planYear" value="${planYear}"/>
		<input id="planMonth" type="hidden" name="planMonth" value="${planMonth}"/>
		<input id="groupId" type="hidden" name="groupId" value="${groupId}"/>
		<input id="groupName" name="groupName" type="hidden" value="${groupName}"/>
		<input id="isNotFirst" name="isNotFirst" type="hidden" value="true"/>
	</form>
	<div class="hyx-aspa-time" style="margin-bottom:20px;">
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
				<label class="fl_l">月度销售计划及执行分析</label>
			</span>
			</label>
		</div>
	<div class="bottle">
		<span class="l"></span>
		<div id="list_shot">
		<ul>
			<c:forEach items="${months}" var="month" varStatus="i">
			<c:choose>
				<c:when test="${plansMap[month] !=null}">
				<c:set value="${plansMap[month].teamPlan}" var="plan" ></c:set>
					<c:choose>
					<c:when test="${planYear<currentYear or (planYear==currentYear and month <= currentMonth)}">
					<li>
						<div class="canl-first ${(planYear==currentYear and month == currentMonth and currentTime < autoAuthTime and (plan.status==0 or plan.authState==0))?'no-exec':''}" planId="${plan.id }" planMonth="${month}" status="${plan.status }" authState="${plan.authState}">
							<img src="${ctx}/static/js/monl-cale/slider/coin-gold.png" alt="" class="icon-gold">
							<div class="canl-icon-right">
								<p class="canl-jan"><label for="">${month}</label><span>月</span></p>
								<p class="play-sum-ten">计划${plan.planMoney }万</p>
								<p class="play-fina"><span style="display:inline-block;float:left;">完成${plan.factMoney }万</span>
								<c:choose>
									<c:when test="${plan.factMoney >plan.planMoney}"><i class="up-arrow"></i></c:when>
									<c:when test="${plan.factMoney <plan.planMoney}"><i class="down-arrow"></i></c:when>
								</c:choose>
								</p>
								
							</div>
						</div>
						<div class="canl-second" style="display:none;">
							<div class="canl-mon-max">
								<label for="">${month}</label><span>月</span>
								<c:choose>
								<c:when test="${planYear==currentYear and month == currentMonth and currentTime < autoAuthTime and (plan.status==0 or plan.authState==0)}">
								<a data-authority="deptPlan_edit" class="canl-edit" planId="${plan.id }" planMonth="${month}" href="###">编辑</a>
								</c:when>
								<c:otherwise>
								<a data-authority="deptPlan_check" class="canl-look" planId="${plan.id }" planMonth="${month}" href="###">查看</a>
								</c:otherwise>
								</c:choose>
								
								<a class="canl-analyse" planId="${plan.id }" planMonth="${month}" href="###">分析</a>
							</div>
							
							<div class="canl-mon-scroll">
					            <div class="votebox fl_l">
					            	<c:choose>
					            	<c:when test="${plan.planWillcustnum>0}">
					            		<dl class="barbox">
						                    <dd class="barline">
						                        <span style="width:0%;" class="charts"></span>
						                    </dd>
						                </dl>
						                <span class="sp">新增意向：<label class="a will-cust" name="${plan.factWillcustnum}">0</label>个/<label class="b">${plan.planWillcustnum}</label>个</span>
					            		<p class="p"><span>0</span>%</p>
					            	</c:when>
					            	<c:otherwise>
					            		<span class="sp">新增意向：<label class="a will-cust" name="${plan.factWillcustnum}">${plan.factWillcustnum}</label>个/<label class="b">${plan.planWillcustnum}</label>个(无计划)</span>
					            	</c:otherwise>
					            	</c:choose>
					            </div>
					            <div class="votebox fl_l">
					            	<c:choose>
					            	<c:when test="${plan.planSigncustnum>0}">
				            	 	<dl class="barbox">
					                    <dd class="barline">
					                        <span style="width:0%;" class="charts"></span>
					                    </dd>
					                </dl>
					                <span class="sp">新增签约：<label class="a will-cust" name="${plan.factSigncustnum}">0</label>个/<label class="b">${plan.planSigncustnum}</label>个</span>
					                <p class="p"><span>0</span>%</p>
					            	</c:when>
					            	<c:otherwise>
					            	<span class="sp">新增签约：<label class="a will-cust" name="${plan.factSigncustnum}">${plan.factSigncustnum}</label>个/<label class="b">${plan.planSigncustnum}</label>个(无计划)</span>
					            	</c:otherwise>
					            	</c:choose>
					            </div>
					            <div class="votebox fl_l">
					            	<c:choose>
					            	<c:when test="${plan.planMoney>0}">
				            	 	<dl class="barbox">
					                    <dd class="barline">
					                        <span style="width:0%;" class="charts"></span>
					                    </dd>
					                </dl>
					                <span class="sp">回款金额：<label class="a will-cust" name="${plan.factMoney}">0</label>万/<label class="b">${plan.planMoney}</label>万</span>
					                <p class="p"><span>0</span>%</p>
					            	</c:when>
					            	<c:otherwise>
					            	<span class="sp">回款金额：<label class="a will-cust" name="${plan.factMoney}">${plan.factMoney}</label>万/<label class="b">${plan.planMoney}</label>万(无计划)</span>
					            	</c:otherwise>
					            	</c:choose>
					            </div>
							</div>
						</div>
						<div class="canl-third" style="display:none;">
							<div class="canvas"></div>
						</div>
					</li>
					</c:when>
					<c:otherwise>
						<li>
							<div class="canl-first no-exec" planId="${plan.id }" planMonth="${month}" status="${plan.status }" authState="${plan.authState} ">
								<img src="${ctx}/static/js/monl-cale/slider/coin-gold.png" alt="" class="icon-gold">
								<div class="canl-icon-right">
									<p class="canl-jan"><label for="">${month}</label><span>月</span></p>
									<p class="play-sum-ten">计划${plan.planMoney }万</p>
								</div>
							</div>
							<div class="canl-second" style="display:none;">
								<div class="canl-mon-max">
									<label for="">${month}</label><span>月</span>
								</div>
								<div class="canl-mon-noexec">
									<p><label for="">新增意向</label><label for="">新增签约</label><label for="">回款金额</label></p>
									<p><span>${plan.planWillcustnum}个</span><span>${plan.planSigncustnum}个</span><span>${plan.planMoney}万</span></p>
								</div>
							</div>
							<div class="canl-third" style="display:none;">
								<div class="canvas"></div>
							</div>
						</li>	
					</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<li>
					<div class="canl-first ${(planYear>currentYear or(planYear==currentYear and month>currentMonth) or (planYear==currentYear and month==currentMonth and currentTime < autoAuthTime))?'no-plan':''}" planMonth="${month}">
						<img src="${ctx}/static/js/monl-cale/slider/coin-gray.png" alt="" class="icon-gold">
						<div class="canl-icon-right" style="color:#777777;">
							<p class="canl-jan"><label for="">${month}</label><span>月</span></p>
							<p class="play-sum-ten">未编辑</p>
						</div>
					</div>
					<div class="canl-second" style="display:none;">
						<span class="plann-not-prep">计划未编制</span>
					</div>
					</li>
				</c:otherwise>
			</c:choose>
			</c:forEach>
		</ul>
		</div>
		<span class="r"></span>
	</div>
	<div class="month-look-analy">
	<c:choose>
	<c:when test="${planYear==currentYear and planMonth==currentMonth and currentTime < autoAuthTime &&(plansMap[planMonth] ==null or plansMap[month].status ==0 or plan.authState==0)}">
		<c:if test="${plansMap[planMonth] ==null }">
			<iframe src="${ctx}/plan/month/group/monthEdit?groupId=${groupId}&planYear=${planYear}&planMonth=${planMonth}&isNew=true&isFirst=true&groupType=1" id="iframepage" frameborder="0" scrolling="no" width="100%"></iframe>
		</c:if>
		<c:if test="${plansMap[planMonth] !=null }">
			<iframe src="${ctx}/plan/month/group/monthEdit?groupId=${groupId}&planYear=${planYear}&planMonth=${planMonth}&id=${plansMap[planMonth].id }&isFirst=true&groupType=1" id="iframepage" frameborder="0" scrolling="no" width="100%"></iframe>
		</c:if>
	</c:when>	
	<c:otherwise>
	<iframe src="${ctx}/plan/month/group/monthAnaly${_v}&planYear=${planYear}&planMonth=${planMonth}&groupId=${groupId}" id="iframepage" frameborder="0" scrolling="no" width="100%"></iframe>
	</c:otherwise>
	</c:choose>
	</div>
</body>
</html>