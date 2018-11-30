<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/include.jsp"%>
    <style type="text/css">
        .copy_telno {
            background-image: url("${ctx}/static/images/icon_copy.png");
            margin: 4px 2px 0 0;
            display: inline-block;
            width: 20px;
            height: 20px;
            position: absolute;
            top: 2px;
            left: 376px;
            cursor: pointer;
        }

        .delete_telno {
            display: inline-block;
            width: 16px;
            height: 16px;
            position: absolute;
            top: 7px;
            left: 402px;
            cursor: pointer;
        }

        .p_relative {
            position: relative;
        }
        
        .bomp-p .select {
	    	position: relative;
	    	float: left;
	    	width: 260px;
	    }
	    
	    .bomp-p .select dt {
	    	width: 236px;
	    	background: url(${ctx}/static/images/drop-down-arrow.png) 245px center no-repeat rgb(255, 255, 255);
	    	font-weight: normal;
	    	font-size: 12px;
	    }
	    
	    .bomp-p .select,.sel_a{
	    	font-size:12px;
	    }
	    
	    .bomp-p .select dd{
	    	box-sizing: border-box;
	    	width: 100%;
	    	z-index: 200;
	    }
	    .bomp-p .select dd ul {
	    	padding: 0;
	    	width: 100%;
	    }
    </style>
