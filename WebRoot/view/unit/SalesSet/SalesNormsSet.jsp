<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/include.jsp"%>
<style type="text/css">
.setting_sale_con span{float:left; padding-top:3px; padding-right:3px;}
</style>
<tiles:insertDefinition name="unitSideLayout">
	<tiles:putAttribute name="title" type="string">慧营销-销售制度维护</tiles:putAttribute>
	<tiles:putAttribute name="mainContent" type="string">
	<script type="text/javascript" src="${ctx}/js/unit/SalesSet/SalesNormsSet.js${_v}"></script>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- ajax session time out 处理 -->
<%@ include file="/common/ajaxSessionTimeout.jsp" %>
<form action="${ctx}/option/updateNorms.do" method="post">
<p class="title">销售管理设置> <b>销售管理设置</b></p>
<div class="setting_sale">
   <div class="setting_sale_title">
      <span class="txt">资源回收设置</span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="on_0" radio="r" name="dictionaryList[0].dictionaryValue" value="1" ${dictionaryList[0].dictionaryValue eq '1'?'checked':''}/>
      	<label class="label_op" for="on_0">开启</label>
      </span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="off_0" radio="r" name="dictionaryList[0].dictionaryValue" value="0" ${dictionaryList[0].dictionaryValue eq '0'?'checked':''}/>
      	<label class="label_op" for="off_0">关闭</label>
      </span>
   </div> 
   <div class="setting_sale_con" id="div_0" ${dictionaryList[0].dictionaryValue eq '0'?'style="display:none;"':''}>
  	 <p>分配到帐号的客户信息
  	 	<input type="text" class="text_op" name="dictionaryList[4].dictionaryValue" value="${dictionaryList[4].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"/> 天未联系，直接回收到待分资源中。回收天数从资源分配时间点开始计时。
  	 </p>
   </div>
</div>
<div class="setting_sale">
   <div class="setting_sale_title">
      <span class="txt">资源去重设置</span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="on_1" radio="r" name="dictionaryList[1].dictionaryValue" value="1" ${dictionaryList[1].dictionaryValue eq '1'?'checked':''}/>
      	<label class="label_op" for="on_1">开启</label>
      </span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="off_1" radio="r" name="dictionaryList[1].dictionaryValue" value="0" ${dictionaryList[1].dictionaryValue eq '0'?'checked':''}/>
      	<label class="label_op" for="off_1">关闭</label>
      </span>
   </div>
   <div class="setting_sale_con" id="div_1" ${dictionaryList[1].dictionaryValue eq '0'?'style="display:none;"':''}>
  	  <ul>
          <li><input type="checkbox" class="radio_op" id="r5" ${dictionaryList[5].dictionaryValue  gt '1'?'checked':''}/><label class="label_op_1" for="c"></label>
          <span>对</span>          	  
          	  <select class="margin_l5" id="dictionaryList_5" ${dictionaryList[5].dictionaryValue eq '1'?'disabled':''} name="dictionaryList[5].dictionaryValue"> 
	                      <option value="2" ${dictionaryList[5].dictionaryValue eq '2' ? 'selected':'' }>签约用户</option>
	                      <option value="3" ${dictionaryList[5].dictionaryValue eq '3' ? 'selected':'' }>意向用户</option>   
	                      <option value="4" ${dictionaryList[5].dictionaryValue eq '4' ? 'selected':'' }>所有资源</option>    
               </select>&nbsp; 电话号码进行去重保护
          </li>
          <li><input type="checkbox" class="radio_op" id="r6" ${dictionaryList[6].dictionaryValue gt '0'?'checked':''}/><label class="label_op_1" for="c"></label>          	  
          	  <span>对</span>
          	  <select class="margin_l5" id="dictionaryList_6" ${dictionaryList[6].dictionaryValue eq '0'?'disabled':''} name="dictionaryList[6].dictionaryValue"> 
	                      <option value="1" ${dictionaryList[6].dictionaryValue eq '1' ? 'selected':'' }>签约用户</option>
	                      <option value="2" ${dictionaryList[6].dictionaryValue eq '2' ? 'selected':'' }>意向用户</option>   
	                      <option value="3" ${dictionaryList[6].dictionaryValue eq '3' ? 'selected':'' }>所有资源</option>    
               </select>&nbsp; 单位名称进行去重保护
          </li>
          <li><input type="checkbox" class="radio_op" id="r15" ${dictionaryList[15].dictionaryValue gt '0'?'checked':''}/><label class="label_op_1" for="c"></label>          	  
          	  <span>对</span>
          	  <select class="margin_l5" id="dictionaryList_15" ${dictionaryList[15].dictionaryValue eq '0'?'disabled':''} name="dictionaryList[15].dictionaryValue"> 
	                      <option value="1" ${dictionaryList[15].dictionaryValue eq '1' ? 'selected':'' }>签约用户</option>
	                      <option value="2" ${dictionaryList[15].dictionaryValue eq '2' ? 'selected':'' }>意向用户</option>   
	                      <option value="3" ${dictionaryList[15].dictionaryValue eq '3' ? 'selected':'' }>所有资源</option>  
               </select>&nbsp; 单位主页进行去重保护
          </li>
       </ul>
   </div>
