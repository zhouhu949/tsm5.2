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
    <link href="${ctx}/static/css/dialog.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/view/res/custPhoneSafe.js"></script>
    <script type="text/javascript">
        $(function () {
            $("input").each(function (item, obj) {
                if ($(obj).attr("disabled") != "disabled") {
                    $(obj).focus();
                }
            })

            $('input[checkDetailProp^=chk_5]').blur(function () {
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
            isCheckCustWord();
            custDPCheck();
            window.onload = function () {
                var height = $(".bomp_change").height() + 30;
                window.parent.$("#iframepage").css({"height": height + "px"});
            };
            $('.bomp-drop').each(function (i, item) {
                $(item).click(function () {
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

            $('#saveBtn').click(function () {
                var isSubmited = false;
                if (!isSubmited && !checkForm()) {
                    isSubmited = true;
                    return false;
                }
                $('[checkDetailProp]').each(function (item, obj) {
                    isSaveDetail = $(obj).val();
                    if (isSaveDetail == '' || isSaveDetail == null) {
                        isSaveDetail = "0";
                    } else {
                        isSaveDetail = "1";
                        return false;
                    }
                })
                if (isSaveDetail != '1') {
                    window.top.iDialogMsg("提示", "请输入联系内容！");
                    return false;
                }
                var formData = new Object();
                $('#myForm').find('input,select').each(function (index, obj) {
                    formData[$(obj).attr("name")] = $(obj).val();
                });
                $.ajax({
                    url: ctx + '/res/cust/saveDetail',
                    type: 'post',
                    data: formData,
                    dataType: 'json',
                    success: function (data) {
                        if (data == 1) {
                            window.top.iDialogMsg("提示", "修改联系人成功！");
                            var getTimestamp = new Date().getTime();
                            $('li[name$=toSearchRes]').addClass('li_click').siblings('li').removeClass('li_click');
                            window.parent.$('#iframepage').attr('src', ctx + '/res/incall/toSearchRes?custId=' + $('#rciId').val() + '&v=' + getTimestamp);

                        } else {
                            window.top.iDialogMsg("提示", "修改联系人失败！");
                            var getTimestamp = new Date().getTime();
                            $('li[name$=toSearchRes]').addClass('li_click').siblings('li').removeClass('li_click');
                            window.parent.$('#iframepage').attr('src', ctx + '/res/incall/toSearchRes?custId=' + $('#rciId').val() + '&v=' + getTimestamp);

                        }
                    },
                    error: function (data) {
                        alert(JSON.stringify(data))
                    }
                });

            });

            $('#cancelBtn').click(function () {
                var getTimestamp = new Date().getTime();
                $('li[name$=toSearchRes]').addClass('li_click').siblings('li').removeClass('li_click');
                window.parent.$('#iframepage').attr('src', ctx + '/res/incall/toSearchRes?custId=' + $('#rciId').val() + '&v=' + getTimestamp);

            })

            $('input[name="mainLink"]').each(function (item, obj) {
                //重点关注
                $(this).on("ifChecked", function () {
                    $("#isDefault").val(1);
                });
                $(this).on("ifUnchecked", function () {
                    $("#isDefault").val(0);
                });
            });
            
            selectBitch();
        });
        function isCheckCustWord() {
            var cust_check_mobile = "${cust_check_mobile}";
            var cust_check_tel = "${cust_check_tel}";
            var cust_check_tel2 = "${cust_check_tel2}";
            var cust_check_fax = "${cust_check_fax}";
            var cust_check_mail = "${cust_check_mail}";

            if (cust_check_mobile == '1') {
                $('#telphone').attr('checkDetailProp', $('#telphone').attr('checkDetailProp') + "4");
            } else {
                $('#telphone').attr('checkDetailProp', $('#telphone').attr('checkDetailProp') + "0");
            }
            if (cust_check_tel == '1') {
                $('#telphonebak').attr('checkDetailProp', $('#telphonebak').attr('checkDetailProp') + "4");
            } else {
                $('#telphonebak').attr('checkDetailProp', $('#telphonebak').attr('checkDetailProp') + "0");
            }
            if (cust_check_mail == '1') {
                $('#email').attr('checkDetailProp', $('#email').attr('checkDetailProp') + "5");
            } else {
                $('#email').attr('checkDetailProp', $('#email').attr('checkDetailProp') + "0");
            }
            if (cust_check_fax == '1') {
                $('#fax').attr('checkDetailProp', $('#fax').attr('checkDetailProp') + "4");
            } else {
                $('#fax').attr('checkDetailProp', $('#fax').attr('checkDetailProp') + "0");
            }
        }

        //资源去重
        function custDPCheck() {
            var pValiDateType = '${pValiDateType}';
            var uValiDateType = '${uValiDateType}';
            var uhValiDateType = '${uhValiDateType}';
            if (pValiDateType != '1') {
                $('#telphone').attr('checkDetailProp', $('#telphone').attr('checkDetailProp') + '1');
                $('#telphonebak').attr('checkDetailProp', $('#telphonebak').attr('checkDetailProp') + '1');
            }
        }

        //设置显示或隐藏button
        function hiddenOrShowButton(id,idReplaceWord){
            var idval= $('#'+id).val();
            $('span[id=button_telphonebak_copy]').each(function(){
                if(idval==null || idval=='' || idReplaceWord=='1'){
                    $(this).css('display','none');
                }else{
                    $(this).css('display','block');
                }
            });
        }
    </script>
    <script type="text/javascript" src="${ctx}/static/js/view/res/resourceDetailCheck.js${_v}"></script>
</head>
<body>
<form action="${ctx}/res/cust/saveDetail" method="post" id="myForm" data-field="${concatFieldSets }">
    <div class='bomp-cen bomp_change bomp-mcl'>
        <div class='bomp_tit bomp_tit_a skin-minimal'><label class='lab'>编辑联系人</label>
        <input type="hidden" name="tscidId" value="${detail.tscidId }" id="tscidId"/>
        <input type="hidden" id="isDefault" name="isDefault" value="${detail.isDefault }"/>
        <input type="hidden" name="rciId" value="${rciId }" id="rciId"/>
        <input type="hidden" name='pValiDateType' id='pValiDateType' value='${pValiDateType }'>
        <input type="hidden" name='idReplaceWord' id='idReplaceWord' value='${idReplaceWord}'>
            　　　<input type="hidden" name='uValiDateType' id='uValiDateType' value='${uValiDateType }'>
            　　　<input type="hidden" name='uhValiDateType' id='uhValiDateType' value='${uhValiDateType }'>
        <input type="hidden" name='tanpin' id='tanpin' value='1'>	
            <div class="check fl_r"><input type='checkbox' ${detail.isDefault eq 1 ? 'checked disabled':''}
                                           name="mainLink" class='fl_l' id="mainLink"/><label
                    class='fl_l font-s'>主要联系人</label></div>
        </div>
        <span class="bomp-mcb-tip" id="error_dp"></span>
        <c:if test="${!empty concatFieldSets }">
            <c:forEach items="${ concatFieldSets}" var="field" varStatus="vs">
                <c:set var="code" value="${field.fieldCode}"></c:set>
                <c:choose>
                    <c:when test="${'name' eq field.fieldCode && field.enable eq 1  }">
                        <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                            <label class='lab_a fl_l'>
                                <c:choose>
                                    <c:when test="${field.isRequired eq 1 }">
                                        <i class="font-color"
                                           style="display:inline-block;width:9px;text-align:left;">*</i>
                                    </c:when>
                                </c:choose>
                                    ${field.fieldName}：
                            </label><input type='text' id="${field.fieldCode }" name="${field.fieldCode }"
                                           value='${detail.name }' ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                           checkDetailProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l'/>
                            <span class="error" id="error_${ field.fieldCode}"></span>
                        </p>
                    </c:when>
                    <c:when test="${'telphone' eq field.fieldCode && field.enable eq 1  }">
                        <p class='bomp-p p_relative' id="bomp_error_${field.fieldCode }">
                            <label class='lab_a fl_l'>
                                <c:choose>
                                    <c:when test="${field.isRequired eq 1 }">
                                        <i class="font-color"
                                           style="display:inline-block;width:9px;text-align:left;">*</i>
                                    </c:when>
                                </c:choose>
                                    ${field.fieldName}：
                            </label>
                            <input type='hidden' id="${field.fieldCode }" name="${field.fieldCode }"
                                   value='${detail.telphone }'
                                   checkDetailProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"      ${(field.isRead eq 0  || isRead)  ? '':'disabled'}
                                   class='ipt_a fl_l'/>
                            <span class="copy_telno" title="复制"
                                  onclick="copyPhone('telphone_safe')"
                                  style="${idReplaceWord eq '1'?'display: none':'display:block'}">
                            </span>
                            <span class="min-dele delete_telno" title="清除" id="button_telphone_clear"
                                  onclick="clearPhone('telphone','telphone_safe','error_dp')">
                            </span>                            
                            <input type='text' id="${field.fieldCode }_safe" name="${field.fieldCode }_safe"
                                   value='${detail.telphone }'
                                   checkDetailProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"      ${(field.isRead eq 0  || isRead)  ? '':'disabled'}
                                   class='ipt_a fl_l'/>
                            <span class="error" id="error_${ field.fieldCode}"></span>
                        </p>
                    </c:when>
                    <c:when test="${'telphonebak' eq field.fieldCode && field.enable  eq 1  }">
                        <p class='bomp-p p_relative' id="bomp_error_${field.fieldCode }">
                            <label class='lab_a fl_l'>
                                <c:choose>
                                    <c:when test="${field.isRequired eq 1 }">
                                        <i class="font-color"
                                           style="display:inline-block;width:9px;text-align:left;">*</i>
                                    </c:when>
                                </c:choose>
                                    ${field.fieldName}：
                            </label>
                            <input type='hidden' id="${field.fieldCode }" name="${field.fieldCode }"
                                   value='${detail.telphonebak }'
                                   checkDetailProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"     ${(field.isRead eq 0  || isRead)  ? '':'disabled'}
                                   class='ipt_a fl_l'/>
                            <span class="copy_telno" title="复制" id="button_telphonebak_copy"
                                  onclick="copyPhone('telphonebak_safe')"
                                  style="${(idReplaceWord eq '1' or empty detail.telphonebak  )?'display: none':'display:block'}">
                            </span>
                            <span class="min-dele delete_telno" title="清除" id="button_telphonebak_clear"
                                  onclick="clearPhone('telphonebak','telphonebak_safe','error_dp')">
                            </span>
                            <input type='text' id="${field.fieldCode }_safe" name="${field.fieldCode }_safe"
                                   value='${detail.telphonebak }'
                                   checkDetailProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"     ${(field.isRead eq 0  || isRead)  ? '':'disabled'}
                                   class='ipt_a fl_l'/>
                            <span class="error" id="error_${ field.fieldCode}"></span>
                        </p>
                    </c:when>
                    <c:when test="${'sex' eq field.fieldCode && field.enable eq 1  }">
                        <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                            <label class='lab_a fl_l'>
                                <c:choose>
                                    <c:when test="${field.isRequired eq 1 }">
                                        <i class="font-color"
                                           style="display:inline-block;width:9px;text-align:left;">*</i>
                                    </c:when>
                                </c:choose>
                                    ${field.fieldName}：
                            </label>
                            <select class='sel_a fl_l' id="${field.fieldCode }" name="${field.fieldCode }"
                                    value='${detail.sex }'
                                    checkDetailProp="${field.isRequired eq 0?'':'chk_2_1' }"   ${(field.isRead eq 0  || isRead) ? '':'disabled'} >
                                <option value="">-请选择-</option>
                                <option value="1" ${ detail.sex eq 1 ?'selected':''}>男</option>
                                <option value="2" ${ detail.sex eq 2 ?'selected':''}>女</option>
                            </select>
                            <span class="error" id="error_${ field.fieldCode}"></span>
                        </p>
                    </c:when>
                    <c:when test="${'birthday' eq field.fieldCode && field.enable  eq 1  }">
                        <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                            <label class='lab_a fl_l'>
                                <c:choose>
                                    <c:when test="${field.isRequired eq 1 }">
                                        <i class="font-color"
                                           style="display:inline-block;width:9px;text-align:left;">*</i>
                                    </c:when>
                                </c:choose>
                                    ${field.fieldName}：
                            </label><input type='text' onclick="WdatePicker({maxDate:'%y-%M-%d'})"
                                           id="${field.fieldCode }" name="${field.fieldCode }"
                                           value='<fmt:formatDate value="${detail.birthday}" pattern="yyyy-MM-dd"/>'
                                           checkDetailProp="${field.isRequired eq 0?'':'chk_5_1' }"    ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                           class='ipt_a fl_l'/>
                            <span class="error" id="error_${ field.fieldCode}"></span>
                        </p>
                    </c:when>
                    <c:when test="${'email' eq field.fieldCode && field.enable  eq 1  }">
                        <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                            <label class='lab_a fl_l'>
                                <c:choose>
                                    <c:when test="${field.isRequired eq 1 }">
                                        <i class="font-color"
                                           style="display:inline-block;width:9px;text-align:left;">*</i>
                                    </c:when>
                                </c:choose>
                                    ${field.fieldName}：
                            </label><input type='text' id="${field.fieldCode }" name="${field.fieldCode }"
                                           value='${detail.email }'
                                           checkDetailProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"    ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                           class='ipt_a fl_l'/>
                            <span class="error" id="error_${ field.fieldCode}"></span>
                        </p>
                    </c:when>
                    <c:when test="${'fax' eq field.fieldCode && field.enable  eq 1  }">
                        <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                            <label class='lab_a fl_l'>
                                <c:choose>
                                    <c:when test="${field.isRequired eq 1 }">
                                        <i class="font-color"
                                           style="display:inline-block;width:9px;text-align:left;">*</i>
                                    </c:when>
                                </c:choose>
                                    ${field.fieldName}：
                            </label><input type='text' id="${field.fieldCode }" name="${field.fieldCode }"
                                           value='${detail.fax }'
                                           checkDetailProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"   ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                           class='ipt_a fl_l'/>
                            <span class="error" id="error_${ field.fieldCode}"></span>
                        </p>
                    </c:when>
                    <c:when test="${'qq' eq field.fieldCode && field.enable  eq 1  }">
                        <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                            <label class='lab_a fl_l'>
                                <c:choose>
                                    <c:when test="${field.isRequired eq 1 }">
                                        <i class="font-color"
                                           style="display:inline-block;width:9px;text-align:left;">*</i>
                                    </c:when>
                                </c:choose>
                                    ${field.fieldName}：
                            </label><input type='text' id="${field.fieldCode }" name="${field.fieldCode }"
                                           value='${detail.qq }'
                                           checkDetailProp="${field.isRequired eq 0?'':'chk_1_1' }"   ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                           class='ipt_a fl_l'/>
                            <span class="error" id="error_${ field.fieldCode}"></span>
                        </p>
                    </c:when>

                    <c:when test="${'wangwang' eq field.fieldCode && field.enable  eq 1  }">
                        <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                            <label class='lab_a fl_l'>
                                <c:choose>
                                    <c:when test="${field.isRequired eq 1 }">
                                        <i class="font-color"
                                           style="display:inline-block;width:9px;text-align:left;">*</i>
                                    </c:when>
                                </c:choose>
                                    ${field.fieldName}：
                            </label><input type='text' id="${field.fieldCode }" name="${field.fieldCode }"
                                           value='${detail.wangwang }'
                                           checkDetailProp="${field.isRequired eq 0?'':'chk_1_1' }"   ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                           class='ipt_a fl_l'/>
                            <span class="error" id="error_${ field.fieldCode}"></span>
                        </p>
                    </c:when>
                    <c:when test="${'work' eq field.fieldCode && field.enable  eq 1  }">
                        <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                            <label class='lab_a fl_l'>
                                <c:choose>
                                    <c:when test="${field.isRequired eq 1 }">
                                        <i class="font-color"
                                           style="display:inline-block;width:9px;text-align:left;">*</i>
                                    </c:when>
                                </c:choose>
                                    ${field.fieldName}：
                            </label><input type='text' id="${field.fieldCode }" name="${field.fieldCode }"
                                           value='${detail.work }'
                                           checkDetailProp="${field.isRequired eq 0?'':'chk_1_1' }"   ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                           class='ipt_a fl_l'/>
                            <span class="error" id="error_${ field.fieldCode}"></span>
                        </p>
                    </c:when>
                    <c:when test="${'groupname' eq field.fieldCode && field.enable  eq 1  }">
                        <p class='bomp-p' id="bomp_error_${field.fieldCode }">
                            <label class='lab_a fl_l'>
                                <c:choose>
                                    <c:when test="${field.isRequired eq 1 }">
                                        <i class="font-color"
                                           style="display:inline-block;width:9px;text-align:left;">*</i>
                                    </c:when>
                                </c:choose>
                                    ${field.fieldName}：
                            </label><input type='text' id="${field.fieldCode }" name="${field.fieldCode }"
                                           value='${detail.groupname }'
                                           checkDetailProp="${field.isRequired eq 0?'':'chk_1_1' }"   ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                                           class='ipt_a fl_l'/>
                            <span class="error" id="error_${ field.fieldCode}"></span>
                        </p>
                    </c:when>
                </c:choose>
                
                <c:if test="${field.fieldCode.toString().contains('conDefined') && field.enable eq 1}">

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
        <p class='bomp_tit_e fl_l'><label class="bomp-red">温馨提示：</label>设为主要联系人后，客户当前联系人会被替换掉。</p>

        <div class='bomb-btn'>
            <label class='bomb-btn-cen'>
                <a href="###" id="saveBtn" class="com-btna bomp-btna com-btna-sty fl_l"><label>保存</label></a>
                <a href="###" id="cancelBtn" class="com-btna bomp-btna cancel fl_l"><label>返回</label></a>
            </label>
        </div>
    </div>
</form>
<script>
	$(function(){
		console.log($("form").data("field"));
	});
</script>
</body>
</html>