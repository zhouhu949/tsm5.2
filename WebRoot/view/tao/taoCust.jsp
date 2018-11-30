<%@ page language="java" pageEncoding="UTF-8"%>
<%-- <%@ include file="/view/tsm/resource/include_tao.jsp"%> --%>
<%@ include file="/common/common.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shiroUser", ShiroUtil.getShiroUser());%>
<!DOCTYPE>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/style.css${_v}" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/taoCust_style.css${_v}" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/icon.css${_v}"/><!--树形结构样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/easyui/themes/gray/easyui.css${_v}"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/font-awesome-4.7.0/css/font-awesome.min.css${_v}"/>
	<style type="text/css">
		.taoCust_right{
			display: flex;
			flex-direction: column;
			height:100%;
		}
		.hyx-cfu-timeline .timeline{
			overflow-y: auto;
			overflow-x: hidden;
		}
		.taoCust_right_top{
			height:420px;
		}
		.taoCust_right_bottom{
			height:100%;
		}
		.hyx-cfu-timeline .timeline{
			height:100%;
		}
		#timeline_ul{
			height:auto !important;
		}
		.hyx-wlm-cent-timeline .timeline .cfu-box,.hyx-cfu-timeline .timeline .right,.hyx-cfu-timeline .timeline .cfu-mt{
			float:none;
		}
	</style>
</head>
<body>
    <input type="hidden" id="isSet" value="${isSet }" />
    <input type="hidden" id="callStatUrl" value="${callStatUrl }" />
	<input type="hidden" id="accountName" value="<shiro:principal property="name"/>" />
	<input type="hidden" id="isState" value="<shiro:principal property="isState"/>" />
	<input type="hidden" id="pageTypeId" value="1" />
	<input type="hidden" id="account" value="<shiro:principal property="account"/>" />
	<input type="hidden" id="timeElngth" value="" />
	<input type="hidden" id="orgId" value="<shiro:principal property="orgId"/>" />
	<input type="hidden" id="custId" value="${custId }" />
	<input type="hidden" id="refreshOneId" value="0" />
	<input type="hidden" id="resourceGroupId" name="resourceGroupId" value="" />
	<input type="hidden" id="orderType" name="orderType" value="" />
	<input type="hidden" id="isConcat" name="isConcat" value="" />
	<input type="hidden" id="resIsConcat" name="resIsConcat" />
	<input type="hidden" id="concat_phone" name="concat_phone" value="" />
	<input type="hidden" id="concat_name" name="concat_name" value="" />
	<input type="hidden" id="company" name="company" value="" />
	<input type="hidden" id="custName" name="custName" value="" />
	<input type="hidden" id="concatId" name="concatId" value="" />
	<input type="hidden" id='project_path' value="" />
	<input type="hidden" id='isFromOtherPage' value="" />
	<input type="hidden" id="isReplaceWord" value="" />
	<input type="hidden" id="followId" value="" />
	<input type="hidden" id="autoCallResId" value="" />
	<input type="hidden" id="pool" name="pool" value="" />
	<input type="hidden" id="isAutoCall" name="isAutoCall" value="" />
	<input type="hidden" id="currResId" name="currResId" value="" />
	<input type="hidden" id="signSetting" name="signSetting" value="" />
	<input type="hidden" id="custInfoName" name="custInfoName" value=""/>
	<input type="hidden" id="add_contract" name="add_contract" value="${ addContract}" />
	<input type="hidden" id="nextFollowValidation" name="nextFollowValidation" value="${nextFollowValidation}">
