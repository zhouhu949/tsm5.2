<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE>
<html>
<head>
    <%@ include file="/common/include.jsp" %>
    <style type="text/css">
        .copy_telno {
            background-image: url("${ctx}/static/images/icon_copy.png");
            margin: 4px 2px 0 0;
            display: inline-block;
            width: 20px;
            height: 20px;
            position: absolute;
            top: 2px;
            right: 392px;
            cursor: pointer;
        }

        .delete_telno {
            display: inline-block;
            width: 16px;
            height: 16px;
            position: absolute;
            top: 7px;
            right: 372px;
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
    <link href="${ctx}/static/css/dialog.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/view/res/custPhoneSafe_p.js"></script>
    <script type="text/javascript">
        $(function () {
            $("input").each(function (item, obj) {
                if ($(obj).attr("disabled") != "disabled") {
                    $(obj).focus();
                }
            })

            $('input[checkProp^=chk_5]').blur(function () {
                var tag = $(this);
                var id = tag.attr('id');
                if ($(this).val() == '') {
//	  			$('#error_'+id).text('必填项');
//	  			tag.parent().attr('class',"bomp-p bomp-error");
                } else {
                    $('#error_' + id).text('');
                    tag.parent().attr('class', "bomp-p");
                }
            });
            var idReplace = '${idReplaceWord}';
            custPhoneSafe(idReplace);
            isCheckCustWord()
            custDPCheck();
            window.onload = function () {
                var height = $(".bomp_change").height() + 30;
                window.parent.$("#iframepage").css({"height": height + "px"});
            };

            $('.bomp-drop').each(function (i, item) {
                $(item).click(function () {
                    var height = $(".bomp_change").height() + 30;
                    var height_hid = $(".bomp-drop-hid").height();
                    if ($(this).hasClass('bomp-pull') == false) {
                        window.parent.$("#iframepage").css({"height": height + height_hid + "px"});
                        $(this).parent().find('.bomp-drop-hid').eq(i).slideDown();
                        $(this).find('label').removeClass('arrow').addClass('arrow_a');
                        $(this).addClass('bomp-pull');
                    } else {
                        window.parent.$("#iframepage").css({"height": height - height_hid + "px"});
                        $(this).parent().find('.bomp-drop-hid').eq(i).slideUp();
                        $(this).find('label').removeClass('arrow_a').addClass('arrow');
                        $(this).removeClass('bomp-pull');
                    }
                });
            });

            $('input[timeWay="timeWay"]').each(function (item, obj) {
                $(obj).dateRangePicker({
                    showShortcuts: false,
                    singleDate: true,
                    showShortcuts: false

                }).bind('datepicker-change', function (event, obj) {
                    $(obj).val(obj)
                    setErrMsg($(this), '', false)
                });
            })

            $('input[majorWay="majorWay"]').each(function (item, obj) {
                //重点关注
                $("#isMajor_").on("ifChecked", function () {
                    $("#isMajor").val(1);
                });
                $("#isMajor_").on("ifUnchecked", function () {
                    $("#isMajor").val(0);
                });
            })

            //加载省、市、区
            initArea();
            //选择省
            $("#s_province").change(function () {
                $("#s_city").html('<option value="" selected="selected">-请选择-</option>');
                $("#s_county").html('<option value="" selected="selected">-请选择-</option>');
                var provinceId = $(this).find("option:selected").val();
                $("#provinceId").val(provinceId);
                $("#cityId").val("");
                $("#countyId").val("");
                isCity(provinceId)
                if (provinceId != null && provinceId != '') {
                    $.ajax({
                        url: ctx + '/common/area/city',
                        type: 'post',
                        data: {pid: provinceId},
                        dataType: 'json',
                        success: function (data) {
                            $.each(data, function (index, obj) {
                                $("#s_city").append('<option value="' + obj.cid + '">' + obj.cname + '</option>');
                            });
                        }
                    });
                }
            });
            //选择市
            $("#s_city").change(function () {
                $("#s_county").html('<option value="" selected="selected">-请选择-</option>');
                var cityId = $(this).find("option:selected").val();
                $("#cityId").val(cityId);
                $("#countyId").val("");
                isCity(cityId)
                if (cityId != null && cityId != '') {
                    $.ajax({
                        url: ctx + '/common/area/county',
                        type: 'post',
                        data: {cid: cityId},
                        dataType: 'json',
                        success: function (data) {
                            $.each(data, function (index, obj) {
                                $("#s_county").append('<option value="' + obj.oid + '">' + obj.oname + '</option>');
                            });
                        }
                    });
                }
            });
            //选择区
            $("#s_county").change(function () {
                var countyId = $(this).find("option:selected").val();
                $("#countyId").val(countyId);
            });

            //取消
            $("#cacleResBtn").click(function () {
                var getTimestamp = new Date().getTime();
                $('li[name$=toSearchRes]').addClass('li_click').siblings('li').removeClass('li_click');
                window.parent.$('#iframepage').attr('src', ctx + '/res/incall/toSearchRes?custId=' + $('#resCustId').val() + '&v=' + getTimestamp);
            });
            //确定
            $("#saveResBtn").click(function () {
                if ($("form").length > 1) {
                    return;
                }
                var isArea = true;
                var isSubmited = false;
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
                    return false;
                }
                $("#resForm").ajaxSubmit({
                    dataType: 'json',
                    success: function (data) {
                        var getTimestamp = new Date().getTime();
                        $('li[name$=toSearchRes]').addClass('li_click').siblings('li').removeClass('li_click');
                        window.parent.$('#iframepage').attr('src', ctx + '/res/incall/toSearchRes?custId=' + $('#resCustId').val() + '&v=' + getTimestamp);

                    }
                });
            });
            
            selectBitch();
        });

        function resourceToDW() {
            var firstInput = "";
            var isShow = false;
            var mark = 0;
            $('span[name=a]').each(function (item, obj) {
                firstInput = $(obj).text();
                if (firstInput != '') {
                    if (mark == 0) {
                        firstInput = $(this).attr('id').split('_')[1];
                    }
                    if (mark >= 10) {
                        isShow = true;
                    }
                }
                mark = mark + 1;
            })
            $('#' + firstInput).focus();
            if (isShow) {
                if ($('.bomp-drop').hasClass('bomp-pull') == false) {
                    $('.bomp-drop').parent().find('.bomp-drop-hid').slideToggle();
                    $('.bomp-drop').find('label').removeClass('arrow').addClass('arrow_a');
                    $('.bomp-drop').addClass('bomp-pull');
                }
            } else {
                if ($('.bomp-drop').hasClass('bomp-pull') == true) {
                    $('.bomp-drop').parent().find('.bomp-drop-hid').slideToggle();
                    $('.bomp-drop').find('label').removeClass('arrow_a').addClass('arrow');
                    $('.bomp-drop').removeClass('bomp-pull');
                }
            }
        }

        function initArea() {
            var provinceId = $("#provinceId").val();
            var cityId = $("#cityId").val();
            var countyId = $("#countyId").val();
            //加载省
            $.ajax({
                url: ctx + '/common/area/province',
                type: 'post',
                dataType: 'json',
                success: function (data) {
                    $.each(data, function (index, obj) {
                        $("#s_province").append('<option value="' + obj.pid + '">' + obj.pname + '</option>');
                    });
                    if (provinceId != null && provinceId != '') {
                        $("#s_province").find("option[value=" + provinceId + "]").attr("selected", true);
                    }
                }
            });
            //加载市
            if (provinceId != null && provinceId != '') {
                $.ajax({
                    url: ctx + '/common/area/city',
                    type: 'post',
                    data: {pid: provinceId},
                    dataType: 'json',
                    success: function (data) {
                        $.each(data, function (index, obj) {
                            $("#s_city").append('<option value="' + obj.cid + '">' + obj.cname + '</option>');
                        });
                        if (cityId != null && cityId != '') {
                            $("#s_city").find("option[value=" + cityId + "]").attr("selected", true);
                        }
                    }
                });
            }
            //加载区
            if (cityId != null && cityId != '') {
                $.ajax({
                    url: ctx + '/common/area/county',
                    type: 'post',
                    data: {cid: cityId},
                    dataType: 'json',
                    success: function (data) {
                        $.each(data, function (index, obj) {
                            $("#s_county").append('<option value="' + obj.oid + '">' + obj.oname + '</option>');
                        });
                        if (countyId != null && countyId != '') {
                            $("#s_county").find("option[value=" + countyId + "]").attr("selected", true);
                        }
                    }
                });
            }
        }


        function isRequireArea() {
            var provinceId = $("#provinceId").val();
            var cityId = $("#cityId").val();
            var countyId = $("#countyId").val();
            var isSucc = true;
            if (provinceId == null || provinceId == '' || cityId == null || cityId == '') {
                $('#area').html('必填项');
                $('#area').focus();
                $('#area').parent().attr('class', "bomp-p bomp-error");
                isSucc = false;
            } else {
                $('#area').html('');
                $('#area').parent().attr('class', "bomp-p");
                isSucc = true;
            }
            return isSucc;
        }

        function isCity(val) {
            var isSucc = true;
            if (val == null || val == '') {
                $('#area').html('必填项');
                $('#area').focus();
                $('#area').parent().attr('class', "bomp-p bomp-error");
                isSucc = false;
            } else {
                $('#area').html('');
                $('#area').parent().attr('class', "bomp-p");
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
                $('#mobilephone').attr('checkProp', $('#mobilephone').attr('checkProp') + "4");
            } else {
                $('#mobilephone').attr('checkProp', $('#mobilephone').attr('checkProp') + "0");
            }
            if (cust_check_tel == '1') {
                $('#telphone').attr('checkProp', $('#telphone').attr('checkProp') + "4");
            } else {
                $('#telphone').attr('checkProp', $('#mobilephone').attr('checkProp') + "0");
            }
/*             if (cust_check_tel2 == '1') {
                $('#alternatePhone2').attr('checkProp', $('#alternatePhone2').attr('checkProp') + "4");
            } else {
                $('#alternatePhone2').attr('checkProp', $('#alternatePhone2').attr('checkProp') + "0");
            } */
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

            $('#company').attr('checkProp', $('#company').attr('checkProp') + "0");
            $('#unithome').attr('checkProp', $('#unithome').attr('checkProp') + "0");
        }

        //资源去重
        function custDPCheck() {
            var pValiDateType = '${pValiDateType}';
            var uValiDateType = '${uValiDateType}';
            var uhValiDateType = '${uhValiDateType}';
            if (pValiDateType != '1') {
                $('#telphone').attr('checkProp', $('#telphone').attr('checkProp') + '1');
                $('#mobilephone').attr('checkProp', $('#mobilephone').attr('checkProp') + '1');
              //  $('#alternatePhone2').attr('checkProp', $('#alternatePhone2').attr('checkProp') + '1');
            }
            if ($('#uValiDateType').val() != '0') {
                $('#company').attr('checkProp', $('#company').attr('checkProp') + '2');
            }
            if ($('#uhValiDateType').val() != '0') {
                $('#unithome').attr('checkProp', $('#unithome').attr('checkProp') + '3');
            }
        }
        //设置显示或隐藏button
        function hiddenOrShowButton(id, idReplaceWord) {
            var idval = $('#' + id).val();
            $('span[id=button_telphone_copy]').each(function () {
                if (idval == null || idval == '' || idReplaceWord == '1') {
                    $(this).css('display', 'none');
                } else {
                    $(this).css('display', 'block');
                }
            });
        }
    </script>
    <script type="text/javascript" src="${ctx}/static/js/view/res/resourceCheck.js${_v}"></script>