</div>
<div class="setting_sale">
   <div class="setting_sale_title">
      <span class="txt">客户跟进设置</span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="on_2" radio="r" name="dictionaryList[2].dictionaryValue" value="1" ${dictionaryList[2].dictionaryValue eq '1'?'checked':''}/>
      	<label class="label_op" for="on_2">开启</label>
      </span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="off_2" radio="r" name="dictionaryList[2].dictionaryValue" value="0" ${dictionaryList[2].dictionaryValue eq '0'?'checked':''}/>
      	<label class="label_op" for="off_2">关闭</label>
      </span>
   </div> 
   <div class="setting_sale_con" id="div_2" ${dictionaryList[2].dictionaryValue eq '0'?'style="display:none;"':''}>
  	 <p><input type="checkbox" class="radio_op" id="r13" checked disabled /><input type="hidden" name="dictionaryList[13].isOpen" value="1" />下次跟进客户默认时间为
      	<input type="text" class="text_op" id="dictionaryList_13"  name="dictionaryList[13].dictionaryValue" value="${dictionaryList[13].dictionaryValue}" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"/> 
      	 <select id="dictionaryList_14" name="dictionaryList[14].dictionaryValue"> 
	                      <option value="0" ${dictionaryList[14].dictionaryValue eq '0' ? 'selected':'' }>天</option>
	                      <option value="1" ${dictionaryList[14].dictionaryValue eq '1' ? 'selected':'' }>小时</option>       
               </select>以后。
      </p>  
  	 <p><input type="checkbox" class="radio_op" id="r16" name="dictionaryList[16].isOpen" ${(empty dictionaryList[16].isOpen)||(dictionaryList[16].isOpen eq '0') ? '':'checked'}/>意向客户超过<input type="text" class="text_op" id="dictionaryList_16" ${(empty dictionaryList[16].isOpen)||(dictionaryList[16].isOpen eq '0') ? 'disabled':''} name="dictionaryList[16].dictionaryValue" value="${dictionaryList[16].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"/> 天未跟进，将自动回收到公海客户池中。
  	 </p>      	
      <p><input type="checkbox" class="radio_op" id="r21" name="dictionaryList[21].isOpen" ${(empty dictionaryList[21].isOpen)||(dictionaryList[21].isOpen eq '0') ? '':'checked'}/>允许个人拥有资源上限 
      	<input type="text" class="text_op" id="dictionaryList_21" ${(empty dictionaryList[21].isOpen)||(dictionaryList[21].isOpen eq '0') ? 'disabled':''} name="dictionaryList[21].dictionaryValue" value="${dictionaryList[21].dictionaryValue}" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"/> 人，到达上限将提示操作人员并限制添加客户操作。
      </p>
      <p><input type="checkbox" class="radio_op" id="r9" name="dictionaryList[9].isOpen" ${(empty dictionaryList[9].isOpen)||(dictionaryList[9].isOpen eq '0') ? '':'checked'}/>允许个人拥有意向客户上限 
      	<input type="text" class="text_op" id="dictionaryList_9" ${(empty dictionaryList[9].isOpen)||(dictionaryList[9].isOpen eq '0') ? 'disabled':''} name="dictionaryList[9].dictionaryValue" value="${dictionaryList[9].dictionaryValue}" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"/> 人，到达上限将提示操作人员并限制淘客户操作。
      </p>
  	 <p><input type="checkbox" class="radio_op" id="r7" name="dictionaryList[7].isOpen" ${(empty dictionaryList[7].isOpen)||(dictionaryList[7].isOpen eq '0') ? '':'checked'}/>允许完成客户签单的最长期限 
  	 	<input type="text" class="text_op" id="dictionaryList_7" ${(empty dictionaryList[7].isOpen)||(dictionaryList[7].isOpen eq '0') ? 'disabled':''} name="dictionaryList[7].dictionaryValue" value="${dictionaryList[7].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"/> 天，如超期将回收到公海客户池中，到期前
  	 	<input type="text" class="text_op" id="dictionaryList_8" ${(empty dictionaryList[8].isOpen)||(dictionaryList[8].isOpen eq '0') ? 'disabled':''} name="dictionaryList[8].dictionaryValue" value="${dictionaryList[8].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"/> 天进行提醒。
  	 </p>
   </div>
