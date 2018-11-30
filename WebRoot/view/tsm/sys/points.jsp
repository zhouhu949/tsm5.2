<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
<title>系统设置-销售成长等级设置</title>
<script type="text/javascript">
	var opera = true;
	function save_(){
		if(opera && isCheckNull()){
			opera = false;
			$('#pointForm').ajaxSubmit({
				url : '${ctx}/sys/points/setPoints.do',
				type : 'post',
				error : function(XMLHttpRequest, textStatus){alert("请求失败！");},
				success : function(data) {	
					opera = true
					if(data == 0){			
						window.top.iDialogMsg("提示","保存成功！");
					}else{
						window.top.iDialogMsg("提示","保存失败！");
					}
				}
			});
		}
	}
	
	// 积分范围做验证判断
	function setPointNum(id,value){
		var isTrue = true;
		if(value != null && value !=""){
			var $id_ = id.split("_")[0]
			var _$id = id.split("_")[1]
			if(_$id != null){
				if($id_ == "start"){
					var $end = $("#end_"+(parseInt(_$id)-parseInt(1))).val();
					if($end !=null && $end !=""){
						if(parseInt($end)>=parseInt(value)){
							isTrue = false;
							window.top.iDialogMsg("提示","要大于上一级结束的积分范围oo");
							$("#"+id).val("");
						}
						if(_$id != 5){
							var $end_1 = $("#end_"+parseInt(_$id)).val();
							if(parseInt($end_1)<parseInt(value)){
								isTrue = false;
								window.top.iDialogMsg("提示","要小于这一级结束的积分范围oo");
								$("#"+id).val("");
							}
						}
					}else{
						isTrue = false;
						window.top.iDialogMsg("提示","请保证前面的积分都填写好oo");
						$("#"+id).val("");
					}
					if(isTrue){
						// 设置上一级结束范围值 = 本级开始值 -1
						$("#end_"+(parseInt(_$id)-parseInt(1))).val(parseInt(value)-parseInt(1));
					}					
				}else if($id_ == "end"){
					var $start = $("#start_"+_$id).val();
					if($start !=null && $start !=""){
						if(parseInt($start)>parseInt(value)){
							isTrue = false;
							window.top.iDialogMsg("提示","要大于这一级开始的积分范围oo");
							$("#"+id).val("");
						}
						var $start_1 = $("#start_"+(parseInt(_$id)+parseInt(1))).val();
						if($start_1 !=null && $start_1 !=""){
							if(parseInt(value)>=parseInt($start_1)){
								isTrue = false;
								window.top.iDialogMsg("提示","要小于下一级开始的积分范围oo");
								$("#"+id).val("");
							}
						}						
					}
					if(isTrue){
						// 设置下一级开始范围值 = 本级结束值 +1
						$("#start_"+(parseInt(_$id)+parseInt(1))).val(parseInt(value)+parseInt(1));
					}					
				}
			}
		}
	} 
	
	// 判断是否为空
	function isCheckNull(){
		var isTrue = true;
		$("input[id^=end_],input[id^=start_],input[id^=dic_],input[id^=level_]").each(function(){
			var err = $(this).attr("id").split("_")[1]
			if($(this).val() == null || $(this).val() == ""){						
				$("#error_"+err).text("不能为空!");
				isTrue = false;
			}else{
				$("#error_"+err).text("");
			}
		});
		return isTrue;
	}
