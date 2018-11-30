<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
<title>系统设置-销售管理设置</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/salesmanage.css${_v}"/>
<script type="text/javascript" src="${ctx}/static/js/jquery.cookie.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/view/tsm/option/salesmanage.js${_v}"></script>

</head>
<body class="clearfix">
<c:if test="${ systemoperateids == 1 }">
	<div class="shade-box"></div>
</c:if> 

<div class="pending-box" style="display:none;">
	<div class="tip_search_text">
		<span><img alt="处理中" src="/static/images/hua_loading.gif"></span>
		<span>处理中,请稍后...</span>
	</div>
</div>

<div class="new-salesmanage-page clearfix">
	<div class="new-salesmanage-page-head">
		<ul class="new-salesmanage-page-changer">
			<div class="li_first selects" id="routine_setting_changer">常规设置</div>
			<div class="li_last" id="special_setting_changer">特殊设置</div>
		</ul>
	</div>
	<ul class="new-salesmanage-page-tabs">
	<form id="myForm" method="post" action="${ctx}/sales/manage/salesmanage">
		<div class="routine_setting_changer-tabs hyx-sms">
		<!--  常规设置-->
		<!-- 资源回收设置 -->
		<p class="tit">
			<input type="hide" id="dic_0" name="dictionaryList[0].dictionaryValue" value="${dictionaryList[0].dictionaryValue}">
			<label class="lab fl_l"><i class="tip"></i>资源回收设置</label>
			<i class="switch fl_r ${dictionaryList[0].dictionaryValue eq '0'?'switch-hover':''}"  id="on_0" name="${dictionaryList[0].dictionaryValue eq '1'?'on':'off'}"  ></i>
		</p>
		<div class="switch-hid" id="div_0" ${dictionaryList[0].dictionaryValue eq '0'?'style="display:none;"':''}>
			<p class="tit_b">
				<input type="radio"  class="check recycling-radio"  name="dictionaryList[71].dictionaryValue" ${dictionaryList[71].dictionaryValue ne '2' ?'checked':''} value="1"/>
				<label class="lab" >分配到帐号的资源</label>
				<input type="text" name="dictionaryList[4].dictionaryValue" value="${dictionaryList[4].dictionaryValue}"  ${dictionaryList[71].dictionaryValue eq '2' ?'disabled':''}  maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt" />
				<label class="lab">天未转化，直接回收到待分资源中。回收天数从资源分配时间点开始计时。</label>
			</p>
			<p class="tit_b">
				<input type="radio" class="check recycling-radio"  name="dictionaryList[71].dictionaryValue"  ${dictionaryList[71].dictionaryValue eq '2' ?'checked':''} value="2"/>
				<label class="lab" >分配到帐号的资源</label>
				<input type="text" name="dictionaryList[72].dictionaryValue"  ${dictionaryList[71].dictionaryValue ne '2' ?'disabled':''}  value="${dictionaryList[72].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt" />
				<label class="lab">天未联系，将自动回收到</label>
				<select class="sel newselect"  name="dictionaryList[73].dictionaryValue"  ${dictionaryList[71].dictionaryValue ne '2' ?'disabled':''}>
						<option value="1" ${dictionaryList[73].dictionaryValue eq '1' ? 'selected':'' }>待分配资源</option>
		            	<option value="2" ${dictionaryList[73].dictionaryValue eq '2' ? 'selected':'' }>公海客户池</option>
				</select>
				<label class="lab">中。</label>
			</p>
			<p class="tit_b">
				<label class="lab padingleft15" >（回收天数从最近联系时间点开始计时，当最近联系时间为空时从分配时间点开始计时）</label>
			</p>
		</div>
		<!-- 资源去重设置 -->
		<p class="tit">
			<input type="hide" id="dic_1" name="dictionaryList[1].dictionaryValue" value="${dictionaryList[1].dictionaryValue}">
			<label class="lab fl_l"><i class="tip"></i>资源去重设置</label>
			<i class="switch fl_r ${dictionaryList[1].dictionaryValue eq '0'?'switch-hover':''}"  id="on_1" name="${dictionaryList[1].dictionaryValue eq '1'?'on':'off'}" ></i>
		</p>
		<div class="switch-hid" id="div_1"  ${dictionaryList[1].dictionaryValue eq '0'?'style="display:none;"':''}>
			<p class="tit_b">
				<input type="checkbox" id="r_5" ${dictionaryList[5].dictionaryValue  gt '1'?'checked':''} class="check" />
				<label class="lab">对</label>
				<select class="sel sel_a" id="dictionaryList_5" ${dictionaryList[5].dictionaryValue eq '1'?'disabled':''} name="dictionaryList[5].dictionaryValue">
						<option value="2" ${dictionaryList[5].dictionaryValue eq '2' ? 'selected':'' }>签约用户</option>
		            	<option value="3" ${dictionaryList[5].dictionaryValue eq '3' ? 'selected':'' }>意向用户</option>
		            	<option value="4" ${dictionaryList[5].dictionaryValue eq '4' ? 'selected':'' }>所有资源</option>
				</select>
				<label class="lab">电话号码进行去重保护</label>
			</p>
			<p class="tit_b">
				<input type="checkbox" id="r_6" ${dictionaryList[6].dictionaryValue  gt '0'?'checked':''} class="check" />
				<label class="lab">对</label>
				<select class="sel sel_a" id="dictionaryList_6" ${dictionaryList[6].dictionaryValue eq '0'?'disabled':''} name="dictionaryList[6].dictionaryValue">
					<option value="1" ${dictionaryList[6].dictionaryValue eq '1' ? 'selected':'' }>签约用户</option>
		            <option value="2" ${dictionaryList[6].dictionaryValue eq '2' ? 'selected':'' }>意向用户</option>
		            <option value="3" ${dictionaryList[6].dictionaryValue eq '3' ? 'selected':'' }>所有资源</option>
				</select>
				<c:choose>
					<c:when test="${shrioUser.isState eq 1}">
						<label class="lab">客户名称进行去重保护</label>
					</c:when>
					<c:otherwise>
						<label class="lab">单位名称进行去重保护</label>
					</c:otherwise>
				</c:choose>
			</p>
			<p class="tit_b">
				<input type="checkbox" id="r_15" ${dictionaryList[15].dictionaryValue  gt '0'?'checked':''} class="check" />
				<label class="lab">对</label>
				<select class="sel sel_a" id="dictionaryList_15" ${dictionaryList[15].dictionaryValue eq '0'?'disabled':''} name="dictionaryList[15].dictionaryValue">
					<option value="1" ${dictionaryList[15].dictionaryValue eq '1' ? 'selected':'' }>签约用户</option>
		            <option value="2" ${dictionaryList[15].dictionaryValue eq '2' ? 'selected':'' }>意向用户</option>
		            <option value="3" ${dictionaryList[15].dictionaryValue eq '3' ? 'selected':'' }>所有资源</option>
				</select>
				<label class="lab">公司网站进行去重保护</label>
			</p>
		</div>
		<!-- 资源安全设置 -->
		<p class="tit">
			<label class="lab fl_l"><i class="tip"></i>资源安全设置</label>
		</p>
		<div class="posirela">
			<p class="tit_b">
				<input type="hide" id="formart_17" name="dictionaryList[17].dictionaryValue" value="${dictionaryList[17].dictionaryValue}">
				<label class="lab"><input type="checkbox" id="rr_17"  ${dictionaryList[17].dictionaryValue eq 1 ? 'checked':''} class="check" >电话号码中间4位用"*"作模糊处理</label>
			</p>
			<p class="tit_b">
				<input type="hide" id="formart_43"  name="dictionaryList[43].isOpen"  value="${(empty dictionaryList[43].isOpen)||(dictionaryList[43].isOpen eq '0') ? '0':'1'}"/>
				<label class="lab" ><input type="checkbox" id="rr_43"  ${dictionaryList[43].isOpen eq 1 ? 'checked':''} class="check" >添加号码隐藏白名单（列表中帐号看到的电话号码不做隐藏处理）</label><button type="button" class="white_mingdan">+添加名单</button>
			</p>
			<p class="tit_c">
					<label class="lab zhangsans" ></label>
			</p>
			<p class="tit_b">
				<input type="hide" id="formart_44"  name="dictionaryList[44].isOpen"  value="${(empty dictionaryList[44].isOpen)||(dictionaryList[44].isOpen eq '0') ? '0':'1'}"/>
				<label class="lab" ><input type="checkbox" id="rr_44"  ${dictionaryList[44].isOpen eq 1 ? 'checked':''} class="check" >添加客户信息只读白名单（列表中帐号可以读写客户信息）</label><button type="button" class="black_mingdan">+添加名单</button>
			</p>
			<p class="tit_c">
					<label class="lab zhangsana" ></label>
			</p>
			<!-- 只有单位管理员 才可以设置 -->
			<div  ${isAdmin eq '1' ? '':'style="display:none;"'}>
				<p class="tit_b">
						<input type="hide" id="formart_103"  name="dictionaryList[103].isOpen"  value="${(empty dictionaryList[103].isOpen)||(dictionaryList[103].isOpen eq '0') ? '0':'1'}"/>
						<label class="lab" ><input type="checkbox" id="rr_103"  ${dictionaryList[103].isOpen eq 1 && isAdmin eq '1' ? 'checked':''}  class="check cust-info-output-setting" >客户信息导出审核设置（导出人需填写发往列表内帐号的验证码信息才允许导出数据）</label>
					</p>
					<p class="tit_b padingleft15">
						<label class="lab add-check-man-label" ><i class="must">*</i>手机号码：</label><input type="text"  name="dictionaryList[103].dictionaryValue"  value="${dictionaryList[103].dictionaryValue }"  class="check-man-mustbe"  placeholeder="请输入手机号码" ${dictionaryList[103].isOpen ne 1 ? 'disabled':''}>
						<span class="reminder"></span>
					</p>
					<div class="tit_b padingleft15 check-man-box">
						<input type="hide" id="useraccount" name="dictionaryList[104].dictionaryValue"  value="${dictionaryList[104].dictionaryValue }" />
						<label class="lab add-check-man-label" >审核人：</label><input type="text" class="check-man-setting" id="usertext"  placeholeder="请输入审核人" ${dictionaryList[103].isOpen ne 1 ? 'disabled':''}>
						<div class="manage-drop fl_l" id="manage_drop">
								<ul id="tt1">
	
								</ul>
								<div class="sure-cancle clearfix" style="width: 120px">
									<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l"
														href="javascript:;" id="checkedId"><label>确定</label></a>
									<a	class="com-btnd bomp-btna fl_l reso-sub-clear" id="clearId"
														href="javascript:;"><label>清空</label></a>
								</div>
						</div>
					</div>
			</div>
			<div class="tree-drop1 baimingdan">
								<div class="tree-drop-ul">
									<ul class="easyui-tree"  data-options="
										url:'${ctx}/orgGroup/get_all_group_user_json',
										animate:false,
										dnd:false,
										checkbox:true">
									</ul>
								</div>
								<div class="sure-cancle clearfix" >
									<button type="button" class="submit_rights submit_right">确定</button>
									<button type="button" class="clean_rights clean_right">清空</button>
								</div>
							</div>
			<div class="tree-drop1 heimingdan">
								<div class="tree-drop-ul">
									<ul class="easyui-tree"  data-options="
										url:'${ctx}/orgGroup/get_all_group_user_json',
										animate:false,
										dnd:false,
										checkbox:true">
									</ul>
								</div>
								<div class="sure-cancle clearfix" >
									<button type="button" class="submit_righta submit_right">确定</button>
									<button type="button" class="clean_righta clean_right">清空</button>
								</div>
							</div>
			<input type="hide" id="bai_numval"  value="${baiNumval}">
			<input type="hide" id="bai_numid" name="dictionaryList[43].dictionaryValue" value="${dictionaryList[43].dictionaryValue}">
			<input type="hide" id="hei_numval"  value="${heiNumval}">
			<input type="hide" id="hei_numid"  name="dictionaryList[44].dictionaryValue" value="${dictionaryList[44].dictionaryValue}">
		</div>
		<!-- 客户跟进设置 -->
		<p class="tit">
			<input type="hide" id="dic_2" name="dictionaryList[2].dictionaryValue" value="${dictionaryList[2].dictionaryValue}">
			<label class="lab fl_l"><i class="tip"></i>客户跟进设置</label>
			<i class="switch fl_r ${dictionaryList[2].dictionaryValue eq '0'?'switch-hover':''}" id="on_2" name="${dictionaryList[2].dictionaryValue eq '1'?'on':'off'}" ></i>
		</p>
		<div class="switch-hid" id="div_2"  ${dictionaryList[2].dictionaryValue eq '0'?'style="display:none;"':''}>
			<p class="tit_b">
				<input type="checkbox" id="r_13"  name="dictionaryList[13].isOpen" value="${dictionaryList[13].isOpen}"  ${dictionaryList[13].isOpen gt '0'?'checked':'' } class="check" />
				<label class="lab">下次联系时间必填，跟进客户默认时间为</label>
				<input type="text" id="dictionaryList_13" name="dictionaryList[13].dictionaryValue" value="${dictionaryList[13].dictionaryValue}" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt" />
				<select class="sel" id="dictionaryList_14"  name="dictionaryList[14].dictionaryValue">
					<option value="0" ${dictionaryList[14].dictionaryValue eq '0' ? 'selected':'' }>天</option>
					<option value="1" ${dictionaryList[14].dictionaryValue eq '1' ? 'selected':'' }>小时</option>
					<option value="2" ${dictionaryList[14].dictionaryValue eq '2' ? 'selected':'' }>分钟</option>
					<option value="3" ${dictionaryList[14].dictionaryValue eq '3' ? 'selected':'' }>秒</option>
				</select>
				<label class="lab">以后。(勾选后销售信息中下次联系时间与下次联系方式必填)</label>
			</p>
			<p class="tit_b">
				<input type="checkbox" id="r_16" name="dictionaryList[16].isOpen" ${(empty dictionaryList[16].isOpen)||(dictionaryList[16].isOpen eq '0') ? '':'checked'} class="check" />
				<label class="lab">意向客户超过</label>
				<input type="text" id="dictionaryList_16" ${(empty dictionaryList[16].isOpen)||(dictionaryList[16].isOpen eq '0') ? 'disabled':''} name="dictionaryList[16].dictionaryValue" value="${dictionaryList[16].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt willCust-num-overwrite" />
				<label class="lab">天未跟进，将自动回收到公海客户池中。</label>
				<span class="fetur_to_follow">详细设置</span>
				<label class="lab">（按销售进程设置）</label>
				<input type="hide" id="fetur_to_follow_input" name="dictionaryList[48].dictionaryValue" value="${dictionaryList[48].dictionaryValue }">
			</p>
			<p class="tit_b">
				<input type="checkbox"  id="r_18" name="dictionaryList[18].isOpen" ${(empty dictionaryList[18].isOpen)||(dictionaryList[18].isOpen eq '0') ? '':'checked'} class="check" />
				<label class="lab">允许个人拥有资源上限</label>
				<input type="text" id="dictionaryList_18" ${(empty dictionaryList[18].isOpen)||(dictionaryList[18].isOpen eq '0') ? 'disabled':''} name="dictionaryList[18].dictionaryValue" value="${dictionaryList[18].dictionaryValue}" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt res-max-number" />
				<label class="lab">人，到达上限将提示操作人员并限制添加客户操作。</label>
			</p>
			<!--
			<p class="tit_b">
				<input type="checkbox"id="r27" name="dictionaryList[27].isOpen" ${(empty dictionaryList[27].isOpen)||(dictionaryList[27].isOpen eq '0') ? '':'checked'} class="check" />
				<label class="lab">当个人资源数少于</label>
				<input type="text" id="dictionaryList_27" ${(empty dictionaryList[27].isOpen)||(dictionaryList[27].isOpen eq '0') ? 'disabled':''} name="dictionaryList[27].dictionaryValue" value="${dictionaryList[27].dictionaryValue}" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt" />
				<label class="lab">人，系统自动分配资源，每次分配</label>
				<input type="text" id="dictionaryList_28" ${(empty dictionaryList[27].isOpen)||(dictionaryList[27].isOpen eq '0') ? 'disabled':''} name="dictionaryList[28].dictionaryValue" value="${dictionaryList[28].dictionaryValue}" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt" />
				<label class="lab">人。</label>
			</p>  -->
			<p class="tit_b">
				<input type="checkbox"  id="r_9" name="dictionaryList[9].isOpen" ${(empty dictionaryList[9].isOpen)||(dictionaryList[9].isOpen eq '0') ? '':'checked'} class="check" />
				<label class="lab">允许个人拥有意向客户上限</label>
				<input type="text" id="dictionaryList_9" ${(empty dictionaryList[9].isOpen)||(dictionaryList[9].isOpen eq '0') ? 'disabled':''} name="dictionaryList[9].dictionaryValue" value="${dictionaryList[9].dictionaryValue}" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt personal-willcust-max" />
				<label class="lab">人，到达上限将提示操作人员并限制转为意向操作。</label>
			</p>
			<p class="tit_b">
				<input type="checkbox" id="r_7" name="dictionaryList[7].isOpen" ${(empty dictionaryList[7].isOpen)||(dictionaryList[7].isOpen eq '0') ? '':'checked'} class="check" />
				<label class="lab">允许完成客户签单的最长期限</label>
				<input type="text" id="dictionaryList_7" ${(empty dictionaryList[7].isOpen)||(dictionaryList[7].isOpen eq '0') ? 'disabled':''} name="dictionaryList[7].dictionaryValue" value="${dictionaryList[7].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt sign-time-max" />
				<label class="lab">天，如超期回收到公海客户池中。</label>
			</p>
		</div>
		<!-- 有效呼叫设置 -->
		<p class="tit">
			<input type="hide" id="dic_3" name="dictionaryList[3].dictionaryValue" value="${dictionaryList[3].dictionaryValue}">
			<label class="lab fl_l"><i class="tip"></i>有效呼叫设置</label>
			<i class="switch fl_r ${dictionaryList[3].dictionaryValue eq '0'?'switch-hover':''}" id="on_3" name="${dictionaryList[3].dictionaryValue eq '1'?'on':'off'}"></i>
		</p>
		<div class="switch-hid" id="div_3"  ${dictionaryList[3].dictionaryValue eq '0'?'style="display:none;"':''}>
			<p class="tit_b">
				<label class="lab padingleft15" >电话接通</label>
				<input type="text" name="dictionaryList[10].dictionaryValue" value="${dictionaryList[10].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt" />
				<label class="lab">秒后，视为有效通话。</label>
			</p>
		</div>
		<!-- 定时分配通信币设置 -->
		<p class="tit">
			<input type="hide" id="dic_40" name="dictionaryList[40].dictionaryValue" value="${dictionaryList[40].dictionaryValue}">
			<label class="lab fl_l"><i class="tip"></i>定时分配通信币设置</label>
			<i class="switch fl_r ${dictionaryList[40].dictionaryValue eq '0'?'switch-hover':''}" id="on_40" name="${dictionaryList[40].dictionaryValue eq '1'?'on':'off'}" ></i>
		</p>
		<div class="switch-hid" id="div_40"  ${dictionaryList[40].dictionaryValue eq '0'?'style="display:none;"':''}>
			<p class="tit_b posirela" >
				<label class="lab padingleft15" style="width:168px;">每个帐号通信币低于的临界值：</label>
				<select id="dictionaryList_41" class="ipt1 linjie" maxlength="4" >
				  <option value="500"  <c:if test="${dictionaryList[41].dictionaryValue eq '500'}">selected</c:if>>500</option>
				 <option value="1000" <c:if test="${dictionaryList[41].dictionaryValue eq '1000'}">selected</c:if>>1000</option>
				 <option value="2000" <c:if test="${dictionaryList[41].dictionaryValue eq '2000'}">selected</c:if>>2000</option>
				 <option value="其他" <c:if test="${dictionaryList[41].dictionaryValue ne '500' and dictionaryList[41].dictionaryValue ne '1000' and dictionaryList[41].dictionaryValue ne '2000'}">selected</c:if>>其他</option>
				 </select>
				 <input class="ipt2"   style="display:none;"  type="text"  id="linjie_num1" name="dictionaryList[41].dictionaryValue" value="${dictionaryList[41].dictionaryValue}" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onblur="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/><span class="linjie-span" style="display:none;">输入整数</span>
			</p>
			<p class="tit_b posirela" >
				<label class="lab padingleft15" >每个帐号每次定量分配的通信币：</label>
				<select  id="dictionaryList_42"  class="ipt1 shichang" maxlength="4" >
					<option value="3000" <c:if test="${dictionaryList[42].dictionaryValue eq '3000'}">selected</c:if>>3000</option>
					<option value="5000" <c:if test="${dictionaryList[42].dictionaryValue eq '5000'}">selected</c:if>>5000</option>
					<option value="10000" <c:if test="${dictionaryList[42].dictionaryValue eq '10000'}">selected</c:if>>10000</option>
					<option value="20000" <c:if test="${dictionaryList[42].dictionaryValue eq '20000'}">selected</c:if>>20000</option>
					<option value="50000" <c:if test="${dictionaryList[42].dictionaryValue eq '50000'}">selected</c:if>>50000</option>
					<%-- <option value="其他" <c:if test="${dictionaryList[42].dictionaryValue ne '3000' and dictionaryList[42].dictionaryValue ne '5000' and dictionaryList[42].dictionaryValue ne '10000' and dictionaryList[42].dictionaryValue ne '20000' and dictionaryList[42].dictionaryValue ne '50000'}">selected</c:if>>其他</option> --%>
				</select>
				<input class="ipt2"  style="display:none;" type="text"   id="shichang_num1" name="dictionaryList[42].dictionaryValue" value="${dictionaryList[42].dictionaryValue}" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onblur="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/><span class="shichang-span" style="display:none;">输入整数</span>
			</p>
		</div>
		<!-- 客户信息字段格式校验设置 -->
		<p class="tit">
			<input type="hide" id="dic_19" name="dictionaryList[19].dictionaryValue" value="${dictionaryList[19].dictionaryValue}">
			<label class="lab fl_l"><i class="tip"></i>客户信息字段格式校验设置</label>
			<i class="switch fl_r ${dictionaryList[19].dictionaryValue eq '0'?'switch-hover':''}" id="on_19" name="${dictionaryList[19].dictionaryValue eq '1'?'on':'off'}"></i>
		</p>
		<div class="switch-hid" id="div_19"  ${dictionaryList[19].dictionaryValue eq '0'?'style="display:none;"':''}>
			<p class="tit_a">
				<input type="hide" id="formart_20"  name="dictionaryList[20].dictionaryValue"  value="${(empty dictionaryList[20].dictionaryValue)||(dictionaryList[20].dictionaryValue eq '0') ? '0':'1'}"/>
				<input type="hide" id="formart_21"  name="dictionaryList[21].dictionaryValue"  value="${(empty dictionaryList[21].dictionaryValue)||(dictionaryList[21].dictionaryValue eq '0') ? '0':'1'}"/>
				<input type="hide" id="formart_22"  name="dictionaryList[22].dictionaryValue"  value="${(empty dictionaryList[22].dictionaryValue)||(dictionaryList[22].dictionaryValue eq '0') ? '0':'1'}"/>
				<input type="hide" id="formart_23"  name="dictionaryList[23].dictionaryValue"  value="${(empty dictionaryList[23].dictionaryValue)||(dictionaryList[23].dictionaryValue eq '0') ? '0':'1'}"/>
				<input type="hide" id="formart_24"  name="dictionaryList[24].dictionaryValue"  value="${(empty dictionaryList[24].dictionaryValue)||(dictionaryList[24].dictionaryValue eq '0') ? '0':'1'}"/>
				<input type="checkbox" id="rr_20"  ${(empty dictionaryList[20].dictionaryValue)||(dictionaryList[20].dictionaryValue eq '0') ? '':'checked'} class="radio"><label class="lab">常用电话</label>
				<input type="checkbox" id="rr_21"  ${(empty dictionaryList[21].dictionaryValue)||(dictionaryList[21].dictionaryValue eq '0') ? '':'checked'} class="radio"><label class="lab">备用电话</label>
				<!-- <input type="checkbox" id="rr_22"  ${(empty dictionaryList[22].dictionaryValue)||(dictionaryList[22].dictionaryValue eq '0') ? '':'checked'} class="radio"><label class="lab">备用电话2</label> -->
				<input type="checkbox" id="rr_23"  ${(empty dictionaryList[23].dictionaryValue)||(dictionaryList[23].dictionaryValue eq '0') ? '':'checked'} class="radio"><label class="lab">传真</label>
				<input type="checkbox" id="rr_24"  ${(empty dictionaryList[24].dictionaryValue)||(dictionaryList[24].dictionaryValue eq '0') ? '':'checked'} class="radio"><label class="lab">邮箱</label>
			</p>
		</div>
		<!-- 数据默认显示设置 -->
		<p class="tit">
			<label class="lab fl_l"><i class="tip"></i>数据默认显示设置</label>
		</p>
		<p class="tit_a">
			<input type="radio" name="dictionaryList[30].dictionaryValue" value="10" ${dictionaryList[30].dictionaryValue ne '20' and dictionaryList[30].dictionaryValue ne '30'?'checked':''} class="radio"><label class="lab">10条</label>
			<input type="radio" name="dictionaryList[30].dictionaryValue" value="20" ${dictionaryList[30].dictionaryValue eq '20'?'checked':''} class="radio"><label class="lab">20条</label>
			<input type="radio"	name="dictionaryList[30].dictionaryValue" value="30"  ${dictionaryList[30].dictionaryValue eq '30'?'checked':''}  class="radio"><label class="lab">30条</label>
		</p>
		<!-- 客户防骚扰设置 -->
		<p class="tit">
			<input type="hide" id="dic_25" name="dictionaryList[25].dictionaryValue" value="${dictionaryList[25].dictionaryValue}">
			<label class="lab fl_l"><i class="tip"></i>客户防骚扰设置</label>
			<i class="switch fl_r ${dictionaryList[25].dictionaryValue eq '0'?'switch-hover':''}" id="on_25" name="${dictionaryList[25].dictionaryValue eq '1'?'on':'off'}"></i>
		</p>
		<div class="switch-hid" id="div_25"  ${dictionaryList[25].dictionaryValue eq '0'?'style="display:none;"':''}>
			<p class="tit_a">
				<label class="lab fl_l padingleft15" >客户防骚扰选择</label>
				<input type="radio" name="dictionaryList[26].dictionaryValue" value="1" ${dictionaryList[26].dictionaryValue eq '1'?'checked':''} class="radio"><label class="lab">意向客户</label>
				<input type="radio" name="dictionaryList[26].dictionaryValue" value="0" ${dictionaryList[26].dictionaryValue eq '0'?'checked':''}  class="radio"><label class="lab">签约客户</label>
			</p>	
			<p class="tit_b">
				<input type="hide" id="formart_63"  name="dictionaryList[63].isOpen"  value="${(empty dictionaryList[63].isOpen)||(dictionaryList[63].isOpen eq '0') ? '0':'1'}"/>
				<input type="checkbox" id="rr_63"  ${(empty dictionaryList[63].isOpen)||(dictionaryList[63].isOpen eq '0') ? '':'checked'} class="check">
				<label class="lab">公海客户防骚扰设置，允许同一公海客户每日被联系次数为</label>
				<input type="text"  id="dictionaryList_63" name="dictionaryList[63].dictionaryValue" value="${ dictionaryList[63].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt minvalue-notequel-zero" />
				<label class="lab">次，超出次数后将限制销售员对该公海客户进行联系</label>
			</p>
		</div>
		<!-- 行动完成率基数设置 -->
		<p class="tit">
			<label class="lab fl_l"><i class="tip"></i>行动完成率基数设置</label>
		</p>
		<p class="tit_b">
			<label class="lab padingleft15">每日的通话时长达标值</label>
			<input type="text"  name="dictionaryList[52].dictionaryValue" value="${dictionaryList[52].dictionaryValue }" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt minvalue-notequel-zero" />
			<label class="lab">分钟</label>
			<input type="hide" id="formart_53"  name="dictionaryList[53].dictionaryValue"  value="${(empty dictionaryList[53].dictionaryValue)||(dictionaryList[53].dictionaryValue eq '0') ? '0':'1'}"/>
			<input type="checkbox"  id="rr_53"  ${(empty dictionaryList[53].dictionaryValue)||(dictionaryList[53].dictionaryValue eq '0') ? '':'checked'}  class="radio action-finish"><label class="lab">呼入时长</label>
			<input type="hide" id="formart_54"  name="dictionaryList[54].dictionaryValue"  value="${(empty dictionaryList[54].dictionaryValue)||(dictionaryList[54].dictionaryValue eq '0') ? '0':'1'}"/>
			<input type="checkbox" id="rr_54"  ${(empty dictionaryList[54].dictionaryValue)||(dictionaryList[54].dictionaryValue eq '0') ? '':'checked'}  class="radio action-finish"><label class="lab">呼出时长</label>
		</p>
		<p class="tit_b">
			<label class="lab padingleft15">每日接通的通话次数达标值</label>
			<input type="text"  name="dictionaryList[55].dictionaryValue" value="${dictionaryList[55].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt minvalue-notequel-zero" />
			<label class="lab">次</label>
		</p>
		<p class="tit_b">
			<input type="hide" id="formart_51"  name="dictionaryList[51].dictionaryValue"  value="${(empty dictionaryList[51].dictionaryValue)||(dictionaryList[51].dictionaryValue eq '0') ? '0':'1'}"/>
			<input type="checkbox"  id="rr_51" ${(empty dictionaryList[51].dictionaryValue)||(dictionaryList[51].dictionaryValue eq '0') ? '':'checked'}   class="check actionbtn" />
			<label class="lab">启用指标系数占比</label>
		</p>
			<p class="tit_b actionContent" ${(empty dictionaryList[51].dictionaryValue)||(dictionaryList[51].dictionaryValue eq '0') ? 'style="display:none;"':''}>
				<label class="lab padingleft15">通话时长指标系数：</label>
				<input type="text"  name="dictionaryList[56].dictionaryValue" value="${dictionaryList[56].dictionaryValue }" maxlength="3" onkeyup="this.value=this.value.replace(/[^0-9\.]/g, '');" onblur="this.value=this.value.replace(/[^0-9\.]/g, '');" class="ipt action-finish-input" />
				<label class="lab padingleft15">接通次数指标系数：</label>
				<input type="text"  name="dictionaryList[57].dictionaryValue" value="${dictionaryList[57].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/[^0-9\.]/g, '');" onblur="this.value=this.value.replace(/[^0-9\.]/g, '');" class="ipt action-finish-input" />
				<label class="lab">（通话时长系数占比+接通次数系数占比之和等于1）</label>
			</p>
			<p class="tit_b tit_spacial padingleft15 actionContent"  ${(empty dictionaryList[51].dictionaryValue)||(dictionaryList[51].dictionaryValue eq '0') ? 'style="display:none;"':''}>
				行动完成率的计算公式={(每日呼出已接次数/每日呼出已接次数达标值)*接通次数指标系数+（每日通话时长/每日通话时长达标值）*通话时长指标系数}*100%
			</p>	
			<!-- 弹幕设置 -->
			<p class="tit">
				<input type="hide" id="dic_80" name="dictionaryList[80].dictionaryValue" value="${dictionaryList[80].dictionaryValue }">
				<label class="lab fl_l"><i class="tip"></i>弹幕设置</label>
				<i class="switch fl_r ${dictionaryList[80].dictionaryValue eq '0'?'switch-hover':''}" id="on_80" name="${dictionaryList[80].dictionaryValue eq '1'?'on':'off'}"></i>
			</p>
			<div class="switch-hid" id="div_80"  ${dictionaryList[80].dictionaryValue eq 0? 'style="display:none;"' : '' }>
				<p class="tit_b posirela">
					<label class="lab padingleft15">生日弹幕:</label>
					<span class="openAndClose-radio-Box">
						<label class="lab"><input type="radio" class="radio-change-visitity-btn-1" name="dictionaryList[81].dictionaryValue" ${dictionaryList[81].dictionaryValue ne '0' ?'checked':''} value="1" />开启</label>
						<label class="lab"><input type="radio" class="radio-change-visitity-btn-1"  name="dictionaryList[81].dictionaryValue" ${dictionaryList[81].dictionaryValue eq '0' ?'checked':''} value="0"/>关闭</label>
					</span>
				</p>
			<div class="radio-change-visitity-1" ${dictionaryList[81].dictionaryValue eq '0' ?'style="display:none;"':''}>
				<p class="tit_b padingleft15">
					自动弹出生日祝福时间设置：
					<input type="text" name="dictionaryList[82].dictionaryValue"  value="${dictionaryList[82].dictionaryValue}" class="auto-birthday-tips auto-birthday-out-wav" />
				</p>
				<p class="tit_b padingleft15">
					为生日成员送上公司奖励红包，红包额度设置
					<select id="dictionaryList_83"  class="select-choose-others-input-show">
						<option value="0" <c:if test="${dictionaryList[83].dictionaryValue eq '0'}">selected</c:if>>无</option>
						<option value="8" <c:if test="${dictionaryList[83].dictionaryValue eq '8'}">selected</c:if>>8元</option>
						<option value="18" <c:if test="${dictionaryList[83].dictionaryValue eq '18'}">selected</c:if>>18元</option>
						<option value="28" <c:if test="${dictionaryList[83].dictionaryValue eq '28'}">selected</c:if>>28元</option>
						<option value="38" <c:if test="${dictionaryList[83].dictionaryValue eq '38'}">selected</c:if>>38元</option>
						<option value="48" <c:if test="${dictionaryList[83].dictionaryValue eq '48'}">selected</c:if>>48元</option>
						<option value="58" <c:if test="${dictionaryList[83].dictionaryValue eq '58'}">selected</c:if>>58元</option>
						<option value="" <c:if test="${dictionaryList[83].dictionaryValue ne '0' and dictionaryList[83].dictionaryValue ne '8' and dictionaryList[83].dictionaryValue ne '18' and dictionaryList[83].dictionaryValue ne '28' and dictionaryList[83].dictionaryValue ne '38' and dictionaryList[83].dictionaryValue ne '48' and dictionaryList[83].dictionaryValue ne '58'}">selected</c:if>>其他</option>
					</select>
					<input type="text"  value="${dictionaryList[83].dictionaryValue}"  name="dictionaryList[83].dictionaryValue" placeholder="红包金额不能大于200元"  class="gift-to-somebody-balance-setting"  <c:if test="${dictionaryList[83].dictionaryValue eq '0' or dictionaryList[83].dictionaryValue eq '8' or dictionaryList[83].dictionaryValue eq '18' or dictionaryList[83].dictionaryValue eq '28' or dictionaryList[83].dictionaryValue eq '38' or dictionaryList[83].dictionaryValue eq '48' or dictionaryList[83].dictionaryValue eq '58'}">style="display:none;"</c:if>/>
				</p>
			</div>
			<p class="tit_b line-between-every-setting"></p>
			<p class="tit_b posirela">
				<label class="lab padingleft15">月度冠军弹幕:</label>
				<span class="openAndClose-radio-Box">
					<label class="lab"><input type="radio" class="radio-change-visitity-btn-2"  name="dictionaryList[84].dictionaryValue" ${dictionaryList[84].dictionaryValue ne '0' ?'checked':''} value="1" />开启</label>
					<label class="lab"><input type="radio" class="radio-change-visitity-btn-2"  name="dictionaryList[84].dictionaryValue" ${dictionaryList[84].dictionaryValue eq '0' ?'checked':''} value="0"/>关闭</label>
				</span>
			</p>
			<div class="radio-change-visitity-2" ${dictionaryList[84].dictionaryValue eq '0' ?'style="display:none;"':''}>
				<p class="tit_b padingleft15">
					<label class="lab">荣誉榜弹幕弹出时间为每月的第一个</label>
					<select class="w70 fl_l" id="dictionaryList_85" name="dictionaryList[85].dictionaryValue">
						<option value="2" ${dictionaryList[85].dictionaryValue eq '2' ? 'selected':'' }>星期一</option>
						<option value="3" ${dictionaryList[85].dictionaryValue eq '3' ? 'selected':'' }>星期二</option>
						<option value="4" ${dictionaryList[85].dictionaryValue eq '4' ? 'selected':'' }>星期三</option>
						<option value="5" ${dictionaryList[85].dictionaryValue eq '5' ? 'selected':'' }>星期四</option>
						<option value="6" ${dictionaryList[85].dictionaryValue eq '6' ? 'selected':'' }>星期五</option>
						<option value="7" ${dictionaryList[85].dictionaryValue eq '7' ? 'selected':'' }>星期六</option>
						<option value="1" ${dictionaryList[85].dictionaryValue eq '1' ? 'selected':'' }>星期日</option>
					</select>
					<label class="lab padingleft15">的早上9：45发送荣誉榜弹幕</label>
				</p>
				<p class="tit_b padingleft15">
					<input type="hide" id="formart_86"  name="dictionaryList[86].isOpen"  value="${(empty dictionaryList[86].isOpen)||(dictionaryList[86].isOpen eq '0') ? '0':'1'}"/>
					<input type="checkbox" id="rr_86"  class="month-champion-check" ${(empty dictionaryList[86].isOpen)||(dictionaryList[86].isOpen eq '0') ? '':'checked'}/>
					月度签约金额冠军展示，公司奖励红包额度设置
					<select id="dictionaryList_86"   class="select-choose-others-input-show">
						<option value="0" <c:if test="${dictionaryList[86].dictionaryValue eq '0'}">selected</c:if>>无</option>
						<option value="8" <c:if test="${dictionaryList[86].dictionaryValue eq '8'}">selected</c:if>>8元</option>
						<option value="18" <c:if test="${dictionaryList[86].dictionaryValue eq '18'}">selected</c:if>>18元</option>
						<option value="28" <c:if test="${dictionaryList[86].dictionaryValue eq '28'}">selected</c:if>>28元</option>
						<option value="38" <c:if test="${dictionaryList[86].dictionaryValue eq '38'}">selected</c:if>>38元</option>
						<option value="48" <c:if test="${dictionaryList[86].dictionaryValue eq '48'}">selected</c:if>>48元</option>
						<option value="58" <c:if test="${dictionaryList[86].dictionaryValue eq '58'}">selected</c:if>>58元</option>
						<option value="" <c:if test="${dictionaryList[86].dictionaryValue ne '0' and dictionaryList[86].dictionaryValue ne '8' and dictionaryList[86].dictionaryValue ne '18' and dictionaryList[86].dictionaryValue ne '28' and dictionaryList[86].dictionaryValue ne '38' and dictionaryList[86].dictionaryValue ne '48' and dictionaryList[86].dictionaryValue ne '58'}">selected</c:if>>其他</option>
					</select>
					<input type="text" value="${dictionaryList[86].dictionaryValue}" name="dictionaryList[86].dictionaryValue" placeholder="红包金额不能大于200元"  class="gift-to-somebody-balance-setting"   <c:if test="${dictionaryList[86].dictionaryValue eq '0' or dictionaryList[86].dictionaryValue eq '8' or dictionaryList[86].dictionaryValue eq '18' or dictionaryList[86].dictionaryValue eq '28' or dictionaryList[86].dictionaryValue eq '38' or dictionaryList[86].dictionaryValue eq '48' or dictionaryList[86].dictionaryValue eq '58'}">style="display:none;"</c:if>/>
				</p>
				<p class="tit_b padingleft15">
					<input type="hide" id="formart_87"  name="dictionaryList[87].isOpen"  value="${(empty dictionaryList[87].isOpen)||(dictionaryList[87].isOpen eq '0') ? '0':'1'}"/>
					<input type="checkbox" id="rr_87" class="month-champion-check"  ${(empty dictionaryList[87].isOpen)||(dictionaryList[87].isOpen eq '0') ? '':'checked'}/>
					月度新增意向冠军展示，公司奖励红包额度设置
					<select id="dictionaryList_87"   class="select-choose-others-input-show">
						<option value="0"<c:if test="${dictionaryList[87].dictionaryValue eq '0'}">selected</c:if>>无</option>
						<option value="8"<c:if test="${dictionaryList[87].dictionaryValue eq '8'}">selected</c:if>>8元</option>
						<option value="18"<c:if test="${dictionaryList[87].dictionaryValue eq '18'}">selected</c:if>>18元</option>
						<option value="28"<c:if test="${dictionaryList[87].dictionaryValue eq '28'}">selected</c:if>>28元</option>
						<option value="38"<c:if test="${dictionaryList[87].dictionaryValue eq '38'}">selected</c:if>>38元</option>
						<option value="48"<c:if test="${dictionaryList[87].dictionaryValue eq '48'}">selected</c:if>>48元</option>
						<option value="58"<c:if test="${dictionaryList[87].dictionaryValue eq '58'}">selected</c:if>>58元</option>
						<option value=""<c:if test="${dictionaryList[87].dictionaryValue ne '0' and dictionaryList[87].dictionaryValue ne '8' and dictionaryList[87].dictionaryValue ne '18' and dictionaryList[87].dictionaryValue ne '28' and dictionaryList[87].dictionaryValue ne '38' and dictionaryList[87].dictionaryValue ne '48' and dictionaryList[87].dictionaryValue ne '58'}">selected</c:if>>其他</option>
					</select>
					<input type="text" value="${dictionaryList[87].dictionaryValue}" name="dictionaryList[87].dictionaryValue" placeholder="红包金额不能大于200元"  class="gift-to-somebody-balance-setting"   <c:if test="${dictionaryList[87].dictionaryValue eq '0' or dictionaryList[87].dictionaryValue eq '8' or dictionaryList[87].dictionaryValue eq '18' or dictionaryList[87].dictionaryValue eq '28' or dictionaryList[87].dictionaryValue eq '38' or dictionaryList[87].dictionaryValue eq '48' or dictionaryList[87].dictionaryValue eq '58'}">style="display:none;"</c:if>/>
				</p>
				<p class="tit_b padingleft15">
					<input type="hide" id="formart_88"  name="dictionaryList[88].isOpen"  value="${(empty dictionaryList[88].isOpen)||(dictionaryList[88].isOpen eq '0') ? '0':'1'}"/>
					<input type="checkbox" id="rr_88" class="month-champion-check"   ${(empty dictionaryList[88].isOpen)||(dictionaryList[88].isOpen eq '0') ? '':'checked'}/>
					月度新增签约冠军展示，公司奖励红包额度设置
					<select id="dictionaryList_88"  class="select-choose-others-input-show">
						<option value="0"<c:if test="${dictionaryList[88].dictionaryValue eq '0'}">selected</c:if>>无</option>
						<option value="8"<c:if test="${dictionaryList[88].dictionaryValue eq '8'}">selected</c:if>>8元</option>
						<option value="18"<c:if test="${dictionaryList[88].dictionaryValue eq '18'}">selected</c:if>>18元</option>
						<option value="28"<c:if test="${dictionaryList[88].dictionaryValue eq '28'}">selected</c:if>>28元</option>
						<option value="38"<c:if test="${dictionaryList[88].dictionaryValue eq '38'}">selected</c:if>>38元</option>
						<option value="48"<c:if test="${dictionaryList[88].dictionaryValue eq '48'}">selected</c:if>>48元</option>
						<option value="58"<c:if test="${dictionaryList[88].dictionaryValue eq '58'}">selected</c:if>>58元</option>
						<option value=""<c:if test="${dictionaryList[88].dictionaryValue ne '0' and dictionaryList[88].dictionaryValue ne '8' and dictionaryList[88].dictionaryValue ne '18' and dictionaryList[88].dictionaryValue ne '28' and dictionaryList[88].dictionaryValue ne '38' and dictionaryList[88].dictionaryValue ne '48' and dictionaryList[88].dictionaryValue ne '58'}">selected</c:if>>其他</option>
					</select>
					<input type="text" value="${dictionaryList[88].dictionaryValue}" name="dictionaryList[88].dictionaryValue"  placeholder="红包金额不能大于200元"  class="gift-to-somebody-balance-setting" <c:if test="${dictionaryList[88].dictionaryValue eq '0' or dictionaryList[88].dictionaryValue eq '8' or dictionaryList[88].dictionaryValue eq '18' or dictionaryList[88].dictionaryValue eq '28' or dictionaryList[88].dictionaryValue eq '38' or dictionaryList[88].dictionaryValue eq '48' or dictionaryList[88].dictionaryValue eq '58'}">style="display:none;"</c:if>/>
				</p>
				<p class="tit_b padingleft15">
					<input type="hide" id="formart_89"  name="dictionaryList[89].isOpen"  value="${(empty dictionaryList[89].isOpen)||(dictionaryList[89].isOpen eq '0') ? '0':'1'}"/>
					<input type="checkbox" id="rr_89"  class="month-champion-check"  ${(empty dictionaryList[89].isOpen)||(dictionaryList[89].isOpen eq '0') ? '':'checked'}/>
					月度呼出次数冠军展示，公司奖励红包额度设置
					<select id="dictionaryList_89"  class="select-choose-others-input-show">
						<option value="0"<c:if test="${dictionaryList[89].dictionaryValue eq '0'}">selected</c:if>>无</option>
						<option value="8"<c:if test="${dictionaryList[89].dictionaryValue eq '8'}">selected</c:if>>8元</option>
						<option value="18"<c:if test="${dictionaryList[89].dictionaryValue eq '18'}">selected</c:if>>18元</option>
						<option value="28"<c:if test="${dictionaryList[89].dictionaryValue eq '28'}">selected</c:if>>28元</option>
						<option value="38"<c:if test="${dictionaryList[89].dictionaryValue eq '38'}">selected</c:if>>38元</option>
						<option value="48"<c:if test="${dictionaryList[89].dictionaryValue eq '48'}">selected</c:if>>48元</option>
						<option value="58"<c:if test="${dictionaryList[89].dictionaryValue eq '58'}">selected</c:if>>58元</option>
						<option value=""<c:if test="${dictionaryList[89].dictionaryValue ne '0' and dictionaryList[89].dictionaryValue ne '8' and dictionaryList[89].dictionaryValue ne '18' and dictionaryList[89].dictionaryValue ne '28' and dictionaryList[89].dictionaryValue ne '38' and dictionaryList[89].dictionaryValue ne '48' and dictionaryList[89].dictionaryValue ne '58'}">selected</c:if>>其他</option>
					</select>
					<input type="text" value="${dictionaryList[89].dictionaryValue}" name="dictionaryList[89].dictionaryValue" placeholder="红包金额不能大于200元"  class="gift-to-somebody-balance-setting"  <c:if test="${dictionaryList[89].dictionaryValue eq '0' or dictionaryList[89].dictionaryValue eq '8' or dictionaryList[89].dictionaryValue eq '18' or dictionaryList[89].dictionaryValue eq '28' or dictionaryList[89].dictionaryValue eq '38' or dictionaryList[89].dictionaryValue eq '48' or dictionaryList[89].dictionaryValue eq '58'}">style="display:none;"</c:if>/>
				</p>
				<p class="tit_b padingleft15">
					<input type="hide" id="formart_90"  name="dictionaryList[90].isOpen"  value="${(empty dictionaryList[90].isOpen)||(dictionaryList[90].isOpen eq '0') ? '0':'1'}"/>
					<input type="checkbox" id="rr_90" class="month-champion-check"  ${(empty dictionaryList[90].isOpen)||(dictionaryList[90].isOpen eq '0') ? '':'checked'}/>
					月度呼出时长冠军展示，公司奖励红包额度设置
					<select id="dictionaryList_90"  class="select-choose-others-input-show">
						<option value="0" <c:if test="${dictionaryList[90].dictionaryValue eq '0'}">selected</c:if>>无</option>
						<option value="8" <c:if test="${dictionaryList[90].dictionaryValue eq '8'}">selected</c:if>>8元</option>
						<option value="18" <c:if test="${dictionaryList[90].dictionaryValue eq '18'}">selected</c:if>>18元</option>
						<option value="28" <c:if test="${dictionaryList[90].dictionaryValue eq '28'}">selected</c:if>>28元</option>
						<option value="38" <c:if test="${dictionaryList[90].dictionaryValue eq '38'}">selected</c:if>>38元</option>
						<option value="48" <c:if test="${dictionaryList[90].dictionaryValue eq '48'}">selected</c:if>>48元</option>
						<option value="58" <c:if test="${dictionaryList[90].dictionaryValue eq '58'}">selected</c:if>>58元</option>
						<option value="" <c:if test="${dictionaryList[90].dictionaryValue ne '0' and dictionaryList[90].dictionaryValue ne '8' and dictionaryList[90].dictionaryValue ne '18' and dictionaryList[90].dictionaryValue ne '28' and dictionaryList[90].dictionaryValue ne '38' and dictionaryList[90].dictionaryValue ne '48' and dictionaryList[90].dictionaryValue ne '58'}">selected</c:if>>其他</option>
					</select>
					<input type="text" value="${dictionaryList[90].dictionaryValue}" name="dictionaryList[90].dictionaryValue" placeholder="红包金额不能大于200元" class="gift-to-somebody-balance-setting"  <c:if test="${dictionaryList[90].dictionaryValue eq '0' or dictionaryList[90].dictionaryValue eq '8' or dictionaryList[90].dictionaryValue eq '18' or dictionaryList[90].dictionaryValue eq '28' or dictionaryList[90].dictionaryValue eq '38' or dictionaryList[90].dictionaryValue eq '48' or dictionaryList[90].dictionaryValue eq '58'}">style="display:none;"</c:if>/>
				</p>
			</div>
			<p class="tit_b line-between-every-setting"></p>
			<p class="tit_b posirela">
				<label class="lab padingleft15">签约弹幕:</label>
				<span class="openAndClose-radio-Box">
					<label class="lab"><input type="radio" class="radio-change-visitity-btn-3"   name="dictionaryList[91].dictionaryValue" ${dictionaryList[91].dictionaryValue ne '0' ?'checked':''} value="1"/>开启</label>
					<label class="lab"><input type="radio" class="radio-change-visitity-btn-3"  name="dictionaryList[91].dictionaryValue" ${dictionaryList[91].dictionaryValue eq '0' ?'checked':''} value="0"/>关闭</label>
				</span>
			</p>
			<div class="radio-change-visitity-3" ${dictionaryList[91].dictionaryValue eq '0' ?'style="display:none;"':''}>
				<p class="tit_b padingleft15">
					<input type="hide" id="formart_92"  name="dictionaryList[92].dictionaryValue"  value="${(empty dictionaryList[92].dictionaryValue)||(dictionaryList[92].dictionaryValue eq '0') ? '0':'1'}"/>
					<input type="checkbox" id="rr_92"  ${(empty dictionaryList[92].dictionaryValue)||(dictionaryList[92].dictionaryValue eq '0') ? '':'checked'}/>
					签约成功祝福，不显示客户信息：（示例：恭喜张伟成功签约！）
				</p>
				<p class="tit_b padingleft15">
					<input type="hide" id="formart_93"  name="dictionaryList[93].dictionaryValue"  value="${(empty dictionaryList[93].dictionaryValue)||(dictionaryList[93].dictionaryValue eq '0') ? '0':'1'}"/>
					<input type="checkbox" id="rr_93"  ${(empty dictionaryList[93].dictionaryValue)||(dictionaryList[93].dictionaryValue eq '0') ? '':'checked'}/>
					签约成功祝福，显示签约金额：（勾选后签约祝福将会于订单上报后弹出，并显示订单金额）
				</p>
				<p class="tit_b padingleft15">
					为签约成功成员送上公司奖励红包：红包额度设置：
					<select id="dictionaryList_94"  class="select-choose-others-input-show">
						<option value="0" <c:if test="${dictionaryList[94].dictionaryValue eq '0'}">selected</c:if>>无</option>
						<option value="8" <c:if test="${dictionaryList[94].dictionaryValue eq '8'}">selected</c:if>>8元</option>
						<option value="18" <c:if test="${dictionaryList[94].dictionaryValue eq '18'}">selected</c:if>>18元</option>
						<option value="28" <c:if test="${dictionaryList[94].dictionaryValue eq '28'}">selected</c:if>>28元</option>
						<option value="38" <c:if test="${dictionaryList[94].dictionaryValue eq '38'}">selected</c:if>>38元</option>
						<option value="48" <c:if test="${dictionaryList[94].dictionaryValue eq '48'}">selected</c:if>>48元</option>
						<option value="58" <c:if test="${dictionaryList[94].dictionaryValue eq '58'}">selected</c:if>>58元</option>
						<option value="" <c:if test="${dictionaryList[94].dictionaryValue ne '0' and dictionaryList[94].dictionaryValue ne '8' and dictionaryList[94].dictionaryValue ne '18' and dictionaryList[94].dictionaryValue ne '28' and dictionaryList[94].dictionaryValue ne '38' and dictionaryList[94].dictionaryValue ne '48' and dictionaryList[94].dictionaryValue ne '58'}">selected</c:if>>其他</option>
					</select>
					<input type="text" value="${dictionaryList[94].dictionaryValue}" name="dictionaryList[94].dictionaryValue" placeholder="红包金额不能大于200元"  class="gift-to-somebody-balance-setting"  <c:if test="${dictionaryList[94].dictionaryValue eq '0' or dictionaryList[94].dictionaryValue eq '8' or dictionaryList[94].dictionaryValue eq '18' or dictionaryList[94].dictionaryValue eq '28' or dictionaryList[94].dictionaryValue eq '38' or dictionaryList[94].dictionaryValue eq '48' or dictionaryList[94].dictionaryValue eq '58'}">style="display:none;"</c:if>/>
				</p>
			</div>
			<p class="tit_b line-between-every-setting"></p>
			<p class="tit_b posirela">
				<label class="lab padingleft15">回款金额弹幕:</label>
				<span class="openAndClose-radio-Box">
					<label class="lab"><input type="radio" name="dictionaryList[95].dictionaryValue" ${dictionaryList[95].dictionaryValue ne '0' ?'checked':''} value="1"/>开启</label>
					<label class="lab"><input type="radio" name="dictionaryList[95].dictionaryValue" ${dictionaryList[95].dictionaryValue eq '0' ?'checked':''} value="0"/>关闭</label>
				</span>
			</p>
			<p class="tit_b padingleft15"> 开启后，回款祝福将于订单审核通过后触发弹幕自动弹出，祝福中将显示回款金额：</p>
			<p class="tit_b padingleft15">
				<input type="radio" name="dictionaryList[96].dictionaryValue" ${dictionaryList[96].dictionaryValue ne '2' ?'checked':''} value="1"/>
				订单金额不小于
				<input type="text" class="auto-birthday-tips" placeholder="输入订单金额" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" name="dictionaryList[97].dictionaryValue" value="${dictionaryList[97].dictionaryValue}"/>，
				红包额度设置：
				<select id="dictionaryList_98"  class="select-choose-others-input-show">
					<option value="0" <c:if test="${dictionaryList[98].dictionaryValue eq '0'}">selected</c:if>>无</option>
					<option value="8" <c:if test="${dictionaryList[98].dictionaryValue eq '8'}">selected</c:if>>8元</option>
					<option value="18" <c:if test="${dictionaryList[98].dictionaryValue eq '18'}">selected</c:if>>18元</option>
					<option value="28" <c:if test="${dictionaryList[98].dictionaryValue eq '28'}">selected</c:if>>28元</option>
					<option value="38" <c:if test="${dictionaryList[98].dictionaryValue eq '38'}">selected</c:if>>38元</option>
					<option value="48" <c:if test="${dictionaryList[98].dictionaryValue eq '48'}">selected</c:if>>48元</option>
					<option value="58" <c:if test="${dictionaryList[98].dictionaryValue eq '58'}">selected</c:if>>58元</option>
					<option value="" <c:if test="${dictionaryList[98].dictionaryValue ne '0' and dictionaryList[98].dictionaryValue ne '8' and dictionaryList[98].dictionaryValue ne '18' and dictionaryList[98].dictionaryValue ne '28' and dictionaryList[98].dictionaryValue ne '38' and dictionaryList[98].dictionaryValue ne '48' and dictionaryList[98].dictionaryValue ne '58'}">selected</c:if>>其他</option>
				</select>
				<input type="text" name="dictionaryList[98].dictionaryValue" value="${dictionaryList[98].dictionaryValue}" placeholder="红包金额不能大于200元"  class="gift-to-somebody-balance-setting"   <c:if test="${dictionaryList[98].dictionaryValue eq '0' or dictionaryList[98].dictionaryValue eq '8' or dictionaryList[98].dictionaryValue eq '18' or dictionaryList[98].dictionaryValue eq '28' or dictionaryList[98].dictionaryValue eq '38' or dictionaryList[98].dictionaryValue eq '48' or dictionaryList[98].dictionaryValue eq '58'}">style="display:none;"</c:if>/>
			</p>
			<div class="append-balance-box">
				<c:choose>
					<c:when test="${not empty moneys}">
						<c:forEach items="${moneys }"  var="money"  varStatus="vs">
								<c:choose>
									<c:when test="${vs.index eq 0 }">
										<p class="tit_b padingleft15">
												<input type="radio" class="order-return-radio"  name="dictionaryList[96].dictionaryValue" ${dictionaryList[96].dictionaryValue eq '2' ?'checked':''} value="2"/>
												订单金额范围
												<input type="text" class="auto-birthday-tips return-order-amount order-amount-min" name="dictionaryMoneyList[${ vs.index}].minM" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" value="${money.minM }" placeholder="输入订单金额"/>——
												<input type="text" class="auto-birthday-tips return-order-amount order-amount-max" name="dictionaryMoneyList[${ vs.index}].maxM" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" value="${money.maxM }"  placeholder="输入订单金额"/>
												红包额度设置：
												<select  class="select-choose-others-input-show">
													<option value="0" <c:if test="${money.redPacket eq '0'}">selected</c:if> >无</option>
													<option value="8" <c:if test="${money.redPacket eq '8'}">selected</c:if>>8元</option>
													<option value="18" <c:if test="${money.redPacket eq '18'}">selected</c:if>>18元</option>
													<option value="28" <c:if test="${money.redPacket eq '28'}">selected</c:if>>28元</option>
													<option value="38" <c:if test="${money.redPacket eq '38'}">selected</c:if>>38元</option>
													<option value="48" <c:if test="${money.redPacket eq '48'}">selected</c:if>>48元</option>
													<option value="58" <c:if test="${money.redPacket eq '58'}">selected</c:if>>58元</option>
													<option value="" <c:if test="${money.redPacket ne '0' and money.redPacket ne '8' and money.redPacket ne '18' and money.redPacket ne '28' and money.redPacket ne '38' and money.redPacket ne '48' and money.redPacket ne '58'}">selected</c:if>>其他</option>
												</select>
												<input type="text"  name="dictionaryMoneyList[${ vs.index}].redPacket" value="${money.redPacket }" placeholder="红包金额不能大于200元"  class="gift-to-somebody-balance-setting"  <c:if test="${money.redPacket eq '0' or money.redPacket eq '8' or money.redPacket eq '18' or money.redPacket eq '28' or money.redPacket eq '38' or money.redPacket eq '48' or money.redPacket eq '58'}">style="display:none;"</c:if>/>
												<b class="fa fa-plus add-order-balace-set" title="增加"></b>
											</p>
									</c:when>
									<c:otherwise>
										<p class="tit_b padingleft30">
											订单金额范围
											<input type="text" class="auto-birthday-tips return-order-amount order-amount-min" readonly name="dictionaryMoneyList[${ vs.index}].minM" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"  value="${money.minM }" placeholder="输入订单金额"/>——
											<input type="text" class="auto-birthday-tips return-order-amount order-amount-max" name="dictionaryMoneyList[${ vs.index}].maxM" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"  value="${money.maxM }"  placeholder="输入订单金额"/>
											红包额度设置：
											<select  class="select-choose-others-input-show">
												<option value="0" <c:if test="${money.redPacket eq '0'}">selected</c:if> >无</option>
												<option value="8" <c:if test="${money.redPacket eq '8'}">selected</c:if>>8元</option>
												<option value="18" <c:if test="${money.redPacket eq '18'}">selected</c:if>>18元</option>
												<option value="28" <c:if test="${money.redPacket eq '28'}">selected</c:if>>28元</option>
												<option value="38" <c:if test="${money.redPacket eq '38'}">selected</c:if>>38元</option>
												<option value="48" <c:if test="${money.redPacket eq '48'}">selected</c:if>>48元</option>
												<option value="58" <c:if test="${money.redPacket eq '58'}">selected</c:if>>58元</option>
												<option value="" <c:if test="${money.redPacket ne '0' and money.redPacket ne '8' and money.redPacket ne '18' and money.redPacket ne '28' and money.redPacket ne '38' and money.redPacket ne '48' and money.redPacket ne '58'}">selected</c:if>>其他</option>
											</select>
											<input type="text"  name="dictionaryMoneyList[${ vs.index}].redPacket"  value="${money.redPacket }" placeholder="红包金额不能大于200元"  class="gift-to-somebody-balance-setting"   <c:if test="${money.redPacket eq '0' or money.redPacket eq '8' or money.redPacket eq '18' or money.redPacket eq '28' or money.redPacket eq '38' or money.redPacket eq '48' or money.redPacket eq '58'}">style="display:none;"</c:if>/>
											<b class="fa fa-minus del-order-balace-set" title="删除"></b>
											</p>
									</c:otherwise>
								</c:choose>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<p class="tit_b padingleft15">
							<input type="radio" class="order-return-radio"  name="dictionaryList[96].dictionaryValue" ${dictionaryList[96].dictionaryValue eq '2' ?'checked':''} value="2"/>
							订单金额范围
								<input type="text" class="auto-birthday-tips return-order-amount order-amount-min"  onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" name="dictionaryMoneyList[0].minM" placeholder="输入订单金额"/>——
								<input type="text" class="auto-birthday-tips return-order-amount order-amount-max" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" name="dictionaryMoneyList[0].maxM" placeholder="输入订单金额"/>
								红包额度设置：
								<select  class="select-choose-others-input-show">
									<option value="0">无</option>
									<option value="8">8元</option>
									<option value="18">18元</option>
									<option value="28">28元</option>
									<option value="38">38元</option>
									<option value="48">48元</option>
									<option value="58">58元</option>
									<option value="">其他</option>
								</select>
								<input type="text"  name="dictionaryMoneyList[0].redPacket"  placeholder="红包金额不能大于200元"  class="gift-to-somebody-balance-setting"   style="display:none;"/>
								<b class="fa fa-plus add-order-balace-set" title="增加"></b>
						</p>
					</c:otherwise>
				</c:choose>
				</div>	
				<p class="tit_b padingleft15"><span class="reminder"> 温馨提示</span>：红包奖励将于达成条件后直接发放至销售的资金账号中，奖励金额将从单位总账户中扣除</p>
			</div>
			<!-- 客户签约设置 -->
			<p class="tit">
				<label class="lab fl_l"><i class="tip"></i>客户签约设置</label>
			</p>
			<p class="tit_b">
					<input type="hide" id="dic_45" name="dictionaryList[45].dictionaryValue" value="${dictionaryList[45].dictionaryValue}">
					<input type="checkbox"  id="r45" ${(empty dictionaryList[45].dictionaryValue)||(dictionaryList[45].dictionaryValue eq '0') ? '':'checked'} class="check" />
					<label class="lab">客户签约时，是否需要维护合同信息（勾选表示不需要维护，不勾选表示需要维护）</label>
			</p>
			<!-- 呼叫区号设置 -->
			<p class="tit">
				<input type="hide" id="dic_11" name="dictionaryList[11].dictionaryValue" value="${dictionaryList[11].dictionaryValue}">
				<label class="lab fl_l"><i class="tip"></i>呼叫区号设置</label>
				<i class="switch fl_r ${dictionaryList[11].dictionaryValue eq '0'?'switch-hover':''}" id="on_11" name="${dictionaryList[11].dictionaryValue eq '1'?'on':'off'}"></i>
			</p>
			<div class="switch-hid" id="div_11"  ${dictionaryList[11].dictionaryValue eq '0'?'style="display:none;"':''}>
				<p class="tit_b">
					<label class="lab padingleft15">在7位或者8位固话号码前自动添加本地区号</label>
					<input type="text" name="dictionaryList[12].dictionaryValue" value="${dictionaryList[12].dictionaryValue}" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt" />
				</p>
			</div>
			<!-- 常规设置结束 -->
			<div class="com-btnbox">
				<a href="javascript:;"  id="savemanageId"  class="com-btna com-btna-sty"><label>保存</label></a>
			</div>
		</div>
		<div class="special_setting_changer-tabs hyx-sms" style="display:none;">
		<!--特殊设置  -->
		<!-- 双卡设置 -->
		<p class="tit">
			<input type="hide" id="dic_64" name="dictionaryList[74].dictionaryValue" value="${dictionaryList[74].dictionaryValue}">
			<label class="lab fl_l"><i class="tip"></i>双卡自动切换规则设置（仅适用于双卡设备）</label>
			<i class="switch fl_r ${dictionaryList[74].dictionaryValue eq '0'?'switch-hover':''}"  id="on_64" name="${dictionaryList[74].dictionaryValue eq '1'?'on':'off'}"  ></i>
		</p>
		<div class="switch-hid" id="div_64"   ><!--  ${dictionaryList[0].dictionaryValue eq '0'?'style="display:none;"':''} -->
			<p class="tit_b">
			    <input type="radio"  class="check "  name="dictionaryList[75].dictionaryValue" ${dictionaryList[75].dictionaryValue ne '2' ?'checked':''} value="1"/>
				<label class="lab" >每张卡呼出次数达到</label>
				<input type="text"  name="dictionaryList[76].dictionaryValue"  value="${dictionaryList[76].dictionaryValue}"    maxlength="3"  onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt double-card-setting-combo" />:
				<label class="lab">次，自动切换至另一张卡；（每次切换后本轮呼出次数重新计数；）</label>
			</p>
			<p class="tit_b">
			<input type="radio"  class="check "  name="dictionaryList[75].dictionaryValue" ${dictionaryList[75].dictionaryValue eq '2' ?'checked':''} value="2"/>
				每日定时切换设置；系统将于每日的
				<input type="text"  name="dictionaryList[77].dictionaryValue"  value="${dictionaryList[77].dictionaryValue}"    maxlength="3"  onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt birth-time time-rules"  time-rules="23"/>:
				<input type="text"  name="dictionaryList[78].dictionaryValue"  value="${dictionaryList[78].dictionaryValue}"  maxlength="3"  onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt birth-time time-rules"  time-rules="59"/>
				自动切换电话卡；
			</p>
			<p class="tit_b">
				<label class="lab padingleft15" >单卡每月呼叫时长上限：</label>
				<input type="text"  name="dictionaryList[79].dictionaryValue" value="${dictionaryList[79].dictionaryValue}"   onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt" />
				<label class="lab">分钟；达到上限后，系统将不再执行自动切换操作；</label>
			</p>
		</div>
		<!-- 公海客户批量取回设置 -->
			<p class="tit">
				 <input type="hide" id="dic_38" name="dictionaryList[38].dictionaryValue" value="${dictionaryList[38].dictionaryValue}">
				<label class="lab fl_l"><i class="tip"></i>公海客户批量取回设置</label>
				<i class="switch fl_r ${dictionaryList[38].dictionaryValue eq '0'?'switch-hover':''}" id="on_38" name="${dictionaryList[38].dictionaryValue eq '1'?'on':'off'}"></i>
			</p>
			<div class="switch-hid" id="div_38"  ${dictionaryList[38].dictionaryValue eq '0'?'style="display:none;"':''}>
				<p class="tit_b">
				    <input type="hide" id="formart_101"  name="dictionaryList[101].dictionaryValue"  value="${(empty dictionaryList[101].dictionaryValue)||(dictionaryList[101].dictionaryValue eq '0') ? '0':'1'}"/>
					<input type="checkbox" id="rr_101"  ${(empty dictionaryList[101].dictionaryValue)||(dictionaryList[101].dictionaryValue eq '0') ? '':'checked'} class="check">
					<label class="lab">允许公海客户批量取回为资源</label>
				</p>
				<p class="tit_b">
					<input type="hide" id="formart_102"  name="dictionaryList[102].isOpen"  value="${(empty dictionaryList[102].isOpen)||(dictionaryList[102].isOpen eq '0') ? '0':'1'}"/>
					<input type="checkbox" id="rr_102"  ${(empty dictionaryList[102].isOpen)||(dictionaryList[102].isOpen eq '0') ? '':'checked'} class="check">
					<label class="lab">允许公海客户批量取回为意向，取回意向后该客户的下次联系时间保存为操作时间的</label>
					<select class="sel" id="dictionaryList_102"  name="dictionaryList[102].dictionaryValue">
							<option value="1"  ${dictionaryList[102].dictionaryValue eq '1' ? 'selected':'' }>一天</option>
			            	<option value="2"  ${dictionaryList[102].dictionaryValue eq '2' ? 'selected':'' }>二天</option>
			            	<option value="3"  ${dictionaryList[102].dictionaryValue eq '3' ? 'selected':'' }>三天</option>
			            	<option value="4"  ${dictionaryList[102].dictionaryValue eq '4' ? 'selected':'' }>四天</option>
			            	<option value="5"  ${dictionaryList[102].dictionaryValue eq '5' ? 'selected':'' }>五天</option>
					</select>
					<label class="lab">后</label>
				</p>
			</div>
			<!-- 公海池数据权限设置 -->
			<p class="tit">
				<input type="hide" id="dic_39" name="dictionaryList[39].isOpen" value="${dictionaryList[39].isOpen}">
				<label class="lab fl_l"><i class="tip"></i>公海池数据权限设置</label>
				<i class="switch fl_r ${dictionaryList[39].isOpen eq '0'?'switch-hover':''}" id="on_39" name="${dictionaryList[39].isOpen eq '1'?'on':'off'}"></i>
			</p>
			<div class="switch-hid"  id="div_39"  ${dictionaryList[39].isOpen eq '0'?'style="display:none;"':''}>
				<table width="100%" class="high-seas-data-table">
					<thead>
					<tr>
						<th>部门名称</th>
						<th>公海数据范围</th>
						<th><button type="button" class="add_btn">增加</button></th>
					</tr>
					<input type="hide" id="tree_index" value="${empty waters ?'0':fn:length(waters)}"/>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty waters}">
						<tr class="tr-add-tree">
						<td>
						<input type="hide"  class="input_groupId"   name="dictionaryWaterList[0].groupId" >
						<input type="hide"  class="input_shireGroupIds" name="dictionaryWaterList[0].shareGroupIds"  >
							<div class="company_choose">
								<input class="choose_input" type="text" value="请选择" readonly="true"/>
								<div class="tree-drop">
									<div class="tree-drop-ul">
										<ul class="easyui-tree" data-options="
											url:'${ctx}/orgGroup/get_all_group_json',
											animate:false,
											dnd:false">
										</ul>
									</div>
									<div class="sure-cancle clearfix" >
										<button type="button" class="submit_left">确定</button>
										<button type="button" class="clean_left">清空</button>
									</div>
								</div>
							</div>
						</td>
						<td>
							<div class="company_choose">
								<input class="choose_input" type="text" value="请选择" readonly="true"/>
								<div class="tree-drop">
									<div class="tree-drop-ul">
										<ul class="easyui-tree"  data-options="
											url:'${ctx}/orgGroup/get_all_group_json',
											animate:false,
											dnd:false,
											checkbox:true">
										</ul>
									</div>
									<div class="sure-cancle clearfix" >
										<button type="button" class="submit_right">确定</button>
										<button type="button" class="clean_right">清空</button>
									</div>
								</div>
							</div>
						</td>
						<td><button type="button" class="delete">删除</button></td>
					</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${waters }"  var="water"  varStatus="vs">
					 	<tr class="tr-add-tree">
						<td>
						<input type="hide"  class="input_groupId"  name="dictionaryWaterList[${ vs.index}].groupId" value=${water.groupId } >
						<input type="hide"  class="input_shireGroupIds"   name="dictionaryWaterList[${ vs.index}].shareGroupIds"  value=${water.shareGroupIds }>
							<div class="company_choose">
								<input class="choose_input" type="text" value="${water.groupName } " readonly="true"/>
								<div class="tree-drop">
									<div class="tree-drop-ul">
										<ul class="easyui-tree"  data-options="
											url:'${ctx}/orgGroup/get_all_group_json',
											animate:false,
											dnd:false,
		
		    								onLoadSuccess:function(node,data){
		    									var $this = $(this);
		    									var $that = $this.parents('tr');
								    			var temp_groupId = $that.find('.input_groupId').val();
								    			if(temp_groupId != null && temp_groupId.length > 0){
								    				var node = $this.tree('find',temp_groupId);
							    					if(node != null){
							    						$this.tree('select',node.target);
							    					}
								    			}
								    		}
										">
		
										</ul>
									</div>
									<div class="sure-cancle clearfix" >
										<button type="button" class="submit_left">确定</button>
										<button type="button" class="clean_left">清空</button>
									</div>
								</div>
							</div>
						</td>
						<td>
							<div class="company_choose">
								<input class="choose_input" type="text" value="${water.shareGroupNames }" readonly="true"/>
								<div class="tree-drop">
									<div class="tree-drop-ul">
										<ul class="easyui-tree"  data-options="
											url:'${ctx}/orgGroup/get_all_group_json',
											animate:false,
											dnd:false,
											checkbox:true,
		
		    								onLoadSuccess:function(node,data){
		    									var $this = $(this);
		    									var $that = $this.parents('tr');
								    			var temp_shireGroupIds = $that.find('.input_shireGroupIds').val();
								    			if(temp_shireGroupIds != null && temp_shireGroupIds.length > 0){
								    				$(temp_shireGroupIds.split(',')).each(function(index,data){
								    					var node = $this.tree('find',data);
								    					if(node != null){
								    						$this.tree('check',node.target);
								    					}
								    				});
								    			}
								    		}
										">
		
										</ul>
									</div>
									<div class="sure-cancle clearfix" >
										<button type="button" class="submit_right">确定</button>
										<button type="button" class="clean_right">清空</button>
									</div>
								</div>
							</div>
						</td>
						<td><button type="button" class="delete">删除</button></td>
					</tr>
					 </c:forEach>
						</c:otherwise>
					</c:choose>
					</tbody>
				</table>
			</div>
			<!-- 待分配资源共享到公海池设置 -->
			<p class="tit">
				<input type="hide" id="dic_29" name="dictionaryList[29].dictionaryValue" value="${dictionaryList[29].dictionaryValue}">
				<label class="lab fl_l"><i class="tip"></i>待分配资源共享到公海池设置</label>
				<i class="switch fl_r ${dictionaryList[29].dictionaryValue eq '0'?'switch-hover':''}"  id="on_29" name="${dictionaryList[29].dictionaryValue eq '1'?'on':'off'}"></i>
			</p>
			<!-- 资源筛查设置 -->
			<p class="tit">
				<input type="hide" id="dic_105" name="dictionaryList[105].isOpen" value="${dictionaryList[105].isOpen}">
				<label class="lab fl_l"><i class="tip"></i>资源筛查设置 </label>
				<i class="switch fl_r ${empty dictionaryList[105].isOpen || dictionaryList[105].isOpen eq '0'?'switch-hover':''}" id="on_105" name="${dictionaryList[105].isOpen eq '1'?'on':'off'}"></i>
			</p>
			<div class="switch-hid" id="div_105"  ${empty dictionaryList[105].isOpen || dictionaryList[105].isOpen eq '0'?'style="display:none;"':''}>
				<div class="tit_a com-search" style="height:auto; overflow:unset; min-height: auto;">
					<span>资源筛查结果如果是</span>
					<dl class="select sales-select" data-input="[name='dictionaryList[105].dictionaryValue']" data-multi="true" style="z-index: 1;vertical-align: middle;">
						<dt class="resouceSearch">${dictionaryList[105].dictionaryValue?dictionaryList[105].dictionaryValue:'筛查结果（多选）' }</dt>
						<dd style="display: none;">
							<ul data-palceholder="筛查结果（多选）">
								<li class=""><a href="javascript:;" data-value="1" title="空号">空号</a></li>
								<li class=""><a href="javascript:;" data-value="2" title="停机">停机</a></li>
								<li class=""><a href="javascript:;" data-value="3" title="沉默">沉默</a></li>
							</ul>
						</dd>
						 <input name="dictionaryList[105].dictionaryValue"  type="hide"  value="${dictionaryList[105].dictionaryValue }">
				   </dl>
				   <span>将自动回收至公海。</span>
				</div>
			</div>
			<!-- 自动审核时间设置 -->
			<p class="tit">
				<input type="hide" id="dic_37" name="dictionaryList[37].isOpen" value="${dictionaryList[37].isOpen}">
				<label class="lab fl_l"><i class="tip"></i>自动审核时间设置</label>
				<i class="switch fl_r ${empty dictionaryList[37].isOpen || dictionaryList[37].isOpen eq '0'?'switch-hover':''}" id="on_37" name="${dictionaryList[37].isOpen eq '1'?'on':'off'}"></i>
			</p>
			<div class="switch-hid" id="div_37"  ${empty dictionaryList[37].isOpen || dictionaryList[37].isOpen eq '0'?'style="display:none;"':''}>
				<p class="tit_a">
					<span class="fl_l">选择月度计划的自动审核时间为每月的:</span>
					<input type="radio" name="dictionaryList[37].dictionaryValue" value="1"  class="radio" ${dictionaryList[37].dictionaryValue ne '2' and dictionaryList[37].dictionaryValue ne '3' ?'checked':''}><label class="lab">1号</label>
					<input type="radio" name="dictionaryList[37].dictionaryValue" value="2"  class="radio" ${dictionaryList[37].dictionaryValue eq '2'?'checked':''}><label class="lab">5号</label>
					<input type="radio" name="dictionaryList[37].dictionaryValue" value="3"  class="radio" ${dictionaryList[37].dictionaryValue eq '3'?'checked':''}><label class="lab">10号</label>
				</p>
			</div>
			<!-- 共享客户数据查看范围设置 -->
			<p class="tit">
				<input type="hide" id="dic_46" name="dictionaryList[46].dictionaryValue" value="${dictionaryList[46].dictionaryValue}">
				<label class="lab fl_l"><i class="tip"></i>共享客户数据查看范围设置</label>
				<i class="switch fl_r ${dictionaryList[46].dictionaryValue eq '0'?'switch-hover':''}" id="on_46" name="${dictionaryList[46].dictionaryValue eq '1'?'on':'off'}"></i>
			</p>
			<p class="tit_b tit_spacial">
			  <label class="lab padingleft15">打开该设置，表示共享客户里面可以查看设置了特定的客服人员的客户，关闭该设置，表示共享客户里面不能查看设置了特定的客服人员的客户，默认为“打开；</label>
			</p>
			<!-- 管理员签约权限设置 -->
			<p class="tit">
				<input type="hide" id="dic_47" name="dictionaryList[47].dictionaryValue" value="${dictionaryList[47].dictionaryValue}">
				<label class="lab fl_l"><i class="tip"></i>管理员签约权限设置</label>
				<i class="switch fl_r ${dictionaryList[47].dictionaryValue eq '0'?'switch-hover':''}" id="on_47" name="${dictionaryList[47].dictionaryValue eq '1'?'on':'off'}"></i>
			</p>
			<p class="tit_b tit_spacial">
			  <label class="lab padingleft15">开启设置项，管理员可以对下属成员的客户进行【签约】和【取消签约】的操作；关闭设置项，管理员不能对下属成员的客户进行【签约】和【取消签约】的操作；默认为“关闭；</label>
			</p>
			<!-- 签约客户回收设置 -->
			<p class="tit">
				<input type="hide" id="dic_49" name="dictionaryList[49].isOpen"  value="${dictionaryList[49].isOpen}">
				<label class="lab fl_l"><i class="tip"></i>签约客户回收设置</label>
				<i class="switch fl_r  ${dictionaryList[49].isOpen eq '0'?'switch-hover':''} "  id="on_49" name="${dictionaryList[49].isOpen eq '1'?'on':'off'}"></i>
			</p>
			<div class="switch-hid" id="div_49"  ${dictionaryList[49].isOpen eq '0'?'style="display:none;"':''}>
				<p class="tit_b" >
					<label class="lab padingleft15">签约客户全部订单失效</label>
					<input type="text" id=""  name="dictionaryList[49].dictionaryValue" value="${ dictionaryList[49].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt" />
					<label class="lab">天后，将自动回收到公海客户池中。</label>
				</p>
			</div>
			<!-- 自动连拨设置 -->
			<p class="tit">
				<input type="hide" id="dic_50"  name="dictionaryList[50].dictionaryValue" value="${dictionaryList[50].dictionaryValue }">
				<label class="lab fl_l"><i class="tip"></i>自动连拨设置</label>
				<i class="switch fl_r  ${dictionaryList[50].dictionaryValue eq '0'?'switch-hover':''} " id="on_50" name="${dictionaryList[50].dictionaryValue eq '1'?'on':'off'}"></i>
			</p>
			<p class="tit_b tit_spacial">
				<label class="lab padingleft15">在电话未挂断的前提下，点击以下操作：【沟通失败】（包含子项）、【信息错误】（包含子项），【延后呼叫】的【确定】，【下一条】、【资源备注】中的【保存并继续】；系统将会自动挂断电话并呼出下一通电话；</label>
			</p>
			<!-- APP查看附近设置 -->
			<p class="tit">
				<input type="hide" id="dic_62" name="dictionaryList[62].dictionaryValue" value="${dictionaryList[62].dictionaryValue }">
				<label class="lab fl_l"><i class="tip"></i>APP查看附近设置</label>
				<i class="switch fl_r ${dictionaryList[62].dictionaryValue eq '0'?'switch-hover':''}" id="on_62" name="${dictionaryList[62].dictionaryValue eq '1'?'on':'off'}"></i>
			</p>
			<p class="tit_b tit_spacial">
				<label class="lab padingleft15">允许销售员在APP中查看公司的客户；（只显示位置，不显示客户信息）</label>
			</p>
			<!-- 共有客户设置 -->
			<p class="tit">
				<input type="hide" id="dic_58" name="dictionaryList[58].dictionaryValue" value="${dictionaryList[58].dictionaryValue }">
				<input type = "hide" name ="comAccVal" value="${dictionaryList[58].dictionaryValue }">
				<label class="lab fl_l"><i class="tip"></i>共有客户设置</label>
				<i class="switch fl_r ${dictionaryList[58].dictionaryValue eq '0'?'switch-hover':''}" id="on_58" name="${dictionaryList[58].dictionaryValue eq '1'?'on':'off'}"></i>
			</p>
			<div class="switch-hid" id="div_58"  ${dictionaryList[58].dictionaryValue eq '0'?'style="display:none;"':''}>
				<p class="tit_b">
						<input type="hide" id="formart_59"  name="dictionaryList[59].dictionaryValue"  value="${(empty dictionaryList[59].dictionaryValue)||(dictionaryList[59].dictionaryValue eq '0') ? '0':'1'}"/>
						<input type="checkbox"  id="rr_59"  ${(empty dictionaryList[59].dictionaryValue)||(dictionaryList[59].dictionaryValue eq '0') ? '':'checked'} class="check" />
						<label class="lab">允许共有者放弃共有客户至公海客户池</label>
				</p>
				<p class="tit_b">
						<input type="hide" id="formart_60"  name="dictionaryList[60].dictionaryValue"  value="${(empty dictionaryList[60].dictionaryValue)||(dictionaryList[60].dictionaryValue eq '0') ? '0':'1'}"/>
						<input type="checkbox"  id="rr_60"  ${(empty dictionaryList[60].dictionaryValue)||(dictionaryList[60].dictionaryValue eq '0') ? '':'checked'} class="check" />
						<label class="lab">允许共有者修改共有客户信息</label>
				</p>
				<p class="tit_b tit_spacial">
						<input type="hide" id="formart_61"  name="dictionaryList[61].dictionaryValue"  value="${(empty dictionaryList[61].dictionaryValue)||(dictionaryList[61].dictionaryValue eq '0') ? '0':'1'}"/>
						<input type="checkbox"  id="rr_61"  ${(empty dictionaryList[61].dictionaryValue)||(dictionaryList[61].dictionaryValue eq '0') ? '':'checked'} class="check" />
						<label class="lab w620" >允许共有者对共有客户进行签约、取消签约、新增合同、编辑合同、取消合同、新增订单、编辑订单、作废订单、撤销订单、删除订单记录的操作；</label>
				</p>
			</div>
			<!-- 客户跟进时间段设置 -->
			<p class="tit">
				<input type="hide" id="dic_100" name="dictionaryList[100].isOpen" value="${dictionaryList[100].isOpen }">
				<label class="lab fl_l"><i class="tip"></i>客户跟进时间段设置</label>
				<i class="switch fl_r ${dictionaryList[100].isOpen eq '0'?'switch-hover':''}" id="on_100" name="${dictionaryList[100].isOpen eq '1'?'on':'off'}"></i>
			</p>
			<div class="switch-hid"  id="div_100"  ${dictionaryList[100].isOpen eq '0'?'style="display:none;"':''}>
					<table class="com-search" style="width:100%;">
						<thead>
							<tr>
								<th>客户</th>
								<th>时间段</th>
								<th>是否提示</th>
								<th><button type="button" class="cust_follow_set_add_btns">新增</button></th>
							</tr>
						</thead>
						<tbody>
						 <c:choose>
						 	<c:when test="${not empty followProcess}">
								<c:forEach items="${followProcess }"  var="followProcess"  varStatus="vs">
									<tr class="tr-add-tree">
									<td>
										<dl class="select sales-select" data-input="[name='dictionaryFollowList[${ vs.index}].optionId']" data-multi="true" style="z-index: 1;">
											<dt class="">${followProcess.optionName }</dt>
											<dd style="display: none;">
												<ul data-palceholder="选择销售进程（可多选）">
													<c:forEach items="${process }"  var="process" >
															<li class=""><a href="javascript:;" data-value="${process.optionlistId}" title="${process.optionName}">${process.optionName}</a></li>
													</c:forEach>	
													</ul>
											</dd>
											 <input name="dictionaryFollowList[${ vs.index}].optionId"  type="hide"  value="${followProcess.optionId }">
									   </dl>
									</td>
									<td>
									<input type="text" id="time_${ vs.index}" name="dictionaryFollowList[${ vs.index}].startTime" value="${followProcess.startTime}"   class="ipt followCust-timeSetting" onclick="WdatePicker({dateFmt:'HH:mm',readOnly:true,onpicked:timeChange,changing:timeChange,oncleared:timeChange});">
										~
									<input type="text" name="dictionaryFollowList[${ vs.index}].endTime" value="${followProcess.endTime}"   class="ipt followCust-timeSetting" onclick="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'time_${ vs.index}\');}',readOnly:true,onpicked:timeChange,changing:timeChange,oncleared:timeChange});">
									</td>
									<td>
										<label class="cust_follow_timesetting">
										<input type="hide"  name="dictionaryFollowList[${vs.index}].isAlert" value="${followProcess.isAlert }" class="time-remind-input">
										<label class="lab"><input type="checkbox"  ${followProcess.isAlert eq '1' ? 'checked':''} class="check time-remind-tip"  id="random">时间提示</label><!-- checkbox 需要加id 不然js获取所有选中checkbox的id会报错 -->
									</td>
									<td>
										<button type="button" class="cust_follow_set_del_btns">删除</button>
									</td>
								</tr>
								</c:forEach>
						 	</c:when>
						 	<c:otherwise>
							 	<tr class="tr-add-tree">
									<td>
										<dl class="select sales-select" data-input="[name='dictionaryFollowList[0].optionId']" data-multi="true" style="z-index: 1;">
											<dt class="">选择销售进程（可多选）</dt>
											<dd style="display: none;">
												<ul data-palceholder="选择销售进程（可多选）">
													<c:forEach items="${process }"  var="process" >
															<li class=""><a href="javascript:;" data-value="${process.optionlistId}" title="${process.optionName}">${process.optionName}</a></li>
													</c:forEach>	
													</ul>
											</dd>
											 <input name="dictionaryFollowList[0].optionId"  type="hide"   value="">
									   </dl>
									</td>
									<td>
										<input type="text" id="time_0" name="dictionaryFollowList[0].startTime"  class="ipt followCust-timeSetting" placeholder="开始时间段" onclick="WdatePicker({dateFmt:'HH:mm',readOnly:true,onpicked:timeChange,changing:timeChange,oncleared:timeChange});">
											~
										<input type="text" name="dictionaryFollowList[0].endTime"  class="ipt followCust-timeSetting" placeholder="结束时间段" onclick="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'time_0\');}',readOnly:true,onpicked:timeChange,changing:timeChange,oncleared:timeChange});">
									</td>
									<td>
										<label class="cust_follow_timesetting">
										<input type="hide"  name="dictionaryFollowList[0].isAlert" value="0"  class="time-remind-input">
										<label class="lab"><input type="checkbox"  class="check time-remind-tip"  id="random">时间提示</label>
									</td>
									<td>
										<button type="button" class="cust_follow_set_del_btns">删除</button>
									</td>
								</tr>
						 	</c:otherwise>
						 </c:choose>
	
						</tbody>
					</table>
				</div>
				<!--特殊设置结束  -->
				<div class="com-btnbox">
					<a href="javascript:;"  id="savemanageIds"  class="com-btna com-btna-sty"><label>保存</label></a>
				</div>
		</div>
		
		
			<!-- 隐藏域  -->