<div class="hyx-layout">
	<div class="taoCust_left hyx-layout-content">

			<div class="card fl_l">
				<p class="p_a">
					<label id="custInfo_name"></label>
					<i data-authority="base_custEdit" class="icon-edit icon-edit-position" title="编辑"></i>

				</p>
				<div class="p_b_div">
					<div class="sty-hid"></div>
					<i class="icon_down" name="down"></i>
				</div>
			</div>

			<div class="card_operate fl_r">
				<div id="card_operate_head_ul" class="card_operate_head"></div>

					<div class="card_operate_person_infor">
						<div class="card_person_infomation_sex card_person_infomation">
							性别：<span></span>
						</div>
						<div class="card_person_infomation_zw card_person_infomation">
							职务：<span></span>
						</div>
						<div class="card_person_infomation_cs card_person_infomation" >
							拨通次数：<span id="list_callnum"></span>次
						</div>
					</div>

					<div class="card_operate_content border-right">
						<div class="operate_content_area">
							常用电话：<span id="custInfo_mobilephone"></span>
							<!-- <m>拨通<b>--</b>次</m> -->
						</div>
						<div class="operate_content_icon border-top">
							<div class="content_icon_size border-right fl_l custInfo_mobilephone chrome_new">
								<img src="${ctx}/static/images/icon_grey_tel.png" src_grey="${ctx}/static/images/icon_grey_tel.png" src_blue="${ctx}/static/images/icon_blue_tel.png" src_white="${ctx}/static/images/icon_white_tel.png"/>
							</div>
							<div class="content_icon_size fl_l custInfo_mobilephone chrome_new">
								<img src="${ctx}/static/images/icon_grey_msg.png" src_grey="${ctx}/static/images/icon_grey_msg.png" src_blue="${ctx}/static/images/icon_blue_msg.png" src_white="${ctx}/static/images/icon_white_msg.png"/>
							</div>
						</div>
					</div>

					<div class="card_operate_content card_operate_content_second border-left border-right">
						<div class="operate_content_area">
							备用电话：<span id="custInfo_telphone"></span>
							<!-- <m>拨通<b>--</b>次</m> -->
						</div>
						<div class="operate_content_icon border-top">
							<div class="content_icon_size border-right fl_l custInfo_telphone chrome_new">
								<img src="${ctx}/static/images/icon_grey_tel.png" src_grey="${ctx}/static/images/icon_grey_tel.png" src_blue="${ctx}/static/images/icon_blue_tel.png" src_white="${ctx}/static/images/icon_white_tel.png"/>
							</div>
							<div class="content_icon_size fl_l custInfo_telphone chrome_new">
								<img src="${ctx}/static/images/icon_grey_msg.png" src_grey="${ctx}/static/images/icon_grey_msg.png" src_blue="${ctx}/static/images/icon_blue_msg.png" src_white="${ctx}/static/images/icon_white_msg.png"/>
							</div>
						</div>
					</div>

					<div class="card_operate_content_table border-left">
						<div class="operate_content_icons border-bottom">
							<div class="content_icon_size content_icon_size_four border-right fl_l custInfo_qq chrome_new">
								<img src="${ctx}/static/images/icon_grey_qq.png" src_grey="${ctx}/static/images/icon_grey_qq.png" src_blue="${ctx}/static/images/icon_blue_qq.png" src_white="${ctx}/static/images/icon_white_qq.png"/>
							</div>
							<div class="content_icon_size content_icon_size_four fl_l custInfo_weChat chrome_new">
								<div id="hidden_wxName"></div>
								<img src="${ctx}/static/images/icon_grey_weChat.png" src_grey="${ctx}/static/images/icon_grey_weChat.png" src_blue="${ctx}/static/images/icon_blue_weChat.png" src_white="${ctx}/static/images/icon_white_weChat.png"/>
								<div class="btn_bind_weChat">关联微信</div>
								<div class="btn_reBind_weChat" title="重新关联">
									<div class="icon_reBind_weChat icon_reBind_weChat_grey"></div>
									<div class="icon_reBind_weChat icon_reBind_weChat_white"></div>
									<div class="icon_reBind_weChat icon_reBind_weChat_blue"></div>
								</div>
							</div>
						</div>
						<div class="operate_content_icons">
							<div class="content_icon_size content_icon_size_four border-right fl_l custInfo_email chrome_new">
								<img src="${ctx}/static/images/icon_grey_mail.png" src_grey="${ctx}/static/images/icon_grey_mail.png" src_blue="${ctx}/static/images/icon_blue_mail.png" src_white="${ctx}/static/images/icon_white_mail.png"/>
							</div>
							<div class="content_icon_size content_icon_size_four fl_l custInfo_wangwang chrome_new">
								<img src="${ctx}/static/images/icon_grey_wangwang.png" src_grey="${ctx}/static/images/icon_grey_wangwang.png" src_blue="${ctx}/static/images/icon_blue_wangwang.png" src_white="${ctx}/static/images/icon_white_wangwang.png"/>
							</div>
						</div>
					</div>
			</div>

			<div class="operate_button fl_r">
				<div class="hyx-sce-left-btn fl_l">
					<div class="drop-box drop-box-agin fl_r">
						<button type="button" class="com-btna com-btna-sty com-btna-no">下次再淘</button>
						<span class="drop nextTao" style="display:none;">
							<label class="arrow"><em>◆</em><span>◆</span></label>
							<label class="box">
								<a href="###" id="updownType_2"><i></i><label title="下一条">下一条</label></a>
								<a href="###" id="zybz" class="demoBtn_c"><i></i><label title="资源备注">资源备注</label></a>
							</label>
						</span>
					</div>
					<div class="drop-box drop-box-a fl_r">
						<button id="delayId" type="button" class="com-btna fl_r delay_call com-btna-no">延后呼叫</button>
						<span class="drop drop-a" style="display:none;">
							<label class="arrow"><em>◆</em><span>◆</span></label>
							<label class="box">
								<label class="lab">
									<span>待呼时间：</span>
									<input type="text" id="waitDate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})" name="waitDate" />
								</label>
								<label class="lab">
									<span>延后原因：</span>
									<label class="hyx-sce-area">
										<textarea class='area_a fl_l' id="delayReason" name="delayReason" maxlength="30" placeholder="最多可输入30个汉字"></textarea>
										<!-- <span class="textarea_tip">最多可输入30个汉字。</span> -->
									</label>
								</label>
								<label class="btn">
									<a href="###" class="com-btnc com-btnc-sty fl_l" id='submitDelayId'>确定</a>
									<a href="###" class="com-btnd fl_r" id="closeDelayId">取消</a>
								</label>
							</label>
						</span>
					</div>
					<div class="drop-box drop-box-b fl_r">
						<button data-authority="base_putIntoHighSeas" id="dealType_4" type="button" class="com-btna fl_r com-btna-no">沟通失败</button>
						<span class="drop" style="display:none;">
							<label class="arrow"><em>◆</em><span>◆</span></label>
							<label class="box"></label>
						</span>
					</div>
					<div class="drop-box drop-box-b fl_r">
						<button data-authority="base_putIntoHighSeas" id="dealType_5" type="button" class="com-btna fl_r com-btna-no">信息错误</button>
						<span class="drop" style="display:none;">
							<label class="arrow"><em>◆</em><span>◆</span></label>
							<label class="box"></label>
						</span>
					</div>
					<button id="updownType_1" type="button" class="com-btna fl_r com-btna-no">上一条</button>
				</div>
			</div>
			<form action="${ctx}/res/tao/addMyCust" id="taoForm" method="post">
				<input type="hidden" value="" name="labelCode" id="labelCodeId">
				<input type="hidden" value="" name="labelName" id="labelNameId">
				<input type="hidden" name="custFollowId" value="" id="custFollowId">
			    <input type="hidden" id="resCustId" name="resCustId" value="${custId}" />
			    <input type="hidden" id="opterType" name="opterType" value="" />
			    <input type="hidden" id="custDetailId" name="custDetailId" value="" />
			    <input type="hidden" id="concatName" name="concatName" value="" />
			    <input type="hidden" id="concatPhone" name="concatPhone" value="" />
			    <input type="hidden" id="resourceGroupId_tao" name="resourceGroupId_tao" value="" />
				<input type="hidden" id="orderType_tao" name="orderType_tao" value="" />
				<input type="hidden" id="isConcat_tao" name="isConcat_tao" value="" />
				<input type="hidden" id="pool_tao" name="pool_tao" value="" />
			    <input type="hidden" id="saleProcessName" name="saleProcessName" value="" />
			    <input type="hidden" id="custInfoName_tao" name="custInfoName_tao" value=""/>
			    <input type="hidden" id="isAddDate" name="isAddDate" value="" />
					<div class="sales_info">
					<div class="hyx-sce-left-form fl_l">
						<div class='bomp_tit'><label class='lab'>销售信息</label></div>
						<div class="sales_info_detail">
							<div class="sales_info_detail_input">
								<div class="bomp-box">
									<p class='bomp-p'>
										<label class='lab_a fl_l'>
											<i class="com-red">*</i>客户类型：
										</label>
										<select class="sel_a fl_l custType_list" name="custTypeId" checkProp="chk_2_1" id="custTypeId">
											<option value="" selected>--请选择--</option>
										</select>
										<span class="error"></span>
									</p>
									<div class='bomp-p' id="bomp_error_suitProcId">
										<label class='lab_a fl_l'><i class="com-red">*</i>适用产品：</label>
										<dl class="select reso-sub-dep" data-multi="true" data-input="[name='suitProcId']">
											<dt data-placeholder="--请选择--">--请选择--</dt>
											<dd>
												<ul id="productTree" ></ul>
											</dd>
										</dl>
											<!-- <div class="manage-drop">
										   		 <div class="sure-cancle clearfix" style="width:120px">
													<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" href="javascript:;" id="setProId"><label>确定</label></a>
													<a class="com-btnd bomp-btna fl_l" id="clearId" href="javascript:;"><label>清空</label></a>
												</div>
											</div> -->
										<input type="hidden" id=suitProcId name="suitProcId" value="" />
										<span class="error" id="error_suitProcId"></span>
									</div>
								</div>
								<div class="bomp-box">
									<p class='bomp-p'>
										<label class='lab_a fl_l'>
											<i class="com-red">*</i>销售进程：
										</label>
										<select name="saleProcessId"  id="saleProcessId" checkProp="chk_2_1"  class="sel_a fl_l saleProcess_list">
											<option value="" selected>--请选择--</option>
										</select>
										<span class="error"></span>
									</p>
									<p class='bomp-p'>
										<label class='lab_a fl_l'>
											<i class="com-red">*</i>联系有效性：
										</label>
										<select id="effectiveness" name="effectiveness" class="sel_a fl_l">
											<option value="1">有效联系</option>
											<option value="0">无效联系</option>
										</select>
									</p>
								</div>
								<div class='bomp-p bomp-p-w actionTag'>
									<label class='lab_a fl_l'>行动标签：</label>
									<div class="hyx-sce-area">
										<div class="tip-box fl_l" style="height:30px">
											<a href="javascript:;" class="choose-tag" style="display:none;">选择标签</a>
											<div class="1sty-hid fl_l"></div>
										</div>
									</div>
									<i class="icon_down icon_down_a fl_l" name="down"></i>
								</div>
								<div class='bomp-p bomp-p-w contact_note'>
									<label class='lab_a fl_l'><i class="com-red">*</i>联系小记：</label>
									<label class="hyx-sce-area">
										<textarea id="feedbackComment" name="feedbackComment"  checkProp="chk_1_1" maxlength="4000"  class='area_a fl_l' placeholder="请输入与客户联系结果，最多可输入2000个汉字"></textarea>
										<span class="error" style="padding:0 !important;min-width:10%;"></span>
										<!-- <span class="tip textarea_tip">请输入与客户联系结果，最多可输入2000个汉字。</span> -->
									</label>
								</div>

			                    <div class="bomp-box">
									<p class='bomp-p'>
										<label class='lab_a fl_l'><c:if test="${nextFollowValidation eq 1 }"><i class="com-red">*</i></c:if>下次联系：</label>
										<select id="followType" class="sel_a fl_l" name="followType" <c:if test="${nextFollowValidation eq 1 }">checkProp="chk_2_1"</c:if>>
												<option value="" ${nextFollowValidation eq 0 ? '' : 'selected' }>-请选择-</option>
												<option value="1" ${nextFollowValidation eq 1 ? 'selected' : '' }>电话联系</option>
												<option value="2">会客联系</option>
												<option value="3">客户来电</option>
												<option value="4">短信联系</option>
												<option value="5">微信联系</option>
										</select>
										<span class="error"></span>
									</p>
									<p class='bomp-p'>
										<label class='lab_a fl_l'><c:if test="${nextFollowValidation eq 1 }"><i class="com-red">*</i></c:if>下次联系时间：</label>
										<input type="text"  name="followDate"  id="followDate" class="ipt date_littleicon"  <c:if test="${nextFollowValidation eq 1 }">checkProp="chk_1_1"</c:if> data-next="${nextFollowValidation }" />
										<span class="error"></span>
									</p>
								</div>
							</div>		
						</div>
							<div class="sales_info_detail_button">
								<div class="com-btnbox hyx-sce-left-btnbox">
									<a href="###" id="taoCustId" class="com-btnb com-btnb-sty fl_l"><label>淘到客户</label></a>
									<a data-authority="base_signCust" href="###" id="signCustId" class="com-btnb fl_r"><label>签&nbsp;&nbsp;约</label></a>
								</div>
							</div>
						</div>
					</div>
			</form>
		</div>

		<div class="taoCust_right hyx-layout-side" style="background-color: inherit;">
			<div class="hyx-sce-right fl_r taoCust_right_top">
				<p class="hyx-sce-right-btn fl_l">
					<label class="lab fl_l">自动连拨：</label><i class="${shiroUser.serverDay gt 0 ?'switch fl_l':'switch hyx-switch-no fl_l '}  ${isAutoCall eq 1 ?'': ' click-hover' }" name="on"></i>
					<button type="button"  class="${shiroUser.serverDay gt 0 ? 'right com-btna demoBtn_a fl_r ' : 'right com-btna-no  fl_r' }" id="add_res">
						<i class="${shiroUser.serverDay gt 0 ? 'min-add-resou' : 'min-add-resou img-gray'}"></i><label class="lab-mar">增加资源</label></a>
					</button>
					<button type="button" class="right com-btna demoBtn_d fl_r margin_right_5px">
						<i class="min-word-temp"></i><label class="lab-mar">话术模板</label>
					</button>
				</p>

				<div class="com-search hyx-sce-right-search fl_l width_100">
					<div class="com-search-box fl_l width_100">
						<ul class="taoCust_pools fl_l width_100">
							<li name="1" class="active">待呼叫资源</li>
							<li name="2" class="" >延后呼叫</li>
						</ul>
						<div class="list-box width_100" id="resPoolId">

						<!--表格上面的三个排序方法  -->
							<dl class="select" style="z-index: 1;width: 30%">
							<dt  class="shows-linshi"><span class="select_text"></span><span class="icon-down-arrow"></span></dt>
								<%-- <c:if test="${!empty groupList }">
									<c:choose>
										<c:when test="${resourceGroupId eq 'today'}">
											<dt>今日计划<span class="icon-down-arrow"></span></dt>
										</c:when>
										<c:when test="${resourceGroupId eq 'temp'}">
											<dt>临时计划<span class="icon-down-arrow"></span></dt>
										</c:when>
										<c:when test="${resourceGroupId eq 'all' or resourceGroupId eq '' or empty resourceGroupId }">
											<dt>全部资源<span class="icon-down-arrow"></span></dt>
										</c:when>
									</c:choose>
									<c:set var="dt" value="0" />
									<c:forEach items="${groupList}" var="group" varStatus="vs">
										<c:if test="${resourceGroupId eq group.resGroupId}">
											<dt>${group.groupName }<span class="icon-down-arrow"></span></dt>
											<c:set var="dt" value="1" />
										</c:if>
									</c:forEach>
									<c:if test="${  resourceGroupId ne 'today' && resourceGroupId ne 'temp' && resourceGroupId ne 'all' && resourceGroupId ne '' && !empty resourceGroupId  && dt eq '0'}">
									    <dt>全部资源<span class="icon-down-arrow"></span></dt>
									</c:if>
								</c:if> --%>
							<%-- 	<c:if test="${empty groupList }">
									<c:choose>
										<c:when test="${resourceGroupId eq 'today'}">
											<dt>今日计划</dt>
										</c:when>
										<c:when test="${resourceGroupId eq 'temp'}">
											<dt>临时计划</dt>
										</c:when>
										<c:otherwise>
											<dt>全部资源</dt>
										</c:otherwise>
									</c:choose>
								</c:if> --%>
								<dd style="display: none;" class="">
									<ul class="groupList_ul shows-ul-linshi">
										<li><a href="###" title="今日计划" select_res_id="today">今日计划</a></li>
		                               <li class="groupList_all_resource"><a href="###" title="全部资源" select_res_id="all">全部资源</a></li>

										<!-- 可排序项数多时的循环 这里需要js循环插入 -->
										<%-- <c:if test="${!empty groupList }">
											<c:forEach items="${groupList}" var="group" varStatus="vs">
												<li><a href="###" title="${group.groupName}" onclick="selectRes('${group.resGroupId}')">${group.groupName}</a></li>
											</c:forEach>
										</c:if> --%>
									</ul>
								</dd>
							</dl>

							<!--2  -->
							<dl class="select" style="z-index: 1;width: 40%">
							<dt  class="shows-fenpei"><span class="select_text"></span><span class="icon-down-arrow"></span></dt>
								<%-- <c:choose>
									<c:when test="${orderType eq 'owner_start_date desc'}">
										<dt>分配时间倒序排序<span class="icon-down-arrow"></span></dt>
									</c:when>
									<c:when test="${orderType eq 'owner_start_date asc'}">
										<dt>分配时间顺序排序<span class="icon-down-arrow"></span></dt>
									</c:when>
									<c:when test="${orderType eq 'is_precedence desc'}">
										<dt>个人优选资源排序</dt>
									</c:when>
									<c:otherwise>
										<dt>分配时间倒序排序<span class="icon-down-arrow"></span></dt>
									</c:otherwise>
								</c:choose> --%>
								<dd style="display: none;">
									<ul class="shows-select-order">
										<li><a href="###" title="分分配时间倒序排序"  select_order_id="owner_start_date desc">分配时间倒序排序</a></li>
										<li><a href="###" title="分配时间升序排列" select_order_id="owner_start_date asc">分配时间顺序排序</a></li>
										<li><a href="###" title="个人优选资源排序"  select_order_id="is_precedence desc">个人优选资源排序</a></li>
									</ul>
								</dd>
							</dl>

							<!--3  -->
							<dl class="select" style="z-index: 1;width: 30%">
							<dt style="border-right: none !important;" class="shows-contact"><span class="select_text"></span><span class="icon-down-arrow"></span></dt>
								<%-- <c:choose>
									<c:when test="${isConcat eq '2'}">
										<dt style="border-right: none !important;">全部<span class="icon-down-arrow"></span></dt>
									</c:when>
									<c:when test="${isConcat eq '0'}">
										<dt style="border-right: none !important;">未联系<span class="icon-down-arrow"></span></dt>
									</c:when>
									<c:when test="${isConcat eq '1'}">
										<dt style="border-right: none !important;">已联系<span class="icon-down-arrow"></span></dt>
									</c:when>
									<c:otherwise>
										<dt style="border-right: none !important;">全部<span class="icon-down-arrow"></span></dt>
									</c:otherwise>
								</c:choose> --%>
								<dd style="display: none;">
									<ul class="shows-contact-ye">
										<li><a href="###" title="全部"  contact_id="2">全部</a></li>
										<li><a href="###" title="未联系"  contact_id="0">未联系</a></li>
										<li><a href="###" title="已联系"  contact_id="1">已联系</a></li>
									</ul>
								</dd>
							</dl>
							<img id="freshCache" src="${ctx}/static/images/Share_change.png" title="刷新"/>
						</div>


						<div class="list-box width_100" id="willPoolId" style="display:none">
							<dl class="select" style="z-index: 1;width: 100%">
										<dt>延后呼叫记录的最晚保留时间是延期时间的24点</dt>
							</dl>
