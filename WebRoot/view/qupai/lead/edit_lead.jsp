<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<html>
<head>
    <title>编辑放款</title>
    <%@ page import="com.qftx.base.shiro.ShiroUtil" %>
   
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/style.css${_v}">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/default.css${_v}"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/button.css${_v}"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/skin/default/color.css${_v}">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/js/form/skins/all.css${_v}">
    <script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js${_v}" dialog-theme="default"></script><!--可移动弹框插件-->
    <script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js${_v}"></script><!--可移动弹框插件公共js-->
    <script type="text/javascript" src="${ctx}/static/js/form.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery.form.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/my97datepicker/WdatePicker.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/common.js${_v}"></script>
    <style>
        .swfupload{display:inline-block;float:left;width:200px;height:20px;line-height:20px;border:solid #d6d6d6 1px;}
        .chooseFiles{padding: 2px 10px;width:158px;height: 158px;line-height: 20px;position: relative;cursor: pointer;color: #545454;background: #fafafa;border: 1px solid #ddd;overflow: hidden;display: inline-block;*display: inline;*zoom: 1}
        .chooseFiles:hover{text-decoration:none;}
        .chooseFiles .add-text{position:absolute;width:80px;height:30px;line-height:30px;left:50%;margin-left:-40px;bottom:40px;text-align:center;font-size:16px;}
        .chooseFiles .add-file-icon{position:absolute;width:20px;height:20px;font-size:30px;text-align:center;left:50%;margin-left:-10px;top:50px;}
        #files{position: absolute; font-size: 100px; right: 0; top: 0; opacity: 0; filter: alpha(opacity=0); cursor: pointer ;}
        #files:hover{color: #444; background: #eee; border-color: #ccc; text-decoration: none ;}
        .hyx-nc .bomp-p{width:350px;position:relative;margin-top:24px;}
        .hyx-nc .bomp-error .error{position:absolute;left:0;top:100%;font-size:12px;}
    </style>
     <% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
    <script type="text/javascript">
    var account = '${shrioUser.account}';
    var orgId = '${shrioUser.orgId}';
    var userId = '${shrioUser.id}';
    var tsmUpLoadServiceUrl = '${tsmUpLoadServiceUrl}'; // <!-- 上传服务器路径 -->
        var totalUploaded, fileCount, filesUploaded;

        //接收人文件上传
        function onFileSelect(e) {
            var files = e.target.files; // FileList object
            fileCount = files.length;
          for (var i=0; i<fileCount; i++) {
              var file = files[i];
              var names=file.name;
              var sarr=names.split(".");
              var lastOne=sarr.pop();
              var Mbs=file.size/(1024*1024);
                if(Mbs > 10 ){
                    alert("文件大小超过限制！");
                    return false;
                }
            if(lastOne !== "jpg" && lastOne !== "jpeg" && lastOne !== "png" && lastOne !== "pdf" && lastOne !== "docx" && lastOne !== "doc"){
                alert("只允许上传word、pdf、jpg、png格式的文件！");
                return false;
              }
          }
            startUpload();
        }

        function onUploadFailed(e) {
            alert("Error uploading file");
        }

        function uploadNext() {
            var url = tsmUpLoadServiceUrl+ctx + '/fileupload/upload';
            var xhr = new XMLHttpRequest();
            var fd = new FormData();
            var file = document.getElementById('files').files[filesUploaded];
            fd.append("file", file);
            fd.append("type", "2");
            fd.append("account", account);
            fd.append("orgId", orgId);
            fd.append("userId", userId);
            xhr.addEventListener("error", onUploadFailed, false);
            xhr.open("POST", url);
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    var txt = xhr.responseText;
                    var data = JSON.parse(txt);
                    if (data.code == '0') {
                        window.top.iDialogMsg("提示", data.message);
                        return;
                    }
                    if (data == null || data == 'undefined') {
                        return;
                    }
                    if (typeof (data.status) != 'undefined') {
                        if (data.status == 'success') {
                            var fileIds = $("#fileIdsStr").val();
                            $("#fileIdsStr").val(fileIds + data.fileId + ","); // 获取附件ID
                            $("#fileList")
                                    .append(
                                            "<li id=\"file_li_"
                                                    + data.fileId
                                                    + "\"><label class=\"name\">"
                                                    + data.fileName
                                                    + "</label><a href=\"###\" onclick=\"delfile('"
                                                    + data.fileId
                                                    + "')\" class=\"link\">删除</a></li>");
                        }
                    }
                }
            };
            xhr.send(fd);
        }
        function startUpload() {
            totalUploaded = filesUploaded = 0;
            uploadNext();
        }
        window.onload = function() {
            document.getElementById('files').addEventListener('change',
                    onFileSelect, false);//接收人上传文件
        };
    </script>
    <script type="text/javascript" src="${ctx }/static/js/view/rest/ajaxfileupload.js"></script>
    <script type="text/javascript" src="${ctx }/static/js/view/qupai/add-leads.js${_v}"></script>
