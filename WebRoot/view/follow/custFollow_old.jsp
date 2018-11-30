<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%> 


<title>客户跟进-跟进详细页面-企业</title>
<script type="text/javascript">
function disabledButton(click_btn){
	var $_this = $(click_btn);
	$_this.text("请稍候");
	$_this.attr("disabled","true");
	$_this.css("cursor","not-allowed");
}
$(function(){
    $("input:text:first").focus();


	// 预期时间 
	$('#expectDate').dateRangePicker({
		showShortcuts:false,
		autoClose:true,
		singleDate:true,
		showShortcuts:false,
		format:'YYYY-MM-DD HH:mm:ss',
		autoClose: false,
		time: {
			enabled: true
		}
	}).bind('datepicker-change',function(event,obj){
		$("#expectDate").val(obj.value);
	});

    /*card下拉*/
    $('.icon_down_a').click(function(){
    	var sty_hid = $(this).prev();
    	if($(this).attr('name') == 'up'){
    		$(this).css({'background':'url(${ctx}/static/images/down-alert.png) no-repeat'});
    		$(this).attr('name','down');
    		if($(this).hasClass('icon_down') == true){
    			$(this).parent().animate({'height':'130px'},1);
    		}	
    	}else{
    		if($(this).hasClass('icon_down') == true){
    			$(this).parent().css({'height':'auto'});
    		}
    		$(this).css({'background':'url(${ctx}/static/images/up-alert.png) no-repeat'});
    		$(this).attr('name','up');
    	}
    	sty_hid.slideToggle();
    });
    /*行动标签*/
    $('.tip-box').find('a').click(function(){
    	if($(this).hasClass('click-hover') == true){
    		$(this).removeClass('click-hover');
    	}else{
    		$(this).addClass('click-hover');
    	}
    	labelStr();
    });

    /*textarea提示信息*/
    $('.hyx-sce-area').each(function(i,item){
    	if($(item).find('textarea').val() != ''){
    		$(item).find('span').hide();
    	}
    });
    
    $('.hyx-sce-area').find('textarea,span').click(function(){
    	var ipt_a = $(this).parent().find('textarea');
        ipt_a.focus();
        $(this).parent().find('span').hide();
        ipt_a.blur(function(){
            if($(this).val() == ''){
                $(this).parent().find('span').show();
            }
         }); 
    });

    // 联系小记 
    $('.hyx-cm-new').find('.load').each(function(){
    	if($(this).find('.sp').text().length >= 40){
	    	$(this).find('.sp').text($(this).find('.sp').text().substr(0,40) + '...');
	    }
    });
    $('.hyx-cm-new').find('.load').mouseover(function(){
        $(this).find('.drop').fadeIn(1);
    });
    $('.hyx-cm-new').find('.load').mouseleave(function(){
        $(this).find('.drop').fadeOut(1);
    });
    
    
    /**  ********************自定义js 事件***********************  */
    // 异步加载 其他待跟进数据
    var planParam_ = $("#planParam").val();
    var url = "${ctx}/cust/custFollow/custFollowRightPage.do?planParam="+planParam_;
    if($("#isAlarm").val() == 1){ // 右侧待跟进数据 为跟进警报
    	var startPage = $("#startPage").val();
    	url = "${ctx}/cust/custFollow/custFollowAralmRightPage.do?startPage="+startPage;
    }
    var custType =$("#custType").val();
    var custCation =$("#custCation").val();
    var custId =$("#custId").val();
    $.ajax({
		url: url,
		type:'get',
		data:{'custType':custType,'custCation':custCation,'custId':custId},
		dataType:'html',
		error:function(){alert("网络异常，请稍后再操作！");},
		success:function(data){
			$('#wait_page_div').html(data);
		}
	});
    
 	// 异步加载 客户信息
 	var url2 = "${ctx}/cust/custFollow/custFollowEnterInfo.do";
 	if($("#state").val() == 0){ // 个人客户
 		url2 = "${ctx}/cust/custFollow/custFollowPersonInfo.do";
 	}
    $.ajax({
		url: url2,
		type:'get',
		data:{'custId':custId},
		dataType:'html',
		error:function(){alert("网络异常，请稍后再操作！");},
		success:function(data){
			$('#cust_info_div').html(data);
		}
	});
    
    // 异步加载 跟进记录
    var custId =$("#custId").val();
    $.ajax({
		url: '${ctx}/cust/custFollow/custFollowShowList.do',
		type:'POST',
		data:{'custId':custId},
		dataType:'html',
		error:function(){alert("网络异常，请稍后再操作！");},
		success:function(data){
			$('#show_followList').html(data);
		}
	});
    /** **************************** 保存 **********************************  */
    var isOpera = true; // 不可重复点击
    // 保存并继续下一条
    $("#saveBut").click(function(){
    	if(isOpera && checkIsNull()){
    		isOpera = false;
    		disabledButton(this);
    		// 查询客户是否变成公海客户
    		var url1 = ctx+"/cust/custFollow/getCustStatus";
    		$.ajax({
    			url: url1,
    			type:'POST',
    			data:{'custId':custId},
    			dataType:'json',
    			error:function(){alert("网络异常，请稍后再操作！");},
    			success:function(data){
    				if(data == 0){
    					queDivDialog("follw_save_cust","客户已入公海池，是否需要继续当前操作？",function(){
    		    			saveCustFollow("");
    		    		},
    		    		function(){
    		    			nextPage(); // 取下一条
    		    		});  
    				}else{
    					saveCustFollow("");
    				}
    			}
    		}); 		
    	}
    });
    
   // 保存并放弃 
    $("#canelBut").click(function(){
    	if(isOpera && checkIsNull()){
    		$("#custGiveUp").val("1"); // 有值表示是放弃 操作
    		isOpera = false;
    		// 查询客户是否变成公海客户
    		var url1 = ctx+"/cust/custFollow/getCustStatus";
    		$.ajax({
    			url: url1,
    			type:'POST',
    			data:{'custId':custId},
    			dataType:'json',
    			error:function(){alert("网络异常，请稍后再操作！");},
    			success:function(data){
    				if(data == 0){
    					queDivDialog("follw_save_cust","客户已入公海池，是否需要继续当前操作？",function(){
    		    			saveCustFollow("");
    		    		},
    		    		function(){
    		    			nextPage(); // 取下一条
    		    		});  
    				}else{
    					saveCustFollow("");
    				}
    			}
    		}); 		
    	}
    });
    
 	// 签约客户
    $("#signCustId").click(function(){
    	if(isOpera && checkIsNull()){
    		$("#custSign").val("1"); // 有值表示是签约 操作
    		disabledButton(this);
    		// 查询客户是否变成公海客户
    		var url1 = ctx+"/cust/custFollow/getCustStatus";
    		$.ajax({
    			url: url1,
    			type:'POST',
    			data:{'custId':custId},
    			dataType:'json',
    			error:function(){alert("网络异常，请稍后再操作！");},
    			success:function(data){
    				if(data == 0){
    					window.top.iDialogMsg("提示","客户已入公海池，无法签约！"); 
    					setTimeout('nextPage()',1000); // 取下一条
    				}else{
    					saveCustFollow("sign");
    				}
    			}
    		}); 	   		
    	}
    });
    
 	// 确定
    $('#setProId').click(function(){
    	setSuitPro();
    });
    // 清空
    $('#close02').click(function(){
    	$('#suitProNameId').text("");
    	$('#suitProcId').val("");
    	$("#manage_drop").hide();
    });
 	
 	/** 验证 */
    $('[checkProp^="chk_"]').each(function() {
		var chkTag = $(this); 			// 待验证元素
		var typeRules = $(this).attr('checkProp').split('_');
		var chkType = typeRules[1];		// 验证类型
		
		if(chkType=='1'){ // 通过blur事件验证
			chkTag.blur(function(){
				removeErrorClass($(this).attr("id"));
			});
		}else if(chkType=='2'){ // 通过change事件验证
			chkTag.change(function(){
				removeErrorClass($(this).attr("id"));
			});
		}
	});
 	
});

