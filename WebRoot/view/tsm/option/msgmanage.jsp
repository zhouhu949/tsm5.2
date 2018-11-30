<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>系统设置-消息设置</title>
<script type="text/javascript" src="${ctx}/static/js/view/tsm/option/msgmanage.js"></script>
</head>
<body> 
<div class="hyx-na-tit fl_l"><label class="lab">消息中心设置</label></div>
<form id="myForm" action="${ctx}/msg/manage/msgmanage" method="post">
<div class="hyx-sms hyx-ms fl_l">
	<p class="tit">
		<label class="lab fl_l"><i class="tip"></i>客户联系设置</label>
	</p>
	<p class="tit_a">
		<label class="lab">客户跟进提前：</label>
		<select class="sel" id="dictionaryList_8" name="dictionaryList[8].dictionaryValue">
			<option value="5"   ${dictionaryList[8].dictionaryValue eq '5' ? 'selected':'' }>5分钟</option>
			<option value="10" ${dictionaryList[8].dictionaryValue eq '10' ? 'selected':'' }>10分钟</option>
			<option value="15" ${dictionaryList[8].dictionaryValue eq '15' ? 'selected':'' }>15分钟</option>
			<option value="20" ${dictionaryList[8].dictionaryValue eq '20' ? 'selected':'' }>20分钟</option>
		</select>
		<label class="lab">发送提醒。</label>
		<input type="hidden" id="dic_0" name="dictionaryList[0].dictionaryValue" value="${dictionaryList[0].dictionaryValue}">
		<i class="switch fl_r ${dictionaryList[0].dictionaryValue eq '1'?'':'switch-hover'}"   id="on_0" name="${dictionaryList[0].dictionaryValue eq '1'?'on':'off'}"  ></i>
	</p>
	<p class="tit_a">
		<label class="lab">延后呼叫提前：</label>
		<select class="sel"  id="dictionaryList_9" name="dictionaryList[9].dictionaryValue">
				<option value="5"   ${dictionaryList[9].dictionaryValue eq '5' ? 'selected':'' }>5分钟</option>
				<option value="10" ${dictionaryList[9].dictionaryValue eq '10' ? 'selected':'' }>10分钟</option>
				<option value="15" ${dictionaryList[9].dictionaryValue eq '15' ? 'selected':'' }>15分钟</option>
				<option value="20" ${dictionaryList[9].dictionaryValue eq '20' ? 'selected':'' }>20分钟</option>
		</select>
		<label class="lab">发送提醒。</label>
		<input type="hidden" id="dic_1" name="dictionaryList[1].dictionaryValue" value="${dictionaryList[1].dictionaryValue}">
		<i class="switch fl_r ${dictionaryList[1].dictionaryValue eq '1'?'':'switch-hover'}" id="on_1" name="${dictionaryList[1].dictionaryValue eq '1'?'on':'off'}" ></i>
	</p>
	<p class="tit_a">
		<label class="lab">跟进警报提前：</label>
		<select class="sel" id="dictionaryList_10" name="dictionaryList[10].dictionaryValue">
			<option value="1" ${dictionaryList[10].dictionaryValue eq '1' ? 'selected':'' }>1天</option>
			<option value="2" ${dictionaryList[10].dictionaryValue eq '2' ? 'selected':'' }>2天</option>
			<option value="3" ${dictionaryList[10].dictionaryValue eq '3' ? 'selected':'' }>3天</option>
			<option value="4" ${dictionaryList[10].dictionaryValue eq '4' ? 'selected':'' }>4天</option>
		</select>
		<label class="lab">发送提醒。</label>
		<input type="hidden" id="dic_2" name="dictionaryList[2].dictionaryValue" value="${dictionaryList[2].dictionaryValue}">
		<i class="switch fl_r ${dictionaryList[2].dictionaryValue eq '1'?'':'switch-hover'}" id="on_2" name="${dictionaryList[2].dictionaryValue eq '1'?'on':'off'}"></i>
	</p>
	<p class="tit_a">
		<label class="lab">意向客户放弃提前：</label>
		<select class="sel" id="dictionaryList_11" name="dictionaryList[11].dictionaryValue">
			<option value="1" ${dictionaryList[11].dictionaryValue eq '1' ? 'selected':'' }>1天</option>
			<option value="2" ${dictionaryList[11].dictionaryValue eq '2' ? 'selected':'' }>2天</option>
			<option value="3" ${dictionaryList[11].dictionaryValue eq '3' ? 'selected':'' }>3天</option>
			<option value="4" ${dictionaryList[11].dictionaryValue eq '4' ? 'selected':'' }>4天</option>
		</select>
		<label class="lab">发送提醒。</label>
		<input type="hidden" id="dic_11" name="dictionaryList[11].isOpen" value="${dictionaryList[11].isOpen}">
		<i class="switch fl_r ${dictionaryList[11].isOpen eq '1'?'':'switch-hover'}" id="on_11" name="${dictionaryList[11].isOpen eq '1'?'on':'off'}"></i>
	</p>
	<p class="tit_a">
		<label class="lab">客户回访提前：</label>
		<select class="sel"  id="dictionaryList_12" name="dictionaryList[12].dictionaryValue">
				<option value="5"   ${dictionaryList[12].dictionaryValue eq '5' ? 'selected':'' }>5分钟</option>
				<option value="10" ${dictionaryList[12].dictionaryValue eq '10' ? 'selected':'' }>10分钟</option>
				<option value="15" ${dictionaryList[12].dictionaryValue eq '15' ? 'selected':'' }>15分钟</option>
				<option value="20" ${dictionaryList[12].dictionaryValue eq '20' ? 'selected':'' }>20分钟</option>
		</select>
		<label class="lab">发送提醒。</label>
		<input type="hidden" id="dic_12" name="dictionaryList[12].isOpen" value="${dictionaryList[12].isOpen}">
		<i class="switch fl_r ${dictionaryList[12].isOpen eq '1'?'':'switch-hover'}" id="on_12" name="${dictionaryList[12].isOpen eq '1'?'on':'off'}" ></i>
	</p>
	<p class="tit_a">
		<label class="lab">资源放弃提前：</label>
		<select class="sel" id="dictionaryList_14" name="dictionaryList[14].dictionaryValue">
			<option value="1"   ${dictionaryList[14].dictionaryValue eq '1' ? 'selected':'' }>1天</option>
			<option value="2" ${dictionaryList[14].dictionaryValue eq '2' ? 'selected':'' }>2天</option>
			<option value="3" ${dictionaryList[14].dictionaryValue eq '3' ? 'selected':'' }>3天</option>
			<option value="4" ${dictionaryList[14].dictionaryValue eq '4' ? 'selected':'' }>4天</option>
		</select>
		<label class="lab">发送提醒。</label>
		<input type="hidden" id="dic_14" name="dictionaryList[14].isOpen" value="${dictionaryList[14].isOpen}">
		<i class="switch fl_r ${dictionaryList[14].isOpen eq '1'?'':'switch-hover'}"   id="on_14" name="${dictionaryList[14].isOpen eq '1'?'on':'off'}"  ></i>
	</p>
	<p class="tit_a">
		<label class="lab">签约客户放弃提前：</label>
		<select class="sel" id="dictionaryList_15" name="dictionaryList[15].dictionaryValue">
			<option value="1"   ${dictionaryList[15].dictionaryValue eq '1' ? 'selected':'' }>1天</option>
			<option value="2" ${dictionaryList[15].dictionaryValue eq '2' ? 'selected':'' }>2天</option>
			<option value="3" ${dictionaryList[15].dictionaryValue eq '3' ? 'selected':'' }>3天</option>
			<option value="4" ${dictionaryList[15].dictionaryValue eq '4' ? 'selected':'' }>4天</option>
		</select>
		<label class="lab">发送提醒。</label>
		<input type="hidden" id="dic_15" name="dictionaryList[15].isOpen" value="${dictionaryList[15].isOpen}">
		<i class="switch fl_r ${dictionaryList[15].isOpen eq '1'?'':'switch-hover'}"   id="on_15" name="${dictionaryList[15].isOpen eq '1'?'on':'off'}"  ></i>
	</p>
	<p class="tit">
		<label class="lab fl_l"><i class="tip"></i>续费提醒设置</label>
	</p>
	<%-- <p class="tit_a">
		<label class="lab">短信剩余量为：</label>
		<input type="text" class="ipt" name="dictionaryList[4].dictionaryValue" value="${dictionaryList[4].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" />
		<label class="lab">条时发送提醒。</label>
		<input type="hidden" id="dic_4" name="dictionaryList[4].isOpen" value="${dictionaryList[4].isOpen}">
		<i class="switch fl_r ${dictionaryList[4].isOpen eq '1'?'':'switch-hover'}" id="on_4" name="${dictionaryList[4].isOpen eq '1'?'on':'off'}"></i>
	</p> --%>
	<p class="tit_a">
		<label class="lab">坐席剩余时间：</label>
		<input type="text" class="ipt" name="dictionaryList[5].dictionaryValue" value="${dictionaryList[5].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" />
		<label class="lab">天时发送提醒。</label>
		<input type="hidden" id="dic_5" name="dictionaryList[5].isOpen" value="${dictionaryList[5].isOpen}">
		<i class="switch fl_r ${dictionaryList[5].isOpen eq '1'?'':'switch-hover'}" id="on_5" name="${dictionaryList[5].isOpen eq '1'?'on':'off'}"></i>
	</p>
	<p class="tit_a">
		<label class="lab">订单剩余时间：</label>
		<input type="text" class="ipt" name="dictionaryList[7].dictionaryValue" value="${dictionaryList[7].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"/>
		<label class="lab">天时发送提醒。</label>
		<input type="hidden" id="dic_7" name="dictionaryList[7].isOpen" value="${dictionaryList[7].isOpen}">
		<i class="switch fl_r ${dictionaryList[7].isOpen eq '1'?'':'switch-hover'}" id="on_7" name="${dictionaryList[7].isOpen eq '1'?'on':'off'}"></i>
	</p>
	<p class="tit">
		<label class="lab fl_l"><i class="tip"></i>短信消息设置</label>
	</p>
	<p class="tit_a">
		<label class="lab">未接来电短信提醒。</label>		
		<input type="hidden" id="dic_13" name="dictionaryList[13].isOpen" value="${dictionaryList[13].isOpen}">
		<i class="switch fl_r ${dictionaryList[13].isOpen eq '1'?'':'switch-hover'}" id="on_13" name="${dictionaryList[13].isOpen eq '1'?'on':'off'}"></i>
	</p>
	<!--
	<p class="tit">
		<label class="lab fl_l"><i class="tip"></i>月计划提交设置</label>
	</p>
	<p class="tit_a">
		<label class="lab">每月计划在月底前</label>
		<input type="text" class="ipt" name="dictionaryList[3].dictionaryValue" value="${dictionaryList[3].dictionaryValue}" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');"/>
		<label class="lab">天未提交时发送提醒。</label>
	</p>
	  -->
	
	<div class="com-btnbox">
		<a href="javascript:;"  id="savemanageId"  class="com-btna com-btna-sty"><label>保存</label></a>
	</div>
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
<input type="hidden" name="dictionaryList[11].dictionaryId" value="${dictionaryList[11].dictionaryId}">
<input type="hidden" name="dictionaryList[12].dictionaryId" value="${dictionaryList[12].dictionaryId}">
<input type="hidden" name="dictionaryList[13].dictionaryId" value="${dictionaryList[13].dictionaryId}">				
<input type="hidden" name="dictionaryList[14].dictionaryId" value="${dictionaryList[14].dictionaryId}">
<input type="hidden" name="dictionaryList[15].dictionaryId" value="${dictionaryList[15].dictionaryId}">
</div>
 </form>
</body>
</html>