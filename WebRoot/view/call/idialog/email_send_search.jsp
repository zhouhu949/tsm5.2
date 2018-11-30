<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript">
		/********** 去除邮箱特殊字符，用于匹配  *********/
		$("input:checkbox[name=check_]").each(
			function(){
				var emailpp = $(this).attr("emailpp");
				if($.trim(emailpp)!=""){
					var emailVal = emailpp.toString().replace(/[~'!<>@#$%^&*()-+_=:.]/g, "")
					$(this).attr("emailpp",emailVal)
				}
			}
		);
	  // 选中的，分页后再显示时也选中 
	  var $checks =$("#name_email").val().split(",");	
	  
	  if($checks.length >0){
		  // 带姓名的邮箱
		  var nameReg = /^(\S+[^<])(<[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+>)$/; 
		  // 不带姓名的邮箱
		  var emailReg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/
	  	  var emailVal = "";
	  	for(var i = 0;i<$checks.length-1;i++){
	  		if(nameReg.test($checks[i])){
	  			emailVal = $checks[i].match(/[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+/g);
	  			var emailVa1 = emailVal.toString().replace(/[~'!<>@#$%^&*()-+_=:.]/g, "");
	  			$("input:checkbox[emailpp="+emailVa1+"]").attr("checked",true);
	  		}else if(emailReg.test($checks[i])){
	  			var emailVa1 = $checks[i].toString().replace(/[~'!<>@#$%^&*()-+_=:.]/g, "");
	  			$("input:checkbox[emailpp="+emailVa1+"]").attr("checked",true);
	  		}
	  	}
	  }				
	 $("input[type='checkbox']").attr('ondblclick', 'this.click()');		
	
	
	/**全选事件*/
	function checkBoxAll($obj){
		var isCheck = $($obj).is(":checked");
		$("input[name=check_]").each(function(index,obj){
			$(obj).attr("checked",isCheck);
			checkedAcc(obj);
		});
	}

	//操作复选框
	function checkedAcc(obj){
		var email = $(obj).attr("email");
		var name = $(obj).attr("custName");
		if($(obj).is(":checked")){
			if(name != null && name != ""){
				$("#name_email").val($("#name_email").val().replace(name+"<"+email+">,",""));
				$("#name_email").val($("#name_email").val()+name+"<"+email+">,");	
			}else{
				$("#name_email").val($("#name_email").val().replace(email+",",""));
				$("#name_email").val($("#name_email").val()+email+",");
			}			
		}else{
			if(name !=null && name !=""){
				$("#name_email").val($("#name_email").val().replace(name+"<"+email+">,",""));
			}else{
				$("#name_email").val($("#name_email").val().replace(email+",",""));
			}
		}
		
	}
	
	function ajax_Submit(){	
		var queryText = $("#queryText").val();
		var pageCurrent = document.getElementById("page.currentPage");
		var pageCount =  document.getElementById("page.showCount");
		var totalResult =  document.getElementById("page.totalResult");
		var url = "${ctx}/call/custInfos";
		var data = {'queryText':queryText};
		if(pageCount != null){
			data['page.showCount'] = pageCount.value;
		}
		if(pageCurrent != null){
			data['page.currentPage'] = pageCurrent.value;
		}
		if(totalResult != null){
			data['page.totalResult'] = totalResult.value;
		}
		$.post(url,data,function(data){
			$('#drop_').html(data);
		},'html');
	}
	$(function(){
	    $(".ser").find('.ipt,.icon').click(function(){
	        var ipt = $(this).parent().find('.ipt');
	        ipt.focus();
	        $(this).parent().find('.icon').hide();
	        ipt.blur(function(){
	            if($(this).val() == ''){
	                $(this).parent().find('.icon').show();
	            }
	         }); 
	    });	
	    $(".mail-sender-search").on("change",function(){
	    	ajax_Submit();
	    })
	});
</script>
<label class="arrow"><em>◆</em><span>◆</span></label>
	<div class="box">
		<label class="ser"><input type='text' id="queryText"  value='${item.queryText}'  name="queryText" class="ipt mail-sender-search" /><c:if test="${empty item.queryText}"><span class="icon">搜索</span></c:if><i class="icon_i min-search" style="cursor:pointer;" onclick="ajax_Submit();"></i></label>
		<div class='com-table bomp-table-a fl_l' style="width:97%;height:312px;overflow-y:auto;margin-left:6px;">
			<table width='100%' cellspacing='0' cellpadding='0' class='sty-borcolor-a sty-borcolor-c com-table-b com-radius' style="border:solid #cbcbcb 1px;">
				<thead>
					<tr class='sty-bgcolor-b'>
						<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" name="checkAll" onclick="checkBoxAll(this)" class="check" /></span></th>
						<th><span class='sp sty-borcolor-b'>${empty filedMap['name'] ? '客户名称' : filedMap['name'] }</span></th> 
						<c:if test="${shrioUser.isState eq 1 }"> <!-- 企业资源  -->
							<th><span class='sp sty-borcolor-b'>联系人</span></th>
						</c:if>
						<th>邮箱</th>
					</tr>
				</thead>
				<tbody> 
					<c:choose>
						<c:when test="${not empty list}">
							<c:forEach items="${list}" var="custInfo" varStatus="vs">
								<tr  class=" ${vs.count%2==0?'sty-bgcolor-b':''}">
									<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox"  name="check_"  emailpp = "${custInfo.email}" onclick="checkedAcc(this)" email="${custInfo.email}" custName="${custInfo.custName }"/></div></td>
									<td><div class='overflow_hidden w90' title="${custInfo.company }">${custInfo.company }</div></td>
									<c:if test="${shrioUser.isState eq 1 }"> <!-- 企业资源  -->
										<td><div class='overflow_hidden w50' title='${custInfo.custName }'>${custInfo.custName }</div></td>
									</c:if>
									<td><div class='overflow_hidden w90' title='${custInfo.email }' >${custInfo.email }</div></td>
								</tr>
							</c:forEach>    
						</c:when>
						<c:otherwise>
							<tr>
									<td colspan="4" style="text-align: center;">
                                		<div class="overflow_hidden w120" title="当前列表无数据">当前列表无数据！</div>
                            		</td>
							</tr>
						</c:otherwise>
					</c:choose>
					         										
				</tbody>
			</table>
		</div>
		<div class="com-bot">
			<div class="com-page fl_r">
				${item.page.ajaxPageStr}
			</div>
		</div>
</div>