//  保存
function saveCustFollow(i){
	// 设值 联系人ID 企业客户才会有该值
	if($("#state").val() == 1){
		var tscidName = $("#tscidName").val();
		var tscMobile = $("#detail_telphone").val();
		if(tscidName != null){
			$("#main_tscName").val(tscidName);
		}
		if(tscMobile != null){
			$("#main_tscMobile").val(tscMobile);
		}
	}
	$("#myForm").ajaxSubmit({
		dataType:'json',
		type : 'post',
		error:function(){
			isOpera = true;
			alert(" 请求失败！");},
		success:function(data){
			isOpera = true;
			if(data == 0){		
				// 跳转至下一条
				nextPage();
				if(i == "sign"){ // 签约客户
					// 弹出新增合同tab
					window.top.addTab(ctx+"/contract/toAdd?custId="+$("#custId").val()+"&areFrom=1","新增合同");
				}				
			}else{
				window.top.iDialogMsg("提示","保存失败！");
			}  				
		}
	});	
}

// 选择适用产品
var setSuitPro = function(){
	var proId = "";
	var proName = '';
	$('input[id^=chk_]').each(function(index, obj) {
		if ($(obj).is(':checked')) { 
			proId += $(this).attr('id').split('_')[1] + ',';
			proName += $(this).attr('show')+ ',';
		}
	});
	$('#suitProNameId').text(proName);
	$('#suitProcId').val(proId);
	$("#manage_drop").hide();
	 removeErrorClass("suitProcId");
};