</script>
</head>
<body> 
<div class="hyx-na-tit fl_l"><label class="lab">销售成长等级设置</label></div>
<form id="pointForm" method="post">
<div class="hyx-sms hyx-rgs fl_l">
	<div class="com-table com-mta fl_l">
		<table width="100%" cellspacing="0" cellpadding="0" class="sty-borcolor-a sty-borcolor-c com-table-b com-radius">
			<thead>
				<tr class="sty-bgcolor-b">
					<th><span class="sp sty-borcolor-b">销售等级图标</span></th>
					<th><span class="sp sty-borcolor-b">销售等级</span></th> 
					<th>等级积分范围</th>
				</tr>
			</thead>
			<tbody>              
				<tr>
					<input type="hidden" name="points[0].level" value="1">
					<input type="hidden" name="points[0].sort" value="${map[1].sort}">
					<input type="hidden" name="points[0].pointsId" value="${map[1].pointsId}">
					<td><div class="overflow_hidden w70" title=""><i class="i_grade_a"></i></div></td>
					<td><div class="overflow_hidden w130" title=""><input type="text" id="level_t1" name="points[0].levelName" value="${map[1].levelName}" class="hyx-rgs-ipt" /></div>
							<span class="sp_tip_a" id="error_t1"></span>
					</td>
					<td><div class="overflow_hidden w250" title="">
					<input type="hidden" id="start_1" name="points[0].startNumber" value="0">
					<label class="hyx-rgs-lab">0</label><span class="hyx-rgs-sp"></span>
					<input type="text" id="end_1" name="points[0].endNumber" value="${map[1].endNumber}" maxlength="9" onblur="setPointNum('end_1',this.value=this.value.replace(/\D/g, ''));" class="hyx-rgs-ipta w-170" /></div>
					<span class="sp_tip" id="error_1"></span>
					</td>
				</tr>
				<tr class="sty-bgcolor-b">
					<input type="hidden" name="points[1].level" value="2">
					<input type="hidden" name="points[1].sort" value="${map[2].sort}">
					<input type="hidden" name="points[1].pointsId" value="${map[2].pointsId}">
					<td><div class="overflow_hidden w70" title=""><i class="i_grade_b"></i></div></td>
					<td><div class="overflow_hidden w130" title=""><input type="text" id="level_t2" name="points[1].levelName" value="${map[2].levelName}" class="hyx-rgs-ipt" /></div>
						<span class="sp_tip_a" id="error_t2"></span>
					</td>
					<td>
					<div class="overflow_hidden w250" title="">
					<input type="text" id="start_2" name="points[1].startNumber" value="${map[2].startNumber}" maxlength="9"   onblur="setPointNum('start_2',this.value=this.value.replace(/\D/g, ''));" class="hyx-rgs-ipta" />
					<span class="hyx-rgs-sp"></span>
					<input type="text" id="end_2" name="points[1].endNumber" value="${map[2].endNumber}" maxlength="9"   onblur="setPointNum('end_2',this.value=this.value.replace(/\D/g, ''));" class="hyx-rgs-ipta" />
					</div>
					<span class="sp_tip" id="error_2"></span>
					</td>
				</tr>
				<tr>
					<input type="hidden" name="points[2].level" value="3">
					<input type="hidden" name="points[2].sort" value="${map[3].sort}">
					<input type="hidden" name="points[2].pointsId" value="${map[3].pointsId}">
					<td><div class="overflow_hidden w70" title=""><i class="i_grade_c"></i></div></td>
					<td><div class="overflow_hidden w130" title=""><input type="text" id="level_t3"  name="points[2].levelName" value="${map[3].levelName}" class="hyx-rgs-ipt" /></div>
						<span class="sp_tip_a" id="error_t3"></span>
					</td>
					<td>
						<div class="overflow_hidden w250" title="">
						<input type="text" id="start_3" name="points[2].startNumber" value="${map[3].startNumber}" maxlength="9"  onblur="setPointNum('start_3',this.value=this.value.replace(/\D/g, ''));" class="hyx-rgs-ipta" />
						<span class="hyx-rgs-sp"></span>
						<input type="text" id="end_3" name="points[2].endNumber" value="${map[3].endNumber}" maxlength="9"   onblur="setPointNum('end_3',this.value=this.value.replace(/\D/g, ''));" class="hyx-rgs-ipta" />
						</div>
						<span class="sp_tip" id="error_3"></span>
					</td>
				</tr>
				<tr class="sty-bgcolor-b">
					<input type="hidden" name="points[3].level" value="4">
					<input type="hidden" name="points[3].sort" value="${map[4].sort}">
					<input type="hidden" name="points[3].pointsId" value="${map[4].pointsId}">
					<td><div class="overflow_hidden w70" title=""><i class="i_grade_d"></i></div></td>
					<td><div class="overflow_hidden w130" title=""><input type="text" id="level_t4"  name="points[3].levelName" value="${map[4].levelName}" class="hyx-rgs-ipt" /></div>
						<span class="sp_tip_a" id="error_t4"></span>
					</td>
					<td>
						<div class="overflow_hidden w250" title="">
						<input type="text" id="start_4" name="points[3].startNumber" value="${map[4].startNumber}" maxlength="9"  onblur="setPointNum('start_4',this.value=this.value.replace(/\D/g, ''));" class="hyx-rgs-ipta" />
						<span class="hyx-rgs-sp"></span>
						<input type="text" id="end_4" name="points[3].endNumber" value="${map[4].endNumber}" maxlength="9"   onblur="setPointNum('end_4',this.value=this.value.replace(/\D/g, ''));" class="hyx-rgs-ipta" />
						</div>
						<span class="sp_tip" id="error_4"></span>
					</td>
				</tr>
				<tr>
					<input type="hidden" name="points[4].level" value="5">
					<input type="hidden" name="points[4].sort" value="${map[5].sort}">
					<input type="hidden" name="points[4].pointsId" value="${map[5].pointsId}">
					<td><div class="overflow_hidden w70" title=""><i class="i_grade_e"></i></div></td>
					<td><div class="overflow_hidden w130" title=""><input type="text" id="level_t5"  name="points[4].levelName" value="${map[5].levelName}" class="hyx-rgs-ipt" /></div>
							<span class="sp_tip_a" id="error_t5"></span>
					</td>
					<td><div class="overflow_hidden w250" title=""><input type="text" id="start_5" name="points[4].startNumber" maxlength="9"  value="${map[5].startNumber}"  onblur="setPointNum('start_5',this.value=this.value.replace(/\D/g, ''));" class="hyx-rgs-ipta w-170" /><label class="hyx-rgs-lab">以上</label></div>
					<span class="sp_tip" id="error_5"></span>
					</td>					
				</tr>
			</tbody>
		</table>
	</div>
	<p class="tit">
		<label class="lab fl_l">积分获取规则</label>
	</p>
	<p class="tit_b">
		<label class="lab">销售每签单</label>
		<input type="text" id="dic_6"  name="dictionaryList[0].dictionaryValue" value="${dictionaryList[0].dictionaryValue}" maxlength="20" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt" />
		<label class="lab">元获得1积分奖励；</label>
		<span class="sp_tip" id="error_6"></span>
	</p>
	<p class="tit_b">
		<label class="lab">每月单项任务完成获得</label>
		<input type="text" id="dic_7"  name="dictionaryList[1].dictionaryValue" value="${dictionaryList[1].dictionaryValue}" maxlength="20" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt" />
		<label class="lab">积分奖励；</label>
		<span class="sp_tip" id="error_7"></span>
	</p>
	<p class="tit_b">
		<label class="lab">完成月度计划，获得</label>
		<input type="text" id="dic_8" name="dictionaryList[2].dictionaryValue" value="${dictionaryList[2].dictionaryValue}" maxlength="20" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt" />
		<label class="lab">积分奖励；</label>
		<span class="sp_tip" id="error_8"></span>
	</p>
	<p class="tit_b">
		<label class="lab">连续3个月完成月度计划，获得</label>
		<input type="text" id="dic_9" name="dictionaryList[3].dictionaryValue" value="${dictionaryList[3].dictionaryValue}" maxlength="20" onkeyup="this.value=this.value.replace(/\D/g, '');" onblur="this.value=this.value.replace(/\D/g, '');" class="ipt" />
		<label class="lab">积分奖励；</label>
		<span class="sp_tip" id="error_9"></span>
	</p>
	<!-- 隐藏域  -->
	<input type="hidden" name="dictionaryList[0].dictionaryId" value="${dictionaryList[0].dictionaryId}">
	<input type="hidden" name="dictionaryList[1].dictionaryId" value="${dictionaryList[1].dictionaryId}">
	<input type="hidden" name="dictionaryList[2].dictionaryId" value="${dictionaryList[2].dictionaryId}">
	<input type="hidden" name="dictionaryList[3].dictionaryId" value="${dictionaryList[3].dictionaryId}">
	<div class="com-btnbox">
		<a href="javascript:;" id="but_save" onclick="save_()" class="com-btna com-btna-sty"><label>保存</label></a>
	</div>
</div>
</form>
</body>
</html>