<script type="text/javascript">
	$(function() {
		 	var inputArrs = $("input[type='text']");
		 	for(var i=0;i<inputArrs.length;i++){
		 		if(inputArrs.eq(i).attr("disabled")!="disabled"){
		 			inputArrs.eq(i).focus();
		 			break;
		 		}
		 	}
		   
		   $('input[checkProp^=chk_5]').blur(function(){
		 		var tag = $(this);
		 		var id = tag.attr('id');
		 		if($(this).val()==''){
//		  			$('#error_'+id).text('必填项');
//		  			tag.parent().attr('class',"bomp-p bomp-error");
		 		}else{
		 			$('#error_'+id).text('');
		 			tag.parent().attr('class',"bomp-p");
		 		}
		 	});
		 	
		/*表单优化*/

		$('.bomp-drop').each(function(i, item) {
			$(item).click(function() {
				$(this).parent().find('.bomp-drop-hid').eq(i).slideToggle();
				if ($(this).hasClass('bomp-pull') == false) {
					$(this).find('label').removeClass('arrow').addClass('arrow_a');
					$(this).addClass('bomp-pull');
				} else {
					$(this).find('label').removeClass('arrow_a').addClass('arrow');
					$(this).removeClass('bomp-pull');
				}
			});
		});

		$('input[majorWay="majorWay"]').each(function(item, obj) {
			//重点关注
			$("#isMajor_").on("ifChecked", function() {
				$("#isMajor").val(1);
			});
			$("#isMajor_").on("ifUnchecked", function() {
				$("#isMajor").val(0);
			});
		})

		//加载省、市、区
		initArea();
		//选择省
		$("#s_province").change(function() {
			$("#s_city").html('<option value="" selected="selected">-请选择-</option>');
			$("#s_county").html('<option value="" selected="selected">-请选择-</option>');
			var provinceId = $(this).find("option:selected").val();
			$("#provinceId").val(provinceId);
			$("#cityId").val("");
			$("#countyId").val("");
			isCity(provinceId);
			if (provinceId != null && provinceId != '') {
				$.ajax({
					url : ctx + '/common/area/city',
					type : 'post',
					data : {
						pid : provinceId
					},
					dataType : 'json',
					success : function(data) {
						$.each(data, function(index, obj) {
							$("#s_city").append('<option value="'+obj.cid+'">' + obj.cname + '</option>');
						});
					}
				});
			}
		});
		//选择市
		$("#s_city").change(function() {
			$("#s_county").html('<option value="" selected="selected">-请选择-</option>');
			var cityId = $(this).find("option:selected").val();
			$("#cityId").val(cityId);
			$("#countyId").val("");
			isCity(cityId);
			if (cityId != null && cityId != '') {
				$.ajax({
					url : ctx + '/common/area/county',
					type : 'post',
					data : {
						cid : cityId
					},
					dataType : 'json',
					success : function(data) {
						$.each(data, function(index, obj) {
							$("#s_county").append('<option value="'+obj.oid+'">' + obj.oname + '</option>');
						});
					}
				});
			}
		});
		//选择区
		$("#s_county").change(function() {
			var countyId = $(this).find("option:selected").val();
			$("#countyId").val(countyId);
		});
		//确定
		var isSubmited = false;
		$("#addCustByScreen").click(function() {
			$.ajax({
				url : ctx + '/res/resmanage/getCustLimitNum',
				type : 'post',
				data : {
					code : 'DATA_10003'
				},
				success : function(data) {
					if (data == '1') {
						window.top.iDialogMsg("提示", "超出个人拥有意向客户上限！");
						return false;
					}
					var isArea = true;
					var comArea = $('#chk_comArea').val();
					var isSubmited = false;
					if (comArea != undefined && comArea == '1') {
						if (!isRequireArea()) {
							isArea = false;
						}
					}
					if (!isSubmited && (!checkForm() || !isArea)) {
						isSubmited = true;
						resourceToDW();
						detailToDW();
						return false;
					}
					var isSaveDetail = "";
					$('input[name^=custInfoDetail]').each(function(item, obj) {
						isSaveDetail = $(obj).val();
						if (isSaveDetail == '' || isSaveDetail == null) {
							isSaveDetail = "0";
						} else {
							isSaveDetail = "1";
							return false;
						}
					})
					$('#isSaveDetail').val(isSaveDetail);
					$('#isSave').val('1');
					$("#resForm").submit();
				}
			})
		});
		
		var idReplace = '${idReplaceWord}';
		isCheckCustWord();
		custDPCheck();
		custPhoneSafe(idReplace);
		
		selectBitch();
	});
	function resourceToDW() {
		var firstInput = "";
		var isShow = false;
		var mark = 0;
		$('span[name=a]').each(function(item, obj) {
			firstInput = $(obj).text();
			if (firstInput != '') {
				if (mark == 0) {
					firstInput = $(this).attr('id').split('_')[1];
				}
				if (mark >= 4) {
					isShow = true;
				}
			}
			mark = mark + 1;
		})
		$('#' + firstInput).focus();
		if (isShow) {
			if ($('.resource').hasClass('bomp-pull') == false) {
				$('.resource').parent().find('.resource-hid').slideToggle();
				$('.resource').find('label').removeClass('arrow').addClass('arrow_a');
				$('.resource').addClass('bomp-pull');
			}
		} else {
			if ($('.resource').hasClass('bomp-pull') == true) {
				$('.resource').parent().find('.resource-hid').slideToggle();
				$('.resource').find('label').removeClass('arrow_a').addClass('arrow');
				$('.resource').removeClass('bomp-pull');
			}
		}
	}

	function detailToDW() {
		var firstInput = "";
		var isShow = false;
		var mark = 0;
		$('span[name=b]').each(function(item, obj) {
			firstInput = $(obj).text();
			if (firstInput != '') {
				if (mark == 0) {
					firstInput = $(this).attr('id').split('_')[1];
				}
				if (mark >= 4) {
					isShow = true;
				}
			}
			mark = mark + 1;
		})
		$('#' + firstInput).focus();
		if (isShow) {
			if ($('.detail').hasClass('bomp-pull') == false) {
				$('.detail').parent().find('.resource-detail').slideToggle();
				$('.detail').find('label').removeClass('arrow').addClass('arrow_a');
				$('.detail').addClass('bomp-pull');
			}
		} else {
			if ($('.detail').hasClass('bomp-pull') == true) {
				$('.detail').parent().find('.resource-detail').slideToggle();
				$('.detail').find('label').removeClass('arrow_a').addClass('arrow');
				$('.detail').removeClass('bomp-pull');
			}
		}
	}
	
	
	function addCustQuHuiYiXiang(num,type,val){
		var $object = $('#error_dp').children(":first");
		if(type !='6' && type !='7' && type !='8'){
		  return;	
		}
		if(num>1){
			 window.top.iDialogMsg("提示", "重复数据多于2（含）条，请到待分配资源取回意向");
			 return;
		}else{
			
			
			$.ajax({
				url : ctx + '/res/resmanage/getCustLimitNum',
				type : 'post',
				data : {
					code : 'DATA_10003'
				},
				success : function(data) {
					if (data == '1') {
						window.top.iDialogMsg("提示", "超出个人拥有意向客户上限！");
						return false;
					}else{
				        $.ajax({
				            url: ctx + '/res/cust//qiHuiYiXiang',
				            type: 'post',
				            data: {resId:"",val:val,type:type},
				            dataType: 'json',
				            success: function (data) {
				            	var code = data.code;
				            	var custId = data.custId;
				            	var result = data.result;
				            	retUrl(custId,result);
				            }
				        });
					}

				}
			});	

		}
	}
	function retUrl(resId,result){
		var pageTypeId = $('#pageTypeId', window.parent.document).val();
		var phone = $('#phone').val()
		if(phone!=''){//判断是否来自屏幕取值
			window.top.iDialogMsg("提示","保存成功！");
			setTimeout('external.OnCloseDlg(0);',1000);
		    return;
		}
		if('1'== pageTypeId){
			if(result == "1"){
				window.top.iDialogMsg("提示","保存成功！");
		    	    window.parent.window.taoMainPlatform(resId);
		    	    window.parent.window.taoCustGuide(resId);
		    	    $('#custId',parent.window.document).val(resId);
		    	    $('#tempResId',parent.window.document).val("1");
		    	    setTimeout('closeParentPubDivDialogIframe("add_cust");',1000);
			}else{
				window.top.iDialogMsg("提示","保存失败！");
				setTimeout('window.parent.document.forms[0].submit();',1000);
			}
		}else{
			if(result == "1"){
				window.top.iDialogMsg("提示","保存成功！");
				setTimeout('window.parent.document.forms[0].submit();',1000);
			}else{
				window.top.iDialogMsg("提示","保存失败！");
				setTimeout('window.parent.document.forms[0].submit();',1000);
			}					
		}
	}

	
	function initArea() {
		var provinceId = $("#provinceId").val();
		var cityId = $("#cityId").val();
		var countyId = $("#countyId").val();
		//加载省
		$.ajax({
			url : ctx + '/common/area/province',
			type : 'post',
			dataType : 'json',
			success : function(data) {
				$.each(data, function(index, obj) {
					$("#s_province").append('<option value="'+obj.pid+'">' + obj.pname + '</option>');
				});
			}
		});
	}

	function isRequireArea() {
		var provinceId = $("#provinceId").val();
		var cityId = $("#cityId").val();
		var countyId = $("#countyId").val();
		var isSucc = true;
		var comArea = $('#chk_comArea').val();
		if (comArea == undefined || comArea != '1') {
			return false;
		}
		if (provinceId == null || provinceId == '' || cityId == null || cityId == '') {
			$('#comArea').html('必填项');
			$('#comArea').focus();
			$('#comArea').parent().attr('class', "bomp-p bomp-error");
			isSucc = false;
		} else {
			$('#comArea').html('');
			$('#comArea').parent().attr('class', "bomp-p");
			isSucc = true;
		}
		return isSucc;
	}

	function isCity(val) {
		var comArea = $('#chk_comArea').val();
		var isSucc = true;
		if (comArea == undefined || comArea != '1') {
			return false;
		}
		if (val == null || val == '') {
			$('#comArea').html('必填项');
			$('#comArea').focus();
			$('#comArea').parent().attr('class', "bomp-p bomp-error");
			isSucc = false;
		} else {
			$('#comArea').html('');
			$('#comArea').parent().attr('class', "bomp-p");
			isSucc = true;
		}
		return isSucc;
	}

	function isCheckCustWord() {
		var cust_check_mobile = "${cust_check_mobile}";
		var cust_check_tel = "${cust_check_tel}";
		var cust_check_tel2 = "${cust_check_tel2}";
		var cust_check_fax = "${cust_check_fax}";
		var cust_check_mail = "${cust_check_mail}";

		if (cust_check_mobile == '1') {
			$('#telphone').attr('checkProp', $('#telphone').attr('checkProp') + "4");
		} else {
			$('#telphone').attr('checkProp', $('#telphone').attr('checkProp') + "0");
		}
		if (cust_check_tel == '1') {
			$('#telphonebak').attr('checkProp', $('#telphonebak').attr('checkProp') + "4");
		} else {
			$('#telphonebak').attr('checkProp', $('#telphonebak').attr('checkProp') + "0");
		}
		if (cust_check_mail == '1') {
			$('#email').attr('checkProp', $('#email').attr('checkProp') + "5");
		} else {
			$('#email').attr('checkProp', $('#email').attr('checkProp') + "0");
		}
		if (cust_check_fax == '1') {
			$('#fax').attr('checkProp', $('#fax').attr('checkProp') + "4");
		} else {
			$('#fax').attr('checkProp', $('#fax').attr('checkProp') + "0");
		}

		$('#name').attr('checkProp', $('#name').attr('checkProp') + "0");
		$('#unithome').attr('checkProp', $('#unithome').attr('checkProp') + "0");
	}

	//资源去重
	function custDPCheck() {
		var pValiDateType = '${pValiDateType}';
		var uValiDateType = '${uValiDateType}';
		var uhValiDateType = '${uhValiDateType}';
		if (pValiDateType != '1') {
			$('#telphone').attr('checkProp', $('#telphone').attr('checkProp') + '1');
			$('#telphonebak').attr('checkProp', $('#telphonebak').attr('checkProp') + '1');
		}
		if ($('#uValiDateType').val() != '0') {
			$('#name').attr('checkProp', $('#name').attr('checkProp') + '2');
		}
		if ($('#uhValiDateType').val() != '0') {
			$('#unithome').attr('checkProp', $('#unithome').attr('checkProp') + '3');
		}
	}