</div>


<!--客户防骚扰设置-->
<div class="setting_sale">
   <div class="setting_sale_title">
      <span class="txt">客户防骚扰设置</span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="on_33" radio="r" name="dictionaryList[33].dictionaryValue" value="1" ${dictionaryList[33].dictionaryValue eq '1'?'checked':''}/>
      	<label class="label_op" for="on_33">开启</label>
      </span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="off_33" radio="r" name="dictionaryList[33].dictionaryValue" value="0" ${dictionaryList[33].dictionaryValue eq '0'?'checked':''}/>
      	<label class="label_op" for="off_33">关闭</label>
      </span>
   </div>
   
   <div class="setting_sale_con" id="div_33" ${dictionaryList[33].dictionaryValue eq '0'?'style="display:none;"':''}>
	      <span class="txt" style="float:left;font-size:12px;color:#535353;">客户防骚扰选择</span>
	       <span class="ico_op">
	      	<input type="radio" class="radio_op" id="on_34" radio="r"  style="margin:0px 5px 0 0;*margin:-5px 5px 0 0;"name="dictionaryList[34].dictionaryValue" value="1" ${dictionaryList[34].dictionaryValue eq '1'?'checked':''}/>
	      	<label class="label_op" for="on_34">意向客户</label>
	      </span>
	      <span class="ico_op">
	      	<input type="radio" class="radio_op" id="off_34" radio="r" style="margin:0px 5px 0 0;*margin:-5px 5px 0 0;" name="dictionaryList[34].dictionaryValue" value="0" ${dictionaryList[34].dictionaryValue eq '0'?'checked':''}/>
	      	<label class="label_op" for="off_34">签约客户</label>
	      </span> 
   </div>
</div>

<div class="setting_sale">
   <div class="setting_sale_title">
      <span class="txt">有效呼叫设置</span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="on_3" radio="r" name="dictionaryList[3].dictionaryValue" value="1" ${dictionaryList[3].dictionaryValue eq '1'?'checked':''}/>
      	<label class="label_op" for="on_3">开启</label>
      </span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="off_3" radio="r" name="dictionaryList[3].dictionaryValue" value="0" ${dictionaryList[3].dictionaryValue eq '0'?'checked':''}/>
      	<label class="label_op" for="off_3">关闭</label>
      </span>
   </div>
   <div class="setting_sale_con" id="div_3" ${dictionaryList[3].dictionaryValue eq '0'?'style="display:none;"':''}>
  	 <p>电话接通 
  	 	<input type="text" class="text_op" name="dictionaryList[10].dictionaryValue" value="${dictionaryList[10].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"/> 秒后，视为有效通话。
  	 </p>
   </div>
</div>
<!--资源安全设置-->
<div class="setting_sale">
   <div class="setting_sale_title">
      <span class="txt">资源安全设置</span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="on_5" radio="r" name="dictionaryList[17].dictionaryValue" value="1" ${dictionaryList[17].dictionaryValue eq '1'?'checked':''}/>
      	<label class="label_op" for="on_5">开启</label>
      </span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="off_5" radio="r" name="dictionaryList[17].dictionaryValue" value="0" ${dictionaryList[17].dictionaryValue eq '0'?'checked':''}/>
      	<label class="label_op" for="off_5">关闭</label>
      </span>
   </div>
   <div class="setting_sale_con" id="div_5" ${dictionaryList[17].dictionaryValue eq '0'?'style="display:none;"':''}>
  	 <p>电话号码中间4位用"*"作模糊处理</p>
   </div>   
</div>

<!--呼叫区号设置-开始-->
<div class="setting_sale">
   <div class="setting_sale_title">
      <span class="txt">呼叫区号设置</span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="on_4" radio="r" name="dictionaryList[11].dictionaryValue" value="1" ${dictionaryList[11].dictionaryValue eq '1'?'checked':''}/>
      	<label class="label_op" for="on_4">开启</label>
      </span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="off_4" radio="r" name="dictionaryList[11].dictionaryValue" value="0" ${dictionaryList[11].dictionaryValue eq '0'?'checked':''}/>
      	<label class="label_op" for="off_4">关闭</label>
      </span>
   </div>
   <div class="setting_sale_con" id="div_4" ${dictionaryList[11].dictionaryValue eq '0'?'style="display:none;"':''}>
  	 <p>在7位或者8位固话号码前自动添加本地区号：
  	 <input type="text" class="text_op" maxlength="4" name="dictionaryList[12].dictionaryValue" value="${dictionaryList[12].dictionaryValue}" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"/></p>
   </div>
