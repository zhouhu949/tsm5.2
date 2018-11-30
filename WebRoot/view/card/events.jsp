<%@page import="com.qftx.base.shiro.ShiroUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/common/card_include.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/qc_custCard.css${_v}">
    <script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/view/card/card_ajax.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/view/card/card_common.js${_v}"></script>
    <c:if test="${type eq 5 }">
    	<script type="text/javascript" src="${ctx}/static/js/view/card/card_contract.js${_v}"></script>
    </c:if>
    <c:if test="${type eq 6 }">
    	<script type="text/javascript" src="${ctx}/static/js/view/card/card_order.js${_v}"></script>
    </c:if>
    <c:if test="${type eq 7 }">
    	<script type="text/javascript" src="${ctx}/static/js/view/card/operate_log.js${_v}"></script>
    </c:if>
    <c:if test="${type eq 8 }">
     	<script type="text/javascript" src="${ctx}/static/js/view/card/sale_chance.js${_v}"></script>
    </c:if>
    <script type="text/javascript">
    	$(function(){
    		$("[btn-type=auth]").each(function(index,element){
    			var _this = $(element);
    			var auth_id = _this.attr("auth_id");
    			var is_show = window.top.shrioAuthObj(auth_id);
    			if(!is_show){
    				_this.remove();
    			}
    		});
    	});
    </script>
</head>
<body style="overflow-y:hidden;">
	<input type="hidden" id="li_type" value="${type }"/>
	<input type="hidden" id="custState" value="${state }" />
	<input type="hidden" id="custId" value="${custId }" />
	<input type="hidden" id="custName" value="${custName }" />
	<input type="hidden" id="custType" value="${custType }" />
	<input type="hidden" id="custStatus" value="${custStatus }" />
	<input type="hidden" id="lifeCode" value="${lifeCode }" />
	<input type="hidden" id="serverDay" value="${shiroUser.serverDay }" />
	<input type="hidden" id="pageTypeId" value="2" />
	<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
	<input type="hidden" id="saleProcs" value="${saleProcs }" />
	<input type="hidden" id="isState" value="${shiroUser.isState }"/>
	<input type="hidden" id="showCount" value="10" />
	<input type="hidden" id="totalResult" value="" />
	<input type="hidden" id="currentPage" value="" />
	<input type="hidden" id="project_path" value="${project_path }" />
	<input type="hidden" id="playUrl" value="${playUrl }" />
	<input type="hidden" id="tabType" value="${type }" />	
	<input type="hidden" id="lastOptionId" value="${lastOptionId }" />	
	<input type="hidden" id="lastOptionName" value="${lastOptionName }" />	
	<div class="hyxCustCard">
		<div class="hyxCustCard_right fl_r">
			<div id="hyxCustCard_head">
				<div class="custCard_head_content">
					<div class="custCard_mark_icon">
						<img class="width-100" src="${ctx }/static/images/icon_state_mark.png" />
						<c:if test="${!empty cycleTime }">
							<div class="dialog-box">
								<div class="down-arrow"></div>
								<div class="dialog-box-content">客户开发周期<span>${cycleTime }</span>天</div>
							</div>
						</c:if>
					</div>
					<div class="custCard_progress_solid"></div>
					<div class="custCard_progress_dashed"></div>
					<div class="custCard_progress_state progress_state_1" >
						<div class="progress-icon-state-big">
							<img class="width-100" src="${ctx }/static/images/icon_state_big.png" />
						</div>
						<div class="font-icon-state-big">资源</div>
					</div>
					<div class="custCard_progress_state progress_state_2" >
						<div class="progress-icon-state-big">
							<img class="width-100" src="${ctx }/static/images/icon_state_big.png" />
						</div>
						<div class="font-icon-state-big">意向</div>
					</div>
					<div class="custCard_progress_state progress_state_3" >
						<!--mark="marked"-->
						<div class="progress-icon-state-big">
							<img class="width-100" src="${ctx }/static/images/icon_state_big.png" />
						</div>
						<div class="font-icon-state-big">签约</div>
					</div>
					<div class="progress_actionTag" total="4"></div>
				</div>
			</div>
			<c:if test="${not empty state && state ne 1 && userGroupType ne 2}">
				<div class="custCard_button_area">
					<button btn-type="auth" auth_id="base_followCust" id="btn_follow" type="button" class="fl_r hyx-btn-w80" style="margin-right:7%;">跟  进</button>
					<c:if test="${((shiroUser.account eq ownerAcc) || (shiroUser.account eq commonAcc && commonOnwerGiveUp eq 1)) && state ne 3 && state ne 4 && state ne 5 && state ne 6}">
						<button btn-type="auth" auth_id="base_putIntoHighSeas" id="btn_giveUp" type="button" class="fl_r hyx-btn-w80" style="margin-right:10px;">放入公海</button>
					</c:if>
					<c:if test="${((state eq 1 || state eq 2) && (ownerAcc eq shiroUser.account || (adminSignAuth eq 1 && commonAcc ne shiroUser.account))) || ( (state eq 1 || state eq 2) && (commonOnwerSign eq 1 && shiroUser.account eq commonAcc))}">
						<button btn-type="auth" auth_id="base_signCust" id="btn_sign" type="button" class="fl_r hyx-btn-w80" style="margin-right:10px;">签  约</button>
					</c:if>
					<c:if test="${state eq 6 }">
						<button id="btn_tao" type="button" class="fl_r hyx-btn-w80" style="margin-right:10px;">淘到客户</button>
					</c:if>
					<button id="btn_cancel" type="button" class="fl_r hyx-btn-w80" style="margin-right:10px;">取  消</button>
					<button id="btn_save" type="button" class="fl_r hyx-btn-w80" style="margin-right:10px;">保  存</button>
				</div>
				<div class="cust_follow_area">
					<iframe id="followFrame" src="${ctx}/cust/card/custFollowPage?custId=${custId}&custType=${state}" style="width:100%; height:330px; border:none;"></iframe>
				</div>
			</c:if>
			
			<div class="custCard_timeline">
				<div class="left_timeline fl_l"></div>
				<div id="lineDiv" class="custCard_timeline_content fl_l"></div>
			</div>
			
			<div class="custCard_weChatRecord">
				<div id='lineDiv' class='custCard_timeline_content fl_l' style='margin-top:100px;'><div class='hyx-mcn-bg'><span>暂无记录！</span></div></div>
			</div>
			<div class="hyx-mca-nonew" id="wechat_record_getmore">
						<span class="line fl_l"></span>
						<label class="fl_l">加载更多</label>
						<span class="line fl_r"></span>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/view/card/cust_info_card.js${_v}"></script>	
</body>
</html>