</script>

</head>
<body>
	<form id="resForm" action="${ctx }/res/cust/toFollowByScreen" method="post">
		<input type="hidden" name='pValiDateType' id='pValiDateType' value='${pValiDateType }'> <input type="hidden" name='uValiDateType' id='uValiDateType' value='${uValiDateType }'> 
		<input type="hidden" name='uhValiDateType' id='uhValiDateType' value='${uhValiDateType }'> <input type="hidden" name="isSaveDetail" id='isSaveDetail' value="${isSaveDetail}"> 
		<input type="hidden" name="isSave" id='isSave' value="${isSave }">
		<input type="hidden" name="phone" id='phone' value="${phone }">
		<input type="hidden" name='tanpin' id='tanpin' value='1'>
		<input type="hidden" name='idReplaceWord' id='idReplaceWord' value='${idReplaceWord}'>
		<div class="year-sale-play hyx-hsrs-top">
			<ul class="ann-sale-plan clearfix">
				<a href="${ctx}/res/cust/toAddResByScreen?isSaveDetail=${isSaveDetail}&isSave=${isSave}&phone=${phone}"><li class="select li_first" name="a">客户信息</li></a>
				<a href="${ctx}/res/cust/toFollowByScreen?isSaveDetail=${isSaveDetail}&isSave=${isSave}&phone=${phone}"><li class="li_last" name="b">跟进信息</li></a>
			</ul>
		</div>
		<div class="hyx-bomp" style="height:435px;overflow-x:hidden;overflow-y:auto;">
			<div class='bomp-cent bomp_change'>
				<div class='bomp_tit bomp_tit_a skin-minimal'>
					<label class='lab'>企业信息</label>
					<c:if test="${!empty fieldSets }">
						<c:forEach items="${fieldSets}" var="field" varStatus="vs">
							<c:if test="${'isMajor' eq field.fieldCode && field.enable eq 1  }">
								<!-- 							             <div class="check fl_r"><input type="hidden" id="isMajor"  majorWay="majorWay"  name="custInfo.isMajor" value="" /><input type='checkbox' id="isMajor_" class='fl_l' /><label class='fl_l font-s'>重点关注</label></div> -->
							</c:if>
						</c:forEach>
					</c:if>
				</div>
				<span class="bomp-mcb-tip" id="error_dp" style="color:red;"></span>
				<div class='bomp-p' id="bomp_error_resGroupId">
					<label class='lab_a fl_l'><i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>资源分组：</label>
					<dl class="select resGroup" data-input="[name='custInfo.resGroupId']" >
						<dt>资源分组</dt>
						<dd>
							<ul class="resource-group-tree" data-url="${ctx}/res/group/get_res_group_json">
								<!-- 部门树 -->
							</ul>
						</dd>
				 		<input class="input-resGroup" name="custInfo.resGroupId" type="hidden" value=""  checkProp="chk_1_1" />
						<span class="error" name='a' id="error_resGroupId" style="padding-left:0;"></span>				
				   	</dl>	
				</div>
				<c:if test="${ !empty fieldSets}">
					<c:forEach items="${fieldSets}" var="field" varStatus="vs">
						<c:if test="${vs.index eq  4 }">
							<div class="bomp-drop-hid resource-hid">
						</c:if>
						<c:choose>
							<c:when test="${'name' eq field.fieldCode && field.enable eq 1  }">
								<p class='bomp-p' id="bomp_error_${field.fieldCode }">
									<label class='lab_a fl_l'> <c:choose>
											<c:when test="${field.isRequired eq 1 }">
												<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
											</c:when>
										</c:choose> ${field.fieldName}：
									</label><input type='text' id="${field.fieldCode }" name="custInfo.${field.fieldCode }" value='' checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }" class='ipt_a fl_l' /> <span class="error"
										name='a' id="error_${ field.fieldCode}"></span>
								</p>
							</c:when>
							<c:when test="${'companyMoney' eq field.fieldCode && field.enable eq 1}">
								<p class='bomp-p' id="bomp_error_${field.fieldCode }">
									<label class='lab_a fl_l'> <c:choose>
											<c:when test="${field.isRequired eq 1 }">
												<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
											</c:when>
										</c:choose> ${field.fieldName}：
									</label><input type='text' id="${field.fieldCode }" name="custInfo.${field.fieldCode }" value='' checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l' /> <span class="error" name='a'
										id="error_${ field.fieldCode}"></span>
								</p>
							</c:when>
							<c:when test="${'companyTrade' eq  field.fieldCode && field.enable eq 1 }">
								<p class='bomp-p' id="bomp_error_${field.fieldCode }">
									<label class='lab_a fl_l'> <c:choose>
											<c:when test="${field.isRequired eq 1 }">
												<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
											</c:when>
										</c:choose> ${field.fieldName}：
									</label> <select class='sel_a fl_l' id="${field.fieldCode }" name="custInfo.${field.fieldCode }" checkProp="${field.isRequired eq 0?'':'chk_2_1' }">
                                                     <option value="">--请选择--</option>
                                                     <c:forEach var="option" items="${optionList }">
                                                     <c:if test="${option.isDefault =='1'}">
                                                     <option value="${option.optionlistId }"  selected="selected">${option.optionName }</option>
                                                     </c:if>
                                                     
                                                     <c:if test="${option.isDefault !='1'}">
                                                     <option value="${option.optionlistId }">${option.optionName }</option>
                                                     </c:if>                                                    
							                         </c:forEach>

