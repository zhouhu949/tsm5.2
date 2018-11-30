<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<%@ include file="/common/include.jsp"%>
	<title>客户跟进-跟进详细页面-企业</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/custCard_custFollow.css${_v}">
	<style>
		<c:if test="${extension == 1 }">
			.bomp-saleChance {display:none;}
			.bomp-extension-tip{display:none;text-align:center;padding-top:190px;}
			.img-extension {width:64px;height:64px;;}
			.tip-content{padding:10px 0;font-size:16px;}
		</c:if>
	</style>
</head>
<body>
	<form id="myForm" method="post" action="${ctx }${empty isPoolCust ? '/cust/custFollow/saveCustFollow' : '/cust/custFollow/savePoolCustFollow' }">
	<input type="hidden" id="custSign" name="custSign" value="" />
	<input type="hidden" id="custGiveUp" name="custGiveUp" value="" />
	<input type="hidden" id="isAddDate" name="isAddDate" value="" />
	<input type="hidden" id="signSetting" name="signSetting" value="${signSetting }" />
<%-- 	<input type="hidden" id="followId" name="custFollow.custFollowId" value="${followId }"> --%>
	<input type="hidden" id="main_tscidId" name="custFollow.custDetailMobile" value='${phone }'>
	<input type="hidden" id="concatName" name="custFollow.custDetailName" value='${name }'>
	<input type="hidden"  id="custId" name="custId" value="${custId }"><!-- 资源ID -->
	<input type="hidden"  name="custFollow.lastSaleProcessId" value="${lastCustFollow.optionlistId }"><!--赋值上次销售进程ID -->
	<input type="hidden" name="custGuide.custGuideId"  value="${tsmCustGuideEntity.custGuideId}">
	
	<c:if test="${dayPlan eq 1 }"> <input type="hidden"  id="dayflag" value="1"></c:if>