<input type="hide" name="dictionaryList[0].dictionaryId" value="${dictionaryList[0].dictionaryId}">
<input type="hide" name="dictionaryList[1].dictionaryId" value="${dictionaryList[1].dictionaryId}">
<input type="hide" name="dictionaryList[2].dictionaryId" value="${dictionaryList[2].dictionaryId}">
<input type="hide" name="dictionaryList[3].dictionaryId" value="${dictionaryList[3].dictionaryId}">
<input type="hide" name="dictionaryList[4].dictionaryId" value="${dictionaryList[4].dictionaryId}">
<input type="hide" name="dictionaryList[5].dictionaryId" value="${dictionaryList[5].dictionaryId}">
<input type="hide" name="dictionaryList[6].dictionaryId" value="${dictionaryList[6].dictionaryId}">
<input type="hide" name="dictionaryList[7].dictionaryId" value="${dictionaryList[7].dictionaryId}">
<input type="hide" name="dictionaryList[8].dictionaryId" value="${dictionaryList[8].dictionaryId}">
<input type="hide" name="dictionaryList[9].dictionaryId" value="${dictionaryList[9].dictionaryId}">
<input type="hide" name="dictionaryList[10].dictionaryId" value="${dictionaryList[10].dictionaryId}">
<!-- 需求后续追加 -->
<input type="hide" name="dictionaryList[11].dictionaryId" value="${dictionaryList[11].dictionaryId}">
<input type="hide" name="dictionaryList[12].dictionaryId" value="${dictionaryList[12].dictionaryId}">
<input type="hide" name="dictionaryList[13].dictionaryId" value="${dictionaryList[13].dictionaryId}">
<input type="hide" name="dictionaryList[14].dictionaryId" value="${dictionaryList[14].dictionaryId}">
<input type="hide" name="dictionaryList[15].dictionaryId" value="${dictionaryList[15].dictionaryId}">
<input type="hide" name="dictionaryList[16].dictionaryId" value="${dictionaryList[16].dictionaryId}">
<input type="hide" name="dictionaryList[17].dictionaryId" value="${dictionaryList[17].dictionaryId}">
<input type="hide" name="dictionaryList[18].dictionaryId" value="${dictionaryList[18].dictionaryId}">
<input type="hide" name="dictionaryList[19].dictionaryId" value="${dictionaryList[19].dictionaryId}">
<input type="hide" name="dictionaryList[20].dictionaryId" value="${dictionaryList[20].dictionaryId}">
<input type="hide" name="dictionaryList[21].dictionaryId" value="${dictionaryList[21].dictionaryId}">
<input type="hide" name="dictionaryList[22].dictionaryId" value="${dictionaryList[22].dictionaryId}">
<input type="hide" name="dictionaryList[23].dictionaryId" value="${dictionaryList[23].dictionaryId}">
<input type="hide" name="dictionaryList[24].dictionaryId" value="${dictionaryList[24].dictionaryId}">
<input type="hide" name="dictionaryList[25].dictionaryId" value="${dictionaryList[25].dictionaryId}">
<input type="hide" name="dictionaryList[26].dictionaryId" value="${dictionaryList[26].dictionaryId}">
<input type="hide" name="dictionaryList[27].dictionaryId" value="${dictionaryList[27].dictionaryId}">
<input type="hide" name="dictionaryList[28].dictionaryId" value="${dictionaryList[28].dictionaryId}">
<input type="hide" name="dictionaryList[29].dictionaryId" value="${dictionaryList[29].dictionaryId}">
<input type="hide" name="dictionaryList[30].dictionaryId" value="${dictionaryList[30].dictionaryId}">
<input type="hide" name="dictionaryList[31].dictionaryId" value="${dictionaryList[31].dictionaryId}">
<input type="hide" name="dictionaryList[32].dictionaryId" value="${dictionaryList[32].dictionaryId}">
<input type="hide" name="dictionaryList[33].dictionaryId" value="${dictionaryList[33].dictionaryId}">
<input type="hide" name="dictionaryList[34].dictionaryId" value="${dictionaryList[34].dictionaryId}">
<input type="hide" name="dictionaryList[35].dictionaryId" value="${dictionaryList[35].dictionaryId}">
<input type="hide" name="dictionaryList[36].dictionaryId" value="${dictionaryList[36].dictionaryId}">
<input type="hide" name="dictionaryList[37].dictionaryId" value="${dictionaryList[37].dictionaryId}">
<input type="hide" name="dictionaryList[38].dictionaryId" value="${dictionaryList[38].dictionaryId}">
<input type="hide" name="dictionaryList[39].dictionaryId" value="${dictionaryList[39].dictionaryId}">
<input type="hide" name="dictionaryList[40].dictionaryId" value="${dictionaryList[40].dictionaryId}">
<input type="hide" name="dictionaryList[41].dictionaryId" value="${dictionaryList[41].dictionaryId}">
<input type="hide" name="dictionaryList[42].dictionaryId" value="${dictionaryList[42].dictionaryId}">
<input type="hide" name="dictionaryList[43].dictionaryId" value="${dictionaryList[43].dictionaryId}">
<input type="hide" name="dictionaryList[44].dictionaryId" value="${dictionaryList[44].dictionaryId}">
<input type="hide" name="dictionaryList[45].dictionaryId" value="${dictionaryList[45].dictionaryId}">
<input type="hide" name="dictionaryList[46].dictionaryId" value="${dictionaryList[46].dictionaryId}">
<input type="hide" name="dictionaryList[47].dictionaryId" value="${dictionaryList[47].dictionaryId}">
<input type="hide" name="dictionaryList[48].dictionaryId" value="${dictionaryList[48].dictionaryId}">
<input type="hide" name="dictionaryList[49].dictionaryId" value="${dictionaryList[49].dictionaryId}">
<input type="hide" name="dictionaryList[50].dictionaryId" value="${dictionaryList[50].dictionaryId}">
<input type="hide" name="dictionaryList[51].dictionaryId" value="${dictionaryList[51].dictionaryId}">
<input type="hide" name="dictionaryList[52].dictionaryId" value="${dictionaryList[52].dictionaryId}">
<input type="hide" name="dictionaryList[53].dictionaryId" value="${dictionaryList[53].dictionaryId}">
<input type="hide" name="dictionaryList[54].dictionaryId" value="${dictionaryList[54].dictionaryId}">
<input type="hide" name="dictionaryList[55].dictionaryId" value="${dictionaryList[55].dictionaryId}">
<input type="hide" name="dictionaryList[56].dictionaryId" value="${dictionaryList[56].dictionaryId}">
<input type="hide" name="dictionaryList[57].dictionaryId" value="${dictionaryList[57].dictionaryId}">
<input type="hide" name="dictionaryList[58].dictionaryId" value="${dictionaryList[58].dictionaryId}">
<input type="hide" name="dictionaryList[59].dictionaryId" value="${dictionaryList[59].dictionaryId}">
<input type="hide" name="dictionaryList[60].dictionaryId" value="${dictionaryList[60].dictionaryId}">
<input type="hide" name="dictionaryList[61].dictionaryId" value="${dictionaryList[61].dictionaryId}">
<input type="hide" name="dictionaryList[62].dictionaryId" value="${dictionaryList[62].dictionaryId}">
<input type="hide" name="dictionaryList[63].dictionaryId" value="${dictionaryList[63].dictionaryId}">
<input type="hide" name="dictionaryList[64].dictionaryId" value="${dictionaryList[64].dictionaryId}">
<input type="hide" name="dictionaryList[65].dictionaryId" value="${dictionaryList[65].dictionaryId}">
<input type="hide" name="dictionaryList[66].dictionaryId" value="${dictionaryList[66].dictionaryId}">
<input type="hide" name="dictionaryList[67].dictionaryId" value="${dictionaryList[67].dictionaryId}">
<input type="hide" name="dictionaryList[68].dictionaryId" value="${dictionaryList[68].dictionaryId}">
<input type="hide" name="dictionaryList[69].dictionaryId" value="${dictionaryList[69].dictionaryId}">
<input type="hide" name="dictionaryList[70].dictionaryId" value="${dictionaryList[70].dictionaryId}">
<input type="hide" name="dictionaryList[71].dictionaryId" value="${dictionaryList[71].dictionaryId}">
<input type="hide" name="dictionaryList[72].dictionaryId" value="${dictionaryList[72].dictionaryId}">
<input type="hide" name="dictionaryList[73].dictionaryId" value="${dictionaryList[73].dictionaryId}">

