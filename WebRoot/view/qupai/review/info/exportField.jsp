<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
    <head>
        <%@ include file="/common/include.jsp" %>
        <title>放款确认-导出</title>
        <link href="${ctx}/static/css/dialog.css" rel="stylesheet" type="text/css" />
        <style type="text/css">
            body{
                height:100%;
            }
            .formItem{
                width: 100px;
                display: inline-block;
                font-size: 12px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
                height: 24px;
                line-height: 24px;
                cursor:pointer;
            }
            .formItem input{
                vertical-align: middle;
            }
            .btn-box{
                overflow: hidden;
                position: absolute;
                bottom: 10px;
                left: 50%;
                margin-left: -104px;
            }
            .formItem-box{
                padding: 10px 20px;
                overflow-y: auto;
                height: 100%;
                box-sizing: border-box;
            }
            .form-title{
                margin-bottom: 20px;
                font-size: 14px;
            }
            .submit-form{
                height:100%;
                padding-bottom:40px;
                position: relative;
                box-sizing: border-box;
            }
        </style>
<script type="text/javascript">
	$(function() {
		var message = '${message}';
		var leadData = parent.formDataSerialize();
		/*保存*/
	    $("#saveResBtn").on("click",function(e){
	        e.stopPropagation();
	        var form = $(".submit-form");
	        var dataStr = dealFormData(form);
	        leadData.exportColumnStr = dataStr
	        console.log(leadData);
	        var data = removeObjectName(leadData,"page");
	        if($("#count").val() == 0){
	            iDialogMsg("提示","当前条件下暂无放款数据，请重新选择条件再进行导出！");
	            return false;
	        }
	        if(!form.serializeArray().length){
	            iDialogMsg("提示","请至少选择一个字段！");
	            return false;
	        }
	        
	        
	        window.parent.location.href = ctx+"/credit/review/exportData?" + jQuery.param(data);
	
	        closeParentPubDivDialogIframe('export');
	    })
		/*取消*/
		$("#cacleResBtn").on("click",function(e){
	        e.stopPropagation();
	        closeParentPubDivDialogIframe('export');
	    })
	});
	function dealFormData(form){
	    var arr = form.serializeArray();
	    var returnData = []
	    arr.forEach(function(it){
	        returnData.push(it.name)
	    })
	    return returnData.join(",")
	}
	function removeObjectName(obj,name){
	    var newObj = obj;
	    delete newObj[name]; 
	    return newObj;
	}
</script>
</head>
<body>
<%-- 	<table width="100%" cellspacing="0" cellpadding="0"
		class="sty-borcolor-a sty-borcolor-c com-table-a">
		<thead>
			<c:forEach var="bean" items="${list}" varStatus="i">
				</tr>
					<td>${bean.searchName}</td>
					<td>${bean.searchCode}</td>
					<td>${bean.developCode}</td>
				</tr>
			</c:forEach>
		</thead>
	</table> --%>
	
	<form class="submit-form">
            <div class="formItem-box">
                <div class="form-title">当前查询条件下共 ${count }条数据，导出需要选择导出字段</div>
                 <input type="hidden" id="count" value="${count}"/>
                <c:forEach var="bean" items="${list}" varStatus="i"> 
	                 <label class="formItem" title="${bean.searchName}">
		                <input type="checkbox" name="${bean.developCode}" value="1" checked/>
		                <span class="labelName">${bean.searchName}</span>
		            </label>
                </c:forEach> 
            </div>
            <div class="btn-box">
                    <a href="###" id="saveResBtn" class="com-btna bomp-btna sure-btn com-btna-sty fl_l"><label>保存</label></a>
                    <a href="###" id="cacleResBtn" class="com-btna bomp-btna cancel-btn fl_l"><label>取消</label></a>
            </div>
        </form>
</body>
</html>