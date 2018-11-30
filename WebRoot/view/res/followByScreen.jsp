<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%> 

<title>客户跟进-跟进详细页面-企业</title>
<style type="text/css">
body{background-color:#f2f2f2 !important;}
.hyx-sce-left-form .reso-sub-dep .manage-drop .sub-dep-ul.sub-dep-followByScreen{margin-left:0;}
.hyx-sce-left-form .tree-node{margin-left:0;width:100%;}
.hyx-sce-left-form .tree-title input{margin-top:0; vertical-align: middle;}
.tree-title{ width: 80%; overflow: hidden; white-space: nowrap;text-overflow: ellipsis;}
</style>
</head>
<body> 
	<form id="myForm" method="post" action="${ctx}/res/cust/addMyCustByScreen">
	<input type='hidden' name='phone' id='phone' value='${phone}'>
	<input type="hidden" id="followId" name="custFollow.custFollowId" value="${followId}">
	<input type="hidden" id="main_tscidId" name="custFollow.custDetailId"> <!-- 联系人ID 企业客户会有值 -->
	<input type="hidden"  id="custId" name="custId" value="${custId}"><!-- 资源ID -->
	<input type="hidden"  name="custFollow.lastSaleProcessId" value="${lastCustFollow.optionlistId}"><!--赋值上次销售进程ID -->
<%-- 	<input type="hidden" name="custGuide.custGuideId"  value="${tsmCustGuideEntity.custGuideId}"> --%>
	<input type="hidden" name="isSaveDetail" id='isSaveDetail' value="${isSaveDetail}">
	<input type="hidden" name="isSave"  id='name' value="${isSave}">
<div class="year-sale-play hyx-hsrs-top">
	<ul class="ann-sale-plan clearfix">
		<a href="${ctx}/res/cust/toAddResByScreen?isSaveDetail=${isSaveDetail}&isSave=${isSave}&phone=${phone}"><li class="li_first" name="a">客户信息</li></a>
		<a href="${ctx}/res/cust/toFollowByScreen?isSaveDetail=${isSaveDetail}&isSave=${isSave}&phone=${phone}"><li class="select li_last" name="b">跟进信息</li></a>
	</ul>
</div>
<div class="hyx-bomp-fllow" style="height:435px;overflow-x:hidden;overflow-y:auto;">
	<div class="hyx-sce-left-form">
		<div class='bomp_tit'><label class='lab'>销售信息</label></div>
			<div class="bomp-box">
				<p class='bomp-p'  id="bomp_error_custTypeId">
					<label class='lab_a fl_l'><i class="com-red">*</i>客户类型：</label>
					 <select class="sel_a fl_l" name="custGuide.custTypeId" id="custTypeId" checkProp="chk_2">							        
							<c:forEach items="${custTypeList}" var="custType"
								varStatus="vs">
								<option value="${custType.optionlistId}"
									<c:if test="${custType.isDefault == 1}">selected</c:if>>
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
						<span style="border:0 none;width:245px;height:25px;line-height:25px;font-weight:normal;display:inline-block;margin-top:0;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;" id="suitProNameId" title="${procNames }">${procNames }</span>
						<div class="manage-drop" id="manage_drop">
							<div class="sub-dep-ul sub-dep-followByScreen">
								<ul id="tt1" class="easyui-tree"  data-options="animate:false,dnd:false">
						            <c:forEach items="${suitProcList}" var="suitProc" varStatus="vs">								    
										<li><span><input type="checkbox" id="chk_${suitProc.id }" show="${suitProc.name}"  value="${suitProc.id}" <c:if test="${1 eq suitProc.isDefault }">checked</c:if> ><label title="${suitProc.name}">${suitProc.name}</label></span></li>
									</c:forEach>	
						        </ul>
					    	</div>
					   		 <div class="sure-cancle clearfix" style="width:120px">
								<a class="com-btnc bomp-btna com-btna-sty reso-alloc-sure fl_l" href="###" id="setProId"><label>确定</label></a>
								<a class="com-btnd bomp-btna fl_l" id="close02" href="###"><label>清空</label></a>
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
					<input type="text" id="actionDate" name="custFollow.actionDate" value="<fmt:formatDate value="${actionDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="ipt"  checkProp="chk_" readOnly="readonly"/>
				</div>
			</div>
			<div class="bomp-box">
				<p class='bomp-p' id="bomp_error_saleProcessId">
					<label class='lab_a fl_l'><i class="com-red">*</i>销售进程：</label>
					<select class="sel_a fl_l" name="custFollow.saleProcessId" id="saleProcessId" checkProp="chk_2" >
						<c:forEach items="${saleProcessList}" var="saleProcess" varStatus="vs">
							<option value="${saleProcess.optionlistId}"
								<c:choose>
								    <c:when test='${saleProcess.optionlistId eq lastCustFollow.saleProcessId }'>
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
					<c:choose>
						<c:when test="${isSelect == 0 }">
							<c:if test="${! empty label6List }">
							     <c:forEach items="${ label6List}" var="label" varStatus="vs">
							       <c:choose>
							          <c:when test="${fn:contains(lastCustFollow.labelCode, label.optionlistId)}">
							               <a href="###" class="show-all-label click-hover" id="${label.optionlistId }">${label.optionName}</a>
							          </c:when>
							          <c:otherwise>
							                <a href="###" class="show-all-label" id="${label.optionlistId }">${label.optionName}</a>
							          </c:otherwise>
							       </c:choose>
							     </c:forEach>
							</c:if>
							<label class="1sty-hid fl_l">
								<c:if test="${! empty otberLabelList }">
										     <c:forEach items="${ otberLabelList}" var="label" varStatus="vs">
											       <c:choose>
											          <c:when test="${fn:contains(lastCustFollow.labelCode, label.optionlistId)}">
											               <a href="###" class="show-all-label click-hover" id="${label.optionlistId }">${label.optionName}</a>
											          </c:when>
											          <c:otherwise>
											                <a href="###" class="show-all-label" id="${label.optionlistId }">${label.optionName}</a>
											          </c:otherwise>
											       </c:choose>
										     </c:forEach>
										</c:if>
							</label>
							<i class="tip-more icon_down_a fl_l" name="down"></i>
						</c:when>
						<c:otherwise>
							<a href="javascript:;" class="choose-tag" data-chosen-id ="${labelIds }">选择标签</a>
							<c:if test="${! empty labelList }">
							     <c:forEach items="${ labelList}" var="label" varStatus="vs">
								       <c:choose>
								          <c:when test="${fn:contains(labelIds, label.optionlistId)}">
								               <a href="###" class="click-hover" id="${label.optionlistId }">${label.optionName}</a>
								          </c:when>
								       </c:choose>
							     </c:forEach>
							</c:if>
						</c:otherwise>
					</c:choose>
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
					<label class='lab_a fl_l'><c:if test="${nextFollowValidation eq 1 }"><i class="com-red">*</i></c:if>下次联系：</label>
					<select class="sel_a fl_l" name="custFollow.followType" id="followType" <c:if test="${nextFollowValidation eq 1 }">  checkProp="chk_2" </c:if>>
						<option value="" ${nextFollowValidation eq 0 ? '' : 'selected' }>-请选择-</option>
						<option value="1" ${nextFollowValidation eq 1 ? 'selected' : '' }>电话联系</option>
						<option value="2">会客联系</option>
						<option value="3">客户来电</option>
						<option value="4">短信联系</option>
						<option value="5">QQ联系</option>
						<option value="6">邮件联系</option>
					</select>
					<span class="error" id="error_followType"></span>
				</p>
				<p class='bomp-p' id="bomp_error_followDate">
					<label class='lab_a fl_l'><c:if test="${nextFollowValidation eq 1 }"><i class="com-red">*</i></c:if>下次联系时间：</label>
					<input type="text" id="followDate" name="custFollow.followDate" value="${nextFollowValidation eq 1 ? defDate : ''}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})" class="ipt"  <c:if test="${nextFollowValidation eq 1 }">checkProp="chk_"</c:if> readOnly="readonly"/>
					<span class="error" id="error_followDate"></span>
				</p>
			</div>
			
<!-- 			<div class="bomp-box">
				<p class='bomp-p'>
					<label class='lab_a fl_l'>关联销售机会：</label>
					<select id="saleChanceId" class="sel_a fl_l" name="custFollow.saleChanceId" ></select>
				</p>
			</div> -->
			
			<!--  
			<div class="bomp-box">
				<p class='bomp-p'>
					<label class='lab_a fl_l'>预期签单时间：</label>
					<input type="text" id="expectDate" name="custGuide.expectDate" value="<fmt:formatDate value="${tsmCustGuideEntity.expectDate}" />"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="ipt" />
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
			-->
		</div>
		<div class="com-btnbox hyx-sce-left-btnbox">
			<button  id="saveBut"  type="button" class="com-btnb com-btnb-sty">保存并继续</button>
		</div>
	</div>
	</form>
<script type="text/javascript">
$(function(){
	/* 解决客户端弹框没有window.top问题 */
	window.top={
		iDialogMsg:iDialogMsg
	};
	//屏蔽掉脚本错误
	function onerrorFun(err){
		//alert(JSON.stringify(err))
		return true;
	}
	window.onerror=onerrorFun;
/* 	window.onerror = function(errorMessage, scriptURI, lineNumber,columnNumber,errorObj) {
        alert("错误信息：" + errorMessage+ "/n/r"+"出错文件：" + scriptURI+"/n/r出错行号：" + lineNumber+ "/n/r出错列号：" + columnNumber+"/n/r错误详情：" , errorObj);
	} */
	saleChanceInit($("#custId").val());
	
	/*card下拉*/
    $('.icon_down,.icon_down_a').each(function(i,item){
    	if($(item).prev().hasClass('sty-hid') == true){
    		$(item).show();
    	}else{
    		$(item).hide();
    	}
    });

    $('.icon_down,.icon_down_a').click(function(){
    	var sty_hid = $(this).prev();
    	if($(this).attr('name') == 'up'){
    		$(this).css({'background':'url(${ctx}/static/images/down-alert.png) no-repeat'});
    		$(this).attr('name','down');	
    		sty_hid.slideUp();
    	}else{
    		if($(this).hasClass('icon_down') == true){
    			$(this).parent().css({'height':'auto'});
    		}
    		$(this).css({'background':'url(${ctx}/static/images/up-alert.png) no-repeat'});
    		$(this).attr('name','up');
    		sty_hid.slideDown();
    	}
    });
    /*行动标签*/
    $('.tip-box').find('a.show-all-label').click(function(){
    	if($(this).hasClass('click-hover') == true){
    		$(this).removeClass('click-hover');
    	}else{
    		$(this).addClass('click-hover');
    	}
    	labelStr();
    });

    /*textarea提示信息*/
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
    
    
   
    /** **************************** 保存 **********************************  */
    // 保存
    $("#saveBut").click(function(){
    	$(this).attr('disabled',true);
    	var isSave = '${isSave}';
    	if(isSave !='1'){
    		$(this).attr('disabled',false);
    		window.top.iDialogMsg("提示","请添加客户信息！");
    		return ;
    	}
    	if( checkIsNull()){
    		saveCustFollow("");
    	}else{
    		$(this).attr('disabled',false);
    	}
    });
    
    // 适用产品点击
    $("#suitProNameId").click(function(){
    	$("#manage_drop").toggle();
    });
    
    if($('#suitProNameId').text() == "" || $('#suitProNameId').text() == null){
    	$('#suitProNameId').text("--请选择--");
    }
    
 	// 确定
    $('#setProId').click(function(){
    	setSuitPro();
    })
    // 清空
    $('#close02').click(function(){
    	$('#suitProNameId').text("--请选择--");
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
	
 	$(".tip-box").delegate(".choose-tag","click",function(e){
		var labelCodeId = $(this).attr("data-chosen-id")?$(this).attr("data-chosen-id"):"";
		pubDivShowIframe('alert_action_tag_choose',ctx+'/res/tao/toActionTagChoose?optionlistIds='+labelCodeId,'行动标签',610,420);
	});
});

function saleChanceInit(custId){
	$.ajax({
		type: "get",
		url: ctx+"/cust/saleChance/getSaleChanceByCustId",
		data:{
			"custId": custId
		},
		success: function(data){
			var appendCode = '<option value="">-请选择-</option>';
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					appendCode += '<option value="'+data[i].saleChanceId+'">'+data[i].saleChanceName+'</option>';
				}
			}
			$("#saleChanceId").html(appendCode);
		},
		error: function(e){}
	});
}

//  保存
function saveCustFollow(i){
	$("#myForm").ajaxSubmit({
		dataType:'json',
		type : 'post',
		error:function(){
			alert(" 请求失败！");},
		success:function(data){
			if(data == 0){
				window.top.iDialogMsg("提示","保存成功！");	
				setTimeout(function(){
					external.OnCloseDlg(0);
				},500);
				return;
			}else{
	    		$(this).attr('disabled',false);
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
	$('#suitProNameId').text(proName).attr("title",proName);
	$('#suitProcId').val(proId);
	$("#manage_drop").hide();
	 removeErrorClass("suitProcId");
}


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
		$('#labelCodeId').val(tempLabel.replace(/#$/g,""));
		$('#labelNameId').val(tempLabelName.replace(/#$/g,""));
	}



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
<script type="text/javascript">
window.onload=function(){
	var height = $(".bomp_ical").height();
	window.parent.$("#iframepage").css({"height":height+"px"});
};
</script>
	</body>
	</html>