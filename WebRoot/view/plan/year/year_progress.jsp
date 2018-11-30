<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>销售计划管理-年度销售规划-执行进度</title>
<script type="text/javascript" src="${ctx }/static/js/view/plan/year/year_progress.js"></script>
<script type="text/javascript">
var currMonth="${currMonth}";
var month="${currMonth}".replace(/^0/,"");
var planYear="${planYear}";
var selGroupId=null;
var selGroupName=null;

$(function(){
	$(".exceu-max-circle").each(function(){
		$(this).click(function(){
			month=$(this).attr("month").replace(/^0/,"");
			chart1Fn();
			chart2Fn();
			if($(this).parent().index()%2 != 0){
				$(this).parent().parent().children("div:even").find(".exceu-contain01").removeClass("exceu-contain01").addClass("exceu-contain");
				$(this).parent().parent().children("div:odd").find(".exceu-contain").removeClass("exceu-contain").addClass("exceu-contain01");
			
			}else{
				$(this).parent().parent().children("div:even").find(".exceu-contain").removeClass("exceu-contain").addClass("exceu-contain01");
				$(this).parent().parent().children("div:odd").find(".exceu-contain01").removeClass("exceu-contain01").addClass("exceu-contain");
			
			};
			$(this).parent().append('<div class="execu-prog-circ13"><div class="exceu-contain13"><span class="drop_a"><label class="arrow"><span>◆</span></label></span></div></div>');
			$(this).parent().siblings().find(".execu-prog-circ13").remove();
		});
	});
	var groupFirst = $(".sales-member li:first");
	selGroupId = $(groupFirst).attr("groupId");
	selGroupName = $(groupFirst).attr("groupName");
	$(".sales-member").find("li").each(function(){
		$(this).click(function(){
			selGroupId = $(this).attr("groupId");
			selGroupName = $(this).attr("groupName");
			chart2Fn();
			$(this).addClass("sales-mem-selectd");
			$(this).siblings().removeClass("sales-mem-selectd");
		});
	});
	chart1Fn();
	chart2Fn();
	
	var goldbltt = parseInt($(".trophy-max span").text());
	var txcel = parseInt($(".trophy-base span").text());
	var point = 0;
	if(goldbltt != 0){
		point = txcel/goldbltt;
	}
		if(point<1){
			var x=1;
			var inter = setInterval(function(){
				if(x<=point*136){
					if(x<0.75*(point*136)){
						if(x<0.5*(point*136)){
							if(x<0.25*(point*136)){
								$(".bottle-scroll").css({"background":"#B40000","height":/*point*136*/x+"px"});
							}else{
							$(".bottle-scroll").css({"background":"#D76A31","height":/*point*136*/x+"px"});
							}
						}else{
							$(".bottle-scroll").css({"background":"#E5AA22","height":/*point*136*/x+"px"});
						}
						}else{
							$(".bottle-scroll").css({"background":"#E7DA1F","height":/*point*136*/x+"px"});
						}
					if(x<=point*100){
						$(".bottle-bg span").text((x).toFixed(0)+"%");
					}
					x=x+1;
				};
			},5);
			if(point<0.75){
				var x=1;
				var inter = setInterval(function(){
					if(x<=point*136){
						if(x<0.5*(point*136)){
							if(x<0.25*(point*136)){
								$(".bottle-scroll").css({"background":"#B40000","height":/*point*136*/x+"px"});
							}else{
							$(".bottle-scroll").css({"background":"#D76A31","height":/*point*136*/x+"px"});
							}
						}else{
							$(".bottle-scroll").css({"background":"#E5AA22","height":/*point*136*/x+"px"});
						}
						if(x<=point*100){
							$(".bottle-bg span").text((x).toFixed(0)+"%");
						}
						x=x+1;
					}
				},20);
					if(point < 0.5){
						var x=1;
						var inter = setInterval(function(){
							if(x<=point*136){
								if(x<0.25*(point*136)){
									$(".bottle-scroll").css({"background":"#B40000","height":/*point*136*/x+"px"});
								}else{
								$(".bottle-scroll").css({"background":"#D76A31","height":x+"px"});
								}
								if(x<=point*100){
									$(".bottle-bg span").text((x).toFixed(0)+"%");
								}
								x=x+1;
							}
						},1000);
						if(point < 0.25){
							var x=1;
							var inter = setInterval(function(){
								if(x<=point*136){
									$(".bottle-scroll").css({"background":"#B40000","height":/*point*136*/x+"px"});
									if(x<=point*100){
										$(".bottle-bg span").text((x).toFixed(0)+"%");
									}
									x=x+1;
								}
							},1200);
						}
					};
			}
		}
		if(point >= 1){
			var x=1;
			var inter = setInterval(function(){
				if(x<=point*136){
					if(x<0.75*(point*136)){
						if(x<0.5*(point*136)){
							if(x<0.25*(point*136)){
								$(".bottle-scroll").css({"background":"#B40000","height":/*point*136*/x+"px"});
							}else{
							$(".bottle-scroll").css({"background":"#D76A31","height":/*point*136*/x+"px"});
							}
						}else{
							$(".bottle-scroll").css({"background":"#E5AA22","height":/*point*136*/x+"px"});
						}
						}else{
							$(".bottle-scroll").css({"background":"#E7DA1F","height":/*point*136*/x+"px"});
						}
					if(x<=point*100){
						$(".bottle-bg span").text((x).toFixed(0)+"%");
					}
					x=x+1;
				}else{

			$(".bottle-bg").css({"background":"url(${ctx}/static/images/glod-cup.png) no-repeat"});
			$(".bottle-bg span").text("");
				}
			},15);
		};
	
});
</script>
</head>
<body> 
<div class="year-sale-play hyx-aspa-play">
	<ul class="ann-sale-plan clearfix">
		<a href="${ctx}/plan/year/view"><li class="li_first">年度规划</li></a>
		<a href="${ctx}/plan/year/progress/view"><li class="select">执行进度</li></a>
		<a href="${ctx}/plan/yearlog/view"><li class="li_last">变更日志</li></a>
	</ul>
	<div class="annual-sales-play">
	<div class="execu-prog-axis fl_l">
		<div class="execu-max-line"></div>
		<div class="execu-max-contain">
		<div class="execu-prog-circ">
			<span class="exceu-circle"></span>
			<span class="exceu-line"></span>
		</div>
		<c:set var="yu" value="${currMonth%2}"></c:set>
		<c:forEach items="${dto.months}" var="month" varStatus="i">
			<c:set var="plan" value="${dto.map[month]}"></c:set>
			<div class="execu-prog-circ${month }">
			<c:if test="${i.index%2!=yu }">
			<div class="exceu-contain01 exceu-conta-local${month}">
				<span class="drop_a">
					<label class="arrow"><em>◆</em><span>◆</span></label>
					<label class="box">
						<c:choose>
						<c:when test="${plan.planMoney!=0}">
						<c:choose>
							<c:when test="${planYear<currYear or (planYear == currYear and  month <= currMonth) }">
							<label class="list_right">
								<label for="" class="exceu-progra">规划：</label>
								<span class="exceu-je"><fmt:formatNumber value="${plan.planMoney}" minFractionDigits="0" pattern="#.##"/>万</span>
							</label>
							<label class="list_right">
								<label for="" class="exceu-progra">完成度：</label>
								<span class="exceu-je"><fmt:formatNumber value="${plan.factMoney/plan.planMoney}" minFractionDigits="0" pattern="#%"/></span>
							</label>
							</c:when>
							<c:otherwise>
								<label class="list_right01">
								<label for="" class="exceu-progra">规划：</label>
								<span class="exceu-je"><fmt:formatNumber value="${plan.planMoney}" minFractionDigits="0" pattern="#.##"/>万</span>
								</label>
							</c:otherwise>
						</c:choose>
						</c:when>
						<c:otherwise>
						<label class="list_right02">
							<label for="" class="exceu-progra">待编辑</label>
						</label>
						</c:otherwise>
						</c:choose>
					</label>
				</span>
			</div>
			</c:if>
			<c:choose>
				<c:when test="${planYear==currYear and currMonth ==month }">
						<span month="${month }" class="exceu-max-circle">
							<span class="exceu-circle-selec">${month}</span>
						</span>
						<c:if test="${!i.last}">
							<span class="exceu-line${month} line-exceu"></span>
						</c:if>
						<div class="execu-prog-circ13"><div class="exceu-contain13"><span class="drop_a"><label class="arrow"><span>◆</span></label></span></div></div>
				</c:when>
				<c:when test="${planYear<currYear or (planYear == currYear and  month <= currMonth) }">
					<c:choose>
						<c:when test="${plan.factMoney>=plan.planMoney}">
							<span month="${month }" class="exceu-max-circle">
								<span class="exceu-circle${month} finish">${month}</span>
							</span>
							<c:if test="${!i.last}">
							<span class="exceu-line${month} finish"></span>
							</c:if>
						</c:when>
						<c:otherwise>
							<span month="${month }" class="exceu-max-circle">
								<span class="exceu-circle${month} nofinish">${month}</span>
							</span>
							<c:if test="${!i.last}">
							<span class="exceu-line${month} nofinish"></span>
							</c:if>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
						<span class="exceu-max-circle01">
							<span class="exceu-circle${month } circle-exceu-all">${month }</span>
						</span>
						<c:if test="${!i.last}">
						<span class="exceu-line${month } circle-exceu-all"></span>
						</c:if>
				</c:otherwise>
			</c:choose>
			<c:if test="${i.index%2==yu }">
			<div class="exceu-contain exceu-conta-local${month }">
				<span class="drop_a">
					<label class="arrow"><em>◆</em><span>◆</span></label>
					<label class="box">
						<c:choose>
						<c:when test="${plan.planMoney!=0}">
						<c:choose>
							<c:when test="${planYear<currYear or (planYear == currYear and  month <= currMonth) }">
							<label class="list_right">
								<label for="" class="exceu-progra">规划：</label>
								<span class="exceu-je"><fmt:formatNumber value="${plan.planMoney}" minFractionDigits="0" pattern="#.##"/>万</span>
							</label>
							<label class="list_right">
								<label for="" class="exceu-progra">完成度：</label>
								<span class="exceu-je"><fmt:formatNumber value="${plan.factMoney/plan.planMoney}" minFractionDigits="0" pattern="#%"/></span>
							</label>
							</c:when>
							<c:otherwise>
								<label class="list_right01">
								<label for="" class="exceu-progra">规划：</label>
								<span class="exceu-je"><fmt:formatNumber value="${plan.planMoney}" minFractionDigits="0" pattern="#.##"/>万</span>
								</label>
							</c:otherwise>
						</c:choose>
						
						</c:when>
						<c:otherwise>
							<label class="list_right02">
							<label for="" class="exceu-progra">待编辑</label>
						</label>
						</c:otherwise>
						</c:choose>
					</label>
				</span>
			</div>
			</c:if>
		</div>
		</c:forEach>
		</div>
	</div>
	<div class="plate-cup fl_l">
		<div class="bottle-cup">
			<span class="bottle-bg"><span></span></span>
			<span class="bottle-scroll"></span>
		</div>
		<div class="trophy-base"><span>${progress.factMoney}</span>万</div>
		<div class="trophy-max" style="display:none;"><span>${progress.planMoneyAll}万</span></div>
	</div>
	</div>
</div>
<div class="annual-deta-bar">
    <div id="main01" class="anal-deta-bar"></div>
</div>
<div class="sales-member clearfix">
	<ul>
	<c:forEach items="${groups}" var="group" varStatus="i">
		<li groupId="${group.groupId}" groupName="${group.groupName}"<c:if test="${i.first}">class="sales-mem-selectd"</c:if>>
		<span>${group.groupName}</span>
		</li>
	</c:forEach>
	</ul>
	<div class="annual-deta-bar02">
    	<div id="main02" class="anal-deta-bar"></div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/thirdparty/echarts-3.3.2/echarts.min.js"></script>
</body>
</html>