<div class="bomp_ical <c:if test="${dayPlan eq 1 || extension == 1}"> newDay_plan_right_follow </c:if>">
	<c:if test="${extension == 1}">
		<div class="bomp-extension-tip">
			<img class="img-extension" src="${ctx }/static/images/extension-success.png" />
			<div class="tip-content">保存成功！</div>
		</div>
	</c:if>
	
	<div class="bomp_icb fl_l bomp-extension">
			<div class="bomp-box">
				<p class='bomp-p'  id="bomp_error_custTypeId">
					<label class='lab_a fl_l'><i class="com-red">*</i>客户类型：</label>
					 <select class="sel_a fl_l" name="custGuide.custTypeId" id="custTypeId" checkProp="chk_2">

						<c:forEach items="${custTypeList}" var="custType" varStatus="vs">
							<option value="${custType.optionlistId}"
								<c:choose>
								    <c:when test='${custType.optionlistId eq lastCustFollow.custTypeId }'>
								         selected
								    </c:when>
								    <c:when test="${custType.isDefault ==1 && (empty lastCustFollow.custTypeId || '' eq  lastCustFollow.custTypeId )}">
								         selected
								    </c:when>
								</c:choose>>
								${custType.optionName}
							</option>
						</c:forEach>
					</select>
					<span class="error" id="error_custTypeId"></span>
				</p>
				<div class='bomp-p'  id="bomp_error_suitProcId">
					<input type="hidden" id="suitProcId" name ="suitProcId" value="${productIds }" checkProp="chk_"/><!-- 适用产品 -->
					<label class='lab_a fl_l'><i class="com-red">*</i>适用产品：</label>
					<div class="reso-sub-dep fl_l">
						<span style="border:0 none;width:100%;height:28px;line-height:28px;font-weight:normal;display:inline-block;margin-top:0;white-space: nowrap;overflow-x: hidden;text-overflow: ellipsis;" id="suitProNameId" title="${productNames }">${productNames }</span>
						<div class="manage-drop" id="manage_drop">
							<div class="sub-dep-ul">
								<ul id="tt1" class="easyui-tree"  data-options="animate:false,dnd:false">
						            <c:forEach items="${suitProcList}" var="suitProc" varStatus="vs">
										<li>
										   <span>
										        <input type="checkbox" id="chk_${suitProc.id }" show="${suitProc.name}"  value="${suitProc.id}" <c:if test="${ fn:contains(productIds,suitProc.id) }">checked</c:if> >
										          <label title="${suitProc.name}">${suitProc.name}</label></span></li>
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
					<input type="text" id="actionDate" name="custFollow.actionDate" value="<fmt:formatDate value="${actionDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"  <c:if test="${empty isPoolCust }">onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"</c:if> class="ipt"  checkProp="chk_" readOnly="readonly"/>
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
			<div class='bomp-p bomp-p-w actionTag' style="display: none;">
				<input type="hidden" value="${lastCustFollow.labelCode }" name="custFollow.labelCode" id="labelCodeId">
				<input type="hidden" value="${lastCustFollow.labelName }" name="custFollow.labelName" id="labelNameId">
				<label class='lab_a fl_l lab_b'>行动标签：</label>
				<div class="tip-box fl_l">
					<label class="1sty-hid fl_l"></label>
					<i class="tip-more icon_down icon_down_a fl_l" name="down"></i>
				</div>
			</div>
			<div class='bomp-p bomp-p-w'   id="bomp_error_feedbackComment">
				<label class='lab_a fl_l lab_b'><i class="com-red">*</i>联系小记：</label>
				<label class="hyx-sce-area">
					<textarea class='area_a fl_l' id="feedbackComment" name="custFollow.feedbackComment" maxlength="4000" checkProp="chk_1" placeholder="请输入与客户联系结果，最多可输入2000个汉字。"></textarea>
				</label>
				<span class="error" id="error_feedbackComment"></span>
			</div>
			<div class="bomp-box contact-required">
				<p class='bomp-p' id="bomp_error_followType">
					<label class='lab_a fl_l'><c:if test="${empty isPoolCust && nextFollowValidation eq 1 }"><i class="com-red">*</i></c:if>下次联系：</label>
					<select class="sel_a fl_l" name="custFollow.followType" id="followType" <c:if test="${empty isPoolCust && nextFollowValidation eq 1 }">checkProp="chk_2"</c:if>>
						<option value="" ${(empty isPoolCust || nextFollowValidation eq 0) ? '' : 'selected' }>请选择</option>
						<option value="1" ${(empty isPoolCust && nextFollowValidation eq 1) ? 'selected' : '' }>电话联系</option>
						<option value="2">会客联系</option>
						<option value="3">客户来电</option>
						<option value="4">短信联系</option>
						<option value="5">QQ联系</option>
						<option value="6">邮件联系</option>
						<option value="7">微信联系</option>
					</select>
					<span class="error" id="error_followType"></span>
				</p>
				<p class='bomp-p' id="bomp_error_followDate">
					<label class='lab_a fl_l'><c:if test="${empty isPoolCust && nextFollowValidation eq 1 }"><i class="com-red">*</i></c:if>下次联系时间：</label>
					<input type="text" id="followDate" name="custFollow.followDate" value="${(empty isPoolCust && nextFollowValidation eq 1) ? defDate : ''}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})" class="ipt"  <c:if test="${empty isPoolCust && nextFollowValidation eq 1 }">checkProp="chk_"</c:if> readOnly="readonly"/>
					<span class="error" id="error_followDate"></span>
				</p>
			</div>
			<div class="bomp-box">
				<p class='bomp-p bomp-saleChance'>
					<label class='lab_a fl_l'>关联销售机会：</label>
					<select id="saleChanceId" class="sel_a fl_l" name="custFollow.saleChanceId" ></select>
				</p>
			</div>
			<c:if test="${dayPlan eq 1}">
				<div class="bomb-btn bomb-btn-top bomb-btn-change project-comp-idialog-btnbox" style="padding:10px 0;">
					<label class="bomb-btn-cen">
						<a href="###" id="saveResBtn" class="com-btna bomp-btna sure-btn com-btna-sty fl_l"><label>保存</label></a>
						<a href="###" id="cacleResBtn" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
					</label>
				</div>
			</c:if>
		</div>
		<c:if test="${extension == 1}">
			<div class="bomb-btn bomb-btn-top bomb-btn-change project-comp-idialog-btnbox" style="padding:10px 0;">
				<div class="bomb-btn-cen" style="float:right;margin-right:10px;">
					<a href="###" id="saveResBtn" class="com-btna bomp-btna sure-btn com-btna-sty fl_l" style="margin-right:0;"><label>保存</label></a>
					<a href="###" id="cacleResBtn" class="com-btna bomp-btna cancel-btn fl_l"><label>关闭</label></a>
				</div>
			</div>
		</c:if>
	</div>
	</form>
	</body>
	<c:if test="${dayPlan eq 1 }">
	<script type="text/javascript">
		$(function(){
			$("#saveResBtn").on("click",function(e){
				e.stopPropagation();
				if(isOpera && checkIsNull()){
					isOpera = false;
					$("#myForm").ajaxSubmit({
						dataType:'html',
						type : 'post',
						error:function(){
							isOpera = true;
							alert(" 请求失败！");
						},
						success:function(data){
							isOpera = true;
							if(data == '0'){
								window.top.iDialogMsg("提示","保存成功！");
								window.parent.location.reload();
							}else if(data == '-1'){
								window.top.iDialogMsg("提示","保存失败！");
							}else{
								window.top.iDialogMsg("提示",data);
							}
						}
					});
				}
			})
			$("#cacleResBtn").on("click",function(e){
				e.stopPropagation();
				closeParentPubDivDialogIframe("dayPlan_add_follow_");
			});
		});
	</script>
	</c:if>
	
	<script type="text/javascript">
		var isOpera = true; // 不可重复点击
		$(function(){
			//行动标签
// 			var state = window.parent.$("#custState").val();
// 			if(state == 6){
// 				$(".contact-required").remove();
// 				$(".contact").show();
// 			}else{
// 				$(".contact").remove();
// 			}
			var labelList = '${labelList}';
			var lastLabelCodes = '${lastCustFollow.labelCode}';
			var jsonLabelList = eval('('+labelList+')');

			var tip_box_html = $(".actionTag .tip-box").html();

			actionTagInit(jsonLabelList);
			saleChanceInit($("#custId").val());
			
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
			
			function actionTagInit(labelList){
				var length = labelList.length;
				var tip_box = $(".actionTag .tip-box");
				tip_box.html(tip_box_html);
				var tip_box_hidden = tip_box.find(".sty-hid");
				var icon_down = $(".actionTag>.icon_down");
				icon_down.hide();
				for(var i = 0; i < length; i++){
					var appendCode = "";
					if(lastLabelCodes.indexOf(labelList[i].optionlistId) > -1){
						appendCode = "<a href='###' class='click-hover' id='"+labelList[i].optionlistId+"'>"+labelList[i].optionName+"</a>";
					}
					else{
						appendCode = "<a href='###' id='"+labelList[i].optionlistId+"'>"+labelList[i].optionName+"</a>";
					}
					tip_box.append(appendCode);
					var tip_box_a = tip_box.find("a:last");
					if(tip_box.height() > 25){
						icon_down.show();
						tip_box_a.appendTo(tip_box_hidden);
					}else{
						tip_box_a.insertBefore(tip_box_hidden);
					}
				}
				$(".hyx-sce-area .tip-box a").each(function(){
					var $this = $(this);
					$this.click(function(e){
						e.stopPropagation();
						if($this.hasClass("click-hover") == true){
							$this.removeClass("click-hover");
						}else{
							$this.addClass("click-hover");
						}
						labelStr();
					});
				});
			}


			/*card下拉*/
		    $('.icon_down_a').click(function(){
		    	var sty_hid = $(this).prev();
		    	var height = $(".bomp_ical").height();
		    	var height_hid = sty_hid.height();
		    	if($(this).attr('name') == 'up'){
		    		$(this).css({'background':'url(${ctx}/static/images/down-alert.png) no-repeat'});
		    		$(this).attr('name','down');
		    	}else{
		    		$(this).css({'background':'url(${ctx}/static/images/up-alert.png) no-repeat'});
		    		$(this).attr('name','up');
		    	}
		    	sty_hid.toggle();
		    	window.parent.$("#followFrame").height($(".bomp_ical").height() + 30);
		    	window.parent.$("#followFrame").parent().height($(".bomp_ical").height() + 30);
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
		    // 适用产品点击
		    $("#suitProNameId").click(function(event){
		    	event.stopPropagation();
		    	$("#manage_drop").toggle();
		    });
		    $(document).click(function(){
		    	if($("#manage_drop").css("display") == "block"){
		    		$("#manage_drop").hide();
		    	}
		    });
		    
		    //若使用产品为空，则默认显示请选择
		    if($('#suitProNameId').text() == ""){
				$('#suitProNameId').text("--请选择--").attr("title","--请选择--");
			}
		    
		 	// 确定
		    $('#setProId').click(function(){
		    	setSuitPro();
		    })
		    // 清空
		    $('#close02').click(function(){
		    	$('#suitProNameId').text("--请选择--").attr("title","--请选择--");
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
		//淘到客户
		function tao(){
			if(isOpera && checkIsNull()){
				var url = $("#myForm").attr("action");
				url+="?isTao=1";
				$("#myForm").attr("action",url);
				saveCustFollow("");
			}else{
				window.parent.$("#btn_tao").removeAttr("disabled");
			}
		}

		//保存按钮点击
		function save(){
			if(isOpera && checkIsNull()){
				saveCustFollow("follow");
			}else{
				window.parent.$("#btn_save").removeAttr("disabled");
			}
		}
		//放弃按钮点击
		function giveUpCust(){
			giveUp();
		}

		//签约按钮
		function sign(){
			if(isOpera && checkIsNull()){
				var html = '${nextFollowValidation}' == '1' ? "是否确认签约？<br /><input type='checkbox' id='saveNextFollow' />勾选保存下次联系时间" : "是否确认签约？";
				window.top.pubDivDialog("confirm_sign",html,function(){
					$("#custSign").val("1"); // 有值表示是签约 操作
					if(window.top.$("#saveNextFollow").length > 0){
						var saveNextFollow = window.top.$("#saveNextFollow").is(":checked") ? 2 : 1;
						$("#isAddDate").val(saveNextFollow);
					}
					saveCustFollow("sign");
				});
			}
		}

		//  保存
		function saveCustFollow(i){
			isOpera = false;
			$("#myForm").ajaxSubmit({
				dataType:'html',
				type : 'post',
				error:function(){
					isOpera = true;
					alert(" 请求失败！");
				},
				success:function(data){
					isOpera = true;
					if(data == '0'){
						if(i == "sign" && $("#signSetting").val() == "0"){ // 签约客户
							var options = window.top.centerTabs.tabs('getSelected').panel("options");
							window.freshCard = options._title;
							// 弹出新增合同tab
							window.top.addTab(ctx+"/contract/toAdd?custId="+$("#custId").val()+"&areFrom=1","新增合同");
						}else if(i == "follow"){
							window.top.iDialogMsg("提示","保存成功！");
// 							window.parent.parent.document.location.href=window.parent.parent.document.location.href;
							window.parent.parent.document.location.reload(true);
						}else{
							window.parent.parent.document.location.reload(true);
// 							window.parent.parent.document.location.href=window.parent.parent.document.location.href;
						}
					}else if(data == '-1'){
						window.top.iDialogMsg("提示","保存失败！");
					}else{
						window.top.iDialogMsg("提示",data);
					}
				}
			});
		}

		function giveUp(){
			var custId = $('#custId').val();
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
	    					window.top.pubDivDialog("follw_save_cust","客户已入公海池，是否需要继续当前操作？",function(){
	    		    			saveCustFollow("");
	    		    		},
	    		    		function(){
	    		    			isOpera = true;
	    		    		});
	    				}else{
	    					window.top.pubDivDialog("follw_save_cust","正在放弃客户，是否需要继续当前操作？",function(){
	    		    			saveCustFollow("");
	    					},
	    					function(){
	    						isOpera = true;
	    					});
	    				}
	    			}
	    		});
			}
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
	<c:if test="${extension eq 1 }">
	<script type="text/javascript">
		function saveCustFollow(i){
			isOpera = false;
			$("#myForm").ajaxSubmit({
				dataType:'html',
				type : 'post',
				error:function(){
					isOpera = true;
					alert(" 请求失败！");
				},
				success:function(data){
					isOpera = true;
					if(data == '0'){
						if(i == "sign" && $("#signSetting").val() == "0"){ // 签约客户
							var options = window.top.centerTabs.tabs('getSelected').panel("options");
							window.freshCard = options._title;
							// 弹出新增合同tab
							window.top.addTab(ctx+"/contract/toAdd?custId="+$("#custId").val()+"&areFrom=1","新增合同");
						}else if(i == "follow"){
							window.iDialogMsg("提示","保存成功！");
							$(".bomp-extension").hide();
							$(".bomp-extension-tip").show();
						}else{

						}
					}else if(data == '-1'){
						window.iDialogMsg("提示","保存失败！");
					}else{
						window.iDialogMsg("提示",data);
					}
				}
			});
		}
		$(function(){
			$("#saveResBtn").on("click",function(e){
				e.stopPropagation();
				if(isOpera && checkIsNull()){
					isOpera = false;
					$("#myForm").ajaxSubmit({
						dataType:'html',
						type : 'post',
						error:function(){
							isOpera = true;
							alert(" 请求失败！");
						},
						success:function(data){
							isOpera = true;
							if(data == '0'){
								window.iDialogMsg("提示","保存成功！");
								$("#saveResBtn").css("visibility","hidden");
								$(".bomp-extension").hide();
								$(".bomp-extension-tip").show();
							}else if(data == '-1'){
								window.iDialogMsg("提示","保存失败！");
							}else{
								window.iDialogMsg("提示",data);
							}
						}
					});
				}
			});
		});
	</script>
	</c:if>
</html>
