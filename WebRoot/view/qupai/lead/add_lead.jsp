<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<html>
<head>
    <title>新增放款</title>
    <%@ page import="com.qftx.base.shiro.ShiroUtil" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/font-awesome-4.7.0/css/font-awesome.min.css${_v}"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/style.css${_v}">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/com-search.css${_v}"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/leadAddEdit.css${_v}"/>
    <script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.2.min.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/idialog/jquery.idialog.js${_v}" dialog-theme="default"></script><!--可移动弹框插件-->
    <script type="text/javascript" src="${ctx}/static/js/idialog/idialog.common.js${_v}"></script><!--可移动弹框插件公共js-->
    <script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery.form.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/my97datepicker/WdatePicker.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/form.js${_v}"></script>
    <script type="text/javascript" src="${ctx}/static/js/time/moment.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/common.js${_v}"></script>
    <script src="${ctx}/static/thirdparty/jquery.validation/dist/jquery.validate.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${ctx}/static/js/common/jquery-validate.js" type="text/javascript" charset="utf-8"></script>
    <% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
    <script>
    	console.log(${fieldSetsJson});
    </script> 
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
                /* if(Mbs > 10 ){
                    alert("文件大小超过限制！");
                    return false;
                } */
                if(lastOne !== "jpg" && lastOne !== "png" && lastOne !== "gif"){
	                alert("只允许上传jpg、png、gif格式的文件！");
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
                            $("#fileId").val( data.fileId ); // 获取附件ID
                            $("#uploadImg").attr("src", data.url );
                            $("#loanBill").val(data.url);
                            $("#uploadImg").show();
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
            document.getElementById('files').addEventListener('change',onFileSelect, false);//接收人上传文件
        };
    </script>
    <script type="text/javascript" src="${ctx }/static/js/view/rest/ajaxfileupload.js"></script>
    <script type="text/javascript" src="${ctx }/static/js/view/qupai/add-leads.js"></script>