</head>
<body>
    <shiro:hasPermission name="mySignCust_addOrder">
        <input type="hidden" id="mySignCust_addOrder" value="1"/>
    </shiro:hasPermission>

    <form id="cform" action="${ctx }/credit/lead/addLead" method="post">
        <div class="hyx-nc com-search01">
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
                                       value='${custInfoBean.name }'  ${(field.isRead eq 0  || isRead) ? '':'readonly'}
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
                        </label><input type='text' id="${field.fieldCode }" name="${field.fieldCode }"
                                       value='<fmt:formatDate value="${custInfoBean.birthday }" pattern="yyyy-MM-dd"/>'
                                       checkProp="${field.isRequired eq 0?'':'chk_5_1' }"  ${(field.isRead eq 0  || isRead) ? '':'readonly'}
                                       onclick="WdatePicker({maxDate:'%y-%M-%d'})" class='ipt_a fl_l'/>
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
                               checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"   ${(field.isRead eq 0  || isRead) ? '':'disabled'}
                               class='ipt_a fl_l'/>
                       <span class="copy_telno"
                             title="复制" onclick="copyPhone('mobilephone_safe')"
                             style="${idReplaceWord eq '1'?'display: none':'display:block'}"></span>
                        <span class="min-dele delete_telno" title="清除" onclick="clearPhone('mobilephone','mobilephone_safe','error_dp')"></span>
                        <input type='text' id="${field.fieldCode }_safe" name="${field.fieldCode }_safe"
                               value='${custInfoBean.mobilephone }'
                               checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"   ${(field.isRead eq 0  || isRead)  ? '':'disabled'}
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
                               checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"     ${(field.isRead eq 0  || isRead)  ? '':'disabled'}
                               class='ipt_a fl_l'/>
                        <span class="copy_telno" id="button_telphone_copy"
                              title="复制" onclick="copyPhone('telphone_safe')"
                              style="${ (idReplaceWord eq '1' or empty custInfoBean.telphone )?'display: none':'display:block'}">
                        </span>
                        <span class="min-dele delete_telno" title="清除" onclick="clearPhone('telphone','telphone_safe','error_dp')"></span>
                        <input type='text' id="${field.fieldCode }_safe" name="${field.fieldCode }_safe"
                               value='${custInfoBean.telphone }'
                               checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"     ${(field.isRead eq 0  || isRead)  ? '':'disabled'}
                               class='ipt_a fl_l'  onblur="hiddenOrShowButton('telphone','${idReplaceWord}')"/>
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
                                       checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'readonly'}
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
                                       checkProp="${field.isRequired eq 0?'chk_1_0':'chk_1_1' }"  ${(field.isRead eq 0  || isRead) ? '':'readonly'}
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
                        <input type='text'   id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l'  <c:if test="${field.isRead eq 1 }">readonly</c:if>/>
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
                        <input type='text'  <c:if test="${field.isRead eq 0 }">onclick="WdatePicker()"</c:if>  id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_5_1' }" class='ipt_a fl_l' <c:if test="${field.isRead eq 1 }">readonly</c:if> />
                        <span class="error"  name='a' id="error_${ field.fieldCode}"></span>
                    </p>                                            
            </c:when>
            <c:when test="${ field.dataType eq 3 }">
                    <p class='bomp-p' >
                        <label class='lab_a fl_l'><c:if test="${field.isRequired eq 1 }"><i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i></c:if>${field.fieldName}：</label>
                        <select name="${field.fieldCode }" class='sel_a fl_l' <c:if test="${field.isRequired eq 1 }">checkProp="chk_2_1"</c:if> <c:if test="${field.isRead eq 1 }">disabled</c:if>>
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
                    <dl class="select pos2" data-input="[name='${ field.fieldCode}']" data-multi="true" <c:if test="${field.isRead eq 1 }">disabled</c:if>>
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
                        <input type="hidden" name="${ field.fieldCode}" id="s_${ field.fieldCode}" value="${field.showValue }" checkProp="${field.isRequired eq 0?'':'chk_1_1' }" />
                        <span class="error" name='a' id="error_${field.fieldCode }"></span>
                    </dl>
                </div>
            </c:when>
              </c:choose>
            </c:if>
            </c:forEach>
            
            </c:if>
           
            <center class="com-btnbox">
                    <a href="javascript:;" id="addBtn" class="com-btna com-btna-sty" style="margin:0 auto;"><label>保存</label></a>
            </center>
        </div>
    </form>
</body>
</html>