/** 行动标签 */
 var labelStr = function(){
	var tempLabel = '';
	var tempLabelName = '';
	$('.tip-box').find('a').each(function(item, obj){
    	if($(this).hasClass('click-hover') == true){
    		tempLabel = tempLabel+$(this).attr('id') + '#';
    		tempLabelName = tempLabelName + $(this).text() + '#';
    	}
	});
	$('#labelCodeId').val(tempLabel);
	$('#labelNameId').val(tempLabelName);
};

/**跳转至下一资源 */
var nextPage = function(){
	var nextCustId = $("#nextCustId").val(); // 下一条资源ID
	if(typeof(nextCustId)!="undefined" && nextCustId != null && nextCustId !=0){ // 设值
		$("#next_custId").val(nextCustId);
	}
	var nextStartPage = $("#nextStartPage").val(); // 下一页
	if(typeof(nextStartPage)!="undefined" && nextStartPage != null && nextStartPage !=0){ // 设值
		$("#startPage").val(nextStartPage);
	}
	$("#nextForm").submit();
};

// 验证是否为空
function checkIsNull(){
	var isTrue = true;
	$('[checkProp^="chk_"]').each(function() {
		var id =$(this).attr("id");
		if($(this).val() != null && $(this).val() != ""){
			$("#bomp_error_"+id).removeClass("bomp-error");
			$("#error_"+id).text("");
		}else{
			isTrue = false;
			$("#bomp_error_"+id).addClass("bomp-error");
			$("#error_"+id).text("必填项！");
		}
	});
	return isTrue;
}

// 移除 验证样式
function removeErrorClass(id){
	if($("#"+id).val() != null && $("#"+id).val() != ""){
		$("#bomp_error_"+id).removeClass("bomp-error");
		$("#error_"+id).text("");
	}
}

</script>

