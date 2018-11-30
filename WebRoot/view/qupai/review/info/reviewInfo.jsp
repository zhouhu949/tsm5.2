<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/common/include.jsp" %>
<title>放款确认详情</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/leadAddEdit.css${_v}"/>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript" src="${ctx}/static/js/form.js"></script>
<script src="${ctx}/static/thirdparty/jquery.validation/dist/jquery.validate.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/static/js/common/jquery-validate.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript"> 

	function aaa() {
        if($("#myform").valid()){
            var data = $("#myform").serialize();
            $.ajax({
                type : 'post',
                url : ctx+"/credit/review/updateReviewInfo",
                data : data,
                success : function(data) {
                    if(data.result == 1){
                        parent.iDialogMsg('提示','保存成功！');
                        parent.closePubDivDialogIframe('review_operate');
                        parent.loadData();
                    }else{
                        parent.iDialogMsg('提示','保存失败！');
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    // 状态码
                    console.log(XMLHttpRequest.status);
                    // 状态
                    console.log(XMLHttpRequest.readyState);
                    // 错误信息
                    console.log(textStatus);
                }
            });
        }
	}

</script>
</head>
<body>
	<form id="myform">
	<input type="hidden" name="leadId" value="${leadId }" />
	<input type="hidden" id="idReplaceWord" value=${idReplaceWord }>
	<div class="hyx-nc">
        <c:if test="${ !empty fieldSets}">
            <c:forEach items="${fieldSets}" var="field" varStatus="vs">
            	<c:choose>
            		<c:when test="${field.enable eq 1 && field.fieldCode == 'fundAccount' && auditStatus == '1'}">
	                     <p class='bomp-p'>
	                         <label class='lab_a fl_l'>
	                             <c:choose>
	                                 <c:when test="${field.isRequired eq 1 }">
	                                     <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>                                                   
	                                 </c:when>
	                             </c:choose>
	                             ${field.fieldName}：
	                         </label>
	                         <input type='text'   id="${field.fieldCode }"  name="${field.fieldCode }"  value='${field.showValue }'  class='ipt_a fl_l' />
	                     </p>    
            		</c:when>
            		<c:when test="${field.dataType == 5 || field.dataType == 7 || field.dataType == 8 }">
                         <p class='bomp-p'>
                             <label class='lab_a fl_l'>
                                 <c:choose>
                                     <c:when test="${field.isRequired eq 1 }">
                                         <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
                                     </c:when>
                                 </c:choose>
                                 ${field.fieldName}：
                             </label>
                             <span class="detail-info" data-moneyformat="true" data-fieldcode="${field.fieldCode }" title='${field.showValue }'>${field.showValue }</span>
                         </p>
                    </c:when>
            		<c:when test="${field.dataType == 10 }">
                         <p class='bomp-p loanVoucherBox'>
                             <label class='lab_a fl_l'>
                                 <c:choose>
                                     <c:when test="${field.isRequired eq 1 }">
                                         <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>                                                   
                                     </c:when>
                                 </c:choose>
                                 ${field.fieldName}：
                             </label>
                             <c:if test="${!empty field.showValue }">
                                 <span class="file_box fl_l">
                                      <a href="javascript:;" class="chooseFiles">
                                          <img src="${field.showValue }"/>
                                     </a>
                                 </span>
                             </c:if>
                         </p>    
                    </c:when>
            		<c:otherwise>
            			 <p class='bomp-p'>
	                         <label class='lab_a fl_l'>
	                             <c:choose>
	                                 <c:when test="${field.isRequired eq 1 }">
	                                     <i class="font-color" style="display:inline-block;width:9px;text-align:left;">*</i>
	                                 </c:when>
	                             </c:choose>
	                             ${field.fieldName}：
	                         </label>
	                         <span class="detail-info" data-fieldcode="${field.fieldCode }" title='${field.showValue }'>${field.showValue }</span>
	                     </p>
            		</c:otherwise>
            	</c:choose>
            </c:forEach>
        </c:if>
    </div>
<%-- 	<c:if test="${result == '-1'}">
		${message}
	</c:if>
	${item.custName}<br/>
	<c:if test="${reviewAuth == '3'}">
	无需审核（测试）
	</c:if>
	<c:if test="${reviewAuth == '2'}">
	审核已完成（测试）
	</c:if> --%>
	<c:if test="${reviewAuth != '3'}">
	    <div class="progress">
            <c:if test="${list.size() > 0}">
                <span class="title">审核流程：</span>
            </c:if>
	        <c:forEach var="bean" items="${list}" varStatus="i">
                <div class="box">
                    <div class="round">
                        <span class="step">${i.index + 1}</span>
                        <span class="status">
                            <c:choose>
                                <c:when test="${bean.reviewResult == 0}">未完成</c:when>
                                <c:when test="${bean.reviewResult == 1}">进行中</c:when>
                                <c:when test="${bean.reviewResult == 2}">已完成</c:when>
                                <c:when test="${bean.reviewResult == 3}">驳回</c:when>
                            </c:choose>
                        </span>
                        <span class="account">审核人：${bean.reviewAcc}</span>
                        <span class="remarks" title=${bean.reviewRemark}>审核备注：${bean.reviewRemark}</span>

                    </div>
                    <c:if test="${i.index > 0}">
                        <div class="line">
                            <c:if test="${list[i.index-1].reviewResult == 2}">通过</c:if>&nbsp;
                            <c:if test="${list[i.index-1].reviewResult == 3}">驳回</c:if>&nbsp;
                        </div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
	</c:if>
	<c:if test="${reviewAuth == '1'}">
	    <div class="select">
            <label>审核结果：</label>
            <select name="result" data-rule-required="true">
                <option value="">请选择</option>
                <option value="2">通过</option>
                <option value="3">驳回</option>
            </select>
            <span class="error" style="position:relative;left:0;"></span>
		</div>
		<div class="textarea">
		    <label>审核备注：</label>
            <textarea name="remark" maxlength="50"></textarea>
        </div>
        <div class="button">
            <button class="button-sure" type="submit" onclick="aaa()">保存</button>
            <button class="button-cancel" type="button">取消</button>
        </div>
	</c:if>
	</form>
	<script>
	    $(function(){
            $('.button-cancel').on('click',function(e){
                parent.closePubDivDialogIframe('review_operate');
            });

            $('[data-moneyformat]').each(function(idx,item){
                var $item = $(item);
                var num = $item.text();
                num = formatMoney(num);
                $item.text(num);
                $item.attr('title',num);
            });
        });
	</script>
</body>
</html>