</head>
<body>
    <form id="leadOperateForm" action="${ctx }/credit/lead/addLead" method="post">
    	<input type="hidden" id="idReplaceWord" value=${idReplaceWord }>
    	<input type="hidden" name="leadId" value='${leadInfo.leadId }' />
        <div class="hyx-nc com-search01">
        <c:if test="${ !empty fieldSets}">
                <c:forEach items="${fieldSets}" var="field" varStatus="vs">
                    <c:if test="${field.enable eq 1}">
                       <c:choose>
                       	<c:when test="${ field.dataType eq 1 && field.fieldCode=='custName' }">
                                <div class='bomp-p'>
                                    <label class='lab_a fl_l'>
                                        <c:choose>
                                            <c:when test="${field.isRequired eq 1 }">
                                                <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>                                                   
                                            </c:when>
                                        </c:choose>
                                        ${field.fieldName}：
                                    </label>
                                    <input type="hidden" name="company" value='${leadInfo.company }' />
                                    <input type="hidden" name="rciId" value='${leadInfo.rciId }' />
                                    <input type='text' autocomplete="off" data-readonly=true <c:if test="${field.isRequired == 1 }">data-rule-required="true"</c:if> <c:if test="${field.isRead == 1 }">data-readonly=true</c:if> id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l' />
                                    <div class="custname-drop">
                                    	<div class="head">
                                    		<input type="text" class="search-text" />
                                    		<button type="button" class="btn-custname-search">查询</button>
                                    	</div>
                                    	<div class="body">
                                    		<ul class="custname-drop-ul"></ul>
                                    	</div>
                                    </div>
                                    <span class="error"  name='a' id="error_${ field.fieldCode}"></span>
                                </div>
                        </c:when>
                        <c:when test="${ field.dataType eq 1 && field.fieldCode!='custName' && field.fieldCode!='batch' }">
                                <p class='bomp-p'>
                                    <label class='lab_a fl_l'>
                                        <c:choose>
                                            <c:when test="${field.isRequired eq 1 }">
                                                <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                            </c:when>
                                        </c:choose>
                                        ${field.fieldName}：
                                    </label>
                                    <c:if test="${field.fieldCode == 'phone' }">
                                        <input type='text' <c:if test="${field.isRequired == 1 }">data-rule-required="true"</c:if> <c:if test="${field.isRead == 1 }">data-readonly=true</c:if> data-replaceword="true" id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }' data-hiddenvalue='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l' />
                                    </c:if>
                                    <c:if test="${field.fieldCode != 'phone' }">
                                        <input type='text' <c:if test="${field.isRequired == 1 }">data-rule-required="true"</c:if> <c:if test="${field.isRead == 1 }">data-readonly=true</c:if> id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l' />
                                    </c:if>
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
                                    <input type='text' <c:if test="${field.isRequired == 1 }">data-rule-required="true"</c:if> <c:if test="${field.isRead == 1 }">data-disabled=true data-readonly=true</c:if>  onclick="WdatePicker()"  id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_5_1' }" class='ipt_a fl_l' />
                                    <span class="error"  name='a' id="error_${ field.fieldCode}"></span>
                                </p>
                        </c:when>
                        <c:when test="${ field.dataType eq 3 }">
                                <p class='bomp-p' >
                                    <label class='lab_a fl_l'><c:if test="${field.isRequired eq 1 }"><i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i></c:if>${field.fieldName}：</label>
                                    <select <c:if test="${field.isRequired == 1 }">data-rule-required="true"</c:if> <c:if test="${field.isRead == 1 }">data-disabled=true</c:if> name="${field.fieldCode }" class='sel_a fl_l'>
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
                                    <dt <c:if test="${field.isRead == 1 }">data-disabled=true</c:if>>
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
                                     <input type="text" class="input-hidden" <c:if test="${field.isRequired == 1 }">data-rule-required="true"</c:if> name="${ field.fieldCode}" id="s_${ field.fieldCode}" value="${field.showValue }" checkProp="${field.isRequired eq 0?'':'chk_1_1' }" />
                                    <span class="error" name='a' id="error_${field.fieldCode }"></span>
                                </dl>
                            </div>
                        </c:when>
                        <c:when test="${ field.dataType eq 5 }">
                                <p class='bomp-p'>
                                    <label class='lab_a fl_l'>
                                        <c:choose>
                                            <c:when test="${field.isRequired eq 1 }">
                                                <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                            </c:when>
                                        </c:choose>
                                        ${field.fieldName}：
                                    </label>
                                    <input type='text' data-datatype="${field.dataType}" id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_1_1' }" <c:if test="${field.isRequired == 1 }">data-rule-required="true"</c:if> <c:if test="${field.isRead == 1 }">data-readonly=true</c:if> data-rule-money="true" class='ipt_a fl_l' />
                                    <span class="error"  name='a' id="error_${ field.fieldCode}"></span>
                                </p>
                        </c:when>
                        <c:when test="${ field.dataType eq 6 }">
                                <p class='bomp-p'>
                                    <label class='lab_a fl_l'>
                                        <c:choose>
                                            <c:when test="${field.isRequired eq 1 }">
                                                <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                            </c:when>
                                        </c:choose>
                                        ${field.fieldName}：
                                    </label>
                                    <input type='text' data-datatype="${field.dataType}" data-fieldcode="${field.fieldCode }" id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_1_1' }" <c:if test="${field.isRequired == 1 }">data-rule-required="true"</c:if> <c:if test="${field.isRead == 1 }">data-readonly=true</c:if> class='ipt_a fl_l' />
                                    <span class="error"  name='a' id="error_${ field.fieldCode}"></span>
                                </p>
                        </c:when>
                        <c:when test="${ field.dataType eq 7 }">
                                <p class='bomp-p'>
                                    <label class='lab_a fl_l'>
                                        <c:choose>
                                            <c:when test="${field.isRequired eq 1 }">
                                                <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                            </c:when>
                                        </c:choose>
                                        ${field.fieldName}：
                                    </label>
                                    <input type='text' data-datatype="${field.dataType}" data-fieldcode="${field.fieldCode }" id="${field.fieldCode }"  name="${field.fieldCode }"  data-fieldvalue='${field.fieldValue }' value='${field.showValue }' <c:if test="${field.isRequired == 1 }">data-rule-required="true"</c:if> <c:if test="${field.isRead == 1 }">data-readonly=true</c:if>  checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l' />
                                    <span class="error"  name='a' id="error_${ field.fieldCode}"></span>
                                </p>
                        </c:when>
                        <c:when test="${ field.dataType eq 8 }">
                                <p class='bomp-p'>
                                    <label class='lab_a fl_l'>
                                        <c:choose>
                                            <c:when test="${field.isRequired eq 1 }">
                                                <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                            </c:when>
                                        </c:choose>
                                        ${field.fieldName}：
                                    </label>
                                    <input type='text' data-datatype="${field.dataType}" data-fieldcode="${field.fieldCode }" id="${field.fieldCode }"  name="${field.fieldCode }"  data-fieldvalue='${field.fieldValue }' value='${field.showValue }' <c:if test="${field.isRequired == 1 }">data-rule-required="true"</c:if> <c:if test="${field.isRead == 1 }">data-readonly=true</c:if> checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l' />
                                    <span class="error"  name='a' id="error_${ field.fieldCode}"></span>
                                </p>
                        </c:when>
                        <c:when test="${ field.dataType eq 9 }">
                                <p class='bomp-p'>
                                    <label class='lab_a fl_l'>
                                        <c:choose>
                                            <c:when test="${field.isRequired eq 1 }">
                                                <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                            </c:when>
                                        </c:choose>
                                        ${field.fieldName}：
                                    </label>
                                    <input type='text' data-datatype="${field.dataType}" data-fieldcode="${field.fieldCode }" id="${field.fieldCode }"  name="${field.fieldCode }"  data-fieldvalue='${field.fieldValue }' value='${field.showValue }' <c:if test="${field.isRequired == 1 }">data-rule-required="true"</c:if> <c:if test="${field.isRead == 1 }">data-readonly=true</c:if> checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l' />
                                    <span class="error"  name='a' id="error_${ field.fieldCode}"></span>
                                </p>
                        </c:when>
                        <c:when test="${ field.dataType eq 10 }">
                                <p class='bomp-p loanVoucherBox'>
                                    <label class='lab_a fl_l'>
                                        <c:choose>
                                            <c:when test="${field.isRequired eq 1 }">
                                                <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                            </c:when>
                                        </c:choose>
                                        ${field.fieldName}：
                                    </label>
                                    <input type='text' class='input-hidden' <c:if test="${field.isRequired == 1 }">data-rule-required="true"</c:if> id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'   />
                                    <span class="error"  name='a' id="error_${ field.fieldCode}"></span>
                                   <%--  <input type='text'   id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l' />
                                    <span class="error"  name='a' id="error_${ field.fieldCode}"></span> --%>
                                     <!-- <a href="javascript:;" class="com-btna" id="spanButtonPlaceholder">上传附件</a> -->
                                        <!--  <input type="file" id="file" name="file" class="imgUpload"> -->
                                    <!--  <label class='lab_a fl_l'>借款凭证：</label> -->
                                    <c:choose>
                                    <c:when test="${!empty field.showValue }">
                                        <input type="hidden" id="fileId" name="fileId" value=""/>
	                                    <span class="file_box fl_l">
	                                         <a href="javascript:;" class="chooseFiles">
	                                                <img id="uploadImg" src="${field.showValue }"/>
	                                                <span class="add-file-icon">+</span>
	                                                 <span class="add-text">上传</span>
	                                                <input type="file" id="files" />
	                                        </a>
	                                    </span>
	                                    <!-- 上传进度条 -->
	                                    <div id="divFileProgressContainer" style="padding:2px;margin-top:8px;margin-left:94px;"></div>
	                                    <ul id="fileList" class="hyx-vc-list fl_l">

	                                    </ul>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" id="fileId" name="fileId" value=""/>
	                                    <span class="file_box fl_l">
	                                         <a href="javascript:;" class="chooseFiles">
	                                         		<img src="" id="uploadImg" style="display:none;"/>
	                                                <span class="add-file-icon">+</span>
	                                                 <span class="add-text">上传</span>
	                                                <input type="file" id="files" />
	                                        </a>
	                                    </span>
	                                    <!-- 上传进度条 -->
	                                    <div id="divFileProgressContainer" style="padding:2px;margin-top:8px;margin-left:94px;"></div>
	                                    <ul id="fileList" class="hyx-vc-list fl_l">

	                                    </ul>
                                    </c:otherwise>
                                    </c:choose>

                                </p>
                        </c:when>
                        <c:when test="${ field.dataType eq 11 }">
                                <p class='bomp-p'>
                                    <label class='lab_a fl_l'>
                                        <c:choose>
                                            <c:when test="${field.isRequired eq 1 }">
                                                <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                            </c:when>
                                        </c:choose>
                                        ${field.fieldName}：
                                    </label>
                                    <input type='text' <c:if test="${field.isRequired == 1 }">data-rule-required="true"</c:if> <c:if test="${field.isRead == 1 }">data-readonly=true</c:if>  id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'  checkProp="${field.isRequired eq 0?'':'chk_1_1' }" class='ipt_a fl_l' />
                                    <span class="error"  name='a' id="error_${ field.fieldCode}"></span>
                                </p>                                            
                        </c:when>
                          </c:choose>
                    </c:if>
                </c:forEach>
                
            </c:if>
           
            <div class="com-btnbox">
                    <button type="button" class="btn btn-primary btn-submit">保存</button>
            </div>
        </div>
    </form>
</body>
</html>