</div>
<!--呼叫区号设置-结束-->

<!--定时分配时长设置-开始-->
<div class="setting_sale">
   <div class="setting_sale_title">
      <span class="txt">定时分配时长设置</span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="on_23" radio="r" name="dictionaryList[23].dictionaryValue" value="1" ${dictionaryList[23].dictionaryValue eq '1'?'checked':''}/>
      	<label class="label_op" for="on_23">开启</label>
      </span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="off_23" radio="r" name="dictionaryList[23].dictionaryValue" value="0" ${dictionaryList[23].dictionaryValue eq '0'?'checked':''}/>
      	<label class="label_op" for="off_23">关闭</label>
      </span>
   </div>
   <div class="setting_sale_con" id="div_23" ${dictionaryList[23].dictionaryValue eq '0'?'style="display:none;"':''}>
  	 <p>每个帐号时长低于的临界值：
  	 	<input type="text" id="dictionaryList_24" class="text_op" maxlength="4" name="dictionaryList[24].dictionaryValue" value="${dictionaryList[24].dictionaryValue}" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"/>
  	 </p>
  	 <p>每个帐号每次定量分配的时长：
  		<input type="text" id="dictionaryList_25" class="text_op" maxlength="4" name="dictionaryList[25].dictionaryValue" value="${dictionaryList[25].dictionaryValue}" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"/>
  	 </p>
  	 <p>每个帐号每天可分配的时长：
  	 	<input type="text" id="dictionaryList_26" class="text_op" maxlength="4" name="dictionaryList[26].dictionaryValue" value="${dictionaryList[26].dictionaryValue}" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"/>
  	 </p>
   </div>
</div>
<!--定时分配时长设置-结束-->
<!--资源规则设置-开始-->
<div class="setting_sale">
   <div class="setting_sale_title">
      <span class="txt">客户信息字段格式校验设置</span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="on_27" radio="r" name="dictionaryList[27].dictionaryValue" value="1" ${dictionaryList[27].dictionaryValue eq '1'?'checked':''}/>
      	<label class="label_op" for="on_27">开启</label>
      </span>
      <span class="ico_op">
      	<input type="radio" class="radio_op" id="off_27" radio="r" name="dictionaryList[27].dictionaryValue" value="0" ${dictionaryList[27].dictionaryValue eq '0'?'checked':''}/>
      	<label class="label_op" for="off_27">关闭</label>
      </span>
   </div>
   <div class="setting_sale_con" id="div_27" ${dictionaryList[27].dictionaryValue eq '0'?'style="display:none;"':''}>
	      <span class="ico_op"><input type="checkbox" class="radio_op" id="r28" name="dictionaryList[28].dictionaryValue" ${(empty dictionaryList[28].dictionaryValue)||(dictionaryList[28].dictionaryValue eq '0') ? '':'checked'}/><label class="label_op_2" for="on_28">常用电话</label></span>
	      <span class="ico_op"><input type="checkbox" class="radio_op" id="r29" name="dictionaryList[29].dictionaryValue" ${(empty dictionaryList[29].dictionaryValue)||(dictionaryList[29].dictionaryValue eq '0') ? '':'checked'}/><label class="label_op_2" for="on_29">备用电话</label></span>
	      <span class="ico_op"> <input type="checkbox" class="radio_op" id="r30" name="dictionaryList[30].dictionaryValue" ${(empty dictionaryList[30].dictionaryValue)||(dictionaryList[30].dictionaryValue eq '0') ? '':'checked'}/><label class="label_op_2" for="on_30">备用电话2</label></span>
	      <span class="ico_op"><input type="checkbox" class="radio_op" id="r31" name="dictionaryList[31].dictionaryValue" ${(empty dictionaryList[31].dictionaryValue)||(dictionaryList[31].dictionaryValue eq '0') ? '':'checked'}/><label class="label_op_2" for="on_31">传真</label></span>
	     <span class="ico_op"> <input type="checkbox" class="radio_op" id="r32" name="dictionaryList[32].dictionaryValue" ${(empty dictionaryList[32].dictionaryValue)||(dictionaryList[32].dictionaryValue eq '0') ? '':'checked'}/><label class="label_op_2" for="on_32">邮箱</label></span>
   </div>