</head>
<body>
<form id="resForm" action="${ctx }/res/cust/editRes" method="post">
    <div class='bomp-cen bomp_change'>
        　　　<input type="hidden" name='pValiDateType' id='pValiDateType' value='${pValiDateType }'>
        　　　<input type="hidden" name='uValiDateType' id='uValiDateType' value='${uValiDateType }'>
        　　　<input type="hidden" name='uhValiDateType' id='uhValiDateType' value='${uhValiDateType }'>
       <input type="hidden" id="resCustId" name="resCustId" value="${custInfoBean.resCustId }"/>
       <input type="hidden" name='tanpin' id='tanpin' value='1'>	
       <input type="hidden" name='idReplaceWord' id='idReplaceWord' value='${idReplaceWord}'>
        <div class='bomp_tit bomp_tit_a skin-minimal'><label class='lab'>客户信息</label>
            <c:if test="${!empty fieldSets  && custInfoBean.status eq 3}">
                <c:forEach items="${fieldSets}" var="field" varStatus="vs">
                    <c:if test="${'isMajor' eq field.fieldCode && field.enable eq 1  }">
                        <div class="check fl_r">
                            <input type="hidden" id="isMajor" name="isMajor" value="${custInfoBean.isMajor }"/>
                            <input type='checkbox' majorWay="majorWay"
                                   id="isMajor_" ${custInfoBean.isMajor eq 1 ? 'checked' : '' } class='fl_l'/><label
                                class='fl_l font-s'>重点关注</label>
                        </div>
                    </c:if>
                </c:forEach>
            </c:if>
        </div>
        <span class="bomp-mcb-tip" id="error_dp"></span>

        <div class='bomp-p' id="bomp_error_resGroupId">
            <label class='lab_a fl_l'> <i class="font-color"  style="display:inline-block;width:9px;text-align:left;">*</i>资源分组：</label>
            <dl class="select resGroup" data-input="[name='resGroupId']" data-selected-node="${custInfoBean.resGroupId }">
				<dt>资源分组</dt>
				<dd>
					<ul class="resource-group-tree" data-url="${ctx}/res/group/get_res_group_json">
						<!-- 部门树 -->
					</ul>
				</dd>
		 		<input name="resGroupId" type="hidden" value=""  checkProp="chk_1_1" />
				<span class="error" name='a' id="error_resGroupId" style="padding-left:0;"></span>				
		   	</dl>		
        </div>
        <c:if test="${ !empty fieldSets}">
        <c:forEach items="${fieldSets}" var="field" varStatus="vs">
        <c:if test="${vs.index eq  11 }">
        <div class="bomp-drop-hid">
            </c:if>
            <c:choose>
                <c:when test="${'name' eq field.fieldCode && field.enable eq 1  }">
                    <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label><input type='text' id="${field.fieldCode }" name="${field.fieldCode }"
                                       value='${custInfoBean.name }'  ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                       checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }" class='ipt_a fl_l'/>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
                    </p>
                </c:when>
                <c:when test="${'birthday' eq field.fieldCode && field.enable eq 1}">
                    <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label><input type='text' onclick="WdatePicker({maxDate:'%y-%M-%d'})" id="${field.fieldCode }"
                                       name="${field.fieldCode }"
                                       value='<fmt:formatDate value="${custInfoBean.birthday }" pattern="yyyy-MM-dd"/>'
                                       checkProp="${field.isRequired eq 0?'':'chk_5_1' }"  ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                       class='ipt_a fl_l'/>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
                    </p>
                </c:when>
                <c:when test="${'sex' eq field.fieldCode && field.enable eq 1  }">
                    <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label>
                        <select class='sel_a fl_l' id="${field.fieldCode }" name="${field.fieldCode }"
                                checkProp="${field.isRequired eq 0?'':'chk_2_1' }"  ${(field.isRead eq 0  || isRead) ? '':'disabled'} >
                            <option value="">-请选择-</option>
                            <option value="1" ${custInfoBean.sex eq '1' ? 'selected' : '' }>男</option>
                            <option value="2" ${custInfoBean.sex eq '2' ? 'selected' : '' }>女</option>
                        </select>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
                    </p>
                </c:when>
                <c:when test="${'comArea' eq field.fieldCode && field.enable eq 1}">
                    <input type="hidden" id="provinceId" name="provinceId" value="${custInfoBean.provinceId }"/>
                    <input type="hidden" id="cityId" name="cityId" value="${custInfoBean.cityId }"/>
                    <input type="hidden" id="countyId" name="countyId" value="${custInfoBean.countyId }"/>
                    <input type="hidden" id="chk_comArea" value='${field.isRequired}'>

                    <p class='bomp-p'>
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label>
                        <select class='sel_a sel_mar fl_l' id="s_province"   ${(field.isRead eq 0  || isRead) ? '':'disabled'}  >
                            <option value="">-请选择-</option>
                        </select>
                        <select class='sel_a sel_mar fl_l' id="s_city"    ${(field.isRead eq 0  || isRead) ? '':'disabled'} >
                            <option value="">-请选择-</option>
                        </select>
                        <select class='sel_a sel_mar fl_l' id="s_county"    ${(field.isRead eq 0  || isRead) ? '':'disabled'}  >
                            <option value="">-请选择-</option>
                        </select>
                        <span class="error" name='a' id="${field.isRequired eq 0?'':'area' }"></span>
                    </p>

                </c:when>
                <c:when test="${'mobilephone' eq field.fieldCode && field.enable eq 1  }">
                    <p class='bomp-p p_relative' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label>
                        <input type='hidden' id="${field.fieldCode }" name="${field.fieldCode }"
                               value='${custInfoBean.mobilephone }'
                               checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"     ${(field.isRead eq 0  || isRead)  ? '':'disabled'}
                               class='ipt_a fl_l'/>
                       <span class="copy_telno"
                             title="复制" onclick="copyPhone('mobilephone_safe')"
                             style="${idReplaceWord eq '1'?'display: none':'display:block'}">
                       </span>
                        <span class="min-dele delete_telno" title="清除"
                              onclick="clearPhone('mobilephone','mobilephone_safe','error_dp')"></span>                       
                        <input type='text' id="${field.fieldCode }_safe" name="${field.fieldCode }_safe"
                               value='${custInfoBean.mobilephone }'
                               checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"     ${(field.isRead eq 0  || isRead)  ? '':'disabled'}
                               class='ipt_a fl_l'/>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
                    </p>
                </c:when>
                <c:when test="${'telphone' eq field.fieldCode && field.enable eq 1  }">
                    <p class='bomp-p p_relative' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label>
                        <input type='hidden' id="${field.fieldCode }" name="${field.fieldCode }"
                               value='${custInfoBean.telphone }'
                               checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"      ${(field.isRead eq 0  || isRead)  ? '':'disabled'}
                               class='ipt_a fl_l'/>
                        <span class="copy_telno"
                              title="复制"
                              onclick="copyPhone('mobilephone_safe')"
                              style="${idReplaceWord eq '1'?'display: none':'display:block'}">
                        </span>
                        <span class="min-dele delete_telno" title="清除"
                              onclick="clearPhone('telphone','telphone_safe','error_dp')"></span>
                        <input type='text' id="${field.fieldCode }_safe" name="${field.fieldCode }_safe"
                               value='${custInfoBean.telphone }'
                               checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"      ${(field.isRead eq 0  || isRead)  ? '':'disabled'}
                               class='ipt_a fl_l'/>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
                    </p>
                </c:when>

                <c:when test="${'alternatePhone2' eq field.fieldCode && field.enable eq 1  }">
                    <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label><input type='text' id="${field.fieldCode }" name="${field.fieldCode }"
                                       value='${custInfoBean.alternatePhone2 }'
                                       checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"     ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                       class='ipt_a fl_l'/>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
                    </p>
                </c:when>
                <c:when test="${'fax' eq field.fieldCode && field.enable eq 1  }">
                    <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label><input type='text' id="${field.fieldCode }" name="${field.fieldCode }"
                                       value='${custInfoBean.fax }'
                                       checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                       class='ipt_a fl_l'/>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
                    </p>
                </c:when>
                <c:when test="${'company' eq field.fieldCode && field.enable eq 1  }">
                    <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label><input type='text' id="${field.fieldCode }" name="${field.fieldCode }"
                                       value='${custInfoBean.company }'
                                       checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                       class='ipt_a fl_l'/>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
                    </p>
                </c:when>
                <c:when test="${'keyWord' eq  field.fieldCode  && field.enable eq 1}">
                    <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label><input type='text' id="${field.fieldCode }" name="${field.fieldCode }"
                                       value='${custInfoBean.keyWord }'
                                       checkProp="${field.isRequired eq 0?'':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                       class='ipt_a fl_l'/>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
                    </p>
                </c:when>
                <c:when test="${'email' eq field.fieldCode && field.enable eq 1  }">
                    <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label><input type='text' id="${field.fieldCode }" name="${field.fieldCode }"
                                       value='${custInfoBean.email }'
                                       checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                       class='ipt_a fl_l'/>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
                    </p>
                </c:when>
                <c:when test="${'qq' eq field.fieldCode && field.enable eq 1  }">
                    <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label><input type='text' id="${field.fieldCode }" name="${field.fieldCode }"
                                       value='${custInfoBean.qq }'
                                       checkProp="${field.isRequired eq 0?'':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                       class='ipt_a fl_l'/>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
                    </p>
                </c:when>
                <c:when test="${'wangwang' eq field.fieldCode && field.enable eq 1  }">
                    <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label><input type='text' id="${field.fieldCode }" name="${field.fieldCode }"
                                       value='${custInfoBean.wangwang }'
                                       checkProp="${field.isRequired eq 0?'':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                       class='ipt_a fl_l'/>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
                    </p>
                </c:when>
                <c:when test="${'unithome' eq  field.fieldCode  && field.enable eq 1}">
                    <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label><input type='text' value='${custInfoBean.unithome }' id="${field.fieldCode }"
                                       name="${field.fieldCode }"
                                       checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                       class='ipt_a fl_l'/>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
                    </p>
                </c:when>
                <c:when test="${'companyOrg' eq  field.fieldCode  && field.enable eq 1}">
                    <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label><input type='text' value='${custInfoBean.companyOrg }' id="${field.fieldCode }"
                                       name="${field.fieldCode }"
                                       checkProp="${field.isRequired eq 0?'':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                       class='ipt_a fl_l'/>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
                    </p>
                </c:when>
                <c:when test="${'duty' eq  field.fieldCode  && field.enable eq 1}">
                    <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label><input type='text' value='${custInfoBean.duty }' id="${field.fieldCode }"
                                       name="${field.fieldCode }"
                                       checkProp="${field.isRequired eq 0?'':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                       class='ipt_a fl_l'/>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
                    </p>
                </c:when>
                <c:when test="${'companyAddr' eq  field.fieldCode  && field.enable eq 1}">
                    <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label><input type='text' value='${custInfoBean.companyAddr }' id="${field.fieldCode }"
                                       name="${field.fieldCode }"
                                       checkProp="${field.isRequired eq 0?'':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                       class='ipt_a fl_l'/>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
                    </p>
                </c:when>
                <c:when test="${'remark' eq  field.fieldCode  && field.enable eq 1}">
                    <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                        <label class='lab_a fl_l'>
                            <c:choose>
                                <c:when test="${field.isRequired eq 1 }">
                                    <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                </c:when>
                            </c:choose>
                                ${field.fieldName}：
                        </label><input type='text' value='${custInfoBean.remark }' id="${field.fieldCode }"
                                       name="${field.fieldCode }"
                                       checkProp="${field.isRequired eq 0?'':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                       class='ipt_a fl_l'/>
                        <span class="error" name='a' id="error_${ field.fieldCode}"></span>
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
					   	<input type='text'   id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a w_b fl_l'  <c:if test="${field.isRead eq 1 }">readonly</c:if>/>
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
					   	<input type='text'  <c:if test="${field.isRead eq 0 }">onclick="WdatePicker()"</c:if>  id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_5_1' }" class='ipt_a w_b fl_l' <c:if test="${field.isRead eq 1 }">readonly</c:if> />
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
							<ul>
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
        </div>
        <div class="bomp-drop">
            <span class="sp fl_l"></span>
            <label class="arrow a"><em>◆</em><span>◆</span></label>
            <label class="arrow b"><em>◆</em><span>◆</span></label>
            <span class="sp fl_r"></span>
        </div>
        <div class='bomb-btn bomb-btn-top bomb-btn-change'>
            <label class='bomb-btn-cen'>
                <a href="###" id="saveResBtn"
                   class="com-btna bomp-btna sure-btn com-btna-sty fl_l"><label>保存</label></a>
                <a href="###" id="cacleResBtn" class="com-btna bomp-btna cancel-btn fl_l"><label>返回</label></a>
            </label>
        </div>
</form>
</body>
</html>