<input type="hide" name="dictionaryList[74].dictionaryId" value="${dictionaryList[74].dictionaryId}">
<input type="hide" name="dictionaryList[75].dictionaryId" value="${dictionaryList[75].dictionaryId}">
<input type="hide" name="dictionaryList[76].dictionaryId" value="${dictionaryList[76].dictionaryId}">
<input type="hide" name="dictionaryList[77].dictionaryId" value="${dictionaryList[77].dictionaryId}">
<input type="hide" name="dictionaryList[78].dictionaryId" value="${dictionaryList[78].dictionaryId}">
<input type="hide" name="dictionaryList[79].dictionaryId" value="${dictionaryList[79].dictionaryId}">

<input type="hide" name="dictionaryList[80].dictionaryId" value="${dictionaryList[80].dictionaryId}">
<input type="hide" name="dictionaryList[81].dictionaryId" value="${dictionaryList[81].dictionaryId}">
<input type="hide" name="dictionaryList[82].dictionaryId" value="${dictionaryList[82].dictionaryId}">
<input type="hide" name="dictionaryList[83].dictionaryId" value="${dictionaryList[83].dictionaryId}">
<input type="hide" name="dictionaryList[84].dictionaryId" value="${dictionaryList[84].dictionaryId}">
<input type="hide" name="dictionaryList[85].dictionaryId" value="${dictionaryList[85].dictionaryId}">
<input type="hide" name="dictionaryList[86].dictionaryId" value="${dictionaryList[86].dictionaryId}">
<input type="hide" name="dictionaryList[87].dictionaryId" value="${dictionaryList[87].dictionaryId}">
<input type="hide" name="dictionaryList[88].dictionaryId" value="${dictionaryList[88].dictionaryId}">
<input type="hide" name="dictionaryList[89].dictionaryId" value="${dictionaryList[89].dictionaryId}">
<input type="hide" name="dictionaryList[90].dictionaryId" value="${dictionaryList[90].dictionaryId}">
<input type="hide" name="dictionaryList[91].dictionaryId" value="${dictionaryList[91].dictionaryId}">
<input type="hide" name="dictionaryList[92].dictionaryId" value="${dictionaryList[92].dictionaryId}">
<input type="hide" name="dictionaryList[93].dictionaryId" value="${dictionaryList[93].dictionaryId}">
<input type="hide" name="dictionaryList[94].dictionaryId" value="${dictionaryList[94].dictionaryId}">
<input type="hide" name="dictionaryList[95].dictionaryId" value="${dictionaryList[95].dictionaryId}">
<input type="hide" name="dictionaryList[96].dictionaryId" value="${dictionaryList[96].dictionaryId}">
<input type="hide" name="dictionaryList[97].dictionaryId" value="${dictionaryList[97].dictionaryId}">
<input type="hide" name="dictionaryList[98].dictionaryId" value="${dictionaryList[98].dictionaryId}">
<input type="hide" name="dictionaryList[99].dictionaryId" value="${dictionaryList[99].dictionaryId}">
<input type="hide" name="dictionaryList[100].dictionaryId" value="${dictionaryList[100].dictionaryId}">
<input type="hide" name="dictionaryList[101].dictionaryId" value="${dictionaryList[101].dictionaryId}">
<input type="hide" name="dictionaryList[102].dictionaryId" value="${dictionaryList[102].dictionaryId}">
<input type="hide" name="dictionaryList[103].dictionaryId" value="${dictionaryList[103].dictionaryId}">
<input type="hide" name="dictionaryList[104].dictionaryId" value="${dictionaryList[104].dictionaryId}">
<input type="hide" name="dictionaryList[105].dictionaryId" value="${dictionaryList[105].dictionaryId}">
		</form>
	</ul>