</div>
<!--资源规则设置-结束-->
<div class="margin_l500"><a class="save_submit_1" id="savemanageId" href="###"></a></div>
<div class="clr_both"></div>
<!-- 隐藏域  -->
<input type="hidden" name="dictionaryList[0].dictionaryId" value="${dictionaryList[0].dictionaryId}">
<input type="hidden" name="dictionaryList[1].dictionaryId" value="${dictionaryList[1].dictionaryId}">
<input type="hidden" name="dictionaryList[2].dictionaryId" value="${dictionaryList[2].dictionaryId}">
<input type="hidden" name="dictionaryList[3].dictionaryId" value="${dictionaryList[3].dictionaryId}">
<input type="hidden" name="dictionaryList[4].dictionaryId" value="${dictionaryList[4].dictionaryId}">
<input type="hidden" name="dictionaryList[5].dictionaryId" value="${dictionaryList[5].dictionaryId}">
<input type="hidden" name="dictionaryList[6].dictionaryId" value="${dictionaryList[6].dictionaryId}">
<input type="hidden" name="dictionaryList[7].dictionaryId" value="${dictionaryList[7].dictionaryId}">
<input type="hidden" name="dictionaryList[8].dictionaryId" value="${dictionaryList[8].dictionaryId}">
<input type="hidden" name="dictionaryList[9].dictionaryId" value="${dictionaryList[9].dictionaryId}">
<input type="hidden" name="dictionaryList[10].dictionaryId" value="${dictionaryList[10].dictionaryId}">
<!-- 需求后续追加 -->
<input type="hidden" name="dictionaryList[11].dictionaryId" value="${dictionaryList[11].dictionaryId}">
<input type="hidden" name="dictionaryList[12].dictionaryId" value="${dictionaryList[12].dictionaryId}">
<input type="hidden" name="dictionaryList[13].dictionaryId" value="${dictionaryList[13].dictionaryId}">
<input type="hidden" name="dictionaryList[14].dictionaryId" value="${dictionaryList[14].dictionaryId}">
<input type="hidden" name="dictionaryList[15].dictionaryId" value="${dictionaryList[15].dictionaryId}">
<input type="hidden" name="dictionaryList[16].dictionaryId" value="${dictionaryList[16].dictionaryId}">
<input type="hidden" name="dictionaryList[17].dictionaryId" value="${dictionaryList[17].dictionaryId}">
<input type="hidden" name="dictionaryList[18].dictionaryId" value="${dictionaryList[18].dictionaryId}">
<input type="hidden" name="dictionaryList[19].dictionaryId" value="${dictionaryList[19].dictionaryId}">
<input type="hidden" name="dictionaryList[20].dictionaryId" value="${dictionaryList[20].dictionaryId}">
<input type="hidden" name="dictionaryList[21].dictionaryId" value="${dictionaryList[21].dictionaryId}">
<input type="hidden" name="dictionaryList[22].dictionaryId" value="${dictionaryList[22].dictionaryId}">
<input type="hidden" name="dictionaryList[23].dictionaryId" value="${dictionaryList[23].dictionaryId}">
<input type="hidden" name="dictionaryList[24].dictionaryId" value="${dictionaryList[24].dictionaryId}">
<input type="hidden" name="dictionaryList[25].dictionaryId" value="${dictionaryList[25].dictionaryId}">
<input type="hidden" name="dictionaryList[26].dictionaryId" value="${dictionaryList[26].dictionaryId}">
<input type="hidden" name="dictionaryList[27].dictionaryId" value="${dictionaryList[27].dictionaryId}">
<input type="hidden" name="dictionaryList[28].dictionaryId" value="${dictionaryList[28].dictionaryId}">
<input type="hidden" name="dictionaryList[29].dictionaryId" value="${dictionaryList[29].dictionaryId}">
<input type="hidden" name="dictionaryList[30].dictionaryId" value="${dictionaryList[30].dictionaryId}">
<input type="hidden" name="dictionaryList[31].dictionaryId" value="${dictionaryList[31].dictionaryId}">
<input type="hidden" name="dictionaryList[32].dictionaryId" value="${dictionaryList[32].dictionaryId}">
<input type="hidden" name="dictionaryList[33].dictionaryId" value="${dictionaryList[33].dictionaryId}">
<input type="hidden" name="dictionaryList[34].dictionaryId" value="${dictionaryList[34].dictionaryId}">
</form>
	</tiles:putAttribute>
</tiles:insertDefinition>