</head>
<body> 
<div class="hyx-sce hyx-fud">	
    <!-- 用于跳转 查询 -->
	<form action="${ctx}/cust/custFollow/custFollowPage.do" method="post" id="nextForm">
			<!-- 1:全部，2:意向客户，3:签约客户，4:流失客户，5:沉默客户 -->
		<input type="hidden"  id="custType" name="custType" value="${custType }">
		<!-- 跟进数据分类 -->
		<input type="hidden"  id="custCation" name="custCation" value="${custCation }">	
		<input type="hidden"  id="state" name="state" value="${state }"><!-- 客户类型：0：个人客户，1：企业客户 -->
		<input type="hidden" id="isAlarm" name="isAlarm" value="${isAlarm}" ><!-- 有值 表示从跟进警报页面点击触发 -->
		<input type="hidden" id="startPage" name="startPage" value="${startPage}" ><!-- 起始行号，用于跟进警报排序  -->	
		<input type="hidden"  id="next_custId" name="custId" value="${custId }"><!-- 资源ID -->
		<input  type="hidden" name="planParam" id="planParam" value="${planParam}"><!-- 1：只显示计划中客户，2：不显示计划中客户 -->
	</form>
		
	<form id="myForm" method="post" action="${ctx }/cust/custFollow/saveCustFollow.do">
		<input type="hidden" id="followId" name="custFollow.custFollowId" value="${followId }">
		<input type="hidden" id="main_tscName" name="custFollow.custDetailName"> <!-- 联系人 企业客户会有值 -->
		<input type="hidden" id="main_tscMobile" name="custFollow.custDetailMobile"><!-- 联系人号码 企业客户会有值 -->
	
		<input type="hidden"  id="custId" name="custId" value="${custId }"><!-- 资源ID -->
		<input type="hidden"  name="custFollow.lastSaleProcessId" value="${lastCustFollow.optionlistId }"><!--赋值上次销售进程ID -->
	
	<div class="hyx-sce-left fl_l">
		<div id="cust_info_div">
			<!-- 客户信息页面  /tsm4.0/WebRoot/view/follow/info_submodual/follow_custInfo.jsp -->
		</div>		
		<div class="hyx-sce-left-form fl_l">
		     <!--  销售导航 主键ID -->
			<input type="hidden" name="custGuide.custGuideId"  value="${tsmCustGuideEntity.custGuideId}">
			<div class='bomp_tit'><label class='lab'>销售信息</label></div>
			<div class="bomp-box">
				<p class='bomp-p'  id="bomp_error_custTypeId">
					<label class='lab_a fl_l'><i class="com-red">*</i>客户类型：</label>
					 <select class="sel_a fl_l" name="custGuide.custTypeId" id="custTypeId" checkProp="chk_2">							        
							<c:forEach items="${custTypeList}" var="custType"
								varStatus="vs">
								<option value="${custType.optionlistId}"
									<c:choose>
										<c:when test="${custType.optionlistId == tsmCustGuideEntity.custTypeId}">selected</c:when>
										<c:when test="${custType.isDefault == 1}">selected</c:when>
									</c:choose>
								>
									${custType.optionName}
								</option>
							</c:forEach>						         
					</select>
					<span class="error" id="error_custTypeId"></span>
				</p>
				<div class='bomp-p'  id="bomp_error_suitProcId">
					<input type="hidden" id="suitProcId" name ="suitProcId" value="${procIds }" checkProp="chk_"/><!-- 适用产品 -->
					<label class='lab_a fl_l'><i class="com-red">*</i>适用产品：</label>
					<div class="reso-sub-dep fl_l">
						<span class="sub-dep-inpu" style="border:0 none;width:245px;height:25px;line-height:25px;font-weight:normal;display:inline-block;margin-top:0;" id="suitProNameId">${procNames }</span>
						<div class="manage-drop" id="manage_drop">
							<div class="sub-dep-ul">
								<ul id="tt1" class="easyui-tree"  data-options="animate:false,dnd:false">
						            <c:forEach items="${suitProcList}" var="suitProc" varStatus="vs">								    
										<li><span><input type="checkbox" id="chk_${suitProc.id }" show="${suitProc.name}"  value="${suitProc.id}" <c:if test="${1 eq suitProc.isDefault }">checked</c:if> ><label>${suitProc.name}</label></span></li>
									</c:forEach>		
						        </ul>
					    	</div>
					   		 <div class="sure-cancle clearfix" style="width:120px">
								<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" href="javascript:;" id="setProId"><label>确定</label></a>
								<a class="com-btnd bomp-btna fl_l" id="close02" href="javascript:;"><label>清空</label></a>
							</div>
						</div>
					</div>
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
					<span class="error" id="error_custTypeId"></span>
				</p>
				<div class='bomp-p' id="bomp_error_actionDate">
					<label class='lab_a fl_l'><i class="com-red">*</i>联系时间：</label>
					<input type="text" id="actionDate" name="custFollow.actionDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  value="<fmt:formatDate value="${actionDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"  class="ipt"  checkProp="chk_"/>
				</div>
			</div>
			<div class="bomp-box">
				<p class='bomp-p' id="bomp_error_saleProcessId">
					<label class='lab_a fl_l'><i class="com-red">*</i>销售进程：</label>
					<select class="sel_a fl_l" name="custFollow.saleProcessId" id="saleProcessId" checkProp="chk_2" >
						<c:forEach items="${saleProcessList}" var="saleProcess" varStatus="vs">
							<option value="${saleProcess.optionlistId}"
								<c:choose>
								    <c:when test='${saleProcess.optionlistId eq lastCustFollow.optionlistId }'>
								         selected
								    </c:when>
								    <c:when test="${saleProcess.isDefault ==1 && (empty lastCustFollow.saleProcessId || '' eq  lastCustFollow.saleProcessId )}">
								         selected
								    </c:when>											    
								</c:choose>>
								${saleProcess.optionName}
							</option>
						</c:forEach>
					</select>
					<span class="error" id="error_saleProcessId"></span>
				</p>
				<p class='bomp-p' id="bomp_error_effectiveness">
					<label class='lab_a fl_l'><i class="com-red">*</i>联系有效性：</label>
					 <select class="sel_a fl_l" id="effectiveness" name="custFollow.effectiveness" checkProp="chk_2">
					      <option value="1" ${lastCustFollow.effectiveness eq '1'?'selected':'' }>有效联系</option>
					      <option value="0" ${lastCustFollow.effectiveness eq '0'?'selected':'' }>无效联系</option>
					</select>
					<span class="error" id="error_effectiveness"></span>
				</p>
			</div>
			<div class='bomp-p bomp-p-w'>
					<input type="hidden" value="${lastCustFollow.labelCode }" name="custFollow.labelCode" id="labelCodeId">
				<input type="hidden" value="${lastCustFollow.labelName }" name="custFollow.labelName" id="labelNameId">
				<label class='lab_a fl_l'>行动标签：</label>
				<div class="tip-box fl_l">
					<c:if test="${! empty label6List }">
						     <c:forEach items="${ label6List}" var="label" varStatus="vs">
						       <c:choose>
						          <c:when test="${fn:contains(lastCustFollow.labelCode, label.optionlistId)}">
						               <a href="###" class="click-hover" id="${label.optionlistId }">${label.optionName}</a>
						          </c:when>
						          <c:otherwise>
						                <a href="###" id="${label.optionlistId }">${label.optionName}</a>
						          </c:otherwise>
						       </c:choose>
						     </c:forEach>
						</c:if>
					<label class="1sty-hid fl_l">
						<c:if test="${! empty otberLabelList }">
								     <c:forEach items="${ otberLabelList}" var="label" varStatus="vs">
									       <c:choose>
									          <c:when test="${fn:contains(lastCustFollow.labelCode, label.optionlistId)}">
									               <a href="###" class="click-hover" id="${label.optionlistId }">${label.optionName}</a>
									          </c:when>
									          <c:otherwise>
									                <a href="###" id="${label.optionlistId }">${label.optionName}</a>
									          </c:otherwise>
									       </c:choose>
								     </c:forEach>
								</c:if>	
					</label>
					<i class="tip-more icon_down_a fl_l" name="down"></i>
				</div>
			</div>
			<div class='bomp-p bomp-p-w'   id="bomp_error_feedbackComment">
				<label class='lab_a fl_l'><i class="com-red">*</i>联系小记：</label>
				<label class="hyx-sce-area">
					<textarea class='area_a fl_l' id="feedbackComment" name="custFollow.feedbackComment" maxlength="4000" checkProp="chk_1"></textarea>
					<span>请输入与客户联系结果，最多可输入2000个汉字。</span>
				</label>
				<span class="error" id="error_feedbackComment"></span>
			</div>
			<div class="bomp-box">
				<p class='bomp-p' id="bomp_error_followType">
					<label class='lab_a fl_l'><i class="com-red">*</i>下次联系：</label>
					<select class="sel_a fl_l" name="custFollow.followType" id="followType" checkProp="chk_2">
						<option value="1" selected>电话联系</option>
						<option value="2">会客联系</option>
						<option value="3">客户来电</option>
						<option value="4">短信联系</option>
						<option value="5">QQ联系</option>
						<option value="6">邮件联系</option>
					</select>
					<span class="error" id="error_followType"></span>
				</p>
				<p class='bomp-p' id="bomp_error_followDate">
					<label class='lab_a fl_l'><i class="com-red">*</i>下次联系时间：</label>
					<input type="text" id="followDate" name="custFollow.followDate" value="${defDate}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="ipt"  checkProp="chk_"/>
					<span class="error" id="error_followDate"></span>
				</p>
			</div>
			<div class="sty-hid fl_l">
				<div class="bomp-box">
					<p class='bomp-p'>
						<label class='lab_a fl_l'>预期签单时间：</label>
						<input type="text" name="custGuide.expectDate" class="ipt" id="expectDate" value="<fmt:formatDate value="${tsmCustGuideEntity.expectDate}" />" />
					</p>
					<p class='bomp-p'>
						<label class='lab_a fl_l'>预期销售额：</label>
						<input type="text" name="custGuide.expectSale"  class="ipt"  value="${tsmCustGuideEntity.expectSale}"/>
					</p>
				</div>
				<p class='bomp-p bomp-p-w'>
					<label class='lab_a fl_l'>销售对策：</label>
					<input type="text" name="custGuide.saleWay" class="ipt bomp-ipt-w" value="${tsmCustGuideEntity.saleWay}" />
				</p>
				<p class='bomp-p bomp-p-w'>
					<label class='lab_a fl_l'>客户异议：</label>
					<input type="text" name="custGuide.custArgue" class="ipt bomp-ipt-w" value="${tsmCustGuideEntity.custArgue}" />
				</p>
				<p class='bomp-p bomp-p-w'>
					<label class='lab_a fl_l'>客户兴趣：</label>
					<input type="text" name="custGuide.custInterest" class="ipt bomp-ipt-w" value="${tsmCustGuideEntity.custInterest}" />
				</p>
				<p class='bomp-p bomp-p-w'>
					<label class='lab_a fl_l'>竞争对手：</label>
					<input type="text" name="custGuide.competitor"  class="ipt bomp-ipt-w" value="${tsmCustGuideEntity.competitor}"/>
				</p>
				<div class='bomp-p bomp-p-w'>
					<label class='lab_a fl_l'>备注：</label>
					<label class="hyx-sce-area">
						<textarea class='area_a fl_l' name="custGuide.remark">${tsmCustGuideEntity.remark}</textarea>
						<span>最多可输入200个汉字。</span>
					</label>
				</div>
			</div>
			<i class="tip-more icon_down_a form-more fl_l" name="down"></i>
		</div>
		<div class="com-btnbox hyx-sce-left-btnbox" style="width:310px;">
			<!-- <a href="javascript:;"  id="saveBut"  class="com-btnb fl_r">保存并继续</a> -->
			<!-- <button style="position:relative;font-size:18px;border-width:0;border-radius:5px;cursor:pointer;" id="saveBut"  class="com-btnb fl_l" onclick="return false;">保存并继续</button> -->
			
			<a href="javascript:;"  id="saveBut"  class="com-btnb com-btnb-sty fl_l">保存并继续</a>
			<c:if test="${custEntity.status != 6 and custEntity.status != 7 and custEntity.status != 8 }">
				<input type="hidden" name="custGiveUp" id="custGiveUp" ><!--有值 表示是放弃操作 -->
				<a href="javascript:;"  id="canelBut"  class="com-btnb fl_l" style="margin-left:8px;">保存并放弃</a>
			
				<input type="hidden" name="custSign" id="custSign" ><!--有值 表示是签约操作 -->
				<!-- <a href="javascript:;" id="signCustId" class="com-btnb com-btnb-sty fl_l">签&nbsp;&nbsp;约</a> -->
				<!-- <button style="position:relative;font-size:18px;border-width:0;border-radius:5px;cursor:pointer;" id="signCustId"  class="com-btnb com-btnb-sty fl_r" onclick="return false;">签&nbsp;&nbsp;约</button> -->
				<a href="javascript:;" id="signCustId" class="com-btnb fl_r">签&nbsp;&nbsp;约</a>
			</c:if>						
		</div>
	</div>
	</form>
	<div class="hyx-sce-right fl_r" style="margin-top:25px;position:relative;">
		<div id="wait_page_div">
			<!-- 待跟进页面 /tsm4.0/WebRoot/view/follow/info_submodual/follow_wait.jsp -->
		</div>
		<div class="hyx-cm-new fl_l">
			<div class="top">
				<label>客户跟进记录</label><i></i>
			</div>
			<div id="show_followList">
				<!-- 客户跟进记录页面 /tsm4.0/WebRoot/view/follow/info_submodual/follow_lists.jsp -->
			</div>
		</div>
	</div>
</div>
<!-- 在线客服 -->
<iframe style="display:none;" class="qq_iframe" src=""></iframe>
</body>
</html>