</div>
<script type="text/x-handlebars-template" id="template">
<p class="tit_b padingleft30"">
订单金额范围
<input type="text" class="auto-birthday-tips return-order-amount order-amount-min" name="dictionaryMoneyList[{{index}}].minM" placeholder="输入订单金额"onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" readonly/>——
					<input type="text" class="auto-birthday-tips return-order-amount order-amount-max" name="dictionaryMoneyList[{{index}}].maxM" placeholder="输入订单金额"onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" />
					红包额度设置：
					<select class="select-choose-others-input-show">
						<option value="0">无</option>
						<option value="8">8元</option>
						<option value="18">18元</option>
						<option value="28">28元</option>
						<option value="38">38元</option>
						<option value="48">48元</option>
						<option value="58">58元</option>
						<option value="">其他</option>
					</select>
					<input type="text"  name="dictionaryMoneyList[{{index}}].redPacket"  placeholder="红包金额不能大于200元"  class="gift-to-somebody-balance-setting"  style="display:none;"/>
					<b class="fa fa-minus del-order-balace-set" title="删除"></b>
</p>
</script>
<script type="text/x-handlebars-template" id="follow_template">
							<tr class="tr-add-tree">
								<td>
									<dl class="select sales-select" data-input="[name='dictionaryFollowList[{{index}}].optionId']" data-multi="true" style="z-index: 1;">
											<dt class="">选择销售进程（可多选）</dt>
											<dd style="display: none;">
												<ul data-palceholder="选择销售进程（可多选）">
													<c:forEach items="${process }"  var="process" >
															<li class=""><a href="javascript:;" data-value="${process.optionlistId}" title="${process.optionName}">${process.optionName}</a></li>
													</c:forEach>	
												</ul>
											</dd>
											 <input name="dictionaryFollowList[{{index}}].optionId"  type="hide"  value="">
									   </dl>
								</td>
								<td>
									<input type="text" id="time_{{index}}" name="dictionaryFollowList[{{index}}].startTime"  class="ipt followCust-timeSetting" placeholder="开始时间段" onclick="WdatePicker({dateFmt:'HH:mm',readOnly:true,onpicked:timeChange,changing:timeChange,oncleared:timeChange});">
									~
									<input type="text" name="dictionaryFollowList[{{index}}].endTime"  class="ipt followCust-timeSetting" placeholder="结束时间段" onclick="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'time_{{index}}\');}',readOnly:true,onpicked:timeChange,changing:timeChange,oncleared:timeChange});">
								</td>
								<td>
									<label class="cust_follow_timesetting">
									<input type="hide"  name="dictionaryFollowList[{{index}}].isAlert" value="0"  class="time-remind-input">
									<label class="lab"><input type="checkbox"  class="check time-remind-tip" id="random">时间提示</label>
								</td>
								<td>
									<button type="button" class="cust_follow_set_del_btns">删除</button>
								</td>
							</tr>
</script>
</body>
</html>
