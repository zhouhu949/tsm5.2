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
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/time/css/daterangepicker.css${_v}" /><!--选择区域日期插件样式-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/font-awesome-4.7.0/css/font-awesome.min.css${_v}"/>
</head>
<body>
		<input type="hidden" id="isSet" value="${isSet }" />
		<!-- 1:全部，2:意向客户，3:签约客户，4:流失客户，5:沉默客户 ,6:客户跟进,7:共有客户-->
		<input type="hidden"  id="custType" name="custListType" value="${custListType }" />
		<!-- 跟进数据分类 -->
		<input type="hidden" id="pageTypeId" value="3" /><!-- 用于 edit_cust.jsp 保存，区别其他同类操作 -->
		<input type="hidden"  id="custCation" name="custCation" value="${custCation }" />
		<input type="hidden"  id="state" name="state" value="${state }" /><!-- 客户类型：0：个人客户，1：企业客户 -->
		<input type="hidden" id="isAlarm" name="isAlarm" value="${isAlarm}"  /><!-- 有值 表示从跟进警报页面点击触发 -->
		<input type="hidden" id="startPage" name="startPage" value="${startPage}"  /><!-- 起始行号，用于跟进警报排序  -->
		<input type="hidden"  id="isSelect" name="isSelect" value="${isSelect }" />
		<input type="hidden"  id="next_custId"  value="${custId }" /><!-- 资源ID -->
		<input  type="hidden" name="planParam" id="planParam" value="${planParam}" /><!-- 1：只显示计划中客户，2：不显示计划中客户 -->
		<input type = "hidden" id="last_cust_type"><!-- 最近跟进客户类型ID -->
		<input type = "hidden" id="last_option_id"><!-- 最近跟进销售进程ID -->
		<input type="hidden" id="concat_phone" name="concat_phone" value="" />
		<input type="hidden" id="concat_name" name="concat_name" value="" />
		<input type="hidden"  id="cust_name" name="cust_name" value="" /><!-- 客户名称-->
		<input type="hidden"  id="cust_type" name="cust_type" value="" /><!-- 客户类型 -->
		<input type="hidden"  id="cust_status" name="cust_status" value="" /><!-- 客户状态 -->
		<input type="hidden"  id="cust_company" name="cust_company" value="" /><!-- 个人版 单位名称 -->
		<input type="hidden" id="idReplaceWord" value="${idReplaceWord}" />
		<input type="hidden" id="playUrl" value="${playUrl}">
		<input type="hidden" id="project_path" value="${project_path}">
		<input type="hidden" id="concatId" name="concatId" value="" />
		<input type="hidden" id="isConcat"  value="" />
		<input type="hidden" id="taoStartDate" name="taoStartDate" value="" />
		<input type="hidden" id="taoEndDate" name="taoEndDate" value="" />
		<input type="hidden" id="com_putinto_seas" name="com_putinto_seas" value="${ comPutIntoSeas}" />
		<input type="hidden" id="com_sign" name="com_sign" value="${ comSign}" />
		<input type="hidden" id="add_contract" name="add_contract" value="${ addContract}" />
		<input type="hidden" id="nextContactValidation" name="nextContactValidation" value="${ nextContactValidation}" />
		<!-- 默认当前是第1页；默认一页显示15条 -->
		<input type="hidden" id="pp_totalResult" name="page.totalResult" value="" />
		<input type="hidden" id="pp_showCount" name="page.showCount" value="15" />
		<input type="hidden" id="pp_currentPage" name="page.currentPage" value="1" />
		
	<div class="hyx-layout">

		<div class="taoCust_left hyx-layout-content">
			<div class="card fl_l">
				<p class="p_a">
					<label id="custInfo_name"></label>
					<shiro:hasPermission name="base_custEdit"> <!-- 编辑权限 -->
						<i class="icon-edit icon-edit-position" title="编辑"></i>
					</shiro:hasPermission>
				</p>
				<div class="p_b_div">
					<div class="sty-hid"></div>
					<i class="icon_down" name="down"></i>
				</div>
			</div>

			<div class="card_operate fl_r">
				<div id="card_operate_head_ul" class="card_operate_head"></div>

				<div class="card_operate_person_infor" style="display:none;">
					<div class="card_person_infomation_sex card_person_infomation">
						性别：<span></span>
					</div>
					<div class="card_person_infomation_zw card_person_infomation">
						职务：<span></span>
					</div>
					<!-- <div class="card_person_infomation_cs card_person_infomation" >
						拨通次数：<span>--</span>次
					</div> -->
				</div>

				<div class="card_operate_content border-right">
					<div class="operate_content_area">
						常用电话：<span id="custInfo_mobilephone"></span>
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

			<form id="myForm" method="post" action="${ctx }/cust/custFollow/saveCustFollow.do">
				<input type="hidden"  id="custId" name="custId" value="${custId }"><!-- 资源ID -->
				<input type="hidden" id="followId" name="custFollow.custFollowId" value="${followId }">
				<input type="hidden" id="main_tscName" name="custFollow.custDetailName" value="${name}"> <!-- 联系人 企业客户会有值 -->
				<input type="hidden" id="main_tscMobile" name="custFollow.custDetailMobile"><!-- 联系人号码 企业客户会有值 -->
				<input type="hidden" value="${lastCustFollow.labelCode }" name="custFollow.labelCode" id="labelCodeId">
				<input type="hidden" value="${lastCustFollow.labelName }" name="custFollow.labelName" id="labelNameId">
				<input type="hidden" id="isAddDate" name="isAddDate" value="" />
				<input type="hidden" id="signSetting"  name="signSetting"  value="${signSetting}" />
				<input type="hidden"  id="lastSaleProcessId" name="custFollow.lastSaleProcessId" value=""><!--赋值上次销售进程ID -->
				<input type="hidden"  id="custGuideId" name="custGuide.custGuideId"  value="">
				<input type="hidden" name="custGiveUp" id="custGiveUp" ><!--有值 表示是放弃操作 -->
				<input type="hidden" name="custSign" id="custSign" ><!--有值 表示是签约操作 -->

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
										<select class="sel_a fl_l custType_list" name="custGuide.custTypeId" checkProp="chk_2" id="custTypeId">
											<option value="" selected>--请选择--</option>
										</select>
										<span class="error" id="error_custTypeId"></span>
									</p>
									<div class='bomp-p' id="bomp_error_suitProcId">
										<input type="hidden" id="suitProcId" name ="suitProcId" value="${procIds }" checkProp="chk_"/><!-- 适用产品 -->
										<label class='lab_a fl_l'><i class="com-red">*</i>适用产品：</label>
										<dl class="select reso-sub-dep" data-multi="true" data-input="[name='suitProcId']">
											<dt data-placeholder="--请选择--">--请选择--</dt>
											<dd>
												<ul id="productTree" ></ul>
											</dd>
										</dl>
										<span class="error" id="error_suitProcId"></span>
									</div>
								</div>

								<div class="bomp-box">
									<p class='bomp-p' id="bomp_error_actionType">
										<label class='lab_a fl_l'><i class="com-red">*</i>联系方式：</label>
										<select class="sel_a fl_l" name="custFollow.actionType" id="actionType" checkProp="chk_2">
											<option value="1" selected>电话联系</option>
											<option value="2">会客联系</option>
											<option value="3">客户来电</option>
											<option value="4">短信联系</option>
											<option value="5">QQ联系</option>
											<option value="6">邮件联系</option>
										</select>
										<span class="error" id="error_actionType"></span>
									</p>
									<div class='bomp-p' id="bomp_error_actionDate">
										<label class='lab_a fl_l'><i class="com-red">*</i>联系时间：</label>
										<input type="text" id="actionDate" name="custFollow.actionDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d',readOnly:true})"  class="ipt"  checkProp="chk_" readOnly="readonly"/>
										<span class="error" id="error_actionDate"></span>
									</div>
								</div>

								<div class="bomp-box">
									<p class='bomp-p'>
										<label class='lab_a fl_l'>
											<i class="com-red">*</i>销售进程：
										</label>
										<select name="custFollow.saleProcessId"  id="saleProcessId" checkProp="chk_2"  class="sel_a fl_l saleProcess_list">
											<option value="" selected>--请选择--</option>
										</select>
										<span class="error" id="error_saleProcessId"></span>
									</p>
									<p class='bomp-p'>
										<label class='lab_a fl_l'>
											<i class="com-red">*</i>联系有效性：
										</label>
										<select id="effectiveness" name="custFollow.effectiveness" class="sel_a fl_l" checkProp="chk_2">
											<option value="1">有效联系</option>
											<option value="0">无效联系</option>
										</select>
										<span class="error" id="error_effectiveness"></span>
									</p>
								</div>
								<div class='bomp-p bomp-p-w actionTag'>
									<label class='lab_a fl_l'>行动标签：</label>
									<div class="hyx-sce-area">
										<div class="tip-box fl_l">
											<a href="javascript:;" class="choose-tag" style="display:none;">选择标签</a>
											<div class="1sty-hid fl_l"></div>
										</div>
									</div>
									<i class="icon_down icon_down_a fl_l" name="down"></i>
								</div>
								<div class='bomp-p bomp-p-w contact_note'>
									<label class='lab_a fl_l'><i class="com-red">*</i>联系小记：</label>
									<label class="hyx-sce-area">
										<textarea id="feedbackComment" name="custFollow.feedbackComment" maxlength="4000"  checkProp="chk_1_1" class='area_a fl_l' placeholder="请输入与客户联系结果，最多可输入2000个汉字。"></textarea>
										<!-- <span class="tip textarea_tip">请输入与客户联系结果，最多可输入2000个汉字。</span> -->
									</label>
									<span class="error" id="error_feedbackComment" style="padding-left: 14%;min-width:10%;"></span>
								</div>
								<div class="bomp-box">
									<p class='bomp-p'>
										<label class='lab_a fl_l'>
											<c:if test="${nextContactValidation eq 1 }"><i class="com-red">*</i></c:if>下次联系：
										</label>
										<select id="followType" class="sel_a fl_l" name="custFollow.followType" data-select-value="${nextContactValidation }" <c:if test="${nextContactValidation eq 1 }">checkProp="chk_2"</c:if>>
												<option value="" ${(nextContactValidation eq 0) ? 'selected' : ''}>-请选择-</option>
												<option value="1" ${(nextContactValidation eq 1) ? 'selected' : ''}>电话联系</option>
												<option value="2">会客联系</option>
												<option value="3">客户来电</option>
												<option value="4">短信联系</option>
												<option value="5">QQ联系</option>
												<option value="6">邮件联系</option>
										</select>
										<span class="error" id="error_followType"></span>
									</p>
									<p class='bomp-p' id="bomp_error_followDate">
										<label class='lab_a fl_l'><c:if test="${nextContactValidation eq 1 }"><i class="com-red">*</i></c:if>下次联系时间：</label>
										<input type="text"  name="custFollow.followDate"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d',readOnly:true})" id="followDate" class="ipt"  <c:if test="${nextContactValidation eq 1 }">checkProp="chk_"</c:if> readOnly="readonly"/>
										<!-- <select class="sel_a fl_l"><option>默认：1天后 12-13 01:01:01</option></select> -->
										<span class="error" id="error_followDate"></span>
									</p>
								</div>
								
								<div class="bomp-box">
									<p class='bomp-p'>
										<label class='lab_a fl_l'>关联销售机会：</label>
										<select id="saleChanceId" class="sel_a fl_l" name="custFollow.saleChanceId" ></select>
									</p>
								</div>
								
							<div class="com-btnbox hyx-sce-left-btnbox" style="width:310px;">
								<a data-authority="base_followCust" href="javascript:;"  id="saveBut"  class="com-btnb com-btnb-sty fl_l" >保存并继续</a>


								<a data-authority="base_putIntoHighSeas" href="javascript:;"  id="canelBut"  class="com-btnb fl_l" style="margin-left:8px;display:none;">放入公海</a>


								<a data-authority="base_signCust" href="javascript:;" id="signCustId" class="com-btnb fl_r" style="display:none;">签&nbsp;&nbsp;约</a>
							</div>
						</div>
					</div>
				</div>
					</div>
			</form>
		</div>

		<div class="taoCust_right hyx-layout-side">
				<div class=" hyx-sce-right-search">
					<%-- <div class="com-search-box fl_l width_100">
						<div class="list-box width_100" id="willPoolId" style="${ pool eq '2' ? 'display:block':'display:none'}">
							<dl class="select" style="z-index: 1;width: 100%">
										<dt>延后呼叫记录的最晚保留时间是延期当日的24点</dt>
							</dl>
							<img id="freshCache" src="${ctx}/static/images/Share_change.png">
						</div>
					</div> --%>
				</div>
				<div class="com-table hyx-sce-right-table fl_l" style="background-color:#fff;">
                    <div class="easyui-tabs custfollow_tabs" id="follow_tabs">
                        <div title="待跟进客户">
                            <div style="width:100%; position: relative; <c:if test="${isAlarm eq 1 }">display: none;</c:if>">
                                <ul class="button-area">
                                    <li class="function-button btn-screen" >筛选</li>
                                    <li class="function-button btn-sort" >排序</li>
                                </ul>
                            </div>
                            <table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius right_wait_table">
                                <thead>
                                <tr style="border-top: 1px solid #e6e6e6;border-bottom: 1px solid #e6e6e6;">
                                    <th class="sort-field">下次联系时间</th>
                                    <c:choose>
                                        <c:when test="${state eq 1}">
                                            <th>客户名称</th>
                                        </c:when>
                                        <c:otherwise>
                                            <th>客户姓名</th>
                                        </c:otherwise>
                                    </c:choose>

                                    <th>联系电话</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td colspan="5">当前列表暂无数据</td>
                                </tr>
                                <tr><td colspan="5"></td></tr>
                                <tr><td colspan="5"></td></tr>
                                <tr><td colspan="5"></td></tr>
                                <tr><td colspan="5"></td></tr>
                                </tbody>
                            </table>
                            <div class="table-bottom-area">
                            	 <div class="table-check-area">
	                            	<label><input type="checkbox" name="isContact" value="1" checked="checked" />排除今日已联系</label>
	                            </div>
	                            <div class="table-button-area">
	                            	<button type="button" class="btn btn-sm btn-default btn-prev">上一页</button>
	                            	<span class="show-count">1</span>
	                            	<button type="button" class="btn btn-sm btn-default btn-next">下一页</button>
	                            </div>
                            </div>
                        </div>
                        <div title="跟进记录">
							<div class="hyx-wlm-cent-timeline hyx-cfu-timeline taoCust_right_bottom" style="padding-top: 25px;">
								<span class="timeline_more" id="moreFollowId">更多</span>
								<div class="timeline">
									<div class="timeline_icon_grey_empty"></div>
									<!-- <div class="time_line"></div> -->
									<ul id="timeline_ul" style="margin-top: 10px;">

									</ul>
									<div class="timeline_icon_grey_empty_end" style="position: static; float: left; margin-left: 16px;"></div>
								</div>
							</div>
                        </div>
                    </div>
				</div>
			<div class="hidden-function-area area-screen">
				<ul class="left-area-ul">
					<li class="active">客户范围</li>
					<li>客户类型</li>
					<li>销售进程</li>
					<li>资源分组</li>
					<li>淘到时间</li>
				</ul>
				<ul class="right-area-ul active">
					<li>全部<input type="radio" name="planParam" value="2" /><span class="check-mark fa fa-check"></span></li>
					<li>今日待联系<input type="radio" name="planParam" value="1" /><span class="check-mark fa fa-check"></span></li>
				</ul>
				<ul class="right-area-ul custTypeList">
					<li class="active">全部<input type="radio" name="last_cust_type" value="" checked="checked" /><span class="check-mark fa fa-check"></span></li>
				</ul>
				<ul class="right-area-ul saleProcessList">
					<li class="active">全部<input type="radio" name="last_option_id" value="" checked="checked" /><span class="check-mark fa fa-check"></span></li>
				</ul>
				<ul class="right-area-ul resGroupList">
					<li class="active">全部<input type="radio" name="resGroupId" value="" checked="checked" /><span class="check-mark fa fa-check"></span></li>
				</ul>
				<ul class="right-area-ul">
					<li>全部<input type="radio" name="tDateType" value="0" /><span class="check-mark fa fa-check"></span></li>
					<li>当天<input type="radio" name="tDateType" value="1" /><span class="check-mark fa fa-check"></span></li>
					<li>本周<input type="radio" name="tDateType" value="2" /><span class="check-mark fa fa-check"></span></li>
					<li>本月<input type="radio" name="tDateType" value="3" /><span class="check-mark fa fa-check"></span></li>
					<li>半年<input type="radio" name="tDateType" value="4" /><span class="check-mark fa fa-check"></span></li>
					<li class="dateRange">自定义<input type="radio" name="tDateType" value="5" /><span class="dateValueInit"></span><span class="check-mark fa fa-check"></span></li>
				</ul>
				<div class="bottom-button-area">
					<button type="button" class="btn btn-default btn-reset">重置</button>
					<button type="button" class="btn btn-submit">确定</button>
				</div>
			</div>

			<div class="hidden-function-area area-sort">
				<ul class="area-ul">
					<li  class="active">按下次联系时间正序排列<input type="radio" name="orderType" value="1" checked="checked"  /><span class="check-mark fa fa-check"></span></li>
					<li>按下次联系时间倒序排列<input type="radio" name="orderType" value="2" /><span class="check-mark fa fa-check"></span></li>
					<li>按最近联系时间正序排列<input type="radio" name="orderType" value="3" /><span class="check-mark fa fa-check"></span></li>
					<!-- <li>按最近联系时间倒序排列<input type="radio" name="orderType" value="4" /><span class="check-mark fa fa-check"></span></li>
					<li>按淘到客户时间正序排列<input type="radio" name="orderType" value="5" /><span class="check-mark fa fa-check"></span></li>
					<li>按淘到客户时间倒序排列<input type="radio" name="orderType" value="6" /><span class="check-mark fa fa-check"></span></li> -->
				</ul>
				<div class="bottom-button-area">
					<button type="button" class="btn btn-default btn-cancel">取消</button>
					<button type="button" class="btn btn-submit">确定</button>
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
		<script type="text/javascript" src="${ctx}/static/js/form.js${_v}"></script>
		<script type="text/javascript"  src="${ctx}/static/js/view/follow/follow_style.js${_v}"></script>
		<script type="text/javascript"  src="${ctx}/static/js/view/follow/follow_data.js${_v}"></script>
		<script type="text/javascript" src="${ctx}/static/js/my97datepicker/WdatePicker.js${_v}"></script>
        <%-- <script type="text/javascript" src="${ctx}/static/js/view/res/custGuideCheck.js${_v}"></script> --%>
        <script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery.form.js${_v}"></script>
        <script type="text/javascript" src="${ctx}/static/js/common/hack_common.js${_v}"></script>
        <script type="text/javascript" src="${ctx}/static/js/base64.js${_v}"></script>
       	<script type="text/javascript" src="${ctx}/static/js/view/tao/bind_wx.js${_v}"></script>
       	<script type="text/javascript" src="${ctx}/static/js/time/moment.min.js"></script><!--选择区域日期插件-->
		<script type="text/javascript" src="${ctx}/static/js/time/jquery.daterangepicker.js"></script><!--选择区域日期插件-->
		<script type="text/javascript" src="${ctx }/static/js/common/button_auth.js${_v}"></script>
<script>
	    setTimeout(function(){
			
            $(".taoCust_right .custfollow_tabs .timeline").height($(".taoCust_right .custfollow_tabs .tabs-panels").height()-25)
		},500);
</script>
</body>
</html>