<%--									<option value="">-请选择-</option>
										<option value="1">信息传输、软件和信息技术服务业</option>
										<option value="2">采矿业</option>
										<option value="3">制造业</option>
										<option value="4">电力、热力、燃气及水生产和供应业</option>
										<option value="5">建筑业</option>
										<option value="6">批发和零售业</option>
										<option value="7">交通运输、仓储和邮政业</option>
										<option value="8">住宿和餐饮业</option>
										<option value="9">农、林、牧、渔业</option>
										<option value="10">金融业</option>
										<option value="11">房地产业</option>
										<option value="12">租赁和商务服务业</option>
										<option value="13">科学研究和技术服务业</option>
										<option value="14">水利、环境和公共设施管理业</option>
										<option value="15">居民服务、修理和其他服务业</option>
										<option value="16">教育</option>
										<option value="17">卫生和社会工作</option>
										<option value="18">文化、体育和娱乐业</option>
										<option value="19">公共管理、社会保障和社会组织</option>
										<option value="20">国际组织</option>
										<option value="21">其他</option>
										 --%>
									</select> <span class="error" name='a' id="error_${ field.fieldCode}"></span>
								</p>
							</c:when>
							<c:when test="${'companyUser' eq  field.fieldCode && field.enable eq 1 }">
								<p class='bomp-p' id="bomp_error_${field.fieldCode }">
									<label class='lab_a fl_l'> <c:choose>
											<c:when test="${field.isRequired eq 1 }">
												<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
											</c:when>
										</c:choose> ${field.fieldName}：
									</label><input type='text' id="${field.fieldCode }" name="custInfo.${field.fieldCode }" value='' checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l' /> <span class="error" name='a'
										id="error_${ field.fieldCode}"></span>
								</p>
							</c:when>
							<c:when test="${'companyFax' eq field.fieldCode && field.enable eq 1}">
								<p class='bomp-p' id="bomp_error_${field.fieldCode }">
									<label class='lab_a fl_l'> <c:choose>
											<c:when test="${field.isRequired eq 1 }">
												<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
											</c:when>
										</c:choose> ${field.fieldName}：
									</label><input type='text' id="${field.fieldCode }" name="custInfo.${field.fieldCode }" value='' checkProp="${field.isRequired eq 0?'chk_1_04':'chk_1_14' }" class='ipt_a fl_l' /> <span class="error"
										name='a' id="error_${ field.fieldCode}"></span>
								</p>
							</c:when>
							<c:when test="${'keyWord' eq  field.fieldCode  && field.enable eq 1}">
								<p class='bomp-p' id="bomp_error_${field.fieldCode }">
									<label class='lab_a fl_l'> <c:choose>
											<c:when test="${field.isRequired eq 1 }">
												<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
											</c:when>
										</c:choose> ${field.fieldName}：
									</label><input type='text' id="${field.fieldCode }" name="custInfo.${field.fieldCode }" value='' checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l' /> <span class="error" name='a'
										id="error_${ field.fieldCode}"></span>
								</p>
							</c:when>
							<c:when test="${'unithome' eq  field.fieldCode  && field.enable eq 1}">
								<p class='bomp-p' id="bomp_error_${field.fieldCode }">
									<label class='lab_a fl_l'> <c:choose>
											<c:when test="${field.isRequired eq 1 }">
												<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
											</c:when>
										</c:choose> ${field.fieldName}：
									</label><input type='text' value='' id="${field.fieldCode }" name="custInfo.${field.fieldCode }" checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }" class='ipt_a fl_l' /> <span class="error"
										name='a' id="error_${ field.fieldCode}"></span>
								</p>
							</c:when>
							<c:when test="${'comArea' eq field.fieldCode && field.enable eq 1}">
								<input type="hidden" id="provinceId" name="custInfo.provinceId" value="" />
								<input type="hidden" id="cityId" name="custInfo.cityId" value="" />
								<input type="hidden" id="countyId" name="custInfo.countyId" value="" />
								<input type="hidden" id="chk_comArea" value='${field.isRequired}'>
								<p class='bomp-p'>
									<label class='lab_a fl_l'> <c:choose>
											<c:when test="${field.isRequired eq 1 }">
												<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
											</c:when>
										</c:choose> ${field.fieldName}：
									</label> <select class='sel_a sel_mar fl_l' id="s_province">
										<option>-请选择-</option>
									</select> <select class='sel_a sel_mar fl_l' id="s_city">
										<option>-请选择-</option>
									</select> <select class='sel_a sel_mar fl_l' id="s_county">
										<option>-请选择-</option>
									</select> <span class="error" name='a' id="${field.isRequired eq 0?'':'comArea' }"></span>
								</p>

							</c:when>
							<c:when test="${'address' eq  field.fieldCode  && field.enable eq 1}">
								<p class='bomp-p'>
									<label class='lab_a fl_l'> <c:choose>
											<c:when test="${field.isRequired eq 1 }">
												<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
											</c:when>
										</c:choose> ${field.fieldName}：
									</label> <input type='text' id="${field.fieldCode }" name="custInfo.${field.fieldCode }" value='' checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a w_b fl_l' /> <span class="error"
										name='a' id="error_${ field.fieldCode}"></span>
								</p>
							</c:when>
							<c:when test="${'scope' eq  field.fieldCode && field.enable eq 1 }">
								<p class='bomp-p'>
									<label class='lab_a fl_l'> <c:choose>
											<c:when test="${field.isRequired eq 1 }">
												<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
											</c:when>
										</c:choose> ${field.fieldName}：
									</label><input type='text' id="${field.fieldCode }" name="custInfo.${field.fieldCode }" value='' checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a w_b fl_l' /> <span class="error"
										name='a' id="error_${ field.fieldCode}"></span>
								</p>
							</c:when>
							<c:when test="${'remark' eq  field.fieldCode  && field.enable eq 1}">
								<p class='bomp-p'>
									<label class='lab_a fl_l'> <c:choose>
											<c:when test="${field.isRequired eq 1 }">
												<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
											</c:when>
										</c:choose> ${field.fieldName}：
									</label><input type='text' id="${field.fieldCode }" name="custInfo.${field.fieldCode }" value='' checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a w_b fl_l' /> <span class="error"
										name='a' id="error_${ field.fieldCode}"></span>
								</p>
							</c:when>
						</c:choose>
						<c:if test="${field.fieldCode.toString().contains('defined') && field.enable eq 1}">

			    	   <c:choose>
			            <c:when test="${ field.dataType eq 1 }">
								<p class='bomp-p'>
								   	<label class='lab_a fl_l'>
								   		<c:choose>
								   	      	<c:when test="${field.isRequired eq 1 }">
			                                	<i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
								   	      	</c:when>
								   		</c:choose>
								    	${field.fieldName}：
								   	</label>
								   	<input type='text'   id="${field.fieldCode }"  name="custInfo.${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a w_b fl_l' />
									<span class="error"  name='a' id="error_${ field.fieldCode}"></span>
								</p> 		                                    
			            </c:when>
			            <c:when test="${ field.dataType eq 2 }">
								<p class='bomp-p'>
								   	<label class='lab_a fl_l'>
								   	   	<c:choose>
								   	    	<c:when test="${field.isRequired eq 1 }">
			                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
								   	    	</c:when>
								   	   	</c:choose>
								    	${field.fieldName}：
								   	</label>
								   	<input type='text'  onclick="WdatePicker()"  id="${field.fieldCode }"  name="custInfo.${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_5_1' }" class='ipt_a w_b fl_l' />
									<span class="error"  name='a' id="error_${ field.fieldCode}"></span>
								</p>		                                    
			            </c:when>
			            <c:when test="${ field.dataType eq 3 }">
								<p class='bomp-p' >
									<label class='lab_a fl_l'><c:if test="${field.isRequired eq 1 }"><i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i></c:if>${field.fieldName}：</label>
									<select name="custInfo.${field.fieldCode }" class='sel_a fl_l' <c:if test="${field.isRequired eq 1 }">checkProp="chk_2_1"</c:if>>
									    <option value="">--请选择--</option>
										<c:forEach var="os" items="${field.optionList }">
											<option value="${os.optionlistId}"  <c:if test="${field.showValue eq os.optionlistId}">selected="selected"</c:if>>${os.optionName }</option>
										</c:forEach>
									</select>	
									<span class="error" name='a' id="error_${field.fieldCode }"></span>				
								</p>
			            </c:when>
			            <c:when test="${ field.dataType eq 4 }">
			            	<div class='bomp-p' >
								<label class='lab_a fl_l'><c:if test="${field.isRequired eq 1 }"><i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i></c:if>${field.fieldName}：</label>
			                	<dl class="select pos2" data-input="[name='custInfo.${ field.fieldCode}']" data-multi="true">
			                	 	<c:set var="optionNames"  value="" />
			                		<c:forEach items="${field.optionList }" var="os">
										<c:if test="${field.showValue.contains(os.optionlistId)}">
											<c:set var="optionNames"  value="${optionNames},${os.optionName } " />
										</c:if>
									</c:forEach>
									<dt>
										<c:choose>
								   	    	<c:when test="${empty optionNames }">
			                                    --请选择--											   	      
								   	    	</c:when>
								   	    	<c:otherwise>
								   	    		${optionNames.substring(1) }
								   	    	</c:otherwise>
								   	   	</c:choose>
									</dt>
									<dd>
										<ul>
											<c:forEach items="${field.optionList }" var="os">
												<li <c:if test="${field.showValue.contains(os.optionlistId)}">class="selected"</c:if>><a href="javascript:void(0);"  data-value="${os.optionlistId}" title="${os.optionName }">${os.optionName }</a></li>
											</c:forEach>
										</ul>
									</dd>
								</dl>
			                    <input type="hidden" name="custInfo.${ field.fieldCode}" id="s_${ field.fieldCode}" value="${field.showValue }" checkProp="${field.isRequired eq 0?'':'chk_1_1' }" />
								<span class="error" name='a' id="error_${field.fieldCode }"></span>
							</div>
			            </c:when>
			              </c:choose>
			            </c:if>
					</c:forEach>
					
				</c:if>
			</div>
			<div class="bomp-drop resource">
				<span class="sp fl_l"></span> <label class="arrow a"><em>◆</em><span>◆</span></label> <label class="arrow b"><em>◆</em><span>◆</span></label> <span class="sp fl_r"></span>
			</div>
			<p class='bomp_tit bomp_tit_at'>
				<label class="lab">联系人信息</label>
			</p>

			<c:if test="${!empty concatFieldSets }">
				<c:forEach items="${ concatFieldSets}" var="concat" varStatus="vs">
					<c:if test="${vs.index eq  4 }">
						<div class="bomp-drop-hid resource-detail">
					</c:if>
					<c:choose>
						<c:when test="${'name' eq concat.fieldCode && concat.enable eq 1  }">
							<p class='bomp-p' id="bomp_error_${concat.fieldCode }">
								<label class='lab_a fl_l'> <c:choose>
										<c:when test="${concat.isRequired eq 1 }">
											<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
										</c:when>
									</c:choose> ${concat.fieldName}：
								</label><input type='text' id="${concat.fieldCode }" name="custInfoDetail.${concat.fieldCode }" value='' checkProp="${concat.isRequired eq 0?'chk_1_0':'chk_1_1' }" class='ipt_a fl_l' /> <span
									class="error" name='b' id="error_${ concat.fieldCode}"></span>
							</p>
						</c:when>
						<c:when test="${'telphone' eq concat.fieldCode && concat.enable eq 1  }">
							<p class='bomp-p p_relative'  id="bomp_error_${concat.fieldCode }">
								<label class='lab_a fl_l'> <c:choose>
										<c:when test="${concat.isRequired eq 1 }">
											<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
										</c:when>
									</c:choose> ${concat.fieldName}：
								</label>
								<input type='hidden' id="${concat.fieldCode }" name="custInfoDetail.${concat.fieldCode }" value='${phone }' checkProp="${concat.isRequired eq 0?'chk_1_0':'chk_1_1' }" class='ipt_a fl_l' /> 
								<span class="min-dele delete_telno" title="清除"  onclick="clearPhone('telphone','telphone_safe','error_dp')"></span>
								<input type='text' id="${concat.fieldCode }_safe" name="custInfoDetail.${concat.fieldCode }_safe" value='${phone }'  class='ipt_a fl_l' /> 
									<span class="error" name='b' id="error_${ concat.fieldCode}"></span>
							</p>
						</c:when>
						<c:when test="${'telphonebak' eq concat.fieldCode && concat.enable eq 1  }">
							<p class='bomp-p p_relative' id="bomp_error_${concat.fieldCode }">
								<label class='lab_a fl_l'> <c:choose>
										<c:when test="${concat.isRequired eq 1 }">
											<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
										</c:when>
									</c:choose> ${concat.fieldName}：
								</label>
								<input type='hidden' id="${concat.fieldCode }" name="custInfoDetail.${concat.fieldCode }" checkProp="${concat.isRequired eq 0?'chk_1_0':'chk_1_1' }" class='ipt_a fl_l' /> 
								<span class="min-dele delete_telno" title="清除"  onclick="clearPhone('telphonebak','telphonebak_safe','error_dp')"></span>
								<input type='text' id="${concat.fieldCode }_safe" name="custInfoDetail.${concat.fieldCode }_safe" checkProp="${concat.isRequired eq 0?'chk_1_0':'chk_1_1' }" class='ipt_a fl_l' /> 
									<span class="error" name='b' id="error_${ concat.fieldCode}"></span>
							</p>
						</c:when>
						<c:when test="${'sex' eq concat.fieldCode && concat.enable eq 1  }">
							<p class='bomp-p' id="bomp_error_${concat.fieldCode }">
								<label class='lab_a fl_l'> <c:choose>
										<c:when test="${concat.isRequired eq 1 }">
											<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
										</c:when>
									</c:choose> ${concat.fieldName}：
								</label> <select class='sel_a fl_l' id="${concat.fieldCode }" name="custInfoDetail.${concat.fieldCode }" checkProp="${concat.isRequired eq 0?'':'chk_2_1' }">
									<option value="">-请选择-</option>
									<option value="1">男</option>
									<option value="2">女</option>
								</select> <span class="error" name='b' id="error_${ concat.fieldCode}"></span>
							</p>
						</c:when>
						<c:when test="${'birthday' eq concat.fieldCode && concat.enable eq 1  }">
							<p class='bomp-p' id="bomp_error_${concat.fieldCode }">
								<label class='lab_a fl_l'> <c:choose>
										<c:when test="${concat.isRequired eq 1 }">
											<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
										</c:when>
									</c:choose> ${concat.fieldName}：
								</label><input type='text' onclick="WdatePicker({maxDate:'%y-%M-%d'})" id="birthday" name="custInfoDetail.${concat.fieldCode }" value='' checkProp="${concat.isRequired eq 0?'':'chk_5_1' }"
									class='ipt_a bomp-data fl_l' /> <span class="error" name='b' id="error_${ concat.fieldCode}"></span>
							</p>
						</c:when>
						<c:when test="${'email' eq concat.fieldCode && concat.enable eq 1  }">
							<p class='bomp-p' id="bomp_error_${concat.fieldCode }">
								<label class='lab_a fl_l'> <c:choose>
										<c:when test="${concat.isRequired eq 1 }">
											<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
										</c:when>
									</c:choose> ${concat.fieldName}：
								</label><input type='text' id="${concat.fieldCode }" name="custInfoDetail.${concat.fieldCode }" value='' checkProp="${concat.isRequired eq 0?'chk_1_0':'chk_1_1' }" class='ipt_a fl_l' /> <span
									class="error" name='b' id="error_${ concat.fieldCode}"></span>
							</p>
						</c:when>
						<c:when test="${'fax' eq concat.fieldCode && concat.enable eq 1  }">
							<p class='bomp-p' id="bomp_error_${concat.fieldCode }">
								<label class='lab_a fl_l'> <c:choose>
										<c:when test="${concat.isRequired eq 1 }">
											<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
										</c:when>
									</c:choose> ${concat.fieldName}：
								</label><input type='text' id="${concat.fieldCode }" name="custInfoDetail.${concat.fieldCode }" value='' checkProp="${concat.isRequired eq 0?'chk_1_0':'chk_1_1' }" class='ipt_a fl_l' /> <span
									class="error" name='b' id="error_${ concat.fieldCode}"></span>
							</p>
						</c:when>
						<c:when test="${'qq' eq concat.fieldCode && concat.enable eq 1  }">
							<p class='bomp-p' id="bomp_error_${concat.fieldCode }">
								<label class='lab_a fl_l'> <c:choose>
										<c:when test="${concat.isRequired eq 1 }">
											<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
										</c:when>
									</c:choose> ${concat.fieldName}：
								</label><input type='text' id="${concat.fieldCode }" name="custInfoDetail.${concat.fieldCode }" value='' checkProp="${concat.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l' /> <span class="error"
									name='b' id="error_${ concat.fieldCode}"></span>
							</p>
						</c:when>
						<c:when test="${'wangwang' eq concat.fieldCode && concat.enable eq 1  }">
							<p class='bomp-p' id="bomp_error_${concat.fieldCode }">
								<label class='lab_a fl_l'> <c:choose>
										<c:when test="${concat.isRequired eq 1 }">
											<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
										</c:when>
									</c:choose> ${concat.fieldName}：
								</label><input type='text' id="${concat.fieldCode }" name="custInfoDetail.${concat.fieldCode }" value='' checkProp="${concat.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l' /> <span class="error"
									name='b' id="error_${ concat.fieldCode}"></span>
							</p>
						</c:when>
						<c:when test="${'work' eq concat.fieldCode && concat.enable eq 1  }">
							<p class='bomp-p' id="bomp_error_${concat.fieldCode }">
								<label class='lab_a fl_l'> <c:choose>
										<c:when test="${concat.isRequired eq 1 }">
											<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
										</c:when>
									</c:choose> ${concat.fieldName}：
								</label><input type='text' id="${concat.fieldCode }" name="custInfoDetail.${concat.fieldCode }" value='' checkProp="${concat.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l' /> <span class="error"
									name='b' id="error_${ concat.fieldCode}"></span>
							</p>
						</c:when>
						<c:when test="${'groupname' eq concat.fieldCode && concat.enable eq 1  }">
							<p class='bomp-p' id="bomp_error_${concat.fieldCode }">
								<label class='lab_a fl_l'> <c:choose>
										<c:when test="${concat.isRequired eq 1 }">
											<i class="font-color" style="display: inline-block; width: 9px; text-align: left;">*</i>
										</c:when>
									</c:choose> ${concat.fieldName}：
								</label><input type='text' id="${concat.fieldCode }" name="custInfoDetail.${concat.fieldCode }" value='' checkProp="${concat.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l' /> <span class="error"
									name='b' id="error_${ concat.fieldCode}"></span>
							</p>
						</c:when>
					</c:choose>
					<c:if test="${field.fieldCode.toString().contains('defined') && field.enable eq 1}">

			    	   <c:choose>
			            <c:when test="${ field.dataType eq 1 }">
								<p class='bomp-p'>
								   	<label class='lab_a fl_l'>
								   		<c:choose>
								   	      	<c:when test="${field.isRequired eq 1 }">
			                                	<i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
								   	      	</c:when>
								   		</c:choose>
								    	${field.fieldName}：
								   	</label>
								   	<input type='text'   id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a w_b fl_l' />
									<span class="error"  name='a' id="error_${ field.fieldCode}"></span>
								</p> 		                                    
			            </c:when>
			            <c:when test="${ field.dataType eq 2 }">
								<p class='bomp-p'>
								   	<label class='lab_a fl_l'>
								   	   	<c:choose>
								   	    	<c:when test="${field.isRequired eq 1 }">
			                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>											   	      
								   	    	</c:when>
								   	   	</c:choose>
								    	${field.fieldName}：
								   	</label>
								   	<input type='text'  onclick="WdatePicker()"  id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_5_1' }" class='ipt_a w_b fl_l' />
									<span class="error"  name='a' id="error_${ field.fieldCode}"></span>
								</p>		                                    
			            </c:when>
			            <c:when test="${ field.dataType eq 3 }">
								<p class='bomp-p' >
									<label class='lab_a fl_l'><c:if test="${field.isRequired eq 1 }"><i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i></c:if>${field.fieldName}：</label>
									<select name="${field.fieldCode }" class='sel_a fl_l' <c:if test="${field.isRequired eq 1 }">checkProp="chk_2_1"</c:if>>
									    <option value="">--请选择--</option>
										<c:forEach var="os" items="${field.optionList }">
											<option value="${os.optionlistId}"  <c:if test="${field.showValue eq os.optionlistId}">selected="selected"</c:if>>${os.optionName }</option>
										</c:forEach>
									</select>	
									<span class="error" name='a' id="error_${field.fieldCode }"></span>				
								</p>
			            </c:when>
			            <c:when test="${ field.dataType eq 4 }">
			            	<div class='bomp-p' >
								<label class='lab_a fl_l'><c:if test="${field.isRequired eq 1 }"><i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i></c:if>${field.fieldName}：</label>
			                	<dl class="select pos2" data-input="[name='${ field.fieldCode}']" data-multi="true">
			                	 	<c:set var="optionNames"  value="" />
			                		<c:forEach items="${field.optionList }" var="os">
										<c:if test="${field.showValue.contains(os.optionlistId)}">
											<c:set var="optionNames"  value="${optionNames},${os.optionName } " />
										</c:if>
									</c:forEach>
									<dt>
										<c:choose>
								   	    	<c:when test="${empty optionNames }">
			                                    --请选择--											   	      
								   	    	</c:when>
								   	    	<c:otherwise>
								   	    		${optionNames.substring(1) }
								   	    	</c:otherwise>
								   	   	</c:choose>
									</dt>
									<dd>
										<ul data-ul="${field.optionList }">
											<c:forEach items="${field.optionList }" var="os">
												<li <c:if test="${field.showValue.contains(os.optionlistId)}">class="selected"</c:if>><a href="javascript:void(0);"  data-value="${os.optionlistId}" title="${os.optionName }">${os.optionName }</a></li>
											</c:forEach>
										</ul>
									</dd>
								</dl>
			                    <input type="hidden" name="${ field.fieldCode}" id="s_${ field.fieldCode}" value="${field.showValue }" checkProp="${field.isRequired eq 0?'':'chk_1_1' }" />
								<span class="error" name='a' id="error_${field.fieldCode }"></span>
							</div>
			            </c:when>
			              </c:choose>
		            </c:if>
				</c:forEach>
			</c:if>
			<%-- 				<p class="'bomp-p' id="bomp_error_${concat.fieldCode }"> --%>
			<%-- 					<label class='lab_a fl_l'>工作单位：</label><input type='text' value=''  checkProp="${concat.isRequired eq 0?'':'chk_1' }" class='ipt_a fl_l' /> --%>
			<!-- 				</p> -->
			<%-- 				<p class="'bomp-p' id="bomp_error_${concat.fieldCode }"> --%>
			<%-- 					<label class='lab_a fl_l'>单位网址：</label><input type='text' value=''  checkProp="${concat.isRequired eq 0?'':'chk_1' }" class='ipt_a fl_l' /> --%>
			<!-- 				</p> -->
			<%-- 				<p class="'bomp-p' id="bomp_error_${concat.fieldCode }"> --%>
			<%-- 					<label class='lab_a fl_l'>联系地址：</label><input type='text' value='' checkProp="${concat.isRequired eq 0?'':'chk_1' }"  class='ipt_a fl_l' /> --%>
			<!-- 				</p> -->

			<%-- 				<p class="'bomp-p' id="bomp_error_${concat.fieldCode }"> --%>
			<%-- 					<label class='lab_a fl_l'>职务：</label><input type='text' value=''  checkProp="${concat.isRequired eq 0?'':'chk_1' }" class='ipt_a fl_l' /> --%>
			<!-- 				</p> -->
			<%-- 				<p class="'bomp-p' id="bomp_error_${concat.fieldCode }"> --%>
			<%-- 					<label class='lab_a fl_l'>关键字：</label><input type='text'  name="custInfoDetail.wangwang" value=''  checkProp="${concat.isRequired eq 0?'':'chk_1' }" class='ipt_a fl_l' /> --%>
			<!-- 				</p> -->
			<!-- 				<p class='bomp-p'> -->
			<%-- 					<label class='lab_a fl_l'>备注：</label><textarea name="custInfoDetail.context"  checkProp="${concat.isRequired eq 0?'':'chk_1' }" class='area_a w_b fl_l'></textarea> --%>
			<!-- 				</p> -->
		</div>
		<div class="bomp-drop detail">
			<span class="sp fl_l"></span> <label class="arrow a"><em>◆</em><span>◆</span></label> <label class="arrow b"><em>◆</em><span>◆</span></label> <span class="sp fl_r"></span>
		</div>
		<div class='bomb-btn bomb-btn-top bomb-btn-change'>
			<label class='bomb-btn-cen'> <a href="###" id="addCustByScreen" class="com-btna bomp-btna sure-btn com-btna-sty fl_l"><label>下一步</label></a>
			</label>
		</div>
		</div>
		</div>
	</form>
	<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/view/res/custPhoneSafe_p.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/view/res/resourceCheck.js${_v}"></script>
</body>
</html>