<%-- 							<img id="freshCache" src="${ctx}/static/images/Share_change.png"> --%>
						</div>
					</div>
				</div>
							   <div class="com-table hyx-sce-right-table fl_l resource_table">
									<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
										<thead>
											<tr class="sty-bgcolor-b">
												<th style="border: 1px solid #e6e6e6; border-left: none;" class="th_dyName">客户名称</th>
												<th style="border: 1px solid #e6e6e6; border-right: none;border-left: none;" class="th_dyMoblie">联系电话</th>
											</tr>
										</thead>
									<tbody>
											<tr style="height:310px;">
												<td colspan="5">当前列表无数据</td>
											</tr>
									</tbody>
								</table>
							</div>
							<div class="com-table hyx-sce-right-table fl_l delay_table" style="display:none;">
								<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius" >
									<thead>
										<tr class="sty-bgcolor-b">
											<th style="border: 1px solid #e6e6e6; border-left: none;">延期时间</th>
											<th style="border: 1px solid #e6e6e6; border-left: none;" class="th_dyName">客户名称</th>
											<th style="border: 1px solid #e6e6e6; border-right: none;">延后原因</th>
										</tr>
									</thead>
									<tbody>
										<tr style="height:310px;">
											<td colspan="5">当前列表无数据</td>
										</tr>
									</tbody>
								</table>
							</div>
			</div>


			<div class="hyx-wlm-cent-timeline hyx-cfu-timeline taoCust_right_bottom" style="margin:0;display: flex; flex-direction: column;margin-top:10px;">
				<div class="taoCust_right_bottom_head">
					<span>资源备注</span>
					<span class="timeline_more" id="moreFollowId">更多</span>
				</div>
				<div class="timeline">
					<div class="timeline_icon_grey_empty" style="top:0;"></div>
					<!-- <div class="time_line"></div> -->
					<ul id="timeline_ul" style="height:100%;">

					</ul>
					<div class="timeline_icon_grey_empty_end" style="position: static; float: left; margin-left: 16px;"></div>
				</div>
			</div>
		</div>
	</div>
		<iframe style="display:none;" class="qq_iframe" src=""></iframe>

		<script type="text/javascript"  src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/thirdparty/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js${_v}" dialog-theme="default"></script><!--可移动弹框插件-->
		<script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js${_v}"></script><!--可移动弹框插件公共js-->
		<script type="text/javascript" src="${ctx}/static/js/common/common.js${_v}"></script>
		<script type="text/javascript" src="${ctx}/static/js/common/common_business.js${_v}"></script>
		<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
		<script type="text/javascript" src="${ctx}/static/js/view/res/phoneEncrpyt.js${_v}"></script>
		<script type="text/javascript" src="${ctx}/static/js/idialog/loading.js${_v}"></script><!-- 等待页面加载遮盖层 -->
		<%-- <script type="text/javascript" src="${ctx}/static/js/view/tao/taoCust.js"></script> --%>
		<%-- <script type="text/javascript" src="${ctx}/static/js/form.js${_v}"></script> --%>
		<script type="text/javascript"  src="${ctx}/static/js/view/tao/tao_data.js${_v}"></script>
		<script type="text/javascript"  src="${ctx}/static/js/view/tao/tao_style.js${_v}"></script>
		<script type="text/javascript" src="${ctx}/static/js/my97datepicker/WdatePicker.js${_v}"></script>
        <script type="text/javascript" src="${ctx}/static/js/view/res/custGuideCheck.js${_v}"></script>
        <script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery.form.js${_v}"></script>
        <script type="text/javascript" src="${ctx}/static/js/common/hack_common.js${_v}"></script>
        <script type="text/javascript" src="${ctx}/static/js/base64.js${_v}"></script>
        <script type="text/javascript"  src="${ctx}/static/js/view/tao/bind_wx.js${_v}"></script>
        <script type="text/javascript" src="${ctx }/static/js/common/button_auth.js${_v}"></script>
</body>
</html>
