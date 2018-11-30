<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>客户跟进-服务记录</title>
<script type="text/javascript">

$(function(){

    /*客户跟进tab*/
    $('.hyx-cfu-tab').find('li').click(function(){
        $(this).addClass('select').siblings('li').removeClass('select');
        var index = $(this).index();
        $(this).parent().parent().parent().find('.hyx-cfu-timeline').hide();
        $(this).parent().parent().parent().find('.hyx-cfu-timeline').eq(index).show();
    });

    /*log加载*/
    $('.timeline').each(function(i,item){
        var load_num = 5,i_num = 5,forLiLen = 0,
        load_len = $(item).find('.li-load').length;
        if($(item).parent().hasClass('hyx-cfu-timeline') == true){
            load_num = 5;
            i_num = 4;
        }
        $(item).find('.li-load').css({'display':'none'});
        for(var i=0;i<load_num;i++){
            $(item).find('.li-load').eq(i).fadeIn(500);
        }
        function limitLi(){
            var timer = setTimeout(function(){
                if(i_num >= load_len){
                        clearTimeout(timer);
                }
                if(load_len - i_num < load_num){
                        forLiLen = load_len;
                }else{
                        forLiLen = i_num + load_num;
                }
                if(forLiLen <= load_len){
                        for(var i=i_num;i<forLiLen;i++){
                                $(item).find('.li-load').eq(i).fadeIn(1000);
                        }
                }
                i_num = i_num + load_num;
            },200);
        }

        $('.hyx-wlm-cent-timeline').scroll(function(){
            if($(this)[0].scrollTop + $(this).height() >= $(this)[0].scrollHeight){
                limitLi();
            }
         });
    });

	/*客户跟进加载*/
    var left_height = $('.hyx-cfu-left').height();
    var card_height = $('.hyx-cfu-card').height();
    var tab_height = $('.hyx-cfu-tab').height();
    var load_height = left_height - card_height - tab_height -65;
    $('.hyx-cfu-timeline').each(function(i,item){
        $(item).css({'height':load_height + 'px'})
        $(item).find('.right:first').find('.cfu-box').find('i').addClass('i-down');
        $(item).find('.right:first').find('.com-none').slideDown();
        $(item).find('.right').each(function(j,itema){
           $(itema).find('i').click(function(){
                	var right_box = $(this).parents('.right');
                    if(right_box.find('.com-none').css('display') == 'none'){
                        right_box.parent().siblings('.li-load').find('.cfu-box').find('i').removeClass('i-down');
                        right_box.parent().siblings('.li-load').find('.com-none').slideUp();
                        right_box.find('.cfu-box').find('i').addClass('i-down');
                        right_box.find('.com-none').slideDown();
                    }else{
                        right_box.find('.cfu-box').find('i').removeClass('i-down');
                        right_box.find('.com-none').slideUp();
                    }
                });
        });
    });

    /*下拉框部分*/
    $('.dt_list,.mail').click(function(){
        $(this).find('.drop').fadeToggle();
    });
});

/** 客户卡片 */
function custCard(custId,custName){
	var custName1 = custName||"客户卡片";
	window.top.addTab(ctx+"/cust/card/custInfoCard?custId="+custId,custName1);
}
</script>

<script type="text/javascript">
$(function(){
	$('.demoBtn_a').click(function(){
		showIframe('alert_cust_follow_a','alert_cust_follow_a.html','跟进时间变更',450,330);
	});

	$('.demoBtn_b').click(function(){
		showIframe('alert_cust_follow_b','alert_cust_follow_b.html','发送短信',915,510);
	});
	$('.demoBtn_c').click(function(){
		showIframe('alert_cust_follow_c','alert_cust_follow_c.html','信息编辑',700,570);
	});
	$('.demoBtn_d').click(function(){
		showIframe('alert_cust_follow_d.html','alert_cust_follow_d.html','绑定邮箱',330,190);
	});
	});
</script>

