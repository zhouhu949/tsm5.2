<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%@ page import="com.qftx.base.shiro.ShiroUtil" %>
<% pageContext.setAttribute("shrioUser", ShiroUtil.getShiroUser());%>
<script type="text/javascript" src="${ctx}/static/js/common/replaceWord.js${_v}"></script>
<script type="text/javascript">  
	    // 选中的，分页后再显示时也选中 
	     var $checks =$("#name_mobile_textarea").val().split(";");	
		    if($checks.length >0){
		    	var phoneVal = "";
		    	for(var i = 0;i<$checks.length-1;i++){
		    		var checkVal = valMap.get($checks[i]);
		    		if(checkVal.indexOf("|") > 0 ){
		    			phoneVal = checkVal.split("|")[1];
		    			$("#"+phoneVal+"_check").attr("checked",true);
		    		}else{
		    			$("#"+checkVal+"_check").attr("checked",true);
		    		}
		    	}
		    }	
	   $("input[type='checkbox']").attr('ondblclick', 'this.click()');		
	   var idReplaceWord = $("#idReplaceWord").val();	
	   
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
		var phone = $(obj).attr("phone");			
		var phone1 = "";
		if(idReplaceWord == 1){
			phone1 = replaceWord_(phone);
			if($.trim(phone1) == ""){
				phone1 = phone;
			}
		}else{
			phone1 = phone;
		}
		var name = $(obj).attr("custName");
		if($(obj).is(":checked")){		
			if(name != null && name != ""){
				if($.trim($("#name_mobile_textarea").val()) != ""){
						$("#name_mobile_textarea").val($("#name_mobile_textarea").val().replace(name+"|"+phone1+";",""));						
						valMap.remove(name+"|"+phone1);
						$("#name_mobile_textarea").val($("#name_mobile_textarea").val()+name+"|"+phone1+";");
						valMap.put(name+"|"+phone1,name+"|"+phone);			
				}else{
						$("#name_mobile_textarea").val(name+"|"+phone1+";");
						valMap.put(name+"|"+phone1,name+"|"+phone);					
				}				
			}else{				
				if($.trim($("#name_mobile_textarea").val()) != ""){	
					$("#name_mobile_textarea").val($("#name_mobile_textarea").val().replace(phone1+";",""));
					valMap.remove(phone1);
					$("#name_mobile_textarea").val($("#name_mobile_textarea").val()+phone1+";");
					valMap.put(phone1,phone);	
				}else{
					$("#name_mobile_textarea").val(phone1+";");
					valMap.put(phone1,phone);	
				}				
			}			
		}else{
			if(name !=null && name !=""){
				$("#name_mobile_textarea").val($("#name_mobile_textarea").val().replace(name+"|"+phone1+";",""));
				valMap.remove(name+"|"+phone1);
			}else{
				$("#name_mobile_textarea").val($("#name_mobile_textarea").val().replace(phone1+";",""));
				valMap.remove(phone1);
			}
		}
		
	}
	
	function ajax_Submit(){	
		var queryText = $("#queryText").val();
		var pageCurrent = document.getElementById("page.currentPage");
		var pageCount =  document.getElementById("page.showCount");
		var totalResult =  document.getElementById("page.totalResult");
		var url = "${ctx}/call/phone/custInfos";
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
		    
		    /********** 屏蔽手机或固话中间几位  *********/
		  
			if(idReplaceWord==1){
			    $("div[name=telphone_]").each(
			    		function(){
			    		   var str = $(this).text();
			    		   replaceWord(str,$(this));
			    		   replaceTitleWord(str,$(this));
			    		  }
			    );    
			}
			
			//判断是否ie11 搜索按钮向内靠
			var a1 = navigator.userAgent;
			var yesIE = a1.search(/Trident/i);
			if(yesIE > 0){
				$(".min-search").css("right","20px");
			}
		});
	   
</script>
<label class="arrow"><em>◆</em><span>◆</span></label>
<div class="box">
	<label class="ser"><input type='text' id="queryText"  value='${item.queryText}'  name="queryText" class="ipt" /><c:if test="${empty item.queryText}"><c:choose><c:when test="${shrioUser.isState eq 1 }"><span class="icon"  style="left:122px;">输入客户名称/联系人</span></c:when><c:otherwise><span class="icon"  style="left:110px;">输入客户姓名/单位名称</span></c:otherwise></c:choose>	</c:if><i class="icon_i min-search" onclick="ajax_Submit();"style="cursor:pointer;"></i></label>
	<div class='com-table bomp-table-a fl_l' style="width:97%;height:312px;overflow-y:auto;margin-left:6px;">
		<table width='100%' cellspacing='0' cellpadding='0' class='sty-borcolor-a sty-borcolor-c com-table-b com-radius' style="border:solid #cbcbcb 1px;">
			<thead>
				<tr class='sty-bgcolor-b'>
					<th><span class="sp sty-borcolor-b skin-minimal"><input type="checkbox" onclick="checkBoxAll(this)" /></span></th>
					<c:choose>
						<c:when test="${shrioUser.isState eq 1 }"><!-- 企业资源  -->
							<th><span class='sp sty-borcolor-b'>客户名称</span></th> 		
							<th><span class='sp sty-borcolor-b'>联系人</span></th>		
						</c:when>
						<c:otherwise>
							<th><span class='sp sty-borcolor-b'>客户姓名</span></th> 		
							<th><span class='sp sty-borcolor-b'>单位名称</span></th>
						</c:otherwise>
					</c:choose>	
					<th>联系电话</th>
				</tr>
			</thead>
			<tbody>              
				<c:choose>
					<c:when test="${not empty list}">
						<c:choose>
							<c:when test="${item.page.totalResult le 10 }">
								<c:forEach items="${list}" var="custInfo" varStatus="vs">
									<tr  class=" ${vs.count%2==0?'sty-bgcolor-b':''}">
										<td style="width:30px;"><div class="overflow_hidden w30 skin-minimal"><input type="checkbox"  name="check_" id="${custInfo.telphone }_check" onclick="checkedAcc(this)" phone="${custInfo.telphone}" custName="${custInfo.custName}"/></div></td>
										<c:choose>
											<c:when test="${shrioUser.isState eq 1 }"><!-- 企业资源  -->
												<td><div class='overflow_hidden w90' title="${custInfo.company }">${custInfo.company }</div></td>										
												<td><div class='overflow_hidden w50' title='${custInfo.custName }'>${custInfo.custName }</div></td>				
											</c:when>
											<c:otherwise>
												<td><div class='overflow_hidden w50' title='${custInfo.custName }'>${custInfo.custName }</div></td>
												<td><div class='overflow_hidden w90' title="${custInfo.company }">${custInfo.company }</div></td>										
											</c:otherwise>
										</c:choose>							
										<td><div class='overflow_hidden w90' title='${custInfo.telphone }' name="telphone_">${custInfo.telphone }</div></td>
									</tr>
								</c:forEach> 
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="4">
										输入的关键词不够精确，请优化查询关键词！
									</td>
								</tr>
							</c:otherwise>
						</c:choose>						
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
</div>