<script type="text/javascript" src="${ctx}/static/js/common/tablerows.js${_v}"></script>
</head>
<body>
<div class="hyx-cfu-left fl_l">
<form action="${ctx }/cust/service/visit/serviceVisitListPage.do" method="post">
   <div class="com-search hyx-cfu-search">
      <div class="com-search-box fl_l">
	   <p class="search clearfix"><input class="input-query fl_l" type="text" value="" /><label class="hide-span">输入客户名称/联系电话/联系人</label></p>
	   <div class="list-box">
	   			<!--  下次联系时间  -->
			  <input type="hidden" name="nstartDate" id="nstartDate" value="${item.nstartDate }" />
			  <input type="hidden" name="nendDate" id="nendDate" value="${item.nendDate }"/>
			  <input type="hidden" name="nDateType" id="nDateType" value="${nDateType }" />
			  <c:set var="nDateName" value="下次联系时间" />
			  <c:choose>
		   		<c:when test="${nDateType eq 1 }"><c:set var="nDateName" value="当天" /></c:when>
		   		<c:when test="${nDateType eq 2 }"><c:set var="nDateName" value="本周" /></c:when>
		   		<c:when test="${nDateType eq 3 }"><c:set var="nDateName" value="本月" /></c:when>
		   		<c:when test="${nDateType eq 4 }"><c:set var="nDateName" value="半年" /></c:when>
		   		<c:when test="${nDateType eq 5 }"><c:set var="nDateName" value="自定义" /></c:when>
		  	 </c:choose>
			<dl class="select" style="margin-bottom:4px;">
				<dt>${nDateName}</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="setNdate(0)">下次联系时间</a></li>
						<li><a href="###" onclick="setNdate(1)">当天</a></li>
						<li><a href="###" onclick="setNdate(2)">本周</a></li>
						<li><a href="###" onclick="setNdate(3)">本月</a></li>
						<li><a href="###" onclick="setNdate(4)">半年</a></li>
						<li><a href="###" onclick="setNdate(5)"id="d1" class="diy">自定义</a></li>
					</ul>
				</dd>
			</dl>
			  <!--  最近联系时间  -->
			  <input type="hidden" name="vstartDate" id="vstartDate" value="${item.vstartDate }" />
			  <input type="hidden" name="vendDate" id="vendDate" value="${item.vendDate }"/>
			  <input type="hidden" name="DateType" id="vDateType" value="${vDateType }" />
			  <c:set var="vDateName" value="最近联系时间" />
			  <c:choose>
		   		<c:when test="${vDateType eq 1 }"><c:set var="vDateName" value="当天" /></c:when>
		   		<c:when test="${vDateType eq 2 }"><c:set var="vDateName" value="本周" /></c:when>
		   		<c:when test="${vDateType eq 3 }"><c:set var="vDateName" value="本月" /></c:when>
		   		<c:when test="${vDateType eq 4 }"><c:set var="vDateName" value="半年" /></c:when>
		   		<c:when test="${vDateType eq 5 }"><c:set var="vDateName" value="自定义" /></c:when>
		  	 </c:choose>
			<dl class="select" style="margin-bottom:4px;">
				<dt>${vDateName}</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="setVdate(0)">下次联系时间</a></li>
						<li><a href="###" onclick="setVdate(1)">当天</a></li>
						<li><a href="###" onclick="setVdate(2)">本周</a></li>
						<li><a href="###" onclick="setVdate(3)">本月</a></li>
						<li><a href="###" onclick="setVdate(4)">半年</a></li>
						<li><a href="###" onclick="setVdate(5)" id="d2" class="diy">自定义</a></li>
					</ul>
				</dd>
			</dl>
			<!-- 服务评级 -->
			<input type="hidden" name="serviceLevel" id="serviceLevel" value="${item.serviceLevel }">
		   	 <c:set var="serviceLevelName" value="服务评级"/>
		   	 <c:forEach items="${serviceLevelList}" var="v">
					<option value="${v.optionlistId}" ${item.serviceLevel eq v.optionlistId?'selected':''}>${v.optionName}</option>
				</c:forEach>
		   <dl class="select" style="margin-bottom:4px;">
				<dt>${serviceLevelName }</dt>
				<dd>
					<ul>
						<li><a href="###" onclick="$('#serviceLevel').val('');">服务评级</a></li>
						<c:forEach var="sop" items="${serviceLevelList}">
							<li><a href="###" onclick="$('#serviceLevel').val('${sop.optionlistId}');" title="${sop.optionName }">${sop.optionName }</a></li>
						</c:forEach>
					</ul>
				</dd>
			</dl>
				<div class="reso-sub-dep fl_l">
					<input class="owner-sour" type="text" value="客服人员" style="height:18px;line-height:18px;border-right:2px solid #e1e1e1;">
					<div class="manage-owner-sour">
						<div class="sub-dep-ul">
							<ul id="tt1" >

						      </ul>
			    		</div>
					    <div class="sure-cancle clearfix" style="width:120px">
							<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" href="javascript:;"><label>确定</label></a>
							<a class="com-btnd bomp-btna fl_l" id="close02" href="javascript:;"><label>清空</label></a>
						</div>
					</div>
					</div>
				<div class="reso-sub-dep fl_l">
					<input class="owner-sour" type="text" value="所有者">
					<div class="manage-owner-sour">
						<div class="sub-dep-ul">
								<ul id="tt2"  >

						        </ul>
			    		</div>
					    <div class="sure-cancle clearfix" style="width:120px">
							<a class="com-btnc bomp-btna com-btna-sty reso-owner-sure fl_l" href="javascript:;"><label>确定</label></a>
							<a class="com-btnd bomp-btna fl_l" id="close02" href="javascript:;"><label>清空</label></a>
						</div>
					</div>
					</div>
					<div class="list-hid fl_l" style="min-width:400px;">
						<p class='tag fl_l' style="padding:10px 22px 5px 0;"><label>服务标签：</label><a href='javascript:;'>全部标签</a></p>
						<p class="check skin-minimal fl_l"><input type="radio" /><label class="fl_l" style="height:23px;line-height:23px;">有效联系</label></p>
					</div>
		</div>
	</div>
		<input class="query-button fl_r" type="button" value="查&nbsp;询" id="query" onClick="doQuery();"/>
		<a href="javascript:;" class="more"><i></i>更多</a>
  </div>
	<div class="com-table hyx-fur-table fl_l" style="display:block;margin-top:40px;">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">操作</span></th>
					<th><span class="sp sty-borcolor-b"><label class="lab-d">联系日期</label></span></th>
					<th><span class="sp sty-borcolor-b">客户名称</span></th>
					<th><span class="sp sty-borcolor-b">客服人员</span></th>
					<th><span class="sp sty-borcolor-b"><label class="lab-d">服务评级</label></th>
					<th><span class="sp sty-borcolor-b"><label class="lab-d">有效联系</label></th>
					<th><span class="sp sty-borcolor-b"><label class="lab-d">销售最近联系</label></span></th>
					<th>所有者</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty serviceVisitList }">
						<c:forEach items="${serviceVisitList }"  var="visit"  varStatus="vs">
							<tr class="${vs.count%2==0?'sty-bgcolor-b':''}">
								<td style="width:100px;"><div class="overflow_hidden w100 link"><a href="javascript:;" class="icon-cust-card" onclick="custCard('${visit.custId}','${visit.custName}')"  title="客户卡片"></a></div></td>
								<td><div class="overflow_hidden w90" title="<fmt:formatDate value="${visit.visitingDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${visit.visitingDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div></td>
								<td><div class="overflow_hidden w70" title="${visit.custName}">${visit.custName}</div></td>
								<td><div class="overflow_hidden w90" title="${visit.visitingName}">${visit.visitingName}</div></td>
								<td><div class="overflow_hidden w100" title="${visit.optionName}">${visit.optionName}</div></td>
								<td><div class="overflow_hidden w50" >
										<c:if test="${visit.effectiveness eq '0'}">否</c:if>
			                            <c:if test="${visit.effectiveness eq '1'}">是</c:if>
								</div></td>
								<td><div class="overflow_hidden w90" title="<fmt:formatDate value="${visit.saleFollowDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${visit.saleFollowDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div></td>
								<td><div class="overflow_hidden w70" title="${visit.ownerAcc}">${visit.ownerAcc}</div></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="no_data_tr"><td colspan="8" style="text-align: center;">当前列表无数据！</td></tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	<div class="com-bot">
		<div class="com-page fl_r">
			${item.page.pageStr}
		</div>
	</div>
	</form>
</div>
<div class="hyx-cm-right hyx-cfu-right fl_l">
	<!-- 右侧页面  serviceVisitRecordRight.jsp -->
</div>
</body>
</html>
